<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<TITLE>知识终审</TITLE>
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
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示部门</a>';
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
			ToolsBarObj.ToolsBar_Add("toolbar_update","修改","images/newversion/un_view.gif","updateDetail_searchContactTags()");
			ToolsBarObj.ToolsBar_Add("toolbar_delete","删除","images/newversion/un_view.gif","deleteDetail_searchContactTags()");
			ToolsBarObj.ToolsBar_Add("toolbar_pass","通过","images/newversion/un_view.gif","passDetail_searchContactTags()");
			ToolsBarObj.ToolsBar_Add("toolbar_nopass","不通过","images/newversion/un_view.gif","nopassDetail_searchContactTags()");
			ToolsBarObj.ToolsBar_Add("toolbar_allowupdate","允许修改","images/newversion/un_view.gif","allowupdateDetail_searchContactTags()");
			ToolsBarObj.ToolsBar_Add("toolbar_noallowupdate","不允许修改","images/newversion/un_view.gif","noallowupdateDetail_searchContactTags()");
			ToolsBarObj.ToolsBar_Add("toolbar_allowdelete","允许删除","images/newversion/un_view.gif","allowdeleteDetail_searchContactTags()");
			ToolsBarObj.ToolsBar_Add("toolbar_noallowdelete","不允许删除","images/newversion/un_view.gif","noallowdeleteDetail_searchContactTags()");
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
			var st = 0;
			var can_update = [];
			var can_delete = [];
			var can_pass = [];
			var can_nopass = [];
			var can_allowdelete = [];
			var can_noallowdelete = [];
			var can_allowupdate = [];
			var can_noallowupdate = [];
			if(pp.length>1){
				//对于选择了多个checkbox
				for(var i=0;i<pp.length;i++){
					st = status[i];
					if(st == 0){
						can_update[i] = true;
						can_delete[i] = true;
						can_pass[i] = true;
					}else if(st == 2){	
						can_update[i] = true;
						can_delete[i] = true;
						can_allowupdate[i] = true;
						can_noallowupdate[i] = true;
					}else if(st == 3){
						can_update[i] = true;
						can_delete[i] = true;
						can_allowdelete[i] = true;
						can_noallowdelete[i] = true;
					}else if(st == 5 || st == 6 || st == 7 || st == 8){
						can_update[i] = true;
						can_delete[i] = true;
						can_pass[i] = true;
						can_nopass[i] = true;
					}else if(st == 9){
						can_update[i] = true;
						can_delete[i] = true;
						can_nopass[i] = true;
					}else if(st == 10){
						can_update[i] = true;
						can_delete[i] = true;
						can_pass[i] = true;
					}
				}
				if(check_can_op(can_delete))
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
				if(check_can_op(can_allowupdate))
					ToolsBarObj.ToolsBar_Enabled("toolbar_allowdelete");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_allowdelete");
				if(check_can_op(can_noallowupdate))
					ToolsBarObj.ToolsBar_Enabled("toolbar_noallowdelete");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_noallowdelete");
				if(check_can_op(can_allowupdate))
					ToolsBarObj.ToolsBar_Enabled("toolbar_allowupdate");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_allowupdate");
				if(check_can_op(can_noallowupdate))
					ToolsBarObj.ToolsBar_Enabled("toolbar_noallowupdate");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_noallowupdate");
					
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_update");
				ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
			}else if(pp.length == 1){
				st = status[0];
				if(st == 0){
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_pass");
				}else if(st == 2){	
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_allowupdate");
					ToolsBarObj.ToolsBar_Enabled("toolbar_noallowupdate");
				}else if(st == 3){
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_allowdelete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_noallowdelete");
				}else if(st == 5 || st == 6 || st == 7 || st == 8){
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_pass");
					ToolsBarObj.ToolsBar_Enabled("toolbar_nopass");
				}else if(st == 9){
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_nopass");
				}else if(st == 10){
					ToolsBarObj.ToolsBar_Enabled("toolbar_update");
					ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
					ToolsBarObj.ToolsBar_Enabled("toolbar_pass");
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_view");
				ToolsBarObj.ToolsBar_Enabled("toolbar_beizhu");
			}else{
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_update");
				ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
				ToolsBarObj.ToolsBar_Disabled("toolbar_pass");
				ToolsBarObj.ToolsBar_Disabled("toolbar_nopass");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allowdelete");
				ToolsBarObj.ToolsBar_Disabled("toolbar_noallowdelete");
				ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allowupdate");
				ToolsBarObj.ToolsBar_Disabled("toolbar_noallowupdate");
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
		//修改
		function updateDetail_searchContactTags(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			update(pp.toString());
		}
		//删除
		function deleteDetail_searchContactTags(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			del(pp.toString());
		}
		//通过
		function passDetail_searchContactTags(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			finalpass(pp.toString());
		}
		//不通过
		function nopassDetail_searchContactTags(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			finalnopass(pp.toString());
			
		}
		//允许修改
		function allowupdateDetail_searchContactTags(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			allow_update(pp.toString());
		}
		//不允许修改
		function noallowupdateDetail_searchContactTags(){
			pp = getCheckedCheckboxs(pp);
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			noallow_update(pp.toString());
		}
		//允许删除
		function allowdeleteDetail_searchContactTags(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			allow_del(pp.toString());
		}
		//不允许删除
		function noallowdeleteDetail_searchContactTags(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			noallow_del(pp.toString());
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="知识终审" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;display:none;">  
						<%
							Department dep = (Department) request
										.getAttribute("department");
								String depid = dep.getId() + "";
							String url ="kledgeZhongshen.action?department.id=";
						%>
						<wysLib:dep_list_aj rootAble="true"
							href="<%=url%>"
							iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td width="5px;" valign="middle" bgcolor="#FAFCFC" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg"/>
					</td>
					<td valign="top">
							
							<s:form action="listMyKledge.action" method="post" name="kledge" theme="simple">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<input type="hidden" name="kledge.id" id="kledge.id" />
								<input type="hidden" name="ids" id="ids" />
								<input type="hidden" name="listType" id="listType" />
								<input type="hidden" name="auditMark.audit_mark" id="auditMark.audit_mark"/>
								<input type="hidden" name="auditMark.moduleid" value="knowledgemanage" />
							</s:form>
						<s:if test="kledges.size==0"><span style="margin-top:10px;">暂无知识</span></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#EBEBEB">
								<tr>
								<td colspan=20>
								  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td width="95"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示部门</a>
							</div></td>
                                      <td><div id="Div_ToolsBar"></div></td>
                                    </tr>
                                  </table>
								</td>
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
				newsQuery.submit();
			}
		</script>
		<script type="text/javascript">
			//查看
			function view(id){
				document.getElementById("kledge.id").value = id;
				document.getElementById("listType").value = 3;
				kledge.action = "viewKledge.action";
				kledge.submit();
			}
			//修改Init
			function update(id){
				document.getElementById("kledge.id").value = id;
				document.getElementById("listType").value = 3;
				kledge.action = "updateKledgeInit.action";
				kledge.submit();
			}
			//删除
			function del(id){
				if(window.confirm("确认删除?")){
					document.getElementById("ids").value = id;
					document.getElementById("listType").value = 3;
					kledge.action = "deleteKledge.action";
					kledge.submit();
				}
			}
			//终审通过
			function finalpass(ids){
					width=500;
					height=400;
					var url = "fieldAuditMark.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("auditMark.audit_mark").value=rv;
					}
			
				document.getElementById("ids").value=ids;
				kledge.action="verifypassFinal.action";
				
				if(window.confirm("确认提交？")){
					if(document.getElementById("auditMark.audit_mark").value == null || 
						document.getElementById("auditMark.audit_mark").value == ""){
						alert("请重新填写备注!!!");
						return ;
					}
					document.getElementById("listType").value = 3;
					kledge.submit();
				}
			}
			//终审不通过
			function finalnopass(ids){
				width=500;
					height=400;
					var url = "fieldAuditMark.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("auditMark.audit_mark").value=rv;
					}
			
				document.getElementById("ids").value=ids;
				kledge.action="verifynopassFinal.action";
				
				if(window.confirm("确认提交？")){
					if(document.getElementById("auditMark.audit_mark").value == null || 
						document.getElementById("auditMark.audit_mark").value == ""){
						alert("请重新填写备注!!!");
						return ;
					}
					document.getElementById("listType").value = 3;
					kledge.submit();
				}
			}
			//允许修改
			function allow_update(ids){
				document.getElementById("ids").value=ids;
				document.getElementById("listType").value = 3;
				kledge.action="myaddAllowUpate.action";
				kledge.submit();
			}
			//允许删除
			function allow_del(ids){
				if(window.confirm("确认删除？")){
					document.getElementById("ids").value=ids;
					document.getElementById("listType").value = 3;
					kledge.action="myaddAllowDel.action";
					kledge.submit();
				}
			}
			//不允许修改
			function noallow_update(ids){
				document.getElementById("ids").value=ids;
				document.getElementById("listType").value = 3;
				kledge.action="myaddNoAllowUpate.action";
				kledge.submit();
			}
			//不允许删除
			function noallow_del(ids){
				document.getElementById("ids").value=ids;
				document.getElementById("listType").value = 3;
				kledge.action="myaddNoAllowDel.action";
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
			<wysLib:page></wysLib:page>
		</div>
	
	</body>
</HTML>
										   