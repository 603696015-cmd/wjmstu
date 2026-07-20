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
		<TITLE>考试概况</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/tree/dtreequizdep.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
		function toexcel(){   
				statEval.action = "quiz_stat_view.action?exprot=true";
				statEval.submit();
			}
			function view(){   
				statEval.action = "quiz_stat_view.action";
				statEval.submit();
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
					<wysLib:Navigation ivalue="部门概况比较" />
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
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<caption>
					<s:property value="examRoom.title" />
					考核成绩情况汇总表
				</caption>
				<tr>
					<td valign="top" id="tree_list_td" width="210px">
						<%
							ExamRoom examroom = (ExamRoom) request.getAttribute("examRoom");
							String url = "quiz_stat_view.action?examRoom.id="
									+ examroom.getId() + "&sub_department=1&department.id=";
						%><!--
						 wysLib:dep_list_f rootAble="true" href="<%=url%>" wysLib:dep_list_f 
						  wysLib:dep_list_cb attrname="depTree" inputname="departments1.id" wysLib:dep_list_cb 
						 -->
						<form action="quiz_stat_view.action" method="post" name="statEval">
							&nbsp;
							<input type="button" class="textbg4" value="查看" onClick="view()" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="导出" class="textbg4"
								onClick="toexcel()" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:hidden name="examRoom.id"></s:hidden>
							<s:hidden name="batchstat"></s:hidden>
							<s:hidden name="erbatch.id"></s:hidden>
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
					<td valign="top" align="left">
						<table style="margin-top: 0px;" align="left" cellpadding="1"
							cellspacing="1" bgcolor="#EBEBEB">
							<tr height="30" style="color: blue;" align="left">
								<td align="center">
									部门名称
								</td>
								<s:iterator value="departments1">
									<td height="30" style="width: 80px;" align="left">
										<s:property value="name" />
									</td>
								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									应考人数
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:property value="examRoom.usersize" />
									</td>

								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									应考人员平均分
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:property value="examRoom.avgscore" />
									</td>

								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									参考人数
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:property value="examRoom.joinusersize" />
									</td>

								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									参考人员平均分
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<%-- 	<s:if test="examRoom.avgscorejoin == NaN">0</s:if> --%>
										<s:if test="examRoom.joinusersize == 0">0</s:if>
										<s:else>
											<s:if
												test="(examRoom.avgscorejoin+'').substring((examRoom.avgscorejoin+'').lastIndexOf('.'),(examRoom.avgscorejoin+'').length()).length()<=3">
												<s:property value="examRoom.avgscorejoin" />
											</s:if>
											<s:else>
												<s:property
													value="(examRoom.avgscorejoin+'').substring(0,(examRoom.avgscorejoin+'').lastIndexOf('.')+3)" />
											</s:else>
										</s:else>
									</td>

								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									缺考人数
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:property value="examRoom.LOEusersize" />
									</td>

								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									及格人数
								</td>
								<s:iterator value="departments1">
									<td height="30" align="center">
										<s:property value="examRoom.passsize" />
									</td>
								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									参考人员及格率
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:if test="examRoom.joinusersize == 0">0</s:if>
										<s:else>
											<s:if
												test="(examRoom.passgrade+'').substring((examRoom.passgrade+'').lastIndexOf('.'),(examRoom.passgrade+'').length()).length()<=3">
												<s:property value="examRoom.passgrade" />
											</s:if>
											<s:else>
												<s:property
													value="(examRoom.passgrade+'').substring(0,(examRoom.passgrade+'').lastIndexOf('.')+3)" />
											</s:else>
										</s:else>
										%
									</td>

								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									应考人员及格率
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:if test="examRoom.joinusersize == 0">0</s:if>
										<s:else>
											<s:if
												test="(examRoom.passgrade2+'').substring((examRoom.passgrade2+'').lastIndexOf('.'),(examRoom.passgrade2+'').length()).length()<=3">
												<s:property value="examRoom.passgrade2" />
											</s:if>
											<s:else>
												<s:property
													value="(examRoom.passgrade2+'').substring(0,(examRoom.passgrade2+'').lastIndexOf('.')+3)" />
											</s:else>
										</s:else>
										%
									</td>
								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									90分以上
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:property value="examRoom.pass9_" />
									</td>
								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									80-89分
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:property value="examRoom.pass8_9" />
									</td>
								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									70-79分
								</td>
								<s:iterator value="departments1">

									<td height="30" align="center">
										<s:property value="examRoom.pass7_8" />
									</td>
								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									60-69分
								</td>
								<s:iterator value="departments1">
									<td height="30" align="center">
										<s:property value="examRoom.pass6_7" />
									</td>

								</s:iterator>
							</tr>
							<tr height="30" align="center">
								<td>
									60以下
								</td>
								<s:iterator value="departments1">
									<td height="30" align="center">
										<s:property value="examRoom.pass_6" />
									</td>
								</s:iterator>
							</tr>
						</table>
				<SCRIPT type="text/javascript"> 
				//function toexcel(){
				//		document.location="quiz_stat_view.action?exprot=true&examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department" />";
				//	}
			</SCRIPT>
				<!--<a target="" href="javascript:toexcel();">导出列表</a>
					-->
				</td>
				</tr>
			</table>
			<div style="text-align: center;">
				<!--  <a
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
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>

