package com.sopia.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.sopia.ElConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.dao.impl.QuestionDaoImpl;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.wordman.dao.WordDao;
import com.sopia.wordman.entities.Vocabulary;

/**
 * 试题导入类
 * 
 * @author Administrator
 * 
 */
public class ExcelUtil {
	private static StringBuffer impTip;// 导入提示
	private static final Log logger = LogFactory.getLog(ExcelUtil.class);

	public static void writeQlib(File source, int userid) throws ElException,
			Exception {
		InputStream is = new FileInputStream(source);
		jxl.Workbook rwb = Workbook.getWorkbook(is);
		Sheet ss[] = rwb.getSheets();
		// QuestionDao qd = new QuestionDaoImpl();
		QuestionDao qed = new QuestionDaoImpl();
		if (null != ss && ss.length > 0) {
			Sheet ss1 = ss[0];
			for (int i = 0; i < ss1.getRows(); i++) {
				QuestionLib preQlib = qed.getQLbRoot();
				for (int j = 0; j < ss1.getColumns(); j++) {
					String cellContent = ss1.getCell(j, i).getContents();
					if (null != cellContent && !"".equals(cellContent)) {
						QuestionLib nowQlib = new QuestionLib();
						nowQlib.setName(cellContent.trim());
						nowQlib.setParent(preQlib);
						int id = qed.getQlibId(nowQlib.getName(), userid);
						if (id == -1) {
							// id = qed.addQLib(nowQlib);
							qed.addQuestionLib(nowQlib);
							id = nowQlib.getId();
						}
						nowQlib.setId(id);
						preQlib = nowQlib;
					}
				}
			}
		}
	}

	// public static void writeQuestion(File source, int type, int userid)
	// throws ElException, Exception {
	// InputStream is = new FileInputStream(source);
	// jxl.Workbook rwb = Workbook.getWorkbook(is);
	// Sheet ss[] = rwb.getSheets();
	//
	// if (null != ss && ss.length > 0) {
	// Sheet ss1 = ss[0];
	// if (type == 1) {
	// writeYesOrno(ss1, type, userid);
	// } else if (type == 2)
	// writeSelect(ss1, userid);
	// else if (type == 8)
	// writeDazi(ss1, userid, 8);
	// else if (type == 9)
	// writeEMAIL(ss1, userid, 9);
	// else if (type == 10)
	// writeSearch(ss1, userid, 10);
	// else if (type == 11)
	// writeOffice(ss1, userid, 11);
	// else {
	// writeBlankAndEasy(ss1, type, userid);
	// }
	// }
	// }

//	public static void writeQuestion(File source, int userid)
//			throws ElException, Exception {
//		InputStream is = new FileInputStream(source);
//		jxl.Workbook rwb = Workbook.getWorkbook(is);
//		Sheet ss[] = rwb.getSheets();
//
//		if (null != ss && ss.length > 0) {
//			Sheet ss1 = ss[0];
//			batchimport(ss1, userid);
//		}
//	}

	public static String writeQuestion2(File source, int userid)
			throws ElException, Exception {
		try {
			InputStream is = new FileInputStream(source);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();

			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				batchimport(ss1, userid);
			}
		} catch (Exception e) {
			logger.error("试题导入出错", e);
			return "false";
		}
		if (impTip.substring(impTip.length() - 4, impTip.length()).equals(
				"导入成功")) {
			return "true";
		} else {
			String string = new String(impTip);
			impTip.setLength(0);
			return string;
		}
	}

	// 批量导入
	public static void batchimport(Sheet sheet, int userid) throws ElException {
		int qtype = 0;
		String answer = "";
		String[] ch = null;
		impTip = new StringBuffer();
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {
			Question question = new Question();
			if (sheet.getCell(0, i).getContents().trim().equals("")) {
				impTip.append("第" + i + "行，无题型");
				break;
			} else {
				if (sheet.getCell(0, i).getContents().trim().equals("判断题")) {
					checkImport(sheet, i);
					qtype = 1;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"单选题")) {
					qtype = 2;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"不定项选择题")) {
					qtype = 3;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"多选题")) {
					qtype = 4;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"填空题")) {
					qtype = 5;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"问答题")) {
					qtype = 6;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"材料题")) {
					qtype = 7;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"打字题")) {
					qtype = 8;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"邮件题")) {
					qtype = 9;
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"搜索题")) {
					qtype = 10;
					if (!checkImport(sheet, i)) {
						break;
					}
				} else if (sheet.getCell(0, i).getContents().trim().equals(
						"office题")) {
					qtype = 11;
				} else {
					impTip.append("第" + i + "行，这种 <"
							+ sheet.getCell(0, i).getContents().trim()
							+ ">  题型不存在");
				}
				question.setQtype(qtype);
				question
						.setTitle(sheet.getCell(1, i).getContents() == null ? ""
								: sheet.getCell(1, i).getContents().trim()
										.contains(ElConstants.optSplit) ? sheet
										.getCell(1, i).getContents().trim()
										.replaceAll("==", ElConstants.optSplit)
										: sheet.getCell(1, i).getContents()
												.trim());
				question.setContent(sheet.getCell(2, i).getContents().trim());
				question
						.setSubject(sheet.getCell(3, i).getContents() == null ? ""
								: sheet.getCell(3, i).getContents().trim()
										.contains("==") ? sheet.getCell(3, i)
										.getContents().trim().replaceAll("==",
												ElConstants.optSplit)
										+ ElConstants.optSplit : sheet.getCell(
										3, i).getContents().trim());
				// if(qtype!=9){
				// if(sheet.getCell(4, i).getContents().trim().equals("正确"))
				// answer+="yes"; else if(sheet.getCell(4,
				// i).getContents().trim().equals("错误")) answer+="no";
				// else{
				// ch=sheet.getCell(4, i).getContents().trim().split("");
				// if(ch!=null)
				// // for(int j=0;j<ch.length;j++){
				// // /*if(ch[j].equals("A")) answer+=0+ElConstants.optSplit;
				// else
				// // if(ch[j].equals("B")) answer+=1+ElConstants.optSplit; else
				// // if(ch[j].equals("C")) answer+=2+ElConstants.optSplit; else
				// // if(ch[j].equals("D")) answer+=3+ElConstants.optSplit; else
				// // answer+=ch[j]; } } question.setAnswer(answer);*/

				// if (qtype != 9) {
				// if (sheet.getCell(4, i).getContents().trim().equals("正确"))
				// answer += "yes";
				// else if (sheet.getCell(4, i).getContents().trim().equals(
				// "错误"))
				// answer += "no";
				// else {
				// ch = sheet.getCell(4, i).getContents().trim().split("");
				// if (ch != null)
				// for (int j = 0; j < ch.length; j++) {
				// if (ch[j].equals("A"))
				// answer += 0 + ElConstants.optSplit;
				// else if (ch[j].equals("B"))
				// answer += 1 + ElConstants.optSplit;
				// else if (ch[j].equals("C"))
				// answer += 2 + ElConstants.optSplit;
				// else if (ch[j].equals("D"))
				// answer += 3 + ElConstants.optSplit;
				// else
				// answer += ch[j];
				// }
				// }
				// question.setAnswer(answer);
				// }

				// }else {
				// question.setAnswers(sheet.getCell(4, i).getContents()
				// .trim().split("=="));
				// }
				if (qtype != 9) {
					if (qtype == 5) {
						if (sheet.getCell(4, i).getContents().trim().contains(
								"=="))
							answer = sheet.getCell(4, i).getContents().trim()
									.replaceAll("==", ElConstants.optSplit)
									+ ElConstants.optSplit;
						else
							answer = sheet.getCell(4, i).getContents().trim();
					} else if (sheet.getCell(4, i).getContents().trim().equals(
							"正确"))
						answer += "yes" + ElConstants.optSplit;
					else if (sheet.getCell(4, i).getContents().trim().equals(
							"错误"))
						answer += "no" + ElConstants.optSplit;
					else {
						ch = sheet.getCell(4, i).getContents().trim().split("");
						if (qtype == 10) {
							answer = sheet.getCell(4, i).getContents().trim();
						} else {
							if (ch != null)
								for (int j = 0; j < ch.length; j++) {
									// if(ch[j].equals("A"))
									// answer+=0+ElConstants.optSplit;
									// else if(ch[j].equals("B"))
									// answer+=1+ElConstants.optSplit;
									// else if(ch[j].equals("C"))
									// answer+=2+ElConstants.optSplit;
									// else if(ch[j].equals("D"))
									// answer+=3+ElConstants.optSplit;
									// else
									// answer+=ch[j];
									if (!ch[j].trim().equals("")) {
										answer += getIntValue(ch[j].charAt(0))
												+ ElConstants.optSplit;
									}
								}
						}
					}
					question.setAnswer(answer);
				} else {
					question.setAnswers(sheet.getCell(4, i).getContents()
							.trim().split("=="));
				}
				/*
				 * question.setAnswer(getAnswer(qtype, sheet.getCell(4,
				 * i).getContents() .trim()));
				 */
				question.setQexplain(sheet.getCell(5, i).getContents().trim());
				question.setQlevel(sheet.getCell(6, i).getContents().trim()
						.equals("") ? 0 : Integer.parseInt(sheet.getCell(6, i)
						.getContents().trim()));
				/*
				 * question.setOldscore(sheet.getCell(7,
				 * i).getContents().trim().equals("")?0:Float.parseFloat(sheet.getCell(7,
				 * i).getContents().trim()));
				 */
				if (qtype != 9) {
					question.setOldscore(sheet.getCell(7, i).getContents()
							.trim().equals("") ? 0 : Float.parseFloat(sheet
							.getCell(7, i).getContents().trim()));
				} else {
					String rules[] = sheet.getCell(7, i).getContents().trim()
							.split("==");
					question.setOldrules(rules);
					float score = 0;
					if (null != rules) {
						for (int j = 0; j < rules.length; j++) {
							score += ExamPaperUtil.getFloat(rules[j]);
						}
					}
					question.setOldscore(score);
				}

				int qlibid = qd.getQlibId(sheet.getCell(8, i).getContents()
						.trim(), userid);
				/*
				 * if (qlibid == -1) // qlibid = 0;
				 */
				ELUser e = new ELUser();
				e.setId(userid);
				question.setEluser(e);
				int newqlid = qd.getQlibId(sheet.getCell(8, i).getContents()
						.trim(), userid);
				if (newqlid == -1) {
					impTip
							.append("第" + i + "行，未找到<"
									+ sheet.getCell(8, i).getContents().trim()
									+ ">此题库");
					break;
				}
				QuestionLib qlib = new QuestionLib(newqlid);
				question.setQlib(qlib);
				question.setParent(new Question(0));
				qd.addQuestion(question);
				answer = "";
				if (sheet.getRows() - 1 == i) {
					impTip.setLength(0);
					impTip.append("共" + i + "题, 导入成功");
				}
			}
		}
	}

	// public static String getAnswer(int qtype, String answer) {
	// String ch[] = null;
	// String answer1 = "";
	// if (qtype == 1) {
	// if (answer.equals("正确"))
	// answer1 += "yes" + ElConstants.optSplit;
	// else if (answer.equals("错误"))
	// answer1 += "no" + ElConstants.optSplit;
	// return answer1;
	// }
	// if (qtype == 2 || qtype == 3 || qtype == 4) {
	// ch = answer.split("");
	// if (ch != null)
	// for (int j = 0; j < ch.length; j++) {
	// if (!ch[j].trim().equals("")) {
	// answer1 += getIntValue(ch[j].charAt(0))
	// + ElConstants.optSplit;
	// }
	// }
	// return answer1;
	// }
	// if (qtype == 5 || qtype == 6) {
	// answer.replaceAll("==", ElConstants.optSplit);
	// }
	// if (qtype == 8) {
	// return "";
	// }
	// if (qtype == 9) {
	// answer.replaceAll("==", ElConstants.optSplit);
	// }
	// if (qtype == 10) {
	// return answer + ElConstants.optSplit;
	// }
	// if (qtype == 11) {
	// return "";
	// }
	// // return "";
	// return answer;
	// }

