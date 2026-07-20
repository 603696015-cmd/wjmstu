package com.sopia.simulation.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import com.sopia.simulation.entity.Paper;
import com.sopia.simulation.entity.QuestionNum;

public class SimulationUtil {

	public static List<ExamPaperBlock> data = null;

	private static String str[] = new String[] { "一", "二", "三", "四", "五", "六",
			"七", "八", "九", "十" };

	private static String charter[] = new String[26];

	private static final String EQUAL = "=";

	private static final String OR = "|";

	static {
		createChater(charter);
	}

	public static String pinyinHtml(String str) {
		String returnStr = "";
		try {
			String str2= str.replaceAll("\\s*", "").trim();
			str = str2;
			String py = PinyinHelper.convertToPinyinString(str, "|",
					PinyinFormat.WITH_TONE_MARK);
			char[] charArray = str.toCharArray();
			String[] pyArray = py.split("\\|");
			returnStr = "";
			int j = 0;
			int h = 0;
			for (int i = 0; i < pyArray.length; i++) {
				i= h;
				if (ChineseHelper.isChinese(charArray[j])) {
					returnStr += "<ruby>" + charArray[j] + "<rt>" + pyArray[i]
							+ "</rt></ruby>";
					j++;
					h++;
					if(j == pyArray.length){
						break;
					}
				} else {
					for (int k = j; k < charArray.length; k++) {
						if (ChineseHelper.isChinese(charArray[j])) {
							break;
						}
						returnStr += charArray[k];
						j++;h++;
					}
					
					if(j == pyArray.length){
						break;
					}
				}
			}
		} catch (PinyinException e) {
			// TODO Auto-generated catch block
		}
		return returnStr;
	}

	public static List<Paper> questionConvertList(List<Question> ques,
			QuestionNum qn, int tempIndex) {
		List<Paper> paperData = new ArrayList<Paper>();
		List<Integer> nums = new ArrayList<Integer>();
		int i = tempIndex;
		for (Question question : ques) {
			Paper paper = null;
			switch (question.getQtype()) {
			case 15:
				// 看图选择
				paper = lookPicQuestion(question, "1");
				break;
			case 18:
				// 听音选图题型
				paper = lookPicQuestion(question, "2");
				break;
			default:
				break;
			}

			paperData.add(paper);
			nums.add((i + 1));
			i++;
		}
		qn.setNum(nums);
		return paperData;
	}

	private static void createChater(String[] parms) {
		int index = 0;
		for (int i = 1; i <= 26; i++) {
			char charter = (char) (96 + i);
			String strCharter = (charter + "").toUpperCase();
			parms[index] = strCharter;
			index++;
		}
	}

	/**
	 * 看图选择题型
	 */
	public static Paper lookPicQuestion(Question question, String type) {
		Paper pp = new Paper();
		pp.setAudio(true);
		pp.setId(String.valueOf(question.getId() + ""));
		pp.setMp3Url(question.getVoicePath());
		pp.setImageUrl(question.getContent());
		pp.setScore(String.valueOf(question.getScore() + ""));
		pp.setType("1");// 题型部分
		pp.setQuesType(type);
		pp.setOptions(question.getOptions());
		pp.setAnswer(question.getAnswer());
		return pp;
	}

	public static List<QuestionNum> getQuestionNum(String json) {
		List<QuestionNum> result = new ArrayList<QuestionNum>();
		JSONArray jsonArr = JSONArray.fromObject(json);
		int i = 0;
		int j = 0;
		QuestionNum qn = new QuestionNum();
		List<Integer> nums = new ArrayList<Integer>();
		for (Object object : jsonArr) {
			int temInt = i + 1;

			if (temInt > jsonArr.size()) {
				break;
			}

			if (temInt == jsonArr.size()) {
				// 最后一个
				nums.add(temInt);
				qn.setParts(convertNumberToStr(j));
				qn.setNum(nums);
				result.add(qn);
			} else {
				JSONObject item = (JSONObject) object;
				JSONObject item1 = (JSONObject) jsonArr.get(temInt);
				if (!item.get("type").toString().equals(item1.get("type"))) {
					// 当前部分最后一个
					nums.add(temInt);
					qn.setParts(convertNumberToStr(j));
					qn.setNum(nums);
					result.add(qn);
					// qn.setNum(nums);
					// result.add(qn);
					qn = new QuestionNum();
					nums = new ArrayList<Integer>();

					j++;
				} else {
					qn.setParts(convertNumberToStr(j));
					nums.add(temInt);
				}

			}
			i++;
		}

		return result;
	}

