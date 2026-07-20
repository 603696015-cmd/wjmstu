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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system003.css" />
		<link rel="stylesheet" type="text/css" href="css/manage003.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function dateTimeCheck(startTime,endTime,now,courseid){
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
				
				var courserstudyurl="course_study.action?course.id="+courseid+"&coursePage.id=-1&course.classid=0";
				window.showModalDialog(courserstudyurl,'',widthheight);
				return true;
			}
			function studycourse(aaaaa,bbb){
				var widthheight = "dialogHeight:"+screen.height+"px;dialogWidth:"+screen.width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
				var  towurl="course_study.action?course.id="+aaaaa+"&coursePage.id=-1&classid="+bbb
			
			window.showModalDialog(towurl,'',widthheight);
			}
			function toDate(str){
  				 var sd=str.split("-");
   				 return new Date(sd[0],sd[1],sd[2],sd[3],sd[4],sd[5]);
}
			
		</script>
        <style type="text/css">
		td{font-size:13px;}
	table{background-color:white;}
	</style>
	</HEAD>
	<body>

		<ul class="nav">
			<li>
				<div style="color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的课程培训</span>
				
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="mycourseAlllist.action">我的全部课程</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="text-align:center;">
			<div style="width: 100%;text-align: center;border:0px solid #C1EBFF;">
            <s:if test="myCourses.size==0">
            <table width="100%" border="0" cellspacing="0" style=" margin-top:200px;">
  <tr>
    <td width="48%" align="right"><img src="images/wrong.gif" width="128" height="128"></td>
    <td width="52%"><span style="color:red;"><strong>您当前没有需要学习的课程!</strong></span></td>
  </tr>
</table></s:if>
<s:else></div>
			<s:iterator value="myCourses">
			  <table width="100%" border="0" cellspacing="10" cellpadding="0" style="border-bottom:2px solid #DFF8FF;">
				  <tr>
				    <td style="width:300px; text-align:center;">
				   <s:if test="course.mainimg==null">
				   	 <img src="elfrontimages/coursedimg.jpg" width="250" height="170" alt="">
				    </s:if>
				    <s:else>
				     <img src="<s:property value='course.mainimg_' />" width="250" height="170" alt="">
				    </s:else>
				    </td>
				    <td><table width="100%" border="0" cellspacing="5" cellpadding="0">
				      <tr>
				        <td colspan="5"><table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px dashed #000; height:40px;">
				          <tr>
				            <td width="128" align="right" valign="middle" style="color:#00F;">课程名称：</td>
				            <th width="604"  align="left" style="color:#ff6600; font-weight:bold;"><s:property value="course.name" />  </th>
			              </tr>
			            </table></td>
			          </tr>
				      <tr>
				        <td width="128" align="right" valign="middle" style="color:#00F; height:30px;">讲 师：</td>
				        <td width="116"><s:property value="course.teacherName" /></td>
				        <td width="93" align="right" valign="middle" style="color:#00F;">学习进度：</td>
				        <td colspan="2"><div style="border: 1px dotted #FF6633; width:200px;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div></td>
			          </tr>
				      <tr>
				        <td align="right" valign="middle" style="color:#00F;height:30px;">开始：</td>
				        <td>	<s:if test="course.roomstart == course.roomend">
									不限  
								</s:if>
								<s:else>
									<s:date name="course.roomstart" format="yyyy-MM-dd HH:mm:ss" />
												----
									
								</s:else></td>
				        <td align="right" valign="middle" style="color:#00F;height:30px;">总时间：</td>
				        <td colspan="2"><s:property value="course.during" />
						分钟</td>
			          </tr>
				      <tr>
				        <td align="right" valign="middle" style="color:#00F;height:30px;">结束时间 ：</td>
				        <td>
                        <s:if test="course.roomstart == course.roomend">
									不限  
						  </s:if>
                        <s:else>
                        <s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" />
                        </s:else>
                        </td>
				        <td align="right" valign="middle" style="color:#00F;">已学时间：</td>
				        <td colspan="2"><s:property value="passtime" />
								分钟（<s:property value="processStr" />%）</td>
			          </tr>
				      <tr>
				        <td align="right" valign="middle" style="color:#00F;height:30px;"><span style="color:#00F;">结业方式 ：</span></td>
				        <td> </td>
				        <td align="right" valign="middle" style="color:#00F;">&nbsp;</td>
				        <td width="206"></td>
				        <td width="169"><s:if test="course.roomstart == course.roomend">
				          <a target="_blank"  onclick="studycourse('<s:property value="course.id"/>','<s:property value="classId"/>')";
				          class="textbg">进入学习</a>
				          </s:if><s:else>	
				            <!-- "course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&course.classid=0"		 -->		 	
				            <a 
									 onclick=" dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>','<s:property value="course.id"/>');" class="textbg5">进入学习</a>
			            </s:else></td>
			          </tr>
				      <tr>
				        <td colspan="3" align="right" valign="middle" style="color:#00F;height:30px;">&nbsp;</td>
				        <td colspan="2"></td>
			          </tr>
			        </table></td>
			      </tr>

          </table>
          </s:iterator>
				<form action="mycourselist.action" name="erform" method="post">
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
				<wysLib:page_cisco></wysLib:page_cisco>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
