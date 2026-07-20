package com.sopia.common.quiz;

import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;

/**
 * @author wys 试题试卷被使用后将其状态设置为已经创建的工作
 */
public class EpQStatus implements Runnable {
	private static final Log logger = LogFactory.getLog(EpQStatus.class);
	// private static Vector<Integer> exampapers = new Vector<Integer>();
	// private static Vector<Integer> questions = new Vector<Integer>();
	private static HashSet<Integer> examPapers = new HashSet<Integer>();
	private static HashSet<Integer> quesTions = new HashSet<Integer>();
	private static QuestionDao qd = (QuestionDao) SpringContextUtil
			.getBean("questionDao");
	private static ExamPaperDao epd = (ExamPaperDao) SpringContextUtil
			.getBean("examPaperDao");

	/**
	 * 试卷试题状态设置 线程。
	 */
	public static void init() {
		new Thread(new EpQStatus()).start();
	}

	/*
	 * public void run1() { while (true) {
	 * logger.info(this.hashCode()+",QUESTIONS-SIZE:" + questions.size() +
	 * ";EXAMPAPER-SIZE:" + exampapers.size()); try { for (int i = 0; i <
	 * questions.size(); i++) { Integer id = questions.get(i);
	 * qd.setQuestionStatus(id, 0); questions.remove(id); } for (int i = 0; i <
	 * exampapers.size(); i++) { Integer id = exampapers.get(i);
	 * epd.updateExampaperIseditor(id); exampapers.remove(id); }
	 * Thread.sleep(60*1000); } catch (Exception e) {
	 * logger.error("设置试题，试卷被使用状态出错！",e); }finally{ try {
	 * DBConnection.getConnection().close(); } catch (Exception e) { // TODO:
	 * handle exception }finally{ DBConnection.setNull(); }
	 *  } } }
	 */
	public void run() {
		while (true) {
//			int size1 =quesTions.size();
//			int  size2=examPapers.size();
//			if(size1>0||size2>0)
//			logger.info(this.hashCode() + ",QUESTIONS-SIZE:" + size1
//					+ ";EXAMPAPER-SIZE:" +size2);
			try {
				if (qd == null)
					qd = (QuestionDao) SpringContextUtil.getBean("questionDao");
				if (epd == null)
					epd = (ExamPaperDao) SpringContextUtil
							.getBean("examPaperDao");
				if (quesTions == null)
					quesTions = new HashSet<Integer>();
				if (examPapers == null)
					examPapers = new HashSet<Integer>();
				while (!quesTions.isEmpty()) {
					Integer id = (Integer) quesTions.toArray()[0];
					qd.setQuestionStatus(id, 0);
					quesTions.remove(id);
				}
				while (!examPapers.isEmpty()) {
					Integer id = (Integer) examPapers.toArray()[0];
					epd.updateExampaperIseditor(id);
					examPapers.remove(id);
				}
				Thread.sleep(100 * 1000);
			} catch (Exception e) {
				logger.error("设置试题，试卷被使用状态出错！", e);
			} finally {
				try {
					DBConnection.getConnection().close();
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					DBConnection.setNull();
				}

			}
		}
	}

	/**
	 * 添加试题id
	 * 
	 * @param id
	 */
	public static void addQuestion(int id) {
		quesTions.add(id);
	}

	/**
	 * 添加试卷id
	 * 
	 * @param id
	 */
	public static void addExampaper(int id) {
		examPapers.add(id);
	}
	public static int questionsize(){
		return quesTions==null?0:quesTions.size();
	}
	public static int exampapersize(){
		return examPapers==null?0:examPapers.size();
	}
}
