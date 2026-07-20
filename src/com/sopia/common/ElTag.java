package com.sopia.common;

import java.io.OutputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * 自定义标签 父类
 * @author Administrator
 *
 */
public abstract class ElTag extends TagSupport{
	private int selectid;
	private boolean rootAble;
	private String iname;
	private Object ivalue;
	private String itype;
	private int did;
	private String attrname;
	private	String treeType;
	private int iid;
	private int nodeIndex=0; 
	private int use;
	private boolean model;
	private int competenceType;
	private String inputtype;
	private String inputname;
	
	

	

	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public String getInputname() {
		return inputname;
	}

	public void setInputname(String inputname) {
		this.inputname = inputname;
	}

	public boolean getModel() {
		return model;
	}

	public void setModel(boolean model) {
		this.model = model;
	}

	public int getCompetenceType() {
		return competenceType;
	}

	public void setCompetenceType(int competenceType) {
		this.competenceType = competenceType;
	}

	public int getUse() {
		return use;
	}

	public void setUse(int use) {
		this.use = use;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public int getSelectid() {
		return selectid;
	}

	public void setSelectid(int selectid) {
		this.selectid = selectid;
	}
	public Object getIvalue() {
		return ivalue;
	}
	public void setIvalue(Object ivalue)throws JspException    {
		
		this.ivalue = ExpressionEvaluatorManager.evaluate("ivalue", ivalue.toString(), Object.class, this, pageContext);   //ivalue;
	}
	public String getItype() {
		return itype;
	}
	public void setItype(String itype) {
		this.itype = itype;
	}
	public String getIname() {
		return iname;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public boolean getRootAble() {
		return rootAble;
	}
	public void setRootAble(boolean rootAble) {
		this.rootAble = rootAble;
	}
	private String href;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public abstract void writeChilds(JspWriter out,Object obj ) throws Exception;

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	} 
	
//	public abstract int doEndTag() throws JspException ;
}
