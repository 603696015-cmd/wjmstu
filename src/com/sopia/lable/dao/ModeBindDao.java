package com.sopia.lable.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.lable.entites.Mode;
import com.sopia.lable.entites.Template;
import com.sopia.lable.entites.TreeNode;

public interface ModeBindDao {
	
	/**
	 * 模块列表
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public  List<Mode>   Mode_getallmode(int pageNow,int pageSize)throws ElException ;
	public int Mode_getallmodecount() throws ElException ;
	/**
	 * 得到模块信息
	 * @param modeid:模块ID
	 * @param modetype:模块类型
	 * @param type:绑定类型
	 * @return
	 * @throws ElException
	 */
	public  Mode   Mode_getmodebyIDandType(int modeid,int modetype,int type) throws ElException;
	/**
	 * 得到所有模板
	 * @return
	 * @throws ElException
	 */
	public  List<Template> mode_modepageList(int pageNow,int pageSize,String filename) throws ElException;
	
	public  int mode_modepageListcount(String filename) throws ElException;
	/**
	 * 修改绑定信息
	 * @param mid:绑定的模板ID
	 * @param tid:绑定表ID
	 * @throws ElException
	 */
	public void mode_updmode_bind(int mid,int tid) throws ElException;
	/**
	 * 解除绑定
	 * @param bid
	 * @throws ElException
	 */
	public void mode_removemode_bind(int bid) throws ElException;
	/**
	 * 得到节点树
	 * @param id
	 * @param tableName
	 * @param stopid
	 * @param isContainStop
	 * @return
	 * @throws ElException
	 */
	public TreeNode epLibTree(int id, String tableName , int stopid,
			boolean isContainStop) throws ElException;
	
	/**
	 * 自定义表信息插入mode_info表
	 * @param id
	 * @throws ElException
	 */
	public void intoModeInfo(int id) throws ElException;
	
	/**
	 * 通过ID得到系统模块的基本信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public  Mode  Mode_getmodebyID(int id) throws ElException;
	/**
	 * 得到系统模块类别的绑定信息
	 * @param modeid
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public  Mode  Mode_getTypebindbyID(int modeid,int typeid) throws ElException;
	/**
	 * 检查类别是否存在
	 * @param typeid ：类别ID
	 * @param tableName：类别表名
	 * @return
	 * @throws ElException
	 */
	public   boolean   Mode_checktype(int  typeid,String tableName) throws ElException;
	/**
	 * 修改继承信息
	 * @param extendtype
	 * @param bid
	 * @throws ElException
	 */
	public  void  Mode_updtypeextend(int  extendtype,int bid) throws ElException;
	
	/**
	 * 获得页面（内容，模块）
	 * @param bindtype
	 * @param modeid
	 * @param modetype
	 * @return
	 * @throws ElException
	 */
	public  String  Mode_getpage(int bindtype,int modeid,int modetype) throws ElException;
	/**
	 * 获得页面（类别）
	 * @param modeid
	 * @param bindtypeid
	 * @return
	 * @throws ElException
	 */
	public  String  Mode_gettypepage(int modeid,int bindtypeid) throws ElException;
	/**
	 * 类别信息判断
	 * @param tableName
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public boolean checknode(String tableName,int id) throws ElException ;
	/**
	 * 得到一个节点的所有父节点
	 * @param tableName
	 * @param id
	 * @return
	 */
	public  List<TreeNode>   Mode_getnodeallparent(String tableName,int id )  throws ElException;
	/**
	 * 查询可继承绑定模板
	 * @param tableName
	 * @param id
	 * @return
	 */
	public  String  Mode_getentendtypepage(int modeid,int bindtypeid) throws ElException;
	/**
	 * 模板信息保存
	 * @param truename
	 * @param oldname
	 * @throws ElException
	 */
	public  void Mode_modeupload(String  truename,String oldname) throws ElException;
	
	public String createPerTypeId(TreeNode treeNode, int ptid);
	/**
	 * 获得当前模块的内容的类别ID
	 * @param id
	 * @param tablename
	 * @param field
	 * @return
	 * @throws ElException
	 */
	public  int  gettypeidformode(int  id,String  tablename,String  field) throws ElException;
	
	
	public  Template Mode_modeupload(int  id) throws ElException;
}
