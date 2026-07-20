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
		<base href="<%=basePath%>" target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
		function clickcheckbox(){
		}
		
		function copyDetail(){
			//course_copy.action?copy=1&course.id=<s:property value="id" />
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp.toString();
			window.location.href = "course_copy.action?copy=1&course.id="+value;
		}
		
		function editDetail(){
			//course_alterInit.action?course.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "course_alterInit.action?course.id="+value;
		}
		
		function yulanDetail(){
			//course_preview.action?course.id=<s:property value="id"/>   _blank
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
		
		function viewDetail(){
			//course_view.action?course.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp.toString();
			window.location.href = "course_view.action?course.id="+value;
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
		<script type="text/javascript">
			if('${elmessage}' != ""){
				alert('${elmessage}');
			}
			
			
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
			var idandtitle = new Array();
			function queding(){
				var cks= document.getElementsByName("uid");
				var m =0;
				for(var i = 0 ; i < cks.length; i++){
					if(cks[i].checked){
						idandtitle[m]=cks[i].value;
						m++;
					}
				}
			
				window.returnValue = idandtitle;
				window.close();
			}
		</script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
.textbg4{margin-top: 2px;}
.textbg6{margin-top: 2px;}
</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>

	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td">
						<wysLib:ctypeTree rootAble="true"
							href="word_course_list.action?ctype.id="></wysLib:ctypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg"/>
					</td>
					<td valign="top">
						<s:if test="courses.size==0">没有找到符合条件的课程<br />
						</s:if>
						<s:else>

								<table width="100%" align="center" cellpadding="2"
									cellspacing="1">
									<tr>
										<td colspan=20>
                                        <table width="100%" border="0" cellpadding="0" cellspacing="1"  bgcolor="#D1E4F5">
  <tr>
    <td width="96" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
							<a href="javascript:showtree(true);" class="textbg5">显示课程类别</a>
			</div></td>
    <td bgcolor="#F8FCFE"><s:form action="word_course_list" name="myclist" theme="simple" method="post">
							<s:hidden name="ctype.id" />
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
				课程名称：<s:textfield name="course.name"></s:textfield>
							<s:submit cssClass="textbg4" value="搜索"></s:submit>
						</s:form></td>
  </tr>
</table>
										<table width="100%" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td></td>
    <td width="675"><div id="Div_ToolsBar"></div></td>
  </tr>
</table>

										</td>
									</tr>
									<tr>
										<th width="20" align="center">
										</th>
										<th width="180" align="center">
											课程名称
										</th>
										<th width="90" align="center">
											课程类别
										</th>
										<th width="70" align="center">
											推荐学分
										</th>
										<th width="110" align="center">
											创建时间
										</th>
										<th width="80" align="center">
											课程类型
										</th>
										<th width="60" align="center">
											时长
										</th>
										<th width="50" align="center">
											章节数
										</th>
										<!--
										<th align="center" >
											开始/结束时间
										</th>
										<th align="center" >
											讲师姓名
										</th>
										-->
										<th width="80" align="center">
											状态
										</th>
										<!-- 
										<th width="140" align="center">
											&nbsp;
										</th>
										 -->
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="courses" status="status">
											<tr>
												<td width="20" align="center">
													<input type="radio" value="<s:property value="id"/>"
														name="uid" onclick='clickcheckbox();'>
												</td>
												<td width="180" height="30"
													style="padding-left: 8px; color: blue;" align="left">
													<s:property value="name" />
												</td>
												<td width="90" align="center">
													<s:property value="ctype.name" />
												</td>
												<td width="70" align="center">
													<s:property value="credit" />
												</td>
												<td width="110" align="center">
													<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td width="80" align="center">
													<s:property value="islinkName" />
												</td>
												<td width="60" align="center">
													<s:property value="during" />
													分钟
												</td>
												<td width="50" align="center">
													<s:property value="cpagesize" />
												</td>
												
												<td width="80" align="center">
													&nbsp;
													<s:property value="validName" />
												</td>
												<input type="hidden" id="status_<s:property value='#status.index'/>" value="<s:property value='status' />"/>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							<wysLib:page></wysLib:page>
						</s:else>&nbsp;&nbsp;
						<a href="javascript:queding()" class="textbg4">确定</a>
					</td>
				</tr>
			</table>
			<script>
			    function deleteFunction(){
			       var checkObj = document.getElementsByName("courses.id");
				   var billIDs = "";
				   for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					}
				   if(billIDs==""){
					  //alert("提示框", "请选择要删除的的记录！");
					  alert("请选择要删除的的记录！");
					  return ;
				   }
				   if(confirm('没有用到的课程会被真删除，确定删除？')){
				      //location = "course_deleteInit.action?ids="+billIDs;
				      alert("ibs="+billIDs)
				      location = "course_del.action?ids="+billIDs;//流程变动，更换了action
				   }
			    }
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
		</div>

		<!-- 内容 -->
	</BODY>
</HTML>
