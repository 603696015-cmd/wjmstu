package com.sopia.schedule.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.ZDYTemplateUtil;
import com.sopia.schedule.dao.ClientTemplateDao;
import com.sopia.schedule.dao.ModuleManageDao;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;

/**
 * 自定义模块的自定义模板管理
 * @author Administrator
 *
 */
public class ClientTemplateAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(ClientTemplateAction.class);
	
	private ClientTemplateDao clientTemplateDao;
	private ModuleManageDao moduleManageDao;
	private TagsDao tagsDao;
	private ModuleManage moduleManage;
	private ModuleZDY moduleZDY;
	private int uploadType;//上传类型	1：添加页面、2：修改页面、3：查看页面
	private File st;
	private File st1;
	private String st1FileName;
	private String stFileName;
	private List<Tags> list_tags;
	
	
	/////////////////////////////////////////////////
	//action
	public String templateUploadInit() throws ElException, UnsupportedEncodingException{
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		
		moduleManage = moduleManageDao.select_module_by_id(moduleManage.getId());
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
		return "templateUploadInit";
	}
	
	public String templateUpload() throws Exception,ElException{
		String filenameall = uploadType == 4?
				moduleManage.getTablename() + ".css"
				:moduleManage.getTablename()+ "_" +ZDYTemplateUtil.getUploadValueByUploadType(uploadType)+".jsp";
		clientTemplateDao.updateModuleZDYByModuleid(moduleManage.getId(),uploadType,filenameall);
		
		moduleManage = moduleManageDao.select_module_by_id(moduleManage.getId());
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
		
		//将jsp页面上传到指定目录下
		//admin/etcperfect/template
		if(moduleZDY!=null){
			//判断是否已经存在以模块表名的文件夹，没有则创建，有则不创建
			if(moduleManage!=null&&!moduleManage.getTablename().equals("")){
				String foldername = moduleManage.getTablename();
				String ext = ZDYTemplateUtil.getExtByUploadType(uploadType);
				st = uploadType == 4?st1:st;
				String filename = uploadType == 4?
						moduleManage.getTablename()
						:moduleManage.getTablename()+ "_" +ZDYTemplateUtil.getUploadValueByUploadType(uploadType);
				J2EEFileUtil.upload_xianzhong(st, ext,
						ZDYTemplateUtil.PATH + foldername, filename
								+ "");
			}
		}
		
		
		
		return "templateUploadInit";
	}
	
	public String tableinfo_downloadInit() throws ElException{
		//参数moduleManage.tablename,uploadType
		if(moduleManage!=null&&moduleManage.getTablename()!=null&&!moduleManage.getTablename().equals("")){
			moduleManage = moduleManageDao.select_module_by_TableName(moduleManage.getTablename());
			list_tags = tagsDao.select_designe_field_by_tablename(moduleManage.getTablename());
		}
		
		if(list_tags!=null&&list_tags.size()>0){
			for(Tags tags:list_tags){
				if(tags.getColumn_name()!=null&&!tags.getColumn_name().equals("")){
					tags.setColumnTBHTMLName(ZDYTemplateUtil.createColumnTBHTMLName(tags.getColumn_name()));//显示列中文
					tags.setColumnTBHTML(ZDYTemplateUtil.createColumnTBHTML(tags.getColumn_name(),uploadType));//HTML
				}
			}
			
			getResponse().reset(); 		
			getResponse().setHeader("Content-disposition","attachment; filename="+moduleManage.getTablename()+ "_"+ZDYTemplateUtil.getUploadValueByUploadType(uploadType) + ".xls"); 
			getResponse().setContentType("application/vnd.ms-excel");  
			try {
				String titles[] = {"名称","列名","字段类型","标签名(列中文体现)","标签名(列HTML体现)"};
				String attrs[]= {"column_name","name_display","display_type","columnTBHTMLName","columnTBHTML"};
				new ExcelOutPut().writeExcel(moduleManage.getTablename()+"说明表",getResponse().getOutputStream(),titles,
						Tags.class.getName(), list_tags, attrs);
			} catch (Exception e) {
				logger.error("导出"+moduleManage.getTablename()+"说明Excel文档错误",e);
			}
			return null;
		}
		return "templateUploadInit";
	}
	
	public String userMakeHTML_downloadInit() throws ElException, IOException{
		//参数moduleManage.tablename,uploadType
		if(moduleManage!=null&&moduleManage.getTablename()!=null&&!moduleManage.getTablename().equals("")){
			moduleManage = moduleManageDao.select_module_by_TableName(moduleManage.getTablename());
			list_tags = tagsDao.select_designe_field_by_tablename(moduleManage.getTablename());
		}
		
		StringBuffer sb = new StringBuffer();
		if(list_tags!=null&&list_tags.size()>0){
			for(Tags tags:list_tags){
				if(tags.getColumn_name()!=null&&!tags.getColumn_name().equals("")){
					tags.setColumnTBHTMLName(ZDYTemplateUtil.createColumnTBHTMLName(tags.getColumn_name()));//显示列中文
					tags.setColumnTBHTML(ZDYTemplateUtil.createColumnTBHTML(tags.getColumn_name(),uploadType));//HTML
				}
				//获取列的一个基本HTML
				sb.append(ZDYTemplateUtil.formateColumnToHTML(tags.getColumn_name(), uploadType));
			}
		}
		
		if(sb!=null&&!sb.toString().equals("")){
			//判断路径下是否有保存txt文件的文件夹，无则创建
			if(ZDYTemplateUtil.checkFolderIsExist(moduleManage.getTablename())){
				//创建txt文件
				if(ZDYTemplateUtil.creatTxtFile(moduleManage.getTablename(),moduleManage.getTablename(),uploadType)){
					//写入txt
					if(ZDYTemplateUtil.writeTxtFile(moduleManage.getTablename(),uploadType,sb.toString())){
						//写入成功的提示信息
						setElmessage(URLEncoder.encode(URLEncoder.encode("写入到"+ZDYTemplateUtil.SHOWUSERPATH + moduleManage.getTablename()+"成功!!!", "UTF-8"), "UTF-8"));
					}else{
						setElmessage(URLEncoder.encode(URLEncoder.encode("写入失败!!!", "UTF-8"), "UTF-8"));
					}
				}else{
					setElmessage(URLEncoder.encode(URLEncoder.encode("写入失败!!!", "UTF-8"), "UTF-8"));
				}
			}else{
				setElmessage(URLEncoder.encode(URLEncoder.encode("写入失败!!!", "UTF-8"), "UTF-8"));
			}
		}
		return "templateUploadInit";
	}
	
	
	
	////////////////////////////////////////////////////////
	//get() set()

	public ClientTemplateDao getClientTemplateDao() {
		return clientTemplateDao;
	}

	public void setClientTemplateDao(ClientTemplateDao clientTemplateDao) {
		this.clientTemplateDao = clientTemplateDao;
	}

	public ModuleManage getModuleManage() {
		return moduleManage;
	}

	public void setModuleManage(ModuleManage moduleManage) {
		this.moduleManage = moduleManage;
	}

	public ModuleManageDao getModuleManageDao() {
		return moduleManageDao;
	}

	public void setModuleManageDao(ModuleManageDao moduleManageDao) {
		this.moduleManageDao = moduleManageDao;
	}

	public ModuleZDY getModuleZDY() {
		return moduleZDY;
	}

	public void setModuleZDY(ModuleZDY moduleZDY) {
		this.moduleZDY = moduleZDY;
	}



	public int getUploadType() {
		return uploadType;
	}



	public void setUploadType(int uploadType) {
		this.uploadType = uploadType;
	}

	public File getSt() {
		return st;
	}

	public void setSt(File st) {
		this.st = st;
	}

	public String getStFileName() {
		return stFileName;
	}

	public void setStFileName(String stFileName) {
		this.stFileName = stFileName;
	}

	public File getSt1() {
		return st1;
	}

	public void setSt1(File st1) {
		this.st1 = st1;
	}

	public String getSt1FileName() {
		return st1FileName;
	}

	public void setSt1FileName(String st1FileName) {
		this.st1FileName = st1FileName;
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}

	public TagsDao getTagsDao() {
		return tagsDao;
	}

	public void setTagsDao(TagsDao tagsDao) {
		this.tagsDao = tagsDao;
	}
	
	
	

}
