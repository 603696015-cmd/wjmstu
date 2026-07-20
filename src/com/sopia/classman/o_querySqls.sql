
####sql for class operate #######
classtype.add=insert into elclasstype(name,description,parentid,lid,rid,isshared)values(?,?,?,?,?,?)
classtype.alter=update elclasstype set name=?,description=?,parentid=?,isshared=? where id =?
#classtype.query.byid= select el1.id,el1.name,el1.description,el1.parentid,el2.name,el1.isshared from elclasstype el1 left join elclasstype el2 on el1.parentid = el2.id where el1.id = ?
classtype.query.byid= select el1.id,el1.name,el1.description,el1.parentid,el2.name,el1.isshared,el1.lid,el1.rid from elclasstype el1 left join elclasstype el2 on el1.parentid = el2.id where el1.id = ?
#classtype.query.byparentid=select id,name,description,parentid from elclasstype where parentid = ?
classtype.query.byparentid=select id,name,description,parentid,lid,rid from elclasstype where parentid = ? order by id
#classtype.delete=update elclasstype set status = ? where id = ?
classtype.delete=delete from elclasstype where id = ?
classtype.lirid = select id,lid,rid from elclasstype where id = ?
class.add=insert into elclass(name,certificatename,cltype,creater,description ,optionalcredit,status,createtime,mainimg,global,group1 , group2,diplomatime,isapplication,starttime,finishtime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)

class.man.mylist= select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname from elclass cl, elclasstype clt where cl.cltype=clt.id and clt.lid>=? and clt.rid<=?  and cl.creater = ? and cl.name  like ? and cl.status >=0 order by cl.createtime desc )t where rownum <= ? ) where rn>=?
class.man.mylistSize=select count(*) from elclass cl,elclasstype clt where clt.lid>=? and clt.rid<=? and cl.cltype=clt.id and cl.creater = ?  and cl.name  like ? and   cl.status!=9

class.man.myShlist= select *   from (select t.*, rownum rn  from (select   cl.id, cl.name, cl.certificatename, cl.cltype,  cl.optionalcredit, cl.status, clt.name cltname  from elclass cl, elclasstype clt, eluser u, department dep  where cl.cltype = clt.id and cl.creater = u.id	 and u.depid = dep.id  and cl.status = ?  and cl.name like ?  and dep.lid >= ?\
                     and dep.rid <= ?  and clt.lid >= ?  and clt.rid <= ?  order by cl.createtime desc) t  where rownum <= ?)  where rn >= ?
class.man.myShlistSize=select count(*) from elclass cl, elclasstype clt, eluser u, department dep  where cl.cltype = clt.id and cl.creater = u.id  and u.depid = dep.id and cl.status = ? and cl.name like ?  and dep.lid >= ?  and dep.rid <= ? and clt.lid >= ?  and clt.rid <= ?

#class.man.byuidandid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1 ,cl.group2,cl.diplomatime  from elclass cl,elclasstype clt where cl.cltype = clt.id and cl.creater = ? and cl.id = ?
class.man.byuidandid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1 ,cl.group2,cl.diplomatime,cl.starttime,cl.finishtime,cl.classtype  from elclass cl,elclasstype clt where cl.cltype = clt.id and cl.creater = ? and cl.id = ?
class.alter=update elclass set name=? ,certificatename=?,cltype=?,description =?,optionalcredit=?,status =?,mainimg=?,global=?,group1= ?, group2 = ? ,diplomatime=? where id= ? and creater=?

##class.course=select c.id,c.name,c.credit,cc.credit from course c ,class_course cc where cc.courseid = c.id and cc.classid= ? and cc.status = ?
class.course=select  c.id, c.name, c.credit, cc.credit, c.createtime,  c.during, ct.id, ct.name, el.id, el.realname,cc.suggestcredit,cc.setcredit,cc.getcredit,c.roomstart,c.roomend from course c, class_course cc, course_type ct,eluser el  where cc.courseid = c.id and c.creater = el.id and c.ctypeid=ct.id and cc.classid = ? and cc.status = ?
##class.course.page=select * from (select t.id, rownum rn from (select c.id, c.name, c.credit, cc.credit, c.createtime,c.during, ct.id, ct.name, el.id, el.realname,cc.suggestcredit, cc.setcredit, cc.getcredit \
 				from course c, class_course cc, course_type ct, eluser el where cc.courseid = c.id and c.creater = el.id and c.ctypeid = ct.id and cc.classid = ? and cc.status = ?) t where rownum <= ?) where rn >= ?
