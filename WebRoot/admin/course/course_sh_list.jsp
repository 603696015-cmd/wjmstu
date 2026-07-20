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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏课程类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示课程类别</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
			function Obj(pp_,status_){ 
			this.pp=pp_; 
			this.status=status_;
		} 
		//按钮
		var ToolsBarObj = null;
		var pp = [];
		var status = [];
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_yulan","预览","images/newversion/un_view.gif","yulanDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_fenpei","分配","images/newversion/un_view.gif","fenpeiDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_pass","通过","images/newversion/un_view.gif","passDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_nopass","不通过","images/newversion/un_view.gif","nopassDetail()");
		});
		
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			status = obj.status;
			var value = 0;
			var st = 0;
			if(pp.length>1){
				ToolsBarObj.ToolsBar_Disabled("toolbar_yulan");
				ToolsBarObj.ToolsBar_Disabled("toolbar_fenpei");
				ToolsBarObj.ToolsBar_Disabled("toolbar_pass");
				ToolsBarObj.ToolsBar_Disabled("toolbar_nopass");
			}else if(pp.length == 1){
				st= status[0];
				if(st != 5){
					ToolsBarObj.ToolsBar_Enabled("toolbar_yulan");
				}else{
					ToolsBarObj.ToolsBar_Disabled("toolbar_yulan");
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_fenpei");
				ToolsBarObj.ToolsBar_Enabled("toolbar_pass");
				ToolsBarObj.ToolsBar_Enabled("toolbar_nopass");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_yulan");
				ToolsBarObj.ToolsBar_Disabled("toolbar_fenpei");
				ToolsBarObj.ToolsBar_Disabled("toolbar_pass");
				ToolsBarObj.ToolsBar_Disabled("toolbar_nopass");
			}
		}
		
		function yulanDetail(){
			//course_preview.action?course.id=<s:property value="id"/>  _blank
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			width=screen.availWidth * 0.8;
			height=screen.availHeight * 0.8;
			window.open ("course_preview.action?course.id="+value, '课程预览', 'height='+height+', width='+width+', toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no') ;
		}
		
		function fenpeiDetail(){
			//course_assigntoUsersInit.action?course.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "course_assigntoUsersInit.action?course.id="+value;
		}
		
		function passDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 5);
		}
		
		function nopassDetail(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 4);
		}
		
		//获取选中的checkbox
		function getCheckedCheckboxs(pp,status){
			var checkboxs = document.getElementsByName("courses.id");
			if(checkboxs.length>0){
				if(pp.length>0)  pp=[];
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked){
						pp.push(checkboxs[i].value);
						status.push(document.getElementById("status_"+i).value);
					}
				}
			}
			var obj = new Obj(pp,status);
			return obj;
		}
		</script>
		</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程终审</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<form action="course_sh_list.action" name="caform" method="post">
				<!--<s:hidden name="pN" id="pageNow">
				</s:hidden>
				<s:hidden name="pS">
				</s:hidden>
				<s:hidden name="course.name">
				</s:hidden>
				<s:hidden name="course_sourse">
				</s:hidden>-->
		  </form>
		<table width="100%">
				<tr>
					<td valign="top" id="tree_list_td" style="display:none">
						<wysLib:ctypeTree rootAble="true" href="course_sh_list.action?ctype.id="  ></wysLib:ctypeTree>
				  <td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
				  </td>
					<td valign="top">
					<s:form action="course_sh_list" name="myclist" theme="simple">
						<s:hidden name="pN" id = "pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
                        <table width="100%" border="0" cellspacing="1" bgcolor="#D1E4F5">
  <tr>
    <td width="98" rowspan="2" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
							<a href="javascript:showtree(true);" class="textbg5">显示课程类别</a>
						</div></td>
<td width="583" bgcolor="#F8FCFE"><input type="hidden" name="ctype.id" value="<s:property value='ctype.id'/>" />
						课程名称：&nbsp;&nbsp;&nbsp;
							<INPUT  id="name"  name="course.name" /> 
							创建者
							<INPUT  id="creater"  name="course.creater.realname" />
							状态&nbsp;
	   						<select name="course.status_type"  id="status_type" style="WIDTH: 110px" 
	   							onchange="this.value=this.options[this.selectedIndex].value;">
	   							<option value="-1">
									==状态==
								</option>
								
								<s:iterator value="statusMap" id="column">
									<option value="<s:property value="key"/>">
										<s:property value="value"/> 
									</option>
								</s:iterator>
							</select></td>
    <td width="340" rowspan="2" bgcolor="#F8FCFE">&nbsp;<s:submit value="搜索"></s:submit></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">创建时间&nbsp;
							从
							  <input  id="begintime"  name="course.begintime" onClick="setday(this)" />
&nbsp;&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input  id="endtime"  name="course.endtime" onClick="setday(this)" /></td>
  </tr>
                      </table>
