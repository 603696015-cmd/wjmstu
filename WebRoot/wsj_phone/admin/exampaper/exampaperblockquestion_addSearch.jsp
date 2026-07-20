<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<table align="center" cellpadding="1" cellspacing="1">
	<tr>
		<td width="130">
			试题名称关键字
		</td>
		<td>
			<input type="text" id="epb_q_title" name="question.title">
			<s:if test="epBlock.type!=12">
				<input type="hidden" id="epb_q_type" name="question.qtype"
					value="<s:property value="epBlock.type"/>" />
			</s:if>
		</td>
	</tr>
	<s:if test="epBlock.type==12">
		<tr>
			<td>
				试题类型
			</td>
			<td>
				<select name="epBlock.type" id="epb_q_type">
					<option value="1" id="type_1">
						判断题
					</option>
					<option value="2" id="type_2">
						单项选择题
					</option>
					<option value="4" id="type_4">
						多项选择题
					</option>
					<option value="5" id="type_5">
						填空题
					</option>
					<option value="6" id="type_6">
						问答题
					</option>
					<option value="7" id="type_7">
						材料题
					</option>
					<option value="8" id="type_8">
						打字题
					</option>
					<option value="9" id="type_9">
						邮件题
					</option>
					<option value="10" id="type_10">
						搜索题
					</option>
					<option value="11" id="type_11">
						office题
					</option>
				</select>

			</td>
		</tr>
	</s:if>
	<tr>
		<td>
			所属知识点
		</td>
		<td>
			<SELECT name="question.qlib.id" id="epb_q_lib">
				<wysLib:qlibselect></wysLib:qlibselect>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td>
			包含子试题库
		</td>
		<td>
			<input type="checkbox" name="sublibs" id="epb_q_sublibs" value="1">
			<input type="hidden" name="pN" value="0" id="epb_q_pn">
			<input type="hidden" name="pS" value="10" id="epb_q_ps">
			<input type="hidden" name="epBlock.id"
				value="<s:property value="epBlock.id"/>" id="epb_q_blid">
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" class="textbg6"
				onclick="addexampaperblockquestionslist(<s:property value="epBlock.id"/>)"
				value="搜索">
		</td>
	</tr>
</table>

