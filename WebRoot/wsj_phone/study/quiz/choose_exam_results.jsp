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
		<TITLE>报名考场结果</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="报名考场结果" /></div>
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
			<table width="100%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					报名考场结果
				</caption>
				<tr>
					<th width="200" height="30" align="center" >
						考场名称					</th>
					<th width="150" height="30" align="center" >
						创建者					</th>
					<th height="30" align="center" >
						有效时间段					</th>
					<th width="80" height="30" align="center" >
						报名结果</th> 
					<th width="80" height="30" align="center" >
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="examRooms">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="title" />
						</td>
						<td height="30" align="center" >
							<s:property value="creater.realname" />				  
						</td>
						<td height="30" align="center" > 
							<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />~<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="center" > 
							<s:if test="svalid == 0">审核通过</s:if>
							<s:elseif test="svalid == 1">审核中</s:elseif>
							<s:elseif test="svalid == 2">审核不通过</s:elseif>
							<s:elseif test="svalid == 3">审核通过</s:elseif>
							<s:else>未知</s:else>
						</td> 
						<td width="200" height="30" align="left" style="padding-left:10px;" >
							<a href="exam_view.action?examRoom.id=<s:property value="id"/>" target="_blank" class="textbg6">考场详情</a>
							<s:if test="svalid==2">
								<a href="listSimpleRemack.action?simpleRemack.type=1&simpleRemack.typeid=<s:property value="id" />&simpleRemack.toUser.id=<s:property value="#session.userId" />" class="textbg6" style="width:80px;">不通过原因</a>
							</s:if>
						</td>
					</tr>
				</s:iterator></tbody>
		  </table>
		  <form action="choose_exam_results.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow"> 
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				</script>			  
				<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
