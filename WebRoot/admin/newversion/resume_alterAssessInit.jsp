<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>简历管理_我的智联_智联招聘</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/myzhaopin.css">
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/subnav_resumes.css">
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/function.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/utilScript.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/myresume_popupdiv.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/New_v3/formValidator.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/New_v3/ajaxbase.js">;</script>
<script language="javascript" type="text/javascript">
<!--
var helpURL = 'http://interface.zhaopin.com/help/help.asp?MID=22500';
var custom_commenttitle = new formEle(true,'text3','title',['请填写自定义标题','您输入的文字超过20个汉字或40个英文字符和标点符号'],null,{length:40,condition:'document.frmMain.commenttitle.value==1'});
var comment = new formEle(true,'text','comment',['请填写自我评价','自我评价内容过长'],null,{length:250});

function saveResume(){
	delInvisiChar(document.frmMain.comment);
	if(!document.frmMain.checkForm()) return;

	document.frmMain.submit();
}

function commentTitle(s){
	var t = document.frmMain.custom_commenttitle;
	if(s && t) if(s.value=='1'){t.style.display='';}
	else {t.style.display='none';}
	//t.value='';
}

String.prototype.Trimall = function() { return this.replace(/\s/g,""); }
function calWordNumRemained(max,o,show1,show2,inputname){
	if(o){
	    show1.style.display = "inline";
	    var minlen = o.value.Trimall().length;
		var l=o.value.length;
		var r=parseInt(max-l);
		if(show1 && show2){
			show1.innerHTML='已输入'+ l +'个字';
			show1.className='org12';
			show2.innerHTML = "，最多可输入" + max + "个字";
			document.getElementById(inputname + "Img").style.display = "inline";
			document.getElementById(inputname + "Img").style.marginLeft = "6px";
			document.getElementById(inputname + "Div").innerHTML='总共输入了'+l+'个字（其中可见字数为'+ minlen + "个，不可见字数为" + (l-minlen) + "个）"

		}
	}
}
function iniWordNum(show1,show2,txt,inputname){
	if(show1 && show2){
		show1.style.display = "none";
		document.getElementById(inputname + "Img").style.display = "none";
		show2.innerHTML=txt;
	}
}
function ajaxReturn(success,responseText){
	if(success){
		if(responseText!=''){
			var expi = new Date( new Date().getTime() + (1000 * 365 * 24 * 60 * 60) );
			document.cookie=  'bbll=' + escape(responseText) + '; expires=' + expi.toGMTString() + '; path=/' + '; domain=zhaopin.com';
		}
	}
	else{// something went wrong with the AJAX callback
	}
}


//-->
</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/analytics.js"></script><script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/za/ga.js"></script>

</head>

<body>
<!-- popupDiv eg -->
<div id="eg1" class="popupDiv" style="width:250px;visibility:hidden;position:absolute;top:-100px;left:-100px;" onClick="event.cancelBubble=true;">
	<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterWhite" style="width:234px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
	<div class="content"><div style="padding:0 10px 5px 10px;line-height:150%;">
		<div><b>社会简历</b></div>
		<div>资深市场销售人员，六年销售和市场领域从业经验，年销售额过一千万；长期接触国外客户，英语能力强；对市场营销、 渠道开发及经销商管理有丰富的经验</div><br>
		<div><b>学生简历</b></div>
		<div>两年学生会主席生涯，培养了较强的组织能力和领导能力。曾在课余时间，参与过网站开发项目，能够较熟练地使用Photoshop，同时积累了一定的团队合作经验。</div></div>
	</div>
	<div class="bottomLeft"><img src="" width="1" height="1"></div><div class="bottomCenter" style="width:234px;"><img src="" width="1" height="1"></div><div class="bottomRight"><img src="" width="1" height="1"></div>
</div>
<div id="eg2" class="popupDiv" style="width:250px;visibility:hidden;position:absolute;top:-100px;left:-100px;" onClick="event.cancelBubble=true;">
	<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterWhite" style="width:234px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
	<div class="content"><div style="padding:0 10px 5px 10px;line-height:150%;">
		<div><b>社会简历</b></div>
		<div>3年数据库管理员经验，熟悉Oracle数据库体系的备份与恢复；2年的网络信息系统管理经验，熟练掌握UNIX操作系统及网络管理技术；长期供职于外企，英语听说能力强。</div><br>
		<div><b>学生简历</b></div>
		<div>曾担任外联社社长一职，负责学校12个大小型活动的赞助商招揽，并成功筹集活动资金。具备良好的沟通能力和组织领导能力。</div></div>
	</div>
	<div class="bottomLeft"><img src="" width="1" height="1"></div><div class="bottomCenter" style="width:234px;"><img src="" width="1" height="1"></div><div class="bottomRight"><img src="" width="1" height="1"></div>
