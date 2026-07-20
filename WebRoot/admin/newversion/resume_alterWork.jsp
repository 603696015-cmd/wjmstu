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
<link rel="stylesheet" href="http://img00.zhaopin.cn/2012/css/ui/xw_selectcity/resume_style.css" />
<script type="text/javascript" src="http://my.zhaopin.com/js/analytics.js"></script><script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/za/ga.js"></script>

</head>
<body>
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
		<h2><span>当前简历完整度：<font class='org12'>★</font><font class='org12'>★</font><font class='org12'>★</font><font class='grey12'>★</font><font class='grey12'>★</font></span>求职意向</h2>

		<!--div class="editResumeDes">（<span class="org14">*</span>为必填项）</div-->
		<form name="frmMain" action="resume_alterWork.action" method="post">
		<input name="recruit.id" value="${recruit.id }" type="hidden"/>

		<!-- popupDiv jobtype -->
		<div id="popupDiv_jobtypeF" class="popupDiv" style="width:600px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;" onClick="event.cancelBubble=true;">
			<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterOrg" style="width:584px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
			<div class="title"><div style="width:584px;">
					<div style="float:left;">&nbsp;&nbsp;&nbsp;<b class="blue14">职位类别：</b>最多可选 <span class="org14">5</span> 项</div><!--<div style="float:right;">[ <span class="blue12" onClick="hideCurrentPopup()" style="cursor:hand;">关闭</span> ]&nbsp;</div>--></div>
			</div>
			<div class="content"><div style="width:564px;padding:10px 10px 5px 10px;">
					<div><select name="pSel_jobtypeF" id="pSel_jobtypeF"></select></div>
					<div id="itemDiv_jobtypeF"></div>
					<div id="cacheItemDiv_jobtypeF"></div><br>
					<div align="center"><input type="button" value="确 定" class="btn3" name="buttonSave_jobtypeF" id="buttonSave_jobtypeF">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="取 消" class="btn3" onClick="hideCurrentPopup();"></div></div>
			</div>
			<div class="bottomLeft"><img src="" width="1" height="1"></div><div class="bottomCenter" style="width:584px;"><img src="" width="1" height="1"></div><div class="bottomRight"><img src="" width="1" height="1"></div>
		</div>
		<!-- end popupDiv jobtype -->

		<!-- popupDiv industry -->
		<div id="popupDiv_industryF" class="popupDiv" style="width:550px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;" onClick="event.cancelBubble=true;">
			<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterOrg" style="width:534px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
			<div class="title"><div style="width:534px;">
					<div style="float:left;">&nbsp;&nbsp;&nbsp;<b class="blue14">行业类别：</b></div><!--<div style="float:right;">[ <span class="blue12" onClick="hideCurrentPopup()" style="cursor:hand;">关闭</span> ]&nbsp;</div>--></div>
			</div>
			<div class="content"><div style="width:514px;padding:10px 10px 5px 10px;">
					<div id="itemDiv_industryF"></div><br>
					<div align="center"><input type="button" value="确 定" class="btn3" name="buttonSave_industryF" id="buttonSave_industryF">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="取 消" class="btn3" onClick="hideCurrentPopup();"></div></div>
			</div>
			<div class="bottomLeft"><img src="" width="1" height="1"></div><div class="bottomCenter" style="width:534px;"><img src="" width="1" height="1"></div><div class="bottomRight"><img src="" width="1" height="1"></div>
		</div>
		<!-- end popupDiv industry -->

		<!-- popupDiv location -->
		<div id="popupDiv_locationF" class="popupDiv" style="width:540px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;display:none" onClick="event.cancelBubble=true;">
			<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterOrg" style="width:524px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
			<div class="title"><div style="width:524px;">
					<div style="float:left;">&nbsp;&nbsp;&nbsp;<b class="blue14">工作地点：</b>最多可选 <span class="org14">5</span> 项</div><!--<div style="float:right;">[ <span class="blue12" onClick="hideCurrentPopup()" style="cursor:hand;">关闭</span> ]&nbsp;</div>--></div>
			</div>
			<div class="content"><div style="width:504px;padding:10px 10px 5px 10px;">
					<div><select name="pSel_locationF" id="pSel_locationF"><option value=489 selected>全国</option><option value=530>&nbsp;&nbsp;--北京</option><option value=538>&nbsp;&nbsp;--上海</option><option value=548>&nbsp;&nbsp;--广东</option><option value=531>&nbsp;&nbsp;--天津</option><option value=546>&nbsp;&nbsp;--湖北</option><option value=556>&nbsp;&nbsp;--陕西</option><option value=552>&nbsp;&nbsp;--四川</option><option value=535>&nbsp;&nbsp;--辽宁</option><option value=536>&nbsp;&nbsp;--吉林</option><option value=539>&nbsp;&nbsp;--江苏</option><option value=544>&nbsp;&nbsp;--山东</option><option value=540>&nbsp;&nbsp;--浙江</option><option value=549>&nbsp;&nbsp;--广西壮族自治区</option><option value=541>&nbsp;&nbsp;--安徽</option><option value=532>&nbsp;&nbsp;--河北</option><option value=533>&nbsp;&nbsp;--山西</option><option value=534>&nbsp;&nbsp;--内蒙古自治区</option><option value=537>&nbsp;&nbsp;--黑龙江</option><option value=542>&nbsp;&nbsp;--福建</option><option value=543>&nbsp;&nbsp;--江西</option><option value=545>&nbsp;&nbsp;--河南</option><option value=547>&nbsp;&nbsp;--湖南</option><option value=550>&nbsp;&nbsp;--海南</option><option value=551>&nbsp;&nbsp;--重庆</option><option value=553>&nbsp;&nbsp;--贵州</option><option value=554>&nbsp;&nbsp;--云南</option><option value=555>&nbsp;&nbsp;--西藏自治区</option><option value=557>&nbsp;&nbsp;--甘肃</option><option value=558>&nbsp;&nbsp;--青海</option><option value=559>&nbsp;&nbsp;--宁夏回族自治区</option><option value=560>&nbsp;&nbsp;--新疆维吾尔自治区</option><option value=561>&nbsp;&nbsp;--香港特别行政区</option><option value=562>&nbsp;&nbsp;--澳门特别行政区</option><option value=563>&nbsp;&nbsp;--台湾</option><option value="481">阿根廷</option><option value="482">澳大利亚</option><option value="483">奥地利</option><option value="484">白俄罗斯</option><option value="485">比利时</option><option value="486">巴西</option><option value="487">保加利亚</option><option value="488">加拿大</option><option value="490">塞浦路斯</option><option value="491">捷克</option><option value="492">丹麦</option><option value="493">埃及</option><option value="494">芬兰</option><option value="495">法国</option><option value="496">德国</option><option value="497">希腊</option><option value="498">匈牙利</option><option value="499">冰岛</option><option value="500">印度</option><option value="501">印度尼西亚</option><option value="502">爱尔兰</option><option value="503">以色列</option><option value="504">意大利</option><option value="505">日本</option><option value="506">韩国</option><option value="507">科威特</option><option value="508">马来西亚</option><option value="509">荷兰</option><option value="510">新西兰</option><option value="511">挪威</option><option value="513">巴基斯坦</option><option value="514">波兰</option><option value="515">葡萄牙</option><option value="516">俄罗斯联邦</option><option value="517">沙特阿拉伯</option><option value="518">新加坡</option><option value="519">南非</option><option value="520">西班牙</option><option value="521">瑞典</option><option value="522">瑞士</option><option value="523">泰国</option><option value="524">土耳其</option><option value="525">乌克兰</option><option value="526">阿联酋</option><option value="527">英国</option><option value="528">美国</option><option value="529">越南</option><option value="913">安哥拉</option><option value="914">加纳</option><option value="915">尼日利亚</option><option value="916">坦桑尼亚</option><option value="917">乌干达</option><option value="918">阿尔及利亚</option><option value="919">塞内加尔</option><option value="512">其他</option></select></div>
					<div id="itemDiv_locationF"></div>
					<div id="cacheItemDiv_locationF"></div><br>
					<div align="center"><input type="button" value="确 定" class="btn3" name="buttonSave_locationF" id="buttonSave_locationF">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="取 消" class="btn3" onClick="hideCurrentPopup();"></div></div>
			</div>
			<div class="bottomLeft"><img src="" width="1" height="1"></div><div class="bottomCenter" style="width:524px;"><img src="" width="1" height="1"></div><div class="bottomRight"><img src="" width="1" height="1"></div>
		</div>
		<!-- end popupDiv location -->

		<!-- 求职意向 -->
		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="table5 table5_new">
		<colgroup><col width="1"><col width="110"><col></colgroup>
		<tr><td colspan="3" class="editResumeDes" style="padding-bottom:12px;">（<span class="org14">*</span>为必填项）</td></tr>
		<tr><td><font class="org14">*</font></td>
			<td width="140">期望工作性质</td>
			<td>
