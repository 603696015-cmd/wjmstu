package com.sopia.knowledgeman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.MyLogin;
import com.sopia.forumman.entities.Forum;
import com.sopia.knowledgeman.KnowledgeConstants;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.entities.DownloadInfo;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.questionman.entities.StuffLib;

public class KnowledgeDaoImpl implements KnowledgeDao {
	private static final Log logger = LogFactory.getLog(KnowledgeDaoImpl.class);

	public KnowledgeType getKltypeTree(int fromid, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		KnowledgeType kltype = null;
		try {
			if (fromid == 0) {
				//kltype = new KnowledgeType(0, "知识类别");
				//kltype = new KnowledgeType(0,"资料中心");
				kltype = getKltypeById(1);
			} else {
				kltype = getKltypeById(fromid);
			}
			ct = DBConnection.getConnection();
			kltype.setChild(listDepartmentsById(kltype.getId(), stopid, containStop, 0,
					ct));
		} catch (Exception e) {
			logger.error("获取知识类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kltype;
	}
	public KnowledgeType getKltypeTreeByPerOrShar(int from, int stop,
			boolean constop, String userid, boolean isShared, String permtype)
	throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		KnowledgeType cltype = null;
		List<Integer> perTypeid=new ArrayList<Integer>();//当前用户存放有权限的知识类别管理ID
		List<KnowledgeType> typeList=new ArrayList<KnowledgeType>();//所有知识类别管理
		try {
			ct = DBConnection.getConnection();
			//当前用户有权限的id
			String perSql="select tr.ctypeid from eluser r,"+permtype+" tr where r.id=tr.userid and tr.userid in ("+userid+")";
			ps = ct.prepareStatement(perSql);
			rs=ps.executeQuery();
			while(rs.next()){
				perTypeid.add(rs.getInt(1));
			}
			//所有新闻类型
			String typeSql=" select id,name,description,parentid,isshared from knowledgetype";
			ps = ct.prepareStatement(typeSql);
			rs=ps.executeQuery();
			while (rs.next()) {
				KnowledgeType type = new KnowledgeType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setDescription(rs.getString(3));
				type.setParent(new ElNode(rs.getInt(4)));
				type.setIsshared(rs.getInt(5));
				typeList.add(type);
			}

			if (from == 0) {
				cltype = getCtypeRootByPerOrShar(from,perTypeid,typeList,isShared);
			} else {
				cltype = getCtypeByIdByPerOrShar(from,perTypeid,typeList,isShared);
			}
//			ct = DBConnection.getConnection();
//			cltype
//			.setChild(getChilds(ct, cltype.getId(), stop, containStop,
//			0));
		} catch (Exception e) {
			logger.error("知识类别管理目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}

	private KnowledgeType getCtypeByIdByPerOrShar(int from,List<Integer> perTypeid,List<KnowledgeType> typeList,boolean isShared)throws ElException{
		KnowledgeType ctype = null;
		//查找根节点并且判断当前用户是否有权限
		for(KnowledgeType type:typeList){
			if(from==type.getId()){
				if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
					ctype=type;
					ctype.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,0));
				}
			}
		}
		return ctype;
	}
	private KnowledgeType getCtypeRootByPerOrShar(int from,List<Integer> perTypeid,List<KnowledgeType> typeList,boolean isShared) throws ElException {
		KnowledgeType ctype = null;
		boolean isPer=false;//是否有权限
		//查找根节点并且判断当前用户是否有权限
		for(KnowledgeType type:typeList){
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
	private List<KnowledgeType> findChildsType(int typeid,List<Integer> perTypeid,List<KnowledgeType> typeList,boolean isShared,int level){
		List<KnowledgeType> ctypeList = new ArrayList<KnowledgeType>();
		level++;
		for(KnowledgeType type:typeList){
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


	private List<KnowledgeType> listDepartmentsById(int parentid, int stopid,
			boolean isContainStop, int level, Connection ct) throws Exception {
		List<KnowledgeType> deps = new ArrayList<KnowledgeType>();
		PreparedStatement pstemp = ct
		.prepareStatement(KnowledgeConstants.QUERY_KLTYPE_CHILD_BYID);
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			KnowledgeType dep = new KnowledgeType(rstemp.getInt(1), rstemp
					.getString(2));
			dep.setDescription(rstemp.getString(3));
			dep.setParent(new KnowledgeType(rstemp.getInt(4)));
			dep.setLevel(level);
			if (dep.getId() != stopid)
				dep.setChild(listDepartmentsById(dep.getId(), stopid,
						isContainStop, level, ct));
			if (!isContainStop && dep.getId() == stopid) {

			} else
				deps.add(dep);
		}
		rstemp.close();
		pstemp.close();
		return deps;
	}

	public KnowledgeType getKltypeById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		KnowledgeType exLib= new KnowledgeType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KLTYPE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				exLib = new KnowledgeType(rs.getInt(1), rs
						.getString(2));
				exLib.setDescription(rs.getString(3));
				exLib
				.setParent(new KnowledgeType(rs.getInt(4), rs
						.getString(5)));
				exLib.setManager(new ELUser(rs.getInt(6), rs.getString(7)));
				exLib.setIsshared(rs.getInt(8));
				exLib.setLid(rs.getInt(9));
				exLib.setRid(rs.getInt(10));
			}
		} catch (Exception e) {
			logger.error("获取知识库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return exLib;
	}

	public void addKl(Knowledge kl) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		try {
			sql = "insert into knowledge(title,content,createtime,userid,kltypeid,valid,mainimg,wendang,swf ) values(?,empty_blob(),?,?,?,?,?,?,?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(KnowledgeConstants.KNOWLEDGE_ADD));
			ps.setString(1, kl.getTitle()); 
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, kl.getOwner().getId());
			ps.setInt(4, kl.getKltype().getId());
//			ps.setBoolean(6, kl.getValid());
			ps.setInt(5, 0); 
			ps.setString(6, kl.getMainimg());
			ps.setString(7, kl.getWendang());
			ps.setString(8, kl.getSwf());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob(ct,"knowledge_sequence","knowledge","id","content",kl.getContent(),"添加新闻失败");
			setblob.addContent(); 
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
				.prepareStatement("SELECT IDENT_CURRENT('knowledge') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			}else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
				.prepareStatement("select knowledge_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				kl.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("知识添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addKstuff(String addr, int kid, String title)
	throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("insert into knowledge_stuff(stuffaddr,knowid,title) values(?,?,?)");
			ps.setString(1, addr);
			ps.setInt(2, kid);
			ps.setString(3, title);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterKstuff(String title, int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("update knowledge_stuff set title=? where id = ?");
			ps.setString(1, title);
			ps.setInt(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<StuffLib> listKstuff(int klid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<StuffLib> stuffss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select cs.id,cs.title,cs.stuffaddr from knowledge_stuff cs where cs.knowid = ?");
			ps.setInt(1, klid);
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib s = new StuffLib(rs.getInt(1), rs.getString(2));
				s.setDescription(rs.getString(3));
				// s.setFileext(rs.getString(3));
				// s.setType(rs.getInt(4));
				stuffss.add(s);

			}
		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffss;
	}
	
	public List<StuffLib> listKstuff() throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<StuffLib> stuffss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select cs.id,cs.title,cs.stuffaddr,kl.id knowid from knowledge kl inner join knowledge_stuff cs on kl.id=cs.knowid");
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib s = new StuffLib(rs.getInt(1), rs.getString(2));
				s.setDescription(rs.getString(3));
				// s.setFileext(rs.getString(3));
				// s.setType(rs.getInt(4));
				stuffss.add(s);

			}
		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffss;
	}
	

	public StuffLib getKStuffLib(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		StuffLib s = new StuffLib();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select cs.id,cs.title,cs.stuffaddr  from knowledge_stuff cs where cs.id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = new StuffLib(rs.getInt(1), rs.getString(2));
				s.setDescription(rs.getString(3));

			}
		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	public void deleteKstuff(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from knowledge_stuff where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void alterKl(Knowledge kl) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql;
		try {
			sql = "update knowledge set title=?,content=empty_blob(),kltypeid = ?,mainimg=?,wendang=? ,swf=? where id = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(KnowledgeConstants.KNOWLEDGE_ALTER));
			ps.setString(1, kl.getTitle()); 
			ps.setInt(2, kl.getKltype().getId());
			ps.setString(3, kl.getMainimg());
			ps.setString(4, kl.getWendang());
			ps.setString(5, kl.getSwf());
			ps.setInt(6, kl.getId());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob("knowledge","id",kl.getId()+"","content",kl.getContent(),"知识修改出错",ct);
			setblob.updateContent();  
		} catch (Exception e) {
			logger.error("知识修改出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据资料类别获取资料id集合
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getKlByKltype(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> klts = new ArrayList<Integer>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select id from knowledge where kltypeid=? ");
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			while (rs.next()) {
				klts.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("根据资料类别获取资料id集合出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return klts;
	}
	/**
	 * 更新资料的父id
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateKnowledgePid(int pid,int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql;
		try {
			sql = " update knowledge set kltypeid=? where kltypeid=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新资料的父id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addKltype_dep(int kltypeid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(KnowledgeConstants.INSERT_KLTYPE_DEP);
			ps.setInt(1, kltypeid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<KnowledgeType> listKltsByDepId(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<KnowledgeType> klts = new ArrayList<KnowledgeType>();
		try {
			ct = DBConnection.getConnection();
			// ps =
			// ct.prepareStatement(ElQuerySql.getSQL(KnowledgeConstants.QUERY_KLTYPE_BYDEPID));
			ps = ct
			.prepareStatement("select id ,name from  knowledgetype klt ");
			// ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				klts.add(new KnowledgeType(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			logger.error("部门知识库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return klts;
	}
	public List<KnowledgeType> listKltsByDepIdNew(int userid,int shared) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<KnowledgeType> klts = new ArrayList<KnowledgeType>();
		try {
			ct = DBConnection.getConnection();
			// ps =
			// ct.prepareStatement(ElQuerySql.getSQL(KnowledgeConstants.QUERY_KLTYPE_BYDEPID));
			ps = ct
			.prepareStatement("select kn.id,kn.name from knowledgetype kn left join KNOWLEDGE_USE_TYPE ty on kn.id=ty.ctypeid where ty.userid=? or kn.isshared=?");
			 ps.setInt(1, userid);
			 ps.setInt(2,shared);
			rs = ps.executeQuery();
			while (rs.next()) {
				klts.add(new KnowledgeType(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			logger.error("部门知识库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return klts;
	}
	public List<KnowledgeType> listKltsByDepIdNew(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<KnowledgeType> klts = new ArrayList<KnowledgeType>();
		try {
			ct = DBConnection.getConnection();
			// ps =
			// ct.prepareStatement(ElQuerySql.getSQL(KnowledgeConstants.QUERY_KLTYPE_BYDEPID));
			ps = ct
			.prepareStatement("select kn.id,kn.name from knowledgetype kn left join KNOWLEDGE_USE_TYPE ty on kn.id=ty.ctypeid where ty.userid=? ");
			 ps.setInt(1, userid); 
			rs = ps.executeQuery();
			while (rs.next()) {
				klts.add(new KnowledgeType(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			logger.error("部门知识库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return klts;
	}
	public List<Department> listDepByKltypeId(int kltypeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(KnowledgeConstants.QUERY_DEP_BYKLTYPEID);
//			ps.setInt(1, kltypeid);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//			deps.add(new Department(rs.getInt(1), rs.getString(2)));
//			}
		} catch (Exception e) {
			logger.error("知识库部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public void alterKltype(KnowledgeType kltype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(KnowledgeConstants.ALTER_KLTYPE);
			ps.setString(1, kltype.getName());
			ps.setString(2, kltype.getDescription());
			ps.setInt(3, kltype.getParent().getId());
			ps.setInt(4, kltype.getManager().getId());
			ps.setInt(5, kltype.getIsshared());
			ps.setInt(6, kltype.getId());
			ps.executeUpdate();
			if(kltype.getIsshared()==1){
				updateParentShared(kltype.getId());
			}
		} catch (Exception e) {
			logger.error("知识库部门出错！", e);
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
		List<KnowledgeType> listType = new ArrayList<KnowledgeType>();
		try {
			ct = DBConnection.getConnection();
			String sql="select id,name,parentid,isshared from knowledgetype";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				KnowledgeType type=new KnowledgeType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			String ids=null;
			for(KnowledgeType type:listType){
				if(type.getId()==id){
					ids=createSharedId(listType,type.getParent().getId(),"");
				}	
			}
			if(ids!=null&&!"".equals(ids)){
				ps = ct.prepareStatement("update knowledgetype set isshared=1 where id in ("+ids+")");
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("知识类别管理目修改失败", e);
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
	private String createSharedId(List<KnowledgeType> listType,int parentid,String ids)throws ElException {
		if(parentid==0){
			return ids;
		}
		if(!ids.equals("")){
			ids+=",";
		}
		for(KnowledgeType type:listType){
			if(type.getId()==parentid){
				ids+=type.getId();
				return createSharedId(listType,type.getParent().getId(),ids);
			}
		}
		return "";
	}

	public void deleteKltypedep(int kltypeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(KnowledgeConstants.DELTET_DEP_BYKLTYPEID);
			ps.setInt(1, kltypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("知识库删除部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkKltype_dep(int kltypeid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(KnowledgeConstants.CHECK_KLTYPE_DEP);
			ps.setInt(1, kltypeid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("检测知识库部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public int addKltype(KnowledgeType klType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<KnowledgeType> listType = new ArrayList<KnowledgeType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(KnowledgeConstants.INSERT_KLTYPE);
			ps.setString(1, klType.getName());
			ps.setString(2, klType.getDescription());
			ps.setInt(3, klType.getParent().getId());
			ps.setInt(4, klType.getManager().getId());
			ps.setInt(5, klType.getIsshared());
			ps.executeUpdate();
			String sql="select id,name,parentid,isshared from knowledgetype";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				KnowledgeType type=new KnowledgeType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			if(klType.getIsshared()!=null&&klType.getIsshared()==1){
				String ids = createSharedId(listType,klType.getParent().getId(),"");
				if(ids!=null&&!"".equals(ids)){
					ps = ct.prepareStatement("update knowledgetype set isshared=1 where id in ("+ids+")");
					ps.executeUpdate();
				}
			}
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
				.prepareStatement("SELECT IDENT_CURRENT('knowledgetype') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
				.prepareStatement("select knowledgetype_sequence.currval from dual ");
				rs = ps.executeQuery();
			}
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Knowledge> listMyKls(int userid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_MY_LIST));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	public List<Knowledge> listMyKlsNew(int userid,String type,String title, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content," +
					" kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left " + 
			" join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ? ");
			if(type!=null&&!type.equals("")&&!type.equals("0")){
				buffer.append(" and klt.id="+type);
			}
			if(title!=null&&!title.equals("")){
				buffer.append(" and kl.title like '%"+title+"%'");
			}
			buffer.append(" order by kl.createtime) t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	
//	public List<Knowledge> listMyKlsNew(int userid ,KnowledgeType kltypeTree,int ktid ,String type,String title, int pageNow, int pageSize)
//	throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<Knowledge> kls = new ArrayList<Knowledge>();
//		try {
//			ct = DBConnection.getConnection();
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content," +
//					" kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left " +
//			" join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ? and kl.kltypeid in ("+createPerTypeId(kltypeTree,ktid)+")"); 
//			if(type!=null&&!type.equals("")&&!type.equals("0")&&!type.equals("-2")){
//				buffer.append(" and klt.id="+type);
//			}
//			if(title!=null&&!title.equals("")){
//				buffer.append(" and kl.title like '%"+title+"%'");
//			}
//			buffer.append(" order by kl.createtime desc) t where rownum <= ? ) where rn>=?");
//			ps = ct.prepareStatement(buffer.toString()); 
//			ps.setInt(1, userid);
//			ps.setInt(2, pageNow);
//			ps.setInt(3, pageSize);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
//				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
//				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
//				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
//				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
//				kls.add(kl);
//			}
//		} catch (Exception e) {
//			logger.error("获取知识库添加出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return kls;
//	}
	/**
	 * 查询我发布的资料
	 * @param userid
	 * @param kltypeTree
	 * @param knowledge
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Knowledge> listMyKlsNew(int userid ,KnowledgeType kltypeTree,Knowledge knowledge, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			Vector<Object> prams=new Vector<Object>();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content," +
					" kl.createtime,kl.modifytime,klt.id kltid,klt.name,kl.valid,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,kl.hot from knowledge kl " +
					" left join eluser eu on kl.userid=eu.id " +
					" left join department dep on eu.depid=dep.id " +
			" join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("knowledgetype", kltypeTree, true)+") klt on kl.kltypeid = klt.id where 1=1");
			if(userid>0){
				buffer.append(" and kl.userid = ? ");
				prams.add(userid);
			}
			if(knowledge!=null){
				if(knowledge.getTitle()!=null&&!"".equals(knowledge.getTitle())){
					buffer.append(" and kl.title like ?");
					prams.add("%"+StringUtil.toLikeStr(knowledge.getTitle())+"%");
				}
				if(knowledge.getStatus()==1){
					buffer.append(" and kl.valid = 1 ");
				}
				if(knowledge.getOwner()!=null){
					if(null!=knowledge.getOwner().getUsername()&&!"".equals(knowledge.getOwner().getUsername())){
						buffer.append(" and eu.username like ? ");
						prams.add("%"+StringUtil.toLikeStr(knowledge.getOwner().getUsername())+"%");
					}
					if(null!=knowledge.getOwner().getRealname()&&!"".equals(knowledge.getOwner().getRealname())){
						buffer.append(" and eu.realname like ? ");
						prams.add("%"+StringUtil.toLikeStr(knowledge.getOwner().getRealname())+"%");
					}
				}
			}
			buffer.append(" order by kl.createtime desc) t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString()); 
			System.out.println(buffer.toString());
			for (int i = 0; i < prams.size(); i++) {
				ps.setObject(i+1, prams.get(i));
			}
			ps.setInt(1+prams.size(), pageNow);
			ps.setInt(2+prams.size(), pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setStatus(rs.getInt(8));//valid
				kl.setOwner(new ELUser(rs.getInt(9),rs.getString(10),rs.getString(11)));
				kl.getOwner().setDepartment(new Department(rs.getInt(12),rs.getString(13)));
				kl.setHot(rs.getInt(14));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("查询我发布的资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

//	public int listMyklsSizeNew(int userid ,KnowledgeType kltypeTree,int ktid ,String type,String title) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("select  count(*) from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ? and kl.kltypeid in ("+createPerTypeId(kltypeTree,ktid)+")");
//			if(type!=null&&!type.equals("")&&!type.equals("0")&&!type.equals("-2")){
//				buffer.append(" and klt.id="+type);
//			}
//			if(title!=null&&!title.equals("")){
//				buffer.append(" and kl.title like '%"+title+"%'");
//			}
//			ps = ct.prepareStatement(buffer.toString()); 
//			ps.setInt(1, userid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("获取知识库添加出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return 0;
//
//	}
	/**
	 * 查询我发布的资料数量
	 * @param userid
	 * @param kltypeTree
	 * @param knowledge
	 * @return
	 * @throws ElException
	 */
	public int listMyklsSizeNew(int userid ,KnowledgeType kltypeTree,Knowledge knowledge) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Vector<Object> prams=new Vector<Object>();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select  count(*) from knowledge kl join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("knowledgetype", kltypeTree, true)+") klt on kl.kltypeid = klt.id " +
					" left join eluser eu on eu.id=kl.userid where 1=1 ");
			if(userid>0){
				buffer.append(" and kl.userid = ? ");
				prams.add(userid);
			}
			if(knowledge!=null){
				if(knowledge.getTitle()!=null&&!"".equals(knowledge.getTitle())){
					buffer.append(" and kl.title like ?");
					prams.add("%"+StringUtil.toLikeStr(knowledge.getTitle())+"%");
				}
				if(knowledge.getStatus()==1){
					buffer.append(" and kl.valid = 1 ");
				}
				if(knowledge.getOwner()!=null){
					if(null!=knowledge.getOwner().getUsername()&&!"".equals(knowledge.getOwner().getUsername())){
						buffer.append(" and eu.username like ? ");
						prams.add("%"+StringUtil.toLikeStr(knowledge.getOwner().getUsername())+"%");
					}
					if(null!=knowledge.getOwner().getRealname()&&!"".equals(knowledge.getOwner().getRealname())){
						buffer.append(" and eu.realname like ? ");
						prams.add("%"+StringUtil.toLikeStr(knowledge.getOwner().getRealname())+"%");
					}
				}
			}
			ps = ct.prepareStatement(buffer.toString()); 
			for (int i = 0; i < prams.size(); i++) {
				ps.setObject(i+1, prams.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询我发布的资料数量！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listMyklsSizeNew(int userid,String type,String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select  count(*) from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ?");
			if(type!=null&&!type.equals("")&&!type.equals("0")){
				buffer.append(" and klt.id="+type);
			}
			if(title!=null&&!title.equals("")){
				buffer.append(" and kl.title like '%"+title+"%'");
			}
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;

	}
	public int listMyklsSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_MY_LIST_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;

	}

	public void deleteKl(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("知识删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Knowledge getKlById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Knowledge kl = null;
		try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name,kl.readtime,kl.userid,eu.realname," +
				"kl.mainimg,kl.wendang ,kl.swf from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid " +
				"where kl.id = ?");
//		ps = ct.prepareStatement(ElQuerySql.getSQL(KnowledgeConstants.KNOWLEDGE_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setReadtime(rs.getInt(8));
				kl.setOwner(new ELUser(rs.getInt(9), rs.getString(10)));
				kl.setMainimg(rs.getString(11));
				kl.setWendang(rs.getString(12));
				kl.setSwf(rs.getString(13));
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kl;
	}

	public List<Knowledge> listKls(int depid, boolean depcon, int pageNow,
			int pageSize, String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			name = (name == null) ? "" : name.trim();

			ct = DBConnection.getConnection();
			if (depcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEP_SUB));
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setString(3, "%" + name + "%");
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEP));
				ps.setInt(1, depid);
				ps.setString(2, "%" + name + "%");
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.getOwner().setDepartment(
						new Department(rs.getInt(10), rs.getString(11)));
				kl.setReadtime(rs.getInt(12));
				kl.setHot(rs.getInt(13));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public int listKlsSize(int depid, boolean depcon, String name)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = (name == null) ? "" : name.trim();

			ct = DBConnection.getConnection();
			if (depcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				ps = ct
				.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEP_SUB_SIZE));
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setString(3, "%" + name + "%");

			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEP_SIZE));
				ps.setInt(1, depid);
				ps.setString(2, "%" + name + "%");

			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listKlsSize(int depid, int type, boolean depcon, String name)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = (name == null) ? "" : name.trim();

			ct = DBConnection.getConnection();
			if (depcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				ps = ct
				.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEPT_SUB_SIZE));
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setString(3, "%" + name + "%");
				ps.setInt(4, type);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEPT_SIZE));
				ps.setInt(1, depid);
				ps.setString(2, "%" + name + "%");
				ps.setInt(3, type);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Knowledge> listKls(int depid, int type, boolean depcon,
			int pageNow, int pageSize, String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			name = (name == null) ? "" : name.trim();

			ct = DBConnection.getConnection();
			if (depcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEPT_SUB));
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setString(3, "%" + name + "%");
				ps.setInt(4, type);
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYDEPT));
				ps.setInt(1, depid);
				ps.setString(2, "%" + name + "%");
				ps.setInt(3, type);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.getOwner().setDepartment(
						new Department(rs.getInt(10), rs.getString(11)));
				kl.setReadtime(rs.getInt(12));
				kl.setHot(rs.getInt(13));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public List<Knowledge> listKlsByType(int typeid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == 0) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYNOTYPE));
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYTYPE));
				ps.setInt(1, typeid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.setReadtime(rs.getInt(10));
				kl.setMainimg(rs.getString(11));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public int listKlsByTypeSize(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (typeid == 0) {
				ps = ct
				.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYNOTYPE_SIZE));
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYTYPE_SIZE));
				ps.setInt(1, typeid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Knowledge> listKlByHot(int hot, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYHOT));
			ps.setInt(1, hot);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.setReadtime(rs.getInt(10));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public List<Knowledge> listKlByReadTime(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYREADTIME));
			ps.setInt(2, pageNow);
			ps.setInt(1, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				/*
				 * kl.setContent(rs.getString(3)); kl.setCreatetime(new
				 * Date(rs.getTimestamp(4).getTime())); kl.setModifytime(new
				 * Date(rs.getTimestamp(5).getTime())); kl.setKltype(new
				 * KnowledgeType(rs.getInt(6),rs.getString(7))); kl.setOwner(new
				 * ELUser(rs.getInt(8),rs.getString(9)));
				 * kl.setReadtime(rs.getInt(10));
				 */
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public void setKlReadtime(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_READTIME_SET));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

