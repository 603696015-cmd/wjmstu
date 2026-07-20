<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
		
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--用户显示</TITLE>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<SCRIPT type="text/javascript">
		 var imgs = new Array();
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	} 
	</SCRIPT>
	</HEAD>
	<BODY>
	
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<BODY>
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>

    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<div style="margin-top: 0px; float: left;">
			<div style="margin-top: 0px;">
				<table id="info1" width="320" cellpadding="1" cellspacing="1">
					<caption>
						基本信息
					</caption>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>部门：</strong></td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								${elUser.department.name}
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>身份证：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.shenfenzheng" />
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>用户名：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.username" />
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>姓 名：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.realname" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE">
							<strong> 权限：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<s:property value="elUser.role.name" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>性别：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.sex" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						<strong>出生日期</strong><strong>：</strong></td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:date format="yyyy-MM-dd" name="elUser.shengri_" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						<strong>电子邮箱</strong><strong>：</strong></td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.email" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						<strong>电话号码</strong><strong>：</strong></td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.movephone" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>工作单位：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.danwei" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>单位地址：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.danweiaddress" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE">
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE">
							<label>
								<s:property value="elUser.xianzhiwei" />
							</label>
							<label>
								<!-- 
								<a href="sta_view.action?station.id=<s:property value="elUser.staid" />">查看岗位详情</a>
								 -->
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE">
							<strong> <wysLib:BasetName btid="1" />：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE">
							<label>
								<s:property value="elUser.jingzhong_" />
							</label>
							<label>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE">
							<strong> <wysLib:BasetName btid="2" />：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE">
							<label>
								<s:property value="elUser.zhiwu_" />
							</label>
							<label>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE">
							<strong> <wysLib:BasetName btid="3" />：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE">
							<label>
								<s:property value="elUser.zhiji_" />
							</label>
							<label>
							</label>
						</td>
					</tr><tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE">
							<strong> <wysLib:BasetName btid="5" />：</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE">
							<label>
								<s:property value="elUser.dishi_" />
							</label>
							<label>
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE">
							<strong>开通状态</strong><strong>：</strong></td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:property value="elUser.validName" />
							</label>
						</td>
					</tr>
					<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						<strong> 用户头像： </strong>
					</td>
					<td bgcolor="#F8FCFE">
					<s:if test="elUser.touxiang!= null">
									<img src="<s:property value="elUser.mainimg_"/>" width="240" height="300"> 
							</s:if><s:else> 
								<img
									src="<s:property  escape="false" value="elUser.mainimg_"/>"
									id="cimg_0" width="240" height="300" />
								<SCRIPT type="text/javascript">
									obj = document.getElementById("cimg_0");
									addImgs(obj);
								</SCRIPT> 
							</s:else>	
					</td>
					</tr>
				</table>
			</div>
			<script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
		</script>
		</div>
	
	</body>
</HTML>
