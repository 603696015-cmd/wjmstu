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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
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
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
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
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="搜索结果页" />
				</div>
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
				<td width="165" valign="top" id="tree_list_td">
					<wysLib:clTypeTree href="combinationSearchClass.action?cltype.id="
						rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">

					<s:form action="elclass_sh_list" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<!--培训班名称：<s:textfield name="elClass.name"></s:textfield> <s:submit value="搜索"></s:submit>-->

					</s:form>

					<s:if test="elclasses.size==0">没有符合条件的培训班</s:if>
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
								<th align="center">
									创建时间
								</th>
								<!--<th align="center" >
									证书名称
								</th>
								-->
								<th width="70" align="center">
									开放状态
								</th>
								<th width="90" align="center">
									考场待操作
								</th>
								<th width="100" align="center">
									&nbsp;
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
											<s:date format="yyyy-MM-dd  hh:mm:ss" name="createtime" />
										</td>
										<!--<td align="center" >
											<s:property value="certificatename" />
										</td>
										-->
										<!--<td width="100" align="center" >
											<s:property value="optionalcredit" />
										</td>
										-->
										<td align="center" bgcolor="#FFFFFF" style="color: green;">
											<s:property value="statusName" />
										</td>
										<td align="center">
											<s:if test="operation">
												<span style="color: red">有</span>
											</s:if>
											<s:else> 
											无
										</s:else>
										</td>
										<td align="left">
											<a
												href="elclass_check_students.action?elclass.id=<s:property value="id"/>&sub_department=1&Return=csc"
												class="textbg4">学 员</a>
											<!-- <a href="elclass_view.action?elclass.id=<s:property value="id" />">详情</a> -->
											<s:if test="status == 0">
												<a
													href="elclass_addInit.action?elclassId=<s:property value="id" />&elclass.cltype.id=<s:property value="cltype.id"/>&Return=csc"
													class=textbg4>編 緝</a>
											</s:if>
											<s:else>
												<a
													href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=9&PageStatusint=9&Return=csc"
													class=textbg4>详情</a>
											</s:else>
											<s:if test="status == 9">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>,<s:property value="astatus"/>);"
													class="textbg4">还原</a>
											</s:if>
											<s:if test="status == 5">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>,11);" class="textbg4">暂停</a>
											</s:if>
											<s:if test="status == 11">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>,5);" class="textbg4">开通</a>
											</s:if>
											<a
												href="CRE_notelistInit.action?elclass.id=<s:property value="id"/>&Return=elclass_primash_list"
												onclick="showCre(<s:property value="id"/>);return false;" class="textbg4">备注</a>
										</td>
										<!-- <td align="center" >
											<s:if test="status>2">
												<a
													href="elclass_sh_apply.action?elclass.id=<s:property value="id"/>&status=2"
													class=textbg6>申请审核</a>
											</s:if>
										</td>
										<td width="100" align="center" >
											<a
												href="elclass_delete_applyInit.action?elclass.id=<s:property value="id"/>"
												class=textbg6>申请删除</a>
										</td> -->
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
			<s:hidden name="Return" id="Return" value="combinationSearchClass"></s:hidden>
		</form>

		<script> 
		
								function sh(id,status){
								    document.getElementById("elclass.id").value=id;
								    document.getElementById("status").value=status; 
								 	if(window.confirm("确定该操作吗？")){
										if(FillInNoteksInit(id)){
								 		document.forms.elclass_sh.submit();
								 		} 
								 	}
								}  
									function page(i){
										document.location.href="combinationSearchClass.action?pS=<s:property value="pS"/>&pN="+i
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
		<div style="text-align: center;">
			<wysLib:page></wysLib:page>
			<a href="combinationSearchclassInit.action" class="textbg4" style="width: 80px">返回</a>
		</div>

	</body>
</HTML>
