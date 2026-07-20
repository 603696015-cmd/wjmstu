###############sql for qlib######################
qlib.add=insert into question_lib(name ,parentid,description,lid,rid) values(?, ?,?,?,?)
qlib.alter=update question_lib set name= ?, parentid=?,description=? where id  = ? 
qlib.query.byidanduid=select q.id,q.name,q.parentid,q.description,qp.name,q.lid,q.rid from question_lib q,question_lib qp where q.id = ? and q.userid =? and q.parentid = qp.id
qlib.query.byparentidanduid=select id,name ,parentid,description, lid, rid  from question_lib where parentid = ? order by id
/*qlib.parent.set=update question_lib set parentid=? where id =? and userid=?*/
qlib.parent.set=update question_lib set parentid=? where id =?
qlib.queryid.byidanduid = select  id from question_lib where name = ?  
qlib.querylrid.byidanduid = select  id,lid,rid from question_lib where id = ?  

###############sql for qlib######################
###############sql for question######################
question.add=insert into question( title, content, subject,qexplain, userid, qlibid,createtime, qlevel, answer,qtype,scoreper,parentid,minword,sortid,oldrulestring,oldscore,fwsize,status,fasheng_question,media_file,model_voice,model_voice_text,voice_path,fen_content,stem_text,front_half_media_file,standard_answer) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
question.alter= update question set title =?, content=?, subject=?,qexplain = ?, qlibid=?, qlevel=?, answer=?,scoreper=?,minWord=?,sortid=?,oldrulestring=?,oldscore=?,fwsize=? ,fasheng_question=? , media_file=?, model_voice=?,model_voice_text=?,voice_path=?,fen_content=?,stem_text=?,front_half_media_file=?,standard_answer=? where id=?
question.querybyid=select q.id,q.title ,q.content,q.subject,q.qexplain,q.qlibid,q.modifytime,q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,q.scoreper,q.parentid,q.minWord,q.oldrulestring,q.oldscore ,q.sortid,q.fasheng_question,q.media_file,q. model_voice,q. model_voice_text ,q.voice_path,fen_content,q.fwsize,q.stem_text,q.front_half_media_file,q.right_answer,q.standard_answer from QUESTION q left join QUESTION_LIB qlb on q.qlibid = qlb.id where q.id = ? 

question.man.mylist.sub=select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status from question q ,question_lib qlib \
		where q.qlibid=qlib.id and  q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?
question.man.mylist.sub.type=select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype=? and q.title like ? and qlib.lid >=? and qlib.rid<=? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?
question.man.mylist=select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.title like ? and qlib.id=? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?
question.man.mylist.type=select * from(select t.*,rownum rn from (select q.id, q.title,q.qlibid,q.createtime,q.modifytime,q.qtype,qlib.name,q.qlevel,q.scoreper,q.parentid,q.minword,q.status from question q ,question_lib qlib \
		where q.qlibid=qlib.id and q.qtype = ? and q.title like ? and qlib.id=? and q.parentid=0  order by q.createtime desc )t  where rownum <=? ) where rn >=?
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
eplib.query.byparentidanduid=select id,name ,parentid,description,lid,rid from exampaperlib where parentid = ?  order by id
eplib.query.byidanduid=select el.id,el.name,el.parentid, el.description,elp.name,el.lid,el.rid from exampaperlib el left join  exampaperlib elp on el.parentid=elp.id where el.id =? 
eplib.parent.set=update exampaperlib set parentid=? where id =? and userid=?
eplib.querylrid.byidanduid= select  id,lid,rid from exampaperlib where id = ?  
###############sql for exampaperlib ######################

###############sql for exampaper######################
exampaper.query.mylist= select * from(select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,ep.status from exampaper ep, exampaperlib epl \
						where ep.eplid = epl.id  and ep.title like ? and ep.eplid = ? order by ep.createtime desc )t  where rownum <=? ) where rn >=?
exampaper.query.mylist.sub=select * from(select t.*,rownum rn from (select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from exampaper ep, exampaperlib epl \
						where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=? order by ep.createtime desc )t  where rownum <=? ) where rn >=?
