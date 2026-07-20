<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<table width="530" align="center" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB">
	<tr>
		<td width="100" align="center" >
			<span class="neededitem">*</span>大题名称
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<input name="epBlock.title"
					value="<s:property value="epBlock.title"/>" type="text"
					id="epbtitle" size="60" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="100" align="center" >
			大题说明
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<textarea name="epBlock.description" id="epbdesc" cols="40" rows="4"><s:property value="epBlock.description" /></textarea>
			</label>
		</td>
	</tr>
	<tr>
		<td width="100" align="center" >
			<span class="neededitem">*</span>题型
		</td>
		<td >
			<s:property value="epBlock.typeName"/>
			<s:hidden id="epbtype" name="epBlock.type"></s:hidden>
			<!--<select name="epBlock.type" id="epbtype">
				<option value="1"
					<s:if test="epBlock.type==1">selected="selected"</s:if> id="type_1">
					判断题
				</option>
				<option value="2"
					<s:if test="epBlock.type==2">selected="selected"</s:if> id="type_2">
					单项选择题
				</option>
				<option value="3"
					<s:if test="epBlock.type==3">selected="selected"</s:if> id="type_3">
					不定项选择题
				</option>
				<option value="4"
					<s:if test="epBlock.type==4">selected="selected"</s:if> id="type_4">
					多项选择题
				</option>
				<option value="5"
					<s:if test="epBlock.type==5">selected="selected"</s:if> id="type_5">
					填空题
				</option>
				<option value="6"
					<s:if test="epBlock.type==6">selected="selected"</s:if> id="type_6">
					问答题
				</option>
				<option value="7"
					<s:if test="epBlock.type==7">selected="selected"</s:if> id="type_7">
					材料题
				</option>
				 <option value="8" <s:if test="epBlock.type==8">selected="selected"</s:if> id="type_8">
					打字题
				</option>
				<option value="9" <s:if test="epBlock.type==9">selected="selected"</s:if> id="type_9">
					邮件题
				</option>
				<option value="10" <s:if test="epBlock.type==10">selected="selected"</s:if> id="type_10">
					搜索题
				</option>
				<option value="11" <s:if test="epBlock.type==11">selected="selected"</s:if> id="type_11">
					office题
				</option>
				<option value="12" <s:if test="epBlock.type==12">selected="selected"</s:if> id="type_12">
					选做题
				</option>
			</select>
		--></td>
		<td width="100" align="center" >
			<span class="neededitem">*</span>出题方式
		</td>
		<td >
			<s:if test="epBlock.random==0"><label>
				手工
			</label></s:if>
			<s:else><label>
				随机
			</label></s:else>
			<input type="hidden" id="epbrandom" name="epBlock.random" value="<s:property value="epBlock.random"/>"/>
		</td>
	</tr>
	<tr>
		<td width="100" align="center" style="font-weight:bolder;">
			<s:if test="epBlock.type == 12">
				<span class="neededitem">*</span>选作题数
			</s:if>
			<s:else>	
				<span class="neededitem">*</span>试题总数
			</s:else>
		</td>
		<td >
			<label>
				<input type="text"
					value="<s:property value="epBlock.questionamount"/>"
					name="epBlock.questionamount" id="questionamount" size="6" />
				<input type="hidden" value="<s:property value="epBlock.id"/>"
					id="epblockid" />
				<input type="hidden" value="<s:property value="epBlock.realqamount"/>"
					id="epb_realqamount" />
			</label>
		</td>
		<td width="100" align="center" style="font-weight:bolder;">
			<span class="neededitem">*</span>每题分数
		</td>
		<td >
			<label>
				<input name="epBlock.eachscore" type="text" id="epbeachscore"
					value="<s:property value="epBlock.eachscore"/>" size="6" />
			</label>
		</td>
	</tr>
	<tr >
		<td width="100" align="center"  >
			<span style="font-weight:bolder;" ><span class="neededitem">*</span>答题时长</span>
		</td>
		<td >
			<label>
				<input type="text" name="epBlock.answerTime" id="answerTimet"
					size="6" value="${epBlock.answerTime}" />
			</label>
		</td>
		<td width="100" align="center" >
			<span style="font-weight:bolder;"><span class="neededitem">*</span>第二次答对得分</span>
		</td>
		<td >
			<label>
				<input name="epBlock.secondScore" type="text" id="secondScore"
					value="${epBlock.secondScore}" size="6" />
			</label>
		</td>
		
	</tr>
	<tr id="rule_tr" style="<s:if test="(epBlock.random==1&&(epBlock.type==8||epBlock.type==9))">display: block;</s:if><s:else>display:none</s:else>">
		<td width="100" align="center" >
			<span class="neededitem">*</span>评分规则
		</td>
		<td colspan="3" id="rule_td">
		<s:if test="epBlock.type==8"><!--
					范文最少字数：
					--><input type="hidden" size="4" id="rules1"
					value="1" d-value="1"/>
					<!--<br />
					准确分：
					--><input type="hidden" size="4" id="rules2"
					value="<s:property value="1"/>" />
					<br />
					时&nbsp;&nbsp;&nbsp;&nbsp;长：
					<input type="text" size="4" id="rules3"
					value="<s:property value="epBlock.rules[2]"/>" />分钟
					<br />
					评分策略： <a class="textbg4" onclick="daziruleadd();return false;" href="#" >添加</a>  <a href="#" class="textbg4" onclick="daziruledelete();return false;">删除</a>
				<div id="dazi_rule">
				<s:set name="rulesize" value="0"></s:set>
				<s:iterator value="epBlock.dazirule" status="dazist">
					<div id="dazi_rule<s:property value="#dazist.index+1"/>">
					年龄段<s:property value="#dazist.index+1"/>：<input id="b_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="epBlock.dazirule[#dazist.index][0]"/>" /> 到<input id="e_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="epBlock.dazirule[#dazist.index][1]"/>" />
					及格速度：<input id="jg_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="epBlock.dazirule[#dazist.index][2]"/>" />
					优秀速度：<input onblur="setDaziMax()" id="yx_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="epBlock.dazirule[#dazist.index][3]"/>" />
					满分速度：<input id="mf_dazirules<s:property value="#dazist.index+1"/>" style="width:30px;" value="<s:property value="epBlock.dazirule[#dazist.index][4]"/>" />
					</div>
				<s:set name="rulesize" value="#dazist.index+1"></s:set>
				</s:iterator>
				<script type="text/javascript">dazi = <s:property value="#rulesize+1"/>;</script>
				</div>
			</s:if>
				<s:if test="epBlock.type==9">
					发 给：
					<input type="text" size="4" id="rules1" value="<s:property value="epBlock.rules[0]"/>"/>分
					<br />
					抄 送：
					<input type="text" size="4" id="rules2" value="<s:property value="epBlock.rules[1]"/>" />分
					<br />
					密 送：
					<input type="text" size="4" id="rules3" value="<s:property value="epBlock.rules[2]"/>" />分
					<br />
					主 题：
					<input type="text" size="4" id="rules4" value="<s:property value="epBlock.rules[3]"/>" />分
					<br />
					附 件：
					<input type="text" size="4" id="rules5" value="<s:property value="epBlock.rules[4]"/>" />分
					<br />
					正 文：
					<input type="text" size="4" id="rules6" value="<s:property value="epBlock.rules[5]"/>" />分
				</s:if>
				<s:if test="epBlock.type==12">
					<!-- 共 有： -->
					<input type="hidden" size="4" id="rules1" value="<s:property value="epBlock.rules[0]"/>"/>
					<input type="hidden" size="4" id="rules2"
					value="<s:property value="epBlock.rules[1]"/>" />
					<input type="hidden" size="4" id="rules3"
					value="<s:property value="epBlock.rules[2]"/>" />
					<!-- <br />
					选 做：
					<input type="text" size="4" id="rules2" value="<s:property value="epBlock.rules[1]"/>" />题
					<br />
					每 题：
					<input type="text" size="4" id="rules3" value="<s:property value="epBlock.rules[2]"/>" />分 -->
				</s:if>
		</td>		
	</tr>
	<tr id="rule_td_dz" style="<s:if test="epBlock.type==8&&epBlock.random==1">display: block;</s:if><s:else>display:none</s:else>">
		<td width="100" align="center" >
			<span class="neededitem">*</span>范文最少字数
		</td>
		<td colspan="3">
					<input type="text" size="4" id="dazi_fwsize"
					value="<s:property value="epBlock.fwsize"/>" d-value="<s:property value="epBlock.fwsize"/>"/>
		</td>		
	</tr>
	<tr>
		<td width="100" align="center" >
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<input class="textbg6" type="button" onclick="alterepblock();" name="button"
				id="button" value="确认修改" />
			<input type="button" onclick="dia_close();return false;"
				 class="textbg6" name="button" id="button"
				value="取消" />
		</td>
	</tr>
</table>
