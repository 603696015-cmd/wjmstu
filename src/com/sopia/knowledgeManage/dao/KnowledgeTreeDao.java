package com.sopia.knowledgeManage.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public interface KnowledgeTreeDao {
	public KnowledgeTree getknowledgeTree_level1(int pid, int stopid, boolean containStop)
	throws ElException;
	
	public KnowledgeTree getknowledgeTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException;
	
	public boolean checkKnowledgeTreeBh(String bh) throws ElException;
	
	public void addKnowledgeTree(KnowledgeTree klTree) throws ElException;
	
	public List<KnowledgeTree> listKnowledgeTreeChildsByPId(int parentid) throws Exception;
	
	public KnowledgeTree getKnowledgeTreeById(int id) throws ElException;
	public List<ELUser> getOpUsers(String type, int knowledgeTreeid) throws ElException;
	
	public List<ELUser> getEUsByKnowledgeTreeid(int depid) throws ElException;
	
	public void alterKnowledgeTree(KnowledgeTree klTree) throws ElException;
	
	public void deleteDep(int depid,int klTreeParentid) throws ElException;
	
	public void deleteKnowledgeTreeAndSubNot(int id) throws ElException;
	

}
