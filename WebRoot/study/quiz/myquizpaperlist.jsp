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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((                 this .                 sectionRowIndex %  
		              2 ==   
		
		            0) ?        
		        "#ffffff" :                 "#f4f4f4" )
}
.STYLE10 {font-size: 12px; color: #FF0000;}
        </style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function openQuiz(id){
			 if(confirm('确定开始答卷？'))
			 	//alert("quizpaper.action?myExamPaper.id="+id+"&myroom.examroom.id="+"<s:property value='myroom.examroom.id' />");
			 	window.open("quizpaper.action?myExamPaper.id="+id+"&myroom.examroom.id="+"<s:property value='myroom.examroom.id' />","quizpaper","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			 }
			 function enterEroom(id){
				var mw = window.open("quizpaper.action?myExamPaper.id="+id+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				if (window.screen){ 
					mw.moveTo(0, 0);
					mw.resizeTo(screen.availWidth,screen.availHeight);
				}
			}
			function refresh1(){
				document.location.href= document.location.href;
			}
			 window.onunload = function (){
		   		quizpaper.submit();
			}
		</script>
	</HEAD>
	<body>
	
	<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30">&nbsp;</td>
      </tr>
    </table>
	<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
	
	<!--		<table width="100%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					考场信息
				</caption>
				<tr>
					<th height="22" width="200" align="center">
						考场标题
					</th>
					<td align="center">
						<s:property value="myroom.examroom.title" />
					</td>
				</tr>
				<tr>
					<th height="22" align="center">
						开始时间
					</th>
					<td align="center">
						<s:date name="myroom.examroom.begintime"
							format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>

					<th height="22" align="center">
						结束时间
					</th>
					<td align="center">
						<s:date name="myroom.examroom.endtime"
							format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<th height="22" align="center">
						试卷数量
					</th>
					<td align="center">
						<s:property value="myroom.epsize" />
					</td>
				</tr>
			</table>
			
			-->
			<s:if test="myExamPapers.size==0">
<br>
						您还未开始作答作答此试卷
			</s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="0" cellspacing="1" bgcolor="#CFDBE2">
					<caption>
						<!--考场试卷-->
						<s:if test="myroom.examroom.type==1">
							<h5 style="color: red">
								此为选拨式考场,需要练习达到条件，经过筛选通过后才能进行考试！							</h5>
						</s:if>
					</caption>
					<tr>
					  <th height="30" colspan="5" align="left" background="images/bg002.jpg" style="padding-left:30px;"><span style="color:blue;font-size:14px;font-weight:bold;">考场名称：<s:property value="myroom.examroom.title" /></span></th>
				  </tr>
					<tr>
						<th width="50" height="40" align="center" background="images/bg002.jpg"><span class="STYLE10">
						  序号						</span></th>
						<th width="200" height="30" align="center" background="images/bg002.jpg"><span class="STYLE10">
						  交卷时间						</span></th>
						<th width="130" height="30" align="center" background="images/bg002.jpg"><span class="STYLE10">
						  考试状态						</span></th>
						<th width="200" height="30" align="center" background="images/bg002.jpg"><span class="STYLE10">
						  得分/是否通过						</span></th>
						<th height="30" align="center" background="images/bg002.jpg">&nbsp;						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="myExamPapers" status="mepstat">
							<tr>
								<td width="50" height="45"
									align="center" bgcolor="#F8FCFE" style="padding-left: 8px; color: blue;">
									<s:property value="#mepstat.index+1" />							  </td>
								<td width="200" height="45" align="center" bgcolor="#F8FCFE">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />							  </td>
								<td width="130" height="45" align="center" bgcolor="#F8FCFE">
									<s:property value="statusName" />							  </td>
								<td width="200" height="45" align="center" bgcolor="#F8FCFE">
									
									
									
									
									<s:if test="examPaper.scorelook==1">
										<span style="color: red"><s:property value="myScore	" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:if test="ispassed==1">是</s:if> <s:else>否</s:else> </span>									</s:if>
									<s:else>
										<span style="color: red">不可查看</span>									</s:else>							  </td>
								<td height="45" align="center" bgcolor="#F8FCFE">
									
									
									
									
									<s:if test="examPaper.quizlook==1">
										<s:if test="status == 2 || status == 3 ">
											
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a target="_blank"
												href='myquizpaperview.action?myExamPaper.id=<s:property value="id"/>'><span style="font-size:14px;font-weight:bold;color:white;">查看答卷</span></a></td>
                                              </tr>
                                            </table>
																	</s:if>
									</s:if>
									<s:if test="status != 2 &&status != 3 ">
										<s:if
											test="myroom.examroom.isMacBand==0&&myroom.examroom.isIpLimit==0">
											
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="javascript:enterEroom('<s:property value="id"/>');"><span style="font-size:14px;font-weight:bold;color:white;">开始作答</span></a></td>
                                              </tr>
                                            </table>
																				</s:if>
										<s:else>
											
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a target="_blank"
												href="qpracInit.action?myExamPaper.id=<s:property value="id"/>"><span style="font-size:14px;font-weight:bold;color:white;">开始作答</span></a></td>
                                              </tr>
                                            </table>
																				</s:else>
									</s:if>							  </td>
							</tr>
						</s:iterator>
					</tbody>
			  </table>
			</s:else>
	</td>
  </tr>
</table>

	

			
		
		
	<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="50">&nbsp;</td>
      </tr>
    </table>
	</BODY>
</HTML>
