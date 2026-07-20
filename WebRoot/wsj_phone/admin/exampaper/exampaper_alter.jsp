<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<script type="text/javascript">
	var ii = 0;
	function addSt(){
		ii++;
		var stuff = document.createElement("div");
		stuff.id= "ds_"+ii;
		stuff.innerHTML="名称：<input type='text' style='width:200px;' name='eatitle' id='stufftt_"+ii+"'/>"+
		"&nbsp;&nbsp;&nbsp;地址：<input type='text' name='eahref' style='width:200px;' id='stufft_"+ii
		+"'> &nbsp;&nbsp;&nbsp; ";
		document.getElementById("stuff").appendChild(stuff);
	}
	function deleteSt(){
		if(ii<=0)return ;
		var stuff = document.getElementById("ds_"+ii);
		document.getElementById("stuff").removeChild(stuff);
		ii--; 
	}
</script>
<table width="900px" align="center" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB">
	<tr>
		<td width="160" align="right">
			<span class="neededitem">*</span>试卷标题：
		</td>
		<td bgcolor="#FFFFFF" colspan="3" style="padding-left: 8px">
			<input type="text" value="<s:property value="examPaper.title"/>"
				name="examPaper.title" id="ep_title" size="60" />
		</td>
	</tr>
	<tr>
		<td width="160" align="right">
			<span class="neededitem">*</span>试卷呈现方式：
		</td>
		<td bgcolor="#EBEBEB" colspan="3">
			一屏一题：<input type="radio" name="examPaper.showType" value=0 <s:if test="examPaper.showType==0">checked="checked"</s:if> />
			一屏一卷：<input type="radio" name="examPaper.showType" value=5 <s:if test="examPaper.showType==5">checked="checked"</s:if> />
			知识竞赛：<input type="radio" name="examPaper.showType" value=10 <s:if test="examPaper.showType==10">checked="checked"</s:if> />
		</td>
	</tr>
	<tr>
		<td align="right">
			查询题网站链接：
		</td>
		<td bgcolor="#FFFFFF" colspan="3" style="padding-left: 8px">
			<div id="stuff">
				<s:iterator value="examPaper.queryurls" status="qurlst">
					<div id='ds_<s:property value="#qurlst.index+1"/>'>
						名称：
						<input type='text' style='width: 200px;'
							value='<s:property value="title"/>' name='eatitle'
							id='stufftt_<s:property value="#qurlst.index+1"/>' />
						&nbsp;&nbsp;&nbsp;地址：
						<input type='text' value='<s:property value="href"/>'
							name='eahref' style='width: 200px;'
							id='stufft_<s:property value="#qurlst.index+1"/>'>
						&nbsp;&nbsp;&nbsp;
					</div>
					<script type="text/javascript">ii++</script>
				</s:iterator>
			</div>
			<input type="button" onClick="addSt();" class=textbg4 value="添加">
			<input type="button" onClick="deleteSt();" class=textbg4 value="删除">
			（http://www.gd.com,注：【http://】不要漏掉）
			<br />
		</td>
	</tr>
	<tr>
		<td align="right">
			<span class="neededitem">*</span>所属试卷库：
		</td>
		<td bgcolor="#FFFFFF" colspan="3" style="padding-left: 8px">
			<%-- 
							<select name="examPaper.epl.id" id="ep_eplid">
								<s:iterator value="eplTrees" id="st">
									<option
										<s:if test="#st.id == examPaper.epl.id">selected='selected'</s:if>
										value="<s:property value="id"/>">
										<s:property value="name" />
									</option>
								</s:iterator>
							</select>
							 --%>
			<select name="examPaper.epl.id" id="ep_eplid">
				<wysLib:elibselect selectid="${examPaper.epl.id}"></wysLib:elibselect>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">
			试卷说明：
		</td>
		<td bgcolor="#FFFFFF" colspan="3" style="padding-left: 8px">
			<textarea name="examPaper.description" id="ep_description" cols="40"
				rows="4"><s:property value="examPaper.description" /></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">
			<span class="neededitem">*</span>试卷时长：
		</td>
		<td style="padding-left: 8px">
			<input type="text" name="examPaper.during"
				value="<s:property value="examPaper.during"/>" id="ep_during"
				size="6" />
			（分钟）
		</td>
		<td align="right">
			<span class="neededitem">*</span>试题总分：
		</td>
		<td style="padding-left: 8px">
			<input type="text" name="examPaper.ep_tscore"
				value="<s:property value="examPaper.ep_tscore"/>" id="ep_score"
				size="6" />
		</td>
	</tr>
	<!--
	<tr>
		<td align="center" >
			出题方式
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				普通显示
			</label>
			<input type="radio" name="examPaper.showmod" value="0"
				<s:if test="!examPaper.showmod">checked="checked"</s:if> />
			&nbsp;&nbsp;
			<label>
				逐题显示
			</label>
			<input type="radio" name="examPaper.showmod" value="1"
				<s:if test="examPaper.showmod">checked="checked"</s:if> />
		</td>
	</tr>
	-->
	<tr>
		<td height="20" align="center">
			&nbsp;
		</td>
		<td bgcolor="#FFFFFF" colspan="3" style="padding-left: 8px">
			<input id="ep_id" type="hidden"
				value="<s:property value="examPaper.id"/>" name="examPaper.id" />
			<input type="button" name="button2" onclick="alterepbaseinfo();"
				id="button2" class=textbg4 value="保存" />
			<input type="button" name="button2" onclick="viewepbaseinfo();"
				id="button2" class=textbg4 value="取消" />
		</td>
	</tr>
</table>