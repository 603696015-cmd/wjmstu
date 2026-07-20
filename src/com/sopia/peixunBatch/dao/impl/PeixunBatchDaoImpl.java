package com.sopia.peixunBatch.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.batchman.BatchConstants;
import com.sopia.batchman.entities.Batch;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.impl.ClassDaoImpl;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.StringUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.schedule.entities.Eluser;
import com.sopia.statman.entities.MyClass;

/**
 * @author WKM
 *
 */
/**
 * @author WKM
 *
 */
/**
 * @author WKM
 *
 */
public class PeixunBatchDaoImpl implements PeixunBatchDao{
	private static final Log logger = LogFactory.getLog(PeixunBatchDaoImpl.class);
	
	public int getSessionValue(String key) {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return Integer.parseInt(session.getAttribute(key).toString());
	}
	
	/**
	 * 获取所有基础数据类别
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataType> getBaseDataType() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDataType> baseTypeList = new ArrayList<BaseDataType>(5);
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,remack from basedatatype  where name='培训批次类型' order by id ");
			rs = ps.executeQuery();
			BaseDataType bt = null;
			while (rs.next()) {
				bt = new BaseDataType();
				bt.setId(rs.getInt("id"));
				bt.setName(rs.getString("name"));
				bt.setRemack(rs.getString("remack"));
				baseTypeList.add(bt);
			}
		} catch (Exception e) {
			logger.error("获取所有基础数据类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baseTypeList;
	}
	
	
	/**
	 * 根据类别查询数据(分页)
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid2() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			
				ps = ct
						.prepareStatement("select bd.id bdid,bd.typeid bdtypeid,bd.basevalue,bd.remack bdremack,bd.sortid,bt.id btid,bt.name,bd.bh from basedatat bd left join basedatatype bt on bd.typeid=bt.id where bd.typeid=? and bd.status!=1 order by sortid");
				ps.setInt(1, 11);
			
			rs = ps.executeQuery();
			BaseDatat bd = null;
			while (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("bdid"));
				bd.setTypeid(rs.getInt("bdtypeid"));
				bd.setBaseType(new BaseDataType(rs.getInt("btid"), rs
						.getString("name")));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("bdremack"));
				bd.setSortid(rs.getInt("sortid"));
				bd.setBh(rs.getString(8));
				baseList.add(bd);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baseList;
	}
	
	
	/**
	 * 保存批次
	 */
	public void save_batch(PeixunBatch peixunBatch,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql="insert into peixun_batch(name,description,typeid,createtime,endtime,createrid,status) values(?,?,?,?,?,?,1)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, peixunBatch.getName());
			ps.setString(2, peixunBatch.getDescription());
			ps.setInt(3, peixunBatch.getTypeid());
			ps.setTimestamp(4, peixunBatch.getCreatetime());
			ps.setTimestamp(5, peixunBatch.getEndtime());
			ps.setInt(6, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 获得批次列表
	 */
	
	public List<PeixunBatch> getBatchList(String name, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<PeixunBatch> list = new ArrayList<PeixunBatch>();
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			String sql ="select *" +
						"  from (select t.*, rownum rn" + 
						"          from (select pb.id," + 
						"                       pb.name," + 
						"                       pb.typeid," + 
						"                       to_char(pb.createtime,'yyyy-mm-dd hh24:mi:ss') createtime," + 
						"                       el.realname," +
						"                        pb.createrid ," +
						"                       count(bef.elclassid) elclasses," +
						"                         pb.status, " +
						"                          bd.basevalue" + 
						"                  from peixun_batch pb, eluser el, batch_elclass_fenpei bef,basedatat bd " + 
						"                 where pb.createrid = el.id(+)" + 
						"                   and pb.id = bef.batchid(+)" +
						"                    and pb.typeid=bd.id(+)" + 
						"                   and pb.status=1 "+
						"                   and name like ?" + 
						"                 group by pb.id," + 
						"                          pb.name," + 
						"                          pb.typeid," + 
						"                          pb.createtime," + 
						"                          el.realname, pb.createrid,pb.status, bd.basevalue  " + 
						"                 order by pb.id) t" + 
						"         where rownum <= ?)" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				PeixunBatch peixunBatch = new PeixunBatch(rs.getInt(1), rs.getString(2));
				peixunBatch.setTypeid(rs.getInt(3));
				peixunBatch.setCreatetime(rs.getTimestamp(4));
				ELUser el  = new ELUser();
				el.setRealname(rs.getString(5));
				peixunBatch.setCreater(el);
				peixunBatch.setCreaterid(rs.getInt(6));
				peixunBatch.setElclassCount(rs.getInt(7));
				peixunBatch.setStatus(rs.getInt(8));
				BaseDatat bd = new BaseDatat();
				bd.setBasevalue(rs.getString(9));
				peixunBatch.setBaseData(bd);
				list.add(peixunBatch);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public int getBatchListSize(int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from peixun_batch where status=1 and createrid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<PeixunBatch> getBatchElClssList(int id) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<PeixunBatch> list = new ArrayList<PeixunBatch>();
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select el.id,el.name,pb.typeid,el.createtime,el.classtype, " +
						"  from batch_elclass_fenpei bef, elclass el, peixun_batch pb " + 
						" where bef.elclassid = el.id(+)" + 
						"   and pb.id = bef. batchid(+)" + 
						"   and bef.batchid = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				PeixunBatch peixunBatch = new PeixunBatch(rs.getInt(1), rs.getString(2));
				peixunBatch.setTypeid(rs.getInt(3));
				ElClass elc = new ElClass();
				elc.setCreatetime(rs.getDate(4));
				elc.setClasstype(rs.getInt(5));
				peixunBatch.setElclass(elc);
				list.add(peixunBatch);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public List<ELUser> getBatchElUserList(int id) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> list = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select el.realname,el.sex,el.username,dep.name  from batch_user_fenpei buf, eluser el, department dep,study_class scl\n" + 
						" where buf.userid = el.id(+)" + 
						"   and el.id = scl.userid(+)" + 
						"   and el.depid = dep.id(+)" + 
						" where buf.batchid = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser el = new ELUser();
				el.setRealname(rs.getString(1));
				el.setSex(rs.getString(2));
				el.setUsername(rs.getString(3));
				list.add(el);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void delete_batch(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql=" update peixun_batch set status=0 where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
//	public PeixunBatch getList(int id) throws ElException{
//		PreparedStatement ps = null;
//		Connection ct = null;
//		ResultSet rs = null;
//		PeixunBatch peixunBatch = null;
//		List<ElClass> elclass = new ArrayList<ElClass>();
//		List<ELUser> eluser = new ArrayList<ELUser>();
//		try {
//			ct = DBConnection.getConnection();
//			String sql =
//						"select pb.id pbId," +
//						"       pb.name," + 
//						"       pb.description," + 
//						"       pb.typeid," + 
//						"       elc.id," + 
//						"       elc.name," + 
//						"       elc.createtime," + 
//						"       elc.classtype" + 
//						"  from elclass elc, batch_elclass_fenpei bef, peixun_batch pb" + 
//						" where bef.elclassid = elc.id(+)" + 
//						"   and pb.id = bef.batchid" + 
//						"   and bef.batchid = "+id;
//			ps = ct.prepareStatement(sql);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				 peixunBatch = new PeixunBatch(rs.getInt(1), rs.getString(2));
//				peixunBatch.setDescription(rs.getString(3));
//				peixunBatch.setTypeid(rs.getInt(4));
//				ElClass elc =new ElClass();
//				elc.setId(rs.getInt(5));
//				elc.setName(rs.getString(6));
//				elc.setCreatetime(rs.getDate(7));
//				elc.setClasstype(rs.getInt(8));
//				elclass.add(elc);
//				peixunBatch.setClasses(elclass);
//				
//			}
//		} catch (Exception e) {
//			logger.error("获取培训批次列表失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return peixunBatch;
//	}
	
	
	public void addBatchClass(int batchid,int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into batch_elclass_fenpei(batchid,elclassid,sortid) values(?,?,?)");
			ps.setInt(1, batchid);
			ps.setInt(2, classid);
			ps.setInt(3, maxSortIdInBe(batchid)+1);
			rs = ps.executeQuery();
			
		} catch (Exception e) {
			logger.error("添加培训批次和培训班关联失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	
	public int maxSortIdInBe(int batchId) throws ElException {
		int sortid = 0;
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select max(sortid) from batch_elclass_fenpei where batchid= ? ");
			ps.setInt(1, batchId);
			rs = ps.executeQuery();
			if (rs.next())
				sortid = rs.getInt(1);

		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sortid;
	}
	
	
	
	
	
	public List<ElClass> listcombinationSearchClass(ElClass elClass,
			ElClType cltypeTree, String sqlw, int pageNow, int pageSize, int peixunBatchId)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			// name = name == null ? "" : name.trim();
			int lid = 0;
			int rid = 0;
			ps = ct
					.prepareStatement("select id,lid,rid from elclasstype where id =?");
			// ps.setInt(1, cltid);
			ps.setInt(1, elClass.getCltype().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}

			String sqlstr = "";
			sqlstr += "select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,cl.createtime,elu.realname,cl.astauts,cl.isApplication from elclass cl, elclasstype clt,eluser elu ";
			sqlstr += " where cl.cltype=clt.id and elu.id=cl.creater and cl.status >=0 ";
			// sqlstr+=elClass.getCltype()==null?"":(elClass.getCltype().getId()==-1||elClass.getCltype().getId()==0)?"":"
			// and clt.id in
			// ("+createPerTypeId(cltypeTree,elClass.getCltype().getId())+")";
			sqlstr += " and clt.lid>=" + lid + " and clt.rid<=" + rid;
			if (elClass.getOwner() != null) {
				sqlstr += elClass.getOwner().getUsername() == null ? ""
						: " and elu.username like '%"
								+ elClass.getOwner().getUsername() + "%'";
				sqlstr += elClass.getOwner().getRealname() == null ? ""
						: " and elu.realname like '%"
								+ elClass.getOwner().getRealname() + "%'";
			}
			sqlstr += elClass.getStatus() == -1 ? "" : " and cl.status = "
					+ elClass.getStatus();
			sqlstr += (elClass.getName() == null || elClass.getName()
					.equals("")) ? "" : " and cl.name like '%"
					+ elClass.getName() + "%'";
			sqlstr += (elClass.getBegintime() == null && elClass.getEndtime() == null) ? ""
					: " and to_date(to_char(cl.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"
							+ elClass.getBegintime()
							+ "','yyyy-mm-dd') and to_date('"
							+ elClass.getEndtime() + "','yyyy-mm-dd')";
			sqlstr += "  and cl.id not in (select cc.elclassid "+
                                   "   from batch_elclass_fenpei cc "+
                                   "  where cc.batchid = "+peixunBatchId+") order by cl.createtime desc) t where rownum <= "
					+ pageNow + " ) where rn>=" + pageSize + "";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setCreatetime(rs.getTimestamp(8));
				cl.setAstatus(rs.getInt(10));
				cl.setIsApplication(rs.getInt("isApplication"));
				ELUser user = new ELUser();
				user.setRealname(rs.getString(9));
				cl.setCreater(user);
				
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班组合搜索列表失败!失败方法：listcombinationSearchClass(ElClass elClass,ElClType cltypeTree,String sqlw ,int pageNow,int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班组合搜索列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public int listcombinationSearchClassCount(ElClass elClass,
			ElClType cltypeTree, int pageNow, int pageSize, int peixunBatchId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;

		try {
			ct = DBConnection.getConnection();
			// name = name == null ? "" : name.trim();
			int lid = 0;
			int rid = 0;
			ps = ct
					.prepareStatement("select id,lid,rid from elclasstype where id =?");
			// ps.setInt(1, cltid);
			ps.setInt(1, elClass.getCltype().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			String sqlstr = "";
			sqlstr += "select count(*) from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname from elclass cl, elclasstype clt,eluser elu ";
			sqlstr += " where cl.cltype=clt.id and elu.id=cl.creater and cl.status >=0 ";
			// sqlstr+=elClass.getCltype()==null?"":(elClass.getCltype().getId()==-1||elClass.getCltype().getId()==0)?"":"
			// and clt.id in
			// ("+createPerTypeId(cltypeTree,elClass.getCltype().getId())+")";
			sqlstr += " and clt.lid>=" + lid + " and clt.rid<=" + rid;
			if (elClass.getOwner() != null) {
				sqlstr += elClass.getOwner().getUsername() == null ? ""
						: " and elu.username like '%"
								+ elClass.getOwner().getUsername() + "%'";
				sqlstr += elClass.getOwner().getRealname() == null ? ""
						: " and elu.realname like '%"
								+ elClass.getOwner().getRealname() + "%'";
			}
			sqlstr += elClass.getStatus() == -1 ? "" : " and cl.status = "
					+ elClass.getStatus();
			sqlstr += (elClass.getName() == null || elClass.getName()
					.equals("")) ? "" : " and cl.name like '%"
					+ elClass.getName() + "%'";
			sqlstr += (elClass.getBegintime() == null && elClass.getEndtime() == null) ? ""
					: " and to_date(to_char(cl.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"
							+ elClass.getBegintime()
							+ "','yyyy-mm-dd') and to_date('"
							+ elClass.getEndtime() + "','yyyy-mm-dd')";
			sqlstr += "  and cl.id not in (select cc.elclassid "+
            "   from batch_elclass_fenpei cc "+
            "  where cc.batchid = "+peixunBatchId+") order by cl.createtime desc) t )";// where rownum <=
			// "+pageNow+" )
			// where
			// rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班组合搜索列表失败!失败方法：listcombinationSearchClassCount(ElClass elClass,ElClType cltypeTree,int pageNow,int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班组合搜索列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int batchId) throws ElException{
		List<ELUser> userList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select *" +
						"  from (select t.* , rownum rn" + 
						"          from (select distinct(el.id) id," + 
						"                       el.username," + 
						"                       el.realname," + 
						"                       elr.name elrName," + 
						"                       sat.name satName," + 
						"                       dep.name depName," + 
						"                       stc.joinway," + 
						"                       el.sex," + 
						"                       nvl(floor(to_char(sysdate, 'yyyy')) -" + 
						"                           floor(to_char(el.shengri, 'yyyy'))," + 
						"                           -1) age_" + 
						"                  from eluser            el," + 
						"                       Station           sat," + 
						"                       ELROLE            elr," + 
						"                       department        dep," + 
						"                       study_class       stc," + 
						"                       batch_user_fenpei buf" + 
						"                 where buf.userid = el.id(+)" + 
						"                   and el.role = elr.id(+)" + 
						"                   and el.depid = dep.id(+)" + 
						"                   and el.staid = sat.id(+)" + 
						"                   and el.id = stc.userid(+)" + 
						"                   and buf.batchid = "+batchId+") t" + 
						"         where rownum <= "+pageNow+")" + 
						" where rn >= "+pageSize;

			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				ELUser user = new ELUser();
				Department dept= new Department();
				Station sta = new Station();
				ElRole role = new ElRole();
				
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setRealname(rs.getString(3));
				role.setName(rs.getString(4));
				user.setRole(role);
				sta.setName(rs.getString(5));
				user.setStation(sta);
				dept.setName(rs.getString(6));
				user.setDepartment(dept);
				
				user.setIsAssign("已分配");
				user.setJoinway(rs.getInt(7) == 0 ? "分配"
						: "申请");
				user.setSex(rs.getString(8));
				user.setAge(rs.getInt(9));
				userList.add(user);
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}
	
	
	public int listAssignedUserSize(int pageNow, int pageSize, int batchId) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select *" +
						"  from (select t.* , rownum rn" + 
						"          from (select count(distinct(el.id) )id " + 
						"                  from eluser            el," + 
						"                       batch_user_fenpei buf" + 
						"                 where buf.userid = el.id(+)" + 
						"                   and buf.batchid = "+batchId+") t" + 
						"         where rownum <= "+pageNow+")" + 
						" where rn >= "+pageSize;

			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if (rs.next())
				s = rs.getInt(1);
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	
	
	
	
	public List<ELUser> listUnAssignedUser(int pageNow, int pageSize) throws ElException{
		List<ELUser> userList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select *" +
						"  from (select t.* , rownum rn" + 
						"          from (select el.id," + 
						"                       el.username," + 
						"                       el.realname," + 
						"                       elr.name elrName," + 
						"                       sat.name satName," + 
						"                       dep.name depName," + 
						"                       stc.joinway," + 
						"                       el.sex," + 
						"                       nvl(floor(to_char(sysdate, 'yyyy')) -" + 
						"                           floor(to_char(el.shengri, 'yyyy'))," + 
						"                           -1) age_" + 
						"                  from eluser            el," + 
						"                       Station           sat," + 
						"                       ELROLE            elr," + 
						"                       department        dep," + 
						"                       study_class       stc " + 
						"                 where  el.role = elr.id(+)" + 
						"                   and el.depid = dep.id(+)" + 
						"                   and el.staid = sat.id(+)" + 
						"                   and el.id = stc.userid(+)" +") t" + 
						"         where rownum <= "+pageSize+")" + 
						" where rn >= "+pageNow;

			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				ELUser user = new ELUser();
				Department dept= new Department();
				Station sta = new Station();
				ElRole role = new ElRole();
				
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(3));
				
				user.setDepartment(new Department(rs.getInt(3), rs
								.getString(4)));
				user.setUsername(rs.getString(2));
				user.setJingzhong(rs.getInt(6));
				user.setRole(new ElRole(rs.getInt(7), rs.getString(8)));
				user.setSex(rs.getString(9));
				user.setIsAssign("未分配");
				user.setJoinway(rs.getInt("joinway") == 0 ? "分配"
						: "申请");
				user.setAge(rs.getInt(11));
				userList.add(user);
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	} 
	
	
	
	
	
/**
 * 
 */
	public List<ELUser> listUsers(ElNode dep, ElNode sta,int subdep, ELUser eu,
			int pageNow, int pageSize,int peixunBatchId) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			boolean consub = subdep == 1 ? true : false;
			String username = "";
			String realname = "";
			String staname = "";
			String sex = "";
			Date shengri = null;
			Date shengri_end = null;
			int valid = -2;
			int roleid = -2;
			int zhiwu = 0;
			int jingzhong = 0;
			StringBuffer basesql = null;
			if(pageNow==-1 && pageSize == -1){
				basesql = new StringBuffer(
				"select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,eu.education,eu.specialty,eu.cepingjindu  from ELUSER eu join (");
			}else{
				basesql = new StringBuffer(
				"select * from (select t.*,rownum rn from(select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,eu.education,eu.specialty,eu.cepingjindu  from ELUSER eu join (");
			}
			basesql.append( ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, consub));
			basesql.append(") dep on eu.depid = dep.id inner join (" );
			basesql.append( ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("station", sta, consub));
			basesql.append(") sta on sta.id=eu.staid left join");
			basesql.append( " elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? and eu.xianzhiwei like ?");
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if(null != eu.getXianzhiwei())
					staname = eu.getXianzhiwei().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
				if (eu.getShengri() != null) {
					basesql.append(" and eu.shengri >=?)");
					shengri = eu.getShengri() ;
				}
				if (eu.getShengri_end() != null) {
					basesql.append(" and eu.shengri <= ?");
					shengri_end =  eu.getShengri_end() ;
				}
				if (eu.getValid2() != 0) {
					if (eu.getValid2() == 1) {
						basesql.append(" and eu.valid= ?");
						valid = 1;
					} else {
						basesql.append(" and eu.valid= ?");
						valid = 0;
					}
				}
				if (eu.getRole() != null && eu.getRole().getId() > 0) {
					basesql.append(" and eu.role =  ?");
					roleid = eu.getRole().getId();
				}
				if (0!=eu.getZhiwu()){
					basesql.append(" and eu.zhiwu= ?");
					zhiwu = eu.getZhiwu();
				}
				if (0!=eu.getJingzhong()){
					basesql.append(" and eu.jingzhong= ?");
					jingzhong = eu.getJingzhong();
				}
			}  
			basesql.append(" and eu.id not in (select userid from batch_user_fenpei where batchid="+peixunBatchId);
			if(pageNow!=-1&&pageSize!=-1){
				basesql.append(" ))t where rownum <=? ) where rn >=?");
			}
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
			ps.setString(4, "%"+staname+"%");
			int idx = 5;
			if(shengri!=null){
				ps.setDate(idx, shengri);
				idx++;
			}
			if(shengri_end!=null){
				ps.setDate(idx, shengri_end);
				idx++;
			}
			if(valid !=-2)
			{
				ps.setInt(idx, valid);
				idx++;
			}
			if(roleid !=-2)
			{
				ps.setInt(idx, roleid);
				idx++;
			}
			if(zhiwu !=0)
			{
				ps.setInt(idx, zhiwu);
				idx++;
			}
			if(jingzhong !=0)
			{
				ps.setInt(idx, jingzhong);
				idx++;
			}
			ps.setInt(idx, pageNow);
			ps.setInt(idx+1, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(5), rs
						.getString(6)));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(9));
				elUser.setJingzhong(rs.getInt(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
				elUser.setEducation(rs.getInt(13));
				elUser.setSpecialty(rs.getString(14));
				elUser.setCepingjindu(rs.getString(15));
				elUser.setIsAssign("未分配");
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}
	
	
	public int listUsersSize(ElNode dep, ElNode sta,int subdep, ELUser eu,int peixunBatchId)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			boolean consub = subdep == 1 ? true : false;
			String username = "";
			String realname = "";
			String sex = "";
			String staname = "";
			Date shengri = null;
			Date shengri_end = null;
			int valid = -2;
			int roleid = -2;
			StringBuffer basesql = new StringBuffer(
					"select count(eu.id) from ELUSER eu join (");
			basesql.append( ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, consub));
			basesql.append(") dep on eu.depid = dep.id inner join (");
			basesql.append( ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("station", sta, consub));
			basesql.append(") sta on sta.id=eu.staid left join");
			basesql.append(	" elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? and sta.name like ?");
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getXianzhiwei())
					staname = eu.getXianzhiwei().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
				// if (null != eu.getJingzhong())
				// jz = eu.getJingzhong().trim();
				if (eu.getShengri() != null) {
					basesql.append(" and eu.shengri >=?)");
					shengri = eu.getShengri() ;
				}
				if (eu.getShengri_end() != null) {
					basesql.append(" and eu.shengri <= ?");
					shengri_end =  eu.getShengri_end() ;
				}
				if (eu.getValid2() != 0) {
					if (eu.getValid2() == 1) {
						basesql.append(" and eu.valid= ?");
						valid = 1;
					} else {
						basesql.append(" and eu.valid= ?");
						valid = 0;
					}
				}
				if (eu.getRole() != null && eu.getRole().getId() > 0) {
					basesql.append(" and eu.role =  ?");
					roleid = eu.getRole().getId();
				}
			}  
			basesql.append(" and eu.id not in (select userid from batch_user_fenpei where batchid="+peixunBatchId+")");
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
			ps.setString(4, "%"+staname+"%");
			int idx = 5;
			if(shengri!=null){
				ps.setDate(idx, shengri);
				idx++;
			}
			if(shengri_end!=null){
				ps.setDate(idx, shengri_end);
				idx++;
			}
			if(valid !=-2)
			{
				ps.setInt(idx, valid);
				idx++;
			}
			if(roleid !=-2)
			{
				ps.setInt(idx, roleid);
				idx++;
			}
			rs = ps.executeQuery();
			if (rs.next())
				s = rs.getInt(1);
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
		}
	
	
	public void addBatchEluser(int batchid,int userid) throws ElException{
//		PreparedStatement ps = null;
//		Statement st = null;
//		Connection ct = null;
//		ResultSet rs = null;
//		String sql = "";
//		try {
//			ct = DBConnection.getConnection();
//			st = ct.createStatement();
//			st.addBatch("insert into batch_user_fenpei (batchid,userid,begintime,endtime,process) select  "+batchid+","+userid+",createtime,endtime,0.00 from peixun_batch where id="+batchid);
//			st.addBatch("insert into study_class (classid,userid,applydate,status,joinway,process) select elclassid,"+userid+",sysdate,1,0,0.00 from batch_elclass_fenpei where batchid="+batchid);
//		    st.executeBatch();
//			
//		} catch (Exception e) {
//			logger.error("添加培训批次和培训班关联失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
		/**
		 * 修改TMK
		 */
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call add_batch_user(?,?)");
			ps.setInt(1, batchid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新培训batch进度失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 
	 */
	public void delete_elclass(int elclassid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		Statement st = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql=" update peixun_batch set status=0 where id=?";
			st = ct.createStatement();
			st.addBatch("delete from study_class where classid="+elclassid);
			st.addBatch("delete from batch_elclass_fenpei where elclassid="+elclassid);
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, id);
//			ps.executeUpdate();
			st.executeBatch();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 获得培训班列表
	 */
	public List<ElClass> getElclassList(int batchId) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> elclass = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select elc.id, elc.name, elc.createtime, elc.classtype,bef.sortid ,bd.basevalue  " +
						"  from batch_elclass_fenpei bef, elclass elc,peixun_batch pb,basedatat bd" + 
						" where bef.elclassid = elc.id(+) and bef.batchid=pb.id(+) and pb.typeid=bd.id(+) " + 
						"   and bef.batchid = "+batchId+
						"   order by bef.sortid ";
			System.out.println("获取所有培训班sql"+sql);
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()){
				ElClass elc = new ElClass();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setCreatetime(rs.getDate(3));
				elc.setClasstype(rs.getInt(4));
				elc.setSortid(rs.getInt(5));
				BaseDatat bd = new BaseDatat();
				bd.setBasevalue(rs.getString(6));
				elc.setBaseData(bd);
				elclass.add(elc);
			}
				
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elclass;
}
	
	public PeixunBatch getPeixunBatchById(int batchId) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PeixunBatch pb = new PeixunBatch();
		try {
			ct = DBConnection.getConnection();
			String sql =  "select pb.id,pb.name,pb.description ,bef.sortid from peixun_batch pb,batch_elclass_fenpei bef  where pb.id=bef.batchid(+) and id= "+batchId;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()){
				pb.setId(rs.getInt(1));
				pb.setName(rs.getString(2));
				pb.setDescription(rs.getString(3));
				pb.setSortid(rs.getInt(4));
			}
				
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pb;
	}
	
	/**
	 * 查看自己培训列表
	 */
	public List<PeixunBatch> getMyBatchList(int id, int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<PeixunBatch> list = new ArrayList<PeixunBatch>();
		try {
			ct = DBConnection.getConnection();
			String sql ="select *" +
						"  from (select t.*, rownum rn" + 
						"          from ( select pb.id, pb.name, buf.begintime, buf.endtime, count(bef.elclassid) elclasses,\n" +
						"(select count(bef1.elclassid) from batch_elclass_fenpei bef1, batch_user_fenpei buf1 where buf1.userid=? and buf1.batchid = bef1.batchid(+) and bef1.process=100.00) elclassOk\n" + 
						"  from batch_elclass_fenpei bef, peixun_batch pb, batch_user_fenpei buf\n" + 
						" where buf.batchid = bef.batchid\n" + 
						"   and pb.id = buf.batchid(+)\n" + 
						"   and buf.userid=?\n" + 
						" group by pb.id, pb.name, buf.begintime, buf.endtime"+
						") t" + 
						"         where rownum <= ?)" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1,id);
			ps.setInt(2,id);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				PeixunBatch peixunBatch = new PeixunBatch(rs.getInt(1), rs.getString(2));
//				peixunBatch.setTypeid(rs.getInt(3));
				peixunBatch.setCreatetime(rs.getTimestamp(3));
				peixunBatch.setEndtime(rs.getTimestamp(4));
				peixunBatch.setElclassCount(rs.getInt(5));
				peixunBatch.setClassCount(rs.getInt(6));
//				ELUser el  = new ELUser();
//				el.setRealname(rs.getString(5));
//				peixunBatch.setCreater(el);
//				peixunBatch.setCreaterid(rs.getInt(6));
//				
//				peixunBatch.setStatus(rs.getInt(8));
				list.add(peixunBatch);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	
	public void addBatchClass_course(int batchid,int userid,int joinway) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql="{call addBatchClass_course(?,?,?)}";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, batchid);
			ps.setInt(2, userid);
			ps.setInt(3, joinway);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public int getMyBatchDetailSize( int userid) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"select count(distinct(pb.id))" +
					"  from batch_elclass_fenpei bef, peixun_batch pb, batch_user_fenpei buf" + 
					" where buf.batchid = bef.batchid" + 
					"   and pb.id = buf.batchid(+)" + 
					"   and buf.userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取个人培训批次列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	/**
	 * 获得个人培训详情
	 * （未测试）
	 */
	public List<MyClass> getMyBatchDetail(int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mycs = new ArrayList<MyClass>();
		MyClass myc = null;
		ElClass elc = null;
		ExamRoom room = null;
		try {
			ct = DBConnection.getConnection();
			String sql ="select buf.userid," +
						"       elc.id," + 
						"       elc.name," + 
						"       round(sc.process,2) process," + 
						"       count(sco.courseid) courses," + 
						"       (select count(passed)" + 
						"          from study_course" + 
						"         where tprocess = 100.00" + 
						"           and userid ="+userid+") copassed," + 
						"       bef.sortid," +
						"		eae.examroomid" +
						"  from batch_elclass_fenpei bef," + 
						"       elclass              elc," + 
						"       study_class          sc," + 
						"       study_course         sco," + 
						"       batch_user_fenpei    buf," +
						"		elclass_assign_examroom eae " + 
						" where bef.elclassid = elc.id(+)" + 
						"   and elc.id = sc.classid" + 
						"   and buf.userid=sc.userid(+)" + 
						"   and buf.userid=sco.userid(+)" +
						"	and bef.elclassid=eae.classid(+)" + 
						"   and sc.userid = sco.userid" + 
						"   and buf.userid=" + userid+
						" group by  bef.sortid,buf.userid,sc.userid, elc.id, elc.name, sc.process,eae.examroomid order by  bef.sortid asc";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()){
				myc = new MyClass();
				elc = new ElClass(rs.getInt(2),rs.getString(3));
				room = new ExamRoom(rs.getInt(8));
				myc.setExamRoom(room);
				myc.setElClass(elc);
				myc.setUserid(rs.getInt(1));
				myc.setProcessForElc(rs.getDouble(4));
				myc.setCourseCount(rs.getInt(5));
				myc.setCoforpassed(rs.getInt(6));
				myc.setSortid(rs.getInt(7));
				mycs.add(myc);
			}
				
		} catch (Exception e) {
			logger.error("查询详情列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mycs;
	}
	
	/**
	 * 培训班上下移动
	 */
	public void sortCps(int elclassid, int sortid, int upordown,int batchid)
		throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			if (upordown == ElConstants.SORT_UP) {
				upSort(ct, elclassid, sortid,batchid);
		
			} else {
				downSort(ct, elclassid, sortid,batchid);
			}
		} catch (Exception e) {
			logger.error("移动网页失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	private void upSort(Connection ct, int elclassid, int sortid,int batchid)
		throws ElException {
	try {
		Statement st = ct.createStatement();
		if (sortid > 0) {
			String sql = "select elclassid from batch_elclass_fenpei where batchid = "
					+ batchid + " and sortid = " + (sortid - 1);
			ResultSet rs = st.executeQuery(sql);
			int nextId = 0;
			if (rs.next())
				nextId = rs.getInt(1);
			rs.close();
			if (nextId != 0) {
				sql = "update batch_elclass_fenpei set sortid=sortid-1 "
						+ " where elclassid = " + elclassid + " and batchid="
						+ batchid;
				st.executeUpdate(sql);
				sql = "update batch_elclass_fenpei set sortid=sortid+1 "
						+ " where elclassid = " + nextId;
				st.executeUpdate(sql);
			}
		}
		st.close();
	
	} catch (Exception e) {
		logger.error("网页上移失败！", e);
		throw new ElException("网页上移失败", e);
	}
	}
	
	private void downSort(Connection ct, int elclassid, int sortid,int batchid)
		throws ElException {
	try {
		Statement st = ct.createStatement();
		String sql = "select max(sortid) from batch_elclass_fenpei where batchid= "
				+ batchid;
		ResultSet rs = st.executeQuery(sql);
		int maxSortid = 0;
		if (rs.next()) {
			maxSortid = rs.getInt(1);
		}
		rs.close();
		if (sortid < maxSortid) {
			sql = "select elclassid from batch_elclass_fenpei where   sortid = " + (sortid + 1);
			rs = st.executeQuery(sql);
			int nextId = 0;
			if (rs.next())
				nextId = rs.getInt(1);
			rs.close();
			if (nextId != 0) {
				sql = "update batch_elclass_fenpei set sortid=sortid+1 "
						+ " where elclassid = " + elclassid + " and sortid="
						+ sortid;
				st.executeUpdate(sql);
				sql = "update batch_elclass_fenpei set sortid=sortid-1 "
						+ " where elclassid = " + nextId;
				st.executeUpdate(sql);
			}
		}
		st.close();
	} catch (Exception e) {
		logger.error("网页下移失败！", e);
		throw new ElException("网页下移失败", e);
	}
	}

	public void updateBatchProcess(int batchid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call set_batch_process(?,?)");
			ps.setInt(1, batchid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新培训batch进度失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
		
	
	/**
	 * 获取培训班list
	 * 
	 * @param tree
	 * @param elclass
	 * @param sublibs
	 * @param status
	 * @param sqlw
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getClassList(ElNode tree, ElClass elclass,
			int sublibs, String status, String sqlw, int pageNow, int pageSize,int peixunBatchId)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select * from (select t.*, rownum rn from ("
							+ "select cl.id, cl.name, cl.certificatename, cl.cltype, cl.optionalcredit, cl.status,"
							+ " clt.name cltname,u.realname,cl.createtime , cl.astauts ,cl.starttime,cl.finishtime,"
							+ " sc.usercount classsize,cl.isApplication,elr.planrecruitstudents,cl.depName,cl.jingzhong "
							+ " from elclass cl inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"elclasstype", tree, consub)
							+ ") clt on cl.cltype = clt.id "
							+ "left join eluser u on cl.creater = u.id "
							+ "left join department dep on u.depid = dep.id "
							+ "left join (select classid,count(userid) usercount from study_class group by classid) sc on sc.classid=cl.id "
							+ "left join elclass_registration elr on elr.classid=cl.id"
							+ "  where cl.status in(" + status + ") ");
			this.checkClassParam(buffer, params, elclass);
			buffer.append("  and cl.id not in (select cc.elclassid "+
                    "   from batch_elclass_fenpei cc "+
                    "  where cc.batchid = "+peixunBatchId+")");
			buffer.append("  order by cl.createtime desc) t where rownum <= ?) where rn >= ? ");
			ps = ct.prepareStatement(buffer.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ps.setInt(params.size() + 1, pageNow);
			ps.setInt(params.size() + 2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				ElClass cl = new ElClass(id, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setAstatus(rs.getInt(10));
				ElClType clType = new ElClType();
				clType.setName(rs.getString(7));
				cl.setCltype(clType);
				ELUser user = new ELUser();
				user.setRealname(rs.getString(8));
				cl.setCreater(user);
				cl.setCreatetime(rs.getTimestamp(9));
				cl.setStarttime(rs.getTimestamp(11));
				cl.setFinishtime(rs.getTimestamp(12));
				cl.setClassSize(rs.getInt(13));
				cl.setIsApplication(rs.getInt(14));
				cl.setPlanNumber(rs.getInt(15));
				cl.setIsUvalid(checkElclassIsUvalid("valids", id) ? "true"
						: "false");// ???
				cl.setDepName(rs.getString(16));
				cl.setJingzhong(rs.getString(17));
				// cl.setStudentCount(rs.getInt(13));
				if (isOpen(id)) {
					cl.setOperation(1);// 考场全部开通
				} else {
					if (checkuserClassEroomOperation(id, sqlw))
						cl.setOperation(2);// 有可操作
					else
						cl.setOperation(3);// 无可操作
				}
				cl.setExamRooms(getClassRooms(cl.getId()));
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班列表失败!失败方法：getClassList 失败原因：" + new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 获取培训班list数量
	 * 
	 * @param tree
	 * @param elclass
	 * @param sublibs
	 * @param status
	 * @param sqlw
	 * @return
	 * @throws ElException
	 */
	public int getClassListSize(ElNode tree, ElClass elclass, int sublibs,
			String status,int peixunBatchId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(cl.id) "
							+ " from elclass cl inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"elclasstype", tree, consub)
							+ ") clt on cl.cltype = clt.id "
							+ " left join eluser u on cl.creater=u.id "
							+ " left join elclass_registration elr on elr.classid=cl.id "
							+ "  where cl.status in(" + status + ") ");
			this.checkClassParam(buffer, params, elclass);
			buffer.append("  and cl.id not in (select cc.elclassid "+
                    "   from batch_elclass_fenpei cc "+
                    "  where cc.batchid = "+peixunBatchId+")");
			ps = ct.prepareStatement(buffer.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班列表数量失败!失败方法：getClassListSize 失败原因："
							+ new ElException(e));
			logger.error("获取培训班列表数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 检测参数
	 * 
	 * @param sql
	 * @param params
	 * @param elclass
	 */
	public void checkClassParam(StringBuffer sql, List<Object> params,
			ElClass elclass) {
		if (elclass != null) {
			if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
				sql.append(" and cl.name like ? escape '/'");
				params.add("%" + StringUtil.toLikeStr(elclass.getName()) + "%");
			}
			if (elclass.getOwner() != null) {
				if (elclass.getOwner().getUsername() != null
						&& !elclass.getOwner().getUsername().equals("")) {// 培训名称
					sql.append(" and u.username like ?");
					params.add("%"
							+ StringUtil.toLikeStr(elclass.getOwner()
									.getUsername()) + "%");
				}
				if (elclass.getOwner().getRealname() != null
						&& !elclass.getOwner().getRealname().equals("")) {// 培训名称
					sql.append(" and u.realname like ?");
					params.add("%"
							+ StringUtil.toLikeStr(elclass.getOwner()
									.getRealname()) + "%");
				}
			}
			if (elclass.getStatus() != -1) {// 考场状态
				sql.append(" and cl.status=?");
				params.add(elclass.getStatus());
			}
			if (elclass.getBegintime() != null) {
				// sql.append(" and cl.STARTTIME >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getBegintime())+ "','yyyy-MM-dd
				// HH24:mi:ss')");
				sql.append(" and cl.STARTTIME >= ?");
				params.add(elclass.getBegintime());
			}
			if (elclass.getEndtime() != null) {
				// sql.append(" and cl.FINISHTIME <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')");
				sql.append(" and cl.FINISHTIME <= ?");
				params.add(elclass.getEndtime());
			}
			if (elclass.getElRegistration() != null
					&& elclass.getElRegistration().getIsAudit() == 1) {
				sql.append(" and elr.isAudit=1 ");
			}
		}
	}
	
	/**
	 * 查看是培训班否有符合人员
	 * 
	 * @param type
	 * @param elclassid
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassIsUvalid(String type, int elclassid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from elclass_" + type
					+ "  where classid = ?");
			ps.setInt(1, elclassid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"验证培训班否有符合人员失败!失败方法：checkElclassIsUvalid(String type, int elclassid) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班否有符合人员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkElclassUvalid(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select uvalid from elclass where id = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next())
				if (rs.getInt(1) == 1) {
					return true;
				}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验证培训班复核人员失败!失败方法：checkElclassUvalid(int classid) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班复核人员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	/**
	 * 获取培训班中所有被绑定的考场
	 * 
	 * @param classId
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getClassRooms(int classId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> examRooms = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eroomid,er.title,valid,cc.courseid,cc.classid from class_course cc inner join exam_room er on cc.eroomid=er.id where cc.classId  = ?  and cc.binding = 1");//
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			ExamRoom examRoom = null;
			Course course = null;
			while (rs.next()) {
				examRoom = new ExamRoom(rs.getInt(1), rs.getString(2));
				examRoom.setValid(rs.getInt(3));
				course = new Course();
				course.setId(rs.getInt(4));
				course.setClassid(rs.getInt(5));
				examRoom.setCourse(course);
				examRooms.add(examRoom);
			}
		} catch (Exception e) {
			logger.error("获取培训班中所有被绑定的考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examRooms;
	}
	
	public boolean checkuserClassEroomOperation(int classId, String sqlw)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select  (select id from exam_room where valid in ("
							+ sqlw
							+ ") and id=cc.eroomid) evalid  "
							+ "from course c, class_course cc where cc.courseid = c.id  and cc.classid = ? and cc.status in (0,1)");
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验证培训班课程失败!失败方法：checkuserClassEroomOperation(int classId, String sqlw) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
		}
	
	
	
	public boolean isOpen(int classId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean is = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id ,er.valid from course c ,class_course cc ,exam_room er  "
							+ "where cc.courseid = c.id and er.id = cc.eroomid and cc.classid = ? and cc.status in (0,1)");
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt(2) != 5) {
					is = false;
					break;
				} else {
					is = true;
				}
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验是否开通失败!失败方法：isOpen(int classId) 失败原因："
							+ new ElException(e));
			logger.error("验是否开通失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return is;
	}
	
	public int getDoneOrNowElClassIdSearch(int batchid,int userid,int type,Map<String,Object> query) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int classid = 0;
		boolean finish  = false;
		int temp_classid = 0;
		ClassDao classDao = new ClassDaoImpl();
		
		String sqlWhere = "";
		if(query!= null){
			if(query.containsKey("start_date") && query.containsKey("end_date")){
				sqlWhere = " and sc.applydate >=to_date('"+query.get("start_date")+"','yyyy-MM-dd HH24:MI:SS')";
				sqlWhere+= " and sc.applydate <=to_date('"+query.get("end_date")+"','yyyy-MM-dd HH24:MI:SS')";
			}
		}
		
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sc.classid,sc.process from study_class sc " +
					" left join BATCH_ELCLASS_FENPEI bef on bef.elclassid=sc.classid " +
					" where sc.userid=? and bef.batchid=? " +sqlWhere+
					" order by bef.sortid asc ");
			ps.setInt(1, userid);
			ps.setInt(2, batchid);
			rs = ps.executeQuery();
			while(rs.next()){
				if(type == 1){
					if(rs.getFloat(2)==100){
						finish = classDao.checkClassExamIsPass(rs.getInt(1), userid);
						if(finish){
							temp_classid = rs.getInt(1);
						}
					}else{
						classid=temp_classid;
						break;
					}
				}else{
					if(rs.getFloat(2)<100){
						classid = rs.getInt(1);
						break;
					}else if(rs.getFloat(2)==100){
						finish = classDao.checkClassExamIsPass(rs.getInt(1), userid);
						if(!finish){
							classid = rs.getInt(1);
							return classid;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classid;
	}
	
	public int getDoneOrNowElClassId(int batchid,int userid,int type) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int classid = 0;
		boolean finish  = false;
		int temp_classid = 0;
		ClassDao classDao = new ClassDaoImpl();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sc.classid,sc.process from study_class sc " +
					" left join BATCH_ELCLASS_FENPEI bef on bef.elclassid=sc.classid " +
					" where sc.userid=? and bef.batchid=? " +
					" order by bef.sortid asc ");
			ps.setInt(1, userid);
			ps.setInt(2, batchid);
			rs = ps.executeQuery();
			while(rs.next()){
				if(type == 1){
					if(rs.getFloat(2)==100){
						finish = classDao.checkClassExamIsPass(rs.getInt(1), userid);
						if(finish){
							temp_classid = rs.getInt(1);
						}
					}else{
						classid=temp_classid;
						break;
					}
				}else{
					if(rs.getFloat(2)<100){
						classid = rs.getInt(1);
						break;
					}else if(rs.getFloat(2)==100){
						finish = classDao.checkClassExamIsPass(rs.getInt(1), userid);
						if(!finish){
							classid = rs.getInt(1);
							return classid;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classid;
	}
	
	public List<ElClass> getDoneOrNowElClassSearch(String userid,int batchid,int type)throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClass cl = null;
		List<ElClass> eSet = new ArrayList<ElClass>(); 
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			
			//正在学习和已完成的判断条件修改
			//加入了培训班对应等级是否通过
			String uids[] = userid.split(",");
			String classId = "";
			
			for (String string : uids) {
				int num = this.getDoneOrNowElClassId(batchid,Integer.parseInt(string),type);
				if(num>0){
					classId+=num+",";
				}
			}
			
			sqlwhere += " and sc.classid in( '"+classId +"')" ;
//			if(type == 0){//正在学习
//				sqlwhere += " and sc.process<100 ";
//			}else{//已完成
//				sqlwhere += " and sc.process=100 ";
//			}
			
			sql = " select a.* from " +
					" (select el.id,el.name,sc.userid,sc.process from BATCH_ELCLASS_FENPEI bef "+
					" left join study_class sc on bef.elclassid=sc.classid and sc.userid in( "+userid + ")"+
					" left join elclass el on bef.elclassid=el.id "+
					" where bef.batchid=? " + sqlwhere + 
					" order by sc.process desc,bef.sortid asc) a" +
					" where rownum=1 ";
			
			ps = ct.prepareStatement(sql);
			ps.setInt( 1, batchid);
			rs = ps.executeQuery();
			while (rs.next()) {
				cl = new ElClass();
				cl.setId(rs.getInt(1));
				cl.setName(rs.getString(2));
				MyClass myClass = new MyClass();
				myClass.setProcess(rs.getDouble(4));
				cl.setMyClass(myClass);
				
				if(type == 0){//正在学习
					cl = cl == null ? new ElClass(-1,"暂无"):cl;
				}else{//已完成
					cl = cl == null ? new ElClass(-1,"暂无"):cl;
				}
				eSet.add(cl);
			}
			
			
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班列表失败!失败方法：getClassList 失败原因：" + new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return eSet;
	}

	public ElClass getDoneOrNowElClass(int batchid, int userid ,int type)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClass cl = null;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			
			//正在学习和已完成的判断条件修改
			//加入了培训班对应等级是否通过
			System.out.println(batchid);
			sqlwhere += " and sc.classid= " + this.getDoneOrNowElClassId(batchid,userid,type);
//			if(type == 0){//正在学习
//				sqlwhere += " and sc.process<100 ";
//			}else{//已完成
//				sqlwhere += " and sc.process=100 ";
//			}
			
			sql = " select a.* from " +
					" (select el.id,el.name,sc.userid,sc.process from BATCH_ELCLASS_FENPEI bef "+
					" left join study_class sc on bef.elclassid=sc.classid and sc.userid=? " + 
					" left join elclass el on bef.elclassid=el.id "+
					" where bef.batchid=? " + sqlwhere + 
					" order by sc.process desc,bef.sortid asc) a" +
					" where rownum=1 ";
			
			ps = ct.prepareStatement(sql);
			ps.setInt( 1, userid);
			ps.setInt( 2, batchid);
			rs = ps.executeQuery();
			if (rs.next()) {
				cl = new ElClass();
				cl.setId(rs.getInt(1));
				cl.setName(rs.getString(2));
				MyClass myClass = new MyClass();
				myClass.setProcess(rs.getDouble(4));
				cl.setMyClass(myClass);
			}
			if(type == 0){//正在学习
				cl = cl == null ? new ElClass(-1,"暂无"):cl;
			}else{//已完成
				cl = cl == null ? new ElClass(-1,"暂无"):cl;
			}
			
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班列表失败!失败方法：getClassList 失败原因：" + new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cl;
	}

	public boolean checkPeixunBatchIsAssignToUser(int batchid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag  = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(1) from batch_user_fenpei where userid=? and  batchid=?");
			ps.setInt(1, userid);
			ps.setInt(2, batchid);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0)
					flag = true;
			}
		} catch (Exception e) {
			logger.error("判断培训批次是否分配给用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public double getPeixunBatchProcess(int batchid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		double flag  = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select process from batch_user_fenpei where userid=? and  batchid=?");
			ps.setInt(1, userid);
			ps.setInt(2, batchid);
			rs = ps.executeQuery();
			if(rs.next()){
				flag = rs.getDouble(1);
			}
		} catch (Exception e) {
			logger.error("用户培训batch进度失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public List<ElClass> listElClasses() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> elclasses = new ArrayList<ElClass>();
		ElClass elclass = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select e.id,e.name from batch_elclass_fenpei bef left join elclass e on bef.elclassid=e.id order by bef.sortid asc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				elclass = new ElClass(rs.getInt(1),rs.getString(2));
				elclasses.add(elclass);
			}
		} catch (Exception e) {
			logger.error("系统培训班学习情况出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elclasses;
	}
}
