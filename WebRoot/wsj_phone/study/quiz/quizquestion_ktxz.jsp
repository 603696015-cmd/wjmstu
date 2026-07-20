<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="images/px_images/css.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery/jquery-ui-1.9.2.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
		<style type="text/css">
img,input {
	margin: 0;
	padding: 0;
	border: 0;
}

input {
	background: none;
}

.bx_answer {
	
}

.bx_answer table {
	float: left;
	cursor: pointer;
	margin: 3px 0px 0px 10px;
}

.s_answer table {
	float: left;
}
</style>
		<title>看图选择题</title>

		<script type="text/javascript">
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
function playQmusicByObject(object){
		if(object.URL!=""){
			object.controls.play();	
		}
   }
     var questype = <s:property value="question.qtype" />;
	var state = null;
	var model_state = null;
	var temp = null;
	var vedio_state = null;
	
	//答错了，播放错误提示音，播放完后，播放样音，播放完后，再次播放flash
	function playQmusic2(type,atime){
	   if(questype==15){//看图选择
		   js_stop();
	   }else if(questype==16){//看动画选择
	   		js_stop();
	   }else if(questype==17){//角色扮演
	   		js_stop();
	   }else if(questype==18){//听音选图
	   		js_stop();
	   }else if(questype==19){//拖拽
	   		js_stop();
	   }else if(questype==20){//排序
	   		js_stop();
	   }
		if(error.URL!=""){
			error.controls.play();	
		  //做一个循环去判断 第一个视频是否结束
		  state = setTimeout("checkState("+type+","+atime+")", 1000);	
		}
		
	}

   function checkState(type,atime){
   	   if(type == 1){
   	   		if(right.playState==1||right.playState==8){
			   clearTimeout(state);
			   modelVoice.controls.play();
			   model_state = setTimeout("checkModelState("+type+","+atime+")", 1000);
			   
		   }else{
			   setTimeout("checkState("+type+","+atime+")", 1000);
		   }
   	   }else if(type == 2){
   	   		if(error.playState==1||error.playState==8){
			   clearTimeout(state);
			   modelVoice.controls.play();
			   model_state = setTimeout("checkModelState("+type+","+atime+")", 1000);
		   }else{
			   setTimeout("checkState("+type+","+atime+")", 1000);
		   }
   	   }
   }
   
   function checkModelState(type,atime){
   		if(modelVoice.playState==1||modelVoice.playState==8){
		   clearTimeout(model_state);
		   if(type == 1){
		   	 	window.parent.closeFrame();
		   		window.parent.showQN();
		   }else{
		   		if(atime == 2){
		   			window.parent.closeFrame();
		   			window.parent.showQN();
		   		}else if(atime ==1){
		   			if(questype==15){//看图选择
			   			js_reset();
				   }else if(questype==16){//看动画选择
				   		js_reset();
				   }else if(questype==17){//角色扮演
				   		//提示音播放完成后再次播放flash
				   		js_reset();
				   }else if(questype==18){//听音选图
				   		js_reset();
				   }else if(questype==19){//拖拽
				   		js_reset();
				   }else if(questype==20){//排序
				   		js_reset();
				   }
		   		}
		   }
	   }else{
		   setTimeout("checkModelState("+type+","+atime+")", 1000);
	   }
   }
   
   //答对了，播放正确提示音，播放完后，播放样音
   function playQmusic(type,atime){
   		if(questype==15){//看图选择
		    js_stop();
	   }else if(questype==16){//看动画选择
	   		js_stop();
	   }else if(questype==17){//角色扮演
	   		js_stop();
	   }else if(questype==18){//听音选图
	   		js_stop();
	   }else if(questype==19){//拖拽
	   		js_stop();
	   }else if(questype==20){//排序
	   		js_stop();
	   }
		if(right.URL!=""){
			right.controls.play();	
			state = setTimeout("checkState("+type+","+atime+")", 1000);	
		}
   }
   
   function checkVedio(){
	   if(video.totalFrames == video.CurrentFrame()+1){
		   clearTimeout(vedio_state);
		   //播放结束后播放提问文件
			playQmusicByObject(fashengQuestion);
	   }else{
		   setTimeout("checkVedio()", 1000);
	   }
	}
