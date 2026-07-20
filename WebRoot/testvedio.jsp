<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testvedio.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		function init(){
				var stuffPath="elstuffs/1213/1219.flv";
				var _cvideo = new CourseVideo(2,stuffPath,1);
				_cvideo.show("flvcontent");
			}
	</script>
  </HEAD>
  
  <body onload="init();">
    <div id="flvcontent"></div>
  </body>
  
  <script type="text/javascript">
	function CourseVideo(type,url,from){
	this.type = type;
	this.url = url;
	this.from = from;
}
CourseVideo.prototype.show=function(objid){
	url = this.url;
	type = this.type;
	var ext1 = url.substring(url.lastIndexOf(".")+1,url.length);
	ext1 = ext1.toLowerCase();
	if(ext1!="wmv"&&ext1!="swf"&&ext1!="flv"&&ext1!="rm"&&ext1!="avi"&&ext1=='asf'&&ext1!="mpg"
		&&ext1!="doc"&&ext1!="ppt"&&ext1!="xls"&&ext1!="docx"&&ext1!="pptx"&&ext1!="xlsx"&&ext1!="txt"&&ext1!="pdf"){
		$("#"+objid).html("鏃犳硶鍔犺浇瑙嗛锛伮�");
	}
	if(type==0){
		$("#"+objid).html("<img height=\"250px\" src=\"images/study_homepage.jpg\" width=\"300px\"/>");
	}
	if(type==1||type==2){
		var bol=false;
		var ext = url.substring(url.lastIndexOf(".")+1,url.length);
		if(ext=="wmv"||ext=='mp3'||ext=='asf'||ext=='avi'||ext=='mpg'){
			//$("#"+objid).html(wmvVideo(url,this.from));
			document.getElementById(objid).innerHTML=wmvVideo(url,this.from);
			bol=true;
		}
		if(ext=="swf"){
			//$("#"+objid).html(swfVideo(url,this.from));
			document.getElementById(objid).innerHTML=swfVideo(url,this.from);
			bol=true;
		}
		if(ext=="flv"||ext=='mp4'||ext=='f4v'){
			//$("#"+objid).html(flvVideo(url,this.from));
			document.getElementById(objid).innerHTML=flvVideo(url,this.from);
			bol=true;
		}
		if(ext=="rm"||ext=='rmvb'){
			//$("#"+objid).html(rmVideo(url,this.from));
			document.getElementById(objid).innerHTML=rmVideo(url,this.from);
			bol=true;
		}
		if(ext=="csf"){
			//$("#"+objid).html(rmVideo(url,this.from));
			document.getElementById(objid).innerHTML=ScenicVideo(url);
			bol=true;
		}
		if(ext=="doc"||ext=="ppt"||ext=="xls"||ext=="docx"||ext=="pptx"||ext=="xlsx"||ext=="txt"||ext=="pdf")
		{
			var x = url.substring(0,url.lastIndexOf("."));
			docVideo(objid,x+".swf");
		}
		return bol;
	}
}
function fullScreen(objid){
	$("#"+objid).css("width","100%");
	$("#"+objid).css("height","100%");
	$("#"+objid).css("height","100%");
	$("#"+objid).css("position","absolute");
	$("#"+objid).css("z-index","1000");
}
function docVideo(objid,url_){
  	$('#'+objid).FlexPaperViewer(
            { config : {
                SWFFile : url_,
                Scale : 0.6,
                ZoomTransition : 'easeOut',
                ZoomTime : 0.5,
                ZoomInterval : 0.2,
                FitPageOnLoad : true,
                FitWidthOnLoad : false,
                FullScreenAsMaxWindow : false,
                ProgressiveLoading : false,
                MinZoomSize : 0.2,
                MaxZoomSize : 5,
                SearchMatchAll : false,
                InitViewMode : 'Portrait',
                RenderingOrder : 'flash,html',
                StartAtPage : '',
                ViewModeToolsVisible : true,
                ZoomToolsVisible : true,
                NavToolsVisible : true,
                CursorToolsVisible : true,
                SearchToolsVisible : true,
                WMode : 'window',
                localeChain: 'en_US'
            }}
    );
}
//3.rm鏍煎紡
function rmVideo(url,passtime){
return "<OBJECT ID=video CLASSID=\"clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA\" HEIGHT=100% WIDTH=100%>"+ 
"<param name=\"_ExtentX\" value=\"9313\">"+
"<param name=\"_ExtentY\" value=\"7620\">"+
"<param name=\"AUTOSTART\" value=\"true\">"+
"<param name=\"SHUFFLE\" value=\"0\">"+
"<param name=CurrentPosition value='"+passtime+"'/>"+
"<param name=\"PREFETCH\" value=\"0\">"+
"<param name=\"NOLABELS\" value=\"0\">"+
"<param name=\"SRC\" value=\""+url+"\";>"+
"<param name=\"CONTROLS\" value=\"ImageWindow\">"+
"<param name=\"CONSOLE\" value=\"Clip1\">"+
"<param name=\"LOOP\" value=\"0\">"+
"<param name=\"NUMLOOP\" value=\"0\">"+
"<param name=\"CENTER\" value=\"0\">"+
"<param name=\"MAINTAINASPECT\" value=\"0\">"+
"<param name=\"BACKGROUNDCOLOR\" value=\"#000000\"><embed SRC type=\"audio/x-pn-realaudio-plugin\" CONSOLE=\"Clip1\" CONTROLS=\"ImageWindow\" HEIGHT=\"288\" WIDTH=\"352\" AUTOSTART=\"true\">"+
"</OBJECT>";
}
//4.wmv鏍煎紡聫
function wmvVideo(url,passtime){
return "<object id=\"video_wmv\" width=\"100%\" height=\"100%\" classid=\"CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95\" codebase=\"http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715\" standby=\"Loading Microsoft Windows Media Player components...\" type=\"application/x-oleobject\" hspace=\"5\">"+ 
 "<param name=\"AutoRewind\" value=1>"+
 "<param name=\"FileName\" value=\""+url+"\">"+
 "<param name=CurrentPosition value='"+passtime+"'/>"+
 "<param name=\"ShowAudioControls\" value=\"1\">"+
  "<param name=\"ShowPositionControls\" value=\"0\">"+
 "<param name=\"ShowTracker\" value=\"1\">"+
 "<param name=\"ShowStatusBar\" value=\"1\">"+
 "<param name=\"AutoStart\" value=true>"+
 "<param name=\"Volume\" value=\"-2500\">"+
 "<param name=\"AnimationAtStart\" value=\"1\">"+
 "<param name=\"TransparentAtStart\" value=\"0\">"+
 "<param name=\"AllowChangeDisplaySize\" value=\"0\">"+
 "<param name=\"AllowScan\" value=\"0\">"+
 "<param name=\"EnableContextMenu\" value=\"0\">"+
 "<param name=\"ClickToPlay\" value=\"0\">"+
 "</object>";
}