exampaper.query.mylist.size=select count(*) from exampaper ep, exampaperlib epl where ep.eplid = epl.id and ep.title like ? and ep.eplid = ? 
exampaper.query.mylist.sub.size=select count(*) from exampaper ep, exampaperlib epl where ep.eplid = epl.id and ep.title like ? and epl.lid >= ? and epl.rid<=? 
exampaper.add=insert into exampaper( title,description, userid, eplid,showmod, during, createtime, opentimelimit,ep_tscore,queryurl,status,showtype) values(?,?,?,?,?,?,?,?,?,?,?,?)
exampaper.alter=update exampaper set title=?,description=?, eplid=?,showmod=?, during=?,opentimelimit=?,ep_tscore=?,queryurl=?,showtype=? where id = ? 
exampaper.query.byidanduid=select ep.id,ep.title,ep.description,ep.userid,ep.eplid,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore from exampaper ep,exampaperlib epl where ep.id = ? and ep.userid = ? and ep.eplid = epl.id
exampaper.query.byid=select ep.id,ep.title,ep.description,ep.userid,ep.eplid, ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit,epl.name,ep.ep_tscore,ep.ep_realscore,ep.queryurl,ep.showtype from exampaper ep left join exampaperlib epl on ep.eplid = epl.id where ep.id = ?
myexampaper.quiz=select * from (select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myscore,sqi.endtime,eu.realname,er.title,row_number() over(er.begintime) from  study_quizinfo sqi left join eluser eu on sqi.userid = eu.id left join exam_room er on sqi.roomid = er.id where sqi.epid = ?) t where t.rownum between ? and ?
##############sql for exampaperblock##########
epblock.query.maxsortid=select max(sortid) from exampaperblock where exampaperid=?
epblock.query.byid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,ep.title,epb.id,ep.showmod,epb.random,epb.rulestring,epb.fwsize,epb.answertime, epb.secondscore,epb.cosPlayRemark,epb.readsort  from exampaperblock epb left join exampaper ep on epb.exampaperid = ep.id where epb.id =?
epblock.add=insert into exampaperblock(exampaperid,title,description,type,questionamount,eachscore,sortid,random,rulestring,fwsize,answertime,secondscore,readsort) values(?,?,?,?,?,?,?,?,?,?,?,?,?)
epblock.query.byepid=SELECT EPB.EXAMPAPERID,EPB.TITLE,EPB.DESCRIPTION,EPB.TYPE,EPB.QUESTIONAMOUNT,EPB.EACHSCORE,EPB.SORTID,EPB.ID,EPB.RANDOM,EPB.RULESTRING,EPB.REALSCORE,EPB.FWSIZE FROM EXAMPAPERBLOCK EPB WHERE EPB.EXAMPAPERID=? ORDER BY EPB.SORTID ASC
##epblock.query.random.byepid=select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,(select sum(eplevel1+eplevel2+eplevel3+eplevel4+eplevel5+eplevel) from exampaper_random eq where eq.blockid= epb.id) as rqcount,epb.random from exampaperblock epb where epb.exampaperid=? order by epb.sortid asc
epblock.alter = update exampaperblock set title = ?,description = ?,type = ?, eachscore =  ?,questionamount=?,random=?,rulestring=?,fwsize=?,answertime=?,secondscore=?  where id = ?
epblock.delete = delete from exampaperblock where id= ?
epblock.sortid.byid=select epb.exampaperid,epb.sortid from exampaperblock epb where epb.id =?
epblock.bigsortid.set=update exampaperblock set sortid= sortid-1 where exampaperid = ? and sortid>?


##############sql for exampaperblock question##########
epblock.question.bybid=SELECT Q.ID,Q.TITLE ,Q.CONTENT,Q.SUBJECT,Q.QEXPLAIN, Q.QLIBID,Q.MODIFYTIME,Q.CREATETIME,Q.QLEVEL,Q.ANSWER,Q.QTYPE,QLB.NAME,EPQ.SORTID,EPQ.RULESTRING,EPQ.SCORE,Q.FWSIZE,q.fasheng_question FROM QUESTION Q \
					LEFT JOIN EXAMPAPERBLOCKQUESTION EPQ ON EPQ.QUESTIONID = Q.ID LEFT JOIN QUESTION_LIB QLB ON Q.QLIBID = QLB.ID WHERE EPQ.BLOCKID = ? ORDER BY EPQ.SORTID ASC
