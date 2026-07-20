package com.sopia.questionman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.quiz.EpQStatus;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Typelrid;
import com.sopia.questionman.QuestionConstants;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.ExampaperRandom;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.studyman.entities.MyExamPaper;

public class ExamPaperDaoImpl extends ElNodeDao implements ExamPaperDao {
	private static final Log logger = LogFactory.getLog(ExamPaperDaoImpl.class);

	public ExamPaperBlock getEpbWithQuestionsById(int id) throws ElException {
		ExamPaperBlock epb = getEpbById(id);
		epb.setQuestions(listEpBlockQusetionsByBepbId(id));
		return epb;
	}

	public ExamPaper getEPAllInfoById(int id) throws ElException {
		ExamPaper examPaper = getExamPaperById(id);
		List<ExamPaperBlock> epblocks = listEpBlockByEpid(id);
		int epbsize = epblocks.size();
		for (int i = 0; i < epbsize; i++) {
			setQu(epblocks.get(i));
		}
		examPaper.setEpBlocks(epblocks);
		return examPaper;
	}

	/**
	 * 按大题设置规则，组装大题的小题
	 * 
	 * @param epb
	 * @throws ElException
	 */
	private void setQu(ExamPaperBlock epb) throws ElException {
		if (null == epb)
			return;
//		QuestionDao qd = (QuestionDao)SpringContextUtil.getBean("questionDao");
		if (epb.getRandom() == 0) {
			// 手工
			List<Question> qs = listEpBlockQusetionsByBepbId(epb.getId());
			int qsize = qs.size();
			for (int j = 0; j < qsize; j++) {
				EpQStatus.addQuestion(qs.get(j).getId());
//				qd.setQuestionStatus(qs.get(j).getId(), 0);
				qs.get(j).setEpblock(epb);
				if (qs.get(j).getQtype() == 7) {
					// 材料题的时候
					qs.get(j).setChilds(getQChildbyPid(qs.get(j).getId()));
				}
			}
			epb.setQuestions(qs);
		} else {
			// 随机
			if (epb.getQuestions() == null)
				epb.setQuestions(new ArrayList<Question>());
			// 随机规则列表
			List<ExampaperRandom> eprs = listEpbRandom(epb.getId());
			int sortid = 0;
			int eprssize = eprs.size();
			for (int i = 0; i < eprssize; i++) {
				ExampaperRandom er = eprs.get(i);
				int qlibid = er.getQlib().getId();
				int type = er.getEpBlock().getType();
				int subop = er.getSuboperate();
				if(epb.getType()==8){//打字题随机需考虑范文长度
					List<Question> qs = getRandomQ(qlibid, type, 1, subop, er
							.getQlevel1(), sortid,epb.getFwsize());
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 2, subop, er.getQlevel2(), sortid,epb.getFwsize());
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 3, subop, er.getQlevel3(), sortid,epb.getFwsize());
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 4, subop, er.getQlevel4(), sortid,epb.getFwsize());
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 5, subop, er.getQlevel5(), sortid,epb.getFwsize());
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 0, subop, er.getQlevel(), sortid,epb.getFwsize());
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
				}else{
					List<Question> qs = getRandomQ(qlibid, type, 1, subop, er
							.getQlevel1(), sortid);
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 2, subop, er.getQlevel2(), sortid);
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 3, subop, er.getQlevel3(), sortid);
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 4, subop, er.getQlevel4(), sortid);
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
	//						+ "  subop:" + subop + "  er.getQlevel5():"
	//						+ er.getQlevel5() + "  sortid:" + sortid + "");
					qs = getRandomQ(qlibid, type, 5, subop, er.getQlevel5(), sortid);
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					qs = getRandomQ(qlibid, type, 0, subop, er.getQlevel(), sortid);
					sortid = qs.size() + sortid;
					epb.getQuestions().addAll(qs);
					/*
					 * qs = getRandomQ(qlibid, type, 1, subop, er .getQlevel1(),
					 * sortid); sortid = qs.size()+sortid;
					 * epb.getQuestions().addAll( getRandomQ(qlibid, type, 2, subop,
					 * er.getQlevel2(), epb .getQuestions().size()));
					 * epb.getQuestions().addAll( getRandomQ(qlibid, type, 3, subop,
					 * er.getQlevel3(), epb .getQuestions().size()));
					 * epb.getQuestions().addAll( getRandomQ(qlibid, type, 4, subop,
					 * er.getQlevel4(), epb .getQuestions().size()));
					 * epb.getQuestions().addAll( getRandomQ(qlibid, type, 5, subop,
					 * er.getQlevel5(), epb .getQuestions().size()));
					 * epb.getQuestions().addAll( getRandomQ(qlibid, type, 0, subop,
					 * er.getQlevel(), epb .getQuestions().size()));
					 */
				}
			}
			List<Question> qs = epb.getQuestions();
			if (null != qs){
				int qsize = qs.size();
				for (int j = 0; j < qsize; j++) {
//					qd.setQuestionStatus(qs.get(j).getId(), 0);
					EpQStatus.addQuestion(qs.get(j).getId());
					qs.get(j).setEpblock(epb);
					 if (qs.get(j).getQtype() == 7) {
						 qs.get(j).setChilds(getQChildbyPid(qs.get(j).getId()));
					 }
				}
			}
		}

	}
	public List<ExampaperRandom> getEPRandomsBy(int qlib, int type, int fwsize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExampaperRandom> list = new ArrayList<ExampaperRandom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name from question_lib where parentid = ? and status!=1 ");
			ps.setInt(1, qlib);
			rs = ps.executeQuery();
			while (rs.next()) {
				int qlibid = rs.getInt(1);
					ExampaperRandom er= getEPRandomBy(qlibid, type, 1);
					er.setQlib(new QuestionLib(qlibid,rs.getString(2)));
					list.add(er);
			}
		} catch (Exception e) {
			logger.error("获取题库子节点，数量及类型错误！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	public ExampaperRandom getEPRandomBy(int qlib, int type, int subop)
			throws ElException {
		ExampaperRandom epr = new ExampaperRandom();
		epr.setQlevel(getRandomQSize(qlib, type, 0, subop));
		epr.setQlevel1(getRandomQSize(qlib, type, 1, subop));
		epr.setQlevel2(getRandomQSize(qlib, type, 2, subop));
		epr.setQlevel3(getRandomQSize(qlib, type, 3, subop));
		epr.setQlevel4(getRandomQSize(qlib, type, 4, subop));
		epr.setQlevel5(getRandomQSize(qlib, type, 5, subop));
		return epr;
	}
	public ExampaperRandom getEPRandomBy(int qlib, int type, int subop,
			int fwsize) throws ElException {
		ExampaperRandom epr = new ExampaperRandom();
		epr.setQlevel(getRandomQSize(qlib, type, 0, subop,fwsize));
		epr.setQlevel1(getRandomQSize(qlib, type, 1, subop,fwsize));
		epr.setQlevel2(getRandomQSize(qlib, type, 2, subop,fwsize));
		epr.setQlevel3(getRandomQSize(qlib, type, 3, subop,fwsize));
		epr.setQlevel4(getRandomQSize(qlib, type, 4, subop,fwsize));
		epr.setQlevel5(getRandomQSize(qlib, type, 5, subop,fwsize));
		return epr;
	}
	public void addepLib(ExamPaperLib examPaperLib) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// addNode(ct, examPaperLib, "exampaperlib", "1 =1 ");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPLIB_ADD));
			ps.setString(1, examPaperLib.getName());
			ps.setInt(2, examPaperLib.getParent().getId());
			ps.setString(3, examPaperLib.getDescription());
			ps.setInt(4, examPaperLib.getLid());
			ps.setInt(5, examPaperLib.getRid());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('exampaperlib') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select exampaperlib_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				examPaperLib.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterEpl(ExamPaperLib examPaperLib, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// alterNode(ct, examPaperLib, "exampaperlib", "1 =1 ");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPLIB_ALTER));
			ps.setString(1, examPaperLib.getName());
			ps.setInt(2, examPaperLib.getParent().getId());
			ps.setString(3, examPaperLib.getDescription());
			ps.setInt(4, examPaperLib.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteEpl(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// TODO 删除
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from EXAMPAPERLIB where id =? ");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新试卷库的状态
	 * @param id
	 * @throws ElException
	 */
	public void deleteEplNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update EXAMPAPERLIB set status=1,lid=0,rid=0 where id =? ");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新试卷库的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 删除试卷库以及下级试卷库和试卷
	 */
	public void deleteEpAndSub(int id) throws ElException {
		// 查出该类别的左右id，然后查出所有子类别，然后循环根据id删除子类别，删除所有类别下的课程最后删除该类别
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid = this.getLidRid(ct, id, "exampaperlib");
			List<Integer> typelist = this.getTypeByLidRid(ct,
					typelrid.getLid(), typelrid.getRid(), "exampaperlib");
			for (int i = 0; i < typelist.size(); i++) {
				// 根据id删除类别以及类别下的资源(先删资源)
				this.deleteExamPaperByTypeid(ct, typelist.get(i));
				this.deleteEpl(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("删除试卷库以及下级试卷库和试卷失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 假删除试卷类别
	 * @param id
	 * @throws ElException
	 */
	public void deleteEpAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid = this.getLidRid(ct, id, "exampaperlib");
			List<Integer> typelist = this.getTypeByLidRid(ct,
					typelrid.getLid(), typelrid.getRid(), "exampaperlib");
			for (int i = 0; i < typelist.size(); i++) {
				// 根据id删除类别以及类别下的资源(先删资源)
				this.deleteExamPaperByTypeidNot(typelist.get(i));
				this.deleteEplNot(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("假删除试卷类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取树的左右id
	 * 
	 * @param typeId
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public Typelrid getLidRid(Connection ct, int typeId, String tabName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		Typelrid type = null;
		try {
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select lid,rid from " + tabName
					+ " where id=?");
			ps.setInt(1, typeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				type = new Typelrid(rs.getInt(1), rs.getInt(2));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		}
		return type;
	}

	/**
	 * 根据左右id获取树的id集合
	 * 
	 * @param lid
	 * @param rid
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getTypeByLidRid(Connection ct, int lid, int rid,
			String tabName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from " + tabName
					+ " where lid>=? and rid<=? ");
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

	public ExamPaperLib epLibTree(int id, int userid, int stopid,
			boolean isContainStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaperLib epl = null;
		if (id == 0)
			epl = getEPLRoot();
		else
			epl = getEpLById(id);

		try {
			ct = DBConnection.getConnection();
			epl.setChild(listEplById(epl.getId(), userid, stopid,
					isContainStop, 0, ct));
		} catch (Exception e) {
			logger.error("获取全部试卷库列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epl;
	}

	private ExamPaperLib epLibTree(int id, int stopid, boolean isContainStop,
			int level) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaperLib epl = null;
		epl = getEpLById(id);
		if(epl==null||epl.getId()==0){
			return epl;
		}
		try {
			epl.setLevel(level);
			ct = DBConnection.getConnection();
			epl.setChild(listEplById(epl.getId(), stopid, isContainStop,
							0, ct));
		} catch (Exception e) {
			logger.error("获取全部试卷库列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epl;
	}

	private List<ExamPaperLib> listEplById(int parentid, int stopid,
			boolean isContainStop, int level, Connection ct) throws Exception {
		List<ExamPaperLib> qls = new ArrayList<ExamPaperLib>();
		PreparedStatement pstemp = ct
				.prepareStatement("select id,name, parentid,description,lid,rid from exampaperlib where parentid = ? and status!=1");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			ExamPaperLib epl = new ExamPaperLib(rstemp.getInt(1), rstemp
					.getString(2));
			epl.setParent(new ExamPaperLib(rstemp.getInt(3)));
			epl.setDescription(rstemp.getString(4));
			epl.setLid(rstemp.getInt(5));
			epl.setRid(rstemp.getInt(6));
			epl.setLevel(level);
			if (epl.getId() != stopid)
				epl.setChild(listEplById(epl.getId(), stopid, isContainStop,
						level, ct));
			if (!isContainStop && epl.getId() == stopid) {

			} else
				qls.add(epl);
		}
		rstemp.close();
		pstemp.close();
		return qls;
	}

	public ExamPaperLib epLibTree(String op, int userid, int stopid,
			boolean isContainStop) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// ExamPaperLib dep = op.equals("op") ? new ExamPaperLib(1, "可操作的试卷库")
		// : new ExamPaperLib(1, "可使用的试卷库");
		ExamPaperLib dep = new ExamPaperLib(ElConstants.USER_OP_LIB, "可操作的试卷库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from exampaperlib_" + op
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<ExamPaperLib> list = new ArrayList<ExamPaperLib>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !isContainStop) {
				} else {
					ExamPaperLib depc = epLibTree(depid, stopid, isContainStop,
							1);
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

	private List<ExamPaperLib> listEplById(int parentid, int userId,
			int stopid, boolean isContainStop, int level, Connection ct)
			throws Exception {
		List<ExamPaperLib> qls = new ArrayList<ExamPaperLib>();
//		PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
//				.getSQL(QuestionConstants.EPLIB_QUERY_BYPARENTIDANDUID));
		PreparedStatement pstemp = ct.prepareStatement("select id,name ,parentid,description,lid,rid from exampaperlib where parentid = ? and status!=1 order by id");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			ExamPaperLib epl = new ExamPaperLib(rstemp.getInt(1), rstemp
					.getString(2));
			epl.setParent(new ExamPaperLib(rstemp.getInt(3)));
			epl.setDescription(rstemp.getString(4));
			epl.setLid(rstemp.getInt(5));
			epl.setRid(rstemp.getInt(6));
			epl.setLevel(level);
			if (epl.getId() != stopid)
				epl.setChild(listEplById(epl.getId(), userId, stopid,
						isContainStop, level, ct));
			if (!isContainStop && epl.getId() == stopid) {

			} else
				qls.add(epl);
		}
		rstemp.close();
		pstemp.close();
		return qls;
	}

	/*
	 * public ExamPaperLib getEpLById(int id ) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * try { ct = DBConnection.getConnection(); ps = ct.prepareStatement("select
	 * el.id,el.name,el.parentid, el.description,elp.name from exampaperlib el,
	 * exampaperlib elp where el.parentid=elp.id and el.id =?"); ps.setInt(1,
	 * id); rs = ps.executeQuery(); if (rs.next()) { ExamPaperLib exLib = new
	 * ExamPaperLib(rs.getInt(1), rs .getString(2)); exLib .setParent(new
	 * ExamPaperLib(rs.getInt(3), rs .getString(5)));
	 * exLib.setDescription(rs.getString(4)); return exLib; } } catch (Exception
	 * e) { logger.error("获取试卷库出错！", e); throw new ElException(e); } finally {
	 * DBConnection.closeConnectInfo(ct, ps, rs); } return null; }
	 */
	public ExamPaperLib getEpLById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(QuestionConstants.EPLIB_QUERY_BYIDANDUID));
			ps = ct.prepareStatement("select el.id,el.name,el.parentid, el.description,elp.name,el.lid,el.rid " +
					" from exampaperlib el left join  exampaperlib elp on el.parentid=elp.id and elp.status!=1 where el.id =? and el.status!=1");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ExamPaperLib exLib = new ExamPaperLib(rs.getInt(1), rs
						.getString(2));
				exLib
						.setParent(new ExamPaperLib(rs.getInt(3), rs
								.getString(5)));
				exLib.setDescription(rs.getString(4));
				exLib.setLid(rs.getInt(6));
				exLib.setRid(rs.getInt(7));
				return exLib;
			}
		} catch (Exception e) {
			logger.error("获取试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}

	/**
	 * 获取试卷库列表
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaperLib> getExampaperlib() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperLib> lib = new ArrayList();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name from exampaperlib ");
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperLib exLib = new ExamPaperLib(rs.getInt(1), rs
						.getString(2));
				lib.add(exLib);
			}
		} catch (Exception e) {
			logger.error("获取试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lib;
	}

	public List<ExamPaperLib> listEpChild(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperLib> epls = new ArrayList<ExamPaperLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select el.id,el.name, el.description  from EXAMPAPERLIB el "
							+ " where el.parentid =? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperLib exLib = new ExamPaperLib(rs.getInt(1), rs
						.getString(2));
				// exLib.setParent(new
				// ExamPaperLib(rs.getInt(3),rs.getString(5)));
				exLib.setDescription(rs.getString(3));
				epls.add(exLib);
			}
		} catch (Exception e) {
			logger.error("删除试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epls;
	}

	public void setEplparent(int id, int parentid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPLIB_PARENT_SET));
			ps.setInt(1, parentid);
			ps.setInt(2, id);
			ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 设置父id
	 * 
	 * @param id
	 * @param parentid
	 * @throws ElException
	 */
	public void setEplparent2(int id, int parentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exampaperlib set parentid=? where parentid =? ");
			ps.setInt(1, parentid);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 设置试卷父id
	 * 
	 * @param id
	 * @param parentid
	 * @throws ElException
	 */
	public void setEpparent(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exampaper set eplid=? where eplid =? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置试卷父id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ExamPaperLib getEPLRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaperLib epl = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPLIB_QUERY_BYPARENTIDANDUID));
			ps.setInt(1, ElConstants.TREE_ROOT);
			rs = ps.executeQuery();
			if (rs.next()) {
				epl = new ExamPaperLib(rs.getInt(1), rs.getString(2));
				epl.setParent(new ExamPaperLib(rs.getInt(3)));
				epl.setDescription(rs.getString(4));
				epl.setLid(rs.getInt(5));
				epl.setRid(rs.getInt(6));
				// return epl;
			}
			rs.close();
		} catch (Exception e) {
			logger.error("获取试卷库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epl;
	}

	// 试卷管理------------------
	public void addExamPaper(ExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EXAMPAPER_ADD));
			ps.setString(1, examPaper.getTitle());
			ps.setString(2, examPaper.getDescription());
			ps.setInt(3, examPaper.getElUser().getId());
			ps.setInt(4, examPaper.getEpl().getId());
			ps.setInt(5, examPaper.getShowmod());
			ps.setInt(6, examPaper.getDuring());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			// if (null != examPaper.getBegintime())
			// ps.setDate(8, new Date(examPaper.getBegintime().getTime()));
			// else
			// ps.setDate(8, null);
			// if (null != examPaper.getEndtime())
			// ps.setDate(9, new Date(examPaper.getEndtime().getTime()));
			// else
			// ps.setDate(9, null);
			ps.setBoolean(8, examPaper.getOpentimelimit());
			ps.setFloat(9, examPaper.getEp_tscore());
			ps.setString(10, examPaper.getQueryurl());
			ps.setInt(11, 2); // 编辑中2
			ps.setInt(12, examPaper.getShowType());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('exampaper') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select exampaper_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				examPaper.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int getExamPaperId(String title, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from EXAMPAPER where title = ? and userid = ?");
			ps.setString(1, title);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

	// public ExamPaper getExamPaperById(int id, int userid) throws ElException
	// {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// ExamPaper ep = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYIDANDUID));
	// ps.setInt(1, id);
	// ps.setInt(2, userid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// ep = new ExamPaper(rs.getInt(1), rs.getString(2));
	// ep.setDescription(rs.getString(3));
	// ep.setElUser(new ELUser(rs.getInt(4)));
	// ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
	// ep.setRandom(rs.getBoolean(6));
	// ep.setDuring(rs.getInt(7));
	// ep.setModifytime(rs.getTimestamp(8));
	// ep.setCreatetime(rs.getTimestamp(9));
	// // ep.setBegintime(rs.getDate(10));
	// // ep.setEndtime(rs.getDate(11));
	// ep.setOpentimelimit(rs.getBoolean(10));
	// ep.setEp_tscore(rs.getInt(12));
	// }
	// } catch (Exception e) {
	// logger.error("获取试卷出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return ep;
	// }

	public ExamPaper getExamPaperById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				// ep.id,ep.title,ep.description,ep.userid,ep.eplid,
				// ep.israndom,ep.during,
				// ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9));
				// ep.setBegintime(rs.getDate(10));
				// ep.setEndtime(rs.getDate(11));
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getFloat(12));
				ep.setEp_realscore(rs.getFloat(13));
				ep.setQueryurl(rs.getString(14));
				ep.setShowType(rs.getInt(15));
			}
		} catch (Exception e) {
			logger.error("获取试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public void alterExamPaper(ExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EXAMPAPER_ALTER));
			ps.setString(1, examPaper.getTitle());
			ps.setString(2, examPaper.getDescription());
			ps.setInt(3, examPaper.getEpl().getId());
			ps.setInt(4, examPaper.getShowmod());
			ps.setInt(5, examPaper.getDuring());
			ps.setBoolean(6, examPaper.getOpentimelimit());
			ps.setFloat(7, examPaper.getEp_tscore());
			ps.setString(8, examPaper.getQueryurl());
			ps.setInt(9, examPaper.getShowType());
			ps.setInt(10, examPaper.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExamPaper> listEpsByEplId(int eplid, String title,
			boolean conSub, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, eplid);
				ExamPaperLib epl = new ExamPaperLib(eplid);
				rs = ps.executeQuery();
				if (rs.next()) {
					epl.setLid(rs.getInt(2));
					epl.setRid(rs.getInt(3));
				}
				String sql = "select * from (select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname,ep.status from eluser el, exampaper ep, exampaperlib epl where el.id=ep.userid and ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=? order by ep.createtime desc )t  where rownum <=? ) where rn >=?";
				rs.close();
				/*
				 * ps = ct.prepareStatement(ElQuerySql
				 * .getSQL(QuestionConstants.EXAMPAPER_QUERY_MYLIST_SUB));
				 */
				ps = ct.prepareStatement(sql);
				// ps = ct.prepareStatement("select * from (select t.*,rownum rn
				// from (select
				// ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname
				// from eluser el, exampaper ep, exampaperlib epl where
				// el.id=ep.userid and ep.eplid = epl.id and ep.title like ? and
				// epl.lid >= ? and epl.rid<=? order by ep.createtime desc )t
				// where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, epl.getLid());
				ps.setInt(3, epl.getRid());
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);

			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EXAMPAPER_QUERY_MYLIST));
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, eplid);
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			int userid = 1;
			UserDao ud = new UserDaoImpl();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9));
				// ep.setBegintime(rs.getDate(10));
				// ep.setEndtime(rs.getDate(11));
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getInt(12));
				ep.setStatus(rs.getInt(14));
				user = new ELUser();
				userid = rs.getInt("userid");
				user = ud.getUserById(userid);
				// user.setRealname(rs.getString("realname"));
				ep.setElUser(user);
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}

	public List<ExamPaper> listEpsByEplId(int eplid, String title,
			boolean conSub, String sqlw, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, eplid);
				ExamPaperLib epl = new ExamPaperLib(eplid);
				rs = ps.executeQuery();
				if (rs.next()) {
					epl.setLid(rs.getInt(2));
					epl.setRid(rs.getInt(3));
				}
				String sql = "select * from (select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname,ep.status from eluser el, exampaper ep, exampaperlib epl where el.id=ep.userid and ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=? "
						+ sqlw
						+ " order by ep.createtime desc )t  where rownum <=? ) where rn >=?";
				rs.close();
				/*
				 * ps = ct.prepareStatement(ElQuerySql
				 * .getSQL(QuestionConstants.EXAMPAPER_QUERY_MYLIST_SUB));
				 */
				ps = ct.prepareStatement(sql);
				// ps = ct.prepareStatement("select * from (select t.*,rownum rn
				// from (select
				// ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname
				// from eluser el, exampaper ep, exampaperlib epl where
				// el.id=ep.userid and ep.eplid = epl.id and ep.title like ? and
				// epl.lid >= ? and epl.rid<=? order by ep.createtime desc )t
				// where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, epl.getLid());
				ps.setInt(3, epl.getRid());
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);

			} else {
				ps = ct
						.prepareStatement("select * from(select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,ep.status from exampaper ep, exampaperlib epl where ep.eplid = epl.id  and ep.title like ? and ep.eplid = ? "
								+ sqlw
								+ " order by ep.createtime desc )t  where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, eplid);
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			int userid = 1;
			UserDao ud = new UserDaoImpl();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9));
				// ep.setBegintime(rs.getDate(10));
				// ep.setEndtime(rs.getDate(11));
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getInt(12));
				ep.setStatus(rs.getInt(14));
				user = new ELUser();
				userid = rs.getInt("userid");
				user = ud.getUserById(userid);
				// user.setRealname(rs.getString("realname"));
				ep.setElUser(user);
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}

	/**
	 * 根据树查出试卷内容
	 * 
	 * @param etree
	 * @param eplid
	 * @param title
	 * @param conSub
	 * @param sqlw
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> listEpsByEplId(ExamPaperLib eplTree, int eplid,
			String title, boolean conSub, String sqlw, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, eplid);
				ExamPaperLib epl = new ExamPaperLib(eplid);
				rs = ps.executeQuery();
				if (rs.next()) {
					epl.setLid(rs.getInt(2));
					epl.setRid(rs.getInt(3));
				}
				String ids = ExamPaperLibId(eplTree, eplid);
				String sql = "select * from (select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname,ep.status from eluser el, exampaper ep, (select * from exampaperlib where  id in("
						+ ids
						+ ")) epl where el.id=ep.userid and ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=? "
						+ sqlw
						+ " order by ep.createtime desc )t  where rownum <=? ) where rn >=?";
				rs.close();
				/*
				 * ps = ct.prepareStatement(ElQuerySql
				 * .getSQL(QuestionConstants.EXAMPAPER_QUERY_MYLIST_SUB));
				 */
				ps = ct.prepareStatement(sql);
				// ps = ct.prepareStatement("select * from (select t.*,rownum rn
				// from (select
				// ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname
				// from eluser el, exampaper ep, exampaperlib epl where
				// el.id=ep.userid and ep.eplid = epl.id and ep.title like ? and
				// epl.lid >= ? and epl.rid<=? order by ep.createtime desc )t
				// where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, epl.getLid());
				ps.setInt(3, epl.getRid());
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);

			} else {
				ps = ct
						.prepareStatement("select * from(select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,ep.status from exampaper ep, exampaperlib epl where ep.eplid = epl.id  and ep.title like ? and ep.eplid = ? "
								+ sqlw
								+ " order by ep.createtime desc )t  where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, eplid);
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			int userid = 1;
			UserDao ud = new UserDaoImpl();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9));
				// ep.setBegintime(rs.getDate(10));
				// ep.setEndtime(rs.getDate(11));
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getInt(12));
				ep.setStatus(rs.getInt(14));
				user = new ELUser();
				userid = rs.getInt("userid");
				user = ud.getUserById(userid);
				// user.setRealname(rs.getString("realname"));
				ep.setElUser(user);
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}

	// 原来的
	// public int listEpsByEpIdSize(int eplid, String title, boolean conSub)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// if (null == title)
	// title = "";
	// else
	// title = title.trim();
	//
	// if (conSub) {
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
	// ps.setInt(1, eplid);
	// ExamPaperLib epl = new ExamPaperLib(eplid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// epl.setLid(rs.getInt(2));
	// epl.setRid(rs.getInt(3));
	// }
	// rs.close();
	// ps = ct
	// .prepareStatement("select count(*) from exampaper ep, exampaperlib epl
	// where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and
	// epl.rid<=?");
	// ps.setString(1, "%" + title + "%");
	// ps.setInt(2, epl.getLid());
	// ps.setInt(3, epl.getRid());
	//
	// } else {
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(QuestionConstants.EXAMPAPER_QUERY_MYLIST_SIZE));
	// ps.setString(1, "%" + title + "%");
	// ps.setInt(2, eplid);
	// }
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("检测试卷标题出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	public int listEpsByEpIdSize(int eplid, String title, boolean conSub,
			String sqlw) throws ElException {
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
						.getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, eplid);
				ExamPaperLib epl = new ExamPaperLib(eplid);
				rs = ps.executeQuery();
				if (rs.next()) {
					epl.setLid(rs.getInt(2));
					epl.setRid(rs.getInt(3));
				}
				rs.close();
				ps = ct
						.prepareStatement("select count(*) from exampaper ep, exampaperlib epl where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=?"
								+ sqlw);
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, epl.getLid());
				ps.setInt(3, epl.getRid());

			} else {
				ps = ct
						.prepareStatement("select count(*) from exampaper ep, exampaperlib epl where ep.eplid = epl.id and ep.title like ? and ep.eplid = ? "
								+ sqlw);
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, eplid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/*
	 * 根据树查出试卷内容(non-Javadoc)
	 * 
	 * @see com.sopia.questionman.dao.ExamPaperDao#listEpsByEpIdSize(int,
	 *      java.lang.String, boolean, java.lang.String)
	 */
	public int listEpsByEpIdSize(ExamPaperLib eplTree, int eplid, String title,
			boolean conSub, String sqlw) throws ElException {
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
						.getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, eplid);
				ExamPaperLib epl = new ExamPaperLib(eplid);
				rs = ps.executeQuery();
				if (rs.next()) {
					epl.setLid(rs.getInt(2));
					epl.setRid(rs.getInt(3));
				}
				rs.close();
				String ids = ExamPaperLibId(eplTree, eplid);
				ps = ct
						.prepareStatement("select count(*) from exampaper ep, (select * from exampaperlib where  id in("
								+ ids
								+ ") ) epl where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=?"
								+ sqlw);
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, epl.getLid());
				ps.setInt(3, epl.getRid());

			} else {
				ps = ct
						.prepareStatement("select count(*) from exampaper ep, exampaperlib epl where ep.eplid = epl.id and ep.title like ? and ep.eplid = ? "
								+ sqlw);
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, eplid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ExamPaper> exampaper_list_listEpsByEplId(ExamPaperLib eplTree,
			int eplid, String title, boolean conSub, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

			String ids = ExamPaperLibId(eplTree, eplid);

			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, eplid);
				ExamPaperLib epl = new ExamPaperLib(eplid);
				rs = ps.executeQuery();
				if (rs.next()) {
					epl.setLid(rs.getInt(2));
					epl.setRid(rs.getInt(3));
				}
				rs.close();
				/*
				 * ps = ct.prepareStatement(ElQuerySql
				 * .getSQL(QuestionConstants.EXAMPAPER_QUERY_MYLIST_SUB));
				 */
				ps = ct
						.prepareStatement("select * from(select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from exampaper ep,  (select * from exampaperlib where  id in("
								+ ids
								+ ") ) epl where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=? order by ep.createtime desc )t  where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, epl.getLid());
				ps.setInt(3, epl.getRid());
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);

			} else {
				ids = eplid == 1 ? ids.substring(2, ids.length()) : ids;
				ps = ct
						.prepareStatement("select * from(select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from exampaper ep, (select * from exampaperlib where  id in("
								+ ids
								+ ") ) epl where ep.eplid = epl.id  and ep.title like ? order by ep.createtime desc )t  where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			UserDao ud = new UserDaoImpl();
			ELUser user = null;
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9));
				// ep.setBegintime(rs.getDate(10));
				// ep.setEndtime(rs.getDate(11));
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getInt(12));
				user = ud.getUserById(ep.getElUser().getId());
				ep.setElUser(user);
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}

	public int exampaper_list_listEpsByEpIdSize(ExamPaperLib eplTree,
			int eplid, String title, boolean conSub) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (null == title)
				title = "";
			else
				title = title.trim();

			String ids = ExamPaperLibId(eplTree, eplid);
			if (conSub) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
				ps.setInt(1, eplid);
				ExamPaperLib epl = new ExamPaperLib(eplid);
				rs = ps.executeQuery();
				if (rs.next()) {
					epl.setLid(rs.getInt(2));
					epl.setRid(rs.getInt(3));
				}
				rs.close();
				ps = ct
						.prepareStatement("select count(*) from exampaper ep, (select * from exampaperlib where  id in("
								+ ids
								+ ") ) epl where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=?");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, epl.getLid());
				ps.setInt(3, epl.getRid());

			} else {
				ids = eplid == 1 ? ids.substring(2, ids.length()) : ids;
				ps = ct
						.prepareStatement("select count(*) from exampaper ep,  (select * from exampaperlib where  id in("
								+ ids
								+ ") ) epl where ep.eplid = epl.id and ep.title like ? ");
				ps.setString(1, "%" + title + "%");
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void deleteExamPaper(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 删除试卷要做？？？
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from EXAMPAPER where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 根据类型删除试卷
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteExamPaperByTypeid(Connection ct, int typeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		try {// TODO 删除试卷要做？？？
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from EXAMPAPER where eplid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类型删除试卷出错！", e);
			throw new ElException(e);
		}
	}
	/**
	 * 根据类别更新试卷状态
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteExamPaperByTypeidNot(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		 Connection ct = null;
		try {
			 ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update EXAMPAPER set status=1 where eplid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类别更新试卷状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setExamPaperStatus(int id, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update EXAMPAPER set status = ? where id = ?");
			ps.setInt(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void setExamPaperQuestionTotalCount(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call set_epq_totalcount(?)}");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置试卷总题目数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// ---------------------答题管理-------
	public void addExamPaperBlock(ExamPaperBlock examPaperBlock)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int sortid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUERY_MAXSORTID));
			ps.setInt(1, examPaperBlock.getExamPaper().getId());
			rs = ps.executeQuery();
			if (rs.next())
				sortid = rs.getInt(1);
			sortid++;
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_ADD));
			ps.setInt(1, examPaperBlock.getExamPaper().getId());
			ps.setString(2, examPaperBlock.getTitle());
			ps.setString(3, examPaperBlock.getDescription());
			ps.setInt(4, examPaperBlock.getType());
			ps.setInt(5, examPaperBlock.getQuestionamount());
			ps.setFloat(6, examPaperBlock.getEachscore());
			ps.setInt(7, sortid);
			ps.setInt(8, examPaperBlock.getRandom());
			ps.setString(9, examPaperBlock.getRulestring());
			ps.setInt(10, examPaperBlock.getFwsize());
			ps.setInt(11, examPaperBlock.getAnswerTime());
			ps.setDouble(12, examPaperBlock.getSecondScore());
			ps.setInt(13, examPaperBlock.getSortid());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('examPaperBlock') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select examPaperBlock_sequence.currval from dual ");
				rs = ps.executeQuery();
			}
			if (rs.next())
				examPaperBlock.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加试卷大题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExamPaperBlock> listEpBlockByEpid(int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(QuestionConstants.EPBLOCK_QUERY_BYEPID));
			ps.setInt(1, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				int rm = epblockReqalqumunt(epb.getId(), epb
						.getRandom());
				if(epb.getType()==12){
					if(rm>epb.getQuestionamount())
						rm = epb.getQuestionamount();
				}
				epb.setRealqamount(rm);
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getInt(11));
				epb.setFwsize(rs.getInt(12));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("查询大题列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	/**
	 * Description: 获取试卷小题数量
	* @Version1.0 2012-7-30 下午07:04:04 by 闻益舜（wenyishun110@163.com）创建
	 * @param blockid
	 * @param random
	 * @return
	 * @throws ElException
	 */
	private int epblockReqalqumunt(int blockid, int random) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			if (random == 0)
				ps = ct
						.prepareStatement("SELECT COUNT(*) FROM EXAMPAPERBLOCKQUESTION EQ WHERE EQ.BLOCKID= ?");
			else
				ps = ct
						.prepareStatement("SELECT SUM(EPLEVEL1+EPLEVEL2+EPLEVEL3+EPLEVEL4+EPLEVEL5+EPLEVEL) FROM EXAMPAPER_RANDOM EQ WHERE EQ.BLOCKID= ?");
			ps.setInt(1, blockid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询大题列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	// ,(select count(*) from exampaperblockquestion eq where eq.blockid=
	// epb.id) as
	// rqcount
	// 试题数量
	// public List<ExamPaperBlock> listEpBlockByEpidRandom(int epid)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(QuestionConstants.EPBLOCK_RANDOM_QUERY_BYEPID));
	// ps.setInt(1, epid);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
	// epb.setExamPaper(new ExamPaper(rs.getInt(1)));
	// epb.setTitle(rs.getString(2));
	// epb.setDescription(rs.getString(3));
	// epb.setType(rs.getInt(4));
	// epb.setQuestionamount(rs.getInt(5));
	// epb.setEachscore(rs.getInt(6));
	// epb.setSortid(rs.getInt(7));
	// epb.setRealqamount(rs.getInt(9));
	// epb.setRandom(rs.getInt(10));
	// epbs.add(epb);
	// }
	// } catch (Exception e) {
	// logger.error("查询大题列表出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return epbs;
	// }

	public void deleteEpb(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 删除试卷要做？？？
			ct = DBConnection.getConnection();
			int sortid = 0;
			int epid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_SORTID_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				epid = rs.getInt(1);
				sortid = rs.getInt(2);
			}
			rs.close();
			ps.close();
			if (sortid != 0 && epid != 0) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPBLOCK_BIGSORTID_SET));
				ps.setInt(1, epid);
				ps.setInt(2, sortid);
				ps.executeUpdate();
				ps.close();
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除大题试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * public ExamPaperBlock getEpbWithRandomById(int id) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * ExamPaperBlock epb = new ExamPaperBlock(); try { ct =
	 * DBConnection.getConnection(); ps = ct
	 * .prepareStatement(ElQuerySql.getSQL(QuestionConstants.EPBLOCK_EPRANDOM_BYBID));
	 * 
	 * ps.setInt(1, id); rs = ps.executeQuery(); if (rs.next()) { ExamPaper ep =
	 * new ExamPaper(rs.getInt(1), rs.getString(2), rs .getBoolean(3)); epb =
	 * new ExamPaperBlock(rs.getInt(4), rs.getString(5));
	 * epb.setDescription(rs.getString(6)); epb.setType(rs.getInt(7));
	 * epb.setQuestionamount(rs.getInt(8)); epb.setEachscore(rs.getInt(9));
	 * epb.setSortid(rs.getInt(10));
	 * 
	 * ExampaperRandom epr = new ExampaperRandom(); epb.setQlib(new
	 * QuestionLib(rs.getInt(11))); epb.setEplevel1(rs.getInt(12));
	 * epb.setEplevel2(rs.getInt(13)); epb.setEplevel3(rs.getInt(14));
	 * epb.setEplevel4(rs.getInt(15)); epb.setEplevel5(rs.getInt(16));
	 * epb.setEplevel(rs.getInt(17)); epb.setSuboperate(rs.getInt(18));
	 * 
	 * ep.setElUser(new ELUser(rs.getInt(19))); epb.setExamPaper(ep); } } catch
	 * (Exception e) { logger.error("查询大题列表出错！", e); throw new ElException(e); }
	 * finally { DBConnection.closeConnectInfo(ct, ps, rs); } return epb; }
	 */

	public ExamPaperBlock getEpbById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaperBlock epb = new ExamPaperBlock();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				epb = new ExamPaperBlock(rs.getInt(9), rs.getString(2));
				epb.setExamPaper(new ExamPaper(rs.getInt(1), rs.getString(8),
						rs.getInt(10)));
				// epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				// epb.setRealqamount(rs.getInt(11));
				epb.setRandom(rs.getInt(11));
				epb.setRulestring(rs.getString(12));
				int rm =epblockReqalqumunt(epb.getId(), epb
						.getRandom());
				if(epb.getType()==12){//大题为选做题时的实际题目数
					if(rm>epb.getQuestionamount())
						rm = epb.getQuestionamount();
				}
				epb.setRealqamount(rm);
				epb.setFwsize(rs.getInt(13));
				epb.setAnswerTime(rs.getInt(14));
				epb.setSecondScore(rs.getDouble(15));
				epb.setCosPlayRemark(rs.getString(16));
				epb.setReadsort(rs.getInt(17));
				
			}
		} catch (Exception e) {
			logger.error("查询大题列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epb;
	}

	public void alterExamPaperBlock(ExamPaperBlock examPaperBlock)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update exampaperblock set title = ?,description = ?,type = ?, eachscore =  ?,questionamount=?,random=?,rulestring=?,fwsize=?,answertime=?,secondscore=?  ,cosPlayRemark=?,readsort=? where id = ?");
			ps.setString(1, examPaperBlock.getTitle());
			ps.setString(2, examPaperBlock.getDescription());
			// TODO 大题类型更改要做的事
			ps.setInt(3, examPaperBlock.getType());
			ps.setFloat(4, examPaperBlock.getEachscore());
			// ps.setInt(5, examPaperBlock.getSortid());
			ps.setInt(5, examPaperBlock.getQuestionamount());
			ps.setInt(6, examPaperBlock.getRandom());
			ps.setString(7, examPaperBlock.getRulestring());
			ps.setInt(8, examPaperBlock.getFwsize());
			ps.setInt(9, examPaperBlock.getAnswerTime());
			ps.setDouble(10, examPaperBlock.getSecondScore());
			ps.setString(11, examPaperBlock.getCosPlayRemark());
			ps.setInt(12, examPaperBlock.getSortid());
			ps.setInt(13, examPaperBlock.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改试卷大题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Question> listEpBlockQusetionsByBepbId(int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_BYBID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setSortid(rs.getInt(13));
				q.setRulestring(rs.getString(14));
				q.setScore(rs.getFloat(15));
				q.setFwsize(rs.getInt(16));
				q.setVoicePath(rs.getString(17));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("按大题获取其试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}

	public void alterEpBlockQusetionrule(int epbid, int id, String rule)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" update exampaperblockquestion set rulestring = ? where  questionid = ? and blockid = ? ");
			ps.setString(1, rule);
			ps.setInt(2, id);
			ps.setInt(3, epbid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("按大题获取其试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterEpBlockrule(int epbid, String rule) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" update exampaperblock set rulestring = ? where id = ? ");
			ps.setString(1, rule);
			ps.setInt(2, epbid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("按大题获取其试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Question getEpBlockQusetionsByBepbId(int epbid, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Question q = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.sortid,epq.rulestring,q.fwsize from question q "
							+ "left join exampaperblockquestion epq on epq.questionid = q.id left join question_lib qlb on q.qlibid = qlb.id where q.id=?  and epq.blockid = ?  ");
			ps.setInt(1, id);
			ps.setInt(2, epbid);
			rs = ps.executeQuery();
			while (rs.next()) {
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
				q.setSortid(rs.getInt(13));
				q.setRulestring(rs.getString(14));
				q.setFwsize(rs.getInt(15));
			}
		} catch (Exception e) {
			logger.error("按大题获取其试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	/**
	 * 大题中是否包含该试题
	 */
	public boolean haveTheQuestion(int eqbid, int qid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_CHECK));
			ps.setInt(1, eqbid);
			ps.setInt(2, qid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("检测大题是否包含该试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void addEpbQuestion(int eqbid, int qid, float score)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int sortid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_MAXSORTID));
			ps.setInt(1, eqbid);
			rs = ps.executeQuery();
			if (rs.next())
				sortid = rs.getInt(1);
			sortid++;
			// String oldrule = "";
			// int oldscore = 0;
			// ps = ct
			// .prepareStatement("select oldrulestring,oldscore from question
			// where id = ?");
			// ps.setInt(1, qid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// oldrule = rs.getString(1);
			// oldscore = rs.getInt(2);
			// }
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_ADD));
			ps.setInt(1, eqbid);
			ps.setInt(2, qid);
			ps.setFloat(3, score);
			ps.setInt(4, sortid);
			// ps.setString(4, oldrule);
			// ps.setInt(5, oldscore);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加试题到大题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteEpbQuestion(int qid, int epbid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int sortid = 0;
			// TODO 删除试卷试题要做？？？
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_SORTID_BYBQID));
			ps.setInt(1, epbid);
			ps.setInt(2, qid);
			rs = ps.executeQuery();
			if (rs.next())
				sortid = rs.getInt(1);
			rs.close();
			ps.close();
			if (sortid != 0) {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.EPBLOCK_QUESTION_BIGSORTID_SET));
				ps.setInt(1, epbid);
				ps.setInt(2, sortid);
				ps.executeUpdate();
				ps.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPBLOCK_QUESTION_DELETE));
				ps.setInt(1, qid);
				ps.setInt(2, epbid);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("删除大题试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// TODO 这个？？？？？？？？？
	public List<Question> getQChildbyPid(int pid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("SELECT Q.ID, Q.TITLE, Q.CREATETIME,Q.MODIFYTIME,Q.QTYPE ,"
							+ " Q.SCOREPER,Q.MINWORD,Q.SORTID,Q.SUBJECT,Q.CONTENT,Q.ANSWER,Q.QEXPLAIN FROM QUESTION Q WHERE "
							+ "Q.PARENTID= ? ORDER BY Q.SORTID ASC");
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
				q.setSubject(rs.getString(9));
				q.setContent(rs.getString(10));
				q.setAnswer(rs.getString(11));
				q.setQexplain(rs.getString(12));
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

	/*
	 * 检测大题题库是否存在
	 */
	public boolean checkEpbRandom(int qlibid, int blockid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select qlibid,blockid from exampaper_random where blockid=?");
			ps.setInt(1, blockid);
			rs = ps.executeQuery();
			while(rs.next()){
				if(qlibid==rs.getInt(1)){
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("检测大题是否包含该试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void addEpbRandom(ExampaperRandom epb) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_ADD));
			ps.setInt(1, epb.getQlib().getId());
			ps.setInt(2, epb.getEpBlock().getId());
			ps.setInt(3, epb.getQlevel1());
			ps.setInt(4, epb.getQlevel2());
			ps.setInt(5, epb.getQlevel3());
			ps.setInt(6, epb.getQlevel4());
			ps.setInt(7, epb.getQlevel5());
			ps.setInt(8, epb.getQlevel());
			ps.setInt(9, epb.getSuboperate());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置试题大大题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新试卷大题规则
	 * @param epb
	 * @throws ElException
	 */
	public void updateEpbRandom(ExampaperRandom epb) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update exampaper_random set eplevel1=eplevel1+?,eplevel2=eplevel2+?,eplevel3=eplevel3+?,eplevel4=eplevel4+?,eplevel5=eplevel5+?,eplevel=eplevel+? where qlibid=? and blockid=?");
			ps.setInt(1, epb.getQlevel1());
			ps.setInt(2, epb.getQlevel2());
			ps.setInt(3, epb.getQlevel3());
			ps.setInt(4, epb.getQlevel4());
			ps.setInt(5, epb.getQlevel5());
			ps.setInt(6, epb.getQlevel());
			ps.setInt(7, epb.getQlib().getId());
			ps.setInt(8, epb.getEpBlock().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新试卷大题规则出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExampaperRandom> listEpbRandom(int blockid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExampaperRandom> epbs = new ArrayList<ExampaperRandom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_BYBID));
			ps.setInt(1, blockid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// select er.id,er.blockid, epb.title,er.qlibid ,
				// er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,
				// er.suboperate,qlib.name,epb.type
				ExampaperRandom er = new ExampaperRandom(rs.getInt(1));
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(2), rs
						.getString(3));
				er.setQlib(new QuestionLib(rs.getInt(4), rs.getString(12)));
				er.setQlevel1(rs.getInt(5));
				er.setQlevel2(rs.getInt(6));
				er.setQlevel3(rs.getInt(7));
				er.setQlevel4(rs.getInt(8));
				er.setQlevel5(rs.getInt(9));
				er.setQlevel(rs.getInt(10));
				er.setSuboperate(rs.getInt(11));
				er.setEpBlock(epb);
				er.getEpBlock().setType(rs.getInt(13));
				er.setQlevel_(getRandomQSize(er.getQlib().getId(), er
						.getEpBlock().getType(), 0, er.getSuboperate()));
				er.setQlevel1_(getRandomQSize(er.getQlib().getId(), er
						.getEpBlock().getType(), 1, er.getSuboperate()));
				er.setQlevel2_(getRandomQSize(er.getQlib().getId(), er
						.getEpBlock().getType(), 2, er.getSuboperate()));
				er.setQlevel3_(getRandomQSize(er.getQlib().getId(), er
						.getEpBlock().getType(), 3, er.getSuboperate()));
				er.setQlevel4_(getRandomQSize(er.getQlib().getId(), er
						.getEpBlock().getType(), 4, er.getSuboperate()));
				er.setQlevel5_(getRandomQSize(er.getQlib().getId(), er
						.getEpBlock().getType(), 5, er.getSuboperate()));
				epbs.add(er);
			}
			ps.close();
		} catch (Exception e) {
			logger.error("随机试题大题试题列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	public void deleteEpbRandom(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 删除随机试卷要做？？？
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除随机试题大题试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 检测大题试题数量
	 */
	public boolean checkQuestionSize(int blockid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {

			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_SIZE_CHECK));
			ps.setInt(1, blockid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > rs.getInt(2);
			}
		} catch (Exception e) {
			logger.error("删除随机试题大题试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return false;
	}

	public void sortEpbQs(int blockid, int qid, int upordown)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (upordown == ElConstants.SORT_UP)
				upSort(ct, blockid, qid);
			else
				downSort(ct, blockid, qid);
		} catch (Exception e) {
			logger.error("大题试题排序失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void upSort(Connection ct, int blockid, int qid)
			throws ElException, SQLException {
		try {
			Statement ps = ct.createStatement();
			String sql = "select questionid from exampaperblockquestion where blockid = "
					+ blockid + " and sortid = " + (qid - 1);
			ResultSet rs = ps.executeQuery(sql);
			int nextId = 0;
			if (rs.next())
				nextId = rs.getInt(1);
			rs.close();
			if (nextId != 0) {
				sql = "update exampaperblockquestion set sortid=sortid-1 "
						+ " where blockid = " + blockid + " and sortid=" + qid;
				ps.executeUpdate(sql);
				sql = "update exampaperblockquestion set sortid=sortid+1 "
						+ " where blockid= "+blockid+" and questionid = " + nextId;
				ps.executeUpdate(sql);
			}
		} catch (Exception e) {
			logger.error("大题试题排序失败！", e);
			throw new ElException("大题上移", e);
		}
	}

	private void downSort(Connection ct, int blockid, int sortid)
			throws ElException {
		try {
			Statement ps = ct.createStatement();
			String sql = "select max(sortid) from exampaperblockquestion where blockid= "
					+ blockid;
			ResultSet rs = ps.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select questionid from exampaperblockquestion where blockid = "
						+ blockid + " and sortid = " + (sortid + 1);
				rs = ps.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update exampaperblockquestion set sortid=sortid+1 "
							+ " where blockid = " + blockid + " and sortid="
							+ sortid;
					ps.executeUpdate(sql);
					sql = "update exampaperblockquestion set sortid=sortid-1 "
							+ " where questionid = " + nextId;
					ps.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			logger.error("大题试题排序失败！", e);
			throw new ElException("大题下移", e);
		}
	}

	public void sortEpBlock(int epid, int sortid, int upordown)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("");

			if (upordown == ElConstants.SORT_UP)
				upBlockSort(ps, epid, sortid);
			else
				downBlockSort(ps, epid, sortid);
		} catch (Exception e) {
			logger.error("大题排序失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void upBlockSort(PreparedStatement ps, int epid, int sortid)
			throws ElException {
		try {
			if (sortid > 0) {
				String sql = "select id from exampaperblock where exampaperid = "
						+ epid + " and sortid = " + (sortid - 1);
				ResultSet rs = ps.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update exampaperblock set sortid=sortid-1 "
							+ " where exampaperid = " + epid + " and sortid="
							+ sortid;
					ps.executeUpdate(sql);
					sql = "update exampaperblock set sortid=sortid+1 "
							+ " where id = " + nextId;
					ps.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			logger.error("大题排序失败！", e);
			throw new ElException("大题上移", e);
		}
	}

	private void downBlockSort(PreparedStatement ps, int epid, int sortid)
			throws ElException {
		try {
			String sql = "select max(sortid) from exampaperblock where exampaperid= "
					+ epid;
			ResultSet rs = ps.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from exampaperblock where exampaperid = "
						+ epid + " and sortid = " + (sortid + 1);
				rs = ps.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update exampaperblock set sortid=sortid+1 "
							+ " where exampaperid = " + epid + " and sortid="
							+ sortid;
					ps.executeUpdate(sql);
					sql = "update exampaperblock set sortid=sortid-1 "
							+ " where id = " + nextId;
					ps.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			logger.error("大题排序失败！", e);
			throw new ElException("大题下移", e);
		}
	}

	private int getRandomQSize(int qlib, int type, int level, int subop)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			String levelS = "";
			if (level != 0)
				levelS = level + "";
			if (subop == ElConstants.SUBOP_YES) {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_QLIBLRID_BYID));
				ps.setInt(1, qlib);
				QuestionLib qb = new QuestionLib(qlib);
				rs = ps.executeQuery();
				if (rs.next()) {
					qb.setLid(rs.getInt(2));
					qb.setRid(rs.getInt(3));
				}
				ps.close();
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_SUB_SIZE));
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qb.getLid());
				ps.setInt(4, qb.getRid());
			} else {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_SIZE));
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qlib);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
			ps.close();
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	private int getRandomQSize(int qlib, int type, int level, int subop,
			int fwsize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			String levelS = "";
			if (level != 0)
				levelS = level + "";
			if (subop == ElConstants.SUBOP_YES) {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_QLIBLRID_BYID));
				ps.setInt(1, qlib);
				QuestionLib qb = new QuestionLib(qlib);
				rs = ps.executeQuery();
				if (rs.next()) {
					qb.setLid(rs.getInt(2));
					qb.setRid(rs.getInt(3));
				}
				ps.close();
				ps = ct
						.prepareStatement("select count(*) from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid >=? and qlib.rid<=? and q.parentid= 0  and q.status != 1 and q.fwsize>=?");
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qb.getLid());
				ps.setInt(4, qb.getRid());
				ps.setInt(5, fwsize);
			} else {
				ps = ct
						.prepareStatement("select count(*) from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.id=? and q.parentid= 0 and q.status != 1 and q.fwsize>?");
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qlib);
				ps.setInt(4, fwsize);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
			ps.close();
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	/**
	 * 按照 题库，试题类型 难度级别，包含下级，返回数量到题库中获取试题
	 * 
	 * @param qlib
	 * @param type
	 * @param level
	 * @param subop
	 * @param size
	 * @param sortid
	 * @return
	 * @throws ElException
	 */
	private List<Question> getRandomQ(int qlib, int type, int level, int subop,
			int size, int sortid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			String levelS = "";
			if (level != 0)
				levelS = level + "";
			if (subop == ElConstants.SUBOP_YES) {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_QLIBLRID_BYID));
				ps.setInt(1, qlib);
				QuestionLib qb = new QuestionLib(qlib);
				rs = ps.executeQuery();
				if (rs.next()) {
					qb.setLid(rs.getInt(2));
					qb.setRid(rs.getInt(3));
				}
				ps.close();
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_SUB));
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qb.getLid());
				ps.setInt(4, qb.getRid());
				ps.setInt(5, size);
				ps.setInt(6, 0);

			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM));
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qlib);
				ps.setInt(4, size);
				ps.setInt(5, 0);

			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setSortid(sortid + 1);
				qs.add(q);
				sortid++;

			}
			ps.close();
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}
	/**
	 * 按照 题库，试题类型 难度级别，包含下级，返回数量到题库中获取试题（打字题范文长度现在）
	 * 
	 * @param qlib
	 * @param type
	 * @param level
	 * @param subop
	 * @param size
	 * @param sortid
	 * @return
	 * @throws ElException
	 */
	private List<Question> getRandomQ(int qlib, int type, int level, int subop,
			int size, int sortid,int fwsize ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> qs = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			String levelS = "";
			if (level != 0)
				levelS = level + "";
			if (subop == ElConstants.SUBOP_YES) {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_QLIBLRID_BYID));
				ps.setInt(1, qlib);
				QuestionLib qb = new QuestionLib(qlib);
				rs = ps.executeQuery();
				if (rs.next()) {
					qb.setLid(rs.getInt(2));
					qb.setRid(rs.getInt(3));
				}
				ps.close();
				ps = null;
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement("select * from (select t.*,rownum rn from(select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlib.name from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid >=? and qlib.rid<=? and q.parentid= 0 and q.status != 1 and q.fwsize>=? order by dbms_random.value()) t where rownum<=?) where rn>=?");
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qb.getLid());
				ps.setInt(4, qb.getRid());
				ps.setInt(5, fwsize);
				ps.setInt(6, size);
				ps.setInt(7, 0);

			} else {
				ps = ct.prepareStatement("select * from( select t.* ,rownum rn from (select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlib.name from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.id=? and q.parentid= 0 and q.status != 1 and q.fwsize>=? order by dbms_random.value()) t where rownum<=?) where rn>=?");
				ps.setInt(1, type);
				ps.setString(2, "%" + levelS + "%");
				ps.setInt(3, qlib);
				ps.setInt(4, fwsize);
				ps.setInt(5, size);
				ps.setInt(6, 0);

			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setSortid(sortid + 1);
				qs.add(q);
				sortid++;

			}
			ps.close();
			ps = null;
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}
	public ExampaperRandom getEPRandomById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExampaperRandom er = new ExampaperRandom();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				// select er.id,er.blockid, epb.title,er.qlibid ,
				// er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,
				// er.suboperate,qlib.name,epb.type
				er = new ExampaperRandom(rs.getInt(1));
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(2), rs
						.getString(3));
				er.setQlib(new QuestionLib(rs.getInt(4), rs.getString(12)));
				er.setQlevel1(rs.getInt(5));
				er.setQlevel2(rs.getInt(6));
				er.setQlevel3(rs.getInt(7));
				er.setQlevel4(rs.getInt(8));
				er.setQlevel5(rs.getInt(9));
				er.setQlevel(rs.getInt(10));
				er.setSuboperate(rs.getInt(11));
				er.setEpBlock(epb);
				er.getEpBlock().setType(rs.getInt(13));
			}
		} catch (Exception e) {
			logger.error("随机试题大题试题列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}

	public void alterEpbRandom(ExampaperRandom epb) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EPBLOCK_QUESTION_RANDOM_ALTER));
			ps.setInt(1, epb.getQlevel1());
			ps.setInt(2, epb.getQlevel2());
			ps.setInt(3, epb.getQlevel3());
			ps.setInt(4, epb.getQlevel4());
			ps.setInt(5, epb.getQlevel5());
			ps.setInt(6, epb.getQlevel());
			ps.setInt(7, epb.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置试题大大题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyExamPaper> listEprquiz(int epid, int pN, int pS)
			throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement(" select sqi.id ,sqi.userid, sqi.roomid,
			// sqi.epid, sqi.status,
			// sqi.myScore,sqi.endtime,eu.realname,er.title from
			// study_quizinfo sqi left join ELUSER eu on sqi.userid = eu.id
			// left join exam_room er on sqi.roomid = er.id where sqi.epid = ?
			// limit ?,?");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.MYEXAMPAPER_QUIZ));
			ps.setInt(1, epid);
			ps.setInt(2, pN);
			ps.setInt(3, pS);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2), rs.getString(8)));
				mep.setExamRoom(new ExamRoom(rs.getInt(3), rs.getString(9)));
				mep.setExamPaper(new ExamPaper(rs.getInt(4)));
				mep.setStatus(rs.getInt(5));
				mep.setMyScore(rs.getInt(6));
				// mep.setEndtime(rs.getDate(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public int listEprquizSize(int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(*) from  study_quizinfo sqi left join ELUSER eu on sqi.userid = eu.id left join exam_room er on sqi.roomid = er.id where sqi.epid = ?  ");
			ps.setInt(1, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void addOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into exampaperlib_" + type
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
			ps = ct.prepareStatement("select * from exampaperlib_" + type
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
			ps = ct.prepareStatement("delete from exampaperlib_" + type
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
					.prepareStatement("select eu.id,eu.realname,eu.username from exampaperlib_"
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

	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String ExamPaperLibId(ExamPaperLib ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = getCourseTypeById(ctypeTree.getChild(), ctid);
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
	private String createTypeId(List<ExamPaperLib> listType, int id) {
		String ids = id + "";
		for (ExamPaperLib type : listType) {
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
	private ExamPaperLib getCourseTypeById(List<ExamPaperLib> listType, int ctid) {
		ExamPaperLib courseType = null;
		for (ExamPaperLib type : listType) {
			if (type.getId() != ctid) {
				courseType = getCourseTypeById(type.getChild(), ctid);
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

	public ExamPaper getEPAllInfoById(int id, int roomId) throws ElException {
		ExamPaper examPaper = getExamPaperById(id, roomId);
		List<ExamPaperBlock> epblocks = listEpBlockByEpid(id);
		for (int i = 0; i < epblocks.size(); i++) {
			setQu(epblocks.get(i));
		}
		examPaper.setEpBlocks(epblocks);
		return examPaper;
	}

	public ExamPaper getExamPaperById(int id, int roomId) throws ElException {
		// TODO Auto-generated method stub
		int stuview = getRoomId(roomId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();

			// int roomId=

			// select a.stuview from exam_reps a inner join exam_room b on
			// a.roomid=b.id inner join examPaper c on a.epid=c.id where
			// a.roomid=31;
			if (rs.next()) {
				// ep.id,ep.title,ep.description,ep.userid,ep.eplid,
				// ep.israndom,ep.during,
				// ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9));
				// ep.setBegintime(rs.getDate(10));
				// ep.setEndtime(rs.getDate(11));
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getInt(12));
				ep.setEp_realscore(rs.getFloat(13));
				ep.setQueryurl(rs.getString(14));
				ep.setStuview(stuview);
			}
		} catch (Exception e) {
			logger.error("获取试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public int getRoomId(int roomId) throws ElException {
		// TODO Auto-generated method stub
		int stuview = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select a.stuview from exam_reps a inner join exam_room b on a.roomid=b.id inner join examPaper c on a.epid=c.id where a.roomid=?");
			ps.setInt(1, roomId);
			rs = ps.executeQuery();

			// int roomId=
			// select a.stuview from exam_reps a inner join exam_room b on
			// a.roomid=b.id inner join examPaper c on a.epid=c.id where
			// a.roomid=31;
			if (rs.next()) {
				stuview = rs.getInt("stuview");
			}
		} catch (Exception e) {
			logger.error("获取试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuview;
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
					.prepareStatement(" delete from exampaperlib_op_user where userid= ?");
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
					.prepareStatement(" delete from exampaperlib_use_user where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 原来的
	// public List<ExamPaper> listEpsByEplId(int eplid, ExamPaper examPaper,
	// boolean conSub, int pageNow, int pageSize) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<ExamPaper> eps = new ArrayList<ExamPaper>();
	// String LidRid = "";
	// String conditions = "";
	// try {
	// ct = DBConnection.getConnection();
	// if (conSub) {
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
	// ps.setInt(1, eplid);
	// ExamPaperLib epl = new ExamPaperLib(eplid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// epl.setLid(rs.getInt(2));
	// epl.setRid(rs.getInt(3));
	// }
	// rs.close();
	// LidRid = " and epl.lid >= " + epl.getLid() + " and epl.rid<="
	// + epl.getRid();
	// }
	// if (examPaper != null) {
	// if (examPaper.getTitle() != null
	// && !examPaper.getTitle().equals("")) {
	// conditions = conditions + " and ep.title like '%"
	// + examPaper.getTitle() + "%'";
	// }
	// if (examPaper.getElUser() != null
	// && examPaper.getElUser().getRealname() != null
	// && !examPaper.getElUser().getRealname().equals("")) {
	// conditions = conditions + " and el.realname like '%"
	// + examPaper.getElUser().getRealname() + "%'";
	// }
	// if (examPaper.getCreatetime() != null
	// && !examPaper.getCreatetime().equals("")) {
	// conditions = conditions +" and ep.createtime >= to_date('"+ new
	// SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss").format(examPaper.getCreatetime())+ "','yyyy-MM-dd
	// HH24:mi:ss')";
	// }
	// if (examPaper.getCreatetimeEnd() != null
	// && !examPaper.getCreatetimeEnd().equals("")) {
	// conditions = conditions +" and ep.createtime <= to_date('"+ new
	// SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss").format(examPaper.getCreatetimeEnd())+ "','yyyy-MM-dd
	// HH24:mi:ss')";
	// }
	// }
	// String sql = "select * from (select t.*,rownum rn from (select
	// ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname,ep.status
	// from eluser el, exampaper ep, exampaperlib epl where el.id=ep.userid and
	// ep.eplid = epl.id "
	// + ""
	// + conditions
	// + LidRid
	// + " order by ep.createtime desc )t where rownum <=? ) where rn >=?";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, pageNow);
	// ps.setInt(2, pageSize);
	// rs = ps.executeQuery();
	// ELUser user = null;
	// int userid = 1;
	// UserDao ud = new UserDaoImpl();
	// while (rs.next()) {
	// ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
	// ep.setDescription(rs.getString(3));
	// ep.setElUser(new ELUser(rs.getInt(4)));
	// ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
	// ep.setShowmod(rs.getInt(6));
	// ep.setDuring(rs.getInt(7));
	// ep.setModifytime(rs.getTimestamp(8));
	// ep.setCreatetime(rs.getTimestamp(9));
	// // ep.setBegintime(rs.getDate(10));
	// // ep.setEndtime(rs.getDate(11));
	// ep.setOpentimelimit(rs.getBoolean(10));
	// ep.setEp_tscore(rs.getInt(12));
	// ep.setStatus(rs.getInt(14));
	// user = new ELUser();
	// userid = rs.getInt("userid");
	// user = ud.getUserById(userid);
	// // user.setRealname(rs.getString("realname"));
	// ep.setElUser(user);
	// eps.add(ep);
	// }
	// } catch (Exception e) {
	// logger.error("检测试卷标题出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return eps;
	// }
	public List<ExamPaper> listEpsByEplId(ElNode dep, int subdep,
			ExamPaper examPaper, int pageNow, int pageSize,int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		String conditions = "";
		boolean conSub = subdep == 1 ? true : false;
		try {
			ct = DBConnection.getConnection();
			if (examPaper != null) {
				if (examPaper.getTitle() != null
						&& !examPaper.getTitle().equals("")) {
					conditions = conditions + " and ep.title like '%"
							+ examPaper.getTitle() + "%'";
				}
				if (examPaper.getElUser() != null
						&& examPaper.getElUser().getRealname() != null
						&& !examPaper.getElUser().getRealname().equals("")) {
					conditions = conditions + " and el.realname like '%"
							+ examPaper.getElUser().getRealname() + "%'";
				}
				if (examPaper.getCreatetime() != null) {
					conditions = conditions
							+ " and ep.createtime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examPaper.getCreatetime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examPaper.getCreatetimeEnd() != null) {
					conditions = conditions
							+ " and ep.createtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examPaper.getCreatetimeEnd())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}
			String sql = "select * from (select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,el.realname,ep.status,ep.isEditor from eluser el, exampaper ep,("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("exampaperlib", dep, conSub)
					+ ") epl where el.id=ep.userid and ep.eplid = epl.id";
					//如果状态等于0 就把已创建全部查出来
					if(status == 0){
						sql += " and ep.status = 0";
					}else{
						if(examPaper != null && examPaper.getStatus() != -1){
							sql += " and ep.status = " + examPaper.getStatus();
						}
					}
					sql+= conditions
					+ " order by ep.status desc,ep.createtime desc )t  where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			int userid = 1;
			UserDao ud = new UserDaoImpl();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9));
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getInt(12));
				ep.setStatus(rs.getInt(14));
				ep.setIsEditor(rs.getInt(15));
				userid = rs.getInt("userid");
				user = ud.getUserById(userid);
				ep.setElUser(user);
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}

	// 原来的
	// public int listEpsByEpIdSize(int eplid, ExamPaper examPaper, boolean
	// conSub)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// String LidRid = "";
	// String conditions = "";
	// try {
	// ct = DBConnection.getConnection();
	// if (conSub) {
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(QuestionConstants.EPLIB_QUERYLRID_BYIDANDUID));
	// ps.setInt(1, eplid);
	// ExamPaperLib epl = new ExamPaperLib(eplid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// epl.setLid(rs.getInt(2));
	// epl.setRid(rs.getInt(3));
	// }
	// rs.close();
	// LidRid = " and epl.lid >= " + epl.getLid() + " and epl.rid<="
	// + epl.getRid();
	// }
	// if (examPaper != null) {
	// if (examPaper.getTitle() != null
	// && !examPaper.getTitle().equals("")) {
	// conditions = conditions + " and ep.title like '%"
	// + examPaper.getTitle() + "%'";
	// }
	// if (examPaper.getElUser() != null
	// && examPaper.getElUser().getRealname() != null
	// && !examPaper.getElUser().getRealname().equals("")) {
	// conditions = conditions + " and el.realname like '%"
	// + examPaper.getElUser().getRealname() + "%'";
	// }
	// if (examPaper.getCreatetime() != null
	// && !examPaper.getCreatetime().equals("")) {
	// conditions = conditions +" and ep.createtime >= to_date('"+ new
	// SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss").format(examPaper.getCreatetime())+ "','yyyy-MM-dd
	// HH24:mi:ss')";
	// }
	// if (examPaper.getCreatetimeEnd() != null
	// && !examPaper.getCreatetimeEnd().equals("")) {
	// conditions = conditions +" and ep.createtime <= to_date('"+ new
	// SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss").format(examPaper.getCreatetimeEnd())+ "','yyyy-MM-dd
	// HH24:mi:ss')";
	// }
	// }
	// String sql = "select count(*) from eluser el, exampaper ep, exampaperlib
	// epl where el.id=ep.userid and ep.eplid = epl.id "
	// + "" + conditions + LidRid;
	// ps = ct.prepareStatement(sql);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("检测试卷标题出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	public int listEpsByEpIdSize(ElNode dep, int subdep, ExamPaper examPaper,int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String LidRid = "";
		String conditions = "";
		boolean conSub = subdep == 1 ? true : false;
		try {
			ct = DBConnection.getConnection();
			if (examPaper != null) {
				if (examPaper.getTitle() != null
						&& !examPaper.getTitle().equals("")) {
					conditions = conditions + " and ep.title like '%"
							+ examPaper.getTitle() + "%'";
				}
				if (examPaper.getElUser() != null
						&& examPaper.getElUser().getRealname() != null
						&& !examPaper.getElUser().getRealname().equals("")) {
					conditions = conditions + " and el.realname like '%"
							+ examPaper.getElUser().getRealname() + "%'";
				}
				if (examPaper.getCreatetime() != null) {
					conditions = conditions
							+ " and ep.createtime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examPaper.getCreatetime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examPaper.getCreatetimeEnd() != null) {
					conditions = conditions
							+ " and ep.createtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examPaper.getCreatetimeEnd())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}
			String sql = "select count(*) from eluser el, exampaper ep, ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("exampaperlib", dep, conSub)
					+ ") epl where el.id=ep.userid and ep.eplid = epl.id ";
					//如果状态等于0 就把已创建全部查出来
					if(status == 0){
						sql += " and ep.status = 0";
					}else{
						if(examPaper != null && examPaper.getStatus() != -1){
							sql += " and ep.status = " + examPaper.getStatus();
						}
					}
					sql+= ""
					+ conditions + LidRid;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检测试卷标题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取考场所有的试卷
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> listEroomExamPaper(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid,status from exam_reps where roomid=? ");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			ExamPaper ep = null;
			while (rs.next()) {
				ep = new ExamPaper(rs.getInt(1));
				ep.setStatus(rs.getInt(2));
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("获取学员分配的所有试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}
	
	/**
	 * 获取考场中用户分配的所有试卷
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> listEroomExamPaper(int roomid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid from study_exampaper where roomid=? and userid=? ");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			ExamPaper ep = null;
			while (rs.next()) {
				ep = new ExamPaper(rs.getInt(1));
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("获取考场中用户分配的所有试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}
	
	/**
	 * 判断试卷的评分规则
	 */
	public String checkExampaper(int id) throws ElException {
		// 将 检测信息存到String 中返回，若试卷校验 完整无问题返回 yes;不完整时 记录下面的错误信息
		// 获取试卷基本信息
		ExamPaper examPaper = getExamPaperById(id);
//		ExamPaperUtil examPaperUtil = new ExamPaperUtil();
		StringBuffer msg =new StringBuffer();
		// 获取大题列表(计算大题总分)
		float sum = 0f;
		float realscore = examPaper.getEp_tscore();
		List<ExamPaperBlock> epblocks = listEpBlockByEpid(id);
		for (int i = 0; i < epblocks.size(); i++) {
			ExamPaperBlock e = epblocks.get(i);
			if(e.getType()==12){
				// 手工类型（选做题）校验评分规则
				//校验评分规则
				if(!ExamPaperUtil.checkXuanzuo(e.getRulestring())){
					msg.append("<b>"+e.getTitle()+"</b>选做题评分规则不符合要求，请重新输入评分规则! <br/>");
				}
				//评分规则
				int rule = Integer.parseInt(e.getRules()[0]);
				//实际数
//				int count = epblockReqalqumunt(e.getId(), 0);
				
//				if(rule != count){
//					msg.append("<b>"+e.getTitle()+"</b>选做题实际题目数量<b>("+ count +")</b>与评分规则中设定数量(<b>"+ rule +"</b>)不一致，请确认并修正! <br/>");
//				}
				if(e.getQuestionamount()>e.getRealqamount()){
//					count = e.getQuestionamount();
					rule = e.getQuestionamount();
					msg.append("<b>"+e.getTitle()+"</b>选做题实际题目数量<b>("+ e.getRealqamount() +")</b>小于大题设定选做题数量(<b>"+ rule +"</b>)，请确认并修正! <br/>");
				}
//				sum += e.getRealqamount()*(e.getEachscore()*1000)/1000.0f;
			}else{
				if (e.getRandom() == 0) {
					
					// 获取小题列表 大题设置数量和小题实际数量比较
					List<Question> qs = listEpBlockQusetionsByBepbId(e.getId());
					int cha=e.getQuestionamount()-qs.size() ;
					if(cha>0){
						msg.append("<b>"+e.getTitle()+"</b>还有"+cha+"道小题没添加，请添加！<br/>");
					}else if(cha<0){
						msg.append("<b>"+e.getTitle()+"</b>有"+cha+"道小题是多添加，请删除！<br/>");
					}
					//（邮件，打字）校验各小题评分规则
					for (int j = 0; j < qs.size(); j++) {
						if(qs.get(j).getQtype()==8){//打字题
							//检验评分规则
							//1-=SpRule-1-=SpRule-1-=SpRule-0:100:33:44:
							String rulej = qs.get(j).getRulestring();
							if(rulej!=null){
								if(!ExamPaperUtil.checkDazi(rulej)){
									msg.append("<b>"+e.getTitle()+qs.get(j).getTitle()+"</b>打字题评分规则不符合要求，请重新输入评分规则! <br/>");
								}else{
//									float count =0;
//									for(int a = 0; a < 2;a++){
//										count += Float.parseFloat(qs.get(j).getRules()[a]);
//									}
//									if(count != e.getEachscore()){
//										msg.append("<b>"+e.getTitle()+qs.get(j).getTitle()+"</b>打字题题的实际分值不正确，请重新输入评分规则! <br/>");
//									}
								}
							}else{
								msg.append("<b>"+e.getTitle()+qs.get(j).getTitle()+"</b>打字题评分规则不能为空，请重新输入评分规则! <br/>");
							}
						}
						//邮件题
						if(qs.get(j).getQtype()==9){
							//检验评分规则
							//1-=SpRule-1-=SpRule-1-=SpRule-1-=SpRule-1-=SpRule-1-=SpRule-
							String rulej = qs.get(j).getRulestring();
							if(rulej!=null){
								if(!ExamPaperUtil.checkEmail(rulej)){
									msg.append("<b>"+e.getTitle()+qs.get(j).getTitle()+"</b>邮件题评分规则不符合要求，请重新输入评分规则! <br/>");
								}else{
									float count =0;
									for(int a = 0; a < qs.get(j).getRules().length;a++){
										count += Float.parseFloat(qs.get(j).getRules()[a]);
									}
									if(count != e.getEachscore()){
										msg.append("<b>"+e.getTitle()+qs.get(j).getTitle()+"</b>邮件题的实际分值不正确，请重新输入评分规则! <br/>");
									}
								}
							}else{
								msg.append("<b>"+e.getTitle()+qs.get(j).getTitle()+"</b>邮件题评分规则不能为空，请重新输入评分规则! <br/>");
							}
						}
					}
				} else {
					// 随机类型（邮件，打字，选作题）校验评分规则合法性
					if(e.getType()==8){//打字题
						//检验评分规则
						//1-=SpRule-1-=SpRule-1-=SpRule-0:100:33:44:
						//+代表1到N个 (\\d*)\\d代表数字 *代表0到N多个
						String rulej = e.getRulestring();
						if(rulej!=null){
							if(!ExamPaperUtil.checkDazi(rulej)){
								msg.append("<b>"+e.getTitle()+"</b>打字题评分规则不符合要求，请重新输入评分规则! <br/>");
							}else{
//								float count =0;
//								for(int a = 0; a < 2;a++){
//									count += Float.parseFloat(e.getRules()[a]);
//								}
//								if(count != e.getEachscore()){
//									msg.append("<b>"+e.getTitle()+"</b>打字题题的实际分值不正确，请重新输入评分规则! <br/>");
//								}
							}
						}else{
							msg.append("<b>"+e.getTitle()+"</b>打字题评分规则不能为空，请重新输入评分规则! <br/>");
						}
					}
					//邮件题
					if(e.getType()==9){
						//检验评分规则
						//1-=SpRule-1-=SpRule-1-=SpRule-1-=SpRule-1-=SpRule-1-=SpRule-
						String rulej = e.getRulestring();
						if(rulej!=null){
							if(!ExamPaperUtil.checkEmail(rulej)){
								msg.append("<b>"+e.getTitle()+"</b>邮件题评分规则不符合要求，请重新输入评分规则! <br/>");
							}else{
								float count =0;
								for(int a = 0; a < e.getRules().length;a++){
									count += Float.parseFloat(e.getRules()[a]);
								}
								if(count != e.getEachscore()){
									msg.append("<b>"+e.getTitle()+"</b>邮件题的实际分值不正确，请重新输入评分规则! <br/>");
								}
							}
						}else{
							msg.append("<b>"+e.getTitle()+"</b>邮件题评分规则不能为空，请重新输入评分规则! <br/>");
						}
					}
					// 获取出题规则（计算个小题总分=大题每题分值*出题规则(是个列表)中的小题数量(难度级别1,2,3,4,5,不限 的数量)）
					List<ExampaperRandom> eprs = listEpbRandom(e.getId());
					int real = 0;
					for(int z = 0;z<eprs.size();z++){
						ExampaperRandom random = eprs.get(z);
						real += random.getQlevel() + random.getQlevel1() + random.getQlevel2() + + random.getQlevel3() + random.getQlevel4() + random.getQlevel5();
					}
					int cha=e.getQuestionamount()-real ;
					if(cha>0){
						msg.append("<b>"+e.getTitle()+"还有"+cha+"道小题没设置，请设置！<br/>");
					}else if(cha<0){
						msg.append("<b>"+e.getTitle()+"有"+cha+"道小题设置多了，请更改！<br/>");
					}
				}
//				sum += e.getQuestionamount() * (e.getEachscore()*1000)/1000.0f;
			}
		}
		
		if(realscore != examPaper.getEp_tscore()){
			msg.append("<b>"+examPaper.getTitle()+"</b>的实际分值与试题总分不一致，请更改！<br/>");
		}
		if("".equals(msg.toString())){
			msg.append("yes");
		}
		return msg.toString();
	}

	public int copyExampaper(int id) throws ElException {
		// 获取试卷基本信息
		ExamPaper examPaper = getExamPaperById(id);
		examPaper.setTitle(examPaper.getTitle() + "_副本");
		//添加试卷
		addExamPaper(examPaper);
		// 获取大题列表(计算大题总分)
		List<ExamPaperBlock> epblocks = listEpBlockByEpid(id);
		for (int i = 0; i < epblocks.size(); i++) {
			ExamPaperBlock e = epblocks.get(i);
			int oldid = e.getId();
			//设置大题是哪张试卷
			e.setExamPaper(examPaper);
			//添加大题
			addExamPaperBlock(e);
			//手工
			if (e.getRandom() == 0) {
				// 获取小题列表 大题设置数量和小题实际数量比较
				List<Question> qs = listEpBlockQusetionsByBepbId(oldid);
				for(int j = 0; j < qs.size(); j++){
					//添加小题
					Question q = qs.get(j);
					addEpbQuestion(e.getId(), q.getId(), q.getScore());
					//设定评分规则
					alterEpBlockQusetionrule(e.getId(), q.getId(), q.getRulestring());
				}
			}else{
				//随机
				// 获取出题规则（计算个小题总分=大题每题分值*出题规则(是个列表)中的小题数量(难度级别1,2,3,4,5,不限 的数量)）
				List<ExampaperRandom> eprs = listEpbRandom(oldid);
				for(int j = 0; j < eprs.size(); j++){
					//这个规则是属于哪个大题
					eprs.get(j).setEpBlock(e);
					//设置出题规则
					addEpbRandom(eprs.get(j));
				}
			}
		}
		return examPaper.getId();
	}
	/**
	 * 设置试卷为不可编辑
	 * @param epid
	 * @throws ElException
	 */
	public void updateExampaperIseditor(int epid) throws ElException{
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement("update exampaper set iseditor=1 where id=?");
			ps.setInt(1, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("设置试卷为不可编辑出错！",e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ExamPaper getQuestionScoreAndRealScore(int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sum(t1.setscore)epsetscore,sum(t1.realscore) eprealscore from( select epb.questionamount*epb.eachscore setscore,epb.eachscore*t.realcount realscore from exampaperblock epb left join (" +
					"(select sum(eplevel1+eplevel2+eplevel3+eplevel4+eplevel5+eplevel) realcount,blockid from exampaper_random group by blockid) union(select count(*) realcount,blockid from exampaperblockquestion group by blockid)) t on t.blockid = epb.id where epb.exampaperid=?)t1");
			ps.setInt(1, epid);
			rs=ps.executeQuery();
			if(rs.next()){
				ep.setEp_questionscore(rs.getFloat(1));
				ep.setEp_realscore(rs.getFloat(2));
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}
	/**
	 * 根据考场id获取到所有该考场试卷
	 * @param roomId
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> getExamPaperByRoomId(int roomId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> epList = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ep.id,ep.title,ep.description,ep.userid,ep.eplid, ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit," +
					" epl.name,ep.ep_tscore,ep.ep_realscore,ep.queryurl from exampaper ep left join exampaperlib epl on ep.eplid = epl.id " +
					"  left join exam_reps er  on ep.id = er.epid  where er.roomid = ?");
			ps.setInt(1, roomId);
			rs = ps.executeQuery(); 
			while (rs.next()) { 
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setEpl(new ExamPaperLib(rs.getInt(5), rs.getString(11)));
				ep.setShowmod(rs.getInt(6));
				ep.setDuring(rs.getInt(7));
				ep.setModifytime(rs.getTimestamp(8));
				ep.setCreatetime(rs.getTimestamp(9)); 
				ep.setOpentimelimit(rs.getBoolean(10));
				ep.setEp_tscore(rs.getInt(12));
				ep.setEp_realscore(rs.getFloat(13));
				ep.setQueryurl(rs.getString(14));
				epList.add(ep);
			}
		} catch (Exception e) {
			logger.error("获取考场试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs); 
		}
		return epList;
	}

	public float getMyEpBlocksScore(int exampaperid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		float ff = 0.00f;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call getMyEpBlocksScore(?,?)}");  
			cs.setInt(1, exampaperid);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.FLOAT);  
			cs.execute(); 
			ff = cs.getFloat(2);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ff;
	}

	public boolean checkMyQuestionIsGetScore(int qid, int blockid, int sqid,
			int qindex) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call checkMyQuestionIsGetScore(?,?,?,?,?)}");  
			cs.setInt(1, qid);
			cs.setInt(2, blockid);
			cs.setInt(3, sqid);
			cs.setInt(4, qindex);
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getBoolean(5));
			flag = cs.getBoolean(5);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public int getWrongQuesSizeByBlockid(int myexampaperid, int blockid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(1) from exampaperblock ebq,study_questions sq " +
					"	where ebq.id=sq.blockid and sq.sqid=? and sq.blockid=? and sq.status=0");
			ps.setInt(1, myexampaperid);
			ps.setInt(2, blockid);
			rs = ps.executeQuery(); 
			if (rs.next()) { 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据试卷ID和blockid获取答错题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs); 
		}
		return size;
	}

	@Override
	public void addPaperData(int examId,String questions)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into NEW_STU_EXAM(exam_id,questions)values(?,?)");
			ps.setInt(1, examId);
			ps.setString(2, questions);
			rs = ps.executeQuery(); 
			
		} catch (Exception e) {
			logger.error("根据试卷ID和blockid获取答错题数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs); 
		}
	}

	@Override
	public List<ExamPaperBlock> listEpBlockByEpidAndType(int epid, int type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String where = " AND EPB.DESCRIPTION ='"+type+"'  ORDER BY EPB.readsort ASC";;
		
		String sql = "SELECT EPB.EXAMPAPERID,EPB.TITLE,EPB.DESCRIPTION,EPB.TYPE,EPB.QUESTIONAMOUNT,EPB.EACHSCORE,EPB.SORTID,EPB.ID,EPB.RANDOM,EPB.RULESTRING,EPB.REALSCORE,EPB.FWSIZE FROM EXAMPAPERBLOCK EPB WHERE EPB.EXAMPAPERID=? "+where;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				int rm = epblockReqalqumunt(epb.getId(), epb
						.getRandom());
				if(epb.getType()==12){
					if(rm>epb.getQuestionamount())
						rm = epb.getQuestionamount();
				}
				epb.setRealqamount(rm);
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getInt(11));
				epb.setFwsize(rs.getInt(12));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("查询大题列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}
}
