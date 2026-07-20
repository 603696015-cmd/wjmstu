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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script src="js/jquery.alerts.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery/jquery-ui-1.9.2.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.countdown.js"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="js/listen_keydown.js"></script>
		<script type="text/javascript" src="js/tinybox2.js"></script>
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
		<title>听音选择</title>
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet" type="text/css" media="screen" />
				
<link href="js/tinybox2_style.css" rel="stylesheet" type="text/css"  />
				
<style type="text/css">
.zxx_list_title{background:#eeeeee; border:1px solid #cccccc; padding:1em;}
.zxx_list_content{padding:1em;}
.tbox{position:absolute; display:none; padding:10px; background:#ffffff url(image/preload.gif) no-repeat 50% 50%; border:10px solid #e3e3e3; z-index:2000;}
.tmask{position:absolute; display:none; top:0; left:0; height:100%; width:100%; background:#000000; z-index:1500;}

.tcontent{background:#ffffff; font-size:1.1em;}
.tclose {position:absolute; top:0px; right:0px; width:30px; height:30px; cursor:pointer; background:url(images/close.png) no-repeat;z-index:2000;}
.tclose:hover {background-position:0 -30px}
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
	background-image: url(images/images1113/datiye2.png);
	background-position: center top;
	background-repeat: repeat-y;
	font-family:"楷体";
}

.STYLE1 {
	color: #FFFFFF;
	font-size: 25px;
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
	font-size: 18px;
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
function playQmusicByObject(object){
	if(object.URL!=""){
		object.controls.play();	
	}
}
 var tempObj = null;
    function checkObject(){
    	if(fashengQuestion.url==""){
    		clearTimeout(tempObj);
    		if(document.getElementById("counter").innerHTML==""){
				loadCountdown('counter',returnToHHSSString(<s:property value="question.epblock.answerTime" />));
    		}
    	}else{
    		if(fashengQuestion.playState==1||fashengQuestion.playState==8){
			   clearTimeout(tempObj);
			   if(document.getElementById("counter").innerHTML==""){
			   		loadCountdown('counter',returnToHHSSString(<s:property value="question.epblock.answerTime" />));
			   }
		   }else{
			   setTimeout("checkObject()", 1000);
		   }
    	}
    }
function playQmusic(answer,type,atime,wrongCount){
	var iAnswer = parseInt(answer.split("-=SpEl=-")[0]);
	addRightOrWrongImg("r_o_w_img",1);
	r_o_w_state = setTimeout("checkROWState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
}
function playQmusic2(answer,type,atime,wrongCount){
	var iAnswer = parseInt(answer.split("-=SpEl=-")[0]);
	addRightOrWrongImg("r_o_w_img",0);
	r_o_w_state = setTimeout("checkROWState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
}
 //答错后，播放错误提示音，错误提示音播完后，播放选择图片的音频文件
  var state = null;
  var music_statu = null;
  var r_o_w_state = null;
  
  //判断答对或者答错swf是否播放结束
   function checkROWState(type,iAnswer,atime,wrongCount){
   		if(video__.totalFrames == video__.CurrentFrame()+1){
		   clearTimeout(r_o_w_state);
		   if(type == 1){
		   		if(right.URL!=""){
		   		if(examType==1){
					right.controls.play();	
					}
					 //做一个循环去判断 第一个视频是否结束
				    state = setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);	
				}
		   }else {
		   		if(atime == 1){
					if(examType == 1){
						if(error1.URL!=""){
						if(examType==1){
							error1.controls.play();
							}	
						  	//做一个循环去判断 第一个视频是否结束
						  	state = setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);	
						}
					}else{
						if(error2.URL!=""){
						if(examType==1){
							error2.controls.play();	
							}
						  	//做一个循环去判断 第一个视频是否结束
						  	state = setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);	
						}
					}
				 }else if(atime == 2){
				   		if(error2.URL!=""){
				   		if(examType==1){
							error2.controls.play();	
							}
					  		//做一个循环去判断 第一个视频是否结束
					  		state = setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);	
					}
				 }
		   }
	   }else{
		   setTimeout("checkROWState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
	   }
   }
  
   function checkState(type,iAnswer,atime,wrongCount){
   		if(type == 1){
   				if(right.playState==1||right.playState==8||right.playState==10){
				   clearTimeout(state);
				   getObject(iAnswer).controls.play();
				   music_statu = setTimeout("checkMusicState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
			   }else{
				   setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
			   }
   		}else if(type == 2){


   				if(atime == 1){
   					if(examType == 1){
						if(error1.playState==1||error1.playState==8||error1.playState==10){
						   clearTimeout(state);
						   playQmusicByObject(fashengQuestion);
		    		   	   tempObj = setTimeout("checkObject()", 1000);
					   }else{
						   setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
					   }
					}else{
						if(error2.playState==1||error2.playState==8||error2.playState==10){
						   clearTimeout(state);
						   getObject(iAnswer).controls.play();
					       music_statu = setTimeout("checkMusicState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
						   //window.parent.closeFrame();
				   		   //window.parent.showQN();
					   }else{
						   setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
					   }
					}
	   	   		}else if(atime == 2){
	   	   			if(error2.playState==1||error2.playState==8||error2.playState==10){
					   clearTimeout(state);
					   getObject(iAnswer).controls.play();
					   music_statu = setTimeout("checkMusicState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
				   }else{
					   setTimeout("checkState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
				   }
	   	   		}
   		}
   }
   function getObject(index){
	if(index == 0){
		return A;
	}else if(index == 1){
		return AA;
	}else if(index == 2){
		return AAA;
	}else if(index == 3){
		return AAAA;
	}else if(index == 4){
		return AAAAA;
	}else if(index == 5){
		return AAAAAA;
	}else {
		return null;
	}
}
   function checkMusicState(type,iAnswer,atime,wrongCount){
	   if(getObject(iAnswer).playState==1||getObject(iAnswer).playState==8){
		   clearTimeout(music_statu);
		   if(type == 1){
		   		 if(examType == 1){
	   			}else{
	   				window.parent.closeFrame();
	   				window.parent.showQN();
	   			}
		   }else{
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
			   				//window.parent.closeFrame();
			   				//window.parent.showQN();
			   			}
		   			}else{
		   				//window.parent.closeFrame();
		   				//window.parent.showQN();
		   			}
		   		}else if(atime ==1){
		   			if(examType == 1){
	   	   				js_reset();
	   	   			}else{
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
				   				window.parent.closeFrame();
				   				window.parent.showQN();
				   			}
			   			}else{
			   				window.parent.closeFrame();
			   				window.parent.showQN();
			   			}
	   	   			}
		   		}
		   }
	   }else{
		   setTimeout("checkMusicState("+type+","+iAnswer+","+atime+","+wrongCount+")", 1000);
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
		          		submittyxt();//强制提交
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
		    playQmusicByObject(fashengQuestion);
			tempObj = setTimeout("checkObject()", 1000);
			
		}else{
			init_statu = setTimeout("checkInit()",1000);
		}
	}
$(function(){
		$("#tj").bind("click",function(){
			submittyxt();
		});
		var left = $(document.body).width()/2+260;
		var top = $(document.body).height()/2 + 180;
		$("#youxiajiao").css({"position":"absolute","top":top,"left":left});
		
		//默认赋值第defaultSelect+1张图片
		var defaultSelect = <s:property value="defaultSelect" />;
		$("#questions_"+blockid+"_0_" + sortid +"_stuAnswers" ).val(defaultSelect);
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
					var jdata=eval("("+data+")");
					if(jdata.atime==1)
						if(jdata.status==1){//1次回答正确
							$("#counter").html("");
							$("#totalScore").html(jdata.totalScore);
							$("#next").attr("src","images/datigaiban/anniu_32.gif");
							playQmusic(jdata.answer,1,1,jdata.wrongCount);
						}else{//1次回答错误
							$("#counter").html("");
							$("#totalScore").html(jdata.totalScore);
							playQmusic2(jdata.answer,2,1,jdata.wrongCount);
						}
					else if (jdata.atime==2){
						if(jdata.status==1){//2次回答正确
							$("#counter").html("");
							$("#totalScore").html(jdata.totalScore);
							$("#next").attr("src","images/datigaiban/anniu_32.gif");
							playQmusic(jdata.answer,1,2,jdata.wrongCount);
						}else{//2次回答错误
							$("#counter").html("");
							$("#totalScore").html(jdata.totalScore);
							$("#next").attr("src","images/datigaiban/anniu_32.gif");
							playQmusic2(jdata.answer,2,2,jdata.wrongCount);
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


var blockid= <s:property value="question.epblock.id" />;
var sortid = <s:property value="question.sortid" />;
var title_images = null;
function setVal(obj,index){
	var view = <s:property value="view" />;
	if(view == 0){//考试
		$(obj).attr("id","question_title_image_"+index);
		var imageid = $(obj).attr("id");
		title_images =  $("#ques_title_images").find("img");
		for(var ci = 0 ; ci<title_images.length;ci++){
			$(title_images[ci]).attr("id","question_title_image_"+ci);
			if($(title_images[ci]).attr("id") != imageid){
				title_images[ci].style.border='';
				title_images[ci].style.padding='';
			}else{
				title_images[ci].style.border='solid 2px #CF9';
				title_images[ci].style.padding='2px';
			}
		}
		$("#questions_"+blockid+"_0_" + sortid +"_stuAnswers" ).val(index);
	}
}
function submittyxt(){
	var ifview = <s:property value="view" />;
	if(ifview == 0){
		if(endtime == 0){//时间到
			ss = setTimeout("checktimesubmit()", 1000);
		}else{
			if($("#questions_" + blockid + "_0_" + sortid).val() != -1 ){
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
						var jdata=eval("("+data+")");
						if(jdata.atime==1)
							if(jdata.status==1){//1次回答正确
								$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								$("#next").attr("src","images/datigaiban/anniu_32.gif");
								playQmusic(jdata.answer,1,1,jdata.wrongCount);
							}else{//1次回答错误
								$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								playQmusic2(jdata.answer,2,1,jdata.wrongCount);
							}
						else if (jdata.atime==2){
							if(jdata.status==1){//2次回答正确
								$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								$("#next").attr("src","images/datigaiban/anniu_32.gif");
								playQmusic(jdata.answer,1,2,jdata.wrongCount);
							}else{//2次回答错误
								$("#counter").html("");
								$("#totalScore").html(jdata.totalScore);
								$("#next").attr("src","images/datigaiban/anniu_32.gif");
								playQmusic2(jdata.answer,2,2,jdata.wrongCount);
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
		}
	}
	
	
}

function play_fashengquestion(){
	check_bofang();
}
var bofang_temp = null;
function check_bofang(){
	if(fashengQuestion.url!=""){
		if(fashengQuestion.playState==1||fashengQuestion.playState==8){
			clearTimeout(bofang_temp);
			fashengQuestion.controls.play();	
		}else{
			bofang_temp = setTimeout("check_bofang()", 1000);
		}
	}
}
</script>
		<script type="text/javascript">
var obj = document.getElementById("pause_play");

//停止OK

function js_stop(){
	var image = document.getElementById("pause_play");
	image.src = "images/bf/anniu_36.png";
	fashengQuestion.controls.stop();
	
}
//播放暂停切换OK

function js_pause_play(){
	var image = document.getElementById("pause_play");
	//停止、暂停、正常
	if(fashengQuestion.playState == 1 || fashengQuestion.playState == 2 || fashengQuestion.playState == 3){
		if(fashengQuestion.playState == 2){//暂停
			image.src = "images/bf/anniu_31.png";
			fashengQuestion.controls.play();
		}else if(fashengQuestion.playState == 3){//正常
			image.src = "images/bf/anniu_36.png";
			fashengQuestion.controls.pause();
		}else if(fashengQuestion.playState == 1){//停止
			image.src = "images/bf/anniu_31.png";
			fashengQuestion.controls.play();
		}
	}
}
//从头开始播放 OK

function js_reset(){
	var image = document.getElementById("pause_play");
	image.src = "images/bf/anniu_31.png";
	fashengQuestion.controls.stop();
	fashengQuestion.controls.play();
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
	 alert(end);
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
img,input {
	margin: 0;
	padding: 0;
	border: 0;
}

input {
	background: none;
}
</style>
		<script>


</script>
		<script type="text/javascript">
</script>
		<style type="text/css">
.daan {
	font-weight: bold;
	color: red;
	padding-left: 20px;
}
</style>
	</head>

	<body onload="load();" style="overflow:hidden;">
		<APPLET ID="录音功能(record)" CODE="com.msc.applet.RecognizeApplet.class"
			CODEBASE="/svn3/record" ARCHIVE="mscclient.jar" WIDTH="0" HEIGHT="0">
			<PARAM NAME="type" VALUE="application/x-java-applet;version=1.2.2">
			<PARAM NAME="scriptable" VALUE="false">
		</APPLET>
		<!-- 答对提示音 -->
		<div style="display: none;" id="music">
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
				<PARAM NAME="volume" value="100">
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
			<!-- 各选项对应的音频文件 -->
			<s:iterator value="question.voicePaths" status="status">
				<s:if test="#status.index==0">
					<object id=A classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
						width=280 height=60
						codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
						Microsoft? Windows Media? Player components...
						type=application/x-oleobject>
						<param name=URL value="<s:property/> ">
						<PARAM NAME=UIMode value=full>
						<PARAM NAME=AutoStart value=false>
						<PARAM NAME=Enabled value=true>
						<PARAM NAME=enableContextMenu value=false>
					</object>
				</s:if>
				<s:elseif test="#status.index==1">
					<object id=AA classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
						width=280 height=60
						codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
						Microsoft? Windows Media? Player components...
						type=application/x-oleobject>
						<param name=URL value="<s:property/> ">
						<PARAM NAME=UIMode value=full>
						<PARAM NAME=AutoStart value=false>
						<PARAM NAME=Enabled value=true>
						<PARAM NAME=enableContextMenu value=false>
					</object>
				</s:elseif>
				<s:elseif test="#status.index==2">
					<object id=AAA classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
						width=280 height=60
						codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
						Microsoft? Windows Media? Player components...
						type=application/x-oleobject>
						<param name=URL value="<s:property/> ">
						<PARAM NAME=UIMode value=full>
						<PARAM NAME=AutoStart value=false>
						<PARAM NAME=Enabled value=true>
						<PARAM NAME=enableContextMenu value=false>
					</object>
				</s:elseif>
				<s:elseif test="#status.index==3">
					<object id=AAAA classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
						width=280 height=60
						codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
						Microsoft? Windows Media? Player components...
						type=application/x-oleobject>
						<param name=URL value="<s:property/> ">
						<PARAM NAME=UIMode value=full>
						<PARAM NAME=AutoStart value=false>
						<PARAM NAME=Enabled value=true>
						<PARAM NAME=enableContextMenu value=false>
					</object>
				</s:elseif>
				<s:elseif test="#status.index==4">
					<object id=AAAAA classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
						width=280 height=60
						codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
						Microsoft? Windows Media? Player components...
						type=application/x-oleobject>
						<param name=URL value="<s:property/> ">
						<PARAM NAME=UIMode value=full>
						<PARAM NAME=AutoStart value=false>
						<PARAM NAME=Enabled value=true>
						<PARAM NAME=enableContextMenu value=false>
					</object>
				</s:elseif>
				<s:elseif test="#status.index==5">
					<object id=AAAAAA classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6
						width=280 height=60
						codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=5,1,52,701standby=Loading
						Microsoft? Windows Media? Player components...
						type=application/x-oleobject>
						<param name=URL value="<s:property/> ">
						<PARAM NAME=UIMode value=full>
						<PARAM NAME=AutoStart value=false>
						<PARAM NAME=Enabled value=true>
						<PARAM NAME=enableContextMenu value=false>
					</object>
				</s:elseif>
			</s:iterator>
		</div>


		<!-- 新页面格局 -->
		<table width="1147" height="610" border="0" align="center"
			cellpadding="0" cellspacing="0"
			style="margin-top:117px;">
			<tr>
				<td height="507">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="557" height="507" align="center" valign="top">
								<table width="94%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td height="74"  align="center" valign="middle">
                                            
										    <table border="0" align="left" cellpadding="0" cellspacing="0" style="margin-top:3px;">
										      <tr>
										        <td width="200" align="center">&nbsp;</td>
										        <td width="70" align="center"><img src="images/datigaiban/money.png" width="55" height="54" align="middle"/></td>
                                              <td>
                                              
											<span class="STYLE1">得分：</span>
									 <span style="color:#EC0000;font-size:25px;font-weight:bold;" id="totalScore"><s:property value="myExamPaper.score" /></span> 
											&nbsp;&nbsp;<span class="STYLE1">分</span>                                              </td>
                                              <td width="190"></td>
										        <td width="60" align="left" valign="top" style="padding-top:4px;"><img height="45" width="45" src="images/20140702/help_l.png" 
													onmousedown="this.src='images/20140702/help_l.png'"
													onmouseup="this.src='images/20140702/help_l.png'"  
													onclick="preview('<s:property value="helpSwf" />');"/>											</td>
										        <td width="60" align="left" valign="top" style="padding-top:4px;"><img height="45" src="images/20140702/txt_s.png" alt="" width="45" onclick="showStemText();"/></td>
										        <td width="60" align="left" valign="top" style="padding-top:4px;"><img height="45" src="images/20140702/fanhui.png" alt=""  width="45" onclick="backup();" /></td>
									          </tr>
								      </table></td>
										
									</tr>
									<tr>
										<td width="535" height="200" align="center" valign="top"
											style="padding-top: 20px;" id="ques_title_images">
											<!-- 选项 -->
											<wysLib:wjmSelects></wysLib:wjmSelects>
										</td>
									</tr>
									
									<s:if test="view == 1">
										<tr>
											<td width="390" height="100" valign="top">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													 <tr>
														 <td >
															<table width="80%" height="44" border="0" align="center"
																cellpadding="0" cellspacing="1"
																style="color:#F90; font-weight: bold;">
																<tr>
																	<td height="30" align="left"  >
																		发声提问:<img src='images/bofang.jpg' onclick="play_fashengquestion();"/>
																	</td>
																</tr>
																<tr>
																	<td height="30" align="left" >
																		答案:<s:property value="question.standardAnswer18" />
																	</td>
																</tr>
																<tr>
																	<td align="left" >
																		作答:<s:property value="question.stuAnswer_" />
																	</td>
																</tr>
																<tr>
																	<td align="left"   >
																		得分:<span style="color:red;font-size:30px;"><s:property value="question.myScore" /></span>分
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
								<div>
									<div>
										<form action="#" id="theform" method="post">
											<s:hidden name="question.id" />
											<s:hidden name="question.qtype" />
											<s:hidden name="question.epblock.id" />
											<s:hidden name="question.sortid" />
											<s:hidden name="myExamPaper.id" />
											<input type="hidden"
												name="questions_<s:property value="question.epblock.id" />_0_<s:property value="question.sortid" />_stuAnswers"
												id="questions_<s:property value="question.epblock.id" />_0_<s:property value="question.sortid" />_stuAnswers"
												value=-1 />
										</form>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="margin-top: 46px;">
						<tr>
						  <s:if test="view==0">
	                            <td width="200" align="center" >
	                            	
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
								
								<td width="260" align="center" valign="middle">&nbsp;								</td>
								
	                            <!-- 倒计时 -->
								<td   align="center" valign="middle" >
									<div id="counter"></div>
								</td>
								
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
								<td width="70" align="center">&nbsp;	                            </td>
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
		<table border="0"  cellpadding="0" cellspacing="0" width="280px">
             <tr>
             	 <td width="180px" style=" float:right;">&nbsp;</td>
                 <td width="70" ><div id="r_o_w_img" style="width:50px;height:50px;"></div></td>
                 <td width="50px">
                      <img src="images/datigaiban/tj.png" 
                           onmousedown="this.src='images/datigaiban/tj_h.png'"
						   onmouseup="this.src='images/datigaiban/tj.png'"
					       id="tj" width="50" height="50" style=" float:right;margin-right:5px" />
				 </td>
                                             
             </tr>
        </table>
        </s:if>
		</div>

		<!--题干和按钮部分到此结束-->

		<script type="text/javascript">
			function showStemText(){
				/*if($("#stem_text").css("display")=="block"){
					$("#stem_text").css({  display:"none" });
				}else{
					$("#stem_text").css({  display:"block" });
				}*/
			    $("#stem_text").fadeToggle(2000);
			   // TINY.box.show({html:ss,boxid:'frameless',width:550,height:300,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){closeJS()}});
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
		    
		    //查看帮助swf
		    function preview(obj) {
				var content3 = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='550' height='300'><param name='movie' value='"+obj+"' /><param name='quality' value='high' /><param name='wmode' value='opaque' /><embed height='300' width='550'  src='"+obj+"' type='application/x-shockwave-flash'></embed></object>";
        		//	TINY.box.show(content3,0,0,0,1);
			
		
		
	
TINY.box.show({html:content3,boxid:'frameless',width:550,height:300,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){closeJS()}});

			}
			
			function closeJS(){}
			
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
			/*	if($("#stem_Voc").css("display")=="block"){
					closeVoc();
				}else{*/
					var i = document.getElementById("add").value;
					$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
						type:"post",
						url:"vocabulary_search2.action",data:{"course.id":courseid},success:function (data) {
							if(i==0){
								//$("#stem_Voc").append(data);
								TINY.box.show({html:data,boxid:'frameless',width:550,height:300,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){closeJS()}});
							}
					   	//	$("#stem_Voc").css("display","block");
					},error:function(msg){
						//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
						//$("#loading_"+blockid).css("display","none");
					}});
				//}
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
				jConfirm("确认回到首页? Back to Home Page,Sure?", 'Information', function(r) {
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
						jConfirm("确认回到首页? Back to Home Page,Sure?", 'Information', function(r) {
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
						jConfirm("&nbsp;&nbsp;&nbsp;试卷将被提交。确认提交? Submit?&nbsp;&nbsp;&nbsp;", 'Information', function(r) {
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



