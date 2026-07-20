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
<head id="Head1">
    <title>企业网络大学注册 - 云学堂</title>
    <meta http-equiv="X-UA-Compatible" content="IE=7" />
    <script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.validate.js"></script>
	<script type="text/javascript" src="js/userCheck.js"></script>
	<script type="text/javascript" src="js/calendar.js"></script>
	<script type="text/javascript" src="js/stuffman.js"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/jquery/jquery.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/jquery/jquery.core.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/jquery/jquery.dialog.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/jquery/jquery.draggable.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/jquery/jquery.validate_new.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/global.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/function.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/tooltip.js?v=6054"></script>
	<script type="text/javascript" src="http://media.xxtown.com.cn/SystemFiles/Js/ui.core.js?v=6054"></script>
	<link href="http://media.xxtown.com.cn/SystemFiles/Themes/elearning20/global.css?v=6054" type="text/css" rel="stylesheet" />
	<link href="http://media.xxtown.com.cn/SystemFiles/Themes/elearning20/uc/ui.core.css?v=6054" type="text/css" rel="stylesheet" /> 
</head>
<body>
    <form name="form1" method="post" action="jg_register.action" id="form1">
		<input type="hidden" name="depid" value="1"/>
    <script type="text/javascript">
    	//下拉列表联动
			function change2(){
				document.getElementById("danyuan").style.display="inline";
			//	removeall();
			//	var wordid = document.getElementById("select1").value;
			//	$.post("mess_getVocabularyJson.action", {
			//			"vocabulary.wordid":wordid,
			//			"x":Math.random
			//			}, 
			//			function (data) {
			//				var dataObj=eval("("+data+")");
			//				var first = document.getElementById("select2");
			//				for(var i=0;i<dataObj.length;i++){
			//					first.options.add(new Option(dataObj[i].word.name,dataObj[i].word.id));
			//				}
			//			}); 
			}

        function loginIn(type) {
            if (type == "1") {
                document.getElementById('btnManageLoginIn').click();
            }
            else if (type == "2") {
                document.getElementById('btnUserLoginIn').click();
            }
            else if (type == "3") {
                document.getElementById('btnHrLoginIn').click();
            }
            else if (type == "4") {
                document.getElementById('btnKnowledgeManagerLoginIn').click();
            }
            showDealingMsg("登录中，请稍候...");
        }
        
        $(function() {
            $("#txtCurrentNumber").change();
        });

        function clearAllErrorText() {
            $("#spanEnterpriseNameError").html("");
            $("#divEnterpriseNameError").attr("class", "hiddenelement");
            //$("#hfEnterpriseName").val("");

            $("#spanDomainNameError").html("");
            $("#divDomainNameError").attr("class", "hiddenelement");
            //$("#hfDomainName").val("");
            
			$("#spanEnterPasswordError").html("");
            $("#divEnterPasswordError").attr("class", "hiddenelement");
            
            $("#spanCurrentNumberError").html("");
            $("#divCurrentNumberError").attr("class", "hiddenelement");

            $("#spanMobileError").html("");
            $("#divMobileError").attr("class", "hiddenelement");
            //$("#hfMobileNumber").val("");

            $("#spanMobileValidateCodeError").html("");
            $("#divMobileValidateCodeError").attr("class", "hiddenelement");

            $("#spanEmailError").html("");
            $("#divEmailError").attr("class", "hiddenelement");

            $("#spanEmailValidateCodeError").html("");
            $("#divEmailValidateCodeError").attr("class", "hiddenelement");
            //$("#hfValidateCode").val("");

            $("#spanAcceptProtocol").html("");
            $("#divAcceptProtocol").attr("class", "hiddenelement");
        }

        function clearErrorText(spanName, hfMsg) {
            $("#span" + spanName).html("");
            $("#div" + spanName).attr("class", "hiddenelement");
            if (spanName == "EnterpriseNameError") {
                $("#divEnterpriseNameAlt").attr("class", "floatleft");
            }
            if (hfMsg) {
                $("#" + hfMsg).val("");
            }
        }

        function setErrorText(spanName, msg, hfMsg) {
            $("#span" + spanName).html(msg);
            $("#div" + spanName).attr("class", "floatleft");
            if (spanName == "EnterpriseNameError") {
                $("#divEnterpriseNameAlt").attr("class", "hiddenelement");                
            }
            if (hfMsg) {
                $("#" + hfMsg).val(msg);
            }
        }

        ///*********************************************************************
        /// 切换手机或邮件注册
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="type">手机或邮件</param>
        ///*********************************************************************
        function changeRegisterType(type) {

            if (type == "eMail") {
                mobileSeconds = 0;
                $("#txtMobileNumber").val("");
                $("#txtValidateCode").val("输入短信验证码");
                $("#divMobileValidateCode").attr("class", "hiddenelement");
                $("#divEmail").attr("class", "editrow");
                $("#divMobile").attr("class", "hiddenelement");
                $("#hfValidateType").val("email");
                clearErrorText("MobileError", "hfMobileNumber")
                clearErrorText("MobileValidateCodeError", "hfValidateCode");
            }
            else {
                mailSeconds = 0;
                $("#txtEmail").val("");
                $("#txtEmailValidateCode").val("输入邮箱验证码");
                $("#divValidateCode").attr("class", "hiddenelement");
                $("#divEmail").attr("class", "hiddenelement");
                $("#divMobile").attr("class", "editrow");
                $("#hfValidateType").val("mobile");
                clearErrorText("EmailError", "hfEmail")
                clearErrorText("spanEmailValidateCodeError", "hfValidateCode")
            }
        }

        ///*********************************************************************
        /// 切换设置站点名称字体样式及检查站点名称是否存在
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="type">onfocus或onblur</param>
        ///*********************************************************************
        function changeSetFontStyle(type, obj) {
            var enterpriseName =$(obj).val();
            enterpriseName=enterpriseName.ReplaceAll(" ","");
            if (type == "onfocus") {
                if (enterpriseName == '例如：联想网络大学') {
                    $(obj).val("");
                    $(obj).css('color', '#666');
                }
            }
            else {
                if (enterpriseName == '') {
                    $(obj).val('例如：联想网络大学');
                    $(obj).css('color', '#999');
                    $("#divFontSet").attr("class", "hiddenelement");
                    $("#divVideo").attr("class", "uplaodarea");
                }
                else {
                    if (enterpriseName == '例如：联想网络大学') {
                        $("#divFontSet").attr("class", "hiddenelement");
                        $("#divVideo").attr("class", "uplaodarea");
                    }
                    else {
                        checkOrganizationName(enterpriseName,obj);
                        
                    }
                }
            }
        }

        ///*********************************************************************
        /// 检查网站名称是否已存在
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="organizationName">企业大学名称</param>
        ///*********************************************************************
        function checkOrganizationName(organizationName, obj) {
            var count = getStringWidth(organizationName);
            if (count <= 20&&count>1) {
                var methodName = 'ExistsByOrganizationName';
                var arr = '{"organizationName":"' + organizationName + '"}';
                if (organizationName == "") {
                    return;
                }
                AjaxAsyncCallMethod(methodName, arr,
                function(result) {
                    if (result == 1) {
                        setErrorText("EnterpriseNameError", "该企业网络大学名称已存在", "hfEnterpriseName");
                        $("#divFontSet").attr("class", "hiddenelement");
                        $("#divVideo").attr("class", "uplaodarea");
                    }
                    else {
                        clearErrorText("EnterpriseNameError", "hfEnterpriseName");
                        $("#hfEnterpriseName").val("");
                        $("#divFontSet").attr("class", "titleview");
                        $("#divVideo").attr("class", "hiddenelement");
                    }
                }, true, true, true);
            }
            else {
                setErrorText("EnterpriseNameError", "企业网络大学名称由1~10位中文或2~20位英文组成", "hfEnterpriseName");
                var str = organizationName.substr(0, 22);
                $("#txtEnterpriseName").val(str);
                return false;
            }
        }

        function checkDomainFormat(v) {
            var reg = /^[\d-\w]{2,14}$/;
            return reg.test(v);
        }

        ///*********************************************************************
        /// 检查网站域名是否已存在
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="organizationName">网站域名</param>
        ///*********************************************************************
            function checkDomainName(obj) {
                $("#hfDomainName").val("");
                var domainName = $(obj).val() + ".yunxuetang.cn";
                var methodName = 'ExistsByDomainName';
                var arr = '{"domainName":"' + domainName + '"}';
                if ($(obj).val() == "") {
                    return;
                }
                var isFlag = checkDomainFormat($(obj).val());
                if (!isFlag) {
                    setErrorText("DomainNameError", "输入的域名格式不正确", "hfDomainName");
                    return;
                }
                AjaxAsyncCallMethod(methodName, arr,
                function(result) {
                    if (result == 1) {
                        setErrorText("DomainNameError", "该网站“地址/域名”已存在", "hfDomainName");
                    }
                    else {
                        clearErrorText("DomainNameError", "hfDomainName");
                    }
                }, true, true, true);
            }

            var mailSeconds = 60; //倒计时时间60秒
            var mobileSeconds = 60; //倒计时时间60秒
        var intervalId;

        ///*********************************************************************
        /// 启动倒计时
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="organizationName">网站域名</param>
        ///*********************************************************************
        function runCountDown(obj, type,yzCodey) {
            var email = "";
            if (type == "email") {
                var txtEmail = $("#txtEmail");
                email = $("#txtEmail").val();
                if (email == "") {
                    setErrorText("EmailError", "请输入邮箱地址", "hfEmail");
                    return;
                }
                
                if (!checkFormat("email")) {
                    setErrorText("EmailError", "输入的邮箱格式不正确", "hfEmail");
                    return;
                }
                var emailErrorMsg = $("#hfEmail").val();
                if (emailErrorMsg != "") {
                    setErrorText("EmailError", emailErrorMsg, "hfEmail");
                    return;
                }
                mailSeconds = 60;
            }
            else {
                var txtEmail = $("#txtMobileNumber");
                email = $("#txtMobileNumber").val();
                if (email == "") {
                    setErrorText("MobileError", "请输入手机号码", "hfMobileNumber");
                    return;
                }

                if (!checkFormat("mobile")) {
                    setErrorText("MobileError", "输入的手机格式不正确", "hfMobileNumber");
                    return;
                }
                var emailErrorMsg = $("#hfMobileNumber").val();
                if (emailErrorMsg != "") {
                    setErrorText("MobileError", emailErrorMsg, "hfMobileNumber");
                    return;
                }
                var errorMsg = $("#hfMobileNumber").val();
                if (errorMsg != "") {
                    setErrorText("MobileError", errorMsg, "hfMobileNumber");
                    return;
                }
                mobileSeconds = 60
            }
            
            var methodName = "ExistsEmailOrMobile";
            var arr = '{"key":"' + email + '","type":"' + type + '"}';
            if (email == "" || type == "") {
                return;
            }
            //把以下ajax方法去掉就可以，只保留else部分
       //     AjaxAsyncCallMethod(methodName, arr,
       //         function(result) {
       //             if (result == 1) {
       //                 if (type == "email") {
      //                      setErrorText("EmailError", "此邮箱地址已被占用，请使用其它邮箱地址申请", "hfEmail");
       //                 }
       //                 else {
      //                      setErrorText("MobileError", "此手机号已被占用，请使用其它手机号申请", "hfMobileNumber");
      //                  }
     //               }
      //              else{
                        clearErrorText("EmailError", "hfEmail");
                        if (type == "email") {
                            $("#divValidateCode").attr("class", "");
                        }
                        else {
                            $("#divMobileValidateCode").attr("class", "");
                        }
                        $("#" + obj).attr("disabled", true); //设置按钮不可用
                        $("#" + obj).attr("class", "btnfreegetcheckcode"); //设置按钮不可用
                        intervalId = setInterval("CountDown('" + obj + "','" + type + "')", 1000); //调用倒计时的方法
                        sendMessage(email, type,yzCodey)
      //             }
      //          }, true, true, true);
        }

        ///*********************************************************************
        /// 倒计时函数
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="organizationName">网站域名</param>
        ///*********************************************************************
        function CountDown(obj, type) {
            //倒计时方法
            if (type == "email"&&mailSeconds <= 0) {
                $("#" + obj).val("通过此邮箱获取验证码"); //当时间<=0的时候改变按钮的value
                $("#" + obj).attr("disabled", false); //如果时间<=0的时候按钮可用
                $("#" + obj).attr("class", "btngetcheckcode"); //设置按钮不可用
                clearInterval(intervalId); //取消由 setInterval() 设置的 timeout  
                return;
            }
            else if (type == "mobile" && mobileSeconds <= 0) {
                $("#" + obj).val("免费获取短信验证码"); //当时间<=0的时候改变按钮的value
                $("#" + obj).attr("disabled", false); //如果时间<=0的时候按钮可用
                $("#" + obj).attr("class", "btngetcheckcode"); //设置按钮不可用
                clearInterval(intervalId); //取消由 setInterval() 设置的 timeout  
                return;
            }
            if (type == "email") {
                mailSeconds--;
                $("#" + obj).val(mailSeconds + "秒后可重新获取验证码");
            } else {
                mobileSeconds--;
                $("#" + obj).val(mobileSeconds + "秒后可重新获取验证码");
            }
            
           // $("#" + obj).val("通过此邮箱获取验证码(" + leftSeconds + ")");
            //obj.value = "通过此邮箱获取验证码&#13;&#10;(" + leftSeconds + ")";
        }

        ///*********************************************************************
        /// 发送验证码邮件
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="organizationName">邮件地址</param>
        ///*********************************************************************
        function sendMessage(email,type,yzCodey) {
            var code = ""; //验证码
            var codeLength = 6; //验证码长度
            //产生验证码
            for (var i = 0; i < codeLength; i++) {
                code += parseInt(Math.random() * 9).toString();
            }
      //      var methodName = 'SendValidateCodeToEmail';
            var arr = '{"code":"' + code + '","email":"' + email + '","validateType":"' + type + '"}';
            if (code == "" || email == "") {
                return;
            }
            $.post("sendcode.action", {
						"txtEmail":email,
						"yzCodey":yzCodey,
						"x":Math.random
						}, 
						function (data) {
						//	var dataObj=eval("("+data+")");
						//	var first = document.getElementById("select2");
						//	for(var i=0;i<dataObj.length;i++){
						//		first.options.add(new Option(dataObj[i].word.name,dataObj[i].word.id));
						//	}
			});
        }

        ///*********************************************************************
        /// 验证邮件及手机格式
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        /// <param name="organizationName">类型</param>
        ///*********************************************************************
        function checkFormat(type) {
            if (type == "email") {
                if (!$("#txtEmail").val().match(/^[-\.\w_]{2,20}@[-\.\w_]{2,20}\.[\S]+$/)) {
                    return false;
                }
            }
            else {
                if (!$("#txtMobileNumber").val().match(/^1[3|4|5|8][0-9]\d{4,8}$/)) {
                    return false;
                }
            }
            return true;
        }

        ///*********************************************************************
        /// 验证录入
        /// 创建人：栾灿 
        /// 日期：2013-01-6
        ///*********************************************************************
        function checkInput() {
            clearAllErrorText();
            var ishasError = false;
            var enterpriseName = $("#txtEnterpriseName").val();
            if (enterpriseName == "例如：联想网络大学"
                    || enterpriseName == "") {
                setErrorText("EnterpriseNameError", "请输入企业网络大学名称", "hfEnterpriseName");
                location.hash = "a1";
                $("#txtEnterpriseName").focus();
                return false;
                //ishasError = true;
            }
            else {
                var count = getStringWidth(enterpriseName);
                if (count > 20) {
                    setErrorText("EnterpriseNameError", "企业网络大学名称由1~10位中文或2~20位英文组成", "hfEnterpriseName");
                    var str = enterpriseName.substr(0, 22);
                    $("#txtEnterpriseName").val(str);
                    location.hash = "a1";
                    $("#txtEnterpriseName").focus();
                    return false;
                }
                else {
                    clearErrorText("EnterpriseNameError", "hfEnterpriseName");
                }
                
                var enterpriseNameMsg = $("#hfEnterpriseName").val();
                if (enterpriseNameMsg != "") {
                    setErrorText("EnterpriseNameError", enterpriseNameMsg, "hfEnterpriseName");
                    $("#txtEnterpriseName").focus();
                    return false;
                }
            }
            var domainName = $("#depname").val();
            if (domainName == ""||domainName == null) {
                setErrorText("DomainNameError", "请输入企业网络大学网站的地址/域名", "hfDomainName");
                location.hash = "a2";
                $("#depname").focus();
                return false;
            }
            else {
                var domainNameMsg = $("#hfDomainName").val();
                if (domainNameMsg != "") {
                    setErrorText("DomainNameError", domainNameMsg, "hfDomainName");
                    $("#department.name").focus();
                    return false;
                }
            }
            var password = $("#password").val();
            if(password==null||password==""){
            	setErrorText("EnterPasswordError", "请输入密码", "hfEnterPassword");
            	location.hash = "a3";
            	$("#password").focus();
            	return false;
            }else{
            	var count = getStringWidth(password);
            	if(count<6){
            		setErrorText("EnterPasswordError", "密码长度不小于6位", "hfEnterPassword");
	            	location.hash = "a3";
	            	$("#password").focus();
	            	return false;
            	}
            }
          
//            var currentNumber = $("#txtCurrentNumber").val();
//            if (currentNumber == "0") {
//                setErrorText("CurrentNumberError", "请输入需要使用此平台的员工/职员人数"); 
//                $("#txtCurrentNumber").focus();
//                return false;
//            }

            var type = $("#hfValidateType").val(); //mobile
            var email = "";
            var validateCode ="";
            if (type == "email") {
                validateCode = $("#txtEmailValidateCode").val();
                email = $("#txtEmail").val();
                if (email == "") {
                    setErrorText("EmailError", "请输入邮箱地址", "hfEmail");
                    return false;
                }
                else if (validateCode == "输入邮箱验证码" || validateCode == "") {
                    setErrorText("EmailValidateCodeError", "请输入邮箱验证码", "hfValidateCode");
                    return false;
                }
            }
            else {
                email = $("#txtMobileNumber").val();
                validateCode = $("#txtValidateCode").val();
                if (email == "") {
                    setErrorText("MobileError", "请输入手机号码", "hfMobileNumber");
                    return false;
                }
                else if (validateCode == "输入短信验证码" || validateCode == "") {
                    setErrorText("MobileValidateCodeError", "请输入手机验证码", "hfValidateCode");
                    return false;
                }
            }

            var methodName = 'CheckInputValidateCode';
            var arr = '{"code":"' + validateCode + '","email":"' + email + '"}';
            if (validateCode == "输入短信验证码" || validateCode == "输入邮箱验证码") {
                return false;
            }
         //   window.document().getElementById("form1").submit();
            $("#btnSubmit").click();
            AjaxAsyncCallMethod(methodName, arr,
                    function (result) {
                        if (result == 1) {
                            if (type == "email") {
                                setErrorText("EmailValidateCodeError", "验证码错误，请输入正确的验证码或重新获取", "hfValidateCode");
                            }
                            else {
                                setErrorText("MobileValidateCodeError", "验证码错误，请输入正确的验证码或重新获取", "hfValidateCode");
                            }

                        }
                        else if (result == 2) {
                            if (type == "email") {
                                setErrorText("EmailValidateCodeError", "验证码已过期，请重新获取", "hfValidateCode");
                            }
                            else {
                                setErrorText("MobileValidateCodeError", "验证码已过期，请重新获取", "hfValidateCode");
                            }
                        }
                        else if (result == 3) {
                            var msg = "";
                            if (type == "email") {
                                msg = "邮件地址与验证码不匹配"
                                setErrorText("EmailValidateCodeError", msg, "hfValidateCode");
                            }
                            else {
                                msg = "手机号码与验证码不匹配";
                                setErrorText("MobileValidateCodeError", msg, "hfValidateCode");
                            }
                        }
                        else {
                            var isCheck = $("#chkAcceptProtocol").attr("checked");
                            if (isCheck == false) {
                                $("#spanAcceptProtocol").html("请勾选“我已阅读，并同意云学堂网站服务协议”选项");
                                $("#divAcceptProtocol").attr("class", "");
                                return false;
                            }
                            //showDealingMsg("正在创建网站，请稍候...");
                            disabledButton(1);
                            $("#btnSubmit").click();
                            return true;
                        }
                    }, true, true, true);
                    
      }

        ///*********************************************************************
        /// 检查邮箱及手机号码是否已注册过
        /// 创建人：栾灿 
        /// 日期：2013-01-7
        ///*********************************************************************
      function checkRegisterTypeIsExists(obj, type) {
          $("#hfEmail").val("");
          var methodName = "ExistsEmailOrMobile";
          var key = "";
          if (type == "email") {
              key = $("#txtEmail").val();
              if (key != "") {
                  if (!checkFormat("email")) {
                      setErrorText("EmailError", "输入的邮箱格式不正确", "hfEmail");
                      return;
                  }
              }
              else {
                  return;
              }
          }
          else {
              key = $("#txtMobileNumber").val();
              if (key != "") {
                  if (!checkFormat("mobile")) {
                      setErrorText("MobileError", "输入的手机格式不正确", "hfMobileNumber");
                      return;
                  }
                  else {
                      clearErrorText("MobileError", "hfMobileNumber");
                  }
              }
              else {
                  return;
              }
          }
          if (key == "") {
              return;
          }
          var arr = '{"key":"' + key + '","type":"' + type + '"}';
          if (key == "" || type == "") {
              return;
          }
          AjaxAsyncCallMethod(methodName, arr,
                function (result) {
                    if (result == 1) {
                        if (type == "email") {
                            setErrorText("EmailError", "此邮箱地址已被占用，请使用其它邮箱地址申请", "hfEmail");
                        }
                        else {
                            setErrorText("MobileError", "此手机号已被占用，请使用其它手机号申请", "hfMobileNumber");
                        }
                    }
                    else {
                        clearErrorText("EmailError", "hfEmail");
                        if (type == "email") {
                            $("#divValidateCode").attr("class", "");
                        }
                        else {
                            $("#divMobileValidateCode").attr("class", "");
                        }
                    }
                }, true, true, true);
      }

        ///*********************************************************************
        /// 选择企业大学类型:弹出模板简介
        /// 创建人：栾灿 
        /// 日期：2013-01-7
        ///*********************************************************************
        var isChangeStyle = "0";
        function openDescription(obj, type, hfdescription) {
            var description = $("#" + hfdescription).val(); 
            if (type == "onmousemove") {
                obj.className = "itemselect1 clearfix";
            }
            else {
                obj.className = "item clearfix"; 
            }
        }

        ///*********************************************************************
        /// 检查验证码是否正确
        /// 创建人：栾灿 
        /// 日期：2013-01-8
        ///*********************************************************************
        function checkValidateCode(obj, type) {
            clearErrorText("EmailValidateCodeError",  "hfValidateCode");
            var methodName = 'CheckInputValidateCode';
            var email = "";
            var validateCode = $(obj).val();
            if (type == "email") {
                email = $("#txtEmail").val();
            }
            else {
                email = $("#txtMobileNumber").val();
            }
            var arr = '{"code":"' + validateCode + '","email":"' + email + '"}';
            if (validateCode == "输入短信验证码" || validateCode == "输入邮箱验证码") {
                return false;
            }
            AjaxAsyncCallMethod(methodName, arr,
                function (result) {
                    if (result == 1) {
                        if (type == "email") {
                            setErrorText("EmailValidateCodeError", "验证码错误，请输入正确的验证码或重新获取", "hfValidateCode");
                        }
                        else {
                            setErrorText("MobileValidateCodeError", "验证码错误，请输入正确的验证码或重新获取", "hfValidateCode");
                        }

                    }
                    else if (result == 2) {
                        if (type == "email") {
                            setErrorText("EmailValidateCodeError", "验证码已过期，请重新获取", "hfValidateCode");
                        }
                        else {
                            setErrorText("MobileValidateCodeError", "验证码已过期，请重新获取", "hfValidateCode");
                        }
                    }
                    else if (result == 3) {
                        var msg = "";
                        if (type == "email") {
                            msg = "邮件地址与验证码不匹配"
                            setErrorText("EmailValidateCodeError", msg, "hfValidateCode");
                        }
                        else {
                            msg = "手机号码与验证码不匹配";
                            setErrorText("MobileValidateCodeError", msg, "hfValidateCode");
                        }
                    }
                }, true, true, true);
        }
            
            ///*********************************************************************
            /// 如果用户已经成功注册过,则显示再次创建功能
            /// 创建人：栾灿 
            /// 日期：2013-01-11
            ///*********************************************************************
            function IsRegisterd() {
                $("#divTryUselayout").attr("class", "tryuselayout");
                $("#divRegisterInfo").attr("class", "formedit hiddenelement");
                $("#divHead").attr("class", "hiddenelement");
                $("#aEnterproseDomainName").attr("href", $("#lblEnterproseDomainName").html());
                                
            }


            function hrefregister() {
                var registerurl = '/registerprotocol.htm';
                openURL(registerurl);
            }

            function strEscape(type, context) {
                if (type == "1") {
                    return encodeURI(context);
                }
                else {
                    return decodeURI(context);
                }
            }

            $(document).ready(function() {
                $("#hfIndustryTemplateID").val($("#hdIndustryTemplateID_0").val());
                $("#hfIndustryTemplateName").val($("#hdIndustryTemplateName_0").val());
                //alert($("#hfIndustryTemplateID").val() + "------------" + $("#hfIndustryTemplateName").val());
                
                var inageSelectedIndex = parseInt($("#hfInageSelectedIndex").val());
                var enterpriseName = $("#txtEnterpriseName").val();
                if (inageSelectedIndex != 0 && enterpriseName != "例如：联想网络大学" && enterpriseName != "") {
                    $("#divFontSet").attr("class", "titleview");
                    $("#divVideo").attr("class", "hiddenelement");
                    SelectImage(inageSelectedIndex);
                }

            });

            function justVisit() {
                var url = $("#lblEnterproseDomainName").html();
                location.href = url;
            }

            function clearErrorMsg() {
                if ($('#osTextBoxErrinfo')) {
                    $('#osTextBoxErrinfo').remove(); 
                }
            }

            function sleep(numberMillis) {
                var now = new Date();
                var exitTime = now.getTime() + numberMillis;
                while (true) {
                    now = new Date();
                    if (now.getTime() > exitTime) return;
                }
            }

            function redirectPage(url) {
                window.location = url;
            }
    </script>

    <script type="text/javascript">


        ///*********************************************************************
        /// 选择行业模板
        /// 创建人：栾灿 
        /// 日期：2013-01-18
        ///*********************************************************************

        function selectIndustryTemplate(sender, imageIndex) {  
            sender.onmouseover = null;
            sender.onmouseout = null;
            sender.style.cursor = 'text';
            if (oldObject != null) {
                oldObject.onmouseover = function() {
                    this.className = 'itemselect clearfix';
                    this.style.cursor = 'pointer';
                };
                oldObject.onmouseout = function() {
                this.className = 'item clearfix';
                };
            }

            //currentSelectedFontIndex = imageIndex;
            currentDescIndex = imageIndex; 

            $("#hfIndustryTemplateID").val($("#hdIndustryTemplateID_" + imageIndex).val());
            $("#hfIndustryTemplateName").val($("#hdIndustryTemplateName_" + imageIndex).val());

            isChangeStyle == "1";
            
            $("#divDescription").html("");
            var decValue = $("#hfDescription_" + currentDescIndex).val();
            $("#divDescription").html(strEscape(0, decValue));

            document.getElementById("divIndustryTemplatesHead").innerHTML = "";
            var titleValue = $("#hdIndustryTemplateName_" + currentDescIndex).val();

            //alert(document.getElementById("divIndustryTemplatesHead").innerHTML);
            document.getElementById("divIndustryTemplatesHead").innerHTML = titleValue+"：";
            //alert(document.getElementById("divIndustryTemplatesHead").innerHTML);

            $(".itemselect").attr("class", "item clearfix");
            $(sender).attr("class", "itemselect clearfix"); 
            
            oldObject = sender; 
        }

        var oldObject = null;
        function SetDivEvent() {
            oldObject = document.getElementById("item_0");
            oldObject.onmouseover = null;
            oldObject.onmouseout = null;
        }

        //JS页面加载事件
        $(document).ready(function() {
            var enterpriseName = $("#txtEnterpriseName").val();
            enterpriseName = enterpriseName.ReplaceAll(" ", "");
            if (enterpriseName != "例如：联想网络大学" && enterpriseName != "") {
                SetDivEvent();
                $("#image1").attr("src", GetImageURL(1));
                $("#image2").attr("src", GetImageURL(2));
                $("#image3").attr("src", GetImageURL(3));
                $("#image4").attr("src", GetImageURL(4));
            }
        });

        //获取图片地址
        //fontIndex：字体索引
        //textID：文本ID
        function GetImageURL(fontIndex, textID) {
            if (!textID) {
                textID = "txtEnterpriseName";
            }
            var text = encodeURIComponent($("#" + textID).val());
            if (text) {
                text.ReplaceAll(" ", "");
            }
            var url = "/Services/DrawImage.ashx?t=g&f=" + fontIndex + "&c=" + text;
            return url;
        }

        //生成选中的图片地址
        //fontIndex：字体索引
        //textID：文本ID
        function BuildSelectedImageURL(fontIndex, textID) {
            if (!textID) {
                textID = "txtEnterpriseName";
            }
            var text = encodeURIComponent($("#" + textID).val());
            if (text) {
                text.ReplaceAll(" ", "");
            }
            var url = "/Services/DrawImage.ashx?t=x&f=" + fontIndex + "&c=" + text;
            return url;
        }

        //生成图片
        function GeneralImages(textID) {
            var enterpriseName =$("#txtEnterpriseName").val();
            enterpriseName=enterpriseName.ReplaceAll(" ","");
            if (enterpriseName != "例如：联想网络大学" && enterpriseName != "") {
                $("#image1").attr("src", GetImageURL(1, textID));
                $("#image2").attr("src", GetImageURL(2, textID));
                $("#image3").attr("src", GetImageURL(3, textID));
                $("#image4").attr("src", GetImageURL(4, textID));
                SelectImage(1);
            }
        }

        //当前选中的图片地址
        var currentSelectedFontIndex = "";

        var currentDescIndex = "0";

        //选择图片
        function SelectImage(imageIndex) {
            currentSelectedFontIndex = imageIndex;
            $("#radfont" + imageIndex).attr("checked", true);

            $("#hfInageSelectedIndex").val(imageIndex);
            var imgUrl = BuildSelectedImageURL(imageIndex, "txtEnterpriseName");
            $("#imageSelected").attr("src", imgUrl);      
        }

        function checkEnter(e, type) {
            clearAllErrorText();
            if (e.keyCode == 13) {
                if (type == "EnterpriseName") {
                    clearErrorText("EnterpriseNameError", "hfEnterpriseName");
                    var enterpriseName = $("#txtEnterpriseName").val();
                    if (enterpriseName == "例如：联想网络大学"
                    || enterpriseName == "") {
                        setErrorText("EnterpriseNameError", "请输入企业网络大学名称", "hfEnterpriseName");
                    }
                    else {
                        checkOrganizationName(enterpriseName, $("#txtEnterpriseName"));
                    }
                    $("#txtEnterpriseName").blur();
                    $("#txtEnterpriseName").focus();
                    $("#txtEnterpriseName").select();
                    location.hash = "a1";
                    return false;
                }
                if (type == "DomainName") {
                    clearErrorText("DomainNameError", "hfDomainName");
                    var domainName = $("#department.name").val();
                    if (domainName == "") {
                        setErrorText("DomainNameError", "请输入企业网络大学网站的地址/域名", "hfDomainName");
                    }
                    else {
                        checkDomainName($("#department.name"));
                    }
                    $("#department.name").focus();
                    location.hash = "a2";
                    $("#department.name").select();
                    return false;
                }

                //                if (type == "CurrentNumber") {
                //                    clearErrorText("CurrentNumberError");
                //                    var currentNumber = $("#txtCurrentNumber").val();
                //                    if (currentNumber == "0" || currentNumber == "") {
                //                        setErrorText("CurrentNumberError",  "请输入需要使用此平台的员工/职员人数");
                //                    }
                //                    $("#txtCurrentNumber").focus();
                //                    $("#txtCurrentNumber").select();
                //                    return false;
                //                }

                if (type == "MobileNumber") {
                    //$('#divMobileError').attr("class", "hiddenelement");
                    var mobileNumber = $("#txtMobileNumber").val();
                    clearErrorText("MobileError", "hfMobileNumber");
                    if (mobileNumber == "") {
                        //$('#divMobileError').attr("class", "floatleft");
                        setErrorText("MobileError", "请输入手机号码", "hfMobileNumber");
                    }
                    else {
                        checkRegisterTypeIsExists($("#txtMobileNumber"), "mobile");
                    }
                    $("#txtMobileNumber").focus();
                    $("#txtMobileNumber").select();
                    return false;
                }
                if (type == "MobileValidateCode") {
                    var validateCode = $("#txtValidateCode").val();
                    clearErrorText("MobileValidateCodeError", "hfValidateCode");
                    if (validateCode == "输入短信验证码" || validateCode == "") {
                        setErrorText("MobileValidateCodeError", "请输入手机验证码", "hfValidateCode");
                    }
                    else {
                        checkValidateCode($("#txtValidateCode"), "mobile");
                    }
                    $("#txtValidateCode").focus();
                    $("#txtValidateCode").select();
                    return false;
                }

                if (type == "Email") {
                    var email = $("#txtEmail").val();
                    clearErrorText("EmailError", "hfEmail");
                    if (email == "") {
                        setErrorText("EmailError", "请输入邮箱地址", "hfEmail");
                    }
                    else {
                        checkRegisterTypeIsExists($("#txtEmail"), 'email');
                    }
                    $("#txtEmail").focus();
                    $("#txtEmail").select();
                    return false;
                }
                if (type == "EmailValidateCode") {
                    var validateCode = $("#txtEmailValidateCode").val();
                    clearErrorText("EmailValidateCodeError", "hfValidateCode");
                    if (validateCode == "输入邮箱验证码" || validateCode == "") {
                        setErrorText("EmailValidateCodeError", "请输入邮箱验证码", "hfValidateCode");
                    }
                    else {
                        checkValidateCode($("#txtEmailValidateCode"), "email");
                    }
                    $("#txtEmailValidateCode").focus();
                    $("#txtEmailValidateCode").select();
                    return false;
                }
            }
            else if (type == "EnterpriseName") {
                // alert(e.keyCode);
            if (e.keyCode == 8 || e.keyCode == 46) {
                    clearErrorText("EnterpriseNameError", "hfEnterpriseName");
                }
                if (e.keyCode == 8 || e.keyCode == 46 || e.keyCode == 37 || e.keyCode == 39) {
                    checkLength(21);
                    return true;
                }
                if (e.keyCode != 8 && !(e.shiftKey && e.keyCode == 37) && !(e.shiftKey && e.keyCode == 39) && !(e.ctrlKey && e.keyCode == 86)) {
                    return checkLength(19);
                }
            }
            return true;

        }

        function checkLength(maxLenght) {
            clearErrorText("EnterpriseNameError", "hfEnterpriseName");
            var enterpriseName = $("#txtEnterpriseName").val();
            var count = getStringWidth(enterpriseName);
            if (count > maxLenght) {
                setErrorText("EnterpriseNameError", "企业网络大学名称由1~10位中文或2~20位英文组成", "hfEnterpriseName");
//                var str = enterpriseName.substr(0, 20);
                //                $("#txtEnterpriseName").val(str);
                return false;
            }
            else {
                clearErrorText("EnterpriseNameError", "hfEnterpriseName");
                return true;
            }
        }

        function getStringWidth(v) {
            var len = 0;
            for (i = 0; i < v.length; i++) {
                if (v.charCodeAt(i) > 256) {
                    len += 2;
                }
                else {
                    len++;
                }
            }
            return len;
        }

        //设置按钮是否可用
        function disabledButton( isDisable) {
            if (isDisable == 1) {
                $("#btnDisable").attr("class", "btndisabledsave"); //设置按钮不可用
                $("#btnSave").attr("class", "hiddenelement"); //设置按钮可用
            }
            else {
                $("#btnSave").attr("class", "btnjustview"); //设置按钮可用
                $("#btnDisable").attr("class", "hiddenelement"); //设置按钮可用
            }
        }

