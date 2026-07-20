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
		<TITLE>流量统计</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dtreequizdep.js"></script>
		<script type="text/javascript">
		function toexcel(){   
				statEval.action = "quiz_stat_view.action?exprot=true";
				statEval.submit();
			}
			function view(){   
				statEval.action = "displayFlowInfo.action";
				statEval.submit();
			}
			function setabled(idstr,id){
				//alert(idstr+id);
				document.getElementById(idstr+id).checked=true;
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="流量统计" /></div>
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
			<table width="1100" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td">
						 <form action="displayFlowInfo.action" method="post" name="statEval">
							&nbsp;<input type="button" class="textbg4" value="查看" onClick="view()" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%-- <input type="button" value="导出" onClick="toexcel()" /> --%>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%--  <wysLib:dep_list_cb attrname="depTree" inputname="departments.id"></wysLib:dep_list_cb> --%>
							<wysLib:dep_list_aj href="" itype="cb" iname="departments.id" rootAble="true"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([<s:iterator value="departments" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments.size-1)!=#depst.index">,</s:if></s:iterator>]);
							</script>
						</form>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table width="100%" align="center" cellpadding="1" cellspacing="1"
							bgcolor="#EBEBEB">
							<tr>
							    <td height="30" style="padding-left:8px;color:blue;" align="left">
									部门名称
							    </td>
								<td height="30" align="center" >
									注册用户总数
								</td>
								<td height="30" align="center" >
									当前登录用户数
								</td>
								<td height="30" align="center" >
									今天登录人数
								</td>
								<td height="30" align="center" >
									今天登录人次
								</td>
								<td height="30" align="center" >
									本周登录人数
								</td>
								<td height="30" align="center" >
									本周登录人次
								</td>
								<td height="30" align="center" >
									本月登录人数
								</td>
								<td height="30" align="center" >
									本月登录人次
								</td>
							</tr>
							<s:iterator value="flows">
								<tr>
									<td height="30" style="padding-left:8px;color:blue;" align="left">
										<s:property value="department.name" />
								    </td>
									<td height="30" align="center" >
										<s:property value="registerUserCount" />
									</td>
									<td height="30" align="center" >
										<s:property value="loginUserCount" />
									</td>
									<td height="30" align="center" >
										<s:property value="dayLoginUserCount" />
									</td>
									<td height="30" align="center" >
										<s:property value="dayLoginUserSum" />
									</td>
									<td height="30" align="center" >
										<s:property value="weekLoginUserCount" />
									</td>
									<td height="30" align="center" >
										<s:property value="weekLoginUserSum" />
									</td>
									<td height="30" align="center" >
										<s:property value="monthLoginUserCount" />
									</td>
									<td height="30" align="center" >
										<s:property value="monthLoginUserSum" />
									</td>
								</tr>
							</s:iterator>
					  </table></td>
				</tr>
		  </table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>

