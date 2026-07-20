word.query.bypid =select id,name,parentid,lid,rid from words where parentid=? and status!=1 order by id

wordslib.add=insert into words( name, description, parentid,lid,rid,courseid ) values(?,?,?,?,?,?)

wordslib.alter=update words set name = ? , description =?,parentid =? ,courseid = ? where id= ?
