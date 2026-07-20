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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的考试考场 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">  
				<table width="1080px" cellpadding="1" cellspacing="1">
					<tr>
						<td width="150px" valign="top" id="tree_list_td"> 
							<wysLib:eroomLibTree
								href="examroom_mylist.action?examRoom.eroomLib.id=" rootAble="true"></wysLib:eroomLibTree>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand" onClick="changeTreeDisplay(this)" />
						</td>
						<td>
					<form action="examroom_mylist.action?examRoom.eroomLib.id=<s:property value="examRoom.eroomLib.id"/>" method="post" name="erform">
					    <s:hidden name="pN" id="pageNow">
						</s:hidden>
						<s:hidden name="pS">
						</s:hidden>
						<div>
							考场名称&nbsp;<input size="16" type="text" name="examRoom.title" value="<s:property value="examRoom.title"/>">&nbsp;&nbsp;&nbsp;
							状态&nbsp;
							<s:select theme="simple" headerKey="-1" headerValue="全部" list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中',9:'已删除'}" name="examRoom.valid" value="examRoom.valid"/>
							&nbsp;&nbsp;&nbsp;开考时间&nbsp;从<input size="16" type="text" onclick=setday(this) name="examRoom.begintime" value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">&nbsp;到&nbsp;
							<input size="16" type="text" onclick=setday(this) name="examRoom.endtime" value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">&nbsp;&nbsp;&nbsp;
							<s:select theme="simple" list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}" name="examRoom.classid" value="examRoom.classid"/>
							<input onClick="initPN();" type="submit" value="搜索" />
						</div>
					</form>
					<table width="100%" align="center" cellspacing="2">
						<tr>
							<!-- <th width="20" height="30" align="center" >&nbsp;</th> -->
							<th height="30" align="center" >
								考场标题
							</th>
							<s:if test="examRoom.classid!=-1">
								<th height="30" align="center" >
									所属课程
								</th>
							</s:if>
							<th height="30" align="center" >
								类别
							</th>
							<th height="30" align="center" >
								创建者
							</th>
							<th width="120" height="30" align="center" >
								开考时间							</th>
							<th width="120" height="30" align="center" >
								结束时间							</th>
							<th width="70" height="30" align="center" >
								状态
							</th>
							<th height="30" align="center" >
								考生人数
							</th>
							<th width="150" height="30" align="center" >&nbsp;</th> 
						</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="examRooms">
							<tr>
							<!-- 	<td width="20" height="30" align="center" >
									<input type="checkbox" name="delete_item[]" value="5">
							  </td>
							   -->
								<td height="30" align="center" >
									<s:property value="title" />
								</td>
								<s:if test="examRoom.classid!=-1">
									<td height="30" align="center" >
										<s:property value="course.name" />
									</td>
								</s:if>
								 <td height="30" align="center" >
									<s:property value="eroomLib.name" />
								</td>
								<!--<td width="70" height="30" align="center" >
									<s:property value="supervisor.realname" />
							  </td>
								-->
							<!-- 	<td width="70" height="30" align="center" >
									<s:if test="supervisorrealname!=null">
										<s:iterator value="supervisorrealname" var="str" status="st">
										<s:property value="str" />&nbsp;&nbsp; 
									</s:iterator>
									</s:if> 
								</td>
							 -->
							
								<td height="30" align="center" >
									<s:property value="creater.realname" />
								</td>
								<td width="120" height="30" align="center" >
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
							  </td>
								<td width="120" height="30" align="center" >
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" /> 
							  </td>
								<td width="70" height="30" align="center" >
									<s:property value="validName"/>
							  </td>
							  <td width="70" height="30" align="center" >
									<s:property value="usersize"/>
							  </td>
							  <td>
							   <s:if test="valid == 0 || valid ==2">
								<s:if test="course.name!=null">
									<!-- <a href="examroom_alterInit.action?examRoom.id=<s:property value="id"/>" class="textbg4">编 辑</a>  -->
									<a href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>" class="textbg4">编 辑</a>
								</s:if><s:else>
									<a href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>" class="textbg4">编 辑</a>
								</s:else>
							   </s:if><s:else>
							   	  <a href="examroom_sh_view.action?examRoom.id=<s:property value="id"/>" class="textbg4">查 看</a> 
							   </s:else>
							  </td>
							</tr>
						</s:iterator><tbody>
				  </table>

					<wysLib:page></wysLib:page>
						</td>
					</tr>
			</table> 
		</div> 
				
				<script> 
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				function initPN(){
					document.getElementById("pageNow").value=0;
					erform.submit();
				}
			</script>
		<!-- 内容 -->
	</BODY>
</HTML>
