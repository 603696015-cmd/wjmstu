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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system003.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage003.css" />
        <link href="wsj_phone/css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	<style type="text/css">
	
	</style>
    
	</HEAD>
	<BODY>
	
	<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考核考试列表</span>
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
			<s:if test="myrooms.size==0">
				<br>
				<div style="width: 100%; text-align: center; "><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><span style="color:red;"><strong>您当前没有需要参加的考试!</strong></span></td>
    </tr>
</table>
</div>
			</s:if>
			<s:else>
			  <s:iterator value="myrooms">

				<table width="320" border="0" cellspacing="0" cellpadding="0"  style="font-size:13px;">
				  <tr>
				    
				    <td>
                    <table bgcolor="#D1E4F5" width="320" border="0" align="left" cellpadding="0" cellspacing="1" style="font-size:13px;">
				      <tr>
				        <td colspan="4">
                        <table width="100%" border="0" cellspacing="1" cellpadding="0" style="border-bottom:1px dashed #000;">
				          <tr>
				            <td height="30" align="left" bgcolor="#F8FCFE" style="color:#00F;">考场标题：</td>
    <th align="left" valign="middle" bgcolor="#F8FCFE" style="color:#ff6600; font-weight:bold;"><s:if test="examroom.isApplication == 1">
										<SPAN style="color: red">【申请】</SPAN>
									</s:if>
									<s:elseif test="examroom.isApplication == 2">
										<SPAN style="color: blue;">【全工】</SPAN>
									</s:elseif>
									<s:else>
										<SPAN style="color: gray">【分配】</SPAN>
									</s:else>
							<s:property value="examroom.title" /></th>
			              </tr>
			            </table></td>
			          </tr>
				      <tr>
				        <td width="100" height="30" align="left" bgcolor="#F8FCFE" style="color:#00F;">创建者：</td>
				        <td bgcolor="#F8FCFE"><s:property value="examroom.creater.realname" /></td>
				        <td width="70" align="left" bgcolor="#F8FCFE" style="color:#00F;">试卷数量：</td>
				        <td bgcolor="#F8FCFE"><s:property value="epsize" /></td>
			          </tr>
				      <tr>
				        <td height="30" align="left" bgcolor="#F8FCFE" style="color:#00F;">考场开始时间： </td>
				        <td bgcolor="#F8FCFE"><s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm:ss" /></td>
				        <td align="left" bgcolor="#F8FCFE" style="color:#00F;">成绩：</td>
				        <td bgcolor="#F8FCFE"><s:property value="myScore" /></td>
			          </tr>
				      <tr>
				        <td height="30" align="left" bgcolor="#F8FCFE" style="color:#00F;">考场结束时间：</td>
				        <td bgcolor="#F8FCFE"><s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm:ss" /></td>
				        <td align="left" bgcolor="#F8FCFE" style="color:#00F;">是否通过：</td>
				        <td bgcolor="#F8FCFE"><s:if test="ispassed==1">是</s:if>
									<s:else>否</s:else></td>
			          </tr>
				      <tr>
				        <td height="30" colspan="4" align="center" bgcolor="#F8FCFE"><s:if test="examroom.type == 1">
				          <a
											href='quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list'
											class="textbg5">进入练习</a>
				          </s:if>
				          <s:else>
				            <s:if test="ispassed==3">
				              <a
												href='quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list'
												class="textbg5">进入考场</a>
				              </s:if>
				            <s:else>
				              <a
												href='quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list'
												class="textbg5">查看详情</a>
				              </s:else>
                          <span style="text-align: center"></span> <span style="text-align: center"></span></s:else></td>
			          </tr>
			        </table></td>
			      </tr>
			  </table>
          </s:iterator>
				<form action="listErsWithoutC.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow">
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script type="text/javascript">
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
			</script>
				<wysLib:page_cisco></wysLib:page_cisco>
			</s:else>
			<%-- 
			<div style="margin-top: 0px; text-align: center; <s:if test="myrooms_xbs.size == 0">display:none</s:if>" >　
				<iframe id="houC_xbsFrame" src="listErsWithoutC_xbs.action" width=100%  height="600"
					marginwidth="0" marginheight="0" frameborder=0 ></iframe>　 
			</div>
			 --%>
		</div>


		<!-- 内容 -->
	
	</body>
</HTML>
