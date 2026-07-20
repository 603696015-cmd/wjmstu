<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.jit.attr.JitAcComp"%>
<%@ page import="com.jit.attr.GenGACode"%>
<%@page import="java.security.cert.X509Certificate"%>
<html>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<%
//本例中IP地址等参数为北京市局适用，其他试点请按实际情况修改。
    String myinfo = null;
    try{
       X509Certificate[] certs=(X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
       if(certs==null){
      	 out.println("错误！请提交证书！");
      	 return;
       }
       X509Certificate gaX509Cert=null;
       gaX509Cert=certs[0];
       JitAcComp jitaccomp = new JitAcComp();
       jitaccomp.setBaseDN("c=cn");
       jitaccomp.setPrivilegeSetType(0);
       jitaccomp.setPKICertificate(gaX509Cert);
      
       //客户端IP获取及设置
       String ip = request.getRemoteAddr();
       jitaccomp.setClientIP(ip);
       
       //校验证书有效性
       jitaccomp.setParameter("10.8.1.160,10.1.1.103", "390,389");//PKI的LDAP服务器IP、端口。
       jitaccomp.isCheckCRL(true);                                //Crl验证
       jitaccomp.isCheckCertPath(true);                           //证书链验证
       
       //审计，不设定则不执行审计操作。
       jitaccomp.setAuditParameter("10.8.1.155", "3000");         
       
       //获取授权码
       myinfo = jitaccomp.getPrivilegeList("10.8.1.155","390","23001","110000");
       out.println("myinfo---" + myinfo);
       
      //获取机构代码
      GenGACode ga = new GenGACode();
      ga.setx509(gaX509Cert);
      ga.parserDN();
      String code = ga.getgaxS() + ga.getgaxLCity() + ga.getgaxLCounty() +
          ga.getgaxOU78() + ga.getgaxOU9a() + ga.getgaxOUbc();
      out.println("code---" + code);
      
	   }
     catch(Exception e){
			 out.println("错误！"+e.getMessage());//－－此处即为获取并显示统一提示信息的方法。
     }
%>
</body>
</html>