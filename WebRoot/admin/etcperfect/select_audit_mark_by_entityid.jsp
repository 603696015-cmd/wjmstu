<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" target="_self">
		<TITLE>备注列表</TITLE>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript">
		function showContent(id){
		var obj = document.getElementById(id);
		if(obj.style.display==''||obj.style.display=='block'){
			obj.style.display='none';
		}else{
			obj.style.display="block";
		}
	}
	
	
	function page(i){ 
			 		document.getElementById("pageNow").value=i;
			 		search.submit();
			 	}
		</script>
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="备注列表" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="select_audit_mark_by_entityid.action" name="search" method="post">
		<s:hidden name="id"/>
		<s:hidden name="tablename"/>
		<s:hidden name="pN" id="pageNow"/>
		<s:hidden name="pS"/>
		<table width="600" align="center" cellpadding="1" cellspacing="1">
			<caption>
				备注
			</caption>
			<tr>
				<th align="center">
					内容
				</th>
				<th align="center">
					审核级别
				</th>
				<th align="center">
					审核人
				</th>
				<th align="center">
					审核状态
				</th>
				<th align="center">
					审核日期
				</th>
				<th align="center">
					操作
				</th>
			</tr>
			<s:iterator value="ams">
				<tr>
					<td align="center">
						<div style="word-wrap: break-word; word-break: break-all; width:100%;">
						<a
							href="javascript:showContent('crelist_<s:property value="id"/>')">
							  <s:if test="audit_mark.length()>5">
							  	<s:property value="audit_mark.substring(0,5)+'...'" /> 
							  </s:if>

			           		 <s:else>
				                <s:property value="audit_mark" /> 
				           	 </s:else> 
			            </a>
						</div>
					</td>
					<td align="center">
						<s:property value="auditName_chinese" />
					</td>
					<td align="center">
						<s:property value="username" />
					</td>
					<td align="center">
						<s:property value="status_chinese" />
					</td>
					<td align="center">
						<s:date name="audittime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td align="center">
						<a
							href="javascript:showContent('crelist_<s:property value="id"/>')">查看</a>
					</td>
				</tr>
				<tr style="display: none;" id="crelist_<s:property value="id"/>">
					<td style="font: 12px; padding: 5px;" colspan="6">
						<s:property value="audit_mark" />
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td bgcolor="#FFFFFF" style="font: 12px; padding: 5px;" align="center" colspan="6">
						
					<a
						href="#" onclick="window.close();return false;"
						style="width: 80px;padding: 3px 0px 3px 0px;" class="textbg4">关闭</a>
				</td>
			</tr>
		</table>
		</form>
		<center><wysLib:page></wysLib:page></center>
	</BODY>
</HTML>
