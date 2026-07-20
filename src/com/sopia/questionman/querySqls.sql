###############sql for qlib######################
qlib.add=insert into QUESTION_LIB(name,userid,parentid,description,lid,rid) values(?,?,?,?,?,?)
qlib.alter=update QUESTION_LIB set name= ?, parentid=?,description=? where id  = ? and userid=?
qlib.query.byidanduid=select q.id,q.name,q.parentid,q.description,qp.name,q.lid,q.rid from QUESTION_LIB q,QUESTION_LIB qp where q.id = ? and q.userid =? and q.parentid = qp.id
qlib.query.byparentidanduid=select id,name ,parentid,description, lid, rid  from question_lib where parentid = ? and userid=?
qlib.parent.set=update QUESTION_LIB set parentid=? where id =? and userid=?
qlib.queryid.byidanduid = select  id from question_lib where name = ? and userid = ?
qlib.querylrid.byidanduid = select  id,lid,rid from question_lib where id = ? and userid = ?

###############sql for qlib######################
###############sql for question######################
question.add=insert into QUESTION( title, content, subject,qexplain, userid, qlibid,createtime, qlevel, answer,qtype,scoreper,parentid,minWord,sortid,fasheng_question,media_file) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
question.man.mylist.sub=select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minWord from QUESTION q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype like ? and q.title like ? and q.userid=? and qlib.lid >=? and qlib.rid<=? and q.parentid=0 order by q.createtime desc limit ?,?
question.man.mylist=select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minWord from QUESTION q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype like ? and q.title like ? and q.userid=? and qlib.id=? and q.parentid=0 order by q.createtime desc limit ?,?
question.man.mylist.sub.size=select count(*) from QUESTION q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype like ? and q.title like ? and q.userid=? and qlib.lid >=? and qlib.rid<=? and q.parentid=0
question.man.mylist.size=select count(*) from QUESTION q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype like ? and q.title like ? and q.userid=? and qlib.id=? and q.parentid=0
question.child.maxsize=select max(sortid) from question where parentid = ?		
question.delete = delete from question where id = ?
    
###############sql for question######################

###############sql for exampaperlib ######################
eplib.add=insert into EXAMPAPERLIB(name,userid,parentid,description,lid,rid) values(?,?,?,?,?,?)
eplib.alter=update EXAMPAPERLIB set name= ?, parentid=?,description=? where id  = ? and userid=?
eplib.query.byparentidanduid=select id,name,userid,parentid,description from EXAMPAPERLIB where parentid = ? and userid=?
eplib.query.byidanduid=select el.id,el.name,el.parentid, el.description,elp.name from EXAMPAPERLIB el, EXAMPAPERLIB elp  where el.parentid=elp.id and el.id =? and  el.userid=?
eplib.parent.set=update EXAMPAPERLIB set parentid=? where id =? and userid=?
eplib.querylrid.byidanduid= select  id,lid,rid from EXAMPAPERLIB where id = ? and userid = ?
###############sql for exampaperlib ######################

###############sql for exampaper######################
exampaper.query.mylist=select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.israndom,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from EXAMPAPER ep, EXAMPAPERLIB epl \
						where ep.eplid = epl.id  and ep.userid = ? and ep.title like ? and ep.eplid = ? order by ep.createtime desc limit ?,?
exampaper.query.mylist.sub=select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.israndom,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from EXAMPAPER ep, EXAMPAPERLIB epl \
						where ep.eplid = epl.id  and ep.userid = ? and ep.title like ? and epl.lid >= ? and epl.rid<=?  order by ep.createtime desc limit ?,? 
exampaper.query.mylist.size=select count(*) from EXAMPAPER ep, EXAMPAPERLIB epl where ep.eplid = epl.id  and ep.userid = ? and ep.title like ? and ep.eplid = ? 
exampaper.query.mylist.sub.size=select count(*) from EXAMPAPER ep, EXAMPAPERLIB epl where ep.eplid = epl.id  and ep.userid = ? and ep.title like ? and epl.lid >= ? and epl.rid<=? 
exampaper.add=insert into EXAMPAPER( title,description, userid, eplid,israndom, during, createtime, opentimelimit,ep_tscore) values(?,?,?,?,?,?,?,?,?)
exampaper.alter=update EXAMPAPER set title=?,description=?, eplid=?,israndom=?, during=?,opentimelimit=?,ep_tscore=? where id = ? and userid=?
exampaper.query.byidanduid=select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.israndom,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from EXAMPAPER ep,EXAMPAPERLIB epl where ep.id = ? and ep.userid = ? and ep.eplid = epl.id
exampaper.query.byid=select ep.id,ep.title,ep.description,ep.userid,ep.eplid, ep.israndom,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from EXAMPAPER ep left join EXAMPAPERLIB epl on ep.eplid = epl.id where ep.id = ?
myexampaper.quiz=select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myScore,sqi.endtime,eu.realname,er.title from  study_quizinfo sqi left join ELUSER eu on sqi.userid = eu.id left join exam_room er on sqi.roomid = er.id where sqi.epid = ? limit ?,?
##############sql for exampaperblock##########
epblock.query.maxsortid=select max(sortid) from EXAMPAPERBLOCK where exampaperid=?
epblock.query.byid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,ep.title,epb.id,ep.israndom,(select count(*) from exampaperblockquestion eq where eq.blockid= epb.id) as rqcount from EXAMPAPERBLOCK epb left join EXAMPAPER ep on epb.exampaperid = ep.id where epb.id =?
epblock.add=insert into EXAMPAPERBLOCK(exampaperid,title,description,type,questionamount,eachscore,sortid) values(?,?,?,?,?,?,?)
epblock.query.byepid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,(select count(*) from exampaperblockquestion eq where eq.blockid= epb.id) as rqcount from EXAMPAPERBLOCK epb where epb.exampaperid=? order by epb.sortid asc
epblock.query.random.byepid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,(select sum(eplevel1+eplevel2+eplevel3+eplevel4+eplevel5+eplevel) from exampaper_random eq where eq.blockid= epb.id) as rqcount from EXAMPAPERBLOCK epb where epb.exampaperid=? order by epb.sortid asc
epblock.alter = update EXAMPAPERBLOCK set title = ?,description = ?,type = ?, eachscore = ?,sortid = ?,questionamount=? where id = ?
epblock.delete = delete from EXAMPAPERBLOCK where id= ?
epblock.sortid.byid=select epb.exampaperid,epb.sortid from EXAMPAPERBLOCK epb where epb.id =?
epblock.bigsortid.set=update EXAMPAPERBLOCK set sortid= sortid-1 where exampaperid = ? and sortid>?


