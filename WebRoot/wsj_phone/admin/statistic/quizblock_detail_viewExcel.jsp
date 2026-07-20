<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.ExamRoom;"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档
	ExamRoom examRoom = (ExamRoom) request.getAttribute("examRoom");
	response.setHeader("Content-disposition",
			"attachment; filename=Examblock_detail.xls");
%>
<%
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<table width="100%" align="center" cellpadding="1" cellspacing="1"
	border="1">
	<tr>
		<td height="30" colspan="<s:property value="examRoom.myrooms[0].myExamPapers.size+7"/>" align="left">
			考核总人数：
			<b><s:property value="examRoom.userSize" /> </b> 缺考人数：
			<b><s:property value="examRoom.usersize" /> </b>
			<s:set name="roomid" value="examRoom.id"></s:set>
		</td>
	</tr>
	<tr>
		<th width="40" align="center">
			排名
		</th>
		<th align="center">
			姓名
		</th>
		<th align="center">
			所属部门
		</th>
		<th align="center">
			用户名
		</th>
		<s:iterator value="erblock.erepblocks">
			<th align="center" style="color: red;">
				<s:property value="title" />
			</th>
		</s:iterator>
		<th width="90" height="30" align="center">
									考试时间
		</th>
		<th width="40" align="center">
			总成绩
		</th>
		<th width="60" align="center">
			是否通过
		</th>
		<th width="80" align="center">
			试卷数量
		</th>
	</tr>
	<tbody>
		<s:iterator value="examRoom.myrooms" status="ermst">
			<tr>
				<td width="40" align="center">
					<s:property value="#ermst.index+1" />
				</td>
				<td align="center">
					<s:property value="tester.realname" />
				</td>
				<td align="center">
					<s:property value="tester.danwei" />
				</td>
				<td align="center">
					<s:property value="tester.username" />
					&nbsp;
				</td>
				<s:set name="userid" value="tester.id"></s:set>
				<s:iterator value="myExamPapers">
					<td align="center">
					 
							<s:property value="myScore" />
					 
					</td>
				</s:iterator>
				<td width="90" height="30" align="center">
					<s:date name="begintime" format="yyyy-MM-dd HH:mm" />
				</td>
				<td width="40" align="center">
					<s:property value="myScore" />
				</td>
				<td width="60" align="center">
					<s:if test="ispassed==1">通过</s:if>
					<s:else>不通过</s:else>
				</td>
				<td width="80" align="center">
					<s:property value="epsize" />
					个
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
