package com.sopia.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.RoleDaoImpl;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElRole;

/**
 * 角色授权工具类
 * @author Administrator
 *
 */
public class AuthorityUtil {
	private static Map<String, List<ElFunc>> roleFuncs;
	private static List<String> funcs;
	private static List<Integer> funcs_id;
	
	private static Map<String,List<ElFunc>> roleFuncs_two;//权限二级菜单
	private static final Log logger = LogFactory.getLog(AuthorityUtil.class);

	public static void load() {
		try {
			roleFuncs = new HashMap<String, List<ElFunc>>();
			List<ElRole> roles = new RoleDaoImpl().listRoles();
			RoleDao rd = (RoleDao)SpringContextUtil.getBean("roleDao");
			funcs =rd.listFuncs();
			funcs_id=rd.listFuncs_id();
			List<ElFunc> els = null;
			ElFunc el = null;
			List<ElFunc> els_two = new ArrayList<ElFunc>();
			if (roles != null) {
				for (int i = 0; i < roles.size(); i++) {
					int id = roles.get(i).getId();
					roleFuncs.put(id + "", rd.getFuncsByRid(id));
//					//如果是二级菜单
//					els = rd.getFuncsByRid(id);
//					if(els!=null){
//						for(int j=0;j<els.size();j++){
//							el = els.get(j);
//							if(el.getId()!=0){
//								if(rd.checkFuncIsTwo(el.getId())){
//									els_two.add(el);
//								}
//							}
//						}
//						roleFuncs_two.put(id+"", els_two);
//					}
				}
			}
		} catch (Exception e) {
			logger.error("加载角色失败！", e);
		}

	}

	public static boolean checkHasFunc(String funccode) {
		return funcs.contains(funccode);
	}

	public static boolean checkAuthor(int roleid, String funccode,int userid) {
		try {
			if (null == roleFuncs)
				load();
			List<ElFunc> funcrole = roleFuncs.get(roleid + "");
			if (funcs.contains(funccode)) {
				if (funcrole != null)
					for (ElFunc elFunc : funcrole) {
						System.out.println(elFunc.getFunccode()+elFunc.getId()+elFunc.getName());
						
						if(elFunc.getFunccode() == null){
							continue;
						}
						
						if (elFunc.getFunccode().equals(funccode)) {//悲剧呀  ，居然不判断id
							return true;
						}
					}
//				if(userid == 0){
//					return true;
//				}
				if(new RoleDaoImpl().checkUserFuncs(userid, funccode)){
					return true;
				}
			} else {
				
				if (roleid == 7)
					return false;
				return true;
			}

		} catch (Exception e) {
			logger.error("权限判断出错！", e);
		}
		return false;
	}
	
	public static boolean checkAuthor(int roleid, int funcId,int userid) {
		try {
			if (null == roleFuncs)
				load();
			List<ElFunc> funcrole = roleFuncs.get(roleid + "");
			if (funcs_id.contains(funcId)) {
				if (funcrole != null)
					for (ElFunc elFunc : funcrole) {
						if (elFunc.getId()==funcId) {
							return true;
						}
					}
//				if(new RoleDaoImpl().checkUserFuncs(userid, funccode)){
//					return true;
//				}
			} else {
				
				if (roleid == 7)
					return false;
				return true;
			}

		} catch (Exception e) {
			logger.error("权限判断出错！", e);
		}
		return false;
	}
	/**
//	 * 检测用户是否具有功能权限
	 * @param userid
	 * @param funcId
	 * @return
	 */
	public static boolean checkUserfuncAuthor(int userid, int funcId) {
		try {
			//1.先查出用户所具有的功能权限
			UserDao ud = (UserDao)SpringContextUtil.getBean("userDao");
			List<ElFunc> elfs=ud.getEluserFunc(userid);
			for (int i = 0; i < elfs.size(); i++) {
				if(elfs.get(i).getId()==funcId){
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("检测用户是否具有功能权限出错！", e);
		}
		return false;
	}

	public static Map<String, List<ElFunc>> getRoleFuncs_two() {
		return roleFuncs_two;
	}

	public static void setRoleFuncs_two(Map<String, List<ElFunc>> roleFuncs_two) {
		AuthorityUtil.roleFuncs_two = roleFuncs_two;
	}
	
}
