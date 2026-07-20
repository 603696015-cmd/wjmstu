-- 用户表
drop table if exists eluser;
CREATE TABLE `eluser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `realname` varchar(20) DEFAULT NULL,
  `userno` varchar(25) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `role` int DEFAULT '4',
   `company` varchar(100) NOT NULL DEFAULT '1',
  `depid` int(11) NOT NULL DEFAULT '0',
   sex varchar(1) ,-- //性别、
   age int(11),-- //年龄（NUMBER）、
   edubg varchar(5),-- //学历（多选一）、
   major varchar(1000),-- ;//专业、
   studyDir varchar(1000), -- //研究方向、
   gradchool varchar(1000), -- //毕业院校、
   graddate date, -- //毕业时间（DATE）、
   jobdate date , -- //参加工作时间（DATE）、
   protitle varchar(5) ,-- //职称（多选一）、
   jobdesc text ,-- //工作简历、
   majorc varchAR(3000) ,-- 专业证书
  `valid` boolean default false,
  -- studenttag boolean default true,
  dot int default 0 ,
  score int default 0,
  xfscore int default 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
);
insert into eluser(id,username,password,role,email);

-- 部门表
drop table if exists department;
CREATE TABLE `department` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `company` int(11) NOT NULL,
  `description` text,
  `parentid` int(11) DEFAULT '0',
  `manager` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `postalcode` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `lid` int(11),
  `rid` int(11),
  PRIMARY KEY (`id`)
  -- UNIQUE KEY `manager` (`manager`)
) ;
-- 企业表
drop table if exists company;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text,
  `manager` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `postalcode` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

drop table if exists elgroup;
create table elgroup(
	id int(11) primary key auto_increment ,
	name varchar(100),
	description varchar(1000),
	gtype int(11) default 3
);

drop table if exists elgroup2user;
create table elgroup2user(
	gid int,
	userid int ,
	primary key(gid,userid)
);


-- 角色表
drop table if exists elrole;
create table elrole(
	id int(11) primary key auto_increment,
	name varchar(100),
	description varchar(1000)
);
-- 角色功能表
drop table if exists elrolefunc;
create table elrolefunc(
	roleid int,	
	funcid int(11),
	primary key(roleid,funcid)
);
-- 功能表
drop table if exists elfunc;
create table elfunc(
	id int primary key auto_increment,	
	funccode varchar(100) ,
	name varchar(100),
	description varchar(100),
	parentid int default 0 ,
	needcheck boolean default false,
	params varchar(100) default '',
	target varchar(100) default "rightFrame"
);
-- 积分点数 记录表
drop table if exists eldotandscore;
create table eldotandscore(
	id int primary key auto_increment,
	userid int ,
	type int default 1,-- 1 点数，2 积分
	operate varchar(100),
	description varchar(500),
	score int default 0,
	thedate timestamp
);

CREATE TABLE course_server (
	 id int primary key auto_increment,
	 name   varchar (500)  ,
	 description   varchar(500) ,
	 url varchar (500)
);
-- 学分 记录表
drop table if exists elxfscore;
create table elxfscore(
	id int primary key auto_increment,
	courseid int,
	userid int ,
	-- type int default 1,-- 1 学习，2 考试
	operate varchar(100),
	description varchar(500),
	score int default 0,
	thedate timestamp,
	unique(courseid,userid,operate)
);

drop table if exists course_page;
CREATE TABLE `course_page` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseid` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `property` tinyint(4) NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `file` tinytext,
  `filepwd` varchar(50) DEFAULT NULL,
  `page` longtext,
  `pagesimple` longtext,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sortid` int(11) NOT NULL DEFAULT '1',
  `during` int(11) NOT NULL DEFAULT '0',
  `skipable` tinyint(4) NOT NULL DEFAULT '1',
  `page_url` varchar(100),
  `queryTime` int not null default '-1',
  PRIMARY KEY (`id`)
);


-- 课程表
drop table if exists COURSE;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `ctypeid` int(11) NOT NULL DEFAULT '0',
  `creater` int(11) NOT NULL,
  `description` text,
  `mainimg` varchar(150) DEFAULT NULL,
  `credit` int(11) NOT NULL DEFAULT '1',
  `passgrade` float NOT NULL DEFAULT '60',
  `fee` float DEFAULT '0',
  `fee2` float NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `createtime` datetime DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `islink` tinyint(1) NOT NULL DEFAULT '0',
  `exurl` varchar(200) DEFAULT NULL,
  `hot` int ,
  during int default 0,
  querytime int default 0,
  teacherinfo text ,
  studyplan text ,
  PRIMARY KEY (`id`)
) ;

-- 课程类别
drop table if exists COURSE_TYPE;
CREATE TABLE `COURSE_TYPE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` text,
  `parentid` int(11) NOT NULL,
   `lid` int (11),
  `rid` int(11),
   PRIMARY KEY (`id`)
) ;
-- 课程分配到用户
drop table if exists course_apply;
CREATE TABLE `course_apply` (
  `courseid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `applydate` date NOT NULL,
  `status` smallint(6) NOT NULL DEFAULT '0',
  `valid` smallint(6) default 0,
  PRIMARY KEY (`courseid`,`userid`)
) ;
-- 课程分配到部门
drop table if exists course_dep;
CREATE TABLE `course_dep` (
  `courseid` int(11) NOT NULL,
  `depid` int(11) NOT NULL,
  `applydate` date NOT NULL,
  -- `status` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`courseid`,`depid`)
) ;

