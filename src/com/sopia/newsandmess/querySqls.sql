newstype.query.byparentid = select id,name from newstype where parentid = ?
newstype.query.byid= select n.id,n.name,n.description,np.id,np.name from newstype n left join newstype np on  n.parentid =np.id where  n.id = ? 
newstype.add= insert into newstype(name,description,parentid,lid,rid) values(?,?,?,?,?)
newstype.alter= update newstype set name=?,description=? ,parentid=? where id = ?
newstype.lrid = select id ,lid,rid from newstype where id =?
newstype.delete=delete from newstype where id =?

--news.add = insert into news(title,content,ntid,userid,releasetime,mainimg) values(?,?,?,?,?,?)
news.add = insert into news(title,content,ntid,userid,releasetime,mainimg,status_tow) values(?,?,?,?,?,?,?)
news.query.byid = select n.id, n.title,n.content,n.releasetime,n.ntid ,nt.name,n.mainimg,eu.realname from news n,newstype nt,eluser eu where eu.id = n.userid and nt.id = n.ntid and n.id =?

news.query.byuid=select n.id,n.title,n.releasetime,n.ntid ,nt.name from news n,newstype nt where nt.id = n.ntid and nt.lid>=? and nt.rid <=? and n.userid =? order by n.releasetime desc limit ?,?
news.query.size.byuid=select count(*) from news n,newstype nt where nt.id = n.ntid and nt.lid>=? and nt.rid <=?  and n.userid =? 
news.alter=update news set title = ? , content =? ,ntid=?,mainimg=? where id =?

news.delete = delete from news where id = ?

mess.user.sub.bydepidandos=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.valid=true limit ?,?
mess.user.sub.bydepidandos.size=user.query.subs.size.bydepidandos=select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.valid = true
mess.user.bydepidandos=user.query.bydepidandos=select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id,dep.name,eu.valid  from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.valid = true limit ?,?
mess.user.bydepidandos.size=user.query.size.bydepidandos=select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.valid = true
mess.new.list = select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname from MESSAGE m left join ElUser t on m.mess_from = t.id where m.mess_to = ? and m.recDel=false and m.is_read=false order by mess_time desc limit 0,5
mess.to.list=select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, f.id,f.realname from MESSAGE m left join ElUser f on  m.mess_to = f.id where m.mess_to = ? and m.recDel=false order by mess_time desc, is_read desc limit ?,?
mess.from.list = select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname from MESSAGE m left join ElUser t on t.id=m.mess_to where m.mess_from = ? and m.sendDel= false order by m.mess_time desc, m.is_read desc limit ?,?



	