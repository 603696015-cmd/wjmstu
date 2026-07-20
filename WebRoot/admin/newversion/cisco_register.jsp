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
		<TITLE>北京市卫生局用户注册</TITLE>
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
		</STYLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript"> 
			function idcardnoIsExist(value){
				var v = false;
				$.ajax({
				  type: 'POST',
				  url: "checkIdcardnoIsExist.action",
				  data: {'elUser.shenfenzheng':value},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").jsonsBoolean ;
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
		    	return  this.optional(element) || idcardnoIsExist(value); //true
			}, "请正确输入唯一的身份证号码");
			$.validator.addMethod("myPassword", function(value, element) {
			    return this.optional(element) || /^\w{6,20}$/.test(value);
			}, "输入错误，应输入6-20个字符");
			$.validator.addMethod("myName", function(value, element) {
			  //  return this.optional(element) || /^\w{6,20}$/.test(value);
			 // }, "输入错误，应输入6-20个字符");
			    return this.optional(element) || /^1[3|4|5|7|8][0-9]\d{4,8}$/.test(value);
			   }, "不是完整的11位手机号或者正确的手机号前七位");
			$.validator.addMethod("toPhoneValue", function(value, element,param) {
				var date8 = value;
				$(param).val(date8); 
		    	return true; 
			},  "不是完整的11位手机号或者正确的手机号前七位"); 
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
	                    "elUser.username":{ required: true,myName:true,toPhoneValue:"#movephone"},
	                    "elUser.realname":{required: true},
	                    "elUser.shenfenzheng":{required: true,idcardno:true,idcardnoIsExist:true,toDateValue:"#shengri"},
	                    "elUser.movephone":{ required: false,myMovephone:true},
	                    "elUser.email":{ required: false,myEmail:true},
						"elUser.xianzhiwei":{required: true}
	                },
	                messages: {
	                    "elUser.password":{required:"请输入密码"},
	                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
	                    "elUser.username": {
	                            required: "请输入正确的手机号" 
	                        },
	                    "elUser.realname":{required: "请输入姓名"},
	                   	"elUser.shenfenzheng":{required: "请输入身份证号",idcardno:"非法身份证",idcardnoIsExist:"身份证已存在"},
						"elUser.xianzhiwei":{required: "请输入职务"}
	                }
	            }); 
			 })
			 
			 
			 
			 
			 //短信验证码
		function sendSMSCode(){
			var movephone = document.getElementById("movephone").value;
			var msg="";
			$.ajax({async:false,  //   
					type:"post",   
				    url:"sendSMSCode.action",   
				    data:{"elUser.movephone":movephone},   
					success:function(data){
								jd = eval("("+data+")");
								msg = jd.tishi;
						 }});
						 alert(msg);
				//setTimeout(window.close(),2000);
			}
			
		function checkCode(){
			var movephone = document.getElementById("movephone").value;
			var yzcode = document.getElementById("yzcode").value;
			var flag = "";
			$.ajax( {async:false,  //   
					type:"post",   
				    url:"checkSMSCode.action",   
				    data:{"elUser.movephone":movephone,"yzcode.yzCode":yzcode},   
					success:function(data){
								data = eval("("+data+")");
								flag = data.flag
						 }});
					if(flag !=1){
						alert("您输入的注册码有误！")					
					}
				//setTimeout(window.close(),2000);
		}
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
			    var usr=${"#username"};
			    if(usr.val()!=""){
					$("#movephone").val(usr);
				}
			    
			}
			function load(){
				if("${elmessage}"!=""){
					alert("${elmessage}");
				}
			}
			function login(){
				form1.action="cisco_user_center_login.action";
				form1.submit();
			}
