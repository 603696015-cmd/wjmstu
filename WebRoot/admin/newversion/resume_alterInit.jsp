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
<!--<link type="text/css" rel="stylesheet" href="/css/new_v3/myzhaopin.css"> -->
<script language="javascript" type="text/javascript">
var helpURL = 'http://interface.zhaopin.com/help/help.asp?MID=22500';
</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/function.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/utilScript.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/analytics.js"></script><script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/za/ga.js"></script>

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
		<h2>我的简历：&#38144;&#21806;&#19994;&#21153; 3&#24180; &#24503;&#24030;</h2>
    	<div class="rightRow1">
        	<p>申请职位数：<em>0</em>&nbsp;|&nbsp;<span title="简历被企业搜索并打开浏览的次数">被浏览次数：</span><em>0</em>&nbsp;<!--|&nbsp;被下载次数：<em>0</em>--><br /><a href="resume_add_language.asp?ext_id=JR475973035R90250006000&Language_ID=2&Resume_ID=206642320&Version_Number=1">填写英文简历</a></p>简历更新日期：2014-1-15 14:37:42<br />开放方式：开放<br />简历等级：<font class='org12'>★</font><font class='org12'>★</font><font class='org12'>★</font><font class='grey12'>★</font><font class='grey12'>★</font>（<span class="org">★</span>&nbsp;<a href="#" class="black" onClick="openPopup('/help/helprank.htm','help',520,520);return false;">是什么？</a>）
						<p style="display:none; padding-left:10px; text-align:left"><br /><a href="#" onClick="window.location='editatta_edit.asp?resume_id=206642320&ext_id=JR475973035R90250006000&version_number=1&language_id=1';return false;"><img src="http://myimg.zhaopin.com/images/new_v3/bluedot.gif" border="0" hspace="5">修改照片</a></p><p><s:property value="recruit.elUser.sex"/>  | <s:property value="recruit.hunyinzhuangkuang"/> |  <s:property value="recruit.elUser.shengri"/>   | 户口：<s:property value="recruit.hukou"/> | 现居住于<s:property value="recruit.juzhuchengshi"/><br> 3年工作经验  | <s:property value="recruit.elUser.zhengzhimianmao"/>  | <s:property value="recruit.elUser.zhengjianleixing"/>： <s:property value="recruit.elUser.zhengjianhao"/><br>  <span style='display:none;'><br> </span><s:property value="recruit.elUser.movephone"/>(<s:property value="recruit.lianxifangshi"/>)<br> E-mail: <a href='mailto:liguanglongvip@126.com'><s:property value="recruit.elUser.email"/></a> <br><a href="resume_alterUserInfoInit.action?recruit.id=${recruit.id }"  style="display:">修改个人信息</a></p><h3><s:property value="recruit.elUser.realname"/></h3>
			<div class="clear"></div>
		</div>
        <div class="rightRow2">
       		<!-- resume item:奥运经验 -->
			<!--BEGIN_olympic-->
			<div style="display:none">
				<h3><a href="#" onClick="window.location='olympic_edit.asp?resume_id=206642320&ext_id=JR475973035R90250006000&version_number=1&language_id=1';return false;" style="display:">[修改]</a>奥运服务经验</h3>
				<p></p>
			</div>
			<!--END_show_olympic-->
			<!-- end resume item:奥运经验 -->
          
          	<!-- resume item:求职意向 -->
			
			<div style="display:">
				<h3><a href="resume_alterWorkInit.action?recruit.id=${recruit.id }"  >[修改]</a>求职意向</h3>	
				<table width="740" border="0" cellpadding="0" cellspacing="0" class="table3">
				<colgroup><col width="110" /><col width="630" /></colgroup>		
                <tr><td align='right' nowrap='nowrap'>期望工作性质：</td><td align='left'><s:property value="recruit.xingzhi"/></td></tr>
				<tr><td align='right' nowrap='nowrap' style='padding-top:2px;'>期望从事职业：</td><td align='left'><s:property value="recruit.zhiye"/></td></tr>
				<tr><td align='right' nowrap='nowrap' valign='top' style='padding-top:2px;'>期望从事行业：</td><td align='left'><s:property value="recruit.hangye"/></td></tr>
				<tr><td align='right' nowrap='nowrap'>期望工作地区：</td><td align='left'><s:property value="recruit.didian"/></td></tr>
				<tr><td align='right' nowrap='nowrap'>期望月薪：</td><td align='left'><s:property value="recruit.yuexin"/></td></tr>
				<tr><td width='1%' align='right' nowrap='nowrap'>目前状况：</td><td align='left'><s:property value="recruit.status"/></td></tr>
				</table>
			</div>
			
			<!-- end resume item:求职意向 -->
            <!-- <th>期望从事职业：</th><td>销售总监</td> -->
            
            <!-- resume item:自我评价 -->
			
			<div style="display:">
				<h3><a href="resume_alterAssessInit.action?recruit.id=${recruit.id }"  style="display:">[修改]</a><s:property value="recruit.biaoti"/></h3>	
				<p><div class='resume_p'><s:property value="recruit.neirong"/></div></p>
			</div>
			
			<!-- end resume item:自我评价 -->
            <!-- <p>具有良好的职业道德和素养，认真细致、思路清晰、责任心强。</p> -->
            
            <!-- resume item:工作经验 -->
			
			<div id="itemWorkexpe" style="display:">
				<h3><a href="resume_alterWorkExpInit.action?recruit.id=${recruit.id }"  style="display:">[修改]</a>工作经验</h3>
				
				<table width="740" border="0" cellpadding="0" cellspacing="0" class="table3">
            	<colgroup><col width="130" /><col width="632" /></colgroup>
            	<s:iterator value="experiences">
                <tr><td colspan="2">
                <!-- 工作经验 item1 -->
				<table cellpadding='0' cellspacing='0' border='0'><tr><td width='1%' nowrap='nowrap' valign='top'><s:property value="workstartdate"/> -- <s:property value="workenddate"/>：</td><td class='line150' style='width:462px;word-wrap:break-word' align='left'><s:property value="companyname"/> | <s:property value="bumen"/> | <s:property value="zhiweimingcheng"/><br><s:property value="hangyeleibie"/> | <s:property value="xingzhi"/> | <s:property value="guimo"/> | <s:property value="zhiweiyuexin"/><br><s:property value="miaoshu"/></td></tr><tr><td colspan='2' height='18'></td></tr></table> 
				<!-- end 工作经验 item1 -->
                </td></tr>
                </s:iterator>
                </table>
                
			</div>
			
			<!-- end resume item:工作经验 -->
            
            <!-- resume item:项目经验 -->
							   
			<!-- end resume item:项目经验 -->
            
            <!-- resume item:教育背景 -->
			
			<div style="display:">
				<h3><a href="resume_alterSchoolInit.action?recruit.id=${ recruit.id}"  style="display:">[修改]</a>教育背景</h3>
				<p><s:property value="recruit.startdate"/> -- <s:property value="recruit.enddate"/>：<s:property value="recruit.school"/> | 电气工程及其自动化 | <s:property value="recruit.xueli"/></p>
			</div>
			
			<!-- end resume item:教育背景 -->
            
            <!-- 在校情况 -->
			
			<!-- end 在校情况 -->
            
            <!-- 实践经验 -->
			
			<!-- end 实践经验 -->
            
            <!-- resume item:培训经历 -->
			
			<!-- end resume item:培训经历 -->
            
            <!-- resume item:证书 -->
			
			<!-- end resume item:证书 -->
            
            <!-- resume item:语言能力 -->
			
			<div style="display:">
				<h3><a href="#" onClick="window.location='editlagu.asp?resume_id=206642320&ext_id=JR475973035R90250006000&version_number=1&language_id=1';return false;" style="display:">[修改]</a>语言能力</h3>	
				<s:iterator value="languages">
				<p><s:property value="name"/>：读写能力<s:property value="read"/> | 听说能力<s:property value="speak"/></p>
				</s:iterator>
			</div>
			
			<!-- end resume item:语言能力 -->
            
            <!-- resume item:专业技能 -->
			
			<!-- end resume item:专业技能 -->
            
            <!-- resume item:其他信息 -->
			
			<!-- end resume item:其他信息 -->
            
            <!-- resume item:照片/附件 -->
			
			<!-- end resume item:照片/附件 -->
            
            <!-- 附件简历 -->
			
			<!-- end 附件简历 -->
            
            <!-- 粘贴简历 -->
			
			<!-- end 粘贴简历 -->
            
        </div>

         </div>
	</div>
	<div class="clear"></div>
</div>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnutil.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fn4resumelist.js"></script>
<script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

</body>
</html>
