<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>店铺管理</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet>
<LINK href="<%=path %>/css/css.css" type=text/css rel=stylesheet>

<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/js/fckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery2.js"></script>
<script type="text/javascript" src="<%=path %>/js/stuffman.js"></script>
<script type="text/javascript" src="<%=path %>/js/calendar.js"></script>
<META content="MSHTML 6.00.2900.6197" name=GENERATOR>
<style type="text/css">
<!--
.STYLE1 {	font-size: 14px;
	color: green;
}
-->
</style>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 >
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid>图片修改</SPAN></DIV>
<div class="tabs">
<UL>
  <LI class=select><A href="#">图片修改</A> 
  </LI>
  </UL></DIV>

  <FORM id=myform name=myform  action="alterPicture.action" method=post>
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  
		  <TR class=tdbg>
		    <td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">BANNER图片：
					<s:if test="pfmsUser.banner != null">															
						<img src="<s:property value="pfmsUser.banner_"/>" width="100" height="80" />
					</s:if><s:else>
						<img src="<s:property  escape="false" value="banner"/>" id="cimg" width="100" height="80" /> 
						<SCRIPT type="text/javascript">
							obj = document.getElementById("cimg");
							addImgs(obj);
						</SCRIPT>
					</s:else>
				</SPAN>
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input class=textbox type="text"  id="banner" maxLength=50  size=30 name="pfmsUser.banner" value='<s:property value="pfmsUser.banner"/>'/>
					<a href="javascript:setUrl('banner');" class="textbg">浏览资源库</a>
				</label>
			</td>
		  </TR>
		  
		  <TR class=tdbg>
		    <td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">店铺简介图片：
					<s:if test="pfmsUser.dianpujianjietupian != null">															
						<img src="<s:property value="pfmsUser.dianpujianjietupian_"/>" width="100" height="80" />
					</s:if><s:else>
						<img src="<s:property  escape="false" value="dianpujianjietupian"/>" id="cimg" width="100" height="80" /> 
						<SCRIPT type="text/javascript">
							obj = document.getElementById("cimg");
							addImgs(obj);
						</SCRIPT>
					</s:else>
				</SPAN>
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input class=textbox type="text"  id="dianpujianjietupian" maxLength=50  size=30 name="pfmsUser.dianpujianjietupian" value='<s:property value="pfmsUser.dianpujianjietupian"/>'/>
					<a href="javascript:setUrl('dianpujianjietupian');" class="textbg">浏览资源库</a>
				</label>
			</td>
		  </TR>
		  
		  <TR class=tdbg>
		    <td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">店铺LOGO：
					<s:if test="pfmsUser.logo != null">															
						<img src="<s:property value="pfmsUser.logo_"/>" width="100" height="80" />
					</s:if><s:else>
						<img src="<s:property  escape="false" value="logo"/>" id="cimg" width="100" height="80" /> 
						<SCRIPT type="text/javascript">
							obj = document.getElementById("cimg");
							addImgs(obj);
						</SCRIPT>
					</s:else>
				</SPAN>
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input class=textbox type="text"  id="logo" maxLength=50  size=30 name="pfmsUser.logo" value='<s:property value="pfmsUser.logo"/>'/>
					<a href="javascript:setUrl('logo');" class="textbg">浏览资源库</a>
				</label>
			</td>
		  </TR>
		  
		  
		  </TBODY>
		</TABLE>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,修改 " name=Submit>
		</div>
  </FORM>

	</body></HTML>
