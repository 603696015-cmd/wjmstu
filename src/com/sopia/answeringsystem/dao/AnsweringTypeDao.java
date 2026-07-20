package com.sopia.answeringsystem.dao;

import java.util.List;

import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.entities.KnowledgeTree;

public interface AnsweringTypeDao {
	public AnsweringType getAnsweringTypeTree_level1(int pid, int stopid, boolean containStop)
		throws ElException;
	public AnsweringType getAnsweringTypeTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException;
	
	public boolean checkAnsweringTypeTreeBh(String bh) throws ElException;
	
	public void addAnsweringTypeTree(AnsweringType answeringType) throws ElException;
	
	public AnsweringType getAnsweringTypeTreeById(int id) throws ElException;
	
	public List<ELUser> getOpUsers(String type, int answeringTypeTreeid) throws ElException;
	
	public List<AnsweringType> listAnsweringTypeTreeChildsByPId(int parentid) throws Exception;
	
	public List<ELUser> getEUsByAnsweringTypeTreeid(int answeringTypeTreeid) throws ElException;
	
	public void alterAnsweringTypeTree(AnsweringType answeringType) throws ElException;
	
	public void deleteDep(int depid,int answeringTypeTreeParentid) throws ElException;
	
	public void deleteAnsweringTypeTreeAndSubNot(int id) throws ElException;
	
	public List<AnsweringType> listAllAnsweringTypes()throws ElException;
	
	public int getCountById(int lid,int rid) throws ElException;

}
