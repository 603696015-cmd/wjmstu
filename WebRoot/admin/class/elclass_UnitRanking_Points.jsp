<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<TITLE>单位积分排名</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script></HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="单位积分排名" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考核考试列表</span>
			</li>-->
		</ul>
		<!-- 内容 -->
	<div style="margin-top: 20px; text-align: center;"> 
<table width="99%" align="center" cellspacing="1" cellpadding="1">
					<caption>&nbsp;
					</caption>
					<tr>
						<th width="54" height="30" align="center" bgcolor="#ECEDEB"><strong>排名</strong></th> 
						<th width="174" align="center" bgcolor="#ECEDEB"> 单位名称</th>
						<th width="98" height="30" align="center" bgcolor="#ECEDEB"><strong>通过率</strong></th>
						<th height="30" colspan="2" align="center" bgcolor="#ECEDEB"><strong>基础综合得分</strong></th>
						<th colspan="2" align="center" bgcolor="#ECEDEB">学历层次得分</th>
						<th height="30" colspan="2" align="center" bgcolor="#ECEDEB">职称层次得分</th>
						<th width="46" height="30" align="center" bgcolor="#ECEDEB"><strong>总分</strong></th>
						<th width="160" align="center" bgcolor="#ECEDEB">加分</th>
						<th width="71" height="30" align="center" bgcolor="#ECEDEB"><strong>最终得分</strong></th>
					</tr> 
					<tbody> 
					  <s:iterator value="unitRanks" status="ur"> 
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="ranking"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unit.name"/></td> 
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="passing"/>%</td>
					    <td width="98" height="30" align="center" bgcolor="#ECEDEB">
					    	<s:if test="passing != 100.0"><!-- 通过率未达到100% 得分显示为0-->
					    	0
					    	</s:if><s:else>
					    		
					    		<fmt:formatNumber value='${basedScore}'   pattern="0.0" type="number"/> 
					    	</s:else>
					    </td>
					    <td width="54" align="center" bgcolor="#ECEDEB"> 
							<a href="elclass_record_rankinglist.action?elclass.id=<s:property value="elclass.id"/>&deptid=<s:property value="unit.id"/>" class="textbg4">查 看</a>
					   	</td>
					    <td width="98" height="30" align="center" bgcolor="#ECEDEB">
					    	<s:if test="passing != 100.0"><!-- 通过率未达到100% 得分显示为0-->
					   		0
					    	</s:if><s:else>
					    	
					    	<fmt:formatNumber value='${DegreeScore}'  pattern="0.0" type="number"/>
					    </s:else> 
					    </td>
					    <td width="54" align="center" bgcolor="#ECEDEB"> 
					    
							<a href="elclass_UnitRanking_DegreeScoreInit.action?unitRank.elclass.id=<s:property value="elclass.id"/>&unitRank.unit.id=<s:property value="unit.id"/>" class="textbg4">查 看</a>
						</td>
					    <td width="98" height="30" align="center" bgcolor="#ECEDEB">
					    	
					    	
					    	<s:if test="passing != 100.0"><!-- 通过率未达到100% 得分显示为0-->
					    	0
					    	</s:if><s:else>
					    	
					    	<fmt:formatNumber value='${TitleScore}'  pattern="0.0" type="number"/>
					    	</s:else>
					    </td>
					    <td width="54" align="center" bgcolor="#ECEDEB"> 
							<a href="elclass_UnitRanking_TitleScoreInit.action?unitRank.elclass.id=<s:property value="elclass.id"/>&unitRank.unit.id=<s:property value="unit.id"/>" class="textbg4">查 看</a>
						</td>
					    <td height="30" align="center" bgcolor="#ECEDEB">
					    <fmt:formatNumber value='${TotalScore}'  pattern="0.0" type="number"/>
					    
					    <td height="30" align="center" bgcolor="#ECEDEB"> 
						<input type="text" name="unitRank.AddCent" id="addsCent_<s:property value="#ur.index+1" />" value="<s:property value="AddCent" />"> 
						<input type="button" value="确定" onclick="alterAddCent(<s:property value="#ur.index+1" />,<s:property value="elclass.id" />,<s:property value="unit.id" />)" />
					    <s:property value="AddCent"/>
					    </td>
					    <td height="30" align="center" bgcolor="#ECEDEB">
					     <fmt:formatNumber value='${FinalScore}'  pattern="0.0" type="number"/>
					    </td>
				      </tr>
					  </s:iterator>
					</tbody>
			  </table>    
	</div>  
		<form action="elclass_UnitRanking_PointsInit.action" method="post" name="eup">
			<s:hidden name="unitRank.elclass.id" id = "uei"/>
			<s:hidden name="unitRank.unit.id" id ="uui"/> 
			<s:hidden name="unitRank.AddCent" id ="ua" /> 
			<s:hidden name="isAlter" id ="isAlter" /> 
		</form>
		<script type="text/javascript">  
			function alterAddCent(i,classid,depid){    
				var AddCent = document.getElementById('addsCent_'+i).value;  
				document.getElementById("ua").value = AddCent;
				document.getElementById("uui").value = depid;
				document.getElementById("uei").value = classid; 
				document.getElementById("isAlter").value = 1; 
				eup.submit();
				alert("修改成功!");
			} 		
		</script>
		<!-- 内容 -->
	</BODY>
</HTML>
