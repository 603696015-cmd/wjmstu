<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	out.print("<input type='hidden' id='urlHead' value='"
			+ request.getScheme() + "' />");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中国食品安全培训网</title>
		<base href="<%=basePath%>" />
		<META content="MSHTML 6.00.2900.5897" name=GENERATOR>
		<LINK href="elfrontimages/style__.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<style type="text/css">
body {
	background-color: #FFFFFF;
}

.STYLE2 {
	color: #FF0000
}

.textbox {
	border: solid 1 #000000;
}

#divNews {
	position: absolute;
	border: 1px solid;
	background-color: white;
	left: 0px;
	top: 0px;
	text-align: center;
}
        </style>
		<script type="text/javascript">
	function divNone(){
		document.getElementById("divNews").style.display='none';
	}
	function isregister(){
		alert('系统关闭了注册功能，请与管理员联系！');
	}
	function init(){
		//alert(document.getElementById("urlHead").value);
		if(document.getElementById("urlHead").value=="https"){
			var urlPath=document.getElementById("urlPath").value;
			//alert(urlPath);//
			//document.location.href="http"+urlPath;
			document.location.href=urlPath;
		}
	}
	function pkilogin(){
		//alert('<s:property value="httpsPath"/>'+"loginpki.action");
		var path='<s:property value="httpsPath"/>'+"loginpki.action?m="+Math.random();
		myform.action=path;
		myform.submit();
	}