//        function CheckYou(text) {
//            var regu = "^[a-zA-Z\u4e00-\u9fa5]+$";
//            var re = new RegExp(regu);
//            if (!sender.value.match(re)) {
//                return false;
//            }
//            else {
//               return true
//            }
//        }

//        function CheckYou(sender) {
//            var regu = "^[a-zA-Z\u4e00-\u9fa5]+$";
//            var re = new RegExp(regu);
//            if (!sender.value.match(re)) {
//                if (typeof(sender.t_value) != "undefined") {
//                    alert(sender.value);
//                    sender.value = sender.t_value;
//                }
//                else {
//                    sender.value = "";
//                }
//            }
//            else {
//                sender.t_value = sender.value;
//            }            
//        }
        function changeQQServiceStyle(sender) {
            $(sender).attr("class", "qqservicehover");
            $(sender).one("mouseout", function () {
                $(sender).attr("class", "qqservice");
            });
        }
    </script>

    <style type="text/css">
        .radio
        {
            margin-top: 15px;
            vertical-align: top;
        }
        body {background:url('http://media.xxtown.com.cn/SystemFiles/Themes/elearning20/images/areabackground/registerbg.jpg') repeat; font-family:微软雅黑;}
</style>
    <link rel="stylesheet" type="text/css" href="http://media.xxtown.com.cn/SystemFiles/Themes/elearning20/global.css" />
    <link rel="stylesheet" type="text/css" href="http://media.xxtown.com.cn/SystemFiles/Themes/elearning20/manage.css" />
    <script src="OnlineHelp/onlinehelp.js" type="text/javascript"></script>
    <script src="http://media.xxtown.com.cn/SystemFiles/Js/slide.js" type="text/javascript"></script>
    <div class="pageheader clearfix" style="background-image:none; position:static; height:auto; background-color:#FFFFFF;">
        <div class="serviceheader clearfix">
            <div class="floatleft">客服电话：<span class="tel">400-666-4898</span></div>
            <div class="floatright" style="margin-left:20px;">
                <div class="qqservice" onmouseover="changeQQServiceStyle(this);">
                    <div class="head">QQ客服</div>
                    <div class="body">
                        <div class="row"><a class="link4" href="http://wpa.qq.com/msgrd?v=3&uin=2355499209&site=qq&menu=yes" target="_blank"><img border="0" src="http://wpa.qq.com/pa?p=2:2355499209:51" alt="点击这里给我发消息" title="点击这里给我发消息"></img></a></div>
                        <div class="row"><a class="link4" href="http://wpa.qq.com/msgrd?v=3&uin=2355499208&site=qq&menu=yes" target="_blank"><img border="0" src="http://wpa.qq.com/pa?p=2:2355499208:51" alt="点击这里给我发消息" title="点击这里给我发消息"></img></a></div>
                        <div class="row"><a class="link4" href="http://wpa.qq.com/msgrd?v=3&uin=2355499207&site=qq&menu=yes" target="_blank"><img border="0" src="http://wpa.qq.com/pa?p=2:2355499207:51" alt="点击这里给我发消息" title="点击这里给我发消息"></img></a></div>
                        <div class="row"><a class="link4" href="http://wpa.qq.com/msgrd?v=3&uin=2355499202&site=qq&menu=yes" target="_blank"><img border="0" src="http://wpa.qq.com/pa?p=2:2355499202:51" alt="点击这里给我发消息" title="点击这里给我发消息"></img></a></div>
                    </div>
                </div>
            </div>
            <div class="floatright" style="margin-left:20px; margin-top:1px;"><a href="http://sns.yunxuetang.com" target="_blank" class="link4">产品社区</a></div>
            <div class="floatright" style="margin-left:20px; margin-top:1px;"><a href="http://weibo.com/xxtown" target="_blank" class="link4">官方微博</a></div>
            <div class="floatright" style="margin-left:20px; margin-top:1px;"><a href="http://www.yunxuetang.cn" target="_blank" class="link4">返回云学堂</a></div>
        </div>
        <div class="content" style="width:1000px; margin:0 auto; height:70px; display:block;">
            <div class="yxtlogoshort2 hand" onclick="javascript:location.href='http://www.yunxuetang.cn'">&nbsp;</div>
            <div class="yxtlogoword0">创建企业网络大学</div>
        </div>
        <div class="infotip clearfix" >
            <div class="leftarea">
                20秒钟内创建您免费的企业网络大学！ 赶快来试试吧！</div>
            <div class="rightarea" id="divHead" style="display:none;">
                已有<span class="number">&nbsp;<span id="spanEnterpriseCount">3984</span> &nbsp;</span>家企业创建企业网络大学</div>
        </div>
    </div>
    <div class="splidline">
    </div>
    <div class="main clearfix" style="padding:15px;" onmousemove="moveNumber(event);">
         <!-- 再次创建时 -->
    	<div class="tryuselayout hiddenelement" id="divTryUselayout">
    	    <div class="completebox">
                <div class="fontsize2">尊敬的试用客户：</div><br/>
                <div class="fontsize2" style="padding-left:50px;">您之前创建的企业网络大学为：<a href="" id="aEnterproseDomainName" class="fontweightbold" style="color:#336699;"><span id="lblEnterproseDomainName"></span></a>
            　　　　    <input type="button" ID="btnJustVisit" class="btnjustvisit hiddenelement" onclick="justVisit();" /></div><br/><br/>
                <div class="completesplit">&nbsp;</div>
                <div class="body">
                <div class="rowtool clearfix">
                    <div class="clearfix">
            	    <span class="fontsize2 fontweightbold">登录系统的用户名及密码:</span><br/><br/>
                    <div class="leftarea">
                	    <div class="content">
                    	    <span class="fontweightbold">系统管理员帐号：</span><span style="font-family:Arial">admin</span>　　　密码为：<label id="lblAdminPwd"></label><br/>
                            <span class="color4">此帐号用于初始化平台以及管理和维护平台的各类数据</span><br/>
                            <input type="button" class="btncomplete1" onclick="loginIn(1);" />
                        </div>
                    </div>
                    
                    <div class="rightarea">
                	    <div class="content">
                    	    <span class="fontweightbold">普通员工帐号：</span><span style="font-family:Arial">testuser1</span>　 　密码为：<label id="lblUserPwd"></label><br/>
                            <span class="color4">此帐号用于体验普通员工的学习之用</span><br/>
                            <input type="button" class="btncomplete2" onclick="loginIn(2);" />
                        </div>
                    </div>
                    <div class="leftarea" style="margin-top:10px;">
                	    <div class="content">
                    	    <span class="fontweightbold">部门主管帐号：</span><span style="font-family:Arial">deptmanager1</span>　 　密码为：<label id="lblDeptPwd"></label><br/>
                            <span class="color4">此帐号用于对部门下的员工进行组织学习指派/跟踪</span><br/>
                            <input type="button" class="btncomplete5" onclick="loginIn(4);" />
                        </div>
                    </div>
                                    
                    <div class="rightarea" style="margin-top:10px;">
                	    <div class="content">
                    	    <span class="fontweightbold">HR主管帐号：</span><span style="font-family:Arial">hruser1</span>　　　密码为：<label id="lblHRPwd"></label><br/>
                            <span class="color4">此账号用于管理及维护岗位能力模型、组织学习和跟踪、组织培训等数据</span><br/>
                            <input type="button" class="btncomplete6" onclick="loginIn(3);" />
                        </div>
                    </div>
                </div>
                </div>
                </div>
            </div>
        </div>
        <div class="formedit clearfix" id="divRegisterInfo">
            <div class="tryuselayout clearfix">
                <div class="editcontent clearfix">
                    <div class="edit_upload clearfix" >                        
                        <div class="uplaodarea" id="divVideo" style="float:right;">
                                <div class="demovedio" onclick="showPopupVideo(605, 430,encodeURI('企业网络大学注册.flv'));">
                                </div>
                        </div>
                        <div class="editarea">
                            <div class="editrow" id="divEnterpriseNam">
                                <div class="title">
                                    <a name="a1"></a>
                                    <span class="iconnum1">&nbsp;</span>为您的企业网络大学起一个好听、好记的名字吧：</div>
                                <div class="content">
                                    <div class="dvinputlong floatleft">
                                    	<input name="elUser.username" type="text" value="例如：联想网络大学" id="txtEnterpriseName" class="inputlong" onfocus="changeSetFontStyle(&#39;onfocus&#39;,this);" onblur="changeSetFontStyle(&#39;onblur&#39;,this);GeneralImages(this.id);" onkeydown="return checkEnter(event,&#39;EnterpriseName&#39;);" style="color:#999;" />
                                    </div>
                                    <div class="floatleft" style="color:#555555; line-height:33px;" id="divEnterpriseNameAlt">
                                    	　<span  class="fontsize0 fontweightnormal" id="spanEnterpriseNameAlt">2-20位字符，可由中文或英文组成</span>
                                    </div>
                                    <div class="floatleft hiddenelement" id="divEnterpriseNameError">　　
                                    	<span class="iconerror001">&nbsp;</span><span style="color:#ff6600; font-size:14px; font-weight:bold;" id="spanEnterpriseNameError"></span>
                                    </div>
                                     <div class="clearstatic"></div>
                                     <div class="titleview hiddenelement" id="divFontSet"">
                                        <div class="left">
                                            <div class="head">请选择系统自动生成的网站Logo样式：</div>
                                            <div class="fontstyle hand" onclick="SelectImage(1);"><div class="radio"><input type="radio" id="radfont1" name="rad" /></div><label for="radfont1"><img src=""  id="image1" alt="" /></label></div>
                                            <div class="fontstyle hand" onclick="SelectImage(2);"><div class="radio"><input type="radio" id="radfont2" name="rad" /></div><label for="radfont2"><img src=""  id="image2" alt=""/></label></div>
                                            <div class="fontstyle hand" onclick="SelectImage(3);"><div class="radio"><input type="radio" id="radfont3" name="rad" /></div><label for="radfont3"><img src=""  id="image3" alt=""/></label></div>
                                             <div class="fontstyle hand" onclick="SelectImage(4);"><div class="radio"><input type="radio" id="radfont4" name="rad" /></div><label for="radfont4"><img src=""  id="image4" alt=""/></label></div> 
                                            <div class="color4" style="margin-left:5px; margin-top:5px;vertical-align:top;">注：在申请进入平台后，可以上传自己企业/机构的Logo</div>
                                        </div>
                                        <div class="middle" style="margin-left:50px;"></div>
                                        <div class="right" style="margin-left:20px;">
                                        	<div class="currentimage"><img id="imageSelected" style="border-style:none;" src="loginfont.png" alt="当前选中的字体样式"/></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="editrow">
                                <a name="a2"></a>
                                <div class="title">
                                    <span class="iconnum2">&nbsp;</span>为您的企业网络大学网站提供一个好记的地址/域名：</div>
                                <div class="content">
                                    <div class="clearfix">
                                        <div class="inputtitle"><span class="fontsize1" style="font-family:Arial Black">Http://</span>&nbsp;</div>
                                         <div class="dvinputshort"><input name="department.name" type="text" maxlength="12" id="depname" class="inputshort fontweightbold color4" onblur="checkDomainName(this);" onkeydown="return checkEnter(event,&#39;DomainName&#39;);" onkeyup="OsTextBox.codeKeyup(this);" onpaste="OsTextBox.codePaste(this);" ondrop="OsTextBox.codeDrop(this);" /></div>
                                            <div class="inputtitle fontsize1" style="font-family:Arial Black">.yunxuetang.cn</div>
                                            <div class="floatleft hiddenelement" id="divDomainNameError">　　<span class="iconerror001">&nbsp;</span><span style="color:#ff6600; font-size:14px; font-weight:bold;" id="spanDomainNameError"></span></div>
                                    </div>
                                    <div class="domaintip">
                                        两位以上英文字符或数字组合！建议是贵公司/机构的英文名称或简称等。例如 lenovo
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="clearstatic"></div>
                        <div class="editrow" id="divEnterpriseNam">
                                <div class="title">
                                    <a name="a1"></a>
                                    <span class="iconnum3">&nbsp;</span>请填写您的密码：</div>
                                <div class="content">
                                    <div class="dvinputlong floatleft">
                                    	<input name="elUser.password" type="password"  id="password" class="inputlong" onfocus="changeSetFontStyle(&#39;onfocus&#39;,this);"  onkeydown="return checkEnter(event,&#39;EnterPassword&#39;);" style="color:#999;" />
                                    </div>
                                    <div class="floatleft" style="color:#555555; line-height:33px;" id="divEnterpriseNameAlt">
                                    	　<span  class="fontsize0 fontweightnormal" id="spanEnterpriseNameAlt">不少于6个字符</span>
                                    </div>
                                    <div class="floatleft hiddenelement" id="divEnterPasswordError">　　
                                    	<span class="iconerror001">&nbsp;</span><span style="color:#ff6600; font-size:14px; font-weight:bold;" id="spanEnterPasswordError"></span>
                                    </div>
                                     <div class="clearstatic"></div>
                                     <div class="titleview hiddenelement" id="divFontSet"">
                                        <div class="left">
                                            <div class="head">请选择系统自动生成的网站Logo样式：</div>
                                            <div class="fontstyle hand" onclick="SelectImage(1);"><div class="radio"><input type="radio" id="radfont1" name="rad" /></div><label for="radfont1"><img src=""  id="image1" alt="" /></label></div>
                                            <div class="fontstyle hand" onclick="SelectImage(2);"><div class="radio"><input type="radio" id="radfont2" name="rad" /></div><label for="radfont2"><img src=""  id="image2" alt=""/></label></div>
                                            <div class="fontstyle hand" onclick="SelectImage(3);"><div class="radio"><input type="radio" id="radfont3" name="rad" /></div><label for="radfont3"><img src=""  id="image3" alt=""/></label></div>
                                             <div class="fontstyle hand" onclick="SelectImage(4);"><div class="radio"><input type="radio" id="radfont4" name="rad" /></div><label for="radfont4"><img src=""  id="image4" alt=""/></label></div> 
                                            <div class="color4" style="margin-left:5px; margin-top:5px;vertical-align:top;">注：在申请进入平台后，可以上传自己企业/机构的Logo</div>
                                        </div>
                                        <div class="middle" style="margin-left:50px;"></div>
                                        <div class="right" style="margin-left:20px;">
                                        	<div class="currentimage"><img id="imageSelected" style="border-style:none;" src="loginfont.png" alt="当前选中的字体样式"/></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="editrow" id="divEmail">
                                <div class="title">
                                    <span class="iconnum4">&nbsp;</span>为了方便您正式开通免费平台，并与您取得联系，请提供您的联系邮箱：
                                </div>
                                <div class="content" style="padding-bottom:15px;" >
                                    <div class="floatleft">
                                        <div class="inputtitle">　邮箱：</div>
                                        <div class="dvinputlong floatleft"><input name="txtEmail" type="text" maxlength="50" id="txtEmail" class="inputlong color4" onfocus="clearErrorText(&#39;EmailError&#39;,&#39;hfEmail&#39;);" onkeydown="return checkEnter(event,&#39;Email&#39;);" onblur="checkRegisterTypeIsExists(this,&#39;email&#39;);" /></div>
                                        <div class="floatleft">&nbsp;&nbsp;<input type="button" id="btnSendEmailValidateCode" class="btngetcheckcode" value="免费获取邮件验证码" onclick="runCountDown('btnSendEmailValidateCode','email',<s:property value="yzCodey"/>);return false;"/>
                                        </div>
                                        </div>
                                    <div class="floatleft">
                                        <div class="getcheckemailcodebg color4">
                                            点击按钮，我们将在60秒以内发送验证码到此邮箱，请查看您的邮箱。</div>
                                    </div>
                                    <div class="clearstatic"></div>
                                    <div class="floatleft hiddenelement" id="divEmailError">　　　　&nbsp;&nbsp;<span class="iconerror001">&nbsp;</span><span style="color:#ff6600; font-size:14px; font-weight:bold;" id="spanEmailError"></span></div>
                                    <div class="clearstatic"></div>
                                    <div class="floatleft">
                                        <div id="divValidateCode" >
                                        <div class="inputtitle">验证码：</div>
                                        <div class="dvinputshort"><input name="txtEmailValidateCode" type="text" value="输入邮箱验证码" maxlength="6" id="txtEmailValidateCode" class="inputshort" onfocus="if($(this).val() == &#39;输入邮箱验证码&#39;) {$(this).val(&#39;&#39;);} $(this).css(&#39;color&#39;,&#39;#333&#39;);" onkeydown="return checkEnter(event,&#39;EmailValidateCode&#39;);" onblur="if($(this).val() == &#39;&#39;) {$(this).val(&#39;输入邮箱验证码&#39;);$(this).css(&#39;color&#39;,&#39;#999&#39;);} else{checkValidateCode(this,&#39;email&#39;);}" style="color:#999;" /></div>
                                        <div class="floatleft hiddenelement" id="divEmailValidateCodeError">　　<span class="iconerror001">&nbsp;</span><span style="color:#ff6600; font-size:14px; font-weight:bold;" id="spanEmailValidateCodeError"></span></div>
                                        </div>
                                        <div class="clearstatic">
                                        </div>
                                    </div>
                                </div>
                                <div class="clearstatic">
                                </div>
                            </div>
                            <!--  新加 会员类型     -->   
                            <div class="editrow"  id="divMobile">
                                <div class="title">
                                    <span class="iconnum5">&nbsp;</span>会员类型：
                                </div>
                                <div class="content" style="padding-bottom:15px;">
                                    <div class="floatleft clearfix">
                                    	<select name="elUser.usertype" id="select1" onChange="change2();">
												<option  value=-1>--会员类型--</option>
												<option  value="1" >培训机构</option>
												<option  value="2" >讲师</option>
												<option  value="3" >学校</option>
												<option  value="4" >企业</option>
										</select>
                                    </div>
                                    
	                                   <div class="g-collection-item" id="danyuan" style="display:none">
											<label class="g-collection-label">
												<span class="txt-impt">*</span> &nbsp;<span><wysLib:BasetName btid="1" /></span>&nbsp;
											</label>
												<s:select name="elUser.jingzhong" cssClass="g-select"
													list="jingzhongs" listKey="id" listValue="basevalue" />
													
											<label class="g-collection-label">
												<span class="txt-impt">*</span> &nbsp;<span><wysLib:BasetName btid="5" /></span>&nbsp;
											</label>
												<s:select name="elUser.dishi" cssClass="g-select"
													list="dishis" listKey="id" listValue="basevalue" />
									</div>
                               </div>
                                <div class="clearstatic">
                                </div>
                            </div>
                         <!--       -->   
                         
                         <!--  新加 会员类型     -->   
                            <div class="editrow"  id="divMobile">
                                <div class="title">
                                    <span class="iconnum6">&nbsp;</span>论坛：
                                </div>
                                <div class="content" style="padding-bottom:15px;">
                                    <div class="floatleft clearfix">
                                    	<select name="elUser.luntanbankuai">
											<s:iterator value="fbtypes">
												<option value="<s:property value="id"/>">
													<s:property value="name" />
												</option>
											</s:iterator>
										</select>
                                    </div>
                                    <div>
                                    	<input type="text" name="elUser.bankuaimingcheng"/>
                                    </div>
                               </div>
                                <div class="clearstatic">
                                </div>
                            </div>
                         <!--       -->   
                            <div class="editrow">
                                    <div class="rowbottom" style ="line-height:31px;">
                                        <input name="chkAcceptProtocol" type="checkbox" id="chkAcceptProtocol" style=" margin-bottom:2px;" class="checkboxsplit" onclick="javascript:if(this.checked){ $(&#39;#spanAcceptProtocol&#39;).html(&#39;&#39;);$(&#39;#divAcceptProtocol&#39;).attr(&#39;class&#39;, &#39;hiddenelement&#39;);};" />
                                        <label for="chk" class="fontsize1">我已阅读，并同意<a href="javascript:;" onclick="hrefregister();"  class="fontsize1" style="color:#0055a0">云学堂网站服务协议</a></label>
                                        <span class ="hiddenelement" id="divAcceptProtocol">　　<span class="iconerror001">&nbsp;</span><span style="color:#ff6600; font-size:14px; font-weight:bold;" id="spanAcceptProtocol"></span></span>
                                    </div>
                                    <div class="rowbottom">
                                         <input id="btnSave" type="button" class="btnjustview" onclick="return checkInput();" />
                                         <input type="submit" name="btnSubmit" value="" id="btnSubmit" class="hiddenelement" />
                                         <input type="submit" name="btnDisable" value="" id="btnDisable" disabled="disabled" class="hiddenelement" />
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="plLoading">

