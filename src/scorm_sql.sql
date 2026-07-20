/*==============================================================*/
/* SCORM 2004                            */
/* DBMS name:      ORACLE Version 9i                            */
/* Created on:     2011-11-3 16:28:36                           */
/*==============================================================*/



-- Type package declaration
create or replace package PDTypes  
as
    TYPE ref_cursor IS REF CURSOR;
end;

-- Integrity package declaration
create or replace package IntegrityPackage AS
 procedure InitNestLevel;
 function GetNestLevel return number;
 procedure NextNestLevel;
 procedure PreviousNestLevel;
 end IntegrityPackage;
/

-- Integrity package definition
create or replace package body IntegrityPackage AS
 NestLevel number;

-- Procedure to initialize the trigger nest level
 procedure InitNestLevel is
 begin
 NestLevel := 0;
 end;


-- Function to return the trigger nest level
 function GetNestLevel return number is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 return(NestLevel);
 end;

-- Procedure to increase the trigger nest level
 procedure NextNestLevel is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 NestLevel := NestLevel + 1;
 end;

-- Procedure to decrease the trigger nest level
 procedure PreviousNestLevel is
 begin
 NestLevel := NestLevel - 1;
 end;

 end IntegrityPackage;
/

create sequence S_ITEMINFO
/

create sequence S_SCOCOMMENTS
/

/*==============================================================*/
/* Table: APPLICATIONDATA                                       */
/*==============================================================*/
create table APPLICATIONDATA  (
   DATANAME             VARCHAR2(50)                    not null,
   TEXTVALUE            VARCHAR2(50),
   NUMBERVALUE          INTEGER,
   constraint PK_APPLICATIONDATA primary key (DATANAME)
)
/

/*==============================================================*/
/* Index: NEXTCOURSEID                                          */
/*==============================================================*/
create index NEXTCOURSEID on APPLICATIONDATA (
   DATANAME ASC
)
/

/*==============================================================*/
/* Index: NUMBERVALUE                                           */
/*==============================================================*/
create index NUMBERVALUE on APPLICATIONDATA (
   NUMBERVALUE ASC
)
/

/*==============================================================*/
/* Table: COURSEINFO                                            */
/*==============================================================*/
create table COURSEINFO  (
   COURSEID             VARCHAR2(50)                    not null,
   COURSETITLE          VARCHAR2(50),
   ACTIVE               NUMBER(1)                       not null,
   IMPORTDATETIME       DATE,
   constraint PK_COURSEINFO primary key (COURSEID)
)
/

/*==============================================================*/
/* Table: ITEMINFO                                              */
/*==============================================================*/
create table ITEMINFO  (
   COURSEID             VARCHAR2(255),
   ORGANIZATIONIDENTIFIER VARCHAR2(255),
   ITEMIDENTIFIER       VARCHAR2(255),
   RESOURCEIDENTIFIER   VARCHAR2(255),
   LAUNCH               VARCHAR2(255),
   TYPE                 VARCHAR2(50),
   TITLE                VARCHAR2(255),
   PARAMETERSTRING      VARCHAR2(255),
   PERSISTSTATE         VARCHAR2(255),
   DATAFROMLMS          VARCHAR2(255),
   MINNORMALIZEDMEASURE VARCHAR2(50),
   ATTEMPTABSOLUTEDURATIONLIMIT VARCHAR2(255),
   TIMELIMITACTION      VARCHAR2(255),
   COMPLETIONTHRESHOLD  VARCHAR2(255),
   NEXT                 NUMBER(1)                       not null,
   PREVIOUS             NUMBER(1)                       not null,
   EXIT                 NUMBER(1)                       not null,
   ABANDON              NUMBER(1)                       not null,
   ACTIVITYID           NUMBER(6)                       not null,
   constraint PK_ITEMINFO primary key (ACTIVITYID)
)
/

/*==============================================================*/
/* Table: SCOCOMMENTS                                           */
/*==============================================================*/
create table SCOCOMMENTS  (
   ACTIVITYID           INTEGER,
   COMMENTID            NUMBER(6)                       not null,
   "COMMENT"            VARCHAR2(255),
   COMMENTDATETIME      DATE,
   COMMENTLOCATION      VARCHAR2(255),
   constraint PK_SCOCOMMENTS primary key (COMMENTID)
)
/

