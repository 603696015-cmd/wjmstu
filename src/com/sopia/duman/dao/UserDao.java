package com.sopia.duman.dao;

import java.util.List;
import java.util.Map;

import com.sopia.classman.entities.ElClType;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.MyLogin;
import com.sopia.duman.entities.Station;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.wordman.entities.Word;

public interface UserDao {
	/**
	 * 检测用户名，密码
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws ElException
	 */
	public boolean check(String username, String password) throws ElException;

	/**
	 * 通过用户名查询用户
	 * 
	 * @param username
	 * @return
	 * @throws ElException
	 */
	public ELUser query(String username) throws ElException;

	/**
	 * 按用户id得到用户信息
	 * 
	 * @param un
	 * @return
	 * @throws ElException
	 */
	public ELUser getUserById(int id) throws ElException;

	/**
	 * 按用户name得到用户信息
	 * 
	 * @param un
	 * @return
	 * @throws ElException
	 */
	public ELUser getUserByName(String name) throws ElException;

	/**
	 * 用户插入
	 * 
	 * @param elUser
	 * @throws ElException
	 */
	public int insert(ELUser elUser) throws ElException;
	/**
	 * 北京市卫生局注册
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int insert_cisco(ELUser elUser) throws ElException;
	
	/**
	 * 机构会员注册
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int insert_jg(ELUser elUser) throws ElException;
	/**
	 * 插入部门管理员
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int insert1(ELUser elUser) throws ElException;

	/**
	 * 用户修改
	 * 
	 * @param elUser
	 * @throws ElException
	 */
	public void update(ELUser elUser) throws ElException;

	/**
	 * 用户删除
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void delete(int id) throws ElException;

	/**
	 * 用户搜索
	 * 
	 * @param username
	 * @param realname
	 * @param email
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	/**
	 * 用户搜索
	 * 
	 * @param username
	 * @param realname
	 * @param email
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
//	public List<ELUser> getUserByDepId(int depid, int subdep, ELUser eu,
//			int pageNow, int pageSize) throws ElException;

	public List<ELUser> getUserByDepId(int depid, int subdep, ELUser eu)
			throws ElException;

//	public List<ELUser> getUserByUserId(int userid, ELUser eu, int pageNow,
//			int pageSize) throws ElException;

//	public int getUserByUserIdSize(int userid, ELUser eu) throws ElException;

//	public List<ELUser> getUserByUserId(int userid, int roleid, ELUser eu,
//			int pageNow, int pageSize) throws ElException;

//	public int getUserByUserIdSize(int userid, int roleid, ELUser eu)
//			throws ElException;

//	public List<ELUser> getUserByUserId3(Department depTree, int depid,
//			int role, int userid, int roleid, ELUser eu, int pageNow,
//			int pageSize) throws ElException;

//	public int getUserByUserIdSize3(Department depTree, int depid, int role,
//			int userid, int roleid, ELUser eu) throws ElException;

	/**
	 * 用户数量
	 * 
	 * @param depid
	 * @param username
	 * @param realname
	 * @param email
	 * @return
	 * @throws ElException
	 */
	public int getUserByDepIdSize(int depid, int subdep, ELUser eu)
			throws ElException;

