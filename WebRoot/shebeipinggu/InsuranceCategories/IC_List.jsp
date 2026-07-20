<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>险种列表管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script> 
		<script type="text/javascript">
			function load(){
				if("${elmessage}"!='null'&&"${elmessage}"!='')
					 alert("${elmessage}!");
			}
			function page(i){
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body onload="load()"> 
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="险种列表管理" /></div>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top">
							<form action="PG_IC_ListInit.action" method="post" >
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<div>
								<center>
									名称&nbsp;<input type="text" name="IC.name" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									表名&nbsp;<input type="text" name="IC.tableName" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									创建人&nbsp;<input type="text" name="IC.founder.realname" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="submit"  value="搜索" /> 
								</center> 
								</div> 
							</form> 
							<table width="100%" align="center" cellpadding="2" 	cellspacing="2" bgcolor="#EBEBEB">
							<caption > 
							    <a href="PG_IC_addInit.action"　class="textbg4">添加建筑设备保单</a> 
							</caption>
								<tr>
									<th width="100" height="30" align="center" >
										名称									
									</th>
									<th width="100" height="30" align="center" >
										表名								 
									</th>
									<th width="100" height="30" align="center" >
										创建人									
									</th>
									<th width="100" height="30" align="center" >
										创建时间									
									</th> 
									<!-- <th width="90" height="30" align="center" >
										投保人数据表								
									</th>
									<th width="90" height="30" align="center" >
										被保人数据表									
									</th>
									<th width="90" height="30" align="center" >
										标地数据表									
									</th> -->
									<th width="180" height="30" align="center" >
										模板路径		&nbsp;&nbsp; 
										 <a href="PG_IC_U_Demo_downloadInit.action?fileName=IC_U_demo.jsp" target="_blank" style="color:red">模板下载</a> 
										 <a href="PG_IC_U_Demo_downloadInit.action?fileName=IC_U_demo.css" target="_blank" style="color:red">样式下载</a> 					
									</th> 
									<th  height="30" align="center" colspan="2">
										操作								 　
									</th> 
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="ICList">
									<tr>
										<td height="30" style="padding-left:8px;color:blue;" align="center">
											<s:property value="name" />
									    </td>
										<td height="30" align="center" >
												<s:property value="tableName" />
										</td>
										<td height="30" align="center" >
											<s:property value="founder.realname" />
										</td>
										<td height="30" align="center" >
											<s:date name="createTime" format="yyyy-MM-dd hh:mm:ss"></s:date>
										</td> 
										<!-- <td height="30" align="center" >
											<s:property value="read_auto_toubaoren" />
										</td> 
										<td height="30" align="center" >
											<s:property value="read_auto_beibaoren" />
										</td> 
										<td height="30" align="center" >
											<s:property value="read_auto_biaodi" />
										</td> -->
										<td height="30" align="center" >  
											<form action="PG_IC_U_UpdateDemo.action?IC.id=<s:property value="id" />" enctype="multipart/form-data"	method="post"> 
												模板：<input type="file" name="st" value="<s:property value=""/>"> 
												<s:if test="demourl.length() > 0">
													<input type="submit" value="替换">
										 			<a href="PG_IC_U_Demo_downloadInit.action?fileName=<s:property value="tableName"/>.jsp" target="_blank" style="color:red">下载</a> 
												</s:if><s:else>
													<input type="submit" value="上传"> 
												</s:else>
											</form>
											<form action="PG_IC_U_UpdateDemo_CSS.action?IC.id=<s:property value="id" />" enctype="multipart/form-data"	method="post"> 
												样式：<input type="file" name="st" value="<s:property value=""/>"> 
												<s:if test="democss.length() > 0">
													<input type="submit" value="替换">
										 			<a href="PG_IC_U_Demo_downloadInit.action?fileName=<s:property value="tableName"/>.css" target="_blank" style="color:red">下载</a> 
												</s:if><s:else>
													<input type="submit" value="上传"> 
												</s:else>
											</form>
										</td> 
									  <td align="center" valign="middle">
									    <a href="PG_IC_U_columns_manageInit.action?IC.id=<s:property value="id"/>">管理表</a>  
    								    <!-- <a href="IC_Delete.action?IC.id=<s:property value="id"/>">删除</a> -->  
    								  </td>
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
										   
