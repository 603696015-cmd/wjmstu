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
		<base href="<%=basePath%>" target="_self">
		<TITLE>人员搜索</TITLE>
		<META http-equiv=Pragma content=no-cache>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<LINK href="member/css.css" type=text/css rel=stylesheet>
		<LINK href="member/css2.css" type=text/css rel=stylesheet>
		<LINK href="css/tscss.css" type=text/css rel=stylesheet>
		<LINK href="css/manage.css" type=text/css rel=stylesheet>
		<LINK href="exam_css/houtai.css" type=text/css rel=stylesheet>
		<SCRIPT type="text/javascript" src="exam_js/message.js"></SCRIPT>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0) ?     "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY
		style="height: 100%; width: 100%; overflow-y: scroll; text-align: center;">
		<!--整个页面的顶部-->
		<!-- 内容 -->
		<table style="margin: 0px;padding: 0px;" width="800" cellpadding="1" cellspacing="1">
			<tr>
				<td valign="top" width="100px" id="tree_list_td">
					<%
						Department dep = (Department) request.getAttribute("department");
						String depid = dep.getId() + "";
					%>
					<wysLib:dep_list_aj rootAble="true"
						href="mess_sendUserlist.action?sub_department=1&elUser.valid2=0&department.id="
						iname="department.idd" ivalue="<%=depid%>" itype="nihao"></wysLib:dep_list_aj>
					<script type="text/javascript">
						w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
					</script>
				</td>
				<td valign="middle" width="5px" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top" width="695px">
					<s:form action="mess_sendUserlist.action" theme="simple" method="post"
						name="acc_list">
						<s:hidden name="pN" id="pageNow" />
						<s:hidden name="pS" />
						<s:hidden name="elUser.email" />
						<s:hidden name="department.id" />
						<div style="text-align: center;">
							角色：
							<select name="elUser.role.id">
								<option value="0">
									请选择
								</option>
								<s:iterator value="roles">
									<option <s:if test="role.id==id">selected='selected'</s:if>
										value="<s:property value="id"/>">
										<s:property value="name" />
									</option>
								</s:iterator>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 用户名：
							<s:textfield name="elUser.username" cssStyle="width:80px;" />
							&nbsp;&nbsp;&nbsp; 姓名：
							<s:textfield name="elUser.realname" cssStyle="width:80px;" />
							&nbsp;&nbsp;&nbsp;
							<s:hidden name="elUser.valid2" value="1" />
							<br/>
							&nbsp;&nbsp;&nbsp; 包含下属部门：
							<label>
								<input type="checkbox" name="sub_department"
									<s:if test="sub_department==1">checked="checked"</s:if>
									id="sub_department" value="1">
							</label> 
							<input type="submit" value="搜 索">
						</div>
					</s:form>
					<table width="100%" id="user_list" align="center" cellpadding="1"
						cellspacing="1" style="font-size: 11">
						<tr>
							<td height="20" align="center" bgcolor="#6699FF">
							</td>
							<td height="20" align="center" bgcolor="#6699FF">
								学号
							</td>
							<td height="20" align="center" bgcolor="#6699FF">
								姓名
							</td>
							<td height="20" align="center" bgcolor="#6699FF">
								单位
							</td>
							<td height="20" align="center" bgcolor="#6699FF">
								部门
							</td>
							<td height="20" align="center" bgcolor="#6699FF">
								角色
							</td>
						</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
							<s:iterator value="elUsers">
								<tr>
									<td height="14" align="center">
										<input type="checkbox" name="uid"
											value="<s:property value="id"/>" />
									</td>
									<td height="14" align="center">
										<s:property value="username" />
									</td>
									<td height="14" align="center">
										<s:property value="realname" />
									</td>
									<td height="14" align="center">
										<s:property value="company.name" />
									</td>
									<td height="14" align="center">
										<s:property value="department.name" />
									</td>
									<td height="14" align="center">
										<s:property value="role.name" />
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			function select_All(){
				//var userlist=document.getElementById("user_list");
				var cks= document.getElementsByName("uid");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			function select_Fan(){
				var cks= document.getElementsByName("uid");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			function select_Bux(){
				var cks= document.getElementsByName("uid");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			var idandtitle = new Array();
			function queding(){
				var cks= document.getElementsByName("uid");
				var m =0;
				for(var i = 0 ; i < cks.length; i++){
					if(cks[i].checked){
						idandtitle[m]=cks[i].value;
						m++;
					}
				}
			
				window.returnValue = idandtitle;
				window.close();
			}
			function page(i){
				document.getElementById("pageNow").value = i;
				acc_list.submit();
			
			}
		</script>
		<div style="font: 13px">
			<wysLib:page></wysLib:page>
			<br>
			<a href="javascript:select_All()" class="textbg4">全选</a>
			<a href="javascript:select_Fan()" class="textbg4">反选</a>
			<a href="javascript:select_Bux()" class="textbg6">全不选</a>
			<a href="javascript:queding()" class="textbg4">确定</a>
			<br /><br />
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
