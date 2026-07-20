package com.sopia.forumman.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.ScoreOperate;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.forumman.entities.Topic;

public class ForumAdminAction extends BaseAction{
	private List<ForumBlockType> fbtypes;
	private ForumBlockType fbtype;
	private ForumAdminDao forumAdminDao;
	private ForumBlock fblock;
	private DepartmentDao departmentDao;
	private List<ELUser> elUsers;
	private ELUser elUser;
	private int sub_department;
	private Department department;
	private Department depTree;
	private List<Forum> forums;
	private Forum forum ;
	private String optype;
	private UserDao userDao;
	private List<Topic> topics;
	private IndexDataUtil indexDataUtil;
	private List<BaseDatat> luntanjibies;
	private int fblockid;

	public int getFblockid() {
		return fblockid;
	}

	public void setFblockid(int fblockid) {
		this.fblockid = fblockid;
	}

	public List<BaseDatat> getLuntanjibies() {
		return luntanjibies;
	}

	public void setLuntanjibies(List<BaseDatat> luntanjibies) {
		this.luntanjibies = luntanjibies;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	public ForumAdminDao getForumAdminDao() {
		return forumAdminDao;
	}

	public void setForumAdminDao(ForumAdminDao forumAdminDao) {
		this.forumAdminDao = forumAdminDao;
	}

	public List<ForumBlockType> getFbtypes() {
		return fbtypes;
	}

	public void setFbtypes(List<ForumBlockType> fbtypes) {
		this.fbtypes = fbtypes;
	}

	public String forum_blocktype_list() throws ElException {
		fbtypes = forumAdminDao.listFbtypes();

		return "forum_blocktype_list";
	}

	public String forum_blocktype_addInit() throws ElException {

		return "forum_blocktype_add";
	}

	public String forum_blocktype_add() throws ElException {
		forumAdminDao.addFbtype(fbtype);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_FORUMBLOCKTYPE,
				ElLoggerConstants.LOG_TYPE_ADD, fbtype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,fbtype.getId());
		return "forum_blocktype_add_success";
	}

	public String forum_blocktype_downSort() throws ElException {
		forumAdminDao.sortFbtype(fbtype.getId(), ElConstants.SORT_DOWN);
		return "forum_blocktype_downSort";
	}

	public String forum_blocktype_upSort() throws ElException {

		forumAdminDao.sortFbtype(fbtype.getId(), ElConstants.SORT_UP);
		return "forum_blocktype_upSort";
	}

	public String forum_blocktype_alterInit() throws ElException {
		fbtype = forumAdminDao.getFbtypeByid(fbtype.getId());
		return "forum_blocktype_alter";
	}

	public String forum_blocktype_alter() throws ElException {
		forumAdminDao.alterFbtype(fbtype);
		fbtype=forumAdminDao.getFbtypeByid(fbtype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_FORUMBLOCKTYPE,
				ElLoggerConstants.LOG_TYPE_ALTER, fbtype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,fbtype.getId());
		return "forum_blocktype_alter_success";
	}

	public String forum_blocktype_delete() throws ElException {
		forumAdminDao.deleteFbtype(fbtype.getId());
		fbtype=forumAdminDao.getFbtypeByid(fbtype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_FORUMBLOCKTYPE,
				ElLoggerConstants.LOG_TYPE_DELETE, fbtype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,fbtype.getId());
		return "forum_blocktype_delete";
	}

	// 版面管理
	public String forum_searchUsersInit() throws ElException {
		depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "forum_searchUsers";
	}
	public String forum_searchUserslist() throws ElException {
//		elUsers = userDao.getUserByDepId(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
//		count = userDao.getUserByDepIdSize(department.getId(),
//				sub_department, elUser);

		return "forum_searchUserslist";
	}

