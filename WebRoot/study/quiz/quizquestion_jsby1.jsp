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
		<basefont face="楷体" size="13px"  /> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script src="js/jquery.alerts.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery/jquery-ui-1.9.2.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.countdown.js"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="js/listen_keydown.js"></script>
		<script type="text/javascript" src="js/tinybox.js"></script>
		<script type="text/javascript">
		function swfVideo__(url,passtime){

			return "<object id='video__' classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\""+
			" codebase=\"http://download.macromedia.com/pub/shockwave/_cabs/flash/swflash.cab#version=6,0,29,0\""+
			" style=\"width: 100%; height: 100%; text-align: center;\">"+
			"<param name=\"movie\" value=\""+url+"\">"+
			"<param name=CurrentPosition value='"+passtime+"'/>"+
			"<!----上value值填入flash的地址，你的flash在本机上就用相对地址!---->"+
			" <param name=\"quality\" value=\"high\">"+
			"<param name=\"SCALE\" value=\"exactfit\">"+
			"<param name=\"loop\" value=\"false\">"+
			"<param name=\"wmode\" value=\"transparent\">"+
			"<!---- 下src值填入和刚才一样的地址!---->"+
			"<embed swliveconnect=\"true\" name=\"video\" src=\"../images/head.swf\" width=\"100%\""+
			" height=\"100%\" quality=\"high\""+
			"ãpluginspage=\"http://www.macromedia.com/go/getflashplayer\""+
			"ãtype=\"application/x-shockwave-flash\""+
			" scale=\"exactfit\"></embed> </object>";
		}
		</script>
		<title>角色扮演</title>
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet" type="text/css" media="screen" />
				
<style type="text/css">
.zxx_list_title{background:#eeeeee; border:1px solid #cccccc; padding:1em;}
.zxx_list_content{padding:1em;}
#tinybox{position:absolute; display:none; padding:10px; background:#ffffff url(image/preload.gif) no-repeat 50% 50%; border:10px solid #e3e3e3; z-index:2000;}
#tinymask{position:absolute; display:none; top:0; left:0; height:100%; width:100%; background:#000000; z-index:1500;}
#tinycontent{background:#ffffff; font-size:1.1em;}
</style>
		<style type="text/css">
<!--
.cntSeparator {
        font-size: 20px;
        margin: 10px 7px;
        color: #000;
      }
body {
	background-color: #033B5E;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(images/images1113/datiye.png);
	background-position: center top;
	background-repeat: repeat-y;
}

.STYLE1 {
	color: #FFFFFF;
	font-size: 16px;
	font-weight: bold;
}
#stem_text {
	position:absolute;
	top:50%;
	left:50%;
	margin:-150px 0 0 -200px;
	width:400px;
	height:300px;
	font-family: "楷体", Arial;
	font-size: 15px;
	border: solid 5px #999;
	color: #000;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	color: yellow;
	background: #005294;
	border-color: #113F66;
}

#stem_text div{
	padding:0 15px 0 15px;
}

