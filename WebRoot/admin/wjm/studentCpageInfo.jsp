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
		<TITLE>模块学习</TITLE>
		<base href="<%=basePath%>">
		<base target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function toexcel(exprot) { 
			document.getElementById("exprot").value=exprot;
			document.getElementById("elUser.id").value = <s:property value="elUser.id" />;
			document.getElementById("elClass.id").value = <s:property value="elClass.id" />;
			document.getElementById("course.id").value = <s:property value="course.id" />;
			studentCpageInfo.submit();
		}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height: 30px;
}

tr {
	background-color: expression((               this .               sectionRowIndex % 
		             2 ==  
		
		           0) ?               "#ffffff" :               "#f4f4f4" )
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
			<form action="studentCpageInfo.action" name="studentCpageInfo"
				method="post">
				<s:hidden name="exprot" id="exprot" />
				<input type="hidden" name="elUser.id" id="elUser.id" />
				<input type="hidden" name="elClass.id" id="elClass.id" />
				<input type="hidden" name="course.id" id="course.id" />
			</form>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" border="0" cellpadding="5" cellspacing="1"
				bgcolor="#CFDBE2">

				<tr>
					<td height="40" colspan="2" align="left"
						background="images/bg002.jpg" bgcolor="#E9F5FC"
						style="padding-left: 25px;">
						章节和考场
					</td>
					<td width="180" align="center" background="images/bg002.jpg"
						bgcolor="#E9F5FC">
						完成时间
					</td>
					<td width="80" align="center" background="images/bg002.jpg"
						bgcolor="#E9F5FC">
						成 绩
					</td>
					<td width="80" align="center" background="images/bg002.jpg"
						bgcolor="#E9F5FC">
						答 卷
					</td>
				</tr>
				<s:iterator value="myCPages" status="stu">
					<s:iterator value="examRooms" status="status">
						<s:if test="#status.index==0">
							<tr>
								
								<td height="40" valign="middle" bgcolor="#F8FCFE" rowspan="<s:property value="examRooms.size()" />">
									<span style="color: green">【章节】</span>
									<s:property value="cpage.title" />
								</td>

								<td height="40" valign="middle" bgcolor="#F8FCFE">
									<span style="color: red">【考试】</span>
									<s:property value="title" />

								</td>
								<td width="180" align="center" bgcolor="#F8FCFE" class="bt001">
									<s:if test="myExamPaper.endtime!=null">
										<s:date name="myExamPaper.endtime" format="yyyy年MM月dd日 HH时mm分" />
									</s:if>
								</td>
								<td width="80" align="center" bgcolor="#F8FCFE" class="bt001">
									<s:property value="myExamPaper.myScore" />
								</td>
								<td width="80" align="center" bgcolor="#F8FCFE" class="bt001">
									<s:if test="myExamPaper.endtime!=null">
										<a target="_blank"
											href="myquizpaperview.action?myExamPaper.id=<s:property value="myExamPaper.id" />">
											<img src="images/dajuan.jpg" width="30" height="25" /> </a>
									</s:if>
									<s:else>
										暂无答卷
									</s:else>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr>
								<td height="40" valign="middle" bgcolor="#F8FCFE">
									<span style="color: red">【考试】</span>
									<s:property value="title" />

								</td>
								<td width="180" align="center" bgcolor="#F8FCFE" class="bt001">
									<s:if test="myExamPaper.endtime!=null">
										<s:date name="myExamPaper.endtime" format="yyyy年MM月dd日 HH时mm分" />
									</s:if>
								</td>
								<td width="80" align="center" bgcolor="#F8FCFE" class="bt001">
									<s:property value="myExamPaper.myScore" />
								</td>
								<td width="80" align="center" bgcolor="#F8FCFE" class="bt001">
									<s:if test="myExamPaper.endtime!=null">
										<a target="_blank"
											href="myquizpaperview.action?myExamPaper.id=<s:property value="myExamPaper.id" />">
											<img src="images/dajuan.jpg" width="30" height="25" /> </a>
									</s:if>
									<s:else>
										暂无答卷
									</s:else>
								</td>
							</tr>
						</s:else>
					</s:iterator>
				</s:iterator>
			</table>
			<a href="javascript:toexcel(true);" class="textbg5">导出Excel</a>
		</div>
	</BODY>
</HTML>