$(function(){
		playQmusicByObject(fashengQuestion);
});
function submitktxz(){
	if(window.confirm("请检查清楚！确定提交")){
		var xx = $('#theform').formSerialize();
		$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
			url:"quizquestion_save.action",data:xx,timeout:20000,cache:false,success:function (data) {
		   	if(data=='success'){
		   		alert(data);
			}else{
				var jdata=eval("("+data+")");
				if(jdata.atime==1)
					if(jdata.status==1){//1次回答正确
						//alert("恭喜您回答正确！您"+jdata.atime+"您的得分："+jdata.myscore);
						playQmusic(1,1);
					}else{
						//继续作答
						//alert("对不起，回答错误，请继续作答");
						playQmusic2(2,1);
					}
				else if (jdata.atime==2){
					if(jdata.status==1){//1次回答正确
						//alert("恭喜您回答正确！您"+jdata.atime+"次作答的得分："+jdata.myscore);
						playQmusic(1,2);
					}else{
						//跳到下一题
						window.parent.closeFrame();
						window.parent.showQN();
					}
				}else {
					//跳到下一题
					window.parent.closeFrame();
					window.parent.showQN();
				}
			}
		},error:function(msg){
			alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
		}});
	}
}
</script>
<script type="text/javascript">
var obj = document.getElementById("pause_play");

//停止OK

function js_stop(){
	fashengQuestion.controls.stop();
	
}
//播放暂停切换OK

function js_pause_play(){
	//停止、暂停、正常
	if(fashengQuestion.playState == 1 || fashengQuestion.playState == 2 || fashengQuestion.playState == 3){
		if(fashengQuestion.playState == 2){//暂停
			fashengQuestion.controls.play();
		}else if(fashengQuestion.playState == 3){//正常
			fashengQuestion.controls.pause();
		}else if(fashengQuestion.playState == 1){//停止
			fashengQuestion.controls.play();
		}
	}
}
//从头开始播放 OK

