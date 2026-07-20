package com.sopia.examroompeice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.elclasspeice.entities.ElClassPeice;
import com.sopia.examroompeice.entities.ExamRoomPeice;

public interface ExamRoomPeiceDao {
	
	public List<ExamRoomPeice> getExamRoomList(ElNode eroomLibTree,ExamRoom examRoom,int sublibs, String status,String sqlw, int pageNow, 
			int pageSize, String name, String userid, int dprice, int role ) throws ElException ;
	
	
	public void examRoomPeice_Submit(int examroomid) throws ElException;
	
	public void examRoomPeice_change(float peicevale, int elclassid,
			int peicetype,int userid) throws ElException ;
	
	public List<ExamRoomPeice> getMyAll(EroomLib eroomLib, int type,
			String name, String status, String userid, int dprice, int role,
			int pageNow, int pageSize) throws ElException;
	
	public int getMyAllSize(ElNode eroomLibTree,ExamRoom examRoom,int sublibs, String status,String sqlw, int pageNow, 
			int pageSize, String name, String userid, int dprice, int role)
			throws ElException;
	
//	public int getClassListSize(ElNode tree,ElClass elclass,int sublibs, String status)
//	throws ElException;
	
	public void examRoomPeice_audit(int examroomid, int userid,int setstatus) throws ElException; 
	
	
	
	
	
	
//-----------------------shopping------------------------------------------------------------------
	public List<EroomLib> getexamroomerjijiedian() throws ElException;
	
	/**
	 * 获取全部已开通的培训班信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getApplyForexamRoom(EroomLib tree, int cltid,
			ExamRoom examRoom , int role, String sqlw, int pageNow, int pageSize)throws ElException;
	
	public ExamRoomPeice getApplyForeExamRoomPeiceById(int examroomid) throws ElException;
	
	/**
	 * 获取全部已开通的培训班列表大小（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public int getApplyForeElclasssize(EroomLib tree, int cltid,
			ExamRoom examRoom, int role, String sqlw)
			throws ElException ;
	
	/**
	 * 判断用户是否已经购买该考场
	 * @param roomid
	 * @param userid
	 * @throws ElException
	 */
	public boolean checkUserRoom(int roomid,int userid) throws ElException;
		
}
