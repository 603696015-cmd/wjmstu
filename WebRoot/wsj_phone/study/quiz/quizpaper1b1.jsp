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
<html>	<head>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta name="MobileOptimized" content="320">
		<base href="<%=basePath%>" target="_self" />
		<META http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>网络考试系统</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<meta content="MSHTML 6.00.2900.5921" name=GENERATOR />
		<link rel="stylesheet" type="text/css" href="css/buttonStyle.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/quiz_1b1.js"></script>
		<script type="text/javascript" src="js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="/wsj_phone/js/quiz_1b1_timer.js"></script>
		<style type="text/css">
body {
	margin: 0px;
	font-size: 12px;
	height: 100%;
	word-break: break-all;
	word-wrap: break-word;
	background-color: #DAE9FE;
}

.unnamed1 {
	font-size: 12px;
	line-height: 24px;
	font-family: "宋体"
}

.style5 {
	color: #ff0000;
}
.style3 {
	color: #ff00dd;
	font-size: 14px;
}
.style31 {
	color: #ff1111;
	font-size: 14px;
}
.bt {
	font-size: 18px;
	color: #ffffff;
	line-height: 26px;
	font-family: "黑体"
}
.bt_ {
	font-size: 13px;
	color: #ffffff;
	line-height: 26px;
	font-family: "黑体"
}
.ca_td{
	background:#9FC7FD;
	width:27px;
	height:27px;
	text-align:center;
	vertical-align: middle;
	cursor: pointer;
}
.ca_td_d{
	background:#9FC7FD;
	width:27px;
	height:27px;
	text-align:center;
	vertical-align: middle;
	cursor: default;
}
.ca_td_yd{
	background:#3EB97B;
	width:27px;
	height:27px;
	text-align:center;
	vertical-align: middle;
	cursor: pointer;
}
.ca_td_cy{
	background:#FF6060;
	width:27px;
	height:27px;
	text-align:center;
	vertical-align: middle;
	cursor: pointer;
	
}
.ca_td_now{
	background:#FFFF80;
	width:27px;
	height:27px;
	text-align:center;
	vertical-align: middle;
	cursor: pointer;
}
.ca_tb{
	display: block;
}
.ca_tb_block{
	height:20px;
	font-size: 14px;;
	display: block;
}
.block {
	position:fixed;
	display: none;	
}
.question {
	position:fixed;
	display: none;	
}
.block_question_content{
	font-size: 15px;
	padding: 15px;
}
.block_name {
	font-size: 16px;
	font-weight: bolder;
	padding:5px;
}
.block_desc {
	font-size: 13px;
	padding: 5px;
}
.block_loading{
	height:100px;
	padding-top:30px;
	display: none;
}
.startNewWindow {
	font-size: 13px;
	font-weight: bolder;
}
</style>
<script type="text/javascript">
	var sqid_ = <s:property value="myExamPaper.id"/>;
	var quizpaper ;
	var qtimer ;
	function myload(){
		$("#load_img").css("display","none");
		qtimer = new QuizTimer('11',"examtime",<s:property value="examPaper.during"/>*60,<s:property value="myExamPaper.jiashi"/>*60,<s:property value="myExamPaper.passTime"/>,400,140);
		quizpaper=new QuizPaper("quizform","examtime","" ,qtimer);
		quizpaper.autosave();
		q_show(1,1);
	}
	function qsubmit(){
		var tds = $("#page_file").find('td');
		var cy=0;yd=0,wdt=0;
		for(var i = 0 ; i <tds.length;i++){
			if($(tds[i]).attr("opstatus")==2) 
				cy++;
			if($(tds[i]).attr("opstatus")==1)
				yd++;
			if($(tds[i]).attr("opstatus")==0)
				wdt++;
		}
		if(window.confirm("未答试题有"+wdt+"道，存疑："+cy+"道题，确定提交")){
			quizpaper.submit();
		}
	}
	window.onbeforeunload =function(){
		var tds = $("#page_file").find('td');
		var cy=0;yd=0,wdt=0;
		for(var i = 0 ; i <tds.length;i++){
			if($(tds[i]).attr("opstatus")==2) 
				cy++;
			if($(tds[i]).attr("opstatus")==1)
				yd++;
			if($(tds[i]).attr("opstatus")==0)
				wdt++;
		}
		window.event.returnValue="未答试题有"+wdt+"道，存疑："+cy+"道题，确定提交？";  
    }
    window.onunload = function (){
   		quizpaper.submit();
	}
	function closeFrame(){
		$("#oprate_question").css("display","none");
		$("#oprate_question").attr("src","blank.html");
	}
	
