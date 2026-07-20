package com.sopia.userDemo.dao;

import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.userDemo.entities.ELUserColumn;
import com.sopia.userDemo.entities.ELUserColumnJs;
import com.sopia.userDemo.entities.ELUserJs;
import com.sopia.userDemo.entities.ELUserColumnPage;
import com.sopia.userDemo.entities.ELUserPage;
import com.sun.star.container.ElementExistException;

public interface UserDemoDao {
	/**
	 * 用户表列信息
	 * @return
	 * @throws ElException
	 */
	public List<ELUserColumn> listUserDemoColumns(String tablename) throws ElException;
	public List<ELUserColumn> getColumnsByPageid(int pageid) throws ElException;
	/**
	 * 修改字段
	 * @param co
	 * @throws ElException
	 */
	public void updateUserDemoColumn(ELUserColumn co,String tablename) throws ElException;
	
	/**
	 * 验证该列名是否已经存在
	 * @param column
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public boolean checkColumnIsExist(String column,String tablename) throws ElException;
	/**
	 * 添加字段
	 * @param co
	 * @param tablename
	 * @throws ElException
	 */
	public void insertUserDemoColumn(ELUserColumn co,String tablename,String user_tablename) throws ElException;
	
	/**
	 * 查询需要显示的列
	 * @param show
	 * @param tablename
	 * @return
	 * @throws ElementExistException
	 */
	public List<ELUserColumn> selectColumnsByShow(int show,String tablename) throws ElException;
	
	/**
	 * 获取所有js校验实体
	 * @param jstable
	 * @return
	 * @throws ElException
	 */
	public List<ELUserJs> listAllJsTypes(String jstable) throws ElException;
	
	/**
	 * 给列在不同显示页面添加js验证
	 * @param elUserJs
	 * @throws ElException
	 */
	public void insertUserColumnJs(ELUserColumnJs elUserJs,int show) throws ElException;
	
	/**
	 * 给列设置范围
	 * @param pageType
	 * @throws ElException
	 */
	public void setPageType(ELUserPage pageType) throws ElException;
	
	/**
	 * 获取页面信息
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public List<ELUserColumnPage> listELUserPage(String tablename) throws ElException;
	
	/**
	 * 修改上传属性
	 * @param pageid
	 * @throws ElException
	 */
	public void updateUpload(int pageid) throws ElException;
	
	
	/**
	 * 插入用户表
	 * @param map
	 * @return
	 * @throws ElException
	 */
	public int insertIntoELUser(Map<String,String> map,String tablename) throws ElException;

}
