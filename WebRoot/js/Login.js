//根据参数，弹出层，显示登录或注册
//选项卡
function Select(c) {
    if (c == 1) {
        document.getElementById("login").style.display = "block";
        document.getElementById("regist").style.display = "none";
    } else if (c == 2) {
        document.getElementById("login").style.display = "none";
        document.getElementById("regist").style.display = "block";
    } else {
        document.getElementById("login").style.display = "block";
        document.getElementById("regist").style.display = "none";
    }
}
//删除层
function DeleteDiv() {
    $.unblockUI();
}
//c : 1 登录 ，2 注册 ， 3 立即投保(显示登录)
//弹出div
function ShowLogin(c) {
    var html = '';
    html += '<div id=\"newdiv\" style="width:498px;height:370px;">';
    html += '        <table id=\"login\" width=\"498\" height="370" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#f2f2f2\">';
    html += '            <tr>';
    html += '                <td height=\"26\" background=\"http:\/\/images.hzins.com\/web\/hz2011712_3.gif\">';
    html += '                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">';
    html += '                        <tr>';
    html += '                            <td style=\"font-size: 14px; font-weight: bold; padding-left: 18px; color: #585858;\" align=\"center\" colspan=\"2\">';
    html += '                                您尚未登录';
    html += '                                <div style=\"position:absolute;top:0px;right:0px;\">';
    html += '                                   <img src=\"http:\/\/images.hzins.com\/web\/hz2011712_7.gif\" onclick=\"DeleteDiv()\" align=\"absmiddle\" \/>';
    html += '                                </div>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                    <\/table>';
    html += '                <\/td>';
    html += '            <\/tr>';
    html += '            <tr>';
    html += '                <td bgcolor=\"#FFFFFF\" style=\"padding: 15px;\">';
    html += '                    <div style=\"border-bottom: 1px solid #EBEBEB; width: 432px; padding-left: 18px; height: 28px;';
    html += '                        margin-bottom: 20px;\">';
    html += '                        <div style=\"float: left; width: 85px; height: 28px; color: #CE1500; font-size: 14px;';
    html += '                            line-height: 28px; background-image: url(http:\/\/images.hzins.com\/web\/hz2011712_8.gif);';
    html += '                            text-align: center; margin-right: 10px; font-weight: bold;\" onmouseover=\"Select(1)\">';
    html += '                            登 录<\/div>';
    html += '                        <div style=\"float: left; width: 85px; height: 28px; color: #434343; font-size: 14px;';
    html += '                            line-height: 28px; background-image: url(http:\/\/images.hzins.com\/web\/hz2011712_4.gif);';
    html += '                            text-align: center; margin-right: 10px;\" onmouseover=\"Select(2)\">';
    html += '                            注 册<\/div>';
    html += '                    <\/div>';
    html += '                    <table width=\"468\" height="263" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">';
    html += '                        <tr>';
    html += '                            <td width=\"30%\" class=\"tx38\">用户名或Email：<\/td>';
    html += '                            <td align="left"><input name=\"logName\" type=\"text\" id=\"logName\" class=\"tx10\" tabindex=\"1\" onblur=\"IsRegName();\" \/><input name=\"HidClientDateTime\" type=\"hidden\" id=\"HidClientDateTime\" \/>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td class=\"tx38\">密码：<\/td>';
    html += '                            <td align="left"><input name=\"logPass\" type=\"password\" id=\"logPass\" maxlength=\"15\" onblur=\"IsRegPass();\" class=\"tx10\" tabindex=\"2\" \/>';
    html += '                                <a href=\"/member/ForgetPassword.aspx\" target=\"_blank\" class=\"more\">忘记密码？<\/a>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td class=\"tx38\">验证码：<\/td>';
    html += '                            <td align="left">';
    html += '                                <input name=\"txtValid\" type=\"text\" id=\"txtValid\" class=\"tx10\" ';
    html += '                                    maxlength=\"4\" size=\"4\" tabindex=\"3\" \/>';
    html += '                                <img id="imgcode" align="top" onclick="Gettocode()" alt="单击刷新验证码" height="25" src="/VerifyCode.aspx?m=0" align=\"absmiddle\" \/>';
    html += '                                <font color=\"#666666\">(请输入验证码)<\/font>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td class=\"tx38\"><input type=\"checkbox\" id=\"ChkRemember\" \/><\/td>';
    html += '                            <td align="left">';
    html += '                                记住帐号 <span class=\"gray\">（与他人共用电脑，建议不选中此项）<\/span>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr class=\"loginerror\" style=\"display: none\">';
    html += '                            <td class=\"tx38\"><\/td>';
    html += '                            <td align="left">';
    html += '                               <img src=\"http:\/\/images.hzins.com\/web\/ls_12.gif\" id=\"imgerror\" style=\"display: none\" \/>&nbsp;';
    html += '                               <span id=\"errormess\" class=\"red\"><\/span>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td class=\"tx38\">&nbsp;<\/td>';
    html += '                            <td height=\"50\" align="left">';
    html += '                                <input name=\"btnlogin\" type=\"submit\" id=\"btnlogin\" onclick=\"return login(' + c + ');\" value=\"登　录\"';
    html += '                                    class=\"tx14\" \/>&nbsp;&nbsp;';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td height=\"50\" colspan=\"2\" align=\"center\" class=\"gray\">';
    html += '                                有任何疑问请点击 <a href=\"\/help\/\" target=\"_blank\" class=\"gray\">帮助中心<\/a> 或 联系客服 400-678-8618';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                    <\/table>';
    html += '                <\/td>';
    html += '            <\/tr>';
    html += '        <\/table>';
    html += '        <table id=\"regist\" style=\"display: none;\" width=\"500\" height="370" border=\"0\" align=\"center\" cellpadding=\"0\"';
    html += '            cellspacing=\"1\" bgcolor=\"#f2f2f2\">';
    html += '            <tr>';
    html += '                <td height=\"26\" background=\"http:\/\/images.hzins.com\/web\/hz2011712_3.gif\">';
    html += '                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">';
    html += '                        <tr>';
    html += '                            <td style=\"font-size: 14px; font-weight: bold; padding-left: 18px; color: #585858;\" align=\"center\" colspan=\"2\">';
    html += '                                您尚未登录';
    html += '                                <div style=\"position:absolute;top:0px;right:0px;\">';
    html += '                                   <img src=\"http:\/\/images.hzins.com\/web\/hz2011712_7.gif\" onclick=\"DeleteDiv()\" align=\"absmiddle\" \/>';
    html += '                                </div>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                    <\/table>';
    html += '                <\/td>';
    html += '            <\/tr>';
    html += '            <tr>';
    html += '                <td bgcolor=\"#FFFFFF\" style=\"padding: 15px;\">';
    html += '                    <div style=\"border-bottom: 1px solid #EBEBEB; width: 432px; padding-left: 18px; height: 28px;';
    html += '                        margin-bottom: 20px;\">';
    html += '                        <div style=\"float: left; width: 85px; height: 28px; color: #434343; font-size: 14px;';
    html += '                            line-height: 28px; background-image: url(http:\/\/images.hzins.com\/web\/hz2011712_4.gif);';
    html += '                            text-align: center; margin-right: 10px;\" onmouseover=\"Select(1)\">';
    html += '                            登 录<\/div>';
    html += '                        <div style=\"float: left; width: 85px; height: 28px; color: #CE1500; font-size: 14px;';
    html += '                            line-height: 28px; background-image: url(http:\/\/images.hzins.com\/web\/hz2011712_8.gif);';
    html += '                            text-align: center; margin-right: 10px; font-weight: bold;\" onmouseover=\"Select(2)\">';
    html += '                            注 册<\/div>';
    html += '                    <\/div>';
    html += '                    <table width=\"468\" height="263" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">';
    html += '                        <tr>';
    html += '                            <td colspan=\"2\" class=\"tx13\">';
    html += '                                温馨提示：注册为会员后，您可以在购买时享受会员价、在我的保险箱中查询、下载管理您的保单。';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td width=\"30%\" class=\"tx38\" align=\"right\">您的Email地址：<\/td>';
    html += '                            <td width=\"64%\" align="left">';
    html += '                                <input class=\"tx10\" onblur=\"validEmail();\" id=\"newlogname\" name=\"newlogname\"  \/>&nbsp;&nbsp;<img';
    html += '                                    src=\"http:\/\/images.hzins.com\/web\/ls_12.gif\" id=\"img1\" style=\"display: none\" \/>&nbsp;<span';
    html += '                                        id=\"rehisterror1\" class=\"red\"><\/span>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td align=\"right\">&nbsp;<\/td>';
    html += '                            <td align="left"><span class=\"gray\">您可以使用Email登录<\/span><\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td class=\"tx38\" align=\"right\">设置密码：<\/td>';
    html += '                            <td align="left">';
    html += '                                <input name=\"newpassword\" onblur=\"validPass();\" id=\"newpassword\" maxlength=\"18\" type=\"password\"';
    html += '                                    class=\"tx10\" \/>&nbsp;&nbsp;<img src=\"http:\/\/images.hzins.com\/web\/ls_12.gif\" id=\"img2\"';
    html += '                                        style=\"display: none\" \/>&nbsp;<span id=\"rehisterror2\" class=\"red\"><\/span>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td class=\"tx38\" align=\"right\">再次输入密码：<\/td>';
    html += '                            <td align="left">';
    html += '                                <input name=\"newpassword1\" onblur=\"validRePass();\" id=\"newpassword1\" maxlength=\"18\"';
    html += '                                    type=\"password\" class=\"tx10\" \/>&nbsp;&nbsp;<img src=\"http:\/\/images.hzins.com\/web\/ls_12.gif\"';
    html += '                                        id=\"img3\" style=\"display: none\" \/>&nbsp;<span id=\"rehisterror3\" class=\"red\"><\/span>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr class=\"regisiterror\" style=\"display: none\">';
    html += '                            <td class=\"tx38\"><\/td>';
    html += '                            <td align="left">';
    html += '                               <img src=\"http:\/\/images.hzins.com\/web\/ls_12.gif\" id=\"regimgerror\" style=\"display: none\" \/>&nbsp;';
    html += '                               <span id=\"regerrormess\" class=\"red\"><\/span>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    
    html += '                        <tr>';
    html += '                            <td class=\"tx38\">验证码：<\/td>';
    html += '			                 <td>';
    html += '                            <div style=\"float:left\">';
    html += '                            <input name=\"txtRegisterValid\" id=\"txtRegisterValid\" type=\"text\" class=\"tx10\" size=\"8\"  onfocus=\"hideError()\" maxlength=\"4\" \/>';
    html += '                            <\/div>';
    html += '                            <div style=\"float:left\">';
    html += '                            <img id=\"imgRegister\" src="/RegisterCode.aspx?m=0" align=\"top\" onclick=\"GettocodeRegister()\" alt=\"单击刷新验证码\" height=\"25\" style=\"width: 60px\" \/>';
    html += '                            <\/div>';
    html += '                            <div style=\"float:left\">';
    html += '                            <a href=\"#\" onclick=\"GettocodeRegister()\"  >(请输入验证码)<\/a><br \/>';
    html += '                            <\/div>';
    html += '                            <\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                        <td colspan=\"2\">';
    html += '                            <img src=\"http:\/\/images.hzins.com\/web\/ls_12.gif\" runat=\"server\" id=\"imgRgError\" style=\"display: none\" \/>';
    html += '                            <span id=\"errorWord\" class=\"red\"><\/span>';
    html += '                        <\/td>';
    html += '                        <\/tr>'; 
    html += '                        <tr>';
    html += '                            <td class=\"tx38\">&nbsp;<\/td>';
    html += '                            <td align="left"><input type=\"button\" value=\"同意协议，提交注册！\" id=\"btnregedit\" class=\"tx20\" onclick=\'RegistFastByEmail(' + c + ');\' \/><\/td>';
    html += '                        <\/tr>';
    html += '                        <tr>';
    html += '                            <td class=\"tx38\">&nbsp;<\/td>';
    html += '                            <td align="left"><a href=\"\/Member\/term.html\" target=\"_blank\" class=\"more\">阅读《用户注册协议》<\/a><\/td>';
    html += '                        <\/tr>';
    html += '                    <\/table>';
    html += '                <\/td>';
    html += '            <\/tr>';
    html += '        <\/table>';
    html += '    <\/div>';

    jQuery.MessageBox(html, 450, 498);
    Gettocode();
    GettocodeRegister();
    G_IsLogin = undefined;
    G_V_Login = false;
    //判断
    Select(c);
    return false;
}