/*==============================================================*/
/* Index: ACTIVITYID                                            */
/*==============================================================*/
create index ACTIVITYID on SCOCOMMENTS (
   ACTIVITYID ASC
)
/

/*==============================================================*/
/* Table: USERCOURSEINFO                                        */
/*==============================================================*/
create table USERCOURSEINFO  (
   USERID               VARCHAR2(50),
   COURSEID             VARCHAR2(50)
)
/

/*==============================================================*/
/* Index: COURSEID                                              */
/*==============================================================*/
create index COURSEID on USERCOURSEINFO (
   COURSEID ASC
)
/

/*==============================================================*/
/* Index: COURSEINFOUSERCOURSEINFO                              */
/*==============================================================*/
create index COURSEINFOUSERCOURSEINFO on USERCOURSEINFO (
   COURSEID ASC
)
/

/*==============================================================*/
/* Index: COURSEINFOUSERCOURSEINFO1                             */
/*==============================================================*/
create index COURSEINFOUSERCOURSEINFO1 on USERCOURSEINFO (
   COURSEID ASC
)
/

/*==============================================================*/
/* Index: USERID                                                */
/*==============================================================*/
create index USERID on USERCOURSEINFO (
   USERID ASC
)
/

/*==============================================================*/
/* Index: USERINFOUSERCOURSEINFO                                */
/*==============================================================*/
create index USERINFOUSERCOURSEINFO on USERCOURSEINFO (
   USERID ASC
)
/

/*==============================================================*/
/* Table: USERINFO                                              */
/*==============================================================*/
create table USERINFO  (
   USERID               VARCHAR2(50)                    not null,
   LASTNAME             VARCHAR2(50),
   FIRSTNAME            VARCHAR2(50),
   ADMIN                NUMBER(1)                       not null,
   PASSWORD             VARCHAR2(50),
   ACTIVE               NUMBER(1)                       not null,
   AUDIOLEVEL           VARCHAR2(50),
   AUDIOCAPTIONING      INTEGER,
   DELIVERYSPEED        VARCHAR2(50),
   LANGUAGE             VARCHAR2(50),
   constraint PK_USERINFO primary key (USERID)
)
/


create trigger TIB_ITEMINFO before insert
on ITEMINFO for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ACTIVITYID" uses sequence S_ITEMINFO
    select S_ITEMINFO.NEXTVAL INTO :new.ACTIVITYID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger TIB_SCOCOMMENTS before insert
on SCOCOMMENTS for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "COMMENTID" uses sequence S_SCOCOMMENTS
    select S_SCOCOMMENTS.NEXTVAL INTO :new.COMMENTID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

/*==============================================================*/
/* Global Table                                         */
/*==============================================================*/
drop table COURSESTATUS cascade constraints;

drop table OBJECTIVES cascade constraints;

/*==============================================================*/
/* Table: COURSESTATUS                                          */
/*==============================================================*/
create table COURSESTATUS  (
   COURSEID             VARCHAR2(255)                   not null,
   LEARNERID            VARCHAR2(255)                   not null,
   SATISFIED            VARCHAR2(50),
   MEASURE              VARCHAR2(50),
   COMPLETED            VARCHAR2(50),
   constraint PK_COURSESTATUS primary key (COURSEID, LEARNERID)
)
/

/*==============================================================*/
/* Table: OBJECTIVES                                            */
/*==============================================================*/
create table OBJECTIVES  (
   OBJID                VARCHAR2(255)                   not null,
   LEARNERID            VARCHAR2(255),
   SATISFIED            VARCHAR2(50),
   MEASURE              VARCHAR2(50),
   SCOPEID              VARCHAR2(255),
   constraint PK_OBJECTIVES primary key (OBJID)
)
/

INSERT INTO APPLICATIONDATA VALUES('nextCourseID',NULL,0)
/

INSERT INTO USERINFO VALUES ('admin', 'Admin', 'Joe', '1', 'admin', '1', '1.0', '1.0', '', NULL)
/
