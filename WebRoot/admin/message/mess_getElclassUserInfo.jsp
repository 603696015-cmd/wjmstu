<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" name="<s:property value="input_name"/>"
	value="<s:property value="elClass.id"/>">
<label style="width: 150px; float: left;">
	<s:property value="elClass.name" />
</label>
<a style="cursor: hand; float: right; width: 14px; height: 14px;"
	href=""
	onclick="javascript:deleteElclassUserinfo(this,<s:property value="elClass.id"/>);return false;">X</a>
 