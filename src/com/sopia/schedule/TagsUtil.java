package com.sopia.schedule;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.SystemConf;
import com.sopia.common.SystemConfOp;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.CurrentUser;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.TagsMark;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

/**
 * 自定义管理
 * 
 * @author Administrator
 * 
 */
public class TagsUtil {
	public static GsonBuilder builder = new GsonBuilder();
	public static Gson gson;
	public static final String BZGL = "BZGL";// 备注管理表
	public static final String[] array = new String[] { "addContactTagsInit",
			"addContactTagsInitZDY", "viewContactTagsInit",
			"viewContactTagsInitZDY", "updateContactTagsInit",
			"updateContactTagsInitZDY", "searchContactTags",
			"finalsearchContactTags", "dataApplicationInit",
			"dataAllocationInit", "customAuditListContactTags" };

	static {
		builder.excludeFieldsWithoutExposeAnnotation();
		gson = builder.create();
	}
	
	//验证id是否存在于array中
	public static boolean checkIn(String[] array,int id){
		boolean flag = false;
		if(array!=null){
			for(int i=0;i<array.length;i++){
				if(Integer.parseInt(array[i]) == id){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 判断actionName ==> html_actionName.shtm
	 * 
	 * @param actionName
	 * @return
	 */
	public static String formatactionName(String actionName) {
//		return "html_" + actionName + ".shtm";
		return actionName ;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			System.out.println("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			System.out.println("删除单个文件" + fileName + "失败！");
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			System.out.println("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			System.out.println("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			System.out.println("删除目录" + dir + "失败！");
			return false;
		}
	}

	/**
	 * 删除静态页
	 * 
	 * @param tablename
	 */
	public static void deleteStaticHtml(String tablename) {
		SystemConf sysconf = new SystemConf();
		sysconf.setZdy_html(SystemConfOp.getValue(ElConstants.ZDYHTML));
		String filename = J2EEFileUtil.getRealPath("/") + sysconf.getZdy_html()
				+ "/zdy/" + tablename;
		File file = new File(filename);
		if (file.exists()) {
			if (file.isFile()) { // 为文件时调用删除文件方法
				deleteFile(filename);
			} else { // 为目录时调用删除目录方法
				deleteDirectory(filename);
			}
		}
	}

	/**
	 * 如果是某些需要静态化的action，则拼凑action
	 * 
	 * @param funccode
	 * @return
	 */
	public static boolean checkFunccodeIsHTMLAction(String funccode) {
		boolean flag = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(funccode)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * List返回json
	 * 
	 * @param list
	 * @return
	 */
	public static String ToGson(List list) {
		return gson.toJson(list);
	}

	/**
	 * Object返回json
	 * 
	 * @param obj
	 * @return
	 */
	public static String ToGsonObj(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * 得到给定日期N天后的日期
	 * 
	 * @param num
	 * @return
	 */
	public static String do4(String datestr, int num) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			Date getdate = format.parse(datestr);
			long time = getdate.getTime() + (1000L * 60 * 60 * 24 * num);
			Date date = new Date();
			if (time > 0) {
				date.setTime(time);
			}
			return format.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 将前台添加页面的值放入map中
	 * 
	 * @param list_tags
	 * @param request
	 * @param hm
	 * @return
	 */
	public static Map<String, String> addToDb(List<Tags> list_tags,
			HttpServletRequest request, Map<String, String> hm) {
		for (int i = 0; i < list_tags.size(); i++) {
			if (getSearchListType(5, list_tags.get(i)) == 1) {
				TagsColumnUtil.addOneColumnValueToDb(list_tags.get(i), request,
						hm);
			}
		}
		return hm;
	}
	/**
	 * 将前台修改页面的值放入map中
	 * 
	 * @param list_tags
	 * @param request
	 * @param hm
	 * @return
	 */
	public static Map<String, String> addToDb1(List<Tags> list_tags,
			HttpServletRequest request, Map<String, String> hm) {
		for (int i = 0; i < list_tags.size(); i++) {
			if (getSearchListType(6, list_tags.get(i)) == 1) {
				TagsColumnUtil.addOneColumnValueToDb(list_tags.get(i), request,
						hm);
			}
		}
		return hm;
	}

	/**
	 * 将03/08/2012该为自定义格式 如yyyy年MM月dd日 正对自定义模块的导入
	 * 
	 * @param str
	 * @param format
	 * @param oraFormat
	 * @return
	 */
	public static String outExcel(String str, String format, String oraFormat) {
		String formatValue = "";
		if (str == null || str.equals("")) {
			formatValue = "";
		} else {
			if (format == null || format.trim().equals("")) {
				formatValue = str;
			} else {
				SimpleDateFormat sdfx = new SimpleDateFormat(oraFormat.trim());
				SimpleDateFormat sdfh = new SimpleDateFormat(format.trim());
				try {
					formatValue = sdfh.format(sdfx.parse(str));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return formatValue;
	}

	/**
	 * 列表获取一条数据
	 * 
	 * @param type
	 *            表示列表字段是否显示
	 * @param list_tags
	 * @param map
	 * @param rs
	 * @param ct2
	 * @param rs2
	 * @param ps2
	 * @return
	 * @throws ElException
	 * @throws SQLException
	 */
	public static Map<String, String> getOneData(int type,
			List<Tags> list_tags, Map<String, String> map, ResultSet rs,
			Connection ct2, ResultSet rs2, PreparedStatement ps2)
			throws ElException, SQLException {
		for (int i = 0; i < list_tags.size(); i++) {
			if (getSearchListType(type, list_tags.get(i)) == 1) {
				map = TagsColumnUtil.putToMapFromDbByDisplayType(map, list_tags
						.get(i), rs, ct2, rs2, ps2);
			}
		}
		return map;
	}
	/**
	 * 获取相关显示
	 * @param type
	 * @param list_tags
	 * @param map
	 * @param rs
	 * @param ct2
	 * @param rs2
	 * @param ps2
	 * @return
	 * @throws ElException
	 * @throws SQLException
	 */
	public static Map<String, String> getOneDataRelate(int type,
			List<Tags> list_tags, Map<String, String> map, ResultSet rs,
			Connection ct2, ResultSet rs2, PreparedStatement ps2)
			throws ElException, SQLException {
		for (int i = 0; i < list_tags.size(); i++) {
				map = TagsColumnUtil.putToMapFromDbByDisplayType(map, list_tags
						.get(i), rs, ct2, rs2, ps2);
		}
		return map;
	}

	/**
	 * 根据id获取list_tags的value,value2
	 * 
	 * @param list_tags
	 * @param map
	 * @param rs
	 * @param ct2
	 * @param rs2
	 * @param ps2
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<Tags> getOneData1(List<Tags> list_tags,
			Map<String, String> map, ResultSet rs, Connection ct2,
			ResultSet rs2, PreparedStatement ps2,int id) throws SQLException,
			IOException {
		Tags tags = null;
		for (int i = 0; i < list_tags.size(); i++) {
			if (getSearchListType(7, list_tags.get(i)) == 1) {
				tags = list_tags.get(i);
				tags = TagsColumnUtil.tagsSetValue(tags, rs, ct2, rs2, ps2,id);
			}
		}
		return list_tags;
	}

	/**
	 * 获取列表显示是我添加的还是初审终审
	 * 
	 * @param ifListNumber
	 * @return
	 */
	public static int getSearchListType(int ifListNumber, Tags tags) {
		int searchListType = 0;
		if (ifListNumber == 1) {// 我添加的
			searchListType = tags.getList_display();
		} else if (ifListNumber == 2) {// 初审
			searchListType = tags.getDepartsearch_display();
		} else if (ifListNumber == 3) {// 终审
			searchListType = tags.getDepartsearch_display();
		} else if (ifListNumber == 4) {
			searchListType = tags.getList_display();// 我负责的
		} else if (ifListNumber == 5) {
			searchListType = tags.getAdd_display();// 添加页面
		} else if (ifListNumber == 6) {
			searchListType = tags.getUpdate_display();// 修改页面
		} else if (ifListNumber == 7) {
			searchListType = tags.getView_display();// 查看页面
		}
		return searchListType;
	}

	/**
	 * 获取查询的自定义字段的列
	 * 
	 * @param ifListNumber
	 * @param list_tags
	 * @return
	 */
	public static String getSqlColumns(int ifListNumber, List<Tags> list_tags) {
		String sqlcolumn = "";

		for (int i = 0; i < list_tags.size(); i++) {
			if(ifListNumber!=-1){
				if (getSearchListType(ifListNumber, list_tags.get(i)) == 1) {
					sqlcolumn += ",";
					if (list_tags.get(i).getDisplay_type().equals("日期")) {
						sqlcolumn += " to_char("
								+ list_tags.get(i).getColumn_name()
								+ ",'yyyy-mm-dd') "
								+ list_tags.get(i).getColumn_name();
					} else {
						sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
					}
				}
			}else{//过程相关显示字段
				sqlcolumn += ",";
				if (list_tags.get(i).getDisplay_type().equals("日期")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
			}
		}
		if (sqlcolumn != null
				&& !sqlcolumn.equals("")
				&& String.valueOf(sqlcolumn.charAt(sqlcolumn.length() - 1))
						.equals(","))
			sqlcolumn = sqlcolumn.substring(0, sqlcolumn.lastIndexOf(","));

		return sqlcolumn;
	}

	/**
	 * 获取搜索条件的sql
	 * 
	 * @param hm
	 * @return
	 */
	public static String getSqlWhere(Map<String, String> hm) {
		String sqlwhere = "";
		Iterator iterator = hm.entrySet().iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			if (((String) entry.getKey()).equals("status")) {
				int kk = 0;
				if ("通过".equals((String) entry.getValue()))
					kk = 1;
				else if ("其他".equals((String) entry.getValue()))
					kk = 0;
				sqlwhere += " and shenhestatus = " + kk + " ";
			}
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			String str[] = ((String) entry.getKey()).split("==");

			if (str[0].equals("number") || str[0].equals("float")) {
				if (str[1].lastIndexOf("_") + 1 == str[1].length()) {
					sqlwhere += " and "
							+ str[1].substring(0, str[1].lastIndexOf("_"))
							+ "<=" + (String) entry.getValue() + " ";
				} else {
					sqlwhere += " and " + str[1] + ">="
							+ (String) entry.getValue() + " ";
				}
			} else if (str[0].indexOf("varchar2") > -1) {
				sqlwhere += " and " + str[1] + " like '%"
						+ (String) entry.getValue() + "%' ";
			} else if (str[0].equals("date")) {
				if (str[1].lastIndexOf("_") + 1 == str[1].length())//
				{
					sqlwhere += " and "
							+ str[1].substring(0, str[1].lastIndexOf("_"))
							+ "<= to_date('" + (String) entry.getValue()
							+ "','yyyy-mm-dd hh24:mi:ss') ";
				} else {
					sqlwhere += " and " + str[1] + ">= to_date('"
							+ (String) entry.getValue()
							+ "','yyyy-mm-dd hh24:mi:ss') ";
				}

			}
			// 相关字段
			else if (str[0].equals("relate_type")) {
				sqlwhere += " and "
						+ str[3]
						+ " is not null and t.id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from " + str[1] + " where " + str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
			}
		}
		return sqlwhere;
	}

	/**
	 * 添加前台的搜索条件
	 * 
	 * @param list_tags
	 * @param request
	 * @return
	 */
	public static Map<String, Object> addSearch(List<Tags> list_tags,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> hm = new HashMap<String, String>();

		// 组合搜索获取搜索条件
		for (int i = 0; i < list_tags.size(); i++) {
			// 相关字段
			if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
				String str_relate = (String) request.getParameter(list_tags
						.get(i).getColumn_name());
				if (str_relate != null && !str_relate.equals("")) {
					String arr[] = list_tags.get(i).getDefault_value().split(
							"==");
					hm.put("relate_type" + "==" + arr[0] + "==" + arr[1] + "=="
							+ list_tags.get(i).getColumn_name(), str_relate);
					list_tags.get(i).setValue2(str_relate);
				}
				continue;
			}
			// 相关负责人
			else if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				String str_relate = (String) request.getParameter(list_tags
						.get(i).getColumn_name());
				String str_rx = (String) request.getParameter("rx");
				if (str_relate != null && str_rx != null)
					str_relate = (String) request.getParameter(list_tags.get(i)
							.getColumn_name());
				if (str_relate != null && !str_relate.equals("")) {
					hm.put("relate_type" + "==eluser==realname=="
							+ list_tags.get(i).getColumn_name(), str_relate);
					list_tags.get(i).setValue2(str_relate);
				}
				continue;
			}
			// 日期
			else if (list_tags.get(i).getDisplay_type().equals("日期")) {
				String str2 = (String) request.getParameter(list_tags.get(i)
						.getColumn_name()
						+ "_");
				if (str2 != null && !str2.equals("")) {
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
			// 数字
			else if (list_tags.get(i).getDisplay_type().equals("实数")
					|| list_tags.get(i).getDisplay_type().equals("整数")) {
				String str2 = (String) request.getParameter(list_tags.get(i)
						.getColumn_name()
						+ "_");
				if (str2 != null && !str2.equals("")) {
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
			// 城市
			else if (list_tags.get(i).getDisplay_type().equals("城市")) {
				String str2 = (String) request.getParameter(list_tags.get(i)
						.getColumn_name());
				if (str2 != null && !str2.equals("")) {
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name(), str2);
					list_tags.get(i).setValue2(str2);
				}
			} else {
				String str = (String) request.getParameter(list_tags.get(i)
						.getColumn_name());
				if (str != null && !str.equals("")) {
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name(), str);
					list_tags.get(i).setValue(str);
				}
			}
		}
		map.put("hm", hm);
		map.put("list_tags", list_tags);
		return map;
	}

	/**
	 * 初始化更新时间进度
	 * 
	 * @param tablename
	 * @param tagsDao
	 * @throws ElException
	 */
	public static void updateTimeJindu(String tablename, TagsDao tagsDao)
			throws ElException {
		String time_jindu_column = tagsDao.IfHasTimeJindu_column(tablename);
		if (time_jindu_column != null && !time_jindu_column.equals("")
				&& time_jindu_column.indexOf(",") > 0) {
			String[] times = time_jindu_column.split(",");
			String column = time_jindu_column.split(",")[0] + ",";
			for (int i = 1; i < times.length; i++) {
				if (i == times.length - 1)
					column += tagsDao.getColumn_name_by_id(Integer
							.parseInt(times[i]));
				else
					column += tagsDao.getColumn_name_by_id(Integer
							.parseInt(times[i]))
							+ ",";
			}
			// 更新
			tagsDao.updateTimeJindu(tablename, column);
		}
	}

	/**
	 * 初始化更新业务进度
	 * 
	 * @param tablename
	 * @param tagsDao
	 * @throws ElException
	 */
	public static void updateYewuJindu(String tablename, TagsDao tagsDao,
			List<Tags> list_tags) throws ElException {
		// 有多组业务进度
		String yewu_jindu_column = tagsDao.IfHasYewuJindu_column(0, tablename);
		String[] yewu_array = null;
		String[] yewu = null;
		if (yewu_jindu_column != null && !yewu_jindu_column.equals("")) {
			yewu_array = yewu_jindu_column.split("=");
			if (yewu_array != null && yewu_array.length > 0) {
				for (int i = 0; i < yewu_array.length; i++) {
					yewu = yewu_array[i].split(",");
					String yewu_jindu = "";
					String column = "";
					for (int x = 0; x < yewu.length; x++) {// 将需要计算的字段id转换为字段名称
						column = tagsDao.getColumn_name_by_id(Integer
								.parseInt(yewu[x]));
						if (x == yewu.length - 1) {
							yewu_jindu += column;
						} else {
							yewu_jindu += column + ",";
						}
					}
					tagsDao.updateYewuJindu(tablename, yewu_jindu, list_tags);
				}
			}
		}
	}

	/**
	 * 输出字段的完整备注
	 * 
	 * @param tablename
	 * @param columnname
	 * @param mark
	 * @param tagsMark
	 * @return
	 */
	public static String getMarkHTML(String tablename, String columnname,
			String mark, TagsMark tagsMark) {

		String str = "<div id='mark_"
				+ columnname
				+ "' style='display:none;border:1px solid silver;background:ffffff;width:400px'>"
				+ "<span style='color:red'>备注:</span><br>"
				+ "<span style='color:black'>" + mark + "</span>" + "<br>";
		if (tagsMark != null && tagsMark.getRelates_info() != null
				&& !tagsMark.getRelates_info().equals("")) {
			str += "<span style='color:red'>相关备注信息:</span><br>";
			String[] infos = tagsMark.getRelates_info().split("_-_");
			String[] relates = tagsMark.getRelates().split(",");
			if (infos != null && infos.length > 0) {
				// str += "<table border='0' cellspacing='0' cellpadding='0' >";
				for (int i = 0; i < infos.length; i++) {
					if (i == 0) {
						str += "<a href='viewContactTags.action?tablename="
								+ BZGL
								+ "&id="
								+ relates[i]
								+ "'>"
								+ infos[i]
								+ "</a>"
								+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					} else {
						if (i % 3 == 0) {
							str += "<br>";
							str += "<a href='viewContactTags.action?tablename="
									+ BZGL
									+ "&id="
									+ relates[i]
									+ "'>"
									+ infos[i]
									+ "</a>"
									+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						} else {
							str += "<a href='viewContactTags.action?tablename="
									+ BZGL
									+ "&id="
									+ relates[i]
									+ "'>"
									+ infos[i]
									+ "</a>"
									+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
				}
			}
		}
		str += "</div>";
		return str;
	}

	/**
	 * 获取字段的mark
	 * 
	 * @param mark
	 * @return
	 */
	public static String getMarkHTML(String mark) {
		return mark == null ? "" : mark;
	}

	/**
	 * 获取字段完整mark
	 * 
	 * @param tablename
	 * @param columnname
	 * @param wanzheng_mark
	 * @param tagsMark
	 * @return
	 */
	public static String getWanzheng_mark(String tablename, String columnname,
			String wanzheng_mark, TagsMark tagsMark) {
		return wanzheng_mark == null ? "" : TagsUtil.getMarkHTML(tablename,
				columnname, wanzheng_mark, tagsMark);
	}

	// ///////////////////////////////////////
	/**
	 * 添加页面的表单js验证
	 */
	public static void outPutAddInfoJs(List<Tags> list_tags, JspWriter out)
			throws IOException {
		out.println("<script type='text/javascript'>");
		out.println("function doSubmit(){");
		out.println("addToProduce(iii,type);");
		for (int i = 0; i < list_tags.size(); i++) {
			// 添加的时候是否显示
			if (list_tags.get(i).getAdd_display() == 1) {
				// 必填项
				if (list_tags.get(i).getRequired() == 1) {
					if(list_tags.get(i).getDisplay_type().equals("分级下拉选项")){
						List<SelectLevel> list = list_tags.get(i).getSelectLevelList();
						int level = list_tags.get(i).getJibieshu();
						if(level>0){
							for(int m=1;m<level+1;m++){
								out.println("if(document.all."
										+ list_tags.get(i).getColumn_name() + "_"+m+".value=='')"
										+ "{" + "   alert('"
										+ list_tags.get(i).getName_display() + ""+m+"级下拉选项不能为空！！！');"
										+ "	return false;" + "}");
							}
						}
					}else{
						out.println("if(document.all."
								+ list_tags.get(i).getColumn_name() + ".value=='')"
								+ "{" + "   alert('"
								+ list_tags.get(i).getName_display() + "不能为空！！！');"
								+ "	return false;" + "}");
					}
				}
				// 整数,实数
				if (list_tags.get(i).getColumn_type().equals("number")
						|| list_tags.get(i).getColumn_type().equals("float")) {
					out.println("if(isNaN(document.all."
							+ list_tags.get(i).getColumn_name()
							+ ".value))"
							+ // true:非数字
							"{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ "只能为数字！！！');" + "	return false;" + "}");
				}
				// 图片
				if (list_tags.get(i).getDisplay_type().equals("图片")) {
					out.println("if(isNaN(document.all."
							+ list_tags.get(i).getColumn_name()
							+ "_h.value))"
							+ // true:非数字
							"{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ ",高只能为数字！！！');" + "	return false;" + "}");
					out.println("if(isNaN(document.all."
							+ list_tags.get(i).getColumn_name()
							+ "_w.value))"
							+ // true:非数字
							"{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ ",宽只能为数字！！！');" + "	return false;" + "}");
				}
				// 附件
				if (list_tags.get(i).getDisplay_type().equals("附件上传")) {
					out.println("if(document.all."
							+ list_tags.get(i).getColumn_name() + ".value!=''"
							+ "		&&document.all."
							+ list_tags.get(i).getColumn_name()
							+ "_.value=='')" + "{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ "，路径不能为空！！！');" + "	return false;" + "}");
				}
				// 音频
				if (list_tags.get(i).getDisplay_type().equals("音频")) {
					out.println("if(document.all."
							+ list_tags.get(i).getColumn_name() + ".value!=''"
							+ "		&&document.all."
							+ list_tags.get(i).getColumn_name()
							+ "_.value=='')" + "{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ "，路径不能为空！！！');" + "	return false;" + "}");
				}
			}
		}
		out.println("}");
		out.println("</script>");
	}

	/**
	 * 修改页面表单js验证
	 * 
	 * @param list_tags
	 * @param out
	 * @throws IOException
	 */
	public static void outPutUpdateJs(List<Tags> list_tags, JspWriter out)
			throws IOException {
		out.println("<script type='text/javascript'>");
		out.println("function doSubmit(){");
		out.println("addToProduce();");
		out.println("addToProduce_();");

		for (int i = 0; i < list_tags.size(); i++) {
			// 添加的时候是否显示
			if (list_tags.get(i).getAdd_display() == 1) {
				// 必填项
				if (list_tags.get(i).getRequired() == 1) {
					if(list_tags.get(i).getDisplay_type().equals("分级下拉选项")){
//						List<SelectLevel> list = list_tags.get(i).getSelectLevelList();
						int level = list_tags.get(i).getJibieshu();
						if(level > 0){
							for(int m=1;m<level+1;m++){
								out.println("if(document.all."
										+ list_tags.get(i).getColumn_name() + "_"+m+".value=='')"
										+ "{" + "   alert('"
										+ list_tags.get(i).getName_display() + ""+m+"级下拉选项不能为空！！！');"
										+ "	return false;" + "}");
							}
						}
					}else{
						out.println("if(document.all."
								+ list_tags.get(i).getColumn_name() + ".value=='')"
								+ "{" + "   alert('"
								+ list_tags.get(i).getName_display() + "不能为空！！！');"
								+ "	return false;" + "}");
					}
				}
				// 整数,实数
				if (list_tags.get(i).getColumn_type().equals("number")
						|| list_tags.get(i).getColumn_type().equals("float")) {
					out.println("if(isNaN(document.all."
							+ list_tags.get(i).getColumn_name()
							+ ".value))"
							+ // true:非数字
							"{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ "只能为数字！！！');" + "	return false;" + "}");
				}
				// 图片
				if (list_tags.get(i).getDisplay_type().equals("图片")) {
					out.println("if(isNaN(document.all."
							+ list_tags.get(i).getColumn_name()
							+ "_h.value))"
							+ // true:非数字
							"{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ ",高只能为数字！！！');" + "	return false;" + "}");
					out.println("if(isNaN(document.all."
							+ list_tags.get(i).getColumn_name()
							+ "_w.value))"
							+ // true:非数字
							"{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ ",宽只能为数字！！！');" + "	return false;" + "}");
				}
				// 附件
				if (list_tags.get(i).getDisplay_type().equals("附件上传")) {
					out.println("if(document.all."
							+ list_tags.get(i).getColumn_name() + ".value!=''"
							+ "		&&document.all."
							+ list_tags.get(i).getColumn_name()
							+ "_.value=='')" + "{" + "   alert('"
							+ list_tags.get(i).getName_display()
							+ "，路径不能为空！！！');" + "	return false;" + "}");
				}

			}

		}
		out.println("}");
		out.println("</script>");
	}

	/**
	 * 输出myload方法的js，并且返回tags所在list的序号i
	 */
	public static int outPutMyloadJs(List<Tags> list_tags, JspWriter out)
			throws IOException {
		int j = -1;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("富文本")) {
				out.println("<script type='text/javascript'>"
						+ "	function myload(){"
						+ "	var oFCKeditor = new FCKeditor('content') ; "
						+ "	oFCKeditor.BasePath = 'editor/' ;"
						+ "	oFCKeditor.Height = 400;"
						+ "	oFCKeditor.Width = '100%';"
						+ "	oFCKeditor.ReplaceTextarea();" 
						+ " alertMessage();" +
								"} ");
				out.println(" </script> ");
				j = i;
			} else {
				if (i == list_tags.size() - 1) {
					out.println("<script type='text/javascript'>"
							+ "	function myload(){" + "alertMessage();	} ");
					out.println(" </script> ");
				}
			}
		}
		return j;
	}

	/**
	 * 输出用户姓名和部门
	 * 
	 * @param currentUser
	 * @param out
	 * @param type
	 *            1 add 2 update 3 view
	 * @throws IOException
	 */
	public static void outPutCurrentUser(CurrentUser currentUser,
			JspWriter out, int type) throws IOException {
		if (currentUser != null) {
			if (type == 1) {
				if (currentUser.getUser_add() == 1) {
					// 用户和部门
					out.println("<tr>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>用户姓名:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getName());
					out.println("</td>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>部门:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getDepname());
					out.println("</td>");
					out.println("</tr>");
					// 职务和地市
					out.println("<tr>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>职务:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getZhiwuname());
					out.println("</td>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>地市:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getDishiname());
					out.println("</td>");
					out.println("</tr>");
				}
			} else if (type == 2) {
				if (currentUser.getUser_update() == 1) {
					// 用户和部门
					out.println("<tr>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>用户姓名:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getName());
					out.println("</td>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>部门:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getDepname());
					out.println("</td>");
					out.println("</tr>");
					// 职务和地市
					out.println("<tr>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>职务:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getZhiwuname());
					out.println("</td>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>地市:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getDishiname());
					out.println("</td>");
					out.println("</tr>");
				}
			} else {
				if (currentUser.getUser_view() == 1) {
					// 用户和部门
					out.println("<tr>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>用户姓名:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getName());
					out.println("</td>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>部门:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getDepname());
					out.println("</td>");
					out.println("</tr>");
					// 职务和地市
					out.println("<tr>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>职务:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getZhiwuname());
					out.println("</td>");
					out
							.println("<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>地市:");
					out.println("</td>");
					out.println("<td style='padding-left:10px;color:#0099CC'>"
							+ currentUser.getDishiname());
					out.println("</td>");
					out.println("</tr>");
				}
			}
		}
	}

	/**
	 * 输出添加页面的HTML
	 * 
	 * @param list_tags
	 * @param out
	 * @param control_tr
	 * @param control_size
	 * @param currentUser
	 * @param nowdate
	 * @param list_ricktext
	 * @param kk
	 * @param username
	 * @param uid
	 * @throws IOException
	 */
	public static void outPutAddInfoHTML(List<Tags> list_tags, JspWriter out,
			int control_tr, int control_size, CurrentUser currentUser,
			String nowdate, List<Integer> list_ricktext, String kk,
			String username, String uid) throws IOException {

		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getAdd_display() == 1)// display 是否显示
			{
				control_size--;
				if (control_tr == 0) {
					out.println("<tr>");
				}

				String mark = "";// 简单mark
				// 将mark放在字段下方,未做
				mark = TagsUtil.getMarkHTML(list_tags.get(i).getMark());

				String MarkDiv = "";
				MarkDiv = TagsUtil.getWanzheng_mark(list_tags.get(i)
						.getTable_name(), list_tags.get(i).getColumn_name(),
						list_tags.get(i).getWanzheng_mark(), list_tags.get(i)
								.getTagsMark());
				out.println(MarkDiv);

				if (list_tags.get(i).getDisplay_type().equals("文本")) {
					String textvalue = "";
					String textwidth = " style='width:300px;' ";

					if (list_tags.get(i).getDefault_value() != null) {
						String textcontrol[] = list_tags.get(i)
								.getDefault_value().split("==");// defaultvalue==width

						if (!list_tags.get(i).getDefault_value().equals("")) {
							if (!textcontrol[0].equals(""))
								textvalue = " value='" + textcontrol[0] + "' ";
							if (textcontrol.length > 1)
								textwidth = " style='width:" + textcontrol[1]
										+ "%;' ";
						}
					}
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getRequired() == 1) {
						if (list_tags.get(i).getWritible() == 1) {
							out
									.println("<input "
											+ textvalue
											+ " type='text' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' name='"
											+ list_tags.get(i).getColumn_name()
											+ "'   "
											+ textwidth
											+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" /><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input "
											+ textvalue
											+ " type='text' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' readOnly name='"
											+ list_tags.get(i).getColumn_name()
											+ "'   "
											+ textwidth
											+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
											+ mark
											+ "</span><span style='color:red'>不可填写</span>");
						}
					} else {
						if (list_tags.get(i).getWritible() == 1) {
							out
									.println("<input "
											+ textvalue
											+ " type='text' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' name='"
											+ list_tags.get(i).getColumn_name()
											+ "'   "
											+ textwidth
											+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" /><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input "
											+ textvalue
											+ " type='text' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' readOnly name='"
											+ list_tags.get(i).getColumn_name()
											+ "'   "
											+ textwidth
											+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
											+ mark
											+ "</span><span style='color:red'>不可填写</span>");
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("实数")
						|| list_tags.get(i).getDisplay_type().equals("整数")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getBiaojianqiuhe_check() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							if (list_tags.get(i).getWritible() == 1) {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input readOnly type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark
												+ "</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
							}
						} else {
							if (list_tags.get(i).getWritible() == 1) {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input readOnly type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark
												+ "</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
							}
						}
					} else {
						if (list_tags.get(i).getRequired() == 1) {
							if (list_tags.get(i).getWritible() == 1) {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input readOnly type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark
												+ "</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
							}
						} else {
							if (list_tags.get(i).getWritible() == 1) {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input readOnly type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark
												+ "</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
							}
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("百分比")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;' >");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getTime_jindu_ids() != null
							&& !list_tags.get(i).getTime_jindu_ids().equals("")) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input readOnly id='value_jindutiao__"
											+ list_tags.get(i).getColumn_name()
											+ "' type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' style='width:300px;' onKeyUp='getJindutiao("
											+ list_tags.get(i).getJindutiao()
											+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
											+ list_tags.get(i).getColumn_name()
											+ "'>%</span><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input readOnly id='value_jindutiao__"
											+ list_tags.get(i).getColumn_name()
											+ "' type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' style='width:300px;' onKeyUp='getJindutiao("
											+ list_tags.get(i).getJindutiao()
											+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
											+ list_tags.get(i).getColumn_name()
											+ "'>%</span><span>"
											+ mark
											+ "</span>");
						}

					} else if (list_tags.get(i).getYewu_jindu_ids() != null
							&& !list_tags.get(i).getYewu_jindu_ids().equals("")) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input readOnly id='value_jindutiao__"
											+ list_tags.get(i).getColumn_name()
											+ "' type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
											+ list_tags.get(i).getColumn_name()
											+ "'>%</span><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input readOnly id='value_jindutiao__"
											+ list_tags.get(i).getColumn_name()
											+ "' type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
											+ list_tags.get(i).getColumn_name()
											+ "'>%</span><span>"
											+ mark
											+ "</span>");
						}
					} else {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input id='value_jindutiao__"
											+ list_tags.get(i).getColumn_name()
											+ "' type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' style='width:300px;' onKeyUp='getJindutiao("
											+ list_tags.get(i).getJindutiao()
											+ ",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
											+ list_tags.get(i).getColumn_name()
											+ "'>%</span><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input id='value_jindutiao__"
											+ list_tags.get(i).getColumn_name()
											+ "' type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' style='width:300px;' onKeyUp='getJindutiao("
											+ list_tags.get(i).getJindutiao()
											+ ",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
											+ list_tags.get(i).getColumn_name()
											+ "'>%</span><span>" + mark
											+ "</span>");
						}
					}

					if (list_tags.get(i).getJindutiao() == 1) {// 显示进度条
						out
								.println("<div id='jindutiao_div__"
										+ list_tags.get(i).getColumn_name()
										+ "' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='0%'  id='show_jindutiao__"
										+ list_tags.get(i).getColumn_name()
										+ "' /></div>");
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("日期")) {

					String value = "";
					if (list_tags.get(i).getDefault_value() != null) {
						if (list_tags.get(i).getDefault_value().contains("_"))
							value = "value='"
									+ do4(nowdate, Integer.parseInt(list_tags
											.get(i).getDefault_value().split(
													"_")[1])) + "'";
						else
							value = "value='" + do4(nowdate, 0) + "'";
					}

					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' "
											+ " onClick='setday(this)' id='"
											+ list_tags.get(i).getColumn_name()
											+ "'   style='width:120px;' "
											+ value
											+ " /><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' "
											+ " onClick='setday(this)' id='"
											+ list_tags.get(i).getColumn_name()
											+ "'   style='width:120px;' "
											+ value
											+ " /><span>"
											+ mark
											+ "</span>");
						}
					} else {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' "
											+ "  id='"
											+ list_tags.get(i).getColumn_name()
											+ "'   style='width:120px;' "
											+ value
											+ " /><span style='color:red'>*</span><span>"
											+ mark
											+ "</span><span style='color:red'>不可填写</span>");
						} else {

							out
									.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' "
											+ "  id='"
											+ list_tags.get(i).getColumn_name()
											+ "'   style='width:120px;' "
											+ value
											+ " /><span>"
											+ mark
											+ "</span><span style='color:red'>不可填写</span>");
						}
					}

					out.println("</td>");
					control_tr++;
				}

				else if (list_tags.get(i).getDisplay_type().equals("下拉选项")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str[] = list_tags.get(i).getDefault_value().split(
							"==");
					String str_select_head = "";

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							str_select_head = "<select id='"
									+ list_tags.get(i).getColumn_name()
									+ "' name='"
									+ list_tags.get(i).getColumn_name()
									+ "' ><span style='color:red'>*</span>";
						} else {
							str_select_head = "<select id='"
									+ list_tags.get(i).getColumn_name()
									+ "' name='"
									+ list_tags.get(i).getColumn_name() + "' >";
						}
					} else {
						if (list_tags.get(i).getRequired() == 1) {
							str_select_head = "<select id='"
									+ list_tags.get(i).getColumn_name()
									+ "' disabled name='"
									+ list_tags.get(i).getColumn_name()
									+ "' ><span style='color:red'>*</span><span style='color:red'>不可选择</span>";
						} else {
							str_select_head = "<select id='"
									+ list_tags.get(i).getColumn_name()
									+ "' disabled name='"
									+ list_tags.get(i).getColumn_name()
									+ "' ><span style='color:red'>不可选择</span>";
						}
					}

					String str_default = "<option value=''>请选择</option>";
					String str_select_tail = "</select>";
					String str_select_body = "";
					if (str.length > 0) {
						for (int j = 0; j < str.length; j++) {

							str_select_body += "<option value='" + str[j]
									+ "'>" + str[j] + "</option>";
						}
					}

					out.println(str_select_head + str_default + str_select_body
							+ str_select_tail + "&nbsp;&nbsp;<span>" + mark
							+ "</span>");

					out.println("</td>");
					control_tr++;
				}
				else if (list_tags.get(i).getDisplay_type().equals("分级下拉选项")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  colspan=3>");
					
					
					//输出分级下拉菜单的html
					int level = list_tags.get(i).getJibieshu();
					String select_div_begin = "<div id='"+list_tags.get(i).getColumn_name()+"__'>";
					String select_value = "<input type='hidden' name='"+list_tags.get(i).getColumn_name()+"' id='"+list_tags.get(i).getColumn_name()+"' />";
					String select_div_end = "</div>";
					String str_select_head = "";
					
					String disabled = "";
					String required = "";
					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							required = "<span style='color:red'>*</span>";
							str_select_head = "<select onchange='javascript:change_selectlevel(this,1,"+level+");' name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1'>";
						} else {
							str_select_head = "<select onchange='javascript:change_selectlevel(this,1,"+level+");' name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1'>";
						}
					} else {
						disabled = "disabled";
						if (list_tags.get(i).getRequired() == 1) {
							required = "<span style='color:red'>*</span>"+"<span style='color:red'>不可选择</span>";
							str_select_head = "<select "+disabled+" onchange='javascript:change_selectlevel(this,1,"+level+");' name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1'>";
						} else {
							str_select_head = "<select "+disabled+" onchange='javascript:change_selectlevel(this,1,"+level+");' name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1'>"+"<span style='color:red'>不可选择</span>";
						}
					}
					
					String select_end = "</select>";
					String select_body = "<option value='0'>请选择</option>";
					String html = "";
					SelectLevel selectLevel = list_tags.get(i).getSelectLevel();
					int jibieshu = list_tags.get(i).getJibieshu();
					if(selectLevel!=null){
						List<SelectLevel> childs = list_tags.get(i).getSelectLevelList();
						if(childs!=null&&childs.size()>0){
							for(int m=0;m<childs.size();m++){
								select_body += "<option value='"+childs.get(m).getId()+"'>"+childs.get(m).getName()+"</option>";
							}
						}
					}
					String otherhtml =  "";
					if(jibieshu>=2){
						for(int m=2;m<jibieshu+2-1;m++){
							otherhtml += "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_"+m+"' id='"+list_tags.get(i).getColumn_name()+"_"+m+"' onchange='javascript:change_selectlevel(this,"+m+","+level+");'><option value='0'>请选择</option></select>";
						}
					}
					
					
					
					html = select_div_begin + select_value + str_select_head + select_body + select_end + otherhtml + required +  select_div_end;
					System.out.println(html);
					
					
					out.println(html);
					out.println("</td>");
					control_tr = 2;
				} 
				else if (list_tags.get(i).getDisplay_type().equals("单选")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str[] = null;
					if (list_tags.get(i).getDefault_value() != null
							&& !list_tags.get(i).getDefault_value().equals("")) {
						str = list_tags.get(i).getDefault_value().split("==");
						String radio_body = "";
						if (str.length > 0) {
							for (int j = 0; j < str.length; j++) {
								if (list_tags.get(i).getWritible() == 1) {
									radio_body += "<input type='radio' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='" + str[j] + "'>"
											+ str[j];
								} else {
									radio_body += "<input disabled type='radio' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='"
											+ str[j]
											+ "'>"
											+ str[j];
								}

							}
						}
						if (list_tags.get(i).getRequired() == 1) {
							out.println(radio_body
									+ "<span style='color:red'>*</span><span>"
									+ mark + "</span>");
						} else {
							out.println(radio_body + "<span>" + mark
									+ "</span>");
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("复选")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str[] = null;
					if (list_tags.get(i).getDefault_value() != null
							&& !list_tags.get(i).getDefault_value().equals("")) {
						str = list_tags.get(i).getDefault_value().split("==");
						String checkbox_body = "";
						if (str.length > 0) {
							for (int j = 0; j < str.length; j++) {
								if (list_tags.get(i).getWritible() == 1) {
									checkbox_body += "<input type='checkbox' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='"
											+ str[j]
											+ "'>"
											+ str[j];
								} else {
									checkbox_body += "<input disabled type='checkbox' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='"
											+ str[j]
											+ "'>"
											+ str[j];
								}

							}
						}
						//						
						if (list_tags.get(i).getRequired() == 1) {
							out.println(checkbox_body
									+ "<span style='color:red'>*</span><span>"
									+ mark + "</span>");
						} else {
							out.println(checkbox_body + "<span>" + mark
									+ "</span>");
						}
					}
					out.println("</td>");
					control_tr++;

				} else if (list_tags.get(i).getDisplay_type().equals("城市")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					String province = "";
					String city = "";
					String county = "";
					if (list_tags.get(i).getDefault_value() != null
							&& !list_tags.get(i).getDefault_value().equals("")) {
						String textcontrol[] = list_tags.get(i)
								.getDefault_value().split(" ");// defaultvalue==width
						province = textcontrol[0];
						city = textcontrol[1];
						county = textcontrol[2];

					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					out.println("<input type='hidden' id='"
							+ list_tags.get(i).getColumn_name() + "' name='"
							+ list_tags.get(i).getColumn_name() + "' >");

					String output = "";
					String output_not_required = "";
					String output_disabled = "";
					if (province != "" && city != "" && county != "") {
						output = "" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_province'  name='"
								+ list_tags.get(i).getColumn_name()
								+ "_province' onchange=\"changeProvince('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_province' >  " + province
								+ "</option>" + "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' onchange=\"changeCity('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\" >  "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_city'  >  " + city + "</option>"
								+ "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' onchange=\"changeCounty('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_county' >" + county + "</option>"
								+ "</select>"
								+ "<span style='color:red'>*</span><span>"
								+ mark + "</span>";

						output_not_required = "" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_province'  name='"
								+ list_tags.get(i).getColumn_name()
								+ "_province' onchange=\"changeProvince('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_province' >  " + province
								+ "</option>" + "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' onchange=\"changeCity('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\" >  "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_city'  >  " + city + "</option>"
								+ "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' onchange=\"changeCounty('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_county' >" + county + "</option>"
								+ "</select>" + "<span>" + mark + "</span>";

						output_disabled = "" + "<select disabled id='"
								+ list_tags.get(i).getColumn_name()
								+ "_province'  name='"
								+ list_tags.get(i).getColumn_name()
								+ "_province' onchange=\"changeProvince('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_province' >  " + province
								+ "</option>" + "</select>"
								+ "<select disabled id='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' onchange=\"changeCity('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\" >  "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_city'  >  " + city + "</option>"
								+ "</select>" + "<select disabled id='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' onchange=\"changeCounty('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_county' >" + county + "</option>"
								+ "</select>" + "<span>" + mark
								+ "</span><span style='color:red'>不可填写</span>";
					} else {
						output = "" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_province'  name='"
								+ list_tags.get(i).getColumn_name()
								+ "_province' onchange=\"changeProvince('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_province' >  " + "请选择省"
								+ "</option>" + "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' onchange=\"changeCity('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\" >  "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_city'  >  " + "请选择市"
								+ "</option>" + "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' onchange=\"changeCounty('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_county' >" + "请选择县" + "</option>"
								+ "</select>"
								+ "<span style='color:red'>*</span><span>"
								+ mark + "</span>";

						output_not_required = "" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_province'  name='"
								+ list_tags.get(i).getColumn_name()
								+ "_province' onchange=\"changeProvince('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_province' >  " + "请选择省"
								+ "</option>" + "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' onchange=\"changeCity('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\" >  "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_city'  >  " + "请选择市"
								+ "</option>" + "</select>" + "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' onchange=\"changeCounty('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_county' >" + "请选择县" + "</option>"
								+ "</select>" + "<span>" + mark + "</span>";

						output_disabled = "" + "<select disabled id='"
								+ list_tags.get(i).getColumn_name()
								+ "_province'  name='"
								+ list_tags.get(i).getColumn_name()
								+ "_province' onchange=\"changeProvince('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_province' >  " + "请选择省"
								+ "</option>" + "</select>"
								+ "<select disabled id='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_city' onchange=\"changeCity('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\" >  "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_city'  >  " + "请选择市"
								+ "</option>" + "</select>"
								+ "<select disabled id='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' name='"
								+ list_tags.get(i).getColumn_name()
								+ "_county' onchange=\"changeCounty('"
								+ list_tags.get(i).getColumn_name()
								+ "');\" style=\"width:100\"> "
								+ "<option id='"
								+ list_tags.get(i).getColumn_name()
								+ "_option_in_county' >" + "请选择县" + "</option>"
								+ "</select>" + "<span>" + mark
								+ "</span><span style='color:red'>不可填写</span>";
					}

					if (list_tags.get(i).getRequired() == 1) {
						if (list_tags.get(i).getWritible() == 1) {
							out.println(output);
						} else {
							out.println(output_disabled);
						}
					} else {
						if (list_tags.get(i).getWritible() == 1) {
							out.println(output_not_required);
						} else {
							out.println(output_disabled);
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("大文本")) {

					if (control_tr == 1) {
						out.println("<td colspan=2></td>");
						out.println("</tr>");
						out.println("<tr>");
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td colspan='3'>");

					if (list_tags.get(i).getWritible() == 1) {
						out
								.println("<textarea cols='100%' id='"
										+ list_tags.get(i).getColumn_name()
										+ "' rows='10' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"></textarea>");
					} else {
						out
								.println("<textarea readOnly id='"
										+ list_tags.get(i).getColumn_name()
										+ "' cols='100%' rows='10' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"></textarea><span style='color:red'>不可填写</span>");
					}
					out.println("</td>");
					control_tr = 2;
				} else if (list_tags.get(i).getDisplay_type().equals("附件上传")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					out
							.println("<script type='text/javascript'>	"
									+ " function addStuff_"
									+ list_tags.get(i).getId()
									+ "() {	 "
									+ " 	width=1060;	"
									+ " 	height=500;	"
									+ "  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ "  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	"
									+

									" 	if(null==rv){	"
									+ " 	 	alert('您没选择东西！'); 	"
									+ " 	 	return ;		" + " 	 } 					"
									+ " 	 document.getElementById('"
									+ list_tags.get(i).getColumn_name()
									+ "_').value=rv; 		" + " 	 } "
									+ " </script> ");

					if (list_tags.get(i).getWritible() == 1) {
						out
								.println("<input type='text' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' id='"
										+ list_tags.get(i).getColumn_name()
										+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr
						out
								.println("<input type='text' name='"
										+ list_tags.get(i).getColumn_name()
										+ "_' id='"
										+ list_tags.get(i).getColumn_name()
										+ "_'  readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr
						out.println("<a  onClick='addStuff_"
								+ list_tags.get(i).getId() + "()'>浏览资源库</a>");
					} else {
						out
								.println("<input readOnly type='text' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' id='"
										+ list_tags.get(i).getColumn_name()
										+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr
						out
								.println("<input readOnly type='text' name='"
										+ list_tags.get(i).getColumn_name()
										+ "_' id='"
										+ list_tags.get(i).getColumn_name()
										+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>不可填写</span>");// addr
					}

					out.println("</td>");
					control_tr++;
				} 
				//-------------------音频-------------------------
				 else if (list_tags.get(i).getDisplay_type().equals("音频")) {
						int k = i + 1;
						for (int l = i + 1; l < list_tags.size(); l++) {
							if (list_tags.get(l).getAdd_display() == 1) {
								break;
							} else
								k++;
						}

						out
								.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if (!list_tags.get(i).getDisplay_type().equals("富文本"))
							out
									.println("<span id='_mark_"
											+ list_tags.get(i).getColumn_name()
											+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
											+ list_tags.get(i).getName_display()
											+ "</span>" + ":");
						out.println("</td>");
						out.println("<td  >");

						out
								.println("<script type='text/javascript'>	"
										+ " function addStuff_"
										+ list_tags.get(i).getId()
										+ "() {	 "
										+ " 	width=1060;	"
										+ " 	height=500;	"
										+ "  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
										+ "  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	"
										+

										" 	if(null==rv){	"
										+ " 	 	alert('您没选择东西！'); 	"
										+ " 	 	return ;		" + " 	 } 					"
										+ " 	 document.getElementById('"
										+ list_tags.get(i).getColumn_name()
										+ "_').value=rv; 		" + " 	 } "
										+ " </script> ");

						if (list_tags.get(i).getWritible() == 1) {
							out
									.println("<input type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>"
											+"<input  type=\"button\" class=\"bottom\" onClick=\"ready('')\" />");//ADDR
							out
									.println("<input type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "_' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_'  readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr
							out.println("<a  onClick='addStuff_"
									+ list_tags.get(i).getId() + "()'>浏览资源库</a>");
						} else {
							out
									.println("<input readOnly type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr
							out
									.println("<input readOnly type='text' name='"
											+ list_tags.get(i).getColumn_name()
											+ "_' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>不可填写</span>");// addr
						}

						out.println("</td>");
						control_tr++;
					} 
				//-----------------------音频结束-------------------------------------------------
				else if (list_tags.get(i).getDisplay_type().equals("图片")) {

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td >");

					out
							.println("<script type='text/javascript'>	"
									+ " function addStuff_"
									+ list_tags.get(i).getId()
									+ "() {	 "
									+ " 	width=600;	"
									+ " 	height=400;	"
									+ "  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ "  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	"
									+

									" 	if(null==rv){	"
									+ " 	 	alert('您没选择东西！'); 	"
									+ " 	 	return ;		"
									+ " 	 } 					"
									+ "	 var pos = '.' + rv.replace(/.+\\./, ''); "
									+ "   	"
									+ "	if(!(pos=='.jpg'||pos=='.png'||pos=='.bmp'))	"
									+ "	{	"
									+ "		alert('只能上传.jpg,.png,.bmp格式的图片');"
									+ "		return ;"
									+ "	}"
									+ " 	 document.getElementById('"
									+ list_tags.get(i).getColumn_name()
									+ "').value=rv; 		"
									+ " 	 } "
									+ " </script> ");

					if (list_tags.get(i).getWritible() == 1) {
						out
								.println("高<input type='text' id='"
										+ list_tags.get(i).getColumn_name()
										+ "_h'  name='"
										+ list_tags.get(i).getColumn_name()
										+ "_h'  size='5' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// height
						out
								.println("宽<input type='text' id='"
										+ list_tags.get(i).getColumn_name()
										+ "_w' name='"
										+ list_tags.get(i).getColumn_name()
										+ "_w'  size='5'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// width
						out
								.println("<input type='text' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' id='"
										+ list_tags.get(i).getColumn_name()
										+ "'  readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr
						out.println("<a  onClick='addStuff_"
								+ list_tags.get(i).getId() + "()'>浏览资源库</a>");
					} else {
						out
								.println("高<input readOnly type='text' id='"
										+ list_tags.get(i).getColumn_name()
										+ "_h' name='"
										+ list_tags.get(i).getColumn_name()
										+ "_h'  size='5' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// height
						out
								.println("宽<input readOnly type='text' id='"
										+ list_tags.get(i).getColumn_name()
										+ "_w' name='"
										+ list_tags.get(i).getColumn_name()
										+ "_w'  size='5'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// width
						out
								.println("<input readOnly type='text' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' id='"
										+ list_tags.get(i).getColumn_name()
										+ "'  readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>不可填写</span>");// addr
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("相关字段")) {

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td >");

					String tablename[] = list_tags.get(i).getDefault_value()
							.split("==");// tb_clientlinkman_tags==tb_clientlinkman_tags_35==联系主题==varchar2(500)

					out
							.println("<script type='text/javascript'>"
									+ " function add_"
									+ list_tags.get(i).getId()
									+ "() "
									+ " {			"
									+ " 	width=screen.availWidth * 0.8;	"
									+ " 	height=screen.availHeight * 0.8;	"
									+ "   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ " 	  	var rv = window.showModalDialog('relateColumn.action?tablename="
									+ tablename[0]
									+ "&columnname="
									+ tablename[1]
									+ "&columnName="
									+ list_tags.get(i).getColumn_name()
									+ ""
									+ "&control=0&is_judge="
									+ list_tags.get(i).getIs_judge()
									+ "&rn='+Math.random(),null,sFeature);	"
									+ "var display='';"
									+ "var returnvalue='';"
									+ "var str;"
									+ "if(rv!=null && rv != '')"
									+ "{"
									+ "	str = String(rv).split('_--_');"
									+ "}"
									+ "if(str!=null&&str.length>0)"
									+ "{"
									+ "	for(i=0;i<str.length;i++)"
									+ "	{"
									+ "		var tmp =str[i].split('_-_');"
									+ "		if(tmp[1] == 'null'){alert('为空,请重新选择!!!');return;}"
									+ "		display += tmp[1] ;"
									+ "		returnvalue+=tmp[0];"
									+ "		if(i+1!=str.length) "
									+ "		{"
									+ "			display +=',' ;"
									+ "			returnvalue+='__-__'"
									+ "		}"
									+ "	}"
									+ "}"
									+ ""
									+ ""
									+ "document.getElementById('relate_"
									+ list_tags.get(i).getId()
									+ "_').value=display;"
									+ "document.getElementById('relate_"
									+ list_tags.get(i).getId()
									+ "').value=returnvalue; ");
									if (kk != null && !kk.equals("")) {
										if (kk.equals(list_tags.get(i).getColumn_name()))
											out.print("addRelate(returnvalue);");
									}
					out.print("}</script>");

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input style='width:300px;' type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
											+ mark + "</span>");
							out.println("<input type='hidden' name='relate_"
									+ list_tags.get(i).getId()
									+ "' id='relate_"
									+ list_tags.get(i).getId() + "'  />");
							out
									.println("<img src='images/choose.gif' style='cursor:hand' title='选择相关' onclick='add_"
											+ list_tags.get(i).getId()
											+ "();' />");
							// out.print("<span style='color:red;cursor: hand'
							// onclick='add_"+list_tags.get(i).getId()+"();'>点此添加</span>");
						} else {
							out
									.println("<input style='width:300px;' type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
											+ mark + "</span>");
							out.println("<input type='hidden' name='relate_"
									+ list_tags.get(i).getId()
									+ "' id='relate_"
									+ list_tags.get(i).getId() + "'  />");
							out
									.println("<img src='images/choose.gif' style='cursor:hand' title='选择相关' onclick='add_"
											+ list_tags.get(i).getId()
											+ "();' />");
							// out.print("<span style='color:red;cursor: hand'
							// onclick='add_"+list_tags.get(i).getId()+"();'>点此添加</span>");
						}
					} else {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input style='width:300px;' type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
											+ mark + "</span>");
							out.println("<input type='hidden' name='relate_"
									+ list_tags.get(i).getId()
									+ "' id='relate_"
									+ list_tags.get(i).getId() + "'  />");
							out
									.println("<img src='images/choose.gif' style='cursor:hand' title='选择相关' onclick='add_"
											+ list_tags.get(i).getId()
											+ "();' />");
							// out.print("<span style='color:red;cursor: hand'
							// onclick='add_"+list_tags.get(i).getId()+"();'>点此添加</span>");
						} else {
							out
									.println("<input style='width:300px;' type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
											+ mark + "</span>");
							out.println("<input type='hidden' name='relate_"
									+ list_tags.get(i).getId()
									+ "' id='relate_"
									+ list_tags.get(i).getId() + "'  />");
							out
									.println("<img src='images/choose.gif' style='cursor:hand' title='选择相关' onclick='add_"
											+ list_tags.get(i).getId()
											+ "();' />");
							// out.print("<span style='color:red;cursor: hand'
							// onclick='add_"+list_tags.get(i).getId()+"();'>点此添加</span>");
						}
					}

					out.println("</td>");
					control_tr++;

				} else if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td >");

					out
							.println("<script type='text/javascript'>"
									+ " function add_"
									+ list_tags.get(i).getId()
									+ "() "
									+ " {			"
									+ " 	width=screen.availWidth * 0.8;	"
									+ " 	height=screen.availHeight * 0.8;	"
									+ "   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ " 	  	var rv = window.showModalDialog('getRelateEluserInfo.action?rn='+Math.random(),null,sFeature);	"
									+ "var display='';"
									+ "var returnvalue='';"
									+ "if(rv!=null)"
									+ "{"
									+ "	var str=String(rv).split('_--_');"
									+ "}"
									+ "if(str!=null&&str.length>0)"
									+ "{"
									+ "	for(i=0;i<str.length;i++)"
									+ "	{"
									+ "		var tmp =str[i].split('_-_');"
									+ "		display += tmp[1] ;"
									+ "		returnvalue+=tmp[0];"
									+ "		if(i+1!=str.length) "
									+ "		{"
									+ "			display +=',' ;"
									+ "			returnvalue+='__-__'"
									+ "		}"
									+ "	}"
									+ "}"
									+ ""
									+ ""
									+ "document.getElementById('relate_"
									+ list_tags.get(i).getId()
									+ "').value=returnvalue; "
									+ "}"
									+ " </script> ");

					out.println("<span style='color:red' id='relate_"
							+ list_tags.get(i).getId() + "__' >" + username
							+ "</span><span>" + mark + "</span>");
					out
							.println("<img src='images/choose.gif' style='cursor:hand' id='relate_"
									+ list_tags.get(i).getId()
									+ "___' title='选择相关负责人' onclick='changeRelateUser(this);' />");
					// out.println("<span style='color:red;cursor: hand'
					// id='relate_"+list_tags.get(i).getId()+"___'
					// onclick='changeRelateUser(this);'>点此添加</span>");
					out.println("<input type='hidden' name='relate_"
							+ list_tags.get(i).getId() + "' id='relate_"
							+ list_tags.get(i).getId() + "'  value='" + uid
							+ "'/>");
					out.println("</td>");
					control_tr++;

				} else if (list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td >");
					out.println("<input type='hidden' name='"
							+ list_tags.get(i).getColumn_name() + "' id='"
							+ list_tags.get(i).getColumn_name() + "' />");
					if (list_tags.get(i).getRequired() == 1) {
						if (list_tags.get(i).getWritible() == 1) {
							out
									.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
											+ mark
											+ "</span><span style='color:red'>不可填写</span>");
						}
					} else {
						if (list_tags.get(i).getWritible() == 1) {
							out
									.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
											+ mark
											+ "</span><span style='color:red'>不可填写</span>");
						}
					}

					out.println("</td>");
					control_tr++;
				}

				if (i == list_tags.size() - 1 && control_tr == 1) {
					out.println("<td colspan=2></td>");
				}
				if (control_tr == 2) {
					out.println("</tr>");
					control_tr = 0;
				}
			}
		}
		 out.println("</tbody>");
		 out.println("</table>");

		out.println("<div  style='text-align: center; width: 100%'>");
		for (int i = 0; i < list_ricktext.size(); i++) {
			if (list_tags.get(list_ricktext.get(i)).getValue() == null)
				list_tags.get(list_ricktext.get(i)).setValue("");
			out.println("<label>"
					+ list_tags.get(list_ricktext.get(i)).getName_display()
					+ "</label>");
			out
					.println("<textarea  name='"
							+ list_tags.get(list_ricktext.get(0))
									.getColumn_name()
							+ "' id='content' cols='60' rows='7' "
							+ " cssStyle='width: 100%; height: 440px;; visibility: hidden;'  > "
							+ list_tags.get(list_ricktext.get(i)).getValue()
							+ " </textarea>");
			break;
		}

		out.println("</div>");
	}

	/**
	 * 输出修改页面的HTML
	 * 
	 * @param list_tags
	 * @param out
	 * @param control_tr
	 * @param control_size
	 * @param currentUser
	 * @param nowdate
	 * @param list_ricktext
	 * @param kk
	 * @param final_
	 * @param actionName
	 * @param id
	 * @throws IOException
	 */
	public static void outPutUpdateHTML(List<Tags> list_tags, JspWriter out,
			int control_tr, int control_size, CurrentUser currentUser,
			String nowdate, List<Integer> list_ricktext, String kk, int final_,
			String actionName, int id) throws IOException {
		System.out.println(actionName);
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getUpdate_display() == 1)// display
			{
				control_size--;

				if (control_tr == 0) {
					out.println("<tr>");
				}

				String value = list_tags.get(i).getValue();
				if (value == null)
					value = "";

				String mark = "";// 简单mark
				// 将mark放在字段下方,未做
				mark = TagsUtil.getMarkHTML(list_tags.get(i).getMark());

				String MarkDiv = "";
				MarkDiv = TagsUtil.getWanzheng_mark(list_tags.get(i)
						.getTable_name(), list_tags.get(i).getColumn_name(),
						list_tags.get(i).getWanzheng_mark(), list_tags.get(i)
								.getTagsMark());
				out.println(MarkDiv);

				String textvalue = "";
				String textwidth = " style='width:300px;' ";

				if (list_tags.get(i).getDefault_value() != null) {
					String textcontrol[] = list_tags.get(i).getDefault_value()
							.split("==");// defaultvalue==width

					if (!list_tags.get(i).getDefault_value().equals("")) {
						if (!textcontrol[0].equals(""))
							textvalue = " value='" + textcontrol[0] + "' ";
						if (textcontrol.length > 1)
							textwidth = " style='width:" + textcontrol[1]
									+ "px;' ";
					}
				}

				if (list_tags.get(i).getDisplay_type().equals("文本")) {

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input  type='text' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value = '"
											+ value
											+ "'  "
											+ textwidth
											+ "  "
											+ "  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input  type='text' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value = '"
											+ value
											+ "'  "
											+ textwidth
											+ "  "
											+ "  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
											+ mark + "</span>");
						}
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " "
													+ "  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " "
													+ "  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " "
													+ "  readOnly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " "
													+ "  readOnly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " /"
													+ "  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " /"
													+ "  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " /"
													+ "  readOnly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value = '"
													+ value
													+ "'  "
													+ textwidth
													+ " /"
													+ "  readOnly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' value = '"
												+ value
												+ "'  "
												+ textwidth
												+ " /"
												+ "  readOnly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							} else {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' value = '"
												+ value
												+ "'  "
												+ textwidth
												+ " /"
												+ "  readOnly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							}
						}
					}

					out.println("</td>");
					control_tr++;
				}

				else if (list_tags.get(i).getDisplay_type().equals("实数")
						|| list_tags.get(i).getDisplay_type().equals("整数")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getIs_calculate() == 1) {
							if (list_tags.get(i).getRequired() == 1) {
								out.println("<input id='"
										+ list_tags.get(i).getColumn_name()
										+ "' readOnly value='" + value + "' "
										+ textwidth
										+ " /><span style='color:red'></span>");
							} else {
								out.println("<input id='"
										+ list_tags.get(i).getColumn_name()
										+ "' readOnly value='" + value + "' "
										+ textwidth + "/>");
							}
						} else if (list_tags.get(i).getBiaojianqiuhe_check() == 1) {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' size='50' "
												+ " value ='"
												+ value
												+ "' "
												+ textwidth
												+ " onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' size='50' "
												+ " value ='"
												+ value
												+ "' "
												+ textwidth
												+ " onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' size='50' "
												+ " value ='"
												+ value
												+ "' "
												+ textwidth
												+ " onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input type='text' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' size='50' "
												+ " value ='"
												+ value
												+ "' "
												+ textwidth
												+ " onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							}
						}
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								if (list_tags.get(i).getIs_calculate() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out.println("<input id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark + "</span>");
									}
								} else if (list_tags.get(i).getBiaojianqiuhe_check() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark + "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark + "</span>");
									}
								}
							} else {
								if (list_tags.get(i).getIs_calculate() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark + "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark + "</span><span style='color:red'>不可修改</span>");
									}
								} else if (list_tags.get(i)
										.getBiaojianqiuhe_check() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark + "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark + "</span><span style='color:red'>不可修改</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark + "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark + "</span><span style='color:red'>不可修改</span>");
									}
								}
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								if (list_tags.get(i).getIs_calculate() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark
														+ "</span>");
									} else {
										out
												.println("<input id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark
														+ "</span>");
									}
								} else if (list_tags.get(i)
										.getBiaojianqiuhe_check() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark
														+ "</span>");
									} else {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark
														+ "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark
														+ "</span>");
									} else {
										out
												.println("<input type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark
														+ "</span>");
									}
								}
							} else {
								if (list_tags.get(i).getIs_calculate() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' readOnly value='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									}
								} else if (list_tags.get(i)
										.getBiaojianqiuhe_check() == 1) {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly type='text' id='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' size='50' "
														+ " value ='"
														+ value
														+ "' "
														+ textwidth
														+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									}
								}
							}
						} else {
							if (list_tags.get(i).getIs_calculate() == 1) {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input readOnly id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' readOnly value='"
													+ value
													+ "' "
													+ textwidth
													+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input readOnly id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' readOnly value='"
													+ value
													+ "' "
													+ textwidth
													+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							} else if (list_tags.get(i)
									.getBiaojianqiuhe_check() == 1) {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input readOnly type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' size='50' "
													+ " value ='"
													+ value
													+ "' "
													+ textwidth
													+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input readOnly type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' size='50' "
													+ " value ='"
													+ value
													+ "' "
													+ textwidth
													+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input readOnly type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' size='50' "
													+ " value ='"
													+ value
													+ "' "
													+ textwidth
													+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input readOnly type='text' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' size='50' "
													+ " value ='"
													+ value
													+ "' "
													+ textwidth
													+ " class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("百分比")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;' >");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getTime_jindu_ids() != null
							&& !list_tags.get(i).getTime_jindu_ids().equals("")) {

						BigDecimal bg;
						if (!value.equals("")) {
							bg = new BigDecimal(value);
							value = String.valueOf(bg.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue());
						}
						if (list_tags.get(i).getWritible() == 1) {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input value='"
												+ value
												+ "' readOnly id='value_jindutiao__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' type='text' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onKeyUp='getJindutiao("
												+ list_tags.get(i)
														.getJindutiao()
												+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "'>%</span><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input value='"
												+ value
												+ "' readOnly id='value_jindutiao__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' type='text' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onKeyUp='getJindutiao("
												+ list_tags.get(i)
														.getJindutiao()
												+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "'>%</span><span>" + mark
												+ "</span>");
							}
						} else {
							if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
								if (list_tags.get(i).getWritible() == 3) {// 终审可写
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark + "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									}
								}
							} else if (actionName.equals("searchContactTags")) {// 初审页面入口
								if (list_tags.get(i).getWritible() == 2) {// 初审可写
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark + "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									}
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input readOnly value='"
													+ value
													+ "' id='value_jindutiao__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' type='text' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'>%</span><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input readOnly value='"
													+ value
													+ "' id='value_jindutiao__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' type='text' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'>%</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						}

					} else if (list_tags.get(i).getYewu_jindu_ids() != null
							&& !list_tags.get(i).getYewu_jindu_ids().equals("")) {

						BigDecimal bg;
						if (!value.equals("")) {
							bg = new BigDecimal(value);
							value = String.valueOf(bg.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue());
						}
						if (list_tags.get(i).getWritible() == 1) {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input value='"
												+ value
												+ "' readOnly id='value_jindutiao__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' type='text' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "'>%</span><span>"
												+ mark
												+ "</span><span style='color:red'>*</span>");
							} else {
								out
										.println("<input value='"
												+ value
												+ "' readOnly id='value_jindutiao__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' type='text' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "'>%</span><span>" + mark
												+ "</span>");
							}
						} else {
							if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
								if (list_tags.get(i).getWritible() == 3) {// 终审可写
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark + "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span style='color:red'>不可修改</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>不可修改</span><span>"
														+ mark + "</span>");
									}
								}
							} else if (actionName.equals("searchContactTags")) {// 初审页面入口
								if (list_tags.get(i).getWritible() == 2) {// 初审可写
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark + "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span style='color:red'>不可修改</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>不可修改</span><span>"
														+ mark + "</span>");
									}
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input readOnly value='"
													+ value
													+ "' id='value_jindutiao__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' type='text' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'>%</span><span style='color:red'>*</span><span style='color:red'>不可修改</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input readOnly value='"
													+ value
													+ "' id='value_jindutiao__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' type='text' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'>%</span><span style='color:red'>不可修改</span><span>"
													+ mark + "</span>");
								}
							}
						}
					} else {
						BigDecimal bg;
						if (!value.equals("")) {
							bg = new BigDecimal(value);
							value = String.valueOf(bg.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue());
						}
						if (list_tags.get(i).getWritible() == 1) {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input value='"
												+ value
												+ "' id='value_jindutiao__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' type='text' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onKeyUp='getJindutiao("
												+ list_tags.get(i)
														.getJindutiao()
												+ ",this);'/><span style='color:red;' id='span_red__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">%</span><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input value='"
												+ value
												+ "' id='value_jindutiao__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' type='text' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' style='width:300px;' onKeyUp='getJindutiao("
												+ list_tags.get(i)
														.getJindutiao()
												+ ",this);'/><span style='color:red;' id='span_red__"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">%</span><span>"
												+ mark + "</span>");
							}
						} else {
							if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
								if (list_tags.get(i).getWritible() == 3) {// 终审可写
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark + "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									}
								}
							} else if (actionName.equals("searchContactTags")) {// 初审页面入口
								if (list_tags.get(i).getWritible() == 2) {// 初审可写
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark + "</span>");
									} else {
										out
												.println("<input value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' onKeyUp='getJindutiao("
														+ list_tags.get(i)
																.getJindutiao()
														+ ",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark + "</span>");
									}
								} else {
									if (list_tags.get(i).getRequired() == 1) {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span style='color:red'>*</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									} else {
										out
												.println("<input readOnly value='"
														+ value
														+ "' id='value_jindutiao__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' type='text' name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "'>%</span><span>"
														+ mark
														+ "</span><span style='color:red'>不可修改</span>");
									}
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input readOnly value='"
													+ value
													+ "' id='value_jindutiao__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' type='text' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'>%</span><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input readOnly value='"
													+ value
													+ "' id='value_jindutiao__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' type='text' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' style='width:300px;' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'>%</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						}
					}

					if (list_tags.get(i).getJindutiao() == 1) {// 显示进度条
						out
								.println("<div id='jindutiao_div__"
										+ list_tags.get(i).getColumn_name()
										+ "' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='"
										+ value + "'%'  id='show_jindutiao__"
										+ list_tags.get(i).getColumn_name()
										+ "' /></div>");
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("日期")) {

					if (list_tags.get(i).getDefault_value() != null) {
						if (list_tags.get(i).getDefault_value().indexOf("_") >= 0)
							value = do4(nowdate, Integer.parseInt(list_tags
									.get(i).getDefault_value().split("_")[1]));
						else
							value = do4(nowdate, 0);
					}
					value = ScheduleUtil.dateFormat(value, list_tags.get(i)
							.getTimeformat());
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input value='"
											+ value
											+ "' class='Wdate'  readonly='readonly' type='text'  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' "
											+ " onClick='setday(this)' id='"
											+ list_tags.get(i).getColumn_name()
											+ "'   style='width:120px;'  onblur='time_columns(this);'/><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input value='"
											+ value
											+ "' class='Wdate'  readonly='readonly' type='text'  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' "
											+ " onClick='setday(this)' id='"
											+ list_tags.get(i).getColumn_name()
											+ "'   style='width:120px;'  onblur='time_columns(this);'/><span>"
											+ mark + "</span>");
						}
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ " onClick='setday(this)' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  onblur='time_columns(this);'/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ " onClick='setday(this)' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  onblur='time_columns(this);'/><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ "  id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  /><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ "  id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  /><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ " onClick='setday(this)' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  onblur='time_columns(this);'/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ " onClick='setday(this)' id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  onblur='time_columns(this);'/><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ "  id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  /><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input value='"
													+ value
													+ "' class='Wdate'  readonly='readonly' type='text'  name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' "
													+ "  id='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "'   style='width:120px;'  /><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input value='"
												+ value
												+ "' class='Wdate'  readonly='readonly' type='text'  name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' "
												+ "  id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "'   style='width:120px;'  /><span style='color:red'>*</span><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							} else {
								out
										.println("<input value='"
												+ value
												+ "' class='Wdate'  readonly='readonly' type='text'  name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' "
												+ "  id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "'   style='width:120px;'  /><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							}
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("分级下拉选项")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getUpdate_display() == 1) {
							break;
						} else
							k++;
					}
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  colspan=3>");
					
					int level = list_tags.get(i).getJibieshu();
					String str_select_head = "";
					String disabled = "";
					String required = "";
					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							required = "<span style='color:red'>*</span>";
							str_select_head = "<select name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
						} else {
							str_select_head = "<select name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
						}
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								if (list_tags.get(i).getRequired() == 1) {
									required = "<span style='color:red'>*</span>";
									str_select_head = "<select name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								} else {
									str_select_head = "<select name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								}
							} else {
								disabled = "disabled";
								required = "<span style='color:red'>*</span>";
								if (list_tags.get(i).getRequired() == 1) {
									str_select_head = "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								} else {
									str_select_head = "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								}
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								if (list_tags.get(i).getRequired() == 1) {
									required = "<span style='color:red'>*</span>";
									str_select_head = "<select name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								} else {
									str_select_head = "<select name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								}
							} else {
								disabled = "disabled";
								if (list_tags.get(i).getRequired() == 1) {
									required = "<span style='color:red'>*</span>";
									str_select_head = "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								} else {
									str_select_head = "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
								}
							}
						} else {
							disabled = "disabled";
							if (list_tags.get(i).getRequired() == 1) {
								required = "<span style='color:red'>*</span>";
								str_select_head = "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
							} else {
								str_select_head = "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_1' id='"+list_tags.get(i).getColumn_name()+"_1' onchange='javascript:change_selectlevel(this,1,"+level+");' >";
							}
						}
					}

					String html = "";
					//输出修改页面的分级下拉选项
					String[] array = null;
					if(value != null && !value.equals("")){
						array = value.split("___");
						
					}

					String select_html = "";
					String select_div_begin = "<div id='"+list_tags.get(i).getColumn_name()+"__'>";
					String select_value = "<input type='hidden' value='"+list_tags.get(i).getValue()+"' name='"+list_tags.get(i).getColumn_name()+"' id='"+list_tags.get(i).getColumn_name()+"' />";
					String select_div_end = "</div>";
					String select_end = "</select>";
					String select_body = "<option value='0'>请选择</option>";
					SelectLevel selectLevel = list_tags.get(i).getSelectLevel();
					if(selectLevel!=null){
						List<SelectLevel> childs = list_tags.get(i).getSelectLevelList();
						if(childs!=null&&childs.size()>0){
							for(int q=0;q<childs.size();q++){
								if(Integer.parseInt(array[0].split("__")[0])==childs.get(q).getId()){
									select_body += "<option value='"+childs.get(q).getId()+"' selected='selected'>"+childs.get(q).getName()+"</option>";
								}else{
									select_body += "<option value='"+childs.get(q).getId()+"'>"+childs.get(q).getName()+"</option>";
								}
							}
						}
					}
					select_html = str_select_head + select_body + select_end;
					
					String otherhtml =  "";
					String otherhtml_begin = "";
					String otherhtml_end = "";
					String otherhtml_body = "<option value='0'>请选择</option>";
					String otherhtml_select = "";
					if(level>=2){
						for(int m=2;m<level+2-1;m++){
							otherhtml_begin = "<select "+disabled+" name='"+list_tags.get(i).getColumn_name()+"_"+m+"' id='"+list_tags.get(i).getColumn_name()+"_"+m+"' onchange='javascript:change_selectlevel(this,"+m+","+level+");'>";
							otherhtml_end = "</select>";
							otherhtml_body = "<option value='"+array[m-1].split("__")[0]+"' selected='selected'>"+array[m-1].split("__")[1]+"</option>";
							otherhtml_select += otherhtml_begin + otherhtml_body + otherhtml_end ;
						}
					}
					otherhtml = otherhtml_select;
					
					html = select_div_begin + select_value + select_html + otherhtml + required + select_div_end;
					
					System.out.println(html);
					out.println(html);
					out.println("</td>");
					control_tr = 2;
				}else if (list_tags.get(i).getDisplay_type().equals("下拉选项")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getUpdate_display() == 1) {
							break;
						} else
							k++;
					}
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str[] = list_tags.get(i).getDefault_value().split(
							"==");
					String str_select_head = "";
					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							str_select_head = "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "'  name='"
								+ list_tags.get(i).getColumn_name() + "' ><span style='color:red'>*</span>";
						}else{
							str_select_head = "<select id='"
								+ list_tags.get(i).getColumn_name()
								+ "'  name='"
								+ list_tags.get(i).getColumn_name() + "' >";
						}
						
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								if (list_tags.get(i).getRequired() == 1) {
									str_select_head = "<select   name='"
											+ list_tags.get(i).getColumn_name()
											+ "' ><span style='color:red'>*</span>";
								} else {
									str_select_head = "<select   name='"
											+ list_tags.get(i).getColumn_name()
											+ "' >";
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									str_select_head = "<select disabled  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' ><span style='color:red'>*</span>";
								} else {
									str_select_head = "<select disabled  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' >";
								}
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								if (list_tags.get(i).getRequired() == 1) {
									str_select_head = "<select   name='"
											+ list_tags.get(i).getColumn_name()
											+ "' ><span style='color:red'>*</span>";
								} else {
									str_select_head = "<select   name='"
											+ list_tags.get(i).getColumn_name()
											+ "' >";
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									str_select_head = "<select disabled  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' ><span style='color:red'>*</span>";
								} else {
									str_select_head = "<select disabled  name='"
											+ list_tags.get(i).getColumn_name()
											+ "' >";
								}
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								str_select_head = "<select disabled  name='"
										+ list_tags.get(i).getColumn_name()
										+ "' ><span style='color:red'>*</span>";
							} else {
								str_select_head = "<select disabled  name='"
										+ list_tags.get(i).getColumn_name()
										+ "' >";
							}
						}
					}

					String str_default = "<option value=''>请选择</option>";
					String str_select_tail = "</select>";
					String str_select_body = "";
					if (str.length > 0) {
						for (int j = 0; j < str.length; j++) {

							if (str[j].equals(list_tags.get(i).getValue())) {
								str_select_body += "<option value='" + str[j]
										+ "' selected >" + str[j] + "</option>";
							} else {
								str_select_body += "<option value='" + str[j]
										+ "' >" + str[j] + "</option>";
							}
						}
					}

					out.println(str_select_head + str_default + str_select_body
							+ str_select_tail + "<span>" + mark + "</span>");

					out.println("</td>");
					control_tr++;
				}else if (list_tags.get(i).getDisplay_type().equals("单选")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str[] = null;
					if (list_tags.get(i).getDefault_value() != null
							&& !list_tags.get(i).getDefault_value().equals("")) {
						str = list_tags.get(i).getDefault_value().split("==");
						String radio_body = "";
						if (str.length > 0) {
							for (int j = 0; j < str.length; j++) {
								if (list_tags.get(i).getValue() != null) {
									if (str[j].equals(list_tags.get(i)
											.getValue())) {
										if (list_tags.get(i).getWritible() == 1) {
											radio_body += "<input type='radio' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value='"
													+ str[j]
													+ "' checked>" + str[j];
										} else {
											if (actionName
													.equals("finalsearchContactTags")) {// 终审页面入口
												if (list_tags.get(i)
														.getWritible() == 3) {// 终审可写
													radio_body += "<input type='radio' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												} else {
													radio_body += "<input type='radio' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												}
											} else if (actionName
													.equals("searchContactTags")) {// 初审页面入口
												if (list_tags.get(i)
														.getWritible() == 2) {// 初审可写
													radio_body += "<input type='radio' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												} else {
													radio_body += "<input type='radio' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												}
											} else {
												radio_body += "<input type='radio' disabled name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' value='"
														+ str[j]
														+ "' checked>" + str[j];
											}
										}
									} else {
										if (list_tags.get(i).getWritible() == 1) {
											radio_body += "<input type='radio' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value='"
													+ str[j]
													+ "'>" + str[j];
										} else {
											if (actionName
													.equals("finalsearchContactTags")) {// 终审页面入口
												if (list_tags.get(i)
														.getWritible() == 3) {// 终审可写
													radio_body += "<input type='radio' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												} else {
													radio_body += "<input type='radio' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												}
											} else if (actionName
													.equals("searchContactTags")) {// 初审页面入口
												if (list_tags.get(i)
														.getWritible() == 2) {// 初审可写
													radio_body += "<input type='radio' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												} else {
													radio_body += "<input type='radio' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												}
											} else {
												radio_body += "<input type='radio' disabled name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' value='"
														+ str[j]
														+ "'>" + str[j];
											}
										}
									}
								} else {
									radio_body += "<input type='radio' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='" + str[j] + "'>"
											+ str[j];
								}
							}
						}
						//						
						if (list_tags.get(i).getRequired() == 1) {
							out.println(radio_body
									+ "<span style='color:red'>*</span><span>"
									+ mark + "</span>");
						} else {
							out.println(radio_body + "<span>" + mark
									+ "</span>");
						}

					}
					out.println("</td>");
					control_tr++;

				} else if (list_tags.get(i).getDisplay_type().equals("复选")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str[] = null;
					if (list_tags.get(i).getDefault_value() != null
							&& !list_tags.get(i).getDefault_value().equals("")) {
						str = list_tags.get(i).getDefault_value().split("==");
						String checkbox_body = "";
						if (str.length > 0) {
							for (int j = 0; j < str.length; j++) {
								if (list_tags.get(i).getValue() != null) {
									if (list_tags.get(i).getValue().indexOf(
											str[j]) >= 0) {
										if (list_tags.get(i).getWritible() == 1) {
											checkbox_body += "<input type='checkbox' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value='"
													+ str[j]
													+ "' checked>" + str[j];
										} else {
											if (actionName
													.equals("finalsearchContactTags")) {// 终审页面入口
												if (list_tags.get(i)
														.getWritible() == 3) {// 终审可写
													checkbox_body += "<input type='checkbox' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												} else {
													checkbox_body += "<input type='checkbox' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												}
											} else if (actionName
													.equals("searchContactTags")) {// 初审页面入口
												if (list_tags.get(i)
														.getWritible() == 2) {// 初审可写
													checkbox_body += "<input type='checkbox' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												} else {
													checkbox_body += "<input type='checkbox' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "' checked>"
															+ str[j];
												}
											} else {
												checkbox_body += "<input type='checkbox' disabled name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' value='"
														+ str[j]
														+ "' checked>" + str[j];
											}
										}
									} else {
										if (list_tags.get(i).getWritible() == 1) {
											checkbox_body += "<input type='checkbox' name='"
													+ list_tags.get(i)
															.getColumn_name()
													+ "' value='"
													+ str[j]
													+ "'>" + str[j];
										} else {
											if (actionName
													.equals("finalsearchContactTags")) {// 终审页面入口
												if (list_tags.get(i)
														.getWritible() == 3) {// 终审可写
													checkbox_body += "<input type='checkbox' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												} else {
													checkbox_body += "<input type='checkbox' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												}
											} else if (actionName
													.equals("searchContactTags")) {// 初审页面入口
												if (list_tags.get(i)
														.getWritible() == 2) {// 初审可写
													checkbox_body += "<input type='checkbox' name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												} else {
													checkbox_body += "<input type='checkbox' disabled name='"
															+ list_tags
																	.get(i)
																	.getColumn_name()
															+ "' value='"
															+ str[j]
															+ "'>"
															+ str[j];
												}
											} else {
												checkbox_body += "<input type='checkbox' disabled name='"
														+ list_tags
																.get(i)
																.getColumn_name()
														+ "' value='"
														+ str[j]
														+ "'>" + str[j];
											}
										}
									}
								} else {
									checkbox_body += "<input type='checkbox' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='"
											+ str[j]
											+ "' >"
											+ str[j];
								}
							}
						}
						//						
						if (list_tags.get(i).getRequired() == 1) {
							out.println(checkbox_body
									+ "<span style='color:red'>*</span><span>"
									+ mark + "</span>");
						} else {
							out.println(checkbox_body + "<span>" + mark
									+ "</span>");
						}

					}

					out.println("</td>");
					control_tr++;

				} else if (list_tags.get(i).getDisplay_type().equals("城市")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					out.println("<input type='hidden' id='"
							+ list_tags.get(i).getColumn_name() + "' name='"
							+ list_tags.get(i).getColumn_name() + "' >");
					String output = "" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_province'  name='"
							+ list_tags.get(i).getColumn_name()
							+ "_province' onchange=\"changeProvince('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_province' >  " + "请选择省" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' onchange=\"changeCity('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\" >  " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_city'  >  " + "请选择市" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' onchange=\"changeCounty('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_county' >" + "请选择县" + "</option>"
							+ "</select>" + "<span style='color:red'>*</span>"
							+ "<span>" + mark + "</span>";

					String output_not_required = "" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_province'  name='"
							+ list_tags.get(i).getColumn_name()
							+ "_province' onchange=\"changeProvince('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_province' >  " + "请选择省" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' onchange=\"changeCity('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\" >  " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_city'  >  " + "请选择市" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' onchange=\"changeCounty('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_county' >" + "请选择县" + "</option>"
							+ "<span>" + mark + "</span>" + "</select>";

					String output_disabled = "" + "<select disabled id='"
							+ list_tags.get(i).getColumn_name()
							+ "_province'  name='"
							+ list_tags.get(i).getColumn_name()
							+ "_province' onchange=\"changeProvince('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_province' >  " + "请选择省" + "</option>"
							+ "</select>" + "<select disabled id='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' onchange=\"changeCity('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\" >  " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_city'  >  " + "请选择市" + "</option>"
							+ "</select>" + "<select disabled id='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' onchange=\"changeCounty('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_county' >" + "请选择县" + "</option>"
							+ "</select>" + "<span>" + mark + "</span>"
							+ "<span style='color:red'>不可填写</span>";

					if (list_tags.get(i).getWritible() == 1) {
						out.println(output_not_required);
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								out.println(output_not_required);
							} else {
								out.println(output_disabled);
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								out.println(output_not_required);
							} else {
								out.println(output_disabled);
							}
						} else {
							out.println(output_disabled);
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("大文本")) {
					if (control_tr == 1) {
						out.println("<td colspan=2></td>");
						out.println("</tr>");
						out.println("<tr>");
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td colspan='3'>");

					if (list_tags.get(i).getWritible() == 1) {
						out
								.println("<textarea cols='100%' rows='10' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' "
										+ "   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">"
										+ value
										+ "</textarea><span>"
										+ mark
										+ "</span>");
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								out
										.println("<textarea cols='100%' rows='10' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' "
												+ "   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">"
												+ value
												+ "</textarea><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<textarea disabled cols='100%' rows='10' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' "
												+ "   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">"
												+ value
												+ "</textarea><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								out
										.println("<textarea cols='100%' rows='10' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' "
												+ "   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">"
												+ value
												+ "</textarea><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<textarea disabled cols='100%' rows='10' id='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' name='"
												+ list_tags.get(i)
														.getColumn_name()
												+ "' "
												+ "   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">"
												+ value
												+ "</textarea><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							}
						} else {
							out
									.println("<textarea disabled cols='100%' rows='10' id='"
											+ list_tags.get(i).getColumn_name()
											+ "' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' "
											+ "   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\">"
											+ value
											+ "</textarea><span>"
											+ mark
											+ "</span><span style='color:red'>不可修改</span>");
						}
					}

					out.println("</td>");
					control_tr = 2;
				} else if (list_tags.get(i).getDisplay_type().equals("附件上传")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getUpdate_display() == 1) {
							break;
						} else
							k++;
					}
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str2[] = { "", "" };
					if (!value.equals("")) {
						String str[] = value.split("==");
						if (str.length == 2) {
							str2[0] = str[0];
							str2[1] = str[1];
						} else {
							str2[1] = str[0];
						}
					}

					out
							.println("<script type='text/javascript'>	"
									+ " function addStuff_"
									+ list_tags.get(i).getId()
									+ "() {	 "
									+ " 	width=1060;	"
									+ " 	height=500;	"
									+ "  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ "  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	"
									+

									" 	if(null==rv){	"
									+ " 	 	alert('您没选择东西！'); 	"
									+ " 	 	return ;		" + " 	 } 					"
									+ " 	 document.getElementById('"
									+ list_tags.get(i).getColumn_name()
									+ "_').value=rv; 		" + " 	 } "
									+ " </script> ");

					out
							.println("<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "' id='"
									+ list_tags.get(i).getColumn_name()
									+ "' "
									+ " value='"
									+ str2[0]
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr
					out
							.println("<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "_' id='"
									+ list_tags.get(i).getColumn_name()
									+ "_'  readonly "
									+ " value='"
									+ str2[1]
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr

					if (list_tags.get(i).getWritible() == 1) {
						out.println("<a  onClick='addStuff_"
								+ list_tags.get(i).getId()
								+ "()'>浏览资源库</a><span>" + mark + "</span>");
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								out.println("<a  onClick='addStuff_"
										+ list_tags.get(i).getId()
										+ "()'>浏览资源库</a><span>" + mark
										+ "</span>");
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								out.println("<a  onClick='addStuff_"
										+ list_tags.get(i).getId()
										+ "()'>浏览资源库</a><span>" + mark
										+ "</span>");
							}
						}
					}

					out.println("</td>");
					control_tr++;
				}
				//-----------------------音频------------------------
				else if (list_tags.get(i).getDisplay_type().equals("音频")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getUpdate_display() == 1) {
							break;
						} else
							k++;
					}
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");

					String str2[] = { "", "" };
					if (!value.equals("")) {
						String str[] = value.split("==");
						if (str.length == 2) {
							str2[0] = str[0];
							str2[1] = str[1];
						} else {
							str2[1] = str[0];
						}
					}

					out
							.println("<script type='text/javascript'>	"
									+ " function addStuff_"
									+ list_tags.get(i).getId()
									+ "() {	 "
									+ " 	width=1060;	"
									+ " 	height=500;	"
									+ "  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ "  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	"
									+

									" 	if(null==rv){	"
									+ " 	 	alert('您没选择东西！'); 	"
									+ " 	 	return ;		" + " 	 } 					"
									+ " 	 document.getElementById('"
									+ list_tags.get(i).getColumn_name()
									+ "_').value=rv; 		" + " 	 } "
									+ " </script> ");

					out
							.println("<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "' id='"
									+ list_tags.get(i).getColumn_name()
									+ "' "
									+ " value='"
									+ str2[0]
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>"
									+"<input  type=\"button\" class=\"bottom\" onClick=\"ready('"+SystemConfOp.getStuffUrl()+str2[1]+"')\" >");
									out
							.println("<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "_' id='"
									+ list_tags.get(i).getColumn_name()
									+ "_'  readonly "
									+ " value='"
									+ str2[1]
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr

					if (list_tags.get(i).getWritible() == 1) {
						out.println("<a  onClick='addStuff_"
								+ list_tags.get(i).getId()
								+ "()'>浏览资源库</a><span>" + mark + "</span>");
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								out.println("<a  onClick='addStuff_"
										+ list_tags.get(i).getId()
										+ "()'>浏览资源库</a><span>" + mark
										+ "</span>");
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								out.println("<a  onClick='addStuff_"
										+ list_tags.get(i).getId()
										+ "()'>浏览资源库</a><span>" + mark
										+ "</span>");
							}
						}
					}

					out.println("</td>");
					control_tr++;
				}
				//-----------------------音频结束----------------------
				else if (list_tags.get(i).getDisplay_type().equals("图片")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td >");

					String str3[] = value.split("==");// 0:height 1:width
					// 2:addr
					String str2[] = { "", "", "" };
//					if (str3 != null && str3.length > 1) {
//						str2[0] = str3[0];
//						str2[1] = str3[1];
//						str2[2] = str3[2];
//					}
					if(value!=null && !value.equals("")){
						str2[2] = value;
						if(list_tags.get(i).getPic()!=null){
							str2[1] = String.valueOf(list_tags.get(i).getPic().getWidth());
							str2[0] = String.valueOf(list_tags.get(i).getPic().getHeight());
						}
					}
					if (str2[0].equals("0") || str2[0].equals(""))
						str2[0] = "100";
					if (str2[1].equals("0") || str2[1].equals(""))
						str2[1] = "100";

					out
							.println("<script type='text/javascript'>	"
									+ " function addStuff_"
									+ list_tags.get(i).getId()
									+ "() {	 "
									+ " 	width=600;	"
									+ " 	height=400;	"
									+ "  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ "  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	"
									+

									" 	if(null==rv){	"
									+ " 	 	alert('您没选择东西！'); 	"
									+ " 	 	return ;		"
									+ " 	 } 					"
									+ "	 var pos = '.' + rv.replace(/.+\\./, ''); "
									+ "   	"
									+ "	if(!(pos=='.jpg'||pos=='.png'||pos=='.bmp'))	"
									+ "	{	"
									+ "		alert('只能上传.jpg,.png,.bmp格式的图片');"
									+ "		return ;"
									+ "	}"
									+ " 	 document.getElementById('"
									+ list_tags.get(i).getColumn_name()
									+ "').value=rv; 		"
									+ " 	 } "
									+ " </script> ");

					out
							.println("高<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "_h'  size='5' value='"
									+ str2[0]
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// height
					out
							.println("宽<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "_w'  size='5' value='"
									+ str2[1]
									+ "'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// width
					out
							.println("<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "' id='"
									+ list_tags.get(i).getColumn_name()
									+ "' "
									+ "  value='"
									+ str2[2]
									+ "'   readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");// addr

					if (list_tags.get(i).getWritible() == 1) {
						out.println("<a  onClick='addStuff_"
								+ list_tags.get(i).getId() + "()'>浏览资源库</a>");
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								out.println("<a  onClick='addStuff_"
										+ list_tags.get(i).getId()
										+ "()'>浏览资源库</a><span>" + mark
										+ "</span>");
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								out.println("<a  onClick='addStuff_"
										+ list_tags.get(i).getId()
										+ "()'>浏览资源库</a><span>" + mark
										+ "</span>");
							}
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td >");

					String tablename[] = list_tags.get(i).getDefault_value()
							.split("==");// tb_clientlinkman_tags==tb_clientlinkman_tags_35==联系主题==varchar2(500)

					out
							.println("<script type='text/javascript'>"
									+ " function add_"
									+ list_tags.get(i).getId()
									+ "() "
									+ " {			"
									+ " 	width=screen.availWidth * 0.8;	"
									+ " 	height=screen.availHeight * 0.8;	"
									+ "   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ " 	  	var rv = window.showModalDialog('relateColumn.action?tablename="
									+ tablename[0]
									+ "&columnname="
									+ tablename[1]
									+ "&is_judge="
									+ list_tags.get(i).getIs_judge()
									+ "&columnName="
									+ list_tags.get(i).getColumn_name()
									+ "&rn='+Math.random(),null,sFeature);	"
									+ "var display='';"
									+ "var returnvalue='';"
									+ "var str;"
									+ "if(rv!=null && rv != '')"
									+ "{"
									+ "	str=String(rv).split('_--_');"
									+ "}"
									+ "if(str!=null&&str.length>0)"
									+ "{"
									+ "	for(i=0;i<str.length;i++)"
									+ "	{"
									+ "		var tmp =str[i].split('_-_');"
									+ "		display += tmp[1] ;"
									+ "		returnvalue+=tmp[0];"
									+ "		if(i+1!=str.length) "
									+ "		{"
									+ "			display +=',' ;"
									+ "			returnvalue+='__-__'"
									+ "		}"
									+ "	}"
									+ "}"
									+ ""
									+ ""
									+ "document.getElementById('relate_"
									+ list_tags.get(i).getId()
									+ "_').value=display;"
									+ "document.getElementById('relate_"
									+ list_tags.get(i).getId()
									+ "').value=returnvalue; ");

					if (kk != null && !kk.equals("")) {
						if (kk.equals(list_tags.get(i).getColumn_name()))
							out.print("addRelate(returnvalue);");
					}

					out.println("}" + "" + "function del(name)" + "{"
							+ "	alert(name);" + "	" + "}" + "" + ""
							+ " </script> ");

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input style='width:300px;' type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_' onclick='add_"
											+ list_tags.get(i).getId()
											+ "();' /><span style='color:red'>*</span>");
						} else {
							out
									.println("<input style='width:300px;' type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_' onclick='add_"
											+ list_tags.get(i).getId()
											+ "();' />");
						}
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_' onclick='add_"
													+ list_tags.get(i).getId()
													+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_' onclick='add_"
													+ list_tags.get(i).getId()
													+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark + "</span>");
								}
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_' onclick='add_"
													+ list_tags.get(i).getId()
													+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_' onclick='add_"
													+ list_tags.get(i).getId()
													+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input style='width:300px;' type='text' readonly  id='relate_"
													+ list_tags.get(i).getId()
													+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
													+ mark + "</span>");
								}
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input style='width:300px;' type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input style='width:300px;' type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");
							}
						}
					}
					out
							.println("<img src='images/choose.gif' style='cursor:hand' title='选择相关' onclick='add_"
									+ list_tags.get(i).getId() + "();' />");
					// ----已添加
					String values[] = value.split(";");
					String tmp = "";
					if (list_tags.get(i).getValue2() != null)
						tmp = list_tags.get(i).getValue2();
					String relateid[] = tmp.split(",");

					String relatename = list_tags.get(i).getColumn_name();
					String relatetablename = list_tags.get(i).getTable_name();
					String relate_tname[] = list_tags.get(i).getDefault_value()
							.split("==");

					String mm = "";
					for (int m = 0; m < relateid.length; m++) {
						if (m == relateid.length - 1)
							mm += relateid[m] + "==" + relate_tname[1];
						else
							mm += relateid[m] + "==" + relate_tname[1]
									+ "__-__";
					}
					out.println("<input type='hidden' name='relate_"
							+ list_tags.get(i).getId() + "' id='relate_"
							+ list_tags.get(i).getId() + "'  value='" + mm
							+ "'/>");

					if (values != null && !values[0].equals("")) {
						for (int j = 0; j < values.length; j++) {
							out.println("<div id='ii_"
									+ list_tags.get(i).getId() + "'>");

							out
									.println("<span style='background-color:white'>"
											+ "<a href='viewContactTags.action?tablename="
											+ relate_tname[0]
											+ "&id="
											+ relateid[j]
											+ "'  >"
											+ values[j]
											+ "</a>"
											+ "|<a href='delRelateTags.action?relateid="
											+ relateid[j]
											+ "&relatename="
											+ relatename
											+ ""
											+ "&id="
											+ id
											+ "&relatetablename="
											+ relatetablename
											+ "'>X</a></span>");

							if (j + 1 != values.length)
								out
										.println("<span style='background-color:red'>|</span>");
							out.println("</div>");
						}
					}
					// out.print("<span style='color:red;cursor: hand'
					// onclick='add_"+list_tags.get(i).getId()+"();'>点此添加</span>");

					out.println("</td>");
					control_tr++;

				} else if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td >");

					out
							.println("<script type='text/javascript'>"
									+ " function add_"
									+ list_tags.get(i).getId()
									+ "() "
									+ " {			"
									+ " 	width=screen.availWidth * 0.8;	"
									+ " 	height=screen.availHeight * 0.8;	"
									+ "   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"
									+ " 	  	var rv = window.showModalDialog('getRelateEluserInfo.action?tablename="
									+ list_tags.get(0).getTable_name()
									+ "&rn='+Math.random(),null,sFeature);	"
									+ "var display='';"
									+ "var returnvalue='';"
									+ "if(rv!=null)"
									+ "{"
									+ "	var str=String(rv).split('_--_');"
									+ "}"
									+ "if(str!=null&&str.length>0)"
									+ "{"
									+ "	for(i=0;i<str.length;i++)"
									+ "	{"
									+ "		var tmp =str[i].split('_-_');"
									+ "		display += tmp[1] ;"
									+ "		returnvalue+=tmp[0];"
									+ "		if(i+1!=str.length) "
									+ "		{"
									+ "			display +=',' ;"
									+ "			returnvalue+='__-__'"
									+ "		}"
									+ "	}"
									+ "}"
									+ ""
									+ ""
									+ "document.getElementById('relate_"
									+ list_tags.get(i).getId()
									+ "_').value=display;"
									+ "document.getElementById('relate_"
									+ list_tags.get(i).getId()
									+ "').value=returnvalue; "
									+ "}"
									+ ""
									+ "function del(name)"
									+ "{"
									+ "	alert(name);"
									+ "	"
									+ "}"
									+ ""
									+ ""
									+ " </script> ");

					if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
						if (list_tags.get(i).getWritible() == 3) {// 终审可写
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_' onclick='add_"
												+ list_tags.get(i).getId()
												+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_' onclick='add_"
												+ list_tags.get(i).getId()
												+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							}
						}
					} else if (actionName.equals("searchContactTags")) {// 初审页面入口
						if (list_tags.get(i).getWritible() == 2) {// 初审可写
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_' onclick='add_"
												+ list_tags.get(i).getId()
												+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_' onclick='add_"
												+ list_tags.get(i).getId()
												+ "();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
												+ mark + "</span>");
							} else {
								out
										.println("<input type='text' readonly  id='relate_"
												+ list_tags.get(i).getId()
												+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
												+ mark + "</span>");
							}
						}
					} else {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input type='text' readonly  id='relate_"
											+ list_tags.get(i).getId()
											+ "_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"
											+ mark + "</span>");
						}
					}
					out.println("<input type='hidden' name='relate_"
							+ list_tags.get(i).getId() + "' id='relate_"
							+ list_tags.get(i).getId() + "'  />");

					// ----已添加
					String values[] = value.split(";");
					String tmp = "";
					if (list_tags.get(i).getValue2() != null)
						tmp = list_tags.get(i).getValue2();
					String relateid[] = tmp.split(",");
					String relatename = list_tags.get(i).getColumn_name();
					String relatetablename = list_tags.get(i).getTable_name();
					if (values != null && !values[0].equals("")) {
						for (int j = 0; j < values.length; j++) {
							out
									.println("<span style='background-color:white'><a>"
											+ ""
											+ values[j]
											+ "</a>"
											+ "|<a href='delRelateTags.action?&id="
											+ id
											+ "&relateid="
											+ relateid[j]
											+ "&relatename="
											+ relatename
											+ "&final_="
											+ final_
											+ "&relatetablename="
											+ relatetablename
											+ "&actionName="
											+ actionName
											+ "'>X</a>"
											+ "</span>");

							if (j + 1 != values.length)
								out
										.println("<span style='background-color:red'>|</span>");
						}
					}
					// out.print("<span style='color:red;cursor: hand'
					// onclick='add_"+list_tags.get(i).getId()+"();'>点此添加</span>");
					out
							.println("<img src='images/choose.gif' style='cursor:hand' title='选择相关负责人' onclick='add_"
									+ list_tags.get(i).getId() + "();' />");
					out.println("</td>");
					control_tr++;

				} else if (list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out
								.println("<span id='_mark_"
										+ list_tags.get(i).getColumn_name()
										+ "' style='cursor:hand' onMouseOver='javascript:show(this);'  >"
										+ list_tags.get(i).getName_display()
										+ "</span>" + ":");
					out.println("</td>");
					out.println("<td  >");
					
					out.println("<input type='hidden' name='"
							+ list_tags.get(i).getColumn_name() + "' id='"
							+ list_tags.get(i).getColumn_name() + "' />");

					if (list_tags.get(i).getWritible() == 1) {
						if (list_tags.get(i).getRequired() == 1) {
							out
									.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span style='color:red'>*</span><span>"
											+ mark + "</span>");
						} else {
							out
									.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
											+ mark + "</span>");
						}
					} else {
						if (actionName.equals("finalsearchContactTags")) {// 终审页面入口
							if (list_tags.get(i).getWritible() == 3) {// 终审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						} else if (actionName.equals("searchContactTags")) {// 初审页面入口
							if (list_tags.get(i).getWritible() == 2) {// 初审可写
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span style='color:red'>*</span><span>"
													+ mark + "</span>");
								} else {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
													+ mark + "</span>");
								}
							} else {
								if (list_tags.get(i).getRequired() == 1) {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span style='color:red'>*</span><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								} else {
									out
											.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
													+ mark
													+ "</span><span style='color:red'>不可修改</span>");
								}
							}
						} else {
							if (list_tags.get(i).getRequired() == 1) {
								out
										.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span style='color:red'>*</span><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							} else {
								out
										.println("<input type='button' value='点击签名' id='"
											+ list_tags.get(i).getColumn_name()
											+ "_' onclick='sign(this);' /><span>"
												+ mark
												+ "</span><span style='color:red'>不可修改</span>");
							}
						}
					}

					out.println("</td>");
					control_tr++;
				}

				if (i == list_tags.size() - 1 && control_tr == 1) {
					out.println("<td colspan=2></td>");
				}

				if (control_tr == 2) {
					out.println("</tr>");
					control_tr = 0;
				}
				out.println("<input type='hidden' value='" + id
						+ "' name='id' />");
			}

		}
		out.println("</tbody>");
		out.println("</table>");

		out.println("<div  style='text-align: center; width: 100%'>");
		for (int i = 0; i < list_ricktext.size(); i++) {
			if (list_tags.get(list_ricktext.get(i)).getValue() == null)
				list_tags.get(list_ricktext.get(i)).setValue("");
			out.println("<label>"
					+ list_tags.get(list_ricktext.get(i)).getName_display()
					+ "</label>");
			out
					.println("<textarea  name='"
							+ list_tags.get(list_ricktext.get(0))
									.getColumn_name()
							+ "' id='content' cols='60' rows='7' "
							+ " cssStyle='width: 100%; height: 440px;; visibility: hidden;'  > "
							+ list_tags.get(list_ricktext.get(i)).getValue()
							+ " </textarea>");
			break;
		}

		out.println("</div>");
	}

	/**
	 * 输出查看页面的HMTL
	 * @param list_tags
	 * @param out
	 * @param currentUser
	 * @param control_tr
	 * @param control_size
	 * @param list_ricktext
	 * @throws IOException
	 */
	public static void outPutViewHTML(List<Tags> list_tags, JspWriter out,
			CurrentUser currentUser, int control_tr, int control_size,
			List<Integer> list_ricktext) throws IOException {

		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getView_display() == 1)// display
			{
				control_size--;
				if (control_tr == 0) {
					out.println("<tr>");
				}
				String str = list_tags.get(i).getValue();
				if (str == null)
					str = "";

				if (list_tags.get(i).getDisplay_type().equals("文本")
						|| list_tags.get(i).getDisplay_type().equals("实数")
						|| list_tags.get(i).getDisplay_type().equals("整数")
						|| list_tags.get(i).getDisplay_type().equals("下拉选项")
						|| list_tags.get(i).getDisplay_type().equals("单选")
						|| list_tags.get(i).getDisplay_type().equals("复选")
						|| list_tags.get(i).getDisplay_type().equals("城市")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getView_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td  >");
					if (list_tags.get(i).getIs_calculate() == 1) {
						out
								.println("<label style='padding-left:10px;color:red' >"
										+ str + "</label>");
					} else {
						out.println("<label style='padding-left:10px' >" + str
								+ "</label>");
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("百分比")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getAdd_display() == 1) {
							break;
						} else
							k++;
					}
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;' >");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");
					out.println("<td  >");

					if (list_tags.get(i).getJindutiao() == 1) {// 显示进度条

						str = !str.equals("") ? String.valueOf(new BigDecimal(
								str).setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue()) : "";
						out
								.println("<table width='300px' border='0' cellspacing='1' ><tr><td><div  id='jindutiao_div__"
										+ list_tags.get(i).getColumn_name()
										+ ":"
										+ i
										+ "' style='border: 1px dotted #FF6633;width:280px'><img height='14' src='images/jd.gif' width='"
										+ str
										+ "%'  id='show_jindutiao__"
										+ list_tags.get(i).getColumn_name()
										+ ":"
										+ i
										+ "'/></div></td><td><center><span style='color:red;'>"
										+ str
										+ "%</span></center></td></tr></table>");
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("日期")) {
					str = ScheduleUtil.dateFormat(str, list_tags.get(i)
							.getTimeformat());

					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getView_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td  >");
					out.println("<label style='padding-left:10px' >" + str
							+ "</label>");

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td >");

					String values[] = str.split(";");
					String tmp = "";
					if (list_tags.get(i).getValue2() != null)
						tmp = list_tags.get(i).getValue2();
					String relateid[] = tmp.split(",");
					String relatename = list_tags.get(i).getColumn_name();
					String relatetablename = list_tags.get(i).getTable_name();

					String relate_tname[] = list_tags.get(i).getDefault_value()
							.split("==");
					if (values != null && !values[0].equals("")) {
						for (int j = 0; j < values.length; j++) {
							out
									.println("<span style='background-color:white'>"
											+ "<a href='viewContactTags.action?tablename="
											+ relate_tname[0]
											+ "&id="
											+ relateid[j]
											+ "'  >"
											+ values[j]
											+ "</a>" + "</span>");

							if (j + 1 != values.length)
								out
										.println("<span style='background-color:red'>|</span>");
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td >");

					String values[] = str.split(";");
					String tmp = "";
					if (list_tags.get(i).getValue2() != null)
						tmp = list_tags.get(i).getValue2();

					if (values != null && !values[0].equals("")) {
						for (int j = 0; j < values.length; j++) {
							out.println("<span style='background-color:white'>"
									+ "" + values[j] + "" + "</span>");

							if (j + 1 != values.length)
								out
										.println("<span style='background-color:red'>|</span>");
						}
					}

					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("附件上传")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getView_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td>");

					String str2[] = str.split("==");
					if (str2.length > 1) {
						out
								.println("<label  style='padding-left:10px'  cols='40' rows='10' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' "
										+ "  readonly  ><a href='downloadStuff.action?down="
										+ str
										+ "' > "
										+ str2[0]
										+ "</a></label>");
					}

					out.println("</td>");
					control_tr++;
				}  else if (list_tags.get(i).getDisplay_type().equals("音频")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getView_display() == 1) {
							break;
						} else
							k++;
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td>");

					String str2[] = str.split("==");
					if (str2.length > 1) {
						out
								.println("<label  style='padding-left:10px'  cols='40' rows='10' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' "
										+ "  readonly  >" 
										+ str2[0]
										+"<input  type=\"button\" class=\"bottom\" onClick=\"ready('"+SystemConfOp.getStuffUrl()+str2[1]+"')\" >"
										+ "</label>");
					}

					out.println("</td>");
					control_tr++;
				}
				
				else if (list_tags.get(i).getDisplay_type().equals("大文本")) {
					if (control_tr == 1) {
						out.println("<td colspan=2></td>");
						out.println("</tr>");
						out.println("<tr>");
					}

					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td colspan='3'>");
					out.println("<label>" + str + "</label>");
					out.println("</td>");
					control_tr = 2;
				} else if (list_tags.get(i).getDisplay_type().equals("图片")) {
					if (control_tr == 1)
						out.println("</tr>");
					out.println("<tr>");
					out
							.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");
					out.println("<td colspan=3>");
					String height = "";
					String width = "";

					String str2[] = { "", "", "" };
					if(str!=null && !str.equals("")){
						str2[2] = str;
						if(list_tags.get(i).getPic()!=null ){
							str2[1] = String.valueOf(list_tags.get(i).getPic().getWidth());
							str2[0] = String.valueOf(list_tags.get(i).getPic().getHeight());
						}
					}
					
//					String str3[] = str.split("==");// 0:height 1:width 2:addr
//					String str2[] = { "", "", "" };
//					if (str3 != null && str3.length > 1) {
//						str2[0] = str3[0];
//						str2[1] = str3[1];
//						str2[2] = str3[2];
//					}

					if (str2[0].equals("0") || str2[0].equals(""))
						height = " height='" + 100 + "' ";
					else
						height = " height='" + str2[0] + "' ";
					if (str2[1].equals("0") || str2[1].equals(""))
						width = " width='" + 100 + "' ";
					else
						width = " width='" + str2[1] + "' ";
					if (!str2[2].equals(""))
						out.println("<img  src='" + str2[2] + "' " + height
								+ " " + width + "  />");

					out.println("</td>");
					control_tr++;
				}else if (list_tags.get(i).getDisplay_type().equals("富文本")) {
					list_ricktext.add(i);
				}else if (list_tags.get(i).getDisplay_type().equals("分级下拉选项")) {
					int k = i + 1;
					for (int l = i + 1; l < list_tags.size(); l++) {
						if (list_tags.get(l).getView_display() == 1) {
							break;
						} else
							k++;
					}

					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if (!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display() + ":");
					out.println("</td>");

					out.println("<td colspan=3>");

					String value = list_tags.get(i).getValue();
					String[] array = null;
					String[] array_ = null;
					String html = "";
					if(value!=null&&!value.equals("")){
						array = value.split("___");
						if(array!=null&&array.length>0){
							for(int m=0;m<array.length;m++){
								array_ = array[m].split("__");
								if(Integer.parseInt(array_[0])!=0){
									html += array_[1] + ">>";
								}
							}
						}
					}
					if(!html.equals("") && html.indexOf(">>")!=-1){
						html = html.substring(0,html.lastIndexOf(">>"));
					}
					out.println("<label style='padding-left:10px' >" + html
							+ "</label>");
					out.println("</td>");
					control_tr = 2;
				}else if(list_tags.get(i).getDisplay_type().equals("当前用户信息")){
					TagsUtil.outPutUserSignViewPage(out);
				}

				if (i == list_tags.size() - 1 && control_tr == 1) {
					out.println("<td colspan=2></td>");
				}
				if (control_tr == 2) {
					out.println("</tr>");
					control_tr = 0;
				}
			}

		}
		out.println("</tbody>");
		out.println("</table>");

		out.println(" <TABLE cellSpacing=1 cellPadding=3 width=100%>");
		for (int i = 0; i < list_ricktext.size(); i++) {
			if (list_tags.get(list_ricktext.get(i)).getValue() == null)
				list_tags.get(list_ricktext.get(i)).setValue("");
			out
					.println(" <TBODY><TR><TD height='30' colspan='2' align='center'>"
							+ list_tags.get(list_ricktext.get(i))
									.getName_display() + "</TD></TR><TR>");

			out
					.println("<TD colspan='2' align='left' bgcolor='#FFFFFF' style='padding:10px;'>"
							+ list_tags.get(list_ricktext.get(i)).getValue()
							+ "</TD></TR> "
							+ " <TR>  <TD></TD> <TD></TD> </TR> </TBODY></TABLE>");
			break;
		}

		out.println("</div>");
	}

	/**
	 * 输出列表页上的搜索标签HTML
	 * 
	 * @param list_tags
	 * @param out
	 * @param rx
	 * @param realname
	 * @param control
	 * @throws IOException
	 */
	public static void outPutCombinesearch(List<Tags> list_tags, JspWriter out,
			int rx, String realname, int control_tr, int control_size)
			throws IOException {
		for (int i = 0; i < list_tags.size(); i++) {
			String value = "";
			if (list_tags.get(i).getValue() == null)
				value = "";
			else
				value = list_tags.get(i).getValue();

			if (list_tags.get(i).getMutilsearch_display() == 1)// display
			{

				control_size--;
				if (control_tr == 0) {
					out.println("<tr>");
				}

				if (i == 0) {// rowspan暂时设置为100
					out
							.println("<TD vAlign=center align=middle width=120 bgColor=#ffffff rowSpan=100>"
									+ "<INPUT class=btn1_mouseout onMouseOver=\"this.className='btn1_mouseover'\" onMouseOut=\"this.className='btn1_mouseout'\" onclick=search(); type=button value=开始搜索></TD></tr><tr>");
//					out.println("<td ><div style=\"text-align: left;width:30px;\" id=\"showtree\"><a href=\"javascript:showtree(true);\" class=\"textbg\">显示部门</a></div></td>");
				}
				
				if (list_tags.get(i).getDisplay_type().equals("文本")) {
					out
							.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out
							.println("<input  type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "'  "
									+ " value ='"
									+ value
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" />");
					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("实数")
						|| list_tags.get(i).getDisplay_type().equals("整数")) {
					out
							.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out
							.println("<input  type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "' "
									+ "       value ='"
									+ value
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" />");
					out.println("至");
					String value2;
					if (list_tags.get(i).getValue2() == null)
						value2 = "";
					else
						value2 = list_tags.get(i).getValue2();
					out
							.println("<input  type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "_' "
									+ "      value ='"
									+ value2
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" />");
					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("日期")) {
					out
							.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out
							.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "' "
									+ " onClick='setday(this)' id='releasetime'      value ='"
									+ value
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" />");
					out.println("至");
					String value2;
					if (list_tags.get(i).getValue2() == null)
						value2 = "";
					else
						value2 = list_tags.get(i).getValue2();
					out
							.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "_' "
									+ " onClick='setday(this)' id='releasetime'      value ='"
									+ value2
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" />");
					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("下拉选项")) {
					String str[] = list_tags.get(i).getDefault_value().split(
							"==");
					String str_select_head = "<select  name='"
							+ list_tags.get(i).getColumn_name() + "' >";
					String str_default = "<option value=''>请选择</option>";
					String str_select_tail = "</select>";
					String str_select_body = "";
					if (str.length > 0) {
						for (int j = 0; j < str.length; j++) {

							if (str[j].equals(list_tags.get(i).getValue())) {
								str_select_body += "<option value='" + str[j]
										+ "' selected >" + str[j] + "</option>";
							} else {
								str_select_body += "<option value='" + str[j]
										+ "' >" + str[j] + "</option>";
							}
						}
					}

					out
							.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out.println(str_select_head + str_default + str_select_body
							+ str_select_tail);
					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("单选")) {
					if (list_tags.get(i).getDefault_value() != null
							&& !list_tags.get(i).getDefault_value().equals("")) {
						String str[] = list_tags.get(i).getDefault_value()
								.split("==");
						String radio_body = "";
						if (str.length > 0) {
							for (int j = 0; j < str.length; j++) {

								if (str[j].equals(list_tags.get(i).getValue())) {
									radio_body += "<input type='radio' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='" + str[j] + "'>"
											+ str[j];
								} else {
									radio_body += "<input type='radio' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='" + str[j] + "'>"
											+ str[j];
								}
							}
						}

						out
								.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
						out.println(list_tags.get(i).getName_display());
						out.println("</td>");
						out.println("<td >");
						out.println(radio_body);
						out.println("</td>");
						control_tr++;
					}
				} else if (list_tags.get(i).getDisplay_type().equals("复选")) {
					if (list_tags.get(i).getDefault_value() != null
							&& !list_tags.get(i).getDefault_value().equals("")) {
						String str[] = list_tags.get(i).getDefault_value()
								.split("==");
						String checkbox_body = "";
						if (str.length > 0) {
							for (int j = 0; j < str.length; j++) {

								if (list_tags.get(i).getDefault_value()
										.indexOf(str[j]) >= 0) {
									checkbox_body += "<input type='radio' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='"
											+ str[j]
											+ "'>"
											+ str[j];
								} else {
									checkbox_body += "<input type='radio' name='"
											+ list_tags.get(i).getColumn_name()
											+ "' value='"
											+ str[j]
											+ "'>"
											+ str[j];
								}
							}
						}

						out
								.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
						out.println(list_tags.get(i).getName_display());
						out.println("</td>");
						out.println("<td >");
						out.println(checkbox_body);
						out.println("</td>");
						control_tr++;
					}

				} else if (list_tags.get(i).getDisplay_type().equals("城市")) {
					out.println("<input type='hidden' id='"
							+ list_tags.get(i).getColumn_name() + "' name='"
							+ list_tags.get(i).getColumn_name() + "' >");
					out
							.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out.println("" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_province'  id='"
							+ list_tags.get(i).getColumn_name()
							+ "_province' onchange=\"changeProvince('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_province' >  " + "请选择省" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' onchange=\"changeCity('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\" >  " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_city'  >  " + "请选择市" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' onchange=\"changeCounty('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_county' >" + "请选择县" + "</option>"
							+ "</select>");
					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
					String value2 = "";
					if (list_tags.get(i).getValue2() == null)
						value2 = "";
					else
						value2 = list_tags.get(i).getValue2();

					out
							.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out
							.println("<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "'  "
									+ " value ='"
									+ value2
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" />");
					out.println("</td>");
					control_tr++;
				} else if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
					String value2 = "";
					if (list_tags.get(i).getValue2() == null)
						value2 = "";
					else
						value2 = list_tags.get(i).getValue2();

					out
							.println("<td height='30' width='100px'  bgcolor='#FFFFFF' align='right' style='padding-right:5px;' >");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					if (rx == 1) {
						out.println(realname);
					} else {
						out
								.println("<input id='relate' type='text' name='"
										+ list_tags.get(i).getColumn_name()
										+ "'  "
										+ " value ='"
										+ realname
										+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\" />");
					}
					out.println("</td>");
					control_tr++;
				}
			}
			if (i == list_tags.size() - 1 && control_tr == 1) {
				out.println("<td colspan=3></td>");
			}
			if (control_tr == 2) {
				out.println("</tr>");
				control_tr = 0;
			}
		}
	}

	/**
	 * 输出选择相关时的搜索HTML
	 * 
	 * @param list_tags
	 * @param out
	 * @throws IOException
	 */
	public static void outPutCombinesearchForRelate(List<Tags> list_tags,
			JspWriter out) throws IOException {
		for (int i = 0; i < list_tags.size(); i++) {
			String value = "";
			if (list_tags.get(i).getValue() == null)
				value = "";
			else
				value = list_tags.get(i).getValue();

			if (list_tags.get(i).getMutilsearch_display() == 1)// display
			{
				if (i == 0) {// rowspan暂时设置为100
					out
							.println("<tr><TD vAlign=center align=middle width=120 bgColor=#ffffff rowSpan=100>"
									+ "<INPUT class=btn1_mouseout onMouseOver=\"this.className='btn1_mouseover'\" onMouseOut=\"this.className='btn1_mouseout'\" onclick=search(); type=button value=开始搜索></TD>"
									+ "</tr>");
				}
				// 百分比、相关负责人、大文本、图片、附件、相关字段 不参与搜索

				if (list_tags.get(i).getDisplay_type().equals("文本")) {
					out.println("<tr>");
					out
							.println("<td height='30' bgcolor='#FFFFFF' align='right'>");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out
							.println("<input type='text' name='"
									+ list_tags.get(i).getColumn_name()
									+ "' size='50' "
									+ " value ='"
									+ value
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");
					out.println("</td>");
					out.println("</tr>");
				} else if (list_tags.get(i).getDisplay_type().equals("实数")
						|| list_tags.get(i).getDisplay_type().equals("整数")) {
					out.println("<tr>");
					out
							.println("<td height='30' bgcolor='#FFFFFF' align='right'>");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out
							.println("<input  type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "' "
									+ "    size='30'   value ='"
									+ value
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");
					out.println("至");
					String value2;
					if (list_tags.get(i).getValue2() == null)
						value2 = "";
					else
						value2 = list_tags.get(i).getValue2();
					out
							.println("<input  type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "_' "
									+ "    size='30'   value ='"
									+ value2
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");
					out.println("</td>");
					out.println("</tr>");
				} else if (list_tags.get(i).getDisplay_type().equals("日期")) {
					out.println("<tr>");
					out
							.println("<td height='30' bgcolor='#FFFFFF' align='right'>");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out
							.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "' "
									+ " onClick='setday(this)' id='releasetime'   size='21'   value ='"
									+ value
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");
					out.println("至");
					String value2;
					if (list_tags.get(i).getValue2() == null)
						value2 = "";
					else
						value2 = list_tags.get(i).getValue2();
					out
							.println("<input class='Wdate'  readonly='readonly' type='text'  name='"
									+ list_tags.get(i).getColumn_name()
									+ "_' "
									+ " onClick='setday(this)' id='releasetime'   size='20'   value ='"
									+ value2
									+ "' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");
					out.println("</td>");
					out.println("</tr>");
				} else if (list_tags.get(i).getDisplay_type().equals("下拉选项")) {
					String str[] = list_tags.get(i).getDefault_value().split(
							"==");
					String str_select_head = "<select  name='"
							+ list_tags.get(i).getColumn_name() + "' >";
					String str_default = "<option value=''>请选择</option>";
					String str_select_tail = "</select>";
					String str_select_body = "";
					if (str.length > 0) {
						for (int j = 0; j < str.length; j++) {

							if (str[j].equals(list_tags.get(i).getValue())) {
								str_select_body += "<option value='" + str[j]
										+ "' selected >" + str[j] + "</option>";
							} else {
								str_select_body += "<option value='" + str[j]
										+ "' >" + str[j] + "</option>";
							}
						}
					}
					out.println("<tr>");
					out
							.println("<td height='30' bgcolor='#FFFFFF' align='right'>");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out.println(str_select_head + str_default + str_select_body
							+ str_select_tail);
					out.println("</td>");
					out.println("</tr>");
				} else if (list_tags.get(i).getDisplay_type().equals("单选")) {
					String str[] = list_tags.get(i).getDefault_value().split(
							"==");
					String radio_body = "";
					if (str.length > 0) {
						for (int j = 0; j < str.length; j++) {

							if (str[j].equals(list_tags.get(i).getValue())) {
								radio_body += "<input type='radio' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' value='" + str[j] + "'>" + str[j];
							} else {
								radio_body += "<input type='radio' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' value='" + str[j] + "'>" + str[j];
							}
						}
					}
					out.println("<tr>");
					out
							.println("<td height='30' bgcolor='#FFFFFF' align='right'>");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out.println(radio_body);
					out.println("</td>");
					out.println("</tr>");
				} else if (list_tags.get(i).getDisplay_type().equals("复选")) {
					String str[] = list_tags.get(i).getDefault_value().split(
							"==");
					String checkbox_body = "";
					if (str.length > 0) {
						for (int j = 0; j < str.length; j++) {

							if (list_tags.get(i).getDefault_value().indexOf(
									str[j]) >= 0) {
								checkbox_body += "<input type='radio' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' value='" + str[j] + "'>" + str[j];
							} else {
								checkbox_body += "<input type='radio' name='"
										+ list_tags.get(i).getColumn_name()
										+ "' value='" + str[j] + "'>" + str[j];
							}
						}
					}
					out.println("<tr>");
					out
							.println("<td height='30' bgcolor='#FFFFFF' align='right'>");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out.println(checkbox_body);
					out.println("</td>");
					out.println("</tr>");
				} else if (list_tags.get(i).getDisplay_type().equals("城市")) {
					out.println("<tr>");
					out
							.println("<td height='30' bgcolor='#FFFFFF' align='right'>");
					out.println(list_tags.get(i).getName_display());
					out.println("</td>");
					out.println("<td >");
					out.println("<input type='hidden' id='"
							+ list_tags.get(i).getColumn_name() + "' name='"
							+ list_tags.get(i).getColumn_name() + "' >");
					out.println("" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_province'  id='"
							+ list_tags.get(i).getColumn_name()
							+ "_province' onchange=\"changeProvince('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_province' >  " + "请选择省" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_city' onchange=\"changeCity('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\" >  " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_city'  >  " + "请选择市" + "</option>"
							+ "</select>" + "<select id='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' name='"
							+ list_tags.get(i).getColumn_name()
							+ "_county' onchange=\"changeCounty('"
							+ list_tags.get(i).getColumn_name()
							+ "');\" style=\"width:100\"> " + "<option id='"
							+ list_tags.get(i).getColumn_name()
							+ "_option_in_county' >" + "请选择县" + "</option>"
							+ "</select>");
					out.println("</td>");
					out.println("</tr>");
				}
				// else if(list_tags.get(i).getDisplay_type().equals("相关字段"))
				// {
				// String value2="";
				// if(list_tags.get(i).getValue2()==null)
				// value2="";
				// else value2=list_tags.get(i).getValue2();
				// out.println("<tr>");
				// out.println("<td height='30' bgcolor='#FFFFFF'
				// align='right'>");
				// out.println(list_tags.get(i).getName_display());
				// out.println("</td>");
				// out.println("<td >");
				// out.println("<input type='text'
				// name='"+list_tags.get(i).getColumn_name()+"' size='50' " +
				// " value ='"+value2+"' class=\"x_bill_item_input\"
				// onFocus=\"_bill_item_focus_bg(this)\"
				// onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");
				// out.println("</td>");
				// out.println("</tr>");
				// }
			}
		}
		out.println("<br>");
	}

	// ///////////////////////////////////////
	//输出查看页面的当前用户信息
	public static void outPutUserSignViewPage(JspWriter out)throws IOException{
		/**
			<script type="text/javascript">
			document.getElementById("userinfo").style.display = "block";
				var ele;
				$.ajax({
						  type: 'POST',
						  url: "getCurrentUserinfo.action",
						  async:false,
						  success: function(data){
						  		data = eval("("+data+")").check_json_result;
						  		if(data != ""){
						  			ele = "<tr>"+
											"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>用户姓名:"+
											"</td>"+
											"<td style='padding-left:10px;color:#0099CC'>"+
													data.name+
											"</td>"+
											"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>部门:"+
											"</td>"+
											"<td style='padding-left:10px;color:#0099CC'>"+
													data.depname+
											"</td>"+
										"</tr>"+
										"<tr>"+
											"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>职务:"+
											"</td>"+
											"<td style='padding-left:10px;color:#0099CC'>"+
													data.zhiwuname+
											"</td>"+
											"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>地市:"+
											"</td>"+
											"<td style='padding-left:10px;color:#0099CC'>"+
													data.dishiname+
											"</td>"+
										"</tr>";
										$(ele).appendTo($("#userinfo"));
						  		}
						  }
					});
			</script>
		 */
		out.println(""
				+"<script type=\"text/javascript\">"
				+"document.getElementById(\"userinfo\").style.display = \"block\";"
				+"var ele;"
				+"$.ajax({"
				+"	 type: 'POST',"
				+"	url: \"getCurrentUserinfo.action\","
				+"	async:false,"
				+"	success: function(data){"
				+"		data = eval(\"(\"+data+\")\").check_json_result;"
				+"		if(data != \"\"){"
				+"			ele = \"<tr>\"+"
				+"			\"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>用户姓名:\"+"
				+"			\"</td>\"+"
				+"			\"<td style='padding-left:10px;color:#0099CC'>\"+"
				+"			data.name+"
				+"			\"</td>\"+"
				+"			\"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>部门:\"+"
				+"			\"</td>\"+"
				+"			\"<td style='padding-left:10px;color:#0099CC'>\"+"
				+"			data.depname+"
				+"			\"</td>\"+"
				+"			\"</tr>\"+"
				+"			\"<tr>\"+"
				+"			\"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>职务:\"+"
				+"			\"</td>\"+"
				+"			\"<td style='padding-left:10px;color:#0099CC'>\"+"
				+"			data.zhiwuname+"
				+"			\"</td>\"+"
				+"			\"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>地市:\"+"
				+"			\"</td>\"+"
				+"			\"<td style='padding-left:10px;color:#0099CC'>\"+"
				+"			data.dishiname+"
				+"			\"</td>\"+"
				+"			\"</tr>\";"
				+"			$(ele).appendTo($(\"#userinfo\"));"
				+"		}"
				+"	}"
				+"});"
				+"</script>"
		);
	}

}
