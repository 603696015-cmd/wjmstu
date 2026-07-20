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
					<wysLib:Navigation ivalue="确认删除" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">知识库删除</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledgetype_list.action">知识库管理
				</a>

			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledgetype_alterInit.action?kltype.id=<s:property value='kltype.id'/>">知识库修改
				</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="knowledgetype_delete" method="post" theme="simple">
				<table border="0" align="center" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td width="150" height="30" align="center">
							<input type="hidden" name="id" id="id" value="6">
							确认要删除的知识类别
						</td>
						<td width="300">
							<label>
								<s:property value="kltype.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center">
							所属类别及子目录
						</td>
						<td width="300">
							<input type="radio" name="sub_operate" checked="checked"
								value="0">
							<label>
								并入上级类别
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="sub_operate" value="1">
							<label>
								与本类别一起删除
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="50" align="center">
							<s:hidden name="kltype.id" />
						</td>
						<td width="300">

							<s:if test="#session.roleid==1">
								<input onClick="return window.confirm('确定删除？');" name="submit"
									type="submit" class="textbg6" value="确认删除" />
							</s:if>
							<s:else>
								<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');"
									class="textbg4">删 除</a>
							</s:else>&nbsp;&nbsp;&nbsp;&nbsp;
							<a style="" href="knowledgetype_alterInit.action?kltype.id=<s:property value="kltype.id"/>"
								class="textbg6">取消</a>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
