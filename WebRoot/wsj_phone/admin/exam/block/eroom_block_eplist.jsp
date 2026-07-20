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
		<TITLE>考场批次管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			var idandtitle = new Array();
			function queding(){
				var cks= document.getElementsByName("blockids");
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
		</script>
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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场试卷大题列表" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="eroom_block_add" method="post" name="catalog_info"
				theme="simple" onsubmit="return onsubmit_();">
				<table width="400px"  cellpadding="1" cellspacing="1">
					<tr>
						<th width="40" height="30" align="center">
							选择
						</th>
						<th width="220" align="center">
							试卷名称
						</th>
						<th width="120" align="center">
							大题名称
						</th>
						<th width="120" align="center">
							大题类型
						</th>
					</tr>
					<s:iterator value="epblocks">
						<tr>
							<td height="30" align="center">
								<input name="blockids" type="checkbox" value="<s:property value="id"/>=-=<s:property value="title"/>" />
							</td>
							<td align="center">
								<s:property value="examPaper.title" />
							</td>
							<td   align="center">
								<s:property value="title" />
							</td>
							<td  align="center">
								<s:property value="typeName" />
							</td>
						</tr>
					</s:iterator>
				</table>
				<br />
			</s:form>
			<a href="javascript:queding()" class="textbg4">确定</a>
			<a href="javascript:window.close();" class="textbg4">关闭</a><br/>
			<span class="neededitem">*</span>注意：请一次选择指定模块需要的大题，不支持连续添加到模块中
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