</script>
<script type="text/javascript">
		
	</script>
		<META name=GENERATOR content="MSHTML 8.00.6001.19088">
	</HEAD>
	<BODY onLoad="init();load();">
		<%@include file="../../elfrontman/frontheader.jsp"%>
			<s:form action="cisco_register.action" method="post" theme="simple"
			id="registerForm" name="form1">
			<s:hidden name="myLogin.ipAddr" id="ipAddr" />
			<s:hidden name="elUser.role.id" value="4" />
			<s:hidden name="elUser.department.id" value="6269" /><!-- 注册用户dep -->
			<s:hidden name="elUser.station.id" value="16371" /><!-- 注册用户dep -->
			<!--<s:hidden name="elUser.xianzhiwei" value="职位" />现职位dep -->
			<DIV id=Rpage class=Rpage-main>
				<DIV id=Rbody>
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
						<div class="g-collection collection-main">
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
									<span class="txt-info" id="usernameMsg">请填写正确的手机号码</span>
								</DIV>
							</DIV>
							<div id="passworditem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>&nbsp;密 码&nbsp;
								</label>
							  <div id="passwWrap" class="ipt-wraper ">
									<input style="IME-MODE: disabled" type="password" class="g-ipt" name="elUser.password" id="password">
								  <span class="txt-info">6~20个字符，区分大小写</span>								</div>
							</div>
							<div id="passwordconfitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>&nbsp;确认密码&nbsp;
								</label>
								<div id="passconfimWrap" class="ipt-wraper">
									<input style="IME-MODE: disabled" type="password" class="g-ipt" name="elUser.confirmPassword" id="password1">
									<span class="txt-info">请再次输入密码</span>
								</div>
							</div>
							<div id="div7" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>&nbsp;身份证号&nbsp;
								</label>
								<div id="div8" class="ipt-wraper">
									<input type="text" name="elUser.shenfenzheng" value="" id="shenfenzheng" class="g-ipt">
									<span class="txt-info" id="shenfenzhengMsg">18位身份证号码</span>
								</div>
							</div>
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>&nbsp;姓
									名&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.realname" value="" id="realname" class="g-ipt">
									<span class="txt-info">请填写中文名</span>
								</div>
							</div>
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
								<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>&nbsp;手 机&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.movephone" class="g-ipt" id="movephone">
									<!--<span class="txt-info"><input type="button" value="获取短信激活码" style="width:120px"  class="textbg4" onClick="sendSMSCode()"/></span>-->
								请填写真实手机号码，以接收短信通知</div>
							</div>
						  <!-- 
						  <div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>&nbsp;短信激活码&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="yzcode.yzCode" value="" id="yzcode" onchange="checkCode()"  class="g-ipt"/>
								</div>
							</div>
							 -->
							
							
							<!--  <div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>&nbsp;用户名&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.username" value="" id="username" class="g-ipt">
									<span class="txt-info">请填写用户名</span>
								</div>
							</div>-->
							
							
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									性别 &nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">

									<input type="radio" name="elUser.sex" value="男" checked="checked">
									男
									<input type="radio" name="elUser.sex" value="女">
									女
								</div>
							</div>
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
																		<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									出生日期&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.shengri" readonly="readonly" class="g-ipt" id="shengri">
									
										<span class="txt-info">点击空格，由身份证号码自动生成</span>
									
								</div>
							</div>
							
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
								
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									电子邮箱&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.email" class="g-ipt" id="email">
									<span class="txt-info"></span>
								请填写常用邮箱，以接收相关通知</div>
							</div>
							
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									工作单位&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.danwei" value="" id="danwei" class="g-ipt">
									<span class="txt-info">请填写当前所在工作单位全称</span>
								</div>
							</div>
							
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									单位地址&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.danweiaddress" value="" id="danweiaddress" class="g-ipt">
									<span class="txt-info">请填写当前工作单位地址</span>
								</div>
							</div>
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span>职务&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<input type="text" name="elUser.xianzhiwei" value="" id="xianzhiwei" class="g-ipt">
									<span class="txt-info">请填写现职务</span>
								</div>
							</div>
							
							<div class="g-collection-item" style=" display:none;">
								<label class="g-collection-label">
									<span class="txt-impt">*</span> &nbsp;<span><wysLib:BasetName btid="1" /></span>&nbsp;
								</label>
								<div id="div" class="ipt-wraper ">
									<s:select name="elUser.jingzhong" cssClass="g-select"
										list="jingzhongs" listKey="id" listValue="basevalue" />

								</div>
							</div>
							<div id="mobileitem" class="g-collection-item" style=" display:none;">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span> &nbsp;<span><wysLib:BasetName btid="2" /></span>&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />

								</div>
							</div>
							<div id="mobileitem" class="g-collection-item" style=" display:none;">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span> &nbsp;<span><wysLib:BasetName btid="3" /></span>&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
										listKey="id" listValue="basevalue" />

								</div>
							</div>
							<div id="mobileitem" class="g-collection-item">
								<div class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<label class="g-collection-label">
									<span class="txt-impt">*</span> &nbsp;<span><wysLib:BasetName btid="5" /></span>&nbsp;
								</label>
								<div id="mobileWrap" class="ipt-wraper">
									<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
										listKey="id" listValue="basevalue" />

								</div>
							</div>
							
							<div id="usernotice" class="g-collection-item">
								<div style="HEIGHT: 100px" class="disableMask">
									<!-- 白色遮罩 -->
								</div>
								<div>
									<input id="regBtn" class="btn-reg" title="立即注册" tabindex="9" value="" type="submit">									
								</DIV>
							</DIV>
						</DIV>
					</DIV>
					<DIV class=bottom>
						<B class=crl></B><B class=crr></B>
					</DIV>
				</DIV>
			</DIV>
		</s:form>
		<s:include value="../../elfrontman/frontbottom.jsp" />
	</BODY>
</HTML>