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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dtreequizdep.js"></script>
		<script type="text/javascript">
			function openPrac(id){
			 if(confirm('确定开始考试？'))
			 	window.open("examRoominto.action?examRoom.id="+id,"examRoompaper","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			}
			function setabled(idstr,id){
					document.getElementById(idstr+id).checked=true;
			}
		</script>
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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="部门比较" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">部门评比</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quiz_stat_view.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/><s:iterator value="departments1">&departments1.id=<s:property value="id"/></s:iterator>">考试概况</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quiz_detail_view.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">考试详情</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<SCRIPT type="text/javascript"> 
			function toexcel(){   
				statEval.action = "quiz_stat_eval.action?exprot=true";
				statEval.submit();
			} 
			function view(){   
				statEval.action = "quiz_stat_eval.action";
				statEval.submit();
			}
		</SCRIPT>
		<div style="margin-top: 0px;">
			<div>
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top" id="tree_list_td" style="display: block;">
							<form action="quiz_stat_eval.action" method="post"
								name="statEval">
								<input type="button" class="textbg4" value="查看" onClick="view()" />
								<input type="button" value="导出" class="textbg4" onClick="toexcel()" />
								<br/>试卷<br/>
								<s:iterator value="myExamPapers">
									<input type="checkbox" <s:if test="status==5">checked='checked'</s:if> value="<s:property value="examPaper.id" />" name="examPapers.id" /><b><s:property value="examPaper.title" /></b><br/>
								</s:iterator>
								<hr/>
								<s:hidden name="batchstat"></s:hidden>
								<s:hidden name="erbatch.id"></s:hidden>
								<s:hidden name="examRoom.id"></s:hidden>
								<wysLib:dep_list_aj href="" itype="cb" iname="departments1.id"
									rootAble="true"></wysLib:dep_list_aj>
								<script type="text/javascript">
									w0.setValues([<s:iterator value="departments1" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments1.size-1)!=#depst.index">,</s:if></s:iterator>]);
								</script>
							</form>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<table align="left" width="100%" cellpadding="1" cellspacing="1">
								<caption>
									考核各单位情况汇总表
								</caption>
								<tr>
									<th align="center">
										排行
									</th>
									<th align="center">
										部门
									</th>
									<th align="center">
										应考人数
									</th>
									<th align="center">
										缺考人数
									</th>
									<th align="center">
										及格人数
									</th>
									<th align="center">
										不及格人数
									</th>
									<th align="center">
										及格率
									</th>
									<th align="center">
										平均分
									</th>
									<s:iterator value="examPapers">
										<th height="30" align="center" style="color: red;">
											<s:property value="title" />
										</th>
									</s:iterator>
								</tr>
								<s:iterator value="departments" status="st">
									<s:if test="userCount==0">
										<tr height="30px">
											<td align="center">
												<s:property value="#st.index+1" />
											</td>
											<td align="center">
												<s:property value="name" />
											</td>
											<td align="center">
												-
											</td>
											<td align="center">
												-
											</td>
											<td align="center">
												-
											</td>
											<td align="center">
												-
											</td>
											<td align="center">
												-
											</td>
											<td align="center">
												-
											</td>
											<s:iterator value="myExamPapers">
												<td align="center" style="color: red;">
													-
												</td>
												<%-- 
											<td align="center" style="color: red;" >
												-
											</td>
											 --%>
											</s:iterator>
										</tr>
									</s:if>
									<s:else>
										<tr height="30px">
											<td align="center">
												<s:property value="#st.index+1" />
											</td>
											<td align="center">
												<s:property value="name" />
											</td>
											<td align="center">
												<s:property value="userCount" />
											</td>
											<td align="center">
												<s:property value="userCount_" />
											</td>
											<td align="center">
												<s:property value="userCount_jg" />
											</td>
											<td align="center">
												<s:property value="userCount-userCount_-userCount_jg" />
											</td>
											<td align="center">
												<s:property value="ratio" />

											</td>
											<td align="center">
												<s:if test="userCount-userCount_==0">0</s:if>
												<s:else>
													<s:property value="avg" />
												</s:else>
											</td>
											<s:iterator value="myexampapers">
												<td align="center" style="color: red;">
													<s:property value="avgscore" />
												</td>
												<%-- 
												<td align="center" style="color: red;" >
													<s:property value="mySort" />
												</td>
												 --%>
											</s:iterator>
										</tr>
									</s:else>
								</s:iterator>
							</table>
						</td>
					</tr>
				</table>
				说明：“-”表示该部门无人参加考试<br/>
										<div style="text-align: center;">
										<!-- 	<a
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
				</div>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
