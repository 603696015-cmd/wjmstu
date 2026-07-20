package com.sopia.record.service;

import com.iflytek.speech.RecognizerListener;
import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SpeechRecognizer;
import com.iflytek.speech.SynthesizerPlayer;
import com.iflytek.speech.SynthesizerPlayerListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class MscRecodServiceImpl
{
  private static final String APPID = "5193881f";
  private static MscRecodServiceImpl mObject;
  private static StringBuffer mStr = new StringBuffer();
  
  public static void main(String[] args)
  {
    System.out.println(System.getProperty("java.library.path"));
    System.out.println(getMscObj().recognize("D:\\20150414\\16k.pcm"));
  }
  
  public static MscRecodServiceImpl getMscObj()
  {
    if (mObject == null) {
      mObject = new MscRecodServiceImpl();
    }
    return mObject;
  }
  
  public String recognize(String recordFile)
  {
    if (SpeechRecognizer.getRecognizer() == null) {
      SpeechRecognizer.createRecognizer("appid=5193881f");//5193881f
    }
    return RecognizePcmfileByte(recordFile);
  }
  
  public String RecognizePcmfileByte(String recordFile)
  {
    try
    {
      FileInputStream io = new FileInputStream(new File(recordFile));
      byte[] data = new byte[io.available()];
      io.read(data);
      if (data == null)
      {
        mStr.append("no audio avaible!");
      }
      else
      {
        mStr.delete(0, mStr.length());
        SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
        recognizer.recognizeAudio(this.recListener, data, null, null, null);
        
        recognizer.recognizeStream(this.recListener, "sms", null, null);
        ArrayList<byte[]> buffers = splitBuffer(data, data.length, 4800);
        for (int i = 0; i < buffers.size(); i++)
        {
          recognizer.writeAudio((byte[])buffers.get(i), ((byte[])buffers.get(i)).length);
          
          Thread.sleep(150L);
        }
        recognizer.stopListening();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return mStr.toString();
  }
  
  public ArrayList<byte[]> splitBuffer(byte[] buffer, int length, int spsize)
  {
    ArrayList<byte[]> array = new ArrayList();
    if ((spsize <= 0) || (length <= 0) || (buffer == null) || (buffer.length < length)) {
      return array;
    }
    int size = 0;
    while (size < length)
    {
      int left = length - size;
      if (spsize < left)
      {
        byte[] sdata = new byte[spsize];
        System.arraycopy(buffer, size, sdata, 0, spsize);
        array.add(sdata);
        size += spsize;
      }
      else
      {
        byte[] sdata = new byte[left];
        System.arraycopy(buffer, size, sdata, 0, left);
        array.add(sdata);
        size += left;
      }
    }
    return array;
  }
  
  private void Synthesize()
  {
    if (SynthesizerPlayer.getSynthesizerPlayer() == null) {
      SynthesizerPlayer.createSynthesizerPlayer("appid=5193881f");
    }
    SynthesizerPlayer synthesizer = SynthesizerPlayer.getSynthesizerPlayer();
    synthesizer.playText("科大讯飞语音合成测试程序", null, this.synListener);
  }
  
  private SynthesizerPlayerListener synListener = new SynthesizerPlayerListener()
  {
    public void onBufferPercent(int percent, int beginPos, int endPos) {}
    
    public void onEnd(SpeechError error) {}
    
    public void onPlayBegin() {}
    
    public void onPlayPaused() {}
    
    public void onPlayPercent(int percent, int beginPos, int endPos) {}
    
    public void onPlayResumed() {}
  };
  private RecognizerListener recListener = new RecognizerListener()
  {
    public void onBeginOfSpeech() {}
    
    public void onCancel() {}
    
    public void onEnd(SpeechError error) {}
    
    public void onEndOfSpeech() {}
    
    public void onResults(ArrayList results, boolean isLast)
    {
      for (Object obj : results)
      {
        RecognizerResult res = (RecognizerResult)obj;
        MscRecodServiceImpl.mStr.append(res.text);
      }
    }
    
    public void onVolumeChanged(int volume) {}
  };
}
