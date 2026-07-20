var xmlHttpdec;
var golbaldivname;
//定义XMLHttpRequest对象实例
var http_request = false;


function qryNewByDBID (goclid,dbid,dbval){//alert(goclid);//goclid='CL0016_'+goclid,
  if(document.getElementById(dbid)) {
   document.getElementById(dbid).value  = dbval;
   goSearch('gernalsearch','../'+goclid+'/search.html?sort=true&sortId=CTIME&columnid=CPUB|'+dbid+'&relation=MUST|MUST',document.getElementById('classStr').value,'Region',2,2,'CPUB|'+dbid,'CL0016_'+goclid+'|' + document.getElementById(dbid).value,document.getElementById('curpage').value)
  }
}
//-----------------政务公开----------------------

function qryNewByMultCLID (multclid,clname){
  
  //表示已经选择了栏目
  //alert(multclid);
  if (multclid != "-1" || multclid != -1) {
    document.getElementById('CLID').value = multclid;
    //如果已经选择了栏目，则清空输入诓里面的内容
    document.getElementById('options_value10').value = "";
    document.getElementById('CTITLE').value = "";
    document.getElementById('STARTTIME').value = "";
    document.getElementById('ENDTIME').value = "";
  }
  if (clname != "-1" || clname != -1) {
    document.getElementById('CLNAME').value = clname;
  }
  if (multclid == "-1" || multclid == -1) {
    //如果栏目号传过来的是－1，则表示是按钮提交的，要获取隐藏表单的数据
    multclid = document.getElementById('CLID').value;
    //alert("2==="+document.getElementById('CLID').value);
  }
  if (clname == "-1" || clname == -1) {
    //如果栏目名称传过来的是－1，则表示是按钮提交的，要获取隐藏表单的数据
    clname = document.getElementById('CLNAME').value;
  }	
  //alert(multclid + "====" +clname);
  //先把div的栏目名称换掉
  //alert(document.getElementById('CLNAME'));
  if (document.getElementById('CLNAME')) {
    document.getElementById('CLNAME').innerHTML = clname;
  }
  //alert(qryNewByMultCLID);
  var qryidstr="CLID|OPTIONS_VALUE10|CTITLE|CTIME2";
  var relation='MUST|MUST|MUST|MUST';
  var qryvalue='';
 // if(document.getElementById('CLID')) {
   document.getElementById('CLID').value  = multclid;
   qryvalue += multclid;//栏目号
   
  //}
  qryvalue += "|"+document.getElementById('options_value10').value;//索引号
  
  qryvalue += "|"+document.getElementById('CTITLE').value;//标题
  
  var starttime;
  var endtime;
  //alert(document.getElementById('STARTTIME').value);
  if(document.getElementById('STARTTIME').value == null || document.getElementById('STARTTIME').value  == ""){
    starttime = 'null';
  }
  else {
    starttime = document.getElementById('STARTTIME').value;
  }
  if(document.getElementById('ENDTIME').value == null || document.getElementById('ENDTIME').value  == ""){
    endtime = 'null';
  }
  else {
    endtime = document.getElementById('ENDTIME').value;
  }
  //alert("ctime=="+document.getElementById('CTIME2'));
  document.getElementById('CTIME2').value = starttime + "," + endtime;
  qryvalue += "|"+starttime + "," + endtime;//开始结束时间
//alert(qryvalue);

  goSearch2('gernalsearch','../CL0001/search.html?sort=true&sortId=CTIME&columnid='+qryidstr+'&relation='+relation,document.getElementById('classStr').value,'Region',4,4,qryidstr,qryvalue,document.getElementById('curpage').value)

}


