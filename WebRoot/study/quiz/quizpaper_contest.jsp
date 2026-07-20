<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>知识竞赛</title>
		<link href="css/css_contest.css" type="text/css" rel="stylesheet" />
		
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/quiz_1b1_10.js"></script>
		<script type="text/javascript" src="js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="js/quiz_1b1_timer.js"></script>
		<script type="text/javascript" src="js/audio5.js"></script>
		<script type="text/javascript" src="js/jquery.countdown.js"></script>
		<script type="text/javascript">
		var audio5js ;
		//停止播放
		function stop_sound(obj){
			audio5js.seek(0);
			audio5js.pause();
		}
		//暂停播放
		function pause_sound(obj){
			audio5js.playPause();
			if(!audio5js.playing){
				jQuery("#pause").attr("background","images/qq_04.png");
			}else{
				jQuery("#pause").attr("background","images/qq_05.png");
			}
			
		}
		//重新开始
		function restart_sound(obj){
			audio5js.seek(0);
			audio5js.play();
		}
		
		var audio1 = new Audio5js({
			ready:function(){
				this.load("<%=basePath%>" + "elstuffs/1273/1350.mp3");
			}
		});
		
		var audio2 = new Audio5js({
			ready:function(){
				this.load("<%=basePath%>" + "elstuffs/1273/1349.mp3");
			}
		});
		
		function loadCountdown(element_id,starttime){
			jQuery(function(){
				jQuery('#'+element_id).countdown({
				  image:'<%=basePath%>images/digits.png',
				  //获取大题中每道题的答题时间
		          startTime:starttime,
		          timerEnd:function(){ //倒计时结束时，停止播放
		          		confirmtimerstop();//判断得分
		          },
		          format:'mm:ss'
				});
			});
		}
		
//开启提示音
function openaudio(audio){
	if(audio == "audio1"){
		if(audio1==undefined && audio1==null){
			audio1 = new Audio5js({
				ready:function(){
					this.load("<%=basePath%>" + "elstuffs/1273/1350.mp3");
				}
			});
			return audio1;
		}
	}else{
		if(audio2==undefined && audio2==null){
			audio2 = new Audio5js({
				ready:function(){
					this.load("<%=basePath%>" + "elstuffs/1273/1349.mp3");
				}
			});
			return audio2;
		}
	}
	
	
}
//删除提示音
function clearaudio(){
	if(audio1!=undefined && audio1!=null){
		audio1.pause();
		audio1 = null;
	}
	if(audio2!=undefined && audio2!=null){
		audio2.pause();
		audio2 = null;
	}
}
function confirmtimerstop(){
	//q_save(blockid,bsortid,sortid,qindex)
	if(!window.confirm("确认提交这道题?")){
		return ;
	}
	var blockid=jQuery("#ca_block_"+nowbsort).attr("blockid");
	bsortid = nowbsort;
	sortid = nowsort;
	var temp_audio ; 
	var theq=jQuery("#ca_question_"+bsortid+"_"+sortid);
	if(jQuery(theq).attr("opstatus")==0)
		return ;
	var qid_ =getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_id"),"question.id") ;
	var qbid_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_epblock_id"),"question.epblock.id") ;
	var qbsort_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_epblock_sortid"),"question.epblock.sortid") ;
	var qsort_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_sortid"),"question.sortid") ;
	var qans_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_stuAnswers"),"question.stuAnswers");
	var xx=("myExamPaper.id="+sqid_+"&"+qid_+"&"+ qbid_+"&"+ qbsort_+"&"+ qsort_+"&"+ qans_+"&question.opstatus="+jQuery(theq).attr("opstatus")+"&question.qindex="+qindex+"&x="+Math.random());
	xx = encodeURI(encodeURI(xx));
	jQuery.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"checkMyQuestionIsGetScore.action",
		data:xx,
		timeout:60000,
		cache:false,
		success:function (data) {
	   		var flag = eval("("+data+")");
			if(flag){//正确
				if(audio5js!=undefined && audio5js!=null){
					audio5js.seek(0);
					audio5js.pause();
					audio5js = null;
				}
				if(audio1==undefined || audio1==null){
					audio1 = openaudio("audio1");
				}
				audio1.seek(0);
				audio1.play();
				
				var temp = null;
				temp = window.setInterval(function(){
					if(typeof (audio1.position) == "string" && ( audio1.duration == audio1.position)){
						window.clearInterval(temp);
	 					temp = null;
						qindex = 0;
						showQN();
					}
				},1000);
				
			}else{//错误
				if(audio5js!=undefined && audio5js!=null){
					audio5js.seek(0);
					audio5js.pause();
					audio5js = null;
				}
				if(audio2==undefined || audio2==null){
					audio2 = openaudio("audio2");
				}
				//audio2.seek(0);
				audio2.play();
				var temp = null;
				temp = window.setInterval(function(){
					if(typeof (audio2.position) == "string" && ( audio2.duration == audio2.position)){
						window.clearInterval(temp);
	 					temp = null;
						if(qindex == 1){
							getBlockquestions(blockid,nowsort);
						}else{
							showQN();
						}
					}
				},1000);
			}
		}
	});
	
	
	
}
	
		
		
		
		
		

