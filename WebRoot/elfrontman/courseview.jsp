<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv = "X-UA-Compatible" content = "IE=7,IE=9"/>
	<title>[百点学苑-推销与谈判9]-传课网</title>
	<meta name="keywords" content="百点学苑-推销与谈判9，网络课程,传课网" />
		<meta name="description" content="百点学苑-推销与谈判9，由传课网提供"/>
	<link rel="shortcut icon" type="/image/x-icon" href="http://www.chuanke.com/favicon.ico" />
	<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_base.css" media="screen">
	<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_detail.css" media="screen">
	<script src="http://res.ckimg.com/lib/jquery/jquery.js"></script>
	<script src="http://res.ckimg.com/lib/jquery/plugin/jquery.common.js"></script>
	<script src="http://res.ckimg.com/common/v2/js/functions.js"></script>
	<script src="http://res.ckimg.com/common/v2/js/kk_header.js"></script>
		<script src="http://www.chuanke.com/js/plugins/suggest/suggest.js"></script>
		<script>
	$(function(){
		KK.init();
		setInterval("getrsp()",60000);
		$("#header div.thead_search").inputTips();
		$("#head_searchKeywords").prev("span.iptTips").css('left', '60px');
		$("#head_searchTypeSelecte").click(function(){
			var cobj = $(this);

			if($("#head_searchType").css('display') == 'none'){
				cobj.showMenu('#head_searchType');
			}else{
				cobj.hideMenu('#head_searchType');
			}
		});
		
		$('#head_searchType').find("li").click(function(){
			$("#head_searchTypeSelecte").attr('type', $(this).find('a').attr('type')).text($(this).find('a').text());
			$("#head_searchType").hide();
		});
		
		$("#head_searchKeywords").keydown(function(event){
			if(event.keyCode == 13){
				$("#head_searchSubmit").trigger('click');
			}
		});
		
		$("#head_searchSubmit").click(function(){
			var keywords = $.trim($("#head_searchKeywords").val()),
			searchType = $("#head_searchTypeSelecte").attr('type');
			if(searchType == "course"){
				//searchUrl = "/?mod=search&act=course&keyword="+encodeURIComponent(keywords);
				if(keywords == ''){
					searchUrl = "/course/index.html";	
				}else{
					searchUrl = "/course/_"+encodeURIComponent(keywords)+"____.html";
				}
			}else{
				searchUrl = "/?mod=search&act=school&keyword="+encodeURIComponent(keywords);
			}
			document.location.href = searchUrl;
			return false;
		});

				KK.Suggest.Install('head_searchKeywords', 'search_suggestion');
			});
	</script>
	<!--[if IE]>
	<script src="http://res.ckimg.com/sites/www/v1/js/html5.js"></script>
	<![endif]-->
 
	<!-- Internet Explorer .png-fix -->
	<!--[if IE 6]>
		<script src="http://res.ckimg.com/sites/www/v1/js/dd_belatedpng.js"></script>
		<script>
			DD_belatedPNG.fix('.png_bg, img');
		</script>
	<![endif]-->
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript">

	$(	
	
		function a(){
				$("#ms").load(
					"getShoppingCarCount.action",
					ax
				);
			}
			
			
			/*var height=100/one;
			alert(height);
			var one=${courseComment.one}
			alert(one);
			var oneheight = height*one;
			$("#onestart").style.height=oneheight+"px";
			*/
			);

var ax={"statusId":'1'}
function  liuyan1(){ 
		location.hash="liuyan2";
	}

$(document).ready(function(){  
    $('#send_ajax').click(function(){ 
     if(document.getElementById("mycourse").value==1){
					 	alert('您已拥有该课程不需要再次报名'); 
					 	return ;
					 } 
     if(document.getElementById("mycourseorder").value==1){
					 	alert('您已存在该课程订单，不需要再次订购'); 
					 	return ;
					 }
		var aaa =$('#courseid').attr("value");
        var params = {courseid:aaa,type:1}; 
                          // contactEmail:$('#contactEmail').attr('value'),
                          // subject:$('#subject').attr('value'),
                           //content:$('#content').attr('value')                
                     
       // $.ajax({  
        //    url:'json.action',  
        //    type:'post',  
        //   data:params,  
        //    success:function(data){
         //   if(data.result=="success"){
        //    	alert("shibai");
        //    }else{
        //    alert("shibai1");
        //    }
        //    }
           // failure:function(){alert("shibai");}
      //  });  
       $.post("executeaa.action",params, function (data) {	
       			a();	
            	alert("已加入购物车");
 			    });
      // jQuery.post('executeaa.action', params, update_page, 'json');
    });  

    }); 
	
</script>

