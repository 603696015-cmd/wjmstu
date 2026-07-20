<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel" %> 
<% 
    //就是靠这一行，让前端浏览器以为接收到一个excel档   
     response.setHeader("Content-disposition","attachment; filename=ctime_user_list.xls"); 
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<BODY>
			<table width="90%" align="center" border="1">
				<tr>
					<td align="center" >
						姓名
					</td>
					<td align="center" >
						账号
					</td>
					<td align="center" >
						部门
					</td>
					<td align="center" >
						课程总时长
					</td>
					<td align="center" >
						已学总时长
					</td>
					<!--<td align="center" >
						总学分
					</td>
					<td align="center" >
						已获学分
					</td>-->
				</tr>
				<s:iterator value="elUsers" status="st">
					<tr>
						<td align="center" >
							<s:property value="realname" />
						</td>
						<td align="center" >
							<s:property value="username" />&nbsp;
						</td>
						<td align="center" >
							<s:property value="department.name" />
						</td>
						<td align="center" >
						<s:property value="ct_time" />
						</td>
						<td align="center" >
						<s:property value="xx_time" />
						</td>
						<!--<td align="center" >
						<s:property value="ct_credit" />
						</td>
						<td align="center" >
						<s:property value="xx_credit" />
						</td>-->
					</tr>
				</s:iterator>
			</table>
	</BODY>
</HTML>
