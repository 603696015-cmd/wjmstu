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
			题型
		</th>
		<th align="center" >
			题量/实际题量123
		</th>
		<th align="center" >
			每题分数
		</th>
		<th align="center" >
			设置的总分
		</th>
		<th align="center" >
			实际总分
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
	<s:set id="questionScoreSum" value="examPaper.ep_questionscore" />
	<s:set id="realqScoreSum" value="examPaper.ep_realscore" />
	<s:iterator value="examPaper.epBlocks" id="epid">
		<tr>
			 <td align="center" >
				<strong><s:property value="sortid" /></strong>
			</td>
			<td align="center" >
				<strong><s:property value="title" /></strong>
			</td>
			<td align="center" >
				<strong><s:property value="typeName" /></strong>
			</td>
			<td align="center" >
				<s:property value="questionamount" />
				/
				<s:property value="realqamount" />
			</td>
			<td align="center" >
				<s:property value="eachscore" />
			</td>
			<td align="center" >
				<s:property value="questionscoresum" />
			</td>
			<td align="center" >
				<s:property value="realqscoresum" />
			</td>
			<!--<td align="center" >
				<s:property value="realscore" />
			</td>
			-->
			<td align="center" >
				<strong><s:if test="random==0">
						手工
				</s:if>
				<s:else>
						随机
				</s:else></strong>
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
			<td align="left" width="250">
				<div id="ccc_<s:property value="id"/>" style="float:left;display:none;">
				<a href="javascript:;" onclick="showorhidden('exampaperblockquestion_list_<s:property value="id" />');return false;" class=textbg6>查看小题</a>
				</div>
				<div id="ddd_<s:property value="id"/>" style="float:left;margin-left:5px;">
				<a id="disquestion_<s:property value="id"/>" href="#" onclick="listexampaperblockquestions(<s:property value="id"/>);return false;" class=textbg6><s:if test="random==0">
						查看小题
				</s:if>
				<s:else>
						查看规则
				</s:else></a>
				</div>
				<a href="#"
					onclick="alterepblockinit(<s:property value="id" />);return false;" class=textbg4>
					编 辑</a>
				<a href="#"
					onclick="deleteepblock(<s:property value="id" />);return false;" class=textbg4>
					删 除</a> 
				<s:if test="random==0">
					<a href="#"
						onclick="addexampaperblockquestions(<s:property value="id" />);return false;" class=textbg6>添加试题</a>
				</s:if>
				<s:else>
					<a href="#"
						onclick="addexampaperblockquestions(<s:property value="id" />);return false;" class=textbg6>设置规则</a>
				</s:else>
				<%-- 
				<a href="#" onclick="showorhidden('exampaperblockquestion_list_<s:property value="id" />');return false;" class=textbg4>收起</a>
				 --%>
			</td>
			<!-- 
			<td align="center">
				<s:if test="random==0">
					<s:if test="sortid!=1">
						<a class=textbg4
							href="exampaperblock_upsort.action?examPaper.id=<s:property value="examPaper.id"/>&epBlock.sortid=<s:property value="sortid"/>">上移
						</a>
					</s:if>
				</s:if>
			</td>
			<td align="center">
				<s:if test="random==0">
					<s:if test="sortid!=#epbsize">
						<a class=textbg4
							href="exampaperblock_downsort.action?examPaper.id=<s:property value="examPaper.id"/>&epBlock.sortid=<s:property value="sortid"/>">下移
						</a>
					</s:if>
				</s:if>
			</td>
			 -->
		</tr>
		<tr>
				<!-- 设置规则 -->
			<td id="exampaperblockquestion_list_<s:property value="id"/>" style="display:none;padding:0px;"
				colspan="9" align="left" >
			</td>
		</tr>
		<s:if test="type==8&&random==1">
			<tr>
				<td align="left" bgcolor="#ECEDEB" colspan="9">
					<a
						onclick="exampaperblock_rulealterinit(<s:property value="id"/>);return false;"
						href="#" class=textbg4>修 改</a>评分规则： 范文最少字数： <s:property value="fwsize" />
						<!-- 分 ，准确分：
						<s:property value="rules[1]" />
						分 --> ，时&nbsp;&nbsp;&nbsp;&nbsp;长：
						<s:property value="rules[2]" />
						分钟 <br/>
						<s:iterator value="dazirule" status="dazist">
							<strong>年龄段:</strong> <s:property value="#dazist.index+1"/>：
							<s:property value="dazirule[#dazist.index][0]"/>到<s:property value="dazirule[#dazist.index][1]"/>  及格速度：<s:property value="dazirule[#dazist.index][2]"/> 优秀速度：<s:property value="dazirule[#dazist.index][3]"/> 满分速度：<s:property value="dazirule[#dazist.index][4]"/>
							 <br/>
						</s:iterator>
				</td>
			</tr>
		</s:if>
		<s:if test="type==9&&random==1">
			<tr>
				<td align="left" bgcolor="#ECEDEB" colspan="9">
					<a
						onclick="exampaperblock_rulealterinit(<s:property value="id"/>);return false;"
						href="#" class=textbg4>修 改</a>评分规则 发 给：
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
				<td align="center" bgcolor="#ECEDEB" colspan="9">
					<a
						onclick="exampaperblock_rulealterinit(<s:property value="id"/>);return false;"
						href="#" class=textbg4>修 改</a>评分规则 搜索关键字分：
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
				<td align="left" bgcolor="#ECEDEB" colspan="9">
					<!-- <a
						onclick="exampaperblock_rulealterinit(<s:property value="id"/>);return false;"
						href="#" class=textbg4>修 改</a>评分规则 搜索关键字分： 共 有：
					<s:property value="rules[0]" />
					题, --> 选 做：
					<s:property value="rules[1]" />
					题, 每 题：
					<s:property value="rules[2]" />
					分(如需修改，请修改大题)
				</td>
			</tr>
		</s:if>
	</s:iterator>
</table>
<script type="text/javascript">
<!--
	var questionScoreSum=<s:property value="#questionScoreSum" />;
	var realqScoreSum=<s:property value="#realqScoreSum" />;
//	alert("当前试卷总分"+ep_tscore+"分\n\r各大题设置分值总和"+questionScoreSum+"分\n\r实际分值总和"+realqScoreSum+"分");
//-->
</script>