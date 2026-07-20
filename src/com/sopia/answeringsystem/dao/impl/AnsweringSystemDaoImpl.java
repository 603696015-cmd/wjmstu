package com.sopia.answeringsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.answeringsystem.dao.AnsweringSystemDao;
import com.sopia.answeringsystem.entities.Answer;
import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.answeringsystem.entities.Ques;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;

public class AnsweringSystemDaoImpl implements AnsweringSystemDao {
	private static final Log logger = LogFactory.getLog(AnsweringSystemDaoImpl.class);

	public int addQues(Ques ques) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int id=0;
		try {
			ct = DBConnection.getConnection();
			sql = "insert into ques (name,status,validTime,fabuuserid,fabutime,viewcount,answeringtypeid,answeruserids,statustow,content) " +
					"values (?,?,?,?,?,?,?,?,?,empty_blob())";
			ps = ct.prepareStatement(sql);
			ps.setString(1, ques.getName());
			ps.setInt(2, ques.getStatus());
			ps.setTimestamp(3, ques.getValidTime());
			ps.setInt(4, ques.getFabuUserid());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setInt(6, ques.getViewCount());
			ps.setInt(7, ques.getAnsweringType().getId());
			ps.setString(8, ques.getAnswerUserids());
			ps.setInt(9, ques.getStatusTow());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"ques_sequence","ques","id","content",ques.getContent(),"添加问题失败");
			setblob.addContent(); 
			
			ps = ct.prepareStatement("select ques_sequence.currval from dual");
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("添加问题失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
		
	}

	public List<Ques> listMyQues(int userid, int pageNow, int pageSize,Ques q)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<Ques> queses = new ArrayList<Ques>();
		Ques ques = null;
		AnsweringType at = null;
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			if(q!=null){
				if(q.getName()!=null&&!q.getName().equals("")){
					sqlAppend += " and q.name like '%" + q.getName() + "%'";
				}
			}
			sql = "select b.*,rn from (select a.*,rownum rn from (select q.id,q.name,q.status,q.validTime,q.fabuuserid,q.fabutime,q.viewcount,q.answeringtypeid,q.answeruserids,a.id as aid,a.name as aname,e.id as eid,e.realname as erealname,q.statustow " +
					" from ques q,answeringtype a,eluser e " +
					"where q.answeringtypeid=a.id and q.fabuuserid=e.id and fabuuserid=?" + sqlAppend + ") a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(2));
				ques.setStatus(rs.getInt(3));
				ques.setValidTime(rs.getTimestamp(4));
				ques.setFabuUserid(rs.getInt(5));
				ques.setFabuTime(rs.getTimestamp(6));
				ques.setViewCount(rs.getInt(7));
				ques.setAnsweringTypeid(rs.getInt(8));
				ques.setAnswerUserids(rs.getString(9));
				at = new AnsweringType(rs.getInt(10),rs.getString(11));
				elUser = new ELUser(rs.getInt(12),rs.getString(13));
				ques.setStatusTow(rs.getInt(14));
				ques.setAnsweringType(at);
				ques.setFabuUser(elUser);
				
