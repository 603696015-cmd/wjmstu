<%@ page language="java" pageEncoding="gbk"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档
	response.setHeader("Content-disposition",
			"attachment; filename=Export_Examination_Survey.xls");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<table width="600" align="center" border="1" bgcolor="#EBEBEB">
	<tr>
		<td height="30" align="center">
		部门名称
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="name" />
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			应考试人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.joinusersize" />
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			应考人员平均分
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.avgscore" />
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			参考人员平均分
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:if test="examRoom.avgscorejoin == NaN"> 0	</s:if>
				<s:else>
					<s:property value="examRoom.avgscorejoin" />
				</s:else>
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			参加人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.usersize" />
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			缺考人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.LOEusersize" />
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			及格人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.passsize" />
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			参考人员及格率
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.passgrade" />
				%
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			应考人员及格率
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.passgrade2" />
				%
			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30"  align="center">
			90分以上人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.pass9_" />

			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			80-90分人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.pass8_9" />

			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			70-80分人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.pass7_8" />

			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			60-70分人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.pass6_7" />

			</td>
		</s:iterator>
	</tr>
	<tr>
		<td height="30" align="center">
			60以下人数
		</td>
		<s:iterator value="departments1">
			<td height="30" align="center">
				<s:property value="examRoom.pass_6" />

			</td>
		</s:iterator>
	</tr>
</table>
