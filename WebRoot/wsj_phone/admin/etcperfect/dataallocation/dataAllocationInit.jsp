<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<TITLE><wysLib:Title  /></TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			var moduleManage_ondemo = "<s:property value='moduleManage.ondemo' />";
			var moduleZDY_addjsp = "<s:property value='moduleZDY.addjsp' />";
			var moduleZDY_updateJsp = "<s:property value='moduleZDY.updatejsp' />";
			var moduleZDY_viewJsp = "<s:property value='moduleZDY.viewjsp' />";
			var actionName = "<s:property value='actionName' />";
			var tbname = "<s:property value='tablename' />";
		</script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示部门</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript" src="js/newversion/dataallocation.js"></script>
		
		<script type="text/javascript">
			function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		searchLog_form.submit();
		 	}
		 	
		 	function fenpei(id){
		 		document.getElementById("dataid").value = id;
		 		ff.submit();
		 	}
		 	
		 	function columnsearch(col)
			{
			//	alert(col);
				document.getElementById("ordercolumn").value=col;
				var sc=document.getElementById("ordersc").value;
				if(sc=="")
				{	
					document.getElementById("ordersc").value="desc";
				}
				if(sc=="desc") document.getElementById("ordersc").value="";
				
				search();
			}
			
			function search()
			{
				document.getElementById("pageNow").value=0;
				searchLog_form.submit();
			}
			
			function show_beizhu(entityid){
					var tablename = "<s:property value='tablename'/>";
					width=800;
					height=600;
					var url = "select_audit_mark_by_entityid.action?id="+entityid+"&tablename="+tablename+"&x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
				}
		</script>
		
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height:30px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:NavigationForZDY  /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: px; text-align: center;">
			<form action="dataAllocationFenpeiInit.action" method="post" name="ff" >
			<s:hidden name="tablename" />
			<s:hidden name="id" id="dataid"></s:hidden>
			</form>
			<s:form action="dataAllocationInit.action" method="post" name="searchLog_form"
				theme="simple">
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="tablename" />
				<s:hidden id="ordersc" name="tags.ordersc" />
				<s:hidden id="ordercolumn" name="tags.ordercolumn"  />
				<table width="100%">
					<tr>
						<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;display:none;">  
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
								String tablename=(String)request.getAttribute("tablename");
								String url ="dataAllocationInit.action?tablename="+tablename+"&department.id=";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="<%=url%>"
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
								
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td width="5px;" valign="middle" bgcolor="#FAFCFC" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg"/>
						</td>
						<td valign="top">
							<div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg">显示部门</a>
							</div>
							<!-- 搜索 -->
							
							
							<!-- 列表 -->
							<wysLib:dataAllocationList type="1"/>
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>