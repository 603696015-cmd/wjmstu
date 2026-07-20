
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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system003.css" />
		<link rel="stylesheet" type="text/css" href="css/manage003.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程结业考试</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="myExamPapers.size==0"><span style="color:red;">您当前没有需要参加的结业考试</span></s:if>
			<s:else>
			  <s:iterator value="myrooms">
				<table width="100%" border="0" cellpadding="0" cellspacing="10">
				  <tr>
				    <td width="24%">
				    	 <s:if test="examroom.mainimg==null">
				   		<img src="elfrontimages/coursedimg.jpg" width="250" height="170" alt="">
				    </s:if>
				    <s:else>
				     <img src="<s:property value='examroom.mainimg_' />" width="250" height="170" alt="">
				    
				    </s:else>
				    </td>
				    <td width="76%"><table width="100%" border="0" cellspacing="5" cellpadding="0">
				      <tr>
				        <td colspan="4"><table width="100%" border="0" cellspacing="5" cellpadding="0"  style="border-bottom:1px dashed #000;">
				          <tr>
				            <td width="148" align="right" style="color:#00F;">考场名称：</td>
				            <th  align="left" valign="middle" style="color:#ff6600; font-weight:bold;"><s:property value="examroom.title" /></th>
			              </tr>
			            </table></td>
			          </tr>
				      <tr>
				        <td width="150" height="30" align="right" style="color:#00F;">所属培训班：</td>
                       
				        <td width="200">单独分配而来</td>
                        
				        <td width="150" align="right" style="color:#00F;">创建者：</td>
				        <td><s:property value="examroom.course.creater.realname" /></td>
			          </tr>
				      <tr>
				        <td height="30" align="right" style="color:#00F;">课程名称：</td>
				       
                        <td><s:property value="examroom.course.name" /></td>
                        
				        <td align="right" style="color:#00F;">考卷数量：</td>
				        <td><s:property value="epsize" /></td>
			          </tr>
				      <tr>
				        <td height="30" align="right" style="color:#00F;">开始时间：</td>
				        <td><s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm" /></td>
				        <td align="right" style="color:#00F;">是否通过：</td>
				        <td><s:if test="ispassed==1">是</s:if>
						<s:else>否</s:else></td>
			          </tr>
				      <tr>
				        <td height="30" align="right" style="color:#00F;">结束时间：</td>
				        <td><s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm" /></td>
				        <td align="right" style="color:#00F;"></td>
				        <td><a href="quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&iscommon=0" class="textbg5">查看详情</a></td>
			          </tr>
			        </table></td>
			      </tr>
			  </table>
            </s:iterator>
				<p>&nbsp;</p>
				<form action="myquiz_list.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow">
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				
				function enter(id){
					//alert(id);
					//document.location.href="qpracInit.action?examRoom.id="+id;
					//document.myForm.submit();
					//window.open("quizpaper.action?myExamPaper.id="+id+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				}
			</script>
				<wysLib:page_cisco></wysLib:page_cisco>
			</s:else> 
			<%-- 
			<div style="margin-top: 0px; text-align: center; <s:if test="myExamPapers_xbs.size == 0">display:none</s:if>" >　
				<iframe id="myquiz_xbsFrame" src="myquiz_list_xbs.action" width=100%  height="600"
					marginwidth="0" marginheight="0" frameborder=0 ></iframe>　
			</div>
			 --%>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
			