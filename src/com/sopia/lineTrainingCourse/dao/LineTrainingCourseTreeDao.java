package com.sopia.lineTrainingCourse.dao;

import java.sql.Connection;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.lineTrainingCourse.entities.TrainTypeTree;

public interface LineTrainingCourseTreeDao {
	public TrainTypeTree getTraintypeTree(int from, int stop, boolean constop) 
		throws ElException;
	
	public TrainTypeTree getTraintypeRoot() throws ElException;
	
	public TrainTypeTree getTraintypeByid(int id) throws ElException;
	
	public List<TrainTypeTree> getNtChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception;
	
	public TrainTypeTree getPtypeLibById(int id) throws ElException;
	
	public void addTraintype(TrainTypeTree ptype) throws ElException;
	
	public abstract void alterTrainType(TrainTypeTree ptype) throws ElException;
	
	public void updateProductTypeParentid(int pid, int npid) throws ElException;
	public void updateProductParentid(int pid, int npid) throws ElException;
	public void deletePtype(int id) throws ElException;
	public void deleteProductTypeAndSub(int id,int pid) throws ElException;

}
