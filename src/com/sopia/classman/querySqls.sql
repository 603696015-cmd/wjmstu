####sql for class operate #######
classtype.add=insert into elclasstype(name,description,parentid,lid,rid)values(?,?,?,?,?)
classtype.alter=update elclasstype set name=?,description=?,parentid=? where id =?
classtype.query.byid=select id,name,description,parentid from elclasstype where id = ?
classtype.query.byparentid=select id,name,description,parentid from elclasstype where parentid = ?
#classtype.delete=update elclasstype set status = ? where id = ?
classtype.delete=delete from elclasstype where id = ?
classtype.lirid = select id,lid,rid from elclasstype where id = ?
class.add=insert into elclass(name,certificatename,cltype,creater,description ,optionalcredit,status,createtime,mainimg,global,group1 , group2,diplomatime ) values(?,?,?,?,?,?,?,?,?,?,?,?,?)

class.man.mylist=select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name from elclass cl, elclasstype clt where cl.cltype=clt.id and clt.lid>=? and clt.rid<=?  and cl.creater = ?  and cl.name  like ?  and (cl.status=0||cl.status=1 )order by cl.createtime desc limit ?,?
class.man.mylistSize=select count(*) from elclass cl,elclasstype clt where clt.lid>=? and clt.rid<=? and cl.cltype=clt.id and cl.creater = ?  and cl.name  like ?  and cl.status<>?

class.man.myShlist=select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name from elclass cl, elclasstype clt where cl.cltype=clt.id and clt.lid>=? and clt.rid<=?  and cl.creater = ?  and cl.name  like ?  and cl.status=2 order by cl.createtime desc limit ?,?
class.man.myShlistSize=select count(*) from elclass cl,elclasstype clt where clt.lid>=? and clt.rid<=? and cl.cltype=clt.id and cl.creater = ?  and cl.name  like ?  and cl.status=2

class.man.byuidandid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1 ,cl.group2,cl.diplomatime  from elclass cl,elclasstype clt where cl.cltype = clt.id and cl.creater = ? and cl.id = ?
class.alter=update elclass set name=? ,certificatename=?,cltype=?,description =?,optionalcredit=?,status =?,mainimg=?,global=?,group1= ?, group2 = ? ,diplomatime=? where id= ? and creater=?

class.course=select c.id,c.name,c.credit,cc.credit from course c ,class_course cc where cc.courseid = c.id and cc.classid= ? and cc.status = ?
class.course.all =select c.id,c.name from course c,department dep,eluser eu where eu.id = c.creater and eu.depid=dep.id and c.status = 1 and dep.lid>=? and dep.rid<=? and c.id not in(select cc.courseid from class_course cc where cc.classid = ?)
class.course.add =insert into class_course(classid,courseid,status,suggestcredit) values(?,?,?,?)
class.course.delete =delete from class_course where classid=? and courseid=?
class.coruse.credit.alter=update class_course set credit=? where courseid = ? and classid=?
class.apply.delete = insert into class_delete(classid,userid,deletetime) values(?,?,?)
class.assign.super = select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name \
			 from elclass cl,elclasstype clt,class_assign ca where cl.cltype = clt.id \
			 and cl.id = ca.classid and cl.name like ? and ca.depid= ? \
			 and cl.status = ? order by cl.createtime desc limit ?,?
class.assign.this= select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name  \
			 from elclass cl,elclasstype clt,eluser eu,department dep where cl.cltype = clt.id \
			 and cl.name like ? and eu.depid= dep.id and eu.id = cl.creater \
			 and eu.depid=dep.id and cl.status = ? and dep.lid>=? and dep.rid<=? order by cl.createtime desc limit ?,?
class.canassgin.user = select eu.id,eu.realname from eluser eu,department dep where eu.id not in ( select ca.userid from study_class ca where ca.classid = ? )\
			 and dep.id=eu.depid and dep.lid>=? and dep.rid<=? 
class.assigned.user = select eu.id,eu.realname from eluser eu,department dep where eu.id in ( select ca.userid from study_class ca where ca.classid = ? )\
			 and dep.id=eu.depid and dep.lid>=? and dep.rid<=? 
class.assign2user.add = insert into study_class(classid,userid,applydate,status) values(?,?,?,?)
class.assign2user.delete= delete from study_class where classid = ? and userid = ?
class.assign2user.course.byclid = select courseid ,status from class_course where classid = ?
class.canassgin.deps = select dep.id,dep.name from department dep ,department fdep where dep.id not in(select ca.depid from class_assign ca where ca.classid = ?)\
			and dep.id =fdep.id  and fdep.lid>=? and fdep.rid<=?
class.assigned.deps= select dep.id,dep.name from department dep ,department fdep where dep.id  in(select ca.depid from class_assign ca where ca.classid = ?)\
			and dep.id =fdep.id  and fdep.lid>=? and fdep.rid<=?
class.assign2dep.add = insert into class_assign(classid,depid,assigntime) values(?,?,?)
class.assign2dep.delete= delete from class_assign where classid = ? and depid = ?
class.apply.this= select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name \
			 from elclass cl,elclasstype clt,eluser eu,department dep where cl.cltype = clt.id \
			 and eu.depid= dep.id and eu.id = cl.creater and cl.name like ?  \
			 and eu.depid=dep.id and cl.status = ? and dep.lid >=? and dep.rid<=? \
			 and cl.id not in(select ca.classid from study_class ca where ca.userid = ?) order by cl.createtime desc limit ?,?
class.apply=insert into study_class(classid,userid,applydate,status) values(?,?,?,?)
class.applyed =select cl.id,cl.name,cl.certificatename ,eu.id,eu.realname from study_class ca ,eluser eu,elclass cl,department dep where ca.userid = eu.id and ca.classid = cl.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and ca.status = ? limit ?,?
class.graduate.apply.list =select cl.id,cl.name,cl.certificatename ,eu.id,eu.realname from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.status=1 and sc.userid = eu.id and cl.creater = ? order by sc.applydate desc limit ?,?
class.graduate.apply.list.size =select count(*) from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.userid = eu.id and sc.status=1 and cl.creater = ?

###class.my.study = select cl.id,cl.name,cl.certificatename, \
###		 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?) ,\
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?), \
###			 (select count(stu.userid) from study_class stu where cl.id=stu.classid ) ,ca.applydate,cl.optionalcredit \
###			 from elclass cl,study_class ca where cl.id = ca.classid and ca.userid = ? and ca.status = ? limit ?,?
class.byid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,eu.id,eu.realname,cl.global,cl.group1 ,cl.group2,cl.diplomatime  from elclass cl,elclasstype clt,eluser eu where eu.id = cl.creater and cl.cltype = clt.id and cl.id = ?
class.apply.status.set= update class_apply set status = ? where userid = ? and classid =?
class.status.set= update elclass set status = ? where id =?
class.delete.apply.list = select cl.id,cl.name,cl.certificatename,cl.optionalcredit,eu.id,eu.realname \
			 from elclass cl,eluser eu, department dep  where cl.creater = eu.id and eu.depid = dep.id and  and cl.cltype =?  and cl.name like ?  dep.lid>=? and dep.rid <=? and cl.status = ?  limit ?,?
###class.delete.apply.list = select cl.id,cl.name,cl.certificatename, \
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?) ,\
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?), \
###			 (select count(stu.userid) from class_apply stu where cl.id=stu.classid ) ,cl.optionalcredit,eu.id,eu.realname \
###			 from elclass cl,eluser eu, department dep  where cl.creater = eu.id and eu.depid = dep.id and dep.lid>=? and dep.rid <=? and cl.status = ?  limit ?,?
####sql for class operate #######