create or replace  table "gdgat"."department" 
   ("id" number not null primary key, 
	"name" varchar2(50), 
	"description" varchar2(1000), 
	"parentid" number, 
	"address" varchar2(100), 
	"postalcode" varchar2(20), 
	"phone" varchar2(20), 
	"fax" varchar2(20), 
	"email" varchar2(30), 
	"lid" number, 
	"rid" number
   )  ;
 
	create or replace  trigger auto_id_department before
	insert on department for each row when (new.id is null)
	begin
	select department_sequence.nextval into :new.id from dual;
	end;
	
	create or replace procedure department_add
	  ( 
	   name in varchar2 , description in varchar2 , parentid in number ,manager in number, address in varchar2 ,
	    postalcode in varchar2 , phone in varchar2 ,fax in varchar2 , email in varchar2 
	  )
	  as
	    plid number;
	      mlid number;
	      mrid number;
	  begin
	     plid :=0;
	      mlid :=0;
	      mrid :=0;
		-- 查找当前插入节点的父节点的lft值
	    select lid into plid from department where id = parentid;
	    -- 将树形结构中所有大于父节点左值的左节点+2
	    update department set lid = lid+2 where lid >plid;
	    -- 将树形结构中所有大于父节点左值的右节点+2
	    update department set rid = rid +2 where rid>plid;
	    -- 定位自己的左值(父节点左值+1)和右值(父节点左值+2)
	      mlid := plid+1;
	     mrid := plid+2;
	    insert into department(name, description,parentid,manager,address,postalcode,phone,fax,email,lid,rid)
	    values(name, description,parentid,manager,address,postalcode,phone,fax,email, mlid,mrid );
	  end;

--==================================================
create or replace trigger auto_id_department before insert on department for each row 
when (new.id is null)
begin
     select department_sequence.nextval into :new.id from dual;
end;
create  SEQUENCE department_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;


----===============题库试题部分

create  SEQUENCE exampaperliblib_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create trigger auto_id_questionlib before insert on question_lib for each row 
when (new.id is null)
begin
     select questionlib_sequence.nextval into :new.id from dual;
end;
--试题
create  SEQUENCE question_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create trigger auto_id_question before insert on question for each row 
when (new.id is null)
begin
     select question_sequence.nextval into :new.id from dual;
end;
--试卷库
create SEQUENCE exampaperlib_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_exampaperlib before insert on exampaperlib for each row 
when (new.id is null)
begin
     select exampaperlib_sequence.nextval into :new.id from dual;
end;
--试卷
create  SEQUENCE exampaper_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_exampaper before insert on exampaper for each row 
when (new.id is null)
begin
     select exampaper_sequence.nextval into :new.id from dual;
end;
create  SEQUENCE exampaperblock_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_exampaperblock before insert on exampaperblock for each row 
when (new.id is null)
begin
     select exampaperblock_sequence.nextval into :new.id from dual;
end;

create  SEQUENCE exampaperrandom_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_exampaperrandom before insert on exampaper_random for each row 
when (new.id is null)
begin
     select exampaperrandom_sequence.nextval into :new.id from dual;
end;
--考场
create  SEQUENCE eroomlib_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_eroomlib before insert on eroom_lib for each row 
when (new.id is null)
begin
     select eroomlib_sequence.nextval into :new.id from dual;
end;
create  SEQUENCE examroom_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_examroom before insert on exam_room for each row 
when (new.id is null)
begin
     select examroom_sequence.nextval into :new.id from dual;
end;
---考试
create  SEQUENCE studyquizinfo_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_studyquizinfo before insert on study_quizinfo for each row 
when (new.id is null)
begin
     select studyquizinfo_sequence.nextval into :new.id from dual;
end;
create  SEQUENCE studyquizinfo_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_studyquizinfo before insert on study_quizinfo for each row 
when (new.id is null)
begin
     select studyquizinfo_sequence.nextval into :new.id from dual;
