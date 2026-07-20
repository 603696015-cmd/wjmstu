<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>自定义报表</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		
		
		
		<script type="text/javascript">
			//搜索框的搜索
			function searchhtml(){
				var pageNow = document.getElementById("pageNow").value;
				if(pageNow!=undefined){
					document.getElementById("pageNow").value = 0;
				}
				searchLog_form.submit();
			}
			
			//按照列排序
			function combinesearch(obj){
				var columnname = $(obj).attr("title");
				$("#orderColumnname").val(columnname);
				var orderColumnname_type = document.getElementById("orderColumnname_type").value;
				if(orderColumnname_type== undefined || orderColumnname_type == ""){
					$("#orderColumnname_type").val("asc");
				}else{
					if($("#orderColumnname_type").val() == "asc")
						$("#orderColumnname_type").val("desc");
					else
						$("#orderColumnname_type").val("asc");
				}
				searchLog_form.submit();
			}
		
			function page(i){
				document.getElementById("pageNow").value = i;
				searchLog_form.submit();
			}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height:30px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body  >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:NavigationForZDY  />
				</div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="customReportZDYInit.action" method="post" name="searchLog_form"
				theme="simple">
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="orderColumnname" id="orderColumnname" />
				<s:hidden name="orderColumnname_type" id="orderColumnname_type" />
				<!-- <input type="hidden" name="resultPage" value="<s:property value="tablename"/>" /> -->
				<s:hidden name="tablename" />
				<s:hidden name="actionName" />
				<s:hidden name="customReport.id" />
				<table width="100%">
					<tr>
						<s:if test="customReport.showtree==1">
						<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;">  
							<%
								Department dep = (Department) request
											.getAttribute("department");
								String depid = dep.getId() + "";
								String actionName = (String)request.getAttribute("actionName");
								//String resultPage = (String)request.getAttribute("tablename");
								int customreportid = Integer.parseInt(String.valueOf(request.getAttribute("customReport.id")));
								String url =actionName + ".action?customReport.id="+customreportid+"&department.id=";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="<%=url%>"
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
								
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td width="5px;" valign="middle" bgcolor="#FAFCFC" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						</s:if>
						<td valign="top">
							<zdyLib:customReportZDY/>
						</td>
					</tr>
				</table>
			</s:form>
			
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>