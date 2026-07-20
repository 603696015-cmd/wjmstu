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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看详情" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看具体详情</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_examprac_detail.action?examprac.id=<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">练习详情</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_examprac_eval.action?examprac.id=<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">评比详情</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">

			<table width="600" align="center" cellspacing="1" cellpadding="1">
				<caption>
					练习信息
				</caption>
				<tr>
					<td height="30" width="200" align="center">
						练习标题
					</td>
					<td height="30" align="center">
						<s:property value="myeprac.prac.title" />
					</td>
				</tr>
				<tr>
					<td height="30" style="padding-left: 8px; color: blue;"
						align="left">
						练习开始时间
					</td>
					<td height="30" align="center">
						<s:date name="myeprac.prac.begintime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td height="30" style="padding-left: 8px; color: blue;"
						align="left">
						练习结束时间
					</td>
					<td height="30" align="center">
						<s:date name="myeprac.prac.endtime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td height="30" style="padding-left: 8px; color: blue;"
						align="left">
						已经练习次数
					</td>
					<td height="30" align="center">
						<s:property value="myeprac.times" />
					</td>
				</tr>
				<tr>
					<td height="30" style="padding-left: 8px; color: blue;"
						align="left">
						已经练习的平均分
					</td>
					<td height="30" align="center">
						<s:property value="myeprac.avgscore" />
					</td>
				</tr>
				<tr>
					<td height="30" style="padding-left: 8px; color: blue;"
						align="left">
						及格分数
					</td>
					<td height="30" align="center">
						<s:property value="myeprac.passScore" />
					</td>
				</tr>
				<tr>
					<td height="30" style="padding-left: 8px; color: blue;"
						align="left">
						及格次数
					</td>
					<td height="30" align="center">
						<s:property value="myeprac.passCount" />
					</td>
				</tr>
			</table>
			<s:if test="myExamPapers.size==0">
				<br>
					  还没开始练习！			</s:if>
			<s:else>
				<table width="600" align="center" cellspacing="1" cellpadding="1">
					<caption>
						练习试卷
					</caption>
					<tr>
						<td height="30" style="padding-left: 8px; color: blue;"
							align="left">
						</td>
						<td height="30" align="center">
							交卷时间
						</td>
						<td width="100" height="30" align="center">
							是否及格
						</td>
						<td height="30" align="center">
							成绩
						</td>
						<td width="120" height="30" align="center">
							查看答卷
						</td>
					</tr>
					<s:iterator value="myExamPapers" status="st">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								align="left">
								<s:property value="#st.index+1" />
							</td>
							<td height="30" align="center">
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td width="100" height="30" align="center">
								<s:if test="myScore>=myeprac.passScore">
									是
								</s:if>
								<s:else>
									否
								</s:else>
							</td>
							<td height="30" align="center">
								<s:property value="myScore" />
							</td>
							<td width="120" height="30" align="center">
								<a target="_blank"
									href='examprac_view.action?myExamPaper.id=<s:property value="id"/>'
									class=textbg>查看答卷</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</s:else>
			<a
				href='stat_examprac_detail.action?examprac.id=<s:property value="examprac.id"/>'
				class=textbg>练习人员列表</a>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
