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
		<TITLE>学籍查询管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<BODY>
	
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="个人收支明细列表" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>-->
			
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center; width:320px;">
		<form action="balance_incomeList.action" method="post"
			name="acc_list">
			<s:hidden name="pN" id="pageNow"/>
			<s:hidden name="pS" />			
		<table width="100%">
			<tr>
			<td valign="top" align="left">
				<table width="320" align="center" cellpadding="0" cellspacing="1"
					bgcolor="#D1E4F5">
					<caption>
						 收支明细
					</caption>
					<tr>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;" onClick="alterFee(this,1)">
						收支
						</td>
					   <td height="30" align="center" bgcolor="#F8FCFE" >
						金额
				    </td>
					    <td height="30" align="center" bgcolor="#F8FCFE" >
						时间
					  </td>
						
						<td height="30" align="center" bgcolor="#F8FCFE" >
						备注
						</td>	
					</tr>
					<s:if test="li.size==0">
						<TR>
							<TD colspan="4" align="center" bgcolor="#F8FCFE">
								当前没有记录
							</TD>
                           
						</TR>
					</s:if>
					<s:else><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="li">
							<tr>
							
							    <td height="30" align="center" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;">
													<s:if test="typeflag==2">
														<SPAN style="color: red"><s:property
																value="Typeflagname" />
														</SPAN>
													</s:if>
													<s:else><s:property value="Typeflagname"  /></s:else>
						       </td>
                                <td height="30" align="center" bgcolor="#F8FCFE" >
									<s:property value="balance" />
								</td>
                                <td height="30" align="center" bgcolor="#F8FCFE" >
									<s:date  name="date"	format="yyyy-MM-dd HH:mm:ss"  />
								</td>
											
								<td height="30" align="center" bgcolor="#F8FCFE" >
							   <s:if test="type==4">
								   <a href="balance_ordercommodity.action?orderid= <s:property value="id" />" class=textbg4>查 看</a>
								</s:if>
								<s:else>
								   <a href="balance_incomerechargeInfo.action?orderid= <s:property value="id" />" class=textbg4>查 看</a>
								</s:else>
								</td>
							</tr>
						</s:iterator></tbody>
					</s:else>
			  </table>
		<script>
			</script>
			</td></tr></table><wysLib:page></wysLib:page>
			</form>
		</div>

		<!-- 内容 -->
	
	</body><script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<!--<form action="schoolrolls.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
			</form>
-->
</HTML>
