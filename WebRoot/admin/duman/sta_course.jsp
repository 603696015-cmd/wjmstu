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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((           this .           sectionRowIndex %           2 ==
		
		         0) ?     
		     "#ffffff" :           "#f4f4f4" )
}
</style>
		<SCRIPT type="text/javascript">
	function sh(id,status){
	    document.getElementById("elclass.id").value=id;
	    document.getElementById("status").value=status; 
	 	if(status==1 && window.confirm("确定创建完成？")){
	 		document.forms.elclass_sh.submit();
	 	} 
	}

</SCRIPT>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="管理培训班课程" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table cellpadding="1" cellspacing="1" align="center" width="100%">
			<tr>
				<td style="font-size: 15px" height="30px;" align="center">
					岗位
					<strong><s:property value="elclass.name" /> </strong>的课程管理
				</td>
			</tr>
			<tr>
				<td style="padding: 0px; margin: 0px;">
					<iframe id="bixiuFrame"
						src="sta_addCourseInit_bx.action?staid=${station.id}&classid=-2" width=100%
						marginwidth="0" marginheight="0" frameborder=0
						onload="this.height=bixiuFrame.document.body.scrollHeight + 20"></iframe>
				</td>
				
			</tr>
			<tr>
				<td style="padding: 0px; margin: 0px;">
					<iframe id="xuanxiuFrame"
						src="sta_addCourseInit_xx.action?staid=${station.id}&classid=-3" width=100%
						height=280 marginwidth="0" marginheight="0" frameborder=0
						onload="this.height=xuanxiuFrame.document.body.scrollHeight + 20"></iframe>
				</td>
			</tr>
			<tr>
				<td align="center">
					<form action="elclass_sh.action?state=1" name="elclass_sh"
						method="post">
						<s:hidden name="elclass.id" id="elclass.id"></s:hidden>
						<s:hidden name="status" id="status"></s:hidden>
						<s:hidden name="sublibs" value="1"></s:hidden>
						<s:hidden name="Return" id="Return" value="elclass_alllist"></s:hidden>
					</form>
					<a href="elclass_alterInit.action?elclass.id=${elclass.id }"
						class=textbg>修改岗位</a>
					<a
						href="elclass_assign2userInit.action?sub_department=1&elclass.id=${elclass.id }"
						class=textbg style="color: red;">分配学员</a>
					<s:if test="elclass.status==0">
						<input type="button" onclick="sh(${elclass.id }, 1);" class=textbg
							style="color: red; border: none" value="创建完成" />
					</s:if>
					<a href="elclass_view_man.action?elclass.id=${elclass.id }&sublibs=1"
						class=textbg>返回岗位详情</a>
				</td>
			</tr>
		</table>
	</body>
</HTML>
