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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system003.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage003.css" />
        <link href="wsj_phone/css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript">
		function disNopassInfo(classid){
			width=420;
			height=360;
		   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
		}
		</script>
		<style type="text/css">
td {
	font-size: 13px;
	color: #333333;
	line-height: 150%
}

        </style>
	<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<BODY>
	
		<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="证书列表" />
							</div>
						</li>
						<li>
				<span style="font-weight: bold;">证书查看</span>
			</li><!--
				<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="mydiploma_result_np.action"> 未通过的证书 </a>
			</li>
		
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>-->
		<table width="320" border="0" align="left" cellpadding="0" cellspacing="0" style="margin-bottom:10px;">
        <s:iterator value="myClasses">
		  <tr>
		    <td width="76%"><table width="320" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
		      <tr>
		        <td height="30" colspan="4" align="right" bgcolor="#F8FCFE" style="color:#00F;"><table width="320" border="0" align="left" cellpadding="0" cellspacing="1" style="border-bottom:1px dashed #000;">
		          <tr>
		            <td width="150" height="30" align="right" style="color:#00F;">培训班名称：</td>
		            <th align="left" style="color:#ff6600; font-weight:bold;"><s:property value="elClass.name" /></th>
	              </tr>
	            </table></td>
	          </tr>
		      <tr>
		        <td width="85" height="30" align="left" bgcolor="#F8FCFE" style="color:#00F;">创建者：</td>
		        <td width="80" bgcolor="#F8FCFE"><s:property value="user.realname" /></td>
		        <td width="85" align="left" bgcolor="#F8FCFE" style="color:#00F;">我的证书：</td>
		        <td bgcolor="#F8FCFE">	<a target="_blank"
									href="mydiploma_view.action?elclass.id=<s:property value="elClass.id"/>"
									class="textbg5">查看证书：</a></td>
	          </tr>
		      <tr>
		        <td height="30" align="left" bgcolor="#F8FCFE" style="color:#00F;">证书名称：</td>
		        <td bgcolor="#F8FCFE"><s:property value="elClass.certificatename" /></td>
		        <td align="left" bgcolor="#F8FCFE" style="color:#00F;">培训班详情：</td>
		        <td bgcolor="#F8FCFE"><a href="myelclass_view.action?elclass.id=<s:property value="elClass.id"/>"
								onclick="return iselClass('
			  <s:property value="elClass.status" />');"
								class="textbg5">查看详情</a></td>
	          </tr>
	        </table></td>
	      </tr>
          <tr>
          <td height="2" style="background-color:#DFF8FF; background-position:center bottom;"colspan="2"></td>
          </tr>
     </s:iterator>  
    </table>
    <p>&nbsp;</p>

		<p>&nbsp;</p>
		<form action="mydiploma_result_p.action" method="post"
			name="mydiploma_result_p">
      <s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
		</form>
		<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i; 
							mydiploma_result_p.submit();
						}
					</script>
		<div style="text-align:left;width:320px;"><wysLib:page_cisco></wysLib:page_cisco></div>
	
	</body>
</HTML>
