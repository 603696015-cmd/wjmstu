<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" name="<s:property value="input_name"/>"
	value="<s:property value="examRoom.id"/>">
<div style="float: left;">
	<table width="600px" style="margin: 0px;" align="center" cellpadding="1" cellspacing="1">
		<tr>
			<td height="20" align="center">
				考场名称
			</td>
			<td height="20" align="center">
				开始时间
			</td>
			<td height="20" width="150px" align="center">
				结束时间
			</td>
		</tr>
		<tr>
			<td height="20" align="center">
				<s:property value="examRoom.title" />
			</td>
			<td height="20" align="center">
				<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td height="20" align="center">
				<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
		</tr>
	</table>
</div>
<a
	style="cursor: hand; float: right; width: 14px; height: 14px; font-size: 14px;"
	href=""
	onclick="javascript:deleteerinfo(this,<s:property value="examRoom.id"/>);return false;">X</a>
