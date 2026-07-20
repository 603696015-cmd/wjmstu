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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script>
			function page(i){
				document.getElementById("pageNow").value=i;
				examFh.submit();
			}
			function initPN(){
				document.getElementById("pageNow").value=0;
				examFh.submit();
			}
			function disUserInfo(roomid,status){
				width=850;
				height=500;
   				var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("eroomStudyInfo.action?examRoom.id="+roomid+"&myroom.status="+status+"&x="+Math.random(),"",sFeature);
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场列表" />
				</div>
			</li>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
			<!--<li>
				<span style="font-weight: bold;">我监考的考场</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="myExamroom_list.action" method="post" name="examFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div style="height:30px; text-align:center; background-color:#F8FCFE; line-height:30px;">
					考场标题：&nbsp;
					<input size="16" type="text" name="examRoom.title"
						value="<s:property value="examRoom.title"/>">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="hidden" value="-1" name="examRoom.valid" /> <!-- 状态&nbsp;
					<s:select theme="simple" headerKey="-1" headerValue="全部"
						list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
						name="examRoom.valid" value="examRoom.valid" /> -->
					时间段范围：&nbsp;从
					<input size="16" type="text" onclick=setday(this)
						name="examRoom.begintime"
						value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">
					&nbsp;到&nbsp;
					<input size="16" type="text" onclick=setday(this)
						name="examRoom.endtime"
						value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">
					类型：<s:select theme="simple"
						list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}"
						name="examRoom.classid" value="examRoom.classid" />
					<input onClick="initPN();" type="button" class="textbg4" value="搜索" />
				</div>
			</form>
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="300" align="center">
						考场信息
					</th>
					<%-- 
					<th width="60" align="center" >类型</th>
					<th align="center" >
						所属课程					</th>
					<th align="center" >
						类别					</th>
					 --%>
					<!-- 
					<th width="120" align="center" >
						考场地点
					</th>
					 -->
					<th width="130" align="center">
						开始时间
					</th>
					<th width="130" align="center">
						结束时间
					</th>
					<th width="130" align="center">
						总考核人数
					</th>
					<th width="100" align="center">
						缺考人数
					</th>
					<%-- 
					<th align="center" >
						审核状态					</th>
					<th width="120" align="center" >
						参加(计划)人数					</th>
					 --%>
					<th width="110" align="center">&nbsp;
						
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="examRooms">
						<tr>
							<td style="padding: 3px 0px 3px 2px;" valign="top" align="left">
								<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size:15px;color: blue;"><s:property
														value="title" /> </strong>
												<br />
												<strong>类别:</strong>
												<s:property value="eroomLib.name" />
												<br />
												<strong>组织单位:</strong>
												<s:property value="depName" />
												<br />
												<strong>组织工钟:</strong>
												<s:property value="jingzhong" />
												<br />
												<strong>创建者:</strong>
												<s:property value="creater.realname" /><br/>
												<s:if test="examRoom.classid!=-1">
													<strong> 所属课程: </strong><s:property value="course.name" />
												</s:if>
											</div>
							</td>
							<%-- 
						<td width="60" align="center" ><s:if test="isApplication == 1">
								<SPAN style="color:red">申请</SPAN>							</s:if><s:else>
								<SPAN style="color:gray">分配</SPAN>
							</s:else></td>
						<td align="center" >
							<s:property value="course.name" />						</td>
						<td align="center" >
							<s:property value="eroomLib.name" />						</td>
					  --%>
							<!-- 
						<td align="center" >
							<s:property value="location" />					  </td>
						 -->
							<td align="center">
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<s:property value="usersize" />
								<a class="textbg4"
									href="javascript:disUserInfo('<s:property value="id" />',-1);">详情</a>
							</td>
							<td align="center">
								<s:property value="usize" />
								<a class="textbg4"
									href="javascript:disUserInfo('<s:property value="id" />',0);">详情</a>
							</td>
							<%-- 
						<td align="center" >
							<s:property value="validName" />						</td>
						<td width="120" align="center" >
							<s:property value="usersize" />
									<s:if test="isApplication == 1">
										<span style="color:red">(<s:property value="planNumber"/>)</span>									</s:if>						</td>
						 --%>
							<td align="center">
								<a
									href="myExamroom_man.action?examRoom.id=<s:property value="id"/>"
									class="textbg">进入监考</a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
