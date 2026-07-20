package com.sopia.classman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Typelrid;
import com.sopia.knowledgeman.entities.KnowledgeType;

public class ElClTypeDaoImpl extends ElNodeDao implements ElClTypeDao {
	private static final Log logger = LogFactory.getLog(ElClTypeDaoImpl.class);

	public int addCltype(ElClType elcltype) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
//			addNode(ct, elcltype, "elclasstype", "1=1");
			ps = ct.prepareStatement(ElQuerySql.getSQL(ClassConstants.CLTYPE_ADD));
			ps.setString(1, elcltype.getName());
			ps.setString(2, elcltype.getDescription());
			ps.setInt(3, elcltype.getParent().getId());
			ps.setInt(4, elcltype.getLid());
			ps.setInt(5, elcltype.getRid());
			ps.setInt(6, elcltype.getIsshared());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('elclasstype') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select elclasstype_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next()){
				elcltype.setId(rs.getInt(1));
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("培训班添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void alterCltype(ElClType elcltype) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
//			alterNode(ct, elcltype, "elclasstype", "1=1");
			ps = ct.prepareStatement(ElQuerySql.getSQL(ClassConstants.CLTYPE_ALTER));
			ps.setString(1, elcltype.getName());
			ps.setString(2, elcltype.getDescription());
			ps.setInt(3, elcltype.getParent().getId());
			ps.setInt(4, elcltype.getIsshared());
			ps.setInt(5, elcltype.getId());
			
			ps.executeUpdate();
			if(elcltype.getIsshared()!=null&&elcltype.getIsshared()==1){
				updateParentShared(elcltype.getId());
			}
		} catch (Exception e) {
			logger.error("培训班修改失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 如果当前节点设置为共享节点 则把当前节点的所有父节点设置为共享节点
	 * @throws ElException
	 */
	private void updateParentShared(int id)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClType> listType = new ArrayList<ElClType>();
		try {
			ct = DBConnection.getConnection();
			String sql="select id,name,parentid,isshared from elclasstype";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ElClType type=new ElClType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			String ids=null;
			for(ElClType type:listType){
			    if(type.getId()==id){
			    	ids=createSharedId(listType,type.getParent().getId(),"");
			    }	
			}
			if(ids!=null&&!"".equals(ids)){
				ps = ct.prepareStatement("update elclasstype set isshared=1 where id in ("+ids+")");
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("培训班类型及上修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 找到所有父节点的ID
	 * @return
	 * @throws ElException
	 */
	private String createSharedId(List<ElClType> listType,int parentid,String ids)throws ElException {
		if(parentid==0){
			return ids;
		}
		if(!ids.equals("")){
			ids+=",";
		}
		for(ElClType type:listType){
			if(type.getId()==parentid){
				ids+=type.getId();
				return createSharedId(listType,type.getParent().getId(),ids);
			}
		}
		return "";
	}
	
	public void deleteCltype(ElClType elcltype) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
//			deleteNode(ct, elcltype, "elclasstype", "1=1");
			ps = ct.prepareStatement(ElQuerySql.getSQL(ClassConstants.CLTYPE_DELETE));
//			ps.setString(1,ClassConstants.CLASSTYPE_STATUS_DELETE_YES);
			ps.setInt(1, elcltype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("培训班删除失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新培训班类别状态
	 * @param cltypeid
	 * @throws ElException
	 */
	public void deleteCltypeNot(int cltypeid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update elclasstype set status=1,lid=0,rid=0 where id = ?");
			ps.setInt(1, cltypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新培训班类别状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public ElClType getCltypeTree(int from, int stop, boolean containStop/*,
			String status*/) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClType cltype = null;
		try {
			if (from == 0) {
				cltype = getCltypeRoot();
			} else {
				cltype = getClTypeById(from);
			}
			ct = DBConnection.getConnection();
			cltype.setChild(getChilds(ct, cltype.getId(), stop, containStop, 0/*, status*/)) ;
		} catch (Exception e) {
			logger.error("培训班类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}

	private List<ElClType> getChilds(Connection ct, int from, int stop,
			boolean containStop, int level/*, String status*/) throws Exception {
		List<ElClType> deps = new ArrayList<ElClType>();
//		PreparedStatement ps=ct.prepareStatement(ElQuerySql.getSQL(ClassConstants.CLTYPE_QUERY_BYPARENT));
		PreparedStatement ps=ct.prepareStatement("select id,name,description,parentid,lid,rid from elclasstype where parentid = ? and status!=1");
		ps.setInt(1, from) ;
//		ps.setString(2, "%"+status+"%");
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			ElClType dep = new ElClType(rstemp.getInt(1), rstemp
					.getString(2));
			dep.setDescription(rstemp.getString(3));
			dep.setParent(new KnowledgeType(rstemp.getInt(4)));
			dep.setLid(rstemp.getInt(5));
			dep.setRid(rstemp.getInt(6));
			dep.setLevel(level);
			if (dep.getId() != stop)
				dep.setChild(getChilds(ct,dep.getId(), stop,
						containStop, level/*, status*/));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		} 
		ps.close();
		rstemp.close();
		return deps;
	}

	public ElClType getClTypeById(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClType clt = new ElClType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				clt.setId(rs.getInt(1));
				clt.setName(rs.getString(2));
				clt.setDescription(rs.getString(3));
				clt.setParent(new ElClType(rs.getInt(4),rs.getString(5)));
				clt.setIsshared(rs.getInt(6));
				clt.setLid(rs.getInt(7));
				clt.setRid(rs.getInt(8));
			}
		} catch (Exception e) {
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return clt;
	}

	public ElClType getCltypeRoot() throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClType clt = new ElClType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_QUERY_BYPARENT));
			ps.setInt(1, 0);
//			ps.setString(2, "%%") ; 
			rs = ps.executeQuery();
			if (rs.next()) {
				clt.setId(rs.getInt(1));
				clt.setName(rs.getString(2));
				clt.setDescription(rs.getString(3));
				clt.setParent(new ElClType(rs.getInt(4)));
				clt.setLid(rs.getInt(5));
				clt.setRid(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.error("培训班修改失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return clt;
	}

	/**
	 * 构建培训班类型树，树的查找分为有权限的和共享节点的。
	 * @author luocw
	 * @param from
	 * @param stop
	 * @param containStop
	 * @param isShared 是否需要加载共享节点，true需要，false不需要。
	 * @param permtype 管理权限和使用权限 这些就直接传表名方便处理
	 * @return
	 * @throws ElException
	 */
	public ElClType getCltypeTreeByPerOrShar(int from, int stop, boolean containStop, int userid, boolean isShared, String permtype)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClType cltype = null;
		List<Integer> perTypeid=new ArrayList<Integer>();		//当前用户存放有权限的培训班类型ID
		List<ElClType> typeList=new ArrayList<ElClType>();	//所有培训班类型
		try {
			ct = DBConnection.getConnection();
			//当前用户有权限的id
			String perSql="select tr.ctypeid from eluser r,"+permtype+" tr where r.id=tr.userid and tr.userid=?";
			ps = ct.prepareStatement(perSql);
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			while(rs.next()){
				perTypeid.add(rs.getInt(1));
			}
			//所有培训班类型
			
//			String typeSql=" select id,name,description,parentid,lid,rid,mainimg,isshared from elclasstype";
			String typeSql=" select id, name, description, parentid, lid, rid, isshared from elclasstype";
			ps = ct.prepareStatement(typeSql);
			rs=ps.executeQuery();
			while (rs.next()) {
				ElClType type = new ElClType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setDescription(rs.getString(3));
				type.setParent(new ElClType(rs.getInt(4)));
				type.setLid(rs.getInt(5));
				type.setRid(rs.getInt(6));
//				type.setMainimg(rs.getString(7));
				type.setIsshared(rs.getInt(7));
				typeList.add(type);
			}
			
			if (from == 0) {
				cltype = getCtypeRootByPerOrShar(from,perTypeid,typeList,isShared);
			} else {
				cltype = getCtypeByIdByPerOrShar(from,perTypeid,typeList,isShared);
			}
//			ct = DBConnection.getConnection();
//			cltype
//					.setChild(getChilds(ct, cltype.getId(), stop, containStop,
//							0));
		} catch (Exception e) {
			logger.error("培训班类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	
	/**
	 * 查询培训班类型 根节点
	 * @author luocw
	 * @return
	 * @throws ElException
	 */
	private ElClType getCtypeRootByPerOrShar(int from,List<Integer> perTypeid,List<ElClType> typeList,boolean isShared) throws ElException {
		ElClType ctype = null;
		boolean isPer=false;//是否有权限
		//查找根节点并且判断当前用户是否有权限
	    for(ElClType type:typeList){
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
	 * 判断是否有权限
	 * @author luocw
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
	
	/**
	 * 递归 构建树节点
	 * @author luocw
	 * @param typeid
	 * @param perTypeid
	 * @param typeList
	 * @param isShared
	 * @return
	 */
	private List<ElClType> findChildsType(int typeid,List<Integer> perTypeid,List<ElClType> typeList,boolean isShared,int level){
		List<ElClType> ctypeList = new ArrayList<ElClType>();
		for(ElClType type:typeList){
			//查找下级节点
			if(type.getParent().getId()==typeid){
				//如果有权限或是共享节点时
				if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
					level++;
					type.setLevel(level);
					type.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,level));
					ctypeList.add(type);
				}
			}
		}
		return ctypeList;
	}
	
	/**
	 * 查询培训班节点
	 * @author luocw
	 * @return
	 * @throws ElException
	 */
	private ElClType getCtypeByIdByPerOrShar(int from,List<Integer> perTypeid,List<ElClType> typeList,boolean isShared)throws ElException{
		ElClType ctype = null;
		//查找根节点并且判断当前用户是否有权限
	    for(ElClType type:typeList){
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
	 * 获取管理或权限的用户
	 * @author luocw
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
			ps = ct.prepareStatement("select eu.id,eu.realname,eu.username from "+permtype+" du left join eluser eu on eu.id = du.userid where du.ctypeid = ?");
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查询培训班类型管理权限或使用权限的用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	/**
	 * 添加培训班类型权限
	 * @author luocw
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
			logger.error("添加培训班类型权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	/**
	 * 判断权限是否存在
	 * @author luocw
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
			logger.error("查询培训班权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	
	public void deleteOpusers(String optype, int userId, int cltypeId) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from " + optype
					+ " where userid = ? and ctypeid = ?");
			ps.setInt(1, userId);
			ps.setInt(2, cltypeId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("培训班类别可管理可使用人员删除失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	 
	public ElClType getClassLibTree(int userid, String op, int stopid,	boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection ct = null; 
//		ElClType dep = op.equals("op") ? new ElClType(1, "可操作培训班")
//				: new ElClType(1, "可使用培训班");
		ElClType dep = new ElClType(ElConstants.USER_OP_LIB, "可操作培训班");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ctypeid from class_" +op+ "_type where userid = ?");  
			ps.setInt(1, userid);
			rs = ps.executeQuery();       
			List<ElClType> list = new ArrayList<ElClType>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					ElClType depc = getClassLibTree(depid, stopid, containStop,1);
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
	 
	private ElClType getClassLibTree(int from, int stop, boolean containStop, int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClType cltype = null;
		try {
			cltype = getClassLibById(from);
			if(cltype==null||cltype.getId()==0){
				return cltype;
			}
			cltype.setLevel(level);
			ct = DBConnection.getConnection();
			cltype.setChild(getEChilds(ct, cltype.getId(), stop, containStop,level));
		} catch (Exception e) {
			logger.error("课程类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
 
	public ElClType getClassLibById(int id) throws ElException {
		ElClType courseLib = new ElClType();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select c1.id,c1.name,c1.description,c1.parentid,c2.name,c1.lid,c1.rid " +
							" from elclasstype c1 left join elclasstype c2 on c1.parentid = c2.id and c2.status!=1 where c1.id=? and c1.status!=1");
			ps.setInt(1, id);
			rs = ps.executeQuery(); 
			if (rs.next()) {
				courseLib.setId(rs.getInt(1));
				courseLib.setName(rs.getString(2));
				courseLib.setDescription(rs.getString(3));
				courseLib.setParent(new ElClType(rs.getInt(4), rs.getString(5)));
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
	
	private List<ElClType> getEChilds(Connection ct, int from, int stop,Boolean containStop, int level) throws Exception { 
		List<ElClType> deps = new ArrayList<ElClType>();
		PreparedStatement ps=ct.prepareStatement("select id,name,parentid,lid,rid,isshared from elclasstype where parentid=? and status!=1");
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			ElClType dep = new ElClType(rstemp.getInt(1), rstemp.getString(2)); 
			dep.setParent(new ElClType(rstemp.getInt(3)));
			dep.setLevel(level);  
			dep.setLid(rstemp.getInt(4));
			dep.setRid(rstemp.getInt(5));
			dep.setIsshared(rstemp.getInt(6));
			if (dep.getId() != stop)
				dep.setChild(getEChilds(ct, dep.getId(), stop, containStop,level));
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
			ps = ct.prepareStatement(" delete from class_op_type where userid= ?");
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
			ps = ct.prepareStatement(" delete from class_use_type where userid= ?");
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
	 * 设置上级类别
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setCtypeparent(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update elclasstype set parentid=? where parentid =?");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 设置上级类别
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setClassparent(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update elclass set cltype=? where cltype =?");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据类别删除培训班
	 * @param ct
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteClassByTypeid(Connection ct,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Connection ct = null;
		try {
			//ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from elclass where cltype=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类别删除培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据类别更新培训班状态
	 * @param ct
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteClassByTypeidNot(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update elclass set status=9 where cltype=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类别更新培训班状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 删除培训班库
	 */
	public void deleteCtypeAndSub(int id) throws ElException {
		//查出该类别的左右id，然后查出所有子类别，然后循环根据id删除子类别，删除所有类别下的课程最后删除该类别
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid=this.getLidRid(ct,id, "elclasstype");
			List<Integer> typelist=this.getTypeByLidRid(ct,typelrid.getLid(), typelrid.getRid(), "elclasstype");
			for (int i = 0; i < typelist.size(); i++) {
				//根据id删除类别以及类别下的资源(先删资源)
				this.deleteClassByTypeid(ct, typelist.get(i));
				this.deleteCltype(new ElClType(typelist.get(i)));
			}
		} catch (Exception e) {
			logger.error("删除试卷库以及下级试卷库和试卷失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 假删除培训班库
	 * @param id
	 * @throws ElException
	 */
	public void deleteCtypeAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid=this.getLidRid(ct,id, "elclasstype");
			List<Integer> typelist=this.getTypeByLidRid(ct,typelrid.getLid(), typelrid.getRid(), "elclasstype");
			for (int i = 0; i < typelist.size(); i++) {
				//根据id删除类别以及类别下的资源(先删资源)
				this.deleteClassByTypeidNot(typelist.get(i));
				this.deleteCltypeNot(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("假删除培训班库失败！", e);
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
}
