<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<s:if test="questions.size==0">没有符合条件的试题</s:if>
<s:else>
	<table width="500px" align="left" border="1" cellspacing="1"
		cellpadding="1">
		<tr>
			<th height="20" align="left" >
				标题
				<s:property value="questionart.content" />
			</th>
		</tr>
		<s:iterator value="questionarts">
			<tr>
				<td height="20" align="left" >
					<a href="#"
						onclick="qsearch<s:property value="questionart.content"/>.qanswer_setanswer(this);return false;"><s:property
							value="title" />
					</a>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<th height="20" colspan="2" align="left" >
				<wysLib:page2 objid="${questionart.content}"></wysLib:page2>
			</th>
		</tr>
	</table>
</s:else>
