package com.msc.applet;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.msc.applet.HttpUtils.CallBack;
import com.msc.cache.CacheFactory;
import com.msc.cache.IBaseCache;
import com.msc.cache.JedisPoolUtil;

import net.sf.json.JSONObject;
import netscape.javascript.JSObject;

public class RecognizeApplet extends Applet
  implements ActionListener
{
  Button start = new Button("开始录音");
  Button stop = new Button("结束录音");
  TextField txt = new TextField();

  
  PcmRecord mPcmRecorder = new PcmRecord();
  String mDisplay = "iFlytek Recognize";

  String fileName = "20201_16043_13152.pcm";
  String uploadUrl;
  String str;
  String uid;
  private static int mAxis_Y = 30;

  public void init()
  {
    mAxis_Y = 30;

    add(this.start);
    this.start.addActionListener(this);
    Color c = new Color(126, 159, 174);
    setBackground(c);

    this.fileName = getParameter("filename");
    System.out.println(this.fileName + "!!!!!!!!!!!!!!!!!!!!!!!!!!");
    if (this.fileName == null) {
      this.fileName = "20201_16043_13152.pcm";
    }
    this.uploadUrl = getParameter("uploadUrl");
  }

  public String startRecord(String fileName, String uploadUrl)
  {
    return "开始录音";
  }

  public String endRecord()
  {
    System.out.println(this.str + "++++++++===================");
    return "结束录音";
  }
  
  public void setUid(String uid){
	  this.uid = uid;
  }

  public String uploadRecord()
  {
    return "上传完成";
  }

  public String getName(String fileName)
  {
    File file = new File("c:\\record\\" + fileName);
    return String.valueOf(file.isFile());
  }

  public void actionPerformed(ActionEvent e) {
    Button b = (Button)e.getSource();
    IBaseCache baseCache = CacheFactory.getRedisCache();
    final JSObject win = JSObject.getWindow(this);
    
    boolean flag = false;
    
    if (b.getLabel().equals("开始录音"))
    {
      win.eval("javascript:changeSoundwave(1);");
      System.out.println(this.fileName + "!!!!!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println(this.uploadUrl + "@@@@@@@@@@@@@@@@@@@@@@@@@");
      this.mPcmRecorder.start(this, this.fileName, "http://10.0.1.100:8877/record/RecordUploadServlet");
      b.setLabel("结束录音");
    }
    else if (b.getLabel().equals("结束录音")) {
    	b.setLabel("开始录音");
    	
    	this.mPcmRecorder.stop();
    	flag = true;
    	  try {
			Thread.sleep(3000L);
		} catch (InterruptedException e2) {
		}
		
		String filePath = "C:/record/" + this.fileName;
		File iofile = new File(filePath);
		if (!iofile.exists()) {
			// 没有生成文件
			win.call("setStr", new Object[] { "" });//请检查耳机是否有声音，未生成录音文件
		}else{
				try {
					// UploadRecordFile up = new
					// UploadRecordFile("C:\\record\\"+this.fileName,this.fileName,this.uploadUrl);
					// up.send();
					byte[] fileIos = UploadRecordFile.getBytesByFile(filePath);
					if (fileIos.length == 0) {
						win.call("setStr", new Object[] { "" });// 请检查耳机是否有声音，解析为空语音！
					} else {
						String temp [] = this.fileName.split("_");
						System.out.println(temp[0]);
						Map<String,String> params = new HashMap<String, String>();
						params.put("account", temp[0]);
						
						Map<String, byte[]> files = new HashMap<String, byte[]>();
						files.put("test", fileIos);
						HttpUtils.postFile1(this.uploadUrl, params, files);
					}

				} catch (UnsupportedEncodingException e1) {
				} catch (Exception e1) {
				}
				boolean flag1 = true;
				int i = 0;
				while(flag1){
					try {
						Thread.sleep(1000);
						i++;
						String temp [] = this.fileName.split("_");
						System.out.println("start get cache++++++++第"+temp[0]+"===="+i+"次+++++++++++++++");
						String key = temp[0]+"_audio_speak";
						System.out.println("key value"+key);
						System.out.println("测试值是：");
						System.out.println(baseCache.get("tset"));
						
						String speak =baseCache.containsKey(key)?baseCache.get(key).toString():"";
						System.out.println("fetch cache is"+speak+"==============");
						if(speak == null  || speak.equals("")){
							if(i == 10){
								win.eval("javascript:changeSoundwave(-1)");
								win.call("setStr", new Object[] { speak });
								flag1 = false;
								System.out.println("go here exception 3");
							}
							continue;
						}
						System.out.println("解析语音:"+speak);
						win.eval("javascript:changeSoundwave(-1)");
						win.call("setStr", new Object[] { speak });
						baseCache.put(temp[0]+"_audio_speak", null);
						flag1 = false;
						System.out.println("go here 1");
					} catch (InterruptedException e1) {
						flag1 = false;
						System.out.println("go here exception 2");
						e1.printStackTrace();
					}
				}
				win.eval("javascript:changeSoundwave(-1)");
				
		}
  
      //this.str = this.xf.recognize("C:/record/" + this.fileName);
      
    }
 
  }

  public void setContent(String str)
  {
    this.mDisplay = str;
  }

  public void paint(Graphics g)
  {
  }

}