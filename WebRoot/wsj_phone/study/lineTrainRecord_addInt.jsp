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
		<title>添加培训记录</title>
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
  <body onLoad="message();">
    	<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<!-- span style="font-weight: bold;">我的课程</span>
					<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="lineTrainRecord_addInt.action">添加线下培训记录</a>
					<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="lineTrainRecord_list.action">查看与修改</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<s:form action="lineTrainRecord_add" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
				<s:hidden name="linetrainrecord.trainid"></s:hidden>
				<s:hidden name="linetrainrecord.submittime"></s:hidden>
				<s:hidden name="linetrainrecord.state"></s:hidden>
		<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="1" cellspacing="1" width="100%">
					<tr>
						<td width="160" height="30" align="center" >
							培训名称：
						</td>
						<td >
							&nbsp;&nbsp;<label>
								<s:textfield name="linetrainrecord.trainname" id="trainname" size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训开始时间：
						</td>
						<td >
							&nbsp;&nbsp;<label>
								<s:textfield name="linetrainrecord.trainstarttime" readonly="true" size="40" onclick="setday(this)"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训结束时间：
						</td>
						<td >
							&nbsp;&nbsp;<label>
								<s:textfield name="linetrainrecord.trainendtime" readonly="true" size="40" onclick="setday(this)"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训时长：
						</td>
						<td >
							&nbsp;&nbsp;<label>
							<s:textfield name="linetrainrecord.trainlength" size="40" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							培训证书名称：
						</td>
						<td >
							&nbsp;&nbsp;<label>
								<s:textfield name="linetrainrecord.certificate" size="40" />
							</label>
						</td>
					</tr>
	                <tr>
						<td width="160" height="30" align="center" >
							备注：
						</td>
						<td >
							&nbsp;&nbsp;<label>
								<s:textarea name="linetrainrecord.remark" style="width:230"></s:textarea>
							</label>
						</td>
					</tr>
					<%-- 
					<tr>
						<td width="160" height="30" align="center" >
							附件名称：
						</td>
						<td >
							<label>
								<s:textfield name="linetrainrecord_stuff.title" size="40" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							附件：
						</td>
						<td >
							<label>
								<s:file name="myFile" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >
						</td>
						<td >
							<input name="submit" type="submit" value="确认添加" />
						</td>
					</tr>
					 --%>
					 <tr>
						<td width="160" height="30" align="center" >
							附件：
						</td>
						<td >
							<div id="stuff" style="margin-top:8px;width:560px;">
								<%-- 
								名称：<s:textfield name="linetrainrecord_stuff.title" size="20" />
								地址：<s:file name="myFile" /><input type="file" name="myFile" />
								 --%>
								&nbsp;&nbsp;<s:iterator value="linetrainrecord.lineTrainRecordStuffs">
									<div style="text-align:left;padding-left:13px;">
										附件标题：<s:property value="title" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:delStuff('<s:property value="linetrainrecord.trainid" />','<s:property value="id" />');">删除</a>
									</div>
								</s:iterator>
							</div>
							&nbsp;&nbsp;<label>
								<input type="button" onClick="addSt();" value="添加" class=textbg4>
								<input type="button" onClick="deleteSt();" value="删除" class=textbg4>
							</label>
						</td>
					</tr>
		  </table>
				
		</div>
		
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认添加" class="textbg6" />
			<a href="lineTrainRecord_list.action" class=textbg>查看与修改</a>
		</div>
		</s:form>
  
	</body>
</html>