package com.sopia.knowledgeManage.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.ElConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

/**
 * 知识
 * @author Administrator
 *
 */
public class Kledge {
	private int id;
	private String name;			//知识名称
	private int knowledgeTreeid;	//知识类别id
	private Timestamp begintime;	//开始时间
	private Timestamp endtime;		//结束时间
	private Timestamp fabutime;		//发布时间
	private Timestamp xiugaitime;	//修改时间
	private String depname;			//制定部门
	private String zhizuoren;		//制作人
	private int fabuuserid;			//发布人id
	private int xiugaiuserid;		//修改人id
	private String fujian;			//附件
	private String jianjie;			//简介
	private int status;				//状态
	//0：已创建、9：终审通过
	
	private KnowledgeTree klTree;//知识类别
	private ELUser fabuUser;//发布人
	private ELUser xiugaiUser;//修改人
	
	private Competence ct_view;
	private Competence ct_update;
	private Competence ct_delete;
	private Competence ct_copy;
	private Competence ct_download;
	private List<Competence> ct_views;//查看权限
	private List<Competence> ct_updates;//修改权限
	private List<Competence> ct_deletes;//删除权限
	private List<Competence> ct_copys;//复制权限
	private List<Competence> ct_downloads;//下载权限
	private int count_view;
	private int count_update;
	private int count_delete;
	private int count_copy;
	private int count_download;
	
	private Department dep_view;
	private Department dep_update;
	private Department dep_delete;
	private Department dep_copy;
	private Department dep_download;
	private int hot;
	
	public String getHotName(){
		if(hot==ElConstants.HOT_RM) return "热门";
		if(hot==ElConstants.HOT_TJ) return "推荐";
		if(hot==ElConstants.HOT_ZD) return "重点";
		
		return "普通";
	}
	
	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public String getStatus_(){//数据状态中文标示
		if(status == 0){
			return "已创建";
		}
		if(status == 2){return "修改等待中";}
		if(status == 3){return "删除等待中";}
		if(status == 5){return "初审等待中";}
		if(status == 6){return "初审通过";}
		if(status == 7){return "初审不通过";}
		if(status == 8){return "终审等待中";}
		if(status == 9){return "终审通过";}
		if(status == 10){return "终审不通过";}
		return "";
	}
	
	public Kledge(){
		
	}
	
	public int getCount_view() {
		return count_view;
	}

	public void setCount_view(int count_view) {
		this.count_view = count_view;
	}

	public int getCount_update() {
		return count_update;
	}

	public void setCount_update(int count_update) {
		this.count_update = count_update;
	}

	public int getCount_delete() {
		return count_delete;
	}

	public void setCount_delete(int count_delete) {
		this.count_delete = count_delete;
	}

	public int getCount_copy() {
		return count_copy;
	}

	public void setCount_copy(int count_copy) {
		this.count_copy = count_copy;
	}

	public int getCount_download() {
		return count_download;
	}

	public void setCount_download(int count_download) {
		this.count_download = count_download;
	}

	public Kledge(int id){
		this.id = id;
	}
	public Kledge(int id,String name){
		this.id = id;
		this.name = name;
	}
	public Kledge(int id,String name,int knowledgeTreeid){
		this.id = id;
		this.name = name;
		this.knowledgeTreeid = knowledgeTreeid;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKnowledgeTreeid() {
		return knowledgeTreeid;
	}
	public void setKnowledgeTreeid(int knowledgeTreeid) {
		this.knowledgeTreeid = knowledgeTreeid;
	}
	public Timestamp getBegintime() {
		return begintime;
	}
	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public Timestamp getFabutime() {
		return fabutime;
	}
	public void setFabutime(Timestamp fabutime) {
		this.fabutime = fabutime;
	}
	public Timestamp getXiugaitime() {
		return xiugaitime;
	}
	public void setXiugaitime(Timestamp xiugaitime) {
		this.xiugaitime = xiugaitime;
	}
	
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public String getZhizuoren() {
		return zhizuoren;
	}
	public void setZhizuoren(String zhizuoren) {
		this.zhizuoren = zhizuoren;
	}
	public int getFabuuserid() {
		return fabuuserid;
	}
	public void setFabuuserid(int fabuuserid) {
		this.fabuuserid = fabuuserid;
	}
	
	public int getXiugaiuserid() {
		return xiugaiuserid;
	}
	public void setXiugaiuserid(int xiugaiuserid) {
		this.xiugaiuserid = xiugaiuserid;
	}
	public String getFujian() {
		return fujian;
	}
	public void setFujian(String fujian) {
		this.fujian = fujian;
	}
	public KnowledgeTree getKlTree() {
		return klTree;
	}
	public void setKlTree(KnowledgeTree klTree) {
		this.klTree = klTree;
	}
	public String getJianjie() {
		return jianjie;
	}
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	public ELUser getFabuUser() {
		return fabuUser;
	}
	public void setFabuUser(ELUser fabuUser) {
		this.fabuUser = fabuUser;
	}
	public ELUser getXiugaiUser() {
		return xiugaiUser;
	}
	public void setXiugaiUser(ELUser xiugaiUser) {
		this.xiugaiUser = xiugaiUser;
	}
	public List<Competence> getCt_views() {
		return ct_views;
	}
	public void setCt_views(List<Competence> ct_views) {
		this.ct_views = ct_views;
	}
	public List<Competence> getCt_updates() {
		return ct_updates;
	}
	public void setCt_updates(List<Competence> ct_updates) {
		this.ct_updates = ct_updates;
	}
	public List<Competence> getCt_deletes() {
		return ct_deletes;
	}
	public void setCt_deletes(List<Competence> ct_deletes) {
		this.ct_deletes = ct_deletes;
	}
	public List<Competence> getCt_copys() {
		return ct_copys;
	}
	public void setCt_copys(List<Competence> ct_copys) {
		this.ct_copys = ct_copys;
	}
	public List<Competence> getCt_downloads() {
		return ct_downloads;
	}
	public void setCt_downloads(List<Competence> ct_downloads) {
		this.ct_downloads = ct_downloads;
	}
	public Competence getCt_view() {
		return ct_view;
	}
	public void setCt_view(Competence ct_view) {
		this.ct_view = ct_view;
	}
	public Competence getCt_update() {
		return ct_update;
	}
	public void setCt_update(Competence ct_update) {
		this.ct_update = ct_update;
	}
	public Competence getCt_delete() {
		return ct_delete;
	}
	public void setCt_delete(Competence ct_delete) {
		this.ct_delete = ct_delete;
	}
	public Competence getCt_copy() {
		return ct_copy;
	}
	public void setCt_copy(Competence ct_copy) {
		this.ct_copy = ct_copy;
	}
	public Competence getCt_download() {
		return ct_download;
	}
	public void setCt_download(Competence ct_download) {
		this.ct_download = ct_download;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Department getDep_view() {
		return dep_view;
	}

	public void setDep_view(Department dep_view) {
		this.dep_view = dep_view;
	}

	public Department getDep_update() {
		return dep_update;
	}

	public void setDep_update(Department dep_update) {
		this.dep_update = dep_update;
	}

	public Department getDep_delete() {
		return dep_delete;
	}

	public void setDep_delete(Department dep_delete) {
		this.dep_delete = dep_delete;
	}

	public Department getDep_copy() {
		return dep_copy;
	}

	public void setDep_copy(Department dep_copy) {
		this.dep_copy = dep_copy;
	}

	public Department getDep_download() {
		return dep_download;
	}

	public void setDep_download(Department dep_download) {
		this.dep_download = dep_download;
	}
	
	
	
	
}
