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
		<title>问答系统首页</title>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta content="" name=keywords>
		<meta content="" name=description>
		<link href="http://demo.kesion.com/images/style.css" type=text/css
			rel=stylesheet>
		<script src="http://demo.kesion.com/ks_inc/jquery.js"
			type="text/javascript"></script>
		<script src="http://demo.kesion.com/ks_inc/common.js"
			type="text/javascript"></script>
		<!-- 
<SCRIPT type=text/javascript>
jQuery(document).ready(function(){
	var SDmodel = new scrollDoor();
	SDmodel.sd(["m01","m02","m03","m04","m05","m06","m07"],["c01","c02","c03","c04","c05","c06","c07"],"sd01","sd02");
	SDmodel.sd(["m08","m09","m10","m11","m12","m13","m14"],["c08","c09","c10","c11","c12","c13","c14"],"sd01","sd02");
})
</SCRIPT>
 -->
	</HEAD>
	<body>
		<div id="wrap">
			<table width="977" border="1" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 15px;">
				<tr>
					<td height="150" align="center">
						网站头部
					</td>
				</tr>
			</table>

			<div class="askmain">
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
												value="question_count.quesion_all_count" />
										</span> 条
										<a target="_self" href="ques_index.action?m=1&status=0,1,2,3,4">查看</a>
									</li>
									<li>
										已解决问题数：
										<span><s:property
												value="question_count.quesion_has_finish" />
										</span> 条
										<a target="_self" href="ques_index.action?m=3&status=3">查看</a>
									</li>
									<li>
										待解决问题数：
										<span><s:property
												value="question_count.question_need_finish" />
										</span> 条
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
					<div class="clear blank10"></div>
					<div class="left02">
						<ul>
							想要分享你的博学多识， 想要收藏这里的点滴知识， 那就赶快登录吧~
						</ul>
					</div>



				</div>
				<div class="middle">
					<div class="middle01">
						<div class="title">
							<span></span>精彩话题
						</div>
						<div class="middle01box">
							<div class="focus">
								<img src="http://demo.kesion.com/images/ask.gif" alt="问答" />
							</div>
							<div class="ask_topic">
								<ul>
									<s:if test="listMap.queses.size()!=0">
										<s:iterator value="listMap.queses">
											<li>
												<a href="ques_index.action?ansType.id=<s:property value="answeringType.id" />"><s:property value="answeringType.name" />
												</a>|
												<a href="ques_index_view.action?ques.id=<s:property value="id" />"><s:property value="name" />
												</a>
											</li>
										</s:iterator>
									</s:if>
									<s:else>
					    		暂无推荐问题
					    	</s:else>
								</ul>
							</div>
						</div>
					</div>

					<div class="clear blank10"></div>
					<div class="middle02">
						<div class="title">
							<span></span>最新提问
						</div>
						<!--鼠标切换-->
						<wysLib:newestQuesesTags></wysLib:newestQuesesTags>
						<!--鼠标切换-->
					</div>
					<div class="clear blank10"></div>
					<div class="middle02">
						<div class="title">
							<span></span>最新回答
						</div>
						<!--鼠标切换-->
						<wysLib:newestAnswersTags></wysLib:newestAnswersTags>
						<!--鼠标切换-->
					</div>
				</div>
				<div class="right">
					<div class="right02">
						<div class="title">
							<span></span>网站公告
						</div>
						<div class="right02box">
							<ul>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<s:if test="listMap.newses.size()!=0">
										<s:iterator value="listMap.newses">
											<tr>
												<td>
													<a href="newsIndexView.action?news.id=<s:property value="id" />" title="<s:property value="title" />"
														target="_self"> 
														<s:if test="title.length()>=13">
															<s:property value="title.substring(0,13)" />...
														</s:if>
														<s:else>
															<s:property value="title" />
														</s:else>
													</a>
												</td>
											</tr>
										</s:iterator>
									</s:if>
									<s:else>
										暂无新闻公告
									</s:else>
								</table>

							</ul>
						</div>
					</div>

					<div class="clear blank10"></div>






				</div>
			</div>
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
<script src="http://demo.kesion.com/ks_inc/ajax.js"
	type="text/javascript"></script>
<!-- published at 2013-5-28 10:25:26 publish by KesionCMS V9.03 -->

