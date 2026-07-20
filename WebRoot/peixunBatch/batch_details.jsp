<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.sopia.classman.entities.ElClass"%>

<%
	String cltypeName = "";
	if (request.getAttribute("elclass") != null) {
		cltypeName = ((ElClass) request.getAttribute("elclass"))
				.getCltype().getName()
				+ "";
	}

	ElClType cltypeTree = (ElClType) request.getAttribute("cltypeTree");
%>

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<div class="dh3">
			<!--<div class="newpos"></div> 
			<div class="newpos2"> 
				<span style="font-weight: bold;">培训班详情</span>
			</div>-->
			<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				<wysLib:Navigation ivalue="培训批次详情页" />
			</div>
		</div>
		<s:form action="elclass_add" theme="simple" method="post"
			name="class_info" id="class_info">
			<table width="100%" cellpadding="2" cellspacing="1">

				<tr>
					<td width="50" height="30" align="center" bgcolor="#FFFFFF">
						名称：
					</td>
					<td height="30">
						<label>
							<s:property value="peixunBatch.name" />
						</label>
					</td>
				</tr>

				<tr>
					<td width="50" height="30" align="center" bgcolor="#FFFFFF">
						简介：
					</td>
					<td height="30">
						<label>
							<s:property value="peixunBatch.description" />
						</label>
					</td>
				</tr>
			</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="middle" class="tablequiz">
						<ul class="nav">
							<!--<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看详情" /></div>
			</li>-->
							<li>
								<span style="font-weight: bold;">培训班列表</span>
							</li>

						</ul>
					</td>
					<td width="120" valign="middle" class="tablequiz">
						<A id=quit href="javascript:window.parent.full_screen(false);"
							class="textbg6" style="display: none">退出全屏</A>
					</td>
				</tr>
			</table>

			<!-- 内容 -->
			<div style="margin-top: 0px; text-align: center;">

				<div>
					<table width="100%" align="center" cellpadding="1" cellspacing="1"
						id="table1">
							<s:if test="elclasses.size==0">当前还没有分配班级</s:if>
							<s:else>
						<tr>
							<th width="60"  align="center">
								序号
							</th>
							<th width="180"  align="center">
								培训班名称
							</th>
							<!--  <th width="60"  align="center">
								类别
							</th>-->
							<th width="100"  align="center">
								创建时间
							</th>
							<th width="100" height="30" align="center">
								类型
							</th>
						</tr>
						<tbody >
							<s:iterator value="elclasses">
								<tr>

									<td height="30" align="center">
										<s:property value="id" />
									</td>
									<td height="30" align="center">
										<s:property value="name" />
									</td>
									<!--  <td height="30" align="center">
										<s:property value="classtype" />
									</td>-->
									<td id="start" height="30" align="center">
										<s:date name="createtime" format="yyyy-MM-dd~HH:mm:ss" />
									</td>
									<td height="30" align="center">
									
										<s:property value="baseData.basevalue" />
									</td>
								</tr>
							</s:iterator>
						</tbody>
						</s:else>
					</table>
				</div>
			</div>
 
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="middle" class="tablequiz">
						<ul class="nav">
							<!--<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看详情" /></div>
			</li>-->
							<li>
								<span style="font-weight: bold;">学员列表</span>
							</li>

						</ul>
					</td>
					<td width="120" valign="middle" class="tablequiz">
						<A id=quit href="javascript:window.parent.full_screen(false);"
							class="textbg6" style="display: none">退出全屏</A>
					</td>
				</tr>
			</table>

			<!-- 内容 -->
			<div style="margin-top: 0px; text-align: center;">

				<div>
					<table width="100%" align="center" cellpadding="1" cellspacing="1"
						id="table1">
						<tr>

							<td align="left" valign="top" bgcolor="#FFFFFF">
								<s:if test="elusers.size==0">当前还没有分配学员</s:if>
								<s:else>
									<table width="100%" align="center" cellpadding="1"
										cellspacing="1">
										<tr>
											<td height="30" align="center">
												姓名
											</td>
											<td height="30" align="center">
												性别
											</td>
											<td height="30" align="center">
												账号
											</td>
											<td height="30" align="center">
												部门
											</td>
											<td height="30" align="center">
												<wysLib:BasetName btid="1" />
											</td>
											<td height="30" align="center">
												年龄
											</td>
											<td height="30" align="center">
												角色
											</td>
											<td height="30" align="center">
												分配
											</td>
											<td height="30" align="center">
												参加方式
											</td>
										</tr>
										
											<s:iterator value="elusers">
												<tr>
													<td height="30" style="color: blue;" align="center">
														<s:property value="realname" />
													</td>
													<td height="30" align="center">
														<s:property value="sex" />
													</td>
													<td height="30" align="center">
														<s:property value="username" />
													</td>
													<td height="30" align="center">
														<s:property value="department.name" />
													</td>
													<td height="30" align="center">
														<s:property value="station.name" />
													</td>
													<td height="30" align="center">
														<s:property value="age" />
													</td>
													<td height="30" align="center">
														<s:property value="role.name" />
													</td>
													<td height="30" align="center">
														<s:property value="isAssign" />
													</td>
													<td height="30" align="center">
														<s:property value="joinway" />
													</td>

												</tr>
											</s:iterator>
										</s:else>
							</td>
						</tr>
					</table>
					<wysLib:page></wysLib:page>
					</table>
				</div>

			</div>
			

		</s:form>
		<br />
		<div
			style="text-align: center; padding-top: 10px; padding-bottom: 20px;">
			<a style="cursor: hand" class="textbg6" href="#"
				onClick="addElclass();false;" style="width: 100px">添加培训班</a>
			<!--  <a href="batch_add_elclass.action?peixunBatch.id=${peixunBatch.id}" class=textbg6 style="width: 100px">添加培训班</a>-->
			&nbsp;&nbsp;&nbsp;&nbsp;
			<s:if test="elclasses.size!=0"><a
				href="batch_assigntoUsersInit.action?peixunBatch.id=${peixunBatch.id}"
				class="textbg6">分配学员</a></s:if>
			
		
		</div>
	</body>
	<SCRIPT type="text/javascript">
			function addElclass(){
				var width=1100;
				var height=500;
				var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var x = window.showModalDialog ('batch_elclass_List.action?peixunBatch.id=${peixunBatch.id}&peixunBatch.status=0&x='+Math.random(),null,sFeature);
				document.location.href= document.location.href;
				/*window.setInterval(function(){
					document.location.href= document.location.href;
				},100);*/
				//window.open ('elclass_course_selectList.action?elclassId=${elclassId}&status=0','选择课程','height=500,width=1100,top=100,left=100,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
				//window.open ('page.html','newwindow','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
			}
	</SCRIPT>
</HTML>
