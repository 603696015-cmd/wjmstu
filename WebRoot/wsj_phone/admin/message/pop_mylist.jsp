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
		<TITLE>弹窗列表页</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" /> 
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>	
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="弹窗列表" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;"></label>
			<form action="pop_mylist.action" method="post" name="popFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div>
					弹窗标题&nbsp;<input type="text" name="pop.popTitle" value="<s:property value="pop.popTitle"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					发送方式&nbsp;<s:select theme="simple" list="#{-2:'全部',0:'按人员',1:'按部门',2:'按考场',3:'按培训班'}" name="pop.sendmanner" value="pop.sendmanner"/>
					状态&nbsp;<s:select theme="simple" list="#{-2:'全部',0:'无效',1:'有效'}" name="pop.status" value="pop.status"/>
					<input onClick="initPN();" type="button" value="搜索" class="textbg4" />
					
				</div>
			</form>
		  	<s:if test="pops.size==0">
				<br>您当前没有发布弹窗信息
			</s:if>
			<s:else> 
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<th width="40" height="30" align="center" >&nbsp;						</th>
						<th width="200" height="30" align="center" >
							弹窗标题						</th>
						<th height="30" align="center" >
							创建者						</th>
						<th width="120" height="30" align="center" >
							创建时间						</th>
						<th width="150" height="30" align="center" >
							发送方式						</th>
						<th width="150" height="30" align="center" >
							状态							</th>
						<th width="70" height="30" align="center" >&nbsp;</th>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="pops">
						<tr>
							<td width="40" height="30" align="center" >
								<input type="checkbox" id="id" value="<s:property value="id"/>">
						  </td>
							<td width="200" height="30" align="center" >
								<s:property value="popTitle" />
						  </td>
							<td width="150" height="30" align="center" >
								<s:property value="create.realname" />
						  </td>
							 <td width="120" height="30" align="center" >
								<s:property value="createtime" />
						  </td>
							<td width="150" height="30" align="center" >
								<s:property value="sendmannerName" />
						  </td>
							<td width="120" height="30" align="center" >
								<s:property value="statusName" />
						  </td>
							<td align="center" bgcolor="#ECEDEB">
								<a href="javascript:alterPop('<s:property value="id"/>');" class=textbg4>编辑</a>
								<s:if test="status==0">
									<a href="javascript:setPop('<s:property value="id"/>','1');" class=textbg6>设为弹窗</a>
								</s:if>
								<s:else>
									<a href="javascript:setPop('<s:property value="id"/>','0');" class=textbg6>取消弹窗</a>
								</s:else>
								<a href="javascript:del('<s:property value="id"/>');" class=textbg4>删除</a>
							</td>
						</tr>
					</s:iterator></tbody>
			  </table> 
		  </s:else>
				<script> 
						function page(i){
							document.getElementById("pageNow").value=i;
							popFh.submit();
						}
						function initPN(){
							document.getElementById("pageNow").value=0;
							popFh.submit();
						}
						function del(id){
							if(window.confirm("确认删除？")){
								document.getElementById("popId").value=id;
								popForm.action="pop_del.action";
								popForm.submit();
							}
						}
						function setPop(id,status){
							document.getElementById("popId").value=id;
							document.getElementById("popStatus").value=status;
							popForm.action="pop_setStatus.action";
							popForm.submit();
						}
						function alterPop(id){
							document.getElementById("popId").value=id;
							popForm.action="pop_alterInit.action";
							popForm.submit();
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
						function doDelete(){
							if(window.confirm("确认删除？")){
								var checkObj = document.getElementsByName("id");
							    var popIdArray = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(popIdArray!="")popIdArray+=",";
										popIdArray += checkObj[i].value;
									}
								 }
								if(popIdArray==""){
								  alert("请至少选中1条弹窗信息！");
								  return ;
							    }
							    var popIds = document.getElementById("popIds");
							    popIds.value=popIdArray;
							    popForm.action="pop_dels.action";
								popForm.submit();
							}
						}
						function setPops(status){
							var str="";
							if(status==1){
								str="确认设为弹窗？";
							}else{
								str="确认取消弹窗？";
							}
							if(window.confirm(str)){
								var checkObj = document.getElementsByName("id");
							    var popIdArray = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(popIdArray!="")popIdArray+=",";
										popIdArray += checkObj[i].value;
									}
								 }
								if(popIdArray==""){
								  alert("请至少选中1条弹窗信息！");
								  return ;
							    }
							    var popIds = document.getElementById("popIds");
							    popIds.value=popIdArray;
							    document.getElementById("popStatus").value=status;
							    popForm.action="pop_setPops.action";
								popForm.submit();
							}
						}	
				</script>
				<wysLib:page></wysLib:page>
				<div style="margin-top:7px;">
				<a href="javascript:select_All();" class="textbg4">全选</a>
				<a href="javascript:select_Fan();" class="textbg4">反选</a>
				<a href="javascript:select_Bux();" style="width:60px" class="textbg4">全不选</a>
				<a href="javascript:setPops(1);" style="width:80px" class="textbg4">设为弹窗</a>
				<a href="javascript:setPops(0);" style="width:80px" class="textbg4">取消弹窗</a>
				<a style="width:90px;" href="pop_addInit.action" class="textbg6">发布新弹窗</a>
				<a href="javascript:doDelete();" class="textbg4">删除</a>
				</div>
			<form action="pop_mylist.action" method="post" name="popForm">
				<s:hidden name="pop.id" id="popId" />
				<s:hidden name="pop.status" id="popStatus" />
				<s:hidden name="popIds" id="popIds" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="pageResult" value="pop_mylist" />
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
