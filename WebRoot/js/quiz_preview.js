function QuizPaper(theform,timearea,timeinput,timer){
	//this.during = during;
	//this.passtime = passtime;
	this.theform = theform;
	this.timearea = timearea;
	this.timer = timer;
	//this.timearea_js = timearea_js;
	//during_js_ = this.during_js;
	//this.passtime_js = passtime_js;
	this.timeinput = timeinput;
	//this.nowpassed=0;
	//this.nowpassed_js=0;
	this.status=0;
}
QuizPaper.prototype.save=function(){
	//var pa1 = $("#" + this.theform).serialize();
	//$.post("quizpaper_save.action", pa1, function (data) {
	//	$("#"+this.timearea).html("保存..");
	//});
}
var status = 0;
var during_js_ = 0;
QuizPaper.prototype.getstatus=function(){
	/*$.post("getquizstatus.action",{"myExamPaper.id":sqid_,"myExamPaper.passTime":this.timer.passtime+this.timer.nowpassed}, function (data) {
	    var dataObj=eval("("+data+")");
		during_js_ = dataObj.myExamPaper.jiashi*60;//
		status=dataObj.myExamPaper.status;
	});
	this.timer.during_js = during_js_;*/
}
QuizPaper.prototype.autoquestionsave=function(){
	var obj = this;
	setIt = window.setInterval(	function(){ 
		var lefttime = obj.timer.leftsec;//obj.during+obj.during_js - obj.passtime -obj.nowpassed ;
		if(obj.timer.nowpassed%30==29){
				obj.getstatus();
		}
		if(status==2||status==3){
			alert("试卷已被强制交卷!");
			window.clearInterval(this.setIt);
		}
		if(lefttime<=0){//&&lefttime_js<=0
	 		window.clearInterval(this.setIt);
			alert("时间到，强制交卷!");
		 	quizquestionsubmit();
		}
		
	},1000);
}
QuizPaper.prototype.autosave=function(){
	var obj = this;
	setIt = window.setInterval(	function(){ 
		var lefttime = obj.timer.leftsec;//obj.during+obj.during_js - obj.passtime -obj.nowpassed ;
		/*if(lefttime<=0){
			if(obj.nowpassed_js%30==29){
				obj.getstatus();
			}
			if(obj.nowpassed_js%180==179){
				obj.save();
			}
			obj.nowpassed_js ++;
		}else{*/
			//alert(obj.timer.nowpassed);
			if(obj.timer.nowpassed%30==29){
				obj.getstatus();
			}
			//if(obj.timer.nowpassed%180==179){
			//	obj.save();
			//}
			//obj.nowpassed ++;
		//}
		//obj.settime();
		//obj.settime_js();
		if(status==2||status==3){
			alert("试卷已被强制交卷!");
			window.clearInterval(this.setIt);
		}
		//var lefttime_js =  obj.during_js - obj.passtime_js-obj.nowpassed_js;
		if(lefttime<=0){//&&lefttime_js<=0
	 		window.clearInterval(this.setIt);
			alert("时间到，强制交卷!");
		 	obj.submit();
		}
		
	},1000);
}
QuizPaper.prototype.submit=function(){/*
	$("#" + this.theform).attr("action","quizpaper_submit.action");
	window.onbeforeunload=null;
	window.onunload=null;
	q_save(now_q.qsort,now_q.opstatus);
	var qchilds = now_q.childs;
	//alert(qchilds.length);
	for(var i=0 ;i<qchilds.length;i++){
		q_save(qchilds[i].qsort,qchilds[i].opstatus);
	}
	$("#" + this.theform).submit();*/
}/*
QuizPaper.prototype.settime=function(){
	var leftsec = this.during+this.during_js - this.passtime-this.nowpassed;
	var sec = leftsec%60;
	var min = parseInt(leftsec/60)%60;
	var hour = parseInt(leftsec/60/60);
	var xxx = hour;
	xxx = xxx+ ":"+( min>9?min:"0"+min );
	xxx = xxx+ ":"+( sec>9?sec:"0"+sec);
	$("#"+this.timearea).html( "<acronym title='就剩这么多时间啦，加油哦！'>"+
	xxx+"</acronym>");
	$("#"+this.timeinput).attr("value", this.passtime+this.nowpassed);
}
QuizPaper.prototype.settime_js=function(){
	var leftsec = during_js_ - this.passtime_js-this.nowpassed_js;
	var sec = leftsec%60;
	var min = parseInt(leftsec/60)%60;
	var hour = parseInt(leftsec/60/60);
	var xxx = hour;
	xxx = xxx+ ":"+( min>9?min:"0"+min );
	xxx = xxx+ ":"+( sec>9?sec:"0"+sec);
	$("#"+this.timearea_js).html( xxx);
	if(status ==3||status ==2){
		alert("试卷已被强制交卷!");
		this.submit();
	}
} */     
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
function EXAMPAPER(id){
	this.id = id;
	this.blocks  = [];
	this.questions = [];
	this.q_yds=[];
	this.q_cys=[];
}
EXAMPAPER.prototype.addBlock=function(blocki){
	for(var i = 0 ;i<this.blocks.length;i++){
		if(this.blocks[i].id==blocki.id){
			return ;
		}
	}
	this.blocks[this.blocks.length] = blocki;
}
EXAMPAPER.prototype.showCa= function(){
	for(var i = 0 ;i <this.blocks.length;i++){
		$("#page_file").append (this.blocks[i].getCa());
	}
}
EXAMPAPER.prototype.sortqs= function(){
	var q_count = 0;
	for(var i = 0 ;i <this.blocks.length;i++){
		var blocksi =this.blocks[i];
		var bqs = blocksi.questions;
		for(var j = 0;j<bqs.length;j++){
			this.questions[q_count]= bqs[j];
			this.questions[q_count].block= blocksi;
			q_count++;
		}
	}
}
EXAMPAPER.prototype.getQid= function(q ){
	for(var i = 0 ;i <this.questions.length;i++){
		var qi = this.questions[i]
		if(q.id==qi.id&&q.block ==qi.block)
			return i;
	}
}
EXAMPAPER.prototype.getQByid= function(id,blockid ){
	for(var i = 0 ;i <this.questions.length;i++){
		var qi = this.questions[i];
		if(id==qi.id&&blockid==qi.block.id)
			return qi;
	}
}
EXAMPAPER.prototype.showQP = function(){
	if(q_show_i>0){
		q_show(this.questions[q_show_i-1]);
		//q_show_i = q_show_i-1
	}
	else
	{
		 alert("现在是第一题了");
	}
}
EXAMPAPER.prototype.showQN = function(){
	if(q_show_i<this.questions.length-1){
		q_show(this.questions[q_show_i+1]);
		//	q_show_i = q_show_i+1
	}
	else
	{
		 alert("现在是最后一题了");
	}
}
function BLOCK(id,sortid,title){
	this.id = id;
	this.questions = [];
	this.sortid = sortid;
	this.title = title;
}
BLOCK.prototype.addQuestion=function(qi){
	for(var i = 0 ;i<this.questions.length;i++){
		if(this.questions[i].id==qi.id){
			return ;
		}
	}
	this.questions[this.questions.length] = qi;
}
BLOCK.prototype.getCa=function( ){
	var qus = this.questions;
	var bi_tb = document.createElement("tbody");
	var _row = qus.length/10;
	_row = _row>parseInt(_row)?parseInt(_row)+1:parseInt(_row);
	if(_row == 0) _row =1 ;
	var b_tr = document.createElement("tr");
	var b_td = document.createElement("td");
		$(b_td).attr("class","ca_tb_block");
		$(b_td).attr("colspan",10);
		$(b_td).css("width","100%");
		$(b_td).html("第"+this.sortid+"大题："+this.title);
		$(b_tr).append(b_td);
		$(bi_tb).append(b_tr);
	for(var i = 0 ;i<_row;i++){
		var bi_tr = document.createElement("tr");
			for(var j =0;j<10;j++){
				if(qus[i*10+j]!=undefined){
						$(bi_tr).append (qus[i*10+j].getCa());
					}
				else{	
						var tdi= document.createElement("td")
						$(tdi).html("&nbsp;");//i*5+j+1;
						$(tdi).attr("class","ca_td");
						$(bi_tr).append (tdi);
					}
			}
		$(bi_tb).append(bi_tr);
	}
	var bi_tbl = document.createElement("table");
	bi_tbl.width="300px";
	bi_tbl.className="ca_tb";
	$(bi_tbl).append(bi_tb);
	return bi_tbl;
}
function QUESTION(id,sortid,block,qsort,opstatus){
	this.id = id;
	this.sortid = sortid;
	this.block = block;
	//this.oldclass='ca_td';
	this.qsort = qsort;
	if(!opstatus)
		opstatus = 0;
	this.opstatus = opstatus;
	if(now_q==null)
		now_q = this;
	this.childs = [];
}
var now_q=null;
QUESTION.prototype.addChild=function(q){
	this.childs[this.childs.length]=q;
}
QUESTION.prototype.getCa=function(){
	var qi = document.createElement("td");
	$(qi).attr("id","ca_td_"+this.block.id+"_"+this.id);
	$(qi).css("cursor","pointer");
	//$(qi).attr("opstatus",this.opstatus)
	if(this.opstatus==0)
		$(qi).attr("class","ca_td");
	if(this.opstatus==1)
		$(qi).attr("class","ca_td_yd");
	if(this.opstatus==2)
		$(qi).attr("class","ca_td_cy");
	
	var q = this;
	$(qi).click(function(){
		//alert(qi.id);
		q_show(q);
	} );
	$(qi).html(this.sortid );
	$(qi).attr("title",( this.id+"=="+this.block.id+"="+this.opstatus ));
	return qi;
}
function q_show(q){
	/*if($("#ca_td_"+q.block.id+"_"+q.id).attr("class")=="ca_td_yd"&&now_q!=q){
		document.getElementById("quesJs").value="1";
	}else if(now_q!=q){
		document.getElementById("quesJs").value="0";
	} */
	if(now_q!=q){
		q_save(now_q.qsort,now_q.opstatus);
		var qchilds = now_q.childs;
		//alert(qchilds.length);
		for(var i=0 ;i<qchilds.length;i++){
			q_save(qchilds[i].qsort,qchilds[i].opstatus);
		}
		$("#block_"+now_q.block.id).css("display","none");
		$("#block_"+q.block.id).css("display","block");
		$("#question_"+now_q.block.id+"_"+now_q.id).css("display","none");
		$("#question_"+q.block.id+"_"+q.id).css("display","block");
		var opst = now_q.opstatus;
		if(opst==0)
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td");
		if(opst==1)
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td_yd");
		if(opst==2)
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td_cy");
		/*if(oldclass!='ca_td')
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class",oldclass);
		else
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td");
		
		*/
		$("#ca_td_"+q.block.id+"_"+q.id).attr("class","ca_td_now");
		now_q = q;
	}
	q_show_i = ep.getQid(q);
	/*if(q_show_i==0) $("#a_img_pre").css("display","none");
	else $("#a_img_pre").css("display","block");
	if(q_show_i==ep.questions.length) $("#a_img_next").css("display","none");
	else $("#a_img_pre").css("display","block");*/
}
function q_cy( ){
	//now_q.oldclass='ca_td_cy';
	now_q.opstatus = 2;
	q_save(now_q.qsort,2);
	$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td_cy");
}
function q_yd(blockid,qid){
	var theq = now_q;//ep.getQByid(qid,blockid);
	theq.opstatus = 1;
	//q_save(theq.qsort,1);
	theq.oldclass='ca_td_yd';
	//if($("#ca_td_"+theq.block.id+"_"+theq.id).attr("class")!="ca_td_yd"){
	//	document.getElementById("quesJs").value="1";
	//}
	$("#ca_td_"+theq.block.id+"_"+theq.id).attr("class",theq.oldclass);
}
function getData(elemArray,qsortid){
    var dataString = "";
    for (var i = 0; i < elemArray.length; i++) {
    	var element = elemArray[i];
      	var elemType = element.type.toUpperCase();
		var elemName = element.name;
		var ename = elemName.replace("questions["+qsortid+"]","question");
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
function q_save(qsortid,opstatus){/*
	var qid_ =getData(document.getElementsByName("questions["+qsortid+"].id"),qsortid) ;
	var qbid_ =  getData(document.getElementsByName("questions["+qsortid+"].epblock.id"),qsortid) ;
	var qbsort_ =  getData(document.getElementsByName("questions["+qsortid+"].epblock.sortid"),qsortid) ;
	var qsort_ =  getData(document.getElementsByName("questions["+qsortid+"].sortid"),qsortid) ;
	var qans_ =  getData(document.getElementsByName("questions["+qsortid+"].stuAnswers"),qsortid);
	var xx=("myExamPaper.id="+sqid_+"&"+qid_+"&"+ qbid_+"&"+ qbsort_+"&"+ qsort_+"&"+ qans_+"&question.opstatus="+opstatus+"&x="+Math.random());
	//alert("#ca_td_"+now_q.block.id+"_"+now_q.id+"="+$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("opstatus"));
	 $.post("quizquestion_save.action", xx , function (data) {
		//alert(data);
	 }); */
}
function upload_offices(blockid,qid){
	filename2 =document.getElementById("office_"+blockid+"_"+qid).value;
	if(filename2=='')
	{
		alert("请输入文件！");
		return false;
	} 
		var filename = document.getElementById("a_office_"+blockid+"_"+qid).value;	//获取文件名  
		if(filename.lastIndexOf(".")!=-1){										//如果文件名存在  .doc
			filename=filename.substr(0,filename.lastIndexOf(".")); 				//截取 0 到 从后面算起的第一个.的位置
		} 
	//document.getElementById("a_office_"+blockid+"_"+qid).value =document.getElementById("a_office_"+blockid+"_"+qid).value+filename2.substring(filename2.lastIndexOf('.'));
	document.getElementById("a_office_"+blockid+"_"+qid).value =filename+filename2.substring(filename2.lastIndexOf('.'));//截取后的文件名+文件格式
//	document.getElementById("a_office_"+blockid+"_"+qid).value =document.getElementById("a_office_"+blockid+"_"+qid).value+filename2.substring(filename2.lastIndexOf('.'));
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
               		alert("文件太大，必须小于10M!");
               		if(json.message =="up_file_succ"){
               			alert("上传成功!");
               			//$("#uploadTxt").html("附件已上传"); 
               		}
               		if(json.message =="up_file_input")
               		alert("请输入文件");
               		if(json.message =="up_file_err")
               		alert("文件上传失败");
               }
	       }
      )
      return false;
}

