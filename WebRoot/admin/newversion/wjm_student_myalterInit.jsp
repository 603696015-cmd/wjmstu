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
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>远程教育网络学院平台--管理端--用户编辑</TITLE>
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.error{color: red;}
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
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
		
		
		<table width="1044" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image: url( images/20140416/cent_bg3.png);background-repeat: no-repeat;background-position: center top;">
  <tr>
    <td height="550" valign="top" style="padding-top:90px;">
	<%-- <table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="编辑用户信息" /></div>
			</li>
			
			<li>
				<span style="font-weight: bold;">编辑用户信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_view.action?elUser.id=<s:property value="elUser.id"/>">显示学员信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_deleteInit.action?elUser.id=<s:property value="elUser.id"/>">删除学员</a>
			</li>
			<li class="sep">
			</li>
			 
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>--%>
		
	<s:form action="wjm_student_myalter"
			method="post" theme="simple" id="registerForm" >
			<s:hidden name="elUser.id" />
			<s:hidden name="myLogin.ipAddr" id="ipAddr" />
			<s:hidden name="elUser.department.id"  /><!-- 注册用户dep -->
			<s:hidden name="elUser.station.id"  /><!-- 注册用户dep -->
			<s:hidden name="elUser.role.id"  /><!-- 注册用户dep -->
			<s:hidden name="elUser.valid" />
			
			<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
	<div style="margin-top: 0px;">
				<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#CFDBE2" id="info1"
					>
					
					<tr>
					  <td width="120" height="40" colspan="2" align="center" background="images/bg002.jpg" >修改个人基本信息</td>
				  </tr>
					<tr>
					  <td height="40" colspan="2" align="center" background="images/bg002.jpg" bgcolor="#F8FCFE" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="50%"><table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#CFDBE2" id="info1"
					>
                            <tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><span class="neededitem">*</span><strong>学院：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><s:property value="elUser.department.name" />                              </td>
                            </tr>
                            <!-- 		<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span><strong>身份证：</strong>
						</td>
						<td height="30" align="left" >
							 <s:textfield name="elUser.shenfenzheng" size="20" id="shenfenzheng" />
						</td>
					</tr> -->
                            <tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><span class="neededitem">*</span><strong>学号：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:textfield name="elUser.username" id="username" readonly="true"/>
                                &nbsp;&nbsp; </label>                              </td>
                            </tr>
                            <tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong>中文名：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:textfield name="elUser.realname" id="realname" />
                                &nbsp;&nbsp; </label>                              </td>
                            </tr>
                            <tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><span class="neededitem">*</span><strong>英文名：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:textfield name="elUser.userno" id="userno" />
                                &nbsp;&nbsp; </label>                              </td>
                            </tr>
                            
                            <!-- 	<tr >
						<td width="120" height="30" align="right" >
							<strong> 电话号码：</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.movephone" id="movephone" />
							</label>
						</td>
					</tr> -->
                            <!-- 	<tr>
						<td width="120" height="30" align="right" >
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.xianzhiwei" id="xianzhiwei" />
							</label>
						</td>
					</tr> -->
                          </table></td>
                          <td><table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#CFDBE2" id="info1"
					>
                            <!-- 		<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span><strong>身份证：</strong>
						</td>
						<td height="30" align="left" >
							 <s:textfield name="elUser.shenfenzheng" size="20" id="shenfenzheng" />
						</td>
					</tr> -->
                            <tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong> 年级：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:textfield name="elUser.school" id="school" />
                                </label>                              </td>
                            </tr>
                            <!-- 	<tr >
						<td width="120" height="30" align="right" >
							<strong> 电话号码：</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.movephone" id="movephone" />
							</label>
						</td>
					</tr> -->
					<tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong>性别：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label> <input type="radio" name="elUser.sex" value="男"
									
                    
                                    <s:if test="elUser.sex==\"男\"">checked="checked"</s:if>
                                />
                                男 <input type="radio" name="elUser.sex" value="女"
									
                
                                <s:if test="elUser.sex==\"女\"">checked="checked"</s:if>
                                />
                                女 </label>                              </td>
                            </tr>
                            <tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong> 专业方向：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:textfield name="elUser.specialty" id="specialty" />
                                </label>                              </td>
                            </tr>
                            <tr>
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong> 国籍：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:textfield name="elUser.danwei" id="danwei" />
                                </label>                              </td>
                            </tr>
                            
                            <!-- 	<tr>
						<td width="120" height="30" align="right" >
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.xianzhiwei" id="xianzhiwei" />
							</label>
						</td>
					</tr> -->
                    <!-- <tr >
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong>
                                <wysLib:BasetName btid="1" />
                                ：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:select name="elUser.jingzhong" cssClass="g-select" list="jingzhongs"
										listKey="id" listValue="basevalue" />
                                </label>                              </td>
                            </tr>
                            <tr >
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong>
                                <wysLib:BasetName btid="2" />
                                ：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
                                </label>                              </td>
                            </tr>
                            <tr >
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong>
                                <wysLib:BasetName btid="3" />
                                ：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:select name="elUser.zhiji" cssClass="g-select"
										list="zhijis" listKey="id" listValue="basevalue" />
                                </label>                              </td>
                            </tr>
                            <tr >
                              <td width="120" height="40" align="right" bgcolor="#F8FCFE" ><strong>
                                <wysLib:BasetName btid="5" />
                                ：</strong> </td>
                              <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                                <s:select name="elUser.dishi" cssClass="g-select"
										list="dishis" listKey="id" listValue="basevalue" />
                                </label>                              </td>
                            </tr>-->
                            <tr style="display:none;">
                              <td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong> 用户头像 </strong> </td>
                              <td bgcolor="#F8FCFE"><s:if test="elUser.touxiang!= null"> <img src="<s:property value="elUser.mainimg_"/>" width="240" height="300"> </s:if>
                                  <s:else> <img
								src="<s:property  escape="false" value="elUser.mainimg_"/>"
                                    id="cimg_0" width="240" height="300" />
                            <SCRIPT type="text/javascript">
								obj = document.getElementById("cimg_0");
								addImgs(obj);
							    </SCRIPT>
                                  </s:else>                              </td>
                            </tr>
                            <tr style="display:none;">
                              <td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong> 修改头像 </strong> </td>
                              <td bgcolor="#F8FCFE"><s:textfield name="elUser.touxiang" id="mainimg" size="60" theme="simple" />
                                <a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a> </td>
                            </tr>
                            
                          </table></td>
                        </tr>
                      </table></td>
				  </tr>
					
		<!-- 		<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span><strong>身份证：</strong>
						</td>
						<td height="30" align="left" >
							 <s:textfield name="elUser.shenfenzheng" size="20" id="shenfenzheng" />
						</td>
					</tr> -->	
					
			<!-- 	<tr >
						<td width="120" height="30" align="right" >
							<strong> 电话号码：</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.movephone" id="movephone" />
							</label>
						</td>
					</tr> -->	
					
			<!-- 	<tr>
						<td width="120" height="30" align="right" >
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.xianzhiwei" id="xianzhiwei" />
							</label>
						</td>
					</tr> -->	
					
					<tr>
					  <td height="30" colspan="2" align="center" bgcolor="#F8FCFE" ><input type="submit" value="确认修改" style="width:80px" class="textbg4"></td>
				  </tr>
		  </table>
		</div>	</td>
  </tr>
</table>

			
			
	</s:form>
	
	</td>
  </tr>
</table>

		
		
	</BODY>
</HTML>