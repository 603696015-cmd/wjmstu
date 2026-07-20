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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style> 
		<ul class="nav">
			<!--<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>-->
			<li>
				<span style="font-weight: bold;">我的选拨式考试</span></li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;margin-left: 0px; text-align: center;"> 
				<table width="100%" align="center" cellspacing="1" cellpadding="1"> 
					<tr>
						<th width="200" height="30" align="center" >
							考场标题						</th>
						<!-- <th width="80" height="30" align="center" >
							状态						</th> -->
						<th width="150" height="30" align="center" >
							创建者						</th>
						<th width="120" height="30" align="center" >
							考场开始时间						</th>
						<th width="120" height="30" align="center" >
							考场结束时间						</th>
						<th width="80" height="30" align="center" >
							试卷数量						</th>
						<th width="80" height="30" align="center" >
							成绩						</th>
						<th width="80" height="30" align="center" >
							是否通过						</th>
						<th width="120" height="30" align="center" >&nbsp;						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="myrooms">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<!--<s:if test="examroom.type == 1"><span style="color:red">[选拨]</span></s:if> -->
								<s:if test="examroom.isApplication == 1">
									<SPAN style="color:red">【申请】</SPAN>
								</s:if><s:else>
									<SPAN style="color:gray">【分配】</SPAN>
								</s:else>
								<s:property value="examroom.title" />
							</td>
							<td height="30" align="center" >
								<s:property value="examroom.creater.realname" />
							</td>
							<!-- <td width="80" height="30" align="center" >
								<s:property value="statusName" /> --> 
							<td height="30" align="center" >
								<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm:ss" />						  </td>
							<td height="30" align="center" >
								<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm:ss" />						  </td>
							<td height="30" align="center" >
								<s:property value="epsize" />
						  </td>
							<td height="30" align="center" >
								<s:property value="myScore" />
						  </td>
							<td height="30" align="center" >
								<s:if test="ispassed==1">是</s:if>
								<s:else>否</s:else>	
						  </td>
							<td width="120" height="30" align="center" >
									<a href="quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>" target= "rightFrame" onclick="return isEroom2('<s:property value="examroom.valid"/>','<s:property value="examroom.svalid"/>','<s:property value="examroom.isnormal"/>','<s:property value="examroom.type"/>');" class="textbg">进入</a> 
						  </td>
						</tr>
					</s:iterator></tbody>
			  </table>
			  
				<form action="listErsWithoutC_xbs.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow"> 
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
			</script>
				<wysLib:page></wysLib:page>  
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