function upload_offices2(blockid,qid){
	filename2 =document.getElementById("office_"+blockid+"_"+qid).value;
	if(filename2=='')
	{
		alert("请输入文件！");
		return false;
	} 
		var filename = document.getElementById("a_office_"+blockid+"_"+qid).value;	//获取文件名  
		if(filename.lastIndexOf(".")!=-1){										//如果文件名存在  .doc
			filename=filename.substr(0,filename.lastIndexOf(".")); 				//截取 0 到 从后面算起的第一个.的位置
		} 
	//document.getElementById("a_office_"+blockid+"_"+qid).value =document.getElementById("a_office_"+blockid+"_"+qid).value+filename2.substring(filename2.lastIndexOf('.'));
	document.getElementById("a_office_"+blockid+"_"+qid).value =filename+filename2.substring(filename2.lastIndexOf('.'));//截取后的文件名+文件格式
//	document.getElementById("a_office_"+blockid+"_"+qid).value =document.getElementById("a_office_"+blockid+"_"+qid).value+filename2.substring(filename2.lastIndexOf('.'));
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
               		alert("文件太大，必须小于10M!");
               		if(json.message =="up_file_succ"){
               			alert("上传成功!");
               			$("#uploadTxt_"+qid).html("附件已上传");
               			//alert(json.qid);
               		}
               		if(json.message =="up_file_input")
               		alert("请输入文件");
               		if(json.message =="up_file_err")
               		alert("文件上传失败");
               }
	       }
      )
      return false;
} 
