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
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doSubmit(op){
				if(op==2){
					document.myForm.action="eroomStatusUpdate.action";
				}
				document.myForm.submit();
			}
			function init(){
				var status="<s:property value="examRoom.valid" />";
				if(status==0||status==4){
					//$("#__multiselect_zjcheckbox").removeAttr("disabled");
					//alert($("input[id^='jzIds']").length);
					var jsIdsObj=$("input[id^='jzIds']");
					jsIdsObj.attr("disabled","");
				}
			}
		</script> 
	</HEAD>
	<body onload="init();">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场分配信息" /></div>
			</li>
		</ul>
		<form action="eroomAssignJingzhongProcess.action" method="post" name="myForm">
			<s:hidden name="examRoom.id" />
			<s:hidden name="examRoom.valid" value="1" />
			<TABLE cellSpacing=1 cellPadding=1 width="600" align=center bgColor=#ebebeb>
					<TBODY style="text-align:left;">
						<tr>
							<td style="padding-left:10px;" colspan="2" height="30px">
								考场名称：<s:property value="examRoom.title" />
							</td>
					    </tr>
					    <tr>
							<td style="padding-left:10px;" height="30px">
								开始时间：<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td style="padding-left:10px;">
								结束时间：<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
					    </tr>
					    <tr>
							<td style="padding:10px;" colspan="2">
								分配给工种：<br />
								<s:checkboxlist value="#request.checked" disabled="true" id="zjcheckbox" 
								  list="jingzhongs" name="jzIds" listKey="id" listValue="basevalue" theme="simple" />
							</td>
					    </tr>
					    <s:if test="examRoom.valid==0||examRoom.valid==4">
						    <tr>
								<td style="padding:10px;" colspan="2">
									<a href="javascript:doSubmit(2);" class="textbg6">确认分配</a>
									<a href="javascript:doSubmit(1);" class="textbg4">保存</a>
									<font color="#0033ff" style="width:160px;"><s:property value="#request.elmessage"/></font>
									<font color="#0033ff">注意：上面选项如有修改，确认分配前记得先保存</font>
								</td>
						    </tr>
					    </s:if>
				    </TBODY>
			</TABLE>
		</form>
	</body>
</HTML>