//	public List<Knowledge> listKlsByTitle(String title, int pageNow,
//			int pageSize) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<Knowledge> kls = new ArrayList<Knowledge>();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYTITLE));
//			if (null != title)
//				title.trim();
//			else
//				title = "";
//			ps.setString(1, "%" + title + "%");
//			ps.setInt(2, pageNow);
//			ps.setInt(3, pageSize);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
//				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
//				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
//				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
//				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
//				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
//				kl.setReadtime(rs.getInt(10));
//				kls.add(kl);
//			}
//		} catch (Exception e) {
//			logger.error("获取知识库添加出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return kls;
//	}
	
	/**
	 * 根据标题获取资料集合
	 * @param title
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Knowledge> listKlsByTitle_list(String title, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYTITLE));
			if (null != title)
				title=title.trim();
			else
				title = "";
			System.out.println(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYTITLE));
			ps.setString(1, "%" + StringUtil.toLikeStr(title) + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent_list(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.setReadtime(rs.getInt(10));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public int listKlsByTitleSize(String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_LIST_BYTITLE_SIZE));
			if (null != title)
				title=title.trim();
			else
				title = "";
			ps.setString(1, "%" + StringUtil.toLikeStr(title) + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void setKlhotSet(int id, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_HOT_SET));
			ps.setInt(1, hot);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Knowledge> listShKls(int depid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_SH_LIST));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.getOwner().setDepartment(
						new Department(rs.getInt(10), rs.getString(11)));
				// kl.setReadtime(rs.getInt(12));
				// kl.setHot(rs.getInt(13));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public int listShKlsSize(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct
			.prepareStatement("select count(*)"
					+ "from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id "
					+ "left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid "
					+ "where dep.lid>=? and dep.rid<=? and kl.valid=0 ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Knowledge> listShmKls(int userid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(KnowledgeConstants.KNOWLEDGE_SHM_LIST));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.getOwner().setDepartment(
						new Department(rs.getInt(10), rs.getString(11)));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}

	public int listShmKlsSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select count(*)"
					+ "from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id "
					+ "left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid "
					+ "where klt.manager =? and kl.valid=0 ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void klShSet(int klid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("update knowledge set valid =1 where id  = ?");
			ps.setInt(1, klid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public List<Knowledge> listShKlsByPerOrShar(KnowledgeType kltypeTree,
			int depid, int pageNow, int pageSize,String type,String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,")
			.append(" kl.modifytime,klt.id kltid,klt.name,eu.id euid ,")
			.append("eu.realname,dep.id depid,dep.name depname,kl.valid ")
			.append(" from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id ")
			.append(" left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid ")
			//.append(" where dep.lid>=? and dep.rid<=? and kl.valid=0 and klt.id in ("+createPerTypeId(kltypeTree,kltypeTree.getId())+")");
			.append(" where dep.lid>=? and dep.rid<=? and klt.id in ("+createPerTypeId(kltypeTree,kltypeTree.getId())+")");
			if(type!=null&&!type.equals("")&&!type.equals("0")){
				buffer.append(" and klt.id ="+type);
			}
			if(title!=null&&!title.equals("")){
				buffer.append(" and kl.title like '%"+title+"%'");
			}
			buffer.append(" order by kl.createtime desc)t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.getOwner().setDepartment(
						new Department(rs.getInt(10), rs.getString(11)));
				// kl.setReadtime(rs.getInt(12));
				// kl.setHot(rs.getInt(13));
				//kl.setValid(rs.getInt("valid"));
				kl.setStatus(rs.getInt("valid"));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	public int listShKlsByPerOrSharSize(KnowledgeType kltypeTree,
			int depid,String type,String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select count(*) from (select kl.id,kl.title,kl.content,kl.createtime,")
			.append(" kl.modifytime,klt.id kltid,klt.name,eu.id euid ,")
			.append("eu.realname,dep.id depid,dep.name depname ")
			.append(" from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id ")
			.append(" left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid ")
			//.append(" where dep.lid>=? and dep.rid<=? and kl.valid=0 and klt.id in ("+createPerTypeId(kltypeTree,kltypeTree.getId())+") ");
			.append(" where dep.lid>=? and dep.rid<=? and klt.id in ("+createPerTypeId(kltypeTree,kltypeTree.getId())+") ");
			if(type!=null&&!type.equals("")&&!type.equals("0")){
				buffer.append(" and klt.id ="+type);
			}
			if(title!=null&&!title.equals("")){
				buffer.append(" and kl.title like '%"+title+"%'");
			}
			buffer.append(" order by kl.createtime desc)t ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 查询出从ctid开始的有权的新闻类型ID
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String createPerTypeId(KnowledgeType ctypeTree, int ctid){
		if(ctypeTree!=null){
			if(ctypeTree.getId()!=ctid){
				ctypeTree = getCourseTypeById(ctypeTree.getChild(),ctid,ctypeTree);
			}
			if(ctypeTree.getChild()!=null){
				return createTypeId(ctypeTree.getChild(),ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		}else{
			return null;
		}
	}

	/**
	 * 构建有权的新闻类型ID
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @return
	 */
	private String createTypeId(List<KnowledgeType> listType,int id){
		String ids=id+"";
		for(KnowledgeType type:listType){
			ids=ids+","+createTypeId(type.getChild(),type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * @author jiahaijiang
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private KnowledgeType getCourseTypeById(List<KnowledgeType> listType,int ctid,KnowledgeType ctypeTree){
		KnowledgeType  newsType=null;
		for(KnowledgeType type:listType){
			if(type.getId()!=ctid){
				newsType = getCourseTypeById(type.getChild(),ctid,ctypeTree);
				if(newsType!=null){
					return newsType;
				}
			}else{
				return type;
			}
		}
		return newsType;
	}
	//栏目推荐知识
	public List<Knowledge> listKlByHot2(int hot, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.hot = ? and kl.valid=1 order by kl.createtime");
			ps.setInt(1, hot);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.setReadtime(rs.getInt(10));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	//栏目热门知识
	public List<Knowledge> listKlByReadTime2(int pageNow, int pageSize)
	throws ElException {
PreparedStatement ps = null;
ResultSet rs = null;
Connection ct = null;
List<Knowledge> kls = new ArrayList<Knowledge>();
int i=0;
try {
	ct = DBConnection.getConnection();
	ps = ct.prepareStatement("select kl.id,kl.title from knowledge kl, knowledgetype klt  where kl.kltypeid = klt.id and kl.valid=1 and kl.hot=2 order by kl.readtime desc,kl.createtime desc");
	ps.setInt(1, pageNow);
	ps.setInt(2, pageSize);
	rs = ps.executeQuery();
	while (rs.next()) {
		if(i>7){
			break;
		}
		Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
		/*
		 * kl.setContent(rs.getString(3)); kl.setCreatetime(new
		 * Date(rs.getTimestamp(4).getTime())); kl.setModifytime(new
		 * Date(rs.getTimestamp(5).getTime())); kl.setKltype(new
		 * KnowledgeType(rs.getInt(6),rs.getString(7))); kl.setOwner(new
		 * ELUser(rs.getInt(8),rs.getString(9)));
		 * kl.setReadtime(rs.getInt(10));
		 */
		kls.add(kl);
	}
} catch (Exception e) {
	logger.error("获取知识库添加出错！", e);
	throw new ElException(e);
} finally {
	DBConnection.closeConnectInfo(ct, ps, rs);
}
return kls;
}
	//知识组合搜索
	public List<Knowledge> listCombinationKlsNew(int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
	throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			//
			ct = DBConnection.getConnection();
			String sqlstr="";
			if(knowledge==null){
				sqlstr+="select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = "+userid+"";
			}else{
				sqlstr+="select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where ";
				sqlstr+="kl.userid in(select id from eluser eu where";
				sqlstr+=knowledge.getOwner()==null?"":" eu.username like '%"+knowledge.getOwner().getUsername()+"%'";
				sqlstr+=knowledge.getOwner()==null?"":" and eu.realname like '%"+knowledge.getOwner().getRealname()+"%')";
				sqlstr+=knowledge.getKltype()==null?"":(knowledge.getKltype().getId()==-1||knowledge.getKltype().getId()==0)?"":" and klt.id="+knowledge.getKltype().getId();
				sqlstr+=(knowledge.getWendang()==null || knowledge.getWendang().equals(""))?"":" and kl.wendang like '%"+knowledge.getWendang()+"%'";
				sqlstr+=(knowledge.getBegintime()==null&&knowledge.getEndtime()==null)?"":" and to_date(to_char(kl.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+knowledge.getBegintime()+"','yyyy-mm-dd') and to_date('"+knowledge.getEndtime()+"','yyyy-mm-dd')";
			}
			sqlstr+=" order by kl.createtime) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	
	//知识组合搜索 hwc
	public List<Knowledge> listCombinationKlsNew(KnowledgeType kltypeTree,int ktid ,int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
	throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			//
			ct = DBConnection.getConnection();
			String sqlstr="";
			if(knowledge==null){
				//sqlstr+="select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name,kl.userid from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = "+userid+"";
				sqlstr+="select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name,kl.userid from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where 1=1";
			}else{
				sqlstr+="select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name,kl.userid from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where ";
				sqlstr+="kl.userid in(select id from eluser eu where";
				sqlstr+=knowledge.getOwner()==null?"":" eu.username like '%"+knowledge.getOwner().getUsername()+"%'";
				sqlstr+=knowledge.getOwner()==null?"":" and eu.realname like '%"+knowledge.getOwner().getRealname()+"%')";
				sqlstr+=knowledge.getKltype()==null?"":(knowledge.getKltype().getId()==-1||knowledge.getKltype().getId()==0)?"":" and klt.id="+knowledge.getKltype().getId();
				sqlstr+=(knowledge.getTitle()==null || knowledge.getTitle().equals(""))?"":" and kl.title like '%"+knowledge.getTitle()+"%'";
				sqlstr+=(knowledge.getBegintime()==null&&knowledge.getEndtime()==null)?"":" and to_date(to_char(kl.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+knowledge.getBegintime()+"','yyyy-mm-dd') and to_date('"+knowledge.getEndtime()+"','yyyy-mm-dd')";
			}
			sqlstr+=" and kl.kltypeid in ("+createPerTypeId(kltypeTree,ktid)+") order by kl.createtime) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			UserDao ud=new UserDaoImpl();
			ELUser user=null;
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				// kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				user=ud.getUserById(rs.getInt("userid"));
				kl.setOwner(user); 
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	//知识组合搜索行数
	public int listCombinationKlsNewCount(int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
		throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			//
			ct = DBConnection.getConnection();
			String sqlstr="";
			if(knowledge==null){
				sqlstr+="select count(*) from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = "+userid+"";
			}else{
				sqlstr+="select count(*) from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where ";
				sqlstr+="kl.userid in(select id from eluser eu where";
				sqlstr+=knowledge.getOwner()==null?"":" eu.username like '%"+knowledge.getOwner().getUsername()+"%'";
				sqlstr+=knowledge.getOwner()==null?"":" and eu.realname like '%"+knowledge.getOwner().getRealname()+"%')";
				sqlstr+=knowledge.getKltype()==null?"":(knowledge.getKltype().getId()==-1||knowledge.getKltype().getId()==0)?"":" and klt.id="+knowledge.getKltype().getId();
				sqlstr+=(knowledge.getWendang()==null || knowledge.getWendang().equals(""))?"":" and kl.wendang like '%"+knowledge.getWendang()+"%'";
				sqlstr+=(knowledge.getBegintime()==null&&knowledge.getEndtime()==null)?"":" and to_date(to_char(kl.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+knowledge.getBegintime()+"','yyyy-mm-dd') and to_date('"+knowledge.getEndtime()+"','yyyy-mm-dd')";
			}
			sqlstr+=" order by kl.createtime) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	//hwc
	public int listCombinationKlsNewCount(KnowledgeType kltypeTree,int ktid ,int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
	throws ElException{
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	try {
		//
		ct = DBConnection.getConnection();
		String sqlstr="";
		if(knowledge==null){
			sqlstr+="select count(*) from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = "+userid+"";
		}else{
			sqlstr+="select count(*) from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where ";
			sqlstr+="kl.userid in(select id from eluser eu where";
			sqlstr+=knowledge.getOwner()==null?"":" eu.username like '%"+knowledge.getOwner().getUsername()+"%'";
			sqlstr+=knowledge.getOwner()==null?"":" and eu.realname like '%"+knowledge.getOwner().getRealname()+"%')";
			sqlstr+=knowledge.getKltype()==null?"":(knowledge.getKltype().getId()==-1||knowledge.getKltype().getId()==0)?"":" and klt.id="+knowledge.getKltype().getId();
			sqlstr+=(knowledge.getTitle()==null || knowledge.getTitle().equals(""))?"":" and kl.title like '%"+knowledge.getTitle()+"%'";
			sqlstr+=(knowledge.getBegintime()==null&&knowledge.getEndtime()==null)?"":" and to_date(to_char(kl.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+knowledge.getBegintime()+"','yyyy-mm-dd') and to_date('"+knowledge.getEndtime()+"','yyyy-mm-dd')";
		}
		sqlstr+=" and kl.kltypeid in ("+createPerTypeId(kltypeTree,ktid)+") order by kl.createtime) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
		ps = ct.prepareStatement(sqlstr);
		rs = ps.executeQuery();
			rs = ps.executeQuery();
		rs = ps.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}
	} catch (Exception e) {
		logger.error("获取知识库添加出错！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	return 0;
}
	/**
	 * 资料检索列表
	 * @param kltypeTree
	 * @param ktid
	 * @param userid
	 * @param type
	 * @param knowledge
	 * @return
	 * @throws ElException
	 */
	public int listCombinationKlsNewCount2(KnowledgeType kltypeTree,int ktid ,int userid,String type,Knowledge knowledge)
	throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			//
			ct = DBConnection.getConnection();
			String sqlstr="";
			if(knowledge==null){
				sqlstr+="select count(*) from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where 1=1";
			}else{
				sqlstr+="select count(*) from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where ";
				sqlstr+="kl.userid in(select id from eluser eu where";
				sqlstr+=knowledge.getOwner()==null?"":" eu.username like '%"+knowledge.getOwner().getUsername()+"%'";
				sqlstr+=knowledge.getOwner()==null?"":" and eu.realname like '%"+knowledge.getOwner().getRealname()+"%')";
				sqlstr+=knowledge.getKltype()==null?"":(knowledge.getKltype().getId()==-1||knowledge.getKltype().getId()==0)?"":" and klt.id="+knowledge.getKltype().getId();
				sqlstr+=(knowledge.getTitle()==null || knowledge.getTitle().equals(""))?"":" and kl.title like '%"+knowledge.getTitle()+"%'";
				sqlstr+=(knowledge.getBegintime()==null&&knowledge.getEndtime()==null)?"":" and to_date(to_char(kl.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+knowledge.getBegintime()+"','yyyy-mm-dd') and to_date('"+knowledge.getEndtime()+"','yyyy-mm-dd')";
			}
			sqlstr+=" and kl.kltypeid in ("+createPerTypeId(kltypeTree,ktid)+") order by kl.createtime ";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
				rs = ps.executeQuery();
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
//知识栏目
	public List<KnowledgeType> listKnowledgeType()throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<KnowledgeType> list=new ArrayList<KnowledgeType>();
		try{
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select id,name from knowledgetype");
			rs=ps.executeQuery();
			while(rs.next()){
				KnowledgeType knowledgeType=new KnowledgeType();
				knowledgeType.setId(rs.getInt(1));
				knowledgeType.setName(rs.getString(2));
				list.add(knowledgeType);
			}
		}catch(Exception e){
			logger.error("获取知识库类别列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	//搜索知识
	public List<Knowledge> listKlsByType(Knowledge knowledge, int pageNow, int pageSize)
	throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		String sqlstr="";
		try {
			ct=DBConnection.getConnection();
			sqlstr+="select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime,kl.mainimg from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid ";
			if (knowledge.getKltype().getId() == 0||knowledge.getKltype().getId()==-1) {
				sqlstr+=" and kl.title like '%";
				sqlstr+=knowledge.getTitle()==null?"":knowledge.getTitle().trim()+"%'";

			}else{
				sqlstr+="and kl.kltypeid = ";
				sqlstr+=knowledge.getKltype().getId();
				sqlstr+=" and kl.title like '%";
				sqlstr+=knowledge.getTitle()==null?"":knowledge.getTitle().trim()+"%'";
			}
			sqlstr+=" and kl.valid=1 order by kl.createtime desc) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.setReadtime(rs.getInt(10));
				kl.setMainimg(rs.getString(11));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	
	public List<Knowledge> listKlsByType_list(Knowledge knowledge, int pageNow, int pageSize)
	throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		String sqlstr="";
		try {
			ct=DBConnection.getConnection();
			sqlstr+="select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime,kl.mainimg from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid ";
			if (knowledge.getKltype().getId() == 0||knowledge.getKltype().getId()==-1) {
				sqlstr+=" and kl.title like '%";
				sqlstr+=knowledge.getTitle()==null?"":knowledge.getTitle().trim()+"%'";

			}else{
				sqlstr+="and kl.kltypeid = ";
				sqlstr+=knowledge.getKltype().getId();
				sqlstr+=" and kl.title like '%";
				sqlstr+=knowledge.getTitle()==null?"":knowledge.getTitle().trim()+"%'";
			}
			sqlstr+=" and kl.valid=1 order by kl.createtime desc) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));
				kl.setContent(new OracleBlob().getContent_list(rs.getBlob(3)));
				kl.setCreatetime(new Date(rs.getTimestamp(4).getTime()));
				kl.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				kl.setKltype(new KnowledgeType(rs.getInt(6), rs.getString(7)));
				kl.setOwner(new ELUser(rs.getInt(8), rs.getString(9)));
				kl.setReadtime(rs.getInt(10));
				kl.setMainimg(rs.getString(11));
				kls.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	//搜索知识
	public int listKlsByTypeCount(Knowledge knowledge, int pageNow, int pageSize)
	throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			sqlstr+="select count(*) from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime,kl.mainimg from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid ";
			if (knowledge.getKltype().getId() == 0||knowledge.getKltype().getId()==-1) {
				sqlstr+=" and kl.title like '%";
				sqlstr+=knowledge.getTitle()==null?"":knowledge.getTitle().trim()+"%'";

			}else{
				sqlstr+="and kl.kltypeid = ";
				sqlstr+=knowledge.getKltype().getId();
				sqlstr+=" and kl.title like '%";
				sqlstr+=knowledge.getTitle()==null?"":knowledge.getTitle().trim()+"%'";
			}
			sqlstr+=" and kl.valid=1 order by kl.createtime desc) t)";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 获取前台需要显示的资料库
	 * @param op
	 * @param stopid
	 * @param containStop
	 * @return
	 * @throws ElException
	 */
	public KnowledgeType getKnowledgeLibTree_index(int stopid,boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection ct = null; 
//		KnowledgeType dep = op.equals("op") ? new KnowledgeType(0, "可操作的资料库")
//				: new KnowledgeType(0, "可使用的资料库");
		KnowledgeType dep = new KnowledgeType(ElConstants.USER_OP_LIB, "可查看的资料库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from knowledge_dep where status = 1");  
			rs = ps.executeQuery();
			List<KnowledgeType> list = new ArrayList<KnowledgeType>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					KnowledgeType depc = getKnowledgeLibTree(depid, stopid, containStop,1);
					depc.setParent(dep);
					list.add(depc);
					nlist.add(depc);
				}  
			}  
				dep.setChild(list);
				dep.setNchild(nlist);
		} catch (Exception e) {
			logger.error("获取前台需要显示的资料库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	//hwc1	 
	public KnowledgeType getKnowledgeLibTree(int userid, String op, int stopid,boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection ct = null; 
//		KnowledgeType dep = op.equals("op") ? new KnowledgeType(0, "可操作的资料库")
//				: new KnowledgeType(0, "可使用的资料库");
		KnowledgeType dep = new KnowledgeType(ElConstants.USER_OP_LIB, "可操作的资料库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ctypeid from knowledge_" +op+ "_type where userid = ?");  
			ps.setInt(1, userid);
			rs = ps.executeQuery();       
			List<KnowledgeType> list = new ArrayList<KnowledgeType>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					KnowledgeType depc = getKnowledgeLibTree(depid, stopid, containStop,1);
					depc.setParent(dep);
					list.add(depc);
					nlist.add(depc);
				}  
			}  
				dep.setChild(list);
				dep.setNchild(nlist);
		} catch (Exception e) {
			logger.error("查看资料库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	//hwc2
	private KnowledgeType getKnowledgeLibTree(int from, int stop, boolean containStop, int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		KnowledgeType Kltype = null;
		try {
			Kltype = getKltypeById(from);
			Kltype.setLevel(level);
			ct = DBConnection.getConnection();
			Kltype.setChild(listDepartmentsById(Kltype.getId(), stop, containStop, level,
					ct)); 
		} catch (Exception e) {
			logger.error("资料库树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return Kltype;
	}
	//此方法为添加
	/**
	 * 根据ctid查询出他的所有子节点
	 * @author jiahaijiang
	 * @param ctypeTree 整个树
	 * @param ctid 节点id
	 * @return
	 */
	public String KTTypeById(KnowledgeType ctypeTree, int ctid){
		if(ctypeTree!=null){
			if(ctypeTree.getId()!=ctid){
				ctypeTree = KTTypeById(ctypeTree.getChild(),ctid);
			}
			if(ctypeTree.getChild()!=null){
				return createKTTypeId(ctypeTree.getChild(),ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		}else{
			return null;
		}
	}
	/**
	 * 构建有权的课程类型ID
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @return
	 */
	private String createKTTypeId(List<KnowledgeType> listType,int id){
		String ids=id+"";
		for(KnowledgeType type:listType){
			ids=ids+","+createKTTypeId(type.getChild(),type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * @author jiahaijiang
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private KnowledgeType KTTypeById(List<KnowledgeType> listType,int ctid){
		KnowledgeType courseType=null;
		for(KnowledgeType type:listType){
			if(type.getId()!=ctid){
				courseType =  KTTypeById(type.getChild(),ctid);
				if(courseType!=null){
					return courseType;
				}
			}else{
				courseType = type;
				return courseType;
			}
		}
		return courseType;
	}
	/**
	 * 添加资料部门信息
	 * @param dataId 资料或者部门的id
	 * @param status 1：代表资料 2：代表部门
	 * @throws ElException
	 */
	public void addKnowledgeDep(int dataId,int status)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("insert into knowledge_dep(id,status) values(?,?)");
			ps.setInt(1, dataId);
			ps.setInt(2, status);
			ps.executeUpdate();
		}catch(Exception e){
			logger.error("添加资料部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 *  删除资料部门信息
	 * @throws ElException
	 */
	public void deleteKnowledgeDep()throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("delete from knowledge_dep");
			ps.executeUpdate();
		}catch(Exception e){
			logger.error("删除资料部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 获取资料部门表中的所有部门
	 * @return
	 * @throws ElException
	 */
	public List<Department> listKnowledgeDepd()throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps=new ArrayList<Department>();
		try{
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select dep.id,dep.name,dep.lid,dep.rid from knowledge_dep kd left join department dep on kd.id=dep.id where kd.status=2");
			rs=ps.executeQuery();
			Department dep=null;
			while(rs.next()){
				dep=new Department(rs.getInt(1),rs.getString(2));
				dep.setLid(rs.getInt(3));
				dep.setRid(rs.getInt(4));
				deps.add(dep);
			}
		}catch(Exception e){
			logger.error("获取资料部门表中的所有部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}
	/**
	 * 获取资料部门表中的所有部门id
	 * @return
	 * @throws ElException
	 */
	public List listKnowledgeDepk() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List treeAllId=new ArrayList();
		try{
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select id from knowledge_dep where status=1");
			rs=ps.executeQuery();
			while(rs.next()){
				treeAllId.add(rs.getInt(1));
			}
		}catch(Exception e){
			logger.error("获取资料部门表中的所有部门id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return treeAllId;
	}
	/**
	 * 检测用户是否有部门所对应分配的资料权限
	 * @param userid
	 * @throws ElException
	 */
	public boolean checkUserKnowledgeDep(int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select count(*) from("+
				" select kd.id,dep.lid,dep.rid from knowledge_dep kd inner join department dep on kd.id=dep.id where kd.status=2 "+
				" ) t1,(select dep.id,dep.lid,dep.rid from eluser eu inner join department dep on eu.depid=dep.id where eu.id=? "+
				" ) t2 where t1.lid<=t2.lid and t1.rid>=t2.rid");
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0){
					return true;
				}
			}
		}catch(Exception e){
			logger.error("检测用户是否有部门所对应分配的资料权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	int ktid=0;
	/**
	 * 根据id检查该资料在资料部门中是否存在
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public boolean checkKnowledgeDepK(int ktypeid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		//先获取类别的2级父节点
		//ktypeid=getKtypeid(ktypeid);
		KnowledgeType kt=this.getKltypeById(ktypeid);
		try{
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select id from knowledge_dep where status=1 and id=?");
			ps.setInt(1, ktypeid);
			rs=ps.executeQuery();
			if(rs.next()){
				ktid=1;
				return true;
			}else{
				//判断父节点是否1，如果不是继续查看是否有分配
				if(kt!=null&&kt.getParent().getId()>1){
					checkKnowledgeDepK(kt.getParent().getId());
				}else{
					return false;
				}
			}
		}catch(Exception e){
			logger.error("根据id检查该资料在资料部门中是否存在出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		if(ktid==1){
			return true;
		}else{
			return false;
		}
	}
	public int checkKnowledgeForMonth(String month) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int id = -1;
		KnowledgeType kltype = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select id from knowledgetype where name = '" + month + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}else {
				kltype = new KnowledgeType();
				kltype.setName(month);
				kltype.setDescription("");
				kltype.setParent(new ElNode(1));
				kltype.setManager(new ELUser(0));
				kltype.setIsshared(0);
				id = this.addKltype(kltype);
			}

		} catch (Exception e) {
			logger.error("没有节点，创建节点；有节点，获取节点失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return id;
	}
	public int getDownloadInfoIsAddCent(int type, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select count(*) from downloadinfo where isaddcent = 1 and  userid = ? and type = ? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, type);
			rs = ps.executeQuery();
			if (rs.next())
				count = rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取下载信息表用户下载得分次数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	public List<Course> getLX_MK_BJ_Integra_viewList(int userid, int classid, String classType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> courseList = new ArrayList<Course>(); 
		String sql = "";
		float LX = SystemConfOp.getFloatValue(ElConstants.LEARNING_LX_LX) == 0.0 ? 1 :SystemConfOp.getFloatValue(ElConstants.LEARNING_LX_LX);  
		float MK = SystemConfOp.getFloatValue(ElConstants.LEARNING_MK_MK) == 0.0 ? 1 :SystemConfOp.getFloatValue(ElConstants.LEARNING_MK_MK);  
		float BJ = SystemConfOp.getFloatValue(ElConstants.LEARNING_BJ_BJ) == 0.0 ? 1 :SystemConfOp.getFloatValue(ElConstants.LEARNING_BJ_BJ);  
		try {
		ct = DBConnection.getConnection();
		sql = "select cc.courseid,c.name,sc.certificateno from course c left join  "+classType+" cc on c.id = cc.courseid " +
				"left join study_class sc on sc.classid = cc.classid and sc.userid = ?" +
				"where cc.classid = ? group by cc.courseid,c.name,sc.certificateno";
		ps = ct.prepareStatement(sql); 
		ps.setInt(1, userid); 
		ps.setInt(2, classid); 
			rs = ps.executeQuery();
			while (rs.next()) { 
				Course course = new Course(rs.getInt(1),rs.getString(2));
				course.setIsLX(getLX_course(userid, classid,rs.getInt(1)) != 0 ? 1:0);
				course.setIsMK(getMk_Model(userid, rs.getInt(1)) != 0 ? 1:0);
				course.setIsBJ(getBj_course(userid, rs.getInt(1)) != 0 ? 1:0);
				if(rs.getString(3) != null && !rs.getString(3).equals("")){
					course.setLX_score(course.getIsLX() == 1?LX:0.0f);
					course.setMK_score(course.getIsMK() == 1?MK:0.0f);
					course.setBJ_score(course.getIsBJ() == 1?BJ:0.0f);
				}
				courseList.add(course);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courseList;
	} 
	/**
	 * 获取已做练习的课程数失败
	 * @param userid
	 * @param classid 
	 * @return
	 * @throws ElException
	 */
	public int getLX_course(int userid, int classid,int courseid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql="select count(*)  from (select courseid from study_cpage sc left join practicepaper pp on sc.cpid = pp.cpid " +
					"left join cprac_quizinfo cs on pp.id = cs.ppid " +
					"where cs.userid = ?  and classid = ? and pp.courseid = ? group by pp.courseid) ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid); 
			ps.setInt(2, classid);
			ps.setInt(3, courseid);
			rs = ps.executeQuery();
			if (rs.next())
				size =  rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取已做练习的课程数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	} 
	/**
	 * 做过课程内模考的课程 
	 * @param userid
	 * @param course 
	 * @return
	 * @throws ElException
	 */
	public int getMk_Model(int userid, int courseid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql="select count(*) from (select courseid from practicepaper pp ,cprac_quizinfo cq where pp.id = cq.ppid and courseid in " +
					" ("+courseid+") and cq.userid = ? and pp.cpid =0 " +
					" group by courseid) ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid); 
			rs = ps.executeQuery();
			if (rs.next())
				size =  rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取已做练习的课程数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	} 
	/**
	 * 已做笔记的课程数
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getBj_course(int userid, int courseid )throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql=" select  count(courseid) from course_note where courseid in ("+courseid+") and userid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid); 
			rs = ps.executeQuery();
			if (rs.next())
				size =  rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取已做练习的课程数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	public List<MyLogin> getDL_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyLogin> myLoginList = new ArrayList<MyLogin>(); 
		String sql = "";
		float DL = SystemConfOp.getFloatValue(ElConstants.LEARNING_DL_DL);  
		try {
		ct = DBConnection.getConnection();
		sql = "select * from (select t.*, rownum rn from (" +
				"select userid,logintime,exittime,lognumber from eluserloginInfo where  userid = ? order by logintime desc" +
				") t where rownum <= ? ) where rn>=?";
		ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyLogin myLogin = new MyLogin();  
				myLogin.setElUser(new ELUser(rs.getInt(1)));
				myLogin.setLogintime(rs.getTimestamp(2));
				myLogin.setExittime(rs.getTimestamp(3));
				myLogin.setLognumber(rs.getInt(4));
				if(rs.getInt(4) <= 10)
					myLogin.setScore(DL);
				else
					myLogin.setScore(0.0f);
				myLoginList.add(myLogin);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myLoginList;
	} 
	public int getDL_Integra_viewListSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0 ;
		try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select count(userid) from eluserloginInfo where  userid = ? "); 
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size; 
	} 
	public List<Forum> getFTJH_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> forumList = new ArrayList<Forum>(); 
		String sql = "";
		float FT = SystemConfOp.getFloatValue(ElConstants.LEARNING_FT_FT) == 0.0 ? 1 :SystemConfOp.getFloatValue(ElConstants.LEARNING_FT_FT);
		float JH = SystemConfOp.getFloatValue(ElConstants.LEARNING_JH_JH) == 0.0 ? 1 :SystemConfOp.getFloatValue(ElConstants.LEARNING_JH_JH);
		float score = 0.0f;
		try {
		ct = DBConnection.getConnection();
		sql = "select * from (select t.*, rownum rn from (" +
				"select id,title,valid,hot from forum where creater = ?" +
				") t where rownum <= ? ) where rn>=?";
		ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));  
				f.setValid(rs.getInt(3)==1?true : false);
				f.setHot(rs.getInt(4));
				if(f.getValid())
					score += FT ;
				if(f.getHot()==1)
					score += JH ;
				f.setScore(score);
				forumList.add(f);
				score = 0.0f;//还原
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return forumList;
	} 
	public int getFTJH_Integra_viewListSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0 ;
		try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select count(id) from forum where creater = ?"); 
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size; 
	} 
	public List<DownloadInfo> getXZ_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<DownloadInfo> dis = new ArrayList<DownloadInfo>(); 
		String sql = "";
		try {
		ct = DBConnection.getConnection();
		sql = "select * from (select t.*, rownum rn from (" +
				"select DownloadFileName, downloadtime from downloadinfo where userid = ?" +
				") t where rownum <= ? ) where rn>=?";
		ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) { 
				DownloadInfo d = new DownloadInfo();
				d.setDownloadFileName(rs.getString(1)); 
				d.setDownloadTime(rs.getTimestamp(2)); 
				dis.add(d);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dis;
	} 
	public int getXZ_Integra_viewListSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0 ;
		try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select count(*) from downloadinfo where userid = ?"); 
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size; 
	} 
	public List<Knowledge> getKl_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> klList = new ArrayList<Knowledge>();
		String sql = "";
		try {
		ct = DBConnection.getConnection();
		sql = "select * from (select t.*, rownum rn from (select id ,title,valid,award from knowledge where userid = ? ) t where rownum <= ? ) where rn>=?";
		ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));     
				kl.setStatus(rs.getInt(3));    
				kl.setAward(rs.getInt(4));  
				if(kl.getAward() == 1)
					kl.setScore(55);
				else if(kl.getAward() == 2)
					kl.setScore(35);
				else if(kl.getAward() == 3)
					kl.setScore(25);
				else if(kl.getAward() == 0)
					kl.setScore(15); 
				else if(kl.getAward() == 4)
					kl.setScore(5); 
				
				klList.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return klList;
	}
	public int getKl_Integra_viewListSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		int size = 0 ;
		try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select count(id)  from knowledge where userid = ? "); 
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	public List<Knowledge> getBXZ_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> klList = new ArrayList<Knowledge>();
		float variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_BXZ_BXZ) == 0.0 ? 1 :SystemConfOp.getFloatValue(ElConstants.LEARNING_BXZ_BXZ);
		String sql = "";
		try {
		ct = DBConnection.getConnection();
		sql = "select * from (select t.*, rownum rn from (select id ,title from knowledge where userid = ? ) t where rownum <= ? ) where rn>=?";
		ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kl = new Knowledge(rs.getInt(1), rs.getString(2));      
				kl.setCounts(getBxz_people(userid, rs.getInt(1)));
				kl.setScoreF(kl.getCounts()* variables);
				klList.add(kl);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return klList;
	} 
	/**
	 * 获取本年度某文章被下载次数失败
	 * @param userid
	 * @param kid
	 * @return
	 * @throws ElException
	 */
	public int getBxz_people(int userid ,int kid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = ""; 
			sql="select count(*) from downloadinfo di, knowledge kl where di.typeid = kl.id and di.type = 1 " +
					"and kl.userid = ? and di.userid != ? and typeid = ? " +//type =1 是knowledge类别  and di.userid != ? 是不包含本人自己下载
					" and di.DownloadTime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)" +
					"  and di.DownloadTime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)"; 
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);  
			ps.setInt(2, userid);  
			ps.setInt(3, kid);  
			rs = ps.executeQuery();
			if (rs.next())
				size =  rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取本年度某文章被下载次数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	public int getBXZ_Integra_viewListSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0 ;
		try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select count(id) from knowledge where userid = ? "); 
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size; 
	} 
	public List<ELUser> getBXZ_XQ_Integra_viewList(int kid, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>(); 
		String sql = "";
		try {
		ct = DBConnection.getConnection();
		sql = "select * from (select t.*, rownum rn from (" +
				"select u.id , u.username ,u.realname,di.downloadtime,di.DownloadFileName " +
				"from downloadinfo di , eluser u ,knowledge kl where di.userid = u.id " +
				" and di.userid != kl.userid  and di.typeid = kl.id and di.type = 1 " +
				" and typeid = ?) t where rownum <= ? ) where rn>=?";
		ps = ct.prepareStatement(sql); 
			ps.setInt(1, kid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser u = new ELUser(rs.getInt(1), rs.getString(3));     
				u.setUsername(rs.getString(2)); 
				DownloadInfo d = new DownloadInfo();
				d.setDownloadTime(rs.getTimestamp(4)); 
				d.setDownloadFileName(rs.getString(5));
				u.setDownloadInfo(d);  
				userList.add(u);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	} 
	public int getBXZ_XQ_Integra_viewListSize(int kid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0 ;
		try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select count(u.id)  from downloadinfo di , eluser u ,knowledge kl where di.userid = u.id " +
				" and di.userid != kl.userid  and di.typeid = kl.id and di.type = 1 " +
				" and typeid = ?"); 
			ps.setInt(1, kid);
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size; 
	}
	public List<Knowledge> listKlByStuffAddr(String stuffaddr)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ks.id,ks.knowid,ks.stuffaddr,kl.id,kl.title,kl.userid,kl.content,kl.createtime,kl.modifytime,kl.readtime from KNOWLEDGE_STUFF ks left join knowledge kl on ks.knowid=kl.id where stuffaddr like ?");
			ps.setString(1, "%"+stuffaddr+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				Knowledge kl = new Knowledge();
				kl.setId(rs.getInt(2));
				kl.setTitle(rs.getString(5));
				kl.setContent(rs.getString(7));
				kl.setCreatetime(rs.getDate(8));
				kl.setModifytime(rs.getDate(9));
				kl.setReadtime(rs.getInt(10));
				kls.add(kl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kls;
	}
	public List<Knowledge> listMyKlsNew(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select k.*,d.id depid,eu.realname from knowledge k,department d,eluser eu where k.userid=eu.id and eu.depid=d.id and depid=?)t where rownum <= ? ) where rn>=?");
			ps.setInt(1, depid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				Knowledge kl = new Knowledge();
				kl.setId(rs.getInt(1));
				kl.setTitle(rs.getString(2));
				kl.setContent(rs.getString(12));
				kl.setCreatetime(rs.getDate(6));
				kl.setModifytime(rs.getDate(5));
				kl.setReadtime(rs.getInt(7));
				kls.add(kl);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	public List<Knowledge> getTjKls(int kltype, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select a.*, rownum rn from (select * from knowledge where kltypeid=? and hot=? and valid=1 order by createtime desc)a where rownum<=8");
			ps.setInt(1, kltype);
			ps.setInt(2, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				Knowledge kl = new Knowledge();
				kl.setId(rs.getInt(1));
				kl.setTitle(rs.getString(2));
				kl.setKltype(new KnowledgeType(rs.getInt(4)));
			//	kl.setContent(rs.getString(12));
				kl.setCreatetime(rs.getDate(6));
				kl.setModifytime(rs.getDate(5));
				kls.add(kl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	}
	public List<Knowledge> getTjKls(int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> kls = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select a.*, rownum rn from (select * from knowledge where  hot=? and valid=1 order by createtime desc)a where rownum<=8");
			ps.setInt(1, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				Knowledge kl = new Knowledge();
				kl.setId(rs.getInt(1));
				kl.setTitle(rs.getString(2));
				kl.setKltype(new KnowledgeType(rs.getInt(4)));
			//	kl.setContent(rs.getString(12));
				kl.setCreatetime(rs.getDate(6));
				kl.setModifytime(rs.getDate(5));
				kls.add(kl);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kls;
	} 
}
