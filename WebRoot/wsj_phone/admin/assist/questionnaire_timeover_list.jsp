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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base target="_top" href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
			.tdStyle{
				color:red;
			}
		</style>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="调查问卷时间重叠查看" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的培训班</span>
			</li>-->
		</ul>
		<s:form action="questionnaire_timeover_list.action" name="myclist" theme="simple">
			<s:hidden name="examRoom.id" />
			<s:hidden name="PageStatus" />
			<s:hidden name="pN" id = "pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
		</s:form>
		<div style="text-align:center;margin-top:20px;">
		<table width="100%" align="center" cellpadding="1" cellspacing="1" >
			<tr>
				<th width="200" align="center" >
					问卷名称				</th>
				<th width="60" align="center" >
					创建者				</th>
				<th align="center" >
					所属部门				</th>
				<th width="80" align="center" >
					学员人数				</th>
				<th width="120" align="center" >
					开始时间				</th>
				<th width="120" align="center" >
					结束时间				</th>
				<th width="70" align="center" >
					状态				</th>
				<s:if test="PageStatus==1">
					<th width="60" align="center" >详情</th>
				</s:if>
			</tr>
			<tr>
				<td align="center" bgcolor="#FFFFFF" class="tdStyle">
					<s:property value="examRoom.title"/>
				</td>
				<td align="center" bgcolor="#FFFFFF" class="tdStyle">
					<s:property value="examRoom.creater.realname" />
				</td>
				<td align="center" bgcolor="#FFFFFF" class="tdStyle"> 
					<s:property value="examRoom.creater.department.name" />
				</td>
				<td align="center" bgcolor="#FFFFFF" class="tdStyle">
					<s:property value="examRoom.usersize"/>	  
				</td>
				<td align="center" bgcolor="#FFFFFF" class="tdStyle">
					<s:date format="yyyy-MM-dd hh:mm:ss" name="examRoom.begintime"/> 
				<td align="center" bgcolor="#FFFFFF" class="tdStyle">
					<s:date format="yyyy-MM-dd hh:mm:ss" name="examRoom.endtime"/>
			    </td>
				<td align="center" bgcolor="#FFFFFF" class="tdStyle">
					<s:property value="examRoom.validName"/>
				</td>
				<s:if test="PageStatus==1">
				<td align="center" bgcolor="#FFFFFF" class="tdStyle"> 
					<a target="_blank" href="examroom_sh_view.action?examRoom.id=<s:property value="examRoom.id"/>" class=textbg4>查看</a>	
				</td>
				</s:if>
			</tr>
			<s:if test="examRooms.size==0">当前调查问卷和其他已开通的调查问卷没有时间交叉。</s:if>
			<s:else>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="examRooms">
				<tr>
					<td align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<s:property value="title"/>  
					</td>
					<td align="center" >
						<s:property value="creater.realname" />
					</td>
					<td align="center" > 
						<s:property value="creater.department.name" />
					</td>
					<td align="center" >
						<s:property value="usersize"/>	  
					</td>
					<td align="center" >
						<s:date format="yyyy-MM-dd hh:mm:ss" name="begintime"/>
					</td>
					<td align="center" >
						<s:date format="yyyy-MM-dd hh:mm:ss" name="endtime"/>
				    </td>
					<td align="center" bgcolor="#FFFFFF" style="color:green;">
						<s:property value="validName"/>	  
					</td>
					<s:if test="PageStatus==1">
					<td align="center" > 
						<a target="_blank" href="examroom_sh_view.action?examRoom.id=<s:property value="id"/>" class=textbg4>查看</a>	
					</td>
					</s:if>
				</tr>
				</s:iterator>
			</tbody>
			</s:else>
	</table> 
	</div>
			 
	<script>    
		function page(i) {
			document.getElementById("pageNow").value=i;
			myclist.submit();
		}
		function pageReturn(n){
			if(n==1){
				//通过
				window.returnValue="1";
			}else if(n==2){
				//不通过
				window.returnValue="2";
			}else{
				window.returnValue="3";
			}
			window.close();
		}
	</script>
		<div style="text-align:center;"><wysLib:page></wysLib:page></div>
		<div style="text-align:right;margin-right:50px;">
		<%-- 
			<a style="cursor:pointer;" href="javascript:pageReturn(1);" class="textbg6">确认通过</a>
			<a style="cursor:pointer;" href="javascript:pageReturn(2);" class="textbg6">不通过</a>
		 --%>
		 <a style="cursor:pointer;" href="javascript:pageReturn(1);" class="textbg4">确认</a>
			<a style="cursor:pointer;" href="javascript:pageReturn(3);" class="textbg4">取消</a>
		</div>
	
	</body>
</HTML>