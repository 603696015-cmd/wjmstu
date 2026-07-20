<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" name="<s:property value="input_name"/>"
	value="<s:property value="elUser.id"/>">

<label style="width: 80px; float: left;">
	<s:property value="elUser.realname" />
</label>

<a style="cursor: hand; float: right; width: 14px; height: 14px;"
	href=""
	onclick="javascript:deleteUserinfo(this,<s:property value="elUser.id"/>);return false;">X</a>
 