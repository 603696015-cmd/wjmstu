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
		<TITLE>我的图书管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<form action="newsManage_list.action" method="post" name="nmList">
			<s:hidden name="isOk" value="1"/>
			<s:hidden name="news.id" id="newsId" />
			<s:hidden name="newsOp" id="newsOp" />
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="图书列表" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
					<wysLib:testbooktypeTree rootAble="true" href="bookinfo_listview.action?btype.id=" ></wysLib:testbooktypeTree></td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
				  <td valign="top">
		<s:form action="bookinfo_alllistview.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="bookinfo.id" id="btype.id" ></s:hidden>
				<s:hidden name="delestatus" id="dstatus"></s:hidden>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="bookids" id="bookids"></s:hidden>
				<tr>
				
				   <td width="120" align="right">  书	名：</td>
			      <td width="190">&nbsp;&nbsp;<s:textfield name="bookinfo.name" /></td>
				      <td width="90" align="right">推荐属性： 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->	              </td>
				 
				    <td width="190">&nbsp;&nbsp;<s:select theme="simple"  headerValue="全部" headerKey=""
									list="#{1:'未推荐',2:'推荐'}"
									name="bookinfo.recommend" value="bookinfo.recommend" /></td>
									<td>&nbsp;&nbsp;发布者姓名
									  <s:textfield name="bookinfo.user.realname" /></td>
				</tr>
				<tr>
				   <td align="right">修改时间  开始时间：</td>
				     <td width="190"> 
					&nbsp;&nbsp;<input name='start'
						value="<s:date name="start" format="yyyy-MM-dd"/>"
						onclick='setday(this)' readonly/>
				  </td>
				       <td width="90" align="right">结束时间：</td>
				         <td width="190">
				         	&nbsp;&nbsp;<input name='end'value="<s:date name="end" format="yyyy-MM-dd"/>" onclick='setday(this)' readonly/>
				  </td>
				         <td colspan="2">
				           	 <input id="find" name="find" type="button" value="搜索" onClick="newsSubmit();" style="margin-left:5px;" >
				         </td>
				</tr>
		  </table>
			</s:form>
						<s:if test="listb.size==0"><h3 align="center" style="margin-top:10px;">没有书籍信息</h3></s:if>
					  <s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
								  <th width="30" align="center" >&nbsp;</th>
									<th width="260" height="30" align="center" >
										书名									</th>
									<th width="100" height="30" align="center" >
										修改时间									</th>
									<th width="90" height="30" align="center" >
										发布者姓名									</th>
									<th width="80" height="30" align="center" >
										发布者所部门								</th>
									<th width="70" height="30" align="center" >
										点击数									</th>
									<th width="70" height="30" align="center" >
										推荐属性								</th>
									
									<th width="140" >	操作								</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="listb">
									<tr>
									  <td width="30" align="left" style="padding-left:8px;color:blue;"> <input type="checkbox" value="<s:property value="id"/>" name="id">	</td>
								<td height="30" style="padding-left:8px;color:blue;" align="left">
							    					<s:property value="name" />	       </td>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
										<s:date name="upddate" format="yyyy-MM-dd HH:mm" />	  </td>
										<td height="30" align="center" >
										<s:property value="user.realname" />											</td>
										<td height="30" align="center" >
										<s:property value="dename" />	
									  </td>
										<td width="70" height="30" align="center" >
									  <s:property value="click" />											</td>
										<td height="30" align="center" >
										<s:property value="Recommendname" />												</td>
										<td width="140" height="30" align="center" >
										 <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>);"  class="textbg4">删除</a> 
											 <a style="cursor:pointer;"  href="bookinfo_view.action?bookinfo.id=<s:property value="id"/>&delestatus=2"  class="textbg4">浏览</a>
											 <a href="bookinfo_updinit.action?bookinfo.id=<s:property value="id"/>&delestatus=2"  class="textbg4">修改</a> 
											<s:if test="statuse==1">
												 <a style="cursor:pointer;"  onClick="sh2(<s:property value="id"/>);"  class="textbg4">通过</a>											</s:if>
											<s:if test="statuse==2">
									 <!-- <a style="cursor:pointer;"  onClick="sh3(<s:property value="id"/>);"  class="textbg6">不通过</a>	-->										</s:if>														  </td>
										
									</tr>
								</s:iterator></tbody>
					    </table>
					</s:else>
					</td>
				</tr>
			</table>
			<%-- 
			<form action="newsManage_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</form>
			 --%>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				assignSearch_assignment.submit();
			}
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				assignSearch_assignment.submit();
			}
			function sh(id){
									if(window.confirm('确定删除？')){
									    document.getElementById("btype.id").value=id;
									    document.getElementById("dstatus").value=2; 
									 	assignSearch_assignment.action="bookinfo_dele.action";
									 	assignSearch_assignment.submit();
								 	}
								} 
								
								function sh2(id){
									if(window.confirm('确定通过？')){
									    document.getElementById("btype.id").value=id; 
									    document.getElementById("dstatus").value=3; 
									 	assignSearch_assignment.action="bookinfo_dele.action";
									 	assignSearch_assignment.submit();
								 	}
								} 
								function sh3(id){
									if(window.confirm('确定不通过？')){
									    document.getElementById("btype.id").value=id; 
									 	document.getElementById("dstatus").value=4;
									 	assignSearch_assignment.action="bookinfo_dele.action";
									 	assignSearch_assignment.submit();
								 	}
								} 
					function select_All(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= true;
							}
						}
						function select_Fan(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= !cks[i].checked;
							}
						}
						function select_Bux(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= false;
							}
						}
						function unassign(){
						  if(window.confirm("确定推荐所选图书？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要推荐的图书！");
								  return ;
							    }
							  var userids = document.getElementById("bookids");
						      userids.value=billIDs;
							  assignSearch_assignment.action="bookinfo_tuijian.action";
							  assignSearch_assignment.submit();
							}
						}	
						function unassign2(){
						  if(window.confirm("确定审核通过所选图书？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要审核通过的图书！");
								  return ;
							    }
							  var userids = document.getElementById("bookids");
						      userids.value=billIDs;
							  assignSearch_assignment.action="bookinfo_check.action";
							  assignSearch_assignment.submit();
							}
						}	
					function unassign3(){
						  if(window.confirm("确定不推荐所选图书？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要不推荐的图书！");
								  return ;
							    }
							  var userids = document.getElementById("bookids");
						      userids.value=billIDs;
							  assignSearch_assignment.action="bookinfo_notuijian.action";
							  assignSearch_assignment.submit();
							}
						}
						function unassign4(){
						  if(window.confirm("确定审核不通过所选图书？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要审核通过的图书！");
								  return ;
							    }
							  var userids = document.getElementById("bookids");
						      userids.value=billIDs;
							  assignSearch_assignment.action="bookinfo_check2.action";
							  assignSearch_assignment.submit();
							}
						}			
		</script>
			<wysLib:page></wysLib:page>
			<a href="javascript:select_All()" />全选</a>
					<a href="javascript:select_Fan()" />反选</a>
					<a href="javascript:select_Bux()" />全不选</a>
						<br>
			<input value="推荐" type="button" onClick="unassign()">
			<input value="不推荐" type="button" onClick="unassign3()">
			<input value="通过" type="button" onClick="unassign2()">
			<input value="不通过" type="button" onClick="unassign4()">
		</div>
	
	</body>
</HTML>
										   