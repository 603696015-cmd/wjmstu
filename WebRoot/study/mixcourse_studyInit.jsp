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
	<title>课程学习-传课网</title>
	<meta name="keywords" content="传课，KK，新思诺，英语，在线课堂，远程教育，在线学习，视频课程，公开课，电子商务，信息分享，职场培训，直播课堂，免费听课，名师网络课程，名师网课，B2B" />
		<meta name="description" content="传课，中国最大的网络课程在线分享平台。您可在线传授技能，亦可在线学习你所需的一技之长。内容涉及英语学习、职场培训、生活技巧等任何技能。"/>
	<link rel="shortcut icon" type="/image/x-icon" href="/favicon.ico" />
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
 
</head>
<body class="ck_detail" onkeyup="return registerHotKey(event);">
		<input type="hidden" id="courseid" value="${course.id }">
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
</script>	
<div class="siteNav">
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
					<a href="/jineng" target="_blank">技能专区</a>
				</li>
				<li >
					<a href="/k12" target="_blank">中小学</a>
				</li>
				<li >
					<a href="/kaoshi" target="_blank">考试考级</a>
				</li>
				<li >
					<a href="/xingqu" target="_blank">生活文艺</a>
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
	<div class="detailContent">
 
	<!-- =S w_ksxx -->
	<section class="w_ksxx">
		<div class="cla_info clearfix">
			<div class="pic">
				<a target="_blank" href="http://www.chuanke.com/1032537-91646.html">
					<img width="181" height="141" alt="商务法语" src="http://web.img.chuanke.com/course/2013-07/29/d2da09865915b8974d36a5bb2d9c1b28.jpg">
				</a>
			</div>
			<div class="content">
				<div class="title yahei"><a target="_blank" href="http://www.chuanke.com/1032537-91646.html">
					<s:property value="course.name"/></a></div>
				<dl>
					<dd>总课时：<s:property value="course.during" />课时</dd>
					<dd>结束时间：<s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" /> （剩余<span class="c_f60 fb">369</span>天<a style="top:-2px;_top:0px;_left:5px" target="_blank" class="i_help ml5 mr5 pr" href="http://wenda.chuanke.com/?/question/id-3273__item_id-2039__rf-false"></a>）</dd>
					<dd>已有<span class="c_4b9a03 fb"><s:property value="usercount"/></span>名同学在和你一起学习</dd>
					<dd>学校：<a target="_blank" href="http://www.chuanke.com/s1032537.html">阿浪天涯法语工作室</a></dd>
				</dl>
			</div>
			<div class="ks_tr">
				<a class="i_share bdshare_b" id="bdshare" href="javascript:;" data="{'url':'http://www.chuanke.com/1032537-91646.html'}"></a>
				<!-- Baidu Button BEGIN -->
				<script type="text/javascript" id="bdshare_js" data="type=button&amp;uid=688952;" ></script>
				<script type="text/javascript" id="bdshell_js"></script>
				<script type="text/javascript">
					document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
					//在这里定义bds_config
					var bds_config = {'snsKey':{'tsina':'2463745741','qzone':'100262522','tqq':'801145774','renren':'5e8042ac9ceb446baaf09f4d80fe039a'},'bdText':'我在 @传课网 看到了一个非常不错的课程：《商务法语》，大家也来听听课吧。网址：'};
				</script>
				<!-- Baidu Button END -->
			</div>
		</div>
		
		
		<nav class="bdTabs">
			<ul id="tab_switch">
				<li class="curr" tab="course">课程</li>
				<li tab="stuff">随堂课件</li>
			</ul>
		</nav>
 		<div class="bdBox" id="box_content"></div>
	</section>
	<!-- =E w_ksxx -->
 
	</div>
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
 <script src="js/sopia.js"></script>
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
<script language="javascript">var _ck_p = 6;</script><script src="http://res.ckimg.com/sites/stat/ckweb.js" language="javascript"></script>
<script language="javascript">var _cid = 6;</script><script src="http://res.ckimg.com/sites/stat/ckhit.js" language="javascript"></script>
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
<script src="http://res.ckimg.com/sites/stat/ckpage.js" language="javascript"></script> 
<script> 
var IsTrail = "0",
	CourseID = "91646",
	SID = "1032537",
	CourseClassList = eval({"126382":{"Num":"\u7b2c1\u7ae0\u7b2c1\u8282","IsToday":"","Date":"","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed  \u7cfb\u5217\u4e00"},"127097":{"Num":"\u7b2c1\u7ae0\u7b2c2\u8282","IsToday":"","Date":"","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u4e8c"},"127869":{"Num":"\u7b2c1\u7ae0\u7b2c3\u8282","IsToday":"","Date":"","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u4e09"},"128470":{"Num":"\u7b2c1\u7ae0\u7b2c4\u8282","IsToday":"","Date":"","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u56db"},"129172":{"Num":"\u7b2c1\u7ae0\u7b2c5\u8282","IsToday":"","Date":"","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u4e94"},"129685":{"Num":"\u7b2c1\u7ae0\u7b2c6\u8282","IsToday":"","Date":"","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u516d"},"130286":{"Num":"\u7b2c1\u7ae0\u7b2c7\u8282","IsToday":"","Date":"","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u4e03"},"131167":{"Num":"\u7b2c1\u7ae0\u7b2c8\u8282","IsToday":false,"Date":"10\u670808\u65e5 20:00\u4e0a\u8bfe","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u516b"},"131353":{"Num":"\u7b2c1\u7ae0\u7b2c9\u8282","IsToday":false,"Date":"10\u670815\u65e5 20:00\u4e0a\u8bfe","Status":"\u672a\u5b66\u4e60","ClassName":"\u5546\u52a1\u6cd5\u8bed \u7cfb\u5217\u4e5d"}}),
	TipsWidth = 52;
</script>
<script src="http://www.chuanke.com/js/student/study.js"></script> 