##class.course.pageSize =select count(*) from class_course cc where cc.classid = ? and cc.status = ?
class.course.all =select c.id,c.name from course c,department dep,eluser eu where eu.id = c.creater and eu.depid=dep.id and c.status = 1 and dep.lid>=? and dep.rid<=? and c.id not in(select cc.courseid from class_course cc where cc.classid = ?)
class.course.add =insert into class_course(classid,courseid,status,suggestcredit) values(?,?,?,?)
class.course.delete =delete from class_course where classid=? and courseid=?
class.coruse.credit.alter=update class_course set credit=? where courseid = ? and classid=?
class.apply.delete = insert into class_delete(classid,userid,deletetime) values(?,?,?)
class.assign.super = select * from (select t.*, rownum rn from ( select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name cltname \
			 from elclass cl,elclasstype clt,class_assign ca where cl.cltype = clt.id \
			 and cl.id = ca.classid and cl.name like ? and ca.depid= ? \
			 and cl.status = ? order by cl.createtime desc ) t where rownum <= ? ) where rn>=?
class.assign.this= select * from (select t.*, rownum rn from ( select cl.id,cl.name clname,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name cltname \
			 from elclass cl,elclasstype clt,eluser eu,department dep where cl.cltype = clt.id \
			 and cl.name like ? and eu.depid= dep.id and eu.id = cl.creater \
			 and eu.depid=dep.id and cl.status = ? and dep.lid>=? and dep.rid<=? order by cl.createtime desc)t where rownum <= ? ) where rn>=?
class.canassgin.user = select eu.id,eu.realname,eu.username from eluser eu,department dep where eu.id not in ( select ca.userid from study_class ca where ca.classid = ? )\
			 and dep.id=eu.depid and dep.id = ? 
class.assigned.user = select eu.id,eu.realname,eu.username from eluser eu,department dep where eu.id in ( select ca.userid from study_class ca where ca.classid = ? )\
			 and dep.id=eu.depid and dep.id= ? 
class.assign2user.add =call assign_class (?,?)
class.assign2user.add2 =call assign_class2 (?,?)
###insert into study_class(classid,userid,applydate,status) values(?,?,?,?)
class.assign2user.delete= delete from study_class where classid = ? and userid = ?
class.assign2user.course.byclid = select courseid ,status from class_course where classid = ?
class.canassgin.deps = select dep.id,dep.name from department dep ,department fdep where dep.id not in(select ca.depid from class_assign ca where ca.classid = ?)\
			and dep.id =fdep.id  and fdep.lid>=? and fdep.rid<=?
class.assigned.deps= select dep.id,dep.name from department dep ,department fdep where dep.id  in(select ca.depid from class_assign ca where ca.classid = ?)\
			and dep.id =fdep.id  and fdep.lid>=? and fdep.rid<=?
class.assign2dep.add = insert into class_assign(classid,depid,assigntime) values(?,?,?)
class.assign2dep.delete= delete from class_assign where classid = ? and depid = ?
###class.apply.this= select * from (select t.*, rownum rn from (select cl.id,cl.name clname,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name cltname  \
###			 from elclass cl,elclasstype clt,eluser eu,department dep where cl.cltype = clt.id \
###			 and eu.depid= dep.id and eu.id = cl.creater and cl.name like ?  \
###			 and eu.depid=dep.id and cl.status = ? and dep.lid >=? and dep.rid<=? \
###		 and cl.id not in(select ca.classid from study_class ca where ca.userid = ?) order by cl.createtime desc)t where rownum <= ? ) where rn>=?
class.apply.this= select * from (select t.*, rownum rn from (select cl.id,cl.name clname,cl.certificatename,cl.cltype ,cl.optionalcredit,cl.status,clt.name cltname  \
			 from elclass cl,elclasstype clt,eluser eu,department dep where cl.cltype = clt.id \
			 and eu.depid= dep.id and eu.id = cl.creater and cl.name like ?  \
			 and eu.depid=dep.id and cl.status = ? and dep.lid >=? and dep.rid<=? \
			order by cl.createtime desc)t where rownum <= ? ) where rn>=?
class.apply.select=select classid from study_class sc  where sc.userid = ?
class.apply.already=select classid from study_class where userid= ? 
class.apply=insert into study_class(classid,userid,applydate,status) values(?,?,?,?)
class.applyed = select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename ,eu.id euid,eu.realname from study_class ca ,eluser eu,elclass cl,department dep where ca.userid = eu.id and ca.classid = cl.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and ca.status = ? order by ca.applydate desc)t where rownum <= ? ) where rn>=?
class.graduate.apply.list = select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename ,eu.id euid,eu.realname from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.status=1 and sc.userid = eu.id and cl.creater = ? order by sc.applydate desc) t where rownum <= ? ) where rn>=?
class.graduate.apply.list.size =select count(*) from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.userid = eu.id and sc.status=1 and cl.creater = ?

