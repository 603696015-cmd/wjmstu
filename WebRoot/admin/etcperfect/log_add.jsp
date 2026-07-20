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
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>添加日志</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
  </HEAD>
  <%String message=(String)request.getAttribute("message"); %>
  <script type="text/javascript">
  		
  
  
         function message(){
              var message="<%=message %>";
              if(message!="null"&&message!=null&&message!=""){
                 alert(message);
                 document.location="lineTrainRecord_list.action";
              }
         }
         
         function doSubmit(){
         	if($("#trainname").val()==""){
         		alert("培训班名称不能为空！");
         		return false;
         	}
         	var stuffArray=$("#stuff").find("input");
         	for(var i=0;i<stuffArray.length;i++){
         		//if(stuffArray[i].name=="linetrainrecord_stuff.title"){
         			if(stuffArray[i].value==""){
         				alert("附件名称和附件不能为空！");
         				return false;
         			}
         			if(stuffArray[i].name=="myFile"&&stuffArray[i].value.indexOf(".")!=-1){
         				//判断是否exe
         				var fileExName=stuffArray[i].value.substring(stuffArray[i].value.indexOf("."),stuffArray[i].value.length);
         				//alert(fileExName);
         				if(fileExName==".exe"){
         					alert("请不要上传.exe文件!");
         					return false;
         				}
         			}
         		//}
         	}
         	return true;
         }
        var ii = 0;
		function addSt(){
			ii++;
			var stuff = document.createElement("div");
			stuff.id="ds_"+ii;
			stuff.innerHTML="附件标题：<input type='text' style='width:200px;' name='linetrainrecord.lineTrainRecordStuffs.title' id='stufftt_"+ii+"' />"+
			"&nbsp;&nbsp;&nbsp;附件：<input type='file' name='myFile' />";
			document.getElementById("stuff").appendChild(stuff);
			
		}
		function deleteSt(){
			if(ii<=0)return ;
			var stuff = document.getElementById("ds_"+ii);
			document.getElementById("stuff").removeChild(stuff);
			ii--;
				
		}
		function delStuff(trainid,id){
			document.location.href="lineTrainRecordStuff_delete.action?linetrainrecord.trainid="+trainid+"&lineTrainRecordStuff.id="+id;
		}
  </script>
  <body onLoad="loadStuff();">
  		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加日志" /></div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
    	
		<!-- 内容 -->
		<s:form action="log_add" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
		<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="1" cellspacing="1" width="700">
					<tr>
						<td width="160" height="30" align="center" >
							日志标题：
						</td>
						<td >
							<label>
								<s:textfield name="log.log_title"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							工作计划：
						</td>
						<td >
							<label>
								<s:textfield name="log.log_plan"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							工作结果：
						</td>
						<td >
							<label>
								<s:textfield name="log.log_result"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							总结分析：
						</td>
						<td >
							<label>
							<s:textfield name="log.log_analysis" size="40" />
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="160" height="30" align="center" >
							关联客户：
						</td>
						<td >
							<label>
							<s:textfield name="log.log_co_client" size="40" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							关联计划：
						</td>
						<td >
							<label>
							<s:textfield name="log.log_co_plan" size="40" />
							</label>
						</td>
					</tr>
	                
					
					 <tr>
						<td width="160" height="30" align="center" >
							附件：
						</td>
						<td >
							<script type="text/javascript">
								
									function addStufff(i) {
										width=600;
										height=400;
									   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										//var rv = window.showModalDialog("editor/editor/filemanager/browser/default/browser.html?Type=&Connector=connectors/jsp/connector",null,sFeature);
										var rv = window.showModalDialog("question_stuffList.action",null,sFeature);
										
										 if(null==rv){
										 	alert("您没选择东西！");
										 	return ;
										 }
										 document.getElementById("stufft_"+i).innerHTML=rv;
										 document.getElementById("stuff_"+i).value=rv;
										 }
									var ii = 0;
									function addSt(){
										ii++;
										var stuff = document.createElement("div");
										stuff.id= "ds_"+ii;
										stuff.innerHTML="名称：<input type='text' style='width:200px;' name='knowledge.stuffs.title' id='stufftt_"+ii+"'/><input type='hidden' style='width:200px;' name='knowledge.stuffs.description' id='stuff_"+ii+
										"'/>&nbsp;&nbsp;&nbsp;地址：<span style='width:150px;'  id='stufft_"+ii
										+"'></span>&nbsp;&nbsp;&nbsp;<a onclick='addStufff("+
										ii+")'>浏览资源库</a>";
										document.getElementById("stuff").appendChild(stuff);
										
									}
									function deleteSt(){
										if(ii<=0)return ;
										var stuff = document.getElementById("ds_"+ii);
										document.getElementById("stuff").removeChild(stuff);
										ii--;
											
									}
									function getT(){
									var o = document.getElementsByTagName("input");
										for(var i=0;i<o.length;i++){
											alert(o[i].name+"==="+o[i].value);
										}
									}
									
									function loadStuff()
							  		{
							  			
							  		}
							  		
							  		function initSt(){
										ii++;
										var stuff = document.createElement("div");
										stuff.id= "ds_"+ii;
										stuff.innerHTML="名称：<input type='text' style='width:200px;' name='knowledge.stuffs.title' id='stufftt_"+ii+"'/><input type='hidden' style='width:200px;' name='knowledge.stuffs.description' id='stuff_"+ii+
										"'/>&nbsp;&nbsp;&nbsp;地址：<span style='width:150px;'  id='stufft_"+ii
										+"'></span>&nbsp;&nbsp;&nbsp;<a onclick='addStufff("+
										ii+")'>浏览资源库</a>";
										document.getElementById("stuff").appendChild(stuff);
										
									}
							  		
								</script>
								<div id="stuff">
								</div>
								<input type="button" onClick="addSt();" value="添加" class=textbg4>
								<input type="button" onClick="deleteSt();" value="删除">
						</td>
					</tr>
				</table>
				
		</div>
		
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认添加" class=textbg6 />
		</div>
		<s:hidden name="log.log_createtime" id="log_createtime" />
		</s:form>
  </body>
</html>