//	public static void writeQues(File source, int userid) throws ElException,
//			Exception {
//		InputStream is = new FileInputStream(source);
//		jxl.Workbook rwb = Workbook.getWorkbook(is);
//		Sheet ss[] = rwb.getSheets();
//		if (null != ss && ss.length > 0) {
//			Sheet ss1 = ss[0];
//			// writeQuestion(ss1, userid, qlibid);
//			batchimportQlbid(ss1, userid);
//		}
//	}

	// public static void writeQues(File source, int qlibid, int userid)
	// throws ElException, Exception {
	// InputStream is = new FileInputStream(source);
	// jxl.Workbook rwb = Workbook.getWorkbook(is);
	// Sheet ss[] = rwb.getSheets();
	// if (null != ss && ss.length > 0) {
	// Sheet ss1 = ss[0];
	// // writeQuestion(ss1, userid, qlibid);
	// batchimportQlbid(ss1, userid, qlibid);
	// }
	// }

	/**
	 * Description:指定试题目录的试题导入操作
	 * 
	 * @Version1.0 2012-7-15 上午11:42:56 by 闻益舜（wenyishun110@163.com）创建
	 * @param source
	 * @param qlibid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public static String writeQues2(File source, int qlibid, int userid,boolean import_)
			throws ElException {
		InputStream is = null;
		String msg="";
		try {
			is = new FileInputStream(source);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
//			if (null != ss && ss.length > 0) {
//				Sheet ss1 = ss[0];
//				// writeQuestion(ss1, userid, qlibid);
//				msg=batchimportQlbid(ss1, userid, qlibid);
//			}
			if(null != ss){
				int i=0;
				for(i=0;i<ss.length ;i++) {
					Sheet ss1 = ss[i];
					if(ss1.getRows()>0){
						msg=batchimportQlbid(ss1, userid, qlibid,import_);
						break;
					}
				}
				if(i==ss.length){
					return "false";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("试题导入出错", e);
			return "false";
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
				logger.error("试题导入文件流关闭失败", e);
			}
		}
		//return "true";
		return msg;
	}

	// 批量导入ByQlbid
	public static void batchimportQlbid(Sheet sheet, int userid)
			throws ElException {
		int qtype = 0;
		String answer = "";
		String[] ch = null;
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {
			Question question = new Question();
			if (sheet.getCell(0, i).getContents().trim().equals("")) {
				break;
			} else {
				if (sheet.getCell(0, i).getContents().trim().equals("判断题"))
					qtype = 1;
				else if (sheet.getCell(0, i).getContents().trim().equals("单选题"))
					qtype = 2;
				else if (sheet.getCell(0, i).getContents().trim().equals(
						"不定项选择题"))
					qtype = 3;
				else if (sheet.getCell(0, i).getContents().trim().equals("多选题"))
					qtype = 4;
				else if (sheet.getCell(0, i).getContents().trim().equals("填空题"))
					qtype = 5;
				else if (sheet.getCell(0, i).getContents().trim().equals("问答题"))
					qtype = 6;
				else if (sheet.getCell(0, i).getContents().trim().equals("材料题"))
					qtype = 7;
				else if (sheet.getCell(0, i).getContents().trim().equals("打字题"))
					qtype = 8;
				else if (sheet.getCell(0, i).getContents().trim().equals("邮件题"))
					qtype = 9;
				else if (sheet.getCell(0, i).getContents().trim().equals("搜索题"))
					qtype = 10;
				else if (sheet.getCell(0, i).getContents().trim().equals(
						"office题"))
					qtype = 11;
				else
					qtype = 12;
				question.setQtype(qtype);
				question
						.setTitle(sheet.getCell(1, i).getContents() == null ? ""
								: sheet.getCell(1, i).getContents().trim()
										.contains(ElConstants.optSplit) ? sheet
										.getCell(1, i).getContents().trim()
										.replaceAll("==", ElConstants.optSplit)
										: sheet.getCell(1, i).getContents()
												.trim());
				question.setContent(sheet.getCell(2, i).getContents().trim());
				question
						.setSubject(sheet.getCell(3, i).getContents() == null ? ""
								: sheet.getCell(3, i).getContents().trim()
										.contains("==") ? sheet.getCell(3, i)
										.getContents().trim().replaceAll("==",
												ElConstants.optSplit)
										+ ElConstants.optSplit : sheet.getCell(
										3, i).getContents().trim());
				if (qtype != 9) {
					if (qtype == 5) {
						if (sheet.getCell(4, i).getContents().trim().contains(
								"=="))
							answer = sheet.getCell(4, i).getContents().trim()
									.replaceAll("==", ElConstants.optSplit)
									+ ElConstants.optSplit;
						else
							answer = sheet.getCell(4, i).getContents().trim();
					} else if (sheet.getCell(4, i).getContents().trim().equals(
							"正确"))
						answer += "yes" + ElConstants.optSplit;
					else if (sheet.getCell(4, i).getContents().trim().equals(
							"错误"))
						answer += "no" + ElConstants.optSplit;
					else {
						ch = sheet.getCell(4, i).getContents().trim().split("");
						if (ch != null) {
							if (qtype == 10) {
								answer = sheet.getCell(4, i).getContents()
										.trim();
							} else {
								for (int j = 0; j < ch.length; j++) {
									// if(ch[j].equals("A"))
									// answer+=0+ElConstants.optSplit;
									// else if(ch[j].equals("B"))
									// answer+=1+ElConstants.optSplit;
									// else if(ch[j].equals("C"))
									// answer+=2+ElConstants.optSplit;
									// else if(ch[j].equals("D"))
									// answer+=3+ElConstants.optSplit;
									// else
									// answer+=ch[j];
									if (!ch[j].trim().equals("")) {
										answer += getIntValue(ch[j].charAt(0))
												+ ElConstants.optSplit;
									}
								}
							}
						}
					}
					question.setAnswer(answer);
				} else {
					question.setAnswers(sheet.getCell(4, i).getContents()
							.trim().split("=="));
				}
				// question.setAnswer(getAnswer(qtype, sheet.getCell(4,
				// i).getContents()
				// .trim()));
				question.setQexplain(sheet.getCell(5, i).getContents().trim());
				question.setQlevel(sheet.getCell(6, i).getContents().trim()
						.equals("") ? 0 : Integer.parseInt(sheet.getCell(6, i)
						.getContents().trim()));
				if (qtype != 9) {
					question.setOldscore(sheet.getCell(7, i).getContents()
							.trim().equals("") ? 0 : Float.parseFloat(sheet
							.getCell(7, i).getContents().trim()));
				} else {
					String rules[] = sheet.getCell(7, i).getContents().trim()
							.split("==");
					question.setOldrules(rules);
					float score = 0;
					if (null != rules) {
						for (int j = 0; j < rules.length; j++) {
							score += ExamPaperUtil.getFloat(rules[j]);
						}
					}
					question.setOldscore(score);
				}
				ELUser e = new ELUser();
				e.setId(userid);
				question.setEluser(e);
				int newqlid = qd.getQlibId(sheet.getCell(8, i).getContents()
						.trim(), userid);
				QuestionLib qlib = new QuestionLib(newqlid);
				question.setQlib(qlib);
				question.setParent(new Question(0));
				qd.addQuestion(question);
				answer = "";
			}
		}
	}

	// 批量导入ByQlbid
	public static String batchimportQlbid(Sheet sheet, int userid, int qlibid,boolean import_)
			throws ElException {
		int qtype = 0;
		int qlid = qlibid;
		String answer = "";
		String standardAnswer = "";//排序题标准答案
		String[] ch = null;
		QuestionDao qd = ((QuestionDao) SpringContextUtil
				.getBean("questionDao"));
		Map<String, Integer> map = new HashMap<String, Integer>();
		int clqId = 0;// 存储材料题的id
		int clqliId = 0;// 存储材料题类别的id
		int m=0;//存储材料题的编号
		StringBuffer msg=new StringBuffer("");
//		QuestionDao questionDao=(QuestionDao)SpringContextUtil.getBean("questionDao");
		if(sheet.getRows()==0){
			return "未找到试题，请检查文档格式是否正确！";
		}
		for (int i = 3; i < sheet.getRows(); i++) {
			Question question = new Question();
			int n = 0;
			answer = "";
			if (sheet.getCell(0, i).getContents().trim().equals("")
					&& sheet.getCell(1, i).getContents().trim().equals("")) {
				// 如果第1列和第2列都为空跳出
				break;
			} else {
				if (sheet.getCell(0, i).getContents().trim().equals("")) {
					// 材料题小题
					n++;
					m++;
				}else{
					m=0;
				}
				if (sheet.getCell(0 + n, i).getContents().trim().equals("判断题"))
					qtype = 1;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"单选题"))
					qtype = 2;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"不定项选择题"))
					qtype = 3;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"多选题"))
					qtype = 4;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"填空题"))
					qtype = 5;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"问答题"))
					qtype = 6;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"材料题"))
					qtype = 7;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"打字题"))
					qtype = 8;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"邮件题"))
					qtype = 9;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"搜索题"))
					qtype = 10;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"office题"))
					qtype = 11;
				//wjm0214修改
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
						"看图选择题"))
					qtype = 15;
				//wjm0225修改
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
				"看动画选择题"))
					qtype = 16;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
				"排序题"))
					qtype = 20;
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
				"角色扮演题"))
					qtype = 17;
				//20140611拖拽题
				else if (sheet.getCell(0 + n, i).getContents().trim().equals(
				"拖拽题"))
					qtype = 19;
				else{
					//qtype = 12;
					msg.append("第"+(i+1)+"行题型有误<br/>");
					continue;
				}
				question.setQtype(qtype);
				question
						.setContent(sheet.getCell(5 + n, i).getContents() == null ? ""
								: sheet.getCell(5 + n, i).getContents().trim()
										.contains(ElConstants.optSplit) ? sheet
										.getCell(5 + n, i).getContents().trim()
										.replaceAll("==", ElConstants.optSplit)
										: sheet.getCell(5 + n, i).getContents()
												.trim());
				question
						.setSubject(sheet.getCell(6 + n, i).getContents() == null ? ""
								: sheet.getCell(6 + n, i).getContents().trim()
										.contains(";") ? sheet
										.getCell(6 + n, i).getContents().trim()
										.replaceAll(";", ElConstants.optSplit)
										+ ElConstants.optSplit : sheet.getCell(
										6 + n, i).getContents().trim());
				switch (qtype) {
				case 1://判断题
					String ans=sheet.getCell(7 + n, i).getContents().trim();
					//没填写答按
					if(ans==null||"".equals(ans)){
						msg.append("第"+(i+1)+"行有误，该题没有填写答案<br/>");
						continue;
					}
					if(Pattern.compile("[1,2]").matcher(ans).matches()){
						if (ans.equals("1")) 
							answer += "yes" + ElConstants.optSplit;
						 else 
							answer += "no" + ElConstants.optSplit;
					}else{
						msg.append("第"+(i+1)+"行判断题答案有误，答案只能是1(正确)和2(错误)<br/>");
						continue;
					}
					break;
				case 2:
				case 3:
				case 4:
					if (qtype == 10) {//搜索题答案
						answer = sheet.getCell(7 + n, i).getContents()
								.trim()
								+ ElConstants.optSplit;
						continue;
					} 
					String ans_temp = sheet.getCell(7 + n, i).getContents().trim();
					String ans_ = ans_temp.toUpperCase();
					//没填写
					if(ans_==null||"".equals(ans_)){
						msg.append("第"+(i+1)+"行有误，该题没有填写答案<br/>");
						continue;
					}
					if(ans_.charAt(ans_.length()-1)!=',')
						ans_=ans_+",";
					if(question.getOption()==null||"".equals(question.getOption())){
						msg.append("第"+(i+1)+"行有误，该题没有填写选项<br/>");
						continue;
					}
					//选项，答案的长度判断，最后一个答案 不能大于选项长度。
					boolean an_=Pattern.compile("([A-Z],)+").matcher(ans_).matches();//Pattern.compile("((A,)?(B,)?(C,)?(D,)?(E,)?(F,)?(G,)?(H,)?(I,)?(J,)?(K,)?(L,)?(M,)?(N,)?(O,)?(P,)?(Q,)?(R,)?(S,)?(T,)?(U,)?(V,)?(W,)?(X,)?(Y,)?(Z,)?)").matcher(ans_).matches();
					int prean_=-1;
					if (an_){
						ch = ans_.split(",");
						int alength = ch.length;
						int optlength=question.getOption().split(ElConstants.optSplit).length;
//						if(alength>optlength){
//							msg.append("第"+(i+1)+"行有误，该题答案个数过大于选项个数<br/>");
//							continue;
//						}
						if(getIntValue(ch[alength-1].charAt(0))>optlength-1){
							msg.append("第"+(i+1)+"行有误，该题答案值超过大于选项个数<br/>");
							continue;
						}
						boolean ansr= true;
						if (ch != null&&ch.length>0&&!ch[0].equals("")) {
								for (int j = 0; j < ch.length; j++) {//多选题答案
									int nowan_ = getIntValue(ch[j].trim().charAt(0));
									if(nowan_<prean_)
									{
										msg.append("第"+(i+1)+"行有误，该题答案顺序不正确<br/>");
										ansr=false;
										break;
									}else
										if (!ch[j].trim().equals("")) {
											answer += nowan_
												+ ElConstants.optSplit;
										}
									prean_ = nowan_;
								}
							}
						if(!ansr)
							continue;
					}else{
						msg.append("第"+(i+1)+"行有误，该题答案不符合规范<br/>");
						continue;
					}
					
					break;
				case 5:
					if (sheet.getCell(7 + n, i).getContents().trim().contains(
							";"))
						answer = sheet.getCell(7 + n, i).getContents().trim()
								.replaceAll(";", ElConstants.optSplit)
								+ ElConstants.optSplit;
					else {
						answer = sheet.getCell(7 + n, i).getContents().trim()
								+ ElConstants.optSplit;
					}
					break;
				case 6:
					answer = sheet.getCell(7 + n, i).getContents().trim();// +
					// ElConstants.optSplit;
					break;
				case 10:
				answer = sheet.getCell(7+n, i).getContents()
				.trim()
				+ ElConstants.optSplit;
				break;
//				case 9:
//					answer = sheet.getCell(7 + n, i).getContents().trim().split(";");
//					break;
				//wjm0214修改
				case 15:
					if (sheet.getCell(7 + n, i).getContents().trim().contains(
							";"))
						answer = sheet.getCell(7 + n, i).getContents().trim()
								.replaceAll(";", ElConstants.optSplit)
								+ ElConstants.optSplit;
					else {
						answer = sheet.getCell(7 + n, i).getContents().trim()
								+ ElConstants.optSplit;
					}
					break;
				case 16:
					if (sheet.getCell(7 + n, i).getContents().trim().contains(
							";"))
						answer = sheet.getCell(7 + n, i).getContents().trim()
								.replaceAll(";", ElConstants.optSplit)
								+ ElConstants.optSplit;
					else {
						answer = sheet.getCell(7 + n, i).getContents().trim()
								+ ElConstants.optSplit;
					}
					break;
				case 19:
					if (sheet.getCell(7 + n, i).getContents().trim().contains(
					";"))
					standardAnswer = sheet.getCell(7 + n, i).getContents().trim()
					.replaceAll(";", ElConstants.optSplit)
						+ ElConstants.optSplit;
				else {
					standardAnswer = sheet.getCell(7 + n, i).getContents().trim()
						+ ElConstants.optSplit;
				}
				
				break;
				case 20:
					
//					if (sheet.getCell(7 + n, i).getContents().trim().contains(
//							";"))
//						answer = sheet.getCell(7 + n, i).getContents().trim()
//								.replaceAll(";", ElConstants.optSplit)
//								+ ElConstants.optSplit;
//					else {
//						answer = sheet.getCell(7 + n, i).getContents().trim()
//								+ ElConstants.optSplit;
//					}
					
					if (sheet.getCell(7 + n, i).getContents().trim().contains(
						";"))
						standardAnswer = sheet.getCell(7 + n, i).getContents().trim()
						.replaceAll(";", ElConstants.optSplit)
							+ ElConstants.optSplit;
					else {
						standardAnswer = sheet.getCell(7 + n, i).getContents().trim()
							+ ElConstants.optSplit;
					}
					
					break;
				default:
					break;
				}
				
				
				if(qtype==9)
					question.setAnswers(sheet.getCell(7+n, i).getContents()
							.trim().split(";"));
				else
					question.setAnswer(answer);
				// question.setAnswer(getAnswer(qtype, sheet.getCell(4,
				// i).getContents()
				// .trim()));
				
				//wjm0225修改
				if(qtype==20 ){
					question.setStandardAnswer(standardAnswer);
					question.setQexplain(sheet.getCell(8 + n, i).getContents()
							.trim());
					question.setStemText(sheet.getCell(9 + n, i).getContents() == null ? ""
							: sheet.getCell(9 + n, i).getContents().trim()
							.contains(ElConstants.optSplit) ? sheet
							.getCell(9 + n, i).getContents().trim()
							.replaceAll("==", ElConstants.optSplit)
							: sheet.getCell(9 + n, i).getContents()
									.trim());
					question.setModelVoice(sheet.getCell(10 + n, i).getContents() == null ? ""
							: sheet.getCell(10 + n, i).getContents().trim()
							);
					question.setModelVoiceText(sheet.getCell(11 + n, i).getContents() == null ? ""
							: sheet.getCell(11+ n, i).getContents().trim()
							);
					question.setFashengQuestion(sheet.getCell(12 + n, i).getContents() == null ? ""
							: sheet.getCell(12 + n, i).getContents().trim());
					question.setMediaFile(sheet.getCell(13 + n, i).getContents() == null ? ""
							: sheet.getCell(13 + n, i).getContents().trim());
				}else if(qtype==17){
					question.setQexplain(sheet.getCell(8 + n, i).getContents()
							.trim());
					question.setStemText(sheet.getCell(9 + n, i).getContents() == null ? ""
							: sheet.getCell(9 + n, i).getContents().trim()
							.contains(ElConstants.optSplit) ? sheet
							.getCell(9 + n, i).getContents().trim()
							.replaceAll("==", ElConstants.optSplit)
							: sheet.getCell(9 + n, i).getContents()
									.trim());
					question.setModelVoice(sheet.getCell(10 + n, i).getContents() == null ? ""
							: sheet.getCell(10 + n, i).getContents().trim()
							);
					question.setModelVoiceText(sheet.getCell(11 + n, i).getContents() == null ? ""
							: sheet.getCell(11+ n, i).getContents().trim()
							);
					question.setMediaFile(sheet.getCell(12 + n, i).getContents() == null ? ""
							: sheet.getCell(12+ n, i).getContents().trim()
					);
					question.setFrontHalfMediaFile(sheet.getCell(13 + n, i).getContents() == null ? ""
							: sheet.getCell(13+ n, i).getContents().trim()
					);
					//20140611
				}else if(qtype==19){
					question.setStandardAnswer(standardAnswer);
					question.setQexplain(sheet.getCell(8 + n, i).getContents()
							.trim());
					question.setStemText(sheet.getCell(9 + n, i).getContents() == null ? ""
							: sheet.getCell(9 + n, i).getContents().trim()
							.contains(ElConstants.optSplit) ? sheet
							.getCell(9 + n, i).getContents().trim()
							.replaceAll("==", ElConstants.optSplit)
							: sheet.getCell(9 + n, i).getContents()
									.trim());
					question.setModelVoice(sheet.getCell(10 + n, i).getContents() == null ? ""
							: sheet.getCell(10 + n, i).getContents().trim()
							);
					question.setModelVoiceText(sheet.getCell(11 + n, i).getContents() == null ? ""
							: sheet.getCell(11+ n, i).getContents().trim()
							);
					question.setFashengQuestion(sheet.getCell(12 + n, i).getContents() == null ? ""
							: sheet.getCell(12 + n, i).getContents().trim());
					question.setMediaFile(sheet.getCell(13 + n, i).getContents() == null ? ""
							: sheet.getCell(13 + n, i).getContents().trim());
					String fenContent="";
					if (sheet.getCell(14 + n, i).getContents().trim().contains(
					";"))
						fenContent = sheet.getCell(14 + n, i).getContents().trim()
					.replaceAll(";", ElConstants.optSplit)
						+ ElConstants.optSplit;
				else {
					fenContent = sheet.getCell(14 + n, i).getContents().trim()
						+ ElConstants.optSplit;
				}
					question.setFenContent(fenContent);
					String fwsizeString=sheet.getCell(15 + n, i).getContents().trim();
					if(fwsizeString!=null&&!"".equals(fwsizeString)){
						if(fwsizeString.equals("开头空")){
							question.setFwsize(1);
						}else if(fwsizeString.equals("结尾空")){
							question.setFwsize(2);
						}else if(fwsizeString.equals("均为空")){
							question.setFwsize(0);
						}else{
							question.setFwsize(3);
						}
					}
					
				}else{
				
				question.setQexplain(sheet.getCell(8 + n, i).getContents()
						.trim());
				}
				question.setQlevel(sheet.getCell(3 + n, i).getContents().trim()
						.equals("") ? 1 : Integer.parseInt(sheet.getCell(3 + n,
						i).getContents().trim()));
				if(question.getQlevel()==0){
					question.setQlevel(1);
				}
				if (qtype != 9) {
					question.setOldscore(sheet.getCell(4 + n, i).getContents()
							.trim().equals("") ? 0 : Float.parseFloat(sheet
							.getCell(4 + n, i).getContents().trim()));
				} else {
					// String rules[] = sheet.getCell(9+n,
					// i).getContents().trim()
					// .split("==");
					// question.setOldrules(rules);
					// float score = 0;
					// if (null != rules) {
					// for (int j = 0; j < rules.length; j++) {
					// score += ExamPaperUtil.getFloat(rules[j]);
					// }
					// }
					// question.setOldscore(score);
				}
				//wjm0214修改
				if(qtype==15||qtype==16){
					question.setStemText(sheet.getCell(9 + n, i).getContents() == null ? ""
							: sheet.getCell(9 + n, i).getContents().trim()
							.contains(ElConstants.optSplit) ? sheet
							.getCell(9 + n, i).getContents().trim()
							.replaceAll("==", ElConstants.optSplit)
							: sheet.getCell(9 + n, i).getContents()
									.trim());
					question.setModelVoice(sheet.getCell(10 + n, i).getContents() == null ? ""
							: sheet.getCell(10 + n, i).getContents().trim()
							);
					question.setFashengQuestion(sheet.getCell(11 + n, i).getContents() == null ? ""
							: sheet.getCell(11 + n, i).getContents().trim());
				}
				//wjm0225修改
				if(qtype==16){
					question.setMediaFile(sheet.getCell(12 + n, i).getContents() == null ? ""
							: sheet.getCell(12+ n, i).getContents().trim()
					);
				}
				
				ELUser e = new ELUser();
				e.setId(userid);
				question.setEluser(e);
				// int newqlid=qd.getQlibId(sheet.getCell(8,
				// i).getContents().trim(), userid);
				int id = 0;
				//wjm0225修改
				if(qtype==17||qtype==20){
				if (n == 0) {
					StringBuffer nowcats = new StringBuffer();
					for (int x = 14; x < sheet.getRow(i).length; x++) {
						String cell = sheet.getCell(x, i).getContents().trim();
						if (("").equals(cell))
							break;
						nowcats.append(cell);
						nowcats.append("=_=");
					}
					String s = nowcats.toString();
					if (map.containsKey(s))
						id = map.get(s);
					if (id == 0) {
						for (int x = 14; x < sheet.getRow(i).length; x++) {
							String cell = sheet.getCell(x, i).getContents()
									.trim();
							if (("").equals(cell))
								break;
							qlibid = id == 0 ? qlibid : id;
							id = qd.getLowerIdById(qlibid, cell);
						}
						map.put(s, id);
					}
				} else {
					question.setScoreper(Integer.parseInt(sheet.getCell(15, i)
							.getContents().trim()));
					id = clqliId;
					question.setSortid(m);
				}//
				}else if(qtype==19){
					if (n == 0) {
						StringBuffer nowcats = new StringBuffer();
						for (int x = 16; x < sheet.getRow(i).length; x++) {
							String cell = sheet.getCell(x, i).getContents().trim();
							if (("").equals(cell))
								break;
							nowcats.append(cell);
							nowcats.append("=_=");
						}
						String s = nowcats.toString();
						if (map.containsKey(s))
							id = map.get(s);
						if (id == 0) {
							for (int x = 16; x < sheet.getRow(i).length; x++) {
								String cell = sheet.getCell(x, i).getContents()
										.trim();
								if (("").equals(cell))
									break;
								qlibid = id == 0 ? qlibid : id;
								id = qd.getLowerIdById(qlibid, cell);
							}
							map.put(s, id);
						}
				}
					}else if(qtype==15||qtype==16){
						if (n == 0) {
							StringBuffer nowcats = new StringBuffer();
							for (int x = 13; x < sheet.getRow(i).length; x++) {
								String cell = sheet.getCell(x, i).getContents().trim();
								if (("").equals(cell))
									break;
								nowcats.append(cell);
								nowcats.append("=_=");
							}
							String s = nowcats.toString();
							if (map.containsKey(s))
								id = map.get(s);
							if (id == 0) {
								for (int x = 13; x < sheet.getRow(i).length; x++) {
									String cell = sheet.getCell(x, i).getContents()
											.trim();
									if (("").equals(cell))
										break;
									qlibid = id == 0 ? qlibid : id;
									id = qd.getLowerIdById(qlibid, cell);
								}
								map.put(s, id);
							}
						} else {
							question.setScoreper(Integer.parseInt(sheet.getCell(14, i)
									.getContents().trim()));
							id = clqliId;
							question.setSortid(m);
						}
					}//
				else{
					if (n == 0) {
						StringBuffer nowcats = new StringBuffer();
						for (int x = 9; x < sheet.getRow(i).length; x++) {
							String cell = sheet.getCell(x, i).getContents().trim();
							if (("").equals(cell))
								break;
							nowcats.append(cell);
							nowcats.append("=_=");
						}
						String s = nowcats.toString();
						if (map.containsKey(s))
							id = map.get(s);
						if (id == 0) {
							for (int x = 9; x < sheet.getRow(i).length; x++) {
								String cell = sheet.getCell(x, i).getContents()
										.trim();
								if (("").equals(cell))
									break;
								qlibid = id == 0 ? qlibid : id;
								id = qd.getLowerIdById(qlibid, cell);
							}
							map.put(s, id);
						}
					} else {
						question.setScoreper(Integer.parseInt(sheet.getCell(14, i)
								.getContents().trim()));
						id = clqliId;
						question.setSortid(m);
					}
				}
				if (question.getQtype() == 8) {// 打字题
					question.setTitle(StringUtil.qshortTitle(question
							.getContent(), 50));
					question.setFwsize(question.getContent().length());
				}
				else if (question.getQtype() == 9) {
					question.setContent(question.getMailContent());
					question.setTitle(question.getMailTitle());
				}
				else if (question.getQtype() == 10) {
					question.setContent(question.getAnswers()[0]);
					question.setTitle(question.getContent());
				}else
					question.setTitle(question.getContent());
				QuestionLib qlib = id == 0 ? new QuestionLib(qlibid)
						: new QuestionLib(id);
				question.setQlib(qlib);
				if (n == 0) {
					question.setParent(new Question(0));
				} else {
					question.setParent(new Question(clqId));
				}
				//判断标题长度
				if(question.getTitle().length()>2000){
					msg.append("第"+(i+1)+"行有误，题干的长度不能超过2000<br/>");
					continue;
				}
				//添加前先检测是否有重复题了
				if (n == 0) {
					if(qd.checkQuestionIsRepeat(question)){
						msg.append("第"+(i+1)+"行未导入， 该题已经存在<br/>");
						continue;
					}
				}
				if(import_)
				qd.addQuestion(question);
				answer = "";
				id = 0;// 清理Id;
				qlibid = qlid;// 还原qlibid
				// 如果是材料题，添加后把id和类别id记录下来
				if (question.getQtype() == 7) {
					clqId = question.getId();
					clqliId = question.getQlib().getId();
				}
			}
		}
		return msg.toString();
	}

	public static void writeDazi(Sheet sheet, int userid, int type)
			throws ElException {
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {
			Question question = new Question();
			question.setTitle("打字题");
			if (question.getTitle() != null && !"".equals(question.getTitle())) {
				question.setContent(sheet.getCell(1, i).getContents().trim());
				question.setSubject(sheet.getCell(1, i).getContents().trim());
				question.setAnswer("");
				question.setQexplain(sheet.getCell(2, i).getContents().trim());
				String level = sheet.getCell(3, i).getContents().trim();
				question.setQlevel(level);
				question.setQtype(type);
				question.setEluser(new ELUser(userid));
				int qlibid = qd.getQlibId(sheet.getCell(4, i).getContents()
						.trim(), userid);
				if (qlibid == -1)
					qlibid = qd.getQLbRoot().getId();
				QuestionLib qlib = new QuestionLib(qlibid);
				question.setQlib(qlib);
				question.setParent(new Question(0));
				question.setOldscore(ExamPaperUtil.getFloat(sheet.getCell(5, i)
						.getContents()));
				qd.addQuestion(question);
			}
		}
	}

	public static void writeSearch(Sheet sheet, int userid, int type)
			throws ElException {
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {
			Question question = new Question();
			question.setTitle(sheet.getCell(1, i).getContents().trim());
			if (question.getTitle() != null && !"".equals(question.getTitle())) {
				question.setContent(sheet.getCell(1, i).getContents().trim());
				question.setAnswer("" + ElConstants.optSplit
						+ sheet.getCell(1, i).getContents().trim());
				question.setQexplain(sheet.getCell(2, i).getContents().trim());
				String level = sheet.getCell(3, i).getContents().trim();
				question.setQlevel(level);
				question.setQtype(type);
				question.setEluser(new ELUser(userid));
				int qlibid = qd.getQlibId(sheet.getCell(4, i).getContents()
						.trim(), userid);
				if (qlibid == -1)
					qlibid = qd.getQLbRoot().getId();
				QuestionLib qlib = new QuestionLib(qlibid);
				question.setQlib(qlib);
				question.setParent(new Question(0));
				question.setOldscore(ExamPaperUtil.getFloat(sheet.getCell(5, i)
						.getContents()));
				qd.addQuestion(question);
			}
		}
	}

	public static void writeOffice(Sheet sheet, int userid, int type)
			throws ElException {
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {
			Question question = new Question();
			question.setTitle("【" + sheet.getCell(0, i).getContents().trim()
					+ "】" + sheet.getCell(1, i).getContents().trim());
			if (question.getTitle() != null && !"".equals(question.getTitle())) {
				question.setContent(sheet.getCell(1, i).getContents().trim());
				question.setAnswer("");
				question.setQexplain(sheet.getCell(2, i).getContents().trim());
				String level = sheet.getCell(3, i).getContents().trim();
				question.setQlevel(level);
				question.setQtype(type);
				question.setEluser(new ELUser(userid));
				int qlibid = qd.getQlibId(sheet.getCell(4, i).getContents()
						.trim(), userid);
				if (qlibid == -1)
					qlibid = qd.getQLbRoot().getId();
				QuestionLib qlib = new QuestionLib(qlibid);
				question.setQlib(qlib);
				question.setParent(new Question(0));
				question.setOldscore(ExamPaperUtil.getFloat(sheet.getCell(5, i)
						.getContents()));
				qd.addQuestion(question);
			}
		}
	}

	public static void writeEMAIL(Sheet sheet, int userid, int type)
			throws ElException {
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {

			Question question = new Question();
			question.setTitle(sheet.getCell(1, i).getContents().trim());
			if (question.getTitle() != null && !"".equals(question.getTitle())) {
				question.setContent(sheet.getCell(1, i).getContents().trim());
				question.setAnswers(sheet.getCell(2, i).getContents().trim()
						.split("=="));
				question.setEluser(new ELUser(userid));
				question.setQexplain(sheet.getCell(3, i).getContents().trim());
				String level = sheet.getCell(4, i).getContents().trim();
				question.setQlevel(level);
				question.setQtype(type);
				int qlibid = qd.getQlibId(sheet.getCell(5, i).getContents()
						.trim(), userid);
				if (qlibid == -1)
					qlibid = qd.getQLbRoot().getId();
				QuestionLib qlib = new QuestionLib(qlibid);
				question.setQlib(qlib);
				question.setParent(new Question(0));
				String rules[] = sheet.getCell(6, i).getContents().trim()
						.split("==");
				question.setOldrules(rules);
				float score = 0;
				if (null != rules) {
					for (int j = 0; j < rules.length; j++) {
						score += ExamPaperUtil.getFloat(rules[j]);
					}
				}
				question.setOldscore(score);
				qd.addQuestion(question);
			}
		}
	}

	// public static void writeSelect(Sheet sheet, int userid) throws
	// ElException {
	// QuestionDao qd = new QuestionDaoImpl();
	// for (int i = 1; i < sheet.getRows(); i++) {
	//
	// Question question = new Question();
	// String types = sheet.getCell(0, i).getContents().trim();
	// int type = 0;
	// if (types.equals("单选题"))
	// type = 2;
	// else if (types.equals("多选题"))
	// type = 4;
	// else
	// type = 2;
	// question.setTitle(sheet.getCell(1, i).getContents().trim());
	// if (question.getTitle() != null && !"".equals(question.getTitle())) {
	// question.setContent(sheet.getCell(1, i).getContents().trim());
	// question.setOptions(sheet.getCell(2, i).getContents().trim()
	// .split("=="));
	// String answer = sheet.getCell(3, i).getContents().trim();
	// String answer1 = "";
	// for (int j = 0; j < answer.length(); j++) {
	// if (!"".equals(answer.indexOf(j))) {
	// answer1 += getIntValue(answer.charAt(j))
	// + ElConstants.optSplit;
	// }
	// }
	// question.setAnswers(answer1.split(ElConstants.optSplit));
	//
	// question.setEluser(new ELUser(userid));
	// question.setQexplain(sheet.getCell(4, i).getContents().trim());
	// String level = sheet.getCell(5, i).getContents().trim();
	// question.setQlevel(level);
	// question.setQtype(type);
	// int qlibid = qd.getQlibId(sheet.getCell(6, i).getContents()
	// .trim(), userid);
	// if (qlibid == -1)
	// qlibid = qd.getQLbRoot().getId();
	// QuestionLib qlib = new QuestionLib(qlibid);
	// question.setQlib(qlib);
	// question.setParent(new Question(0));
	// question.setOldscore(ExamPaperUtil.getFloat(sheet.getCell(7, i)
	// .getContents()));
	// qd.addQuestion(question);
	// }
	// }
	// }

	public static void writeBlankAndEasy(Sheet sheet, int type, int userid)
			throws ElException {
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {
			Question question = new Question();
			question.setTitle(sheet.getCell(1, i).getContents().trim());
			question.setContent(sheet.getCell(2, i).getContents().trim());

			question.setAnswers(sheet.getCell(3, i).getContents().trim().split(
					"=="));

			question.setEluser(new ELUser(userid));
			question.setQexplain(sheet.getCell(4, i).getContents().trim());
			String level = sheet.getCell(5, i).getContents().trim();

			// if ("高".equals(level))
			// question.setQlevel(5);
			// else if ("中".equals(level))
			// question.setQlevel(3);
			// else if ("低".equals(level))
			// question.setQlevel(1);
			// else
			// question.setQlevel(1);
			question.setQlevel(level);
			question.setQtype(type);
			int qlibid = qd.getQlibId(sheet.getCell(6, i).getContents().trim(),
					userid);
			if (qlibid == -1)
				// qlibid = 0;
				qlibid = qd.getQLbRoot().getId();
			QuestionLib qlib = new QuestionLib(qlibid);
			question.setQlib(qlib);
			question.setParent(new Question(0));
			qd.addQuestion(question);
		}
	}

	public static void writeYesOrno(Sheet sheet, int type, int userid)
			throws ElException {
		QuestionDao qd = new QuestionDaoImpl();
		for (int i = 1; i < sheet.getRows(); i++) {
			Question question = new Question();
			question.setTitle(sheet.getCell(1, i).getContents().trim());
			question.setContent(sheet.getCell(1, i).getContents().trim());
			String answer = sheet.getCell(2, i).getContents().trim();
			if ("错误".equals(answer))
				answer = "no";
			else
				answer = "yes";
			question.setAnswers(answer.split("=="));
			question.setEluser(new ELUser(userid));
			question.setQexplain(sheet.getCell(3, i).getContents().trim());
			String level = sheet.getCell(4, i).getContents().trim();
			question.setQlevel(level);
			question.setQtype(type);
			int qlibid = qd.getQlibId(sheet.getCell(5, i).getContents().trim(),
					userid);
			if (qlibid == -1)
				qlibid = qd.getQLbRoot().getId();
			QuestionLib qlib = new QuestionLib(qlibid);
			question.setQlib(qlib);
			question.setParent(new Question(0));
			question.setOldscore(ExamPaperUtil.getFloat(sheet.getCell(6, i)
					.getContents()));
			qd.addQuestion(question);
		}
	}

	// public static void writeQuestion(Sheet sheet, int userid, int qlibid)
	// throws ElException {
	// QuestionDao qd = new QuestionDaoImpl();
	// for (int i = 1; i < sheet.getRows(); i++) {
	//
	// Question question = new Question();
	// String types = sheet.getCell(0, i).getContents().trim();
	// int type = getQtype(types);
	// if (type != -1) {
	// question.setTitle(sheet.getCell(1, i).getContents().trim());
	// if (question.getTitle() != null
	// && !"".equals(question.getTitle())) {
	// question.setContent(sheet.getCell(1, i).getContents()
	// .trim());
	// if (type == 2 || type == 3 || type == 4) {
	// question.setOptions(sheet.getCell(2, i).getContents()
	// .trim().split("=="));
	// } else {
	// question.setSubject("");
	// }
	// String answer = sheet.getCell(3, i).getContents().trim();
	// if (type == 2 || type == 3 || type == 4) {
	// String answer1 = "";
	// for (int j = 0; j < answer.length(); j++) {
	// if (!"".equals(answer.indexOf(j))) {
	// answer1 += getIntValue(answer.charAt(j))
	// + ElConstants.optSplit;
	// }
	// }
	// question
	// .setAnswers(answer1.split(ElConstants.optSplit));
	// } else if (type == 1) {
	// if ("错误".equals(answer))
	// answer = "no";
	// else
	// answer = "yes";
	// } else if (type == 8) {
	// question
	// .setTitle("打字题:"
	// + (question.getContent().length() > 31 ? question
	// .getContent().substring(0, 30)
	// + "..."
	// : question.getContent()));
	//
	// } else if (type == 9) {
	// question.setAnswers(sheet.getCell(3, i).getContents()
	// .trim().split("=="));
	//
	// } else if (type == 10) {
	// question.setAnswer(sheet.getCell(1, i).getContents()
	// .trim()
	// + ElConstants.optSplit);
	// } else if (type == 5 || type == 6) {
	// question.setAnswers(sheet.getCell(3, i).getContents()
	// .trim().split("=="));
	// } else {
	// question.setAnswer("");
	// }
	// question.setEluser(new ELUser(userid));
	// question.setQexplain(sheet.getCell(4, i).getContents()
	// .trim());
	// String level = sheet.getCell(5, i).getContents().trim();
	// question.setQlevel(level);
	// question.setQtype(type);
	// QuestionLib qlib = new QuestionLib(qlibid);
	// question.setQlib(qlib);
	// question.setParent(new Question(0));
	// if (type == 9) {
	// String rules[] = sheet.getCell(6, i).getContents()
	// .trim().split("==");
	// question.setOldrules(rules);
	// float score = 0;
	// if (null != rules) {
	// for (int j = 0; j < rules.length; j++) {
	// score += ExamPaperUtil.getFloat(rules[j]);
	// }
	// }
	// question.setOldscore(score);
	// } else {
	// question.setOldscore(ExamPaperUtil.getFloat(sheet
	// .getCell(6, i).getContents()));
	// }
	// qd.addQuestion(question);
	// }
	// }
	// }
	// }

	public static int getQtype(String qtypeName) {
		if (qtypeName == null)
			return -1;
		if (qtypeName.trim().equals("判断题"))
			return 1;
		if (qtypeName.trim().equals("单选题"))
			return 2;
		if (qtypeName.trim().equals("不定项选择题"))
			return 3;
		if (qtypeName.trim().equals("多选题"))
			return 4;
		if (qtypeName.trim().equals("填空题"))
			return 5;
		if (qtypeName.trim().equals("问答题"))
			return 6;
		if (qtypeName.trim().equals("打字题"))
			return 8;
		if (qtypeName.trim().equals("邮件题"))
			return 9;
		if (qtypeName.trim().equals("搜索题"))
			return 10;
		if (qtypeName.trim().equals("WORD题")
				|| qtypeName.trim().equals("EXCEL题"))
			return 11;
		return -1;
	}

	public static boolean checkImport(Sheet sheet, int i) {
		boolean is = true;
		if (sheet.getCell(0, i).getContents().trim().equals("判断题")) {// 题型
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("单选题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("不定项选择题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("多选题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("填空题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("问答题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("材料题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("打字题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("邮件题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			// if(sheet.getCell(3,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,题支不能为空");is= false;}//题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("搜索题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			// if(sheet.getCell(3,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,题支不能为空");is= false;}//题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			if (sheet.getCell(5, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,解析不能为空");
				is = false;
			}// 解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		} else if (sheet.getCell(0, i).getContents().trim().equals("office题")) {
			if (sheet.getCell(1, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,试题名称不能为空");
				is = false;
			}// 试题名称
			if (sheet.getCell(2, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题干不能为空");
				is = false;
			}// 题干
			if (sheet.getCell(3, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,题支不能为空");
				is = false;
			}// 题支
			if (sheet.getCell(4, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,答案不能为空");
				is = false;
			}// 答案
			// if(sheet.getCell(5,
			// i).getContents().trim().equals("")){impTip.append("
			// 第"+i+"行,解析不能为空");is= false;}//解析 可不填
			if (sheet.getCell(6, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,难度不能为空");
				is = false;
			}// 难度
			if (sheet.getCell(7, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,参考分值不能为空");
				is = false;
			}// 参考分值
			if (sheet.getCell(8, i).getContents().trim().equals("")) {
				impTip.append("  第" + i + "行,所属题库名称不能为空");
				is = false;
			}// 所属题库名称
		}
		return is;
	}

	// 小写字母转换为数字
	public static int getIntValue(char c) {
		// Character c = s.charAt(0);

		return (int) Character.toLowerCase(c) - 97;
	}

	// 数字转换为大写字母
	public static char getCharValue(int c) {
		// Character c = s.charAt(0);

		return (char) ((char) Character.toLowerCase(c) + 65);
	}

	public StringBuffer getImpTip() {
		return impTip;
	}

	// public void setImpTip(StringBuffer impTip) {
	// this.impTip = impTip;
	// }
	
//	
	//wjm0221词汇导入
	/**
	 * Description:指定词汇目录的词汇导入操作
	 * 
	 * @Version1.0 2012-7-15 上午11:42:56 by 闻益舜（wenyishun110@163.com）创建
	 * @param source
	 * @param qlibid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public static String writeVoc(File source, int userid,boolean import_)
			throws ElException {
		InputStream is = null;
		String msg="";
		try {
			is = new FileInputStream(source);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
//			if (null != ss && ss.length > 0) {
//				Sheet ss1 = ss[0];
//				// writeQuestion(ss1, userid, qlibid);
//				msg=batchimportQlbid(ss1, userid, qlibid);
//			}
			if(null != ss){
				int i=0;
				for(i=0;i<ss.length ;i++) {
					Sheet ss1 = ss[i];
					if(ss1.getRows()>0){
						msg=batchimportVoc(ss1, userid,import_);
						break;
					}
				}
				if(i==ss.length){
					return "false";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("词汇导入出错", e);
			return "false";
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
				logger.error("试题导入文件流关闭失败", e);
			}
		}
		//return "true";
		return msg;
	}
	
	// 批量导入ByQlbid
	public static String batchimportVoc(Sheet sheet, int userid,boolean import_)
			throws ElException {
		WordDao wd = ((WordDao) SpringContextUtil
				.getBean("WordDao"));
		Map<String, Integer> map = new HashMap<String, Integer>();
		int clqId = 0;// 存储材料题的id
		int clqliId = 0;// 存储材料题类别的id
		int m=0;//存储材料题的编号
		StringBuffer msg=new StringBuffer("");
//		QuestionDao questionDao=(QuestionDao)SpringContextUtil.getBean("questionDao");
		if(sheet.getRows()==0){
			return "未找到词汇，请检查文档格式是否正确！";
		}
		for (int i = 3; i < sheet.getRows(); i++) {
			Vocabulary voc = new Vocabulary();
			int n = 0;
			if (sheet.getCell(0, i).getContents().trim().equals("")
					&& sheet.getCell(1, i).getContents().trim().equals("")) {
				// 如果第1列和第2列都为空跳出
				break;
			} else {
				
				voc.setName(sheet.getCell( n, i).getContents() == null ? ""
						: sheet.getCell(n, i).getContents().trim());
				int wordid=wd.getwdLibTreeId(sheet.getCell( 1+n, i).getContents() == null ? ""
						: sheet.getCell(1+n, i).getContents().trim());
				voc.setWordid(wordid);
				voc.setYingwen(sheet.getCell( 2+n, i).getContents() == null ? ""
						: sheet.getCell(2+n, i).getContents().trim());
				voc.setPinyin(sheet.getCell( 3+n, i).getContents() == null ? ""
						: sheet.getCell(3+n, i).getContents().trim());
				voc.setWenzijieshi(sheet.getCell( 6+n, i).getContents() == null ? ""
						: sheet.getCell(6+n, i).getContents().trim());
				voc.setShengyinjieshi(sheet.getCell( 7+n, i).getContents() == null ? ""
						: sheet.getCell(7+n, i).getContents().trim());
				voc.setDuyin(sheet.getCell( 8+n, i).getContents() == null ? ""
						: sheet.getCell(8+n, i).getContents().trim());
				
				String [] lijuText = (sheet.getCell( 4+n, i).getContents() == null ? ""
						: sheet.getCell(4+n, i).getContents().trim()).split(ElConstants.vocsplit);
				String [] lijuVocie = (sheet.getCell( 5+n, i).getContents() == null ? ""
						: sheet.getCell(5+n, i).getContents().trim()).split(ElConstants.vocsplit);


				
				ELUser e = new ELUser();
				e.setId(userid);
				voc.setStatus(1);
				voc.setAdduserid(userid);
				int id = 0;
				int id_=0;
				
				//添加前先检测是否有重复题了
				if (n == 0) {
					if(wd.checkVocIsRepeat(voc)){
						msg.append("第"+(i+1)+"行未导入， 该词汇已经存在<br/>");
						continue;
					}
				}
				if(import_)
				id_ = wd.addVocabulary(voc);
				voc.setId(id_);
				for(int j=0;j<lijuText.length;j++){
					voc.setWenziliju(lijuText[j]);
					voc.setLijudizhi(lijuVocie[j]);
					wd.addVocabularySen(voc);
				}
				
			}
		}
		return msg.toString();
	}

}