var sqid_ = <s:property value="myExamPaper.id"/>;
var quizpaper ;
var qtimer ;
function myload(){
	qtimer = new QuizTimer('11',"examtime",<s:property value="examPaper.during"/>*60,<s:property value="myExamPaper.jiashi"/>*60,<s:property value="myExamPaper.passTime"/>,75,940);
	quizpaper=new QuizPaper("quizform","examtime","" ,qtimer);
	quizpaper.autosave();
	q_show(1,1);
}


</script>
		<script type="text/javascript">
function QuizPaper(theform,timearea,timeinput,timer){
	this.theform = theform;
	this.timearea = timearea;
	this.timer = timer;
	this.timeinput = timeinput;
	this.status=0;
	this.passt = 0;
}
QuizPaper.prototype.save=function(){
	
}
var status = 0;
var during_js_ = 0;
QuizPaper.prototype.getstatus=function(){
	jQuery.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"getquizstatus.action",data:{"myExamPaper.id":sqid_,
		"myExamPaper.passTime":(qtimer.passtime+qtimer.nowpassed),"x":Math.random()},success:function (data) {
	    var dataObj=eval("("+data+")");
		during_js_ = dataObj.myExamPaper.jiashi*60;//
		status=dataObj.myExamPaper.status;
	}});
	qtimer.during_js = during_js_;
}
var setIT =null;
QuizPaper.prototype.autosave=function(){
	var obj = this;
	setIT = window.setInterval(function(){ 
		var lefttime = qtimer.leftsec;
		var np = obj.passt;
		if(np>0&&np%30==0){
			obj.getstatus();
		}
		if(status==2||status==3){
			alert("试卷已被强制交卷!");
			window.clearInterval(setIT);
			setIT = null;
			obj.submit();
		}
		if(lefttime<=0){
	 		window.clearInterval(setIT);
	 		setIT = null;
			alert("时间到，强制交卷!");
		 	obj.submit();
		}
		obj.passt++;
	},1000);
}
QuizPaper.prototype.submit=function(){
	jQuery("#" + this.theform).attr("action","quizpaper_submit_contest.action");
	window.onbeforeunload=null;
	window.onunload=null;
	jQuery("#meppasstime").attr("value",(qtimer.passtime+qtimer.nowpassed));
	/**
	if(nowqtype!=8&&nowqtype!=9&&nowqtype!=10){
		var lastqids = jQuery("#lastqids").val();
		jQuery("#lastqids").val(lastqids+","+(nowbid+"-0-"+nowsort));
	}
	if(nowqtype==7){
		var childs = jQuery("#question_"+nowbid+"_"+nowsort).find(".question1");
		for(var ci = 0 ; ci<childs.length;ci++){
			if(jQuery(childs[ci]).attr("sortid")){
				var lastqids = jQuery("#lastqids").val();
				jQuery("#lastqids").val(lastqids+","+(nowbid+"-"+nowsort+"-"+jQuery(childs[ci]).attr("sortid")));
			}
		}
	}
	*/
	jQuery("#" + this.theform).submit();
	if(window.opener){
		window.opener.refresh1();
	}
} 
function catalog_switch()
{
	var oPageFile = document.getElementById('page_file');
	var oSwitchButton = document.getElementById('switch_button');
	if(oPageFile.style.display != 'none')
	{
		oPageFile.style.display='none';
		oSwitchButton.src='images/img/yincang2.jpg';
	}
	else
	{
		oPageFile.style.display='';
		oSwitchButton.src='images/img/yincang.jpg';
	}
}

