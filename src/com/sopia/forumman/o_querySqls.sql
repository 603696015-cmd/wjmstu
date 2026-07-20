forum.fbtype.list = select id,name,description,sortid from forumblocktype order by sortid asc
forum.fbtype.add = insert into forumblocktype(name,description,sortid) values(?,?,?)
forum.fbtype.maxsortid= select max(sortid) from forumblocktype
forum.fbtype.byid = select id,name,description,sortid from forumblocktype where id = ?
forum.fbtype.alter = update forumblocktype set name=?,description=? where id = ?
forum.fbtype.delete = delete from forumblocktype where id= ?
forum.fbtype.delete.sort = update forumblocktype set sortid=sortid-1 where sortid>?



forum.fblock.byfbtid = select fb.id,fb.title,fb.description,fb.manager,eu.realname,fb.sortid from forumblock fb ,eluser eu where eu.id = fb.manager and fb.fbtid = ? order by fb.sortid asc
forum.fblock.byfbtidandper = select fb.id,fb.title,fb.description,fb.manager,eu.realname,fb.sortid from forumblock fb ,eluser eu where eu.id = fb.manager and fb.fbtid = ? and fb.id in (select fblockid from fblock_use_type where userid = ?) order by fb.sortid asc
forum.fblock.byfbtidandperorsha = select   fb.id, fb.title, fb.description, fb.manager, eu.realname, fb.sortid from forumblock fb left join eluser eu on eu.id = fb.manager where fb.fbtid = ? and (fb.id in (select fblockid from fblock_use_type where userid = ? ) or fb.isshared = 1) order by fb.sortid asc
forum.fblock.maxsortid.byfbtid= select max(sortid) from forumblock where fbtid = ?
forum.fblock.add = insert into forumblock(title,description,fbtid,manager,isshared,sortid,luntanjibies)values(?,?,?,?,?,?,?)
forum.fblock.byid = select fb.id,fb.title,fb.description,fb.manager,eu.realname,fb.sortid,fb.fbtid,fbt.name,fb.isshared,fb.luntanjibies from forumblock fb ,eluser eu,forumblocktype fbt where fbt.id=fb.fbtid and eu.id = fb.manager and fb.id=?
forum.fblock.alter= update forumblock set title = ?,description=?,fbtid= ?,manager = ?, isshared = ?,luntanjibies=? where id = ?


forum.list.bybid =  select * from (select t.*, rownum rn from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=? and  fm.valid=1 order by fm.createtime)t where rownum <= ? ) where rn>=?
forum.list.bybid.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=? and  fm.valid=1
forum.list.byjh.bybid = select * from (select t.*, rownum rn from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=?  and fm.hot=? and  fm.valid=1 order by fm.createtime)t where rownum <= ? ) where rn>=?
forum.list.byrm.bybid=  select * from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=? and  fm.valid=1 order by fm.createtime)t where rownum <= ? ) where rn>=?
forum.list.byjh = select * from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.hot=? and  fm.valid=1 order by fm.createtime)t where rownum <= ? ) where rn>=?
forum.list.byzx=  select * from (select t.*, rownum rn from  ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1 order by fm.createtime desc)t where rownum <= ? ) where rn>=?
forum.list.byrm=  select * from (select t.*, rownum rn from  (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id  and fm.valid=1 order by fm.receipttime)t where rownum <= ? ) where rn>=?
forum.add = insert into forum (title,description,createtime,creater,fblockid,valid) values(?,empty_clob(),?,?,?,?)
forum.alter =update forum set title=?,description=?,fblockid= ? where id = ?
forum.byid= select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime,eu.depid from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.id=? 
forum.topic.byfid = select * from (select t.*, rownum rn from ( select tp.id,tp.content,tp.createtime,tp.creater,eu.realname from ftopic tp,eluser eu where eu.id = tp.creater and tp.forumid = ? order by tp.createtime desc  )t where rownum <= ? ) where rn>=?
forum.topic.byfid.size = select count(*) from ftopic tp,eluser eu where eu.id = tp.creater and tp.forumid = ? 
forum.topic.add = insert into ftopic(content, createtime,creater,forumid) values(?,?,?,?)

forum.list.bymanager = select * from (select t.*, rownum rn from  ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.fblockid = ? and bl.manager= ? and fm.title like ? order by fm.createtime )t where rownum <= ? ) where rn>=?
forum.list.bymanager.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.fblockid = ? and bl.manager= ? and fm.title like ?
forum.list.bymanagersha = select * from (select t.*, rownum rn from  ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ? and fm.fblockid in (?) order by fm.createtime )t where rownum <= ? ) where rn>=?
forum.list.bymanagersha.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ? and fm.fblockid in (?)

forum.list.bymanager.all = select * from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ? and fm.fblockid in (select fblockid from fblock_use_type where userid = ? ) order by fm.createtime )t where rownum <= ? ) where rn>=?
forum.list.bymanager.all.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ? and fm.fblockid in (select fblockid from fblock_use_type where userid = ? ) 
forum.list.byuid = select * from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.creater= ? and fm.title like ? order by fm.createtime)t where rownum <= ? ) where rn>=?
forum.list.byuid.size = select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.creater= ? and fm.title like ? 
forum.hot.set = update forum set hot = ? where id = ?
forum.delete = delete from forum where  id = ?
forum.receipttime.add= update forum set receipttime =receipttime+1 where id = ?
forum.readtime.add= update forum set readtime =readtime+1 where id =?
forum.sh.list= select * from (select t.*, rownum rn from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager=? and fm.valid=0 order by fm.createtime desc)t where rownum <= ? ) where rn>=?
