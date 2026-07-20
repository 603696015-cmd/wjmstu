plan.add=insert into elplan(name,content,manager,contact,participator,createtime,finishdate) values(?,?,?,?,?,?,?)
plan.alter = update elplan set name = ?,content= ?,contact= ?,participator = ?,finishdate=?  where id = ?
plan.list.byuid = select p.id,p.name,p.content,p.manager,p.contact,p.participator,eu.realname,p.createtime,p.status from elplan p,eluser eu where p.manager = eu.id and  p.manager = ? order by p.createtime desc limit ?,?
plan.byid = select p.id,p.name,p.content,p.manager,p.contact,p.participator,eu.realname,p.createtime,p.status from elplan p,eluser eu where p.manager = eu.id and  p.id= ?
plan.status.set = update elplan set status = ? where id = ?
plan.verify.add = insert into elplanverify (planid,userid,status,verifydate) values(?,?,?,?)
plan.superverified.set = update elplanverify set superverify = true where planid = ?
###plan.verify.list = select p.id,p.name,p.manager,p.createtime,p.status,eu.realname from elplanverify eplv,eluser veu,department vdep,elplan p,eluser eu where p.id = eplv.planid and p.manager = eu.id and eplv.status=2 and veu.id = eplv.userid and veu.depid = vdep.id and vdep.lid>=? and vdep.rid<=? order by eplv.verifydate desc limit ?,?
plan.verify.list = select p.id,p.name,p.manager,p.createtime,p.status,eu.realname from elplanverify eplv,eluser veu,department vdep,elplan p,eluser eu where p.id = eplv.planid and p.manager = eu.id and eplv.status=2 and veu.id = eplv.userid and veu.depid = vdep.id and vdep.parentid=? and eplv.superverify=false order by eplv.verifydate desc limit ?,?
plan.verified.list = select pv.id,pv.planid,pl.name,pv.userid,eu.realname,pv.status,pv.verifydate from elplanverify pv,elplan pl,eluser eu where pv.userid = eu.id and pv.planid =? and pv.planid = pl.id order by pv.verifydate 
plan.carryout.list = select p.id,p.name,p.manager,eu.realname,p.createtime,p.status,(select sum(elps.plandays) from elplanstage elps where elps.planid = p.id) as pldays,\
					(select sum(elps.realdays) from elplanstage elps where elps.planid = p.id) as redays,p.finishdate,(select max(realfinishdate) from elplanstage elps where elps.planid = p.id) from elplan p,eluser eu,department dep where dep.id = eu.depid and dep.lid>=? and dep.rid <=? and eu.id = p.manager and (p.status = 2 or p.status=4)  limit ?,?

planstage.list.bypid = select ps.id ,ps.content,ps.plandays,ps.realdays,ps.planfinishdate,ps.realfinishdate,pl.id,pl.name from elplanstage ps, elplan pl where pl.id = ps.planid and pl.id=? order by ps.id
planstage.carryout= update elplanstage set realdays = ?, realfinishdate =? where id = ?
planstage.add=insert into elplanstage(content,plandays,planfinishdate,planid) values(?,?,?,?)
planstage.delete = delete from elplanstage where id = ?
planstage.alter = update elplanstage set content = ?,plandays= ?, planfinishdate = ? where id = ?
planstage.list.byid=select ps.id ,ps.content,ps.plandays,ps.realdays,ps.planfinishdate,ps.realfinishdate from elplanstage ps where ps.id = ?