function getBlockquestions(blockid,sort){
	$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"quizpaper_oneQuestion.action",data:{"question.sortid":sort,"question.epblock.id":blockid,"myExamPaper.id":sqid_},success:function (data) {
	   		$("#ques").html("").append(data);
	   		if($("#ca_question_"+nowbsort+"_"+nowsort).attr("fashengQuestion")!= ""){
	   			audio5js  = new Audio5js({//加载音频
					ready:function(){
						this.load($("#ca_question_"+nowbsort+"_"+nowsort).attr("fashengQuestion"));
						this.play();
					}
				});
	   		}
	   		if(qindex == 2){
	   			qindex = 0;
	   		}
	   		qindex++;
	   		
	   		if($("#ca_block_"+nowbsort).attr("answerTime")>=1 && $("#ca_block_"+nowbsort).attr("answerTime")<=60){//加载答题倒计时定时器
	   			var setITss =null;
		   		setITss = window.setInterval(function(){
		   			if(audio5js!=undefined && audio5js!=null){
		   				if(typeof (audio5js.position) == "string" && (audio5js.duration == audio5js.position)){
		   					window.clearInterval(setITss);
	 						setITss = null;
		   					audio5js = null;
		   					loadCountdown('counter',returnToHHSSString($("#ca_block_"+nowbsort).attr("answerTime")));
		   				}
		   			}
		   		});
	   		}

	},error:function(msg){
		//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
		//jQuery("#loading_"+blockid).css("display","none");
	}});
}
function returnToHHSSString(minute){
	if(minute>0){
		if(minute>=10){
			return minute + ":00";
		}else{
			return "0" + minute + ":00";
		}
	}else{
		return "00:00";
	}
}

var nowbid=0,nowsort=0,nowbsort=0,maxbsort=0,nowqtype=0,qindex=0;
function q_show(bsort,sort ){
	if(nowbsort==bsort&&nowsort==sort)
	{
		return ;
	}
	if(nowbid!=0&&nowsort!=0){
			//alert(nowbid);
			//alert(nowbsort);
			//alert(nowsort);
			q_save(nowbid,nowbsort,nowsort);
	}
	var blockid=$("#ca_block_"+bsort).attr("blockid");
	getBlockquestions(blockid,sort);
	nowbid = blockid;
	nowsort = sort;
	nowbsort = bsort;
	nowqtype = $("#ca_question_"+nowbsort+"_"+nowsort).attr("qtype");
	
	var theq=$("#ca_question_"+nowbsort+"_"+nowsort);
	var opstatus =$(theq).attr("opstatus");
	if(opstatus!=1){
		$("#ca_question_"+nowbsort+"_"+nowsort).attr("opstatus", 1);
	}
	
}

//前一题
function showQP(){
	if(nowbsort==1&&nowsort==1){
	 	alert("现在是第一题了");
	 	return ;
	}
	if(nowbsort==1){//第一大题
		if(nowsort>1){
			q_show(nowbsort,nowsort-1);
		}
	}else{//后面大题
		if(nowsort==1){
			var maxsort = jQuery("#ca_block_"+(nowbsort-1)).attr("questionmount");
			q_show(nowbsort-1,maxsort);
		}else
			q_show(nowbsort,nowsort-1);
	}
	if(audio5js!=undefined && audio5js!=null){
		audio5js.seek(0);
		audio5js.pause();
		audio5js = null;
	}
	clearaudio();
}

