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
		<style>
#ddd img {
	display: block;
}

body {
	background-image:url(images/bg1111.jpg);
	background-color: #9381FC;
	background-repeat: no-repeat;
	background-position: center top;
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
		
		function cl(){
			var courseid=<s:property value="course.id" />;
			
			var classid=<s:property value="elClass.id" />;
			window.location.href="wjm_user_center_index.action?course.id="+courseid+"&elClass.id="+classid;
		}
		</script>
        

	</head>

	<body>
<table width="1044" height="531" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" background="images/20140416/cent_bg3.png" style="padding-left:0px;padding-top:35px;">
    
      <s:if test="myCourses!=null && myCourses.size()>0">
		<s:iterator value="myCourses">
		<s:if test="initCompliance">
			 <table border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<td width="57" height="48" align="center" class="bg_xing"><s:property value="course.name" /></td>
         			 <td width="677" height="57" align="right" valign="bottom"><!--
						学习中的单元，带链接。注意：链接是加在外层整个TABLE上面，背景图片为images/image0421/dy002.gif，链接样式为 class='textbg002'-->
             	 		<a href="javascript:listPagesByCourseid(<s:property value="course.id" />,<s:property value="elClass.id" />);" class="textbg001" onclick="cl();">
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
          			<td width="57" height="48" align="center" class="bg_xing"><s:property value="course.name" /></td>
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
          			<td width="57" height="48" align="center" class="bg_xing"><s:property value="course.name" /></td>
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


						

	</body>
</html>


