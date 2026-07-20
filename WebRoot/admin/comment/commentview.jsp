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
		<TITLE>留言信息</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="留言查看" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		
			<table width="100%"> 
			<caption>留言浏览</caption>
				<tr>
				
				   <td width="80">  留言者：</td>
				     <td><s:property value="courseComment.user.realname"  /></td>
				      <td>留言时间： 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->  
				           
				           </td>
				 
				    <td><s:property value="courseComment.commentdate" /></td>
				    <td>状态</td><td><s:property value="courseComment.StatusName"  /></td>
				</tr>
				<tr >
					<td  width="80">留言内容：</td>
					<td  colspan="5" ><s:property value="courseComment.content"  /></td>
				</tr>
				<tr >
					<td  colspan="6" ><a onclick="javascript:history.back(-1);" class=textbg4>返回</a></td>
				</tr>
			</table>

		</div>
	</BODY>
</HTML>
										   