function js_reset(){
	fashengQuestion.controls.stop();
	fashengQuestion.controls.play();
}
</script>
		<script type="text/javascript">

  var isSoundWave = false;
  var cha=null;
  function recordFunc(){
	  if(isSoundWave==false){
		  startRecord();
		  changeSoundwave(1);
		  isSoundWave=true;
	  }else{
		  endRecord();
		  isSoundWave=false;
		  clearTimeout(cha);
	  }
  }
  
  //开始录音的方法
  function startRecord(){
	  //两个参数：第一个参数是录音文件的名称，必须是pcm结尾的文件；第二个参数是上传录音的请求地址
	  //获取试题ID
	  var userid = <s:property value="#session.userId" />;
	  var exampaperid = <s:property value="myExamPaper.id" />;
	  var questionid = <s:property value="question.id" />;
	  if(userid<=0 || exampaperid<=0 || questionid<=0){
	  	alert("参数有误");
	  	return ;
	  }
	  //alert(getApplet());
	  var start = getApplet().startRecord("<s:property value="#session.userId" />_<s:property value="myExamPaper.id" />_<s:property value="question.id" />.pcm","<%=basePath%>recordUpload");
	  //alert(start);
  }
  
  //结束录音的方法
  var endrec = -1;
  function endRecord(){
	 var end = getApplet().endRecord();
	 endrec = 0;
	 alert(end);
  }

  function changeSoundwave(count){
      var wave = document.getElementById("soundwave");
      wave.src="<%=basePath%>record/img/mic_0"+count+".png";
      if(count==6){
    	 count=0;
      }
      count++;
      cha=setTimeout("changeSoundwave("+count+")", "200");
  }
  var endupload = -1;
   function uploadRecord(){
     var upload=getApplet().uploadRecord();
     alert(upload);
     endupload = 0;
  }
  
  //获取页面上传applet对象
  function getApplet(){
	  if(document.applets.length > 0){
	      return document.applets[0];  
	  }
	} 
</script>
		<style type="text/css">
.daan {
	font-weight: bold;
	color: red;
	padding-left: 20px;
}

#apDiv1 {
	position: absolute;
	left: 992px;
	top: 1174px;
	width: 117px;
	height: 57px;
	z-index: 1;
	background-color: #0000FF;
}
</style>
	</head>

	<body>
		<APPLET ID="录音功能(record)" CODE="com.msc.applet.RecognizeApplet.class"
			CODEBASE="/wjm/record" ARCHIVE="mscclient.jar" WIDTH="0" HEIGHT="0">
			<PARAM NAME="type" VALUE="application/x-java-applet;version=1.2.2">
			<PARAM NAME="scriptable" VALUE="false">
		</APPLET>
		<!-- 答对提示音 -->
		<div style="display: none;">
			<object id=right classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
				width=280 height=60
				codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
				Microsoft? Windows Media? Player components...
				type=application/x-oleobject>
				<param name=URL value="<s:property value="publicBegin" />">
				<PARAM NAME=UIMode value=full>
				<PARAM NAME=AutoStart value=false>
				<PARAM NAME=Enabled value=true>
				<PARAM NAME=enableContextMenu value=false>
			</object>
			<!-- 答错提示音 -->
			<object id=error classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
				width=280 height=60
				codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
				Microsoft? Windows Media? Player components...
				type=application/x-oleobject>
				<param name=URL value="<s:property value="publicEnd" />">
				<PARAM NAME=UIMode value=full>
				<PARAM NAME=AutoStart value=false>
				<PARAM NAME=Enabled value=true>
				<PARAM NAME=enableContextMenu value=false>
			</object>
			<!-- 正确的样音 -->
			<object id=modelVoice
				classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6 width=280
				height=60
				codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
				Microsoft? Windows Media? Player components...
				type=application/x-oleobject>
				<param name=URL value="<s:property value="question.modelVoice" />">
				<PARAM NAME=UIMode value=full>
				<PARAM NAME=AutoStart value=false>
				<PARAM NAME=Enabled value=true>
				<PARAM NAME=enableContextMenu value=false>
			</object>
			<!-- 题干发问音 -->
			<object id=fashengQuestion
				classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6 width=280
				height=60
				codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
				Microsoft? Windows Media? Player components...
				type=application/x-oleobject>
				<param name=URL
					value="<s:property value="question.fashengQuestion" />">
				<PARAM NAME=UIMode value=full>
				<PARAM NAME=AutoStart value=false>
				<PARAM NAME=Enabled value=true>
				<PARAM NAME=enableContextMenu value=false>
			</object>
		</div>


		




		<!-- 新版页面格局 -->
		<!--题干和按钮部分开始-->

		<div id="content_zhong" style="position: relative;">
			<!--上一题按钮-->
			<s:if test="view==0"><!-- 考试 -->
				<div id="last">
					<input type="image" src="images/bf/ann_37.png"
						onmousedown="this.src='images/bf/anniu_27.png'"
						onmouseup="this.src='images/bf/ann_37.png'" />
				</div>
			</s:if>
			<s:else><!-- 查看答卷 -->
				<div id="last">
					<input type="image" src="images/bf/ann_37.png"
						onmousedown="this.src='images/bf/anniu_27.png'"
						onmouseup="this.src='images/bf/ann_37.png'" onclick="window.parent.ep.showQP();"/>
				</div>
			</s:else>
			<div id="tigan">
				<!--主体上边背景-->
				<div id="m_t" style="width: 800px">
					<div style="float: left;">
						<img src="images/ktxz_images/main_lt.png" />
					</div>
					<div id="main_t"></div>
					<div>
						<img src="images/ktxz_images/main_rt.png" />
					</div>
				</div>
				<!--主体中部-->
				<div id="m_z">
					<div id="m_zl"></div>
					<div id="m_zz">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td height="173" align="center">
									<table width="100%" border="0" cellspacing="10">
										<tr>
											<td align="center">
												${question.title }
											</td>
											<!-- 
											<td align="center">
												<img src="images/ktxz_images/shij.jpg" width="249" height="200" alt="" />
											</td>
											 -->
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="center" height="150">
									<form action="#" id="theform" method="post">
										<s:hidden name="question.id" />
										<s:hidden name="question.qtype" />
										<s:hidden name="question.epblock.id" />
										<s:hidden name="question.sortid" />
										<s:hidden name="myExamPaper.id" />
										<table width="70%" border="0" cellspacing="5"
											cellpadding="0" style="color: #CCC; font-weight: bold;">
											<s:iterator value="question.options" status="status">
												<tr>
													<td align="right">
														<input type="radio"
															name="questions_<s:property value="question.epblock.id" />_0_<s:property value="question.sortid" />_stuAnswers"
															id="radio<s:property value="#status.index" />"
															value="<s:property value="#status.index" />" />
														<label for="radio"></label>
													</td>
													<td align="left"
														id="option<s:property value="#status.index" />">
														<s:property />
													</td>
												</tr>
											</s:iterator>
										</table>
									</form>
								</td>
							</tr>
						</table>
					</div>
					<div id="m_zr"></div>
				</div>
				<!--主体下部-->
				<div id="m_b" style="width: 800px;">
					<div id="m_bl">
						<img src="images/ktxz_images/main_lb.png" />
					</div>
					<div id="m_bz"></div>
					<div>
						<img src="images/ktxz_images/main_rb.png" />
					</div>
				</div>
			</div>
			<!--下一题按钮-->
			<s:if test="view==0"><!-- 考试 -->
				<div id="next">
					<input type="image" src="images/bf/anniu_32.png"
						onmousedown="this.src='images/bf/ann_39.png'"
						onmouseup="this.src='images/bf/anniu_32.png'" />
				</div>
			</s:if>
			<s:else><!-- 查看答卷 -->
				<div id="next">
					<input type="image" src="images/bf/anniu_32.png"
						onmousedown="this.src='images/bf/ann_39.png'"
						onmouseup="this.src='images/bf/anniu_32.png'" onclick="window.parent.ep.showQN();" />
				</div>
			</s:else>
			<!--播放条代码-->
			<div id="bfq">
				<div id="bfq_l">
					<img src="images/ktxz_images/bf_l.png" />
				</div>
				<div id="bfq_z">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="63" align="center">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<s:if test="view==0"><!-- 考试 -->
											<td width="74" height="79" align="center">
											</td>
											<td height="79" width="74">
												<input type="image" src="images/ktxz_images/bf/anniu_35.png"
													onmousedown="this.src='images/ktxz_images/bf/ann_35.png'"
													onmouseup="this.src='images/ktxz_images/bf/anniu_35.png'" onclick="js_stop();"/>
											</td>
											<td height="79" width="83">
												<input type="image" 
													src="images/ktxz_images/bf/anniu_36.png"
													onclick="var tmp=this.attributes['old'].value;this.attributes['old'].value=this.src;this.src=tmp;js_pause_play();"
													style='pointer: cursor; border: none'
													old='images/ktxz_images/bf/ann_36.png' 
													id="pause_play"/>
											</td>
											<td height="79" width="74">
												<input type="image" src="images/ktxz_images/bf/reset.png"
													" onmousedown="this.src='images/ktxz_images/bf/reseth.png'"
													onmouseup="this.src='images/ktxz_images/bf/reset.png'" onclick="js_reset()"/>
											</td>
											<td height="79" width="74">
												此处可放倒计时
											</td>
											<td>
												<input type="button" value="" onclick="submitktxz()"
															style="cursor: pointer; background-image: url(images/ktxz_images/bf/tj.png); width: 64px; height: 50px;" />
											</td>
											<td height="79" width="74">
												<span id="totalScore" style="color:white"><s:property value="myExamPaper.score" /></span>
											</td>
										</s:if>
										<s:else><!-- 查看答卷 -->
											<!-- 放置用户答题信息 -->
										</s:else>
									</tr>
								</table>
							</td>
						</tr>
					</table>

				</div>
				<div id="bfq_r">
					<img src="images/ktxz_images/bf_r.png" />
				</div>
			</div>
		</div>
		<!--主体播放条-->


		<!--题干和按钮部分到此结束-->
	
	</body>
</html>