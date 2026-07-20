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
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/New_v3/myresume_date.css">
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/function.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/utilScript.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/myresume_popupdiv.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/New_v3/myresume_util.js">;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/New_v4/myresume_date.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/New_v3/formValidator.js">;</script>
<script type="text/javascript" src="js/calendar.js"></script>
<script language="javascript" type="text/javascript">
<!--
var helpURL = 'http://interface.zhaopin.com/help/help.asp?MID=22500';

//var start_date_y = new formEle(true,'startend','date',['请输入您受教育的开始年月','受教育的开始年月不能晚于结束年月'],null,{sMonth:'document.frmMain.start_date_m',eYear:'document.frmMain.end_date_y',eMonth:'document.frmMain.end_date_m'});
var start_date_y = new formEle(true,'date','start_date_y',['请选择开始年份','开始时间不能晚于结束时间'],null,{pre_condition:'education_date.start.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.start.objectY.div,"visibility")=="visible"',ymym:['start_date_y','start_date_m','end_date_y','end_date_m']});
var start_date_m = new formEle(true,'date','start_date_m',['请选择开始月份','开始时间不能晚于结束时间'],null,{pre_condition:'education_date.start.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.start.objectM.div,"visibility")=="visible"',ymym:['start_date_y','start_date_m','end_date_y','end_date_m']});
var end_date_y = new formEle(true,'date','end_date_y',['您已选择了结束月份，请选择相应的年份','开始时间不能晚于结束时间'],null,{condition:'document.frmMain.end_date_m.value!=""',pre_condition:'education_date.end.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.end.objectY.div,"visibility")=="visible"',ymym:['start_date_y','start_date_m','end_date_y','end_date_m']});
var end_date_m = new formEle(true,'date','end_date_m',['您已选择了结束年份，请选择相应的月份','开始时间不能晚于结束时间'],null,{condition:'document.frmMain.end_date_y.value!=""',pre_condition:'education_date.end.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.end.objectM.div,"visibility")=="visible"',ymym:['start_date_y','start_date_m','end_date_y','end_date_m']});
var school_name = new formEle(true,'text','schoolname',['请输入学校名称','学校名称长度不得超过255'],null,{length:255});
var subMajor = new formEle(true,'text','subMajor',['请选择专业名称'],null,{o4focus:'document.getElementById("subMajorF")'});
var major = new formEle(true,'text','major',['请输入专业名称','专业名称长度不得超过255'],null,{length:255,arrInvaTxt:['若无合适选项，请在此处填写专业名称']});
var degree = new formEle(true,'select','degree',['请选择您的学历/学位']);

function editItem(pStr_ID){
	document.frmMain.action="resume_education_edit.asp";
	document.frmMain.RowID.value=pStr_ID;
	document.frmMain.submit();
}

function deleteItem(pStr_ID){
	if(confirm('确定要删除该教育背景？'))
	{
		document.frmMain.action="resume_education_del.asp";
		document.frmMain.RowID.value=pStr_ID;
		document.frmMain.submit();
	}
}

function education_save(where){
	tryClearDefaultText(document.frmMain.major,'若无合适选项，请在此处填写专业名称');
	if(!document.frmMain.checkForm()) return;
	document.frmMain.action="resume_education_edit_save.asp";
	switch(where){
		case 'new' :
			document.frmMain.SaveType.value="1";
			document.frmMain.submit();
			break;
		case 'save' :
			document.frmMain.SaveType.value="0";
			document.frmMain.submit();
			break;
	}
}
//-->
</script>
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
		<h2><span>当前简历完整度：<font class='org12'>★</font><font class='org12'>★</font><font class='org12'>★</font><font class='grey12'>★</font><font class='grey12'>★</font></span>教育背景</h2>

		<div class="rightRow1">请在页面下方"新增教育经历"处填写新的教育经历。 要更改或删除某项教育经历，请按"编辑"或"删除"链接。</div>
		<!-- 已添加的教育背景 -->
		<table width="770" cellpadding="0" cellspacing="0" border="0" class="table1">
		<thead>
        <tr>
            <th width="4" class="noLine"><img src="http://myimg.zhaopin.com/images/new_v4/row2TL.jpg" width="4" height="24" /></th>
			<th width="280">学校名称</th>
			<th width="120">时间</th>
			<th width="260">学历/学位</th>
			<th width="100">&nbsp;</th>
            <th width="4" class="noLine"><img src="http://myimg.zhaopin.com/images/new_v4/row2TR.jpg" width="4" height="24" /></th>
		</tr>
        </thead>
        <tbody>
		<tr>
	<td colspan="6" valign="top" style="border-left:1px solid #B9B9D3;border-right:1px solid #B9B9D3">
    <table width="760" border="0" cellspacing="0" cellpadding="0" class="table6">
    <tr style="display:none;">
			<td width="280" align="left" class="tab6Td1"><span>江苏理工</span></td>
			<td width="120">2003-01---2003-01</td>
			<td width="260">本科</td>
			<td width="100"><a href="#" class="blue12line" onClick="editItem(1);return false;">编辑</a>  |  <a href="#" class="blue12line" onClick="deleteItem(1);return false;">删除</a></td></tr>
     <tr class="nowEdit" style="display:;">
			<td width="280" align="left" class="tab6Td1"><span><s:property value="recruit.school"/></span></td>
			<td width="120"><s:property value="recruit.startdate"/>---<s:property value="recruit.enddate"/></td>
			<td width="260"><s:property value="recruit.xueli"/></td>
			<td width="100"><a class="blue12line">编辑</a>  |  <a class="blue12line">删除</a></td>									</tr>   
            </table>
     </td>
