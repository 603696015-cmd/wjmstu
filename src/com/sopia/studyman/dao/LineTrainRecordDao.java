package com.sopia.studyman.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.LineTrainRecord;
import com.sopia.studyman.entities.LineTrainRecordStuff;
import com.sopia.studyman.entities.Schoolrolls;

/**
 * 线下培训
 * @author jiahaijiang
 */
public interface LineTrainRecordDao {
	
   /**
    * 保存或修改线下培训记录
    * @return
    * @throws ElException
    */
    public LineTrainRecord saveUpdateRecord(LineTrainRecord record)throws ElException;
    
    /**
     * 删除线下培训记录
     * @param ids
     * @throws ElException
     */
    public void deleteRecord(String ids)throws ElException;
    
    
    /**
     * 查询线下培训记录
     * @return
     * @throws ElException
     */
    public List<LineTrainRecord> findRecordList(Integer userid,Integer state)throws ElException;
    public int findRecordListCount(Integer userid,Integer state)throws ElException;
    
    /**
     * 查询线下培训记录
     * @return
     * @throws ElException
     */
    public List<LineTrainRecord> findManagementRecordList(ElNode tree,Integer state,int pageNow, int pageSize)throws ElException;
    /**
     * 查询线下培训记录数
     * @return
     * @throws ElException
     */
    public int findManagementRecordListSize(ElNode tree,Integer state)throws ElException;
    /**
     * 根据trainid查询线下培训记录
     * @param trainid
     * @return
     * @throws ElException
     */
    public LineTrainRecord findRecordByIds(Integer trainid)throws ElException;
    
    
    /**
     * 跟新培训记录状态 申请，审核
     * @param trainids
     * @param state
     * @throws ElException
     */
    public void updateState(String trainids,Integer state)throws ElException;
    
	/**
	 * 学籍查询
	 * @param deptid
	 * @return
	 * @throws ElException
	 */
    public List<Schoolrolls> getSchoolrollsList(Integer deptid, int pN,int pS)throws ElException;
    public List<Schoolrolls> getSchoolrollsList(ElNode tree,ELUser eluser,int sublibs, int pN,int pS)throws ElException;
	/**
	 * 学籍查询
	 * @param deptid
	 * @return
	 * @throws ElException
	 */
	public int getSchoolrollsListSize(Integer deptid)throws ElException;
	public int getSchoolrollsSize(ElNode tree,ELUser eluser,int sublibs)throws ElException;
	/**
	 * 添加或者修改线下培训记录附件
	 * @param recordStuff
	 * @return
	 * @throws ElException
	 */
	public LineTrainRecordStuff saveUpdateRecordStuff(LineTrainRecordStuff recordStuff) throws ElException;
	/**
	 * 根据培训记录id获取附件集合
	 * @param trainid
	 * @return
	 * @throws ElException
	 */
	public List<LineTrainRecordStuff> listRecordStuffByTrainid(int trainid) throws ElException;
	/**
	 * 删除线下培训记录附件
	 * @param id
	 * @throws ElException
	 */
	public void deleteLineTrainRecordStuffById(int id) throws ElException;
	/**
	 * 根据id获取线下培训记录附件
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public LineTrainRecordStuff getLineTrainRecordStuffById(int id) throws ElException;
	/**
	 * 设置线下培训记录学分
	 * @param credit
	 * @param trainid
	 * @throws ElException
	 */
	public  void  lineTrainsetcredit(int credit,int trainid) throws ElException;
	 /**
     * 线下培训记录总学分
     * @param userid
     * @return
     * @throws ElException
     */
	public int findMyRecordallcredit(int  userid ) throws ElException ;
}
