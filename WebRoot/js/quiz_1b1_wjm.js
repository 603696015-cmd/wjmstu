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
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
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
			alert("试卷已被强制交卷Papers have been forced assignment!");
			window.clearInterval(setIT);
			setIT = null;
			obj.submit();
		}
		if(lefttime<=0){
	 		window.clearInterval(setIT);
	 		setIT = null;
			alert("时间到，强制交卷Time to mandatory assignment!");
		 	obj.submit();
		}
		obj.passt++;
	},1000);
}

QuizPaper.prototype.submit=function(){
	$("#" + this.theform).attr("action","quizpaper_submit.action");
	window.onbeforeunload=null;
	window.onunload=null;
	$("#meppasstime").attr("value",(qtimer.passtime+qtimer.nowpassed));
	if(nowqtype!=8&&nowqtype!=9&&nowqtype!=10&&nowqtype!=15&&nowqtype!=16&&nowqtype!=17&&nowqtype!=18&&nowqtype!=19&&nowqtype!=20){
		var lastqids = $("#lastqids").val();
		$("#lastqids").val(lastqids+","+(nowbid+"-0-"+nowsort));
	}
	if(nowqtype==7){
		var childs = $("#question_"+nowbid+"_"+nowsort).find(".question1");
		for(var ci = 0 ; ci<childs.length;ci++){
			if($(childs[ci]).attr("sortid")){
				var lastqids = $("#lastqids").val();
				$("#lastqids").val(lastqids+","+(nowbid+"-"+nowsort+"-"+$(childs[ci]).attr("sortid")));
			}
		}
	}
	$("#" + this.theform).submit();
	if(window.opener){
		window.opener.refresh2();
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
	$("#loading_"+blockid).css("display","block");
	$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"quizpaper_blockquestions.action",data:{"pN":parseInt(sort/10),"question.epblock.id":blockid,"myExamPaper.id":sqid_},success:function (data) {
	   		$("#block_"+blockid).append(data);
	   		$("#block_"+blockid).css("display","block");
	   		$("#loading_"+blockid).css("display","none");
			$("#question_"+blockid+"_"+sort).css("display","block");
	},error:function(msg){
		//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
		$("#loading_"+blockid).css("display","none");
	}});
}
var nowbid=0,nowsort=0,nowbsort=0,maxbsort=0,nowqtype=0;
function q_show(bsort,sort){
	if(nowbsort==bsort&&nowsort==sort)
	{
		return ;
	}
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("class",$("#ca_question_"+nowbsort+"_"+nowsort).attr("old-class"));
	if(nowqtype!=8&&nowqtype!=9&&nowqtype!=10&&nowqtype!=15&&nowqtype!=16&&nowqtype!=17&&nowqtype!=18&&nowqtype!=19&&nowqtype!=20)
	{//保存上一题(普通)
		if(nowbid!=0&&nowsort!=0)
			q_save(nowbid,nowbsort,nowsort);
	}
	if(nowqtype==7){//保存上一题材料题
		var childs = $("#question_"+nowbid+"_"+nowsort).find(".question1");
		for(var ci = 0 ; ci<childs.length;ci++){
			if($(childs[ci]).attr("sortid")){
				q_save_(nowbid,nowbsort,nowsort,$(childs[ci]).attr("sortid"));
			}
		}
	}
	var blockid=$("#ca_block_"+bsort).attr("blockid");
	if(nowbid!=0&&nowsort!=0){
		$("#block_"+nowbid).css("display","none");
		$("#question_"+nowbid+"_"+nowsort).css("display","none");
	}
	if($("#question_"+blockid+"_"+sort).length<=0){
		getBlockquestions(blockid,sort);
	}
	$("#block_"+blockid).css("display","block");
	$("#question_"+blockid+"_"+sort).css("display","block");
	nowbid = blockid;
	nowsort = sort;
	nowbsort = bsort;
	nowqtype = $("#ca_question_"+nowbsort+"_"+nowsort).attr("qtype");
	if(nowqtype==8||nowqtype==9||nowqtype==10||nowqtype==19||nowqtype==20||nowqtype==18||nowqtype==15||nowqtype==16||nowqtype==17){
		var theq=$("#ca_question_"+nowbid+"_"+nowsort);
		var opstatus =$(theq).attr("opstatus");
		if(opstatus!=1){
			if(nowqtype==8){$("#oprate_question").css("z-index",99999);}
			else{$("#oprate_question").css("z-index",1000);}
			$("#oprate_question").css("width",$(document).width());
			$("#oprate_question").css("height",$(document).height());
			$("#oprate_question").css("display","block");
			var qid = $("#ca_question_"+nowbsort+"_"+nowsort).attr("qid");
			$("#oprate_question").attr("src",
			"quizquestioninit.action?myExamPaper.id="+sqid_
			+"&question.epblock.id="+nowbid
			+"&question.epblock.sortid="+nowbsort
			+"&question.id="+qid
			+"&question.sortid="+nowsort);
			q_yd();
			$("#ca_question_"+nowbid+"_"+nowsort).attr("opstatus", 1);
		}else
		{
			alert("本题已做答，不能再答This question has been pop star, can not answer！");
		}
	}
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("class","ca_td_now");
}
//前一题
function showQP(){
	if(nowbsort==1&&nowsort==1){
	 	alert("现在是第一题了It is the first question");
	 	return ;
	}
	if(nowbsort==1){//第一大题
		if(nowsort>1){
			q_show(nowbsort,nowsort-1);
		}
	}else{//后面大题
		if(nowsort==1){
			var maxsort = $("#ca_block_"+(nowbsort-1)).attr("questionmount");
			q_show(nowbsort-1,maxsort);
		}else
			q_show(nowbsort,nowsort-1);
	}
}
//后一题
function showQN(){
	var maxsort = 0;
	if(nowbsort==maxbsort)
	{
		maxsort = $("#ca_block_"+nowbsort).attr("questionmount")
		if(maxsort==nowsort)
		{
			alert("现在是最后一题了It is the last question");
			if(window.confirm("确认提交Confirm Submit")){
				document.getElementById("answered").value = 1;
				qsubmit();
				if(window.opener){
					window.opener.refresh1();
				}
			}
			//quizpaper.submit();
			return ;
		}
	}
	if(nowbsort==maxbsort){//最后一大题
		if(nowsort<maxsort){
			q_show(nowbsort,nowsort+1);
		}
	}else{//之前的大题
		maxsort = $("#ca_block_"+nowbsort).attr("questionmount")
		if(nowsort==maxsort){
			q_show(nowbsort+1,1);
		}else{
			q_show(nowbsort,nowsort+1);
		}
	}
	
}
function q_cy(){
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("class","ca_td_cy");
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("old-class","ca_td_cy");
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("opstatus","2");
}
function q_yd(blockid,qid){
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("class","ca_td_yd");
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("old-class","ca_td_yd");
	$("#ca_question_"+nowbsort+"_"+nowsort).attr("opstatus","1");
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
	if(e_i==1){e_i=$(obj).val().length/20+1}
	if($(obj).val().length>e_i*20){
		if(psortid==0)
			q_save(blockid,nowbsort,sortid);
		else{
			$("#ca_question_"+nowbsort+"_"+psortid).attr("opstatus","1");
			q_save(blockid,nowbsort,psortid);
			q_save_(blockid,nowbsort,psortid,sortid);
		}
		e_i++;
	}
}
function q_save(blockid,bsortid,sortid){
	var theq=$("#ca_question_"+bsortid+"_"+sortid);
	if($(theq).attr("opstatus")==0)
		return ;
	
	var qid_ =getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_id"),"question.id") ;
	var qbid_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_epblock_id"),"question.epblock.id") ;
	var qbsort_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_epblock_sortid"),"question.epblock.sortid") ;
	var qsort_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_sortid"),"question.sortid") ;
	var qans_ =  getData(document.getElementsByName("questions_"+blockid+"_0_"+sortid+"_stuAnswers"),"question.stuAnswers");
	if($(theq).attr("qtype")==6){
		while(qans_.indexOf("&")>0){//过滤特殊字符&
			qans_=qans_.replace("&","ahned");
		}
		while(qans_.indexOf("+")>0){//过滤特殊字符+
			qans_=qans_.replace("+","pjliuas");
		}
		qans_+="&question.qtype=6";
	}
	var xx=("myExamPaper.id="+sqid_+"&"+qid_+"&"+ qbid_+"&"+ qbsort_+"&"+ qsort_+"&"+ qans_+"&question.opstatus="+$(theq).attr("opstatus")+"&x="+Math.random());
	xx = encodeURI(encodeURI(xx));
	$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"quizquestion_save.action",data:xx,timeout:8000,cache:false,success:function (data) {
	   	if(data!='success'){
	   		//alert("保存上一题失败！");
		}
	},error:function(msg){
		alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务When the error, this error occurs, please check the local network, or contact the administrator to check the service！");
		var xtype=$(theq).attr("qtype");
		if(xtype!=8&&xtype!=9&&xtype!=10){
			var lastqids = $("#lastqids").val();
			$("#lastqids").val(lastqids+","+(blockid+"-0-"+sortid));
		}
		if(xtype==7){
			var childs = $("#question_"+blockid+"_"+sortid).find(".question1");
			for(var ci = 0 ; ci<childs.length;ci++){
				if($(childs[ci]).attr("sortid")){
					var lastqids = $("#lastqids").val();
					$("#lastqids").val(lastqids+","+(blockid+"-"+sortid+"-"+$(childs[ci]).attr("sortid")));
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
	if($('#question_c_'+blockid+"_"+psortid+"_"+sortid).attr('qtype')==6){
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
	$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"quizquestion_save.action",data:xx,timeout:8000,cache:false,success:function (data) {
	   	if(data!='success'){
		}
	},error:function(msg){
		alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务When the error, this error occurs, please check the local network, or contact the administrator to check the service！");
		var xtype=$('#question_c_'+blockid+"_"+psortid+"_"+sortid).attr("qtype");
		if(xtype!=8&&xtype!=9&&xtype!=10){
			var lastqids = $("#lastqids").val();
			$("#lastqids").val(lastqids+","+(blockid+"-"+psortid+"-"+sortid));
		}
	}});
}
function upload_offices(blockid,qid){
	filename2 =document.getElementById("office_"+blockid+"_"+qid).value;
	if(filename2=='')
	{
		alert("请输入文件Please enter the file！");
		return false;
	} 
	var filename = document.getElementById("a_office_"+blockid+"_"+qid).value;	//获取文件名  
	if(filename.lastIndexOf(".")!=-1){										//如果文件名存在  .doc
		filename=filename.substr(0,filename.lastIndexOf(".")); 				//截取 0 到 从后面算起的第一个.的位置
	} 
	document.getElementById("a_office_"+blockid+"_"+qid).value =filename+filename2.substring(filename2.lastIndexOf('.'));//截取后的文件名+文件格式
	$.ajaxFileUpload
	    (
	      {
	      	   url:"upload_office_stuff.action", //你处理上传文件的服务端
	           secureuri:false,
	           fileElementId:"office_"+blockid+"_"+qid,
	           dataType: 'json',
	           data: {//加入的文本参数
			      "filename":sqid_+"_"+blockid+"_"+qid,
			      "path":"quizanswer" 
			   },
	           success: function (json,status )
               {
               		if(json.message =="up_file_toobig")
               		alert("文件太大，必须小于10M,File is too large, must be less than 10M!");
               		if(json.message =="up_file_succ"){
               			alert("上传成功Upload Success!");
               			//$("#uploadTxt").html("附件已上传"); 
               		}
               		if(json.message =="up_file_input")
               		alert("请输入文件Please enter the file");
               		if(json.message =="up_file_err")
               		alert("文件上传失败File upload failed");
               }
	       }
      )
      return false;
}

function upload_offices2(blockid,qid){
	filename2 =document.getElementById("office_"+blockid+"_"+qid).value;
	if(filename2=='')
	{
		alert("请输入文件Please enter the file！");
		return false;
	} 
	var filename = document.getElementById("a_office_"+blockid+"_"+qid).value;	//获取文件名  
	if(filename.lastIndexOf(".")!=-1){										//如果文件名存在  .doc
		filename=filename.substr(0,filename.lastIndexOf(".")); 				//截取 0 到 从后面算起的第一个.的位置
	} 
	document.getElementById("a_office_"+blockid+"_"+qid).value =filename+filename2.substring(filename2.lastIndexOf('.'));//截取后的文件名+文件格式
	$.ajaxFileUpload
	    (
	      {
	      	   url:"upload_office_stuff.action", //你处理上传文件的服务端
	           secureuri:false,
	           fileElementId:"office_"+blockid+"_"+qid,
	           dataType: 'json',
	           data: {//加入的文本参数
			      "filename":sqid_+"_"+blockid+"_"+qid,
			      "path":"quizanswer" 
			   },
	           success: function (json,status )
               {
               		if(json.message =="up_file_toobig")
               		alert("文件太大，必须小于"+json.offsize+"M,File is too large, must be less than "+ json.offsize +" M!");
               		if(json.message =="up_file_succ"){
               			alert("上传成功Upload Success!");
               			$("#uploadTxt_"+qid).html("附件已上传Attachments have been uploaded");
               			//alert(json.qid);
               		}
               		if(json.message =="up_file_input")
               		alert("请输入文件Please enter the file");
               		if(json.message =="up_file_err")
               		alert("文件上传失败File upload failed");
               }
	       }
      )
      return false;
} 

function intelligentProportion(){
	var qid = $("#ca_question_"+nowbsort+"_"+nowsort).attr("qid");
	$.ajax({
		async:false,    
		type:"post",
		url:"intelligent_proportion.action",
		data:{
			"myExamPaper.id":sqid_,
			"question.epblock.id":nowbid,
			"question.id":qid,
			"examPaper.id":examPaperid,
			"elclass.id":classid,
			"course.id":courseid,
			"coursePage.id":pageid,
			"examRoom.id":examRoomid,
			"x":Math.random()
		},
		success:function (data) {
		}
	});
}
function intelligentRecoding(){
	var qid = $("#ca_question_"+nowbsort+"_"+nowsort).attr("qid");
	$.ajax({
		async:false,    
		type:"post",
		url:"intelligent_recoding.action",
		data:{
			"myExamPaper.id":sqid_,
			"question.epblock.id":nowbid,
			"question.id":qid,
			"examPaper.id":examPaperid,
			"elclass.id":classid,
			"course.id":courseid,
			"coursePage.id":pageid,
			"examRoom.id":examRoomid,
			"x":Math.random()
		},
		success:function (data) {
		}
	});
}
function quizpaper_begin(){
	$.ajax({async:true,   
		type:"post",
		url:"quizpaper_begin.action",data:{"myExamPaper.id":sqid_,"elclass.id":classid,"course.id":courseid,"coursePage.id":pageid},success:function (data) {
	   		
	},error:function(msg){
	}});
}
function quizpaper_end(){
	$.ajax({async:true,   
		type:"post",
		url:"quizpaper_end.action",data:{"myExamPaper.id":sqid_,"elclass.id":classid,"course.id":courseid,"coursePage.id":pageid},success:function (data) {
	},error:function(msg){
	}});
}