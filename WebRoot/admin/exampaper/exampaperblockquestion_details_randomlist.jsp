<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<s:if test="epRandoms.size!=0">
		<table width="900" align="center" cellpadding="1" cellspacing="1">
			<tr>
				<th align="center" >
					试题库
				</th>
				<th align="center" >
					1级/ 总数
				</th>
				<th align="center" >
					2级 / 总数 
				</th>
				<th align="center" >
					3级/ 总数
				</th>
				<th align="center" >
					4级 / 总数
				</th>
				<th align="center" >
					5级 / 总数
				</th>
				<th align="center" >
					不限难度 / 总数
				</th> 
			</tr>
			<s:iterator value="epRandoms">
				<tr>
					<td align="center" >
						<s:property value="qlib.name" />
						<s:if test="suboperate==1">
									(包含下级题库)
									</s:if>
						<s:else>
									(不包含下级题库)
									</s:else>
					</td>
					<td align="center" >
						<s:property value="qlevel1" />
						/
						<s:property value="qlevel1_" />
					</td>
					<td align="center" >
						<s:property value="qlevel2" />
						/
						<s:property value="qlevel2_" />
					</td>
					<td align="center" >
						<s:property value="qlevel3" />
						/
						<s:property value="qlevel3_" />
					</td>
					<td align="center" >
						<s:property value="qlevel4" />
						/
						<s:property value="qlevel4_" />
					</td>
					<td align="center" >
						<s:property value="qlevel5" />
						/
						<s:property value="qlevel5_" />
					</td>
					<td align="center" >
						<s:property value="qlevel" />
						/
						<s:property value="qlevel_" />
					</td> 
				</tr>

			</s:iterator>
		</table>
</s:if>
