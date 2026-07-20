package com.sopia.assistman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.assistman.SurveyAndPollContants;
import com.sopia.assistman.dao.SurveyAndPollDao;
import com.sopia.assistman.entities.Poll;
import com.sopia.assistman.entities.QstatInfo;
import com.sopia.assistman.entities.Survey;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.Question;
import com.sopia.studyman.entities.MyPollQuesion;
import com.sopia.studyman.entities.MySurvyEP;

public class SurveyAndPollDaoImpl implements SurveyAndPollDao {
	private static final Log logger = LogFactory
			.getLog(SurveyAndPollDaoImpl.class);

	public void addSurvey(Survey survey) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_ADD));
			ps.setString(1, survey.getTitle());
			ps.setString(2, survey.getDescription());
			ps.setInt(3, survey.getCreater().getId());
			ps.setTimestamp(4, survey.getBegintime());
			ps.setTimestamp(5, survey.getEndtime());
			ps.setInt(6, survey.getExamPaper().getId());
			ps.setBoolean(7, survey.getStureadresult());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterSurvey(Survey survey) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_ALTER));
			ps.setString(1, survey.getTitle());
			ps.setString(2, survey.getDescription());
			ps.setTimestamp(3, survey.getBegintime());
			ps.setTimestamp(4, survey.getEndtime());
			ps.setInt(5, survey.getExamPaper().getId());
			ps.setBoolean(6, survey.getStureadresult());
			ps.setInt(7, survey.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Survey> listMySurvey(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Survey> ss = new ArrayList<Survey>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_MYLIST));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Survey s = new Survey(rs.getInt(1), rs.getString(2));
				s.setBegintime(rs.getTimestamp(3));
				s.setEndtime(rs.getTimestamp(4));
				s.setExamPaper(new ExamPaper(rs.getInt(5), rs.getString(6)));
				s.setStureadresult(rs.getBoolean(7));
				ss.add(s);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ss;
	}

	public int listMySurveySize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_MYLIST_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public Survey getSurvey(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Survey s = new Survey();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = new Survey(rs.getInt(1), rs.getString(2));
				s.setBegintime(rs.getTimestamp(3));
				s.setEndtime(rs.getTimestamp(4));
				s.setExamPaper(new ExamPaper(rs.getInt(5), rs.getString(6)));
				s.setStureadresult(rs.getBoolean(7));
				s.setDescription(rs.getString(8));
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public void deleteSurvey(int id) throws ElException {
		// TODO 调查删除
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Survey> listSurveyByDepid(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Survey> ss = new ArrayList<Survey>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;

			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_LIST_BYDEPID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow );
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// es.id,es.title,es.creater,eu.realname,
				// es.begintime,es.endtime,es.epid,ep.title,dep.id,dep.name
				Survey s = new Survey(rs.getInt(1), rs.getString(2));
				s.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				s.setBegintime(rs.getTimestamp(5));
				s.setEndtime(rs.getTimestamp(6));
				s.setExamPaper(new ExamPaper(rs.getInt(7), rs.getString(8)));
				s.getCreater().setDepartment(
						new Department(rs.getInt(9), rs.getString(10)));
				ss.add(s);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ss;
	}

	public int listSurveyByDepidSize(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;

			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_LIST_BYDEPID_SIZE));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void surveyDoSubmit(MySurvyEP mep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_DO_SUBMIT));

			ps.setInt(1, mep.getTester().getId());
			ps.setInt(2, mep.getSurvey().getId());
			ps.setInt(3, mep.getExamPaper().getId());
			ps.setString(4, mep.getMyAnswer());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean surveyDoCheck(MySurvyEP mep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_DO_CHECK));
			ps.setInt(1, mep.getTester().getId());
			ps.setInt(2, mep.getSurvey().getId());
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<QstatInfo> listQstatinfoBySurid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<QstatInfo> qsis = new ArrayList<QstatInfo>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.SURVEY_QUESTION_LIST_BYSID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				int qid = rs.getInt(1);
				String title = rs.getString(2);
				int type = rs.getInt(3);
				String subject = rs.getString(4);

				QstatInfo qsi = new QstatInfo();
				qsi.setId(qid);
				qsi.setTitle(title);
				// 选项
				if (type == 1) {
					String ops[] = { "对", "错" };
					qsi.setOptions(ops);
					int anscount[] = new int[2];
					int totalCount = 0;
					PreparedStatement ps1 = ct
							.prepareStatement(ElQuerySql
									.getSQL(SurveyAndPollContants.SURVEY_QUESTION_ANSWER_COUNT));
					ps1.setInt(1, qid);
					ps1.setString(2, "yes");
					ps1.setInt(3, id);
					ResultSet rs1 = ps1.executeQuery();
					if (rs1.next()) {
						anscount[0] = rs1.getInt(1);
						totalCount += anscount[0];
					}
					rs1.close();
					ps1.close();
					ps1 = ct
							.prepareStatement(ElQuerySql
									.getSQL(SurveyAndPollContants.SURVEY_QUESTION_ANSWER_COUNT));
					ps1.setInt(1, qid);
					ps1.setString(2, "no");
					ps1.setInt(3, id);
					rs1 = ps1.executeQuery();
					if (rs1.next()) {
						anscount[1] = rs1.getInt(1);
						totalCount += anscount[1];
					}
					rs1.close();
					ps1.close();
					// TODO 未回答
					float f[] = new float[ops.length];
					for (int i = 0; i < anscount.length; i++) {
						f[i] = anscount[i] * 100.0f / totalCount;
					}
					qsi.setAnswerPer(f);
					qsi.setAnswerCount(anscount);
					qsi.setTotalCount(totalCount);
				}
				// 客观题
				if (type >= 2 && type <= 4) {
					if (null != subject) {
						qsi.setOptions(subject.split(ElConstants.optSplit));
						int anscount[] = new int[qsi.getOptions().length];
						int totalCount = 0;
						for (int i = 0; i < qsi.getOptions().length; i++) {
							PreparedStatement ps1 = ct
									.prepareStatement(ElQuerySql
											.getSQL(SurveyAndPollContants.SURVEY_QUESTION_ANSWER_COUNT));
							ps1.setInt(1, qid);
							ps1.setString(2, "" + i);
							ps1.setInt(3, id);
							ResultSet rs1 = ps1.executeQuery();
							if (rs1.next()) {
								anscount[i] = rs1.getInt(1);
								totalCount += anscount[i];
							}
							rs1.close();
							ps1.close();
						}
						// TODO 未回答
						float f[] = new float[qsi.getOptions().length];
						for (int i = 0; i < anscount.length; i++) {
							f[i] = anscount[i] * 100.0f / totalCount;
						}
						qsi.setAnswerPer(f);
						qsi.setAnswerCount(anscount);
						qsi.setTotalCount(totalCount);
					}
				}
				// 主观题//TODO 应该是有问题的....
				if (type == 5 || type == 6) {

				}
				qsis.add(qsi);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qsis;
	}
	public QstatInfo getQstatinfoByPollid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		QstatInfo qsi = new QstatInfo();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_QUESTION_BYPID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				int qid = rs.getInt(1);
				String title = rs.getString(2);
				int type = rs.getInt(3);
				String subject = rs.getString(4);

				qsi.setId(qid);
				qsi.setTitle(title);
				// 选项
				if (type == 1) {
					String ops[] = { "对", "错" };
					qsi.setOptions(ops);
					int anscount[] = new int[2];
					int totalCount = 0;
					PreparedStatement ps1 = ct
							.prepareStatement(ElQuerySql
									.getSQL(SurveyAndPollContants.POLL_QUESTION_ANSWER_COUNT));
					ps1.setInt(1, qid);
					ps1.setString(2, "yes");
					ps1.setInt(3, id);
					ResultSet rs1 = ps1.executeQuery();
					if (rs1.next()) {
						anscount[0] = rs1.getInt(1);
						totalCount += anscount[0];
					}
					rs1.close();
					ps1.close();
					ps1 = ct
							.prepareStatement(ElQuerySql
									.getSQL(SurveyAndPollContants.POLL_QUESTION_ANSWER_COUNT));
					ps1.setInt(1, qid);
					ps1.setString(2, "no");
					ps1.setInt(3, id);
					rs1 = ps1.executeQuery();
					if (rs1.next()) {
						anscount[1] = rs1.getInt(1);
						totalCount += anscount[1];
					}
					rs1.close();
					ps1.close();
					// TODO 未回答
					float f[] = new float[ops.length];
					for (int i = 0; i < anscount.length; i++) {
						f[i] = anscount[i] * 100.0f / totalCount;
					}
					qsi.setAnswerPer(f);
					qsi.setAnswerCount(anscount);
					qsi.setTotalCount(totalCount);
				}
				// 客观题
				if (type >= 2 && type <= 4) {
					if (null != subject) {
						qsi.setOptions(subject.split(ElConstants.optSplit));
						int anscount[] = new int[qsi.getOptions().length];
						int totalCount = 0;
						for (int i = 0; i < qsi.getOptions().length; i++) {
							PreparedStatement ps1 = ct
									.prepareStatement(ElQuerySql
											.getSQL(SurveyAndPollContants.POLL_QUESTION_ANSWER_COUNT));
							ps1.setInt(1, qid);
							ps1.setString(2, "" + i);
							ps1.setInt(3, id);
							ResultSet rs1 = ps1.executeQuery();
							if (rs1.next()) {
								anscount[i] = rs1.getInt(1);
								totalCount += anscount[i];
							}
							rs1.close();
							ps1.close();
						}
						// TODO 未回答
						float f[] = new float[qsi.getOptions().length];
						for (int i = 0; i < anscount.length; i++) {
							f[i] = anscount[i] * 100.0f / totalCount;
						}
						qsi.setAnswerPer(f);
						qsi.setAnswerCount(anscount);
						qsi.setTotalCount(totalCount);
					}
				}
				// 主观题//TODO 应该是有问题的....
				if (type == 5 || type == 6) {

				}
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qsi;
	}
	public void addPoll(Poll poll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_ADD));
			ps.setString(1, poll.getTitle());
			ps.setString(2, poll.getDescription());
			ps.setInt(3, poll.getCreater().getId());
			ps.setTimestamp(4, poll.getBegintime());
			ps.setTimestamp(5, poll.getEndtime());
			ps.setInt(6, poll.getQuestion().getId());
			ps.setBoolean(7, poll.getStureadresult());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void alterPoll(Poll poll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_ALTER));
			ps.setString(1, poll.getTitle());
			ps.setString(2, poll.getDescription());
			ps.setTimestamp(3, poll.getBegintime());
			ps.setTimestamp(4, poll.getEndtime());
			ps.setInt(5, poll.getQuestion().getId());
			ps.setBoolean(6,poll.getStureadresult());
			ps.setInt(7, poll.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void deletePoll(int id) throws ElException {
		// TODO 投票删除
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public Poll getPoll(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		 Poll s = new  Poll();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = new  Poll(rs.getInt(1), rs.getString(2));
				s.setBegintime(rs.getTimestamp(3));
				s.setEndtime(rs.getTimestamp(4));
				s.setQuestion(new Question(rs.getInt(5), rs.getString(6)));
				s.setStureadresult(rs.getBoolean(7));
				s.setDescription(rs.getString(8));
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	public List<Poll> listMyPoll(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Poll> ss = new ArrayList<Poll>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_MYLIST));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow );
			ps.setInt(3, pageSize );
			rs = ps.executeQuery();
			while (rs.next()) {
				Poll s = new Poll(rs.getInt(1), rs.getString(2));
				s.setBegintime(rs.getTimestamp(3));
				s.setEndtime(rs.getTimestamp(4));
				s.setQuestion(new Question(rs.getInt(5), rs.getString(6)));
				s.setStureadresult(rs.getBoolean(7));
				ss.add(s);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ss;
	}
	public int listMyPollSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_MYLIST_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public List<Poll> listPollByDepid(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Poll> ss = new ArrayList<Poll>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;

			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_LIST_BYDEPID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow );
			ps.setInt(4, pageSize );
			rs = ps.executeQuery();
			while (rs.next()) {
				// es.id,es.title,es.creater,eu.realname,
				// es.begintime,es.endtime,es.epid,ep.title,dep.id,dep.name
				Poll s = new Poll(rs.getInt(1), rs.getString(2));
				s.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				s.setBegintime(rs.getTimestamp(5));
				s.setEndtime(rs.getTimestamp(6));
				s.setQuestion(new Question(rs.getInt(7), rs.getString(8)));
				s.getCreater().setDepartment(
						new Department(rs.getInt(9), rs.getString(10)));
				ss.add(s);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ss;
	}
	public int listPollByDepidSize(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;

			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_LIST_BYDEPID_SIZE));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public boolean pollDoCheck(MyPollQuesion mpq) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_DO_CHECK));
			ps.setInt(1, mpq.getTester().getId());
			ps.setInt(2, mpq.getPoll().getId());
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	public void pollDoSubmit(MyPollQuesion mpq) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(SurveyAndPollContants.POLL_DO_SUBMIT));

			ps.setInt(1, mpq.getTester().getId());
			ps.setInt(2, mpq.getPoll().getId());
			ps.setInt(3, mpq.getQuestion().getId());
			ps.setString(4, mpq.getMyAnswer());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
}
