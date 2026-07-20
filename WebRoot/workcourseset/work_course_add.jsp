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
		<TITLE>--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function doSubmit(){
				var basevalue=document.getElementById("basevalue");
				basevalue.value = basevalue.value.replace(/^\s+|\s+$/g,"");//去除2头空格
				if(basevalue.value==""){
					alert("名称不能为空！");
					return false;
				}
				if($("#bh").val()==''){
					alert("编号不要为空");
					return false;
				}
				return true;
			}
			
			
			
			function addCourse(){
			     width=950;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv = window.showModalDialog ('elclass_course_select.action?elclassId=<s:property value='elClass.id'/>&status=0&x='+Math.random(),null,sFeature);
				// alert(rv);
				 if(rv!=undefined&&rv!=""){
					 //var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("workCourseId").value=bh[1];
					 //document.getElementById("danweiName").innerHTML=bh[1];
					 document.getElementById("bhName").value=bh[0];
				 }
			}
			
			function createeditor(obj){
					//alert("dd"+id);
					//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
					//$("#opt_frame"+id).attr("width",500);
					//$("#opt_frame"+id).attr("height",120);
					var oFCKeditor = new FCKeditor(obj.id) ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 120;
					oFCKeditor.Width = 500;
					oFCKeditor.ToolbarSet = "qoption" ;
					oFCKeditor.ReplaceTextarea();
				}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;padding-right: 8px;
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
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
					<wysLib:Navigation ivalue="基础数据修改" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="work_course_add.action" method="post"
			theme="simple" onsubmit="return doSubmit();">
								<s:hidden name="workCourse.work_course_id" id="workCourseId"/>
				
			<table width="100%" cellpadding="2" cellspacing="1">
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>职业类别名称：
					</td>
					<td>
						<s:select name="workCourse.work_type" cssClass="g-select" list="workType"
										listKey="id" listValue="name" />
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>是否启用：
					</td>
					<td>
						<s:radio list="#{1:'是',2:'否'}"
								name="workCourse.isuse" value="1"></s:radio>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>对应课程：
					</td>
					<td>
						&nbsp;<label>
							<s:textfield name="workCourse.Coursename" id="bhName" size="50" />
							<input  onclick="addCourse();return false;" class="textbg6" type="submit" value="选择">
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right">
						显示名称：
					</td>
					<td>
						&nbsp;<s:textarea name="workCourse.work_anniu_name" cols="60" rows="7" onfocus="createeditor(this);"></s:textarea>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right">
						详细说明：
					</td>
					<td>
						&nbsp;<s:textarea name="workCourse.description"   cols="60" rows="7" onfocus="createeditor(this);"></s:textarea>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center">&nbsp;
						

					</td>
					<td>
						&nbsp;<input class="textbg6" type="submit" value="确认修改">
						<input class="textbg6"
							onclick="document.location='work_course_set.action'"
							type="button" value="取消">

					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
