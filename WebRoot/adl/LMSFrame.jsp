<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">
<style type="text/css">
	img {
		width:expression(this.width>90?"55px":this.width); 
		max-height:90px; 
		height:expression(this.height>50?"28px":this.height); 
	}
</style>
<title>SCORM 1.2.2 LMS</title>
<script language=javascript>
/****************************************************************************
**
** Function: LMSIsInitialized()
** Input:   none
** Output:  boolean
**
** Description:  This function returns a boolean that represents where or 
**               no LMSInitialize() has been called by the SCO.
**
***************************************************************************/
function LMSIsInitialized()
{
   // Determines if the API (LMS) is in an initialized state.
   // There is no direct method for determining if the LMS API is initialized
   // for example an LMSIsInitialized function defined on the API so we'll try
   // a simple LMSGetValue and trap for the LMS Not Initialized Error
   
   var value = API.LMSGetValue("cmi.core.student_name");
   var errCode = API.LMSGetLastError().toString();
   if (errCode == 301){
      return false;
   }else{
      return true;
   }
}

/****************************************************************************
**
** Function: login_onclick()
** Input:   none
** Output:  none
**
** Description:  This function changes the content frame to the login page,
**               as "hides" the login button.
**
***************************************************************************/
function login_onclick() {
   window.parent.frames[3].document.location.href = "LMSLogin.htm";
   if (document.layers != null){
      swapLayers();
   }else if(document.all != null){
      window.document.forms[0].login.style.visibility = "hidden";
   }else{
      //Niether IE nor Netscape is being used
      alert("您的浏览器可能不支持，请使用IE浏览器");
   }
}

/****************************************************************************
**
** 退出
**
***************************************************************************/
function logout_onclick() 
{
   // two known potential difficulties exist with having the logout
   // button in this frame...   The first is that the user may not
   // have exited the lesson before attempting to log out.  The second
   // problem is that a child window may be open containing the lesson
   // if the user has not exited.   To deal with these two cases, we'll
   // force the user to exit the lesson before we allow a logout.

   if (LMSIsInitialized() == true) {
      // we're making an assumtion that the user is trying
      // to log out without first exiting the lesson via the
      // appropriate means - because if the user had exited
      // the lesson, the LMS would not still be initialized.
      var  mesg = "退出前先注销课程";
      alert(mesg);
   }else{
      window.parent.frames[3].location.href="logout.jsp";
   }

   return;
}

/****************************************************************************
**
** Function: changeSCOContent()
** Input:   none
** Output:  none
**
** Description:  This function enables the appropriate controls so that
**               the user can progress to the next item.
**
***************************************************************************/
function changeSCOContent()
{
   //This function is called by the APIAdapterApplet during 
   //LMSFinish.
   if ( document.layers != null ){
     swapLayers();
   }else if ( document.all != null ){
     ctrl = window.document.forms[0].control.value; 
     
     if ( ctrl == "mixed" || ctrl == "flow" || ctrl == "" || ctrl == null ){
         window.top.frames[0].document.forms[0].next.style.visibility = "visible"; 
         window.top.frames[0].document.forms[0].previous.style.visibility = "visible";
         window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
         window.top.frames[0].document.forms[0].next.disabled = false;
         window.top.frames[0].document.forms[0].previous.disabled = false;
     }else if ( ctrl == "choice" ){ 
         window.top.frames[0].document.forms[0].next.style.visibility = "hidden"; 
         window.top.frames[0].document.forms[0].previous.style.visibility = "hidden"; 
         window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
      }else if ( ctrl = "auto" ){
         window.top.contentWindow.document.location.href = "sequencingEngine.jsp";
      }
   }else{
     //Neither IE nor Netscape is being used
     alert("您的浏览器可能不支持，请使用IE浏览器");
   }
}

