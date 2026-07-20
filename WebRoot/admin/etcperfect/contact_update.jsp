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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript">
			 function doSubmit(){
			 if($("#theme").val()==""){
         		alert("联系主题不能为空！");
         		return false;
         	}
         	if($("#content").val()==""){
         		alert("联系内容不能为空！");
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
			
			function setid(i)
			{
				//alert(i);
			}
		</script>
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="基础数据修改" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="updateContact.action" method="post" theme="simple" onsubmit="return doSubmit();">
			<s:hidden name="contact.id"   />
	
			<table width="700px" cellpadding="2" cellspacing="1" >
				<tr>
						<td width="160" height="30" align="center" >
							联系主题：
						</td>
						<td >
							<label>
								<s:textfield name="contact.theme"  id="theme" size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							联系类型：
						</td>
						<td >
							<label>
								<s:select  name="contact.type" 
								list="{'销售机会','报价','竞争分析','需求分析','方案管理','投标管理','中标事宜'}"  
								theme="simple" headerKey="" headerValue="请选择"  />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							联系内容：
						</td>
						<td >
							<label>
								<s:textfield name="contact.content"  id="content" size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							联系时间：
						</td>
						<td >
							<label>
							<!-- <s:textfield name="contact.time" size="40" /> -->
							<input class="Wdate" name="contact.time" readonly="readonly"
							type="text" onClick="setday(this);" id="relea"     />
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="160" height="30" align="center" >
							预期金额：
						</td>
						<td >
							<label>
							<s:textfield name="contact.money" size="40" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							关联客户：
						</td>
						<td >
							<label>
							<s:textfield name="contact.re_client" size="40" />
							</label>
						</td>
					</tr>
	                
				
				
				<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="padding-left:8px;color:blue;"><span class="STYLE2">
							  附件					  </span></td>
							<td bgcolor="#FFFFFF" >
								<span class="STYLE2">
								<script type="text/javascript">
								
									function addStufff(i) {
										/*width=600;
										height=400;
									   var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										var   rv   =  window.showModalDialog("question_stuff_mylist.action?pN=0&pS=10",
										 null,sFeature);
										 //if("undefined"!=rv)
										 document.getElementById("stufft_"+i).innerHTML=rv;
										rv= rv.substring(rv.lastIndexOf("/")+1);
										rv = rv.substring(0,rv.lastIndexOf("."));
										*/
										width=600;
										height=400;
									   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										//var rv = window.showModalDialog("editor/editor/filemanager/browser/default/browser.html?Type=&Connector=connectors/jsp/connector",null,sFeature);
										var rv = window.showModalDialog("question_stuffList.action",null,sFeature);
										
										 if(null==rv){
										 	alert("您没选择东西！");
										 	return ;
										 }
										 //alert(rv);
										 document.getElementById("stufft_"+i).innerHTML=rv;
										 document.getElementById("stuff_"+i).value=rv;
										 }
									var ii = 0;
									function addSt(){
										ii++;
										var stuff = document.createElement("div");
										stuff.id= "ds_"+ii;
										stuff.innerHTML="名称：<input type='text' style='width:200px;' name='knowledge.stuffs.title' id='stufftt_"+ii+"'/>地址：<input type='hidden' style='width:200px;' name='knowledge.stuffs.description' id='stuff_"+ii+"'/><span style='width:200px;'  id='stufft_"+ii+"'></span>&nbsp;&nbsp;&nbsp;<a onclick='addStufff("+ii+")'>浏览资源库</a>";
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
											if(o[i].name=='knowledge.stuffs.description')
											alert(o[i].name+"=="+o[i].value);
										}
									}
								</script>
								</span>
								<div class="STYLE2" id="stuff">
									<s:iterator value="list_contactstuff" status="stst">
										<div id="ds_">
											<span id='stufft_'>名称：<input type="hidden"
													name='knowledge.stuffs.description'
													value="<s:property value="stuffaddr"/>" />
												<input type="hidden" name='knowledge.stuffs.id'
													value="<s:property value="id"/>" />
												<input type='text' style='width: 200px;'
													name='knowledge.stuffs.title'
													value="<s:property value="title"/>" />地址：<s:property
													value="stuffaddr" />
											</span>&nbsp;&nbsp;&nbsp;
											   <a href="deleteContactstuff.action?contactstuff.id=<s:property value="id"/>&contact.id=<s:property value="contact.id"/>" >删除</a>	 								
											  
											  </div>
											
									</s:iterator>
								</div>
								<span class="STYLE2">
							 								
								<input type="button" onClick="addSt();" value="添加">
								<input type="button" onClick="deleteSt();" value="删除">
								 	
		              </span></td>
				  </tr>
				
				
				
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					
					<td >
						<input class="textbg6" type="submit" value="确认修改" >
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
