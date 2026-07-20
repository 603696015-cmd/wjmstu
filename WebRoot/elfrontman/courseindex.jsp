<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String cltypeId = "";
	if (request.getAttribute("course") != null) {
		cltypeId = ((Course) request.getAttribute("course"))
				.getCtype().getId()+ "";
	}else{
		cltypeId = "1";
	} 
%>

<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv = "X-UA-Compatible" content = "IE=7,IE=9"/>
	<title>[网络课程大全]-传课网</title>
	<meta name="keywords" content="网络课程大全,网络教育,精品课程，传课网" />
	<meta name="description" content="传课为你提供国内最全的网络教育课程，所有课程均来自教育战线的优秀老师,拥有丰富经验的行业精英,学有所成的学术带头人，拥有特殊技能的各界人士。传课帮助你快速成长。" />
	<link rel="shortcut icon" type="/image/x-icon" href="http://www.chuanke.com/favicon.ico" />
	<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_base.css" media="screen">
	<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_list.css" media="screen">
	<script src="http://res.ckimg.com/lib/jquery/jquery.js"></script>
	<script src="http://res.ckimg.com/lib/jquery/plugin/jquery.common.js"></script>
	<script src="http://res.ckimg.com/common/v2/js/functions.js"></script>
	<script src="http://res.ckimg.com/common/v2/js/kk_header.js"></script>
	<script src="http://res.ckimg.com/sites/uc/v1/js/jquery.mailAutoComplete-4.0.js"></script>
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
					searchUrl = "http://www.chuanke.com/course/index.html";	
				}else{
					searchUrl = "http://www.chuanke.com/course/_"+encodeURIComponent(keywords)+"____.html";
				}
			}else{
				searchUrl = "http://www.chuanke.com/?mod=search&act=school&keyword="+encodeURIComponent(keywords);
			}
			document.location.href = searchUrl;
			return false;
		});

				KK.Suggest.Install('head_searchKeywords', 'search_suggestion');
				
		if(typeof KK.user.username != 'undefined'){
			var bfx_username = KK.user.username;
		}		
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
<body class="ck_list">
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
	<div style="position: fixed;_position:absolute;top: 50%; z-index: 599; right: 0px;" class="rTop">
		<ul>
			<li class="r_weixin">				
				<div class="codeImgWrap" id="weixinCodeImg" style="background-color:#FFFFFF;display: none;">
					<a href="javascript:;" id="weixinClose" class="close"></a>
					<p class="c_f60 tc mt20">轻松一扫，精彩不停</p>
					<div class="codeImg"></div>
				</div>
				<a href="javascript:;" style="display: block;" id="btnWeixin" class="wx item">微信关注</a>
			</li>
			
			<li class="r_msg">
				<a href="javascript:;" id="btnFeedback" class="item">意见反馈</a>
			</li>
			<li class="r_gotop" style="display: none;">
				<a href="#" id="btnGotop" class="item">返回顶部</a>
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
			$("li.r_gotop").fadeIn(750);
		}else{
			$("li.r_gotop").fadeOut(750);
		}
	});
	addListener(document, "mouseover",function(evt) {
	    var evt = window.event ? window.event: evt,
	    target = evt.srcElement || evt.target;
	    if(target.id == "btnWeixin") {
	    	$("#btnWeixin").animate({left: '-66px'}, "fast");   
	        return;
	    }else if(target.id == "btnFeedback"){
	    	$("#btnFeedback").animate({left: '-66px'}, "fast");   
	        return;
	    }else if(target.id == "btnGotop"){
	    	$("#btnGotop").animate({left: '-66px'}, "fast");   
	        return;
	    }
	});
	addListener(document, "mouseout",function(evt) {
	    var evt = window.event ? window.event: evt,
	    target = evt.srcElement || evt.target;
	    if(target.id == "btnWeixin") {
	    	$("#btnWeixin").stop(true).animate({left: '0px'}, "fast");   
	        return;
	    }else if(target.id == "btnFeedback"){
	    	$("#btnFeedback").stop(true).animate({left: '0px'}, "fast");   
	        return;
	    }else if(target.id == "btnGotop"){
	    	$("#btnGotop").stop(true).animate({left: '0px'}, "fast");   
	        return;
	    }
	}); 
	var url = window.location.href;
	var	reg = /chuanke.com\/#?$/;
	
	if(url.search(reg) != -1){
		$("#weixinCodeImg").show();
		$("#btnWeixin").hide();
	} 
	$("#weixinClose").click(function(){
		$("#weixinCodeImg").hide();
		$("#btnWeixin").css("display","block");		
	});
	$("#btnWeixin").click(function(){
		$("#btnWeixin").css("display","none");
		$("#weixinCodeImg").show();
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
<li><a target="_blank" href="http://www.chuanke.com/course/72354505127100416_____.html">英语</a></li>
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
				<li >
					<a href="/course/__prelectstarttime_asc__.html" target="_blank">今日直播</a>
				</li>
			</ul>
		</div>
		<div class="srh_mzmy">
			<p>
				<span><a href="http://mind.chuanke.com/?p=672" target="_blank">加入传课交流群 与他人知识共享</a></span>			</p>
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
	<div class="listContent">
		<nav class="listCrumb">
			<div class="fl mCrumb">
				<span class="s"><a href="/">首页</a><i>&gt;</i></span>
								<span class="s here c_f60">所有分类<i>(5316)</i></span>
							</div>
		</nav>
		<!-- =S listMain -->
		<section class="listMain mb30">
						<div class="lSortBox mb10">
				<div class="s_cate_list">
					<ul style="overflow:hidden">
												<li><a href="http://www.chuanke.com/course/72363258270449664_____.html">中小学</a>（1663）</li>
												<li><a href="http://www.chuanke.com/course/72351163642544128_____.html">IT/计算机/互联网</a>（1085）</li>
												<li><a href="http://www.chuanke.com/course/72365457293705216_____.html">考试考级</a>（654）</li>
												<li><a href="http://www.chuanke.com/course/72364357782077440_____.html">生活/文艺/兴趣类</a>（555）</li>
												<li><a href="http://www.chuanke.com/course/72354462177427456_____.html">语言技能</a>（308）</li>
												<li><a href="http://www.chuanke.com/course/72355561689055232_____.html">其他</a>（301）</li>
												<li><a href="http://www.chuanke.com/course/72366556805332992_____.html">营销类</a>（287）</li>
												<li><a href="http://www.chuanke.com/course/72362158758821888_____.html">职场</a>（263）</li>
												<li><a href="http://www.chuanke.com/course/72367656316960768_____.html">通用技能</a>（113）</li>
												<li><a href="http://www.chuanke.com/course/72348964619288576_____.html">金融管理类</a>（87）</li>
											</ul>
				</div>
			</div>
			
			<div class="lSelectBar mb5">
			</div>
			
			<section class="litemBox mb30">
				<div class="s_result_list">
										<ul>
										<s:iterator value="zxCourses">
												<li>
							<a href="http://www.chuanke.com/1262740-89072.html" class="pic" target="_blank"><img src="http://web.img.chuanke.com/course/2013-05/16/00452bb3a3794a41aafc9fb43af00d87.jpg" /></a>
							<div class="s_result_con ml15">
								<h3><a href="getCourseIndexview.action?course.id=<s:property value="id"/>&ctype=<s:property value="ctype.id"/>" target="_blank">${name }<em class='c_f60'></em></a></h3>
																<p class="c_555 pt10"><a href="http://www.chuanke.com/s1262740.html" target="_blank">${description}<em class='c_f60'></em></a>&nbsp;</p>
																<p class="c_555 pt5" title="信用值：1283">学校信用：<img border="0" src="http://res.ckimg.com/sites/www/v2/images/public/level/s23.gif" /></p>
															</div>
							<div class="s_result_con s_result_price">
								<p><b class="f16 c_f60 mr5">0.00</b>元</p>
								<p class="pt10">共${during }课时</p>
							</div>
							<div class="s_result_con s_result_info">
								<p class="pt5">319人购买</p>
								<p class="pt10 c_777">186 条评价</p>
							</div>
						</li>
						</s:iterator>
												
												
												
									
					</ul>
									</div>
			</section>

						<div class="ck_page cb tr">
				<span class='curr'> 1 </span><a title='第2页' href='http://www.chuanke.com/course/index.html?page=2' class='ajaxLink'>2</a><a title='第3页' href='http://www.chuanke.com/course/index.html?page=3' class='ajaxLink'>3</a><a title='第4页' href='http://www.chuanke.com/course/index.html?page=4' class='ajaxLink'>4</a><a title='第5页' href='http://www.chuanke.com/course/index.html?page=5' class='ajaxLink'>5</a><span class='ellipsis'>...</span><a title='第532页' href='http://www.chuanke.com/course/index.html?page=532' class='ajaxLink'>532</a><a title='下一页' href='http://www.chuanke.com/course/index.html?page=2'  class='next ajaxLink'>下一页<s></s></a><div class="pageNum"><span>到第<input type="text" class="ipt1" name="page" id="jumpPage" url="http://www.chuanke.com/course/index.html?page=" maxpage="532">页<input type="button" class="ipt2" value="" id="jumpBtn"></span></div>			</div>
					</section>
		<!-- =E listMain -->

		<!-- =S listAside -->
		<aside class="listAside mb20">
			<div class="asideC">
				<header class="hd mb5">
					<h4>课程推荐</h4>
				</header>
				<ul class="bd" style="width:132px;">
					<s:iterator value="tjCourses">
						<li>
						<a target="_blank" href="http://www.chuanke.com/1002127-96095.html"><img src="http://web.img.chuanke.com/fragment/85e44e3fb3b30fa7f8228b3c1f7fc4ea.jpg" alt="巧记韩语单词"></a>
						<p class="mb5 pt10"><a target="_blank" href="http://www.chuanke.com/1002127-96095.html">${name }</a></p>
						<p>价格：￥<i class="c_f60 arial">0</i></p>
						</li>	
					</s:iterator>
				</ul>
			</div>
					</aside>
		<s class="clear"></s>		
	</div>
	<!-- =E listContent -->
<script>
$(function(){
	var cost_focus = 0;
	
	$("#cost_range").mouseover(function(){
		cost_focus = 1;
	}).mouseout(function(){
		cost_focus = 0;
	});
	
	$(".price-field").focus(function(){
		$("#cost_search").show();
	}).blur(function(){
		if (cost_focus == 0) {
			$("#cost_search").hide();
		}
	});

	$("#cost_search").click(function(){
		var minprice = $("#minprice").val();
		var maxprice = $("#maxprice").val();

		if ((minprice && !/^\d+$/.test(minprice)) || (maxprice && !/^\d+$/.test(maxprice))){
			$.ckAlert({message:'价格格式填写不正确，只能为整数'});
			return false;
		}
		if(minprice == '' && maxprice == ''){
			var url = "/course/index.html";
		}else{
			var url = "/course/____";
			url = url + minprice + '_'+maxprice+'.html';
		}
		location.href = url;
		return false;
	});

	$("#jumpPage").keydown(function(event){
		if(event.keyCode == 13){
			$("#jumpBtn").trigger('click');
		}
	});

	$("#jumpBtn").click(function(){
		var page = parseInt($("#jumpPage").val());
		if (!/^\d+$/.test(page)){
			return false;
		}
		
		var maxpage = parseInt($("#jumpPage").attr("maxpage"));
		if (page < 1 || page > maxpage) {
			return false;
		} else {
			location.href=$("#jumpPage").attr('url')+page;
		}
	});
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
				<span class="mr10">Copyright &copy; 2014 </span>
				<span>传课 <a href="http://www.chuanke.com/" target="_blank">Chuanke.com</a></span>
			</p>
		</div>
	</footer>
</body>
</html>

<script language="javascript">var _ck_p = 3;</script><script src="http:http://www.chuanke.com//res.ckimg.com/sites/stat/ckweb.js" language="javascript"></script>
<script language="javascript">var _cid = 3;</script><script src="http:http://www.chuanke.com//res.ckimg.com/sites/stat/ckhit.js" language="javascript"></script>
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
 