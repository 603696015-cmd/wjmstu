function MsgBox(obj,msg){
	this.obj = obj;
	this.msg = msg;
	$(obj).attr("disabled","disabled");
	$(obj).unbind("click");
	$(obj).bind("click",function(){return false;});
	$(obj).attr("href",'#');
	this.creMsgBox();
}
MsgBox.prototype.creMsgBox = function(){
	var msgbox = $("<div>");
	$(msgbox).css("border","solid 1px blue");
	$(msgbox).css("font-size","20px");
	$(msgbox).css("font-weight","bolder");
	$(msgbox).css("padding","10px");
	$(msgbox).css("color","red");
	$(msgbox).css("position","absolute");
	$(msgbox).css("background","#fffffe");
	$(msgbox).css("top",$(this.obj).offset().top-50);
	$(msgbox).css("left",$(this.obj).offset().left-$(msgbox).width());
	$(msgbox).html(this.msg);
	$("body").append(msgbox);
}