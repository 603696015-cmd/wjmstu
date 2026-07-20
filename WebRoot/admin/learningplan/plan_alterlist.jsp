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
		<TITLE>学习计划管理</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
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
	<object classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6"type="application/x-oleobject" id="alarmPlayer" height="0" width="0">
		<param name="autoStart" value="false">
		<param name="balance" value="0">
		<param name="currentPosition" value="0">
		<param name="currentMarker" value="0">
		<param name="enableContextMenu" value="true">
		<param name="enableErrorDialogs" value="false">
		<param name="enabled" value="true">
		<param name="fullScreen" value="false">
		<param name="invokeURLs" value="false">
		<param name="mute" value="true">
		<param name="playCount" value="1">
		<param name="rate" value="1">
		<param name="uiMode" value="none">
		<param name="volume" value="100">
	</object>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="学习计划列表" />
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
			<s:form action="plan_alterlist" name="myelist" theme="simple">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						
						
						<td valign="top">
							
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1" bgcolor="#D1E4F5">
							</table>
								<table width="100%" align="center" cellspacing="1"
									cellpadding="1">
									<tr>
										
										<th width="215" height="30" align="center">
											计划名称</th>
										<th width="266" height="30" align="center">
											计划周期</th>
										<th width="316" align="center">
											计划开始时间</th>
										<th width="316" height="30" align="center">
											计划结束时间</th>
										<th width="291" height="30" align="center">
											操作 </th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="learnplans">
											<tr>
												
												<td height="30" align="center">
													<s:property value="name" />
												<td width="266" height="30" align="center">
													<s:property value="period" />
										        <td width="316" align="center"> 
										        	<s:date name="starttime" format="yyyy-MM-dd HH:mm:ss"/>
										        </td>                                                 
									          	<td width="316" height="30" align="center">
									          		<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss"/>
										        </td>
											    <td width="291" height="30" align="center" colspan="2">
										<!-- 		<a 
														href="vocabulary_view.action?vocabulary.id=<s:property value="id" />"
														class=textbg4>查 看</a> -->	
														<a
															href="plan_alterInit.action?learnplan.id=<s:property value="id" />"
															class=textbg4>修 改</a>
									<!-- 				<a
														href="vocabulary_delete.action?vocabulary.id=<s:property value="id" />"
														class=textbg4>删 除</a>		 -->											</td>
											</tr>
										</s:iterator>
									</tbody>
							  </table>
								<br>
								<script>
									function page(i){
										document.getElementById("pageNow").value=i;
										myelist.submit();
										
									}
								</script>
								<wysLib:page></wysLib:page>
							<br/>
				<!-- 		<input class=textbg6 style="height:35px;" type="button"
										value="添加词汇" onClick="javascript:document.location.href='vocabulary_addInit.action';">
							<input class=textbg6 style="height:35px;" type="button"
										value="查看更多" onClick=""> -->	
						
							
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
