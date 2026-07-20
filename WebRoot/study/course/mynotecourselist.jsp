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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
        	<style type="text/css">
	td{font-size:13px;
	}
	</style>
	</HEAD>
	<body>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的课程笔记</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			
			<s:if test="myCourses.size==0">
            <div style="width: 100%; text-align: center;border:0px solid #C1EBFF; margin-top:200px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="48%" align="right"><img src="images/wrong.gif" /></td>
    <td><span style="color:red;"><strong>您当前没有需要学习的课程!</strong></span></td>
  </tr>
</table>
</div>
           </s:if>
			<s:else>
            
			  <s:iterator value="myCourses">
				<table width="100%" border="0" cellspacing="10" cellpadding="0" style="border-bottom:2px solid #DFF8FF;">
				  <tr>
				    <td width="200" align="center" valign="middle" >
				    <s:if test="course.mainimg==null">
				    <img src="elfrontimages/coursedimg.jpg" width="250" height="170" alt="">
				    </s:if>
				    <s:else>
				    <img src="<s:property value='course.mainimg_' />" width="250" height="170" alt="">
				    
				    </s:else>
				    </td>
				    <td width="75%"><table width="100%" border="0" cellspacing="5" cellpadding="0">
				      <tr>
				        <td colspan="4"><table width="100%" border="0" cellspacing="5" cellpadding="0" style="border-bottom:1px dashed #000;">
				          <tr>
				            <td height="40" align="right" valign="middle" width="146" style="color:#00F;">课程名称：</td>
				            <th align="left" style="color:#ff6600; font-weight:bold;"><s:property value="course.name" /></th>
			              </tr>
			            </table></td>
			          </tr>
				      <tr>
				        <td height="40" align="right" valign="middle" width="150" style="color:#00F;">总时间：</td>
				        <td width="100"><s:property value="course.during" />
								分钟</td>
				        <td align="right" valign="middle" width="150" style="color:#00F;">讲师：</td>
				        <td><s:property value="course.teacherName" /></td>
			          </tr>
				      <tr>
				        <td height="40" align="right" valign="middle" width="150" style="color:#00F;">已学时间：</td>
				        <td><s:property value="passtime" />
								分钟（<s:property value="processStr" />%）</td>
				        <td align="right" valign="middle"  style="color:#00F;">创建者：</td>
				        <td><s:property value="course.creater.realname" /></td>
			          </tr>
				      <tr>
				        <td height="40" align="right" valign="middle" width="150" style="color:#00F;">学习进度：</td>
				        <td><div style="border: 1px dotted #FF6633;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div></td>
				        <td colspan="2" align="right" style="padding-right:100px;"><a href="course_study_noteAddInit.action?course.id=<s:property value="course.id"/>" class="textbg5">做笔记</a></td>
			          </tr>
			        </table></td>
			      </tr>
			  </table>
              </s:iterator>
				
				<form action="mynotecourselist.action" name="erform" method="post">
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
				</script>			  
				<wysLib:page></wysLib:page>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