</head>
<body class="ck_detail" onkeyup="return registerHotKey(event);" onLoad="setImgs();">
	<input type="hidden" id="courseid" value="${course.id }">
	<s:hidden name="mycourse" id="mycourse"></s:hidden>
	<s:hidden name="mycourseorder" id="mycourseorder"></s:hidden>
	<div class="topbar" role="navigation" id="header-bar"></div>
		<header class="header" id="header">
		<div class="hd_wrap">
			<a class="logo" href="/">传课网</a>
			<!-- =S thead_search -->
			<div class="thead_search">
				<div class="s_fields">
										<a class="l_f" id="head_searchTypeSelecte" type="course" href="javascript:;">课程</a>
										<ul class="s_fields_select" style="display:none;" id="head_searchType">
						<li><a href="javascript:;" type="course">课程</a></li>
						<li><a href="javascript:;" type="school">学校</a></li>
					</ul>
					<input type="text" prompt="请输入关键词、学校名等..." class="s_fields_txt" id="head_searchKeywords" value="">
					<div id="search_suggestion" style="visibility:hidden;"></div>
				</div>
				<input type="button" value="" id="head_searchSubmit" class="s_btn">
			</div>
		</div>
	</header>
		<style>
	.rTop{width:60px;margin-left:510px;}
	.rTop ul li{position:relative;width:60px;height:60px;margin-bottom:7px;}
	.rTop ul li.r_gotop a,
	.rTop ul li.r_msg a,
	.rTop ul li.r_weixin a,
	.rTop ul li.r_weixin .codeImg{display:block;width:60px;height:60px;overflow:hidden;text-indent:-999em;background:url('http://res.ckimg.com/sites/www/v2/images/detail/r_gotop.png') no-repeat -302px 0;}
	.rTop ul li.r_gotop a{background-position:-302px 0;}
	.rTop ul li.r_gotop a:hover{background-position:-241px 0;}
	.rTop ul li.r_msg a{background-position:-302px -67px;}
	.rTop ul li.r_msg a:hover{background-position:-241px -67px;}
	.rTop ul li.r_weixin a{background-position:-302px -133px;}
	.rTop ul li.r_weixin a:hover{background-position:-241px -133px;}
	.rTop ul li.r_weixin .codeImg{position:absolute;bottom:0;left:-166px;width:166px;height:159px;background-position:-74px -33px;}
	</style>
	<div style="position:fixed;_position:absolute;left:50%;margin-left:510px;bottom:20px;z-index:599;" class="rTop">
		<ul>
			<li class="r_gotop" style="display:none;">
				<a href="#"></a>
			</li>
			<li class="r_msg">
				<a href="javascript:;" id="btnFeedback"></a>
			</li>
			<li class="r_weixin">
				<a href="javascript:;" id="btnWeixin"></a>
				<span class="codeImg" id="weixinCodeImg" style="display:none;"></span>
			</li>
		</ul>
	</div>		
<script type="text/javascript">
function addListener(element, e, fn) {
    if (element.addEventListener) {
        element.addEventListener(e, fn, false);
    } else {
        element.attachEvent("on" + e, fn);
    }
}
$(function(){
	$(window).scroll(function(){
		if ($(window).scrollTop()>100){
			$("li.r_gotop").fadeIn(1500);
		}else{
			$("li.r_gotop").fadeOut(1500);
		}
	});
	
	addListener(document, "click",function(evt) {
	    var evt = window.event ? window.event: evt,
	    target = evt.srcElement || evt.target;
	    if(target.id == "btnWeixin") {
	        $("#weixinCodeImg").show();
	        return;
	    }else {
	        while (target.id != "weixinCodeImg" && target.nodeName.toLowerCase() != "html") {
	            target = target.parentNode;
	        }
	        if (target.nodeName.toLowerCase() == "html") {
	            $("#weixinCodeImg").hide();
	        }
	    }
	});	
	
	$("#btnFeedback").click(function(){
		var popFeedbackBox = $.ckAjaxBoxy({
			url:"/?mod=help&act=about&type=popfeed",
			title:"意见反馈",
			width:540,
			height:424,
			callback:function(){
				$("#cancel").click(function(){
					popFeedbackBox.cancel();
				});
				$("#FeedbackType").change(function(){
					$("#FeedbackTypeErrorTips").hide();
				});
				$("#submit").click(function(){
					$("#FeedbackErrorTips,#FeedbackTypeErrorTips").hide();
					var type = $("#FeedbackType").val();
					var message = $("#FeedbackMsg").val();
					if(type == ''){
						$("#FeedbackTypeErrorTips").text('请选择问题类型').show();
						return false;
					}
					if(message == ''){
						$("#FeedbackErrorTips").text('请填写反馈意见').show();
						return false;
					}
					if(getStrActualLen(message) > 64000){
						$("#FeedbackErrorTips").text('反馈意见不得超过64000字符').show();
						return false;
					}
					if($(this).attr('disabled') == true){
						return false;
					}
					$.ajax({
						type:"post",
						url:"/?mod=help&act=about&type=popfeed&op=submit",
						dataType:"json",
						data:"type="+type+"&message="+encodeURIComponent(message),
						beforeSend:function(){
							$(this).attr("disabled",true);
						},
						success:function(ret){
							if(ret.code == 0){
								popFeedbackBox.cancel();
								$.ckTipsBoxy({message:ret.data,width:300});
							}else{
								$(this).removeAttr("disabled");
								alert(ret.data);
							}
						}
					});
				});
				$("#divFbMsg").inputTips();
				$("#FeedbackType").divSelect(160);
			}
		});		
	});
});
</script>		<div class="siteNav">
	<div class="srh_wrap">
		<div class="srh_category">
			<header class="mt">
				<h2><a class="mt_t" href="http://www.chuanke.com/course/index.html" target="_blank" id="categorySwitch" show="0">全部课程分类</a></h2>
			</header>
			<div class="mc" id="categoryList" style="display:none;" show="0">
				<dl itemid="1" class="item">
    <dt class="i_t first">技能专区</dt>
    <dd class="i_c" style="height:96px;">
