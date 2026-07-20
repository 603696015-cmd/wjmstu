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
		<TITLE>学籍查询管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
			<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="个人收支明细列表" /></div>
			</li>
			<!--<li>
					 学籍查询管理
				</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<form action="getstudentcousebyuserid.action" method="post"
			name="acc_list">
			<s:hidden name="pN" id="pageNow"/>
			<s:hidden name="pS" />
			
		<table width="100%">
			<tr>
			<td valign="top" align="left">
				<table width="70%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						 学员学分详情
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left" onclick="alterFee(this,1)">
						课程名称
						</td>
					   <td height="30" align="center" >
						课程类别
					  </td>
					    <td height="30" align="center" >
						考试成绩
						</td>
						<td height="30" align="center" >
						是否通过
						</td>
						<td height="30" align="center" >
						课程学分
						</td>	
						<td height="30" align="center" >
						获得学分
						</td>	
					</tr>
					<s:if test="myCourses.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前没有记录
							</TD>
						</TR>
					</s:if>
					<s:else><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="myCourses">
							<tr>
							
							    <td height="30" style="padding-left:8px;color:blue;" align="left">
							    
							      <SPAN  style="color: red"><s:property value="course.name"  /></SPAN>

							      
						       </td>
						         <td height="30" align="center"  >
						         	<s:property value="course.ctype.name" />
						         </td>
						       
		<s:if  test="myRoom.id!=0">		       
				<s:if test="myRoom.sspassed==3">
						       <td height="30" align="center" colspan="2" >
						        	未开考
						       </td>
				</s:if>
				<s:else>
						<s:if test="myRoom.sspassed==1">
								<td height="30" align="center"  >
						        	<s:property value="myRoom.myScore"  />
						       </td> 
						       <td height="30" align="center"  >
						        通过
						       </td> 
						       
						 </s:if>
						 <s:else>
							    
							     <td height="30" align="center"  >
							        	<s:property value="myRoom.myScore"  />
							       </td> 
							       <td height="30" align="center"  >
							       		未通过
							     </td> 
						 </s:else>
				</s:else>
		</s:if>
		<s:else>
				<s:if test="className!=null" >
					<td height="30" align="center"  >
							无考场
					</td>					
				
					<s:if test="passed==true" >
						<td height="30" align="center"  >
								通过
						</td>	
					</s:if>
					<s:else>
					<td height="30" align="center"  >
								未通过
						</td>
						
					</s:else>
				</s:if>
				<s:else>
					<td height="30" align="center" colspan="2" >
						        无考场
					</td>
				</s:else>
		</s:else>
									
								
                                <td height="30" align="center" >
                                <s:if test="className==null">
                                	<s:property value="course.credit" />
                                </s:if>
                                <s:else>
                                	<s:property value="course.setcredit" />
                                </s:else>
								</td>
                                <td height="30" align="center" >
									<s:property value="myCredit" />
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
