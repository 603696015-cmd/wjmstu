<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold; color: #077ac7; font-size: 14px; margin-top:3px;">学员列表</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center; background-color:#D1E4F5;">

			<table align="center" cellpadding="0" cellspacing="1" width="100%"
				 bgcolor="#ECEDEB">
				<tr>
					<th bgcolor="#99FFFF">
						学员姓名	
					</th>
					<th bgcolor="#99FFFF">
						学员账号	
					</th>
					<th bgcolor="#99FFFF">
						余额	
					</th>
				</tr>
			
					<tr>
						<td height="30" align="center" bgcolor="#F8FCFE">
							<s:property value="balance.user.realname" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						-->
						
					  <td height="30" align="center" bgcolor="#F8FCFE">
							<s:property value="balance.user.username" />
						</td>
						<td height="30" align="center" bgcolor="#F8FCFE">
							<s:property value="balance.balance" />
						</td>
					</tr>
			</table>
<table align="center" cellpadding="0" cellspacing="1" width="100%"
				 bgcolor="#ECEDEB">
				<tr>
				<td height="40" align="right" bgcolor="#F8FCFE"><a href="order_chongzhi.action" class="textbg5">&nbsp;&nbsp;我要充值</a>
				    </td>
					</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
