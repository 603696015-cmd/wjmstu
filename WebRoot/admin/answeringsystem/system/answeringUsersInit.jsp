<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base target="_self">
		<TITLE>知识人员授权</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
			function init(){
				document.getElementById("depId").name="department.id";
			}
			function search()
			{
				//alert("hello");
				
				
				
				searchLog_form.submit();
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function select_All(){
				var cks= document.getElementsByName("check");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			
			function select_Fan(){
				var cks= document.getElementsByName("check");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			
			function select_Bux(){
				var cks= document.getElementsByName("check");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			
			function page(i){ 
			 		document.getElementById("pageNow").value=i;
			 		searchLog_form.submit();
			 	}
				
				function opensearchdiv()
			{
				//alert("hello");
				if((document.getElementById("searchdiv").style.display)=="")
					document.getElementById("searchdiv").style.display="none";
				else document.getElementById("searchdiv").style.display="";
			}
			
			
			 function setRv()
		{
			var code_Values = document.getElementsByName("check");
			var str = "";
			for(i = 0;i < code_Values.length;i++)
			{ 
				if(code_Values[i].checked ) 
				{ 
					 str +=code_Values[i].value + ",";
				} 
			}
			if(str==""){
			  alert("请至少选择一个复选框！");
			  return ;
		    }
		    if(str.lastIndexOf(",")!=-1){
		    	str = str.substring(0,str.lastIndexOf(","));
		    }
			window.returnValue = str;
			window.close();
	 	}
	 	
	 	function do_submit(){
	 		searchLog_form.submit();
	 	}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height:30px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" style="height:20px;">
  			<tr>
    			<td>
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="日志列表" />
				</div>
			</li>
		
		</ul>
				</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 10px; text-align: center;">
			<s:form action="competenceByUserInit.action" method="post" name="searchLog_form"
				theme="simple">
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				
				<table width="100%">
					<tr>
						<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;">  
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
								String url ="competenceByUserInit.action?&department.id=";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="<%=url%>"
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td width="5px;" valign="middle" bgcolor="#FAFCFC" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
						
				<table width="95%"> 
					<tr>
					
					   <td>姓名<input  name="elUser.realname" /></td>
					   <td>
					   		角色
							<SELECT  style="WIDTH: 100px" name="elUser.role.id" 
						      onchange="this.value=this.options[this.selectedIndex].value;">
						        <OPTION value="" selected>选择角色</OPTION>
						        <s:iterator value="roles">
						        	<option value="<s:property value="id"/>">
										<s:property value="name"/> 
									</option>
						        </s:iterator>
						    </SELECT>			           
				       </td>
				       <td>
				       		职务
							<SELECT  style="WIDTH: 100px" name="elUser.zhiwu" 
						      onchange="this.value=this.options[this.selectedIndex].value;">
						        <OPTION value="" selected>选择职务</OPTION>
						        <s:iterator value="zhiwus">
						        	<option value="<s:property value="id"/>">
										<s:property value="basevalue"/> 
									</option>
						        </s:iterator>
						    </SELECT>
				       </td>
				       <td>
				       		工种
							<SELECT  style="WIDTH: 100px" name="elUser.jingzhong" 
						      onchange="this.value=this.options[this.selectedIndex].value;">
						        <OPTION value="" selected>选择工种</OPTION>
						        <s:iterator value="jingzhongs">
						        	<option value="<s:property value="id"/>">
										<s:property value="basevalue"/> 
									</option>
						        </s:iterator>
						    </SELECT>
				       </td>
					   <td colspan="2">
			           	 	<input  type="button" onclick="do_submit();" value="搜索" >
			           </td>
					</tr>
				</table>
				
				<table width="95%" align="center" cellpadding="1" cellspacing="1">
					
					<tr>
						<th>
						</th>
						<th>
						姓名
						</th>
						<th>
						部门
						</th>
						<th>
						角色
						</th>
					</tr>
					<s:iterator value="elUsers" status="status">
					<tr>
						<td align="center">
						<input type="checkbox" name="check" value="<s:property value='id'/>==<s:property value='realname'/>" />
						</td>
						<td align="center">
						<s:property value="realname"/>
						</td>	
						<td align="center">
						<s:property value="department.name"/>
						</td>
						<td align="center">
						<s:property value="role.name"/>
						</td>
						</tr>
					</s:iterator>
				</table>
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
			
			
			<div style="margin-top: 30px; text-align: center;">
			<input name="submit" type="button" value="确认添加" class=textbg6  onclick="setRv();"  />
		</div>
			<br />
			
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>