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
		<TITLE>数据查看</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="stylesheet" type="text/css" href="admin/etcperfect/template/${tablename}/${tablename}.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			 function doSubmit(){
			 if($("#theme").val()==""){
         		alert("联系主题不能为空！");
         		return false;
         	}
         	if($("#content").val()==""){
         		alert("联系内容不能为空！");
         		return false;
         	}
			 
         	var stuffArray=$("#stuff").find("input");
         	for(var i=0;i<stuffArray.length;i++){
         		//if(stuffArray[i].name=="linetrainrecord_stuff.title"){
         			if(stuffArray[i].value==""){
         				alert("附件名称和附件不能为空！");
         				return false;
         			}
         			if(stuffArray[i].name=="myFile"&&stuffArray[i].value.indexOf(".")!=-1){
         				//判断是否exe
         				var fileExName=stuffArray[i].value.substring(stuffArray[i].value.indexOf("."),stuffArray[i].value.length);
         				//alert(fileExName);
         				if(fileExName==".exe"){
         					alert("请不要上传.exe文件!");
         					return false;
         				}
         			}
         		//}
         	}
         	return true;
         }
			
			function setid(i)
			{
				//alert(i);
			}
			
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
			}
			
		function FCKeditor_OnComplete(editorInstance)
		{
		    //editorInstance.Commands.GetCommand('Source').Execute();  //执行“源代码”命令
		    editorInstance.ToolbarSet.Collapse();  //隐藏工具栏
		    
		    editorInstance.EditorWindow.parent.document.getElementById("xExpanded").style.display = "none";
        	editorInstance.EditorWindow.parent.document.getElementById("xCollapsed").style.display = "none";
        	editorInstance.EditorWindow.blur();
        	
		    
		}
		
			
		</script>
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} 
		</style>
	</HEAD>
	<body onLoad="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:NavigationForViewAndUpdate/>
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:if test="currentUser !=null && currentUser.user_add == 1">
			<table width='100%' cellpadding='1' align='center' cellspacing='1'>
			<caption>用户信息与部门</caption>
			<jsp:include page="userinfo.jsp" flush="true" />
			</table>
		</s:if>
		<table width='100%' cellpadding='1' align='center' cellspacing='1' style="display:none;" id="userinfo">
			<caption>签名信息</caption>
		</table>
		<s:form action="viewContactTags.action" method="post" theme="simple" onsubmit="return doSubmit();" name = "form_list_client">
			<input type="hidden" name="id" id="contactid"/>
			<div style="margin-top: 0px; text-align: center;">
				<table width='100%' cellpadding='1' align='center' cellspacing='1' id='before'>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<!-- 
						说明:
							1标签的iname参数是表内字段名称；
							2标签名为TBHTMLName的是表内该字段的中文名
							3标签名为TBHTML_VIEW的是表内该字段显示的HTML
							4如果想在table、tr、td中添加样式，可直接写
							5如果现在显示成HTML中添加样式，就需要自己制作.css文件；
								css如何制作见下载的demo.css
							例如：   		  
		
							下面是我们插入的table 
							<tr>
								<td align="center">
									<wysLib:TBHTMLName iname="xxx"></wysLib:TBHTMLName>
								</td>
								<td>
									<wysLib:TBHTML_VIEW iname="xxx"></wysLib:TBHTML_VIEW>
								</td>
							</tr>
		
							那么在页面上显示如下：
		
							<tr>
								<td align="center">
									xxx
								</td>
								<td>
									<input type="text" name="xxx" id="xxx" />
								</td>
							</tr>
		
							注意： 请不要在  内容区域开始  ~ 内容区域结束 之外做任何修改。
					--> 
					<!-- 内容区域开始-->
						
						
					<!-- 内容区域结束 -->
					</tbody>
				</table>
			</div>
			<br>
		</s:form>
		<div style="margin-top: 0px; text-align: center;">
			<wysLib:showlistRelate />
		</div>
	
	</body>
</HTML>
