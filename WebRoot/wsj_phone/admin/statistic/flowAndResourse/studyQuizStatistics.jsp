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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				recordInfo.submit();
			}
			function initPN(){
				document.getElementById("pageNow").value=0;
				recordInfo.submit();
			}
			function searchUserInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRegister.action?x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv==undefined||rv==""){
				 	document.getElementById("danweiId").value=1;
				 	document.getElementById("danweiName").value="";
				 }
				 if(rv!=undefined&&rv!=""){
				 	//var bh=rv.split("_");
				 	var bh=rv.split("-=wys=-");
				 	document.getElementById("danweiId").value=bh[2];
				 	document.getElementById("danweiName").value=bh[1];
				 }
			}
			function delCeRecordInfo(){
				if(window.confirm("确认删除吗？")){
					recordInfo.action="delCeRecordInfo.action";
					recordInfo.submit();
				}
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="在线考试统计" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<table align="center" width="500">
		  <tr>
		    <th>当前在线考试数</th>
		    <th>今日在线考试数</th>
		    <th>昨日在线考试数</th>
		    <th>本周在线考试数</th>
		  </tr>
		  <tr align="center">
		  	<td><s:property value="statisticobj.currentCount" /></td>
		  	<td><s:property value="statisticobj.dayCount" /></td>
		  	<td><s:property value="statisticobj.YesterdayCount" /></td>
		  	<td><s:property value="statisticobj.weekCount" /></td>
		  </tr>
		</table>
		<div style="margin-top: 0px; text-align: center;">
			<form action="studyQuizStatistics.action" method="post" name="recordInfo">
				<s:hidden name="queryobj.elUser.department.id" id="danweiId" />
				<s:hidden name="queryobj.tableName" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div>
					用户名：<s:textfield name="queryobj.elUser.username" />&nbsp;&nbsp;&nbsp;
					姓名：<s:textfield name="queryobj.elUser.realname" />&nbsp;&nbsp;&nbsp;
					部门名称：<s:textfield id="danweiName" readonly="true" name="queryobj.elUser.department.name" />&nbsp;<a href="javascript:;" onClick="searchUserInit();return false;">选择</a><br />
					开始时间段：从<input size="16" type="text" onclick=setday(this) name="queryobj.myExamPaper.begintime" value="<s:date name="queryobj.myExamPaper.begintime" format="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">&nbsp;到&nbsp;
							   <input size="16" type="text" onclick=setday(this) name="queryobj.myExamPaper.endtime" value="<s:date name="queryobj.myExamPaper.endtime" format="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;
					<input onClick="initPN();" type="button" value="搜索" />
				</div>
			</form>
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th height="30" align="center" >
						用户名
					</th>
					<th height="30" align="center" >
						姓名
					</th>
					<th height="30" align="center" >
						部门
					</th>
					<th height="30" align="center" >
						考场名称
					</th>
					<th height="30" align="center" >
						开始时间
					</th>
					<th height="30" align="center" >
						结束时间
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="statisticobj.queryobjs">
					<tr>
						<td height="30" align="center">
							<s:property value="elUser.username" />
						</td>
						<td height="30" align="center" >
							<s:property value="elUser.realname" />
						</td>
						<td height="30" align="center" >
							<s:property value="elUser.department.name" />
						</td>
						<td height="30" align="center" >
							<s:property value="myExamPaper.examRoom.title" />
						</td>
						<td height="30" align="center" >
							<s:date name="myExamPaper.begintime" format="yy-MM-dd HH:mm:ss"/>
						</td>
						<td height="30" align="center" >
							<s:date name="myExamPaper.endtime" format="yy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</s:iterator></tbody>
		  </table>
		  <div><a href="javascript:delCeRecordInfo();">删除全部搜索结果</a></div>
			<wysLib:page></wysLib:page> 
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>