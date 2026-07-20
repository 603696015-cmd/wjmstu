<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>培训班类别管理</TITLE>
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
	background-color: expression((           this .           sectionRowIndex %           2 ==
		
		         0) ?     
		     "#ffffff" :           "#f4f4f4" )
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
					<wysLib:Navigation ivalue="培训班类别删除" />
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
			<s:form action="cltype_delete" method="post" name="catalog_info"
				theme="simple">
				<input type="hidden" name="cltype.id"
					value="<s:property value="cltype.id"/>">
				<table width="700" align="center" cellpadding="2" cellspacing="1">
					<tr>
						<td height="30" align="left">
							<label>
								<strong> 确定要删除这个类别吗</strong> &nbsp;&nbsp;
								<s:property value="cltype.name" />
							</label>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td height="30" align="left">
							<strong> 下属培训班与子类别操作</strong>
							<input type="radio" name="class_sourse" checked="checked" value="0">
							并入上级类别 &nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="class_sourse" value="1">
							与本类别同时删除
						</td>
					</tr>
					<tr>
						<td height="50" align="left">
							<input type="submit" class="textbg" style="border: none;color: red" value="确认删除">
							&nbsp;
							<input type="button" onclick="document.location='cltype_view.action?cltype.id=<s:property value="cltype.id" />'" class="textbg" style="border: none;" value="取  消">
						</td>
					</tr>
				</table>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