	public String forum_block_list() throws ElException {
//		fbtypes = forumAdminDao.listFbtypesWithBlocks();
		fbtypes = forumAdminDao.listFbtypes();
		if(null!=fbtypes){
			List<ForumBlock> list = null;
			for (int i = 0; i < fbtypes.size(); i++) {
//				fbtypes.get(i).setFblocks(forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
				//如果是系统管理员
				if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
					list = forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId());
//					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID), true);
				}else{
					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID), false);
				}
				fbtypes.get(i).setFblocks(list); 
			}
		}
		return "forum_block_list";
	}

	public String forum_block_addInit() throws ElException {
		luntanjibies=userDao.getBaseDatatByTypeidc(6);
		fbtypes = forumAdminDao.listFbtypes();
		
//		List<Integer> checked=userDao.getForumUseBaseDataIdByfblockid(fblock.getId());
//		getRequest().setAttribute("checked", checked);
		return "forum_block_add";
	}

	public String forum_block_add() throws ElException {
		if(fblock!=null)
			if(fblock.getManager()==null||fblock.getManager().getId()==0) {
				fblock.setManager(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
			}
		forumAdminDao.addFblock(fblock); 
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_FORUMBLOCK,
				ElLoggerConstants.LOG_TYPE_ADD, fblock.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,fblock.getId());
		return "forum_block_add_success";
	}

	public String forum_block_alterInit() throws ElException {
		luntanjibies=userDao.getBaseDatatByTypeidc(6);
		fblock = forumAdminDao.getFblockById(fblock.getId());
//		fbtypes = forumAdminDao.listFbtypesWithBlocks();
		fbtypes = forumAdminDao.listFbtypes();
		if(null!=fbtypes){
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		List<Integer> checked=userDao.getForumUseBaseDataIdByfblockid(fblock.getId());
		getRequest().setAttribute("checked", checked);
		//增加可使用权限
		fblock.setUseusers(forumAdminDao.getOpUsers("FBLOCK_USE_TYPE", fblock.getId()));
		return "forum_block_alter";
	}

	public String forum_block_alter() throws ElException {
		forumAdminDao.alterFblock(fblock);
//		forumAdminDao.addFblock_huiyuanjibie(fblock);
		if (null != fblock.getUseusers()) {
			for (int i = 0; i < fblock.getUseusers().size(); i++) {
				if (!forumAdminDao.checkOpUsers("FBLOCK_USE_TYPE", fblock.getUseusers()
						.get(i).getId(), fblock.getId()))
					forumAdminDao.addOpusers("FBLOCK_USE_TYPE", fblock.getUseusers()
							.get(i).getId(), fblock.getId());
			}
		}
		fblock=forumAdminDao.getFblockById(fblock.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_FORUMBLOCK,
				ElLoggerConstants.LOG_TYPE_ALTER, fblock.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,fblock.getId());
		return "forum_block_alter_success";
	}

	public String fblock_delete_user() throws ElException {
		forumAdminDao.deleteOpusers(optype, elUser.getId(), fblock.getId());
		return null;
	}

	public String forum_block_delete() throws ElException {
//		fbtypes = forumAdminDao.listFbtypesWithBlocks();
		if(null!=fblock){
			forumAdminDao.deleteFblock(fblock.getId());
		}
		fbtypes = forumAdminDao.listFbtypes();
		if(null!=fbtypes){
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		fblock=forumAdminDao.getFblockById(fblock.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_FORUMBLOCK,
				ElLoggerConstants.LOG_TYPE_DELETE, fblock.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,fblock.getId());
		return "forum_block_delete";
	}
	public String forum_list_byblockid() throws ElException {
		String title = (forum==null)?"":forum.getTitle();
//		getPageSize()= getPageSize()==0?10:getPageSize();
		int bid = fblock==null?0:fblock.getId(); //模块id
//		fbtypes = forumAdminDao.listFbtypesWithBlocks();

		fbtypes = forumAdminDao.listFbtypes();
		int userId = getSessionIntValue(ElConstants.SESSION_USERID);

		if(null!=fbtypes){
			List<ForumBlock> list = null;
			for (int i = 0; i < fbtypes.size(); i++) {
				//如果是系统管理员
				if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
					list = forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId());
//					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), userId, true);
				}else{
					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), userId, false);
				}
				fbtypes.get(i).setFblocks(list);
			}
		}
//		forums = forumAdminDao.listForumsByManager(userId, title,bid, getPageNow(), getPageSize());
//		count = forumAdminDao.listForumsByManagerSize(userId, title,bid);
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			//在listForumsByManagerPer里isshared参数功能没有。 并在把此当作超级管理员与其他帐号的区别了。需优化
			forums = forumAdminDao.listForumsByManagerPer(userId, title, bid,getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(), getPageSize(),false);
			count = forumAdminDao.listForumsByManagerPerSize(userId, title, bid,getSessionIntValue(ElConstants.SESSION_ROLE), false);