function goSearch2 (divname,mylocation,classStr,tableName,colNum,qryNum,qryidstr,qryvalue,curpage) {
//alert("");
qryvalue=  replaceHtmlCode(qryvalue); 
  //if (document.getElementById(searchvalid)) {
    //查询值
    //mylocation += "&qryValue=" + document.getElementById(searchvalid).value
  //}
  //alert(mylocation);
  //mylocation = mylocation + "&classStr=" + classStr;
  mylocation = mylocation + "&tableName=" + tableName;//alert(encodeURI(tableName));
  mylocation = mylocation + "&colNum=" + colNum;
  mylocation = mylocation + "&qryNum=" + qryNum;
  //当前页
  mylocation = mylocation + "&curPage=" + curpage;
  mylocation = mylocation + "&qryidstr=" + qryidstr
  mylocation = mylocation + "&qryValue=" + escape(qryvalue);

if (document.getElementById("record")) {
  mylocation += "&record=" + document.getElementById("record").value;}
if (document.getElementById("mytarget")) {
  mylocation += "&mytarget=" + document.getElementById("mytarget").value;}
if (document.getElementById("dateFormat")) {
  mylocation += "&dateFormat=" + document.getElementById("dateFormat").value;}
if (document.getElementById("titleLength")) {
  mylocation += "&titleLength=" + document.getElementById("titleLength").value;}
if (document.getElementById("subTitleFlag")) {
  mylocation += "&subTitleFlag=" + document.getElementById("subTitleFlag").value;}
if (document.getElementById("classStr")) {
mylocation += "&classStr=" + document.getElementById("classStr").value;}
mylocation += "&gelqryType=1";
//alert(mylocation);
mylocation = unescape(mylocation);

decAJAX("gernalsearch",mylocation);
  //decAJAX(divname,mylocation);
}
//---------------政务公开---------------


function qryNewByDBIDCLID (goclid,dbid,dbval){
  if(document.getElementById(dbid)) {
   document.getElementById(dbid).value  = dbval;
   goSearch('gernalsearch','../'+goclid+'/search.html?sort=true&sortId=CTIME&columnid=CLID|'+dbid+'&relation=MUST|MUST',document.getElementById('classStr').value,'Region',2,2,'CLID|'+dbid,document.getElementById('CLID').value+'|' + document.getElementById(dbid).value,document.getElementById('curpage').value)
  }
}
function test() {
  if (http_request.readyState == 4) { // 判断对象状态
  	//alert(http_request.status);
    if (http_request.status == 200) { // 信息已经成功返回，开始处理信息
      //returnstr = http_request.responseText;
      //alert(http_request.responseText);
      var obj = document.getElementById(golbaldivname);
      if (obj) {
        obj.innerHTML = http_request.responseText;
      }
    } 
    else { //页面不正常
      alert("您所请求的页面有异常。");
    }
  }
}
function setindexname(searchfields,relation){alert("dd");
  form123.searchField.value=searchfields;
  form123.relation.value=relation;
}
function setTextInput(temp) {//alert("temp==============="+temp)
  var index = temp.indexOf("qryidstr");
  if (index != -1) {
    var qryidStr = temp.substring(index+9); 
    index = qryidStr.indexOf("&");
    qryidStr = qryidStr.substring(0,index);
    //alert("id = " + qryidStr.indexOf("CPUB"));
    var qryidAry = qryidStr.split("|");
  
    index = temp.indexOf("qryValue");
    if (index != -1) {
      var qryvalStr = temp.substring(index+9); 
      index = qryvalStr.indexOf("&");
      qryvalStr = qryvalStr.substring(0,index);
      //alert("iddd = " + qryvalStr);
      var qryvalAry = qryvalStr.split("|");
      //alert("qryvalAry="+qryvalAry.length);
      for (var i=0;i<qryidAry.length;i++) {
        var idstr = qryidAry[i];
        //alert("idstr="+idstr);
        //alert(document.getElementById("keywordwbpp").value);
        if (document.getElementById(idstr)) {
          //if(document.getElementById(idstr).type == "text"){
            document.getElementById(idstr).value = qryvalAry[i];
          //}
        }

         if (qryidStr.indexOf("CPUB") == -1 && document.getElementById("form123") &&  document.getElementById("form123").keywordwbpp) {
           //document.getElementsById("keywordwbpp").value = "dasdfasdf";//
           form123.keywordwbpp.value=qryvalAry[i];

          }
      }
    }
  }
}
function setindexname(temp2){//alert(temp2);
	 if (temp2 != null && temp2 != '' && temp2 != '-1') {
	   document.getElementById('radioval').value = temp2;
            //alert( document.getElementById('eventSubmit_doSearch').name);
            //document.getElementById('eventSubmit_doSearch').name='CONTENT';
           //alert( document.getElementById('eventSubmit_doSearch').name);
	  // return true;
	 }
	else {
   
   var temp = document.getElementById('radioval').value;
   var temp1 = temp.split(',');
	 searchfields = temp1[0];
   relation = temp1[1];//alert("=="+temp);alert("=="+temp1);
		var fields = searchfields.split('|');
		var value = form123.keywordwbpp.value;
    var wid = temp1[2];//alert("wid="+wid);
    var cid = temp1[3];//alert("cid="+cid);
		form123.searchField.value=searchfields;
		form123.relation.value=relation;
		
		if(fields.length==2){
		  form123.searchWord.value= wid + '|' + value;
		  //alert(document.getElementById('searchWord').value);
		  goSearch('gernalsearch','../'+cid+'/search.html?sort=true&sortId=CTIME&columnid='+document.getElementById('searchField').value+'&relation='+relation,document.getElementById('classStr').value,'Region',2,2,form123.searchField.value,document.getElementById('searchWord').value,document.getElementById('curpage').value)
		  return true;
		}
		else if(fields.length==3){
		  form123.searchWord.value= wid + "|" + value + '|' + value;
		  //alert(document.getElementById('searchWord').value);
		  goSearch('gernalsearch','../'+cid+'/search.html?sort=true&sortId=CTIME&columnid='+document.getElementById('searchField').value+'&relation='+relation,document.getElementById('classStr').value,'Region',3,3,form123.searchField.value,document.getElementById('searchWord').value,document.getElementById('curpage').value)
		  return true;
		}
		else{
		  form123.searchWord.value= value;	
		}
            //alert(fields);
            //alert(value);
            //alert(form123.searchWord.value);
            goSearch('gernalsearch','../'+cid+'/search.html?sort=true&sortId=CTIME&columnid='+document.getElementById('searchField').value+'&relation='+relation,document.getElementById('classStr').value,'Region',1,1,form123.searchField.value,document.getElementById('searchWord').value,document.getElementById('curpage').value)
}}
String.prototype.replaceAll = function (AFindText,ARepText){

                raRegExp = new RegExp(AFindText,"g");
                return this.replace(raRegExp,ARepText);
}

