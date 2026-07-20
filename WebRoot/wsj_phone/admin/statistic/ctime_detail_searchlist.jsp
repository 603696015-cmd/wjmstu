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
		<style type="text/css">
td {
	font-size: 11px;
}
</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="学习详情页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">学时详情</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="javascript:toDetail();">学时概况</a>
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
			<form action="ctime_detail_searchlist.action" method="post"
				name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.realname" />

			</form>
			<script type="text/javascript">
													 	function page(i){
													 		acc_list.action="ctime_detail_searchlist.action";
													 		document.getElementById("pageNow").value=i;
													 		acc_list.submit();
													 	}
													 	function toDetail(){
														 	acc_list.action="ctime_user_searchlist.action";
														 	acc_list.submit();
													 	}
													 	 function toexcel(){
													 		acc_list.action="ctime_detail_list.action";
													 		acc_list.submit();
													 	}
													</script>
			<table width="100%" align="center" cellpadding="2" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<th width="120" align="center">
						姓名
					</th>
					<th width="100" align="center">
						账号
					</th>
					<th width="120" align="center">
						部门
					</th>
					<th align="center" style="padding: 0px">
						<table width="100%" align="center" style="margin: 0px" cellpadding="2" cellspacing="1"
							bgcolor="#EBEBEB">
							<tr>
								<th width="300" align="center">
									课程名称
								</th>
								<th align="center" width="70">
									时长
								</th>
								<th align="center" width="70">
									已学
								</th>
								<th align="center" width="70">
									进度
								</th>
								<!--<th align="center" >
						总学分					</th>
					<th align="center" >
						已获学分					</th>-->
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="elUsers" status="st">
								</s:iterator>
							</tbody>
						</table>
					</th>
					<th width="90" align="center">
						总时长
					</th>
					<th width="50" align="center">
						已学
					</th>
					<!--<th align="center" >
						总学分					</th>
					<th align="center" >
						已获学分					</th>-->
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="elUsers" status="st">
						<tr>
							<td align="center">
								<s:property value="realname" />
							</td>
							<td align="center">
								<s:property value="username" />
							</td>
							<td align="center">
								<s:property value="department.name" />
							</td>
							<td align="center" bgcolor="#FFFFFF" style="padding: 0px;">
								<table width="100%" align="center" cellpadding="1"
									style="margin: 0px;" cellspacing="1" bgcolor="#EBEBEB">
									<s:iterator value="myCourses">
										<tr>
											<td width="300" align="center">
												<s:property value="course.name" />
											</td>
											<td width="55" align="center">
												<s:property value="course.during" />
												分钟
											</td>
											<td width="55" align="center">
												<s:property value="passtime" />
												分钟
											</td>
											<td width="55" align="center">
												<s:property value="processStr" />
												%
											</td>
										</tr>
									</s:iterator>
								</table>
							</td>
							<td align="center">
								<s:property value="ct_time" />
							</td>
							<td align="center">
								<s:property value="xx_time" />
							</td>
							<!--<td align="center" >
						<s:property value="ct_credit" />						</td>
						<td align="center" >
						<s:property value="xx_credit" />						</td>-->
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<p>&nbsp;
				
			</p>
			<p>
				<br>
				<wysLib:page></wysLib:page>
				<a target="" href="javascript:toexcel();" class="textbg">导出列表</a>
				<a target="" href="ctime_user_searchlist.action" class="textbg">返回人员列表</a>
			</p>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
