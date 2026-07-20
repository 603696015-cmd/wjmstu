<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<select id="select3" onchange="changeSelect3();">
	<option value="-1" selected="selected">
			全部
	</option>
	<s:iterator value="coursePages">
		<option value="<s:property value="id"/>">
				<s:property value="title" />
		</option>
	</s:iterator>
</select>