/****************************************************************************
**
** Function: doNavEvent( navEvent)
** Input:   none
** Output:  none
**
** Description:  This function is called when an LMSFinish has been called by 
** the SCO after a navEvent has been set.
**                
**
***************************************************************************/
function  doNavEvent(navEvent){   
   //禁用按钮控制
   document.forms[0].next.disabled = true;
   document.forms[0].previous.disabled = true;
   document.forms[0].quit.disabled = true;
   document.forms[0].suspend.disabled = true;   
   
   // This is the launch line for the next SCO...
   // The Sequencing Engine determines which to launch and
   // serves it up into the LMS's content frame or child window - depending
    //on the method that was used to launch the content in the first place.
   var scoWinType = typeof(window.parent.frames[3].scoWindow);
   //alert("scoWinType:"+scoWinType);
   if ( navEvent == "continue" ){   
       navEvent = "next";
   }
   if ( navEvent == "previous" ){   
       navEvent = "prev";
   }

   var theURL = "sequencingEngine.jsp?button=" + navEvent;
  
   if (scoWinType != "undefined" && scoWinType != "unknown"){
      if (window.parent.frames[3].scoWindow != null){
         // there is a child content window so display the sco there.
         window.parent.frames[3].scoWindow.document.location.href = theURL;
	  }else{
         window.parent.frames[3].document.location.href = theURL;
      }
   }else{
      window.parent.frames[3].document.location.href = theURL;
                
   }
   
   if( document.layers != null ){
      swapLayers();
   }else if ( document.all != null ){
     // window.top.frames[0].document.forms[0].next.disabled = true;
     // window.top.frames[0].document.forms[0].previous.disabled = true;
   }else{
      //Neither IE nor Netscape is being used
      alert("您的浏览器不被支持！");
   }  
}

/****************************************************************************
**
** 下一页 
**
***************************************************************************/
function  nextSCO(){
   // This is the launch line for the next SCO...
   // The Sequencing Engine determines which to launch and
   // serves it up into the LMS's content frame or child window - depending
    //on the method that was used to launch the content in the first place.
   var scoWinType = typeof(window.parent.frames[3].scoWindow);
   var theURL = "pleaseWait.jsp?button=next";
  
   if (scoWinType != "undefined" && scoWinType != "unknown"){
      if (window.parent.frames[3].scoWindow != null){
         // there is a child content window so display the sco there.
         window.parent.frames[3].scoWindow.document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }else{
         window.parent.frames[3].document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }
   }else{
      window.parent.frames[3].document.location.href = theURL;
      window.parent.frames[2].document.location.href = "code.jsp";
   }
   if ( document.layers != null ){
      swapLayers();
   }else if ( document.all != null ){
     // window.top.frames[0].document.forms[0].next.disabled = true;
     // window.top.frames[0].document.forms[0].previous.disabled = true;
   }else{
      //Neither IE nor Netscape is being used
      alert("您的浏览器可能不支持，请使用IE浏览器");
   }  
}


/****************************************************************************
**
** 上一页
**
***************************************************************************/
function  previousSCO()
{

   // This function is called when the "Previous" button is clicked.
   // The LMSLesson servlet figures out which SCO to launch and
   // serves it up into the LMS's content frame or child window - depending
   //on the method that was used to launch the content in the first place.

   var scoWinType = typeof(window.parent.frames[3].scoWindow);
   var theURL = "pleaseWait.jsp?button=prev";
   
   if (scoWinType != "undefined" && scoWinType != "unknown"){
      if (window.parent.frames[3].scoWindow != null){
         // there is a child content window so display the sco there.
         window.parent.frames[3].scoWindow.document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }else{
         window.parent.frames[3].document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }
   }else{
      window.parent.frames[3].document.location.href = theURL;
      window.parent.frames[2].document.location.href = "code.jsp";

      //  scoWindow is undefined which means that the content frame
      //  does not contain the lesson menu at this time.
   }
   if( document.layers != null ){
      swapLayers();
   }else if ( document.all != null ){
     // window.document.forms[0].next.disabled = true;
     // window.document.forms[0].previous.disabled = true;
   }else{
     //Neither IE nor Netscape is being used
      alert("您的浏览器可能不支持，请使用IE浏览器");
   }
  
}

/****************************************************************************
**
** Function: closeSCOContent()
** Input:   none
** Output:  none
**
** Description:  This function exits out of the current lesson and presents
**               the RTE menu. 
**
***************************************************************************/
function closeSCOContent(){
   var scoWinType = typeof(window.parent.frames[3].window);
   
   ctrl = window.document.forms[0].control.value;
   
   if ( ctrl == "auto" ){
      window.parent.frames[2].document.location.href = "code.jsp";
      window.top.frames[3].location.href = "LMSMenu.jsp"
      window.top.contentWindow.close();
   }else{
      window.parent.frames[2].document.location.href = "code.jsp";   
      if(scoWinType != "undefined" && scoWinType != "unknown"){
         if (window.parent.frames[3].scoWindow != null){      
            // there is a child content window so close it.
            window.parent.frames[3].scoWindow.close();
            window.parent.frames[3].scoWindow = null;
         }
         window.parent.frames[3].document.location.href = "LMSMenu.jsp";
      }else{
         //  scoWindow is undefined which means that the content frame
         //  does not contain the lesson menu so do nothing...
      }
   }   
}

