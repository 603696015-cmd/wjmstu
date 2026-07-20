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
                <h2 class="e-info-topic">${examPaper.title }</h2>
                <div class="e-content">
                    <div class="row pd0 mg0">
                        <div class="col-md-4 pd0 fl">
                            <div class="e-content-left">
                                <div class="e-eq-info">
                                    <p class="mg0">
                                        <b>姓名：</b>
                                    </p>
                                    <p>${sessionScope.realname}</p>
                                    <p class="mg0">
                                        <b>准考证号：</b>
                                    </p>
                                    <p class="mg0">${sessionScope.username}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 pd0 fr">
                            <div class="e-content-right">
                                <div class="e-pd-30">
                                    <p class="e-info-title">
                                        <span></span>
                                        <a href="javascript:;">设备调试</a>
                                        <a class="fr e-help" href="javascript:;">帮助</a>
                                    </p>
                                    <div class="e-loading-container clearfix">
                                        <div class="e-loading-left">
                                            <p class="e-paper">下载试卷</p>
                                            <span class="e-trangle"></span>
                                        </div>
                                        <div class="e-loading-right">
                                            <div class="row mgt27">
                                                <div class="col-md-3 e-align-right">
                                                    <img src="simulation/images/test.png" alt="">
                                                </div>
                                                <div class="col-md-5">
                                                    <p class="e-align-center mgt10">正在下载</p>
                                                    <div class="progress">
                                                        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100"
                                                            style="width: 45%">
                                                            <span class="sr-only">45% Complete</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <img src="simulation/images/computer.png" alt="">
                                                </div>
                                            </div>
                                            <div class="row mgt27">
                                                <div class="col-md-12">
                                                    <p class="e-align-center e-loading-notice">正在下载试卷，请稍后...</p>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="e-loading-container clearfix">
                                        <div class="e-loading-left">
                                            <div class="e-hearing">
                                                <img src="simulation/images/headset.png" alt="">
                                            </div>
                                            <span class="e-trangle"></span>
                                        </div>
                                        <div class="e-loading-right">
                                            <div class="row mgt56">
                                                <div class="col-md-2"></div>
                                                <div class="col-md-4 pd0">  
                                                    <p class="e-align-center"><img src="simulation/images/valume.png" alt=""></p>
                                                    <div class="e-range e-align-center">
                                                            <input type="range">
                                                    </div>
                                                </div>
                                                <div class="col-md-6 e-align-center">
                                                    <a href="javascript:void(0)" class="ajax">
                                                    	<input class="e-eq-control" type="button" value="设备调试完成"/>
                                                    </a>
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                    <p class="e-notice">
                                        根据播放声音，调节耳机音量大小。如果音量大小已经调整好，请点击“设备调试完成”按钮
                                    </p>
                                </div>

                            </div>
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
			location.href="simulation_paper.action?examId="+${examId};
		});
	});

    </script>
</body>
</html>
