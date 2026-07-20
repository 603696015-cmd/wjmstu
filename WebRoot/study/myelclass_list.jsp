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
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system003.css" />
		<link rel="stylesheet" type="text/css" href="css/manage003.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	<style type="text/css">
	td{font-size:13px;
	}
	</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">在学培训班</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
	</table>
		
	<s:if test="myClasses.size==0">
			<div style="width: 100%; text-align: center; margin-top: 30px;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%" align="right"><img src="http://ico.ooopic.com/iconset01/ose/gif/60790.gif" width="128" height="128"></td>
    <td><font size="+1" color="#FF0000"> 您当前没有需要参加的培训班!</font></td>
  </tr>
</table>

                
		  </div>
		</s:if>
		<s:else>
        
       
      <table width="100%" border="0" cellpadding="0" cellspacing="10">
       <s:iterator value="myClasses">
<tr>
  <td width="200" align="center" valign="middle" >
  	 <s:if test="elClass.mainimg==null">
	     <img src="elfrontimages/coursedimg.jpg" width="250" height="170" alt="">
	    </s:if>
	    <s:else>
	    <img src="<s:property value='elClass.mainimg_' />" width="250" height="170" alt="">
	   
	    </s:else>
  </td>
  <td align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="5">
    <tr>  
      <th height="30" colspan="4"><table width="100%" border="0" cellspacing="5" cellpadding="0"  style="border-bottom:1px dashed #000;">
        <tr>
          <td  height="40" align="right" valign="middle" width="150" style="color:#00F;">培训班名称&nbsp;:</td>
          <th height="40" align="left" valign="middle" style="color:#ff6600; font-weight:bold;"><s:property value="elClass.name" /></th>
          </tr>
      </table></th>      
      </tr>
    
    <tr>
      <td width="150" height="30" align="right" style="color:#00F;">创建者&nbsp;:</td>
      <td><s:property value="elClass.creater.realname" /></td>
      <td align="right" style="color:#00F;">来源&nbsp;:</td>
      <td><SPAN 
        
        
        <s:if test="elClass.isjoin=='申请'">style="color:red"</s:if>
        >
        <s:property value="elClass.isjoin" />
        </SPAN></td>
    </tr>
    <tr>
      <td height="30" align="right" style="color:#00F;">创建时间&nbsp;:</td>
      <td><s:date name="elClass.createtime" format="yyyy-MM-dd" /></td>
      <td align="right" style="color:#00F;">加入时间&nbsp;:</td>
      <td><s:date name="begintime" format="yyyy-MM-dd" /></td>
    </tr>
    <tr>
      <td height="30" align="right" style="color:#00F;">开始时间&nbsp;:</td>
      <td><s:date name="elClass.starttime" format="yyyy-MM-dd HH:mm:ss" /></td>
      <td align="right" style="color:#00F;">结束时间&nbsp;:</td>
      <td><s:date name="elClass.finishtime" format="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
      <td height="30" align="right">&nbsp;</td>
      <td>&nbsp;</td>
      <td align="right">&nbsp;</td>
      <td align="right"><a href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />&Return=stclalist"
    class="textbg5">学习详情</a><a href="MyIntegraInit.action?elclass.id=<s:property value="elClass.id" />" class="textbg5">得分详情</a> </td>
    </tr>
  </table></td>

</tr>
    <tr>
    <td height="2" colspan="3" align="center" valign="middle" style=" background-color:#DFF8FF; background-position:center bottom;">
    </tr>
  </s:iterator>
</table>
      <form action="myelclass_list.action" method="post"
				name="myelclass_list">
<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
		  </form>
		  <script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i; 
							myelclass_list.submit();
						}
					</script>
			<div style="text-align: center;"><wysLib:page_cisco></wysLib:page_cisco></div>
		</s:else>
	</body>
</HTML>
