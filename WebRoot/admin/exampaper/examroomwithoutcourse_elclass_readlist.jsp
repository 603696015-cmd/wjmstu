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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" /> 
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 考试阅卷</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;"></label>
			
		  <s:if test="examRooms.size==0">
				<br>
					  您当前没有需要评阅的考场或试卷			</s:if>
			<s:else> 
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<th width="40" height="30" align="center" >&nbsp;						</th>
						<th width="200" height="30" align="center" >
							考场名称						</th>
						<th height="30" align="center" >
							创建者
						</th>
						<th width="120" height="30" align="center" >
							考场地点						</th>
						<!--<th height="30" align="center" >
							监考老师
						</th>-->
						<th width="150" height="30" align="center" >
							开始时间						</th>
						<th width="150" height="30" align="center" >
							结束时间						</th>
						<th width="70" height="30" align="center" >&nbsp;						</th>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="examRooms">
						<tr>
							<td width="40" height="30" align="center" >
								<input type="checkbox" name="delete_item[]" value="5">
						  </td>
							<td width="200" height="30" align="center" >
								<s:property value="title" />
						  </td>
							<td width="150" height="30" align="center" >
								<s:property value="creater.realname" />
						  </td>
							<td width="120" height="30" align="center" >
								<s:property value="location" />
						  </td>
							<!--<td height="30" align="center" >
								<s:property value="supervisor.realname" />
							</td>-->
							
							<td width="150" height="30" align="center" >
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
						  </td>
							<td width="150" height="30" align="center" >
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
						  </td>
							<td width="70" height="30" align="center" >
								<a href="exampaperreadlist.action?examRoom.id=<s:property value="id"/>" class=textbg>阅 卷</a>
							</td>
						</tr>
					</s:iterator></tbody>
			  </table> 
		  </s:else>
				<script> 
						function page(i){
							document.location.href="examroomwithoutcourse_readlist.action?pS=<s:property value="pS"/>&pN="+i
						} 
								
				</script>
				<wysLib:page></wysLib:page>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
