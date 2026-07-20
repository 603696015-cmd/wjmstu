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
   setCurTime("releasetime");
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
		<s:form action="addClient" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
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
							<select name="client.clientfrom">
							<option value="" >请选择</option>
							<option value="电话来访" >电话来访</option><option value="客户介绍" >客户介绍</option>
							<option value="独立开发" >独立开发</option><option value="媒体宣传" >媒体宣传</option><option value="促销活动" >促销活动</option>
							<option value="朋友介绍" >朋友介绍</option><option value="公司招标" >公司招标</option><option value="网络营销" >网络营销</option>
							<option value="邮寄宣传" >邮寄宣传</option><option value="代理商" >代理商</option><option value="合作伙伴" >合作伙伴</option>
							<option value="其他" >其他</option><option value="后台订单" >后台订单</option><option value="在线订购" >在线订购</option>
							<option value="短信订购" >短信订购</option>
							</select>
							
						</td>
						<td width="80" height="30" align="center" >
							公司性质：
						</td>
						<td >
							<select name="client.companytype">
							<option value="" >请选择</option>
							<option value="民营企业" >民营企业</option><option value="国有企业" >国有企业</option><option value="政府机构" >政府机构</option>
							<option value="事业单位" >事业单位</option><option value="外商独资" >外商独资</option><option value="合资/合作" >合资/合作</option>
							<option value="其他" >其他</option><option value="私营企业" >私营企业</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td width="80" height="30" align="center" >
							行业：
						</td>
						<td >
							<select name="client.industry">
							<option value="" >请选择</option>
							<option value="化工" >化工</option><option value="食品" >食品</option><option value="医药" >医药</option>
							<option value="服装" >服装</option><option value="运输" >运输</option><option value="建筑" >建筑</option>
							<option value="其他" >其他</option><option value="制造" >制造</option><option value="工程" >工程</option>					
							</select>
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
							<select name="client.companysize" >
								<option value="" >请选择</option>
								<option value="1-50人" >1-50人</option><option value="51-100人" >51-100人</option><option value="101-250人" >101-250人</option>
								<option value="251-500人" >251-500人</option><option value="501-1000人" >501-1000人</option><option value="1001-5000人" >1001-5000人</option>
								<option value="5001-10000人" >5001-10000人</option><option value="10000人或以上" >10000人或以上</option>
							</select>
							
						</td>
						<td width="80" height="30" align="center" >
							开业年份：
						</td>
						<td >
							<input class="Wdate" name="client.startbusiness" readonly="readonly"
							type="text" onClick="setday(this);" id="relea" onblur="setTime();" />
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
							type="text" onClick="setday(this);" id="releasetime" onblur="setTime();" />
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
		<table width="850">
				<tr>
					<td>联系人添加</td>
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