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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/forum.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<SCRIPT type="text/javascript">
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 300;
				oFCKeditor.Width = 320;
				oFCKeditor.ReplaceTextarea();
			}
		</SCRIPT>
		</HEAD>
	
	<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="内容修改" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">文章修改</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>-->
		
		 <form action="forum_alter.action" method="post">
          <table width="320" height="30" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
            <tr>
              <td height="25" align="center" bgcolor="#F8FCFE" >帖子标题</td>
                <td height="25" bgcolor="#F8FCFE" ><input type="text" size="20" name="forum.title" value="<s:property value="forum.title" />"><input type="submit" value="提交">  
                <s:hidden name="forum.id"></s:hidden> </td>
            </tr>
            <tr>
              <td height="25" align="center" bgcolor="#F8FCFE" >所属版块</td>
                <td height="25" bgcolor="#F8FCFE">
                <!--<input type="hidden" name="forum.fblock.id" value="<s:property value="fblock.id"/>">
                <s:property value="fblock.title"/>
					-->
					<SELECT name="forum.fblock.id">
					
						<s:iterator value="fbtypes" status="fbtst">
									<optgroup label="<s:property value="name" />"><s:property value="name" /> </optgroup>
										<s:iterator value="fblocks" status="fbs" id="fbsid">
											<option <s:if test="forum.fblock.id==#fbsid.id">selected='selected'</s:if> value="<s:property value="#fbsid.id"/>"><s:property value="#fbsid.title" /></option>
										</s:iterator>
									</s:iterator>
					</SELECT>
					</td>
            </tr>
            <tr>
              <td height="25" colspan="2" align="center" bgcolor="#F8FCFE" >帖子描述</td>
            </tr>
          </table>
<textarea id="content" name="forum.description"
								style="width: 320px; height: 500px; visibility: hidden;">${forum.description}</textarea>
				</form>
	
	</body>
</HTML>
