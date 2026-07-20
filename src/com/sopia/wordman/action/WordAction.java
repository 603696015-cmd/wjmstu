package com.sopia.wordman.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ExcelUtil;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.entities.MyLogin;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.wordman.dao.LearnPlanDao;
import com.sopia.wordman.dao.WordDao;
import com.sopia.wordman.entities.LearnPlan;
import com.sopia.wordman.entities.Vocabulary;
import com.sopia.wordman.entities.Word;

public class WordAction extends BaseAction{
	private Word wordsTree;
	private WordDao wordDao;
	private Word word;
	private int course_sourse;
	private Course course;
	private CourseDao courseDao; 
	private List<Vocabulary> vocabularys;
	private Vocabulary vocabulary;
	protected int count;
	private StuffLib stuff;
	private LearnPlanDao learnPlanDao;
	private LearnPlan learnplan;
	private List<LearnPlan> learnplans;
	private List<MyLogin> mylogins;
	private int target;
	private List<Word> wordTree;
	private List<Course> courses;
	private List<Word> words;
	
	//wjm0221词汇导入
	private File st;
	private String stFileName;
	private String stfilename;
	
	public String getStfilename() {
		return stfilename;
	}

	public void setStfilename(String stfilename) {
		this.stfilename = stfilename;
	}

	public File getSt() {
		return st;
	}

	public void setSt(File st) {
		this.st = st;
	}

	public String getStFileName() {
		return stFileName;
	}

