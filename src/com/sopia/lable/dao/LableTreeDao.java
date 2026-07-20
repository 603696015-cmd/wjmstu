package com.sopia.lable.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.lable.entites.LableTree;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public interface LableTreeDao {
	
	public List<LableTree> listdepChildsByPId(int parentid) throws Exception;
	
	public LableTree getLableTree_level1(int pid, int stopid, boolean containStop)
	throws ElException;
	
	public LableTree getLableTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException;
	
	public boolean checkLableTreeBh(String bh) throws ElException;
	
	public void addLableTree(LableTree lableTree) throws ElException;
	
	public LableTree getLableTreeById(int id) throws ElException;
	
	public void alterLableTree(LableTree lableTree) throws ElException;
	
	public void deleteDep(int depid,int lTreeParentid) throws ElException;
	
	public void deleteLableTreeAndSubNot(int id) throws ElException;

}
