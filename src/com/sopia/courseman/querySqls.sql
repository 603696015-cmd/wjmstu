####sql for coursetype manager##########
ctype.add=insert into course_type( name, description, parentid,lid,rid,mainimg) values(?,?,?,?,?,?)
ctype.delete= delete from course_type where id = ?
ctype.alter=update course_type set name = ? , description =?,parentid =?,mainimg =?  where id= ?
ctype.query.byid=select ct1.id,ct1.name,ct1.description,ct1.parentid,ct2.name,ct1.lid,ct1.rid,ct1.mainimg  from course_type ct1 left join course_type ct2 on ct1.parentid = ct2.id where ct1.id= ?
ctype.query.child=select id,name,parentid,lid,rid,mainimg  from course_type where parentid= ?
ctype.query.bypid =select id,name,parentid,lid,rid from course_type where parentid=?
ctype.course.query.byctid = select id from course where ctypeid = ?
ctype.course.ctype.set = update course set ctypeid = ? where id = ?
ctype.parent.set = update course_type set parentid = ? where parentid =?
ctype.lrid = select id,lid,rid from course_type where id=?
####sql for coursetype manager##########

#####sql for course manager######
course.add=insert into COURSE(name,ctypeid,creater,description, createtime,credit,mainimg,isLink,exurl,during,querytime,teacherinfo,studyplan,teachername,kj_appendix,jy_appendix,creditmod) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
course.man.mylist=select c.id,c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,ct.name,c.credit,c.during,c.isLink from COURSE c,course_type ct where c.ctypeid=ct.id and ct.lid>=? and ct.rid<=? and c.creater=? and c.name like ? order by c.createtime desc limit ?,?
course.man.mylist.size = select count(*) from COURSE c,course_type ct where c.ctypeid=ct.id and ct.lid>=? and ct.rid<=? and c.creater=? and c.name like ?  and (c.status =1|| c.status =2)
course.query.byid =select c.id,c.name,c.ctypeid,c.description,c.status,c.createtime,c.creater,ct.name,u.realname,c.credit,c.mainimg,c.isLink,c.exurl,c.during,c.querytime,c.teacherinfo,c.studyplan,c.teachername,c.kj_appendix,c.jy_appendix,c.creditmod from COURSE c,COURSE_TYPE ct,ELUSER u where c.ctypeid=ct.id and c.creater = u.id and c.id = ?
course.status.set = update course set status = ? where id = ?
course.status.set.byuser = update course set status = ? where id = ? and creater = ?
course.delete.list=select c.id,c.name,c.ctypeid, c.status,c.createtime,c.creater,ct.name,u.realname,c.modifytime,c.credit from course c, COURSE_TYPE ct,ELUSER u where c.ctypeid=ct.id and c.creater = u.id and c.status = ? limit ?,?
course.apply.this=select c.id,c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,ct.name,u.realname,c.credit,c.hot from course c, COURSE_TYPE ct,ELUSER u,DEPARTMENT dep \
 		where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ? and c.name like ? and dep.lid>=? and dep.rid <=? and ct.lid>= ? and ct.rid<=? limit ?,?
course.apply.size.this=select count(*) from course c, COURSE_TYPE ct,ELUSER u,DEPARTMENT dep \
 		where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ? and c.name like ? and dep.lid>=? and dep.rid <=? and ct.lid>= ? and ct.rid<=? 
course.apply.super=select c.id,c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,ct.name,u.realname,c.credit,c.hot from course c, COURSE_TYPE ct,ELUSER u,course_dep cd \
		where c.ctypeid=ct.id and c.creater = u.id and cd.courseid = c.id and c.status = ? and c.name like ? and cd.depid=? limit ?,?
course.apply.size.super=select count(*) from course c, COURSE_TYPE ct,ELUSER u,course_dep cd \
		where c.ctypeid=ct.id and c.creater = u.id and cd.courseid = c.id and c.status = ? and c.name like ? and cd.depid=?