planstuff.add = insert into elplanstuff ( psid,stuffid) values( ?,?)
planstuff.delete = delete from elplanstuff where id = ?
planstuff.list.bypsid = select psf.id,sf.id,sf.title,sf.fileext,sf.type from elplanstuff psf ,question_stuff sf where psf.stuffid = sf.id and psf.psid = ? 
######survey sql 
survey.add =insert into elsurvey(title,description,creater,begintime,endtime,epid,stureadresult) value(?,?,?,?,?,?,?)
survey.mylist=select s.id, s.title,s.begintime,s.endtime,s.epid,ep.title ,s.stureadresult from elsurvey s,exampaper ep where s.epid = ep.id and s.creater = ? order by s.begintime desc limit ?,?
survey.mylist.size=select count(*) from elsurvey s,exampaper ep where s.epid = ep.id and s.creater = ?
survey.byid=select s.id, s.title,s.begintime,s.endtime,s.epid,ep.title ,s.stureadresult,s.description from elsurvey s,exampaper ep where s.epid = ep.id and s.id = ?
survey.alter=update elsurvey set title=?,description=?, begintime=?,endtime=?,epid=?,stureadresult=? where id = ?
survey.delete = delete from elsurvey where id = ?
survey.list.bydepid = select es.id,es.title,es.creater,eu.realname,es.begintime,es.endtime,es.epid,ep.title,dep.id,dep.name from elsurvey es ,eluser eu,exampaper ep,department dep \
                where es.creater = eu.id and eu.depid = dep.id and es.epid = ep.id and dep.lid<=? and dep.rid>=? order by es.begintime desc limit ?,?
survey.list.bydepid.size = select count(*) from elsurvey es ,eluser eu,exampaper ep,department dep \
                where es.creater = eu.id and eu.depid = dep.id and es.epid = ep.id and dep.lid<=? and dep.rid>=? 
survey.do.submit = insert into student_survey(userid,surveyid,epid,myAnswer,endtime) values(?,?,?,?,?)
survey.do.check = select * from student_survey where userid = ? and surveyid = ?
survey.question.list.bysid = select q.id,q.title,q.qtype,q.subject from question q,exampaper ep,exampaperblock epb,elsurvey es,exampaperblockquestion epbq where es.epid = ep.id and epb.exampaperid=ep.id and epb.id = epbq.blockid and epbq.questionid = q.id and es.epid = ep.id and es.id = ?
survey.question.answer.count = select getSurveyCount_ByQandAns(?,?,?)
survey.question.answer.list.byid = select getSurveyCount_ByQandAns(?,?)


poll.add = insert into elpoll(title,description,creater,begintime,endtime,qid,stureadresult) value(?,?,?,?,?,?,?)
poll.alter=update elpoll set title=?,description=?, begintime=?,endtime=?,qid=?,stureadresult=? where id = ?
poll.delete = delete from elpoll where id = ?
poll.byid=select p.id, p.title,p.begintime,p.endtime,p.qid,q.title ,p.stureadresult,p.description from elpoll p,question q where p.qid = q.id and p.id = ?
poll.mylist=select s.id, s.title,s.begintime,s.endtime,s.qid,q.title ,s.stureadresult from elpoll s,question q where s.qid = q.id and s.creater = ? order by s.begintime desc limit ?,?
poll.mylist.size=select count(*) from elpoll s,question q where s.qid = q.id and s.creater = ?
poll.list.bydepid = select es.id,es.title,es.creater,eu.realname,es.begintime,es.endtime,es.qid,ep.title,dep.id,dep.name from elpoll es ,eluser eu,question ep,department dep \
                where es.creater = eu.id and eu.depid = dep.id and es.qid = ep.id and dep.lid<=? and dep.rid>=? order by es.begintime desc limit ?,?
poll.list.bydepid.size = select count(*) from elpoll es ,eluser eu,question ep,department dep \
                where es.creater = eu.id and eu.depid = dep.id and es.qid = ep.id and dep.lid<=? and dep.rid>=? 
poll.do.check = select * from student_poll where userid = ? and pollid = ?
poll.do.submit= insert into student_poll(userid,pollid,qid,myAnswer,endtime) values(?,?,?,?,?)
poll.question.bypid = select q.id,q.title,q.qtype,q.subject from question q,elpoll pl where pl.id = ? and pl.qid = q.id
poll.question.answer.count = select getPollCount_ByQandAns(?,?,?)

