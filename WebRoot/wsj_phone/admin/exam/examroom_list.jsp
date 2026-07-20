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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示部门</a>';
					}
				}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}
tr {
	background-color: expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")
}
</style>

	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="课程列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试考场管理课程列表 </span>
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

				<!-- DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
				</DIV-->
				<form action="examroom_list.action" name="erform" method="post">
					<s:hidden name="ctype.id" />
					<s:hidden name="pN" id="pageNow" />
					<s:hidden name="pS" />
					<s:hidden name="course.name" />
					<s:hidden name="course_sourse" />
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
			</script>
				<table width="100%">
					<tr>
						<td width="120" valign="top" id="tree_list_td" style="display:none">
							<wysLib:ctypeTree rootAble="true"
								href="examroom_list.action?ctype.id="></wysLib:ctypeTree>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg"/>
						</td>
						<td valign="top">
							<div style="text-align: left;" id="showtree">
								<a href="javascript:showtree(true);" class="textbg5">显示部门</a>
							</div>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1">
								<tr>
									<th width="200" height="30" align="center">
										课程名称
									</th>
									<th width="120" height="30" align="center">
										创建者
									</th>
									<th width="150" height="30" align="center">
										创建时间
									</th>
									<th width="110" height="30" align="center">&nbsp;
										
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="courses">
										<tr>
											<td height="30" style="padding-left: 8px; color: blue;"
												align="left">
												<s:property value="name" />
											</td>
											<td height="30" align="center">
												<s:property value="creater.realname" />
											</td>
											<td height="30" align="center">
												<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td height="30" align="center">
												<a
													href="examroom_listbyc.action?course.id=<s:property value="id"/>"
													class="textbg5">考场管理</a>
											</td>
										</tr>
									</s:iterator>
								<tbody>
							</table>
							<wysLib:page></wysLib:page>
						</td>
					</tr>
				</table>

			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
