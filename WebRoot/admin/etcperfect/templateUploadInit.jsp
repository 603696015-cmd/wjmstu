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
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>添加客户</title>
		<base href="<%=basePath%>">
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
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			if('${elmessage}'!=""){
				alert('${elmessage}');			
			}
		
			function doClick(uploadType,form_,st){
				var flag = LimitAttach(form_,st,uploadType);
				if(flag == false){return;}
				
				//判断是否覆盖
				var value = "";
				var type = "";
				if(uploadType == 1){
					value = $("#addjsp").text().trim();
					type = "add";
				}else if(uploadType == 2){
					value = $("#updatejsp").text().trim();
					type = "update";
				}else if(uploadType == 3){
					value = $("#viewjsp").text().trim();
					type = "view";
				}else if(uploadType == 4){
					value = $("#cssfile").text().trim();
				}
				
				
				if(uploadType!=4){
					//将st文件名改为表名+add、update、view+'.jsp'
					st = "<s:property value='moduleManage.tablename' />" + "_" + type + ".jsp";
					if(value!=""&&value!="还没上传"){
						if(st == value){
							if(window.confirm("已存在同名文件,是否覆盖?")){
								document.getElementById("uploadType").value = uploadType;
								uploadform.submit();
							}
						}else{
							document.getElementById("uploadType").value = uploadType;
							uploadform.submit();
						}
					}else {
						document.getElementById("uploadType").value = uploadType;
						uploadform.submit();
					}
				}else{
					if(value!=""&&value!="还没上传"){
						st = "<s:property value='moduleManage.tablename' />"  + ".css";
						if(st == value){
							if(window.confirm("已存在同名文件,是否覆盖?")){
								document.getElementById("uploadType").value = uploadType;
								uploadform.submit();
							}
						}else{
							document.getElementById("uploadType").value = uploadType;
							uploadform.submit();
						}
					}else {
						document.getElementById("uploadType").value = uploadType;
						uploadform.submit();
					}
				}
				
			}
		</script>

		<SCRIPT LANGUAGE="JavaScript">
			function LimitAttach(form, file,uploadType) {
				
				var extArray ;
				if(uploadType!=4){
					extArray = new Array(".jsp");
				}else{
					extArray = new Array(".css")
				}
				allowSubmit = false;
				if (!file) {
					alert("您还未选择文件,请先选择!!!");
					return allowSubmit;
				}
				while (file.indexOf("\\") != -1)
					file = file.slice(file.indexOf("\\") + 1);
					ext = file.slice(file.indexOf(".")).toLowerCase();
					for (var i = 0; i < extArray.length; i++) {
						if (extArray[i] == ext) { 
							allowSubmit = true; 
							break; 
						}
					}
					if (allowSubmit) {return allowSubmit;}
					else{
						alert("只能上传:  " 
							+ (extArray.join("  ")) +"的文件"+ "\n请重新选择文件"
							+ "再上传");
						return allowSubmit;
					}
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<span>首页</span>&nbsp;>>&nbsp;
					<span>系统管理</span>&nbsp;>>&nbsp;
					<span>模块复制</span>&nbsp;>>&nbsp;
					<span>上传模板</span>
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<div>
			模块名称：
			<s:property value="moduleManage.modulename" />
			<br>
			
			模板JSP下载：
			<a href="TB_Demo_downloadInit.action?fileName=demo_add.jsp" target="_blank" style="color:red">添加页模板下载</a>
			<a href="TB_Demo_downloadInit.action?fileName=demo_update.jsp" target="_blank" style="color:red">修改页模板下载</a>
			<a href="TB_Demo_downloadInit.action?fileName=demo_view.jsp" target="_blank" style="color:red">查看页模板下载</a>
			<br>
			模板CSS下载：
			<a href="TB_Demo_downloadInit.action?fileName=demo.css" target="_blank" style="color:red">样式下载</a>
			<br>
			<!-- 表内列名、标签、名称等说明下载：
			<a href="tableinfo_downloadInit.action?moduleManage.tablename=<s:property value='moduleManage.tablename' />&uploadType=1"  style="color:red">添加页说明Excel下载</a>
			<a href="tableinfo_downloadInit.action?moduleManage.tablename=<s:property value='moduleManage.tablename' />&uploadType=2"  style="color:red">修改页说明Excel下载</a>
			<a href="tableinfo_downloadInit.action?moduleManage.tablename=<s:property value='moduleManage.tablename' />&uploadType=3"  style="color:red">查看页说明Excel下载</a>
			<br> -->
			HTML及标签下载(指该模块<span style='color:red'>基本</span>做好的HTML，下载后粘贴到模板的指定位置即可)：
			<a href="userMakeHTML_downloadInit.action?moduleManage.tablename=<s:property value='moduleManage.tablename' />&uploadType=1"  style="color:red">添加页HTML及标签下载</a>
			<a href="userMakeHTML_downloadInit.action?moduleManage.tablename=<s:property value='moduleManage.tablename' />&uploadType=2"  style="color:red">修改页HTML及标签下载</a>
			<a href="userMakeHTML_downloadInit.action?moduleManage.tablename=<s:property value='moduleManage.tablename' />&uploadType=3"  style="color:red">查看页HTML及标签下载</a>
			<br>
			<!-- 文件存放路径：
			<span style="color:blue">
				admin/etcperfect/template/<s:property value='moduleManage.tablename' />
			</span>
			<br> -->
			
			添加页面：
			<span style="color:red" id="addjsp">
			<s:if test="moduleZDY.addjsp!=null&&moduleZDY.addjsp!=''">
				<s:property value="moduleZDY.addjsp" />
			</s:if>
			<s:else>还没上传</s:else>
			</span>
			<br>
			修改页面：
			<span style="color:red" id="updatejsp">
			<s:if test="moduleZDY.updatejsp!=null&&moduleZDY.updatejsp!=''">
				<s:property value="moduleZDY.updatejsp" />
			</s:if>
			<s:else>还没上传</s:else>
			</span>
			<br>
			查看页面：
			<span style="color:red" id="viewjsp">
			<s:if test="moduleZDY.viewjsp!=null&&moduleZDY.viewjsp!=''">
				<s:property value="moduleZDY.viewjsp" />
			</s:if>
			<s:else>还没上传</s:else>
			</span>
			<br>
			CSS样式文件：
			<span style="color:red" id="cssfile">
			<s:if test="moduleZDY.cssfile!=null&&moduleZDY.cssfile!=''">
				<s:property value="moduleZDY.cssfile" />
			</s:if>
			<s:else>还没上传</s:else>
			</span>
			<br>

			<form action="templateUpload.action" method="post"
				enctype="multipart/form-data" 
				name="uploadform">
				<input type="hidden" name="uploadType" id="uploadType" />
				<s:hidden name="moduleManage.id" />
				<input type="hidden" name="moduleManage.tablename" value="<s:property value='moduleManage.tablename' />" />
				<table border="0" cellspacing="1" cellpadding="5">
					<tr>
						<td align="center">模板文件上传</td>
						<td colspan=2>
							<s:file name="st" theme="simple" id="st" />
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="上传添加页面" onclick="doClick(1,this.form,this.form.st.value)" />
						</td>

						<td>
							<input type="button" value="上传修改页面" onclick="doClick(2,this.form,this.form.st.value)" />
						</td>

						<td>
							<input type="button" value="上传查看页面" onclick="doClick(3,this.form,this.form.st.value)" />
						</td>
					</tr>
					<tr>
						<td align="center">样式文件上传</td>
						<td colspan=2>
							<s:file name="st1" theme="simple" id="st1" />
						</td>
					</tr>
					<tr>
						<td colspan=3>
							<input type="button" value="上传样式文件" onclick="doClick(4,this.form,this.form.st1.value)" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>