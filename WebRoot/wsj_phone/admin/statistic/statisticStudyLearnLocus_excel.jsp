<%@ page language="java" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=StudyLearn.xls");
%>

	
			<h3>培训班学习轨迹信息</h3>
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
			
				<tr>
					<th height="30" align="center" >
						培训班
					</th>
					<th height="30" align="center" >
						课程名称
					</th>
					<th height="30" align="center" >
						章节名称
					</th>
					<th height="30" align="center" >
						章节练习
					</th>
					<th height="30" width="120" align="center" >
						开始时间
					</th>
					<th height="30" width="120" align="center" >
						结束时间
					</th>
					<th height="30" width="60" align="center" >
						学习时间
					</th>
					<th height="30" width="120" align="center" >
						练习得分/是否通过
					</th>
				</tr>
				<s:iterator value="myCpages">
					<tr>
						<td height="30" align="center">
							<s:property value="cpage.course.className" />
						</td>
						<td height="30" align="center" >
							<s:property value="cpage.course.name" />
						</td>
						<td height="30" align="center" >
							<s:if test="cpage.title==null">─</s:if>
							<s:else>
								<s:property value="cpage.title" />
							</s:else>
						</td>
						<td height="30" align="center" >
							<s:if test="pracp.title==null">─</s:if>
							<s:else>
								<s:property value="pracp.title" />
							</s:else>
						</td>
						<td height="30" align="center" >
							<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td height="30" align="center" >
							<s:if test="endtime==null">非正常退出</s:if>
							<s:else>
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss"/>
							</s:else>
						</td>
						<td height="30" align="center" >
							<s:if test="passtime==-1">─</s:if>
							<s:else>
								<s:property value="passtime2Str" />
							</s:else>
						</td>
						<td height="30" align="center" >
							<s:if test="passed2==-1">─</s:if>
							<s:else>
								<s:property value="myscore" />/
								<s:if test="passed2==1">是</s:if>
								<s:else>否</s:else>
							</s:else>
						</td>
					</tr>
				</s:iterator>
		  </table>
		  <table width="100%" align="center" cellpadding="1" cellspacing="1">
		  		<tr>
		  			<td>
		  			合计：<s:property  value="alltime"/>
		  			</td>
		  		</tr>
		  </table>
