##kltype.query.bydepid=select klt.id,klt.name from kltype_dep kd left join knowledgetype klt on kd.kltypeid = klt.id where kd.depid = ?

kltype.query.bydepid=select klt.id,klt.name from kltype_dep kd left join knowledgetype klt on kd.kltypeid = klt.id where kd.depid = ?




kltype.query.byid=select kl.id, kl.name,kl.description , kl.parentid,klp.name,kl.manager,eu.realname from knowledgetype kl left join knowledgetype klp  on kl.parentid = klp.id left join eluser eu on eu.id = kl.manager where kl.id = ?



knowledge.my.list =select * from ( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name,row_number() over ( order by kl.createtime ) rownum  from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ?) t where t.rownum between ? and ?
knowledge.my.list.size = select  count(*) from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ?

knowledge.list.bydep.sub= select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid ,dep.name depname,kl.readtime ,kl.hot,row_number() over ( order by kl.createtime ) rownum from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and kl.valid=1 ) t where t.rownum between ? and ? 
knowledge.list.bydep= select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid,dep.name depname,kl.readtime,kl.hot,row_number() over ( order by kl.createtime ) rownum from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ?  and kl.valid=1 ) t where t.rownum between ? and ? 
knowledge.list.bydept.sub=select * from ( select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid,dep.name depname,kl.readtime ,kl.hot,row_number() over ( order by kl.createtime ) rownum  from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and klt.id = ? and kl.valid=1  ) t where t.rownum between ? and ?  
knowledge.list.bydept= select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,dep.id depid,dep.name depname,kl.readtime ,kl.hot,row_number() over ( order by kl.createtime ) rownum from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ? and klt.id = ? and kl.valid=1  ) t where t.rownum between ? and ?  
knowledge.list.bydep.sub.size= select count(*)  from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?   and kl.valid=1
knowledge.list.bydep.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ?    and kl.valid=1
knowledge.list.bydept.sub.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and klt.id = ?   and kl.valid=1
knowledge.list.bydept.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ? and klt.id = ?   and kl.valid=1
knowledge.readtime.set = update knowledge set readtime = readtime+1 where id = ?							
knowledge.hot.set = update knowledge set hot=? where id = ?							
knowledge.list.byhot = select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime,row_number() over ( order by kl.createtime ) rownum from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.hot = ? and kl.valid=1 ) t where t.rownum between ? and ?  	
knowledge.list.bytitle=select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime,row_number() over ( order by kl.createtime ) rownum from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.title like ? and kl.valid=1 ) t where t.rownum between ? and ?  
knowledge.list.bytitle.size=select count(*) from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.title like ? and kl.valid=1
knowledge.list.bynotype= select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname, eu.id euid,eu.realname,kl.readtime,kl.mainimg ,row_number() over ( order by kl.createtime ) rownum from knowledge kl  left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid  where kl.valid=1) t where t.rownum between ? and ?  
knowledge.list.bynotype.size= select count(*)  from knowledge kl  left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid  where kl.valid=1
knowledge.list.bytype=select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname,eu.id euid,eu.realname,kl.readtime,kl.mainimg,row_number() over ( order by kl.createtime ) rownum  from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.kltypeid = ? and kl.valid=1 ) t where t.rownum between ? and ?  
knowledge.list.bytype.size =select count(*) from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.kltypeid = ?  and kl.valid=1

knowledge.byid= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,kl.readtime,kl.userid,eu.realname,kl.mainimg,kl.wendang  from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid where kl.id = ?
knowledge.add=insert into knowledge(title,content,createtime,userid,kltypeid,valid,mainimg,wendang ) values(?,?,?,?,?,?,?,?)
knowledge.alter=update knowledge set title=?,content=?,kltypeid = ?,mainimg=?,wendang=? where id = ?
knowledge.delete = delete from knowledge where id = ?

knowledge.sh.list = select * from (select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name,eu.id euid ,eu.realname,dep.id depid,dep.name depname,row_number() over( order by kl.createtime desc) rownum \
					from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id \
					left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid \
					where dep.lid>=? and dep.rid<=? and kl.valid=0 )t where t.rownum between  ? and ?
knowledge.shm.list =	select * from (select  kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id kltid,klt.name kltname ,eu.id euid,eu.realname,dep.id depid,dep.name depname,row_number() over( order by kl.createtime desc) rownum \
				from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id \
				left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid \
				where klt.manager = ? and kl.valid=0)t where t.rownum between ? and ?