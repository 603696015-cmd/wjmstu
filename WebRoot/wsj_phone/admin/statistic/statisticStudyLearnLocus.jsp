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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				recordInfo.submit();
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="在线学习统计" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<s:form action="statisticStudyLearnLocus" method="post"
			name="recordInfo">
			<s:hidden name="elUser.id" />
			<s:hidden name="course.classid" />
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="Return"/>
		</s:form>
		<div style="margin-top: 0px; text-align: center;">
			<h3>
				培训班学习轨迹信息
			</h3>
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th height="30" align="center">
						培训班
					</th>
					<th height="30" align="center">
						课程名称
					</th>
					<th height="30" align="center">
						章节名称
					</th>
					<th height="30" align="center">
						章节练习
					</th>
					<th height="30" width="120" align="center">
						开始时间
					</th>
					<th height="30" width="120" align="center">
						结束时间
					</th>
					<th height="30" width="60" align="center">
						学习时间
					</th>
					<th height="30" width="120" align="center">
						练习得分/是否通过
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="myCpages">
						<tr>
							<td height="30" align="center">
								<s:property value="cpage.course.className" />
							</td>
							<td height="30" align="center">
								<s:property value="cpage.course.name" />
							</td>
							<td height="30" align="center">
								<s:if test="cpage.title==null">─</s:if>
								<s:else>
									<s:property value="cpage.title" />
								</s:else>
							</td>
							<td height="30" align="center">
								<s:if test="pracp.title==null">─</s:if>
								<s:else>
									<s:property value="pracp.title" />
								</s:else>
							</td>
							<td height="30" align="center">
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center">
								<s:if test="endtime==null">非正常退出</s:if>
								<s:else>
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</s:else>
							</td>
							<td height="30" align="center">
								<s:if test="passtime==-1">─</s:if>
								<s:else>
									<s:property value="passtime2Str" />
								</s:else>
							</td>
							<td height="30" align="center">
								<s:if test="passed2==-1">─</s:if>
								<s:else>
									<s:property value="myscore" />/
								<s:if test="passed2==1">是</s:if>
									<s:else>否</s:else>
								</s:else>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<wysLib:page></wysLib:page>
			<a href="<s:property value="Return"/>" class="textbg">返回</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>