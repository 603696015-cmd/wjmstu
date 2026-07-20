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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习审核 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div>


				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<th width="200" height="30" align="center" >
							练习名称						</th>
						<th width="120" height="30" align="center" >
							创建者						</th>
						<th width="200" height="30" align="center" >
							创建者所属部门						</th>
						<th width="120" height="30" align="center" >
							开始时间						</th>
						<th width="120" height="30" align="center" >
							结束时间						</th>
						<th width="100" height="30" align="center" >
							考生人数				</th>
						<th width="100" height="30" align="center" >
							状态						</th>
					  <th width="120" height="30" align="center" >&nbsp;					  </th>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="exampracs">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="title" />
							</td>
							<td height="30" align="center" >
								<s:property value="user.username" />
							</td>
							<td height="30" align="center" >
								<s:property value="user.danwei" /><!-- 此处借用 实际值为  部门名称 -->
							</td>
							<td height="30" align="center" >
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />						  </td>
							<td height="30" align="center" >
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />						  </td>
							<td height="30" align="center" >
								<s:property value="usersize" />						  </td>
							<td height="30" align="center" >
								<font color="<s:if test="valid==1">red</s:if>">
								<s:property	value="validName" />
							  </font>
						  </td>
							<td height="30" align="center" > 
							<s:if test="valid==1">
							<input class="textbg4" type="button" name="button2" onClick="sh_np(<s:property	value="id" />);"
								id="button2" value="暂停" />		 					
							</s:if><s:elseif test="valid==3">
							<input class="textbg4" type="button" name="button2" onClick="sh_p(<s:property	value="id" />);"
								id="button2" value="恢复" />
							</s:elseif>
							</td>
						</tr>
					</s:iterator></tbody>
			  </table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
				</DIV> 
				<script>
					function sh_p(id){
						if(window.confirm("确定恢复？"))  
							document.location.href="examprac_validrecovery.action?examprac.id="+id;
					} 
					function sh_np(id){
						if(window.confirm("确定暂停？")) 
							document.location.href="examprac_validsuspended.action?examprac.id="+id;
					}
					function page(i){
						document.getElementById("pageNow").value=i;
						erform.submit();
					}
				</script>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
