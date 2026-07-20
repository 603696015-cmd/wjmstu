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
		<TITLE>单元学习</TITLE>
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
			studentCourseInfo.submit();
		}
		function showStudentCpageInfo(userid,classid,courseid){
			document.getElementById("exprot").value=false;
			document.getElementById("elUser.id").value = userid;
			document.getElementById("elClass.id").value = classid;
			document.getElementById("course.id").value = courseid;
			studentCourseInfo.action = "studentCpageInfo.action";
			studentCourseInfo.submit();
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
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?       "#ffffff" :       "#f4f4f4" )
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
			<form action="studentCourseInfo.action" name="studentCourseInfo"
				method="post">
				<s:hidden name="exprot" id="exprot" />
				<input type="hidden" name="elUser.id" id="elUser.id" />
				<input type="hidden" name="elClass.id" id="elClass.id" />
				<input type="hidden" name="course.id" id="course.id" />
			</form>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<table width="980px" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="130">
						单元
					</th>
					<th width="130" height="30" align="center">
						学习进度
					</th>
					<th width="130" height="30" align="center">&nbsp;
						
					</th>
					<th width="130" height="30" align="center">
						完成时间
					</th>
					<th width="130" height="30" align="center">
						测验成绩
					</th>
					<th width="130" height="30" align="center">
						详情
					</th>
				</tr>
				<s:iterator value="myCourses">
					<tr>

						<td width="100" height="30" align="center">
							<s:property value="course.name" />
						</td>
						<td width="100" height="30" align="left">
							<div
								style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
								<img src="images/jd.gif" width="<s:property value="process" />%" height="14" />							</div>
					  </td>
						<td width="50" height="30" align="center">
							<s:property value="process" />%
						</td>
						<td width="100" height="30" align="center">
							<s:if test="endtime!=null">
								<s:date name="endtime" format="yyyy年MM月dd日 HH时mm分" />
							</s:if>
						</td>
						<td width="50" height="30" align="center">
							<s:if test="endtime!=null">
								<s:property value="myRoom.myScore" />
							</s:if>
						</td>
						<td width="50" height="30" align="center">
							<a class="textbg4"
									href="javascript:showStudentCpageInfo(<s:property value="elUser.id" />,<s:property value="elClass.id" />,<s:property value="course.id" />);">查看</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			<a href="javascript:toexcel(true);" class="textbg5">导出Excel</a>
		</div>
	</BODY>
</HTML>






