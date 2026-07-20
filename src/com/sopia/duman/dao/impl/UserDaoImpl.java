package com.sopia.duman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.dao.impl.ElClTypeDaoImpl;
import com.sopia.classman.entities.ElClType;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SendMsgUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.impl.CourseTypeDaoImpl;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.MyLogin;
import com.sopia.duman.entities.Station;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.dao.impl.ForumAdminDaoImpl;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.dao.impl.KnowledgeDaoImpl;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.dao.impl.NewsDaoImpl;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.dao.impl.ExamPaperDaoImpl;
import com.sopia.questionman.dao.impl.QuestionDaoImpl;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.schedule.entities.Eluser;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.wordman.dao.WordDao;
import com.sopia.wordman.dao.impl.WordDaoImpl;
import com.sopia.wordman.entities.Word;

public class UserDaoImpl implements UserDao {
	private static final Log logger = LogFactory.getLog(UserDaoImpl.class);

	public boolean check(String username, String password) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_CHECK_PWD));
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 身份证验证
	 * 
	 * @param card
	 * @return
	 * @throws ElException
	 */
	public boolean checkCard(String card) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from eluser where shenfenzheng=? ");
			ps.setString(1, card);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("检查身份证失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ELUser query(String username) throws ElException {
		ELUser elUser = new ELUser();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_QUERY_BYUN));
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setPassword(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setRole(new ElRole(rs.getInt(5), rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(6)));
				elUser.setValid(rs.getBoolean(7));
			//	elUser.setAge(rs.getInt(9));
				elUser.setAdmin(rs.getInt(10));
				elUser.setActive(rs.getInt(11));
			}
		} catch (Exception e) {
			logger.error("通过用户名查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}

	public ELUser getUserById(int id) throws ElException {
		ELUser elUser = new ELUser();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			 ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_QUERY_BYID));
			ps = ct.prepareStatement("select eu.id,eu.username,eu.password,eu.realname,eu.role,eu.depid,dep.name,eu.valid,er.name, eu.sex, eu.xuhao,  eu.dishi,  eu.danwei,  eu.shenfenzheng, eu.shengri,  eu.zhiji,  eu.zhiwu,  eu.jingzhong,  eu.gangwei ,eu.jy,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ,eu.movephone,dep.lid,dep.rid ,eltx.touxiang,eu.email,eu.mac,eu.luntanjibie,eu.education,eu.specialty,eu.school,eu.staid,st.name as stname " +
					",eu.xuewei,eu.minzu,eu.jiguan,eu.canjiagongzuoshijian,eu.rusishijian,eu.xianrenzhishijian,eu.zhengzhimianmao,eu.pinyinjianxie,eu.chushengdi,eu.xianyuangongzu,eu.xianzhiwei,eu.xueli" +
					"  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role " +
			"left join  ELUSER_TOUXIANG  eltx on  eltx.id=eu.id left join station st on eu.staid=st.id  where eu.id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if (rs.next()) {
				// eu.id,eu.username,eu.password,eu.realname,eu.role,eu.depid,dep.name,eu.valid,er.name,
				// eu.sex
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setPassword(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setRole(new ElRole(rs.getInt(5), rs.getString(9)));
				elUser.setDepartment(new Department(rs.getInt(6), rs
						.getString(7)));
				elUser.setValid(rs.getBoolean(8));
				elUser.setSex(rs.getString(10));
				elUser.setXuhao(rs.getString(11));
				elUser.setDishi(rs.getInt(12));
				elUser.setDanwei(rs.getString(13));
				elUser.setShenfenzheng(rs.getString(14));
				elUser.setShengri(rs.getDate(15));
				elUser.setZhiji(rs.getInt(16));
				elUser.setZhiwu(rs.getInt(17));
				elUser.setJingzhong(rs.getInt(18));
				elUser.setGangwei(rs.getString(19));
				elUser.setJy(rs.getInt(20));
				elUser.setAge(rs.getInt(21));
				elUser.setMovephone(rs.getString(22));
				elUser.getDepartment().setLid(rs.getInt(23));
				elUser.getDepartment().setRid(rs.getInt(24));
				elUser.setTouxiang(rs.getString(25));
				elUser.setEmail(rs.getString(26));
				elUser.setMac(rs.getString("mac"));
				elUser.setLuntanjibie(rs.getInt("luntanjibie"));
				elUser.setEducation(rs.getInt("education"));
				elUser.setSpecialty(rs.getString("specialty"));
				elUser.setSchool(rs.getString("school"));
				elUser.setStaid(rs.getInt("staid"));
				elUser.setStation(new Station(rs.getInt("staid"),rs.getString("stname")));
				elUser.setXuewei(rs.getString("xuewei"));
				elUser.setMinzu(rs.getString("minzu"));
				elUser.setJiguan(rs.getString("jiguan"));
				elUser.setCanjiagongzuoshijian(rs.getDate("canjiagongzuoshijian"));
				elUser.setRusishijian(rs.getDate("rusishijian"));
				elUser.setXianrenzhishijian(rs.getDate("xianrenzhishijian"));
				elUser.setZhengzhimianmao(rs.getString("zhengzhimianmao"));
				elUser.setPinyinjianxie(rs.getString("pinyinjianxie"));
				elUser.setChushengdi(rs.getString("chushengdi"));
				elUser.setXianyuangongzu(rs.getString("xianyuangongzu"));
				elUser.setXianzhiwei(rs.getString("xianzhiwei"));
				elUser.setXueli(rs.getString("xueli"));
			}
		} catch (Exception e) {
			logger.error("通过用户id查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}

	public ELUser getUserByName(String name) throws ElException {
		ELUser elUser = new ELUser();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_QUERY_BYNAME));
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				// eu.id,eu.username,eu.password,eu.realname,eu.role,eu.depid,dep.name,eu.valid,er.name,
				// eu.sex
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setPassword(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setRole(new ElRole(rs.getInt(5), rs.getString(9)));
				elUser.setDepartment(new Department(rs.getInt(6), rs
						.getString(7)));
				elUser.setValid(rs.getBoolean(8));
				elUser.setSex(rs.getString(10));
				elUser.setXuhao(rs.getString(11));
				elUser.setDishi(rs.getInt(12));
				elUser.setDanwei(rs.getString(13));
				elUser.setShenfenzheng(rs.getString(14));
				elUser.setShengri(rs.getDate(15));
				elUser.setZhiji(rs.getInt(16));
				elUser.setZhiwu(rs.getInt(17));
				elUser.setJingzhong(rs.getInt(18));
				elUser.setGangwei(rs.getString(19));
				elUser.setJy(rs.getInt(20));
				elUser.setAge(rs.getInt(21));
			}
		} catch (Exception e) {
			logger.error("通过用户name查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}

	/*
	 * private void setUserxxInfo(int id, ELUser elUser) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * try { ct = DBConnection.getConnection(); ps = ct
	 * .prepareStatement("select xuehao,studentno,danweihao,realname," +
	 * "username,password,kuaijihao,renyuanleibie,zhichengleibie,zhichengjibie,lianxifangshi," +
	 * "sex,minzu,peixunleibie,shifouzaizhi,suozaigangwei,biyeyuanxiao,biyeshijian,suoxuezhuanye," +
	 * "xueli,xuewei,zhichenghao,zhiwupinrenriqi,zhichengquderiqi,beizhu,headphoto
	 * from eluser where id = ?"); ps.setInt(1, id); rs = ps.executeQuery(); if
	 * (rs.next()) { elUser.setRealname(rs.getString(4));
	 * elUser.setUsername(rs.getString(5)); elUser.setPassword(rs.getString(6)); } }
	 * catch (Exception e) { logger.error("通过用户id查询用户失败！", e); throw new
	 * ElException(e); } finally { DBConnection.closeConnectInfo(ct, ps, rs); } }
	 */
	/**
	 * 用户添加 TODO 全部信息
	 * 
	 * @param elUser
	 * @throws ElException
	 */
	public void userAdd(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.USER_ADD));
			// username,password,realname,role,depid,valid, sex,
			// xuhao, dishi, danwei, shenfenzheng, shengri,
			// zhiji, zhiwu, jingzhong, gangwei ,jy
			ps.setString(1, elUser.getUsername());
			ps.setString(2, elUser.getPassword());
			ps.setString(3, elUser.getRealname());
			ps.setInt(4, elUser.getRole().getId());
			ps.setInt(5, elUser.getDepartment().getId());
			ps.setBoolean(6, elUser.getValid());
			ps.setString(7, elUser.getSex());
			ps.setString(8, elUser.getXuhao());
			ps.setInt(9, elUser.getDishi());
			ps.setString(10, elUser.getDanwei());
			ps.setString(11, elUser.getShenfenzheng());
			ps.setDate(12, elUser.getShengri());
			ps.setInt(13, elUser.getZhiji());
			ps.setInt(14, elUser.getZhiwu());
			ps.setInt(15, elUser.getJingzhong());
			ps.setString(16, elUser.getGangwei());
			ps.setInt(17, elUser.getJy());

			ps.executeUpdate();
			ps.close();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('eluser') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps.close();
				ps = ct
						.prepareStatement("select eluser_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next()) {
				elUser.setId(rs.getInt(1));
				this.usertouxiang(elUser.getId(), elUser.getTouxiang());
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		/*
		 * PreparedStatement ps = null; ResultSet rs = null; Connection ct =
		 * null; try { ct = DBConnection.getConnection(); ps = ct
		 * .prepareStatement("insert into eluser
		 * (xuehao,studentno,danweihao,realname," +
		 * "username,password,kuaijihao,renyuanleibie,zhichengleibie,zhichengjibie,lianxifangshi," +
		 * "sex,minzu,peixunleibie,shifouzaizhi,suozaigangwei,biyeyuanxiao,biyeshijian,suoxuezhuanye," +
		 * "xueli,xuewei,zhichenghao,zhiwupinrenriqi,zhichengquderiqi,beizhu,depid,email)
		 * values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		 * ps.setString(4, elUser.getRealname()); ps.setString(5,
		 * elUser.getUsername()); ps.setString(6, elUser.getPassword());
		 * ps.setString(12, elUser.getSex()); ps.executeUpdate(); if
		 * ("mssql".equals(SystemConfOp
		 * .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) { ps = ct
		 * .prepareStatement("SELECT IDENT_CURRENT('eluser') AS id"); rs =
		 * ps.executeQuery(); } else if ("mysql".equals(SystemConfOp
		 * .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) { rs =
		 * ps.getGeneratedKeys(); }
		 * 
		 * if (rs.next()) elUser.setId(rs.getInt(1)); } catch (Exception e) {
		 * logger.error("插入用户失败！", e); throw new ElException(e); } finally {
		 * DBConnection.closeConnectInfo(ct, ps, rs); }
		 */
	}

	public int insert(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			// ps =
			// ct.prepareStatement(ElQuerySql.getSQL(DUConstants.USER_ADD));
			ps = ct
					.prepareStatement("insert into ELUSER(username,password,realname,role,depid,valid, sex, xuhao,  dishi,  danwei,  shenfenzheng, shengri,  zhiji,  zhiwu,  jingzhong,  gangwei ,jy,movephone,email,luntanjibie,staid" +
							",school,xuewei,Education,specialty,minzu,jiguan,canjiagongzuoshijian,rusishijian,xianrenzhishijian" +
							",zhengzhimianmao,pinyinjianxie,chushengdi,xianyuangongzu,xianzhiwei,zhideng,xueli ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
							",?,?,?,?,?,?,?,?,?,?,?,?,?)");
			// username,password,realname,role,depid,valid, sex,
			// xuhao, dishi, danwei, shenfenzheng, shengri,
			// zhiji, zhiwu, jingzhong, gangwei ,jy
			ps.setString(1, elUser.getUsername());
			ps.setString(2, elUser.getPassword());
			ps.setString(3, elUser.getRealname());
			ps.setInt(4, elUser.getRole().getId());
			ps.setInt(5, elUser.getDepartment().getId());
			ps.setBoolean(6, elUser.getValid());
			ps.setString(7, elUser.getSex());
			ps.setString(8, elUser.getXuhao());
			ps.setInt(9, elUser.getDishi());
			ps.setString(10, elUser.getDanwei());
			ps.setString(11, elUser.getShenfenzheng());
			ps.setDate(12, elUser.getShengri());
			ps.setInt(13, elUser.getZhiji());
			ps.setInt(14, elUser.getZhiwu());
			ps.setInt(15, elUser.getJingzhong());
			ps.setString(16, elUser.getGangwei());
			ps.setInt(17, elUser.getJy());
			ps.setString(18, elUser.getMovephone());
			ps.setString(19, elUser.getEmail());
			ps.setInt(20, elUser.getLuntanjibie());
			ps.setInt(21, 16371);
			
			ps.setString(22, elUser.getSchool());
			ps.setString(23, elUser.getXuewei());
			ps.setInt(24, elUser.getEducation());
			ps.setString(25, elUser.getSpecialty());
			ps.setString(26, elUser.getMinzu());
			ps.setString(27, elUser.getJiguan());
			ps.setDate(28, elUser.getCanjiagongzuoshijian());
			ps.setDate(29, elUser.getRusishijian());
			ps.setDate(30, elUser.getXianrenzhishijian());
			ps.setString(31, elUser.getZhengzhimianmao());
			ps.setString(32, elUser.getPinyinjianxie());
			ps.setString(33, elUser.getChushengdi());
			ps.setString(34, elUser.getXianyuangongzu());
		//	ps.setString(35,elUser.getXianzhiwei());
			ps.setString(35, "注册岗位");
			ps.setString(36, elUser.getZhideng());
			ps.setString(37, elUser.getXueli());
			ps.executeUpdate();
			ps = ct.prepareStatement("select eluser_sequence.currval from dual");
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	public int insert_cisco(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into ELUSER(" +
							"username,password,realname,role,depid," +
							"valid, sex,  dishi,  danwei,shenfenzheng,  " +
							" shengri,  zhiji,  zhiwu,  jingzhong,movephone,  " +
							"email,staid,danweiaddress,xianzhiwei,headphoto,specialty,school,userno,phone " +
							" ) values(" +
							"?,?,?,?,?," +
							"?,?,?,?,?," +
							"?,?,?,?,?," +
							"?,?,?,?,?,?,?,?,?)");
			ps.setString(1,  elUser.getUsername());
			ps.setString(2, elUser.getPassword());
			ps.setString(3, elUser.getRealname());
			ps.setInt(4, elUser.getRole().getId());
			ps.setInt(5, elUser.getDepartment().getId());
			
			ps.setBoolean(6, elUser.getValid());
			ps.setString(7, elUser.getSex());
			ps.setInt(8, elUser.getDishi());
			ps.setString(9, elUser.getDanwei()==null?"":elUser.getDanwei());
			ps.setString(10, elUser.getShenfenzheng());
			
			
			ps.setDate(11, elUser.getShengri());
			ps.setInt(12, elUser.getZhiji());
			ps.setInt(13, elUser.getZhiwu());
			ps.setInt(14, elUser.getJingzhong());
			ps.setString(15, elUser.getMovephone()==null?"":elUser.getMovephone());
			
			
			ps.setString(16, elUser.getEmail()==null?"":elUser.getEmail());
			ps.setInt(17, elUser.getStation().getId());
			ps.setString(18, elUser.getDanweiaddress()==null?"":elUser.getDanweiaddress());
			ps.setString(19, elUser.getXianzhiwei());
			ps.setString(20, elUser.getTouxiang());
			ps.setString(21, elUser.getSpecialty()==null?"":elUser.getSpecialty());
			ps.setString(22, elUser.getSchool()==null?"":elUser.getSchool());
			ps.setString(23, elUser.getUserno()==null?"":elUser.getUserno());
			ps.setString(24, elUser.getPhone());
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select eluser_sequence.currval from dual");
			rs = ps.executeQuery();
			
			if (rs.next())
				elUser.setId(rs.getInt(1));
			this.usertouxiang(elUser.getId(), elUser.getTouxiang());
			
			
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eluser_SEQUENCE.currval from dual");
			rs = ps.executeQuery();
			
			if(rs.next()){
				id = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	public int insert_jg(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into ELUSER(" +
							"username,password,realname,role,depid," +
							"valid, sex,  dishi,  danwei,shenfenzheng,  " +
							" shengri,  zhiji,  zhiwu,  jingzhong,movephone,  " +
							"email,staid,danweiaddress,xianzhiwei,headphoto,specialty,school,userno,huiyuanleixing,luntanbankuai,bankuaimingcheng " +
							" ) values(" +
							"?,?,?,?,?," +
							"?,?,?,?,?," +
							"?,?,?,?,?," +
							"?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1,  elUser.getUsername());
			ps.setString(2, elUser.getPassword());
			ps.setString(3, elUser.getRealname());
			ps.setInt(4, elUser.getRole().getId());
			ps.setInt(5, elUser.getDepartment().getId());
			
			ps.setBoolean(6, elUser.getValid());
			ps.setString(7, elUser.getSex());
			ps.setInt(8, elUser.getDishi());
			ps.setString(9, elUser.getDanwei()==null?"":elUser.getDanwei());
			ps.setString(10, elUser.getShenfenzheng());
			
			
			ps.setDate(11, elUser.getShengri());
			ps.setInt(12, elUser.getZhiji());
			ps.setInt(13, elUser.getZhiwu());
			ps.setInt(14, elUser.getJingzhong());
			ps.setString(15, elUser.getMovephone()==null?"":elUser.getMovephone());
			
			
			ps.setString(16, elUser.getEmail()==null?"":elUser.getEmail());
			ps.setInt(17, elUser.getStation().getId());
			ps.setString(18, elUser.getDanweiaddress()==null?"":elUser.getDanweiaddress());
			ps.setString(19, elUser.getXianzhiwei());
			ps.setString(20, elUser.getTouxiang());
			ps.setString(21, elUser.getSpecialty());
			ps.setString(22, elUser.getSchool());
			ps.setString(23, elUser.getUserno());
			ps.setInt(24, elUser.getHuiyuanleixing());
			ps.setInt(25, elUser.getLuntanbankuai());
			ps.setString(26, elUser.getBankuaimingcheng());
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select eluser_sequence.currval from dual");
			rs = ps.executeQuery();
			
			if (rs.next())
				elUser.setId(rs.getInt(1));
			this.usertouxiang(elUser.getId(), elUser.getTouxiang());
			
			
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eluser_SEQUENCE.currval from dual");
			rs = ps.executeQuery();
			
			if(rs.next()){
				id = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	public int insert1(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int returnValue = 0;
		try {
			ct = DBConnection.getConnection();
			// ps =
			// ct.prepareStatement(ElQuerySql.getSQL(DUConstants.USER_ADD));
			ps = ct
					.prepareStatement("insert into ELUSER(username,password,realname,role,depid,valid,   dishi,    zhiji,  zhiwu,  jingzhong,  email ,sex,usertype,luntanjibie) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			// username,password,realname,role,depid,valid, sex,
			// xuhao, dishi, danwei, shenfenzheng, shengri,
			// zhiji, zhiwu, jingzhong, gangwei ,jy
			ps.setString(1, elUser.getUsername());
			ps.setString(2, elUser.getPassword());
			ps.setString(3, elUser.getRealname());
			ps.setInt(4, elUser.getRole().getId());
			ps.setInt(5, elUser.getDepartment().getId());
			ps.setBoolean(6, elUser.getValid());
			ps.setInt(7, elUser.getDishi());
			ps.setInt(8, elUser.getZhiji());
			ps.setInt(9, elUser.getZhiwu());
			ps.setInt(10, elUser.getJingzhong());
			ps.setString(11, elUser.getEmail());
			ps.setString(12, elUser.getSex());
			ps.setInt(13, elUser.getUsertype());
			ps.setInt(14, elUser.getLuntanjibie());
			ps.executeUpdate();
			ps.close();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('eluser') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select eluser_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				elUser.setId(rs.getInt(1));
			this.usertouxiang(elUser.getId(), elUser.getTouxiang());
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eluser_SEQUENCE.currval from dual");
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}
	
	public void insert2(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps =
			// ct.prepareStatement(ElQuerySql.getSQL(DUConstants.USER_ADD));
			ps = ct
					.prepareStatement("insert into ELUSER(username,password,realname,role,depid,valid, sex, xuhao,  dishi,  danwei,  shenfenzheng, shengri,  zhiji,  zhiwu,  jingzhong,  gangwei ,jy,movephone,email ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			// username,password,realname,role,depid,valid, sex,
			// xuhao, dishi, danwei, shenfenzheng, shengri,
			// zhiji, zhiwu, jingzhong, gangwei ,jy
			ps.setString(1, elUser.getUsername());
			ps.setString(2, elUser.getPassword());
			ps.setString(3, elUser.getRealname());
			ps.setInt(4, elUser.getRole().getId());
			ps.setInt(5, elUser.getDepartment().getId());
			ps.setBoolean(6, elUser.getValid());
			ps.setString(7, elUser.getSex());
			ps.setString(8, elUser.getXuhao());
			ps.setInt(9, elUser.getDishi());
			ps.setString(10, elUser.getDanwei());
			ps.setString(11, elUser.getShenfenzheng());
			ps.setDate(12, elUser.getShengri());
			ps.setInt(13, elUser.getZhiji());
			ps.setInt(14, elUser.getZhiwu());
			ps.setInt(15, elUser.getJingzhong());
			ps.setString(16, elUser.getGangwei());
			ps.setInt(17, elUser.getJy());
			ps.setString(18, elUser.getMovephone());
			ps.setString(19, elUser.getEmail());
			ps.executeUpdate();
			ps.close();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('eluser') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select eluser_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				elUser.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	private  void  usertouxiang(int userid,String  touxiang) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String  sql = "insert into eluser_touxiang  values(?,?) ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setString(2, touxiang);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("添加用户头像失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	private  void  deletetouxiang(int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String  sql = "delete from eluser_touxiang where id=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("删除用户头像失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void update(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps =
			// ct.prepareStatement(ElQuerySql.getSQL(DUConstants.USER_ALTER));
			// ps = ct.prepareStatement("update ELUSER set
			// password=?,realname=?,role=?,depid=?,valid=?, sex=?, xuhao=?,
			// dishi=?, danwei=?, shenfenzheng=?, shengri=?, zhiji=?, zhiwu=?,
			// jingzhong=?, gangwei =?,jy =?,movephone=? where id = ?");
			ps = ct
					.prepareStatement("update ELUSER set realname=?,role=?,depid=?,valid=?, sex=?, xuhao=?,  dishi=?,  danwei=?,  shenfenzheng=?, shengri=?,  zhiji=?,  zhiwu=?,  jingzhong=?,  gangwei =?,jy =?,movephone=?,email=?,luntanjibie=?" +
							",specialty=?,school=?,Education=?,xuewei=?,minzu=?,jiguan=?,canjiagongzuoshijian=?,rusishijian=?,zhengzhimianmao=?,pinyinjianxie=?" +
							",chushengdi=?,xianrenzhishijian=?,xianyuangongzu=?,xianzhiwei=?,xueli=?,staid=?,username=?" +
							" where id = ?");
			ps.setString(1, elUser.getRealname());
			ps.setInt(2, elUser.getRole().getId());
			ps.setInt(3, elUser.getDepartment().getId());
			ps.setBoolean(4, elUser.getValid());
			ps.setString(5, elUser.getSex());
			ps.setString(6, elUser.getXuhao());
			ps.setInt(7, elUser.getDishi());
			ps.setString(8, elUser.getDanwei());
			ps.setString(9, elUser.getShenfenzheng());
			ps.setDate(10, elUser.getShengri());
			ps.setInt(11, elUser.getZhiji());
			ps.setInt(12, elUser.getZhiwu());
			ps.setInt(13, elUser.getJingzhong());
			ps.setString(14, elUser.getGangwei());
			ps.setInt(15, elUser.getJy());
			ps.setString(16, elUser.getMovephone());
			ps.setString(17, elUser.getEmail());
			ps.setInt(18, elUser.getLuntanjibie());
			
			ps.setString(19, elUser.getSpecialty());
			ps.setString(20, elUser.getSchool());
			ps.setInt(21, elUser.getEducation());
			ps.setString(22, elUser.getXuewei());
			ps.setString(23, elUser.getMinzu());
			ps.setString(24, elUser.getJiguan());
			ps.setDate(25, elUser.getCanjiagongzuoshijian());
			ps.setDate(26, elUser.getRusishijian());
			ps.setString(27, elUser.getZhengzhimianmao());
			ps.setString(28, elUser.getPinyinjianxie());
			ps.setString(29, elUser.getChushengdi());
			ps.setDate(30, elUser.getXianrenzhishijian());
			ps.setString(31, elUser.getXianyuangongzu());
		//	ps.setString(32, elUser.getXianzhiwei());
			ps.setString(32, elUser.getStation().getName());
			ps.setString(33, elUser.getXueli());
			ps.setInt(34, elUser.getStation().getId());
			ps.setString(35, elUser.getUsername());
			ps.setInt(36, elUser.getId());
			
			updusertouxiang(elUser.getId(),elUser.getTouxiang());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 修改用户头像
	 * @throws ElException 
	 */
	private  void   updusertouxiang(int  userid,String  touxiang) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String  sql = "select count(1) from  eluser_touxiang where id=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1,userid);
			rs=ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0){
			  sql = "update eluser_touxiang set touxiang=?  where id=? ";
				ps=ct.prepareStatement(sql);
				ps.setString(1,  touxiang);
				ps.setInt(2,userid);
				ps.executeUpdate();
			}else{
				this.usertouxiang(userid, touxiang);
			}
			
		} catch (Exception e) {
			logger.error("添加用户头像失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	/**
	 * 更该用户的开通状态
	 * 
	 * @param userid
	 * @param valid
	 * @throws ElException
	 */
	public void updateValid(int userid, int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update eluser set valid=? where id=? ");
			ps.setInt(1, valid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * private void alterUserxxInfo(ELUser elUser) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * try { ct = DBConnection.getConnection(); ps = ct
	 * .prepareStatement("update eluser set
	 * xuehao=?,studentno=?,danweihao=?,realname=?," + "
	 * kuaijihao=?,renyuanleibie=?,zhichengleibie=?,zhichengjibie=?,lianxifangshi=?," +
	 * "sex=?,minzu=?,peixunleibie=?,shifouzaizhi=?,suozaigangwei=?,biyeyuanxiao=?,biyeshijian=?,suoxuezhuanye=?," +
	 * "xueli=?,xuewei=?,zhichenghao=?,zhiwupinrenriqi=?,zhichengquderiqi=?,beizhu=?,depid =?
	 * where id = ?"); ps.setString(1, elUser.getXuehao()); ps.setString(2,
	 * elUser.getStudentno()); ps.setString(3, elUser.getDanweihao());
	 * ps.setString(4, elUser.getRealname()); ps.setString(5,
	 * elUser.getKuaijihao()); ps.setString(6, elUser.getRenyuanleibie());
	 * ps.setString(7, elUser.getZhichengleibie()); ps.setString(8,
	 * elUser.getZhichengjibie()); ps.setString(9, elUser.getLianxifangshi());
	 * ps.setString(10, elUser.getSex()); ps.setString(11, elUser.getMinzu());
	 * ps.setString(12, elUser.getPeixunleibie()); ps.setString(13,
	 * elUser.getShifouzaizhi()); ps.setString(14, elUser.getSuozaigangwei());
	 * ps.setString(15, elUser.getBiyeyuanxiao()); ps.setDate(16,
	 * elUser.getBiyeshijian()); ps.setString(17, elUser.getSuoxuezhuanye());
	 * ps.setString(18, elUser.getXueli()); ps.setString(19,
	 * elUser.getXuewei()); ps.setString(20, elUser.getZhichenghao());
	 * ps.setDate(21, elUser.getZhiwupinrenriqi()); ps.setDate(22,
	 * elUser.getZhichengquderiqi()); ps.setString(23, elUser.getBeizhu());
	 * ps.setInt(24, elUser.getDepartment().getId()); ps.setInt(25,
	 * elUser.getId()); ps.executeUpdate();
	 * 
	 * 
	 * if ("mssql".equals(SystemConfOp
	 * .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) { ps = ct
	 * .prepareStatement("SELECT IDENT_CURRENT('eluser') AS id"); rs =
	 * ps.executeQuery(); } else if ("mysql".equals(SystemConfOp
	 * .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) { rs =
	 * ps.getGeneratedKeys(); } if (rs.next()) elUser.setId(rs.getInt(1)); }
	 * catch (Exception e) { logger.error("插入用户失败！", e); throw new
	 * ElException(e); } finally { DBConnection.closeConnectInfo(ct, ps, rs); } }
	 */
	/**
	 * (non-Javadoc) 获取不指定角色的用户
	 * 
	 * @see com.sopia.duman.dao.UserDao#getUserByDepId(int, int,
	 *      com.sopia.duman.entities.ELUser, int, int)
	 */
//	public List<ELUser> getUserByDepId(int depid, int subdep, ELUser eu,
//			int pageNow, int pageSize) throws ElException {
//		List<ELUser> eus = new ArrayList<ELUser>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			String jz = "";
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//			}
//			ct = DBConnection.getConnection();
//			// ElConstants.SUBOP_YES包含下级。
//			String sql = "";
//			if (subdep == ElConstants.SUBOP_YES) {
//				Department dep = new Department();
//				// 获取 部门的左右值
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//				ps.setInt(1, depid);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					dep.setId(rs.getInt(1));
//					dep.setLid(rs.getInt(2));
//					dep.setRid(rs.getInt(3));
//				}
//
//				ps.close();
//				rs.close();
//				// ps = ct.prepareStatement(ElQuerySql
//				// .getSQL(DUConstants.USER_QUERY_SUBS_BYDEPIDANDOS));
//				sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_   from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id"
//						+ " where eu.username like ? and eu.realname like ? and eu.sex like ? "// and
//						// eu.jingzhong
//						// like
//						// ?
//						+ con
//						+ "  and dep.lid >=? and dep.rid<=? )t1 where rownum <=? ) where rn >=?";
//				ps = ct.prepareStatement(sql);
//				/**
//				 * 1.点击USER_QUERY_SUBS_BYDEPIDANDOS
//				 * 找到了user.query.subs.bydepidandos 2.如果系统是oracle
//				 * 那么请在常量（*Constants.java)同级中找到o_querySqls.sql
//				 * 3.可以看到user.query.subs.bydepidandos
//				 * 对应的sql语句。如果实在没有请Ctrl+h搜索文件。 4.select * from (select
//				 * t1.*,rownum rn from( select eu.id euid,eu.username,
//				 * eu.realname,eu.role,dep.id depid,dep.name
//				 * depname,eu.valid,er.name ername from ELUSER eu left join
//				 * elrole er on er.id = eu.role left join DEPARTMENT dep on
//				 * eu.depid = dep.id \ where eu.username like ? and eu.realname
//				 * like ? and dep.lid >=? and dep.rid<=? )t1 where rownum <=? )
//				 * where rn >=? 5 看到dep.lid>=? an dep.rid<=? 这里就是应用到了左右值的树特性。
//				 */
//
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + sex + "%");
//				// ps.setString(4, "%" + jz + "%");
//				ps.setInt(4, dep.getLid());
//				ps.setInt(5, dep.getRid());
//				ps.setInt(6, pageNow);
//				ps.setInt(7, pageSize);
//			} else {
//				// ps = ct.prepareStatement(ElQuerySql
//				// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
//				sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//						+ "where eu.username like ? and eu.realname like ? and eu.sex like ? "// and
//						// eu.jingzhong
//						// like
//						// ?
//						+ con
//						+ " and dep.id=?)t1 where rownum <=? ) where rn >=?";
//				ps = ct.prepareStatement(sql);
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + sex + "%");
//				// ps.setString(4, "%" + jz + "%");
//				ps.setInt(4, depid);
//				ps.setInt(5, pageNow);
//				ps.setInt(6, pageSize);
//			}
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ELUser elUser = new ELUser();
//				elUser.setId(rs.getInt(1));
//				elUser.setUsername(rs.getString(2));
//				elUser.setRealname(rs.getString(3));
//				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
//				elUser.setDepartment(new Department(rs.getInt(5), rs
//						.getString(6)));
//				elUser.setValid(rs.getBoolean(7));
//				elUser.setSex(rs.getString(9));
//				elUser.setJingzhong(rs.getString(10));
//				elUser.setShengri(rs.getDate(11));
//				elUser.setAge(rs.getInt(12));
//				eus.add(elUser);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return eus;
//	}

	/**
	 * (non-Javadoc) 获取不指定角色的用户
	 * 
	 * @see com.sopia.duman.dao.UserDao#getUserByDepId(int, int,
	 *      com.sopia.duman.entities.ELUser, int, int)
	 */
	public List<ELUser> getUserByDepId(int depid, int subdep, ELUser eu)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String sex = "";
//			int jz = 0;
			String con = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
//				if (eu.getJingzhong()>0)
//					jz = eu.getJingzhong();
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}
			ct = DBConnection.getConnection();
			// ElConstants.SUBOP_YES包含下级。
			String sql = "";
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}

				ps.close();
				rs.close();
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_SUBS_BYDEPIDANDOS));
				sql = "select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_   from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id"
						+ " where eu.username like ? and eu.realname like ? and eu.sex like ? "// and
						// eu.jingzhong
						// like
						// ?
						+ con + "  and dep.lid >=? and dep.rid<=? ";
				ps = ct.prepareStatement(sql);
				/**
				 * 1.点击USER_QUERY_SUBS_BYDEPIDANDOS
				 * 找到了user.query.subs.bydepidandos 2.如果系统是oracle
				 * 那么请在常量（*Constants.java)同级中找到o_querySqls.sql
				 * 3.可以看到user.query.subs.bydepidandos
				 * 对应的sql语句。如果实在没有请Ctrl+h搜索文件。 4.select * from (select
				 * t1.*,rownum rn from( select eu.id euid,eu.username,
				 * eu.realname,eu.role,dep.id depid,dep.name
				 * depname,eu.valid,er.name ername from ELUSER eu left join
				 * elrole er on er.id = eu.role left join DEPARTMENT dep on
				 * eu.depid = dep.id \ where eu.username like ? and eu.realname
				 * like ? and dep.lid >=? and dep.rid<=? )t1 where rownum <=? )
				 * where rn >=? 5 看到dep.lid>=? an dep.rid<=? 这里就是应用到了左右值的树特性。
				 */

				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
				// ps.setString(4, "%" + jz + "%");
				ps.setInt(4, dep.getLid());
				ps.setInt(5, dep.getRid());
			} else {
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
				sql = " select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
						+ "where eu.username like ? and eu.realname like ? and eu.sex like ? "// and
						// eu.jingzhong
						// like
						// ?
						+ con + " and dep.id=? ";
				ps = ct.prepareStatement(sql);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
				// ps.setString(4, "%" + jz + "%");
				ps.setInt(4, depid);
			}
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

	/**
	 * (non-Javadoc) 获取不指定角色的用户
	 * 
	 * @see com.sopia.duman.dao.UserDao#getUserByDepId(int, int,
	 *      com.sopia.duman.entities.ELUser, int, int)
	 */
	// public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
	// int pageNow, int pageSize) throws ElException {
	// List<ELUser> eus = new ArrayList<ELUser>();
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// String username = "";
	// String realname = "";
	// String sex = "";
	// String jz = "";
	// String con = "";
	// String isValid = "";
	// if (null != eu) {
	// if (null != eu.getUsername())
	// username = eu.getUsername().trim();
	// if (null != eu.getRealname())
	// realname = eu.getRealname().trim();
	// if (null != eu.getSex())
	// sex = eu.getSex().trim();
	// if (null != eu.getJingzhong())
	// jz = eu.getJingzhong().trim();
	// if (eu.getShengri() != null)
	// con = con
	// + " and eu.shengri >=to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(eu.getShengri())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (eu.getShengri_end() != null)
	// con = con
	// + " and eu.shengri <= to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(eu.getShengri_end())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (eu.getNov() != 1) {
	// isValid = " and eu.valid=? ";
	// }
	// }
	// ct = DBConnection.getConnection();
	// // ElConstants.SUBOP_YES包含下级。
	// String sql = "";
	// if (subdep == ElConstants.SUBOP_YES) {
	// Department dep = new Department();
	// // 获取 部门的左右值
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
	// ps.setInt(1, depid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// dep.setId(rs.getInt(1));
	// dep.setLid(rs.getInt(2));
	// dep.setRid(rs.getInt(3));
	// }
	//
	// ps.close();
	// rs.close();
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(DUConstants.USER_QUERY_SUBS_BYDEPIDANDOS));
	// sql = "select * from (select t1.*,rownum rn from( select eu.id
	// euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name
	// depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl(
	// floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_
	// from ELUSER eu left join elrole er on er.id = eu.role left join
	// DEPARTMENT dep on eu.depid = dep.id"
	// + " where eu.username like ? and eu.realname like ? and eu.sex like ? "
	// + isValid// and eu.jingzhong like ?
	// + con
	// + " and dep.lid >=? and dep.rid<=? )t1 where rownum <=? ) where rn >=?";
	// ps = ct.prepareStatement(sql);
	// /**
	// * 1.点击USER_QUERY_SUBS_BYDEPIDANDOS
	// * 找到了user.query.subs.bydepidandos 2.如果系统是oracle
	// * 那么请在常量（*Constants.java)同级中找到o_querySqls.sql
	// * 3.可以看到user.query.subs.bydepidandos
	// * 对应的sql语句。如果实在没有请Ctrl+h搜索文件。 4.select * from (select
	// * t1.*,rownum rn from( select eu.id euid,eu.username,
	// * eu.realname,eu.role,dep.id depid,dep.name
	// * depname,eu.valid,er.name ername from ELUSER eu left join
	// * elrole er on er.id = eu.role left join DEPARTMENT dep on
	// * eu.depid = dep.id \ where eu.username like ? and eu.realname
	// * like ? and dep.lid >=? and dep.rid<=? )t1 where rownum <=? )
	// * where rn >=? 5 看到dep.lid>=? an dep.rid<=? 这里就是应用到了左右值的树特性。
	// */
	//
	// ps.setString(1, "%" + username + "%");
	// ps.setString(2, "%" + realname + "%");
	// ps.setString(3, "%" + sex + "%");
	// // ps.setString(4, "%" + jz + "%");
	// if (null != eu && eu.getNov() != 1) {
	// if (eu.getValid() == true) {
	// ps.setInt(4, 1);
	// } else {
	// ps.setInt(4, 0);
	// }
	// ps.setInt(5, dep.getLid());
	// ps.setInt(6, dep.getRid());
	// ps.setInt(7, pageNow);
	// ps.setInt(8, pageSize);
	// } else {
	// ps.setInt(4, dep.getLid());
	// ps.setInt(5, dep.getRid());
	// ps.setInt(6, pageNow);
	// ps.setInt(7, pageSize);
	// }
	// } else {
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
	// sql = "select * from (select t1.*,rownum rn from( select eu.id
	// euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name
	// depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl(
	// floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_
	// from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join
	// elrole er on er.id = eu.role "
	// + "where eu.username like ? and eu.realname like ? and eu.sex like ? "
	// + isValid// and eu.jingzhong like ?
	// + con
	// + " and dep.id=?)t1 where rownum <=? ) where rn >=?";
	// ps = ct.prepareStatement(sql);
	// ps.setString(1, "%" + username + "%");
	// ps.setString(2, "%" + realname + "%");
	// ps.setString(3, "%" + sex + "%");
	// // ps.setString(4, "%" + jz + "%");
	// if (null != eu && eu.getNov() != 1) {
	// if (eu.getValid() == true) {
	// ps.setInt(4, 1);
	// } else {
	// ps.setInt(4, 0);
	// }
	// ps.setInt(5, depid);
	// ps.setInt(6, pageNow);
	// ps.setInt(7, pageSize);
	// } else {
	// ps.setInt(4, depid);
	// ps.setInt(5, pageNow);
	// ps.setInt(6, pageSize);
	// }
	// }
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ELUser elUser = new ELUser();
	// elUser.setId(rs.getInt(1));
	// elUser.setUsername(rs.getString(2));
	// elUser.setRealname(rs.getString(3));
	// elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
	// elUser.setDepartment(new Department(rs.getInt(5), rs
	// .getString(6)));
	// elUser.setValid(rs.getBoolean(7));
	// elUser.setSex(rs.getString(9));
	// elUser.setJingzhong(rs.getString(10));
	// elUser.setShengri(rs.getDate(11));
	// elUser.setAge(rs.getInt(12));
	// eus.add(elUser);
	// }
	// } catch (Exception e) {
	// logger.error("用户列表搜索失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return eus;
	// }
	/**
	 * 根据部门获取部门用户（包含下级）
	 */
	public List<ELUser> getUserByDepId(int depid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			Department dep = new Department();
			// 获取 部门的左右值
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			ps.close();
			rs.close();
			sql = "select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_   from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id"
					+ " where  dep.lid >=? and dep.rid<=?";
			ps = ct.prepareStatement(sql);
			/**
			 * 1.点击USER_QUERY_SUBS_BYDEPIDANDOS 找到了user.query.subs.bydepidandos
			 * 2.如果系统是oracle 那么请在常量（*Constants.java)同级中找到o_querySqls.sql
			 * 3.可以看到user.query.subs.bydepidandos 对应的sql语句。如果实在没有请Ctrl+h搜索文件。
			 * 4.select * from (select t1.*,rownum rn from( select eu.id
			 * euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name
			 * depname,eu.valid,er.name ername from ELUSER eu left join elrole
			 * er on er.id = eu.role left join DEPARTMENT dep on eu.depid =
			 * dep.id \ where eu.username like ? and eu.realname like ? and
			 * dep.lid >=? and dep.rid<=? )t1 where rownum <=? ) where rn >=? 5
			 * 看到dep.lid>=? an dep.rid<=? 这里就是应用到了左右值的树特性。
			 */
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
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
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("根据部门获取部门用户搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	/**
	 * (non-Javadoc) 获取不指定角色的用户
	 * 
	 * @see com.sopia.duman.dao.UserDao#getUserByDepId(int, int,
	 *      com.sopia.duman.entities.ELUser, int, int)
	 */
	// public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu)
	// throws ElException {
	// List<ELUser> eus = new ArrayList<ELUser>();
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// String username = "";
	// String realname = "";
	// String sex = "";
	// String jz = "";
	// String con = "";
	// String isValid = "";
	// if (null != eu) {
	// if (null != eu.getUsername())
	// username = eu.getUsername().trim();
	// if (null != eu.getRealname())
	// realname = eu.getRealname().trim();
	// if (null != eu.getSex())
	// sex = eu.getSex().trim();
	// if (null != eu.getJingzhong())
	// jz = eu.getJingzhong().trim();
	// if (eu.getShengri() != null)
	// con = con
	// + " and eu.shengri >=to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(eu.getShengri())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (eu.getShengri_end() != null)
	// con = con
	// + " and eu.shengri <= to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(eu.getShengri_end())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (eu.getNov() != 1) {
	// isValid = " and eu.valid=? ";
	// }
	// }
	// ct = DBConnection.getConnection();
	// // ElConstants.SUBOP_YES包含下级。
	// String sql = "";
	// if (subdep == ElConstants.SUBOP_YES) {
	// Department dep = new Department();
	// // 获取 部门的左右值
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
	// ps.setInt(1, depid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// dep.setId(rs.getInt(1));
	// dep.setLid(rs.getInt(2));
	// dep.setRid(rs.getInt(3));
	// }
	//
	// ps.close();
	// rs.close();
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(DUConstants.USER_QUERY_SUBS_BYDEPIDANDOS));
	// sql = "select eu.id euid,eu.username, eu.realname,eu.role,dep.id
	// depid,dep.name depname,eu.valid,er.name
	// ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy'
	// ))-floor(to_char(shengri,'yyyy')),-1) age_
	// ,eu.password,eu.xuhao,eu.dishi,eu.shenfenzheng,eu.zhiji,eu.zhiwu,eu.gangwei,dep.bh
	// from ELUSER eu left join elrole er on er.id = eu.role left join
	// DEPARTMENT dep on eu.depid = dep.id"
	// + " where eu.username like ? and eu.realname like ? and eu.sex like ? "
	// + isValid// and eu.jingzhong like ?
	// + con + " and dep.lid >=? and dep.rid<=? ";
	// ps = ct.prepareStatement(sql);
	// /**
	// * 1.点击USER_QUERY_SUBS_BYDEPIDANDOS
	// * 找到了user.query.subs.bydepidandos 2.如果系统是oracle
	// * 那么请在常量（*Constants.java)同级中找到o_querySqls.sql
	// * 3.可以看到user.query.subs.bydepidandos
	// * 对应的sql语句。如果实在没有请Ctrl+h搜索文件。 4.select * from (select
	// * t1.*,rownum rn from( select eu.id euid,eu.username,
	// * eu.realname,eu.role,dep.id depid,dep.name
	// * depname,eu.valid,er.name ername from ELUSER eu left join
	// * elrole er on er.id = eu.role left join DEPARTMENT dep on
	// * eu.depid = dep.id \ where eu.username like ? and eu.realname
	// * like ? and dep.lid >=? and dep.rid<=? )t1 where rownum <=? )
	// * where rn >=? 5 看到dep.lid>=? an dep.rid<=? 这里就是应用到了左右值的树特性。
	// */
	//
	// ps.setString(1, "%" + username + "%");
	// ps.setString(2, "%" + realname + "%");
	// ps.setString(3, "%" + sex + "%");
	// // ps.setString(4, "%" + jz + "%");
	// if (null != eu && eu.getNov() != 1) {
	// if (eu.getValid() == true) {
	// ps.setInt(4, 1);
	// } else {
	// ps.setInt(4, 0);
	// }
	// ps.setInt(5, dep.getLid());
	// ps.setInt(6, dep.getRid());
	// } else {
	// ps.setInt(4, dep.getLid());
	// ps.setInt(5, dep.getRid());
	// }
	// } else {
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
	// sql = "select eu.id euid,eu.username, eu.realname,eu.role,dep.id
	// depid,dep.name depname,eu.valid,er.name
	// ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy'
	// ))-floor(to_char(shengri,'yyyy')),-1) age_ from ELUSER eu left join
	// DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id =
	// eu.role "
	// + "where eu.username like ? and eu.realname like ? and eu.sex like ? "
	// + isValid// and eu.jingzhong like ?
	// + con + " and dep.id=?";
	// ps = ct.prepareStatement(sql);
	// ps.setString(1, "%" + username + "%");
	// ps.setString(2, "%" + realname + "%");
	// ps.setString(3, "%" + sex + "%");
	// // ps.setString(4, "%" + jz + "%");
	// if (null != eu && eu.getNov() != 1) {
	// if (eu.getValid() == true) {
	// ps.setInt(4, 1);
	// } else {
	// ps.setInt(4, 0);
	// }
	// ps.setInt(5, depid);
	// } else {
	// ps.setInt(4, depid);
	// }
	// }
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ELUser elUser = new ELUser();
	// elUser.setId(rs.getInt(1));
	// elUser.setUsername(rs.getString(2));
	// elUser.setRealname(rs.getString(3));
	// elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
	// elUser.setValid(rs.getBoolean(7));
	// elUser.setSex(rs.getString(9));
	// elUser.setJingzhong(rs.getString(10));
	// elUser.setShengri(rs.getDate(11));
	// elUser.setAge(rs.getInt(12));
	// elUser.setPassword(rs.getString(13));
	// elUser.setXuhao(rs.getString(14));
	// elUser.setDishi(rs.getString(15));
	// elUser.setShenfenzheng(rs.getString(16));
	// elUser.setZhiji(rs.getString(17));
	// elUser.setZhiwu(rs.getString(18));
	// elUser.setGangwei(rs.getString(19));
	// Department dep = new Department(rs.getInt(5), rs.getString(6));
	// dep.setBh(rs.getString(20));
	// elUser.setDepartment(dep);
	// eus.add(elUser);
	// }
	// } catch (Exception e) {
	// logger.error("用户列表搜索失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return eus;
	// }
//	public int getUserByUserIdSize(int userid, ELUser eu) throws ElException {
//		int b = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			String jz = "";
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//			}
//			ct = DBConnection.getConnection();
//
//			// ElConstants.SUBOP_YES包含下级。
//			String sql = "";
//			// ps = ct.prepareStatement(ElQuerySql
//			// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
//			sql = " select count( eu.id) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? "// and
//					// eu.jingzhong
//					// like
//					// ?
//					+ con;
//			List<Department> dep = listMyDeps(userid, "op");
//			if (null != dep && dep.size() > 0) {
//				String ids = "";
//				for (int i = 0; i < dep.size(); i++) {
//					ids = ids + dep.get(i).getId() + ",";
//				}
//				ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
//						: ids;
//				sql += " and dep.id in(" + ids + ") ";
//
//				// else
//				// sql+=" and dep.id="+depid")t1 where rownum <=? ) where rn
//				// >=?";
//				ps = ct.prepareStatement(sql);
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + sex + "%");
//				// ps.setString(4, "%" + jz + "%");
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					b = rs.getInt(1);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return b;
//	}

//	public List<ELUser> getUserByUserId(int userid, ELUser eu, int pageNow,
//			int pageSize) throws ElException {
//
//		List<ELUser> eus = new ArrayList<ELUser>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			String jz = "";
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//			}
//			ct = DBConnection.getConnection();
//
//			// ElConstants.SUBOP_YES包含下级。
//			String sql = "";
//			// ps = ct.prepareStatement(ElQuerySql
//			// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
//			sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? and eu.jingzhong like ? "
//					+ con;
//			List<Department> dep = listMyDeps(userid, "op");
//			if (null != dep && dep.size() > 0) {
//				String ids = "";
//				for (int i = 0; i < dep.size(); i++) {
//					ids = ids + dep.get(i).getId() + ",";
//				}
//				ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
//						: ids;
//				sql += " and dep.id in(" + ids
//						+ "))t1 where rownum <=? ) where rn >=?";
//
//				// else
//				// sql+=" and dep.id="+depid")t1 where rownum <=? ) where rn
//				// >=?";
//				ps = ct.prepareStatement(sql);
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + sex + "%");
//				ps.setString(4, "%" + jz + "%");
//				ps.setInt(5, pageNow);
//				ps.setInt(6, pageSize);
//				rs = ps.executeQuery();
//				while (rs.next()) {
//					ELUser elUser = new ELUser();
//					elUser.setId(rs.getInt(1));
//					elUser.setUsername(rs.getString(2));
//					elUser.setRealname(rs.getString(3));
//					elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
//					elUser.setDepartment(new Department(rs.getInt(5), rs
//							.getString(6)));
//					elUser.setValid(rs.getBoolean(7));
//					elUser.setSex(rs.getString(9));
//					elUser.setJingzhong(rs.getString(10));
//					elUser.setShengri(rs.getDate(11));
//					elUser.setAge(rs.getInt(12));
//					eus.add(elUser);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return eus;
//	}

//	public List<ELUser> getUserByUserId(int userid, int roleid, ELUser eu,
//			int pageNow, int pageSize) throws ElException {
//
//		List<ELUser> eus = new ArrayList<ELUser>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			int jz = 0;
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (eu.getJingzhong()>0)
//					jz = eu.getJingzhong();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//			}
//			ct = DBConnection.getConnection();
//
//			// ElConstants.SUBOP_YES包含下级。
//			String sql = "";
//			// ps = ct.prepareStatement(ElQuerySql
//			// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
//			sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? and eu.jingzhong like ? and eu.role  = ? "
//					+ con;
//			List<Department> dep = listMyDeps(userid, "op");
//			if (null != dep && dep.size() > 0) {
//				String ids = "";
//				for (int i = 0; i < dep.size(); i++) {
//					ids = ids + dep.get(i).getId() + ",";
//				}
//				ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
//						: ids;
//				sql += " and dep.id in(" + ids
//						+ "))t1 where rownum <=? ) where rn >=?";
//
//				// else
//				// sql+=" and dep.id="+depid")t1 where rownum <=? ) where rn
//				// >=?";
//				ps = ct.prepareStatement(sql);
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + sex + "%");
//				ps.setString(4, "%" + jz + "%");
//				ps.setInt(5, roleid);
//				ps.setInt(6, pageNow);
//				ps.setInt(7, pageSize);
//				rs = ps.executeQuery();
//				while (rs.next()) {
//					ELUser elUser = new ELUser();
//					elUser.setId(rs.getInt(1));
//					elUser.setUsername(rs.getString(2));
//					elUser.setRealname(rs.getString(3));
//					elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
//					elUser.setDepartment(new Department(rs.getInt(5), rs
//							.getString(6)));
//					elUser.setValid(rs.getBoolean(7));
//					elUser.setSex(rs.getString(9));
//					elUser.setJingzhong(rs.getInt(10));
//					elUser.setShengri(rs.getDate(11));
//					elUser.setAge(rs.getInt(12));
//					eus.add(elUser);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return eus;
//	}

//	public int getUserByUserIdSize(int userid, int roleid, ELUser eu)
//			throws ElException {
//		int b = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			String jz = "";
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//			}
//			ct = DBConnection.getConnection();
//
//			// ElConstants.SUBOP_YES包含下级。
//			String sql = "";
//			// ps = ct.prepareStatement(ElQuerySql
//			// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
//			sql = " select count( eu.id) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? and eu.jingzhong like ? and eu.role = ? "
//					+ con;
//			List<Department> dep = listMyDeps(userid, "op");
//			if (null != dep && dep.size() > 0) {
//				String ids = "";
//				for (int i = 0; i < dep.size(); i++) {
//					ids = ids + dep.get(i).getId() + ",";
//				}
//				ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
//						: ids;
//				sql += " and dep.id in(" + ids + ") ";
//
//				// else
//				// sql+=" and dep.id="+depid")t1 where rownum <=? ) where rn
//				// >=?";
//				ps = ct.prepareStatement(sql);
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + sex + "%");
//				ps.setString(4, "%" + jz + "%");
//				ps.setInt(5, roleid);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					b = rs.getInt(1);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return b;
//	}

//	public List<ELUser> getUserByUserId3(Department depTree, int depid,
//			int role, int userid, int roleid, ELUser eu, int pageNow,
//			int pageSize) throws ElException {
//
//		List<ELUser> eus = new ArrayList<ELUser>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			String jz = "";
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//			}
//			String roleids = "";
//			if (roleid != 0) {
//				roleids = " and eu.role  = " + roleid;
//			}
//			String x = Integer.toString(depid);
//			String ids = createDepartmentId(depTree, depid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//				// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
//
//			ct = DBConnection.getConnection();
//
//			String sql = "";
//			sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ "where eu.username like ? and eu.realname like ? and eu.sex like ?  "
//					+ roleids//
//					+ con
//					+ " and dep.id in("
//					+ ids
//					+ "))t1 where rownum <=? ) where rn >=?";
//			ps = ct.prepareStatement(sql);
//			ps.setString(1, "%" + username + "%");
//			ps.setString(2, "%" + realname + "%");
//			ps.setString(3, "%" + sex + "%");
//			ps.setInt(4, pageNow);
//			ps.setInt(5, pageSize);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ELUser elUser = new ELUser();
//				elUser.setId(rs.getInt(1));
//				elUser.setUsername(rs.getString(2));
//				elUser.setRealname(rs.getString(3));
//				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
//				elUser.setDepartment(new Department(rs.getInt(5), rs
//						.getString(6)));
//				elUser.setValid(rs.getBoolean(7));
//				elUser.setSex(rs.getString(9));
//				elUser.setJingzhong(rs.getString(10));
//				elUser.setShengri(rs.getDate(11));
//				elUser.setAge(rs.getInt(12));
//				eus.add(elUser);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return eus;
//	}

//	public int getUserByUserIdSize3(Department depTree, int depid, int role,
//			int userid, int roleid, ELUser eu) throws ElException {
//		int b = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			String jz = "";
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//
//			}
//
//			String roleids = "";
//			if (roleid != 0) {
//				roleids = " and eu.role  = " + roleid;
//			}
//
//			String x = Integer.toString(depid);
//			String ids = createDepartmentId(depTree, depid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//				// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
//
//			ct = DBConnection.getConnection();
//			String sql = "";
//			sql = " select count( eu.id) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ "where eu.username like ? and eu.realname like ? and eu.sex like ?  "
//					+ roleids // and eu.jingzhong like ?
//					+ con + " and dep.id in(" + ids + ") ";
//			ps = ct.prepareStatement(sql);
//			ps.setString(1, "%" + username + "%");
//			ps.setString(2, "%" + realname + "%");
//			ps.setString(3, "%" + sex + "%");
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				b = rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return b;
//	}

	public List<Department> listMyDeps(int userid, String type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> list = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from department_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int depid = rs.getInt(1);
				list.addAll(listDepartmentsById(depid, ct));

			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	private List<Department> listDepartmentsById(int parentid, Connection ct)
			throws Exception {
		List<Department> deps = new ArrayList<Department>();
		PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
				.getSQL(DUConstants.DEP_QUERY_BYPIDANDCID));
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		while (rstemp.next()) {
			Department dep = new Department(rstemp.getInt(1), rstemp
					.getString(2));
			if (dep.getId() != 0)
				dep.setChild(listDepartmentsById(dep.getId(), ct));
			deps.add(dep);
		}
		rstemp.close();
		pstemp.close();
		return deps;
	}

	public int getUserByDepIdSize(int depid, int subdep, ELUser eu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String sex = "";
//			String jz = "";
			String con = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_SUBS_SIZE_BYDEPIDANDOS));
				ps = ct
						.prepareStatement("select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.username like ? and eu.realname  like ? and eu.sex like ? "
								+ con + " and dep.lid >=? and dep.rid<=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
				// ps.setString(4, "%" + jz + "%");
				ps.setInt(4, dep.getLid());
				ps.setInt(5, dep.getRid());
			} else {
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_SIZE_BYDEPIDANDOS));
				ps = ct
						.prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.username like ? and eu.realname like ?  and eu.sex like ? "
								+ con + " and dep.id=? ");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
				// ps.setString(4, "%" + jz + "%");
				ps.setInt(4, depid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	// public int getUserByDepIdSize2(int depid, int subdep, ELUser eu)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// String username = "";
	// String realname = "";
	// String sex = "";
	// String jz = "";
	// String con = "";
	// String isValie = "";
	// if (null != eu) {
	// if (null != eu.getUsername())
	// username = eu.getUsername().trim();
	// if (null != eu.getRealname())
	// realname = eu.getRealname().trim();
	// if (null != eu.getSex())
	// sex = eu.getSex().trim();
	// if (null != eu.getJingzhong())
	// jz = eu.getJingzhong().trim();
	// if (eu.getShengri() != null)
	// con = con
	// + " and eu.shengri >=to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(eu.getShengri())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (eu.getShengri_end() != null)
	// con = con
	// + " and eu.shengri <= to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(eu.getShengri_end())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (eu.getNov() != 1) {
	// isValie = " and eu.valid=? ";
	// }
	// }
	// ct = DBConnection.getConnection();
	// if (subdep == ElConstants.SUBOP_YES) {
	// Department dep = new Department();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
	// ps.setInt(1, depid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// dep.setId(rs.getInt(1));
	// dep.setLid(rs.getInt(2));
	// dep.setRid(rs.getInt(3));
	// }
	// ps.close();
	// rs.close();
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(DUConstants.USER_QUERY_SUBS_SIZE_BYDEPIDANDOS));
	// ps = ct
	// .prepareStatement("select count(*)from ELUSER eu left join DEPARTMENT dep
	// on eu.depid = dep.id left join elrole er on er.id = eu.role where
	// eu.username like ? and eu.realname like ? and eu.sex like ? "
	// + isValie
	// + con
	// + " and dep.lid >=? and dep.rid<=?");
	// ps.setString(1, "%" + username + "%");
	// ps.setString(2, "%" + realname + "%");
	// ps.setString(3, "%" + sex + "%");
	// // ps.setString(4, "%" + jz + "%");
	// if (null != eu && eu.getNov() != 1) {
	// if (eu.getValid() == true) {
	// ps.setInt(4, 1);
	// } else {
	// ps.setInt(4, 0);
	// }
	// ps.setInt(5, dep.getLid());
	// ps.setInt(6, dep.getRid());
	// } else {
	// ps.setInt(4, dep.getLid());
	// ps.setInt(5, dep.getRid());
	// }
	// } else {
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(DUConstants.USER_QUERY_SIZE_BYDEPIDANDOS));
	// ps = ct
	// .prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT
	// dep on eu.depid = dep.id left join elrole er on er.id = eu.role where
	// eu.username like ? and eu.realname like ? and eu.sex like ? "
	// + isValie + con + " and dep.id=? ");
	// ps.setString(1, "%" + username + "%");
	// ps.setString(2, "%" + realname + "%");
	// ps.setString(3, "%" + sex + "%");
	// // ps.setString(4, "%" + jz + "%");
	// if (null != eu && eu.getNov() != 1) {
	// if (eu.getValid() == true) {
	// ps.setInt(4, 1);
	// } else {
	// ps.setInt(4, 0);
	// }
	// ps.setInt(5, depid);
	// } else {
	// ps.setInt(4, depid);
	// }
	// }
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("用户列表搜索失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	public List<ELUser> getUserByDepId(int depid, int subdep, ELUser eu,
			int role, int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(DUConstants.USER_QUERY_SUBS_BYDEPIDANDOS_ROLE));
				ps = ct.prepareStatement("select * from (select t1.* ,rownum rn from(select eu.id euid,eu.username,eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id "+
				" where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=? ) t1 where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
//				ps.setInt(5, role);
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS_ROLE));
				ps = ct.prepareStatement("select * from (select t1.* ,rownum rn from(select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "+
				" where eu.username like ? and eu.realname like ? and dep.id=? ) t1 where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
//				ps.setInt(4, role);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
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

	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
			int role, int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String isValid = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				isValid = " and eu.valid=? ";
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement("select * from (select t1.* ,rownum rn from(select eu.id euid,eu.username,eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id "
								+ " where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=? and eu.role=? "
								+ isValid
								+ ") t1 where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, role);
				if (null != eu) {
					if (eu.getValid() == true) {
						ps.setInt(6, 1);
					} else {
						ps.setInt(6, 0);
					}
					ps.setInt(7, pageNow);
					ps.setInt(8, pageSize);
				} else {
					ps.setInt(6, pageNow);
					ps.setInt(7, pageSize);
				}
			} else {
				ps = ct
						.prepareStatement("select * from (select t1.* ,rownum rn from(select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
								+ " where eu.username like ? and eu.realname like ? and dep.id=? and eu.role=? "
								+ isValid
								+ ") t1 where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, role);
				if (null != eu) {
					if (eu.getValid() == true) {
						ps.setInt(5, 1);
					} else {
						ps.setInt(5, 0);
					}
					ps.setInt(6, pageNow);
					ps.setInt(7, pageSize);
				} else {
					ps.setInt(5, pageNow);
					ps.setInt(6, pageSize);
				}
			}
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

	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
			int role) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String isValid = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				isValid = " and eu.valid=? ";
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement("select eu.id euid,eu.username,eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id "
								+ " where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=? and eu.role=? "
								+ isValid);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, role);
				if (null != eu) {
					if (eu.getValid() == true) {
						ps.setInt(6, 1);
					} else {
						ps.setInt(6, 0);
					}
				} else {
				}
			} else {
				ps = ct
						.prepareStatement("select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
								+ " where eu.username like ? and eu.realname like ? and dep.id=? and eu.role=? "
								+ isValid);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, role);
				if (null != eu) {
					if (eu.getValid() == true) {
						ps.setInt(5, 1);
					} else {
						ps.setInt(5, 0);
					}
				} else {
				}
			}
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

	public int getUserByDepIdSize(int depid, int subdep, ELUser eu, int role)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(DUConstants.USER_QUERY_SUBS_SIZE_BYDEPIDANDOS_ROLE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, role);

			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.USER_QUERY_SIZE_BYDEPIDANDOS_ROLE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, role);

			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int getUserByDepIdSize2(int depid, int subdep, ELUser eu, int role)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String isValid = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				isValid = " and eu.valid=? ";
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement("select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
								+ " where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=? and eu.role=?"
								+ isValid);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, role);
				if (null != eu) {
					if (eu.getValid() == true) {
						ps.setInt(6, 1);
					} else {
						ps.setInt(6, 0);
					}
				}
			} else {
				ps = ct
						.prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
								+ " where eu.username like ? and eu.realname like ? and dep.id=? and eu.role=?"
								+ isValid);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, role);
				if (null != eu) {
					if (eu.getValid() == true) {
						ps.setInt(5, 1);
					} else {
						ps.setInt(5, 0);
					}
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> getVUserByDepId(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.USER_QUERY_VSUBS_BYDEPIDANDOS));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.USER_QUERY_VBYDEPIDANDOS));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(6), rs.getString(11)));

				elUser.setDepartment(new Department(rs.getInt(8), rs
						.getString(9)));

				elUser.setValid(rs.getBoolean(10));
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

	public int getVUserByDepIdSize(int depid, int subdep, ELUser eu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(DUConstants.USER_QUERY_VSUBS_SIZE_BYDEPIDANDOS));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.USER_QUERY_VSIZE_BYDEPIDANDOS));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void delete(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// TODO 删除用户做那些事？
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("delete from study_course where userid = ?");
			// ps.setInt(1, id);
			// ps.executeUpdate();
			// // 删除基本信息
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(DUConstants.USER_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			// 删除相关...

		} catch (Exception e) {
			logger.error("删除用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> getEUsByDepid(int depid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_QUERY_BYDEPID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				eus.add(new ELUser(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			logger.error("部门用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public void setEURole(int uid, int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLE_SET));
			ps.setInt(1, role);
			ps.setInt(2, uid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置用户权限失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterMyInfo(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update ELUSER set  realname=?, depid=?, sex=?, xuhao=?,  dishi=?,  danwei=?,  shenfenzheng=?, shengri=?,  zhiji=?,  zhiwu=?,  jingzhong=?, gangwei =?,movephone=?,email=?" +
							",specialty=?,school=?,Education=?,xuewei=?,minzu=?,jiguan=?,canjiagongzuoshijian=?,rusishijian=?,zhengzhimianmao=?,pinyinjianxie=?" +
							",chushengdi=?,xianrenzhishijian=?,xianyuangongzu=?,xianzhiwei=?,xueli=?"	+
					" where id = ?");
			ps.setString(1, elUser.getRealname());
			ps.setInt(2, elUser.getDepartment().getId());
			ps.setString(3, elUser.getSex());
			ps.setString(4, elUser.getXuhao());
			ps.setInt(5, elUser.getDishi());
			ps.setString(6, elUser.getDanwei());
			ps.setString(7, elUser.getShenfenzheng());
			ps.setDate(8, elUser.getShengri());
			ps.setInt(9, elUser.getZhiji());
			ps.setInt(10, elUser.getZhiwu());
			ps.setInt(11, elUser.getJingzhong());
			ps.setString(12, elUser.getGangwei());
			ps.setString(13, elUser.getMovephone());
			updusertouxiang(elUser.getId(),elUser.getTouxiang());
			ps.setString(14, elUser.getEmail());
			
			ps.setString(15, elUser.getSpecialty());
			ps.setString(16, elUser.getSchool());
			ps.setInt(17, elUser.getEducation());
			ps.setString(18, elUser.getXuewei());
			ps.setString(19, elUser.getMinzu());
			ps.setString(20, elUser.getJiguan());
			ps.setDate(21, elUser.getCanjiagongzuoshijian());
			ps.setDate(22, elUser.getRusishijian());
			ps.setString(23, elUser.getZhengzhimianmao());
			ps.setString(24, elUser.getPinyinjianxie());
			ps.setString(25, elUser.getChushengdi());
			ps.setDate(26, elUser.getXianrenzhishijian());
			ps.setString(27, elUser.getXianyuangongzu());
			ps.setString(28, elUser.getXianzhiwei());
			ps.setString(29, elUser.getXueli());
			ps.setInt(30, elUser.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新个人设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setStation(int id, int station) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eluser set station = ? where id = ?");
			ps.setInt(1, station);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新个人设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterMyPwd(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_MYINFO_PWD_ALTER));
			ps.setString(1, elUser.getPassword());
			ps.setInt(2, elUser.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新个人密码设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkPwd(int id, String thePwd) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_CHECK_PWD_BYID));
			ps.setString(1, thePwd);
			ps.setInt(2, id);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("检测个人密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<ELUser> getEUsByRole() throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_QUERY_BYROLE));
			ps.setInt(1, 4);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(3), rs.getString(4)));
				eus.add(eu);

			}
		} catch (Exception e) {
			logger.error("部门用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public boolean checkUsername(String username) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_CHECK_UN));

			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				// return true;
				bool = true;
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("检测用户名是否存在！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}

//	public void alterUserHead(ELUser elUser) throws ElException {
		/*
		 * PreparedStatement ps = null; ResultSet rs = null; Connection ct =
		 * null; try { ct = DBConnection.getConnection(); ps = ct
		 * .prepareStatement("update eluser set headphoto = ? where id=?");
		 * 
		 * ps.setString(1, elUser.getHeadPhoto()); ps.setInt(2, elUser.getId());
		 * ps.executeUpdate(); } catch (Exception e) {
		 * logger.error("检测用户名是否存在！", e); throw new ElException(e); } finally {
		 * DBConnection.closeConnectInfo(ct, ps, rs); }
		 */
//	}
	
	public void alterUserHead(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update eluser set headphoto = ? where id=?"); 
			ps.setString(1, elUser.getHeadPhoto());
			ps.setInt(2, elUser.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检测用户名是否存在！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkHasFblock(int userid) throws ElException {
		// PreparedStatement ps = null;
		// ResultSet rs = null;
		// Connection ct = null;
		// try {
		// ct = DBConnection.getConnection();
		// ps = ct
		// .prepareStatement("select * from forumblock where manager=? and title
		// like '%专家%'");
		// ps.setInt(1, userid);
		// rs = ps.executeQuery();
		// if (rs.next())
		// return true;
		// } catch (Exception e) {
		// logger.error("检测用户名是否存在！", e);
		// throw new ElException(e);
		// } finally {
		// DBConnection.closeConnectInfo(ct, ps, rs);
		// }
		return false;
	}

	public boolean checkSfzandusername(String username, String userno)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from  eluser where username=? and userno = ?");
			ps.setString(1, username);
			ps.setString(2, userno);
			rs = ps.executeQuery();

			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("检测用户名是否存在！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	// 用户总数
	public void getFlowUser() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update flow_statistics set currentOnline=currentOnline+1");
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("用户总数统计失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 当前在线用户数减少
	public void updateFlowUser() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update flow_statistics set currentOnline=currentOnline-1");
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("用户总数统计失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> getDistributionStudents(Department depTree, Station staTree,int depid,
			ELUser eu, int role, int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String staLidRid = " and ";
		String LidRid = " and ";
		String depids = "";
		List<Station> staList = new ArrayList<Station>();
		int x = 1;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String con = "";
			String xianzhiwei = "";
			if (null != eu) {
				if (null != eu.getUsername()) {
					username = eu.getUsername().trim();
				}
				if (null != eu.getRealname()) {
					realname = eu.getRealname().trim();
				}
				if (null != eu.getSex()) {
					sex = eu.getSex().trim();
				}
				if(null != eu.getXianzhiwei()){
					xianzhiwei = eu.getXianzhiwei().trim();
				}
//				if (null != eu.getJingzhong() && !eu.getJingzhong().equals("0")) {
//					con = con + " and eu.jingzhong = '"
//							+ eu.getJingzhong().trim() + "' ";
//				}
//				if (null != eu.getDishi() && !eu.getDishi().equals("0")) {
//					con = con + " and eu.dishi = '" + eu.getDishi().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiji() && !eu.getZhiji().equals("0")) {
//					con = con + " and eu.zhiji = '" + eu.getZhiji().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiwu() && !eu.getZhiwu().equals("0")) {
//					con = con + " and eu.zhiwu = '" + eu.getZhiwu().trim()
//							+ "' ";
//				}
				if (eu.getJingzhong()>0) {
					con=con + " and eu.jingzhong = "
							+ eu.getJingzhong();
				}
				if (eu.getDishi()>0) {
					con=con + " and eu.dishi = "
							+ eu.getDishi();
				}
				if (eu.getZhiji()>0) {
					con=con + " and eu.zhiji = "
							+ eu.getZhiji();
				}
				if (eu.getZhiwu()>0) {
					con=con + " and eu.zhiwu = "
							+ eu.getZhiwu();
				}
				if (null != eu.getGangwei() && !eu.getGangwei().equals("0")) {
					con = con + " and eu.gangwei = '" + eu.getGangwei().trim()
							+ "' ";
				}
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}

			ct = DBConnection.getConnection();
			Department dep = new Department();
			Station sta = new Station();
			if (depTree.getId() == -2) {
				for (int i = 0; i < depTree.getChild().size(); i++) {
					if (depids.equals("")) {
						depids = depids + depTree.getChild().get(i).getId();
					} else {
						depids = depids + ","
								+ depTree.getChild().get(i).getId();
					}
				}
				ps = ct
						.prepareStatement("select lid,rid from DEPARTMENT where id in ("
								+ depids + ")");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (depTree.getChild().size() == 1) {
						LidRid = LidRid + "  dep.lid >= " + rs.getInt(1)
								+ " and  dep.rid <= " + rs.getInt(2);
					} else {
						if (depTree.getChild().size() > 1
								&& depTree.getChild().size() != x && x > 1) {// 中间不用加
							LidRid = LidRid + " or (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						} else if (depTree.getChild().size() == x) {// 结束前面加 ）
							LidRid = LidRid + "  or (dep.lid >= "
									+ rs.getInt(1) + " and  dep.rid <= "
									+ rs.getInt(2) + "))";
						} else {// 开始前面加 （
							LidRid = LidRid + "  ( (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						}
					}
					x++;
				}
			} else {
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depTree.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
					LidRid = LidRid + " dep.lid>=" + rs.getInt(2)
							+ " and dep.rid<= " + rs.getInt(3);
				}
			}
			ps = ct.prepareStatement("select * from station where id=?");
			ps.setInt(1, staTree.getId());
			rs = ps.executeQuery();
			rs.next();
			sta.setId(rs.getInt("id"));
			sta.setName(rs.getString("name"));
			sta.setLid(rs.getInt("lid"));
			sta.setRid(rs.getInt("rid"));
			staLidRid = staLidRid + " and sta.lid>="+rs.getInt("lid")+" and sta.rid<= "+rs.getInt("rid");
			staList.add(sta);
			ps.close();
			rs.close();

			String lower = "";
			String stalower ="";
			if (depTree.isLower()) {// 包含下级
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.lid >= " + dep.getLid()
							+ " and dep.rid<= " + dep.getRid();
					stalower = " and sta.lid>="+sta.getLid()+" and sta.rid<= "+sta.getRid();
				}
			} else {
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.id = " + depTree.getId();
				}
			}
			String sql = "";
			sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id inner join station sta on sta.id=eu.staid left join elrole er on er.id = eu.role "
					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? and sta.name like ? "
					+ con + lower + stalower +" )t1 where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "%" + realname + "%");
			ps.setString(3, "%" + sex + "%");
			ps.setString(4, "%" + xianzhiwei + "%");
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
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

	public int getDistributionStudentsCount(Department depTree, Station staTree,int depid,
			ELUser eu, int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int b = 0;
		String LidRid = " and ";
		String staLidRid = " and ";
		String depids = "";
		int x = 1;
		List<Station> staList = new ArrayList<Station>();
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String con = "";
			String xianzhiwei ="";
			if (null != eu) {
				if (null != eu.getUsername()) {
					username = eu.getUsername().trim();
				}
				if (null != eu.getRealname()) {
					realname = eu.getRealname().trim();
				}
				if (null != eu.getSex()) {
					sex = eu.getSex().trim();
				}
				if(null != eu.getXianzhiwei()){
					xianzhiwei = eu.getXianzhiwei().trim();
				}
//				if (null != eu.getJingzhong() && !eu.getJingzhong().equals("0")) {
//					con = con + " and eu.jingzhong = '"
//							+ eu.getJingzhong().trim() + "' ";
//				}
//				if (null != eu.getDishi() && !eu.getDishi().equals("0")) {
//					con = con + " and eu.dishi = '" + eu.getDishi().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiji() && !eu.getZhiji().equals("0")) {
//					con = con + " and eu.zhiji = '" + eu.getZhiji().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiwu() && !eu.getZhiwu().equals("0")) {
//					con = con + " and eu.zhiwu = '" + eu.getZhiwu().trim()
//							+ "' ";
//				}
				if (eu.getJingzhong()>0) {
					con=con + " and eu.jingzhong = "
							+ eu.getJingzhong();
				}
				if (eu.getDishi()>0) {
					con=con + " and eu.dishi = "
							+ eu.getDishi();
				}
				if (eu.getZhiji()>0) {
					con=con + " and eu.zhiji = "
							+ eu.getZhiji();
				}
				if (eu.getZhiwu()>0) {
					con=con + " and eu.zhiwu = "
							+ eu.getZhiwu();
				}
				if (null != eu.getGangwei() && !eu.getGangwei().equals("0")) {
					con = con + " and eu.gangwei = '" + eu.getGangwei().trim()
							+ "' ";
				}
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}

			ct = DBConnection.getConnection();
			Department dep = new Department();
			Station sta = new Station();
			if (depTree.getId() == -2) {
				for (int i = 0; i < depTree.getChild().size(); i++) {
					if (depids.equals("")) {
						depids = depids + depTree.getChild().get(i).getId();
					} else {
						depids = depids + ","
								+ depTree.getChild().get(i).getId();
					}
				}
				ps = ct
						.prepareStatement("select lid,rid from DEPARTMENT where id in ("
								+ depids + ")");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (depTree.getChild().size() == 1) {
						LidRid = LidRid + "  dep.lid >= " + rs.getInt(1)
								+ " and  dep.rid <= " + rs.getInt(2);
					} else {
						if (depTree.getChild().size() > 1
								&& depTree.getChild().size() != x && x > 1) {// 中间不用加
							LidRid = LidRid + " or (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						} else if (depTree.getChild().size() == x) {// 结束前面加 ）
							LidRid = LidRid + "  or (dep.lid >= "
									+ rs.getInt(1) + " and  dep.rid <= "
									+ rs.getInt(2) + "))";
						} else {// 开始前面加 （
							LidRid = LidRid + "  ( (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						}
					}
					x++;
				}
			} else {
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depTree.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
					LidRid = LidRid + "  dep.lid>=" + rs.getInt(2)
							+ " and dep.rid<= " + rs.getInt(3);
				}
			}
			ps = ct.prepareStatement("select * from station where id=?");
			ps.setInt(1, staTree.getId());
			rs = ps.executeQuery();
			rs.next();
			sta.setId(rs.getInt("id"));
			sta.setName(rs.getString("name"));
			sta.setLid(rs.getInt("lid"));
			sta.setRid(rs.getInt("rid"));
			staLidRid = staLidRid + " and sta.lid>="+rs.getInt("lid")+" and sta.rid<= "+rs.getInt("rid");
			staList.add(sta);
			ps.close();
			rs.close();

			String lower = "";
			String stalower = "";
			if (depTree.isLower()) {// 包含下级
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.lid >= " + dep.getLid()
							+ " and dep.rid<= " + dep.getRid();
					stalower = " and sta.lid>="+sta.getLid()+" and sta.rid<= "+sta.getRid();
				}
			} else {
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.id = " + depTree.getId();
				}
			}
			String sql = "";
			sql = "select count(*) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id inner join station sta on sta.id=eu.staid left join elrole er on er.id = eu.role "
					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? and sta.name like ? "
					+ con + lower + stalower + "";
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "%" + realname + "%");
			ps.setString(3, "%" + sex + "%");
			ps.setString(4, "%" + xianzhiwei + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				b = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	/**
	 * 查询出从depid开始的有权的部门ID
	 * 
	 * @author HeweiCheng
	 * @param depTree
	 * @param depid
	 * @return
	 */
//	private String createDepartmentId(Department depTree, int depid) {
//		// String id=depTree.getId()+"";
//		if (depTree != null) {
//			if (depTree.getId() != depid) {
//				depTree = getDepartmentById(depTree.getChild(), depid);
//			}
//			if (depTree.getChild() != null) {
//				return createDepartmentId(depTree.getChild(), depTree.getId());
//			}
//			return String.valueOf(depTree.getId());
//		} else {
//			return null;
//		}
//	}

	/**
	 * 构建有权限的部门ID
	 * 
	 * @author Heweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createDepartmentId(List<Department> listType, int id) {
		String ids = id + "";
		for (Department type : listType) {
			ids = ids + "," + createDepartmentId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是根节点开始 要找出开始节点
	 * 
	 * @author Heweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private Department getDepartmentById(List<Department> listType, int depid) {
		Department dep = null;
		for (Department type : listType) {
			if (type.getId() != depid) {
				dep = getDepartmentById(type.getChild(), depid);
				if (dep != null) {
					return dep;
				}
			} else {
				dep = type;
				return dep;
			}
		}
		return dep;
	}

	/**
	 * 分配所有学员
	 */
//	public List<ELUser> getDistributionStudents(Department depTree, int depid,
//			ELUser eu, int role) throws ElException {
//		List<ELUser> eus = new ArrayList<ELUser>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			String sex = "";
//			String jz = "";
//			String con = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getSex())
//					sex = eu.getSex().trim();
//				if (null != eu.getJingzhong())
//					jz = eu.getJingzhong().trim();
//				if (eu.getShengri() != null)
//					con = con
//							+ " and eu.shengri >=to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri())
//							+ "','yyyy-MM-dd HH24:mi:ss') ";
//				if (eu.getShengri_end() != null)
//					con = con
//							+ " and eu.shengri <= to_date('"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(eu.getShengri_end())
//							+ "','yyyy-MM-dd HH24:mi:ss')  ";
//			}
//			ct = DBConnection.getConnection();
//
//			String x = Integer.toString(depid);
//			String ids = createDepartmentId(depTree, depid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//				// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
//
//			String sql = "";
//			sql = "select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? and eu.jingzhong like ? "
//					+ con + "and dep.id in(" + ids + ")) t1";
//			ps = ct.prepareStatement(sql);
//			ps.setString(1, "%" + username + "%");
//			ps.setString(2, "%" + realname + "%");
//			ps.setString(3, "%" + sex + "%");
//			ps.setString(4, "%" + jz + "%");
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ELUser elUser = new ELUser();
//				elUser.setId(rs.getInt(1));
//				elUser.setUsername(rs.getString(2));
//				elUser.setRealname(rs.getString(3));
//				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
//				elUser.setDepartment(new Department(rs.getInt(5), rs
//						.getString(6)));
//				elUser.setValid(rs.getBoolean(7));
//				elUser.setSex(rs.getString(9));
//				elUser.setJingzhong(rs.getString(10));
//				elUser.setShengri(rs.getDate(11));
//				elUser.setAge(rs.getInt(12));
//				eus.add(elUser);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return eus;
//	}

	private List treeAllId = null;

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(QuestionLib qlbTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < qlbTree.getChild().size(); i++) {
			QuestionLib temp = qlbTree.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(CourseType ctype, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < ctype.getChild().size(); i++) {
			CourseType temp = ctype.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(ExamPaperLib examPaperLib, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < examPaperLib.getChild().size(); i++) {
			ExamPaperLib temp = examPaperLib.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(ElClType cltypeTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < cltypeTree.getChild().size(); i++) {
			ElClType temp = cltypeTree.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(EroomLib eroomLibTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < eroomLibTree.getChild().size(); i++) {
			EroomLib temp = eroomLibTree.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}
	
	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(Word WordsTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < WordsTree.getChild().size(); i++) {
			Word temp = WordsTree.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(Department depTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < depTree.getChild().size(); i++) {
			Department temp = depTree.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(StuffLib stuffTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < stuffTree.getChilds().size(); i++) {
			StuffLib temp = stuffTree.getChilds().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChilds() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(NewsType ntypeTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < ntypeTree.getChild().size(); i++) {
			NewsType temp = ntypeTree.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 获取TreeAllId
	 */
	public List getTreeAllId(KnowledgeType userTree, boolean config)
			throws ElException {
		// 需要初始化
		if (config == true) {
			treeAllId = new ArrayList();
		}
		for (int i = 0; i < userTree.getChild().size(); i++) {
			KnowledgeType temp = userTree.getChild().get(i);
			treeAllId.add(temp.getId());
			if (temp.getChild() != null) {
				this.getTreeAllId(temp, false);
			}
		}
		return treeAllId;
	}

	/**
	 * 给用户的题库树赋权(已赋权的不再赋权)
	 */
	public String userGrantOnQlibTree(String chkstr[], int userId,
			List treeAllId) throws ElException {
		QuestionDao questionDao = new QuestionDaoImpl();
		QuestionLib questionLib = null;
		RoleDaoImpl roleDao = new RoleDaoImpl();

		String str = treeAllId.get(0).toString();
		str = str.substring(1, str.length() - 1);// 去掉前后2个中括号
		String[] treeArray = str.split(",");

		// for (int i = 0; i < treeArray.length; i++) {
		// }
		int n = 0;
		for (int i = 0; i < chkstr.length; i++) {
			questionLib = questionDao.getQLbById(Integer.parseInt(chkstr[i]));

			for (int j = 0; j < treeArray.length; j++) {
				if (treeArray[j].trim().equals(chkstr[i])) {
					n = 1;
					break;
				}
			}
			if (n == 1) {
				n = 0;
				continue;
			}

			if (!questionDao.checkOpUsers("op", userId, questionLib.getId())) {// 2.操作人id
				// 3.题库id
				questionDao.addOpusers("op", userId, questionLib.getId());
				roleDao.setUserfunc(userId, "question_lib_list", 0);
				roleDao.setUserfunc(userId, "question_lib_addInit", 0);
				roleDao.setUserfunc(userId, "question_listInit", 0);
				roleDao.setUserfunc(userId, "question_addInit", 0);
				roleDao.setUserfunc(userId, "admin", 0);
			}

			if (!questionDao.checkOpUsers("op", userId, questionLib.getId())) {
				questionDao.addOpusers("op", userId, questionLib.getId());
			}
		}
		return "userGrant";
	}

	/**
	 * 给用户的题库树赋权(删除已有的)
	 */
	public String userGrantOnQlibTree(String chkstr[], int userId,
			String treeType) throws ElException {
		// 先对树类型进行判断
		QuestionDao questionDao = ((QuestionDao) SpringContextUtil
				.getBean("questionDao"));// new QuestionDaoImpl();
		StuffDao stuffDao = ((StuffDao) SpringContextUtil.getBean("stuffDao"));
		if ("qlib".equals(treeType)) {
			QuestionLib questionLib = null;
			RoleDaoImpl roleDao = new RoleDaoImpl();
			// 在此先删除此用户已有节点
			questionDao.deleteUserOpGrant(userId);
//			questionDao.deleteUserUseGrant(userId);
			if (chkstr == null) {
				return "userGrant";
			}
			// for (int i = 0; i < treeArray.length; i++) {
			// }
			// int n=0;
			for (int i = 0; i < chkstr.length; i++) {
				questionLib = questionDao.getQLbById(Integer
						.parseInt(chkstr[i]));
				if (!questionDao
						.checkOpUsers("op", userId, questionLib.getId())) {// 2.操作人id
					// 3.题库id
					questionDao.addOpusers("op", userId, questionLib.getId());
					roleDao.setUserfunc(userId, "question_lib_list", 0);
					roleDao.setUserfunc(userId, "question_lib_addInit", 0);
					roleDao.setUserfunc(userId, "question_listInit", 0);
					roleDao.setUserfunc(userId, "question_addInit", 0);
					roleDao.setUserfunc(userId, "admin", 0);
				}
//				if (!questionDao.checkOpUsers("op", userId, questionLib
//						.getId())) {
//					questionDao.addOpusers("op", userId, questionLib.getId());
//				}
			}
		} else if ("ctyp".equals(treeType)) {
			CourseTypeDao courseTypeDao = new CourseTypeDaoImpl();
			CourseType courseType = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			courseTypeDao.deleteUserOpGrant(userId);
//			courseTypeDao.deleteUserUseGrant(userId);
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				courseType = courseTypeDao.getCtypeById(Integer
						.parseInt(chkstr[i]));
				if (!courseTypeDao.checkOpUsers("course_op_type", userId,
						courseType.getId())) {// 2.操作人id 3.题库id
					courseTypeDao.addOpusers("course_op_type", userId,
							courseType.getId());

				}
//				if (!courseTypeDao.checkOpUsers("course_use_type", userId,
//						courseType.getId())) {
//					courseTypeDao.addOpusers("course_use_type", userId,
//							courseType.getId());
//				}
			}
		} else if ("elib".equals(treeType)) {
			ExamPaperDao examPaperDao = new ExamPaperDaoImpl();
			ExamPaperLib eplTree = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			examPaperDao.deleteUserOpGrant(userId);
//			examPaperDao.deleteUserUseGrant(userId);
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				eplTree = examPaperDao.getEpLById(Integer.parseInt(chkstr[i]));
				if (!examPaperDao.checkOpUsers("op", userId, eplTree.getId())) {// 2.操作人id
					// 3.题库id
					examPaperDao.addOpusers("op", userId, eplTree.getId());

				}
//				if (!examPaperDao.checkOpUsers("op", userId, eplTree.getId())) {
//					examPaperDao.addOpusers("op", userId, eplTree.getId());
//				}
			}
		}else if("wd".equals(treeType)){
			WordDao wordDao = new WordDaoImpl();
			Word wordsTree = null;
			wordDao.deleteUserOpGrant(userId);
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				wordsTree = wordDao.getWordsById(Integer.parseInt(chkstr[i]));
				if (!wordDao.checkOpUsers("op", userId, wordsTree.getId())) {// 2.操作人id
					// 3.词汇库id
					wordDao.addOpusers("op", userId, wordsTree.getId());

				}
			}
		} else if ("clty".equals(treeType)) {
			ElClTypeDao elClTypeDao = new ElClTypeDaoImpl();
			ElClType cltypeTree = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			elClTypeDao.deleteUserOpGrant(userId);
//			elClTypeDao.deleteUserUseGrant(userId);
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				cltypeTree = elClTypeDao.getClTypeById(Integer
						.parseInt(chkstr[i]));
				if (!elClTypeDao.checkOpUsers("class_op_type", userId,
						cltypeTree.getId())) {// 2.操作人id 3.题库id
					elClTypeDao.addOpusers("class_op_type", userId, cltypeTree
							.getId());

				}
//				if (!elClTypeDao.checkOpUsers("class_use_type", userId,
//						cltypeTree.getId())) {
//					elClTypeDao.addOpusers("class_use_type", userId, cltypeTree
//							.getId());
//				}
			}
		} else if ("eroo".equals(treeType)) {
			EroomDao eroomDao = new EroomDaoImpl();
			EroomLib eroomLibTree = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			eroomDao.deleteUserOpGrant(userId);
//			eroomDao.deleteUserUseGrant(userId);
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				eroomLibTree = eroomDao.getEroomLibById(Integer
						.parseInt(chkstr[i]));
				if (!eroomDao.checkOpUsers("op", userId, eroomLibTree.getId())) {// 2.操作人id
					// 3.题库id
					eroomDao.addOpusers("op", userId, eroomLibTree.getId());

				}
//				if (!eroomDao.checkOpUsers("op", userId, eroomLibTree.getId())) {
//					eroomDao.addOpusers("op", userId, eroomLibTree.getId());
//				}
			}
		} else if ("depl".equals(treeType)) {
			DepartmentDao departmentDao = new DepartmentDaoImpl();
			Department depTree = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			departmentDao.deleteUserOpGrant(userId);
//			departmentDao.deleteUserUseGrant(userId);
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				departmentDao.addOpusers("op", userId, Integer
						.parseInt(chkstr[i]));
//				departmentDao.addOpusers("op", userId, Integer
//						.parseInt(chkstr[i]));
			}
		} else if ("stuf".equals(treeType)) {
			// QuestionDao questionDao=new QuestionDaoImpl();
			StuffLib stuffTree = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			stuffDao.deleteStuffUseusers(userId);// 只有可使用权限表
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				stuffDao.addStuffOpusers(userId, Integer.parseInt(chkstr[i]));// 只有可使用权限表
			}
		} else if ("news".equals(treeType)) {
			NewsDao newsDao = new NewsDaoImpl();
			NewsType ntypeTree = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			CourseTypeDao ctypeDao = new CourseTypeDaoImpl();
			// 删除所有权限
			this.deleteUserGrant(userId, "NEWSTYPE_OP_TYPE");
//			this.deleteUserGrant(userId, "NEWSTYPE_USE_TYPE");
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				ntypeTree = newsDao.getNtypeByid(Integer.parseInt(chkstr[i]));
				if (!ctypeDao.checkOpUsers("NEWSTYPE_OP_TYPE", userId,
						ntypeTree.getId())) {
					ctypeDao.addOpusers("NEWSTYPE_OP_TYPE", userId, ntypeTree
							.getId());
				}
//				if (!ctypeDao.checkOpUsers("NEWSTYPE_USE_TYPE", userId,
//						ntypeTree.getId())) {
//					ctypeDao.addOpusers("NEWSTYPE_USE_TYPE", userId, ntypeTree
//							.getId());
//				}
			}
		} else if ("klty".equals(treeType)) {
			KnowledgeDao knowledgeDao = new KnowledgeDaoImpl();
			KnowledgeType kltypeTree = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			CourseTypeDao ctypeDao = new CourseTypeDaoImpl();
			// 删除所有权限
			this.deleteUserGrant(userId, "knowledge_op_type");
//			this.deleteUserGrant(userId, "knowledge_use_type");
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				kltypeTree = knowledgeDao.getKltypeById(Integer
						.parseInt(chkstr[i]));
				if (!ctypeDao.checkOpUsers("knowledge_op_type", userId,
						kltypeTree.getId())) {
					ctypeDao.addOpusers("knowledge_op_type", userId, kltypeTree
							.getId());
				}
//				if (!ctypeDao.checkOpUsers("knowledge_use_type", userId,
//						kltypeTree.getId())) {
//					ctypeDao.addOpusers("knowledge_use_type", userId,
//							kltypeTree.getId());
//				}
			}
		} else if ("bmsq".equals(treeType)) {
			ForumAdminDao forumAdminDao = new ForumAdminDaoImpl();
			ForumBlock fblock = null;
			// RoleDaoImpl roleDao=new RoleDaoImpl();
			// 删除所有权限
			forumAdminDao.deleteOpusers("FBLOCK_USE_TYPE", userId);
			if (chkstr == null) {
				return "userGrant";
			}
			for (int i = 0; i < chkstr.length; i++) {
				fblock = forumAdminDao.getFblockById(Integer
						.parseInt(chkstr[i]));
				if (!forumAdminDao.checkOpUsers("FBLOCK_USE_TYPE", userId,
						fblock.getId()))
					forumAdminDao.addOpusers("FBLOCK_USE_TYPE", userId, fblock
							.getId());
			}
		}
		return "userGrant";
	}

	/**
	 * 删除用户权限
	 * 
	 * @param userId
	 * @throws ElException
	 */
	public void deleteUserGrant(int userId, String tabName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" delete from " + tabName
					+ " where userid= ?");
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
	 * 根据类别查询数据
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {//全部
				ps = ct
						.prepareStatement("select * from basedatat where status!=1 order by typeid,sortid ");
			} else {
				ps = ct
						.prepareStatement("select * from basedatat where typeid=? and status!=1 order by sortid ");
				ps.setInt(1, typeid);
			}
			rs = ps.executeQuery();
			BaseDatat bd = null;
			while (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("id"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setSortid(rs.getInt("sortid"));
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
	 * 根据类别和创建者查询数据（只显示自己创建的和超级管理员创建的数据）
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid(int typeid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {//全部
				ps = ct
						.prepareStatement("select * from basedatat bd left join eluser eu on bd.createrid=eu.id where bd.status!=1 and (bd.createrid=? or eu.role=1) order by bd.typeid,bd.sortid ");
				ps.setInt(1, userid);
			} else {
				ps = ct
						.prepareStatement("select * from basedatat bd left join eluser eu on bd.createrid=eu.id where bd.typeid=? and bd.status!=1 and (bd.createrid=? or eu.role=1) order by bd.sortid ");
				ps.setInt(1, typeid);
				ps.setInt(2, userid);
			}
			rs = ps.executeQuery();
			BaseDatat bd = null;
			while (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("id"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setSortid(rs.getInt("sortid"));
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
	 * 根据类别查询数据（只显示超级管理员创建的数据）
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeidc(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {//全部
				ps = ct
						.prepareStatement("select * from basedatat bd left join eluser eu on bd.createrid=eu.id where bd.status!=1 and eu.role=1 order by bd.typeid,bd.sortid ");
			} else {
				ps = ct
						.prepareStatement("select * from basedatat bd left join eluser eu on bd.createrid=eu.id where bd.typeid=? and bd.status!=1 and eu.role=1 order by bd.sortid ");
				ps.setInt(1, typeid);
			}
			rs = ps.executeQuery();
			BaseDatat bd = null;
			while (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("id"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setSortid(rs.getInt("sortid"));
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
	 * 根据类别查询数据(分页)
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid(int typeid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {//全部
				ps = ct
						.prepareStatement("select * from (select t.*,rownum rn from( select * from basedatat where status!=1 order by typeid,sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else {
				ps = ct
						.prepareStatement("select * from (select t.*,rownum rn from( select * from basedatat where typeid=? and status!=1 order by sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, typeid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			BaseDatat bd = null;
			while (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("id"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setSortid(rs.getInt("sortid"));
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
	 * 根据类别查询数据(分页)
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid2(int typeid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {
				ps = ct
						.prepareStatement("select * from (select t.*,rownum rn from( select bd.id bdid,bd.typeid bdtypeid,bd.basevalue,bd.remack bdremack,bd.sortid,bt.id btid,bt.name,bd.bh from basedatat bd left join basedatatype bt on bd.typeid=bt.id where bd.status!=1 order by typeid,sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else {
				ps = ct
						.prepareStatement("select * from (select t.*,rownum rn from( select bd.id bdid,bd.typeid bdtypeid,bd.basevalue,bd.remack bdremack,bd.sortid,bt.id btid,bt.name,bd.bh from basedatat bd left join basedatatype bt on bd.typeid=bt.id where bd.typeid=? and bd.status!=1 order by sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, typeid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
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
	 * 根据类别和创建者查询数据(分页)
	 * 非超级管理员调用
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid2(int userid,int typeid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {
				ps = ct
						.prepareStatement("select * from (select t.*,rownum rn from( select bd.id bdid,bd.typeid bdtypeid,bd.basevalue,bd.remack bdremack,bd.sortid,bt.id btid,bt.name,bd.bh from basedatat bd left join basedatatype bt on bd.typeid=bt.id where bd.status!=1 and createrid=? order by typeid,sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			} else {
				ps = ct
						.prepareStatement("select * from (select t.*,rownum rn from( select bd.id bdid,bd.typeid bdtypeid,bd.basevalue,bd.remack bdremack,bd.sortid,bt.id btid,bt.name,bd.bh from basedatat bd left join basedatatype bt on bd.typeid=bt.id where bd.typeid=? and bd.status!=1 and createrid=? order by sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, typeid);
				ps.setInt(2, userid);
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
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
			logger.error("根据类别和创建者查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baseList;
	}

	/**
	 * 根据类别查询数据数量
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public int getBaseDatatByTypeidCount(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {
				ps = ct
						.prepareStatement("select count(*) from basedatat where status!=1 ");
			} else {
				ps = ct
						.prepareStatement("select count(*) from basedatat where typeid=? and status!=1 ");
				ps.setInt(1, typeid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 根据类别和创建者查询数据数量
	 * 非超级管理员调用
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public int getBaseDatatByTypeidCount(int userid,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {
				ps = ct
						.prepareStatement("select count(*) from basedatat where status!=1 and createrid=? ");
				ps.setInt(1, userid);
			} else {
				ps = ct
						.prepareStatement("select count(*) from basedatat where typeid=? and status!=1 and createrid=? ");
				ps.setInt(1, typeid);
				ps.setInt(2, userid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据类别和创建者查询数据数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 根据id查询数据，返回id的名字串
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public String getBaseDatatInId(String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String values = "";
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select basevalue from basedatat where id in("
							+ ids + ") ");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (values.equals("")) {
					values = rs.getString("basevalue");
				} else {
					values = values + "," + rs.getString("basevalue");
				}
			}
		} catch (Exception e) {
			logger.error("根据id查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return values;
	}

	/**
	 * 根据id查询数据
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public BaseDatat getBaseDatatById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BaseDatat bd = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from basedatat where id in=?
			// ");//
			// ps = ct.prepareStatement("select * from basedatat where id=? ");
			ps = ct
					.prepareStatement("select bt.id btid,bt.typeid,bt.basevalue,bt.remack,bdt.id bdtid,bdt.name bdtname,bt.bh from basedatat bt left join basedatatype bdt on bt.typeid=bdt.id where bt.id=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("btid"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setBaseType(new BaseDataType(rs.getInt("bdtid"), rs
						.getString("bdtname")));
				bd.setBh(rs.getString(7));
			}
		} catch (Exception e) {
			logger.error("根据id查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bd;
	}
	/**
	 *  根据名称和类别来获取基础数据
	 * @param typeid
	 * @param basevalue
	 * @return
	 * @throws ElException
	 */
	public BaseDatat getBaseDatatByBasevalue(int typeid,String basevalue) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BaseDatat bd = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from basedatat where id in=?
			// ");//
			// ps = ct.prepareStatement("select * from basedatat where id=? ");
			ps = ct
					.prepareStatement("select bt.id btid,bt.typeid,bt.basevalue,bt.remack,bdt.id bdtid,bdt.name bdtname,bt.bh from basedatat bt left join basedatatype bdt on bt.typeid=bdt.id where bt.basevalue=? and bt.typeid=? ");
			ps.setString(1, basevalue);
			ps.setInt(2, typeid);
			rs = ps.executeQuery();
			if (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("btid"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setBaseType(new BaseDataType(rs.getInt("bdtid"), rs
						.getString("bdtname")));
				bd.setBh(rs.getString(7));
			}
		} catch (Exception e) {
			logger.error("根据名称和类别来获取基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bd;
	}

	/**
	 * 更新工种字段到基础数据表
	 * 
	 * @throws ElException
	 */
	public void updateBaseDb_jingzhong() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call userjingzhong_(1)}");
			// ps.setInt(1, 1);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新工种字段到基础数据表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 添加基础数据
	 * 
	 * @param bd
	 * @throws ElException
	 */
	public void addBaseDb(BaseDatat bd) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select max(sortid) from basedatat where typeid=? and status!=1";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bd.getTypeid());
			rs = ps.executeQuery();
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			ps = ct
			.prepareStatement("insert into basedatat(typeid,basevalue,remack,sortid,bh,createrid) values(?,?,?,?,?,?)");
			ps.setInt(1, bd.getTypeid());
			ps.setString(2, bd.getBasevalue());
			ps.setString(3, bd.getRemack());
			ps.setInt(4, maxSortid + 1);
			ps.setString(5, bd.getBh());
			ps.setInt(6, bd.getElUser().getId());
			ps.executeUpdate();
		} catch (Exception e) { 
			logger.error("添加基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 编辑基础数据
	 * 
	 * @param bd
	 * @throws ElException
	 */
	public void updateBaseDb(BaseDatat bd) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update basedatat set basevalue=?,remack=?,bh=? where id=? ");
			ps.setString(1, bd.getBasevalue());
			ps.setString(2, bd.getRemack());
			ps.setString(3, bd.getBh());
			ps.setInt(4, bd.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("编辑基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 删除基础数据
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void delBaseDb(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int typeid = 0;
			int sortid = 0;
			ps = ct
					.prepareStatement("select typeid,sortid from basedatat where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				typeid = rs.getInt("typeid");
				sortid = rs.getInt("sortid");
			}
//			ps = ct
//					.prepareStatement("update basedatat set status=1 where id=?");
			ps = ct
				.prepareStatement("delete from basedatat where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			// 更新所有比他大的sort，往上移动
			if (sortid > 0) {
				ps = ct
						.prepareStatement("update basedatat set sortid=sortid-1 where typeid=? and sortid>?");
				ps.setInt(1, typeid);
				ps.setInt(2, sortid);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("删除基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public boolean checkHasUser(int id,String col) throws ElException{
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 更新所有比他大的sort，往上移动
			ps = ct
					.prepareStatement("select count(id) from eluser where "+col+"=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next())
				if(rs.getInt(1)>0)
					b = true;
		} catch (Exception e) {
			logger.error("删除基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}
	
	public void impBaseDb() throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 更新所有比他大的sort，往上移动
			ps = ct
					.prepareStatement("call impbasedatat()");
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 基础数据排序
	 * 
	 * @param typeid
	 * @param sortid
	 * @param upordown
	 * @throws ElException
	 */
	public void sortBaseDbs(int typeid, int sortid, int upordown)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			if (upordown == ElConstants.SORT_UP) {// =1 向上移
				upSort(ct, typeid, sortid);
			} else {
				downSort(ct, typeid, sortid);
			}
		} catch (Exception e) {
			logger.error("移动网页失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void upSort(Connection ct, int typeid, int sortid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			if (sortid > 0) {
				String sql = "select id from basedatat where typeid = "
						+ typeid + " and sortid = " + (sortid - 1)
						+ " and status!=1 ";
				ResultSet rs = st.executeQuery(sql);// 先得出该对象上1对象
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update basedatat set sortid=sortid-1 "
							+ " where typeid = " + typeid + " and sortid="
							+ sortid + " and status!=1 ";
					st.executeUpdate(sql);// 上移该对象
					sql = "update basedatat set sortid=sortid+1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);// 下移该对象的上一对象
				}
			}
			st.close();

		} catch (Exception e) {
			logger.error("网页上移失败！", e);
			throw new ElException("网页上移失败", e);
		}
	}

	private void downSort(Connection ct, int typeid, int sortid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			String sql = "select max(sortid) from basedatat where typeid= "
					+ typeid;
			ResultSet rs = st.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from basedatat where typeid = " + typeid
						+ " and sortid = " + (sortid + 1) + " and status!=1 ";
				rs = st.executeQuery(sql);// 先得到该对象的下一对象
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update basedatat set sortid=sortid+1 "
							+ " where typeid = " + typeid + " and sortid="
							+ sortid + " and status!=1 ";
					st.executeUpdate(sql);// 下移该对象
					sql = "update basedatat set sortid=sortid-1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);// 上移该对象的上一对象
				}
			}
			st.close();
		} catch (Exception e) {
			logger.error("网页下移失败！", e);
			throw new ElException("网页下移失败", e);
		}
	}

	/**
	 * 验证基础数据库名称
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBase(String name, int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from basedatat where typeid = ? and basevalue=? and status=0");
			ps.setInt(1, type);
			ps.setString(2, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				// return true;
				bool = true;
			} else {
				// return false;
				bool = false;
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("验证基础数据库名称出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}
	/**
	 * 验证基础数据库名称
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBase(String name, int type,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from basedatat where typeid = ? and basevalue=? and status=0 and id!=?");
			ps.setInt(1, type);
			ps.setString(2, name);
			ps.setInt(3, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				// return true;
				bool = true;
			} else {
				// return false;
				bool = false;
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("验证基础数据库名称出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}
	/**
	 * 验证基础数据编号是否重复
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBaseBh(String bh, int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from basedatat where typeid = ? and bh=? and status=0");
			ps.setInt(1, type);
			ps.setString(2, bh);
			rs = ps.executeQuery();
			if (rs.next()) {
				// return true;
				bool = true;
			} else {
				// return false;
				bool = false;
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("验证基础数据编号是否重复出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}
	/**
	 * 验证基础数据编号是否重复
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public boolean checkBaseBh(String bh, int type,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from basedatat where typeid = ? and bh=? and status=0 and id!=?");
			ps.setInt(1, type);
			ps.setString(2, bh);
			ps.setInt(3,id);
			rs = ps.executeQuery();
			if (rs.next()) {
				// return true;
				bool = true;
			} else {
				// return false;
				bool = false;
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("验证基础数据编号是否重复出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}

	/**
	 * 添加用户登录信息(用户登录和pki登录都有调用)
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public void addUserLoginInfo(MyLogin myLogin) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		//检测用户是否有退出时间没有记录到的
		this.checkUserIsExittime(myLogin.getElUser().getId());
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into eluserloginInfo(userid,logintime,exittime,ipaddr) values(?,sysdate,?,?)");
			ps.setInt(1, myLogin.getElUser().getId());
			// ps.setTimestamp(2, myLogin.getLogintime());
			//ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(2, myLogin.getExittime());
			ps.setString(3, myLogin.getIpAddr());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加用户登录信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 查询所有用户登录信息
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public List<MyLogin> getAllUserLoginInfo(MyLogin myLogin, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyLogin> loginList = new ArrayList<MyLogin>();
		String sql = "";
		String sql2 = "";
		if (myLogin != null) {
			if (myLogin.getElUser().getUsername().trim() != null
					&& !myLogin.getElUser().getUsername().trim().equals("")) {
				sql2 += " and eu.username like '%"
						+ myLogin.getElUser().getUsername().trim() + "%'";
			}
			if (myLogin.getElUser().getRealname().trim() != null
					&& !myLogin.getElUser().getRealname().trim().equals("")) {
				sql2 += " and eu.realname like '%"
						+ myLogin.getElUser().getRealname().trim() + "%'";
			}
			// if(myLogin.getElUser().getDepartment().getName().trim()!=null&&!myLogin.getElUser().getDepartment().getName().trim().equals("")){
			// sql2+=" and
			// dep.name="+myLogin.getElUser().getDepartment().getName().trim();
			// }
			if (myLogin.getElUser().getDepartment().getId() != 0) {
				Department dep = new Department();
				try {
					ct = DBConnection.getConnection();
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
					ps.setInt(1, myLogin.getElUser().getDepartment().getId());
					rs = ps.executeQuery();
					if (rs.next()) {
						dep.setId(rs.getInt(1));
						dep.setLid(rs.getInt(2));
						dep.setRid(rs.getInt(3));
					}
					ps.close();
					rs.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("查询所有用户登录信息出错！", e);
				}
				// sql2+=" and
				// dep.name="+myLogin.getElUser().getDepartment().getName().trim();
				sql2 += " and dep.lid>=" + dep.getLid() + " and dep.rid<="
						+ dep.getRid();
			}
			if (myLogin.getLogintime() != null) {
				sql2 += " and eli.logintime >= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(myLogin.getLogintime())
						+ "','yyyy-MM-dd HH24:mi:ss')";
			}
			if (myLogin.getExittime() != null) {
				// 还是判断登录时间
				sql2 += " and eli.logintime <= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(myLogin.getExittime())
						+ "','yyyy-MM-dd HH24:mi:ss')";
			}
			if (myLogin.getIpAddr().trim() != null
					&& !myLogin.getIpAddr().trim().equals("")) {
				sql2 += " and eli.ipaddr like '%" + myLogin.getIpAddr().trim()
						+ "%'";
			}
		}
		try {
			ct = DBConnection.getConnection();
			sql = "select * from (select t.*,rownum rn from( select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,eli.id eliid,eli.logintime,eli.exittime,eli.ipaddr from eluserloginInfo eli left join eluser eu on eli.userid=eu.id left join department dep on eu.depid=dep.id where 1=1";
			sql += sql2;
			sql += " order by eli.logintime desc )t where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			MyLogin mlg = null;
			ELUser user = null;
			while (rs.next()) {
				mlg = new MyLogin();
				mlg.setId(rs.getInt("eliid"));
				mlg.setLogintime(rs.getTimestamp("logintime"));
				mlg.setExittime(rs.getTimestamp("exittime"));
				mlg.setIpAddr(rs.getString("ipaddr"));
				user = new ELUser(rs.getInt("euid"), rs.getString("username"),
						rs.getString("realname"));
				user.setDepartment(new Department(rs.getInt("depid"), rs
						.getString("depname")));
				mlg.setElUser(user);
				loginList.add(mlg);
			}
		} catch (Exception e) {
			logger.error("查询所有用户登录信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return loginList;
	}

	/**
	 * 查询所有用户登录信息数量
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public int getAllUserLoginInfoCount(MyLogin myLogin) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sql2 = "";
		if (myLogin != null) {
			if (myLogin.getElUser().getUsername().trim() != null
					&& !myLogin.getElUser().getUsername().trim().equals("")) {
				sql2 += " and eu.username like '%"
						+ myLogin.getElUser().getUsername().trim() + "%'";
			}
			if (myLogin.getElUser().getRealname().trim() != null
					&& !myLogin.getElUser().getRealname().trim().equals("")) {
				sql2 += " and eu.realname like '%"
						+ myLogin.getElUser().getRealname().trim() + "%'";
			}
			if (myLogin.getElUser().getDepartment().getId() != 0) {
				Department dep = new Department();
				try {
					ct = DBConnection.getConnection();
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
					ps.setInt(1, myLogin.getElUser().getDepartment().getId());
					rs = ps.executeQuery();
					if (rs.next()) {
						dep.setId(rs.getInt(1));
						dep.setLid(rs.getInt(2));
						dep.setRid(rs.getInt(3));
					}
					// ps.close();
					// rs.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("查询所有用户登录信息数量出错！", e);
				}
				sql2 += " and dep.lid>=" + dep.getLid() + " and dep.rid<="
						+ dep.getRid();
			}
			if (myLogin.getLogintime() != null) {
				sql2 += " and eli.logintime >= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(myLogin.getLogintime())
						+ "','yyyy-MM-dd HH24:mi:ss')";
			}
			if (myLogin.getExittime() != null) {
				// 还是判断登录时间
				sql2 += " and eli.logintime <= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(myLogin.getExittime())
						+ "','yyyy-MM-dd HH24:mi:ss')";
			}
			if (myLogin.getIpAddr().trim() != null
					&& !myLogin.getIpAddr().trim().equals("")) {
				sql2 += " and eli.ipaddr like '%" + myLogin.getIpAddr().trim()
						+ "%'";
			}
		}
		try {
			ct = DBConnection.getConnection();
			sql = " select count(*) from eluserloginInfo eli left join eluser eu on eli.userid=eu.id left join department dep on eu.depid=dep.id where 1=1";
			sql += sql2;
			sql += " order by eli.logintime desc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询所有用户登录信息数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 删除所有用户登录信息（搜索结果）
	 * 
	 * @param myLogin
	 * @return
	 * @throws ElException
	 */
	public void delUserLoginInfo(MyLogin myLogin) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sql2 = "";
		if (myLogin != null) {
			if (myLogin.getElUser().getUsername().trim() != null
					&& !myLogin.getElUser().getUsername().trim().equals("")) {
				sql2 += " and eu.username like '%"
						+ myLogin.getElUser().getUsername().trim() + "%'";
			}
			if (myLogin.getElUser().getRealname().trim() != null
					&& !myLogin.getElUser().getRealname().trim().equals("")) {
				sql2 += " and eu.realname like '%"
						+ myLogin.getElUser().getRealname().trim() + "%'";
			}
			if (myLogin.getElUser().getDepartment().getId() != 0) {
				Department dep = new Department();
				try {
					ct = DBConnection.getConnection();
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
					ps.setInt(1, myLogin.getElUser().getDepartment().getId());
					rs = ps.executeQuery();
					if (rs.next()) {
						dep.setId(rs.getInt(1));
						dep.setLid(rs.getInt(2));
						dep.setRid(rs.getInt(3));
					}
					ps.close();
					rs.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("删除所有用户登录信息（搜索结果）出错！", e);
				}
				sql2 += " and dep.lid>=" + dep.getLid() + " and dep.rid<="
						+ dep.getRid();
			}
			if (myLogin.getLogintime() != null) {
				sql2 += " and eli.logintime >= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(myLogin.getLogintime())
						+ "','yyyy-MM-dd HH24:mi:ss')";
			}
			if (myLogin.getExittime() != null) {
				// 还是判断登录时间
				sql2 += " and eli.logintime <= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(myLogin.getExittime())
						+ "','yyyy-MM-dd HH24:mi:ss')";
			}
			if (myLogin.getIpAddr().trim() != null
					&& !myLogin.getIpAddr().trim().equals("")) {
				sql2 += " and eli.ipaddr like '%" + myLogin.getIpAddr().trim()
						+ "%'";
			}
		}
		try {
			ct = DBConnection.getConnection();
			sql = " delete from eluserloginInfo where id in(select eli.id from eluserloginInfo eli left join eluser eu on eli.userid=eu.id left join department dep on eu.depid=dep.id where 1=1";
			sql += sql2;
			sql += " )";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除所有用户登录信息（搜索结果）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 根据用户名获取该用户最后一次的登录信息
	 * 
	 * @param name
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public MyLogin getSessionUserLoginInfo(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyLogin mlg = new MyLogin();
		try {
			ct = DBConnection.getConnection();
			// "select t.*,rownum from (select id,userid,logintime,rownum r from
			// eluserloginInfo where userid=1 order by logintime desc)t where
			// rownum=1"
			ps = ct
					.prepareStatement("select t.* from (select id eliid,userid,logintime,exittime,ipaddr from eluserloginInfo where userid=? order by logintime desc)t where rownum=1");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				mlg.setId(rs.getInt("eliid"));
				mlg.setLogintime(rs.getTimestamp("logintime"));
				mlg.setExittime(rs.getTimestamp("exittime"));
				mlg.setIpAddr(rs.getString("ipaddr"));
			}
		} catch (Exception e) {
			logger.error("根据用户名获取该用户最后一次的登录信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mlg;
	}

	/**
	 * 记录用户退出登录的时间(用户退出，注销，session销毁都有调用)
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void updateSessionUserExittime(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eluserloginInfo set exittime=sysdate where id in(select t.* from (select id from eluserloginInfo where userid=? order by logintime desc)t where rownum=1)");
			ps.setInt(1, userid);
			ps.executeUpdate();
			//检测用户是否有退出时间没有记录到的
			this.checkUserIsExittime(userid);
			//检测在线学习记录
			((StudyCourseDao)SpringContextUtil.getBean("studyCourseDao")).updateStudyCourseRecordStatus(userid, 0, null);
			//检测在线考试记录
			((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).updateStudyQuizinfoRecordStatus(userid, null,"study_quizinfo_record");
		} catch (Exception e) {
			logger.error("记录用户退出登录的时间出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 检测用户是否有退出时间没有记录到的（比如说服务器断电了会引发此情况）
	 * @param userid
	 * @throws ElException
	 */
	public void checkUserIsExittime(int userid) throws ElException {
		//如果有没有记录到的，那么就让退出时间等于登录时间
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update eluserlogininfo set exittime=logintime++5/1440  where id in (select id from eluserlogininfo where exittime is null and userid=?)");
			ps.setInt(1, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检测用户是否有退出时间没有记录到的出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新基础数据的类别
	 * 
	 * @param map
	 * @throws ElException
	 */
	public void updateBasedbType(Map map) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update basedatatype set name=? where id=?");
			ps.setString(1, map.get("name").toString());
			ps.setInt(2, (Integer) map.get("colId"));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新基础数据的类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取所有基础数据类别
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataType> getAllBaseDataType(int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDataType> baseTypeList = new ArrayList<BaseDataType>(5);
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from( select id,name,remack from basedatatype order by id )t where rownum <=? ) where rn >=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
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
	 * 获取所有基础数据类别
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataType> getAllBaseDataType() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDataType> baseTypeList = new ArrayList<BaseDataType>(5);
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,remack from basedatatype order by id ");
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
	 * 根据类别和名字获取基础数据信息
	 * 
	 * @param name(名字)
	 * @param type(1.工种2.职务3.职级4.岗位5.地市)
	 * @return
	 * @throws ElException
	 */
	public BaseDatat getBaseInfo_NameType(String name, int type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BaseDatat base = new BaseDatat();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from basedatat where typeid = ? and basevalue=?");
			ps.setInt(1, type);
			ps.setString(2, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				base.setId(rs.getInt("id"));
				base.setTypeid(rs.getInt("typeid"));
				base.setBasevalue(rs.getString("basevalue"));
				base.setRemack(rs.getString("remack"));
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("编辑基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);

		}
		return base;
	}
	
	public List<ELUser> listUsers(ElNode dep, ElNode sta,int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
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
			String depname = "";
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
			basesql.append( " elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? and sta.name like ? and dep.name like ? ");
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if(null != eu.getXianzhiwei())
					staname = eu.getXianzhiwei().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
				if(null != eu.getDepartment()&&null!=eu.getDepartment().getName()){
					depname = eu.getDepartment().getName();
				}
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
			if(pageNow!=-1&&pageSize!=-1){
				basesql.append(")t where rownum <=? ) where rn >=?");
			}
			ct = DBConnection.getConnection();
			System.out.println(basesql.toString());
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
			ps.setString(4, "%"+staname+"%");
			ps.setString(5, "%"+depname+"%");
			int idx =6;
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
	
	public List<ELUser> wjm_listUsers(ElNode dep, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
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
			basesql.append(") dep on eu.depid = dep.id left join " );
	//		basesql.append(") sta on sta.id=eu.staid left join");
			//20140829去掉现职位查询
			//basesql.append( " elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? and eu.xianzhiwei like ?");
			basesql.append( " elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ?  ");
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
			if(pageNow!=-1&&pageSize!=-1){
				basesql.append(")t where rownum <=? ) where rn >=?");
			}
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
			//ps.setString(4, "%"+staname+"%");
			int idx = 4;
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
			logger.info(basesql.toString());
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
	
	public List<ELUser> wjm_listUsers(ElNode dep, ElNode sta,int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
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
			if(pageNow!=-1&&pageSize!=-1){
				basesql.append(")t where rownum <=? ) where rn >=?");
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

	public List<ELUser> listUsers(ElNode dep, int subdep, ELUser eu)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			boolean consub = subdep == 1 ? true : false;
			String username = "";
			String realname = "";
			String sex = "";
			Date shengri = null;
			Date shengri_end = null;
			int valid = -2;
			int roleid = -2;
			StringBuffer basesql = new StringBuffer(
					" select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ,eu.password,eu.xuhao,eu.dishi,eu.shenfenzheng,eu.zhiji,eu.zhiwu,eu.gangwei,dep.bh from ELUSER eu join (");
			basesql.append( ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, consub));
			basesql.append(") dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ?");
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();

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
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
			int idx = 4;
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
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(9));
				elUser.setJingzhong(rs.getInt(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
				elUser.setPassword(rs.getString(13));
				elUser.setXuhao(rs.getString(14));
				elUser.setDishi(rs.getInt(15));
				elUser.setShenfenzheng(rs.getString(16));
				elUser.setZhiji(rs.getInt(17));
				elUser.setZhiwu(rs.getInt(18));
				elUser.setGangwei(rs.getString(19));
				Department dep1 = new Department(rs.getInt(5), rs.getString(6));
				dep1.setBh(rs.getString(20));
				elUser.setDepartment(dep1);
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

	public int listUsersSize(ElNode dep, ElNode sta,int subdep, ELUser eu)
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
			String depname = "";
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
			basesql.append(	" elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? and sta.name like ? and dep.name like ? ");
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
				if(null!=eu.getDepartment()&&null != eu.getDepartment().getName()){
					depname = eu.getDepartment().getName();
				}
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
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
			ps.setString(4, "%"+staname+"%");
			ps.setString(5, "%"+depname+"%");
			int idx = 6;
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
	
	public int wjm_listUsersSize(ElNode dep, int subdep, ELUser eu)
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
			basesql.append(") dep on eu.depid = dep.id left join ");
	//		basesql.append( ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("station", sta, consub));
	//		basesql.append(") sta on sta.id=eu.staid left join");
			//20140829去掉现职位查询
			//basesql.append(	" elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? and eu.xianzhiwei like ?");
			basesql.append(	" elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ?  ");
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
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
//			ps.setString(4, "%"+staname+"%");
			int idx = 4;
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
	
	public int wjm_listUsersSize(ElNode dep, ElNode sta,int subdep, ELUser eu)
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
	
	/**
	 * 检测用户是否在部门里面
	 * @param map
	 * @throws ElException
	 */
	public boolean checkUserIsInDep(int userid,String depIds) throws ElException {
		//获取用户信息
		ELUser elUser=this.getUserById(userid);//得到用户所在部门的左右id
		//获取所有部门信息
		Department tempDep=null;
		String[] depsArray=depIds.split(",");
		for (int i = 0; i < depsArray.length; i++) {
			tempDep=((DepartmentDao)SpringContextUtil.getBean("departmentDao")).getDepById(Integer.parseInt(depsArray[i]));
			//判断用户所在部门的lid是否大于等于此部门的lid,rid是否小于等于此部门的rid，如果是 那么用户就属于此部门（包含下级）
			if(elUser.getDepartment().getLid()>=tempDep.getLid() && 
					elUser.getDepartment().getRid()<=tempDep.getRid()){
				return true;
			}
		}
		return false;
	}
	/**
	 * 检测用户是否创建过课程是否有学习考试（用于真假删除）
	 * @param courseid
	 * @throws ElException
	 */
	public boolean checkElUserIsUse(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select count(*) ell from eluserlogininfo where userid=?) t1,"+
				" (select count(*) sc from study_course where userid=?) t2,"+
				" (select count(*) sq from study_quizinfo where userid=?) t3,"+
				" (select count(*) c from course where creater=?) t4,"+
				" (select count(*) er from exam_room where createrid=?) t5,"+
				" (select count(*) ec from elclass where creater=?) t6"+
				" where t1.ell>0 or t2.sc>0 or t3.sq>0 or t4.c>0 or t5.er>0 or t6.ec>0");
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, userid);
			ps.setInt(5, userid);
			ps.setInt(6, userid);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			logger.error("检测用户是否创建过课程是否有学习考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	/**
	 * 检测此身份证是否已经被其他用户使用
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserShenfenzheng(String shenfenzheng,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from eluser where shenfenzheng=? and id<>?");
			ps.setString(1, shenfenzheng);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				bool = true;
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("检测此身份证是否已经被其他用户使用出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}
	
	/**
	 * 检测此用户名在数据库中是否存在
	 * @param userName
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserShenfenzhengIsUniqune(String userName,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from eluser where username=? and id<>?");
			ps.setString(1, userName);	 
			ps.setInt(2,id);
			rs = ps.executeQuery();
			if (rs.next()) {
				bool = true;
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("检测此身份证是否已经被其他用户使用出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}
	
	
	/**
	 * 根据身份证获取用户信息
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public ELUser getEluserByShenfenzhang(String shenfenzheng) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ELUser user=null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,username,realname from eluser where shenfenzheng=?");
			ps.setString(1, shenfenzheng);
			rs = ps.executeQuery();
			if (rs.next()) {
				user=new ELUser(rs.getInt(1),rs.getString(2),rs.getString(3));
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("根据身份证获取用户信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return user;
	}
	/**
	 * 获取用户的功能权限
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ElFunc> getEluserFunc(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> elfs=new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select userid,funcid,funccode from eluserfunc elf left join elfunc ef on elf.funcid=ef.id where elf.userid=?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc er = new ElFunc();
				er.setElUser(new ELUser(rs.getInt(1)));
				er.setId(rs.getInt(2));
				er.setFunccode(rs.getString(3));
				elfs.add(er);
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("获取用户的功能权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elfs;
	}

	public List<Integer> getSuoshuDepUserIdByDepid(int userid,int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag = 0;
		String sql= "";
		int parentid = 0;
		List<Integer> list = new ArrayList<Integer>();
		try {
			ct = DBConnection.getConnection();
			
			sql = "select parentid from department where id=" + depid;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				parentid = rs.getInt(1);
			}
			
			CallableStatement cs = ct.prepareCall("{call getSuoshuDepUserIdByUserid(?,?,?,?,?)}");  
			cs.setInt(1, userid);
			cs.setInt(2, depid);
			cs.setInt(3, parentid);
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR);  
			cs.execute();
			flag = cs.getInt(4);
			list.add(flag);
//			rs = (ResultSet) cs.getObject(4);

//			while (rs.next()) {
//			   list.add(rs.getInt(4));
//		    }
			
		} catch (Exception e) {
			logger.error("根据userid获取所属三级部门节点的userid失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void alterUserRole(ELUser elUser,int roleid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update ELUSER set  role = ? where id = ?");
			ps.setInt(1, roleid); 
			ps.setInt(2, elUser.getId());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新个人角色失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 检验用户登陆失败次数（是否锁定  是true ， 否 false）
	 * @param userid
	 * @param number
	 * @return
	 * @throws ElException
	 */
	public boolean checkLogonFailureNumber(int userid,int number) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql ="";
		try {
			ct = DBConnection.getConnection();
			sql ="select count(*) from ELUSERLOGINFAILUREINFO where  " +
				 " to_char(logintime, 'yyyy-mm-dd hh24:mi:ss')> (select to_char(sysdate-0.020833, 'yyyy-mm-dd hh24:mi:ss')  from dual)" +
				 " and userid = ? and ISLOCK != 1"; 
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid); 
			rs = ps.executeQuery();
			if (rs.next()){ 
				if(number<=rs.getInt(1))
				return true;
			}
		} catch (Exception e) {
			logger.error("检验用户登陆失败次数失败！", e);
			throw new ElException(e);
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}	 

	/**
	 * 锁定账号解锁
	 * @param userid
	 * @throws ElException
	 */
	public void deleteLoingFailure(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql ="";
		try {
			ct = DBConnection.getConnection();
			sql = "delete from ELUSERLOGINFAILUREINFO where " +
				  "to_char(logintime, 'yyyy-mm-dd hh24:mi:ss')> (select to_char(sysdate-0.020833, 'yyyy-mm-dd hh24:mi:ss')  from dual) " +
				  "and USERID = ? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("账号解锁！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取目前的在线用户数量
	 * @return
	 * @throws ElException
	 */
	public int getTheCurrentOnlineUsersSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select count(*) from eluserlogininfo where to_char(logintime, 'yyyy-mm-dd') = (select to_char(sysdate, 'yyyy-mm-dd')  from dual) and EXITTIME is null";
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<Integer> getForumUseBaseDataIdByfblockid(int fblockid) throws ElException {
		List<Integer> list = new ArrayList<Integer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select * from fblock_use_huiyuanjibie_type where fblockid="+fblockid;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("huiyuanjibieid"));
			}
		} catch (Exception e) {
			logger.error("根据userid获取对应会员级别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void updateCepingjinduByUserid(int userid, String cepingjindu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			
			ct = DBConnection.getConnection();
			sql = "update eluser set cepingjindu=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, cepingjindu);
			ps.setInt(2, userid);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("更新测评进度失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void updateUserByName(ELUser elUser)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
				ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("update ELUSER set realname=?,role=?,depid=?,valid=?, sex=?, xuhao=?,  dishi=?,  danwei=?,  shenfenzheng=?, shengri=?,  zhiji=?,  zhiwu=?,  jingzhong=?,  gangwei =?,jy =?,movephone=?,email=?,userName=?,password=?," +
					"staid=?,school=?,xuewei=?,Education=?,specialty=?,minzu=?,jiguan=?,canjiagongzuoshijian=?,rusishijian=?,xianrenzhishijian=?," +
					"zhengzhimianmao=?,pinyinjianxie=?,chushengdi=?,xianyuangongzu=?,xianzhiwei=?,zhideng=?,xueli=? where userName = ?");
			ps.setString(1, elUser.getRealname());
			ps.setInt(2, elUser.getRole().getId());
			ps.setInt(3, elUser.getDepartment().getId());
			ps.setBoolean(4, elUser.getValid());
			ps.setString(5, elUser.getSex());
			ps.setString(6, elUser.getXuhao());
			ps.setInt(7, elUser.getDishi());
			ps.setString(8, elUser.getDanwei());
			ps.setString(9, elUser.getShenfenzheng());
			ps.setDate(10, elUser.getShengri());
			ps.setInt(11, elUser.getZhiji());
			ps.setInt(12, elUser.getZhiwu());
			ps.setInt(13, elUser.getJingzhong());
			ps.setString(14, elUser.getGangwei());
			ps.setInt(15, elUser.getJy());
			ps.setString(16, elUser.getMovephone());
			ps.setString(17, elUser.getEmail());
			ps.setString(18, elUser.getUsername());//用户名
			ps.setString(19, elUser.getPassword());
			ps.setInt(20,elUser.getStation().getId());
			ps.setString(21, elUser.getSchool());
			ps.setString(22, elUser.getXuewei());
			ps.setInt(23, elUser.getEducation());
			ps.setString(24, elUser.getSpecialty());
			ps.setString(25, elUser.getMinzu());
			ps.setString(26, elUser.getJiguan());
			ps.setDate(27, elUser.getCanjiagongzuoshijian());
			ps.setDate(28, elUser.getRusishijian());
			ps.setDate(29, elUser.getXianrenzhishijian());
			ps.setString(30, elUser.getZhengzhimianmao());
			ps.setString(31, elUser.getPinyinjianxie());
			ps.setString(32, elUser.getChushengdi());
			ps.setString(33,elUser.getXianyuangongzu());
			ps.setString(34,elUser.getXianzhiwei());
			ps.setString(35, elUser.getZhideng());
			ps.setString(36,elUser.getXueli());
			ps.setString(37, elUser.getUsername());
	
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void insertLoingFailure(MyLogin myLogin) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int LogNumber = 0 ;
		try {
			int max = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("无记录") ? 0 :  SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX);
			boolean islock = checkLogonFailureNumber(myLogin.getElUser().getId(), max);
			ct = DBConnection.getConnection();    
			sql ="select count(*) from ELUSERLOGINFAILUREINFO where  " +
			 " to_char(logintime, 'yyyy-mm-dd hh24:mi:ss')> (select to_char(sysdate-0.020833, 'yyyy-mm-dd hh24:mi:ss')  from dual)" +
			 " and userid = ? and ISLOCK != 1";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, myLogin.getElUser().getId()); 
			rs = ps.executeQuery();
			if(rs.next()){
				LogNumber = rs.getInt(1);
			}
			ps.close(); 
			rs.close();
			
			sql = "insert into ELUSERLOGINFAILUREINFO(userid,LOGINTIME,ISLOCK,LOGNUMBER) values(?,?,?,?)";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, myLogin.getElUser().getId());
			ps.setTimestamp(2, myLogin.getLogintime());
			ps.setInt(3, islock == true ? 1 : 0); 
			ps.setInt(4, LogNumber+1); 
			ps.executeUpdate();
			ps.close();  
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<ELUser> getUserByUserId3(Department depTree ,int depid,int role ,int userid, int roleid, ELUser eu,
			int pageNow, int pageSize) throws ElException {

		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String jz = "";
			String con = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
			//	if (null != eu.getJingzhong())
			//		jz = eu.getJingzhong().trim();
				if (0 != eu.getIsAlter())
					con = con+ " and eu.isalter = "+eu.getIsAlter();
				if (null != eu.getPeixunleibie() && !eu.getPeixunleibie().equals("0"))
					con = con+ " and eu.peixunleibie = '"+eu.getPeixunleibie().trim()+"'";
				if (null != eu.getShifouzaizhi() && !eu.getShifouzaizhi().equals("0"))
					con = con+ " and eu.shifouzaizhi = '"+eu.getShifouzaizhi().trim()+"'"; 
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end()) 
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}
			String roleids = "";
			if(roleid != 0){
				roleids = " and eu.role  = "+roleid;
			}
			String x = Integer.toString(depid);
			String ids = createDepartmentId(depTree, depid);
			if(role != 1 && !ids.equals(x) )//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取 ,当角色不为1时ids的只有一个根节点时也不截取
				ids = depid == 1?ids.substring(x.length()+1,ids.length()):ids; //当id等于虚拟根时,从所有的id中去掉虚拟根id 
			
			ct = DBConnection.getConnection();
 
				
			
			String sql = ""; 
			sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,isalter  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
					+ "where eu.username like ? and eu.realname like ? and eu.sex like ?  "+roleids//
					+ con   
					+ " and dep.id in(" + ids+ "))t1 where rownum <=? ) where rn >=?"; 
				ps = ct.prepareStatement(sql);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");  
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
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
					elUser.setValids(rs.getInt(7));//北京二次开发新增用户状态类型
					elUser.setSex(rs.getString(9));
				//	elUser.setJingzhong(rs.getString(10));
					elUser.setShengri(rs.getDate(11));
					elUser.setAge(rs.getInt(12));
					elUser.setIsAlter(rs.getInt(13));
					if(!SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("无记录")){
						elUser.setIsLock(checkLogonFailureNumber(elUser.getId(), SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX))==true ? 1:0);
					}
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
	
	/**
	 * 查询出从depid开始的有权的部门ID
	 * 
	 * @author HeweiCheng
	 * @param depTree
	 * @param depid
	 * @return
	 */
	private String createDepartmentId(Department depTree, int depid) {
		//String id=depTree.getId()+"";
		if (depTree != null) {
			if (depTree.getId() != depid) {
				depTree = getDepartmentById(depTree.getChild(), depid);
			}
			if (depTree.getChild() != null) {
				return createDepartmentId(depTree.getChild(), depTree.getId());
			}
			return String.valueOf(depTree.getId());
		} else {
			return null;
		}
	}
	public int getUserByUserIdSize3(Department depTree ,int depid ,int role ,int userid, int roleid, ELUser eu) throws ElException {
		int b = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String jz = "";
			String con = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
			//	if (null != eu.getJingzhong())
			//		jz = eu.getJingzhong().trim();
				if (0 != eu.getIsAlter())
					con = con+ " and eu.isalter = "+eu.getIsAlter();
				if (null != eu.getPeixunleibie() && !eu.getPeixunleibie().equals("0"))
					con = con+ " and eu.peixunleibie = '"+eu.getPeixunleibie().trim()+"'";
				if (null != eu.getShifouzaizhi() && !eu.getShifouzaizhi().equals("0"))
					con = con+ " and eu.shifouzaizhi = '"+eu.getShifouzaizhi().trim()+"'"; 
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
				
			} 

			String roleids = "";
			if(roleid != 0){
				roleids = " and eu.role  = "+roleid;
			}
			
			String x = Integer.toString(depid);
			String ids = createDepartmentId(depTree, depid); 
			if(role != 1 && !ids.equals(x) )//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取 ,当角色不为1时ids的只有一个根节点时也不截取
				ids = depid == 1?ids.substring(x.length()+1,ids.length()):ids; //当id等于虚拟根时,从所有的id中去掉虚拟根id 
			
			ct = DBConnection.getConnection(); 
			String sql = ""; 
			sql = " select count( eu.id) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
					+ "where eu.username like ? and eu.realname like ? and eu.sex like ?  " +roleids //and eu.jingzhong like ?  
					+ con +" and dep.id in(" + ids + ") "; 
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "%" + realname + "%");
			ps.setString(3, "%" + sex + "%");  
			rs = ps.executeQuery();
			if (rs.next()) {
				b = rs.getInt(1);
			} 
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}
	
	/**
	 * (non-Javadoc) 获取不指定角色的用户
	 * 
	 * @see com.sopia.duman.dao.UserDao#getUserByDepId(int, int,
	 *      com.sopia.duman.entities.ELUser, int, int)
	 */
	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String jz = "";
			String con = "";
			String isValid="";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
		//		if (null != eu.getJingzhong())
		//			jz = eu.getJingzhong().trim();
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
				if(eu.getNov()!=1){
					isValid=" and eu.valid=? ";
				}
			}
			ct = DBConnection.getConnection();
			// ElConstants.SUBOP_YES包含下级。
			String sql = "";
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}

				ps.close();
				rs.close();
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_SUBS_BYDEPIDANDOS));
				sql = "select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ,eu.password,eu.xuhao,eu.dishi,eu.shenfenzheng,eu.zhiji,eu.zhiwu,eu.gangwei,dep.bh  from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id"
						+ " where eu.username like ? and eu.realname like ? and eu.sex like ? "+isValid// and eu.jingzhong like ?
						+ con
						+ "  and dep.lid >=? and dep.rid<=? ";
				ps = ct.prepareStatement(sql);
				/**
				 * 1.点击USER_QUERY_SUBS_BYDEPIDANDOS
				 * 找到了user.query.subs.bydepidandos 2.如果系统是oracle
				 * 那么请在常量（*Constants.java)同级中找到o_querySqls.sql
				 * 3.可以看到user.query.subs.bydepidandos
				 * 对应的sql语句。如果实在没有请Ctrl+h搜索文件。 4.select * from (select
				 * t1.*,rownum rn from( select eu.id euid,eu.username,
				 * eu.realname,eu.role,dep.id depid,dep.name
				 * depname,eu.valid,er.name ername from ELUSER eu left join
				 * elrole er on er.id = eu.role left join DEPARTMENT dep on
				 * eu.depid = dep.id \ where eu.username like ? and eu.realname
				 * like ? and dep.lid >=? and dep.rid<=? )t1 where rownum <=? )
				 * where rn >=? 5 看到dep.lid>=? an dep.rid<=? 这里就是应用到了左右值的树特性。
				 */

				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%"); 
//				ps.setString(4, "%" + jz + "%");
				if (null != eu&&eu.getNov()!=1) {
					if(eu.getValid()==true){
						ps.setInt(4,1); 
					}else{
						ps.setInt(4,0);
					}
					ps.setInt(5, dep.getLid());
					ps.setInt(6, dep.getRid()); 
				}else{
					ps.setInt(4, dep.getLid());
					ps.setInt(5, dep.getRid()); 
				}
			} else {
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
				sql = "select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
						+ "where eu.username like ? and eu.realname like ? and eu.sex like ? "+isValid// and eu.jingzhong like ? 
						+ con
						+ " and dep.id=?";
				ps = ct.prepareStatement(sql);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
//				ps.setString(4, "%" + jz + "%");
				if(null!=eu&&eu.getNov()!=1){
					if(eu.getValid()==true){
						ps.setInt(4,1); 
					}else{
						ps.setInt(4,0);
					}
					ps.setInt(5, depid); 
				}else{
					ps.setInt(4, depid); 
				}
			}
			//System.out.println(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(9));
			//	elUser.setJingzhong(rs.getString(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
				elUser.setPassword(rs.getString(13));
				elUser.setXuhao(rs.getString(14));
			//	elUser.setDishi(rs.getString(15));
				elUser.setShenfenzheng(rs.getString(16));
			//	elUser.setZhiji(rs.getString(17));
			//	elUser.setZhiwu(rs.getString(18));
				elUser.setGangwei(rs.getString(19));
				Department dep = new Department(rs.getInt(5), rs.getString(6));
				dep.setBh(rs.getString(20));
				elUser.setDepartment(dep); 
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
	
	/**
	 * (non-Javadoc) 获取不指定角色的用户
	 * 
	 * @see com.sopia.duman.dao.UserDao#getUserByDepId(int, int,
	 *      com.sopia.duman.entities.ELUser, int, int)
	 */
	public List<ELUser> getUserByDepId2(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String jz = "";
			String con = "";
			String isValid="";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
		//		if (null != eu.getJingzhong())
		//			jz = eu.getJingzhong().trim();
				if (null != eu.getPeixunleibie() && !eu.getPeixunleibie().equals("0"))
					con = con+ " and eu.peixunleibie = '"+eu.getPeixunleibie().trim()+"'";
				if (0 != eu.getIsAlter())
					con = con+ " and eu.isalter = "+eu.getIsAlter();
				if (null != eu.getShifouzaizhi() && !eu.getShifouzaizhi().equals("0"))
					con = con+ " and eu.shifouzaizhi = '"+eu.getShifouzaizhi().trim()+"'";
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
				if(eu.getNov()!=1){
					isValid=" and eu.valid=? ";
				}
			}
			ct = DBConnection.getConnection();
			// ElConstants.SUBOP_YES包含下级。
			String sql = "";
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}

				ps.close();
				rs.close();
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_SUBS_BYDEPIDANDOS));
				sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ,isalter  from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id"
						+ " where eu.username like ? and eu.realname like ? and eu.sex like ? "+isValid// and eu.jingzhong like ?
						+ con
						+ "  and dep.lid >=? and dep.rid<=? )t1 where rownum <=? ) where rn >=?";
				ps = ct.prepareStatement(sql);
				/**
				 * 1.点击USER_QUERY_SUBS_BYDEPIDANDOS
				 * 找到了user.query.subs.bydepidandos 2.如果系统是oracle
				 * 那么请在常量（*Constants.java)同级中找到o_querySqls.sql
				 * 3.可以看到user.query.subs.bydepidandos
				 * 对应的sql语句。如果实在没有请Ctrl+h搜索文件。 4.select * from (select
				 * t1.*,rownum rn from( select eu.id euid,eu.username,
				 * eu.realname,eu.role,dep.id depid,dep.name
				 * depname,eu.valid,er.name ername from ELUSER eu left join
				 * elrole er on er.id = eu.role left join DEPARTMENT dep on
				 * eu.depid = dep.id \ where eu.username like ? and eu.realname
				 * like ? and dep.lid >=? and dep.rid<=? )t1 where rownum <=? )
				 * where rn >=? 5 看到dep.lid>=? an dep.rid<=? 这里就是应用到了左右值的树特性。
				 */

				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%"); 
//				ps.setString(4, "%" + jz + "%");
				if (null != eu&&eu.getNov()!=1) {
					if(eu.getValid()==true){
						ps.setInt(4,1); 
					}else{
						ps.setInt(4,0);
					}
					ps.setInt(5, dep.getLid());
					ps.setInt(6, dep.getRid());
					ps.setInt(7, pageNow);
					ps.setInt(8, pageSize);
				}else{
					ps.setInt(4, dep.getLid());
					ps.setInt(5, dep.getRid());
					ps.setInt(6, pageNow);
					ps.setInt(7, pageSize);
				}
			} else {
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_BYDEPIDANDOS));
				sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ,isalter  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
						+ "where eu.username like ? and eu.realname like ? and eu.sex like ? "+isValid// and eu.jingzhong like ? 
						+ con
						+ " and dep.id=?)t1 where rownum <=? ) where rn >=?";
				ps = ct.prepareStatement(sql);
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
//				ps.setString(4, "%" + jz + "%");
				if(null!=eu&&eu.getNov()!=1){
					if(eu.getValid()==true){
						ps.setInt(4,1); 
					}else{
						ps.setInt(4,0);
					}
					ps.setInt(5, depid);
					ps.setInt(6, pageNow);
					ps.setInt(7, pageSize);
				}else{
					ps.setInt(4, depid);
					ps.setInt(5, pageNow);
					ps.setInt(6, pageSize);
				}
			}
			//System.out.println(sql);
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
				elUser.setValids(rs.getInt(7));//北京二次开发新增用户状态类型
				elUser.setSex(rs.getString(9));
			//	elUser.setJingzhong(rs.getString(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
			//	elUser.setCompany(new Company(rs.getInt(13), SystemConfOp.getSecondDep(elUser.getDepartment().getId()).getName()));
				elUser.setIsAlter(rs.getInt(14)); 
				if(!SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("无记录")){
					elUser.setIsLock(checkLogonFailureNumber(elUser.getId(), SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX))==true ? 1:0);
				}
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
	public int getUserByDepIdSize2(int depid, int subdep, ELUser eu)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String jz = "";
			String con = "";
			String isValie="";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getSex())
					sex = eu.getSex().trim();
		//		if (null != eu.getJingzhong())
		//			jz = eu.getJingzhong().trim();
				if (0 != eu.getIsAlter())
					con = con+ " and eu.isalter = "+eu.getIsAlter();
				if (null != eu.getPeixunleibie() && !eu.getPeixunleibie().equals("0"))
					con = con+ " and eu.peixunleibie = '"+eu.getPeixunleibie().trim()+"'";
				if (null != eu.getShifouzaizhi() && !eu.getShifouzaizhi().equals("0"))
					con = con+ " and eu.shifouzaizhi = '"+eu.getShifouzaizhi().trim()+"'";
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
				if(eu.getNov()!=1){
					isValie=" and eu.valid=? ";
				}
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_SUBS_SIZE_BYDEPIDANDOS));
				ps = ct
						.prepareStatement("select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.username like ? and eu.realname  like ? and eu.sex like ? "+isValie
								+ con + " and dep.lid >=? and dep.rid<=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
		//		ps.setString(4, "%" + jz + "%");
				if(null!=eu&&eu.getNov()!=1){
					if(eu.getValid()==true){
						ps.setInt(4,1); 
					}else{
						ps.setInt(4,0);
					}
					ps.setInt(5, dep.getLid());
					ps.setInt(6, dep.getRid());
				}else{
					ps.setInt(4, dep.getLid());
					ps.setInt(5, dep.getRid());
				}
			} else {
				// ps = ct.prepareStatement(ElQuerySql
				// .getSQL(DUConstants.USER_QUERY_SIZE_BYDEPIDANDOS));
				ps = ct
						.prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.username like ? and eu.realname like ?  and eu.sex like ? "+isValie
								+ con + " and dep.id=? ");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + sex + "%");
		//		ps.setString(4, "%" + jz + "%");
				if(null!=eu&&eu.getNov()!=1){
					if(eu.getValid()==true){
						ps.setInt(4,1); 
					}else{
						ps.setInt(4,0);
					}
					ps.setInt(5, depid);
				}else{
					ps.setInt(4, depid);
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		}
	
	public ELUser getUserById2(int id) throws ElException {
		ELUser elUser = new ELUser();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_QUERY_BYID));
			ps = ct
					.prepareStatement("select eu.id,eu.username,eu.password,eu.realname,eu.userno,eu.phone,eu.address,eu.email,eu.role,eu.id,eu.depid,eu.id,dep.name,eu.valid,er.name, eu.sex, eu.id, eu.edubg , eu.major , eu.studyDir , eu.gradchool , eu.graddate , eu.jobdate , eu.protitle , eu.jobdesc ,eu.majorc,eu.score,eu.dot,eu.xfscore,"
							+ " renyuanleibie ,zhiwupinrenriqi ,zhichengleibie , zhichengjibie ,zhichengquderiqi , zhichenghao , peixunleibie ,beizhu , kuaijihao , zhengjianleixing , shenfenzheng ,shengri ,kuaijizhengfazhengriqi , kuaijizhengfazhengjiguan ,kuaijizhengyouxiaoqi , kaishikuaijishijian , zhengzhi , xueli , kuaijizhuanyejishuzhiwu , "
							+ "kuaijizhuanyejishuzhiwuriqi , xuewei ,	 school , biyeshijian ,	 specialty ,eu.phone , eu.email , shifouzaizhi ,feixuewei , feixueli ,feibiyeyuanxiao ,feibiyeshijian , feisuoxuezhuanye ,	 lianxifangshi , danwei , danweiaddress ,gangwei , kuaijixingzhengzhiwu , suozaidixingzhengqu , zhuanyezigeleixing ," +
									" zhuanyezigejibie , zhuanyezigehuoqufangshi ,	 zhuanyezigehuoquriqi , zhuanyezigezhengshu , zhucekuaijishi , zhucepinggushi , zhuceshuiwushi ,	 gaoduanrencai , gaoduanrencaileixing ,gaoduanrencairiqi,danweileixing,shenfenzheng "
							+ "from ELUSER eu  left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setPassword(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setUserno(rs.getString(5));
				elUser.setPhone(rs.getString(6));
				elUser.setAddress(rs.getString(7));
				elUser.setEmail(rs.getString(8));
				elUser.setRole(new ElRole(rs.getInt(9), rs.getString(15)));
				elUser.setDepartment(new Department(rs.getInt(11), rs
						.getString(13)));
		//		elUser
		//				.setCompany(new Company(rs.getInt(10), SystemConfOp
		//						.getSecondDep(elUser.getDepartment().getId())
		//						.getName()));
				elUser.setValid(rs.getBoolean(14));
				elUser.setSex(rs.getString(16));
				elUser.setAge(rs.getInt(17));
				elUser.setEdubg(rs.getString(18));
				elUser.setMajor(rs.getString(19));
				elUser.setStudyDir(rs.getString(20));
				elUser.setGradchool(rs.getString(21));
				elUser.setGraddate(rs.getDate(22));
				elUser.setJobdate(rs.getDate(23));
				elUser.setProtitle(rs.getString(24));
				elUser.setJobdesc(rs.getString(25));
				elUser.setMajorc(rs.getString(26));
				elUser.setScore(rs.getInt(27));
				elUser.setDot(rs.getInt(28));
				elUser.setXfscore(rs.getInt(29));
				elUser.setRenyuanleibie(rs.getString(30));
				elUser.setZhiwupinrenriqi(rs.getDate(31));
				elUser.setZhichengleibie(rs.getString(32));
				elUser.setZhichengjibie(rs.getString(33));
				elUser.setZhichengquderiqi(rs.getDate(34));
				elUser.setZhichenghao(rs.getString(35));
				elUser.setPeixunleibie(rs.getString(36));
				elUser.setBeizhu(rs.getString(37));
				elUser.setKuaijihao(rs.getString(38));
				elUser.setZhengjianleixing(rs.getString(39));
				elUser.setShenfenzheng(rs.getString(40));
				elUser.setShengri(rs.getDate(41));
				elUser.setKuaijizhengfazhengriqi(rs.getString(42));
				elUser.setKuaijizhengfazhengjiguan(rs.getString(43));
				elUser.setKuaijizhengyouxiaoqi(rs.getString(44));
				elUser.setKaishikuaijishijian(rs.getString(45));
				elUser.setZhengzhi(rs.getString(46));
				elUser.setXueli(rs.getString(47));
				elUser.setKuaijizhuanyejishuzhiwu(rs.getString(48));
				elUser.setKuaijizhuanyejishuzhiwuriqi(rs.getString(49));
				elUser.setXuewei(rs.getString(50));
				elUser.setSchool(rs.getString(51));
				elUser.setBiyeshijian(rs.getDate(52));
				elUser.setSpecialty(rs.getString(53));
				elUser.setPhone(rs.getString(54));
				elUser.setEmail(rs.getString(55));
				elUser.setShifouzaizhi(rs.getString(56));
				elUser.setFeixuewei(rs.getString(57));
				elUser.setFeixueli(rs.getString(58));
				elUser.setFeibiyeyuanxiao(rs.getString(59));
				elUser.setFeibiyeshijian(rs.getString(60));
				elUser.setFeisuoxuezhuanye(rs.getString(61));
				elUser.setLianxifangshi(rs.getString(62));
				elUser.setDanwei(rs.getString(63));
				elUser.setDanweiaddress(rs.getString(64));
				elUser.setGangwei(rs.getString(65));
				elUser.setKuaijixingzhengzhiwu(rs.getString(66));
				elUser.setSuozaidixingzhengqu(rs.getString(67));
				elUser.setZhuanyezigeleixing(rs.getString(68));
				elUser.setZhuanyezigejibie(rs.getString(69));
				elUser.setZhuanyezigehuoqufangshi(rs.getString(70));
				elUser.setZhuanyezigehuoquriqi(rs.getString(71));
				elUser.setZhuanyezigezhengshu(rs.getString(72));
				elUser.setZhucekuaijishi(rs.getString(73));
				elUser.setZhucepinggushi(rs.getString(74));
				elUser.setZhuceshuiwushi(rs.getString(75));
				elUser.setGaoduanrencai(rs.getString(76));
				elUser.setGaoduanrencaileixing(rs.getString(77));
				elUser.setGaoduanrencairiqi(rs.getString(78));
				elUser.setDanweileixing(rs.getString(79));
				elUser.setShenfenzheng(rs.getString(80));
			}
		} catch (Exception e) {
			logger.error("通过用户id查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		setUserxxInfo(id, elUser);
		return elUser;
	}
	
	public ELUser getUserById_cisco(int id) throws ElException {
		ELUser elUser = null;
		ElRole role = null;
		Department d = null;
		PreparedStatement ps = null; 
		Station st = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			/**
			 * "insert into ELUSER(" +
							"username,password,realname,role,depid," +
							"valid, sex,  dishi,  danwei,shenfenzheng,  " +
							" shengri,  zhiji,  zhiwu,  jingzhong,movephone,  " +
							"email,staid,danweiaddress "
			 */
			String sql = "select eu.id,eu.username,eu.password,eu.realname,eu.role,eu.depid," +
			"eu.valid,eu.sex,eu.dishi,eu.danwei,eu.shenfenzheng," +
			"eu.shengri,eu.zhiji,eu.zhiwu,eu.jingzhong,eu.movephone," +
			"eu.email,eu.staid,eu.danweiaddress," +
			"er.name as ername,d.name as dname,eu.xianzhiwei," +
			"eu.zhiji,eu.zhiwu,eu.jingzhong,eu.dishi ,st.name as stname ,eltx.touxiang,eu.specialty,eu.school,eu.userno,eu.phone  " +
			" from eluser eu,elrole er,department d,station st ,eluser_touxiang eltx  " +
			"" +
			"where eu.staid=st.id and eu.role=er.id and eu.depid=d.id  and eu.id=eltx.id and  eu.id=?";
			ps = ct
					.prepareStatement(sql);
			logger.info(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				role = new ElRole(rs.getInt(5),rs.getString(20));
				d = new Department(rs.getInt(6),rs.getString(21));
				st = new Station(rs.getInt(18),rs.getString(27));
				elUser = new ELUser();
				elUser.setDepartment(d);
				elUser.setRole(role);
				elUser.setStation(st);
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setPassword(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(8));
				elUser.setDanwei(rs.getString(10));
				elUser.setMovephone(rs.getString(16));
				elUser.setEmail(rs.getString(17));
				elUser.setDanweiaddress(rs.getString(19));
				elUser.setShengri(rs.getDate(12));
				elUser.setXianzhiwei(rs.getString(22));
				elUser.setShenfenzheng(rs.getString(11));
				elUser.setZhiji(rs.getInt(23));
				elUser.setJingzhong(rs.getInt(25));
				elUser.setZhiwu(rs.getInt(24));
				elUser.setDishi(rs.getInt(26));
				elUser.setTouxiang(rs.getString(28));
				elUser.setSpecialty(rs.getString(29));
				elUser.setSchool(rs.getString(30));
				elUser.setUserno(rs.getString(31));
				elUser.setPhone(rs.getString(32));
			}
		} catch (Exception e) {
			logger.error("通过用户id查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}
	
	private void setUserxxInfo(int id, ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select xuehao,studentno,danweihao,realname,"
							+ "username,password,kuaijihao,renyuanleibie,zhichengleibie,zhichengjibie,lianxifangshi,"
							+ "sex,minzu,peixunleibie,shifouzaizhi,gangwei,school,biyeshijian,specialty,"
							+ "xueli,xuewei,zhichenghao,zhiwupinrenriqi,zhichengquderiqi,beizhu,headphoto from eluser where id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				elUser.setXuehao(rs.getString(1));
				elUser.setStudentno(rs.getString(2));
				elUser.setDanweihao(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setUsername(rs.getString(5));
				elUser.setPassword(rs.getString(6));
				elUser.setKuaijihao(rs.getString(7));
				elUser.setRenyuanleibie(rs.getString(8));
				elUser.setZhichengleibie(rs.getString(9));
				elUser.setZhichengjibie(rs.getString(10));
				elUser.setLianxifangshi(rs.getString(11));
				elUser.setSex(rs.getString(12));
				elUser.setMinzu(rs.getString(13));
				elUser.setPeixunleibie(rs.getString(14));
				elUser.setShifouzaizhi(rs.getString(15));
				elUser.setGangwei(rs.getString(16));
				elUser.setSchool(rs.getString(17));
				elUser.setBiyeshijian(rs.getDate(18));
				elUser.setSpecialty(rs.getString(19));
				elUser.setXueli(rs.getString(20));
				elUser.setXuewei(rs.getString(21));
				elUser.setZhichenghao(rs.getString(22));
				elUser.setZhiwupinrenriqi(rs.getDate(23));
				elUser.setZhichengquderiqi(rs.getDate(24));
				elUser.setBeizhu(rs.getString(25));
				elUser.setHeadPhoto(rs.getString(26));
			}
		} catch (Exception e) {
			logger.error("通过用户id查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void update2(ELUser elUser) throws ElException { 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement("update eluser set renyuanleibie=? ,zhiwupinrenriqi=? ,zhichengleibie=? , zhichengjibie=? ,zhichengquderiqi=? , zhichenghao=? , peixunleibie=? ,beizhu=? , kuaijihao=? , zhengjianleixing=? , shenfenzheng=? ,shengri=? ,kuaijizhengfazhengriqi=? , kuaijizhengfazhengjiguan=? ,kuaijizhengyouxiaoqi=? , kaishikuaijishijian=? , zhengzhi=? , xueli=? , kuaijizhuanyejishuzhiwu=? , kuaijizhuanyejishuzhiwuriqi=? , xuewei=? ,	 school=? , biyeshijian=? ,	 specialty=? ,"
							+ "	 phone=? , email=? , shifouzaizhi=? ,feixuewei=? , feixueli=? ,feibiyeyuanxiao=? ,feibiyeshijian=? , feisuoxuezhuanye=? ,	 lianxifangshi=? , danwei=? , danweiaddress=? ,gangwei=? , kuaijixingzhengzhiwu=? , suozaidixingzhengqu=? , zhuanyezigeleixing=? , zhuanyezigejibie=? , zhuanyezigehuoqufangshi=? ,	 zhuanyezigehuoquriqi=? , zhuanyezigezhengshu=? , zhucekuaijishi=? , zhucepinggushi=? , zhuceshuiwushi=? ,	 gaoduanrencai=? , gaoduanrencaileixing=? ,gaoduanrencairiqi=?,realname = ?,danweileixing=? ," +
									"role = ? , depid = ?, valid = ? where id = ?");
			ps.setString(1, elUser.getRenyuanleibie());
			ps.setDate(2, elUser.getZhiwupinrenriqi());
			ps.setString(3, elUser.getZhichengleibie());
			ps.setString(4, elUser.getZhichengjibie());
			ps.setDate(5, elUser.getZhichengquderiqi());
			ps.setString(6, elUser.getZhichenghao());
			ps.setString(7, elUser.getPeixunleibie());
			ps.setString(8, elUser.getBeizhu());
			ps.setString(9, elUser.getKuaijihao());
			ps.setString(10, elUser.getZhengjianleixing());
			ps.setString(11, elUser.getShenfenzheng());
			ps.setDate(12, elUser.getShengri());
			ps.setString(13, elUser.getKuaijizhengfazhengriqi());
			ps.setString(14, elUser.getKuaijizhengfazhengjiguan());
			ps.setString(15, elUser.getKuaijizhengyouxiaoqi());
			ps.setString(16, elUser.getKaishikuaijishijian());
			ps.setString(17, elUser.getZhengzhi());
			ps.setString(18, elUser.getXueli());
			ps.setString(19, elUser.getKuaijizhuanyejishuzhiwu());
			ps.setString(20, elUser.getKuaijizhuanyejishuzhiwuriqi());
			ps.setString(21, elUser.getXuewei());
			ps.setString(22, elUser.getSchool());
			ps.setDate(23, elUser.getBiyeshijian());
			ps.setString(24, elUser.getSpecialty());
			ps.setString(25, elUser.getPhone());
			ps.setString(26, elUser.getEmail());
			ps.setString(27, elUser.getShifouzaizhi());
			ps.setString(28, elUser.getFeixuewei());
			ps.setString(29, elUser.getFeixueli());
			ps.setString(30, elUser.getFeibiyeyuanxiao());
			ps.setString(31, elUser.getFeibiyeshijian());
			ps.setString(32, elUser.getFeisuoxuezhuanye());
			ps.setString(33, elUser.getLianxifangshi());
			ps.setString(34, elUser.getDanwei());
			ps.setString(35, elUser.getDanweiaddress());
			ps.setString(36, elUser.getGangwei());
			ps.setString(37, elUser.getKuaijixingzhengzhiwu());
			ps.setString(38, elUser.getSuozaidixingzhengqu());
			ps.setString(39, elUser.getZhuanyezigeleixing());
			ps.setString(40, elUser.getZhuanyezigejibie());
			ps.setString(41, elUser.getZhuanyezigehuoqufangshi());
			ps.setString(42, elUser.getZhuanyezigehuoquriqi());
			ps.setString(43, elUser.getZhuanyezigezhengshu());
			ps.setString(44, elUser.getZhucekuaijishi());
			ps.setString(45, elUser.getZhucepinggushi());
			ps.setString(46, elUser.getZhuceshuiwushi());
			ps.setString(47, elUser.getGaoduanrencai());
			ps.setString(48, elUser.getGaoduanrencaileixing());
			ps.setString(49, elUser.getGaoduanrencairiqi());
			ps.setString(50, elUser.getRealname());
			ps.setString(51, elUser.getDanweileixing());
			ps.setInt(52, elUser.getRole().getId());
		//	ps.setInt(53, elUser.getCompany().getId());
			ps.setInt(53, elUser.getDepartment().getId());
			ps.setBoolean(54, elUser.getValid()); 
		//	ps.setDate(55, elUser.getShengri());
			ps.setInt(55, elUser.getId()); 
			ps.executeUpdate(); 
			alterUserxxInfo(elUser);
		} catch (Exception e) {
			logger.error("更新个人设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
	}
	
	public void update_cisco(ELUser elUser) throws ElException { 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection(); 
			/**
			 * select eu.id,eu.username,eu.password,eu.realname,eu.role,eu.depid," +
										"eu.valid,eu.sex,eu.dishi,eu.danwei,eu.shenfenzheng," +
										"eu.shengri,eu.zhiji,eu.zhiwu,eu.jingzhong,eu.movephone," +
										"eu.email,eu.statid,eu.danweiaddress," +
										"er.name as ername,d.name as dname " +
										" from eluser eu,elrole er,department d  " +
										"" +
										"where eu.role=er.id and eu.depid=d.id and  id=?
			 */
			ps = ct.prepareStatement("update eluser set realname=?,role=?,depid=?,valid=?,sex=?,dishi=?,danwei=?,shenfenzheng=?," +
					"shengri=?,zhiji=?,zhiwu=?,jingzhong=?,movephone=?,email=?,staid=?,danweiaddress=?,xianzhiwei=?,username=?,headphoto=?,userno=?,school=?,specialty=? where id = ?");
			ps.setString(1, elUser.getRealname());
			ps.setInt(2, elUser.getRole().getId());
			ps.setInt(3, elUser.getDepartment().getId());
			ps.setBoolean(4, elUser.getValid());
			ps.setString(5,elUser.getSex());
			ps.setInt(6, elUser.getDishi());
			ps.setString(7, elUser.getDanwei());
			ps.setString(8, elUser.getShenfenzheng());
			ps.setDate(9, elUser.getShengri());
			ps.setInt(10,elUser.getZhiji());
			ps.setInt(11, elUser.getZhiwu());
			ps.setInt(12, elUser.getJingzhong());
			ps.setString(13, elUser.getMovephone());
			ps.setString(14, elUser.getEmail());
			ps.setInt(15, elUser.getStation().getId());
			ps.setString(16, elUser.getDanweiaddress());
			ps.setString(17, elUser.getXianzhiwei());
//			ps.setString(18, elUser.getPassword());
			ps.setString(18, elUser.getUsername());
			ps.setString(19, elUser.getTouxiang());
			ps.setString(20, elUser.getUserno());
			ps.setString(21, elUser.getSchool());
			ps.setString(22, elUser.getSpecialty());
			ps.setInt(23, elUser.getId());
			ps.executeUpdate(); 
			
//			ps.close();
//			ps = ct.prepareStatement("select eluser_sequence.currval from dual");
//			rs = ps.executeQuery();
//			
//			if (rs.next())
//				elUser.setId(rs.getInt(1));
			this.deletetouxiang(elUser.getId());
			this.usertouxiang(elUser.getId(), elUser.getTouxiang());
		} catch (Exception e) {
			logger.error("更新个人设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
	}
	public void update_wjm(ELUser elUser) throws ElException { 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement("update eluser set realname=?,role=?,depid=?,valid=?,sex=?,dishi=?,danwei=?,shenfenzheng=?," +
					"shengri=?,zhiji=?,zhiwu=?,jingzhong=?,movephone=?,email=?,staid=?,danweiaddress=?,xianzhiwei=?,username=?,headphoto=?,userno=?,specialty=?,school=? where id = ?");
			ps.setString(1, elUser.getRealname());
			ps.setInt(2, elUser.getRole().getId());
			ps.setInt(3, elUser.getDepartment().getId());
			ps.setBoolean(4, elUser.getValid());
			ps.setString(5,elUser.getSex());
			ps.setInt(6, elUser.getDishi());
			ps.setString(7, elUser.getDanwei());
			ps.setString(8, elUser.getShenfenzheng());
			ps.setDate(9, elUser.getShengri());
			ps.setInt(10,elUser.getZhiji());
			ps.setInt(11, elUser.getZhiwu());
			ps.setInt(12, elUser.getJingzhong());
			ps.setString(13, elUser.getMovephone());
			ps.setString(14, elUser.getEmail());
			ps.setInt(15, elUser.getStation().getId());
			ps.setString(16, elUser.getDanweiaddress());
			ps.setString(17, elUser.getXianzhiwei());
//			ps.setString(18, elUser.getPassword());
			ps.setString(18, elUser.getUsername());
			ps.setString(19, elUser.getTouxiang());
			ps.setString(20, elUser.getUserno());
			ps.setString(21, elUser.getSpecialty());
			ps.setString(22, elUser.getSchool());
			ps.setInt(23, elUser.getId());
			ps.executeUpdate(); 
			
//			ps.close();
//			ps = ct.prepareStatement("select eluser_sequence.currval from dual");
//			rs = ps.executeQuery();
//			
//			if (rs.next())
//				elUser.setId(rs.getInt(1));
			this.deletetouxiang(elUser.getId());
			this.usertouxiang(elUser.getId(), elUser.getTouxiang());
		} catch (Exception e) {
			logger.error("更新个人设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
	}
	
	private void alterUserxxInfo(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eluser set xuehao=?,studentno=?,danweihao=?,realname=?,"
							+ " kuaijihao=?,renyuanleibie=?,zhichengleibie=?,zhichengjibie=?,lianxifangshi=?,"
							+ "sex=?,minzu=?,peixunleibie=?,shifouzaizhi=?,gangwei=?,school=?,biyeshijian=?,specialty=?,"
							+ "xueli=?,xuewei=?,zhichenghao=?,zhiwupinrenriqi=?,zhichengquderiqi=?,beizhu=?,depid =? where id = ?");
			ps.setString(1, elUser.getXuehao());
			ps.setString(2, elUser.getStudentno());
			ps.setString(3, elUser.getDanweihao());
			ps.setString(4, elUser.getRealname());
			ps.setString(5, elUser.getKuaijihao());
			ps.setString(6, elUser.getRenyuanleibie());
			ps.setString(7, elUser.getZhichengleibie());
			ps.setString(8, elUser.getZhichengjibie());
			ps.setString(9, elUser.getLianxifangshi());
			ps.setString(10, elUser.getSex());
			ps.setString(11, elUser.getMinzu());
			ps.setString(12, elUser.getPeixunleibie());
			ps.setString(13, elUser.getShifouzaizhi());
			ps.setString(14, elUser.getGangwei());
			ps.setString(15, elUser.getSchool());
			ps.setDate(16, elUser.getBiyeshijian());
			ps.setString(17, elUser.getSpecialty());
			ps.setString(18, elUser.getXueli());
			ps.setString(19, elUser.getXuewei());
			ps.setString(20, elUser.getZhichenghao());
			ps.setDate(21, elUser.getZhiwupinrenriqi());
			ps.setDate(22, elUser.getZhichengquderiqi());
			ps.setString(23, elUser.getBeizhu());
			ps.setInt(24, elUser.getDepartment().getId());
			// ps.setInt(27, elUser.getCompany().getId());
//			ps.setString(25, elUser.getPassword());
			ps.setInt(25, elUser.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void alterMyInfo2(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_MYINFO_ALTER));
			ps = ct
					.prepareStatement("update eluser set renyuanleibie=? ,zhiwupinrenriqi=? ,zhichengleibie=? , zhichengjibie=? ,zhichengquderiqi=? , zhichenghao=? , peixunleibie=? ,beizhu=? , kuaijihao=? , zhengjianleixing=? , shenfenzheng=? ,shengri=? ,kuaijizhengfazhengriqi=? , kuaijizhengfazhengjiguan=? ,kuaijizhengyouxiaoqi=? , kaishikuaijishijian=? , zhengzhi=? , xueli=? , kuaijizhuanyejishuzhiwu=? , kuaijizhuanyejishuzhiwuriqi=? , xuewei=? ,	 school=? , biyeshijian=? ,	 specialty=? ,"
							+ "	 phone=? , email=? , shifouzaizhi=? ,feixuewei=? , feixueli=? ,feibiyeyuanxiao=? ,feibiyeshijian=? , feisuoxuezhuanye=? ,	 lianxifangshi=? , danwei=? , danweiaddress=? ,gangwei=? , kuaijixingzhengzhiwu=? , suozaidixingzhengqu=? , zhuanyezigeleixing=? , zhuanyezigejibie=? , zhuanyezigehuoqufangshi=? ,	 zhuanyezigehuoquriqi=? , zhuanyezigezhengshu=? , zhucekuaijishi=? , zhucepinggushi=? , zhuceshuiwushi=? ,	 gaoduanrencai=? , gaoduanrencaileixing=? ,gaoduanrencairiqi=?,realname = ?,danweileixing=?,isalter = 2where id = ?");
			ps.setString(1, elUser.getRenyuanleibie());
			ps.setDate(2, elUser.getZhiwupinrenriqi());
			ps.setString(3, elUser.getZhichengleibie());
			ps.setString(4, elUser.getZhichengjibie());
			ps.setDate(5, elUser.getZhichengquderiqi());
			ps.setString(6, elUser.getZhichenghao());
			ps.setString(7, elUser.getPeixunleibie());
			ps.setString(8, elUser.getBeizhu());
			ps.setString(9, elUser.getKuaijihao());
			ps.setString(10, elUser.getZhengjianleixing());
			ps.setString(11, elUser.getShenfenzheng());
			ps.setDate(12, elUser.getShengri());
			ps.setString(13, elUser.getKuaijizhengfazhengriqi());
			ps.setString(14, elUser.getKuaijizhengfazhengjiguan());
			ps.setString(15, elUser.getKuaijizhengyouxiaoqi());
			ps.setString(16, elUser.getKaishikuaijishijian());
			ps.setString(17, elUser.getZhengzhi());
			ps.setString(18, elUser.getXueli());
			ps.setString(19, elUser.getKuaijizhuanyejishuzhiwu());
			ps.setString(20, elUser.getKuaijizhuanyejishuzhiwuriqi());
			ps.setString(21, elUser.getXuewei());
			ps.setString(22, elUser.getSchool());
			ps.setDate(23, elUser.getBiyeshijian());
			ps.setString(24, elUser.getSpecialty());
			ps.setString(25, elUser.getPhone());
			ps.setString(26, elUser.getEmail());
			ps.setString(27, elUser.getShifouzaizhi());
			ps.setString(28, elUser.getFeixuewei());
			ps.setString(29, elUser.getFeixueli());
			ps.setString(30, elUser.getFeibiyeyuanxiao());
			ps.setString(31, elUser.getFeibiyeshijian());
			ps.setString(32, elUser.getFeisuoxuezhuanye());
			ps.setString(33, elUser.getLianxifangshi());
			ps.setString(34, elUser.getDanwei());
			ps.setString(35, elUser.getDanweiaddress());
			ps.setString(36, elUser.getGangwei());
			ps.setString(37, elUser.getKuaijixingzhengzhiwu());
			ps.setString(38, elUser.getSuozaidixingzhengqu());
			ps.setString(39, elUser.getZhuanyezigeleixing());
			ps.setString(40, elUser.getZhuanyezigejibie());
			ps.setString(41, elUser.getZhuanyezigehuoqufangshi());
			ps.setString(42, elUser.getZhuanyezigehuoquriqi());
			ps.setString(43, elUser.getZhuanyezigezhengshu());
			ps.setString(44, elUser.getZhucekuaijishi());
			ps.setString(45, elUser.getZhucepinggushi());
			ps.setString(46, elUser.getZhuceshuiwushi());
			ps.setString(47, elUser.getGaoduanrencai());
			ps.setString(48, elUser.getGaoduanrencaileixing());
			ps.setString(49, elUser.getGaoduanrencairiqi());
			ps.setString(50, elUser.getRealname());
			ps.setString(51, elUser.getDanweileixing());
			ps.setInt(52, elUser.getId());
			ps.executeUpdate();
			alterUserxxInfo(elUser);
		} catch (Exception e) {
			logger.error("更新个人设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 锁定用户
	 */
	public void insertLoingFailure2(MyLogin myLogin) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			int max = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("无记录") ? 0 :  SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX);
			boolean islock = checkLogonFailureNumber(myLogin.getElUser().getId(), max);
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into ELUSERLOGINFAILUREINFO(userid,LOGINTIME,ISLOCK,LOGNUMBER) values(?,?,?,?)");
			ps.setInt(1, myLogin.getElUser().getId());
			ps.setTimestamp(2, myLogin.getLogintime());
			ps.setInt(3, islock == true ? 1 : 0); 
			ps.setInt(4, myLogin.getLognumber()); 
			ps.executeUpdate(); 
		} catch (Exception e) {
			logger.error("锁定用户失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	} 
	
	/**
	 * 按照role名字获取roleid
	 * @param roleName
	 * @return
	 * @throws ElException
	 */
	public int getEURoleByName(String roleName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from elrole where name = ?");
			ps.setString(1, roleName); 
			rs = ps.executeQuery();
			if (rs.next()){
				return rs.getInt(1); 
			}else{ 
				return 0; 
			}
		} catch (Exception e) {
			logger.error("设置用户权限失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkShenfenzhengIsExsit(String shenfenzheng)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(1) from eluser where shenfenzheng=?");
			ps.setString(1, shenfenzheng);
			rs = ps.executeQuery();
			if (rs.next())
				if(rs.getInt(1)>0)
					flag =  true;
		} catch (Exception e) {
			logger.error("验证身份证唯一失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	
	/**
	 * 验证码插入数据库
	 */
	public void insertYzCode(String movephone,String content) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into yzcode_temp(movephone,yzcode,status) values(?,?,1)");
			ps.setString(1, movephone);
			ps.setString(2, content);
			ps.executeUpdate(); 
		} catch (Exception e) {
			logger.error("添加失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	} 
	
	/**
	 * 修改验证码
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public void updateYzCode(String movephone,String content) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update yzcode_temp set status=0 where movephone=?");
			ps.setString(1, movephone);
			ps.executeUpdate(); 
		} catch (Exception e) {
			logger.error("更新失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 查询验证码
	 * @param shenfenzheng
	 * @return
	 * @throws ElException
	 */
	public int  searchYzCode(String movephone,String content) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag= 0 ;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select * from yzcode_temp where movephone=? ");
			ps.setString(1, movephone);
		    rs = ps.executeQuery(); 
		    if(rs.next()){
		    	flag=1;
		    	
		    }
		} catch (Exception e) {
			logger.error("更新失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	
	
	public String sendMsg(String movephone,String content,String yzcode) throws ElException {
		UserDao userDao=((UserDao)SpringContextUtil.getBean("userDao"));
		int flag = userDao.searchYzCode(movephone, yzcode);
		if(flag>0){
			userDao.updateYzCode(movephone, yzcode);
			userDao.insertYzCode(movephone, yzcode);
			//调用短信帮助类
			if(!movephone.toString().equals("")){
				return new SendMsgUtil().sendMsg(movephone.toString(),content);
			}else{
				return "接收人的手机号码都有误！";
			}
		}else{
		userDao.insertYzCode(movephone, yzcode);
		//调用短信帮助类
		if(!movephone.toString().equals("")){
			return new SendMsgUtil().sendMsg(movephone.toString(),content);
		}else{
			return "接收人的手机号码都有误！";
		}
		}
	}
	
	public int checkMsg(String movephone,String yzcode) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag= 0 ;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select * from yzcode_temp where movephone=?  and yzcode =? and status=1 ");
			ps.setString(1, movephone);
			ps.setString(2, yzcode);
		    rs = ps.executeQuery(); 
		    if(rs.next()){
		    	flag=1;
		    	
		    }
		} catch (Exception e) {
			logger.error("更新失败！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	
	public ELUser getUserById_wjm(int id) throws ElException {
		ELUser elUser = null;
		ElRole role = null;
		Department d = null;
		PreparedStatement ps = null; 
		Station st = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			/**
			 * "insert into ELUSER(" +
							"username,password,realname,role,depid," +
							"valid, sex,  dishi,  danwei,shenfenzheng,  " +
							" shengri,  zhiji,  zhiwu,  jingzhong,movephone,  " +
							"email,staid,danweiaddress "
			 */
			ps = ct
					.prepareStatement("select eu.id,eu.username,eu.password,eu.realname,eu.role,eu.depid," +
										"eu.valid,eu.sex,eu.dishi,eu.danwei,eu.shenfenzheng," +
										"eu.shengri,eu.zhiji,eu.zhiwu,eu.jingzhong,eu.movephone," +
										"eu.email,eu.staid,eu.danweiaddress," +
										"er.name as ername,d.name as dname,eu.xianzhiwei," +
										"eu.zhiji,eu.zhiwu,eu.jingzhong,eu.dishi ,st.name as stname ,eltx.touxiang,eu.specialty,eu.school,eu.userno,fi.finger_info  " +
										" from eluser eu,elrole er,department d,station st ,eluser_touxiang eltx ,fingerInfo fi " +
										"" +
										"where eu.staid=st.id and eu.role=er.id and eu.depid=d.id  and eu.id=eltx.id and eu.id=fi.userid(+) and  eu.id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				role = new ElRole(rs.getInt(5),rs.getString(20));
				d = new Department(rs.getInt(6),rs.getString(21));
				st = new Station(rs.getInt(18),rs.getString(27));
				elUser = new ELUser();
				elUser.setDepartment(d);
				elUser.setRole(role);
				elUser.setStation(st);
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setPassword(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(8));
				elUser.setDanwei(rs.getString(10));
				elUser.setMovephone(rs.getString(16));
				elUser.setEmail(rs.getString(17));
				elUser.setDanweiaddress(rs.getString(19));
				elUser.setShengri(rs.getDate(12));
				elUser.setXianzhiwei(rs.getString(22));
				elUser.setShenfenzheng(rs.getString(11));
				elUser.setZhiji(rs.getInt(23));
				elUser.setJingzhong(rs.getInt(25));
				elUser.setZhiwu(rs.getInt(24));
				elUser.setDishi(rs.getInt(26));
				elUser.setTouxiang(rs.getString(28));
				elUser.setSpecialty(rs.getString(29));
				elUser.setSchool(rs.getString(30));
				elUser.setUserno(rs.getString(31));
				elUser.setFingerInfo(rs.getString(32));
			}
		} catch (Exception e) {
			logger.error("通过用户id查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}

	public boolean checkRealname(String realname) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from eluser where realname like ?");
			ps.setString(1, "%"+realname+"%");
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkStudyCourse(String realname, int courseid, int elclassid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select el.id,el.realname,el.username,sc.userid,sc.courseid,sc.classid from eluser el left join study_course sc on el.id=sc.userid where el.realname like ? and sc.courseid=? and sc.classid=?");
			ps.setString(1, "%"+realname+"%");
			ps.setInt(2, courseid);
			ps.setInt(3, elclassid);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	public boolean checkStudyClass(String realname, int elclassid)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select el.id,el.realname,el.username,sc.userid,sc.classid from eluser el left join study_class sc on el.id=sc.userid where el.realname like ? and sc.classid=?");
			ps.setString(1, "%"+realname+"%");
			ps.setInt(2, elclassid);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
			return false;
	}

	public ELUser getUserByRealname(String realname) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ELUser user = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from eluser where realname like ?");
			ps.setString(1, "%"+realname+"%");
			rs = ps.executeQuery();
			if(rs.next()){
				user = new ELUser();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return user;
	}

	public int getUserIdByDepid(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from eluser where role=2 and depid=?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	//sd1230-----------------------------
	public List<ELUser> listUsers_sd(ElNode dep, ElNode sta,int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
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
			basesql.append( " elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ?  and eu.role=4 ");
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
//				if (eu.getRole() != null && eu.getRole().getId() > 0) {
//					basesql.append(" and eu.role =  ?");
//					roleid = eu.getRole().getId();
//				}
				if (0!=eu.getZhiwu()){
					basesql.append(" and eu.zhiwu= ?");
					zhiwu = eu.getZhiwu();
				}
				if (0!=eu.getJingzhong()){
					basesql.append(" and eu.jingzhong= ?");
					jingzhong = eu.getJingzhong();
				}
			}  
			if(pageNow!=-1&&pageSize!=-1){
				basesql.append(")t where rownum <=? ) where rn >=?");
			}
			ct = DBConnection.getConnection();
			logger.info(basesql.toString());
			ps =ct.prepareStatement(basesql.toString());
			ps.setString(1, "%"+username+"%");
			ps.setString(2, "%"+realname+"%");
			ps.setString(3, "%"+sex+"%");
//			ps.setString(4, "%"+staname+"%");
			int idx = 4;
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
	
	
	public int listUsersSize_sd(ElNode dep, ElNode sta,int subdep, ELUser eu)
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
			basesql.append(	" elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? and sta.name like ? and eu.role=4 ");
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
//				if (eu.getRole() != null && eu.getRole().getId() > 0) {
//					basesql.append(" and eu.role =  ?");
//					roleid = eu.getRole().getId();
//				}
			}  
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(basesql.toString());
			logger.info(basesql.toString());
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

	
	//sd1231
	/**
	 * 山东项目
	
	* @Title: insert_sd  
	
	* @Description: TODO 
	
	* @param @param elUser
	* @param @return
	* @param @throws ElException      
	
	* @return int     
	
	* @throws
	 */
	public int insert_sd(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into ELUSER(" +
							"id,username,password,realname,role,depid," +
							"valid, sex, shenfenzheng,  " +
							" shengri,  movephone,  " +
							"email,danweiaddress,jingzhong,staid,gangwei,danwei ,xianzhiwei   " +
							" ) values(" +
							"eluser_sequence.nextval,?,?,?,?,?," +
							"?,?,?,?,?," +
							"?,?,?,?,?,?,?)");
			ps.setString(1,  elUser.getUsername());
			ps.setString(2, elUser.getPassword());
			ps.setString(3, elUser.getRealname());
			ps.setInt(4, elUser.getRole().getId());
			ps.setInt(5, elUser.getDepartment().getId());
			
			ps.setBoolean(6, elUser.getValid());
			ps.setString(7, elUser.getSex());
			ps.setString(8, elUser.getShenfenzheng());
			ps.setDate(9, elUser.getShengri());
			ps.setString(10, elUser.getMovephone());
			ps.setString(11, elUser.getEmail());
			ps.setString(12, elUser.getDanweiaddress());
			ps.setInt(13,elUser.getJingzhong());
			ps.setInt(14, 16363);
			ps.setString(15, elUser.getGangwei());
			ps.setString(16, elUser.getDanwei());
			ps.setString(17, elUser.getDanweiaddress());
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select eluser_sequence.currval from dual");
			rs = ps.executeQuery();
			
			if (rs.next())
				elUser.setId(rs.getInt(1));
			this.usertouxiang(elUser.getId(), elUser.getTouxiang());
//			
//			
//			
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select eluser_SEQUENCE.currval from dual");
//			rs = ps.executeQuery();
//			
//			if(rs.next()){
//				id = rs.getInt(1);
//			}
			
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	//山东项目检测用户是否选班
	public boolean isCheckElClass(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag=false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_class where userid=?");
			ps.setInt(1,  elUser.getId());
			rs = ps.executeQuery();
			if(rs.next()){
				flag=true;
			}
			
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
		
	}
	
	//山东项目将学员插入培训班列表
	public int insert_sc(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_class(classid,userid,applydate,status,joinway) values(2050,?,sysdate,1,0)");
			ps.setInt(1,  elUser.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	//山东项目将试卷分配给学员
	public int insert_se(ELUser elUser,int epid,int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_exampaper(classid,userid,epid,roomid) values(2050,?,?,?)");
			ps.setInt(1,  elUser.getId());
			ps.setInt(2,  epid);
			ps.setInt(3,  roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	//山东项目将试卷分配给学员
	public int insert_sr(ELUser elUser,int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_room(classid,userid,roomid,joinway) values(2050,?,?,3)");
			ps.setInt(1,  elUser.getId());
			ps.setInt(2,  roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	//山东项目将试卷分配给学员
	public int insert_sce(ELUser elUser,int courseid,String starttime,String endtime) throws ElException  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(
							"insert into study_course\n" +
							"  (userid,\n" + 
							"   courseid,\n" + 
							"   passed,\n" + 
							"   passtime,\n" + 
							"   process,\n" + 
							"   starttime,\n" + 
							"   finishtime,\n" + 
							"   status,\n" + 
							"   classid,\n" + 
							"   mycredit,\n" + 
							"   passtime_2,\n" + 
							"   ceping)\n" + 
							"values\n" + 
							"  ( ?,?,0, 0, 0, to_date(?,'yyyy/MM/dd hh24:mi:ss'), to_date(?,'yyyy/MM/dd hh24:mi:ss'), 1, 2050, 0, 0, 0)");
			ps.setInt(1,  elUser.getId());
			ps.setInt(2,  courseid);
			ps.setString(3, starttime);
			ps.setString(4, endtime);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	
	//sd0102
	/**
	 * 根据类别查询数据（只显示超级管理员创建的数据）
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeidc_sd(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatat> baseList = new ArrayList<BaseDatat>();
		try {
			ct = DBConnection.getConnection();
			if (typeid == -1) {//全部
				ps = ct
						.prepareStatement("select * from basedatat bd left join eluser eu on bd.createrid=eu.id where bd.status!=1 and  bd.basevalue != '除外' and eu.role=1 order by bd.typeid,bd.sortid ");
			} else {
				ps = ct
						.prepareStatement("select * from basedatat bd left join eluser eu on bd.createrid=eu.id where bd.typeid=? and bd.status!=1  and  bd.basevalue != '除外' and eu.role=1 order by bd.sortid ");
				ps.setInt(1, typeid);
			}
			rs = ps.executeQuery();
			BaseDatat bd = null;
			while (rs.next()) {
				bd = new BaseDatat();
				bd.setId(rs.getInt("id"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setSortid(rs.getInt("sortid"));
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
	
	
	//----------------------------------------sd0109-------------------------------------------------------
	
	
	
	/**
	 * 根据类别查询数据数量
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public int getBaseTypeCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
				ps = ct
						.prepareStatement("select count(*) from basedatatype bdt order by id ");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	
	/**
	 * 添加基础数据
	 * 
	 * @param bd
	 * @throws ElException
	 */
	public void addBaseType(BaseDataType bd) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into basedatatype(id,name,remack,status) values (basedatatype_sequence.nextval,?,?,0)");
			ps.setString(1, bd.getName());
			ps.setString(2, bd.getRemack());
			ps.executeUpdate();
		} catch (Exception e) { 
			logger.error("添加基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 删除基础数据
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void delBaseType(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			
			ps = ct
				.prepareStatement("delete from basedatatype where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("删除基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	 //sd021修改
	/**
	 * 获取当前登陆人数
	 * 
	 * @param id
	 * @throws ElException
	 */
	public int  loginCount() throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
				ps = ct
						.prepareStatement(
								"select count(1)\n" +
								"  from eluserloginInfo\n" + 
								" where logintime is not null\n" + 
								"   and exittime is null\n" + 
								"   and logintime > sysdate - interval '10' hour ");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 20140827增加指纹识别
	 * @param eluser
	 * @return
	 * @throws ElException
	 */
	public int insertFingerInfo(ELUser eluser)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag=0;
		try {
			ct = DBConnection.getConnection();
			String sql ="delete from fingerinfo where userid=?";
			ps = ct
					.prepareStatement(sql);
			ps.setInt(1, eluser.getId());
			ps.execute();
			ps.close();
			
			 sql ="insert into fingerinfo (userid,finger_info) values(?,?)";
				ps = ct
						.prepareStatement(sql);
				ps.setInt(1, eluser.getId());
				ps.setString(2, eluser.getFingerInfo());
			 ps.execute();
			 flag=eluser.getId();
			
		} catch (Exception e) {
			flag=-1;
			logger.error("根据类别查询数据数量出错！", e);
			throw new ElException(e);
			
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	
	
	/**
	 * 20140827增加指纹识别用户查询
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getUserByFingerInfo() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			
			String sql="select userid,finger_info from fingerinfo";
			ps=ct.prepareStatement(sql);
			rs = ps.executeQuery();
			ELUser eu = null;
			while (rs.next()) {
				eu = new ELUser();
				eu.setId(rs.getInt(1));
				eu.setFingerInfo(rs.getString(2));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}
	
	
}
