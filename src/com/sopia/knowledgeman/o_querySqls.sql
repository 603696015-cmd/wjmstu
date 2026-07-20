##kltype.query.bydepid=select klt.id,klt.name from kltype_dep kd left join knowledgetype klt on kd.kltypeid = klt.id where kd.depid = ?

kltype.query.bydepid=select klt.id,klt.name from kltype_dep kd left join knowledgetype klt on kd.kltypeid = klt.id where kd.depid = ?




##kltype.query.byid=select kl.id, kl.name,kl.description , kl.parentid,klp.name,kl.manager,eu.realname,kl.isshared from knowledgetype kl left join knowledgetype klp  on kl.parentid = klp.id left join eluser eu on eu.id = kl.manager where kl.id = ?
kltype.query.byid=select kl.id, kl.name,kl.description , kl.parentid,klp.name,kl.manager,eu.realname,kl.isshared,kl.lid,kl.rid from knowledgetype kl left join knowledgetype klp  on kl.parentid = klp.id left join eluser eu on eu.id = kl.manager where kl.id = ?


knowledge.my.list = select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ? order by kl.createtime) t where rownum <= ? ) where rn>=?
knowledge.my.list.size = select  count(*) from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ?

knowledge.list.bydep.sub= select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid ,dep.name depname,kl.readtime ,kl.hot  from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and kl.valid=1 order by kl.createtime desc ) t where rownum <= ? ) where rn>=?
knowledge.list.bydep=  select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid,dep.name depname,kl.readtime,kl.hot from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ?  and kl.valid=1 order by kl.createtime desc) t where rownum <= ? ) where rn>=?
knowledge.list.bydept.sub= select * from (select t.*, rownum rn from( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid,dep.name depname,kl.readtime ,kl.hot from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and klt.id = ? and kl.valid=1 order by kl.createtime  desc) t where rownum <= ? ) where rn>=?
knowledge.list.bydept= select * from (select t.*, rownum rn from(select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid,dep.name depname,kl.readtime ,kl.hot from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ? and klt.id = ? and kl.valid=1  order by kl.createtime  desc ) t where rownum <= ? ) where rn>=?
knowledge.list.bydep.sub.size= select count(*)  from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?   and kl.valid=1
knowledge.list.bydep.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ?    and kl.valid=1
knowledge.list.bydept.sub.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and klt.id = ?   and kl.valid=1
knowledge.list.bydept.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ? and klt.id = ?   and kl.valid=1
knowledge.readtime.set = update knowledge set readtime = readtime+1 where id = ?							
knowledge.hot.set = update knowledge set hot=? where id = ?							
knowledge.list.byhot =  select * from (select t.*, rownum rn from(select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.hot = ? and kl.valid=1 order by kl.createtime )t where rownum <= ? ) where rn>=?  	
knowledge.list.bytitle= select * from (select t.*, rownum rn from(select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.title like ? and kl.valid=1 order by kl.createtime )t where rownum <= ? ) where rn>=?  
knowledge.list.bytitle.size=select count(*) from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.title like ? and kl.valid=1
knowledge.list.bynotype=  select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname, eu.id euid,eu.realname,kl.readtime,kl.mainimg from knowledge kl  left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid  where kl.valid=1 order by kl.createtime desc)t where rownum <= ? ) where rn>=?  
knowledge.list.bynotype.size= select count(*)  from knowledge kl  left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid  where kl.valid=1
knowledge.list.bytype= select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime,kl.mainimg from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.kltypeid = ? and kl.valid=1 order by kl.createtime  desc) t where rownum <= ? ) where rn>=?  
knowledge.list.bytype.size =select count(*) from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.kltypeid = ?  and kl.valid=1

knowledge.byid= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,kl.readtime,kl.userid,eu.realname,kl.mainimg,kl.wendang  from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid where kl.id = ?
knowledge.add=insert into knowledge(title,content,createtime,userid,kltypeid,valid,mainimg,wendang ) values(?,?,?,?,?,?,?,?)
knowledge.alter=update knowledge set title=?,content=?,kltypeid = ?,mainimg=?,wendang=? where id = ?
knowledge.delete = delete from knowledge where id = ?

knowledge.sh.list =  select * from (select t.*, rownum rn from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name,eu.id euid ,eu.realname,dep.id depid,dep.name depname \
					from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id \
					left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid \
					where dep.lid>=? and dep.rid<=? and kl.valid=0 order by kl.createtime desc)t where rownum <= ? ) where rn>=?
knowledge.shm.list = select * from (select t.*, rownum rn from (select  kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname ,eu.id euid,eu.realname,dep.id depid,dep.name depname \
				from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id \
				left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid \
				where klt.manager = ? and kl.valid=0 order by kl.createtime desc)t where rownum <= ? ) where rn>=?