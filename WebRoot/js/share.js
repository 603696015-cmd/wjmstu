
//------------------------------------------状态栏欢迎滚动信息--welcome()-------------------------------

var hellotext="云南省干部在线学习学院，欢迎您！"
    var thetext=""
    var started=false
    var step=0
    var times=1

    function welcome()
    {
      times--
      if (times==0)
      { 
        if (started==false)
        {
          started = true;
          window.status = hellotext;
          setTimeout("anim()",1); 
        }
        thetext = hellotext;
      }
    }

    function showstatustext(txt)
    {
      thetext = txt;
      setTimeout("welcometext()",4000)
      times++
    }

    function anim()
    {
      step++
      if (step==7) {step=1}
      if (step==1) {window.status='>==='+thetext+'===<'}
      if (step==2) {window.status='=>=='+thetext+'==<='}
      if (step==3) {window.status='>=>='+thetext+'=<=<'}
      if (step==4) {window.status='=>=>'+thetext+'<=<='}
      if (step==5) {window.status='==>='+thetext+'=<=='}
      if (step==6) {window.status='===>'+thetext+'<==='}
      setTimeout("anim()",200);
    }
	
	
//---------------------------------------------显示系统当前时间代码--showDate()----------------------------

  var today = new Date();

  function showDate(){
	var year = today.getYear();
  	var month = today.getMonth() + 1; 
  	var date = today.getDate();		//日期 
  	var day = today.getDay();		//星期
  	var week =new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var dayValue = "";
  	dayValue += year + "年";
  	dayValue += ((month < 10) ? "0" : "") + month + "月";
  	dayValue += date + "日  ";
  	dayValue += (week[day]);
  	document.write("今天是：" + dayValue);
  }
  
//---------------------------------------------弹出指定大小窗口代码--popWin()-----------------------------

function popWin(web,w,h,t,l)
{
var web;
pop=window.open(web,'_blank','width='+ w +',height='+ h +',top='+ t +',left='+ l +',menubar=no,toolbar=no,scrollbars=yes,resizable=yes,status=no');
}


//---------------------------------------------添加收藏代码--myAddBookmark()------------------------------

function myAddBookmark(title,url)
{
    if ((typeof window.sidebar == 'object') && (typeof window.sidebar.addPanel == 'function'))//Gecko
    {
        window.sidebar.addPanel(title,url,"");
    }
    else//IE
    {
        window.external.AddFavorite(url,title);
    }
}


//---------------------------------------------登录框提交检验代码--onSubmit()----------------------------

function onSubmit()
{        
	if(login.login_id.value.length == 0)
	{
		alert("请输入用户名!") ;
		login.login_id.focus() ;
		return false;
	}
	if(login.login_pwd.value.length == 0)
	{
		alert("请输入密码!") ;
		login.login_pwd.focus() ;
		return false;
	}
	if(login.hn.checked)
    {
      login.action="/admin/admin_chklogin.asp";
	login.submit();
    }
    else
    {      
	  login.action="user_chklogin1.asp";
login.submit();
    }
}

//---------------------------------------------切换显示层--changeTab()----------------------------
var flog = 0;
var n = 1;
var total=0;
var LayerName;

function changeTab(LayerName,n)
{
 flog = 1;
 showTab(LayerName,n);
}

function showTab(LayerName,n)
{
with (LayerName)
  {
	for(i=0;i<children.length;i++)
	{
	 if (i==n)
	 {
	  children[i].style.visibility="visible";
	 }
	 else
	 {
	  children[i].style.visibility="hidden";
	 }
	}
	total=children.length;
  }
}
function tab(n){
 var tabnav="tab"+n;
 var tabid="tabid"+n;
 cleardisplay();
 document.getElementById(tabid).style.display="block";
  }
  function cleardisplay(){
 for (i=1;i<7;i++)
 {
  var cleartabid="tabid"+i;
  document.getElementById(cleartabid).style.display="none";
 }
  }

//---------------------------------------------------------(END)------------------------------------------