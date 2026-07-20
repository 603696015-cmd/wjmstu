<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训班审核</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		 
		<s:if test="elclasses.size==0">没有需要审核的培训班</s:if>
		<s:else>
		<table width="96%" align="center" cellpadding="2" cellspacing="1"
			>
			<tr>
				<th width="150" align="center" >
					培训班名称				</th>
				<th width="120" align="center" >
					证书名称				</th>
				<th width="150" align="center" >
					必修学分(课程数)				</th>
				<th width="120" align="center" >
					选修学分(课程数)				</th>
				<th width="120" align="center" >
					最少选修学分				</th>
				<th width="100" align="center" >
					申请人				</th>
				<th width="100" align="center" >
					开放状态				</th>
				<th align="center" >&nbsp;
					
				</th>
				<th align="center" >&nbsp;
					
				</th>
			</tr>
			<s:iterator value="elclasses">
				<tr>
					<td width="150" align="center" >
						<s:property value="name" />
				  </td>
					<td width="120" align="center" >
						<s:property value="certificatename" />
				  </td>
					<td width="150" align="center" >
						<s:property value="bxCredit"/>(<s:property value="bxCount"/>)
				  </td>
					<td width="120" align="center" >
					<s:property value="xxCredit"/>(	<s:property value="xxCount"/>)
				
				  </td>
					<td width="120" align="center" >
						<s:property value="optionalcredit" />
				  </td>
					<td width="100" align="center" >
						<s:property value="student.realname" />
				  </td>
					<td width="100" align="center" >
						<s:property value="statusName" />
				  </td>
				  <td align="center" >
					<a href="applyedClass_op.action?elclass.id=<s:property value="id" />&elclass.student.id=<s:property value="student.id"/>&status=3&elclass.name=&pN=0&pS=10">不通过</a>					</td>
					<td align="center" >
					<a href="applyedClass_op.action?elclass.id=<s:property value="id" />&elclass.student.id=<s:property value="student.id"/>&status=2&elclass.name=&pN=0&pS=10">通过</a>					</td>
				</tr>
			</s:iterator>
		</table>
		</s:else>
	
	</body>
</HTML>
