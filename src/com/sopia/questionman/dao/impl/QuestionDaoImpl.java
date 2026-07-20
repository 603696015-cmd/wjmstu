package com.sopia.questionman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Typelrid;
import com.sopia.questionman.QuestionConstants;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionArt;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.questionman.tags.QLibTree;
import com.sopia.studyman.entities.MyExamPaper;

public class QuestionDaoImpl extends ElNodeDao implements QuestionDao {
	private static final Log logger = LogFactory.getLog(QuestionDaoImpl.class);

	public QuestionLib getQlibTree(int id, int userId, int stopid,
			boolean isContainStop) throws ElException {
		QuestionLib qls = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			if (id == 0)
				qls = getQLbRoot();
			else
				qls = getQLbById(id);
			ct = DBConnection.getConnection();
			qls.setChild(listQuestionLibById(qls.getId(), userId, stopid,
					isContainStop, 0, ct));
		} catch (Exception e) {
			logger.error("获取题库树出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qls;
	}

	private QuestionLib getQlibTree(int id, int stopid, boolean isContainStop,
			int level) throws ElException {
		QuestionLib qls = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			qls = getQLbById(id);
			if(qls==null||qls.getId()==0){
				return qls;
			}
			qls.setLevel(level);
			ct = DBConnection.getConnection();
			qls.setChild(listQuestionLibById(qls.getId(), stopid,
					isContainStop, level, ct));
		} catch (Exception e) {
			logger.error("获取题库树出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qls;
	}

	private List<QuestionLib> listQuestionLibById(int parentid, int stopid,
			boolean isContainStop, int level, Connection ct) throws Exception {
		List<QuestionLib> qls = new ArrayList<QuestionLib>();
		PreparedStatement pstemp = ct
				.prepareStatement("select id,name ,parentid,description, lid, rid  from question_lib where parentid = ? and status!=1");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			QuestionLib qlb = new QuestionLib(rstemp.getInt(1), rstemp
					.getString(2));
			qlb.setParent(new QuestionLib(rstemp.getInt(3)));
			qlb.setDescription(rstemp.getString(4));
			qlb.setLevel(level);

			if (qlb.getId() != stopid)
				qlb.setChild(listQuestionLibById(qlb.getId(), stopid,
						isContainStop, level, ct));
			if (!isContainStop && qlb.getId() == stopid) {

			} else
				qls.add(qlb);
		}
		rstemp.close();
		pstemp.close();
		return qls;
	}
public QuestionLib getQlibTree(int userid, String op, int stopid,
			boolean isContainStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
//		QuestionLib dep = op.equals("op") ? new QuestionLib(1, "可操作的题库")
//				: new QuestionLib(1, "可使用的题库");
		QuestionLib dep = new QuestionLib(ElConstants.USER_OP_LIB, "可操作的题库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from questionlib_" + op
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<QuestionLib> list = new ArrayList<QuestionLib>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !isContainStop) {
				} else {
					QuestionLib depc = getQlibTree(depid, stopid,
							isContainStop, 1);
					if(depc==null||depc.getId()==0){
						continue;
					}
					depc.setParent(dep);
					list.add(depc);
					nlist.add(depc);
				}
			}
			dep.setNchild(nlist);
			dep.setChild(list);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	

	public QuestionLib getQLbRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		QuestionLib qlb = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QLIB_QUERY_BYPARENTIDANDUID));// and
			ps.setInt(1, ElConstants.TREE_ROOT);
			rs = ps.executeQuery();
			if (rs.next()) {
				qlb = new QuestionLib(rs.getInt(1), rs.getString(2));
				qlb.setParent(new QuestionLib(rs.getInt(3)));
				qlb.setDescription(rs.getString(4));
				qlb.setLid(rs.getInt(5));
				qlb.setRid(rs.getInt(6));
			}
			rs.close();
			// if (qlb == null) {
			// qlb = new QuestionLib();
			// qlb.setName("我的题库");
			// qlb.setParent(new QuestionLib(0));
			// addQuestionLib(qlb);
			// }
		} catch (Exception e) {
			logger.error("查看题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qlb;
	}

	public int getQlibId(String name, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QLIB_QUERYID_BYIDANDUID));
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取题库id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

	private List<QuestionLib> listQuestionLibById(int parentid, int userId,
			int stopid, boolean isContainStop, int level, Connection ct)
			throws Exception {
		List<QuestionLib> qls = new ArrayList<QuestionLib>();
//		PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
//				.getSQL(QuestionConstants.QLIB_QUERY_BYPARENTIDANDUID));
		PreparedStatement pstemp = ct.prepareStatement("select id,name ,parentid,description, lid, rid  from question_lib where parentid = ? and status!=1 order by id");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			QuestionLib qlb = new QuestionLib(rstemp.getInt(1), rstemp
					.getString(2));
			qlb.setParent(new QuestionLib(rstemp.getInt(3)));
			qlb.setDescription(rstemp.getString(4));
			qlb.setLevel(level);

			if (qlb.getId() != stopid)
				qlb.setChild(listQuestionLibById(qlb.getId(), userId, stopid,
						isContainStop, level, ct));
			if (!isContainStop && qlb.getId() == stopid) {

			} else
				qls.add(qlb);
		}
		rstemp.close();
		pstemp.close();
		return qls;
	}

	public void addQuestionLib(QuestionLib questionLib) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

//			addNode(ct, questionLib, "QUESTION_LIB", "1 = 1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QLIB_ADD));
			ps.setString(1, questionLib.getName());
			ps.setInt(2, questionLib.getParent().getId());
			ps.setString(3, questionLib.getDescription());
			ps.setInt(4, questionLib.getLid());
			ps.setInt(5, questionLib.getRid());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('QUESTION_LIB') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select questionlib_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				questionLib.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public int addQuestionLibToId(QuestionLib questionLib) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

