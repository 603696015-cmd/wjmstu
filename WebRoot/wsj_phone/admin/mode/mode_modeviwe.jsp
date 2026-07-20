<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base target="_self"> 
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
		<SCRIPT type="text/javascript" src="js/mode/zdytree.js" ></script>
	</HEAD>
	<BODY  onBeforeUnload="if(ClosesWindow == true){CheckWindowClosed();}" onmouseover="ClosesWindow=false;" onmouseout="ClosesWindow=true;">
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<span style="font-weight: bold;">模块列表</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="mode==null">无信息</s:if>
			<s:else>
			<table align="center" cellpadding="2" cellspacing="2" width="700"
				 bgcolor="#ECEDEB">
				<tr>
					<th width="100px"   >
						模块名称	
					</th>
					<th width="250px"  >
					<font color="red"><s:property value="mode.name" /></font>	
					</th>
					<th width="100px" >
						模块表	
					</th>
					<th width="250px">
						<font color="red"><s:property value="mode.tableName" /></font>
					</th>
				</tr>
				<tr>
					<th width="100px">
						绑定模板	
					</th>
					<th width="250px">
						<s:if test="mode.modeJspName==null||mode.modeJspName==''">
							<font color="red">未绑定</font>
						</s:if>
						<s:else>
							<font color="red"><s:property value="mode.modeJspName" /></font>
						</s:else>
						
					</th>
					<th width="350px" colspan="2">
						  <a class=textbg6 onclick="gettemplatelist('1')">修改绑定</a>
						  <s:if test="mode.modeJspid!=0">
						  	&nbsp;&nbsp;<a onclick="removebind()" class=textbg6>解除绑定</a>
						  </s:if>
						 &nbsp;&nbsp;<a onclick="wclose()" class=textbg6>关&nbsp;闭</a>
					</th>
				</tr>		
			</table>
			</s:else>
		</div>
		<div >
				<table align="center" cellpadding="2" cellspacing="2" width="500" bgcolor="#ECEDEB" id="show">
				</table>
				<table align="center" cellpadding="2" cellspacing="2" width="500" bgcolor="#ECEDEB" id="sumbit" style="display:none">
					<tr>
						<th>
							 <a class=textbg6  onclick="return sumbitupd()">确&nbsp;&nbsp;定</a>
						</th>
					</tr>
				</table>
		</div>
		<form action="mode_updmodebind.action" method="post"
			name="acc_list">
			<s:hidden name="mode.typeid"/>
			<s:hidden name="mode.id" />
			<s:hidden name="mode.bindtypeid" />
			<s:hidden name="mode.bindid" />
			<s:hidden name="mode.modeJspid" id="modemodeJspid"/>
		</form>
		
		<script type="text/javascript">
		$(
		function aaa(){
			if("${elmessage}"!=""){
				alert("${elmessage}");
				

			}
		
		});
		var ClosesWindow = true;
		function CheckWindowClosed(){
			 window.parent.returnValue='1'
		}
		function  wclose(){
		 window.parent.returnValue='1'
		 window.close();
		
		}





		
			function gettemplatelist(i){
			
			$.ajax({
			  type: 'POST',
			  url: "mode_modepageList.action",
			  data: {"newpageajax":i},
			  async:true,//
			  success: function(data){
			  $('#show').empty();
					$(data).appendTo($('#show'));
					
				}
				});	
				$("#sumbit").css({ display:"block" });
			}
			function sumbitupd(){
			
				var flag = false;
			    
				if($("input[name='templateradio'][checked]").val()!=null){
					var id =$("input[name='templateradio'][checked]").val();
					$("#modemodeJspid").attr("value",id);
					acc_list.submit();
				}else{
					alert("请选择模板");
					return false;
				}
				
			}	
			function removebind(){
				acc_list.action="mode_removebind.action";
				acc_list.submit();
			
			}
			function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<!-- 内容 -->
	
	</body>
</HTML>
