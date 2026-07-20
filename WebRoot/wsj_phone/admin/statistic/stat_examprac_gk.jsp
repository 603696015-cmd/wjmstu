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
		<TITLE>考试概况</TITLE>
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
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?   
		   "#ffffff" :       "#f4f4f4" )
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dtreequizdep.js"></script>
		<script type="text/javascript">
			function view(){   
				statEval.action = "stat_examprac_gk.action";
				statEval.submit();
			}
			function setabled(idstr,id){
					document.getElementById(idstr+id).checked=true;
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="练习概况" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习概况</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_examprac_detail.action?examprac.id=<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">练习详情</a>
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
		<div style="margin-top: 0px; text-align: center;">
			<table width="1100" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td">
						<%
							Examprac examprac = (Examprac) request.getAttribute("examprac");
							String url = "stat_examprac_gk.action?examprac.id="
									+ examprac.getId() + "&sub_department=1&department.id=";
						%>
						<%-- 
						<wysLib:dep_list_f rootAble="true"
							href="<%=url%>"></wysLib:dep_list_f>
						 --%>
						<form action="stat_examprac_gk.action" method="post"
							name="statEval">
							<input type="button" value="查看" class="textbg4" onClick="view()" />
							<%-- 	<input type="button" value="导出" onClick="toexcel()" /> --%>
							<s:hidden name="examprac.id"></s:hidden>
							<%-- 
							<wysLib:dep_list_cb attrname="depTree"
								inputname="departments1.id"></wysLib:dep_list_cb>
							 --%>
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
						<table align="center" cellpadding="1" cellspacing="1"
							bgcolor="#EBEBEB">
							<tr>
								<td height="30" style="padding-left: 8px; color: blue;"
									align="left">
									部门名称
								</td>
								<td height="30" align="center">
									应练习人数
								</td>
								<td height="30" align="center">
									已练习人数
								</td>
								<td height="30" align="center">
									平均练习人次
								</td>
								<td height="30" align="center">
									部门全部人员平均分
								</td>
								<td height="30" align="center">
									已练习人员平均分
								</td>
								<td height="30" align="center">
									及格人数
								</td>
								<td height="30" align="center">
									部门全部人员及格率
								</td>
								<td height="30" align="center">
									已练习人员及格率
								</td>
								<td height="30" align="center">
									90分以上
								</td>
								<td height="30" align="center">
									80-89分
								</td>
								<td height="30" align="center">
									70-79分
								</td>
								<td height="30" align="center">
									60-69分
								</td>
								<td height="30" align="center">
									60以下
								</td>
							</tr>
							<s:iterator value="departments1">
								<tr>
									<td height="30" style="padding-left: 8px; color: blue;"
										align="left">
										<s:property value="name" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.totalnumber" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.usersize" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.avgnumber" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.avgscore" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.avgscorejoin" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.passsize" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.passreta" />
										%
									</td>
									<td height="30" align="center">
										<s:property value="examprac.passreta2" />
										%
									</td>
									<td height="30" align="center">
										<s:property value="examprac.pass9_" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.pass8_9" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.pass7_8" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.pass6_7" />
									</td>
									<td height="30" align="center">
										<s:property value="examprac.pass_6" />
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>
			<div style="text-align: center;">
				<a href="stat_examprac_gk.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >查看概况</a>
				<a href="stat_examprac_detail.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >查看详情</a>
				<a href="stat_examprac_eval.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >部门比较</a>
				<a href="stat_examprac_list.action" class=textbg >返回练习列表</a>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>