<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<title>修改密码</title>
		
		
		<script type="text/javascript">
			function check(){
				if(!$.trim($("#newPwd").val()).length > 0){
						alert("新密码不能为空!");
						$("#newPwd").focus();
						return false;
				}else if($("#newPwd").val() != $("#newPwdConfirm").val()){
					alert("新密码和新密码确认不一致请重新输入!");
					return false;
				}else{
					return true;
				}
			}
		</script>
		<style>
#ddd img {
	display: block;
}

.STYLE1 {
	font-size: 36px;
	font-weight: bold;
}
</style>

	</head>

	<body >
	<s:form action="student_mypwdalter" method="post" theme="simple" onsubmit="return check();">
				<s:property value="elmessage" />
<table width="700" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#CFDBE2" style="margin-top:30px;">
					<tr>
						<td height="40" colspan="2" align="center" background="images/bg002.jpg" bgcolor="#F8FCFE" >
							<b>修改登陆密码</b>						</td>
					</tr>
					<tr>
						<td width="140" height="40" align="right" bgcolor="#F8FCFE" >
							<span class="neededitem">*</span>原密码：						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:textfield name="oldPwd" />
							</label>
					  </td>
					</tr>
					<tr>
						<td width="140" height="40" align="right" bgcolor="#F8FCFE" >
							<span class="neededitem">*</span>新密码：						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:textfield name="newPwd" id="newPwd"/>
							</label>
					  </td>
					</tr>
					<tr>
						<td width="140" height="40" align="right" bgcolor="#F8FCFE" >
							<span class="neededitem">*</span>新密码确认：						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:textfield name="newPwdConfirm" id="newPwdConfirm"/>
							</label>
					  </td>
					</tr>
					<tr>
						<td width="140" height="40" align="center" bgcolor="#F8FCFE" >						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:submit value="提交修改" cssStyle="width:100px;" cssClass="textbg4"></s:submit>
							</label>
					  </td>
					</tr>
	  </table>

	<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>

	</s:form>

	</body>
</html>



