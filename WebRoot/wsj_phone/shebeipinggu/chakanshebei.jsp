<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>设备登记管理</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet><LINK 
href="<%=path %>/css/css.css" type=text/css rel=stylesheet>
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/js/jquery2.js"></script>
<script type="text/javascript" src="<%=path %>/js/stuffman.js"></script>
<script type="text/javascript" src="<%=path %>/js/cexampaper.js"></script>
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
class=shadow id=locationid></SPAN></DIV>
<DIV class=tabs>
<UL>
  <LI class=select><A href="http://www.sopia.cc/user/User_EditInfo.asp">查看设备</A> 
  </LI>
  </UL></DIV>
<SCRIPT type=text/javascript>$('#locationid').html("查看信息");</SCRIPT>


  <FORM id=myform name=myform>
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
  
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备名称：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp; ${shebei.name } </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">型号：</SPAN></TD>
		    <TD width="25%">${shebei.xinghao }
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备地址：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      ${shebei.shebeidizhi }</TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">邮政编码：</SPAN></TD>
		    <TD width="25%">${shebei.postalcode }
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备使用证号：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp; ${shebei.shebeiusezhenghao }</TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">设备使用有效期：</SPAN></TD>
		    <TD width="25%">${shebei.shebeiuseyouxiaoqi }&nbsp;&nbsp;&nbsp;(年)
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">使用区域：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp; ${shebei.shebeiusequyu }</TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">使用类型：</SPAN></TD>
		    <TD width="25%">${shebei.shebeiusequyu }
		      </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">设备类型：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      ${shebei.shebeileixing }
		      </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">设备等级：</SPAN></TD>
		    <TD width="25%">${shebei.shebeidengji }
		      </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">生产厂家：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      ${shebei.shengchanchangjia }
		      </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">出厂日期：</SPAN></TD>
		    <TD width="25%"><s:date name="shebei.chuchangriqi" format="yyyy-MM-dd"/>
		      </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">检验期限：</SPAN></TD>
		    <TD width="25%">&nbsp;&nbsp;
		      ${shebei.jianyanqixian }&nbsp;&nbsp;&nbsp;(年)
		      </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">检验日期：</SPAN></TD>
		    <TD width="25%"><s:date name="shebei.jianyanriqi" format="yyyy-MM-dd"/>
		      </TD>
		  </TR>
		   
		  <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">开始时间(保险期间)：</SPAN>
		    </TD>
		    <TD width="25%"><s:date name="shebei.kaishishijian" format="yyyy-MM-dd"/>
		    </TD>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">结束时间(保险期间)：</SPAN>
		    </td>
		    <td>
		      <s:date name="shebei.jieshushijian" format="yyyy-MM-dd"/>
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">投保状态：</SPAN>
		    </TD>
		    <TD width="25%">
		    	<s:property value="shebei.toubaozhuangtai_entity.toubaozhuangtai"/>
		    </TD>
		    <TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">登记编号：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;
		      <s:property value="shebei.dengjibianhao"/>
		    </TD>
		  </TR>
		
		  <TR class=tdbg>
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">设备特色：</SPAN></TD>
		    <TD width="25%">
		    		${shebei.shebeitese }
		    </TD>
		  	<TD width="25%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">备注：</SPAN></TD>
		    <TD colspan="3">&nbsp;&nbsp; 
		    		<s:property value="shebei.beizhu"/>
		    </TD>
		  </TR>
		  
		  
		  </TBODY>
		</TABLE>
		<div>
	    	${shebei.shebeijianjie_}
	    </div>
  </FORM><td width="25%">

<p>&nbsp;</p>

	</body></HTML>

