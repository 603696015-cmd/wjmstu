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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript">
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
			}
		</script>
	</HEAD>
	<body
		onload="myload();">
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
				<span style="font-weight: bold;">图书添加</span>
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
		
		<s:form action="book_add" method="post" name="catalog_info"
			theme="simple">
			<table width="90%" cellpadding="2" align="center" cellspacing="1"
				>
				<tr>
					<td height="30" align="center" >
						图书名称					</td>
					<td >
						<label>
							<s:textfield name="book.title" id="name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" >
						图书作者					</td>
					<td >
						<label>
							<s:textfield name="book.writer" id="name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" >
						出版社					</td>
					<td >
						<label>
							<s:textfield name="book.pubhouse" id="name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" >
						出版时间					</td>
					<td >
						<label>
							<s:textfield name="book.pubtime" id="name" size="60" onclick="setday(this)" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" >
						首页新闻图片					</td>
					<td >
						<label>
							<s:textfield name="book.mainimg" id="mainimg" size="60" />
							(<a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a>若是需要在首页显示图片，请填写！)
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" >
						所属栏目					</td>
					<td >
						<label>
							<select name="book.ntype.id" id="parentid">
								<wysLib:bTypeSelect></wysLib:bTypeSelect>
							</select>
						</label>
						<label>
							<input type="submit" value="确认添加">
						</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" >
						图书介绍
					</td>
				</tr>
		  </table>
			<div style="text-align: center; width: 100%">
				<s:textarea name="book.content" id="content" cols="60" rows="7"
					cssStyle="width: 90%; height: 440px;; visibility: hidden;" />
			</div>
			<br>
		</s:form>
	
	</body>
</HTML>