function goSearch (divname,mylocation,classStr,tableName,colNum,qryNum,qryidstr,qryvalue,curpage) {
//alert(qryvalue);
 // alert(qryvalue);
qryvalue=  replaceHtmlCode(qryvalue); 

  //先进行过滤操作
  //alert("");
  //if (document.getElementById(searchvalid)) {
    //查询值
    //mylocation += "&qryValue=" + document.getElementById(searchvalid).value
  //}
  //alert(mylocation);
  //mylocation = mylocation + "&classStr=" + classStr;
  mylocation = mylocation + "&tableName=" + tableName;//alert(encodeURI(tableName));
  mylocation = mylocation + "&colNum=" + colNum;
  mylocation = mylocation + "&qryNum=" + qryNum;
  //当前页
  mylocation = mylocation + "&curPage=" + curpage;
  mylocation = mylocation + "&qryidstr=" + qryidstr
  mylocation = mylocation + "&qryValue=" + escape(qryvalue);
  //alert(mylocation);
  //alert("222" + escape(mylocation));

   this.location = mylocation;
  //window.open(mylocation);
  //mylocation = escape(mylocation);
  //decAJAX(divname,mylocation);
}


function goSearch1 (divname,mylocation,classStr,tableName,colNum,qryNum,qryidstr,qryvalue,curpage,sortid,sort) {
//alert(qryvalue);

qryvalue=  replaceHtmlCode(qryvalue); 

  //先进行过滤操作
  //alert("");
  //if (document.getElementById(searchvalid)) {
    //查询值
    //mylocation += "&qryValue=" + document.getElementById(searchvalid).value
  //}
  //alert(mylocation);
  //mylocation = mylocation + "&classStr=" + classStr;
  mylocation = mylocation + "&tableName=" + tableName;//alert(encodeURI(tableName));
  mylocation = mylocation + "&colNum=" + colNum;
  mylocation = mylocation + "&qryNum=" + qryNum;
  //当前页
  mylocation = mylocation + "&curPage=" + curpage;
  mylocation = mylocation + "&qryidstr=" + qryidstr
  mylocation = mylocation + "&qryValue=" + escape(qryvalue);
  //alert(mylocation);
  //alert("222" + escape(mylocation));
  mylocation = mylocation + "&sortId=" + sortid;
  mylocation = mylocation + "&sort=" + sort
   this.location = mylocation;
  //window.open(mylocation);
  //mylocation = escape(mylocation);
  //decAJAX(divname,mylocation);
}

