function QuizPaper(during,passtime,theform,timearea,timeinput,tblocksize ){
	this.during = during;
	this.passtime = passtime;
	this.theform = theform;
	this.timearea = timearea;
	this.timeinput = timeinput;
	this.tblocksize = tblocksize;
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
	$("#" + this.theform).attr("action","questionnaire_quizpaper_submit.action");
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
QuizPaper.prototype.showAllBlocks=function(){
	for(var i = 0 ; i <this.tblocksize ;i++)
	{
		document.getElementById("block_"+i).style.display="block";
		document.getElementById("b_t_"+i).className ="input";
		document.getElementById("b_t2_"+i).className ="input";
	}
 	document.getElementById("b_t_a").className ="inputover";
	document.getElementById("b_t2_a").className ="inputover";
} 
QuizPaper.prototype.showBlocks=function(id){
	for(var i = 0 ; i <this.tblocksize;i++){
		document.getElementById("block_"+i).style.display="none";
	 	document.getElementById("b_t_"+i).className ="input";
	 	document.getElementById("b_t2_"+i).className ="input";
	}
	document.getElementById("b_t_a").className ="input";
	document.getElementById("b_t2_a").className ="input";
	document.getElementById("block_"+id).style.display="block";
	document.getElementById("b_t_"+id).className ="inputover";
	document.getElementById("b_t2_"+id).className ="inputover";
}