//		}else{
//			forums = forumAdminDao.listForumsByManagerPer(userId, title, bid,getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(), getPageSize(),false);
//			count = forumAdminDao.listForumsByManagerPerSize(userId, title, bid,getSessionIntValue(ElConstants.SESSION_ROLE), false);
//		}

		return "forum_list_byblockid";
	}
	public String forum_jhset() throws ElException {
		if(null!=forums){
			for (int i = 0; i < forums.size(); i++) {
				int forumid = forums.get(i).getId();
				Forum f = forumAdminDao.getForumsByid(forumid);
				forumAdminDao.forumJhSet(forumid);
				int userid = f.getCreater().getId();
//				ScoreOperate				ScoreOperate.setScore(userid, ElConstants.SCORE_FORUM_JH);
			}
			indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
		}
		return "forum_jhset";
	}
	public String forum_delete() throws ElException {
		if(null!=forums){
			for (int i = 0; i < forums.size(); i++) {
				int forumid = forums.get(i).getId();
				Forum f = forumAdminDao.getForumsByid(forumid);
				forumAdminDao.forumDelete(forums.get(i).getId());
				int userid = f.getCreater().getId();
				ScoreOperate.setScore(userid, ElConstants.JIAN_FORUM_DO);
				forum=forumAdminDao.getForumsByid(forums.get(i).getId());
				ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_FORUM,
						ElLoggerConstants.LOG_TYPE_DELETE, forum.getTitle(),
						ElLoggerConstants.LOG_RES_SUCC,forum.getId());
			}
		}
		//刷新首页帖子模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
		return "forum_delete";
	}
	public String forum_list_byuid() throws ElException {
		String title = (forum==null)?"":forum.getTitle();
//		getPageSize()= getPageSize()==0?getPageSize()=10:getPageSize();
		forums = forumAdminDao.listForumsByUid(getSessionIntValue(ElConstants.SESSION_USERID), title, getPageNow(), getPageSize());
		count = forumAdminDao.listForumsByUidSize(getSessionIntValue(ElConstants.SESSION_USERID), title);
		return "forum_list_byuid";
	}
	public String forum_alterInit() throws ElException {
//		fbtypes = forumAdminDao.listFbtypesWithBlocks()	;
		fbtypes = forumAdminDao.listFbtypes();
		if(null!=fbtypes){
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		forum = forumAdminDao.getForumsByid(forum.getId());
		return "forum_alter";
	}
	public String forum_alter() throws ElException {
		forumAdminDao.alterForum(forum);
		//刷新首页帖子模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
		forum=forumAdminDao.getForumsByid(forum.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_FORUM,
				ElLoggerConstants.LOG_TYPE_ALTER, forum.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,forum.getId());
		return "forum_alter";
	}

	public String forum_deletebyuid() throws ElException {
		if(null!=forum){
			forumAdminDao.forumDelete(forum.getId());
		}
		//刷新首页帖子模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
		return "forum_deletebyuid";
	}

	public String forum_shlist()throws ElException{ 
		fbtypes = forumAdminDao.listFbtypes();
		int userId = getSessionIntValue(ElConstants.SESSION_USERID);
		String fbt="";
		if(null!=fbtypes){
			List<ForumBlock> list = null;
			for (int i = 0; i < fbtypes.size(); i++) {
				//如果是系统管理员
				if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
					list = forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId());
//					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), userId, true);
				}else{
					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), userId, false);

				}
				fbtypes.get(i).setFblocks(list);
				for(ForumBlock fblock:list){
					fbt+=fblock.getId()+",";
				}
			}

		}
		/*if(getSessionIntValue(ElConstants.SESSION_ROLE)==1)
			fbt=fbt.substring(0,fbt.lastIndexOf(","));
		else
			fbt=fbt.substring(fbt.indexOf(",")+1,fbt.lastIndexOf(","));*/
		if(!fbt.equals("")){
			fbt="and fm.fblockid in("+fbt.substring(0,fbt.lastIndexOf(","))+")"; 
		}
		if(getRequest().getParameter("str")!=null){
			if(getRequest().getParameter("str").equals("byfblockid")){
				forums = forumAdminDao.listShForums(getSessionIntValue(ElConstants.SESSION_USERID),fblock.getId(), getPageNow(), getPageSize());
				count=forumAdminDao.listShForumsCount(getSessionIntValue(ElConstants.SESSION_USERID),fblock.getId());
			}
		}else{
			if(fbt.equals("")){
				return "forum_shlist";
			}
			forums = forumAdminDao.listShForums(fbt, getPageNow(), getPageSize());
			count=forumAdminDao.listShForumsCount(fbt);
		}
		//count = forumAdminDao.listShForumsSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "forum_shlist";
	}
	
	
	/**
	 * 所有回帖(帖子已审核通过的)
	 * @return
	 * @throws ElException
	 */
	public String TopicList() throws ElException{
		fbtypes = forumAdminDao.listFbtypes();//查出所有版块类别
		int userId = getSessionIntValue(ElConstants.SESSION_USERID);
		String fbt="";
		if(null!=fbtypes){
			List<ForumBlock> list = null;
			for (int i = 0; i < fbtypes.size(); i++) {
				//如果是系统管理员
				if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
					list = forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId());
//					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), userId, true);
				}else{
					list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(), userId, false);
				}
				//list为某1版块类别的所有版块
				fbtypes.get(i).setFblocks(list);
				if(fblock==null){
					for(ForumBlock fblock:list){
						fbt+=fblock.getId()+",";
					}
				}
			}
		}
		
		if(fblock==null){
			//截断最后一个逗号
			if(fbt.length()>1)
			fbt=fbt.substring(0,fbt.length()-1);
		}else{
			fbt=fblock.getId()+"";
		}
		if(fbt==null||"".equals(fbt)||fbtypes==null){
			return "TopicList";
		}
		//fbt为版块的id集合
		topics=forumAdminDao.listTopic(fbt, getPageNow(), getPageSize());
		count=forumAdminDao.listTopicCount(fbt);
		return "TopicList";
	}

	/**
	 * 帖子审核
	 * @return
	 * @throws ElException
	 */
	public String forum_sh()throws ElException{
		if(null!=forums){
			for (int i = 0; i < forums.size(); i++) {
				forumAdminDao.shForumset(forums.get(i).getId());
			}
		}
		//刷新首页帖子模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
		return "forum_shlist";
	}
	public String combinationSearchforumInit()throws ElException{
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		return "combinationSearchforumInit";
	}
	public String combinationSearchforum()throws ElException{
		String newid="";
		String fbtid=getRequest().getParameter("fbtid");
		if(fbtid!=null){
			if(fbtid.indexOf("--")>=0){
				newid=fbtid.substring(2,fbtid.length());
				ForumBlock forumBlock=new ForumBlock();
				forumBlock.setId(Integer.parseInt(newid));
				forum.setFblock(forumBlock);
			}else{
				ForumBlock forumBlock=new ForumBlock();
				forumBlock.setFbtype(new ForumBlockType());
				forumBlock.getFbtype().setId(Integer.parseInt(fbtid));
				forum.setFblock(forumBlock);
			}
		}
		if(getRequest().getParameter("str")==null){
			getSession().setAttribute("csf",forum );
		}else{
			forum=(Forum)getSession().getAttribute("csf");
		}
		if("del".equals(optype)){
			if(null!=forum){
				forumAdminDao.forumDelete(forum.getId());
				indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
			}
		}
		forums=forumAdminDao.listCombinationForum(forum, getPageNow(), getPageSize());
		count = forumAdminDao.listCombinationForumCount(forum, getPageNow(), getPageSize());
		getRequest().setAttribute("fbtid", fbtid);
		System.out.println(getRequest().getAttribute("fbtid"));
		return "forum_list_byuid";
	}
	
	public ForumBlockType getFbtype() {
		return fbtype;
	}

	public void setFbtype(ForumBlockType fbtype) {
		this.fbtype = fbtype;
	}

	public ForumBlock getFblock() {
		return fblock;
	}

	public void setFblock(ForumBlock fblock) {
		this.fblock = fblock;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}


	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


}
