<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--模板下载</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
	</HEAD>
	<BODY>
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<span style="font-weight: bold;">模板列表</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="tList.size==0">没有模板文件</s:if>
			<s:else>
			<table align="center" cellpadding="2" cellspacing="2" width="100%"
				 bgcolor="#ECEDEB">
				<tr>
					
					<th>
						模板名称	
					</th>
					<th>
						模板真实名称	
					</th>
					<th>
						操作	
					</th>
					
					
				</tr>
				<s:iterator value="tList">
					<tr>
						
						<td height="40" align="center">
							<s:property value="name" />
						</td>
						<td height="40" align="center">
							<s:property value="trueName" />
						</td>
						<td height="40" align="center">
							<a  href="down.action?template.id=<s:property value="id" />"  class=textbg6>下载模板</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			</s:else>
 		
		</div>
		<wysLib:page></wysLib:page>
		<form action="mode_downloadDemoInit.action" method="post"
			name="acc_list">
			<s:hidden name="pN" id="pageNow"/>
			<s:hidden name="pS" />
			</form>
			
		<script type="text/javascript">
		function down(i){
	$.ajax({
			
			  type: 'POST',
			  url: "mode_downloadDemo.action",
			  data: {name:i},
			  async:true,//
			  success: function(data){
			 
			alert("下载成功")	;
}
});
}
			function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<!-- 内容 -->
	
	</body>
</HTML>
