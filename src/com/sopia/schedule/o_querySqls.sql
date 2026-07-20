###############sql for Statistics######################
customreport.insert=insert into customreport (name) values (?)
customreport.list=select b.*,rn from (select a.*,rownum rn from (select * from customreport  order by id desc) a where rownum<=?)b where rn>=?
customreport.list.no.page=select * from customreport where resultpage=? 
customreport.list.size=select count(1) from customreport 
customreport.query.byid=select * from customreport where id=?
customreport.query.currentval=select customreport_sequence.currval from dual
customreport.update.byid=update customreport set sqlcondition=?,pagesize=? where id=?
customreport.update.tree=update customreport set showtree=? where id=?
customreport.update.search=update customreport set showsearch=?,searchhtmlfield=?,searchtype=?,searchhtml=empty_blob() where id=?
customreport.update.final=update customreport set sql=?,lable=empty_blob() where id=?
customreport.update.jsp=update customreport set resultpage=? where id=?


################
customreport_jisuanzu_insert=insert into customreport_jisuanzu (customreportid,columnname,type) values (?,?,?)
customreport_jisuanzu_list=select * from customreport_jisuanzu order by orderid asc
customreport_jisuanzu_list_byid = select * from customreport_jisuanzu where customreportid=? order by orderid asc
customreport_jisuanzu_list_buid_relatetype_2=select * from customreport_jisuanzu where customreportid=? and relatetype=2 order by id asc
customreport_jisuanzu_update_bycolumnname=update customreport_jisuanzu set formula=?,type=?,formatnumber=?,viewjindutiao=?,relatetype=? where columnname=?
customreport_jisuanzu_update_bycolumnname_=update customreport_jisuanzu set formula=?,type=?,showview=?,relatetype=?,relatecolumnname=? where columnname=?
customreport_jisuanzu_query_bycolumnname=select * from customreport_jisuanzu where columnname=? order by orderid asc
customreport_jisuanzu_boolean_checknameisexist=select columnname from customreport_jisuanzu where customreportid=? and columnname=?
customreport_jisuan_query_id=select customreport_jisuanzu_sequence.currval from customreport_jisuanzu
customreport_jisuan_update_orderid_by_id=update customreport_jisuanzu set orderid=? where id=?
customreport_jisuanzu_update_by_columnname_and_customreportid=update customreport_jisuanzu set orderid=? where columnname=? and customreportid=?
customreport_jisuanzu_delete_by_id=delete from customreport_jisuanzu where id=?