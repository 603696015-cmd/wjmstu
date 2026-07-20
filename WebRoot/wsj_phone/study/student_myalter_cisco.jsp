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
		<TITLE>中国食品安全培训网--管理端--用户编辑</TITLE>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<style type="text/css">
			.error{color: red;}
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<script type="text/javascript">	
			function idcardnoIsExist(value){
				var v = false;
				$.ajax({
				  type: 'POST',
				  url: "checkIdcardnoIsExist.action",
				  data: {'elUser.shenfenzheng':value},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").jsonsBoolean;
			  		if( data != true){
			  			
			  			v = true;
			  		}
				  }
				});
				return v;
			}
			
			$.validator.addMethod("idcardno", function(value, element) {
		    	return this.optional(element) || isIdCardNo(value); 
			}, "请正确输入身份证号码"); 
			$.validator.addMethod("toDateValue", function(value, element,param) {
				var date8 = "";
				if(value.length==18){
					date8 = value.substring(6, 14);
				}else{
					date8 ="19"+ value.substring(6, 12);
				}
				date8=date8.substring(0,4)+"-"+date8.substring(4,6)+"-"+date8.substring(6,8);
				$(param).val(date8); 
		    	return true; 
			}, "请正确输入身份证号码"); 
			$.validator.addMethod("idcardnoIsExist", function(value, element) {
		    	return this.optional(element) || idcardnoIsExist(value); 
			}, "请正确输入唯一的身份证号码");
			$.validator.addMethod("myPassword", function(value, element) {
			    return this.optional(element) || /^\w{6,20}$/.test(value);
			}, "输入错误，应输入6-20个字符");
			$.validator.addMethod("myName", function(value, element) {
			    return this.optional(element) || /^\w{6,20}$/.test(value);
			}, "输入错误，应输入6-20个字符");
			$.validator.addMethod("myName2", function(value, element) {
			    return this.optional(element) || /^\w{4,20}$/.test(value);
			}, "输入错误，应输入4-20个字符");
			$.validator.addMethod("myCHName", function(value, element) {
			    return this.optional(element) || /^[^u4E00-u9FA5\w]{2,5}$/.test(value);
			}, "输入错误，应输入2-5个中文");
			$.validator.addMethod("myMovephone", function(value, element) {
			    return this.optional(element) || /^[\d]{1,20}$/.test(value);
			}, "输入错误，只能输入数字且不能过长");
			$.validator.addMethod("myEmail", function(value, element) {
			    return this.optional(element) || /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
			}, "电子邮箱格式错误");
			
			$(function(){ 
			 	 $("#registerForm").validate({ 
	                rules: {
	                    "elUser.password":{required: true,myPassword:true},
	                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
	                    "elUser.username":{ required: true,myName:true},
	                    "elUser.realname":{required: true},
	                    "elUser.shenfenzheng":{required: true,idcardno:true,idcardnoIsExist:true,toDateValue:"#shengri"},
	                    "elUser.movephone":{ required: false,myMovephone:true},
	                    "elUser.email":{ required: false,myEmail:true},
	                    "elUser.xianzhiwei":{required:true}
	                },
	                messages: {
	                    "elUser.password":{required:"请输入密码"},
	                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
	                    "elUser.username": {
	                            required: "请输入用户名" 
	                        },
	                    "elUser.realname":{required: "请输入姓名"},
	                    "elUser.shenfenzheng":{required: required: "请输入身份证号",idcardno:"非法身份证",idcardnoIsExist:"身份证已存在"},
	                    "elUser.xianzhiwei":{required: "请输入现职位"}
	                }
	            }); 
			 })
		</script>
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
			function myload(isOk){
				if("${elmessage}"!=""){
					alert( "${elmessage}!");
				}
				if(isOk==1){
					alert("修改成功!");
				}
			}
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
		</SCRIPT>
		<script type="text/javascript">
	</script>
	</HEAD>
	<BODY>
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    	
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="student_myalter"
			method="post" theme="simple" id="registerForm" >
			<s:hidden name="elUser.id" />
			<s:hidden name="myLogin.ipAddr" id="ipAddr" />
			<s:hidden name="elUser.department.id"  /><!-- 注册用户dep -->
			<s:hidden name="elUser.station.id"  /><!-- 注册用户dep -->
			<s:hidden name="elUser.role.id"  /><!-- 注册用户dep -->
			<s:hidden name="elUser.valid" />
			<div style="margin-top: 0px;">
				<!--<table id="info1" width="320" cellpadding="1" cellspacing="1"
					>
					<caption>
						基本信息
					</caption>
					<tr>
						<td width="50" height="30" align="right" >
							<span class="neededitem">*</span><strong>部门：</strong>
						</td>
						<td height="30" align="left" >
							<s:property value="elUser.department.name" />
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span><strong>身份证：</strong>
						</td>
						<td height="30" align="left" >
							 <s:textfield name="elUser.shenfenzheng" size="20" id="shenfenzheng" />
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span><strong>用户名：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.username" id="username" />&nbsp;&nbsp;
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<strong>姓名：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.realname" id="realname" />&nbsp;&nbsp;
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span><strong>密码：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.password" id="password" value=""/>&nbsp;&nbsp;
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span><strong>确认密码：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.confirmPassword" id="password1" value="" />&nbsp;&nbsp;
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<strong>性别：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<input type="radio" name="elUser.sex" value="男"
									<s:if test="elUser.sex==\"男\"">checked="checked"</s:if> />
								男
								<input type="radio" name="elUser.sex" value="女"
									<s:if test="elUser.sex==\"女\"">checked="checked"</s:if> />
								女
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span><strong>出生日期：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<input type="text" name="elUser.shengri"
									value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
									 readonly="readonly"  
										 id="shengri" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<strong> 电子邮箱：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.email" id="email" />
							</label>
						</td>
					</tr>
					<tr >
						<td height="30" align="right" >
							<strong> 电话号码：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.movephone" id="movephone" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<strong> 工作单位：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.danwei" id="danwei" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<strong> 单位地址：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.danweiaddress" id="danweiaddress" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:textfield name="elUser.xianzhiwei" id="xianzhiwei" />
							</label>
						</td>
					</tr>
					<tr >
						<td height="30" align="right" >
							<strong> <wysLib:BasetName btid="1" />：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:select name="elUser.jingzhong" cssClass="g-select" list="jingzhongs"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr >
						<td height="30" align="right" >
							<strong> <wysLib:BasetName btid="2" />：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr >
						<td height="30" align="right" >
							<strong> <wysLib:BasetName btid="3" />：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:select name="elUser.zhiji" cssClass="g-select"
										list="zhijis" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr >
						<td height="30" align="right" >
							<strong> <wysLib:BasetName btid="5" />：</strong>
						</td>
						<td height="30" align="left" >
						  <label>
								<s:select name="elUser.dishi" cssClass="g-select"
										list="dishis" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr >
					<td height="30" align="center" >
						<strong> 用户头像 </strong>
					</td>
					<td>
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
					<tr >
					<td height="30" align="center" >
						<strong> 修改头像 </strong>
					</td>
					<td>
		  <s:textfield name="elUser.touxiang" id="mainimg" size="60" theme="simple" />
								<a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a>
					</td>
					</tr>
				</table>-->