//后一题
function showQN(){
	var maxsort = 0;
	if(nowbsort==maxbsort)
	{
		maxsort = jQuery("#ca_block_"+nowbsort).attr("questionmount");
		if(maxsort==nowsort)
		{
			alert("现在是最后一题了");
			return ;
		}
	}
	if(nowbsort==maxbsort){//最后一大题
		if(nowsort<maxsort){
			q_show(nowbsort,nowsort+1);
		}
	}else{//之前的大题
		maxsort = jQuery("#ca_block_"+nowbsort).attr("questionmount")
		if(nowsort==maxsort){
			q_show(nowbsort+1,1);
		}else{
			q_show(nowbsort,nowsort+1);
		}
	}
	if(audio5js!=undefined && audio5js!=null){
		audio5js.seek(0);
		audio5js.pause();
		audio5js = null;
	}
	clearaudio();
	
}
function q_cy(){
	jQuery("#ca_question_"+nowbsort+"_"+nowsort).attr("class","ca_td_cy");
	jQuery("#ca_question_"+nowbsort+"_"+nowsort).attr("old-class","ca_td_cy");
	jQuery("#ca_question_"+nowbsort+"_"+nowsort).attr("opstatus","2");
}
function q_yd(blockid,qid){
	jQuery("#ca_question_"+nowbsort+"_"+nowsort).attr("class","ca_td_yd");
	jQuery("#ca_question_"+nowbsort+"_"+nowsort).attr("old-class","ca_td_yd");
	jQuery("#ca_question_"+nowbsort+"_"+nowsort).attr("opstatus","1");
}
function getData(elemArray,ename){
    var dataString = "";
    for (var i = 0; i < elemArray.length; i++) {
    	var element = elemArray[i];
      	var elemType = element.type.toUpperCase();
		var elemName = element.name;
		//var ename = elemName.replace("questions["+qsortid+"]","question");
		if (elemName) {
	         if (elemType == "TEXT"
	                 || elemType == "TEXTAREA"
	                 || elemType == "PASSWORD"
	                 || elemType == "HIDDEN")
	              dataString += (dataString.length > 0 ? "&" : "")+ename+"="+element.value;
	         else if (elemType == "CHECKBOX" && element.checked)
	            dataString += (dataString.length > 0 ? "&" : "")+ename+"="+(element.value ? element.value : "" );
	         else if (elemType == "RADIO" && element.checked)
	           dataString += (dataString.length > 0 ? "&" : "")+ename+"="+ element.value ;
	         else if (elemType.indexOf("SELECT") != -1)
	             for (var j = 0; j < element.options.length; j++) {
	                 var option = element.options[j];
	                 if (option.selected)
		                dataString += (dataString.length > 0 ? "&" : "")+ename+"="+
		                         (option.value ? option.value : option.text);
	             }
	     }
	 }
     return dataString;
}
var e_i=1;
function qsave_(blockid,psortid,sortid,obj){
	if(e_i==1){e_i=jQuery(obj).val().length/20+1}
	if(jQuery(obj).val().length>e_i*20){
		if(psortid==0)
			q_save(blockid,nowbsort,sortid);
		else{
			jQuery("#ca_question_"+nowbsort+"_"+psortid).attr("opstatus","1");
			q_save(blockid,nowbsort,psortid);
			q_save_(blockid,nowbsort,psortid,sortid);
		}
		e_i++;
	}
}
function q_save(blockid,bsortid,sortid,qindex){
	var theq=jQuery("#ca_question_"+bsortid+"_"+sortid);
	if(jQuery(theq).attr("opstatus")==0)
		return ;
	//alert("questions_"+blockid+"_0_"+sortid+"_id");
	var qid_ =getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_id"),"question.id") ;
	//alert(qid);
	var qbid_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_epblock_id"),"question.epblock.id") ;
	var qbsort_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_epblock_sortid"),"question.epblock.sortid") ;
	var qsort_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_sortid"),"question.sortid") ;
	var qans_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_stuAnswers"),"question.stuAnswers");
	if(jQuery(theq).attr("qtype")==6){
		while(qans_.indexOf("&")>0){//过滤特殊字符&
			qans_=qans_.replace("&","ahned");
		}
		while(qans_.indexOf("+")>0){//过滤特殊字符+
			qans_=qans_.replace("+","pjliuas");
		}
		qans_+="&question.qtype=6";
	}
	var xx=("myExamPaper.id="+sqid_+"&"+qid_+"&"+ qbid_+"&"+ qbsort_+"&"+ qsort_+"&"+ qans_+"&question.opstatus="+jQuery(theq).attr("opstatus")+"&question.qindex="+qindex+"&x="+Math.random());
	xx = encodeURI(encodeURI(xx));
	//alert(xx);
	jQuery.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"quizquestion_save.action",data:xx,timeout:8000,cache:false,success:function (data) {
	   	if(data!='success'){
	   		//alert("保存上一题失败！");
		}
	},error:function(msg){
		alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
		var xtype=jQuery(theq).attr("qtype");
		if(xtype!=8&&xtype!=9&&xtype!=10){
			var lastqids = jQuery("#lastqids").val();
			jQuery("#lastqids").val(lastqids+","+(blockid+"-0-"+sortid));
		}
		
		if(xtype==7){
			var childs = jQuery("#question_"+blockid+"_"+sortid).find(".question1");
			for(var ci = 0 ; ci<childs.length;ci++){
				if(jQuery(childs[ci]).attr("sortid")){
					var lastqids = jQuery("#lastqids").val();
					jQuery("#lastqids").val(lastqids+","+(blockid+"-"+sortid+"-"+jQuery(childs[ci]).attr("sortid")));
				}
			}
		}
	}});
}
function q_save_(blockid,bsortid,psortid,sortid){
	var opstatus = 1;
	var qid_ =getData(document.getElementsByName("questions_"+blockid+"_"+psortid+"_"+sortid+"_id"),"question.id") ;
	var qbid_ =  getData(document.getElementsByName("questions_"+blockid+"_"+psortid+"_"+sortid+"_epblock_id"),"question.epblock.id") ;
	var qbsort_ =  getData(document.getElementsByName("questions_"+blockid+"_"+psortid+"_"+sortid+"_epblock_sortid"),"question.epblock.sortid") ;
	var qsort_ =  getData(document.getElementsByName("questions_"+blockid+"_"+psortid+"_"+sortid+"_sortid"),"question.sortid") ;
	var qans_ =  getData(document.getElementsByName("questions_"+blockid+"_"+psortid+"_"+sortid+"_stuAnswers"),"question.stuAnswers");
	if(jQuery('#question_c_'+blockid+"_"+psortid+"_"+sortid).attr('qtype')==6){
		while(qans_.indexOf("&")>0){//过滤特殊字符&
			qans_=qans_.replace("&","ahned");
		}
		while(qans_.indexOf("+")>0){//过滤特殊字符+
			qans_=qans_.replace("+","pjliuas");
		}
		qans_+="&question.qtype=6";
	}
	var xx=("myExamPaper.id="+sqid_+"&"+qid_+"&"+ qbid_+"&"+ qbsort_+"&"+ qsort_+"&"+ qans_+"&question.opstatus="+opstatus+"&x="+Math.random());
	xx = encodeURI(encodeURI(xx));
	jQuery.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"quizquestion_save.action",data:xx,timeout:8000,cache:false,success:function (data) {
	   	if(data!='success'){
		}
	},error:function(msg){
		alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
		var xtype=jQuery('#question_c_'+blockid+"_"+psortid+"_"+sortid).attr("qtype");
		if(xtype!=8&&xtype!=9&&xtype!=10){
			var lastqids = jQuery("#lastqids").val();
			jQuery("#lastqids").val(lastqids+","+(blockid+"-"+psortid+"-"+sortid));
		}
	}});
}



		window.onbeforeunload =function(){
			window.event.returnValue="确定提交？";  
	    }
		window.onunload = function (){
	   		quizpaper.submit();
		}
		function confirmpapersubmit(){
			if(window.confirm("确认提交试卷")){
				quizpaper.submit();
			}
		}
