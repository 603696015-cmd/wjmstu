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
<head>
<title>简历管理_我的智联_智联招聘</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/myzhaopin.css">
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/subnav_resumes.css">
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/myresume_popupdiv.css">
<link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/New_v3/myresume_date.css">
<link href="http://img00.zhaopin.cn/2012/css/my/v5/common.css" type="text/css" rel="stylesheet" />
<link href="http://img00.zhaopin.cn/2012/css/ui/jquery.zlzp.popupdiv.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="http://my.zhaopin.com/js/function.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/utilScript.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/arrdata.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/myresume_popupdiv.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/myresume_util.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/myresume_radio.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/New_v4/myresume_date.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/new_v3/formValidator.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript">
<!--
var helpURL = 'http://interface.zhaopin.com/help/help.asp?MID=22500';

var username = new formEle(true,'name','username',['请填写您的姓名','请正确填写你的姓名','请正确填写你的姓名'],null,{min:2});
var birth_date_y = new formEle(true,'text','birth_date_y',['请选择出生日期年份']);
var birth_date_m = new formEle(true,'text','birth_date_m',['请选择出生日期月份']);
var expe = new formEle(true,'experience','experience',['请选择参加工作年份','请选择参加工作月份'],null,{miny:12,year:'document.frmMain.experience',month:'document.frmMain.experience_month',o4focus:'document.getElementById("expeButton")'});
var nationality = new formEle(true,'text','nationality',['请选择国籍']);
var hukou = new formEle(true,'text','hukou',['请选择户口所在地'],null,{condition:'document.frmMain.nationality.value=="489"',o4focus:'document.getElementById("hukouF_button")'});
//var hukouF = new lib_popupDivR("hukouF","document.frmMain.hukou","arrCity",nationalityF,"writeDivItem","clickL3","clickL3P","showSelectedItem()",{province:document.frmMain.hukou_p});
var id_type = new formEle(true,'select','id_type',['请选择证件类型']);
var id_number = new formEle(true,'id','id_number',['请填写证件号码','身份证号码填写有误','请核对身份证号码和您的出生日期'],null,{year:'document.frmMain.birth_date_y',month:'document.frmMain.birth_date_m',flag:'document.frmMain.id_type.value==1'});
var residence = new formEle(true,'text','residence',['请选择现居住城市'],null,{o4focus:'document.getElementById("residenceF_button")'});
//var residenceF = new lib_popupDivR("residenceF","document.frmMain.residence","arrCity",null,"writeDivItem","clickL3","clickL3P","showSelectedItem()",{province:document.frmMain.residence_p});
var contact_num0 = new formEle(true,'textel','contact0',['请填写您的联系方式','请输入正确的联系方式']);
var email = new formEle(true,'email','email',['请填写您的电子邮箱','电子邮箱格式有误']);

function fnStart(){
	var f = document.frmMain,overseas = f.overseas,flag=false;
	if(overseas.length)
		if(overseas[0].checked) flag = true;
		else flag=false;
	overseasTime(flag);

	workTime()
}
window.onload=fnStart;

function addContact(max){
	var maxItem = max,tr;
	for(var i=1;i<=max;i++){
		tr = document.getElementById('trContact'+i);
		if(tr.style.display=='none'){
			tr.style.display='';
			if(eval('document.frmMain.contact_type'+i)) eval('document.frmMain.contact_type'+i).style.display='';//for IE
			break;
		}
	}
	var lastItem = document.getElementById('trContact'+maxItem),objA = document.getElementById('aAddContact');
	if(lastItem && lastItem.style.display!='none' && objA) objA.style.display='none';
}
function delContact(n){
	if(confirm('确定要删除该联系方式？')){
	var tr = document.getElementById('trContact'+n);
	if(tr){
		tr.style.display='none';
		if(document.getElementById('conError_contact'+n)) document.getElementById('conError_contact'+n).style.display='none';
		if(eval('document.frmMain.contact_type'+n)){
			eval('document.frmMain.contact_type'+n).style.display='none';//for IE
			eval('document.frmMain.contact_type'+n).selectedIndex=0;
		}
		eval('document.frmMain.contact_num'+n).value='';
	}
	var objA = document.getElementById('aAddContact');
	if(objA.style.display=='none') objA.style.display='';}
}

