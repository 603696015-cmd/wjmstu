<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商务汉语学习系统</title>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<link rel="stylesheet" href="css/wjm.css" />
		<style>
#ddd img {
	display: block;
}

body {
	background-color: #666666;
}
</style>

		<script type="text/javascript">
		function iframe(){
			document.all("rightFrame").height=rightFrame.document.body.scrollHeight;
			document.all("rightFrame").width=rightFrame.document.body.scrollWidth;
		}
		function listCoursesByClassid(batchid,classid){
			//document.all("rightFrame").src = "mystudy_course_view_wjm.action?peixunBatch.id="+batchid+"&elClass.id="+classid;
			document.all("rightFrame").src = "wjm_user_center_index.action?elClass.id="+classid;
		}
		function listClasses(){
			document.all("rightFrame").src = "mystudy_class_view_wjm.action";
		}
		function blockUser(){
			$.blockUI({ 
                message:"您正在考试,请考完后再进行操作<br>You're examinations, please finished before handling", 
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
		</script>

	</head>

	<body onload="iframe()">
		
		<table width="990" border="0" align="center" cellpadding="0"
			cellspacing="0" background="images/bluebg2.jpg">
			<tr>
				<td align="center" valign="top">
					<!--头部开始-->
					<div id="head">
						<div id="schedule">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<s:if test="peixunBatch.process==0">
										<td width="<s:property value="peixunBatch.process" />%">
										</td>
									</s:if>
									<s:else>
										<td width="<s:property value="peixunBatch.process" />%">
											<p onclick="javascript:listClasses();"></p>
										</td>
									</s:else>
									<td align="left">
										<a href="javascript:listClasses();"><img src="images/go.gif" /></a>
									</td>
								</tr>
							</table>
		
						</div>
						<a href="wjm_user_center.action"><div id="fanhui"></div>
						</a>
						<div id="zhang">
							<ul style=" margin-left:2px;">
								<s:if test="myclasses.size()==0">
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												1A
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												1B
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												2A
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												2B
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												3A
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												3B
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												4A
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												4B
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												5A
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												5B
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												6A
											</p>
										</li> </a>
									<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												6B
											</p>
										</li> </a>
								</s:if>
								<s:else>
									<a
										href="<s:if test="myclasses[0].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[0].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[0].processForElc"/>%;'>
												1A
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[1].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[1].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[1].processForElc"/>%;'>
												1B
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[2].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[2].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value ="myclasses[2].processForElc"/>%;'>
												2A
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[3].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[3].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value ="myclasses[3].processForElc"/>%;'>
												2B
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[4].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[4].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[4].processForElc"/>%;'>
												3A
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[5].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[5].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[5].processForElc"/>%;'>
												3B
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[6].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[6].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[6].processForElc"/>%;'>
												4A
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[7].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[7].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[7].processForElc"/>%;'>
												4B
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[8].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[8].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[8].processForElc"/>%;'>
												5A
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[9].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[9].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value ="myclasses[9].processForElc"/>%;'>
												5B
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[10].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[10].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value="myclasses[10].processForElc"/>%;'>
												6A
											</p>
										</li> </a>
									<a
										href="<s:if test="myclasses[11].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[11].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
											<p
												style='font-size: 30px; width: <s:property value ="myclasses[11].processForElc"/>%;'>
												6B
											</p>
										</li> </a>
								</s:else>
							</ul>
						</div>
						<div id="JZ">
							<ul>
								<s:if test="myclasses.size()==0">
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ1_h.png) no-repeat;">
											<p style="background: url(images/JZ1.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ2_h.png) no-repeat;">
											<p style="background: url(images/JZ2.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ3_h.png) no-repeat; margin-left: 6px;">
											<p style="background: url(images/JZ3.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ4_h.png) no-repeat;">
											<p style="background: url(images/JZ4.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ5_h.png) no-repeat; margin-left: 6px;">
											<p style="background: url(images/JZ5.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);">
										<li style="background: url(images/JZ6_h.png) no-repeat;">
											<p style="background: url(images/JZ6.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ7_h.png) no-repeat; margin-left: 6px;">
											<p style="background: url(images/JZ7.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);">
										<li style="background: url(images/JZ8_h.png) no-repeat;">
											<p style="background: url(images/JZ8.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ9_h.png) no-repeat; margin-left: 6px;">
											<p style="background: url(images/JZ9.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ10_h.png) no-repeat;">
											<p style="background: url(images/JZ10.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ11_h.png) no-repeat; margin-left: 6px;">
											<p style="background: url(images/JZ11.png); width: 0%;"></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ12_h.png) no-repeat;">
											<p style="background: url(images/JZ12.png); width: 0%;"></p>
										</li> </a>
								</s:if>
								<s:else>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ1_h.png) no-repeat;">
											<p
												style='background: url(images/JZ1.png); width: <s:property value="myclasses[0].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ2_h.png) no-repeat;">
											<p
												style='background: url(images/JZ2.png); width: <s:property value="myclasses[1].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ3_h.png) no-repeat; margin-left: 6px;">
											<p
												style='background: url(images/JZ3.png); width: <s:property value="myclasses[2].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ4_h.png) no-repeat;">
											<p
												style='background: url(images/JZ4.png); width: <s:property value="myclasses[3].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ5_h.png) no-repeat; margin-left: 6px;">
											<p
												style='background: url(images/JZ5.png); width: <s:property value="myclasses[4].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);">
										<li style="background: url(images/JZ6_h.png) no-repeat;">
											<p
												style='background: url(images/JZ6.png); width: <s:property value="myclasses[5].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ7_h.png) no-repeat; margin-left: 6px;">
											<p
												style='background: url(images/JZ7.png); width: <s:property value="myclasses[6].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);">
										<li style="background: url(images/JZ8_h.png) no-repeat;">
											<p
												style='background: url(images/JZ8.png); width: <s:property value="myclasses[7].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ9_h.png) no-repeat; margin-left: 6px;">
											<p
												style='background: url(images/JZ9.png); width: <s:property value="myclasses[8].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ10_h.png) no-repeat;">
											<p
												style='background: url(images/JZ10.png); width: <s:property value="myclasses[9].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ11_h.png) no-repeat; margin-left: 6px;">
											<p
												style='background: url(images/JZ11.png); width: <s:property value="myclasses[10].processForElc"/>%;'></p>
										</li> </a>
									<a href="javascript:void(0);"><li
											style="background: url(images/JZ12_h.png) no-repeat;">
											<p
												style='background: url(images/JZ12.png); width: <s:property value="myclasses[11].processForElc"/>%;'></p>
										</li> </a>
								</s:else>
							</ul>
						</div>
					</div>
					<!--头部结束-->
					<!--中部开始-->
					<table width="960" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<iframe src="${module}?intelligentLogin.loginType=<s:property value="intelligentLogin.loginType" />" id="rightFrame"
									name="rightFrame" align="middle" width="100%" height="100%"
									scrolling="no" frameborder="0"
									style="z-index: 9999; padding-bottom: 0px;"></iframe>
							</td>
						</tr>
					</table>

					<!--中部结束-->
			  </td>
			</tr>
		</table>




	</body>
</html>


