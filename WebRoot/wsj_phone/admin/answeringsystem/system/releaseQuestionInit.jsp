<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>问吧-KesionCMS 在线演示</title>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta content="" name=keywords>
		<meta content="" name=description>
		<link href=" http://demo.kesion.com/images/style.css" type=text/css
			rel=stylesheet>
		<script src='/ks_inc/kesion.box.js' language="JavaScript"></script>
		<script src=" http://demo.kesion.com/ks_inc/jquery.js"
			type="text/javascript"></script>
		<script src=" http://demo.kesion.com/ks_inc/common.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<SCRIPT type="text/javascript">
		//选择问答类别
		function searchAnsweringTypeTreeInit(){
				 width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("selectAnsweringTypeTreeInit.action?x="+Math.random(),null,sFeature);
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("-=wys=-");
					 document.getElementById("ques.answeringType.id").value=bh[2];
					 document.getElementById("ques.answeringType.name").value=bh[1];
				 }
			}
		//指定回答人
		function answeringUsersInit(){
			var userids_dom = document.getElementById("ques.answerUserids");
			var type = 1;
			var user_ary ;
			var user ;
			var userids = "";
			var user_html = "" ;
			var ccount = 5;//每行显示10个人
			
			width=screen.availWidth * 0.8;;
			height=screen.availHeight * 0.8;;
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		  	var rv =  window.showModalDialog("answeringUsersInit.action?x="+Math.random(),null,sFeature);
		  	if(rv!=undefined&&rv!=""){
				user_ary = rv.split(",");
				if(user_ary!=undefined && user_ary.length>0){
					user_html = "<table id=userids_table_"+type+" border='0'  cellspacing='1' cellpadding='1'><tr>";
					for(var i=0;i<user_ary.length;i++){
						user = user_ary[i].split("==");
						userids += user[0] + ",";
						user_html += "<td id=userids_td_"+type+"_"+i+">"+user[1] + "<a href='javascript:removeByUserid("+user[0]+","+type+","+i+");' ><span style='color:blue;corsor:hand'>X</span></a>" + "&nbsp;&nbsp;" +"</td>";
					}
					user_html += "</tr><table>";
				}
				if(user_html!=""){
					$("#users_div_1_"+type).html(user_html);
				}
				if(userids!="" && userids.charAt(userids.length-1)==","){
					userids = userids.substring(0,userids.lastIndexOf(","));
					userids_dom.value = userids;
				}
			}
		}
		function removeByUserid(userid,type,i){
			$("#userids_td_"+type+"_"+i).remove();
			var userids = document.getElementById("ques.answerUserids");
			var val = "";
			var arr;
			var val_ = "";
			if(userids!=undefined){
				val = userids.value;
				if(val!=""){
					arr = val.split(",");
					for(var i=0;i<arr.length;i++){
						if(parseInt(arr[i]) != parseInt(userid)){
							val_ += arr[i]+",";
						}
					}
				}
			}
			if(val_!="" && val_.charAt(val_.length-1)==","){
				val_ = val_.substring(0,val_.lastIndexOf(","));
			}
			userids.value = val_;
		}
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 600;
				oFCKeditor.ReplaceTextarea();
				
			}
		function setCurTime(oid){
				var now=new Date();
				var year=now.getYear();
				var month=now.getMonth()+1;
				var day=now.getDate();
				var hours=now.getHours();
				var minutes=now.getMinutes();
				if(minutes<10){
					minutes="0"+minutes;
				}
				var seconds=now.getSeconds();
				if(seconds<10){
					seconds="0"+seconds;
				}
				var timeString = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
				var oCtl = document.getElementById(oid);
				oCtl.value = timeString;
			}
			function doSubmit(){
				var flag = true;
				var titleObj=document.getElementById("ques.name");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				var typeObj=document.getElementById("ques.answeringType.id");
				var type = typeObj.value;
				if(title==""){
					alert("问题标题不能为空!");
					titleObj.focus();
					flag =  false;
				}
				if(type==0){
					alert("请选择问题类别!");
					typeObj.focus();
					flag = false;
				}
				if(flag){
					addform.submit();
				}
			}
		</SCRIPT>
	</HEAD>
	<body onload="myload();">
		<!--网站头部-->
		<!--header begin-->

		<!--header end-->
		<!--wrap begin-->
		<div id="wrap">
			<!--top_a begin-->
			<!--top_a end-->
			<!--menu begin-->
			<table width="977" border="1" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 15px;">
				<tr>
					<td height="150" align="center">
						网站头部
					</td>
				</tr>
			</table>

			<!--EndMenu-->
			<div class="blank10 clear"></div>
			<div class="navigation">
				您当前位置：
				<a href="/">KesionCMS V9.x在线演示</a> &gt;
				<a href="/ask">问吧首页</a> &gt; 我要提问
			</div>
			<div class="blank10 clear"></div>
			<div class="askxqmain">
				<div class="left">
					<!--问题分类-->
					<div class="left01">
						<div class="title">
							<span></span>问题分类
						</div>
						<div class="left01box">
							<div class="askbox1">
								<ul>
									<li>
										问题总数：
										<span><s:property
												value="question_count.quesion_all_count" /> </span> 条
										<a target="_self"
											href="ques_index.action?m=1&status=0,1,2,3,4">查看</a>
									</li>
									<li>
										已解决问题数：
										<span><s:property
												value="question_count.quesion_has_finish" /> </span> 条
										<a target="_self" href="ques_index.action?m=3&status=3">查看</a>
									</li>
									<li>
										待解决问题数：
										<span><s:property
												value="question_count.question_need_finish" /> </span> 条
										<a target="_self" href="ques_index.action?m=2&status=0,1,2,4">查看</a>
									</li>
								</ul>
							</div>

							<div class="dotline clear"></div>

							<div class="askbox2">
								<dl>
									<wysLib:frontAnsweringTypeTags></wysLib:frontAnsweringTypeTags>
								</dl>
							</div>

						</div>
					</div>
					<!--问题分类-->
				</div>
				<div class="right">


					<!--相关提问-->
					<div class="clear blank10"></div>
					<div class="twbox">
						<!--提问-->
						<div class="title">
							<span></span>我要提问
						</div>
						<form name="addform" method="post" action="releaseQuestion.action">
							<input type="hidden" id="ques.answeringType.id"
								name="ques.answeringType.id" value="0" />
							<table width="100%" border="0" cellpadding="0" cellspacing="6"
								class="ask_box">
								<tr>
									<td width="15%" align="left">
										问题描述：
									</td>
									<td width="85%">
										<input type="text" size="75" name="ques.name" id="ques.name"
											value="" maxlength="150" />
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td align="left">
										所属类别：
									</td>
									<td>
										<s:textfield theme="simple" name="ques.answeringType.name"
											size="20" readonly="true" id="ques.answeringType.name" />
										<a href="#"
											onClick="searchAnsweringTypeTreeInit();return false;">点此进行选择</a>
									</td>
								</tr>
								<tr>
									<td align="left">
										有效期：
									</td>
									<td>
										<input class="Wdate" name="ques.validTime" readonly="readonly"
											type="text" onClick="setday(this)" id="validTime" />
										指定时间到后，问题将被关闭。
									</td>
								</tr>
								<tr>
									<td align="left">
										指定回答人：
									</td>
									<td>
										<a href="javascript:answeringUsersInit()" class="textbg">点此进行选择</a>
										<div id="users_div_1_1"></div>
										<input type="hidden" name="ques.answerUserids"
											id="ques.answerUserids" />
									</td>
								</tr>
								<tr>
									<td colspan="2" align="left">
										问题简介
										<span style="font-size: 14px; color: blue"><strong>注意：</strong>在下面编辑器中使用的图片宽度过大时，请自行调整，以免造成页面样式混乱！</span>
									</td>
								</tr>
							</table>
							<div style="text-align: left; width: 100%">
								<s:textarea name="ques.content" id="content" cols="60" rows="7"
									cssStyle="width: 980px; height: 440px;; visibility: hidden;" />
							</div>
							<div style="text-align: left;">
								<input type="image"
									src=" http://demo.kesion.com/images/tjwtbtn.gif"
									onclick="doSubmit();" />
							</div>
						</form>
						<!--提问end-->


					</div>




				</div>
			</div>

			<!--问答详情end-->
			<!--问答主体start-->
			<div class="clear blank10"></div>

			<!--版权信息-->
			<table width="977" border="1" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 15px;">
				<tr>
					<td height="150" align="center">
						网站底部
					</td>
				</tr>
			</table>
			<!--版权信息-->
		</div>
	
	</body>
</html>
<script src=" http://demo.kesion.com/ks_inc/ajax.js"
	type="text/javascript"></script>

