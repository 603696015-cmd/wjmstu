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
		<TITLE>知识查询</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏新闻类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示新闻类别</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
		function Obj(pp_,status_){ 
			this.pp=pp_; 
			this.status=status_; 
		} 
		//按钮
		var ToolsBarObj = null;
		var pp = [];
		var status = [];
		var pn = '<s:property value="pN"/>';
		var ps = '<s:property value="pS"/>';
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_view","查看详情","images/newversion/un_view.gif","viewDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_beizhu","查看备注","images/newversion/un_view.gif","beizhuDetail()");
		});
		
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			status = obj.status;
			if(pp.length>1 || pp.length==0){
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
				
			}else if(pp.length == 1){
				st = status[0];
				ToolsBarObj.ToolsBar_Enabled("toolbar_view");
				ToolsBarObj.ToolsBar_Enabled("toolbar_beizhu");
			}
		}
		
		//查看
		function viewDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			view(pp.toString());
		}
		
		//备注
		function beizhuDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			show_beizhu(pp.toString());
		}
		
		
		//获取选中的checkbox
		function getCheckedCheckboxs(pp,status){
			var checkboxs = document.getElementsByName("id_");
			if(checkboxs.length>0){
				if(pp.length>0)  pp=[];
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked){
						pp.push(checkboxs[i].value);
						status.push(document.getElementById("status_"+i).value);
					}
				}
			}
			var obj = new Obj(pp,status);
			return obj;
		}
		</script>
	</HEAD>
	<body>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我添加的知识" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top">
							<s:form action="listMyKledge.action" method="post" name="kledge" theme="simple">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<input type="hidden" name="kledge.id" id="kledge.id" />
							</s:form>
						<s:if test="kledges.size==0"><span style="margin-top:10px;">暂无知识</span></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#EBEBEB">
								<tr>
								<td colspan=20><div id="Div_ToolsBar"></div></td>
								</tr>
								<tr>
									<th width="20" ></th>
									<th width="100" height="30" align="center" >
										知识名称									</th>
									<th width="100" height="30" align="center" >
										知识类别									</th>
									<th width="100" height="30" align="center" >
										开始时间									</th>
									<th width="100" height="30" align="center" >
										结束时间									</th>
									<th width="100" height="30" align="center" >
										发布人									</th>
									<th width="100" height="30" align="center" >
										发布时间									</th>
									<th width="100" height="30" align="center" >
										修改人								</th>
									<th width="100" height="30" align="center" >
										修改时间								</th>
										<!-- 
										<th width="100" height="30" align="center" >
										状态							</th>
										 -->
									
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="kledges" status="status">
									<tr>
										<input type="hidden" id="status_<s:property value='#status.index'/>" value="<s:property value='status' />"/>
									  	<td width='20' height='20' align='center'>
											<input type='checkbox' onclick='clickcheckbox();' value="<s:property value="id" />" name='id_'/>
										</td>
										<td height="30" align="center">
											<s:property value="name" />
									    </td>
										<td height="30" align="center" >
												<s:property value="klTree.name" />
										</td>
										<td height="30" align="center" >
											<s:date name="begintime" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											<s:date name="endtime" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											<s:property value="fabuUser.realname" />
										</td>
										<td height="30" align="center" >
											<s:date name="fabutime" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											<s:property value="xiugaiUser.realname" />
										</td>
										<td  height="30" align="center" >
											<s:date name="xiugaitime" format="yyyy-MM-dd HH:mm" />
									  	</td>
									  	<!-- 
									  	<td  height="30" align="center" >
											<s:property value="status_" />
									  	</td>
									  	 -->
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				kledge.submit();
			}
			
		</script>
			<wysLib:page></wysLib:page>
		</div>
		<script type="text/javascript">
			//查看
			function view(id){
				document.getElementById("kledge.id").value = id;
				kledge.action = "viewKledge.action";
				kledge.submit();
			}
			
			//备注
			function show_beizhu(entityid){
				var tablename = "KNOWLEDGEMANAGE";
				width=800;
				height=600;
				var url = "getKnowledgeMark.action?id="+entityid+"&tablename="+tablename+"&x="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
			}
		</script>
	
	</body>
</HTML>
										   