	public void setStFileName(String stFileName) {
		this.stFileName = stFileName;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Word> getWordTree() {
		return wordTree;
	}

	public void setWordTree(List<Word> wordTree) {
		this.wordTree = wordTree;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public List<MyLogin> getMylogins() {
		return mylogins;
	}

	public void setMylogins(List<MyLogin> mylogins) {
		this.mylogins = mylogins;
	}

	public List<LearnPlan> getLearnplans() {
		return learnplans;
	}

	public void setLearnplans(List<LearnPlan> learnplans) {
		this.learnplans = learnplans;
	}

	public LearnPlan getLearnplan() {
		return learnplan;
	}

	public void setLearnplan(LearnPlan learnplan) {
		this.learnplan = learnplan;
	}

	public LearnPlanDao getLearnPlanDao() {
		return learnPlanDao;
	}

	public void setLearnPlanDao(LearnPlanDao learnPlanDao) {
		this.learnPlanDao = learnPlanDao;
	}
	public StuffLib getStuff() {
		return stuff;
	}

	public void setStuff(StuffLib stuff) {
		this.stuff = stuff;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	public List<Vocabulary> getVocabularys() {
		return vocabularys;
	}

	public void setVocabularys(List<Vocabulary> vocabularys) {
		this.vocabularys = vocabularys;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public int getCourse_sourse() {
		return course_sourse;
	}

	public void setCourse_sourse(int course_sourse) {
		this.course_sourse = course_sourse;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Word getWordsTree() {
		return wordsTree;
	}

	public void setWordsTree(Word wordsTree) {
		this.wordsTree = wordsTree;
	}

	public WordDao getWordDao() {
		return wordDao;
	}

	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	private int getIntValue(String value) {
		if (value == null)
			return 0;
		if (("").equals(value.trim()))
			return 0;
		int valuei = 0;
		try {
			valuei = new Integer(value).intValue();
		} catch (Exception e) {
			//logger.error("数字转换错误",e);
		}
		return valuei;
	}
	/**
	 * 词汇类别列表
	 * @return
	 * @throws ElException
	 */
	public String word_list()throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			wordsTree = wordDao.getWordsTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL,true);
		}else{
			wordsTree = wordDao.getWordsTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		return "word_list";
	}

	public String wordslib_addInit()throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			wordsTree = wordDao.getWordsTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL,true);
		}else{
			wordsTree = wordDao.getWordsTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if(wordsTree.getChild().size()==0&&getSessionIntValue(ElConstants.SESSION_ROLE) != 1){
			setElmessage("没有可操作的词汇库");
			return "error";
		}
		
		return "wordslib_addInit";
	}
	
	public String wordslib()throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			wordsTree = wordDao.getWordsTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL,true);
		}else{
			wordsTree = wordDao.getWordsTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if(wordsTree.getChild().size()==0&&getSessionIntValue(ElConstants.SESSION_ROLE) != 1){
			setElmessage("没有可操作的词汇库");
			return "error";
		}
		
		return "wordslib";
	}
	
	public String wordslib_add() throws ElException{
		wordDao.addWord(word);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("words");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_WORD,
				ElLoggerConstants.LOG_TYPE_ADD, word.getName(),
				ElLoggerConstants.LOG_RES_SUCC, word.getId());
		return "wordslib_add_success";
	}
	
	public String word_view()throws ElException{
		
		if(word==null||word.getId()<=0){	
			setElmessage("您需要查看的词汇库不存在,请重新选择！");
			return "error";
		}
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			wordsTree = wordDao.getWordsTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL,true);
		}else{
			wordsTree = wordDao.getWordsTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		word = wordDao.getWordsById(word.getId());
		course = courseDao.getCourseById(word.getCourseid());
		word.setOpusers(wordDao.getOpUsers("op", word.getId()));
		return "word_view";
	}
	
	public String wordslib_deleteInit() throws ElException{
		if(word.getId()==1){
			setElmessage("不能删除根类别");
			return "error";
		}
		word = wordDao.getWordsById(word.getId());
		return "wordslib_delete";
	}
	
	public String wordslib_delete() throws ElException{
		if(word.getId()==1){
			setElmessage("不能删除根类别");
			return "error";
		}
		word = wordDao.getWordsById(word.getId());
		if (course_sourse == 0) {
			// 并入上级
			wordDao.updateWordlibParentid(word.getId(), word.getParent()
					.getId());
		//	wordDao.updateExamroomParentid(word.getId(), word
		//			.getParent().getId());
		} else {
			// 一起删除
//			eroomDao.deleteElibAndSub(eroomLib.getId());
			//改成假删除
			wordDao.deleteWordlibAndSubNot(word.getId());
		}
		wordDao.deletewordsLibNot(word.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("words");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_WORD,
				ElLoggerConstants.LOG_TYPE_DELETE, word.getName(),
				ElLoggerConstants.LOG_RES_SUCC, word.getId());
		return "wordslib_delete_success";
	}
	
	public String wordlib_alterInit() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			wordsTree = wordDao.getWordsTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL,true);
		}else{
			wordsTree = wordDao.getWordsTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		word = wordDao.getWordsById(word.getId());
		word.setOpusers(wordDao.getOpUsers("op", word.getId()));
		course = courseDao.getCourseById(word.getCourseid());
		return "wordlib_alter";
	}
	
	public String wordlib_alter() throws ElException{
		if (word.getId() == 1) {
			word.setParent(new EroomLib(0));
		}
		if (word.getParent() == null) {// 如果是省厅管理员，编辑1级子节点的时候会出现null
			word.setParent(new ElNode(1));
		}
		wordDao.alterWordLib(word);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("words");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_WORD,
				ElLoggerConstants.LOG_TYPE_ALTER, word.getName(),
				ElLoggerConstants.LOG_RES_SUCC, word.getId());
		return "wordlib_alter_success";
	}

	/**
	 * 词汇列表
	 * @return
	 * @throws ElException
	 */
	public String vocabulary_listInit() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_USERID)==1){
			word = wordDao.wdLibTree(0,getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}else{
			word = wordDao.wdLibTree("op", getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
	//	vocabulary.setWordid(-1);
		vocabularys = wordDao.getVocList(word,vocabulary,getPageNow(),getPageSize());
		count = wordDao.getWordSize(word,vocabulary);
		if(target==4){
			wordTree = wordDao.getWordsTree();
			return "vocabulary_search";
		}
		return "vocabulary_listInit";
	}
	
	public String mess_getWordsLibInfoJson() throws ElException {
		word = wordDao.getWordsById(word.getId());
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"word\":{\"id\":\"" + word.getId()
					+ "\",\"name\":\"" + word.getName() + "\"}}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
		//	logger.error("ajax 获取人员信息错误",e);
		}
		return null;
	}
	
	public String mess_getVocabularyJson() throws ElException{
		words = wordDao.getWordsTreeByParentid(vocabulary.getWordid());
		
	//	courses = wordDao.getVocByWordId(word);
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.print("[");
			if(words!=null&&words.size()>1){
				int size = words.size();
				for(int i=0;i<words.size()-1;i++){
					String json = "{\"word\":{\"id\":\"" + words.get(i).getId()+"\",\"name\":\"" + words.get(i).getName() + "\"}}";
					json+=",";
				//	json+="{\"word\":{\"id\":\"" + words.get(i+1).getId()+"\",\"name\":\"" + words.get(i+1).getName() + "\"}}";
				//	localPrintWriter.println("{\"vocabulary\":{\"id\":\"" + vocabularys.get(i).getId()
				//			+ "\",\"name\":\"" + vocabularys.get(i).getName() + "\"}}");
				//	i++;
					localPrintWriter.println(json);
				}
				localPrintWriter.print("{\"word\":{\"id\":\"" + words.get(size-1).getId()+"\",\"name\":\"" + words.get(size-1).getName() + "\"}}");
				localPrintWriter.print("]");
			}else{
				localPrintWriter.print("{\"word\":{\"id\":\"" + words.get(0).getId()
									+ "\",\"name\":\"" + words.get(0).getName() + "\"}}");
				localPrintWriter.print("]");
			}
			
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
		//	logger.error("ajax 获取人员信息错误",e);
		}
		return null;
	}

	public String vocabulary_addInit() throws ElException{
		
		return "vocabulary_addInit";
	}
	/*
	 * 添加词汇
	 */
	public String vocabulary_add() throws ElException{
		vocabulary.setAdduserid(getSessionIntValue(ElConstants.SESSION_USERID));
		vocabulary.setStatus(0);//未审核
		int id = wordDao.addVocabulary(vocabulary);
		vocabulary.setId(id);
		String[] liju = vocabulary.getWenziliju().split(",");
		String[] dizhi = vocabulary.getLijudizhi().split(",");
		for(int i=0;i<liju.length;i++){
			vocabulary.setWenziliju(liju[i]);
			vocabulary.setLijudizhi(dizhi[i]);
			wordDao.addVocabularySen(vocabulary);
		}
		
		return "vocabulary_listInit";
	}
	/**
	 * 词汇审核列表
	 */
	public String vocabulary_shInit() throws ElException{
	//	vocabulary.setWordid(-1);
		if(getSessionIntValue(ElConstants.SESSION_USERID)==1){
			word = wordDao.wdLibTree(0,getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}else{
			word = wordDao.wdLibTree("op", getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		vocabularys = wordDao.getVocList(word,vocabulary,getPageNow(),getPageSize());
		count = wordDao.getWordSize(word,vocabulary);
		return "vocabulary_shInit";
	}
	
	public String vocabulary_alterInit() throws ElException{
	//	target = this.getTarget();
		vocabulary = wordDao.getVocById(vocabulary.getId());
		vocabulary.setWord(wordDao.getWordsById(vocabulary.getWordid()));
		vocabulary.setStuffs(wordDao.liststuff(vocabulary.getId()));
		return "vocabulary_alterInit";
	}
	
	public String vocabulary_delete()throws ElException{
		wordDao.delVocById(vocabulary.getId());
		if(target==1){
			return "vocabulary_listInit";
		}
		if(target==3){
			return "myvocabulary";
		}else{
			return "vocabulary_search";
		}
		
	}
	/*
	 * 词汇审核
	 */
	public String alterVocaSta() throws ElException{
		wordDao.alterVocSta(vocabulary);
		return "vocabulary_shInit";
	}
	public String vocabulary_view() throws ElException{
		vocabulary = wordDao.getVocById(vocabulary.getId());
		vocabulary.setStuffs(wordDao.liststuff(vocabulary.getId()));
		return "vocabulary_view";
	}
	/**
	 * 答题页词汇查看
	 * @return
	 * @throws ElException
	 */
	public String vocabulary_view2() throws ElException{
		vocabulary = wordDao.getVocById(vocabulary.getId());
		vocabulary.setStuffs(wordDao.liststuff(vocabulary.getId()));
		return "vocabulary_view2";
	}
	/**
	 * 词汇例句删除
	 * @return
	 * @throws ElException
	 */
	public String vocabularyStuff_delete() throws ElException{
		wordDao.deleteVocStuff(stuff.getId());
		return "vocabulary_alterInit";
	}
	public String vocabulary_alter() throws ElException{
		vocabulary.setAlteruserid(getSessionIntValue(ElConstants.SESSION_USERID));
		wordDao.alterVocabulary(vocabulary);
		String stid[] = getRequest().getParameterValues("vocabulary.stuffs.id");
		String staddr[] = getRequest().getParameterValues("vocabulary.stuffs.description");
		String sttitle[] = getRequest().getParameterValues("vocabulary.stuffs.title");
		if(stid!=null){
			for(int i=0;i<stid.length;i++){
				vocabulary.setStuff(new StuffLib(getIntValue(stid[i]),sttitle[i]));
				vocabulary.getStuff().setDescription(staddr[i]);
			//	vocabulary.setWenziliju(sttitle[i]);
			//	vocabulary.setLijudizhi(staddr[i]);
				wordDao.alterVocStuff(vocabulary);
			}
			
		}
		if(vocabulary.getWenziliju()!=null){
			String[] liju = vocabulary.getWenziliju().split(",");
			String[] dizhi = vocabulary.getLijudizhi().split(",");
			for(int i=0;i<liju.length;i++){
				vocabulary.setWenziliju(liju[i]);
				vocabulary.setLijudizhi(dizhi[i]);
				wordDao.addVocabularySen(vocabulary);
			}
		}
		if(target==2){
			return "vocabulary_shInit";
		}
		if(target==1){
			return "vocabulary_listInit";
		}
		if(target==3){
			return "myvocabulary";
		}else{
			return "vocabulary_search";
		}
		
	}
	/**
	 * 词汇查询
	 * @return
	 * @throws ElException
	 */
	public String vocabulary_search() throws ElException{
		wordTree = wordDao.getWordsTreeByParentid(1);
		if(vocabulary == null){
			vocabulary = new Vocabulary();
			vocabulary.setStatus(1);
		}else{
			vocabulary.setStatus(1);
		}
	//	vocabulary.setWordid(-1);//wordid为-1时，查询出所有词汇
		if(vocabulary.getWordid()>0){
			vocabulary.setWordid(vocabulary.getWordid());
		}
		if(vocabulary.getChildid()>0){
			vocabulary.setWordid(vocabulary.getChildid());
		}
		if(course!=null&&course.getId()>0){
			word = wordDao.getWordsByCourseId(course.getId()); //单元和词汇库是一对一关系
			vocabulary.setWordid(word.getId());
		}
		vocabularys = wordDao.getVocList2(vocabulary,getPageNow(),getPageSize());
		count = wordDao.getWordSize2(vocabulary);
		return "vocabulary_search";
	}
	/*
	 * 答题页词汇查询
	 */
	public String vocabulary_search2() throws ElException{
		wordTree = wordDao.getWordsTreeByParentid(1);
		if(vocabulary == null){
			vocabulary = new Vocabulary();
			vocabulary.setStatus(1);
		}else{
			vocabulary.setStatus(1);
		}
	//	vocabulary.setWordid(-1);//wordid为-1时，查询出所有词汇
		if(vocabulary.getWordid()>0){
			vocabulary.setWordid(vocabulary.getWordid());
		}
		if(vocabulary.getChildid()>0){
			vocabulary.setWordid(vocabulary.getChildid());
		}
		if(course!=null&&course.getId()>0){
			word = wordDao.getWordsByCourseId(course.getId()); //单元和词汇库是一对一关系
			vocabulary.setWordid(word.getId());
		}
		vocabularys = wordDao.getVocList2(vocabulary,getPageNow(),getPageSize());
		count = wordDao.getWordSize2(vocabulary);
		return "vocabulary_search2";
	}
	
	/**
	 * 我添加的词汇
	 */
	public String myvocabulary()throws ElException{
		int adduserid = getSessionIntValue(ElConstants.SESSION_USERID);
		vocabularys = wordDao.getVocListByUserid(adduserid, getPageNow(),getPageSize());
		count = wordDao.getVocListByUseridSize(adduserid);
		return "myvocabulary";
	}
	
	/**
	 * 学习计划
	 */
	public String plan_addInit()throws ElException{
		return "plan_addInit";
	}
	
	/**
	 * 添加学习计划
	 */
	public String plan_add() throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		learnplan.setUserid(userid);
		learnPlanDao.addLearnPlan(learnplan);
		return null;
	}
	
	public String plan_alterlist()throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		learnplans = learnPlanDao.getallPlan(getPageNow(),getPageSize(),userid);
		count = learnPlanDao.getCount(userid);
		return "plan_alterlist";
	}
	public String plan_alterInit()throws ElException{
		learnplan = learnPlanDao.getPlanById(learnplan.getId());
		return "plan_alterInit";
	}
	/*
	 * 修改学习计划
	 */
	public String plan_alter() throws ElException{
		learnPlanDao.alterPlan(learnplan);
		return "plan_alterlist";
	}
	
	public String plan_viewlist()throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		learnplans = learnPlanDao.getallPlanXS(getPageNow(),getPageSize(),userid);
		count = learnPlanDao.getCount(userid);
		return "plan_viewlist";
	}
	
	public String plan_view()throws ElException{
	//	learnplan = learnPlanDao.getPlanById(learnplan.getId());
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		learnplan.setUserid(userid);
		mylogins = learnPlanDao.getMyloginInfo(getPageNow(),getPageSize(),learnplan);
		count = learnPlanDao.getLoginInfoCount(learnplan);
		return "plan_view";
	}
	
	//----------wjm0221修改
	/*
	 * 词汇导入
	 */
	public String word_importInit() throws ElException {
		return "word_import";
	}
	
	/**词汇导入校验
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String word_importCheck() throws ElException, Exception {
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).equals("xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "word_importInit";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "word_importInit";
			} else {
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String isOk = ExcelUtil.writeVoc(st, 
						userid,false);
				//备份文件
				String filename = "vocs_"+userid+ "_"+System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/"
						, filename);
				stfilename = filename+".xls";
//				if(!"".equals(isOk)){
					setElmessage(isOk);
//				}
			}
		} else {
			setElmessage("请输入上传文件");
			return "word_importInit";
		}
		return "word_importCheck";
	}
	
	/**
	 * Description: 指定题库目录的试题导入
	* @Version1.0 2012-7-15 上午11:42:32 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String word_import() throws ElException, Exception {
		int wordid=0;
//			wordDao.getwdLibTreeId(vocabulary.getName());
		if(stfilename!=null){
			File xls = new File(ServletActionContext.getServletContext().getRealPath("/")+"/importtemp/"+stfilename);
			if(xls.exists()){
				String isOk = "";
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
					isOk  = ExcelUtil.writeVoc(xls, 
							userid,true);
//				((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).updatetlrid("QUESTION_LIB");
					if(!"".equals(isOk)){
						if("false".equals(isOk)){
							setElmessage("批量导入失败，请检查词汇格式!");
						}else{
							ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
									ElLoggerConstants.LOG_MOD_QUESTION,
									ElLoggerConstants.LOG_TYPE_IMPORT,isOk,
									ElLoggerConstants.LOG_RES_SUCC,wordid);
	//						return "question_importByqlib";
							setElmessage(isOk);
						}
					}else{
						setElmessage("批量导入全部成功!");
					}
//				}
				//xls.delete();
				//xls.deleteOnExit();
				return "voc_import_result";
			} else {
				setElmessage("请输入上传文件");
				return "word_importInit";
			}
//			}
//			((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("QUESTION_LIB");
		} else {
			setElmessage("请输入上传文件");
			return "word_importInit";
		}
		//return "question_import_success";
		//return "question_list";
	}
}
