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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="部门比较" /></div>
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
					href="stat_eroom_batch_gk.action?erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">批次概况</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_eroom_batch_view.action?erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">批次详情</a>
			</li>-->
		</ul>
		<!-- 内容 -->
					<script type="text/javascript">
						function toexcel(){     
							statEval.action = "stat_eroom_batch_eval.action?exprot=true";
							statEval.submit();
						}
						function view(){     
							statEval.action = "stat_eroom_batch_eval.action";
							statEval.submit();
						}						
					</script>
		<div style="margin-top: 0px;">
			<div>
				<table>
					<tr>
						<td valign="top">
							<form action="stat_eroom_batch_eval.action" method="post" name="statEval">
								<input type="button" value="查看" onClick="view()">	<input type="button" value="导出" onClick="toexcel()"> 
								<s:hidden name="erbatch.id"></s:hidden>
								<wysLib:dep_list_cb attrname="depTree"
									inputname="departments1.id"></wysLib:dep_list_cb>

							</form>
						</td>
						<td valign="top">
							<table width="700px" align="center" cellpadding="1"
								cellspacing="1" >
								<tr>
									<th align="center" >
										排行
									</th>
									<th align="center" >
										部门
									</th>
									<th align="center" >
										考试人数
									</th>
									<th align="center" >
										及格人数
									</th>
									<th align="center" >
										及格率
									</th>
									<th align="center" >
										平均分
									</th>
									<s:iterator value="myExamPapers">
										<th height="30" colspan="2" align="center" style="color: red;"
											>
											<s:property value="examPaper.title" />
										</th>
									</s:iterator>
								</tr>
								<s:iterator value="departments" status="st">
									<tr>
										<td align="center" >
											<s:property value="#st.index+1" />
										</td>
										<td align="center" >
											<s:property value="name" />
										</td>
										<td align="center" >
											<s:property value="userCount" />
										</td>
										<td align="center" >
											<s:property value="userCredit" />
										</td>
										<td align="center" >
											<s:if test="userCount==0||userCredit==0">0%</s:if> 
											<s:else>
												<s:property value="ratio" /> 
											</s:else>
										</td>
										<td align="center" >
											<s:property value="avg" />
										</td>
										<s:iterator value="myexampapers">
												<td align="center" style="color: red;" >
													<s:property value="avgscore" />
												</td>
												<td align="center" style="color: red;" >
													<s:property value="mySort" />
												</td>
											</s:iterator>
									</tr>
								</s:iterator>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<s:iterator value="departments1">
					<script> setabled('depTree',<s:property value="id" />);</script>
		</s:iterator>
		
		<!-- 内容 -->
	
	</body>
</HTML>
