package com.sopia.lable.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.MD5;
import com.sopia.lable.dao.ModeBindDao;
import com.sopia.lable.entites.Geturl;
import com.sopia.lable.entites.Mode;
import com.sopia.lable.entites.Template;
import com.sopia.lable.entites.TreeNode;

/**
 * 模板绑定action
 * 
 * @author Administrator
 * 
 */
public class ModeBindAction extends BaseAction {
	private ModeBindDao modeBindDao;
	private List<Mode> modeList;
	private Mode mode;
	private List<Template> tList;
	private int newpageajax;
	private TreeNode node;// 节点
	private TreeNode nodes;
	private String json = "";
	private String page = "error.jsp";
	private File st;
	private String stFileName;
	private Geturl geturl;
	private String name;
	private String strname = "";
	private Template template;
	private TreeNode treeNodes;
	private TreeNode treeNode;
	private String tableName;
	private int modetypeid;

	public int getModetypeid() {
		return modetypeid;
	}

	public void setModetypeid(int modetypeid) {
		this.modetypeid = modetypeid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public TreeNode getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(TreeNode treeNodes) {
		this.treeNodes = treeNodes;
	}

	public TreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getStrname() {
		return strname;
	}

	public void setStrname(String strname) {
		this.strname = strname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Geturl getGeturl() {
		return geturl;
	}

	public void setGeturl(Geturl geturl) {
		this.geturl = geturl;
	}

	public File getSt() {
		return st;
	}

	public void setSt(File st) {
		this.st = st;
	}

	public String getStFileName() {
		return stFileName;
	}

	public void setStFileName(String stFileName) {
		this.stFileName = stFileName;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public TreeNode getNodes() {
		return nodes;
	}

	public void setNodes(TreeNode nodes) {
		this.nodes = nodes;
	}

	public TreeNode getNode() {
		return node;
	}

	public void setNode(TreeNode node) {
		this.node = node;
	}

	public int getNewpageajax() {
		return newpageajax;
	}

	public void setNewpageajax(int newpageajax) {
		this.newpageajax = newpageajax;
	}

	public List<Template> getTList() {
		return tList;
	}

	public void setTList(List<Template> list) {
		tList = list;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public ModeBindDao getModeBindDao() {
		return modeBindDao;
	}

	public void setModeBindDao(ModeBindDao modeBindDao) {
		this.modeBindDao = modeBindDao;
	}

	public List<Mode> getModeList() {
		return modeList;
	}

	public void setModeList(List<Mode> modeList) {
		this.modeList = modeList;
	}

	/**
	 * 获得系统中所有的模块
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_allmodeList() throws ElException {
		// 得到系统中所有模块和自定义模块
		modeList = modeBindDao.Mode_getallmode(getPageNow(), getPageSize());
		count = modeBindDao.Mode_getallmodecount();
		return "success";
	}

	public String mode_addPageToMode() {
		// 得到mode
		return "";
	}

	/**
	 * 得到指定模块的指定信息
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_modeviwe() throws ElException {
		// 得到当前查询模块
		mode = modeBindDao.Mode_getmodebyIDandType(mode.getId(), mode
				.getTypeid(), mode.getBindtypeid());
		return "success";
	}

	/**
	 * 得到所有模板信息
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_modepageList() throws ElException {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter localPrintWriter;

		// 得到模板
		int page1 = (newpageajax - 1) * 10 + 1;
		int page2 = newpageajax * 10;

		File file = new File(ServletActionContext.getServletContext()
				.getRealPath("/")
				+ "mode");
		if (!file.exists()) {
			boolean bool = file.mkdirs();
			if (!bool) {
				try {
					localPrintWriter = resp.getWriter();
					localPrintWriter.println("创建模板文件夹失败");
					localPrintWriter.flush();
					localPrintWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}
		if (file == null) {
			try {
				localPrintWriter = resp.getWriter();
				localPrintWriter.println("没有任何文件");
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		String test[];
		test = file.list();
		if (test == null && test.length < 1) {

			try {
				localPrintWriter = resp.getWriter();
				localPrintWriter.println("没有任何文件");
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		String strfilename = "";
		for (int i = 0; i < test.length; i++) {
			if (i == 0)
				strfilename += "'" + test[i] + "'";
			else
				strfilename += ",'" + test[i] + "'";
		}
		tList = modeBindDao.mode_modepageList(page2, page1, strfilename);
		count = modeBindDao.mode_modepageListcount(strfilename);
		int zongpage = 0;
		if (count % 10 == 0) {
			zongpage = count / 10;
		} else {
			zongpage = count / 10 + 1;
		}
		StringBuilder str = new StringBuilder();

		try {
			localPrintWriter = resp.getWriter();
			int a = 1;
			if (tList != null) {
				for (Template t : tList) {

					if (a % 2 == 1) {
						str.append("<tr>");
					}
					str
							.append("<td width='50px'><input type='radio' name='templateradio' value='"
									+ t.getId()
									+ "'/></td><td width='200px'>"
									+ t.getName() + "</td>");
					if (tList.size() == a && a % 2 == 1) {
						str
								.append("<td width='50px'>&nbsp;</td><td width='200px'>&nbsp;</td>");
					} else if (a % 2 == 0 || tList.size() == a) {
						str.append("</tr>");
					}

					a++;
				}

			}
			str.append("<tr><td colspan='4'>");
			if (newpageajax > 1) {
				str
						.append("<a style='cursor: hand' href='javascript:gettemplatelist("
								+ 1 + ")'>[首页]</a>");
				str
						.append("<a style='cursor: hand' href='javascript:gettemplatelist("
								+ (newpageajax - 1) + ")'>[上一页]</a>");
			} else {

				str.append("[首页]");
				str.append("[上一页]");
			}
			if (zongpage > 0) {
				str
						.append("<select  onchange='gettemplatelist(this.options[this.selectedIndex].value)'>");
				for (int i = 1; i <= zongpage; i++) {
					if (i == newpageajax)
						str.append("<option value='" + i
								+ "' selected='selected'>" + i + "</option>");
					else {
						str.append("<option value='" + i + "'>" + i
								+ "</option>");
					}

				}
				str.append("</select> ");
			}
			if (newpageajax < zongpage) {
				str
						.append("<a style='cursor: hand' href='javascript:gettemplatelist("
								+ (newpageajax + 1) + ")'>[下一页]</a>");
				str
						.append("<a style='cursor: hand' href='javascript:gettemplatelist("
								+ zongpage + ")'>[末页]</a>");
			} else {
				str.append("[下一页]");
				str.append("[末页]");
			}

			str.append("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
					+ "<b>条</b></span>");

			str.append("</td></tr>");
			localPrintWriter.println(str.toString());
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 绑定模板
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_updmodebind() throws ElException {

		try {

			modeBindDao
					.mode_updmode_bind(mode.getModeJspid(), mode.getBindid());
			setElmessage("绑定成功");

		} catch (ElException e) {
			// TODO Auto-generated catch block
			setElmessage("绑定失败");
			e.printStackTrace();
		}
		if (mode.getTypetableName() != null
				&& !mode.getTypetableName().equals("")) {
			return mode_typemodeviwe();
		} else {

			return mode_modeviwe();
		}

	}

	/**
	 * 解除绑定（内容页，和模板）
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_removebind() throws ElException {
		try {
			modeBindDao.mode_removemode_bind(mode.getBindid());
			setElmessage("解除成功");
		} catch (ElException e) {
			// TODO Auto-generated catch block
			setElmessage("解除失败");
			e.printStackTrace();
		}

		if (mode.getTypetableName() != null
				&& !mode.getTypetableName().equals("")) {
			return mode_typemodeviwe();
		} else {

			return mode_modeviwe();
		}
	}

	/**
	 * 得到指定模块的类别绑定信息
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_typemodeviwe() throws ElException {
		// 得到当前模块的类别绑定信息
		if (node == null) {
			node = new TreeNode(1);
		} else if (node.getId() == 0) {
			node.setId(1);
		}
		mode = modeBindDao.Mode_getTypebindbyID(mode.getId(), node.getId());
		// 得到类别树
		nodes = modeBindDao.epLibTree(0, mode.getTypetableName(), -1, true);
		if (nodes == null) {
			node.setId(0);
			mode.setBindtypestatus(0);
		}
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		String jsonsorderField = null;
		jsonsorderField = gson.toJson(nodes);
		json = "[" + jsonsorderField + "]";
		return "mode_typemodeviwe_success";
	}

	public String mode_updtypeextend() throws ElException {
		try {
			if (node != null && node.getId() != 0) {
				if (modeBindDao.Mode_checktype(node.getId(), mode
						.getTypetableName())) {
					modeBindDao.Mode_updtypeextend(mode.getBindtypestatus(),
							mode.getBindid());
					setElmessage("设置成功");
				} else {
					setElmessage("该类别不存在");
				}
			} else {
				setElmessage("该类别不存在");
			}

		} catch (ElException e) {
			// TODO Auto-generated catch block
			setElmessage("该类别不存在");
			e.printStackTrace();
		}
		return mode_typemodeviwe();
	}

	/**
	 * url解析
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_action() throws ElException {
		if (mode != null) {
			if (mode.getId() <= 0 || mode.getTypeid() <= 0
					|| mode.getTypeid() > 2) {
				setElmessage("参数值错误");
			} else if (mode.getBindtypeid() == 1 || mode.getBindtypeid() == 3) {// 模块绑定
				if (mode.getBindtypeid() == 3
						&& (geturl == null || geturl.getContentid() == null)) {
					setElmessage("参数不足");

				}
				if (geturl != null && geturl.getContentid() != null) {
					// 对于自定义表
					// 插入mode_info表相关信息
					modeBindDao.intoModeInfo(mode.getId());
					Mode m = modeBindDao.Mode_getmodebyID(mode.getId());
					mode.setTableName(m.getTableName());
					mode.setKey(m.getKey());
					mode.setTypefield(m.getTypefield());
					mode.setTypetableName(m.getTypetableName());
				}
				page = modeBindDao.Mode_getpage(mode.getBindtypeid(), mode
						.getId(), mode.getTypeid());

			} else if (mode.getBindtypeid() == 2) {// 类别绑定
				if (mode.getTypebindId() <= 0) {
					setElmessage("类别绑定-类别参数值错误");
				} else {
					modetypeid = mode.getTypebindId();
					Mode m = modeBindDao.Mode_getmodebyID(mode.getId());
					if (m == null) {
						setElmessage("类别绑定-类别参数值错误");
					} else if (m.getTypetableName() == null
							|| m.getTypetableName().equals("")) {// 如果没有类别
						setElmessage("类别绑定-该模块没有类别信息");
					} else if (!modeBindDao.checknode(m.getTypetableName(),
							mode.getTypebindId())) {
						setElmessage("类别表不存在或该类别不存在");
					} else {
						page = modeBindDao.Mode_gettypepage(mode.getId(), mode
								.getTypebindId());
						if (page == null || page.equals("")) {// 如果没找到该类别的绑定信息，或者没有绑定
							// 1.在该模块的类别中查找该类别的所有父节点集合;
							if (mode.getTypebindId() != 1) {// 如果不是根节点
								List<TreeNode> list = modeBindDao
										.Mode_getnodeallparent(m
												.getTypetableName(), mode
												.getTypebindId());
								if (list != null && list.size() >= 1) {
									for (TreeNode treeNode : list) {// 循环集合在绑定信息表里查找是否有已经绑定且允许继承的父ID，并
										page = modeBindDao
												.Mode_getentendtypepage(mode
														.getId(), treeNode
														.getId());
										if (page != null && !page.equals("")) {// 如果找到可以继承的父ID
											mode.setTypetableName(m
													.getTypetableName());

											return "mode_action_success";
										}
									}
								}
							}
							setElmessage("该类别未绑定模板");
						} else {
							mode.setTypetableName(m.getTypetableName());
							return "mode_action_success";
						}
					}
				}
			} else {

				setElmessage("参数值错误");
			}
		} else {

			setElmessage("参数不足");

		}
		if (page == null || page.equals("")) {

			page = "error.jsp";
		}
		return "mode_action_success";
	}

	/**
	 * 上传模板
	 * 
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String mode_updateDemo() throws ElException, Exception {
		if (st == null) {
			setElmessage("文件不存在");
			return "mode_updateDemo";
		}
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "mode_updateDemo";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			if (!ext.equals("jsp")) {
				setElmessage("模板文件格式只能为jsp！");
				return "mode_updateDemo";
			}
			J2EEFileUtil.upload_xianzhong(st, ext, "mode", MD5
					.crypt(stFileName));

			modeBindDao.Mode_modeupload(MD5.crypt(stFileName) + "." + ext,
					stFileName);

			setElmessage("上传成功！");
		}

		return "mode_updateDemo";
	}

	/**
	 * 上传模板页面
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_updateDemoInit() throws ElException {
		File file = new File(ServletActionContext.getServletContext()
				.getRealPath("/")
				+ "mode");
		if (!file.exists()) {

			return "success";

		}
		if (file == null) {

			return "success";

		}
		String test[];
		test = file.list();
		if (test == null && test.length < 1) {

			return "success";
		}
		String strfilename = "";
		for (int i = 0; i < test.length; i++) {
			if (i == 0)
				strfilename += "'" + test[i] + "'";
			else
				strfilename += ",'" + test[i] + "'";
		}
		tList = modeBindDao.mode_modepageList(999999, 1, strfilename);
		if (tList != null) {
			for (Template t : tList) {
				strname += t.getName();
			}
		}

		return "success";
	}

	/**
	 * 下载模板页面
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_downloadDemoInit() throws ElException {
		//
		File file = new File(ServletActionContext.getServletContext()
				.getRealPath("/")
				+ "mode");
		if (!file.exists()) {

			return "success";

		}
		if (file == null) {

			return "success";

		}
		String test[];
		test = file.list();
		if (test == null && test.length < 1) {

			return "success";
		}
		String strfilename = "";
		for (int i = 0; i < test.length; i++) {
			if (i == 0)
				strfilename += "'" + test[i] + "'";
			else
				strfilename += ",'" + test[i] + "'";
		}
		tList = modeBindDao.mode_modepageList(getPageNow(), getPageSize(),
				strfilename);
		count = modeBindDao.mode_modepageListcount(strfilename);
		return "success";
	}

	// 获得下载文件的内容
	public InputStream getInputStream() throws Exception {

		String dir = ServletActionContext.getServletContext().getRealPath("/")
				+ "mode";
		File file = new File(dir, template.getTrueName());
		if (file.exists()) {
			// 下载文件
			return new FileInputStream(file);
			// 和 Servlet 中不一样，这里我们不需对输出的中文转码为 ISO8859-1
			// 将内容(Struts2 文件下载测试)直接写入文件,下载的文件名必须是文本(txt)类型
			// return new ByteArrayInputStream("Struts2 文件下载测试".getBytes());
		}
		return null;
	}

	// 对于配置中的 ${fileName}, 获得下载保存时的文件名
	public String getFileName() {
		String fileName = template.getName();
		try {
			// 中文文件名也是需要转码为 ISO8859-1，否则乱码
			return new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			return "muban.jsp";
		}
	}

	// 调用的下载文件方法
	public String down() throws ElException {
		template = modeBindDao.Mode_modeupload(template.getId());
		return "down";
	}

	/**
	 * 得到指定类别表的json数据
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mode_treeviwe() throws ElException {
		// 得到当前模块的类别绑定信息
		if(tableName!=null && !tableName.equals("")){
			// 得到类别树
			treeNodes = modeBindDao.epLibTree(0, tableName, -1, true);
			GsonBuilder builder = new GsonBuilder();
			builder.excludeFieldsWithoutExposeAnnotation();
			Gson gson = builder.create();
			String jsonsorderField = null;
			if (treeNodes != null) {
				jsonsorderField = gson.toJson(treeNodes);
			}
			json = "[" + jsonsorderField + "]";
		}
		
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = resp.getWriter();
			localPrintWriter.println(json);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
