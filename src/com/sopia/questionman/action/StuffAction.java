package com.sopia.questionman.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.cms.IoUtil;
import com.sopia.common.ContinueFTP;
import com.sopia.common.ConvertWmv;
import com.sopia.common.ElException;
import com.sopia.common.FileUtils;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.Office2pdf;
import com.sopia.common.OfficeJpgUtil;
import com.sopia.common.SwfUtil;
import com.sopia.common.SystemConf;
import com.sopia.common.SystemConfOp;
import com.sopia.common.ZipUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.questionman.entities.StuffOfficeJpg;
import com.sopia.questionman.entities.StuffQuery;

public class StuffAction extends BaseAction {
	private StuffDao stuffDao;
	private File st;
	private String stFileName;
	private String sfContentType;
	private int sublibs;
	private StuffLib qstuff;
	private StuffLib qpstuff;
	private StuffLib stuffTree;
	private StuffLib stuffSharedTree;
	private List<StuffLib> qstuffs;
	private String stfilename;
	private int st_type;
	private SystemConf sysconf;
	private int isSeach;
	private StuffQuery stuffQuery;
	private SwfUtil swfUtil;
	private String fileName;//大文件上传的本地文件位置
	private InputStream inputStream;
	private String downFileName;
	private int isWbrowse;// 是否window浏览方式
	public static Map<String, String> mapSess=new HashMap<String, String>(); //存放断点的标记
	private String updateName;//重命名文件

	private List<StuffLib> qstuffs_video; //视频资源
	private List<StuffLib> qstuffs_txt;//文档资源
	private List<StuffLib> qstuffs_image; //图片资源
	private int previousid; 
	private int nextid;
	private List<StuffLib> stuffs;//
	private List<StuffOfficeJpg> stuffOfficeJpgs;
	
	
	public List<StuffOfficeJpg> getStuffOfficeJpgs() {
		return stuffOfficeJpgs;
	}

	public void setStuffOfficeJpgs(List<StuffOfficeJpg> stuffOfficeJpgs) {
		this.stuffOfficeJpgs = stuffOfficeJpgs;
	}

