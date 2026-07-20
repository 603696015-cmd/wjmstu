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
		<TITLE>选择数据表</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				searchRelate.submit();
			}
			
			function doSubmit(){
				var checkObj = document.getElementsByName("id");
			    var billIDs = "";
			    for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				}
				if(billIDs=="" || billIDs.split(",").length >2){
				  alert("请至少选择1-2个复选框！");
				  return ;
			    }
			    var module_ids = billIDs;
			    alert(module_ids);
			    return ;
			    
				window.returnValue = module_ids;
				window.close();
			}
		</script>
	</HEAD>
	<body>
		<div >
				<form action="searchRelateTableInit.action" method="post" name="searchRelate">
					<s:hidden name="pN" id="pageNow" />
					<s:hidden name="pS" /> 
				</form>
				<table width="100%" align="center" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<tr>
						<th width="5%"></th>
						<th width="30%" height="30" align="center" >
							模块名								 
						</th>
						<th width="25%" height="30" align="center" >
							表名									
						</th>
						<th width="40%" height="30" align="center" >
							标记									
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="moduleTables">
						<tr>
							<td width="5%" height="20" align="center">
								<input type="checkbox" name="id" value="<s:property value="id"/>"/>
							</td>
							<td width="30%" height="20" align="center">
								<s:property value="modulename"/>
							</td>
							<td width="25%" height="20" align="center">
								<s:property value="tablename"/>
							</td>
							<td height="40%" align="center" >
								<s:property value="remark" />
							</td>
						</tr>
					</s:iterator>
					</tbody>
			  	</table>
				<input type="button" style="margin-left:260px" value="确&nbsp;认" onclick="doSubmit();" class="textbg4"/>
				<center>
					<s:if test="count != 0">
						<wysLib:page></wysLib:page>
					</s:if>
				</center>
		</div>
		
	
	</body>
</HTML>