</div>
<div id="eg3" class="popupDiv" style="width:250px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;" onClick="event.cancelBubble=true;">
	<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterWhite" style="width:234px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
	<div class="content">
	    <div style="padding:0 10px 5px 10px;line-height:150%;">
		<div><b>字数统计</b></div>
		<div id="eg3Div"></div><br>
		</div>
	</div>
	<div class="bottomLeft"><img src="" width="1" height="1"></div>
	<div class="bottomCenter" style="width:234px;"><img src="" width="1" height="1"></div>
	<div class="bottomRight"><img src="" width="1" height="1"></div>
</div>
<!-- end popupDiv eg -->

<link href="http://my.zhaopin.com/css/new_v5/my_nav.css" type="text/css" rel="stylesheet" />
<link href="http://img00.zhaopin.cn/2012/css/my/v5/common.css" type="text/css" rel="stylesheet" />
<style>
#hd2011cityMoreBox h6 
{
    font-size:12px; 
    font-weight:400;   
}
input 
{
   border:1px solid #ccc;
}
body
{
    color:#000;
    }
.round_box{ position:static}
</style>
<link href="http://img00.zhaopin.cn/2012/css/reset.min.css" type="text/css" rel="stylesheet" />   
<style>
#globalHeader,#globalHeader .site-logo,#globalHeader .nav-bar li,#globalHeader .nav-bar .l,#globalHeader .nav-bar .r,#globalHeader .nav-bar em:hover a,#globalHeader .nav-bar .mycurrent a{background-image:url(http://img00.zhaopin.com/new2011/head/images/spri.gif);}
#globalHeader{height:63px;background-position:0 -111px;background-repeat:repeat-x;font-family:simsun;}
#globalHeader .hd-wrapper{width:990px;margin:0 auto;position:relative;}
#globalHeader .site-logo,#globalHeader .mobile-page,#globalHeader .hotline,#globalHeader .choosecity,#globalHeader .companyregin{position:absolute;color:#155fc9;font-size:12px;text-decoration:none;line-height:25px;top:5px;}
#globalHeader .mobile-page,#globalHeader #person-service,#globalHeader .hotline,#globalHeader .choosecity,#globalHeader .regin,#globalHeader .companyregin{background:url(http://img01.zhaopin.cn/new2011/head/images/headerbg0719.png) left center no-repeat;padding-left:10px;}
#globalHeader .site-logo{width:165px;height:56px;top:0;left:30px;}
#globalHeader .companyregin{right:290px;color:#e56100;font-weight:bold;}
#globalHeader .hotline{right:156px;color:#848484;background:none;padding:0;}
#globalHeader .choosecity{right:87px;}
#globalHeader .mobile-page{right:20px}
#globalHeader .nav-bar{position:absolute;right:0;top:37px;font-size:14px;}
#globalHeader .nav-bar li{float:left;height:26px;background-position:154px -177px;overflow:hidden;width:90px}
#globalHeader .nav-bar li.minwidth{width:60px}
#globalHeader .nav-bar em{display:block;margin-top:2px;margin-left:0!important;;}
#globalHeader .nav-bar em a,#globalHeader .nav-bar em span{float:left;height:25px;}
#globalHeader .nav-bar .nav-first{background-position:0 -175px;width:3px;}
#globalHeader .nav-bar .nav-last{background-position:-155px -175px;width:3px;}
#globalHeader .nav-bar a{padding:0 12px;color:white;text-decoration:none;line-height:23px;font-weight:bold;}
#globalHeader .nav-bar a:hover{color:#d7e6fc;}
#globalHeader .nav-bar .l,#globalHeader .nav-bar .r{width:3px;background-position:154px -179px;}
#globalHeader .nav-bar .mycurrent a,#globalHeader .nav-bar .mycurrent a:hover,#globalHeader .nav-bar .mycurrent em:hover a{background-position:-4px -203px;color:#06c;}
#globalHeader .nav-bar .mycurrent .l,#globalHeader .nav-bar .mycurrent em:hover .l{background-position:0 -203px;}
#globalHeader .nav-bar .mycurrent .r,#globalHeader .nav-bar .mycurrent em:hover .r{background-position:-155px -203px;}
#globalHeader .nav-bar em:hover .l{background-position:0px -306px;}
#globalHeader .nav-bar em:hover .r{background-position:-155px -306px;}
#globalHeader .nav-bar em:hover a{background-position:-4px -306px;}
#globalHeader a.citymap{background:url(http://img03.zhaopin.cn/new2011/head/images/gnav960.gif) no-repeat;width:75px;height:20px;display:block;margin:4px 5px 0 5px;text-indent:-999em;padding:0;overflow:hidden;}
#globalHeader .citymap:hover{background-position:0 -21px;}
</style>
<div id="globalHeader"><div class="hd-wrapper"><a href="http://www.zhaopin.com/" class="site-logo" title="智联招聘首页"></a><a href="http://hr.zhaopin.com/" class="companyregin" target="_blank" onclick="recordOutboundLink(this,'addnewlink','compuser')">企业用户</a><span class="hotline">服务热线&nbsp;400-885-9898</span><a href="http://www.zhaopin.com/citymap.html" onclick="recordOutboundLink(this,'addnewlink','choosecity')" target="_blank" class="choosecity">选择城市</a><a href="http://images.zhaopin.com/2012/other/mobile/mobile.html" target="_blank" class="mobile-page" onclick="recordOutboundLink(this,'addnewlink','mobilejob')">手机求职</a><div class="nav-bar"><ul><li class="nav-first"></li><li class="minwidth"><em><span class="l"></span><a href="http://www.zhaopin.com/">首页</a><span class="r"></span></em></li><li class="mycurrent"><em><span class="l"></span><a href="http://my.zhaopin.com/">简历中心</a><span class="r"></span></em></li><li><em><span class="l"></span><a href="http://sou.zhaopin.com/">职位搜索</a><span class="r"></span></em></li><li><em><span class="l"></span><a href="http://student.zhaopin.com/" target="_blank">校园招聘</a><span class="r"></span></em></li><li><em><span class="l"></span><a href="http://edu.zhaopin.com/"  target="_blank" onclick="recordOutboundLink(this,'addnewlink','educchannal')">智联教育</a><span class="r"></span></em></li><li><em><span class="l"></span><a href="http://www.zhaopin.com/jobseeker/index_industry.html"  target="_blank">行业求职</a><span class="r"></span></em></li><li><em><span class="l"></span><a href="http://whitecollar.zhaopin.com/">高端职位</a><span class="r"></span></em></li><li><em><span class="l"></span><a href="http://article.zhaopin.com/">求职指导</a><span class="r"></span></em></li><li class="minwidth"><em><span class="l"></span><a href="http://wendao.zhaopin.com/" target="_blank">问道</a><span class="r"></span></em></li><li class="nav-last"></li></ul></div></div></div>
<div class="nav_listmain">
   <div class="nav_title">
       <div class="nav_list nav_a">简历管理</div>
	   <div class="nav_list_icon1"></div>
   </div>
   <div class="nav_list_content"><a target="_top" class="emailBiao" href="http://my.zhaopin.com/myzhaopin/resume_list.asp">简历管理</a></div>
   <div class="nav_list_content"><a target="_top" class="emailQian" href="http://my.zhaopin.com/myzhaopin/resume_hits.asp">谁看了简历</a></div>
   <div class="nav_list_content"><a target="_top" class="resumes" href="http://my.zhaopin.com/myzhaopin/job_letter.asp">求职信管理</a></div>
