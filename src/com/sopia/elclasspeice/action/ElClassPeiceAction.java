package com.sopia.elclasspeice.action;

import java.io.UnsupportedEncodingException;
import java.util.List;


import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.elclasspeice.dao.ElClassPeiceDao;
import com.sopia.elclasspeice.entities.ElClassPeice;

public class ElClassPeiceAction extends BaseAction{
	
	private ElClassPeice elClassPeice;
	private ElClassPeiceDao elClassPeiceDao;
	private ElClass elClass;//培训班
	private int sublibs;
	private List<ElClassPeice> elclasses;
	private ClassDao classDao;  
//	private List<ElClass> elclasses;
	private int   stype;//搜索分类
	private ElClType cltypeTree;//培训班类型树
	private ElClType cltype;
	private ElClTypeDao elClTypeDao;
	private int      pt;//价格类型
	private float wpeice;
	private String upd;	
	private int biaoshi;//判断是否需要修改
	private int setstatus;//需要修改的状态值
	private String ifadmin;
	private ELUser creater;
	
	
	//培训班
	
//
//	private Department department;
//	private ElClass elclass;
//	private int DBMethods;
//	private List<ElClass> elClasss;
//	private int sub_department;
//	private List<ExamPaper> examPapers;
//	private	EroomDao eroomDao;
//	private int ajax;
//	private ClassOrder classOrder;
//	private List<ClassOrder> classOrders;
	
	public String peice_applyfor_elclass() throws ElException, UnsupportedEncodingException{
		//如果价格状态存在，则修改价格
		if(pt==1||pt==2){
			elClassPeice_change();
			pt=0;
		}
		//如果表示=1 则申请审核价格
		if(biaoshi==1){
			elClassPeiceDao.elClassPeice_Submit(elClass.getId());
		}
		
		int typeid = 1;
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		String name = elClass == null ? "" : elClass.getName(); 
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
 			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
 		}else{
 			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
 		} 
 		
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		sublibs = 1;
		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
//		String status="0,9";//创建完成的课程
//		elclasses = classDao.getClassList(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = elClassPeiceDao.getMyAllSize(cltype, typeid, name,"0,1,2,3,4,5,6,7,8",  myUserId, stype, role);             
		elclasses = elClassPeiceDao.getClassList(cltype, elClass, sublibs, "0,1,2,3,4,5,6,7,8", "0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize(), name, myUserId, stype, role);
// 		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
//		String status="0,9";//创建完成的课程
//		cp=peiceDao.getMyAll( ctype , 1 ,name, status, myUserId, stype,role, getPageNow(), getPageSize());
//		count = elClassPeiceDao.getMyAllSize( ctype , 1 ,name, status, myUserId, stype,role);
//	
//		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
////		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
////			elcltypeTree = elcltypeDao.getClassLibTree(ElConstants.TREE_ROOT,ifadmin, ElConstants.TREE_FIANL, true);
//////			elcltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
//// 		}else{
//// 			elcltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
//// 		} 
//		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			elcltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
// 		}else{
// 			elcltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
// 		} 
//		if(elcltype==null||elcltype.getId()<=0){
//			elcltype=elcltypeTree;
//		}else{
//			elcltype=elClTypeDao.getClTypeById(elcltype.getId());    
//		}
//		
//		String name = elclass == null ? "" : elclass.getName();//搜索名字
//		
//		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
//		String status="0,9";//创建完成的课程
//		cp=elClassPeiceDao.getMyAll(elcltypeTree, 1 ,name, status, myUserId, stype,role, getPageNow(), getPageSize());   
//		count = elClassPeiceDao.getMyAllSize(elcltypeTree, 1 ,name, status, myUserId, stype,role);    
//		
//
//		sublibs = elclass == null ? 1 : sublibs;
// 		elclasses = classDao.getClassList(elcltype, elclass,sublibs,"0,1,2,3,4,5,6,7,8","0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
//		count = classDao.getClassListSize(elcltype, elclass,sublibs,"0,1,2,3,4,5,6,7,8");
		
	
// 		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}else{
//			typeid=cltypeTree.getId();
//		}
// 		
//		elclasses = classDao.getClassesList3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
//		count = classDao.getClassesSize3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
// 		elclasses = classDao.listClasses(0, typeid, name, getPageNow(), getPageSize());
//		count = classDao.listClassesSize(0, typeid, name);
 		
		
		return "peice_applyfor_elclass_success";
	}
	
	
	