<ul class="categUl">
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72351163642544128_____.html">IT/互联网</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72354462177427456_____.html">语言学习</a></li>
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72362158758821888_____.html">职场/求职</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72367656316960768_____.html">通用技能</a></li>
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72348964619288576_____.html">金融管理</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72366556805332992_____.html">市场/营销</a></li>
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72355561689055232_____.html">其他技能</a></li>
</ul>
   </dd>    
</dl>
<div style="top: 0px; left: 180px; display: none;" class="i_c_tips" id="itemList_1">
    <div class="i_c_tips_t">IT/职场/技术/技能</div>
    <div class="i_c_tips_c clearfix">
<div class="c_item">
            <div class="t">IT/互联网/计算机</div>
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72351176527446016_____.html">编程语言</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236791205888_____.html">工具软件</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236841537536_____.html">页面设计</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351180822413312_____.html">数据库</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351189412347904_____.html">系统运维</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351202297249792_____.html">移动互联网</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351185117380608_____.html">系统架构</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236774428672_____.html">网站制作</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236740874240_____.html">工业设计</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236707319808_____.html">平面设计</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236724097024_____.html">游戏设计</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236673765376_____.html">CG动画</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351236824760320_____.html">三维设计</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72351210887184384_____.html">产品/运营</a></li>
    </ul>
    <div class="t">语言学习</div>    
            <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72354505345204224_____.html">英语</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72354470784139264_____.html">日语</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72354479374073856_____.html">法语</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72354475062329344_____.html">韩语</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72354500832133120_____.html">粤语</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72354496537165824_____.html">小语种</a></li>
    </ul>
    <div class="t">市场营销</div>   
            <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72366565529485312_____.html">淘宝营销</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366565412044800_____.html">SEO</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366565428822016_____.html">SEM</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366565646925824_____.html">数据库营销</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366565462376448_____.html">SNS营销</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366565445599232_____.html">EDM营销</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366565546262528_____.html">网络推广</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366561234518016_____.html">推销/促销</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366561133854720_____.html">电子商务</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366561251295232_____.html">销售</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366561167409152_____.html">营销策略</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72366561117077504_____.html">对外贸易</a></li>
    </ul>
    </div>
<div class="c_item">
    <div class="t">职场/求职</div>   
            <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72362171677278208_____.html">职业规划</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362171710832640_____.html">求职简历</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362171727609856_____.html">面试技巧</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362188823592960_____.html">职场礼仪</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362171660500992_____.html">就业指导</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362167348756480_____.html">职场技能</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362180233658368_____.html">创业</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362184646066176_____.html">财务</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72362175938691072_____.html">领导力培训</a></li>
    </ul>    
    <div class="t">通用技能</div>
            <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72367660628836352_____.html">Excel</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72367660628901888_____.html">Word</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72367660628770816_____.html">PPT</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72367660645482496_____.html">WPS</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72367664906895360_____.html">电脑基础</a></li>
    </ul>
    <div class="t">金融/管理/经济</div>   
            <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72348990389092352_____.html">理财</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72349029060575232_____.html">股票</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72349029094129664_____.html">期货</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72349024748830720_____.html">保险</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72349020453863424_____.html">管理</a></li>
    </ul>
    <div class="t">其他技能</div>    
            <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72355570278989824_____.html">医疗/医药</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72355574573957120_____.html">师资培训</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72355587475636224_____.html">PLC</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72355583180668928_____.html">电路基础</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72355565984022528_____.html">其他</a></li>
    </ul>
</div>
    </div>
</div>

<dl itemid="2" class="item">
    <dt class="i_t">考试考级</dt>
    <dd class="i_c" style="height:48px;">   
        <ul class="categUl">
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72365461588672512_____.html">大学生考试</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72365465883639808_____.html">资格考试</a></li>
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72365474473574400_____.html">公务员</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72365478768541696_____.html">出国留学</a></li>
</ul>
    </dd>
</dl>
<div style="top: 0px; left: 180px; display: none;" class="i_c_tips" id="itemList_2">
    <div class="i_c_tips_t">考试/考级/留学</div>
    <div class="i_c_tips_c clearfix">
<div class="c_item">
    <div class="t">大学生考试</div>    
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72365461605449728_____.html">考研</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365461622226944_____.html">四六级</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365461639004160_____.html">计算机等考</a></li>
    </ul>
    <div class="t">公务员</div>  
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72365474490351616_____.html">国考</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365474507128832_____.html">省考</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365474624569344_____.html">在职公务员</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365474607792128_____.html">军转干</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365474540683264_____.html">大学生村官</a></li>
    </ul>
</div>
<div class="c_item">
    <div class="t">资格考试</div>   
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72365465900417024_____.html">专业类</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365465917194240_____.html">建造类</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365465967525888_____.html">医药类</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365465933971456_____.html">财会类</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365465984303104_____.html">金融类</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365465950748672_____.html">IT/计算机类</a></li>
    </ul>
    <div class="t">出国留学</div>   
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72365478885982208_____.html">雅思</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365478886113280_____.html">托福</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365478886178816_____.html">GRE</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365478886309888_____.html">SAT</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72365478869204992_____.html">留学指导</a></li>
    </ul>
