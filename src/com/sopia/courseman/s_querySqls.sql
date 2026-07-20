####sql for coursetype manager##########
ctype.add=insert into course_type( name, description, parentid,lid,rid,mainimg) values(?,?,?,?,?,?)
ctype.delete= delete from course_type where id = ?
ctype.alter=update course_type set name = ? , description =?,parentid =?,mainimg =? where id= ?
ctype.query.byid=select ct1.id,ct1.name,ct1.description,ct1.parentid,ct2.name,ct1.lid,ct1.rid,ct1.mainimg from course_type ct1 left join course_type ct2 on ct1.parentid = ct2.id where ct1.id= ?
ctype.query.child=select id,name,parentid,lid,rid,mainimg from course_type where parentid= ?
ctype.query.bypid =select id,name,parentid,lid,rid from course_type where parentid=?
ctype.course.query.byctid = select id from course where ctypeid = ?
ctype.course.ctype.set = update course set ctypeid = ? where id = ?
ctype.parent.set = update course_type set parentid = ? where parentid =?
ctype.lrid = select id,lid,rid from course_type where id=?
####sql for coursetype manager##########

#####sql for course manager######
course.add=insert into course(name,ctypeid,creater,description,createtime,credit,mainimg,islink,exurl,during,querytime,teacherinfo,studyplan,teachername,kj_appendix,jy_appendix,creditmod,notenumber,notedate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
course.man.mylist=select * from (select c.id,c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,ct.name ctname,c.credit,c.during,c.islink,row_number() over ( order by c.createtime desc) rownum from course c,course_type ct where c.ctypeid=ct.id and ct.lid>=? and ct.rid<=? and c.creater=? and c.name like ?)t where t.rownum between ? and ?
course.man.mylist.size = select count(*) from course c,course_type ct where c.ctypeid=ct.id and ct.lid>=? and ct.rid<=? and c.creater=? and c.name like ?  and (c.status =1 or c.status =2)
course.query.byid =select c.id,c.name,c.ctypeid,c.description,c.status,c.createtime,c.creater,ct.name,u.realname,c.credit,c.mainimg,c.islink,c.exurl,c.during,c.querytime,c.teacherinfo,c.studyplan,c.teachername,c.kj_appendix,c.jy_appendix,c.creditmod,c.notenumber,c.notedate from course c,course_type ct,eluser u where c.ctypeid=ct.id and c.creater = u.id and c.id = ?
course.status.set = update course set status = ? where id = ?
course.status.set.byuser = update course set status = ? where id = ? and creater = ?
course.delete.list=select * from (select c.id,c.name,c.ctypeid, c.status,c.createtime,c.creater,ct.name ctname,u.realname,c.modifytime,c.credit,row_number() over(order by c.createtime desc ) rownum from course c, course_type ct,eluser u where c.ctypeid=ct.id and c.creater = u.id and c.status = ?) t where t.rownum between ? and ?
course.apply.this=select * from (select c.id,c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,ct.name ctname,u.realname,c.credit,c.hot,row_number() over(order by c.createtime desc ) rownum  from course c, course_type ct,eluser u,department dep \
 		where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ? and c.name like ? and dep.lid>=? and dep.rid <=?  and ct.lid>= ? and ct.rid<=?) t where t.rownum between ? and ?
course.apply.size.this=select count(*) from course c, course_type ct,eluser u,department dep \
 		where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ? and c.name like ? and dep.lid>=? and dep.rid <=? and  ct.lid>= ? and ct.rid<=?
course.apply.super=select * from (select c.id,c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,ct.name ctname,u.realname,c.credit,c.hot,row_number() over(order by c.createtime desc ) rownum  from course c, course_type ct,eluser u,course_dep cd \
		where c.ctypeid=ct.id and c.creater = u.id and cd.courseid = c.id and c.status = ? and c.name like ? and cd.depid=? ) t where t.rownum between ? and ?
course.apply.size.super=select count(*) from course c, course_type ct,eluser u,course_dep cd \
		where c.ctypeid=ct.id and c.creater = u.id and cd.courseid = c.id and c.status = ? and c.name like ? and cd.depid=?
course.canassign.users=select u.id,u.realname,d.name,u.username from eluser u left join department d on u.depid = d.id where d.id =? and u.id not in(select userid from study_course where courseid=?) 
##d.lid>=? and d.rid<=? and u.id not in(select userid from course_apply where courseid=?)
course.assigned.users=select u.id,u.realname ,d.name,u.username from study_course ca ,eluser u,department d where ca.userid = u.id and u.depid = d.id and courseid=? and status=? and d.id = ?
##lid>=? and d.rid<=?
course.canassign.deps = select dep.id ,dep.name from department dep where dep.lid>=? and dep.rid<=? and dep.id not in(select cd.depid from course_dep cd where cd.courseid =?) 
course.assigned.deps = select dep.id ,dep.name from department dep,course_dep cd where dep.lid>=? and dep.rid<=? and dep.id=cd.depid and cd.courseid =?  
course.alter=update course set name = ?,ctypeid = ?,description  = ?, credit = ?,mainimg = ?,exurl=?,during = ?,querytime=?,teacherinfo=?,studyplan=?,teachername=?,kj_appendix=?,jy_appendix=? ,creditmod = ?,notenumber=?,notedate=? where creater=? and id = ?
course.assigne2user=exec into_sc ?,?,?,? 
course.assigne2user.delete=delete from study_course where courseid = ? and userid = ?
course.user.check = select * from course_apply where courseid = ? and userid = ?
course.dep.check=select * from course_dep where  depid = ? and courseid =?
course.dep.add=insert into course_dep(courseid,depid,applydate) values(?,?,?)
course.dep.delete=delete from course_dep where courseid =? and depid=?
course.selected.list =select * from ( select c.id,c.name,eu.id,eu.realname,ca.applydate,ct.id ctid,ct.name ctname,euc.id eucid,euc.realname eucrealname,row_number() over(order by ca.applydate desc) rownum from course_apply ca,course c,eluser eu,course_type ct,eluser euc where c.creater = euc.id and ct.id = c.ctypeid and ca.courseid = c.id and ca.userid = eu.id and ca.valid = ?  ) t where t.rownum between ? and ?
course.selected.size = select count(*)  from course_apply ca,course c,eluser eu,course_type ct,eluser euc where c.creater = euc.id and ct.id = c.ctypeid and ca.courseid = c.id and ca.userid = eu.id and ca.valid = ? 
course.selected.set =update course_apply set valid = ? where userid = ? and courseid= ?
course.hot.set = update course set hot= ? where id = ?

course.study.delete = select * from (select scd.userid ,eu.realname,scd.courseid,c.name,scd.deletedate ,row_number() over(order by scd.deletedate desc ) rownum from study_course_delete scd,eluser eu,course c where scd.userid = eu.id and c.id = scd.courseid )t where t.rownum between ? and ? 
course.study.delete.size = select count(*) from study_course_delete scd,eluser eu,course c where scd.userid = eu.id and c.id = scd.courseid
course.study.delete.op = delete from study_course_delete where userid = ? and courseid= ?
course.study.delete.op.yes.ca = delete from course_apply where userid = ? and courseid= ?
course.study.delete.op.yes.sc = delete from study_course where userid = ? and courseid= ?
course.study.delete.op.yes.scp = delete from study_cpage where userid = ? and cpid in (select id from course_page where courseid= ?)

#####sql for course manager######
######### sql for examroom#######
eroom.add=insert into exam_room( courseid,createrid, title,description, location, begintime, endtime,iscommon, passgrade,score,erlibid,type,pracid,practimes,pracscore) values(?,?,?,?, ?,?,?,?,?,?,?,?,?,? ,?)
eroom.delete = delete from exam_room where id = ?
eroom.alter=update exam_room set title = ?,description = ?, location = ?,  begintime = ?, endtime  = ?,passgrade=?,score= ?,erlibid=?,type=?,pracid=?,practimes=?,pracscore=? where id = ?
eroom.query.bycid=select er.id , er.title, er.supervisor,eu.realname,er.begintime, er.endtime,er.location,er.courseid,er.passgrade from exam_room er left join eluser eu on eu.id=er.supervisor where er.courseid=? 
eroom.query.byid=select er.id , er.title,er.begintime, er.endtime,er.location,er.courseid, c.name cname , er.description,er.passgrade,er.score,er.erlibid,er.type,er.pracid,ep.title,er.practimes,er.pracscore from exam_room er left join exampaper ep on ep.id=er.pracid left join course c on c.id= er.courseid where er.id=? 
eroom.query.can.assignuser=select u.id,u.realname,d.name from eluser u left join department d on u.depid = d.id where u.id not in(select ra.userid from study_quizinfo ra where ra.roomid=? )
eroom.query.assigneduser=select u.id,u.realname,d.name from eluser u left join department d on u.depid = d.id where u.id in(select ra.userid from study_quizinfo ra where  ra.roomid = ?)
eroom.assigneduser.delete=delete from room_assign where userid = ? and courseid=?
eroom.assigneduser.add=insert into room_assign(userid,courseid,roomid) values(?,?,?)
eroom.assigneduser.check=select * from  room_assign where userid = ? and courseid =?
eroom.query.bycidandt=select id, title, description, location, begintime, endtime from  exam_room where title like ? and courseid =?
eroom.query.byuid=select * from (select er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name,row_number() over( order by er.begintime desc ) rownum from exam_room er left join course c on c.id = er.courseid where er.createrid=? and er.title like ?) t where t.rownum between ? and ?
eroom.query.byuid.size = select count(*) from exam_room er left join course c on c.id = er.courseid where er.createrid=? and er.title like ? 
eroom.query.bydepid=select * from (select er.id , er.title, er.createrid,eu.realname,er.begintime, er.endtime,er.location,er.courseid,c.name,erlib.id erlibid,erlib.name erlibname , row_number() over( order by er.begintime desc ) rownum from exam_room er left join eluser eu on eu.id=er.createrid left join course c on c.id = er.courseid left join department dep on eu.depid = dep.id left join eroom_lib erlib on er.erlibid = erlib.id where dep.lid>=? and dep.rid <=? and er.title like ?) t where t.rownum between ? and ?
eroom.query.bydepid.size=select count(*) from exam_room er left join eluser eu on eu.id=er.createrid left join course c on c.id = er.courseid left join department dep on eu.depid = dep.id  where dep.lid>=? and dep.rid <=? and er.title like ?  
eroom.whithout.course=select * from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type ,row_number() over( order by er.begintime desc)rownum from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid where er.iscommon=1 and erlib.lid>=? and erlib.rid<=?)t where t.rownum between ? and ? 

