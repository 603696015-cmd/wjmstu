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
		<TITLE>练习管理</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			function searchExampracInit(input_name){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("examprac_doDepInit.action?examprac.id="+input_name+"&x="+Math.random(),null,sFeature);
				 if(rv=="nihao"){
				 	alert("分配成功！！！");
				 	location.href="examprac_depInfo.action?examprac.id="+<s:property value='examprac.id'/>+"&statusValue="+<s:property value='statusValue'/>+"&date="+new Date();
				 }
			}
			function sh_t(id){
				if(window.confirm("确定让它通过审核？"))
					document.location.href="examprac_validpass.action?examprac.id="+id+"&pageResult=2";
			}
			function sh_nt(id){
				if(window.confirm("确定让它不通过审核？"))
					document.location.href="examprac_validunpass.action?examprac.id="+id+"&pageResult=2";
			}
		</script> 
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习分配信息" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="text-align:center;">练习名称：<s:property value="examprac.title" /></div>
		部门总人数：<s:property value="count"/>
		<form action="userGrant.action" method="post" name="myForm"> 
			<wysLib:dep_list_f did="6" itype="OP" rootAble="true" />	<!-- 部门树 -->
			<s:if test="statusValue==1">
			<br />
			<%-- <input type="button" value="分&nbsp;&nbsp;&nbsp;配" onClick="searchExampracInit('<s:property value="examprac.id"/>');return false;" /> --%>
			<a href="javascript:searchExampracInit('<s:property value="examprac.id"/>');" class="textbg6">分配部门</a>
			</s:if>
			<s:if test="statusValue==2">
			<br />
				<a href="javascript:searchExampracInit('<s:property value="examprac.id"/>');" class="textbg6">分配部门</a>
				<a href="javascript:sh_t('<s:property value="examprac.id"/>');" class="textbg4">开 通</a>
				<a href="javascript:sh_nt('<s:property value="examprac.id"/>');" class="textbg6">不开通</a>
			</s:if>
		</form>
	
	</body>
</HTML>