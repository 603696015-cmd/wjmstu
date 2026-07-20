package com.sopia.questionman;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.intelligentTutoringPoints.IntelligentTutoringPointsConstants;
import com.sopia.questionman.entities.Question;
import com.sopia.record.service.MscRecodServiceImpl;

/**
 * 解析用户录音
 * @author TMK
 *
 */
public class QtypeUtil {
	public static MscRecodServiceImpl mscRecodService = (MscRecodServiceImpl)SpringContextUtil.getBean("mscRecodService");
	
	public static HttpSession getSession(){
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return session;
	}
	
	/**
	 * 录音文件是否存在
	 * @param q
	 * @return
	 */
	public static boolean checkFileIsExist(Question q){
		boolean flag = false;
		String fileName = J2EEFileUtil.getRealPath("/") 
		+ "elstuffs\\audio\\" +
		(Integer) getSession().getAttribute(ElConstants.SESSION_USERID) + "_" + 
		q.getMyExamPaperid() + "_" + 
		q.getId() + ".pcm";
		File file = new File(fileName);
		if(file.isFile() && file.exists()){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 获取录音文件名的
	 * @param q
	 * @return
	 */
	public static String getVoiceFile(Question q){
		String fileName = J2EEFileUtil.getRealPath("/") 
		+ "elstuffs\\audio\\" +
		(Integer) getSession().getAttribute(ElConstants.SESSION_USERID) + "_" + 
		q.getMyExamPaperid() + "_" + 
		q.getId() + ".pcm";
		File file = new File(fileName);
		if(file.isFile() && file.exists()){
			return (Integer) getSession().getAttribute(ElConstants.SESSION_USERID) + "_" + 
			q.getMyExamPaperid() + "_" + 
			q.getId() + ".pcm";
		}else{
			return "";
		}
	}
	
	/**
	 * 获取录音文件完整路径
	 * @param q
	 * @return
	 */
	public static String getVoiceFileAllPath(Question q){
		String fileName = J2EEFileUtil.getRealPath("/") 
		+ "elstuffs\\audio\\" +
		(Integer) getSession().getAttribute(ElConstants.SESSION_USERID) + "_" + 
		q.getMyExamPaperid() + "_" + 
		q.getId() + ".pcm";
		File file = new File(fileName);
		if(file.isFile() && file.exists()){
			return fileName;
		}else{
			return "";
		}
	}
	
	/**
	 * 获取pcm转换为wma格式的filename
	 * @param q
	 * @return
	 */
	public static String getRecodingWmaFilename(Question q){
		String fileName = J2EEFileUtil.getRealPath("/") 
		+ "elstuffs\\audio\\" +
		(Integer) getSession().getAttribute(ElConstants.SESSION_USERID) + "_" + 
		q.getMyExamPaperid() + "_" + 
		q.getId() + ".wma";
		File file = new File(fileName);
		if(file.isFile() && file.exists()){
			return (Integer) getSession().getAttribute(ElConstants.SESSION_USERID) + "_" + 
			q.getMyExamPaperid() + "_" + 
			q.getId() + ".wma";
		}else{
			return "";
		}
	}
	
	/**
	 * 获取录音文件解析的文本
	 * @param q
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getStuVoiceText(Question q){
		String fileName = J2EEFileUtil.getRealPath("/") 
		+ "elstuffs\\audio\\" +
		(Integer) getSession().getAttribute(ElConstants.SESSION_USERID) + "_" + 
		q.getMyExamPaperid() + "_" + 
		q.getId() + ".pcm";
		File file = new File(fileName);
		String voiceText = "";
		//判断如果录音文件存在
		if(file.isFile() && file.exists()){
			voiceText = mscRecodService.getMscObj().recognize(fileName);
		}
		voiceText = voiceText==null?"":voiceText;
		return voiceText;
	}
	
}
