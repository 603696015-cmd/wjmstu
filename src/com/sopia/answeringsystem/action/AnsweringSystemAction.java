package com.sopia.answeringsystem.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.answeringsystem.QuesAnswerConstants;
import com.sopia.answeringsystem.QuesAnswerUtil;
import com.sopia.answeringsystem.dao.AnsweringSystemDao;
import com.sopia.answeringsystem.dao.AnsweringTypeDao;
import com.sopia.answeringsystem.entities.Answer;
import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.answeringsystem.entities.Ques;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.entities.News;
/**
 * 问答系统
 * @author Administrator
 *
 */
public class AnsweringSystemAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(AnsweringSystemAction.class);
	
	private AnsweringSystemDao answeringSystemDao;
	private AnsweringTypeDao answeringTypeDao;
	private NewsDao newsDao;
	private RoleDao roleDao;
	private Ques ques;
	private AnsweringType answeringType;
	private AnsweringType ansType;
	private Department depTree;
	private Department department;
	private int sub_department;
	private List<ELUser> elUsers;
	private ELUser elUser;
	private List<ElRole> roles;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private List<Ques> queses ;
	private Answer answer;
	private List<Answer> answers;
	private int count ;
	
	private String returnPage;
	
	private Map<String,Integer> question_count;
	
	private List<AnsweringType> answeringTypes;
	private Map<String,List> listMap;
	private String status;
	private int  m;
	
	private Station stTree;
	private Station station;
	
	private int notView;//判断浏览问题的时候是否需要数据库中值加1
	private String ids;
	//////
	//actions
	//发布问题初始化
	public String releaseQuestionInit() throws ElException{
		question_count = new HashMap<String,Integer>();
		question_count.put("quesion_all_count", answeringSystemDao.getQuestionCount(-1));//问题总数
		question_count.put("quesion_has_finish", answeringSystemDao.getQuestionCount(3));//已解决
		question_count.put("question_need_finish", answeringSystemDao.getQuestionCount(-3));//待解决
		return "releaseQuestionInit";
	}
	//发布问题
	public String releaseQuestion() throws ElException{
		ques.setFabuUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		ques.setViewCount(0);
		if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_RELEASE_QUESTION_NEED_SH)){
			ques.setStatus(2);//已发布
		}else{
			ques.setStatus(0);//已创建
		}
		ques.setStatusTow(-1);//普通
		answeringSystemDao.addQues(ques) ;
		return "releaseQuestion_success";
	}
	//选择所属类别初始化
	public String selectAnsweringTypeTreeInit() throws ElException{
		answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		return "selectAnsweringTypeTreeInit";
	}
	//选择回答人初始化
	public String answeringUsersInit() throws ElException{
		sub_department = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (department == null || department.getId() <= 0) {
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		
		elUsers = userDao.listUsers(department,station, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize(department, station,sub_department, elUser);
		elUser = elUser == null ? new ELUser() : elUser;
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		roles = roleDao.listRoles();
		return "answeringUsersInit";
	}
	//我的问题
	public String myQues() throws ElException, UnsupportedEncodingException{
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		queses = answeringSystemDao.listMyQues(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(),getPageSize(),ques);
		count = answeringSystemDao.listMyQuesSize(getSessionIntValue(ElConstants.SESSION_USERID),ques);
		
		if(queses!=null){
			for(int i=0;i<queses.size();i++){
				queses.get(i).setAnswerCount(answeringSystemDao.listAnswersSizeByQuesid(queses.get(i).getId()));
			}
		}
		return "myQues";
	}
	//删除问题
	public String deleteQues() throws ElException, UnsupportedEncodingException{
		answeringSystemDao.deleteQues(ques.getId());
		setElmessage(URLEncoder.encode(URLEncoder.encode("删除成功!!!", "UTF-8"), "UTF-8"));
		return "deleteQues_success";
	}
	//修改问题初始化
	public String alterQuesInit() throws ElException{
		ques = answeringSystemDao.queryQuesById(ques.getId());
		if(ques!=null&&ques.getAnswerUserids()!=null&&!ques.getAnswerUserids().equals("")){
			ques.setAnswerUsers(answeringSystemDao.listAnswerUser(ques.getAnswerUserids()));
		}
		return "alterQuesInit";
	}
	//修改问题
	public String alterQues() throws ElException{
		answeringSystemDao.alterQuesById(ques);
		return "alterQues_success";
	}
	//查看问题
	public String viewQues() throws ElException{
		ques = answeringSystemDao.queryQuesById(ques.getId());
		if(ques!=null&&ques.getAnswerUserids()!=null&&!ques.getAnswerUserids().equals("")){
			ques.setAnswerUsers(answeringSystemDao.listAnswerUser(ques.getAnswerUserids()));
		}
		ques.setViewCount(ques.getViewCount()+1);
		answeringSystemDao.addViewCountById(ques);
		return "viewQues";
	}
	//问题审核
	public String verifyQueslist() throws ElException{
		//是否需要权限判断
		answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		queses = answeringSystemDao.listQuesByAnsweringType(answeringType,getPageNow(),getPageSize(),ques);
		count = answeringSystemDao.listQuesSizeByAnsweringType(answeringType,ques);
		if(queses!=null){
			for(int i=0;i<queses.size();i++){
				queses.get(i).setAnswerCount(answeringSystemDao.listAnswersSizeByQuesid(queses.get(i).getId()));
			}
		}
		return "verifyQueslist";
	}
	//问题推荐
	public String questionRecommend() throws ElException{
	 	//是否需要权限判断
		answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		queses = answeringSystemDao.listQuesByAnsweringType(answeringType,getPageNow(),getPageSize(),ques);
		count = answeringSystemDao.listQuesSizeByAnsweringType(answeringType,ques);
		if(queses!=null){
			for(int i=0;i<queses.size();i++){
				queses.get(i).setAnswerCount(answeringSystemDao.listAnswersSizeByQuesid(queses.get(i).getId()));
			}
		}
		return "questionRecommend";
	}
	//设置问题的热度
	public String setStatusTow() throws ElException{
		String[] ids_array = null;
		if(ids!=null&&!ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null&&ids_array.length>0){
				for(int i = 0;i<ids_array.length;i++){
					answeringSystemDao.setStatusTow(Integer.parseInt(ids_array[i]),ques.getStatusTow());
				}
			}
		}
		
		return "setStatusTow_success";
	}
	//审核通过
	public String verifyQues() throws ElException{
//		if(!SystemConfOp.getBooleanValue(ElConstants.SYSTEM_RELEASE_QUESTION_NEED_SH)){
//			this.setElmessage("系统设置为发布问题不需要审核");
//			return "error";
//		}
		answeringSystemDao.alterQuesStatus(ques.getId(),2);
		return "verifyQues_success";
	}
	//审核不通过
	public String noverifyQues() throws ElException{
//		if(!SystemConfOp.getBooleanValue(ElConstants.SYSTEM_RELEASE_QUESTION_NEED_SH)){
//			this.setElmessage("系统设置为发布问题不需要审核");
//			return "error";
//		}
		answeringSystemDao.alterQuesStatus(ques.getId(),1);
		return "noverifyQues_success";
	}
	//我的回复
	public String myAnswerList() throws ElException{
		answers = answeringSystemDao.listMyAnswers(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(),getPageSize());
		count = answeringSystemDao.listMyAnswersSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "myAnswerList";
	}
	//回复审核
	public String verifyAnswerList() throws ElException{
		answers = answeringSystemDao.listMyAnswers(-1,getPageNow(),getPageSize());
		count = answeringSystemDao.listMyAnswersSize(-1);
		return "verifyAnswerList";
	}
	//回复审核通过
	public String verifyAnswer() throws ElException{
		answeringSystemDao.alterAnswerStatus(answer.getId(),1);
		return "verifyAnswer_success";
	}
	//回复审核不通过
	public String noverifyAnswer() throws ElException{
		answeringSystemDao.alterAnswerStatus(answer.getId(),0);
		return "noverifyAnswer_success";
	}
	//问答首页
	public String queses_answers_Index() throws ElException{
		AnsweringType a = null;
		answeringTypes = answeringTypeDao.listAllAnsweringTypes();
		for(int i=0;i<answeringTypes.size();i++){
			a = answeringTypes.get(i);
			if(a.getParentid()==1){
				a.setHasTotalCount(answeringTypeDao.getCountById(a.getLid(),a.getRid()));
			}
		}
		question_count = new HashMap<String,Integer>();
		question_count.put("quesion_all_count", answeringSystemDao.getQuestionCount(-1));//问题总数
		question_count.put("quesion_has_finish", answeringSystemDao.getQuestionCount(3));//已解决
		question_count.put("question_need_finish", answeringSystemDao.getQuestionCount(-3));//待解决
		
		listMap = new HashMap<String,List>();
		List<Ques> newestQueses = answeringSystemDao.listQuesesByDate();
		List<Answer> newestAnswers = answeringSystemDao.listAnswersByDate();
		List<Ques> queses = answeringSystemDao.listQuesesByStatusTow(QuesAnswerConstants.TUIJIAN);
		List<News> newses = newsDao.listNews();
		listMap.put("newestQueses", newestQueses);//最新问题
		listMap.put("newestAnswers", newestAnswers);//最新回答
		listMap.put("queses", queses);//推荐问题
		listMap.put("newses", newses);//新闻公告//最新开通
		
		return "queses_answers_Index";
	}
	//问题预览
	public String ques_index_view() throws ElException{
		AnsweringType a = null;
		answeringTypes = answeringTypeDao.listAllAnsweringTypes();
		for(int i=0;i<answeringTypes.size();i++){
			a = answeringTypes.get(i);
			if(a.getParentid()==1){
				a.setHasTotalCount(answeringTypeDao.getCountById(a.getLid(),a.getRid()));
			}
		}
		question_count = new HashMap<String,Integer>();
		question_count.put("quesion_all_count", answeringSystemDao.getQuestionCount(-1));//问题总数
		question_count.put("quesion_has_finish", answeringSystemDao.getQuestionCount(3));//已解决
		question_count.put("question_need_finish", answeringSystemDao.getQuestionCount(-3));//待解决
		ques = answeringSystemDao.queryQuesById(ques.getId());
		ques.setAnswers(answeringSystemDao.listAnswersByQuesid(ques.getId(),getPageNow(),getPageSize()));
		count = answeringSystemDao.listAnswersSizeByQuesid(ques.getId());
		
		if(notView == 0){
			//查看数量加1
			ques.setViewCount(ques.getViewCount()+1);
			answeringSystemDao.addViewCountById(ques);
		}
		return "ques_index_view";
	}
	//提交回答
	public String addAnswer() throws ElException{
		//判断指定回答人中是否包含当前用户
		ques = answeringSystemDao.queryQuesById(answer.getQuestionId());
		if(ques!=null&&ques.getAnswerUserids()!=null&&!ques.getAnswerUserids().equals("")){
			String[] ary = ques.getAnswerUserids().split(",");
			if(ary!=null&&ary.length>0){
				if(!QuesAnswerUtil.contains(ary, getSessionIntValue(ElConstants.SESSION_USERID))){
					this.setElmessage("对不起,您不是指定回答人,无法回答!!!");
					return "error";
				}
			}
		}
		answer.setAnswerUser(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		//提交是否需要审核
		if(!SystemConfOp.getBooleanValue(ElConstants.SYSTEM_ANSWER_QUESTION_NEED_SH)){
			answer.setStatus(0);
		}else{
			answer.setStatus(1);
		}
		answeringSystemDao.addAnswer(answer);
		return "addAnswer_success";
	}
	//根据问答树查找问题列表页
	public String ques_index() throws ElException{
		AnsweringType a = null;
		answeringTypes = answeringTypeDao.listAllAnsweringTypes();
		for(int i=0;i<answeringTypes.size();i++){
			a = answeringTypes.get(i);
			if(a.getParentid()==1){
				a.setHasTotalCount(answeringTypeDao.getCountById(a.getLid(),a.getRid()));
			}
		}
		question_count = new HashMap<String,Integer>();
		question_count.put("quesion_all_count", answeringSystemDao.getQuestionCount(-1));//问题总数
		question_count.put("quesion_has_finish", answeringSystemDao.getQuestionCount(3));//已解决
		question_count.put("question_need_finish", answeringSystemDao.getQuestionCount(-3));//待解决
		
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int sub = 0;
		if(ansType!=null&&ansType.getId()>0){
			sub = 1;
			answeringType = answeringTypeDao.getAnsweringTypeTreeById(ansType.getId());
		}
		if(answeringType!=null){
			if(answeringType.getId() == 1){//根节点
				ansType = answeringType;
			}else {
				if(answeringType.getParentid()!=1){//三级节点
					ansType = answeringTypeDao.getAnsweringTypeTreeById(answeringType.getParentid());
				}else{//二级节点
					ansType = answeringType;
				}
			}
			try {
				ansType.setChild(answeringTypeDao.listAnsweringTypeTreeChildsByPId(ansType.getId()));
				AnsweringType aa = null;
				if(ansType.getChild()!=null&&ansType.getChild().size()>0){
					for(int i=0;i<ansType.getChild().size();i++){
						aa = ansType.getChild().get(i);
						aa.setHasTotalCount(answeringTypeDao.getCountById(aa.getLid(),aa.getRid()));
					}
				}
				ansType.setHasTotalCount(answeringTypeDao.getCountById(ansType.getLid(),ansType.getRid()));
//				for(int i=0;i<answeringTypes.size();i++){
//					if(answeringTypes.get(i).getId() == ansType.getId()){
//						ansType.setHasTotalCount(answeringTypes.get(i).getHasTotalCount());
//						break;
//					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(status==null||status.equals("")){
			status = QuesAnswerConstants.ALLSTATUS;
		}
		queses = answeringSystemDao.listQuestionByAnsweringType(answeringType,getPageNow(),getPageSize(),status);
		count = answeringSystemDao.listQuestionSizeByAnsweringType(answeringType,status);
		if(queses!=null&&queses.size()>0){
			for(int i=0;i<queses.size();i++){
				queses.get(i).setAnswerCount(answeringSystemDao.listAnswersSizeByQuesid(queses.get(i).getId()));
			}
		}
		m = m == 0?1:m;//控制样式的
		return "ques_index";
	}
	
	//////
	//gets、sets
	public AnsweringSystemDao getAnsweringSystemDao() {
		return answeringSystemDao;
	}

	public void setAnsweringSystemDao(AnsweringSystemDao answeringSystemDao) {
		this.answeringSystemDao = answeringSystemDao;
	}

	public Ques getQues() {
		return ques;
	}

	public void setQues(Ques ques) {
		this.ques = ques;
	}
	public AnsweringTypeDao getAnsweringTypeDao() {
		return answeringTypeDao;
	}
	public void setAnsweringTypeDao(AnsweringTypeDao answeringTypeDao) {
		this.answeringTypeDao = answeringTypeDao;
	}
	public AnsweringType getAnsweringType() {
		return answeringType;
	}
	public void setAnsweringType(AnsweringType answeringType) {
		this.answeringType = answeringType;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public Department getDepTree() {
		return depTree;
	}
	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getSub_department() {
		return sub_department;
	}
	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public List<ElRole> getRoles() {
		return roles;
	}
	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}
	public List<BaseDatat> getJingzhongs() {
		return jingzhongs;
	}
	public void setJingzhongs(List<BaseDatat> jingzhongs) {
		this.jingzhongs = jingzhongs;
	}
	public List<BaseDatat> getZhiwus() {
		return zhiwus;
	}
	public void setZhiwus(List<BaseDatat> zhiwus) {
		this.zhiwus = zhiwus;
	}
	public List<BaseDatat> getZhijis() {
		return zhijis;
	}
	public void setZhijis(List<BaseDatat> zhijis) {
		this.zhijis = zhijis;
	}
	public List<BaseDatat> getGangweis() {
		return gangweis;
	}
	public void setGangweis(List<BaseDatat> gangweis) {
		this.gangweis = gangweis;
	}
	public List<BaseDatat> getDishis() {
		return dishis;
	}
	public void setDishis(List<BaseDatat> dishis) {
		this.dishis = dishis;
	}
	public List<Ques> getQueses() {
		return queses;
	}
	public void setQueses(List<Ques> queses) {
		this.queses = queses;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getReturnPage() {
		return returnPage;
	}
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public Map<String, Integer> getQuestion_count() {
		return question_count;
	}
	public void setQuestion_count(Map<String, Integer> question_count) {
		this.question_count = question_count;
	}
	public List<AnsweringType> getAnsweringTypes() {
		return answeringTypes;
	}
	public void setAnsweringTypes(List<AnsweringType> answeringTypes) {
		this.answeringTypes = answeringTypes;
	}
	public Map<String, List> getListMap() {
		return listMap;
	}
	public void setListMap(Map<String, List> listMap) {
		this.listMap = listMap;
	}
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	public AnsweringType getAnsType() {
		return ansType;
	}
	public void setAnsType(AnsweringType ansType) {
		this.ansType = ansType;
	}
	public NewsDao getNewsDao() {
		return newsDao;
	}
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public Station getStTree() {
		return stTree;
	}
	public void setStTree(Station stTree) {
		this.stTree = stTree;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public int getNotView() {
		return notView;
	}
	public void setNotView(int notView) {
		this.notView = notView;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	
	
	
}
