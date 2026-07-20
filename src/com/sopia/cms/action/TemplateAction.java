package com.sopia.cms.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.cms.IoUtil;
import com.sopia.cms.NewsUtil;
import com.sopia.cms.dao.LabelDao;
import com.sopia.cms.dao.TemplateDao;
import com.sopia.cms.entities.ColumnTemplate;
import com.sopia.cms.entities.Template;
import com.sopia.cms.impl.LabelDaoImpl;
import com.sopia.cms.impl.TemplateDaoImpl;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.CourseType;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.newsandmess.entities.NewsType;
 

public class TemplateAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(TemplateAction.class);
	private Template template;
	private List<Template> templateList;
	private TemplateDao templateDao;
	private LabelDao labelDao;
	private Map<String, String> fileMap;
	private List<NewsType> newsTypeList;
	private List<KnowledgeType> knowledgeTypeList;
	private List<CourseType> courseTypeList;
	private List<ForumBlockType> forumBlockTypeList;
	private List<ColumnTemplate> columnTmpList;
	private ColumnTemplate columnTmp;

	public List<ColumnTemplate> getColumnTmpList() {
		return columnTmpList;
	}

	public void setColumnTmpList(List<ColumnTemplate> columnTmpList) {
		this.columnTmpList = columnTmpList;
	}

	public ColumnTemplate getColumnTmp() {
		return columnTmp;
	}

	public void setColumnTmp(ColumnTemplate columnTmp) {
		this.columnTmp = columnTmp;
	}

	public List<NewsType> getNewsTypeList() {
		return newsTypeList;
	}

	public void setNewsTypeList(List<NewsType> newsTypeList) {
		this.newsTypeList = newsTypeList;
	}

	public List<KnowledgeType> getKnowledgeTypeList() {
		return knowledgeTypeList;
	}

	public void setKnowledgeTypeList(List<KnowledgeType> knowledgeTypeList) {
		this.knowledgeTypeList = knowledgeTypeList;
	}

	public List<CourseType> getCourseTypeList() {
		return courseTypeList;
	}

	public void setCourseTypeList(List<CourseType> courseTypeList) {
		this.courseTypeList = courseTypeList;
	}

	public List<ForumBlockType> getForumBlockTypeList() {
		return forumBlockTypeList;
	}

	public void setForumBlockTypeList(List<ForumBlockType> forumBlockTypeList) {
		this.forumBlockTypeList = forumBlockTypeList;
	}

	public Map<String, String> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, String> fileMap) {
		this.fileMap = fileMap;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public List<Template> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<Template> templateList) {
		this.templateList = templateList;
	}

	public String template_addInit() throws ElException { 
		return "template_add";
	}

	public String template_add() throws ElException {
		String tempJsp=getSession().getServletContext().getRealPath(
		"/template/tmp.jsp");
		String tmpName="Template";
		if("".equals(template.getName())==false){
			tmpName=template.getName();
		}
		template.setJspTmp(getSession().getServletContext().getRealPath(
		"/template")+"/"+tmpName+".jsp"); 
		templateDao = new TemplateDaoImpl();
		templateDao.addTemplate(template);

		IoUtil iu = new IoUtil();
		try {
			iu.copyFile(tempJsp, template.getJspTmp());
			iu.copyFile(template.getJspTmp(), template.getJsp());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "template_list";
	}

	public String template_toJsp() throws ElException {
		templateDao = new TemplateDaoImpl();
		columnTmp=templateDao.getColumnTmp(columnTmp.getId()); 
		NewsUtil iu = new NewsUtil();
		try {
			String jspTempPath = columnTmp.getTmpJspTmp();// jsp模板路径
			String jspPath ;
			if("index".equals(columnTmp.getColumnType())){
				jspPath = getSession().getServletContext().getRealPath(
				"/elfrontman")+"/"+columnTmp.getColumnType()+".jsp";// 
			}else{
				jspPath = getSession().getServletContext().getRealPath(
				"/elfrontman/cms")+"/"+columnTmp.getColumnType()+columnTmp.getColumnId()+".jsp";// 要替换的jsp文件路径  注：这个可能需要循环如果是选择的是栏目的根
			}
			String tempPath = getSession().getServletContext().getRealPath(
					"/template"); // 模块路径
			iu.newsJsp(jspTempPath, tempPath, jspPath, new LabelDaoImpl());
		} catch (Exception e) {
			logger.error("模版转jsp错误",e);
			throw new ElException(e);
		}
		return "template_toJsp_success";
	}

	public String template_list() throws ElException {
		templateDao = new TemplateDaoImpl();
		templateList = templateDao.listAllTemplate();
		return "template_list";
	}

	public String template_deleteById() throws ElException {
		templateDao = new TemplateDaoImpl();
		template = templateDao.getTemplate(template.getId());
		templateDao.deleteTemplate(template.getId());
		try {
			new IoUtil().deleteFile(template.getJspTmp());
			new IoUtil().deleteFile(template.getJsp());
		} catch (Exception e) {
			logger.error("删除模版错误",e);
		}
		return "template_list";
	}

	public String template_alterInit() throws ElException {
		templateDao = new TemplateDaoImpl();
		template = templateDao.getTemplate(template.getId()); 
		return "template_alter";
	}

	public String template_alter() throws ElException {
		templateDao = new TemplateDaoImpl();
		templateDao.updateTemplate(template);
		return "template_list";
	}

	public String column_alterTempInit() throws ElException {
		templateDao = new TemplateDaoImpl();
		columnTmp = templateDao.getColumnTmp(columnTmp.getId()); 
		templateList = templateDao.listAllTemplate();
		return "column_alterTempInit";
	}
	public String column_alterTemp() throws ElException {
		templateDao = new TemplateDaoImpl();
		if(null != columnTmp.getTmpName() && "".equals(columnTmp.getTmpName())==false){
			String[] tmp=columnTmp.getTmpName().split("-=lwh=-");
			columnTmp.setTmpId(Integer.parseInt(tmp[0]));
			columnTmp.setTmpName(tmp[1]);
			columnTmp.setTmpJspTmp(tmp[2]);
		}else{
			columnTmp.setTmpId(0);
			columnTmp.setTmpName("");
			columnTmp.setTmpJspTmp("");
		}
		templateDao.updateColumnTmp(columnTmp);
		return "column_template";
	}
	public String column_bindTempInit() throws ElException {
		templateDao = new TemplateDaoImpl(); 
		labelDao = new LabelDaoImpl(); 
		newsTypeList = labelDao.getNewsTypesAll();
		courseTypeList = labelDao.getCourseType();
		knowledgeTypeList = labelDao.getKnowledgeType();
		forumBlockTypeList = labelDao.getForumBlockTypeAll();
		templateList = templateDao.listAllTemplate();
		return "column_bindTempInit";
	}
	public String index_bindTemp() throws ElException {
		templateDao = new TemplateDaoImpl();  
		templateList = templateDao.listAllTemplate();
		return "index_bindTemp";
	}
	public String column_bindTemp() throws ElException {
		templateDao = new TemplateDaoImpl(); 
		String[] column=columnTmp.getColumnName().split("-=lwh=-");
		columnTmp.setColumnId(Integer.parseInt(column[0]));
		columnTmp.setColumnName(column[1]);
		if(null != columnTmp.getTmpName() && "".equals(columnTmp.getTmpName())==false){
			String[] tmp=columnTmp.getTmpName().split("-=lwh=-");
			columnTmp.setTmpId(Integer.parseInt(tmp[0]));
			columnTmp.setTmpName(tmp[1]);
			columnTmp.setTmpJspTmp(tmp[2]);
		}else{
			columnTmp.setTmpId(0);
			columnTmp.setTmpName("");
			columnTmp.setTmpJspTmp("");
		}
		templateDao.bindColumnTmp(columnTmp);
		return "column_template";
	}

	public String column_template() throws ElException {
		templateDao = new TemplateDaoImpl(); 
		columnTmpList=templateDao.listAllColumnTmp();
		return "column_template";
	}
	public String column_deleteById()throws ElException{
		templateDao = new TemplateDaoImpl(); 
		templateDao.deleteColumnTmp(columnTmp.getId());
		return "column_template";
	}
	
	public LabelDao getLabelDao() {
		return labelDao;
	}

	public void setLabelDao(LabelDao labelDao) {
		this.labelDao = labelDao;
	}
}
