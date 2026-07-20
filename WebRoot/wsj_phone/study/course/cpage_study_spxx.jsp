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
<link href="images/tz_images/css.css" type="text/css"  rel="stylesheet" />
<script type="text/javascript" src="js/jquery/jquery-ui-1.9.2.js"></script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<link href="css/study_csp.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/CourseStudy.js"></script>
<script type="text/javascript" src="js/course_spxx.js"></script>
<script type="text/javascript" src="js/flexpaper.js"></script>
<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
<title>视频学习页</title>
<style type="text/css">
img,input{margin:0;padding:0;border:0;}
input{background:none;}
.q_content{}
.q_content .cnt{height:30px;line-height: 40px;padding: 0px 10px 0px 10px;}
.q_content div{float:left}
.q_content .blnak{border-bottom: solid 1px;width:100px;height:30px;}
.bx_answeri{border:1px solid #0000FF;
 background:#C4E3FD; width:100px;
 height:30px;float: left;color:#000;
 text-align: center;line-height: 40px;
 margin:10px 0px 0px 10px;
 cursor: default;}
.bx_answeri_sp{width:20px;height:30px;float:left;}
.bx_answer{text-align:center; width:660px;height:150px;margin:0px auto 0px auto;}
.blnak-state-active{border: dotted 1px #ffffff;}
.clr{clear: both;}
</style>


		<script type="text/javascript">
			var second=1000; //间隔时间1秒钟
			var _cpst;
			var needsetCp = true;
			function myload(status){
				var from = 0;
				if(!<s:property value="myCPage.passed"/>)
					from = <s:property value="myCPage.passtime"/>;
				_cvideo = new CourseVideo(<s:property value="coursePage.type"/>,"<s:property value="coursePage.page_url_"/>", from);
				_cvideo.show("page_file" );
				_cpst=new CourseStudy(<s:property value="course.classid"/>,<s:property value="course.id"/>, <s:property value="coursePage.id"/>,
					 <s:property value="myCPage.passtime"/>,
					 <s:property value="myCPage.cpage.during"/>*60,
					 <s:property value="coursePage.queryTime"/>,
					 <s:property value="myCPage.passtime2"/>,
					 <s:property value="studyCourseRecordId"/>);
				_cpst.passed2=<s:property value="myCPage.passed2"/>;
				_cpst.durtimediv="timer3";
				_cpst.realtimediv="timer2";
				_cpst.processdiv="processDiv3";
				_cpst.studyinfo_time=<s:property value="#session.studyinfo_time"/>;
				_cpst.init();
				//_cpst.autoSetCprocess();
				document.getElementById("next_course").src="images/tz_images/bf/anniu_32h.png";
			}
			window.onbeforeunload=function(){
				//if(<s:property value="coursePage.getcredit"/>!=1){
					//if(_cpst.passtime2>=<s:property value="myCPage.cpage.during"/>*60){
					//	if(_cpst.passed2==0){
					//		alert("你还有考试没过！");
					//	}
					//}
				//}
				window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
				_cpst.exitStudy(); 
				//刷新当前窗口的父页面
				/**
				if(window.opener){
					window.onbeforeunload = null;
					window.setInterval(function(){
						quizpaperinit.action=document.location.href;
						quizpaperinit.submit();
					},800);
				}
				*/
			}
			
	var status=1;
	var course_status;
	function check_pass(){
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
		url:"course_checkPass.action",data: {
		"myCPage.cpid":<s:property value="coursePage.id"/>, 
		"x":Math.random
		}, success:
		function (data) {
			var d = eval("("+data+")");
			
			if(d.msg==1){
			    window.clearInterval(timeObj);
			    timeObj = null;
				document.getElementById("next_course").src="images/tz_images/bf/anniu_32.png";
				course_status=document.getElementById("next_course").src;
				
				if(status==1){
				$('#next_course').bind("click",function(){
					$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
						type:"post",
						url:"course_checkExamRoom.action",data: {
						"myCPage.cpid":<s:property value="coursePage.id"/>, 
						"myCPage.courseid":<s:property value="course.id"/>, 
						"myCPage.classid":<s:property value="course.classid"/>,
						"x":Math.random
						}, success:
						function (data) {
						var jsondata = eval("("+data+")");
						if(jsondata.flag==1){
							//var mw = window.open("quizpaperinit_byepid.action?course.isLogout=1&coursePage.id=<s:property value='coursePage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>&myCPage.cpid=<s:property value='coursePage.id'/>&examRoom.id=1476&examPaper.id="+jsondata.epid+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
						//	if (window.screen){ 
							//	mw.moveTo(0, 0);
							//	mw.resizeTo(screen.availWidth,screen.availHeight);
							//	}
							document.getElementById("choose_menu").href="quizpaperinit_byepid_wjm.action?myCPage.classid=<s:property value='course.classid'/>&course.isLogout=1&coursePage.id=<s:property value='coursePage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>&myCPage.cpid=<s:property value='coursePage.id'/>&examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&datetime="+new Date();
							//setdisable();
						}else{
							//window.href="course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>";
							//window.open("course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>","course_exam_6","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
							//window.close();	
							document.getElementById("choose_menu").href="course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>";
						}
				}});
				
				})}
			}else if(d.msg =="error"){
				alert("学习进度信息,停止记录！将关闭页面！");
				window.onbeforeunload=null;
				window.onunload=null;
				window.clearInterval(this.setIt);
				window.close();
			}
		}});
}
 			
				 
	function saveToUrl(obj){
	$(obj).attr("href",$(obj).attr("href"));
	return true;
}

	function check_back(){
			$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
			url:"course_checkExamRoom.action",data: {
			"myCPage.cpid":<s:property value="coursePage.id"/>, 
			"myCPage.courseid":<s:property value="course.id"/>, 
			"myCPage.classid":<s:property value="course.classid"/>,
			"x":Math.random
			}, success:
			function (data) {
			var jsondata = eval("("+data+")");
			if(jsondata.flag==1){
				var mw = window.open("quizpaperinit_byepid_wjm.action?course.isLogout=1&coursePage.id=<s:property value='coursePage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>&myCPage.cpid=<s:property value='coursePage.id'/>&examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				if (window.screen){ 
					mw.moveTo(0, 0);
					mw.resizeTo(screen.availWidth,screen.availHeight);
					}
				//setdisable();
			}else{
				//window.href="course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>";
				window.open("course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>","_self");
				//window.close();	
			}
	}});
	
	}
		</script>
		<script type="text/javascript">
			var timeObj;
			function loadInterval(){
				 timeObj= setInterval('check_pass()',second);
			}
		</script>
