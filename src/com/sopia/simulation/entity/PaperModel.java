package com.sopia.simulation.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.simulation.util.SimulationUtil;

public class PaperModel {

	/**
	 * 通过试卷id获取试卷数据
	 * @param examId
	 * @return
	 */
	public static List<Paper> getPaperData(String examId,ExamPaperDao examPaperDao){
		List<Paper> paperList = new ArrayList<Paper>();
		try {
			Object cacheData =  SystemConfOp.getCache().get("exam_all_question_"+examId);
			String jsonArr = null;

			List<QuestionNum> list = new ArrayList<QuestionNum>();
			if(cacheData== null || cacheData.equals("")){
				//更新缓存
				List<ExamPaperBlock> musicSection = examPaperDao.listEpBlockByEpidAndType(Integer.parseInt(examId),1);//得到所有听力问题
				SimulationUtil.data = musicSection;
				Map<String,Object> mapResult = SimulationUtil.fetchMusicData(examPaperDao);
				List<Paper> listenMode = (List<Paper>) mapResult.get("questionItem");
				
				//阅读理解
				List<ExamPaperBlock> readSection = examPaperDao.listEpBlockByEpidAndType(Integer.parseInt(examId), 2);
				SimulationUtil.data = readSection;
				Map<String,Object> readResult = SimulationUtil.fetchReadModeData(examPaperDao,listenMode.size());
				List<Paper> readMode = (List<Paper>) readResult.get("questionItem");
				jsonArr = JSONObject.fromObject(mapResult).toString();
			
				
				//书写模式
				int size = listenMode.size() + readMode.size();//前俩者相加数据总和
				List<ExamPaperBlock> writeSection = examPaperDao.listEpBlockByEpidAndType(Integer.parseInt(examId), 3);//书写
				SimulationUtil.data = writeSection;
				Map<String,Object> writeResult = SimulationUtil.fetchWriteModeData(examPaperDao, size);
				
				SystemConfOp.getCache().put("paper_"+examId+"_type_1", jsonArr);
				SystemConfOp.getCache().put("paper_"+examId+"_type_2", JSONObject.fromObject(readResult).toString());//阅读理解
				SystemConfOp.getCache().put("paper_"+examId+"_type_3", JSONObject.fromObject(writeResult).toString());//书写理解
				
				//获取所有试题对象
				paperList.addAll(listenMode);
				paperList.addAll((List<Paper>)readResult.get("questionItem"));
				paperList.addAll((List<Paper>)writeResult.get("questionItem"));
				
				
				SystemConfOp.getCache().put("exam_all_question_"+examId, JSONArray.fromObject(paperList).toString());//所有题数
			}else{
				jsonArr = cacheData.toString();
				paperList = SimulationUtil.cacheDataToList(jsonArr);
			}
		} catch (Exception e) {
		}
		
		return paperList;
	}
	
	/**
	 * 获取分数
	 */
	public static int getUserPaperScore(String json,List<Paper> paperData){
		int score= 0;
		JSONArray jsonArr = JSONArray.fromObject(json);
		if(jsonArr.size() == 0){
			return 0;
		}
		
		for (Object object : jsonArr) {
			JSONObject jsonObject = (JSONObject) object;
			score+=getPaperDataScore(jsonObject, paperData);
		}
		
		return score;
		
	}
	
	public static int getPaperDataScore(JSONObject json,List<Paper> paperData){
		int score = 0;
		if(json.getString("answer").equals("")){
			return 0;
		}
		   // 创建 Pattern 对象
		  String pattern = "[a-z|A-Z]+";
		  String regexChar = "^[.。，,\"\\?!！;；：:‘‘’’']{1}$";
		try {
			for (Paper paper : paperData) {
				if(paper.getChildQuestion().size()>0){
					for (Paper paper2 : paper.getChildQuestion()) {
						if(!paper2.getId().equals(json.getString("id"))){
							continue;
						}
						if(paper2.getAnswer().equals(json.getString("answer"))){
							score = (int) ExamPaperUtil.getFloat(paper.getScore());
							break;
						}
						System.out.println(123);
					}
					
				}else{
					if(paper.getId().equals(json.get("id").toString())){
						String ansStr = paper.getAnswer().split(ElConstants.optSplit)[0];
						String qType = json.getString("type");
						String sysAnswer = ExamPaperUtil.getABC(ansStr);
						
						String temAns = json.get("answer").toString();
						//单选题
						if(!temAns.matches(pattern)){
							if(temAns.length() == 1&& !temAns.matches(regexChar)){
								//单选题
								temAns = json.get("answer").toString().equals("T")?"A":"B";
							}
							
						}
						
						//比对分数
						if(sysAnswer.equals(temAns)){
							score = (int) ExamPaperUtil.getFloat(paper.getScore());
						}
						
						
//					if(qType.equals("1")){
//						//听力
//						String temAns = json.get("answer").toString().equals("T")?"A":"B";
//						if(sysAnswer.equals(temAns)){
//							score = (int) ExamPaperUtil.getFloat(paper.getScore());
//						}
//						
//					}else if(qType.equals("2")){
//						//阅读理解
//						if(sysAnswer.equals(json.get("answer").toString())){
//							score = (int) ExamPaperUtil.getFloat(paper.getScore());
//						}
//					}else if(qType.equals("3")){
//						//书写听力
//						
//					}
						
						break;
					}
				}
				
			}
		} catch (ElException e) {
		}
		
		return score;
	}
	
	public static String answerToString(String json){
		JSONArray jsonArr = JSONArray.fromObject(json);
		StringBuffer sb = new StringBuffer();
	    // 创建 Pattern 对象
		int i = 0;
		int size = jsonArr.size();
		for (Object object : jsonArr) {
			JSONObject jsonObject = (JSONObject) object;
			String answer = jsonObject.get("answer").toString();
			
			if(i == size){
				sb.append(jsonObject.getString("id")+","+answer);
			}else{
				sb.append(jsonObject.getString("id")+","+answer+"|");
			}
			i++;
		}
		return sb.toString();
	}
}
