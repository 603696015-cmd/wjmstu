package com.sopia.courseman.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sopia.classman.dao.impl.ClassDaoImpl;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.UserDaoImpl;

public class CourseRegistration {
	private Course course;
	private String PlanRecruitStudents; // 计划招收人数
	private Timestamp RegistrationStartTime;// 报名开始时间
	private Timestamp RegistrationStopTime;// 报名截至时间
	private int StartAge;// 开始年龄
	private int StopAge;// 终止年龄
	private String sex;// 性别
	private String jingzhong;// 工种
	private String dishi;// 地市
	private String zhiwu;// 职务
	private String zhiji;// 职级
	private String gangwei;// 岗位
	private String treeType;// 部门
	private String treeTypes;// 部门
	private String joinNumber; // 已参加人数
	private List<ElClass> elclass; // 培训班
	private List<ExamRoom> examRoom; // 考场
	private String examroomName;
	private String elclassName;
	private int classScreeningWay;//培训班筛选方式（0，全部  1，通过 2，不通过）
	private int eroomScreeningWay;//考场筛选方式（0，全部  1，通过 2，不通过）
	private int isAudit;//是否可申请
	
	
	public int getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
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

	public CourseRegistration() {

	}

	public String getPlanRecruitStudents() {
		return PlanRecruitStudents;
	}

	public void setPlanRecruitStudents(String planRecruitStudents) {
		PlanRecruitStudents = planRecruitStudents;
	}

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
		if (jingzhong != null) {
			list = new ArrayList<String>(Arrays.asList(jingzhong.split(",")));
		}
		return list;
	}

	public List<String> getDslist() {
		List<String> list = null;
		;
		if (dishi != null) {
			list = new ArrayList<String>(Arrays.asList(dishi.split(",")));
		}
		return list;
	}

	public List<String> getZjlist() {
		List<String> list = null;
		;
		if (zhiji != null) {
			list = new ArrayList<String>(Arrays.asList(zhiji.split(",")));
		}
		return list;
	}

	public List<String> getZwlist() {
		List<String> list = null;
		;
		if (zhiwu != null) {
			list = new ArrayList<String>(Arrays.asList(zhiwu.split(",")));
		}
		return list;
	}

	public List<String> getGwlist() {
		List<String> list = null;
		;
		if (gangwei != null) {
			list = new ArrayList<String>(Arrays.asList(gangwei.split(",")));
		}
		return list;
	}

	public List<String> getBmlist() {
		List<String> list = null;
		;
		if (treeType != null) {
			list = new ArrayList<String>(Arrays.asList(treeType.split(",")));
		}
		return list;
	}

	public String getTreeTypes() throws ElException {
		List bmName = getBmlist();
		String bm = "";
		for (int i = 0; i < bmName.size(); i++) {
			String dep = new DepartmentDaoImpl().getByIdXiaJi(Integer
					.parseInt(bmName.get(i).toString()));
			if (!dep.equals("")) {
				if (bm.equals("")) {
					bm = bmName.get(i).toString()+","+ new DepartmentDaoImpl().getByIdXiaJi(Integer
							.parseInt(bmName.get(i).toString()));
				} else {
					bm = bmName.get(i).toString()+","+bm
							+ ","
							+ new DepartmentDaoImpl().getByIdXiaJi(Integer
									.parseInt(bmName.get(i).toString()));
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

	public String getJoinNumber() {
		return joinNumber;
	}

	public void setJoinNumber(String joinNumber) {
		this.joinNumber = joinNumber;
	}

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
