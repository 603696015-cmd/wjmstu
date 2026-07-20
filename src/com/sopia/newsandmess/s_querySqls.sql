newstype.query.byparentid = select id,name from newstype where parentid = ?
newstype.query.byid= select n.id,n.name,n.description,np.id,np.name from newstype n left join newstype np on  n.parentid =np.id where  n.id = ? 
newstype.add= insert into newstype(name,description,parentid,lid,rid) values(?,?,?,?,?)
newstype.alter= update newstype set name=?,description=? ,parentid=? where id = ?
newstype.lrid = select id ,lid,rid from newstype where id =?
newstype.delete=delete from newstype where id =?

news.add = insert into news(title,content,ntid,userid,releasetime,mainimg,hot) values(?,?,?,?,?,?,?)
news.query.byid = select n.id, n.title,n.content,n.releasetime,n.ntid ,nt.name,n.mainimg,eu.realname,n.hot from news n,newstype nt,eluser eu where eu.id = n.userid and nt.id = n.ntid and n.id =?

news.query.byuid=select * from (select n.id,n.title,n.releasetime,n.ntid ,nt.name,n.hot,row_number() over(order by n.releasetime desc ) rownum from news n,newstype nt where nt.id = n.ntid and nt.lid>=? and nt.rid <=? and n.userid =? )t where t.rownum  between ? and ?
news.query.size.byuid=select count(*) from news n,newstype nt where nt.id = n.ntid and nt.lid>=? and nt.rid <=?  and n.userid =? 
news.alter=update news set title = ? , content =? ,ntid=?,mainimg=?,hot  = ? where id =?

news.delete = delete from news where id = ?

mess.user.sub.bydepidandos=select * from (select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role, dep.id depid,dep.name depname,eu.valid ,row_number() over(order by n.releasetime desc ) rownum  from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.valid=1 )t where t.rownum between ? and ?
mess.user.sub.bydepidandos.size=user.query.subs.size.bydepidandos=select count(*)from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.lid >=? and dep.rid<=? and eu.valid = 1
mess.user.bydepidandos=user.query.bydepidandos=select * from (select eu.id,eu.username, eu.realname,eu.userno,eu.email,eu.role,dep.id depid,dep.name depname,eu.valid ,row_number() over(order by n.releasetime desc ) rownum from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.valid = 1 )t where t.rownum between ? and ?
mess.user.bydepidandos.size=user.query.size.bydepidandos=select count(*) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id \
			where eu.username like ? and eu.realname like ? and eu.email like ? and dep.id=? and eu.valid = 1
mess.new.list = select * from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname,row_number() over ( order by mess_time desc) rownum from MESSAGE m left join ElUser t on m.mess_from = t.id where m.mess_to = ? and m.recDel=0 and m.is_read=0) t where t.rownum between  0 and 5
mess.to.list=select * from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, f.id,f.realname,row_number() over(order by mess_time desc, is_read desc) rownum from MESSAGE m left join ElUser f on  m.mess_to = f.id where m.mess_to = ? and m.recDel=0  )t where t.rownum between  ? and ?
mess.from.list=select  * from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname,row_number() over (order by m.mess_time desc, m.is_read desc) rownum from MESSAGE m left join ElUser t on t.id=m.mess_to where m.mess_from = ? and m.sendDel= 0) t where t.rownum between  ? and ?