</tr>

        </tbody>
        <tfoot>
            <tr>
                <td height="4"><img src="http://myimg.zhaopin.com/images/new_v4/row1BL.gif" width="4" height="4" /></td>
                <td colspan="4" background="http://myimg.zhaopin.com/images/new_v4/row1BBg.gif"></td>
                <td><img src="http://myimg.zhaopin.com/images/new_v4/row1BR.gif" width="4" height="4" /></td>
            </tr>
        </tfoot>
		</table>
		<!-- end 已添加的教育背景 -->
		<div class="rightRow1">修改教育背景（<span class="org14">*</span>为必填项）<a href="javascript:void(0)" class="blue12line" style="font-weight:normal;display:none;" mzpmodule="addOkDegreeFac" id="popupOkDegree" flagdiv="email_info" formele="frmMain$username|school_name|degree" modjs="/js/new_v3/ajaxbase.js|submitCallback" modprefn="addOkDegreeFac.initFn"><img src="http://myimg.zhaopin.com/images/new_v4/icon_queren.gif" width="19" height="16" vspace="2" align="absmiddle" style="margin-right:3px;" />添加已通过认证的学历信息</a></div>
		<span class="org12" style="display:;">[ 提示：如果您修改了选择项，则相应的英文简历该条记录也会自动被更改。请保持中英文内容的一致性。]</span>
		<form name="frmMain" action="resume_alterSchool.action" method="post">
		<input name="recruit.id" value="${recruit.id }" type="hidden"/>
		<!-- 教育背景 -->
		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="table5">
		<colgroup><col width="1"><col width="110"><col></colgroup>
		<tr><td><font class="org14">*</font></td>
			<td>时间</td>
			<td><input type="text" name="recruit.startdate" id="experience.workstartdate" onclick="setday(this)"/>至 <span><input type="text" name="recruit.startdate" id="recruit.enddate" onclick="setday(this)"/></span>
			</td>
		</tr>
		<tr><td></td><td></td><td><span id="txtError_start_date_y" style="margin-right:16px;display:none;"></span><span id="txtError_start_date_m" style="margin-right:16px;display:none;"></span><span id="txtError_end_date_y" style="margin-right:16px;display:none;"></span><span id="txtError_end_date_m" style="display:none;"></span></td></tr>
		<script language="javascript" type="text/javascript">
				var education_date = new MYRESUME.date_startend('education_date',document.frmMain.start_date_y,document.frmMain.start_date_m,document.frmMain.end_date_y,document.frmMain.end_date_m,true,'教育');
		</script>
		<tr><td><font class="org14">*</font></td>
			<td>学校名称</td>
			<td><input type="text" value="<s:property value="recruit.school"/>" name="recruit.school" size="50" id="school_name" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td></tr>
		<tr id="conError_schoolname" style="display:none;"><td></td><td></td><td id="txtError_schoolname"></td></tr>
		<script language="javascript" type="text/javascript">
		if(document.frmMain.school_name.value!=''&&(education_date.end.objectY.input.value==''||education_date.end.objectM.input.value=='')) education_date.end.setNow();
		</script>
		<tr><td><font class="org14">*</font></td>
			<td>专业名称</td>
			<td><input type="button" id="mainMajorF" class="selectBut2" mzpmodule="popupSingChooFac" hiddenname="document.frmMain.mainMajor" data="major" divwidth="500" titlename="专业名称" itemcol="4">&nbsp;&nbsp;<input type="button" id="subMajorF" class="selectBut2" mzpmodule="popupSingChooFac" parentobj="mainMajorF" hiddenname="document.frmMain.subMajor" data="major" divwidth="410" titlename="专业名称" clickitemfixfn="showCustomMajor"><input type="hidden" name="mainMajor" value="1"><input type="hidden" name="subMajor" value="73"></td></tr>
		<tr id="conError_subMajor" style="display:none;"><td></td><td></td><td id="txtError_subMajor"></td></tr>
        <tbody id="customMajorBlock" style="display:none;">
        <tr><td></td>
			<td></td>
			<td><input type="text" value="&#30005;&#27668;&#24037;&#31243;&#21450;&#20854;&#33258;&#21160;&#21270;" name="major" size="50" id="major" mzpmodule="resumeChEnFac" tiptext="" lang="en" onFocus="tryClearDefaultText(this,'若无合适选项，请在此处填写专业名称');" onBlur="trySetDefaultText(this,'若无合适选项，请在此处填写专业名称');"></td></tr>
		<tr id="conError_major" style="display:none;"><td></td><td></td><td id="txtError_major"></td></tr>
        </tbody>
		<tr><td><font class="org14">*</font></td>
			<td>学历/学位</td>
			<td>
				<select name="recruit.xueli">
					<option value="" selected="selected">请选择</option>
					
