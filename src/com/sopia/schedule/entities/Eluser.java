package com.sopia.schedule.entities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElException;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Station;
import com.sopia.pfms.entities.PfmsUser;

public class Eluser


{
	private static final Log logger = LogFactory.getLog(Eluser.class);
	
	  private int id      ;//        NUMBER not null,
	  private String username        ;//        VARCHAR2(20) not null,
	  private String password       ;//         VARCHAR2(100) not null,
	  private String realname       ;//         VARCHAR2(20) default ' ' not null,
	  private int role         ;//           NUMBER,
	  private int depid        ;//           NUMBER,
	  private int valid        ;//           NUMBER default 0 not null,
	  private String sex        ;//             VARCHAR2(10) default '?' not null,
	  private String xuhao       ;//            VARCHAR2(20),
	  private String dishi       ;//            VARCHAR2(50),
	  private String danwei       ;//           VARCHAR2(50),
	  private String zhiwu         ;//          VARCHAR2(50),
	  private String jingzhong;
	  private String gangwei        ;//         VARCHAR2(50),
	  private String movephone      ;//         VARCHAR2(20),
	  
	  private String departmentname;
	  private String rolename;
	  
	  private PfmsUser pfmsUser;
	  private Station station;
	  
	//wsj学分统计
		private int bxCredit;//必修学分
		private int tCredit;//总学分
	 
	public int getBxCredit() {
			return bxCredit;
		}
		public void setBxCredit(int bxCredit) {
			this.bxCredit = bxCredit;
		}
		public int getTCredit() {
			return tCredit;
		}
		public void setTCredit(int credit) {
			tCredit = credit;
		}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}
	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}
	public String getJingzhong() {
		return jingzhong;
	}
	public void setJingzhong(String jingzhong) {
		this.jingzhong = jingzhong;
	}
	public String getDepartmentname()
	{
		return departmentname;
	}
	public void setDepartmentname(String departmentname)
	{
		this.departmentname = departmentname;
	}
	public String getRolename()
	{
		return rolename;
	}
	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getRealname()
	{
		return realname;
	}
	public void setRealname(String realname)
	{
		this.realname = realname;
	}
	public int getRole()
	{
		return role;
	}
	public void setRole(int role)
	{
		this.role = role;
	}
	public int getDepid()
	{
		return depid;
	}
	public void setDepid(int depid)
	{
		this.depid = depid;
	}
	public int getValid()
	{
		return valid;
	}
	public void setValid(int valid)
	{
		this.valid = valid;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getXuhao()
	{
		return xuhao;
	}
	public void setXuhao(String xuhao)
	{
		this.xuhao = xuhao;
	}
	public String getDishi()
	{
		return dishi;
	}
	public void setDishi(String dishi)
	{
		this.dishi = dishi;
	}
	public String getDanwei()
	{
		return danwei;
	}
	public void setDanwei(String danwei)
	{
		this.danwei = danwei;
	}
	public String getZhiwu()
	{
		return zhiwu;
	}
	public void setZhiwu(String zhiwu)
	{
		this.zhiwu = zhiwu;
	}
	public String getZhiwu_() {
		if(zhiwu!=null&&!zhiwu.equals("0"))
			return getBasevalue(Integer.parseInt(zhiwu));
		else
			return "未知";
	}
	public String getGangwei()
	{
		return gangwei;
	}
	public void setGangwei(String gangwei)
	{
		this.gangwei = gangwei;
	}
	public String getMovephone()
	{
		return movephone;
	}
	public void setMovephone(String movephone)
	{
		this.movephone = movephone;
	}
	  
	public String getBasevalue(int key){ 
		try {
			BaseDatat base = ((UserDao)SpringContextUtil.getBean("userDao")).getBaseDatatById(key); 
			if(base != null){ 
				return base.getBasevalue();
			} 
		} catch (ElException e) {
			// TODO Auto-generated catch block
			logger.error("获取基础数据错误",e);
		}
		return key+"";
	}
	  
	  
}