#stem_Voc {
	position:absolute;
	top:50%;
	left:50%;
	margin:-150px 0 0 -200px;
	width:400px;
	height:300px;
	background-color: #e7e8ed;
}
#view_Voc {
	position:absolute;
	top:50%;
	left:50%;
	margin:-150px 0 0 -200px;
	width:400px;
	height:300px;
	background-color: #e7e8ed;
}
-->
</style>
		<script type="text/javascript">
		//表示是那种考试
		var examType = <s:property value="examType" />;
		var question_block_sortid=<s:property value="question.epblock.sortid" />;
		function getDingjiName(question_block_sortid){
			if(question_block_sortid==1){
				return "1A";
			}else if(question_block_sortid==2){
				return "2A";
			}else if(question_block_sortid==3){
				return "3A";
			}else if(question_block_sortid==4){
				return "4A";
			}else if(question_block_sortid==5){
				return "5A";
			}else if(question_block_sortid==6){
				return "6A";
			}
		}
		function getElUserClassificationByUserid(roomid){
			var time = 0;
			$.ajax({
			  type: 'POST',
			  url: "getElUserClassificationByUserid.action",
			  data: {'roomid':roomid},
			  async:false,//同步
			  success: function(data){
		  			time = eval("("+data+")");
			  }
			});
			return time;
		}
		function load(){
			//blockUser("请稍候...");
			//setTimeout("listenState()", 1000);
		
			if("${elmessage}"!=""){
				var roomid = <s:property value="myExamPaper.examRoom.id" />;
				var view = <s:property value="view" />;
				if(view == 1){
					var time =  getElUserClassificationByUserid(roomid);
					if(time == 1){
						$.alerts.dialogClass = "style_1"; // set custom style class
						jConfirm("恭喜您，您当前的等级是"+"${elmessage}"+",您需要重新参加定级考试吗?,Congratulations, your got "+" ${elmessage}"+", try again?", 'Information', function(r) {
							$.alerts.dialogClass = null; // reset to default
							if(r){
								if(roomid!=0){
									//第二次定级
									//window.parent.opener.haha();
									window.parent.haha();
								}
							}else{
								if(roomid!=0){
									//addOrUpdateElUserClassificationByUserid(roomid,"${elmessage}",time);
									assign_batch(roomid,"${elmessage}");
									//window.parent.closeFrame();
									//window.parent.opener.refresh1();
									window.parent.location.href="wjm_user_center.action";
								}
							}
						});
						/**
						if(window.confirm("恭喜您，您当前的等级是"+"${elmessage}"+",您需要重新参加定级考试吗?,Congratulations, your got "+" ${elmessage}"+", try again?")){
							if(roomid!=0){
								//第二次定级
								window.parent.opener.haha();
							}
						}else{
							if(roomid!=0){
								//addOrUpdateElUserClassificationByUserid(roomid,"${elmessage}",time);
								assign_batch(roomid,"${elmessage}");
								window.parent.closeFrame();
								window.parent.opener.refresh1();
							}
						}
						*/
					}else if(time == 2){
						/**
						jjAlert("恭喜您，您当前的等级是"+"${elmessage}"+"Congratulations, your got "+"${elmessage}");
						if(roomid!=0){
							//addOrUpdateElUserClassificationByUserid(roomid,"${elmessage}",time);
							assign_batch(roomid,"${elmessage}");
							window.parent.closeFrame();
							window.parent.opener.refresh1();
						}
						*/
						$.alerts.dialogClass = "style_1"; // set custom style class
						jAlert("恭喜您，您当前的等级是"+"${elmessage}"+"Congratulations, your got "+"${elmessage}",'Information',function(){
							$.alerts.dialogClass = null; // reset to default
							if(roomid!=0){
								assign_batch(roomid,"${elmessage}");
								/**
								window.parent.closeFrame();
								window.parent.opener.refresh1();
								*/
								window.parent.location.href="wjm_user_center.action";
							}
						});
					}
				}
			}
		}
		var init = 1;
		function listenState(){
			if(document.readyState == "complete"){
				$.unblockUI();
				init = 1;//表示页面已经加载完成，可以开始播放视频了
			}else{
				setTimeout("listenState()", 1000);
			}  
			
		}
			
			function assign_batch(roomid,classification_name){
				//获取培训批次
				//将培训批次分配给用户
				//将3A及3A以前的培训班进度改为100%
				$.ajax({
				  type: 'POST',
				  url: "assign_batch.action",
				  data: {'roomid':roomid,'classification.name':classification_name},
				  async:false,//同步
				  success: function(data){
			  		
				  }
				});
			}
			function addOrUpdateElUserClassificationByUserid(roomid,classification_name,time){
				$.ajax({
				  type: 'POST',
				  url: "addOrUpdateElUserClassificationByUserid.action",
				  data: {'roomid':roomid,'classification.name':classification_name,"time":time},
				  async:false,//同步
				  success: function(data){
			  		
				  }
				});
			}
		
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

    var questype = <s:property value="question.qtype" />;
	var state = null;
	var vedio_state = null;
	var vedio_state2 = null;
	var r_o_w_state = null;
	
	
   
   //判断第一个视频是否已经播放完成,完成操作
   function checkFirstVideoIsPlayOver(type,atime,wrongCount){
   		 if(video.totalFrames == video.CurrentFrame()+1){
   		 	clearTimeout(vedio_state);
   		 	if(type == 0 && atime == 0){//初次加载，依次播放两段视频，播放完成后，加载倒计时
   		 		_cvideo = new CourseVideo(1,"<s:property value="question.frontHalfMediaFile" />", 60*60);
				_cvideo.show("swfcontent");
				vedio_state2 = null;
				vedio_state2 = setTimeout("checkSecondVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
		    }else if(type == 1 && atime == 1){//第一次答对，系统播放答对提示音+完整对话文件
		    	if(examType != 1){
		    		window.parent.closeFrame();
		   			window.parent.showQN();
		    	}	
		    }else if(type == 1 && atime == 2){//第二次答对，系统播放答对提示音+完整对话文件，自动进入下一题
		   		//window.parent.closeFrame();
		   		//window.parent.showQN();
		    }else if(type == 2 && atime == 1){//第一次答错，系统播放答错提示音1，两个视频都需要重新播放
		    	if(examType == 1){
		    		_cvideo = new CourseVideo(1,"<s:property value="question.frontHalfMediaFile" />", 60*60);
					_cvideo.show("swfcontent");
					vedio_state2 = null;
					vedio_state2 = setTimeout("checkSecondVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
		    	}else{
		    		var roomid = <s:property value="myExamPaper.examRoom.id" />;
			   		if(roomid == 1469){
		   				if(wrongCount >=4){
			   				if(question_block_sortid<=6){
								var name = getDingjiName(question_block_sortid);
								var time =  getElUserClassificationByUserid(roomid);
								if(time == 1){
									$.alerts.dialogClass = "style_1"; // set custom style class
									jConfirm("恭喜您，您当前的等级是"+name+",您需要重新参加定级考试吗?,Congratulations, your got "+name+", try again?", 'Information', function(r) {
										$.alerts.dialogClass = null; // reset to default
										if(r){
											if(roomid!=0){
												//addOrUpdateElUserClassificationByUserid(roomid,name);
												//第二次定级
												window.parent.closeFrameSimple();
												window.parent.opener.haha();
											}
										}else{
											if(roomid!=0){
												addOrUpdateElUserClassificationByUserid(roomid,name,time);
												assign_batch(roomid,name);
												window.parent.closeFrameSimple();
												window.parent.opener.refresh2();
											}
										}
									});
									/**
									if(window.confirm("恭喜您，您当前的等级是"+name+",您需要重新参加定级考试吗?,Congratulations, your got "+name+", try again?")){
										if(roomid!=0){
											//addOrUpdateElUserClassificationByUserid(roomid,name);
											//第二次定级
											window.parent.closeFrameSimple();
											window.parent.opener.haha();
										}
									}else{
										if(roomid!=0){
											addOrUpdateElUserClassificationByUserid(roomid,name,time);
											assign_batch(roomid,name);
											window.parent.closeFrameSimple();
											window.parent.opener.refresh2();
										}
									}
									*/
								}else if(time == 2){
									jjAlert("恭喜您，您当前的等级是"+name+",Congratulations, your got "+name);
									//alert("恭喜您，您当前的等级是"+name+",Congratulations, your got "+name);
									if(roomid!=0){
										addOrUpdateElUserClassificationByUserid(roomid,name,time);
										assign_batch(roomid,name);
										window.parent.closeFrameSimple();
										window.parent.opener.refresh2();
									}
								}
			   				}
			   			}else{
			   				window.parent.closeFrame();
			   				window.parent.showQN();
			   			}
		   			}else{
		   				window.parent.closeFrame();
		   				window.parent.showQN();
		   			}
		    	}
		    }else if(type == 2 && atime == 2){//第二次答错，系统播入答错提示音2+完整对话文件，自动进入下一题
		    	var roomid = <s:property value="myExamPaper.examRoom.id" />;
		   		if(atime == 2){
		   			if(roomid == 1469){
		   				if(wrongCount >=4){
			   				if(question_block_sortid<=6){
								var name = getDingjiName(question_block_sortid);
								var time =  getElUserClassificationByUserid(roomid);
								if(time == 1){
									$.alerts.dialogClass = "style_1"; // set custom style class
									jConfirm("恭喜您，您当前的等级是"+name+",您需要重新参加定级考试吗?,Congratulations, your got "+name+", try again?", 'Information', function(r) {
										$.alerts.dialogClass = null; // reset to default
										if(r){
											if(roomid!=0){
												//addOrUpdateElUserClassificationByUserid(roomid,name);
												//第二次定级
												/**
												window.parent.closeFrameSimple();
												window.parent.opener.haha();
												*/
												window.parent.haha();
											}
										}else{
											if(roomid!=0){
												addOrUpdateElUserClassificationByUserid(roomid,name,time);
												assign_batch(roomid,name);
												window.parent.location.href="wjm_user_center.action";
												/**
												window.parent.closeFrameSimple();
												window.parent.opener.refresh2();
												*/
											}
										}
									});
									/**
									if(window.confirm("恭喜您，您当前的等级是"+name+",您需要重新参加定级考试吗?,Congratulations, your got "+name+", try again?")){
										if(roomid!=0){
											//addOrUpdateElUserClassificationByUserid(roomid,name);
											//第二次定级
											window.parent.closeFrameSimple();
											window.parent.opener.haha();
										}
									}else{
										if(roomid!=0){
											addOrUpdateElUserClassificationByUserid(roomid,name,time);
											assign_batch(roomid,name);
											window.parent.closeFrameSimple();
											window.parent.opener.refresh2();
										}
									}
									*/
								}else if(time == 2){
									$.alerts.dialogClass = "style_1"; // set custom style class
									jAlert("恭喜您，您当前的等级是"+name+",Congratulations, your got "+name,'Information',function(){
										$.alerts.dialogClass = null; // reset to default
										if(roomid!=0){
											addOrUpdateElUserClassificationByUserid(roomid,name,time);
											assign_batch(roomid,name);
											/**
											window.parent.closeFrameSimple();
											window.parent.opener.refresh2();
											*/
											window.parent.location.href="wjm_user_center.action";
										}
									});
								}
			   				}
			   			}else{
			   				//window.parent.closeFrame();
			   				//window.parent.showQN();
			   			}
		   			}else{
		   				//window.parent.closeFrame();
		   				//window.parent.showQN();
		   			}
		   		}else if(atime ==1){
		   			js_reset();
		   		}
		    }
   		 }else {
   		 	setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")", 1000);
   		 }
   }
   //判断第二个视频是否已经播放完成,完成操作
   function checkSecondVideoIsPlayOver(type,atime,wrongCount){
   		var view = <s:property value="view" />;
   		if(video.totalFrames == video.CurrentFrame()+1){
   			clearTimeout(vedio_state2);
   			if(type == 0 && atime == 0){//初次加载，依次播放两段视频，播放完成后，加载倒计时，显示题干文本
   				if(fashengQuestion.url!=""){
   					fashengQuestion.controls.play();
   				}
   				state = checkMusicIsPlayOver(type,atime,wrongCount);
		    }else if(type == 1 && atime == 1){//第一次答对，系统播放答对提示音+完整对话文件
		    	
		    }else if(type == 1 && atime == 2){//第二次答对，系统播放答对提示音+完整对话文件，自动进入下一题
		   		
		    }else if(type == 2 && atime == 1){//第一次答错，系统播放答错提示音1，两个视频都需要重新播放
		    	if(view == 0){
		    		//设置录音按钮gif
	    			//document.getElementById("soundwave").src = "record/img/mic_01.gif";
	    			/**
			    	if(document.getElementById("counter").innerHTML=="")
						loadCountdown('counter',returnToHHSSString(<s:property value="question.epblock.answerTime" />));
					*/
		    	}
		    }else if(type == 2 && atime == 2){//第二次答错，系统播入答错提示音2+完整对话文件，自动进入下一题
		    	
		    }
   		}else{
   			setTimeout("checkSecondVideoIsPlayOver("+type+","+atime+","+wrongCount+")", 1000);
   		}
   }
   //判断音频是否播放完成，完成操作
   function checkMusicIsPlayOver(type,atime,wrongCount){
   		if(type == 0 && atime == 0){//初始化
   			if(fashengQuestion.url!=""){
   				if(fashengQuestion.playState==1||fashengQuestion.playState==8){
   					clearTimeout(state);
		   			playOver = 0;
	   				if(<s:property value="view" />==0){
	   					//设置录音按钮gif
    					//document.getElementById("soundwave").src = "record/img/mic_01.gif";
	   					$("#ques_stemText").html(document.getElementById("st").innerHTML);
	   					/**
	   					if(document.getElementById("counter").innerHTML=="")
							loadCountdown('counter',returnToHHSSString(<s:property value="question.epblock.answerTime" />));
						*/
	   				}
		   		}else{
		   			setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
		   		}
   			}else{
   				clearTimeout(state);
	   			playOver = 0;
   				if(<s:property value="view" />==0){
   					//设置录音按钮gif
    				//document.getElementById("soundwave").src = "record/img/mic_01.gif";
   					$("#ques_stemText").html(document.getElementById("st").innerHTML);
   					/**
   					if(document.getElementById("counter").innerHTML=="")
						loadCountdown('counter',returnToHHSSString(<s:property value="question.epblock.answerTime" />));
					*/
   				}
   			}
   		}else if(type == 1 && atime == 1){//第一次答对，系统播放答对提示音+完整对话文件
	   		if(right.playState==1||right.playState==8){
	   			clearTimeout(state);
	   			if(examType == 1){
	   				_cvideo = new CourseVideo(1,"<s:property value="question.mediaFile" />", 60*60);
					_cvideo.show("swfcontent");
					vedio_state = null;
					vedio_state = setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	   			}else{
	   				_cvideo = new CourseVideo(1,"<s:property value="question.mediaFile" />", 60*60);
					_cvideo.show("swfcontent");
					vedio_state = null;
					vedio_state = setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	   				//window.parent.closeFrame();
		   			//window.parent.showQN();
	   			}
	   		}else{
	   			setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	   		}
	    }else if(type == 1 && atime == 2){//第二次答对，系统播放答对提示音+完整对话文件，自动进入下一题
	   		if(right.playState==1||right.playState==8){
	   			clearTimeout(state);
	   			_cvideo = new CourseVideo(1,"<s:property value="question.mediaFile" />", 60*60);
				_cvideo.show("swfcontent");
				vedio_state = null;
				vedio_state = setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	   		}else{
	   			setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	   		}
	    }else if(type == 2 && atime == 1){//第一次答错，系统播放答错提示音1，两个视频都需要重新播放
	    	if(examType == 1){
				if(error1.playState==1||error1.playState==8){
		   			clearTimeout(state);
		   			_cvideo = new CourseVideo(1,"<s:property value="question.mediaFile" />", 60*60);
					_cvideo.show("swfcontent");
					vedio_state = null;
					vedio_state = setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
		   		}else{
		   			setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
		   		}
			}else{
				if(error2.playState==1||error2.playState==8){
		   			clearTimeout(state);
		   			_cvideo = new CourseVideo(1,"<s:property value="question.mediaFile" />", 60*60);
					_cvideo.show("swfcontent");
					vedio_state = null;
					vedio_state = setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
		   			//window.parent.closeFrame();
			   		//window.parent.showQN();
		   		}else{
		   			setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
		   		}
			}
	    }else if(type == 2 && atime == 2){//第二次答错，系统播入答错提示音2+完整对话文件，自动进入下一题
	    	if(error2.playState==1||error2.playState==8){
	    		clearTimeout(state);
	   			_cvideo = new CourseVideo(1,"<s:property value="question.mediaFile" />", 60*60);
				_cvideo.show("swfcontent");
				vedio_state = null;
				vedio_state = setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	   		}else{
	   			setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	   		}
	    }
   }
   
   //判断答对或者答错swf是否播放结束
   function checkROWState(type,atime,wrongCount){
   		if(video__.totalFrames == video__.CurrentFrame()+1){
		   clearTimeout(r_o_w_state);
		   if(type == 1){
		   		if(atime == 1){
					//播放答对提示音
					if(right.URL!=""){
						right.controls.play();	
						state = setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")", 1000);	
					}
				}else {
					//播放答对提示音
					if(right.URL!=""){
						right.controls.play();	
						state = setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")", 1000);	
					}
				}
		   }else {
		   	   if(atime == 1){
				     //播放答对提示音
			   	    if(examType == 1){
						if(error1.URL!=""){
							error1.controls.play();	
							state = setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")", 1000);	
						}
					}else{
						if(error2.URL!=""){
							error2.controls.play();	
							state = setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")", 1000);	
						}
					}
			   }else if(atime == 2){
			   	    //播放答对提示音
					if(error2.URL!=""){
						error2.controls.play();	
						state = setTimeout("checkMusicIsPlayOver("+type+","+atime+","+wrongCount+")", 1000);	
					}
			   }
		   }
	   }else{
		   setTimeout("checkROWState("+type+","+atime+","+wrongCount+")", 1000);
	   }
   }
   
   function checkVideo(type,atime,wrongCount){
   		if(type == 0 && atime == 0){//初次加载，依次播放两段视频，播放完成后，加载倒计时
   			_cvideo = new CourseVideo(1,"<s:property value="question.mediaFile" />", 60*60);
			_cvideo.show("swfcontent");
			vedio_state = null;
			vedio_state = setTimeout("checkFirstVideoIsPlayOver("+type+","+atime+","+wrongCount+")",1000);
	    }else if(type == 1 && atime == 1){//第一次答对，系统播放答对提示音+完整对话文件
	   		//暂停正在播放的音频或者视频
	   	    js_stop();
	   	    addRightOrWrongImg("r_o_w_img",1);
	   	    r_o_w_state = setTimeout("checkROWState("+type+","+atime+","+wrongCount+")", 1000);
	    }else if(type == 1 && atime == 2){//第二次答对，系统播放答对提示音+完整对话文件，自动进入下一题
	   		//暂停正在播放的音频或者视频
	   	    js_stop();
	   	    addRightOrWrongImg("r_o_w_img",1);
	   	    r_o_w_state = setTimeout("checkROWState("+type+","+atime+","+wrongCount+")", 1000);
	    }else if(type == 2 && atime == 1){//第一次答错，系统播放答错提示音1，两个视频都需要重新播放
	   		//暂停正在播放的音频或者视频
	   	    js_stop();
	   	    addRightOrWrongImg("r_o_w_img",0);
	   	    r_o_w_state = setTimeout("checkROWState("+type+","+atime+","+wrongCount+")", 1000);
	    }else if(type == 2 && atime == 2){//第二次答错，系统播入答错提示音2+完整对话文件，自动进入下一题
	   		//暂停正在播放的音频或者视频
	   	    js_stop();
	   	    addRightOrWrongImg("r_o_w_img",0);
	   	    r_o_w_state = setTimeout("checkROWState("+type+","+atime+","+wrongCount+")", 1000);
	    }
	}
	
	//加载倒计时
	var endtime = -1 ; //标识倒计时结束
	function loadCountdown(element_id,starttime){
		jQuery(function(){
			if(<s:property value="view" />==0){
				jQuery('#'+element_id).countdown({
				  image:'images/digits.png',
				  //获取大题中每道题的答题时间
		          startTime:starttime,
		          timerEnd:function(){ //倒计时结束时，停止播放
		          		endtime = 0;
		          		submitjsby();//强制提交
		          },
		          format:'mm:ss'
				});
			}
		});
	}
	function returnToHHSSString(minute){
		if(minute>0){
			if(minute>=10){
				return minute + ":00";
			}else{
				return "0" + minute + ":00";
			}
		}else{
			return "00:00";
		}
	}
	var init_statu = null;
	function checkInit(){
		if(init == 1){
			clearTimeout(init_statu);
		   
		   //执行初始化操作
		   var _cvideo = null;	
		   vedio_state = checkVideo(0,0,0); 
		}else{
			setTimeout("checkInit()", 1000);
		}
	}
