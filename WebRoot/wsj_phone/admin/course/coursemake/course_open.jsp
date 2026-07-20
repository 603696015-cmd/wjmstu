<%@ page language="java" pageEncoding="UTF-8"   %>
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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程开通" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程开通</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div style="font-size: 15px; font-weight: bolder">
				<B>注意：</B> 请确定课件无误，提交开通后将提交给管理员审核。通过后方可使用，通过审核后 将不能对它做学习的操作的修改。
				<br />
				<span style="color: blue"><s:property value="elmessage" /> </span>
			</div>
			<table cellpadding="1" width="500" cellspacing="1" >
				<tr>
					<td width="160" height="30" align="center" >
						课程名称：
					</td>
					<td >
						<label>
							<s:property value="course.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						课程介绍：
					</td>
					<td >
						<label>
							<s:property value="course.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						讲师姓名：
					</td>
					<td >
						<label>
							<s:property value="course.teacherName" />
						</label>
					</td>
				</tr>
				<!--<tr>
						<td width="160" height="30" align="center" >
							师资介绍：
						</td>
						<td >
							<label>
								<s:textarea name="course.teacherinfo" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					<tr>
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
							<s:property value="course.mainimg" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						所属类别：
					</td>
					<td >
						<label>
							<s:property value="course.ctype.name" />
						</label>
					</td>
				</tr>

				<tr>
					<td width="160" height="30" align="center" >
						推荐学分：
					</td>
					<td >
						<label>
							<s:property value="course.credit" />
						</label>
					</td>
				</tr>
				<!--<tr>
					<td width="160" height="30" align="center" >
						通过成绩：
					</td>
					<td >
						<label>
							<s:property value="course.passgrade" />
							%
						</label>
					</td>
				</tr>
				--><tr>
					<td align="center" >
						课程类型：
					</td>
					<td >
						<label>
							<span><s:property value="course.islinkName" /> </span>
							<br />
							<s:if test="course.islink==1||course.islink==3">
								<span id="url_desc">外部课程链接地址</span>
								<s:property value="course.exurl" />

							</s:if>
							<br>
							学习时间：
							<s:property value="course.during" />
							<br>
							分钟 学习询问时间：
							<s:property value="course.querytime" />
							分钟

						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						获得学分方式：
					</td>
					<td >
						<label>
							<s:property value="course.creditmodName" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						章节数：
					</td>
					<td >
						<label>
							<s:property value="course.cpagesize" />
						</label>
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
					</td>
				</tr>
				<tr>
					<td width="160" height="30" colspan="2" align="center"
						>
						<a target="_blank"
							href="course_preview.action?course.id=<s:property value="course.id"/>">预览</a>
							<s:if test="course.status==0||course.status==5"> 
								<s:form onsubmit="return confrim('确定课程制作无误吗？')" action="course_open" method="post" theme="simple">
									 			
									<!-- 提交申请标题：<s:textarea name="courseAudit.title" cols="30" rows="1"></s:textarea><br/> -->
									备注详情：<a href="course_audit.action?course.id=<s:property value="course.id"/>&courseAudit.status=2"
															style="color: black;">查看回复</a>　
											
										<label>
											<s:textarea name="courseAudit.content" cols="60" rows="7"></s:textarea> 
										</label>					
									<label>
									<s:hidden name="course.id"></s:hidden> 
									<input type="submit" value="提交开通">
								</s:form>
							</s:if> 
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
