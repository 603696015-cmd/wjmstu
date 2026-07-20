package com.sopia.JTM.action.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Sheet;
import jxl.Workbook;

import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.impl.CourseDaoImpl;
import com.sopia.courseman.entities.Course;

public class JTMUtil {
	private static final Log logger = LogFactory.getLog(JTMUtil.class);
	public static CourseDao courseDao = new CourseDaoImpl();
	
	/**
	 * 同步课程维度
	 * @return
	 */
	public static boolean tongbuCourseWeidu(File file){
		boolean flag = false;
		Course course = null;
		jxl.Workbook rwb = null;
		StringBuffer sb = null;
		try {
			InputStream is = new FileInputStream(file);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					flag = false;
					String courseName = ss1.getCell(1, i).getContents().trim();//课程名称
					String bianhao = ss1.getCell(2, i).getContents().trim();//课程维度
					if(courseName!=null || !courseName.equals("")){
						//首先检查该课程名称在课程表中是否存在
						//存在的话，更新该课程的维度
						course = courseDao.getCourseByName(courseName);
						
						if(course != null){//课程存在
							if(course.getWeidu() == null || course.getWeidu().equals("")){//无维度信息
								if(bianhao!=null&&!bianhao.equals("")){
									course.setWeidu(String.valueOf(bianhao));
								}
							}else{//存在维度信息
								if(bianhao!=null&&!bianhao.equals("")){
									sb = new StringBuffer();
									sb.append(course.getWeidu()).append(",").append(String.valueOf(bianhao));
									course.setWeidu(sb.toString());
								}
							}
						}
						if(course != null && course.getId()!=0 && course.getWeidu()!=null && !course.getWeidu().equals("")){
							//更新数据库中维度字段
							courseDao.updateCourseWeiduById(course);
							flag = true;
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("同步课程维度失败", e); 
			return flag;
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		
		return flag;
	}

}
