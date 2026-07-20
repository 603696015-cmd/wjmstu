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
		<TITLE>培训管理信息系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" /> 
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function disNopassInfo(classid){
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}
			function updateXX(elclassid,courseid){
				alert(elclassid);
				alert(courseid);
				if(window.confirm("是否选修该课程")){
					updatexx.href="myelclass_view.action?elclass.id="+elclassid+"&course.id="+courseid+"&str=updatexx";
				}
			}
			/*
			function dateTimeCheck(startTime,endTime,now,val){
				//course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1
				
				//alert(startTime);
				//alert(now);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				var valid=val;
				//alert(start);
				//alert(end);
				//alert(noww);
				//alert(valid);
				
				if(valid !=6 || valid !=8 ){
					alert("考场正在修改中，请等待!!!");
					return false;
				}
				
				if(noww<start){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}else if(noww>end){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}
				return true;
			}
			*/
			
			function dateTimeCheck(startTime,endTime,now){
				//course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1
				
				//alert(startTime);
				//alert(now);
				//alert(endTime);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				//end=toDate("2012-05-10-00-00-00");
				//noww=toDate("2012-05-11-00-00-00");
				//var valid=val;
				//alert(start);
				//alert(end);
				//alert(noww);
				//alert(valid);
				/*
				if(valid !=6 || valid !=8 ){
					alert("考场正在修改中，请等待!!!");
					return false;
				}
				*/
				if(noww<start){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}else if(noww>end){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}
				return true;
			}
			
			function toDate(str){
  				 var sd=str.split("-");
   				 return new Date(sd[0],sd[1],sd[2],sd[3],sd[4],sd[5]);
}
			function deletecheck(classid,courseid)
			{
				if(window.confirm("确认删除？")){
					 window.location.href="deleteCourse.action?elclass.id="+classid+"&course.id="+courseid; 
				}
			}
		</script>
	    <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<body>
		<ul class="nav">
			
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班详情" /></div>
			</li>
		</ul>
		<table width="100%" align="center" cellpadding="2" cellspacing="1"
			bgcolor="#ECEDEB">
			<tr>
				<th width="120" align="center" bgcolor="#FFFFFF">
					培训班名称				</th> <!-- <s:property value="myClass.elClass.id"/> -->
				<td width="35%" align="center" bgcolor="#FFFFFF">
					<s:property value="myClass.elClass.name" />				</td>
			    <th width="120" align="center" bgcolor="#FFFFFF">证书名称</th>
			    <td width="35%" align="center" bgcolor="#FFFFFF"><s:property value="myClass.elClass.certificatename" /></td>
			</tr>
			<tr>
				<th width="120" align="center" bgcolor="#FFFFFF">创建人 </th>
				<td width="35%" align="center" bgcolor="#FFFFFF"><s:property value="myClass.elClass.creater.realname" />					</td>
			    <th width="120" align="center" bgcolor="#FFFFFF">所属类别 </th>
			    <td width="35%" align="center" bgcolor="#FFFFFF"><s:property value="myClass.elClass.cltype.name" />	</td>
			</tr>
			<tr>
			  <th width="120" align="center" bgcolor="#FFFFFF">结业条件 </th>
			  <td align="center" bgcolor="#FFFFFF">
			  		<s:if test="myClass.elClass.classtype==0">
				  		必修课全部通过，选修课最少获得　
						<span style="color:red;"><b>
						<s:property value="myClass.elClass.optionalcredit" /> 
						</b></span>　学分
					</s:if><s:elseif test="myClass.elClass.classtype==2"> 
				  		必修课最少获得:<s:property value="myClass.elClass.credit_bx" /> <br/>
				  		选修课最少获得:<s:property value="myClass.elClass.credit_xx" /> 
					</s:elseif>
					<s:else>
						必修课全部通过
					</s:else>
			  </td>
			  <th width="120" align="center" bgcolor="#FFFFFF">证书查看</th> 
			  <td width="35%" align="center" bgcolor="#FFFFFF">
			  		<s:if test="myClass.passed">
		  				<a target="_blank" href="mydiploma_view.action?elclass.id=<s:property value="myClass.elClass.id"/>" class="textbg">查 看</a>
		  				<br>
		  			</s:if>
			  		<s:else>
			  			还没能获得证书
			  			<a href="javascript:disNopassInfo('<s:property value="myClass.elClass.id"/>');"
							class="textbg4">详 情</a>
			  		</s:else>
			  		
			  </td>
		  </tr>
			<tr>
				<th width="120" align="center" bgcolor="#FFFFFF">
					简介				</th>
				<td width="35%" colspan="3" align="center" bgcolor="#FFFFFF">
					<s:property value="myClass.elClass.description" />				
				</td>
		    </tr>
			<tr>
				<th width="120" align="center" bgcolor="#FFFFFF">
					总计(<span style="color:red">包含必修,选修</span>)
				</th>
				<td  width="35%" colspan="3" align="center" bgcolor="#FFFFFF">
					<label>课程考试平均分:<span style="color:red"><s:property value="myClass.elClass.Kc_scoresAVG" /></span></label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label>学时总数:<span style="color:red"><s:property value="myClass.elClass.Xs_Count" /></span></label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label>学分总数:<span style="color:red"><s:property value="myClass.elClass.Xf_Count" /></span></label>
				</td>  
		    </tr>
	    	<tr>
		    	<th width="120" align="center" bgcolor="#FFFFFF">
					考场
				</th>
				<s:if test="elclass.examRooms!=null||elclass.examRooms.size!=0">
					<td width="35%" colspan="3" align="center" bgcolor="#FFFFFF">
						<s:iterator value="elclass.examRooms">
							 	<a href="quizpaperinit.action?classid=<s:property value="myClass.elClass.id"/>&myroom.examroom.id=<s:property value="id" />&Return=list"><s:property value="title" /></a>
						</s:iterator>
					</td>
				</s:if>
				<s:else>
					<td></td>
				</s:else>
		    </tr>
		</table>
		<br>
		<table align="center" width="100%" cellpadding="1" cellspacing="1">
			<caption>
				必修课 
				<s:if test="myClass.elClass.classtype ==2"> 
				<a href="course_libraryList.action?pN=0&pS=10&course.courseCss=0&course.ctype.id=1" class="textbg" target="_blank">添加必修课程</a> 
				</s:if>
			</caption>
			<tr>
				<th width="130">
					课程名称				</th>
				<th width="70">
					格式				
					<a href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>" >(全部)</a>
				</th>
				<!--<th width="60">
					讲师				</th>-->
				<th width="80">
					开始时间				</th>
				<th width="80">
					结束时间				</th>
				<th width="80">

					时长/已完成				    <!--/学分-->				</th>
				<th width="60">进度				</th>
				<th width="60">
					已学习				</th> 
				<th width="40">
					成绩				</th>
				<th width="70">
					结业条件				</th>
				<th width="70"> 学分/已获</th>
				<th width="50">
					学习				</th>
				<th width="50">
					笔记				</th>
			  <th width="50">
					模考				</th> 
				<th width="50">
					结业				</th>
					<s:if test="myClass.elClass.classtype==2"> 
					<th width="50">
					操作				</th>
					</s:if>
			</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
			<s:set name="btotalscore" value="0f"></s:set>
			<s:set name="btotalcredit" value="0.0f"></s:set>
			<s:iterator value="myClass.myCourseB">
				<tr>
					<td width="130" height="30" align="center" bgcolor="#FFFFFF">
						<s:property value="course.name" />	
						<s:if test="examRoom.type == 1"><span style="color:red">[选拨式]</span>	</s:if>				  </td>
					<td width="70">  
					<a href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>&courseBX.courseForm=<s:property value="course.courseForm"/>" >
						<s:property value="course.courseFormName" />
					</a>	
					</td>
					<!--<td width="60" height="60"> 
					<s:property value="course.creater.realname" /> 
						<s:property value="course.teacherName" />				  </td>--> 
						<td width="80">
							<s:date name="course.roomstart" format="yyyy-MM-dd HH:mm:ss" />				  </td>
						<td width="80">
							<s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" />				  </td> 
						<td width="80" height="30" align="center" bgcolor="#FFFFFF">
							<s:property value="course.during" />
							分钟 /
							<s:property value="passtime" /> 
							分钟<!--/
							<s:property value="course.credit" />-->				  </td>
						<td width="60" height="30" align="center" bgcolor="#FFFFFF">
							<s:property value="processStr" />%<!--[<s:if test="passed" >学完</s:if><s:else>未完成</s:else>]-->				  </td>
						<td width="60">
							<s:property value="passtime2" />分钟					</td> 
					<td width="40">
						<s:property value="myExamPaper.myScore" />				  </td>
					<td width="70">
						<s:if test="course.getcredit == 1">
								学完						</s:if>
						<s:elseif test="course.getcredit == 2">
								考过						</s:elseif>
						<s:elseif test="course.getcredit == 3">
								学完且考过						</s:elseif>
						<s:else>
								学完						</s:else>				  </td>
					<td width="70" align="center">
						<s:property value="course.setcredit" />	/			  
						<s:property value="myCredit" />				  </td>
					<s:set name="btotalscore" value="#btotalscore+myCredit"></s:set>
					<%-- <s:set name="btotalcredit" value="#btotalcredit+course.setcredit"></s:set> --%>
					<s:if test="isDel!=-1">
					<td width="50" height="30" align="center" bgcolor="#FFFFFF"> 
						<s:if test="course.getcredit == 1 || course.getcredit == 3"> 		 
							<a target="_blank"
								href="course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&classid=<s:property value="myClass.elClass.id"/>"  onclick="return dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');" class="textbg4">学 习</a>						
						</s:if>		
						<s:elseif test="myClass.elClass.classtype==2 || course.getcredit == 2"> 
							<a target="_blank"
								href="course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&classid=<s:property value="myClass.elClass.id"/>"  onclick="return dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');" class="textbg4">学 习</a>						
						</s:elseif>			 
					</td>
					<td width="50" height="30" align="center" bgcolor="#FFFFFF">  
						<s:if test="course.getcredit == 1 || course.getcredit == 3"> 	
							<a href="course_study_notelist.action?course.id=<s:property value="course.id"/>" class="textbg4">查 看</a>
						</s:if> 
						<s:elseif test="myClass.elClass.classtype==2 || course.getcredit == 2"> <!--classtype==2 ,自主培训的考过可以看见该操作。  -->
							<a href="course_study_notelist.action?course.id=<s:property value="course.id"/>" class="textbg4">查 看</a>	
						</s:elseif>	
					</td>
					 <td width="50" height="30" align="center" bgcolor="#FFFFFF">
							<a target="_blank" href='practice_listInit.action?course.id=<s:property value="course.id"/>' class="textbg4">模考</a>			
						<!--<s:if test="examRoom.id != 0 && examRoom.isnormal == 1">
						</s:if><s:else>
							无练习
						</s:else> 	-->	
					</td>  
					<td width="50" height="30" align="center" bgcolor="#FFFFFF">
					<%-- 
						<s:set name="btotalscore" value="#btotalscore+myCredit"></s:set>
					 --%>
						<s:set name="btotalcredit" value="#btotalcredit+course.setcredit"></s:set> 
						<s:if test="examRoom.type == 1">
							<s:if test="examRoom.id != 0 && examRoom.svalid == 5" >   
								<s:if test="course.getcredit == 2 || course.getcredit == 3"> 	
									<a href='quizpaperinit.action?course.id=<s:property value="course.id" />&course.getcredit=<s:property value="course.getcredit" />&course.firstLearn=<s:property value="course.firstLearn" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elclass.id"/>' onClick="return isEroom2('<s:property value="examRoom.valid"/>','<s:property value="examRoom.svalid"/>','<s:property value="examRoom.isnormal"/>','<s:property value="examRoom.type"/>');" class="textbg4">考 试1</a>	
								</s:if>
							</s:if>
							<s:else>
								无考场1
							</s:else>
						</s:if>
						<s:else> 
							<s:if test="examRoom.id != 0" >   <!--  && examRoom.isnormal == 1 -->
								<s:if test="course.getcredit == 2 || course.getcredit == 3"> 	
									<a href='quizpaperinit.action?course.id=<s:property value="course.id" />&course.getcredit=<s:property value="course.getcredit" />&course.firstLearn=<s:property value="course.firstLearn" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elclass.id"/>' onClick="return isEroom2('<s:property value="examRoom.valid"/>','<s:property value="examRoom.svalid"/>','<s:property value="examRoom.isnormal"/>','<s:property value="examRoom.type"/>');" class="textbg4">考 试2</a>
								</s:if>
							</s:if>
							<s:else>
								无考场2
							</s:else>
						</s:else>					
						</td>
					</s:if>
					<s:else>
						<td colspan="4">该课程在培训班中已删除</td>
					</s:else>
					
					<s:if test="myClass.elClass.classtype==2"> 
					<td><!-- <a href="deleteCourse.action?elclass.id=<s:property value="myClass.elClass.id"/>&course.id=<s:property value="course.id"/>" onClick="deletecheck();" class="textbg4">删除</a>
					 -->
					 
					<a  href="javascript:void(0)" onclick="javascirpt:deletecheck(<s:property value="myClass.elClass.id"/>,<s:property value="course.id"/>);" class="textbg4">删除</a>
					</td>
					</s:if>
			</s:iterator>
			<tr>
				<td width="200" height="30" align="center" bgcolor="#FFFFFF">
					合计				</td>
				<td height="30" colspan="14" align="center" bgcolor="#FFFFFF">
					总学分　
					  <s:property value="#btotalcredit" />
					<span class="STYLE1">　**　</span>我的学分　
			        <s:property value="#btotalscore" />			  				</td>
			</tr></tbody>
	</table>
		<Br>
		<s:if test="myClass.elClass.classtype!=1">
		<table align="center" width="100%" cellpadding="1" cellspacing="1">
			<caption>
				选修课 
				<s:if test="myClass.elClass.classtype ==2">
				<a href="course_libraryList.action?pN=0&pS=10&course.courseCss=1&course.ctype.id=1" class="textbg" target="_blank">添加选修课程</a> 
				</s:if>
			</caption>
			<tr>
				<th width="130">
					课程名称				</th>
				<th width="70">
					格式				 
					<a href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>" >(全部)</a>
					</th>
				<!--<th width="60">
					讲师				</th>-->
				<th width="80">
					开始时间				</th>
				<th width="80">
					结束时间				</th>
				<th width="100">

					时长/已完成				    <!--/学分-->				</th>
				<th width="60">
					进度				</th>
				<th width="60">
					已学习				</th>
				<th width="40">
					成绩				</th>
				<th width="70">
					结业条件				</th>
				<th width="70">学分/已获</th>
				<th width="50">
					学习				</th>
				<th width="50">
					笔记				</th>
				 <th width="50">
					模考				</th> 
				<th width="50">
					结业				</th>
					<s:if test="myClass.elClass.classtype==2"> 
					<th width="50">
					操作				</th>
					</s:if>
					
					<!--<th width="50">&nbsp;
									</th>-->
			</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
			<s:set name="xtotalscore" value="0"></s:set><%--用来保存我的选修课总学分 --%>
			<s:set name="xtotalcredit" value="0"></s:set>
			<s:iterator value="myClass.myCourseX">
				<tr>
					<td width="150" height="30" align="center" bgcolor="#FFFFFF">
						<s:property value="course.name" />			
						<s:if test="examRoom.type == 1"><span style="color:red">[选拨式]</span>	</s:if>				  </td>
					<td width="50"> 
					<a href="myelclass_view.action?elclass.id=<s:property value="myClass.elClass.id"/>&courseBX.courseForm=<s:property value="course.courseForm"/>" >
						<s:property value="course.courseFormName" />
					</a>		
					</td>
					<!--<td width="60">
						 <s:property value="course.creater.realname" />	
						<s:property value="course.teacherName" />				  </td>-->	
					<td width="80">
				  <s:date name="course.roomstart" format="yyyy-MM-dd HH:mm:ss" />					</td>
					<td width="80">
				  <s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" /></td>  
						<td width="100" height="30" align="center" bgcolor="#FFFFFF">
							<s:property value="course.during" />
							分钟 /
							<s:property value="passtime" />
					  		分钟 <!--/
							
							<s:property value="course.credit" />	-->				</td>
						<td width="60" height="30" align="center" bgcolor="#FFFFFF">
					 		<s:property value="processStr" />%<!--[<s:if test="passed" >学完</s:if><s:else>未完成</s:else>]-->				</td>
						<td width="60">
							<s:property value="passtime2" />分钟					
						</td> 
					<td width="40">
				  <s:property value="myExamPaper.myScore" /></td>
					<td width="70">
						<s:if test="course.getcredit == 1">
								学完						</s:if>
						<s:elseif test="course.getcredit == 2">
								考过						</s:elseif>
						<s:elseif test="course.getcredit == 3">
								学完且考过						</s:elseif>
						<s:else>
				  学完						</s:else>					</td>
					<td width="70" align="center">
				  <s:property value="course.setcredit" /> /
				  <s:property value="myCredit" /></td>
					
					<s:set name="xtotalscore" value="#xtotalscore+myCredit"></s:set>
					<%-- <s:set name="xtotalcredit" value="#xtotalcredit+course.setcredit"></s:set> --%>
					<s:if test="isDel!=-1">
					<td width="50" height="30" align="center" bgcolor="#FFFFFF"> 
						<s:if test="course.getcredit == 1 || course.getcredit == 3"> 
							<a target="_blank"
								href="course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&classid=
							<s:property value="myClass.elClass.id"/>" onclick="return dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');" class="textbg4">学 习</a>
						</s:if>
						<s:elseif test="myClass.elClass.classtype==2 || course.getcredit == 2">  <!--classtype==2 ,自主培训的考过可以看见该操作。  -->
							<a target="_blank"
								href="course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&classid=
							<s:property value="myClass.elClass.id"/>" onclick="return dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');" class="textbg4">学 习</a>
						</s:elseif>	
					</td>
					<td width="50" height="30" align="center" bgcolor="#FFFFFF">
						<s:if test="course.getcredit == 1 || course.getcredit == 3"> 
							<a href="course_study_notelist.action?course.id=<s:property value="course.id"/>" class="textbg4">查 看</a>	
						</s:if>	 
						<s:elseif test="myClass.elClass.classtype==2 || course.getcredit == 2"> <!--classtype==2 ,自主培训的考过可以看见该操作。  -->
							<a href="course_study_notelist.action?course.id=<s:property value="course.id"/>" class="textbg4">查 看</a>	
						</s:elseif>	
					</td>
					  <td height="30" align="center" bgcolor="#FFFFFF">
						<a target="_blank" href='practice_listInit.action?course.id=<s:property value="course.id"/>' class="textbg4">模考</a>					
					<!-- <s:if test="examRoom.id != 0 && examRoom.isnormal == 1">
						</s:if><s:else>
							无练习
						</s:else> -->
						</td>   
					<td width="50" height="30" align="center" bgcolor="#FFFFFF"> 
						<s:set name="xtotalcredit" value="#xtotalcredit+course.setcredit"></s:set>
						<!-- <a href='javascript: void(window.open("quizpaper.action?myExamPaper.id=<s:property value="myExamPaper.id"/>","course_exam_5","toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no"))' class="textbg4">考试</a>	 -->
						<s:if test="examRoom.type == 1">
							<s:if test="examRoom.id != 0 && examRoom.svalid == 5" >   
								<s:if test="course.getcredit == 2 || course.getcredit == 3"> 
									<a href='quizpaperinit.action?course.id=<s:property value="course.id" />&course.getcredit=<s:property value="course.getcredit" />&course.firstLearn=<s:property value="course.firstLearn" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elclass.id"/>' onClick="return isEroom2('<s:property value="examRoom.valid"/>','<s:property value="examRoom.svalid"/>','<s:property value="examRoom.isnormal"/>','<s:property value="examRoom.type"/>');" class="textbg4">考 试</a>								</s:if>
							</s:if><s:else>
								无考场2
							</s:else>
						</s:if><s:else>
							<s:if test="examRoom.id != 0 " >   <!-- && examRoom.isnormal == 1 -->
							  <s:if test="course.getcredit == 2 || course.getcredit == 3"> 
								<a href='quizpaperinit.action?course.id=<s:property value="course.id" />&course.getcredit=<s:property value="course.getcredit" />&course.firstLearn=<s:property value="course.firstLearn" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elclass.id"/>' onClick="return isEroom2('<s:property value="examRoom.valid"/>','<s:property value="examRoom.svalid"/>','<s:property value="examRoom.isnormal"/>','<s:property value="examRoom.type"/>');" class="textbg4">考 试</a>								</s:if>			
							</s:if><s:else>
								无考场1
							</s:else>
						</s:else>					
					</td>
					</s:if>
					<s:else>
						<td colspan="4">该课程在培训班中已删除</td>
					</s:else>
					<s:if test="myClass.elClass.classtype==2"> 
					<td><!--  <a href="deleteCourse.action?elclass.id=<s:property value="myClass.elClass.id"/>&course.id=<s:property value="course.id"/>"  class="textbg4">删除</a>
					-->
					<a  href="javascript:void(0)" onclick="javascirpt:deletecheck(<s:property value="myClass.elClass.id"/>,<s:property value="course.id"/>);" class="textbg4">删除</a>
					</td>
					</s:if>
			</s:iterator>
			<tr>
				<td height="30" align="center" bgcolor="#FFFFFF">
					合计				</td>
				<td height="30" colspan="14" align="center" bgcolor="#FFFFFF">
					总学分　<s:property value="#xtotalcredit" /><span class="STYLE1">　**　</span>我的学分　
			  <s:property value="#xtotalscore" />								</td>
			</tr></tbody>
	</table>
	</s:if>
	</body>
</HTML>
