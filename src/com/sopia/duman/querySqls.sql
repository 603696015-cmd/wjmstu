############sql for department operate###############
dep.add =insert into DEPARTMENT(name, description,parentid,manager,address,postalcode,phone,fax,email,lid,rid ) values(?,?,?,?,?,?,?,?,?,?,?)
dep.alter=update DEPARTMENT set name=?, description=?,parentid=?,manager=?,address=?,postalcode=?,phone=?,fax=?,email=? where id = ?
dep.query.root=select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email, u.realname from DEPARTMENT d left join  ELUSER u on u.id = d.manager  where d.parentid=0
dep.query.byid=select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email, u.realname from DEPARTMENT d left join  ELUSER u on u.id = d.manager  where d.id = ?
dep.query.lrid.byid=select d.id,d.lid,d.rid from DEPARTMENT d where d.id = ?
dep.query.parent.byid=select d.id,d.parentid from DEPARTMENT d where d.id = ?
dep.parent.set=update DEPARTMENT set parentid =? where id = ?
dep.delete=delete from DEPARTMENT where id = ?
dep.query.bypidandcid=select id,name,description,parentid,manager,address,postalcode,phone,fax,email from DEPARTMENT where parentid = ? 
dep.delete.user.set = update eluser set depid = ? where depid = ?
dep.delete.course.set = delete from  course_dep  where depid = ?
dep.delete.class.set = delete from class_assign  where depid = ?
dep.delete.kltype.set = delete from kltype_dep  where depid = ?
dep.delete.dep.set = update department set parentid = ? where parentid = ?
dep.query.subs=select id from department where lid>=? and rid<=? 
dep.query.dsubs=select id from department where parentid=? 

############sql for department operate###############
############sql for user operate ###########
user.add=insert into ELUSER(username,password,realname,userno,phone,address,email,role,depid,valid, sex, age , edubg , major , studyDir , gradchool , graddate , jobdate , protitle , jobdesc ,  majorc ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
user.alter=update ELUSER set  password=?,realname=?,userno=?,phone=?,address=?,email=?,role=?,depid=?,valid=?, sex=?, age=? , edubg=? , major=? , studyDir=? , gradchool=? , graddate=? , jobdate =?, protitle=? , jobdesc=? ,  majorc=?  where id = ?
user.delete = delete from ELUSER where id = ?
user.check.pwd = select * from eluser where username=? and password = ?

user.query.byid=select eu.id,eu.username,eu.password,eu.realname,eu.userno,eu.phone,eu.address,eu.email,eu.role,eu.depid,dep.name,eu.valid,er.name, eu.sex, eu.age , eu.edubg , eu.major , eu.studyDir , eu.gradchool , eu.graddate , eu.jobdate , eu.protitle , eu.jobdesc ,eu.majorc,eu.score,eu.dot,eu.xfscore \
			from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.id=?
user.query.un=select eu.id,eu.username, eu.password,eu.realname,eu.userno, eu.phone,eu.address,eu.email, eu.role,eu.depid,eu.valid,er.name from eluser eu,elrole er where er.id = eu.role and  eu.username=?
user.query.bydepid=select eu.id,eu.username from ELUSER eu where eu.depid =?
user.query.subs.bydepidandos=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid,er.name  from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? limit ?,?
user.query.subs.size.bydepidandos=select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=?
user.query.bydepidandos=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid,er.name from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? limit ?,?
user.query.size.bydepidandos=select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? 
user.query.vsubs.bydepidandos=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.valid=true limit ?,?
user.query.vsubs.size.bydepidandos=select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.valid=true
user.query.vbydepidandos=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid  from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.valid=true limit ?,?
user.query.vsize.bydepidandos=select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.valid=true
			
user.query.subs.bydepidandos.role=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid,er.name  from ELUSER eu left join elrole er on er.id = eu.role left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.role=? limit ?,?
user.query.subs.size.bydepidandos.role=select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.role=?
user.query.bydepidandos.role=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid,er.name from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.role=? limit ?,?
user.query.size.bydepidandos.role=select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.role=?
 
user.role.set=update eluser set role = ? where id = ?
user.role.depmanger.back=update eluser set role = ? where depid = ? and role = 2
user.myinfo.alter=update ELUSER set realname=?,userno=?,phone=?,address=?,email =? where id=? 
user.myinfo.pwd.alter=update ELUSER set password=? where id=? 
user.check.pwd.byid =select * from  ELUSER where password=? and id=? 
user.query.byrole = select eu.id,eu.realname,dep.id,dep.name from ELUSER eu left join department dep on eu.depid = dep.id where eu.role <> ?
user.check.un = select * from ELUSER  where username =  ?


#####ROLE operate######
user.role.add = insert into elrole(name,description) values(?,?)
user.role.alter =update elrole set name = ?, description =? where id = ?
user.role.list= select id,name,description from elrole 
user.role.byid=select id,name,description from elrole where id = ?
user.role.delete = delete from elrole where id = ?
user.role.set.byrid = update eluser set role = ? where role = ?
user.rolefunc.delete.byrid = delete from elrolefunc where roleid = ?

user.rolefunc.add=insert into elrolefunc(roleid,funcid) values(?,?)
user.rolefunc.delete=delete from elrolefunc where roleid =?
user.group.assign.list = select eu.id,eu.realname,dep.id,dep.name,eu.username from elgroup2user gu,eluser eu,department dep where dep.id=eu.depid \
							and eoou.id=gu.userid and gu.gid = ? limit ?,?

user.func.add=insert into elfunc(funccode,name,description,parentid,needcheck,params,target) values(?,?,?,?,?,?,?)
user.func.child= select id,funccode,name,description,parentid,needcheck from elfunc where parentid = ? order by description
user.func.list= select id,funccode,needcheck from elfunc
user.func.unclist = select id,funccode from elfunc where needcheck = false
user.rolefunc.list.byrid = select ef.id,ef.funccode,ef.name,ef.description from elfunc ef,elrolefunc erf where erf.funcid=ef.id and erf.roleid = ?
user.func.alter = update elfunc set funccode = ?,name = ? , description = ?,parentid = ?,needcheck=?,params=?,target=? where id = ?
user.func.byid = select ef.id,ef.funccode,ef.name,ef.description,ef.parentid,ef.needcheck,params,target from elfunc ef where ef.id = ?
user.func.delete.byid = delete from elfunc where id = ?
###user.func.delete.bypid = delete from elfunc where parentid = ?
user.rolefunc.delete.byid = delete from elrolefunc where funcid = ?

system.alter= update systemconf set content = ? where type = ?
system.bytype = select type,content from systemconf where type = ?
 

