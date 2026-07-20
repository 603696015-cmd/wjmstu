############sql for cmsLabel operate###############
cmsLabel.add=insert into cms_label(lb_id,lb_name,lb_code,lb_style,lb_modelId,lb_modelType,lb_viewType,lb_record,lb_contentType,lb_titleLength,lb_row,lb_contentLength,lb_remark) values(cmsLabel_SEQUENCE.nextval,?,?,?,?,?,?,?,?,?,?,?,?)
cmsLabel.update=update cms_label set lb_name=?,lb_code=?,lb_style=?,lb_modelId=?,lb_modelType=?,lb_viewType=?,lb_record=?,lb_contentType=?,lb_titleLength=?,lb_row=?,lb_contentLength=?,lb_remark=? where lb_id=?
cmsLabel.delete=delete from cms_label where lb_id=?
cmsLabel.query.byId=select lb_id,lb_name,lb_code,lb_style,lb_modelId,lb_modelType,lb_viewType,lb_record,lb_contentType,lb_titleLength,lb_row,lb_contentLength,lb_remark from cms_label where lb_id=?
cmsLabel.query.likePager=select rownum rn,lb_id,lb_name,lb_code,lb_style,lb_modelId,lb_modelType,lb_viewType,lb_record,lb_contentType,lb_titleLength,lb_row,lb_contentLength,lb_remark from cms_label where (lb_name like ? or lb_code like ? )  and rn>=? and rn<=?  
cmsLabel.query.all=select lb_id,lb_name,lb_code,lb_style,lb_modelId,lb_modelType,lb_viewType,lb_record,lb_contentType,lb_titleLength,lb_row,lb_contentLength,lb_remark from cms_label 


############sql for cmsTemplate operate####,tmp_type=?,tmp_type_id=?###########
cmsTemplate.add=insert into cms_template(tmp_id,tmp_name,tmp_jspTmp,tmp_jsp,tmp_remark,tmp_type,tmp_type_id) values(cmsTmp_SEQUENCE.nextval,?,?,?,?,?,?)
cmsTemplate.update=update cms_template set tmp_name=?,tmp_remark=?  where tmp_id=?
cmsTemplate.delete=delete from cms_template where tmp_id=?
cmsTemplate.query.byId=select tmp_id,tmp_name,tmp_jspTmp,tmp_jsp,tmp_remark,tmp_type,tmp_type_id from cms_template where tmp_id=?
cmsTemplate.query.likePager=select rownum rn,tmp_id,tmp_name,tmp_jspTmp,tmp_jsp,tmp_remark  from cms_template where tmp_name like ? and rn>=? and rn<=?  
cmsTemplate.query.all=select tmp_id,tmp_name,tmp_jspTmp,tmp_jsp,tmp_remark,tmp_type,tmp_type_id  from cms_template

############sql for cmsDate operate###############
cmsNewsType.query.all=select * from newsType
cmsForumBlockType.query.all=select id,name,description,sortid from forumblocktype
cmsForum.query.pager=select rownum rn,*  from forum where rn<=?  