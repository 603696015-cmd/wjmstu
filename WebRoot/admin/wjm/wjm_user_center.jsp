<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商务汉语学习系统董克</title>
       
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
		function iframe(){
			document.all("rightFrame").height=rightFrame.document.body.scrollHeight;
			document.all("rightFrame").width=rightFrame.document.body.scrollWidth;
		}
		
		function reinitIframe(){
			var iframe = document.getElementById("rightFrame");
			try{
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.max(bHeight, dHeight);
			iframe.height =  height;
			}catch (ex){}
		}
		window.setInterval("reinitIframe()", 200);

		
		function listCoursesByClassid(batchid,classid){
			//document.all("rightFrame").src = "wjm_user_center_index.action?elClass.id="+classid;
			window.location.href =  "wjm_user_center.action?elClass.id="+classid;;
		}
		
		function listCoursesByClassid1(batchid,classid){
			window.location.href =  "wjm_user_center.action?elClass.id="+classid;;
			//document.all("rightFrame").src ="mystudy_course_view_wjm_front.action?peixunBatch.id="+batchid+"&elClass.id="+classid;
		}
		
		
		function listPagesByCourseid(courseid,classid){
			document.all("rightFrame").src = "wjm_user_center_index.action?course.id="+courseid+"&elClass.id="+classid;
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
		</script>
        

	</head>

	<body>
<!--<div class="main">
	<div  class="main_1" style="display:none;">-->
		<div id="all">
			<!--头部开始-->
			<div id="bluebg_top">
				<div id="head" class="pngfix">
					<div id="schedule">
						<s:if test="peixunBatch.process>1">
							<table width="<s:property value="peixunBatch.process" />%" border="0" align="left" cellpadding="0"
								cellspacing="0" height="44">
								<tr>
									<td width="100%">
										<p></p>
									</td>
									<td style="text-align: left; vertical-align:center;">
										<a href="javascript:listClasses();"><img src="images/go.png" style="margin-left:-5px;"/></a>
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
										<a href="javascript:listClasses();"><img src="images/go.png" style="margin-left:-5px;"/></a>
									</td>
								</tr>
							</table>
						</s:else>
					</div>
					<div style="overflow: auto; vertical-align:bottom;">
						<a href="wjm_user_center_navigation.action"><div id="fanhui">
                                </div>
						</a>
						<div id="zhang_w" >
							<div id="zhang">
								<ul style="margin-left: 7px;">
									<s:if test="!inDingjiRoom">
										<a href="javascript:void(0);"><li>
											<p style="font-size: 30px; width: 0%;">
												<span>1A</span>
											</p>
										</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>1B</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>2A</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>2B</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>3A</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>3B</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>4A</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>4B</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>5A</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>5B</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>6A</span>
												</p>
											</li> </a>
										<a href="javascript:void(0);"><li>
												<p style="font-size: 30px; width: 0%;">
													<span>6B</span>
												</p>
											</li> </a>
									</s:if>
									<s:else>
										<s:if test="myclasses.size()==0">
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>1A</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>1B</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>2A</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>2B</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>3A</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>3B</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>4A</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>4B</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>5A</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>5B</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>6A</span>
													</p>
												</li> </a>
											<a href="javascript:void(0);"><li>
													<p style="font-size: 30px; width: 0%;">
														<span>6B</span>
													</p>
												</li> </a>
										</s:if>
										<s:else>
											<%--<a
												href="<s:if test="myclasses[0].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[0].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[0].processForElc"/>%;'>
														<span>1A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[1].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[1].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[1].processForElc"/>%;'>
														<span>1B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[2].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[2].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[2].processForElc"/>%;'>
														<span>2A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[3].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[3].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[3].processForElc"/>%;'>
														<span>2B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[4].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[4].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[4].processForElc"/>%;'>
														<span>3A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[5].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[5].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[5].processForElc"/>%;'>
														<span>3B</span>
													</p>
												</li> </a>
											<a href="<s:if test="myclasses[6].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[6].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
<!--											<a href="<s:if test="myclasses[6].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[6].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>-->
													<p
														style='font-size: 30px; width: <s:property value="myclasses[6].processForElc"/>%;'>
														<span>4A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[7].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[7].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[7].processForElc"/>%;'>
														<span>4B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[8].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[8].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[8].processForElc"/>%;'>
														<span>5A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[9].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[9].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[9].processForElc"/>%;'>
														<span>5B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[10].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[10].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[10].processForElc"/>%;'>
														<span>6A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[11].canLearn">javascript:listCoursesByClassid(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[11].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[11].processForElc"/>%;'>
														<span>6B</span>
													</p>
												</li> </a>--%>
												
										<!-- 20141011 -->		
											<a
												href="<s:if test="myclasses[0].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[0].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[0].processForElc"/>%;'>
														<span>1A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[1].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[1].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[1].processForElc"/>%;'>
														<span>1B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[2].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[2].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[2].processForElc"/>%;'>
														<span>2A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[3].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[3].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[3].processForElc"/>%;'>
														<span>2B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[4].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[4].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[4].processForElc"/>%;'>
														<span>3A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[5].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[5].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[5].processForElc"/>%;'>
														<span>3B</span>
													</p>
												</li> </a>
											<a href="<s:if test="myclasses[6].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[6].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
<!--											<a href="<s:if test="myclasses[6].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[6].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>-->
													<p
														style='font-size: 30px; width: <s:property value="myclasses[6].processForElc"/>%;'>
														<span>4A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[7].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[7].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[7].processForElc"/>%;'>
														<span>4B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[8].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[8].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[8].processForElc"/>%;'>
														<span>5A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[9].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[9].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[9].processForElc"/>%;'>
														<span>5B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[10].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[10].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[10].processForElc"/>%;'>
														<span>6A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[11].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[11].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[11].processForElc"/>%;'>
														<span>6B</span>
													</p>
												</li> </a>
												
										<%--a
												href="javascript:alert('您不需要学习这个等级');"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[0].processForElc"/>%;'>
														<span>1A</span>
													</p>
												</li> </a>
											<a
												href="javascript:alert('您不需要学习这个等级');"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[1].processForElc"/>%;'>
														<span>1B</span>
													</p>
												</li> </a>
											<a
												href="javascript:alert('您不需要学习这个等级');"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[2].processForElc"/>%;'>
														<span>2A</span>
													</p>
												</li> </a>
											<a
												href="javascript:alert('您不需要学习这个等级');"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[3].processForElc"/>%;'>
														<span>2B</span>
													</p>
												</li> </a>
											<a
												href="javascript:alert('您不需要学习这个等级');"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[4].processForElc"/>%;'>
														<span>3A</span>
													</p>
												</li> </a>
											<a
												href="javascript:alert('您不需要学习这个等级');"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[5].processForElc"/>%;'>
														<span>3B</span>
													</p>
												</li> </a>
											<a href="<s:if test="myclasses[6].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[6].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
<!--											<a href="<s:if test="myclasses[6].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[6].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>-->
													<p
														style='font-size: 30px; width: <s:property value="myclasses[6].processForElc"/>%;'>
														<span>4A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[7].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[7].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[7].processForElc"/>%;'>
														<span>4B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[8].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[8].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[8].processForElc"/>%;'>
														<span>5A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[9].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[9].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[9].processForElc"/>%;'>
														<span>5B</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[10].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[10].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value="myclasses[10].processForElc"/>%;'>
														<span>6A</span>
													</p>
												</li> </a>
											<a
												href="<s:if test="myclasses[11].canLearn">javascript:listCoursesByClassid1(<s:property value="peixunBatch.id"/>,<s:property value='myclasses[11].elClass.id' />)</s:if><s:else>javascript:void(0);</s:else>"><li>
													<p
														style='font-size: 30px; width: <s:property value ="myclasses[11].processForElc"/>%;'>
														<span>6B</span>
													</p>
												</li> </a>--%>
										</s:else>
									</s:else>
									

								</ul>

							</div>
						</div>
						<a href="#"><div
								style=" width: 80px; height: 80px; float: left; margin-left: 20px; display: inline;"></div>
						</a>
					</div>
				</div>
			</div>
			<!--单元代码开始-->
			<div id="danyuan">
				<div id="danyuan_cent">
					<p style="float: left; width: 90px; height: 40px; text-align: center; font-weight: bold; font-size: 36px; color:yellow; margin-left:60px;">
						<s:property value="elClass.name" />
					</p>
					<ul>
						<s:if test="myCourses!=null && myCourses.size()>0">
							<s:iterator value="myCourses">
								<s:if test="initCompliance">
									<a
										href="javascript:listPagesByCourseid(<s:property value="course.id" />,<s:property value="elClass.id" />);">
										<li class="pngfix">
											<p class="pngfix"
												style='width: 100%'>
												<span><s:property value="course.name" />
												</span>
											</p>
										</li> </a>
								</s:if>
								<s:else>
									<s:if test="canLearn==1">
										<a
											href="javascript:listPagesByCourseid(<s:property value="course.id" />,<s:property value="elClass.id" />);">
											<li class="pngfix">
												<p class="pngfix"
													style='width: <s:property value = "process"/>%'>
													<span><s:property value="course.name" />
													</span>
												</p>
											</li> </a>
									</s:if>
									<s:else>
										<a href="javascript:void(0);">
											<li class="pngfix">
												<p class="pngfix"
													style='width: <s:property value = "process"/>%'>
													<span><s:property value="course.name" />
													</span>
												</p>
											</li> </a>
									</s:else>
								</s:else>
								
							</s:iterator>
						</s:if>
					</ul>
					<div style="float:left; width: 60px; height: 40px; margin-left:10px;">
						<s:if test="elClass.myClass.hasExam == 1">
							<s:if test="initCompliance">
								<!-- 
								<a target="_blank"
										href="quizpaperinit.action?initCompliance=<s:property value="initCompliance" />&classid=<s:property value="elClass.id" />&myroom.examroom.id=<s:property value="elClass.myClass.examRoom.id"/>">
										<img src="images/ggks1.gif" /> </a>
								 -->
								 <a  href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="elClass.myClass.examRoom.id"/>&examPaper.id=<s:property value="elClass.myClass.examRoom.examPaper.id" />&elclass.id=<s:property value="elClass.id" />&course.id=0&coursePage.id=0"> <img src="images/ggks.png"  /> </a>
							</s:if>
							<s:else>
								<s:if
									test="elClass.myClass.process==100 && elClass.myClass.canExam">
									<!-- 进度为100且智能辅导分达标 -->
									<a href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="elClass.myClass.examRoom.id"/>&examPaper.id=<s:property value="elClass.myClass.examRoom.examPaper.id" />&elclass.id=<s:property value="elClass.id" />&course.id=0&coursePage.id=0">
										<img src="images/ggks.png" /> </a>
									<!-- 
									<a target="_blank"
										href="quizpaperinit.action?initCompliance=<s:property value="initCompliance" />&classid=<s:property value="elClass.id" />&myroom.examroom.id=<s:property value="elClass.myClass.examRoom.id"/>">
										<img src="images/ggks1.gif" /> </a>
									 -->
								</s:if>
								<s:else>
									<img src="images/ggks1_h.png" />
								</s:else>
							</s:else>
						</s:if>
                        </div>
                         <!--智能辅导分等四个按钮-->
                        <div id="four_anniu">
                            <div style="text-align:left;margin-left:10px;">
                                <a href="javascript:closeIndex();"><img src="images/cl_close.png" /></a>
                            </div>
                            <div >
                                <a href="javascript:showWord();">
                                    <img src="images/cidian.png"
                                    style="display: block;" class="pngfix" />
                                </a>
                            </div>
                            <div>
                                <a href="javascript:showI();">
                                    <img src="images/points.png" 
                                    style="display: block;" />
                                </a>
                            </div>
                            <div>
                                <a href="javascript:backToNavigation();">
                                    <img src="images/fanhui.png" 
                                    style="display: block;" />
                                </a>
                            </div>
					</div>
				</div>
			</div>
			<!-- 头部结束 -->
		</div>
			<!--中部开始-->
			<table width="990" border="0" align="center" cellpadding="0"
				cellspacing="0" id="frameTable"  >
				<tr>
				  <td align="center" >
				  	<!-- 
				  	<iframe src="${module}" id="rightFrame"
							name="rightFrame" align="middle"  height="100%"
							scrolling="no" frameborder="0"
							style="z-index: 9999; padding-bottom: 0px;width:1040px;"
							onload="javascript:SetWinHeight(this);"> 
					</iframe>
					 -->
					 <iframe src="${module}" id="rightFrame"
							name="rightFrame" align="middle"  height="100%"
							scrolling="no" frameborder="0"
							style="z-index: 9999; padding-bottom: 0px;width:1040px;"
							onload="this.height=100"> 
					</iframe>
				  </td>
				</tr>
		   </table>
			<!--中部结束-->
<!--        </div>    
</div>-->
	</body>
</html>


