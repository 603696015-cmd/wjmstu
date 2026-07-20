<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>定损员管理</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet><LINK 
href="<%=path %>/css/css.css" type=text/css rel=stylesheet>

<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/editor/fckeditor.js"></script>
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
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid>修改定损员</SPAN></DIV>
<DIV class=tabs>
</DIV>

  <FORM id=myform name=myform onSubmit="" action="updateDamageMember.action" method=post>
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		  	<input type="hidden" name="damageMember.id" value="<s:property value='damageMember.id'/>"/>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">姓名：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="name" maxLength=50 
		      size=30 name="damageMember.name" value='<s:property value="damageMember.name"/>'>
		    </TD>
		  </TR>
		  
		  
		  <TR class=tdbg>
		  	<TD width="25%" height=22  bgcolor="#FFFFFF" style="color: black">
		    	<SPAN style="FONT-WEIGHT: bold">性别：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;  
		    	<label>
					男  
				</label>
				<input type="radio" <s:if test="damageMember.sex_ == 0">checked="checked"</s:if>  name="damageMember.sex"
					value="男" onclick=""/>
				&nbsp;&nbsp;
				<label>
					女
				</label>
				<input type="radio" <s:if test="damageMember.sex_ == 1">checked="checked"</s:if> name="damageMember.sex"
					value="女"/>
				&nbsp;&nbsp;
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">身份证号码：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="personId" maxLength=50 
		      size=30 name="damageMember.personId" value='<s:property value="damageMember.personId"/>'> 
		    </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">出生年月日：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="birthday" maxLength=50 
		      size=30 name="damageMember.birthday" onclick="setday(this)" value="<s:date name="damageMember.birthday" format="yyyy-MM-dd HH:mm:ss"/>"> 
		    </TD>
		  </TR>
		  
		   <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">工作单位：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="workCompany" maxLength=50 
		      size=30 name="damageMember.workCompany" value='<s:property value="damageMember.workCompany"/>'> 
		    </TD>
		  </TR>
		   
		
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">籍贯：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="hometown" maxLength=50 
		      size=30 name="damageMember.hometown" value='<s:property value="damageMember.hometown"/>'> 
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">
					照片：
					<s:if test="damageMember.picture != null">															
						<img src="<s:property value="damageMember.picture_"/>" width="100" height="80" />
					</s:if><s:else>
						<img src="" width="100" height="80" /> 
					</s:else> 
				</SPAN>
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input class=textbox type="text"  id="pic" maxLength=50  size=30 name="damageMember.picture" value='<s:property value="damageMember.picture"/>'/>
					<a href="javascript:setUrl('pic');" class="textbg">浏览资源库</a>
				</label>
			</td>
		  </TR>
		  
		  
		  </TBODY>
		</TABLE>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,修改 " name=Submit>
		</div>
  </FORM>
</BODY></HTML>
