package com.sopia.JTM.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.struts2.ServletActionContext;




import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.JTM.action.util.JTMUtil;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.JTM;
import com.sopia.common.JTMSystemConf;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.StationDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.entities.MyCourse;

public class JTMAction extends BaseAction{
	private UserDao userDao;
	private EroomDao eroomDao;
	private String JTM_URL = "";
	private ELUser elUser;
	private Department depTree;
	private String weidu;
	private Course course;
	private CourseDao courseDao;
	private List<ELUser> elUsers;
	private Department department;
	private int sub_department;
	private List<ElRole> roles;
	private RoleDao roleDao;
	private List<MyCourse> myCourses;
	private int count;
	private StudyCourseDao studyCourseDao;
	private String courseIds;
	private boolean addSuccess;
	private StationDao stationDao;
	private Station st;
	
	private String realname;
	private File sst;
	private String sstFileName;
	private Station stTree;
	private Station station;
	/////////////////////////////////
	
	//JTM整合

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

	//我的测评接口
	public String My_EvaluationInit() throws ElException, UnsupportedEncodingException{
		boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		if(!open_jtm){
			setElmessage("JTM未启用!");
			return "error";
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		
		String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
//		if(elUser.getRealname()==null||elUser.getRealname().equals("")){
//			this.setElmessage("姓名不能为空");
//			return "error";
//		}
//		if(elUser.getXueli()==null||elUser.getXueli().equals("")){
//			this.setElmessage("学历不能为空");
//			return "error";
//		}
//		if(elUser.getSpecialty()==null||elUser.getSpecialty().equals("")){
//			this.setElmessage("专业不能为空");
//			return "error";
//		}
//		if(elUser.getSchool()==null||elUser.getSchool().equals("")){
//			this.setElmessage("毕业院校不能为空");
//			return "error";
//		}
//		if(elUser.getSex()==null||elUser.getSex().equals("")){
//			this.setElmessage("性别不能为空");
//			return "error";
//		}
		if(elUser.getStation().getName()==null||elUser.getStation().getName().equals("")){
			this.setElmessage("岗位名称不能为空");
			return "error";
		}
		//一次GB2312编码
		JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_EVALUATION_URL)+
		"?userid="+elUser.getId()+
		"&name="+URLEncoder.encode(elUser.getRealname(), "GB2312")+
		"&username="+URLEncoder.encode(elUser.getUsername(), "GB2312")+
		"&Education="+URLEncoder.encode(elUser.getXueli(), "GB2312")+
		"&specialty="+URLEncoder.encode(elUser.getSpecialty(), "GB2312")+
		"&school="+URLEncoder.encode(elUser.getSchool(), "GB2312")+
		"&age="+elUser.getAge()+
		"&sex="+URLEncoder.encode(elUser.getSex(), "GB2312")+
		"&jobid="+elUser.getStaid()+
		"&jobdesc="+URLEncoder.encode(elUser.getStation().getName(), "GB2312")+
		"&cer="+cer;
		
		return "JTM_DEMO";
	}
	
	//人岗匹配接口
	public String peoplePostInit() throws ElException, UnsupportedEncodingException{
		boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		if(!open_jtm){
			setElmessage("JTM未启用!");
			return "error";
		}
		st = stationDao.getStById(st.getId());
		if(st.getName()==null||st.getName().equals("")){
			this.setElmessage("岗位名称不能为空");
			return "error";
		}
		String cer = JTM.getJTM_cer(String.valueOf(st.getId()));
		JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_PEOPLEPOST_URL)+
		"?jobid="+st.getId()+
		"&jobdesc="+URLEncoder.encode(st.getName(), "GB2312")+
		"&cer="+cer;
		
