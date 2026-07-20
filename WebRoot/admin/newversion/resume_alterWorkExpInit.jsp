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
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/New_v3/resume_add.css">
<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/function.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/utilscript.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/arrdata.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/myresume_popupdiv.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/new_v3/myresume_util.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/new_v4/myresume_date.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/new_v3/formvalidator.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/new_v3/ajaxbase.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript">
    var docEle = function () {
        return document.getElementById(arguments[0]) || false;
    }

    function openMask(_id) {
        var m = "mask";
        if (docEle(_id)) document.body.removeChild(docEle(_id));
        if (docEle(m)) document.body.removeChild(docEle(m));

        //mask遮罩层

        var newMask = document.createElement("div");
        newMask.id = m;
        newMask.style.position = "absolute";
        newMask.style.zIndex = "1";
        _scrollWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);
        _scrollHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
        newMask.style.width = _scrollWidth + "px";
        newMask.style.height = _scrollHeight + "px";
        newMask.style.top = "0px";
        newMask.style.left = "0px";
        newMask.style.background = "#33393C";
        newMask.style.filter = "alpha(opacity=40)";
        newMask.style.opacity = "0.40";
        document.body.appendChild(newMask);

        var img = new Image();
        img.src = "http://myimg.zhaopin.com/images/new_v3/ani_ajaxload.gif";

        img.style.position = "absolute";
        img.style.top = document.body.clientHeight / 2 - img.height / 2 - 200 + "px";
        img.style.left = document.body.clientWidth / 2 - img.width / 2 + "px";
        document.body.appendChild(img);

    }
</script>
<script type="text/javascript">
<!--
var helpURL = 'http://interface.zhaopin.com/help/help.asp?MID=22500';

var cmpany_name = new formEle(true,'text','cmpanyname',['请输入企业名称']);
var company_type = new formEle(true,'select','companytype',['请选择企业性质']);
var industry = new formEle(true,'text','industry',['请选择行业类别'],null,{o4focus:'document.getElementById("button_industryF")'});
var subJobType = new formEle(true,'text','jobtype',['请选择职位类别'],null,{o4focus:'document.getElementById("button_jobtypeF")'});
var customSubJobtype = new formEle(true,'text','customSubJobtype',['请填写职位名称']);
//var workstart_date_y = new formEle(true,'startend','workdate',['请正确输入您工作的开始年月','工作的开始年月不能晚于结束年月'],null,{sMonth:'document.frmMain.workstart_date_m',eYear:'document.frmMain.workend_date_y',eMonth:'document.frmMain.workend_date_m'});
var workstart_date_y = new formEle(true,'date','workstart_date_y',['请选择开始年份','开始时间不能晚于结束时间'],null,{pre_condition:'work_date.start.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.start.objectY.div,"visibility")=="visible"',ymym:['workstart_date_y','workstart_date_m','workend_date_y','workend_date_m']});
var workstart_date_m = new formEle(true,'date','workstart_date_m',['请选择开始月份','开始时间不能晚于结束时间'],null,{pre_condition:'work_date.start.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.start.objectM.div,"visibility")=="visible"',ymym:['workstart_date_y','workstart_date_m','workend_date_y','workend_date_m']});
var workend_date_y = new formEle(true,'date','workend_date_y',['您已选择了结束月份，请选择相应的年份','开始时间不能晚于结束时间'],null,{condition:'document.frmMain.workend_date_m.value!=""',pre_condition:'work_date.end.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.end.objectY.div,"visibility")=="visible"',ymym:['workstart_date_y','workstart_date_m','workend_date_y','workend_date_m']});
var workend_date_m = new formEle(true,'date','workend_date_m',['您已选择了结束年份，请选择相应的月份','开始时间不能晚于结束时间'],null,{condition:'document.frmMain.workend_date_y.value!=""',pre_condition:'work_date.end.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.end.objectM.div,"visibility")=="visible"',ymym:['workstart_date_y','workstart_date_m','workend_date_y','workend_date_m']});
var salary_scope = new formEle(true,'select','salary',['请选择职位月薪']);
var job_description = new formEle(true,'text','description',['请对您的工作进行描述','工作描述内容过长'],null,{length:3000});

function changeLevel(sel){
	if(sel && document.frmMain.direct_supervision){
		document.frmMain.direct_supervision.selectedIndex=0;
		if(sel.value>0) document.frmMain.direct_supervision.disabled=false;
		else document.frmMain.direct_supervision.disabled=true;
	}
}

