package com.sopia.common.logger;

/**
 * 日志系统
 * @author Administrator
 *
 */
public class ElLoggerConstants { 
	public static final int LOG_TYPE_ADD =1;//增加
	public static final int LOG_TYPE_VALID =2 ;//申请初审(创建完成)
	public static final int LOG_TYPE_ALTER=3 ;//修改
	public static final int LOG_TYPE_FUHE=4 ;//复核
	public static final int LOG_TYPE_DELETE=5 ;//删除
	public static final int LOG_TYPE_PASS=6 ;//初审通过（提交申请）
	public static final int LOG_TYPE_UNPASS=7 ;//初审不通过（返回）
	public static final int LOG_TYPE_PASS2=8 ;//终审通过（核准）
	public static final int LOG_TYPE_UNPASS2=9 ;//终审不通过（返回申请）
	public static final int LOG_TYPE_APPDELETE=10 ;//申请删除
	public static final int LOG_TYPE_UNDELETE=11 ;//不许删除
	public static final int LOG_TYPE_APPUPDATE=12 ;//申请修改
	public static final int LOG_TYPE_VALID2 =13 ;//申请终审（提交申请）
	public static final int LOG_TYPE_GET =14 ;//获取 
	public static final int LOG_TYPE_OETHER =15 ;//其他 
	public static final int LOG_TYPE_APPUPDSUSPENDED =16 ;//申请暂停
	public static final int LOG_TYPE_IMPORT =17;//导入
	
	public static final int LOG_MOD_NEWS=1 ;//新闻、
	public static final int LOG_MOD_KNOWLEDGE=2 ;//知识、
	public static final int LOG_MOD_FORUM=3 ;//帖子、
	public static final int LOG_MOD_STUFF=4 ;//素材、
	public static final int LOG_MOD_DEPARTMENT=5 ;//部门
	public static final int LOG_MOD_COURSE=6 ;//课程
	public static final int LOG_MOD_CLASS=7 ;//培训班、
	public static final int LOG_MOD_EROOM=8 ;//考场、
	public static final int LOG_MOD_EXAMPAPER=9 ;//试卷 
	public static final int LOG_MOD_EROOMLIB=10 ;//考场类别
	public static final int LOG_MOD_EPRAC=11 ;//练习
	public static final int LOG_MOD_COURSELIB=12 ;//课程类别
	public static final int LOG_MOD_ELUSER=13 ;//用户
	public static final int LOG_MOD_ROLE=14 ;//角色
	public static final int LOG_MOD_FORUMBLOCKTYPE=15 ;//帖子版块类别
	public static final int LOG_MOD_FORUMBLOCK=16 ;//帖子版块
	public static final int LOG_MOD_KNOWLEDGETYPE=17 ;//知识库
	public static final int LOG_MOD_NEWSTYPE=18 ;//新闻类别
	public static final int LOG_MOD_MESSAGE=19 ;//消息
	public static final int LOG_MOD_EXAMPAPERLIB=20 ;//试卷库
	public static final int LOG_MOD_EXAMPAPERBLOCK=21 ;//试卷大题
	public static final int LOG_MOD_QUESTIONLIB=22 ;//试题库
	public static final int LOG_MOD_QUESTION=23 ;//试题
	public static final int LOG_MOD_CLASSLIB=24 ;//培训班类别
	public static final int LOG_MOD_PRODUCTLIB=25;//产品所属栏目类别
	public static final int LOG_MOD_POLICYLIB = 26;//自定义险种
	public static final int LOG_MOD_STATION=27 ;//岗位
	public static final int LOG_RES_SUCC=1 ;//成功 
	public static final int LOG_RES_ERR=2 ;//失败 
//	public static final int LOG_RES_PASS=3 ;//通过 
//	public static final int LOG_RES_UNPASS=4 ;//不通过
	public static final int LOG_MOD_SELECTLEVEL=28;//下拉选项
	public static final int LOG_MOD_KNOWLEDGETREE=29;//知识类别
	public static final int LOG_MOD_ANSWERINGTYPE=30;//问答类别
	public static final int LOG_MOD_LABLETREE = 31;//自定义标签类别
	public static final int LOG_MOD_WORD = 32;//词汇类别
	
}
