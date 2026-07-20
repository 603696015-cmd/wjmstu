<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/tree/dep.js"></script>

  </HEAD>
  
  <body>
  <table style="height:100%">
  	<tr>
  		<td>
  				<%
					Department dep = (Department) request
								.getAttribute("department");
						String depid = dep.getId() + "";
					String tablename=(String)request.getAttribute("tablename");
					String url ="searchContactTags.action?tablename="+tablename+"&department.id=";
				%>
				<wysLib:dep_list_aj rootAble="true"
					href="<%=url%>"
					iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
		  		<script type="text/javascript">
					w0.setValues([new DEP(<s:property value="department.id"/>,<s:property 
						value="department.lid"/>,<s:property value="department.rid"/>)]);
				</script>
  		</td>
  	</tr>
  </table>
				
  </body>
</html>
