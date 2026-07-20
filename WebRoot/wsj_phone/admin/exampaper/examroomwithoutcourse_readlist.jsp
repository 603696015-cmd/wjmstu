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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
					<wysLib:Navigation ivalue="考场列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 考试阅卷</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align:center;">
			<label style="font-size: 16px;"></label>
			<form action="examroomwithoutcourse_readlist.action" method="post"
				name="examFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div  style=" vertical-align:middle;background:#F8FCFE; height:30px;line-height:40px;padding:10px;">
					考场标题：&nbsp;
					<input type="text" name="examRoom.title"
						value="<s:property value="examRoom.title"/>">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 状态&nbsp;
					<s:select theme="simple" headerKey="-1" headerValue="全部"
						list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
						name="examRoom.valid" value="examRoom.valid" />
					时间段范围：&nbsp;从
					<input type="text" onclick=setday(this) name="examRoom.begintime"
						value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">
					&nbsp;到&nbsp;
					<input type="text" onclick=setday(this) name="examRoom.endtime"
						value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">
					类型：<s:select theme="simple"
						list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}"
						name="examRoom.classid" value="examRoom.classid" />
					<input onClick="initPN();" type="button" value="搜索" class="textbg4" />
				</div>
			</form>
			<s:if test="examRooms.size==0">
				<br>
					  您当前没有需要评阅的考场或试卷			</s:if>
			<s:else>
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<!--<th width="40" align="center" >&nbsp;						</th>-->
						<th width="200" align="center">
							考场信息
						</th>
						<th width="60" align="center">
							类型
						</th>
						<th width="120" align="center">
							考场地点
						</th>
						<!--<th align="center" >
							监考老师
						</th>-->
						<th width="150" align="center">
							开始时间
						</th>
						<th width="150" align="center">
							结束时间
						</th>
						<th width="90" align="center">
							考场状态
						</th>
						<th width="120" align="center">
							人数
						</th>
						<th width="70" align="center">&nbsp;
							
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="examRooms">
							<tr>
								<!--<td width="40" align="center" >
								<input type="checkbox" name="delete_item[]" value="5">
						  </td>-->
								<td style="padding: 3px 0px 3px 2px;" valign="top" align="left">
									<div
										style="word-wrap: break-word; word-break: break-all; width: 100%;">
										<strong style="font-size: 15px; color: blue;"><s:property
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
										<s:property value="creater.realname" />
										<br />
										<s:if test="examRoom.classid!=-1">
											<strong> 所属课程: </strong>
											<s:property value="course.name" />
										</s:if>
									</div>
								</td>
								<td align="center">
									<s:if test="isApplication == 1">
										<SPAN style="color: red">【申请】</SPAN>
									</s:if>
									<s:elseif test="isApplication == 2">
										<SPAN style="color: blue;">【全工】</SPAN>
									</s:elseif>
									<s:else>
										<SPAN style="color: gray">【分配】</SPAN>
									</s:else>
								</td>
								<td align="center">
									<s:property value="location" />
								</td>
								<!--<td align="center" >
								<s:property value="supervisor.realname" />
							</td>-->

								<td align="center">
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									<s:property value="validName" />
								</td>
								<td align="center">
									参加：<s:property value="usersize" />
									<s:if test="isApplication == 1"><br/>
										<span style="color: red">计划：<s:property
												value="planNumber" /></span>
									</s:if>
								</td>
								<td align="center">
									<a
										href="exampaperreadlist.action?examRoom.id=<s:property value="id"/>"
										class=textbg5>阅 卷</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</s:else>
			<script> 
						function page(i){
							document.getElementById("pageNow").value=i;
							examFh.submit();
							//document.location.href="examroomwithoutcourse_readlist.action?pS=<s:property value="pS"/>&pN="+i
						}
						function initPN(){
							document.getElementById("pageNow").value=0;
							examFh.submit();
						}
								
				</script>
			<wysLib:page></wysLib:page>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
