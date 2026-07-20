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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 11px;
}
</style>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="日志列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">业务日志列表</span>
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
			<s:form action="busilog_list.action" theme="simple" method="post" name="acc_list">
			<s:hidden name="pN" id = "pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>  
				账号：<s:textfield name="eluser.username" />
				姓名：<s:textfield name="eluser.realname" />
				模块：<select name="ellog.opmod">
					<option value="0">全部</option>
					<s:iterator value="ellog.mods" id="modsi">
						<option <s:if test="ellog.opmod==#modsi[0]">selected="selected"</s:if> value="<s:property value="#modsi[0]"/>">
							<s:property value="#modsi[1]"/>
						</option>
					</s:iterator>
				</select>
				时间段：<input type="text" onClick="setday(this)"
					name="ellog.querybtime" readonly="readonly"
					value="<s:date name="ellog.querybtime" format="yyyy-MM-dd HH:mm:ss" />" />~<input
					type="text"
					value="<s:date name="ellog.queryetime" format="yyyy-MM-dd HH:mm:ss" />"
					name="ellog.queryetime" onclick="setday(this)"  readonly="readonly" />
					<s:submit value="查询" cssClass="textbg4" onclick="document.getElementById('pageNow').value='0';"></s:submit>
			</s:form>
			<script type="text/javascript">
			 	function page(i){
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			 	function busilogInfo(id){
			 		 width=900;
				 height=600;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("busilogInfo.action?ellog.id="+id+"&x="+Math.random(),null,sFeature);
			 	}
			</script>
			<table width="100%" align="center" cellpadding="2" cellspacing="1">
				<tr>
					<th align="center" >
						编号
					</th>
					<th align="center" >
						姓名
					</th>
					<th align="center" >
						部门
					</th>
					<th align="center" >
						日期
					</th>
					<th align="center" >
						操作类型
					</th>
					<th align="center" >
						操作模块
					</th>
					<th align="center" >
						操作内容
					</th>
					<th align="center" >
						结果
					</th>
					<th align="center" >
						详情
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="elLogs" status="st">
					<tr>
						<td height="30" align="center" >
							<s:property value="id" />					  </td>
						<td align="center" >
							<s:property value="user.username" />
							(
							<s:property value="user.realname" />
							)
						</td>
						<td align="center" >
							<s:property value="user.department.name" />
						</td>
						<td align="center" >
							<s:date name="optime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center" >
							<s:property value="optypeStr" />
						</td>
						<td align="center" >
							<s:property value="opmodStr" />
						</td>
						<td align="center" >
							<s:property value="opcontent" />
						</td>
						<td align="center" >
							<s:property value="opresultStr" />
						</td>
						<td align="center" >
							<a class="textbg4" href="javascript:busilogInfo('<s:property value="id" />');">查看</a>
						</td>
					</tr>
				</s:iterator></tbody>
		  </table>
			<br> 
			<wysLib:page></wysLib:page>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