function flvVideo(url){
return "<object id=\"vcastr3\" data=\"js/vcastr3.swf\" height=\"100%\" type=\"application/x-shockwave-flash\" width=\"100%\">"+
		"	<param name=\"movie\" value=\"js/vcastr3.swf\"/>"+
		"	<param name=\"allowFullScreen\" value=\"true\" />"+
		"	<param name=\"scale\" value=\"noborder\" />"+
		"	<param name=\"FlashVars\" value=\"xml="+
		"	<vcastr>"+
		"		<plugIns>"+
		"			<javaScriptPlugIn>"+
		"				<url>js/javaScriptPlugIn.swf</url>"+
		"			</javaScriptPlugIn>"+
		"		</plugIns>"+
		"		<channel>"+
		"			<item>"+
		"				<source>http://localhost:8080/yun_gaiban/"+url+"</source>"+
		"			</item>"+
		"		</channel>"+
		"	</vcastr>\" />"+
		"</object>";
}

function ScenicVideo(url){
return "<object classid='clsid:8EF11386-FCAF-426D-88B0-62C68E9B5770' width='100%' height='100%' id='ScenicPlayer' name='ScenicPlayer' onError=if(window.confirm('璇锋偍鍏堝畨瑁匰cenicPlayer杞欢,鐒跺悗鍒锋柊鏈〉鎵嶅彲浠ユ甯告挱鏀�.')){}>"+
			"<param name='url' value='"+url+"'/>"+
			"<param name='autoplay' value='1'/>"+
			"<param name='ShowToolbar' value='1'>"+
		    "<param name='AutoScreenStretch' value='1'>"+
		"</object>";
}

function baiduVideo(url){
return "<object classid='clsid:02E2D748-67F8-48B4-8AB4-0A085374BB99' width='500' height='400' id='BaiduPlayer' name='BaiduPlayer' onError=if(window.confirm('璇锋偍鍏堝畨瑁呯櫨搴﹀奖闊宠蒋浠�,鐒跺悗鍒锋柊鏈〉鎵嶅彲浠ユ甯告挱鏀�.')){window.open('http://player.baidu.com')}else{self.location='http://player.baidu.com'}>"+
			"<param name='url' value='"+url+"'/>"+
			"<param name='autoplay' value='1'/>"+
		"</object>";
}
function getVcastr() {
         if (navigator.appName.indexOf("Microsoft") != -1) {
             return window["vcastr3"];
         } else {
             return document["vcastr3"];
         }
     }

