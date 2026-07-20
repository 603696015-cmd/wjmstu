<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>五矿发展员工职业发展系统--管理端--</title>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
				var depId=$("input[name='department.parent.id']:checked").val();
				if(depId==undefined){
					alert("请选择上级部门！");
					return false;
				}
				return window.confirm("确定信息填写无误？");
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>

		<!-- 页面 -->
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
				<span style="font-weight: bold;">添加新部门</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_list.action">部门管理</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="dep_add" method="post" theme="simple"
			name="department_info" id="department_info"
			onsubmit="return _onsubmit();">
			<table style="margin-top: 3px;" border="0" align="left"
				cellpadding="1" cellspacing="1" width="100%" bgcolor="#EBEBEB">
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>单位/部门：
					</td>
					<td>
						<label>
							<s:textfield id="depname" name="department.name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="80" align="right">
						详细说明：
					</td>
					<td>
						<label>
							<s:textarea name="department.description" cols="45" rows="5" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						<span class="neededitem">*</span>上级部门：
					</td>
					<td>
						<label>
						<wysLib:dep_list_aj rootAble="true" iname="department.parent.id" itype="ra"></wysLib:dep_list_aj>
							<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>	<!--<select name="department.parent.id" id="parentid">
								 wysLib:dep_select selectid="${department.parent.id}" 
							</select>
						--></label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						联系电话：
					</td>
					<td>
						<label>
							<s:textfield name="department.phone" size="60" />
						</label>
					</td>
				</tr>
				<!--<tr>
					<td height="30" align="left">
						管 理 员：
					</td>
					<td>
						<label>
							<select name="department.manager.id" id="manager">
								<option value="0">
									无指定
								</option>
							</select>
						</label>
					</td>
				</tr>
				-->
				<tr>
					<td height="30" align="right">
						地 址：
					</td>
					<td>
						<label>
							<s:textfield name="department.address" size="60" />
							<s:hidden name="department.manager.id" value="0" />

						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						邮政编码：
					</td>
					<td>
						<label>
							<s:textfield name="department.postalcode" size="6" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						传 真：
					</td>
					<td>
						<label>
							<s:textfield name="department.fax" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						电子邮箱：
					</td>
					<td>
						<label>
							<s:textfield name="department.email" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						<span class="neededitem">*</span>编号：
					</td>
					<td>
						<label>
							<s:textfield name="department.bh" id="depBh" />
							<span style="color: red;"><s:property value="elmessage" />
							</span>
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						是否二级页面：
					</td>
					<td>
						<label>
							<s:radio list="#{'0':'否','1':'是'}" name="department.issp" value="0"/>
							<span style="color: red;"><s:property value="elmessage" />
							</span>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
					<strong> 图片：</strong>
					</td>
					<td>
					<input type="text" name="department.image" size="60" value="" id="mainimg"/>
								<a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						标题：
					</td>
					<td>
						<label>
							<s:textfield name="department.title" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						落款文字：
					</td>
					<td>
						<label>
							<s:textfield name="department.luokuanwenzi" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						专注领域：
					</td>
					<td>
						
							<s:select name="department.lingyu" cssClass="g-select"
								list="jingzhongs" listKey="id" listValue="basevalue" />
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						地市：
					</td>
					<td>
						
							<s:select name="department.dishi" cssClass="g-select"
								list="dishis" listKey="id" listValue="basevalue" />
					</td>
				</tr>
				<tr>
					<td height="50" align="center">
					</td>
					<td>
						<input name="submit" type="submit" class="textbg4" value="添加" />
							<input type="button" class="textbg4" style="width:100px;" onClick="document.location='dep_list.action'" value="返回列表" />
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>