###class.my.study = select cl.id,cl.name,cl.certificatename, \
###		 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?) ,\
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?), \
###			 (select count(stu.userid) from study_class stu where cl.id=stu.classid ) ,ca.applydate,cl.optionalcredit \
###			 from elclass cl,study_class ca where cl.id = ca.classid and ca.userid = ? and ca.status = ? limit ?,?
#class.byid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,eu.id,eu.realname,cl.global,cl.group1 ,cl.group2,cl.diplomatime  from elclass cl,elclasstype clt,eluser eu where eu.id = cl.creater and cl.cltype = clt.id and cl.id = ?
class.byid=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,eu.id,eu.realname,cl.global,cl.group1 ,cl.group2,cl.diplomatime,cl.classtype  from elclass cl,elclasstype clt,eluser eu where eu.id = cl.creater and cl.cltype = clt.id and cl.id = ?
class.byname=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,eu.id,eu.realname,cl.global,cl.group1 ,cl.group2,cl.diplomatime,cl.classtype  from elclass cl,elclasstype clt,eluser eu where eu.id = cl.creater and cl.cltype = clt.id and cl.name = ?
class.byid_cisco=select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,eu.id,eu.realname,cl.global,cl.group1 ,cl.group2,cl.diplomatime,cl.classtype,sc.applydate  from elclass cl,elclasstype clt,eluser eu,study_class sc where eu.id = cl.creater and cl.cltype = clt.id and cl.id = ? and sc.userid=?
class.apply.status.set= update study_class set status = ? where userid = ? and classid =?
class.apply.status.set.no= delete from  study_class  where classid = ? and  userid=?
class.status.set= update elclass set status = ? where id =?
class.delete.apply.list = select *   from (select t.*, rownum rn  from (select   cl.id, cl.name, cl.certificatename, cl.cltype,  cl.optionalcredit, cl.status, clt.name cltname  from elclass cl, elclasstype clt, eluser u, department dep  where cl.cltype = clt.id and cl.creater = u.id	 and u.depid = dep.id  and cl.status = ?  and cl.name like ?  and dep.lid >= ?\
                     and dep.rid <= ?  and clt.lid >= ?  and clt.rid <= ?  order by cl.createtime desc) t  where rownum <= ?)  where rn >= ?
class.delete.apply.listSize = select count(*) from elclass cl, elclasstype clt, eluser u, department dep  where cl.cltype = clt.id and cl.creater = u.id  and u.depid = dep.id and cl.status = ?  and cl.name like ?  and dep.lid >= ?  and dep.rid <= ? and clt.lid >= ?  and clt.rid <= ?
###class.delete.apply.list = select cl.id,cl.name,cl.certificatename, \
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?) ,\
###			 (select count(bx.courseid) from class_course bx where cl.id=bx.classid and bx.status = ? ) ,\
###			 (select sum(xx.credit) from class_course xx where cl.id=xx.classid and xx.status =  ?), \
###			 (select count(stu.userid) from study_class stu where cl.id=stu.classid ) ,cl.optionalcredit,eu.id,eu.realname \
###			 from elclass cl,eluser eu, department dep  where cl.creater = eu.id and eu.depid = dep.id and dep.lid>=? and dep.rid <=? and cl.status = ?  limit ?,?
####sql for class operate #######

class.stat.classlist= select *   from (select t.*, rownum rn  from (select   cl.id, cl.name, cl.certificatename, cl.cltype,  cl.optionalcredit, cl.status, clt.name cltname, u.id userid, u.realname,cl.createtime  from elclass cl, elclasstype clt, eluser u, department dep  where cl.cltype = clt.id and cl.creater = u.id	 and u.depid = dep.id  and (cl.status = ? or cl.status=?)  and cl.name like ?  and dep.lid >= ?\
                     and dep.rid <= ?  and clt.lid >= ?  and clt.rid <= ?  order by cl.createtime desc) t  where rownum <= ?)  where rn >= ?
class.stat.classlistSize=select count(*) from elclass cl, elclasstype clt, eluser u, department dep  where cl.cltype = clt.id and cl.creater = u.id  and u.depid = dep.id and  (cl.status = ? or cl.status=?) and cl.name like ?  and dep.lid >= ?  and dep.rid <= ? and clt.lid >= ?  and clt.rid <= ?
class.course.credit=update class_course c set c.suggestcredit = ? , c.setcredit=? , c.getcredit=? where c.classid=? and c.courseid =?
