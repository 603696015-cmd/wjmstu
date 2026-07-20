<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.answeringsystem.entities.AnsweringType;"%>
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
		<TITLE>中国食品安全培训网--管理端--显示下拉选项编辑</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#answeringTypeTree.name").val()==''){
					alert("请填写问答类别名称！");
					$("#answeringTypeTree.name").focus();
					return false;
				}
				var bh=document.getElementById("answeringTypeTree.bh");
				if(bh.value==""){
					alert("请填写问答类别编号！");
					$("#answeringTypeTree.bh").focus();
					return false;
				}
				var ts=/^[\d]{1,}$/;
				if(!ts.test(bh.value)){
					alert("问答类别编号只能是数字！");
					$("#answeringTypeTree.bh").focus();
					return false;
				}
				<s:if test="answeringTypeTree.id!=1">
					var depId=$("input[name='answeringTypeTree.parent.id']:checked").val();
					if(depId==undefined){
						alert("请选择上级问答类别！");
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
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
		</ul>
		<div style="margin-top: 0px;">
			<s:form action="alterAnsweringTypeTree.action" method="post" theme="simple"
				name="department_info" id="department_info"
				onsubmit="return _onsubmit();">
				<table border="0" cellpadding="1" width="600px" cellspacing="1">
					<tr>
						<td width="120" height="30" align="right">
							<span class="neededitem">*</span>问答类别名称：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield id="answeringTypeTree.name" name="answeringTypeTree.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							详细说明：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textarea name="answeringTypeTree.description" cols="45" rows="5" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>上级部门：
						</td>
						<td style="padding-left: 8px;" align="left">
							<%
								AnsweringType dep = (AnsweringType) request.getAttribute("answeringTypeTree");
								String depId = "";
								if (dep.getParent() != null) {
									depId = dep.getParent().getId() + "";
								}
							%>
							<s:if test="answeringTypeTree.id==1">
										根节点没有父节点
										<input type="hidden" name="answeringTypeTree.parent.id" value="0" />
							</s:if>
							<s:else>
								<wysLib:answeringTypeTree_list_aj href="" itype="ra"
									iname="answeringTypeTree.parent.id" rootAble="true"></wysLib:answeringTypeTree_list_aj>
								<script type="text/javascript">
										w0.setValues([new DEP(<s:property value="answeringTypeTree.parent.id"/>,<s:property value="answeringTypeTree.parent.lid"/>,<s:property value="answeringTypeTree.parent.rid"/>)]);
									</script>
							</s:else>
						</td>

					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>编号：
						</td>
						<td style="padding-left: 8px;" align="left">
							<label>
								<s:textfield name="answeringTypeTree.bh" id="answeringTypeTree.bh" />
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
								<s:iterator value="answeringTypeTree.opusers">
									<span
										style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="float: left;">
											<s:property value="realname" />
										</label> <span class="STYLE1">＊</span> 
									</span>
								</s:iterator>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<s:hidden name="answeringTypeTree.id"></s:hidden>
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
											href="viewAnsweringTypeTree.action?answeringTypeTree.id=<s:property value="answeringTypeTree.id" />"
											class=textbg>取消</a>
									</td>
									<td>
										&nbsp;

									</td>
									<td align="left">
										<a
											href="deleteAnsweringTypeTreeInit.action?answeringTypeTree.id=<s:property value="answeringTypeTree.id" />"
											class=textbg>删除问答类别</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	
	</body>
</HTML>
