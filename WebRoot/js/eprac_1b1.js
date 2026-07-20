function QuizPaper(during,passtime,theform,timearea,timeinput ){
	this.during = during;
	this.passtime = passtime;
	this.theform = theform;
	this.timearea = timearea;
	this.timeinput = timeinput;
	this.nowpassed=0;
}
QuizPaper.prototype.save=function(){
	var pa1 = $("#" + this.theform).serialize();
	$.post("quizpaper_save.action", pa1, function (data) {
		$("#"+this.timearea).html("保存..");
	});
}
QuizPaper.prototype.getstatus=function(){

}
QuizPaper.prototype.autosave=function(){
	var obj = this;
	setIt = window.setInterval(	function(){ 
		var lefttime = qtimer.leftsec;//obj.during+obj.during_js - obj.passtime -obj.nowpassed ;
			if(qtimer.nowpassed%30==29){
				$("#"+this.timeinput).attr("value", qtimer.passtime+qtimer.nowpassed);
			}
		if(status==2||status==3){
			alert("试卷已被强制交卷!");
			window.clearInterval(this.setIt);
			obj.submit();
		}
		if(lefttime<=0){//&&lefttime_js<=0
	 		window.clearInterval(this.setIt);
			alert("时间到，强制交卷!");
		 	obj.submit();
		}
		
	},1000);
}/*
QuizPaper.prototype.autosave=function(){
	var obj = this;
	setIt = window.setInterval(	function(){ 
		obj.settime();
		if(obj.nowpassed%60==59){
			obj.getstatus();
		}
		//if(obj.nowpassed%180==179){
			//obj.save();
		//}
		if((obj.during - obj.passtime-obj.nowpassed)<=0){
			window.clearInterval(this.setIt);
		}
		obj.nowpassed ++;
	},1000);
}*/
QuizPaper.prototype.submit=function(){
	$("#" + this.theform).attr("action","quizpaper_submit.action");
	$("#" + this.theform).submit();
}
QuizPaper.prototype.settime=function(){
	var leftsec = this.during - this.passtime-this.nowpassed;
	var sec = leftsec%60;
	var min = parseInt(leftsec/60)%60;
	var hour = parseInt(leftsec/60/60);
	var xxx = hour;
	xxx = xxx+ ":"+( min>9?min:"0"+min );
	xxx = xxx+ ":"+( sec>9?sec:"0"+sec);
//	if(xxx.indexOf(":")==0)
	//	xxx = xxx.replace(":","");
	$("#"+this.timearea).html( "<acronym title='就剩这么多时间啦，加油哦！'>"+
	xxx+"</acronym>");
	$("#"+this.timeinput).attr("value", this.passtime+this.nowpassed);
	if(hour<=0&&min<=0&&sec<=0){
		alert("时间到，强制交卷!");
		this.submit();
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
function QUESTION(id,sortid,block,qsort,opstatus,qtype){
	this.id = id;
	this.sortid = sortid;
	this.block = block;
	this.oldclass='ca_td';
	this.qsort = qsort;
	this.qtype = qtype;
	if(now_q==null)
		now_q = this;
	this.childs = [];
}
QUESTION.prototype.addChild=function(q){
	this.childs[this.childs.length]=q;
}
var now_q=null;
QUESTION.prototype.getCa=function(){
	var qi = document.createElement("td");
	$(qi).attr("id","ca_td_"+this.block.id+"_"+this.id);
	$(qi).attr("class","ca_td");
	$(qi).css("cursor","pointer");
	var q = this;
	$(qi).click(function(){
		//alert(qi.id);
		q_show(q);
	} );
	$(qi).html(this.sortid );
	$(qi).attr("title",( this.id+"=="+this.block.id ));
	return qi;
}
function fq_show(){
	var q = now_q;
		$("#block_"+now_q.block.id).css("display","none");
		$("#block_"+q.block.id).css("display","block");
		$("#question_"+now_q.block.id+"_"+now_q.id).css("display","none");
		$("#question_"+q.block.id+"_"+q.id).css("display","block");
		var oldclass = now_q.oldclass;
		if(oldclass!='ca_td')
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class",oldclass);
		else
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td");
	
		$("#ca_td_"+q.block.id+"_"+q.id).attr("class","ca_td_now");
		if(q.qtype==8||q.qtype==9||q.qtype==10){
			if(q.qtype==8){$("#oprate_question").css("z-index",99999);}
			else{$("#oprate_question").css("z-index",1000);}
			$("#oprate_question").css("width",$(document).width()-23);
			$("#oprate_question").css("height",$(document).height());
			$("#oprate_question").css("display","block");
			$("#oprate_question").attr("src","pracquestioninit.action?myExamPaper.id="+sqid_+"&question.epblock.id="+q.block.id+"&question.id="+q.id);
			q.oldclass='ca_td_yd';
			$("#ca_td_"+q.block.id+"_"+q.id).attr("class",q.oldclass);
		}
	q_show_i = ep.getQid(q);
}
function q_show(q){
	if(now_q!=q){
		$("#block_"+now_q.block.id).css("display","none");
		$("#block_"+q.block.id).css("display","block");
		$("#question_"+now_q.block.id+"_"+now_q.id).css("display","none");
		$("#question_"+q.block.id+"_"+q.id).css("display","block");
		var oldclass = now_q.oldclass;
		if(oldclass!='ca_td')
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class",oldclass);
		else
			$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td");
	
		$("#ca_td_"+q.block.id+"_"+q.id).attr("class","ca_td_now");
		if(q.qtype==8||q.qtype==9||q.qtype==10){
			if(q.qtype==8){$("#oprate_question").css("z-index",99999);}
			else{$("#oprate_question").css("z-index",1000);}
			$("#oprate_question").css("width",$(document).width()-23);
			$("#oprate_question").css("height",$(document).height());
			$("#oprate_question").css("display","block");
			$("#oprate_question").attr("src","pracquestioninit.action?myExamPaper.id="+sqid_+"&question.epblock.id="+q.block.id+"&question.id="+q.id);
			q.oldclass='ca_td_yd';
			$("#ca_td_"+q.block.id+"_"+q.id).attr("class",q.oldclass);
		}
		now_q = q;
	}
//	q_save(ep.questions[q_show_i].qsort);
	q_show_i = ep.getQid(q);
	/*if(q_show_i==0) $("#a_img_pre").css("display","none");
	else $("#a_img_pre").css("display","block");
	if(q_show_i==ep.questions.length) $("#a_img_next").css("display","none");
	else $("#a_img_pre").css("display","block");*/
}
function q_cy( ){
	now_q.oldclass='ca_td_cy';
	$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td_cy");
}
function q_yd(blockid,qid){
	var theq =now_q;// ep.getQByid(qid,blockid);
	theq.oldclass='ca_td_yd';
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
function q_save(qsortid){
	var qid_ =getData(document.getElementsByName("questions["+qsortid+"].id"),qsortid) ;
	var qbid_ =  getData(document.getElementsByName("questions["+qsortid+"].epblock.id"),qsortid) ;
	var qbsort_ =  getData(document.getElementsByName("questions["+qsortid+"].epblock.sortid"),qsortid) ;
	var qsort_ =  getData(document.getElementsByName("questions["+qsortid+"].sortid"),qsortid) ;
	var qans_ =  getData(document.getElementsByName("questions["+qsortid+"].stuAnswers"),qsortid);
	var xx=("myExamPaper.id="+sqid_+"&"+qid_+"&"+ qbid_+"&"+ qbsort_+"&"+ qsort_+"&"+ qans_+"&x="+Math.random());
	/*$.ajax(
		{	 
			type:"post",   
		    url:"quizquestion_save.action",   
		    data:xx,   
			success:function(data){alert( data );}});
	*/
	 $.post("quizquestion_save.action", xx , function (data) {
		//alert(data);
	 }); 
}
function upload_offices(blockid,qid){
	filename2 =document.getElementById("office_"+blockid+"_"+qid).value;
	if(filename2=='')
	{
		alert("请输入文件！");
		return false;
	} 
	//下面代码filename2.substring(filename2.lastIndexOf('.'), 照成每上传一次加一次结尾格式(列：.doc),上传几次便+几个.doc
	//Hwc
	var filename = document.getElementById("a_office_"+blockid+"_"+qid).value;	//获取文件名  
		if(filename.lastIndexOf(".")!=-1){										//如果文件名存在  .doc
			filename=filename.substr(0,filename.lastIndexOf(".")); 				//截取 0 到 从后面算起的第一个.的位置
		} 
	//document.getElementById("a_office_"+blockid+"_"+qid).value =document.getElementById("a_office_"+blockid+"_"+qid).value+filename2.substring(filename2.lastIndexOf('.'));
	document.getElementById("a_office_"+blockid+"_"+qid).value =filename+filename2.substring(filename2.lastIndexOf('.'));//截取后的文件名+文件格式
	$.ajaxFileUpload
	    (
	      {
	      	   url:"upload_office_stuff.action", //你处理上传文件的服务端
	           secureuri:false,
	           fileElementId:"office_"+blockid+"_"+qid,
	           dataType: 'json',
	           data: {//加入的文本参数
			      	"filename":sqid_+"_"+blockid+"_"+qid ,
			      	"path":"epracanswer" 
			   },
	           success: function (json,status )
               {
               		alert("上传成功！");//+json.message 
               }
	       }
      )
      //alert("上传成功！"+json.message );
      return false;
} 