eroomlib.add=insert into eroom_lib( name, description, parentid,lid,rid ) values(?,?,?,?,?)
eroomlib.delete= delete from eroom_lib where id = ?
eroomlib.alter=update eroom_lib set name = ? , description =?,parentid =?  where id= ?
eroomlib.query.byid=select ct1.id,ct1.name,ct1.description,ct1.parentid,ct2.name,ct1.lid,ct1.rid from eroom_lib ct1 left join eroom_lib ct2 on ct1.parentid = ct2.id where ct1.id= ?
eroomlib.query.child=select id,name,parentid,lid,rid from eroom_lib where parentid= ?
eroomlib.query.bypid =select id,name,parentid,lid,rid from eroom_lib where parentid=?
eroomlib.course.query.byctid = select id from exam_room where erlibid = ?
eroomlib.course.eroomlib.set = update exam_room set erlibid = ? where id = ?
eroomlib.parent.set = update eroom_lib set parentid = ? where parentid =?
eroomlib.lrid = select id,lid,rid from eroom_lib where id=?

#######sql for course page manager######
cpage.add=insert into course_page (courseid,title,type,createtime,page, sortid,page_url,property,querytime,during,skipable) values(?,?,?,?,?,?,?,?,?,?,?)
cpage.alter=update course_page set title=?,type=?,page=?,page_url=?,property=?,querytime=?,during=?,skipable=? where id = ?
cpage.query.max.sortid=select max(sortid) from course_page where courseid= ?
cpage.query.list.bycid=select id,title,courseid,type,createtime,modifytime,sortid,property,during from course_page where courseid = ? order by sortid
cpage.query.byid=select id,title,page,type,page_url,property,sortid,querytime,skipable,during,courseid from course_page where id= ?
cpage.query.cidandsid= select courseid,sortid from course_page where id= ? 
cpage.bigsort.set=update course_page set sortid = sortid-1 where sortid> ? and courseid= ?
cpage.query.first.bycid=select id from course_page where courseid = ? and sortid= 1
cpage.delete=delete from course_page where id = ?
########## sql for practicepaper####
ppaper.query.bycidandpid=select pp.id, ep.id,ep.title,ep.showmod,ep. modifytime,ep.createtime,pp.sortid,pp.skipable from practicepaper pp left join exampaper ep on pp.epid = ep.id where pp.courseid=? and pp.cpid=? order by pp.sortid
ppaper.query.byid=select id,epid,courseid,cpid,sortid,skipable from practicepaper where id= ?
ppaper.delete = delete from practicepaper where id = ?
ppaper.bigsort.set=update practicepaper set sortid = sortid-1 where sortid>? and courseid=? and cpid=?
ppaper.check.incp=select * from practicepaper where  epid = ? and courseid =? and cpid=?
ppaper.add=insert into practicepaper( epid,courseid,cpid,skipable,sortid) values(?,?,?,?,?)
ppaper.msortid.incp=select max(sortid) from practicepaper where courseid=? and cpid=?