<input type="CheckBox" name="recruit.xingzhi" value="2" <s:if test='recruit.xingzhi==2'>CHECKED</s:if> >全职
<input type="CheckBox" name="recruit.xingzhi" value="1" <s:if test='recruit.xingzhi==1'>CHECKED</s:if>>兼职
<input type="CheckBox" name="recruit.xingzhi" value="4" <s:if test='recruit.xingzhi==4'>CHECKED</s:if>>实习
			</td></tr>
		<tr id="conError_employment" style="display:none;"><td></td><td></td><td id="txtError_employment"></td></tr>
		<tr><td><font class="org14">*</font></td>
			<td>期望工作地点</td>
			<td><input type="button" class="selectBut2" name="button_locationF" id="button_locationF" value="选择/修改" onFocus="this.blur()"><img src="" width="1" height="1" id="popupDivImg_locationF"><select name="cache_locationF" id="cache_locationF" style="display:none;"></select><input type="hidden" name="desired_City" value="530" id="desired_City"></td></tr>
		<tr><td></td>
			<td></td>
			<td><span id="selItem_locationF" style="line-height:150%;"></span></td></tr>
		<tr id="conError_city" style="display:none;"><td></td><td></td><td id="txtError_city"></td></tr>
		<tr><td><font class="org14">*</font></td>
			<td>期望从事职业</td>
			<td><input type="button" class="selectBut2" name="button_jobtypeF" id="button_jobtypeF" value="选择/修改" onFocus="this.blur()"><img src="" width="1" height="1" id="popupDivImg_jobtypeF"><select name="cache_jobtypeF" id="cache_jobtypeF" style="display:none;"></select><input type="hidden" name="desired_Jobtype" value="4010200"></td></tr>
		<tr><td></td>
			<td></td>
			<td><span id="selItem_jobtypeF" style="line-height:150%;"></span></td></tr>
		<tr id="conError_jobtype" style="display:none;"><td></td><td></td><td id="txtError_jobtype"></td></tr>
		<tr><td><font class="org14">*</font></td>
			<td>期望从事行业</td>
			<td><input type="button" class="selectBut2" name="button_industryF" id="button_industryF" value="选择/修改" onFocus="this.blur()"><img src="" width="1" height="1" id="popupDivImg_industryF"><select name="cache_industryF" id="cache_industryF" style="display:none;"></select><input type="hidden" name="desired_Industry" value="140200"></td></tr>
		<tr><td></td>
			<td></td>
			<td><span id="selItem_industryF" style="line-height:150%;"></span></td></tr>
		<tr id="conError_industry" style="display:none;"><td></td><td></td><td id="txtError_industry"></td></tr>
		<tr><td><font class="org14">*</font></td>
			<td>期望月薪(税前)</td>
			<td><select name="recruit.yuexin">
			<option value="">请选择</option>
			
