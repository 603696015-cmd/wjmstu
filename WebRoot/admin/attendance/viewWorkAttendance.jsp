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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
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
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function select(number){
				var id = "<s:property value='workAttendance.id'/>";
				width=700;
				height=500;
				var url = "selectRelateLeave.action?tablename=QXJGL&control=0&x="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
				if(rv!=undefined&&rv!=""){
					if(window.confirm("确定修改？")){
						document.getElementById("id").value = id;
						document.getElementById("value").value = rv;
						document.getElementById("type").value = number;
						ww.submit();
					}
				}
			}
		</script>
	</HEAD>
	<body>
		<form action="updateWorkAttendanceById.action" name="ww" method="post">
			<input type="hidden" name="id" id="id"/>
			<input type="hidden" name="value" id="value"/>
			<input type="hidden" name="type" id="type"/>
		</form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold;">我的考勤详情</span>
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

			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					考勤信息
				</caption>
				<tr>
					<th height="22" width="200" align="center">
						日期
					</th>
					<td align="center">
						<s:date name="workAttendance.riqi"
							format="yyyy年MM月dd日" />
					</td>
					<td align="center"></td>
				</tr>
				<tr>
					<th height="22" align="center">
						签到时间
					</th>
					<td align="center">
						<s:date name="workAttendance.signdaotime"
							format="yyyy年MM月dd日 HH时:mm分:ss秒" />
					</td>
					<td align="center"></td>
				</tr>
				<tr>

					<th height="22" align="center">
						签退时间
					</th>
					<td align="center">
						<s:date name="workAttendance.signtuitime"
							format="yyyy年MM月dd日 HH时:mm分:ss秒" />
					</td>
					<td align="center"></td>
				</tr>
				<tr>
					<th height="22" align="center">
						相关请假
					</th>
					<td align="center">
						<s:if test="workAttendance.relateleave != null ">
							<a href="viewContactTags.action?tablename=QXJGL&id=<s:property value='workAttendance.relateleave'/>" >请假单</a>
						</s:if>
					</td>
					<td align="center">
						<span style='color:red;cursor:hand' onclick="select(1);">点击选择相关请假条</span>
					</td>
				</tr>
				<tr>
					<th height="22" align="center">
						相关外出
					</th>
					<td align="center">
						<s:if test="workAttendance.relateout != null ">
							<a href="viewContactTags.action?tablename=WCGL&id=<s:property value='workAttendance.relateout'/>" >外出单</a>
						</s:if>
					</td>
					<td align="center">
						<span style='color:red;cursor:hand' onclick="select(2);">点击选择相关外出单</span>
					</td>
				</tr>
				<tr>
					<th height="22" align="center">
						相关补签
					</th>
					<td align="center">
						<s:if test="workAttendance.relateretroactive != null ">
							<a href="viewContactTags.action?tablename=BQGL&id=<s:property value='workAttendance.relateretroactive'/>" >补签单</a>
						</s:if>
					</td>
					<td align="center">
						<span style='color:red;cursor:hand' onclick="select(3);">点击选择相关补签单</span>
					</td>
				</tr>
				<tr>
					<th height="22" align="center" >
						结果
					</th>
					<td align="center" >
						<s:property value="workAttendance.result" />
					</td>
					<td align="center"></td>
				</tr>
				<tr>
					<th height="22" align="center" >
						备注
					</th>
					<td align="center" >
						<s:property value="workAttendance.mark" />
					</td>
					<td align="center"></td>
				</tr>
			</table>
		</div>
	</BODY>
</HTML>
