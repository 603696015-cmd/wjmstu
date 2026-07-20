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
		<TITLE>模板管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold;">栏目模板管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
			<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="template_list.action">模板列表</a> 
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="template_addInit.action">添加新模板</a> 
					
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="column_bindTempInit.action">栏目绑定模板</a> 
					<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="index_bindTemp.action">首页绑定模板</a> 
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; margin-left: 10px;">
			<table width="86%" align="center" bgColor="#ecedeb" cellSpacing="1" cellPadding="2">
				<thead>
					<tr> 
						<th>
							栏目名
						</th>
						<th>
							模板名
						</th> 
						<th>
							模板路径
						</th>
						 <th>     
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="columnTmpList">
						<tr> 
							<td>
								<s:if test="columnType =='XW'">
									新闻--->
								</s:if>
								<s:if test="columnType =='ZS'">
									知识--->
								</s:if>
								<s:if test="columnType =='KC'">
									课程--->
								</s:if>
								<s:if test="columnType =='LT'">
									论坛--->
								</s:if>   
								<s:property value="columnName" />
							</td> 
							<td>
								<s:property value="tmpName" />
							</td>  
							<td>
								<s:property value="tmpJspTmp" />
							</td>
							<td> 
								<a href='template_toJsp.action?columnTmp.id=<s:property value="id"/>'>生成页面</a>
								<a href='column_alterTempInit.action?columnTmp.id=<s:property value="id"/>'>修改</a>
								<a onclick="return confirm('确定删除？');" href='column_deleteById.action?columnTmp.id=<s:property value="id"/>'>删除</a> 
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
