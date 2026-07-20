<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
 <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<base href="<%=basePath%>">
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<head>
		<link rel="stylesheet" href="simulation/css/bootstrap.min.css">
		<link rel="stylesheet" href="simulation/css/common.css">
		<script type="text/javascript" src="simulation/js/jquery.1.11.js" ></script>
<title>考试登录</title>
</head>
<body>
  <div class="e-container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div class="e-content">
                    <div class="col-md-4 pd0 fl">
                        <div class="e-content-left">
                            <p class="e-selector">请选择提示语</p>
                            <ul class="e-language">
                                <li>
                                    <a href="javascript:;">
                                        <img src="simulation/images/china.png" alt="Chinese">
                                        <b>中文</b>
                                        <span>Chinese</span>
                                    </a>
                                </li>
                                <li>
                                    
                                    <a href="javascript:;">
                                        <img src="simulation/images/English.png" alt="English">
                                        <b>英文</b>
                                        <span>English</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-8 pd0 fr">
                        <div class="e-content-right">
                            <div class="e-university">
                                <div class="e-university-img">
                                    <img src="simulation/images/logo.png" alt="">
                                </div>
                                <div>汉语水平考试HSK网模拟考试系统</div>
                            </div>
                            <div class="e-login-c">
                                <form action="">
                                    <div class="row mgt56">
                                        <div class="col-md-4">
                                            <strong class="e-title fr">准考证号</strong>
                                        </div>
                                        <div class="col-md-8">
                                            <input class="e-input" type="text" name="code" value="${sessionScope.username}" readonly="readonly"/>
                                        </div>
                                        <div class="col-md-4">
                                            <strong class="e-title fr" >考试密码</strong>
                                        </div>
                                        <div class="col-md-8">
                                            <input class="e-input" type="password" name="pwd" />
                                        </div>
                                        <div class="col-md-4"></div>
                                        <div class="col-md-8">
                                           <!--  <a href="simulation_infoconfirm.action"> -->
                                                <input class="e-button startExam" type="button" value="开始考试" style="cursor:pointer !important;">
                                          <!--   </a> -->
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="row mgt96">
                                <div class="col-md-5"></div>
                                <div class="col-md-7">
                                    <div class="col-md-12">
                                        <span class="e-copyright">责任单位：</span>
                                        <i class="e-copy-cont">xx部门</i>
                                    </div>
                                    <div class="col-md-12">
                                        <span class="e-copyright">责任部门：</span>
                                        <i class="e-copy-cont">xx部门</i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>

    </div>
    <script type="text/javascript" src="simulation/layer/layer.js"></script>
	<script type="text/javascript">
		$(function(){
			$('.startExam').bind('click',function(){
				var code = $('input[name=code]');
				var pwd = $('input[name=pwd]');
				if(code.val() == ''){
					 layer.msg('请输入准考证号', {
						    time: 2000 //20s后自动关闭
						  });
					code.focus();
					return;
				}
				
				if(pwd.val() == ''){
					layer.msg('请输入密码',{
						time:2000
					});
					pwd.focus();
					return ;
				}
				//验证登录
				checkLogin(code,pwd);
				
			});
			
			function checkLogin(userName,pwd){
				$.ajax({
					url:'simulation_login_check.action',
					type:'post',
					dataType:'json',
					data:{
						'code':userName.val(),
						'pwd':pwd.val()
					},
					success:function(data){
						if(data.status == 200){
							if(data.data){
								location.href ="simulation_info.action?examId="+${examId};
							}else{
								 layer.msg('准考证号或密码错误', {
									    time: 2000 //20s后自动关闭
									  });
							}
						}else{
							layer.msg(data.error);
						}
					}
				});
			}
		});
	</script>
	<%-- <script type="text/javascript" src="js/html5shiv.min.js" ></script>
	<script type="text/javascript" src="js/respond.min.js" ></script> --%>
</body>
</html>