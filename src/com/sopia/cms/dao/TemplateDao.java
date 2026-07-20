package com.sopia.cms.dao;

import java.util.List;

import com.sopia.cms.entities.ColumnTemplate;
import com.sopia.cms.entities.Template;
import com.sopia.common.ElException;

public interface TemplateDao {

	public abstract void addTemplate(Template tmp) throws ElException;

	public abstract Template getTemplate(int id) throws ElException;
	
	public abstract List<Template> listAllTemplate()throws ElException;
	
	public abstract void deleteTemplate(int id) throws ElException;
	
	public abstract void updateTemplate(Template tmp) throws ElException ;
	
	
	public abstract void bindColumnTmp(ColumnTemplate columnTmp) throws ElException;

	public abstract ColumnTemplate getColumnTmp(int id) throws ElException;
	
	public abstract List<ColumnTemplate> listAllColumnTmp()throws ElException;
	
	public abstract List<ColumnTemplate> listColumnTmpByType(String type)throws ElException;
	
	public abstract void deleteColumnTmp(int id) throws ElException;
	
	public abstract void updateColumnTmp(ColumnTemplate columnTmp) throws ElException ;
}