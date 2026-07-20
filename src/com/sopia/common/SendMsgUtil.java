package com.sopia.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
/**
 * 发送短信
 * @author Administrator
 *
 */
public class SendMsgUtil {
	private static final Log logger=LogFactory.getLog(SendMsgUtil.class);

	/**
	 * 组装参数
	 * @return
	 */
	private String getContents(String username,String password,String movePhones,String content){
//		System.out.println("username="+username+"&password="+password+"&destaddr="+movePhones+"&content="+content);
		username=stringToUnicode_(username);
		password=stringToUnicode_(password);
		movePhones=stringToUnicode_(movePhones);
		content=stringToUnicode_(content);
		String contents="username="+username+"&password="+password+"&destaddr="+movePhones+"&content="+content;
		return contents.toUpperCase();
	}
	/**
	 * 发送短信
	 * @param movePhones 手机号（如果多个用分号隔开）
	 * @param content 内容
	 */
	public String sendMsg(String movePhones,String content){
		String serverUrl="http://27.50.130.2/sdk_asp/SendSms.asp";
		String username=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_MSG_USERNAME);
		String password=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_MSG_PASSWORD);
		String msg=postUrl(serverUrl, getContents(username,password,movePhones,content));
		if(msg.indexOf("&")==-1){//有错误
			return "发送短信失败，请联系管理员！";
		}else{
			//根据返回的数据进行处理
			String[] msgArray=msg.split("&");
			if(Integer.parseInt(msgArray[0].split("=")[1])==0){
				//msgArray[2].split("=")[1].toString();//发送提交数量
				return "发送成功！";
			}else{
				return msgArray[1].split("=")[1].toString();
			}
		}
	}
	
	/**
	 * 发送短信
	 * @param url 服务地址
	 * @param contents  post参数
	 * @return
	 */
	private String postUrl(String url,String contents){
		InputStream ins=null;
		InputStreamReader isr=null;
		
		BufferedReader br=null;
//		System.out.println("**"+contents);
		try {
			URL serverUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection)serverUrl.openConnection();
			conn.setRequestMethod("POST");
//			conn.addRequestProperty("Cookie", cookie);   
			conn.addRequestProperty("Accept-Charset", "GB2312");//GB2312, 
//			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
			conn.setDoOutput(true);//使用输出流 需要开启
			conn.connect();
			conn.getOutputStream().write(contents.getBytes());
			if(conn.getResponseCode()==conn.HTTP_OK){
				ins = conn.getInputStream();
				String charset = "GB2312";
				isr = new InputStreamReader(ins, charset);
				br = new BufferedReader(isr);
				String line = "";
				StringBuffer res = new StringBuffer();
				do {
					res.append(line);
					line = br.readLine();
				} while (line != null);
				System.out.println(">>>===" + res);
				return res.toString();
			}else{
				logger.error("短信服务器错误！");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("发送短信失败，请联系管理员！！",e);
		}finally{
			try {
				if(br!=null)
					br.close();
			} catch (IOException e) {
				logger.error("流关闭失败!",e);
			}
			try {
				if(isr!=null)
					isr.close();
			} catch (IOException e) {
				logger.error("流关闭失败!!",e);
			}
			try {
				if(ins!=null)
					ins.close();
			} catch (IOException e) {
				logger.error("流关闭失败!!!",e);
			}
		}
		return "发送短信失败，请联系管理员！！！！";
	}
	
	/**
	 *  把中文字符串转换为十六进制Unicode编码字符串  
	 */
	public String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			if (ch > 255){
				str += "\\u" + Integer.toHexString(ch);
			}else{
				str += "\\" + Integer.toHexString(ch);
			}
		}
		return str;
	}
	/**
	 * 把十六进制Unicode编码字符串转换为中文字符串
	 */ 
    public String unicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}
    
	/**
	 * 把中文字符串转换为十六进制Unicode编码字符串
	 */
	public String stringToUnicode_(String s) {
		String str = "";
		String strr="";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			if (ch > 255){
				//str += Integer.toHexString(ch)+"";
				strr = Integer.toHexString(ch)+"";
				str+=strr.substring(2,4)+strr.substring(0,2);
			}else{
				str += Integer.toHexString(ch)+"00";
			}
		}
		return str;
	}
	
//	public static void main(String[] args) {
//	username=stringToUnicode_("aawhlcy");
//	password=stringToUnicode_("aa123456");
////	System.out.println(postUrl(sendUrl,"username=testD001&password=111111"));
////	String sendUrl="http://27.50.130.2/sdk_asp/login.asp";
////	System.out.println(postUrl(sendUrl,"username="+username+"&password="+password));
//	//movePhone=stringToUnicode_("18665069096");
//	movePhone=stringToUnicode_("15101104019;18664824996;18665069096");
//	//movePhone=stringToUnicode_("18664824996");
//	//content=stringToUnicode_("尊敬的客户您好，您很好，您非常好，您非常的好，您非常非常好!!!......(本条短信不免费)");
//	content=stringToUnicode_("尊敬的客户您好，这是一条祝福短信，祝福您年年好，月月好，天天好，时时刻刻都好!!!......(本条短信不免费)");
//	String sendUrl="http://27.50.130.2/sdk_asp/SendSms.asp";
//	String prams="username="+username+"&password="+password+"&destaddr="+movePhone+"&content="+content;
//	System.out.println(prams);
//	System.out.println(content=stringToUnicode_("尊"));
//	System.out.println(postUrl(sendUrl,prams.toUpperCase()));
//}
}
