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
			function select_table_and_column(){
				var value =$(":radio:checked").val(); 
				return_value = value;
				window.returnValue = return_value;
				window.close();
			}
			
			function do_onclick(radio,tablename){
				alert(tablename);
				width=400;
				height=300;
				var url = "relateColumnsInit2.action?x="+Math.random()+"&tablename="+tablename;
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
				if(rv!=undefined&&rv!=""){
					alert(rv);
				}
				
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
							表名								 
						</th>
						<th width="90" height="30" align="center" >
							描述									
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="relateTables" status="status">
						<tr>
							<td width="20" height="20" align="center">
								<input type="radio" value="<s:property value="tableName"/>"
									name="tableName" >
							</td>
							<td width="20" height="20" align="center" >
								<s:property value="tableName"/>
							</td>
							<td height="30" align="center" >
								<s:property value="comments"/>
							</td>
						</tr>
					</s:iterator>
					</tbody>
			  	</table>
			  		
				<input type="button" style="margin-left:260px" value="确&nbsp;认" onclick="select_table_and_column();" class="textbg4"/>
		</div>
		
	</body>
</HTML>
