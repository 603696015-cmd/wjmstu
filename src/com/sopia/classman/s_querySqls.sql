####sql for class operate #######
classtype.add=insert into elclasstype(name,description,parentid,lid,rid)values(?,?,?,?,?)
classtype.alter=update elclasstype set name=?,description=?,parentid=? where id =?
classtype.query.byid=select id,name,description,parentid from elclasstype where id = ?
classtype.query.byparentid=select id,name,description,parentid from elclasstype where parentid = ?
#classtype.delete=update elclasstype set status = ? where id = ?
classtype.delete=delete from elclasstype where id = ?
classtype.lirid = select id,lid,rid from elclasstype where id = ?
class.add=insert into elclass(name,certificatename,cltype,creater,description ,optionalcredit,status,createtime,mainimg,global,group1 , group2,diplomatime ) values(?,?,?,?,?,?,?,?,?,?,?,?,?)

class.man.mylist=select * from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,row_number() over (order by cl.createtime desc) rownum from elclass cl, elclasstype clt where cl.cltype=clt.id and clt.lid>=? and clt.rid<=?  and cl.creater = ?  and cl.name  like ?  and (cl.status=0 or cl.status=1 ) ) t where t.rownum between ? and ?
class.man.mylistSize=select count(*) from elclass cl,elclasstype clt where clt.lid>=? and clt.rid<=? and cl.cltype=clt.id and cl.creater = ?   and cl.name  like ?  and cl.status<>?

class.man.myShlist=select * from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,row_number() over (order by cl.createtime desc) rownum from elclass cl, elclasstype clt where cl.cltype=clt.id and clt.lid>=? and clt.rid<=?  and cl.creater = ?  and cl.name  like ?  and cl.status=2 ) t where t.rownum between ? and ?
class.man.myShlistSize=select count(*) from elclass cl,elclasstype clt where clt.lid>=? and clt.rid<=? and cl.cltype=clt.id and cl.creater = ?   and cl.name  like ?  and cl.status=2

class.man.byuidandid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1 ,cl.group2,cl.diplomatime  from elclass cl,elclasstype clt where cl.cltype = clt.id and cl.creater = ? and cl.id = ?
class.alter=update elclass set name=? ,certificatename=?,cltype=?,description =?,optionalcredit=?,status =?,mainimg=?,global=?,group1= ?, group2 = ? ,diplomatime=? where id= ? and creater=?

class.course=select c.id,c.name,c.credit,cc.credit from course c ,class_course cc where cc.courseid = c.id and cc.classid= ? and cc.status = ?
class.course.all =select c.id,c.name from course c,department dep,eluser eu where eu.id = c.creater and eu.depid=dep.id and c.status = 1 and dep.lid>=? and dep.rid<=? and c.id not in(select cc.courseid from class_course cc where cc.classid = ?)
class.course.add =insert into class_course(classid,courseid,status,suggestcredit) values(?,?,?,?)
class.course.delete =delete from class_course where classid=? and courseid=?
class.coruse.credit.alter=update class_course set credit=? where courseid = ? and classid=?
class.apply.delete = insert into class_delete(classid,userid,deletetime) values(?,?,?)
class.assign.super =select * from( select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name cltname,  row_number() over ( order by cl.createtime desc ) rownum \
			 from elclass cl,elclasstype clt,class_assign ca where cl.cltype = clt.id \
			 and cl.id = ca.classid and cl.name like ? and ca.depid= ? \
			 and cl.status = ?) t t.rownum between ? and ?
class.assign.this=select * from ( select cl.id,cl.name clname,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name cltname, \
			 row_number() over(order by cl.createtime desc) rownum \
			 from elclass cl,elclasstype clt,eluser eu,department dep where cl.cltype = clt.id \
			 and cl.name like ? and eu.depid= dep.id and eu.id = cl.creater \
			 and eu.depid=dep.id and cl.status = ? and dep.lid>=? and dep.rid<=? ) t where t.rownum between ? and ?
class.canassgin.user = select eu.id,eu.realname,eu.username from eluser eu,department dep where eu.id not in ( select ca.userid from study_class ca where ca.classid = ? )\
			 and dep.id=eu.depid and dep.id = ? 
