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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试统计</span>
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
			<script>
				function page(i){ 
					document.getElementById("pageNow").value=i;
			 		quiz_list.submit();
				}
			</script>
			<form action="quiz_searchlist.action" name="quiz_list" method="post">
				<s:hidden name="pN" id="pageNow">
				</s:hidden>
				<s:hidden name="pS">
				</s:hidden>
				<s:hidden name="examRoom.begintime" />
				<s:hidden name="examRoom.eroomLib.id" />
				<s:hidden name="examRoom.endtime" />
				<s:hidden name="examRoom.title" />
				<s:hidden name="quizpage" value="1"></s:hidden>
			</form>
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td valign="top">
						<wysLib:eroomLibTree
							href="quiz_searchlist.action?str=libids&sub_department=1&department.id=1&examRoom.eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td>
						<table width="100%" style="margin-top: 0px;" align="center" cellpadding="1" cellspacing="1"
							bgcolor="#EBEBEB">
							<tr>
								<td colspan="9">
									<s:form action="quiz_searchlist.action" method="post" name="department_info">
										<s:hidden name="examRoom.eroomLib.id" />
										考场：
										<input name="examRoom.title" type="text" value="<s:property value="examRoom.title" />" size="24" />
										<%-- 
										考场类别：
										<select name="examRoom.eroomLib.id" id="er_erlid">
											<wysLib:eroomLibSelect selectid="${examRoom.eroomLib.id}"></wysLib:eroomLibSelect>
										</select>
										 --%>
										&nbsp; 时间：&nbsp;&nbsp;从
										<input name="examRoom.begintime" type="text"
											onclick="setday(this)" value="<s:date name="examRoom.begintime" format="yyyy-MM-dd hh:mm:ss"/>" />
										&nbsp;&nbsp;到
										<input name="examRoom.endtime" type="text"
											onclick="setday(this)" value="<s:date name="examRoom.endtime" format="yyyy-MM-dd hh:mm:ss"/>" />
										<input type="submit" value="搜索" />
									</s:form>
								</td>
							</tr>
							<tr style="font-weight: bolder;">
								<td height="40" align="center" >
									考场名称
								</td>
								<!--<th height="30" align="center" >
									创建者
								</th>-->
								<td align="center" >
									课程
								</td>
								<td width="70"  align="center" >
									考试/缺考<br/>人数
								</td>
								<!--<th  align="center" >
									考场类别
								</td>-->
								<td width="80"  align="center" >
									各工种情况<br/>汇总表
								</td>
								<td width="80"  align="center" >
									各试卷情况<br/>汇总表
								</td>
								<td width="80"  align="center" >
									考核成绩情况<br/>汇总表
								</td>
								<td width="80"  align="center" >
									成绩情况<br/>汇总表
								</td>
								<td width="80"  align="center" >
									各单位情况<br/>汇总表
								</td>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms">
									<tr>
										<td height="30" align="center"
											style="color: #CC0099;">
											<s:property value="title" />									  </td>
										<!--<td height="30" align="center" >
										<s:property value=" creater.realname" />
									</td>-->
										<td height="30" align="center" >
											<s:if test="course.name == '一般考试'">
										—————
									</s:if>
											<s:else>
												<s:property value="course.name" />
											</s:else>
										</td>
										<td width="70" height="30" align="center" >
											<s:property value="userSize" />/<s:property value="usersize" />
										</td>
										<!--<td height="30" align="center" >
										<s:property value="eroomLib.name" />
									</td>-->
										<td width="80" height="30" align="center" >
											<a
												href="quiz_stat_eval_jz.action?examRoom.id=<s:property value="id"/>"
												class=textbg6>查 看</a>
										</td>
										<td width="80" height="30" align="center" >
											<a
												href="quiz_detail_paper_view.action?examRoom.id=<s:property value="id"/>"
												class=textbg6>查 看</a>
										</td>
										<td width="80" height="30" align="center" >
											<a
												href="quiz_stat_view.action?examRoom.id=<s:property value="id"/>"
												class=textbg6>查 看</a>
										</td>
										<td width="80" height="30" align="center" >
											<a
												href="quiz_detail_view.action?examRoom.id=<s:property value="id"/>"
												class=textbg6>查 看</a>
										</td>
										<td width="80" height="30" align="center" >
											<a
												href="quiz_stat_eval.action?examRoom.id=<s:property value="id"/>"
												class=textbg4>查 看</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
			<a href="stat_eroom_block_list.action?examRoom.id=<s:property value="examRoom.id"/>" style="width:100px" class="textbg4">按试卷模块</a>
				
			<a href="stat_eroom_batch_list.action" class="textbg4" style="width:90px">批次统计</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>