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
			function doSubmit(){
				var type="<s:property value='type'/>";
				var checkObj = document.getElementsByName("id");
			    var billIDs = "";//字段id
			    var column = "";//字段名称
			    for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				}
				if(type == 'time'){
					if(billIDs=="" || billIDs.split(",").length <2){
					  alert("请选择2个日期类型字段复选框！");
					  return ;
				    }
				}else if(type == 'yewu'){
					if(billIDs=="" || billIDs.split(",").length <3){
					  alert("请选择2个日期类型字段和1个百分比类型字段复选框！");
					  return ;
				    }else {
				    	var  billIDs_array = billIDs.split(",");
				    	var  columns = "";
				    	for(var i=0;i<billIDs_array.length;i++){
				    		if(i == billIDs_array.length - 1)
				    			columns += getNameDisplayById(parseInt(billIDs_array[i]));
				    		else 
				    			columns += getNameDisplayById(parseInt(billIDs_array[i])) + ",";
				    	}
				    	
				    	billIDs = billIDs + "=" + columns;
				    }
				}else if(type == 'yewu_relate_1'){
					//判断该字段是否是相关字段
					if(billIDs=="" || billIDs.split(",").length !=1){
					  alert("请选择1个相关字段复选框！");
					  return ;
				    }else {
				    	billIDs = parseInt(billIDs);
				    	column = getNameDisplayById(parseInt(billIDs));
				    	
				    	billIDs = billIDs + "," + column;
				    }
				}else if(type == 'yewu_relate_2'){
					//判断该字段是否是相关字段
					if(billIDs=="" || billIDs.split(",").length !=1){
					  alert("请选择1个开始时间复选框！");
					  return ;
				    }else {
				    	billIDs = parseInt(billIDs);
				    	column = getNameDisplayById(parseInt(billIDs));
				    	
				    	billIDs = billIDs + "," + column;
				    }
				}else if(type == 'yewu_relate_3'){
					//判断该字段是否是相关字段
					if(billIDs=="" || billIDs.split(",").length !=1){
					  alert("请选择1个结束时间复选框！");
					  return ;
				    }else {
				    	billIDs = parseInt(billIDs);
				    	column = getNameDisplayById(parseInt(billIDs));
				    	
				    	billIDs = billIDs + "," + column;
				    }
				}else if(type == 'jisuan_produce'){
					if(billIDs=="" || billIDs.split(",").length !=1){
					  alert("请选择1个复选框！");
					  return ;
				    }else {
				    	billIDs = parseInt(billIDs);
				    	column = getNameDisplayById(parseInt(billIDs));
				    	
				    	billIDs = billIDs + "," + column;
				    }
				}else if(type == "jisuan_result"){
					if(billIDs=="" || billIDs.split(",").length !=1){
					  alert("请选择1个复选框！");
					  return ;
				    }else {
				    	billIDs = parseInt(billIDs);
				    	column = getNameDisplayById(parseInt(billIDs));
				    	
				    	billIDs = billIDs + "," + column;
				    }
				}else if(type == "jisuan_relate"){
					if(billIDs=="" || billIDs.split(",").length !=1){
					  alert("请选择1个复选框！");
					  return ;
				    }else {
				    	billIDs = parseInt(billIDs);
				    	column = getNameDisplayById(parseInt(billIDs));
				    	
				    	billIDs = billIDs + "," + column;
				    }
				}else if(type=="ziduan_wanzheng"){
					if(billIDs==""){
						alert("请至少选择1个复选框！");
					  	return ;
					}else {
						var  billIDs_array = billIDs.split(",");
				    	var  columns = "";
				    	for(var i=0;i<billIDs_array.length;i++){
				    		if(i == billIDs_array.length - 1)
				    			columns += getNameDisplayById(parseInt(billIDs_array[i]));
				    		else 
				    			columns += getNameDisplayById(parseInt(billIDs_array[i])) + ",";
				    	}
				    	billIDs = billIDs + "=" + columns;
					}
				}else if(type=="biaojianqiuhe"){
					if(billIDs=="" || billIDs.split(",").length !=1){
					  alert("请选择1个复选框！");
					  return ;
				    }else {
				    	billIDs = parseInt(billIDs);
				    	column = getNameDisplayById(parseInt(billIDs));
				    	
				    	billIDs = billIDs + "," + column;
				    }
				}
				window.returnValue = billIDs;
				window.close();
			}
			
			function getNameDisplayById(id){//id为id数组
				var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "getNameDisplayById.action",
					  data: {id:id},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != "")
				  			returnValue = data;
					  }
				});
				return returnValue;
			}
		</script>
	</HEAD>
	<body>
		<div >
				<table width="100%" align="center" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<tr>
						<th width="5%"></th>
						<th width="30%" height="30" align="center" >
							列名								 
						</th>
						<th width="30%" height="30" align="center" >
							描述								 
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="list_tags">
						<tr>
							<td width="5%" height="20" align="center">
								<input type="checkbox" name="id" value="<s:property value="id"/>"/>
							</td>
							<td width="30%" height="20" align="center">
								<s:property value="column_name"/>
							</td>
							<td width="30%" height="20" align="center">
								<s:property value="name_display"/>
							</td>
						</tr>
					</s:iterator>
					</tbody>
			  	</table>
				<input type="button" style="margin-left:260px" value="确&nbsp;认" onclick="doSubmit();" class="textbg4"/>
		</div>
		
	
	</body>
</HTML>