end;
create  SEQUENCE examprac_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_examprac before insert on examprac for each row 
when (new.id is null)
begin
     select examprac_sequence.nextval into :new.id from dual;
end;

create  SEQUENCE eluser_sequence
	increment by 1 -- 每次加几个
	start with 2 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_eluser before insert on eluser for each row 
when (new.id is null)
begin
     select eluser_sequence.nextval into :new.id from dual;
end;

---2011-6-27
delete from study_room where userid not in(select id from eluser);
delete from study_room where roomid not in(select id from exam_Room);
update study_room set ispassed = 0 where ispassed is null;
update study_room set practimes = 0 where practimes is null;
update study_room set pracscore = 0 where pracscore is null;
alter table STUDY_ROOM modify STATUS default 0 not null;
alter table STUDY_ROOM
  add constraint STUDY_ROOM_RID_FK foreign key (ROOMID)
  references EXAM_ROOM (ID) on delete cascade;
alter table STUDY_ROOM
  add constraint STUDY_ROOM_UID_FK foreign key (USERID)
  references ELUSER (ID) on delete cascade;
create index study_room_rid_ind on STUDY_ROOM (roomid);
create index study_room_uid_ind on STUDY_ROOM (userid);
update study_quizinfo set ispassed = 0 where ispassed is null;

alter table STUDY_QUIZINFO
  add constraint STUDY_QUIZINFO_RID_FK foreign key (ROOMID)
  references EXAM_ROOM (ID) on delete cascade;
alter table STUDY_QUIZINFO
  add constraint STUDY_QUIZINFO_UID_FK foreign key (USERID)
  references ELUSER (ID) on delete cascade;
alter table STUDY_QUIZINFO
  add constraint STUDY_QUIZNFO_EPID_FK foreign key (EPID)
  references EXAMPAPER (ID) on delete cascade;
create index STUDY_QUIZINFO_RID_Ind on STUDY_QUIZINFO (roomid);
create index STUDY_QUIZINFO_uid_ind on STUDY_QUIZINFO (userid);
create index STUDY_QUIZINFO_epid_ind on STUDY_QUIZINFO (epid);
--试卷分值检查部分
alter table EXAMPAPER add ep_realscore number default 0 not null;
alter table EXAMPAPERBLOCK add realscore number default 0 not null;

--试题分值--引发大题分值的trigger；
create or replace trigger epb_a_i_u_d after delete or insert or update on exampaperblockquestion for each row
declare i_blockid number;
 	i_score number;
	i_oscore number;
begin
 	if inserting  then
	 	i_blockid :=:new.blockid;
	 	i_score := :new.score;
 		update exampaperblock set realscore= realscore+ i_score where id =  i_blockid;
 	elsif updating  then
 		i_blockid :=:new.blockid;
	 	i_score := :new.score;
	 	i_oscore :=:old.score;
 		update exampaperblock set realscore= realscore+( i_score-i_oscore) where id =  i_blockid;
 	elsif deleting then
 		i_blockid :=:old.blockid;
	 	i_oscore :=:old.score;
 		update exampaperblock set realscore= realscore-i_oscore where id =  i_blockid;
 	end if ;
end ;
create or replace trigger epb_b_i_u_d after delete or insert or update on exampaperblockquestion for each row
declare i_blockid number;
 	i_score number;
	i_oscore number;
begin
 	if inserting  then
	 	i_blockid :=:new.blockid;
	 	i_score := :new.score;
 		update exampaperblock set realscore= realscore+ i_score where id =  i_blockid;
 	elsif updating  then
 		i_blockid :=:new.blockid;
	 	i_score := :new.score;
	 	i_oscore :=:old.score;
 		update exampaperblock set realscore= realscore+( i_score-i_oscore) where id =  i_blockid;
 	elsif deleting then
 		i_blockid :=:old.blockid;
	 	i_oscore :=:old.score;
 		update exampaperblock set realscore= realscore-i_oscore where id =  i_blockid;
 	end if ;
