<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.IntelligentSystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
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
		<TITLE>智能辅导分明细</TITLE>
		<base href="<%=basePath%>">
		<base target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height: 30px;
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?     "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="" />
							</div>
						</li>
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;" class="divClass">
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<table width="800px" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200" valign="center" align="center">
						<p>
							考查类别
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							考查指标
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							满分（分）
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							得分（分）
						</p>
					</td>
					<td width="120" align="center" valign="middle" background="images/bg002.jpg"
											>
											
									  </td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
							学习时间
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							每天学习时间加分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGIN) %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.scoreLogin"/>
						</p>
					</td>
					 <td  align="center" >
					 <a  class="textbg4" href="loginInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							每周的学习时间加分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEK) %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.scoreWeek"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="weekInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							等级总学习时间得分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASS) %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.scoreClass"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="classInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
							学习习惯
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							复听比例智能辅导分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESS) %>
						</p>
					</td>
					<td width="200" valign="center" align="center" >
						<p>
							<s:property value="intelligentTutoringPoints.scoreProportionQ"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="proportionQInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							复听次数智能辅导分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIME) %>
						</p>
					</td>
					<td width="200" valign="center" align="center" >
						<p>
							<s:property value="intelligentTutoringPoints.scoreProportionT"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="proportionTInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							录音比例智能辅导分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESS) %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.scoreRecodingQ"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="recodingQInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							录音次数智能辅导分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIME) %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.scoreRecodingT"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="recodingTInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
							学习成绩
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							模块练习成绩智能辅分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGE) %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.scoreAcademic"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="academicInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							单元测验辅导分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE) %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.scoreAcademicCourse"/>
						</p>
					</td>
					<td  align="center" >
					 <a  class="textbg4" href="academicCourseInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">
					 详 情</a></td>
				</tr>
				<tr>
					<td width="200" valign="center" align="center">
						<p>
							总分
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<%
								double totalScore = 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGIN) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEK) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASS) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESS) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIME) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESS) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIME) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGE) + 
									IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE);
							%>
							<%=totalScore %>
						</p>
					</td>
					<td width="200" valign="center" align="center">
						<p>
							<s:property value="intelligentTutoringPoints.totalScore"/>
						</p>
					</td>
					<td  align="center" >
					 ---</td>
				</tr>
			</table>
		</div>
	</BODY>
</HTML>