function editItem(pStr_ID){
	document.frmMain.action="resume_experience_edit.asp";
	document.frmMain.RowID.value=pStr_ID;
	document.frmMain.submit();
}

function deleteItem(pStr_ID,pAdvaNumber){
	if(confirm('确定要删除该工作经验？'))
	{
		if (pAdvaNumber>0){
			alert("不能删除该工作经验，请先对该工作经验下的团队管理经验删除后再进行此操作！");
			return;
		}
		document.frmMain.action="resume_experience_del.asp";
		document.frmMain.RowID.value=pStr_ID;
        document.frmMain.submit();

        openMask('newMask');
	 }
}



function editAdva(pStr_ID){
	document.frmMain.action="editadva.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1";
	document.frmMain.CompanyID.value=pStr_ID;
	document.frmMain.submit();
}

function experience_save(where){
    if(document.frmMain.job_description.value=='请详细描述您所负责的具体工作内容、业绩的达成情况和掌握的资源、客户等。'){
        document.frmMain.job_description.value='';
    }
	delInvisiChar(document.frmMain.job_description);
    /**20131220**/
    if(isopen == true && isNewUser == "true" && $("#sell-resumequstion").css("display") == "block"){
        sellDescriptionfn();
    }
    /**20131220**/
	if(!document.frmMain.checkForm()) return;
	document.frmMain.action="resume_experience_edit_save.asp";
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
// Task No: 81589 --modify by Andy.lu
	function bodyload()
	{
		industryF.initialize(industryF.h.value);
		jobtypeF.initialize(jobtypeF.h.value);
		subjobtypeF.initialize(subjobtypeF.h.value);
	}
// Task No: 81589 --modify by Andy.lu
//-->
</script>

<script type="text/javascript" src="http://img01.zhaopin.cn/myzhaopin/js/analytics.js"></script><script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/za/ga.js"></script>

</head>

<body>
<!-- popupDiv eg -->
<div id="eg1" class="popupDiv" style="width:350px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;" onClick="event.cancelBubble=true;">
	<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterWhite" style="width:334px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
	<div class="content"><div style="padding:0 10px 5px 10px;line-height:150%;">
		该公司为海外著名网络技术公司驻华办事处。任职期间参与制定公司发展战略和目标，组织策划并实施了人力资源管理体系，健全了各项规章制度，加大员工本土化进程，改革薪酬福利制度，完善了人力资源相关业务过程（包括工作分析、招聘、培训、绩效、薪资等），并参与完成ERP系统改进工作。</div>
	</div>
	<div class="bottomLeft"><img src="" width="1" height="1"></div><div class="bottomCenter" style="width:334px;"><img src="" width="1" height="1"></div><div class="bottomRight"><img src="" width="1" height="1"></div>
</div>
<div id="eg2" class="popupDiv" style="width:300px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;" onClick="event.cancelBubble=true;">
	<div class="topLeft"><img src="" width="1" height="1"></div><div class="topCenterWhite" style="width:284px;"><img src="" width="1" height="1"></div><div class="topRight"><img src="" width="1" height="1"></div>
	<div class="content"><div style="padding:0 10px 5px 10px;line-height:150%;">
		根据公司的近期和远期目标、财务预算，制定销售计划、制定和审核销售预算，提出产品价格政策；根据同类其他产品的市场动态，销售动态、存在问题、市场竞争发展状况等实施分析汇总，并提出改进方案和措施，协同销售计划的顺利完成；保持与客户的良好关系，维护客户管理，定期组织市场调研、分析市场动向、特点和发展趋势。于2006年成功拓展市场，实现年销售额600万的产品销售业绩。</div>
	</div>
	<div class="bottomLeft"><img src="" width="1" height="1"></div><div class="bottomCenter" style="width:284px;"><img src="" width="1" height="1"></div><div class="bottomRight"><img src="" width="1" height="1"></div>
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

<link href="/css/new_v5/my_nav.css" type="text/css" rel="stylesheet" />
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
<li class="ok"><a href="http://my.zhaopin.com/myzhaopin/resume_contactinfo_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">个人信息</a></li>
<li class="ok"><a href="http://my.zhaopin.com/MYZHAOPIN/EditComm.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">自我评价</a></li>
<li class="ok"><a href="http://my.zhaopin.com/MYZHAOPIN/EditInts.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">求职意向</a></li>
<li class="ok"><a href="http://my.zhaopin.com/MYZHAOPIN/resume_experience_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">工作经验</a></li>
<li class="ok"><a href="http://my.zhaopin.com/MYZHAOPIN/resume_education_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">教育背景</a></li>
<li class="blank"><a href="http://my.zhaopin.com/MYZHAOPIN/resume_training_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">培训经历</a></li>
<li class="ok"><a href="http://my.zhaopin.com/MYZHAOPIN/EditLagu.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">语言能力</a></li>
<li class="blank"><a href="http://my.zhaopin.com/MYZHAOPIN/job_letter.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">求职信</a></li>
<li class="blank"><a href="http://my.zhaopin.com/MYZHAOPIN/editcert.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">证书</a></li>
<li class="blank"><a href="http://my.zhaopin.com/MYZHAOPIN/editatta_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">照片/附件</a></li>
<li class="blank"><a href="http://my.zhaopin.com/MYZHAOPIN/resume_others_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1">其他信息</a></li>

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
		<h2><span>当前简历完整度：<font class='org12'>★</font><font class='org12'>★</font><font class='org12'>★</font><font class='grey12'>★</font><font class='grey12'>★</font></span>工作经验</h2>
		<div class="rightRow1">请在页面下方“新增工作经验”处填写新的工作经验。要更改或删除某项工作经验，请按“编辑”或“删除”链接。</div>
		<!-- 已添加的工作经验 -->
		<table width="770" cellpadding="0" cellspacing="0" border="0" class="table1">
        <thead>
			<tr>
           		<th width="4" class="noLine"><img src="http://myimg.zhaopin.com/images/new_v4/row2TL.jpg" width="4" height="24" /></th>
				<th width="250">公司名称</th>
				<th width="150">时间</th>
				<th width="150">部门</th>
				<th width="210">&nbsp;</th>
            	<th width="4" class="noLine"><img src="http://myimg.zhaopin.com/images/new_v4/row2TR.jpg" width="4" height="24" /></th></tr>
			</tr>
        </thead>
			<tr>
	<td colspan="6" valign="top" style="border-left:1px solid #B9B9D3;border-right:1px solid #B9B9D3">
    			<table width="760" border="0" cellspacing="0" cellpadding="0" class="table6">
    			<s:iterator value="experiences">
              <tr style="display:;">
				<td align="left" class="tab6Td1  " width="250"><s:property value="companyname"/></span></td>
				<td width="150"><s:property value="workstartdate"/>---<s:property value="workenddate"/></td>
				<td width="150"><s:property value="bumen"/></td>
				<td width="210"><a href="alterWorkExpInit.action?experience.id=<s:property value="id"/>&recruit.id=<s:property value="recruit.id"/>" class="blue12line" >编辑</a>  |  <a href="#" class="blue12line" onClick="deleteItem(1,0);return false;">删除</a>  |  <a href="#" class="blue12line" onClick="editAdva(1);return false;">编辑团队管理经验</a></td>
			  </tr>
            	</s:iterator>
            </table>
</td>
</tr>

        <tfoot>
            <tr>
                <td height="4"><img src="http://myimg.zhaopin.com/images/new_v4/row1BL.gif" width="4" height="4" /></td>
                <td colspan="4" background="http://myimg.zhaopin.com/images/new_v4/row1BBg.gif"></td>
                <td><img src="http://myimg.zhaopin.com/images/new_v4/row1BR.gif" width="4" height="4" /></td>
            </tr>
        </tfoot>
		</table>
		<!-- end 已添加的工作经验 -->
		<div class="rightRow1">新增工作经验（<span class="org14">*</span>为必填项）</div>
<span class="org12" id="editoldmess" style="display:none;">[ 提示：如果您修改了选择项，则相应的英文简历该条记录也会自动被更改。请保持中英文内容的一致性。]</span>
		<form name="frmMain" action="resume_addWorkExp.action" method="post">
		<input  type="hidden" name="recruit.id" value="${recruit.id }"/>
		<!-- 工作经验 -->
		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="table5">
		<colgroup><col width="1"><col width="110"><col width="300"><col width="1"><col width="70"><col></colgroup>
		<tr><td><font class="org14">*</font></td>
			<td>企业名称</td>
			<td colspan="4"><input type="text" value="" name="experience.companyname" size="59" id="cmpany_name" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td></tr>
		<tr id="conError_cmpanyname" style="display:none;"><td></td><td></td><td id="txtError_cmpanyname" colspan="3"></td></tr>
		<tr><td><font class="org14">*</font></td>
			<td>企业性质</td>
			<td>
				<select name="experience.xingzhi">
					<option value="">请选择</option>
					
<option value="1" >国企</option>
<option value="2" >外商独资</option>
<option value="3" >代表处</option>
<option value="4" >合资</option>
<option value="5" >民营</option>
<option value="8" >股份制企业</option>
<option value="9" >上市公司</option>
<option value="6" >国家机关</option>
<option value="10" >事业单位</option>
<option value="7" >其它</option>
				</select>
			</td>
			<td></td>
			<td>企业规模</td>
			<td>
				<select name="experience.guimo">
					<option value="" selected="selected">请选择</option>
					
<option value="1" >20人以下</option>
<option value="2" >20-99人</option>
<option value="3" >100-499人</option>
<option value="4" >500-999人</option>
<option value="5" >1000-9999人</option>
<option value="6" >10000人以上</option>
				</select>
			</td></tr>
		<tr id="conError_companytype" style="display:none;"><td></td><td></td><td id="txtError_companytype" colspan="3"></td></tr>
		<tr><td><font class="org14">*</font></td>
			<td>行业类别</td>
			<td colspan="4"><input type="button" id="button_industryF" class="selectBut2" name="experience.hangyeleibie" mzpmodule="popupSingChooFac" hiddenname="document.frmMain.industry" data="industry" divwidth="660" titlename="行业类别" itemcol="3"><input type="hidden" name="industry" value=""></td></tr>
		<tr id="conError_industry" style="display:none;"><td></td><td></td><td id="txtError_industry" colspan="3"></td></tr>
		<tr><td></td>
			<td>所在的部门</td>
			<td colspan="4"><input type="text" name="experience.bumen" value="" size="59" id="department" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td></tr>
		<tr><td valign="top"><font class="org14">*</font></td>
			<td valign="top">职位类别</td>
			<td colspan="4"><input type="button" id="button_jobtypeF" name="experience.zhiweileibie" class="selectBut2" mzpmodule="popupSingChooFac" hiddenname="document.frmMain.SchJobType" data="jobname" divwidth="480" titlename="职位类别" itemcol="3">&nbsp;&nbsp;<input type="button" id="button_subjobtypeF" class="selectBut2" mzpmodule="popupSingChooFac" parentobj="button_jobtypeF" hiddenname="document.frmMain.subJobType" data="jobname" divwidth="410" titlename="职位名称" clickitemfixfn="showCustomJobName" initfixfn="initCustomJobName"><input type="Hidden" name="SchJobType" value=""><input type="Hidden" name="subJobType" value=""></td></tr>
		<tr id="conError_jobtype" style="display:none;"><td></td><td></td><td id="txtError_jobtype" colspan="3"></td></tr>
		<tr><td><font class="org14">*</font></td>
        	<td>职位名称</td>
            <td colspan="4"><input type="text" name="experience.zhiyemingcheng" value="" size="59" id="customSubJobtype" mzpmodule="resumeChEnFac" tiptext="" lang="en"><span style="color:#989898;">（此项可修改）</span></td></tr>
        <tr id="conError_customSubJobtype" style="display:none;"><td></td><td></td><td id="txtError_customSubJobtype" colspan="3"></td></tr>
        <tr><td><font class="org14">*</font></td>
			<td>工作时间</td>
			<td colspan="4">
				<input type="text" name="experience.workstartdate" id="experience.workstartdate" onclick="setday(this)"/> 至 <span><input type="text" name="experience.workenddate" id="experience.workenddate" onclick="setday(this)"/></span>
			</td>
		</tr>
		<tr><td></td><td></td><td colspan="3"><span id="txtError_workstart_date_y" style="margin-right:16px;display:none;"></span><span id="txtError_workstart_date_m" style="margin-right:16px;display:none;"></span><span id="txtError_workend_date_y" style="margin-right:16px;display:none;"></span><span id="txtError_workend_date_m" style="display:none; "></span></td></tr>
		<script language="javascript" type="text/javascript">
			var work_date = new MYRESUME.date_startend('work_date',document.frmMain.workstart_date_y,document.frmMain.workstart_date_m,document.frmMain.workend_date_y,document.frmMain.workend_date_m,true,'工作');
			if(document.frmMain.cmpany_name.value!=''&&(work_date.end.objectY.input.value==''||work_date.end.objectM.input.value=='')) work_date.end.setNow();
		</script>
		<tr><td><font class="org14">*</font></td>
			<td>职位月薪(税前)</td>
			<td colspan="4">
				<select name="experience.zhiweiyuexin">
					<option value="">请选择</option>
					
<option value="0000001000" >1000元/月以下</option>
<option value="0100002000" >1000-2000元/月</option>
<option value="0200104000" >2001-4000元/月</option>
<option value="0400106000" >4001-6000元/月</option>
<option value="0600108000" >6001-8000元/月</option>
<option value="0800110000" >8001-10000元/月</option>
<option value="1000115000" >10001-15000元/月</option>
<option value="1500125000" >15000-25000元/月</option>
<option value="2500199999" >25000元/月以上</option>
<option value="0000000000" >保密</option>
				</select>
			</td></tr>
		<tr id="conError_salary" style="display:none;"><td></td><td></td><td id="txtError_salary" colspan="3"></td></tr>
		<tr><td valign="top"><font class="org14">*</font></td>
			<td valign="top">工作描述</td>
			<td colspan="4">
            <div class="sell_resumetitle_self table5sef">参考模板：<em data-index="1">销售代表</em><em data-index="2">电话销售</em><em data-index="3">销售主管</em></div>
            <div class="sell_resumetitle_help table5sef">已输入3000字，还可输入0字</div>
            <div class="sell_resumetitle" id="sell_resumetitle"><span class="current" onclick="dyweTrackEvent('sellresume','oneselfwrite')">自己写</span><span class="sell_titicon" onclick="dyweTrackEvent('sellresume','helpwrite')">我帮你写</span></div>
            <div class="sell_resumeCont">
                <textarea class="sell_resumearea grey12 sell_diy" name="experience.miaoshu" cols="95" rows="13" onBlur="submitCallback('comment='+ua(document.frmMain.job_description.value),'usermaster_blacklists_save.asp?iChecked=0&iBlocked=0',ajaxReturn,'post','');iniWordNum(document.getElementById('maxWord1'),document.getElementById('maxWord2'),'限3000字以内','eg3')" onFocus="calWordNumRemained(3000,this,document.getElementById('maxWord1'),document.getElementById('maxWord2'),'eg3')"  onkeyup="calWordNumRemained(3000,this,document.getElementById('maxWord1'),document.getElementById('maxWord2'),'eg3')" id="job_description" mzpmodule="resumeChEnFac" tiptext="" lang="en"></textarea></br>
                <span class="grey12">填写文字在100个字以上评定等级，少于不计算，内容越详细，等级越高。<br>（<SPAN id=maxWord1 style="DISPLAY: none"></SPAN><SPAN id=maxWord2>限3000个字</SPAN><A onMouseOver="stopTimeG();showPopup('eg3',event,getXY(document.getElementById('eg3Img')).x+12,getXY(document.getElementById('eg3Img')).y+10)" onClick="return false;" onmouseout=startTimeG(); href="http://my.zhaopin.com/myzhaopin/resume_baseinfo.asp?ext_id=JR028855777R90000009000&amp;resume_id=3430176&amp;Version_Number=1&amp;language_id=1&amp;LocationUrl=resume_list&amp;DYWE=1228439254234.89987.1229671288.1229909284.33#"><IMG id=eg3Img src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" align=absMiddle border=0 style="display:none"></A>）</span>
                <div id="conError_description" style="display:none;">
                    <div id="txtError_description"></div>
                </div>
            </div>
            <div class="sell-resumequstion" id="sell-resumequstion">
                        <div class="questionclassfi">
                            智联调查显示，近七成HR在面试销售岗位时会关注以下问题，善用求职利器，让求职成功率翻倍。<span class="questiontips">带*号的为必填项</span>
                        </div>
                        <!--基础问题-->
                        <div class="questionDiv">
                            <div>
                                <label for="">您的日常工作有</label>
                                <input type="text" class="questiontxt size1" />
                            </div>
                            <div>
                                <label for="">* 您销售的产品是</label>
                                <input type="text" class="questiontxt size2 mustreturn" />
                                <div class="errortxt">请回答该必填项</div>
                            </div>
                            <div>
                                <label for="">您所负责的产品销售区域是</label>
                                <input type="text" class="questiontxt size3" />
                            </div>
                            <div>
                                <label for="">您曾获得的奖项是</label>
                                <input type="text" class="questiontxt size4" />
                            </div>
                            <div>
                                <label for="">* 您曾取得的销售业绩是</label>
                                <input type="text" class="questiontxt size5 mustreturn" />
                                <div class="errortxt">请回答该必填项</div>
                            </div>
                            <div class="maxhg">
                                <label for="">您掌握的渠道资源是（例如：渠道、客户、政府、运营商、商超、餐饮业、校园和医疗行业资源等）</label>
                                <input type="text" class="questiontxt size6" />
                            </div>
                            <div>
                                <label for="">您管理的经销商层级是</label>
                                <input type="text" class="questiontxt size7" />
                            </div>
                        </div>
                        <div class="creatBox">
                            <button type="button" class="creatSellbtn"  id="createDescription"></button>
                        </div>
                </div>
        </td></tr>
		</table>
		<!-- end 工作经验 -->
		<div class="btnCon"><input type="submit" class="btn7 sellDescription12" name="save" value="保存" title="保存" /> &nbsp;&nbsp;<a href="resume_preview_edit.asp?ext_id=JR475973035R90250006000&resume_id=206642320&Version_Number=1&language_id=1">返回</a></div>
		</form>
		<script language="javascript" type="text/javascript">
		<!--
		iniCheckForm('frmMain');
		var isNewUser="false" ;
		/*myAttachEvent(document.frmMain.workstart_date_m,'blur',function(){workstart_date_y.fnValidate()});
		myAttachEvent(document.frmMain.workend_date_y,'blur',function(){workstart_date_y.fnValidate()});
		myAttachEvent(document.frmMain.workend_date_m,'blur',function(){workstart_date_y.fnValidate()});*/
		myAttachEvent(document.frmMain.customSubJobtype,'blur',function(){subJobType.fnValidate()});

		function hideCurrentPopup(){
			if(window.currentlyVisiblePopup){
				changeObjectVisibility(window.currentlyVisiblePopup, 'hidden');

				switch(window.currentlyVisiblePopup){
					case 'button_industryF_div' : industry.fnValidate();break;
					case 'button_subjobtypeF_div' : subJobType.fnValidate();break;
					case 'work_date_start_y_div' : workstart_date_y.fnValidate();if(workstart_date_m.s!=null&&workstart_date_m.s>-1) workstart_date_m.fnValidate();break;
					case 'work_date_start_m_div' : workstart_date_m.fnValidate();if(workstart_date_y.s!=null&&workstart_date_y.s>-1) workstart_date_y.fnValidate();break;
					case 'work_date_end_y_div' : workend_date_y.fnValidate();if(workend_date_m.s!=null&&workend_date_m.s>-1) workend_date_m.fnValidate();break;
					case 'work_date_end_m_div' : workend_date_m.fnValidate();if(workend_date_y.s!=null&&workend_date_y.s>-1) workend_date_y.fnValidate();break;
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

<script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnUtil.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnResumeChEn.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnPopupSingChoo.js"></script>
<script src="http://img01.zhaopin.cn/2012/js/jquery-1.6.4.min.js" type="text/javascript"></script>
<script type="text/javascript" >
    $(function(){
        $("#createDescription").click(function(){
		   //官方监控ga
		   dyweTrackEvent('sellresume','createdescription');
		   //”生成描述”按钮,需要跟踪到具体某一份简历
		   var i = new Image(1, 1);
		   var extid = GetQueryString("ext_id");
		   var version = GetQueryString("Version_number");
		   var  dywesuId = getCookie("JsNewlogin");
		   i.src = "http://pv.zhaopin.cn/track.gif?dywehn=my.zhaopin.com&pos=f0001&act=10001&dywesu="+dywesuId+"&extid="+extid+"_"+version+"&path="+document.location+"&referer="+document.referrer;
		});
		
		function getCookie(name) {
			var tmp, reg = new RegExp("(?:^| )" + name + "=([^;]*)(?:;|$)", "gi");
			return (tmp = reg.exec(document.cookie)) ? (unescape(tmp[1])) : '';
		}
		function GetQueryString(name) {
		   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
		   var r = window.location.search.substr(1).match(reg);
		   if (r!=null) return unescape(r[2]); return null;
		}
	});
</script>
<script type="text/javascript" src="http://my.zhaopin.com/js/New_v3/resume_addjs.js"></script>
</body>
</html>
