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
		<TITLE>培训班批次管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function checkHaser(_id,id){
				if( document.getElementById("_d_u"+_id+id)) return true;
				return false;
			}
			function searchClassInit(_id,input_name ,comp){
			     width=820;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("class_batch_class_list.action?m="+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择培训班！');
				 }else{
				 	if(rv[0]<=0)  	alert('您没有选择培训班！');
				 	for(var i = 0 ;i <rv.length ; i++){
				 	if(!checkHaser(_id,rv[i]))
				 		addclassinfo(_id,rv[i],input_name);
				 	}
				 }
			}
			function addclassinfo(_id,id,inputname){
				var _d = document.createElement("<div>");
				_d.id = "_d_u"+_id+id;
				_d.style.width="620px";
				_d.style.height="14px";
				_d.style.background="#dddfff";
				_d.style.float="left";
				_d.style.border="solid buttonface 1px";
				$.post("class_batch_class_view.action", {
					"elclass.id":id,
					"input_name":inputname, 
					"x":Math.random
					}, 
					function (data) {
						$("#"+_d.id).html(data);
					}); 
				document.getElementById(_id).appendChild(_d);
			}
			function deleteerinfo(obj,id){
					obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
			function onsubmit_(){
				if($("#erbname").val()==''){
					alert("请填写批次标题！");
					return false;
				}
			}
		</script>
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
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班批次" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="class_batch_add" method="post" name="catalog_info"
				theme="simple" onsubmit="return onsubmit_();">
				<table width="100%" align="left" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" height="30" align="center">
							名称
						</td>
						<td>
							&nbsp;<label>
								<s:textfield name="batch.name" id="erbname" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							介绍
						</td>
						<td>
							&nbsp;<label>
								<s:textarea name="batch.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							添加培训班：
						</td>
						<td>
							<div id="can_op">

							</div><div style="clear: both;"></div>
							&nbsp;<a href="" class="textbg4"
								onclick="searchClassInit('can_op','batch.erooms.id'); return false;">添加</a>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center">&nbsp;
							

						</td>
						<td>
							&nbsp;<input type="submit" style="width: 90px" class="textbg4"
								value="确认添加" />
								
							<a href="stat_class_batch_list.action" class="textbg4" style="width:90px">返回</a>
						
						</td>
					</tr>
				</table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
