<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0063)http://reg.email.163.com/mailregAll/reg0.jsp -->
<HTML>
	<HEAD>
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		<TITLE>用户注册</TITLE>
		<META name=description content="">
		<LINK rel=stylesheet type=text/css href="images/reg/style_110531.css">
		<LINK rel=stylesheet type=text/css href="images/reg/patch120202.css">
		<!-- 
<LINK href="elfrontimages/style__.css" rel=stylesheet type="text/css">
 -->
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<STYLE type=text/css>
.chose-list .recommend A {
	ZOOM: 1;
	COLOR: #000;
	TEXT-DECORATION: none
}

.chose-list .recommend A:hover {
	TEXT-DECORATION: underline
}

.chose-list .recommend LABEL {
	CURSOR: default
}

.error {
	color: red;
}
</STYLE>
		<%-- 
<SCRIPT type=text/javascript src="images/reg/jquery-1.3.2.min.js"></SCRIPT>
<SCRIPT type=text/javascript src="images/reg/function.js"></SCRIPT>
<SCRIPT type=text/javascript src="images/reg/ntes.js"></SCRIPT>
<SCRIPT type=text/javascript src="images/reg/reg.js"></SCRIPT>
 --%>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>


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
			        //var date6 = idNumber.substring(6, 12);
			        //alert("nihao");
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
			    return this.optional(element) || /^[^u4E00-u9FA5\w]{2,5}$/.test(value);
			}, "输入错误，应输入2-5个中文");
			$.validator.addMethod("myMovephone", function(value, element) {
			    return this.optional(element) || /^[\d]{1,20}$/.test(value);
			}, "输入错误，只能输入数字且不能过长");
			 $(function(){ 
			 	 $("#registerForm").validate({ 
	                rules: {
	                    "elUser.password":{required: true,myPassword:true},
	                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
	                    "elUser.username":{ required: true},
	                    "elUser.realname":{required: true},
	                    //"elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
	                    "elUser.movephone":{ required: false,myMovephone:true}
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
		<script type="text/javascript">
			function searchUserInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRegister.action?isreg=1&x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("_");
					 document.getElementById("danwei").value=bh[0];
					 //document.getElementById("danweiName").value=bh[1];
					 document.getElementById("danweiName").innerHTML=bh[1];
				 }
			}
			function init(){
				var sfz=$("#shenfenzheng");
				if(sfz.val()!=""){
					var bool=isIdCardNo(sfz.val());
					if(bool==true){
						var date8 = "";
						if(sfz.val().length==18){
							date8 = sfz.val().substring(6, 14);
						}else{
							date8 ="19"+ sfz.val().substring(6, 12);
						}
						date8=date8.substring(0,4)+"-"+date8.substring(4,6)+"-"+date8.substring(6,8);
						$("#shengri").val(date8);
					}
				}
				//document.forms[0].submit();
			}
			function login(){
				form1.action="login.action";
				form1.submit();
			}
</script>
		<META name=GENERATOR content="MSHTML 8.00.6001.19088">
	</HEAD>
	<BODY onload="init();">
		<s:form action="register.action" method="post" theme="simple"
			id="registerForm" name="form1">
			<s:hidden name="elUser.danwei" id="danwei" />
			<s:hidden name="myLogin.ipAddr" id="ipAddr" />
			<DIV id=Rpage class=Rpage-main>
				<DIV id=Rbody>
					<%@include file="elfrontman/frontheader.jsp"%>
					<DIV class=title>
						<B class=crl></B><B class=crr></B><A class=ext href="javascript:login();">登
							录»</A>
						<H1>
							欢迎注册，注意：带
							<SPAN class=txt-impt>* </SPAN>的为必填项
						</H1>
					</DIV>
					<DIV class=content>
						<div style="text-align:center;color:red;">${elmessage}</div>
						<DIV class="g-collection collection-main">
							<DIV id=div7 class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN><SPAN class=txt-impt>*</SPAN>&nbsp;用户名&nbsp;
								</LABEL>
								<DIV id=div8 class=ipt-wraper>
									<s:textfield name="elUser.username" id="username"
										cssClass="g-ipt" />
									<span class="txt-info" id="usernameMsg">6~20个字符，区分大小写，不包含中文</span>
								</DIV>
							</DIV>
							<DIV id=passworditem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt>*</SPAN>&nbsp;密 码&nbsp;
								</LABEL>
								<DIV id=passwWrap class="ipt-wraper ">
									<input style="IME-MODE: disabled" type="password" class="g-ipt"
										name="elUser.password" id="password" />
									<span class="txt-info">6~20个字符，区分大小写，不包含中文</span>
								</DIV>
							</DIV>
							<DIV id=passwordconfitem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt>*</SPAN>&nbsp;确认密码&nbsp;
								</LABEL>
								<DIV id=passconfimWrap class=ipt-wraper>
									<input style="IME-MODE: disabled" type="password" class="g-ipt"
										name="elUser.confirmPassword" id="password1" />
									<span class="txt-info">请再次输入密码</span>
								</DIV>
							</DIV>
							<DIV id=mobileitem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN><SPAN class=txt-impt>*</SPAN>&nbsp;姓
									名&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<s:textfield cssClass="g-ipt" name="elUser.realname"
										id="realname" />
									<span class="txt-info">请填写中文名</span>
								</DIV>
							</DIV>
							<DIV id=mobileitem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN><SPAN class=txt-impt>*</SPAN>&nbsp;性
									别 &nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>

									<input type="radio" name="elUser.sex" value="男"
										checked="checked" />
									男
									<input type="radio" name="elUser.sex" value="女" />
									女
								</DIV>
							</DIV>
							<DIV id=mobileitem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN><SPAN class=txt-impt>*</SPAN>&nbsp;身份证号&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<s:textfield name="elUser.shenfenzheng" cssClass="g-ipt"
										id="shenfenzheng" />
									<span class="txt-info">18位或15位</span>
								</DIV>
							</DIV>
							<DIV id=mobileitem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN> &nbsp;出生日期&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<input type="text" name="elUser.shengri" readonly="readonly"
										class="g-ipt" id="shengri" />
									<span class="txt-info">由身份证号码自动生成</span>
								</DIV>
							</DIV>
							<DIV id=mobileitem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN> &nbsp;联系电话&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<input type="text" name="elUser.movephone"
										class="g-ipt" id="movephone" />
									<span class="txt-info"></span>
								</DIV>
							</DIV>
							<DIV class=g-collection-item>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt>*</SPAN>&nbsp;<wysLib:BasetName btid="1" />&nbsp;
								</LABEL>
								<DIV id=div class="ipt-wraper ">
									<s:select name="elUser.jingzhong" cssClass="g-select"
										list="jingzhongs" listKey="id" listValue="basevalue" />
								</DIV>
							</DIV>
							<DIV id=mobileitem class=g-collection-item>
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN> &nbsp;<wysLib:BasetName btid="2" />&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
								</DIV>
							</DIV>
							<DIV id="mobileitem" class="g-collection-item">
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN> &nbsp;<wysLib:BasetName btid="3" />&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
										listKey="id" listValue="basevalue" />
								</DIV>
							</DIV>
							<%-- 
							<DIV id="mobileitem" class="g-collection-item">
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN> &nbsp;<wysLib:BasetName btid="4" />&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" listValue="basevalue" />
								</DIV>
							</DIV>
							 --%>
							<DIV id="mobileitem" class="g-collection-item">
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN><SPAN class=txt-impt>*</SPAN>&nbsp;
									<wysLib:BasetName btid="5" />&nbsp;
								</LABEL>
								<DIV id="mobileWrap" class="ipt-wraper">
									<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
										listKey="id" listValue="basevalue" />
								</DIV>
							</DIV>
							<DIV id="mobileitem" class="g-collection-item">
								<DIV class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<LABEL class=g-collection-label>
									<SPAN class=txt-impt></SPAN><SPAN class=txt-impt>*</SPAN>&nbsp;单位名称&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
								<%-- 
									<s:textfield name="department.name" size="13" id="danweiName"
										cssClass="g-ipt" readonly="true" />
								 --%>
								 	<div id="danweiName" style="font-size:15px;"></div>
									<span class="txt-info" style="margin-left:20px;"><a href="#"
										onClick="searchUserInit();return false;">点此进行选择</a></span>
								</DIV>
							</DIV>
							<DIV id="usernotice" class="g-collection-item">
								<DIV style="HEIGHT: 100px" class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<DIV>
									<INPUT id=regBtn class=btn-reg title=立即注册 tabIndex=9 value=""
										type="submit">
									
								</DIV>
							</DIV>
						</DIV>
					</DIV>
					<DIV class=bottom>
						<B class=crl></B><B class=crr></B>
					</DIV>
				</DIV>
			</DIV>
			<s:hidden name="elUser.role.id" value="4" />
			<s:hidden name="elUser.department.id" value="1" />
		</s:form>
		<table style="margin-top: 5px;" width="960" height="50" border="0"
			align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td height="102" align="center" background="elfrontimages/botbg.png"
					style="line-height: 25px;">
					<p class="foot">
						中国食品安全培训网 copyright 2011-2015 all rights reserved
						<br />
						地址：北京市海淀区三里河路5号B座。服务电话：010-56219458
					</p>
				</td>
			</tr>
		</table>
	
	</body>
</HTML>