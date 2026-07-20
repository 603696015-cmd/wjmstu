package com.sopia.courseman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;

public interface ExamDao {
	/**
	 * 考试答卷统计（概况）
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getExamQuizOverview(ExamRoom examRoom,ELUser user) throws ElException;
	/**
	 * 考试答卷统计（详情）
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> getExamQuizDetail(ExamRoom examRoom,ELUser elUser,int pageNow,int pageSize) throws ElException;
	/**
	 * 考试答卷统计（详情）数量
	 * @return
	 * @throws ElException
	 */
	public int getExamQuizDetailSize(ExamRoom examRoom,ELUser elUser) throws ElException;
}
