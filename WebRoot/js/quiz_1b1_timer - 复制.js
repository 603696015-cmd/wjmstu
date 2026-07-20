function QuizTimer(id,timerarea,during,during_js,passtime,top,left,right){
	 this.id = id;
	 this.timerarea = timerarea;
	 this.move = 0;
	 this.timerd = null;
	 this.during = during;
	 this.passtime = passtime;
	 this.during_js = during_js;
	 this.nowpassed=0;
	 this.oldtime = new Date().getTime();
	 var timer =$("<div>");
	 $(timer).css("position","absolute");
	 $(timer).css("top",top);
	 $(timer).css("right",right);
	 //$(timer).css("left",left);
	 $(timer).css("width",135);
	 $(timer).css("height",40);
	 $(timer).css("z-index",99995);
	 $(timer).css("background","#fefeee");
	 $(timer).css("border-width","1px");
	 $(timer).css("border-style","solid");
	 $(timer).css("border-color","green");
	 /**
	 var timer_h = $("<div>");
	 $(timer_h).css("cursor","move");
	 $(timer_h).css("height",20);
	 $(timer_h).css("background","#eeaaff");
	 var obj = this;
	 $(timer_h).bind("mousedown",function(e){
		 $(document).bind("mousedown",function(e){
		 	obj.move = 1;
		 }).bind("mouseup",function(e){
		 	obj.move = 0;
		 	$(this).unbind("mousemove");
			$(this).unbind("mousedown");
			$(this).unbind("mouseup");
		 }).bind("mousemove",function(e){
	 		if(obj.move==1){
		 		obj.timerd.css("left",e.pageX -65 );
				obj.timerd.css("top",e.pageY-10);
			}else
				return false;	
		 });
	 });
	 */
	 //$(timer).append(timer_h);
	 //$(timer).append("<h4 style='margin:3px 0px 3px 0px;color:blue;text-align:center;font-family:\"Times New Roman\",Georgia,Serif;'>剩余时间</h4>");
	 //$(timer).append("<img src='images/shalou.gif' />");
	 $(timer).append("<h2 style='margin:7px 0px 3px 0px;color:red;text-align:center;font-family:\"Times New Roman\",Georgia,Serif;' id='"+timerarea+"'>loading</h2>");
	 this.timerd = timer;
	 $("body").append(timer);
	 this.leftsec = this.during+ this.during_js - this.passtime-this.nowpassed;
	 this.start();
} 
var setIt = null;
QuizTimer.prototype.start=function(){
	var that = this;
	setIt = window.setInterval(function(){
		that.nowpassed = parseInt((new Date().getTime()-that.oldtime)/1000);
		that.leftsec = that.during + that.during_js - that.passtime-that.nowpassed;
		var sec = that.leftsec%60;
		var min = parseInt(that.leftsec/60)%60;
		var hour = parseInt(that.leftsec/60/60);
		var xxx = hour;
		xxx = xxx+ ":"+( min>9?min:"0"+min );
		xxx = xxx+ ":"+( sec>9?sec:"0"+sec);
		$("#"+that.timerarea).html( ""+xxx+"");
		if(that.leftsec<=0){
			window.clearInterval(setIt);
			setIt =  null;
		}
	},1000);
	
	//$("#"+this.timeinput).attr("value", this.passtime+this.nowpassed);
}