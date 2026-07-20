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
		<TITLE>商务汉语学习系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color: "#ffffff" }
			.textbg4{margin-top:2px;}
			.textbg6{margin-top:2px;} 
		</style>
		<!--
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression("#ffffff")}
			.textbg4{margin-top:2px;}
			.textbg6{margin-top:2px;} 
		</style>
		-->
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script>    
			function page(i) {
				document.getElementById("pageNow").value=i;
				myclist.submit();
			}
			function sh(id,status){
			    document.getElementById("elclass.id").value=id;
			    document.getElementById("status").value=status; 
			 	if(status==1 && window.confirm("确定创建完成？")){
			 		document.forms.elclass_sh.submit();
			 	} 
			}
			function seachClassInDel(){
				document.getElementById("pageNow").value=0;
				document.getElementById("sqlw").value=9;
				myclist.submit();
			}
			function initPN(){
				document.getElementById("pageNow").value=0;
				document.getElementById("sqlw").value=0;
				myclist.submit();
			}
			function shEroom(id,valid,alterValid,deleteValid,fushenValid,Return){
			    document.getElementById("examRoom.id").value=id;
			    document.getElementById("examRoom.valid").value=valid; 
			    document.getElementById("deleteValid").value=deleteValid; 
			    document.getElementById("alterValid").value=alterValid; 
			    document.getElementById("fushenValid").value=fushenValid; 
			    document.getElementById("Return").value=Return; 
			 	if(window.confirm("确定此操作？")){
					if(FillInNoteksInit(id)){ 
			 			document.examroom_sh_p.submit();
			 		}
			 	}
			}
			function FillInNoteksInit(id){ 
			     width=600;
				 height=500;  
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("CRE_addNotes.action?examRoom.id="+id+"&Return=elclass_primash_list&x="+Math.random(),null,sFeature); 
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
		</script> 
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="岗位培训班列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的培训班</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="examroom_sh_p.action" name="examroom_sh_p" method="post"> 
			<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
			<s:hidden name="examRoom.valid" id="examRoom.valid"></s:hidden>
			<s:hidden name="deleteValid" id="deleteValid"></s:hidden>
			<s:hidden name="alterValid" id="alterValid"></s:hidden>
			<s:hidden name="fushenValid" id="fushenValid"></s:hidden>
			<s:hidden name="Return" id="Return"></s:hidden><!-- value="course_return" -->
		</form>
		<form action="elclass_sh.action?state=1" name="elclass_sh" method="post">
			<s:hidden name="elclass.id" id="elclass.id"></s:hidden>
			<s:hidden name="status" id="status"></s:hidden>
			<s:hidden name="Return" id="Return" value="elclass_alllist"></s:hidden>
		</form>
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="165" valign="top" id="tree_list_td">
					<wysLib:clTypeTree
						href="elclass_alllist.action?sublibs=1&cltype.id=" rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">
					<s:form action="elclass_alllist" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="cltype.id"></s:hidden>
						<s:hidden name="elClass.sqlw" id="sqlw" />
						<table width="100%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<td>
									包含下级节点:
									<input type="checkbox" name="sublibs"
										<s:if test="sublibs==1">checked='checked'</s:if> value="1">
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
								<td>
									状态:
									<s:select theme="simple" headerValue="全部" headerKey="-1"
										list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
										name="elClass.status" value="elClass.status" />
								</td>
								<td>
									<input onClick="initPN();" class="textbg4" type="submit" value="搜索" />
								</td>
							</tr>
						</table>
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
								<th width="100" align="center">
									&nbsp;
								</th>
							</tr>
							<tbody>
								<s:iterator value="elclasses" status="elclasses_st">
									<tr <s:if test="(#elclasses_st.index%2)==1">style="background:#f4f4f4"</s:if>>
									  <s:if test="examRooms.size==0">
									  	 <td  style="padding: 3px 0px 3px 2px;" valign="top"
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
												<strong>组织警钟:</strong>
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
									   </s:if>
									   <s:else>
									    <td rowspan="2" style="padding: 3px 0px 3px 2px;" valign="top"
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
												<strong>组织警钟:</strong>
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
									   </s:else>
										
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
										<td  align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime" />
										</td>
										<td align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime" />
										</td>
										<td align="center" style="color: green;">
											<s:property value="statusName" />
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
										<td align="left">
											<a
												href="elclass_copy.action?elclass.id=<s:property value="id" />"
												onclick="return window.confirm('确定复制？')" class="textbg4">复制</a>
											<s:if test="status == 0 || status == 2">
												<!-- <a href="elclass_view.action?elclass.id=<s:property value="id" />" class=textbg4>编辑</a>  -->
												<a
													href="elclass_alterInit.action?elclass.id=<s:property value="id" />"
													class="textbg4">编辑</a>
												<s:if test="isUvalid == 'false'">
													<a style="cursor: pointer;"
														onClick="sh(<s:property value="id"/>, 1);" class="textbg6">创建完成</a>
												</s:if>
											</s:if>
											<s:else>
												<a
													href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=-2&PageStatusint=-2"
													class="textbg4">详情</a>
											</s:else>
											<!--<s:if test="status == 0 || status == 2"> 
					<a href="elclass_assign2userInit.action?sub_department=1&elclass.id=<s:property value="id" />" class="textbg">分配学员</a>				</s:if>		-->
										</td>
									</tr>
									<s:if test="examRooms.size!=0">
									<tr <s:if test="(#elclasses_st.index%2)==1">style="background:#f4f4f4"</s:if>>
										<td colspan="7" align="left" valign="top" style="padding: 0px;margin: 0px">
												<s:if test="examRooms.size!=0">
													<s:iterator value="examRooms">
														<span style='width:360px;text-align:left;'><s:property value="title"/>(<s:property value="validName"/>)</span> 
														<s:if test="valid==0||valid==2">
															<a href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=error" class="textbg4">编 辑</a>
															<a href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>" class="textbg4">分配</a>
															<a style="cursor:pointer;"  onClick="shEroom(<s:property value="id"/>, 1,'falsa','falsa','true','elclass_alllist');" class="textbg6">创建完成</a>
														</s:if>
														
														<br/>
													</s:iterator>
											</s:if>
										</td>
									</tr>
									</s:if>
								</s:iterator>
							</tbody>
						</table>
					</s:else>
					<wysLib:page></wysLib:page>
					<input class="textbg6" style="height: 35px; width: 100px;"
						type="button" value="创建培训班"
						onClick="javascript:document.location.href='elclass_addInit.action';">
					<s:if test="elClass.sqlw==9">
						<input class="textbg7" style="height: 35px; width: 110px;"
						type="button" value="返回培训班列表" onClick="initPN();">
					</s:if>
					<s:else>
					<input class="textbg7" style="height: 35px; width: 110px;"
						type="button" value="已删培训班列表" onClick="seachClassInDel();">
					</s:else>
					
					<input class="textbg7" style="height: 35px; width: 110px;"
						type="button" value="培训班类别" onClick="document.location='cltype_list.action'">
						
				</td>
			</tr>
		</table> 
	</body>
</HTML>
