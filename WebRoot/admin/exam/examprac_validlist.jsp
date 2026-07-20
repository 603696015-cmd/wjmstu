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
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="练习列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习审核 </span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div>
				<table width="100%" align="center" cellpadding="1" cellspacing="1">
					<tr>
						<th></th>
						<th width="150" align="center">
							练习名称
						</th>
						<th width="120"  align="center">
							创建者
						</th>
						<th width="200"  align="center">
							创建者所属部门
						</th>
						<th width="120"  align="center">
							开始时间
						</th>
						<th width="120"  align="center">
							结束时间
						</th>
						<th width="80"  align="center">
							考生人数
						</th>
						<th width="100"  align="center">
							状态
						</th>
						<th width="220"  align="center">&nbsp;
							
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="exampracs">
							<tr>
								<td height="30" align="center">
									<input type="checkbox" value="<s:property value="id"/>"
										name="id">
								</td>
								<td  align="center">
									<s:property value="title" />
								</td>
								<td  align="center">
									<s:property value="user.username" />
								</td>
								<td  align="center">
									<s:property value="user.danwei" />
									<!-- 此处借用 实际值为  部门名称 -->
								</td>
								<td  align="center">
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td  align="center">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td  align="center">
									<s:property value="usersize" />
								</td>
								<td  align="center">
									<font color="<s:if test="valid==1">red</s:if>"> <s:property
											value="validName" /> </font>
								</td>
								<td  align="left">
									<a
										href="examprac_validview.action?examprac.id=<s:property value="id"/>"
										class="textbg4">查 看</a>
									<a target="_blank"
										href="exampaper_preview.action?examPaper.id=<s:property value="examPaper.id" />"
										class=textbg4>预 览</a>
									<s:if test="valid==1">

										<a href="javascript:sh_np(<s:property	value="id" />);"
											class="textbg4">暂停</a>
									</s:if>
									<s:elseif test="valid==3">
										<%-- 
							<input class="textbg4" type="button" name="button2" onClick="sh_p(<s:property	value="id" />);"
								id="button2" value="恢复" />
							 --%>
										<a href="javascript:sh_p(<s:property value="id" />);"
											class="textbg4">恢复</a>
									</s:elseif>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
					<a href="javascript:select_All()" class="textbg4">全选</a>
					<a href="javascript:select_Fan()" class="textbg4">反选</a>
					<a href="javascript:select_Bux()" style="width:60px;" class="textbg4">全不选</a>
					<a href="javascript:assign()" class="textbg4">删除</a>
				</DIV>
				<script>
					function page(i){
						document.getElementById("pageNow").value=i;
						erform.submit();
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
				function assign(){
				    if(window.confirm("确定删除？")){
						var checkObj = document.getElementsByName("id");
					    var billIDs = "";
					    for (i = 0; i < checkObj.length; i++) {
							if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
						 }
						if(billIDs==""){
						  alert("请选择要删除的记录！");
						  return ;
					    }
					    var pracids = document.getElementById("pracids");
					    pracids.value=billIDs;
						examprac_del.action="examprac_del.action";
						examprac_del.submit();
					}
				}
				function sh_p(id){
					if(window.confirm("确定恢复？"))
						document.location.href="examprac_validrecovery.action?examprac.id="+id+"&pageResult=1";
				} 
				function sh_np(id){
					if(window.confirm("确定暂停？")) 
						document.location.href="examprac_validsuspended.action?examprac.id="+id+"&pageResult=1";
				}
				</script>
			</div>
		</div>
		<s:form action="examprac_del.action" method="post" name="examprac_del">
			<s:hidden name="pracids" id="pracids" />
		</s:form>
		<form action="examprac_validlist.action" method="post" name="erform">
			<input type="hidden" name="pN" id="pageNow" />
			<input type="hidden" name="pS" id="pS" />
		</form>
		<!-- 内容 -->
	</BODY>
</HTML>
