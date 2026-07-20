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
		<script type="text/javascript"> 
				function page(i){
					document.getElementById("pageNow").value=i;
					examFh.submit();
					//document.location.href="examroomwithoutcourse_readlist.action?pS=<s:property value="pS"/>&pN="+i
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
					<wysLib:Navigation ivalue="投票列表" />
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
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;"></label>
			<form action="pollShList.action" method="post"
				name="examFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<%-- 
				<div>
					投票名称：&nbsp;
					<input type="text" name="poll.title"
						value="<s:property value="poll.title"/>">
					创建时间段范围：&nbsp;从
					<input type="text" onclick=setday(this) name="poll.createtimeStart"
						value="<s:date name="poll.createtimeStart"/>">
					&nbsp;到&nbsp;
					<input type="text" onclick=setday(this) name="poll.createtimeEnd"
						value="<s:date name="poll.createtimeEnd"/>">
					<br />
					包含下级节点:
					<input type="checkbox" name="sublibs" value="1"
						<s:if test="sublibs==1">checked="checked"</s:if> />
					结束时间段范围：&nbsp;从
					<input type="text" onclick=setday(this) name="poll.endtimeStart"
						value="<s:date name="poll.endtimeStart" />">
					&nbsp;到&nbsp;
					<input type="text" onclick=setday(this) name="poll.endtimeEnd"
						value="<s:date name="poll.endtimeEnd" />">
					<input onClick="initPN();" type="button" value="搜索" class="textbg4" />
				</div>
				 --%>
			</form>
			<s:if test="polls.size==0">
				没有分配给你的投票
			</s:if>
			<s:else>
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<th width="120" height="25" align="center">
							投票名称
						</th>
						<th width="100" align="center">
							创建者
						</th>
						<th width="120" align="center">
							创建者部门
						</th>
						<th width="120" align="center">
							创建时间
						</th>
						<th width="120" align="center">
							开始时间
						</th>
						<th width="120" align="center">
							结束时间
						</th>
						<th width="200" align="center">
							操作
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="polls">
							<tr>
								<td align="center" height="25">
									<s:property value="title" />
								</td>
								<td align="center">
									<s:property value="creater.realname" />
								</td>
								<td align="center">
									<s:property value="creater.department.name" />
								</td>
								<td align="center">
									<s:date name="createtime"/>
								</td>
								<td align="center">
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="left" style="padding-left:5px;">
									<s:if test="isApply==1">
										<s:if test="stuViewResult==1">
										<a href="pollResult.action?poll.id=<s:property value="id"/>"
											class="textbg4">查看</a>
										</s:if>
										<s:else>
											不允许查看
										</s:else>
									</s:if>
									<s:else>
										<a href="studyPoll.action?poll.id=<s:property value="id"/>"
											class="textbg4">投票</a>
									</s:else>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<wysLib:page></wysLib:page>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
