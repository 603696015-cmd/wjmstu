<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<table width="100%" align="center" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB">
	<tr>
		<td width="80" align="center" >
			试题名称
		</td>
		<td >
			<label>
				<s:property value="question.title" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="80" align="center" >
			题型
		</td>
		<td >
			<label>
				<s:property value="question.qtypeName" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="80" align="center" >
			评分规则
		</td>
		<td >
			<s:if test="question.qtype==8"><!--
					范文最少字数：
					-->
					范文长度:<s:property value="question.fwsize"/>
					<input type="hidden" size="4" id="rules1"
					value="<s:property value="question.fwsize"/>" d-value="0"/>
					<input type="hidden" size="4" id="dazi_fwsize" d-value="0">
					<!--<br />
					准确分：
					--><input type="hidden" size="4" id="rules2"
					value="<s:property value="1"/>" />
					<br />
					时&nbsp;&nbsp;&nbsp;&nbsp;长：
					<input type="text" size="4" id="rules3"
					value="<s:property value="question.rules[2]"/>" />分钟
					<br />
					评分策略： <a class="textbg4" onclick="daziruleadd();return false;" href="#" >添加</a>  <a href="#" class="textbg4" onclick="daziruledelete();return false;">删除</a>
				<div id="dazi_rule">
				<s:set name="rulesize" value="0"></s:set>
				<s:iterator value="question.dazirule" status="dazist">
					<div id="dazi_rule<s:property value="#dazist.index+1"/>">
						年龄段<s:property value="#dazist.index+1"/>：<input id="b_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="question.dazirule[#dazist.index][0]"/>" /> 到<input id="e_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="question.dazirule[#dazist.index][1]"/>" />
						及格速度：<input id="jg_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="question.dazirule[#dazist.index][2]"/>" /> 
						优秀速度：<input onblur="setDaziMax()"  id="yx_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="question.dazirule[#dazist.index][3]"/>" /> 
						满分速度：<input id="mf_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="question.dazirule[#dazist.index][4]"/>" />
					</div>
				<s:set name="rulesize" value="#dazist.index+1"></s:set>
				</s:iterator>
				<script type="text/javascript">dazi = <s:property value="#rulesize+1"/>;</script>
				</div>
			</s:if>
			<label>
				<s:if test="question.qtype==9">
					发 给：
					<input type="text" size="4" id="rules1"
						value="<s:property value="question.rules[0]"/>" />分
					<br />
					抄 送：
					<input type="text" size="4" id="rules2"
						value="<s:property value="question.rules[1]"/>" />分
					<br />
					密 送：
					<input type="text" size="4" id="rules3"
						value="<s:property value="question.rules[2]"/>" />分
					<br />
					主 题：
					<input type="text" size="4" id="rules4"
						value="<s:property value="question.rules[3]"/>" />分
					<br />
					附 件：
					<input type="text" size="4" id="rules5"
						value="<s:property value="question.rules[4]"/>" />分
					<br />
					正 文：
					<input type="text" size="4" id="rules6"
						value="<s:property value="question.rules[5]"/>" />分
				</s:if>
			</label>
		</td>
	</tr>
	<tr>
		<td width="80" align="center" >
		</td>
		<td >
			<input type="button" class="textbg6"
				onclick="alterexampaperblockquestions(<s:property value="epBlock.id"/>,<s:property value="question.id"/>,<s:property value="question.qtype"/>);"
				name="button" id="button" value="确认修改" />
		</td>
	</tr>
</table>
