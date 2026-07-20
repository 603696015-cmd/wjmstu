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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
			<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
	<script type="text/javascript" src="js/message.js"></script>
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">搜索课程</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;text-align: center;">
			 <TABLE class=serach style="MARGIN-TOP: 5px" height=46
															cellSpacing=5 cellPadding=0 width="69%">
															<!--DWLayoutTable-->
															<TBODY>
																<TR>
																	<TD valign=middle height=34>
																		<TABLE height=15 cellSpacing=0 cellPadding=0
																			width="98%" bgColor=#ffffff border=0>
																			<TBODY>
																				<TR>
																				<TD align=right  height=34 width="60">
																						关键字 
																					</TD>
																					<TD align=right width="70%" height=34>
																						<INPUT id="search_content" type="text"
																							style="WIDTH: 400px"
																							name="search_content">
																					</TD>
																					<TD align=center valign=middle >
																						<SELECT id=search_type>
																							<OPTION value='kc' selected>
																								-课程-
																							</OPTION>
																							<OPTION value='zs'>
																								-知识-
																							</OPTION>
																							<OPTION value='zl'>
																								-素材-
																							</OPTION>
																							<OPTION value='cl'>
																								-培训班-
																							</OPTION>
																							<!--
																							<OPTION value=2>
																								-帖子-
																							</OPTION>
																						--></SELECT>
																					</TD>
																					<TD>
																					<form action="" method="post" name="isform" target="_blank">
																						<input type="hidden" name="knowledge.title" id="klt"/>
																						<input type="hidden" name="course.name" id="cn"/>
																						<input type="hidden" name="qstuff.title" id="qtitle" />
																						<input type="hidden" name="elclass.name" id="elcname" />
																						<input type="hidden" name="pN" value="0"/>
																						<input type="hidden" name="pS" value="10"/>
																						
																					</form>
																					<script type="text/javascript">
																						function indexsearch(){
																							var content =  document.getElementById("search_content").value ;
																							var url = "";
																							if(document.getElementById("search_type").value=='zs'){
																								url="knowledge_center_listbytitle.action";
																								document.getElementById("klt").value=content;
																							}
																							if(document.getElementById("search_type").value=='kc'){
																								url = "course_listbytitle.action";
																								document.getElementById("cn").value=content;
																							}
																							if(document.getElementById("search_type").value=='cl'){
																								url = "class_listbytitle.action";
																								document.getElementById("elcname").value=content;
																							}
																							if(document.getElementById("search_type").value=='zl'){
																								url = "stuff_listbyTitle.action";
																								document.getElementById("qtitle").value=content;
																							}
																							isform.action = url;
																							isform.submit();
																						}
																					</script>
																						<INPUT onClick="indexsearch();";
																							style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
																							type=image src="elfrontimages/search_01.gif">
																					</TD>
																				</TR>
																			</TBODY>
																		</TABLE>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
