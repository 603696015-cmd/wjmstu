package com.sopia.courseman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;

public interface ExampracDao {
	/**
	 * 练习列表页（已删除的不显示）
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public List<Examprac> listExampracDep(int begin, int end) throws ElException;
	/**
	 * 获取练习的数量（已删除的不显示）
	 */
	public int listExampracDepSize() throws ElException;
	/**
	 * 添加练习分配给部门
	 * @return
	 * @throws ElException
	 */
	public void addExamprac_dep(int pracid,int depid) throws ElException;
	/**
	 * 删除练习分配的所有部门
	 * @return
	 * @throws ElException
	 */
	public void delExamprac_dep(int pracid) throws ElException;
	/**
	 * 练习列表页（显示全部）
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public List<Examprac> listExampracAll(int begin, int end) throws ElException;
	/**
	 * 获取练习的数量（显示全部）
	 */
	public int listExampracAllSize() throws ElException;
	/**
	 * 练习答卷统计（概况）
	 * @return
	 * @throws ElException
	 */
	public Examprac getExampracQuizOverview(Examprac examprac,ELUser user) throws ElException;
	/**
	 * 练习答卷统计（详情）
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> getExampracQuizDetail(Examprac examprac,ELUser elUser,int pageNow,int pageSize) throws ElException;
	/**
	 * 练习答卷统计（详情）数量
	 * @return
	 * @throws ElException
	 */
	public int getExampracQuizDetailSize(Examprac examprac,ELUser elUser) throws ElException;
}