				queses.add(ques);
			}
			
			
		} catch (Exception e) {
			logger.error("我的问题失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return queses;
	}

	public int listMyQuesSize(int userid,Ques q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			if(q!=null){
				if(q.getName()!=null&&!q.getName().equals("")){
					sqlAppend += " and name like '%" + q.getName() + "%'";
				}
			}
			sql = "select count(1) from ques where fabuuserid=?" + sqlAppend;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("我的问题size失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public void deleteQues(int quesid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "delete from ques where id=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, quesid);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("删除问题失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public Ques queryQuesById(int quesid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		Ques ques = null;
		AnsweringType at = null;
		ELUser elUser = null;
		ElRole role = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select q.id,q.name,q.status,q.validTime,q.fabuuserid,q.fabutime,q.viewcount,q.answeringtypeid,q.answeruserids,q.content,a.id as aid,a.name as aname,e.id as eid,e.realname as erealname,q.statustow,r.id as rid,r.name as rname " +
					" from ques q,answeringtype a,eluser e,elrole r " +
					"where e.role=r.id and q.answeringtypeid=a.id and q.fabuuserid=e.id and q.id=? " ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, quesid);
			rs = ps.executeQuery();
			if(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(2));
				ques.setStatus(rs.getInt(3));
				ques.setValidTime(rs.getTimestamp(4));
				ques.setFabuUserid(rs.getInt(5));
				ques.setFabuTime(rs.getTimestamp(6));
				ques.setViewCount(rs.getInt(7));
				ques.setAnsweringTypeid(rs.getInt(8));
				ques.setAnswerUserids(rs.getString(9));
				ques.setContent(new OracleBlob().getContent(rs.getBlob(10)));
				at = new AnsweringType(rs.getInt(11),rs.getString(12));
				role = new ElRole(rs.getInt(16),rs.getString(17));
				elUser = new ELUser(rs.getInt(13),rs.getString(14));
				elUser.setRole(role);
				ques.setStatusTow(rs.getInt(15));
				ques.setAnsweringType(at);
				ques.setFabuUser(elUser);
				
			}
			
			
		} catch (Exception e) {
			logger.error("根据id查找失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ques;
	}

	public List<ELUser> listAnswerUser(String userids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<ELUser> elUsers = new ArrayList<ELUser>();
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select id,realname from eluser where id in (" + userids + ")";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				elUser = new ELUser(rs.getInt(1),rs.getString(2));
				
				elUsers.add(elUser);
			}
			
			
		} catch (Exception e) {
			logger.error("我的问题的回答人失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUsers;
	}

	public void alterQuesById(Ques ques) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update ques set name=?,validtime=?,answeringtypeid=?,answeruserids=?,content=empty_blob() where id=?" ;
			ps = ct.prepareStatement(sql);
			ps.setString(1, ques.getName());
			ps.setTimestamp(2, ques.getValidTime());
			ps.setInt(3, ques.getAnsweringType().getId());
			ps.setString(4, ques.getAnswerUserids());
			ps.setInt(5, ques.getId());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob("ques","id",ques.getId()+"","content",ques.getContent(),"修改问题失败",ct);
			setblob.updateContent();  
			
		} catch (Exception e) {
			logger.error("修改问题失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void addViewCountById(Ques ques) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update ques set viewcount=? where id=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, ques.getViewCount());
			ps.setInt(2, ques.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("viewcount增加1失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public int listAnswersSizeByQuesid(int quesid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from answer where questionid=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, quesid);
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("回复数失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<Ques> listQuesByAnsweringType(AnsweringType answeringType,int pageNow,int pageSize,Ques q)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<Ques> queses = new ArrayList<Ques>();
		Ques ques = null;
		AnsweringType at = null;
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			if(q!=null){
				if(q.getName()!=null&&!q.getName().equals("")){
					sqlAppend += " and q.name like '%" + q.getName() + "%'";
				}
			}
			sql = "select b.*,rn from (select a.*,rownum rn from (select q.id,q.name,q.status,q.validTime,q.fabuuserid,q.fabutime,q.viewcount,q.answeringtypeid,q.answeruserids,a.id as aid,a.name as aname,e.id as eid,e.realname as erealname,q.statustow " +
					" from ques q,answeringtype a,eluser e ," +
					"  ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("answeringtype", answeringType, true)
					+ ") ansty  " +
					"where ansty.id=q.answeringtypeid and q.answeringtypeid=a.id and q.fabuuserid=e.id " + sqlAppend + ") a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(2));
				ques.setStatus(rs.getInt(3));
				ques.setValidTime(rs.getTimestamp(4));
				ques.setFabuUserid(rs.getInt(5));
				ques.setFabuTime(rs.getTimestamp(6));
				ques.setViewCount(rs.getInt(7));
				ques.setAnsweringTypeid(rs.getInt(8));
				ques.setAnswerUserids(rs.getString(9));
				at = new AnsweringType(rs.getInt(10),rs.getString(11));
				elUser = new ELUser(rs.getInt(12),rs.getString(13));
				ques.setStatusTow(rs.getInt(14));
				ques.setAnsweringType(at);
				ques.setFabuUser(elUser);
				
				queses.add(ques);
			}
			
			
		} catch (Exception e) {
			logger.error("我的问题失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return queses;
	}

	public int listQuesSizeByAnsweringType(AnsweringType answeringType,Ques q)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			if(q!=null){
				if(q.getName()!=null&&!q.getName().equals("")){
					sqlAppend += " and name like '%" + q.getName() + "%'";
				}
			}
			sql = "select count(1) from ques q, " +
					"  ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("answeringtype", answeringType, true)
					+ ") ansty  " +
					"where q.answeringtypeid=ansty.id" + sqlAppend;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("我的问题size失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public void alterQuesStatus(int quesid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update ques set status=? where id=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, quesid);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("通过或者不通过失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void alterAnswerStatus(int answerid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update answer set status=? where id=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, answerid);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("通过或者不通过失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<Answer> listMyAnswers(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = null;
		Ques ques = null;
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			if(userid == -1){
				sql = "select c.*,rn from (select b.*,rownum rn from " +
				"(select a.questionid,a.answertime,a.answeruserid,a.status,a.answercontent,q.name,e.realname,a.id from answer a,ques q,eluser e where a.questionid=q.id and a.answeruserid=e.id " +
				" ) b where rownum<=? ) c where rn>=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}else{
				sql = "select c.*,rn from (select b.*,rownum rn from " +
				"(select a.questionid,a.answertime,a.answeruserid,a.status,a.answercontent,q.name,e.realname,a.id from answer a,ques q,eluser e where a.questionid=q.id and a.answeruserid=e.id and a.answeruserid=?" +
				" ) b where rownum<=? ) c where rn>=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(6));
				elUser = new ELUser(rs.getInt(3),rs.getString(7));
				answer = new Answer();
				answer.setQues(ques);
				answer.setAnswerContent(new OracleBlob().getContent(rs.getBlob(5)));
				answer.setAnswerUser(elUser);
				answer.setAnswerTime(rs.getTimestamp(2));
				answer.setStatus(rs.getInt(4));
				answer.setId(rs.getInt(8));
				answers.add(answer);
			}
			
			
		} catch (Exception e) {
			logger.error("我的回复失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return answers;
	}

	public int listMyAnswersSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			if(userid == -1){
				sql = "select count(1) from answer";
				ps = ct.prepareStatement(sql);
			}else{
				sql = "select count(1) from answer where answeruserid=?" ;
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
			}
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("我的回复数失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public int getQuestionCount(int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlwhere = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			if(status>0){
				sqlwhere = "where status=" + status;
			}else if(status<0){
				sqlwhere = "where status!=" + status;
			}
			sql = "select count(1) from ques " + sqlwhere ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("问题数失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<Answer> listAnswersByDate() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = null;
		Ques ques = null;
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select a.questionid,a.answertime,a.answeruserid,a.status,a.answercontent,q.name,e.realname,at.id as atid, at.name as atname from answer a,ques q,answeringtype at,eluser e where a.questionid=q.id and a.answeruserid=e.id and q.answeringtypeid=at.id " +
					" order by a.answertime desc";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(6));
				ques.setAnsweringType(new AnsweringType(rs.getInt(8),rs.getString(9)));
				elUser = new ELUser(rs.getInt(3),rs.getString(7));
				answer = new Answer();
				answer.setQues(ques);
				answer.setAnswerContent(new OracleBlob().getContent(rs.getBlob(5)));
				answer.setAnswerUser(elUser);
				answer.setAnswerTime(rs.getTimestamp(2));
				answer.setStatus(rs.getInt(4));
				answers.add(answer);
			}
			
			
		} catch (Exception e) {
			logger.error("最新回复失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return answers;
	}

	public List<Ques> listQuesesByDate() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Ques> queses = new ArrayList<Ques>();
		Ques ques = null;
		AnsweringType at = null;
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select q.id,q.name,q.status,q.validTime,q.fabuuserid,q.fabutime,q.viewcount,q.answeringtypeid,q.answeruserids,a.id as aid,a.name as aname,e.id as eid,e.realname as erealname,q.statustow " +
					" from ques q,answeringtype a,eluser e " +
					"where  q.answeringtypeid=a.id and q.fabuuserid=e.id order by q.fabutime desc ";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(2));
				ques.setStatus(rs.getInt(3));
				ques.setValidTime(rs.getTimestamp(4));
				ques.setFabuUserid(rs.getInt(5));
				ques.setFabuTime(rs.getTimestamp(6));
				ques.setViewCount(rs.getInt(7));
				ques.setAnsweringTypeid(rs.getInt(8));
				ques.setAnswerUserids(rs.getString(9));
				at = new AnsweringType(rs.getInt(10),rs.getString(11));
				elUser = new ELUser(rs.getInt(12),rs.getString(13));
				ques.setStatusTow(rs.getInt(14));
				ques.setAnsweringType(at);
				ques.setFabuUser(elUser);
				
				queses.add(ques);
			}
			
			
		} catch (Exception e) {
			logger.error("最新问题失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return queses;
	}

	public List<Ques> listQuesesByStatusTow(int statusTow) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Ques> queses = new ArrayList<Ques>();
		Ques ques = null;
		AnsweringType at = null;
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select c.* from (select q.id,q.name,q.status,q.validTime,q.fabuuserid,q.fabutime,q.viewcount,q.answeringtypeid,q.answeruserids,a.id as aid,a.name as aname,e.id as eid,e.realname as erealname,q.statustow " +
					" from ques q,answeringtype a,eluser e " +
					"where q.statustow=3 and  q.answeringtypeid=a.id and q.fabuuserid=e.id order by q.fabutime desc ) c where rownum<=8";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(2));
				ques.setStatus(rs.getInt(3));
				ques.setValidTime(rs.getTimestamp(4));
				ques.setFabuUserid(rs.getInt(5));
				ques.setFabuTime(rs.getTimestamp(6));
				ques.setViewCount(rs.getInt(7));
				ques.setAnsweringTypeid(rs.getInt(8));
				ques.setAnswerUserids(rs.getString(9));
				at = new AnsweringType(rs.getInt(10),rs.getString(11));
				elUser = new ELUser(rs.getInt(12),rs.getString(13));
				ques.setStatusTow(rs.getInt(14));
				ques.setAnsweringType(at);
				ques.setFabuUser(elUser);
				
				queses.add(ques);
			}
			
			
		} catch (Exception e) {
			logger.error("最新问题失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return queses;
	}
	
	
	public List<Answer> listAnswersByQuesid(int quesid,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = null;
		Ques ques = null;
		ELUser elUser = null;
		ElRole role = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select c.* ,rn from (select b.* ,rownum rn from " +
					" (select a.questionid,a.answertime,a.answeruserid,a.status,a.answercontent,q.name,e.realname,at.id as atid, at.name as atname,r.id as rid, r.name as rname from answer a,ques q,answeringtype at,eluser e,elrole r where e.role=r.id and a.questionid=q.id and a.answeruserid=e.id and q.answeringtypeid=at.id and a.questionid=? " +
					" order by a.answertime desc ) b where rownum<=? ) c where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, quesid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(6));
				ques.setAnsweringType(new AnsweringType(rs.getInt(8),rs.getString(9)));
				role = new ElRole(rs.getInt(10),rs.getString(11));
				elUser = new ELUser(rs.getInt(3),rs.getString(7));
				elUser.setRole(role);
				answer = new Answer();
				answer.setQues(ques);
				answer.setAnswerContent(new OracleBlob().getContent(rs.getBlob(5)));
				answer.setAnswerUser(elUser);
				answer.setAnswerTime(rs.getTimestamp(2));
				answer.setStatus(rs.getInt(4));
				answers.add(answer);
			}
			
			
		} catch (Exception e) {
			logger.error("获取该问题的回复失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return answers;
	}

	public void addAnswer(Answer answer) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into answer (questionid,answercontent,answertime,answeruserid,status) " +
					" values (?,empty_blob(),?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, answer.getQuestionId());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, answer.getAnswerUser().getId());
			ps.setInt(4, answer.getStatus());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"answer_sequence","answer","id","answercontent",answer.getAnswerContent(),"提交回答失败");
			setblob.addContent();  
			
			
		} catch (Exception e) {
			logger.error("提交回复失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Ques> listQuestionByAnsweringType(ElNode tree, int pageNow,
			int pageSize,String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Ques> queses = new ArrayList<Ques>();
		Ques ques = null;
		AnsweringType at = null;
		ELUser elUser = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (select a.*,rownum rn from (select q.id,q.name,q.status,q.validTime,q.fabuuserid,q.fabutime,q.viewcount,q.answeringtypeid,q.answeruserids,a.id as aid,a.name as aname,e.id as eid,e.realname as erealname,q.statustow " +
					" from ques q,(select * from answeringtype where lid>=? and rid<=?) a,eluser e " +
					"where q.answeringtypeid=a.id and q.fabuuserid=e.id and q.status in ("+status+") order by q.fabutime desc) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, tree.getLid());
			ps.setInt(2, tree.getRid());
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ques = new Ques(rs.getInt(1),rs.getString(2));
				ques.setStatus(rs.getInt(3));
				ques.setValidTime(rs.getTimestamp(4));
				ques.setFabuUserid(rs.getInt(5));
				ques.setFabuTime(rs.getTimestamp(6));
				ques.setViewCount(rs.getInt(7));
				ques.setAnsweringTypeid(rs.getInt(8));
				ques.setAnswerUserids(rs.getString(9));
				at = new AnsweringType(rs.getInt(10),rs.getString(11));
				elUser = new ELUser(rs.getInt(12),rs.getString(13));
				ques.setStatusTow(rs.getInt(14));
				ques.setAnsweringType(at);
				ques.setFabuUser(elUser);
				
				queses.add(ques);
			}
			
			
		} catch (Exception e) {
			logger.error("根据树失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return queses;
	}

	public int listQuestionSizeByAnsweringType(ElNode tree,String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) " +
					" from ques q,(select * from answeringtype where lid>=? and rid<=?) a,eluser e " +
					"where q.answeringtypeid=a.id and q.fabuuserid=e.id and q.status in ("+status+") order by q.fabutime desc";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, tree.getLid());
			ps.setInt(2, tree.getRid());
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("根据树size失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public void setStatusTow(int quesid, int statusTow) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update ques set statustow=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, statusTow);
			ps.setInt(2, quesid);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("设置问题热度失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


}
