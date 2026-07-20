package com.sopia.courseman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.getFloat;
import com.sopia.courseman.dao.ExampracQuestionDao;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.courseman.entities.QuestionSelect;
import com.sopia.questionman.dao.impl.QuestionDaoImpl;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;

public class ExampracQuestionDaoImpl implements ExampracQuestionDao {
	private static final Log logger = LogFactory.getLog(ExampracQuestionDaoImpl.class);
	/**
	 * 练习试题统计 错题排行
	 */
	public List<QuestionRanking> listQuestionRanking(Question question,int pageNow,int pageSize) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<QuestionRanking> qrList=new ArrayList<QuestionRanking>();
		QuestionRanking qr=null;
		String sql="";
		if(question.getQlib().getId()>0){
			sql=" and qlib.lid>=? and qlib.rid<=? ";
		}
		try {
			ct=DBConnection.getConnection();
			QuestionLib qlib=new QuestionDaoImpl().getQuestionLibLRid(question.getQlib().getId());
			//String sql="select zq.id,zq.title,zq.cnt ,count (sqc.qid) ccnt,decode(count(sqc.qid),0,0,cnt/count(sqc.qid)) scnt from (select q.id,q.title,count(sq.qid) cnt from question q left join (select * from eprac_questions where myscore=0 ) sq on sq.qid = q.id where q.qtype=2 group  by q.id ,q.title) zq left join eprac_questions sqc on sqc.qid = zq.id group by zq.id,zq.title,zq.cnt order by scnt,ccnt asc";
			//  select zq.id,zq.title,zq.cnt ,count (sqc.qid) ccnt,decode(count(sqc.qid),0,0,cnt/count(sqc.qid)) scnt from (select q.id,q.title,count(sq.qid) cnt from question q left join (select * from eprac_questions where myscore=0 ) sq on sq.qid = q.id where q.qtype=2 group  by q.id ,q.title) zq left join eprac_questions sqc on sqc.qid = zq.id group by zq.id,zq.title,zq.cnt order by scnt desc
			ps=ct.prepareStatement("select * from (select t.*,rownum rn from( select zq.id,zq.title,zq.cnt ,count (sqc.qid) ccnt,decode(count(sqc.qid),0,0,cnt/count(sqc.qid)) scnt " +
					" from (select q.id,q.title,count(sq.qid) cnt from question q left join (select * from eprac_questions where myscore=0 ) sq on sq.qid = q.id " +
					" left join question_lib qlib on q.qlibid=qlib.id where q.qtype=? and q.title like ?"+sql+" group  by q.id ,q.title) zq " +
					" left join eprac_questions sqc on sqc.qid = zq.id group by zq.id,zq.title,zq.cnt order by scnt desc,cnt desc,ccnt desc,id asc ) t where rownum<=? ) where rn>=?");
			ps.setInt(1, question.getQtype());
			ps.setString(2, "%"+question.getTitle()+"%");
			if(question.getQlib().getId()>0){
				ps.setInt(3, qlib.getLid());
				ps.setInt(4, qlib.getRid());
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			}else{
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				qr=new QuestionRanking();
				String strTitle=rs.getString(2);
				if(strTitle.length()>20){
					strTitle=strTitle.substring(0,20)+"...";
				}
				qr.setQuestion(new Question(rs.getInt(1),strTitle));
				qr.setAnswerWrong(rs.getInt(3));
				qr.setAnswerCount(rs.getInt(4));
				qr.setAnswerWrongRate(getFloat.GetFloat(rs.getDouble(5)));
				qr.setAnswerTo(qr.getAnswerCount()-qr.getAnswerWrong());
				qrList.add(qr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("练习试题统计错题排行失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qrList;
	}
	/**
	 * 获取练习试题统计题量
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public int listQuestionRankingSize(Question question) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="";
		if(question.getQlib().getId()>0){
			sql=" and qlib.lid>=? and qlib.rid<=? ";
		}
		try {
			ct=DBConnection.getConnection();
			QuestionLib qlib=new QuestionDaoImpl().getQuestionLibLRid(question.getQlib().getId());
			ps=ct.prepareStatement("select count(q.id) from question q left join question_lib qlib on q.qlibid=qlib.id where q.qtype=? and q.title like ?"+sql);
			ps.setInt(1, question.getQtype());
			ps.setString(2, "%"+question.getTitle()+"%");
			if(question.getQlib().getId()>0){
				ps.setInt(3, qlib.getLid());
				ps.setInt(4, qlib.getRid());
			}
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取练习试题统计题量失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 统计练习中该试题的答案选择情况
	 * @param qid
	 * @return
	 * @throws ElException
	 */
	public QuestionRanking getEpracQuestionInfo(int qid) throws ElException {
		QuestionRanking qr=new QuestionRanking();
		//1.首先获取该题信息以及该题有多少答案
		qr.setQuestion(new QuestionDaoImpl().getQuestionByid(qid));
		qr.setAnswerSum(qr.getQuestion().getSubject().split("-=SpEl=-").length);
		List<QuestionSelect> qList=new ArrayList<QuestionSelect>(qr.getAnswerSum());
		QuestionSelect qSelect=null;
		for(int i=0;i<qr.getAnswerSum();i++){
			qSelect=new QuestionSelect();
			qSelect.setSelectCount(this.getEpracQuestionSelectCount(qid,i));
			qSelect.setSelectOptions(i);
			qList.add(qSelect);
			//计算答题总人数
			qr.setAnswerCount(qr.getAnswerCount()+qSelect.getSelectCount());
		}
		//计算未答的人数
		qr.setAnswerWrong(this.getEpracQuestionNoSelectCount(qid));
		qr.setAnswerCount(qr.getAnswerCount()+qr.getAnswerWrong());
		qr.setAnswerInfo(qList);
		return qr;
	}
	/**
	 * 获取题目选择人的数量
	 * @param n
	 * @return
	 * @throws ElException
	 */
	public int getEpracQuestionSelectCount(int qid,int n) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			//1.首先获取该题信息以及该题有多少答案
			ps=ct.prepareStatement("select count(*) from eprac_questions where qid=? and to_char(myanswer)=?");
			ps.setInt(1, qid);
			ps.setString(2, n+"-=SpEl=-");
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取题目选择人的数量失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 获取未答题人的数量
	 * @param n
	 * @return
	 * @throws ElException
	 */
	public int getEpracQuestionNoSelectCount(int qid) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			//1.首先获取该题信息以及该题有多少答案
			ps=ct.prepareStatement("select count(*) from eprac_questions where qid=? and to_char(myanswer) is null");
			ps.setInt(1, qid);
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取未答题人的数量失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
}
