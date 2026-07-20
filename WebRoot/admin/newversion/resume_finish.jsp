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
<link href="http://img00.zhaopin.cn/2012/css/my/v5/resumes.css" type="text/css" rel="stylesheet" />
<link href="http://img00.zhaopin.cn/2012/css/my/v5/common.css" type="text/css" rel="stylesheet" />
<link href="http://img00.zhaopin.cn/2012/css/my/v5/layer.css" type="text/css" rel="stylesheet" />
<link href="http://img00.zhaopin.cn/2012/css/my/v5/jobsearch.css" type="text/css" rel="stylesheet" />
<link href="http://img00.zhaopin.cn/2012/css/ui/jquery.zlzp.popupdiv.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.resumeInput {border:0;color:#f60;font-weight:bold;background:#fff;}
</style>
<!-- Google Code for &#31616;&#21382;&#22635;&#20889; Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 991304468;
var google_conversion_language = "en";
var google_conversion_format = "2";
var google_conversion_color = "ffffff";
var google_conversion_label = "CjsoCLSPu1IQlLbY2AM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/991304468/?value=0&amp;label=CjsoCLSPu1IQlLbY2AM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>
<!-- Google Code for &#31616;&#21382;&#22635;&#20889; Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 1066359016;
var google_conversion_language = "en";
var google_conversion_format = "2";
var google_conversion_color = "ffffff";
var google_conversion_label = "_FkXCLyBtgUQ6LG9_AM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/1066359016/?value=0&amp;label=_FkXCLyBtgUQ6LG9_AM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>
<!-- Google Code for RMKT 2.0 &#20195;&#30721; -->
<!-- Remarketing tags may not be associated with personally identifiable information or placed on pages related to sensitive categories. For instructions on adding this tag and more information on the above requirements, read the setup guide: google.com/ads/remarketingsetup -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 991304468;
var google_conversion_label = "S9vACIyV0FEQlLbY2AM";
var google_custom_params = window.google_tag_params;
var google_remarketing_only = true;
/* ]]> */
</script>
<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/991304468/?value=0&amp;label=S9vACIyV0FEQlLbY2AM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/jquery-1.6.4.min.js"></script>
<script type="text/javascript"  src="http://img01.zhaopin.cn/2012/js/ui/jquery.zlzp.popupbase.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/function.js">    ;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/utilScript.js">    ;</script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v3/ajaxbase.js"></script>
<script language="javascript" type="text/javascript">
<!--
    var helpURL = 'http://interface.zhaopin.com/help/help.asp?MID=25800';

    //修改简历名称
    function editResumeName(obj, aEdit, aSave) {
        if (obj) {
            //obj.disabled=false;
            obj.style.border = "#7f9db9 1px solid";
            obj.onfocus = null;
        }
        if (aEdit) aEdit.style.display = 'none';
        if (aSave) aSave.style.display = '';
    }

    function submitResumeName() {
        if (document.editform.name.value == "") {
            alert("请填写您的简历名称")
            document.editform.name.focus();
        } else {
            document.editform.submit();
        }
    }
    //设置公开
    function SetOpen(pm) {
        var baseUrl = "http://my.zhaopin.com/template/myzhaopin/";
        var baseaspUrl = "http://my.zhaopin.com/myzhaopin/";
        var resumeInfo = pm;
        var targetUrl = "http://my.zhaopin.com/myzhaopin/resume_opencv.asp?" + resumeInfo + "&t=" + Math.random();
        $.popupDiv({ title: "简历公开设置", url: targetUrl, width: 420, success: function () {
            $("#opencv .popupConfirmBtn").click(function () {
                var item = $('input:radio[name="disclosure_level"]:checked').val();
                if (item != "") {
                    $.ajax(
                    {
                        type: "post",
                        url: "http://my.zhaopin.com/myzhaopin/resume_opencv_save.asp?t=" + Math.random(),
                        data: resumeInfo + "&disclosure_level=" + item,
                        success: function (data) {
                            if (data == "ok") {
                                window.location.href = window.location.href;
                            }
                            else {
                                alert(data);

                            }
                        }
                    }
                   );
                }

            });
        }
        });
    }
    //取消委托
    function CancelDeliver(resumeId) {
        var baseaspUrl = "http://my.zhaopin.com/myzhaopin/";
        var url = baseaspUrl + "cancel_consign.asp?" + resumeId + "&t=" + Math.random();
        $.post(url, {}, function (data) {
            if (data == "ok") {
                window.location.href = window.location.href;
                $.popupClose();
            }
            else {
                alert("取消委托投递失败!");
            }
        });
    }
    //设置委托投递
    function SetDeliver(pm) {
        var baseUrl = "http://my.zhaopin.com/template/myzhaopin/";
        var baseaspUrl = "http://my.zhaopin.com/myzhaopin/";
        var param = pm;
        var url = "/myzhaopin/resume_auto_post.asp?" + param + "&t=" + Math.random();

        var html = $.post(url, {},
        function (data) {
            if (data == "1") //只有公开简历才能委托
            {
                $.popupDiv({ title: "简历委托投递", url: baseUrl + "resume_auto_post_secret.htm", buttons: "no", width: 420 });
            }
            else {
                $.popupDiv({ title: "委托投递", html: data, width: 520, success: function () {
                    $("#lnkCancelDeliver").click(function () {
                        CancelDeliver(param);
                        return false;
                    });
                }, buttons: {
                    "保 存": function () {
                        var flag = true;
                        var jobt = $("#jobTypeVal");
                        var indu = $("#industryVal");
                        var city = $("#cityVal");
                        var empl = $("input[name='emplType']");
                        var delp = $("input[name='delPeriod']");
                        if (jobt.val() === "") {
                            flag = false;
                            createErrTag(jobt, "请选择期望从事职业", 2, $("#jobTypeBtn"));
                        }
                        if (indu.val() === "") {
                            flag = false;
                            createErrTag(indu, "请选择期望从事行业", 2, $("#industryBtn"));
                        }
                        if (city.val() === "") {
                            flag = false;
                            createErrTag(city, "请选择期望工作地点", 2, $("#cityBtn"));
                        }
                        if (!empl[0].checked && !empl[1].checked && !empl[2].checked) {
                            flag = false;
                            createErrTag(empl, "请选择期望工作性质", 3);
                        }
                        if (!delp[0].checked && !delp[1].checked) {
                            flag = false;
                            createErrTag(delp, "请选择委托周期", 3);
                        }
                        if (flag) {
                            var consign_period = "";
                            if (delp[0].checked) {
                                consign_period = 7;
                            }
                            else {
                                consign_period = 14;
                            }
                            var companyMask = "n";
                            if ($("#companyMask")[0].checked) {
                                companyMask = "y";
                            }
                            var emplTypeValue = [];
                            $('input[name="emplType"]:checked').each(function () {
                                emplTypeValue.push($(this).val());
                            });
                            var saveParam = "";
                            saveParam = param + "&max_position_number=10&expected_salary=0000000000&date_availability=5&showInit=y&item_id=1&pSel_jobtypeF=4010200&pSel_locationF=489&consign_period=" + consign_period + "&company_mask=" + companyMask + "&consign_condition=2&employment_type=" + emplTypeValue.join(",") + "&desired_City=" + $.trim(city.val()) + "&desired_Jobtype=" + $.trim(jobt.val()) + "&desired_Industry=" + $.trim(indu.val()) + "&t=" + Math.random();

                            $.post(baseaspUrl + "resume_auto_post_save.asp", saveParam,
                function (data) {
                    if (data == "ok") {
                        window.location.href = window.location.href;
                        $.popupClose();
                    }
                    else {
                        createErrTag(si, data, 2);
                    }
                });
                        }
                        return;
                    },
                    "取 消": function () {
                        $.popupClose();
                    }
                }
                });
            }
        });
    }
    function apply(formObj) {

        var chkbox = formObj.vacancyid;
        chkbox = chkbox.length ? chkbox : [chkbox];
        var form = formObj;
        var hidden = form.h_method;
        var arrOkNo = [];
        if (hidden && hidden.value) arrOkNo = hidden.value.split("|");
        var data = { num: 0, ok: "", no: "" };
        for (var i = 0; i < chkbox.length; i++) if (chkbox[i].checked) {
            data.num++;
            if (arrOkNo.length > i && arrOkNo[i] == 0) data.no += (data.no == "" ? "" : ",") + chkbox[i].value;
            else data.ok += (data.ok == "" ? "" : ",") + chkbox[i].value;

        }
        if (data.num == 0) alert("请选择职位");
        else {
            recordApplyAll();
            if (data.ok == "") zlzp.searchjob.allNoPosition(data);
            else zlzp.searchjob.ajaxApply(data, "");
        }
    }

    function recordApplyAll() {
        function ed(d, a) {
            var c = encodeURIComponent;
            return c instanceof Function ? (a ? encodeURI(d) : c(d)) : escape(d);
        }
        var c = document.getElementById("allvacancyid");
        try {
            _dywet._getTrackerByName()._trackEvent("rc", c.checked ? "recommendAll" : "recommend");
            try {
                _gat._getTrackerByName()._trackEvent("rc", c.checked ? "recommendAll" : "recommend");
            } catch (err) { }
        } catch (err) {
            var i = new Image(1, 1);
            var e = document.location;
            i.src = "http://l.zhaopin.com/track_err.gif?dywee=5(rc*" + (c.checked ? "recommendAll" : "recommend") + ")&dywehn=" + ed(e.hostname) + "&dywep=" + ed(e.pathname + e.search, true);
        }
    }

    function saveSearcher() {
        var p = "";
        p += "&SchJobTypeAdv=" + document.frmMain.SchJobTypeAdv.value;
        p += "&SchCityAdv=" + document.frmMain.SchCityAdv.value;
        p += "&EmplType=" + document.frmMain.EmplType.value;
        p += "&SchCompIndAdv=" + document.frmMain.SchCompIndAdv.value;
        var url = "/myzhaopin/job_search_save.asp?SaveType=EDIT" + p

        location.href = url;
    }

    function fnDoFavJob() {
        var i;
        var bChecked;
        bChecked = false;
        if (document.frmMain.vacancyid) {
            if (document.frmMain.vacancyid.length) {
                for (i = 0; i < document.frmMain.vacancyid.length; i++) {
                    if (document.frmMain.vacancyid[i].checked) {
                        bChecked = true;
                        break;
                    }
                }
                if (!bChecked) {
                    alert("请选择一个职位");
                    return false;
                }
            }
            else {
                if (!document.frmMain.vacancyid.checked) {
                    alert("请选择一个职位");
                    return false;
                }
            }
        }
        else {
            alert("请选择一个职位");
            return false;
        }
        document.frmMain.method = "get"
        document.frmMain.action = "http://my.zhaopin.com/myzhaopin/jobmng_fav_save.asp"
        document.frmMain.submit()
    }

    /* 用户行为监控 */
    var zpjk_cnt = {
        "urlfrom2": getCookie("urlfrom2"),
        "adfcid2": getCookie("adfcid2"),
        "adfbid2": getCookie("adfbid2"),
        "urlfrom": getCookie("urlfrom"),
        "adfcid": getCookie("adfcid"),
        "adfbid": getCookie("adfbid"),
        "userid": getCookie("JsNewlogin")
    };
    if (zpjk_cnt.urlfrom !== null && zpjk_cnt.adfcid !== null) (new Image()).src = "http://cnt.zhaopin.com/user_action.html?sid=" + zpjk_cnt.urlfrom + "&site=" + zpjk_cnt.adfcid + "&action=nuwresume&uid=" + zpjk_cnt.userid;
    else if (zpjk_cnt.urlfrom2 !== null && zpjk_cnt.adfcid2 !== null) (new Image()).src = "http://cnt.zhaopin.com/user_action.html?sid=" + zpjk_cnt.urlfrom2 + "&site=" + zpjk_cnt.adfcid2 + "&action=ouwresume&uid=" + zpjk_cnt.userid;
    /* 用户行为监控 */
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

