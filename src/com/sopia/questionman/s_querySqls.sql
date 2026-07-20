###############sql for qlib######################
qlib.add=insert into question_lib(name ,parentid,description,lid,rid) values(?, ?,?,?,?)
qlib.alter=update question_lib set name= ?, parentid=?,description=? where id  = ? 
qlib.query.byidanduid=select q.id,q.name,q.parentid,q.description,qp.name,q.lid,q.rid from question_lib q,question_lib qp where q.id = ? and q.userid =? and q.parentid = qp.id
qlib.query.byparentidanduid=select id,name ,parentid,description, lid, rid  from question_lib where parentid = ?
qlib.parent.set=update question_lib set parentid=? where id =? and userid=?
qlib.queryid.byidanduid = select  id from question_lib where name = ? and userid = ?
qlib.querylrid.byidanduid = select  id,lid,rid from question_lib where id = ?  

###############sql for qlib######################
###############sql for question######################
question.add=insert into question( title, content, subject,qexplain, userid, qlibid,createtime, qlevel, answer,qtype,scoreper,parentid,minword,sortid,oldrulestring,oldscore,fasheng_question,media_file) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
question.alter= update question set title =?, content=?, subject=?,qexplain = ?, qlibid=?, qlevel=?, answer=?,scoreper=?,minWord=?,sortid=?,oldrulestring=?,oldscore=? where id=?
question.querybyid=select q.id,q.title ,q.content,q.subject,q.qexplain,q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,q.scoreper,q.parentid,q.minWord,q.oldrulestring,q.oldscore  from QUESTION q left join QUESTION_LIB qlb on q.qlibid = qlb.id where q.id = ? 

question.man.mylist.sub=select * from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,row_number() over( order by q.createtime desc) rownum from question q ,question_lib qlib \
		where q.qlibid=qlib.id and  q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0) t where t.rownum between ? and ?
question.man.mylist.sub.type=select * from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,row_number() over( order by q.createtime desc) rownum from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype=? and q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0) t where t.rownum between ? and ?
question.man.mylist=select * from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,row_number() over( order by q.createtime desc ) rownum from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.title like ? and qlib.id=? and q.parentid=0 )t where t.rownum between ? and ?
question.man.mylist.type=select * from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,row_number() over( order by q.createtime desc ) rownum from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype = ? and q.title like ? and qlib.id=? and q.parentid=0 )t where t.rownum between ? and ?
question.man.mylist.sub.size=select count(*) from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.title like ?  and qlib.lid >=? and qlib.rid<=? and q.parentid=0
question.man.mylist.sub.size.type=select count(*) from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype  = ? and q.title like ?  and qlib.lid >=? and qlib.rid<=? and q.parentid=0
question.man.mylist.size=select count(*) from question q ,question_lib qlib \
		where q.qlibid=qlib.id and  q.title like ?  and qlib.id=? and q.parentid=0
question.man.mylist.size.type=select count(*) from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype = ? and q.title like ?  and qlib.id=? and q.parentid=0
question.child.maxsize=select max(sortid) from question where parentid = ?		
question.delete = delete from question where id = ?
    
###############sql for question######################

###############sql for exampaperlib ######################
eplib.add=insert into exampaperlib(name ,parentid,description,lid,rid) values(?,?,?,?,? )
eplib.alter=update exampaperlib set name= ?, parentid=?,description=? where id  = ? 
eplib.query.byparentidanduid=select id,name ,parentid,description from exampaperlib where parentid = ?  
eplib.query.byidanduid=select el.id,el.name,el.parentid, el.description,elp.name from exampaperlib el, exampaperlib elp  where el.parentid=elp.id and el.id =? 
eplib.parent.set=update exampaperlib set parentid=? where id =? and userid=?
eplib.querylrid.byidanduid= select  id,lid,rid from exampaperlib where id = ?  
###############sql for exampaperlib ######################

###############sql for exampaper######################
exampaper.query.mylist=select * from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,row_number() over(order by ep.createtime desc ) rownum from exampaper ep, exampaperlib epl \
						where ep.eplid = epl.id  and ep.title like ? and ep.eplid = ? )t where t.rownum between ? and ?
exampaper.query.mylist.sub=select * from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,row_number() over(order by ep.createtime desc ) rownum from exampaper ep, exampaperlib epl \
						where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=?)t where t.rownum between ? and ?
exampaper.query.mylist.size=select count(*) from exampaper ep, exampaperlib epl where ep.eplid = epl.id and ep.title like ? and ep.eplid = ? 
exampaper.query.mylist.sub.size=select count(*) from exampaper ep, exampaperlib epl where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=? 
exampaper.add=insert into exampaper( title,description, userid, eplid,showmod, during, createtime, opentimelimit,ep_tscore) values(?,?,?,?,?,?,?,?,?)
exampaper.alter=update exampaper set title=?,description=?, eplid=?,showmod=?, during=?,opentimelimit=?,ep_tscore=? where id = ? 
exampaper.query.byidanduid=select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from exampaper ep,exampaperlib epl where ep.id = ? and ep.userid = ? and ep.eplid = epl.id
exampaper.query.byid=select ep.id,ep.title,ep.description,ep.userid,ep.eplid, ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from exampaper ep left join exampaperlib epl on ep.eplid = epl.id where ep.id = ?
myexampaper.quiz=select * from (select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myscore,sqi.endtime,eu.realname,er.title,row_number() over(er.begintime) from  study_quizinfo sqi left join eluser eu on sqi.userid = eu.id left join exam_room er on sqi.roomid = er.id where sqi.epid = ?) t where t.rownum between ? and ?
##############sql for exampaperblock##########
epblock.query.maxsortid=select max(sortid) from exampaperblock where exampaperid=?
epblock.query.byid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,ep.title,epb.id,ep.showmod,epb.random,epb.rulestring from exampaperblock epb left join exampaper ep on epb.exampaperid = ep.id where epb.id =?
epblock.add=insert into exampaperblock(exampaperid,title,description,type,questionamount,eachscore,sortid,random,rulestring) values(?,?,?,?,?,?,?,?,?)
epblock.query.byepid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,epb.random,epb.rulestring from exampaperblock epb where epb.exampaperid=? order by epb.sortid asc
##epblock.query.random.byepid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,(select sum(eplevel1+eplevel2+eplevel3+eplevel4+eplevel5+eplevel) from exampaper_random eq where eq.blockid= epb.id) as rqcount,epb.random from exampaperblock epb where epb.exampaperid=? order by epb.sortid asc
epblock.alter = update exampaperblock set title = ?,description = ?,type = ?, eachscore = ?,sortid = ?,questionamount=?,random=? where id = ?
epblock.delete = delete from exampaperblock where id= ?
epblock.sortid.byid=select epb.exampaperid,epb.sortid from exampaperblock epb where epb.id =?
epblock.bigsortid.set=update exampaperblock set sortid= sortid-1 where exampaperid = ? and sortid>?