<style type="text/css">
.daan {font-weight:bold;color:red; padding-left:20px;}
</style>
</head>

<body OnLoad="myload('<s:property value="status"/>');loadInterval()">
<div><img src="images/tz_images/header.jpg" width="1349" /></div>
<div><img src="images/tz_images/header_xia.jpg" width="1349" /></div>
<div id="main_l"><img src="images/tz_images/left.png" /></div>
<div id="content">
	<div>
    	<div id="content_l"><img src="images/tz_images/content_l.png" /></div>
        <!--题干和按钮部分开始-->
        
<div id="content_zhong">
          <div style="width:802px;margin-left:135px;margin-top:130px; float:left;">
            <!--主体上边背景-->
                <div id="m_t" style="width:800px">
                    <div style="float:left;"><img src="images/tz_images/main_lt.png" /></div>
                    <div id="main_t"></div>
                    <div><img src="images/tz_images/main_rt.png" /></div>
                </div>
                <!--主体中部-->
                <div id="m_z">
                    <div id="m_zl"></div>
                    <div id="m_zz">
                      <table width="100%" height="444" border="0" cellpadding="0" cellspacing="0">
                       <div style="height: 444px; width: 700px;" id="page_file"></div>
                      </table>
                    </div><div id="m_zr"></div>
                </div>
                <!--主体下部-->
                <div id="m_b" style="width:800px;">
                    <div id="m_bl"><img src="images/tz_images/main_lb.png" /></div>
                    <div id="m_bz" ></div>
                    <div><img src="images/tz_images/main_rb.png" /></div>
                </div>
                  </div>
                <!--主体播放条-->
<div id="bfq" style="margin-left:85px; width:911px;">
                    <div id="bfq_l"><img src="images/tz_images/bf_l.png" /></div>
                    <div id="bfq_z" >
                    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="63" align="center">
    	<table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="79" width="74" ><input type="image" src="images/tz_images/bf/ann_37.png" onmousedown="this.src='images/tz_images/bf/anniu_27.png'" onmouseup="this.src='images/tz_images/bf/ann_37.png'" onclick="javascript:history.go(-1);"/></td>
        <td height="79" width="74" ><input type="image" src="images/tz_images/bf/anniu_35.png" onmousedown="this.src='images/tz_images/bf/ann_35.png'" onmouseup="this.src='images/tz_images/bf/anniu_35.png'" onclick="getVcastr().videoStop();"/></td>
        <td height="79" width="83" ><input type="image" src="images/tz_images/bf/anniu_36.png" onclick=" getVcastr().videoPlay(); var tmp=this.attributes['old'].value;this.attributes['old'].value=this.src;this.src=tmp;" style='pointer:cursor;border:none' old='images/tz_images/bf/ann_36.png'   /></td>
        <td height="79" width="74" ><input type="image" src="images/tz_images/bf/anniu_31.png" onclick="getVcastr().pause(); var tmp=this.attributes['old'].value;this.attributes['old'].value=this.src;this.src=tmp;" style='pointer:cursor;border:none' old='images/tz_images/bf/ann_35.png' /> </td>
      <!--   <td height="79" width="74" ><input type="image" src="images/tz_images/bf/anniu_32.png" onmousedown="this.src='images/tz_images/bf/ann_39.png'" onmouseup="this.src='images/tz_images/bf/anniu_32.png'"/></td> -->
     	<td height="79" width="74" ><A  id="choose_menu"  onclick="return saveToUrl(this);"> <img src="images/tz_images/bf/anniu_32h.png" id="next_course"  /></A></td>
     	
     	
      </tr>
    </table>
    </td>
  </tr>
</table>

                    </div>
                    <div id="bfq_r"><img src="images/tz_images/bf_r.png" /></div>
                </div>
      </div>
          
 		
<!--题干和按钮部分到此结束-->
        <div id="content_r"><img src="images/tz_images/content_r.png" /></div>
    </div>
    <div id="bottom"></div>
</div> 
<div id="main_r"><img src="images/tz_images/right.png" /></div>

	</body>
</html>