function goSecondSearch (divname,mylocation,classStr,tableName,colNum,qryNum,qryidstr,qryvalue,curpage) {
//alert(qryvalue);
 // alert(qryvalue);
qryvalue=  replaceHtmlCode(qryvalue); 

  //先进行过滤操作
  //alert("");
  //if (document.getElementById(searchvalid)) {
    //查询值
    //mylocation += "&qryValue=" + document.getElementById(searchvalid).value
  //}
  //alert(mylocation);
  //mylocation = mylocation + "&classStr=" + classStr;
  mylocation = mylocation + "&tableName=" + tableName;//alert(encodeURI(tableName));
  mylocation = mylocation + "&colNum=" + colNum;
  mylocation = mylocation + "&qryNum=" + qryNum;
  //当前页
  mylocation = mylocation + "&curPage=" + curpage;
  mylocation = mylocation + "&qryidstr=" + qryidstr
  mylocation = mylocation + "&secondQuery=" + escape(qryvalue);
   mylocation = mylocation + "&qryValue=" + escape(qryvalue);
  //alert(mylocation);
  //alert("222" + escape(mylocation));

   this.location = mylocation;
  //window.open(mylocation);
  //mylocation = escape(mylocation);
  //decAJAX(divname,mylocation);
}


function goSearch3 (divname,mylocation,classStr,tableName,colNum,qryNum,qryidstr,qryvalue,curpage,sortid,sort) {
//alert(qryvalue);
 // alert(qryvalue);
qryvalue=  replaceHtmlCode(qryvalue); 

  //先进行过滤操作
  //alert("");
  //if (document.getElementById(searchvalid)) {
    //查询值
    //mylocation += "&qryValue=" + document.getElementById(searchvalid).value
  //}
  //alert(mylocation);
  //mylocation = mylocation + "&classStr=" + classStr;
  mylocation = mylocation + "&tableName=" + tableName;//alert(encodeURI(tableName));
  mylocation = mylocation + "&colNum=" + colNum;
  mylocation = mylocation + "&qryNum=" + qryNum;
  //当前页
  mylocation = mylocation + "&curPage=" + curpage;
  mylocation = mylocation + "&qryidstr=" + qryidstr
  mylocation = mylocation + "&secondQuery=" + escape(qryvalue);
  mylocation = mylocation + "&qryValue=" + escape(qryvalue);
  //alert(mylocation);
  //alert("222" + escape(mylocation));
  mylocation = mylocation + "&sortId=" + sortid;
  mylocation = mylocation + "&sort=" + sort
   this.location = mylocation;
  //window.open(mylocation);
  //mylocation = escape(mylocation);
  //decAJAX(divname,mylocation);
}