</s:form>
						
						
		 <s:if test="courses.size==0">没有找到需要审核的课程</s:if>
		<s:else>
			<table width="100%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<td colspan=20><div id="Div_ToolsBar"></div></td>
				</tr>
				<tr>
					<th width="20" align="center">
										</th>
					<th align="center" >
						课程名称					</th>
					<th width="70" align="center" >
						创建者					</th>
					<!--<th align="center" >
						课程类别
					</th>
					<th align="center" >
						推荐学分
					</th>-->
					<th width="110" align="center" >
						创建时间					</th>
					<!--<th width="150" align="center" >
											开始/结束时间				  </th>-->
										<th width="70" align="center" >
											讲师										</th>
					<th width="100" align="center" >
						状态					</th>
					<!-- <th width="220" align="center" >&nbsp;				  </th> -->
				   <!--  <th width="50" align="center" >&nbsp;</th>
				    <th width="90" align="center" >&nbsp;</th> --> 
				    
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="courses" status="status">
					<tr>
						<td width="20" align="center">
													<input type="checkbox" value="<s:property value="id"/>"
														name="courses.id" onclick='clickcheckbox();'>
												</td>
						<td height="30" align="left" style="padding-left:8px;color:blue;"> 
							<s:property value="name" />						</td>
						<td width="70" align="center" >
							<s:property value="creater.realname" />					  </td>
						<!--<td align="center" >
							<s:property value="ctype.name" />
						</td>
						<td align="center" >
							<s:property value="credit" />
						</td>-->
						<td width="110" align="center" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />					  </td>
							<!--<td width="150" align="center" >
												<s:if test="islink==4"><s:date name="roomstart" format="yyyy-MM-dd HH:mm:ss" />
												-<br/><s:date name="roomend" format="yyyy-MM-dd HH:mm:ss" />
												</s:if>
												<s:else>
												--
												</s:else>
					  </td>-->
							<td width="70" align="center" >
												<s:if test="islink==4">
												  <s:property value="teacherName" />
												</s:if>
												<s:else>
												--												</s:else>					  </td>
						<td width="100" align="center" >
							<s:if test="status != 1">
								<s:property value="validName" />
							</s:if>
							<s:else>
								<div style="color:red;"><s:property value="validName" /></div>
							</s:else> 
						</td>
						<input type="hidden" id="status_<s:property value='#status.index'/>" value="<s:property value='status' />"/>
					 <!-- <td width="50" align="center" >
					  <a target="_parent" href="courseman.action?course.id=<s:property value="id"/>" class="textbg4">修 改</a> </td>	  
					  <td width="50" align="center" ><a href="course_deleteOp.action?course.id=<s:property value="id"/>"
								onclick="return confirm('确定删除该课程？')" class="textbg4">删除</a></td> -->	
						<!-- 
					   <td width="220" align="left" > 
					  	<s:if test="status != 5"> 
					  	 <a target="_blank" href="course_preview.action?course.id=<s:property value="id"/>" class="textbg4">预览</a>  
					  	 <a href="course_assigntoUsersInit.action?course.id=<s:property value="id"/>" class="textbg4">分配</a>
						 <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 5);" class="textbg4">通 过</a> 
						 <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 4);"  class="textbg6">不通过</a>					  	
					    </s:if>
					  	<s:else> 
					  	 <a target="_blank" href="course_preview.action?course.id=<s:property value="id"/>" class="textbg4">预览</a>  
					  	 <a href="course_assigntoUsersInit.action?course.id=<s:property value="id"/>" class="textbg4">分配</a>
						 <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 4);"  class="textbg6">不通过</a>
					  	</s:else>
					  </td>
					   -->
					 <!-- <td width="90" align="center" >  
					  		<a href="course_audit.action?course.id=<s:property value="id"/>&courseAudit.status=1" class="textbg6">审核详情</a> 
					  </td>
					  <td width="50" align="center" > 
					  </td> -->
					</tr>
				</s:iterator> <tbody>
		  </table>
			</s:else> </td></tr></table>
			<!--<form action="myCoursemake_list.action" name="myclist">
				<s:hidden name="pN" id = "pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				
			</form>-->
					 	<form action="course_sh.action" name="course_sh" method="post">
								<s:hidden name="course.id" id="course.id"></s:hidden>
								<s:hidden name="status" id="status"></s:hidden>
								<s:hidden name="Return" id="Return" value="course_sh_list"></s:hidden>
							</form> 	
			<script>
								function sh(id,status){
								    document.getElementById("course.id").value=id;
								    document.getElementById("status").value=status; 
								 	if(status==4 && window.confirm("确定不通过审核？")){
								 		document.forms.course_sh.submit();
								 	} 
								 	if(status==5 && window.confirm("确定通过审核？")){
								 		document.forms.course_sh.submit();
								 	}
								}  
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
			<wysLib:page></wysLib:page>
			
			
		</div>

		<!-- 内容 -->
	</BODY>
</HTML>
