package com.sopia.schedule.action;

import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;

import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;

import com.sopia.schedule.dao.impl.LogDaoImpl;
import com.sopia.schedule.dao.LogDao;
import com.sopia.schedule.entities.LogStuff;
import com.sopia.schedule.entities.Logfile;
import com.sopia.schedule.entities.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogAction extends BaseAction {
	private Logfile log;// = new Logfile();
	private LogDao logDao;// = new LogDao();
	private List<Logfile> list_log = new ArrayList<Logfile>();

	private Department depTree;
	private Department department;

	private LogStuff logStuff;
	private List<LogStuff> list_logstuff = new ArrayList<LogStuff>();

	private String ismodify;

	public LogStuff getLogStuff() {
		return logStuff;
	}

	public void setLogStuff(LogStuff logStuff) {
		this.logStuff = logStuff;
	}

	// --------------------action methods-------------------------
	/*
	 * 跳转到添加页面
	 */
	public String addLog_view() throws ElException {

		return "add_log_jsp";
	}

	/*
	 * 添加日志
	 */
	public String addLog() throws ElException {

		int logid = -1;

		String staddr[] = getRequest().getParameterValues(
				"knowledge.stuffs.description");
		String sttitle[] = getRequest().getParameterValues(
				"knowledge.stuffs.title");

		// for(int i=0;i<staddr.length;i++)
		// {
		// System.out.print("\n>>"+staddr[i]+"<<\n");
		// }

		log.setLog_createtime(getCreateTime());
		log.setLog_userid(getSessionIntValue(ElConstants.SESSION_USERID));
		logid = logDao.addLog(log);

		if (null != staddr) {
			for (int i = 0; i < staddr.length; i++) {
				logDao.addLogStuff(logid, staddr[i], sttitle[i]);
			}
		}

		return "addLogSuccess";
	}

	/*
	 * 按用户id分页查询日志
	 */
	public String selectMyLogById() throws ElException {
		// if(log!=null)
		// {
		// // if(log.getLog_title()!=null)
		// // System.out.print("\n>>"+log.getLog_title()+"<<\n");
		// if(log.getLog_co_client()!=null&&!log.getLog_co_client().equals(""))
		// System.out.print("\n>>>>>"+log.getLog_co_client()+"<<\n");
		// if(log.getLog_createtime()!=null&&!log.getLog_createtime().equals(""))
		// System.out.print("\n>>time:"+log.getLog_createtime()+"<<\n");
		// }

		// int userid=0;
		// userid=getSessionIntValue(ElConstants.SESSION_USERID);
		// list_log =
		// logDao.selectMyLogsByUserId(userid,getPageNow(),getPageSize());
		// count = logDao.selectMyLogsByUserIdCount(userid);
		// System.out.print(">>>"+getSessionIntValue(ElConstants.SESSION_USERID)+"<<<<");

		if (log == null) {
			// System.out.print(">>>dkdkdkdd<<<<");
			log = new Logfile();
		}

		log.setLog_userid(getSessionIntValue(ElConstants.SESSION_USERID));
		list_log = logDao
				.selectMyLogsByUserId(log, getPageNow(), getPageSize());
		count = logDao.selectMyLogsByUserIdCount(log);

		return "selectLogsSuccess";
	}

	/*
	 * 删除日志
	 */
	public String delLog() throws ElException {
		int logid = 0;
		logid = log.getId();
		logDao.delLogByUserId(logid);
		logDao.delLogStuffByLogId(logid);

		return "delLogSuccess";
	}

	/*
	 * 删除日志附件
	 */
	public String delLogStuff() throws ElException {
		// int logid=0;
		// logid=log.getId();
		// logDao.delLogStuffByLogId(logid);
		// logDao.delLogStuffById();

		// System.out.print("\nid:>>"+logStuff.getId()+"\n");

		logDao.delLogStuffById(logStuff.getId());
		log = logDao.getLogByLogId(log.getId());
		list_logstuff = logDao.getListLogStuff(log.getId());

		return "delLogStuffSuccess";
	}

	/*
	 * 按日志id查询单个日志
	 */
	public String getLogById() throws ElException {
		int logid;
		logid = log.getId();
		log = logDao.getLogByLogId(logid);

		list_logstuff = logDao.getListLogStuff(log.getId());

		if (ismodify == null)
			ismodify = "view";
		if (ismodify.equals("modify"))
			return "updateLog";
		else
			return "getLogSuccess";
	}

	/*
	 * 按id修改日志
	 */
	public String updateLog() throws ElException {
	//	int numOfLogstuff = 0;
		String staddr[] = getRequest().getParameterValues(
				"knowledge.stuffs.description");
		String sttitle[] = getRequest().getParameterValues(
				"knowledge.stuffs.title");

		// for(int i=0;i<staddr.length;i++)
		// {
		// System.out.print("\n>>"+staddr[i]+"<<\n");
		// }
		//		
		//		
		// System.out.print("\nidddd:"+log.getId()+"\n");

//		numOfLogstuff = logDao.getNumOfLogStuffByLogId(log.getId());
//		// System.out.print("\nidddd:"+numOfLogstuff+"\n");
//		if (null != staddr) {
//			for (int i = 0 + numOfLogstuff; i < staddr.length; i++) {
//				logDao.addLogStuff(log.getId(), staddr[i], sttitle[i]);
//			}
//		}
		logDao.delLogStuffByLogId(log.getId());
		
		if (null != staddr) {
			for (int i = 0; i < staddr.length; i++) {
				logDao.addLogStuff(log.getId(), staddr[i], sttitle[i]);
			}
		}
		
		logDao.updateLogById(log);

		return "updateSuccessAndList";
	}

	public String searchLog() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
			if (department == null)
				department = new Department();
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if (depTree.getChild().size() != 0) {
				if (department == null)
					department = new Department(depTree.getChild().get(0).getId());
			} else {
				setElmessage("您无可操作的节点！请联系管理员！");
				return "error";
			}
		}

		// department = departmentDao.getDepById(department.getId());
		// if(department.getId()>0)
		// {
		// list_log=logDao.searchLogByDepid(department.getLid(),department.getRid(),getPageNow(),getPageSize());
		// count=logDao.searchLogByDepidCount(department.getLid(),department.getRid());
		// }

		// --------------------------------------
		if (log == null) {
			// System.out.print(">>>dkdkdkdd<<<<");
			log = new Logfile();
		}
		
		department = departmentDao.getDepById(department.getId());
		log.setLog_userid(getSessionIntValue(ElConstants.SESSION_USERID));
		if (department.getId() > 0) {
			list_log = logDao.searchLogByDepid(log, department.getLid(),
					department.getRid(), getPageNow(), getPageSize());
			count = logDao.searchLogByDepidCount(log, department.getLid(),
					department.getRid());
		}

		return "search_jsp";
	}

	public String searchLogAction() throws ElException {
		return "search_jsp";
	}

	// ---------------一些调用到的方法------------------
	// 获取当前时间
	public String getCreateTime() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(now);
	}

	// -----------------------setters and getters-------------------------
	public Logfile getLog() {
		return log;
	}

	public void setLog(Logfile log) {
		this.log = log;
	}

	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	public List<Logfile> getList_log() {
		return list_log;
	}

	public void setList_log(List<Logfile> list_log) {
		this.list_log = list_log;
	}

	public String getIsmodify() {
		return ismodify;
	}

	public void setIsmodify(String ismodify) {
		this.ismodify = ismodify;
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

	public List<LogStuff> getList_logstuff() {
		return list_logstuff;
	}

	public void setList_logstuff(List<LogStuff> list_logstuff) {
		this.list_logstuff = list_logstuff;
	}

}
