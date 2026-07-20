<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" />
		<title>中国食品安全培训网--练习答卷查看</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<meta content="MSHTML 6.00.2900.5921" name=GENERATOR />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/prac_view_1b1.js"></script>
		<script type="text/javascript" src="js/ajaxfileupload.js"></script>
		<style type="text/css">
body {
	margin: 0px;
	font-size: 12px;
	height: 100%;
	word-break: break-all;
	word-wrap: break-word
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

.ca_td {
	background: #ffccaa;
	width: 27px;
	height: 27px;
	text-align: center;
	vertical-align: middle;
}

.ca_td_yd {
	background: blue;
	width: 27px;
	height: 27px;
	text-align: center;
	vertical-align: middle;
}

.ca_td_cy {
	background: red;
	width: 27px;
	height: 27px;
	text-align: center;
	vertical-align: middle;
}

.ca_td_now {
	background: yellow;
	width: 27px;
	height: 27px;
	text-align: center;
	vertical-align: middle;
}

.ca_tb {
	display: block;
}

.ca_tb_block {
	height: 20px;
	font-size: 14px;;
	display: block;
}

.block {
	position: fixed;
	display: none;
}

.question {
	position: fixed;
	display: none;
}

.block_question_content {
	font-size: 15px;
	padding: 15px;
}

.block_name {
	font-size: 16px;
	font-weight: bolder;
	padding: 5px;
}

.block_desc {
	font-size: 13px;
	padding: 5px;
}

.startNewWindow {
	font-size: 13px;
	font-weight: bolder;
}
</style>
		<script type="text/javascript">
	var q_show_i=0;
	function myload(){
		$("#block_"+now_q.block.id).css("display","block");
		$("#question_"+now_q.block.id+"_"+now_q.id).css("display","block");
		$("#ca_td_"+now_q.block.id+"_"+now_q.id).attr("class","ca_td_now");
		ep.sortqs();
		/*quizpaper=new QuizPaper(<s:property value="examPaper.during"/>*60,
		<s:property value="myExamPaper.passTime"/>,"quizform","examtime","passTime");
		quizpaper.autosave();*/
	}
	function openNewWindowQ(obj,blockid,qid){
		//obj.href="quizquestioninit.action?examRoom.id=<s:property value="myExamPaper.examRoom.id"/>&examPaper.id=<s:property value="examPaper.id"/>&question.epblock.id="+blockid+"&question.id="+qid;
	}
</script>
	</HEAD>
	<body onload="myload();">
		<table cellspacing=0 style="height: 100%" cellpadding=0 width="100%"
			border=0>
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
													<td valign="middle" align="center" width=300
														background=images/img/bfz_r1_c11.jpg>
														<div style="font-size: 13;color:#ffffff">
															<span class="ca_td_cy" style="width:25px;height:25px;">&nbsp;</span> 未得分
															<span class="ca_td_yd" style="width:25px;height:25px;">&nbsp;</span> 有得分
															<span class="ca_td_now" style="width:25px;height:25px;">&nbsp;</span> 当前
														</div>
													</td>
													<td align="left" width="350" background=images/img/bfz_r1_c11.jpg >
														<font style="font-size:14px;">
														  姓名：<font color="red"><s:property value="#session.realname" /></font><br />
														  身份证号码：<font color="red"><s:property value="#session.shenfenzheng" /></font><br />
														  部门：<font color="red"><s:property value="#session.myDepName" /></font>
														</font>
													</td>
													<td align="left" background=images/img/bfz_r1_c11.jpg>
														<font class="bt"><s:property
																value="examPaper.title" /> </font>
														<font class="bt_"> </font>
													</td>
												</tr>
												<tr>
													<td background=images/img/t-5.jpg colspan=3 height=13>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td width=192>
										<table cellspacing=0 cellpadding=0 width=192 border=0>
											<tbody>
												<tr>
													<td width=192 height=47>
														<img height=47 src="images/img/t-6.jpg" width=192 />
													</td>
												</tr>
												<tr>
													<td width=192 background=images/img/t-7.jpg height=21>
														<table cellspacing=0 cellpadding=0 width=192 border=0>
															<tbody>
																<tr>
																	<td width=72 height=18>
																		&nbsp;

																	</td>
																	<td style="font-size: 12px" valign=bottom width=120>
																		<span class="style5">&gt;&gt;&gt; </span><a
																			href="study.action" target="_parent"><span
																			class="style5">返回个人中心</span> </a>
																	</td>
																</tr>
															</tbody>
														</table>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td valign=top>
						<table height="100%" bgcolor=#dae9fe cellspacing=0 cellpadding=0
							width="100%" border=0>
							<tbody>
								<tr>
									<td width="300" height="100%" valign="top" id="page_file"></td>
									<td width="10" background="images/img/bf_r12_c17.jpg">
										<a onclick="javascript:catalog_switch();"><img
												id=switch_button src="images/img/yincang.jpg" width="10"
												height="24" border="0"> </a>
									</td>
									<td width="100%" class="block_question_content" valign="top">
										<div style="height: 400px;">
											<wysLib:pracPaper1b1view/>
										</div>
										<a onclick="ep.showQP();return false;" href='' class="bt14"><img
												border=0 id="a_img_pre" src='images/img/preone.gif' /></a> &nbsp;&nbsp;&nbsp;&nbsp;
										<a onclick="ep.showQN();return false;" href='' class="bt14"><img
												border=0 id="a_img_next" src='images/img/nextone.gif' /></a>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td valign=top align="center" height=28>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td width=18>
										<img height=28 src="images/img/bf_r14_c1.jpg" width=18
											border=0 />
									</td>
									<td class="unnamed1" align="center" width=267
										background="images/img/bf_r14_c3.jpg">
										<span class="style3"> </span><span id="examtime"
											class="style3"> </span>
									</td>
									<td width=48>
										<img height=28 src="images/img/bf_r14_c15.jpg" width=48
											border=0 />
									</td>
									<td valign="middle" align=right
										background=images/img/bf_r14_c21.jpg>
										&nbsp;
									</td>
									<td width=17>
										<img height=28 src="images/img/bf_r14_c29.jpg" width=19
											border=0 />
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	
	</body>
</html>