<option value="大专" <s:if test='recruit.xueli=="大专"'>SELECTED</s:if> >大专</option>
<option value="本科" <s:if test='recruit.xueli=="本科"'>SELECTED</s:if>>本科</option>
<option value="硕士" <s:if test='recruit.xueli=="硕士"'>SELECTED</s:if>>硕士</option>
<option value="博士" <s:if test='recruit.xueli=="博士"'>SELECTED</s:if>>博士</option>
<option value="MBA" <s:if test='recruit.xueli=="MBA"'>SELECTED</s:if>>MBA</option>
<option value="EMBA" <s:if test='recruit.xueli=="EMBA"'>SELECTED</s:if>>EMBA</option>
<option value="中专" <s:if test='recruit.xueli=="中专"'>SELECTED</s:if>>中专</option>
<option value="中技" <s:if test='recruit.xueli=="中技"'>SELECTED</s:if>>中技</option>
<option value="高中" <s:if test='recruit.xueli=="高中"'>SELECTED</s:if>>高中</option>
<option value="初中" <s:if test='recruit.xueli=="初中"'>SELECTED</s:if>>初中</option>
<option value="其他" <s:if test='recruit.xueli=="其他"'>SELECTED</s:if>>其他</option>
				</select><em id="email_info" class="msg_ok" style="display:none;">已通过教育部数据库认证</em></td></tr>
		<tr id="conError_degree" style="display:none;"><td></td><td></td><td id="txtError_degree"></td></tr>
		</table>
		<!-- end 教育背景 -->
		<div class="btnCon"><input type="button" class="btn7" name="save" value="保存" title="保存" onClick="education_save('save')">&nbsp;&nbsp;<a href="resume_preview_edit.asp?ext_id=JR475973035R90250006000&resume_id=206642320&Version_Number=1&language_id=1">返回</a></div>
		</form>
		<script language="javascript" type="text/javascript">
		<!--
		trySetDefaultText(document.frmMain.major,'若无合适选项，请在此处填写专业名称');
		iniCheckForm('frmMain');

		function hideCurrentPopup(){
			if(window.currentlyVisiblePopup){
				changeObjectVisibility(window.currentlyVisiblePopup, 'hidden');

				switch(window.currentlyVisiblePopup){
					case 'education_date_start_y_div' : start_date_y.fnValidate();if(start_date_m.s!=null&&start_date_m.s>-1) start_date_m.fnValidate();break;
					case 'education_date_start_m_div' : start_date_m.fnValidate();if(start_date_y.s!=null&&start_date_y.s>-1) start_date_y.fnValidate();break;
					case 'education_date_end_y_div' : end_date_y.fnValidate();if(end_date_m.s!=null&&end_date_m.s>-1) end_date_m.fnValidate();break;
					case 'education_date_end_m_div' : end_date_m.fnValidate();if(end_date_y.s!=null&&end_date_y.s>-1) end_date_y.fnValidate();break;
					case 'subMajorF_div' : subMajor.fnValidate();break;
				}

				window.currentlyVisiblePopup = false;
			}
		}
		MYRESUME.EventUtils.addEvent(document,'click',hideCurrentPopup)
		-->
		</script>
	</div>

    </div>
</div>
</div>


<div class="clear"></div>
<script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnUtil.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnResumeChEn.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnDegree.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnPopupSingChoo.js"></script>
</body>
</html>
