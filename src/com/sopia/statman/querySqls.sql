###############sql for Statistics######################
stat.user.course.list=select c.id,c.name,c.creater,eu.realname,c.credit ,ca.status from study_course ca left join COURSE c on c.id = ca.courseid left join ELUSER eu on c.creater = eu.id where ca.userid = ?
stat.user.course.size = select count(*) from study_course ca where ca.userid = ? 
stat.course.bycreater = select c.id,c.name,ct.id,ct.name,c.credit,c.createtime,(select count(*) from study_course ca where ca.valid = 1 and ca.courseid = c.id) as ucount,(select count(*) from study_course sc where sc.courseid= c.id and sc.passed = true) as passcount from course c,course_type ct where c.ctypeid = ct.id and c.creater = ? 
stat.class.bycreater = select cl.id,cl.name,cl.createtime,(select count(*) from class_course ccb where ccb.status = 0 and ccb.classid=cl.id) as bxCount ,\
									(select count(*) from class_course ccx where ccx.status = 1 and ccx.classid=cl.id) as ccxCount ,\
									(select sum(ccx1.credit) from class_course ccx1 where ccx1.status=1 and ccx1.classid =cl.id ) as ccxCredit,optionalcredit,\
									(select count(*) from study_Class ca where ca.classid = cl.id and ca.status = 2),(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  from elclass cl where cl.creater = ?
stat.user.class.size = select count(*) from study_Class ca where ca.userid = ? and ca.status = 2
stat.user.totalcredit= select sum(c.credit) as stuc from study_course sc , course c where  c.id = sc.courseid and sc.passed=true and sc.userid= ?
########stat.user.class.list = select cl.id,cl.name,(select count(*) from class_course cc where cc.classid = cl.id and cc.status = 0) as bxCount, (select sum(cc.credit) from class_course cc where cc.classid = cl.id and cc.status = 1) as xxScore,ca.applydate from elclass cl,study_Class ca where  ca.userid = ? and ca.classid = cl.id


#######stat.dep.course.size=select count(distinct(c.id)) from study_course ca left join COURSE c on c.id = ca.courseid left join ELUSER eu on c.creater = eu.id left join ELUSER eu_s on eu_s.id = ca.userid left join DEPARTMENT dep on eu_s.depid = dep.id where eu_s.depid = ? 
#######stat.dep.user.size=select count(*) from ELUSER eu_s left join DEPARTMENT dep on eu_s.depid = dep.id where eu_s.depid = ?  
stat.dep.course.list=select distinct(c.id),c.name,c.creater,eu.realname,c.credit, c.createtime from study_course ca left join COURSE c on c.id = ca.courseid left join ELUSER eu on c.creater = eu.id left join ELUSER eu_s on eu_s.id = ca.userid left join DEPARTMENT dep on eu_s.depid =dep.id where eu_s.depid = ? 
stat.dep.info.bydid = select dep.id ,dep.name ,(select count(*) from eluser eu where eu.depid = dep.id ) as usercount,\
					(select count(distinct (ca.courseid)) from study_course ca,eluser caeu where caeu.depid = dep.id and caeu.id = ca.userid ),\
					(select count(distinct(cla.classid)) from study_Class cla,eluser claeu where claeu.depid = dep.id and claeu.id = cla.userid  ) from department dep , department depp  where dep.lid >depp.lid and dep.rid <depp.rid and depp.id= ? 
stat.dep.info.byid = select dep.id ,dep.name ,(select count(*) from eluser eu where eu.depid = dep.id ) as usercount,\
					(select count(distinct (ca.courseid)) from study_course ca,eluser caeu where caeu.depid = dep.id and caeu.id = ca.userid ),\
					(select count(distinct(cla.classid)) from study_Class cla,eluser claeu where claeu.depid = dep.id and claeu.id = cla.userid  ) from department dep , department depp  where dep.lid >depp.lid and dep.rid <depp.rid and dep.id = ? 
stat.dep.class.list = select distinct(cl.id),cl.name,euc.id,euc.realname,optionalcredit,createtime,(select count(*) from class_course ccb where ccb.status = 0 and ccb.classid=cl.id) as bxCount ,\
									(select count(*) from class_course ccx where ccx.status = 1 and ccx.classid=cl.id) as ccxCount ,\
									(select sum(ccx1.credit) from class_course ccx1 where ccx1.status=1 and ccx1.classid =cl.id ) as ccxCredit \
									 from elclass cl,eluser euc,study_Class cla,eluser eus where cl.creater = euc.id and eus.id = cla.userid and cla.classid = cl.id and eus.depid = ?
									
####stat.course.list=select c.id, c.name,c.ctypeid,c.createtime,ct.name from COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id where c.name like ?

stat.course.list.bytid=select c.id, c.name cname,c.ctypeid,c.createtime,ct.name ctname,count(sc.userid) sccount from COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id left join study_course sc on sc.courseid = c.id where c.name like ? and  ct.lid>=? and ct.rid<=? group by  c.id, c.name,c.ctypeid,c.createtime,ct.name
stat.course.list.sub.bytid=select c.id, c.name,c.ctypeid,c.createtime,ct.name,(select count(*) from study_course ca where ca.courseid = c.id ) from COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  where c.name like ? and c.ctypeid=?
stat.course.user.list= select eu.id, eu.username,eu.realname,eu.depid,dep.name depname ,c.credit, c.during, sc.passtime/60 passtime,sc.process,sc.status, sc.mycredit,sqi.id _sqiid,sqi.myScore,sqi.ispassed from study_course sc left join course c on sc.courseid = c.id left join eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi on sqi.id=sc.sqiid where sc.courseid =?

stat.simexam.didandepid = select sep.epid , sep.courseid ,c.name,c.creater,eu_c.realname,ep.title from SIMEXAMPAPER sep,COURSE c,ELUSER eu_c,EXAMPAPER ep where sep.courseid in (select ca.courseid from study_course ca,ELUSER eu where eu.depid = ?) and sep.courseid = c.id and eu_c.id = c.creater and sep.epid = ep.id