</script>
		<script>

var isIE = (document.all) ? true : false;

var jQuery = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}

var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, arguments);
	}
}

var BindAsEventListener = function(object, fun) {
	return function(event) {
		return fun.call(object, (event || window.event));
	}
}

function addEventHandler(oTarget, sEventType, fnHandler) {
	if (oTarget.addEventListener) {
		oTarget.addEventListener(sEventType, fnHandler, false);
	} else if (oTarget.attachEvent) {
		oTarget.attachEvent("on" + sEventType, fnHandler);
	} else {
		oTarget["on" + sEventType] = fnHandler;
	}
};

function removeEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.removeEventListener) {
        oTarget.removeEventListener(sEventType, fnHandler, false);
    } else if (oTarget.detachEvent) {
        oTarget.detachEvent("on" + sEventType, fnHandler);
    } else { 
        oTarget["on" + sEventType] = null;
    }
};

//拖放程序
var SimpleDrag = Class.create();
SimpleDrag.prototype = {
  //拖放对象,触发对象
  initialize: function(drag) {
	this.Drag = jQuery(drag);
	this._x = this._y = 0;
	this._fM = BindAsEventListener(this, this.Move);
	this._fS = Bind(this, this.Stop);
	this.Drag.style.position = "absolute";
	addEventHandler(this.Drag, "mousedown", BindAsEventListener(this, this.Start));
  },
  //准备拖动
  Start: function(oEvent) {
	this._x = oEvent.clientX - this.Drag.offsetLeft;
	this._y = oEvent.clientY - this.Drag.offsetTop;
	addEventHandler(document, "mousemove", this._fM);
	addEventHandler(document, "mouseup", this._fS);
  },
  //拖动
  Move: function(oEvent) {
	this.Drag.style.left = oEvent.clientX - this._x + "px";
	this.Drag.style.top = oEvent.clientY - this._y + "px";
  },
  //停止拖动
  Stop: function() {
	removeEventHandler(document, "mousemove", this._fM);
	removeEventHandler(document, "mouseup", this._fS);
  }
};

