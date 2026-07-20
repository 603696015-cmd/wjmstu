package com.sopia.duman.dao;

import java.util.HashMap;
import java.util.List;


import com.sopia.common.ElException;
import com.sopia.duman.entities.ElFunc;

public interface FuncDao {
	public HashMap<String ,  ElFunc> listFuncNavs( ) throws ElException;
	public ElFunc getFuncById(int id) throws ElException;
	
	public List<ElFunc> listChildFunc(int id) throws ElException;
	public List<ElFunc> listChildFunc_cisco(int id,int roleid) throws ElException;
	
	public int getCountRemoveUserCenter(int userid,int roleid) throws ElException;
}
