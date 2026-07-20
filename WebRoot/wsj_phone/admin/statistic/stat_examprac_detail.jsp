<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@page import="com.sopia.courseman.entities.Examprac"%>
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
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript">
			function openPrac(id){
			 if(confirm('确定开始练习？'))
			 	window.open("exampracinto.action?examprac.id="+id,"exampracpaper","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			}
		</script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考生列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习详情</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_examprac_gk.action?examprac.id=<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">练习概况</a>
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
		<div style="margin-top: 0px;">
			<div>
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top" width="200px;" id="tree_list_td">
							<%
								Examprac examprac = (Examprac) request.getAttribute("examprac");
								String url = "stat_examprac_detail.action?examprac.id="
										+ examprac.getId() + "&sub_department=1&department.id=";
							%>
							<wysLib:dep_list_aj rootAble="true" href="<%=url%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1">
								<tr>
									<th width="130" align="center">
										考生姓名
									</th>
									<th align="center">
										部门
									</th>
									<th align="center">
										平均分
									</th>
									<th align="center">
										及格
									</th>
									<th align="center">
										练习状态
									</th>
									<th width="120" align="center">
										查看详情
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="myexampracs">
										<tr>
											<td width="130" align="center" bgcolor="#FFFFFF"
												style="color: #CC0099;">
												<s:property value="tester.realname" />
											</td>
											<td align="center">
												<s:property value="tester.department.name" />
											</td>
											<td align="center">
												<s:if test="pracStatus=='未开始'">--</s:if>
												<s:else>
													<s:property value="avgscore" />
												</s:else>
											</td>
											<td align="center">
												<s:if test="avgscore>passScore">及格</s:if>
												<s:else>不及格</s:else>
											</td>
											<td align="center">
												<s:property value="pracStatus" />
											</td>
											<td width="120" align="center">
												<a
													href="stat_examprac_detail_list.action?elUser.id=<s:property value="tester.id"/>&examprac.id=
								<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>"
													class=textbg>查看详情</a>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>

						</td>
					</tr>
				</table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
					<a class="textbg"
						href="stat_examprac_detail.action?exprot=true&elUser.id=<s:property value="tester.id"/>&examprac.id=
								<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">导出列表</a>
				</DIV><div style="text-align: center;">
				<a href="stat_examprac_gk.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >查看概况</a>
				<a href="stat_examprac_detail.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >查看详情</a>
				<a href="stat_examprac_eval.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >部门比较</a>
				<a href="stat_examprac_list.action" class=textbg >返回练习列表</a>
			</div>
				<s:form action="stat_examprac_detail" method="post" name="erform">
					<s:hidden name="pN" id="pageNow" />
					<s:hidden name="pS" />
					<s:hidden name="examprac.id" />
					<s:hidden name="department.id" />
					<s:hidden name="sub_department" value="1" />
				</s:form>
				<script>
					function page(i){
						document.getElementById("pageNow").value=i;
						erform.submit();
					}
				</script>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