</div>
    </div>
</div>

<dl itemid="3" class="item">
    <dt class="i_t">中小学</dt>
    <dd class="i_c" style="height:48px;">    
<ul class="categUl">
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72363271155351552_____.html">小学</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72363266860384256_____.html">初中</a></li>
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72363262565416960_____.html">高中</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72363275450318848_____.html">家长专区</a></li>
</ul>
    </dd>
</dl>
<div style="top: 0px; left: 180px; display: none;" class="i_c_tips" id="itemList_3">
    <div class="i_c_tips_t">小学/初中/高中</div>
    <div class="i_c_tips_c clearfix">
<div class="c_item">
    <div class="t">小学</div>
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271390232576_____.html">学前班</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271407009792_____.html">一年级</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271423787008_____.html">二年级</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271440564224_____.html">三年级</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271457341440_____.html">四年级</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271474118656_____.html">五年级</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271490895872_____.html">六年级</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271507673088_____.html">小升初</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363271373455360_____.html">素质教育</a></li>
    </ul>
    <div class="t">高中</div>    
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72363262884184064_____.html">高一</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363262900961280_____.html">高二</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363262917738496_____.html">高三</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363262934515712_____.html">高考</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363262867406848_____.html">素质教育</a></li>
    </ul>
</div>
<div class="c_item">
    <div class="t">初中</div>  
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72363267128819712_____.html">初一</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363267145596928_____.html">初二</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363267162374144_____.html">初三</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363267179151360_____.html">中考</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363267112042496_____.html">素质教育</a></li>
    </ul>
    <div class="t">家长专区</div>  
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72363275467096064_____.html">早期教育</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363275483873280_____.html">小学教育</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363275500650496_____.html">初中教育</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363275517427712_____.html">高中教育</a></li>
    </ul>
    <div class="t">品牌专区</div>  
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72363279762063360_____.html">新概念</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363279795617792_____.html">三一口语</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72363279778840576_____.html">剑桥英语</a></li>
    </ul>
</div>
    </div>
</div>
<dl itemid="4" class="item">
    <dt class="i_t">生活/文艺</dt>
    <dd class="i_c" style="height:48px;">           
<ul class="categUl"> 
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72364366372012032_____.html">兴趣爱好</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72364362077044736_____.html">文化艺术</a></li>
    <li class="i_1"><a target="_blank" href="http://www.chuanke.com/course/72364374961946624_____.html">生活技巧</a></li>
    <li class="i_2"><a target="_blank" href="http://www.chuanke.com/course/72364370666979328_____.html">学术学科</a></li>
</ul>
    </dd>
</dl>
<div style="top: 0px; left: 180px; display: none;" class="i_c_tips" id="itemList_4">
    <div class="i_c_tips_t">文化/生活/兴趣</div>
    <div class="i_c_tips_c clearfix">
<div class="c_item">
    <div class="t">兴趣爱好</div>
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366372012032_____.html">摄影</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366388789248_____.html">旅游</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366590115840_____.html">星座</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366674001920_____.html">动漫</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366640447488_____.html">棋牌</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366388789248_____.html">游戏</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366506229760_____.html">体育</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364366690779136_____.html">舞蹈</a></li>
    </ul>                        
    <div class="t">生活技巧</div>   
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72364374995501056_____.html">美食</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364374978723840_____.html">化妆</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364374978723840_____.html">服饰</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364375012278272_____.html">社交礼仪</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364375029055488_____.html">其它</a></li>
    </ul>
</div>
<div class="c_item">          
    <div class="t">文化艺术</div>
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72364362093821952_____.html">国学</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364362110599168_____.html">文学</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364362278371328_____.html">美术/绘画</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364362228039680_____.html">音乐</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364362211262464_____.html">影视</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364362295148544_____.html">其它</a></li>
    </ul>
    <div class="t">学术/学科</div>   
    <ul>
<li><a target="_blank" href="http://www.chuanke.com/course/72364370683756544_____.html">自然科学</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364370700533760_____.html">社会科学</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364370717310976_____.html">形式科学</a></li>
<li><a target="_blank" href="http://www.chuanke.com/course/72364370734088192_____.html">应用科学</a></li>
    </ul>
</div>
    </div>
</div>			</div>
		</div>
		<div class="srh_menu">
			<ul>
				<li >
					<a href="/">首页</a>
				</li>
				<li >
					<a href="http://www.chuanke.com/?mod=index&act=channel&channelid=1" target="_blank">技能专区</a>
				</li>
				<li >
					<a href="http://www.chuanke.com/?mod=index&act=channel&channelid=2" target="_blank">中小学</a>
				</li>
				<li >
					<a href="http://www.chuanke.com/?mod=index&act=channel&channelid=3" target="_blank">考试考级</a>
				</li>
				<li >
					<a href="http://www.chuanke.com/?mod=index&act=channel&channelid=4" target="_blank">生活文艺</a>
				</li>
			</ul>
		</div>
		<div class="srh_mzmy">
			<p>
				<span><a href="http://zt.chuanke.com/?mod=special&act=summer2013" target="_blank">快乐暑假，轻松学习</a></span>			</p>
		</div>
	</div>
