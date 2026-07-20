<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.adl.samplerte.server.LMSPackageHandler"%>
<%@page import="org.adl.samplerte.server.LMSManifestHandler"%>
<%@page import="java.util.zip.ZipFile"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.zip.ZipEntry"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@ include file="importUtil.jsp"%>
<jsp:useBean id="myUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
	String sessionID = new String();
	String uploadDir = new String();
	String userDir = new String();
	String error = new String();
	LMSManifestHandler myManifestHandler;
	LMSPackageHandler myPackageHandler;

	try {
		sessionID = session.getId();

		String theWebPath = getServletConfig().getServletContext().getRealPath("/");

		myUpload.initialize(pageContext);
		myUpload.upload();

		uploadDir = "\\SampleRTEFiles\\tempUploads\\" + sessionID;
		java.io.File theRTEUploadDir = new java.io.File(uploadDir);

		//  The course directory should not exist yet
		if (!theRTEUploadDir.isDirectory()) {
			theRTEUploadDir.mkdirs();
		}

		// Save the file in the virtual path of the web server
		int count2 = myUpload.save(uploadDir, myUpload.SAVE_PHYSICAL);

		String courseTitle = new String(myUpload.getRequest().getParameter("coursename").getBytes(),"UTF-8");
		String zipFile = myUpload.getRequest().getParameter("theZipFile");
		String controlType = myUpload.getRequest().getParameter("controltype");
		
		System.out.println("[SCORM1.2]导入课程名称:"+courseTitle+"  \n 导入课程文件路径:"+zipFile+" \n 导入课程类型:"+controlType);
		   

		// Extract the manfest from the package
		myPackageHandler = new LMSPackageHandler();
		myPackageHandler.extract(zipFile, "imsmanifest.xml", uploadDir);

		String manifestFile = uploadDir + "\\" + "imsmanifest.xml";
		String newZip = zipFile.substring(zipFile.lastIndexOf("\\") + 1);
		zipFile = uploadDir + "\\" + newZip;

		// Create a manifest handler instance
		myManifestHandler = new LMSManifestHandler();

		InputSource fileToParse = setUpInputSource(manifestFile);
		//fileToParse.setEncoding("UTF-8");		//add by luocw 处理乱码问题
		myManifestHandler.setCourseName(courseTitle);
		myManifestHandler.setFileToParse(fileToParse);
		myManifestHandler.setControl(controlType);

		String courseId = myUpload.getRequest().getParameter("id");		//获取系统课程ID
		// Parse the manifest and fill up the object structure
		boolean result = myManifestHandler.processManifest();

		//获取course ID
		String courseID = myManifestHandler.getCourseID();

		ZipFile archive = new ZipFile(zipFile);

		// do our own buffering; reuse the same buffer.
		byte[] buffer = new byte[16384];

		// Loop through each Zip file entry
		for (Enumeration e = archive.entries(); e.hasMoreElements();) {
			// get the next entry in the archive
			ZipEntry entry = (ZipEntry) e.nextElement();

			if (!entry.isDirectory()) {
				// Set up the name and location of where the file will be 
				// extracted to
				String filename = entry.getName();
				filename = filename.replace('/', java.io.File.separatorChar);
				filename = theWebPath + "CourseImports\\" + courseID + "\\" + filename;
				java.io.File destFile = new java.io.File(filename);

				String parent = destFile.getParent();
				if (parent != null) {
					java.io.File parentFile = new java.io.File(parent);
					if (!parentFile.exists()) {
						// create the chain of subdirs to the file
						parentFile.mkdirs();
					}
				}

				// get a stream of the archive entry's bytes
				InputStream in = archive.getInputStream(entry);
				
				// open a stream to the destination file
				OutputStream outStream = new FileOutputStream(filename);

				// repeat reading into buffer and writing buffer to file,
				// until done.  count will always be # bytes read, until
				// EOF when it is -1.
				int count;
				while ((count = in.read(buffer)) != -1)
					outStream.write(buffer, 0, count);

				in.close();
				outStream.close();
			}
		}

		//  Write the Sequencing Object to a file
		String sequencingFileName = theWebPath + "CourseImports\\" + courseID + "\\sequence.obj";
		java.io.File sequencingFile = new java.io.File(sequencingFileName);
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

		// set content-type header before accessing Writer
		response.setContentType("text/xml; charset=utf-8");
		response.setCharacterEncoding("utf-8");  
		response.sendRedirect("confirmImport.jsp");
		
		//建立课程与scorm课件关联
		myManifestHandler.updateCourse(courseId);

	} catch (Exception e) {
		error = e.toString();
		e.printStackTrace();
	}
%>

导入出现错误:<%=error%>

