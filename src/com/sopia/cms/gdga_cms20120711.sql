create table cms_label(
     lb_id number not null,
     lb_name nvarchar2(50),
     lb_code varchar2(100),
     lb_style varchar2(100),
     lb_modelId number,
     lb_modelType varchar2(100),
     lb_viewType varchar2(100),
     lb_record number,
     lb_contentType number,
     lb_titleLength number,
     lb_row number,
     lb_contentLength number,
     lb_remark nvarchar2(1000)
);
create sequence cmsLabel_SEQUENCE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 10;

create table cms_template(
       tmp_id number not null,
       tmp_name nvarchar2(50),
       tmp_jspTmp nvarchar2(500),
       tmp_jsp nvarchar2(500),
       tmp_remark nvarchar2(1000),
       tmp_type nvarchar2(100),
       tmp_type_id number
);
create sequence cmsTmp_SEQUENCE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 10;


create table cms_ColumnTemp(--À¸Ä¿Ä£°å±í
       ColumnTemp_id number not null,
       Column_name nvarchar2(50),
       Column_id number,
       column_type nvarchar2(500),
       tmp_id nvarchar2(500),
       tmp_name nvarchar2(100),
       tmp_jspTmp nvarchar2(100) 
);
create sequence cmsColumnTemp_SEQUENCE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 10;
 