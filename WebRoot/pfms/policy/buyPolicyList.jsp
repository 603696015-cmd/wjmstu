<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>保险产品管理</TITLE>
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
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="产品列表页" /></div>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
						<wysLib:productTypeTree href="buyPolicyListInit.action?ptype.id="
							rootAble="true"></wysLib:productTypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
							<form action="buyPolicyListInit.action" method="post" name="assignProduct" id="assignProduct">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<div>
								<center>
									发布时间&nbsp;
									从<INPUT class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onclick="setday(this)">
	       						 	到<INPUT class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onclick="setday(this)">
      								<br>
      								<!-- 审核状态&nbsp;<select name="baoxianProduct.shenhezhuangtai"  id="shenhezhuangtai" style="WIDTH: 110px" 
      									onclick="baoxianProduct.shenhezhuangtai.value=this.options[this.selectedIndex].value">
      									<option value="">
											==审核状态==
										</option>
										<s:iterator value="shenhezhuangtaiList">
										<option value="<s:property value="id"/>">
											<s:property value="shenhezhuangtai"/> 
										</option>
										</s:iterator>
									</select> -->
      								产品名称&nbsp;<input type="text" name="baoxianProduct.name" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							<input type="submit"  value="搜索" />
								</center>
								</div> 
							</form>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="80" height="30" align="center" >
										产品名称									
									</th>
									<th width="80" height="30" align="center" >
										所属栏目								 
									</th>
									<th width="80" height="30" align="center" >
										市场价									
									</th>
									<th width="80" height="30" align="center" >
										会员价									
									</th>
									<th width="80" height="30" align="center" >
										发布时间									
									</th>
									<th width="80" height="30" align="center" >
										审核状态									
									</th>
									<th width="100" height="30" align="center"  colspan="3">
										操作									
									</th>
									
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="baoxianProductList">
									<tr>
										<td height="30" style="padding-left:8px;color:blue;" align="center">
											<s:property value="name" />
									    </td>
										<td height="30" align="center" >
												<s:property value="lanmu.lanmu" />
										</td>
										<td height="30" align="center" >
											<s:property value="shichangjia" />
										</td>
										<td height="30" align="center" >
											<s:property value="huiyuanjia" />
										</td>
									    <td width="70" height="30" align="center" >
											<s:property value="fabushijian" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shenhezhuangtai_entity.shenhezhuangtai" />
									    </td>
									    <td align="center" valign="middle">
									    <p><a href="buyPolicyView.action?baoxianProduct.id=${id }">查看与投保</a></p></td> 
									</tr>
								</s:iterator></tbody>
						  </table>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
										   








