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
		<TITLE>培训管理信息系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			function doSubmit(){
				var basevalue=document.getElementById("basevalue");
				basevalue.value = basevalue.value.replace(/^\s+|\s+$/g,"");//去除2头空格
				if(basevalue.value==""){
					alert("名称不能为空！");
					return false;
				}
				return true;
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程基础数据修改" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="course_alterBasedb.action" method="post" theme="simple" onsubmit="return doSubmit();">
			<s:hidden name="baseCourse.id" id="basedbId" />
			<s:hidden name="baseCourse.typeid" />
			<table width="60%" cellpadding="2" cellspacing="1" bgcolor="#ECEDEB">
				<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						类别
					</td>
					<td bgcolor="#FFFFFF">
						<s:property value="baseCourse.baseCourseType.name"/>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						名称
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="baseCourse.basevalue" id="basevalue" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						描述
					</td>
					<td bgcolor="#FFFFFF">
						<s:textarea name="baseCourse.remack" cols="60" rows="7"></s:textarea>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center" bgcolor="#FFFFFF">&nbsp;
						
					</td>
					<td bgcolor="#FFFFFF">
						<input class="textbg2" type="submit" value="确认修改">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	
	</body>
</HTML>