-- 练习库---
drop table if exists practicepaper;
CREATE TABLE `practicepaper` (
  	`id`  int(11) NOT NULL AUTO_INCREMENT,
   `epid` int(11) NOT NULL ,
   `courseid` int(11) NOT NULL,
   `cpid` int (11) NOT NULL DEFAULT '0',
   `sortid` smallint(6) NOT NULL DEFAULT '0',
   `skipable` int(11) ,
    PRIMARY KEY (`id`),
    unique(courseid,epid,cpid)
);  
-- 模拟试卷库
drop table if exists simexampaper;
CREATE TABLE `simexampaper` (
   `id`  int(11) NOT NULL AUTO_INCREMENT,
   `epid` int(11) NOT NULL ,
   `courseid` int(11) NOT NULL,
  -- `sortid` smallint(6) NOT NULL DEFAULT '0',
   `begintime` timestamp,
   `endtime` timestamp ,
   PRIMARY KEY (`id`),
   unique(epid,courseid)
);  
-- 课程试卷
drop table if exists quizpaper;
CREATE TABLE `quizpaper` (
  	`id`  int(11) NOT NULL AUTO_INCREMENT,
   `exampaperid` int(11) NOT NULL ,
   `courseid` int(11) NOT NULL,
   `sortid` smallint(6) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
); 

