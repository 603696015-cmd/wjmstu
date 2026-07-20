 
 
 
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>协同工作平台--&#26032;&#38395;&#31867;&#21035;--列表</TITLE>
		<base href="http://localhost:8080/beijing/">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<STYLE type=text/css> 
BODY {
	FONT-SIZE: 12px
}
 
UL {
	LIST-STYLE-TYPE: none
}
</STYLE>
		<LINK href="elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet>
		<style type="text/css"> 
<!--
.STYLE2 {
	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}
 
.STYLE3 {
	color: #0000FF
}
 
.STYLE4 {
	color: #DFDFDF
}
 
.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}
 
.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
 
.STYLE7 {
	font-size: 12px
}
 
.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #0000ff
}
-->
</style>
	</HEAD>
	<BODY>
		
<script language="javascript" type="text/javascript"> 
  var today = new Date();
  function showDate(){
	var year = today.getYear();
  	var month = today.getMonth() + 1; 
  	var date = today.getDate();		//日期 
  	var day = today.getDay();		//星期
  	var week =new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var dayValue = "";
  	dayValue += year + "年";
  	dayValue += ((month < 10) ? "0" : "") + month + "月";
  	dayValue += date + "日  ";
  	dayValue += (week[day]);
  	document.write("今天是：" + dayValue);
  }
</script>
 
<script type="text/javascript"> 
function displaySubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "block"; 
} 
function hideSubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "none"; 
} 
function changeNews(number){
	var newsCom=document.getElementById("newsCom");
    var newsWork=document.getElementById("newsWork");
	var new1=document.getElementById("new1");
	var new2=document.getElementById("new2");
	if(number=="1"){
		newsCom.className="label_name";
		newsWork.className="label_dis";
		new1.style.display="block";
		new2.style.display="none";
	}else{
		newsCom.className="label_dis";
		newsWork.className="label_name";
		new1.style.display="none";
		new2.style.display="block";
	}
}
function getOtherPage(page){
 
	var mainFrame=document.getElementById("mainFrame");
	mainFrame.src=page;
}
 
</script>
 
<table width="960" height="147" border="0" align="center"
	cellpadding="0" cellspacing="0">
	<tr>
		<td background="elfrontimages/banner.jpg">&nbsp;
			
		</td>
	</tr>
</table>
<table width="960" border="0" align="center" cellpadding="0"
	cellspacing="0" background="elfrontimages/menu_bak.jpg">
	<tr>
		<td width="300" align="center">
			<span style="color: #FFFFFF;"><script>showDate();</script> </span>
		</td>
		<td>
			<table border="0" align="center" cellpadding="0" cellspacing="0"
				background="elfrontimages/menu_bak.jpg">
				<tr>
					<td>
						<div id="menu">
							<ul id="navigation">
								<li>
									<a href="login.jsp" class="parent">
										<span> 登 陆 </span> </a>
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">
 
									<a href="registerInit.action?type=1" class="parent"> 
										<span> 注 册 </span> </a>
									
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">
									<a href="forumIndex.action" class="parent">
										<span> 论 坛 </span> </a>
									
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">
 
									<a href="knowledge_center_list.action" class="parent">
										<span> 知识库 </span> </a>
									
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">
									<a href="study.action" class="parent">
										<span> 个人中心 </span> </a>
									
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
 
 
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="270" valign="top">
					<table width="100%" border="0">
						<tr>
						  <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
        border="0">
                                  <tbody>
                                    <tr>
                                      <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                                      <td width="662" background="images/knowledge/zhao_22.gif"></td>
                                      <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td><span class="STYLE6">新闻资讯组合搜索</span></td>
                                            <td width="60" align="center"><a href="#"></a></td>
                                          </tr>
                                      </table></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td align="left" valign="top" style="PADDING: 8px; line-height:25px;">
									  <form action="stuff_listbyTitle.action"
													method="post" name="klsearch" target="_parent">
													<input type="hidden" name="pN" value="0" id="pageNow"/>
													<input type="hidden" name="pS" value="10" id="pS"/>
													<input type="hidden" name="str" id="str"
														value="knowledgeserach">
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#ebebeb border=0>
														<TBODY>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资讯栏目</STRONG>
																</TD>
																<TD bgColor=#ffffff>
																	<LABEL>
																	
																		<select style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" name="news.ntype.id"
																			id="parentid">
																			<option value="">${ntypeTree.name }</option>
																			<#list nt as tree>
																				<option value="">--${tree.name}</option>
																			</#list>
																			
																			
																		</select>
																		
																	</LABEL>
																</TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>
																	<STRONG>资讯标题</STRONG>
																</TD>
															  <TD bgColor=#ffffff>
															    <LABEL>
																		<input style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" type="text" id="news.title" name="news.title" />
															    </LABEL></TD>
															</TR>
															<TR>
																<TD align=middle width=100 bgColor=#ffffff height=30>&nbsp;
																	
 
															  </TD>
																<TD bgColor=#ffffff>
																	<INPUT name="submit" type="submit" class="textbg4"
																		onclick="javascript:document.getElementById('pageNow')=0"
																		value="搜 索">
																</TD>
															</TR>
														</TBODY>
													</TABLE>
										</form>
									  
									  </td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                                      <td background="images/knowledge/zhao_27.gif"></td>
                                      <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                                    </tr>
                                  </tbody>
                                </table>
					        </td>
						</tr>
						<tr>
						  <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
        border="0">
                                  <tbody>
                                    <tr>
                                      <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                                      <td width="662" background="images/knowledge/zhao_22.gif"></td>
                                      <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td><span class="STYLE6">新闻资讯栏目导航</span></td>
                                            <td width="60" align="center"><a href="#"></a></td>
                                          </tr>
                                      </table></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                                      			
												<link rel="stylesheet" type="text/css" href="js/tree/dtree.css" />
