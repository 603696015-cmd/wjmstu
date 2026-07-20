package com.sopia.knowledgeManage.dao;

import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.entities.Competence;
import com.sopia.knowledgeManage.entities.Kledge;
import com.sopia.schedule.entities.AuditMark;

public interface KnowledgeManageDao {
	
	public int addKledge(Kledge kledge) throws ElException;
	public int updateKledge(Kledge kledge) throws ElException;
	public void deleteKledgeById(int id) throws ElException;
	
	public void addCompetenceByUserid(String tablename,int id,int userid,int competenceType) throws ElException;
	
	public void addCompetenceByDepartmentid(String tablename,int id,int depid,int competenceType) throws ElException;
	
	public List<Kledge> listMyKledge(int userid,int pageNow,int pageSize) throws ElException;
	public int listMyKledgeSize(int userid) throws ElException;
	
	public Kledge getKledgeById(int id) throws ElException;
	
	public Competence getCompetenceByUseridOrDepid(ELUser elUser,String competenceTable,int competenceType,String sql) throws ElException;
	public List<Competence> getCompetencesById(int id,String competenceTable,int competenceType) throws ElException;
	public Map<String,String> getCompetencesSizeByDepid(int id,String competenceTable,int competenceType) throws ElException;
	
	public List<Kledge> listKledgeSearch(String ids,int pageNow,int pageSize) throws ElException;
	public int listKledgeSearchSize(String ids) throws ElException;
	public List<Kledge> listKledgeAll(ElNode department,int status,int pageNow,int pageSize) throws ElException;
	public int listKledgeAllSize(ElNode department,int status) throws ElException;
	
	public void changeStatus(int id,int status) throws ElException ;
	public void addMark(int id,AuditMark audit,int status) throws ElException;
	
	public List<AuditMark> getKnowledgeMark(String tablename,int id,int pageNow,int pageSize) throws ElException;
	public int getKnowledgeMarkSize(String tablename,int id) throws ElException;
	
	public int knowledgeManageCopy(int id,int userid) throws ElException;
	
	public void deleteCompetenceById(int id) throws ElException;
	
	/**
	 * 本人获得授权的知识ids
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public String getKledgeByCompetence(int userid) throws ElException;
	/**
	 * 所在部门获得授权的知识ids
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public String getKledgeByCompetence1(Department dep,DepartmentDao departmentDao) throws ElException;
	
	public void kledgeHotSet(int id,int hot)throws ElException;

}