epblock.question.check=select * from exampaperblockquestion where blockid =? and questionid=?
epblock.question.sortid.bybqid=select sortid from exampaperblockquestion where blockid =? and questionid=?
epblock.question.add=insert into exampaperblockquestion(blockid,questionid,score,sortid) values(?,?,?,?) 
epblock.question.maxsortid=select max(sortid) from exampaperblockquestion where blockid =? 
epblock.question.delete=delete from exampaperblockquestion where questionid =? and blockid=?
epblock.question.bigsortid.set=update exampaperblockquestion set sortid= sortid-1 where blockid=? and sortid >?
epblock.question.size.check = select epb.questionamount,count(epq.questionid) epqcount from exampaperblock epb left join  exampaperblockquestion epq on epq.blockid = epb.id where epb.id=? group by epb.questionamount
epblock.question.random.sub.size=select count(*) from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.lid >=? and qlib.rid<=? and q.parentid= 0  and q.status != 1
epblock.question.random.size=select count(*) from question q,question_lib qlib where q.qlibid=qlib.id and q.qtype = ? and q.qlevel like ? and qlib.id=? and q.parentid= 0 and q.status != 1
epblock.question.random.sub=SELECT * FROM (SELECT T.*,ROWNUM RN FROM(SELECT Q.ID,Q.TITLE ,Q.CONTENT,Q.SUBJECT,Q.QEXPLAIN, Q.QLIBID,Q.MODIFYTIME,Q.CREATETIME,Q.QLEVEL,Q.ANSWER,Q.QTYPE,QLIB.NAME FROM QUESTION Q,QUESTION_LIB QLIB WHERE Q.QLIBID=QLIB.ID AND Q.QTYPE = ? AND Q.QLEVEL LIKE ? AND QLIB.LID >=? AND QLIB.RID<=? AND Q.PARENTID= 0 AND Q.STATUS != 1 ORDER BY DBMS_RANDOM.VALUE()) T WHERE ROWNUM<=?) WHERE RN>=?
epblock.question.random=SELECT * FROM( SELECT T.* ,ROWNUM RN FROM (SELECT Q.ID,Q.TITLE ,Q.CONTENT,Q.SUBJECT,Q.QEXPLAIN, Q.QLIBID,Q.MODIFYTIME,Q.CREATETIME,Q.QLEVEL,Q.ANSWER,Q.QTYPE,QLIB.NAME FROM QUESTION Q,QUESTION_LIB QLIB WHERE Q.QLIBID=QLIB.ID AND Q.QTYPE = ? AND Q.QLEVEL LIKE ? AND QLIB.ID=? AND Q.PARENTID= 0 AND Q.STATUS != 1 ORDER BY DBMS_RANDOM.VALUE()) T WHERE ROWNUM<=?) WHERE RN>=?
epblock.question.random.qliblrid.byid = SELECT  ID,LID,RID FROM QUESTION_LIB WHERE ID = ?
epblock.question.random.add=insert into exampaper_random(qlibid,blockid,eplevel1,eplevel2,eplevel3,eplevel4,eplevel5,eplevel,suboperate) values(?,?,?,?,?,?,?,?,?)
epblock.question.random.alter = update exampaper_random set eplevel1 =?,eplevel2 =?,eplevel3 =?,eplevel4 =?,eplevel5 =?,eplevel =? where id = ?
epblock.question.random.bybid=select er.id,er.blockid, epb.title,er.qlibid ,er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,er.suboperate,qlib.name,epb.type from exampaper_random er left join question_lib qlib on er.qlibid = qlib.id left join exampaperblock epb on er.blockid = epb.id where er.blockid = ?
epblock.question.random.byid=select er.id,er.blockid, epb.title,er.qlibid ,er.eplevel1,er.eplevel2,er.eplevel3,er.eplevel4,er.eplevel5,er.eplevel,er.suboperate,qlib.name,epb.type from exampaper_random er left join question_lib qlib on er.qlibid = qlib.id left join exampaperblock epb on er.blockid = epb.id where er.id = ?
epblock.question.random.delete=delete from  exampaper_random where id = ?
epblock.eprandom.bybid=select epb.exampaperid,ep.title,ep.showmod,epb.id,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epbr.id,ep.userid from exampaperblock epb \
				left join exampaper ep on epb.exampaperid = ep.id left join exampaper_random epbr on epb.id = epbr.blockid where epbr.blockid   =?

				
#################sql for stuff####
stuff.add=insert into question_stuff(title,description,fileext,onwer,createtime,length,type,parentid,key,fileinfo,stuffpic,stuffhot,fromchange) values(?,?,?,?,?,?,?,?,?,?,?,?,?)
stuff.alter = update question_stuff set title = ?,description = ?,type = ? where id = ? 
stuff.alter.jpg = update question_stuff set generatejpg=? where id = ? 
stuff.delete =delete from question_stuff where id = ? and onwer = ?
stuff.delete.byid =delete from question_stuff where id = ?
stuff.query.list.byname=select * from ( select t.* ,rownum rn from (select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs where qs.onwer = ? and qs.title like ? order by qs.createtime desc )t where rownum<=?) where rn>=?
stuff.query.list.bynametype=select * from ( select t.* ,rownum rn from (select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type from question_stuff qs where qs.onwer = ? and qs.title like ? and qs.type = ? order by qs.createtime desc)t where rownum<=?) where rn>=?
stuff.query.list.byname.size=select count(*) from question_stuff qs where qs.onwer = ? and qs.title like ? 
stuff.query.list.bynametype.size=select count(*) from question_stuff qs where qs.onwer = ? and qs.title like ? and qs.type = ?
stuff.query.list.jpg=select id,title from question_stuff where fileext in ('doc','docx','xls','xlsx','pdf','ppt','txt') and generatejpg=1 
stuff.query.byid=select qs.id qsid,qs.title qstitle,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length, qs.type,qsp.id qspid, qsp.title qsptitle,qs.shared,qs.onwer,qs.fileinfo,qs.stuffpic,qs.stuffhot,qs.fromchange  from question_stuff qs left join question_stuff qsp on qs.parentid = qsp.id where qs.id = ?
stuff.query.byuid=select qs.id qsid,qs.title qstitle,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length, qs.type,qsp.id qspid, qsp.title qsptitle,qs.shared,qs.onwer,qs.fileinfo,qs.stuffpic,qs.stuffhot from question_stuff qs left join question_stuff qsp on qs.parentid = qsp.id where qs.onwer = ? and qs.id = ?

question.art.list=select * from(select t.*,rownum rn from (select id,title,content from questionart where title like ? order by id desc) t where rownum<=?) where rn>=?
alter table QUESTION_STUFF add shared number default 0 not null;
stuff.query.fromchange.by.id=select fromchange from question_stuff where id=?

 