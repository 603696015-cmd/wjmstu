<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--显示部门编辑</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#depname").val()==''){
					alert("请填写部门名称！");
					$("#depname").focus();
					return false;
				}
				var bh=document.getElementById("depBh");
				if(bh.value==""){
					alert("请填写部门编号！");
					$("#depBh").focus();
					return false;
				}
				var ts=/^[\d]{1,}$/;
				if(!ts.test(bh.value)){
					alert("部门编号只能是数字！");
					$("#depBh").focus();
					return false;
				}
				<s:if test="department.id!=1">
					var depId=$("input[name='department.parent.id']:checked").val();
					if(depId==undefined){
						alert("请选择上级部门！");
						return false;
					}
				</s:if>
				return window.confirm("确定信息填写无误？");
			}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}

td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
-->
</style>
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑部门信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_deleteInit.action?department.id=<s:property value="department.id" />">删除部门</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_view.action?department.id=<s:property value="department.id" />">显示部门信息</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 0px;">
			<s:form action="dep_alter" method="post" theme="simple"
				name="department_info" id="department_info"
				onsubmit="return _onsubmit();">
				<table border="0" cellpadding="1" width="600px" cellspacing="1">
					<tr>
						<td width="120" height="30" align="right">
							<span class="neededitem">*</span>单位/部门：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield id="depname" name="department.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							详细说明：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textarea name="department.description" cols="45" rows="5" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>上级部门：
						</td>
						<td style="padding-left: 8px;" align="left">
							<!--<label>
											<select name="department.parent.id" id="parentid">
												 wysLib:dep_select selectid="${department.parent.id}" /wysLib:dep_select 
											</select>
										</label>
									-->
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depId = "";
									if (dep.getParent() != null) {
										depId = dep.getParent().getId() + "";
									}
							%>
							<s:if test="department.id==1">
										根节点没有父节点
										<input type="hidden" name="department.parent.id" value="0" />
							</s:if>
							<s:else>
								<wysLib:dep_list_aj href="" itype="ra"
									iname="department.parent.id" rootAble="true"></wysLib:dep_list_aj>
								<script type="text/javascript">
										w0.setValues([new DEP(<s:property value="department.parent.id"/>,<s:property value="department.parent.lid"/>,<s:property value="department.parent.rid"/>)]);
									</script>
								<!--
										 wysLib:dep_list_f rootAble="true" iname="department.parent.id" itype="ra_f" ivalue="<%=depId%>" iid="${department.id}"wysLib:dep_list_f
										 
									-->
							</s:else>
						</td>

					</tr>
					<tr>
						<td align="right">
							联系电话：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.phone" />
							</label>
						</td>
					</tr>
					<!--<tr>
									<td width="120" align="center" >
										管 理 员：
									</td>
									<td style="padding-left:8px;" align="left">
										<label>
											<select id="elu" name="department.manager.id" id="manager">
												<option value="0">
													无指定
												</option>
												<s:iterator value="elUsers">
													<option value="<s:property value="id"/>">
														<s:property value="realname" />
													</option>
												</s:iterator>
											</select>
											<script type="text/javascript">
									function setManager(id){
										var obj = document.getElementById("elu").getElementsByTagName("option");
										for(var i= 0 ;i< obj.length;++i){
											 if(obj[i].value==id){
											 obj[i].selected=true;
											 }
										}
									}
									setManager(<s:property value="department.manager.id"/>);
								</script>
										</label>
									</td>
								</tr>
								-->
					<tr>
						<td align="right">
							地 址：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.address" />
								<s:hidden name="department.manager.id" value="0" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							邮政编码：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.postalcode" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							传 真：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.fax" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							电子邮箱：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.email" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>编号：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.bh" id="depBh" />
								<span style="color: red;"><s:property value="elmessage" />
								</span>
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							可管理人员：
						</td>
						<td style="padding-left: 8px;" align="left">
							<div id="can_op">
								<s:iterator value="department.opusers">
									<span
										style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="float: left;">
											<s:property value="realname" />
										</label> <span class="STYLE1">＊</span> <!--<a
													style="cursor: hand; float: right; width: 14px; height: 14px;"
													href=""
													onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'op');return false;">X</a>-->
									</span>
								</s:iterator>
							</div>
							<!--<a href=""
											onclick="searchUserInit('can_op','department.opusers.id'); return false;">授权</a>-->
						</td>
					</tr>
					<tr>
							<td width="120" height="30" align="right">
									是否二级页面：
							</td>
							<td style="padding-left: 8px;" align="left">
								<label>
									<s:radio list="#{'0':'否','1':'是'}" name="department.issp" value="#request.department.issp"/>
								</label>
							</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
						<strong> 图片： </strong>
						</td>
						<td>
						<s:if test="department.image!= null">
									<img src="<s:property value="department.image"/>" width="240" height="300"> 
							</s:if><s:else> 
								<img
									src="<s:property  escape="false" value="department.image"/>"
									id="cimg_0" width="240" height="300" />
								<SCRIPT type="text/javascript">
									obj = document.getElementById("cimg_0");
									addImgs(obj);
								</SCRIPT> 
							</s:else>	
						</td>
					</tr>
					<tr >
						<td width="120" height="30" align="center" >
							<strong> 修改图片 </strong>
						</td>
						<td>
						<s:textfield name="department.image" id="mainimg" size="60" theme="simple" />
									<a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a>
						</td>	
					</tr>
					<tr>
						<td align="right">
							标题：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.title" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							落款文字：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.luokuanwenzi" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							专注领域：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.lingyu" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							地市：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="department.dishi" />
							</label>
						</td>
					</tr>
					<!-- <tr>
									<td width="120" height="30" align="center" >
										可使用人员：									</td>
									<td style="padding-left:8px;" align="left">
										<div id="can_use">
											<s:iterator value="department.useusers">
												<span
													style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
													<label style="float: left;">
														<s:property value="realname" />
											</label> <span class="STYLE1">＊</span>  -->
					<!--<a
													style="cursor: hand; float: right; width: 14px; height: 14px;"
													href=""
													onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'op');return false;">X</a>-->
					<!-- </span>
											</s:iterator>
							</div> -->
					<!--<a href=""
											onclick="searchUserInit('can_use','department.useusers.id'); return false;">授权</a>-->
					<!-- 	</td>
								</tr> -->
					<tr>
						<td align="right">
							<s:hidden name="department.id"></s:hidden>
						</td>
						<td>

							<table width="100%" height="19" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td>
										&nbsp;

									</td>
									<td width="120">
										<input name="submit" type="submit" class="textbg"
											style="border: none;" value="提交更改" />
									</td>
									<td>
										&nbsp;

									</td>
									<td width="100">
										<a
											href="dep_view.action?department.id=<s:property value="department.id" />"
											class=textbg>取消</a>
									</td>
									<td>
										&nbsp;

									</td>
									<td align="left">
										<a
											href="dep_deleteInit.action?department.id=<s:property value="department.id" />"
											class=textbg>删除部门</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</BODY>
</HTML>
