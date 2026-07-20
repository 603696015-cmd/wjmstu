var studied=false;
function CPstudy(cpid,passtime,during,querytime){
	this.cpid = cpid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.nowpassed=0;
	this.setIt;
	this.setprocess();
}

function CPstudy(cpid,passtime,during,querytime,classid){
	this.cpid = cpid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.nowpassed=0;
	this.setIt;
	this.setprocess();
	this.classid=classid;
}

function CPstudy(cpid,passtime,during,querytime,classid,passtime2){
	this.cpid = cpid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.nowpassed=0;
	this.setIt;
	this.setprocess();
	this.classid=classid;
	this.nowpassed2=0;
	this.setIt2;
	this.passtime2 = passtime2;
	this.isOk=false;
}

function CPstudy(cpid,passtime,during,querytime,classid,passtime2,status,getcredit){
	this.cpid = cpid;
	this.passtime = passtime;
	this.during = during ;
	this.querytime = querytime;
	this.nowpassed=0;
	this.setIt;
	this.setprocess();
	this.classid=classid;
	this.nowpassed2=0;
	this.setIt2;
	this.passtime2 = passtime2;
	this.isOk=false;
	this.status=status;
	this.getcredit=getcredit;
	//this.erpass=erpass;//考试是否通过
}
var getcreditName="";
CPstudy.prototype.setprocess=function(){
	if(this.getcredit==1){
		getcreditName="学完";
	}else if(this.getcredit==2){
		getcreditName="考过";
	}else{
		getcreditName="学完且考过";
	}
	//alert(this.getcredit);
	/*
	if(this.getcredit==3){
		if(this.during==0) {
			this.passtime=1;
			this.passtime2=1;
			this.during=1;
		}
		//判断结业方式
		var width=0;
		width =(this.passtime2/this.during)*200;
		if(this.passtime2>this.during){
			width=200;
		}
		if(this.getcredit==3){
			width=width;
			$("#processDiv").html("<div style='background:#ff0000;width:"+width+"px;height:100%'></div>");
		}else{
			$("#processDiv").html("<div style='background:#ff0000;width:"+width+"px;height:100%'></div>");
		}
		var process1 = (width/200)*100;
		var process1S = ((process1+"").lastIndexOf(".")==-1) ? (process1+""): (process1+"").substring(0,(process1+"").lastIndexOf(".")+2);
		$("#processDiv1").html("学习进度："+process1S+"%");
		
		//--//
		var width2 =(this.passtime2/this.during)*200;
		if(this.passtime2>this.during) width=200;
		$("#processDiv3").html("<div style='background:#ff0000;width:"+width2+"px;height:100%'></div>");
		var process1 = (width2/200)*100;
		var process1S = ((process1+"").lastIndexOf(".")==-1) ? (process1+""): (process1+"").substring(0,(process1+"").lastIndexOf(".")+2);
		$("#processDiv4").html(process1S+"%");
		
		//alert(width);
	}else{
	*/
		if(this.during==0) {
			this.passtime=1;
			this.during=1;
		}
		//判断结业方式
		var width=0;
		width =(this.passtime/this.during)*200;
		if(this.passtime>this.during){
			width=200;
		}
		//alert(width);
		//alert(this.status);
		if(true){//if(this.status!=2){  //现在的需求是学习进度条的走动与只与学习相关
			$("#processDiv").html("<div style='background:#ff0000;width:"+width+"px;height:100%'></div>");
			var process1 = (width/200)*100;
			var process1S = ((process1+"").lastIndexOf(".")==-1) ? (process1+""): (process1+"").substring(0,(process1+"").lastIndexOf(".")+2);
			$("#processDiv1").html("学习进度："+process1S+"%"+"(完成标准："+getcreditName+")");
			
			//--//
			//var width2 =(this.passtime/this.during)*200;
			var width2 =(this.passtime2/this.during)*200;
			//if(this.passtime>this.during) width=200;
			if(this.passtime2>this.during){
				width2=200;
			}
			$("#processDiv3").html("<div style='background:#ff0000;width:"+width2+"px;height:100%'></div>");
			var process1 = (width2/200)*100;
			var process1S = ((process1+"").lastIndexOf(".")==-1) ? (process1+""): (process1+"").substring(0,(process1+"").lastIndexOf(".")+2);
			$("#processDiv4").html(process1S+"%"+"(完成标准："+getcreditName+")");
		}
	//}
}
CPstudy.prototype.autosave = function (){ 
	$.post("cpage_study_save.action", {
		"myCPage.cpage.id":this.cpid, 
		"x":Math.random,
		"classid":this.classid
		}, 
		function (data) {
			var d = eval("("+data+")");
			//alert(d.isPassed);//考试是否通过
			erpass=d.isPassed;
			//alert(erpass);
			if(d.message=='session_err'){
				//alert("学习进度信息,停止记录！");
				studied=true;
				window.clearInterval(this.setIt);
				document.location=document.location.href.replace(/course\.isLogout\=\d+/ig,"");
			}
			if(d.message=='session_err2'){
				studied=true;
				alert("ccd");
				window.clearInterval(this.setIt);
				//exitStudy()
			}
			$("#timer").html("保存..");
		});
}
function exitStudy(){
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post", 
			url:"course_study_logout.action",data:{
		//"coursePage.id":this.cpid, 
		//"course.classid":this.classid,
		"course.isLogout":1,
		"x":Math.random
		}, 
		success:function (data) {
	}});
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
CPstudy.prototype.setTimer=function ( ){
	var m = (parseInt(this.passtime/60))%60;
	var s =this.passtime%60;
	var str= m>0?m+"分"+s:s;
	$("#timer").html("已学时间："+str+"秒"); 				
}
CPstudy.prototype.study = function (){
		//obj.passtime=obj.passtime*2;
		var obj = this;
		setIt = window.setInterval(	function( ){ 
			if(obj.passtime<=obj.during){
				obj.setTimer();
				//obj.setprocess();
				if(obj.nowpassed%60==59){
				if(obj.nowpassed%(obj.querytime*60)==obj.querytime*60-1)
					confirm("您是否在学习！");
					//alert(obj.status);
					//alert(obj.getcredit);
					if(obj.getcredit==1||obj.getcredit==3){
						obj.autosave();
					}
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
CPstudy.prototype.study2 = function (){
		var obj = this;
		setIt2 = window.setInterval( function(){ 
			//if(obj.passtime<=obj.during){
				obj.setTimer2();//显示实际学习时长
				obj.setTimer3();//显示规定学习时长
				//obj.setprocess();
				obj.setprocess();
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

CPstudy.prototype.autosave2 = function (){
	$.post("cpage_study_save2.action", {
		"myCPage.cpage.id":this.cpid, 
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

CPstudy.prototype.setTimer2=function (){
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
	$("#timer2").html("已学时长："+str+"秒");
}
CPstudy.prototype.setTimer3=function (){
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