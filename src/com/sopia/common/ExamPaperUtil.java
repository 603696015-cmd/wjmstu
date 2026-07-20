package com.sopia.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.sopia.ElConstants;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import common.Logger;


/**
 * 试卷处理--答案组装，答案解析。
 * @author Administrator
 *
 */
public class ExamPaperUtil {
	private static Logger logger = Logger.getLogger(ExamPaperUtil.class);

	@SuppressWarnings("unchecked")
	public static String getParamCombString(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			sb.append(paramName + ElConstants.valSplit);
			String values[] = request.getParameterValues(paramName);
			for (int i = 0; i < values.length; i++) {
				String vsi = values[i];
				if (vsi.equals(""))
					vsi = " ";
				sb.append(vsi + ElConstants.valSplit);
			}
			sb.append(ElConstants.resSplit);
		}
		return sb.toString();
	}

	public static void getAnswerExampaper(String s, ExamPaper ep,
			ExamPaperDao eps, QuestionDao qs, Date shengri) throws ElException {
		// 分割没道题
		String[] qa = s.split(ElConstants.resSplit);
		List<ExamPaperBlock> ebs = ep.getEpBlocks();
		int ep_zscore = 0, ep_kscore = 0, tscore = 0, zscore = 0;
		if (null != qa)
			for (int i = 0; i < qa.length; i++) {// 每个条问题答案
				String qai = qa[i];
				String qidAndAnswer[] = qai.split(ElConstants.valSplit, 2);
				if (null != qai.substring(0, 1)
						&& qai.substring(0, 1).matches("\\d")) {
					if (null != qidAndAnswer) {
						if (null != qidAndAnswer[0].split("_")) {
							String qinfo[] = qidAndAnswer[0].split("_");
							int blockid = getInt(qinfo[0]), qid = getInt(qinfo[1]), qsortid = 0;
							if (qinfo.length > 2)
								qsortid = getInt(qinfo[2]);
							ExamPaperBlock eb = getBlockById(blockid, ebs, eps);
							Question q = qs.getQuestionByid(qid, blockid, eb
									.getRandom());
							q.setSortid(qsortid);
							if (q.getParent().getId() != 0) {
								getCombQ(q.getParent().getId(), eb, qs)
										.getChilds().add(q);
								q
										.setScore((eb.getEachscore() * q
												.getScoreper()) / 100);
							} else {
								eb.getQuestions().add(q);
								q.setScore(eb.getEachscore());
							}
							q.setStuAnswer(qidAndAnswer[1]);
							float epb_score = eb.getMyscore();
							float q_score = getScore(q, getAge(shengri));
							epb_score += q_score;
							if (eb.getType() == 5 || eb.getType() == 6
									|| eb.getType() == 11) {
								ep_zscore += q.getScore();
								zscore += q_score;
							} else if (eb.getType() == 12) {
								String rules[] = eb.getRules();
								int t_q = getInt(rules[0]), e_score = getInt(rules[2]), todo_ = getInt(rules[1]);
								
							} else {
								ep_kscore += q.getScore();

							}
							tscore += q_score;
							eb.setMyscore(epb_score);
						}
					}
				}
			}
		// if (null != ebs)
		// for (int i = 0; i < ebs.size(); i++) {
		// ExamPaperBlock epbi = ebs.get(i);
		// String rules[] = epbi.getRules();
		// int t_q = getInt(rules[0]),e_score= getInt(rules[2]),todo_ =
		// getInt(rules[1]);
		// if(epbi.getRandom()==1){
		//					
		// }
		// }
		ep.setEpBlocks(ebs);
		ep.sortBlocks();
		ep.setEp_zscore(ep_zscore);
		ep.setEp_kscore(ep_kscore);
		ep.setEp_tscore(ep_kscore + ep_zscore);
		ep.setMep_tscore(tscore);
		ep.setMepZscore(zscore);
		ep.setMepKscore(tscore - zscore);
		// ep.setEp_tscore(ep_zscore + ep_kscore);
		// setScore(ep);
	}

	private static int getAge(Date date) {
		if (date == null)
			return 30;
		long day = (new Date().getTime() - date.getTime())
				/ (24 * 60 * 60 * 1000) + 1;
		return (int) (day / 365);
	}

	// public static void main(String[] args) {
	// try {
	// SimpleDateFormat("yyyy-MM-dd").parse("1962-05-09")));
	//
	// } catch (Exception e) {
	// }
	// }

	// private static void setScore(ExamPaper ep) {
	// int tscore = 0;
	// int zscore = 0;
	// int ep_kscore = 0;
	//
	// List<ExamPaperBlock> ebps = ep.getEpBlocks();
	// if (null != ebps) {
	// for (int i = 0; i < ebps.size(); i++) {
	// ExamPaperBlock epb = ebps.get(i);
	// List<Question> qs = epb.getQuestions();
	// if (epb.getType() < 5) {
	// ep_kscore += epb.getEachscore() * epb.getQuestionamount();
	// }
	// if (null != qs)
	// for (int j = 0; j < qs.size(); j++) {
	// Question qsj = qs.get(j);
	// if (qsj.getQtype() != 7) {
	// int score = getScore(qsj);
	// tscore += score;
	// if (qsj.getQtype() == 6)
	// zscore += score;
	// }
	// if (qsj.getQtype() == 7) {
	// List<Question> qsc = qsj.getChilds();
	// if (null != qsc) {
	// for (int k = 0; k < qsc.size(); k++) {
	// int score1 = getScore(qsc.get(k));
	// tscore += score1;
	// if (qsc.get(k).getQtype() == 6) {
	// zscore += score1;
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	// ep.setMep_tscore(tscore);
	// ep.setMepZscore(zscore);
	// ep.setMepKscore(tscore - zscore);
	// ep.setEp_kscore(ep_kscore);
	// }

	private static float getScore(Question q, int age) {
		float score = 0;
		if (q.getQtype() == 1 || q.getQtype() == 2) {
//			if (q.getAnswers().length > 0 && q.getStuAnswers().length > 0)
//				if (q.getStuAnswers()[0].trim().equals(q.getAnswers()[0]))
//					score = q.getScore();
			if (q.getAnswers().length > 0 && q.getStuAnswers().length > 0)
				if (q.getStuAnswers()[0].trim().equals(q.getAnswers()[0]))
					score = q.getScore();
		}
		if (q.getQtype() == 3 || q.getQtype() == 4) {
			String answer = q.getAnswer() == null ? "" : q.getAnswer().replace(
					ElConstants.optSplit, ElConstants.valSplit);
			if ((answer).equals(q.getStuAnswer()))
				score = q.getScore();
		}
		if (q.getQtype() == 8) {
			score = getScoreDazi(q, age);
		}
		if (q.getQtype() == 9 || q.getQtype() == 10) {

			score = getScoreMailOrSearch(q);
		}
		if (q.getQtype() == 5) {
//			for (int i = 0; i < q.getStuAnswers().length; i++) {
//				if (q.getStuAnswers()[i].trim()
//						.equals(q.getAnswers()[i].trim()))
//					score += q.getScore() / q.getAnswers().length;
//			}
			for (int i = 0; i < q.getStuAnswers().length; i++) {
				if (q.getStuAnswers()[i].trim()
						.equals(q.getAnswers()[i].trim()))
					score += q.getScore() / q.getAnswers().length;
			}
		}
		if (q.getQtype() == 6) {
		}
		q.setMyScore(score);
		return score;
	}

	private static float getScoreDazi(Question qj, int age) {
		float score = 0;
		if(qj.getStuAnswers().length==1){
			return score;
		}
		float v = getFloat(qj.getStuAnswers()[0]);
		int r = getInt(qj.getStuAnswers()[1]);
		float vimin = 0f, vimax = 0f;
		if (qj.getRules() == null)
			return 0;
		float a = getFloat(qj.getRules()[0]),  b = getFloat(qj.getRules()[1]);
		int t = getInt(qj.getRules()[2]);
		// int passtime = getInt(qj.getStuAnswers()[2]);
		String rules[][] = qj.getDazirule();
		for (int i = 0; i < rules.length; i++) {
			int age_b = getInt(rules[i][0]);
			int age_e = getInt(rules[i][1]);
			if (age_b < age && age <= age_e) {
				vimin = getFloat(rules[i][2]) / 60;
				vimax = getFloat(rules[i][3]) / 60;
				break;
			}
		}
		int N = (int) (t * 60 * vimax);
		if (v < vimin) {
			score = (int) ((0.6 * a * v) / vimin + b * r / N);
		} else if (v >= vimin && v < vimax) {
			score = (int) (0.6 * a + 0.4 * a * (v - vimin) / (vimax - vimin) + b
					* r / N);
		} else {
			score = (int) (a + b * r / N);
		}
		if (score >= qj.getScore())
			score = qj.getScore();
		return score;
	}

	private static float getScoreMailOrSearch(Question qj) {
		String rules[] = qj.getRules();
		float score = 0;
		if(qj.getStuAnswers().length==1){
			return score;
		}
		if (null != rules)
			for (int i = 0; i < rules.length; i++) {
				if (qj.getAnswers()[i].trim().equals(
						qj.getStuAnswers()[i].trim())) {
					score += getFloat(rules[i]);
				}
			}
		if (score >= qj.getScore())
			score = qj.getScore();
		return score;
	}

	private static Question getCombQ(int id, ExamPaperBlock ebs, QuestionDao qs)
			throws ElException {
		List<Question> qus = ebs.getQuestions();
		Question que = null;
		for (int i = 0; i < qus.size(); i++) {
			if (id == qus.get(i).getId()) {
				que = qus.get(i);
				if (null == que.getChilds())
					que.setChilds(new ArrayList<Question>());
				return que;
			}
		}
		que = qs.getQuestionByid(id);
		if (null == que.getChilds())
			que.setChilds(new ArrayList<Question>());
		ebs.getQuestions().add(que);
		return que;
	}

	private static ExamPaperBlock getBlockById(int id,
			List<ExamPaperBlock> ebs, ExamPaperDao eps) throws ElException {
		ExamPaperBlock epb = null;
		for (int i = 0; i < ebs.size(); i++) {
			if (id == ebs.get(i).getId()) {
				epb = ebs.get(i);
				if (null == epb.getQuestions())
					epb.setQuestions(new ArrayList<Question>());
				return epb;
			}
		}
		epb = eps.getEpbById(id);
		if (null == epb.getQuestions())
			epb.setQuestions(new ArrayList<Question>());
		ebs.add(epb);
		return epb;
	}

	public static int getInt(String value) {
		if (value == null)
			return 0;
		if (("").equals(value.trim()))
			return 0;
		int valuei = 0;
		try {
			valuei = new Integer(value).intValue();
		} catch (Exception e) {
			logger.error("数字转化出差!");
		}
		return valuei;

	}

	public static float getFloat(String value) {
		if (value == null)
			return 0f;
		if (("").equals(value.trim()))
			return 0f;
		float valuei = 0f;
		try {
			valuei = new Float(value).floatValue();
		} catch (Exception e) {
			logger.error("数字转化出差!");
			;
		}
		return valuei;

	}

	public static String getABC(int i) throws ElException {
		return ((char) (i + 65) + "").toUpperCase();
	}

	public static String getABC(String i) throws ElException {
		int ij = 0;
		try {
			ij = new Integer(i);
		} catch (Exception e) {
			ij = 0;
		}

		return getABC(ij);
	}
	
	/**
	 * 正则表达式判读
	 * str 字符串 reg设置的规则
	 * @param args
	 */
	public static boolean checkDazi(String str ) {
		Pattern p = Pattern.compile("(\\d.*)(\\d*)-=SpRule-(\\d.*)(\\d*)-=SpRule-(\\d*)-=SpRule-(((\\d*):(\\d*):(\\d*):(\\d*):(\\d*):)+)");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	public static boolean checkEmail(String str) {
		Pattern p = Pattern.compile("(\\d.*)(\\d*)-=SpRule-(\\d.*)(\\d*)-=SpRule-(\\d.*)(\\d*)-=SpRule-(\\d.*)(\\d*)-=SpRule-(\\d.*)(\\d*)-=SpRule-(\\d.*)(\\d*)");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	/**选做题的评分规则检验
	 * Description: 
	* @Version1.0 2012-7-3 上午09:56:03 by 闻益舜（wenyishun110@163.com）创建
	 * @param str
	 * @return
	 */
	public static boolean checkXuanzuo(String str) {
		//3-=SpRule-1-=SpRule-5
		Pattern p = Pattern.compile("(\\d*)-=SpRule-(\\d*)-=SpRule-(\\d.*)(\\d*)");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	public static String getBaseNum2Chinese(int i){
			switch (i) {
			case 0:
				return "零";
			case 1:
				return "一";
			case 2:
				return "二";
			case 3:
				return "三";
			case 4:
				return "四";
			case 5:
				return "五";
			case 6:
				return "六";
			case 7:
				return "七";
			case 8:
				return "八";
			case 9:
				return "九";
			case 10:
				return "十";
			default:
				break;
			}
		return "";
	}
	public static String getNum2Ch (int i){
		
		return "零";
	}
	
//	public static void main(String[] args) {
////		System.out.println(basic(50.3f,61.8f));
//		//int x = 20;
//		int i =0 ;
//		while(i<120){
//			int [] xxx= new int[20];
//			//long l = System.currentTimeMillis();
//			randomSort(1,20,xxx,0);
//			for (int j = 0; j < xxx.length; j++) {
//				System.out.print(xxx[j]+",");
//			}
//			System.out.println();
//			i++;
//		}
//	}
	/**
	 * 获取一个随机数组
	 * @param max 从1开始到max的随机数
	 * @return
	 */
	public static int[] getRandomArray(int max){
		int [] array=new int[max];
		randomSort(1,max,array,0);
//		for (int j = 0; j < array.length; j++) {
//			System.out.print(array[j]+",");
//		}
		return array;
	}
	public static String[] sortStrRandom(String[]  abc){
		if(abc!=null){
			int l = abc.length;
			int[] ra = getRandomArray(l);
			String[] abc1=new String[l];
			for (int i = 0; i < ra.length; i++) {
				abc1[i]=abc[ra[i]-1];
			}
			return abc1;
		}
		return null;
	}
	/**
	 * 给数组随机排序
	 * @param min
	 * @param max
	 * @param xxx
	 * @param sort
	 * @return
	 */
	public static int randomSort(int min,int max,int [] arr,int sort){
		int mid = (int)(Math.random()*(max-min))+min;
		long a =  Math.round(Math.random());
		if(a ==1){
			if(mid<max)
				sort= randomSort(mid+1, max, arr,sort );
			if(mid>min)
				sort= randomSort(min, mid-1, arr,sort );
		}else{
			if(mid>min)
				sort= randomSort(min, mid-1, arr,sort );
			if(mid<max)
				sort= randomSort(mid+1, max, arr,sort );
		}
		arr[sort]=mid;
		return sort+1;
	}
	/**
	 * 看图选择、看动画选择、听音选图默认选择设置
	 * @param options
	 * @return
	 */
	public static int randomSelect(String[] options){
		int returnI = new Random().nextInt(options.length);
		return returnI;
	}
}
