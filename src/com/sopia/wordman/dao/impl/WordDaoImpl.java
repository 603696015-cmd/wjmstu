package com.sopia.wordman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Typelrid;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.wordman.dao.WordDao;
import com.sopia.wordman.entities.Vocabulary;
import com.sopia.wordman.entities.Word;

public class WordDaoImpl extends ElNodeDao implements WordDao{
	private static final Log logger = LogFactory.getLog(WordDaoImpl.class);

	public Word getWordsTree(int from, int stop, boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Word cltype = null;
		try {
			if (from == 0) {
				cltype = getWordsRoot();
			} else {
				cltype = getWordsById(from);
			}
			ct = DBConnection.getConnection();
			cltype
					.setChild(getChilds(ct, cltype.getId(), stop, containStop,
							0));
		} catch (Exception e) {
			logger.error("词汇类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	
	public Word getWordsRoot() throws ElException {
		Word word  =new Word();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.WORDLIB_QUERY_BYPID));
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			while (rs.next()) {
				word.setId(rs.getInt(1));
				word.setName(rs.getString(2));
				word.setParent(new Word(rs.getInt(3)));
				word.setLid(rs.getInt(4));
				word.setRid(rs.getInt(5));
			}
		} catch (Exception e) {
			logger.error("获取词汇类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return word;
	}
	
	public Word getWordsById(int id) throws ElException {
		Word word = new Word();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ct1.id,ct1.name,ct1.description,ct1.parentid,ct2.name,ct1.lid,ct1.rid,ct1.courseid from "
							+ " words ct1 left join words ct2 on ct1.parentid = ct2.id and ct2.status!=1 where ct1.id= ? and ct1.status!=1");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				word.setId(rs.getInt(1));
				word.setName(rs.getString(2));
				word.setDescription(rs.getString(3));
				word.setParent(new EroomLib(rs.getInt(4), rs.getString(5)));
				word.setLid(rs.getInt(6));
				word.setRid(rs.getInt(7));
				word.setCourseid(rs.getInt(8));
			}
		} catch (Exception e) {
			logger.error("获取词汇类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return word;
	}
	
	private List<Word> getChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<Word> deps = new ArrayList<Word>();
		// PreparedStatement ps = ct.prepareStatement(ElQuerySql
		// .getSQL(CourseConstants.EROOMLIB_QUERY_BYPID));
		PreparedStatement ps = ct
				.prepareStatement("select id,name,parentid,lid,rid from words where parentid=? and status!=1 order by id");
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			Word dep = new Word(rstemp.getInt(1), rstemp.getString(2));
			dep.getId();
			dep.setParent(new EroomLib(rstemp.getInt(3)));
			dep.setLevel(level);
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

	public Word getWordsTree(int userid, String op, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Word dep = new Word(ElConstants.USER_OP_LIB, "可操作的词汇库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from words_" + op
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<Word> list = new ArrayList<Word>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					Word depc = getWordsTree(depid, stopid, containStop,
							1);
					if (depc == null || depc.getId() == 0) {
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
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	private Word getWordsTree(int from, int stop, boolean containStop,
			int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Word cltype = null;
		try {
			cltype = getWordLibById(from);
			if (cltype == null || cltype.getId() == 0) {
				return cltype;
			}
			cltype.setLevel(level);
			ct = DBConnection.getConnection();
			cltype.setChild(getChilds(ct, cltype.getId(), stop, containStop,
					level));
		} catch (Exception e) {
			logger.error("词汇类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	
	public Word getWordLibById(int id) throws ElException {
		Word word = new Word();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.EROOMLIB_QUERY_BYID));
			ps = ct
					.prepareStatement("select ct1.id,ct1.name,ct1.description,ct1.parentid,ct2.name,ct1.lid,ct1.rid from "
							+ " words ct1 left join words ct2 on ct1.parentid = ct2.id and ct2.status!=1 where ct1.id= ? and ct1.status!=1");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				word.setId(rs.getInt(1));
				word.setName(rs.getString(2));
				word.setDescription(rs.getString(3));
				word.setParent(new EroomLib(rs.getInt(4), rs.getString(5)));
				word.setLid(rs.getInt(6));
				word.setRid(rs.getInt(7));
			}
		} catch (Exception e) {
			logger.error("获取词汇类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return word;
	}

	public int addWord(Word word) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			// addNode(ct, eroomLib, "eroom_lib", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.WORDLIB_ADD));
			ps.setString(1, word.getName());
			ps.setString(2, word.getDescription());
			ps.setInt(3, word.getParent().getId());
			ps.setInt(4, word.getLid());
			ps.setInt(5, word.getRid());
			ps.setInt(6, word.getCourseid());
			ps.executeUpdate();
			// TODO 获取 刚添加的id
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('eroom_lib') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select words_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next()) {
				word.setId(rs.getInt(1));
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加词汇类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public List<ELUser> getOpUsers(String type, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select eu.id,eu.realname,eu.username from words_"
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
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public void updateWordlibParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update words set parentid=? where parentid=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新词汇库的父节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteWordlibAndSubNot(int id) throws ElException {
		// 查出该类别的左右id，然后查出所有子类别，然后循环根据id更新子类别状态，更新所有类别下的考场最后更新除该类别
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid = this.getLidRid(ct, id, "words");
			List<Integer> typelist = this.getTypeByLidRid(ct,
					typelrid.getLid(), typelrid.getRid(), "words");
			for (int i = 0; i < typelist.size(); i++) {
				// 根据id更新类别以及类别下的资源(先更新资源)
			//	this.deleteExamroomByTypeidNot(ct, typelist.get(i));
				this.deletewordsLibNot(typelist.get(i));

			}
		} catch (Exception e) {
			logger.error("假删除考场库失败！", e);
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
	
	/**
	 * 更新考场库的状态
	 * 
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deletewordsLibNot(int id) throws ElException {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update words set status=1,lid=0,rid=0 where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新考场库的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterWordLib(Word word) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// alterNode(ct, eroomLib, "eroom_lib", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.WORDLIB_ALTER));
			System.out.println(ElQuerySql.getSQL(CourseConstants.WORDLIB_ALTER));
			ps.setString(1, word.getName());
			ps.setString(2, word.getDescription());
			ps.setInt(3, word.getParent().getId());
			ps.setInt(4, word.getCourseid());
			ps.setInt(5, word.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Vocabulary> getVocList(Word word,Vocabulary vocabulary,int pageNow,int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		List<Vocabulary> vocabularys = new ArrayList<Vocabulary>();
		StringBuffer basesql = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*,rownum rn from(select * from vocabulary where name like ";
			sql+= vocabulary==null?"'%%'":vocabulary.getName()==null?"'%%'":" '%"+vocabulary.getName()+"%'";
			sql+= vocabulary==null?"":vocabulary.getWordid()>0?" and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", getWordsById(vocabulary.getWordid()), true)+")":"";
	//		sql+= " ";
	//		sql+= vocabulary==null?"and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", word, true)+")":vocabulary.getWordid()>0?" and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", getWordsById(vocabulary.getWordid()), true)+")":"";
	//		sql+=")";
			sql+= vocabulary==null?"":vocabulary.getStatus()==1?" and status="+vocabulary.getStatus()+"":"";
			sql+=" )t where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			System.out.println(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				Vocabulary voc = new Vocabulary();
				voc.setId(rs.getInt(1));
				voc.setName(rs.getString(2));
				voc.setAdduserid(rs.getInt(3));
				voc.setAlteruserid(rs.getInt(4));
				voc.setAddtime(rs.getTimestamp(5));
				voc.setAltertime(rs.getTimestamp(6));
				voc.setWordid(rs.getInt(7));
				voc.setPinyin(rs.getString(8));
				voc.setDuyin(rs.getString(9));
				voc.setWenzijieshi(rs.getString(10));
				voc.setShengyinjieshi(rs.getString(11));
				voc.setWenziliju(rs.getString(12));
				voc.setLijulangdu(rs.getString(13));
				voc.setStatus(rs.getInt(14));
				vocabularys.add(voc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return vocabularys;
	}

	public int getWordSize(Word word,Vocabulary vocabulary) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from vocabulary where name like";
			sql+= vocabulary==null?"'%%'":vocabulary.getName()==null?"'%%'":" '%"+vocabulary.getName()+"%'";
			sql+= vocabulary==null?"":vocabulary.getWordid()>0?" and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", getWordsById(vocabulary.getWordid()), true)+")":"";
		//	sql+= vocabulary==null?"and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", word, true)+")":vocabulary.getWordid()>0?" and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", getWordsById(vocabulary.getWordid()), true)+")":"";
			sql+= vocabulary==null?"":vocabulary.getStatus()==1?"and status="+vocabulary.getStatus()+"":"";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	public List<Vocabulary> getVocList2(Vocabulary vocabulary,int pageNow,int pageSize)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		List<Vocabulary> vocabularys = new ArrayList<Vocabulary>();
		StringBuffer basesql = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*,rownum rn from(select * from vocabulary where name like ";
			sql+= vocabulary==null?"'%%'":vocabulary.getName()==null?"'%%'":" '%"+vocabulary.getName()+"%'";
			sql+= vocabulary==null?"":vocabulary.getWordid()>=0?" and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", getWordsById(vocabulary.getWordid()), true)+")":"";
			sql+= vocabulary==null?"":vocabulary.getStatus()==1?" and status="+vocabulary.getStatus()+"":"";
			sql+=" )t where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			System.out.println(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				Vocabulary voc = new Vocabulary();
				voc.setId(rs.getInt(1));
				voc.setName(rs.getString(2));
				voc.setAdduserid(rs.getInt(3));
				voc.setAlteruserid(rs.getInt(4));
				voc.setAddtime(rs.getTimestamp(5));
				voc.setAltertime(rs.getTimestamp(6));
				voc.setWordid(rs.getInt(7));
				voc.setPinyin(rs.getString(8));
				voc.setDuyin(rs.getString(9));
				voc.setWenzijieshi(rs.getString(10));
				voc.setShengyinjieshi(rs.getString(11));
				voc.setWenziliju(rs.getString(12));
				voc.setLijulangdu(rs.getString(13));
				voc.setStatus(rs.getInt(14));
				vocabularys.add(voc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return vocabularys;
		}
		
	public int getWordSize2(Vocabulary vocabulary) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from vocabulary where name like";
			sql+= vocabulary==null?"'%%'":vocabulary.getName()==null?"'%%'":" '%"+vocabulary.getName()+"%'";
			sql+= vocabulary==null?"":vocabulary.getWordid()>=0?" and wordid in ("+((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree2("words", getWordsById(vocabulary.getWordid()), true)+")":"";
			sql+= vocabulary==null?"":vocabulary.getStatus()==1?"and status="+vocabulary.getStatus()+"":"";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
		}
	

	public int addVocabulary(Vocabulary vocabulary) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into vocabulary(name,adduserid,addtime,wordid,pinyin,duyin,wenzijieshi,shengyinjieshi,lijulangdu,status,yingwen) values(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, vocabulary.getName());
			ps.setInt(2, vocabulary.getAdduserid());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, vocabulary.getWordid());
			ps.setString(5, vocabulary.getPinyin());
			ps.setString(6, vocabulary.getDuyin());
			ps.setString(7, vocabulary.getWenzijieshi());
			ps.setString(8, vocabulary.getShengyinjieshi());
			ps.setString(9, vocabulary.getLijulangdu());
			ps.setInt(10, vocabulary.getStatus());
			ps.setString(11, vocabulary.getYingwen());
			ps.executeUpdate();
			
			ps = ct.prepareStatement("select VOCABULARY_SEQUENCE.currval from dual ");
			rs = ps.executeQuery();  
			if (rs.next()){
				id =  rs.getInt(1); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void addVocabularySen(Vocabulary vocabulary) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into VOCABULARY_SENTENCES(vocabularyid,lijuwenzi,shengyinwenjian) values(?,?,?)");
			ps.setInt(1, vocabulary.getId());
			ps.setString(2, vocabulary.getWenziliju());
			ps.setString(3, vocabulary.getLijudizhi());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Vocabulary getVocById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		Vocabulary voc = new Vocabulary();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from vocabulary where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				voc.setId(rs.getInt(1));
				voc.setName(rs.getString(2));
				voc.setAdduserid(rs.getInt(3));
				voc.setAlteruserid(rs.getInt(4));
				voc.setAddtime(rs.getTimestamp(5));
				voc.setAltertime(rs.getTimestamp(6));
				voc.setWordid(rs.getInt(7));
				voc.setPinyin(rs.getString(8));
				voc.setDuyin(rs.getString(9));
				voc.setWenzijieshi(rs.getString(10));
				voc.setShengyinjieshi(rs.getString(11));
				voc.setWenziliju(rs.getString(12));
				voc.setLijulangdu(rs.getString(13));
				voc.setStatus(rs.getInt(14));
				voc.setYingwen(rs.getString(15));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return voc;
	}

	public void delVocById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete  from vocabulary where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void alterVocSta(Vocabulary vocabulary) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update vocabulary set status=? where id=?");
			ps.setInt(1, vocabulary.getStatus());
			ps.setInt(2, vocabulary.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<StuffLib> liststuff(int vocabularyid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<StuffLib> stuffss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from VOCABULARY_SENTENCES where vocabularyid=?");
			ps.setInt(1, vocabularyid);
			rs = ps.executeQuery();
			while(rs.next()){
				StuffLib s = new StuffLib();
				s.setId(rs.getInt(1));
				s.setTitle(rs.getString(3));
				s.setDescription(rs.getString(4));
				stuffss.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffss;
	}

	public void deleteVocStuff(int stuffid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from VOCABULARY_SENTENCES where id=?");
			ps.setInt(1, stuffid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterVocabulary(Vocabulary vocabulary) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update VOCABULARY set name=?,alteruserid=?,altertime=?,wordid=?,pinyin=?,duyin=?,wenzijieshi=?,shengyinjieshi=?,yingwen=? where id=?");
			ps.setString(1, vocabulary.getName());
			ps.setInt(2, vocabulary.getAlteruserid());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, vocabulary.getWordid());
			ps.setString(5, vocabulary.getPinyin());
			ps.setString(6, vocabulary.getDuyin());
			ps.setString(7, vocabulary.getWenzijieshi());
			ps.setString(8, vocabulary.getShengyinjieshi());
			ps.setString(9, vocabulary.getYingwen());
			ps.setInt(10, vocabulary.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterVocStuff(Vocabulary vocabulary) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update VOCABULARY_SENTENCES set lijuwenzi=?,shengyinwenjian=? where id=?");
			ps.setString(1, vocabulary.getStuff().getTitle());
			ps.setString(2, vocabulary.getStuff().getDescription());
			ps.setInt(3, vocabulary.getStuff().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<Vocabulary> getVocListByUserid(int adduserid,int pagenow,int pagesize)throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Vocabulary> vocabularys = new ArrayList<Vocabulary>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from (select * from vocabulary where adduserid=?)t where rownum<=?) where rn>=?");
			ps.setInt(1, adduserid);
			ps.setInt(2, pagenow);
			ps.setInt(3, pagesize);
			rs = ps.executeQuery();
			while(rs.next()){
				Vocabulary voc = new Vocabulary();
				voc.setId(rs.getInt(1));
				voc.setName(rs.getString(2));
				voc.setAdduserid(rs.getInt(3));
				voc.setAlteruserid(rs.getInt(4));
				voc.setAddtime(rs.getTimestamp(5));
				voc.setAltertime(rs.getTimestamp(6));
				voc.setWordid(rs.getInt(7));
				voc.setPinyin(rs.getString(8));
				voc.setDuyin(rs.getString(9));
				voc.setWenzijieshi(rs.getString(10));
				voc.setShengyinjieshi(rs.getString(11));
				voc.setWenziliju(rs.getString(12));
				voc.setLijulangdu(rs.getString(13));
				voc.setStatus(rs.getInt(14));
				vocabularys.add(voc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return vocabularys;
	}
	
	public int getVocListByUseridSize(int adduserid)throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from vocabulary where adduserid=?");
			ps.setInt(1, adduserid);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public Word getWordsByCourseId(int courseid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int id = 0;
		Word word = new Word();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from words where courseid=?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if(rs.next()){
				word.setId(rs.getInt(1));
				word.setName(rs.getString(2));
				word.setDescription(rs.getString(3));
				word.setParent(new ElNode(rs.getInt(4)));
				word.setCourseid(rs.getInt(5));
				word.setLid(rs.getInt(6));
				word.setRid(rs.getInt(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return word;
	}

	public List<Word> getWordsTree() throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Word> wordTree = new ArrayList<Word>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from words order by id");
			rs = ps.executeQuery();
			while(rs.next()){
				Word wd = new Word();
				wd.setId(rs.getInt(1));
				wd.setName(rs.getString(2));
				wd.setDescription(rs.getString(3));
				wd.setParent(new ElNode(rs.getInt(4)));
				wd.setCourseid(rs.getInt(5));
				wordTree.add(wd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return wordTree;
	}

	public List<Word> getWordsTreeByParentid(int parentid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Word> wordTree = new ArrayList<Word>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from words where parentid=? order by id");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while(rs.next()){
				Word wd = new Word();
				wd.setId(rs.getInt(1));
				wd.setName(rs.getString(2));
				wd.setDescription(rs.getString(3));
				wd.setParent(new ElNode(rs.getInt(4)));
				wd.setCourseid(rs.getInt(5));
				wordTree.add(wd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return wordTree;
	}
	public List<Course> getVocByWordId(ElNode word) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer basesql = new StringBuffer();
			basesql.append("select ws.id sid,ws.name wsname,ws.courseid wscourseid,c.name cname from words ws inner join(");
			basesql.append( ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("words", word, true));
			basesql.append(")w on w.id=ws.id left join(select id,name from course)c on c.id=ws.courseid where ws.courseid=c.id");
			System.out.println(basesql.toString());
			ps = ct.prepareStatement(basesql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				Course c = new Course();
				c.setId(rs.getInt(3));
				c.setName(rs.getString(4));
				courses.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public void deleteUserOpGrant(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement(" delete from WORDS_OP_USER where userid= ?");
			ps.setInt(1, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
			ps = ct.prepareStatement("select * from words_" + type
					+ "_user where userid = ? and depid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void addOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into words_" + type
					+ "_user(userid,depid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Word wdLibTree(String op, int userid, int stopid,
			boolean isContainStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// ExamPaperLib dep = op.equals("op") ? new ExamPaperLib(1, "可操作的试卷库")
		// : new ExamPaperLib(1, "可使用的试卷库");
		Word dep = new Word(ElConstants.USER_OP_LIB, "可操作的词汇库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from words_" + op
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<Word> list = new ArrayList<Word>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !isContainStop) {
				} else {
					Word depc = wdLibTree(depid, stopid, isContainStop,
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
	private Word wdLibTree(int id, int stopid, boolean isContainStop,
			int level) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Word epl = null;
		epl = getWordsById(id);
		if(epl==null||epl.getId()==0){
			return epl;
		}
		try {
			epl.setLevel(level);
			ct = DBConnection.getConnection();
			epl.setChild(getChilds(ct,epl.getId(), stopid, isContainStop,
							0));
		} catch (Exception e) {
			logger.error("获取全部试卷库列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epl;
	}

	public Word wdLibTree(int id, int userid, int stopid, boolean isContainStop)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Word epl = null;
		if (id == 0)
			epl = getWordsRoot();
		else
			epl = getWordsById(id);

		try {
			ct = DBConnection.getConnection();
			epl.setChild(getChilds(ct,epl.getId(),stopid,isContainStop,0));
		} catch (Exception e) {
			logger.error("获取全部试卷库列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epl;
	}
	//wjm0221修改
	public int getwdLibTreeId(String name)throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int wordid = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from words where name=?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			if(rs.next()){
				wordid = rs.getInt(1);
				return wordid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}
	
	/**
	 * 检测词汇是否重复
	 * @param question
	 * @throws ElException
	 */
	public boolean checkVocIsRepeat(Vocabulary voc) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select id from vocabulary where name=? and wordid=? ");
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			logger.info(sql.toString());
			ps.setString(1, voc.getName());
			ps.setInt(2, voc.getWordid());
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
}
