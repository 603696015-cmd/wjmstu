package com.sopia.elclasspeice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.elclasspeice.entities.ElClassPeice;

public interface ElClassPeiceDao {
	
	public List<ElClassPeice> getClassList(ElNode tree,ElClass elclass,int sublibs, String status,String sqlw, int pageNow, 
			int pageSize, String name, String userid, int dprice, int role ) throws ElException ;
	
	
	public void elClassPeice_Submit(int elclassid) throws ElException;
	
	public void elClassPeice_change(float peicevale, int elclassid,
			int peicetype,int userid) throws ElException ;
	
	public List<ElClassPeice> getMyAll(ElClType elcltypeTree, int type,
			String name, String status, String userid, int dprice, int role,
			int pageNow, int pageSize) throws ElException;
	
	public int getMyAllSize(ElClType elcltypeTree, int typeid, String name,
			String status, String userid, int dprice, int role)
			throws ElException;
	
//	public int getClassListSize(ElNode tree,ElClass elclass,int sublibs, String status)
//	throws ElException;
	
	public void elClassPeice_audit(int courseid, int userid,int setstatus) throws ElException; 
	
}

