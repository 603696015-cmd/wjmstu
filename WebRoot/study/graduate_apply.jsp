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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">可结业培训班</span>
			</li>-->
		</ul>
		<s:if test="myClasses.size==0">
			<div style="width: 100%; text-align: center; margin-top: 30px;">
				当前没有可结业的培训班
			</div>
		</s:if>
		<s:else>
			<table width="86%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<th align="center" >
						培训班名称
					</th>
					<th align="center" >
						创建时间
					</th>
					<th align="center" >
						必修课（总数/我完成数）
					</th>
					<th align="center" >
						选修课（总学分/最低/我的学分）
					</th>
					<th align="center" >
						加入时间
					</th>
					<th align="center" >
						&nbsp;
					</th>
				</tr>
				<s:iterator value="myClasses">
					<tr>
						<td align="center" >
							<s:property value="elClass.name" />
						</td>
						<td align="center" >
							<s:date name="elClass.createtime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<s:property value="elClass.bxCount" />
							/
							<s:property value="bxCount" />
						</td>
						<td align="center" >
							<s:property value="elClass.xxCredit" />
							/
							<s:property value="elClass.optionalcredit" />
							/
							<s:property value="xxCredit" />
						</td>
						<td align="center" >
							<s:date name="begintime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<a
								href="graduate_apply.action?elclass.id=<s:property value="elClass.id" />">申请结业</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:else>
	</body>
</HTML>
