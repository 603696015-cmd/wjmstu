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
		<TITLE>标签管理</TITLE>
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
				<span style="font-weight: bold;">标签管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="label_addInit.action">添加新标签</a>
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
			<table width="86%" align="center" bgColor="#ecedeb" cellSpacing="1"
				cellPadding="2">
				<thead>
					<tr>
						<th>
							标签名称
						</th>
						<th>
							标签代码
						</th>
						<th>
							标签样式
						</th>
						<th>
							模块类型
						</th>
						<th>
							数据类型
						</th>
						<th>
							Hot类型
						</th>
						<th>
							显示条数
						</th>
						<th>
							标题字数
						</th>
						<!--
						 <th>
							内容字数
						</th>
						 -->
						<th>
							显示列数
						</th>
						<th>
							显示类型
						</th>
						<th>
							标签描述
						</th>
						<th>

						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="labelList">
						<tr>
							<td>
								<s:property value="name" />
							</td>
							<td>
								<s:property value="code" />
							</td>
							<td>
								<s:property value="style" />
							</td>
							<td>
								<s:property value="modelType" />
							</td>
							<td>
								<s:property value="modelId" />
							</td>
							<td>
								<s:property value="contentType" />
							</td>
							<td>
								<s:property value="record" />
							</td>
							<td>
								<s:property value="titleLength" />
							</td>
							<!--<td>
								<s:property value="contentLength" />
							</td>
							 -->
							<td>
								<s:property value="row" />
							</td>
							<td>
								<s:property value="viewType" />
							</td>
							<td>
								<s:property value="remark" />
							</td>
							<td>
								<a
									href='label_alterInit.action?label.id=<s:property value="id"/>'>修改</a>
								<a onclick="return confirm('确定删除？');"
									href='label_deleteById.action?label.id=<s:property value="id"/>'>删除</a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