</div>
<script>
var tag = true;
var theHideMenu = function(obj, tag){
	if(tag){
		obj.removeClass("hover"); //删除获取焦点时的样式
		obj.next(".i_c_tips").hide(); //隐藏提示框
	}
}
$(function(){
		$("#categorySwitch").mouseover(function(){
		$(this).showMenu("#categoryList");
	}).mouseout(function(){
		$(this).hideMenu("#categoryList");
	});
		$("#categoryList > dl.item").mouseenter(function() {//设置当前所选项的鼠标滑过事件
		$("#categoryList > dl.item").removeClass('hover').next(".i_c_tips").hide();
		var objL = $(this); //获取当前对象
		var curY = objL.position().top;
		var curW = objL.outerWidth();
		
		objL.addClass("hover"); //增加获取焦点时的样式
		objL.next(".i_c_tips").show().css({"top":curY, "left":curW}); //显示并设置提示框的坐标
	}).mouseleave(function() {//设置当前所选项的鼠标移出事件
		objL = $(this);
		objL.next(".i_c_tips").mouseenter(function(){
			tag = false;
			objL.addClass("hover");
		}).mouseleave(function(){
			tag = true;
			objL.removeClass("hover");
			$(this).hide();
		});
		setTimeout('theHideMenu(objL, tag)', 1);
	});
});
</script>
<SCRIPT type="text/javascript">
	var imgs = new Array();
	 
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	} 
	
