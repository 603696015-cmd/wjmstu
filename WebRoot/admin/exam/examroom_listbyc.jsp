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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试考场管理 </span>
			</li>
			<li class="sep">
			</li>
			<li>
			<!-- 	<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examroom_addInit.action?course.id=<s:property value="course.id"/>">添加考场</a>
					
					<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="erwithout_addInit.action?course.id=<s:property value="course.id"/>">添加考场</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;">
				课程
				<b><s:property value="course.name" /> </b> 的考场管理
			</label>
			<br>
			<br>
			<!-- <a href="examroom_addInit.action?course.id=<s:property value="course.id"/>&choose=false" class="textbg">添加考场</a> -->
			<a href="erwithout_addInit.action?course.id=<s:property value="course.id"/>&choose=false" class="textbg5">添加考场</a>
			<br>
			<s:if test="examRooms.size==0">
				<br>
						没有考场。
			</s:if>
			<s:else>
			<s:form action="class_choose_examroom.action" name="choose" method="post">
				<table width="96%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<!--<td height="30" align="center" >
							&nbsp;
						</td>
						-->
						<!-- 
						<td height="21" align="center" >
							&nbsp;						</td>
						 -->
						<td height="30" align="center" bgcolor="#66CCFF" >
							考场标题						</td>
						<td height="21" align="center" bgcolor="#66CCFF" >
							考场地点						</td>
						<td height="21" align="center" bgcolor="#66CCFF" >
							监考老师						</td>
						<td height="21" align="center" bgcolor="#66CCFF" >
							通过百分比						</td>
						<td height="21" align="center" bgcolor="#66CCFF" >
							考场开始时间						</td>
						<td height="21" align="center" bgcolor="#66CCFF" >
							考场结束时间						</td>
						<td height="21" align="center" bgcolor="#66CCFF" >&nbsp;					  </td>
						<td height="21" align="center" bgcolor="#66CCFF" >&nbsp;					  </td>
						<td height="21" align="center" bgcolor="#66CCFF" >&nbsp;					  </td>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="examRooms">
						<tr>
							<!--
							<td height="30" align="center" >
								<input type="checkbox" name="delete_item[]" value="5">
							</td>
							-->
							<!-- 
						 	<s:if test="isBand==0||isBand==null">	
								<td height="30" align="center" >
									<input type="radio" name="examRoom.id" value="<s:property value='id'/>">
								</td>
							</s:if>
							
							<s:else>
								<td height="30" align="center" >
									<s:property value="bandClassName" />
								</td>
							</s:else>
							  -->
							<td height="30" align="center" >
								<s:property value="title" />
							</td>
							<td height="30" align="center" >
								<s:property value="location" />
							</td>
							<td height="30" align="center" >
								<s:property value="supervisor.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="passgrade" />
							</td>
							<td height="30" align="center" >
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center" >
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center" >
								<!-- <a href="examroom_alterInit.action?examRoom.id=<s:property value="id"/>" class="textbg">编 辑</a> -->
								<a href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>" class="textbg5">编辑</a> 
							</td>
							<td height="30" align="center" >
							<!-- 	<a href="examroom_assignInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>" class="textbg">分配学员</a> -->
								<a href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>" class="textbg5">分配学员</a> 
							</td>
							<td height="30" align="center" >
								<a
									href="exampaperreadlist.action?examRoom.id=<s:property value="id"/>" class="textbg5">阅 卷</a>
							</td>
						</tr>
					</s:iterator></tbody>
			  </table>
				<script type="text/javascript">
								function chooses(){
									if(window.confirm("确定选择此考场绑定？")){
										choose.action="class_choose_examroom.action?classId=0&course.id=<s:property value="course.id" />";
										choose.submit();
									}
								}
				</script>
				 <!-- 	<input style="height:35px;" class="textbg6" type="button" name="button2" onClick="chooses();" id="button2" value="绑定考场" />	 --> 
			  </s:form>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
