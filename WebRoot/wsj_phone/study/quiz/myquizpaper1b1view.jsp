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
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" />
		<title>中国食品安全培训网</title>
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
		//quizpaper=new QuizPaper(<s:property value="examPaper.during"/>*60,
		//<s:property value="myExamPaper.passTime"/>,"quizform","examtime","passTime");
		//quizpaper.autosave();
		//改变打字题颜色
		//var dazicontentArray=$("div[name='dazicontent']");
		//var mydazicontentArray=$("div[name='mydazicontent']");
		//alert($(dazicontentArray[0]).html());
		//for(var i=0;i<dazicontentArray.length;i++){
		//	if(!($(mydazicontentArray[i]).html()=="该题未作答")){
		//		updateDaziColor($(dazicontentArray[i]).html(),$(mydazicontentArray[i]).html());
		//	}
		//}
	}
	function openNewWindowQ(obj,blockid,qid){
		obj.href="quizquestioninit.action?examRoom.id=<s:property value="myExamPaper.examRoom.id"/>&examPaper.id=<s:property value="examPaper.id"/>&question.epblock.id="+blockid+"&question.id="+qid;
	}
</script>
	</HEAD>
	<body onLoad="myload();" oncopy="return false;" oncut="return false;">
	
		<table cellspacing=0  cellpadding=0 width="320"
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
													<td valign="middle" align="left" width=350>
														<font style="font-size:14px;">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
  <tr>
    <td bgcolor="#F8FCFE">姓名：<font color="red"><s:property value="#session.realname" /></font></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">身份证号码：<font color="red"><s:property value="#session.shenfenzheng" /></font></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">部门：<font color="red"><s:property value="#session.myDepName" /></font></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE"><span class="style5">&gt;&gt;&gt; </span><a href="study.action" target="_parent"><span
																			class="style5">返回个人中心</span> </a></td>
  </tr>
                                                        </table>

														  
														</font>
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
						<table height="100%" bgcolor=#D1E4F5 cellspacing=1 cellpadding=0
							width="320" border=0>
							<tbody>
								<tr>

									<td width="100%" valign="top" bgcolor="#F8FCFE" class="block_question_content">
										<wysLib:quizPaper1b1view />
										<br />
										<a onClick="ep.showQP();return false;" href='' class="bt14"><img
												border=0 id="a_img_pre" src='images/img/preone.gif' />
										</a> &nbsp;&nbsp;&nbsp;&nbsp;
										<a onClick="ep.showQN();return false;" href='' class="bt14"><img
												border=0 id="a_img_next" src='images/img/nextone.gif' />
										</a> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td valign=top align="center" height=28>&nbsp;</td>
				</tr>
			</tbody>
		</table>
	
	</body>
</html>
