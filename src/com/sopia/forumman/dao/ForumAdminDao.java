package com.sopia.forumman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.forumman.entities.Topic;

public interface ForumAdminDao {
	public List<ForumBlockType> listFbtypes() throws ElException;

	public void addFbtype(ForumBlockType fbtype) throws ElException;

	public void alterFbtype(ForumBlockType fbtype) throws ElException;

	public void sortFbtype(int id, int sorttype) throws ElException;

	public ForumBlockType getFbtypeByid(int id) throws ElException;

	public void deleteFbtype(int id) throws ElException;

	public List<ForumBlock> listFbsByFbtid(int fbtid) throws ElException;

	public void addFblock(ForumBlock fblock) throws ElException;
	public void deleteFblock(int fblock) throws ElException;

	public ForumBlock getFblockById(int id) throws ElException;

	public void alterFblock(ForumBlock fblock) throws ElException;

	public List<Forum> listForumsByZx(int pageNow, int pageSize)
	throws ElException;
	
	public List<Forum> listForumsByZxByDept(int pageNow, int pageSize,int deptid)
	throws ElException;
	
	public List<Forum> listShForums(String fbts, int pageNow, int pageSize)
	throws ElException;
	public int listShForumsCount(String fbts)
	throws ElException;

	public List<Forum> listForumsByJh(int pageNow, int pageSize)
	throws ElException;

	public List<Forum> listForumsByRm(int pageNow, int pageSize)
	throws ElException;
	public List<Forum> listForumsByRm(int pageNow, int pageSize,int depid)
	throws ElException;
	
	public List<Forum> listForumsByRmByDept(int pageNow, int pageSize,int deptid)
	throws ElException;

	public List<Forum> listForumsByBid(int bid, int pageNow, int pageSize)
	throws ElException;
	
	public List<Forum> listForumsByBid(int bid,String title, int pageNow, int pageSize)
	throws ElException;

	public int listForumsByBidCount(int bid,String title)
	throws ElException;
	
	public int listForumsByBidSize(int bid) throws ElException;

	public void addForum(Forum forum) throws ElException;

	public void alterForum(Forum forum) throws ElException;

	public List<Forum> listForumsByRmBid(int bid, int pageNow, int pageSize)
	throws ElException;

	public List<Forum> listForumsByJhBid(int bid, int pageNow, int pageSize)
	throws ElException;

	public Forum getForumsByid(int id) throws ElException;

	public List<Topic> listTopicByFid(int fid, int pageNow, int pageSize)
	throws ElException;

	public int listTopicByIdSize(int fid) throws ElException;

	public void addTopic(Topic topic) throws ElException;

	public List<Forum> listForumsByManager(int userid, String title, int bid,
			int pageNow, int pageSize) throws ElException;

	public int listForumsByManagerSize(int userid, String title, int bid)
	throws ElException;

	public void forumJhSet(int fid) throws ElException;

	public void forumDelete(int id) throws ElException;

	public void readtimeAdd(int id) throws ElException;

	public void receipttimeAdd(int id) throws ElException;

	public List<Forum> listForumsByUid(int userid, String title, int pageNow,
			int pageSize) throws ElException;

	public int listForumsByUidSize(int userid, String title) throws ElException;

	public List<Forum> listShForums(int userid, int pageNow, int pageSize)
	throws ElException;
	public List<Forum> listShForums(int userid,int fblockid, int pageNow, int pageSize)
	throws ElException;
	public int listShForumsCount(int userid,int fblockid)
	throws ElException;
	public int listShForumsSize(int userid) throws ElException;
	
	public int listShForumsSize(int userid,int fblockid) throws ElException;

	public void shForumset(int id) throws ElException;

	public void deleteTopic(int topicid) throws ElException;

	public void receipttimeDelete(int id) throws ElException;

	/**
	 * 获取使用权限的用户
	 * @param type
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getOpUsers(String type, int id)  throws ElException;

	/**
	 * 判断权限是否存在
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @return
	 * @throws ElException
	 */
	public boolean checkOpUsers(String type, int userid, int ctypeid) throws ElException;

	/**
	 * 添加权限
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void addOpusers(String type, int userid, int ctypeid) throws ElException;

	/**
	 * 删除版面管理可使用人员
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void deleteOpusers(String optype, int userId, int fblockId) throws ElException;

	/**
	 * 获取有权限或共享的版面
	 * @param userid
	 * @param isShared
	 * @return
	 * @throws ElException
	 */
	public List<ForumBlock> fblockByPerOrShare(int fbtid, int userid,boolean isShared) throws ElException;

	/**
	 * 有权限的论坛帖子列表
	 * @param userId
	 * @param title
	 * @param bid
	 * @param pageNow
	 * @param pageSize
	 * @param isshared
	 * @return
	 * @throws ElException
	 */
	public List<Forum> listForumsByManagerPer(int userId, String title, int bid,int role , int pageNow, int pageSize, boolean isshared) throws ElException;