//过滤函数
function replaceHtmlCode (constr) {
  //过滤数组
  var key1 = new Array("onmouseover", "onmouseout", "onmousedown", 
  "onmouseup", "onmousemove", "onclick", "ondblclick", 
  "onkeypress", "onkeydown", "onkeyup", "ondragstart", 
  "onerrorupdate", "onhelp", "onreadystatechange", "onrowenter", 
  "onrowexit", "onselectstart", "onload", "onunload", 
  "onbeforeunload", "onblur", "onerror", "onfocus", "onresize", 
  "onscroll", "oncontextmenu" );
  //\b退格   \n     新的一行   \t     tab     \r     回车     \f     换页
  var key2 = new Array("\n","\b","\t","\f","\r","&lt;","&gt");
   
//过滤<括号内容
constr = constr.replace(/<.*?>/g,"");
constr = constr.replace(/&lt;.*?&gt/g,"");

//过滤script内容
constr = constr.replace(/<script[^>]+>.+?<\/script>/,"");

  if (constr != null) {
      //替换关键字
      for (i=0;i<key1.length;i++) {
        //替换
        constr =constr .replaceAll(key1 [i], "");
      }
      //替换特殊字符
      for (i=0;i<key2.length;i++) {
        //替换
        constr =constr .replaceAll(key2 [i], "");
      }

  }
//alert(constr );
  return constr;
}

function decAJAX(divname,mylocation){ 
    //alert(mylocation);
  //统一在url里面做过滤操作
  //mylocation=  replaceHtmlCode(mylocation); 
  golbaldivname = divname;
  //alert("1="+mylocation);
  //mylocation =  unescape(mylocation);//alert("2="+mylocation);
  var openurl = mylocation;
  var idx = mylocation.indexOf("search.html");
  var temp = mylocation.substring(idx+11);//alert(idx);
  openurl = mylocation.substring(0,idx);
  //alert("openurl = " + openurl);
  //window.open("search.html");
  //alert("temp=" + temp);
  setTextInput(temp);
  var URL="/wbpp/generalsearch" + temp;
 // alert(URL)
  send_request("get",URL,divname);
  
}


//定义可复用的http请求发送函数
function send_request(method,URL,divname) {//初始化、指定处理函数、发送请求的函数
  http_request = null;
  //alert("1111");
  //开始初始化XMLHttpRequest对象
  if(window.XMLHttpRequest) { //Mozilla 浏览器
    http_request = new XMLHttpRequest();
    if (http_request.overrideMimeType) {//设置MiME类别
      http_request.overrideMimeType("text/xml");
    }
  }
  else if (window.ActiveXObject) { // IE浏览器
    try {
      http_request = new ActiveXObject("Msxml2.XMLHTTP");
    } 
    catch (e) {
      try {
        http_request = new ActiveXObject("Microsoft.XMLHTTP");
      } 
      catch (e) {}
    }
  }

  if (!http_request) { // 异常，创建对象实例失败
    window.alert("不能创建XMLHttpRequest对象实例.");
    return false;
  }
  var obj = document.getElementById(divname);//alert("obj===" +obj);
      if (obj) {                
        obj.innerHTML = "<table width='90%'><tr><td align=center><img src=../js/loading.gif><br>数据查询中</td></tr></table>";
      }
  
  http_request.onreadystatechange = test;
  // 确定发送请求的方式和URL以及是否异步执行下段代码
  if(method.toLowerCase()=="get") {
    http_request.open(method, URL, true);
  }
  else if(method.toLowerCase()=="post") {
    http_request.open(method, URL, true);
    //http_request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  }
  else {
    window.alert("http请求类别参数错误。");
    return false;
  }
  http_request.send(null);
  var returnstr="";
  //alert(http_request.readyState);
  //http_request.onreadystatechange = test;    
}

// 处理返回文本格式信息的函数
function processTextResponse() {
  alert(http_request.readyState);
  if (http_request.readyState == 4) { // 判断对象状态
  	alert(http_request.status);
    if (http_request.status == 200) { // 信息已经成功返回，开始处理信息
      //returnstr = http_request.responseText;
      //alert(returnstr);
    } 
    else { //页面不正常
      alert("您所请求的页面有异常。");
    }
  }
  
  
}
//处理返回的XML格式文档的函数
function processXMLResponse() {
  if (http_request.readyState == 4) { // 判断对象状态
    if (http_request.status == 200) { // 信息已经成功返回，开始处理信息
      //alert(http_request.responseXML);
      alert("XML文档响应。");
    } 
    else { //页面不正常
      alert("您所请求的页面有异常。");
    }
  }
}
