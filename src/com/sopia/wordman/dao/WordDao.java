package com.sopia.wordman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.wordman.entities.Vocabulary;
import com.sopia.wordman.entities.Word;

public interface WordDao {
	
	/**
	 * 获得所有节点
	 */
	public List<Word> getWordsTree() throws ElException;
	/**
	 * 获得所有二级节点（词汇查询页面下拉联动使用）
	 */
	public List<Word> getWordsTreeByParentid(int parentid)throws ElException;
	/**
	 * 得到指定id的词汇类别
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Word getWordLibById(int id) throws ElException;
	
	/**
	 * 得到指定id的词汇类别
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Word getWordsById(int id) throws ElException;
	
	public Word getWordsRoot() throws ElException;

	public int addWord(Word word)throws ElException;
	
	public List<ELUser> getOpUsers(String type, int depid) throws ElException;
	
	/**
	 * 更新词汇库的父节点
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateWordlibParentid(int pid, int npid) throws ElException;
	
	/**
	 * 假删除词汇库
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deleteWordlibAndSubNot(int id) throws ElException;
	
	/**
	 * 更新词汇类别的状态
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deletewordsLibNot(int id) throws ElException;
	
	/**
	 * 修改词汇类别
	 * 
	 * @param EroomLib
	 * @throws ElException
	 */
	public void alterWordLib(Word word) throws ElException;
	
	/**
	 * 获得词汇列表
	 */
	
	public List<Vocabulary> getVocList(Word word,Vocabulary vocabulary,int pageNow,int pageSize)throws ElException;
	//前台词汇查询功能
	public List<Vocabulary> getVocList2(Vocabulary vocabulary,int pageNow,int pageSize)throws ElException;
	public int getWordSize2(Vocabulary vocabulary)throws ElException;
	/**
	 * 获得词汇数量
	 */
	public int getWordSize(Word word,Vocabulary vocabulary)throws ElException;
	/**
	 * 添加词汇
	 */
	public int addVocabulary(Vocabulary vocabulary)throws ElException;
	/**
	 * 添加词汇例句
	 */
	public void addVocabularySen(Vocabulary vocabulary)throws ElException;
	
	public Vocabulary getVocById(int id)throws ElException;
	/**
	 * 删除词汇
	 */
	public void delVocById(int id)throws ElException;
	/**
	 * 词汇审核
	 */
	public void alterVocSta(Vocabulary vocabulary)throws ElException;
	
	public List<Vocabulary> getVocListByUserid(int adduserid,int pagenow,int pagesize)throws ElException;
	
	public int getVocListByUseridSize(int adduserid)throws ElException;
	
	/**
	 * 获取例句附件
	 * @param newsid
	 * @return
	 * @throws ElException
	 */
	public List<StuffLib> liststuff(int vocabularyid) throws ElException;
	/**
	 * 删除例句附件
	 */
	public void deleteVocStuff(int stuffid) throws ElException;
	/**
	 * 修改词汇
	 */
	public void alterVocabulary(Vocabulary vocabulary)throws ElException;
	/**
	 * 修改词汇例句附件
	 */
	public void alterVocStuff(Vocabulary vocabulary)throws ElException;
	
	/**
	 * 根据单元id得到词汇（单元和词汇库是一对一关系）
	 */
	public Word getWordsByCourseId(int courseid)throws ElException;
	
	/**
	 * 根据wordid获得单元
	 */
	public List<Course> getVocByWordId(ElNode word) throws ElException;
	/**
	 * 删除用户可操作的节点
	 */
	public void deleteUserOpGrant(int userid) throws ElException;
	
	public boolean checkOpUsers(String type, int userid, int depid)
	throws ElException;
	
	/*
	 * 给用户添加词汇库权限
	 */
	public void addOpusers(String type, int userid, int depid)
	throws ElException;
	
	public Word wdLibTree(String op, int userId, int stopid,
			boolean isContainStop) throws ElException;
	
	public Word getWordsTree(int from, int stop, boolean containStop)
	throws ElException;
	
	public Word getWordsTree(int userid, String op, int stop,
			boolean containStop) throws ElException;
	
	public Word wdLibTree(int id, int userId, int stopid,
			boolean isContainStop) throws ElException;
	
	//wjm0221修改
	//根据类别名获取类别id
	public int getwdLibTreeId(String name)throws ElException;
	//检查某一单元导入是否重复
	public boolean checkVocIsRepeat(Vocabulary voc) throws ElException;
}
