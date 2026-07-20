<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" name="<s:property value="input_name"/>"
	value="<s:property value="department.id"/>">
<label style="width: 250px; float: left;">
	<s:property value="department.name" />
</label>
<a style="cursor: hand; float: right; width: 14px; height: 14px;"
	href=""
	onclick="javascript:deleteDEPUserinfo(this,<s:property value="department.id"/>);return false;">X</a>
 