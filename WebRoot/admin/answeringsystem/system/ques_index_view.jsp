<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<title>问吧-在北京第一次置�?如何选择.是在市里买二手的还是在郊区买新房</title>
		<meta http-equiv=Content-Type content="text/html; charset=gb2312">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta content="" name=keywords>
		<meta content="" name=description>
		<link href="http://demo.kesion.com//images/style.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script src=" http://demo.kesion.com//ks_inc/common.js" type="text/javascript"></script>
		<script src='/ks_inc/kesion.box.js' language="JavaScript"></script>
		<script src=" http://demo.kesion.com//ks_inc/ajax.js" type="text/javascript"></script>
		<script language="javascript">
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 720;
				oFCKeditor.ReplaceTextarea();
		}
		</script>
	</HEAD>
	<body onload="myload();">
		<form action="ques_index_view.action" name="listAnswers" method="post">
			<s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
		</form>
		<div id="wrap">
			<table width="977" border="1" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 15px;">
				<tr>
					<td height="150" align="center">
						网站头部
					</td>
				</tr>
			</table>

			<div class="blank10 clear"></div>
			<div class="navigation">
				<span> 
					<form action="search.asp" method="post" name="myform" id="myform">
						<dt>
							问题搜索
							<input type="text" name="KeyWord" id="KeyWord" size="20" />
							<input type="image" src=" http://demo.kesion.com//images/sda.gif"
								onClick="return(check())" align="absmiddle" />
							<input type="image"
								src=" http://demo.kesion.com//images/wstw.gif"
								onClick="return(goask())" align="absmiddle" />
						</dt>
					</form>
				</span> 您当前位置：
				<a href="/">KesionCMS V9.x在线演示</a> &gt;
				<a href="/ask">问吧首页</a> &gt;
				<a href="showlist.asp?id=1">新房置业</a> &gt;
				<a href="showlist.asp?id=12">购房常识</a> &gt; 查看问题
			</div>
			<div class="blank10 clear"></div>

			<!--问答详情start-->
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
										问题总数�?
										<span><s:property
												value="question_count.quesion_all_count" />
										</span> �?
										<a href="all.asp">查看</a>
									</li>
									<li>
										已解决问题数�?
										<span><s:property
												value="question_count.quesion_has_finish" />
										</span> �?
										<a href="all.asp?m=2">查看</a>
									</li>
									<li>
										待解决问题数�?
										<span><s:property
												value="question_count.question_need_finish" />
										</span> �?
										<a href="all.asp?m=1">查看</a>
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
				</div>
				<div class="right">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="asktitle">
									<tr>
										<td>
											<span id="fav">
											</span><span class="icon"></span>提问问题
										</td>
									</tr>
								</table>

								<table width="100%" border="0" cellpadding="5" cellspacing="1"
									bgcolor="#C5E8FC">
									<tr>
										<td valign="top" bgcolor="#FFFFFF">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="100" align="center" class="userface">
														<br />
														<a href="../space/?218" target="_blank">
														</a>
														<br />
														<span class="name"> <s:property
																value="ques.fabuUser.realname" />
														</span>
														<br />
														<s:property value="ques.fabuUser.role.name" />
													</td>
													<td valign="top">

														<table width="100%" border="0" cellspacing="5"
															cellpadding="5">
															<tr>
																<td>
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td colspan="2">
																				<span class="btn1"> </span>
																				<span class="topictime">发表时间�?s:date
																						name="ques.fabuTime" format="yyyy-MM-dd HH:mm:ss" />
																				</span>
																				 浏览次数�?
																				<span class="f1"><s:property
																						value="ques.viewCount" />
																				</span> �?
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="topictitle"><s:property
																						value="ques.name" />
																				</span>
																			</td>
																			<td width="100" rowspan="2" align="center"
																				style="padding-top: 20px;">
																				
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td class="ms">
																	问题描述�?
																</td>
															</tr>
															<tr>
																<td>
																	<div class="topiccontent">
																		${ques.content }
																	</div>
																</td>
															</tr>

															
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<br />


								<table width="100%" border="0" cellpadding="5" cellspacing="0">
									<tr>
										<td valign="top">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="asktitle">
												<tr>
													<td>
														<span class="icon"></span>网友回答
													</td>
												</tr>
											</table>
											<s:if test="ques.answers.size()!=0">
												<table width="100%" border="0" cellspacing="0"
													style="border: #C5E8FC 1px solid;">
													<s:iterator value="ques.answers">
														<tr>
															<td width="100" align="center" bgcolor="#F6F9FC"
																class="userface">
																<br />
																<a href="../space/?218" target="_blank"><img name=""
																		src="http://demo.kesion.com/uploadfiles/user/avatar/218.jpg"
																		onerror="this.src=' http://demo.kesion.com /user/images/noavatar_small.gif';"
																		width="62" height="62" alt="<s:property value="answerUser.realname" />" />
																</a>
																<br />
																<span class="name"><s:property value="answerUser.realname" /></span>
																<br />
																<s:property value="answerUser.role.name" />
															</td>
															<td valign="top">
																<table width="100%" border="0" cellspacing="5"
																	cellpadding="5" bgcolor="#ffffff"
																	style="height: 100%; overflow: hidden;">
																	<tr>
																		<td>
																			<span class="topictime">回答时间�?s:date name="answerTime" format="yyyy-MM-dd HH:mm:ss" /></span>
																		</td>
		
																	</tr>
																	<tr>
																		<td class="topiccontent">
																			<p>
																				${answerContent }
																			</p>
																			<br />
		
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</s:iterator>
												</table>
											</s:if>
											<s:else>
											  	暂无回答
											</s:else>
										</td>
									</tr>
								</table>
								<br />

								
								<wysLib:page></wysLib:page>
								
								<br />
							</td>
						</tr>
					</table>
					<div style="text-align: left; width: 100%">
						<form action="addAnswer.action" name="addAnswerForm" method="post">
							<s:textarea  name="answer.answerContent" id="content" cols="60" rows="7"
								cssStyle="width: 980px; height: 440px;; visibility: hidden;" />
							<input type="hidden" name="answer.questionId" value="<s:property value='ques.id' />" />
							<input type="image" src="images/tihf.jpg"  onclick="document.addAnswerForm.submit();" />
						</form>
					</div>
					
				</div>
			</div>

			<div class="clear blank10"></div>

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



