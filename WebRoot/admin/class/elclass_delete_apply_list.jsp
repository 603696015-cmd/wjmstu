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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>处理培训班删除申请</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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

.textbg4 {
	margin-top: 2px;
}

.textbg6 {
	margin-top: 2px;
}
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
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示类别</a>';
					}
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
					<wysLib:Navigation ivalue="培训班列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">待删除的培训班</span>
			</li>-->
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
					<wysLib:clTypeTree
						href="elclass_delete_apply_list.action?sublibs=1&cltype.id="
						rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" id="showimg" />
				</td>
				<td valign="top">
					<s:form action="elclass_delete_apply_list" name="myclist"
						theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="cltype.id"></s:hidden>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
  <tr>
    <td width="85" rowspan="2"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示类别</a>
								</div></td>
    <td width="500">包含下级节点: 
<input type="checkbox" name="sublibs" 
								<s:if test="sublibs==1">checked='checked'</s:if>
								 value="1">
							培训班名称:
								<input type="text" name="elClass.name"
									value="<s:property value="elClass.name"/>">
							状态:
								<s:select theme="simple"  headerValue="全部" headerKey="-1"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="elClass.status" value="elClass.status" /></td>
    <td rowspan="2">&nbsp;<s:submit cssClass="textbg4" value="搜索"></s:submit></td>
    </tr>
  <tr>
    <td>时间段范围&nbsp;&nbsp;&nbsp;从&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="text" onclick=setday(this)
									name="elClass.begintime"
									value="<s:date name="elClass.begintime" format="yyyy-MM-dd HH:mm"/>">
								&nbsp;到&nbsp;
								<input type="text" onclick=setday(this) name="elClass.endtime"
									value="<s:date name="elClass.endtime" format="yyyy-MM-dd HH:mm"/>">
							</td>
    </tr>
</table>
					</s:form>
					<s:if test="elclasses.size==0">没有申请删除的培训班</s:if>
					<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1">
							<tr>
								<th width="260" align="center">
									培训班信息
								</th>
								<th width="60" align="center">
									类型
								</th>
								<th width="90" align="center">
									创建时间
								</th>
								<th width="90" align="center">
									开始时间
								</th>
								<th width="90" align="center">
									结束时间
								</th>
								<th width="70" align="center">
									状态
								</th>
								<th width="70" align="center">
									人数
								</th>
								<th width="90" align="center">
									考场待操作
								</th>
								<th width="100" align="center">&nbsp;
									
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="elclasses">
									<tr>
										<td style="padding: 3px 0px 3px 2px;" valign="top"
											align="left">
											<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size: 15px; color: blue;"><s:property
														value="name" /> </strong>
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
												<s:property value="creater.realname" />
												<br />
												<s:if test="examRoom.classid!=-1">
													<strong> 所属课程: </strong>
													<s:property value="course.name" />
												</s:if>
											</div>
										</td>
										<td align="center">
											<s:if test="isApplication == 1">
												<SPAN style="color: red">申请</SPAN>
											</s:if>
											<s:else>
												<SPAN style="color: gray">分配</SPAN>
											</s:else>
										</td>
										<td align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="createtime" />
										</td>
										<td align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime" />
										<td align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime" />
										</td>
										<td align="center">
											<s:if test="status == 8">
												<span style="color: red"><s:property
														value="statusName" /> </span>
											</s:if>
											<s:else>
												<s:property value="statusName" />
											</s:else>
										</td>
										<td align="center">
											参加:
											<s:property value="classSize" />
											<s:if test="isApplication == 1">
												<br />
												<span style="color: red">计划:<s:property
														value="planNumber" /> </span>
											</s:if>
										</td>
										<td align="center">
											<s:if test="operation == 1">
												<span style="color: green">全开通</span>
											</s:if>
											<s:if test="operation == 2">
												<span style="color: red">有待操作</span>
											</s:if>
											<s:if test="operation == 3">
											无待操作										</s:if>
										</td>
										<td align="left">
											<a
												href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=8&PageStatusint=8&Return=adla"
												class="textbg4">详 情</a>
											<a
												href="elclass_check_students.action?elclass.id=<s:property value="id"/>&sub_department=1&Return=adla"
												class="textbg4">学 员</a>
											<s:if test="status == 8">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 9);" class="textbg4">允
													许</a>
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>,
												<s:property value="astatus"/>
												);"
													class="textbg6">不&nbsp;允&nbsp;许</a>
												<a
													href="CRE_notelistInit.action?elclass.id=<s:property value="id"/>&Return=elclass_delete_apply_list"
													onclick="showCre(<s:property value="id"/>);return false;" class="textbg4">备 注</a>
											</s:if>
											<!-- <a onClick="return confirm('确定删除该培训班？')"
											href="elclass_delete_apply_op.action?elclass.id=<s:property value="id"/>&pN=0&pS=10" class="textbg">确认删除</a>
										<a href="elclass_delete_apply_op.action?elclass.id=<s:property value="id"/>&status=<s:property value="astatus" />&pN=0&pS=10" class="textbg">驳回申请</a>									
										 -->
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</s:else>
				</td>
			</tr>
		</table>
		<form action="elclass_sh.action" name="elclass_sh" method="post">
			<s:hidden name="elclass.id" id="elclass.id"></s:hidden>
			<s:hidden name="status" id="status"></s:hidden>
			<s:hidden name="Return" id="Return" value="elclass_delete_apply_list"></s:hidden>
		</form>
		<script>   
								function sh(id,status){
								    document.getElementById("elclass.id").value=id;
								    document.getElementById("status").value=status; 
								 	if(window.confirm("确定操作？")){
										if(FillInNoteksInit(id)){
								 		document.forms.elclass_sh.submit();
								 		}
								 	}  
								}  
								function page(i) {
									document.getElementById("pageNow").value=i;
									myclist.submit();
								} 
								function FillInNoteksInit(id){ 
								     width=600;
									 height=500;  
								  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									 var rv =  window.showModalDialog("CRE_addNotes.action?elclass.id="+id+"&Return=elclass_primash_list&x="+Math.random(),null,sFeature); 
									 if(rv == null){
										alert("未填写备注信息，您不能进行提交！");	 
										return false;				
									 }else{
										if(rv == true){  
											return true;
										}else if(rv == false){
											return false;
										}  
									 }
								 }
								 function showCre(elclassid){
								  	 width=750;
									 height=500;  
								  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									 var rv =  window.showModalDialog("CRE_notelistInit.action?elclass.id="+elclassid+"&examRoom.id=0&course.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
								}
							</script>
		<wysLib:page></wysLib:page>
	</body>
</HTML>
