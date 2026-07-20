<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>培训班终审</TITLE>
		<base href="<%=basePath%>"> 
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")}
			.textbg4{margin-top:2px;}
			.textbg6{margin-top:2px;} 
		</style>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示类别</a>';
					}
				}
		</script>
		<script>   
			function page(i) {
				document.getElementById("pageNow").value=i;
				myclist.submit();
			}
			function isUpData(){
			  	if(window.confirm("此操作慎重，是否继续?")){
			  		return true;
			  	}
			  	return false;
			}
		</script> 
		</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li> 
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="165" valign="top" id="tree_list_td" style="display:none">
					<wysLib:clTypeTree  href="elclass_userAudit.action?sublibs=1&cltype.id="
						rootAble="true" />
			  </td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" id="showimg" />
				</td>
				<td valign="top">
					<div style="text-align: left;" id="showtree">
								<a href="javascript:showtree(true);" class="textbg">显示类别</a>
							</div>
					<s:form action="elclass_userAudit" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="cltype.id"></s:hidden>
						<s:hidden name="elClass.status" value="-1" />
					<table width="100%" align="center" cellspacing="1" cellpadding="1">
						<tr>
							<td> 
								包含下级节点: 
								<input type="checkbox" name="sublibs" 
								<s:if test="sublibs==1">checked='checked'</s:if>
								 value="1">
							</td>
							<td>
								培训班名称:
								<input type="text" name="elClass.name"
									value="<s:property value="elClass.name"/>">
							</td>
							<td>
								时间段范围&nbsp;从
								<input type="text" onclick=setday(this)
									name="elClass.begintime"
									value="<s:date name="elClass.begintime" format="yyyy-MM-dd HH:mm"/>">
								&nbsp;到&nbsp;
								<input type="text" onclick=setday(this) name="elClass.endtime"
									value="<s:date name="elClass.endtime" format="yyyy-MM-dd HH:mm"/>">
							</td>
							<%-- 
							<td> 
								状态:
								<s:select theme="simple"  headerValue="全部" headerKey="-1"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="elClass.status" value="elClass.status" />
							</td> 
							 --%>
							<td>
								<s:submit cssClass="textbg4" value="搜索"></s:submit>
							</td>
						</tr> 
					</table>  
					</s:form> 
					<s:if test="elclasses.size==0">没有需要审核的培训班</s:if>
					<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1" >
							<tr>
							<th width="260" align="center" >
								培训班信息				</th>
							<th width="60" align="center" >类型</th>
							<th width="90" align="center" >
								创建时间				</th>
							<th width="90" align="center" >
								开始时间				</th>
							<th width="90" align="center" >
								结束时间				</th>
							<th width="70" align="center" >
								状态				</th>
							<th width="70" align="center" >
								人数				</th>
								<th width="100" align="center" >&nbsp;								</th> 
							</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
							<s:iterator value="elclasses">
								<tr>
									<td style="padding: 3px 0px 3px 2px;" valign="top"
											align="left">
										<div style="word-wrap: break-word; word-break: break-all; width: 100%;">
											<strong style="font-size:15px;color: blue;"><s:property value="name" /> </strong>
											<br />
											<strong>类别:</strong>
											<s:property value="cltype.name" />
											<br />
											<strong>组织单位:</strong>
											<s:property value="depName" />
											<br />
											<strong>组织工钟:</strong>
											<s:property value="jingzhong" />
											<br />
											<strong>创建者:</strong>
											<s:property value="creater.realname" /><br/>
											<s:if test="examRoom.classid!=-1">
												<strong> 所属课程: </strong><s:property value="course.name" />
											</s:if>
										</div>
									</td>
									<td align="center" ><s:if test="isApplication == 1">
											<SPAN style="color:red">申请</SPAN>										</s:if><s:else>
											<SPAN style="color:gray">分配</SPAN>
								  </s:else></td>
									<td align="center" > 
								  <s:date format="yyyy-MM-dd hh:mm:ss" name="createtime"/>								  </td>
									<td align="center" >
										<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime"/> 
								  <td align="center" >
								  <s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime"/>								  </td>
									<td align="center" >
									<s:if test="status == 3">  
											<span style="color:red"><s:property value="statusName" /></span>									</s:if><s:else> 
										<s:if test="status == 5"> 
											<span style="color:green"><s:property value="statusName" /></span>										</s:if><s:else>
											 <s:property value="statusName" /> 
										</s:else>
									</s:else>								  </td>
									<td align="center">
										参加:
										<s:property value="classSize" />
										<s:if test="isApplication == 1">
											<br />
											<span style="color: red">计划:<s:property
													value="planNumber" /> </span>
										</s:if>
									</td>
									<!-- <td width="60" align="center" >
										<a
											href="elclass_details.action?elclassId=<s:property value="id" />" class="textbg4">详 情</a>									
									</td> -->
									<td align="center" >
										<a href="elclass_auditUserlist.action?elClass.id=<s:property value="id" />" class="textbg6">查看人员</a>
									</td>
								</tr>
							</s:iterator></tbody>
					  </table>
					</s:else>
				</td>
			</tr>
	</table>
							<form action="elclass_sh.action" name="elclass_sh" method="post">
								<s:hidden name="elclass.id" id="elclass.id"></s:hidden>
								<s:hidden name="status" id="status"></s:hidden>
								<s:hidden name="Return" id="Return" value="elclass_userAudit"></s:hidden>
							</form>
		<wysLib:page></wysLib:page>
	
	</body>
</HTML>
											