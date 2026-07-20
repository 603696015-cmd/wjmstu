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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function columnsearch(orderBy){
				document.getElementById("orderBy").value = orderBy;
				var sc=document.getElementById("ordersc").value;
				if(sc == ""){
					document.getElementById("ordersc").value = "asc";
				}else if(sc == "asc"){
					document.getElementById("ordersc").value = "desc";
				}else if(sc == "desc"){
					document.getElementById("ordersc").value = "asc";
				}
				log.submit();
			}
			
			function page(i){
				document.getElementById("pageNow").value=i;
				log.submit();
			}
			
			function do_search(){
				document.getElementById("pageNow").value=0;
				log.submit();
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
					<wysLib:Navigation ivalue="日志评分统计" />
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
			<form action="log_statistics.action" name="log" method="post">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" /> 
			<s:hidden name="tablename" /> 
			<s:hidden name="orderBy" id="orderBy"/> 
			<s:hidden name="ordersc" id="ordersc" /> 
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<%
							Department dep = (Department) request
										.getAttribute("department");
								String depid = dep.getId() + "";
						%>
						<wysLib:dep_list_aj rootAble="true"
							href="log_statistics.action?tablename=GRRZ&sub_department=1&department.id="
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
						<table align="center" cellpadding="1" cellspacing="1"
							width="100%" height="100%">
							<tr>
								<td align="center" >
									姓名
								</td>
								<td >
									<label>
										<input type="text" name="elUser.realname" 
											value="">
									</label>
								</td>
								<td align="center" >
									账号
								</td>
								<td >
									<label>
										<input type="text" name="elUser.username" 
											value="">
									</label>
								</td>
							</tr>
							<tr>
								<td align="center" >
									时间段范围
								</td>
								<td >
									<label>
										开始时间<input type="text" name="starttime" 
											value="" onclick="setday(this);">
									</label>
								</td>
								<td >
									<label>
										结束时间<input type="text" name="endtime" 
											value="" onclick="setday(this);">
									</label>
								</td>
								<td align="center" ><input type="button" value="搜索" onclick="do_search();" class="textbg4"/></td>
							</tr>
						</table>
						<table width="98%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<th height="30" align="center">
									<a href="javascript:columnsearch('realname');">姓名</a>
								</th>
								<th height="30" align="center">
									<a href="javascript:columnsearch('name');">部门</a>
								</th>
								<th height="30" align="center">
									<a href="javascript:columnsearch('t.GRRZ_ZWPF');">自我评分汇总</a>
								</th>
								<th height="30" align="center">
									<a href="javascript:columnsearch('t.GRRZ_BMPF');">部门评分汇总</a>
								</th>
								<th height="30" align="center">
									<a href="javascript:columnsearch('t.GRRZ_LDPF');">领导评分汇总</a>
								</th>
								<th height="30" align="center">
									差值
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="gzrzList">
									<tr>
										<td align="center"><s:property value="name"/></td>
										<td align="center"><s:property value="dep"/></td>
										<td align="center">
											<s:if test="byme == 0.0">
												-
											</s:if>
											<s:else>
												<s:property value="byme"/>
											</s:else>
										</td>
										<td align="center">
											<s:if test="bydep == 0.0">-</s:if>
											<s:else><s:property value="bydep"/></s:else>
										</td>
										<td align="center">
											<s:if test="leader == 0.0">-</s:if>
											<s:else><s:property value="leader"/></s:else>
										</td>
										<td align="center">
											<s:if test="cha1 == 0.0">-</s:if>
											<s:else><span style="color:red"><s:property value="cha1"/></span></s:else>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			</form>
			<center><wysLib:page></wysLib:page></center>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
