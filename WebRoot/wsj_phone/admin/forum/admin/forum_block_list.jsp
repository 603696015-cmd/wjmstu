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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
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
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">版面列表</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="forum_block_addInit.action">添加新版面</a>
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
		</s:if>
		<s:else>
			<s:set name="fbtsize" value="fbtypes.size"></s:set>
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<td align="center" bgcolor="#66CCFF">
						名称
					</td>
					<td align="center" bgcolor="#66CCFF">
						描述
					</td>
					<td align="center" bgcolor="#66CCFF">
						版主
					</td>
					<td bgcolor="#66CCFF">
					</td>
					<!--<td>
					</td>
					<td>
					</td>
				-->
				</tr>
				<s:iterator value="fbtypes" status="fbtst">
					<tr bgcolor="red">
						<th height="30" align="left"
							style="padding-left: 8px; color: blue;">
							<i><b> <s:property value="name" /> </b> </i>
						</th>
						<th align="center" bgcolor="#FFFFFF" colspan="5">
							<s:property value="description" />
						</th>
					</tr>
					<s:set name="fblocksize" value="fblocks.size"></s:set>
					<s:iterator value="fblocks" status="fbs" id="fbsid">
						<tr>
							<td height="40" align="left"
								style="padding-left: 8px; color: blue;">
								<s:property value="title" />						  </td>
							<td align="center">
								<s:property value="#fbsid.description" />
							</td>
							<td align="center">
								<s:property value="manager.realname" />
							</td>
							<td>
								<a
									href="forum_block_alterInit.action?fblock.id=<s:property value="id"/>" class="textbg4">修改</a>/

								<s:if test="#session.roleid==1">
									<a onClick="return window.confirm('确定删除？');"
										href="forum_block_delete.action?fblock.id=<s:property value="id"/>" class="textbg4">删除</a>
								</s:if>
								<s:else>
									<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');"
										class="textbg4">删 除</a>
								</s:else>
							</td>
							<!-- <td align="center" >
							<s:if test="#fbs.index!=0">	
							<a href="forum_block_downSort.action?fblock.id=<s:property value="id"/>">上移</a>
							</s:if>
							</td>
							<td align="center" >
								<s:if test="(#fbs.index+1)!=#fblocksize">	
								<a href="forum_block_upSort.action?fblock.id=<s:property value="id"/>">下移</a>
								</s:if>
							</td> -->
						</tr>
					</s:iterator>
				</s:iterator>
		  </table>
			<div style="width: 100%; text-align: center; margin-top: 30px;">
				<a href="forum_block_addInit.action" class=textbg>添加版面</a>
			</div>
		</s:else>
	
	</body>
</HTML>
