package com.sopia.recruit.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.recruit.dao.RecruitDao;
import com.sopia.recruit.entities.Experience;
import com.sopia.recruit.entities.Language;
import com.sopia.recruit.entities.Recruit;

public class RecruitAction extends BaseAction {
	private ELUser elUser;
	private Recruit recruit;
	private RecruitDao recruitDao;
	private Language language;
	private Experience experience;
	private List<Language> languages;
	private List<Experience> experiences;
	
	
	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public RecruitDao getRecruitDao() {
		return recruitDao;
	}

	public void setRecruitDao(RecruitDao recruitDao) {
		this.recruitDao = recruitDao;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}	
	public Recruit getRecruit() {
		return recruit;
	}

	public void setRecruit(Recruit recruit) {
		this.recruit = recruit;
	}
	/**
	 *¼òÀú´´½¨
	 * @return
	 * @throws ElException
	 */
	public String resume_baseinfo()throws ElException{
		elUser = userDao.getUserById2(getSessionIntValue(ElConstants.SESSION_USERID));
		return "resume_baseinfo";
	}
	public String resume_baseinfo_save()throws ElException{
		//userDao.update2(elUser);
		elUser.setId(getSessionIntValue(ElConstants.SESSION_USERID));
		System.out.println(elUser);
		System.out.println(recruit);
		recruit.setId(recruitDao.addRecruit(recruit,elUser));
		return "resume_baseinfo_save";
	}
	public String resume_baseinfo_save_next()throws ElException{
		System.out.println(recruit.getId());
		experience.setResumeid(recruit.getId());
		experience.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		language.setResumeid(recruit.getId());
		language.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		recruitDao.addExpenrience(experience);
		recruitDao.addLanguage(language);
		recruitDao.addSchool(recruit);
	//	System.out.println(experience);
	//	System.out.print(language);
		return "resume_baseinfo_save_next";
	}

	public String resume_alterInit() throws ElException{
		System.out.println(recruit.getId());
		recruit = recruitDao.getRecruitById(recruit.getId());
		languages = recruitDao.getLanguageByReid(recruit.getId());
		experiences = recruitDao.getExperienceByReid(recruit.getId());
		return "resume_alterInit";
	}
	public String resume_alterUserInfoInit() throws ElException{
		recruit = recruitDao.getRecruitById(recruit.getId());
		return "resume_alterUserInfoInit";
	}
	public String resume_alterUserInfo() throws ElException{
		recruitDao.alterUserInfo(elUser,recruit);
		return "resume_alterUserInfo";
	}
	public String resume_alterWorkInit()throws ElException{
		recruit = recruitDao.getRecruitById(recruit.getId());
		return "resume_alterWorkInit";
	}
	public String resume_alterWork() throws ElException{
		recruitDao.alterWorkInfo(recruit);
		return "resume_alterWork";
	}
	public String resume_alterAssessInit()throws ElException{
		recruit = recruitDao.getRecruitById(recruit.getId());
		return "resume_alterAssessInit";
	}
	public String resume_alterAssess()throws ElException{
		recruitDao.alterAssessInfo(recruit);
		return "resume_alterAssess";
	}
	public String resume_alterWorkExpInit() throws ElException{
		experiences = recruitDao.getExperienceByReid(recruit.getId());
		return "resume_alterWorkExpInit";
	}
	public String alterWorkExpInit() throws ElException{
		experience = recruitDao.getExperienceByid(experience.getId());
		return "alterWorkExpInit";
	}
	public String alterWorkExp() throws ElException{
		recruit.getId();
		recruitDao.alterExperienceByid(experience);
		return "alterWorkExp";
	}
	public String resume_addWorkExp() throws ElException{
		experience.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		experience.setResumeid(recruit.getId());
		recruitDao.addWorkExp(experience);
		return "resume_addWorkExp";
	}
	public String resume_alterSchoolInit() throws ElException{
		recruit = recruitDao.getRecruitById(recruit.getId());
		return "resume_alterSchoolInit";
	}
	public String resume_alterSchool() throws ElException{
		recruitDao.alterSchool(recruit);
		return "resume_alterSchool";
	}
}
