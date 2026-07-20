
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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
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
	<BODY>
	
	<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="密码修改" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">安全设置</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>-->
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align:left; width:320px;">
			<s:form action="student_mypwdalter" method="post" theme="simple" onsubmit="return check();">
				<s:property value="elmessage" />
				<table width="320" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" colspan="2" align="center" bgcolor="#F8FCFE" >
							<b>修改登陆密码</b>						</td>
					</tr>
					<tr>
						<td width="140" height="30" align="right" bgcolor="#F8FCFE" >
							<span class="neededitem">*</span>原密码：						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:textfield name="oldPwd" />
							</label>
					  </td>
					</tr>
					<tr>
						<td width="140" height="30" align="right" bgcolor="#F8FCFE" >
							<span class="neededitem">*</span>新密码：						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:textfield name="newPwd" id="newPwd"/>
							</label>
					  </td>
					</tr>
					<tr>
						<td width="140" height="30" align="right" bgcolor="#F8FCFE" >
							<span class="neededitem">*</span>新密码确认：						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:textfield name="newPwdConfirm" id="newPwdConfirm"/>
							</label>
					  </td>
					</tr>
					<tr>
						<td width="140" height="30" align="center" bgcolor="#F8FCFE" >						</td>
						<td bgcolor="#F8FCFE" >
						  &nbsp;&nbsp;<label>
								<s:submit value="提交修改" cssStyle="width:100px;" cssClass="textbg4"></s:submit>
							</label>
					  </td>
					</tr>
			  </table>

			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
