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
<HTML>
	<HEAD>
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		<TITLE>用户注册</TITLE>
		<META name=description content="">
		<LINK rel=stylesheet type=text/css href="images/reg/style_110531.css">
		<LINK rel=stylesheet type=text/css href="images/reg/patch120202.css">
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
		.STYLE1 {
	color: #0066CC;
	font-size: 16px;
	font-weight: bold;
}
        </STYLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript"> 
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
			<%
				String isAll=request.getAttribute("isAll").toString();
				if(isAll=="yes"){
					//都要验证
					%>
					 $(function(){ 
					 	 $("#registerForm").validate({ 
			                rules: {
			                    "elUser.password":{required: true,myPassword:true},
			                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
			                    "elUser.username":{ required: true,myName:true},
			                    "elUser.realname":{required: true},
			                    "elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
			                    "elUser.movephone":{ required: false,myMovephone:true},
			                    "depName":{required: true},
			                    "elUser.email":{ required: false,myEmail:true}
			                },
			                messages: {
			                    "elUser.password":{required:"请输入密码"},
			                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
			                    "elUser.username": {
			                            required: "请输入用户名" 
			                        },
			                    "elUser.realname":{required: "请输入姓名"},
			                    "elUser.shenfenzheng":{required: "请输入身份证号"},
			                    "depName":{required:  "请选择部门"}
			                }
			            }); 
					 })
					<%
				}else{
					%>
					 $(function(){ 
					 	 $("#registerForm").validate({ 
			                rules: {
			                    "elUser.password":{required: true,myPassword:true},
			                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
			                    "elUser.username":{ required: true,myName2:true},
			                    "elUser.realname":{required: true},
			                    //"elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
			                    "elUser.movephone":{ required: false,myMovephone:true},
			                    "elUser.shengri":{required: true},
			                    "depName":{required: true}
			                },
			                messages: {
			                    "elUser.password":{required:"请输入密码"},
			                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
			                    "elUser.username": {
			                            required: "请输入用户名" 
			                        },
			                    "elUser.realname":{required: "请输入姓名"},
			                    //"elUser.shenfenzheng":{required: "请输入身份证号"},
			                    "elUser.shengri":{required: "请填写出生日期"},
			                    "depName":{required:  "请选择部门"}
			                }
			            }); 
					 })
					<%
				}
			%>
		</script>
		<script type="text/javascript">
			function searchUserInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRegister.action?isreg=1&x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 //var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("danwei").value=bh[0];
					 //document.getElementById("danweiName").innerHTML=bh[1];
					 document.getElementById("danweiName").value=bh[1];
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
	<BODY onLoad="init();">
		<%@include file="elfrontman/frontheader.jsp"%>
			<s:form action="register.action" method="post" theme="simple"
			id="registerForm" name="form1">
			<s:hidden name="myLogin.ipAddr" id="ipAddr" />
			<input type="hidden" name="elUser.jingzhong" value="81"/>
			<input type="hidden" name="elUser.zhiwu" value="82"/>
			<input type="hidden" name="elUser.zhiji" value="83">
			<input type="hidden" name="elUser.dishi" value="91"/>
			<input type="hidden" name="elUser.luntanjibie" value="204"/>
			<input type="hidden" name="elUser.sex" value="男"/>
			<input type="hidden" name="elUser.realname" value="部门管理员"/>
			<input type="hidden" name="type" value="<s:property value='type'/>"/>
			<input type="hidden" name="isFromRegister" value="1" />
			<!--<s:hidden name="type" value="<s:property value='type'/>" />-->
			<DIV id=Rpage class=Rpage-main>
				<DIV id=Rbody>
					<DIV class=title>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-top:15px;padding-left:30px;color: #FF0000;font-size:16px;font-weight: bold;">永久免费　注册即用</td>
    <td width="200" style="padding-top:15px;"><a href="http://www.qianke.org/download/setup.zip"><span style="color: blue;font-size:16px;font-weight: bold;">不想注册　立即下载</span></a> </td>
  </tr>
</table>

						
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
									<SPAN class=txt-impt>*</SPAN> &nbsp;电子邮箱&nbsp;
								</LABEL>
								<DIV id=mobileWrap class=ipt-wraper>
									<input type="text" name="elUser.email"
										class="g-ipt" id="email" />
									<span class="txt-info"></span>
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
									<input type="text" name="department.name"
										class="g-ipt" id="department.name" />
								</DIV>
							</DIV>
							<DIV id="usernotice" class="g-collection-item">
								<DIV style="HEIGHT: 100px" class=disableMask>
									<!-- 白色遮罩 -->
								</DIV>
								<DIV>
									
										
										
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
   <td width="160" height="50" align="left"><INPUT id=regBtn class=btn-reg title=立即注册 tabIndex=9 value=""
										type="submit"></td>
    <td align="left" valign="bottom" style="padding-top:20px;"><a href="http://www.qianke.org/download/help.zip"><img src="images/reg/userhelp.jpg" width="104" height="38" border="0"></a> </td>
  </tr>
</table>
									
								</DIV>
							</DIV>
						</DIV>
					</DIV>
					<DIV class="bottom">
						<B class="crl"></B><B class="crr"></B>
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
					  
<script language="javascript" type="text/javascript" src="http://js.users.51.la/15262831.js"></script>
			    <noscript><a href="http://www.51.la/?15262831" target="_blank"><img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src="http://img.users.51.la/15262831.asp" style="border:none" /></a></noscript>
				  </p>				</td>
			</tr>
		</table>
	<!--<script charset="utf-8" type="text/javascript" src="http://static.b.qq.com/account/bizqq/js/wpa.js?wty=1&type=10&kfuin=800031614&ws=http%3A%2F%2Fwww.sopia.cc&title=%E5%9C%A8%E7%BA%BF%E5%AE%A2%E6%9C%8D&btn1=%E4%BC%81%E4%B8%9AQQ%E4%BA%A4%E8%B0%88&fsty=0&fposX=2&fposY=0&csty=1&tx=1&aty=0&a=&key=%09l%001%036%036QfU2V%60%07oR6%0A5%04%3E%03dTdW1P5%0Bh%0Aa%077Q%60"></script>-->
	</body>
</HTML>