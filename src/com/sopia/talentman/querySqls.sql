


talent.expert.list = select eu.id,eu.realname,eu.age,eu.major,eu.studyDir,eu.company,c.name,eu.protitle from eluser eu,company c where c.id = eu.company and eu.role = 6


###talent room operate##############
talent.trcoll.add = insert into troomcoll(title,description,creater,createtime) values(?,?,?,?)
talent.trcoll.list=select id,title,description,createtime from troomcoll where creater = ? order by createtime desc limit ?,?
talent.trcoll.list.size=select count(*) from troomcoll where creater = ? 
talent.trcoll.byid=select trc.id,trc.title,trc.description,trc.createtime,trc.creater,eu.realname from troomcoll trc,eluser eu where trc.creater = eu.id and trc.id= ?
talent.trcoll.alter  = update troomcoll set title=?,description=? where id = ?


talent.troom.list.bytrcid= select tr.id,tr.title,tr.description,tr.begintime,tr.endtime,tr.epid,tr.trcid,ep.title from troom tr,exampaper ep where tr.epid = ep.id and tr.trcid = ?
talent.troom.add = insert into troom(title,description,begintime,endtime,epid,trcid) values(?,?,?,?,?,?)
talent.troom.byid= select tr.id,tr.title,tr.description,tr.begintime,tr.endtime,tr.epid,tr.trcid,ep.title,trc.title from troom tr,troomcoll trc,exampaper ep where tr.epid = ep.id and tr.trcid=trc.id and tr.id = ?
talent.troom.alter = update troom set title=?,description=?,begintime=?,endtime=?,epid=? where id = ?
talent.troom.assigned.user.list= select tra.userid,eu.realname,eu.depid,dep.name,eu.username from troom_assign tra,eluser eu,department dep where eu.depid=dep.id and eu.id = tra.userid and tra.trid =? order by tra.assigntime desc limit ?,? 
talent.troom.assigned.user.list.size= select count(*) from troom_assign tra,eluser eu,department dep where eu.depid=dep.id and eu.id = tra.userid and tra.trid =? 
talent.troom.check.userintr =select * from troom_assign where trid = ? and userid =?
talent.troom.assign2user =insert into troom_assign(trid,userid,assigntime) values(?,?,?)
talent.troom.uassign2user = delete from troom_assign where trid = ? and userid =?

##########student####
talent.troom.list.bystuid = select tr.id,tr.title,tr.description,tr.createtime,tr.creater,eu.realname,(select sum(ti.myscore) from troom_epinfo ti,troom str where str.id = ti.trid and str.trcid = tr.id and ti.userid = tra.userid) ,(select count(*) from troom str where str.trcid = tr.id) from troom_assign tra, troomcoll tr,eluser eu where tra.trid = tr.id and eu.id=tr.creater and tra.userid = ? order by tr.createtime limit ?,?
talent.troom.list.bystuid.size = select count(*) from troom_assign tra, troomcoll tr,eluser eu where tra.trid = tr.id and eu.id=tr.creater and tra.userid = ? 
talent.troom.bystuid = select tr.id,tr.title,tr.description,tr.createtime,tr.creater,eu.realname,(select sum(ti.myscore) from troom_epinfo ti,troom str where str.id = ti.trid and str.trcid = tr.id and ti.userid = tra.userid) ,(select count(*) from troom str where str.trcid = tr.id) from troom_assign tra, troomcoll tr,eluser eu where tra.trid = tr.id and eu.id=tr.creater and tra.userid = ? and tr.id = ?

talent.mytroom.list.bystuid= select tr.id,tr.title,tr.description,tr.begintime,tr.endtime,tr.epid,tr.trcid,ep.title,epi.myscore from troom tr left join exampaper ep on tr.epid = ep.id left join troom_epinfo epi on epi.trid = tr.id  where epi.userid = ? and tr.trcid = ? 

talent.troom.ts.user.list = select tra.userid,eu.realname,eu.depid,dep.name,eu.username from ztroom_assign tra,eluser eu,department dep where eu.depid=dep.id and eu.id = tra.userid and tra.trid =? and tra.userid!=? and dep.id= ?
talent.troom.xj.user.list = select tra.userid,eu.realname,eu.depid,dep.name,eu.username from ztroom_assign tra,eluser eu,department dep where eu.depid=dep.id and eu.id = tra.userid and tra.trid =? and tra.userid!=? and dep.lid> ? and dep.rid <?
talent.troom.user.deplrid = select dep.id,dep.lid,dep.rid from department dep,eluser eu where dep.id = eu.depid and eu.id = ?
talent.troom.eval = insert into troom_eval(trid,evaler,tester,evaldetail,evaltype,evaltime) values(?,?,?,?,?,?)
talent.troom.eval.alter= update troom_eval set evaldetail = ?,evaltime = ? where trid =? and  evaler = ? and tester =? 
talent.troom.eval.check= select * from  troom_eval where trid =? and  evaler = ? and tester =? 
talent.troom.eval.evaldetail= select evaldetail from  troom_eval where trid =? and  evaler = ? and tester =?
 
