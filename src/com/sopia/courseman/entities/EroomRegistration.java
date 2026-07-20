package com.sopia.courseman.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.impl.ClassDaoImpl;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyQuizDao;

public class EroomRegistration {

	private ExamRoom eroom;
	//private String PlanRecruitStudents; // 计划招收人数
	private int PlanRecruitStudents; // 计划招收人数
	private Timestamp RegistrationStartTime;// 报名开始时间
	private Timestamp RegistrationStopTime;// 报名截至时间
	private int StartAge;// 开始年龄
	private int StopAge;// 终止年龄
	private String sex;// 性别
	private String jingzhong;// 警种
	private String dishi;// 地市
	private String zhiwu;// 职务
	private String zhiji;// 职级 
	private String gangwei;// 岗位
	private String treeType;// 部门
	private String treeTypes;// 部门
	//private String joinNumber; // 已参加人数
	private int joinNumber; // 已参加人数
	private List<ElClass> elclass; // 培训班
	private List<ExamRoom> examRoom; // 培训班
	private String examRoomIds;//考场ids
	private String elclassIds;//培训班ids
	private String examroomName;
	private String elclassName;
	private int classScreeningWay;//培训班筛选方式（0，全部  1，通过 2，不通过）
	private int eroomScreeningWay;//考场筛选方式（0，全部  1，通过 2，不通过）
	private int isAudit;//是否需要审核
	private int applyNumber;//已报名人数
	private int isselectep;//是否自主选择试卷
	private List<ErPara> erParas;//考场条件列表增改时候调用
	private List<ErPara> erepParas;//考场试卷条件列表增改时候调用
	private List<ClassPara> classParas;//培训班条件列表增改时候调用
	private String erParasstr;//考场条件列表字符串(入库）增改时候调用
	private String erepParasstr;//考场试卷条件列表字符串(入库）增改时候调用
	private String classParasstr;//培训班条件列表字符串(入库）增改时候调用
	private String erParasMsg;//考场条件列表字符串打印前台人性化语言
	private String erepParasMsg;//考场条件列表字符串打印前台人性化语言
	private String classParasMsg;//考场条件列表字符串打印前台人性化语言
	private String myerParasMsg;//某人的考场条件列表字符串打印前台人性化语言
	private String myclassParasMsg;//某人的考场条件列表字符串打印前台人性化语言
	private String myerepParasMsg;//某人的考场条件列表字符串打印前台人性化语言
	
	/**某人是否通过考场条件，并输出人性化语言
	 * @param userid
	 * @return
	 */
	public boolean checkErpapspassed(int userid){
		boolean b = true;
		try {
			StudyQuizDao quizDao = (StudyQuizDao)SpringContextUtil.getBean("studyQuizDao");
			getErParas();
			if(erParas!=null){
				myerParasMsg="";
				int size =  erParas.size();
				for (int i = 0; i <size; i++) {
					ErPara erp = erParas.get(i);
					myerParasMsg+="【"+erp.getExamRoom().getTitle()+"】";
					if(erp!=null&&erp.getExamRoom()!=null&&erp.getExamRoom().getId()!=0){
						String x = quizDao.checkPassErooms(erp, userid);
						boolean bi="符合".equals(x);
						if(i==0)
							b =bi;
						if(i>0&&i<size){
							if("and".equals(erParas.get(i-1).getLinkTerm())){
								b = bi&&b;
							}else
								b = bi||b;
						}
						myerParasMsg+=x+"<br/>";
					}
				}
			}
		} catch (Exception e) {
			
		}
		return b;
	}
	/**某人是否通过考场试卷条件，并输出人性化语言
	 * @param userid
	 * @return
	 */
	public boolean checkEreppapspassed(int userid){
		boolean b = true;
		try {
			StudyQuizDao quizDao = (StudyQuizDao)SpringContextUtil.getBean("studyQuizDao");
			getErepParas();
			if(erepParas!=null){
				myerepParasMsg="";
				int size =  erepParas.size();
				for (int i = 0; i <size; i++) {
					ErPara erp = erepParas.get(i);
					myerepParasMsg+="【"+erp.getExamRoom().getTitle()+"【"+erp.getExamPaper().getTitle()+"】】";
					if(erp!=null&&erp.getExamRoom()!=null&&erp.getExamRoom().getId()!=0){
						String x = quizDao.checkPassEroomeps(erp, userid);
						boolean bi="符合".equals(x);
						if(i==0)
							b =bi;
						if(i>0&&i<size){
							if("and".equals(erepParas.get(i-1).getLinkTerm())){
								b = bi&&b;
							}else
								b = bi||b;
						}
						myerepParasMsg+=x+"<br/>" ;
					}
				}
			}
		} catch (Exception e) {
			
		}
		return b;
	}
	/**某人是否通过培训班条件，并输出人性化语言
	 * @param userid
	 * @return
	 */
	public boolean checkClasspapspassed(int userid){
		boolean b = true;
		try {
			StudyClassDao quizDao = (StudyClassDao)SpringContextUtil.getBean("studyClassDao");
			getClassParas();
			if(classParas!=null){
				myclassParasMsg="";
				int size =  classParas.size();
				for (int i = 0; i < size ; i++) {
					ClassPara erp = classParas.get(i);
					myclassParasMsg+="【"+erp.getElClass().getName()+  "】";
					if(erp!=null&&erp.getElClass()!=null&&erp.getElClass().getId()!=0){
						String x = quizDao.checkPassClasss(erp, userid);
						boolean bi="符合".equals(x);
						if(i==0)
							b =bi;
						if(i>0&&i<size){
							if("and".equals(classParas.get(i-1).getLinkTerm())){
								b = bi&&b;
							}else
								b = bi||b;
						}
						myclassParasMsg+=x+"<br/>" ;
					}
				}
			}
		} catch (Exception e) {
			
		}
		return b;
	}
	public String getErepParasMsg() {
		return erepParasMsg;
	}
	public String getErParasMsg() {
		return erParasMsg;
	}
	public String getMyerepParasMsg() {
		return myerepParasMsg;
	}
	public String getMyerParasMsg() {
		return myerParasMsg;
	}
	
