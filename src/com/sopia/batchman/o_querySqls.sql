####sql for batch operate #######
batch.add=insert into batch ( name, description) values ( ?,? )
batch.modify=update batch set name = ?,description = ? where id = ?
batch.list=select * from (select t.*, rownum rn from (select id,name,description from batch where name like ? order by id) t where rownum <= ?) where rn >= ?
batch.list.size=select count(*) from batch where name like ?
batch.by.id=select id,name,description from batch where id = ?
batch.del.id=delete from batch where id = ?
batch.class.relation.del=delete from batch_class where batchid=?
batch.elclass=select cl.id, cl.name, cl.certificatename, cl.cltype, cl.optionalcredit, cl.status, clt.name cltname from batch_class bc, elclass cl,elclasstype clt where bc.classid = cl.id and clt.id = cl.cltype and bc.batchid = ?
batch.class.add=insert into batch_class (batchid, classid) values (? ,? )
batch.class.del=delete from batch_class where batchid =? and classid = ?
batch.stat.list=select * from (select t.*, rownum rn from (select   b.id, b.name, b.description from batch b where b.name like ? and b.id in (select distinct(bc.batchid) from batch_class bc) order by b.id) t where rownum <= ?) where rn >= ?
batch.stat.list.size=select count(*) from batch b where b.name like ? and b.id in (select distinct(bc.batchid) from batch_class bc)
batch.class.stat=select cl.id,cl.name,cl.createtime,(select count(*) from study_class ca where ca.classid = cl.id and ca.status = 2),(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  from elclass cl where cl.ID in (select classid from BATCH_CLASS where batchid= ?) order by cl.createtime desc