-- 考试场次
drop table if exists exam_room;
CREATE TABLE `exam_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseid` int(11) NOT NULL,
  `createrid` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text,
  `location` varchar(200) DEFAULT NULL,
  `supervisor` int(11) DEFAULT NULL,
  `begintime` timestamp NOT NULL,
  `endtime` timestamp NOT NULL,
  `paperid` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
);
-- 学员场次
drop table if exists room_assign;
CREATE TABLE `room_assign` (
  `userid` int(11) NOT NULL,
  `courseid` int(11) NOT NULL,
  `roomid` int(11) NOT NULL,
  PRIMARY KEY (`userid`,`courseid`)
) ;
drop table if exists course_note;
CREATE TABLE `course_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `content` text ,
  `createtime` datetime NOT NULL,
   `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
);
-- 论坛版块类别
drop table if exists forumblocktype;
create table forumblocktype(
	id int primary key auto_increment,
	name varchar(100),
	description varchar(1000),
	sortid int(11) default 0
);
-- 论坛版块
drop table if exists forumblock;
create table forumblock(
	id int primary key auto_increment,
	title varchar(100),
	description varchar(1000),
	manager int(11),
	fbtid int(11),
	sortid int(11) default 0
);
-- 论坛
drop table if exists forum;
create table forum(
	id int(11) primary key auto_increment,
	title varchar(500),
	description text,
	createtime Timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	modifytime Timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	creater int(11),
	fblockid int(11),
	hot int(11) default 0,
	readtime  int(11) default 0,
	receipttime int(11) default 0,
	valid boolean default false
);
-- 文章（帖子）
drop table if exists ftopic;
create table ftopic(
	id int(11) primary key auto_increment,
    	content text,
   	createtime Timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
   	-- modifytime Timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	forumid int(11),
	creater int(11)
);
-- 知识类别
drop table if exists knowledgetype;
CREATE TABLE `knowledgetype` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(200) NOT NULL,
	`description` varchar(500) ,
	`parentid` int(11),
	PRIMARY KEY (`id`)
);
-- 类别--部门
drop table if exists kltype_dep;
create table kltype_dep(
	kltypeid int(11),
	depid int (11),
	primary key(kltype_id,dep_id)
);
-- 知识
drop table if exists knowledge ;
CREATE TABLE `knowledge` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`title` text NOT NULL,
	`content` text NOT NULL,
	`userid` int(11) NOT NULL,
	`kltypeid` int(11) NOT NULL,
	`modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	`hot` int(11) default 0,
	`readtime` int(11) default 0,
	valid boolean default false,
	 PRIMARY KEY (`id`)
)  ;

--  题库表
drop table if exists question_lib;
CREATE TABLE `question_lib` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `userid` int(11) NOT NULL,
  `parentid` int(11) NOT NULL DEFAULT '0',
  `description` text,
  `lid` int(11),
  `rid`	int(11),
  PRIMARY KEY (`id`)
) ;

-- 试题表
drop table if exists question ;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qtype` tinyint(1) NOT NULL,
  `title` text NOT NULL,
  -- `score` float NOT NULL,
  -- `punish` float NOT NULL,
  `content` text NOT NULL,
  `subject` text NOT NULL,
  `qexplain` text,
  `userid` int(11) NOT NULL,
  `qlibid` int(11) NOT NULL,
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `qlevel` tinyint(4) NOT NULL DEFAULT '0',
  `answer` varchar(3000) NOT NULL,
  `scoreper` int(11) default 0,
  `parentid` int default 0,
  `minWord` int (11)  DEFAULT '0',
  `sortid` int(11) default '0',
  PRIMARY KEY (`id`)
)  ;
-- 试卷库--
drop table if exists exampaperlib;
CREATE TABLE `exampaperlib` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `userid` int(11) NOT NULL,
  `parentid` int(11) NOT NULL DEFAULT '0',
  `description` text,
  `lid` int(11),
  `rid`	int(11),
  PRIMARY KEY (`id`)
)  ;
-- 试卷表---------
drop table if exists exampaper;
CREATE TABLE `exampaper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `description` text,
  -- `type` tinyint(1) NOT NULL DEFAULT '0',
  `userid` int(11) NOT NULL,
  `eplid` int(11) NOT NULL DEFAULT '0',
  -- `autograding` tinyint(1) NOT NULL DEFAULT '0',
  `israndom` tinyint(1) NOT NULL DEFAULT '0',
  -- `valid` tinyint(1) NOT NULL DEFAULT '1',
  `during` int(11) NOT NULL DEFAULT '30',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
 -- `begintime` datetime  DEFAULT '1900-01-01 00:00:00',
 -- `endtime` datetime  DEFAULT '2099-12-31 00:00:00',
  `opentimelimit` tinyint(1) NOT NULL DEFAULT '0',
  `ep_tscore` int(11),
  -- `minWord` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
   foreign key (eplid) references exampaperlib(id)
) ;
-- 试卷大题表---------
drop table if exists exampaperblock;
CREATE TABLE `exampaperblock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exampaperid` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text,
  `type` tinyint(4) NOT NULL DEFAULT '1',
  `questionamount` tinyint(4) NOT NULL DEFAULT '0',
  `eachscore` tinyint(4) NOT NULL DEFAULT '1',
  `sortid` smallint(6) NOT NULL DEFAULT '0',
   PRIMARY KEY (`id`),
   foreign key (exampaperid) references exampaper(id)  on delete cascade
)  ;
-- 试卷大题试题表---------
drop table if exists exampaperblockquestion;
CREATE TABLE `exampaperblockquestion` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `blockid` int(11) NOT NULL,
   `questionid` int(11) NOT NULL,
   -- `score` tinyint(4) NOT NULL DEFAULT '1',
   `sortid` smallint(6) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    foreign key (blockid) references exampaperblock(id)  on delete cascade
)  ;
-- 随机试卷设置
drop table if exists exampaper_random;
CREATE TABLE `exampaper_random` (
  `id`int(11) NOT NULL AUTO_INCREMENT,
  -- `epid` int(11) NOT NULL,
  `qlibid` int(11) NOT NULL,
  `blockid` int(11) NOT NULL,
  `eplevel1` smallint(6) NOT NULL DEFAULT '0',
  `eplevel2` smallint(6) NOT NULL DEFAULT '0',
  `eplevel3` smallint(6) NOT NULL DEFAULT '0',
  `eplevel4` smallint(6) NOT NULL DEFAULT '0',
  `eplevel5` smallint(6) NOT NULL DEFAULT '0',
  `eplevel` smallint(6) NOT NULL DEFAULT '0',
  `sortid` smallint(6) NOT NULL DEFAULT '0',
  `suboperate` boolean,
  PRIMARY KEY (`id`),
   foreign key (blockid) references exampaperblock(id)  on delete cascade
) ;

-- 消息表---
drop table if exists message;
create table message(
	mess_id	int auto_increment,
	mess_title	varchar	(100),	
	mess_content	varchar	(1000),
	mess_from int ,	
	mess_to	int	,	
	is_read	boolean	default false,	
	mess_time datetime,
	recDel boolean default false,
	sendDel boolean	default false,
	primary key(mess_id),
	foreign key(mess_from) references Eluser(id) on delete cascade  ,
	foreign key(mess_to) references Eluser(id) on delete cascade 
);
-- 资料表----
drop table if exists question_stuff;

create table `question_stuff`(
	`id` int auto_increment,
	`title` varchar(500),
	`description` varchar(3000),
	`fileExt` varchar(10),
	`onwer` int(11),
	`modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	`createtime` timestamp ,
  	`length` long,
  	`type` int(11),
	primary key(`id`)
) ;
-- 新闻栏目
drop table if exists newstype;
create table newstype(
	id int(11) auto_increment primary key,
	name varchar(500),
	description varchar(3000),
	parentid int(11),
	lid int(11),
	rid int(11)
);
-- 新闻
drop table if exists news;
create table news(
	id int(11) auto_increment primary key,
	title varchar(500),
	mainimg varchar(500),
	content text,
	userid int(11),
	ntid int(11),
	releasetime timestamp
);
-- 图书类别
drop table if exists booktype;
create table booktype(
	id int(11) auto_increment primary key,
	name varchar(500),
	description varchar(3000),
	parentid int(11),
	lid int(11),
	rid int(11)
);
-- 图书
drop table if exists book;
create table book(
	id int(11) auto_increment primary key,
	title varchar(500),
	mainimg  varchar(500),
	content text,
	userid int(11),
	btid int(11),
	releasetime timestamp NULL DEFAULT NULL,
	modifytime  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	pubhouse varchar(500), -- //出版社
	writer varchar(500),-- //作者
	pubtime Timestamp, -- //出版时间
	hot int(11)
);
-- 培训班类别
drop table if exists elclasstype;
create table elclasstype(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` text,
  `parentid` int(11) default 0,
  -- `status` int,
  `lid` int (11),
  `rid` int(11),
  PRIMARY KEY (`id`)
);
-- 培训班
drop table if exists elclass;
CREATE TABLE `elclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `certificatename` varchar(50) NOT NULL,
  `cltype` int(11) NOT NULL DEFAULT '0',
  `creater` int(11) NOT NULL,
  `description` text,
  `fee` float DEFAULT '0',
  `fee2` float NOT NULL DEFAULT '0',
  `optionalcredit` int(11) ,
  `status` int(11) ,
  `createtime` timestamp NULL DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `mainimg` varchar(100) ,
  PRIMARY KEY (`id`)
);
-- 培训班分配到部门
drop table if exists class_assign;
create table class_assign(
	classid int(11),
	company int(11),
	depid int(11),
	assignor int(11),
	assigntime timestamp ,
	primary key(classid,company,depid)
);
-- 培训班课程
drop table if exists class_course;
create table class_course(
	classid int(11),
	courseid int(11),
	status int,
	credit int(11) default '0',
	primary key(classid,courseid)
);
-- 培训班删除