	/**
	 * 将列表转成字符串
	 */
	public void toClassParamsstr(){
		StringBuffer sb = new StringBuffer();
		if(classParas!=null){
			for (int i = 0; i < classParas.size(); i++) {
				ClassPara erp = classParas.get(i);
				if(erp!=null&&erp.getElClass()!=null&&erp.getElClass().getId()!=0){
					sb.append(erp.getElClass().getId());
					sb.append(":");
					sb.append(erp.getIsPassed());
					sb.append(":");
					sb.append(erp.getSumScoreStart());
					sb.append(":");
					sb.append(erp.getSumScoreEnd());
					sb.append(":");
					sb.append(erp.getBsumScoreStart());
					sb.append(":");
					sb.append(erp.getBsumScoreEnd());
					sb.append(":");
					sb.append(erp.getXsumScoreStart());
					sb.append(":");
					sb.append(erp.getXsumScoreEnd());
					sb.append(":");
					sb.append(erp.getLinkTerm()+",");
				}
			}
		}
		classParasstr = sb.toString();
	}
	/**
	 * 将列表转成字符串
	 */
	public void toErParamsstr(){
		StringBuffer sb = new StringBuffer();
		if(erParas!=null){
			for (int i = 0; i < erParas.size(); i++) {
				ErPara erp = erParas.get(i);
				if(erp!=null&&erp.getExamRoom()!=null&&erp.getExamRoom().getId()!=0){
					sb.append(erp.getExamRoom().getId());
					sb.append(":");
					sb.append(erp.getIsPassed());
					sb.append(":");
					sb.append(erp.getExamScoreTerm());
					sb.append(":");
					sb.append(erp.getExamScore());
					sb.append(":");
					sb.append(erp.getLinkTerm()+",");
				}
			}
		}
		erParasstr = sb.toString();
	}
	/**
	 * 将列表转成字符串
	 */
	public void toErepParamsstr(){
		StringBuffer sb = new StringBuffer();
		if(erepParas!=null){
			for (int i = 0; i < erepParas.size(); i++) {
				ErPara erp = erepParas.get(i);
				if(erp!=null&&erp.getExamRoom()!=null&&erp.getExamRoom().getId()!=0){
					sb.append(erp.getExamRoom().getId());
					sb.append(":");
					sb.append(erp.getExamPaper().getId());
					sb.append(":");
					sb.append(erp.getIsPassed());
					sb.append(":");
					sb.append(erp.getExamCountTerm());
					sb.append(":");
					sb.append(erp.getExamCount());
					sb.append(":");
					sb.append(erp.getAvgScoreTerm());
					sb.append(":");
					sb.append(erp.getAvgScore());
					sb.append(":");
					sb.append(erp.getMaxScoreTerm());
					sb.append(":");
					sb.append(erp.getMaxScore());
					sb.append(":");
					sb.append(erp.getLinkTerm()+",");
				}
			}
		}
		erepParasstr = sb.toString();
	}
	/**
	 * 将字符串转成列表(并输出人性化语言）
	 */
	public void toErParams (){
			List<ErPara> erps = null;
			try {
				 if(erParasstr!=null){
					 String x[] = erParasstr.split(",");
					 if(x!=null&&x.length>0){
						 erParasMsg="";
						 EroomDao erd = (EroomDao)SpringContextUtil.getBean("eroomDao");
						 erps = new ArrayList<ErPara>();
						 for (int i = 0; i < x.length; i++) {
							ErPara ep = new ErPara();
							String epstr[] = x[i].split(":");
							ep.setExamRoom(erd.getExamRoomByid(Integer.valueOf(epstr[0])));
							ep.setIsPassed(Integer.valueOf(epstr[1]));
							ep.setExamScoreTerm(epstr[2]);
							ep.setExamScore(Float.valueOf(epstr[3]));
							ep.setLinkTerm(epstr[4]);
							erps.add(ep);
							erParasMsg+="【"+ep.getExamRoom().getTitle()+"】:通过条件："+(ep.getIsPassed()==-1?"不限":ep.getIsPassed()==1?"通过":"不通过")
							+"，考场成绩"+ep.getExamScoreTerm()+ep.getExamScore();
							if(i!=x.length-1){
								erParasMsg+="<b> "+("and".equals(ep.getLinkTerm())?"并且":"或者")+"</b>";
							}
							erParasMsg+="<br/>";
						}
					 }
				 }
			} catch (Exception e) {
				// TODO: handle exception
			}
			erParas = erps;
	}
	/**
	 * 将字符串转成列表(并输出人性化语言）
	 */
	public void toClassParams (){
		List<ClassPara> erps = null;
		try {
			 if(classParasstr!=null){
				 String x[] = classParasstr.split(",");
				 if(x!=null&&x.length>0){
					 classParasMsg="";
					 ClassDao erd = (ClassDao)SpringContextUtil.getBean("classDao");
					 erps = new ArrayList<ClassPara>();
					 for (int i = 0; i < x.length; i++) {
						ClassPara ep = new ClassPara();
						String epstr[] = x[i].split(":");
						ep.setElClass(erd.getClassById(Integer.valueOf(epstr[0])));
						ep.setIsPassed(Integer.valueOf(epstr[1]));
						ep.setSumScoreStart(Float.valueOf(epstr[2]));
						ep.setSumScoreEnd(Float.valueOf(epstr[3]));
						ep.setBsumScoreStart(Float.valueOf(epstr[4]));
						ep.setBsumScoreEnd(Float.valueOf(epstr[5]));
						ep.setXsumScoreStart(Float.valueOf(epstr[6]));
						ep.setXsumScoreEnd(Float.valueOf(epstr[7]));
						ep.setLinkTerm(epstr[8]);
						erps.add(ep);
						classParasMsg+="【"+ep.getElClass().getName()+"】:通过条件："+(ep.getIsPassed()==-1?"不限":ep.getIsPassed()==1?"通过":"不通过")
						+"，总学分："+ep.getSumScoreStart()+"~"+ep.getSumScoreEnd()+"必修总分："+ep.getBsumScoreStart()+"~"+ep.getBsumScoreEnd()
						+"选修总分："+ep.getXsumScoreStart()+"~"+ep.getXsumScoreEnd() ;
						if(i!=x.length-1){
							classParasMsg+="<b> "+("and".equals(ep.getLinkTerm())?"并且":"或者")+"</b>";
						}
						classParasMsg+="<br/>";
					}
				 }
			 }
		} catch (Exception e) {
			// TODO: handle exception
		}
		classParas = erps;
	}
	/**
	 * 将字符串转成列表(并输出人性化语言）
	 */
	public void toErepParams(){
		List<ErPara> erps = null;
		try {
			 if(erepParasstr!=null){
				 String x[] = erepParasstr.split(",");
				 if(x!=null&&x.length>0){
					 erepParasMsg="";
					 erps = new ArrayList<ErPara>();
					 EroomDao erd = (EroomDao)SpringContextUtil.getBean("eroomDao");
					 ExamPaperDao epd = (ExamPaperDao)SpringContextUtil.getBean("examPaperDao");
					 for (int i = 0; i < x.length; i++) {
						ErPara ep = new ErPara();
						String epstr[] = x[i].split(":");
						ep.setExamRoom(erd.getExamRoomByid(Integer.valueOf(epstr[0])));
						ep.setExamPaper(epd.getExamPaperById(Integer.valueOf(epstr[1])));
						ep.setIsPassed(Integer.valueOf(epstr[2]));
						ep.setExamCountTerm(epstr[3]);
						ep.setExamCount(Integer.valueOf(epstr[4]));
						ep.setAvgScoreTerm(epstr[5]);
						ep.setAvgScore(Float.valueOf(epstr[6]));
						ep.setMaxScoreTerm(epstr[7]);
						ep.setMaxScore(Float.valueOf(epstr[8]));
						ep.setLinkTerm(epstr[9]);
						erps.add(ep);
						erepParasMsg+="【"+ep.getExamRoom().getTitle()+"【"+ep.getExamPaper().getTitle()+"】】:通过条件："+(ep.getIsPassed()==-1?"不限":ep.getIsPassed()==1?"通过":"不通过")
						+"，考试次数"+ep.getExamCountTerm()+ep.getExamCount()
						+"，考试平均分"+ep.getAvgScoreTerm()+ep.getAvgScore()
						+"，考试最高分"+ep.getMaxScoreTerm()+ep.getMaxScore() ;
						if(i!=x.length-1){
							erepParasMsg+="<b> "+("and".equals(ep.getLinkTerm())?"并且":"或者")+"</b>";
						}
						erepParasMsg+="<br/>";
					}
				 }
			 }
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 erepParas = erps;
	}
	public String getClassParasstr() {
		if(classParasstr==null)
			toClassParamsstr();
		return classParasstr;
	}
	public String getErParasstr() {
		if(erParasstr == null){
			toErParamsstr();
		}
		return erParasstr;
	}
	public void setErParasstr(String erParasstr) {
		this.erParasstr = erParasstr;
	}
	public String getErepParasstr() {
		if(erepParasstr == null){
			toErepParamsstr();
		}
		return erepParasstr;
	}
	public void setErepParasstr(String erepParasstr) {
		this.erepParasstr = erepParasstr;
	}
	public List<ErPara> getErParas() {
		if(erParas==null)
			toErParams();
		return erParas;
	}
	public List<ClassPara> getClassParas() {
		if(classParas==null)
			toClassParams();
		return classParas;
	}
	public String getClassParasMsg() {
		return classParasMsg;
	}
	public String getMyclassParasMsg() {
		return myclassParasMsg;
	}
	public void setErParas(List<ErPara> erParas) {
		this.erParas = erParas;
	}
	public List<ErPara> getErepParas() {
		if(erepParas==null)
			toErepParams();
		return erepParas;
	}
	public void setErepParas(List<ErPara> erepParas) {
		this.erepParas = erepParas;
	}
	public void setClassParas(List<ClassPara> classParas) {
		this.classParas = classParas;
	}
	public void setClassParasstr(String classParasstr) {
		this.classParasstr = classParasstr;
	}
	public int getIsselectep() {
		return isselectep;
	}
	public void setIsselectep(int isselectep) {
		this.isselectep = isselectep;
	}
	public int getPlanRecruitStudents() {
		return PlanRecruitStudents;
	}
	public void setPlanRecruitStudents(int planRecruitStudents) {
		PlanRecruitStudents = planRecruitStudents;
	}
	public int getJoinNumber() {
		return joinNumber;
	}
	public void setJoinNumber(int joinNumber) {
		this.joinNumber = joinNumber;
	}
	public int getApplyNumber() {
		return applyNumber;
	}
	public void setApplyNumber(int applyNumber) {
		this.applyNumber = applyNumber;
	}
	public int getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
	}
	public String getExamRoomIds() {
		return examRoomIds;
	}
	public void setExamRoomIds(String examRoomIds) {
		this.examRoomIds = examRoomIds;
	}
	public String getElclassIds() {
		return elclassIds;
	}
	public void setElclassIds(String elclassIds) {
		this.elclassIds = elclassIds;
	}
	public int getClassScreeningWay() {
		return classScreeningWay;
	}
	public void setClassScreeningWay(int classScreeningWay) {
		this.classScreeningWay = classScreeningWay;
	}
	public String getClassScreeningWayName() {
		if(classScreeningWay == 0){
			return "全部";
		}else if(classScreeningWay == 1){
			return "通过"; 
		}else if(classScreeningWay == 2){
			return "不通过";
		}
		return "未知类型";
	}
	public int getEroomScreeningWay() {
		return eroomScreeningWay;
	}
	public void setEroomScreeningWay(int eroomScreeningWay) {
		this.eroomScreeningWay = eroomScreeningWay;
	}
	public String getEroomScreeningWayName() {
		if(eroomScreeningWay == 0){
			return "全部";
		}else if(eroomScreeningWay == 1){
			return "通过"; 
		}else if(eroomScreeningWay == 2){
			return "不通过";
		}
		return "未知类型";
	}
	