<div class="layout">
<div class="row3" style="width:950px;padding:0px;">
	<div class="rtl" style="overflow:hidden;"></div><div class="rtcenter" style="width:942px;overflow:hidden;"></div><div class="rtr" style="overflow:hidden;"></div>
	<div class="round_box" style="width:938px;">
    <div class='uploding' style='width:64px;margin:0px auto;margin-bottom:10px;padding-top:10px;' ><div class='uploading_img'><a href='http://my.zhaopin.com/MYZHAOPIN/editatta_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1' onclick='dyweTrackEvent("my_upload","uploadimg")'><img src='http://img00.zhaopin.cn/2012/img/uploading_img.jpg' alt=''/></a></div><div class='uploading_btn' style='margin-top:7px;'><a href='http://my.zhaopin.com/MYZHAOPIN/editatta_edit.asp?Ext_ID=JR475973035R90250006000&Language_ID=1&Resume_ID=206642320&Version_Number=1' style='display:block;width:64px; height:19px;background:url(http://img00.zhaopin.cn/2012/img/uploading_button.png)' onclick='dyweTrackEvent("my_upload","uploadimg")' ></a></div></div>
		<div style="text-align:center;font-size:16px;font-weight:bold;margin-top:10px;">恭喜您，简历填写成功！</div> 
        <div style="text-align:center;margin-top:10px;"><a href="http://my.zhaopin.com/myzhaopin/iconlist.asp" style="display:none" target="_blank"><img src="http://my.zhaopin.com/images/new_v4/getfreeicon.gif" border="0" alt="免费领取图标" align="absmiddle" /></a> <font class="org12" style="font-size:12px;display:none">很抱歉，今天的200个免费图标已经送完。</font></div>
        <div style="text-align:center;font-size:14px;font-weight:bold;margin:10px 0px;">
            <a href="#" onClick="resumeObj.PreviewResume('resume_preview.asp?ext_id=JR475973035R90250006000&resume_id=206642320&Version_Number=1&language_id=1&LocationUrl=resume_list','preview',700,800)">预览简历</a>
            <a href="resume_preview_edit.asp?ext_id=JR475973035R90250006000&Resume_ID=206642320&Version_number=1&language_id=1">完善简历</a>
            <a onclick="SetDeliver('ext_id=JR475973035R90250006000&resume_id=206642320&Version_Number=1&language_id=1');return false;" href="#">委托投递</a>
            <a href="resume_list.asp">简历管理</a>
            <a href="http://sou.zhaopin.com">找工作</a>
            <a href="job_searcher.asp">订阅工作</a>
            <a target="_black" href="http://images.zhaopin.com/2012/other/mobile/mobile.html">下载手机版</a>
        </div>
        <div class="line180" style="text-align:center;"> <!--您已经创建了一份基本完整的中文简历，您的简历完整度为：60<br-->
   	        <form method="post" action="editname_save.asp" name="editform">
	        简历名称：
              <input type="hidden" name="url" value="resume_finished.asp"><input type="text" name="name" id="name" value="销售业务 3年 德州" title="销售业务 3年 德州" class="resumeInput" onfocus="this.blur()">
              <input type="hidden" name="ext_id" value="JR475973035R90250006000">
	          <input type="hidden" name="resume_id" value="206642320">
	          <input type="hidden" name="version_number" value="1">
	          <input type="hidden" name="language_id" value="1">
			  <input type="hidden" name="h_method" value="">
	          <a href="resume_alterInit.action?recruit.id=${recruit.id }" >修改</a>
              <a href="#" onClick="submitResumeName();return false;" id="aSave" style="display:none;">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;开放方式：<a onclick="SetOpen('ext_id=JR475973035R90250006000&language_id=1&Resume_ID=206642320&Version_Number=1');return false;" href="#">开放</a>   
              <span class="resumes_wzd" style="display:inline-block; vertical-align:middle;margin-left:10px;">
                 <div class="wzd_left">完整度</div>
                 <div class="wzd_center" resumeInfo="ext_id=JR475973035R90250006000&language_id=1&Resume_ID=206642320&Version_Number=1">
                    <div class="wzd_img" percent="60"></div>
                 </div>
                 <div class="wzd_right">60%</div>
              </span>
        
            </form>
        </div>
		<div align="right"></div>
    </div>
	<div class="rbl"></div><div class="rbcenter" style="width:942px;"></div><div class="rbr"></div><div class="clear"></div>
		<!-- job search -->
		<div class="maindiv"  id="divMain" style="display:none;">
		    <form style="margin:0px" name="frmMain" method="post">
		        <input type="hidden" name="SchJobTypeAdv" value="%E7%94%B2006%3B">
		        <input type="hidden" name="SchCityAdv" value="530%3B">
		        <input type="hidden" name="EmplType" value="2%3B">
		        <input type="hidden" name="SchCompIndAdv" value="140200%3B">
                <div class="headline">
        	        <div><p class="txt"><strong></strong>根据您的求职意愿，下面的职位也许适合您申请！</p><p class="btn"><span class="all"><input class="cb" id="allvacancyid" type="checkbox" name="allvacancyid" onClick="ChkSelectAll('vacancyid','allvacancyid',this)" />全部选中</span><span><input class="apply" type="button" onclick="apply(document.frmMain)" /></span></p></div>
                </div>
                <div class="cont" id="divJob"></div>
                <div class="bottom"><p class="btn"><span class="all"><input class="cb" id="allvacancyid1" type="checkbox" name="allvacancyid" onClick="ChkSelectAll('vacancyid','allvacancyid',this)" />全部选中</span><span><input class="apply" type="button" onclick="apply(document.frmMain)" /></span></p></div>
		        
            </form>
		</div>
        <!--div class="btnCon"><input type="button" class="btn10" value="继续搜索" title="继续搜索" onClick="window.location='http://sou.zhaopin.com/job_search.html'"></div-->
		<!-- end job search -->
        </div>
