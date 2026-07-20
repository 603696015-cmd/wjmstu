<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<TITLE>扬州专业技术人员继续教育网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">图书查看</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="book_list.action?pN=0&pS=10">我的图书</a>

			</li>
		</ul>  
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
			<table width="90%" cellpadding="2" align="center" cellspacing="1" >
				<tr>
					<td  align="center" >
						图书名称
					</td>
					<td >
						<label>
							<s:property value="book.title"  />
						</label>
					</td>
				</tr>
				<tr>
					<td  align="center" >
						所属栏目
					</td>
					<td >
						<label>
							<s:property value="book.ntype.name"/>
						</label>
					</td>
				</tr>
					<tr>
					<td  align="center" >
						出版社
					</td>
					<td >
						<label>
							<s:property value="book.pubhouse"/>
						</label>
					</td>
				</tr>
						<tr>
					<td  align="center" >
						作者
					</td>
					<td >
						<label>
							<s:property value="book.writer"/>
						</label>
					</td>
				</tr>
				<tr>
					<td  align="center" >
						出版时间
					</td>
					<td >
						<label>
							<s:date  name="book.pubtime" format="yyyy-MM-dd HH:mm:ss"/>
						</label>
					</td>
				</tr>
				<tr height="500px" valign="top">
					<td align="center"
						>
						图书内容
					</td>
					<td align="center"
						>
						${book.content }
					</td>
				</tr>
			</table>
	
	</body>
</HTML>