</div>
<div class="nav_listmain_1">
   <div class="nav_title">
       <div class="nav_list nav_a">申请与反馈</div>
	   <div class="nav_list_icon1"></div>
   </div>
   <div class="nav_list_content"><a target="_top" href="http://my.zhaopin.com/myzhaopin/jobmng_applied.asp">职位申请记录</a></div>
   <div class="nav_list_content"><a target="_top" href="http://my.zhaopin.com/myzhaopin/jobmng_maillist.asp">人事经理来信</a></div>
</div>
<div id="hd2011mainNav2">
	<div id="hd2011mainNav2-Box">
		<ul>
			<li><a href="http://my.zhaopin.com/myzhaopin/resume_index.asp">我的智联</a></li>
			<li id="resume_li_mng" >
			   <div class="navBody1">
			     <div class="nav_list">简历管理</div>
		         <div class="nav_list_icon"></div>
			   </div>
			</li>
			<li><a href="http://my.zhaopin.com/myzhaopin/job_searcher.asp">搜索与订阅</a></li>
			<li><a href="http://my.zhaopin.com/myzhaopin/jobmng_fav.asp">职位收藏夹</a></li>
			<li id="apply_li">
		        <div class="navBody2">
					<div class="nav_list">申请与反馈</div>
					<div class="nav_list_icon"></div>
				</div>
		    </li>
			<li><a href="http://i.zhaopin.com/Recommend/JobRecommend/History">职位推荐</a></li>
		</ul>
		<div class="hd201_listBody">
			<a href="http://i.zhaopin.com/Extend/jobequipment/index" target="_black"><img src="http://i.zhaopin.com/Content/images/freeicon.png" class="free-icon" />求职装备></a>
			<a href="http://article.zhaopin.com/payquery/index.do" target="_black">薪酬报告></a>
			<a href="http://ceping.zhaopin.com" target="_black">职业测评></a>
		</div>
	</div>
