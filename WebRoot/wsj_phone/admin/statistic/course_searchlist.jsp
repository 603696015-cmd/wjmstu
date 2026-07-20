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
		<script type="text/javascript" src="js/menu.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程统计</span>
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
		   <form action="course_searchlist.action" name="caform" method="post">
				<s:hidden name="pN" id="pageNow">
				</s:hidden>
				<s:hidden name="pS">
				</s:hidden>
				<s:hidden name="course.name">
				</s:hidden>
				<s:hidden name="ctype.id">
				</s:hidden>
		  </form>
		<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td">
						<wysLib:ctypeTree rootAble="true" href="course_searchlist.action?str=ctids&ctype.id="></wysLib:ctypeTree> 
				  <td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
				  </td>
					<td valign="top">
					<s:form action="course_searchlist" name="myclist" theme="simple">
							<input type="hidden" name="pN" value="0">
							<input type="hidden" name="pS" value="10"> 
						课程名称：<s:textfield name="course.name"></s:textfield> <input type="button" value="搜索" class="textbg4" onClick="view()">&nbsp;&nbsp; 
						<input type="button" value="导出" class="textbg4" onClick="toexcel(<s:property value="ctype.id" />)"> 
					</s:form>  	
			<s:if test="courses.size==0">没有符合条件的课程</s:if>
			<s:else>
			<table width="100%" border="0" cellpadding="1" cellspacing="1"
				>
				<tr>
					<th height="30" align="center" >
						课程名称
					</th>
					<th width="120" height="30" align="center" >
						课程类别					</th>
					<th width="120" height="30" align="center" >
						创建时间					</th>
					<th width="80" height="30" align="center" >
						基本信息					</th>
					<th height="30" colspan=2 align="center" >
						学员人数
					</th>
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="courses">
					<tr>
						<td height="30" align="center" style="color:#CC0099;">
							<s:property value="name" />
						</td>
						<td width="120" height="30" align="center" >
							<s:property value="ctype.name" />
					  </td>
						<td width="120" height="30" align="center" >
							<s:date name="createtime" format="yyyy-MM-dd" />

					  </td>
						<td width="80" height="30" align="center" >
							<a
								href="course_stat_view.action?course.id=<s:property value="id"/>" class="textbg4">查 看</a>						</td>
						<td height="30" align="center" >
							<s:property value="userCount" />
						</td>
						<td width="60" height="30" align="center" >
							<a
								href="course_user_list.action?course.id=<s:property value="id"/>&course.classid=-1" class="textbg4">查 看</a>						</td>
					</tr>
				</s:iterator></tbody>
			</table>
			</s:else></td></tr></table>
			 
			<script>
				function page(i){ 
					document.getElementById("pageNow").value=i;
					caform.submit();
				} 
				function toexcel(cid){    
					myclist.action = "course_searchlist.action?exprot=true&str=ctids&ctype.id="+cid;
					myclist.submit();
				}
				function view(){    
					myclist.action = "course_searchlist.action";
					myclist.submit();
				}
			</script>
			<wysLib:page></wysLib:page>
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
