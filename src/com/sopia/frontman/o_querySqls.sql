front.coures.sub.bytype=select * from(select t.*, rownum rn from (select c.id,c.name,c.description, eu.realname,c.ctypeid,	ct.name ctname,c.createtime,c.mainimg,c.teachername from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=5 and ct.lid>=? and ct.rid <=? order by c.createtime desc)t where rownum <= ? ) where rn>=?
front.coures.bytype = select * from (select t.*, rownum rn from( select c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername  from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=5 and ct.id=? order by c.createtime desc)t where rownum <= ? ) where rn>=?
 

front.coures.byhot = select * from (select t.*, rownum rn from( select c.id,c.name,c.description,\
			eu.realname,c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=5 and c.hot=? order by c.createtime desc  )t where rownum <= ? ) where rn>=?
front.coures.byname= select * from (select t.*, rownum rn from(select c.id,c.name,c.description,eu.realname, c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername,c.kj_appendix, c.jy_appendix \
 			from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and (c.name like ? or c.description like ?) order by c.createtime desc ) t where rownum <= ? ) where rn>=? 
front.coures.byname.size=select count(*) from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and (c.name like ? or c.description like ?)

front.coures.sub.size.bytype=select count(*) from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and ct.lid>=? and ct.rid <=?  
front.coures.size.bytype = select count(*) from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and ct.id=?  
front.coures.ph= select * from (select t.*, rownum rn from(select c.id,c.name,count(ca.courseid) as cstu   from course c,study_course ca  where c.status=1 and ca.courseid = c.id group by c.id,c.name order by count(ca.courseid)  desc)t where rownum <= ? ) where rn>=?
front.ctype.lrid = select id ,lid,rid from course_type where id = ?
front.user.ph = select * from (select t.*, rownum rn from (select t.*, rownum rn from( select eu.id,eu.realname,eu.username,(select sum(c.credit) from study_course sc,course c where sc.courseid=c.id and sc.userid = eu.id and sc.passed=1) as stuc from eluser eu where (eu.role!=1 and eu.role!=2 ) order by stuc)t where rownum <= ? ) where rn>=?
front.dep.ph =  select * from (select t.*, rownum rn from (select dep.id,dep.name,sum(c.credit) stucredit from department dep  left join eluser eu on  eu.depid = dep.id \
			left join (select * from study_course where  passed=1) sc on eu.id = sc.userid  left join course c on  c.id = sc.courseid  where dep.parentid!=0 group by dep.id,dep.name order by sum(c.credit) desc)t where rownum <= ? ) where rn>=? 
front.news.sub.bytid= select * from (select t.*, rownum rn from( select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where  n.title like ? and  nt.lid>=? and nt.rid<=? and nt.id = n.ntid and n.hot = 2 and n.status=3 and eu.id = n.userid order by n.releasetime desc)t where rownum <= ? ) where rn>=?
front.news.sub.bytidhot= select * from (select t.*, rownum rn from( select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where  n.hot= ? and   nt.lid>=? and nt.rid<=? and nt.id = n.ntid and eu.id = n.userid order by n.releasetime desc)t where rownum <= ? ) where rn>=?
front.news.bytid = select * from (select t.*, rownum rn from ( select n.id,n.title,n.mainimg,eu.realname,  nt.name,n.releasetime,n.content ,  from news n,newstype nt,eluser eu where n.title like ? and  nt.id=? and nt.id = n.ntid and eu.id = n.userid order by n.releasetime desc)t where rownum <= ? ) where rn>=?
front.news.sub.size.bytid= select count(*) from news n,newstype nt,eluser eu where  n.title like ? and  nt.lid>=? and nt.rid<=? and nt.id = n.ntid and eu.id = n.userid
front.news.size.bytid = select count(*) from news n,newstype nt,eluser eu where  n.title like ? and  nt.id=? and nt.id = n.ntid and eu.id = n.userid
front.ntype.lrid = select id,lid,rid from newstype where id = ?

front.knowledge.zx = select * from (select t.*, rownum rn from ( select kl.id,kl.title,kl.content from knowledge kl order by kl.createtime desc )t where rownum <= ? ) where rn>=?
front.knowledge.byhot= select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.mainimg  from knowledge kl, knowledgetype klt  where kl.kltypeid = klt.id and  kl.hot = ? and kl.valid=1 order by kl.createtime desc)t where rownum <= ? ) where rn>=? 
knowledge.list.byreadtime = select * from (select t.*, rownum rn from (select kl.id,kl.title from knowledge kl, knowledgetype klt  where kl.kltypeid = klt.id and kl.valid=1 order by kl.readtime desc,kl.createtime desc)t where rownum <= ? ) where rn>=? 
front.knowledge.bytype = select * from  (select t.*, rownum rn from ( select kl.id,kl.title,kl.kltypeid,kl.mainimg,kl.wendang,kl.createtime from knowledge kl  where kl.kltypeid = ? order by kl.createtime desc)t where rownum <= ? ) where rn>=?

front.stuff.list.bytitle =select * from (select qs.id,qs.title,qs.description,qs.fileExt,qs.modifytime,qs.createtime,qs.length ,qs.type,eu.id euid,eu.realname from question_stuff qs,eluser eu where eu.id = qs.onwer and qs.title like ? order by qs.createtime desc )t where rownum <= ? ) where rn>=? 
front.stuff.list.bytitle.size =select count(*) from question_stuff qs,eluser eu where eu.id = qs.onwer and qs.title like ? 

front.class.byname = select * from (select t.*, rownum rn from ( select cl.id,cl.name,cl.description,cl.mainimg,cl.creater,eu.realname,cl.createtime, rownum from elclass cl,eluser eu where eu.id = cl.creater and cl.name like ? order by cl.createtime desc)t where rownum <= ? ) where rn>=?  
front.class.byname.size = select count(*) from elclass cl,eluser eu where eu.id = cl.creater and cl.name like ? 

front.cltype.lrid = select id,lid,rid from elclasstype where id = ?
front.class.bytid = select * from (select t.*, rownum rn from ( select cl.id,cl.name,cl.description,cl.mainimg,cl.creater,eu.realname,cl.createtime from elclass cl,eluser eu,elclasstype clt where cl.cltype = clt.id and  eu.id = cl.creater and clt.lid>=? and clt.rid<=? order by cl.createtime desc)t where rownum <= ? ) where rn>=?  
front.class.bytid.size = select count(*) from elclass cl,eluser eu,elclasstype clt where cl.cltype = clt.id and eu.id = cl.creater and clt.lid>=? and clt.rid <=?
front.class.user.check = select * from class_apply where classid = ? and userid = ?

front.knowledge.bytype_limit1=select id,title,mainimg,wendang,content from knowledge where kltypeid = ? and hot = 1 and rownum<=1 order by createtime 
front.coures.byhot_limit1=select c.id,c.name,c.description,eu.realname,c.ctypeid, ct.name ctname,c.createtime,c.mainimg,c.teachername  from course c,eluser eu,course_type ct where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and ct.id=? and c.hot = 1 and rownum<=1 order by c.createtime desc

front.news.list.byhot = select * from (select t.*, rownum rn from ( select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where  n.hot =? and n.status=3  and   nt.lid>=? and nt.rid<=? and nt.id = n.ntid and eu.id = n.userid order by n.releasetime desc )t where rownum <= ? ) where rn>=?  