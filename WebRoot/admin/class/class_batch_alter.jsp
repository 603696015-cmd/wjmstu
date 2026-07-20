<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.EroomBatch"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>考场批次管理</title>
		<base href="<%=basePath%>">
		<meta http-equiv=Page-Enter
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
			function deleteerinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			depid = <s:property value="batch.id"/> ;
			$.post("class_batch_class_class_delete.action", {
				"elclass.id":id,
				"batch.id":depid,
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
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
					<wysLib:Navigation ivalue="修改培训班批次" />
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
			<s:form action="class_batch_alter" method="post" name="catalog_info"
				theme="simple">
				<table width="740px" align="left" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" height="30" align="center">
							名称
						</td>
						<td>
							<label>
								<s:textfield name="batch.name" id="name" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							介绍
						</td>
						<td>
							<label>
								<s:textarea name="batch.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center">
							培训班：
						</td>
						<td>
							<div id="can_op">
								<s:iterator value="batch.classes">
									<div
										style="width: 620px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<div style="float: left;">
											<table width="600px" style="margin: 0px;" align="center" cellpadding="1"
												cellspacing="1">
												<tr>
													<td height="20" align="center">
														培训班名称
													</td>
													<td height="20" align="center">
														开始时间
													</td>
													<td height="20" width="150px" align="center">
														结束时间
													</td>
												</tr>
												<tr>
													<td height="20" align="center">
														<s:property value="name" />
													</td>
													<td height="20" align="center">
														<s:date name="starttime" format="yyyy-MM-dd HH:mm:ss" />
													</td>
													<td height="20" align="center">
														<s:date name="finishtime" format="yyyy-MM-dd HH:mm:ss" />
													</td>
												</tr>
											</table>
										</div>
										<a
											style="cursor: hand; float: right; width: 14px; height: 14px;"
											href=""
											onclick="javascript:deleteerinfo(this,<s:property value="id"/>,'use');return false;">X</a>
									</div>
								</s:iterator>
							</div>
							<a href="" class="textbg4" 
								onclick="searchClassInit('can_op','batch.classes.id'); return false;">添加</a>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center">
							&nbsp;

						</td>
						<td>
							<s:hidden name="batch.id" />
							<input type="submit" value="确认修改" class="textbg4" style="width:90px"/>
							<a href="stat_class_batch_list.action" class="textbg4" style="width:90px">返回</a>
						</td>
					</tr>
				</table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	</body>
</html>
