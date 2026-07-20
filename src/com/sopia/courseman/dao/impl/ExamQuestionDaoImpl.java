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
import com.sopia.courseman.dao.ExamQuestionDao;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.courseman.entities.QuestionSelect;
import com.sopia.questionman.dao.impl.QuestionDaoImpl;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;

public class ExamQuestionDaoImpl implements ExamQuestionDao {
	private static final Log logger = LogFactory.getLog(ExamQuestionDaoImpl.class);
	/**
	 * 考试试题统计 错题排行
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
		if(question.getQtype()==-1){
			sql+=" and q.qtype in(1,2,4,5) ";
		}else{
			sql+=" and q.qtype=? ";
		}
		try {
			ct=DBConnection.getConnection();
			QuestionLib qlib=new QuestionDaoImpl().getQuestionLibLRid(question.getQlib().getId());
			ps=ct.prepareStatement("select * from (select t.*,rownum rn from(select q.id,q.title,q.qtype,rq.ec,rq.cq,nvl((rq.ec /rq.cq),0) ecq  from (" +
					" select sq.qid,eq.ec,count(*) cq from ( " +
					" select count(*) ec,qid from study_questions where  myscore=0 group by qid) eq" +
					" right join study_questions sq on eq.qid=sq.qid group by sq.qid,eq.ec) rq right join question q" +
					" on q.id=rq.qid inner join question_lib qlib on q.qlibid=qlib.id where q.title like ? "+sql+" order by ecq desc,id ) t where rownum<=? ) where rn>=?");
			int n=0;
			ps.setString(1, "%"+question.getTitle()+"%");
			if(question.getQtype()>0){
				ps.setInt(2, question.getQtype());
				n++;
			}
			if(question.getQlib().getId()>0){
				ps.setInt(2+n, qlib.getLid());
				ps.setInt(3+n, qlib.getRid());
				ps.setInt(4+n, pageNow);
				ps.setInt(5+n, pageSize);
			}else{
				ps.setInt(2+n, pageNow);
				ps.setInt(3+n, pageSize);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				qr=new QuestionRanking();
				String strTitle=rs.getString(2);
				if(strTitle.length()>20){
					strTitle=strTitle.substring(0,20)+"...";
				}
				qr.setQuestion(new Question(rs.getInt(1),strTitle));
				qr.getQuestion().setQtype(rs.getInt(3));
				qr.setAnswerWrong(rs.getInt(4));
				qr.setAnswerCount(rs.getInt(5));
				qr.setAnswerWrongRate(getFloat.GetFloat(rs.getDouble(6)));
				qr.setAnswerTo(qr.getAnswerCount()-qr.getAnswerWrong());
				qrList.add(qr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("考试试题统计错题排行失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qrList;
	}
	/**
	 * 获取考试试题统计题量
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
		if(question.getQtype()==-1){
			sql+=" and q.qtype in(1,2,4,5) ";
		}else{
			sql+=" and q.qtype=? ";
		}
		try {
			ct=DBConnection.getConnection();
			QuestionLib qlib=new QuestionDaoImpl().getQuestionLibLRid(question.getQlib().getId());
			ps=ct.prepareStatement("select count(q.id) from question q left join question_lib qlib on q.qlibid=qlib.id where q.title like ?"+sql);
			ps.setString(1, "%"+question.getTitle()+"%");
			int n=0;
			if(question.getQtype()>0){
				ps.setInt(2, question.getQtype());
				n++;
			}
			if(question.getQlib().getId()>0){
				ps.setInt(2+n, qlib.getLid());
				ps.setInt(3+n, qlib.getRid());
			}
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取考试试题统计题量失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 统计考试中该试题的答案选择情况
	 * @param qid
	 * @return
	 * @throws ElException
	 */
	public QuestionRanking getExamQuestionInfo(int qid) throws ElException {
		QuestionRanking qr=new QuestionRanking();
		//1.首先获取该题信息以及该题有多少答案
		qr.setQuestion(new QuestionDaoImpl().getQuestionByid(qid));
		List<QuestionSelect> qList=new ArrayList<QuestionSelect>();
		QuestionSelect qSelect=null;
		if(qr.getQuestion().getQtype()==2){
			//单选题
			qr.setAnswerSum(qr.getQuestion().getSubject().split("-=SpEl=-").length);
			for(int i=0;i<qr.getAnswerSum();i++){
				qSelect=new QuestionSelect();
				qSelect.setSelectCount(this.getExamQuestionSelectCount(qid,i));
				qSelect.setSelectOptions(i);
				qList.add(qSelect);
				//计算答题总人数
				qr.setAnswerCount(qr.getAnswerCount()+qSelect.getSelectCount());
			}
		}else{
			//判断题
			qr.setAnswerSum(2);
			String selectOp="yes";
			for(int i=0;i<qr.getAnswerSum();i++){
				if(i==1){
					selectOp="no";
				}
				qSelect=new QuestionSelect();
				qSelect.setSelectCount(this.getExamQuestionSelectCount(qid,selectOp));
				qSelect.setSelectOptionsStr(selectOp);
				qSelect.setSelectOptions(-1);
				qList.add(qSelect);
				//计算答题总人数
				qr.setAnswerCount(qr.getAnswerCount()+qSelect.getSelectCount());
			}
		}
		//计算未答的人数
		qr.setAnswerWrong(this.getExamQuestionNoSelectCount(qid));
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
	public int getExamQuestionSelectCount(int qid,int n) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			//1.首先获取该题信息以及该题有多少答案
			ps=ct.prepareStatement("select count(*) from study_questions where qid=? and to_char(myanswer)=?");
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
	 * 获取题目选择人的数量
	 * @param n
	 * @return
	 * @throws ElException
	 */
	public int getExamQuestionSelectCount(int qid,String n) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			//1.首先获取该题信息以及该题有多少答案
			ps=ct.prepareStatement("select count(*) from study_questions where qid=? and to_char(myanswer)=?");
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
	public int getExamQuestionNoSelectCount(int qid) throws ElException {
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			//1.首先获取该题信息以及该题有多少答案
			ps=ct.prepareStatement("select count(*) from study_questions where qid=? and to_char(myanswer) is null");
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
