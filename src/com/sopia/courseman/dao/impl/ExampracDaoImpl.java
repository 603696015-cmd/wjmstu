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
import com.sopia.courseman.dao.ExampracDao;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.entities.MyExamPaper;

public class ExampracDaoImpl implements ExampracDao {
	private static final Log logger = LogFactory.getLog(ExampracDaoImpl.class);
	/**
	 * 练习列表页（已删除的不显示）
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public List<Examprac> listExampracDep(int begin, int end) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Examprac> xx = new ArrayList<Examprac>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select * from (select t.* ,rownum rn from (select epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,count(epra.userid) xx,el.username,dep.name,ep.id epid,ep.title eptitle from examprac epr left join exampaper ep on epr.epid=ep.id left join examprac_assign epra on epra.eprid= epr.id left join eluser el on epr.userid = el.id left join department dep on el.depid=dep.id where epr.valid!=4 group by epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,el.username,dep.name,ep.id,ep.title order by id)t where rownum <=?) where rn>=?");
			ps.setInt(1, begin);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				epr.setValid(rs.getInt(5));
				epr.setUsersize(rs.getInt(6));
				epr.setExamPaper(new ExamPaper(rs.getInt("epid"),rs.getString("eptitle")));
				ELUser user = new ELUser();
				user.setUsername(rs.getString(7));
				user.setDanwei(rs.getString(8));//借用， 此处为部门名称   
				epr.setUser(user);	
				xx.add(epr);
			}
		} catch (Exception e) {
			logger.error("练习列表页（已删除的不显示）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return xx;
	}
	/**
	 * 获取练习的数量（已删除的不显示）
	 */
	public int listExampracDepSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql="select count(id) from examprac epr where epr.valid!=4";
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取练习的数量（已删除的不显示）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	/**
	 * 添加练习分配给部门
	 * @return
	 * @throws ElException
	 */
	public void addExamprac_dep(int pracid,int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into examprac_dep(pracid,depid) values(?,?)");
			ps.setInt(1, pracid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加练习分配给部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 删除练习分配的所有部门
	 * @return
	 * @throws ElException
	 */
	public void delExamprac_dep(int pracid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from examprac_dep where pracid=?");
			ps.setInt(1, pracid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除练习分配的所有部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 练习列表页（显示全部）
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public List<Examprac> listExampracAll(int begin, int end) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Examprac> xx = new ArrayList<Examprac>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select * from (select t.* ,rownum rn from (select epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,count(epra.userid) xx,el.username,dep.name,ep.id epid,ep.title eptitle from examprac epr left join exampaper ep on epr.epid=ep.id left join examprac_assign epra on epra.eprid= epr.id left join eluser el on epr.userid = el.id left join department dep on el.depid=dep.id group by epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,el.username,dep.name,ep.id,ep.title order by id)t where rownum <=?) where rn>=?");
			ps.setInt(1, begin);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				epr.setValid(rs.getInt(5));
				epr.setUsersize(rs.getInt(6));
				epr.setExamPaper(new ExamPaper(rs.getInt("epid"),rs.getString("eptitle")));
				ELUser user = new ELUser();
				user.setUsername(rs.getString(7));
				user.setDanwei(rs.getString(8));//借用， 此处为部门名称   
				epr.setUser(user);	
				xx.add(epr);
			}
		} catch (Exception e) {
			logger.error("练习列表页（显示全部）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return xx;
	}
	/**
	 * 获取练习的数量（显示全部）
	 */
	public int listExampracAllSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql="select count(id) from examprac epr";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取练习的数量（显示全部）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	/**
	 * 检测组合搜索的参数
	 * @param examprac
	 * @param user
	 * @param sb
	 * @return
	 */
	private void checkParameter(Examprac examprac,ELUser user,StringBuffer sb,Vector<Object> pracs) throws ElException{
		if(examprac!=null){
			if(examprac.getId()>0){
				//sb.append(" and eq.pracid="+examprac.getId());
				sb.append(" and eq.pracid=?");
				pracs.add(examprac.getId());
			}
			if(examprac.getBegintime()!=null){
				//sb.append(" and eq.starttime>="+examprac.getBegintime().getTime());
				sb.append(" and eq.starttime>=?");
				pracs.add(examprac.getBegintime().getTime());
			}
			if(examprac.getEndtime()!=null){
//				sb.append(" and eu.shengri<=to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(examprac.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')");
				sb.append(" and eu.shengri<=?");
				pracs.add(examprac.getEndtime());
			}
		}
		if(user!=null){
			if(user.getRealname()!=null&&!user.getRealname().equals("")){
				//sb.append(" and eu.realname like '%"+user.getRealname()+"%'");
				sb.append(" and eu.realname like ?");
				pracs.add("%"+StringUtil.toLikeStr(user.getRealname())+"%");
			}
			if(user.getShenfenzheng()!=null&&!user.getShenfenzheng().equals("")){
				//sb.append(" and eu.shenfenzheng="+user.getShenfenzheng());
				sb.append(" and eu.shenfenzheng=?");
				pracs.add(user.getShenfenzheng());
			}
			if(user.getSex()!=null&&!user.getSex().equals("")){
				sb.append(" and eu.sex like '"+user.getSex()+"'");
				sb.append(" and eu.sex like ?");
				pracs.add(user.getSex());
			}
			if(user.getDepartment()!=null&&user.getDepartment().getId()>0){
				//sb.append(" and dep.id="+user.getDepartment().getId());
				//获取部门左右id
				Department department=new DepartmentDaoImpl().getDepLRid(user.getDepartment().getId());
				//sb.append(" and dep.lid>="+department.getLid()+" and dep.rid<="+department.getRid());
				sb.append(" and dep.lid>=? and dep.rid<=?");
				pracs.add(department.getLid());
				pracs.add(department.getRid());
			}
			if(user.getAge_start()>0){
				//sb.append(" and eu.shengri>="+user.getAge_startSr());
//				sb.append(" and eu.shengri<=to_date('"+ new SimpleDateFormat("yyyy-MM-dd")
//				.format(user.getAge_startSr())+ "','yyyy-MM-dd')");
				sb.append(" and eu.shengri<=?");
				pracs.add(user.getAge_startSr());
			}
			if(user.getAge_end()>0){
				//sb.append(" and eu.shengri>=to_date('"+ new SimpleDateFormat("yyyy-MM-dd")
//				.format(user.getAge_endSr())+ "','yyyy-MM-dd')");
				sb.append(" and eu.shengri>=?");
				pracs.add(user.getAge_endSr());
			}
			if(user.getJingzhong()>0){
				//sb.append(" and eu.jingzhong="+user.getJingzhong());
				sb.append(" and eu.jingzhong=?");
				pracs.add(user.getJingzhong());
			}
			if(user.getZhiwu()>0){
				//sb.append(" and eu.zhiwu="+user.getZhiwu());
				sb.append(" and eu.zhiwu=?");
				pracs.add(user.getZhiwu());
			}
			if(user.getZhiji()>0){
				//sb.append(" and eu.zhiji="+user.getZhiji());
				sb.append(" and eu.zhiji=?");
				pracs.add(user.getZhiji());
			}
			if(user.getDishi()>0){
				//sb.append(" and eu.dishi="+user.getDishi());
				sb.append(" and eu.dishi=?");
				pracs.add(user.getDishi());
			}
		}
	}
	
	/**
	 * 练习答卷统计（概况）
	 * @return
	 * @throws ElException
	 */
	public Examprac getExampracQuizOverview(Examprac examprac,ELUser user) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Examprac ep=new Examprac();
		StringBuffer sb=new StringBuffer("select count(*) from eprac_quizinfo eq left join eluser eu on eq.userid=eu.id left join department dep on eu.depid=dep.id where 1=1");
		Vector<Object> pracs=new Vector<Object>();
		this.checkParameter(examprac, user, sb,pracs);
		try {
			//String sql="select * from eprac_quizinfo eq left join eluser eu on eq.userid=eu.id left join department dep on eu.depid=dep.id";
			ct = DBConnection.getConnection();
			ep.setPass0_1(this.getExampracQuizOverviewInScore(sb.toString(), ct, 0.0f, 9.99f,pracs));
			ep.setPass1_2(this.getExampracQuizOverviewInScore(sb.toString(), ct, 10.0f, 19.99f,pracs));
			ep.setPass2_3(this.getExampracQuizOverviewInScore(sb.toString(), ct, 20.0f, 29.99f,pracs));
			ep.setPass3_4(this.getExampracQuizOverviewInScore(sb.toString(), ct, 30.0f, 39.99f,pracs));
			ep.setPass4_5(this.getExampracQuizOverviewInScore(sb.toString(), ct, 40.0f, 49.99f,pracs));
			ep.setPass5_6(this.getExampracQuizOverviewInScore(sb.toString(), ct, 50.0f, 59.99f,pracs));
			ep.setPass6_7(this.getExampracQuizOverviewInScore(sb.toString(), ct, 60.0f, 69.99f,pracs));
			ep.setPass7_8(this.getExampracQuizOverviewInScore(sb.toString(), ct, 70.0f, 79.99f,pracs));
			ep.setPass8_9(this.getExampracQuizOverviewInScore(sb.toString(), ct, 80.0f, 89.99f,pracs));
			ep.setPass9_(this.getExampracQuizOverviewInScore(sb.toString(), ct, 90.0f, 100f,pracs));
		} catch (Exception e) {
			logger.error("练习答卷统计（概况）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}
	/**
	 * 获取练习概况统计的分数段的人次
	 * @param l
	 * @param ct
	 * @param scoreStart
	 * @param scoreEnd
	 * @return
	 * @throws ElException
	 */
	public int getExampracQuizOverviewInScore(String sql,Connection ct,float scoreStars,float scoreEnd,Vector<Object> pracs) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int n = 0;
		try {
			ps = ct.prepareStatement(sql+" and eq.myscore>=? and eq.myscore<=?");
//			ps.setObject(1, "to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(new Timestamp(System.currentTimeMillis()))+ "','yyyy-MM-dd HH24:mi:ss')");
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
			logger.error("获取练习概况统计的分数段的人次失败！", e);
			throw new ElException(e);
		}
		return n;
	}
	
	/**
	 * 练习答卷统计（详情）
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> getExampracQuizDetail(Examprac examprac,ELUser elUser,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> epracList=new ArrayList<MyExamPaper>();
		StringBuffer sb=new StringBuffer("select * from (select t.*,rownum rn from (select ep.id epid,ep.title eptitle,eu.id euid,eu.username,eu.realname,eu.shenfenzheng,eu.sex,eu.shengri,eu.jingzhong,eu.zhiwu,eu.zhiji,eu.gangwei,eu.dishi,dep.id depid,dep.name depname,eq.id eqid,eq.myscore,eq.endtime from eprac_quizinfo eq left join examprac ep on eq.pracid=ep.id left join eluser eu on eq.userid=eu.id left join department dep on eu.depid=dep.id where 1=1");
		Vector<Object> pracs=new Vector<Object>();
		this.checkParameter(examprac, elUser, sb,pracs);
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
			logger.error("练习答卷统计（详情）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epracList;
	}
	
	/**
	 * 练习答卷统计（详情）数量
	 * @return
	 * @throws ElException
	 */
	public int getExampracQuizDetailSize(Examprac examprac,ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb=new StringBuffer("select count(*) from eprac_quizinfo eq left join examprac ep on eq.pracid=ep.id left join eluser eu on eq.userid=eu.id left join department dep on eu.depid=dep.id where 1=1");
		Vector<Object> pracs=new Vector<Object>();
		this.checkParameter(examprac, elUser, sb,pracs);
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
			logger.error("练习答卷统计（详情）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
}
