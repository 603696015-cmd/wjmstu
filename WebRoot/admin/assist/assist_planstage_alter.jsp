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
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
				<caption>
					计划信息
				</caption>
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
				</tbody>
			</table>
			<s:property value="elmessage" />
			<script type="text/javascript">size = <s:property value="plan.planStages.size"/>
			sizeinit=size;</script>
			<form action="assist_planstage_alter.action" method="post">
				<s:if test="elmessage!='修改成功！'"><s:hidden name="planStage.id"></s:hidden></s:if>
				<TABLE cellSpacing=1 cellPadding=2 width="70%" align=center
					bgColor=#ebebeb>
					<caption>
						计划分段实施(制定培训计划第二步 ：填写分阶段实施表)
					</caption>
					<TBODY id="stageTable">
						<TR>
							<TH align=center bgColor=#ffffff height=30>
								序 号
							</TH>
							<TH align=center bgColor=#ffffff height=30>
								工作内容
							</TH>
							<TH align=center bgColor=#ffffff>
								计划完成时间
							</TH>
							<TH align=center bgColor=#ffffff>
								所需时间
							</TH>
							<TH align=center bgColor=#ffffff>
							</TH>
						</TR>
						<s:set name="plsid" value="planStage.id"></s:set>
						<s:set name="planid" value="plan.id"></s:set>
						<s:iterator value="plan.planStages" status="ps">
							<tr id="<s:property value="#ps.index+1"/>">
								<td align=center bgcolor=#ffffff>
									阶段
									<s:property value="#ps.index+1" />
								</td>
								<td align=center bgcolor=#ffffff>
									<s:if test="#plsid==id&&elmessage!='修改成功！'">
										<textarea name="planStage.content"><s:property value="content" /></textarea>
									</s:if>
									<s:else>
										<s:property value="content" />
									</s:else>
								</td>
								<td align=center bgcolor=#ffffff>
									<s:if test="#plsid==id&&elmessage!='修改成功！'">
										<input name='planStage.planfinishdate'
											value="<s:date name="planfinishdate" format="yyyy-MM-dd"/>"
											onclick='setday(this)' />
									</s:if>
									<s:else>
										<s:date name="planfinishdate" format="yyyy-MM-dd" />
									</s:else>
								</td>
								<td align=center bgcolor=#ffffff>
									<s:if test="#plsid==id&&elmessage!='修改成功！'">
										<input name='planStage.plandays'
											value="<s:property value="plandays" />" />
									</s:if>
									<s:else>
										<s:property value="plandays" />
									</s:else>
								</td>
								<td align=center bgcolor=#ffffff>
									<a
										href="assist_planstage_alterInit.action?planStage.id=<s:property value="id"/>&plan.id=<s:property value="#planid"/>">修改</a>
								</td>
							</tr>
						</s:iterator>
					</TBODY>
				</TABLE>
				<s:hidden name="plan.id"></s:hidden>
				<s:if test="elmessage!='修改成功！'"><input type="submit" name="Submit" value="确认提交"></s:if>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
