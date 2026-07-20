<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<form action="exampaperblockquestion_addRandomInit.action" method="post"
	name="questions_info" id="questions_info">
</form>
<table align="center" cellpadding="1" cellspacing="1">
	<tr>
		<td width="130">
			所属知识点
		</td>
		<td>
			<SELECT name="question.qlib.id" onchange="change_qlib(this,this.selectedIndex,<s:property value="epBlock.id"/>);" id="epb_q_lib">
				<wysLib:qlibselect></wysLib:qlibselect>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td>
			包含子试题库
		</td>
		<td>
			<input type="checkbox" name="sub_operate" onclick="click_sub(<s:property value="epBlock.id"/>);" id="epb_q_sublibs"  value="1">
			<input type="hidden" name="pN" value="0" id="epb_q_pn">
			<input type="hidden" name="pS" value="10" id="epb_q_ps">
			<input type="hidden" name="epBlock.type" value=<s:property value="epBlock.type"/> id="epb_q_type">
			<input type="hidden" name="epBlock.id"
				value="<s:property value="epBlock.id"/>"  id="epb_q_blid">
		</td>
	</tr>
	<tr>
		<td>
			批量操作
		</td>
		<td>
			<input type="checkbox" name="sublibs" id="epb_q_batch"  value="1">
		</td>
	</tr>
	<s:if test="epBlock.type==8">
	<tr>
		<td>
			范文最少字数
		</td>
		<td>
			<input type="text" name="epBlock.fwsize" readonly="readonly" style="border:none;font-weight: bolder;"
				value="<s:property value="epBlock.fwsize"/>" size="4" id="epb_q_fwsize">
		</td>
	</tr>
	</s:if>
	<tr>
		<td colspan="2" align="center">
			<input type="button" class="textbg6"
				onclick="addexampaperblockquestions_random(<s:property value="epBlock.id"/>)"
				value="下一步">
		</td>
	</tr>
</table>