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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>标签列表</TITLE>
		<base target="_self">
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
		<script type="text/javascript">
			function checkOrderIdIsInOrg(org_param_array,orderid){
				var flag = true;
				for(var i=0;i<org_param_array.length;i++){
					if(parseInt(org_param_array[i]) == parseInt(orderid)){
						flag = true;
						break;
					}else{
						if(i == org_param_array.length- 1){
							flag = false;
							break;
						}
					}
				}
				return flag;
			}
			
			//判断是否有重复数据
			function checkRep(ary){
				return $.unique(ary);
			}

			
			function doButton(){
				var size = '<s:property value="customReportJSZList.size()"/>';
				//拼凑参数
				var param = "";
				var only_orderid = "";
				for(var i=0;i<size;i++){
					/**
					if(!checkOrderIdIsInOrg(org_param_array,$("#orderid_"+i).val())){
						alert("排序id只能在填写"+org_prarm+"中的一个,请重新填写");
						return;
					}
					*/
					param += $("#columnname_"+i).text() +"==" + $("#orderid_"+i).val() + ",";
					only_orderid += $("#orderid_"+i).val() + ",";
				}
				if(param!="" && param.charAt(param.length-1)==","){
					param = param.substring(0,param.lastIndexOf(","));
				}
				if(only_orderid!="" && only_orderid.charAt(only_orderid.length-1)==","){
					only_orderid = only_orderid.substring(0,only_orderid.lastIndexOf(","));
				}
				var ary = only_orderid.split(",");
				var len1 = ary.length;
				var ary_return = checkRep(ary);
				var len2 = ary_return.length;
				
				if(len1 != len2){
					alert("排序有重复数据，请重新修改");
					return ;
				}
				//判断是否有重复
				if(window.confirm("确认修改?")){
						$.ajax({
						  type: 'POST',
						  url: "changeJSZId.action",
						  data: {value:param,'customReport.id':"<s:property value='customReport.id' />"},
						  async:true,//同步
						  success: function(data){
						  	window.returnValue = 1;
						  	window.close();
						  }
						});																										
					}
				
			}
		</script>
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
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<span>首页</span>&nbsp;>>&nbsp;
					<span>系统管理</span>&nbsp;>>&nbsp;
					<span>自定义报表</span>&nbsp;>>&nbsp;
					<span>设置自定义报表</span>&nbsp;>>&nbsp;
					<span>统计字段排序修改</span>
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 40px; text-align: center;">

			<table align="center" cellpadding="2" cellspacing="2" width="100%"
				 bgcolor="#ECEDEB">
				<tr>
					<th>
						标签名称	
					</th>
					<th>
						计算方式
					</th>
					<th>
						排序编号	
					</th>
					<th>
						操作	
					</th>
				</tr>
			<s:iterator value="customReportJSZList" status="status">
					<tr>
						<td height="20" width='20%' align="center" id='columnname_<s:property value='#status.index' />'>
							<s:property value="columnname"  />
						</td>
						<td height="20" width='40%' align="center">
							<s:property value="formula" />
						</td>
						<td height="20" width='20%' align="center" >
							<input type='text' name='orderid' value='<s:property value='orderid' />' id='orderid_<s:property value='#status.index' />' />
						</td>
						<td height="20" width='20%' align="center">
							<a href="javascript:void(0);" onclick="deleteJSZById('<s:property value='id' />');">删除</a>
						</td>
					</tr>
				</s:iterator>	
			</table>
			<br>
			<center>
				<input type="button" value="确认" onclick="doButton();"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="关闭" onclick="window.close();return false;"/>
			</center>
		</div>
		<form action="getJSZByLableid.action" name="form" method="post">
			<input type="hidden" name="customReport.id" id="customReport.id" value= "<s:property value='customReport.id' />" />
		</form>
		<script type="text/javascript">
			var org_prarm = "";
			var org_param_array;
			var size = '<s:property value="customReportJSZList.size()"/>';
			for(var i=0;i<size;i++){
				org_prarm += $("#orderid_"+i).val() + ","; 
			}
			if(org_prarm!="" && org_prarm.charAt(org_prarm.length-1)==","){
				org_prarm = org_prarm.substring(0,org_prarm.lastIndexOf(","));
				org_param_array = org_prarm.split(",");
			}
			
			
			function deleteJSZById(id){
				if(window.confirm("确定删除?")){
					$.ajax({
					  type: 'POST',
					  url: "deleteJSZById.action",
					  data: {id:parseInt(id)},
					  async:true,//同步
					  success: function(data){
					  	alert("删除成功!!!");
					  	form.submit();
					  }
					});	
				}
			}
		</script>
	</body>
</HTML>
