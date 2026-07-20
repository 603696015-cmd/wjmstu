<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	String elclassId = (String)request.getAttribute("elclassId"); 
	String url = "elclass_course_selectList.action?elclassId="+elclassId +"&status="+request.getAttribute("status")+"&ctype.id=";
%>
<HTML>
	<HEAD>
		<base target="_self"/>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>可选择的课程</TITLE>
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript">

		    window.onunload = onunload_handler;   
		    function onunload_handler(){   
		        	//alert("刷新父窗口");
		       // window.opener.location.reload(); //子窗口刷新父窗口   
		    }   		
		</script>
	</HEAD>
	<BODY style="height: 100%; width: 100%; text-align: center; overflow-x: scroll;">
		<ul class="nav" style="text-align: left;">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的课程</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div  style="text-align: center;width: 100%;overflow-x: scroll;"> 
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px" valign="top" id="tree_list_td">
						<wysLib:ctypeTree rootAble="true" href="<%=url%>"></wysLib:ctypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="elclass_course_selectList" name="myclist" theme="simple">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="elclassId"></s:hidden>
							<s:hidden name="status"></s:hidden>
					课程名称：<s:textfield name="course.name"></s:textfield>
							<s:submit value="搜索"></s:submit>
						</s:form>

						<s:if test="courses.size==0">没有找到符合条件的课程<br/></s:if>
						<s:else>

							<form action="elclass_course_selectList.action" name="myclistadd">
								<table width="900px" align="center" cellpadding="2"
									cellspacing="1" >
									<tr>
										<th align="center" >
										</th>
										<th align="center" >
											课程名称
										</th>
										<th align="center" >
											课程类别
										</th>
										<th align="center" >
											推荐学分
										</th>
										<th align="center" >
											创建时间
										</th>
										<th align="center" >
											创建人
										</th>
											<th align="center" >
											查看
										</th>
										<th align="center" >
											课程类型
										</th>
									</tr> 
									<s:iterator value="courses">
										<tr>
											<td align="center" >
												<input type="checkbox" value="<s:property value="id"/>"
													name="courses.id">
											</td>
											<td align="center" >
												<s:property value="name" />
											</td>
											<td align="center" >
												<s:property value="ctype.name" />
											</td>
											<td align="center" >
												<s:property value="credit" />
											</td>
											<td align="center" >
												<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td align="center" >
												<s:property value="creater.realname" />
											</td>
											<td align="center" >
												<a target="_blank" href="course_preview.action?course.id=<s:property value="id"/>" class="textbg4">预 览</a>
											</td>
											<td align="center" >
												<s:property value="islinkName" />
											</td>
										</tr>
									</s:iterator>
								</table>
							</form>
							<wysLib:page></wysLib:page>
							<input type="submit" value="选  课" class="textbg6"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="selectCourse();">
							
						</s:else><input type="button" value="关  闭" class="textbg6"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="window.close();">
					</td>
				</tr>
			</table>
			<form action="elclass_course_add.action" name="eca" method="post">
				<s:hidden name="ids" id="ids"></s:hidden>
				<s:hidden name="status" id="status"></s:hidden>
				<s:hidden name="elclassId" id="elclassId"></s:hidden>
			</form>

			<script>
			    function selectCourse(){
			       var checkObj = document.getElementsByName("courses.id");
				   var billIDs = "";
				   for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					}
				   if(billIDs==""){
					  alert("请选择要添加的记录！");
					  return ;
				   }
				   if(confirm('确定选择？')){
				   		$("#ids").val(billIDs);
				   		$("#status").val(${status});
				   		$("#elclassId").val(${elclassId});
				   		eca.submit();
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
