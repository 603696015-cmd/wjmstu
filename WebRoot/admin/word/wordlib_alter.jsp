<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%@page import="com.sopia.wordman.entities.Word"%>
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
		<TITLE>考场类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function _onsubmit(){
			if(!$.trim($("#name").val()).length > 0){
				alert("词汇类别名称不要为空");
				$("#name").focus();
				return false;
			}
			var qlibId=$("input[name='word.parent.id']:checked").val();
			var libId="<s:property value="word.id" />";
			if(qlibId==undefined&&libId!=1){
				alert("请选择词汇类别！");
				return false;
			}
		}
		function searchUserInit(comp){
			     width=800;
				 height=450;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("word_course_list.action?sub_department=1&x="+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择用户！');
				 }else{
				 	if(rv[0]<=0)  	alert('您没有选择用户！');
				 	else
				 	$.post("mess_getCourseInfoJson.action", {
						"course.id":rv[0],
						"x":Math.random
						}, 
						function (data) {
							var dataObj=eval("("+data+")");
							document.getElementById("t_id").value=dataObj.course.id;
							document.getElementById("t_name").value=dataObj.course.name;
							document.getElementById("t_hname").value=dataObj.course.name;
						}); 
				 }
			} 
		</script>
		<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
        <style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
		<input type="hidden" id="nopaper" name="nopaper" />
		<input type="hidden" id="nouserinvigilators" name="nouserinvigilators" />
		<input type="hidden" id="nouserappraises" name="nouserappraises" />
		<input type="hidden" id="nouservalids" name="nouservalids" />
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="显示基本信息" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroomlib_view.action?eroomLib.id=<s:property value="eroomLib.id" />">显示考场类别信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">编辑考场类别信息 </span>
			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="wordlib_alter" method="post" name="catalog_info"
				theme="simple" onsubmit="return _onsubmit();">
				<table width="100%" align="left" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>类别名称：
						</td>
						<td >
							<label>
								<s:textfield name="word.name" id="name" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
							类别介绍：
						</td>
						<td >
							<label>
								<s:textarea name="word.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>上级类别：<%
							Word uu = ((Word) request.getAttribute("word"));
								String xx = "0";
								xx = uu == null ? "1" : uu.getParent() == null ? "1" : uu
										.getParent().getId()
										+ "";
						%>
						</td>
						<td >
							<s:if test="word.parent.id==0">
								根节点没有父节点
							</s:if>
							<s:else>
								<label>
									<wysLib:wordsTree did="0" iname="word.parent.id"
										itype="ra_f" ivalue="<%=xx%>" iid="${word.id}"></wysLib:wordsTree>
								</label>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>对应单元：
						</td>
						<td >
							<label> 
								<s:textfield id="t_name" size="40" disabled="true" name="course.name"/>
								<s:hidden name="course.teacherName" id="t_hName"/>
								<s:hidden name="word.courseid" id="t_id"/><input class="textbg6" type="button" onClick="searchUserInit('messUser')" value="查 找">
						
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center" >
							<s:hidden name="word.id"></s:hidden>
						</td>
						<td >
							<input type="submit" style="border: none;color: red;" class="textbg5" value="确认修改">
							<input style="border: none;" onClick="document.location='word_view.action?word.id=${word.id }'" class="textbg5" type="button" value="取   消">
						</td>
					</tr>
			  </table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