drop table if exists class_delete;
create table class_delete(
	classid int(11),
	userid int(11),
	deletetime timestamp,
	primary key(classid)
);
-- 培训班分配到学员
drop table if exists class_apply;
create table class_apply(
	classid int(11),
	userid int(11),
	applydate date,
	status int(11),
	primary key(classid,userid)
);


-- 计划表
drop table if exists elplan ;
create table elplan(
	id  int(11) auto_increment primary key ,
	name varchar(500),
	content text,
	manager int(11), 
	contact varchar(1000),
	participator varchar(1000),
	createtime timestamp,
	finishdate date ,
	status int default 0
);
-- 计划审核记录
drop table if exists elplansh;
create table elplansh(
	planid int(11),
	userid int(11),
	shdate date,
);
-- 计划阶段表
drop table if exists elplanstage;
create table elplanstage(
	id int(11) auto_increment primary key,
	content text,
	plandays int(11),
	realdays int(11),
	planid int(11),
	planfinishdate date ,
	realfinishdate date
) ; 
-- 计划材料
drop table if exists elplanstuff;
create table elplanstuff(
	id int(11) auto_increment primary key,
	psid int(11),
	stuffid int(11)
) ; 
-- 计划审核表
drop table if exists elplanverify;
create table elplanverify(
	id int(11) auto_increment primary key,
	planid int(11),
	userid int(11),
	status int(11) default 2,
	verifydate timestamp,
	superverify boolean default false
);
-- 调查问卷
drop table if exists elsurvey;
create table elsurvey(
	id int(11) auto_increment primary key,
	title varchar(500),
	description varchar(2000),
	creater int(11),
	begintime timestamp ,
	endtime timestamp ,
	epid int(11),
	stureadresult boolean default true
);
-- 投票
drop table if exists elpoll;
create table elpoll(
	id int(11) auto_increment primary key,
	title varchar(500),
	description varchar(2000),
	creater int(11),
	begintime timestamp ,
	endtime timestamp ,
	qid int(11),
	stureadresult boolean default true
);

