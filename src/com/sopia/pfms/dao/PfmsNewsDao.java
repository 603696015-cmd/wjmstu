package com.sopia.pfms.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;

public interface PfmsNewsDao {
	
	public abstract List<News> newsList(int userid,int nid,NewsType ntypeTree,News news,int pageNow,int pageSize) throws ElException;
	
	public abstract int newsListCount(int userid,int nid,NewsType ntypeTree,News news) throws ElException;
	
	public List<News> newsPersonerList(boolean is_show, int number) throws ElException;
	public int newsPersonerCount() throws ElException;
	public int getNeedToShenheNewsCount() throws ElException;

}
