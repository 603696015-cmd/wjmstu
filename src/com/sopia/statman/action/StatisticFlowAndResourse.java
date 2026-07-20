package com.sopia.statman.action;

import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.statman.dao.StatisticFlowAndResourseDao;
import com.sopia.statman.entities.Flow;
import com.sopia.statman.entities.Queryobj;
import com.sopia.statman.entities.Resources;
import com.sopia.statman.entities.Statisticobj;

public class StatisticFlowAndResourse extends BaseAction {
	private StatisticFlowAndResourseDao sfrDao;
	private List<Department> departments;
	private List<Flow> flows;
	private Department depTree;
	private List<Resources> resources;
	private Queryobj queryobj;
	private Statisticobj statisticobj;

	public Queryobj getQueryobj() {
		return queryobj;
	}

	public void setQueryobj(Queryobj queryobj) {
		this.queryobj = queryobj;
	}

	public Statisticobj getStatisticobj() {
		return statisticobj;
	}

	public void setStatisticobj(Statisticobj statisticobj) {
		this.statisticobj = statisticobj;
	}

	public List<Resources> getResources() {
		return resources;
	}

	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public StatisticFlowAndResourseDao getSfrDao() {
		return sfrDao;
	}

	public void setSfrDao(StatisticFlowAndResourseDao sfrDao) {
		this.sfrDao = sfrDao;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	/**
	 * 流量统计
	 * @return
	 * @throws ElException 
	 */
	public String displayFlowInfo() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		flows=new ArrayList<Flow>();
		if(departments!=null){
			for (int i = 0; i < departments.size(); i++) {
				Department d = departmentDao.getDepById(departments.get(i).getId());
				departments.get(i).setLid(d.getLid());
				departments.get(i).setRid(d.getRid());
				flows.add(sfrDao.getFlowStatisticInfo(departments.get(i).getId()));
			}
		}else if(depTree.getId()==1){
			flows.add(sfrDao.getFlowStatisticInfo(1));
		}
		return "displayFlowInfo";
	}
	
	/**
	 * 资源统计
	 * @return
	 * @throws ElException
	 */
	public String resourcesstatistics() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		resources=new ArrayList<Resources>();
		if(departments!=null){
			for (int i = 0; i < departments.size(); i++) {
				Department d = departmentDao.getDepById(departments.get(i).getId());
				departments.get(i).setLid(d.getLid());
				departments.get(i).setRid(d.getRid());
				resources.add(sfrDao.getResourceStatistic(departments.get(i).getId()));
			}
		}else if(depTree.getId()==1){
			resources.add(sfrDao.getResourceStatistic(1));
		}
		return "resourcesstatistics";
	}
	/**
	 * 学员在线课程统计
	 * @return
	 * @throws ElException
	 */
	public String studyCourseStatistics() throws ElException {
		statisticobj=sfrDao.getCeRecordInfo("study_course_record");
		statisticobj.setQueryobjs(sfrDao.getAllCeRecordInfo(queryobj, "study_course_record", getPageNow(), getPageSize()));
		count=sfrDao.getAllCeRecordInfoSize(queryobj, "study_course_record");
		queryobj=queryobj==null?new Queryobj():queryobj;
		queryobj.setTableName("study_course_record");//页面隐藏传值用
		return "studyCourseStatistics";
	}
	/**
	 * 删除在线学习，考试等记录的全部搜索结果
	 * @return
	 * @throws ElException
	 */
	public String delCeRecordInfo() throws ElException {
		if(queryobj!=null){
			//根据不同参数返回不同页面
			if("study_course_record".equals(queryobj.getTableName())){
				sfrDao.deleteCeRecordInfo(queryobj);
				return "studyCourseStatistics";
			}else if("study_quizinfo_record".equals(queryobj.getTableName())){
				return "";
			}
		}
		return "error";
	}
}