</SCRIPT>
	<div class="detailContent">
		<nav class="detailCrumb">
			<div class="fl mCrumb">
				<span class="s"><a href="http://www.chuanke.com/course/index.html">全部分类</a><i>&gt;</i></span>
														<span class="s"><a href="http://www.chuanke.com/course/72366556805332992_____.html">营销类</a><i>&gt;</i></span>
																			<span class="s"><a href="http://www.chuanke.com/course/72366561100300288_____.html">市场营销/销售</a><i>&gt;</i></span>
																			<span class="s here">推销/促销</span>
												</div>
		</nav>

		<!-- =S sectionTop -->
		<section class="sectionTop clearfix">
			<div class="st_main">
				<h1 class="bdTitle"><s:property value="course.name"/></h1>
				<!-- =E 主标题 hdTitle -->
				<div class="bdPic fl">
					<s:if test="elclass.mainimg != null">
						<img src="<s:property value="course.mainimg_"/>" width="450" height="300"> 
					</s:if>
					<s:else> 
						<img src="<s:property  escape="false" value="course.mainimg_"/>" id="cimg_0" width="450" height="300" />
						<SCRIPT type="text/javascript">
							obj = document.getElementById("cimg_0");
							addImgs(obj);
						</SCRIPT> 
					</s:else>
				</div>
				<!-- =E 标题图片 bdPic -->
				<div class="bdInfo fr simsun">
					<p class="mb10">价&nbsp;&nbsp;格：<i class="price"><s:property value="course.price.courseoldPrice" /></i> 元</p>
					<p class="mb10">总课时：<s:property value="course.during" />课时</p>
					<p class="mb10">结束时间：<span ><s:date name="course.roomend" format="yyyy年MM月dd日"/></span> <a href="http://wenda.chuanke.com/?/question/id-3273__item_id-2039__rf-false" target="_blank" class="i_help ml5"></a></p>
					<p class="mb10">担保期：15天 <a href="http://wenda.chuanke.com/?/question/id-3272__item_id-2037__rf-false" target="_blank" class="i_help ml5"></a></p>
					<div class="i_box mb10">
						<p>共有 <i class="c_4b9a03 f16 fb"><s:property value="users.size()"/></i> 人购买了此课程 | 限购 <i class="c_333 f14 fb">1000</i> 人</p>
											</div>
					<p class="mb10">学生满意度<i class="c_f60 arial">100%</i> <a class="ml20" href="javascript:;" id="comment_tab_switch">已有<s:property value="courseComment.count" />个评价</a></p>
					
										
					<s class="clear mb10"></s>
					<i id="buy_course_type" style="display:none;">1</i>
					 
					<div class="clearfix">		
						<s:if test="mycourse == 0">				
						<a href="shopping_addandto.action?commodity.commodityid=<s:property value="course.id"/>&commodity.commoditytype=1" ><img src="images/shopping/pic_29.gif" width="112" height="36" id="tocart"></a>
						<a href="javascript:;" ><img src="images/shopping/jr_buy.gif" width="99" height="32" id="send_ajax" ></a>
						</s:if>
						<s:else>
				     		<td colspan=2>已报名</td>
				     	</s:else>
					</div>
					<div class="extra_fav" style="top:-32px;right:-10px;">
						<a href="javascript:;" id="bdshare" class="c_btn26i fl mr10 bdshare_b" style="padding-bottom:0px;"><span>分享</span></a>
						<!-- Baidu Button BEGIN -->
						<script type="text/javascript" id="bdshare_js" data="type=button&amp;uid=688952" ></script>
						<script type="text/javascript" id="bdshell_js"></script>
						<script type="text/javascript">
							document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
							//在这里定义bds_config
							var bds_config = {'snsKey':{'tsina':'2463745741','qzone':'100262522','tqq':'801145774','renren':'5e8042ac9ceb446baaf09f4d80fe039a'},'bdText':'我在 @传课网 看到了一个非常不错的课程：《百点学苑-推销与谈判9》，大家也来听听课吧。@百点学苑 网址：'};
						</script>
						<!-- Baidu Button END -->
												<a href="javascript:;" id="collect_course" class="c_btn26j fl mr10"><span>课程收藏</span></a>
											</div>
				</div>
				<s class="clear"></s>
			</div>
			<!-- =E st_main -->
			<aside class="st_side">
				<div class="bdSchool">
					<header class="hd">
						<dl>
							<dt>
								<a href="http://www.chuanke.com/s1019545.html" target="_blank"><img src="http://www.chuanke.com//upload/logo/ee/1f/1019545_small.jpg?t=1379822020" width="40" height="40"/></a>
							</dt>
							<dd>
								<h4><a href="http://www.chuanke.com/s1019545.html" target="_blank">百点学苑</a></h4>
								<div class="s_rank pt5">
									信用：<img border="0" src="http://res.ckimg.com/sites/www/v2/images/public/level/s31.gif" />								</div>
							</dd>
						</dl>
					</header>
					<div class="bd">
						<ul>
							<li>校长：<s:property value="course.creater.realname" />	
							</li>
							<li>好评率：100%</li>
							<li>学生数：<s:property value="users.size()"/></li>
							<li>课程数：1</li>
							<li>收藏数：  
																<a class="ml5" href="javascript:;" id="collect_school">收藏学校</a>
															</li>
							<li>建校日期：<s:date name="course.createtime" format="yyyy年MM月dd日" /></li>
						</ul>
					</div>
					<footer class="ft">
						<div class="enter">
							<a href="" class="fl a1">进入学校</a>
							<a href="" class="fl a2">进入频道</a>
						</div>
					</footer>
				</div>
			</aside>
		</section>

		<section class="sectionMain mb20 clearfix">
			<div class="sm_side fl">
								<div class="bdStudent mb20">
					<header class="hd">
						<h4 class="fl mt5 ml10">学生<i class="arial c_f60 f12">（166）</i></h4>
					</header>
					<div class="bd">
						<div class="ulBox">
							<ul class="clearfix">
							<s:iterator value="users">
								<li>
									<img src="${touxiang }" width="50" height="50">
								</li>
							</s:iterator>
							</ul>
						</div>
						<a href="javascript:;" id="student_tab_switch" class="more mr10 c_999">更多>></a>
					</div>
				</div>
				
				<div class="bdFav mb20">
					<header class="hd">
						<ul>
							<li class="tab1 curr" id="buy_list_switch">购买量排行</li>
							<li class="tab2" id="collect_list_switch">收藏排行</li>
						</ul>
					</header>
					<div class="bd">
						<ul id="buy_top_list">
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-84880.html" title="百点学苑——如何沟通与说服1" target="_blank"><img src="http://web.img.chuanke.com/course/2013-01/02/d0d740bc33fc7d7c2a2e0466a67f45d6.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-84880.html" title="百点学苑——如何沟通与说服1" target="_blank">百点学苑——如何沟通与说服1</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">783</i>人报名</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-75610.html" title="《创业预备课》之《经验分享》" target="_blank"><img src="http://web.img.chuanke.com/course/2013-01/04/15b87138f81e1b0a01979bc319c73431.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-75610.html" title="《创业预备课》之《经验分享》" target="_blank">《创业预备课》之《经验分享》</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">597</i>人报名</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-84887.html" title="百点学苑——如何沟通与说服2" target="_blank"><img src="http://web.img.chuanke.com/course/2013-01/02/30d1a6e17091f1cf743820da4c499cf7.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-84887.html" title="百点学苑——如何沟通与说服2" target="_blank">百点学苑——如何沟通与说服2</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">432</i>人报名</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-84905.html" title="百点学苑——如何沟通与说服3" target="_blank"><img src="http://web.img.chuanke.com/course/2013-01/03/f2ab33a796d70d7472717a7f17225b9c.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-84905.html" title="百点学苑——如何沟通与说服3" target="_blank">百点学苑——如何沟通与说服3</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">366</i>人报名</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-85072.html" title="百点学苑——肢体语言解码1" target="_blank"><img src="http://web.img.chuanke.com/course/2013-01/16/04033fc671e70b3a76b4a5580bb9b3d6.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-85072.html" title="百点学苑——肢体语言解码1" target="_blank">百点学苑——肢体语言解码1</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">276</i>人报名</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-90925.html" title="【创业思维】菜鸟该如何创业？" target="_blank"><img src="http://web.img.chuanke.com/course/2013-07/08/cacc5da67645b04c82cb0cb487e57d4f.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-90925.html" title="【创业思维】菜鸟该如何创业？" target="_blank">【创业思维】菜鸟该如何创业？</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">245</i>人报名</p>
									</dd>
								</dl>
							</li>
													</ul>
						<ul id="collect_top_list" style="display:none;">
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-90925.html" title="【创业思维】菜鸟该如何创业？" target="_blank"><img src="http://web.img.chuanke.com/course/2013-07/08/cacc5da67645b04c82cb0cb487e57d4f.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-90925.html" title="【创业思维】菜鸟该如何创业？" target="_blank">【创业思维】菜鸟该如何创业？</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">6</i>人收藏</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-75824.html" title="【创业预备课】VIP课程 原价1578 现价219 绝对超值优惠" target="_blank"><img src="http://web.img.chuanke.com/course/2013-03/10/1f30e9f0e569a73db3a8d29d97e6739a.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-75824.html" title="【创业预备课】VIP课程 原价1578 现价219 绝对超值优惠" target="_blank">【创业预备课】VIP课程 原价1578 </a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">3</i>人收藏</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-91195.html" title="【创业思维】如何选择好项目？" target="_blank"><img src="http://web.img.chuanke.com/course/2013-07/15/c59b7fb2fc490479431dfe7d01d0244d.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-91195.html" title="【创业思维】如何选择好项目？" target="_blank">【创业思维】如何选择好项目？</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">3</i>人收藏</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-75602.html" title="【创业必修课】之《如何沟通与说服》系列课程" target="_blank"><img src="http://web.img.chuanke.com/course/2013-01/02/a9212e15c9d6e8007c2945cc4e1d2962.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-75602.html" title="【创业必修课】之《如何沟通与说服》系列课程" target="_blank">【创业必修课】之《如何沟通与说服</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">3</i>人收藏</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-75610.html" title="《创业预备课》之《经验分享》" target="_blank"><img src="http://web.img.chuanke.com/course/2013-01/04/15b87138f81e1b0a01979bc319c73431.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-75610.html" title="《创业预备课》之《经验分享》" target="_blank">《创业预备课》之《经验分享》</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">3</i>人收藏</p>
									</dd>
								</dl>
							</li>
														<li>
								<dl>
									<dt>
										<a href="http://www.chuanke.com/1019545-90986.html" title="【创业思维】如何研究你的客户？" target="_blank"><img src="http://web.img.chuanke.com/course/2013-07/08/e0245da93087ea52c11e6d918cb5eb30.jpg" width="60" height="45"></a>
									</dt>
									<dd>
										<p class="c_555"><a href="http://www.chuanke.com/1019545-90986.html" title="【创业思维】如何研究你的客户？" target="_blank">【创业思维】如何研究你的客户？</a></p>
										<p class="c_999 mt5">已有<i class="arial c_4b9a03">3</i>人收藏</p>
									</dd>
								</dl>
							</li>
													</ul>
					</div>
					<footer class="ft">
						<a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545" class="more" target="_blank">查看更多课程</a>
					</footer>
				</div>
				
								
				
				<div class="bdSort">
					<header class="hd">
						<h4 class="fl mt5 ml10">课程分类</h4>
					</header>
					<div class="bd">
						<ul id="custom_type_list">
														<li class="">
								<div class="c_types">
									<span class="c_t_zk type_switch"></span>
									<a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=814">创业必修课</a>
								</div>
																<dl>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=832">沟通与说服</a></dd>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=818">肢体语言解码</a></dd>
																	</dl>
															</li>
														<li class="last">
								<div class="c_types">
									<span class="c_t_zk type_switch"></span>
									<a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=819">创业预备课</a>
								</div>
																<dl>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=831">创业思维</a></dd>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=820">创新思维</a></dd>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=834">推销与谈判</a></dd>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=822">如何有效促销</a></dd>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=823">采购技巧</a></dd>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=824">经验分享</a></dd>
																		<dd><a href="http://www.chuanke.com/?mod=school&act=show&do=course&sid=1019545&typeid=825">答疑课</a></dd>
																	</dl>
															</li>
													</ul>
					</div>
				</div>				
							</div>
			<div class="sm_main fr">
				<nav class="bdTabs">
					<ul id="tab_switch">
						<li class="curr" tab="brief">课程简介</li>
						<li tab="comment">课程评价</li>
						<li tab="student">学生</li>
						<li tab="teacher">老师</li>
						<li tab="courseware">课件</li>
					</ul>
				</nav>
				<div class="bdBox" id="box_content"></div>
			</div>
		</section>
	</div>
	
	<!-- 超出直播人数上限弹出框  -->
	<div id="over_live_limit" style="display:none;">
		<p class="lh25 mt30 tc vm f14 c_777 mb10">
			<img src="http://res.ckimg.com/sites/www/v2/images/public/ico_warning_24x25.png" alt=""> 
			<span>该课程的购买人数已超过其直播教室容量。</span>
		</p>
		<div class="c_999 pl30 pr30 lh25 mb20">
			<p class="pl30">上直播课时如果教室内人数已满，后来者将无法进入教室听课。你是否继续购买？</p>
		</div>
		<p class="tc">
			<a id="confirm_live_limit" href="http://www.chuanke.com/?mod=order&act=create&do=confirm&ids=MTAxOTU0NTo4ODU0Mw==" class="c_btn30 mr20"><span>继续购买</span></a>
			<a href="javascript:;" class="c_btn30" id="cancel_live_limit"><span>取 消</span></a>
		</p>
	</div>
	
	<!-- 超出直播人数上限弹出框  -->
	<div id="over_live_limit_cart" style="display:none;">
		<p class="lh25 mt30 tc vm f14 c_777 mb10">
			<img src="http://res.ckimg.com/sites/www/v2/images/public/ico_warning_24x25.png" alt=""> 
			<span>该课程的购买人数已超过其直播教室容量。</span>
		</p>
		<div class="c_999 pl30 pr30 lh25 mb20">
			<p class="pl30">上直播课时如果教室内人数已满，后来者将无法进入教室听课。你是否确定加入购物车？</p>
		</div>
		<p class="tc">
			<a id="confirm_live_limit_cart" href="javascript:;" class="c_btn30 mr20"><span>加入购物车</span></a>
			<a href="javascript:;" class="c_btn30" id="cancel_live_limit_cart"><span>取 消</span></a>
		</p>
	</div>	
