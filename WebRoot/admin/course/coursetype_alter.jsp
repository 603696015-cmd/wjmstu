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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
			<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			ctypeid = <s:property value="ctype.id"/> ;
			$.post("coursetype_delete_user.action", {
				"elUser.id":id,
				"ctype.id":ctypeid,
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
				var titleObj=document.getElementById("cname");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("课程类别名称不能为空!");
					titleObj.focus();
					return false;
				}
				var qlibId=$("input[name='ctype.parent.id']:checked").val();
				var libId="<s:property value="ctype.id" />";
				if(qlibId==undefined&&libId!=1){
					alert("请选择课程类别！");
					return false;
				}
				return true;
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="编辑课程类别" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_view.action?ctype.id=<s:property value="ctype.id" />">显示课程类别信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">编辑课程类别信息 </span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="coursetype_alter" method="post" name="catalog_info"
				theme="simple" onsubmit="return doSubmit();">
				<input type="hidden" value="0" name="ctype.isshared" />
				<table style="margin-top:0px;" width="700" align="left" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>类别名称：
						</td>
						<td >
							<label>
								<s:textfield name="ctype.name" id="cname" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
							类别介绍：
						</td>
						<td >
							<label>
								<s:textarea name="ctype.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
									<span class="neededitem">*</span>上级类别：<%
										String xx = ((com.sopia.courseman.entities.CourseType) request
													.getAttribute("ctype")).getParent().getId()
													+ "";
									%>
						</td>
						<td >
						<s:if test="ctype.parent.id==0">
							根节点没有父节点
						</s:if>
						<s:else>
							<label>
								<wysLib:ctypeTree did="0" iname="ctype.parent.id" ivalue="<%=xx%>"  itype="ra_f" iid="${ctype.id}" ></wysLib:ctypeTree>
						  </label>
						</s:else>
						</td>
					</tr>
						<tr>
						<td width="120" align="right" >
							可管理人员：
						</td>
						<td >
							<div id="can_op">
							<s:iterator value="ctype.opusers">
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
					<!--	<tr>
						<td width="120" align="center" >
							可使用人员：
						</td>
						<td >
							<div id="can_use">
							<s:iterator value="ctype.useusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
								</label> <span class="STYLE1">＊</span> <!--<a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'COURSE_USE_TYPE');return false;">X</a>-- 
								</span>
							</s:iterator>
							</div>
							 !--<a href=""
								onclick="searchUserInit('can_use','ctype.useusers.id'); return false;">授权</a>-- 
						</td>
					</tr>-->
					<%--
					<tr>
						<td width="120" height="30" align="center" >
							是否为共享节点
						</td>
						<td >
							<label>
								<s:select list="#{0:'不共享',1:'共享'}"  name="ctype.isshared" id="ctype.isshared" ></s:select>
							</label>
						</td>
					</tr>
					 --%>
					<tr>
						<td width="120" height="50" align="center" >
							<s:hidden name="ctype.id"></s:hidden>
						</td>
						<td >
							<input type="submit" style="border: none;color: red;" class="textbg" value="确认修改">
							<input type="button" onclick="document.location='coursetype_view.action?ctype.id=<s:property value="ctype.id"/>'" style="border: none;" class="textbg" value="取 消">
						</td>
					</tr>
			  </table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