course.canassign.users=select  u.id,u.realname,d.name,u.username from ELUSER u left join DEPARTMENT d on u.depid = d.id where d.id=? and u.id not in(select userid from study_course where courseid=?)
##course.assigned.users=select u.id,u.realname,d.name from COURSE_APPLY ca ,ELUSER u,DEPARTMENT d where ca.userid = u.id and u.depid = d.id and courseid=? and status=? and d.lid>=? and d.rid<=?
course.assigned.users=select u.id,u.realname,d.name from study_course ca ,ELUSER u,DEPARTMENT d where ca.userid = u.id and u.depid = d.id and courseid=? and status=? and d.id=?
course.canassign.deps = select dep.id ,dep.name from department dep where dep.lid>=? and dep.rid<=? and dep.id not in(select cd.depid from course_dep cd where cd.courseid =?) 
course.assigned.deps = select dep.id ,dep.name from department dep,course_dep cd where dep.lid>? and dep.rid<? and dep.id=cd.depid and cd.courseid =?  
course.alter=update COURSE set name = ?,ctypeid = ?,description = ?, credit = ?,mainimg = ?,exurl=?,during = ?,querytime=?,teacherinfo=?,studyplan=?,teachername=?,kj_appendix=?,jy_appendix=?,creditmod=? where creater=? and id = ?
course.assigne2user=insert into COURSE_APPLY(courseid,userid,applydate,status,valid)values(?,?,?,?,1)
course.assigne2user.delete=delete from study_course where courseid = ? and userid = ?
course.user.check = select * from COURSE_APPLY where courseid = ? and userid = ?
course.dep.check=select * from course_dep where  depid = ? and courseid =?
course.dep.add=insert into course_dep(courseid,depid,applydate) values(?,?,?)
course.dep.delete=delete from course_dep where courseid =? and depid=?
course.selected.list = select c.id,c.name,eu.id,eu.realname,ca.applydate,ct.id,ct.name,euc.id,euc.realname from course_apply ca,course c,eluser eu,course_type ct,eluser euc where c.creater = euc.id and ct.id = c.ctypeid and ca.courseid = c.id and ca.userid = eu.id and ca.valid = ? limit ?,?
course.selected.size = select count(*)  from course_apply ca,course c,eluser eu,course_type ct,eluser euc where c.creater = euc.id and ct.id = c.ctypeid and ca.courseid = c.id and ca.userid = eu.id and ca.valid = ? 
course.selected.set =update course_apply set valid = ? where userid = ? and courseid= ?
course.hot.set = update course set hot= ? where id = ?

course.study.delete = select scd.userid ,eu.realname,scd.courseid,c.name,scd.deletedate from study_course_delete scd,eluser eu,course c where scd.userid = eu.id and c.id = scd.courseid order by scd.deletedate desc limit ?,? 
course.study.delete.size = select count(*) from study_course_delete scd,eluser eu,course c where scd.userid = eu.id and c.id = scd.courseid
course.study.delete.op = delete from study_course_delete where userid = ? and courseid= ?
course.study.delete.op.yes.ca = delete from course_apply where userid = ? and courseid= ?
course.study.delete.op.yes.sc = delete from study_course where userid = ? and courseid= ?
course.study.delete.op.yes.scp = delete from study_cpage where userid = ? and cpid in (select id from course_page where courseid= ?)

#####sql for course manager######
######### sql for examroom#######
eroom.add=insert into exam_room( courseid,createrid, title,description, location, supervisor,begintime, endtime,paperid,iscommon, passgrade) values(?,?,?,?,?,?,?,?,?,?,?)
eroom.delete = delete from exam_room where id = ?
eroom.alter=update exam_room set title = ?,description = ?, location = ?, supervisor = ?, begintime = ?, endtime = ?,paperid = ?, passgrade=? where id = ?
eroom.query.bycid=select er.id , er.title, er.supervisor,eu.realname,er.begintime, er.endtime,er.location,er.courseid,er.passgrade from exam_room er left join eluser eu on eu.id=er.supervisor where er.courseid=? 
eroom.query.byid=select er.id , er.title,er.supervisor,eu.realname,er.begintime, er.endtime,er.location,er.courseid, c.name cname ,er.paperid, er.description,er.passgrade  from exam_room er left join eluser eu on eu.id=er.supervisor  left join course c on c.id= er.courseid where er.id=? 
eroom.query.can.assignuser=select u.id,u.realname,d.name from ELUSER u left join DEPARTMENT d on u.depid = d.id where u.id not in(select ra.userid from study_quizinfo ra where ra.roomid=? )
eroom.query.assigneduser=select u.id,u.realname,d.name from ELUSER u left join DEPARTMENT d on u.depid = d.id where u.id in(select ra.userid from study_quizinfo ra where ra.roomid = ?)
eroom.assigneduser.delete=delete from room_assign where userid = ? and courseid=?
eroom.assigneduser.add=insert into room_assign(userid,roomid) values(?,?)
eroom.assigneduser.check=select * from  study_quizinfo where userid = ? and roomid =?
eroom.query.bycidandt=select id, title, description, location,supervisor, begintime, endtime from  Exam_room where title like ? and courseid =?
eroom.query.byuid=select er.id , er.title, er.supervisor,eu.realname,er.begintime, er.endtime,er.location,er.courseid,c.name from exam_room er left join eluser eu on eu.id=er.supervisor left join course c on c.id = er.courseid where er.createrid=? and er.title like ? order by er.begintime desc limit ?,?
eroom.query.byuid.size = select count(*) from exam_room er left join eluser eu on eu.id=er.supervisor left join course c on c.id = er.courseid where er.createrid=? and er.title like ? 
eroom.query.bydepid=select er.id , er.title, er.createrid,eu.realname,er.begintime, er.endtime,er.location,er.courseid,c.name from exam_room er left join eluser eu on eu.id=er.createrid left join course c on c.id = er.courseid left join department dep on eu.depid = dep.id  where dep.lid>=? and dep.rid <=? and er.title like ? order by er.begintime desc limit ?,?
eroom.query.bydepid.size=select count(*) from exam_room er left join eluser eu on eu.id=er.createrid left join course c on c.id = er.courseid left join department dep on eu.depid = dep.id  where dep.lid>=? and dep.rid <=? and er.title like ?  
eroom.whithout.course=select er.id , er.title, er.supervisor,eu.realname,er.begintime, er.endtime,er.location,er.passgrade from exam_room er left join eluser eu on eu.id=er.supervisor where er.iscommon=1 and er.createrid=? order by er.begintime limit ?,?  



