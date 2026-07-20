package com.sopia.common.logger;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;

public class ElLog {
	private int id;
	private ELUser user;
	private Timestamp optime;
	private int optype;
	private int opmod;
	private String opcontent;
	private int opresult;
	private Timestamp querybtime;
	private Timestamp queryetime;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ELUser getUser() {
		return user;
	}

	public void setUser(ELUser user) {
		this.user = user;
	}

	public Timestamp getOptime() {
		return optime;
	}

	public void setOptime(Timestamp optime) {
		this.optime = optime;
	}

	public int getOptype() {
		return optype;
	}

	public void setOptype(int optype) {
		this.optype = optype;
	}

	public String getOptypeStr() {
		switch (optype) {
		case ElLoggerConstants.LOG_TYPE_ADD:
			return "增加";
		case ElLoggerConstants.LOG_TYPE_VALID:
			return "审核";
		case ElLoggerConstants.LOG_TYPE_ALTER:
			return "修改";
		case ElLoggerConstants.LOG_TYPE_FUHE:
			return "复核";
		case ElLoggerConstants.LOG_TYPE_DELETE:
			return "删除";
		case ElLoggerConstants.LOG_TYPE_PASS:
			return "提交申请";
		case ElLoggerConstants.LOG_TYPE_UNPASS:
			return "返回";
		case ElLoggerConstants.LOG_TYPE_PASS2:
			return "核准";
		case ElLoggerConstants.LOG_TYPE_UNPASS2:
			return "返回申请";
		case ElLoggerConstants.LOG_TYPE_APPDELETE:
			return "申请删除";
		case ElLoggerConstants.LOG_TYPE_UNDELETE:
			return "不许删除";
		case ElLoggerConstants.LOG_TYPE_APPUPDATE:
			return "申请修改";
		case ElLoggerConstants.LOG_TYPE_VALID2:
			return "提交申请";
		case ElLoggerConstants.LOG_TYPE_GET:
			return "获取";
		case ElLoggerConstants.LOG_TYPE_OETHER:
			return "其他";
		case ElLoggerConstants.LOG_TYPE_APPUPDSUSPENDED:
			return "申请暂停";
		case ElLoggerConstants.LOG_TYPE_IMPORT:
			return "导入";
		default:
			return "未知类型";
		}
	}

	public int getOpmod() {
		return opmod;
	}

	public String getOpmodStr() {
		switch (opmod) {
		case ElLoggerConstants.LOG_MOD_NEWS:
			return "新闻";
		case ElLoggerConstants.LOG_MOD_KNOWLEDGE:
			return "知识";
		case ElLoggerConstants.LOG_MOD_FORUM:
			return "帖子";
		case ElLoggerConstants.LOG_MOD_STUFF:
			return "素材";
		case ElLoggerConstants.LOG_MOD_DEPARTMENT:
			return "部门";
		case ElLoggerConstants.LOG_MOD_COURSE:
			return "课程";
		case ElLoggerConstants.LOG_MOD_CLASS:
			return "培训班";
		case ElLoggerConstants.LOG_MOD_EROOM:
			return "考场";
		case ElLoggerConstants.LOG_MOD_EXAMPAPER:
			return "试卷";
		case ElLoggerConstants.LOG_MOD_EROOMLIB:
			return "考场库";
		case ElLoggerConstants.LOG_MOD_EPRAC:
			return "练习";
		case ElLoggerConstants.LOG_MOD_COURSELIB:
			return "课程类别";
		case ElLoggerConstants.LOG_MOD_ELUSER:
			return "用户";
		case ElLoggerConstants.LOG_MOD_ROLE:
			return "角色";
		case ElLoggerConstants.LOG_MOD_FORUMBLOCKTYPE:
			return "帖子版块类别";
		case ElLoggerConstants.LOG_MOD_FORUMBLOCK:
			return "帖子版块";
		case ElLoggerConstants.LOG_MOD_KNOWLEDGETYPE:
			return "知识库";
		case ElLoggerConstants.LOG_MOD_NEWSTYPE:
			return "新闻类别";
		case ElLoggerConstants.LOG_MOD_MESSAGE:
			return "消息";
		case ElLoggerConstants.LOG_MOD_EXAMPAPERLIB:
			return "试卷库";
		case ElLoggerConstants.LOG_MOD_EXAMPAPERBLOCK:
			return "试卷大题";
		case ElLoggerConstants.LOG_MOD_QUESTIONLIB:
			return "试题库";
		case ElLoggerConstants.LOG_MOD_QUESTION:
			return "试题";
		default:
			return "未知类型";
		}
	}

	public void setOpmod(int opmod) {
		this.opmod = opmod;
	}

	public String getOpcontent() {
		return opcontent;
	}

	public void setOpcontent(String opcontent) {
		this.opcontent = opcontent;
	}

	public int getOpresult() {
		return opresult;
	}

	public String getOpresultStr() {
		switch (opresult) {
		case ElLoggerConstants.LOG_RES_SUCC:
			return "成功";
		case ElLoggerConstants.LOG_RES_ERR:
			return "失败";
//		case ElLoggerConstants.LOG_RES_PASS:
//			return "通过";
//		case ElLoggerConstants.LOG_RES_UNPASS:
//			return "不通过";
		default:
			return "未知结果";
		}
	}

	public void setOpresult(int opresult) {
		this.opresult = opresult;
	}

	public Timestamp getQuerybtime() {
		return querybtime;
	}

	public void setQuerybtime(Timestamp querybtime) {
		this.querybtime = querybtime;
	}

	public Timestamp getQueryetime() {
		return queryetime;
	}

	public void setQueryetime(Timestamp queryetime) {
		this.queryetime = queryetime;
	}

	public String[][] getMods() {

		return new String[][] { { "1", "新闻" }, { "2", "知识" }, { "3", "帖子" },
				{ "4", "素材" }, { "5", "部门" }, { "6", "课程" }, { "7", "培训班" },
				{ "8", "考场" }, { "9", "试卷" }, { "10", "考场库" }, { "11", "练习" }
				, { "12", "课程类别" }, { "13", "用户" }, { "14", "角色" }, { "15", "帖子版块类别" }
				, { "16", "帖子版块" }, { "17", "知识库" }, { "18", "新闻类别" }, { "19", "消息" }
				, { "20", "试卷库" }, { "21", "试卷大题" }, { "22", "试题库" }, { "23", "试题" }
				};
	}
}
