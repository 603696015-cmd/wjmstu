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
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
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
						<wysLib:st_list_aj href="stageneral.action?station.id="
							rootAble="true"></wysLib:st_list_aj>
				  <td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
				  </td>
					<td valign="top">
					
			<table width="100%" border="0" cellpadding="1" cellspacing="1">
				<tr>
					<th height="30" align="center" >
						岗位名称
					</th>
					<th height="30" align="center" >
						岗位人数					</th>
					<th height="30" align="center" >
						必修课人次					</th>
					<th  height="30" align="center" >
						必修课学分数					</th>
					<th height="30" colspan=2 align="center" >
						完成必修学分数
					</th>
					<th  height="30" align="center" >
						必修课进度					</th>
					<th  height="30" align="center" >
						选修课数量					</th>
					<th  height="30" align="center" >
						选修课学分数					</th>
					<th width="100" height="30" align="center" >
						最少选修学分数					</th>
					<th width="100" height="30" align="center" >
						完成选修学分数					</th>
					<th  height="30" align="center" >
						选修课进度					</th>
					
				</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="station.name"/>
						</td>
						<td  height="30" align="center" >
							<s:property value="station.count"/>
					  	</td>
						<td  height="30" align="center" >
							<s:property value="station.brc"/>
					  	</td>
						<td  height="30" align="center" >
							<s:property value="station.bscore"/>
						</td>
						<td  height="30" align="center" >
						</td>
						<td  height="30" align="center" >
						</td>
						<td  height="30" align="center" >
							<div style="border: 1px dotted #FF6633;"> <IMG height=14 
                 				 src="images/jd.gif" width="<s:property value="processStr" />%">
                 			</div>
						</td>
						<td height="30" align="center" >
						</td>
						<td  height="30" align="center" >
						</td>
						<td height="30" align="center" >
						</td>
						<td height="30" align="center" >
						</td>
						<td  height="30" align="center" >
							<div style="border: 1px dotted #FF6633;"> <IMG height=14 
                 				 src="images/jd.gif" width="<s:property value="processStr" />%">
                 			</div>
						</td>
					</tr>
				</tbody>
			</table>
			</td></tr></table>
			 
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