$(function(){
		$("#tj").bind("click",function(){
			submitjsby();
		});
		var left = $(document.body).width()/2+200;
		var top = $(document.body).height()/2 + 160;
		$("#youxiajiao").css({"position":"absolute","top":top,"left":left});
		init_statu = setTimeout("checkInit()",1000);
});
var ss = null;
var submitstatus = -1;
function checktimesubmit(){
   if(submitstatus == -1){
	   clearTimeout(ss);
	   var xx = $('#theform').formSerialize();
		$.ajax({
			beforeSend:function(){
				submitstatus = 0;
			},
			complete:function(){
				submitstatus = -1;
				//不是章节考试，提交一次后取出OK绑定事件
				if(examType != 1){
					$("#tj").unbind("click");
				}
			},
			type:"post",
			url:"quizquestion_save.action",data:xx,timeout:20000,cache:false,async:true,success:function (data) {
		   	if(data=='success'){
		   		alert(data);
			}else{
				//设置录音按钮静态png
    			document.getElementById("soundwave").src = "record/img/mic_t.png";
				//设置OK按钮为静态图片
    	 		document.getElementById("tj").src = "images/datigaiban/tj.png";
    	 		
    	 		$("#beforeVoiceText").html("录音识别：");
				var jdata=eval("("+data+")");
				if(jdata.voiceText!=null && jdata.voiceText!=""){
					$("#voiceText").html(jdata.voiceText);
				}
				if(jdata.atime==1)
					if(jdata.status==1){//1次回答正确
						//$("#counter").html("");
						$("#totalScore").html(jdata.totalScore);
						$("#next").attr("src","images/datigaiban/anniu_32.gif");
						$("#beforeYangyinText").html("样音文本：");
						$("#ques_yangyinText").html("${question.modelVoiceText}");
						checkVideo(1,1,jdata.wrongCount);
					}else{//1次回答错误
						//$("#counter").html("");
						$("#totalScore").html(jdata.totalScore);
						if(examType != 1){
							$("#beforeYangyinText").html("样音文本：");
							$("#ques_yangyinText").html("${question.modelVoiceText}");
						}
						checkVideo(2,1,jdata.wrongCount);
					}
				else if (jdata.atime==2){
					if(jdata.status==1){//2次回答正确
						//$("#counter").html("");
						$("#totalScore").html(jdata.totalScore);
						$("#next").attr("src","images/datigaiban/anniu_32.gif");
						$("#beforeYangyinText").html("样音文本：");
						$("#ques_yangyinText").html("${question.modelVoiceText}");
						checkVideo(1,2,jdata.wrongCount);
					}else{//2次回答错误
						//$("#counter").html("");
						$("#totalScore").html(jdata.totalScore);
						$("#next").attr("src","images/datigaiban/anniu_32.gif");
						$("#beforeYangyinText").html("样音文本：");
						$("#ques_yangyinText").html("${question.modelVoiceText}");
						checkVideo(2,2,jdata.wrongCount);	
					}
				}else {
					//跳到下一题
					//window.parent.closeFrame();
					//window.parent.showQN();
					jjAlert("答题次数已到达,answer times reached");
				}
			}
		},error:function(msg){
			jjAlert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
			//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
		}});
   }else{
	   setTimeout("checktimesubmit()", 1000);
   }
}
function submitjsby(){
	var ifview = <s:property value="view" />;
	if(ifview == 0){
		if(endtime == 0){//时间到
			uploadRecord();
			if(endupload == 0){//上传成功后endupload==0
				ss = setTimeout("checktimesubmit()", 1000);
			}	
		}else{
			if(endrec == 0 && endupload == 0){//录音结束后endrec==0 && 上传成功后endupload==0
				var xx = $('#theform').formSerialize();
				$.ajax({
					beforeSend:function(){
						submitstatus = 0;
					},
					complete:function(){
						submitstatus = -1;
						//不是章节考试，提交一次后取出OK绑定事件
						if(examType != 1){
							$("#tj").unbind("click");
						}
					},
					type:"post",
					url:"quizquestion_save.action",data:xx,timeout:20000,cache:false,async:true,success:function (data) {
				   	if(data=='success'){
				   		alert(data);
					}else{
						//设置录音按钮静态png
   						document.getElementById("soundwave").src = "record/img/mic_t.png";
						//设置OK按钮为静态图片
   	 					document.getElementById("tj").src = "images/datigaiban/tj.png";
   	 					
   	 					$("#beforeVoiceText").html("录音识别：");
						var jdata=eval("("+data+")");
						if(jdata.voiceText!=null && jdata.voiceText!=""){
							$("#voiceText").html(jdata.voiceText);
						}
						if(jdata.atime==1)
							if(jdata.status==1){//1次回答正确
								//$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								$("#next").attr("src","images/datigaiban/anniu_32.gif");
								$("#beforeYangyinText").html("样音文本：");
								$("#ques_yangyinText").html("${question.modelVoiceText}");
								checkVideo(1,1,jdata.wrongCount);
							}else{//1次回答错误
								//$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								if(examType != 1){
									$("#beforeYangyinText").html("样音文本：");
									$("#ques_yangyinText").html("${question.modelVoiceText}");
								}
								checkVideo(2,1,jdata.wrongCount);
							}
						else if (jdata.atime==2){
							if(jdata.status==1){//2次回答正确
								//$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								$("#next").attr("src","images/datigaiban/anniu_32.gif");
								$("#beforeYangyinText").html("样音文本：");
								$("#ques_yangyinText").html("${question.modelVoiceText}");
								checkVideo(1,2,jdata.wrongCount);
							}else{//2次回答错误
								//$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								$("#next").attr("src","images/datigaiban/anniu_32.gif");
								$("#beforeYangyinText").html("样音文本：");
								$("#ques_yangyinText").html("${question.modelVoiceText}");
								checkVideo(2,2,jdata.wrongCount);
							}
						}else {
							//跳到下一题
							//window.parent.closeFrame();
							//window.parent.showQN();
							jjAlert("答题次数已到达,answer times reached");
						}
					}
				},error:function(msg){
					jjAlert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
					//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
				}});
			}else{
				$.alerts.dialogClass = "style_1";
	   			jConfirm("该题还没有进行录音，您确定提交吗?question has not be recoding,Submit,Yes or No?", 'Information', function(r) {
					$.alerts.dialogClass = null; // reset to default
					if(r){
						var xx = $('#theform').formSerialize();
						$.ajax({
							beforeSend:function(){
								submitstatus = 0;
							},
							complete:function(){
								submitstatus = -1;
								//不是章节考试，提交一次后取出OK绑定事件
								if(examType != 1){
									$("#tj").unbind("click");
								}
							},
							type:"post",
							url:"quizquestion_save.action",data:xx,timeout:20000,cache:false,async:true,success:function (data) {
						   	if(data=='success'){
						   		alert(data);
							}else{
								//设置录音按钮静态png
	    						document.getElementById("soundwave").src = "record/img/mic_t.png";
								//设置OK按钮为静态图片
	    	 					document.getElementById("tj").src = "images/datigaiban/tj.png";
	    	 					
	    	 					$("#beforeVoiceText").html("录音识别：");
								var jdata=eval("("+data+")");
								if(jdata.voiceText!=null && jdata.voiceText!=""){
									$("#voiceText").html(jdata.voiceText);
								}
								if(jdata.atime==1)
									if(jdata.status==1){//1次回答正确
										//$("#counter").html("");
										$("#totalScore").html(jdata.totalScore);
										$("#next").attr("src","images/datigaiban/anniu_32.gif");
										$("#beforeYangyinText").html("样音文本：");
										$("#ques_yangyinText").html("${question.modelVoiceText}");
										checkVideo(1,1,jdata.wrongCount);
									}else{//1次回答错误
										//$("#counter").html("");
										$("#totalScore").html(jdata.totalScore);
										if(examType != 1){
											$("#beforeYangyinText").html("样音文本：");
											$("#ques_yangyinText").html("${question.modelVoiceText}");
										}
										checkVideo(2,1,jdata.wrongCount);
									}
								else if (jdata.atime==2){
									if(jdata.status==1){//2次回答正确
										//$("#counter").html("");
										$("#totalScore").html(jdata.totalScore);
										$("#next").attr("src","images/datigaiban/anniu_32.gif");
										$("#beforeYangyinText").html("样音文本：");
										$("#ques_yangyinText").html("${question.modelVoiceText}");
										checkVideo(1,2,jdata.wrongCount);
									}else{//2次回答错误
										//$("#counter").html("");
										$("#totalScore").html(jdata.totalScore);
										$("#next").attr("src","images/datigaiban/anniu_32.gif");
										$("#beforeYangyinText").html("样音文本：");
										$("#ques_yangyinText").html("${question.modelVoiceText}");
										checkVideo(2,2,jdata.wrongCount);
									}
								}else {
									//跳到下一题
									//window.parent.closeFrame();
									//window.parent.showQN();
									jjAlert("答题次数已到达,answer times reached");
								}
							}
						},error:function(msg){
							jjAlert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
							//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
						}});
					}
				});
			}	
		}
	}
	
}

