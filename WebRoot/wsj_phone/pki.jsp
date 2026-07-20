<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.jit.attr.JitAcComp"%>
<%@ page import="com.jit.attr.GenGACode"%>
<%@page import="java.security.cert.X509Certificate,java.util.Vector,com.jit.attr.GAACInfo,javax.security.auth.x500.X500Principal"%>
<html>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
	<%
			//本例中IP地址等参数为北京市局适用，其他试点请按实际情况修改。
			String myinfo = null;
			try {
				X509Certificate[] certs = (X509Certificate[]) request
						.getAttribute("javax.servlet.request.X509Certificate");
				if (certs == null) {
					out.println("error:错误！请提交证书！");
					return;
				}
				JitAcComp jitaccomp = new JitAcComp();
				X509Certificate gaX509Cert = null;
				gaX509Cert = certs[0];
				//获取序列号
				String sn = gaX509Cert.getSerialNumber().toString(16);
				out.println(sn+"<br/>");
				/*out.println(sn + gaX509Cert.getSigAlgName() + "===="
						+ gaX509Cert.getSigAlgName() + "===="
						+ gaX509Cert.getSigAlgOID());*/
				GenGACode ga = new GenGACode(); 
			    ga.setx509(gaX509Cert);
			    ga.parserDN();
      				String code = ga.getgaxS()+ "===="
						+   ga.getgaxLCity()+ "===="
						+  ga.getgaxLCounty() + "===="
						+  ga.getgaxOU78()+ "===="
						+  ga.getgaxOU9a() + "===="
						+  ga.getgaxOUbc()+"::";
				out.println("<br/>code---" + code);
				X500Principal principal = gaX509Cert.getSubjectX500Principal();
				out.print("<br/>"+principal.getName("RFC1779") );
			} catch (Exception e) {
				out.println("error2:错误！" + e.getMessage());//－－此处即为获取并显示统一提示信息的方法。
			}
		%>

	</body>
</html>