end ;


create or replace trigger js_ep_score after delete or insert or update  on exampaperblock for each row
declare i_epid number;
 		i_score number;
		i_oscore number;
begin
 	if inserting  then
	 	i_epid :=:new.exampaperid;
	 	i_score := :new.realscore;
 		update exampaper set ep_realscore= ep_realscore+ i_score where id =  i_epid;
 	elsif updating  then
 		i_epid :=:new.exampaperid;
	 	i_score := :new.realscore;
	 	i_oscore :=:old.realscore;
 		update exampaper set ep_realscore= ep_realscore+( i_score-i_oscore) where id =  i_epid;
 	elsif deleting then
 		i_epid :=:old.exampaperid;
	 	i_oscore :=:old.realscore;
 		update exampaper set ep_realscore= ep_realscore-i_oscore where id =  i_epid;
 	end if ;
 end ;

alter table question add xx varchar2(4000);
update question set xx = content;
alter table question drop column content ;
alter table question add content clob;
update question set content = xx;
alter table question drop column xx ;
--- 角色 2011-06-30

create  SEQUENCE elrole_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create trigger auto_id_elrole before insert on elrole for each row 
when (new.id is null)
begin
     select elrole_sequence.nextval into :new.id from dual;
end;
alter table ELROLE modify ID not null;
alter table ELROLE modify NAME default '未知' not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table ELROLE
add constraint elrole_pk primary key (ID);

alter table EXAM_REPS add pracid number default 0 not null;
alter table EXAM_REPS add practimes number default 0 not null;
alter table EXAM_REPS add pracscore number default 0 not null;
alter table EXAM_REPS add shouldpass number default 0 not null;
alter table EXAMPAPER add queryurl varchar2(4000);
-- Create table
create table study_questions
(
  userid   number not null,
  roomid   number not null,
  epid     number not null,
  blockid  number not null,
  qid      number not null,
  myanswer varchar2(4000)
)
;
alter table STUDY_QUESTIONS
  add constraint study_questions_uid_fk foreign key (USERID)
  references eluser (ID);
alter table STUDY_QUESTIONS
  add constraint study_questions_rid_fk foreign key (ROOMID)
  references exam_room (ID);
alter table STUDY_QUESTIONS
  add constraint study_question_ep_fk foreign key (EPID)
  references exampaper (ID);
alter table STUDY_QUESTIONS
  add constraint study_questions_qid_fk foreign key (QID)
  references question (ID);