//刷新验证码
function Gettocode() {
    var u = document.getElementById("imgcode");
    u.src = "/VerifyCode.aspx?m=" + Math.random();
}

//刷新注册页面验证码
function GettocodeRegister(){
    var u = document.getElementById("imgRegister");
    u.src = "/RegisterCode.aspx?m=" + Math.random();
}
//登录
function login() {
    if ($('#ChkRemember').attr("checked")) {
        $.cookie('LoginAccect', $('#logName').val());
    }
    
    if (!IsRegName()) return false;
    if (!IsRegPass()) return false;
    if (!IsValidCode()) return false;
    $.ajax({
        url: "/Service/UserWebService.asmx/Login",
        data: "userName=" + $('#logName').val() + "&passWord=" + $('#logPass').val() + "&code=" + $('#txtValid').val(),
        success: function(result) {
            onLoginSucceed(result);
        }
    });
}
//登录  -- 投保优化
function login(c) {
    if ($('#ChkRemember').attr("checked")) {
        $.cookie('LoginAccect', $('#logName').val());
    }
    if (!IsRegName()) return false;
    if (!IsRegPass()) return false;
    if (!IsValidCode()) return false;
    $.ajax({
        type: "post",
        url: "/Service/UserWebService.asmx/Login",
        data: "userName=" + $('#logName').val() + "&passWord=" + $('#logPass').val() + "&code=" + $('#txtValid').val(),
        success: function(result) {
            onLoginSucceed(result, c);
        }
    });
}
//判断登录
function onLoginSucceed(result) {
    if (result != null) {
        if (result.text == '') {
            $.unblockUI();
            G_IsLogin = true;
        }
        else {
            showLoginError(result.text);
            G_IsLogin = false;
        }
    }
    else {
        alert("登录失败，程序报异常！");
        G_IsLogin = false;
    }
}
//判断登录 ---- 投保优化
function onLoginSucceed(result, c) {
    if (result != null) {
        //判断浏览器
        var isresult = "";
        if ($.browser.msie) {
            //IE
            isresult = result.text;
        }
        else if ($.browser.safari) {
            //苹果  、谷歌
            if (result.childNodes[0].lastChild == null) {
                isresult = result.text;
            } else {
                isresult = result.childNodes[0].lastChild.data;
            }
        }
        else if ($.browser.mozilla) {
            //Firefox 火狐
            isresult = result.childNodes[0].textContent;
        }
        else if ($.browser.opera) {
            // opera            
            isresult = result.text;
        }
        else {
            //默认为IE
            isresult = result.text;
        }

        if (isresult == '' || isresult == undefined) {
            //登录成功
            $.unblockUI();
            G_IsLogin = true;

            var id = $("#hidProdId").val();

            //判断是投保登录还是直接登录
            if (c == 1 || c == "1") {
                //objExp107.Calculate();
                var objE = 'objExp' + id;
                //去掉双引号
                objE = eval('(' + objE + ')');
                objE.Calculate(); //重新加载价格
                //刷新验证码
                Gettocode();
                //更新登录状态
                $('#TopUserInfo').html("您好：" + $('#logName').val() + "！ | <a href=\"/Logout.aspx\" title=\"退出登录\">退出登录</a>");

            } else if (c == 2 || c == "2") {
                //objExp107.Calculate();
                var objE = 'objExp' + id;
                //去掉双引号
                objE = eval('(' + objE + ')');
                objE.Calculate(); //重新加载价格
                //刷新验证码
                Gettocode();
                //更新登录状态
                $('#TopUserInfo').html("您好：" + $('#logName').val() + "！ | <a href=\"/Logout.aspx\" title=\"退出登录\">退出登录</a>");

            } else {//立即投保
                //立即投保，重调下单方法
                submitIns(c);
            }
        }
        else {
            //登录失败
            showLoginError(isresult);
            //刷新验证码
            Gettocode();
            G_IsLogin = false;
        }
    }
    else {
        alert("登录失败，程序报异常！");
        G_IsLogin = false;
    }
}
var beginreg = false;
var regname;
var regpass;
var txtValid = $('#txtValid');
//验证用户名
function IsRegName() {
    var $obj = $("#logName");
    if ($obj.val().Trim() == '') {
        showLoginError('用户名或者邮箱不能为空。'); return false;
    }
    else { resetLoginError(); return true; };
}
//验证密码
function IsRegPass() {
    var p = /[a-zA-Z0-9]{1}[a-zA-Z0-9\.\_]{5,15}/;
    var $obj = $("#logPass");
    if ($obj.val().Trim() == '') {
        showLoginError('登陆密码不能为空。'); return false;
    }
    else if (!p.test($obj.val().Trim())) {
        showLoginError('密码长度错误。最少6位，最多16位。'); return false;
    }
    else { resetLoginError(); return true; };
};
//验证验证码
function IsValidCode() {
    var $obj = $("#txtValid");
    if ($obj.val().Trim() == '') {
        showLoginError('请输入验证码。'); return false;
    }
    else if (/^[a-zA-Z0-9]{4}$/.test($obj.val().Trim()) == false) {
        showLoginError('验证码输入错误，请重新输入。'); return false;
    }
    else { resetLoginError(); return true; };
};
function ShowLoginTab() {
    $("#tabRegister").removeClass("active");
    $('#tabLogin').addClass("active");
    $('#TbLogin').show();
    $('#TbRegister').hide();
};
function ShowRegisterTab() {
    $("#tabLogin").removeClass("active");
    $('#tabRegister').addClass("active");
    $('#TbRegister').show();
    $('#TbLogin').hide();
};
//提交注册
function RegistFastByEmail() {
    if (newregedit()) {
        resetRegistError();
        jQuery.Alert('正在提交注册，请稍候');
        beginreg = true;
        var code = $('#txtRegisterValid').val();
        $.ajax({
        url: "/Service/UserWebService.asmx/RegistFastPop",
        data: "email=" + escape(regname) + "&password=" + escape(regpass) + "&code=" + code,
            success: function(result) {
                onRegistFastByEmailSucceed(result.text);
            }
        });
    }
};
//提交注册 -- 投保优化
function RegistFastByEmail(c) {
    if (newregedit()) {
        //resetRegistError();
        //jQuery.Alert('正在提交注册，请稍候');
        //beginreg = true;
        var code = $('#txtRegisterValid').val();
        $.ajax({
        url: "/Service/UserWebService.asmx/RegistFastPop",
            data: "email=" + escape(regname) + "&password=" + escape(regpass) + "&code=" + code,
            success: function(result) {
                onRegistFastByEmailSucceed(result, c);
            }
        });
    }
};
//判断注册返回值
function onRegistFastByEmailSucceed(result) {
    if (result == 'ok') {
        jQuery.Alert('注册成功');
        $.unblockUI();
        if (theForm != undefined) {
            submitIns(2);
        }
    }
    else if (result == 'error1') {
        alert('验证码错误');
    }
    else if (result == 'error2') {
        alert('验证码失效');
    }
    else {
        showRegistError(result);
        //jQuery.MessageBox(result, 380, 450);
        $.unblockUI();
    }
};
//判断注册返回值 --优化投保
function onRegistFastByEmailSucceed(result, c) {
    //判断浏览器
    var isresult = "";
    if ($.browser.msie) {
        //IE
        isresult = result.text;
    }
    else if ($.browser.safari) {
        //苹果  、谷歌
        if (result.childNodes[0].lastChild == null) {
            isresult = result.text;
        } else {
            isresult = result.childNodes[0].lastChild.data;
        }
    }
    else if ($.browser.mozilla) {
        //Firefox 火狐
        isresult = result.childNodes[0].textContent;
    }
    else if ($.browser.opera) {
        // opera            
        isresult = result.text;
    }
    else {
        //默认为IE
        isresult = result.text;
    }
    if (isresult == '' || isresult == undefined) {
        jQuery.Alert('注册成功');

        //注册成功显示会员价
        var id = document.getElementById("hidProdId").value;
        //objExp107.Calculate();
        var objE = 'objExp' + id;
        //去掉双引号
        objE = eval('(' + objE + ')');
        objE.Calculate();

        $.unblockUI();
        if (theForm != undefined) {
            submitIns(c);
        }
    }
    else if ((isresult == '验证码已过期，请重新获取' || isresult == '验证码错误')) {
         GettocodeRegister();
         $('#txtRegisterValid').val('');
         showError(isresult);
    }
    else {
        showRegistError(isresult);
        var id = document.getElementById("hidProdId").value;
        //判断是投保登录还是直接登录
        if (c == 2 || c == "2") {
            //注册成功显示会员价
            //objExp107.Calculate();
            var objE = 'objExp' + id;
            //去掉双引号
            objE = eval('(' + objE + ')');
            objE.Calculate(); //重新加载价格
            //更新登录状态   
            $('#TopUserInfo').html("您好：" + escape(regname) + "！ | <a href=\"/Logout.aspx\" title=\"退出登录\">退出登录</a>");

        } else if (c == 1 || c == "1") {
            //注册成功显示会员价
            //objExp107.Calculate();
            var objE = 'objExp' + id;
            //去掉双引号
            objE = eval('(' + objE + ')');
            objE.Calculate(); //重新加载价格
            //更新登录状态   
            $('#TopUserInfo').html("您好：" + escape(regname) + "！ | <a href=\"/Logout.aspx\" title=\"退出登录\">退出登录</a>");

        } else {//立即投保
            //立即投保，重调下单方法
            submitIns(c);
        }
        //jQuery.MessageBox(result, 380, 450);
        $.unblockUI();
    }
};
//验证邮件
function validEmail() {
    var v = $('#newlogname');
    regname = $('#newlogname').val();
    var img1 = $('#img1');
    var rehisterror1 = $('#rehisterror1');
    var p = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    if (v.val() == '') {
        img1.show();
        rehisterror1.html('请输入邮箱地址');
        showErrorStyle(v.attr("id"));
        IsValidEmail = false
    }
    else {
        if (!p.test(v.val())) {
            img1.show();
            rehisterror1.html('邮箱格式错误');
            showErrorStyle(v.attr("id"));
            IsValidEmail = false
        }
        else {
            $.ajax({
                url: "/Service/UserWebService.asmx/GetMail",
                data: "mymail=" + escape(v.val()),
                success: function(result) {
                    onmailSucceeded(result);
                },
                error: function(err) {
                }
            });
        }
    }
};
//验证邮件 -- 返回值
function onmailSucceeded(result) {
    //判断浏览器
    var isresult = "";
    if ($.browser.msie) {
        //IE
        isresult = result.text;
    }
    else if ($.browser.safari) {
        //苹果  、谷歌
        isresult = result.childNodes[0].textContent;
    }
    else if ($.browser.mozilla) {
        //Firefox 火狐
        isresult = result.childNodes[0].textContent;
    }
    else if ($.browser.opera) {
        // opera            
        isresult = result.text;
    }
    else {
        //默认为IE
        isresult = result.text;
    }
    if (isresult == 'true' || isresult == true) {
        if (beginreg) {
            return;
        }
        $('#img1').show();
        $('#rehisterror1').html('该邮箱已被注册');
        showErrorStyle("newlogname");
        IsValidEmail = false;
    }
    else {
        $('#img1').hide();
        $('#rehisterror1').html('');
        resetErrorStyle("newlogname");
        IsValidEmail = true;
    }
};
//验证密码
function validPass() {
    var v = $('#newpassword');
    var img2 = $('#img2');
    var rehisterror2 = $('#rehisterror2');
    var p = /[a-zA-Z0-9]{1}[a-zA-Z0-9\.\_]{5,15}/;
    if (v.val() == '') {
        img2.show();
        rehisterror2.html('密码不能为空');
        showErrorStyle(v.attr("id"));
        IsValidPass = false;
    }
    else {
        if (!p.test(v.val())) {
            img2.show();
            rehisterror2.html('密码格式错误');
            showErrorStyle(v.attr("id"));
            IsValidPass = false;
        }
        else {
            img2.hide();
            rehisterror2.html('');
            resetErrorStyle(v.attr("id"));
            IsValidPass = true;
        }
    }
};
//验证重复密码
function validRePass() {
    var rev = $('#newpassword1');
    var v = $('#newpassword').val();
    var img3 = $('#img3');
    var rehisterror3 = $('#rehisterror3');
    if (rev.val() == '') {
        img3.show();
        rehisterror3.html('请再次输入密码');
        showErrorStyle(rev.attr("id"));
        IsValidRePass = false;
    }
    else {
        if (rev.val() != v) {
            img3.show();
            rehisterror3.html('两次密码不一致');
            showErrorStyle(rev.attr("id"));
            IsValidRePass = false;
        }
        else {
            img3.hide();
            rehisterror3.html('');
            resetErrorStyle(rev.attr("id"));
            IsValidRePass = true;
            regpass = $('#newpassword1').val();
        }
    }
};
function newregedit() {
    validEmail();
    validPass();
    validRePass();
    if (!IsValidEmail) return false;
    if (!IsValidPass) return false;
    if (!IsValidRePass) return false;
    return true;
}
//显示登录错误提示信息
function showLoginError(msg) {
    $(".loginerror").show();
    $("#imgerror").show();
    $("#errormess").html(msg);
}
//初始化错误提示信息
function resetLoginError() {
    $(".loginerror").hide();
    $("#imgerror").hide();
    $("#errormess").html('');
    resetErrorStyle("logName");
    resetErrorStyle("logPass");
    resetErrorStyle("txtValid");
}
//显示错误样式
function showErrorStyle(objName) {
    $("#" + objName).removeClass('tx10').addClass('tx11');
}
//去掉错误样式
function resetErrorStyle(objName) {
    $("#" + objName).removeClass('tx11').addClass('tx10');
}
//显示登录错误提示信息
function showRegistError(msg) {
    $(".regisiterror").show();
    $("#regimgerror").show();
    $("#regerrormess").html(msg);
}
//初始化注册错误提示信息
function resetRegistError() {
    $(".regisiterror").hide();
    $("#regimgerror").hide();
    $("#regerrormess").html('');
}
//显示注册错误提示信息
function showError(msg) {
    $("#imgRgError").show();
    $("#errorWord").html(msg);
}
//隐藏错误消息
function hideError() {
    $("#imgRgError").hide();
    $("#errorWord").html('');
}