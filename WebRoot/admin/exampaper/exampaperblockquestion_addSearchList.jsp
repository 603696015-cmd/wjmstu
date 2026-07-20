<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<s:if test="epBlock.questionamount>=epBlock.realqamount">
	<font color="ff0000"> 该大题还有<s:property
			value="epBlock.questionamount-epBlock.realqamount" />道题需要添加</font>
</s:if>
<s:else>
			该大题已经没有试题需要添加！
</s:else>
<s:property value="elmessage" />
<s:if test="questions.size==0">没有符合条件的试题</s:if>
<s:else>
	<table width="900" align="center" cellspacing="1" cellpadding="1">
		<tr>
			<th height="30" align="center" >
				&nbsp;
			</th>
			<th height="30" align="center" >
				题干
			</th>
			<th height="30" align="center" >
				题目类型
			</th>
			<th height="30" align="center" >
				所属题库
			</th>
			<th height="30" align="center" >
				创建时间
			</th>
			<th height="30" align="center" >
				难度
			</th>
			<s:if test="question.qtype==8">
			<th height="30" align="center" >
				长度
			</th>
			</s:if>
			<th height="30" align="center" >
				
			</th>
		</tr>
		<s:iterator value="questions">
			<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					<s:if test="eqbHave">
						已经添加
					</s:if>
					<s:else>
						<!-- <input type="checkbox"
							onclick="addexampaperblockquestionsadd(<s:property value="epBlock.id" />,<s:property value="id"/>)"
							name="questions.isdd">
							 -->
						<input type="checkbox" value="<s:property value="id"/>"
							name="questions.id">
					</s:else>
				</td>
				<td height="30" align="center" >
					<a name="tdTitle" title="<s:property value="title" />"> <s:property
							value="title" /> </a>
				</td>
				<td height="30" align="center" >
					<s:property value="qtypeName" />
				</td>
				<td height="30" align="center" >
					<s:property value="qlib.name" />
				</td>
				<td height="30" align="center" >
					<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
				</td>
				<td height="30" align="center" >
					<s:property value="qlevel" />
					级
				</td>
				<s:if test="question.qtype==8">
					<td height="30" align="center" >
						<s:property value="fwsize" />
					</td>
				</s:if>
				<td height="30" align="center" >
					<a href="question_view_status.action?question.id=<s:property value="id" />" target="_blank" class=textbg4>预 览</a>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td align="center" colspan="8">
				<script type="text/javascript">
						function page(i){
							document.getElementById("epb_q_pn").value=i;
							addexampaperblockquestionslist(<s:property value="epBlock.id" />);
						}
						function titleLimit(){
							var obj = document.getElementsByName("tdTitle");
							for(var i = 0 ;i <obj.length;++i){
								hiddenTitle(i);
							}
						}
						function showTitle(i){
							var obj = document.getElementsByName("tdTitle");
							for(var i = 0 ;i <obj.length;++i){
								if(i==j){
								obj[i].innerHTML = obj[i].title; 
								// +"<a href=\"javascript:hiddenTitle("+i+")\">隐藏</a>" ;
								}
							}
						}
						function hiddenTitle(j){
							var obj = document.getElementsByName("tdTitle");
							for(var i = 0 ;i <obj.length;++i){
								if(i==j){
									if(obj[i].title.length>20) 
										obj[i].innerHTML = obj[i].title.substring(0,20)+"... " ;
								}
							} 
						}
						titleLimit();
				</script>
				<wysLib:page></wysLib:page>
				
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
				<input style="display: none;" type="checkbox" value="1"
					<s:if test="sublibs==1">checked='checked'</s:if> name="sublibs" />
				<s:hidden id="epb_q_pn" name="pN"></s:hidden>
				<s:hidden id="epb_q_ps" name="pS"></s:hidden>
				<s:hidden name="epBlock.id"></s:hidden>
				<s:hidden id="epb_q_lib" name="question.qlib.id"></s:hidden>
				<s:hidden id="epb_q_title" name="question.title"></s:hidden>
				<s:hidden id="epb_q_type" name="question.qtype"></s:hidden>
				<a onclick="select_All('questions.id') ;return false;" href="#" >全选</a>
				<a onclick="select_Fan('questions.id') ;return false;" href="#" >反选</a>
				<a onclick="select_Bux('questions.id') ;return false;" href="#" >全不选</a>
				<input type="button" value="添加到该大题中" class="textbg6" style="width:110px;"
					onclick="addexampaperblockquestionsadds(<s:property value="epBlock.id" />)">
				<input type="button" value="重新搜索" class="textbg6" style="width:80px;"
					onclick="addexampaperblockquestions(<s:property value="epBlock.id" />)">
			</td>
		</tr>
	</table>
</s:else>