	public String peice_audit_elclass() throws ElException{
		
		//如果价格状态存在，则修改价格
		if(pt==1||pt==2){
			elClassPeice_change();
			pt=0;
		}
//		//如果表示=1 则申请审核价格
//		if(biaoshi==1){
//			elClassPeiceDao.elClassPeice_Submit(elClass.getId());
//		}
		
		if(elClass!=null&&elClass.getId()>0&&setstatus!=0){//如果setstatus!=0 则审核价格
			int myUserId = getSessionIntValue(ElConstants.SESSION_USERID);
			elClassPeiceDao.elClassPeice_audit(elClass.getId(), myUserId,setstatus);     
			elClass=null;
		}
		
		int type = cltype == null || cltype.getId() <= 0 ? -1 : cltype.getId(); 
		
		int typeid = 1;
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		String name = elClass == null ? "" : elClass.getName(); 
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
 			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
 		}else{
 			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
 		} 
 		
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		sublibs = 1;
		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
//		String status="0,9";//创建完成的课程
//		elclasses = classDao.getClassList(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = elClassPeiceDao.getMyAllSize(cltype, typeid, name,"0,1,2,3,4,5,6,7,8",  myUserId, stype, role);             
		elclasses = elClassPeiceDao.getClassList(cltype, elClass, sublibs, "0,1,2,3,4,5,6,7,8", "0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize(), name, myUserId, stype, role);
		
		return "peice_audit_elclass_success";
	}
	
	
	/**
	 * 培训班定价修改
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ELException 
	 */
	public void elClassPeice_change()throws ElException{
		elClassPeiceDao.elClassPeice_change(wpeice, elClass.getId(), pt,getSessionIntValue(ElConstants.SESSION_USERID));
		

	}

///-------------------------------------------set and get ---------------------------------------------------------
	




	public ElClassPeiceDao getElClassPeiceDao() {
		return elClassPeiceDao;
	}



	public void setElClassPeiceDao(ElClassPeiceDao elClassPeiceDao) {
		this.elClassPeiceDao = elClassPeiceDao;
	}



	public ElClass getElClass() {
		return elClass;
	}



	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}



	public int getSublibs() {
		return sublibs;
	}



	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}



//	public List<ElClass> getElclasses() {
//		return elclasses;
//	}
//
//
//
//	public void setElclasses(List<ElClass> elclasses) {
//		this.elclasses = elclasses;
//	}
//


	public ClassDao getClassDao() {
		return classDao;
	}



	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}



//	public List<ElClassPeice> getCp() {
//		return cp;
//	}
//
//
//
//	public void setCp(List<ElClassPeice> cp) {
//		this.cp = cp;
//	}



	public int getStype() {
		return stype;
	}



	public void setStype(int stype) {
		this.stype = stype;
	}



	public ElClType getCltypeTree() {
		return cltypeTree;
	}



	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}



	public ElClType getCltype() {
		return cltype;
	}



	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}



	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}



	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}



	public int getPt() {
		return pt;
	}



	public void setPt(int pt) {
		this.pt = pt;
	}



	public float getWpeice() {
		return wpeice;
	}



	public void setWpeice(float wpeice) {
		this.wpeice = wpeice;
	}



	public String getUpd() {
		return upd;
	}



	public void setUpd(String upd) {
		this.upd = upd;
	}



	public int getBiaoshi() {
		return biaoshi;
	}



	public void setBiaoshi(int biaoshi) {
		this.biaoshi = biaoshi;
	}



	public int getSetstatus() {
		return setstatus;
	}



	public void setSetstatus(int setstatus) {
		this.setstatus = setstatus;
	}



	public String getIfadmin() {
		return ifadmin;
	}



	public void setIfadmin(String ifadmin) {
		this.ifadmin = ifadmin;
	}



	public ElClassPeice getElClassPeice() {
		return elClassPeice;
	}



	public void setElClassPeice(ElClassPeice elClassPeice) {
		this.elClassPeice = elClassPeice;
	}



	public List<ElClassPeice> getElclasses() {
		return elclasses;
	}



	public void setElclasses(List<ElClassPeice> elclasses) {
		this.elclasses = elclasses;
	}



	public ELUser getCreater() {
		return creater;
	}



	public void setCreater(ELUser creater) {
		this.creater = creater;
	}



	



	
	
	

}
