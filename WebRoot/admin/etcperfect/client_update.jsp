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
		<title>添加客户</title>
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
  
  var numofman = 0;
  
  
  function myload()
  {
   //setCurTime("releasetime");
  }
  		
  	function setCurTime(oid){
				var now=new Date();
				var year=now.getYear();
				var month=now.getMonth()+1;
				var day=now.getDate();
				var hours=now.getHours();
				var minutes=now.getMinutes();
				if(minutes<10){
					minutes="0"+minutes;
				}
				var seconds=now.getSeconds();
				if(seconds<10){
					seconds="0"+seconds;
				}
				var timeString = year+"-"+month+"-"+day;//+" "+hours+":"+minutes+":"+seconds;
				var oCtl = document.getElementById(oid);
				oCtl.value = timeString;
				//setTimeout("setCurTime('"+oid+"')",1000);
				//alert(oid);
			}
  
  
  
         function message(){
              var message="<%=message %>";
              if(message!="null"&&message!=null&&message!=""){
                 alert(message);
                 document.location="lineTrainRecord_list.action";
              }
         }
         
         function doSubmit(){
         	if($("#name").val()==""){
         		alert("公司名称不能为空！");
         		return false;
         	}
         	if($("#tel").val()==""){
         		alert("公司号码不能为空！");
         		return false;
         	}
         	if($("#name_"+numofman).val()==""){
         		alert("联系人姓名不能为空！");
         		return false;
         	}
         	
         	
         	//var stuffArray=$("#stuff").find("input");
         //	for(var i=0;i<stuffArray.length;i++){
         		//if(stuffArray[i].name=="linetrainrecord_stuff.title"){
         			//if(stuffArray[i].value==""){
         			//	alert("附件名称和附件不能为空！");
         		//		return false;
         	//		}
         	//		if(stuffArray[i].name=="myFile"&&stuffArray[i].value.indexOf(".")!=-1){
         	//			//判断是否exe
         //				var fileExName=stuffArray[i].value.substring(stuffArray[i].value.indexOf("."),stuffArray[i].value.length);
         				//alert(fileExName);
       //  				if(fileExName==".exe"){
        // 					alert("请不要上传.exe文件!");
       //  					return false;
         //				}
         //			}
         		//}
      //   	}
         	return true;
         	//return false;
         }
		
		//--------------------
		
		function test()
		{alert("ca");
		}
		
		
				
				function addMan()
				{
				alert("jelo");
				}
				
				
				function addMans()
				{//alert(numofman);
					numofman++;
					var linkman = document.createElement("div");
					linkman.id="link_"+numofman;
					linkman.innerHTML="<table  width='850' >"+
					"<tr>"+
					"<td width='40'>序号</td><td><input readonly='readonly' value="+numofman+" /></td>"+
					"<td width='100'>联系类型</td><td><select name='list_clientlinkman.type'><option value=''>请选择</option><option value='普通员工'>普通员工</option><option value='部门经理'>部门经理</option><option value='重要联系人'>重要联系人</option><option value='老总'>老总</option></select></td>"+
					" <td width='40'>姓名<span style='color:red'>*</span></td><td><input type='text' name='list_clientlinkman.name'  id='name_"+numofman+"' /></td> " +
					" <td width='40'>性别</td><td><select name='list_clientlinkman.sex'><option value='男'>男</option><option value='女'>女</option></select></td> " +
					" <td width='40'>部门</td><td><input type='text' name='list_clientlinkman.dep'   /></td> " +
					"</tr>"+
					"<tr>"+
					" <td width='40'>职务</td><td><input type='text' name='list_clientlinkman.duty'   /></td> " +
					" <td width='100'>负责业务</td><td><input type='text' name='list_clientlinkman.task'   /></td> " +
					" <td width='40'>工作电话</td><td><input type='text' name='list_clientlinkman.worktel'   /></td> " +
					" <td width='40'>移动电话</td><td><input type='text' name='list_clientlinkman.phone'   /></td> " +
					" <td width='40'>传真</td><td><input type='text' name='list_clientlinkman.tax'   /></td> " +
					"</tr>"+
					"<tr>"+
					" <td width='40'>邮箱</td><td><input type='text' name='list_clientlinkman.emainl'   /></td> " +
					" <td width='100'>家庭电话</td><td><input type='text' name='list_clientlinkman.hometel'   /></td> " +
					" <td width='40'>MSN/QQ</td><td><input type='text' name='list_clientlinkman.msnqq'   /></td> " +
					" <td width='40'>生日</td><td><input class='Wdate'  readonly='readonly' type='text' name='list_clientlinkman.birthday' onClick='setday(this);'  /></td> " +
					" <td width='40'>爱好</td><td><input type='text' name='list_clientlinkman.hobby'   /></td> " +
					"</tr>"+
					"<tr>"+
					" <td width='40'>备注</td><td colspan='9'><input type='text' name='list_clientlinkman.remark'   /></td> " +
					" </tr> "+
					"</table>";
					
					document.getElementById("linkmans").appendChild(linkman);
				}
				
				
				function delMans()
				{
					if(numofman<=0)  return ;
					var linkman = document.getElementById("link_"+numofman);
					document.getElementById("linkmans").removeChild(linkman);
					numofman--;
				}
				
				function delLinkmanid(i)
				{
					//alert(i);
					
					if(window.confirm("确认删除？")){
				//	alert("id:"+i);
					var str=document.getElementById("linkmanid").value;
					if(str==null||str=="")
					{
						str=i;
					}
					else str +=","+i;
					document.getElementById("linkmanid").value=str;
					
					document.getElementById("linkman_"+i).style.display="none";

				//	alert(str);
					}
				}
				
				function updateClientlinkman()
				{
					if(window.confirm("确认删除？"))
					{
						
					}
				}
				
				
		
		
  </script>
  <body onLoad="myload();">
  		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加客户" /></div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
    	
		<!-- 内容 -->
		<s:form action="updateClient" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
				<s:hidden name="linkmanids" id="linkmanid"/>
				<s:hidden name="client.id"   />
		<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="1" cellspacing="1" width="850">
					<tr>
						<td width="80" height="30" align="center" >
							公司名称：
						</td>
						<td >
								<s:textfield name="client.name"  id="name" size="40"></s:textfield>
							<span style="color:red">*</span>
							
						</td>
						<td width="80" height="30" align="center" >
							公司电话：
						</td>
						<td >
								<s:textfield name="client.tel" id="tel" size="40"></s:textfield>
								<span style="color:red">*</span>
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							公司传真：
						</td>
						<td >
							<label>
								<s:textfield name="client.tax"  size="40"></s:textfield>
							</label>
							
						</td>
						<td width="80" height="30" align="center" >
							公司网址：
						</td>
						<td >
							<label>
								<s:textfield name="client.url"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
	                
	                <tr>
						<td width="80" height="30" align="center" >
							公司邮箱：
						</td>
						<td >
							<label>
								<s:textfield name="client.email"  size="40"></s:textfield>
							</label>
							
						</td>
						<td width="80" height="30" align="center" >
							所在城市：
						</td>
						<td >
							<label>
								<s:textfield name="client.city"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							公司地址：
						</td>
						<td >
							<label>
								<s:textfield name="client.addr"  size="40"></s:textfield>
							</label>
							
						</td>
						<td width="80" height="30" align="center" >
							邮编：
						</td>
						<td >
							<label>
								<s:textfield name="client.postcode"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
	                
	                <tr>
						<td width="80" height="30" align="center" >
							客户来源：
						</td>
						<td >
						<s:select  name="client.clientfrom" 
						list="{'电话来访','客户介绍','独立开发','媒体宣传','促销活动','朋友介绍','公司招标','网络营销','邮寄宣传','代理商','合作伙伴','其他','后台订单','在线订购','短信订购'}"  
						theme="simple" headerKey="" headerValue="请选择"  />
						<!-- 
							<select name="client.clientfrom">
							<option value="" >请选择</option>
							<option value="电话来访" >电话来访</option><option value="客户介绍" >客户介绍</option>
							<option value="独立开发" >独立开发</option><option value="媒体宣传" >媒体宣传</option><option value="促销活动" >促销活动</option>
							<option value="朋友介绍" >朋友介绍</option><option value="公司招标" >公司招标</option><option value="网络营销" >网络营销</option>
							<option value="邮寄宣传" >邮寄宣传</option><option value="代理商" >代理商</option><option value="合作伙伴" >合作伙伴</option>
							<option value="其他" >其他</option><option value="后台订单" >后台订单</option><option value="在线订购" >在线订购</option>
							<option value="短信订购" >短信订购</option>
							</select>
							 -->
						</td>
						<td width="80" height="30" align="center" >
							公司性质：
						</td>
						<td >
						<s:select  name="client.companytype"
						list="{'民营企业','国有企业','政府机构','事业单位','外商独资','合资/合作','其他','私营企业'}" 
						theme="simple" headerKey="" headerValue="请选择">
						</s:select> 
						</td>
						<!-- 
							<select name="client.companytype">
							<option value="" >请选择</option>
							<option value="民营企业" >民营企业</option><option value="国有企业" >国有企业</option><option value="政府机构" >政府机构</option>
							<option value="事业单位" >事业单位</option><option value="外商独资" >外商独资</option><option value="合资/合作" >合资/合作</option>
							<option value="其他" >其他</option><option value="私营企业" >私营企业</option>
							</select>
							
							 -->
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							行业：
						</td>
						<td >
						
						<s:select name="client.industry" 
						list="{'化工','食品','医药','服装','运输','建筑','其他','制造','工程'}" 
						theme="simple" headerKey="" headerValue="请选择">
						</s:select> 
						<!-- 
							<select name="client.industry">
							<option value="" >请选择</option>
							<option value="化工" >化工</option><option value="食品" >食品</option><option value="医药" >医药</option>
							<option value="服装" >服装</option><option value="运输" >运输</option><option value="建筑" >建筑</option>
							<option value="其他" >其他</option><option value="制造" >制造</option><option value="工程" >工程</option>					
							</select>
							 -->
						</td>
						<td width="80" height="30" align="center" >
							主营业务：
						</td>
						<td >
							<label>
								<s:textfield name="client.mainbusiness"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
	                
	                <tr>
						<td width="80" height="30" align="center" >
							公司规模：
						</td>
						<td >
						
						<s:select name="client.companysize" 
						list="{'1-50人','51-100人','101-250人','251-500人','501-1000人','1001-5000人','5001-10000人','10000人或以上'}" 
						theme="simple" headerKey="" headerValue="请选择">
						</s:select> 
						<!-- 
							<select name="client.companysize" >
								<option value="" >请选择</option>
								<option value="1-50人" >1-50人</option><option value="51-100人" >51-100人</option><option value="101-250人" >101-250人</option>
								<option value="251-500人" >251-500人</option><option value="501-1000人" >501-1000人</option><option value="1001-5000人" >1001-5000人</option>
								<option value="5001-10000人" >5001-10000人</option><option value="10000人或以上" >10000人或以上</option>
							</select>
							 -->
						</td>
						<td width="80" height="30" align="center" >
							开业年份：
						</td>
						<td >
							<input class="Wdate" name="client.startbusiness" readonly="readonly"
							type="text" onClick="setday(this);" id="relea" onblur="setTime();"   value="<s:property value="client.startbusiness"/>" />
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							注册资金：
						</td>
						<td >
								<s:textfield name="client.registeredcapital"  size="40"></s:textfield>
							
						</td>
						<td width="80" height="30" align="center" >
							公司法人：
						</td>
						<td >
							<label>
								<s:textfield name="client.legal"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							开户银行：
						</td>
						<td >
							<label>
								<s:textfield name="client.bank"  size="40"></s:textfield>
							</label>
							
						</td>
						<td width="80" height="30" align="center" >
							银行帐号：
						</td>
						<td >
							<label>
								<s:textfield name="client.bankaccount"  size="40"></s:textfield>
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							税号：
						</td>
						<td >
							<label>
								<s:textfield name="client.duty"  size="40"></s:textfield>
							</label>
							
						</td>
						<td width="80" height="30" align="center" >
							客户建立日期：
						</td>
						<td >
							<input class="Wdate" name="client.createdate" readonly="readonly"
							type="text" onClick="setday(this);" id="releasetime" onblur="setTime();"   value="<s:property value="client.createdate"/>" />
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center"  >
							上级客户：
						</td>
						<td colspan="3">
							<label>
								<s:textfield name="client.superclient"  size="40"></s:textfield>
							</label>
							
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							备注：
						</td>
						<td colspan="3">
							<s:textarea cols="100" rows="5" name="client.remark" ></s:textarea>
							
						</td>
					</tr>
	                
					<!--  -->
					
				</table>
				
				
				
				
				
				
				
		</div>
		
		<table>
			<tr>
			<td>
			已有联系人管理
			</td>
			</tr>
		</table>
		
		<!-- 
			<s:iterator value="list_clientlinkman" status="st" >
		
		<table  width='850' id="linkman_<s:property value="id" />"> 
					<tr> 
					<td width='40'>序号</td><td><s:textfield name="#st.index+1"  id ="hanghao" /></td> 
					<td width='100'>联系类型</td><td><s:select  name="type" list="{'普通员工','部门经理','重要联系人','老总'}" theme="simple" headerKey="" headerValue="请选择"></s:select> </td> 
					<td width='40'>姓名</td><td><s:textfield name="name" /></td> 
					 <td width="40">性别</td><td><s:textfield name="sex" /></td> 
					 <td width="40">部门</td><td><s:textfield name="dep"   /></td> 
					</tr> 
					<tr> 
					 <td width="40">职务</td><td><s:textfield name="duty"   /></td> 
					 <td width="100">负责业务</td><td><s:textfield name="task"   /></td> 
					<td width="40">工作电话</td><td><s:textfield name="worktel"   /></td> 
				 <td width="40">移动电话</td><td><s:textfield name="phone"   /></td> 
					 <td width="40">传真</td><td><s:textfield name="tax"   /></td> 
					</tr> 
					<tr> 
					 <td width="40">邮箱</td><td><s:textfield name="emainl"   /></td> 
					 <td width="100">家庭电话</td><td><s:textfield name="hometel"   /></td> 
					 <td width="40">MSN/QQ</td><td><s:textfield name="msnqq"   /></td> 
					 <td width="40">生日</td><td><s:textfield name="birthday"   /></td> 
					 <td width="40">爱好</td><td><s:textfield name="hobby"   /></td> 
				</tr> 
					<tr> 
					 <td width="40">备注</td><td colspan="8"><s:textfield name="remark"   /></td> 
					 <td><input type="button"  onClick="delLinkmanid(<s:property value="id" />);" value="删除" /> </td>
					 </tr>  
					</table>
		</s:iterator>
		 --> 
		 
		 <s:iterator value="list_clientlinkman" status="st" >
		
		<table  width='850' id="linkman_<s:property value="id" />"> 
					<tr> 
					<td width='40'>操作</td><td><input type="button"  onClick="delLinkmanid(<s:property value="id" />);" value="删除" /> </td><!-- <td><s:textfield name="#st.index+1"  id ="hanghao" /></td>  -->
					<td width='100'>联系类型</td><td><s:select  value="type" name="list_clientlinkman.type" list="{'普通员工','部门经理','重要联系人','老总'}" theme="simple" headerKey="" headerValue="请选择"></s:select> </td> 
					<td width='40'>姓名</td><td><input   value="<s:property value="name" />" name="list_clientlinkman.name" /></td> 
					 <td width="40">性别</td><td><s:select    value="sex"   name="list_clientlinkman.sex"   list="{'男','女'}" theme="simple" headerKey="" headerValue="请选择"/></td> 
					 <td width="40">部门</td><td><input   value="<s:property value="dep"  />"   name="list_clientlinkman.dep"  /></td> 
					</tr> 
					<tr> 
					 <td width="40">职务</td><td><input  value="<s:property  value="duty" />"   name="list_clientlinkman.duty"   /></td> 
					 <td width="100">负责业务</td><td><input   value="<s:property value="task"  />"  name="list_clientlinkman.task"   /></td> 
					<td width="40">工作电话</td><td><input   value="<s:property value="worktel"  />"   name="list_clientlinkman.worktel"  /></td> 
				 <td width="40">移动电话</td><td><input   value="<s:property value="phone"  />"  name="list_clientlinkman.phone"   /></td> 
					 <td width="40">传真</td><td><input   value="<s:property value="tax" />"   name="list_clientlinkman.tax"   /></td> 
					</tr> 
					<tr> 
					 <td width="40">邮箱</td><td><input   value="<s:property value="emainl"  />"   name="list_clientlinkman.emainl"  /></td> 
					 <td width="100">家庭电话</td><td><input   value="<s:property value="hometel" />"   name="list_clientlinkman.hometel"   /></td> 
					 <td width="40">MSN/QQ</td><td><input   value="<s:property value="msnqq" />"   name="list_clientlinkman.msnqq"   /></td> 
					 <td width="40">生日</td><td><input class='Wdate'  readonly='readonly' type='text' name="list_clientlinkman.birthday" onClick='setday(this);' value="<s:property value="birthday" />" /></td> 
					 <td width="40">爱好</td><td><input   value="<s:property value="hobby"  />"   name="list_clientlinkman.hobby"  /></td> 
				</tr> 
					<tr> 
					 <td width="40">备注</td><td colspan="9"><input value="<s:property value="remark" />"   name="list_clientlinkman.remark"   /></td> 
					 
					 </tr>  
					 <input type="hidden" name="list_clientlinkman.id" value="<s:property value="id" />" />
					</table>
		</s:iterator>
		 
		 
		 
		<table width="850">
				<tr>
					<td>添加新联系人</td>
					<td>
						<input type="button" onClick="addMans();" value="添加" class=textbg4>
								<input type="button" onClick="delMans();" value="删除">
					</td>
					
				</tr>
	
					
				</table>
				
				<div id="linkmans" style="margin-top: 30px; text-align: center;">
				</div>
				
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认添加" class=textbg6 />
		</div>
		</s:form>
  </body>
</html>