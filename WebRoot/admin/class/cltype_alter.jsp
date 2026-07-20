<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.sopia.classman.entities.ElClType"%>

<%
	String cltypeParentId = ((ElClType) request.getAttribute("cltype"))
			.getParent().getId()
			+ "";
%>

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function deleteUserinfo(obj,id,optype){
				if(window.confirm("确定删除？")){
				cltypeid = <s:property value="cltype.id"/> ;
				$.post("cltype_delete_user.action", {
					"elUser.id":id,
					"cltype.id":cltypeid,
					"optype":optype, 
					"x":Math.random
					}, 
					function (data) {
						alert('删除成功');
					});
				obj.parentNode.parentNode.removeChild(obj.parentNode);
				}
			}
			function doSubmit(){
				var titleObj=document.getElementById("classTypeName");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("培训班类别名称不能为空!");
					titleObj.focus();
					return false;
				}
				var qlibId=$("input[name='cltype.parent.id']:checked").val();
				var libId="<s:property value="cltype.id" />";
				if(qlibId==undefined&&libId!=1){
					alert("请选择培训班类别！");
					return false;
				}
				return true;
			}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班类别修改" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div class="dh3">
			<!--<div class="newpos"></div>
			<div class="newpos2">
				<a href="cltype_list.action">培训班类别管理</a>
				<span style="font-weight: bold;">培训班类别编辑</span>
			</div>-->
		</div>
		<s:form action="cltype_alter" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" value="0" name="cltype.isshared" />
			<table style="margin-top: 4px;" width="700" cellpadding="1"
				cellspacing="1">
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>类别名称：
					</td>
					<td>
						<label>
							<s:textfield name="cltype.name" id="classTypeName" size="80" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right">
						类别介绍：
					</td>
					<td>
						<label>
							<s:textarea name="cltype.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>上级类别：
					</td>
					<td>
						<s:if test="cltype.parent.id==0">
							根节点没有父节点
						</s:if>
						<s:else>
							<label>
								<wysLib:clTypeTree iname="cltype.parent.id" itype="ra_f"
									ivalue="<%=cltypeParentId%>" href="" rootAble="false"
									iid="${cltype.id}" />
								<!-- ivalue="${cltype.parent.id}" -->
							</label>
						</s:else>
					</td>
				</tr>
				<tr>
					<td width="120" align="right">
						可管理人员：
					</td>
					<td>
						<div id="can_op">
							<s:iterator value="cltype.opusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
									</label><span class="STYLE1">＊</span> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<!--	<tr>
						<td width="120" align="center" >
							可使用人员：
						</td>
						<td >
							<div id="can_use">
							<s:iterator value="cltype.useusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
									</label>  !--<a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'CLASS_USE_TYPE');return false;">X</a>-- <span class="STYLE1">＊</span>
								</span>
							</s:iterator>
							</div>
							 !--<a href=""
								onclick="searchUserInit('can_use','cltype.useusers.id'); return false;">授权</a>-- 
						</td>
					</tr>
					-->
				<%-- 
				<tr>
					<td width="120" height="30" align="center" >
						是否为共享节点
					</td>
					<td >
						<label>
							<s:select list="#{0:'不共享',1:'共享'}" name="cltype.isshared"></s:select>
						</label>
					</td>
				</tr>
				--%>
				<tr>
					<td width="120" height="50" align="center">
						&nbsp;
						<s:hidden name="cltype.id"></s:hidden>
					</td>
					<td>
						<input type="submit" class="textbg"
							style="border: none; color: red;" value="确认修改">
						<input type="button"
							onclick="document.location='cltype_view.action?cltype.id=<s:property value="cltype.id" />'"
							class="textbg" style="border: none;" value="取  消">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