</script>
	</HEAD>
	<s:if test="#request.newspop.id>0">
		<div id="divNews" class="tanchuang">



			<table width="100%" border="0" align="center">
				<tr>
					<td height="25" colspan="2" align="center" valign="middle"
						bgcolor="#DFF7F4">
						<!--新闻标题：-->
						<a target="blank"
							href="newsIndexView.action?news.id=<s:property value="#request.newspop.id"/>"><span
							class="bttanchuang"> <s:property
									value="#request.newspop.title" />
						</span>
						</a>
					</td>
				</tr>
				<tr>
					<td height="20" colspan="2" align="center" bgcolor="#F3FAFA">
						<div
							style="overflow: auto; width: 300px; height: 220px; text-align: left; line-height: 20px; padding: 5px;">
							${requestScope.newspop.content_ }						</div>
					</td>
				</tr>

				<tr>
					<td height="25" colspan="2" align="right" valign="middle"
						bgcolor="#DFF7F4">
						<table width="100%" height="25" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td align="right" valign="middle" style="padding-right: 15px;">
									<a target="blank"
										href="newsIndexView.action?news.id=<s:property value="#request.newspop.id"/>"><span
										style="color: blue;">查看详情</span>
									</a>								</td>
								<td width="25" align="center" bgcolor="#EEEEEE">
									<a href="javascript:divNone();" class="bg001"><span
										style="font-size: 15px; color: #FF0000">X</span>									</a>								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<!--发布时间：<s:date name="#request.newspop.releasetime" format="yyyy-MM-dd"/><br/>
				新闻内容：-->



		</div>



	</s:if>
	<BODY onload="init();">
		<s:hidden name="httpPath" id="urlPath" />
		<%@include file="frontheader.jsp"%>
		<table width="960" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-top: 8px; margin-bottom: 5px;">
			<tr>
				<td width="310" valign="top">
					<SCRIPT src="elfrontimages/sohuflash_1.js" type=text/javascript></SCRIPT> 
					<DIV id=flashcontent01></DIV> 
					<SCRIPT type=text/javascript>
					var focus_width=310;
					var focus_height=197;
					var text_height=0;
					//var pics='http://demo.kesion.com/UploadFiles/2011-08/admin/20110819170448734.jpg|http://img1.gtimg.com/news/pics/hv1/65/179/837/54471635.jpg|http://img1.gtimg.com/news/pics/hv1/95/253/836/54425510.jpg|http://demo.kesion.com/UploadFiles/2011-08/admin/20110811170446229.jpg|http://demo.kesion.com/UploadFiles/2011-08/admin/20110811170442326.jpg';
					//var links=escape('/Item/Show.asp?m=1&d=993|/Item/Show.asp?m=1&d=938|/Item/Show.asp?m=1&d=936|/Item/Show.asp?m=1&d=934|/Item/Show.asp?m=1&d=927');
					//var texts='广州16万立方米重金属污染土|韩电视台天气预报主持人着装|卡扎菲儿子现身澄清被炸死传|辽宁部分地区发生皮肤炭疽传|江西吉安发生大面积山体滑坡';
					var speed = 4000;
					var pics="";
					var links="";
					var texts="";
					<s:iterator value="zxNews">
					pics += "<s:property value="mainimg_" escape="false"/>|";
					links += "newsIndexView.action?news.id=<s:property value="id"/>|";
					texts += "<s:property value="title"/>|";
					</s:iterator>	
					pics= pics.substring(0,pics.length-1);
					links= links.substring(0,links.length-1);
					texts= texts.substring(0,texts.length-1);
					var sohuFlash2 = new sohuFlash("elfrontimages/focus0414a.swf","flashcontent01",focus_width,focus_height+text_height,'pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height,"#ffffff");
					sohuFlash2.addParam("quality", "medium");
					sohuFlash2.addParam("wmode", "opaque");
					sohuFlash2.addVariable("speed",speed);
					sohuFlash2.addVariable("p",pics);	
					sohuFlash2.addVariable("l",links);
					sohuFlash2.addVariable("icon",texts);
					sohuFlash2.write("flashcontent01");
					</SCRIPT> 
					

					<SCRIPT language=JavaScript> 
					function setTab(name,cursel,n){
						for(i=1;i<=n;i++){
						   var menu=document.getElementById(name+i);
						   var con=document.getElementById("con_"+name+"_"+i);
						   menu.className=i==cursel?"hover":"";
						   con.style.display=i==cursel?"block":"none";
						}
					}
					
var focus_width=310;
var focus_height=197;
var text_height=0;
//var pics='images/icon_1.gif|images/icon_2.gif|images/icon_3.gif|images/2010042709434050398.jpg|images/2010042709420423947.jpg|images/2010042709405773981.jpg';
//var links='/jrnd/ndyw/31775.html|/jrnd/ndyw/31774.html|/jrnd/bmdt/31759.html|/jrnd/ndyw/31688.html|/jrnd/xsdt/31687.html|/jrnd/xsdt/31686.html';
//var texts='电博会户外宣传渐入高潮|宁德市中小学幼儿园拉起“安保大闸”|宁德开展家庭小药箱及过期药品清理回收活动|为玉树灾区筹集善款|屏南百万尾鱼苗放流增殖|“一环、一纵、三横”路网大框架托起东侨新';
var pics="";
var links="";
var texts="";
<s:iterator value="zxNews">
pics += "<s:property value="mainimg_" escape="false"/>|";
links += "newsIndexView.action?news.id=<s:property value="id"/>|";
texts += "<s:property value="title"/>|";
</s:iterator>	
pics= pics.substring(0,pics.length-1);
links= links.substring(0,links.length-1);
texts= texts.substring(0,texts.length-1);
LoadFlash('elfrontimages/Slideviewer.swf','transparent',focus_width,focus_height+text_height,'pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height)
</SCRIPT>
				</td>

				<td valign="top">
					<table class="tdbkblue2" width="396" border="0" align="center"
						cellpadding="0" cellspacing="0"> 
						<tr>
							<td height="94" valign="top" background="elfrontimages/top.gif">
								<table width="86%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="40" align="center" class="gqtitle">
										<%-- 
											<a class="gqtitle"
												href="newsIndexView.action?news.id=<s:property value="zxNewss[0].id"/>"><s:property
													value="zxNewss[0].title" />
											</a>
										--%>	
											<a class="gqtitle"
												href="newsIndexView.action?news.id=<s:property value="zxNewss_tw_wb[0].id"/>"><s:property
													value="zxNewss_tw_wb[0].title" />
											</a>
										</td>
									</tr>
									<tr>
										<td class="content" style="line-height: 22px;">
											<%-- ${zxNewss[0].content}--%>
											${zxNewss_tw_wb[0].content}
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="102">
								<div class="line2"></div>
								<%-- 
								<table width="86%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="40" align="center" class="gqtitle">
											<a class="gqtitle"
												href="newsIndexView.action?news.id=<s:property value="zxNewss[1].id"/>">
												<s:property value="zxNewss[1].title" />
											</a>
										</td>
									</tr>
									<tr>
										<td class="content" style="line-height: 22px;">
											${zxNewss[1].content}
										</td>
									</tr>
								</table>
								--%>
								<table width="90%" border="0" align="center" cellpadding="0"
									cellspacing="0" style="margin-top:10px;">
									<s:iterator value="zxNewss_tw_wb" status="zxNewss_st">
										<s:if test="#zxNewss_st.index > 0">
											<tr>
											  <td width="15"  class="gqtitle"><img src="elfrontimages/iconred.gif" width="4"
																height="6" class="icon" /> </td>
												<td align="left"  class="gqtitle">
													<a class="gqtitle"
														href="newsIndexView.action?news.id=<s:property value="id"/>">
														<s:property value="title" />
													</a>												</td> 
												<td width="120" align="center"   class="gqtitle"> 
												<span style="color:#000000">[
											  <s:date name="releasetime" format="yyyy-MM-dd HH:mm"/>]</span>												</td>
											</tr> 
										</s:if>
									</s:iterator>
								</table>
							</td>
						</tr>
					</table>
				</td>
			<%@include file="indexLogin.jsp" %>
			</tr>
		</table>
			<!-- mycourseAlllist.action?module=studentman -->
                <table style="margin-bottom:8px;" width="960" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="310"><a href="study.action?module=My_EvaluationInit.action"><img src="images/cp001.jpg" width="310" height="165" border="0" /></a></td>
                    <td align="center"><a href="study.action?module=My_ReportInit.action"><img src="images/cp002.jpg" width="396" height="165" border="0" /></a></td>
                    <td width="240"><a href="study.action?module=mycourseAlllist.action"><img src="images/cp003.jpg" width="240" height="165" border="0" /></a></td>
                  </tr>
                </table>
                <!--<table width="960" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<iframe id="loginframe" name="loginframe" src="coursetype.action"
						frameborder="0" width="960" scrolling="No" height="212"
						allowtransparency="allowTransparency"></iframe>
				</td>
			</tr>
		</table>
		-->
		<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="680"><table width="680" border="0" align="center" cellpadding="0"
						cellspacing="0">
              <tbody>
                <tr>
                  <td valign="top" align="middle"
									background="elfrontimages/m_bg.jpg" height="63"><table height="63" cellspacing="0" cellpadding="0" width="600"
										align="center" border="0">
                      <tbody>
                        <tr>
                          <td valign="center" align="middle" width="108"><img height="37" src="elfrontimages/znss.jpg" width="108" /> </td>
                          <td valign="center" align="right"><form action="" method="post" name="isform" id="isform">
                              <input type="hidden" name="knowledge.title" id="klt" />
                              <input type="hidden" name="course.name" id="cn" />
                              <input type="hidden" name="qstuff.title" id="qtitle" />
                              <input type="hidden" name="elclass.name" id="elcname" />
                              <input type="hidden" name="news.title" id="newsTitle" />
                              <input type="hidden" name="forum.title" id="forumTitle" />
                              <input type="hidden" name="pN" value="0" />
                              <input type="hidden" name="pS" value="10" />
                            </form>
                              <span class="content4">请输入关键字</span>：
                            <input name="Input" class="textbox" id="search_content" style="MARGIN-RIGHT: 20px; height: 22px;" />
                              <select name="select" id="search_type"
														style="MARGIN-RIGHT: 20px">
                                <option value="kc" selected="selected"> -课程- </option>
                                <option value="zs"> -资讯- </option>
                                <option value="zl"> -知识- </option>
                                <option value="tz"> -帖子- </option>
                              </select>                          </td>
                          <td valign="center" align="left" width="100"><script type="text/javascript">
														function indexsearch(){
															var content = document.getElementById("search_content").value;
															var url = ""; 
															if(document.getElementById("search_type").value=='zs'){
																//url="stuff_listbyTitle.action?news.title="+content;
																url="stuff_listbyTitle.action";
																document.getElementById("newsTitle").value=content;
																//document.getElementById("klt").value=content;
															}
															if(document.getElementById("search_type").value=='kc'){
																//url = "course_listbytitle.action?course.name="+content;
																document.getElementById("cn").value=content;
																url = "courseIndex.action?course.ctype.id=1";
															}
															if(document.getElementById("search_type").value=='cl'){
																url = "class_listbytitle.action?title="+content;
															}
															if(document.getElementById("search_type").value=='zl'){
																//url = "knowledge_center_listbytitle.action?knowledge.title="+content;
																document.getElementById("klt").value=content;
																url = "knowledge_center_listbytitle.action";
															}
															if(document.getElementById("search_type").value=='tz'){
																//url = "searchforumList.action?forum.title="+content;
																document.getElementById("forumTitle").value=content;
																url = "searchforumList.action";
																
															}
																isform.action = url;
																isform.submit();
														}

													</script>
                              <img style="CURSOR: hand" onclick="indexsearch();"
														height="33" src="elfrontimages/go.jpg" width="33" /> </td>
                        </tr>
                      </tbody>
                  </table></td>
                </tr>
              </tbody>
            </table></td>
            <td width="80" align="right" background="elfrontimages/m_bg.jpg">友情链接</td>
            <td align="center" background="elfrontimages/m_bg.jpg">
			<select name="select3" size="1" class="input_bg" onchange="javascript:window.open(this.options[this.selectedIndex].value);">
                    <option>中国食品安全培训网常用网站链接</option>
                    <option value="http://www.xinhua.org">新华</option>
                    <option value="http://www.people.com.cn">人民</option>
                    <option value="http://www.cctv.com">央视国际</option>
                    <option value="http://www.cnradio.com">中央人民广播电台</option>
                    <option value="http://www.cri.com.cn">中国国际广播电台</option>
                    <option value="http://www.gmw.com.cn">光明</option>
                    <option value="http://www.economicdaily.com.cn">经济日报</option>
                    <option value="http://www.chinadaily.com.cn">中国日报</option>
                    <option value="http://www.chinanews.com.cn">中国新闻</option>
                    <option value="http://www.bjd.com.cn">京报</option>
                    <option value="http://www.btv.org">北京电视</option>
                    <option value="http://www.bjradio.com.cn/">北京人民广播电台</option>
                    <option value="http://www.beijingnews.com.cn">千龙新闻</option>
                    <option value="http://www.bjyouth.com.cn">北京青年</option>
                    <option value="http://www.morningpost.com.cn/">北京晨报</option>
            </select>			</td>
          </tr>
        </table>
		<table style="margin-top: 5px;" width="960" height="50" border="0"
			align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td height="102" align="center" background="elfrontimages/botbg.png"
					style="line-height: 25px;">
					<p class="foot">中国食品安全培训网版权所有 copyright 2012-2016 all rights reserved <br />
技术支持：中国食品安全培训网&nbsp;&nbsp;&nbsp;&nbsp;电话：010-62105898</p>
			  </td>
			</tr>
		</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" bgcolor="#FFCC99">手机版测试页面</td>
  </tr>
</table>
	</body>
</HTML>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        