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
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
		.error{
				color: red;
			}
		</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
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
				if(document.getElementById("password").value==""){
					alert("密码不要为空");
					document.getElementById("password").focus();
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
	                    "elUser.password":{required: true,myPassword:true},
	                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
	                    "elUser.username":{ required: true,myName:true},
	                    "elUser.realname":{required: true}
	                    //"elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
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
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<BODY onLoad="myload();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_searchInit.action">用户管理</a>

			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">添加用户</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="account_add.action" method="post"
			 id="registerForm">
			<s:hidden name="elUser.department.id" id="danwei" />
			<div style="margin-top: 0px;">
				<table id="info1" width="100%" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">
					<caption>
						基本信息
					</caption>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>单位/部门</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<%-- 
							<label>
								<select style="width: 300px;" name="elUser.department.id">
									<wysLib:dep_select selectid="${elUser.department.id}" />
								</select>
							</label>
							 --%>
							 <s:textfield theme="simple" name="elUser.department.name" size="20" id="danweiName" readonly="true" />
							 <a href="#" onClick="searchUserInit();return false;">点此进行选择</a>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>用户名</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<input type="text" name="elUser.username" id="username" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>密 码</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<input type="password" name="elUser.password" id="password" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>确认密码</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<input type="password" name="elUser.confirmPassword" id="password2" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE">
							<strong> 权限</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
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
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>序号</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>

								<input type="text" name="elUser.xuhao" id="xuhao" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>姓 名</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<input type="text" name="elUser.realname" id="realname" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>性别</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<input type="radio" name="elUser.sex" value="男"
									checked="checked" />
								男
								<input type="radio" name="elUser.sex" value="女" />
								女
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="5" /></strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:select theme="simple" name="elUser.dishi" cssClass="g-select" list="dishis"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<!--
					<tr>
						<td width="120" height="30" align="center" >
							<strong> 单位 </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<input type="text" name="elUser.danwei" id="danwei" />
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong> 身份证号 </strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<input type="text" name="elUser.shenfenzheng" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>出生日期</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
							<%-- 
								<input type="text" name="elUser.shengri" 
									readonly="readonly" onclick="setday(this)" id="shengri" />
							 --%>
							 <input type="text" name="elUser.shengri" 
									readonly="readonly" id="shengri" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong> 联系电话 </strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:textfield theme="simple" name="elUser.movephone" id="movephone" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="3" /> </strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:select theme="simple" name="elUser.zhiji" cssClass="g-select" list="zhijis"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="2" /> </strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:select theme="simple" name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="1" /> </strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:select theme="simple" name="elUser.jingzhong" cssClass="g-select"
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
								<s:select theme="simple" name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					 --%>
					<tr>
						<td align="center" bgcolor="#F8FCFE">
							<strong>开通状态</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<input type="radio" name="elUser.valid" value="true"
									checked="checked" />
								开通
								<input type="radio" name="elUser.valid" value="false" />
								关闭
							</label>
						</td>
					</tr>
				</table>

			</div>
			<input type="submit" value="确认添加">

		</form>
		<!-- 内容 -->
	</BODY>
</HTML>
