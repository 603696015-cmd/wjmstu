<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="UTF-8">
<title>HSK</title>
<link rel="stylesheet" href="simulation/css/bootstrap.min.css">
<link rel="stylesheet" href="simulation/css/common.css">
</head>
<div class="e-container">       
        <div class="row">           
            <div class="col-md-2"></div>
            <div class="col-md-8  e-relative">
                    <h2 class="e-info-topic">${examPaper.title}</h2>
                <div class="e-content">
                    <div class="row pd0 mg0">
                        <div class="col-md-6 pd0 fl">
                            <div class="e-info-content-left">
                                <div class="e-pd-30">
                                    <p class="e-info-title">
                                        <span></span>
                                        <a href="javascript:;">信息确认</a>
                                    </p>
                                    <p class="e-info-inform">考试须知</p>
                                    <p class="e-info-paragraph">
                                        一、在考试开始前30分钟开始入场；在听力考试结束前到达考场的考生，可待阅读部分开始时参加考试，所误时间不补；在阅读考试开始后，迟到的考生不得进入考场参加考试。
                                    </p>
                                    <p class="e-info-paragraph">
                                        二、考生进考场时须出示准考证和报名时提供的规定身份证件，身份证件上的姓名必须与准考证上登记的姓名完全一致，证件上的照片必须是可以确认的考生本人，即照片与本人面貌一致。进入考场后，考生须将准考证和身份证件放在桌子的右上方，以备主、监考随时检查。入场时无法提供规定证件或持任何假证件的考生，将被拒绝参加考试，不退还考试费用。
                                    </p>
                                    <p class="e-info-paragraph">
                                        三、考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知考试内容的须知
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 pd0 fr">
                            <div class="e-info-content-right">
                                <div class="e-pd-30">
                                    <p class="e-info-title">                                        
                                        <a class="fr e-help" href="javascript:;">帮助</a>
                                    </p>
                                    <p class="e-info-inform">个人信息</p>
                                        <form action="">
                                            <div class="row e-info-personal e-relative">
                                                <div class="e-personal-pic">
                                                    <img src="simulation/images/headset.png" alt="">
                                                </div>
                                                <div class="col-md-3">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</div>
                                                <div class="col-md-9 e-info-val">${user.realname}</div>
                                                <div class="col-md-3">国&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;籍：</div>
                                                <div class="col-md-9 e-info-val">${user.jiguan?user.jiguan:'中国'}</div>
                                                <div class="col-md-3">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</div>
                                                <div class="col-md-9 e-info-val">${user.sex}</div>
                                                <div class="col-md-3">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</div>
                                                <div class="col-md-9 e-info-val">${user.username}</div>
                                                <div class="col-md-3">考试科目：</div>
                                                <div class="col-md-9 e-info-val">${examPaper.title}</div>
                                            </div>
                                        </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 e-align-center fr">
                            	<input class="e-info-submit ajax" type="button" value="确认">
                        </div>
                    </div>
                </div>
               
               
            </div>
            <div class="col-md-2"></div>
        </div>

    </div>
    <script type="text/javascript" src="js/jquery.js" ></script>
    <script type="text/javascript">
    	$(function(){
    		$('.ajax').bind('click',function(){
    			location.href="simulation_device.action?examId="+${examId};
    		});
    	});
    </script>
</body>
</html>
