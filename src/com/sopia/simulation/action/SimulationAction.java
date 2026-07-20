package com.sopia.simulation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.MD5;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.schedule.entities.Eluser;
import com.sopia.simulation.dao.SimulationDao;
import com.sopia.simulation.entity.Paper;
import com.sopia.simulation.entity.PaperModel;
import com.sopia.simulation.entity.QuestionNum;
import com.sopia.simulation.entity.SimulationResult;
import com.sopia.simulation.util.SimulationUtil;

/**
 * 模拟考试action
 * @author zahj
 *
 */
/**
 * @author zahj
 *
 */
public class SimulationAction extends BaseAction{
	private int sublibs;
	private ExamPaper examPaper;
	private ExamPaperDao examPaperDao;
	
	private UserDao userDao;
	
	private List<ExamPaper> examPapers;
	private ExamPaperLib epl = null;
	private ExamPaperLib eplTree;
	
	/**
	 * 模拟考核
	 */
	private SimulationDao simulationDao;
	
	
	/**
	 * 
	 * @Title: ajaxHead 
	 * @author dongke
	 * @date  2017年12月14日 下午2:12:07
	 * @Description: TODO(设置AJAXhead) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void ajaxHead(){
		HttpServletResponse resp=this.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
	}
	
	/**
	 * 正常
	 * @author dongke
	 */
	public void ajaxSuccess(Object obj){
		PrintWriter localPrintWriter;
		try {
			getResponse().setContentType("application/json;charset=utf-8");
			getResponse().setCharacterEncoding("UTF-8");
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson(); 
			Map<String,Object> json = new HashMap<String,Object>();
			json.put("status", 200);
			json.put("data", obj);
			localPrintWriter.println(gson.toJson(json));
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	/**
	 * 
	 * @Title: ajaxError 
	 * @author dongke
	 * @date  2017年12月14日 下午2:16:50
	 * @Description: TODO(异步请求失败) 
	 * @param @param obj    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void ajaxError(Object obj){
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson();
			Map<String,Object> json = new HashMap<String,Object>();
			json.put("status", 400);
			json.put("error", obj);
			localPrintWriter.println(gson.toJson(json));
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	
	/**
	 * 正常
	 * @author dongke
	 */
	public void ajaxSuccess(Object obj,Object obj2){
		PrintWriter localPrintWriter;
		try {
			getResponse().setContentType("application/json;charset=utf-8");
			getResponse().setCharacterEncoding("UTF-8");
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson(); 
			Map<String,Object> json = new HashMap<String,Object>();
			json.put("status", 200);
			json.put("data", obj);
			json.put("itemNum", obj2);
			System.out.println(gson.toJson(json));
			localPrintWriter.println(gson.toJson(json));
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	
	/**
	 * 正常
	 * @author dongke
	 */
	public void ajaxSuccess(String obj,boolean flag){
		PrintWriter localPrintWriter;
		try {
			getResponse().setContentType("application/json;charset=utf-8");
			getResponse().setCharacterEncoding("UTF-8");
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson(); 
			if(flag){
				Map<String,Object> json = new HashMap<String,Object>();
				json.put("status", 200);
				json.put("data", obj);
				localPrintWriter.println(gson.toJson(json));
			}else{
				localPrintWriter.println(obj);
			}
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	
	
	/**
	 * 模拟考试首页
	 * @return
	 * @throws ElException
	 */
	public String index() throws ElException{
		
		epl = examPaperDao.getEpLById(202);//默认模拟考试ID
		
		eplTree = examPaperDao.epLibTree(0,
				getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		sublibs = examPaper == null ? 1 : sublibs;
		examPapers = examPaperDao.listEpsByEplId(epl, sublibs, examPaper,
				getPageNow(), getPageSize(), 1);
		count = examPaperDao.listEpsByEpIdSize(epl, sublibs, examPaper, 1);
		SystemConfOp.getCache().put("dongke", "I miss you");
		return "index";
	}
	
	/**
	 * 试卷登录
	 * @return
	 * @throws ElException
	 */
	public String login()throws ElException{
		
		String msg = "";
		String examId = this.getRequest().getParameter("examId");
		
		if("".equals(examId) || examId == null){
			msg = "获取试卷错误，试卷ID为null";
			System.out.println(msg);
			this.getRequest().setAttribute("msg", msg);
			return "index";
		}
		this.getRequest().setAttribute("examId", examId);
		
		return "index";
	}
	
	/**
	 * 登录认证
	 */
	public String loginCheck(){
		this.ajaxHead();
		String userName = getSessionValue(ElConstants.SESSION_USERNAME);
		String pwd =  this.getRequest().getParameter("pwd");
		
		if(pwd == null || pwd.equals("")){
			this.ajaxError("密码不能为空");
			return null;
		}
		
		try {
			pwd = MD5.crypt(pwd);
			boolean isLogin = userDao.check(userName,pwd);
			this.ajaxSuccess(isLogin);
		} catch (ElException e) {
			this.ajaxError("登录失败");
		}
		return null;
	}
	
	/**
	 * 信息确认页面
	 * @return
	 * @throws ElException
	 */
	public String infoConfirm()throws ElException{

		String msg = "";
		String examId = this.getRequest().getParameter("examId");
		
		if("".equals(examId) || examId == null){
			msg = "获取试卷错误，试卷ID为null";
			this.getRequest().setAttribute("msg", msg);
			return "index";
		}
		
		//获取学员信息页面
		int userId = getSessionIntValue(ElConstants.SESSION_USERID);
		ELUser user = userDao.getUserById(userId);
		
		ExamPaper examPaper = examPaperDao.getExamPaperById(Integer.parseInt(examId));
		
		this.getRequest().setAttribute("examId", examId);
		this.getRequest().setAttribute("user", user);
		this.getRequest().setAttribute("examPaper", examPaper);
		return "information";
	}
	
	/**
	 * 设备调试确认页面
	 * @return
	 * @throws ElException
	 */
	public String deviceDebu()throws ElException{
		String examId = this.getRequest().getParameter("examId");
		String msg = "";
		if("".equals(examId) || examId == null){
			msg = "获取试卷错误，试卷ID为null";
			this.getRequest().setAttribute("msg", msg);
			return "index";
		}
		ExamPaper examPaper = examPaperDao.getExamPaperById(Integer.parseInt(examId));
		this.getRequest().setAttribute("examPaper", examPaper);
		this.getRequest().setAttribute("examId", examId);
		return "deviceDebu";
		
	}
	
	
	/**
	 * 获取试卷信息生成
	 * @return
	 * @throws ElException
	 */
	public String paper()throws ElException{
		
		String examId = this.getRequest().getParameter("examId");
		
		if(examId == null || examId.equals("")){
			System.out.println("试卷为null");
		}
		ExamPaper examPaper = examPaperDao.getExamPaperById(Integer.parseInt(examId));
		this.getRequest().setAttribute("examId", examId);
		this.getRequest().setAttribute("dataTime", examPaper.getDuring());
		//获取试卷缓存
		return "paper";
	}
	
	public String ajaxAllPaperData(){
		this.ajaxHead();
		String examId = this.getRequest().getParameter("examId");
		if(examId == null || examId.equals("")){
			this.ajaxError("读取试卷错误");
			return null;
		}
		
		Object cacheData =  SystemConfOp.getCache().get("exam_all_question_"+examId);
		this.ajaxSuccess(cacheData.toString());
		return null;
	}
	
	/**
	 * 缓存获取试卷信息
	 * @return
	 * @throws ElException
	 */
	public String ajaxPaperData()throws ElException{
		this.ajaxHead();
		String examId = this.getRequest().getParameter("examId");
		String index =this.getRequest().getParameter("index");//1听力2阅读3书写
		if(examId == null || examId.equals("")){
			this.ajaxError("读取试卷错误");
			return null;
		}
		index = index == null?"1":index;
		Object cacheData =  SystemConfOp.getCache().get("paper_"+examId+"_type_"+index);
		String jsonArr = null;
		List<QuestionNum> list = new ArrayList<QuestionNum>();
		if(cacheData== null || cacheData.equals("")){
			//更新缓存
			List<Paper> paperList = new ArrayList<Paper>();
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
			List<Paper> allPaper = new ArrayList<Paper>();
			allPaper.addAll(listenMode);
			allPaper.addAll((List<Paper>)readResult.get("questionItem"));
			allPaper.addAll((List<Paper>)writeResult.get("questionItem"));
			
			System.out.println(JSONArray.fromObject(allPaper).toString());
			SystemConfOp.getCache().put("exam_all_question_"+examId, JSONArray.fromObject(allPaper).toString());//所有题数
		}
		
		jsonArr = (String) SystemConfOp.getCache().get("paper_"+examId+"_type_"+index);
	
		if(jsonArr == null || jsonArr.equals("")){
			this.ajaxError("读取试卷错误");
			return null;
		}
		
		this.ajaxSuccess(jsonArr,false);
		return null;
		
	}
	
	/**
	 * 载入试题
	 * @return
	 */
	public String loadQuestion(){
		this.ajaxHead();
		String examId = this.getRequest().getParameter("examId");
		String quesId = this.getRequest().getParameter("quesId");
		
		JSONObject json = null;
		Object cacheData =  SystemConfOp.getCache().get("exam_all_question_"+examId);
		String jsonArr = cacheData.toString();
		JSONArray arr = JSONArray.fromObject(jsonArr);
		if(quesId!=null && !quesId.equals("")){
			for (Object object : arr) {
				JSONObject js = (JSONObject)object;
				if(js.getString("id").equals(quesId)){
					json = js;
					break;
				}
			}
		}else{
			
			String index = this.getRequest().getParameter("index");
			
			if(examId == null || examId.equals("")){
				this.ajaxError("读取试卷错误");
				return null;
			}
			
			if(index == null || index.equals("")){
				this.ajaxError("读取试题错误");
				return null;
			}
			
			if(arr.size()<=Integer.parseInt(index)){
				index = "1";
			}
			
			json = (JSONObject)arr.get(Integer.parseInt(index));
		}
		
		this.ajaxSuccess(json.toString(), 0);
		return null;
	}
	
	/**
	 * 提交试卷
	 * @return
	 */
	public String examSubmit(){
		this.ajaxHead();
		String jsonData = this.getRequest().getParameter("answer");
		String examId = this.getRequest().getParameter("examId");
		
		if(examId == null || examId.equals("")){
			this.ajaxError("提交试卷失败");
			return null;
		}
		
		if(jsonData== null || jsonData.equals("")){
			this.ajaxError("提交试卷失败");
			return null;
		}

		//获取缓存试卷信息
		List<Paper> paperList = PaperModel.getPaperData(examId, examPaperDao);
		//获取试卷配置
		try {
			int userId =Integer.parseInt(getSession().getAttribute(ElConstants.SESSION_USERID)+"");
			String userName = getSession().getAttribute(ElConstants.SESSION_USERNAME).toString();
			//获取用户分数
			int userScore = PaperModel.getUserPaperScore(jsonData, paperList);
			String result = PaperModel.answerToString(jsonData);
			
			SimulationResult sr = new SimulationResult();
			Timestamp tm = new Timestamp(System.currentTimeMillis());
			sr.setCreateTime(tm);
			sr.setResult(result);
			sr.setScore(userScore+"");
			sr.setName(userName);
			sr.setUserId(userId);
			sr.setPaperId(Integer.parseInt(examId));
			sr.setStuNo(getSession().getAttribute(ElConstants.SESSION_USERID).toString());
			simulationDao.addSimulation(sr);
		} catch (ElException e) {
		}
		this.ajaxSuccess(true);
		
		return null;
	}
	
	public int getSublibs() {
		return sublibs;
	}
	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}
	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}
	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}
	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}
	public ExamPaperLib getEpl() {
		return epl;
	}
	public void setEpl(ExamPaperLib epl) {
		this.epl = epl;
	}
	public ExamPaperLib getEplTree() {
		return eplTree;
	}
	public void setEplTree(ExamPaperLib eplTree) {
		this.eplTree = eplTree;
	}

	public SimulationDao getSimulationDao() {
		return simulationDao;
	}

	public void setSimulationDao(SimulationDao simulationDao) {
		this.simulationDao = simulationDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	
	public static void main(String[] args) {
		String sql = "A 认识。 B 时候。   C 书。   D 名字。    E 电视。   F 热。例如：你  叫  什么（   D   ）？";
		//String sql = "B300块钱。";
		String str2= sql.replaceAll("\\s*", "").trim();
		String arr [] = str2.split("。");
		StringBuffer sb = new StringBuffer();
		
		for (String string : arr) {
			String res = SimulationUtil.pinyinHtml(string.trim());
			sb.append(res+"。");
			sb.append("&nbsp;&nbsp;");
		}
		System.out.println(sb.toString());
//		String html = SimulationUtil.pinyinHtml("这  是  我们  的 教室");
//		System.out.println(html);
//		
//		String sql = "<img alt='' width='171' height='113' src='/elstuffs/images/radio/image5.png' />-=SpEl=-学习";
//		String str [] = sql.split("-=SpEl=-");
//		System.out.println(str[1]);
	}
	
}
