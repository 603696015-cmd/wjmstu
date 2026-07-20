<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户信息与部门</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		var ele;
		$.ajax({
				  type: 'POST',
				  url: "getCurrentUserinfo.action",
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			ele = "<tr>"+
									"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>用户姓名:"+
									"</td>"+
									"<td style='padding-left:10px;color:#0099CC'>"+
											data.name+
									"</td>"+
									"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>部门:"+
									"</td>"+
									"<td style='padding-left:10px;color:#0099CC'>"+
											data.depname+
									"</td>"+
								"</tr>"+
								"<tr>"+
									"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>职务:"+
									"</td>"+
									"<td style='padding-left:10px;color:#0099CC'>"+
											data.zhiwuname+
									"</td>"+
									"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>地市:"+
									"</td>"+
									"<td style='padding-left:10px;color:#0099CC'>"+
											data.dishiname+
									"</td>"+
								"</tr>";
								document.write(ele);
				  		}
				  }
			});
	</script>
  </HEAD>
  <body >
  </body>
</html>
