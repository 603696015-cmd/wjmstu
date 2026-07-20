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
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>添加培训记录</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
  </HEAD>
  <script type="text/javascript">
       function downLoadStuff(id){
       		document.location.href="lineTrainRecordStuffDownload.action?lineTrainRecordStuff.id="+id;
       }
  </script>
  <body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<!-- span style="font-weight: bold;">我的课程</span>
					<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="lineTrainRecord_addInt.action">添加线下培训记录</a>
					<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="lineTrainRecord_list.action">查看与修改</a>
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
				<table width="100%" cellpadding="1" cellspacing="1" >
					<tr>
						<td width="160" height="30" align="center" >
							培训名称：
						</td>
						<td width="300">
							<s:property value="linetrainrecord.trainname" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训开始时间：
						</td>
						<td >
							<s:property value="linetrainrecord.trainstarttime" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训结束时间：
						</td>
						<td >
							<s:property value="linetrainrecord.trainendtime" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训时长：
						</td>
						<td >
							<s:property value="linetrainrecord.trainlength" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训证书名称：
						</td>
						<td >
							<s:property value="linetrainrecord.certificate" />
						</td>
					</tr>
	                <tr>
						<td width="160" height="30" align="center" >
							备注：
						</td>
						<td >
							<s:property value="linetrainrecord.remark" />
						</td>
					</tr>
		  </table>
				<div id="stuff" style="margin-top:8px;width:460px;">
					<s:iterator value="linetrainrecord.lineTrainRecordStuffs">
						<div style="text-align:left;">
							附件标题：<s:property value="title" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:downLoadStuff('<s:property value="id" />');">下载</a>
						</div>
					</s:iterator>
				</div>
		</div>
  
	</body>
</html>