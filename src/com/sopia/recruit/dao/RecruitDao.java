package com.sopia.recruit.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.recruit.entities.Experience;
import com.sopia.recruit.entities.Language;
import com.sopia.recruit.entities.Recruit;

public interface RecruitDao {
	/**
	 * 添加简历
	 */
	public int addRecruit(Recruit recruit,ELUser elUser) throws ElException;
	/**
	 * 添加外语能力
	 */
	public void addLanguage(Language language)throws ElException;
	/**
	 * 添加工作经验
	 */
	public void addExpenrience(Experience experience)throws ElException;
	/**
	 * 根据id查简历
	 */
	public Recruit getRecruitById(int recruitId)throws ElException;
	
	/**
	 * 添加毕业信息
	 */
	public void addSchool(Recruit recruit) throws ElException;
	
	/**
	 * 获得外语能力
	 */
	public List<Language> getLanguageByReid(int recruitid) throws ElException;
	/**
	 * 获得工作经验
	 */
	public List<Experience> getExperienceByReid(int recruitid)throws ElException;
	/**
	 * 修改基本信息
	 */
	public void alterUserInfo(ELUser eluser,Recruit recruit)throws ElException;
	/**
	 * 修改求职意向
	 */
	public void alterWorkInfo(Recruit recruit)throws ElException;
	/**
	 * 修改评价
	 */
	public void alterAssessInfo(Recruit recruit)throws ElException;
	/**
	 * 根据id获得工作经验
	 */
	public Experience getExperienceByid(int id)throws ElException;
	/**
	 * 修改工作经验
	 */
	public void alterExperienceByid(Experience experience)throws ElException;
	/**
	 * 增加工作经验
	 */
	public void addWorkExp(Experience experience)throws ElException;
	/**
	 * 修改教育背景
	 */
	public void alterSchool(Recruit recruit)throws ElException;
}
