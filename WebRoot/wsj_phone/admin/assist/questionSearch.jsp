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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
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
		</style>
		<script type="text/javascript">
			function page(i){
				pages.action = "questionSearch.action";
				document.getElementById("pN").value=i;
				pages.submit();
			}
			function confirmSel(){
				var resultVal="";
				var jsondata = "";
				var qid=$("input[name=\"question.id\"]:checked").val();
				if(qid>0){
					$.ajax({
						async:false,
						type:"POST",
						url:"questionSelect.action",
						data:"question.id="+qid,
						success:function(data){
							jsondata= eval("("+data+")");
							resultVal=jsondata.id+"-=wys=-"+jsondata.title+"-=wys=-"+jsondata.subject;
						}
					});
				}
				//alert("已选择!");
				//alert(resultVal);
				window.returnValue=resultVal;
				window.close();
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
					<wysLib:Navigation ivalue="试题列表" />
				</div>
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
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td">
						<wysLib:qlibtree
							href="questionSearch.action?sublibs=1&question.status=-1&question.qlib.id="
							rootAble="true"></wysLib:qlibtree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<form action="questionSearch.action" method="post" name="pages">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="question.qlib.id" />
							<s:hidden name="question.status" value="-1" />
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1">
								<tr>
									<td width="13%">
										包含下级节点:
										<input type="checkbox" name="sublibs" value="1"
											<s:if test="sublibs == 1">checked="checked"</s:if>>
									</td>
									<td width="24%">
										试题名称：
										<input type="text" name="question.title"
											value="<s:property value="question.title"/>" />
									</td>
									<td width="21%">
										创建者:
										<input type="text" name="elUser.realname"
											value="<s:property value="elUser.realname"/>" />
									</td>
									<td>
										创建时间范围:
										<input type="text" onclick=setday(this)
											name="question.createtime"
											value="<s:date name="question.createtime" format="yyyy-MM-dd HH:mm"/>">
										&nbsp;~&nbsp;
										<input type="text" onclick=setday(this)
											name="question.createtimeEnd"
											value="<s:date name="question.createtimeEnd" format="yyyy-MM-dd HH:mm"/>">
									</td>
								</tr>
								<tr>
									<td colspan="3" style="">
										题型：
										<s:select theme="simple" headerKey="0" headerValue="全部"
											name="question.qtype"
											list="#{2:'单选题',4:'多选题'}"
											value="question.qtype" />
									</td>
									<td colspan="3" style="">
										<input class="textbg4" style="height: 25px;" type="submit"
											value="搜索">
									</td>
								</tr>
							</table>
						</form>
						<s:form action="question_delete_status" method="post"
							name="question_manage" id="question_manage">

							<s:if test="questions.size==0">没有符合条件的试题</s:if>
							<s:else>
								<table width="100%" align="center" cellpadding="1"
									cellspacing="1">
									<tr>
										<th height="30" align="center">
											&nbsp;
										</th>
										<th height="30" align="center">
											题干
										</th>
										<th height="30" align="center">
											题型
										</th>
										<th height="30" align="center">
											所属题库
										</th>
										<!--<th height="30" align="center" >
											创建时间
										</th>-->
										<th height="30" align="center">
											状态
										</th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="questions" status="statu">
											<tr>
												<td height="30" style="padding-left: 8px; color: blue;"
													align="left">
													<input type="radio" name="question.id" 
														value="<s:property value="id"/>">
												</td>
												<td height="30" style="padding-left: 8px; color: blue;"
													align="left">
													<s:property value="title" />
												</td>
												<td height="30" align="center">
													<s:property value="qtypeName" />
												</td>
												<td height="30" align="center">
													<s:property value="qlib.name" />
												</td>
												<td height="30" align="center">
													<s:property value="statusName" />
												</td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</s:else>
							<wysLib:page></wysLib:page>
						</s:form>
					</td>
				</tr>
			</table>
			<div style="margin-top:20px">&nbsp;</div>
			<a href="javascript:confirmSel();" class="textbg4">确&nbsp;定</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
