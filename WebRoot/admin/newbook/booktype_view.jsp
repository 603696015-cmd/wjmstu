<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看目录基本信息" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">图书类型信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="bookType_addInit.action">添加新图书类型</a>

			</li>
		</ul>
		<table width="70%">
			<tr>
			<td valign="top" width="200px" id="tree_list_td">
				<wysLib:testbooktypeTree itype="OP" did="0" rootAble="true" href="bookType_view.action?btype.id=" ></wysLib:testbooktypeTree>
		</td>
			<td valign="middle" width="5px;" style="padding: 0px" >
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
										onclick="changeTreeDisplay(this)" />
					</td>
			<td valign="top"><table width="100%" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="center" >
						栏目名称
					</td>
					<td >
						<label>
							<s:property value="btype.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						栏目介绍
					</td>
					<td >
						<label>
							<s:property value="btype.description"/>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						上级栏目
					</td>
					<td >
						<label>
							<s:property value="btype.parent.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center" >
						&nbsp;
					</td>
					<td >
						<a href="bookType_updinit.action?btype.id=<s:property value="btype.id"/>" class=textbg>修改</a>
						<s:if test="btype.id==1">
									<a href="javascript:alert('您不能删除根节点.');" class="textbg">删 除</a>
									
								</s:if>
								<s:else>
									<a href="deleteBooktype.action?btype.id=<s:property value="btype.id"/>" onclick="javascript:return window.confirm('确定删除？')" class=textbg>删除</a>
								</s:else>
						
					</td>
				</tr>
			</table></td>
			</tr>
		</table>
			
	</body>
</HTML>