-- 调查问卷查看结果
DELIMITER ;;
CREATE  FUNCTION `getSurveyCount_ByQandAns`(qid int,ans varchar(10),sid int) RETURNS int(11)
begin
declare count1 int;
select count(*) into count1 from student_survey where student_survey.surveyid = sid and instr(substring(myanswer,instr(myanswer, concat("_",qid))-length(concat("_",qid)), instr(substring(myanswer,instr(myanswer,concat("_",qid))),"-=SpRe-")-length(concat("_",qid))) ,ans)>0;
return count1 ;
end;;
DELIMITER ;

-- 投票查看结果
DELIMITER ;;
CREATE  FUNCTION `getPollCount_ByQandAns`(qid int,ans varchar(10),sid int) RETURNS int(11)
begin
declare count1 int;
select count(*) into count1 from student_poll where student_poll.pollid = sid and instr(substring(myanswer,instr(myanswer, concat("_",qid))-length(concat("_",qid)), instr(substring(myanswer,instr(myanswer,concat("_",qid))),"-=SpRe-")-length(concat("_",qid))) ,ans)>0;
return count1 ;
end;;
DELIMITER ;
DELIMITER ;;
CREATE PROCEDURE into_sc (@userid int,@courseid int)
AS
BEGIN
	declare @isinsc int 
	set @isinsc = 0 
	select @isinsc=count(*) from study_course where userid = @userid and courseid = @courseid 
	if @isinsc=0 then
		begin
			insert into study_course(userid,courseid,passtime,passed,starttime,finishtime,process)
			values(@userid,@courseid,0,0,now(),now(),0);
		end