	/**
	 * int to upnum
	 * 
	 * @param num
	 * @description 下标重0开始
	 * @return
	 */
	public static String convertNumberToStr(int num) {
		try {
			return str[num];
		} catch (Exception e) {
			return "不详";
		}
	}

	public static Map<String, Object> fetchMusicData(ExamPaperDao examPagerDao) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Paper> paperList = null;
		try {
			int i = 0;
			int tempIndex = 0;
			paperList = new ArrayList<Paper>();
			List<QuestionNum> list = new ArrayList<QuestionNum>();
			for (ExamPaperBlock examPaperBlock : data) {
				QuestionNum qn = new QuestionNum();
				qn.setParts(SimulationUtil.convertNumberToStr(i));
				examPaperBlock = examPagerDao
						.getEpbWithQuestionsById(examPaperBlock.getId());
				List<Question> data = examPaperBlock.getQuestions();
				List<Paper> pp = questionConvertList(data, qn, tempIndex);
				tempIndex += pp.size();
				paperList.addAll(pp);
				list.add(qn);
				i++;

			}
			result.put("status", 200);
			result.put("questionItem", paperList);
			result.put("listnum", list);
		} catch (ElException e) {
			result.put("status", 400);
		}
		return result;

	}
	
	private static Paper questionToPinyin(Question question,String type){
		Paper pp = new Paper();
		pp.setAudio(false);
		pp.setId(String.valueOf(question.getId() + ""));
		String content[] = question.getContent().split(ElConstants.optSplit);
		
		String realPinyin = SimulationUtil.pinyinHtml(content[1]);
		
		pp.setTitle(content[0]+realPinyin);
		pp.setScore(String.valueOf(question.getScore() + ""));
		pp.setType("2");// 看图选题
		pp.setQuesType(type);
		pp.setOptions(question.getOptions());
		pp.setAnswer(question.getAnswer());
		return pp;
	}

	/**
	 * 阅读理解转换
	 * 
	 * @param ques
	 * @param qn
	 * @param tempIndex
	 * @return
	 */
	public static List<Paper> questionReadConvertList(List<Question> ques,
			QuestionNum qn, int tempIndex) {
		List<Paper> paperData = new ArrayList<Paper>();
		List<Integer> nums = new ArrayList<Integer>();
		int i = tempIndex;
		for (Question question : ques) {
			Paper paper = null;
			
			if(question.getQlevel() == 2){
				//带拼音数据
				paper = questionToPinyin(question, "110");
			}else{
				switch (question.getQtype()) {
				case 1:// 判断题

					break;
				case 2:// 选择题
					paper = questionToPaper(question, "100");
					break;
				case 3:// 不定项选择题
					break;
				case 4:// 多项选择题
					paper = questionToPaper(question, "104");
					break;
				case 5:// 填空题

					break;
				case 6:// 问答题

					break;
				case 7:// 材料题
					break;
				case 8:// 材料题
					break;
				case 9:// 材料题
					break;
				case 10:// 材料题
					break;
				case 11:// 材料题
					break;
				case 15:// 材料题
					break;
				case 16:// 材料题
					break;
				case 17:// 材料题
					break;
				case 18:// 材料题
					break;
				case 19:// 材料题
					break;
				case 20:// 材料题
					break;
				default:
					break;
				}
			}
			
			paperData.add(paper);
			nums.add((i + 1));
			i++;
		}
		qn.setNum(nums);
		return paperData;
	}

	/**
	 * 看图选择题型
	 */
	private static Paper questionToPaper(Question question, String type) {
		Paper pp = new Paper();
		pp.setAudio(false);
		pp.setId(String.valueOf(question.getId() + ""));
		pp.setTitle(question.getContent());
		pp.setScore(String.valueOf(question.getScore() + ""));
		pp.setType("2");// 看图选题
		pp.setQuesType(type);
		pp.setOptions(question.getOptions());
		pp.setAnswer(question.getAnswer());
		return pp;
	}

	/**
	 * 阅读模式
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> fetchReadModeData(
			ExamPaperDao examPagerDao, int index) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Paper> paperList = null;
		try {
			int i = 0;
			int tempIndex = index;
			paperList = new ArrayList<Paper>();
			List<QuestionNum> list = new ArrayList<QuestionNum>();

			for (ExamPaperBlock examPaperBlock : data) {

				if (examPaperBlock.getType() == 115) {
					// 新题型阅读题
					examPaperBlock = examPagerDao
							.getEpbWithQuestionsById(examPaperBlock.getId());
					List<Question> questions = examPaperBlock.getQuestions();
					List<String> readStr = new ArrayList<String>();
					String quesId = "";
					for (Question question : questions) {
						// 内部阅读题类型
						String inType = question.getQexplain();
						String[] tempStr = inType.split("=");// 分割
						if (tempStr.length == 2) {
							inType = tempStr[0];
						}
						Paper pp = null;
						if (inType.equals("1")) {
							question.setScore(examPaperBlock.getEachscore());
							// 阅读题多选项类型1
							pp = readConvertQuestion(question, readStr,
									tempIndex);
						} else if (inType.equals("2")) {
							// 阅读题带下划线__1__类型阅读题
							question.setScore(examPaperBlock.getEachscore());
							pp = readUnderlineQuestion(question, readStr,
									tempIndex);
						}
						
//						if(question.getQlevel() == 2){
//							//当前试题等级 等级为2则需要添加拼音
//							String str2= question.getRealSubject().replaceAll("\\s*", "").trim();
//							System.out.println(str2);
//							StringBuffer sb =  new StringBuffer();
//							String options[] = str2.split(ElConstants.optSplit);
//							for (String string : options) {
//								 String temp[] = string.split("=");
//								 String realStr = SimulationUtil.pinyinHtml(temp[0]);
//								 sb.append(realStr+"="+temp[1]);
//								 sb.append(ElConstants.optSplit);
//							}
//							question.setSubject(sb.toString());
//						}
//						

						paperList.add(pp);
						tempIndex += pp.getChildQuestion().size();
						if (questions.size() == 1) {
							quesId = String.valueOf(question.getId());
						} else {
							quesId += String.valueOf(question.getId()) + ",";
						}

					}
					QuestionNum qn = new QuestionNum();
					qn.setParts(SimulationUtil.convertNumberToStr(i));
					qn.setStrNums(readStr);
					qn.setReadId(quesId);// 当前试题Id
					list.add(qn);
					i++;
					continue;
				}

				QuestionNum qn = new QuestionNum();
				qn.setParts(SimulationUtil.convertNumberToStr(i));
				examPaperBlock = examPagerDao
						.getEpbWithQuestionsById(examPaperBlock.getId());
				List<Question> data = examPaperBlock.getQuestions();
				List<Paper> pp = questionReadConvertList(data, qn, tempIndex);
				tempIndex += pp.size();
				paperList.addAll(pp);
				list.add(qn);
				i++;

			}
			result.put("status", 200);
			result.put("questionItem", paperList);
			result.put("listnum", list);
		} catch (ElException e) {
			result.put("status", 400);
		}
		return result;

	}

	/**
	 * 阅读题转换试题对象
	 * 
	 * @param que
	 * @param readStr
	 * @param index
	 * @return
	 */
	private static Paper readUnderlineQuestion(Question que,
			List<String> readStr, int index) {
		List<Paper> childPaper = new ArrayList<Paper>();

		// 获取当前试题选项
		String[] options = que.getOptions();
		String[] qexplan = que.getQexplain().split(EQUAL);

		Paper parent = new Paper();
		parent.setId(String.valueOf(que.getId()));
		parent.setTitle(que.getTitle());
		parent.setType("2");
		parent.setScore(que.getScore() + "");
		parent.setHiddenType(qexplan[0]);
		parent.setQuesType(que.getQtype() + "");
		int tempIndex = index;

		int i = 1;
		for (String string : options) {
			String radionStr = replaceBlank(string);

			String[] answer = radionStr.split(EQUAL);
			Paper p = new Paper();
			// p.setTitle(answer[0]);
			p.setType("2");
			p.setQuesType("115");
			p.setId(que.getId() + "_" + i);
			p.setScore(parent.getScore());
			p.setSerialNum((tempIndex + i) + "");
			p.setHiddenType(qexplan[0]);
			p.setAnswer(answer[1]);
			p.setOptions(answer[0].split("\\" + OR));
			childPaper.add(p);
			i++;
		}
		String seq = (index + 1) + "-" + (tempIndex + options.length);
		readStr.add(seq);
		parent.setTitle(readToSection(seq, parent.getTitle()));
		parent.setSerialNum(seq);
		parent.setChildQuestion(childPaper);

		return parent;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	private static String readToSection(String seqNum, String content) {

		String[] number = seqNum.split("-");
		int start = Integer.parseInt(number[0]);
		int end = Integer.parseInt(number[1]);

		String input = "\\$num";
		Pattern p = Pattern.compile(input);
		StringBuffer sb = new StringBuffer();
		Matcher m = p.matcher(content);
		int i = start;
		boolean result = m.find();
		int lastEnd = 0;
		while (result) {
			m.appendReplacement(sb, "__" + i + "__");
			result = m.find();// 继续下一步匹配
			if (result) {
				lastEnd = m.end();
			}
			i++;
		}

		String lastStr = content.substring(lastEnd, content.length());
		sb.append(lastStr);
		return sb.toString();
	}

	public static void main(String[] args) {
		String content = "&nbsp;曹操得到一只大象，很想知道这只大象到底有多重。官员们都纷纷议论，发表自己的意见。有人说，$num<br />。可是怎样才能造出比大象还大的秤呢？有人说，把它砍成小块儿，然后再称。可是把大象杀了，知道重量又有什么意义呢？大家想了很多办法，可是都行不通。<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 就在这时，曹操的小儿子曹冲对父亲说：&ldquo;爸爸，我有个办法可以称大象！&rdquo; $num<br />，曹操一听，连连叫好，立刻安排人准备称象，并且让大家都过去观看。<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 大家来到河边，看见河里停着一只大船。曹冲叫人把象牵到船上，等船身稳定时，他就在船舷与水面齐平的地方，画了一条线。然后，曹冲再叫人把象牵到岸上来之后，他让人把大大小小的石头，$num<br />，船身就一点儿一点儿往下沉。等船上的那条线和水面再次平齐的时候，曹冲就叫人停止装石头。官员们都睁大了眼睛，$num<br />。他们连声称赞：&ldquo;好办法！好办法！&rdquo;这时候，谁都明白，$num<br />，把重量加起来，就知道这头大象有多重了。曹操得意地望着众人，心里想：你们还不如我的这个小儿子聪明呢！<br />A&nbsp;&nbsp; 一块儿一块儿地往船上装<br />B&nbsp;&nbsp; 制造一个巨大的秤来称<br />C&nbsp;&nbsp; 只要把船里的石头都称一下<br />D&nbsp;&nbsp; 然后他就把办法告诉了曹操<br />E&nbsp;&nbsp; 这才终于弄清了是怎么回事儿";
		String str = SimulationUtil.readToSection("7-10", content);
		System.out.println(str);
	}

	/**
	 * 阅读题转换试题对象
	 * 
	 * @param que
	 * @return
	 */
	private static Paper readConvertQuestion(Question que,
			List<String> readStr, int index) {
		List<Paper> childPaper = new ArrayList<Paper>();

		// 获取当前试题选项
		String[] options = que.getOptions();
		String[] qexplan = que.getQexplain().split(EQUAL);

		Paper parent = new Paper();
		parent.setId(String.valueOf(que.getId()));
		parent.setTitle(que.getTitle());
		
		parent.setType("2");
		parent.setScore(que.getScore() + "");
		parent.setHiddenType(qexplan[0]);
		parent.setQuesType(que.getQtype() + "");
		int tempIndex = index;

		int i = 1;
		for (String string : options) {
			String[] answer = string.split(EQUAL);
			Paper p = new Paper();
			if(que.getQlevel() == 2){
				String str2= answer[0].replaceAll("\\s*", "").trim();
				String realResult = SimulationUtil.pinyinHtml(str2);
				p.setTitle(realResult);
			}else{
				p.setTitle(answer[0]);
			}
			
			p.setType("2");
			p.setQuesType("115");
			p.setScore(parent.getScore());
			p.setId(que.getId() + "_" + i);
			p.setSerialNum((tempIndex + i) + "");
			p.setHiddenType(qexplan[0]);
			if(que.getQlevel() == 2){
				p.setAnswer(answer[1].replaceAll("\\s*", "").trim());
			}else{
				p.setAnswer(answer[1]);
			}
	
			p.setOptions(getOptions(Integer.parseInt(qexplan[1])));
			childPaper.add(p);
			i++;
		}
		String seq = (index + 1) + "-" + (tempIndex + options.length);
		readStr.add(seq);
		parent.setSerialNum(seq);
		parent.setChildQuestion(childPaper);
		return parent;

	}

	private static String[] getOptions(int offset) {
		String options[] = new String[offset];
		System.arraycopy(charter, 0, options, 0, offset);
		return options;
	}

	/**
	 * 书写模式
	 * 
	 * @param examPagerDao
	 * @param index
	 * @return
	 */
	public static Map<String, Object> fetchWriteModeData(
			ExamPaperDao examPagerDao, int index) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Paper> paperList = null;
		try {
			int i = 0;
			int tempIndex = index;
			paperList = new ArrayList<Paper>();
			List<QuestionNum> list = new ArrayList<QuestionNum>();
			for (ExamPaperBlock examPaperBlock : data) {
				QuestionNum qn = new QuestionNum();
				qn.setParts(SimulationUtil.convertNumberToStr(i));
				examPaperBlock = examPagerDao
						.getEpbWithQuestionsById(examPaperBlock.getId());
				List<Question> data = examPaperBlock.getQuestions();
				List<Paper> pp = questionWriteConvertList(data, qn, tempIndex);
				tempIndex += pp.size();
				paperList.addAll(pp);
				list.add(qn);
				i++;

			}
			result.put("status", 200);
			result.put("questionItem", paperList);
			result.put("listnum", list);
		} catch (ElException e) {
			result.put("status", 400);
		}
		return result;
	}

	/**
	 * 书写模式
	 * 
	 * @param ques
	 * @param qn
	 * @param tempIndex
	 * @return
	 */
	public static List<Paper> questionWriteConvertList(List<Question> ques,
			QuestionNum qn, int tempIndex) {
		List<Paper> paperData = new ArrayList<Paper>();
		List<Integer> nums = new ArrayList<Integer>();
		int i = tempIndex;
		for (Question question : ques) {
			Paper paper = null;
			switch (question.getQtype()) {
			case 19:
				paper = questionWriteToPaper(question, "1019");
				break;
			}
			paperData.add(paper);
			nums.add((i + 1));
			i++;
		}
		qn.setNum(nums);
		return paperData;
	}

	/**
	 * 书写模式题型
	 */
	private static Paper questionWriteToPaper(Question question, String type) {
		Paper pp = new Paper();
		pp.setAudio(false);
		pp.setId(String.valueOf(question.getId() + ""));
		pp.setTitle(question.getContent());
		pp.setScore(String.valueOf(question.getScore() + ""));
		pp.setType("3");// 书写模式
		pp.setQuesType(type);
		pp.setAnswer(question.getQexplain());
		return pp;
	}

	public static List<Paper> cacheDataToList(String json) {
		List<Paper> paperList = new ArrayList<Paper>();
		JSONArray jsArray = JSONArray.fromObject(json);
		if (jsArray.size() == 0) {
			return paperList;
		}
		for (Object object : jsArray) {
			JSONObject jsonObject = (JSONObject) object;
			Paper paper = (Paper) jsonObject.toBean(jsonObject, Paper.class);
			JSONArray jsarr = JSONArray.fromObject(jsonObject
					.get("childQuestion"));
			if (jsarr.size() > 0) {
				paper.setChildQuestion((List<Paper>) JSONArray.toCollection(
						JSONArray.fromObject(jsonObject.get("childQuestion")),
						Paper.class));
			}
			paperList.add(paper);
		}

		return paperList;
	}

	public static String dateToString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}
}