		return "JTM_DEMO";
	}
	//个人量身评价
	public String reportEval() throws ElException{
		boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		if(!open_jtm){
			setElmessage("JTM未启用!");
			return "error";
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		
		String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
		JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_REPORT_EVAL_URL) + 
		"?userid="+elUser.getId() + 
		"&cer=" + cer;
		return "JTM_DEMO";
	}
	//个人查看报告
	public String My_ReportInit() throws ElException{
		boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		if(!open_jtm){
			setElmessage("JTM未启用!");
			return "error";
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		
		String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
		
		JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_REPORT_URL)+
		"?jobid=" +elUser.getStaid()+
		"&userid="+elUser.getId()+
		"&cer="+cer;
		return "JTM_DEMO";
	}
	
	//课程同步
	public String courses_synchronization() throws ElException, UnsupportedEncodingException{
		boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		if(!open_jtm){
			setElmessage("JTM未启用!");
			return "error";
		}
		//获取课程
		//course.id,weidu
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		
		course = courseDao.getCourseById(course.getId());
		if(course.getName()==null||course.getName().equals("")){
			this.setElmessage("课程名称不能为空");
			return "error";
		}
		String cer = JTM.getJTM_cer(String.valueOf(course.getId()));
		
		JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_COURSES_AYSCHRONIZATION_URL)+
		"?courseid="+course.getId()+
		"&coursename="+URLEncoder.encode(course.getName(), "GB2312")+
		"&url=http://www.google.com/"+
		"&dimid="+weidu+
		"&cer="+cer;
		
		Content c = null;
		try {
			c = Request.Get(JTM_URL).addHeader("Content-Type", "text/html; charset=UTF-8").execute().returnContent();
			System.out.println(c.asString());
			String returnValue = c.asString();
			addSuccess = (returnValue!=null&&returnValue.equals("true"))?true:false;
			
			if(!addSuccess){
				this.setElmessage("课程添加错误，添加维度信息到JTM出错!");
				return "error";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "JTM_DEMO";
	}
	
	
	//我的测评课程
	public String myCepingCourses() throws ElException{
		boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		if(!open_jtm){
			setElmessage("JTM未启用!");
			return "error";
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		
		String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
		JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_CEPINGCOURSES_URL)+
		"?userid="+elUser.getId()+
		"&jobid="+elUser.getStaid()+
		"&cer="+cer;
		
		Content c = null;
		String returnValue = "";
		try {
			c = Request.Get(JTM_URL).addHeader("Content-Type", "text/html; charset=UTF-8").execute().returnContent();
			System.out.println(c.asString());
			returnValue = c.asString();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] arr = null;
		if(returnValue!=null&&!returnValue.equals("")){
			arr = returnValue.trim().split("\\|");
			if(arr[0].equals("true")){
				String[] courses = arr[1].split(",");
				if(courses!=null&&courses.length>0){
					//插入前，将userid对应的测评课程删除
//					studyCourseDao.deleteCePingCoursesByUseridAndClassid(getSessionIntValue(ElConstants.SESSION_USERID),-4);
					for(int i=0;i<courses.length;i++){
						//添加测评课程到课程分配表
						studyCourseDao.insertCepingCourse(getSessionIntValue(ElConstants.SESSION_USERID),Integer.parseInt(courses[i]));
//						cc = courseDao.getCourseById(Integer.parseInt(courses[i]));
//						if(cc!=null){
//							examRooms = eroomDao.listExamRoom2(Integer.parseInt(courses[i]), 0);
//							if(examRooms.size()==0){
//								mess += cc.getName()+"课程当前还没有安排结业考场,请调整<br>";
//								flag = false;
//							}else{
//								ExamRoom er = examRooms.get(examRooms.size()- 1);
//								int examRoomid = er.getId();
//								//将课程对应的考场分配给用户
//								stationDao.addUserRoom(examRoomid,getSessionIntValue(ElConstants.SESSION_USERID),-4);
//							}
//						}
					}
//					if(!flag){
//						this.setElmessage(mess);
//						return "error";
//					}
				}
				myCourses = studyCourseDao.listMyCepingCourse(
						getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
				count = studyCourseDao
				.listMyCepingCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
			}
		}
		myCourses = myCourses == null?new ArrayList<MyCourse>():myCourses;

		return "myCepingCourses_success";
	}
	
	//修改测评进度
	public String updateCepingjindu() throws ElException, IOException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		
		String userid = getRequest().getParameter("userid");
		String cer = getRequest().getParameter("cer");
		String cepingjindu = getRequest().getParameter("cepingjindu");
		if(userid == null || cepingjindu == null){
			out.print("false|userid或cepingjindu参数错误!");
		}else{
			elUser = userDao.getUserById(Integer.parseInt(userid));
			String myCer = "";
			if(elUser!=null&&elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
				myCer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
				if(!myCer.equals(cer)){
					out.print("false|约定字符串不同");
				}
				userDao.updateCepingjinduByUserid(Integer.parseInt(userid),cepingjindu);
				out.print("true");
			}else{
				out.print("false|用户不存在");
			}
		}
		out.flush();
		out.close();
		return null;
	}
	
	//测评进度
	public String statisticalAnalysis() throws ElException{
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
			sub_department = 1;
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
		
		elUsers = userDao.listUsers(department, station,sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize(department,station, sub_department, elUser);
		roles = roleDao.listRoles();
		return "statisticalAnalysis_success";
	}
	
	//课程维度同步
	public String tongbuCourseWeiduInit() throws ElException{
		return "tongbuCourseWeiduInit";
	}
	
	public String tongbuCourseWeidu() throws ElException, UnsupportedEncodingException{
		if (sst == null) {
			setElmessage("同步未选择文件!");
			return "tongbuCourseWeiduInit";
		}else{
			String ext = J2EEFileUtil.getExtention(sstFileName);
			if (!ext.equals("xls")) {
				setElmessage("同步的文件必须以.xls结尾!");
				return "tongbuCourseWeiduInit";
			}
			
			try {
				//导入
				boolean flag = JTMUtil.tongbuCourseWeidu(sst);
				if(!flag){
					this.setElmessage("同步失败!");
					return "error";
				}else{
					setElmessage(URLEncoder.encode(URLEncoder.encode("维度同步成功!", "UTF-8"), "UTF-8"));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return "tongbuCourseWeidu";
	}
	
	/////////////////////////////////
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public String getJTM_URL() throws UnsupportedEncodingException{
		return JTM_URL;
	}
	public void setJTM_URL(String jtm_url) {
		JTM_URL = jtm_url;
	}


	public ELUser getElUser() {
		return elUser;
	}


	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}


	public Department getDepTree() {
		return depTree;
	}


	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}
	public String getWeidu() {
		return weidu;
	}
	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public CourseDao getCourseDao() {
		return courseDao;
	}
	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
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
	public List<ElRole> getRoles() {
		return roles;
	}
	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public List<MyCourse> getMyCourses() {
		return myCourses;
	}
	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}
	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}
	public String getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}
	public boolean isAddSuccess() {
		return addSuccess;
	}
	public void setAddSuccess(boolean addSuccess) {
		this.addSuccess = addSuccess;
	}
	public EroomDao getEroomDao() {
		return eroomDao;
	}
	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public StationDao getStationDao() {
		return stationDao;
	}

	public void setStationDao(StationDao stationDao) {
		this.stationDao = stationDao;
	}

	public Station getSt() {
		return st;
	}

	public void setSt(Station st) {
		this.st = st;
	}

	public File getSst() {
		return sst;
	}

	public void setSst(File sst) {
		this.sst = sst;
	}

	public String getSstFileName() {
		return sstFileName;
	}

	public void setSstFileName(String sstFileName) {
		this.sstFileName = sstFileName;
	}
	
	
	
	

}