<script src="js/show.js"></script>
<script type="text/javascript">
var courseid = document.getElementById("courseid").value;
var Course = eval('({\"id\":courseid,\"SID\":\"1019545\",\"Type\":\"72366561234518016\",\"Status\":\"1\",\"SaleStatus\":\"2\",\"Display\":\"1\",\"StudentNumber\":\"166\",\"TrialNumber\":\"0\",\"AppraiseGoodCount\":\"156\",\"AppraiseMiddleCount\":\"0\",\"AppraiseBadCount\":\"0\",\"CourseInfoVer\":\"7\",\"ClassListVer\":\"5\",\"ClassHour\":\"1\",\"Cost\":\"1900\",\"ClassBuyFlag\":\"1\",\"ClassCost\":\"0\",\"ExpiryTime\":\"1688140799\",\"PayEndTime\":\"1688140799\",\"AssureTimeLength\":\"1296000\",\"PayStudentLimit\":\"1000\",\"LiveStudentLimit\":\"1000\",\"CourseName\":\"\\u767e\\u70b9\\u5b66\\u82d1-\\u63a8\\u9500\\u4e0e\\u8c08\\u52249\",\"PhotoURL\":\"\\/course\\/2013-04\\/23\\/6ac863909fadfc808c0ae5339ca1fecc.jpg\",\"Brief\":\"<p>\\n\\t<img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/a988c5d945c75bdadf4c9d28abcc3b41.jpg\\\" alt=\\\"\\\" \\/><a href=\\\"http:\\/\\/www.chuanke.com\\/1019545-75602.html\\\" target=\\\"_blank\\\"><\\/a><a href=\\\"http:\\/\\/www.chuanke.com\\/1019545-75835.html\\\" target=\\\"_blank\\\"><a href=\\\"http:\\/\\/www.chuanke.com\\/1019545-75878.html\\\" target=\\\"_blank\\\"><img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/de3053e96a7f30e5b30d7dd2f383ded5.jpg\\\" alt=\\\"\\\" \\/><\\/a><\\/a><img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/c28178062d2b6c56260486b09bb22d33.jpg\\\" alt=\\\"\\\" \\/><span style=\\\"color:#555555;font-family:tahoma, arial, \\u5b8b\\u4f53;\\\"><\\/span><img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/cad85228588463d9647e34632aca9fb4.jpg\\\" alt=\\\"\\\" \\/><a href=\\\"http:\\/\\/www.bydey.com\\/forum.php?mod=viewthread&tid=8998\\\" target=\\\"_blank\\\"><img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/f19b986239d8e8c1dba7b67e0104994f.jpg\\\" alt=\\\"\\\" \\/><\\/a><img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/333ba8c41c730eb9deddcc0ff7288989.jpg\\\" alt=\\\"\\\" \\/><a href=\\\"http:\\/\\/www.chuanke.com\\/s1019545.html\\\" target=\\\"_blank\\\"><img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/f61ac3f0e228d3038ce91215f084d054.jpg\\\" alt=\\\"\\\" \\/><\\/a><img src=\\\"http:\\/\\/web.img.chuanke.com\\/resource\\/db9e231f5afc38169239db6c50165608.jpg\\\" alt=\\\"\\\" \\/> \\n<\\/p>\",\"KeyWords\":\"\",\"Courseware\":\"\",\"CollectNumber\":\"0\",\"CreateTime\":\"1366713228\",\"TotalAppraise\":156,\"GoodRate\":\"100\"})');