	public List<StuffLib> getStuffs() {
		return stuffs;
	}

	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}

	public int getPreviousid() {
		return previousid;
	}

	public void setPreviousid(int previousid) {
		this.previousid = previousid;
	}

	public int getNextid() {
		return nextid;
	}

	public void setNextid(int nextid) {
		this.nextid = nextid;
	}

	public int getIsWbrowse() {
		return isWbrowse;
	}

	public void setIsWbrowse(int isWbrowse) {
		this.isWbrowse = isWbrowse;
	}

	public String question_stuff_mylist() throws ElException {
		// if (0 == getPageSize())
		// getPageSize() = 10;
		qstuffs = stuffDao.getStuffs(qstuff,
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = stuffDao.getStuffsCount(qstuff,
				getSessionIntValue(ElConstants.SESSION_USERID));
		return "question_stuff_mylist";
	}

	/**
	 * 文件下载
	 * 
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String question_stuffDownload() throws ElException, Exception {
		try {
			// qstuff = stuffDao.getStuffbyId(qstuff.getId(),
			// 0);//第2个参数为文件上传者（0代表不限）
			qstuff = stuffDao.getStuffbyId2(qstuff.getId(), 0);
			// qstuff.getFileext());
			this.downFileName = "/elstuffs/" + qstuff.getPath() + "."
					+ qstuff.getFileext();
			String path = ServletActionContext.getServletContext().getRealPath(
					downFileName);
			// downFileName = qstuff.getId()+"."+qstuff.getFileext();
			downFileName = qstuff.getTitle() + "." + qstuff.getFileext();
			downFileName = java.net.URLEncoder.encode(downFileName, "UTF-8");// new
																				// String(downFileName.getBytes(),
																				// "ISO8859-1");
			try {
				inputStream = new FileInputStream(path);
			} catch (Exception e) {
				// logger.error("文档下载失败", e);
				throw new ElException("下载素材出错", e);
			}
		} catch (Exception e) {
			// logger.error("文档下载失败", e);
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "fileDownload";
	}

	public String getImageStream() throws ElException {
		try {
			qstuff = stuffDao.getStuffbyId2(qstuff.getId(), 0);
			this.downFileName = "/elstuffs/" + qstuff.getPath() + "."
					+ qstuff.getFileext();
			String path = ServletActionContext.getServletContext().getRealPath(
					downFileName);
			downFileName = qstuff.getTitle() + "." + qstuff.getFileext();
			downFileName = new String(downFileName.getBytes(), "ISO8859-1");
			try {
				inputStream = new FileInputStream(path);
				// BufferedImage image=javax.imageio.ImageIO.read(inputStream);
				// System.out.println(image.getHeight());
				// image.getGraphics();
				// ImageIO.
				// BufferedImage image=new BufferedImage(10,10,image);
				// image.getImage().getSource();
			} catch (Exception e) {
				throw new ElException("图片显示出错", e);
			}
		} catch (Exception e) {
			// logger.error("文档下载失败", e);
			setElmessage("图片不存在或其他原因导致不能显示！");
			return "error";
		}
		return "imageStream";
	}

	/**
	 * 文件预览
	 * 
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String question_stuffPreview() throws ElException, Exception {
		qstuff = stuffDao.getStuffbyId2(qstuff.getId(), 0);
		String systempath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String serverpath = "";
		if (SystemConfOp.getIntValue(ElConstants.STUFF_URL_LOCAL) == 0) {
			serverpath = SystemConfOp.getStuffUrl();
		} else {
			serverpath = SystemConfOp.getValue(ElConstants.STUFF_URL);
			if (serverpath == null || serverpath.equals("")) {
				// 获取项目路径
				serverpath = getRequest().getRequestURL().toString();
			}
		}
		System.out.println(serverpath);
		// 判断文件是否存在
		File file = new File(systempath + "/swffile/" + qstuff.getId() + ".swf");
		if (file.exists()) {
			qstuff.setStuff_path(serverpath + "swffile/" + file.getName());
		} else {
			// 当图片(或者其他)处理
			String filePath = qstuff.getPath() + "." + qstuff.getFileext();
			qstuff.setStuff_path(serverpath + "elstuffs" + filePath);
			System.out.println("stuff_path:"+serverpath + "elstuffs" + filePath);
			String fext = qstuff.getFileext();
			if ("jpg".equals(fext) || "jpeg".equals(fext) || "gif".equals(fext)
					|| "png".equals(fext) || "tiff".equals(fext)) {
				if (isSeach == 1) {

				} else {
					qstuffs = stuffDao.listStuffs(qstuff.getParent(), "", "",
							"jpg,jpeg,gif,png,tiff");
					qpstuff = new StuffLib();
					qpstuff.setStuff_path(stuffDao.setStuffPath(qstuff
							.getParent().getId()));
					qstuff.setLoca(stuffDao.getStuffLoca(qstuff.getId(), qstuff
							.getParent().getId(), "jpg,jpeg,gif,png,tiff"));
					return "imageStuffPreview";
				}
			}
		}
		//office文档转换的缩略图图片集合
		stuffOfficeJpgs = OfficeJpgUtil.getStuffOfficeJpgs();
		return "question_stuffPreview";
	}

	public String question_stuffdelete() throws ElException, Exception {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			String isDel = getRequest().getParameter("isDel");// 判断是否真删除
			if (null != qstuffs) {
				for (int i = 0; i < qstuffs.size(); i++) {
					int id = qstuffs.get(i).getId();
					// int userid =
					// getSessionIntValue(ElConstants.SESSION_USERID);
					if (null != isDel && "1".equals(isDel)) {
						deleteF2(id);
					} else {
						deleteF(id);
					}
					qstuff = stuffDao.getStuffbyId(id, 0);
					if (qstuff != null) {
						ElLogger.busilogger(
								getSessionIntValue(ElConstants.SESSION_USERID),
								ElLoggerConstants.LOG_MOD_STUFF,
								ElLoggerConstants.LOG_TYPE_DELETE, qstuff
										.getTitle(),
								ElLoggerConstants.LOG_RES_SUCC, qstuff.getId());
					}
				}
			}
		} else {
			if (null != qstuffs) {
				int n = 0;
				for (int i = 0; i < qstuffs.size(); i++) {
					int id = qstuffs.get(i).getId();
					qstuff = stuffDao.getStuffbyId(id, 0);
					// 根据资源创建者，判断是否有权删除（当前只有超级管理员和自己创建的资源能删除,还有创建者是他所管理的部门下的人员）
					if (qstuff.getOwner().getId() != getSessionIntValue(ElConstants.SESSION_USERID)) {
						if (!stuffDao.checkUserIsdelStuff(
								getSessionIntValue(ElConstants.SESSION_USERID),
								qstuff)) {
							n++;
							continue;
						}
					}
					deleteF(id);
					if (qstuff != null) {
						ElLogger.busilogger(
								getSessionIntValue(ElConstants.SESSION_USERID),
								ElLoggerConstants.LOG_MOD_STUFF,
								ElLoggerConstants.LOG_TYPE_DELETE, qstuff
										.getTitle(),
								ElLoggerConstants.LOG_RES_SUCC, qstuff.getId());
					}
				}
				if (n > 0) {
					String msg = "您的操作有" + n
							+ "条未执行成功！（非超级管理员只能删除自己和自己管理的部门下的人员创建的资源）";
					setElmessage(URLEncoder.encode(URLEncoder.encode(msg,
							"UTF-8"), "UTF-8"));
				}
			}
			// setElmessage("您无权限删除系统资源！");
			// return "error";
		}
		// qstuffs = stuffDao.getStuffs(qstuff,
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		return "question_stuffList";
	}

	public String question_stuffPath() throws ElException {
		qstuff = stuffDao.getStuffbyId(qstuff.getId(), 0);
		stuffDao.setStuffParent(qstuff);
		if (qstuff == null || qstuff.getId() <= 0) {
			printMsg("{'path':'此地址文件在系统数据库中未存储！'}");
		} else
			printMsg("{'path':'" + qstuff.getPathAll() + "'}");
		return null;
	}

	/**
	 * 素材重命名
	 * 
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException
	 */
	public String qstuffRename() throws ElException,
			UnsupportedEncodingException {
		String title = qstuff.getTitle();
		qstuff = stuffDao.getStuffbyId(qstuff.getId(), 0);
		qstuff.setTitle(title);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			stuffDao.alter(qstuff);
		} else {
			// 检测是否具有重命名的权限
			// 根据资源创建者，判断是否有权删除（当前只有超级管理员和自己创建的资源能删除,还有创建者是他所管理的部门下的人员）
			if (qstuff.getOwner().getId() != getSessionIntValue(ElConstants.SESSION_USERID)) {
				if (!stuffDao.checkUserIsdelStuff(
						getSessionIntValue(ElConstants.SESSION_USERID), qstuff)) {
					String msg = "您的操作未执行成功！（非超级管理员只能重命名自己和自己管理的部门下的人员创建的资源）";
					setElmessage(URLEncoder.encode(URLEncoder.encode(msg,
							"UTF-8"), "UTF-8"));
				} else {
					stuffDao.alter(qstuff);
				}
			} else {
				stuffDao.alter(qstuff);
			}
		}
		//重新加载服务中的缩略图
		OfficeJpgUtil.load();
		return "question_stuffList";
	}

	public String question_stuffList() throws ElException,
			UnsupportedEncodingException {
		sysconf = new SystemConf();
		sysconf.setStuff_size(SystemConfOp.getIntValue(ElConstants.STUFF_SIZE));
		if (!SystemConfOp.getBooleanValue(ElConstants.STUFF_OP)
				&& !"无记录".equals(SystemConfOp.getValue(ElConstants.STUFF_URL))
				&& !"".equals(SystemConfOp.getValue(ElConstants.STUFF_URL)
						.trim())) {
			// !stuffDao.checkUrlIsLocal(SystemConfOp
			// .getValue(ElConstants.STUFF_URL), getRequest()
			// .getContextPath(), getRequest().getServerName())
			if ("0".equals(SystemConfOp.getValue(ElConstants.STUFF_URL_LOCAL))) {
				ELUser u = userDao
						.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
				stfilename = SystemConfOp.getValue(ElConstants.STUFF_URL)
						+ "/question_stuffList.action?username="
						+ u.getUsername() + "&password=" + u.getPassword()
						+ "&stuffcode=" + getSession().getId();
				// + getSessionIntValue(ElConstants.SESSION_USERID);
				return "to_stuff_url";
			}
		}
		qpstuff = qpstuff == null || qpstuff.getId() <= 0 ? new StuffLib(0, "根")
				: stuffDao.getStuffbyId(qpstuff.getId(), 0);
		qpstuff.setIsgrant(stuffDao.checkStuffidisGrant(qpstuff.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID)));
		sublibs = SystemConfOp.getIntValue(ElConstants.STUFF_SIZE);
		qstuff = qstuff == null ? new StuffLib() : qstuff;
		if (qpstuff != null && qpstuff.getType() == 7) {
			String thepath = (qstuff == null || qstuff.getStuff_path() == null) ? ""
					: qstuff.getStuff_path();
			stuffDao.setStuffParent(qpstuff);
			qstuffs = ZipUtil.listStuffs(ServletActionContext
					.getServletContext().getRealPath("/elstuffs/")
					+ qpstuff.getPath() + "/" + thepath);
			// stuffTree = stuffDao
			// .getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
			// stuffSharedTree = stuffDao.listFolderShared();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
				stuffTree = stuffDao
						.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
				stuffSharedTree = stuffDao.listFolderShared();
			} else {
				stuffTree = stuffDao.getStuffFolderTree();
				stuffSharedTree = stuffDao.listFolderShared();
			}
			qpstuff.setStuff_path(stuffDao.setStuffPath(qpstuff.getId()));
			qpstuff.setStuff_path(qpstuff.getStuff_path() + thepath);
			qpstuff.setPathZh1(qpstuff.getPathZh(thepath));
			return "question_stuffList";
		} else {
			// if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			// qstuffs =
			// stuffDao.listMyStuffs(qpstuff,getSessionIntValue(ElConstants.SESSION_USERID));
			// } else
			// qpstuff = qpstuff == null || qpstuff.getId() == 0 ? new
			// StuffLib(0,
			// "根") : stuffDao.getStuffbyId(qpstuff.getId(), 0);
			// stuffDao.setStuffParent(qpstuff);
			qpstuff = qpstuff == null ? new StuffLib() : qpstuff;
			qpstuff.setUsers(stuffDao.getStuffOpUsers(qpstuff.getId()));
			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
				stuffTree = stuffDao
						.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
				stuffSharedTree = stuffDao.listFolderShared();
				if (qpstuff != null && qpstuff.getId() != 0) {
					qstuffs = stuffDao.listStuffs(qpstuff, "", "");
					qpstuff.setParent(new StuffLib(0, ""));
					if (st_type == 1)
						stuffDao.setStuffParent(qpstuff, stuffSharedTree
								.getChilds());
					else
						stuffDao.setStuffParent(qpstuff, stuffTree.getChilds());
					stuffSharedTree = stuffDao.listFolderShared();
				} else {
					if (st_type == 1)
						qstuffs = stuffSharedTree != null ? stuffSharedTree
								.getChilds() : new ArrayList<StuffLib>();
					else
						qstuffs = stuffTree.getChilds();
				}
			} else {// 超级管理员树-列表
				stuffTree = stuffDao.getStuffFolderTree();
				stuffSharedTree = stuffDao.listFolderShared();
				qstuffs = stuffDao.listStuffs(qpstuff, "", "");
				if (st_type == 1) {
					qpstuff.setParent(new StuffLib(0, ""));
					if (qpstuff.getId() == 0) {
						qstuffs = stuffSharedTree != null ? stuffSharedTree
								.getChilds() : new ArrayList<StuffLib>();
					}
					// stuffDao.setStuffParent(qpstuff, stuffSharedTree
					// .getChilds());
					stuffDao.setStuffParent(qpstuff, qstuffs);
				} else
					stuffDao.setStuffParent(qpstuff);

			}
			if (qpstuff != null) {
				qpstuff.setStuff_path(stuffDao.setStuffPath(qpstuff.getId()));// 设置资源路径
			}
			if (isSeach == 1) {
				if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
					qstuffs = stuffDao.listSeachStuffs(null, stuffQuery);
				} else {
					String stuffIds = stuffDao.getStuffIds(stuffTree);
					String sharedIds = stuffDao.getStuffIds(stuffSharedTree);
					if (sharedIds != null && !"".equals(sharedIds)) {
						stuffIds += "," + stuffDao.getStuffIds(stuffSharedTree);
					}
					qstuffs = stuffDao.listSeachStuffs(stuffIds, stuffQuery);
				}
			}
			if (getElmessage() != null && !getElmessage().equals("")) {
				setElmessage(URLDecoder.decode(getElmessage(), "UTF-8"));
			}
			// 判断客户端用的神马浏览器
			String clientType = getRequest().getHeader("user-agent")
					.toLowerCase();
			if (clientType.indexOf("android") > 0
					|| clientType.indexOf("iphone") > 0
					|| clientType.indexOf("ios") > 0) {
				getRequest().setAttribute("clientType", "movephone");
			}
			// 判断浏览方式
			if (isWbrowse == 1) {
				return "question_stuffList_win";
			}
			
			return "question_stuffList";
		}
	}
	/**
	 * 首页资源库
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String question_stuffList_index() throws ElException,UnsupportedEncodingException {
		
			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
				stuffTree = stuffDao
						.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
				stuffSharedTree = stuffDao.listFolderShared();
			} else {
				stuffTree = stuffDao.getStuffFolderTree();
				stuffSharedTree = stuffDao.listFolderShared();
			}
			if (isSeach == 1) {
				if(stuffQuery.getStuffExt().equals("0")){
					stuffQuery.setStuffExt("");
				}
				if(stuffQuery.getStuffExt().equals("1")){
					stuffQuery.setStuffExt("wmv','rmvb','flv','swf','asf','mpg','avi");//视频
				}
				if(stuffQuery.getStuffExt().equals("2")){
					stuffQuery.setStuffExt("mp3','wav");//音频
				}
				if(stuffQuery.getStuffExt().equals("3")){
					stuffQuery.setStuffExt("doc','docx','ppt','pptx','xls','xlsx','pdf','txt");//文档
				}
				if(stuffQuery.getStuffExt().equals("4")){
					stuffQuery.setStuffExt("gif','jpg','png','tiff");//图片
				}
				if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
					qstuffs = stuffDao.listSeachStuffs(null, stuffQuery,getPageNow(),getPageSize());
					
				} else {
					String stuffIds = stuffDao.getStuffIds(stuffTree);
					String sharedIds = stuffDao.getStuffIds(stuffSharedTree);
					if (sharedIds != null && !"".equals(sharedIds)) {
						stuffIds += "," + stuffDao.getStuffIds(stuffSharedTree);
					}
					qstuffs = stuffDao.listSeachStuffs(stuffIds, stuffQuery);
				}
				count = stuffDao.listSeachStuffsSize(null, stuffQuery);
				stuffs = stuffDao.listStuffs(10, 0);  //最新推荐资源
				return "question_stuffList_search";
			}else{
				qpstuff = new StuffLib(0, "根");
				String video = "'wmv','rmvb','flv','swf','asf','mpg','avi'";
				qstuffs_video = stuffDao.listStuffs(qpstuff, video); //视频资源
				
				String txt = "'doc','docx','ppt','pptx','xls','xlsx','pdf','txt'";
				qstuffs_txt = stuffDao.listStuffs(qpstuff, txt) ;//文档资源
				
				String image = "'gif','jpg','png','tiff'";
				qstuffs_image = stuffDao.listStuffs(qpstuff, image); //图片资源
				
			}
			
			
			if (getElmessage() != null && !getElmessage().equals("")) {
				setElmessage(URLDecoder.decode(getElmessage(), "UTF-8"));
			}
			// 判断客户端用的神马浏览器
			String clientType = getRequest().getHeader("user-agent")
					.toLowerCase();
			if (clientType.indexOf("android") > 0
					|| clientType.indexOf("iphone") > 0
					|| clientType.indexOf("ios") > 0) {
				getRequest().setAttribute("clientType", "movephone");
			}
			// 判断浏览方式
			if (isWbrowse == 1) {
				return "question_stuffList_win";
			}
			return "question_stuffList_index";
		
	}
	/**
	 * 首页资源库内容查看
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String question_stuffPreview_index() throws ElException, Exception {
		qstuff = stuffDao.getStuffbyId2(qstuff.getId(), 0);
		qstuffs = stuffDao.listStuffs(10, 0);  //最新推荐资源
		String form = "";
		if(qstuff.getFileext().equals("wmv")||qstuff.getFileext().equals("rmvb")||qstuff.getFileext().equals("flv")||qstuff.getFileext().equals("swf")||qstuff.getFileext().equals("asf")||qstuff.getFileext().equals("mpg")||qstuff.getFileext().equals("avi")){
			form = "'wmv','rmvb','flv','swf','asf','mpg','avi'";
		}
		if(qstuff.getFileext().equals("doc")||qstuff.getFileext().equals("docx")||qstuff.getFileext().equals("ppt")||qstuff.getFileext().equals("pptx")||qstuff.getFileext().equals("xls")||qstuff.getFileext().equals("xlsx")||qstuff.getFileext().equals("pdf")||qstuff.getFileext().equals("txt")){
			form = "'doc','docx','ppt','pptx','xls','xlsx','pdf','txt'";
		}
		if(qstuff.getFileext().equals("gif")||qstuff.getFileext().equals("jpg")||qstuff.getFileext().equals("png")||qstuff.getFileext().equals("tiff")){
			form = "'gif','jpg','png','tiff'";
		}
		previousid = stuffDao.getStuffId(qstuff.getId(),1,form);  // 1代表上一个，0代表下一个
		nextid = stuffDao.getStuffId(qstuff.getId(), 0,form);
		String systempath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String serverpath = "";
		if (SystemConfOp.getIntValue(ElConstants.STUFF_URL_LOCAL) == 0) {
			serverpath = SystemConfOp.getStuffUrl();
		} else {
			serverpath = SystemConfOp.getValue(ElConstants.STUFF_URL);
			if (serverpath == null || serverpath.equals("")) {
				// 获取项目路径
				serverpath = getRequest().getRequestURL().toString();
			}
		}
		System.out.println(serverpath);
		// 判断文件是否存在
		File file = new File(systempath + "/swffile/" + qstuff.getId() + ".swf");
		if (file.exists()) {
			qstuff.setStuff_path(serverpath + "swffile/" + file.getName());
		} else {
			// 当图片(或者其他)处理
			String filePath = qstuff.getPath() + "." + qstuff.getFileext();
			qstuff.setStuff_path(serverpath + "elstuffs" + filePath);
			System.out.println("stuff_path:"+serverpath + "elstuffs" + filePath);
			String fext = qstuff.getFileext();
			if ("jpg".equals(fext) || "jpeg".equals(fext) || "gif".equals(fext)
					|| "png".equals(fext) || "tiff".equals(fext)) {
				if (isSeach == 1) {

				} else {
					qstuffs = stuffDao.listStuffs(qstuff.getParent(), "", "",
							"jpg,jpeg,gif,png,tiff");
					qpstuff = new StuffLib();
					qpstuff.setStuff_path(stuffDao.setStuffPath(qstuff
							.getParent().getId()));
					qstuff.setLoca(stuffDao.getStuffLoca(qstuff.getId(), qstuff
							.getParent().getId(), "jpg,jpeg,gif,png,tiff"));
					return "imageStuffPreview";
				}
			}
		}
		return "question_stuffPreview_index";
	}
	/**
	 * 首页查询类别
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	
	public String stufftree()throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			stuffTree = stuffDao
					.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
			stuffSharedTree = stuffDao.listFolderShared();
		} else {
			stuffTree = stuffDao.getStuffFolderTree();
			stuffSharedTree = stuffDao.listFolderShared();
		}
		return "stufftree";
	}
	public String mess_getStuffLibInfoJson() throws ElException {
		qstuff = stuffDao.getStuffbyId(qstuff.getId(),0);
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"qstuff\":{\"id\":\"" + qstuff.getId()
					+ "\",\"title\":\"" + qstuff.getTitle() + "\"}}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
		//	logger.error("ajax 获取人员信息错误",e);
		}
		return null;
	}

	public String question_stuffuseradd() throws ElException, Exception {
		if (null != qpstuff.getUsers()) {
			for (int i = 0; i < qpstuff.getUsers().size(); i++) {
				if (!stuffDao.checkStuffOpUsers(qpstuff.getUsers().get(i)
						.getId(), qpstuff.getId()))
					stuffDao.addStuffOpusers(qpstuff.getUsers().get(i).getId(),
							qpstuff.getId());
			}
		}
		return "question_stuffuseradd_succ";
	}

	public String question_stuffshared() throws ElException, Exception {
		// 共享文件夹的时候 先要判断他的父节点有没有被共享的，如果没有就可以共享，然后取消他的所有子节点的共享状态
		if (!stuffDao.checkStuffIsShared(qpstuff.getId())
				|| qpstuff.getShared() == 0) {
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
				stuffDao.updateStuffChildShared(qpstuff.getId(), 0);
				stuffDao.setStuffShared(qpstuff.getId(), qpstuff.getShared());
			} else {
				qstuff = stuffDao.getStuffbyId2(qpstuff.getId(), 0);
				// 根据资源创建者，判断是否有权删除（当前只有超级管理员和自己创建的资源能删除,还有创建者是他所管理的部门下的人员）
				if (qstuff.getOwner().getId() != getSessionIntValue(ElConstants.SESSION_USERID)) {
					if (stuffDao.checkUserIsdelStuff(
							getSessionIntValue(ElConstants.SESSION_USERID),
							qstuff)) {
						stuffDao.updateStuffChildShared(qpstuff.getId(), 0);
						stuffDao.setStuffShared(qpstuff.getId(), qpstuff
								.getShared());
					} else {
						String msg = "您的操作未执行成功！（非超级管理员只能管理自己和自己管理的部门下的人员创建的资源）";
						setElmessage(URLEncoder.encode(URLEncoder.encode(msg,
								"UTF-8"), "UTF-8"));
					}
				} else {
					stuffDao.updateStuffChildShared(qpstuff.getId(), 0);
					stuffDao.setStuffShared(qpstuff.getId(), qpstuff
							.getShared());
				}
			}
		} else {
			setElmessage(URLEncoder.encode(URLEncoder.encode("该文件夹已经存在于共享列表",
					"UTF-8"), "UTF-8"));
		}
		return "question_stuffshared_succ";
	}

	public String question_stuffsizeset() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("无权限设置文件限制！");
			return "error";
		}
		SystemConfOp.setProperty(ElConstants.STUFF_SIZE, sysconf
				.getStuff_size());
		try {
			SystemConfOp.load();
		} catch (Exception e) {
			setElmessage("系统设置失败");
			return "error";
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_STUFF,
				ElLoggerConstants.LOG_TYPE_ALTER, "设置了文件上传限制："
						+ sysconf.getStuff_size() + "MB",
				ElLoggerConstants.LOG_RES_SUCC, 0);
		return "question_stuffsizeset_succ";
	}

	public String question_stufftoswf() throws ElException {
		try {
			// SystemConfOp.load();
			qstuff = stuffDao.getStuffbyId2(qstuff.getId(), 0);
			String msg = swfUtil.toSwf_(qstuff);
			if ("".equals(msg)) {
				stuffDao.updateStuffStatus(qstuff.getId(), 0);
			}
		} catch (Exception e) {
			setElmessage("转换成swf失败");
			return "error";
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_STUFF,
				ElLoggerConstants.LOG_TYPE_ALTER, "转换成swf",
				ElLoggerConstants.LOG_RES_SUCC, 0);
		return "question_stufftoswf_succ";
	}

	public String question_stuffwjjsizeset() throws ElException, Exception {
		// if(0!=stuffDao.getStuffOpStatus(qpstuff.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// getSessionIntValue(ElConstants.SESSION_ROLE),2)){
		// setElmessage("您无权限设置该文件夹！");
		// return "error";
		// }
		// 需求已改为 只有超级管理员可以修改文件夹大小
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
		// setElmessage("您无权限设置该文件夹！");
		// return "error";
		// }
		// 检测是否有修改文件夹大小的权限（非超级管理员只有被分配的文件夹中才能创建文件夹，解决共享文件夹中创建文件夹问题）
		if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1
				&& !stuffDao.checkUserIsaddStuff(
						getSessionIntValue(ElConstants.SESSION_USERID), qpstuff
								.getId())) {
			// 无权限
			String msg = "您的操作未执行成功！（非超级管理员只能更改自己管理的文件夹下面的文件夹大小）";
			setElmessage(URLEncoder.encode(URLEncoder.encode(msg, "UTF-8"),
					"UTF-8"));
			return "question_stuffwjjsizeset_succ";
		}
		qpstuff.setLength(qpstuff.getLength() * 1024 * 1024L);
		long psize = stuffDao.getStuffParentSize(qpstuff.getId());
		long mysize = qpstuff.getLength();
		qstuff = stuffDao.getStuffbyId(qpstuff.getId(), 0);
		if (qstuff == null) {
			setElmessage("找不到文件夹！");
			return "error";
		}
		long chsize = stuffDao.getStuffChildsSize(qstuff.getParent().getId())
				- qstuff.getLength();
		if (chsize >= 0 && psize >= 0 && (chsize + mysize > psize)) {
			setElmessage("该文件夹容量过了，请重新设置！");
			return "error";
		}
		stuffDao.setStuffsize(qpstuff.getId(), qpstuff.getLength());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_STUFF,
				ElLoggerConstants.LOG_TYPE_ALTER, qstuff.getTitle()
						+ " 文件夹大小设置为：" + qpstuff.getLength(),
				ElLoggerConstants.LOG_RES_SUCC, qstuff.getId());
		return "question_stuffwjjsizeset_succ";
	}

	private void deleteF(int id) throws ElException, Exception {
		StuffLib qst = stuffDao.getStuffbyId(id, 0);
		String title = qst.getTitle();
		if (qst.getType() == 5) {
			List<StuffLib> list = stuffDao.listStuffs(qst, "", "");
			if (list != null) {
				for (int j = 0; j < list.size(); j++) {
					deleteF(list.get(j).getId());
				}
			}
			stuffDao.setStuffParent(qst);// 只是设置了父对象，在这里 不知道有什么用...
			// J2EEFileUtil.deleteFolder("/elstuffs/" + qst.getPath() + "");
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
					ElLoggerConstants.LOG_RES_SUCC);
			stuffDao.deleteQs(id, 0);
			// --------stuffDao.get
		} else {
			StuffLib qpst = qst.getParent();
			stuffDao.setStuffParent(qpst);
			// J2EEFileUtil.deleteFile("/elstuffs/" + qpst.getPath(), id +
			// "",qst.getFileext());
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
					ElLoggerConstants.LOG_RES_SUCC);
			stuffDao.deleteQs(id, 0);
		}
	}

	/**
	 * 真删除
	 * 
	 * @param id
	 * @throws ElException
	 * @throws Exception
	 */
	private void deleteF2(int id) throws ElException, Exception {
		StuffLib qst = stuffDao.getStuffbyId(id, 0);
		String title = qst.getTitle();
		if (qst.getType() == 5) {
			List<StuffLib> list = stuffDao.listStuffs(qst, "", "");
			if (list != null) {
				for (int j = 0; j < list.size(); j++) {
					deleteF2(list.get(j).getId());
				}
			}
			stuffDao.setStuffParent(qst);// 只是设置了父对象，在这里 不知道有什么用...
			J2EEFileUtil.deleteFolder("/elstuffs/" + qst.getPath() + "");

			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
					ElLoggerConstants.LOG_RES_SUCC);
			stuffDao.deleteQs(id, 0);
			// --------stuffDao.get
		} else {
			StuffLib qpst = qst.getParent();
			stuffDao.setStuffParent(qpst);
			J2EEFileUtil.deleteFile("/elstuffs/" + qpst.getPath(), id + "", qst
					.getFileext());

			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
					ElLoggerConstants.LOG_RES_SUCC);
			stuffDao.deleteQs(id, 0);
		}
	}

	public String question_stuffaddInit() throws ElException {

		return "question_stuffadd";
	}

	public String question_stuffadd() throws ElException, Exception {
		
		System.out.println("qstufff的id"+qstuff.getParent().getId());
		System.out.println("文件的地址："+fileName);
		ActionContext ac=ActionContext.getContext();
		sublibs = SystemConfOp.getIntValue(ElConstants.STUFF_SIZE);
		if(SystemConfOp
				.getBooleanValue(ElConstants.DUANDIAN_NEED_XC)){
			if(null!=fileName){
				File f=new File(fileName);
				if(!f.isFile()){
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_STUFF,
							ElLoggerConstants.LOG_TYPE_ADD, "请确定上传文件，不是目录。",
							ElLoggerConstants.LOG_RES_ERR);
					ac.getSession().put("result", "请确定上传文件，不是目录");
					return "ftp_success";
				}
				if (f.length() > SystemConfOp.getIntValue(ElConstants.STUFF_SIZE) * 1024l * 1024
						&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_STUFF,
							ElLoggerConstants.LOG_TYPE_ADD, "上传的文件过大！",
							ElLoggerConstants.LOG_RES_ERR);
					ac.getSession().put("result", "上传的文件过大");
					return "ftp_success";
				} else {
					long psize = stuffDao.getStuffbyId(qstuff.getParent().getId(),
							0).getLength();
					long mysize = f.length();
					// qpstuff = questionDao.getStuffbyId(qpstuff.getId(), 0);
					long chsize = stuffDao.getStuffChildsSize(qstuff.getParent()
							.getId());
					if (chsize >= 0 && psize >= 0 && (chsize + mysize > psize)) {
						ElLogger.busilogger(
								getSessionIntValue(ElConstants.SESSION_USERID),
								ElLoggerConstants.LOG_MOD_STUFF,
								ElLoggerConstants.LOG_TYPE_ADD, "文件夹已满，请与系统管理员联系！",
								ElLoggerConstants.LOG_RES_ERR);
						ac.getSession().put("result", "文件夹已满，请与系统管理员联系！");
						return "ftp_success";
					} else {
						String ext = J2EEFileUtil.getExtention(fileName);
						String name=f.getName();
						
						if (null != name)
							qstuff.setTitle(name.substring(0, name
									.lastIndexOf(".")));
						else
							qstuff.setTitle("未命名");
						if (ext != null && ext.equals("zip")) {
							qstuff.setType(6);
						}
						qstuff.setFileext(ext.toLowerCase());
						qstuff.setOwner(new ELUser(
								getSessionIntValue(ElConstants.SESSION_USERID)));
						qstuff.setLength(f.length());
						if(mapSess.size()!=0){
							String path=mapSess.get(f.getPath());
							if(path!=null){
								Properties ps = new Properties();
								FileInputStream fis=new FileInputStream(J2EEFileUtil.getRealPath("/")+"WEB-INF/config/ftp.properties");
								ps.load(fis);
								fis.close();
								String ip=ps.getProperty("ip");
								String port=ps.getProperty("port");
								String username=ps.getProperty("username");
								String password=ps.getProperty("password");
								ContinueFTP ftp=new ContinueFTP();
								ftp.connect(ip, Integer.parseInt(port), username, password);
								ftp.upload(fileName,qstuff.getParent().getId()+ "/"+ path+"."+ext, "p");
								ftp.disconnect();
						     }
						}else{
							int id = stuffDao.addQstuff(qstuff);
							qpstuff = qstuff.getParent();
							stuffDao.setStuffParent(qpstuff);
							mapSess.put(fileName,String.valueOf(id) );
							J2EEFileUtil.upload(f, ext, "/elstuffs/"
									+ qpstuff.getPath(), id + "");
							ElLogger.busilogger(
									getSessionIntValue(ElConstants.SESSION_USERID),
									ElLoggerConstants.LOG_MOD_STUFF,
									ElLoggerConstants.LOG_TYPE_ADD, qstuff.getTitle()
											+ " 上传成功！", ElLoggerConstants.LOG_RES_SUCC,
									qstuff.getId());
							
							// 判断系统是否开启视频转换
							if (SystemConfOp
									.getBooleanValue(ElConstants.SHIPIN_NEED_ZH)) {
								//判断上传的文件是否是视频文件asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv，wmv9，rm，rmvb
								if (ext.equals("asx") || ext.equals("asf") || ext.equals("mpg")
										|| ext.equals("3gp") || ext.equals("mp4")|| ext.equals("rm")
										|| ext.equals("mov")|| ext.equals("avi")|| ext.equals("rmvb")
										|| ext.equals("flv")|| ext.equals("wmv9")) {
									ConvertWmv convertWmv=new ConvertWmv();// 视频转换工具
									String ffpath=J2EEFileUtil.getRealPath("/")+"tools/ffmpeg.exe";
									String mepath=J2EEFileUtil.getRealPath("/")+"tools/mencoder.exe";
									String inputFile=J2EEFileUtil.getRealPath("/")+ "elstuffs/"+ qstuff.getParent().getId()+ "/"+ id+"."+ext;
									String outputFile=J2EEFileUtil.getRealPath("/")+ "elstuffs/"+ qstuff.getParent().getId()+ "/"+ id+".wmv";
									boolean b=convertWmv.convert(ffpath,mepath, inputFile, outputFile);
									if(b){
										File file=new File(outputFile);
										if(file.exists()&&file.length()>0){
											// 继续在数据库中插入一条记录
											StuffLib qstuff_ = qstuff;
											// 设置文件类型为wmv
											qstuff_.setFileext("wmv");
											// 设置wmv文件大小
											qstuff_.setLength(file.length());
											int transformid = stuffDao.addQstuff(qstuff_);
											System.out.println("获取上传地址："+ transformid);
										}
									}
		              
								}
							}
							// 判断系统是否设置了OFFICE和PDF上传需要转换
							// 暂时处理了doc、docx、xls、xlsx、pdf、ppt、txt七种文件
							if (SystemConfOp
									.getBooleanValue(ElConstants.FILEUPLOAD_NEED_ZH)) {
								String msg = swfUtil.toSwf_(qstuff);
								//上传完成后
								//更新数据库信息
								if (ext.equals("doc") || ext.equals("docx")
										|| ext.equals("xls") || ext.equals("xlsx")
										|| ext.equals("pdf") || ext.equals("ppt") 
										|| ext.equals("txt")) {
									File file = null;
									
									// 获取转换后的缩略图-小图，存在，更新数据库
									file = new File(J2EEFileUtil.getRealPath("/")
											+ "elstuffs/" + qstuff.getParent().getId()
											+ "/" + id + "/" + id + "-1-small.jpg");
									if(file.exists() && file.isFile()&& file.length() > 0){
										stuffDao.updateStuffJpg(id);
									}
									
									// 获取转换后的swf文件
									file = new File(J2EEFileUtil.getRealPath("/")
											+ "elstuffs/" + qstuff.getParent().getId()
											+ "/" + id + ".swf");
									// 判断文件是否存在，并且文件大小是否大于0
									if (swfUtil.checkSwfFileIsExist(file)
											&& file.length() > 0) {
										// 继续在数据库中插入一条记录
										StuffLib qstuff_ = qstuff;
										// 设置文件类型为swf
										qstuff_.setFileext("swf");
										// 设置swf文件大小
										qstuff_.setLength(file.length());
										// 设置来自转换
										qstuff_.setFromchange(1);
										int transformid = stuffDao.addQstuff(qstuff_);
										// 因为要保存两条数据，为了方便，复制swf
										new IoUtil().copyFile(J2EEFileUtil
												.getRealPath("/")
												+ "elstuffs/"
												+ qstuff.getParent().getId()
												+ "/"
												+ id
												+ ".swf", J2EEFileUtil.getRealPath("/")
												+ "elstuffs/"
												+ qstuff.getParent().getId() + "/"
												+ transformid + ".swf");
									}
									//重新加载服务中的缩略图
									OfficeJpgUtil.load();
								}
							}
						}
						
						System.out.println("后缀："+ext);
						System.out.println("文件名："+qstuff.getTitle());
					}
					ac.getSession().put("result", "文件上传成功");
					return "ftp_success";
				}
			}
		}
		else if (null != st) {
			if (qstuff == null || qstuff.getParent() == null
					|| qstuff.getParent().getId() == 0) {
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_STUFF,
						ElLoggerConstants.LOG_TYPE_ADD, "请不要在根目录下上传文件。",
						ElLoggerConstants.LOG_RES_ERR);
				printMsg("{'msg':'" + "请不要在根目录下上传文件。" + "'}");
				return null;
			}
			if (st.length() > SystemConfOp.getIntValue(ElConstants.STUFF_SIZE) * 1024l * 1024
					&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_STUFF,
						ElLoggerConstants.LOG_TYPE_ADD, "上传的文件过大！",
						ElLoggerConstants.LOG_RES_ERR);
				printMsg("{'msg':'" + "您上传的文件过大！" + "'}");
				return null;
			} else {
				long psize = stuffDao.getStuffbyId(qstuff.getParent().getId(),
						0).getLength();
				long mysize = st.length();
				// qpstuff = questionDao.getStuffbyId(qpstuff.getId(), 0);
				long chsize = stuffDao.getStuffChildsSize(qstuff.getParent()
						.getId());
				if (chsize >= 0 && psize >= 0 && (chsize + mysize > psize)) {
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_STUFF,
							ElLoggerConstants.LOG_TYPE_ADD, "文件夹已满，请与系统管理员联系！",
							ElLoggerConstants.LOG_RES_ERR);
					printMsg("{'msg':'" + "文件夹已满，请与系统管理员联系！" + "'}");
					return null;
				} else {
					// qstuff = qstuff != null ? qstuff : new
					// StuffLib();//上面有为null的处理，这里无意义
					
					String ext = J2EEFileUtil.getExtention(stFileName);
					if(null != stFileName){
						if(null==qstuff.getTitle()||"".equals(qstuff.getTitle())){
						qstuff.setTitle(stFileName.substring(0, stFileName
								.lastIndexOf(".")));
						}
						
					}else{
						qstuff.setTitle("未命名");
						}
					
					if (ext != null && ext.equals("zip")) {
						qstuff.setType(6);
					}
					System.out.println("stuffpic:"+qstuff.getStuffpic());
					System.out.println("stuffhot:"+qstuff.getStuffhot());
					qstuff.setFileext(ext.toLowerCase());
					qstuff.setOwner(new ELUser(
							getSessionIntValue(ElConstants.SESSION_USERID)));
					qstuff.setLength(st.length());
					int id = stuffDao.addQstuff(qstuff);
					qpstuff = qstuff.getParent();
					stuffDao.setStuffParent(qpstuff);
					J2EEFileUtil.upload(st, ext, "/elstuffs/"
							+ qpstuff.getPath(), id + "");
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_STUFF,
							ElLoggerConstants.LOG_TYPE_ADD, qstuff.getTitle()
									+ " 上传成功！", ElLoggerConstants.LOG_RES_SUCC,
							qstuff.getId());
					// 上传成功后，转换成swf
					// boolean bool=swfUtil.toSwf(qstuff);
					// if(!bool){
					// setElmessage("命令调用有误,请联系管理员检查环境是否配置正确！");
					// return "error";
					// }
					
					// 判断系统是否开启视频转换
					if (SystemConfOp
							.getBooleanValue(ElConstants.SHIPIN_NEED_ZH)) {
						//判断上传的文件是否是视频文件asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv，wmv9，rm，rmvb
						if (ext.equals("asx") || ext.equals("asf") || ext.equals("mpg")
								|| ext.equals("3gp") || ext.equals("mp4")|| ext.equals("rm")
								|| ext.equals("mov")|| ext.equals("avi")|| ext.equals("rmvb")
								|| ext.equals("flv")|| ext.equals("wmv9")) {
							ConvertWmv convertWmv=new ConvertWmv();// 视频转换工具
							String ffpath=J2EEFileUtil.getRealPath("/")+"tools/ffmpeg.exe";
							String mepath=J2EEFileUtil.getRealPath("/")+"tools/mencoder.exe";
							String inputFile=J2EEFileUtil.getRealPath("/")+ "elstuffs/"+ qstuff.getParent().getId()+ "/"+ id+"."+ext;
							String outputFile=J2EEFileUtil.getRealPath("/")+ "elstuffs/"+ qstuff.getParent().getId()+ "/"+ id+".wmv";
							boolean b=convertWmv.convert(ffpath,mepath, inputFile, outputFile);
							if(b){
								File file=new File(outputFile);
								if(file.exists()&&file.length()>0){
									// 继续在数据库中插入一条记录
									StuffLib qstuff_ = qstuff;
									// 设置文件类型为wmv
									qstuff_.setFileext("wmv");
									// 设置wmv文件大小
									qstuff_.setLength(file.length());
									int transformid = stuffDao.addQstuff(qstuff_);
									System.out.println("获取上传地址："+ transformid);
								}
							}
              
						}
					}
					// 判断系统是否设置了OFFICE和PDF上传需要转换
					// 暂时处理了doc、docx、xls、xlsx、pdf、ppt、txt七种文件
					if (SystemConfOp
							.getBooleanValue(ElConstants.FILEUPLOAD_NEED_ZH)) {
						String msg = swfUtil.toSwf_(qstuff);
						//上传完成后
						//更新数据库信息
						if (ext.equals("doc") || ext.equals("docx")
								|| ext.equals("xls") || ext.equals("xlsx")
								|| ext.equals("pdf") || ext.equals("ppt") 
								|| ext.equals("txt")) {
							File file = null;
							
							// 获取转换后的缩略图-小图，存在，更新数据库
							file = new File(J2EEFileUtil.getRealPath("/")
									+ "elstuffs/" + qstuff.getParent().getId()
									+ "/" + id + "/" + id + "-1-small.jpg");
							if(file.exists() && file.isFile()&& file.length() > 0){
								stuffDao.updateStuffJpg(id);
							}
							
							// 获取转换后的swf文件
							file = new File(J2EEFileUtil.getRealPath("/")
									+ "elstuffs/" + qstuff.getParent().getId()
									+ "/" + id + ".swf");
							// 判断文件是否存在，并且文件大小是否大于0
							if (swfUtil.checkSwfFileIsExist(file)
									&& file.length() > 0) {
								// 继续在数据库中插入一条记录
								StuffLib qstuff_ = qstuff;
								// 设置文件类型为swf
								qstuff_.setFileext("swf");
								// 设置swf文件大小
								qstuff_.setLength(file.length());
								// 设置来自转换
								qstuff_.setFromchange(1);
								int transformid = stuffDao.addQstuff(qstuff_);
								// 因为要保存两条数据，为了方便，复制swf
								new IoUtil().copyFile(J2EEFileUtil
										.getRealPath("/")
										+ "elstuffs/"
										+ qstuff.getParent().getId()
										+ "/"
										+ id
										+ ".swf", J2EEFileUtil.getRealPath("/")
										+ "elstuffs/"
										+ qstuff.getParent().getId() + "/"
										+ transformid + ".swf");
							}
							//重新加载服务中的缩略图
							OfficeJpgUtil.load();
						}
					}
					// if(!"".equals(msg)){
					// setElmessage(msg);
					// return "error";
					// }
				}
			}
		} else {
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_ADD, "未输入上传文件！",
					ElLoggerConstants.LOG_RES_ERR);
			printMsg("{'msg':'" + "请输入上传文件" + "'}");
			return null;
		}
		if (getRequest().getParameter("isMovep") != null
				&& "yes".equals(getRequest().getParameter("isMovep"))) {
			return "question_stuffList";
		}
		printMsg("{'msg':'" + "success" + "'}");
		return null;
	}

	/**
	 * 创建文件夹
	 * 
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String question_stuffwjjadd() throws ElException, Exception {
		// if (1 == stuffDao.getStuffOpStatus(qstuff.getId(),//非超级管理员无法创建文件夹
		if (1 == stuffDao.getStuffOpStatus(qstuff.getParent().getId(),
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE), 1)) {

			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_ADD, "您无权在根目录下创建文件夹!",
					ElLoggerConstants.LOG_RES_ERR);
			setElmessage("您无权在根目录下创建文件夹！");
			return "error";
		}
		// 检测是否有创建文件夹的权限（非超级管理员只有被分配的文件夹中才能创建文件夹，解决共享文件夹中创建文件夹问题）
		if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1
				&& !stuffDao.checkUserIsaddStuff(
						getSessionIntValue(ElConstants.SESSION_USERID), qstuff
								.getParent().getId())) {
			// 无权限
			String msg = "您的操作未执行成功！（非超级管理员只能在自己管理的文件夹下创建文件夹）";
			setElmessage(URLEncoder.encode(URLEncoder.encode(msg, "UTF-8"),
					"UTF-8"));
			return "question_stuffwjjadd_succ";
		}
		qstuff.setType(5);
		qstuff.setLength(qstuff.getLength() * 1024 * 1024L);
		long chsize = stuffDao.getStuffChildsSize(qstuff.getParent().getId());
		long mysize = qstuff.getLength();
		qstuff.setOwner(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		stuffDao.addQstuff(qstuff);
		stuffDao.setStuffParent(qstuff);
		long psize = stuffDao.getStuffParentSize(qstuff.getId());
		if (psize >= 0 && chsize >= 0 && (chsize + mysize > psize)) {
			stuffDao.setStuffsize(qstuff.getId(), (psize - chsize) > 0 ? psize
					- chsize : 0);
		}
		J2EEFileUtil.createFolder("/elstuffs/" + qstuff.getPath());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_STUFF,
				ElLoggerConstants.LOG_TYPE_ADD,
				qstuff.getTitle() + " 文件夹创建成功！", ElLoggerConstants.LOG_RES_ERR,
				qstuff.getId());
		return "question_stuffwjjadd_succ";
	}

	public String question_stuffunzip() throws ElException, Exception {
		qpstuff = stuffDao.getStuffbyId(qpstuff.getId(), 0);
		stuffDao.setStuffParent(qpstuff);
		String folder = ServletActionContext.getServletContext().getRealPath(
				"/elstuffs/")
				+ qpstuff.getPath() + "/";
		StuffLib qstuff1 = new StuffLib();
		StuffLib qstuff2 = stuffDao.getStuffbyId(qstuff.getId(), 0);
		qstuff1.setTitle(qstuff2.getTitle());
		qstuff1.setParent(qpstuff);
		qstuff1.setType(7);
		qstuff1.setOwner(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		stuffDao.addQstuff(qstuff1);
		stuffDao.setStuffParent(qstuff1);
		J2EEFileUtil.createFolder("/elstuffs/" + qstuff1.getPath());
		try {
			ZipUtil.unZip(new File(folder + qstuff.getId() + ".zip"), folder
					+ "/" + qstuff1.getId() + "/");

		} catch (FileNotFoundException e) {
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_GET, "文件不存在了!",
					ElLoggerConstants.LOG_RES_ERR);
			setElmessage("文件不存在了!");
			return "error";
		}
		return "question_stuffunzip_succ";
	}

	public String question_stuffalterInit() throws ElException, Exception {
		qstuff = stuffDao.getStuffbyId(qstuff.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID));
		return "question_stuffalter";
	}

	public String question_stuffalter() throws ElException, Exception {
		stuffDao.alter(qstuff);
		qstuff = stuffDao.getStuffbyId(qstuff.getId(), 0);
		if (qstuff != null) {
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_STUFF,
					ElLoggerConstants.LOG_TYPE_ALTER, qstuff.getTitle(),
					ElLoggerConstants.LOG_RES_ERR, qstuff.getId());
		}
		return "question_stuffalter_success";
	}

	public StuffDao getStuffDao() {
		return stuffDao;
	}

	public void setStuffDao(StuffDao stuffDao) {
		this.stuffDao = stuffDao;
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

	public String getSfContentType() {
		return sfContentType;
	}

	public void setSfContentType(String sfContentType) {
		this.sfContentType = sfContentType;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public StuffLib getQstuff() {
		return qstuff;
	}

	public void setQstuff(StuffLib qstuff) {
		this.qstuff = qstuff;
	}

	public StuffLib getQpstuff() {
		return qpstuff;
	}

	public void setQpstuff(StuffLib qpstuff) {
		this.qpstuff = qpstuff;
	}

	public StuffLib getStuffTree() {
		return stuffTree;
	}

	public void setStuffTree(StuffLib stuffTree) {
		this.stuffTree = stuffTree;
	}

	public StuffLib getStuffSharedTree() {
		return stuffSharedTree;
	}

	public void setStuffSharedTree(StuffLib stuffSharedTree) {
		this.stuffSharedTree = stuffSharedTree;
	}

	public List<StuffLib> getQstuffs() {
		return qstuffs;
	}

	public void setQstuffs(List<StuffLib> qstuffs) {
		this.qstuffs = qstuffs;
	}

	public String getStfilename() {
		return stfilename;
	}

	public void setStfilename(String stfilename) {
		this.stfilename = stfilename;
	}

	public int getSt_type() {
		return st_type;
	}

	public void setSt_type(int st_type) {
		this.st_type = st_type;
	}

	public SystemConf getSysconf() {
		return sysconf;
	}

	public void setSysconf(SystemConf sysconf) {
		this.sysconf = sysconf;
	}

	public String getDownFileName() {
		return downFileName;
	}

	public void setDownFileName(String downFileName) {
		this.downFileName = downFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public int getIsSeach() {
		return isSeach;
	}

	public void setIsSeach(int isSeach) {
		this.isSeach = isSeach;
	}

	public StuffQuery getStuffQuery() {
		return stuffQuery;
	}

	public void setStuffQuery(StuffQuery stuffQuery) {
		this.stuffQuery = stuffQuery;
	}

	public SwfUtil getSwfUtil() {
		return swfUtil;
	}

	public void setSwfUtil(SwfUtil swfUtil) {
		this.swfUtil = swfUtil;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public List<StuffLib> getQstuffs_video() {
		return qstuffs_video;
	}

	public void setQstuffs_video(List<StuffLib> qstuffs_video) {
		this.qstuffs_video = qstuffs_video;
	}

	public List<StuffLib> getQstuffs_txt() {
		return qstuffs_txt;
	}

	public void setQstuffs_txt(List<StuffLib> qstuffs_txt) {
		this.qstuffs_txt = qstuffs_txt;
	}

	public List<StuffLib> getQstuffs_image() {
		return qstuffs_image;
	}

	public void setQstuffs_image(List<StuffLib> qstuffs_image) {
		this.qstuffs_image = qstuffs_image;
	}


}