package com.sopia.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElRole;
import com.sopia.newversion.NewVersionConstants;
import com.sopia.newversion.dao.NewVersionDao;
/**
 * 根据角色id加载北京市卫生局个人中心被分配菜单
 * @author Administrator
 *
 */
public class AuthorityNewVersionUtil {
	private static Map<String, List<ElFunc>> roleFuncs;
	private static final Log logger = LogFactory.getLog(AuthorityNewVersionUtil.class);
	
	public static void load(){
		try {
			roleFuncs = new HashMap<String, List<ElFunc>>();
			RoleDao rd = (RoleDao)SpringContextUtil.getBean("roleDao");
			List<ElRole> roles = rd.listRoles();
			NewVersionDao newVersionDao = (NewVersionDao)SpringContextUtil.getBean("newVersionDao");
			if (roles != null) {
				for (int i = 0; i < roles.size(); i++) {
					int id = roles.get(i).getId();
					roleFuncs.put(id + "", newVersionDao.getMenus(0, NewVersionConstants.QITAIYEMIAN_FUNC_ID, NewVersionConstants.GENRENZHONGXIN_ID, id));
				}
			}
		} catch (Exception e) {
			logger.error("新版个人中心加载角色失败！", e);
		}
	}
	
	public static List<ElFunc> getListElFuncByRoleid(String roleid){
		if(roleFuncs == null){
			load();
		}
		return roleFuncs.get(roleid);
	}
	
	
	
	public static List<ElFunc> getListElFuncByName(String name,int roleid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> menu_threes = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
				ps = ct
						.prepareStatement(
								"select el.id,el.funccode,el.name\n" +
								"  from elfunc el, elrolefunc elc\n" + 
								" where el.parentid = (select id from elfunc where funccode=?)\n" + 
								"   and elc.roleid = ?\n" + 
								"   and el.id(+) = elc.funcid");
				ps.setString(1, name);
				ps.setInt(2, roleid);
			rs = ps.executeQuery();
			ElFunc menu_three = null;
			while (rs.next()) {
				menu_three = new ElFunc();
				menu_three.setId(rs.getInt(1));
				menu_three.setFunccode(rs.getString(2));
				menu_three.setName(rs.getString(3));
				menu_threes.add(menu_three);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return menu_threes;
	}
	


}
