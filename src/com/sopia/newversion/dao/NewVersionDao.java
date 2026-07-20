package com.sopia.newversion.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ElFunc;

public interface NewVersionDao {
	public List<ElFunc> getMenus(int isFromAdmin,int qiantaifunc_parentid,int parentid,int roleid) throws ElException;

}