</div>
        <input type="hidden" name="hfValidateType" id="hfValidateType" value="email" />
        <input type="hidden" name="hfImagePath" id="hfImagePath" />
        <input type="hidden" name="hfInageSelectedIndex" id="hfInageSelectedIndex" value="0" />
        <input type="hidden" name="hfIndustryTemplateID" id="hfIndustryTemplateID" />
        <input type="hidden" name="hfIndustryTemplateName" id="hfIndustryTemplateName" />
        <input type="hidden" name="hfEnterpriseName" id="hfEnterpriseName" />
        <input type="hidden" name="hfDomainName" id="hfDomainName" />
        <input type="hidden" name="hfEnterPassword" id="hfEnterPassword"/>
        <input type="hidden" name="hfEmail" id="hfEmail" />
        <input type="hidden" name="hfValidateCode" id="hfValidateCode" />
        <input type="hidden" name="hfMobileNumber" id="hfMobileNumber" />
         <input type="hidden" name="hfCurrentOrgID" id="hfCurrentOrgID" />
          <input type="submit" name="btnManageLoginIn" value="" id="btnManageLoginIn" style="display: none;" />
    <input type="submit" name="btnUserLoginIn" value="" id="btnUserLoginIn" style="display: none;" />
     <input type="submit" name="btnHrLoginIn" value="" id="btnHrLoginIn" style="display: none;" />
    <input type="submit" name="btnKnowledgeManagerLoginIn" value="" id="btnKnowledgeManagerLoginIn" style="display: none;" />
    </div>
    <script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F2d5499de1d4820ca892ef43aa97152b6' type='text/javascript'%3E%3C/script%3E"));
    </script>
    
<div>

	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWGgLVlv8mAtqYvgEC7IGTpQ4CrYvm1QwC2NHrgwcCrqnIkAkChPP9ugwC7Ozx5QgCuauh0w8Cwova3gMC4p/clQQCybGRwwwCjqyM2AgCt6Pugw4Cj833jQ8ChfuljQ8CyO2+kAQCi++CmAwCiKei1g4CoIe6iAoC3K6RygIC9br38QQCttnLwwQCiNnGgQoCsYvS4goCpcvrygc=" />
</div>
<script language='javascript' for='document' event='onkeydown'>
                                    if(event.srcElement.type=='text'&&event.keyCode==13)
                                    {
                                        event.keyCode=9;
                                    }
                                </script>
            </form>
</body>
</html>