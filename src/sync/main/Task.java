package sync.main;

import java.util.List;

import net.sf.json.JSONObject;
import sync.domin.JSONObj;

import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.HttpUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public class Task implements Runnable {
	
	private String queryName;
	
	private int pid;
	
	private DepartmentDao departmentDao = (DepartmentDao) (SpringContextUtil.getBean("departmentDao"));
	
	

	public String getQueryName() {
		return queryName;
	}



	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}



	public int getPid() {
		return pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}



	public Task(String queryName, int i) {
		super();
		this.queryName = queryName;
		this.pid = i;
	}



	@Override
	public void run() {
		//执行任务
		 JSONObject jsonObj = HttpUtil.httpGet("http://restapi.amap.com/v3/config/district?keywords="+this.queryName+"&subdistrict=2&key=157bf68b8049e76e6809dba1a43dd972");
		 List<JSONObj> json1 = HttpUtil.fectData(jsonObj.getJSONArray("districts").getJSONObject(0));
		 
		 for (int i = 0; i < json1.size(); i++) {
			 Department dep1 = new Department();
			 if (dep1.getParent() == null) {
					// 因为ajax树有点缺陷
					dep1.setParent(new ElNode(this.pid));
				}
				if(dep1.getManager() == null){
					dep1.setManager(new ELUser(0));
				}
				dep1.setName(json1.get(i).getName());
				dep1.setBh(json1.get(i).getAdcode());
				try {
					departmentDao.addDep(dep1);
					((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
					.updatetlrid("department");
				} catch (ElException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//添加市区
				for (JSONObj json2 : json1.get(i).getDistricts()) {
					 Department dep2 = new Department();
					 if (dep2.getParent() == null) {
							// 因为ajax树有点缺陷
							dep2.setParent(new ElNode(dep1.getId()));
						}
						if(dep2.getManager() == null){
							dep2.setManager(new ELUser(0));
						}
						dep2.setName(json2.getName());
						dep2.setBh(json2.getAdcode());
						try {
							departmentDao.addDep(dep2);
							((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
							.updatetlrid("department");
						} catch (ElException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		}
		 
		
			System.out.println(this.queryName+"任务同步数据完成");
	}

}