function saveResume(){
	if(!document.frmMain.checkForm()) return;

/*	if (document.frmMain.email.disabled==false){
		document.frmMain.email1.value=document.frmMain.email.value;
	}
	window.frmMain.submit();
	*/
	
	checkemail();
}

function changeDisabled(o,v){
	if(o) o.disabled=v;
}

function overseasTime(action){
	var objS = document.frmMain.overseasyear;
	if(action){
		objS.style.display='';
		//objS.selectedIndex=0;
	}
	else objS.style.display='none';
}

function workTime(){
	var y = document.frmMain.experience,s = document.getElementById('spanExMonth');
	if(y && s){
		if(parseInt(y.value)>1944 && parseInt(y.value)<=2014 && y.selectedIndex>2){
			if(s.style.display=='none'){
				s.style.display='';
				//document.frmMain.experience_month.selectedIndex=4;
			}
		}
		else s.style.display='none';
	}
}

	function checkemail(){

		var d = new Date();

		if (document.getElementById('conError_email').style.display=='none'){

			if (document.frmMain.emailshow.value.toLowerCase() == document.frmMain.email1.value.toLowerCase()){
				document.getElementById('conError_emailmessage').style.display='none';
				document.getElementById('message').innerHTML = "<font color=red>Email未修改。</font>";
				document.frmMain.emailshow.className = "classFormEle_ok";

				if (document.frmMain.emailshow.disabled==false){
						document.frmMain.email1.value=document.frmMain.emailshow.value;
				}
				document.frmMain.submit();
			}
			else{
				if (document.frmMain.emailshow.value.toLowerCase() == document.frmMain.email2.value.toLowerCase() || document.frmMain.emailshow.value.toLowerCase() == document.frmMain.loginName1.value.toLowerCase()){
					document.getElementById('conError_emailmessage').style.display='none';
					document.getElementById('message').innerHTML = "<font color=red>恭喜您，您可以使用该Email。</font>";
					document.frmMain.emailshow.className = "classFormEle_ok";

					if (document.frmMain.emailshow.disabled==false){
							document.frmMain.email1.value=document.frmMain.emailshow.value;
					}
					document.frmMain.submit();
					}
				else{
					checkusername('checkEmail.asp?email='+ document.frmMain.emailshow.value + '&opt=1&timestamp=' + d.getTime())}
				}
		}
		else{
			document.getElementById('conError_emailmessage').style.display='none';
		}
	}
	var xmlhttp;
	function checkusername(url){
		document.getElementById('message').innerHTML="系统正在检测中……"

		if(window.XMLHttpRequest){
			xmlhttp=new XMLHttpRequest();
		}
		else if(window.ActiveXObject)
		{
		   xmlhttp=new ActiveXObject("microsoft.XMLHTTP");
		}

		if(!xmlhttp){
			document.getElementById('message').innerHTML="无法获取远程数据....";
			return false;
		}
		xmlhttp.open("GET",url,true);
		xmlhttp.onreadystatechange=getmessage;
		xmlhttp.send(null);
	}


	function getmessage(){
		if(xmlhttp.readyState==4)
		{
		   if(xmlhttp.status==200)
		   {
			var alertmsg=xmlhttp.responseText;
			  if(alertmsg.indexOf("5")!=-1){
 				  document.getElementById('conError_emailmessage').style.display='';
				  document.getElementById('message').innerHTML = "<font color=red>很遗憾，该Email已被其它求职者使用，您不可使用该Email。</font>";
				  document.frmMain.emailshow.className = "classFormEle_error";

			  }
			  else{
				  document.getElementById('conError_emailmessage').style.display='none';
				  document.getElementById('message').innerHTML = "<font color=red>恭喜您，您可以使用该Email。</font>";
				  document.frmMain.emailshow.className = "classFormEle_ok";

					if (document.frmMain.emailshow.disabled==false){
							document.frmMain.email1.value=document.frmMain.emailshow.value;
					}
					document.frmMain.submit();
			  }

	   }
		   else
		   {
			 document.getElementById('message').innerHTML="请等待……";
		   }
		}


	}

	function textEmail(){
		document.getElementById('message').innerHTML = "";
		document.getElementById('conError_emailmessage').style.display='none';
	}

	function modifyEmail(){
		document.frmMain.emailshow.value = document.frmMain.email1.value;
		document.frmMain.emailshow.disabled = false;
		document.frmMain.BuEmailCheck.disabled = false;
		return false;
	}

	function checkemail2(){

		var d = new Date();

		if (document.getElementById('conError_email').style.display=='none'){
			document.getElementById('conError_emailmessage').style.display='';
			if (document.frmMain.emailshow.value.toLowerCase() == document.frmMain.email1.value.toLowerCase()){
				document.getElementById('message').innerHTML = "<font color=red>Email未修改。</font>";
				document.frmMain.emailshow.className = "classFormEle_ok";
			}
			else{
				if (document.frmMain.emailshow.value.toLowerCase() == document.frmMain.email2.value.toLowerCase() || document.frmMain.emailshow.value.toLowerCase() == document.frmMain.loginName1.value.toLowerCase()){
					document.getElementById('message').innerHTML = "<font color=red>恭喜您，您可以使用该Email。</font>";
					document.frmMain.emailshow.className = "classFormEle_ok";
					}
				else{
					checkusername2('checkEmail.asp?email='+ document.frmMain.emailshow.value + '&opt=1&timestamp=' + d.getTime())}
				}
		}
		else{
			document.getElementById('conError_emailmessage').style.display='none';
		}
	}
	var xmlhttp;
	function checkusername2(url){
		document.getElementById('message').innerHTML="系统正在检测中……"

		if(window.XMLHttpRequest){
			xmlhttp=new XMLHttpRequest();
		}
		else if(window.ActiveXObject)
		{
		   xmlhttp=new ActiveXObject("microsoft.XMLHTTP");
		}

		if(!xmlhttp){
			document.getElementById('message').innerHTML="无法获取远程数据....";
			return false;
		}
		xmlhttp.open("GET",url,true);
		xmlhttp.onreadystatechange=getmessage2;
		xmlhttp.send(null);
	}


	function getmessage2(){
		if(xmlhttp.readyState==4)
		{
		   if(xmlhttp.status==200)
		   {
			var alertmsg=xmlhttp.responseText;
			  if(alertmsg.indexOf("5")!=-1){
				  document.getElementById('message').innerHTML = "<font color=red>很遗憾，该Email已被其它求职者使用，您不可使用该Email。</font>";
				  document.frmMain.emailshow.className = "classFormEle_error";
			  }
			  else{
				  document.getElementById('message').innerHTML = "<font color=red>恭喜您，您可以使用该Email。</font>";
				  document.frmMain.emailshow.className = "classFormEle_ok";
			  }

	   }
		   else
		   {
			 document.getElementById('message').innerHTML="请等待……";
		   }
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
                	<li>销售业务 3年 德州</li>
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
    	<h2><span>当前简历完整度：<font class='org12'>★</font><font class='org12'>★</font><font class='org12'>★</font><font class='grey12'>★</font><font class='grey12'>★</font></span>个人信息</h2>
        <div class="rightRow1">修改个人信息。（<span class="org14">*</span>为必填项）</div>
		<form name="frmMain" action="resume_alterUserInfo.action" method="post">
		<input type="hidden" name="recruit.id" value="${recruit.id }"/>
        <!-- 个人信息 -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table1_found">
		
<tr>
  <th><span>*</span><font id="Lusername">姓名</font></th>
  <td colspan="3"><input type="text" name="elUser.realname" class="frame7" value="${recruit.elUser.realname }" id="username" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td>
</tr>
<tr id="conError_username" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_username"></td>
</tr>
<tr>
  <th><span>*</span><font id="Lgender">性别</font></th>
  <td colspan="3"><input type="radio" name="elUser.sex" value="男" <s:if test='recruit.elUser.sex=="男"'>checked="checked"</s:if> >
    男
    <input type="radio" name="elUser.sex" value="男" <s:if test='recruit.elUser.sex=="女"'>checked="checked"</s:if>  >
    女</td>
</tr>
<tr>
  <th><span>*</span><font id="Lbirth_date_y">出生日期</font></th>
  <td colspan="3"><!--<SELECT name="birth_date_y"><option value=1944 >1944</option>
<option value=1945 >1945</option>
<option value=1946 >1946</option>
<option value=1947 >1947</option>
<option value=1948 >1948</option>
<option value=1949 >1949</option>
<option value=1950 >1950</option>
<option value=1951 >1951</option>
<option value=1952 >1952</option>
<option value=1953 >1953</option>
<option value=1954 >1954</option>
<option value=1955 >1955</option>
<option value=1956 >1956</option>
<option value=1957 >1957</option>
<option value=1958 >1958</option>
<option value=1959 >1959</option>
<option value=1960 >1960</option>
<option value=1961 >1961</option>
<option value=1962 >1962</option>
<option value=1963 >1963</option>
<option value=1964 >1964</option>
<option value=1965 >1965</option>
<option value=1966 >1966</option>
<option value=1967 >1967</option>
<option value=1968 >1968</option>
<option value=1969 >1969</option>
<option value=1970 >1970</option>
<option value=1971 >1971</option>
<option value=1972 >1972</option>
<option value=1973 >1973</option>
<option value=1974 >1974</option>
<option value=1975 >1975</option>
<option value=1976 >1976</option>
<option value=1977 >1977</option>
<option value=1978 >1978</option>
<option value=1979 >1979</option>
<option value=1980 >1980</option>
<option value=1981 >1981</option>
<option value=1982 >1982</option>
<option value=1983 >1983</option>
<option value=1984 >1984</option>
<option value=1985 >1985</option>
<option value=1986 >1986</option>
<option value=1987 >1987</option>
<option value=1988  selected>1988</option>
<option value=1989 >1989</option>
<option value=1990 >1990</option>
<option value=1991 >1991</option>
<option value=1992 >1992</option>
<option value=1993 >1993</option>
<option value=1994 >1994</option>
<option value=1995 >1995</option>
<option value=1996 >1996</option>
<option value=1997 >1997</option>
<option value=1998 >1998</option>
</SELECT>年<SELECT name="birth_date_m"><option value="1" >1</option>
<option value="2" >2</option>
<option value="3" >3</option>
<option value="4" >4</option>
<option value="5" >5</option>
<option value="6" >6</option>
<option value="7" >7</option>
<option value="8" SELECTED>8</option>
<option value="9" >9</option>
<option value="10" >10</option>
<option value="11" >11</option>
<option value="12" >12</option>
</SELECT>月 -->
<input type="text" name="elUser.shengri" value="<s:date format="yyyy-MM-dd" name="recruit.elUser.shengri"/>" readonly="readonly" id="shengri" onclick="setday(this)" class="frame7"/>
    </td>
</tr>
<tr>
  <th></th>
  <td colspan="3"><span id="txtError_birth_date_y"></span><span id="txtError_birth_date_m" style="margin-left:24px;"></span></td>
</tr>
<script language="javascript" type="text/javascript">
var birth_date = new MYRESUME.date_yyyym('birth_date',document.frmMain.birth_date_y,document.frmMain.birth_date_m,'出生日期');
</script>
<tr>
  <th><span>*</span><font id="LexpeButton">参加工作年份</font></th>
  <td colspan="3">
  	<input type="text" name="elUser.canjiagongzuoshijian" value="<s:date format="yyyy-MM-dd" name="recruit.elUser.canjiagongzuoshijian"/>" readonly="readonly" id="canjiagongzuoshijian" onclick="setday(this)" />
    </td>
</tr>
<tr id="conError_experience" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_experience"></td>
</tr>
<tr>
  <th width="140"><span>*</span><font id="Lmarital">婚姻状况</font></th>
  <td width="200">
<input type="Radio" name="recruit.hunyinzhuangkuang" value="1" <s:if test="recruit.hunyinzhuangkuang==1">CHECKED</s:if>>未婚
<input type="Radio" name="recruit.hunyinzhuangkuang" value="2" <s:if test="recruit.hunyinzhuangkuang==2">CHECKED</s:if> >已婚
<input type="Radio" name="recruit.hunyinzhuangkuang" value="3" <s:if test="recruit.hunyinzhuangkuang==3">CHECKED</s:if> >离异
<input type="Radio" name="recruit.hunyinzhuangkuang" value="5" <s:if test="recruit.hunyinzhuangkuang==5">CHECKED</s:if> >保密</td>
  <th width="116"><span>*</span><font id="LnationalityF_button">国家或地区</font></th>
  <td width="180"><input type="button" id="nationalityF_button" class="selectBut2" value="选择/修改" title="选择/修改" onFocus="this.blur()">
    <input type="hidden" name="nationality" value="489"></td>
</tr>
<tr>
  <td></td>
  <td></td>
  <td></td>
  <td><span id="txtError_nationality"></span></td>
</tr>
<script language="javascript">
	<!--
	var nationalityF = new MYRESUME.nationality('nationalityF',arrCity,document.frmMain.nationality);
	nationalityF.config = {width:400,title:'国家或地区',col:5,buttonL:20};
	nationalityF.showTextOnButton();
	//-->
	</script>
<tr>
  <th><span>*</span><font id="Lid_type">证件类型</font></th>
  <td><select name="elUser.zhengjianleixing">
      <option value="">请选择</option>
      
<option <s:if test="recruit.elUser.zhengjianleixing=='身份证'">SELECTED</s:if> >身份证</option>
<option <s:if test="recruit.elUser.zhengjianleixing=='护照'">SELECTED</s:if> >护照</option>
<option <s:if test="recruit.elUser.zhengjianleixing=='军官证'">SELECTED</s:if> >军官证</option>
<option <s:if test="recruit.elUser.zhengjianleixing=='香港身份证'">SELECTED</s:if> >香港身份证</option>
<option <s:if test="recruit.elUser.zhengjianleixing=='澳门身份证'">SELECTED</s:if> >澳门身份证</option>
<option <s:if test="recruit.elUser.zhengjianleixing=='港澳通行证'">SELECTED</s:if> >港澳通行证</option>
<option <s:if test="recruit.elUser.zhengjianleixing=='台胞证'">SELECTED</s:if> >台胞证</option>
<option <s:if test="recruit.elUser.zhengjianleixing=='其他'">SELECTED</s:if> >其他</option>
      
    </select></td>
  <th><span>*</span><font id="Lid_number">证件号码</font></th>
  <td><input type="text" name="elUser.shenfenzheng" class="frame7" value="${recruit.elUser.shenfenzheng }"></td>
</tr>
<tr>
  <td></td>
  <td><span id="conError_id_type" style="display:none;"><span id="txtError_id_type"></span></span></td>
  <td></td>
  <td><span id="conError_id_number" style="display:none;"><span id="txtError_id_number"></span></span></td>
</tr>
<tr>
  <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;海外工作/学习经历</th>
  <td colspan="3"><input type="radio" name="recruit.haiwaigongzuojingli" value="1" <s:if test="recruit.haiwaigongzuojingli==1">checked</s:if> onClick="overseasTime(true)" >
    有
    <input type="radio" name="recruit.haiwaigongzuojingli" value="0" <s:if test="recruit.haiwaigongzuojingli==0">checked</s:if> onClick="overseasTime(false)" checked>
    无&nbsp;&nbsp;&nbsp;&nbsp;
    <select name="overseasyear" style="display:none;">
      
       
<option value="1" >1年</option>
<option value="2" >2年</option>
<option value="3" >3年</option>
<option value="4" >4年</option>
<option value="5" >5年</option>
<option value="6" >5-10年</option>
<option value="10" >10年以上</option>
      
    </select></td>
</tr>
<tr>
  <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;政治面貌</th>
  <td colspan="3"> 
<input type="Radio" name="elUser.zhengzhi" value="中国共产党员（含预备党员）" <s:if test='recruit.elUser.zhengzhi=="中国共产党员（含预备党员）"'>checked="checked"</s:if> >中共党员(含预备党员) 
<input type="Radio" name="elUser.zhengzhi" value="中国共青团员" <s:if test='recruit.elUser.zhengzhi=="中国共青团员"'>checked="checked"</s:if> style=" width : 13px;">团员
<input type="Radio" name="elUser.zhengzhi" value="群众" <s:if test='recruit.elUser.zhengzhi=="群众"'>checked="checked"</s:if> style=" width : 13px;">群众
<input type="Radio" name="elUser.zhengzhi" value="民主党派" <s:if test='recruit.elUser.zhengzhi=="民主党派"'>checked="checked"</s:if> style=" width : 13px;">民主党派
 </td>
</tr>
<tr>
  <th><span>*</span><font id="LhukouF_button">户口所在地</font></th>
  <td colspan="3"><input type="button" id="hukouF_button" class="selectBut2" value="选择/修改" title="选择/修改" onFocus="this.blur()">
    <input type="hidden" name="hukou" value="715">
    <input type="hidden" name="hukou_p" value="544"></td>
</tr>
<tr id="conError_hukou" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_hukou"></td>
</tr>
<script language="javascript">
	<!--
	var hukouF = new MYRESUME.hukou('hukouF',arrCity,document.frmMain.hukou,nationalityF,'fnPassValue2');
	hukouF.config = {width:500,title:'户口所在地',col:5,buttonL:20};
	if(hukouF.parent&&hukouF.parent.hidden.value!=489 && hukouF.parent.hidden.value!=561&& hukouF.parent.hidden.value!=562 && hukouF.parent.hidden.value!=563){
		hukouF.button.disabled = true;
		if(hukouF.hidden.value!='') hukouF.hidden.value = '';
		if(hukouF.hidden.form[hukouF.hidden.name+'_p']&&hukouF.hidden.form[hukouF.hidden.name+'_p'].value!='') hukouF.hidden.form[hukouF.hidden.name+'_p'].value = '';
	}
	else hukouF.button.disabled = false;
	hukouF.showTextOnButton();
	//-->
	</script>
<tr>
  <th><span>*</span><font id="LresidenceF_button">现居住城市</font></th>
  <td colspan="3"><input type="button" id="residenceF_button" class="selectBut2" value="${recruit.juzhuchengshi }" title="选择/修改" onFocus="this.blur()">
    <input type="hidden" name="residence" value="">
    <input type="hidden" name="residence_p" value="544">
	<input type="hidden" name="residence_district" value=""></td>
</tr>
<tr id="conError_residence" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_residence"></td>
</tr>
<script language="javascript">
	<!--
	var residenceF = new MYRESUME.residence('residenceF',arrCity,document.frmMain.residence,null,'fnPassValue2');
	residenceF.config = {width:530,title:'现居住城市',col:5,buttonL:20,linkfn:'residence_dCity'};
	residenceF.showTextOnButton();
	//-->
	</script>
<tr>
  <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通信地址</th>
  <td><input type="text" name="elUser.address" class="frame7" value="${recruit.elUser.address }" id="address" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮政编码</td>
  <td><input type="text" name="recruit.youbian" class="frame7" value="${recruit.youbian }"></td>
</tr>
<tr>
  <th><span>*</span><font id="Lcontact_num0">首选联系方式</font></th>
  <td colspan="3"><select name="recruit.lianxifangshi" id="contact_rule">
      <option value="1"  <s:if test="recruit.lianxifangshi==1">selected</s:if>>移动电话</option>
      <option value="2" <s:if test="recruit.lianxifangshi==2">selected</s:if>>家庭电话</option>
      <option value="3" <s:if test="recruit.lianxifangshi==3">selected</s:if>>工作电话</option>
    </select>
    <input type="text" maxlength="20" name="elUser.movephone" id="contact_num0" class="frame6" value="${recruit.elUser.movephone }">
    <a href="javascript:void()" class="blue12line" onClick="addContact(2);return false;" id="aAddContact">添加其它联系方式</a> (最多3项)</td>
</tr>
<tr id="conError_contact0" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_contact0"></td>
</tr>
<tr id="trContact1" style="display:none;">
  <td></td>
  <td colspan="3"><select name="contact_type1">
      <option value="1" >移动电话</option>
      <option value="2" >家庭电话</option>
      <option value="3" >工作电话</option>
    </select>
    <input type="text" name="contact_num1" class="frame6" value="">
    <a href="javascript:void()" class="blue12line" onClick="delContact(1);return false;">删除</a></td>
</tr>
<tr id="conError_contact1" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_contact1"></td>
</tr>
<tr id="trContact2" style="display:none;">
  <td></td>
  <td colspan="3"><select name="contact_type2">
      <option value="1" >移动电话</option>
      <option value="2" >家庭电话</option>
      <option value="3" >工作电话</option>
    </select>
    <input type="text" name="contact_num2" class="frame6" value="">
    <a href="javascript:void()" class="blue12line" onClick="delContact(2);return false;">删除</a></td>
</tr>
<tr id="conError_contact2" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_contact2"></td>
</tr>
<tr>
  <th><span>*</span><font id="Lemail">电子邮箱</font></th>
  <td colspan="3"><input type="text" id="emailshow" name="elUser.email" value="${recruit.elUser.email }"  class="frame5"></td>
</tr>
<tr id="conError_emailmessage" style="display:none;">
  <td></td>
  <td colspan="3"><font id="message"></font></td>
</tr>
<tr id="conError_email" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_email"></td>
</tr>
<tr>
  <th>&nbsp;</th>
  <td colspan="3"><em>提示：修改后将改变您的登录用户名、找回密码和接收HR来信的邮箱，下次请使用新的邮箱登录。</em></td>
</tr>
<tr>
  <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人主页/博客</th>
  <td colspan="3"><input type="text" name="recruit.gerenzhuye" value="${recruit.gerenzhuye }" class="frame5"></td>
</tr>


		</table>
        <!-- end 个人信息 -->
		<div align="center"><input  class="btn7" name="save" value="保存" title="保存" type="submit">&nbsp;&nbsp;<a href="resume_alterInit.action?recruit.id=${recruit.id }" >返回</a></div>
		</form>
		<script language="javascript" type="text/javascript">
		<!--
		iniCheckForm('frmMain');

		function fnBlurIdnumber(){
			var idNumber = document.frmMain.id_number;
			if(idNumber.value!='') id_number.fnValidate();
		}
		MYRESUME.EventUtils.addEvent(document.frmMain.birth_date_y,'blur',fnBlurIdnumber);
		MYRESUME.EventUtils.addEvent(document.frmMain.birth_date_m,'blur',fnBlurIdnumber);
		MYRESUME.EventUtils.addEvent(document.frmMain.id_type,'blur',fnBlurIdnumber);

		function hideCurrentPopup(){
			if(window.currentlyVisiblePopup){
				changeObjectVisibility(window.currentlyVisiblePopup, 'hidden');

				switch(window.currentlyVisiblePopup){
					case 'nationalityF_div' : nationality.fnValidate();if(hukou.s==0) hukou.fnValidate();break;
					case 'hukouF_div' : hukou.fnValidate();break;
					case 'residenceF_div' : residence.fnValidate();break;
					case 'birth_date_y_div' : birth_date_y.fnValidate();break;
					case 'birth_date_m_div' : birth_date_m.fnValidate();break;
					case 'popupDiv_workExpYear' : expe.fnValidate();break;
				}

				window.currentlyVisiblePopup = false;
			}
		}
		MYRESUME.EventUtils.addEvent(document,'click',hideCurrentPopup)
		//-->
		</script>
	</div>
</div>
</div></div>
<div class="clear"></div>
<script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

<!--修改注册邮箱-->
<div id="newwid2" class="newwidstyle">
<form name="regform" id="regform" method="post" action="">
	<h2>修改注册邮箱<span class="newwidclose close"></span></h2>
	<div class="powerinfo2">
		<table>
			<tr>
				<th>当前邮箱</th>
				<td id="oldemail">liguanglongvip@126.com</td>
			</tr>
			<tr>
				<th valign="top">新邮箱地址</th>
				<td valign="top"><div class="emailaddrr"><input type="text" class="" name="email" id="email" /><input type="hidden" name="istest" id="istest" value="0"></div><span id="email_info"></span></td>
			</tr>
			<tr>
				<th></th>
				<td class="msgtxt_email">邮箱修改后将改变您的登录用户名、找回密码和接收HR来信的邮箱地址；下次请使用新的邮箱登录智联。</td>
			</tr>
			<tr>
				<th></th>
				<td><input type="button" class="nextstepbtn" id="nextstepbtn" /><input type="button" class="cancelbtn close" /></td>
			</tr>
		</table>
	</div>
</form>
</div>

<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/jquery-1.6.4.min.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v3/ajaxbase.js"></script>
<script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/new_v4/regFormVal.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/jquery.zlzp.popupbase.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/jquery.zlzp.popupdiv.js"></script>
<script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/my/v5/resume_foot.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnUtil.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnResumeChEn.js"></script>
<script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/fnWorkYear.js"></script>
</body>
</html>
