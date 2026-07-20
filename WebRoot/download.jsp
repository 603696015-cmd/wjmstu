<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
		<%
			try {
				String filename = request.getParameter("filename");
				filename = filename.substring(filename.indexOf("elstuffs"));
				//String fileName = filename.substring(filename.lastIndexOf("/")+1);
				String path1 = request.getRealPath(filename);
				System.out.println(path1);
				File f = new File(path1);
				if (f.exists()) {
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition",
							"attachment; filename="
									+ URLEncoder.encode(f.getName(), "UTF-8"));
					long len = f.length();
					String range = request.getHeader("Range");
					FileInputStream fis = new FileInputStream(f);
					if (range != null) {
						response.setStatus(206);
						response.setHeader("Content-Range", range + "/" + len);
						String arr[] = range.substring(6).split("-");
						long start = arr[0].length() < 1 ? 0 : Long
								.parseLong(arr[0]);
						long stop = arr[1].length() < 1 ? len : Long
								.parseLong(arr[1]) + 1;
						fis.skip(start);
						len = stop - start;
					}
					response.setContentLength((int) len);
					OutputStream os = response.getOutputStream();
					byte by[] = new byte[8192];
					int value = 0;
					try {
						while ((value = fis.read(by)) != -1) {
							os.write(by, 0, value);
						}
					} finally {
						fis.close();
						os.close();
					}
				} else {
					out.print("文件不存在!");
				}
			} catch (Exception e) {
				out.print("文件下载失败");
			}
			out.clearBuffer();
			out = pageContext.pushBody();
		%>
