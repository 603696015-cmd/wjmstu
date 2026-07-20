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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">培训计划列表</span>
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
			<s:if test="plans.size==0">没有符合条件的计划</s:if>
			<s:else>
				<table width="85%" cellpadding="2" cellspacing="1" bgcolor="#EBEBEB">
					<tr>
						<th align="center" >
							名称
						</th>
						<th align="center" >
							发布人
						</th>
						<th align="center" >
							创建时间
						</th>
						<th align="center" >
							参与人
						</th>
						<th align="center" >
							联系人
						</th>
						<th align="center" >
							状态
						</th>
						<th colspan="4" align="center" >
						</th>
					</tr>
					<s:iterator value="plans">
						<tr>
							<td align="center" >
								<s:property value="name" />
							</td>
							<td align="center" >
								<s:property value="manager.realname" />
							</td>
							<td align="center" >
								<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center" >
								<s:property value="participator" />
							</td>
							<td align="center" >
								<s:property value="contact" />
							</td>
							<td align="center" >
								<s:property value="statusName" />
							</td>
							<td align="center" >
								<s:if test="status==0">
								<a
									href="assist_plan_alterInit.action?plan.id=<s:property value="id" />">修改</a>
								</s:if>
								<s:else>
								修改
								</s:else>
							</td>
							<td align="center" >
								<a
									href="assist_plan_view.action?plan.id=<s:property value="id" />">查看</a>
							</td>
							<td align="center" >
								<s:if test="status==0">
									<a
										href="assist_plan_apply.action?plan.id=<s:property value="id" />&pN=<s:property value="pN"/>&pS=10">提交审核</a>
								</s:if>
								<s:else>提交审核</s:else>
							</td>
							<td align="center" >
								<s:if test="status==2">
								<a
									href="assist_plan_carryoutInit.action?plan.id=<s:property value="id" />">实施计划</a>
								</s:if>
								<s:else>
								实施计划
								</s:else>
							</td>
						</tr>
					</s:iterator>
				</table>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
