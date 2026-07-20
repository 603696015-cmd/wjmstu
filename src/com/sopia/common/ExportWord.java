package com.sopia.common;

import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;
/*1.10版本的修改，有备份*/
public class ExportWord extends BaseAction {
	private static final Log logger = LogFactory.getLog(ExportWord.class);
//	private static QuestionLib qlbTree;
//	private static QuestionLib qlbTrees;
	private static int y = 0;
//	private static StringBuffer QlbName;// 返回题库类别名
//	private static List libName;// 返回题库类别名
	private static int qlibid;// 存放上一题题库id
	private static String qlibAllName;

	/**
	 * 根据试题，类别导出试题 Description:
	 * 
	 * @Version1.0 2012-7-9 下午03:12:20 by 闻益舜（wenyishun110@163.com）创建
	 * @param os
	 * @param questionlist
	 * @param qlbid
	 * @throws Exception
	 */
	public static void writeExcel(OutputStream os, List<Question> questionlist,
			int qlbid) throws Exception {
		try {
			// qlbTree = new
			// QuestionDaoImpl().getQlibTree(qlbid,1,ElConstants.TREE_FIANL,
			// true);
			// qlbTrees = new
			// QuestionDaoImpl().getQlibTree(ElConstants.TREE_ROOT,1,ElConstants.TREE_FIANL,
			// true);
			// File f = new File("e:kkasdasd.xls"); //测试
			// f.createNewFile();
			// WritableWorkbook wwb = Workbook.createWorkbook(new
			// FileOutputStream(f));

			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("TestSheet", 0);
//			QlbName = new StringBuffer();
//			libName = new ArrayList();
			qlibid = 0;
			// writeChilds(ws, qlbTree); //单独的部门结构
			WritableFont wf = new WritableFont(WritableFont.TAHOMA, 20,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 
			WritableCellFormat wcf = new WritableCellFormat(wf);
			ws.setColumnView(0, 12);
			ws.setColumnView(1, 12);
			ws.setColumnView(2, 12);
			ws.setColumnView(3, 6);
			ws.setColumnView(4, 6);
			ws.setColumnView(5, 22);
			ws.setColumnView(6, 22);
			ws.setColumnView(7, 12);
			ws.setColumnView(8, 22);
			ws.setColumnView(9, 12);
			ws.setRowView(1, 2500);
			Label l = new Label(0, 0, "【五矿发展员工职业发展系统】试题文件模板", wcf);
			ws.addCell(l);
			ws.mergeCells(0, 0, 9, 0);
			wcf = new WritableCellFormat(WritableWorkbook.ARIAL_10_PT);
			wcf.setWrap(true);//
			wcf.setBackground(Colour.GREY_25_PERCENT);
			l = new Label(
					0,
					1,
					"说明：\012 "
							+ "1.使用试题模板时，请注意保护模板格式的完整性，包括文字、表头与表项的行列位置\012 "
							+ "2.向【五矿发展员工职业发展系统】导入试题时，请确保题型已在【五矿发展员工职业发展系统】中进行定义，否则将出现试题无法导入的情况\012 "
							+ "3.【五矿发展员工职业发展系统】试题类型有单选类、多选类、判断类、填空类、问答类、邮件类、搜索题、打字题、材料题、office。10种\012 "
							+ "4.试题难度分为1-5，1表示最易，5表示最难，试题分数的有效范围是1-100的整数\012 "
							+ "5.填空类试题中用三个英文下滑线“___”表示填空处，多个答案请用逗号隔开\012 "
							+ "6.供选答案只对单选类、多选类试题有效，其他类型供选答案可以为空。单一供选答案中不能有“;”，多个供选答案之间用“;”隔开\012 "
							+ "7.标准答案中单选类只能是A-Z中的一个字符;多选类可以是A-Z中的多个字符，中间分隔符为“,”，判断类只能是1或2，1表示正确，2表示错误\012 "
							+ "8.模版中J列开始为所属知识点（所属题库） ，按上下级顺序排列(上级排在前面)\012 "
							+ "9.向【五矿发展员工职业发展系统】中导入试题时，应使用此模板创建试题文件，否则将无法进行导入", wcf);
			ws.addCell(l);
			ws.mergeCells(0, 1, 9, 1);
			y = 2;
			wcf = new WritableCellFormat(new WritableFont(WritableFont.TAHOMA,
					10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.YELLOW2));
			wcf.setWrap(true);//
			wcf.setBackground(Colour.BLUE);
			wcf.setAlignment(Alignment.CENTRE);
			// for (int i = 0; 7 >= i; i++) {// 试题标题
			Label tixing = new Label(0, y, "题目类型", wcf);// 题型
			ws.addCell(tixing);
			ws.addCell(new Label(1, y, "基本类型	", wcf));// 题干内容
			ws.addCell(new Label(2, y, "知识点", wcf));// 题干内容
			Label nandu = new Label(3, y, "难度", wcf);// 难度
			ws.addCell(nandu);
			Label cankaozhi = new Label(4, y, "分值", wcf);// 参考分值
			ws.addCell(cankaozhi);
			Label tigan = new Label(5, y, "题干内容", wcf);// 题干内容
			ws.addCell(tigan);
			Label tizhi = new Label(6, y, "题支(选择题选项)", wcf);// 题支(选择题选项)
			ws.addCell(tizhi);
			Label daan = new Label(7, y, "答案", wcf);// 答案
			ws.addCell(daan);
			Label jiexi = new Label(8, y, "解析", wcf);// 解析
			ws.addCell(jiexi);
			// ws.addCell(new Label(9, y, "评分规则"));
			Label shuyutiku = new Label(9, y, "所属题库", wcf);// 所属题库
			ws.addCell(shuyutiku);
			// }
			Question q = null;
			for (int i = 0; questionlist.size() > i; i++) { // 试题内容
				q = questionlist.get(i);
				// 放在这里
				String newtestsupport = "";
				String answer = "";
				switch (q.getQtype()) {
				case 1:
					if (q.getAnswers() != null && q.getAnswers().length > 0) {
						if ("no".equals(q.getAnswers()[0]))
							answer = "2";
						if ("yes".equals(q.getAnswers()[0]))
							answer = "1";
					}
					break;
				case 2:
					if (q.getSubject() != null
							&& q.getSubject().lastIndexOf(ElConstants.optSplit) != -1) {
						newtestsupport = q.getSubject().substring(
								0,
								q.getSubject()
										.lastIndexOf(ElConstants.optSplit))
								.replaceAll(ElConstants.optSplit, ";");
					}
					if (q.getAnswers() != null && q.getAnswers().length > 0) {
						for (int j = 0; j < q.getAnswers().length; j++) {
							answer += ExcelUtil.getCharValue(Integer.parseInt(q
									.getAnswers()[j]))
									+ ",";
						}
					}
					break;
				case 4:
					if (q.getSubject() != null
							&& q.getSubject().lastIndexOf(ElConstants.optSplit) != -1) {
						newtestsupport = q.getSubject().substring(
								0,
								q.getSubject()
										.lastIndexOf(ElConstants.optSplit))
								.replaceAll(ElConstants.optSplit, ";");
					}
					if (q.getAnswers() != null && q.getAnswers().length > 0) {
						for (int j = 0; j < q.getAnswers().length; j++) {
							answer += ExcelUtil.getCharValue(Integer.parseInt(q
									.getAnswers()[j]))
									+ ",";
						}
					}
					break;
				case 5:
					answer = q.getAnswer().substring(0,
							q.getAnswer().lastIndexOf(ElConstants.optSplit))
							.replaceAll(ElConstants.optSplit, ";");
					break;
				case 6:
					answer = q.getAnswer();
					break;
				case 9:
					answer = q.getAnswer().substring(0,
							q.getAnswer().lastIndexOf(ElConstants.optSplit))
							.replaceAll(ElConstants.optSplit, ";");
					break;
				case 10:
					answer = q.getAnswer().substring(0,
							q.getAnswer().lastIndexOf(ElConstants.optSplit))
							.replaceAll(ElConstants.optSplit, ";");
					break;
				case 11:
					newtestsupport = q.getSubject();
				default:
					break;
				}

				y++;
				int n = 0;
				// 判断是否材料题小题
				if (q.getParent() != null && q.getParent().getId() > 0) {
					n++;
				}
				tixing = new Label(0 + n, y, questionlist.get(i).getQtypeName());// 题型
				ws.addCell(tixing);
				tigan = new Label(5 + n, y, questionlist.get(i).getContent());// 题干内容
				ws.addCell(tigan);
				tizhi = new Label(6 + n, y, newtestsupport);// 题支(选择题选项)
				ws.addCell(tizhi);
				daan = new Label(7 + n, y, answer);// 答案
				ws.addCell(daan);
				jiexi = new Label(8 + n, y, questionlist.get(i).getQexplain());// 解析
				ws.addCell(jiexi);
				nandu = new Label(3 + n, y, questionlist.get(i).getQlevel()
						+ "");// 难度
				ws.addCell(nandu);
				cankaozhi = new Label(4 + n, y, questionlist.get(i)
						.getOldrulestring());// 参考分值
				ws.addCell(cankaozhi);
				if (n == 1) {
					Label fenzhi = new Label(9 + n, y, questionlist.get(i)
							.getScoreper()
							+ "");// 参考分值
					ws.addCell(fenzhi);
					continue;
				}
				int qlibid_ =questionlist.get(i).getQlib() == null ? 0:
					 questionlist.get(i).getQlib().getId();
				if (qlibid_ != qlibid) {// 判断此题和上一题是否在同一节点下，如果是的话就直接取值（避免数据库再次查询）
					qlibAllName = null;
//					libName.clear();// 清空list
					qlibAllName =getQlbNames(questionlist.get(i).getQlib().getId(), qlbid);
//					System.out.println(qlibAllName);
					qlibid = qlibid_;
				}	
					String[] qlibNameArray = qlibAllName.split("-=wsy=-");
					int size = qlibNameArray.length;
					for (int x = 0; x < size; x++) {
						// int f = (size-1)-x == -1 ? size-x : (size-1)-x;
						Label shusuotiku = new Label(x + n + 9, y,
								qlibNameArray[x]);// 所属题库
						ws.addCell(shusuotiku);
					}
//					QlbName.setLength(0);// 清空
//				} else {
//					qlibAllName = null;
//					libName.clear();// 清空list
//					qlibAllName =getQlbNames(questionlist.get(i).getQlib().getId(), qlbid);
//					System.out.println(qlibAllName);
//					for (int x = 0; x < libName.size(); x++) {
//						int f = (libName.size() - 1) - x == -1 ? libName.size()
//								- x : (libName.size() - 1) - x;
//						Label shusuotiku = new Label(x + n + 9, y,
//								(String) libName.get(f));// 所属题库
//						ws.addCell(shusuotiku);
//						//
//						if (qlibAllName == null) {
//							qlibAllName = libName.get(f).toString();
//						} else {
//							qlibAllName += "-=wsy=-"
//									+ libName.get(f).toString();
//						}
//					}
//					qlibid = questionlist.get(i).getQlib() == null ? 0
//							: questionlist.get(i).getQlib().getId();
//					libName.clear();// 清空list
//					QlbName.setLength(0);// 清空
//				}
			}
			y = 0; // 还原Y值
			wwb.write(); // 写入Exel工作表
			wwb.close();// 关闭Excel工作薄对象
			// System.gc();
		} catch (Exception ex) {
			logger.error("试题导出文件错误", ex);
		}
	}

	// public static void writeChilds(WritableSheet ws, Object obj)
	// throws Exception {
	// y = y + 1;
	// QuestionLib qlb = (QuestionLib) obj;
	// List<QuestionLib> qlbChild = qlb.getChild();
	// if (qlb.getLevel() == 0)
	// if (true) {// 判断是否为跟目录
	// Label labelC = new Label(qlb.getLevel(), y, qlb.getName());
	// ws.addCell(labelC);
	// } else {
	// Label labelC = new Label(qlb.getLevel(), y, qlb.getName());
	// ws.addCell(labelC);
	// }
	// else {
	// Label labelC = new Label(qlb.getLevel(), y, qlb.getName());
	// ws.addCell(labelC);
	// }
	// for (int i = 0; i < qlbChild.size(); i++) {
	// QuestionLib qlbi = qlbChild.get(i);
	// writeChilds(ws, qlbi);
	// }
	// }

	/*public static void getQlbNames1(int id, int level, int qlbid)
			throws Exception {
		QuestionLib qlb = new QuestionDaoImpl().getQLbById(id);
		if (qlb != null && qlb.getParent() != null) {
			int Parentid = qlb.getParent().getId();
			if (qlb.getId() != qlbid) {
				libName.add(qlb.getName());
			}
			for (int i = 0; i < level; i++) {
				if (qlbid == 1) {
					if (qlb.getParent().getId() == qlbid) {
						// libName.add(qlb.getName());
						break;
					}
				} else {
					if (qlb.getId() == qlbid) {
						libName.add(qlb.getName());
						break;
					}
				}
				getQlbNames1(Parentid, level, qlbid);
			}
		}
	}*/

	public static String getQlbNames(int id,int qlbid)
			throws Exception {
		String s = "";
		QuestionLib qlb = ((QuestionDao)SpringContextUtil.getBean("questionDao")).getQLbById(id);
		if (qlb != null &&qlb.getId()!=0) {
			s = qlb.getName();
			if(qlb.getParent() != null&&qlb.getId()!=qlbid)
			s = getQlbNames( qlb.getParent().getId(), qlbid)+"-=wsy=-"+s;
		}
		return s;
	}

//	public static QuestionLib getQlbTree() {
//		return qlbTree;
//	}
//
//	public static void setQlbTree(QuestionLib qlbTree) {
//		ExportWord.qlbTree = qlbTree;
//	}

//	public static StringBuffer getQlbName() {
//		return QlbName;
//	}
//
//	public static void setQlbName(StringBuffer qlbName) {
//		QlbName = qlbName;
//	}
//
//	public static List getLibName() {
//		return libName;
//	}
//
//	public static void setLibName(List libName) {
//		ExportWord.libName = libName;
//	}

}