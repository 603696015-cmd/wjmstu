package com.sopia.batchman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.batchman.dao.BatchDao;
import com.sopia.batchman.entities.Batch;
import com.sopia.batchman.entities.Flow;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;

/**
 * 培训批次
 * 
 * @author luocw
 * 
 */
public class BatchAction extends BaseAction {
	
	/*** 培训批次ID*/
	private String batchId;
	
	/*** 培训批次*/
	private Batch batch;
	
	/*** 培训批次列表*/
	private List<Batch> batchList;
	
	/*** 培训班列表*/
	private List<ElClass> elclassList;
	
	/*** 培训批次DAO*/
	private BatchDao batchDao;
	
	/*** 培训班id串*/
	private String ids;
	
	/*** 培训班中课程id*/
	private String elClassId;
	
	/*** 培训批次可选培训班相关*/
	private ElClTypeDao elClTypeDao;
	private ElClType cltype;
	private ElClType cltypeTree;
	private ElClass elClass;
	private ClassDao classDao;
	private List<ElClass> elclasses;
	private Flow flow;
	
	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	/**
	 * 添加培训批次
	 * @return
	 * @throws ElException
	 */
	public String batch_edit() throws ElException {
		int id =  (batch == null ? 0 : batch.getId());
		//判断是否新增
		if( id == 0){
			batch = new Batch();
		}else{
			batch = batchDao.getBatchById(id);
		}
		
		return "edit";
	}

	/**
	 * 培训批次列表
	 * @return
	 * @throws ElException
	 */
	public String batch_list() throws ElException {
		
		String name = batch == null ? "" : batch.getName();
		
		batchList = batchDao.getBatchList( name, getPageNow(), getPageSize());
		
		count = batchDao.getBatchListSize(name);
		
		return "list";
	}

	/**
	 * 保存方法
	 * @return
	 * @throws ElException
	 */
	public String batch_saveOrUpdate()  throws ElException {
		int id = batch.getId();
		//判断是否新增
		if( id == 0){
			batchDao.addBatch(batch);
		}else{
			batchDao.updateBatch(batch);
		}
		
		return batch_list();
	}
	
	/**
	 * 删除培训批次
	 * @return
	 * @throws ElException
	 */
	public String batch_delete() throws ElException {
		int id =  (batch == null ? 0 : batch.getId());
		batchDao.deleteBatch(id);
		return "list";
	}
	
	/**
	 * 培训批次添加培训班
	 * @return
	 * @throws ElException
	 */
	public String batch_elclass_add() throws ElException {
		if(batchId != null){
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				batchDao.addBatchClass(Integer.valueOf(batchId), Integer.valueOf(idArray[i]));
			}
		}
		
		return "list";
	}
	
	/**
	 * 培训批次删除培训班
	 * @return
	 * @throws ElException
	 */
	public String batch_elclass_del() throws ElException {
		if(batchId != null && elClassId != null){
			batchDao.delBatchClass(Integer.valueOf(batchId), Integer.valueOf(elClassId));
		}
		
		return "list";
	}
	
	/**
	 * 培训批次培训班
	 * @return
	 * @throws ElException
	 */
	public String batch_elclass_list() throws ElException {
		if(batchId != null){
			elclassList = batchDao.getBatchElclass(Integer.valueOf(batchId));
		}
		
		return "list";
	}
	
	/**
	 * 培训批次统计分析
	 * @return
	 * @throws ElException
	 */
	public String batch_stat_list() throws ElException {
		String name = batch == null ? "" : batch.getName();
		
		batchList = batchDao.getBatchStatList( name, getPageNow(), getPageSize());
		
		count = batchDao.getBatchStatListSize(name);
		
		return "list";
	}
	
	/**
	 * 培训批次包含的培训班 统计分析
	 * @return
	 * @throws ElException
	 */
	public String batch_class_stat() throws ElException {
		
		if(batchId != null){
			elclassList = batchDao.getBatchElclassState(Integer.valueOf(batchId));
		}
		
		return "list";
	}
	
	/**
	 * 培训批次可分配培训班
	 * 
	 * @return
	 * @throws ElException
	 */
	public String batch_elclass_selectList() throws ElException {
//		int deptId = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		String name = elClass == null ? "" : elClass.getName();
//		
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
//		elclasses = classDao.getBatchClassesList(cltypeTree, deptId, typeid, name, ClassConstants.CLASS_STATUS_OPEN_YES, getPageNow(), getPageSize(),Integer.valueOf(batchId));
//		count = classDao.getBatchClassesSize(cltypeTree, deptId, typeid, name, ClassConstants.CLASS_STATUS_OPEN_YES,Integer.valueOf(batchId));
//		 
		
		cltype=new ElClType();
		cltype.setId(1);
		elClass=new ElClass();
		elClass.setCltype(cltype);
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);//获取培训班级树
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "OP",ElConstants.TREE_FIANL, true);
		}
		elclasses=classDao.listcombinationSearchClass(elClass, cltypeTree,"-1", getPageNow(), getPageSize());//获取所有培训班信息
		count=classDao.listcombinationSearchClassCount(elClass, cltypeTree, getPageNow(), getPageSize());//获取培训班的记录数
		return "batch_elclass_selectList";
	}
	//流量统计
	public String flowStatistics()throws ElException{
		flow=batchDao.getFlow();
		return "flowStatistics";
	}
	
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public BatchDao getBatchDao() {
		return batchDao;
	}

	public void setBatchDao(BatchDao batchDao) {
		this.batchDao = batchDao;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public List<Batch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<Batch> batchList) {
		this.batchList = batchList;
	}

	public List<ElClass> getElclassList() {
		return elclassList;
	}

	public void setElclassList(List<ElClass> elclassList) {
		this.elclassList = elclassList;
	}

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public ElClType getCltype() {
		return cltype;
	}

	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getElClassId() {
		return elClassId;
	}

	public void setElClassId(String elClassId) {
		this.elClassId = elClassId;
	}

	
	
}
