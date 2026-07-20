booktype.query.byparentid = select id,name from booktype where parentid = ?
booktype.query.byid= select n.id,n.name,n.description,np.id,np.name from booktype n left join booktype np on  n.parentid =np.id where  n.id = ? 
booktype.add= insert into booktype(name,description,parentid,lid,rid) values(?,?,?,?,?)
booktype.alter= update booktype set name=?,description=? ,parentid=? where id = ?
booktype.lrid = select id ,lid,rid from booktype where id =?
booktype.delete=delete from booktype where id =?
book.add = insert into book(title,content,btid,userid,releasetime,mainimg,pubhouse,writer,pubtime) values(?,?,?,?,?,?,?,?,?)
book.query.byid = select n.id, n.title,n.content,n.releasetime,n.btid ,nt.name,n.mainimg,eu.realname,n.pubhouse,n.writer,n.pubtime from book n,booktype nt,eluser eu where eu.id = n.userid and nt.id = n.btid and n.id =?
book.query.byuid=select * from (select n.id,n.title,n.releasetime,n.btid ,nt.name,n.pubhouse,n.writer,n.pubtime,row_number() over( order by n.releasetime desc) rownum from book n,booktype nt where nt.id = n.btid and nt.lid>=? and nt.rid <=? and n.userid =?) t where t.rownum between ? and ?
book.query.size.byuid=select count(*) from book n,booktype nt where nt.id = n.btid and nt.lid>=? and nt.rid <=?  and n.userid =? 
book.alter=update book set title = ? , content =? ,btid=?,mainimg=?, pubhouse=?, writer=?, pubtime=?  where id =?
book.delete = delete from book where id = ?
book.dep.list= select * from (select n.id,n.title,n.releasetime,n.btid ,nt.name,n.pubhouse,n.writer,n.pubtime,eu.id euid,eu.realname,hot,row_number() over( order by n.releasetime desc) rownum \
							from book n,booktype nt,eluser eu,department dep where nt.id = n.btid and eu.id = n.userid and dep.id = eu.depid and dep.lid>=? and dep.rid <=?) t where t.rownum between ? and ? 