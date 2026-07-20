<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
 <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>  
 <c:set var="ctx" value="${pageContext.request.contextPath}"/>          
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
		<link rel="stylesheet" href="simulation/css/examStyle.css">
		<script type="text/javascript" src="js/jquery.js" ></script>
		<script src="${ctx}/simulation/js/audio.min.js" type="text/javascript"></script> 
		<script type="text/javascript" src="${ctx}/simulation/js/jquery.cookie.js" ></script>
		<script src="${ctx}/simulation/js/layui/layui.js"></script>
		<script type="text/javascript" src="${ctx}/simulation/js/exam.js"></script>
	<%-- 	<script type="text/javascript" src="${ctx}/simulation/js/jquery-hcheckbox.js" ></script> --%>
	
	   <script src='${ctx}/simulation/js/dragdrop.js' type="text/javascript"></script>
       <link href='${ctx}/simulation/css/dragdrop.css' rel="stylesheet" type="text/css" />
<title>试卷页面</title>
</head>
<body>
    <input type="hidden" id="examId" value="${examId}"/>
  <div class="e-header-container">
        <div class="e-logo-container">
            <h1>
                <img src="simulation/images/college-logo.png" alt="">
            </h1>
        </div>
    </div>
    <div class="e-content-container">
        <div class="row">
            <div class="col-md-3" id="main_one">
                <div class="e-wrapper-left">
                    <div class="e-user-info  bg-gray">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="e-user-pic-c">
                                    <img src="simulation/images/logo-icon-blue.png" alt="">
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="e-user-detial">
                                    <p class="mg0">
                                        <b>姓名：</b>
                                    </p>
                                    <p>李晓明</p>
                                    <p class="mg0">
                                        <b>准考证号：</b>
                                    </p>
                                    <p class="mg0">1236545646464564654</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- <div class="e-valume mgt-10 e-align-center">
                        <img src="simulation/images/valume-icon.png" alt="">
                        <input type="range">
                        <i class="e-valume-value">50</i>
                    </div> -->
                    <div class="e-paper-structor mgt-10">
                        <div class="bg-gray">
                            <div class="e-paper-topic clearfix">
                                <h3>试卷结构</h3>
                                <a class="fr e-check" href="javascript:;">查看</a>
                            </div>
                            <ul class="e-paper-tab clearfix">
                                <li class="on" data-type="1">听力</li>
                                <li data-type="2">阅读</li>
                                <li class="mg-0" data-type="3">书写</li>
                            </ul>
                        </div>
                        <div class="e-question clearfix">
                            <!-- <p class="e-question-topic">第一部分</p>
                        <ul class="e-question-num clearfix">
                            <li>1</li>
                            <li>2</li>
                            <li class="mg-0">3</li>
                            <li>4</li>
                            <li>5</li>
                            <li>6</li>
                            <li>7</li>
                            <li>8</li>
                            <li>9</li>
                            <li>10</li>
                            <li>11</li>
                        </ul>
                        <p class="e-question-topic">第二部分</p>
                        <ul class="e-question-num clearfix">
                            <li>1</li>
                            <li>2</li>
                            <li class="mg-0">3</li>
                            <li>4</li>
                            <li>5</li>
                            <li>6</li>
                            <li>7</li>
                            <li>8</li>
                            <li>9</li>
                            <li>10</li>
                            <li>11</li>
                        </ul> -->
                        </div>
                        <div class="e-question e-hide clearfix"></div>
                        <div class="e-question e-hide clearfix"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="e-wrapper-right">
					<div id="subNav_one" class="subNav_one">
						<div class="subNavCenter">
							<ul>
								<li>
									<h5 id="spPartTime" data-time="${dataTime}"></h5>
								</li>
							</ul>
						</div>
						<div class="subNavRight">
							<div class="subNavRight1">
								<input type="button" id="btnFinish" value="结束阅读"
									class="writingbutton1">&nbsp; 
									<input type="submit"
									name="ctl00$CphMain$btnsubmitTest" value="提交试卷"  id="btnsubmitTest"
									title="提交试卷后将结束考试，不能继续作答！" class="writingbutton1"/>
							</div>
							<div class="subNavRight2">
								<a href="#" style="font-size: 18px; text-decoration: none;"
									onclick="top.__popupShareWindow.Show('ExamInfo.aspx','帮助')">
									帮助 </a>
							</div>
						</div>
					</div>

					<div class="content_one" id="content_one"> 
					   <div class="spbj" id="spbj"> 
					    <div class="spbjhh"> 
					     <table width="0" border="0" cellspacing="0" cellpadding="0" class="spbjh2table"> 
					      <tbody>
					       <tr> 
					        <td style="float:left;"> 
					         <div class="voicebjbj" style="float: left; display: block;"> 
					          <ul> 
					           <li> 
					            <div class="text"> 
					             <p id="ItemIDBox">第<strong id="itemIdNum">6</strong>题&nbsp;&nbsp;共<strong>18</strong>秒</p> 
					            </div> </li> 
					           <li> 
					            <div id="progressBarBox">
					             <div class="progressBar">
					              <span><em style="left: 0px; width: 0px;"></em></span>
					             </div>
					            </div> </li> 
					          </ul> 
					         </div> 
					         <div id="playMidea" style="position:absolute;left:-2000px;">
                                        <audio controls="controls" preload="metadata" >
                                            <source type="audio/mp3" src="">
                                        </audio>
                                </div>
					         <div style="float:left;"> 
					          <h2> <span id="sptitle"></span> </h2> 
					         </div> </td> 
					        <td align="right" valign="bottom"> <span class="spbjh3"><a id="btnFlag" style="color: #1484D9; font-size: 18px" href="#"> 标记 </a></span><span class="spbjh4">【文字:<a href="javascript:SetResponseFont(21)">大</a> <a href="javascript:SetResponseFont(20)">中</a> <a href="javascript:SetResponseFont(18)"> 小</a> 】 </span> </td> 
					       </tr> 
					      </tbody>
					     </table> 
					    </div> 
					    <input id="tx_itemId" name="tx_itemId" type="hidden" value="navItem6a158cb0-093c-4894-aef2-aa15f079e37f" /> 
					    <input id="tx_itemType" name="tx_itemType" type="hidden" value="3" /> 
					    <div id="testpaperResponsepanel" class="responsepanel18" onkeydown="onKeyEnter();" style="font-size: 18px;" scrolltop="0">
					     
					    </div> 
					    <div style="text-align: center; padding: 10px;"> 
					     <input type="submit" value="&lt;&lt; 上一题" onclick="return false;" id="btnPrev" class="button" onkeydown="onKeyEnter();" /> &nbsp; 
					     <input type="submit" value="下一题 &gt;&gt;" onclick="return false;" id="btnNext" class="button" onkeydown="onKeyEnter();" /> 
					    </div> 
				   		 <button  style="z-index:-1;opacity:0;position: absolute;left:-1000px;" id="btnPlay"></button>
					    <div id="spnsubmit" style="display: none"> 
					    <!--  <input type="submit" name="ctl00$CphMain$BtnSubmit" value="交卷" onclick="submitPaper();" id="ctl00_CphMain_BtnSubmit"  />  -->
					    </div> 
					   </div> 
					  </div>
                </div>
            </div>
        </div>
    </div>

    <input type="hidden" name="ctx" value="${ctx}"/>

    <script type="text/javascript">
    audiojs.events.ready(function (){
        var as = audiojs.createAll();
        //sample player
        KyPlayer.player = as[0];
        
    });
    
    </script>
	<script type="text/javascript" src="simulation/js/loadReadMultiple.js""></script>
    <script type="text/javascript" src="simulation/js/paper_ex.js"></script>
   
    <script type="text/javascript">
    	function checkPreButton(){
    		var seqNum = getSeqNum();
	    	if((parseInt(seqNum)-1) == 0){
	    		$('#btnPrev').attr('class','d_button');
	    		$("#btnPrev").attr("disabled","disabled");
	    		return false;
	    	}
    	}
    	
    	function checkNextButton(){
    		var num = $.cookie('q_num');
	    	var seqNum = typeof (getSeqNum()) == 'undefined'?1:getSeqNum();
	    	if(parseInt(seqNum) == parseInt(num)){
	    		$('#btnNext').attr('class','d_button');
	    		$("#btnNext").attr("disabled","disabled");
	    		return false;
	    	}
    		
    	}
	    $('#btnPrev').click(function () {
	    	$('#btnNext').attr('class','button');
    		$("#btnNext").removeAttr("disabled");
	        //判断是点击还是通过键盘触发
	        if (checkEnter()) {
	            return false;
	        }
	        goPrevItem();
	        
	        checkPreButton();
	    });
	    $('#btnNext').click(function () {
	    	
	    	$('#btnPrev').attr('class','button');
    		$("#btnPrev").removeAttr("disabled");;
	    	
	        //判断是点击还是通过键盘触发
	        if (checkEnter()) {
	            return false;
	        }
	        goNextItem();
	        checkNextButton();
	        
	    });
	    $('#btnFinish').click(function () {
	        //判断是点击还是通过键盘触发
	        if (checkEnter()) {
	            return false;
	        }
	        redirectPart();
	    });
        $(function () {
        	//$(".e-wrapper-right").load("subject.1.html");
            //test data
            $('.judgementResponse').hradio();
        
            
            //checkPreButton();
            //checkNextButton();
	    	
            
        }); 
        
    </script>
    <script type="text/javascript">
	    
	    $(function () {
            setInitial();
        });

        function setInitial() {
            var volume = $.cookie("mediavolume");
            if (volume == null || volume == undefined) {
                volume = 50;
            }
            $("#amount").html(volume);
            try {
                //if (document.getElementById("player"))
                //    document.getElementById("player").controlVolume(volume);

                KyPlayer.init(volume / 100);
            }
            catch (ex) {
                alert(ex);
            }

            var date = new Date();
            date.setTime(date.getTime() + 24 * 60 * 60 * 1000);  //换成毫秒
            $.cookie("mediavolume", volume, { path: '/', expires: date });

        }
        window.onload = setInitial;
    </script>
</body>

</html>