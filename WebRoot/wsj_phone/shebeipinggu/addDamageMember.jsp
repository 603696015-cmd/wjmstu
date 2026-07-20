<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>产品管理</TITLE>
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
<script type="text/javascript">
	function check() { 
		alert(document.getElementById("personId").value);
		return false;
		alert(parseInt(document.getElementById("personId").value.length);
		return false;
		if(document.getElementById("name").value == ""){
			alert("请填写您姓名！");
			document.getElementById("name").focus();
			return false;
		}else if(document.getElementById("sex").value == ""){
			alert("请选择您的性别！");
			document.getElementById("sex").focus();
			return false;
		}else if(document.getElementById("personId").value == ""){
			alert("请填写您的身份证！");
			document.getElementById("personId").focus();
			return false;
		}else if(parseInt(document.getElementById("personId").value.length)!=15&&parseInt(document.getElementById("personId").value.length!=18)){
			alert("有效身份证号码必须是15位或18位！");
			document.getElementById("personId").focus();
			return false;
		}else {
			return true;
		}
	}
</script>
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
class=shadow id=locationid>添加定损员</SPAN></DIV>
<DIV class=tabs>
</DIV>

  <FORM id=myform name=myform  action="addDamageMember.action" method=post onsubmit="return check();">
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">姓名：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="name" maxLength=50 
		      size=30 name="damageMember.name"><SPAN style="COLOR: red">* </SPAN>
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
				<input type="radio" checked="checked" name="damageMember.sex"
					value="男" />
				&nbsp;&nbsp;
				<label>
					女
				</label>
				<input type="radio"  name="damageMember.sex"
					value="女"/>
				&nbsp;&nbsp;
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">身份证号码：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="personId" maxLength=50 
		      size=30 name="damageMember.personId"> <SPAN style="COLOR: red">* </SPAN>
		    </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">出生年月日：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="birthday" maxLength=50 
		      size=30 name="damageMember.birthday" onclick="setday(this)"> 
		    </TD>
		  </TR>
		  
		   <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">工作单位：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="workCompany" maxLength=50 
		      size=30 name="damageMember.workCompany"> 
		    </TD>
		  </TR>
		   
		
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">籍贯：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <INPUT class=textbox id="hometown" maxLength=50 
		      size=30 name="damageMember.hometown"> 
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">照片(宽高比为4:3)：</SPAN>
			</td>
			<td width="25%">&nbsp;&nbsp;
				<label>
					<input class=textbox type="text"  id="pic" maxLength=50  size=30 name="damageMember.picture" />
					<a href="javascript:setUrl('pic');" class="textbg">浏览资源库</a>
				</label>
			</td>
		  </TR>
		  
		  
		  </TBODY>
		</TABLE>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,添加 " name=Submit>
		</div>
  </FORM>

	</body></HTML>
