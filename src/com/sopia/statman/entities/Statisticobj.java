package com.sopia.statman.entities;

import java.util.List;

import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;


/**
 * 统计装载实体
 * @author Administrator
 *
 */
public class Statisticobj {
	private int currentCount;//当前数(学习，考试，练习，课程、章节练习)
	private int dayCount;//今天数(学习，考试，练习，课程、章节练习)
	private int YesterdayCount;//昨天数(学习，考试，练习，课程、章节练习)
	private int weekCount;//本周数(学习，考试，练习，课程、章节练习)
	private Queryobj queryobj;//查询装载类
	private List<Queryobj> queryobjs;//展示数据
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public int getDayCount() {
		return dayCount;
	}
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	public int getYesterdayCount() {
		return YesterdayCount;
	}
	public void setYesterdayCount(int yesterdayCount) {
		YesterdayCount = yesterdayCount;
	}
	public int getWeekCount() {
		return weekCount;
	}
	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}
	public Queryobj getQueryobj() {
		return queryobj;
	}
	public void setQueryobj(Queryobj queryobj) {
		this.queryobj = queryobj;
	}
	public List<Queryobj> getQueryobjs() {
		return queryobjs;
	}
	public void setQueryobjs(List<Queryobj> queryobjs) {
		this.queryobjs = queryobjs;
	}
}
