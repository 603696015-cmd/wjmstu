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
		<TITLE>课程类别管理</TITLE>
		<base target="_top" href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function doSub(){
				var epids=document.getElementsByName("id");
				var resultValue="";
				for(var i=0;i<epids.length;i++){
					if(epids[i].checked==true){
						resultValue=epids[i].value;
						break;
					}
				}
				if(resultValue==""){
					//alert("请选择1个练习！");
					//return false;
					window.returnValue="";
					window.close();
				}else{
					//alert(resultValue);
					window.returnValue=resultValue;
					window.close();
				}
			}
			function page(i){
				document.getElementById("pageNow").value=i;
				erform.submit();
			}
		</script>
	</HEAD>
	<body>
		<form action="examprac_simple_list.action" method="post" name="erform">
			<input type="hidden" name="pN" id="pageNow"/>
			<input type="hidden" name="pS" id="pS"/>
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习列表" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<th></th>
						<th width="150" height="30" align="center" >
							练习名称						</th>
						<th width="120" height="30" align="center" >
							创建者						</th>
						<%-- 
						<th width="200" height="30" align="center" >
							创建者所属部门						</th>
						<th width="120" height="30" align="center" >
							开始时间						</th>
						<th width="120" height="30" align="center" >
							结束时间						</th>
						<th width="80" height="30" align="center" >
							考生人数				</th>
						 --%>
						<th width="100" height="30" align="center" >
							状态						</th>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="exampracs">
						<tr>
							<td height="20" width="50" align="center">
								<input type="radio" value="<s:property value='id'/>_<s:property value='title'/>" name="id">
							</td>
							<td height="30" align="center" >
								<s:property value="title" />
							</td>
							<td height="30" align="center" >
								<s:property value="user.username" />
							</td>
							<%-- 
							<td height="30" align="center" >
								<s:property value="user.danwei" /><!-- 此处借用 实际值为  部门名称 -->
							</td>
							<td height="30" align="center" >
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />						  </td>
							<td height="30" align="center" >
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />						  </td>
							<td height="30" align="center" >
								<s:property value="usersize" />						  </td>
							 --%>
							<td height="30" align="center" >
								<font color="<s:if test="valid==1">red</s:if>">
								<s:property value="validName" />
							  </font>
						</tr>
					</s:iterator></tbody>
			  </table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
					<div><a href="javascript:doSub();" class="textbg4">确定</a></div>
				</DIV>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
