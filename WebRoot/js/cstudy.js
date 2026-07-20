var studied=false;
function Cstudy(cid,passtime,during,querytime){ 
	this.cid = cid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.nowpassed=0;
	this.setIt;
	///this.setprocess();
}

function Cstudy(cid,passtime,during,querytime,classid){ 
	this.cid = cid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.nowpassed=0;
	this.setIt;
	this.classid=classid;
	///this.setprocess();
}

function Cstudy(cid,passtime,during,querytime,classid,passtime2){ 
	this.cid = cid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.nowpassed=0;
	this.nowpassed2=0;
	this.setIt;
	this.setIt2;
	this.classid=classid;
	this.passtime2 = passtime2;
	///this.setprocess();
	this.isOk=false;
}
Cstudy.prototype.setprocess=function(){
	if(this.during==0) {
		this.passtime=1;
		this.during=1;
	}
	var width =(this.passtime/this.during)*200;
	if(this.passtime>this.during) width=200;
	$("#processDiv").html("<div style='background:#f00;width:"+width+"px;height:100%'></div>");
	var process1 = (width/200)*100;
	var process1S = ((process1+"").lastIndexOf(".")==-1) ? (process1+""): (process1+"").substring(0,(process1+"").lastIndexOf(".")+2);
	$("#processDiv1").html("学习进度："+process1S+"%");
	
	//--// 
	var width2 =(this.passtime/this.during)*200;
	if(this.passtime>this.during) width=200;
	$("#processDiv3").html("<div style='background:#f00;width:"+width2+"px;height:100%'></div>");
	var process1 = (width2/200)*100;
	var process1S = ((process1+"").lastIndexOf(".")==-1) ? (process1+""): (process1+"").substring(0,(process1+"").lastIndexOf(".")+2);
	$("#processDiv4").html(process1S+"%");
}
Cstudy.prototype.autosave = function (){
	$.post("course_study_save.action", {
		"myCourse.course.id":this.cid, 
		"x":Math.random,
		"classid":this.classid
		}, 
		function (data) {
			var d = eval("("+data+")");
			if(d.message=='session_err'){
				//alert("学习进度信息,停止记录！");
				studied=true;
				window.clearInterval(this.setIt);
				document.location=document.location.href.replace(/course\.isLogout\=\d+/ig,"");
			}
			$("#timer").html("保存..");
		});
}
function exitStudy(){
	$.post("course_study_logout.action", {
		//"coursePage.id":this.cpid, 
		//"course.classid":this.classid,
		"course.isLogout":1,
		"x":Math.random
		}, 
		function (data) {
	});
}
function saveStudyRecordEndtime(courseid,classid,cpid){
	//更新学员学习记录退出时间
	$.post("updateStudyCourseRecordEndtime.action", {
	"course.id":courseid,
	"course.classid":classid,
	"coursePage.id":cpid,
	"x":Math.random
	},
	function (data) {
		//alert('更新成功');
	});
}
Cstudy.prototype.setTimer=function ( ){
	var m = (parseInt(this.passtime/60))%60;
	var s =this.passtime%60;
	var str= m>0?m+"分"+s:s;
	$("#timer").html("已学时间："+str+"秒"); 				
}
/*
Cstudy.prototype.study = function (){
		var obj = this;
		setIt = window.setInterval(	function( ){ 
			if(obj.passtime<=obj.during){
				obj.setTimer();
				obj.setprocess();
				if(obj.nowpassed%60==59){
					if(obj.nowpassed%(obj.querytime*60)==obj.querytime*60-1)
						confirm("您是否在学习！");
						obj.autosave();
						obj.autosave2();
					
				}
				obj.nowpassed ++;
				obj.passtime ++;
			}else{
				alert("nihao");
				window.clearInterval(this.setIt);
			}
		} ,
		1000 );
}*/

Cstudy.prototype.study = function (){
		var obj = this;
		setIt = window.setInterval(	function( ){ 
			if(obj.passtime<=obj.during){
				obj.setTimer();
				obj.setprocess();
				if(obj.nowpassed%60==59){
					if(obj.nowpassed%(obj.querytime*60)==obj.querytime*60-1)
						confirm("您是否在学习！");
						obj.autosave();
				}
				obj.nowpassed ++;
				obj.passtime ++;
			}else{
				window.clearInterval(this.setIt);
				obj.isOk=true;
			}
		} ,
		1000 );
}
//保存用户学习的实际学习时长
Cstudy.prototype.study2 = function (){
		var obj = this;
		setIt2 = window.setInterval( function(){ 
			//if(obj.passtime<=obj.during){
				obj.setTimer2();//显示实际学习时长
				obj.setTimer3();//显示规定学习时长
				//obj.setprocess();
				if(obj.nowpassed2%60==59){
					if(obj.isOk==true){
						if(obj.nowpassed2%(obj.querytime*60)==obj.querytime*60-1){
							confirm("您是否在学习!!");
						}
					}
					//alert("ccddee");
					obj.autosave2();
				}
				obj.nowpassed2 ++;
				obj.passtime2 ++;
			//}else{
			//	alert("nihao");
			//	window.clearInterval(this.setIt2);
			//}
		} ,
		1000 );
}

Cstudy.prototype.autosave2 = function (){
	$.post("course_study_save2.action", {
		"myCourse.course.id":this.cid, 
		"x":Math.random,
		"classid":this.classid
		}, 
		function (data) {
			var d = eval("("+data+")");
			if(d.message=='session_err'){
				alert("学习进度信息,停止记录！");
				studied=true;
				window.clearInterval(this.setIt2);
				document.location=document.location.href.replace(/course\.isLogout\=\d+/ig,"");
			}
			$("#timer2").html("保存..");
		}
		);
}

Cstudy.prototype.setTimer2=function (){
	var h = parseInt(this.passtime2/(60*60));
	var m = (parseInt(this.passtime2/60))%60;
	var s =this.passtime2%60;
	var str="";
	//alert(h);
	if(h==0){
		str= m>0?m+"分"+s:s;
	}else{
		str= h>0?(h+"时" + (m>=0?m+"分"+s:s)):(m>0?m+"分"+s:s);
		//str = h+"时"+ (m>=0?m+"分"+s:s);
	}
	$("#timer2").html("实际学习："+str+"秒");
}
Cstudy.prototype.setTimer3=function (){
	var h = parseInt(this.during/(60*60));
	var m = (parseInt(this.during/60))%60;
	var s =this.during%60;
	var str="";
	//alert(h);
	if(h==0){
		str= m>0?m+"分"+s:s;
	}else{
		str= h>0?(h+"时" + (m>=0?m+"分"+s:s)):(m>0?m+"分"+s:s);
		//str = h+"时"+ (m>=0?m+"分"+s:s);
	}
	$("#timer3").html("规定时长："+str+"秒");
}