<table width="320" border="0" cellspacing="1" cellpadding="2">
<caption>基本信息
					</caption>
  <tr>
    <td bgcolor="#F8FCFE"><span class="neededitem">*</span><strong>部门：</strong></td>
    <td bgcolor="#F8FCFE"><s:property value="elUser.department.name" /></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><span class="neededitem">*</span><strong>身份证：</strong></td>
    <td bgcolor="#F8FCFE"><s:textfield name="elUser.shenfenzheng" size="20" id="shenfenzheng" /></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><span class="neededitem">*</span><strong>用户名：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:textfield name="elUser.username" id="username" />&nbsp;&nbsp;
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong>姓名：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:textfield name="elUser.realname" id="realname" />&nbsp;&nbsp;
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><span class="neededitem">*</span><strong>密码：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:textfield name="elUser.password" id="password" value=""/>&nbsp;&nbsp;
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><span class="neededitem">*</span><strong>确认密码：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:textfield name="elUser.confirmPassword" id="password1" value="" />&nbsp;&nbsp;
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong>性别：</strong></td>
    <td bgcolor="#F8FCFE"><label><input type="radio" name="elUser.sex" value="男"
				<s:if test="elUser.sex==\"男\"">checked="checked"</s:if> />
								男
				<input type="radio" name="elUser.sex" value="女"
				<s:if test="elUser.sex==\"女\"">checked="checked"</s:if> />
								女
				</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><span class="neededitem">*</span><strong>出生日期：</strong></td>
    <td bgcolor="#F8FCFE"><label><input type="text" name="elUser.shengri"
									value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
									 readonly="readonly"  
										 id="shengri" />
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong> 电话号码：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:textfield name="elUser.movephone" id="movephone" />
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong> 现职位：</strong></td>
    <td bgcolor="#F8FCFE"> <label><s:textfield name="elUser.xianzhiwei" id="xianzhiwei" />
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong> <wysLib:BasetName btid="1" />：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:select name="elUser.jingzhong" cssClass="g-select" list="jingzhongs"
										listKey="id" listValue="basevalue" />
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong> <wysLib:BasetName btid="2" />：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong> <wysLib:BasetName btid="3" />：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:select name="elUser.zhiji" cssClass="g-select"
										list="zhijis" listKey="id" listValue="basevalue" />
							</label></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><strong> <wysLib:BasetName btid="5" />：</strong></td>
    <td bgcolor="#F8FCFE"><label><s:select name="elUser.dishi" cssClass="g-select"
										list="dishis" listKey="id" listValue="basevalue" />
							</label></td>
  </tr>
</table>

		  </div>
			<input type="submit" value="确认修改" style="width:80px" class="textbg4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</s:form>
		<!-- 内容 -->
	
	</body>
</HTML>