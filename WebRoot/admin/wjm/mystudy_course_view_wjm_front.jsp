<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商务汉语学习系统</title>
       
		<script type="text/javascript" src="js/jquery.js"></script>
		<script src="js/jquery.alerts.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/wjm2.css" />
		
		<link rel="stylesheet" type="text/css" href="css/wjm20140516/jscrollpane2.css" />
<script type="text/javascript" src="js/wjm20140516/jquery.js"></script>

<script type="text/javascript" src="js/wjm20140516/jquery.mousewheel.js"></script>

<script type="text/javascript" src="js/wjm20140516/jquery.jscrollpane.min.js"></script>
		
		
		<style>
#ddd img {
	display: block;
}

body {
	font-family: "楷体";
}
.main{position:absolute; width:100%;top:0px; bottom:0; overflow:auto;}
.main .main_1{position:absolute; width:100%;top:0px; bottom:0; overflow:auto;}
* html .main .main_1{ background:#F90; position:static; height:100%;}/*for ie6*/

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE2 {
	color: #FF0000;
	font-size: 16px;
}
.STYLE4 {color: #000099; font-size: 16px; }

A.textbg001:link {
	color: #006699;text-decoration: none;font-size:18px;
}
A.textbg001:visited {
	color:blue;text-decoration: none;
}
A.textbg001:hover {
	color:red;text-decoration: none;font-size:19px;
}
A.textbg001:active {
	color:blue;text-decoration: none;
}


A.textbg002:link {
	color:red;text-decoration: none;font-size:18px;
}
A.textbg002:visited {
	color:red;text-decoration: none;
}
A.textbg002:hover {
	color:red;text-decoration: none;font-size:19px;
}
A.textbg002:active {
	color:red;text-decoration: none;
}
.bg_xing {

background:url(images/image0421/xing_g.png) no-repeat  center 12px; font-weight:bold;color: #000;font-size:18px;
}



.jp-container{width:100%;height:455px;}




</style>
		<!--淡入效果-->
		<script type="text/javascript">
		$(document).ready(function(){
			$(".main").hover(function(){
			  $(".main_1").fadeIn(3000);
			  });
		 //$(".btn1").hover(function(){
		//  $("p").fadeOut(1000);
		//  });
			//listCoursesByClassid1();
		});
		</script>

		<script type="text/javascript">
		
		
		
		
		
		function blockUser(){
			$.blockUI({ 
                message:"您正在考试,请考完后再进行操作,exam is in process,try it later", 
                css: { 
                border: 'none', 
                padding: '15px', 
                backgroundColor: 'yellow', 
                width:"300px",
                height:"100px", 
                opacity: .0, 
                color: 'Red' 
               } 
            }); 
		}
		function unblockUser(){
			$.unblockUI();
		}
		function refresh1(){
			window.onbeforeunload = null;
			window.setInterval(function(){
				window.location.href="wjm_user_center.action";
			},800);
		}
		
		function closeIndex(){
			$.alerts.dialogClass = "style_1"; // set custom style class
			jConfirm("确定退出,Logout，YES or NO?", 'Information', function(r) {
				$.alerts.dialogClass = null; // reset to default
				if(r){
					window.parent.location.href="logout.action";
				}
			});
			/**
			if(window.confirm("确定退出,Logout，YES or NO?")){
				window.location.href="logout.action";
			}
			*/
		}
		function listPagesByCourseid(courseid,classid){
			document.all("rightFrame").src = "wjm_user_center_index.action?course.id="+courseid+"&elClass.id="+classid;
		}
		
		function showWord(){
			document.all("rightFrame").src = "vocabulary_search.action?vocabulary.status=1&vocabulary.wordid=-1";
		}
		
		function showI(){
			//window.location.href="showIntelligent.action?elClass.id=<s:property value="elClass.id" />";
			document.all("rightFrame").src = "showIntelligent.action?elClass.id=<s:property value="elClass.id" />";
		}
		
		function backToNavigation(){
			window.location.href="wjm_user_center_navigation.action";
		}
		
		function cl(courseid,classid){
		
			
			window.location.href="wjm_user_center_index.action?course.id="+courseid+"&elClass.id="+classid;
		}
		</script>
        

</head>

	<body>
		<s:if test="myCourses.size()>9">
			



<table width="1044" height="550" border="0" align="center" cellpadding="0" cellspacing="0" background="images/20140416/cent_bg3.png" >
  <tr>
    
    <td align="center" valign="top" style="padding-left:0px;padding-top:50px;">
	
	<table width="768" height="456" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td>
		
		<div id="div1" style="position:absolute;width:765px;height:456px;left: 50%; margin:-225px 0px 0px -400px;overflow:hidden;">

<div id="div2" style="position:absolute;width:763px;height:684px;clear:none;top:0px;margin-left:auto;margin-right:auto;">	


	 
	  <s:if test="myCourses!=null && myCourses.size()>0">
		<s:iterator value="myCourses">
		<s:if test="initCompliance">
			 <table border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<s:if test="course.name>99">
				          			<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 14px; font-weight:bold;font-size:14px;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
	          			</s:if>
				          			<s:else>
									<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-size:18px;font-weight:bold;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:else>
         			 <td width="677" height="57" align="right" valign="bottom"><!--
						学习中的单元，带链接。注意：链接是加在外层整个TABLE上面，背景图片为images/image0421/dy002.gif，链接样式为 class='textbg002'-->
             	 		<a href="javascript:listPagesByCourseid(<s:property value="course.id" />,<s:property value="elClass.id" />);" class="textbg001" onclick="cl(<s:property value="course.id" />,<s:property value="elClass.id" />);">
             		 		<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/image0421/dy001.png">
               					 <tr>
                  				<td>
                  				<table width="677" height="57" border="0" cellpadding="0" cellspacing="0">
                     			 <tr>
                        			<td width="136" height="46" align="left" valign="middle" style="padding-left:10px;padding-top:8px;font-weight:bold;">
                       				 ${course.courseDetail }</td>
                        		<td width="125">&nbsp;</td>
                       			 <td align="left" style="padding-top:13px;padding-left:10px;"><s:property value="course.description" /></td>
                      			</tr>
                 			 </table>
                 			 </td>
               				 </tr>
              				</table>
             				 </a> 
   				  </td>
			   </tr>
   			</table>
		</s:if>
		<s:else>
		<s:if test="canLearn==1">
				<table border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<s:if test="course.name>99">
				          			<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 14px; font-weight:bold;font-size:14px;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
	          			  </s:if>
				          			<s:else>
									<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-size:18px;font-weight:bold;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:else>
         			 <td width="677" height="57" align="right" valign="bottom"><!--
						学习中的单元，带链接。注意：链接是加在外层整个TABLE上面，背景图片为images/image0421/dy002.gif，链接样式为 class='textbg002'-->
             	 		<a href="javascript:listPagesByCourseid(<s:property value="course.id" />,<s:property value="elClass.id" />);" class="textbg002"  onclick="cl();">
             		 		<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/image0421/dy002.gif">
               					 <tr>
                  				<td>
                  				<table width="677" height="57" border="0" cellpadding="0" cellspacing="0">
                     			 <tr>
                        			<td width="136" height="46" align="left" valign="middle" style="padding-left:10px;padding-top:8px;font-weight:bold;">  ${course.courseDetail }</td>
                        		<td width="125">&nbsp;</td>
                       			 <td align="left" style="padding-top:13px;padding-left:10px;"><s:property value="course.description" /></td>
                      			</tr>
                 			 </table>
                 			 </td>
               				 </tr>
              				</table>
             				 </a> 
   				  </td>
				  </tr>
      			</table>		
		</s:if>
		<s:else>
			 <table border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<s:if test="course.name>99">
				          			<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 14px; font-weight:bold;font-size:14px;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
	          			  </s:if>
				          			<s:else>
									<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-size:18px;font-weight:bold;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:else>
         			 <td width="677" height="57" align="right" valign="bottom"><!--
						学习中的单元，带链接。注意：链接是加在外层整个TABLE上面，背景图片为images/image0421/dy002.gif，链接样式为 class='textbg002'-->
             	 		<a href="javascript:void(0);" class="textbg001" >
             		 		<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/image0421/dy003.png">
               					 <tr>
                  				<td>
                  				<table width="677" height="57" border="0" cellpadding="0" cellspacing="0">
                     			 <tr>
                        			<td width="136" height="46" align="left" valign="middle" style="padding-left:10px;padding-top:8px;font-weight:bold;">
                       				  ${course.courseDetail }</td>
                        		<td width="125">&nbsp;</td>
                       			 <td align="left" style="padding-top:13px;padding-left:10px;"><s:property value="course.description" /></td>
                      			</tr>
                 			 </table>
                 			 </td>
               				 </tr>
              				</table>
       				   </a> 
   				  </td>
				</tr>
   			  </table>
		</s:else>
	</s:else>
	
	</s:iterator>
	</s:if>
	 
	
	</td>
  </tr>
</table>

	</div>
</div>		</td>
    </tr>
    </table>
	</td>
   
  </tr>
</table>



<div style="position:absolute;width:80px;height:166px;left: 50%; margin:-170px 0px 0px 380px;">

	
	<input id="in1" style="width:60px;height:60px;background-image: url(images/20140416/shanghui.png);" type="button" name="111"  value="" onclick="up()"/>
	
	<!--
	如果不能向上移动了，就显示以下这个IPNUT,和初始化的向上INPUT相比，换了STYLE样式。
	<input style="width:80px;height:80px;background:#CCCCCC" type="button" name="111"  value="上" onclick="up()"/>
	
	-->
	
     
  <input id="in2" style="width:60px;height:60px;margin-top:3px;background-image: url(images/20140416/xiacai.png);" type="button" name="111"  value="" onclick="down()"/>
  
  <!--
	初始化时，或者如果不能向下移动了，就显示以下这个IPNUT。当向上移动后，可以再向下移动时，就显示上述未注掉的INPUT.
	<input style="width:80px;height:80px;background:#CCCCCC" type="button" name="111"  value="下" onclick="down()"/>
	
	-->
	
	</div>


<script type="text/javascript">
	var div=document.getElementById("div2");
	var in1=document.getElementById("in1");
	var in2=document.getElementById("in2");
	

  /*function up(){
  	if(parseInt(div.style.top)>=0){
  				in1.style.background="url(images/20140416/shangcai.png)";
  		}else{
  				in2.style.background="url(images/20140416/shanghui.png)";
  		 div.style.top = (parseInt(div.style.top) + 229) + "px"; //up
  		 	
  		}
  	}
  	
  	 function down(){
  	 	if(parseInt(div.style.top)<=-229){
  				in2.style.background="url(images/20140416/xiacai.png)";
  		}else{
  			in1.style.background="url(images/20140416/xiahui.png)";
  			 div.style.top = (parseInt(div.style.top) - 229) + "px"; //down
  			}
  		 
  	}*/
  	
  	function up(){
  	if(parseInt(div.style.top)>=0){
  				//in1.style.background="#CCCCCC";
  				in1.style.background="url(images/20140416/shanghui.png)";
  		}else{
  			//	in2.style.background="#0099FF";
  			in2.style.background="url(images/20140416/xiacai.png)";
  		 div.style.top = (parseInt(div.style.top) + 229) + "px"; //up
  		// in1.style.background="#CCCCCC";
  		in1.style.background="url(images/20140416/shanghui.png)";
  		 	
  		}
  	}
  	
  	 function down(){
  	 	if(parseInt(div.style.top)<=-229){
  				//in2.style.background="#CCCCCC";
  				in2.style.background="url(images/20140416/xiahui.png)";
  		}else{
  			//in1.style.background="#0099FF";
  			in1.style.background="url(images/20140416/shangcai.png)";
  			 div.style.top = (parseInt(div.style.top) - 229) + "px"; //down
  			// in2.style.background="#CCCCCC";
  			in2.style.background="url(images/20140416/xiahui.png)";
  			}
  		 
  	}
  	
  	function click1(){
  		alert(div.style.top);
  	}
 </script>

						

			
			</s:if>
			<s:else>
<table width="1044" height="550" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" background="images/20140416/cent_bg3.png" style="padding-left:0px;padding-top:50px;">
    
    <table width="768" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
	<div style="width:100%;height:455px;overflow:hidden;">
	 
	  <s:if test="myCourses!=null && myCourses.size()>0">
		<s:iterator value="myCourses">
		<s:if test="initCompliance">
			 <table border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<s:if test="course.name>99">
				          			<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 14px; font-weight:bold;font-size:14px;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:if>
				          			<s:else>
									<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-size:18px;font-weight:bold;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:else>
         			 <td width="677" height="57" align="right" valign="bottom"><!--
						学习中的单元，带链接。注意：链接是加在外层整个TABLE上面，背景图片为images/image0421/dy002.gif，链接样式为 class='textbg002'-->
             	 		<a href="javascript:listPagesByCourseid(<s:property value="course.id" />,<s:property value="elClass.id" />);" class="textbg001" onclick="cl(<s:property value="course.id" />,<s:property value="elClass.id" />);">
             		 		<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/image0421/dy001.png">
               					 <tr>
                  				<td>
                  				<table width="677" height="57" border="0" cellpadding="0" cellspacing="0">
                     			 <tr>
                        			<td width="136" height="46" align="left" valign="middle" style="padding-left:10px;padding-top:8px;font-weight:bold;">
                       				 ${course.courseDetail }</td>
                        		<td width="125">&nbsp;</td>
                       			 <td align="left" style="padding-top:13px;padding-left:10px;"><s:property value="course.description" /></td>
                      			</tr>
                 			 </table>
                 			 </td>
               				 </tr>
              				</table>
             				 </a> 
   				  </td>
			   </tr>
   			</table>
		</s:if>
		<s:else>
		<s:if test="canLearn==1">
				<table border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<s:if test="course.name>99">
				          			<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 14px; font-weight:bold;font-size:14px;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:if>
				          			<s:else>
									<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-size:18px;font-weight:bold;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:else>
         			 <td width="677" height="57" align="right" valign="bottom"><!--
						学习中的单元，带链接。注意：链接是加在外层整个TABLE上面，背景图片为images/image0421/dy002.gif，链接样式为 class='textbg002'-->
             	 		<a href="javascript:listPagesByCourseid(<s:property value="course.id" />,<s:property value="elClass.id" />);" class="textbg002"  onclick="cl();">
             		 		<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/image0421/dy002.gif">
               					 <tr>
                  				<td>
                  				<table width="677" height="57" border="0" cellpadding="0" cellspacing="0">
                     			 <tr>
                        			<td width="136" height="46" align="left" valign="middle" style="padding-left:10px;padding-top:8px;font-weight:bold;">  ${course.courseDetail }</td>
                        		<td width="125">&nbsp;</td>
                       			 <td align="left" style="padding-top:13px;padding-left:10px;"><s:property value="course.description" /></td>
                      			</tr>
                 			 </table>
                 			 </td>
               				 </tr>
              				</table>
             				 </a> 
   				  </td>
				  </tr>
      			</table>		
		</s:if>
		<s:else>
			 <table border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<s:if test="course.name>99">
				          			<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 14px; font-weight:bold;font-size:14px;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:if>
				          			<s:else>
									<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-size:18px;font-weight:bold;color: #000;padding-top:7px;"><s:property value="course.name" /></td>
				          			</s:else>
         			 <td width="677" height="57" align="right" valign="bottom"><!--
						学习中的单元，带链接。注意：链接是加在外层整个TABLE上面，背景图片为images/image0421/dy002.gif，链接样式为 class='textbg002'-->
             	 		<a href="javascript:void(0);" class="textbg001" >
             		 		<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/image0421/dy003.png">
               					 <tr>
                  				<td>
                  				<table width="677" height="57" border="0" cellpadding="0" cellspacing="0">
                     			 <tr>
                        			<td width="136" height="46" align="left" valign="middle" style="padding-left:10px;padding-top:8px;font-weight:bold;">
                       				  ${course.courseDetail }</td>
                        		<td width="125">&nbsp;</td>
                       			 <td align="left" style="padding-top:13px;padding-left:10px;"><s:property value="course.description" /></td>
                      			</tr>
                 			 </table>
                 			 </td>
               				 </tr>
              				</table>
       				   </a> 
   				  </td>
				</tr>
   			  </table>
		</s:else>
	</s:else>
	
	</s:iterator>
	</s:if>
	 
	 </div>
	</td>
  </tr>
</table>

	 
	 
  </td>
  </tr>
</table>

<script type="text/javascript">
			$(function() {
			
				// the element we want to apply the jScrollPane
				var $el					= $('#jp-container').jScrollPane({
					verticalGutter 	: -16
				}),
						
				// the extension functions and options 	
					extensionPlugin 	= {
						
						extPluginOpts	: {
							// speed for the fadeOut animation
							mouseLeaveFadeSpeed	: 500,
							// scrollbar fades out after hovertimeout_t milliseconds
							hovertimeout_t		: 1000,
							// if set to false, the scrollbar will be shown on mouseenter and hidden on mouseleave
							// if set to true, the same will happen, but the scrollbar will be also hidden on mouseenter after "hovertimeout_t" ms
							// also, it will be shown when we start to scroll and hidden when stopping
							useTimeout			: false,
							// the extension only applies for devices with width > deviceWidth
							deviceWidth			: 980
						},
						hovertimeout	: null, // timeout to hide the scrollbar
						isScrollbarHover: false,// true if the mouse is over the scrollbar
						elementtimeout	: null,	// avoids showing the scrollbar when moving from inside the element to outside, passing over the scrollbar
						isScrolling		: false,// true if scrolling
						addHoverFunc	: function() {
							
							// run only if the window has a width bigger than deviceWidth
							if( $(window).width() <= this.extPluginOpts.deviceWidth ) return false;
							
							var instance		= this;
							
							// functions to show / hide the scrollbar
							$.fn.jspmouseenter 	= $.fn.show;
							$.fn.jspmouseleave 	= $.fn.fadeOut;
							
							// hide the jScrollPane vertical bar
							var $vBar			= this.getContentPane().siblings('.jspVerticalBar').hide();
							
							/*
							 * mouseenter / mouseleave events on the main element
							 * also scrollstart / scrollstop - @James Padolsey : http://james.padolsey.com/javascript/special-scroll-events-for-jquery/
							 */
							$el.bind('mouseenter.jsp',function() {
								
								// show the scrollbar
								$vBar.stop( true, true ).jspmouseenter();
								
								if( !instance.extPluginOpts.useTimeout ) return false;
								
								// hide the scrollbar after hovertimeout_t ms
								clearTimeout( instance.hovertimeout );
								instance.hovertimeout 	= setTimeout(function() {
									// if scrolling at the moment don't hide it
									if( !instance.isScrolling )
										$vBar.stop( true, true ).jspmouseleave( instance.extPluginOpts.mouseLeaveFadeSpeed || 0 );
								}, instance.extPluginOpts.hovertimeout_t );
								
								
							}).bind('mouseleave.jsp',function() {
								
								// hide the scrollbar
								if( !instance.extPluginOpts.useTimeout )
									$vBar.stop( true, true ).jspmouseleave( instance.extPluginOpts.mouseLeaveFadeSpeed || 0 );
								else {
								clearTimeout( instance.elementtimeout );
								if( !instance.isScrolling )
										$vBar.stop( true, true ).jspmouseleave( instance.extPluginOpts.mouseLeaveFadeSpeed || 0 );
								}
								
							});
							
							if( this.extPluginOpts.useTimeout ) {
								
								$el.bind('scrollstart.jsp', function() {
								
									// when scrolling show the scrollbar
									clearTimeout( instance.hovertimeout );
									instance.isScrolling	= true;
									$vBar.stop( true, true ).jspmouseenter();
									
								}).bind('scrollstop.jsp', function() {
									
									// when stop scrolling hide the scrollbar (if not hovering it at the moment)
									clearTimeout( instance.hovertimeout );
									instance.isScrolling	= false;
									instance.hovertimeout 	= setTimeout(function() {
										if( !instance.isScrollbarHover )
											$vBar.stop( true, true ).jspmouseleave( instance.extPluginOpts.mouseLeaveFadeSpeed || 0 );
									}, instance.extPluginOpts.hovertimeout_t );
									
								});
								
								// wrap the scrollbar
								// we need this to be able to add the mouseenter / mouseleave events to the scrollbar
								var $vBarWrapper	= $('<div/>').css({
									position	: 'absolute',
									left		: $vBar.css('left'),
									top			: $vBar.css('top'),
									right		: $vBar.css('right'),
									bottom		: $vBar.css('bottom'),
									width		: $vBar.width(),
									height		: $vBar.height()
								}).bind('mouseenter.jsp',function() {
									
									clearTimeout( instance.hovertimeout );
									clearTimeout( instance.elementtimeout );
									
									instance.isScrollbarHover	= true;
									
									// show the scrollbar after 100 ms.
									// avoids showing the scrollbar when moving from inside the element to outside, passing over the scrollbar								
									instance.elementtimeout	= setTimeout(function() {
										$vBar.stop( true, true ).jspmouseenter();
									}, 100 );	
									
								}).bind('mouseleave.jsp',function() {
									
									// hide the scrollbar after hovertimeout_t
									clearTimeout( instance.hovertimeout );
									instance.isScrollbarHover	= false;
									instance.hovertimeout = setTimeout(function() {
										// if scrolling at the moment don't hide it
										if( !instance.isScrolling )
											$vBar.stop( true, true ).jspmouseleave( instance.extPluginOpts.mouseLeaveFadeSpeed || 0 );
									}, instance.extPluginOpts.hovertimeout_t );
									
								});
								
								$vBar.wrap( $vBarWrapper );
							
							}
						
						}
						
					},
					
					// the jScrollPane instance
					jspapi 			= $el.data('jsp');
					
				// extend the jScollPane by merging	
				$.extend( true, jspapi, extensionPlugin );
				jspapi.addHoverFunc();
			
			});
		</script>
						
</s:else>
	</body>
</html>


