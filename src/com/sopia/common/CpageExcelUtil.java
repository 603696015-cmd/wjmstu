package com.sopia.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.sopia.courseman.dao.impl.CoursePageDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 课程章节导入工具
 * @author Administrator
 *
 */
public class CpageExcelUtil {
	public static void writeCPage(File source, int cid) throws ElException,
			Exception {
		InputStream is = new FileInputStream(source);
		jxl.Workbook rwb = Workbook.getWorkbook(is);
		Sheet ss[] = rwb.getSheets();
		if (null != ss && ss.length > 0) {
			Sheet ss1 = ss[0];
			for (int i = 1; i < ss1.getRows(); i++) {
				// for (int j = 0; j < ss1.getColumns(); j++) {
				// String cellContent = ss1.getCell(0, i).getContents();
				// if (null != cellContent && !"".equals(cellContent)) {
				// 章节名称 章节属性（是章还是节） 学习时长 网页类型 课件/视频地址
				if (null == ss1.getCell(0, i).getContents()
						|| "".equals(ss1.getCell(0, i).getContents()))
					continue;
				CoursePage cpage = new CoursePage();
				cpage.setTitle(ss1.getCell(0, i).getContents());
				cpage
						.setProperty("章"
								.equals(ss1.getCell(1, i).getContents()) ? 0
								: ("节".equals(ss1.getCell(1, i).getContents()) ? 1
										: 0));
				cpage.setDuring(getIntValue(ss1.getCell(2, i).getContents()));
				cpage.setType("三分屏".equals(ss1.getCell(3, i).getContents()) ? 3
						: ("纯视频".equals(ss1.getCell(3, i).getContents()) ? 1
								: ("图文型".equals(ss1.getCell(3, i).getContents()) ? 0
										: ("视频讲义".equals(ss1.getCell(3, i).getContents()) ? 2
												: 0))));//图文型，视频讲义
				cpage.setPage_url(ss1.getCell(4, i).getContents());
				cpage.setCourse(new Course(cid));
				cpage.setQueryTime(5);

				new CoursePageDaoImpl().addCoursePage(cpage);
				// }
				// }
			}
		}
	}

	private static int getIntValue(String i) {

		return i == null ? 0 : ("".equals(i.trim()) ? 0 : new Integer(i));
	}/*
		 * public static void main(String[] args) throws Exception{
		 * writeCPage(new File("E:/elearning/章节批量.xls"), 1); }
		 */
}
