<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>个人未审核</TITLE> 
	</HEAD>
	<body>
		<div id="container">
			<table width="1001" height="42" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<s:iterator value="myNoPass" status="status">
					<s:if
						test="#status.index==0 || (#status.index!=0 && #status.index%4==0)">
						<tr>
							<td >
								<span class="STYLE1">*</span>
								<a
									href="myContactTags.action?tablename=<s:property value='tablename' />">未审<s:property
										value='moduleName' /> </a>
								<s:property value='count' />
							</td>
					</s:if>
					<s:else>
						<s:if
							test="#status.index%4==1 || #status.index%4==2">
							<td>
								<span class="STYLE1">*</span>
								<a
									href="myContactTags.action?tablename=<s:property value='tablename' />">未审<s:property
										value='moduleName' /> </a>
								<s:property value='count' />
							</td>
						</s:if>
						<s:else>
							<td>
								<span class="STYLE1">*</span>
								<a
									href="myContactTags.action?tablename=<s:property value='tablename' />">未审<s:property
										value='moduleName' /> </a>
								<s:property value='count' />
							</td>
							</tr>
						</s:else>
					</s:else>
				</s:iterator>
			</table>
		</div>
	</body>
</HTML>


