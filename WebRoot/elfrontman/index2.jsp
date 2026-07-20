<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
%>

<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>北京市卫生局财务人员队伍建设培训管理信息系统</TITLE>
		<base href="<%=basePath%>">
		<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<META content="MSHTML 6.00.2900.5897" name=GENERATOR>
	</HEAD>

	<BODY topmargin="0"  leftmargin="0"> 
		<%@include file="frontheader2.jsp"%> 
		<table width="960" height="8" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td></td>
  </tr>
</table>
<table style="margin-bottom:5px;" width="960" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="177" valign="top"><table width="177" border="0" cellspacing="0" cellpadding="0"><tr>
        <td align="center" valign="top"><table width="175" height="131" border="0" cellpadding="0" cellspacing="0" background="elfrontimages/l_bg.jpg">
          <tr>
            <td width="4"><img src="elfrontimages/l_left_bg.jpg" width="4" height="131" /></td>
            <td align="center" valign="top">
            <s:if test="#session.username!=null"> 
		      <table width="160" border="0" cellspacing="0" cellpadding="0"> 
		        <tr>
		          <td width="97" height="35" align="left" valign="middle"><img src="elfrontimages/07.png" width="10" height="9" /><span class="STYLE12">&nbsp;&nbsp;用户登录</span></td>
		          <td width="53" align="left" valign="middle"><img src="elfrontimages/08.gif" width="53" height="14" /></td>
		        </tr> 
		        <tr>
		          <td height="30" colspan="2" align="left" valign="bottom" class="STYLE2">用户名：<label>
		            <s:property value="#session.username" />
		            </label></td>
		        </tr>
		        <tr>
		          <td height="30" colspan="2" align="left" valign="middle" class="STYLE2">姓&nbsp;&nbsp;&nbsp;&nbsp;名：<label>
		              <s:property value="#session.realname" />
		            </label></td>
		        </tr>
		        <tr>
		          <td height="30" colspan="2" align="right" valign="middle">&nbsp;</td>
		        </tr>
		      </table>
            </s:if>
            <s:else>
            <form style="margin: 0;padding: 0" action="login.action" method="post"><table width="160" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="97" height="35" align="left" valign="middle"><img src="elfrontimages/07.png" width="10" height="9" /><span class="STYLE12">&nbsp;&nbsp;用户登录</span></td>
                <td width="53" align="left" valign="middle"><img src="elfrontimages/08.gif" width="53" height="14" /></td>
              </tr>
              <tr>
                <td height="30" colspan="2" align="left" valign="bottom" class="STYLE2">用户名：<label>
                  <input name="elUser.username" type="text" style="width:100px;border: 1 solid buttonface;"  />
                  </label></td>
              </tr>
              <tr>
                <td height="30" colspan="2" align="left" valign="middle" class="STYLE2">密&nbsp;&nbsp;码：<label>
                  <input name="elUser.password" type="password"  style="width:100px;border: 1 solid buttonface;"/>
                  </label></td>
              </tr>
              <tr>
                <td height="30" colspan="2" align="right" valign="middle"><input style="background-image:url('elfrontimages/09.png');width: 49px;height:19px;border: none;" value="" type="submit"></td>
              </tr>
            </table></form></s:else></td>
            <td width="4"><img src="elfrontimages/l_right_bg.jpg" width="4" height="131" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="10" align="center" valign="top"></td>
      </tr>
      <TR>
          <TD vAlign=top align="center"><table class="tabbk3"  width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F9F9F9" style="margin-top:0px;">
            <tr>
                <td height="35" bgcolor="#F9F9F9"><img src="elfrontimages/phb.jpg" width="111" height="20"></td>
            </tr>
            </table>
            <TABLE class="tabbk4" cellSpacing=0 cellPadding=0 height="293"
								            bgColor=#f9f9f9 border=0>
								              <TBODY>
								              <TR>
								                <TD class=bline3 vAlign=top align=middle width=43 height=28>
								                  <P>排名 </P></TD>
								                <TD class=bline3 vAlign=top align=middle width=150>
								                  <P>单位名称 </P></TD>
								                <TD class=bline3 vAlign=top align=middle width=60>
								                  <P>通过率 </P></TD></TR>
								              <TR>
								            <s:iterator value="phDeps" status="pdst">
								            <tr>
								              <td width="43" height="24" align="center" valign="top"><p><s:property value="#pdst.index+1"/></p></td>
								              <td width="150" align="center" valign="top"><p><s:property value="name"/></p></td>
								              <td width="60" align="center" valign="top"><p><s:property value="ratioPassing_"/>%</p></td>
								            </tr>
								            </s:iterator>
								            <tr>
								              <td width="43" height="24" align="center" valign="top"><p>&nbsp;</p></td>
								              <td colspan="2" align="right" valign="top"><a href="gclass_depph_list.action?elclass.id=${elclass.id }">全部排行榜&gt;&gt;</a></td>
								              </tr>
								              </TBODY>
								            </table>
          </TD>
       </TR>
        <!--<tr>
          <td align="center"><a href="knowledge_center.action"><img border="0" style="margin-top:8px;" src="elfrontimages/15.png" width="170" height="52"></a></td>
        </tr>
    --></table></td>
    <td align="center" valign="top"><table width="630" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="209" align="center" valign="top"><img src="elfrontimages/pic.jpg" width="630" height="209" /></td>
      </tr>
      <tr>
        <td align="center" valign="top"><table width="630" height="191" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="415" align="center" valign="top"><table width="410" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="375" height="40" align="left" valign="middle"><a href="newsIndex.action?pN=0&pS=10&containsub=0&news.ntype.id=2"><img src="elfrontimages/zxzx.jpg" width="65" height="29" border="0" /></a></td>
                <td width="60" align="left" valign="middle"><a href="newsIndex.action?pN=0&pS=10&containsub=0&news.ntype.id=2"><img src="elfrontimages/more.png" width="37" height="11" border="0" /></a></td>
              </tr>
              <tr>
                <td height="5" colspan="2" align="center" valign="top"><img src="elfrontimages/18.png" width="390" height="2" /></td>
              </tr>
              <tr> 
                <td height="136" colspan="2" align="center" valign="middle"><table width="400" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="140" valign="top" style="padding-top:3px;"><table width="100%" height="13" border="0" cellpadding="0" cellspacing="0">
                      <s:iterator value="zxNews">
						<tr>
						  <td width="20" align="center" valign="middle"><img src="elfrontimages/19.png" width="3" height="5" /></td>				
						<td height="23">
						<A title="<s:property value="title"/>"
							 target="_blank" href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property value="title" /></A></td>
						<td>[<s:date name="releasetime" format="yyyy-MM-dd"/>]</td>
                    	</tr>
                       	</s:iterator>
                    </table></td>
                  </tr>
                  
                </table></td>
              </tr>
            </table></td>
            <td width="1"><img src="elfrontimages/dian02.jpg" /></td>
            <td align="center" valign="top"><table width="207" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="40" align="left" valign="middle"><a href="forumIndex.action"><img src="elfrontimages/zxdt.jpg" width="65" height="29" border="0" /></a></td>
              </tr>
              <tr>
                <td height="5" align="center" valign="middle"><img src="elfrontimages/17.png" width="187" height="2" /></td>
              </tr>
              <tr>
                <td height="136" align="center" valign="top"><table width="220" border="0" align="right" cellpadding="0"
												cellspacing="0" class="content3" style="margin-top: 5px;">
												<s:iterator value="rmforums">
													<tr>
														<td width="20" height="20" align="center">
															<img src="elfrontimages/iconred.gif" width="4" height="6"
																class="icon" />														</td>
														<td align="left">
															<a target="_blank"
																href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10">
															<s:property
																	value="title" /> </a>
													  </td>
													</tr>
												</s:iterator>
					  </table></td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="8" align="center" valign="top"></td>
      </tr>
       <!--<TR>
          <TD vAlign=top align=middle height=63>
           <map name="stuman">
			<area href="studentman.action" shape="rect"
				coords="20,20,120,55" >
				<area href="studyexamman.action" shape="rect"
				coords="170,20,270,55" >
				<area href="studentman.action?containsub=1" shape="rect"
				coords="320,20,420,55" >
				<area href="studentman.action?containsub=2" shape="rect"
				coords="485,20,585,55" >
		</map><img src="elfrontimages/yindao2.jpg" border="0" width="630" height="69" usemap="#stuman"></TD>
        </TR>
      --><tr>
        <td height="63" align="center" valign="top" background="elfrontimages/m_bg.jpg"><table width="600" height="63" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="108" align="center" valign="middle"><img src="elfrontimages/znss.jpg" width="108" height="37" /></td>
            <td align="right" valign="middle">请输入关键字：
              <input style="margin-right:20px;" type="text" name="search_content" id="search_content" />
              <SELECT style="margin-right:20px;" id=search_type> 	
				<OPTION value='zl'><!-- 素材 -->
					-资讯-
				</OPTION>
				<OPTION value='zs' selected>
					-知识-
				</OPTION>
				<OPTION value='kck'><!-- 课程库 -->
					-课程-
				</OPTION> 
				<OPTION value='tz'>
					-论坛-
				</OPTION>
				<!--
				<OPTION value='cl'>
					-班级-
				</OPTION>
				<OPTION value='kc'>
					-课程-
				</OPTION> 
			--></SELECT>
			</td>
            <td width="100" align="left" valign="middle"><form action="" method="post" name="isform">
           		<form action="" method="post" name="isform">
					<input type="hidden" name="knowledge.title" id="klt"/>
					<input type="hidden" name="course.name" id="cn"/>
					<input type="hidden" name="qstuff.title" id="qtitle" />
					<input type="hidden" name="elclass.name" id="elcname" /> 
					<input type="hidden" name="forum.title" id="forumTitle" />
					<input type="hidden" name="pN" value="0"/>
					<input type="hidden" name="pS" value="10"/>
					
				</form>
				<script type="text/javascript">
					function indexsearch(){
						var content =  document.getElementById("search_content").value ;
						var url = "";
						if(document.getElementById("search_type").value=='zs'){
							url="knowledge_center_listbytitle.action";
							document.getElementById("klt").value=content;
						} 
						if(document.getElementById("search_type").value=='kc'){
							url = "course_listbytitle.action";
							document.getElementById("cn").value=content;
						}
						if(document.getElementById("search_type").value=='cl'){
							url = "class_listbytitle.action";
							document.getElementById("elcname").value=content;
						}
						if(document.getElementById("search_type").value=='zl'){
							url = "stuff_listbyTitle.action";
							document.getElementById("qtitle").value=content;
						}  
						if(document.getElementById("search_type").value=='kck'){
							url = "course_libraryList.action?course.ctype.id=1&course.courseCss=-1";
							document.getElementById("cn").value=content; 
						}
						if(document.getElementById("search_type").value=='tz'){ 
							url = "searchforumList.action";
							document.getElementById("forumTitle").value=content;
							
						}
						isform.action = url;
						isform.submit();
					}
				</script><img src="elfrontimages/go.jpg" style="cursor:hand;" onClick="indexsearch();" width="33" height="33" /></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="172" align="right" valign="top"><table width="172" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="4" height="233" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_left_bg.jpg" width="4" height="233" /></td>
        <td align="center" valign="top" background="elfrontimages/r_bg.jpg"><table width="172" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="35" align="center" valign="middle"><img src="elfrontimages/zxkc.jpg" width="103" height="20" /></td>
          </tr>
          <tr>
            <td height="198" align="center" valign="middle"><table width="165" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                <td rowspan="8" valign="top"><table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
                <s:iterator value="phCourses">
					<tr>
                    <td height="22"><A title="<s:property value="name"/>"
							target="_blank"
							href="course_libraryView.action?course.id=<s:property value="id"/>"><s:property
								value="name" /></A></td>
                  </tr>
				</s:iterator>
                </table></td>
              </tr>
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                </tr>
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                </tr>
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                </tr>
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                </tr>
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                </tr>
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                </tr>
              <tr>
                <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="4" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_right_bg.jpg" width="4" height="233" /></td>
      </tr>
      <tr>
        <td height="8" colspan="3"></td>
      </tr>
      <tr>
        <td width="4" height="233" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_left_bg.jpg" width="4" height="233" /></td>
        <td height="233" align="center" valign="top" background="elfrontimages/r_bg.jpg"><table width="172" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="35" align="center" valign="middle"><img src="elfrontimages/rmkj.jpg" width="103" height="20" /></td>
          </tr>
          <tr>
            <td height="198" align="center" valign="middle"><table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  <td rowspan="8" valign="top"><table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
                   <s:iterator value="tjCourses">
						<TR>
						<td height="22"> 
							 			<A title="<s:property value="name"/>"
							target="_blank"
							href="course_libraryView.action?course.id=<s:property value="id"/>"><s:property
								value="name" /></A>
							  </TD>
							</TR>
						  </s:iterator>
                  </table></td>
                </tr>
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  </tr>
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  </tr>
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  </tr>
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  </tr>
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  </tr>
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  </tr>
                <tr>
                  <td width="20" height="22" align="center" valign="middle"><img src="elfrontimages/21.png" width="6" height="5" /></td>
                  </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="4" height="233" background="elfrontimages/r_bg.jpg"><img src="elfrontimages/r_right_bg.jpg" width="4" height="233" />
        
        </td>
      </tr>
    </table></td>
  </tr>
</table> 
 <%@include file="frontbottom.jsp" %>
	</BODY>
</HTML>
