######sql for course study#########
study.course.query.byuid=select c.id, c.name,c.creater, \
							   	eu.realname,c.credit, \
							   	c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink \
							   	from study_course sc left join course c on sc.courseid = c.id \
							   	left join eluser eu on c.creater = eu.id \
							   	where sc.userid = ? and sc.status=? limit  ? ,?

study.course.query.size.byuid=select count(*) from study_course sc where sc.userid = ? and sc.status=?
study.course.querywithoutstatus.byuid=select c.id, c.name,c.creater,eu.realname,c.credit, \
							   	c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink \
							   	from  study_course  sc \
							   	left join course c on c.id = sc.courseid  left join eluser eu on c.creater = eu.id \
							    where sc.userid = ? order by sc.status asc, c.createtime desc limit  ? ,?
study.course.querywithoutstatus.size.byuid=select count(*) from study_course sc where sc.userid = ?
study.course.check=select * from study_course where userid = ? and courseid = ?
study.course.add = insert into study_course(userid,courseid,starttime) values(?,?,?)
study.course.finish.set = update study_course set passed =true , endtime = ? where userid = ? and courseid = ?
study.course.finish.check=  select passed from study_course where userid = ? and courseid=?
study.course.finish.set.qpaper = update study_course set passed =? , finishtime = ? where userid = ? and courseid = ?

study.course.select.check=select * from study_course where userid = ? and courseid = ?
study.course.canapply.from.this=select c.id,c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,ct.name,u.realname,c.credit from course c, COURSE_TYPE ct,ELUSER u,DEPARTMENT dep \
 		where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ? and c.name like ? and dep.id=?  and c.id not in(select stuca.courseid from study_course stuca where stuca.userid = ?) and c.ctypeid=? limit ?,?
study.course.canapply.from.super=select c.id,c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,ct.name,u.realname,c.credit from course c, COURSE_TYPE ct,ELUSER u,course_dep cd \
		where c.ctypeid=ct.id and c.creater = u.id and cd.courseid = c.id and c.status = ? and c.name like ? and cd.depid=? and c.id not in(select stuca.courseid from study_course stuca where stuca.userid = ?) and c.ctypeid=? limit ?,?
study.course.canapply.from.this.size=select count(*) from course c, COURSE_TYPE ct,ELUSER u,DEPARTMENT dep \
 		where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ? and c.name like ? and dep.id=? and c.id not in(select stuca.courseid from study_course stuca where stuca.userid = ?)  and c.ctypeid=?
study.course.canapply.from.super.size=select  count(*)  from course c, COURSE_TYPE ct,ELUSER u,course_dep cd \
		where c.ctypeid=ct.id and c.creater = u.id and cd.courseid = c.id and c.status = ? and c.name like ? and cd.depid=? and c.id not in(select stuca.courseid from study_course stuca where stuca.userid = ?)  and c.ctypeid=?
study.course.apply=insert into study_course(courseid,userid,applydate,status,valid)values(?,?,?,?,?)
study.course.credit.byuid = select c.id,c.name,euc.id,euc.realname,c.createtime,c.during,c.credit, ca.status,sc.passtime from course c,study_course ca,eluser euc,study_course sc where sc.courseid = c.id and sc.userid = ca.userid and c.creater = euc.id and c.id = ca.courseid and ca.valid= 1 and sc.passed=true and ca.userid = ?
study.course.ph=select c.id,c.name,c.ctypeid,ct.name,c.creater,eu.realname,c.createtime, (select count(ca.courseid) from study_course ca where ca.courseid = c.id )as cstu from course c,course_type ct,eluser eu  where c.creater = eu.id and c.ctypeid = ct.id order by cstu desc limit ?,?
study.course.ph.size=select count(*) from course c,course_type ct,eluser eu  where c.creater = eu.id and c.ctypeid = ct.id 
study.course.delete = insert into study_course_delete(userid ,courseid,deletedate) values(?,?,?)
study.course.delete.check = select * from study_course_delete where userid = ? and courseid= ?
study.course.passtime.set = update study_course set passtime =?,finishtime= ? where userid = ? and courseid = ?
study.course.cpage.passtime = select sum(scp.passtime) from study_cpage scp,course_page cp  where scp.userid = ? and scp.cpid = cp.id and cp.courseid = ?  
study.course.passed.set= update study_course set passed=true, passtime=? ,finishtime = ? where userid =? and courseid = ?
study.course.apply.ctype =select c.id,c.name from course c where c.ctypeid in (select ct.id from course_type ct,course_type ct1 where ct1.id =? and ct.lid>=ct1.lid and ct.rid <=ct1.rid) and c.id = ? 