//			addNode(ct, questionLib, "QUESTION_LIB", "1 = 1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QLIB_ADD));
			ps.setString(1, questionLib.getName());
			ps.setInt(2, questionLib.getParent().getId());
			ps.setString(3, questionLib.getDescription());
			ps.setInt(4, questionLib.getLid());
			ps.setInt(5, questionLib.getRid());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('QUESTION_LIB') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select questionlib_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				questionLib.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return questionLib.getId();
	}

	/*
	 * public int addQLib(QuestionLib questionLib) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * int id = 0; try { ct = DBConnection.getConnection(); addNode(ct,
	 * questionLib, "QUESTION_LIB", "userid =" +
	 * questionLib.getElUser().getId()); ps = ct.prepareStatement(ElQuerySql
	 * .getSQL(QuestionConstants.QLIB_ADD)); ps.setString(1,
	 * questionLib.getName()); ps.setInt(2, questionLib.getElUser().getId());
	 * ps.setInt(3, questionLib.getParent().getId()); ps.setString(4,
	 * questionLib.getDescription()); ps.setInt(5, questionLib.getLid());
	 * ps.setInt(6, questionLib.getRid()); ps.executeUpdate(); rs =
	 * ps.getGeneratedKeys(); if (rs.next()) id = rs.getInt(1); } catch
	 * (Exception e) { logger.error("添加题库出错！", e); throw new ElException(e); }
	 * finally { DBConnection.closeConnectInfo(ct, ps, rs); } return id; }
	 */
	/*
	 * public QuestionLib getQLbById(int id ) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * try { ct = DBConnection.getConnection(); ps =
	 * ct.prepareStatement(ElQuerySql
	 * .getSQL(QuestionConstants.QLIB_QUERY_BYIDANDUID));// and // q.parentid=0
	 * ps.setInt(1, id); ps.setInt(2, userid); rs = ps.executeQuery(); if
	 * (rs.next()) { QuestionLib qlb = new QuestionLib(rs.getInt(1),
	 * rs.getString(2)); qlb.setParent(new QuestionLib(rs.getInt(3),
	 * rs.getString(5))); qlb.setDescription(rs.getString(4));
	 * qlb.setLid(rs.getInt(6)); qlb.setRid(rs.getInt(7)); return qlb; } } catch
	 * (Exception e) { logger.error("查看题库出错！", e); throw new ElException(e); }
	 * finally { DBConnection.closeConnectInfo(ct, ps, rs); } return new
	 * QuestionLib(); }
	 */

	public QuestionLib getQLbById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.name,q.parentid,q.description,qp.name,q.lid,q.rid from question_lib q left join question_lib qp on q.parentid = qp.id and qp.status!=1 where q.id = ? and q.status!=1");// and
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				QuestionLib qlb = new QuestionLib(rs.getInt(1), rs.getString(2));
				qlb.setParent(new QuestionLib(rs.getInt(3), rs.getString(5)));
				qlb.setDescription(rs.getString(4));
				qlb.setLid(rs.getInt(6));
				qlb.setRid(rs.getInt(7));
				return qlb;
			}
		} catch (Exception e) {
			logger.error("查看题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return new QuestionLib();
	} 
	  
	/**
	 * 根据上级parentid 获取parentid的下级树里是否有  name节点
	 * 有  返回0 
	 * 没有。  创建该name节点 上级节点为parentid
	 * @param parentid
	 * @param name
	 * @return 0  or  创建的id 
	 * @throws ElException
	 */
	public int getLowerIdById(int parentid , String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.name,q.parentid,q.description,q.name from question_lib q  where parentid = ? and name =?"); 
			ps.setInt(1, parentid);
			ps.setString(2, name);
			rs = ps.executeQuery(); 
			if(rs.next()){
				return rs.getInt(1);
			}else{
				QuestionLib questionLib = new QuestionLib(); 
				questionLib.setName(name);
				questionLib.setParent(new ElNode(parentid));
				id = addQuestionLibToId(questionLib);
			} 
		} catch (Exception e) {
			logger.error("查看题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	} 

	public void alterQLB(QuestionLib qlb) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			alterNode(ct, qlb, "QUESTION_LIB", "1 =1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QLIB_ALTER));
			ps.setString(1, qlb.getName());
			// ps.setInt(2, qlb.getElUser().getId());
			ps.setInt(2, qlb.getParent().getId());
			ps.setString(3, qlb.getDescription());
			ps.setInt(4, qlb.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteQLB(int id, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// TODO 删除试题库
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from QUESTION_LIB where id =? ");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新题库类别状态
	 * @param id
	 * @param userid
	 * @throws ElException
	 */
	public void deleteQLibNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update QUESTION_LIB set status=1,lid=0,rid=0 where id =? ");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新题库类别状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 删除试题库
	 * @param id
	 * @throws ElException
	 */
	public void deleteQlibAndSub(int id) throws ElException {
		//查出该类别的左右id，然后查出所有子类别，然后循环根据id删除子类别，删除所有类别下的课程最后删除该类别
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid=this.getLidRid(ct,id, "question_lib");
			List<Integer> typelist=this.getTypeByLidRid(ct,typelrid.getLid(), typelrid.getRid(), "question_lib");
			for (int i = 0; i < typelist.size(); i++) {
				//根据id删除类别以及类别下的资源(先删资源)
				this.deleteQuestionByTypeid(ct,typelist.get(i));
				this.deleteQLB(typelist.get(i),0);
				
			}
		} catch (Exception e) {
			logger.error("删除试卷库以及下级试卷库和试卷失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 假删除试题库
	 * @param id
	 * @throws ElException
	 */
	public void deleteQlibAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid=this.getLidRid(ct,id, "question_lib");
			List<Integer> typelist=this.getTypeByLidRid(ct,typelrid.getLid(), typelrid.getRid(), "question_lib");
			for (int i = 0; i < typelist.size(); i++) {
				this.deleteQuestionByTypeidNot(typelist.get(i));
				this.deleteQLibNot(typelist.get(i));
				
			}
		} catch (Exception e) {
			logger.error("删除试卷库以及下级试卷库和试卷失败！", e);
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

	public void setQLBparent(int id, int parentid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QLIB_PARENT_SET));
			ps.setInt(1, parentid);
			ps.setInt(2, id);
			// ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 设置上级题库
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setQLBparent2(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update question_lib set parentid=? where parentid =?");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级题库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 设置上级试题
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setQLparent(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update question set qlibid=? where qlibid =?");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<QuestionLib> listChild(int id, int userid) throws ElException {
		List<QuestionLib> qls = new ArrayList<QuestionLib>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QLIB_QUERY_BYPARENTIDANDUID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				QuestionLib qlb = new QuestionLib(rs.getInt(1), rs.getString(2));
				qlb.setParent(new QuestionLib(rs.getInt(3)));
				qlb.setDescription(rs.getString(4));
				qls.add(qlb);
			}
		} catch (Exception e) {
			logger.error("获取直接下级部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qls;
	}

	public List<QuestionLib> listQlibs(int roleid, int userid)
			throws ElException {

		List<QuestionLib> qls = new ArrayList<QuestionLib>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (roleid == 1) {
				ps = ct.prepareStatement("select id,name from question_lib ");

			} else {
				ps = ct
						.prepareStatement("select ql.id,ql.name from question_lib ql left join questionlib_op_user qou on qou.depid = ql.id where qou.userid = ?");
				ps.setInt(1, userid);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				QuestionLib qlb = new QuestionLib(rs.getInt(1), rs.getString(2));
				qls.add(qlb);
			}
		} catch (Exception e) {
			logger.error("获取直接下级部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qls;
	}

	public void addQuestion(Question question) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QUESTION_ADD));
			ps.setString(1, question.getTitle());
			ps.setString(2, question.getContent());
			ps.setString(3, question.getSubject());
			ps.setString(4, question.getQexplain());
			ps.setInt(5, question.getEluser().getId());
			ps.setInt(6, question.getQlib().getId());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			ps.setInt(8, question.getQlevel());
			ps.setString(9, question.getAnswer());
			ps.setInt(10, question.getQtype());
			ps.setInt(11, question.getScoreper());
			ps.setInt(12, question.getParent().getId());
			ps.setInt(13, question.getMinWord());
			ps.setInt(14, question.getSortid());
			ps.setString(15, question.getOldrulestring());
			ps.setFloat(16, question.getOldscore());
			ps.setInt(17, question.getFwsize());
		
			//默认试题状态为2编辑中
			ps.setInt(18, 2);
			ps.setString(19,question.getFashengQuestion());
			ps.setString(20, question.getMediaFile());
			ps.setString(21, question.getModelVoice());
			ps.setString(22, question.getModelVoiceText());
			ps.setString(23,question.getVoicePath());
			ps.setString(24, question.getFenContent());
			ps.setString(25, question.getStemText());
			ps.setString(26, question.getFrontHalfMediaFile());
			ps.setString(27, question.getStandardAnswer());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('question') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps.close();
				ps = ct
						.prepareStatement("select question_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				question.setId(rs.getInt(1));
//			ps.close();
//			rs.close();
		} catch (Exception e) {
			logger.error("添加试题出错！", e);
			throw new ElException("添加试题出错！");
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Question> listMyQuestions(String title, int libfrom, int type,
			boolean conSub, int pn, int pS) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			// String typeS = "";
			// if (type != 0)
			// typeS = type + "";
			if (null == title)
				title = "";
			else
				title = title.trim();
			if(libfrom==0){
				libfrom=1;
			}
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB));
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
					ps.setInt(4, pn);
					ps.setInt(5, pS);
				} else {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());
					ps.setInt(5, pn);
					ps.setInt(6, pS);

				}
			} else {
				if (type == 0) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(QuestionConstants.QUESTION_MAN_MYLIST));
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, libfrom);
					ps.setInt(3, pn);
					ps.setInt(4, pS);
				} else {

					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, libfrom);
					ps.setInt(4, pn);
					ps.setInt(5, pS);

				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setStatus(rs.getInt(12));
				q.setEluser(new ELUser());
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	/**
	 * 用于材料题的添加小题 
	 * @param title
	 * @param libfrom
	 * @param type
	 * @param conSub
	 * @param pn
	 * @param pS
	 * @return
	 * @throws ElException
	 */
	public List<Question> listMyQuestions2(String title, int libfrom, int type,
			boolean conSub, int pn, int pS) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			// String typeS = "";
			// if (type != 0)
			// typeS = type + "";
			if (null == title)
				title = "";
			else
				title = title.trim();
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status from question q ,question_lib qlib " +
							" where q.qlibid=qlib.id and q.qtype in(1,2,4,5,6) and  q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
					ps.setInt(4, pn);
					ps.setInt(5, pS);
				} else {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());
					ps.setInt(5, pn);
					ps.setInt(6, pS);

				}
			} else {
				if (type == 0) {
					ps = ct.prepareStatement(" select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status from question q ,question_lib qlib " +
							" where q.qlibid=qlib.id and q.qtype in(1,2,4,5,6) and q.title like ? and qlib.id=? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, libfrom);
					ps.setInt(3, pn);
					ps.setInt(4, pS);
				} else {

					ps = ct.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, libfrom);
					ps.setInt(4, pn);
					ps.setInt(5, pS);

				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setStatus(rs.getInt(12));
				q.setEluser(new ELUser());
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}

	/**
	 *  无分页用于导出
	 */
	public List<Question> listMyQuestions(String title, int libfrom, int type,
			boolean conSub) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			// String typeS = "";
			// if (type != 0)
			// typeS = type + "";
			if (null == title)
				title = "";
			else
				title = title.trim();
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id from question q ,question_lib qlib w" +
							"here q.qlibid=qlib.id and  q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0  order by q.createtime desc ");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid()); 
				} else {
					ps = ct
							.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id from question q ,question_lib qlib " +
									"where q.qlibid=qlib.id and q.qtype=? and q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0  order by q.createtime desc ");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid()); 

				}
			} else {
				if (type == 0) {
					ps = ct.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id " +
							"from question q ,question_lib qlib where q.qlibid=qlib.id and q.title like ? and qlib.id=? and q.parentid=0  order by q.createtime desc ");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, libfrom); 
				} else {

					ps = ct
							.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id " +
									"from question q ,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.title like ? and qlib.id=? and q.parentid=0  order by q.createtime desc");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, libfrom); 

				}
			}
			rs = ps.executeQuery();
			
			String answer = "";
			String newtestsupport = ""; 
			while (rs.next()) {
				Question q = new Question();
				q.setQtype(rs.getInt(1));
				q.setTitle(rs.getString(2));
				q.setSubject(rs.getString(3));
				if (rs.getInt(1) == 10 || rs.getInt(1) == 5
						|| rs.getInt(1) == 8 || rs.getInt(1) == 6
						|| rs.getInt(1) == 9 || rs.getInt(1) == 1) {
					q.setTestsupport("");
				} else {
					if (rs.getString(3) != null) {
						if (rs.getString(3).contains(ElConstants.optSplit)) {
							newtestsupport = rs.getString(3).substring(
									0,
									rs.getString(3).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							newtestsupport = rs.getString(3);
						}
					}
					q.setTestsupport(newtestsupport);
				}
				if (rs.getInt(1) != 9) {
					if (rs.getInt(1) == 5) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					} else {
						if (rs.getString(4) != null) {
							if (rs.getInt(1) == 10) {
								if (rs.getString(4).trim().contains(
										ElConstants.optSplit)) {
									answer += rs.getString(4).substring(
											0,
											rs.getString(4).lastIndexOf(
													ElConstants.optSplit))
											.replaceAll(ElConstants.optSplit,
													"==");
								} else {
									answer = rs.getString(4).trim();
								}
							} else {
								for (int i = 0; i < rs.getString(4).split(
										ElConstants.optSplit).length; i++) {

									if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("0")) {
										answer += "A ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("1")) {
										answer += "B ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("2")) {
										answer += "C ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("3")) {
										answer += "D ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("yes")) {
										answer += "正确";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("no")) {
										answer += "错误";
									} else {
										answer += rs.getString(4).split(
												ElConstants.optSplit)[i].trim();
									}
								}
							}
						}
					}
				} else {
					if (rs.getString(4) != null) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					}
				}
				q.setAnswer(answer);
				q.setQlevel(rs.getInt(5));
				q
						.setOldrulestring(rs.getInt(1) != 9 ? rs.getString(6)
								: rs.getString(10).contains(
										ElConstants.ruleSplit) ? rs
										.getString(10)
										.substring(
												0,
												rs.getString(10).lastIndexOf(
														ElConstants.ruleSplit))
										.replaceAll(ElConstants.ruleSplit, "==")
										: rs.getString(10));
				QuestionLib questionLib = new QuestionLib();
				questionLib.setId(rs.getInt(11));
				questionLib.setName(rs.getString(7));
				q.setQlib(questionLib);
				q.setQexplain(rs.getString(8));
				q.setContent(rs.getString(9)); 
				qs.add(q);
				answer = "";
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}

	public List<Question> listMyQuestions(String title, int libfrom, int type,
			boolean conSub,String sqlw, int pn, int pS) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			// String typeS = "";
			// if (type != 0)
			// typeS = type + "";
			if (null == title)
				title = "";
			else
				title = title.trim();
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype," +
							"qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib where q.qlibid=qlib.id and  " +
							"q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
					ps.setInt(4, pn);
					ps.setInt(5, pS);
				} else {
					ps = ct
							.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name," +
							"q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib 	where q.qlibid=qlib.id and q.qtype=? " +
							"and q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());
					ps.setInt(5, pn);
					ps.setInt(6, pS);

				}
			} else {
				if (type == 0) {
					ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name," +
							"q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib where q.qlibid=qlib.id and q.title like ? and qlib.id=? " +
							"and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, libfrom);
					ps.setInt(3, pn);
					ps.setInt(4, pS);
				} else {

					ps = ct
							.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel," +
									"q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.title like ? " +
									"and qlib.id=? and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, libfrom);
					ps.setInt(4, pn);
					ps.setInt(5, pS);

				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setStatus(rs.getInt(12));
				q.setEluser(new ELUser());
				q.setFwsize(rs.getInt(13));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	
	public int listMyQuestionsSize(String title, int libfrom, int type,
			boolean conSub) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();
			if(libfrom==0){
				libfrom=1;
			}
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_SIZE));
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
				} else {

					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_SIZE_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());

				}
			} else {
				if (type == 0) {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SIZE));
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, libfrom);
				} else {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SIZE_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, libfrom);
				}

			}

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return 0;
	}
	/**
	 * 用于材料题的添加小题 
	 * @param title
	 * @param libfrom
	 * @param type
	 * @param conSub
	 * @param pn
	 * @param pS
	 * @return
	 * @throws ElException
	 */
	public int listMyQuestionsSize2(String title, int libfrom, int type,
			boolean conSub) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and q.qtype in(1,2,4,5,6) and q.title like ?  and qlib.lid >=? and qlib.rid<=? and q.parentid=0");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
				} else {

					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_SIZE_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());

				}
			} else {
				if (type == 0) {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SIZE));
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, libfrom);
				} else {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SIZE_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, libfrom);
				}

			}

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return 0;
	}

	public int listMyQuestionsSize(String title, int libfrom, int type,
			boolean conSub,String sqlw) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct
							.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and q.title like ?  and qlib.lid >=? and qlib.rid<=? and q.parentid=0"+sqlw);
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
				} else {

					ps = ct
							.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and q.qtype  = ? and q.title like ?  and qlib.lid >=? and qlib.rid<=? and q.parentid=0"+sqlw);
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());

				}
			} else {
				if (type == 0) {
					ps = ct
							.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and  q.title like ?  and qlib.id=? and q.parentid=0"+sqlw);
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, libfrom);
				} else {
					ps = ct
							.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.title like ?  and qlib.id=? and q.parentid=0"+sqlw);
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, libfrom);
				}

			}

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return 0;
	}
	
	public List<Question> listQuestionWithOutSubLevel(String title, int userid,
			int qlbid, int qtype, int level) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlb.name,q.qlevel,q.scoreper,q.parentid,q.minWord from QUESTION q left join QUESTION_LIB qlb on q.qlibid = qlb.id where "
							+ "q.title like ? and q.parentid=0 and q.qlibid = ? and q.qtype=? and q.userid = ? and q.qlevel= ? ");
			ps.setString(1, "%" + title + "%");
			ps.setInt(2, qlbid);
			ps.setInt(3, qtype);
			ps.setInt(4, userid);
			ps.setInt(5, level);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}

	public Question getQbyId(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QUESTION_QUERYBYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setScoreper(rs.getInt(13));
				q.setParent(new Question(rs.getInt(14)));
				q.setMinWord(rs.getInt(15));
				q.setOldrulestring(rs.getString(16));
				q.setOldscore(rs.getFloat(17));
				q.setSortid(rs.getInt(18));
				q.setFashengQuestion(rs.getString(19));
				q.setMediaFile(rs.getString(20));
				q.setModelVoice(rs.getString(21));
				q.setModelVoiceText(rs.getString(22));
				q.setVoicePath(rs.getString(23));
				q.setFenContent(rs.getString(24));
				q.setFwsize(rs.getInt(25));
				q.setStemText(rs.getString(26));
				q.setFrontHalfMediaFile(rs.getString(27));
				q.setRightAnswer(rs.getString(28));
				q.setStandardAnswer(rs.getString(29));
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public void alterQuestion(Question question) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QUESTION_ALTER));
			ps.setString(1, question.getTitle());
			ps.setString(2, question.getContent());
			ps.setString(3, question.getSubject());
			ps.setString(4, question.getQexplain());
			ps.setInt(5, question.getQlib().getId());
			ps.setInt(6, question.getQlevel());
			ps.setString(7, question.getAnswer());
			ps.setInt(8, question.getScoreper());
			ps.setInt(9, question.getMinWord());
			ps.setInt(10, question.getSortid());
			ps.setString(11, question.getOldrulestring());
			ps.setFloat(12, question.getOldscore());
			ps.setInt(13, question.getFwsize());
			ps.setString(14, question.getFashengQuestion());
			ps.setString(15, question.getMediaFile());
			ps.setString(16,question.getModelVoice());
			ps.setString(17, question.getModelVoiceText());
			ps.setString(18, question.getVoicePath());
			ps.setString(19, question.getFenContent());
			ps.setString(20, question.getStemText());
			ps.setString(21, question.getFrontHalfMediaFile());
			ps.setString(22, question.getStandardAnswer());
			ps.setInt(23, question.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void alterQuestionScorepre(Question question) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update question set scoreper=? where id =?");
			ps.setInt(1, question.getScoreper());
			ps.setInt(2, question.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void deleteQuestion(int qid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from QUESTION where id=?");
			ps.setInt(1, qid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据类型删除试题
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteQuestionByTypeid(Connection ct,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Connection ct = null;
		try {
			//ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from QUESTION where qlibid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据类型删除试题
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteQuestionByTypeidNot(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update QUESTION set status=1 where qlibid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类型删除试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public List<Question> getQChildbyPid(int pid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id, q.title, q.createtime,q.modifytime,q.qtype,"
							+ " q.scoreper,q.minWord,q.sortid,"
							+ " q.content,q.subject,q.qexplain,q.userid,q.qlibid,q.qlevel,"
							+ " q.answer,q.parentid,q.oldrulestring,q.oldscore"
							+ " from QUESTION q where "
							+ "q.parentid= ? order by q.sortid asc");
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setCreatetime(rs.getTimestamp(3));
				q.setModifytime(rs.getTimestamp(4));
				q.setQtype(rs.getInt(5));
				q.setScoreper(rs.getInt(6));
				q.setMinWord(rs.getInt(7));
				q.setSortid(rs.getInt(8));
				q.setContent(rs.getString(9));
				q.setSubject(rs.getString(10));
				q.setQexplain(rs.getString(11));
				q.setEluser(new ELUser(rs.getInt(12)));
				q.setQlib(new QuestionLib(rs.getInt(13)));
				q.setQlevel(rs.getInt(14));
				q.setAnswer(rs.getString(15));
				q.setParent(new Question(rs.getInt(16)));
				q.setOldrulestring(rs.getString(17));
				q.setOldscore(rs.getFloat(18));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}

	public Question getQuestionByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain,"
							+ " q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,q.scoreper,q.parentid,q.minWord,q.status  from QUESTION q left join QUESTION_LIB qlb on q.qlibid = qlb.id where "
							+ " q.id = ?  ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setScoreper(rs.getInt(13));
				q.setParent(new Question(rs.getInt(14)));
				q.setMinWord(rs.getInt(15));
				q.setStatus(rs.getInt(16));
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public Question getQuestionByid(int id, int blockid, int random)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = new Question();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (random == 0) {
				ps = ct
						.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain,"
								+ " q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,q.scoreper,q.parentid,q.minWord,bq.rulestring "
								+ "from QUESTION q left join QUESTION_LIB qlb on q.qlibid = qlb.id left join (select * from exampaperblockquestion where blockid = ?) bq on bq.questionid = q.id where "
								+ " q.id = ?  ");
				ps.setInt(1, blockid);
				ps.setInt(2, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					q = new Question(rs.getInt(1), rs.getString(2));
					q.setContent(rs.getString(3));
					q.setSubject(rs.getString(4));
					q.setQexplain(rs.getString(5));
					q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
					q.setCreatetime(rs.getTimestamp(7));
					q.setModifytime(rs.getTimestamp(8));
					q.setQlevel(rs.getInt(9));
					q.setAnswer(rs.getString(10));
					q.setQtype(rs.getInt(11));
					q.setScoreper(rs.getInt(13));
					q.setParent(new Question(rs.getInt(14)));
					q.setMinWord(rs.getInt(15));
					q.setRulestring(rs.getString(16));
				}
			} else {
				q = getQuestionByid(id);
				ps = ct.prepareStatement("select b.rulestring "
						+ "from exampaperblock b where b.id = ?");
				ps.setInt(1, blockid);
				rs = ps.executeQuery();
				if (rs.next()) {
					q.setRulestring(rs.getString(1));
				}
			}

		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public int getMaxQsort(int qid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QUESTION_CHILD_MAXSIZE));
			ps.setInt(1, qid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

	public void questionChildSort(int qid, int sortid, int upordown)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("");

			if (upordown == ElConstants.SORT_UP)
				upQchildSort(ps, qid, sortid);
			else
				downQchildSort(ps, qid, sortid);
		} catch (Exception e) {
			logger.error("大题试题排序失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void questionChildDelete(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {

			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sortid,parentid from question where id = ?");

			ps.setInt(1, id);
			rs = ps.executeQuery();
			int sortid = 0;
			int parentid = 0;
			if (rs.next()) {
				sortid = rs.getInt(1);
				parentid = rs.getInt(2);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.QUESTION_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();

			if (sortid > 0) {
				ps = ct
						.prepareStatement("update question set sortid = sortid-1 where sortid >= ? and parentid= ?");
				ps.setInt(1, sortid);
				ps.setInt(2, parentid);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("大题试题排序失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void upQchildSort(PreparedStatement ps, int qid, int sortid)
			throws ElException {
		try {
			if (sortid > 0) {
				String sql = "select id from question where parentid = " + qid
						+ " and sortid = " + (sortid - 1);
				ResultSet rs = ps.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update question set sortid=sortid-1 "
							+ " where parentid = " + qid + " and sortid="
							+ sortid;
					ps.executeUpdate(sql);
					sql = "update question set sortid=sortid+1 "
							+ " where id = " + nextId;
					ps.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			logger.error("大题试题排序失败！", e);
			throw new ElException("大题上移", e);
		}
	}
	
	private void downQchildSort(PreparedStatement ps, int qid, int sortid)
			throws ElException {
		try {
			String sql = "select max(sortid) from question where parentid= "
					+ qid;
			ResultSet rs = ps.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from question where parentid = " + qid
						+ " and sortid = " + (sortid + 1);
				rs = ps.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update question set sortid=sortid+1 "
							+ " where parentid = " + qid + " and sortid="
							+ sortid;
					ps.executeUpdate(sql);
					sql = "update question set sortid=sortid-1 "
							+ " where id = " + nextId;
					ps.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			logger.error("大题试题排序失败！", e);
			throw new ElException("大题下移", e);
		}
	}

//	public int addQstuff(StuffLib qs) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		int id = 0;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(QuestionConstants.STUFF_ADD));
//			ps.setString(1, qs.getTitle());
//			ps.setString(2, qs.getDescription());
//			ps.setString(3, qs.getFileext());
//			ps.setInt(4, qs.getOwner().getId());
//			ps.setTimestamp(5, new Timestamp(new Date().getTime()));
//			ps.setLong(6, qs.getLength());
//			ps.setInt(7, qs.getType());
//			ps.setInt(8, qs.getParent().getId());
//			ps.executeUpdate();
//			if ("mssql".equals(SystemConfOp
//					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
//				ps = ct
//						.prepareStatement("SELECT IDENT_CURRENT('question_stuff') AS id");
//				rs = ps.executeQuery();
//
//			} else if ("mysql".equals(SystemConfOp
//					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
//				rs = ps.getGeneratedKeys();
//			} else if ("oracle".equals(SystemConfOp
//					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
//				ps = ct
//						.prepareStatement("select question_stuff_sequence.currval from dual ");
//				rs = ps.executeQuery();
//
//			}
//			if (rs.next()) {
//				id = rs.getInt(1);
//				qs.setId(id);
//			}
//		} catch (Exception e) {
//			logger.error("获取试题出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return id;
//	}

//	public StuffLib getStuffbyId(int id, int userid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		StuffLib qs = new StuffLib();
//		try {
//			ct = DBConnection.getConnection();
//			if (userid != 0) {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.STUFF_QUERY_BYUID));
//				ps.setInt(1, userid);
//				ps.setInt(2, id);
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.STUFF_QUERY_BYID));
//				ps.setInt(1, id);
//			}
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				qs = new StuffLib(rs.getInt(1), rs.getString(2));
//				qs.setDescription(rs.getString(3));
//				qs.setFileext(rs.getString(4));
//				qs.setModifytime(rs.getTimestamp(5));
//				qs.setCreatetime(rs.getTimestamp(6));
//				qs.setLength(rs.getLong(7));
//				// long length = 0;
//				// try {
//				// length = new Long(rs.getString(7));
//				// } catch (Exception e) {
//				// }
//				// qs.setLength(length);
//				qs.setType(rs.getInt(8));
//				qs.setShared(rs.getInt(11));
//				qs.setParent(new StuffLib(rs.getInt(9), rs.getString(10)));
//				qs.setOwner(new ELUser(rs.getInt(12)));
//
//			}
//		} catch (Exception e) {
//			logger.error("获取资料列表出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return qs;
//	}
	/**
	 * 判断地址是否本机
	 * @param url
	 * @param contextPath
	 * @return
	 * @throws ElException
	 */
	public boolean checkUrlIsLocal(String url,String contextPath,String serverName) throws ElException {
		String port=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_HTTP_PORT);
		if(("http://localhost:"+port+contextPath).equals(url)){
			return true;
		}else if(("http://127.0.0.1:"+port+contextPath).equals(url)){
			return true;
		}else if(("http://"+serverName+":"+port+contextPath).equals(url)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取素材的信息以及所有父信息
	 * @param id
	 * @param userid
	 * @return
	 * @throws ElException
	 */
//	public StuffLib getStuffbyId2(int id, int userid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		StuffLib qs = new StuffLib();
//		try {
//			ct = DBConnection.getConnection();
//			if (userid != 0) {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.STUFF_QUERY_BYUID));
//				ps.setInt(1, userid);
//				ps.setInt(2, id);
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.STUFF_QUERY_BYID));
//				ps.setInt(1, id);
//			}
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				qs = new StuffLib(rs.getInt(1), rs.getString(2));
//				qs.setDescription(rs.getString(3));
//				qs.setFileext(rs.getString(4));
//				qs.setModifytime(rs.getTimestamp(5));
//				qs.setCreatetime(rs.getTimestamp(6));
//				qs.setLength(rs.getLong(7));
//				// long length = 0;
//				// try {
//				// length = new Long(rs.getString(7));
//				// } catch (Exception e) {
//				// }
//				// qs.setLength(length);
//				qs.setType(rs.getInt(8));
//				qs.setShared(rs.getInt(11));
//				if(rs.getInt(9)>0){
//					qs.setParent(getStuffbyId2(rs.getInt(9),0));
//				}else{
//					qs.setParent(new StuffLib(rs.getInt(9), rs.getString(10)));
//				}
//				//qs.setParent(new StuffLib(rs.getInt(9), rs.getString(10)));
//				qs.setOwner(new ELUser(rs.getInt(12)));
//
//			}
//		} catch (Exception e) {
//			logger.error("获取资料列表出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return qs;
//	}

//	public void setStuffParent(StuffLib stuffLib, List<StuffLib> list)
//			throws ElException {
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		if (null != list)
//			for (int i = 0; i < list.size(); i++) {
//				if (stuffLib.getId() == list.get(i).getId())
//					return;
//			}
//		else
//			return;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select p.id,p.title from question_stuff q left join question_stuff p on q.parentid = p.id  where q.id = ?");
//
//			ps.setInt(1, stuffLib.getId());
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				stuffLib.setParent(new StuffLib(rs.getInt(1), rs.getString(2)));
//				if (stuffLib.getParent().getId() != 0) {
//					setStuffParent(stuffLib.getParent(), list);
//				} else
//					stuffLib.setParent(new StuffLib(0, "根"));
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}

//	public void setStuffParent(StuffLib stuffLib) throws ElException {
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select p.id,p.title from question_stuff q left join question_stuff p on q.parentid = p.id  where q.id = ?");
//
//			ps.setInt(1, stuffLib.getId());
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				stuffLib.setParent(new StuffLib(rs.getInt(1), rs.getString(2)));
//				if (stuffLib.getParent().getId() != 0) {
//					setStuffParent(stuffLib.getParent());
//				} else
//					stuffLib.setParent(new StuffLib(0, "根"));
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}

//	public StuffLib getStuffFolderTree() throws ElException {
//		StuffLib q = new StuffLib(0, "根");
//		q.setChilds(listFolder(0, 1));
//		return q;
//	}

//	public StuffLib getStuffFolderTree(int userid) throws ElException {
//		StuffLib q = new StuffLib(0, "被分配的文件夹");
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<StuffLib> qss = new ArrayList<StuffLib>();
//		try {
//			ct = DBConnection.getConnection();
//
//			ps = ct
//					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs left join QUESTION_STUFF_USE_TYPE qsu on qsu.stuffid = qs.id where qsu.userid = ? and qs.type = 5 order by qs.createtime desc");
//			ps.setInt(1, userid);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
//				qs.setDescription(rs.getString(3));
//				qs.setFileext(rs.getString(4));
//				qs.setModifytime(rs.getTimestamp(5));
//				qs.setCreatetime(rs.getTimestamp(6));
//				qs.setLength(rs.getLong(7));
//				qs.setType(rs.getInt(8));
//				qs.setLevel(1);
//				if (qs.getType() == 5) {
//					qs.setChilds(listFolder(qs.getId(), 2));
//				}
//				qs.setParent(q);
//				qss.add(qs);
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		q.setChilds(qss);
//		return q;
//	}

//	public List<StuffLib> listFolder(int parentid, int level)
//			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<StuffLib> qss = new ArrayList<StuffLib>();
//		try {
//			ct = DBConnection.getConnection();
//
//			ps = ct
//					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs where qs.parentid = ? and qs.type = 5 order by qs.createtime desc");
//			ps.setInt(1, parentid);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
//				qs.setDescription(rs.getString(3));
//				qs.setFileext(rs.getString(4));
//				qs.setModifytime(rs.getTimestamp(5));
//				qs.setCreatetime(rs.getTimestamp(6));
//				qs.setLength(rs.getLong(7));
//				qs.setType(rs.getInt(8));
//				qs.setLevel(level);
//				if (qs.getType() == 5) {
//					qs.setChilds(listFolder(qs.getId(), level + 1));
//				}
//				qs.setParent(new StuffLib(parentid, ""));
//				qss.add(qs);
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return qss;
//	}

//	public StuffLib listFolderShared() throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<StuffLib> qss = new ArrayList<StuffLib>();
//		StuffLib q = new StuffLib(0, "共享文件夹");
//		try {
//			ct = DBConnection.getConnection();
//
//			ps = ct
//					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs where qs.shared = 1 and qs.type = 5 order by qs.createtime desc");
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
//				qs.setDescription(rs.getString(3));
//				qs.setFileext(rs.getString(4));
//				qs.setModifytime(rs.getTimestamp(5));
//				qs.setCreatetime(rs.getTimestamp(6));
//				qs.setLength(rs.getLong(7));
//				qs.setType(rs.getInt(8));
//				qs.setLevel(1);
//				if (qs.getType() == 5) {
//					qs.setChilds(listFolder(qs.getId(), 2));
//				}
//				qs.setParent(new StuffLib());
//				qss.add(qs);
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		q.setChilds(qss);
//		return q;
//	}

//	public List<StuffLib> getStuffs(StuffLib stuff, int userid, int pageNow,
//			int pageSize) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<StuffLib> qss = new ArrayList<StuffLib>();
//		try {
//			ct = DBConnection.getConnection();
//			String name = (stuff == null) ? "" : stuff.getTitle() == null ? ""
//					: stuff.getTitle().trim();
//			int type = (stuff == null) ? 0 : stuff.getType();
//			boolean bytype = type == 0 ? false : true;
//			if (!bytype) {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME));
//				ps.setInt(1, userid);
//				ps.setString(2, "%" + name + "%");
//				ps.setInt(3, pageNow);
//				ps.setInt(4, pageSize);
//			} else {
//				ps = ct
//						.prepareStatement(ElQuerySql
//								.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME_TYPE));
//				ps.setInt(1, userid);
//				ps.setString(2, "%" + name + "%");
//				ps.setInt(3, type);
//				ps.setInt(4, pageNow);
//				ps.setInt(5, pageSize);
//			}
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
//				qs.setDescription(rs.getString(3));
//				qs.setFileext(rs.getString(4));
//				qs.setModifytime(rs.getTimestamp(5));
//				qs.setCreatetime(rs.getTimestamp(6));
//				// long length = 0;
//				// try {
//				// length = new Long(rs.getString(7));
//				// } catch (Exception e) {
//				// }
//				// qs.setLength(length);
//				qs.setLength(rs.getLong(7));
//				// qs.setType(rs.getInt(8));
//				qss.add(qs);
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return qss;
//	}

//	public List<StuffLib> listStuffs(StuffLib stuff, String order, String ot)
//			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<StuffLib> qss = new ArrayList<StuffLib>();
//		try {
//			ct = DBConnection.getConnection();
//			int parentid = (stuff == null) ? 0 // : stuff.getParent() == null ?
//					// 0
//					: stuff.getId();
//			if (order == null) {
//				order = "qs.createtime ";
//			} else {
//				order = "qs.title ";
//			}
//			if (ot == null)
//				ot = "asc";
//			else if (ot.equals("up"))
//				ot = "asc";
//			else
//				ot = "desc";
//
//			ps = ct
//					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs where qs.parentid = ? order by qs.type desc,"
//							+ order + " " + ot);
//			ps.setInt(1, parentid);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
//				qs.setDescription(rs.getString(3));
//				qs.setFileext(rs.getString(4));
//				qs.setModifytime(rs.getTimestamp(5));
//				qs.setCreatetime(rs.getTimestamp(6));
//				qs.setLength(rs.getLong(7));
//				qs.setType(rs.getInt(8));
//				qss.add(qs);
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return qss;
//	}

//	public List<StuffLib> listMyStuffs(StuffLib stuff, int userid)
//			throws ElException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public void setStuffShared(int stuffid, int shared) throws ElException {
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("update question_stuff set shared = ? where id = ?");
//			ps.setInt(1, shared);
//			ps.setInt(2, stuffid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("查看部门信息出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}

//	public void setStuffsize(int stuffid, long size) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("update question_stuff set length = ? where id = ?");
//			ps.setLong(1, size);
//			ps.setInt(2, stuffid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("查看部门信息出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}

//	public long getStuffParentSize(int id) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		long s = 0l;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select p.id,p.length from question_stuff p left join question_stuff c on c.parentid =p.id where c.id = ?");
//			ps.setInt(1, id);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				if (rs.getInt(1) == 0)
//					s = -1l;
//				else
//					s = rs.getLong(2);
//			}
//		} catch (Exception e) {
//			logger.error("获取文件夹父亲大小！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return s;
//	}
//
//	public long getStuffChildsSize(int id) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		long s = 0l;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select sum(length) from question_stuff where parentid = ?");
//			ps.setInt(1, id);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				if(id==0)
//					s  = -1l;
//				else
//				s = rs.getLong(1);
//			}
//		} catch (Exception e) {
//			logger.error("获取文件实际大小失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return s;
//	}
//	public int getStuffOpStatus(int stuffid, int userid, int roleid,int type)
//			throws ElException {//type 1 检测创建文件夹。2 设置文件夹大小
//		StuffLib qstuff = getStuffbyId(stuffid, 0);
//		
//		if(type==1&&qstuff.getParent()!=null&&qstuff.getParent().getId()==0&&roleid!=1){
//			return 1;//非超级管理员不能再根目录创建文件夹。	
//		}
//		if(type==2){
//			if(qstuff.getOwner()!=null&&qstuff.getOwner().getId()==userid){
//				return 0;
//			}else
//			{
//				return 2;
//			}
//		}
//		return 0;
//	}
//	public int getStuffsCount(StuffLib stuff, int userid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			String name = (stuff == null) ? "" : stuff.getTitle() == null ? ""
//					: stuff.getTitle().trim();
//			int type = (stuff == null) ? 0 : stuff.getType();
//			boolean bytype = type == 0 ? false : true;
//			if (!bytype) {
//				ps = ct
//						.prepareStatement(ElQuerySql
//								.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME_SIZE));
//				ps.setInt(1, userid);
//				ps.setString(2, "%" + name + "%");
//			} else {
//				ps = ct
//						.prepareStatement(ElQuerySql
//								.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME_TYPE_SIZE));
//				ps.setInt(1, userid);
//				ps.setString(2, "%" + name + "%");
//				ps.setInt(3, type);
//			}
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("获取资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return 0;
//	}
//
//	public void addStuffOpusers(int userid, int stuffid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("insert into question_stuff_use_type(userid,stuffid) values(?,?)");
//			ps.setInt(1, userid);
//			ps.setInt(2, stuffid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("查看部门信息出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//
//	}
//
//	public boolean checkStuffOpUsers(int userid, int stuffid)
//			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select * from question_stuff_use_type where userid = ? and stuffid = ?");
//			ps.setInt(1, userid);
//			ps.setInt(2, stuffid);
//			rs = ps.executeQuery();
//			if (rs.next())
//				return true;
//		} catch (Exception e) {
//			logger.error("查看部门信息出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return false;
//
//	}
//
//	public void deleteStuffOpusers(int userid, int stuffid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("delete from question_stuff_use_type where userid = ? and stuffid = ?");
//			ps.setInt(1, userid);
//			ps.setInt(2, stuffid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("查看部门信息出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//
//	}
//
//	public List<ELUser> getStuffOpUsers(int stuffid) throws ElException {
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<ELUser> us = new ArrayList<ELUser>();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select eu.id,eu.realname,eu.username from question_stuff_use_type du left join eluser eu on eu.id = du.userid where du.stuffid = ?");
//			ps.setInt(1, stuffid);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
//				if (user.getRealname() == null || "".equals(user.getRealname()))
//					user.setRealname(rs.getString(3));
//				us.add(user);
//			}
//		} catch (Exception e) {
//			logger.error("查看部门信息出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return us;
//	}
//
//	public void alter(StuffLib qs) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(QuestionConstants.STUFF_ALTER));
//			ps.setString(1, qs.getTitle());
//			ps.setString(2, qs.getDescription());
//			ps.setInt(3, qs.getType());
//			ps.setInt(4, qs.getId());
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("修改资料出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}
//
//	public void deleteQs(int id, int userid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			if (userid != 0) {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.STUFF_DELETE));
//				ps.setInt(1, id);
//				ps.setInt(2, userid);
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.STUFF_DELETE_BYID));
//				ps.setInt(1, id);
//
//			}
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("修改删除出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}

	public void addOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into questionlib_" + type
					+ "_user(userid,depid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkOpUsers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from questionlib_" + type
					+ "_user where userid = ? and depid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from questionlib_" + type
					+ "_user where userid = ? and depid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> getOpUsers(String type, int depid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from questionlib_"
							+ type
							+ "_user du left join eluser eu on eu.id = du.userid where du.depid = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public List<QuestionArt> listQarts(String title, int begin, int end)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<QuestionArt> us = new ArrayList<QuestionArt>();
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(QuestionConstants.QUESTION_ART_LIST));
			ps=ct.prepareStatement("select * from(select t.*,rownum rn from (select id,title,content,qexplain from question where title like ? and qtype=10 order by id desc) t where rownum<=?) where rn>=?");
			ps.setString(1, "%" + title + "%");
			ps.setInt(2, begin);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				QuestionArt art = new QuestionArt();
				art.setId(rs.getInt(1));
				art.setTitle(rs.getString(2));
				art.setContent(rs.getString(3));
				art.setQexplain(rs.getString("qexplain"));
				us.add(art);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public int listQartsSize(String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//ps = ct.prepareStatement(" select count( id) from questionart where title like ?");
			ps = ct.prepareStatement(" select count( id) from question where title like ? and qtype=10");
			ps.setString(1, "%" + title + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void alterQart(QuestionArt qart) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" update questionart set title = ? ,content = ?  where id= ?");
			ps.setString(1, qart.getTitle());
			ps.setString(2, qart.getContent());
			ps.setInt(3, qart.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addQart(QuestionArt qart) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" insert into  questionart( title ,content) values(?,?)");
			ps.setString(1, qart.getTitle());
			ps.setString(2, qart.getContent());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void deleteQart(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" delete from questionart where id= ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public QuestionArt getQart(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		QuestionArt qArt = new QuestionArt();
		try {
			ct = DBConnection.getConnection();
			//ps = ct.prepareStatement(" select id,title,content from questionart where id = ?");
			ps = ct.prepareStatement(" select id,title,content,qexplain from question where id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				qArt.setId(rs.getInt(1));
				qArt.setTitle(rs.getString(2));
				qArt.setContent(rs.getString(3));
				qArt.setQexplain(rs.getString("qexplain"));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qArt;
	}
	//TODO  无人调用
	public List<Question> getQuestionList() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content from question q,question_lib qlib where q.qlibid=qlib.id and  q.title like '%' and qlib.lid >=1 and qlib.rid<=132 and q.parentid=0  order by q.createtime desc");
			rs = ps.executeQuery();
			String answer = "";
			String newtestsupport = "";
			while (rs.next()) {
				while (rs.next()) {
					Question question = new Question();
					question.setQtype(rs.getInt(1));
					question.setTitle(rs.getString(2));
					question.setSubject(rs.getString(3));
					if (rs.getString(3) != null) {
						if (rs.getString(3).contains(ElConstants.optSplit)) {
							newtestsupport = rs.getString(3).substring(
									0,
									rs.getString(3).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							newtestsupport = rs.getString(3);
						}
					}
					question.setTestsupport(newtestsupport);
					if (rs.getString(4) != null) {
						for (int i = 0; i < rs.getString(4).split(
								ElConstants.optSplit).length; i++) {
							if (rs.getString(4).split(ElConstants.optSplit)[i]
									.trim().equals("0")) {
								answer += "A ";
							} else if (rs.getString(4).split(
									ElConstants.optSplit)[i].trim().equals("1")) {
								answer += "B ";
							} else if (rs.getString(4).split(
									ElConstants.optSplit)[i].trim().equals("2")) {
								answer += "C ";
							} else if (rs.getString(4).split(
									ElConstants.optSplit)[i].trim().equals("3")) {
								answer += "D ";
							} else if (rs.getString(4).split(
									ElConstants.optSplit)[i].trim().equals(
									"yes")) {
								answer += "正确";
							} else if (rs.getString(4).split(
									ElConstants.optSplit)[i].trim()
									.equals("no")) {
								answer += "错误";
							} else {
								answer += rs.getString(4).split(
										ElConstants.optSplit)[i].trim();
							}
						}
					}
					question.setAnswer(answer);
					question.setQlevel(rs.getInt(5));
					question.setOldrulestring(rs.getString(6));
					QuestionLib questionLib = new QuestionLib();
					questionLib.setName(rs.getString(7));
					question.setQlib(questionLib);
					question.setQexplain(rs.getString(8));
					question.setContent(rs.getString(9));
					qs.add(question);
					answer = "";
				}
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}

	public List<Question> getQuestionList(Question question) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			String sqlstr = "select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring from question q,question_lib qlib where q.qlibid=qlib.id and ";
			sqlstr += "  q.title like '%";
			if (question == null) {
				sqlstr += "" + "%'";
			} else {
				sqlstr += question.getTitle() == null ? "" + "%' " : question
						.getTitle()
						+ "%' ";
				if (question.getQlib() != null) {
					sqlstr += "and qlib.lid >="
							+ (question.getQlib().getId() == 0 ? 1
									: "(select lid from question_lib where id="
											+ question.getQlib().getId() + ") ");
					sqlstr += "and qlib.rid <="
							+ (question.getQlib().getId() == 0 ? 132
									: "(select rid from question_lib where id="
											+ question.getQlib().getId() + ") ");
				}
				sqlstr += question.getQtype() == 0 ? "" : " and q.qtype="
						+ question.getQtype();
			}
			sqlstr += " and q.parentid=0 order by q.createtime desc";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			String answer = "";
			String newtestsupport = "";

			while (rs.next()) {
				Question q = new Question();
				q.setQtype(rs.getInt(1));
				q.setTitle(rs.getString(2));
				q.setSubject(rs.getString(3));
				if (rs.getInt(1) == 10 || rs.getInt(1) == 5
						|| rs.getInt(1) == 8 || rs.getInt(1) == 6
						|| rs.getInt(1) == 9 || rs.getInt(1) == 1) {
					q.setTestsupport("");
				} else {
					if (rs.getString(3) != null) {
						if (rs.getString(3).contains(ElConstants.optSplit)) {
							newtestsupport = rs.getString(3).substring(
									0,
									rs.getString(3).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							newtestsupport = rs.getString(3);
						}
					}
					q.setTestsupport(newtestsupport);
				}
				if (rs.getInt(1) != 9) {
					if (rs.getInt(1) == 5) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					} else {
						if (rs.getString(4) != null) {
							if (rs.getInt(1) == 10) {
								if (rs.getString(4).trim().contains(
										ElConstants.optSplit)) {
									answer += rs.getString(4).substring(
											0,
											rs.getString(4).lastIndexOf(
													ElConstants.optSplit))
											.replaceAll(ElConstants.optSplit,
													"==");
								} else {
									answer = rs.getString(4).trim();
								}
							} else {
								for (int i = 0; i < rs.getString(4).split(
										ElConstants.optSplit).length; i++) {

									if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("0")) {
										answer += "A ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("1")) {
										answer += "B ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("2")) {
										answer += "C ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("3")) {
										answer += "D ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("yes")) {
										answer += "正确";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("no")) {
										answer += "错误";
									} else {
										answer += rs.getString(4).split(
												ElConstants.optSplit)[i].trim();
									}
								}
							}
						}
					}
				} else {
					if (rs.getString(4) != null) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					}
				}
				q.setAnswer(answer);
				q.setQlevel(rs.getInt(5));
				q
						.setOldrulestring(rs.getInt(1) != 9 ? rs.getString(6)
								: rs.getString(10).contains(
										ElConstants.ruleSplit) ? rs
										.getString(10)
										.substring(
												0,
												rs.getString(10).lastIndexOf(
														ElConstants.ruleSplit))
										.replaceAll(ElConstants.ruleSplit, "==")
										: rs.getString(10));
				QuestionLib questionLib = new QuestionLib();
				questionLib.setName(rs.getString(7));
				q.setQlib(questionLib);
				q.setQexplain(rs.getString(8));
				q.setContent(rs.getString(9));
				qs.add(q);
				answer = "";
			}

		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}

	public List<Question> question_list_listMyQuestions(QuestionLib qlbTree,
			String title, int libfrom, int type, boolean conSub, int pn, int pS)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));// select
				// id,lid,rid
				// from
				// question_lib
				// where
				// id =
				// ?
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB));
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
					ps.setInt(4, pn);
					ps.setInt(5, pS);
				} else {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());
					ps.setInt(5, pn);
					ps.setInt(6, pS);

				}
			} else {
				String ids = QuestionLibById(qlbTree, libfrom);
				ids = libfrom == 1 ? ids.substring(2, ids.length()) : ids;
				if (type == 0) {
					ps = ct
							.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword from question q ,(select * from question_lib where  id in("
									+ ids
									+ ") ) qlib where q.qlibid=qlib.id and q.title like ? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, pn);
					ps.setInt(3, pS);
				} else {

					ps = ct
							.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword from question q ,(select * from question_lib where  id in("
									+ ids
									+ ") ) qlib where q.qlibid=qlib.id and q.qtype = ? and q.title like ? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, pn);
					ps.setInt(4, pS);

				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setEluser(new ELUser());
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	/**
	 * 用于材料题的添加小题
	 * @param qlbTree
	 * @param title
	 * @param libfrom
	 * @param type
	 * @param conSub
	 * @return
	 * @throws ElException
	 */
	public List<Question> question_list_listMyQuestions2(QuestionLib qlbTree,
			String title, int libfrom, int type, boolean conSub, int pn, int pS)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

//			if (conSub) {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));// select
				// id,lid,rid
				// from
				// question_lib
				// where
				// id =
				// ?
//				ps.setInt(1, libfrom);
//				QuestionLib qlib = new QuestionLib(libfrom);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					qlib.setLid(rs.getInt(2));
//					qlib.setRid(rs.getInt(3));
//				}
//				rs.close();
//				String sql = "select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status from question q , ("
//						+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
//								.generateSQLByTree("question_lib", qlbTree,
//										conSub) + ")"
//						// "question_lib qlib " +
//						// " where q.qlibid=qlib.id and q.qtype in(1,2,4,5,6)
//						// and q.title like ? and qlib.lid >=? and qlib.rid<=?
//						// and q.parentid=0 order by q.createtime desc )t where
//						// rownum <=? ) where rn >=?";
//						+ " where q.title like ?  and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?";
//				if (type == 0) {
//					ps = ct.prepareStatement(sql);
//					ps.setString(1, "%" + title + "%");
////					ps.setInt(2, qlib.getLid());
////					ps.setInt(3, qlib.getRid());
//					ps.setInt(2, pn);
//					ps.setInt(3, pS);
//				} else {
//					ps = ct
//							.prepareStatement(ElQuerySql
//									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_TYPE));
//					ps.setInt(1, type);
//					ps.setString(2, "%" + title + "%");
////					ps.setInt(3, qlib.getLid());
////					ps.setInt(4, qlib.getRid());
//					ps.setInt(3, pn);
//					ps.setInt(4, pS);
//
//				}
//			} else {
//				String ids = QuestionLibById(qlbTree, libfrom);
//				ids = libfrom == 1 ? ids.substring(2, ids.length()) : ids;
//				if (type == 0) {
//					ps = ct
//							.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword from question q , ("
//									// "(select * from question_lib where id
//									// in("
//									// + ids
//									// + ") ) qlib where q.qlibid=qlib.id and
//									// q.qtype in(1,2,4,5,6) and q.title like ?
//									// and q.parentid=0 order by q.createtime
//									// desc )t where rownum <=? ) where rn
//									// >=?");
//									+ ((ElNodeSQL) SpringContextUtil
//											.getBean("elnodesql"))
//											.generateSQLByTree("question_lib",
//													qlbTree, conSub)
//									+ ")"
//									+ "  where q.qlibid=qlib.id and q.qtype in(1,2,4,5,6) and q.title like ? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?");
//					ps.setString(1, "%" + title + "%");
//					ps.setInt(2, pn);
//					ps.setInt(3, pS);
//				} else {
				StringBuffer sql = new StringBuffer("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword from question q ,(");
				sql.append(((ElNodeSQL) SpringContextUtil
						.getBean("elnodesql"))
						.generateSQLByTree("question_lib",
								qlbTree, conSub));
				sql.append(" ) qlib where q.qlibid=qlib.id ");
				if(type!=0)
					sql.append("and q.qtype="+type);
				sql.append( " and q.title like ? and q.parentid=0 and q.qtype <7 order by q.createtime desc )t  where rownum <=? ) where rn >=?");
					ps = ct
							.prepareStatement(sql.toString());
//					ps.setInt(1, type);
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, pn);
					ps.setInt(3, pS);

//				}
//			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setEluser(new ELUser());
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	/**
		 * 用于材料题的添加小题
		 * @param qlbTree
		 * @param title
		 * @param libfrom
		 * @param type
		 * @param conSub
		 * @return
		 * @throws ElException
		 */
		public int question_list_listMyQuestionsSize2(QuestionLib qlbTree,
				String title, int libfrom, int type, boolean conSub)
				throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			try {
				ct = DBConnection.getConnection();
				if (null == title)
					title = "";
				else
					title = title.trim();
	//			if (conSub) {
	//				ps = ct.prepareStatement(ElQuerySql
	//						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
	//				ps.setInt(1, libfrom);
	//				QuestionLib qlib = new QuestionLib(libfrom);
	//				rs = ps.executeQuery();
	//				if (rs.next()) {
	//					qlib.setLid(rs.getInt(2));
	//					qlib.setRid(rs.getInt(3));
	//				}
	//				rs.close();
	//				if (type == 0) {
	//					ps = ct.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and q.qtype in(1,2,4,5,6) and q.title like ?  and qlib.lid >=? and qlib.rid<=? and q.parentid=0");
	//					ps.setString(1, "%" + title + "%");
	//					ps.setInt(2, qlib.getLid());
	//					ps.setInt(3, qlib.getRid());
	//				} else {
				StringBuffer sql = new StringBuffer("select count(*) from question q ,(");
					sql.append(((ElNodeSQL) SpringContextUtil
						.getBean("elnodesql"))
						.generateSQLByTree("question_lib",
								qlbTree, conSub));
					sql.append(") qlib where q.qlibid=qlib.id ");
//						ps = ct
//								.prepareStatement(ElQuerySql
//										.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_SIZE_TYPE));
//						ps.setInt(1, type);
//						ps.setString(2, "%" + title + "%");
				if(type != 0){
					sql.append("and q.qtype="+type);
				}
				sql.append(" and q.title like ? and q.parentid=0 and q.qtype <7 ");
				
	//					ps.setInt(3, qlib.getLid());
	//					ps.setInt(4, qlib.getRid());
	//
	//				}
	//			} else {
	//				String ids = QuestionLibById(qlbTree, libfrom);
	//				ids = libfrom == 1 ? ids.substring(2, ids.length()) : ids;
	//				if (type == 0) {
	//					ps = ct
	//							.prepareStatement("select count(*) from question q ,(select * from question_lib where  id in("
	//									+ ids
	//									+ ") ) qlib where q.qlibid=qlib.id and q.qtype in(1,2,4,5,6) and  q.title like ?  and q.parentid=0");
	//					ps.setString(1, "%" + title + "%");
	//				} else {
	//					ps = ct
	//							.prepareStatement("select count(*) from question q ,(select * from question_lib where  id in("
	//									+ ids
	//									+ ") ) qlib where q.qlibid=qlib.id and q.qtype = ? and q.title like ? and q.parentid=0");
	//					ps.setInt(1, type);
	//					ps.setString(2, "%" + title + "%");
	//				}
	//
	//			}
				ps = ct
				.prepareStatement(sql.toString());
				ps.setString(1, "%" + title + "%");
				rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (Exception e) {
				logger.error("获取试题数量出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
	
			return 0;
		}

	/**
	 * 无分页，用于导出
	 */
	public List<Question> question_list_listMyQuestions(QuestionLib qlbTree,
			String title, int libfrom, int type, boolean conSub)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));// select
				// id,lid,rid
				// from
				// question_lib
				// where
				// id =
				// ?
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id from question q ,question_lib qlib " +
							"where q.qlibid=qlib.id and  q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0  order by q.createtime desc");
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid()); 
				} else {
					ps = ct
							.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id from question q ,question_lib qlib " +
									"where q.qlibid=qlib.id and q.qtype=? and q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0  order by q.createtime desc");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid()); 

				}
			} else {
				String ids = QuestionLibById(qlbTree, libfrom);
				ids = libfrom == 1 ? ids.substring(2, ids.length()) : ids;
				if (type == 0) {
					ps = ct
							.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id from question q ,(select * from question_lib where  id in("
									+ ids
									+ ") ) qlib where q.qlibid=qlib.id and q.title like ? and q.parentid=0  order by q.createtime desc ");
					ps.setString(1, "%" + title + "%"); 
				} else {

					ps = ct
							.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id from question q ,(select * from question_lib where  id in("
									+ ids
									+ ") ) qlib where q.qlibid=qlib.id and q.qtype = ? and q.title like ? and q.parentid=0  order by q.createtime desc ");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%"); 

				}
			}
			rs = ps.executeQuery();

			String answer = "";
			String newtestsupport = "";
			while (rs.next()) {
				Question q = new Question();
				q.setQtype(rs.getInt(1));
				q.setTitle(rs.getString(2));
				q.setSubject(rs.getString(3));
				if (rs.getInt(1) == 10 || rs.getInt(1) == 5
						|| rs.getInt(1) == 8 || rs.getInt(1) == 6
						|| rs.getInt(1) == 9 || rs.getInt(1) == 1) {
					q.setTestsupport("");
				} else {
					if (rs.getString(3) != null) {
						if (rs.getString(3).contains(ElConstants.optSplit)) {
							newtestsupport = rs.getString(3).substring(
									0,
									rs.getString(3).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							newtestsupport = rs.getString(3);
						}
					}
					q.setTestsupport(newtestsupport);
				}
				if (rs.getInt(1) != 9) {
					if (rs.getInt(1) == 5) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					} else {
						if (rs.getString(4) != null) {
							if (rs.getInt(1) == 10) {
								if (rs.getString(4).trim().contains(
										ElConstants.optSplit)) {
									answer += rs.getString(4).substring(
											0,
											rs.getString(4).lastIndexOf(
													ElConstants.optSplit))
											.replaceAll(ElConstants.optSplit,
													"==");
								} else {
									answer = rs.getString(4).trim();
								}
							} else {
								for (int i = 0; i < rs.getString(4).split(
										ElConstants.optSplit).length; i++) {

									if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("0")) {
										answer += "A ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("1")) {
										answer += "B ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("2")) {
										answer += "C ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("3")) {
										answer += "D ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("yes")) {
										answer += "正确";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("no")) {
										answer += "错误";
									} else {
										answer += rs.getString(4).split(
												ElConstants.optSplit)[i].trim();
									}
								}
							}
						}
					}
				} else {
					if (rs.getString(4) != null) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					}
				}
				q.setAnswer(answer);
				q.setQlevel(rs.getInt(5));
				q
						.setOldrulestring(rs.getInt(1) != 9 ? rs.getString(6)
								: rs.getString(10).contains(
										ElConstants.ruleSplit) ? rs
										.getString(10)
										.substring(
												0,
												rs.getString(10).lastIndexOf(
														ElConstants.ruleSplit))
										.replaceAll(ElConstants.ruleSplit, "==")
										: rs.getString(10));
				QuestionLib questionLib = new QuestionLib();
				questionLib.setId(rs.getInt(11));
				questionLib.setName(rs.getString(7));
				q.setQlib(questionLib);
				q.setQexplain(rs.getString(8));
				q.setContent(rs.getString(9)); 
				qs.add(q);
				answer = "";
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}

	public int question_list_listMyQuestionsSize(QuestionLib qlbTree,
			String title, int libfrom, int type, boolean conSub)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();
				if (type == 0) {
					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_SIZE));
					ps.setString(1, "%" + title + "%");
					ps.setInt(2, qlib.getLid());
					ps.setInt(3, qlib.getRid());
				} else {

					ps = ct
							.prepareStatement(ElQuerySql
									.getSQL(QuestionConstants.QUESTION_MAN_MYLIST_SUB_SIZE_TYPE));
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, qlib.getLid());
					ps.setInt(4, qlib.getRid());

				}
			} else {
				String ids = QuestionLibById(qlbTree, libfrom);
				ids = libfrom == 1 ? ids.substring(2, ids.length()) : ids;
				if (type == 0) {
					ps = ct
							.prepareStatement("select count(*) from question q ,(select * from question_lib where  id in("
									+ ids
									+ ") ) qlib where q.qlibid=qlib.id and  q.title like ?  and q.parentid=0");
					ps.setString(1, "%" + title + "%");
				} else {
					ps = ct
							.prepareStatement("select count(*) from question q ,(select * from question_lib where  id in("
									+ ids
									+ ") ) qlib where q.qlibid=qlib.id and q.qtype = ? and q.title like ? and q.parentid=0");
					ps.setInt(1, type);
					ps.setString(2, "%" + title + "%");
				}

			}

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return 0;
	}
	
	public List<QuestionLib> getQuestionlibList() throws ElException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String QuestionLibById(QuestionLib ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = QuestionTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree.getChild() != null) {
				return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @return
	 */
	private String createTypeId(List<QuestionLib> listType, int id) {
		String ids = id + "";
		for (QuestionLib type : listType) {
			ids = ids + "," + createTypeId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author jiahaijiang
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private QuestionLib QuestionTypeById(List<QuestionLib> listType, int ctid) {
		QuestionLib courseType = null;
		for (QuestionLib type : listType) {
			if (type.getId() != ctid) {
				courseType = QuestionTypeById(type.getChild(), ctid);
				if (courseType != null) {
					return courseType;
				}
			} else {
				courseType = type;
				return courseType;
			}
		}
		return courseType;
	}

	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from questionlib_op_user where userid= ?");
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
	public void deleteUserUseGrant(int userId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from questionlib_op_user where userid= ?");
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
//	public void deleteStuffUseusers(int userId) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement(" delete from question_stuff_use_type where userid= ?");
//			ps.setInt(1, userId);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("删除用户权限出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}
	
	public synchronized void setQuestionStatus(int id ,int status) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try { 
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update question set status = ? where id = ?");
			ps.setInt(1, status); 
			ps.setInt(2, id); 
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<Question> getselectQbyIds(String ids,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status " +
					"from question q ,question_lib qlib where q.qlibid=qlib.id and q.id in("+ids+") and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery(); 
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), StringUtil.htmlParse(rs.getString(2)));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setStatus(rs.getInt(12));
				q.setEluser(new ELUser());
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}	
	
	public List<Question> getselectQbyIds(String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement("select qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id,q.id qid " +
					"from question q ,question_lib qlib where q.qlibid=qlib.id and q.id in ("+ids+") and q.parentid=0  order by q.createtime desc"); 
			rs = ps.executeQuery(); 
//				String answer = "";
				while (rs.next()) {
					Question q = new Question();
					q.setQtype(rs.getInt(1));
					q.setTitle(rs.getString(2));
					q.setSubject(rs.getString(3));
					q.setAnswer(rs.getString(4));
					q.setQlevel(rs.getInt(5));
					q.setOldscore(rs.getFloat(6));
					QuestionLib questionLib = new QuestionLib();
					questionLib.setId(rs.getInt(11));
					questionLib.setName(rs.getString(7));
					q.setQlib(questionLib);
					q.setQexplain(rs.getString(8));
					q.setContent(rs.getString(9)); 
					q.setRulestring(rs.getString(10));
					q.setId(rs.getInt(12));
					qs.add(q);
//					answer = "";
					//判断是否材料题
					if(q.getQtype()==7){
						//添加小题
						List<Question> qcls=this.getQChildbyPid(q.getId());
						qs.addAll(qcls);
					}
				}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}
	public int getselectQbyIdsSize(String ids ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(q.id) " +
					"from question q ,question_lib qlib where q.qlibid=qlib.id and q.id in("+ids+") and q.parentid=0  order by q.createtime desc ");
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
//原来的
//	public List<Question> listMyQuestions(Question question, int libfrom,boolean conSub, int pn, int pS) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<Question> qs = new ArrayList<Question>();
//		String LidRid = "";
//		String conditions = "";
//		try {
//			ct = DBConnection.getConnection();  
//			if (conSub) {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
//				ps.setInt(1, libfrom);
//				QuestionLib qlib = new QuestionLib(libfrom);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					qlib.setLid(rs.getInt(2));
//					qlib.setRid(rs.getInt(3));
//				}
//				rs.close();  
//				LidRid = " and qlib.lid >="+qlib.getLid()+" and qlib.rid<="+qlib.getRid();
//			}else{
//				LidRid = " and qlib.id="+libfrom;
//			} 
//			if(question != null){
//				if(question.getTitle() != null && !question.getTitle().equals("")){
//					conditions = conditions + " and  q.title like '%"+question.getTitle()+"%'";
//				}
//				if(question.getEluser() != null && question.getEluser().getRealname() != null && !question.getEluser().getRealname().equals("")){
//					conditions = conditions + " and  el.realname like '%"+question.getEluser().getRealname()+"%'";
//				}
//				if(question.getQtype() != 0){
//					conditions = conditions + "and q.qtype = "+question.getQtype();
//				}
//				if(question.getCreatetime()!=null){
//					conditions = conditions +" and q.createtime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//					.format(question.getCreatetime())+ "','yyyy-MM-dd HH24:mi:ss')";
//				}
//				if(question.getCreatetimeEnd()!=null){
//					conditions = conditions +" and q.createtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//					.format(question.getCreatetimeEnd())+ "','yyyy-MM-dd HH24:mi:ss')";
//				} 
//				
//			} 
//			String sql = "select * from(select t.*,rownum rn from (select " +
//					"q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status " +
//					"from question q ,question_lib qlib,eluser el " +
//					"where q.qlibid=qlib.id  and el.id = q.userid and q.parentid=0 " 
//					+conditions+LidRid+
//					" order by q.createtime desc )t  where rownum <=? ) where rn >=?";
//			ps = ct.prepareStatement(sql);  
//			ps.setInt(1, pn);
//			ps.setInt(2, pS);  
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Question q = new Question(rs.getInt(1), rs.getString(2));
//				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
//				q.setCreatetime(rs.getTimestamp(4));
//				q.setModifytime(rs.getTimestamp(5));
//				q.setQtype(rs.getInt(6));
//				q.setQlevel(rs.getInt(8));
//				q.setScoreper(rs.getInt(9));
//				q.setParent(new Question(rs.getInt(10)));
//				q.setMinWord(rs.getInt(11));
//				q.setStatus(rs.getInt(12));
//				q.setEluser(new ELUser());
//				qs.add(q);
//			}
//		} catch (Exception e) {
//			logger.error("获取试题出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//
//		return qs;
//	}
//	
	public List<Question> listMyQuestions(ElNode dep, int subdep, Question question)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		String conditions = "";
		boolean conSub = subdep ==1?true :false;
		try {
			ct = DBConnection.getConnection();  
			if(question != null){
				if(question.getTitle() != null && !question.getTitle().equals("")){
					conditions = conditions + " and  q.title like '%"+StringUtil.toLikeStr(question.getTitle())+"%'";
				}
				if(question.getEluser() != null && question.getEluser().getRealname() != null && !question.getEluser().getRealname().equals("")){
					conditions = conditions + " and  el.realname like '%"+StringUtil.toLikeStr(question.getEluser().getRealname())+"%'";
				}
				if(question.getQtype() != 0){
					conditions = conditions + "and q.qtype = "+question.getQtype();
				}
				if(question.getStatus() != -1){
					conditions = conditions + "and q.status = "+question.getStatus();
				}
				if(question.getCreatetime()!=null){
					conditions = conditions +" and q.createtime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetime())+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if(question.getCreatetimeEnd()!=null){
					conditions = conditions +" and q.createtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetimeEnd())+ "','yyyy-MM-dd HH24:mi:ss')";
				} 
				
			} 
			String sql = " select " +
					"qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id,q.id qid " +
					"from question q ,("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("question_lib", dep, conSub)+") qlib,eluser el " +
					"where q.qlibid=qlib.id  and el.id = q.userid and q.parentid=0 " 
					+conditions+
					" order by q.createtime desc  ";
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question();
				q.setQtype(rs.getInt(1));
				q.setTitle(rs.getString(2));
				q.setSubject(rs.getString(3));
				q.setAnswer(rs.getString(4));
				q.setQlevel(rs.getInt(5));
				q
						.setOldrulestring(rs.getString(6));
				QuestionLib questionLib = new QuestionLib();
				questionLib.setId(rs.getInt(11));
				questionLib.setName(rs.getString(7));
				q.setQlib(questionLib);
				q.setQexplain(rs.getString(8));
				q.setContent(rs.getString(9));
//				q.setParent(new Question(rs.getInt(11)));
				q.setParent(null);
				q.setId(rs.getInt(12));
//				System.out.println("**"+q.getParent().getId());
				qs.add(q);
				//判断是否材料题
				if(q.getQtype()==7){
					//添加小题
					List<Question> qcls=this.getQChildbyPid(q.getId());
					qs.addAll(qcls);
//					if(qcls!=null){
//						for (int i = 0; i < qcls.size(); i++) {
//							qs.add(qcls.get(i));
//						}
//					}
				}
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	public List<Question> listMyQuestions(ElNode dep, int subdep,
			Question question, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		String conditions = "";
		boolean conSub = subdep ==1?true :false;
		try {
			ct = DBConnection.getConnection();  
			if(question != null){
				if(question.getTitle() != null && !question.getTitle().equals("")){
					conditions = conditions + " and  q.title like '%"+StringUtil.toLikeStr(question.getTitle())+"%'";
				}
				if(question.getEluser() != null && question.getEluser().getRealname() != null && !question.getEluser().getRealname().equals("")){
					conditions = conditions + " and  el.realname like '%"+StringUtil.toLikeStr(question.getEluser().getRealname())+"%'";
				}
				if(question.getQtype() != 0){
					if(question.getQtype()==-3){
						//只查单选和多选题
						conditions = conditions + "and q.qtype in(2,4)";
					}else{
						conditions = conditions + "and q.qtype = "+question.getQtype();
					}
				}
				if(question.getStatus() != -1){
					conditions = conditions + "and q.status = "+question.getStatus();
				}
				if(question.getCreatetime()!=null){
					conditions = conditions +" and q.createtime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetime())+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if(question.getCreatetimeEnd()!=null){
					conditions = conditions +" and q.createtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetimeEnd())+ "','yyyy-MM-dd HH24:mi:ss')";
				} 
				
			} 
			String sql = "select * from(select t.*,rownum rn from (select " +
					"q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.qexplain " +
					"from question q ,("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("question_lib", dep, conSub)+") qlib,eluser el " +
					"where q.qlibid=qlib.id  and el.id = q.userid and q.parentid=0 and q.status!=1  " 
					+conditions+
					" order by q.qtype desc,q.id desc,q.status desc,q.createtime desc )t  where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql); 
			logger.info(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);  
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1),StringUtil.htmlParse(rs.getString(2)));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setStatus(rs.getInt(12));
				q.setEluser(new ELUser());
				q.setQexplain(rs.getString(13));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	//原来的
//	public int listMyQuestionsSize(Question question, int libfrom,boolean conSub) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null; 
//		String LidRid = "";
//		String conditions = "";
//		int size = 0;
//		try {
//			ct = DBConnection.getConnection();  
//			if (conSub) {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
//				ps.setInt(1, libfrom);
//				QuestionLib qlib = new QuestionLib(libfrom);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					qlib.setLid(rs.getInt(2));
//					qlib.setRid(rs.getInt(3));
//				}
//				rs.close();  
//				LidRid = " and qlib.lid >="+qlib.getLid()+" and qlib.rid<="+qlib.getRid();
//			}else{
//				LidRid = " and qlib.id="+libfrom;
//			} 
//			if(question != null){
//				if(question.getTitle() != null && !question.getTitle().equals("")){
//					conditions = conditions + " and  q.title like '%"+question.getTitle()+"%'";
//				}
//				if(question.getQtype() != 0){
//					conditions = conditions + "and q.qtype = "+question.getQtype();
//				}
//				if(question.getCreatetime()!=null){
//					conditions = conditions +" and q.createtime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//					.format(question.getCreatetime())+ "','yyyy-MM-dd HH24:mi:ss')";
//				}
//				if(question.getCreatetimeEnd()!=null){
//					conditions = conditions +" and q.createtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//					.format(question.getCreatetimeEnd())+ "','yyyy-MM-dd HH24:mi:ss')";
//				}  
//			} 
//			String sql = "select count(q.id) from question q ,question_lib qlib,eluser el " +
//					"where q.qlibid=qlib.id  and el.id = q.userid and q.parentid=0 " 
//					+conditions+LidRid;
//			ps = ct.prepareStatement(sql);  
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				size = rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("获取试题出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//
//		return size;
//	}
	
	public int listMyQuestionsSize(ElNode dep, int subdep, Question question) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String LidRid = "";
		String conditions = "";
		boolean conSub = subdep ==1?true :false;
		int size = 0;
		try {
			ct = DBConnection.getConnection();  
			if(question != null){
				if(question.getTitle() != null && !question.getTitle().equals("")){
					conditions = conditions + " and  q.title like '%"+StringUtil.toLikeStr(question.getTitle())+"%'";
				}
				if(question.getEluser() != null && question.getEluser().getRealname() != null && !question.getEluser().getRealname().equals("")){
					conditions = conditions + " and  el.realname like '%"+StringUtil.toLikeStr(question.getEluser().getRealname())+"%'";
				}
				if(question.getQtype() != 0){
					if(question.getQtype()==-3){
						//只查单选和多选题
						conditions = conditions + "and q.qtype in(2,4)";
					}else{
						conditions = conditions + "and q.qtype = "+question.getQtype();
					}
				}
				if(question.getStatus() != -1){
					conditions = conditions + "and q.status = "+question.getStatus();
				}
				if(question.getCreatetime()!=null){
					conditions = conditions +" and q.createtime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetime())+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if(question.getCreatetimeEnd()!=null){
					conditions = conditions +" and q.createtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetimeEnd())+ "','yyyy-MM-dd HH24:mi:ss')";
				}  
			} 
			String sql = "select count(q.id) from question q ,("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("question_lib", dep, conSub)+") qlib,eluser el " +
					"where q.qlibid=qlib.id  and el.id = q.userid and q.parentid=0 and q.status!=1  " 
					+conditions+LidRid;
			ps = ct.prepareStatement(sql);  
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return size;
	}
	
	public List<Question> listMyQuestions(Question question, int libfrom,boolean conSub) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		String LidRid = "";
		String conditions = "";
		try {
			ct = DBConnection.getConnection();  
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, libfrom);
				QuestionLib qlib = new QuestionLib(libfrom);
				rs = ps.executeQuery();
				if (rs.next()) {
					qlib.setLid(rs.getInt(2));
					qlib.setRid(rs.getInt(3));
				}
				rs.close();  
				LidRid = " and qlib.lid >="+qlib.getLid()+" and qlib.rid<="+qlib.getRid();
			}else{
				LidRid = " and qlib.id="+libfrom;
			} 
			if(question != null){
				if(question.getTitle() != null && !question.getTitle().equals("")){
					conditions = conditions + " and  q.title like '%"+question.getTitle()+"%'";
				}
				if(question.getQtype() != 0){
					conditions = conditions + "and q.qtype = "+question.getQtype();
				}
				if(question.getCreatetime()!=null){
					conditions = conditions +" and q.createtime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetime())+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if(question.getCreatetimeEnd()!=null){
					conditions = conditions +" and q.createtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(question.getCreatetimeEnd())+ "','yyyy-MM-dd HH24:mi:ss')";
				} 
				
			} 
			String sql = "select " +
					"qtype,title,subject,answer,qlevel,oldscore,qlib.name,q.qexplain,content,oldrulestring,qlib.id " +
					"from question q ,question_lib qlib,eluser el " +
					"where q.qlibid=qlib.id  and el.id = q.userid and q.parentid=0 " 
					+conditions+LidRid+
					//" order by q.createtime desc";
					" order by qlib.id asc";
			ps = ct.prepareStatement(sql);   
			rs = ps.executeQuery();
			
			String answer = "";
			String newtestsupport = ""; 
			while (rs.next()) {
				Question q = new Question();
				q.setQtype(rs.getInt(1));
				q.setTitle(rs.getString(2));
				q.setSubject(rs.getString(3));
				if (rs.getInt(1) == 10 || rs.getInt(1) == 5
						|| rs.getInt(1) == 8 || rs.getInt(1) == 6
						|| rs.getInt(1) == 9 || rs.getInt(1) == 1) {
					q.setTestsupport("");
				} else {
					if (rs.getString(3) != null) {
						if (rs.getString(3).contains(ElConstants.optSplit)) {
							newtestsupport = rs.getString(3).substring(
									0,
									rs.getString(3).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							newtestsupport = rs.getString(3);
						}
					}
					q.setTestsupport(newtestsupport);
				}
				if (rs.getInt(1) != 9) {
					if (rs.getInt(1) == 5) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					} else {
						if (rs.getString(4) != null) {
							if (rs.getInt(1) == 10) {
								if (rs.getString(4).trim().contains(
										ElConstants.optSplit)) {
									answer += rs.getString(4).substring(
											0,
											rs.getString(4).lastIndexOf(
													ElConstants.optSplit))
											.replaceAll(ElConstants.optSplit,
													"==");
								} else {
									answer = rs.getString(4).trim();
								}
							} else {
								for (int i = 0; i < rs.getString(4).split(
										ElConstants.optSplit).length; i++) {

									if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("0")) {
										answer += "A ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("1")) {
										answer += "B ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("2")) {
										answer += "C ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("3")) {
										answer += "D ";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("yes")) {
										answer += "正确";
									} else if (rs.getString(4).split(
											ElConstants.optSplit)[i].trim()
											.equals("no")) {
										answer += "错误";
									} else {
										answer += rs.getString(4).split(
												ElConstants.optSplit)[i].trim();
									}
								}
							}
						}
					}
				} else {
					if (rs.getString(4) != null) {
						if (rs.getString(4).contains(ElConstants.optSplit)) {
							answer += rs.getString(4).substring(
									0,
									rs.getString(4).lastIndexOf(
											ElConstants.optSplit)).replaceAll(
									ElConstants.optSplit, "==");
						} else {
							answer += rs.getString(4);
						}
					}
				}
				q.setAnswer(answer);
				q.setQlevel(rs.getInt(5));
				q
						.setOldrulestring(rs.getInt(1) != 9 ? rs.getString(6)
								: rs.getString(10).contains(
										ElConstants.ruleSplit) ? rs
										.getString(10)
										.substring(
												0,
												rs.getString(10).lastIndexOf(
														ElConstants.ruleSplit))
										.replaceAll(ElConstants.ruleSplit, "==")
										: rs.getString(10));
				QuestionLib questionLib = new QuestionLib();
				questionLib.setId(rs.getInt(11));
				questionLib.setName(rs.getString(7));
				q.setQlib(questionLib);
				q.setQexplain(rs.getString(8));
				q.setContent(rs.getString(9));
				//
				q.setParent(new Question(rs.getInt(11)));
				qs.add(q);
				answer = "";
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	/**
	 * 获取题库的左右id
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQuestionLibLRid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		QuestionLib qlib=new QuestionLib();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,lid,rid from question_lib where id=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				qlib.setId(rs.getInt(1));
				qlib.setLid(rs.getInt(2));
				qlib.setRid(rs.getInt(3));
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qlib;
	}

	public int minorproblem_Sum_scroe(int id) throws ElException  {
		int sum = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sum(e.scoreper) from question e where e.parentid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				sum = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sum;
	}
	/**
	 * 查询考场里面所有的试题
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public List<Question> listEroomQuestion(int roomid,Question question,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> questions=new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from(select distinct q.id,q.title qtitle,q.qtype,q.parentid,q2.title,qlib.id libid,qlib.name from study_questions sqt " +
					" inner join question q on sqt.qid=q.id " +
					" inner join question_lib qlib on q.qlibid=qlib.id " +
					" left join question q2 on q.parentid=q2.id inner join ("+
					" select id from study_quizinfo where roomid=?"+
					") sqi on sqt.sqid=sqi.id where q.title like ? and q.qtype!=7" +
					") t where rownum<=?) where rn>=?");
			ps.setInt(1, roomid);
			ps.setString(2, "%"+StringUtil.toLikeStr(question.getTitle())+"%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			Question q=null;
			while(rs.next()) {
				String qtitle=rs.getString(2);
				if(qtitle.length()>20){
					qtitle=qtitle.substring(0,20)+"...";
				}
				q=new Question(rs.getInt(1),qtitle);
				q.setQtype(rs.getInt(3));
				q.setParent(new Question(rs.getInt(4),rs.getString(5)));
				q.setQlib(new QuestionLib(rs.getInt(6),rs.getString(7)));
				questions.add(q);
			}
		} catch (Exception e) {
			logger.error("查询考场里面所有的试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return questions;
	}
	/**
	 * 查询考场里面所有的试题数量
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public int listEroomQuestionSize(int roomid,Question question) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(distinct q.id) from study_questions sqt " +
					" inner join question q on sqt.qid=q.id inner join ("+
					" select id from study_quizinfo where roomid=?"+
					") sqi on sqt.sqid=sqi.id where q.title like ? and q.qtype!=7");
			ps.setInt(1, roomid);
			ps.setString(2, "%"+StringUtil.toLikeStr(question.getTitle())+"%");
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询考场里面所有的试题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 根据考场id和题目id获取这个考场，这道题所在的答卷
	 * @param roomid
	 * @param qid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listSqidByRidQid(int roomid,int qid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myExamPapers=new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sqi.id from study_questions sqt " +
					" inner join study_quizinfo sqi on sqt.sqid=sqi.id where qid=? and sqi.roomid=?");
			ps.setInt(1, qid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			MyExamPaper myExamPaper=null;
			while(rs.next()) {
				myExamPaper=new MyExamPaper(rs.getInt(1));
				myExamPapers.add(myExamPaper);
			}
		} catch (Exception e) {
			logger.error("根据考场id和题目id获取这个考场，这道题所在的答卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myExamPapers;
	}
	/**
	 * 根据答卷和题目id更新学员所作答的题目分数
	 * @param qid
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public void updateStudyQuestionScore(int qid,int sqid,float score) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update study_questions set myscore=? where qid=? and sqid=?");
			ps.setFloat(1, score);
			ps.setInt(2, qid);
			ps.setInt(3, sqid);
			ps.executeQuery();
		} catch (Exception e) {
			logger.error("根据答卷和题目id更新学员所作答的题目分数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 获取题库中题目的数量
	 * @param qlibTree
	 * @param sublibs
	 * @param qtype
	 * @return
	 * @throws ElException
	 */
	public int getQlibQuestionCount(QuestionLib qlibTree,int sublibs,int qtype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean subool=sublibs==1?true:false;
		int count=0;
		try {
			ct = DBConnection.getConnection();
			String sql="select count(q.id) from question q" +
			" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("question_lib", qlibTree, subool)+") qlib on q.qlibid=qlib.id where q.qtype=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, qtype);
			rs=ps.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取题库中题目的数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	/**
	 * 获取题库中题目的数量(包含下级)
	 * @param qlibTree
	 * @param sublibs
	 * @param qtype
	 * @return
	 * @throws ElException
	 */
	public int getQlibQuestionCount(int qlibid,int qtype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from ("
				+" select qlib.lid,qlib.rid from question q inner join question_lib qlib on q.qlibid=qlib.id where q.qtype=?"
				+" )t1,(select lid,rid from question_lib where id=?) t2 where t1.lid>=t2.lid and t1.rid<=t2.rid");
			ps.setInt(1, qtype);
			ps.setInt(2, qlibid);
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取题库中题目的数量(包含下级)出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 查询试题库，标题后加题库内题目数量
	 * @param id
	 * @param userId
	 * @param stopid
	 * @param isContainStop
	 * @param sublibs 是否包含下级
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQlibTree(int id, int userId, int stopid,
			boolean isContainStop,int sublibs,int qtype) throws ElException {
		QuestionLib qls = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			if (id == 0){
				qls = getQLbRoot();
			}else{
				qls = getQLbById(id);
			}
			ct = DBConnection.getConnection();
			qls.setChild(listQuestionLibById(qls.getId(), userId, stopid,
					isContainStop, 0, ct,sublibs,qtype));
			qls.setName(qls.getName()+"<span style=\"color:blue;\">("+getQlibQuestionCount(qls, sublibs, qtype)+")</span>");
		} catch (Exception e) {
			logger.error("查询试题库，标题后加题库内题目数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qls;
	}
	private List<QuestionLib> listQuestionLibById(int parentid, int userId,
			int stopid, boolean isContainStop, int level, Connection ct,int sublibs,int qtype)
			throws Exception {
		List<QuestionLib> qls = new ArrayList<QuestionLib>();
		//PreparedStatement pstemp = ct.prepareStatement("select id,name ,parentid,description, lid, rid  from question_lib where parentid = ? and status!=1 order by id");
		String sql="select qlib.id,qlib.name ,qlib.parentid,qlib.description, qlib.lid, qlib.rid,q.qcount from question_lib qlib "+
		 " left join (select qlib.id,count(q.id) qcount from question q left join question_lib qlib on q.qlibid=qlib.id where q.qtype=? group by qlib.id) q on qlib.id=q.id "+
		 " where parentid = ? and status!=1 order by id";
		PreparedStatement pstemp = ct.prepareStatement(sql);
		pstemp.setInt(1, qtype);
		pstemp.setInt(2, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		int count=0;
		while (rstemp.next()) {
			QuestionLib qlb = new QuestionLib(rstemp.getInt(1), rstemp
					.getString(2));
			qlb.setParent(new QuestionLib(rstemp.getInt(3)));
			qlb.setDescription(rstemp.getString(4));
			qlb.setLevel(level);
			if(sublibs==1){
				count=getQlibQuestionCount(qlb.getId(), qtype);
			}else{
				count=rstemp.getInt(7);
			}
			qlb.setName(qlb.getName()+"<span style=\"color:blue;\">("+count+")</span>");
			if (qlb.getId() != stopid)
				qlb.setChild(listQuestionLibById(qlb.getId(), userId, stopid,
						isContainStop, level, ct,sublibs,qtype));
			if (!isContainStop && qlb.getId() == stopid) {

			} else
				qls.add(qlb);
		}
		rstemp.close();
		pstemp.close();
		return qls;
	}
	
	/**
	 * 查询试题库，标题后加题库内题目数量(非超级管理员通道)
	 * @param userid
	 * @param op
	 * @param stopid
	 * @param isContainStop
	 * @param sublibs
	 * @param qtype
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQlibTree(int userid, String op, int stopid,
			boolean isContainStop,int sublibs,int qtype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		QuestionLib dep = new QuestionLib(ElConstants.USER_OP_LIB, "可操作的题库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from questionlib_" + op
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<QuestionLib> list = new ArrayList<QuestionLib>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !isContainStop) {
				} else {
					QuestionLib depc = getQlibTree(depid, stopid,
							isContainStop, 1,sublibs,qtype);
					if(depc==null||depc.getId()==0){
						continue;
					}
					depc.setParent(dep);
					list.add(depc);
					nlist.add(depc);
				}
			}
			dep.setNchild(nlist);
			dep.setChild(list);
			if(sublibs==1){
				dep.setName(dep.getName()+"<span style=\"color:blue;\">("+getQlibQuestionCount(dep, sublibs, qtype)+")</span>");
			}
		} catch (Exception e) {
			logger.error("查询试题库，标题后加题库内题目数量(非超级管理员通道)出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	private QuestionLib getQlibTree(int id, int stopid, boolean isContainStop,
			int level,int sublibs,int qtype) throws ElException {
		QuestionLib qls = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			qls = getQLbById(id);
			if(qls==null||qls.getId()==0){
				return qls;
			}
			qls.setLevel(level);
			ct = DBConnection.getConnection();
			qls.setChild(listQuestionLibById(qls.getId(), stopid,
					isContainStop, level, ct,sublibs,qtype));
			qls.setName(qls.getName()+"<span style=\"color:blue;\">("+getQlibQuestionCount(qls, sublibs, qtype)+")</span>");
		} catch (Exception e) {
			logger.error("获取题库树出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qls;
	}
	
	private List<QuestionLib> listQuestionLibById(int parentid, int stopid,
			boolean isContainStop, int level, Connection ct,int sublibs,int qtype) throws Exception {
		List<QuestionLib> qls = new ArrayList<QuestionLib>();
//		PreparedStatement pstemp = ct
//				.prepareStatement("select id,name ,parentid,description, lid, rid  from question_lib where parentid = ? and status!=1");
		String sql="select qlib.id,qlib.name ,qlib.parentid,qlib.description, qlib.lid, qlib.rid,q.qcount from question_lib qlib "+
		 " left join (select qlib.id,count(q.id) qcount from question q left join question_lib qlib on q.qlibid=qlib.id where q.qtype=? group by qlib.id) q on qlib.id=q.id "+
		 " where parentid = ? and status!=1 order by id";
		PreparedStatement pstemp = ct.prepareStatement(sql);
		pstemp.setInt(1, qtype);
		pstemp.setInt(2, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		int count=0;
		while (rstemp.next()) {
			QuestionLib qlb = new QuestionLib(rstemp.getInt(1), rstemp
					.getString(2));
			qlb.setParent(new QuestionLib(rstemp.getInt(3)));
			qlb.setDescription(rstemp.getString(4));
			qlb.setLevel(level);
			if(sublibs==1){
				count=getQlibQuestionCount(qlb.getId(), qtype);
			}else{
				count=rstemp.getInt(7);
			}
			qlb.setName(qlb.getName()+"<span style=\"color:blue;\">("+count+")</span>");
			if (qlb.getId() != stopid)
				qlb.setChild(listQuestionLibById(qlb.getId(), stopid,
						isContainStop, level, ct,sublibs,qtype));
			if (!isContainStop && qlb.getId() == stopid) {

			} else
				qls.add(qlb);
		}
		rstemp.close();
		pstemp.close();
		return qls;
	}
	
	/**
	 * 试题查询参数设置
	 * @param question
	 * @param sql
	 * @param params
	 * @throws ElException
	 */
	public void questionParamSet(Question question,StringBuffer sql,Vector<Object> params) throws ElException {
		//不同题型需要做不同的判断
		if(question!=null){
			int qtype=question.getQtype();
			if(qtype==1||qtype==5||qtype==15||qtype==16){
				//判断题，填空题
				sql.append(" and qtype=? and qlibid=? and title=? and answer=? and status!=1 ");
				params.add(qtype);
				params.add(question.getQlib().getId());
				params.add(question.getTitle());
				params.add(question.getAnswer());
			}else if(qtype==2||qtype==4){
				//单选，多选题
				sql.append(" and qtype=? and qlibid=? and title=? and answer=? and subject=? and status!=1 ");
				params.add(qtype);
				params.add(question.getQlib().getId());
				params.add(question.getTitle());
				params.add(question.getAnswer());
				params.add(question.getOption());
			}else if(qtype==6||qtype==8){
				//打字，问答题
				sql.append(" and qtype=? and qlibid=? and title=? and status!=1 ");
				params.add(qtype);
				params.add(question.getQlib().getId());
				params.add(question.getTitle());
			}else if(qtype==9||qtype==10){
				//邮件，搜试题
				sql.append(" and qtype=? and qlibid=? and answer=? and status!=1 ");
				params.add(qtype);
				params.add(question.getQlib().getId());
				params.add(question.getAnswer());
			}else if(qtype==11){
				//邮件题
				sql.append(" and qtype=? and qlibid=? and title=? and subject=? and status!=1 ");
				params.add(qtype);
				params.add(question.getQlib().getId());
				params.add(question.getTitle());
				params.add(question.getOption());
			}else if(qtype==20){
				//排序题
				sql.append(" and qtype=? and qlibid=? and qexplain=?  and standard_answer=? and status!=1 ");
				params.add(qtype);
				params.add(question.getQlib().getId());
				params.add(question.getQexplain());
				params.add(question.getStandardAnswer());
			}else if(qtype==17){
				//角色扮演
				sql.append(" and qtype=? and qlibid=? and qexplain=?  and front_half_media_file= ? and status!=1 ");
				params.add(qtype);
				params.add(question.getQlib().getId());
				params.add(question.getQexplain());
				params.add(question.getFrontHalfMediaFile());
			}
			
			else{
				//其他题型不处理
				sql.append(" and 1!=1 ");
			}
		}
	}
	
	/**
	 * 检测试题是否重复
	 * @param question
	 * @throws ElException
	 */
	public boolean checkQuestionIsRepeat(Question question) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select id from question where 1=1 ");
			this.questionParamSet(question, sql, params);
			if(question!=null&&question.getId()>0){
				sql.append(" and id!=?");
				params.add(question.getId());
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			logger.info(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			logger.error("检测试题是否重复出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
//-----------------------------------------wjm1013修改----------------------------------------------------------
	public List<Question> listMyQuestions_wjm(String title, int libfrom, int type,
			boolean conSub,String sqlw, int pn, int pS) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			// String typeS = "";
			// if (type != 0)
			// typeS = type + "";
			if (null == title)
				title = "";
			else
				title = title.trim();
			if(type==0){
				if (conSub) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
					ps.setInt(1, libfrom);
					QuestionLib qlib = new QuestionLib(libfrom);
					rs = ps.executeQuery();
					if (rs.next()) {
						qlib.setLid(rs.getInt(2));
						qlib.setRid(rs.getInt(3));
					}
					rs.close();
					
						ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype," +
								"qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib where q.qlibid=qlib.id and  " +
								"q.title like ? and qlib.lid >=? and qlib.rid<=?  and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, qlib.getLid());
						ps.setInt(3, qlib.getRid());
						ps.setInt(4, pn);
						ps.setInt(5, pS);
					
				} else {
					
						ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name," +
								"q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib where q.qlibid=qlib.id and q.title like ? and qlib.id=? " +
								"   and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, libfrom);
						ps.setInt(3, pn);
						ps.setInt(4, pS);
					
				}
			}else{
				if (conSub) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
					ps.setInt(1, libfrom);
					QuestionLib qlib = new QuestionLib(libfrom);
					rs = ps.executeQuery();
					if (rs.next()) {
						qlib.setLid(rs.getInt(2));
						qlib.setRid(rs.getInt(3));
					}
					rs.close();
					
						ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype," +
								"qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib where q.qlibid=qlib.id and  " +
								"q.title like ? and qlib.lid >=? and qlib.rid<=? and q.qtype=? and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, qlib.getLid());
						ps.setInt(3, qlib.getRid());
						ps.setInt(4, type);
						ps.setInt(5, pn);
						ps.setInt(6, pS);
					
				} else {
					
						ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name," +
								"q.qlevel,q.scoreper,q.parentid,q.minword,q.status,q.fwsize from question q ,question_lib qlib where q.qlibid=qlib.id and q.title like ? and qlib.id=? " +
								"  and q.qtype=? and q.parentid=0 "+sqlw+"  order by q.id desc,q.createtime desc )t  where rownum <=? ) where rn >=?");
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, libfrom);
						ps.setInt(3, type);
						ps.setInt(4, pn);
						ps.setInt(5, pS);
					
				}
			}
			
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setQlib(new QuestionLib(rs.getInt(3), rs.getString(7)));
				q.setCreatetime(rs.getTimestamp(4));
				q.setModifytime(rs.getTimestamp(5));
				q.setQtype(rs.getInt(6));
				q.setQlevel(rs.getInt(8));
				q.setScoreper(rs.getInt(9));
				q.setParent(new Question(rs.getInt(10)));
				q.setMinWord(rs.getInt(11));
				q.setStatus(rs.getInt(12));
				q.setEluser(new ELUser());
				q.setFwsize(rs.getInt(13));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return qs;
	}
	
	public int listMyQuestionsSize_wjm(String title, int libfrom, int type,
			boolean conSub,String sqlw) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();
			if(type==0){
				if (conSub) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
					ps.setInt(1, libfrom);
					QuestionLib qlib = new QuestionLib(libfrom);
					rs = ps.executeQuery();
					if (rs.next()) {
						qlib.setLid(rs.getInt(2));
						qlib.setRid(rs.getInt(3));
					}
					rs.close();
						ps = ct
								.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and q.title like ?  and qlib.lid >=? and qlib.rid<=? and q.parentid=0"+sqlw);
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, qlib.getLid());
						ps.setInt(3, qlib.getRid());
				} else {
						ps = ct
								.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and  q.title like ?  and qlib.id=? and q.parentid=0"+sqlw);
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, libfrom);

				}

			}else{
				if (conSub) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(QuestionConstants.QLIB_QUERYLRID_BYIDANDUID));
					ps.setInt(1, libfrom);
					QuestionLib qlib = new QuestionLib(libfrom);
					rs = ps.executeQuery();
					if (rs.next()) {
						qlib.setLid(rs.getInt(2));
						qlib.setRid(rs.getInt(3));
					}
					rs.close();
						ps = ct
								.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and q.title like ?  and qlib.lid >=? and qlib.rid<=?  and q.qtype=? and q.parentid=0"+sqlw);
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, qlib.getLid());
						ps.setInt(3, qlib.getRid());
						ps.setInt(4, type);
				} else {
						ps = ct
								.prepareStatement("select count(*) from question q ,question_lib qlib where q.qlibid=qlib.id and  q.title like ?  and qlib.id=?  and q.qtype=? and q.parentid=0"+sqlw);
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, libfrom);
						ps.setInt(3, type);

				}

			}
			
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取试题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return 0;
	}
}