</div>
<div class="wzd_body">

</div>
<script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

<script language="javascript" type="text/javascript">
    function startRequest() {
        var strCity, strSubType;
        strCity = document.frmMain.SchCityAdv.value; //工作地点
        strSubType = document.frmMain.SchJobTypeAdv.value; //职位小类
        var d = new Date();
        var url = "getJobList.asp?timestamp=" + d.getTime();
        var Querystring = "&city=" + strCity + "&subtype=" + strSubType;
        submitCallback(Querystring, url, ParseResult, "get");
    }
    function ParseResult(flag, result) {
        if (flag) {
            //处理结果数据,将其放到表中
            var items;
            var oitem, odd;
            var vacancyID, jobtitle, joburl;
            var companyname, companyurl, city;
            var daterefresh;
            var html = "";
            var strappMethod = "";
            var col = 3, row = 5, colIndex = 0;
            try {
                document.getElementById('divTips').style.display = "none";
                if (result.length > 0 && result.indexOf("$") > -1) {
					var today = new Date();
					var defined1 = today.getFullYear()*10000+(today.getMonth()+1)*100+today.getDate();
					var defined2 =today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();

                    var arrData = result.split("\r\n");
                    var intRow = arrData.length - 1;
                    for (var ii = 0; ii < intRow; ii++) {
                        oitem = arrData[ii];
                        odd = oitem.split("{$}");
                        vacancyID = odd[0];
                        jobtitle = odd[1];
                        joburl = odd[2];
                        companyname = odd[3];
                        companyurl = odd[4];
                        city = odd[6];
                        daterefresh = odd[5];
                        strappMethod = odd[7] + "|";
                        if (ii % row == 0) html += "<div class=\"box" + (colIndex % col == col - 1 ? " last" : "") + "\"><ul>";
                        html += "<li" + ((ii - colIndex * row) % 2 == 0 ? "" : " class=\"bg\"") + "><p class=\"job\"><span class=\"fl\"><input type=\"checkbox\" name=\"vacancyid\" value=\"" + vacancyID +"_13_201__1_"+defined1+"#"+defined2 + "\" onClick=\"unChkSelectAll('allvacancyid')\" /></span><span class=\"fr\"><a href=\"" + joburl + "?ssidkey=y&r=RC&ff=13&ss=201\" target=\"_blank\">" + jobtitle + "</a></span></p><p class=\"other\"><span class=\"fl\"><a href=\"" + companyurl + "\" target=\"_blank\">" + companyname + "</a></span><span class=\"fr\">" + city + "</span></p></li>";
                        if (ii % row == row - 1) {
                            html += "</ul></div>";
                            colIndex++;
                        }
                    }
                    document.getElementById('divJob').innerHTML = html;
                    document.getElementById('divMain').style.display = "";
                    if (strappMethod.length > 0) {
                        strappMethod = strappMethod.substring(0, strappMethod.length - 1);
                        document.getElementById('h_method').value = strappMethod;
                    }
                }

                else document.getElementById('divMain').style.display = "none";
            }
            catch (e) {
                //取推荐数据错误
                //document.getElementById('divSingle').style.display = "none";
                //document.getElementById('divMore').style.display = "block";
            }
        }
    }
