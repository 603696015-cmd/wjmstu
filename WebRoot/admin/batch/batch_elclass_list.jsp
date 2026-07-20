<%@ page language="java" pageEncoding="UTF-8"  %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<TITLE>五矿发展员工职业发展系统--管理端</TITLE>
<link rel="stylesheet" type="text/css" href="css/system.css" />
<link rel="stylesheet" type="text/css" href="css/manage.css" />
<script type="text/javascript" src="js/menu.js"></script>
<script type="text/javascript">
function addBatch(){
	window.open ('batch_elclass_selectList.action?batchId=${batchId}','选择培训班','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
}
</script>
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
	<!--<li><span style="font-weight: bold;">包含的培训班</span></li>
	<li class="sep"></li>-->
	<s:if test="batchId != 0">
		<li>
			<a style="cursor: hand"
				onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
				onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
				href="#" onClick="addBatch();return false;">添加培训班</a>
		</li>
	</s:if>
</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>

<table width="100%">
	<tr>
		<td valign="top">
				<table width="100%" height="100%" align="center" cellpadding="1"
					cellspacing="1" >
					<tr>
						<th align="center" >培训班名称</th>
						<th align="center" >证书名称</th>
						<th align="center" >最少选修学分</th>
						<th align="center" >培训班类别</th>
						<th align="center" >开放状态</th>
						<th align="center" >&nbsp;</th>
						<th align="center" >&nbsp;</th>
					</tr>
			<s:if test="elclassList.size==0">
				</table>
			</s:if>
			<s:else><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="elclassList">
						<tr>
							<td align="center" bgcolor="#FFFFFF" style="color:#CC0099;"><s:property value="name" /></td>
							<td align="center" ><s:property value="certificatename" /></td>
							<td align="center" ><s:property value="optionalcredit" /></td>
							<td align="center" ><s:property value="cltype.name" /></td>
							<td align="center" ><s:property value="statusName" /></td>
							<td width="80" align="center" ><a href="elclass_addInit.action?elclassId=<s:property value="id" />" class=textbg4>详 情</a>
							<td width="80" align="center" ><a href="batch_elclass_del.action?elClassId=<s:property value="id" />&batchId=
							  <s:property value="batchId" />" class=textbg4>删 除</a>
						  </td>
						</tr>
					</s:iterator></tbody>
</table>
			</s:else>
		</td>
	</tr>
</table>

</body>
</HTML>