<script type="text/javascript" src="js/tree/dtree.js"></script>
	<script type="text/javascript"> 
<!--
 
var d0 = new dTree('null','null',1, 'd0');
 
d0.add(1,-1,'新闻类别','newsIndex.action?&news.ntype.id=1&ntype.id=1');
d0.add(10,1,'下载中心','newsIndex.action?&news.ntype.id=1&ntype.id=10');
d0.add(151,10,'啊啊啊啊啊啊啊啊啊啊','newsIndex.action?&news.ntype.id=1&ntype.id=151');
d0.add(11,1,'经验交流','newsIndex.action?&news.ntype.id=1&ntype.id=11');
d0.add(12,1,'教学公告','newsIndex.action?&news.ntype.id=1&ntype.id=12');
d0.add(13,1,'帮助中心','newsIndex.action?&news.ntype.id=1&ntype.id=13');
d0.add(14,1,'新闻动态','newsIndex.action?&news.ntype.id=1&ntype.id=14');
d0.add(111,1,'科技信息化处','newsIndex.action?&news.ntype.id=1&ntype.id=111');
d0.add(121,1,'国内新闻','newsIndex.action?&news.ntype.id=1&ntype.id=121');
d0.add(131,1,'企业新闻','newsIndex.action?&news.ntype.id=1&ntype.id=131');
document.write(d0);
//-->
</script>
 
                                      </td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                                      <td background="images/knowledge/zhao_27.gif"></td>
                                      <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                                    </tr>
                                  </tbody>
                                </table>
						    </td>
						</tr>
					</table>
				</td>
			  <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                      <tbody>
                        <tr>
                          <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                          <td width="662" background="images/knowledge/zhao_22.gif"></td>
                          <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                              
                                <div><a href='index.action'>首页</a>&nbsp;>>&nbsp;<span><a href='newsIndex.action?ntype.id=-2'>可查看的新闻类别</a></span>&nbsp;&nbsp;</div>
                              </td>
                              <td width="60" align="center"><a href="#"></a> </td>
                            </tr>
                          </table></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
									<#list newsList as zxNews>
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="100%" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">
																<a
																	href="newsIndexView_${zxNews.id}.shtml">${zxNews.title} </a>
															</td>
															<td align="center"></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="95" valign="bottom">
													<table width="98%" border="0" align="center"
														cellpadding="0" cellspacing="0">
														<tr>
															<td height="85" valign="top">
																简介：${zxNews.descString}
																<br />
																<span class="h30">创建者：${zxNews.owner.realname}  ${zxNews.releasetime?string("yyyy-MM-dd HH:mm:ss")}</span>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</#list>
									
									<form name="ni" action="newsIndex.action" method="post">
										<input type="hidden" name="pN"  id="pageNow2"/>
										<input type="hidden" name="pS"  id="pS" value="10"/>
										<input type="hidden" name="containsub" value="0" />
										<input type="hidden" name="news.ntype.id" value="1"/>
										<input type="hidden" name="ntype.id"  value="1"/>
									</form>
									<SCRIPT type="text/javascript">
						function page(i){
							document.getElementById("pageNow2").value=i;
							ni.submit();
						}
					</SCRIPT>
<div style='margin-top:10px;'><#if (pN>0)><a style='cursor: hand' href='html/newsindex/newsList_0_${nid}.html'>[首页]</a><a style='cursor: hand' href='html/newsindex/newsList_${pN-1}_${nid}.html'>[上一页]</a><#else>[首页][上一页]</#if>
	<select  onchange="location.href=this.options[this.selectedIndex].value"><#list li as page><#if page==pN><option value=${page} selected='selected'>${page+1}</option><#else><option value=newsList_${page}_${nid}.html>${page+1}</option></#if>
									</#list>
	</select> 
<#if (pN<pageCount-1)><a style='cursor: hand' href='html/newsindex/newsList_${pN+1}_${nid}.html'>[下一页]</a><a style='cursor: hand' href='html/newsindex/newsList_${pageCount-1}_${nid}.html'>[末页]</a><#else>[下一页][末页]</#if><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>${count1}<b>条</b></span>
</div>			
					   
						  </td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                      </tbody>
                    </table>		        </td>
			</tr>
		</table>
		
<table style="margin-top:8px;" width="960" height="50" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="102" align="center" background="elfrontimages/botbg.png" style="line-height:25px;"><p class="foot">协同工作平台 copyright 2011-2015 all rights reserved<br />
      地址：北京市朝阳区广渠路36号。电话：010-56219458，传真：010-87729332</p>
    </td>
  </tr>
</table>
 
	</BODY>
</HTML>
                             
