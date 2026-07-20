<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.SystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
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
		<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>" />
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
.STYLE7 {color: #FF0000; font-size: 14; font-weight: bold; }
        body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function openQuiz(id){
				if(confirm('确定开始答卷？'))
			 	//alert("quizpaper.action?myExamPaper.id="+id+"&myroom.examroom.id="+"<s:property value='myroom.examroom.id' />");
			 	window.open("quizpaper.action?myExamPaper.id="+id+"&myroom.examroom.id="+"<s:property value='myroom.examroom.id' />","quizpaper","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			}
			function enterEroom(erid,epid){
			//20141014修改
				/*var mw = window.open("quizpaperinit_byepid.action?examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id=<s:property value="elclass.id" />&course.id=<s:property value="course.id" />&coursePage.id=<s:property value="coursePage.id" />&atetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				*/
				window.parent.location.href="quizpaperinit_byepid.action?examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id=<s:property value="elclass.id" />&course.id=<s:property value="course.id" />&coursePage.id=<s:property value="coursePage.id" />";
				if (window.screen){ 
					mw.moveTo(0, 0);
					mw.resizeTo(screen.availWidth,screen.availHeight);
				}
				
				setdisable();
				/*
				var widthheight = "dialogHeight:"+screen.height+"px;dialogWidth:"+screen.width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
				var mw = window.showModalDialog("quizpaperinit_byepid.action?examRoom.id="+erid+"&examPaper.id="+epid+"&datetime="+new Date(),"course_exam_5",widthheight);
				refresh1();*/
			}
			function refresh1(){
				window.onbeforeunload = null;
				window.setInterval(function(){
					//document.location.href= document.location.href;
					quizpaperinit.action=document.location.href;
					quizpaperinit.submit();
				},800);
			}
			 window.onunload = function (){
		   		quizpaper.submit();
			}
			function setdisable(){
				$("a[name='startExam']").each(function(){
					$(this).attr("disabled","disabled");
				});
				window.onbeforeunload=function(){
					window.event.returnValue="**********************************\n\n当前正在考试,不能离开本页面,以免造成错误!\n\n**********************************";
				}
			}
			
			function load_wjm_usere_center(){
				window.parent.location.href = "wjm_user_center.action";
			}
		</script>
	</HEAD>
	<body >
	
	
	<table width="1044" height="550" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" background="images/20140416/cent_bg3.png" style="padding-left:0px;padding-top:60px;">
    
	
			<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
	<form action="" name="quizpaperinit">
				<input type="hidden" name="myroom.examroom.id" value="<s:property value="myroom.examroom.id"/>"/>
				<input type="hidden" name="Return" value="<s:property value="Return"/>"/>
				<input type="hidden" value="<s:property value="examRoom.pwd"/>" name="myroom.examroom.pwd"/>
	  </form>
			<!--<table width="100%" align="center" cellspacing="1" cellpadding="1" bgcolor="#D1E4F5">
				<caption>
					考场信息
				</caption>
				<tr>
				  <td height="22" colspan="2" align="center" bgcolor="#F8FCFE"><table width="100%" border="0" cellspacing="1" cellpadding="0">
				    <tr>
				      <td height="30" align="center" bgcolor="#F8FCFE"><b>考场标题</b></td>
				      <td align="center" bgcolor="#F8FCFE"><span style="font-weight:bold;">开始时间 </span></td>
				      <td align="center" bgcolor="#F8FCFE"><b>结束时间</b></td>
				      <td align="center" bgcolor="#F8FCFE"><b>试卷数量</b></td>
			        </tr>
				    <tr>
				      <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="myroom.examroom.title" /></td>
				      <td align="center" bgcolor="#F8FCFE"><s:date name="myroom.examroom.begintime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
				      <td align="center" bgcolor="#F8FCFE"><s:date name="myroom.examroom.endtime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
				      <td align="center" bgcolor="#F8FCFE"><s:property value="myroom.epsize" /></td>
			        </tr>
			      </table></td>
			  </tr>
				<tr>
					<th height="22" align="center" >
						剩余次数
					</th>
					<td align="center" >
						<s:property value="myroom.examroom.examcount-myroom.srrcount" />
					</td>
				</tr>
			
			</table> 
			-->
			
			<s:if test="myeroom.myExamPapers.size==0">
<br>
						该考场没有安排我作答试卷，请与管理员联系。			</s:if>
			<s:else>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CFDBE2">
					<caption>
						<!--考场试卷-->
						<s:if test="myroom.examroom.type==1">
							<h5 style="color: red">
								此为选拨式考场,需要练习达到条件，经过筛选通过后才能进行考试！							</h5>
						</s:if>
					</caption>
					<tr>
						<th height="40" align="center" background="images/bg002.jpg" bgcolor="#F8FCFE"><span class="STYLE7">
						  试卷标题						</span></th>
						<%--<th width="90" height="30" align="center">
							考试状态
						</th>
						 
						<th height="30" align="center" >
							练习标题
						</th>
						<th width="130" height="30" align="center" >
							练习次数/我的次数
						</th>
						<th width="130" height="30" align="center" >
							最低分/最高分
						</th>
						 
					  <th height="30" align="center" bgcolor="#F8FCFE">
							得分计算方式
						</th>--%>
						<th height="30" align="center" background="images/bg002.jpg" bgcolor="#F8FCFE"><span class="STYLE7">
						  最高分/是否通过					  </span></th>
						<th height="30" align="center" background="images/bg002.jpg" bgcolor="#F8FCFE">
							<span class="STYLE7">&nbsp; 已考次数 </span></th>
<!--							<th height="30" align="center" background="images/bg002.jpg" bgcolor="#F8FCFE">&nbsp;							 				  </th>-->
						<!--<th height="30" align="center" bgcolor="#F8FCFE">
							&nbsp; 剩余次数					  </th>
						<th height="30" align="center" bgcolor="#F8FCFE">&nbsp;						</th>-->
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="myroom.myExamPapers">
							<s:if test="examPaper.title!=null">
								<tr>
									<td height="100"
										align="center" valign="middle" bgcolor="#F8FCFE" style="padding-left: 8px; color: blue;">
								  <s:property value="examPaper.title" /></td>
									<%-- <td width="90" height="30" align="center">
										<s:property value="statusName" />
									</td>
									
								<s:if test="examPaper.prac.id!=0">
									<td height="30" align="center" >
										<s:property value="examPaper.prac.title" />
									</td>
									<td width="130" height="30" align="center" >
										<s:property value="examPaper.prac.practimes" />
										/
										<s:property value="practimes" />
									</td>
									<td width="130" height="30" align="center" >
										<s:property value="examPaper.prac.pracscore" />
										/
										<s:property value="pracscore" />
									</td>

								</s:if>
								<s:else>
									<td colspan="3">
										无练习
									</td>
								</s:else>
								 --%>
							 	 <!-- <td align="center" bgcolor="#F8FCFE">
								 		<s:if test="examPaper.passmanner==1">平均分</s:if>
								 		<s:else>最高分</s:else>
								 	</td>-->
									<td width="150" height="30" align="center" valign="middle" bgcolor="#F8FCFE">
									  <s:if test="examPaper.scorelook==1">
											<%--<s:if test="status == 2 || status == 3 ">--%>
												<span style="color: red"><s:property value="myScore	" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<s:if test="ispassed==1">是</s:if> <s:else>否</s:else> </span>
											<%--</s:if>
											<s:else>-</s:else>--%>
									  </s:if>
										<s:else>
								  <span style="color: red">不可查看</span>										</s:else>									</td>
									<td align="center" valign="middle" bgcolor="#F8FCFE">
									   <s:if test="examIsCenter==-1">
												<font color="red">
													<s:property value="examIsCenterRemack" />
												</font>
											</s:if>
											<s:else>
												<s:if test="examPaper.quizcount-myexamcount>0||(examPaper.quizcount-myexamcount<=0&&(minstatus==0||minstatus==1))">
												    <s:if test="myroom.examroom.isMacBand==0&&myroom.examroom.isIpLimit==0">
														<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a name="startExam" href="javascript:enterEroom('<s:property value="myroom.examroom.id"/>','<s:property value="examPaper.id"/>');"
														class="textbg5">开始作答</a></td>
                                              </tr>
                          </table>
														
														
												    </s:if>
													<s:else>
														<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a name="startExam" target="_blank"
															href="qpracInit.action?myroom.examroom.id=<s:property value="myroom.examroom.id"/>&examPaper.id=<s:property value="examPaper.id"/>"
															class="textbg5">开始作答</a></td>
                                              </tr>
                          </table>
														
														
													</s:else>
												</s:if>
												<s:else><font color="red">已达到考试次数</font></s:else>
											</s:else>									</td>
						
   <%--  <td width="140">
	<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href='myquizpaperlist.action?examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="myroom.examroom.id"/>&Return=<s:property value="Return"/>'><span style="font-size:14px;font-weight:bold;color:white;">历次记录</span></a></td>
                                              </tr>
                          </table>	</td>--%>
  </tr>
</table>

										 
										 
								  <%-- </s:else>	--%>									</td>
								</tr>
							</s:if>
						</s:iterator>
					</tbody>
			  </table>
				<!-- <a  href="study_room_record_list.action?myroom.examroom.id=<s:property value="myroom.examroom.id"/>&iscommon=${myroom.examroom.iscommon }" class="textbg5">返回</a> -->
	    </s:else>
			
	</td>
  </tr>
</table>
	
      
  </td>
  </tr>
</table>
	
	
			

		
		
		
            
	</BODY>
</HTML>
