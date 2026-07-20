package com.sopia.duman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.studyman.entities.MyExamPaper;

public class Department extends ElNode {
	// private int id;
	private String name;
	private String description;
	private String address;
	private String postalcode;
	private String phone;
	private String fax;
	private String email;
	private ELUser manager;
	// private Department parent;
	private List<Department> child;
	private int courseCount;
	private int classCount;
	private int userCount_;
	private int userCount;
	private int userCount_jg;
	private int userCredit;
	private String deppassper;
	private List<ELUser> users;
	private List<ELUser> opusers;
	private List<ELUser> useusers;
	private String bh;
	private float avg;
	private String ratio;// XX率 例：通过率 ， 及格率...
	private double ratiof;//及格率
	private String ratiof_;//及格率
	private List<MyExamPaper > myexampapers;
	private ExamRoom  examRoom ;
	private Examprac examprac;
	private boolean lower;//是否包含下级
	private int lid;
	private int rid;
	private int issp;//是否二级页面
	private int parentid;
	private UnitRanking unit;//单位
	private int userGaojiCount;//高级职称人数
	private int userGaojiPassCount;//高级职称通过人数
	private double ratioPassing_;//设置通过率
	private double ratioPassing;//通过率
	private String title;//标题
	private String image;//图片
	private String luokuanwenzi;//落款文字
	private String lingyu;//专注领域
	private String dishi;//地市
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLuokuanwenzi() {
		return luokuanwenzi;
	}

	public void setLuokuanwenzi(String luokuanwenzi) {
		this.luokuanwenzi = luokuanwenzi;
	}

	public String getLingyu() {
		return lingyu;
	}

	public void setLingyu(String lingyu) {
		this.lingyu = lingyu;
	}

	public String getDishi() {
		return dishi;
	}

	public void setDishi(String dishi) {
		this.dishi = dishi;
	}

	public double getRatioPassing() {
		return ratioPassing;
	}

	public void setRatioPassing(double ratioPassing) {
		this.ratioPassing = ratioPassing;
	}

	public double getRatioPassing_() {
		return ratioPassing_;
	}

	public void setRatioPassing_(double ratioPassing_) {
		this.ratioPassing_ = ratioPassing_;
	}

	public int getUserGaojiCount() {
		return userGaojiCount;
	}

	public void setUserGaojiCount(int userGaojiCount) {
		this.userGaojiCount = userGaojiCount;
	}

	public int getUserGaojiPassCount() {
		return userGaojiPassCount;
	}

	public void setUserGaojiPassCount(int userGaojiPassCount) {
		this.userGaojiPassCount = userGaojiPassCount;
	}

	public UnitRanking getUnit() {
		return unit;
	}

	public void setUnit(UnitRanking unit) {
		this.unit = unit;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getIssp() {
		return issp;
	}

	public void setIssp(int issp) {
		this.issp = issp;
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public boolean isLower() {
		return lower;
	}

	public void setLower(boolean lower) {
		this.lower = lower;
	}

	public Examprac getExamprac() {
		return examprac;
	}

	public void setExamprac(Examprac examprac) {
		this.examprac = examprac;
	}

	public ExamRoom  getExamRoom () {
		return examRoom ;
	}

	public void setExamRoom ( ExamRoom examRoom ) {
		this.examRoom  = examRoom ;
	}

	public List<MyExamPaper> getMyexampapers() {
		return myexampapers;
	}

	public void setMyexampapers(List<MyExamPaper> myexampapers) {
		this.myexampapers = myexampapers;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public List<ELUser> getOpusers() {
		return opusers;
	}

	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}

	public List<ELUser> getUseusers() {
		return useusers;
	}

	public void setUseusers(List<ELUser> useusers) {
		this.useusers = useusers;
	}

	public List<ELUser> getUsers() {
		return users;
	}

	public void setUsers(List<ELUser> users) {
		this.users = users;
	}

	public int getUserCredit() {
		return userCredit;
	}

	public void setUserCredit(int userCredit) {
		this.userCredit = userCredit;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}

	public List<Department> getChild() {
		return child;
	}

	public void setChild(List<Department> child) {
		this.child = child;
	}

	public Department() {
	}

	public Department(int id) {
		super(id);
	}

	public Department(int id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
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

	/*
	 * public Department getParent() { return parent; } public void
	 * setParent(Department parent) { this.parent = parent; }
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ELUser getManager() {
		return manager;
	}

	public void setManager(ELUser manager) {
		this.manager = manager;
	}

	public int getClassCount() {
		return classCount;
	}

	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}

	public String getDeppassper() {
		float deppass = userCount == 0 ? 0 : userCredit * 100.0f / userCount;
		deppassper = deppass + "";
		deppassper = deppassper.substring(0,
				deppassper.lastIndexOf(".")
						+ ((deppassper.substring(deppassper.lastIndexOf(".")))
								.length() > 2 ? 2 : (deppassper
								.substring(deppassper.lastIndexOf(".")))
								.length()));

		return deppassper;
	}

	public void setDeppassper(String deppassper) {
		this.deppassper = deppassper;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	/**
	 * 获取及格率
	 * @return
	 */
	public double getRatiof() {
		return ratiof;
	}

	/**
	 * 设置及格率
	 */
	public void setRatiof(double ratiof) {
		this.ratiof = ratiof;
	}

	public int getUserCount_() {
		return userCount_;
	}

	public void setUserCount_(int userCount_) {
		this.userCount_ = userCount_;
	}

	public int getUserCount_jg() {
		return userCount_jg;
	}

	public void setUserCount_jg(int userCount_jg) {
		this.userCount_jg = userCount_jg;
	}

	public String getRatiof_() {
		return ratiof_;
	}

	public void setRatiof_(String ratiof_) {
		this.ratiof_ = ratiof_;
	}
}
