<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" name="<s:property value="input_name"/>"
	value="<s:property value="examRoom.id"/>">
<label style="width: 150px; float: left;">
	<s:property value="examRoom.title" />
</label>
<a style="cursor: hand; float: right; width: 14px; height: 14px;"
	href=""
	onclick="javascript:deleteExamRoomUserinfo(this,<s:property value="examRoom.id"/>);return false;">X</a>
 