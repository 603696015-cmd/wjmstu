package com.msc.applet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;


/**
 * 
 * @author jiahaijiang
 */
class PcmRecord implements Runnable {
	// 获取录音对象.
	TargetDataLine mTargetDataLine;
	// 录音线程，防止录音造成界面刷新卡断.
	Thread mThread;
	// 错误描述.
	String mErrStr;
	// 外部音频输入流.
	AudioInputStream mAudioInputStream;
	RecognizeApplet mRecognizeApplet;

	private String recordName;
	private String uploadUrl;
	
	private byte audioBytes[];
	
	private String filePath = "";

	/**
	 * @param applet
	 * @param userId
	 * @param examNum
	 * @param subjectNum
	 * @param uploadUrl
	 */
	public void start(RecognizeApplet applet, String fileName, String uploadUrl) {
		this.recordName = fileName;
		this.uploadUrl = uploadUrl;
		mErrStr = null;
		mRecognizeApplet = applet;

		mThread = new Thread(this);
		mThread.setName("PcmRecord");
		mThread.start();

		// 定时器，可以用来控制录音时间.
		// RecordTimer timer = new RecordTimer();
		// timer.start();
	}

	public String stop() {
		mThread = null;
		return this.filePath;
	}
	
	public void uploadRecord(){
		mThread = null;
		UploadRecordFile upload = new UploadRecordFile("c:/record/"+this.recordName,this.recordName,this.uploadUrl);
		upload.send();//上传文件
	}

	private void shutDown(String message) {
		if ((mErrStr = message) != null && mThread != null) {
			mThread = null;
			System.err.println(mErrStr);
		}
	}

	public void run() {
		mAudioInputStream = null;

		AudioFormat format = getRecordFormat();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info)) {
			shutDown("Line matching " + info + " not supported.");
			return;
		}
		try {
			mTargetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			mTargetDataLine.open(format, mTargetDataLine.getBufferSize());
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
			shutDown("Unable to open the line: " + ex);
			return;
		} catch (SecurityException ex) {
			shutDown(ex.toString());
			return;
		} catch (Exception ex) {
			shutDown(ex.toString());
			return;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int frameSizeInBytes = format.getFrameSize();
		int bufferLengthInFrames = mTargetDataLine.getBufferSize() / 8;
		int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
		byte[] data = new byte[bufferLengthInBytes];
		int numBytesRead;
		mTargetDataLine.start();
		while (mThread != null) {
			if ((numBytesRead = mTargetDataLine.read(data, 0,
					bufferLengthInBytes)) == -1) {
				break;
			}
			out.write(data, 0, numBytesRead);

			// recognizer.writeAudio(data, data.length);
		}
		// recognizer.stopListening();
		mTargetDataLine.stop();
		mTargetDataLine.close();
		mTargetDataLine = null;
		try {
			out.flush();
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		audioBytes = out.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
		mAudioInputStream = new AudioInputStream(bais, format,
				audioBytes.length / frameSizeInBytes);
		try {
			mAudioInputStream.reset();
			File file = new File("c:\\record");
			if (!file.isDirectory()) {
				file.mkdir();
			}
			save(mAudioInputStream, "c:\\record\\" + this.recordName);
			
			AudioConverter();

			// 录音数据已经保存在audioBytes中.
			// FileUtil.saveAudio("E:/record.pcm", audioBytes);
			// FileUtil.send(mRecognizeApplet.getCodeBase(),
			// "http://localhost:8080/servlet/RecognizeServlet"
			// , audioBytes);
			// mRecognizeApplet.setContent("---------------path:" +
			// mRecognizeApplet.getCodeBase()
			// + "/servlet/RecognizeServlet");
			// mRecognizeApplet.repaint();
			// String content = FileUtil.send(mRecognizeApplet.getCodeBase(),
			// "servlet/RecognizeServlet"
			// , audioBytes);
			// mRecognizeApplet.setContent("---------------content:" + content);
			// mRecognizeApplet.repaint();
			// DataUtils.updTime("正在上传……");
			// saveToFile(Type.WAVE);
			filePath = "c:\\record\\" + this.recordName;
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}
	
	public void AudioConverter(){
		String path = "c:\\record\\" + this.recordName;
		String filePathwma =  path.substring(0,path.lastIndexOf(".")) + ".wma";
		String src = path;
		String target = filePathwma;
		
		com.msc.applet.AudioConverterUtil audioConverter = new com.msc.applet.AudioConverterUtil();
		try {
			audioConverter.convertAudioFiles(src, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void save(InputStream is, String path) throws Exception {
		File file = new File(path);
		OutputStream os = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] b = new byte[1000];
		int of = b.length;
		while ((of = bis.read(b, 0, of)) != -1) {
			bos.write(b, 0, of);
		}
		bos.flush();
		bis.close();
		bos.close();
		is.close();
		os.close();
	}


	public AudioFormat getRecordFormat() {
		float sampleRate = 16000.0F;// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;// 8,16
		int channels = 1;// 1,2
		boolean signed = true;// true,false
		boolean bigEndian = false;// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}


}
