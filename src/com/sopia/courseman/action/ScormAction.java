package com.sopia.courseman.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.adl.samplerte.server.LMSManifestHandler;
import org.adl.samplerte.server.LMSPackageHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.struts2.ServletActionContext;
import org.xml.sax.InputSource;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.JTM;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseRegistration;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.ELUser;
import com.sopia.openmeetings.Rooms;

public class ScormAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(ScormAction.class);
	private Course course;
	private IndexDataUtil indexDataUtil;
	private CourseType ctypeTree;
	private CourseTypeDao ctypeDao;
	private CourseRegistration coRegistration;
	private CourseDao courseDao;
	private File scormfile;
	private String scormfileFileName;
	private String scormfileContentType;
	private int optype;
	
	private String weidu;//课程维度

	public String getWeidu() {
		return weidu;
	}

	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}

	public int getOptype() {
		return optype;
	}

	public void setOptype(int optype) {
		this.optype = optype;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	/**
	 * Scorm课程添加初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_scormaddinit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			ctypeTree = ctypeDao.getCourseLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (ctypeTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的课程类别");
			return "error";
		}
		return "course_scormadd";// 返回课程列表页
	}

	/**
	 * Description:Scorm课程导入
	 * 
	 * @Version1.0 2012-7-21 上午09:45:59 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String course_scormadd() throws ElException {
		course.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		if (scormfile == null
				|| !"zip".equals(J2EEFileUtil.getExtention(scormfileFileName)
						.toLowerCase())) {
			setElmessage("请输入zip压缩文件！不支持其他格式文件");
			return "error";
		}
		if (course.getCtype() != null) {
			// 导入scorm课件
			course.setRoom(new Rooms());
//			courseDao.addCourse(course);
			String sessionID = new String();
			String uploadDir = new String();
//			String userDir = new String();
//			String error = new String();
			LMSManifestHandler myManifestHandler;
			LMSPackageHandler myPackageHandler;
			try {
				sessionID = getSession().getId();
				String theWebPath = ServletActionContext.getServletContext()
						.getRealPath("/");
				uploadDir = theWebPath +"/SampleRTEFiles/tempUploads/" + sessionID;
				java.io.File theRTEUploadDir = new java.io.File(uploadDir);
				// The course directory should not exist yet
				if (!theRTEUploadDir.isDirectory()) {
					theRTEUploadDir.mkdirs();
				}
				// Save the file in the virtual path of the web server
				String courseTitle = course.getName();
				// String zipFile =
				// "D:/business/gdgat/scorm/测试课件/华夏银行信息科技管理委员会工作规则..zip";
				String controlType = "flow";
				// Extract the manfest from the package
				myPackageHandler = new LMSPackageHandler();
				myPackageHandler.extract(scormfile.getPath(),
						"imsmanifest.xml", uploadDir);
				String manifestFile = uploadDir + "/" + "imsmanifest.xml";
				myManifestHandler = new LMSManifestHandler();
				InputSource fileToParse = setUpInputSource(manifestFile);
				myManifestHandler.setCourseName(courseTitle);
				myManifestHandler.setFileToParse(fileToParse);
				myManifestHandler.setControl(controlType);
				// Parse the manifest and fill up the object structure
				boolean result = myManifestHandler.processManifest();
				// Get the course ID
				String courseID = myManifestHandler.getCourseID();
				ZipFile archive = new ZipFile(scormfile);
				// do our own buffering; reuse the same buffer.
				byte[] buffer = new byte[16384];
				// Loop through each Zip file entry
				for (Enumeration e = archive.entries(); e.hasMoreElements();) {
					// get the next entry in the archive
					ZipEntry entry = (ZipEntry) e.nextElement();
					if (!entry.isDirectory()) {
						String filename = entry.getName();
						filename = filename.replace('/',
								java.io.File.separatorChar);
						filename = theWebPath + "/CourseImports/" + courseID
								+ "/" + filename;
						java.io.File destFile = new java.io.File(filename);
						String parent = destFile.getParent();
						if (parent != null) {
							java.io.File parentFile = new java.io.File(parent);
							if (!parentFile.exists()) {
								// create the chain of subdirs to the file
								parentFile.mkdirs();
							}
						}
						InputStream in = archive.getInputStream(entry);
						OutputStream outStream = new FileOutputStream(filename);
						int count;
						while ((count = in.read(buffer)) != -1)
							outStream.write(buffer, 0, count);

						in.close();
						outStream.close();
					}
				}

				// Write the Sequencing Object to a file
				String sequencingFileName = theWebPath + "/CourseImports/"
						+ courseID + "/sequence.obj";
				java.io.File sequencingFile = new java.io.File(
						sequencingFileName);
				FileOutputStream ostream = new FileOutputStream(sequencingFile);
				ObjectOutputStream oos = new ObjectOutputStream(ostream);
				oos.writeObject(myManifestHandler.getOrgsCopy());
				oos.flush();
				oos.close();

				// Delete uploaded files
				boolean wasdeleted = false;
				java.io.File uploadFiles[] = theRTEUploadDir.listFiles();
				for (int i = 0; i < uploadFiles.length; i++) {
					uploadFiles[i].deleteOnExit();
				}
				theRTEUploadDir.deleteOnExit();
				course.setExurl(courseID) ;
				
				//获取课程维度
				String[] weidu_array = getRequest().getParameterValues("weidu");
				weidu = "";
				if(weidu_array!=null&&weidu_array.length>0){
					for(int i=0;i<weidu_array.length;i++){
						if(i == weidu_array.length-1){
							weidu += weidu_array[i];
						}else{
							weidu += weidu_array[i] + ",";
						}
					}
				}
				course.setWeidu(weidu);
				courseDao.addCourse(course);
				
				course = courseDao.getCourseById(course.getId());
				if(course.getName()==null||course.getName().equals("")){
					this.setElmessage("课程名称不能为空");
					return "error";
				}
				
				boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
				if(open_jtm){
					//添加维度信息到JTM
					String cer = JTM.getJTM_cer(String.valueOf(course.getId()));
					boolean addSuccess = false;
					String JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_COURSES_AYSCHRONIZATION_URL)+
					"?courseid="+course.getId()+
					"&coursename="+URLEncoder.encode(course.getName(), "GB2312")+
					"&url=http://www.google.com/"+
					"&dimid="+weidu+
					"&cer="+cer;
					
					Content c = null;
					try {
						c = Request.Get(JTM_URL).addHeader("Content-Type", "text/html; charset=UTF-8").execute().returnContent();
						String returnValue = c.asString();
						addSuccess = (returnValue!=null&&returnValue.equals("true"))?true:false;
						
						
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!addSuccess){
						this.setElmessage("scorm课程添加错误，添加维度信息到JTM出错!");
						return "error";
					}
				}
			} catch (Exception e) {
				logger.error("课程导入失败！", e);
				setElmessage("课程导入失败！");
				return "error";
			}
			if (course.getIsApplication() == 1 && coRegistration != null) {// 是否为可申请
				coRegistration.setCourse(course);
				if (!courseDao.checkCourseRegistration(course.getId())) {
					courseDao.addCourseRegistration(coRegistration);
				} else {
					courseDao.alterCourseRegistration(coRegistration);
				}
			}
		} else {
			setElmessage("请选择课程类别!");
			return "error";
		}
		// 刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSE,
				ElLoggerConstants.LOG_TYPE_ADD, course.getName(),
				ElLoggerConstants.LOG_RES_SUCC, course.getId());
		return "course_list";// 返回课程列表页
	}

	/***************************************************************************
	 * * * Function: setUpInputSource() * Input: fileName - String * Output: is -
	 * InputSource * * Description: This function returns the input source. *
	 **************************************************************************/

	private InputSource setUpInputSource(String fileName) {
		System.out.println("fileName:::::::" + fileName);
		InputSource is = new InputSource();
		is = setupFileSource(fileName);
		return is;
	}

	/***************************************************************************
	 * * * Function: setUpFileSource() * Input: fileName - String * Output: is -
	 * InputSource * * Description: This function returns the input source. *
	 **************************************************************************/
	private InputSource setupFileSource(String filename) {
		try {
			java.io.File xmlFile = new java.io.File(filename);
			if (xmlFile.isFile()) {
//				FileReader fr = new FileReader(xmlFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile),"utf-8"));
				InputSource is = new InputSource(br);
				return is;
			} else {
			}
		} catch (NullPointerException npe) {
			System.out.println("Null pointer exception" + npe);
		} catch (SecurityException se) {
			System.out.println("Security Exception" + se);
		} catch (FileNotFoundException fnfe) {
			System.out.println("File Not Found Exception" + fnfe);
		}catch (UnsupportedEncodingException e) {
			System.out.println("File Not Could not Encoding Exception" + e);
		}
		return new InputSource();
	}

	public CourseType getCtypeTree() {
		return ctypeTree;
	}

	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public CourseRegistration getCoRegistration() {
		return coRegistration;
	}

	public void setCoRegistration(CourseRegistration coRegistration) {
		this.coRegistration = coRegistration;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public File getScormfile() {
		return scormfile;
	}

	public void setScormfile(File scormfile) {
		this.scormfile = scormfile;
	}

	public String getScormfileFileName() {
		return scormfileFileName;
	}

	public void setScormfileFileName(String scormfileFileName) {
		this.scormfileFileName = scormfileFileName;
	}

	public String getScormfileContentType() {
		return scormfileContentType;
	}

	public void setScormfileContentType(String scormfileContentType) {
		this.scormfileContentType = scormfileContentType;
	}
}
