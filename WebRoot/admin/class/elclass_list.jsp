<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的培训班</span>
			</li>-->
		</ul>  
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%">
			<tr>
			  <td valign="top" id="tree_list_td">  
					<wysLib:clTypeTree href="elclass_list.action?cltype.id=" rootAble="true" />
			  </td>
				<td valign="middle" width="5px;" style="padding: 0px" >
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
										onclick="changeTreeDisplay(this)" />
			  </td>
				<td valign="top">
				
				<s:form action="elclass_list" name="myclist" theme="simple">
				<s:hidden name="pN" id = "pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
					<!--培训班名称：<s:textfield name="elClass.name"></s:textfield> <s:submit value="搜索"></s:submit>-->
				
				</s:form>
			
			<s:if test="elclasses.size==0">没有符合条件的培训班</s:if>
			<s:else>
			<table width="100%" height="100%" align="center" cellpadding="1" cellspacing="1"
			>
			<tr>
				<th width="200" align="center" >
					培训班名称				</th>
				<th width="100" align="center" >
					类别				</th>
				<th width="60" align="center" >
					创建者				</th>
				<th width="120" align="center" >
					创建时间				</th>
				<th width="120" align="center" >
					开始时间				</th>
				<th width="120" align="center" >
					结束时间				</th>
				<th width="100" align="center" >
					审核状态				</th>
				<th width="80" align="center" >
					学员人数				</th>
				<th width="120" align="center" >&nbsp;</th> 
			</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
			<s:iterator value="elclasses">
			<tr>
				<td width="200" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
					<s:property value="name"/>  
				</td>
				<td width="100" align="center" >
					<s:property value="cltype.name"/>	 
				</td>
				<td width="60" align="center" >
					<s:property value="creater.realname" /> 
				</td>
				<td width="120" align="center" > 
					<s:date format="yyyy-MM-dd hh:mm:ss" name="createtime"/> 
				</td>
				<td width="120" align="center" >
					<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime"/> 
				<td width="120" align="center" >
					<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime"/>
			    </td>
				<td width="100" align="center" bgcolor="#FFFFFF" style="color:green;">
					<s:property value="statusName"/>	  
				</td>
				<td width="80" align="center" >
					<s:property value="classSize"/>	  
				</td>
				<td width="120" align="center" > 
				<s:if test="status == 0 || status == 2"> 
					<!-- <a href="elclass_view.action?elclass.id=<s:property value="id" />" class=textbg4>编辑</a>  -->
					<a href="elclass_alterInit.action?elclass.id=<s:property value="id" />" class=textbg4>编辑</a>	
				</s:if>	
				<s:else>
					<a href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=-2&PageStatusint=-2" class=textbg4>查看</a>	
				</s:else>
				</td>
			</tr>
			</s:iterator></tbody>
		</table> 
		</s:else></td>
			</tr>
	</table> 
			 
							<script>    
								function page(i) {
									document.getElementById("pageNow").value=i;
									myclist.submit();
								} 
							</script> 
							<wysLib:page></wysLib:page>
	</body>
</HTML>
