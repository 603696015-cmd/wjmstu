package com.sopia.assistman.dao;

import java.util.List;

import com.sopia.assistman.entities.Poll;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.QuestionRanking;

public interface PollDao {
	/**
	 * 添加投票
	 * @param poll
	 * @throws ElException
	 */
	public void addPoll(Poll poll) throws ElException;
	/**
	 * 更新投票
	 * @param poll
	 * @throws ElException
	 */
	public void updatePoll(Poll poll) throws ElException;
	/**
	 * 查询my创建的投票信息
	 * @param poll
	 * @return
	 * @throws ElException
	 */
	public List<Poll> myPollList(Poll poll,int pageNow,int pageSize) throws ElException;
	/**
	 * 查询my创建的投票信息数量
	 * @param poll
	 * @return
	 * @throws ElException
	 */
	public int myPollListCount(Poll poll) throws ElException;
	/**
	 * 获取投票信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Poll getPoolById(int id) throws ElException;
	/**
	 * 删除投票
	 * @param poll
	 * @throws ElException
	 */
	public void deletePoll(int id) throws ElException;
	/**
	 * 添加投票学员信息
	 * @param pollid
	 * @param userid
	 * @throws ElException
	 */
	public void addPollUser(int pollid,int userid) throws ElException;
	/**
	 * 检测投票用户是否已经分配
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkPollUser(int pollid,int userid) throws ElException;
	/**
	 * 删除投票学员信息
	 * @param pollid
	 * @param userid
	 * @throws ElException
	 */
	public void deletePollUser(int pollid,int userid) throws ElException;
	/**
	 * 投票审核列表查询
	 * @param tree	部门树
	 * @param sublibs 是否包含下级
	 * @param poll
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Poll> pollShList(ElNode tree,int sublibs,Poll poll,int pageNow,int pageSize) throws ElException;
	/**
	 * 投票审核列表查询数量
	 * @param tree	部门树
	 * @param sublibs 是否包含下级
	 * @param poll
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int pollShListCount(ElNode tree,int sublibs,Poll poll) throws ElException;
	/**
	 * 更新投票状态
	 * @param pollid
	 * @param status
	 * @throws ElException
	 */
	public void updatePollStatus(int pollid,int status) throws ElException;
	/**
	 * 学员分配的投票列表
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Poll> studyPollList(int userid,int pageNow,int pageSize) throws ElException;
	/**
	 * 检测学员是否已经投票
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int checkPollQuizinfo(int pollid,int userid) throws ElException;
	/**
	 * 添加学员投票的结果
	 * @param pollid
	 * @param userid
	 * @param answer
	 * @throws ElException
	 */
	public void addPollQuizinfo(int pollid,int userid,int answer) throws ElException;
	/**
	 * 检测学员是否已经投票
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkPollQuizinfo(int pollid,int userid,int answer) throws ElException;
	/**
	 * 投票统计
	 * @param poll
	 * @return
	 * @throws ElException
	 */
	public QuestionRanking pollResult(Poll poll) throws ElException;
	
	/**
	 * 问卷统计
	 * @param questionid
	 * @return
	 * @throws ElException
	 */
	public QuestionRanking questionnaireResult(int questionid)throws ElException;
	/**
	 * 修改用户为已投票
	 * @param pollid
	 * @param userid
	 * @throws ElException
	 */
	public void updateUserIsPoll(int pollid,int userid) throws ElException;
	/**
	 * 检测用户是否已经投票
	 * @param pollid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserIsPoll(int pollid,int userid) throws ElException;
}
