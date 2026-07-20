function getlicense()
{	
  document.getElementById('license1').style.display='none';
  document.getElementById('license2').style.display='none';
  if (document.getElementById('viewlicense').checked==true)
  {
	if(document.getElementById('enterment_name').style.display=='none')
		document.getElementById('license1').style.display='';
	else
		document.getElementById('license2').style.display='';
  }
}
var msg	;
var bname_m=false;
var ajaxchk=null;
var ajaxstr=null;
function init_reg(){
	msg=new Array(
	"请输入"+minlen+"-"+maxlen+"位字符，英文、数字、下划线的组合。",
	"请输入4-14位字符，英文、数字的组合。",
	"请输入6位以上字符，不允许空格。",
	"请重复输入上面的密码。",
	"请选择密码提示问题。",
	"6个字符、数字或3个汉字以上（包括6个）。",
	"请输入您常用的电子邮箱地址。",
	"请输入验证码。"
	)
	document.getElementById("usernamemsg").innerHTML=msg[0];
	document.getElementById("passwordmsg1").innerHTML=msg[2];
	document.getElementById("passwordmsg2").innerHTML=msg[3];
	document.getElementById("questionmsg").innerHTML=msg[4];
	document.getElementById("answermsg").innerHTML=msg[5];
	document.getElementById("emailmsg").innerHTML=msg[6];
	document.getElementById("chkcodemsg").innerHTML=msg[7];
}
init_reg();
function on_input(objname){
	var strtxt;
	var obj=document.getElementById(objname);
	obj.className="d_on";
	//alert(objname);
	switch (objname){
		case "usernamemsg":
			strtxt=msg[0];
			break;
		case "passwordmsg1":
			strtxt=msg[2];
			break;
		case "passwordmsg2":
			strtxt=msg[3];
			break;
		case "answermsg":
			strtxt=msg[5];
			break;
		case "emailmsg":
			strtxt=msg[6];
			break;
		case "chkcodemsg":
		    strtxt=msg[7];
			break;	
	}
	obj.innerHTML=strtxt;
}
function out_username(){
	var obj=document.getElementById("usernamemsg");
	var str=sl(document.getElementById("UserName").value);
	var chk=true;
	//alert(str);
	if (str<minlen || str>maxlen){chk=false;}
	if (!chk){
		obj.className="d_err";
		obj.innerHTML=msg[0];
		return;
	}
	ajaxLoadPage("ajax_check.asp","action=checkusername&username="+document.getElementById("UserName").value,"post")
	if (ajaxchk=='ok'){
	  obj.className="d_ok";
	  obj.innerHTML=ajaxstr;
	 }else{
		obj.className="d_err";
		obj.innerHTML=ajaxstr;
	 }
}
function out_password1(){
	var obj=document.getElementById("passwordmsg1");
	var str=document.getElementById("PassWord").value;
	var chk=true;
	if (str=='' || str.length<6 || str.length>14){chk=false;}
	if (chk){
		obj.className="d_ok";
		obj.innerHTML='密码已经输入。';
	}else{
		obj.className="d_err";
		obj.innerHTML=msg[2];
	}
	return chk;
}
function out_password2(){
	var obj=document.getElementById("passwordmsg2");
	var str=document.getElementById("RePassWord").value;
	var chk=true;
	if (str!=document.getElementById("PassWord").value||str==''){chk=false;}
	if (chk){
		obj.className="d_ok";
		obj.innerHTML='重复密码输入正确。';
	}else{
		obj.className="d_err";
		obj.innerHTML=msg[3];
	}
	return chk;
}
function out_question(){
	var obj=document.getElementById("questionmsg");
	var str=document.getElementById("Question").value;
	var chk=true;
	if (str==''){chk=false}
	if (chk){
		obj.className="d_ok";
		obj.innerHTML='密码提示问题已经选择。';
	}else{
		obj.className="d_err";
		obj.innerHTML=msg[4];
	}
	return chk;
}
function out_answer(){
	var obj=document.getElementById("answermsg");
	var str=sl(document.getElementById("Answer").value);
	var chk=true;
	if (str<6 || str>40){chk=false}
	if (chk){
		obj.className="d_ok";
		obj.innerHTML='密码提示问题答案已经输入。';
	}else{
		obj.className="d_err";
		obj.innerHTML=msg[5];
	}
	return chk;
}
function out_email(){
	var obj=document.getElementById("emailmsg");
	var str=document.getElementById("Email").value;
	var chk=true;
	if (str==''|| !str.match(/^[\w\.\-]+@([\w\-]+\.)+[a-z]{2,4}$/ig)){chk=false}
	if (chk){
		obj.className="d_ok";
		obj.innerHTML='电子邮箱地址已经输入。';
	}else{
		obj.className="d_err";
		obj.innerHTML=msg[6];
		return chk;
	}
	ajaxLoadPage("ajax_check.asp","action=checkemail&email="+str,"post")
	if (ajaxchk=='ok'){
	  obj.className="d_ok";
	  obj.innerHTML=ajaxstr;
	 }else{
		obj.className="d_err";
		obj.innerHTML=ajaxstr;
	 }
}
function out_chkcode()
{	var obj=document.getElementById("chkcodemsg");
	var str=sl(document.getElementById("Verifycode").value);
	var chk=true;
	if (str<4 || str>6){chk=false}
	if (chk){
		obj.className="d_ok";
		obj.innerHTML='验证码已经输入。';
	}else{
		obj.className="d_err";
		obj.innerHTML=msg[7];
	return chk;
	}
	ajaxLoadPage("ajax_check.asp","action=checkcode&code="+document.getElementById("Verifycode").value,"post")
	if (ajaxchk=='ok'){
	  obj.className="d_ok";
	  obj.innerHTML=ajaxstr;
	 }else{
		obj.className="d_err";
		obj.innerHTML=ajaxstr;
	 }
}
function sl(st){
	sl1=st.length;
	strLen=0;
	for(i=0;i<sl1;i++){
		if(st.charCodeAt(i)>255) strLen+=2;
	 else strLen++;
	}
	return strLen;
}
	<!----检查用户名，电子邮箱开始-->
	function Check()
	{
	var Name=document.all.UserName.value;
	window.open("UserReg.asp?Action=Check&menu=UserName_Check&UserName="+Name,"","top=150,left=350,width=250,height=20");
	}
	function CheckEmail()
	{var Email=document.all.Email.value;
	window.open("UserReg.asp?Action=Check&menu=Email_Check&Email="+Email,"","top=150,left=350,width=250,height=20");
     }  
	 <!----检查用户名，电子邮箱结束-->
	 
      function CheckForm() 
		{ 
			if (document.myform.UserName.value =="")
			{
			alert("请填写您的会员名！");
			document.myform.UserName.focus();
			return false;
			}
			//var filter=/^\s*[.A-Za-z0-9_-]{{$Show_UserNameLimitChar},{$Show_UserNameMaxChar}}\s*$/;
			//if (!filter.test(document.myform.UserName.value)) { 
			//alert("会员名填写不正确,请重新填写！可使用的字符为（A-Z a-z 0-9 _ - .)长度不小于{$Show_UserNameLimitChar}个字符，不超过{$Show_UserNameMaxChar}个字符，注意不要使用空格。"); 
			//document.myform.UserName.focus();
			//return false; 
			//} 
			if (document.myform.PassWord.value =="") 
			{
			alert("请填写您的密码！");
			document.myform.PassWord.focus();
			return false; 
			}
			if(document.myform.RePassWord.value==""){
			alert("请输入您的确认密码！");
			document.myform.RePassWord.focus();
			return false;
			}
			var filter=/^\s*[.A-Za-z0-9_-]{6,15}\s*$/;
			if (!filter.test(document.myform.PassWord.value)) { 
			alert("密码填写不正确,请重新填写！可使用的字符为（A-Z a-z 0-9 _ - .)长度不小于6个字符，不超过15个字符，注意不要使用空格。"); 
			document.myform.PassWord.focus();
			return false; 
			} 
			if (document.myform.PassWord.value!=document.myform.RePassWord.value ){
			alert("两次填写的密码不一致，请重新填写！"); 
			document.myform.PassWord.focus();
			return false; 
			} 
			if (document.myform.Question.value =="")
			{
			alert("请填写您的密码问题！");
			document.myform.Question.focus();
			return false;
			}
			if (document.myform.Answer.value =="")
			{
			alert("请填写您的问题答案！");
			document.myform.Answer.focus();
			return false;
			}
			if (document.myform.Email.value =="")
			{
			alert("请输入您的电子邮件地址！");
			document.myform.Email.focus();
			return false;
			}
			if((document.myform.Email.value.indexOf("@")==-1)||(document.myform.Email.value.indexOf(".")==-1))
			{
				alert("您输入的电子邮件地址有误！");
				document.myform.Email.focus();
				return false;
				}
				return true;
		}

function xmlhttp()
	{
		var A=null;
		try
		{
			A=new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch(e)
		{
			try
			{
				A=new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch(oc)
			{
				if (typeof XMLHttpRequest != "undefined" );
				{
					A=new XMLHttpRequest();
				}
			}
		}			
		return A;
	}
	
var loader=new xmlhttp;
function ajaxLoadPage(url,request,method)
{ 
	method=method.toUpperCase();
	if (method=='GET')
	{
		urls=url.split("?");
		if (urls[1]=='' || typeof urls[1]=='undefined')
		{
			url=urls[0]+"?"+request;
		}
		else
		{
			url=urls[0]+"?"+urls[1]+"&"+request;
		}
		
		request=null;
	}
	loader.open(method,url,false);
	if (method=="POST")
	{
		loader.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	}
	loader.onreadystatechange=function(){
	          if(loader.readyState==4)
				  { 
					 if (loader.status==200)
					 {
					   var s=loader.responseText;
					   ajaxchk=s.split('|')[0];
					   ajaxstr=s.split('|')[1];
					 }
				  }
	}
	loader.send(request);
}
