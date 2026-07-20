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
		<TITLE>绑定信息</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
			
	<link rel="stylesheet" href="css/mode/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="js/mode/jquery.ztree.core-3.5.js"></script>
	</HEAD>
	<BODY  onBeforeUnload="if(ClosesWindow == true){CheckWindowClosed();}" onmouseover="ClosesWindow=false;" onmouseout="ClosesWindow=true;">
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<span style="font-weight: bold;">绑定操作</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="mode==null">无信息</s:if>
			<s:else>
			<table align="center" cellpadding="2" cellspacing="2" width="750"
				 bgcolor="#ECEDEB">
				<tr>
					<th width="70px"   >
						模块名称	
					</th>
					<th width="80px"  >
					<font color="red"><s:property value="mode.name" /></font>	
					
					</th>
					<th width="100px"   >
						类别ID
					</th>
					<th width="100px"  ><font color="red"><s:property value="node.id" /></font></th>
					
					<th width="100px" >
						类别表	
					</th>
					<th width="100px">
						<font color="red"><s:property value="mode.TypetableName" /></font>
					</th>
					<th width="100px" >
						继承方式	
					</th>
					<th width="100px">
						<font color="red">
							<s:if test="mode.bindtypestatus==1">
						  		下级继承
						 	 </s:if><s:else>
						 		不继承
						  </s:else>
						</font>
					</th>
				</tr>
				<tr>
					<th width="150px" colspan="2">
						绑定模板	
					</th>
					<th width="200px" colspan="2">
						<s:if test="mode.modeJspName==null||mode.modeJspName==''">
							<font color="red">未绑定</font>
						</s:if>
						<s:else>
							<font color="red"><s:property value="mode.modeJspName" /></font>
						</s:else>
						
					</th>
					<th width="400px" colspan="4">
						  <a class=textbg6 onclick="gettemplatelist('1')">修改绑定</a>
						  <s:if test="mode.modeJspid!=0">
						  	&nbsp;&nbsp;<a onclick="removebind()" class=textbg6>解除绑定</a>
						  </s:if>
						   <s:if test="mode.bindtypestatus==1">
						  	&nbsp;&nbsp;<a onclick="updextend(2)" class=textbg6>取消继承</a>
						  </s:if><s:elseif  test="mode.bindtypestatus==2">
						  &nbsp;&nbsp;<a onclick="updextend(1)" class=textbg6>设置继承</a>
						  </s:elseif>
						  &nbsp;&nbsp;<a onclick="wclose()" class=textbg6>关&nbsp;闭</a>
					</th>
				</tr>		
			</table>
			</s:else>
		</div>
		<div ><table  align="center" cellpadding="2" cellspacing="2" width="750">
					<tr>
					<td valign="top" width="250">
						<ul id="treeDemo" class="ztree"></ul>
					</td>
					
					<td valign="top">
					<div class="right">
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
					
					</td>
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
			<s:hidden name="node.id" id="nodeid"/>
			<s:hidden name="mode.bindtypestatus" id="modebindtypestatus"/>
			<s:hidden name="mode.TypetableName" />
			
		</form>
		
		<script type="text/javascript">
		$(
		function  showtree(){
		var setting = {
		callback: {
				onClick: onClick
			}
		};
		

		
		var	zNodes=eval("("+'${json}'+")");
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var id=$("#nodeid").val();
		
			var node =zTree.getNodeByParam('id',id,null);
			
			zTree.selectNode(node);
			
		});	
			
		}		
		)
		function onClick(event, treeId, treeNode, clickFlag) {
			
		
			$("#nodeid").attr("value",treeNode.id);
			
			acc_list.action="mode_typemodeviwe.action";
			acc_list.submit();
			
		}
		function updextend(statusid){
			
			$("#modebindtypestatus").attr("value",statusid);
			acc_list.action="mode_updtypeextend.action";
			acc_list.submit();
		}
		$(
		function aaa(){
			if('${elmessage}'!=""){
				alert('${elmessage}');
				

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