function swfVideo(url,passtime){

	return "<object id='video' classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\""+
	" codebase=\"http://download.macromedia.com/pub/shockwave/_cabs/flash/swflash.cab#version=6,0,29,0\""+
	" style=\"width: 100%; height: 100%; text-align: center;\">"+
	"<param name=\"movie\" value=\""+url+"\">"+
	"<param name=CurrentPosition value='"+passtime+"'/>"+
	"<!----涓妚alue鍊煎～鍏lash鐨勫湴鍧�锛屼綘鐨刦lash鍦ㄦ湰鏈轰笂灏辩敤鐩稿鍦板潃!---->"+
	" <param name=\"quality\" value=\"high\">"+
	"<param name=\"SCALE\" value=\"exactfit\">"+
	"<!---- 涓媠rc鍊煎～鍏ュ拰鍒氭墠涓�鏍风殑鍦板潃!---->"+
	"<embed src=\"../images/head.swf\" width=\"100%\""+
	" height=\"100%\" quality=\"high\""+
	"茫聙聙pluginspage=\"http://www.macromedia.com/go/getflashplayer\""+
	"茫聙聙type=\"application/x-shockwave-flash\""+
	" scale=\"exactfit\"></embed> </object>";
}

function cnote_add(id,obj){
	courseid = id;
	var notelistdiv = document.getElementById("notelist");
	var noteadddiv = document.getElementById("noteadd");
	var left = (obj.offsetLeft + obj.clientWidth);
	var top = (obj.offsetTop);
	while (obj = obj.offsetParent) {
		left += obj.offsetLeft;
		top += obj.offsetTop;
	}
	notelistdiv.style.display='none';
	noteadddiv.style.display='';
	noteadddiv.style.left =left-380;
	noteadddiv.style.top =top-10;
	action("courseNote_addInit.action",null,'noteaddcontent');
}
function cnote_add_link(id,obj){
	courseid = id;
	var notelistdiv = document.getElementById("notelist");
	var noteadddiv = document.getElementById("noteadd");
	var left = (obj.offsetLeft + obj.clientWidth);
	var top = (obj.offsetTop);
	while (obj = obj.offsetParent) {
		left += obj.offsetLeft;
		top += obj.offsetTop;
	}
	notelistdiv.style.display='none';
	noteadddiv.style.display='';
	noteadddiv.style.left =left-380;
	noteadddiv.style.top =top+10;
	action("courseNote_addInit.action",null,'noteaddcontent');
}
function cnote_addcl(){
	param = "cnote.course.id="+courseid+"&cnote.content="+document.getElementById("notecontent").value;
	action("courseNote_add.action",param,'noteaddcontent');
}
function closediv(id){
	document.getElementById(id).style.display="none";
}
function cnote_list(id,obj){
	courseid = id;
	var notelistdiv = document.getElementById("notelist");
	var noteadddiv = document.getElementById("noteadd");
	var left = (obj.offsetLeft + obj.clientWidth);
	var top = (obj.offsetTop);
	while (obj = obj.offsetParent) {
		left += obj.offsetLeft;
		top += obj.offsetTop;
	}
	notelistdiv.style.display='';
	noteadddiv.style.display='none';
	notelistdiv.style.left =left-580;
	notelistdiv.style.top =top-10;
	action("courseNote_list.action","course.id="+courseid,'notelistcontent');
}
function cnote_list_link(id,obj){
	courseid = id;
	var notelistdiv = document.getElementById("notelist");
	var noteadddiv = document.getElementById("noteadd");
	var left = (obj.offsetLeft + obj.clientWidth);
	var top = (obj.offsetTop);
	while (obj = obj.offsetParent) {
		left += obj.offsetLeft;
		top += obj.offsetTop;
	}
	notelistdiv.style.display='';
	noteadddiv.style.display='none';
	notelistdiv.style.left =left-580;
	notelistdiv.style.top =top+10;
	action("courseNote_list.action","course.id="+courseid,'notelistcontent');
}
function deleteNote(id){
	if(window.confirm("纭畾鍒犻櫎锛�"))
	action("courseNote_delete.action","cnote.id="+id+"&course.id="+courseid,'notelistcontent');
}

function getCpage(id){
	$.post("cpage_view.action", {
		"coursePage.id":id, 
		"x":Math.random
		}, 
		function (data) {
			$("#cpage_content").html(data);
		});
	//action("cpage_view.action","coursePage.id="+id,'cpage_content');
}
function showContent(id){
	var obj = document.getElementById(id);
	if(obj.style.display==''||obj.style.display=='block'){
		obj.style.display='none';
	}else{
		obj.style.display="block";
	}
}
	</script>
</html>
