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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
					<wysLib:Navigation ivalue="版面分类总览" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">版面类别列表</span>
			</li>
				<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="forum_blocktype_addInit.action">添加新版面类别</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:if test="fbtypes.size==0">
			<div style="width: 100%; text-align: center; margin-top: 4px;">
				当前没有版面类别
			</div>
			<div style="width: 100%; text-align: center; margin-top: 4px;">
				<a href="forum_blocktype_addInit.action" class="textbg">添加新类别</a>
			</div>
		</s:if>
		<s:else>
			<s:set name="fbtsize" value="fbtypes.size"></s:set>
			<table width="100%" align="center" cellpadding="2" cellspacing="1">
				<tr>
					<th align="center">
						名称
					</th>
					<th align="center">
						描述
					</th>
					<th align="center">
					</th>
					<th align="center">
					</th>
					<th align="center">
					</th>
				</tr>
				<s:iterator value="fbtypes" status="fbtst">
					<tr>
						<td align="center" height="40">
							<s:property value="sortid" />
							、
							<s:property value="name" />					  </td>
						<td align="center">
							<s:property value="description" />
						</td>
						<td align="center">
							<a
								href="forum_blocktype_alterInit.action?fbtype.id=<s:property value="id"/>" class="textbg4">编辑</a>

							<s:if test="#session.roleid==1">
								<a
									href="forum_blocktype_delete.action?fbtype.id=<s:property value="id"/>"
									onclick="return confirm('确定删除？')" class="textbg4">删除</a>
							</s:if>
							<s:else>
								<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');"
									class="textbg4">删 除</a>
							</s:else>
						</td>
						<td align="center">
							<s:if test="#fbtst.index!=0">
								<a
									href="forum_blocktype_upSort.action?fbtype.id=<s:property value="id"/>" class="textbg4">上移</a>
							</s:if>
						</td>
						<td align="center">
							<s:if test="(#fbtst.index+1)!=#fbtsize">
								<a
									href="forum_blocktype_downSort.action?fbtype.id=<s:property value="id"/>" class="textbg4">下移</a>
							</s:if>
						</td>
					</tr>
				</s:iterator>
		  </table>
			<div style="width: 100%; text-align: center; margin-top: 30px;">
				<a href="forum_blocktype_addInit.action" class="textbg">添加新类别</a>
			</div>
			<p>&nbsp;
				
			</p>
		</s:else>
	</body>
</HTML>