<option value="1000" <s:if test='recruit.yuexin=="1000"'>selected="selected"</s:if>>1000元/月以下</option>
<option value="2000" <s:if test='recruit.yuexin=="2000"'>selected="selected"</s:if>>1000-2000元/月</option>
<option value="4000" <s:if test='recruit.yuexin=="4000"'>selected="selected"</s:if>>2001-4000元/月</option>
<option value="6000" <s:if test='recruit.yuexin=="6000"'>selected="selected"</s:if>>4001-6000元/月</option>
<option value="8000" <s:if test='recruit.yuexin=="8000"'>selected="selected"</s:if>>6001-8000元/月</option>
<option value="10000" <s:if test='recruit.yuexin=="10000"'>selected="selected"</s:if>>8001-10000元/月</option>
<option value="15000" <s:if test='recruit.yuexin=="15000"'>selected="selected"</s:if>>10001-15000元/月</option>
<option value="25000" <s:if test='recruit.yuexin=="25000"'>selected="selected"</s:if>>15000-25000元/月</option>
<option value="199999" <s:if test='recruit.yuexin=="199999"'>selected="selected"</s:if>>25000元/月以上</option>
<option value="0000" <s:if test='recruit.yuexin=="0000"'>selected="selected"</s:if>>面议</option>
			</select></td></tr>
		<tr id="conError_salary" style="display:none;"><td></td><td></td><td id="txtError_salary"></td></tr>
		<tr><td></td><td></td>
			<td class="tab5Td2">
			<br><input type="Radio" name="recruit.status" value="1" <s:if test='recruit.status==1'>CHECKED</s:if> >我目前处于离职状态，可立即上岗<br>
			<input type="Radio" name="recruit.status" value="2" <s:if test='recruit.status==2'>CHECKED</s:if>>我目前在职，正考虑换个新环境（如有合适的工作机会，到岗时间一个月左右）<br>
			<input type="Radio" name="recruit.status" value="3" <s:if test='recruit.status==3'>CHECKED</s:if>>目前暂无跳槽打算<br>
			<input type="Radio" name="recruit.status" value="4" <s:if test='recruit.status==4'>CHECKED</s:if>>我对现有工作还算满意，如有更好的工作机会，我也可以考虑。（到岗时间另议）<br>
			<input type="Radio" name="recruit.status" value="5" <s:if test='recruit.status==5'>CHECKED</s:if>>应届毕业生
			</td></tr>
		</table>
        <div class="rightRow3a"><input type="checkbox" name="showInit" value="1" checked='checked'>将此求职意向显示在我的简历中</div>
		<!-- end 求职意向 -->
		<div class="btnCon">
		<input type="submit" class="btn7" value="保存" > &nbsp;&nbsp;<a href="resume_alterInit.action?recruit.id=${recruit.id }">返回</a>
		</div>
		</form>
		
	</div>
    </div>