create table study_blocks
(
  USERID  NUMBER not null,
  ROOMID  NUMBER not null,
  EPID    NUMBER not null,
  BLOCKID NUMBER not null,
  myscore NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table study_blocks
  add constraint STUDY_blockS_RID_FK foreign key (ROOMID)
  references EXAM_ROOM (ID);
alter table study_blocks
  add constraint STUDY_blockS_UID_FK foreign key (USERID)
  references ELUSER (ID);
alter table study_blocks
  add constraint STUDY_blockS_EP_FK foreign key (EPID)
  references EXAMPAPER (ID);
  alter table STUDY_questions add sortid number default 0 not null;
alter table STUDY_BLOCKS
  drop constraint STUDY_BLOCKS_EP_FK;
alter table STUDY_BLOCKS
  add constraint STUDY_BLOCKS_EP_FK foreign key (EPID)
  references EXAMPAPER (ID) on delete cascade;
alter table STUDY_BLOCKS
  drop constraint STUDY_BLOCKS_RID_FK;
alter table STUDY_BLOCKS
  add constraint STUDY_BLOCKS_RID_FK foreign key (ROOMID)
  references EXAM_ROOM (ID) on delete cascade;
alter table STUDY_BLOCKS
  drop constraint STUDY_BLOCKS_UID_FK;
alter table STUDY_BLOCKS
  add constraint STUDY_BLOCKS_UID_FK foreign key (USERID)
  references ELUSER (ID) on delete cascade;
alter table STUDY_BLOCKS
  add constraint study_block_pk primary key (EPID, ROOMID, USERID, BLOCKID);
alter table STUDY_BLOCKS
  add constraint study_blocks_bid_fk foreign key (BLOCKID)
  references exampaperblock (ID) on delete cascade;
alter table STUDY_QUESTIONS
  drop constraint STUDY_QUESTIONS_QID_FK;
alter table STUDY_QUESTIONS
  add constraint STUDY_QUESTIONS_QID_FK foreign key (QID)
  references QUESTION (ID) on delete cascade;
alter table STUDY_QUESTIONS
  drop constraint STUDY_QUESTIONS_RID_FK;
alter table STUDY_QUESTIONS
  add constraint STUDY_QUESTIONS_RID_FK foreign key (ROOMID)
  references EXAM_ROOM (ID) on delete cascade;
alter table STUDY_QUESTIONS
  drop constraint STUDY_QUESTIONS_UID_FK;
alter table STUDY_QUESTIONS
  add constraint STUDY_QUESTIONS_UID_FK foreign key (USERID)
  references ELUSER (ID) on delete cascade;
alter table STUDY_QUESTIONS
  drop constraint STUDY_QUESTION_EP_FK;
alter table STUDY_QUESTIONS
  add constraint STUDY_QUESTION_EP_FK foreign key (EPID)
  references EXAMPAPER (ID) on delete cascade;
alter table STUDY_QUESTIONS
  add constraint study_questions_pk primary key (USERID, EPID, ROOMID, BLOCKID, QID);
alter table STUDY_QUESTIONS
  add constraint study_question_bid_fk foreign key (BLOCKID)
  references exampaperblock (ID) on delete cascade;
create index study_questions_qid_ind on STUDY_QUESTIONS (qid);
create index study_questions_bid_ind on STUDY_QUESTIONS (blockid);
create index study_questions_uid_ind on STUDY_QUESTIONS (userid);
create index study_questions_rid_ind on STUDY_QUESTIONS (roomid);
create index study_questions_eid_ind on STUDY_QUESTIONS (epid);
create index study_block_ep_ind on STUDY_BLOCKS (epid);
create index study_block_b_ind on STUDY_BLOCKS (blockid);
create index study_block_u_ind on STUDY_BLOCKS (userid);
create index study_block_r_ind on STUDY_BLOCKS (roomid);

alter table QUESTIONART modify ID not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table QUESTIONART
add constraint questionart_fk primary key (ID);
create  SEQUENCE QUESTIONART_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_QUESTIONART before insert on QUESTIONART for each row 
when (new.id is null)
begin
     select QUESTIONART_sequence.nextval into :new.id from dual;
end;

-- 评分 机制
alter table STUDY_QUESTIONS add score number;

create or replace trigger auto_quscore before update on study_questions for each row
declare u_epid number;
 	 u_qid number;
 	u_blockid number;
	u_myanswer varchar2(4000);
	score_ number ;
begin
 	
 	if score_ is null then
 		score_:=0;
 	end if;
 	select score_ into :new.myscore from dual ;
end;
--set serveroutput on 这个命令敲了吗
--oracle 评分状态
-1 未评分
-2 未知题型
-3 没设定评分规则
0 未通过（打字题）
1 已通过
-4 年龄未知
-5 错误未知
-6 小题题型不对

--选择题
--update study_questions  set myanswer ='1-=SpEl=-' where userid = 1 and roomid =11 and epid = 12 and blockid =66 and qid = 696
-- 邮件题
update study_questions  set myanswer ='teste432-=SpEl=-34243443-=SpEl=- rewtrew-=SpEl=- tretewtrwe-=SpEl=-' where userid = 1 and roomid =11 and epid = 12 and blockid =70 and qid = 662
-- 打字update study_questions  set myanswer ='8-=SpEl=-33-=SpEl=-291-=SpEl=-' where userid = 1 and roomid =11 and epid = 12 and blockid =67 and qid = 578
--部门
1156 总-- 44 -829 -74 --326
select * from bm_jgdm t where dmmc like '广东%' order by bh;
select * from bm_jgdm t where t.bh like '440100%' order by bh;
select * from bm_jgdm t where t.bh like '4401%\_%' escape '\' order by bh;
select length(bh), count(bh) from bm_jgdm where bh like '44%' group by length(bh);
select * from bm_jgdm where length(bh) <12 and bh like '44%' order by bh;

--练习

create  SEQUENCE eprac_quizinfo_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_eprac_quizinfo before insert on eprac_quizinfo for each row 
when (new.id is null)
begin
     select eprac_quizinfo_sequence.nextval into :new.id from dual;
end;
exec dep_set('44',0);

--11-7-15
create  SEQUENCE elfunc_sequence
	increment by 1 -- 每次加几个
	start with 1800 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create trigger auto_id_elfunc before insert on elfunc for each row 
when (new.id is null)
begin
     select elfunc_sequence.nextval into :new.id from dual;
end;
alter table ELFUNC
  add constraint elfunc_pk primary key (ID);
  
   alter table eprac_questions add x clob ;
   alter table eprac_questions drop column myanswer;
   alter table eprac_questions rename column x to myanswer;
    alter table study_questions add x clob ;
   alter table study_questions drop column myanswer;
   alter table study_questions rename column x to myanswer;
   update question set content = replace(content,'/exam/files','/gdgat/files'),subject = replace(subject,'/exam/files','/gdgat/files');
  --路径的问题。
 update question set content = replace(content,'/gdgat/files','/files'),subject = replace(subject,'/gdgat/files','/files');
   
   
   select * from eprac_questions;
   
   
 
SQL> alter table eprac_quizinfo modify myscore number(20,2);
SQL> alter table eprac_questions modify myscore number(20,2);
SQL> alter table eprac_blocks modify myscore number(20,2);
 
SQL> alter table study_room modify myscore number(20,2);
SQL> alter table study_quizinfo modify myscore number(20,2);
SQL> alter table study_questions modify myscore number(20,2);
SQL> alter table study_blocks modify myscore number(20,2);

 alter table course_dep drop column applydate;
alter table COURSE_DEP add column applydate date default sysdate not null;
 
create table eloffline
(
  id          NUMBER not null,
  name        VARCHAR2(500) not null,
  description VARCHAR2(4000),
  during      NUMBER default 0 not null,
  xueshi      NUMBER default 0 not null,
  score       NUMBER default 0 not null,
  begintime   DATE,
  endtime     DATE
);

create  SEQUENCE eloffline_sequence
	increment by 1 -- 每次加几个
	start with 1800 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create trigger auto_id_eloffline before insert on eloffline for each row 
when (new.id is null)
begin
     select eloffline_sequence.nextval into :new.id from dual;
end;
;create table eloffline2user
(
  userid NUMBER default 1 not null,
  offid  NUMBER default 1 not null
)
;
--课程类别
create table course_typeUser
(
  userid  NUMBER not null,
  ctypeid NUMBER not null
)
;alter table ELUSER modify SEX VARCHAR2(30);
alter table ELUSER modify XUHAO VARCHAR2(30);

CREATE TABLE  QUESTION_STUFF_USE_TYPE 
   ( USERID NUMBER NOT NULL ENABLE, 
	 stuffID NUMBER NOT NULL ENABLE
   );
   

create  sequence eqprac_quizinfo_sequence
	increment by 1 -- 每次加几个
	start with 1 -- 从1开始计数
	nomaxvalue -- 不设置最大值
	nocycle -- 一直累加，不循环
	cache 10 ;
create or replace trigger auto_id_eqprac_quizinfo before insert on eqprac_quizinfo for each row 
when (new.id is null)
begin
     select eqprac_quizinfo_sequence.nextval into :new.id from dual;
end;