package com.sopia.questionman.entities;

import java.util.Date;
import java.util.List;

import com.sopia.duman.entities.ELUser;

public class StuffLib  {

	private int id;
	private String title;
	private String description;
	private String fileext;
	private ELUser owner ;
	private Date createtime;
	private Date modifytime;
	private long length;
	private int type;// 0,文件夹1图片，文档，视频，其他
	private StuffLib parent;
	private List<StuffLib> childs;
	private int level;
	private List<ELUser> users ;
	private int shared ;
	private String stuff_path;//资源的路径
//	private String stuff_path1;//资源的路径
	private boolean isgrant;
	private String pathZh1;
	private int loca;//资源位置
	private int status ;
	private String key;
	private String fileinfo;//文件简介
	private String stuffpic;//对应图片
	private int stuffhot;//资源热度
	private int fromchange;//是否来自转换
	private int generatejpg;//是否生成缩略图
	public StuffLib(int id){
		this.id = id;
	}


	public int getGeneratejpg() {
		return generatejpg;
	}


	public void setGeneratejpg(int generatejpg) {
		this.generatejpg = generatejpg;
	}


	public int getFromchange() {
		return fromchange;
	}


	public void setFromchange(int fromchange) {
		this.fromchange = fromchange;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLoca() {
		return loca;
	}

	public void setLoca(int loca) {
		this.loca = loca;
	}

	public String getPathZh1() {
		return pathZh1;
	}

	public void setPathZh1(String pathZh1) {
		this.pathZh1 = pathZh1;
	}

	public boolean getIsgrant() {
		return isgrant;
	}

	public void setIsgrant(boolean isgrant) {
		this.isgrant = isgrant;
	}

	public String getStuff_path() {
		return stuff_path;
	}

	public void setStuff_path(String stuff_path) {
		this.stuff_path = stuff_path;
	}

	public int getShared() {
		return shared;
	}

	public void setShared(int shared) {
		this.shared = shared;
	}

	public List<ELUser> getUsers() {
		return users;
	}

	public void setUsers(List<ELUser> users) {
		this.users = users;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<StuffLib> getChilds() {
		return childs;
	}

	public void setChilds(List<StuffLib> childs) {
		this.childs = childs;
	}

	public StuffLib getParent() {
		return parent;
	}

	public void setParent(StuffLib parent) {
		this.parent = parent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public StuffLib(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileext() {
		return fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	public StuffLib() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * <option value="1">图片</option> <option value="2">音频</option> <option
	 * value="3">视频</option> <option value="4">文档</option> <option
	 * value="5">其他</option>
	 */
	public String getTypeName() {
		if (type == 0)
			return "文件";
		if (type == 1)
			return "文件";
		if (type == 2)
			return "文件";
		if (type == 3)
			return "文件";
		if (type == 4)
			return "文件";
		if (type == 5)
			return "文件夹";
		if (type == 6)
			return "压缩文件";
		if (type == 7)
			return "解压文件夹";
		if (type == 8)
			return "解压文件夹";
		return "文件";
	}

	public String getLengthStr() {
		String s = "0";
		String dw = "B";
		if (length < 1024) {
			s = length + "";
			dw = "B";
		} else if (length < 1024 * 1024) {
			s = (length / 1024.0f) + "";
			dw = "KB";
		}

		else if (length < 1024 * 1024 * 1024) {
			s = (length / 1024 / 1024.0f) + "";
			dw = "MB";
		} else {
			//s = (length / 1024 / 1024 / 1024.0f) + "";
			s = (length / (double)(1024 * 1024 * 1024)) + "";
			dw = "GB";
		}

		if (s.indexOf(".") >= 0) {
			s = s.substring(0, (s.length() > s.indexOf(".") + 3) ? s
					.indexOf(".") + 3 : s.length())
					+ dw;
		}
		return s;
	}

	public String getIconStr() {
		if (type == 5||type == 7||type == 8) {
			return "folder.gif";
		}
		if(type==6)
			return "zip.gif";
		return "default.icon.gif";
	}

	public String getPathZh() {

		return getPathZh(this)+"/<b>"+this.getTitle()+"</b>";
	}
	public String getPathAll() {
		return getPathAll(this)+"/"+this.getTitle()+"."+this.getFileext();
	}
	public String getPathAll(StuffLib st){
		String s = "";
		if (st != null) {
			if (st.getParent() != null) {
				s = st.getParent().getTitle();
				s = getPathAll(st.getParent())+"/" + s;
			}
		}
		return s;
	}
	private String getPathZh(StuffLib st) {
		String s = "";
		if (st != null) {
			if (st.getParent() != null) {
				s = st.getParent().getTitle();
				s = getPathZh(st.getParent())+"/" +"<a href='question_stuffList.action?qpstuff.id="+st.getParent().getId()+"'>" + s+"</a>";
			}
		}
		return s;
	}
	public String setPathZh(String pathZh) {

		return pathZh;
	}
	public String getPathZh( String tpath) {
		String s = "";
		tpath = tpath==null?"":tpath.replaceAll("\\\\", "/");
		s = getPathZh(this)+"/<a href='question_stuffList.action?qpstuff.id="+this.getId()+"'>" + this.getTitle()+"</a>/"+getPathZh1(tpath,tpath);
		s += tpath.indexOf("/")==-1?"":"<b>"+tpath.substring(tpath.lastIndexOf("/")+1)+"</b>";
		return s;
	}
	public String getPathZh1(String tpath,String tpath1){
		String s = "";
		if(tpath.indexOf("/")!=-1){
			String s1= tpath.substring(0,tpath.indexOf("/"));
			String s2= tpath.substring(tpath.indexOf("/")+1);
			s="<a href='question_stuffList.action?qpstuff.id="+this.getId()+"&qstuff.stuff_path="+tpath1.substring(0,tpath1.indexOf(s2))+"'>" + s1+"</a>/"+getPathZh1(s2,tpath1);
		}
		return s;
	}
	public String getPath() {
		 
		return getPath(this)+"/"+this.id;
	}
	/**
	 * 获取资源路径（不加扩展名）
	 */
//	public String getPath() {
//		return this.getStuff_path();
//	}
	public String getPath(StuffLib st) {
		String s = "";
		if (st != null) {
			if (st.getParent() != null&&st.getParent().getId()!=0) {
				s = st.getParent().getId()+"";
				s = getPath(st.getParent())+"/" + s;
			}
		}
		return s;
	}

	public ELUser getOwner() {
		return owner;
	}

	public void setOwner(ELUser owner) {
		this.owner = owner;
	}

	public String getStuff_path1() {
		String s = "";
		if(stuff_path!=null)
			s = stuff_path.replaceAll("\\\\", "/");
		if(stuff_path!=null&&stuff_path.lastIndexOf("/")!=-1)
			s =  stuff_path.substring(0,stuff_path.lastIndexOf("/"));
		return s;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


	public String getFileinfo() {
		return fileinfo;
	}


	public void setFileinfo(String fileinfo) {
		this.fileinfo = fileinfo;
	}


	public String getStuffpic() {
		return stuffpic;
	}


	public void setStuffpic(String stuffpic) {
		this.stuffpic = stuffpic;
	}


	public int getStuffhot() {
		return stuffhot;
	}


	public void setStuffhot(int stuffhot) {
		this.stuffhot = stuffhot;
	}

//	public void setStuff_path1(String stuff_path1) {
//		this.stuff_path1 = stuff_path1;
//	}
}
