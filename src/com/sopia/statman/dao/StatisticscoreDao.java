package com.sopia.statman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyCourse;

public interface StatisticscoreDao {
	
	public  List<ELUser>  studentscoer(ElNode tree, 
			int sublibs,ELUser elUser,int pageNow, int pageSize)throws ElException;
	public int studentscoersize(ElNode tree, 
			int sublibs,ELUser elUser) throws ElException;
	public List<MyCourse> scoerinfo_list_byuserid( int userid,int pn,int ps)
	throws ElException;
	public  int  scoerinfo_size_byuserid( int userid)
	throws ElException;
	public List<Integer> allscoerinfo_list_byuserid( int userid)
	throws ElException ;
}
