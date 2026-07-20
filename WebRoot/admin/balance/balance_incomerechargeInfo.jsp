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
		<!-- 内容 -->
		<div style="margin-top: 27px; text-align: center;">
			<table align="center" cellpadding="2" cellspacing="2" width="100%"
				 bgcolor="#ECEDEB">
				 <caption  >
						 明细信息
					</caption>
				<tr>
					<th>
						时间	
					</th>
					<th>
						学员姓名	
					</th>
					<th>
						学员账号	
					</th>
					<th>
						操作者	
					</th>
					<th>
						金额	
					</th>
					<th>备注
					</th>
				</tr>
					<tr>
						<td height="20" align="center">
							<s:date name="rechargeInfo.Rechargedate" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td height="20" align="center">
							<s:property value="rechargeInfo.reusername" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						-->
						<td height="20" align="center">
							<s:property value="rechargeInfo.reuserid" />
						</td>
						<td height="20" align="center">
							<s:property value="rechargeInfo.username" />
						</td>
						<td height="20" align="center">
							<s:property value="rechargeInfo.Addbalance" />
						</td>
						<td height="20" align="center">
							<s:if test="rechargeInfo.type==3">手工增资</s:if>
							<s:if test="rechargeInfo.type==2">余额转移</s:if>
							<s:if test="rechargeInfo.type==1">充值</s:if>
						</td>
					</tr>
			</table>
			  <a onClick="javascript:history.back(-1);" class=textbg4>返回</a>
			
	</div>
		<!-- 内容 -->
	</BODY>
</HTML>
