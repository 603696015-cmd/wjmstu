<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@page import="com.sopia.courseman.entities.ExamRoom"%>
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
	background-color: expression((         this .         sectionRowIndex %         2 ==
		        0) ?    
		    "#ffffff" :         "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function disRecord(userid){
				var roomid=<s:property value="examRoom.id" />;
				document.location.href="studyRoomRecordList.action?examRoom.id="+roomid+"&elUser.id="+userid;
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="成绩排榜" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试概况</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quiz_stat_view.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">考试概况</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quiz_stat_eval.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">考试详情</a>
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
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="200px;" id="tree_list_td">
						<%
							ExamRoom examroom = (ExamRoom) request.getAttribute("examRoom");
							String url = "quiz_detail_view.action?examRoom.id="
									+ examroom.getId() + "&sub_department=1&department.id=";
						%>
						<wysLib:dep_list_aj rootAble="true" href="<%=url%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
									w0.setValues([ new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="quiz_detail_view" name="qdvForm" method="post"
							theme="simple">
							<s:hidden name="examRoom.id" />
							<div style="padding-left: 20px; margin-top: 5px;">
								考试时间：从
								<input name="examRoom.begintime"
									value="<s:date name="examRoom.begintime" />"
									onclick="setday(this);" readonly="readonly" />
								&nbsp;&nbsp;到
								<input name="examRoom.endtime"
									value="<s:date name="examRoom.endtime" />"
									onclick="setday(this);" readonly="readonly" />
								<s:hidden name="batchstat"></s:hidden>
								<s:hidden name="erbatch.id"></s:hidden>
								<s:submit value="搜索" onclick="qdvForm.action='quiz_detail_view.action';document.getElementById('pageNow').value=0;" cssClass="textbg4" />
								<s:hidden name="department.id"></s:hidden>
							</div>
							<s:hidden name="pN" id="pageNow"></s:hidden>
						</s:form>
						<table width="100%" align="center" cellpadding="1" cellspacing="1"
							bgcolor="#EBEBEB">
							<caption style="font-weight: normal;">
								<s:if test="batchstat!=1">
							<b><s:property value="examRoom.title" /> </b>
						</s:if>
						<s:else>
							<b><s:property value="batchstat.title" /> </b>
						</s:else>考核成绩汇总表
							</caption>
							<tr>
								<td height="30" colspan="12" align="left">
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
								<th width="40" height="30" align="center">
									排名
								</th>
								<th height="30" align="center">
									姓名
								</th>
								<th height="30" align="center">
									所属部门
								</th>
								<%--<th height="30" align="center">
									性别
								</th>
								<th height="30" align="center">
									<wysLib:BasetName btid="1" />
								</th>
								<th height="30" align="center">
									年龄
								</th> --%>
								<th height="30" align="center">
									用户名
								</th>
								<s:iterator value="examRoom.myrooms[0].myExamPapers">
									<th height="30" align="center" style="color: red;">
										<s:property value="examPaper.title" />
									</th>
								</s:iterator>
								<th width="90" height="30" align="center">
									考试时间
								</th>
								<th width="40" height="30" align="center">
									总成绩
								</th>
								<th width="60" height="30" align="center">
									是否通过
								</th>
								<th width="80" height="30" align="center">
									试卷数量
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRoom.myrooms" status="ermst">
									<tr
										style='s: if test =   "status==0" &amp; gt; background: #eeffa2 </ s :   if >'>
										<td width="40" height="30" align="center">
											<s:property value="#ermst.index+1" />
										</td>
										<td height="30" align="center" style="color: #CC0099;">
											<s:property value="tester.realname" />
										</td>
										<td height="30" align="center">
											<s:property value="tester.danwei" />
										</td>
										<%--<td height="30" align="center">
											<s:property value="tester.sex" />
										</td>
										<td height="30" align="center">
											<s:property value="tester.jingzhong" />
										</td>
										<td height="30" align="center">
											<s:property value="tester.age" />
										</td>--%>
										<td height="30" align="center">
											<s:property value="tester.username" />
										</td>

										<!--<td width="100" height="30" align="center">
											<a
												href="javascript:disRecord('<s:property value="tester.id" />');"
												class="textbg6">答卷详情</a>
										</td> 
										-->
										<s:set name="userid" value="tester.id"></s:set>
										<s:iterator value="myExamPapers">
											<td height="30" align="center" style="color: red;">
												<s:if test="id==0">未分配</s:if>
												<s:else>
													<s:property value="statusName" />
													<s:if test="status!=0">
														/ <s:property value="myScore" />
													</s:if>
													<s:if test="status == 2||status==3">
														/<a
															href="exampaperreadlist_meps.action?examRoom.id=<s:property value="examroom.id"/>&examPaper.id=<s:property value="examPaper.id"/>&elUser.id=<s:property value="#userid"/>&Return=stat">阅卷</a>
													</s:if>
													<!--/<a
														href="quizpaper_view.action?elUser.id=<s:property value="#userid"/>&myExamPaper.id=<s:property value="id"/>"
														target=_blank class=textbg4>查看</a>/ <a
											href="exampaperread.action?myExamPaper.id=<s:property value="id"/>"
											target=_blank class=textbg4>改分</a> -->
												</s:else>
											</td>
										</s:iterator>
										<td width="90" height="30" align="center">
											<s:date name="begintime" format="yyyy-MM-dd HH:mm" />&nbsp;
										</td>
										<td width="40" height="30" align="center">
											<s:if test="status==0">--</s:if>
											<s:else>
												<s:property value="myScore" />
											</s:else>
										</td>
										<td width="60" align="center">
											<s:if test="status==0">--</s:if>
											<s:else>
												<s:if test="ispassed==1">通过</s:if>
												<s:else>不通过</s:else>
											</s:else>
										</td>
										<td width="80" height="30" align="center">
											<s:property value="epsize" />
											个
											<%-- 	<a href="quiz_paper_detail_view.action?elUser.id=<s:property value="tester.id"/>&examRoom.id=<s:property value="examRoom.id"/>"
									class=textbg4>详情</a>  --%>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
			<s:if test="examRoom.myrooms.size!=0">
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
					class=textbg>各单位情况</a>-->
				<s:if test="batchstat!=1">
					<a href="quiz_searchlist.action" class="textbg">返回考场列表</a>
				</s:if>
				<s:else>
					<a href="stat_eroom_batch_list.action" class="textbg">返回批次列表</a>
				</s:else>
			<br />
			<br />
			<br />

			<SCRIPT type="text/javascript">
				function page(i){
					qdvForm.action="quiz_detail_view.action";
					document.getElementById("pageNow").value=i;
					qdvForm.submit();
					//document.location.href="quiz_detail_view.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>&pN="+i
				}
				function toexcel(){
						qdvForm.action="quiz_detail_viewExcel.action";
						document.getElementById("pageNow").value=i;
						qdvForm.submit();
						//document.location="quiz_detail_viewExcel.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>";
				}
				</SCRIPT>
			<%--<wysLib:page></wysLib:page>--%>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>