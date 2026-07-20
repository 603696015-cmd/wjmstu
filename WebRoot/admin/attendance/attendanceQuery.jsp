<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function doSearch(){
				document.getElementById("pageNow").value=0;
				myAttendance.submit();
			}
			
			function page(i){
				document.getElementById("pageNow").value=i;
				myAttendance.submit();
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考勤查询" />
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
			<form action="attendanceQuery.action" name="myAttendance" method="post">
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					搜索条件
				</caption>
				<tr>
					<td align="center">
						开始时间
					</td>
					<td align="center">
						<input type="text" name="starttime" onclick="setday(this)"/>
					</td>
					
					<td align="center">
						结束时间
					</td>
					<td align="center">
						<input type="text" name="endtime" onclick="setday(this)"/>
					</td>
					<td align="center">
						<input type="button" value="搜索" class="textbg4" onclick="doSearch();"/>
					</td>
					<td align="center">
						<span style='color:red;CURSOR: hand' onclick="window.location.href='attendanceOrder.action'">按人员进行查询</span>
					</td>
				</tr>
			</table>
			
			<table width="98%"  align="center" cellspacing="1" cellpadding="1">
				<caption>
					考勤列表
				</caption>
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<%
							Department dep = (Department) request
										.getAttribute("department");
								String depid = dep.getId() + "";
						%>
						<wysLib:dep_list_aj rootAble="true"
							href="attendanceQuery.action?sub_department=1&department.id="
							iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:hidden name="pN" id="pageNow" />
						<s:hidden name="pS" />
						<s:hidden name="department.id" />
						<s:hidden name="sub_department" />
						<table align="center" cellpadding="1" cellspacing="1"
							width="100%" height="100%">
							<tr>
								<th height="30" align="center" >
									日期
								</th>
								<th height="30" align="center" >
									姓名
								</th>
								<th height="30" align="center" >
									部门
								</th>
								<th height="30" align="center" >
									签到时间
								</th>
								<th height="30" align="center" >
									签退时间
								</th>
								<th height="30" align="center" >
									相关请假
								</th>
								<th height="30" align="center" >
									相关外出
								</th>
								<th height="30" align="center" >
									相关补签
								</th>
								<th height="30" align="center" >
									结果
								</th>
								<th height="30" align="center" >
									备注
								</th>
								<th height="30" align="center" >
									详情
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="workAttendanceList">
									<tr>
										<td align='center' ><s:date name="riqi" format="yyyy年MM月dd日"/></td>
										<td align='center' ><s:property value="realname"/></td>
										<td align='center' ><s:property value="depname"/></td>
										<td align='center' ><s:date name="signdaotime" format="yyyy年MM月dd日 HH时:mm分:ss秒"/></td>
										<td align='center' ><s:date name="signtuitime" format="yyyy年MM月dd日 HH时:mm分:ss秒"/></td>
										<td align='center' >
											<s:if test="relateleave == null">
											</s:if> 
											<s:else>
												<span style='color:red'>(已请假)</span>
											</s:else>
										</td>
										<td align='center' >
											<s:if test="relateout == null">
											</s:if>
											<s:else>
												<span style='color:red'>(已外出)</span>
											</s:else>
										</td>
										<td align='center' >
											<s:if test="relateretroactive == null">
											</s:if>
											<s:else>
												<span style='color:red'>(已补签)</span>
											</s:else>
										</td>
										<td align='center' ><s:property value='result'/></td>
										<td align='center' ><s:property value='mark'/></td>
										<td align='center' ><a href="viewWorkAttendance.action?id=<s:property value='id'/>" class="textbg">查看</a></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>
		<!-- 内容 -->
		<center><wysLib:page></wysLib:page></center>
	</BODY>
</HTML>
