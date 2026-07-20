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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
			}
		</SCRIPT>
		</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="内容修改" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">文章修改</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		 <form action="forum_alter.action" method="post">
          <table style="padding:8px;" width="100%" height="30" cellpadding="1" cellspacing="1">
            <tr>
              <td height="25" align="center" >帖子标题</td>
                <td height="25" ><input type="text" size="40" name="forum.title" value="<s:property value="forum.title" />"><input type="submit" value="提交">  
                <s:hidden name="forum.id"></s:hidden> </td>
            </tr>
            <tr>
              <td height="25" align="center" >所属版块</td>
                <td height="25">
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
              <td height="25" align="center" colspan="2" >帖子描述</td>
            </tr>
          </table>
				<textarea id="content" name="forum.description"
								style="width: 100%; height: 500px; visibility: hidden;">${forum.description}</textarea>
				</form>
	</body>
</HTML>