	/**
	 * 有权限的论坛帖子列表大小
	 * @param userId
	 * @param title
	 * @param bid
	 * @param isshared
	 * @return
	 * @throws ElException
	 */
	public int listForumsByManagerPerSize(int userId, String title, int bid,int role , boolean isshared) throws ElException;
	//本版最新的精华帖子
	public List<Forum> listForumsByJhBid2(int bid, int pageNow, int pageSize)
	throws ElException;
	//最新的帖子
	public List<Forum> listForumsByZx2(int pageNow, int pageSize)
	throws ElException;
	//最新的精华帖子
	public List<Forum> listForumsByJh2(int pageNow, int pageSize)
	throws ElException;
	//搜索帖子
	public List<Forum> searchlistForums(int pageNow, int pageSize,String title,String str) throws ElException;
	public int searchlistForumsSize(String title) throws ElException;
	public List<Forum> searchlistForumsByJh(int pageNow, int pageSize)throws ElException;
	public List<Forum> searchlistForumsByRm(int pageNow, int pageSize)throws ElException;
	//论坛组合搜索
	public List<Forum> listCombinationForum(Forum forum,int pageNow,int pageSize)throws ElException;
	public int listCombinationForumCount(Forum forum,int pageNow,int pageSize)throws ElException;
	//论坛类型搜索
	public List<ForumBlockType> getForum()throws ElException;
	//搜索帖子
	public List<Forum> searchlistForums(int pageNow, int pageSize,Forum forum,String str) throws ElException;
	public int searchlistForumsSize(Forum forum) throws ElException;
	/**
	 * 查询我的所有回帖
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Topic> myListTopic(int userid, int pageNow, int pageSize)
	throws ElException;
	/**
	 * 查询我的所有回帖数量
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int myListTopicCount(int userid)
	throws ElException;
	/**
	 * 更新回帖的状态
	 * @param newsid
	 * @param status_tow
	 * @throws ElException
	 */
	public void upTopicValid(int topicId, int valid) throws ElException;
	/**
	 * 查询所有回帖(已审核,左树右表)
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Topic> listTopic(String fbt, int pageNow, int pageSize)
	throws ElException;
	/**
	 * 查询所有回帖数量(已审核,左树右表)
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int listTopicCount(String fbt) throws ElException;
	/**
	 * 根据帖子获取所有回复(已审核的)
	 * @param fid
	 * @param pageNow
	 * @param pageSize
	 * @param disValid
	 * @return
	 * @throws ElException
	 */
	public List<Topic> listTopicByFid(int fid, int pageNow, int pageSize,int disValid)
	throws ElException;
	/**
	 * 根据帖子获取所有回复数量(已审核的)
	 * @param fid
	 * @param disvalid
	 * @return
	 * @throws ElException
	 */
	public int listTopicByIdSize(int fid,int disvalid) throws ElException;
	/**
	 * 帖子的回复数减1
	 * @param id
	 * @throws ElException
	 */
	public void receipttimeDel1(int id) throws ElException;
	/**
	 * 根据id获取回贴信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Topic getTopicById(int id) throws ElException;
	/**
	 * 删除用户所有版块权限
	 * @param optype
	 * @param userId
	 * @throws ElException
	 */
	public void deleteOpusers(String optype, int userId) throws ElException;
	/**
	 * 添加帖子回复（不用户审核）
	 * @param topic
	 * @throws ElException
	 */
	public void addTopic2(Topic topic) throws ElException;
	public List<Forum> listForumsByBid_list(int bid,String title, int pageNow, int pageSize)
	throws ElException;
	//最新的精华帖子
	public List<Forum> listForumsByJhBid2_list(int bid, int pageNow, int pageSize)
	throws ElException;
	public List<Forum> listForumsByRmBid_list(int bid, int pageNow, int pageSize)
	throws ElException;
	
	
	/**
	 * 论坛版块与用户论坛级别进行匹配
	 * @throws ElException
	 */
	public void addFblock_huiyuanjibie(int fblockid,int luntanjibieid) throws ElException;
	
	/**
	 * 验证该用户的论坛级别是否有权限发布帖子
	 * @param fblockid
	 * @param luntanjibieid
	 * @return
	 * @throws ElException
	 */
	public boolean checkLuntanjibieHasSelectForumBlock(int fblockid,int luntanjibieid) throws ElException;
	
	
	public List<Forum> newVersionGetForums(int pageNow,int pageSize) throws ElException;
	
	//-----------------外联-----------------
	/**
	 * 查询我的所有回帖
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Topic> myListTopic_(int userid, int pageNow, int pageSize,int valid)
	throws ElException;
	/**
	 * 查询我的所有回帖数量
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int myListTopicCount_(int userid,int valid)
	throws ElException;
	//-----------------外联结束-----------------
	//wsj----------------------------------------
	public List<Forum> listForumsList_wsj(int bid,int pageNow, int pageSize)throws ElException;
	
	public List<Forum> getTjForums(int bid,int hot)throws ElException;
	public List<Forum> getTjForums(int hot)throws ElException;
}
