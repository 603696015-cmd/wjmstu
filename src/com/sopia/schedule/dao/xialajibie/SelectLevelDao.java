package com.sopia.schedule.dao.xialajibie;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public interface SelectLevelDao {
	public SelectLevel getSelectLevelTree_level1(int pid, int stopid, boolean containStop)
	throws ElException;
	
	public SelectLevel getSelectLevelTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException;
	
	public List<SelectLevel> listdepChildsByPId(int parentid) throws Exception;
	public boolean checkSelectLevelBh(String bh) throws ElException;
	public void addSelectLevel(SelectLevel selectLevel) throws ElException;
	
	public SelectLevel getDepById(int id) throws ElException;
	public List<ELUser> getOpUsers(String type, int selectlevelid) throws ElException;
	
	public void deleteSelectLevelAndSubNot(int id) throws ElException;
	
	/**
	 * 删除部门(并入上级)
	 * @param depid
	 * @param depParentid
	 * @throws ElException
	 */
	public void deleteDep(int depid,int depParentid) throws ElException;
	
	public List<ELUser> getEUsBySelectLevelid(int depid) throws ElException;
	
	public void alterSelectLevel(SelectLevel selectLevel) throws ElException;
	
	/**
	 * 获取级别数
	 * @param selectlevelid
	 * @return
	 * @throws ElException
	 */
	public int checkJibieshu(int selectlevelid) throws ElException;

}
