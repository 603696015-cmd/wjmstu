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
		<TITLE>我添加的知识</TITLE>
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
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_view","查看详情","images/newversion/un_view.gif","viewDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_update","修改","images/newversion/un_view.gif","updateDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_delete","删除","images/newversion/un_view.gif","deleteDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_firstInstance","提交审核","images/newversion/un_view.gif","firstInstanceDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_applicationUpdate","申请修改","images/newversion/un_view.gif","applicationUpdateDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_applicationDelete","申请删除","images/newversion/un_view.gif","applicationDeleteDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_beizhu","查看备注","images/newversion/un_view.gif","beizhuDetail()");
		});
		
		function check_can_op(array){
			var flag = false;
			if(array!=null&&array.length>0){
				for(var i=0;i<array.length;i++){
					if(array[i] == undefined){
						flag = false;
						return flag;
					}
				}
				if(array.toString().indexOf("false")<0)	flag = true;
			}
			return flag;
		}
		
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			status = obj.status;
			var can_delete = [];
			var can_applicationUpdate = [];
			var can_applicationDelete = [];
			var can_firstInstance = [];
			var st = 0;
			if(pp.length>1){
				//对于选择了多个checkbox
				//查看详情、修改、查看备注无法点击;
				//删除、提交审核、申请修改、申请删除等需要根据选择的数据id判断状态，只有在所选的数据所有id都满足条件的时候才可以点击
				for(var i=0;i<pp.length;i++){
					st = status[i];
					if(st == 0){//已创建//状态为0才能进行修改、删除、提交审核
						can_delete[i] = true;
						can_firstInstance[i] = true;
					}else if(st == 7 || st == 10){
						can_delete[i] = true;
						can_firstInstance[i] = true;
					}else if(st == 9){
						can_applicationUpdate[i] = true;
						can_applicationDelete[i] = true;
					}
				}
				if(check_can_op(can_delete))
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
				if(check_can_op(can_firstInstance))
					ToolsBarObj.ToolsBar_Enabled("toolbar_firstInstance");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_firstInstance");
				if(check_can_op(can_applicationUpdate))
					ToolsBarObj.ToolsBar_Enabled("toolbar_applicationUpdate");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_applicationUpdate");
				if(check_can_op(can_applicationDelete))
					ToolsBarObj.ToolsBar_Enabled("toolbar_applicationDelete");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_applicationDelete");
					
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_update");
				ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
			}else if(pp.length==1){
				st = status[0];
				if(st == 0){//已创建//状态为0才能进行修改、删除、提交审核
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_firstInstance");
				}else if(st == 7 || st == 10){
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_firstInstance");
				}else if(st == 9){
					ToolsBarObj.ToolsBar_Enabled("toolbar_applicationUpdate");
					ToolsBarObj.ToolsBar_Enabled("toolbar_applicationDelete");
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_view");
				ToolsBarObj.ToolsBar_Enabled("toolbar_beizhu");
			}else{
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_update");
				ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
				ToolsBarObj.ToolsBar_Disabled("toolbar_firstInstance");
				ToolsBarObj.ToolsBar_Disabled("toolbar_applicationUpdate");
				ToolsBarObj.ToolsBar_Disabled("toolbar_applicationDelete");
				ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
			}
		}
		
		//申请修改
		function applicationUpdateDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			apply_update(pp.toString());
		}
		//申请删除
		function applicationDeleteDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			apply_del(pp.toString());
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
		//修改
		function updateDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			update(pp.toString());
		}
		//删除
		function deleteDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			del(pp.toString());
		}
		//个人提交审核
		function firstInstanceDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			commitBySelf(pp.toString());
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
				if(pp.length>0) {
					pp=[];
					status = [];
				}
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
					<td height="30" valign="middle">
							<s:form action="listMyKledge.action" method="post" name="kledge" theme="simple">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<input type="hidden" name="kledge.id" id="kledge.id" />
								<input type="hidden" name="ids" id="ids" />
								<input type="hidden" name="listType" id="listType" />
							</s:form>
						<s:if test="kledges.size==0"><span style="margin-top:10px; font-size:14px; margin-left:5px;">暂无我添加的知识</span></s:if>
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
										<th width="100" height="30" align="center" >
										状态							</th>
									
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
									  	<td  height="30" align="center" >
											<s:property value="status_" />
									  	</td>
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
		<script type="text/javascript">
			//查看
			function view(id){
				document.getElementById("kledge.id").value = id;
				document.getElementById("listType").value = 1;
				kledge.action = "viewKledge.action";
				kledge.submit();
			}
			//修改Init
			function update(id){
				document.getElementById("kledge.id").value = id;
				document.getElementById("listType").value = 1;
				kledge.action = "updateKledgeInit.action";
				kledge.submit();
			}
			//删除
			function del(id){
				if(window.confirm("确认删除?")){
					document.getElementById("ids").value = id;
					document.getElementById("listType").value = 1;
					kledge.action = "deleteKledge.action";
					kledge.submit();
				}
			}
			//申请修改
			function apply_update(ids){//alert(id);
				if(window.confirm("确认申请修改?")){
					document.getElementById("ids").value=ids;
					document.getElementById("listType").value = 1;
					kledge.action="myaddApplyUpate.action";
					kledge.submit();
				}
			}
			//申请删除
			function apply_del(ids){//alert(id);
				if(window.confirm("确认申请删除?")){
					document.getElementById("ids").value=ids;
					document.getElementById("listType").value = 1;
					kledge.action="myaddApplyDel.action";
					kledge.submit();
				}
			}
			//个人提交审核
			function commitBySelf(ids){
				if(window.confirm("确认提交审核？")){
					document.getElementById("ids").value=ids;
					document.getElementById("listType").value = 1;
					kledge.action="commitBySelf.action";
					kledge.submit();
				}
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
			<wysLib:page></wysLib:page>
		</div>
		<script type="text/javascript">
			
		</script>
	
	</body>
</HTML>
										   