</script>
		<script type="text/javascript">
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
</script>
		<style type="text/css">
.daan {
	font-weight: bold;
	color: red;
	padding-left: 20px;
}
</style>
	</head>


	<body onload="myload();">
		<form action="quizpaper_submit_contest.action" method="post"
							name="quizform" id="quizform">
							<s:hidden name="myExamPaper.id" id="myExamPaper.id"></s:hidden>
							<s:hidden name="examPaper.id"></s:hidden>
							<s:hidden name="myExamPaper.examPaper.stuview"></s:hidden>
							<s:hidden name="recordId"></s:hidden>
							<s:hidden name="myExamPaper.passTime" id="meppasstime" />
							<s:hidden name=" course.isLogout" id=" course.isLogout"></s:hidden>
							<s:hidden name="coursePage.id" id="coursePage.id"></s:hidden>
							<s:hidden name="course.id" id="course.id"></s:hidden>
							<s:hidden name="course.classid" id="course.classid"></s:hidden>
							<s:hidden name="myCPage.cpid" id="myCPage.cpid"></s:hidden>
							<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
							<s:hidden name="examPaper.id" id="examPaper.id"></s:hidden>
						
			<SCRIPT type="text/javascript">maxbsort=<s:property value="examPaper.epBlocks.size"/></SCRIPT>
			<s:iterator value="examPaper.epBlocks" status="epb_st">
				<table style="display: none;" class="ca_tb" width="300px"
					blockid="<s:property value="id"/>"
					questionmount="<s:property value="questions.size"/>"
					answerTime="<s:property value="answerTime"/>"
					id="ca_block_<s:property value="sortid"/>">
					<tbody>
						<tr>
							<td class="ca_tb_block" colspan="10">
								第
								<s:property value="sortid" />
								大题：
								<s:property value="title" />
							</td>
						</tr>
						<s:set name="row_" value="1" />
						<s:iterator begin="1" end="row">
							<tr>
								<s:iterator begin="1" end="10">
									<s:if test="#row_<=questions.size">
										<td qid="<s:property value="questions[#row_-1].id"/>"
											qtype="<s:property value="questions[#row_-1].qtype"/>"
											opstatus="<s:property value="questions[#row_-1].opstatus"/>"
											fashengQuestion="<s:property value="questions[#row_-1].compeleteURL"/>"
											class="<s:if test="questions[#row_-1].opstatus==1">ca_td_yd</s:if><s:elseif test="questions[#row_-1].opstatus==2">ca_td_cy</s:elseif><s:else>ca_td</s:else>"
											old-class="<s:if test="questions[#row_-1].opstatus==1">ca_td_yd</s:if><s:elseif test="questions[#row_-1].opstatus==2">ca_td_cy</s:elseif><s:else>ca_td</s:else>"
											id="ca_question_<s:property value="sortid"/>_<s:property value="questions[#row_-1].sortid"/>"
											onclick="q_show(<s:property value="sortid"/>,<s:property value="questions[#row_-1].sortid"/>)"
											title="<s:property value="id"/>_<s:property value="questions[#row_-1].id"/>">
											<s:property value="questions[#row_-1].sortid" />
										</td>
									</s:if>
									<s:else>
										<td class="ca_td_d">
											&nbsp;
										</td>
									</s:else>
									<s:set name="row_" value="#row_+1" />
								</s:iterator>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</s:iterator>
			<table id="ques"> </table>
		</form>
	</body>
</html>
