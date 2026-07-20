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
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold;">记录列表</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="re.size==0">没有记录</s:if>
			<s:else>
			<table align="center" cellpadding="0" cellspacing="1" width="100%"
				 bgcolor="#D1E4F5">
				<tr>
					<th bgcolor="#F8FCFE">
						时间	
					</th>
					<th bgcolor="#F8FCFE">
						学员姓名	
					</th>
					<th bgcolor="#F8FCFE">
						学员账号	
					</th>
					<th bgcolor="#F8FCFE">
						操作者	
					</th>
					<th bgcolor="#F8FCFE">
						增资数额	
					</th>
					<th bgcolor="#F8FCFE">备注
					</th>
				</tr>
				<s:iterator value="re">
					<tr>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:date name="Rechargedate" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:property value="reusername" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						-->
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:property value="reuserid" />
						</td>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:property value="username" />
						</td>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:property value="Addbalance" />
						</td>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:if test="type==3">线下交易</s:if>
							<s:if test="type==2">余额转移</s:if>
						</td>
					</tr>
				</s:iterator>
			</table>
</s:else>
<a onClick="javascript:history.back(-1);" class=textbg4>返回</a>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