talent.troom.eval.quiz.save =update troom_epinfo set myAnswer = ?,status=2 ,endtime=? where trid = ? and userid = ?
talent.troom.eval.quiz.submit = update troom_epinfo set myAnswer = ?,passTime = ? where trid = ? and userid = ?
talent.troom.eval.quiz.into= insert into troom_epinfo (userid ,trid,status,begintime) values(?,?,?,?)
talent.troom.eval.quiz.checkin= select * from troom_epinfo where userid = ? and trid = ?
talent.troom.myscore = select myScore,zjscore,tsscore,sjscore from troom_epinfo where userid = ? and trid = ?

talent.troom.stat.list = select eu.id ,eu.realname,eu.username,eu.depid,dep.name,eu.sex,eu.age,(select sum(rep.myscore) from troom_epinfo rep,troom tr where tr.id = rep.trid and tr.trcid = ra.trid and rep.userid = eu.id ) as totalscore,(select count(*) from troom str where str.trcid = ra.trid) from troom_assign ra ,eluser eu,department dep  where dep.id = eu.depid and eu.id = ra.userid and ra.trid = ? order by totalscore desc

talent.troom.myscore.list = select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,cp.id,cp.name,dep.id,dep.name,er.name, trep.myScore, trep.zjscore, trep.tsscore, trep.sjscore,tr.id,tr.title \
 			from ELUSER eu left join COMPANY cp on eu.company = cp.id left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
 			left join troom_epinfo trep on trep.userid = eu.id left join troom tr on tr.id = trep.trid \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and tr.title like ? order by (trep.myScore+trep.zjscore+trep.tsscore+trep.sjscore) desc limit ?,?
talent.troom.myscore.list.size =  select count(*) \
 			from ELUSER eu left join COMPANY cp on eu.company = cp.id left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
 			left join troom_epinfo trep on trep.userid = eu.id left join troom tr on tr.id = trep.trid \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and tr.title like ?  
talent.troom.myscore.sub.list = select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,cp.id,cp.name,dep.id,dep.name,er.name, trep.myScore, trep.zjscore, trep.tsscore, trep.sjscore,tr.id,tr.title \
			from ELUSER eu left join COMPANY cp on eu.company = cp.id left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id \
			left join troom_epinfo trep on trep.userid = eu.id left join troom tr on tr.id = trep.trid \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and tr.title like ? order by (trep.myScore+trep.zjscore+trep.tsscore+trep.sjscore) desc limit ?,?
talent.troom.myscore.sub.list.size= select count(*) \
			from ELUSER eu left join COMPANY cp on eu.company = cp.id left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id \
			left join troom_epinfo trep on trep.userid = eu.id left join troom tr on tr.id = trep.trid \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and tr.title like ?
##########zhu guan ###
talent.ztroom.add = insert into ztroom(title,description,begintime,endtime,norm,creater) values(?,?,?,?,?,?)
talent.ztroom.list.byuid = select id,title,description,begintime,endtime,norm from ztroom where creater = ? order by begintime limit ?,?
talent.ztroom.list.byuid.size = select count(*) from ztroom where creater = ? 
talent.ztroom.alter = update ztroom set title=?,description=?,begintime=?,endtime=?,norm=? where id = ?
talent.ztroom.byid = select ztr.id,ztr.title,ztr.description,ztr.begintime,ztr.endtime,ztr.norm,ztr.creater ,eu.realname from ztroom ztr,eluser eu where ztr.creater= eu.id and ztr.id= ?
talent.ztroom.assigned.zuser.list= select tra.userid,eu.realname,eu.depid,dep.name,eu.username from ztroom_assign tra,eluser eu,department dep where eu.depid=dep.id and eu.id = tra.userid and tra.trid =? order by tra.assigntime desc limit ?,? 
talent.ztroom.assigned.zuser.list.size= select count(*) from ztroom_assign tra,eluser eu,department dep where eu.depid=dep.id and eu.id = tra.userid and tra.trid =? 
talent.ztroom.check.userintr =select * from ztroom_assign where trid = ? and userid =?
talent.ztroom.assign2user =insert into ztroom_assign(trid,userid,assigntime) values(?,?,?)
talent.ztroom.uassign2user = delete from ztroom_assign where trid = ? and userid =?
talent.myztroom.list.bystid = select ztr.id,ztr.title,ztr.description,ztr.begintime,ztr.endtime,ztr.norm,ztr.creater,eu.realname,ztra.zjscore,ztra.tsscore,ztra.sjscore from ztroom ztr ,ztroom_assign ztra,eluser eu where ztr.creater = eu.id and ztra.trid = ztr.id and  ztra.userid = ? order by ztr.begintime limit ?,?
talent.myztroom.list.bystid.size = select count(*) from ztroom ztr ,ztroom_assign ztra where ztra.trid = ztr.id and  ztra.userid = ?
talent.ztroom.score.set = call talnet_scoreset(?,?);
talent.ztroom.stat.list	= select eu.id ,eu.realname,eu.username,eu.depid,dep.name,eu.sex,eu.age,ztra.zjscore,ztra.tsscore,ztra.sjscore from ztroom_assign ztra,eluser eu,department dep where eu.depid =dep.id and ztra.trid = ? and eu.id= ztra.userid order by (ztra.zjscore+ztra.tsscore +ztra.sjscore) desc
			