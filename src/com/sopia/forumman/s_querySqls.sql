forum.fbtype.list = select id,name,description,sortid from forumblocktype order by sortid asc
forum.fbtype.add = insert into forumblocktype(name,description,sortid) values(?,?,?)
forum.fbtype.maxsortid= select max(sortid) from forumblocktype
forum.fbtype.byid = select id,name,description,sortid from forumblocktype where id = ?
forum.fbtype.alter = update forumblocktype set name=?,description=? where id = ?
forum.fbtype.delete = delete from forumblocktype where id= ?
forum.fbtype.delete.sort = update forumblocktype set sortid=sortid-1 where sortid>?



forum.fblock.byfbtid = select fb.id,fb.title,fb.description,fb.manager,eu.realname,fb.sortid from forumblock fb ,eluser eu where eu.id = fb.manager and fb.fbtid = ? order by fb.sortid asc
forum.fblock.maxsortid.byfbtid= select max(sortid) from forumblock where fbtid = ?
forum.fblock.add = insert into forumblock(title,description,fbtid,manager,isshared,sortid)values(?,?,?,?,?,?)
forum.fblock.byid = select fb.id,fb.title,fb.description,fb.manager,eu.realname,fb.sortid,fb.fbtid,fbt.name from forumblock fb ,eluser eu,forumblocktype fbt where fbt.id=fb.fbtid and eu.id = fb.manager and fb.id=?
forum.fblock.alter= update forumblock set title = ?,description=?,fbtid= ?,manager = ?, isshared = ? where id = ?


forum.list.bybid = select * from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.createtime) rownum from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=? and  fm.valid=1  )t where t.rownum between ? and ?
forum.list.bybid.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=? and  fm.valid=1
forum.list.byjh.bybid =  select * from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.createtime) rownum  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=?  and fm.hot=? and  fm.valid=1 )t where t.rownum between ? and ?
forum.list.byrm.bybid= select * from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.createtime) rownum  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=? and  fm.valid=1 )t where t.rownum between ? and ?
forum.list.byjh = select * from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.createtime) rownum  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.hot=? and  fm.valid=1 )t where t.rownum between ? and ?
forum.list.byzx= select * from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.createtime) rownum  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1  )t where t.rownum between ? and ?
forum.list.byrm= select * from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.receipttime) rownum  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id  and fm.valid=1 )t where t.rownum between ? and ?
forum.add = insert into forum (title,description,createtime,creater,fblockid,valid) values(?,?,?,?,?,?)
forum.alter =update forum set title=?,description=?,fblockid= ? where id = ?
forum.byid= select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.id=? 
forum.topic.byfid =select * from ( select tp.id,tp.content,tp.createtime,tp.creater,eu.realname,row_number() over(order by tp.createtime desc ) rownum  from ftopic tp,eluser eu where eu.id = tp.creater and tp.forumid = ?  )t where t.rownum between ? and ?
forum.topic.byfid.size = select count(*) from ftopic tp,eluser eu where eu.id = tp.creater and tp.forumid = ? 
forum.topic.add = insert into ftopic(content, createtime,creater,forumid) values(?,?,?,?)

forum.list.bymanager =select * from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.createtime) rownum from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.fblockid = ? and bl.manager= ? and fm.title like ?  )t where t.rownum between ? and ?
forum.list.bymanager.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.fblockid = ? and bl.manager= ? and fm.title like ? 
forum.list.bymanager.all =select * from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over (order by fm.createtime) rownum from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ?  )t where t.rownum between ? and ?
forum.list.bymanager.all.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ? 
forum.list.byuid =select * from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime ,row_number() over (order by fm.createtime) rownum from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.creater= ? and fm.title like ?    )t where t.rownum between ? and ?
forum.list.byuid.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.creater= ? and fm.title like ? 
forum.hot.set = update forum set hot = ? where id = ?
forum.delete = delete from forum where  id = ?
forum.receipttime.add= update forum set receipttime =receipttime+1 where id = ?
forum.readtime.add= update forum set readtime =readtime+1 where id =?
forum.sh.list=select * from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,row_number() over(order by fm.createtime desc) rownum from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager=? and fm.valid=0)t where t.rownum between ? and ?