$(function(){
	//自定义分类
	$(".type_switch").toggle(
		function(){
			$(this).removeClass("c_t_zk").addClass("c_t_gb");
			$(this).parent().next().slideUp("fast");
		},
		function(){
			$(this).removeClass("c_t_gb").addClass("c_t_zk");
			$(this).parent().next().slideDown("fast");
		}
	);	
});
</script>
	<footer class="footer">
		<nav class="ft_nav">
			<a target="_blank" href="http://www.chuanke.com/about">关于传课</a> 
		    <a target="_blank" href="http://www.chuanke.com/help">传课帮助</a> 
		    <a target="_blank" href="http://www.chuanke.com/feedback">意见反馈</a> 
		    <a target="_blank" href="http://www.chuanke.com/contactus">联系方式</a> 
		    <a target="_blank" href="http://www.chuanke.com/job">招聘信息</a>
		</nav>
		<div class="copyright">
			<p class="info">
				<span class="mr10">京ICP证<a target="_blank" href="http://www.miitbeian.gov.cn">130265</a>号</span>
				<span class="mr10">Copyright &copy; 2013 </span>
				<span>传课 <a href="http://www.chuanke.com/" target="_blank">Chuanke.com</a></span>
			</p>
		</div>
	</footer>
</body>
</html>

<script language="javascript">var _ck_p = 6;</script><script src="http://res.ckimg.com/sites/stat/ckweb.js" language="javascript"></script>
<script language="javascript">var _cid = 6;</script><script src="js/ckhit.js" language="javascript"></script>
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-33765729-1']);
  _gaq.push(['_setDomainName', 'chuanke.com']);
  _gaq.push(['_trackPageview']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script> 