<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>定级学习系统</title>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<link rel="stylesheet" href="css/wjm.css" />
		<script type="text/javascript">
		function iframe(){
			document.all("rightFrame").height=rightFrame.document.body.scrollHeight;
			document.all("rightFrame").width=rightFrame.document.body.scrollWidth;
		}
		
		function SetWinHeight(obj){ 
			var frame = document.getElementById("rightFrame");
			var src = frame.contentWindow.location.href;
			src = src.substring(src.lastIndexOf("/")+1,src.lastIndexOf(".action")+7);
			var win=obj;
			if(src != "wjm_user_center_index_new.action"){
				if (document.getElementById){ 
						if (win && !window.opera) { 
								if (win.contentDocument && win.contentDocument.body.offsetHeight) {
									win.height = win.contentDocument.body.offsetHeight;
								}else if(win.Document && win.Document.body.scrollHeight) {
									win.height = win.Document.body.scrollHeight; 
								}
						} 
				} 
			}else{
				win.height = 450;
			}
		}

		
		function listCoursesByClassid(batchid,classid){
			//document.all("rightFrame").src = "mystudy_course_view_wjm.action?peixunBatch.id="+batchid+"&elClass.id="+classid;
			document.all("rightFrame").src = "wjm_user_center_index_new.action?elClass.id="+classid;
		}
		function listClasses(){
			document.all("rightFrame").src = "mystudy_class_view_wjm.action";
		}
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
		</script>
		<style>
#ddd img {
	display: block;
}
</style>
		<!--解决IE 6不支持png格式的代码-->
		<!--[if IE 6]>
<script src="DD_belatedPNG_0.0.8a.js?v=b4e86b02"></script>
<script> DD_belatedPNG.fix('.pngfix'); </script>
<![endif]-->
	</head>

	<body>
		<div id="all">
			<!--头部开始-->
			<div id="bluebg_top" style="margin-bottom: 10px;">
				<div id="head" class="pngfix">
					<div id="schedule">
						<s:if test="peixunBatch.process>0">
							<table width="<s:property value="peixunBatch.process" />%" border="0" align="left" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="100%">
										<p></p>
									</td>
									<td style="text-align: left;">
										<a href="javascript:listClasses();"><img src="images/go.gif" /></a>
									</td>
								</tr>
							</table>
						</s:if>
						<s:else>
							<table width="0%" border="0" align="left" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="0%">
										<p></p>
									</td>
									<td style="text-align: left;">
										<a href="javascript:listClasses();"><img src="images/go.gif" /></a>
									</td>
								</tr>
							</table>
						</s:else>

					</div>
					<div style="overflow: auto;">
						<a href="wjm_user_center_navigation.action"><div id="fanhui"
								style="background: url(images/22.png) no-repeat center;"></div>
						</a>
						<div style="float: left; width: 766px;">
							<div id="zhang">
								<ul style="margin-left: 7px;">
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
						</div>
						<a href="#"><div
								style="background: url(images/33.png) no-repeat center; width: 80px; height: 80px; float: left; margin-left: 8px; display: inline;"></div>
						</a>
					</div>
				</div>
			</div>
			<!-- 头部结束 -->
			
			<!-- 中部开始 -->
			<div id="bluebg_bot">
				<iframe src="${module}" id="rightFrame" name="rightFrame"
					align="middle" width="100%" height="100%" scrolling="no"
					frameborder="0" style="z-index: 9999; padding-bottom: 0px;" onload="javascript:SetWinHeight(this);">
				</iframe>
			</div>
			<!--中部结束-->
		</div>

	</body>
</html>