study.simpaper.mylist=select se.id,se.epid,se.courseid,se.begintime,se.endtime from SIMEXAMPAPER se left join study_course ca on ca.courseid = se.courseid where se.courseid = ? and ca.userid= ? order by se.begintime
study.class.byuid = select cl.id,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate ,\
					(select count(*) from class_course ccb where ccb.classid = cl.id and ccb.status = 0) as bxCount,\
					(select count(*) from study_course scb,class_course ccb1 where ccb1.classid = cl.id and ccb1.status = 0 and ccb1.courseid = scb.courseid and scb.passed=true and scb.userid = ca.userid) as mybxCount,\
					(select sum(ccx.credit) from class_course ccx where ccx.classid = cl.id and ccx.status =1) as xxCredit, \
					(select sum(ccx1.credit) from study_course scx,class_course ccx1 where ccx1.classid = cl.id and ccx1.courseid = scx.courseid and ccx1.status =1 and scx.passed =true and scx.userid = ca.userid) as myxxCredit, \
					eu.id,eu.realname,cl.certificatename  from class_apply ca ,elclass cl,eluser eu where cl.creater = eu.id and ca.userid = ? and ca.classid = cl.id and ca.status = 2
study.class.cangradute.byuid = select cl.id,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate ,\
					(select count(*) from class_course ccb where ccb.classid = cl.id and ccb.status = 0) as bxCount,\
					(select count(*) from study_course scb,class_course ccb1 where ccb1.classid = cl.id and ccb1.status = 0 and ccb1.courseid = scb.courseid and scb.passed=true and scb.userid = ca.userid) as mybxCount,\
					(select sum(ccx.credit) from class_course ccx where ccx.classid = cl.id and ccx.status =1) as xxCredit, \
					(select sum(ccx1.credit) from study_course scx,class_course ccx1 where ccx1.classid = cl.id and ccx1.courseid = scx.courseid and ccx1.status =1 and scx.passed =true and scx.userid = ca.userid) as myxxCredit, \
					eu.id,eu.realname from class_apply ca ,elclass cl,eluser eu where cl.creater = eu.id and ca.userid = ? and ca.classid = cl.id and ca.status = 2 and cl.id not in (select stc.classid from study_class stc where stc.userid = eu.id and stc.status !=2)
study.class.finish.info.byuid = select status  ,applyDate from study_class where userid=? and classid =? 
study.class.course.bystatus = select courseid,credit from class_course where classid = ? and status = ?
study.class.graduate.list =select cl.id,cl.name,cl.certificatename ,eu.id,eu.realname from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.status=? and sc.userid = eu.id and sc.userid = ?

study.class.graduate.set = insert into study_class(classid,userid,applydate,status,certificateno) values(?,?,?,?,?)
study.class.graduate.update =update study_class set status = ? where classid= ? and userid =?
study.class.resent = select  id,name from elclass where global=1 order by createtime limit 0,1 
study.class.dep.passper =  select dep.id,dep.name,(select count(*) from eluser eu,department dep1 where eu.depid = dep1.id and dep1.lid>=dep.lid and dep1.rid<=dep.rid) as stucount, (select count(*) from study_class ca,eluser eu,department dep1 where ca.classid =? \
					and eu.id= ca.userid and eu.depid = dep1.id and dep1.lid>=dep.lid and dep1.rid<=dep.rid and eu.id not in (select userid from elgroup2user where gid = ?) ) as passcount \
					from department dep where dep.parentid = 1 and dep.id not in(420 ,419) order by passcount/stucount desc limit ?,?


