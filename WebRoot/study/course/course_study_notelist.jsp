<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>查看笔记</TITLE>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY style="">
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="笔记列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看笔记</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_study_noteAddInit.action?course.id=<s:property value="course.id"/>">
					做笔记 </a>
			</li>-->
		</ul>
		<s:if test="cnotes.size==0">
			<div align="center">
				无笔记
			</div>
		</s:if>
		<s:else>
        <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
  <tr>
    <td colspan="5" align="center" bgcolor="#F8FCFE"><caption>
					课程:
					<s:property value="course.name" />
					的笔记
					<!-- (最晚提交时间：<s:date format="yyyy-MM-dd HH:mm:ss" name="course.notedate" />) -->
			  </caption></tr>
  <tr>
    <th width="200" bgcolor="#F8FCFE">内容： </th>
    <th width="260" bgcolor="#F8FCFE">编写日期 </th>
    <th width="260" bgcolor="#F8FCFE">最后修改日期 </th>
    <th width="64" bgcolor="#F8FCFE">学分</th>
    <th bgcolor="#F8FCFE">&nbsp;</th>
  </tr>
</table>

			<table width="100%" align="center" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">
				<s:set name="courseid" value="course.id"></s:set>
				<s:iterator value="cnotes">
					<tr>
						<td width="196" height="30" bgcolor="#F8FCFE"  style="padding-left:5px;">
							<a
								href="javascript:showContent('content_<s:property value="id"/>')"><s:property
									value="title" /> 
							</a>
						</td>
						<td align="center" bgcolor="#F8FCFE" width="260">
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
					  </td>
						<td align="center" bgcolor="#F8FCFE" width="260">
							<s:date name="modifytime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center" bgcolor="#F8FCFE" width="64">
							<s:property value="score" />
						</td>
						<td align="center" bgcolor="#F8FCFE">
						  <a class="textbg4"
								href="course_study_noteAlterInit.action?cnote.id=<s:property value="id"/>&course.id=<s:property value="#courseid"/>&elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>">编辑</a>
							<a class="textbg4" onClick="return confirm('确定删除该条笔记？')"
								href="course_study_noteDelete.action?cnote.id=<s:property value="id"/>&course.id=<s:property value="#courseid"/>&elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>">删除</a>
							<!--<s:if test="status==0">
								<a onclick="return confirm('确定提交该条笔记？提交后笔记编辑修改将不会改变学分。')"
									href="course_study_notesubmit.action?cnote.id=<s:property value="id"/>&course.id=<s:property value="#courseid"/>">提交</a>
							</s:if>
							<s:else>已提交</s:else>
						-->
						</td>
				  </tr>
					<tr style="display: none;" id="content_<s:property value="id"/>">
						<td bgcolor="#FFFFFF" style="font: 12px; padding: 5px;"
							colspan="3">
							<s:property value="content" />
						</td>
					</tr>
				</s:iterator>
		  </table>
	</s:else>
		<div style="text-align: center; margin-top: 10px;">
			<a style="width: 80px;" class="textbg4"
				href="course_study_noteAddInit.action?course.id=<s:property value="course.id"/>&elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>">添加笔记</a>
			<a class="textbg4"
				href="myelclass_view.action?elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>">返回</a>
		</div>
	</BODY>
</HTML>
