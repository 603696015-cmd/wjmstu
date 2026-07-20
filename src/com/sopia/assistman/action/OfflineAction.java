package com.sopia.assistman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.assistman.dao.OfflineDao;
import com.sopia.assistman.entities.Offline;
import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;

public class OfflineAction extends BaseAction {
	private Offline offline;
	private OfflineDao offlineDao;
	private List<Offline> offlines;

	public Offline getOffline() {
		return offline;
	}

	public void setOffline(Offline offline) {
		this.offline = offline;
	}

	public OfflineDao getOfflineDao() {
		return offlineDao;
	}

	public void setOfflineDao(OfflineDao offlineDao) {
		this.offlineDao = offlineDao;
	}

	// /------部门统计
	public String offline_addinit() throws ElException {

		return "offline_add";
	}

	private List<ELUser> elUsers;

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public String offline_add() throws ElException {
		int id = offlineDao.addOffline(offline);
		offline.setId(id);
		if (null != elUsers) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (!offlineDao.checkOffline2AllUser(elUsers.get(i).getId(),
						offline.getId()))
					offlineDao.addOffline2User(elUsers.get(i).getId(), offline
							.getId());
			}
		}
		return "offline_view";
	}
	private ELUser elUser ; 
	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public String offline_deleteuser()throws ElException{
		offlineDao.deleteOffline2User(elUser.getId(), offline.getId());
		
		return null;
	}
	public String offline_view() throws ElException {
		offline = offlineDao.getOffline(offline.getId());
		elUsers = offlineDao.listOffline2Users(offline.getId());
		return "offline_view";
	}

	public String offline_alterinit() throws ElException {
		offline = offlineDao.getOffline(offline.getId());
		elUsers = offlineDao.listOffline2Users(offline.getId());
		return "offline_alter";
	}

	public String offline_alter() throws ElException {
		offlineDao.alterOffline(offline);
		if (null != elUsers) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (!offlineDao.checkOffline2AllUser(elUsers.get(i).getId(),
						offline.getId()))
					offlineDao.addOffline2User(elUsers.get(i).getId(), offline
							.getId());
			}
		}
		return "offline_view";
	}

	public String offline_delete() throws ElException {
		offlineDao.deleteOffline2AllUser(offline.getId());
		offlineDao.deleteOffline(offline.getId());
		return "offline_list";
	}

	public String offline_list() throws ElException {
		offlines = offlineDao.listOfflines(getPageNow(), getPageSize());
		count = offlineDao.listOfflinesSize();
		return "offline_list";
	}

	public List<Offline> getOfflines() {
		return offlines;
	}

	public void setOfflines(List<Offline> offlines) {
		this.offlines = offlines;
	}
}