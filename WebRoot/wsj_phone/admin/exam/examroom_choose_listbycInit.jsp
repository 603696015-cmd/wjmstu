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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试考场管理 </span>
			</li>
		<!--	<li class="sep">
			</li>
			<li>
				 <a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examroom_addInit.action?course.id=<s:property value="course.id"/>">添加考场</a> 
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;">
				培训班：<b><s:property value="elclass.name" /></b>
				的课程
				<b><s:property value="course.name" /> </b> 的考场管理
			</label>
			<br>
			<br>
			<!--  <a href="examroom_addInit.action?course.id=<s:property value="course.id"/>&choose=true" class="textbg">添加考场</a>  -->

			<s:if test="examRooms.size==0">
				<br>
						没有考场。
			</s:if>
			<s:else>
				<s:if test="elclass.isApplication==1">
					<div style="color: red; text-align: center;">
						当前培训班是可申请培训班，绑定考场会自动为该考场的所有试卷分配人员，手工增减人员将会改变系统自动分配的结果.
					</div>
				</s:if>
				<s:form action="class_choose_examroom.action" name="choose"
					method="post">
					<s:hidden name="elclass.isApplication" />
					<table width="96%" align="center" cellspacing="1" cellpadding="1">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								align="left">&nbsp;
								

							</td>
							<td height="21" align="center">
								考场标题
							</td>
							<td height="21" align="center">
								考场地点
							</td>
							<td height="21" align="center">
								监考老师
							</td>
							<td height="21" align="center">
								通过百分比
							</td>
							<td height="21" align="center">
								考场开始时间
							</td>
							<td height="21" align="center">
								考场结束时间
							</td>
							<td height="21" align="center">&nbsp;
								
							</td>
						</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
							<s:set name="isGrant" value="elclass.xxCount" />
							<s:iterator value="examRooms">
								<s:if test="id == examRoom.id">
									<s:set name="isBand" value="1" />
								</s:if>
								<tr>
									<!-- 	
						    <td height="30" align="center" > 
								<input type="radio" <s:if test="id == examRoom.id">checked='checked'</s:if>
								 name="examRoom.id" value="<s:property value='id'/>"> 
							</td>
						  -->
									<%--	<s:if test="isBand==0||isBand==null"> --%>
									<td height="30" align="center">
										<input type="radio"
											<s:if test="id == examRoom.id">checked='checked'</s:if>
											name="examRoom.id" value="<s:property value='id'/>">
									</td>
									<%--	</s:if>  --%>
									<!-- 	
							<s:else>
								<td height="30" align="center" >
									<s:property value="bandClassName" />
								</td>
							</s:else>
						 -->
									<td height="30" align="center">
										<s:property value="title" />
									</td>
									<td height="30" align="center">
										<s:property value="location" />
									</td>
									<td height="30" align="center">
										<s:property value="supervisor.realname" />
									</td>
									<td height="30" align="center">
										<s:property value="passgrade" />
									</td>
									<td height="30" align="center">
										<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td height="30" align="center">
										<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td height="30" align="center">
										<s:if test="valid == 0 && uvalid == 0">
											<a
												href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=error"
												class="textbg">编 辑</a>
											<a
												href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="classId"/>"
												class="textbg">分配学员</a>
										</s:if>
										<s:if test="valid == 2 && uvalid == 0">
											<a
												href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=error"
												class="textbg">编 辑</a>
											<a
												href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="classId"/>"
												class="textbg">分配学员</a>
										</s:if>
										<s:if test="valid == 4">
											<a
												href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>"
												class="textbg">编 辑</a>
											<a
												href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="classId"/>"
												class="textbg">修改学员</a>
										</s:if>
										<s:if test="valid == 0 && uvalid == 1">
											<a
												href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=error"
												class="textbg">编 辑</a>
										</s:if>
										<s:if test="valid==5&&#request.isGrant==1">
											<a
												href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>"
												class="textbg">编 辑</a>
											<a
												href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="classId"/>"
												class="textbg">修改学员</a>
										</s:if>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<script type="text/javascript">
								function chooses(){
									//获取所有单选按钮值，看是否有选择
									var examArray=document.getElementsByName("examRoom.id");
									//alert(examArray);
									var isOk=0;
									for(var i=0;i<examArray.length;i++){
										if(examArray[i].checked==true){
											//alert("OK");
											isOk=1;
										}
										//alert(examArray[i].checked);
									}
									//if(i==examArray.length){
									if(isOk!=1){
										alert("请选中考场！！！");
										return false;
									}
									if(window.confirm("确定选择此考场绑定？")){
										choose.action="class_choose_examroom.action?classId=<s:property value="classId" />&course.id=<s:property value="course.id" />";
										choose.submit();
									}
								}  
					</script>
					<s:if test="Return != null">
						<s:hidden name="Return"></s:hidden>
					</s:if>
					<br />
					<s:if test="#request.isBand==1">
						<font color="red">已经绑定</font>
					</s:if>
					<s:else>
						<input style="height: 35px;" class="textbg6" type="button"
							name="button2" onClick="chooses();" id="button2" value="选择考场" />
					</s:else>
				</s:form>
			</s:else>
			<br> 
			<s:if test="Return != null">
				<a style="color: red;" href="erwithout_addInit.action?course.id=<s:property value="course.id"/>&choose=false&course.classid=<s:property value="classId"/>&Return=<s:property value="Return"/>" class="textbg">添加考场</a>
			</s:if><s:else>
				 <a style="color: red;" href="erwithout_addInit.action?course.id=<s:property value="course.id"/>&choose=false&course.classid=<s:property value="classId"/>" class="textbg">添加考场</a>
			</s:else>
			<a href="elclass_course.action?elclass.id=${elclass.id }"
						class=textbg>班级课程</a>
			<a href="elclass_view_man.action?elclass.id=${elclass.id }&sublibs=1"
						class=textbg>班级详情</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
