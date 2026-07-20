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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="试卷统计分析" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试概况</span>
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
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<caption style="font-weight: normal;">
					<s:if test="batchstat!=1">
							<b><s:property value="examRoom.title" /> </b>
						</s:if>
						<s:else>
							<b><s:property value="batchstat.title" /> </b>
						</s:else>考核各试卷情况汇总表
				</caption>
				<tr>
					<td height="30" colspan="14" align="left">
						考核总人数：
						<s:if test="batchstat!=1">
							<b><s:property value="examRoom.userSize" /> </b> 缺考人数：
							<b><s:property value="examRoom.usersize" /> </b>
						</s:if>
						<s:else>
							<b><s:property value="erbatch.userSize" /> </b> 缺考人数：
							<b><s:property value="erbatch.usersize" /> </b>
						</s:else>
					</td>
				</tr>
				<tr>
					<th height="30" width="150px;" align="center">
						试卷名称
					</th>
					<th height="30" width="150px;" align="center">
						应考人数
					</th>
					<th height="30" width="150px;" align="center">
						缺考人数
					</th>
					<th height="30" align="center">
						60分以下人数
					</th>
					<th height="30" align="center">
						60-69分
					</th>
					<th height="30" align="center">
						70-79分
					</th>
					<th height="30" align="center">
						80-89分
					</th>
					<th height="30" align="center">
						90分以上
					</th>
					<th height="30" align="center">
						60分以下人数比例
					</th>
					<th height="30" align="center">
						60-69分
					</th>
					<th height="30" align="center">
						70-79分
					</th>
					<th height="30" align="center">
						80-89分
					</th>
					<th height="30" align="center">
						90分以上
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="myExamPapers" status="ermst">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								align="left">
								<s:property value="examPaper.title" />
							</td>
							<td height="30" align="center"  
								style="color: #CC0099;">
								<s:property value="yksize" />
							</td>
							<td height="30" align="center" 
								style="color: #CC0099;">
								<s:property value="qksize" />
							</td>
							<td height="30" align="center" 
								style="color: #CC0099;">
								<s:property value="pass_6" />
							</td>
							<td align="center">
								<s:property value="pass6_7" />
							</td>
							<td align="center">
								<s:property value="pass7_8" />

							</td>
							<td height="30" align="center">
								<s:property value="pass8_9" />
							</td>
							<td height="30" align="center">
								<s:property value="pass9_" />
							</td>
							<td height="30" align="center" 
								style="color: #CC0099;">
								<s:property value="pass_6_ps" />
								%
							</td>
							<td align="center">
								<s:property value="pass6_7_ps" />
								%
							</td>
							<td align="center">
								<s:property value="pass7_8_ps" />
								%

							</td>
							<td height="30" align="center">
								<s:property value="pass8_9_ps" />
								%
							</td>
							<td height="30" align="center">
								<s:property value="pass9__ps" />
								%
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<s:if test="myExamPapers.size!=0">
				<a target="" href="javascript:toexcel();" class="textbg">导出列表</a>
			</s:if>
				<!-- <a
					href="quiz_stat_eval_jz.action?examRoom.id=<s:property value="examRoom.id"/>"
					class=textbg>各工种情况</a>
				<a
					href="quiz_detail_paper_view.action?examRoom.id=<s:property value="examRoom.id"/>"
					class=textbg>各试卷情况</a>
				<a
					href="quiz_stat_view.action?examRoom.id=<s:property value="examRoom.id"/>"
					class=textbg>考核成绩情况</a>
				<a
					href="quiz_detail_view.action?examRoom.id=<s:property value="examRoom.id"/>"
					class=textbg>成绩情况</a>
				<a
					href="quiz_stat_eval.action?examRoom.id=<s:property value="examRoom.id"/>"
					class=textbg>各单位情况</a> -->
				<s:if test="batchstat!=1">
							<a href="quiz_searchlist.action" class="textbg">返回考场列表</a>
						</s:if>
						<s:else>
							<a href="stat_eroom_batch_list.action" class="textbg">返回批次列表</a>
						</s:else>
			<SCRIPT type="text/javascript"> 
				function toexcel(){ 
						document.location="quiz_detail_paper_view_Excel.action?examRoom.id=<s:property value="examRoom.id"/>";
				}
				</SCRIPT>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
