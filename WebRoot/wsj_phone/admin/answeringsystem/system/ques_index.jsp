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
		<title>问题列表页</title>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta content="" name=keywords>
		<meta content="" name=description>
		<link href=" http://demo.kesion.com//images/style.css" type=text/css
			rel=stylesheet>
		<script src="js/jquery.js" type="text/javascript"></script>
		<script src=" http://demo.kesion.com//ks_inc/common.js"
			type="text/javascript"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value = i;
				listQues.submit();
			}
			
			function click_change(value,obj,m){
				document.getElementById("status").value = value;
				document.getElementById("m").value = m;
				listQues.submit();
			}
			function myload(){
				if(<s:property value='m' /> == 1){
					$("#m1").attr("class","curr");
					$("#m2").attr("class","normal");
					$("#m3").attr("class","normal");
				}else if(<s:property value='m' /> == 2){
					$("#m2").attr("class","curr");
					$("#m1").attr("class","normal");
					$("#m3").attr("class","normal");
				}else if(<s:property value='m' /> == 3){
					$("#m3").attr("class","curr");
					$("#m1").attr("class","normal");
					$("#m2").attr("class","normal");
				}
			}
		</script>
	</HEAD>
	<body onload="myload();">
		<form action="ques_index.action" name="listQues" method="post">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="status" id="status" />
				<s:hidden name="m" />
				<s:hidden name="ansType.id" id="ansType.id" />
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

			<!--EndMenu-->
			<div class="blank10 clear"></div>
			<div class="navigation">
				<span> <script>
					  check=function(){
					   if (document.getElementById('KeyWord').value=='')
					   {
						 alert("对不起,您没有输入关键字!");
						 return false
					   }
					  }
					  goask=function(){
					   if (document.getElementById('KeyWord').value=='')
					   {
						 alert("对不起,您没有输入关键字!");
						 return false
					   }else{
						window.open('a.asp?q='+document.getElementById('KeyWord').value);
						return false;
					   }
					   
					  }
				</script>
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
				</span>您当前位置：
				<a href="/">KesionCMS V9.x在线演示</a> &gt;
				<a href="/ask">问吧首页</a> &gt; &gt;
				<a href="showlist.asp?id=1">新房置业</a>
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
				</div>
				<div class="right">
					<div class="right01">
						<div class="tit">
							<span>本类共有问题 <font color=red><s:property value="ansType.hasTotalCount" /></font> 条</span><s:property value="ansType.name" />
						</div>
						<div class="right01box">
							<s:if test="ansType.child.size!=0">
								<ul>
									<s:iterator value="ansType.child">
										<li>
											<a href="ques_index.action?ansType.id=<s:property value="id" />"><s:property value="name" />(<s:property value="hasTotalCount" />)</a>
										</li>
									</s:iterator>
								</ul>
							</s:if>
							<s:else>
								暂无子类别
							</s:else>
						</div>
					</div>
					<div class="blank10 clear"></div>

					<!--鼠标切换-->
					<div class="asklist">
						<table cellSpacing=0 cellPadding=0 width="100%" align=center
							border=0>
								<tr>
									<td colspan="5" class="tit">
										<li class=curr id="m1">
											<a href="javascript:click_change('0,1,2,3,4',this,1);">全部问题</a>
										<li class=normal id="m2">
											<a href="javascript:click_change('0,1,2,4',this,2);">待解决问题</a>
										</li>
										<li class=normal id="m3">
											<a href="javascript:click_change('3',this,3);">已解决</a>
										</li>
									</td>
								</tr>
							<s:if test="queses.size()>0">
								 <tr class="asktopic">
				                     <td align=middle width=211 height="28">标&nbsp;题</td>
				                     <td align=middle width=108>回答数</td>
				                     <td align=middle width=56>状&nbsp;态</td>
				                     <td align=middle width=121>提问人</td>
				                     <td align=middle width=93 >提问时间</td>
				                  </tr>
								
								<s:iterator value="queses">
									<tr>
										<td class="topic">
											<a target="_self" href="ques_index_view.action?ques.id=<s:property value="id" />" target="_blank"><s:property value="name" /></a>
										</td>
										<td class="topic" align=middle>
											<s:property value="answerCount" />
										</td>
										<td class="topic" align=middle>
											 <s:property value="status_" />
										</td>
										<td class="topic" align=middle>
											<s:property value="fabuUser.realname" />
										</td>
										<td class="topic" align=left>
											<s:date name="fabuTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
									</tr>
								</s:iterator>
							</s:if>
						</table>
						<br />
						<wysLib:page></wysLib:page>

					</div>
					<!--鼠标切换-->
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
<script src=" http://demo.kesion.com//ks_inc/ajax.js"
	type="text/javascript"></script>
<!-- published at 2013-5-28 10:50:34 publish by KesionCMS V9.03 -->

