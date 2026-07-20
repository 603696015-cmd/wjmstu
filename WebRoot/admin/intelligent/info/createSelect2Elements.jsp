<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<select id="select2" onchange="changeSelect2();">
	<option value="-1" selected="selected">
			全部
	</option>
	<s:iterator value="courses">
		<option value="<s:property value="id"/>">
				<s:property value="name" />
		</option>
	</s:iterator>
</select>
