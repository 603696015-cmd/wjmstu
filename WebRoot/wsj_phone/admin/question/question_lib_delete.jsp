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
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="确认删除" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">删除试题库信息 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_lib_deleteInit.action?questionLib.id=<s:property value="questionLib.id"/>">编辑试题库信息
				</a>

			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_lib_alterInit.action?questionLib.id=<s:property value="questionLib.id"/>">查看试题库信息
				</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="question_lib_delete" method="post" theme="simple">
				<table width="700px" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="150" height="30" align="center" >
							<input type="hidden" name="id" id="id" value="6">
							确认要删除的目录
						</td>
						<td width="300" >
							<label>
								<s:property value="questionLib.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center" >
							所属试题及子目录
						</td>
						<td width="300" >
							<input type="radio" name="sub_operate" checked="checked"
								value="0">
							<label>
								并入上级试题库
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="sub_operate" value="1">
							<label>
								与本试题库同时删除
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="50" align="center" >
							<s:hidden name="questionLib.id" />
						</td>
						<td width="300" >
							<s:if test="#session.roleid==1">
								<input style="border: none;" class="textbg" onClick="return window.confirm('确定删除？');" name="submit" type="submit" value="删除" />
							</s:if>
							<s:else>
								<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');" class="textbg">删除</a>
							</s:else>
							<input class=textbg style="border: none" onclick="document.location='question_lib_view.action?questionLib.id=${questionLib.id }'" type="button" value="取 消" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