	public ExamRoom getEroom() {
		return eroom;
	}

	public void setEroom(ExamRoom eroom) {
		this.eroom = eroom;
	}

	public String getJingzhongName() throws ElException {
		return new UserDaoImpl().getBaseDatatInId(jingzhong);
	}

	public String getDishiName() throws ElException {
		return new UserDaoImpl().getBaseDatatInId(dishi);
	}

	public String getZhiwuName() throws ElException {
		return new UserDaoImpl().getBaseDatatInId(zhiwu);
	}

	public String getGangweiName() throws ElException {
		return new UserDaoImpl().getBaseDatatInId(gangwei);
	}

	public String getZhijiName() throws ElException {
		return new UserDaoImpl().getBaseDatatInId(zhiji);
	}

	public String getTreeTypeName() throws ElException {
		return new DepartmentDaoImpl().getDepInId(treeType);
	}


	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public EroomRegistration() {

	}

//	public String getPlanRecruitStudents() {
//		return PlanRecruitStudents;
//	}
//
//	public void setPlanRecruitStudents(String planRecruitStudents) {
//		PlanRecruitStudents = planRecruitStudents;
//	}

	public Timestamp getRegistrationStartTime() {
		return RegistrationStartTime;
	}

	public void setRegistrationStartTime(Timestamp registrationStartTime) {
		RegistrationStartTime = registrationStartTime;
	}