	public List<ELUser> getVUserByDepId(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException;

	/**
	 * 用户数量
	 * 
	 * @param depid
	 * @param username
	 * @param realname
	 * @param email
	 * @return
	 * @throws ElException
	 */
	public int getVUserByDepIdSize(int depid, int subdep, ELUser eu)
			throws ElException;

	/**
	 * 列出部门的直接用户
	 * 
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getEUsByDepid(int depid) throws ElException;

	/**
	 * 设置用户权限
	 * 
	 * @param uid
	 * @param role
	 * @throws ElException
	 */
	public void setEURole(int uid, int role) throws ElException;

	public void alterMyInfo(ELUser elUser) throws ElException;

	public void setStation(int id, int station) throws ElException;

	public void alterMyPwd(ELUser elUser) throws ElException;

	public boolean checkPwd(int id, String thePwd) throws ElException;

	public List<ELUser> getEUsByRole() throws ElException;

	public boolean checkUsername(String username) throws ElException;

	public int getUserByDepIdSize(int depid, int subdep, ELUser eu, int role)
			throws ElException;

	public List<ELUser> getUserByDepId(int depid, int subdep, ELUser eu,
			int role, int pageNow, int pageSize) throws ElException;

	public void alterUserHead(ELUser elUser) throws ElException;

	public boolean checkHasFblock(int userid) throws ElException;

	public boolean checkSfzandusername(String username, String userno)
			throws ElException;

	// 当前在线用户数
	public void getFlowUser() throws ElException;

	// 当前在线用户数减少
	public void updateFlowUser() throws ElException;

	/**
	 * 分配学员
	 * 
	 * @param depTree
	 * @param depid
	 * @throws ElException
	 * @author HeweiCheng
	 */
	public List<ELUser> getDistributionStudents(Department depTree,Station stTree, int depid,
			ELUser eu, int role, int pageNow, int pageSize) throws ElException;

	/**
	 * 分配学员Count
	 * 
	 * @param depTree
	 * @param depid
	 * @throws ElException
	 * @author HeweiCheng
	 */
	public int getDistributionStudentsCount(Department depTree, Station stTree,int depid,
			ELUser eu, int role) throws ElException;

	/**
	 * 分配所有学员
	 * 
	 * @param depTree
	 * @param depid
	 * @throws ElException
	 * @author HeweiCheng
	 */
//	public List<ELUser> getDistributionStudents(Department depTree, int depid,
//			ELUser eu, int role) throws ElException;

	/**
	 * 查询试题库树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(QuestionLib qlbTree, boolean config)
			throws ElException;

	/**
	 * 查询课程类型树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(CourseType ctype, boolean config)
			throws ElException;

	/**
	 * 查询试卷库树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(ExamPaperLib examPaperLib, boolean config)
			throws ElException;

	/**
	 * 查询培训班类别树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(ElClType cltypeTree, boolean config)
			throws ElException;

	/**
	 * 查询考场树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(EroomLib eroomLibTree, boolean config)
			throws ElException;
	/**
	 * 查询词汇树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(Word wordsTree, boolean config)
			throws ElException;

	/**
	 * 查询部门树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(Department depTree, boolean config)
			throws ElException;

	/**
	 * 查询素材库树的所有id
	 * 
	 * @param qlibTree
	 * @throws ElException
	 */
	public List getTreeAllId(StuffLib stuffTree, boolean config)
			throws ElException;

	/**
	 * 给用户试卷树赋权
	 * 
	 * @param chkstr
	 * @param userId
	 * @return
	 * @throws ElException
	 */
	public String userGrantOnQlibTree(String chkstr[], int userId,
			List treeAllId) throws ElException;

	/**
	 * 给用户试卷树赋权（删除已有的，重新赋权）
	 * 
	 * @param chkstr
	 * @param userId
	 * @return
	 * @throws ElException
	 */
	public String userGrantOnQlibTree(String chkstr[], int userId,
			String treeType) throws ElException;

	/**
	 * 查询新闻类型树的所有id
	 */
	public List getTreeAllId(NewsType ntypeTree, boolean config)
			throws ElException;

	/**
	 * 查询知识树的所有id
	 */
	public List getTreeAllId(KnowledgeType userTree, boolean config)
			throws ElException;

	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
			int role, int pageNow, int pageSize) throws ElException;

	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
			int role) throws ElException;

	public int getUserByDepIdSize2(int depid, int subdep, ELUser eu, int role)
			throws ElException;

	/**
	 * 更该用户的开通状态
	 * 
	 * @param userid
	 * @param valid
	 * @throws ElException
	 */
	public void updateValid(int userid, int valid) throws ElException;

	/**
	 * (non-Javadoc) 获取不指定角色的用户
	 * 
	 * @see com.sopia.duman.dao.UserDao#getUserByDepId(int, int,
	 *      com.sopia.duman.entities.ELUser, int, int)
	 */
