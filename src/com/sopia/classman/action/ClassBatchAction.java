package com.sopia.classman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.batchman.dao.BatchDao;
import com.sopia.batchman.entities.Batch;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;

/**
 * 培训批次
 * 
 * @author luocw
 * 
 */
public class ClassBatchAction extends BaseAction {
	
	
	/*** 培训批次*/
	private Batch batch;
	
	/*** 培训批次DAO*/
	private BatchDao batchDao;
	private ClassDao classDao;
	private List<ElClass> elclasses;
	private ElClass elclass  ;
	private ElClType cltype;
	private ElClType cltypeTree;
	private ElClTypeDao elClTypeDao;
	
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

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public BatchDao getBatchDao() {
		return batchDao;
	}

	public void setBatchDao(BatchDao batchDao) {
		this.batchDao = batchDao;
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

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	/**
	 * 添加培训批次初始化
	 * @return
	 * @throws ElException
	 */
	public String class_batch_addinit() throws ElException {
		return "class_batch_add";
	}
	/**添加培训班批次
	 * @return
	 * @throws ElException
	 */
	public String class_batch_add() throws ElException {
		batch.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		batchDao.addBatch(batch);
		if(elclasses!=null){
			for (int i = 0; i < elclasses.size(); i++) {
				if(!batchDao.checkBatchClass(batch.getId(), elclasses.get(i).getId()))
				batchDao.addBatchClass(batch.getId(), elclasses.get(i).getId());
			}
		}
		return "stat_class_batch_list";
	}
	/**修改培训班批次初始化
	 * @return
	 * @throws ElException
	 */
	public String class_batch_alterinit() throws ElException {
		batch=batchDao.getBatchById(batch.getId());
		batch.setClasses(batchDao.getBatchElclass(batch.getId()));
		return "class_batch_alter";
	}
	/**修改培训班批次
	 * @return
	 * @throws ElException
	 */
	public String class_batch_alter() throws ElException {
		batchDao.updateBatch(batch);
		if(elclasses!=null){
			for (int i = 0; i < elclasses.size(); i++) {
				if(!batchDao.checkBatchClass(batch.getId(), elclasses.get(i).getId()))
				batchDao.addBatchClass(batch.getId(), elclasses.get(i).getId());
			}
		}
		return "stat_class_batch_list";
	}
	/**删除批次
	 * @return
	 * @throws ElException
	 */
	public String class_batch_delete() throws ElException {
		batchDao.deleteBatch(batch.getId());
		return "stat_class_batch_list";
	}
	/**培训班批次培训班查看
	 * @return
	 * @throws ElException
	 */
	public String class_batch_class_view() throws ElException {
		elclass=classDao.getClassById(elclass.getId());
		return "class_batch_class_view";
	}
	/**培训班批次中的培训班删除
	 * @return
	 * @throws ElException
	 */
	public String class_batch_class_class_delete() throws ElException {
		batchDao.delBatchClass(batch.getId(), elclass.getId());
		return null;
	}
	/**培训班批次培训班列表
	 * @return
	 * @throws ElException
	 */
	public String class_batch_class_list() throws ElException {
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
 		String sqlw="0,1,2,3,4,5,6,7,8";
 		if(elclass!=null&&elclass.getSqlw()==9){
 			sqlw="9";
 		}
 		elclasses = classDao.getClassList(cltype, elclass,1,sqlw,"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype, elclass,1,sqlw);
		return "class_batch_class_list";
	}
}