var playOver = -1;
function play_fashengquestion(index){
	var flag = false;
	if(video.IsPlaying()){
		image.src = "images/bf/ann_36.png";
		video.StopPlay();
	}
	if(index == 1){
		flag = check_bofang_temp1(modelVoice,recoding);
		if(flag){
			fashengQuestion.controls.play();
		}
	}else if(index == 2){
		flag = check_bofang_temp1(fashengQuestion,recoding);
		if(flag){
			modelVoice.controls.play();
		}
	}else if(index == 3){
		flag = check_bofang_temp1(fashengQuestion,modelVoice);
		if(flag){
			recoding.controls.play();
		}
	}
	check_bofang(index);
}
var bofang_temp = null;
var bofang_temp1 = null;
var bofang_switch = 0;
function check_bofang(index){
	if(index == 1){//播放发声提问
		if(fashengQuestion.url!=""){
			if(fashengQuestion.playState==1||fashengQuestion.playState==8){
				clearTimeout(bofang_temp);
			}else{
				bofang_temp = setTimeout("check_bofang(1)", 1000);
			}
		}
	}else if(index == 2){//播放样音
		if(modelVoice.url!=""){
			if(modelVoice.playState==1||modelVoice.playState==8){
				clearTimeout(bofang_temp);
			}else{
				bofang_temp = setTimeout("check_bofang(2)", 1000);
			}
		}
	}else if(index == 3){
		if(recoding.url!=""){
			if(recoding.playState==1||recoding.playState==8){
				clearTimeout(bofang_temp);
			}else{
				bofang_temp = setTimeout("check_bofang(3)", 1000);
			}
		}
		
	}
}
function check_bofang_temp1(obj,obj1){
	var flag = false;
	if(obj.url!=""){
		if(obj.playState==1||obj.playState==8 || obj.playState==10){
			if(obj1.url!=""){
				if(obj1.playState==1||obj1.playState==8 || obj1.playState==10){
					clearTimeout(bofang_temp1);
					flag = true;
				}else{
					bofang_temp1 = setTimeout("check_bofang(obj,obj1)", 1000);
				}
				clearTimeout(bofang_temp1);
				flag = true;
			}else{
				clearTimeout(bofang_temp1);
				flag = true;
			}
		}else{
			bofang_temp1 = setTimeout("check_bofang(obj,obj1)", 1000);
		}
	}else{
		if(obj1.url!=""){
			if(obj1.playState==1||obj1.playState==8 || obj1.playState==10){
				clearTimeout(bofang_temp1);
				flag = true;
			}else{
				bofang_temp1 = setTimeout("check_bofang(obj,obj1)", 1000);
			}
			clearTimeout(bofang_temp1);
			flag = true;
		}else{
			clearTimeout(bofang_temp1);
			flag = true;
		}
	}
	
	return flag;
}
</script>
		<script type="text/javascript">