//	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
//			int pageNow, int pageSize) throws ElException;

//	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu)
//			throws ElException;

//	public int getUserByDepIdSize2(int depid, int subdep, ELUser eu)
//			throws ElException;

	/**
	 * 根据类别查询数据
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid(int typeid, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 根据类别查询数据
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid(int typeid) throws ElException;

	/**
	 * 根据类别查询数据数量
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public int getBaseDatatByTypeidCount(int typeid) throws ElException;

	/**
	 * 根据id查询数据
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public BaseDatat getBaseDatatById(int id) throws ElException;

	/**
	 * 根据id查询数据，返回id的名字串
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public String getBaseDatatInId(String ids) throws ElException;

	/**
	 * 添加基础数据
	 * 
	 * @param bd
	 * @throws ElException
	 */
	public void addBaseDb(BaseDatat bd) throws ElException;

	/**
	 * 基础数据排序
	 * 
	 * @param typeid
	 * @param sortid
	 * @param upordown
	 * @throws ElException
	 */
	public void sortBaseDbs(int typeid, int sortid, int upordown)
			throws ElException;

	/**
	 * 删除基础数据
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void delBaseDb(int id) throws ElException;
	/**检测是否包含帐号
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public boolean checkHasUser(int id,String col) throws ElException;
	/**
	 * Description: 批量导入
	* @Version1.0 2012-7-26 下午07:11:25 by 闻益舜（wenyishun110@163.com）创建
	 * @throws ElException
	 */
	public void impBaseDb() throws ElException;

	/**
	 * 编辑基础数据
	 * 
	 * @param bd
	 * @throws ElException
	 */
	public void updateBaseDb(BaseDatat bd) throws ElException;

	/**
	 * 验证基础数据库工种
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBase(String name, int type) throws ElException;

	/**
	 * 身份证验证
	 * 
	 * @param card
	 * @return
	 * @throws ElException
	 */
	public boolean checkCard(String card) throws ElException;

	/**
	 * 添加用户登录信息
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public void addUserLoginInfo(MyLogin myLogin) throws ElException;

	/**
	 * 查询所有用户登录信息
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public List<MyLogin> getAllUserLoginInfo(MyLogin myLogin, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 查询所有用户登录信息数量
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public int getAllUserLoginInfoCount(MyLogin myLogin) throws ElException;

	/**
	 * 删除所有用户登录信息（搜索结果）
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public void delUserLoginInfo(MyLogin myLogin) throws ElException;

	/**
	 * 根据用户名获取该用户最后一次的登录信息
	 * 
	 * @param name
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public MyLogin getSessionUserLoginInfo(int userid) throws ElException;

	/**
	 * 记录用户退出登录的时间
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void updateSessionUserExittime(int userid) throws ElException;

	/**
	 * 根据部门获取部门用户（包含下级）
	 */
	public List<ELUser> getUserByDepId(int depid) throws ElException;

	/**
	 * 更新基础数据的类别
	 * 
	 * @param map
	 * @throws ElException
	 */
	public void updateBasedbType(Map map) throws ElException;

	/**
	 * 根据类别查询数据(分页)
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid2(int typeid, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 获取所有基础数据类别
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataType> getAllBaseDataType(int pageNow, int pageSize)
			throws ElException;

	/**
	 * 获取所有基础数据类别
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataType> getAllBaseDataType() throws ElException;

	/**
	 * 更新工种字段到基础数据表
	 * 
	 * @throws ElException
	 */
	public void updateBaseDb_jingzhong() throws ElException;

	/**
	 * Description: 按照部门节点，是否包含下级，用户 基本信息（账号，姓名，角色等）搜索相关用户
	 * 部门节点分为：1明确节点，和分配的多个节点作为子节点集合放到传入的节点dep 中，交由传入dao处理
	 * 此方法带分页
	* @Version1.0 2012-6-21 上午09:17:23 by 闻益舜（wenyishun110@163.com）创建
	 * @param dep
	 * @param subdep
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUsers(ElNode dep,ElNode sta, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException;
	/**
	 * 外经贸用户列表查询条件不带岗位
	 * @param dep
	 * @param sta
	 * @param subdep
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> wjm_listUsers(ElNode dep, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException;
	/**
	 * 外经贸用户列表查询条件带岗位
	 * @param dep
	 * @param sta
	 * @param subdep
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> wjm_listUsers(ElNode dep,ElNode sta, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException;
	/**
	 * Description: 按照部门节点，是否包含下级，用户 基本信息（账号，姓名，角色等）搜索相关用户
	 * 部门节点分为：1明确节点，和分配的多个节点作为子节点集合放到传入的节点dep 中，交由传入dao处理
	 * 此方法全部人员
	* @Version1.0 2012-6-21 上午09:17:23 by 闻益舜（wenyishun110@163.com）创建
	 * @param dep
	 * @param subdep
	 * @param eu
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUsers(ElNode dep, int subdep, ELUser eu ) throws ElException;
	/**
	 * Description: 按照部门节点，是否包含下级，用户 基本信息（账号，姓名，角色等）搜索相关用户
	 * 部门节点分为：1明确节点，和分配的多个节点作为子节点集合放到传入的节点dep 中，交由传入dao处理
	 * 此方法获取总数
	* @Version1.0 2012-6-21 上午09:17:23 by 闻益舜（wenyishun110@163.com）创建
	 * @param dep
	 * @param subdep
	 * @param eu
	 * @return
	 * @throws ElException
	 */
	public int listUsersSize(ElNode dep,ElNode sta, int subdep, ELUser eu)
			throws ElException;
	/**
	 * 外经贸用户列表数量查询条件不带岗位
	 * @param dep
	 * @param sta
	 * @param subdep
	 * @param eu
	 * @return
	 * @throws ElException
	 */
	public int wjm_listUsersSize(ElNode dep, int subdep, ELUser eu)
	throws ElException;
	/**
	 * 外经贸用户列表数量查询条件带岗位
	 * @param dep
	 * @param sta
	 * @param subdep
	 * @param eu
	 * @return
	 * @throws ElException
	 */
	public int wjm_listUsersSize(ElNode dep,ElNode sta, int subdep, ELUser eu)
	throws ElException;
	/**
	 * 检测用户是否在部门里面
	 * @param map
	 * @throws ElException
	 */
	public boolean checkUserIsInDep(int userid,String depIds) throws ElException;
	/**
	 * 检测用户是否创建过课程是否有学习考试（用于真假删除）
	 * @param courseid
	 * @throws ElException
	 */
	public boolean checkElUserIsUse(int userid) throws ElException;
	/**
	 * 检测此身份证是否已经被其他用户使用
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserShenfenzheng(String shenfenzheng,int userid) throws ElException;
	
	
	/**
	 * 检测此用户名在数据库中是否存在
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserShenfenzhengIsUniqune(String userName,int id) throws ElException;
	
	/**
	 * 根据身份证获取用户信息
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */	
	public ELUser getEluserByShenfenzhang(String shenfenzheng) throws ElException;
	/**
	 *  根据名称和类别来获取基础数据
	 * @param typeid
	 * @param basevalue
	 * @return
	 * @throws ElException
	 */
	public BaseDatat getBaseDatatByBasevalue(int typeid,String basevalue) throws ElException;
	/**
	 * 验证基础数据编号是否重复
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBaseBh(String bh, int type) throws ElException;
	/**
	 * 验证基础数据库名称
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBase(String name, int type,int id) throws ElException;
	/**
	 * 验证基础数据编号是否重复
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBaseBh(String bh, int type,int id) throws ElException;
	/**
	 * 获取用户的功能权限
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ElFunc> getEluserFunc(int userid) throws ElException;
	/**
	 * 根据类别查询数据(分页)
	 * 非超级管理员调用
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid2(int userid,int typeid, int pageNow,
			int pageSize) throws ElException;
	/**
	 * 根据类别和创建者查询数据数量
	 * 非超级管理员调用
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public int getBaseDatatByTypeidCount(int userid,int typeid) throws ElException;
	/**
	 * 根据类别和创建者查询数据（只显示自己创建的和超级管理员创建的数据）
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid(int typeid,int userid) throws ElException;
	/**
	 * 根据类别查询数据（只显示超级管理员创建的数据）
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeidc(int typeid) throws ElException;
	
	/**
	 * 根据userid获取所属三级部门节点的userid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getSuoshuDepUserIdByDepid(int userid,int depid) throws ElException;
	
	/**
	 * 根据userid获取对应会员级别
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getForumUseBaseDataIdByfblockid(int fblockid ) throws ElException;
	
	/**
	 * 更新测评进度
	 * @param userid
	 * @param cepingjindu
	 * @throws ElException
	 */
	public void updateCepingjinduByUserid(int userid,String cepingjindu) throws ElException;
	
	/**
	 * 获取目前的在线用户数量
	 * @return
	 * @throws ElException
	 */
	public int getTheCurrentOnlineUsersSize() throws ElException ;
	/** 
	* 检验用户登陆失败次数
	 * @param userid
	 * @param number
	 * @return
	 * @throws ElException
	 */
	public boolean checkLogonFailureNumber(int userid,int number) throws ElException ;
	/**
	 * 插入登陆失败信息
	 * @param myLogin
	 * @throws ElException
	 */
	public void insertLoingFailure(MyLogin myLogin) throws ElException;
	
	public List<ELUser> getUserByUserId3(Department depTree ,int depid ,int role ,int userid, int roleid, ELUser eu, int pageNow,
			int pageSize) throws ElException;
	
	/**
	 * 修改用户角色
	 * @param elUser
	 * @param roleid
	 * @throws ElException
	 */
	public void alterUserRole(ELUser elUser,int roleid) throws ElException;
	
	public int getUserByUserIdSize3(Department depTree ,int depid ,int role ,int userid, int roleid, ELUser eu) throws ElException;
	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu) throws ElException ;
	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException ;
	
	public int getUserByDepIdSize2(int depid, int subdep, ELUser eu)
	throws ElException;
	
	/**
	 * 按用户id得到用户信息2（北京二次开发）
	 * 
	 * @param un
	 * @return
	 * @throws ElException
	 */
	public ELUser getUserById2(int id) throws ElException;
	
	/**
	 * 北京市卫生局
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ELUser getUserById_cisco(int id) throws ElException;
	/**
	 * 外经贸
	 */
	public ELUser getUserById_wjm(int id) throws ElException;
	/**
	 * 用户修改   -北京二次开发
	 * 
	 * @param elUser
	 * @throws ElException
	 */
	public void update2(ELUser elUser) throws ElException;
	
	public void update_cisco(ELUser elUser) throws ElException;
	//外经贸
	public void update_wjm(ELUser elUser) throws ElException;
	public void alterMyInfo2(ELUser elUser) throws ElException;
	
	/**
	 * 锁定账号解锁
	 * @param userid
	 * @throws ElException
	 */
	public void deleteLoingFailure(int userid) throws ElException;
	
	/**
	 * 锁定用户
	 */
	public void insertLoingFailure2(MyLogin myLogin) throws ElException;
	
	/**
	 * 用户插入-北京二次开发
	 * 
	 * @param elUser
	 * @throws ElException
	 */
	public void insert2(ELUser elUser) throws ElException;
	
	/**
	 * 按照role名字获取roleid
	 * @param roleName
	 * @return
	 * @throws ElException
	 */
	public int getEURoleByName(String roleName) throws ElException ;
	
	/**
	 * 验证身份证是否存在
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public boolean checkShenfenzhengIsExsit(String shenfenzheng) throws ElException;
	
	/**
	 * 插入验证码
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public void insertYzCode(String movephone,String content) throws ElException;
	
	/**
	 * 修改验证码
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public void updateYzCode(String movephone,String content) throws ElException ;
	
	/**
	 * 查询验证码
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public int searchYzCode(String movephone,String content) throws ElException ;
	
	/**
	 * 发送短信
	 */
	public String sendMsg(String movephone,String content,String yzcode) throws ElException ;
	
	/**
	 * 
	 */
	public int checkMsg(String movephone,String yzcode) throws ElException ;
	

	/**
	 * 根据姓名查询
	 */
	public boolean checkRealname(String realname)throws ElException;
	
	/**
	 * 根据姓名、课程id、培训班id查询该用户是否已经被分配
	 * @param realname
	 * @param courseid
	 * @param elclassid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyCourse(String realname,int courseid,int elclassid)throws ElException;
	
	public ELUser getUserByRealname(String realname)throws ElException;
	/**
	 *  根据部门Id获得该部门管理员Id
	 */
	public int getUserIdByDepid(int depid)throws ElException;
	
	//sd1230-------------------------
	/**
	 * Description: 按照部门节点，是否包含下级，用户 基本信息（账号，姓名，角色等）搜索相关用户
	 * 部门节点分为：1明确节点，和分配的多个节点作为子节点集合放到传入的节点dep 中，交由传入dao处理
	 * 此方法带分页
	* @Version1.0 2012-6-21 上午09:17:23 by 闻益舜（wenyishun110@163.com）创建
	 * @param dep
	 * @param subdep
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUsers_sd(ElNode dep,ElNode sta, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException;
	
	/**
	 * Description: 按照部门节点，是否包含下级，用户 基本信息（账号，姓名，角色等）搜索相关用户
	 * 部门节点分为：1明确节点，和分配的多个节点作为子节点集合放到传入的节点dep 中，交由传入dao处理
	 * 此方法获取总数
	* @Version1.0 2012-6-21 上午09:17:23 by 闻益舜（wenyishun110@163.com）创建
	 * @param dep
	 * @param subdep
	 * @param eu
	 * @return
	 * @throws ElException
	 */
	public int listUsersSize_sd(ElNode dep,ElNode sta, int subdep, ELUser eu)
			throws ElException;
	
	
	/**
	 * 山东项目
	
	* @Title: insert_sd  
	
	* @Description: TODO 
	
	* @param @param elUser
	* @param @return
	* @param @throws ElException      
	
	* @return int     
	
	* @throws
	 */
	public int insert_sd(ELUser elUser) throws ElException ;
	
	//山东项目检测用户是否选班
	public boolean isCheckElClass(ELUser elUser) throws ElException ;
	//山东项目将学员插入培训班列表
	public int insert_sc(ELUser elUser) throws ElException ;
	//山东项目将试卷分配给学员
	public int insert_se(ELUser elUser,int epid,int roomid) throws ElException ;
	//山东项目将考场分配给学员
	public int insert_sr(ELUser elUser,int roomid) throws ElException ;
	//山东项目将课程分配给学员
	public int insert_sce(ELUser elUser,int courseid,String endtime,String starttime) throws ElException ;
	//sd0102
	/**
	 * 根据类别查询数据（只显示超级管理员创建的数据）
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeidc_sd(int typeid) throws ElException;
	
	//-------------------------sd0109------------------------------------------
	public int getBaseTypeCount() throws ElException;
	
	/**
	 * 添加基础数据
	 * 
	 * @param bd
	 * @throws ElException
	 */
	public void addBaseType(BaseDataType bd) throws ElException;
	
	/**
	 * 删除基础数据
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void delBaseType(int id) throws ElException;
    //sd021修改
	/**
	 * 获取当前登陆人数
	 * 
	 * @param id
	 * @throws ElException
	 */
	public int  loginCount() throws ElException;
	
	/**
	 * 20140827增加指纹识别
	 * @param eluser
	 * @return
	 * @throws ElException
	 */
	public int insertFingerInfo(ELUser eluser)throws ElException;
	/**
	 * 20140827增加指纹识别用户查询
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getUserByFingerInfo() throws ElException;
	
	public void checkUserIsExittime(int userid) throws ElException;
}