qpaper.read.byrid = select * from(select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myscore,sqi.endtime,eu.realname,row_number() over(order by sqi.endtime desc) as rownum from  study_quizinfo sqi left join eluser eu on sqi.userid = eu.id where roomid = ? )t where t.rownum between ? and ?
qpaper.read.byrid.size = select count(*) from  study_quizinfo sqi left join eluser eu on sqi.userid = eu.id where roomid = ?
qpaper.requiz = update study_quizinfo set myanswer ='',passtime = 0,status = 1,myscore=0,ispassed=0 where id=?
##########sql for simexampaper #########
spaper.delete=delete from simexampaper where id = ?
spaper.check.insp=select * from simexampaper where  epid = ? and courseid =?
spaper.add=insert into simexampaper( epid,courseid,begintime,endtime) values(?,?,?,?)
spaper.query.bycid=select ep.id,ep.title,ep.showmod,pp.begintime,pp.endtime,pp.id from simexampaper pp left join exampaper ep on pp.epid = ep.id left join course c on pp.courseid = c.id where pp.courseid=? order by pp.begintime
spaper.query.byid=select pp.id,pp.epid,pp.courseid,pp.begintime,pp.endtime from simexampaper pp where pp.id = ?
spaper.requiz = delete from student_siminfo where id=?
spaper.read.list = select * from (select sqi.id ,sqi.userid, sqi.epid, \
							 sqi.status, sqi.myscore \
							 sqi.endtime,eu.realname,ep.title,row_number() over (order by sqi.endtime desc ) rownum from  student_siminfo sqi left join \
							 eluser eu on sqi.userid = eu.id left join exampaper ep on ep.id = sqi.epid where sqi.courseid = ? )t where t.rownum between ? and ?
##########sql for quizpaper #########
qpaper.check.inqp=select * from quizpaper where  exampaperid = ? and courseid =?
qpaper.delete=delete from quizpaper where id = ?
qpaper.add=insert into quizpaper( exampaperid,courseid) values(?,?)
qpaper.query.bycid=select pp.id,ep.id,ep.title,ep.showmod,ep. modifytime,ep.createtime,ep.during from quizpaper pp left join exampaper ep on pp.exampaperid = ep.id left join course c on pp.courseid = c.id where pp.courseid=? 

examprac.list=select * from (select epr.id,epr.title,epr.begintime,epr.endtime,count(epra.userid) xx,row_number() over(order by id ) rownum from examprac epr left join examprac_assign epra on epra.eprid= epr.id where epr.userid =? group by  epr.id,epr.title,epr.begintime,epr.endtime)t  where t.rownum between ? and ?
