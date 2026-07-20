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
		<TITLE>选择数据表列</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			var return_value = "";
			function selectColumn(){
				var value =$(":radio:checked").val(); 
				return_value = value;
				window.returnValue = return_value;
				window.close();
			}
			
		</script>
	</HEAD>
	<body>
		<div style="margin-left: 20px;">
				<table width="100%" align="center" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<tr>
						<th></th>
						<th width="100" height="30" align="center" >
							列名								 
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="relateTable.columns" status="status">
						<tr>
							<td width="20" height="20" align="center">
								<input type="radio" value="<s:property value="columnName"/>"
									name="columnName" >
							</td>
							<td width="20" height="20" align="center" >
								<s:property value="columnName"/>
							</td>
						</tr>
					</s:iterator>
					</tbody>
			  	</table>
			  		
				<input type="button" style="margin-left:260px" value="确&nbsp;认" onclick="selectColumn();" class="textbg4"/>
		</div>
		
	
	</body>
</HTML>
