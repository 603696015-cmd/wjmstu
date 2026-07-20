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
		<TITLE>中国食品安全培训网--管理端--用户编辑</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
			<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
			</style>
		<style type="text/css">
			.error{
				color: red;
			}
			/*
			.g-select {
				COLOR: #555;
				FONT-SIZE: 14px;
				VERTICAL-ALIGN: middle;
				width: 295px;
			}
			*/
		</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<SCRIPT type="text/javascript">
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
		function _onsubmit(){
				if(document.getElementById("username").value==""){
					alert("用户名不要为空");
					document.getElementById("username").focus();
					return false;
				}					
				if(document.getElementById("realname").value==""){
					alert("姓名不要为空");
					document.getElementById("realname").focus();
					return false;
				}
				/*if(document.getElementById("password").value==""){
					alert("密码不要为空");
					document.getElementById("password").focus();
					return false;
				}*/
				var  x = document.getElementsByName("elUser.sex");
				var z =  0;
				for(var i = 0 ;i <x.length;i++){
					if(x[i].checked ) 
						z = 1;
				}
				if(z == 0 ){
					alert("性别不要为空");
					return false;
				}
				return true;
			}
		</SCRIPT>
		<script type="text/javascript"> 	
			/*** 判断是否为“YYYYMM”式的时期 ***/
			function isDate6(sDate) {
			    if (!/^[0-9]{6}$/.test(sDate)) {
			        return false;
			    }
			    var year, month, day;
			    year = sDate.substring(0, 4);
			    month = sDate.substring(4, 6);
			    if (year < 1700 || year > 2500) return false;
			    if (month < 1 || month > 12) return false;
			    return true;
			}
			
			function isDate6_2(sDate) {
			    if (!/^[0-9]{6}$/.test(sDate)) {
			        return false;
			    }
			    var year, month, day;
			    year = "19"+sDate.substring(0, 2);
			    month = sDate.substring(2, 4);
			    day = sDate.substring(4,6);
			    if (year < 1700 || year > 2500) return false;
			    if (month < 1 || month > 12) return false;
			    if (day < 1 || day > 31) return false;
			    return true;
			}
			
			/*** 判断是否为“YYYYMMDD”式的时期 ***/
			function isDate8(sDate) {
			    if (!/^[0-9]{8}$/.test(sDate)) {
			        return false;
			    }
			    var year, month, day;
			    year = sDate.substring(0, 4);
			    month = sDate.substring(4, 6);
			    day = sDate.substring(6, 8);
			
			    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			    if (year < 1700 || year > 2500) return false;
			    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
			    if (month < 1 || month > 12) return false;
			    if (day < 1 || day > iaMonthDays[month - 1]) return false;
			    return true;
			}  
			/*** 身份证号码验证 ***/
			function isIdCardNo(num) {
			    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
			    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
			    var varArray = new Array();
			    var intVal;
			    var lngProd = 0;
			    var intCheckDigit;
			    var intStrLen = num.length;
			    var idNumber = num;
			    // initialize
			    if ((intStrLen != 15) && (intStrLen != 18)) {
			        return false;
			    }
			    // check and set val
			    for (i = 0; i < intStrLen; i++) {
			        varArray[i] = idNumber.charAt(i);
			        if ((varArray[i] < "0" || varArray[i] > "9") && (i != 17)) {
			            return false;
			        }
			        else if (i < 17) {
			            varArray[i] = varArray[i] * factorArr[i];
			        }
			    }
			
			    if (intStrLen == 18) {
			        //check date
			        var date8 = idNumber.substring(6, 14);
			        if (isDate8(date8) == false) {
			            return false;
			        }
			        // calculate the sum of the prod ts
			        for (i = 0; i < 17; i++) {
			            lngProd = lngProd + varArray[i];
			        }
			        // calculate the check digit
			        intCheckDigit = parityBit[lngProd % 11];
			        // check last digit
			        if (varArray[17] != intCheckDigit) {
			            return false;
			        }
			    }
			    else {        //length is 15
			        //check date
			        var date6 = idNumber.substring(6, 12);
			        if (isDate6_2(date6) == false) {
			            return false;
			        }
			    }
			    return true;
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
			$.validator.addMethod("myPassword", function(value, element) {
			    return this.optional(element) || /^\w{6,20}$/.test(value);
			}, "输入错误，应输入6-20个字符");
			$.validator.addMethod("myName", function(value, element) {
			    return this.optional(element) || /^\w{6,20}$/.test(value);
			}, "输入错误，应输入6-20个字符");
			$.validator.addMethod("myCHName", function(value, element) {
			    return this.optional(element) || /^[^u4E00-u9FA5]{2,5}$/.test(value);
			}, "输入错误，应输入2-5个中文");
			$.validator.addMethod("myMovephone", function(value, element) {
			    return this.optional(element) || /^[\d]{1,20}$/.test(value);
			}, "输入错误，只能输入数字且不能过长");
			 $(function(){ 
			 	 $("#registerForm").validate({ 
	                rules: {
	                    "elUser.password":{required: false,myPassword:true},
	                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
	                    "elUser.username":{ required: true},
	                    "elUser.realname":{required: true}
	                    //"elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"}
	                    //"elUser.movephone":{ required: false,myMovephone:true} 
	                },
	                messages: {
	                    "elUser.password":{required:"请输入密码"},
	                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
	                    "elUser.username": {
	                            required: "请输入用户名" 
	                        },
	                    "elUser.realname":{required: "请输入姓名"}
	                    //"elUser.shenfenzheng":{required: "请输入身份证号"} 
	                }
	            }); 
			 })
	</script>
	</HEAD>
	<BODY onload="myload('<s:property value="upOk"/>');">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
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
		</table>
		
		<s:form action="account_alter"
			method="post" theme="simple" id="registerForm" >
			<s:hidden name="elUser.department.id" id="danwei" />
			<div style="margin-top: 0px;">
				<table id="info1" width="60%" cellpadding="1" cellspacing="1"
					>
					<caption>
						基本信息
					</caption>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>单位/部门</strong>
						</td>
						<td height="30" align="left" >
							<%-- 
							<label>
								<select style="width: 300px;" name="elUser.department.id">
									<wysLib:dep_select selectid="${elUser.department.id}" />
								</select>
							</label>
							 --%>
							 <s:textfield name="elUser.department.name" size="20" id="danweiName" readonly="true" />
							 <a href="#" onClick="searchUserInit();return false;">点此进行选择</a>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>用户名</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<%-- 
								<s:textfield name="elUser.username" id="username" />&nbsp;&nbsp;<span style="color:red;">*</span>
								 --%>
								<s:property value="elUser.username"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>密 码</strong>
						</td>
						<td height="30" align="left" >
							<label>
							<%-- 	<s:textfield name="elUser.password" id="password" /> --%>
							<s:password name="elUser.password" value="" id="password" showPassword="true"/>&nbsp;&nbsp;<span style="color:red;">*</span>
							密码为空时，使用原密码。
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<strong> 权限</strong>
						</td>
						<td height="30" align="left" >
							<select name="elUser.role.id">
								<s:iterator value="roles">
									<option
										<s:if test="id==elUser.role.id">selected='selected'</s:if>
										value="<s:property value="id"/>">
										<s:property value="name" />
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>序号</strong>
						</td>
						<td height="30" align="left" >
							<label>

								<s:textfield name="elUser.xuhao" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>姓 名</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.realname" id="realname" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>性别</strong>
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
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="5" /></strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="120" height="30" align="center" >
							<strong> 单位 </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.danwei" />
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td width="120" height="30" align="center" >
							<strong> 身份证号 </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.shenfenzheng" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>出生日期</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<input type="text" name="elUser.shengri"
									value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
									 readonly="readonly" id="shengri" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> 联系电话 </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield name="elUser.movephone" id="movephone" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="3" /> </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="2" /> </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="1" /> </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<%-- 
								<select name="elUser.jingzhong">
									<s:iterator value="elUser.jingzhongs" status="jzs">
										<option
											<s:if test="elUser.jingzhong==elUser.jingzhongs[#jzs.index]">selected = 'selected'</s:if>
											value="<s:property />">
											<s:property />
										</option>
									</s:iterator>
								</select>
								 --%>
								<s:select name="elUser.jingzhong" cssClass="g-select"
										list="jingzhongs" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<%-- 
					<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="4" /> </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					 --%>
					<tr>
						<td>
							<strong>开通状态</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<input type="radio" name="elUser.valid" value="true"
									<s:if test="elUser.valid">checked="checked"</s:if> />
								开通
								<input type="radio" name="elUser.valid" value="false"
									<s:if test="!elUser.valid">checked="checked"</s:if> />
								关闭
							</label>
						</td>
					</tr>
				</table>

			</div>
			<s:hidden name="elUser.id"></s:hidden>
			<s:hidden name="Return" value="account_alterInit"></s:hidden>
			<input type="submit" value="确认修改">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" onclick="grantManage('<s:property value="elUser.id"/>','<s:property value="elUser.role.id"/>');" value="授权">
		</s:form>
		<!-- 内容 -->
	
	</body>
</HTML>
								