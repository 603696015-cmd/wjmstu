package com.sopia.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

/**
 * 学分（点数，计分）管理
 * @author Administrator
 *
 */
public class ScoreOperate {
	private static final Log logger = LogFactory.getLog(ScoreOperate.class);

	public static void setScore(int userid, String operate) throws ElException {
		int type = getType(operate);
		int score = SystemConfOp.getIntValue(operate);
		String description = getDesc(operate, score);
		addLog(userid, type, operate, description, score);
		if (type == 1)
			setUserDot(userid, score);
		else
			setUserScore(userid, score);
	}

	private static void addLog(int userid, int type, String operate,
			String description, int score) throws ElException {
		/*PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into eldotandscore(userid,type,operate,description,score,thedate ) values(?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, type);
			ps.setString(3, operate);
			ps.setString(4, description);
			ps.setInt(5, score);
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("积分点数设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}*/
	}

	private static void setUserDot(int userid, int score) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("update eluser set dot = dot+? where id = ?");
//			ps.setInt(1, score);
//			ps.setInt(2, userid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("积分点数设置失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
	}

	private static void setUserScore(int userid, int score) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("update eluser set score = score+? where id = ?");
//			ps.setInt(1, score);
//			ps.setInt(2, userid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("积分点数设置失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
	}

	private static int getType(String operate) throws ElException {
		if (operate.equals(ElConstants.DIAN_LOGIN_DO)
				|| operate.equals(ElConstants.DIAN_FORUM_DO)
				|| operate.equals(ElConstants.DIAN_TOPIC_DO)
				|| operate.equals(ElConstants.DIAN_STUDY_DO)
				|| operate.equals(ElConstants.DIAN_STUDY_CP_DO)
				|| operate.equals(ElConstants.JIAN_FORUM_DO)
				|| operate.equals(ElConstants.JIAN_KNOWLEDGE_DO)
				|| operate.equals(ElConstants.JIAN_LOGIN_DO)
				|| operate.equals(ElConstants.JIAN_EP_ZHANTING)
				|| operate.equals(ElConstants.JIAN_EP_QIANGZHI))
			return 1;
		return 2;
	}

	private static String getDesc(String operate, int score) throws ElException {
		if (operate.equals(ElConstants.SCORE_FORUM_JH))
			return "一篇帖子被加为精华，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_KNOWLEDGE_TJ))
			return "一篇知识文章被设为推荐，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_COURSE_APPLY))
			return "每申请学习一门课程，奖励（" + score + "分）";
		if (operate.equals(ElConstants.SCORE_PRAC_DO))
			return "每做一次练习，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_SIMP_DO))
			return "每做一次模拟考试，奖励（" + score + "分）";
		if (operate.equals(ElConstants.SCORE_MESS_SEND))
			return "每发一条站内短信，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_SURVEY_DO))
			return "每做一张调查问卷，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_POLL_DO))
			return "每参加一次投票，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_ZTROOM_DO))
			return "每做一张客观测评试卷，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_KTROOM_DO))
			return "每参加一次民主评议，奖励（" + score + "）分";
		if (operate.equals(ElConstants.SCORE_NOTE_DO))
			return "记一次课程小结，奖励（" + score + "）分";
		if (operate.equals(ElConstants.DIAN_LOGIN_DO))
			return "每间隔（60）分钟后，登陆一次将励（" + score + "）点";
		if (operate.equals(ElConstants.DIAN_FORUM_DO))
			return "发帖一篇，将励（" + score + "）点";
		if (operate.equals(ElConstants.DIAN_TOPIC_DO))
			return "回帖一篇，奖励（" + score + "）点";
		if (operate.equals(ElConstants.DIAN_STUDY_DO))
			return "学习次数每增加一次，奖励（" + score + "）点";
		if (operate.equals(ElConstants.DIAN_STUDY_CP_DO))
			return "学习时长每增加一小时，奖励（" + score + "）点";
		if (operate.equals(ElConstants.JIAN_FORUM_DO))
			return "一篇帖子被删除，扣（" + score + "）点";
		if (operate.equals(ElConstants.JIAN_KNOWLEDGE_DO))
			return "一篇知识文章被删除，扣（" + score + "）点";
		if (operate.equals(ElConstants.JIAN_LOGIN_DO))
			return "长时间不登陆：每隔（48）小时不登陆，扣（" + score + "）点";
		if (operate.equals(ElConstants.JIAN_EP_ZHANTING))
			return "每被暂停一次考试，扣（" + score + "）点";
		if (operate.equals(ElConstants.JIAN_EP_QIANGZHI))
			return "每被强制交卷一次，扣（" + score + "）点";
		return "无说明";
	}

	public static int getScoreByOp(int userid, String operate)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sum(score) from eldotandscore where userid=? and operate = ?");
			ps.setInt(1, userid);
			ps.setString(2, operate);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("积分点数设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public static List<ELUser> getUserByDepId(int depid, int subdep,
			String order, int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
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
						.prepareStatement("select * from (select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.dot,eu.score,eu.xfscore,row_number() over(order by "
								+ order + " desc ) rownum  from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id "
								+ "where  dep.lid >=? and dep.rid<=?  ) t where t.rownum > ? and t.rownum <= ?");
				ps.setInt(1, dep.getLid());
				ps.setInt(2, dep.getRid());
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			} else {
				ps = ct
						.prepareStatement("select * from (select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.dot,eu.score,eu.xfscore,row_number() over(order by "
								+ order
								+ " desc) rownum from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
								+ "where  dep.id=?  ) t where t.rownum between ? and ?");
				ps.setInt(1, depid);
				ps.setInt(2, pageNow );
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
//				elUser.setUserno(rs.getString(4));
//				elUser.setEmail(rs.getString(5));
//				elUser.setRole(new ElRole(rs.getInt(6), rs.getString(10)));
//				elUser.setDepartment(new Department(rs.getInt(7), rs
//						.getString(8)));
//				elUser.setValid(rs.getBoolean(9));
//				elUser.setDot(rs.getInt(11));
//				elUser.setScore(rs.getInt(12));
//				elUser.setXfscore(rs.getInt(13));
//				float dot = elUser.getDot();
//				float score = elUser.getScore();
//				score = score +(SystemConfOp.getIntValue(ElConstants.SCORE_2_DIAN)==0?0:dot/SystemConfOp.getIntValue(ElConstants.SCORE_2_DIAN));
//				float xuefen = elUser.getXfscore();
//				xuefen = xuefen +(SystemConfOp.getIntValue(ElConstants.XFSCORE_2_SCORE)==0?0:score/SystemConfOp.getIntValue(ElConstants.XFSCORE_2_SCORE));
//				elUser.setXfph((int)xuefen);
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

	public static int getUserByDepIdSize(int depid, int subdep)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
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
						.prepareStatement("select count(*) from ELUSER eu left join  elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id "
								+ "where  dep.lid >=? and dep.rid<=?  ");
				ps.setInt(1, dep.getLid());
				ps.setInt(2, dep.getRid());

			} else {
				ps = ct
						.prepareStatement("select count(*)  from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
								+ "where  dep.id=?");
				ps.setInt(1, depid);

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

	public static void setXfScore(int userid, String operate, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select name,credit from course where id = ?");
			ps.setInt(1, courseid);
			String name = "";
			int score = 0;
			rs= ps.executeQuery();
			if (rs.next()) {
				name = rs.getString(1);
				score = rs.getInt(2);
			}
			rs.close();
			ps = ct.prepareStatement("select * from elxfscore where userid =? and courseid = ? and operate = ? ");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setString (3, operate);
			rs = ps.executeQuery();
			boolean b  = false;
			if(rs.next()) b =true;
			if (!b&&score != 0 && !"".equals(name)) {
				String description = operate
						.equals(ElConstants.XFCOURSE_QUIZPASSED) ? name
						+ "完成课时得" + score : name + "通过考试" + score;
				ps = ct
						.prepareStatement("insert into elxfscore(userid,courseid,operate,description,score,thedate ) values(?,?,?,?,?,?)");
				ps.setInt(1, userid);
				ps.setInt(2, courseid);
				ps.setString(3, operate);
				ps.setString(4, description);
				ps.setInt(5, score);
				ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				ps.executeUpdate();
				ps = ct
						.prepareStatement("update eluser set xfscore=xfscore+ ? where id =?");
				ps.setInt(1, score);
				ps.setInt(2, userid);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("积分点数设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

}