END
DELIMITER ;
-- 考生考试答题信息
drop table if exists study_quizinfo;
CREATE TABLE `study_quizinfo` (
  `id` int(11) AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `roomid` int(11) NOT NULL,
  `epid` int(11)not null,
  `myAnswer` text,
  `passTime` int default 0 ,
  `status` int default 0 ,
  `myScore` int default 0 ,
  `begintime` timestamp,
  `endtime` timestamp ,
  PRIMARY KEY (`id`),
) ;
-- 问卷调查表
drop table if exists student_survey;
CREATE TABLE `student_survey` (
  `id` int(11) AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `surveyid` int(11) NOT NULL,
  `epid` int(11)not null,
  `myAnswer` text,
  -- `myScore` int default 0 ,
  `endtime` timestamp ,
   unique (userid,surveyid),
  PRIMARY KEY (`id`)
) ;
-- 投票表
drop table if exists student_poll;
CREATE TABLE `student_poll` (
  `id` int(11) AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `pollid` int(11) NOT NULL,
  `qid` int(11)not null,
  `myAnswer` text,
  -- `myScore` int default 0 ,
  `endtime` timestamp ,
   unique (userid,pollid),
  PRIMARY KEY (`id`)
) ;
-- 模考表
drop table if exists student_siminfo;
CREATE TABLE `student_siminfo` (
  `id` int(11) AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `epid` int(11)not null,
  `courseid` int(11) not null,
  `myAnswer` text,
  `passTime` int default 0 ,
  `status` int default 0 ,
  `myScore` int default 0 ,
  `endtime` timestamp ,
  PRIMARY KEY (`id`)
) ;
-- 课程网页学习
drop table if exists study_cpage;
create table study_cpage(
	userid int(11),
	cpid int(11),
	passTime int(11),
	passed boolean,
	begintime timestamp,
	endtime timestamp,
	primary key(userid,cpid)
);
-- 课程学习
drop table if exists study_course;
create table study_course(
	userid int(11),
	courseid int(11),
	passed boolean,
	passtime int(11),
	-- process int(11),
	starttime timestamp,
	finishtime timestamp,
	primary key(userid,courseid)
) ;
-- 做练习
drop table if exists study_prac;
create table study_prac(
	ppid int(11),
	userid int(11),
	myScore int(11),
	lasttime timestamp,
	primary key(ppid,userid)
) ;
-- 培训班学习
drop table if exists study_class;
create table study_class(
	classid int(11),
	userid int(11),
	applydate timestamp,
	status int(11),
	primary key(classid,userid)
) ;
--  人才库管里
-- 客观评价场次
drop table if exists troomcoll;
create table troomcoll(
	id int(11) auto_increment primary key,
	title varchar(500),
	description varchar(1000),
	creater int(11),
	createtime timestamp
);
-- 客观评价考试表
drop table if exists troom ;
create table troom(
	id int(11) auto_increment primary key,
	title varchar(500),
	description varchar(1000),
	trcid int(11),
	epid int(11),
	-- norm varchar(1000),
	begintime timestamp,
	endtime timestamp
);
-- 客观评价分配
drop table if exists troom_assign;
create table troom_assign(
	trid int(11),
	userid int(11),
	assigntime timestamp,
	primary key (trid,userid)
	);

-- 客观评价结果
drop table if exists troom_epinfo;
create table troom_epinfo(
  `userid` int(11) NOT NULL,
  `trid` int(11) NOT NULL,
  `myAnswer` text,
  `passTime` int default 0 ,
  `status` int default 0 ,
  `myScore` int default 0 ,
  `begintime` timestamp,
  `endtime` timestamp ,  
  primary key(userid,trid)
);
-- 主观评价场次分配
drop table if exists ztroom ;
create table ztroom(
	id int(11) auto_increment primary key,
	creater int(11),
	title varchar(500),
	description varchar(1000),
	norm varchar(1000),
	begintime timestamp,
	endtime timestamp
);
-- 主观评价分配表
drop table if exists ztroom_assign;
create table ztroom_assign(
	trid int(11),
	userid int(11),
	assigntime timestamp,
	zjscore int(11) default 0,
	tsscore int(11) default 0,
	sjscore int(11) default 0,
	primary key (trid,userid)
);
-- 客观评价结果
drop table if exists troom_eval;
create table troom_eval(
	trid int(11),
	evaler int(11),
	tester int(11),
	evaldetail varchar(5000),
	evaltype int(11),
	evaltime timestamp,
	primary key (trid,evaler,tester)
);

