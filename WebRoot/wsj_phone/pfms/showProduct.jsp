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
class=shadow id=locationid>产品管理</SPAN></DIV>
<DIV class=tabs>
<UL>
  <LI class=select><A href="http://www.sopia.cc/user/User_EditInfo.asp">查看产品</A> 
  </LI>
  </UL></DIV>

  <FORM id=myform name=myform onSubmit="" action="">
	<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  		<TBODY>
		  <TR class=tdbg>
		  	<input type="hidden" name="product.id" value='<s:property value="product.id"/>' />
		  	<input type="hidden" name="product.userId" value='<s:property value="product.userId"/>' />
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品名称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.name"/>
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">所属栏目：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; 
		    <s:property value="product.lanmu.lanmu"/>
		    </TD>
		    
		    <TD width="25%"><SPAN 
		      style="FONT-WEIGHT: bold">产品关键词：</SPAN>
		    </TD>
		    <TD width="25%"><s:property value="product.key"/>
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">市场价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.shichangjia"/> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">会员价：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.huiyuanjia"/>
		    </TD>
		  </TR>
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品发布者：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.fabuzhe"/>
		    </TD>
		  </TR>
		   <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">库存设置(数量)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.shuliang"/> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">库存设置(报工数)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp;<s:property value="product.baojingshu"/> 
		    </TD>
		  </TR>
		   <!-- <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">点击数(今日)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.dianjishujinri"/>
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">点击数(本周)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.dianjishubenzhou"/>
		    </TD>
		  </TR>
		   
		
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">点击数(本月)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.dianjishubenyue"/>
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">点击数(总计)：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.dianjishuzongji"/>
		    </TD>
		  </TR> -->
		  <tr class=tdbg>
		  	<td width="25%" height=22>
				<SPAN style="FONT-WEIGHT: bold">
					产品图片：
					<s:if test="product.chanpintupian != null">															
						<img src="<s:property value="product.chanpintupian_"/>" width="100" height="80" />
					</s:if>
					<s:else>
						<img src=""  width="100" height="80" /> 
					</s:else> 
				</SPAN>
			</td>
		  </tr>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">商品型号：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.shangpinxinghao"/>
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">商品规格：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.shangpinguige"/> 
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">生产商：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.shengchanshang"/> 
		    </TD>
		    
		    <TD width="25%">
		    	<SPAN style="FONT-WEIGHT: bold">商品商标：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.shangpinshangbiao"/>
		    </TD>
		  </TR>
		  
		  <TR class=tdbg>
		    <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">发布时间：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.fabushijian"/>
		    </TD>
		    
		    <!-- <TD width="25%" height=22>
		    	<SPAN style="FONT-WEIGHT: bold">产品公司名称：</SPAN>
		    </TD>
		    <TD width="25%">&nbsp;&nbsp; <s:property value="product.productCompanyName"/>
		    </TD> -->
		  </TR>
		  
		  <tr>
		  	<TD width="25%">
		    	<SPAN  style="FONT-WEIGHT: bold">产品介绍：</SPAN>
		    </TD>
		    <TD width="25%"><s:property value="product.jieshao"/>
		    </TD>
		  </tr>
		  
		  
		  </TBODY>
		</TABLE>
		<div>
	    	${product.jianjie_ }
	    </div>
  </FORM>

	</body></HTML>
