package com.sopia.courseman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.StringUtil;
import com.sopia.courseman.dao.ExamDao;
import com.sopia.courseman.dao.ExampracDao;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.entities.MyExamPaper;

public class ExamDaoImpl implements ExamDao {
	private static final Log logger = LogFactory.getLog(ExamDaoImpl.class);
	/**
	 * 检测组合搜索的参数
	 * @param examprac
	 * @param user
	 * @param sb
	 * @return
	 */
	private void checkParameter(ExamRoom examRoom,ELUser user,StringBuffer sb,Vector<Object> pracs) throws ElException{
		if(examRoom!=null){
//			if(examRoom.getId()>0){
//				sb.append(" and eq.pracid=?");
//				pracs.add(examRoom.getId());
//			}
			if(examRoom.getEroomIds()!=null&&!"".equals(examRoom.getEroomIds())){
				sb.append(" and eq.roomid in("+examRoom.getEroomIds()+")");
			}
			if(examRoom.getBegintime()!=null){
				sb.append(" and eq.starttime>=?");
				pracs.add(examRoom.getBegintime().getTime());
			}
			if(examRoom.getEndtime()!=null){
				sb.append(" and eu.shengri<=?");
				pracs.add(examRoom.getEndtime());
			}
		}
		if(user!=null){
			if(user.getRealname()!=null&&!user.getRealname().equals("")){
				sb.append(" and eu.realname like ?");
				pracs.add("%"+StringUtil.toLikeStr(user.getRealname())+"%");
			}
			if(user.getShenfenzheng()!=null&&!user.getShenfenzheng().equals("")){
				sb.append(" and eu.shenfenzheng=?");
				pracs.add(user.getShenfenzheng());
			}
			if(user.getSex()!=null&&!user.getSex().equals("")){
				sb.append(" and eu.sex like '"+user.getSex()+"'");
				sb.append(" and eu.sex like ?");
				pracs.add(user.getSex());
			}
			if(user.getDepartment()!=null&&user.getDepartment().getId()>0){
				//获取部门左右id
				Department department=new DepartmentDaoImpl().getDepLRid(user.getDepartment().getId());
				sb.append(" and dep.lid>=? and dep.rid<=?");
				pracs.add(department.getLid());
				pracs.add(department.getRid());
			}
			if(user.getAge_start()>0){
				sb.append(" and eu.shengri<=?");
				pracs.add(user.getAge_startSr());
			}
			if(user.getAge_end()>0){
				sb.append(" and eu.shengri>=?");
				pracs.add(user.getAge_endSr());
			}
			if(user.getJingzhong()>0){
				sb.append(" and eu.jingzhong=?");
				pracs.add(user.getJingzhong());
			}
			if(user.getZhiwu()>0){
				sb.append(" and eu.zhiwu=?");
				pracs.add(user.getZhiwu());
			}
			if(user.getZhiji()>0){
				sb.append(" and eu.zhiji=?");
				pracs.add(user.getZhiji());
			}
			if(user.getDishi()>0){
				sb.append(" and eu.dishi=?");
				pracs.add(user.getDishi());
			}
		}
	}
	
	/**
	 * 考试答卷统计（概况）
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getExamQuizOverview(ExamRoom examRoom,ELUser user) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom ep=new ExamRoom();
		StringBuffer sb=new StringBuffer("select count(*) from study_quizinfo eq left join eluser eu on eq.userid=eu.id left join department dep on eu.depid=dep.id where 1=1");
		Vector<Object> pracs=new Vector<Object>();
		this.checkParameter(examRoom, user, sb,pracs);
		try {
			//String sql="select * from eprac_quizinfo eq left join eluser eu on eq.userid=eu.id left join department dep on eu.depid=dep.id";
			ct = DBConnection.getConnection();
			ep.setPass0_1(this.getExamQuizOverviewInScore(sb.toString(), ct, 0.0f, 9.99f,pracs));
			ep.setPass1_2(this.getExamQuizOverviewInScore(sb.toString(), ct, 10.0f, 19.99f,pracs));
			ep.setPass2_3(this.getExamQuizOverviewInScore(sb.toString(), ct, 20.0f, 29.99f,pracs));
			ep.setPass3_4(this.getExamQuizOverviewInScore(sb.toString(), ct, 30.0f, 39.99f,pracs));
			ep.setPass4_5(this.getExamQuizOverviewInScore(sb.toString(), ct, 40.0f, 49.99f,pracs));
			ep.setPass5_6(this.getExamQuizOverviewInScore(sb.toString(), ct, 50.0f, 59.99f,pracs));
			ep.setPass6_7(this.getExamQuizOverviewInScore(sb.toString(), ct, 60.0f, 69.99f,pracs));
			ep.setPass7_8(this.getExamQuizOverviewInScore(sb.toString(), ct, 70.0f, 79.99f,pracs));
			ep.setPass8_9(this.getExamQuizOverviewInScore(sb.toString(), ct, 80.0f, 89.99f,pracs));
			ep.setPass9_(this.getExamQuizOverviewInScore(sb.toString(), ct, 90.0f, 100f,pracs));
		} catch (Exception e) {
			logger.error("考试答卷统计（概况）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}
	/**
	 * 获取考试概况统计的分数段的人次
	 * @param l
	 * @param ct
	 * @param scoreStart
	 * @param scoreEnd
	 * @return
	 * @throws ElException
	 */
	private int getExamQuizOverviewInScore(String sql,Connection ct,float scoreStars,float scoreEnd,Vector<Object> pracs) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int n = 0;
		try {
			ps = ct.prepareStatement(sql+" and eq.myscore>=? and eq.myscore<=?");
			System.out.println(sql+" and eq.myscore>=? and eq.myscore<=?");
			for (int i = 0; i < pracs.size(); i++) {
				ps.setObject(i+1,pracs.get(i));
			}
			ps.setFloat(pracs.size()+1, scoreStars);
			ps.setFloat(pracs.size()+2, scoreEnd);
			rs = ps.executeQuery();
			if (rs.next()) {
				n = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试概况统计的分数段的人次失败！", e);
			throw new ElException(e);
		}
		return n;
	}
	
