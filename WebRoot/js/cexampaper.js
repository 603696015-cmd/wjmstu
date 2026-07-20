//=============ajax封装========================================
var request = false;
var title = "对话框";
function createRequest() {
	if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		if (window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		}
	}
	if (!request) {
		alert("Error initializing XMLHttpRequest!");
	}
}
function action(url, param) {
	createRequest();
	request.open("POST", url, true);
	request.onreadystatechange = action_cl;
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
	request.send(param);
}
function action_cl() {
	if (request.readyState == 4) {
		dialogShow(title,request.responseText);
	}
}
//=============ajax封装结束========================================
var highlightcolor = "#c1ebff";
//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var clickcolor = "#51b2f6";
function changeto() {
	source = event.srcElement;
	if (source.tagName == "TR" || source.tagName == "TABLE") {
		return;
	}
	while (source.tagName != "TD") {
		source = source.parentElement;
	}
	source = source.parentElement;
	cs = source.children;
//alert(cs.length);
	if (cs[1].style.backgroundColor != highlightcolor && source.id != "nc" && cs[1].style.backgroundColor != clickcolor) {
		for (i = 0; i < cs.length; i++) {
			cs[i].style.backgroundColor = highlightcolor;
		}
	}
}
function changeback() {
	if (event.fromElement.contains(event.toElement) || source.contains(event.toElement) || source.id == "nc") {
		return;
	}
	if (event.toElement != source && cs[1].style.backgroundColor != clickcolor) {
//source.style.backgroundColor=originalcolor
	}
	for (i = 0; i < cs.length; i++) {
		cs[i].style.backgroundColor = "";
	}
}
function clickto() {
	source = event.srcElement;
	if (source.tagName == "TR" || source.tagName == "TABLE") {
		return;
	}
	while (source.tagName != "TD") {
		source = source.parentElement;
	}
	source = source.parentElement;
	cs = source.children;
//alert(cs.length);
	if (cs[1].style.backgroundColor != clickcolor && source.id != "nc") {
		for (i = 0; i < cs.length; i++) {
			cs[i].style.backgroundColor = clickcolor;
		}
	} else {
		for (i = 0; i < cs.length; i++) {
			cs[i].style.backgroundColor = "";
		}
	}
}
function showpracticeAdd(){
	var obj=document.getElementsByName("pracPaper.examPaper.id");
	var epid=0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked==true)
		{
			epid=obj[i].value;
			break;
		}
	}
	if(epid==0){
		alert("请选择试卷！");
		return ;
	}
	var param="pracPaper.examPaper.id="+epid;
	title = "练习添加";
	action("practicepaper_add_view.action",param);
}

function practiceAdd(){
	var obj=document.getElementsByName("ppsa");
	var ppsa=0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked==true)
		{
			ppsa=obj[i].value;
			break;
		}
	}
	document.getElementById("ppsa").value=ppsa;
	document.getElementById("practicepaper_add").submit();
}
function showsimpaperAdd(){
	var obj=document.getElementsByName("simPaper.examPaper.id");
	var epid=0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked==true)
		{
			epid=obj[i].value;
			break;
		}
	}
	if(epid==0){
		alert("请选择试卷！");
		return ;
	}
	var param="simPaper.examPaper.id="+epid;
	title = "模拟考试添加";
	action("simexampaper_add_view.action",param);
}
function simpaperAdd(){
	if(document.getElementById("begintime").value=='') 
	{
		alert("请选择开始时间！");
		return ;
	}
	if(document.getElementById("endtime").value=='') 
	{
		alert("请选择结束时间！");
		return ;
	}
	document.getElementById("sbegintime").value=document.getElementById("begintime").value;
	document.getElementById("sendtime").value=document.getElementById("endtime").value;
	document.getElementById("simexampaper_add").submit();
}
