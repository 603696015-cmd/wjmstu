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
		<script type="text/javascript" src="js/jquery.js"></script>
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
		<script type="text/javascript"> 
				function page(i){
					document.getElementById("pageNow").value=i;
					examFh.submit();
					//document.location.href="examroomwithoutcourse_readlist.action?pS=<s:property value="pS"/>&pN="+i
				}
				function initPN(){
					document.getElementById("pageNow").value=0;
					examFh.submit();
				}
				function pollSh(id,status){
					if(window.confirm("确认提交？")){
						document.getElementById("pollId").value=id;
						document.getElementById("pollStatus").value=status;
						pollShForm.submit();
					}
				}
				
		</script>
		
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="问卷统计列表" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<s:form action="pollSh" method="post" name="pollShForm">
			<s:hidden name="poll.id" id="pollId" />
			<s:hidden name="poll.status" id="pollStatus" />
		</s:form>
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;"></label>
			<form action="pollList.action" method="post"
				name="examFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div>
					问卷名称：&nbsp;
					<input type="text" name="poll.title"
						value="<s:property value="poll.title"/>">
					创建时间段范围：&nbsp;从
					<input type="text" onclick=setday(this) name="poll.createtimeStart"
						value="<s:date name="poll.createtimeStart"/>">
					&nbsp;到&nbsp;
					<input type="text" onclick=setday(this) name="poll.createtimeEnd"
						value="<s:date name="poll.createtimeEnd"/>">
					<br />
					结束时间段范围：&nbsp;从
					<input type="text" onclick=setday(this) name="poll.endtimeStart"
						value="<s:date name="poll.endtimeStart" />">
					&nbsp;到&nbsp;
					<input type="text" onclick=setday(this) name="poll.endtimeEnd"
						value="<s:date name="poll.endtimeEnd" />">
					<input onClick="initPN();" type="button" value="搜索" class="textbg4" />
				</div>
			</form>
			<s:if test="polls.size==0">
			</s:if>
			<s:else>
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan=20><div id="Div_ToolsBar"></div></td>
					</tr>
					<tr>	
						<th width="180" align="center">
							问卷名称
						</th>
						<th width="120" align="center">
							开始时间
						</th>
						<th width="120" align="center">
							结束时间
						</th>
						<th width="70" align="center">
							安排人数
						</th>
						<th width="70" align="center">
							作答人数
						</th>
						<th width="80" align="center">
							作答率
						</th>
						<th width="80" align="center">
							查看统计
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="examRooms" status="status">
							<tr>
								<td align="center">
									<s:property value="title" />
								</td>
								<td align="center">
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									<s:property value="usersize" />
								</td>
								<td align="center">
									<s:property value="UserSize" />
								</td>
								<td align="center">
									<s:property value="percent" />
								</td>
								<td align="center">
									<a href="questionnaireResult.action?examRoom.id=<s:property value="id"/>"
											class="textbg4">查看</a>
								</td>
								<input type="hidden" id="valid_<s:property value='#status.index'/>" value="<s:property value='valid' />"/>
								<input type="hidden" id="uvalid_<s:property value='#status.index'/>" value="<s:property value='uvalid' />"/>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</s:else>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
