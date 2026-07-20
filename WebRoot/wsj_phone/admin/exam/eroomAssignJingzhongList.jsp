<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
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
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏考场树</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示考场树</a>';
					}
				}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
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
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td width="150px" valign="top"
							id="tree_list_td" style="display:none">
							<wysLib:eroomLibTree  href="eroomAssignJingzhongList.action?sublibs=1&str=libids&eroomLib.id=" rootAble="true"></wysLib:eroomLibTree>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg " />
						</td>
						<td width="1000">
							<form action="eroomAssignJingzhongList.action?eroomLib.id=<s:property value="eroomLib.id"/>" name="erform" method="post">
								<s:hidden name="pN" id="pageNow">
								</s:hidden>
								<s:hidden name="pS">
								</s:hidden>
								<s:hidden name="libid" value="libid"></s:hidden>
								<div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg">显示考场树</a>
								</div>
								<div>
									包含下级节点: 
									<input type="checkbox" name="sublibs" 
									<s:if test="sublibs==1">checked="checked"</s:if>
									 value="1">&nbsp;&nbsp;&nbsp;
									考场名称&nbsp;<input size="16" type="text" name="examRoom.title" value="<s:property value="examRoom.title"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									状态&nbsp;
									<s:select theme="simple" headerKey="-1" headerValue="全部" list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}" name="examRoom.valid" value="examRoom.valid"/>
									开考时间&nbsp;从<input size="16" type="text" onclick=setday(this) name="examRoom.begintime" value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">&nbsp;到&nbsp;
									<input size="16" type="text" onclick=setday(this) name="examRoom.endtime" value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">
									<s:select theme="simple" list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}" name="examRoom.classid" value="examRoom.classid"/>
									<input onClick="initPN();" class="textbg4" type="button" value="搜索" />
								</div>
							</form>
							<table width="100%" align="center" cellpadding="1" cellspacing="1">
								<tr>
									<th width="200" height="30" align="center" >
										考场标题									</th>
									<th width="60" align="center" >类型</th>
									<!--<th height="30" align="center" >
										考场地点
									</th>-->
									<!--<th height="30" align="center" >
										类别库
									</th>-->
									<!--<th height="30" align="center" >
										通过百分比
									</th>-->
									<s:if test="examRoom.classid!=-1">
									<th width="150" height="30" align="center" >
										所属课程									</th>
									</s:if>
									<th width="150" height="30" align="center" >
										类别									</th>
									<th width="70" height="30" align="center" >
										创建者									</th>
									<th width="130" height="30" align="center" >
										开始时间									</th>
									<th width="130" height="30" align="center" >
										结束时间									</th>
									<!--<th height="30" align="center" >
										类型
									</th>-->
									<th width="100" height="30" align="center" >
										审核状态									</th>
									<th width="100" height="30" align="center" >
										复核状态									</th>
									<th width="120" height="30" align="center" >
										参加(计划)人数									</th>
									<!--<th height="30" align="center" >
										试卷数
									</th>-->
								  <th width="150" height="30" align="center" >&nbsp;</th> 
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="examRooms">
									<tr>
										<td width="200" height="30" style="padding-left:8px;color:blue;" align="left">  
											
											<s:property value="title" />									  </td>
										<td width="60" align="center" ><s:if test="isApplication == 1">
												<SPAN style="color: red">【申请】</SPAN>
											</s:if>
											<s:elseif test="isApplication == 2">
												<SPAN style="color: blue;">【全工】</SPAN>
											</s:elseif>
											<s:else>
												<SPAN style="color: gray">【分配】</SPAN>
											</s:else></td>
										<s:if test="examRoom.classid!=-1">
											<td width="150" height="30" align="center" >
												<s:property value="course.name" />										  </td>
										</s:if>
										<!--<td height="30" align="center" >
											<s:property value="location" />
										</td>-->
										<!--<td height="30" align="center" >
											<s:property value="eroomLib.name" />
										</td>-->
										<!--<td height="30" align="center" >
											<s:property value="passgrade" />
										</td>-->
										 <td width="150" height="30" align="center" >
											<s:property value="eroomLib.name" />									  </td>
										<td width="70" height="30" align="center" >
											<s:property value="creater.realname" />									  </td>
										<td width="130" height="30" align="center" >
											<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />									  </td>
										<td width="130" height="30" align="center" >
											<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />									  </td>
										<!--<td height="30" align="center" >
											<s:property value="typeName" />
										</td>-->
										<td width="100" height="30" align="center" >
										<font color="<s:if test="valid==1">green</s:if>">
										<s:property value="validName" /></font>									  </td>
										<td width="100" height="30" align="center" >
										<font color="<s:if test="uvalid==1">red</s:if>">
										<s:property value="uvalidName" /></font>									  </td>
									  	<td width="120" height="30" align="center" >
											<s:property value="usersize"/>
											<s:if test="isApplication == 1">
												<span style="color:red">(<s:property value="planNumber"/>)</span>
											</s:if>
										</td>
										<!--<td height="30" align="center" >
											<s:property value="epsize" />
										</td>-->
										<td width="150" height="30" align="left" >
											<a href="eroomAssignJingzhong.action?examRoom.id=<s:property value="id" />" class="textbg4">分配</a>
										</td>
									</tr>
								</s:iterator></tbody>
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
	
	</body>
</HTML>
