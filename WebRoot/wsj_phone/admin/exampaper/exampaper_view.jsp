<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<table width="900" align="center" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB">
	<tr>
		<td width="160" align="center" >
			试卷标题
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<s:property value="examPaper.title" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			试卷呈现方式
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<s:property value="examPaper.showTypeName" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			查询题网站链接
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<s:iterator value="examPaper.queryurls">
					<s:property value="title" />:<s:property value="href" />
					<br />
				</s:iterator>
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			所属试卷库
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<s:property value="examPaper.epl.name" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			试卷说明
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<s:property value="examPaper.description" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			试卷时长（分钟）
		</td>
		<td >
			<label>
				<s:property value="examPaper.during" />
			</label>
		</td>
		<td width="160" align="center" >
			试题总分
		</td>
		<td >
			<s:property value="examPaper.ep_tscore" />
		</td>
	</tr>
	<!--<tr>
		<td width="160" align="center" >
			出题方式
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<s:if test="examPaper.showmod==0">
						普通显示
				</s:if>
				<s:else>
						逐题显示
				</s:else>
			</label>
		</td>
	</tr>
--></table>
<input id="ep_id" type="hidden"
	value="<s:property value="examPaper.id"/>" name="examPaper.id" />
