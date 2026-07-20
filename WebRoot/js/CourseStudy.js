function CourseStudy(classid,courseid,cpid,passtime,during,querytime,passtime2,recordId){ 
	this.classid=classid;
	this.courseid = courseid;
	this.cpid = cpid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.passtime2 = passtime2;
	this.nowpassed=0;
	this.setIt;
	this.scprocess = 0;
	this.passed2 = 0;
	this.realtimediv;//显示实际学习时长
	this.durtimediv;////显示规定学习时长
	this.processdiv;
	this.savepass =1;
	this.studyinfo_time =0;
	this.recordId=recordId;
}
CourseStudy.prototype.init=function(){
	if($("#"+this.durtimediv).length>0)
		$("#"+this.durtimediv).html("规定时长："+this.toTime(this.during));
	this.study();
}
CourseStudy.prototype.intelligent_learn_begin=function(){
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
		url:"intelligent_learn_begin.action",data: {
		"myCPage.cpid":this.cpid, 
		"myCPage.courseid":this.courseid, 
		"myCPage.classid":this.classid,
		"myCPage.passtime":0,
		"studyCourseRecordId":this.recordId,
		"x":Math.random
		}, success:
		function (data) {
		}});
}
CourseStudy.prototype.intelligent_learn_end=function(){
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
		url:"intelligent_learn_end.action",data: {
		"myCPage.cpid":this.cpid, 
		"myCPage.courseid":this.courseid, 
		"myCPage.classid":this.classid,
		"myCPage.passtime":0,
		"studyCourseRecordId":this.recordId,
		"x":Math.random
		}, success:
		function (data) {
		}});
}
CourseStudy.prototype.toTime= function(x){
	x = parseInt(x);
	if(x<60)
		return x+"秒";
	if(x<3600)
		return parseInt( x/60)+"分"+x%60+"秒"
	return parseInt( x/3600)+"小时"+parseInt( (x%3600)/60)+"分"+x%60+"秒"; 
}
CourseStudy.prototype.setprocess=function(){
	if(this.during!=0) {
		var width =(this.passtime/this.during)*200;
		if(this.passtime>this.during) width=200;
		if($("#"+this.processdiv).length>0)
			$("#"+this.processdiv).html("<div style='background:#f00;width:"+width+"px;height:100%'></div>");
		if($("#"+this.realtimediv).length>0)
			$("#"+this.realtimediv).html("已学时长："+this.toTime(this.nowpassed+this.passtime2));
	}
}
CourseStudy.prototype.autosave = function(){
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
		url:"course_studysave.action",data: {
		"myCPage.cpid":this.cpid, 
		"myCPage.courseid":this.courseid, 
		"myCPage.classid":this.classid,
		"myCPage.passtime":this.savepass,
		"myCPage.studyinfo_time":this.studyinfo_time,
		"studyCourseRecordId":this.recordId,
		"x":Math.random
		}, success:
		function (data) {
			var d = eval("("+data+")");
			if(d.msg=='succ'){
				$("#timer").html("保存成功..");
				//alert("学习进度信息,停止记录！");
				//studied=true;
				//window.clearInterval(this.setIt);
				//document.location=document.location.href.replace(/course\.isLogout\=\d+/ig,"");
			}else if(d.msg =="error"){
				alert("学习进度信息,停止记录！将关闭页面！");
				window.onbeforeunload=null;
				window.onunload=null;
				window.clearInterval(this.setIt);
				window.close();
			}
		}});
	this.savepass=1;
}
CourseStudy.prototype.exitStudy=function(){
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
		url:"course_studyexit.action", data:{
		"myCPage.cpid":this.cpid, 
		"myCPage.courseid":this.courseid, 
		"myCPage.classid":this.classid,
		"myCPage.passtime":this.savepass,
		"myCPage.studyinfo_time":this.studyinfo_time,
		"studyCourseRecordId":this.recordId,
		"x":Math.random
		}, 
		success:function (data) {
			  
		}});
	this.savepass=1;
}
function addSavetimeToUrl(obj){
	$(obj).attr("href",$(obj).attr("href")+"&myCPage.passtime="+_cpst.savepass);
	return true;
}
CourseStudy.prototype.study = function (){
		var obj = this;
		this.setIt = window.setInterval(function(){
				var canstudy = true;
				if($("#video_wmv").length>0){//wmv播放器是否存在
					if(video_wmv.playState!=2){//是否是播放状态
						canstudy = false;
					}
				}
				if(canstudy){//是不是需要记录
					obj.setprocess();
					if(obj.nowpassed%60==59){
						if(obj.passtime<obj.during&&obj.nowpassed%(obj.querytime*60)==obj.querytime*60-1)
							confirm("您是否在学习！");
						obj.autosave();
						if(obj.scprocess<=100&&needsetCp){
							obj.getcsinfo();
						}
					}
					obj.nowpassed ++;
					obj.passtime ++;
					obj.savepass ++;
				}
		} ,
		1000 );
}
CourseStudy.prototype.getcsinfo=function(){
	var obj = this;
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"course_studyinfo.action", data:{
		"myCPage.courseid":this.courseid, 
		"myCPage.classid":this.classid,
		"x":Math.random
		}, 
		success:function (data) {
		  if(data!='error'){
			  	var d = eval("("+data+")");
				$("#cp_img").width(d.process);
				$("#cp_img_span").html(d.process+"%");
				//if(d.process>=100)
				//	window.clearInterval(this.setIt_scp);
				obj.scprocess = d.process;
		  }
	}});
}
/*
CourseStudy.prototype.autoSetCprocess = function (){
		var obj = this;
		this.setIt_scp = window.setInterval(function(){ 
			if(obj.nowpassed%60==0){
				obj.getcsinfo();
			}
		} ,1000 );
}
*/