#######sql for course page manager######
cpage.add=insert into course_page (courseid,title,type,createtime,page, sortid,page_url,property,queryTime,during,skipable) values(?,?,?,?,?,?,?,?,?,?,?)
cpage.alter=update course_page set title=?,type=?,page=?,page_url=?,property=?,queryTime=?,during=?,skipable=? where id = ?
cpage.query.max.sortid=select max(sortid) from course_page where courseid= ?
cpage.query.list.bycid=select id,title,courseid,type,createtime,modifytime,sortid,property,during from course_page where courseid = ? order by sortid
cpage.query.byid=select id,title,page,type,page_url,property,sortid,queryTime,skipable,during,courseid from course_page where id= ?
cpage.query.cidandsid= select courseid,sortid from course_page where id= ? 
cpage.bigsort.set=update course_page set sortid = sortid-1 where sortid> ? and courseid= ?
cpage.query.first.bycid=select id from course_page where courseid = ? and sortid= 1
cpage.delete=delete from course_page where id = ?
########## sql for practicepaper####
ppaper.query.bycidandpid=select pp.id, ep.id,ep.title,ep.israndom,ep. modifytime,ep.createtime,pp.sortid,pp.skipable from practicepaper pp left join exampaper ep on pp.epid = ep.id where pp.courseid=? and pp.cpid=? order by pp.sortid
ppaper.query.byid=select id,epid,courseid,cpid,sortid,skipable from practicepaper where id= ?
ppaper.delete = delete from practicepaper where id = ?
ppaper.bigsort.set=update practicepaper set sortid = sortid-1 where sortid>? and courseid=? and cpid=?
ppaper.check.incp=select * from practicepaper where  epid = ? and courseid =? and cpid=?
ppaper.add=insert into practicepaper( epid,courseid,cpid,skipable,sortid) values(?,?,?,?,?)
ppaper.msortid.incp=select max(sortid) from practicepaper where courseid=? and cpid=?

qpaper.read.byrid = select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myScore,sqi.endtime,eu.realname from  study_quizinfo sqi left join ELUSER eu on sqi.userid = eu.id where roomid = ? limit ?,?
qpaper.read.byrid.size = select count(*) from  study_quizinfo sqi left join ELUSER eu on sqi.userid = eu.id where roomid = ?
qpaper.requiz =qpaper.requiz = update study_quizinfo set myanswer ='',passtime = 0,status = 1,myscore=0,ispassed=0 where id=?
##########sql for simexampaper #########
spaper.delete=delete from SIMEXAMPAPER where id = ?
spaper.check.insp=select * from SIMEXAMPAPER where  epid = ? and courseid =?
spaper.add=insert into SIMEXAMPAPER( epid,courseid,begintime,endtime) values(?,?,?,?)
spaper.query.bycid=select ep.id,ep.title,ep.israndom,pp.begintime,pp.endtime,pp.id from SIMEXAMPAPER pp left join exampaper ep on pp.epid = ep.id left join course c on pp.courseid = c.id where pp.courseid=? order by pp.begintime
spaper.query.byid=select pp.id,pp.epid,pp.courseid,pp.begintime,pp.endtime from SIMEXAMPAPER pp where pp.id = ?
spaper.requiz = delete from student_siminfo where id=?
spaper.read.list = select sqi.id ,sqi.userid, sqi.epid, sqi.status, sqi.myScore,\
							sqi.endtime,eu.realname,ep.title from  STUDENT_SIMINFO sqi left join \
							ELUSER eu on sqi.userid = eu.id left join exampaper ep on ep.id = sqi.epid where sqi.courseid = ? limit ?,?
							
##########sql for quizpaper #########
qpaper.check.inqp=select * from QUIZPAPER where  exampaperid = ? and courseid =?
qpaper.delete=delete from QUIZPAPER where id = ?
qpaper.add=insert into QUIZPAPER( exampaperid,courseid) values(?,?)
qpaper.query.bycid=select pp.id,ep.id,ep.title,ep.israndom,ep. modifytime,ep.createtime,ep.during from QUIZPAPER pp left join exampaper ep on pp.exampaperid = ep.id left join course c on pp.courseid = c.id where pp.courseid=? 