//停止OK

function js_stop(){
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
	var image = document.getElementById("pause_play");
	image.src = "images/bf/anniu_31.png";
	//video.Rewind();
	//video.play();
	if(modelVoice.url!=""){
 		modelVoice.controls.play();
 	}
	var view = <s:property value="view" />;
	if(view == 0){
		//智能辅导分之复听
		window.parent.intelligentProportion();
	}
}
</script>
		<script type="text/javascript">

  var isSoundWave = false;
  var cha=null;
  function recordFunc(){
	  if(isSoundWave==false){
	  	  if(video.IsPlaying()){
	  	  	jjAlert("动画正在播放,请等待播放完成,Please wait,video is playing");
			//alert("动画正在播放,请等待播放完成,Please wait,video is playing");
			return ;
		  }else{
		  	startRecord();
			changeSoundwave(1);
			isSoundWave=true;
		  }
	  }else{
	  	  clearTimeout(cha);
		  endRecord();
		  isSoundWave=false;
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
	  	jjAlert("参数有误,Parameter incorrect");
	  	//alert("参数有误,Parameter incorrect");
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
	 //显示喇叭按钮
	 showSpeaker();
	 //智能辅导分之录音
	 window.parent.intelligentRecoding();
	 //提示正在上传，无法进行操作
	 //blockUser("录音文件上传中请稍等......,Uploading......");
	 //录音结束后，上传到服务器
	 uploadRecord();
	 //提示正在解析，无法进行操作
	 //blockUser("语音识别解析中请稍等......,recognizing......");
	 //语音解析
	 yuyin_jiexi();
	 //设置录音按钮静态图片
     document.getElementById("soundwave").src = "record/img/mic_t.png";
     //设置OK按钮为动态图片
     //document.getElementById("tj").src = "images/datigaiban/tj.gif";
  }
function showSpeaker(){
	var sqid = <s:property value="myExamPaper.id" />;
	var questionid = <s:property value="question.id" />;
	var userid = <s:property value="#session.userId" />;
	$("#speaker").css("display","block");
	var filepath = "c:/record/"+userid+"_"+sqid+"_"+questionid+".wma";
	speak.URL = filepath;
}
function userspeak(){
	var sqid = <s:property value="myExamPaper.id" />;
	var questionid = <s:property value="question.id" />;
	var userid = <s:property value="#session.userId" />;
	var name = getApplet().getName(userid+"_"+sqid+"_"+questionid+".wma");
	if(name){
		speak.controls.play();
	}else{
		jjAlert("您尚未提交录音,no Recoding");
	}
	/**
	$.ajax({
	  type: 'POST',
	  url: "checkVoiceFileIsExist.action",
	  data: {'question.myExamPaperid':<s:property value="myExamPaper.id" />,'question.id':<s:property value="question.id" />,'question.epblock.id':<s:property value="question.epblock.id" />},
	  async:false,//同步
	  success: function(data){
	 		var hasVoiceFile = eval("("+data+")").hasVoiceFile;
	 		if(hasVoiceFile){
				speak.controls.play();
			}else{
				jjAlert("您尚未提交录音,no Recoding");
				//alert("您尚未提交录音,no Recoding");
			}
	  }
	});
	*/
}
function yuyin_jiexi(){
$.ajax({
  type: 'POST',
  url: "getVoiceText.action",
  data: {'myExamPaper.id':<s:property value="myExamPaper.id" />,'question.id':<s:property value="question.id" />,'question.epblock.id':<s:property value="question.epblock.id" />},
  timeout:20000,
  cache:false,
  async:true,
  success: function(data){
  		/**
 		var voiceText = eval("("+data+")").voiceText;
 		$("#beforeVoiceText").html("录音识别：");
 		if(voiceText!=null && voiceText!=""){
			$("#voiceText").html(voiceText);
		}
		*/
  },
  error:function(msg){
  		jjAlert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
  		//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务,error, chech your network or contact the administrator");
		//$.unblockUI();
  }
});
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
  function changeSoundwave(count){
      var wave = document.getElementById("soundwave");
      wave.src="record/img/mic_0"+count+".png";
      if(count==6){
    	 count=0;
      }
      count++;
      cha=setTimeout("changeSoundwave("+count+")", "200");
  }
  var endupload = -1;
   function uploadRecord(){
   	 var upload=getApplet().uploadRecord();
     endupload = 0;
     $.unblockUI();
  }
  
  //获取页面上传applet对象
  function getApplet(){
	  if(document.applets.length > 0){
	      return document.applets[0];  
	  }
	} 
</script>
	</head>

	<body onload="load();">
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
			<!-- 答错提示音1 -->
			<object id=error1 classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
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
			<!-- 答错提示音2 -->
			<object id=error2 classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
				width=280 height=60
				codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
				Microsoft? Windows Media? Player components...
				type=application/x-oleobject>
				<param name=URL value="<s:property value="publicEnd2" />">
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
			<object id=speak
				classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6 width=280
				height=60
				codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
				Microsoft? Windows Media? Player components...
				type=application/x-oleobject>
				<param name=URL
					value="">
				<PARAM NAME=UIMode value=full>
				<PARAM NAME=AutoStart value=false>
				<PARAM NAME=Enabled value=true>
				<PARAM NAME=enableContextMenu value=false>
			</object>
			<s:if test="view == 1">
				<!-- 用户录音 -->
				<object id=recoding
					classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6 width=280
					height=60
					codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
					Microsoft? Windows Media? Player components...
					type=application/x-oleobject>
					<param name=URL
						value="<s:property value="question.recoding" />">
					<PARAM NAME=UIMode value=full>
					<PARAM NAME=AutoStart value=false>
					<PARAM NAME=Enabled value=true>
					<PARAM NAME=enableContextMenu value=false>
				</object>
			</s:if>
		</div>

		<!-- 新版页面格局 -->
		<table width="1003" height="600" border="0" align="center"
			cellpadding="0" cellspacing="0" style="margin-top:84px;">
			<tr>
				<td height="507">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="597"  align="right" valign="top">
								<table width="560" border="0" cellpadding="0" cellspacing="0" style="margin-right:8px;">
									<tr>
										<td height="45" align="center" valign="middle">
											<table border="0" align="center" cellpadding="0" cellspacing="0">
											  <tr>
											    <td width="70" align="center"> <img src="images/datigaiban/money.png" align="middle"/></td>
											    <td>
											    	<span class="STYLE1">得分：</span>
													<span class="STYLE1" >
														<span style="color:red;font-size:30px;" id="totalScore">
															<s:property value="myExamPaper.score" />
														</span> 
													</span>
													<span class="STYLE1">分</span>
												</td>
											  </tr>
											</table>
										</td>
									</tr>
									<tr>
										<td width="535" height="460">
											<div id="swfcontent" style="height:446px;overflow:hidden"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="406" valign="top">
								<table width="93%" border="0" align="right" cellpadding="0" cellspacing="0" style="margin-right: 15px;">
                                <tr>
                                	<td height="60" align="left" valign="top">
                                    	<table border="0" cellpadding="0" cellspacing="0" style="margin-left:20px;">
                                            	<tr>
                                                	<td width="50" align="left"><img height="35" src="images/bf/help_l.png" 
														onmousedown="this.src='images/bf/help_l.png'"
														onmouseup="this.src='images/bf/help_l.png'"  
														onclick="preview('<s:property value="helpSwf" />');"/>
													</td>
                                                    <td width="50" align="left"><img height="35" src="images/datigaiban/txt_s.png" width="35" onclick="showStemText();"/></td>
                                                    <td width="50" align="left"><img height="35" src="images/datigaiban/fanhui.png"  width="35"  onclick="backup();" /></td>
                                                </tr>
                                      </table>
                                    </td>
                                </tr>
									<!-- 题干文本 -->
									<s:if test="view==0">
										<tr>
											<td  width="390" height="50" style="PADDING:15PX;">
												<span id="ques_stemText" style="color:white"></span>
											</td>
										</tr>
									</s:if>
									<s:else>
										<tr>
											<td  width="390" height="50" style="PADDING:15PX;">
												<span id="ques_stemText" style="color:white">${question.stemText }</span>
											</td>
										</tr>
									</s:else>
									<!-- 语音识别文本 -->
									<tr>
										<td  width="390" height="50"  style="display:none;PADDING:15PX; font-size:18px;">
											<span style="color:red" id="beforeVoiceText"></span>
											<span id="voiceText" style="color:yellow"></span>
										</td>
									</tr>
									<!-- 样音文本 -->
									<tr>
										<td width="390" height="50"  style="PADDING:15PX;font-size:18px;">
											<span style="color:red" id="beforeYangyinText"></span>
											<span id="ques_yangyinText" style="color:red"></span>
										</td>
									</tr>
									<tr>
										<td align="center">
											<form action="#" id="theform" method="post">
												<s:hidden name="question.id" />
												<s:hidden name="question.qtype" />
												<s:hidden name="question.epblock.id" />
												<s:hidden name="question.sortid" />
												<s:hidden name="myExamPaper.id" />
												<table width="90%" border="0" cellpadding="0"
													cellspacing="0" style="padding-left: 5px; color: #ccc;">
													<s:iterator value="question.options" status="status">
														<tr>
															<td height="50" style="color: #CCC">
																<s:property />
															</td>
														</tr>
													</s:iterator>
												</table>
											</form>
										</td>
									</tr>
									<s:if test="view == 1">
										<tr>
											<td width="390" height="186" valign="top">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													 <tr>
														 <td >
															<table width="88%" height="44" border="0" align="center"
																cellpadding="0" cellspacing="1"
																style="color: #F90; font-weight: bold; font-size: 16px;">
																<tr>
																	<td height="30" >
																		提问:<img src='images/bofang.jpg' onclick="play_fashengquestion(1);" />
																	</td>
																</tr>
																<tr>
																	<td height="30"   >
																		样音:<img src='images/bofang.jpg' onclick="play_fashengquestion(2);" />
																	</td>
																</tr>
																<s:if test="question.hasVoice == 1">
																	<tr>
																		<td height="30"   >
																			录音:<img src='images/bofang.jpg' onclick="play_fashengquestion(3);" />
																		</td>
																	</tr>
																</s:if>
																<tr>
																	<td height="30"  >
																		样音文本:<s:property value="question.modelVoiceText" />
																	</td>
																</tr>
																<!-- 
																<tr>
																	<td height="30" >
																		录音文本
																		<s:if test="question.voiceAnswer==null||question.voiceAnswer==''">
																		 	学员未录音
																		 </s:if>
																		 <s:else>
																		 	<s:property value="question.voiceAnswer" />
																		 </s:else>
																	</td>
																</tr>
																 -->
																<tr>
																	<td>
																		得分:
																		<span style="color: red; font-size: 30px;"><s:property
																				value="question.myScore" /></span>分
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</s:if>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="105" valign="top">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="margin-top: 15px;">
						<tr>
							<s:if test="view==0">
	                            <td width="200" align="center">
	                            	
	                            </td>
	                            
								<!-- 停止 -->
								<td width="65" align="center" valign="middle">
										<img src="images/tz_images/bf/anniu_35.png" width="50" height="50"
										    onmousedown="this.src='images/tz_images/bf/ann_35.png'"
											onmouseup="this.src='images/tz_images/bf/anniu_35.png'"
											onclick="js_stop()" />
							    </td>
							    
								<!-- 暂停播放切换 -->	
								<td width="65" align="center" valign="middle">
										<img src="images/bf/anniu_31.png" width="50" id="pause_play"
											height="50" onclick="js_pause_play();" />
							    </td>
							    
							    <!-- 重置 -->
								<td width="65" align="center" valign="middle">
										<img src="images/datigaiban/reset.png" width="50" height="50"
											onmousedown="this.src='images/bf/reseth.png'"
											onmouseup="this.src='images/bf/reset.png'"
											onclick="js_reset()" />
							    </td>
							    
							    <!-- 词汇 -->
								<td width="65" align="center" valign="middle">
                                
                                	<img src="images/bf/cidian.png" 
										width="50" height="50" align="middle"  
										onclick="showV();"/>
                              	</td>
								
								<td width="290" align="center" valign="middle">&nbsp;
									
								</td>
								
	                            <!-- 倒计时 -->
	                            <!-- 
								<td   align="center" valign="middle" >
									<div id="counter"></div>
								</td>
								 -->
								 
								<!-- 
								<td width="100" align="center" valign="middle" style="font-size:18px; font-weight:bold;background:url(images/datigaiban/tiliang.png) no-repeat center center;">
									<span style="color:green;"><s:property value="question.questionNumber" /></span>
									/
									<span style="color:green;"><s:property value="examPaper.questionTotalCount" /></span>
								</td>
								 --> 
								
								 
								<!-- 下一题 -->
								<s:if test="examType == 1"><!-- 章节考试 -->
									<td width="65" align="center" valign="middle">
		                                	<img id="next" 
		                                		src="images/datigaiban/anniu_32h.png" 
		                                		onmousedown="this.src='images/datigaiban/anniu_32h_h.png'"
												onmouseup="this.src='images/datigaiban/anniu_32h.png'"
		                                		width="50" height="50" onclick="next();" />
		                            </td>
								</s:if>
                                
                                <td width="100" align="center" valign="middle" style="font-size:18px; font-weight:bold;background:url(images/datigaiban/tiliang.png) no-repeat center center;">
									<span style="color:green;"><s:property value="question.sortid" /></span>
									/
									<span style="color:green;"><s:property value="question.epblock.questionamount" /></span>
								</td>
								<td width="65" align="center">&nbsp;
	                            	
	                            </td>
							</s:if>
							<s:else>
								<td width="130" align="right" >
	                            	
	                            </td>
								<!-- 上一题 -->
								<td width="65" align="center" valign="middle">
										<img src="images/datigaiban/ann_37.png" 
		                                		onmousedown="this.src='images/datigaiban/anniu_27.png'"
												onmouseup="this.src='images/datigaiban/ann_37.png'"
		                                		width="50" height="50"
											onclick="window.parent.ep.showQP();" />
								</td>
								
								
								<!-- 停止 -->
								<td width="65" align="center" valign="middle">
										<img src="images/tz_images/bf/anniu_35.png" width="50" height="50"
										    onmousedown="this.src='images/tz_images/bf/ann_35.png'"
											onmouseup="this.src='images/tz_images/bf/anniu_35.png'"
											onclick="js_stop()" />
							    </td>
								<!-- 暂停播放切换 -->	
								<td width="65" align="center" valign="middle">
										<img src="images/bf/anniu_31.png"  id="pause_play"
											width="50" height="50" onclick="js_pause_play();" />
							    </td>
							    <!-- 重置 -->
								<td width="65" align="center" valign="middle">
										<img src="images/datigaiban/reset.png" width="50" height="50"
											onmousedown="this.src='images/bf/reseth.png'"
											onmouseup="this.src='images/bf/reset.png'"
											onclick="js_reset()" />
							    </td>
							    
							    <td width="290" align="center" valign="middle">&nbsp;
									
								</td>
							    
								<!-- 下一题 -->
								<td width="65" align="center" valign="middle">
										<img src="images/datigaiban/anniu_32h.png" 
		                                		onmousedown="this.src='images/datigaiban/anniu_32h_h.png'"
												onmouseup="this.src='images/datigaiban/anniu_32h.png'"
		                                		width="50" height="50"
											 onclick="window.parent.ep.showQN();"/>
							    </td>
							    
							     <td width="100" align="center" valign="middle" style="font-size:18px; font-weight:bold;background:url(images/datigaiban/tiliang.png) no-repeat center center;">
									<span style="color:green;"><s:property value="question.sortid" /></span>
									/
									<span style="color:green;"><s:property value="question.epblock.questionamount" /></span>
								</td>
							    
							    <td width="65" align="center">&nbsp;
	                            	
	                            </td>
							</s:else>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		
		<div id="stem_text" style="display:none ">
			<div>
				<span style="margin-left: 360px; color: #000;"><a href="javascript:void(0);" onclick="showStemText();">
					<img src="images/datigaiban/wrong.png" width="15" height="15"  />
				</a></span>
				<span id="st"  >${question.stemText }</span>
			</div>
		</div>
		<div id="stem_Voc" style="display:none;overflow-x:hidden;overflow-y:auto; scrollbar-face-color: #dbebfe; scrollbar-shadow-color: #b8d6fa; scrollbar-highlight-color: #ffffff; scrollbar-3dlight-color: #dbebfe; scrollbar-darkshadow-color:#458ce4; scrollbar-track-color: #ffffff; scrollbar-arrow-color: #458ce4  ">
			<div>
				<span style="margin-left: 360px; color: #000;"><a href="javascript:void(0);" onclick="closeVoc();">
					<img src="images/datigaiban/wrong.png" width="15" height="15"  />
				</a></span>
			</div>
			<input type="hidden" value="0" id="add"/>
		</div>
		
		<div id="view_Voc" style="display:none;overflow-x:hidden;overflow-y:auto; scrollbar-face-color: #dbebfe; scrollbar-shadow-color: #b8d6fa; scrollbar-highlight-color: #ffffff; scrollbar-3dlight-color: #dbebfe; scrollbar-darkshadow-color:#458ce4; scrollbar-track-color: #ffffff; scrollbar-arrow-color: #458ce4  ">
			<div>
				<span style="margin-left: 380px; color: #000;"><a href="javascript:void(0);" onclick="closeVoc();">
					<img src="images/datigaiban/wrong.png" width="15" height="15"  />
				</a></span>
			</div>
		</div>
		
		<div id="youxiajiao">
		<s:if test="view==0">
		<table border="0"  cellpadding="0" cellspacing="0" width="280">
            <tr>
                <td width="50px" ><div id="r_o_w_img" style="width:50px;height:50px;"></div></td>
                <td width="100px">
                	<img name="soundwave" 
						id="soundwave"  
						width='120' height='44'
						src="record/img/mic_t.png"
						onclick="recordFunc();" style=" float:right;" />
        </td>
                <td width="70">
                	<img src="record/img/anniu_speaker.png" 
						onmousedown="this.src='record/img/ann_speaker.png'" 
						onmouseup="this.src='record/img/anniu_speaker.png'" 
						width='50' height='50' 
						onclick="userspeak();" 
						style="display:none;float:right;" 
						id="speaker" />
				</td>
                <td width="50">
                	<img src="images/datigaiban/tj.png" 
      						onmousedown="this.src='images/datigaiban/tj_h.png'"
							onmouseup="this.src='images/datigaiban/tj.png'"
      						id="tj" width='50' height='50' style=" float:right;margin-right:5px" />
      			</td>
             </tr>
        </table>
        </s:if>
		</div>

		<!--题干和按钮部分到此结束-->

		<script type="text/javascript">
			function showStemText(){
				if($("#stem_text").css("display")=="block"){
					$("#stem_text").css({  display:"none" });
				}else{
					$("#stem_text").css({  display:"block" });
				}
			    //$("#stem_text").fadeToggle(2000);
			}
			//下一题
			function next(){
				//判断这一题是否允许跳转到下一题
				//判断标准：得分了或者已经答题两次
				if(checkCanNext()){
					window.parent.closeFrame();
				   	window.parent.showQN();
				}else{
					jjAlert("未达到下一题的条件,请稍后再试,does not meet the conditions for the next question, please try again later");
					//alert("未达到下一题的条件,请稍后再试,does not meet the conditions for the next question, please try again later");
					return ;
				}
			}
			function checkCanNext(){
				var flag = false;
				var temp = 0;
				$.ajax({
				  type: 'POST',
				  url: "checkQuestionCanNext.action",
				  data: {'myExamPaper.id':<s:property value="myExamPaper.id" />,'question.id':<s:property value="question.id" />,'question.epblock.id':<s:property value="question.epblock.id" />},
				  async:false,//同步
				  success: function(data){
			  		temp = eval("("+data+")").canNext;
		  			if(temp == 1){
		  				flag = true;
		  			}
				  }
				});
				return flag;
			}
			//动态添加答对答错提示图片
			function addRightOrWrongImg(elementid,type){
		   		if(type == 1){//答对
					$("#"+elementid).html(swfVideo__("elstuffs/right.swf",60*60));
		   			//$("#"+elementid).html("<img  src='images/datigaiban/right.png' width='50' height='50' style=' float:right;' />");
		   		}else{//答错
		   			//$("#"+elementid).html("<img  src='images/datigaiban/wrong.png' width='50' height='50' style=' float:right;' />");
					$("#"+elementid).html(swfVideo__("elstuffs/wrong.swf",60*60));
		   		}
		    }
		    /**
		    //查看帮助swf
		    function preview(obj) {
				width=800;
				height=600;
	  	 		var sFeature = "dialogHeight:"+height+"px;dialogWidth:"+width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
	  	 		var indexStart = obj.lastIndexOf("/");
	  	 		var indexEnd = obj.lastIndexOf(".");
	  	 		var qstuffid = obj.substring(indexStart+1,indexEnd);
				window.showModalDialog("question_stuffPreview.action?qstuff.id="+qstuffid+"&x="+Math.random(),null,sFeature);
			}
			*/
			//查看帮助swf
		    function preview(obj) {
				var content3 = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='550' height='400'><param name='movie' value='"+obj+"' /><param name='quality' value='high' /><param name='wmode' value='opaque' /><embed height='400' width='550'  src='"+obj+"' type='application/x-shockwave-flash'></embed></object>";
        		TINY.box.show(content3,0,0,0,1);
			}
			
			//词汇
			function showV(){
				/**
				var courseid = window.parent.courseid;
				var iWidth = 1000; //弹出窗口的宽度;
				var iHeight = 600 ; //弹出窗口的高度;
				var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
				window.open("vocabulary_search.action?vocabulary.status=1&course.id="+courseid,"词汇列表",'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
				*/
				/**
				var courseid = window.parent.courseid;
				window.parent.qsubmit();
				window.parent.location.href='wjm_user_center.action?module=vocabulary_search.action?course.id='+courseid;
				*/
				var courseid = window.parent.courseid;
				if($("#stem_Voc").css("display")=="block"){
					closeVoc();
				}else{
					var i = document.getElementById("add").value;
					$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
						type:"post",
						url:"vocabulary_search2.action",data:{"course.id":courseid},success:function (data) {
					   		if(i==0){
								$("#stem_Voc").append(data);
							}
					   		$("#stem_Voc").css("display","block");
					},error:function(msg){
						//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
						//$("#loading_"+blockid).css("display","none");
					}});
				}
			//	window.parent.qsubmit();
			//	window.parent.location.href='wjm_user_center.action?module=vocabulary_search.action?vocabulary.wordid=-1';
			}
			
			function closeVoc(){
				if($("#stem_Voc").css("display")=="block"){
					$("#stem_Voc").css({  display:"none" });
				}else{
					$("#stem_Voc").css({  display:"block" });
				}
				if($("#view_Voc").css("display")=="block"){
					$("#view_Voc").css({  display:"none" });
					$("#stem_Voc").css({  display:"none" });
				}
				document.getElementById("add").value = 1;
			}
			
			//返回
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
				jConfirm("确认回到首页?,Confirm Return Home Page,Sure?", 'Information', function(r) {
					$.alerts.dialogClass = null; // reset to default
					if(r){
						var view = <s:property value="view" />;
						if(view == 0){
							window.parent.qsubmit();
						}
						if(classid==undefined && courseid==undefined){
							window.parent.location.href='wjm_user_center.action';
						}else{
							window.parent.location.href='wjm_user_center.action?elClass.id='+classid+"&course.id="+courseid+"&backuptoindex=1";
						}
					}
				});
			}
			
			function jjAlert(message){
				$.alerts.dialogClass = "style_1"; // set custom style class
				jAlert(message, 'Information', function() {
					$.alerts.dialogClass = null; // reset to default
				});
			}
			
			function LastFirstConfirm(message){
				var view = <s:property value="view" />;
				if(view == 1){
					//首先提示最后一题或者第一题,然后提示是否关闭窗口
					$.alerts.dialogClass = "style_1"; // set custom style class
					jAlert(message, 'Information', function() {
						$.alerts.dialogClass = null; // reset to default
						$.alerts.dialogClass = "style_1"; // set custom style class
						jConfirm("确认回到首页?,Confirm Return Home Page,Sure?", 'Information', function(r) {
							$.alerts.dialogClass = null; // reset to default
							if(r){
								if($("#oprate_question",parent.document).css("display") == "block"){
								 	//window.parent.closeFrame();
								 	window.parent.location.href="wjm_user_center.action";
								}
							}
						});
					});
				}else {
					$.alerts.dialogClass = "style_1"; // set custom style class
					jAlert(message, 'Information', function() {
						$.alerts.dialogClass = null; // reset to default
						$.alerts.dialogClass = "style_1"; // set custom style class
						jConfirm("确认提交?,Submit?", 'Information', function(r) {
							$.alerts.dialogClass = null; // reset to default
							if(r){
								window.parent.document.getElementById("answered").value = 1;
								window.parent.qsubmit();
								if(window.parent.opener){
									window.parent.opener.refresh1();
								}
							}
						});
					});
				}
			}
		</script>
	</body>
</html>



