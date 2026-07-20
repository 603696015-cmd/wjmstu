<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>保险产品管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="<%=path %>/js/calendar.js"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
			} 
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="产品列表页" /></div>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td"> 
						<wysLib:productTypeTree href="Policy_AuditListInit.action?sublibs=1&ptype.id="
							rootAble="true"></wysLib:productTypeTree>
					</td> 
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
							<form action="buyPolicyListInit.action" method="post" name="assignProduct" id="assignProduct">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<div>
								<center> 
								</center>
								</div> 
							</form>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="80" height="30" align="center" >
										产品名称									
									</th> 
									<th width="80" height="30" align="center" >
										产品所属类别									
									</th>
									<th width="80" height="30" align="center" >
										提交者									
									</th>
									<th width="100" height="30" align="center" >
										提交者单位									</th>
									<th width="80" height="30" align="center" >
										提交时间									
									</th>
									<th width="80" height="30" align="center" >
										修改时间									
									</th>
									<th width="80" height="30" align="center" >
										审核状态									
									</th>
									<th width="80" height="30" align="center" >
										扫描件									
									</th>
									<th width="100" height="30" align="center"  colspan="3">
										操作									
									</th> 
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="policys">
									<tr>
										<td width="80" height="30" style="padding-left:8px;color:blue;" align="center">
											<s:property value="commodityName" />
									    </td>
										<td width="80" height="30" align="center" >
											<s:property value="libName" /> 
										</td> 
										<td width="80" height="30" align="center" >
											<s:property value="createId.realname" /> 
										</td> 
										<td width="80" height="30" align="center" >
											<s:property value="huiyuandanwei" /> 
										</td> 
										<td width="80" height="30" align="center" > 
											<s:date name="submitTime" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td width="80" height="30" align="center" >
											<s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td width="80" height="30" align="center" >
											<s:property value="validName" /> 
										</td> 
										<td width="80" height="30" align="center" >
												<s:if test="scanning != null">  
										 			<a href="Policy_downloadScanningInit.action?fileName=<s:property value="id" />.jpg" target="_blank" style="color:red">下载</a>
												</s:if><s:else>
													无
												</s:else> 
										</td> 
								      <td align="center" valign="middle"> 
									   	  <s:if test="valid == 1 || valid == 4">
										   	  <a href="Policy_AuditInit.action?policy.id=${id }&policy.valid=2&actionName=Policy_AuditListInit">不通过</a>
										   	  <a href="Policy_AuditInit.action?policy.id=${id }&policy.valid=3&actionName=Policy_AuditListInit">通过</a>
										   	  <a href="IC_U_Info_AlertInit.action?policy.id=${id}&IC_U_ID=<s:property value="IC_U_ID" />&actionName=Policy_AuditListInit">修改</a>									   	  </s:if>   
								   	    <!--<s:if test="valid == 3"> 
										   	  <a href="Policy_AuditInit.action?policy.id=${id }&policy.valid=4&actionName=Policy_AuditListInit">删除</a>
									   	  </s:if> -->
									   	    <a href="IC_U_Info_ViewInit.action?policy.id=${id}&IC.id=<s:property value="IC_U_ID" />" target="_blank" >查看</a>									    </td> 
									</tr>
								</s:iterator></tbody>
						  </table>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
										   








