<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>用户管理中心</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet><LINK 
href="<%=path %>/css/css.css" type=text/css rel=stylesheet>
<LINK 
href="<%=path %>/css/houtai.css" type=text/css rel=stylesheet>
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<!--<script type="text/javascript" src="<%=path %>/editor/fckeditor.js"></script>-->
<META content="MSHTML 6.00.2900.6197" name=GENERATOR></HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class="title" style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class="shadow" id="locationid"></SPAN></DIV>
<script type="text/javascript">
	function select(value){
		var li_one = document.getElementById("li_one");
		var li_two = document.getElementById("li_two");
		var li_three = document.getElementById("li_three");
		var li_four = document.getElementById("li_four");
		if(value == "基本信息"){
			li_one.className = "select";
			li_two.className = "";
			li_three.className = "";
		}else if(value == "相关复印件"){
			li_one.className = "";
			li_two.className = "select";
			li_three.className = "";
		}else if(value == "会员简介"){
			li_one.className = "";
			li_two.className = "";
			li_three.className = "select";
		}else{
			return ;
		}
	}
	
	function showOneDiv(value){
		var pfmsBaseInfo = document.getElementById("pfmsBaseInfo");
		var relevantCopies = document.getElementById('relevantCopies');
		var memberProfile = document.getElementById('memberProfile');
		if(value == "基本信息"){
			pfmsBaseInfo.style.display = "block";
			relevantCopies.style.display = "none";
			memberProfile.style.display = "none";
		}else if(value == "相关复印件"){
			pfmsBaseInfo.style.display = "none";
			relevantCopies.style.display = "block";
			memberProfile.style.display = "none";
		}else if(value == "会员简介"){
			pfmsBaseInfo.style.display = "none";
			relevantCopies.style.display = "none";
			memberProfile.style.display = "block";
		}else{
			return ;
		}
	}
</script>
<DIV class=tabs>
	<UL>
		<LI id="li_one" class="select"><A href="#" onclick="showOneDiv(this.innerHTML);select(this.innerHTML);">基本信息</A> 
		</LI>
		<LI id="li_two" class=""><A href="#" onclick="showOneDiv(this.innerHTML);select(this.innerHTML);">相关复印件</A> 
		</LI>
		<LI id="li_three" class=""><A href="#" onclick="showOneDiv(this.innerHTML);select(this.innerHTML);">会员简介</A>
		</LI>
	</UL>
</DIV>
<SCRIPT type=text/javascript>$('#locationid').html("查看基本信息");</SCRIPT>

<SCRIPT>
	
	function myload(){
				var oFCKeditor = new FCKeditor("note") ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
				
				setCurTime("releasetime");
			}
		function setCurTime(oid){
				var now=new Date();
				var year=now.getYear();
				var month=now.getMonth()+1;
				var day=now.getDate();
				var hours=now.getHours();
				var minutes=now.getMinutes();
				if(minutes<10){
					minutes="0"+minutes;
				}
				var seconds=now.getSeconds();
				if(seconds<10){
					seconds="0"+seconds;
				}
				var timeString = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
				var oCtl = document.getElementById(oid);
				oCtl.value = timeString;
				//setTimeout("setCurTime('"+oid+"')",1000);
				//alert(oid);
			}
    </SCRIPT>

<div id="pfmsBaseInfo" style="display:block;">
  <FORM id=myform name=myform onSubmit="return CheckForm();" 
  action="alterBaseInfo.action" method=post>
