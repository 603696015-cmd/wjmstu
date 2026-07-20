package com.sopia.schedule.dao.dataallocation;

import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.dataallocation.DataAllocation;

public interface DataAllocationDao {
	/**
	 * 数据分配
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Map<String,String>> listDataAllocation(List<Tags> list_tags, ModuleManage moduleManage,ElNode department,int pageNow, int pageSize,String tablename,String order) throws ElException;
	/**
	 * 数据分配条数
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listDataAllocationSize(List<Tags> list_tags, ModuleManage moduleManage,ElNode department,int pageNow,int pageSize,String tablename) throws ElException;

	/**
	 * 数据申请
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Map<String,String>> listDataApplication(int userid,List<Tags> list_tags, ModuleManage moduleManage,ElNode department,int pageNow, int pageSize,String tablename,String order) throws ElException;
	/**
	 * 数据申请条数
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listDataApplicationSize(int userid,List<Tags> list_tags, ModuleManage moduleManage,ElNode department,int pageNow,int pageSize,String tablename) throws ElException;
	
	/**
	 * 人员分配列表
	 * @param department
	 * @param eluser
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUsers(int sub_department,ElNode department ,ELUser eluser,int pageNow, int pageSize,int id) throws ElException;
	/**
	 * 人员分配用户数量
	 * @param department
	 * @param eluser
	 * @return
	 * @throws ElException
	 */
	public int listUsersSize(int sub_department,ElNode department ,ELUser eluser,int id) throws ElException;
	
	/**
	 * 插入前删除原有信息
	 * @param id
	 * @param moduleManage
	 * @throws ElException
	 */
	public void deleteDataAllocationAll(int id,ModuleManage moduleManage) throws ElException;
	/**
	 * 插入分配信息表
	 * @param id
	 * @param userid
	 * @param moduleManage
	 * @param dataAllocation
	 * @throws ElException
	 */
	public void insertDataAllocation(int id,int userid,ModuleManage moduleManage,DataAllocation dataAllocation) throws ElException;
	
	/**
	 * 取消分配信息
	 * @param id
	 * @param userid
	 * @param moduleManage
	 * @throws ElException
	 */
	public void deleteDataAllocation(int id,int userid,ModuleManage moduleManage) throws ElException;
	
	/**
	 * 审核通过或者不通过
	 * @param id
	 * @param userid
	 * @param moduleManage
	 * @param type
	 * @throws ElExcepiton
	 */
	public void updateDataAllocation(int id,int userid,ModuleManage moduleManage,int type,DataAllocation dataAllocation) throws ElException;
	
	/**
	 * 获得已分配数据ids
	 * @param moduleManage
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String myGetDataAllocationIds(ModuleManage moduleManage,int userid,String tablename) throws ElException;
	/**
	 * 获取已分配的数据
	 * @param list_tags
	 * @param moduleManage
	 * @param pageNow
	 * @param pageSize
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public List<Map<String,String>> listMyGetDataAllocation(List<Tags> list_tags, ModuleManage moduleManage,int pageNow,int pageSize,String tablename,String ids) throws ElException;
	/**
	 * 获取已分配的数据条数
	 * @param list_tags
	 * @param moduleManage
	 * @param pageNow
	 * @param pageSize
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public int listMyGetDataAllocationSize(List<Tags> list_tags, ModuleManage moduleManage,int pageNow,int pageSize,String tablename,String ids) throws ElException;
	
	/**
	 * 根据数据id、用户id、模块id查询分配信息
	 * @param id
	 * @param userid
	 * @param moduleManage
	 * @return
	 * @throws ElException
	 */
	public DataAllocation select_dataAllocation_by_moduleid_userid_entityid(ModuleManage moduleManage,int userid,int id) throws ElException;
	
}
