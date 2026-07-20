<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<basefont face="楷体" size="13px" />
		<title><s:property value="epblock.title" />帮助SWF</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script src="js/jquery.alerts.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="js/listen_keydown.js"></script>
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet" type="text/css" media="screen" />
		<style type="text/css">
		<!--
		body {
			background-color: #033B5E;
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			background-image: url(images/images1113/chspxx.jpg);
			background-position: center top;
			background-repeat: repeat-y;
		}
		
		.STYLE1 {
			color: #FFFFFF;
			font-size: 16px;
			font-weight: bold;
		}
		-->
		</style>
		<script type="text/javascript">
		function quiz(){
		//	js_stop();
			blockUser("加载中Loading...");
			window.parent.q_show(<s:property value="epblock.sortid" />,1);
		}
		
		function blockUser(message){
			$.blockUI({ 
		              message:'<img src="http://images.hzins.com/web/loading2010.gif" border="0" align="absmiddle" />&nbsp;&nbsp;<span style="font-size:25px;">'+message+'</span>',
		              css: { 
		              border: '3px solid #aaa', 
		              padding: '15px', 
		              backgroundColor: '#CC3300', 
		              width:"300px",
		              height:"50px",
		              opacity: 0.9, 
		              color: '#000' 
		             } 
		          }); 
		}
		
		/*function js_stop(){
			var image = document.getElementById("pause_play");
			image.src = "images/bf/anniu_36.png";
			video.Rewind();
			
		}
		//播放暂停切换OK
		
		function js_pause_play(){
			var image = document.getElementById("pause_play");
			if(video.IsPlaying()){
				image.src = "images/bf/anniu_36.png";
				video.StopPlay();
			}else{
				image.src = "images/bf/anniu_31.png";
				video.play();
			}
		}
		//从头开始播放 OK
		
		function js_reset(){
			image.src = "images/bf/anniu_31.png";
			video.Rewind();
			video.play();
		}*/
		$(function(){
				var _cvideo = new CourseVideo(1,"<s:property value="helpSwf" />", 60*60);
				_cvideo.show("swfcontent");
		});
		
		function backup(){
			var classid ;
			var courseid ;
			if(window.parent.classid!=undefined){
				classid = window.parent.classid;
			}
			if(window.parent.courseid!=undefined){
				courseid = window.parent.courseid;
			}
			$.alerts.dialogClass = "style_1"; // set custom style class
			jConfirm("确认回到首页? Back to Home Page,Sure?", 'Information', function(r) {
				$.alerts.dialogClass = null; // reset to default
				if(r){
					window.parent.qsubmit();
					window.parent.location.href='wjm_user_center.action?elClass.id='+classid+"&course.id="+courseid+"&backuptoindex=1";
				}
			});
		}
		</script>
	</head>

	<body>


		<table width="984" border="0" align="center" cellpadding="0" cellspacing="0"
									style="margin-top:87px;"><!-- sun 样式修改-->
									<tr>
									  <td height="45" align="center" valign="middle">
									    <span style="font-family: 华文楷体; color: red; font-size: 25px;">											  </span>
									    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tr>
                                            <td width="200" height="74" valign="middle" style="padding-top:2px;"><table border="0" cellspacing="0" style="margin-left:20px;margin-top:0px;">
                                              <tr>
                                                <td width="70" align="left" valign="middle"><img height="51" src="images/datigaiban/fanhui.png" width="56" style="cursor:pointer;" onclick="backup();" /></td>
											    <td align="left" valign="top"><img src="images/bf/startquiz.png" width="90" height="53" onClick="quiz()"  style="cursor:pointer;"
												onmousedown="this.src='images/bf/startquiz_h.png'"
												onmouseup="this.src='images/bf/startquiz.png'" /></td>
                                               
                                              </tr>
                                            </table></td>
                                            <td align="center" valign="middle"> 
												<%--	 <span style="font-family: 华文楷体; color: red; font-size: 25px;">操作演示：
									    <s:property value="epblock.title" />
									    题SAMPLE </span>
										
										--%>
										 <%--<s:property value="editorHTML" />--%>
										 ${editorHTML}										</td>
                                            <td width="200" valign="middle">
											   <!--
											   <table border="0" cellspacing="0" style="margin-left:20px;margin-top:6px;">
                                              <tr>
                                                <td width="50" align="left"></td>
                                                <td width="50" align="left"></td>
                                                <td width="50" align="left"><img height="35" src="images/datigaiban/fanhui.png" width="35" onclick="backup();" /></td>
                                              </tr>
                                            </table>
											   
											   -->											</td>
                                          </tr>
                                        </table>
									 
									  </td>
									</tr>
									<tr>
										<td align="center" valign="middle" style="padding-left: 4px;padding-right:0px;padding-top:0px;">
						              <div id="swfcontent"
												style="width: 966px; height: 540px; overflow: hidden"></div>										</td>
									</tr>
    </table>
	</body>
</html>


