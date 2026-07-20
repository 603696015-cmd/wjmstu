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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript">
			function doSubmit(){
				//document.getElementById("scheduleid").value=id;
				return true;
			}
			
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
				
				setCurTime("releasetime");
				setHMTime("hmtime");
			}
			
			function setid(i)
			{
				//alert(i);
			}
		</script>
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body onLoad="myload();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="基础数据修改" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="updateSchedule.action" method="post" theme="simple" onsubmit="return doSubmit();">
			<s:hidden name="schedule.id"   />
	
			<table width="700px" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="center" >
						日期
					</td>
					<td >
						<input class="Wdate" name="schedule.datetime" readonly="readonly"
							type="text" onClick="setday(this)" id="releasetime" value =<s:property value="schedule.datetime"/> />
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						时间
					</td>
					<td >
						<s:textfield name="schedule.timeout"  />
					</td>
				</tr>
				<tr>
					<td align="center" bgcolor="#FFFFFF">
						主题
					<br></td>
					<td bgcolor="#FFFFFF">
						<s:textfield name="schedule.topic"/>
					<br></td>
				</tr> 
				<tr>
					<td align="center" bgcolor="#FFFFFF">
						状态
					<br></td>
					<td bgcolor="#FFFFFF">&nbsp;
						
					<br></td>
				</tr>
				
				
						
				<tr>
					<td align="center" bgcolor="#FFFFFF">
						提醒时间
					</td>
					<td align="left" bgcolor="#FFFFFF">
						<s:textfield name="schedule.alertdate"/>
					</td>
				</tr>
				
				<tr>
					<td align="center" bgcolor="#FFFFFF">
						相关客户
					</td>
					<td align="left" bgcolor="#FFFFFF">
					</td>
				</tr>
				<tr>
					<td align="center" bgcolor="#FFFFFF">
						相关计划
					</td>
					<td align="left" bgcolor="#FFFFFF">
					</td>
				</tr>
			</table>
			<lable>内容 
			<div style="text-align: center; width: 100%">
				<s:textarea name="schedule.content" id="content" cols="60" rows="7"
					cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
			</div>
			<div>
						<input class="textbg6" type="submit" value="确认修改" >
			</div>
			<br>
		</s:form>
	</body>
</HTML>
