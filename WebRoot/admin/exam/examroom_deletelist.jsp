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
		<script type="text/javascript" src="js/menu.js"></script>
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
				<span style="font-weight: bold;">考试删除</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;"> 
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td width="200px" valign="top"
							id="tree_list_td">
							<wysLib:eroomLibTree
								href="examroom_deletelist.action?eroomLib.id=" rootAble="true"></wysLib:eroomLibTree>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td>
							<form action="examroom_deletes.action" method="post">
								<table width="100%" align="center" cellspacing="1"
									cellpadding="1">
									<tr>
										<th width="70" height="30" align="center" >										</th>
										<th height="30" align="center" >
											考场标题
										</th>
										<!--<th height="30" align="center" >
											考场地点
										</th>
										<th height="30" align="center" >
											类别库
										</th>
										<th height="30" align="center" >
											通过百分比
										</th>-->
										<th width="110" height="30" align="center" >
											创建者									</th>
										<th width="110" height="30" align="center" >
											开始时间										</th>
										<th width="110" height="30" align="center" >
											结束时间										</th>
										<!--<th height="30" align="center" >
											类型
										</th>-->
										<th width="70" height="30" align="center" >
											审核状态										</th>
										<th width="60" height="30" align="center" >
											试卷数										</th>
									</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
									<s:iterator value="examRooms">
										<tr>
											<td width="70" height="30" align="center" >
												<s:if test="valid!=1"><input type="checkbox" value="<s:property value="id" />"
													name="examRooms.id"></s:if>
													<s:else>不能删除</s:else>
										  </td>
											<td height="30" style="padding-left:8px;color:blue;" align="left">
												<s:property value="title" />
											</td>
											<td height="30" align="center" >
												<s:property value="creater.realname" />
											</td>
											<!--<td height="30" align="center" >
												<s:property value="location" />
											</td>
											<td height="30" align="center" >
												<s:property value="eroomLib.name" />
											</td>
											<td height="30" align="center" >
												<s:property value="passgrade" />
											</td>-->
											<td width="110" height="30" align="center" >
												<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
										  </td>
											<td width="110" height="30" align="center" >
												<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
										  </td>
											<!--<td height="30" align="center" >
												<s:property value="typeName" />
											</td>-->
											<td width="70" height="30" align="center" >
												<font color="<s:if test="valid==1">red</s:if>">
												<s:property
														value="validName" />
											  </font>
										  </td>
											<td width="60" height="30" align="center" >
												<s:property value="epsize" />
										  </td>
										</tr>
									</s:iterator></tbody>
							  </table>
								<script type="text/javascript">
									function select_All(){
										var cks= document.getElementsByName("examRooms.id");
										for(var i = 0 ; i < cks.length; i++){
											cks[i].checked= true;
										}
									}
									function select_Fan(){
										var cks= document.getElementsByName("examRooms.id");
										for(var i = 0 ; i < cks.length; i++){
											cks[i].checked= !cks[i].checked;
										}
									}
									function select_Bux(){
										var cks= document.getElementsByName("examRooms.id");
										for(var i = 0 ; i < cks.length; i++){
											cks[i].checked= false;
										}
									}
								</script>
								<a href="javascript:select_All()" />全选</a>
								<a href="javascript:select_Fan()" />反选</a>
								<a href="javascript:select_Bux()" />全不选</a>
								<input style="height:35px;" class="textbg4" type="submit" value="删除" />
							</form>
							<wysLib:page></wysLib:page>
						</td>
					</tr>
			  </table> 
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
