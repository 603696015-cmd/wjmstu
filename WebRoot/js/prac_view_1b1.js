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
		$("#"+this.timearea).html("保存Save..");
	});
}
QuizPaper.prototype.getstatus=function(){

}
QuizPaper.prototype.autosave=function(){
	var obj = this;
	setIt = window.setInterval(	function(){ 
		obj.settime();
		if(obj.nowpassed%60==59){
			obj.getstatus();
		}
		if(obj.nowpassed%180==179){
			obj.save();
		}
		if((obj.during - obj.passtime-obj.nowpassed)<=0){
			window.clearInterval(this.setIt);
		}
		obj.nowpassed ++;
	},1000);
}
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
	$("#"+this.timearea).html( "<acronym title='就剩这么多时间啦，加油哦We only have so much time to you, Come！'>"+
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
function EXAMPAPER(id,score,myscore){
	this.id = id;
	this.blocks  = [];
	this.questions = [];
	this.q_yds=[];
	this.q_cys=[];
	this.score=score;
	this.myscore = myscore;
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
	var epscore_d = document.createElement("div");
	$(epscore_d).html("总分："+this.score+"&nbsp;&nbsp;得分："+this.myscore);
	$(epscore_d).css("font-weight","bolder");
	$(epscore_d).css("border-bottom","1 solid buttonface");
	$("#page_file").append(epscore_d);
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
		 /**
		 alert("现在是第一题了,It is the first question");
		 if($("#oprate_question").css("display") == "block"){
		 	closeFrame();
		 }
		 */
		 quizpaperConfirm("现在是第一题了,It is the first question");
	}
}
EXAMPAPER.prototype.showQN = function(){
	if(q_show_i<this.questions.length-1){
		q_show(this.questions[q_show_i+1]);
	//	q_show_i = q_show_i+1
	}
	else
	{
		 /**
		 alert("现在是最后一题了,It is the last question");
		 if($("#oprate_question").css("display") == "block"){
		 	closeFrame();
		 }
		 */
		 quizpaperConfirm("现在是最后一题了,It is the last question");
	}
}
function BLOCK(id,sortid,title,score,myscore){
	this.id = id;
	this.questions = [];
	this.sortid = sortid;
	this.title = title;
	this.score =score; 
	this.myscore =myscore;
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
	var _row = parseInt(qus.length/6);
	if(_row == 0) _row =1 ;
	var b_tr = document.createElement("tr");
	var b_td = document.createElement("td");
		$(b_td).attr("class","ca_tb_block");
		$(b_td).attr("colspan",10);
		$(b_td).css("width","100%");
		$(b_td).html("第"+this.sortid+"大题："+this.title+"总分："+this.score+"我的得分："+this.myscore);
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
function QUESTION(id,sortid,block,oldclass){
	this.id = id;
	this.sortid = sortid;
	this.block = block;
	this.oldclass=oldclass;//'ca_td';
	if(now_q==null)
		now_q = this;
}
var now_q=null;
QUESTION.prototype.getCa=function(){
	var qi = document.createElement("td");
	$(qi).attr("id","ca_td_"+this.block.id+"_"+this.id);
	$(qi).attr("class",this.oldclass);
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
		now_q = q;
	}
	q_show_i = ep.getQid(q);
	
	var qtype = $("#qtype_"+now_q.block.id+"_"+now_q.id).val();
	if(qtype == 15 || qtype == 16 || qtype == 17  || qtype == 18 ||qtype == 19 ||qtype == 20){
		$("#oprate_question").css("z-index",1000);
		$("#oprate_question").css("width",$(document).width());
		$("#oprate_question").css("height",$(document).height());
		$("#oprate_question").css("display","block");
		$("#oprate_question").attr("src","quizquestioninit.action?myExamPaper.id="
		+sqid_+"&question.epblock.id="+now_q.block.id
		+"&question.id="+now_q.id+"&question.sortid="+now_q.sortid+"&view=1&elmessage="+mess);
	}
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
	var theq = ep.getQByid(qid,blockid);
	theq.oldclass='ca_td_yd';
	$("#ca_td_"+theq.block.id+"_"+theq.id).attr("class",theq.oldclass);
}
function upload_offices(blockid,qid){
	filename2 =document.getElementById("office_"+blockid+"_"+qid).value;
	alert(filename2);
	document.getElementById("a_office_"+blockid+"_"+qid).value =document.getElementById("a_office_"+blockid+"_"+qid).value+filename2.substring(filename2.lastIndexOf('.'));
	
	$.ajaxFileUpload
	    (
	      {
	      	   url:'upload_office_stuff.action', //你处理上传文件的服务端
	           secureuri:false,
	           fileElementId:"office_"+blockid+"_"+qid,
	           dataType: 'json',
	           data: {//加入的文本参数
			    "filename":"_"+blockid+"_"+qid 
			   },
	           success: function (data, status)
	              {
	                 	alert(data.message );
	                 }
	              }
	        )
	  alert("上传成功Upload Success");
      return false;
}
function updateDaziColor(daziContent,mydaziContent){
	alert(daziContent);
	alert(mydaziContent);
}