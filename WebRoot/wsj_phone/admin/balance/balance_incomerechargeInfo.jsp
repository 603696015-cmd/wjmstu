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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY>
	
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!-- 内容 -->
		<div style="text-align:left; width:320px;">
			<table width="320" border="0" align="left" cellpadding="0" cellspacing="1"
				 bgcolor="#D1E4F5">
				 <caption  >
						 明细信息
					</caption>
				<tr>
					<th bgcolor="#F8FCFE">
						学员姓名	
					</th>
					<th bgcolor="#F8FCFE">
						操作者	
					</th>
					<th bgcolor="#F8FCFE">
						金额	
					</th>
					<th bgcolor="#F8FCFE">备注
					</th>
				</tr>
					<tr>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:property value="rechargeInfo.reusername" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						-->
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:property value="rechargeInfo.username" />
						</td>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:property value="rechargeInfo.Addbalance" />
						</td>
						<td height="20" align="center" bgcolor="#F8FCFE">
							<s:if test="rechargeInfo.type==3">手工增资</s:if>
							<s:if test="rechargeInfo.type==2">余额转移</s:if>
							<s:if test="rechargeInfo.type==1">充值</s:if>
						</td>
					</tr>
			</table>
			  <a onClick="javascript:history.back(-1);" class=textbg4>返回</a>
			
</div>
		<!-- 内容 -->
	
	</body>
</HTML>
