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
<style type="text/css">
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
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="学员列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习人员列表 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'" 
					href="examprac_assign_addinit.action?examprac.id=<s:property value="examprac.id"/>">练习人员添加</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examprac_assigndeplist.action?examprac.id=<s:property value="examprac.id"/>">添加部门</a>
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
			<div>


				<table width="700px" align="center" cellpadding="1" cellspacing="1">
					<tr>
						<th height="30" 
							align="center">
							姓名
						</th>
						<th height="30" align="center">
							账号
						</th>
						<th height="30" align="center">
							部门
						</th>
						<th height="30" align="center">&nbsp;
							

						</th>
					</tr>
					<s:iterator value="elusers">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								align="left">
								<s:property value="realname" />
							</td>
							<td height="30" align="center">
								<s:property value="username" />
							</td>
							<td height="30" align="center">
								<s:property value="department.name" />
							</td>
							<td height="30" align="center">
								<a onClick="return confirm('确定删除该学员的该练习？');"
									href="examprac_assign_delete.action?examprac.id=<s:property value="examprac.id"/>&elUser.id=<s:property value="id"/>">删除</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<div style="text-align: center;">
					<wysLib:page></wysLib:page>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		page_list.submit();
		 	}
		</script>
		<s:form action="examprac_assign_list.action" method="post"
			name="page_list" theme="simple">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="examprac.id" />
		</s:form>
		<!-- examprac_assign_addinit -->
		<div align=center>
			<a
				href="examprac_assign_addlist.action?examprac.id=<s:property value="examprac.id"/>&sub_department=1"
				class=textbg>练习人员添加</a>
			<input type="button"
				onclick="document.location='examprac_list.action'" class="textbg"
				style="border: none;" value="返回练习列表" />

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