class.assigned.user = select eu.id,eu.realname,eu.username from eluser eu,department dep where eu.id in ( select ca.userid from study_class ca where ca.classid = ? )\
			 and dep.id=eu.depid and dep.id= ? 
class.assign2user.add =exec assign_class ?,? 
##insert into study_class(classid,userid,applydate,status) values(?,?,?,?)
class.assign2user.delete= delete from study_class where classid = ? and userid = ?
class.assign2user.course.byclid = select courseid ,status from class_course where classid = ?
class.canassgin.deps = select dep.id,dep.name from department dep ,department fdep where dep.id not in(select ca.depid from class_assign ca where ca.classid = ?)\
			and dep.id =fdep.id  and fdep.lid>=? and fdep.rid<=?
class.assigned.deps= select dep.id,dep.name from department dep ,department fdep where dep.id  in(select ca.depid from class_assign ca where ca.classid = ?)\
			and dep.id =fdep.id  and fdep.lid>=? and fdep.rid<=?
class.assign2dep.add = insert into class_assign(classid,depid,assigntime) values(?,?,?)
class.assign2dep.delete= delete from class_assign where classid = ? and depid = ?
class.apply.this= select * from (select cl.id,cl.name clname,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name cltname,row_number() over(order by cl.createtime desc) rownum \
			 from elclass cl,elclasstype clt,eluser eu,department dep where cl.cltype = clt.id \
			 and eu.depid= dep.id and eu.id = cl.creater and cl.name like ?  \
			 and eu.depid=dep.id and cl.status = ? and dep.lid >=? and dep.rid<=? \
			 and cl.id not in(select ca.classid from study_class ca where ca.userid = ?) ) t where t.rownum between ? and ?
class.apply=insert into study_class(classid,userid,applydate,status) values(?,?,?,?)
class.applyed =select * from (select cl.id,cl.name,cl.certificatename ,eu.id euid,eu.realname,row_number() over(order by ca.applydate desc) rownum from study_class ca ,eluser eu,elclass cl,department dep where ca.userid = eu.id and ca.classid = cl.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and ca.status = ? ) t where t.rownum between ? and ?
class.graduate.apply.list =select * from (select cl.id,cl.name,cl.certificatename ,eu.id euid,eu.realname,row_number() over(order by sc.applydate desc) rownum from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.status=1 and sc.userid = eu.id and cl.creater = ?  ) t where t.rownum between ? and ?
class.graduate.apply.list.size =select count(*) from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.userid = eu.id and sc.status=1 and cl.creater = ?

###class.my.study = select cl.id,cl.name,cl.certificatename, \
###		 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?) ,\
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?), \
###			 (select count(stu.userid) from study_class stu where cl.id=stu.classid ) ,ca.applydate,cl.optionalcredit \
###			 from elclass cl,study_class ca where cl.id = ca.classid and ca.userid = ? and ca.status = ? limit ?,?
class.byid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,eu.id,eu.realname,cl.global,cl.group1 ,cl.group2,cl.diplomatime  from elclass cl,elclasstype clt,eluser eu where eu.id = cl.creater and cl.cltype = clt.id and cl.id = ?
class.apply.status.set= update study_class set status = ? where userid = ? and classid =?
class.status.set= update elclass set status = ? where id =?
class.delete.apply.list =select * from (select cl.id,cl.name,cl.certificatename,cl.optionalcredit,eu.id euid,eu.realname,row_number() over(order by cl.createtime desc) rownum \
			 from elclass cl,eluser eu, department dep  where cl.creater = eu.id and eu.depid = dep.id  and cl.cltype =?  and cl.name like ?  and dep.lid>=? and dep.rid <=? and cl.status = ? )t where t.rownum between ? and ?
###class.delete.apply.list = select cl.id,cl.name,cl.certificatename, \
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?) ,\
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?), \
###			 (select count(stu.userid) from study_class stu where cl.id=stu.classid ) ,cl.optionalcredit,eu.id,eu.realname \
###			 from elclass cl,eluser eu, department dep  where cl.creater = eu.id and eu.depid = dep.id and dep.lid>=? and dep.rid <=? and cl.status = ?  limit ?,?
####sql for class operate #######