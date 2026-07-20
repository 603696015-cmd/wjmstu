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
		<TITLE>留言管理</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="留言列表" /></div>
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
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top">
		<s:form action="myMessageList.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="courseComment.id" id="courseComment.id" ></s:hidden>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="type1" id="type1" ></s:hidden>
				<s:hidden name="type" id="type" ></s:hidden>
				<tr>
				   	<td>  回复者：</td>
				     <td><s:textfield name="courseComment.pfmsUser.user.realname" /></td>
				     <td>状态</td>
				 
				    <td><s:select theme="simple"  headerValue="全部" headerKey="0"
									list="#{1:'已通过',2:'未审核'}"
									name="courseComment.status" value="courseComment.status" /></td>
									<td></td>
				</tr>
				<tr>
				   <td>留言时间  开始时间:</td>
				     <td> 
					<input name='starttime' onclick='setday(this)' readonly/>
					 </td>
				       <td>结束时间:</td>
				         <td>
				         	<input name='endtime'  onclick='setday(this)' readonly/>
						</td>
				         <td colspan="2">
				           	 <input id="find" name="find" type="button" value="搜索" onclick="newsSubmit();" >
				         </td>
				</tr>
			</table>
			</s:form>
						<s:if test="messageList.size==0"><h3 align="center" style="margin-top:10px;">没有留言信息</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<!-- <th width="250" height="30" align="center" >
										店铺名称									</th> -->
									<th width="300" height="30" align="center" >
										回复内容									</th>
									<th width="100" height="30" align="center" >
										回复者									</th>
									<th width="100" height="30" align="center" >
										回复时间								</th>
									<th width="100" height="30" align="center" >
										回复状态									</th>
									
									<th width="60" >	操作								</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="messageList">
									<tr>
										<td height="30" align="center" >
											<s:property value="content" />
											
										</td>
										<td height="30" align="center" >
											
											<s:property value="pfmsUser.user.realname" />
										</td>
										<td height="30" align="center" >
											<s:date name="commentdate" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											<s:property value="StatusName" />
										</td>
										
										<td bgcolor="#FFFFFF" align="left" width="150" height="30">
											 <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>);"  class="textbg4">删除</a> 
											 <a style="cursor:pointer;"  href="messageView.action?courseComment.id=<s:property value="id"/>"  class="textbg4">浏览</a>
											<s:if test="status==2">
												 <a style="cursor:pointer;"  onClick="sh2(<s:property value="id"/>);"  class="textbg4">通过</a> 
											</s:if>
											<s:else>
												<a style="cursor:pointer;"  onClick="sh3(<s:property value="id"/>);"  class="textbg4">不通过</a> 
											</s:else>.
									  </td>
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
				if("${type1}" == "1"){
					assignSearch_assignment.action = "myProductMessageList.action";
				}
				assignSearch_assignment.submit();
			}
			function sh(id){
				if(window.confirm('确定删除？')){
				    document.getElementById("courseComment.id").value=id;
				 	assignSearch_assignment.action="deleMessageComment.action";
				 	assignSearch_assignment.submit();
			 	}
			} 
			function sh2(id){
				if(window.confirm('确定通过？')){
				    document.getElementById("courseComment.id").value=id;
				 	assignSearch_assignment.action="courseCommentPass.action";
				 	assignSearch_assignment.submit();
			 	}
			} 
			function sh3(id){
				if(window.confirm('确定审核不通过？')){
				    document.getElementById("courseComment.id").value=id;
				 	assignSearch_assignment.action="courseCommentNotPass.action";
				 	assignSearch_assignment.submit();
			 	}
			} 
		</script>
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
										   