
newstype.query.byparentid = select id,name from newstype where parentid = ? order by id
newstype.query.byid= select n.id,n.name,n.description,np.id,np.name,n.isshared  from newstype n left join newstype np on  n.parentid =np.id where  n.id = ? 
newstype.add= insert into newstype(name,description,parentid,lid,rid,isshared) values(?,?,?,?,?,?)
newstype.alter= update newstype set name=?,description=? ,parentid=?,isshared=?  where id = ?
newstype.lrid = select id ,lid,rid from newstype where id =?
newstype.delete=delete from newstype where id =?

news.add = insert into news(title,content,ntid,userid,releasetime,mainimg,hot,status) values(?,?,?,?,?,?,?,?)
news.query.byid = select n.id, n.title,n.content,n.releasetime,n.ntid ,nt.name,n.mainimg,eu.realname,n.hot from news n,newstype nt,eluser eu where eu.id = n.userid and nt.id = n.ntid and n.id =?

news.query.byuid=select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime,n.ntid ,nt.name,n.hot  from news n,newstype nt where nt.id = n.ntid and nt.lid>=? and nt.rid <=? and n.userid =? order by n.releasetime desc) t where rownum <=?)where rn>=?
news.query.size.byuid=select count(*) from news n,newstype nt where nt.id = n.ntid and nt.lid>=? and nt.rid <=?  and n.userid =? 
--news.alter=update news set title = ? , content =? ,ntid=?,mainimg=?,hot  = ? where id =?
news.alter=update news set title = ? , content =? ,ntid=?,mainimg=?,hot  = ?,releasetime=? where id =?

news.delete = delete from news where id = ?

mess.user.sub.bydepidandos= select * from (select t.*, rownum rn from (select eu.id,eu.username, eu.realname,eu.role, dep.id depid,dep.name depname,eu.valid from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=? and eu.valid=1 order by n.releasetime desc )t where rownum <= ? ) where rn>=?
mess.user.sub.bydepidandos.size=user.query.subs.size.bydepidandos=select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=? and eu.valid = 1
mess.user.bydepidandos=user.query.bydepidandos= select * from (select t.*, rownum rn from (select eu.id,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and dep.id=? and eu.valid = 1 order by n.releasetime desc )t where rownum <= ? ) where rn>=?
mess.user.bydepidandos.size=user.query.size.bydepidandos=select count(*) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and dep.id=? and eu.valid = 1
mess.new.list = select t.*,rownum rn from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname  from MESSAGE m left join ElUser t on m.mess_from = t.id where m.mess_to = ? and m.recDel=0 and m.is_read=0 order by mess_time desc) t where rownum<=5
mess.to.list=select * from (select t.*,rownum rn from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, f.id,f.realname from MESSAGE m left join ElUser f on  m.mess_from = f.id where m.mess_to = ? and m.recDel=0 order by m.mess_time desc, m.is_read desc) t where rownum <=?)where rn>=?
mess.from.list=select * from (select t.*,rownum rn from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname from MESSAGE m left join ElUser t on t.id=m.mess_to where m.mess_from = ? and m.sendDel= 0 order by m.mess_time desc, m.is_read desc) t where rownum <=?)where rn>=?


