<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<span style="color: #ff0000;">${elmessage} </span>
<s:if test="sublibs==0">
<table width="900" align="center" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB">
	<tr>
		<td width="160" align="center" >
			试题库
		</td>
		<td >
			<label>
				<s:property value="question.qlib.name" />
				<s:if test="sub_operate==1">
					(包含下级题库)
				</s:if>
				<s:else>
					(不包含下级题库)
				</s:else>
				<input type="hidden" name="epRandom.suboperate"
					value="${sub_operate}" id="epr_sub_operate" />
				<input type="hidden" id="sub_operate" name="suboperate"
					value="${sub_operate}" />
				<input name="epRandom.qlib.id"
					value="<s:property value="question.qlib.id"/>" id="epr_qlibid" type="hidden" />
				<input name="epRandom.epBlock.id"
					value="<s:property value="epBlock.id"/>" id="epr_epbid" type="hidden" />
				<input name="question.qlib.id"
					value="<s:property value="question.qlib.id"/>" id="epb_q_lib" type="hidden" />
				<s:hidden name="epBlock.id" />
				<s:hidden name="epBlock.type" id="epb_q_type" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			1级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel1" type="text" id="epr_qlevel1" size="10"
					value="0" max-v="<s:property value="epRandom1.qlevel1" />" />
				/ 总数
				<s:property value="epRandom1.qlevel1" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			2级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel2" type="text" id="epr_qlevel2" size="10"
					value="0" max-v="<s:property value="epRandom1.qlevel2" />"  />
				/ 总数
				<s:property value="epRandom1.qlevel2" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			3级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel3" type="text" id="epr_qlevel3" size="10"
					value="0" max-v="<s:property value="epRandom1.qlevel3" />"  />
				/ 总数
				<s:property value="epRandom1.qlevel3" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			4级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel4" type="text" id="epr_qlevel4" size="10"
					value="0" max-v="<s:property value="epRandom1.qlevel4" />"  />
				/ 总数
				<s:property value="epRandom1.qlevel4" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			5级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel5" type="text" id="epr_qlevel5" size="10"
					value="0" max-v="<s:property value="epRandom1.qlevel5" />"  />
				/ 总数
				<s:property value="epRandom1.qlevel5" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			不限
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel" type="text" id="epr_qlevel"
					value="0" size="10" max-v="<s:property value="epRandom1.qlevel" />"  />
				/ 总数
				<s:property value="epRandom1.qlevel" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			&nbsp;
		</td>
		<td >
			<input type="hidden" id="epbquestionamount" value="<s:property value="epBlock.questionamount" />"/>
			<input type="hidden" id="epbrealqamount" value="<s:property value="epBlock.realqamount" />"/>
			<input type="button" class="textbg6" name="button" onclick="addexampaperblockquestions_randomadd2(<s:property value="epBlock.id"/>)" value="确认添加" />
			<input type="button" class="textbg6"
				value="重新搜索" onclick="addexampaperblockquestions(<s:property value="epBlock.id" />)">
		</td>
	</tr>
</table>
</s:if>
<s:else>
<table width="900" align="center" id="randomlist" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB">
	<tr><th width="160" align="center" >
			试题库
		</th>
		<td align="center" width="90">
			 1级/总数
		</td>
		<td align="center" width="90">
			 2级/总数
		</td>
		<td align="center" width="90">
			 3级/总数
		</td>
		<td align="center" width="90">
			 4级/总数
		</td>
		<td align="center" width="90">
			 5级/总数
		</td>
		<td align="center" width="90">
			 不限级/总数
		</td>
		<td align="center" >
		</td>
	</tr>
	<s:iterator value="epRandoms1">
	<tr>
		<td width="160" align="center" >
			<s:property value="qlib.name" />
		</td> 
		<td align="center">
				<input name="qlevel1" type="text" id="epr_qlevel1" size="3"
					value="0" max-v="<s:property value="qlevel1" />" />
				/<s:property value="qlevel1" />
		</td>
		<td align="center">
				<input name="qlevel2" type="text" id="epr_qlevel2" size="3"
					value="0" max-v="<s:property value="qlevel2" />"  />
				/<s:property value="qlevel2" />
		</td>
		<td align="center">
				<input name="qlevel3" type="text" id="epr_qlevel3" size="3"
					value="0" max-v="<s:property value="qlevel3" />"  />
				/<s:property value="qlevel3" />
		</td>
		<td align="center">
				<input name="qlevel4" type="text" id="epr_qlevel4" size="3"
					value="0" max-v="<s:property value="qlevel4" />"  />
				/<s:property value="qlevel4" />
		</td>
		<td align="center">
				<input name="qlevel5" type="text" id="epr_qlevel5" size="3"
					value="0" max-v="<s:property value="qlevel5" />"  />
				/<s:property value="qlevel5" />
		</td>
		<td align="center">
				<input name="qlevel" type="text" id="epr_qlevel"
					value="0" size="3" max-v="<s:property value="qlevel" />"  />
				/<s:property value="qlevel" />
		</td>
		<td >
		&nbsp;<input name="qlevel" type="hidden" 
					value="<s:property value="qlib.id"/>" />
		</td>
	</tr>
	</s:iterator>
	<tr>
		<td width="160" align="center" >
			&nbsp;
		</td>
		<td colspan="7">
			<input type="hidden" name="epRandom.suboperate"
					value="${sub_operate}" id="epr_sub_operate" />
				<input type="hidden" id="sub_operate" name="suboperate"
					value="${sub_operate}" />
				<input name="epRandom.qlib.id"
					value="<s:property value="question.qlib.id"/>" id="epr_qlibid" type="hidden" />
				<input name="epRandom.epBlock.id"
					value="<s:property value="epBlock.id"/>" id="epr_epbid" type="hidden" />
				<input name="question.qlib.id"
					value="<s:property value="question.qlib.id"/>" id="epb_q_lib" type="hidden" />
				<s:hidden name="epBlock.id" />
				<s:hidden name="epBlock.type" id="epb_q_type" />
			<input type="hidden" id="epbquestionamount" value="<s:property value="epBlock.questionamount" />"/>
			<input type="hidden" id="epbrealqamount" value="<s:property value="epBlock.realqamount" />"/>
			<input type="button" class="textbg6" name="button" onclick="addexampaperblockquestions_randomadd(<s:property value="epBlock.id"/>)" value="确认添加" />
			<input type="button" class="textbg6"
				value="重新搜索" onclick="addexampaperblockquestions(<s:property value="epBlock.id" />)">
		</td>
	</tr>
</table>

</s:else>