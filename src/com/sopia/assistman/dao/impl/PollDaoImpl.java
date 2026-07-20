package com.sopia.assistman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.assistman.dao.PollDao;
import com.sopia.assistman.entities.Poll;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.StringUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.courseman.entities.QuestionSelect;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.impl.QuestionDaoImpl;
import com.sopia.questionman.entities.Question;

public class PollDaoImpl implements PollDao {
	private static final Log logger = LogFactory.getLog(PollDaoImpl.class);
	
	/**
	 * 添加投票
	 */
	public void addPoll(Poll poll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into pollInfo(title,remack,stuviewresult,qid,createrid,createtime,begintime,endtime,hot) values(?,?,?,?,?,sysdate,?,?,?)");
			ps.setString(1, poll.getTitle());
			ps.setString(2, poll.getRemack());
			ps.setInt(3, poll.getStuViewResult());
			ps.setInt(4, poll.getQuestion().getId());
			ps.setInt(5, poll.getCreater().getId());
			ps.setTimestamp(6, poll.getBegintime());
			ps.setTimestamp(7, poll.getEndtime());
			ps.setInt(8, poll.getHot());
//			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
			ps = ct.prepareStatement("select pollInfo_sequence.nextval from dual");
			rs=ps.executeQuery();
			if(rs.next()){
				poll.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("添加投票失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新投票
	 * @param poll
	 * @throws ElException
	 */
	public void updatePoll(Poll poll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update pollInfo set title=?,remack=?,stuviewresult=?,begintime=?,endtime=? where id=?");
			ps.setString(1, poll.getTitle());
			ps.setString(2, poll.getRemack());
			ps.setInt(3, poll.getStuViewResult());
			ps.setTimestamp(4, poll.getBegintime());
			ps.setTimestamp(5, poll.getEndtime());
			ps.setInt(6, poll.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新投票失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 检测投票查询参数
	 * @param poll
	 * @param sql
	 * @param params
	 */
	private void checkPollParam(Poll poll,StringBuffer sql,Vector<Object> params){
		if(poll!=null){
			if(poll.getTitle()!=null&&!"".equals(poll.getTitle().trim())){
				sql.append(" and pol.title like ?");
				params.add("%"+StringUtil.toLikeStr(poll.getTitle().trim())+"%");
			}
			if(poll.getCreatetimeStart()!=null){
				sql.append(" and pol.createtime>=?");
				params.add(poll.getCreatetimeStart());
			}
			if(poll.getCreatetimeEnd()!=null){
				sql.append(" and pol.createtime<=?");
				params.add(poll.getCreatetimeEnd());
			}
			if(poll.getEndtimeStart()!=null){
				sql.append(" and pol.endtime>=?");
				params.add(poll.getEndtimeStart());
			}
			if(poll.getEndtimeEnd()!=null){
				sql.append(" and pol.endtime<=?");
				params.add(poll.getEndtimeEnd());
			}
		}
	}
	/**
	 * 查询my创建的投票信息
	 * @param poll
	 * @return
	 * @throws ElException
	 */
	public List<Poll> myPollList(Poll poll,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Poll> pollList=new ArrayList<Poll>();
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select * from (select t.*,rownum rn from (select pol.id,pol.title,pol.createtime,pol.begintime,pol.endtime,pol.status from pollinfo pol where 1=1");
			checkPollParam(poll, sql, params);
			sql.append(" order by createtime desc) t where rownum <= ?) where rn >= ?  ");
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(params.size()+1, pageNow);
			ps.setInt(params.size()+2, pageSize);
			rs = ps.executeQuery();
			Poll pl=null;
			while(rs.next()) {
				pl = new Poll(rs.getInt(1), rs.getString(2));
				pl.setCreatetime(rs.getTimestamp(3));
				pl.setBegintime(rs.getTimestamp(4));
				pl.setEndtime(rs.getTimestamp(5));
				pl.setStatus(rs.getInt(6));
				pollList.add(pl);
			}
		} catch (Exception e) {
			logger.error("查询my创建的投票信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pollList;
	}
	/**
	 * 查询my创建的投票信息数量
	 * @param poll
	 * @return
	 * @throws ElException
	 */
	public int myPollListCount(Poll poll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select count(pol.id) from pollinfo pol where 1=1");
			checkPollParam(poll, sql, params);
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询my创建的投票信息数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 获取投票信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Poll getPoolById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Poll pl = new Poll();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select pol.id,pol.title,pol.remack,pol.begintime,pol.endtime,pol.stuviewresult,pol.status,q.id,q.title,q.subject,q.qtype,eu.id euid,eu.username,eu.realname,pol.hot from pollinfo pol " +
					" left join question q on pol.qid=q.id " +
					" left join elUser eu on pol.createrid=eu.id where pol.id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				pl = new Poll(rs.getInt(1), rs.getString(2));
				pl.setRemack(rs.getString(3));
				pl.setBegintime(rs.getTimestamp(4));
				pl.setEndtime(rs.getTimestamp(5));
				pl.setStuViewResult(rs.getInt(6));
				pl.setStatus(rs.getInt(7));
				pl.setQuestion(new Question(rs.getInt(8),rs.getString(9)));
				pl.getQuestion().setSubject(rs.getString(10));
				pl.getQuestion().setQtype(rs.getInt(11));
				pl.setCreater(new ELUser(rs.getInt(12),rs.getString(13)));
				pl.setHot(rs.getInt(15));
			}
		} catch (Exception e) {
			logger.error("获取投票信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pl;
	}
	/**
	 * 删除投票
	 * @param poll
	 * @throws ElException
	 */
	public void deletePoll(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from pollInfo where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除投票失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 添加投票学员信息
	 * @param pollid
	 * @param userid
	 * @throws ElException
	 */
	public void addPollUser(int pollid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into poll_assign(pollid,userid) values(?,?)");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加投票学员信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 删除投票学员信息
	 * @param pollid
	 * @param userid
	 * @throws ElException
	 */
	public void deletePollUser(int pollid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from poll_assign where pollid=? and userid=?");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除投票学员信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 检测投票用户是否已经分配
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkPollUser(int pollid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select pollid from poll_assign where pollid=? and userid=?");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			logger.error("检测投票用户是否已经分配失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	/**
	 * 投票审核列表查询
	 * @param tree	部门树
	 * @param sublibs 是否包含下级
	 * @param poll
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Poll> pollShList(ElNode tree,int sublibs,Poll poll,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Poll> pollList=new ArrayList<Poll>();
		boolean bool=sublibs==1?true:false;
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select * from (select t.*,rownum rn from (select pol.id,pol.title,pol.createtime,pol.begintime,pol.endtime,pol.status,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname " +
					" from pollinfo pol inner join eluser eu on pol.createrid=eu.id " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, bool)+") dep on eu.depid=dep.id where 1=1");
			checkPollParam(poll, sql, params);
			sql.append(" order by createtime desc) t where rownum <= ?) where rn >= ?  ");
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(params.size()+1, pageNow);
			ps.setInt(params.size()+2, pageSize);
			rs = ps.executeQuery();
			Poll pl=null;
			while(rs.next()) {
				pl = new Poll(rs.getInt(1), rs.getString(2));
				pl.setCreatetime(rs.getTimestamp(3));
				pl.setBegintime(rs.getTimestamp(4));
				pl.setEndtime(rs.getTimestamp(5));
				pl.setStatus(rs.getInt(6));
				pl.setCreater(new ELUser(rs.getInt(7),rs.getString(8),rs.getString(9)));
				pl.getCreater().setDepartment(new Department(rs.getInt(10),rs.getString(11)));
				pollList.add(pl);
			}
		} catch (Exception e) {
			logger.error("投票审核列表查询失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pollList;
	}
	/**
	 * 投票审核列表查询数量
	 * @param tree	部门树
	 * @param sublibs 是否包含下级
	 * @param poll
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int pollShListCount(ElNode tree,int sublibs,Poll poll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool=sublibs==1?true:false;
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select count(pol.id) " +
					" from pollinfo pol inner join eluser eu on pol.createrid=eu.id " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, bool)+") dep on eu.depid=dep.id where 1=1");
			checkPollParam(poll, sql, params);
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("投票审核列表查询数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 更新投票状态
	 * @param pollid
	 * @param status
	 * @throws ElException
	 */
	public void updatePollStatus(int pollid,int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update pollInfo set status=? where id=?");
			ps.setInt(1, status);
			ps.setInt(2, pollid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新投票状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 学员分配的投票列表
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Poll> studyPollList(int userid,int pageNow,int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Poll> pollList=new ArrayList<Poll>();
		try {
			//Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select * from (select t.*,rownum rn from (select pol.id,pol.title,pol.createtime,pol.begintime,pol.endtime,pol.status,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,pol.stuviewresult " +
					" from pollinfo pol inner join poll_assign pa on pol.id=pa.pollid " +
					" left join eluser eu on pol.createrid=eu.id " +
					" left join  department dep on eu.depid=dep.id where pol.status=2 and pa.userid=?");
			//checkPollParam(poll, sql, params);
			sql.append(" order by createtime desc) t where rownum <= ?) where rn >= ?  ");
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			Poll pl=null;
			while(rs.next()) {
				pl = new Poll(rs.getInt(1), rs.getString(2));
				pl.setCreatetime(rs.getTimestamp(3));
				pl.setBegintime(rs.getTimestamp(4));
				pl.setEndtime(rs.getTimestamp(5));
				pl.setStatus(rs.getInt(6));
				pl.setCreater(new ELUser(rs.getInt(7),rs.getString(8),rs.getString(9)));
				pl.getCreater().setDepartment(new Department(rs.getInt(10),rs.getString(11)));
				pl.setStuViewResult(rs.getInt(12));
				pl.setIsApply(checkPollQuizinfo(pl.getId(), userid));
				pollList.add(pl);
			}
		} catch (Exception e) {
			logger.error("学员分配的投票列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pollList;
	}
	/**
	 * 检测学员是否已经投票
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int checkPollQuizinfo(int pollid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select pollid from poll_quizinfo where pollid=? and userid=?");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				return 1;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经投票失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 添加学员投票的结果
	 * @param pollid
	 * @param userid
	 * @param answer
	 * @throws ElException
	 */
	public void addPollQuizinfo(int pollid,int userid,int answer) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into poll_quizinfo(pollid,userid,answer) values(?,?,?)");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			ps.setInt(3, answer);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加学员投票的结果失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 检测学员是否已经投票
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkPollQuizinfo(int pollid,int userid,int answer) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select pollid from poll_quizinfo where pollid=? and userid=? and answer=?");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			ps.setString(3, answer+"");
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经投票失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	/**
	 * 投票统计
	 * @param poll
	 * @return
	 * @throws ElException
	 */
	public QuestionRanking pollResult(Poll poll) throws ElException{
		QuestionRanking qr=new QuestionRanking();
		//1.首先获取该题信息以及该题有多少答案
		qr.setQuestion(new QuestionDaoImpl().getQuestionByid(poll.getQuestion().getId()));
		List<QuestionSelect> qList=new ArrayList<QuestionSelect>();
		QuestionSelect qSelect=null;
		qr.setAnswerSum(qr.getQuestion().getSubject().split("-=SpEl=-").length);
		for(int i=0;i<qr.getAnswerSum();i++){
			qSelect=new QuestionSelect();
			qSelect.setSelectCount(this.getPollSelectCount(poll.getId(),i));
			qSelect.setSelectOptions(i);
			qList.add(qSelect);
			//计算投票总人数
			qr.setAnswerCount(qr.getAnswerCount()+qSelect.getSelectCount());
		}
		qr.setAnswerInfo(qList);
		return qr;
	}
	
	public QuestionRanking questionnaireResult(int questionid) throws ElException{
		QuestionRanking qr=new QuestionRanking();
		//1.首先获取该题信息以及该题有多少答案
		qr.setQuestion(new QuestionDaoImpl().getQuestionByid(questionid));
		List<QuestionSelect> qList=new ArrayList<QuestionSelect>();
		QuestionSelect qSelect=null;
		qr.setAnswerSum(qr.getQuestion().getSubject().split("-=SpEl=-").length);
		for(int i=0;i<qr.getAnswerSum();i++){
			qSelect=new QuestionSelect();
			qSelect.setSelectCount(this.getPollSelectCount(questionid,i));
			qSelect.setSelectOptions(i);
			qList.add(qSelect);
			//计算投票总人数
			qr.setAnswerCount(qr.getAnswerCount()+qSelect.getSelectCount());
		}
		qr.setAnswerInfo(qList);
		return qr;
	}
	
	/**
	 * 获取投票选择人的数量
	 * @param n
	 * @return
	 * @throws ElException
	 */
	public int getPollSelectCount(int pollId,int n) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			//1.首先获取该题信息以及该题有多少答案
			ps=ct.prepareStatement("select count(*) from poll_quizinfo where pollid=? and to_char(answer)=?");
			ps.setInt(1, pollId);
			ps.setString(2, n+"");
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取投票选择人的数量失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 修改用户为已投票
	 * @param pollid
	 * @param userid
	 * @throws ElException
	 */
	public void updateUserIsPoll(int pollid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update poll_assign set ispoll=1 where pollid=? and userid=?");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改用户为已投票失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 检测用户是否已经投票
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserIsPoll(int pollid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ispoll from poll_assign where pollid=? and userid=?");
			ps.setInt(1, pollid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==1){
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("检测用户是否已经投票失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	
}