<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  <TBODY>
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">会员头像：</SPAN>
    </TD>
    <TD width="72%">&nbsp;&nbsp; 
		<s:if test="pfmsUser.head != null">															
			<img src="<s:property value="pfmsUser.head_"/>" width="100" height="80" />
		</s:if><s:else>
			<img src=""  width="100" height="80" /> 
		</s:else>
    </TD>
  </TR>
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN style="FONT-WEIGHT: bold">会员账号：
      </SPAN></TD>
    <TD width="72%">&nbsp; ${pfmsUser.user.username }</TD>
  </TR>
  
  <tr>
		<td width="28%" height=22><SPAN style="FONT-WEIGHT: bold" >
			<SPAN style="FONT-WEIGHT: bold">单位/部门：</SPAN>
		</td>
		<td width="72%" >&nbsp;
			 <s:property value="pfmsUser.user.department.name"/>
		</td>
	</tr>
  
  <tr class=tdbg>
	<td width="28%" height=22><SPAN style="FONT-WEIGHT: bold" >
		<SPAN style="FONT-WEIGHT: bold">权限：</SPAN>
	</td>
	<td width="72%" >&nbsp;
		 <s:property value="pfmsUser.user.role.name"/>
	</td>
  </tr>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">会员名称：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.user.realname } </TD>
  </TR>
  <TR class=tdbg>
  	<TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">会员类型：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.huiyuanleixing } </TD>
  </TR>
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">单位代码：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.user.danwei }</TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">负责人姓名：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.respName } </TD></TR>
	   
	  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">性&nbsp;&nbsp;&nbsp; 别：</SPAN><BR></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.user.sex }</TD></TR>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">省&nbsp;市&nbsp;县&nbsp; ：</SPAN><BR></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.province_city_county }</TD></TR>
        
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">身份证号：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.user.shenfenzheng }</TD></TR>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">地址：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.address }</TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">电话：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.mobile }</TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">手机：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.user.movephone }</TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">传真：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; ${pfmsUser.fex }</TD></TR>
  <TR class=tdbg>
    <TD height=22><SPAN 
      style="FONT-WEIGHT: bold">邮箱地址：</SPAN></TD>
    <TD>&nbsp;&nbsp; ${pfmsUser.email }</TD></TR>
  
  <!-- <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">备注信息：</SPAN><BR>
      其他需要补充说明的信息</TD>
    <TD width="72%">&nbsp;&nbsp; <TEXTAREA class=textbox id=Sign style="WIDTH: 300px; HEIGHT: 60px" name=Sign rows=5 cols=60></TEXTAREA>
    </TD>
  </TR>-->
  </TBODY>
  </TABLE>
   </FORM>
  </div>

<SCRIPT type=text/javascript>
     function changeimage()
	 {
		  $("#UserFace").val("<%=path %>/images/face/"+$("#Image").val()+".gif");
		  $("#imgIcon").attr("src",'/Images/Face/'+$("#Image").val()+'.gif');
	 }
	 
</SCRIPT>

<div id="relevantCopies" style="display:none;">
<TABLE width="80%" border=1 align=center cellPadding=3 cellSpacing=0 bordercolor="#333333">
  <TBODY>
  
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon1  
      height=60 src="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.yingyezhizhao }" width=60 
      border=1 name=showimages> <BR>
        <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.yingyezhizhao }"><FONT 
      color=red>查看大图</FONT></A>
    </TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">营业执照</span></TD>
  </TR>
 
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon2   
      height=60 src="<%=path %>/images/pfms/shuiwudengjizheng_${pfmsUser.userId }.${pfmsUser.shuiwudengjizheng }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.shuiwudengjizheng }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
  </TR>
   <TR class=tdbg>
    <TD colSpan="2" height="22"><span class="STYLE1">税务登记证</span></TD>
  </TR>
  
  
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon3   
      height=60 src="<%=path %>/images/pfms/zuzhijigoudaimazheng_${pfmsUser.userId }.${pfmsUser.zuzhijigoudaimazheng }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.zuzhijigoudaimazheng }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">组织机构代码证</span></TD>
  </TR>
  
	
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon4   
      height=60 src="<%=path %>/images/pfms/farenshenfenzheng_${pfmsUser.userId }.${pfmsUser.farenshenfenzheng }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.farenshenfenzheng }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">法人身份证</span></TD>
  </TR>
	
  
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon5   
      height=60 src="<%=path %>/images/pfms/zizhidengjizhengshu_${pfmsUser.userId }.${pfmsUser.zizhidengjizhengshu }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.zizhidengjizhengshu }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">资质等级证书 </span></TD>
  </TR>
	
  
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon6   
      height=60 src="<%=path %>/images/pfms/xinyongdengjipingguzhengshu_${pfmsUser.userId }.${pfmsUser.xinyongdengjipingguzhengshu }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.xinyongdengjipingguzhengshu }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">信用等级评估证书 </span></TD>
  </TR>
	
  
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon7   
      height=60 src="<%=path %>/images/pfms/qitazhengshu_${pfmsUser.userId }.${pfmsUser.qitazhengshu }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.qitazhengshu }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">其他证件</span></TD>
  </TR>
</TBODY></TABLE>
  </div>
  
  
<div id="memberProfile" style="display:none;">
	  <FORM id=myform name=myform action="alterMemberProfile.action" method="post">
		<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
		  <TBODY>
		  <TR class=tdbg>
		    <TD width="28%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">备注信息：</SPAN><BR>
		      其他需要补充说明的信息<br><br>
		    </TD>
		  </TR>
		  <tr>
		  	<td>
		    	${pfmsUser.note }
		    </td>
		  </tr>
		  </TBODY>
		</TABLE>
		<!-- <div style="text-align: center; width: 100%">
			<s:textarea name="pfmsUser.note"  id="note" cols="60" rows="7" cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
		</div> -->
	</FORM>
</div>
</BODY>
</HTML>