</div>
</div>

<div class="clear"></div>
<script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

<script type="text/javascript" src="http://img01.zhaopin.com/myzhaopin/js/function.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.com/myzhaopin/js/utilscript.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.com/myzhaopin/js/myresume_popupdiv.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/jquery.zlzp.popupbase.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/arrdata.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/basedata.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/xw_selectcity/resume_selectCity.js"></script>
<script type="text/javascript">
    arrJobtype = arrJobtype.concat(arrSubjobtype);
</script>
<script type="text/javascript">
    // 职位大类数据填充
    var selJobtype = document.getElementById('pSel_jobtypeF');
    selJobtype.options.length = 0;
    for (var i = 0; i < arrJobtype.length; i++){
        if (arrJobtype[i][1] === '0'){
            selJobtype.options.add(new Option(arrJobtype[i][2],arrJobtype[i][0]));
        }
    }
</script>
<script type="text/javascript" src="http://img01.zhaopin.com/myzhaopin/js/new_v3/buttondivcheckbox.js"></script>
<script type="text/javascript" src="http://img02.zhaopin.com/myzhaopin/js/new_v3/formvalidator.js"></script>
<script type="text/javascript">
<!--
var helpURL = 'http://interface.zhaopin.com/help/help.asp?MID=22500';

var employment_type = new formEle(true,'checkbox','employment',['请选择期望工作性质']);
var desired_Jobtype = new formEle(true,'text','jobtype',['请选择期望从事职业'],null,{o4focus:'document.getElementById("button_jobtypeF")'});
var desired_Industry = new formEle(true,'text','industry',['请选择期望从事行业'],null,{o4focus:'document.getElementById("button_industryF")'});
var desired_City = new formEle(true,'text','city',['请选择期望工作地点'],null,{o4focus:'document.getElementById("button_locationF")'});
var expected_salary = new formEle(true,'select','salary',['请选择期望月薪']);
$(function(){
			$("#button_locationF").selectCity({
				   title:"工作地点",
			       outInput:"selItem_locationF",
				   hiddenInput:"desired_City",
				   oversea:arrOversea,
				   limit:5
			   });
});
function save(){
	if(!document.frmMain.checkForm()) return;
	document.frmMain.submit();

}
//-->
</script>
<script language="javascript" type="text/javascript">
    <!--
    //var locationF = new lib_popupDivC('locationF','location','document.frmMain.desired_City','arrCity',null,'writeDivItem','clickCheckbox','clickCheckboxP','clickCheckboxC','showItem','saveCity')
    //locationF.config = {x:-160,y:5,col:5,max:5};
    //eval('locationF.'+locationF.showSelected);
    var jobtypeF = new lib_popupDivC('jobtypeF','jobtype','document.frmMain.desired_Jobtype','arrJobtype',null,'writeDivItem','clickCheckbox','clickCheckboxP','clickCheckboxC','showItem')
    jobtypeF.config = {x:-160,y:5,col:3,max:5};
    eval('jobtypeF.'+jobtypeF.showSelected);
    var industryF = new lib_popupDivC('industryF','industry','document.frmMain.desired_Industry','arrIndustry',null,'writeDivItem','clickCheckbox',null,null,'showItem')
    industryF.config = {x:-160,y:5,col:2,max:999};
    eval('industryF.'+industryF.showSelected);
    //locationF.op4Child();
    //-->
</script>
<script language="javascript" type="text/javascript">
<!--
    iniCheckForm('frmMain');

    function hideCurrentPopup(){
                        if(window.currentlyVisiblePopup){
                            changeObjectVisibility(window.currentlyVisiblePopup, 'hidden');

                            switch(window.currentlyVisiblePopup){
                                case 'popupDiv_jobtypeF' : desired_Jobtype.fnValidate();break;
                                case 'popupDiv_industryF' : desired_Industry.fnValidate();break;
                                case 'popupDiv_locationF' : desired_City.fnValidate();break;
                            }

                            window.currentlyVisiblePopup = false;
                        }
                    }
    myAttachEvent(document,'click',hideCurrentPopup);
-->
</script>
</body>
</html>