	public Timestamp getRegistrationStopTime() {
		return RegistrationStopTime;
	}

	public void setRegistrationStopTime(Timestamp registrationStopTime) {
		RegistrationStopTime = registrationStopTime;
	}

	public int getStartAge() {
		return StartAge;
	}

	public void setStartAge(int startAge) {
		StartAge = startAge;
	}

	public int getStopAge() {
		return StopAge;
	}

	public void setStopAge(int stopAge) {
		StopAge = stopAge;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getJingzhong() {
		return jingzhong;
	}

	public void setJingzhong(String jingzhong) {
		this.jingzhong = jingzhong;
	}

	public String getDishi() {
		return dishi;
	}

	public void setDishi(String dishi) {
		this.dishi = dishi;
	}

	public String getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	public String getZhiji() {
		return zhiji;
	}

	public void setZhiji(String zhiji) {
		this.zhiji = zhiji;
	}

	public String getGangwei() {
		return gangwei;
	}

	public void setGangwei(String gangwei) {
		this.gangwei = gangwei;
	}

	public List<String> getJzlist() {
		List<String> list = null;
		;
		if (jingzhong != null&&!"".equals(jingzhong)) {
			list = new ArrayList<String>(Arrays.asList(jingzhong.split(",")));
		}
		return list;
	}

	public List<String> getDslist() {
		List<String> list = null;
		;
		if (dishi != null&&!"".equals(dishi)) {
			list = new ArrayList<String>(Arrays.asList(dishi.split(",")));
		}
		return list;
	}

	public List<String> getZjlist() {
		List<String> list = null;
		;
		if (zhiji != null&&!"".equals(zhiji)) {
			list = new ArrayList<String>(Arrays.asList(zhiji.split(",")));
		}
		return list;
	}

	public List<String> getZwlist() {
		List<String> list = null;
		;
		if (zhiwu != null&&!"".equals(zhiwu)) {
			list = new ArrayList<String>(Arrays.asList(zhiwu.split(",")));
		}
		return list;
	}

	public List<String> getGwlist() {
		List<String> list = null;
		;
		if (gangwei != null&&!"".equals(gangwei)) {
			list = new ArrayList<String>(Arrays.asList(gangwei.split(",")));
		}
		return list;
	}

	public List<String> getBmlist() {
		List<String> list = null;
		;
		if (treeType != null&&!"".equals(treeType)) {
			list = new ArrayList<String>(Arrays.asList(treeType.split(",")));
		}
		return list;
	}

	public String getTreeTypes() throws ElException {
		List bmName = getBmlist();
		String bm = "";
		if(bmName!=null){
			for (int i = 0; i < bmName.size(); i++) {
				String dep = new DepartmentDaoImpl().getByIdXiaJi(Integer
						.parseInt(bmName.get(i).toString()));
				if (!dep.equals("")) {
					if (bm.equals("")) {
						bm = bmName.get(i).toString() +","+ new DepartmentDaoImpl().getByIdXiaJi(Integer.parseInt(bmName.get(i).toString()));
					} else {
						bm = bmName.get(i).toString() +","+bm
								+ ","
								+ new DepartmentDaoImpl().getByIdXiaJi(Integer
										.parseInt(bmName.get(i).toString()));
					}
				}else if(bmName!=null){//解决如果没有下级部门了，那么dep为空串 bm为空串 学员报不了名
					bm=bmName.get(i).toString();
				}
			}
		}
		return bm;
	}

	public List<String> getTreeTypelist()throws ElException{  
		List<String> ReturnList = new ArrayList<String>(); 
		String[] dep = getTreeTypes().split(",");
		for(int i = 0; i < dep.length ; i++){
			ReturnList.add(dep[i]);
		}
		return ReturnList;
	}
	public void setTreeTypes(String treeTypes) {
		this.treeTypes = treeTypes;
	}

//	public String getJoinNumber() {
//		return joinNumber;
//	}
//
//	public void setJoinNumber(String joinNumber) {
//		this.joinNumber = joinNumber;
//	}

	public List<ElClass> getElclass() {
		return elclass;
	}

	public void setElclass(List<ElClass> elclass) {
		this.elclass = elclass;
	}

	public List<ExamRoom> getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(List<ExamRoom> examRoom) {
		this.examRoom = examRoom;
	}

	public String getElclasss() {
		String classsId = "";
		if(elclass != null){
			for (int i = 0; i < elclass.size(); i++) {
				if (classsId.equals("")) {
					classsId = elclass.get(i).getId() + "";
				} else {
					classsId = classsId + "," + elclass.get(i).getId();
				}
			}
		}
		return classsId;
	}

	public String getExamRooms() {
		String eroomId = "";
		if(examRoom != null){
			for (int i = 0; i < examRoom.size(); i++) {
				if (eroomId.equals("")) {
					eroomId = examRoom.get(i).getId() + "";
				} else {
					eroomId = eroomId + "," + examRoom.get(i).getId();
				}
			}
		}
		return eroomId;
	}
  
	public String getExamroomName() throws ElException {
		if(examRoom != null){
			EroomDaoImpl eroomDao = new EroomDaoImpl();
			String eroomName = "";
			for (int i = 0; i < examRoom.size(); i++) {
				if (eroomName.equals("")) {
					eroomName = eroomDao.getExamRoomByid(examRoom.get(i).getId())
							.getTitle();
				} else {
					eroomName = eroomName
							+ ","
							+ eroomDao.getExamRoomByid(examRoom.get(i).getId())
									.getTitle();
				}
			} 
		return eroomName;
		}else{ 
			return examroomName;
		}
	}
  
	public void setExamroomName(String examroomName) {
		this.examroomName = examroomName;
	}

	public String getElclassName()throws ElException {
		if(elclass != null){
			ClassDaoImpl elclassDao = new ClassDaoImpl();
			String elclassNames = "";
			for (int i = 0; i < elclass.size(); i++) {
				if (elclassNames.equals("")) {
					elclassNames = elclassDao.getElClassById(elclass.get(i).getId())
							.getName();
				} else {
					elclassNames = elclassNames
							+ ","
							+ elclassDao.getElClassById(elclass.get(i).getId())
									.getName();
				}
			}
			return elclassNames;
		}else{
			return elclassName;
		}
	}
 
	public void setElclassName(String elclassName) {
		this.elclassName = elclassName;
	}
}
