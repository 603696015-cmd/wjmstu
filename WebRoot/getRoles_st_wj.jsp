<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.jit.attr.JitAcComp"%>
<%@page
	import="java.security.cert.X509Certificate,"%>
<%@page import="com.jit.exception.PKILDAPException"%>
<%@page import="com.jit.exception.ParameterException"%>
<%@page import="com.jit.exception.GACertParseException"%>
<%@page import="com.jit.exception.GAIOException"%>
<%@page import="com.jit.exception.GACertTimeException"%>
<%@page import="com.jit.exception.GACertSignException"%>
<%@page import="com.jit.exception.GACertCRLException"%>
<html>
	<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
		<%
			//本例中IP地址等参数为北京市局适用，其他试点请按实际情况修改。
			String myinfo = null;
			try {
				X509Certificate[] certs = (X509Certificate[]) request
						.getAttribute("javax.servlet.request.X509Certificate");
				if (certs == null) {
					out.println("错误！请提交证书！<br/>");
					return;
				}
				out.println("1= cert read<br/>");
				X509Certificate gaX509Cert = null;
				gaX509Cert = certs[0];
				//获取序列号
				//String sn = gaX509Cert.getSerialNumber().toString(16);
				JitAcComp jitaccomp = new JitAcComp();
				out.println("2=JitAcComp set!<br/>");
				jitaccomp.setBaseDN("st=44,c=cn");
				jitaccomp.setPrivilegeSetType(0);
				jitaccomp.setPKICertificate(gaX509Cert);

				//客户端IP获取及设置审计参数,不设定则不执行审计操作。
				String ip = request.getRemoteAddr();
				jitaccomp.setClientIP(ip);
				out.println("2=JitAcComp ip:" + ip+"<br/>");
				jitaccomp.setAuditParameter("10.40.28.102", "3000");

				jitaccomp.setParameter("10.40.28.102,10.40.28.118", "390,389");//PKI的LDAP服务器IP、端口。
				jitaccomp.isCheckCRL(false); //Crl验证
				jitaccomp.isCheckCertPath(false); //证书链验证
				out.println("3=JitAcComp read!<br/>");
				
				
				myinfo = jitaccomp.getPrivilegeList("10.40.28.101","389","23001","110000");
				out.println("4=JitAcComp read!<br/>");
				out.println("myinfo:"+myinfo);
			} catch (PKILDAPException e) {
				out.println("1错误:无法连接PKI的目录服务器" + e.getMessage());
			} catch (ParameterException e) {
				out.println("2错误:方法参数错误，参数为''或null" + e.getMessage());
			} catch (GACertParseException e) {
				out.println("3错误:公安证书解析异常" + e.getMessage());
			} catch (GAIOException e) {
				out.println("4错误:读取本地文件异常" + e.getMessage());
			} catch (GACertTimeException e) {
				out.println("5错误:证书过期" + e.getMessage());
			} catch (GACertSignException e) {
				out.println("6错误:证书签名无效" + e.getMessage());
			} catch (GACertCRLException e) {
				out.println("7错误:证书被注销" + e.getMessage());
			}
		%>
	</body>
</html>