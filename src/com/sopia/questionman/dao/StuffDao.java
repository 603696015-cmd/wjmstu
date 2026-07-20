package com.sopia.questionman.dao;

import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.questionman.entities.StuffQuery;

public interface StuffDao {

	public int addQstuff(StuffLib qs) throws ElException;
	
	public List<StuffLib> getStuffs(StuffLib stuff, int userid, int pageNow,
			int pageSize) throws ElException;

	public int getStuffsCount(StuffLib stuff, int userid) throws ElException;

	public List<StuffLib> listStuffs(StuffLib stuff, String order, String ot)
			throws ElException;

	public List<StuffLib> listMyStuffs(StuffLib stuff, int userid)
			throws ElException;

	public void setStuffShared(int stuffid, int shared) throws ElException;

	public void setStuffsize(int stuffid, long size) throws ElException;

	public long getStuffParentSize(int id) throws ElException;

	public long getStuffChildsSize(int id) throws ElException;

	public int getStuffOpStatus(int stuffid, int userid, int roleid, int type)
			throws ElException;

	public List<ELUser> getStuffOpUsers(int stuffid) throws ElException;

	public void addStuffOpusers(int userid, int stuffid) throws ElException;

	public void deleteStuffOpusers(int userid, int stuffid) throws ElException;

	public boolean checkStuffOpUsers(int userid, int stuffid)
			throws ElException;

	public StuffLib getStuffbyId(int id, int userid) throws ElException;
	public boolean checkStuffidisGrant(int id, int userid) throws ElException;

	public void setStuffParent(StuffLib stuffLib) throws ElException;

	public void setStuffParent(StuffLib stuffLib, List<StuffLib> list)
			throws ElException;

	public StuffLib getStuffFolderTree() throws ElException;

	public StuffLib getStuffFolderTree(int userid) throws ElException;

	public StuffLib listFolderShared() throws ElException;

	public void alter(StuffLib qs) throws ElException;

	public void deleteQs(int id, int userid) throws ElException;

	/**
	 * 删除用户可使用的权限
	 */
	public void deleteStuffUseusers(int userId) throws ElException;

	public StuffLib getStuffbyId2(int id, int userid) throws ElException;

	/**
	 * 判断地址是否本机
	 * 
	 * @param url
	 * @param contextPath
	 * @return
	 * @throws ElException
	 */
	public boolean checkUrlIsLocal(String url, String contextPath,
			String serverName) throws ElException;
	/**
	 * 设置资源的路径
	 * @param stuffLib
	 * @return
	 * @throws ElException
	 */
	public String setStuffPath(int stuffId) throws ElException;
	/**
	 * 获取资源树所有id
	 * @param stuffTree
	 * @return
	 * @throws ElException
	 */
	public String getStuffIds(StuffLib stuffTree) throws ElException;
	/**
	 * 搜索资源
	 * @param stuffIds
	 * @param stuffQuery
	 * @return
	 * @throws ElException
	 */
	public List<StuffLib> listSeachStuffs(String stuffIds,StuffQuery stuffQuery) throws ElException;
	public List<StuffLib> listSeachStuffs(String stuffIds,StuffQuery stuffQuery,int pagenow,int pagesize) throws ElException;
	public int listSeachStuffsSize(String stuffIds,StuffQuery stuffQuery)throws ElException;
	/**
	 * 检测用户是否具有删除资源的权限（判断该用户所管理的部门是否包含资源创建者的所在部门）
	 * @param userId
	 * @param qstuff
	 * @throws ElException
	 */
	public boolean checkUserIsdelStuff(int userid,StuffLib qstuff) throws ElException;
	/**
	 * 检测资源有没有被共享
	 * @param qstuff
	 * @return
	 * @throws ElException
	 */
	public boolean checkStuffIsShared(int qstuffId) throws ElException;
	/**
	 * 设置资源的所有子节点的共享状态
	 * @param qstuffId
	 * @return
	 * @throws ElException
	 */
	public void updateStuffChildShared(int qstuffId,int status) throws ElException;
	/**
	 * 检测用户是否具有创建文件夹的权限
	 * @param userid
	 * @param qstuff
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserIsaddStuff(int userid,int stuffId) throws ElException;
	/**
	 * 查询文件中的所有指定图片资源
	 * @param stuff
	 * @param order
	 * @param ot
	 * @param imgfexts
	 * @return
	 * @throws ElException
	 */
	public List<StuffLib> listStuffs(StuffLib stuff, String order, String ot,String imgfexts)
	throws ElException;
	/**
	 * 获取资源在文件夹中的位置
	 * @param stuffId
	 * @param imgfexts
	 * @return
	 * @throws ElException
	 */
	public int getStuffLoca(int stuffId,int stuffpId,String imgfexts) throws ElException;
	/**
	 * 更新素材的状态
	 * @param qstuffId
	 * @param status
	 * @throws ElException
	 */
	public void updateStuffStatus(int qstuffId,int status) throws ElException;
	/**
	 * 判断有没有当前月份的节点
	 * @param value
	 * @return
	 * @throws ElException
	 */
	public int checkStuffForMonth(String value,int eluserid) throws ElException;
	/**
	 * 首页资源库
	 */
	public List<StuffLib> listStuffs(StuffLib stuff, String form) throws ElException;
	
	public List<StuffLib> listStuffs(int pageNow,int pageSize)throws ElException;
	
	/**
	 * 根据当前ID获得上一个和下一个ID
	 */
	public int getStuffId(int id,int num,String form)throws ElException;
	
	/**
	 * 更新stuff的generatejpg字段
	 * @param id
	 * @throws ElException
	 */
	public void updateStuffJpg(int id) throws ElException;
	
	/**
	 * 获取所有转换缩略图的office文件在数据库中的id和title
	 * @return
	 * @throws ElException
	 */
	public List<Map<String, Object>> listJpgIds() throws ElException;
	
	/**
	 * 根据stuffid获取fromchange属性
	 * @param stuffid
	 * @return
	 * @throws ElException
	 */
	public int getFromchange(int stuffid) throws ElException;
}
