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
		<TITLE>各章节学习详情</TITLE>
		<base href="<%=basePath%>" target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function displayPasstime(){
				//$("#processDiv").html("<div style='background:#ff0000;width:"+width+"px;height:100%'></div>");
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="章节详情" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
					<table width="100%" border="0" cellpadding="1" cellspacing="1">
						  <tr>
						    <td>课程名称：<s:property value="myCourse.course.name"/></td>
						    <td width="400" align="right">规定时长：<s:property value="myCourse.course.during" />      
						      分钟，已完成时长：
						      <s:property value="myCourse.passtimeStr" />
						      分钟，实际学习时长：
						      <s:property value="myCourse.passtime2Str" />
						      </td>
						    <td width="110" align="right">总进度：
						      <s:property value="myCourse.process_" />%		</td>
						    <td width="152" align="left" style="padding:0xp;"><span style="width:150px;border: 1px dotted #FF6633;">
														<IMG height=14 src="images/jd.gif" width="<s:property value="myCourse.process" />%">						</span></td>
						  </tr>
						</table>
				<table width="98%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						
					</caption>
					<tr>
						<th width="60" height="30" align="center" >
							序号						</th>
						<th width="200" height="30" align="center" >
							章节名称						</th>
						<!-- 
						<th width="200" height="30" align="center" >
							练习名称						</th>	
							 -->
						<th width="200" height="30" align="center" >
							章节考场						</th>	
						<th width="100"  height="30" align="center" >
							练习最高分						</th>
						<th width="150" height="30" align="center" >
							是否通过						</th>
						<th width="150" height="30" align="center" >
							完成标准						</th>
						<th width="150" height="30" align="center" >
							规定时长						</th>
						<th width="150" height="30" align="center" >
							已学时长						</th>
						<th width="200" height="30" align="center" >
							学习进度						</th>
					    <th align="center" >&nbsp;</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="myCPages" status="statu">
							<tr>
								<td width="60" height="30" align="center" >
									<s:property value="#statu.count"/>								</td>
								<td height="30" align="center" >
									<a style="color:#cc0099;" href="course_study.action?coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
										<s:property value="cpage.title"/>
									</a>								</td>
								<!-- 
								<td height="30" align="center" >
									<s:if test="pracp.title==null"><span style="color:red">无练习</span></s:if>
									<s:else>
										<a style="color:#cc0099;"  href="practice_paper.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>&coursePage.id=<s:property value="cpage.id"/>&examPaper.id=<s:property value="pracp.examPaper.id"/>&myPractice.ppaper.id=<s:property value="pracp.id"/>">
											<s:property value="pracp.title"/>
										</a>									</s:else>								</td>	
								 -->
								<td height="30" align="center" >
										<a style="color:#cc0099;"  href="quizpaperinit.action?course.id=<s:property value="courseid"/>&myroom.examroom.id=<s:property value="examRoom.id"/>&coursePage.id=<s:property value="examRoom.cpid"/>">
											<s:property value="examRoom.title"/>
										</a>																
								</td>	
								<td  height="30" align="center" >
								<s:if test="pracp.title!=null">
									<s:property value="myscore"/>		
								</s:if><s:else>
									-
								</s:else>
																</td>
								<td  height="30" align="center" >
								<s:if test="pracp.title!=null">
									<s:if test="passed2==1">是</s:if>
									<s:else>否</s:else>			
								</s:if><s:else>
									-
								</s:else>							</td>
								<td height="30" align="center" >
									<s:property value="cpage.getcreditName"/>								</td>
								<td height="30" align="center" >
									<s:property value="cpage.during"/>分钟								</td>
								<td height="30" align="center" >
									<s:property value="passtime2Str"/> 								</td>
								<td width="200" height="30" align="left" >
									<div style="border: 1px dotted #FF6633;">
										<IMG height=14 
                  						src="images/jd.gif" width="<s:property value="process" />%">
                  					</div>
                  				</td>
							    <td width="40" align="center" bgcolor="#FFFFFF" style="padding:0px;">
							    	<s:property value="processStr" />%
							    </td>
							</tr>
						</s:iterator>
						<tr>
							  <td width="60" height="30" align="center" >&nbsp;</td>
							  <td height="30" colspan="8" align="left" >说明：点击章节名称可进入对应章节的学习页，点击练习名称可进入练习作答页</td>
					  </tr>
					</tbody>
			  </table> 
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
