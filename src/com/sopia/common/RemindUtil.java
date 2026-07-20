package com.sopia.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.dao.MessageDao;
import com.sopia.newsandmess.dao.impl.MessageDaoImpl;
import com.sopia.newsandmess.entities.Message;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.Tags;

public class RemindUtil {
	public static String tablename = "TXGL";// 表名
	public static String column = "TXGL_TXSJ";// 列名==提醒时间
	public static String title = "TXGL_TXZT";// 主题
	public static String content = "TXGL_TXNR";// 内容
	public static String receives = "TXGL_JSR";// 接收人
	// public static Map<String,List<Tags>> list_tags_map;
	public static List<Tags> list_tags;
	public static List<Map<String, String>> list_designe;
	public static MessageDao messageDao = new MessageDaoImpl();
	public static UserDao userDao = new UserDaoImpl();

	//	
	public static void load() throws ElException {
		TagsDao tagsDao = new TagsDaoImpl();
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_designe = tagsDao.selectAll(list_tags, tablename);
	}

	// 获取当前时间字符类型
	public static String longToString(String format) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat smp = new SimpleDateFormat(format);
		return smp.format(date);
	}
	//判断字符串日期格式
	public boolean isDateStringValid(String date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		// 输入对象不为空

		try {
			sdf.parse(date);
			return true;
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}

	}

	@SuppressWarnings("static-access")
	public void execute() {
		try {
			if (list_designe == null) {
				this.load();
			}
			for (int i = 0; i < list_designe.size(); i++) {
				Message mess = null;
				if (list_designe.get(i).get(column) != null
						&& !list_designe.get(i).get(column).equals("")
						&& list_designe.get(i).get(receives) != null
						&& !list_designe.get(i).get(receives).equals("")) {
					String[] arr = list_designe.get(i).get(receives).split(",");
					if (arr != null) {
						for (int j = 0; j < arr.length; j++) {
							if ((isDateStringValid(list_designe.get(i).get(column),"yyyy-MM-dd HH:mm:ss")
									&& list_designe.get(i).get(column).equals(
											longToString("yyyy-MM-dd HH:mm:ss"))) || 
											(isDateStringValid(list_designe.get(i).get(column),"yyyy-MM-dd")
													&& list_designe.get(i).get(column).equals(
															longToString("yyyy-MM-dd")))) {
								mess = new Message();
								mess.setMess_title(list_designe.get(i).get(
										title));
								mess.setMess_content(list_designe.get(i).get(
										content));
								mess.setMess_from(new ELUser(0));
								mess.setMess_to(new ELUser(Integer
										.parseInt(arr[j])));
								messageDao.insertMess(mess);
								System.out.println("--insert success--");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