</div>
<script src="http://images.zhaopin.com/new2011/lib/jquery.min.js" type="text/javascript"></script>
<script  type="text/javascript">
// 主导航hover样式
$("#hd2011mainNav li a").hover(function() {
	if(!$(this).hasClass('hd2011current'))$(this).addClass("hd2011onhover");
},function (){
	$(this).removeClass("hd2011onhover");
});

// 选择城市
$('#hd2011citySelect').click(function(){
	$('#hd2011cityMoreBox').show().mouseleave(function(){$(this).hide();});
});
$('.hd2011btnMoreCity').click(function(){
	$('#hd2011cityMoreBox').show().mouseleave(function(){$(this).hide();});
});

$(function(){
    var arr1 = $(".nav_listmain a");
    var arr2 = $(".nav_listmain_1 a");
    var arr3 = $("#hd2011mainNav2 a");
    for(i in arr1){
        arr1[i].href = arr1[i].href + "?r=" +Math.random();
    }
    for(i in arr2){
        arr2[i].href = arr2[i].href + "?r=" + Math.random();
    }
    for(i in arr3){
        arr3[i].href = arr3[i].href + "?r=" + Math.random();
    }
})


</script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/my/v5/resume_foot.js"></script>
<!-- end head -->

<div id="mainContainer">
<div class="layout">
	<div class="left">
  		<div class="leftRow">
        	<h2><span>我的简历</span></h2>
            <div class="leftRowCon">
            	<ul class="leftRowA">
                	<li>&#38144;&#21806;&#19994;&#21153; 3&#24180; &#24503;&#24030;</li>
                    <li><a href="editName.asp?ext_id=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1" title="修改简历名称">修改名称</a>&nbsp;<a href="resume_publish.asp?ext_id=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1" title="刷新您的简历，使简历排序靠前并更容易的被企业浏览到！">刷新</a>&nbsp;<a href="#" onClick="openPopup('resume_preview.asp?ext_id=JR475973035R90250006000&resume_id=206642320&Version_Number=1&language_id=1','preview',700,800);return false;" title="预览简历">预览</a></li>
                </ul>
            </div>
            <div class="row1Bot"><img src="http://myimg.zhaopin.com/images/new_v4/row1BL.gif" width="4" height="4" alt="" /><img src="http://myimg.zhaopin.com/images/new_v4/row1BR.gif" width="4" height="4" alt="" style="float:right" /></div>
    	</div>
    		<!-- 简历内容 -->
	<div id="resumeInfo" class="leftRow"><!-- style="width:156px;" -->
		<h2><span>简历内容</span></h2>
		<div class="leftRowCon">
			<ul class="leftRowB">
<li class="ok"><a href="/myzhaopin/resume_contactinfo_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">个人信息</a></li>
<li class="ok"><a href="/MYZHAOPIN/EditComm.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">自我评价</a></li>
<li class="ok"><a href="/MYZHAOPIN/EditInts.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">求职意向</a></li>
<li class="ok"><a href="/MYZHAOPIN/resume_experience_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">工作经验</a></li>
<li class="ok"><a href="/MYZHAOPIN/resume_education_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">教育背景</a></li>
<li class="blank"><a href="/MYZHAOPIN/resume_training_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">培训经历</a></li>
<li class="ok"><a href="/MYZHAOPIN/EditLagu.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">语言能力</a></li>
<li class="blank"><a href="/MYZHAOPIN/job_letter.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">求职信</a></li>
<li class="blank"><a href="/MYZHAOPIN/editcert.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">证书</a></li>
<li class="blank"><a href="/MYZHAOPIN/editatta_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">照片/附件</a></li>
<li class="blank"><a href="/MYZHAOPIN/resume_others_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">其他信息</a></li>

			</ul>
		</div>
        <div class="row1Bot"><img src="http://myimg.zhaopin.com/images/new_v4/row1BL.gif" width="4" height="4" alt="" /><img src="http://myimg.zhaopin.com/images/new_v4/row1BR.gif" width="4" height="4" alt="" style="float:right" /></div>
	</div>
	<!-- end 简历内容 -->
	<!-- 人才附加内容 -->
	<div id="appendInfo" class="leftRow"><!-- style="width:156px;" -->
		<h2><span>人才附加内容</span></h2>
		<div class="leftRowCon">
			<ul class="leftRowB">
