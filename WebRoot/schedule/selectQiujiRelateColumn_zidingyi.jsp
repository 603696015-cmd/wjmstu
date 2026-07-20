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
			function selectQiujiRelateColumn(){
				var checkObj = document.getElementsByName("relateColumns");
			    var billIDs = "";
			    for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				}
				if(billIDs==""){
				  alert("请至少选择一个复选框！");
				  return ;
			    }
				window.returnValue = billIDs;
				window.close();
			}
			
			function select_id(){
				width=800;
				height=600;
				var url = "searchShebei.action?x="+Math.random()+"&tablename="+tablename;
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
				if(rv!=undefined&&rv!=""){
					alert(rv);
					var value = rv.split(":");
					document.getElementById("shebei_id").value = value[0];
					document.getElementById("IC.read_auto_biaodi").value = value[1];
					document.getElementById("IC.read_auto_toubaoren").value = value[2];
					document.getElementById("IC.read_auto_beibaoren").value = value[3];
					document.getElementById("tablename").value = tablename;
				}
			}
		</script>
	</HEAD>
	<body>
		<div style="margin-left: 20px;">
				<table width="100%" align="center" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<tr>
						<th width="10%"></th>
						<th width="45%" height="30" align="center" >
							列名								 
						</th>
						<th width="45%" height="30" align="center" >
							描述									
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="list_tags" status="status">
						<tr>
							<td width="10%">
								<input type="checkbox" name="relateColumns" value="<s:property value='column_name'/>"/>
							</td>
							<td width="45%" height="20" align="center" >
								<s:property value="column_name"/>
							</td>
							<td width="45%" height="30" align="center" >
								<s:property value="name_display"/>
							</td>
						</tr>
					</s:iterator>
					</tbody>
			  	</table>
			  		
				<input type="button" style="margin-left:260px" value="确&nbsp;认" onclick="selectQiujiRelateColumn();" class="textbg4"/>
		</div>
		
	</body>
</HTML>
