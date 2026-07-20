kltype.query.bydepid=select klt.id,klt.name from kltype_dep kd left join knowledgetype klt on kd.kltypeid = klt.id where kd.depid = ?



kltype.query.byid=select kl.id, kl.name,kl.description , kl.parentid,klp.name,kl.manager,eu.realname from knowledgetype kl left join knowledgetype klp  on kl.parentid = klp.id left join eluser eu on eu.id = kl.manager where kl.id = ?



knowledge.my.list = select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ? order by kl.createtime limit ?,?
knowledge.my.list.size = select  count(*) from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id where kl.userid = ?

knowledge.list.bydep.sub= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,dep.id,dep.name,kl.readtime ,kl.hot from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and kl.valid=true order by createtime desc limit ?,? 
knowledge.list.bydep= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,dep.id,dep.name,kl.readtime,kl.hot  from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ?  and kl.valid=true order by createtime desc limit ?,? 
knowledge.list.bydept.sub= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,dep.id,dep.name,kl.readtime ,kl.hot from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and klt.id = ? and kl.valid=true order by createtime desc limit ?,? 
knowledge.list.bydept= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,dep.id,dep.name,kl.readtime ,kl.hot from knowledge kl ,knowledgetype klt,department dep ,eluser eu where \
							kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ? and klt.id = ? and kl.valid=true order by createtime desc limit ?,? 
knowledge.list.bydep.sub.size= select count(*)  from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?   and kl.valid=true
knowledge.list.bydep.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ?    and kl.valid=true
knowledge.list.bydept.sub.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.lid >=? and dep.rid<=? and kl.title like ?  and klt.id = ?   and kl.valid=true
knowledge.list.bydept.size= select count(*) from knowledge kl ,knowledgetype klt,department dep ,eluser eu where kl.kltypeid=klt.id and kl.userid = eu.id and eu.depid = dep.id and dep.id=? and kl.title like ? and klt.id = ?   and kl.valid=true
knowledge.readtime.set = update knowledge set readtime = readtime+1 where id = ?							
knowledge.hot.set = update knowledge set hot=? where id = ?							
knowledge.list.byhot = select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,kl.readtime from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.hot = ? and kl.valid=true order by createtime desc limit ?,? 			
knowledge.list.bytitle=select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,kl.readtime from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.title like ? and kl.valid=true order by createtime desc limit ?,?
knowledge.list.bytitle.size=select count(*) from knowledge kl, knowledgetype klt,eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.title like ? and kl.valid=true
knowledge.list.bynotype= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,kl.readtime  from knowledge kl  left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid  where kl.valid=true order by createtime desc limit ?,?
knowledge.list.bynotype.size= select count(*)  from knowledge kl  left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid  where kl.valid=true
knowledge.list.bytype=select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id,eu.realname,kl.readtime  from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.kltypeid = ? and kl.valid=true order by createtime desc limit ?,?
knowledge.list.bytype.size =select count(*) from knowledge kl,knowledgetype klt, eluser eu where kl.kltypeid = klt.id and eu.id = kl.userid and kl.kltypeid = ?  and kl.valid=true

knowledge.byid= select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,kl.readtime,kl.userid,eu.realname  from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id left join eluser eu on eu.id = kl.userid where kl.id = ?
knowledge.add=insert into knowledge(title,content,createtime,userid,kltypeid,valid ) values(?,?,?,?,?,?)
knowledge.alter=update knowledge set title=?,content=?,kltypeid = ? where id = ?
knowledge.delete = delete from knowledge where id = ?
knowledge.sh.list =select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id ,eu.realname,dep.id,dep.name \
					from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id \
					left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid \
					where dep.lid>=? and dep.rid<=? and kl.valid=false order by kl.createtime desc limit ?,?
knowledge.shm.list =select kl.id,kl.title,kl.content,kl.createtime,kl.modifytime,klt.id,klt.name,eu.id ,eu.realname,dep.id,dep.name \
					from knowledge kl left join knowledgetype klt on kl.kltypeid = klt.id \
					left join eluser eu on eu.id = kl.userid left join department dep on dep.id = eu.depid \
					where klt.manager = ? and kl.valid=false order by kl.createtime desc limit ?,?