-- 设置打分--

DELIMITER ;;
CREATE  PROCEDURE `talnet_scoreset`(vuserid int,vtrid int) 
begin
declare evald varchar(5000) ;
declare score int default 0;
declare count1 int default 0;
declare theindex int default 0;
declare zscore int default 0;
declare zcount int default 0;
declare cur1 CURSOR FOR select evaldetail  from troom_eval where trid = vtrid and tester = vuserid and evaltype=1;
declare cur2 CURSOR FOR select evaldetail  from troom_eval where trid = vtrid and tester = vuserid and evaltype=2;
declare cur3 CURSOR FOR select evaldetail  from troom_eval where trid = vtrid and tester = vuserid and evaltype=3;
declare CONTINUE HANDLER FOR SQLSTATE '02000' SET evald = null;

-- 自己打分(人才测评)
OPEN cur1;

FETCH cur1 INTO evald;
set theindex = instr(evald,'-=SpEl=-');
while theindex>0
do
	set count1=count1+1;
	set score = score+substring(evald,1,theindex);
	set evald = substring(evald,theindex+8,length(evald));
	set theindex = instr(evald,'-=SpEl=-');
end while;
if count1=0  then
set count1 = 1;
end if ;
update ztroom_assign set zjscore = score/count1 where userid = vuserid and trid = vtrid;
CLOSE cur1;
-- 同事
set evald=null;
set zscore = 0;
set zcount = 0;
OPEN cur2;
FETCH cur2 INTO evald;
WHILE ( evald is not null) DO
set count1=0;
set score = 0;
set theindex = instr(evald,'-=SpEl=-');
while theindex>0
do
	set count1=count1+1;
	set score = score+substring(evald,1,theindex);
	set evald = substring(evald,theindex+8,length(evald));
	set theindex = instr(evald,'-=SpEl=-');
end while;
if count1=0  then
set count1 = 1;
end if ;
set zscore = zscore + score/count1;
set zcount = zcount+1;
  FETCH cur2 INTO evald;
END WHILE;
CLOSE cur2;
if zcount=0  then
set zcount = 1;
end if ;
update ztroom_assign set tsscore = zscore/zcount where userid = vuserid and trid = vtrid;
-- 上级
set evald=null;
set zscore = 0;
set zcount = 0;
OPEN cur3;
FETCH cur3 INTO evald;
WHILE ( evald is not null) DO
set count1=0;
set score = 0;
set theindex = instr(evald,'-=SpEl=-');
select  theindex,score,evald;
while theindex>0
do
	set count1=count1+1;
	set score = score+substring(evald,1,theindex);
	set evald = substring(evald,theindex+8,length(evald));
	set theindex = instr(evald,'-=SpEl=-');
end while;
if count1=0  then
set count1 = 1;
end if ;
set zscore = zscore + score/count1;
set zcount = zcount+1;
  FETCH cur3 INTO evald;
END WHILE;
CLOSE cur3;
if zcount=0  then
set zcount = 1;
end if ;
update ztroom_assign set sjscore = zscore/zcount where userid = vuserid and trid = vtrid;
end;;
DELIMITER ;
drop table if exists knowledgetype;
CREATE TABLE `knowledgetype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(500) ,
  `parentid` int(11),
  PRIMARY KEY (`id`)
);
drop table if exists kltype_dep;
create table kltype_dep(
	kltypeid int(11),
	depid int (11),
	primary key(kltype_id,dep_id)
);


drop table if exists knowledge ;
CREATE TABLE `knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `userid` int(11) NOT NULL,
  `kltypeid` int(11) NOT NULL,
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `hot` int(11) default 0,
  `readtime` int(11) default 0,
  
  PRIMARY KEY (`id`)
)  ;

drop table if exists study_course_delete;
create table study_course_delete(
	userid int ,
	courseid int,
	deletedate timestamp ,
	primary key(userid,courseid)
) ;
drop table if exists systemconf;
create table systemconf(
	type int(11) primary key ,
	content text 
);
drop table if exists omroom_assign;
create table omroom_assign(
	userid int,
	rooms_id int,
	rooms_type int,
	primary key(userid,rooms_id)
);
 