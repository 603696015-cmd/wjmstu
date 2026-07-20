front.coures.sub.bytype=select c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name,c.createtime,c.mainimg,c.teachername  from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and ct.lid>=? and ct.rid <=? order by c.createtime desc limit ?,?

front.coures.bytype = select c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name,c.createtime,c.mainimg,c.teachername   from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and ct.id=? order by c.createtime desc limit ?,?
front.coures.byhot =  select c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name,c.createtime,c.mainimg,c.teachername   from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and c.hot=? order by c.createtime desc limit ?,?
front.coures.byname= select c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name,c.createtime,c.mainimg,c.teachername,c.kj_appendix,c.jy_appendix   from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and (c.name like ? or c.description like ?) order by c.createtime desc limit ?,?
front.coures.byname.size=select count(*) from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and (c.name like ? or c.description like ?)

front.coures.sub.size.bytype=select count(*) from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and ct.lid>=? and ct.rid <=?  
front.coures.size.bytype = select count(*) from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and ct.id=?  
front.coures.ph=select c.id,c.name,count(ca.courseid) as cstu from course c,study_course ca  where c.status=1 and ca.courseid = c.id group by ca.courseid order by cstu desc limit ?,?
front.ctype.lrid = select id ,lid,rid from course_type where id = ?
#####front.ctype.list = select id,name from course_type limit ?,?
##front.user.ph = select eu.id,eu.realname,eu.username,sum(c.credit) as stuc from study_course sc ,eluser eu,course c where eu.id = sc.userid and c.id = sc.courseid and sc.passed=true group by sc.userid order by stuc desc limit ?,?
front.user.ph = select eu.id,eu.realname,eu.username,(select sum(c.credit) from study_course sc,course c where sc.courseid=c.id and sc.userid = eu.id and sc.passed=true) as stuc from eluser eu where (eu.role!=1&&eu.role!=2) order by stuc desc limit ?,?
front.dep.ph = select dep.id,dep.name,(select sum(c.credit) from study_course sc ,eluser eu, course c where eu.id = sc.userid and c.id = sc.courseid and sc.passed= true and eu.depid = dep.id) as stucredit from department dep where dep.parentid!=0 order by stucredit desc limit ?,? 
front.news.sub.bytid= select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where nt.lid>=? and nt.rid<=? and nt.id = n.ntid and eu.id = n.userid  order by n.releasetime desc limit ?,?
front.news.bytid = select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where nt.id=? and nt.id = n.ntid and eu.id = n.userid  order by n.releasetime desc limit ?,?
front.news.sub.size.bytid= select count(*) from news n,newstype nt,eluser eu where nt.lid>=? and nt.rid<=? and nt.id = n.ntid and eu.id = n.userid
front.news.size.bytid = select count(*) from news n,newstype nt,eluser eu where nt.id=? and nt.id = n.ntid and eu.id = n.userid
front.ntype.lrid = select id,lid,rid from newstype where id = ?

front.knowledge.zx = select kl.id,kl.title from knowledge kl order by kl.createtime desc limit ?,?
front.knowledge.byhot=select kl.id,kl.title from knowledge kl, knowledgetype klt  where kl.kltypeid = klt.id and  kl.hot = ? and kl.valid=true order by createtime desc limit ?, ?
knowledge.list.byreadtime =select kl.id,kl.title from knowledge kl, knowledgetype klt  where kl.kltypeid = klt.id and kl.valid=true order by readtime desc,createtime desc limit ?, ?

front.stuff.list.bytitle =select qs.id,qs.title,qs.description,qs.fileExt,qs.modifytime,qs.createtime,qs.length ,qs.type,eu.id,eu.realname from question_stuff qs,eluser eu where eu.id = qs.onwer and qs.title like ? order by qs.createtime desc limit ?,?
front.stuff.list.bytitle.size =select count(*) from question_stuff qs,eluser eu where eu.id = qs.onwer and qs.title like ? 

front.class.byname = select cl.id,cl.name,cl.description,cl.mainimg,cl.creater,eu.realname,cl.createtime from elclass cl,eluser eu where eu.id = cl.creater and cl.name like ? order by cl.createtime limit ?,?
front.class.byname.size = select count(*) from elclass cl,eluser eu where eu.id = cl.creater and cl.name like ? 

front.cltype.lrid = select id,lid,rid from elclasstype where id = ?
front.class.bytid = select cl.id,cl.name,cl.description,cl.mainimg,cl.creater,eu.realname,cl.createtime from elclass cl,eluser eu,elclasstype clt where cl.cltype = clt.id and  eu.id = cl.creater and clt.lid>=? and clt.rid<=? order by cl.createtime limit ?,?
front.class.bytid.size = select count(*) from elclass cl,eluser eu,elclasstype clt where cl.cltype = clt.id and eu.id = cl.creater and clt.lid>=? and clt.rid <=?
front.class.user.check = select * from class_apply where classid = ? and userid = ?


