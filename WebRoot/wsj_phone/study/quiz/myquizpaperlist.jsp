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

		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
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
	<BODY>
	
	<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">

			<li>
				<span style="font-weight: bold;">我的考场信息</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>-->
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align:left;width:320px;">
			<table width="320" align="center" cellspacing="1" cellpadding="1" bgcolor="#D1E4F5">
				<caption>
					考场信息
				</caption>
				<tr>
					<td width="100" height="22" align="center" bgcolor="#F8FCFE">
						考场标题
					</td>
					<td align="center" bgcolor="#F8FCFE">
						<s:property value="myroom.examroom.title" />
					</td>
				</tr>
				<tr>
					<td height="22" align="center" bgcolor="#F8FCFE">
						开始时间
					</td>
					<td align="center" bgcolor="#F8FCFE">
						<s:date name="myroom.examroom.begintime"
							format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>

					<td height="22" align="center" bgcolor="#F8FCFE">
						结束时间
					</td>
					<td align="center" bgcolor="#F8FCFE">
						<s:date name="myroom.examroom.endtime"
							format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td height="22" align="center" bgcolor="#F8FCFE">
						试卷数量
					</td>
					<td align="center" bgcolor="#F8FCFE">
						<s:property value="myroom.epsize" />
					</td>
				</tr>
			</table>
			<s:if test="myExamPapers.size==0">
<br>
						您还未开始作答作答此试卷
			</s:if>
			<s:else>
				<table width="320" align="center" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">
					<caption>
						考场试卷
						<s:if test="myroom.examroom.type==1">
							<h5 style="color: red">
								此为选拨式考场,需要练习达到条件，经过筛选通过后才能进行考试！
							</h5>
						</s:if>
					</caption>
					<tr>
						<td height="30" align="center" bgcolor="#F8FCFE">
							交卷时间
					  </td>
						<td height="30" align="center" bgcolor="#F8FCFE">
							得分/是否通过
					  </td>
						<td height="30" align="center" bgcolor="#F8FCFE">&nbsp;
							

						</td>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="myExamPapers" status="mepstat">
							<tr>
								<td height="30" align="center" bgcolor="#F8FCFE">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td height="30" align="center" bgcolor="#F8FCFE">
									<s:if test="examPaper.scorelook==1">
										<span style="color: red"><s:property value="myScore	" />/
											<s:if test="ispassed==1">是</s:if> <s:else>否</s:else> </span>

									</s:if>
									<s:else>
										<span style="color: red">不可查看</span>
									</s:else>
								</td>
								<td height="30" align="center" bgcolor="#F8FCFE">
									<s:if test="examPaper.quizlook==1">
										<s:if test="status == 2 || status == 3 ">
											<a target="_blank"
												href='myquizpaperview.action?myExamPaper.id=<s:property value="id"/>'
												class="textbg5">查看答卷</a>
										</s:if>
									</s:if>
									<s:if test="status != 2 &&status != 3 ">
										<s:if
											test="myroom.examroom.isMacBand==0&&myroom.examroom.isIpLimit==0">
											<a href="javascript:enterEroom('<s:property value="id"/>');"
												class="textbg5">开始作答</a>
										</s:if>
										<s:else>
											<a target="_blank"
												href="qpracInit.action?myExamPaper.id=<s:property value="id"/>"
												class="textbg5">开始作答</a>
										</s:else>
									</s:if>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
      </s:else>
			<s:if test="examRoom.pwdneed==0">
				<s:if test="Return=='stcl'">
					<input type="button" value="返回"
						onclick="document.location='quizpaperinit.action?myroom.examroom.id=${examRoom.id }&Return=stcl'"
						class="textbg4" />
				</s:if>
				<s:else>
					<input type="button" value="返回"
						onclick="document.location='quizpaperinit.action?myroom.examroom.id=${examRoom.id }&Return=<s:property value="Return"/>'"
						class="textbg4" />
				</s:else>
			</s:if>
			<s:else>
				<input type="button" value="返回" onClick="history.back(-1)"
					class="textbg4" />
			</s:else>

        <span style="text-align: center"></span> <span style="text-align: center"></span></div>
		<!-- 内容 -->
	
	</body>
</HTML>