study.course.roomid.bycuid  = select er.roomid from exam_room er ,room_assgin ra where ra.roomid = er.id and ra.userid = ? and  er.courseid = ?
study.cnote.add = insert into course_note(courseid,userid,content,createtime) values(?,?,?,?)
study.cnote.alter= update course_note set content= ? where id=?
study.cnote.list = select id,content,createtime,modifytime from course_note where courseid = ? and userid =? order by modifytime desc
study.cnote.delete = delete from course_note where id = ?
study.cnote.byid= select id,content,createtime,modifytime from course_note where id = ?  
######study .cpage###########
study.cpage.check = select * from study_cpage where userid = ? and cpid = ?
study.cpage.add = insert into study_cpage (userid,cpid,passtime,passed,begintime) values(?,?,0,false,?)
study.cpage.passtime.set= update study_cpage set passtime=? ,endtime= ? where userid =? and cpid = ?
study.cpage.passed.set= update study_cpage set passed=true, passtime=? ,endtime = ? where userid =? and cpid = ?
study.cpage.query.byuidandcpid = select passtime,passed from study_cpage where userid = ? and cpid = ?
study.cpage.query.byuidandcid = select cp.id,cp.title,cp.skipable,sc.passtime,sc.passed,sc.begintime,sc.endtime,cp.sortid,cp.property from course_page cp left join ( select * from study_cpage where userid=? ) sc on cp.id=sc.cpid where cp.courseid=? order by cp.sortid ;
study.cpage.query.bycid = select id from course_page where courseid = ?
study.cpage.query.byid = select cp.id,cp.title,cp.skipable,sc.passtime,sc.passed,sc.begintime,sc.endtime,cp.sortid,cp.property from course_page cp left join ( select * from study_cpage where userid=? ) sc on cp.id=sc.cpid where cp.courseid=? order by cp.sortid ;
study.cpage.lastid =select sc.cpid from study_cpage sc,course_page cp where sc.cpid= cp.id  and sc.userid = ? and cp.courseid= ? order by sc.endtime desc limit 0,1
###study.cpage.finish.check =
study.ppaper.query.bycid = select id ,skipable from practicepaper where courseid = ? and cpid = 0
study.ppaper.query.bycpid = select id,skipable  from practicepaper where cpid = ?
study.ppaper.query.byppidanduid= select myscore,lasttime from study_prac where ppid = ? and userid =?
study.ppaper.query.byuidandcidandpid = select pp.id, ep.id,ep.title,pp.sortid,pp.skipable from practicepaper pp left join exampaper ep on pp.epid = ep.id where pp.courseid=? and pp.cpid=? order by pp.sortid
study.ppaper.add = insert into study_prac (ppid,userid,lasttime) values(?,?,?)
######sql for course study########
######sql for stdy qpaper########
study.qpaper.reslut=select er.id,er.title,sqi.epid,sqi.status,sqi.myScore,sqi.begintime,sqi.endtime,ep.ep_tscore, (select count(*) from study_quizinfo sqi1 where sqi1.roomid=er.id and sqi1.myScore>(select sqi2.myScore from study_quizinfo sqi2 where sqi2.roomid=er.id and sqi2.userid = sqi.userid)) mySort from exam_room er,exampaper ep,course c,study_quizinfo sqi where er.courseid =? and c.id=er.courseid and sqi.epid=ep.id  and sqi.userid= ? and er.id=sqi.roomid;
study.qpaper.add=insert into study_quizinfo(userid,roomid,epid,status,begintime,endtime) values(?,?,?,?,?,?)
study.qpaper.submit=update study_quizinfo set myAnswer = ?,status=2 ,endtime=? where id = ?
study.qpaper.save= update study_quizinfo set myAnswer = ?,passTime = ? where id = ?
study.qpaper.check=select * from study_quizinfo where  userid = ? and roomid = ? and status = ?
study.qpaper.finalscore.set=update study_quizinfo set myScore = ?,status = 3,ispassed= ? where id = ?
study.qpaper.finish.check = select sqi.myScore,ep.ep_tscore from study_quizinfo sqi,exampaper ep where ep.id = sqi.epid and sqi.roomid = ? and sqi.userid = ?
study.qpaper.course.socre.byrid = select  from course c ,exam_room er where  er.couresid = c.id and er.roomid = ?
study.qpaper.finalscore.set.ep.score = select ep.ep_tscore from exampaper ep,study_quizinfo sqi where ep.id =sqi.epid and sqi.id = ?
study.qpaper.finalscore.set.c.passgrade =select er.passgrade from exam_room er left join study_quizinfo sqi on sqi.roomid = er.id where sqi.id = ?
study.qpaper.recent.list= select  sqi.id sqiid,er.id erid,er.title,er.location ,er.begintime,er.endtime from exam_room er left join study_quizinfo sqi on er.id = sqi.roomid where sqi.userid = ? order by er.begintime desc limit ?,?
study.qpaper.list=  select er.id,er.title,er.location, er.begintime,er.endtime,c.id,c.name,c.status,sqi.id,sqi.myscore,sqi.status,sqi.endtime from (select * from exam_room where iscommon = 0) er left join study_quizinfo sqi on er.id = sqi.roomid left join course c on c.id = er.courseid left join study_course ca on ca.courseid=c.id and sqi.userid=ca.userid where sqi.userid = ? order by er.begintime desc limit ?,?
study.qpaper.list.size= select count(*) from study_quizinfo sqi left join  exam_room er on er.id =sqi.roomid left join study_course ca on ca.courseid=er.courseid and ca.userid = sqi.userid where sqi.userid = ?  and iscommon = 0
study.qpaper.recent.list.size= select count(*) from exam_room er ,eluser eu, room_assign ra where eu.id= er.supervisor and er.id = ra.roomid and  ra.userid = ?
study.qpaper.withoutcourse= select er.id,er.title,er.location ,er.begintime,er.endtime,sqi.id,sqi.status \ 
							 from (select * from exam_room where iscommon=1) er   left join study_quizinfo sqi on er.id = sqi.roomid where sqi.userid = ? order by er.begintime desc limit ?,?
study.qpaper.withoutcourse.size= select count(*) from (select * from exam_room where iscommon=1) er left join study_quizinfo sqi on er.id = sqi.roomid where sqi.userid = ? 
study.qpaper.withoutcourse.result=select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myScore,\
						sqi.endtime,er.title,sqi.begintime from  study_quizinfo sqi left join EXAM_ROOM er on \
						sqi.roomid = er.id where sqi.userid = ? and sqi.status=3 and er.iscommon=1 order by er.begintime limit ?,?
###########
study.spaper.finalscore.set=update student_siminfo set myScore = ?,status = 3 where  userid = ? and courseid = ? and epid = ?

study.spaper.result= select ssi.id,ssi.userid,ssi.epid,ssi.courseid, ssi.status,ssi.myScore,ssi.endtime,c.name,eu.realname,ep.title,ep.ep_tscore,ca.status from \
					STUDENT_SIMINFO ssi left join COURSE c on ssi.courseid = c.id left join EXAMPAPER ep on ep.id = ssi.epid left join study_course ca on ca.courseid = ssi.courseid \
					left join ELUSER eu on eu.id = c.creater where ssi.userid = ca.userid  and ssi.userid = ?
					
					