<li class="blank"><a href="/MYZHAOPIN/editproj.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">项目经验</a></li>
<li class="blank"><a href="/MYZHAOPIN/editskil.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">专业技能</a></li>
<li class="optional"><a href="/MYZHAOPIN/editscho.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">在校学习情况</a></li>
<li class="optional"><a href="/MYZHAOPIN/editprac.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">在校实践经验</a></li>

			</ul>
		</div>
        <div class="row1Bot"><img src="http://myimg.zhaopin.com/images/new_v4/row1BL.gif" width="4" height="4" alt="" /><img src="http://myimg.zhaopin.com/images/new_v4/row1BR.gif" width="4" height="4" alt="" style="float:right" /></div>
	</div>
	<!-- end 人才附加内容 --
	<!-- 图例说明 -->
	<div id="chartInfo" class="leftRow">
		<h2><span>图例说明</span></h2>
		<div class="leftRowCon">
			<ul class="leftRowC">
				<li class="ok">已填写</li>
				<li class="error">不完整</li>
				<li class="blank">未填写</li>
				<li class="optional">选填项</li>
                <div class="clear"></div>
			</ul></div>
            <div class="row1Bot"><img src="http://myimg.zhaopin.com/images/new_v4/row1BL.gif" width="4" height="4" alt="" /><img src="http://myimg.zhaopin.com/images/new_v4/row1BR.gif" width="4" height="4" alt="" style="float:right" /></div>
	</div>
	<!-- end 图例说明 -->

	</div>
	<div class="right">

        <div id="resumeInfo">
		<h2><span>当前简历完整度：<font class='org12'>★</font><font class='org12'>★</font><font class='org12'>★</font><font class='grey12'>★</font><font class='grey12'>★</font></span>自我评价</h2>

        <div class="rightRow1">智联建议您对自己做一个简短评价，简明扼要地描述您的职业优势，让用人单位快速地了解您！<br>优秀的自我评价可以吸引招聘人员的眼球，为您的简历增色不少！（<span class="org14">*</span>为必填项）</div>
		<form name="frmMain" action="resume_alterAssess.action" method="post">
		<input type="hidden" name="recruit.id" value="${recruit.id }"/>
		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="table5">
		<colgroup><col width="1"><col width="110"><col></colgroup>
		<tr><td><font class="org14">*</font></td>
			<td>标题：</td>
			<td><select name="recruit.biaoti" onChange="commentTitle(this)" style="float:left;margin-right:8px;">
			<option value="自我评价" name="recruit.biaoti" <s:if test='recruit.biaoti=="自我评价"'>selected</s:if> >自我评价</option>
			<option value="职业目标" name="recruit.biaoti" <s:if test='recruit.biaoti=="职业目标"'>selected</s:if>>职业目标</option>
			<option value="1" name="recruit.biaoti" <s:if test='recruit.biaoti=="1"'>selected</s:if>>自定义标题</option>
			</select> <input type="text" name="custom_commenttitle" value="" size="73" style="display:none;float:left;" maxlength="100" id="custom_commenttitle" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td></tr>
		<tr id="conError_title" style="display:none;"><td></td><td></td><td id="txtError_title"></td></tr>
		<tr><td valign="top"><font class="org14">*</font></td>
			<td valign="top">内容：</td>
			<td><textarea name="recruit.neirong" rows="12" cols="95" onBlur="submitCallback('comment='+ua(document.frmMain.comment.value),'usermaster_blacklists_save.asp?iChecked=0&iBlocked=0',ajaxReturn,'post','');iniWordNum(document.getElementById('maxWord1'),document.getElementById('maxWord2'),'限250字以内','eg3')" onFocus="calWordNumRemained(250,this,document.getElementById('maxWord1'),document.getElementById('maxWord2'),'eg3')" onKeyUp="calWordNumRemained(250,this,document.getElementById('maxWord1'),document.getElementById('maxWord2'),'eg3')" id="comment" mzpmodule="resumeChEnFac" tiptext="" lang="en">${recruit.neirong }</textarea></td></tr>
		<tr><td></td>
			<td></td>
			<td><span class="grey12">填写文字在100个字以上评定等级，少于不计算，内容越详细，等级越高<br>范例一：<a href="#" onMouseOver="stopTimeG();showPopup('eg1',event,getXY(document.getElementById('eg1Img')).x+12,getXY(document.getElementById('eg1Img')).y+10);" onMouseOut="startTimeG();" onClick="return false;"><img src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" border="0" align="absmiddle" id="eg1Img"></a>&nbsp;&nbsp;范例二：<a href="#" onMouseOver="stopTimeG();showPopup('eg2',event,getXY(document.getElementById('eg2Img')).x+12,getXY(document.getElementById('eg2Img')).y+10);" onMouseOut="startTimeG();" onClick="return false;"><img src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" border="0" align="absmiddle" id="eg2Img"></a> &nbsp;&nbsp;&nbsp;（<SPAN id=maxWord1 style="DISPLAY: none"></SPAN><SPAN id=maxWord2>限250个字</SPAN><A onMouseOver="stopTimeG();showPopup('eg3',event,getXY(document.getElementById('eg3Img')).x+12,getXY(document.getElementById('eg3Img')).y+10)" onClick="return false;" onmouseout=startTimeG(); href="http://my.zhaopin.com/myzhaopin/resume_baseinfo.asp?ext_id=JR028855777R90000009000&amp;resume_id=3430176&amp;Version_Number=1&amp;language_id=1&amp;LocationUrl=resume_list&amp;DYWE=1228439254234.89987.1229671288.1229909284.33#"><IMG id=eg3Img src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" align=absMiddle border=0 style="display:none"></A>）</span></td></tr>
		<tr id="conError_comment" style="display:none;"><td></td><td></td><td id="txtError_comment"></td></tr>
		</table>
		<div class="btnCon">
		<input type="submit" class="btn7" value="保存" > &nbsp;&nbsp;<a href="resume_alterInit.action?recruit.id=${recruit.id }">返回</a>
		</div>
		</form>
		<script language="javascript" type="text/javascript">
		<!--
		iniCheckForm('frmMain');

		function fnBlurCustomcommen(){
					if(custom_commenttitle.s!=null && custom_commenttitle.s>=0) custom_commenttitle.fnValidate();
				}
		myAttachEvent(document.frmMain.commenttitle,'change',fnBlurCustomcommen);
		-->
		</script>
		<div id="helpTxt" class="row3b">
        	<h3><span>自我评价撰写技巧</span></h3>
            	<ul class="row3bCon">
				<li>社会简历</li>
                </ul>
                <ul class="decimal">
					<li>详细罗列出您所拥有的特长、技能和经验，以及您在以前的工作中累积了的优势。您可以根据招聘方的招聘信息，有针对性地罗列，让人事经理更好地了解到您与所申请职位的符合程度。</li>
					<li>用尽可能简要且平实的语句概括描述出您的特长、技能、经验及优势。语言不可过于口语化，内容须实事求是，不可夸张。</li>
					<li>避免提到您的业余爱好、抱负、对公司的感想等；避免空泛、感性、老套的话。</li></ul>

            	<ul class="row3bCon"><li>学生简历</li></ul>
                <ul class="decimal">
					<li>详细罗列出您所拥有的专长、技能和实践经验，以及您在校期间经过锻炼培养出来的能力。您可以根据招聘方的招聘信息，有针对性地罗列，让人事经理更好地了解到您与所申请职位的符合程度。</li>
					<li>用尽可能简要且平实的语句概括描述出您的专长、技能、实践经验及能力。语言不可过于口语化，内容须事实求是，不可夸张。</li>
					<li>避免提到您的业余爱好、抱负、对公司的感想等；避免空泛、感性、老套的话。</li></ul>
		</div>
        <div class="row1Bot"><img src="http://myimg.zhaopin.com/images/new_v4/row1BL.gif" width="4" height="4" alt="" /><img src="http://myimg.zhaopin.com/images/new_v4/row1BR.gif" width="4" height="4" alt="" style="float:right" /></div>

	</div>

    </div>
</div>
</div>

<div class="clear"></div>
<script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnUtil.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnResumeChEn.js"></script>
</body>
</html>