##############sql for exampaperblock question##########
epblock.question.bybid=select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.sortid,epq.rulestring from question q \
					left join exampaperblockquestion epq on epq.questionid = q.id left join question_lib qlb on q.qlibid = qlb.id where epq.blockid = ? order by epq.sortid asc
epblock.question.check=select * from exampaperblockquestion where blockid =? and questionid=?
epblock.question.sortid.bybqid=select sortid from exampaperblockquestion where blockid =? and questionid=?
epblock.question.add=insert into exampaperblockquestion(blockid,questionid,sortid,rulestring,score) values(?,?,?,?,?) 
epblock.question.maxsortid=select max(sortid) from exampaperblockquestion where blockid =? 
epblock.question.delete=delete from exampaperblockquestion where questionid =? and blockid=?
epblock.question.bigsortid.set=update exampaperblockquestion set sortid= sortid-1 where blockid=? and sortid >?
epblock.question.size.check = select epb.questionamount,count(epq.questionid) epqcount from exampaperblock epb left join  exampaperblockquestion epq on epq.blockid = epb.id where epb.id=? group by epb.questionamount
epblock.question.random.sub.size=select count(*) from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid >=? and qlib.rid<=? and q.parentid= 0  
epblock.question.random.size=select count(*) from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid=? and q.parentid= 0
epblock.question.random.sub=select * from(select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlib.name,row_number() over(order by newid()) rownum from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid >=? and qlib.rid<=? and q.parentid= 0) t where t.rownum between ? and ?
epblock.question.random=select * from(select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlib.name,row_number() over(order by newid()) rownum from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid=? and q.parentid= 0 ) t where t.rownum between ? and ?
epblock.question.random.qliblrid.byid = select  id,lid,rid from question_lib where id = ?
epblock.question.random.add=insert into exampaper_random(qlibid,blockid,eplevel1,eplevel2,eplevel3,eplevel4,eplevel5,eplevel,suboperate) values(?,?,?,?,?,?,?,?,?)
epblock.question.random.alter = update exampaper_random set eplevel1 =?,eplevel2 =?,eplevel3 =?,eplevel4 =?,eplevel5 =?,eplevel =? where id = ?
epblock.question.random.bybid=select er.id,er.blockid, epb.title,er.qlibid ,er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,er.suboperate,qlib.name,epb.type from exampaper_random er left join question_lib qlib on er.qlibid = qlib.id left join exampaperblock epb on er.blockid = epb.id where er.blockid = ?
epblock.question.random.byid=select er.id,er.blockid, epb.title,er.qlibid ,er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,er.suboperate,qlib.name,epb.type from exampaper_random er left join question_lib qlib on er.qlibid = qlib.id left join exampaperblock epb on er.blockid = epb.id where er.id = ?
epblock.question.random.delete=delete from  exampaper_random where id = ?
epblock.eprandom.bybid=select epb.exampaperid,ep.title,ep.showmod,epb.id,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epbr.id,ep.userid from exampaperblock epb \
				left join exampaper ep on epb.exampaperid = ep.id left join exampaper_random epbr on epb.id = epbr.blockid where epbr.blockid   =?

				
#################sql for stuff####
stuff.add=insert into question_stuff(title,description,fileext,onwer,createtime,length,type) values(?,?,?,?,?,?,?)
stuff.alter = update question_stuff set title = ?,description = ?,type = ? where id = ? 
stuff.delete =delete from question_stuff where id = ? and onwer = ?
stuff.query.list.byname=select * from (select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,row_number() over( order by qs.createtime desc ) rownum from question_stuff qs where qs.onwer = ? and qs.title like ?) t where t.rownum between ? and  ?
stuff.query.list.bynametype=select * from (select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,row_number() over( order by qs.createtime desc ) rownum from question_stuff qs where qs.onwer = ? and qs.title like ? and qs.type = ? ) t where t.rownum between ? and  ?
stuff.query.list.byname.size=select count(*) from question_stuff qs where qs.onwer = ? and qs.title like ? 
stuff.query.list.bynametype.size=select count(*) from question_stuff qs where qs.onwer = ? and qs.title like ? and qs.type = ?
stuff.query.byid=select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length, qs.type from question_stuff qs where qs.onwer = ? and qs.id = ?

question.art.list=select * from (select id,title,content,row_number() over(order by id desc) rownum from questionart where title like ?) t  where t.rownum between ? and ?



 