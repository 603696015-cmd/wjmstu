<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
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
		<TITLE>考场类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			depid = <s:property value="eroomLib.id"/> ;
			$.post("erlib_delete_user.action", {
				"elUser.id":id,
				"eroomLib.id":depid,
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
.STYLE1 {
	color: #FF0000
}
-->
</style>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">显示考场类别信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroomlib_alterInit.action?eroomLib.id=<s:property value="eroomLib.id" />">编辑考场类别信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroomlib_deleteInit.action?eroomLib.id=<s:property value="eroomLib.id" />">删除考场类别</a>
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
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px;" valign="top">
						<wysLib:wordsTree did="0"
							href="word_view.action?word.id="></wysLib:wordsTree>
					</td>
					<td valign="top" style="padding: 0px;">
						<table width="100%" style="margin: 0px" height="100%" align="center"
							cellpadding="1" cellspacing="1">
							<tr>
								<td width="120" height="30" align="right">
									词汇库名称：
								</td>
								<td width="258">
									<label>
										<s:property value="word.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									简介：
								</td>
								<td>
									<label>
										<s:property value="word.description" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									上级词汇库：
								</td>
								<td>
									<label>
										<s:property value="word.parent.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" align="right">
									对应单元：
								</td>
								<td>
									<div>
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="course.name" />
												</label> <span class="STYLE1">＊</span> </span>
									</div>
								</td>
							</tr>
							<!--<tr>
								<td width="120" align="center" >
									可使用人员：
								</td>
								<td >
									<div>
										<s:iterator value="eroomLib.useusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
											</label> <span class="STYLE1">＊</span>   -<a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'use');return false;">X</a> 
											</span>
										</s:iterator>
									</div>
								</td>
							</tr>-->
							<tr>
								<td></td>
								<td>
									<a
										href="wordlib_alterInit.action?word.id=<s:property value="word.id" />"
										class=textbg5>编 辑</a>


									<s:if test="#session.roleid==1">
										<a
											href="wordslib_deleteInit.action?word.id=<s:property value="word.id" />"
											class=textbg5>删 除</a>
									</s:if>
									<s:else>
										<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');"
											class=textbg5>删 除</a>
									</s:else>
									
									<input style="border: none;" onClick="document.location='word_list.action'" class="textbg5" type="button" value="返回词汇类别">
									<input style="border: none;" onClick="document.location='wordslib_addInit.action'" class="textbg5" type="button" value="添加新类别">
									
									
									<input style="border: none;" onClick="document.location='examroom_alllist.action?sublibs=1&examRoom.eroomLib.id=<s:property value="eroomLib.id"/>&examRoom.valid=-1&examRoom.classid=-1'" class="textbg5" type="button" value="考场列表">
									        
									          
									
								   </td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