/****************************************************************************
**
** 切换登录按钮状态
**
***************************************************************************/
function swapLayers(){
   //if( document.loginLayer.visibility == "hide" ){
   //   document.logoutLayer.visibility = "hide";
   //   document.loginLayer.visibility = "show";
   //}else{
   //   document.loginLayer.visibility = "hide";
   //   document.logoutLayer.visibility = "show";
   //}
}

/****************************************************************************
**
** 设置API可用，并隐藏导航按钮
**
***************************************************************************/
function init()
{
   API = this.document.APIAdapter;
   window.top.frames[0].document.forms[0].next.style.visibility = "hidden"; 
   window.top.frames[0].document.forms[0].previous.style.visibility = "hidden";
}

/****************************************************************************
**
** 直接退出课程确认信息
**
***************************************************************************/
function doConfirm(){
    if(confirm("退出学习并保存学习记录, 确认退出吗")){
       window.parent.frames[3].document.location.href = "LMSMenu.jsp";
    }else{
        
    }
}
</script>
</HEAD>

<body onload="init();">

<form name="buttonform">

    <object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
            width="0" height="0" id="APIAdapter"
            codebase="http://java.sun.com/products/plugin/1.3/jinstall-13-win32.cab#Version=1,3,0,0">
    <param name = "code" value = "org/adl/samplerte/client/APIAdapterApplet.class" >
    <param name = "codebase" value = "/gdgat/adl" >
    <param name = "type" value="application/x-java-applet;version=1.3">
    <param name = "mayscript" value="true" >
    <param name = "scriptable" value="true" >
    <param name = "archive" value = "cmidatamodel.jar,lmsclient.jar,debug.jar" >
    <comment>
    <applet code="org/adl/samplerte/client/APIAdapterApplet.class" 
            archive="cmidatamodel.jar,lmsclient.jar,debug.jar" 
            codebase="/gdgat/adl"
            src="/gdgat/adl" 
            height="1" 
            id="APIAdapter" 
            name="APIAdapter" 
            width="1" 
            mayscript="true">
    </applet>
    </comment>
    </object>
       
         
    <table width="800">
    <tr valign="top"> 
       <td>
          <!--IMG ALIGN="Left" SRC="/adl/images/adlLogo.gif"/-->
          <img align="Left" src="../images/tiertwo.gif"/>
       </td>
       <td align="center">
       <b> 高级分布式学习系统  (SCORM<SUP>&reg;</SUP>) 1.2 <br> </b>
       </td>
    </tr>
    </table> 
    
     
    <input type="hidden" name="control" value="" />            
       
    <!--NOLAYER-->
    <table width="600" align="left" cellspacing=0>
    <tr>
       <!-- td> 
          <input type="button" value="登录" id="login" name="login" onclick="return login_onclick();">&nbsp;       
       </td>
       <td align="left">
          <input type="button" value="退出" id="logout" name="logout" style="visibility: hidden" onclick="return logout_onclick();"> 
       </td -->
       <td align="center">
             <INPUT type="button" ALIGN = "right" VALUE="停止 " name="quit" ONCLICK="doConfirm();" STYLE="visibility: hidden">
       </td>
       <td align="left">
          <input type="button" align ="left" value="Glossary" id="glossary" name="glossary" onclick="return nextSco();"  style="visibility: hidden" disabled>&nbsp; 
       </td>
       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       <td align="center"> 
          <input type="button" align ="right" value="<- 上一页" id="previous" name="previous" onclick="return previousSCO();"  style="visibility: hidden"> 
       </td>
       <td align="center">
             <input type="button" align ="right" value="下一页 ->" id="next" name="next"  onclick="return nextSCO();" style="visibility: hidden">   
       </td>
    </tr>
</table>
    
<!--/NOLAYER-->
</form>

</body>
</html>
