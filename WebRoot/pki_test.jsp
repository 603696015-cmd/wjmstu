<%@ page contentType="text/html; charset=UTF-8"%>
<%@page
	import="java.security.cert.X509Certificate"%>
<%@page import="com.jit.exception.PKILDAPException"%>
<%@page import="com.jit.exception.ParameterException"%>
<%@page import="com.jit.exception.GACertParseException"%>
<%@page import="com.jit.exception.GAIOException"%>
<%@page import="com.jit.exception.GACertTimeException"%>
<%@page import="com.jit.exception.GACertSignException"%>
<%@page import="com.jit.exception.GACertCRLException"%>
<%@page import="com.jit.attr.jitCertVerify"%>
<html>
	<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
		<%
			//本例中IP地址等参数为北京市局适用，其他试点请按实际情况修改。
			try {
				X509Certificate[] certs = (X509Certificate[]) request
						.getAttribute("javax.servlet.request.X509Certificate");
				if (certs == null) {
					out.println("错误！请提交证书！<br/>");
					return;
				}
				X509Certificate cert = certs[0];
				jitCertVerify ver = new jitCertVerify();
				//ver.setBaseDN("st=44,c=cn");
			    ver.setParameter("10.40.28.102,10.40.28.118","390,389");//单ip、port情况
			    //ver.setParameter("172.16.8.147,127.0.0.1","389,389");多ip、port情况
			    ver.verify(cert,false,false);
				out.println("pki 没问题呢！");
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