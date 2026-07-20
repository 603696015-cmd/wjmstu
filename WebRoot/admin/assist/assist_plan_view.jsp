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
				<span style="font-weight: bold;">制定培训计划</span>
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
			<table cellspacing=1 cellpadding=2 width="70%" align=center
				bgcolor=#ebebeb>
				<tbody>
					<tr>
						<td width="100" align=center bgcolor=#ffffff>
							计划名称
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.name" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							培训内容
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.content" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							参加人
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.participator" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							负责人
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.manager.realname" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							联系人
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.contact" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							状态
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.statusName" />
						</td>
					</tr>
					<s:if test="plan.status==0">
					<tr>
						<td align=center bgcolor=#ffffff>
						</td>
						<td align=center bgcolor=#ffffff>
							<br>
							<a
								href="assist_plan_alterInit.action?plan.id=<s:property value="plan.id"/>">修改</a>
						</td>
					</tr>
					</s:if>
				</tbody>
			</table>
			<s:if test="plan.status==0">
			<a
				href="assist_planstage_addInit.action?plan.id=<s:property value="plan.id"/>">增加新阶段</a>
			</s:if>
			<table cellspacing=1 cellpadding=2 width="70%" align=center
				bgcolor=#ebebeb>
				<caption>
					各阶段信息
				</caption>
				<tbody>
					<tr>
						<td align=center bgcolor=#ffffff height=30>
							序 号
						</td>
						<td align=center bgcolor=#ffffff height=30>
							工作内容
						</td>
						<td align=center bgcolor=#ffffff>
							计划完成时间
						</td>
						<td align=center bgcolor=#ffffff>
							所需时间
						</td>
						<td align=center bgcolor=#ffffff>
							 
						</td>
					</tr>
						<s:set name="planid" value="plan.id"></s:set>
						<s:set name="planstatus" value="plan.status"></s:set>
				<s:iterator value="plan.planStages" status="st">
						<tr>
							<td align=center bgcolor=#ffffff>
								阶段<s:property value="#st.index+1"/>
							</td>
							<td align=center bgcolor=#ffffff>
								<s:property value="content" />
							</td>
							<td align=center bgcolor=#ffffff>
								<s:date name="planfinishdate" format="yyyy-MM-dd"/>
							</td>
							<td align=center bgcolor=#ffffff>
									<s:property value="plandays" />
							</td>
							<td align=center bgcolor=#ffffff>
							<s:if test="#planstatus==0">
								<a onclick="return window.confirm('确定删除？');" href="assist_planstage_delete.action?planStage.id=<s:property value="id"/>&plan.id=<s:property value="#planid"/>">删除</a>
								<a href="assist_planstage_alterInit.action?planStage.id=<s:property value="id"/>&plan.id=<s:property value="#planid"/>" >修改</a>
							</s:if>
							<s:else>
							删除/修改
							</s:else>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
