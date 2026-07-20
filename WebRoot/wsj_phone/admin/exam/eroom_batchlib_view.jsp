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
		<TITLE>考场批次类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
			<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			depid = <s:property value="erbatchLib.id"/> ;
			$.post("erblib_delete_user.action", {
				"elUser.id":id,
				"erbatchLib.id":depid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		</script>
	  <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="显示类别信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">显示考场批次类别信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroom_batchlib_alterInit.action?erbatchLib.id=<s:property value="erbatchLib.id" />">编辑考场批次类别信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroom_batchlib_deleteInit.action?erbatchLib.id=<s:property value="erbatchLib.id" />">删除考场批次类别</a>
			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px;" valign="top">
						<wysLib:erbLibTree did="0"
							href="eroom_batchlib_view.action?erbatchLib.id="></wysLib:erbLibTree>
					</td>
					<td valign="top" style="padding: 0px;">
						<table width="100%" style="margin: 0px" height="100%" align="left"
							cellpadding="1" cellspacing="1" >
							<tr>
								<td width="120" height="30" align="center" >
									类别名称
								</td>
								<td >
									<label>
										<s:property value="erbatchLib.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="center" >
									类别介绍
								</td>
								<td >
									<label>
										<s:property value="erbatchLib.description" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="center" >
									上级类别
								</td>
								<td >
									<label>
										<s:property value="erbatchLib.parent.name" />
									</label>
								</td>
							</tr>
							<!--<tr>
								<td width="120" align="center" >
									可管理人员：
								</td>
								<td >
									<div>
										<s:iterator value="erbatchLib.opusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
											</label> <span class="STYLE1">＊</span> <a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'op');return false;">X</a>
											</span>
										</s:iterator>
									</div>
								</td>
							</tr>-->
							<tr>
								<td width="120" align="center" >
									可使用人员：
								</td>
								<td >
									<div>
										<s:iterator value="erbatchLib.useusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
											</label> <span class="STYLE1">＊</span> <!--<a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'use');return false;">X</a>-->
											</span>
										</s:iterator>
									</div>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<a
										href="eroom_batchlib_alterInit.action?erbatchLib.id=<s:property value="erbatchLib.id" />" class=textbg>编 辑</a>

								
								<s:if test="#session.roleid==1">
									<a href="eroom_batchlib_deleteInit.action?erbatchLib.id=<s:property value="erbatchLib.id" />" class=textbg>删 除</a></td>
								</s:if>
								<s:else>
									<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');" class="textbg">删 除</a>
								</s:else>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