</script>
	</HEAD>
	<body onLoad="myload()">
		<div id="load_img" style="position:absolute;background:#fff;left:20px; top:100px;text-align: center;font-size: 13px;font-weight: bolder;">
	 		<div style="border: 2 solid green;width:200px;height:100px;padding-top:30px"><img src="images/loading.gif"/>&nbsp;&nbsp;数据加载中,请等待...</div>
	 	</div>
		<table cellspacing=0 style="height: 100%" cellpadding=0 width="100%" border=0>
			<tbody>
				<tr>
					<td valign=top height=68>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td>
										<table height=68 cellspacing=0 cellpadding=0 width="100%"
											border=0>
											<tbody>
												<tr>
												  <td width="100" align="center" valign="middle" background=images/img/bfz_r1_c11.jpg ><a onClick="qsubmit();" style="cursor: pointer;"><img
																	style="height:30px;" src='images/img/jiaojuan.gif' border="0" /></a></td>
													
													<td align="left" valign="middle" background=images/img/bfz_r1_c11.jpg >
														
														 <s:property value="#session.realname" />&nbsp;&nbsp;
													    <!--身份证号码：<font color="red"><s:property value="#session.shenfenzheng" /></font>
													    部门：<font color="red"><s:property value="#session.myDepName" /></font>	-->																									<a onClick="showQP();return false;" href="javascript:void(-1)" class="bt14"><img border=0 id="a_img_pre" src='images/img/preone.gif'/></a> &nbsp;&nbsp;
								                  <a onClick="showQN();return false;" href="javascript:void(-1)" class="bt14"><img border=0 id="a_img_next" src='images/img/nextone.gif'/></a></td>
											    </tr>
												<tr>
												  <td colspan="2" align="center" valign="middle" background=images/img/bfz_r1_c11.jpg >
												  <font class="bt"><s:property
																value="myExamPaper.examRoom.title" /><!--(<s:property
																value="examPaper.title" />)--> </font>
														<font class="bt_"></font>												  </td>
											  </tr>
												<tr>
													<td background=images/img/t-5.jpg colspan=2 height=13>													</td>
												</tr>
											</tbody>
										</table>									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td valign=top>
						<form action="quizpaper_submit.action" method="post" name="quizform" id="quizform">
							<s:hidden name="myExamPaper.id"></s:hidden>
							<s:hidden name="examPaper.id"></s:hidden>
							<s:hidden name="myExamPaper.examPaper.stuview"></s:hidden>
							<s:hidden name="recordId"></s:hidden>
							<s:hidden name="myExamPaper.passTime" id="meppasstime"/>
							<s:hidden name="lastqids" id="lastqids"></s:hidden>
							<table height="100%" bgcolor=#dae9fe cellspacing=0 cellpadding=0
								width="100%" border=0>
								<tbody>
									<tr>
										<td width="300px" height="100%" valign="top" id="page_file">
											<SCRIPT type="text/javascript">maxbsort=<s:property value="examPaper.epBlocks.size"/></SCRIPT>
											<s:iterator value="examPaper.epBlocks" status="epb_st">
												<table class="ca_tb" width="0px" blockid="<s:property value="id"/>" questionmount="<s:property value="questions.size"/>" id="ca_block_<s:property value="sortid"/>">
													<tbody>
														<!--<tr><td class="ca_tb_block" colspan="10">第<s:property value="sortid"/>大题：<s:property value="title"/></td></tr>-->
														<s:set name="row_" value="1" />
														<s:iterator begin="1" end="row">
															<tr><s:iterator begin="1" end="10">
																		<!--<s:if test="#row_<=questions.size"><td qid="<s:property value="questions[#row_-1].id"/>" qtype="<s:property value="questions[#row_-1].qtype"/>" opstatus="<s:property value="questions[#row_-1].opstatus"/>" class="<s:if test="questions[#row_-1].opstatus==1">ca_td_yd</s:if><s:elseif test="questions[#row_-1].opstatus==2">ca_td_cy</s:elseif><s:else>ca_td</s:else>" old-class="<s:if test="questions[#row_-1].opstatus==1">ca_td_yd</s:if><s:elseif test="questions[#row_-1].opstatus==2">ca_td_cy</s:elseif><s:else>ca_td</s:else>" id="ca_question_<s:property value="sortid"/>_<s:property value="questions[#row_-1].sortid"/>"
																		 onclick="q_show(<s:property value="sortid"/>,<s:property value="questions[#row_-1].sortid"/>)" title="<s:property value="id"/>_<s:property value="questions[#row_-1].id"/>"><s:property value="questions[#row_-1].sortid"/></td></s:if>
																		<s:else><td class="ca_td_d">&nbsp;</td>
																		</s:else>-->
																		<s:set name="row_" value="#row_+1" />
																</s:iterator>
															</tr>
														</s:iterator>
													</tbody>
												</table>
											</s:iterator>
										</td>
										<td width="10" background="images/img/bf_r12_c17.jpg">
											<a onClick="javascript:catalog_switch();"><img
													id=switch_button src="images/img/yincang.jpg" width="10"
													height="24" border="0"> </a>
										</td>
										<td width="100%" class="block_question_content" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td align="left" valign="top"><s:iterator value="examPaper.epBlocks">
												<div id="block_<s:property value="id"/>" class="block">
													<div class="block_name">第<s:property value="sortid"/>大题:<s:property value="title"/></div>
													<div class="block_desc">大题说明：<s:property value="description"/></div>
													<div class="block_loading" id="loading_<s:property value="id"/>"><img src="images/loading.gif"/>&nbsp;&nbsp;数据加载中,请等待...</div>
												</div>
										</s:iterator></td>
  </tr>
</table>

<!--如果是IE浏览器，则操作按纽放在这个位置-->

<!--
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="120" align="center" valign="middle"><a onClick="showQP();return false;" href="javascript:void(-1)" class="bt14"><img style="height:25px;" border=0 id="a_img_pre" src='images/img/preone.gif'/></a>			    </td>
    <td width="120" align="center" valign="middle"> <a onClick="showQN();return false;" href="javascript:void(-1)" class="bt14"><img style="height:25px;" border=0 id="a_img_next" src='images/img/nextone.gif'/></a> </td>
    <td width="120" align="center" valign="middle"><a href="javascript:void(-1);"
																onclick="q_cy(); return false;"><img
																	style="height:25px;" src='images/img/cunyi.gif' border="0" /></a></td>
    <td>&nbsp;</td>
  </tr>
</table>

-->

										<br/><br/><br/>
										 
</td>
									</tr>
								</tbody>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td valign=top align="center" height=28>
						<!--<table cellspacing=0 cellpadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td class="unnamed1" align="center" width=267
										background="images/img/bf_r14_c3.jpg"> <a onClick="showQP();return false;" href="javascript:void(-1)" class="bt14"><img border=0 id="a_img_pre" src='images/img/preone.gif'/></a>
										 &nbsp;&nbsp;&nbsp;&nbsp;
										 <a onClick="showQN();return false;" href="javascript:void(-1)" class="bt14"><img border=0 id="a_img_next" src='images/img/nextone.gif'/></a> </td>
									<td valign="middle" align=right
										background=images/img/bf_r14_c21.jpg>-->
					 <!--如果是谷歌或火狐浏览器，则按纽显示在这个位置--></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<iframe width="100%" height="100%" id="oprate_question" frameborder="0" src="blank.html" style="z-index:1000;position: absolute;left:0px;top:0px;display: none;"></iframe>
	</body>
</html>
