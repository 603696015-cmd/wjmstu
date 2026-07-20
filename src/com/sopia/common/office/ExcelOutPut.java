package com.sopia.common.office;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.PfmsUser;

public class ExcelOutPut {
	private static final Log logger = LogFactory.getLog(ExcelOutPut.class);

	/**
	 * Description: 生成工作簿
	 * 
	 * @Version1.0 2012-5-12 上午09:03:12 by 闻益舜（wenyishun110@163.com）创建
	 * @param name 工作簿名称
	 * @param os
	 * @param titles { "ID", "用户名", "姓名", "密码", "部门" };
	 * @param classname ELUser.class.getName()
	 * @param objs List
	 * @param attrs { "id", "username", "realname", "password" };
	 */
	public void writeExcel(String name,OutputStream os, String titles[], String classname,
			List objs, String attrs[]) {
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet(name, 0);
			// 写标题，第一行
			if (titles != null)
				for (int i = 0; i < titles.length; i++) {
					Label ci = new Label(i, 0, titles[i]);
					ws.addCell(ci);
				}
			// 写内容，第二行开始
			if (null != objs)
				for (int i = 0; i < objs.size(); i++) {
					writeExcelContent(ws, classname, objs.get(i), attrs, i + 1,
							0);
				}
			wwb.write(); // 写入Exel工作表
		} catch (Exception ex) {
			logger.error("生成工作薄失败", ex);
		} finally {
			try {
				if (wwb != null)
					wwb.close();// 关闭Excel工作薄对象
			} catch (Exception e) {
				logger.error("关闭工作薄失败", e);
			}
		}
	}

	public void writeExcelContent(WritableSheet ws, String classname,
			Object obj, String attrs[], int row, int col) throws Exception {
		Class objclazz = Class.forName(classname);
		if (attrs != null)
			for (int i = 0; i < attrs.length; i++) {
				String attr = attrs[i];
				if(attr.indexOf(".")>=0){
					Object v = getSimpleVal(attr.split("[.]")[0], obj, objclazz);
					if(v!=null){
						v = getSimpleVal(attr.split("[.]")[1],
								v, v.getClass());
					}
					String vl = v == null ? "" : v.toString();
					
					Label ci = new Label(col + i, row, vl);
					ws.addCell(ci);
				}else {
					Object v = getSimpleVal(attr, obj, objclazz);
					String vl = v == null ? "" : v.toString();
					Label ci = new Label(col + i, row, vl);
					ws.addCell(ci);
				}
			}

	}
	
	/**
	 * 会员列表的导出
	 * @param ws
	 * @param classname
	 * @param obj
	 * @param attrs
	 * @param row
	 * @param col
	 * @throws Exception
	 */
	public void writeExcelContent1(WritableSheet ws, String classname,
			Object obj, String attrs[], int row, int col) throws Exception {
		Class objclazz = Class.forName(classname);
		if (attrs != null)
			for (int i = 0; i < attrs.length; i++) {
				String attr = attrs[i];
				if(attr.indexOf(".")>=0){
					Object v = getSimpleVal(attr.split("[.]")[0], obj, objclazz);
					if(v!=null){
						v = getSimpleVal(attr.split("[.]")[1],
								v, v.getClass());
					}
					String vl = v == null ? "" : v.toString();
					
					Label ci = new Label(col + i, row, vl);
					ws.addCell(ci);
				}else {
					
					Object v = getSimpleVal(attr, obj, objclazz);
					//判断返回时对象还是值
					if(v == null){
						
						try{
							
							PfmsUser pfmsUser = (PfmsUser)obj;
							v = getSimpleVal(attr, pfmsUser.getUser(), Class.forName(ELUser.class.getName()));
							
						}catch(ClassCastException ex){
							ex.printStackTrace();
						}
						
					}
					
					String vl = v == null ? "" : v.toString();
					Label ci = new Label(col + i, row, vl);
					ws.addCell(ci);
				}
			}

	}

	protected Object getSimpleVal(String attr, Object obj, Class clazz) throws ElException {
		Object v = null;
		try {
			Method innerm_get = clazz.getMethod("get"
					+ attr.substring(0, 1).toUpperCase() + attr.substring(1),
					new Class[0]);
			v = innerm_get.invoke(obj, new Class[0]);
		} catch (Exception e) {
			logger.error("getSimpleVal失败", e);
			throw new ElException(e);
		}
		return v;
	}
	
	
//	public static void main(String[] args) {
//		try {
//			OutputStream fos = new FileOutputStream(new File("F:\\text.xls"));
//			List<ELUser> eus = new ArrayList<ELUser>();
//			for (int i = 0; i <  5535; i++) {
//				eus.add(new ELUser(i, "admin"+i, "等等"+i ));
//			}
//			String ts[] = { "ID", "用户名", "姓名", "密码", "部门" };
//			String attrs[] = { "id", "username", "realname", "passtword" };
//			new ExcelOutPut().writeExcel("用户表",fos, ts, ELUser.class.getName(), eus,
//					attrs);
//			fos.close();
//		} catch (Exception e) {
//			logger.error(e);
//		}
//	}
}
