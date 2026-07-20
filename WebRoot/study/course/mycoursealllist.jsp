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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="stylesheet" type="text/css" href="111.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function dateTimeCheck(startTime,endTime,now,courseid,classid){
				//course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1
				
				//alert(startTime);
				//alert(now);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				//alert(start);
				//alert(end);
				//alert(noww);
				if(noww<start){
					alert("不在有效学习时间段范围内，请与管理员联系"); 
					return false;
				}else if(noww>end){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}
				
				var widthheight = "dialogHeight:"+screen.height+"px;dialogWidth:"+screen.width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
				
				var courserstudyurl="course_study.action?course.id="+courseid+"&coursePage.id=-1&classid="+classid;
				window.showModalDialog(courserstudyurl,'',widthheight);
				return true;
			}
			function studycourse(aaaaa){
			
				var widthheight = "dialogHeight:"+screen.height+"px;dialogWidth:"+screen.width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
				var  towurl="course_study.action?course.id="+aaaaa+"&coursePage.id=-1&course.classid=0"
			
			window.showModalDialog(towurl,'',widthheight);
			}
			function toDate(str){
  				 var sd=str.split("-");
   				 return new Date(sd[0],sd[1],sd[2],sd[3],sd[4],sd[5]);
}
				
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的全部课程</span>
				
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="mycourselist.action">我的课程培训</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center; ">		
            <s:if test="myCourses.size==0">
            <div style="width: 100%;text-align: center;border:0px solid #C1EBFF; margin-top:200px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="48%" align="right"><img src="images/wrong.gif" /></td>
    <td><span style="color:red;"><strong>您当前没有需要学习的课程!</strong></span></td>
  </tr>
</table>
</div>
            </s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="0" cellspacing="1"
					bgcolor="#D1E4F5" style="magin-top:2px;">
					<caption>
						我的课程
					</caption>
					<tr>
						<th width="200" height="30" align="center" bgcolor="#F8FCFE" >
							课程名称						</th>
			<!-- 		<th width="150" height="30" align="center" >
							创建者						</th>
						<th width="100" height="30" align="center" >
							主讲教师						</th> -->
				  <th width="150"  height="30" align="center" bgcolor="#F8FCFE" >
										开始--结束时间							</th>
						    <!--<th height="30" align="center" >
							学习类型
						</th>-->
					  <th width="150" height="30" align="center" bgcolor="#F8FCFE" >
							总时间/已学时间						</th>
						<th width="100" height="30" align="center" bgcolor="#F8FCFE" >
						  学习进度						</th>
						<th width="120" height="30" align="center" bgcolor="#F8FCFE" >
							学习中心						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myCourses">
						<tr>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;  margin-top:10px;">
								<s:property value="course.name" />
						  </td>
				<!-- 	  <td width="100" height="30" align="center" >
								<s:property value="course.creater.realname" />
						  </td>
							<td width="100" height="30" align="center" >
								<s:property value="course.teacherName" />
						  </td> -->
								  <td width="150" align="center" bgcolor="#F8FCFE" >
										<s:if test="course.islink==4"><s:date name="course.roomstart" format="yyyy-MM-dd" />
										-<s:date name="course.roomend" format="yyyy-MM-dd" />
										</s:if>
										<s:else> 
										<s:date name="course.roomstart" format="yyyy-MM-dd" />
										-<s:date name="course.roomend" format="yyyy-MM-dd" />
										</s:else>
						  </td>
							<!--<td height="30" align="center" >
								<a
									href="mycourselistbystatus.action?myCourse.status=<s:property value="status" />"><s:property
										value="statusName" />
								</a>
							</td>-->
						  <td width="150" height="30" align="center" bgcolor="#F8FCFE" >
								<s:property value="course.during" />
								分钟 /
								<s:property value="passtime" />
								分钟（<s:property value="processStr" />%）
						  </td>
							<td width="100" height="30" align="left" bgcolor="#F8FCFE" style="padding:0px;" >
							  <div style="border: 1px dotted #FF6633;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div>
						  </td>
							<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<a 
									 onclick=" dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>','<s:property value="course.id"/>','<s:property value="course.classid"/>');" class="textbg5">进入学习</a>
							<!-- 	<a target="_blank"
									href=window.showModalDialog("course_study.action?course.id='<s:property value="course.id"/>'&coursePage.id=-1") class="textbg">进入学习</a>							</td>
						 -->
					  </tr>
                      </s:iterator>
						<tr>
						  <td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;margin-top:10px;"> 合计</td>
						  <td align="left" bgcolor="#F8FCFE" >总学时 </td>
						  <td height="30" align="left" bgcolor="#F8FCFE" ><s:property  value="li[0]"  />分钟</td>
						  <td height="30" align="left" bgcolor="#F8FCFE" style="padding:0px;" >已完成学时</td>
						  <td height="30" align="center" bgcolor="#F8FCFE" >  <s:property  value="li[1]"  />分钟</td>                   
					  </tr>
					
					</tbody>
			  </table> 
			  
		
		  		<form action="mycourseAlllist.action" method="post" name="ddd">
		      <s:hidden name="pN" id="pageNow"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:hidden name="course.name"></s:hidden>
				</form>
					<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
					</script>
				<wysLib:page_cisco></wysLib:page_cisco>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
