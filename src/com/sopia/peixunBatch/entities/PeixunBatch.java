package com.sopia.peixunBatch.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.classman.entities.ElClass;
import com.sopia.courseman.entities.Course;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.entities.MyClass;


public class PeixunBatch {
	private int id;
	private String name;
	private String description;
	private int typeid;
	private Timestamp createtime;
	private Timestamp endtime;
	private List<ElClass> classes;
	private ELUser creater;
	private int ElclassCount;
	private int createrid;
	private List<ELUser> elusers;
	private ElClass elclass;
	private int status;
	private String  elclassId;
//	private Timestamp beginTime;
//	private Timestamp endTime;
	private double process;
	private List<Course> courses;
	private Course course;
	private double processForElc;
	private int courseCount;
	private int userid;
	private int coforpassed; //已通过课程数
	private List<MyClass> myClasses;
	private int classCount;
	
	private BaseDatat baseData;
	
	private int sortid;
	
	private ElClass doneClass;//已完成的培训班
	private ElClass nowClass;//正在学习的培训班
	

	public ElClass getDoneClass() {
		return doneClass;
	}
	public void setDoneClass(ElClass doneClass) {
		this.doneClass = doneClass;
	}
	public ElClass getNowClass() {
		return nowClass;
	}
	public void setNowClass(ElClass nowClass) {
		this.nowClass = nowClass;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	public int getCoforpassed() {
		return coforpassed;
	}
	public void setCoforpassed(int coforpassed) {
		this.coforpassed = coforpassed;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public String getElclassId() {
		return elclassId;
	}
	public void setElclassId(String elclassId) {
		this.elclassId = elclassId;
	}
	public int getCreaterid() {
		return createrid;
	}
	public void setCreaterid(int createrid) {
		this.createrid = createrid;
	}
	public int getElclassCount() {
		return ElclassCount;
	}
	public void setElclassCount(int elclassCount) {
		ElclassCount = elclassCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public List<ElClass> getClasses() {
		return classes;
	}
	public void setClasses(List<ElClass> classes) {
		this.classes = classes;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}

	public PeixunBatch(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ElClass getElclass() {
		return elclass;
	}
	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}
	
	public PeixunBatch() {
		
	}
	public List<ELUser> getElusers() {
		return elusers;
	}
	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getProcess() {
		return process;
	}
	public void setProcess(double process) {
		this.process = process;
	}
	public int getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}
	public double getProcessForElc() {
		return processForElc;
	}
	public void setProcessForElc(double processForElc) {
		this.processForElc = processForElc;
	}
	public List<MyClass> getMyClasses() {
		return myClasses;
	}
	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}
	public BaseDatat getBaseData() {
		return baseData;
	}
	public void setBaseData(BaseDatat baseData) {
		this.baseData = baseData;
	}
	
	
}
