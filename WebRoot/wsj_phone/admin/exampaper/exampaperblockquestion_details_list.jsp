<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<s:if test="epBlock.questions.size!=0">
	<table width="900" align="center" cellspacing="1">
		<tr>
			<td align="center" >
				编号
			</td>
			<td align="center" >
				题干
			</td> 
			<td align="center" >
				所属题库
			</td>
			<td align="center" width="40" >
				难度
			</td>
			<td align="center" width="40" >
				分值
			</td> 
			<!--<td align="center" >
				&nbsp;
			</td>
			<td align="center" width="40" >
				&nbsp;
			</td>
		--></tr>
		<s:iterator value="epBlock.questions" id="epb1" status="epb">
			<tr>
				<td align="center" >
					<s:property value="sortid" />
				</td>
				<td width="400px" align="center" >
					<a name="tdTitle" title="<s:property value="title" />"> <s:property
							value="title" /> </a>
				</td>
				<td align="center" >
					<s:property value="qlib.name" />
				</td>
				<td align="center" >
					<s:property value="qlevel" />级
				</td>
				<td align="center" >
					<s:property value="score" />
				</td> 
				 <!--<td align="center" >
					<s:if test="sortid!=1">
						<a
							onclick="upexampaperblockquestions(<s:property value="epBlock.id"/>,<s:property value="id"/>);return false;"
							href="#">上移 </a>
					</s:if>
				</td>
				<td align="center" >
					<s:if test="#epb1.sortid!=(epBlock.questions.size )">
						<a
							onclick="downexampaperblockquestions(<s:property value="epBlock.id"/>,<s:property value="id"/>);return false;"
							href="#">下移 </a>
					</s:if>
				</td>
			--></tr>
			<s:if test="qtype==8">
				<tr>
					<td align="center" bgcolor="#ECEDEB" colspan="6">
						评分规则 速度分： <s:property value="rules[0]" />
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
			<s:if test="qtype==9">
				<tr>
					<td align="center" bgcolor="#ECEDEB" colspan="6">
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
			<s:if test="qtype==10">
				<tr>
					<td align="center" bgcolor="#ECEDEB" colspan="6">
						评分规则 搜索关键字分：
						<s:property value="rules[0]" />
						分 ，搜索结果分：
						<s:property value="rules[1]" />
						分
					</td>
				</tr>
			</s:if>
			 --%>
		</s:iterator>
	</table>
	<script type="text/javascript">
			function titleLimit(){
				var obj = document.getElementsByName("tdTitle"
							);
				for(var i=0 ;i
							<obj.length;++i){
					hiddenTitle(i);
				}
			}
			function showTitle(i){
				var obj = document.getElementsByName("tdTitle");
				for(var i = 0 ;i <obj.length;++i){
					if(i==j){
					obj[i].innerHTML = obj[i].title; 
					}
				}
			}
			function hiddenTitle(j){
				var obj = document.getElementsByName("tdTitle");
				for(var i = 0 ;i <obj.length;++i){
					if(i==j){
						if(obj[i].title.length>30) 
							obj[i].innerHTML = obj[i].title.substring(0,30)+"... " ;
					}
				} 
			}
			titleLimit();
		</script>
</s:if>
<s:else>
	没有大题，请添加。 
</s:else>
