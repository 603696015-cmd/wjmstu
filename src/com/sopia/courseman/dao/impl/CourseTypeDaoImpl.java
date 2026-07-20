package com.sopia.courseman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Typelrid;

public class CourseTypeDaoImpl extends ElNodeDao implements CourseTypeDao {
	private static final Log logger = LogFactory
			.getLog(CourseTypeDaoImpl.class);

	public int addCtype(CourseType ctype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseType> listType = new ArrayList<CourseType>();
		int id = 0;
		try {
			ct = DBConnection.getConnection();
//			addNode(ct, ctype, "course_type", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_ADD));
			ps.setString(1, ctype.getName());
			ps.setString(2, ctype.getDescription());
			ps.setInt(3, ctype.getParent().getId());
			ps.setInt(4, ctype.getLid());
			ps.setInt(5, ctype.getRid());
			ps.setString(6, ctype.getMainimg());
			ps.setInt(7, ctype.getIsshared());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('course_type') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select course_type_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next()){
				ctype.setId(rs.getInt(1));
				id = rs.getInt(1);
			}
			String sql="select id,name,parentid,isshared from course_type";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CourseType type=new CourseType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			if(ctype.getIsshared()!=null&&ctype.getIsshared()==1){
				String ids = createSharedId(listType,ctype.getParent().getId(),"");
				if(ids!=null&&!"".equals(ids)){
					ps = ct.prepareStatement("update newstype set isshared=1 where id in ("+ids+")");
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void alterCtype(CourseType ctype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			alterNode(ct, ctype, "course_type", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_ALTER));
			ps.setString(1, ctype.getName());
			ps.setString(2, ctype.getDescription());
			if(ctype.getParent()!=null){
				ps.setInt(3, ctype.getParent().getId());
			}else{
				ps.setInt(3, 0);
			}
			ps.setString(4, ctype.getMainimg());
			ps.setInt(5, ctype.getIsshared());
			ps.setInt(6, ctype.getId());
			ps.executeUpdate();
			if(ctype.getIsshared()!=null&&ctype.getIsshared()==1){
				updateParentShared(ctype.getId());
			}
		} catch (Exception e) {
			logger.error("修改课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 如果当前节点设置为共享节点 则把当前节点的所有父节点设置为共享节点
	 * @author jiahaijiang
	 * @throws ElException
	 */
	private void updateParentShared(int id)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseType> listType = new ArrayList<CourseType>();
		try {
			ct = DBConnection.getConnection();
			String sql="select id,name,parentid,isshared from course_type";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CourseType type=new CourseType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			String ids=null;
			for(CourseType type:listType){
			    if(type.getId()==id){
			    	ids=createSharedId(listType,type.getParent().getId(),"");
			    }	
			}
			if(ids!=null&&!"".equals(ids)){
				ps = ct.prepareStatement("update course_type set isshared=1 where id in ("+ids+")");
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("新闻公告栏目修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 找到所有父节点的ID
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private String createSharedId(List<CourseType> listType,int parentid,String ids)throws ElException {
		if(parentid==0){
			return ids;
		}
		if(!ids.equals("")){
			ids+=",";
		}
		for(CourseType type:listType){
			if(type.getId()==parentid){
				ids+=type.getId();
				return createSharedId(listType,type.getParent().getId(),ids);
			}
		}
		return "";
	}

	private void deleteCtype(Connection ct, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {// TODO 删除课程类别
			// 删除节点信息
//			deleteNode(ct, ctype, "course_type", "1 = 1");
			// 删除基本信息
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteCtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 删除课程类别
			ct = DBConnection.getConnection();
			int parentid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				parentid = rs.getInt(4);
			}
			rs.close();
			// 将该类别下课程设置成上级类别
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_COURSE_QUERY_BYCTID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {//得到该类别的所有课程
				PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
						.getSQL(CourseConstants.CTYPE_COURSE_CTYPE_SET));
				ps1.setInt(1, parentid);
				ps1.setInt(2, rs.getInt(1));
				ps1.executeUpdate();
				ps1.close();
			}
			rs.close();
			// 将该类别下类别设置成上级类别 update course_type set parentid = ? where parentid =?
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(CourseConstants.CTYPE_PARENT_SET));
			ps = ct.prepareStatement(" update course_type set parentid = ? where parentid =?");
			ps.setInt(1, parentid);
			ps.setInt(2, id);
			ps.executeQuery();
//			deleteCtype(ct, id);
			this.deleteCtype2Not(id);
		} catch (Exception e) {
			logger.error("删除课程类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

//	public void deleteCtypeAndSub(int id) throws ElException {
//		// TODO 删除课程类别
//	}
	
	public void deleteCtypeAndSub(int id) throws ElException {
		// TODO 删除课程类别
		//查出该类别的左右id，然后查出所有子类别，然后循环根据id删除子类别，删除所有类别下的课程最后删除该类别
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid=this.getLidRid(ct,id, "course_type");
			List<Integer> typelist=this.getTypeByLidRid(ct,typelrid.getLid(), typelrid.getRid(), "course_type");
			CourseDao cd=new CourseDaoImpl();
			for (int i = 0; i < typelist.size(); i++) {
				//根据id删除类别以及课程
				this.deleteCtype2(ct,typelist.get(i));
				cd.deleteCourseByTypeid(ct, typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 假删除课程类别
	 * @param id
	 * @throws ElException
	 */
	public void deleteCtypeAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid=this.getLidRid(ct,id, "course_type");
			List<Integer> typelist=this.getTypeByLidRid(ct,typelrid.getLid(), typelrid.getRid(), "course_type");
			CourseDao cd=((CourseDao)SpringContextUtil.getBean("courseDao"));
			for (int i = 0; i < typelist.size(); i++) {
				//根据id删除类别以及课程
				this.deleteCtype2Not(typelist.get(i));
				cd.deleteCourseByTypeidNot(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("假删除课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 获取树的左右id
	 * @param typeId
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public Typelrid getLidRid(Connection ct,int typeId,String tabName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Connection ct = null;
		Typelrid type=null;
		try {
			//ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select lid,rid from "+tabName+" where id=?");
			ps.setInt(1, typeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				type=new Typelrid(rs.getInt(1),rs.getInt(2));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		}
		return type;
	}
	/**
	 * 根据左右id获取树的id集合
	 * @param lid
	 * @param rid
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getTypeByLidRid(Connection ct,int lid,int rid,String tabName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Connection ct = null;
		List<Integer>  list=new ArrayList<Integer>();
		try {
			//ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from "+tabName+" where lid>=? and rid<=? ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		}
		return list;
	}
	/**
	 * 删除课程类别
	 * @param ct
	 * @param typeid
	 * @throws ElException
	 */
	private void deleteCtype2(Connection ct, int typeid) throws ElException {
		PreparedStatement ps = null;
		try {
			ps = ct.prepareStatement(ElQuerySql.getSQL(CourseConstants.CTYPE_DELETE));
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程类别信息出错！", e);
			throw new ElException(e);
		}
	}
	/**
	 * 更新课程类别状态
	 * @param ct
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteCtype2Not(int typeid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update course_type set status=1,lid=0,rid=0 where id = ?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新课程类别状态出错！", e);
			throw new ElException(e);
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public CourseType getCtypeById(int id) throws ElException {
		CourseType ctype = new CourseType();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ctype.setId(rs.getInt(1));
				ctype.setName(rs.getString(2));
				ctype.setDescription(rs.getString(3));
				ctype.setParent(new CourseType(rs.getInt(4), rs.getString(5)));
				ctype.setLid(rs.getInt(6));
				ctype.setRid(rs.getInt(7));
				ctype.setMainimg(rs.getString(8));
				ctype.setIsshared(rs.getInt(9));
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ctype;
	}

	public List<CourseType> getCtypeChilds(int parentid) throws ElException {
		List<CourseType> cts = new ArrayList<CourseType>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_QUERY_CHILD));
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseType ctype = new CourseType();
				ctype.setId(rs.getInt(1));
				ctype.setName(rs.getString(2));
				ctype.setParent(new CourseType(rs.getInt(3)));
				ctype.setLid(rs.getInt(4));
				ctype.setRid(rs.getInt(5));
				ctype.setMainimg(rs.getString(6));
				cts.add(ctype);
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cts;
	}

	public CourseType getCtypeRoot() throws ElException {
		CourseType ctype = new CourseType();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_QUERY_BYPID));
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			while (rs.next()) {
				ctype.setId(rs.getInt(1));
				ctype.setName(rs.getString(2));
				ctype.setParent(new CourseType(rs.getInt(3)));
				ctype.setLid(rs.getInt(4));
				ctype.setRid(rs.getInt(5));
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ctype;
	}

	public CourseType getCtypeTree(int from, int stop, boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		CourseType cltype = null;
		try {
			if (from == 0) {
				cltype = getCtypeRoot();
			} else {
				cltype = getCtypeById(from);
			}
			ct = DBConnection.getConnection();
			cltype
					.setChild(getChilds(ct, cltype.getId(), stop, containStop,
							0));
		} catch (Exception e) {
			logger.error("培训班类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	
	/**
	 * 构建课程类型树，树的查找分为有权限的和共享节点的。
	 * @author jiahaijiang
	 * @param from
	 * @param stop
	 * @param containStop
	 * @param isShared 是否需要加载共享节点，true需要，false不需要。
	 * @param permtype 管理权限和使用权限 这些就直接传表名方便处理
	 * @return
	 * @throws ElException
	 */
	public CourseType getCtypeTreeByPerOrShar(int from, int stop, boolean containStop,String userid,boolean isShared,String permtype)
	throws ElException {
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
			CourseType cltype = null; 
			List<Integer> perTypeid=new ArrayList<Integer>();//当前用户存放有权限的课程类型ID
			List<CourseType> typeList=new ArrayList<CourseType>();//所有课程类型
			try {
				ct = DBConnection.getConnection();
				//当前用户有权限的id
				String perSql="select tr.ctypeid from eluser r,"+permtype+" tr where r.id=tr.userid and tr.userid in ("+userid+")";
				ps = ct.prepareStatement(perSql);
				rs=ps.executeQuery();
				while(rs.next()){
					perTypeid.add(rs.getInt(1));
				} 
				//所有课程类型
				String typeSql=" select id,name,description,parentid,lid,rid,mainimg,isshared from course_type";
				ps = ct.prepareStatement(typeSql);
				rs=ps.executeQuery();
				while (rs.next()) {
					CourseType type = new CourseType();
					type.setId(rs.getInt(1));
					type.setName(rs.getString(2));
					type.setDescription(rs.getString(3));
					type.setParent(new CourseType(rs.getInt(4)));
					type.setLid(rs.getInt(5));
					type.setRid(rs.getInt(6));
					type.setMainimg(rs.getString(7));
					type.setIsshared(rs.getInt(8));
					typeList.add(type);
				}
				if (from == 0) {  
					cltype = getCtypeRootByPerOrShar(from,perTypeid,typeList,isShared);
				} else { 
					cltype = getCtypeByIdByPerOrShar(from,perTypeid,typeList,isShared);
				}
//				ct = DBConnection.getConnection();
//				cltype
//						.setChild(getChilds(ct, cltype.getId(), stop, containStop,
//								0));
			} catch (Exception e) {
				logger.error("培训班类别树失败！", e); 
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			} 
			return cltype;
	}
	
	/**
	 * 查询课程节点
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private CourseType getCtypeByIdByPerOrShar(int from,List<Integer> perTypeid,List<CourseType> typeList,boolean isShared)throws ElException{
		CourseType ctype = null;
		//查找根节点并且判断当前用户是否有权限
	    for(CourseType type:typeList){
	    	if(from==type.getId()){
	    		if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
	    			ctype=type;
	    			ctype.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,0));
	    		}
	    	}
	    }
		return ctype;
	}
	
	/**
	 * 查询课程类型 根节点
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private CourseType getCtypeRootByPerOrShar(int from,List<Integer> perTypeid,List<CourseType> typeList,boolean isShared) throws ElException {
		CourseType ctype = null;
		boolean isPer=false;//是否有权限
		//查找根节点并且判断当前用户是否有权限
	    for(CourseType type:typeList){
	    	//parentid为0是根节点
	    	if(from==type.getParent().getId()){
	    		if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
	    			ctype=type;
	    			ctype.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,0));  
	    		}
    		}
	    }
		return ctype;
	}
	
	/**
	 * 递归 构建树节点
	 * @author jiahaijiang
	 * @param typeid
	 * @param perTypeid
	 * @param typeList
	 * @param isShared
	 * @return
	 */
	private List<CourseType> findChildsType(int typeid,List<Integer> perTypeid,List<CourseType> typeList,boolean isShared,int level){
		List<CourseType> ctypeList = new ArrayList<CourseType>();
		level++; 
		for(CourseType type:typeList){
			//查找下级节点
			if(type.getParent().getId()==typeid){
				//如果有权限或是共享节点时
				if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
				 	type.setLevel(level);
					type.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,level));
					ctypeList.add(type); 
				}
			}
		}
		return ctypeList;
	}
	
	/**
	 * 判断是否有权限
	 * @author jiahaijiang
	 * @param typeid
	 * @param perTypeid
	 * @return
	 */
	private boolean isPerOrShared(int typeid,List<Integer> perTypeid){
		boolean isPerOrShared=false;
		for(Integer ptypeid:perTypeid){
			if(typeid==ptypeid){
				isPerOrShared=true;
			}
		}
		return isPerOrShared;
	}
  
	public void addCtypeUser(int userid, int ctypeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into course_typeuser (userid,ctypeid)values(?,?)");
			ps.setInt(1, userid);

			ps.setInt(2, ctypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkCtypeUser(int userid, int ctypeid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from course_typeuser where userid = ? and ctypeid =?");
			ps.setInt(1, userid);
			ps.setInt(2, ctypeid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return false;
	}

	public void deleteCtypeUser(int userid, int ctypeid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from course_typeuser where userid = ? and ctypeid =?");
			ps.setInt(1, userid);
			ps.setInt(2, ctypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> listUserByCtype(int ctypeid) throws ElException {
		List<ELUser> elusers = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.username,eu.realname from course_typeuser ctu "
							+ "left join eluser eu on eu.id= ctu.userid where ctu.ctypeid =?");
			ps.setInt(1, ctypeid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				elusers.add(eu);
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return elusers;
	}

	public List<CourseType> listCtypeByUser(int userid) throws ElException {
		List<CourseType> elusers = new ArrayList<CourseType>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ctu.ctypeid  from course_typeuser ctu where ctu.userid =?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseType eu = new CourseType(rs.getInt(1));
				elusers.add(eu);
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elusers;
	}

	/**
	 * 获取管理或权限的用户
	 * @author jiahaijiang
	 * @param permtype
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getOpUsers(String permtype, int typeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from "+permtype+" du left join eluser eu on eu.id = du.userid where du.ctypeid = ?");
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查询课程类型管理权限或使用权限的用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}
	/**
	 * 删除管理或使用权限的用户
	 * @author jiahaijiang
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void deleteOpusers(String type, int userid, int ctypeid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from " + type
					+ " where userid = ? and ctypeid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, ctypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 判断权限是否存在
	 * @author jiahaijiang
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @return
	 * @throws ElException
	 */
	public boolean checkOpUsers(String type, int userid, int ctypeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from " + type
					+ " where userid = ? and ctypeid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, ctypeid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查询课程权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 添加课程类型权限
	 * @author jiahaijiang
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void addOpusers(String type, int userid, int ctypeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into " + type
					+ "(userid,ctypeid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, ctypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加课程类型全程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
   
	//hwc1	 
	public CourseType getCourseLibTree(int userid, String op, int stopid,	boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection ct = null; 
//		CourseType dep = op.equals("op") ? new CourseType(1, "可操作的课程库")
//				: new CourseType(1, "可使用的课程库");
		CourseType dep = new CourseType(ElConstants.USER_OP_LIB, "可操作的课程库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ctypeid from course_" +op+ "_type where userid = ?");  
			ps.setInt(1, userid);
			rs = ps.executeQuery();       
			List<CourseType> list = new ArrayList<CourseType>(); 
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					CourseType depc = getCourseLibTree(depid, stopid, containStop,1);
					if(depc==null||depc.getId()==0){
						continue;
					}
					depc.setParent(dep);
					list.add(depc); 
					nlist.add(depc);
				}  
			}  
				dep.setChild(list);
				dep.setNchild(nlist);
		} catch (Exception e) {
			logger.error("查看课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	//hwc2
	private CourseType getCourseLibTree(int from, int stop, boolean containStop, int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		CourseType cltype = null;
		try {
			cltype = getCourseLibById(from);
			if(cltype==null||cltype.getId()==0){
				return cltype;
			}
			cltype.setLevel(level);
			ct = DBConnection.getConnection();
			cltype.setChild(getChilds(ct, cltype.getId(), stop, containStop,
					level));
		} catch (Exception e) {
			logger.error("课程类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	
	//hwc3
	public CourseType getCourseLibById(int id) throws ElException {
		CourseType courseLib = new CourseType();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select c1.id,c1.name,c1.description,c1.parentid,c2.name,c1.lid,c1.rid " +
							" from course_type c1 left join course_type c2 on c1.parentid = c2.id and c2.status!=1 where c1.id=? and c1.status!=1");
			ps.setInt(1, id);
			rs = ps.executeQuery(); 
			if (rs.next()) {
				courseLib.setId(rs.getInt(1));
				courseLib.setName(rs.getString(2));
				courseLib.setDescription(rs.getString(3));
				courseLib.setParent(new CourseType(rs.getInt(4), rs.getString(5)));
				courseLib.setLid(rs.getInt(6));
				courseLib.setRid(rs.getInt(7)); 
			} 
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courseLib;
	} 
	
	 
	public CourseType getCourseLibTree(int from, int stop, boolean containStop)	throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		CourseType cltype = null;
		try {
			if (from == 0) {
				cltype = getCourseLibRoot();
			} else {
				cltype = getCourseLibById(from);
			}
			ct = DBConnection.getConnection();
			cltype.setChild(getChilds(ct, cltype.getId(), stop, containStop,
							0));
		} catch (Exception e) {
			logger.error("课程类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	public CourseType getCourseLibRoot() throws ElException {
		CourseType courseLib = new CourseType();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,parentid,lid,rid from course_type where parentid=?");
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			while (rs.next()) {
				courseLib.setId(rs.getInt(1));
				courseLib.setName(rs.getString(2));
				courseLib.setParent(new CourseType(rs.getInt(3)));
				courseLib.setLid(rs.getInt(4));
				courseLib.setRid(rs.getInt(5));
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courseLib;
	}
	 
	private List<CourseType> getChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<CourseType> deps = new ArrayList<CourseType>();
		
//		PreparedStatement ps = ct.prepareStatement(ElQuerySql
//				.getSQL(CourseConstants.CTYPE_QUERY_BYPID));
		PreparedStatement ps = ct.prepareStatement("select id,name,parentid,lid,rid,isshared from course_type where parentid=? and status!=1 order by id");
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			CourseType dep = new CourseType(rstemp.getInt(1), rstemp
					.getString(2));
			// dep.setDescription(rstemp.getString(3));
			dep.setParent(new CourseType(rstemp.getInt(3)));
			dep.setLevel(level);
			dep.setLid(rstemp.getInt(4));
			dep.setRid(rstemp.getInt(5));
			dep.setIsshared(rstemp.getInt(6));
			if (dep.getId() != stop)
				dep.setChild(getChilds(ct, dep.getId(), stop, containStop,
						level));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		}
		ps.close();
		rstemp.close();
		return deps;
	}
	
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" delete from course_op_type where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" delete from course_use_type where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
//	private List<CourseType> getChilds(Connection ct, int from, int stop,
//			boolean containStop, int level,int id) throws Exception {
//		List<CourseType> deps = new ArrayList<CourseType>();
//		PreparedStatement ps = ct.prepareStatement(ElQuerySql
//				.getSQL(CourseConstants.CTYPE_QUERY_BYPID));
//		ps.setInt(1, from);
//		ResultSet rstemp = ps.executeQuery();
//		level++;
//		while (rstemp.next()) {
//			CourseType dep = new CourseType(rstemp.getInt(1), rstemp
//					.getString(2));
//			// dep.setDescription(rstemp.getString(3));
//			dep.setParent(new CourseType(rstemp.getInt(3)));
//			dep.setLevel(level);
//			dep.setIsshared(rstemp.getInt(6));
//			if (dep.getId() != stop)
//				if(dep.getId()==id){
//				}else{
//					dep.setChild(getChilds(ct, dep.getId(), stop, containStop,level));
//				}
//			if (!containStop && dep.getId() == stop) {
//
//			} else
//				deps.add(dep);
//		}
//		ps.close();
//		rstemp.close();
//		return deps;
//	}
} 
