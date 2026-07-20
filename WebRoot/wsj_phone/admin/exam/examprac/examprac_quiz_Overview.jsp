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
		<TITLE>练习管理</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} 
		</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习答卷概况" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="font-size:15px;text-align:center;margin-top:10px;">练习答卷概况</div>
		<TABLE cellSpacing=1 cellPadding=1 width="360" align=center bgColor=#ebebeb>
			<TBODY style="text-align:center;">
				<tr>
					<td width="150px" height="30px">
						分数段
					</td>
					<td>
						人次
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						0-9
					</td>
					<td>
						<s:property value="examprac.pass0_1"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						10-19
					</td>
					<td>
						<s:property value="examprac.pass1_2"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						20-29
					</td>
					<td>
						<s:property value="examprac.pass2_3"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						30-39
					</td>
					<td>
						<s:property value="examprac.pass3_4"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						40-49
					</td>
					<td>
						<s:property value="examprac.pass4_5"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						50-59
					</td>
					<td>
						<s:property value="examprac.pass5_6"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						60-69
					</td>
					<td>
						<s:property value="examprac.pass6_7"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						70-79
					</td>
					<td>
						<s:property value="examprac.pass7_8"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						80-89
					</td>
					<td>
						<s:property value="examprac.pass8_9"/>
					</td>
			    </tr>
			    <tr>
					<td width="150px" height="30px">
						90-100
					</td>
					<td>
						<s:property value="examprac.pass9_"/>
					</td>
			    </tr>
			</TBODY>
	
	</body>
</HTML>