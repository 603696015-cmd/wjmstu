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
    <link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/subnav_resumestep1.css">
    <link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/myresume_popupdiv.css">
    <link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/New_v3/myresume_date.css">
    <link href="http://img00.zhaopin.cn/2012/css/my/v5/common.css" type="text/css" rel="stylesheet" />
    <link href="http://img00.zhaopin.cn/2012/css/ui/jquery.zlzp.popupdiv.css" type="text/css"
        rel="stylesheet" />
    <link rel="stylesheet" href="http://img00.zhaopin.cn/2012/css/ui/xw_selectcity/resume_style.css" />
    <script type="text/javascript" src="http://my.zhaopin.com/js/function.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/utilScript.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/arrdata.js"></script>
    <script type="text/javascript">
        arrJobtype = arrJobtype.concat(arrSubjobtype);
    </script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/myresume_popupdiv.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/myresume_util.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/myresume_radio.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/New_v4/myresume_date.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/buttonDivCheckbox.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/new_v3/formValidator.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/New_v3/ajaxbase.js"></script>
    <style>
        .cuowu
        {
            background: url(http://images.zhaopin.com/new4/article2/images/err_ico.jpg) no-repeat 10px 5px #FFE8EE;
            border: 1px solid #FF8F84;
            padding-left: 30px;
            font-size: 12px;
            line-height: 25px;
            color: #676664;
            width: 130px;
        }
        .cuowu1
        {
            background: url(http://images.zhaopin.com/new4/article2/images/err_ico.jpg) no-repeat 10px 5px #FFE8EE;
            border: 1px solid #FF8F84;
            padding-left: 30px;
            font-size: 12px;
            line-height: 25px;
            color: #676664;
            width: 400px;
        }
        .cuowu2
        {
            background: url(http://images.zhaopin.com/new4/article2/images/err_ico.jpg) no-repeat 10px 5px #FFE8EE;
            border: 1px solid #FF8F84;
            padding-left: 30px;
            font-size: 12px;
            line-height: 25px;
            color: #676664;
            width: 180px;
        }
        .classFormEle_error
        {
            background: none repeat scroll 0 0 #fff;
            border: solid 1px #FF8E84;
        }
        .table1_found .tab1Td2
        {
            line-height: 20px;
            padding-top: 10px;
            text-align: center;
        }
        .tishi
        {
            font-size: 12px;
            color: #FFF;
            font-weight: bold;
            width: 480px;
            line-height: 40px;
            background: url(http://images.zhaopin.com/new4/article2/images/tishi_ico.jpg) no-repeat 15px 5px #CF0505;
            padding-left: 10px;
            margin: 0 auto;
            display: none;
        }
    </style>
    <script language="javascript" type="text/javascript">
<!--
var username = new formEle(true,'name','username',['<div class="cuowu">请填写您的姓名</div>','<div class="cuowu">请正确填写你的姓名</div>','<div class="cuowu">请正确填写你的姓名</div>'],null,{min:2});
var birth_date_y = new formEle(true,'text','birth_date_y',['<div class="cuowu">请选择出生日期年份</div>']);
var birth_date_m = new formEle(true,'text','birth_date_m',['<div class="cuowu">请选择出生日期月份</div>']);
var expe = new formEle(true,'experience','experience',['<div class="cuowu">请选择参加工作年份</div>','<div class="cuowu">请选择参加工作月份</div>'],null,{miny:12,year:'document.frmMain.experience',month:'document.frmMain.experience_month',o4focus:'document.getElementById("expeButton")'});
var nationality = new formEle(true,'text','nationality',['<div class="cuowu">请选择国籍</div>']);
var hukou = new formEle(true,'text','hukou',['<div class="cuowu">请选择户口所在地</div>'],null,{condition:'document.frmMain.nationality.value=="489"',o4focus:'document.getElementById("hukouF_button")'});
var id_type = new formEle(true,'select','id_type',['<div class="cuowu">请选择证件类型</div>']);
var id_number = new formEle(true,'id','id_number',['<div class="cuowu">请填写证件号码</div>','<div class="cuowu">身份证号码填写有误</div>','<div class="cuowu2">请核对身份证号码和您的出生日期</div>'],null,{year:'document.frmMain.birth_date_y',month:'document.frmMain.birth_date_m',flag:'document.frmMain.id_type.value==1'});
var residence = new formEle(true,'text','residence',['<div class="cuowu">请选择现居住城市</div>'],null,{o4focus:'document.getElementById("residenceF_button")'});
var contact_num0 = new formEle(true,'textel','contact0',['<div class="cuowu">请填写您的联系方式</div>','<div class="cuowu">请输入正确的联系方式</div>']);
var email = new formEle(true,'email','email',['<div class="cuowu">请填写您的电子邮箱</div>','<div class="cuowu">电子邮箱格式有误</div>']);
var custom_commenttitle = new formEle(true,'text3','title',['<div class="cuowu">请填写自定义标题</div>','<div class="cuowu1">您输入的文字超过20个汉字或40个英文字符和标点符号</div>'],null,{length:40,condition:'document.frmMain.commenttitle.value==1'});
var comment = new formEle(true,'text','comment',['<div class="cuowu">请填写自我评价','自我评价内容过长</div>'],null,{length:250});
var employment_type = new formEle(true,'checkbox','employment',['<div class="cuowu">请选择期望工作性质</div>']);
var desired_Jobtype = new formEle(true,'text','jobtype',['<div class="cuowu">请选择期望从事职业</div>'],null,{o4focus:'document.getElementById("button_jobtypeF")'});
var desired_Industry = new formEle(true,'text','industry',['<div class="cuowu">请选择期望从事行业</div>'],null,{o4focus:'document.getElementById("button_industryF")'});
var desired_City = new formEle(true,'text','city',['<div class="cuowu">请选择期望工作地点</div>'],null,{o4focus:'document.getElementById("button_locationF")'});
var expected_salary = new formEle(true,'select','salary',['<div class="cuowu">请选择期望月薪</div>']);
var type = "0";
if (type=="2") {var paste = new formEle(true,'text','paste',['<div class="cuowu">请粘贴简历文本</div>','<div class="cuowu">粘贴文本过长</div>'],null,{length:20000});}
if (type=="3") {var upload = new formEle(true,'text','upload',['<div class="cuowu">请选择上传的word文档</div>']);}


function GetErrMsgCounte()
	{
		var arr=["username","birth_date_y","birth_date_m","experience","nationality","hukou","id_type","id_number","residence","contact0","contact1","contact2","email","emailmessage","title","comment","employment","jobtype","industry","city","salary"];
		var errMsgCounter=0;
		for(var i=0;i<arr.length;i++)
		{
			//alert(arr[i]);
			var t=document.getElementById("conError_"+arr[i]);

			//alert(t.style.display);
			if(t!=null)
			{
				//alert(t.innerHTML);
				if(t.style.display=='')
				{
					errMsgCounter++;
				}
			}
		}
		//alert(errMsgCounter);
		document.getElementById("errmsgCounter").innerHTML=errMsgCounter;
	}

function show(oEvent){
     document.getElementById("tishi").style.display = "block";
     e = window.event || oEvent;
     if (e.stopPropagation)
     {
         e.stopPropagation();
     }else{
         e.cancelBubble = true;
     }
   }


function fnStart(){
	

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

function fnFixCityPro(){
	var cityH_h = document.frmMain.hukou||null;
	var cityH_r = document.frmMain.residence||null;
	var provH_h = document.frmMain.hukou_p||null;
	var provH_r = document.frmMain.residence_p||null;
	if(cityH_h!=null&&provH_h!=null&&cityH_h.value!=''&&provH_h.value==''){
		if(window['hukouF']&&window['hukouF'].flagData) window['hukouF'].fnPassValue2(cityH_h.value);
		else findProId(provH_h,cityH_h.value);
	}
	if(cityH_r!=null&&provH_r!=null&&cityH_r.value!=''&&provH_r.value==''){
		if(window['residenceF']&&window['residenceF'].flagData) window['residenceF'].fnPassValue2(cityH_r.value);
		else findProId(provH_r,cityH_r.value);
	}
}
function findProId(h,cityId){
	if(window['arrCity']){
		for(var i=0;arrCity[i];i++){
			if(arrCity[i][0]==cityId){
				if(arrCity[i][1]!=0&&arrCity[i][1]!=489) h.value = arrCity[i][1];
				else h.value = arrCity[i][0];
				break;
			}
		}
	}
}

function goto(){

	delInvisiChar(document.frmMain.comment);
	fnFixCityPro();
	if(!document.frmMain.checkForm())
	{
		GetErrMsgCounte();
		show();
		return;
	}
	var type = "0";
	switch (type)
	{
		case "0"://普通简历
			document.frmMain.locationurl.value="resume_baseinfo2";
			break;
		case "1"://学生简历
			document.frmMain.locationurl.value="student_baseinfo";
			break;
		case "2"://高级人才简历（粘贴简历）
			document.frmMain.locationurl.value="resume_finished";
			break;
		case "3"://高级人才简历（粘贴简历）
			document.frmMain.locationurl.value="resume_finished";
			break;
	}
/*	if (document.frmMain.emailshow.disabled==false){
		document.frmMain.email1.value=document.frmMain.emailshow.value;
	}
	window.frmMain.submit();
	*/
	checkemail();
	
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

function overseasTime(action){
	var objS = document.frmMain.overseasyear;
	if(action){
		objS.style.display='';
		objS.selectedIndex=0;
	}
	else objS.style.display='none';
}

function commentTitle(s){
	var t = document.frmMain.custom_commenttitle;
	if(s && t) if(s.value=='1'){t.style.display='';}
	else {t.style.display='none';}
	//t.value='';
}

function workTime(){
	var y = document.frmMain.experience,s = document.getElementById('spanExMonth');

	if(y && s){
		if(parseInt(y.value)>1944 && parseInt(y.value)<=2014 && y.selectedIndex > 2){
			if(s.style.display=='none'){
				s.style.display='';
				//document.frmMain.experience_month.selectedIndex=0;
			}
		}
		else s.style.display='none';
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

	function checkemail(){
    	if(document.frmMain.emailshow.value.indexOf('@yahoo.cn') >0||document.frmMain.emailshow.value.indexOf('@yahoo.com.cn') >0){
			document.getElementById('conError_emailmessage').style.display='';
			document.getElementById('message').innerHTML = "<font color=red>检测到您填写的为雅虎邮箱，此邮箱即将停止服务，请更换为其他</font>";
			document.frmMain.emailshow.className = "frame5";
			return false;
		}
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
				document.frmMain.next.disabled = true;
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
					document.frmMain.next.disabled = true;
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
					document.frmMain.next.disabled = true;
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
        if(document.frmMain.emailshow.value.indexOf('@yahoo.cn') >0||document.frmMain.emailshow.value.indexOf('@yahoo.com.cn') >0){
			document.getElementById('conError_emailmessage').style.display='';
			document.getElementById('message').innerHTML = "<font color=red>检测到您填写的为雅虎邮箱，此邮箱即将停止服务，请更换为其他</font>";
			document.frmMain.emailshow.className = "frame5";
			return false;
		}
		var d = new Date();
		
		if (document.getElementById('conError_email').style.display=='none'){
			document.getElementById('conError_emailmessage').style.display='';
			if (document.frmMain.emailshow.value.toLowerCase() == document.frmMain.email1.value.toLowerCase()){
				document.getElementById('message').innerHTML = "<font color=red>Email未修改。</font>";
				document.frmMain.emailshow.className = "frame5 classFormEle_ok";
			}
			else{
				if (document.frmMain.emailshow.value.toLowerCase() == document.frmMain.email2.value.toLowerCase() || document.frmMain.emailshow.value.toLowerCase() == document.frmMain.loginName1.value.toLowerCase()){
					document.getElementById('message').innerHTML = "<font color=red>恭喜您，您可以使用该Email。</font>";
					document.frmMain.emailshow.className = "frame5 classFormEle_ok";
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
				  document.frmMain.emailshow.className = "frame5 classFormEle_error";
			  }
			  else{
				  document.getElementById('message').innerHTML = "<font color=red>恭喜您，您可以使用该Email。</font>";
				  document.frmMain.emailshow.className = "frame5 classFormEle_ok";
			  }
	
	   }
		   else
		   {
			 document.getElementById('message').innerHTML="请等待……";
		   }
		}


	}
// Task No: 81589 --modify by Andy.lu
	function bodyload()
	{
		//document.getElementById("tishi").style.display = "none";
		document.body.onclick = function(){
			document.getElementById("tishi").style.display = "none";
			};
		nationalityF.showTextOnButton();
		hukouF.showTextOnButton();
		residenceF.showTextOnButton();
		//eval('locationF.'+locationF.showSelected);
		eval('jobtypeF.'+jobtypeF.showSelected);
		eval('industryF.'+industryF.showSelected);
		workTime();
	}
// Task No: 81589 --modify by Andy.lu

/* 用户行为监控 */
var zpjk_cnt = {
	"urlfrom2" : getCookie("urlfrom2"),
	"adfcid2"  : getCookie("adfcid2"),
	"adfbid2"  : getCookie("adfbid2"),
	"urlfrom"  : getCookie("urlfrom"),
	"adfcid"   : getCookie("adfcid"),
	"adfbid"   : getCookie("adfbid"),
	"userid"   : getCookie("JsNewlogin")
};
if(zpjk_cnt.urlfrom!==null&&zpjk_cnt.adfcid!==null) (new Image()).src="http://cnt.zhaopin.com/user_action.html?sid="+zpjk_cnt.urlfrom+"&site="+zpjk_cnt.adfcid+"&action=nuresume&uid="+zpjk_cnt.userid;
else if(zpjk_cnt.urlfrom2!==null&&zpjk_cnt.adfcid2!==null) (new Image()).src="http://cnt.zhaopin.com/user_action.html?sid="+zpjk_cnt.urlfrom2+"&site="+zpjk_cnt.adfcid2+"&action=ouresume&uid="+zpjk_cnt.userid;
/* 用户行为监控 */
//-->
    </script>
    <script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/analytics.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/za/ga.js"></script>

</head>
<!-- Task No: 81589 --modify by Andy.lu-->
<body onload="bodyload()">
    <!-- Task No: 81589 --modify by Andy.lu-->
    <!-- popupDiv eg -->
    <div id="eg1" class="popupDiv" style="width: 250px; visibility: hidden; position: absolute;
        top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
        <div class="topLeft">
            <img src="" width="1" height="1"></div>
        <div class="topCenterWhite" style="width: 234px;">
            <img src="" width="1" height="1"></div>
        <div class="topRight">
            <img src="" width="1" height="1"></div>
        <div class="content">
            <div style="padding: 0 10px 5px 10px; line-height: 150%;">
                <div>
                    <b>社会简历</b></div>
                <div>
                    资深市场销售人员，六年销售和市场领域从业经验，年销售额过一千万；长期接触国外客户，英语能力强；对市场营销、 渠道开发及经销商管理有丰富的经验</div>
                <br>
                <div>
                    <b>学生简历</b></div>
                <div>
                    两年学生会主席生涯，培养了较强的组织能力和领导能力。曾在课余时间，参与过网站开发项目，能够较熟练地使用Photoshop，同时积累了一定的团队合作经验。</div>
            </div>
        </div>
        <div class="bottomLeft">
            <img src="" width="1" height="1"></div>
        <div class="bottomCenter" style="width: 234px;">
            <img src="" width="1" height="1"></div>
        <div class="bottomRight">
            <img src="" width="1" height="1"></div>
    </div>
    <div id="eg2" class="popupDiv" style="width: 250px; visibility: hidden; position: absolute;
        top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
        <div class="topLeft">
            <img src="" width="1" height="1"></div>
        <div class="topCenterWhite" style="width: 234px;">
            <img src="" width="1" height="1"></div>
        <div class="topRight">
            <img src="" width="1" height="1"></div>
        <div class="content">
            <div style="padding: 0 10px 5px 10px; line-height: 150%;">
                <div>
                    <b>社会简历</b></div>
                <div>
                    3年数据库管理员经验，熟悉Oracle数据库体系的备份与恢复；2年的网络信息系统管理经验，熟练掌握UNIX操作系统及网络管理技术；长期供职于外企，英语听说能力强。</div>
                <br>
                <div>
                    <b>学生简历</b></div>
                <div>
                    曾担任外联社社长一职，负责学校12个大小型活动的赞助商招揽，并成功筹集活动资金。具备良好的沟通能力和组织领导能力。</div>
            </div>
        </div>
        <div class="bottomLeft">
            <img src="" width="1" height="1"></div>
        <div class="bottomCenter" style="width: 234px;">
            <img src="" width="1" height="1"></div>
        <div class="bottomRight">
            <img src="" width="1" height="1"></div>
    </div>
    <!-- end popupDiv eg -->
    <!-- head -->
<style>
#hd2011hd {
	background:url(http://images.zhaopin.com/new2011/head/images/spri.gif) 0 -111px repeat-x;
	height:63px;
}
#hd2011hdmain, #hd2011wrapper {
	width:990px;
	margin:0 auto;
}

#hd2011logo {
	background:url(http://images.zhaopin.com/new2011/head/images/spri.gif) no-repeat;
	width:177px; height:56px;
	float:left;margin-left: 28px;_margin-left:14px;
}
#hd2011logo a {
	display:block;
	width:158px; height:56px;
}
</style>
<div id="hd2011hd">
	<div id="hd2011hdmain">
		<div id="hd2011logo"><a href="http://www.zhaopin.com/"></a></div>
		<img width="202" height="16" style="margin: 19px 0px 0px 26px; float:left" alt="" src="http://myimg.zhaopin.com/images/new_v4/title1.gif" complete="complete"/>
	<div class="subNav">
    	<p><a href="/myzhaopin/resume_index.asp">返回我的智联</a></p>
    </div>
	</div>
</div>
<div class="head_r_buzhou" style="width:1000px; margin:0 auto">
<div class="buzhou">
      <ul>
        <li class="bz1"><span>基本情况</span></li>
        <li class="bz2" style="display:"><span>教育与工作</span></li>
        <li class="bz3" style="display:"><span>附加信息</span></li>
        <li class="bz4"><span>完成</span></li>
      </ul>
    </div>
</div>

<!-- end head -->

    <div id="mainContainer" class="layout_found">
        <div class="huanying">
            欢迎您已经成功注册为智联用户，请完善您的简历，可以更便捷的找到工作哦！</div>
        <form name="frmMain" action="resume_baseinfo_save.action" method="post" enctype="multipart/form-data">
        <input type="hidden" name="recruit.didian" id="recruit.didian"/>
        <input type="hidden" name="recruit.zhiye" id="recruit.zhiye"/>
        <input type="hidden" name="recruit.hangye" id="recruit.hangye"/>
        <!-- popupDiv jobtype -->
        <div id="popupDiv_jobtypeF" class="popupDiv" style="width: 640px; visibility: hidden;
            position: absolute; top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
            <div class="topLeft">
                <img src="" width="1" height="1"></div>
            <div class="topCenterOrg" style="width: 624px;">
                <img src="" width="1" height="1"></div>
            <div class="topRight">
                <img src="" width="1" height="1"></div>
            <div class="title">
                <div style="width: 624px;">
                    <div style="float: left;">
                        &nbsp;&nbsp;&nbsp;<b class="blue14">职位类别：</b>最多可选 <span class="org14">5</span> 项</div>
                    <!--<div style="float:right;">[ <span class="blue12" onClick="hideCurrentPopup()" style="cursor:hand;">关闭</span> ]&nbsp;</div>-->
                </div>
            </div>
            <div class="content">
                <div style="width: 604px; padding: 10px 10px 5px 10px;">
                    <div>
                        <select name="pSel_jobtypeF" id="pSel_jobtypeF">
                        </select></div>
                    <script type="text/javascript">
                        // 职位大类数据填充
                        var selJobtype = document.getElementById('pSel_jobtypeF');
                        selJobtype.options.length = 0;
                        for (var i = 0; i < arrJobtype.length; i++) {
                            if (arrJobtype[i][1] === '0') {
                                selJobtype.options.add(new Option(arrJobtype[i][2], arrJobtype[i][0]));
                            }
                        }
                    </script>
                    <div id="itemDiv_jobtypeF">
                    </div>
                    <div id="cacheItemDiv_jobtypeF">
                    </div>
                    <br>
                    <div align="center">
                        <input type="button" value="确 定" class="btn3" name="buttonSave_jobtypeF" id="buttonSave_jobtypeF">&nbsp;&nbsp;&nbsp;&nbsp;<input
                            type="button" value="取 消" class="btn3" onclick="hideCurrentPopup();"></div>
                </div>
            </div>
            <div class="bottomLeft">
                <img src="" width="1" height="1"></div>
            <div class="bottomCenter" style="width: 624px;">
                <img src="" width="1" height="1"></div>
            <div class="bottomRight">
                <img src="" width="1" height="1"></div>
        </div>
        <!-- end popupDiv jobtype -->
        <!-- popupDiv industry -->
        <div id="popupDiv_industryF" class="popupDiv" style="width: 550px; visibility: hidden;
            position: absolute; top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
            <div class="topLeft">
                <img src="" width="1" height="1"></div>
            <div class="topCenterOrg" style="width: 534px;">
                <img src="" width="1" height="1"></div>
            <div class="topRight">
                <img src="" width="1" height="1"></div>
            <div class="title">
                <div style="width: 534px;">
                    <div style="float: left;">
                        &nbsp;&nbsp;&nbsp;<b class="blue14">行业类别：</b></div>
                    <!--<div style="float:right;">[ <span class="blue12" onClick="hideCurrentPopup()" style="cursor:hand;">关闭</span> ]&nbsp;</div>-->
                </div>
            </div>
            <div class="content">
                <div style="width: 514px; padding: 10px 10px 5px 10px;">
                    <div id="itemDiv_industryF">
                    </div>
                    <br>
                    <div align="center">
                        <input type="button" value="确 定" class="btn3" name="buttonSave_industryF" id="buttonSave_industryF">&nbsp;&nbsp;&nbsp;&nbsp;<input
                            type="button" value="取 消" class="btn3" onclick="hideCurrentPopup();"></div>
                </div>
            </div>
            <div class="bottomLeft">
                <img src="" width="1" height="1"></div>
            <div class="bottomCenter" style="width: 534px;">
                <img src="" width="1" height="1"></div>
            <div class="bottomRight">
                <img src="" width="1" height="1"></div>
        </div>
        <!-- end popupDiv industry -->
        <!-- popupDiv location -->
        <div id="popupDiv_locationF" class="popupDiv" style="width: 540px; visibility: hidden;
            position: absolute; top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
            <div class="topLeft">
                <img src="" width="1" height="1"></div>
            <div class="topCenterOrg" style="width: 524px;">
                <img src="" width="1" height="1"></div>
            <div class="topRight">
                <img src="" width="1" height="1"></div>
            <div class="title">
                <div style="width: 524px;">
                    <div style="float: left;">
                        &nbsp;&nbsp;&nbsp;<b class="blue14">工作地点：</b>最多可选 <span class="org14">5</span> 项</div>
                    <!--<div style="float:right;">[ <span class="blue12" onClick="hideCurrentPopup()" style="cursor:hand;">关闭</span> ]&nbsp;</div>-->
                </div>
            </div>
            <div class="content">
                <div style="width: 504px; padding: 10px 10px 5px 10px;">
                    <div>
                        <select name="pSel_locationF" id="pSel_locationF">
                            <option value="489" selected>全国</option>
                            <option value="530">&nbsp;&nbsp;--北京</option>
                            <option value="538">&nbsp;&nbsp;--上海</option>
                            <option value="548">&nbsp;&nbsp;--广东</option>
                            <option value="531">&nbsp;&nbsp;--天津</option>
                            <option value="546">&nbsp;&nbsp;--湖北</option>
                            <option value="556">&nbsp;&nbsp;--陕西</option>
                            <option value="552">&nbsp;&nbsp;--四川</option>
                            <option value="535">&nbsp;&nbsp;--辽宁</option>
                            <option value="536">&nbsp;&nbsp;--吉林</option>
                            <option value="539">&nbsp;&nbsp;--江苏</option>
                            <option value="544">&nbsp;&nbsp;--山东</option>
                            <option value="540">&nbsp;&nbsp;--浙江</option>
                            <option value="549">&nbsp;&nbsp;--广西壮族自治区</option>
                            <option value="541">&nbsp;&nbsp;--安徽</option>
                            <option value="532">&nbsp;&nbsp;--河北</option>
                            <option value="533">&nbsp;&nbsp;--山西</option>
                            <option value="534">&nbsp;&nbsp;--内蒙古自治区</option>
                            <option value="537">&nbsp;&nbsp;--黑龙江</option>
                            <option value="542">&nbsp;&nbsp;--福建</option>
                            <option value="543">&nbsp;&nbsp;--江西</option>
                            <option value="545">&nbsp;&nbsp;--河南</option>
                            <option value="547">&nbsp;&nbsp;--湖南</option>
                            <option value="550">&nbsp;&nbsp;--海南</option>
                            <option value="551">&nbsp;&nbsp;--重庆</option>
                            <option value="553">&nbsp;&nbsp;--贵州</option>
                            <option value="554">&nbsp;&nbsp;--云南</option>
                            <option value="555">&nbsp;&nbsp;--西藏自治区</option>
                            <option value="557">&nbsp;&nbsp;--甘肃</option>
                            <option value="558">&nbsp;&nbsp;--青海</option>
                            <option value="559">&nbsp;&nbsp;--宁夏回族自治区</option>
                            <option value="560">&nbsp;&nbsp;--新疆维吾尔自治区</option>
                            <option value="561">&nbsp;&nbsp;--香港特别行政区</option>
                            <option value="562">&nbsp;&nbsp;--澳门特别行政区</option>
                            <option value="563">&nbsp;&nbsp;--台湾</option>
                            <option value="481">阿根廷</option>
                            <option value="482">澳大利亚</option>
                            <option value="483">奥地利</option>
                            <option value="484">白俄罗斯</option>
                            <option value="485">比利时</option>
                            <option value="486">巴西</option>
                            <option value="487">保加利亚</option>
                            <option value="488">加拿大</option>
                            <option value="490">塞浦路斯</option>
                            <option value="491">捷克</option>
                            <option value="492">丹麦</option>
                            <option value="493">埃及</option>
                            <option value="494">芬兰</option>
                            <option value="495">法国</option>
                            <option value="496">德国</option>
                            <option value="497">希腊</option>
                            <option value="498">匈牙利</option>
                            <option value="499">冰岛</option>
                            <option value="500">印度</option>
                            <option value="501">印度尼西亚</option>
                            <option value="502">爱尔兰</option>
                            <option value="503">以色列</option>
                            <option value="504">意大利</option>
                            <option value="505">日本</option>
                            <option value="506">韩国</option>
                            <option value="507">科威特</option>
                            <option value="508">马来西亚</option>
                            <option value="509">荷兰</option>
                            <option value="510">新西兰</option>
                            <option value="511">挪威</option>
                            <option value="513">巴基斯坦</option>
                            <option value="514">波兰</option>
                            <option value="515">葡萄牙</option>
                            <option value="516">俄罗斯联邦</option>
                            <option value="517">沙特阿拉伯</option>
                            <option value="518">新加坡</option>
                            <option value="519">南非</option>
                            <option value="520">西班牙</option>
                            <option value="521">瑞典</option>
                            <option value="522">瑞士</option>
                            <option value="523">泰国</option>
                            <option value="524">土耳其</option>
                            <option value="525">乌克兰</option>
                            <option value="526">阿联酋</option>
                            <option value="527">英国</option>
                            <option value="528">美国</option>
                            <option value="529">越南</option>
                            <option value="913">安哥拉</option>
                            <option value="914">加纳</option>
                            <option value="915">尼日利亚</option>
                            <option value="916">坦桑尼亚</option>
                            <option value="917">乌干达</option>
                            <option value="918">阿尔及利亚</option>
                            <option value="919">塞内加尔</option>
                            <option value="512">其他</option>
                        </select></div>
                    <div id="itemDiv_locationF">
                    </div>
                    <div id="cacheItemDiv_locationF">
                    </div>
                    <br>
                    <div align="center">
                        <input type="button" value="确 定" class="btn3" name="buttonSave_locationF" id="buttonSave_locationF">&nbsp;&nbsp;&nbsp;&nbsp;<input
                            type="button" value="取 消" class="btn3" onclick="hideCurrentPopup();"></div>
                </div>
            </div>
            <div class="bottomLeft">
                <img src="" width="1" height="1"></div>
            <div class="bottomCenter" style="width: 524px;">
                <img src="" width="1" height="1"></div>
            <div class="bottomRight">
                <img src="" width="1" height="1"></div>
        </div>
        <!-- end popupDiv location -->
        <div class="row">
            <!-- 个人信息 -->
            <h2>
                <div class="geren">
                    个人信息</div>
                <div class="bitian">
                    <span>*</span>为必填项</div>
            </h2>
            <table width="644" border="0" cellpadding="0" cellspacing="0" class="table1_found">
                
<tr>
  <th><span>*</span><font id="Lusername">姓名</font></th>
  <td colspan="3"><input type="text" name="elUser.realname" class="frame7" value="<s:property value="elUser.realname"/>" id="elUser.realname" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td>
</tr>
<tr id="conError_username" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_username"></td>
</tr>
<tr>
  <th><span>*</span><font id="Lgender">性别</font></th>
  <td colspan="3">
  	<input type="radio" name="elUser.sex" value="男"
		<s:if test="elUser.sex==\"男\"">checked="checked"</s:if> />
		男
	<input type="radio" name="elUser.sex" value="女"
			<s:if test="elUser.sex==\"女\"">checked="checked"</s:if> />
		女
  </td>
</tr>
<tr>
  <th><span>*</span><font id="Lbirth_date_y">出生日期</font></th>
  <td colspan="3">
		<input type="text" name="elUser.shengri" value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>" readonly="readonly" id="shengri" onclick="setday(this)" />
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
  <td colspan="3"><input type="button" id="expeButton" name="elUser.canjiagongzuoshijian" class="selectBut2" value="选择/修改" title="选择/修改" onFocus="this.blur()" mzpmodule="workExpYearFac" datay="@0|无工作经验@2002|2002或更早@2003|2003@2004|2004@2005|2005@2006|2006@2007|2007@2008|2008@2009|2009@2010|2010@2011|2011@2012|2012@2013|2013@2014|2014@" lang="cn" year="document.frmMain.experience" month="document.frmMain.experience_month" />
    <input type="hidden" name="experience" value="2011" />
    <input type="hidden" name="experience_month" value="11" />
    <input type="hidden" name="expe" /></td>
</tr>
<tr id="conError_experience" style="display:none;">
  <td></td>
  <td colspan="3" id="txtError_experience"></td>
</tr>
<tr>
  <th width="140"><span>*</span><font id="Lmarital">婚姻状况</font></th>
  <td width="200">
<input type="Radio" name="recruit.hunyinzhuangkuang" value="1" CHECKED>未婚
<input type="Radio" name="recruit.hunyinzhuangkuang" value="2" >已婚
<input type="Radio" name="recruit.hunyinzhuangkuang" value="3" >离异
<input type="Radio" name="recruit.hunyinzhuangkuang" value="5" >保密</td>
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
  <td>
  	<select name="elUser.zhengjianleixing">  
		<option value="身份证">身份证</option> 
	</select>
  </td>
  <th><span>*</span><font id="Lid_number">证件号码</font></th>
  <td>
  	 <s:textfield name="elUser.shenfenzheng" size="20" id="shenfenzheng" class="frame7"/>
  </td>
</tr>
<tr>
  <td></td>
  <td><span id="conError_id_type" style="display:none;"><span id="txtError_id_type"></span></span></td>
  <td></td>
  <td><span id="conError_id_number" style="display:none;"><span id="txtError_id_number"></span></span></td>
</tr>
<tr>
  <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;海外工作/学习经历</th>
  <td colspan="3"><input type="radio" name="recruit.haiwaigongzuojingli" value="1" onClick="overseasTime(true)" >
    有
    <input type="radio" name="recruit.haiwaigongzuojingli" value="0" onClick="overseasTime(false)" checked>
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
	<input type="Radio" name="elUser.zhengzhi" value="中国共产党员（含预备党员）" <s:if test="elUser.zhengzhi=='中国共产党员（含预备党员）'">checked="checked"</s:if>>中共党员(含预备党员) 
	<input type="Radio" name="elUser.zhengzhi" value="中国共青团员" <s:if test="elUser.zhengzhi=='中国共青团员'">checked="checked"</s:if>>团员
	<input type="Radio" name="elUser.zhengzhi" value="群众" <s:if test="elUser.zhengzhi=='群众'">selected='selected'</s:if>>群众
	<input type="Radio" name="elUser.zhengzhi" value="民主党派" <s:if test="elUser.zhengzhi=='民主党派'">checked="checked"</s:if>>民主党派
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
  <td colspan="3"><input type="button" id="residenceF_button" class="selectBut2" value="选择/修改" title="选择/修改" onFocus="this.blur()">
    <input type="hidden" name="residence" value="715">
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
  <td><input type="text" name="elUser.address" class="frame7" value="<s:property value="elUser.address"/>" id="address" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮政编码</td>
  <td><input type="text" name="recruit.youbian" class="frame7" value=""></td>
</tr>
<tr>
  <th><span>*</span><font id="Lcontact_num0">首选联系方式</font></th>
  <td colspan="3"><select name="recruit.lianxifangshi" id="contact_rule">
      <option value="1" selected>移动电话</option>
      <option value="2" >家庭电话</option>
      <option value="3" >工作电话</option>
    </select>
    
    <s:textfield name="elUser.movephone" id="movephone" class="frame6"/>
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
  <td colspan="3">
  	<s:textfield name="elUser.email" id="email" disabled="disabled" class="frame5"/>
    <input type="hidden" name="email1" id= "email1" value="${elUser.email }">
    <input type="hidden" name="loginName1"  value="${elUser.email }">
    <input type="hidden" name="email2"  value="">
    <a href="javascript:void(0);" id="emailChange" class="blue12line">修改</a>
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
  <td colspan="3"><input type="text" name="recruit.gerenzhuye" value="" class="frame5"></td>
</tr>


            </table>
            <!-- end 个人信息 -->
            <!-- 自我评价 -->
            <h2>
                <div class="geren">
                    自我评价</div>
                <div class="bitian">
                    <span>*</span>为必填项</div>
            </h2>
            <table width="870" border="0" cellpadding="0" cellspacing="0" class="table1_found">
        <colgroup><col width="140" /><col width="494" /></colgroup>
        <tr><td colspan="2"><span class="grey13">智联建议您对自己做一个简短评价，简明扼要地描述您的职业优势，让用人单位快速了解您！优秀的自我评价可以吸引招聘人员的眼球，为您的简历增色不少！</span></td>
			</tr>
  <tr>
    <th><span>*</span><font id="Lcustom_commenttitle">标题</font></th>
    <td><input type="hidden" name="commentID" value="">
      <select name="commenttitle" onChange="commentTitle(this)" style="float:left;margin-right:10px;">
        <option value="自我评价" name="recruit.biaoti">自我评价</option>
        <option value="职业目标" name="recruit.biaoti">职业目标</option>
        <option value="1" name="recruit.biaoti">自定义标题</option>
      </select>
      <input type="text" name="custom_commenttitle" value="" size="58" style="display:none;float:left;" maxlength="100" id="custom_commenttitle" mzpmodule="resumeChEnFac" tiptext="" lang="en"></td>
  </tr>
  <tr id="conError_title" style="display:none;">
    <th></th>
    <td id="txtError_title"></td>
  </tr>
  <tr>
    <th valign="top"><span>*</span><font id="Lcomment">内容</font></th>
    <td><table width="678" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="423"><textarea name="recruit.neirong" cols="80" rows="8" onBlur="submitCallback('comment='+ua(document.frmMain.comment.value),'usermaster_blacklists_save.asp?iChecked=0&iBlocked=0',ajaxReturn,'post','');" onkeyup="calWordNumRemained2(250,this,document.getElementById('maxWord'))" onfocus="calWordNumRemained2(250,this,document.getElementById('maxWord'))"  id="comment" mzpmodule="resumeChEnFac" tiptext="" lang="en"></textarea></td>
                <td width="255" valign="top" style="padding-left:10px;"><span class="grey12">
                  <p style="line-height:20px;">填写文字在100个字以上评定等级，少于不计算，内容越详细，等级越高</p><br><p>范例一：<a href="#" onMouseOver="stopTimeG();showPopup('eg1',event,getXY(document.getElementById('eg1Img')).x+12,getXY(document.getElementById('eg1Img')).y+10);" onMouseOut="startTimeG();" onClick="return false;"><img src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" border="0" align="absmiddle" id="eg1Img"></a>&nbsp;&nbsp;范例二：<a href="#" onMouseOver="stopTimeG();showPopup('eg2',event,getXY(document.getElementById('eg2Img')).x+12,getXY(document.getElementById('eg2Img')).y+10);" onMouseOut="startTimeG();" onClick="return false;"><img src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" border="0" align="absmiddle" id="eg2Img"></a>&nbsp;&nbsp;&nbsp;</p>
				<br>
				<div class="shuru"><span id="maxWord"></span>字</div>
				</span></td>
              </tr>
            </table></td>
  </tr>
  <tr id="conError_comment" style="display:none;">
    <th></th>
    <td id="txtError_comment"></td>
  </tr>
</table>
<script type="text/javascript">
function calWordNumRemained2(max,o,show){
	if(o){
		var l=o.value.length;
		var r=parseInt(max-l);
		if(r<0) r = 0;
		if(show){
			show.innerHTML=r;
		}
	}
}
calWordNumRemained2(250,document.frmMain.comment,document.getElementById('maxWord'))
</script>
<!-- popupDiv eg -->
<div id="eg10" class="popupDiv" style="width:250px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;" onClick="event.cancelBubble=true;">
  <div class="topLeft"><img src="" width="1" height="1"></div>
  <div class="topCenterWhite" style="width:234px;"><img src="" width="1" height="1"></div>
  <div class="topRight"><img src="" width="1" height="1"></div>
  <div class="content">
    <div style="padding:0 10px 5px 10px;line-height:150%;">
      <div><b>字数统计</b></div>
      <div id="eg10Div"></div>
      <br>
    </div>
  </div>
  <div class="bottomLeft"><img src="" width="1" height="1"></div>
  <div class="bottomCenter" style="width:234px;"><img src="" width="1" height="1"></div>
  <div class="bottomRight"><img src="" width="1" height="1"></div>
</div>
<div id="eg11" class="popupDiv" style="width:250px;visibility:hidden;position:absolute;top:-100px;left:-100px;z-index:99;" onClick="event.cancelBubble=true;">
  <div class="topLeft"><img src="" width="1" height="1"></div>
  <div class="topCenterWhite" style="width:234px;"><img src="" width="1" height="1"></div>
  <div class="topRight"><img src="" width="1" height="1"></div>
  <div class="content">
    <div style="padding:0 10px 5px 10px;line-height:150%;">
      <div><b>字数统计</b></div>
      <div id="eg11Div"></div>
      <br>
    </div>
  </div>
  <div class="bottomLeft"><img src="" width="1" height="1"></div>
  <div class="bottomCenter" style="width:234px;"><img src="" width="1" height="1"></div>
  <div class="bottomRight"><img src="" width="1" height="1"></div>
</div>
<!-- end popupDiv eg -->

            <!-- end 自我评价 -->
            <!-- 求职意向 -->
            <h2>
                <div class="geren">
                    求职意向</div>
                <div class="bitian">
                    <span>*</span>为必填项</div>
            </h2>
            <table width="634" border="0" cellpadding="0" cellspacing="0" class="table1_found">
		<colgroup><col width="140" /><col width="494" /></colgroup>
		<input type="hidden" name="item_id" value="">
		<tr><th><span>*</span><font id="Lemployment">期望工作性质</font></th>
			<td>
<input type="CheckBox" name="recruit.xingzhi" value="2" >全职
<input type="CheckBox" name="recruit.xingzhi" value="1" >兼职
<input type="CheckBox" name="recruit.xingzhi" value="4" >实习</td></tr>
		<tr id="conError_employment" style="display:none;"><td></td><td id="txtError_employment"></td></tr>
		<tr><th><span>*</span><font id="Lcity">期望工作地点</font></th>
			<td><input type="button" class="selectBut2"  id="button_locationF" value="选择/修改" onFocus="this.blur()"><img src="" width="1" height="1" id="popupDivImg_locationF" border="0"><select name="cache_locationF" id="cache_locationF" style="display:none;"></select><input type="hidden" id="desired_City" name="desired_City" value=""></td></tr>
		<tr><td></td>
			<td><span id="selItem_locationF" style="line-height:150%;"></span></td></tr>
		<tr id="conError_city" style="display:none;"><td></td><td id="txtError_city"></td></tr>
		<tr><th><span>*</span><font id="Lbutton_jobtypeF">期望从事职业</font></th>
			<td><input type="button" class="selectBut2"  id="button_jobtypeF" value="选择/修改" onFocus="this.blur()"><img src="" width="1" height="1" id="popupDivImg_jobtypeF" border="0"><select name="cache_jobtypeF" id="cache_jobtypeF" style="display:none;"></select><input type="hidden" name="desired_Jobtype" value=""></td></tr>
		<tr><td></td>
			<td><span id="selItem_jobtypeF" style="line-height:150%;"></span></td></tr>
		<tr id="conError_jobtype" style="display:none;"><td></td><td id="txtError_jobtype"></td></tr>
		<tr><th><span>*</span><font id="Lbutton_industryF">期望从事行业</font></th>
			<td><input type="button" class="selectBut2" id="button_industryF" value="选择/修改" onFocus="this.blur()"><img src="" width="1" height="1" id="popupDivImg_industryF" border="0"><select name="cache_industryF" id="cache_industryF" style="display:none;"></select><input type="hidden" name="desired_Industry" value=""></td></tr>
		<tr><td></td>
			<td><span id="selItem_industryF" style="line-height:150%;"></span></td></tr>
		<tr id="conError_industry" style="display:none;"><td></td><td id="txtError_industry"></td></tr>
		<script language="javascript" type="text/javascript">
		<!--
		//var locationF = new lib_popupDivC('locationF','location','document.frmMain.desired_City','arrCity',null,'writeDivItem','clickCheckbox','clickCheckboxP','clickCheckboxC','showItem','saveCity')
		//locationF.config = {x:-160,y:5,col:5,max:5,tdWidthF:'fixed',tdTextL:12,linkfn:'dCity_residence'};
		//eval('locationF.'+locationF.showSelected);
		var jobtypeF = new lib_popupDivC('jobtypeF','jobtype','document.frmMain.desired_Jobtype','arrJobtype',null,'writeDivItem','clickCheckbox','clickCheckboxP','clickCheckboxC','showItem')
		jobtypeF.config = {x:-160,y:5,col:3,max:5,tdWidthF:'fixed',tdTextL:24};
		eval('jobtypeF.'+jobtypeF.showSelected);
		var industryF = new lib_popupDivC('industryF','industry','document.frmMain.desired_Industry','arrIndustry',null,'writeDivItem','clickCheckbox',null,null,'showItem')
		industryF.config = {x:-160,y:5,col:2,max:999};
		eval('industryF.'+industryF.showSelected);
		//locationF.op4Child();
		//-->
		
		</script>
		<tr><th><span>*</span><font id="Lexpected_salary">期望月薪(税前)</font></th>
			<td><select name="recruit.yuexin" id="expected_salary"><option value="" selected="selected">请选择</option>
<option value="1000" >1000元/月以下</option>
<option value="2000" >1000-2000元/月</option>
<option value="4000" >2001-4000元/月</option>
<option value="6000" >4001-6000元/月</option>
<option value="8000" >6001-8000元/月</option>
<option value="10000" >8001-10000元/月</option>
<option value="15000" >10001-15000元/月</option>
<option value="25000" >15000-25000元/月</option>
<option value="99999" >25000元/月以上</option>
<option value="0000" >面议</option></select> /月</td></tr>
		<tr id="conError_salary" style="display:none;"><td></td><td id="txtError_salary"></td></tr>
		<tr><td colspan="2" class="tab1Td1"><br><input type="Radio" name="recruit.status" value="1" CHECKED>我目前处于离职状态，可立即上岗<br><input type="Radio" name="recruit.status" value="2" >我目前在职，正考虑换个新环境（如有合适的工作机会，到岗时间一个月左右）<br><input type="Radio" name="recruit.status" value="3" >目前暂无跳槽打算<br><input type="Radio" name="recruit.status" value="4" >我对现有工作还算满意，如有更好的工作机会，我也可以考虑。（到岗时间另议）<br><input type="Radio" name="recruit.status" value="5" >应届毕业生</td></tr>
		<tr><th colspan="2" class="tab1Td2"><input name="showInit" type="checkbox" value="1" checked checked> 将此求职意向显示在我的简历中</th></tr>
		</table>

            <!-- end 求职意向 -->
            <!-- 粘贴简历 -->
            <div style="display: none;">
                <h2>
                    <div class="geren">
                        文本简历</div>
                    <div class="bitian">
                        <span>*</span>为必填项</div>
                </h2>
                <table width="634" border="0" cellpadding="0" cellspacing="0" class="table1_found">
                    <colgroup>
                        <col width="140" />
                        <col width="494" />
                    </colgroup>
                    <tr>
                        <td>
                        </td>
                        <td>
                            <span class="grey12">为确保信息安全，请勿在粘贴的简历中透露你的联系方式！<br>
                                您可以将已有的简历拷贝粘贴到下列文本框中&nbsp;&nbsp;（<span id="maxWord41" style="display: none"></span><span
                                    id="maxWord42">限20000个字</span><a onmouseover="stopTimeG();showPopup('eg11',event,getXY(document.getElementById('eg11Img')).x+12,getXY(document.getElementById('eg11Img')).y+10)"
                                        onclick="return false;" onmouseout="startTimeG();" href="http://my.zhaopin.com/myzhaopin/resume_baseinfo.asp?ext_id=JR028855777R90000009000&amp;resume_id=3430176&amp;Version_Number=1&amp;language_id=1&amp;LocationUrl=resume_list&amp;DYWE=1228439254234.89987.1229671288.1229909284.33#"><img
                                            id="eg11Img" src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" align="absMiddle"
                                            border="0" style="display: none"></a>）</span>
                        </td>
                    </tr>
                    <tr>
                        <th valign="top">
                            <input type="hidden" name="paste_rowid" value=""><span>*</span>简历文本
                        </th>
                        <td>
                            <textarea name="paste" cols="80" rows="8" onfocus="calWordNumRemained(20000,this,document.getElementById('maxWord41'),document.getElementById('maxWord42'),'eg11')"
                                onblur="iniWordNum(document.getElementById('maxWord41'),document.getElementById('maxWord42'),'限20000字以内','eg11')"
                                onkeyup="calWordNumRemained(20000,this,document.getElementById('maxWord41'),document.getElementById('maxWord42'),'eg11')"
                                id="paste" mzpmodule="resumeChEnFac" tiptext="" lang="en"></textarea>
                        </td>
                    </tr>
                    <tr id="conError_paste" style="display: none;">
                        <td>
                        </td>
                        <td id="txtError_paste">
                        </td>
                    </tr>
                </table>
            </div>
            <!-- end 粘贴简历 -->
            <!-- 上传word -->
            <div style="display: none;">
                <h2>
                    <div class="geren">
                        上传一份word附件简历</div>
                    <div class="bitian">
                        <span>*</span>为必填项</div>
                </h2>
                <table width="634" border="0" cellpadding="0" cellspacing="0" class="table1_found">
                    <colgroup>
                        <col width="140" />
                        <col width="494" />
                    </colgroup>
                    <tr>
                        <th>
                            <span>*</span>选择简历文档：</thd>
                            <td>
                                <input type='file' name='upload' size='60'><input type='hidden' name='upload_text' value=''>
                            </td>
                    </tr>
                    <tr>
                        <td>
                        </td>
                        <td>
                            <span class="grey12">您可以直接上传已编辑好的简历文档，请选择正确的路径后上传。<br>
                                仅支持Word文档（.doc或.docx）Pdf文档（.pdf），文件大小不要超过500K。</span>
                        </td>
                    </tr>
                    <tr id="conError_upload" style="display: none;">
                        <td>
                        </td>
                        <td id="txtError_upload">
                        </td>
                    </tr>
                </table>
            </div>
            <!-- end 上传word -->
            <input type="hidden" name="ext_id" value="JR475973035R90250004000">
            <input type="hidden" name="Resume_ID" value="206403884">
            <input type="hidden" name="Version_number" value="1">
            <input type="hidden" name="language_id" value="1">
            <input type="hidden" name="resume_type" value="0">
            <input type="hidden" name="locationurl" value="resume_baseinfo2">
            <div class="tishi" id="tishi" style="padding-left: 55px; margin-bottom: 10px;">
                您有“<span id="LErrmsgInfo"></span>”等<span id="errmsgCounter"></span>项必要信息未填写，请填写完毕后再保存</div>
            <div class="btnCon_found">
                <a href="resume_index.asp">返回我的智联</a><input type="button" class="btn6" value="保存并下一步"
                    title="保存并下一步" name="next" onclick="send()"></div>
        </div>
        </form>
        <script type="text/javascript">
        	function send(){
        		var didian = document.getElementById("selItem_locationF").outerText;
        		var zhiye = document.getElementById("selItem_jobtypeF").outerText;
        		var hangye = document.getElementById("selItem_industryF").outerText;
        		document.getElementById("recruit.didian").value = didian;
        		document.getElementById("recruit.zhiye").value = zhiye;
        		document.getElementById("recruit.hangye").value = hangye;
        		alert(didian);
        		alert(zhiye);
        		alert(hangye);
        		document.frmMain.submit();
        	}
		<!--
            iniCheckForm('frmMain');

            function fnBlurIdnumber() {
                var idNumber = document.frmMain.id_number;
                if (idNumber.value != '') id_number.fnValidate();
            }
            MYRESUME.EventUtils.addEvent(document.frmMain.birth_date_y, 'blur', fnBlurIdnumber);
            MYRESUME.EventUtils.addEvent(document.frmMain.birth_date_m, 'blur', fnBlurIdnumber);
            MYRESUME.EventUtils.addEvent(document.frmMain.id_type, 'blur', fnBlurIdnumber);
            function fnBlurCustomcommen() {
                if (custom_commenttitle.s != null && custom_commenttitle.s >= 0) custom_commenttitle.fnValidate();
            }
            MYRESUME.EventUtils.addEvent(document.frmMain.commenttitle, 'change', fnBlurCustomcommen);

            function hideCurrentPopup() {
                if (window.currentlyVisiblePopup) {
                    changeObjectVisibility(window.currentlyVisiblePopup, 'hidden');

                    switch (window.currentlyVisiblePopup) {
                        case 'nationalityF_div': nationality.fnValidate(); if (hukou.s == 0) hukou.fnValidate(); break;
                        case 'hukouF_div': hukou.fnValidate(); break;
                        case 'residenceF_div': residence.fnValidate(); if (desired_City.s == 0) timeCon = setTimeout('desired_City.fnValidate()', 100); break;
                        case 'popupDiv_jobtypeF': desired_Jobtype.fnValidate(); break;
                        case 'popupDiv_industryF': desired_Industry.fnValidate(); break;
                        case 'popupDiv_locationF': desired_City.fnValidate(); break;
                        case 'birth_date_y_div': birth_date_y.fnValidate(); break;
                        case 'birth_date_m_div': birth_date_m.fnValidate(); break;
                        case 'popupDiv_workExpYear': expe.fnValidate(); break;
                    }

                    window.currentlyVisiblePopup = false;
                }
            }
            MYRESUME.EventUtils.addEvent(document, 'click', hideCurrentPopup);
		//-->
        </script>
    </div>
    <!--修改注册邮箱-->
    <div id="newwid2" class="newwidstyle">
        <form name="regform" id="regform" method="post" action="resume_baseinfo_save.action">
        <h2>
            修改注册邮箱<span class="newwidclose close"></span></h2>
        <input type="hidden" id="uid" value="147597303" />
        <input type="hidden" id="emailOld" value="liguanglongvip@126.com" />
        <input type="hidden" id="loginNameOld" value="liguanglongvip@126.com" />
        <div class="powerinfo2">
            <table>
                <tr>
                    <th>
                        当前邮箱
                    </th>
                    <td id="oldemail">
                        liguanglongvip@126.com
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        新邮箱地址
                    </th>
                    <td valign="top">
                        <div class="emailaddrr">
                            <input type="text" class="" name="email" id="email" /><input type="hidden" name="istest"
                                id="istest" value="0"></div>
                        <span id="email_info"></span>
                    </td>
                </tr>
                <tr>
                    <th>
                    </th>
                    <td class="msgtxt_email">
                        邮箱修改后将改变您的登录用户名、找回密码和接收HR来信的邮箱地址；下次请使用新的邮箱登录智联。
                    </td>
                </tr>
                <tr>
                    <th>
                    </th>
                    <td>
                        <input type="button" class="nextstepbtn" id="nextstepbtn" /><input type="button"
                            class="cancelbtn close" />
                    </td>
                </tr>
            </table>
        </div>
        </form>
    </div>
    <script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/jquery-1.6.4.min.js"></script>
    <script type="text/javascript" src="/js/new_v4/fnUtil.js"></script>
    <script type="text/javascript" src="/js/new_v4/fnResumeChEn.js"></script>
    <script type="text/javascript" src="/js/new_v4/fnWorkYear.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/new_v4/regFormVal.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/jquery.zlzp.popupbase.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/jquery.zlzp.popupdiv.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/my/v5/resume_foot.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/basedata.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/ui/xw_selectcity/resume_selectCity.js"></script>
    <script>
        $(function () {
            $("#button_locationF").selectCity({
                title: "工作地点",
                outInput: "selItem_locationF",
                hiddenInput: "desired_City",
                oversea: arrOversea,
                limit: 5
            });
        });
    </script>
    <!--zhangchai 添加 -->
    <!--张钗添加 Google Code for RMKT 2.0 &#20195;&#30721; -->
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
        <div style="display: inline;">
            <img height="1" width="1" style="border-style: none;" alt="" src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/991304468/?value=0&amp;label=S9vACIyV0FEQlLbY2AM&amp;guid=ON&amp;script=0" />
        </div>
    </noscript>
</body>
</html>
