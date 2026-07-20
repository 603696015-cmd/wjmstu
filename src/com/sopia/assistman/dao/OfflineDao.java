package com.sopia.assistman.dao;

import java.util.List;

import com.sopia.assistman.entities.Offline;
import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;

public interface OfflineDao {
	public int addOffline(Offline offline) throws ElException;

	public void alterOffline(Offline offline) throws ElException;

	public void deleteOffline(int id) throws ElException;

	public void deleteOffline2AllUser(int id) throws ElException;

	public void addOffline2User(int userid, int offid) throws ElException;

	public boolean checkOffline2AllUser(int userid, int offid) throws ElException;

	public void deleteOffline2User(int userid, int offid) throws ElException;

	public List<ELUser> listOffline2Users(int offid) throws ElException;

	public Offline getOffline(int id) throws ElException;

	public List<Offline> listOfflines(int pageB, int pageE) throws ElException;

	public int listOfflinesSize() throws ElException;
}