	/**
	 * 考试答卷统计（详情）
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> getExamQuizDetail(ExamRoom examRoom,ELUser elUser,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> epracList=new ArrayList<MyExamPaper>();
		StringBuffer sb=new StringBuffer("select * from (select t.*,rownum rn from ( " +
				" select ep.id epid,ep.title eptitle,eu.id euid,eu.username,eu.realname,eu.shenfenzheng,eu.sex,eu.shengri,eu.jingzhong,eu.zhiwu,eu.zhiji,eu.gangwei,eu.dishi,dep.id depid,dep.name depname,eq.id eqid,eq.myscore,eq.endtime from " +
				" study_quizinfo eq left join " +
				" exam_room ep on eq.roomid=ep.id left join " +
				" eluser eu on eq.userid=eu.id left join " +
				" department dep on eu.depid=dep.id where 1=1");
		Vector<Object> pracs=new Vector<Object>();
		this.checkParameter(examRoom, elUser, sb,pracs);
		try {
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement(sb.toString()+" order by eu.username,eqid) t where rownum<=? ) where rn>=? ");
			for (int i = 0; i < pracs.size(); i++) {
				ps.setObject(i+1,pracs.get(i));
			}
			ps.setInt(pracs.size()+1, pageNow);
			ps.setInt(pracs.size()+2, pageSize);
			rs=ps.executeQuery();
			MyExamPaper mep=null;
			Examprac ep=null;
			ELUser user=null;
			while(rs.next()){
				mep=new MyExamPaper(rs.getInt(16));
				ep=new Examprac(rs.getInt(1),rs.getString(2));
				user=new ELUser(rs.getInt(3),rs.getString(4),rs.getString(5));
				user.setShenfenzheng(rs.getString(6));
				user.setSex(rs.getString(7));
				user.setShengri(rs.getDate(8));
				user.setJingzhong(rs.getInt(9));
				user.setZhiwu(rs.getInt(10));
				user.setZhiji(rs.getInt(11));
				user.setGangwei(rs.getString(12));
				user.setDishi(rs.getInt(13));
				user.setDepartment(new Department(rs.getInt(14),rs.getString(15)));
				ep.setUser(user);
				mep.setExamprac(ep);
				mep.setScore(rs.getFloat(17));
				ep.setEndtime(rs.getTimestamp(18));
				epracList.add(mep);
			}
		} catch (Exception e) {
			logger.error("考试答卷统计（详情）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epracList;
	}
	
	/**
	 * 考试答卷统计（详情）数量
	 * @return
	 * @throws ElException
	 */
	public int getExamQuizDetailSize(ExamRoom examRoom,ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb=new StringBuffer("select count(*) from " +
				" study_quizinfo eq left join " +
				" exam_room ep on eq.roomid=ep.id left join " +
				" eluser eu on eq.userid=eu.id left join " +
				" department dep on eu.depid=dep.id where 1=1");
		Vector<Object> pracs=new Vector<Object>();
		this.checkParameter(examRoom, elUser, sb,pracs);
		try {
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement(sb.toString());
			for (int i = 0; i < pracs.size(); i++) {
				ps.setObject(i+1,pracs.get(i));
			}
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("考试答卷统计（详情）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
}
