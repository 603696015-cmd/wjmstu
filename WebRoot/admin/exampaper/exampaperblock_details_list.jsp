<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<s:set name="epbsize" value="examPaper.epBlocks.size"></s:set>
<table width="900" align="center" cellpadding="1" cellspacing="1">
	<tr>
		<th align="center" >
			编号
		</th>

		<th align="center" >
			标题
		</th>
		<th align="center" >
			题型1
		</th>
		<th align="center" >
			题量/实际题量1
		</th>
		<th align="center" >
			每题分数
		</th>
		<!--
		<th align="center" >
			实际分数
		</th>
		-->
		<th align="center" width="60" >
			出题方式
		</th>
		<!--<th align="center" >
			&nbsp;
		</th>
		<th align="center" >
			&nbsp;
		</th>
		-->
		<th align="center" width="215" >&nbsp;
			
		</th>
	</tr>
	<s:iterator value="examPaper.epBlocks" id="epid">
		<tr>
			 <td align="center" >
				<s:property value="sortid" />
			</td>
			<td align="center" >
				<s:property value="title" />
			</td>
			<td align="center" >
				<s:property value="typeName" />
			</td>
			<td align="center" >
				<s:property value="questionamount" />
				/
				<s:property value="realqamount" />
			</td>
			<td align="center" >
				<s:property value="eachscore" />
			</td>
			<!--<td align="center" >
				<s:property value="realscore" />
			</td>
			-->
			<td align="center" >
				<s:if test="random==0">
						手工
				</s:if>
				<s:else>
						随机
				</s:else>
			</td>
			<!--<td align="center" >
				<s:if test="sortid!=1">
					<a
						href="exampaperblock_upsort.action?epBlock.sortid=<s:property value="sortid"/>&examPaper.id=<s:property value="examPaper.id"/>">上移</a>
				</s:if>
			</td>
			<td align="center" >
				<s:if test="#epid.sortid!=#epbsize">
					<a
						href="exampaperblock_downsort.action?epBlock.sortid=<s:property value="sortid"/>&examPaper.id=<s:property value="examPaper.id"/>">下移</a>
				</s:if>
			</td>
			-->
			<td align="center" >
				<a href="#"
					onclick="listexampaperblockquestions_details(<s:property value="id"/>);return false;" class=textbg6>查看小题</a >
			</td>
		</tr>
		<s:if test="type==8&&random==1">
			<tr>
				<td align="center" bgcolor="#ECEDEB" colspan="7">
						评分规则：  速度分： <s:property value="rules[0]" />
						分 ，准确分：
						<s:property value="rules[1]" />
						分 ，时&nbsp;&nbsp;&nbsp;&nbsp;长：
						<s:property value="rules[2]" />
						分钟 <br/>
						<s:iterator value="dazirule" status="dazist">
							年龄段<s:property value="#dazist.index+1"/>：
							<s:property value="dazirule[#dazist.index][0]"/>到<s:property value="dazirule[#dazist.index][1]"/>  
							及格速度：<s:property value="dazirule[#dazist.index][2]"/> 
							优秀速度：<s:property value="dazirule[#dazist.index][3]"/>
							满分速度：<s:property value="dazirule[#dazist.index][4]"/> 
							 <br/>
						</s:iterator>
				</td>
			</tr>
		</s:if>
		<s:if test="type==9&&random==1">
			<tr>
				<td align="center" bgcolor="#ECEDEB" colspan="7">
					评分规则 发 给：
					<s:property value="rules[0]" />
					分 ， 抄 送：
					<s:property value="rules[1]" />
					分 ， 密 送：
					<s:property value="rules[2]" />
					分 ， 主 题：
					<s:property value="rules[3]" />
					分 ， 附 件：
					<s:property value="rules[4]" />
					分 ， 正 文：
					<s:property value="rules[5]" />
					分
				</td>
			</tr>
		</s:if>
		<%-- 
		<s:if test="type==10&&random==1">
			<tr>
				<td align="center" bgcolor="#ECEDEB" colspan="7">
					评分规则 搜索关键字分：
					<s:property value="rules[0]" />
					分 ，搜索结果分：
					<s:property value="rules[1]" />
					分
				</td>
			</tr>
		</s:if>
		 --%>
		<s:if test="type==12&&random==0">
			<tr>
				<td align="center" bgcolor="#ECEDEB" colspan="7">
					评分规则 搜索关键字分： 共 有：
					<s:property value="rules[0]" />
					题, 选 做：
					<s:property value="rules[1]" />
					题, 每 题：
					<s:property value="rules[2]" />
					分
				</td>
			</tr>
		</s:if>
		<tr>
			<td id="exampaperblockquestion_list_<s:property value="id"/>"
				colspan="9" align="center" >
			</td>
		</tr>
	</s:iterator>
</table>
