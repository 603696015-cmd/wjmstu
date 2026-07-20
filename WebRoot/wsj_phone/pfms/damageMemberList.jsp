<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>定损员管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="<%=path %>/js/calendar.js"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("exprot").value=false;
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
			}
			
			function toexcel(exprot) { 
				document.getElementById("exprot").value=exprot;
				document.getElementById("damageMembersQuery").submit();
			}
			
			function delDamageMember(id){
				var assign = document.getElementById("damageMembersQuery");
				if(window.confirm("确定删除？")){
					assign.action="deleteDamageMember.action?id="+id;
				    assign.submit();
				}
			}
		</script>
		
		<script type="text/javascript">
			function select_All(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			
			function select_Fan(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			
			function select_Bux(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			
			function del(){
			  if(window.confirm("确定删除？")){
			     var checkObj = document.getElementsByName("id");
				    var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				   var damageMemberIds = document.getElementById("damageMemberIds");
			       damageMemberIds.value=billIDs;
				   assign.submit();
				}
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="定损员列表页" /></div>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top">
							<form action="damageMemberList.action" method="post" name="damageMembersQuery" id="damageMembersQuery">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<s:hidden name="exprot" id="exprot" />
								<div>
								<center>
									发布时间&nbsp;
									从<INPUT class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onclick="setday(this)">
	       						 	到<INPUT class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onclick="setday(this)">
      								<br>
									姓名&nbsp;<input type="text" name="damageMember.name" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
									性别&nbsp;
									<select name="damageMember.sex"> 
										<OPTION value="" selected>请选择性别</OPTION>
								        <OPTION value=男>男</OPTION>
								        <OPTION value=女>女</OPTION>
									</select>
									
									身份证&nbsp;<input type="text" name="damageMember.personId" />&nbsp;&nbsp;&nbsp;
									<input type="submit"  value="搜索" />
								</center>
								</div> 
							</form>
						<s:if test="damageMemberList.size==0"><h3 align="center" style="margin-top:10px;">没有搜到定损员</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="20"></th>
									<th width="100" height="30" align="center" >
										姓名									
									</th>
									<th width="100" height="30" align="center" >
										性别								 
									</th>
									<th width="90" height="30" align="center" >
										身份证号									
									</th>
									<th width="80" height="30" align="center" >
										工作单位									
									</th>
									<th width="30" height="30" align="center" >
										籍贯									
									</th>
									<th width="60" height="30" align="center" >
										发布时间									
									</th>
									<th width="70" height="30" align="center" colspan="1">
										操作								
									</th>
									
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="damageMemberList">
									<tr>
										<td width="20" height="20" align="center">
											<input type="checkbox" value="<s:property value="id"/>"
												name="id">
										</td>
										<td height="30" style="padding-left:8px;color:blue;" align="center">
											<s:property value="name" />
									    </td>
										<td height="30" align="center" >
												<s:property value="sex" />
										</td>
										<td height="30" align="center" >
											<s:property value="personId" />
										</td>
										<td height="30" align="center" >
											<s:property value="workCompany" />
										</td>
										<td width="70" height="30" align="center" >
											<s:property value="hometown" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:date name="fabushijian" format="yyyy-MM-dd hh:mm:ss"/>
									    </td>
									  <td align="center" valign="middle" height="30" width="70"><p><a href="showDamageMemberView.action?id=${id }" class="textbg4">修改</a></p></td>
    								  <!-- <td align="center" valign="middle"><p><a href="javascript:delDamageMember(${id })">删除</a></p></td> -->
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<br>
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<a href="javascript:del()" />删除</a>
			<s:form action="deleteDamageMember.action" method="post" name="assign">
				<s:hidden name="damageMemberIds" id="damageMemberIds" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</s:form>
			
			<wysLib:page></wysLib:page>
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="200">
						&nbsp;
					</td>
					<td width="400">
						<a href="<%=path %>/pfms/damageMember_import.jsp" class="textbg" />批量导入</a>
						<a href="javascript:toexcel(true);"  class="textbg"/>批量导出</a>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	
	</body>
</HTML>
										   
