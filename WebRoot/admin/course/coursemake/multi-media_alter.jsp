<%@ page language="java" pageEncoding="UTF-8"   %>
<%@page import="com.sopia.courseman.entities.Course"%>
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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#cname").val()==''){
					alert("请填写课程名称");
					$("#cname").focus();
					return false;
				}
				var qlibId=$("input[name='course.ctype.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择课程类别！");
					return false;
				}
				return true;
			}
			function searchUserInit(comp){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("mess_sendUserlistInit.action?x="+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择用户！');
				 }else{
				 	if(rv[0]<=0)  	alert('您没有选择用户！');
				 	else{
				 	//	alert(rv[0]);
				 	$.post("mess_getUserInfoJson.action", {
						"elUser.id":rv[0],
						"x":Math.random
						}, 
						function (data) {
							var dataObj=eval("("+data+")");
							document.getElementById("t_id").value=dataObj.elUser.id;
							document.getElementById("t_name").value=dataObj.elUser.realname;
						}); 
				 }
				 }
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程修改页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">修改课程信息</span>
			</li>
			<li class="sep">
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div>
			<s:form action="course_alter" method="post" name="course_info"
				theme="simple" id="course_info" onsubmit="return _onsubmit()">
				<!--<div>
					<B>注意：</B> 1、课程开通后将不能修改学习信息（时间，章节等信息）；
					<br />
					2、课件开通后将提交给管理员审核。通过后方可使用。
				</div>
				-->
				<div style="font-size: 15px; font-weight: bolder">
					<!--<span style="color: blue"><s:if test="course.status!=0">提示：不能再编辑学习信息(时间等)</s:if>
					</span>-->
				</div>
				<table cellpadding="1" cellspacing="1" >
					<tr>
						<td width="160" height="30" align="center" >
							课程名称：
						</td>
						<td >
							<label>
								<s:textfield id="cname" name="course.name" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							课程介绍：
						</td>
						<td >
							<label>
								<s:textarea name="course.description" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							讲师姓名：
						</td>
						<td >
							<label>
								<s:textfield name="course.teacherName" id="t_name" size="40" />
								<s:hidden name="course.teacherId" id="t_id"/><input class="textbg6" type="button" onClick="searchUserInit('messUser')" value="查找">
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							师资介绍：
						</td>
						<td >
							<label>
								<s:textarea name="course.teacherinfo" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					<!-- <tr>
						<td width="160" height="30" align="center" >
							学习计划：
						</td>
						<td >
							<label>
								<s:textarea name="course.studyplan" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td width="160" height="30" align="center" >
							课程图片：
						</td>
						<td >
							<label>
								<s:textfield name="course.mainimg" size="60" id="pic" />
								<a href="javascript:setUrl('pic');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							所属类别：<%
										String xx = ((Course) request
													.getAttribute("course")).getCtype().getId()
													+ "";
									%>
							
						</td>
						<td >
							<label>
								<wysLib:ctypeTree did="0" iname="course.ctype.id" itype="ra" ivalue="<%=xx %>"></wysLib:ctypeTree>
							</label>
						</td>
					</tr>

					<tr>
						<td width="160" height="30" align="center" >
							推荐学分：
						</td>
						<td >
							<label>
								<input type="text" value="<s:property value="course.credit"/>"
									name="course.credit" />
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="160" height="30" align="center" >
							通过成绩：
						</td>
						<td >
							<label>
								<s:textfield cssStyle="width:40px;" name="course.passgrade" />
								%
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td align="center" >
							课程类型：
						</td>
						<td >
							<label>
								<s:property value="course.islinkName" />
								<s:if test="course.islink==1">
									<span id="url_desc">外部课程链接地址</span>
									<input name="course.exurl" type="text" id="exurl"
										value="<s:property value="course.exurl"/>" size="50">
								</s:if>
								<s:if test="course.islink==3">
									<span id="url_desc">视频地址</span>
									<input name="course.exurl" type="text" id="exurl"
										value="<s:property value="course.exurl"/>" size="50">
								</s:if>
								<br />
								<s:if test="course.islink==1">
									学习时间：
									<input name="course.during" type="text"
										value="<s:property value="course.during"/>" size="5">
									分钟 学习询问时间：
									<input name="course.querytime" type="text"
										value="<s:property value="course.querytime"/>" size="5">
									分钟
								</s:if>
								<s:elseif test="course.islink==4">
									开始时间：
									<input name="course.room.id"  type="hidden" value="<s:property value="course.room.id"/>">
									<input name="course.roomstart" value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomstart"/>" id="r_start" type="text"
										size="20" onclick="setday(this)">
									结束时间：
									<input name="course.roomend" value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomend"/>" id="r_end" type="text"
										size="20" onclick="setday(this)"><br/>
									学习时间：
									<input name="course.during" type="text"
										value="<s:property value="course.during"/>" size="5">
									分钟 学习询问时间：
									<input name="course.querytime" type="text"
										value="<s:property value="course.querytime"/>" size="5">
									分钟
								</s:elseif>
								<s:else>
									学习时间：
									<s:property value="course.during" /> 
									分钟
								</s:else>
							</label>
						</td>
					</tr>
					<!-- tr>
						<td align="center" bgcolor="#FFFFFF" style="color: black">
							获得学分方式：
						</td>
						<td >
							<s:if test="course.status==0">
								<label>
									学习完获得
								</label>
								<input type="radio"
									<s:if test="course.creditmod==0">checked="checked"</s:if>
									name="course.creditmod" value="0"> &nbsp;&nbsp;
								<label>
									进度X学分
								</label>
								<input type="radio"
									<s:if test="course.creditmod==1">checked="checked"</s:if>
									name="course.creditmod" value="1">
							</s:if>
							<s:else>
								<label>
									<s:if test="course.creditmod==0">学习完获得</s:if>
									<s:if test="course.creditmod==1">进度X学分</s:if>
								</label>
								<input type="hidden" name="course.creditmod"
									value="<s:property value="course.creditmod"/>" />
							</s:else>
						</td>
					</tr-->
					<tr>
						<td align="center" >
							标准笔记字数：
						</td>
						<td >
							<input type="text" size="4"
								value="<s:property value="course.notenumber"/>"
								name="course.notenumber">
							笔记提交时间：
							<input type="text" size="16"
								value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.notedate"/>"
								name="course.notedate" onclick="setday(this)">
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							课程状态：
						</td>
						<td >
							<label>
								<s:property value="course.validName" />
							</label>
							<s:if test="course.status==0||course.status==5">
								<a
									href="course_openInit.action?course.id=<s:property value="course.id"/>"
									style="color: red;" class="textbg">申请开通</a>
							</s:if>
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >
							<s:hidden name="course.id"></s:hidden>
						</td>
						<td >

							<input class="textbg6" name="submit" type="submit" value="确认修改" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