</script>
<script type="text/javascript" language='javascript'>    startRequest();</script>
<div id="zlzp_jsc"></div>
<script type="text/javascript">
    var complateFlag = 0;
    window.zlzp = {};
    $(document).ready(
   function () {
       $(".wzd_img").each(function () {
           var p = $(this).attr("percent");
           var c = parseInt(parseInt(p) / 10);
           if (c > 0) {
               $(this).attr("class", "wzd_img" + c);
           }
       });
       //完整度滑过
       $(".wzd_center").mouseout(function () {
           if (complateFlag != 0) {
               clearTimeout(complateFlag);
           }
       });
       $(".wzd_center").mouseover(function () {
           var el = $(this);
           complateFlag = setTimeout(function () {
               var _top = el.offset().top,
                _left = el.offset().left;
               var resumeId = el.attr("resumeInfo");
               var paramData = resumeId + "&t=" + Math.random();
               $.post("/myzhaopin/ajax_check_resume_complete.asp", paramData, function (data) {
                   if (data != "ok") {
                       $(".wzd_body").html(data);
                       $(".wzd_body").css('left', _left - 100).css('top', _top - 5).show();
                   }
               });
           }, 500);
       });
       $(".wzd_body").mouseleave(function () {
           $(".wzd_body").hide();
       });
   });
</script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/arrdata.js"></script>
<script type="text/javascript" src="http://jobs.zhaopin.com/javascript/ajaxapplynow.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/jquery.zlzp.popupdiv.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/my/v5/jsHelper.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/my/v5/resumeObj.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/my/v5/jobSearchObj.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/jquery.zlzp.validate.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/my/v5/jobsearch.js"></script>
</body>
</html>
