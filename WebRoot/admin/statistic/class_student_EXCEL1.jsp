<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=CLASS_STAT.xls");
%>
<table width="100%" border="1">
	<tr>
		<th width="120" align="center">
			培训班名称
		</th>
		<td width="35%" align="center">
			<s:property value="elClass.name" />
		</td>
		<th width="120" align="center">
			证书名称
		</th>
		<td width="35%" align="center">
			<s:property value="elClass.certificatename" />
		</td>
	</tr>
	<tr>
		<th width="120" align="center">
			创建人
		</th>
		<td width="35%" align="center">
			<s:property value="elClass.creater.realname" />
		</td>
		<th width="120" align="center">
			所属类别
		</th>
		<td width="35%" align="center">
			<s:property value="elClass.cltype.name" />
		</td>
	</tr>
	<tr>
		<th align="center">
			结业条件
		</th>
		<td colspan="3" align="center">
			必修课全部通过，选修课最少获得
			<span style="color: red;"><b> <s:property
						value="elClass.optionalcredit" /> </b> </span> 学分
		</td>
	</tr>
	<tr>
		<th width="120" align="center">
			简介
		</th>
		<td colspan="3" align="center">
			<s:property value="elClass.description" />
		</td>
	</tr>
</table>
<br/>
<table align="center" border="1">
	<tr>
		<td align="center">
			<b>姓名</b>
		</td>
		
		<td align="center">
			<b>地区</b>
		</td>
		<td align="center">
			<b>人群</b>
		</td>
		<td align="center">
			<b>职业类别</b>
		</td>
		<td align="center" >
			<b>考试时间</b>
		</td>
		<td align="center" >
			<b>考试分数</b>
		</td>
		<td align="center">
			<b>获证时间</b>
		</td>
	</tr>
	<s:iterator value="elusers">
		<tr>
			<td style="padding-left: 8px; color: blue;" align="left">
				<s:property value="realname" />
			</td>
			<td align="center" >
				<s:property value="department.name" />
			</td>
			<td align="center" >
				<s:property value="personTypeName" />
			</td>
			<td align="center">
				 <s:if test="workTypeName==null">
			    	无
			    </s:if>
			    <s:else>
				<s:property value="workTypeName" />
				</s:else>
			</td>
			<td  align="center" >
				<s:property value="begintime" />
			</td>
			<td align="center" >
				<s:property value="myscore" />
			</td>
			<td align="center">
				<s:if test="graddate == null">尚无证书</s:if>
				<s:else>
					<s:date format="yyyy-MM-dd" name="graddate" />
				</s:else>
			</td>
		</tr>
	</s:iterator>
</table>
