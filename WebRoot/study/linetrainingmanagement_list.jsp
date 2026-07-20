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
		<TITLE>线下培训记录管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script> 
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
			<script type="text/javascript" src="js/calendar.js"></script>
			<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示部门</a>';
					}
				}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="申请列表" /></div>
			</li>
			<!--<li>
					 线下培训记录管理
				</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<table width="100%">
			<tr>
			<td valign="top" id="tree_list_td" width="150px;" style="display:none"><wysLib:dep_list_aj rootAble="true" href="linetrainingmanagement.action?state=0&deptid="></wysLib:dep_list_aj></td>
			
			<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" id="showimg" />
				</td>
			
			<td valign="top" align="left">
				<div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg">显示部门</a>
								</div>
			<s:form action="linetrainingmanagement" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="deptid" />
				<tr>
				
				   <td>  申请人：</td>
				     <td><s:textfield name="username" theme="simple"/></td>
				 <td>培训名称 </td>
				      <td><s:textfield name="name" theme="simple"/> </td>
				    <td>培训所在时间：</td>
					<td><input name='start'
						value="<s:date name="peixun" format="yyyy-MM-dd" />"
						onclick='setday(this)' readonly="readonly"/></td>
				</tr>
				<tr>
				
				   <td>申请时间  开始时间:</td>
				     <td> 
					<input name='start'
						value="<s:date name="start" format="yyyy-MM-dd" />"
						onclick='setday(this)' readonly="readonly"/>
					 </td>
				       <td>结束时间:</td>
				         <td>
				         	<input name='end'value="<s:date name="end" format="yyyy-MM-dd"/>" onclick='setday(this)' readonly="readonly"/>
						</td>
						<td > 状态：<s:select theme="simple"  headerValue="全部" headerKey="0"
									list="#{1:'制作中',2:'审核等待中',3:'未通过',4:'已审核'}"
									name="state1" value="state1" /></td>
						<td > <s:submit  value="搜索"/></td>
				      
				</tr>
			</table>
			</s:form>
			<s:if test="listLineTrainrecord.size==0">当前还没有需要审核的线下培训申请</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						线下培训记录
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						
						</td>
					   <td height="30" align="center" >
							申请人
						</td>
					    <td height="30" align="center" >
							申请时间时间
						</td>
						<td height="30" align="center" >
							培训名称
						</td>
						
						<td height="30" align="center" >
							培训开始时间
						</td>
						<td height="30" align="center" >
							培训结束时间
						</td>
						<td height="30" align="center" >
							培训时长
						</td>
						<td height="30" align="center" >
							设置学分
						</td>
						<td height="30" align="center" >
							证书名称
						</td>
						<td height="30" align="center" >
							审核状态
						</td>
						<td></td>
					</tr>
					<s:if test="listLineTrainrecord.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前还没有线下培训记录
							</TD>
						</TR>
					</s:if>
					<s:else>
						<s:iterator value="listLineTrainrecord">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							     <input type="checkbox" value="<s:property value="trainid"/>" name="trainid"> 
						       </td>
                                <td height="30" align="center" >
									<s:property value="createname" />
								</td>
                                <td height="30" align="center" >
									<s:date name="submittime" format="yyyy-MM-dd" />
								</td>
								<td height="30" align="center" >
									<s:property value="trainname" />
								</td>
								<td height="30" align="center" >
									<s:date name="trainstarttime" format="yyyy-MM-dd" />
								</td>
								<td height="30" align="center" >
									<s:date name="trainendtime" format="yyyy-MM-dd" />
								</td>
								<td height="30" align="center" >
									<s:property value="trainlength" />
								</td>
								<td align="center" bgcolor="#FFFFFF" onClick="alterFee(this,<s:property value="trainid"/>)"> 
									<span id=<s:property value="trainid"/> class="h30"><s:property value="credit" /></span>
								</td>
								<td height="30" align="center" >
									<s:property value="certificate" />
								</td>
								<td height="30" align="center" >
									<s:property value="StateName" />
								</td>
								<td height="30" align="center" >
									<a href="lineTrainRecordLook.action?linetrainrecord.trainid=<s:property value="trainid" />" class="textbg4">查看</a>
								</td>
							</tr>
						</s:iterator>
					</s:else>
				</table>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption align="right">
						总学分：<s:property value="allcredit" />
					</caption>
				</table>
				<input type="submit" value="通过" style="margin-top:20px;margin-left:40px;" onClick="updateState(4);">
			    <input type="submit" value="驳回" style="margin-top:20px;margin-left:40px;" onClick="updateState(3);">
			</s:else></td></tr></table><wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	</BODY>
		<form action="linetrainingmanagement.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
				<s:hidden name="start" />
				<s:hidden name="end" />
				<s:hidden name="peixun" />
				<s:hidden name="name" />
				<s:hidden name="username" />

			</form>
				<div id="fee" style="background: #ddfdff;display:none; border: 1 solid buttonface;width: 160px;position: absolute;" >
		<input type="text" id="cfee" size="5"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onClick="saveFee()" value="设定" />
		<input type="button" onClick=" document.getElementById('fee').style.display='none'" value="关闭"/>
		</div>
	<script type="text/javascript">
	
	
	        var cid = 0 ; 
			var cname = '' ; 
			function alterFee(obj,courseid){ 
			
				cid =  courseid ;
				cname =  obj.parentElement.children[3].innerHTML;
				document.getElementById("fee").style.display="block";
				var left = (obj.offsetLeft + obj.clientWidth);
				var top = (obj.offsetTop);
				while (obj = obj.offsetParent) {
					left += obj.offsetLeft;
					top += obj.offsetTop;
				}
				document.getElementById("fee").style.left =left-200;
				document.getElementById("fee").style.top =top;
			}
			function saveFee(){

				if(cid==0){
				alert("请选择课程");
				return;
				}
					
					var fee1 = document.getElementById("cfee").value;
					if(isNaN(fee1)){
					   alert("请输入正确的学分数！");
					   return;
					}
					if(fee1<0){
					   alert("请输入正确的学分数！");
					   return;
					}
				if(window.confirm("确定为\""+cname+"\"设定学分为"+fee1+"？")){
						var ax={"trainid":cid,"credit":fee1};
						
						var  aaaaa="#"+cid  ;
					$(aaaaa).load(
					"setlinetrainrecord.action",ax);
				
					}
				 document.getElementById("fee").style.display="none";
			
			}
			      function updateState(type){
	           var checkObj = document.getElementsByName("trainid");
			   var billIDs = "";
			   for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				}
			  if(billIDs==""){
			     alert("提示框", "请选择要操作的的记录！");
			     return;
			  }
			  location = "updateState.action?ids="+billIDs+"&state="+type;
	      }
      	function page(i){
		 		document.getElementById("pageNow").value=i;
		 		acc_list.submit();
		}
	</script>
</HTML>