##############sql for exampaperblock question##########
epblock.question.bybid=select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.sortid from QUESTION q \
					left join exampaperblockquestion epq on epq.questionid = q.id left join QUESTION_LIB qlb on q.qlibid = qlb.id where epq.blockid = ? order by epq.sortid asc
epblock.question.check=select * from exampaperblockquestion where blockid =? and questionid=?
epblock.question.sortid.bybqid=select sortid from exampaperblockquestion where blockid =? and questionid=?
epblock.question.add=insert into exampaperblockquestion(blockid,questionid,sortid) values(?,?,?) 
epblock.question.maxsortid=select max(sortid) from exampaperblockquestion where blockid =? 
epblock.question.delete=delete from exampaperblockquestion where questionid =? and blockid=?
epblock.question.bigsortid.set=update exampaperblockquestion set sortid= sortid-1 where blockid=? and sortid >?
epblock.question.size.check = select epb.questionamount,count(epq.questionid) epqcount from exampaperblock epb left join  exampaperblockquestion epq on epq.blockid = epb.id where epb.id=? group by epb.questionamount 
epblock.question.random.sub.size=select count(*) from question q,QUESTION_LIB qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid >=? and qlib.rid<=? and q.parentid= 0 and qlib.userid = ?
epblock.question.random.size=select count(*) from question q,QUESTION_LIB qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid=? and q.parentid= 0
epblock.question.random.sub=select  q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlib.name from question q,QUESTION_LIB qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid >=? and qlib.rid<=? and q.parentid= 0 and qlib.userid = ?
epblock.question.random=select  q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlib.name from question q,QUESTION_LIB qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid=? and q.parentid= 0
epblock.question.random.qliblrid.byid = select  id,lid,rid,userid from question_lib where id = ?
epblock.question.random.add=insert into EXAMPAPER_RANDOM(qlibid,blockid,eplevel1,eplevel2,eplevel3,eplevel4,eplevel5,eplevel,suboperate) values(?,?,?,?,?,?,?,?,?)
epblock.question.random.alter = update EXAMPAPER_RANDOM set eplevel1 =?,eplevel2 =?,eplevel3 =?,eplevel4 =?,eplevel5 =?,eplevel =? where id = ?
epblock.question.random.bybid=select er.id,er.blockid, epb.title,er.qlibid ,er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,er.suboperate,qlib.name,epb.type from EXAMPAPER_RANDOM er left join QUESTION_LIB qlib on er.qlibid = qlib.id left join EXAMPAPERBLOCK epb on er.blockid = epb.id where er.blockid = ?
epblock.question.random.byid=select er.id,er.blockid, epb.title,er.qlibid ,er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,er.suboperate,qlib.name,epb.type from EXAMPAPER_RANDOM er left join QUESTION_LIB qlib on er.qlibid = qlib.id left join EXAMPAPERBLOCK epb on er.blockid = epb.id where er.id = ?
epblock.question.random.delete=delete from  EXAMPAPER_RANDOM where id = ?
epblock.eprandom.bybid=select epb.exampaperid,ep.title,ep.israndom,epb.id,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epbr.id,ep.userid from EXAMPAPERBLOCK epb \
				left join EXAMPAPER ep on epb.exampaperid = ep.id left join EXAMPAPER_RANDOM epbr on epb.id = epbr.blockid where epbr.blockid   =?

				
#################sql for stuff####
stuff.add=insert into question_stuff(title,description,fileExt,onwer,createtime,length,type) values(?,?,?,?,?,?,?)
stuff.alter = update question_stuff set title = ?,description = ?,type = ? where id = ? 
stuff.delete =delete from question_stuff where id = ? and onwer = ?
stuff.query.list.byname=select qs.id,qs.title,qs.description,qs.fileExt,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs where qs.onwer = ? and qs.title like ? order by qs.createtime desc limit ?,?
stuff.query.list.bynametype=select qs.id,qs.title,qs.description,qs.fileExt,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs where qs.onwer = ? and qs.title like ? and qs.type = ? order by qs.createtime desc limit ?,?
stuff.query.list.byname.size=select count(*) from question_stuff qs where qs.onwer = ? and qs.title like ? 
stuff.query.list.bynametype.size=select count(*) from question_stuff qs where qs.onwer = ? and qs.title like ? and qs.type = ?
stuff.query.byid=select qs.id,qs.title,qs.description,qs.fileExt,qs.modifytime,qs.createtime,qs.length, qs.type from question_stuff qs where qs.onwer = ? and qs.id = ?





 