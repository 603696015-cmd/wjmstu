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
		<TITLE>扬州专业技术人员继续教育网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/menu.js"></script>
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
			 	if(status==1 && window.confirm("确定提交？")){
			 	if(FillInNoteksInit(id)){
			 		document.forms.elclass_sh.submit();
			 		}
			 	} 
			}
			 function FillInNoteksInit(id){
									 var rn=2;
									 if(document.getElementById("status").value==5){
									     width=1000;
										 height=560;
										 //此地加一个拦截，用于查看时间是否存在重叠
										 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										 rn =  window.showModalDialog("elclass_timeover_list.action?elclass.id="+id+"&PageStatus=1&x="+Math.random(),null,sFeature);
										 //return false;
									 }
									 if(rn==1 || rn==2){//点击了通过或者不通过
									 	if(rn==2){
									 		document.getElementById("status").value=4;
									 	}
										 width=600;
										 height=500;
									  	 sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
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
								  }
		</script> 
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表页" /></div>
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
		
		<form action="newelclass_sh.action?state=1" name="elclass_sh" method="post">
			<s:hidden name="elclass.id" id="elclass.id"></s:hidden>
			<s:hidden name="status" id="status"></s:hidden>
			<s:hidden name="Return" id="Return" value="newelclass_sh_list"></s:hidden>
		</form>
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
			  <td valign="top" id="tree_list_td" style="display:none">  
					<wysLib:clTypeTree href="shopping_partmentnewmyelclass_list.action?sublibs=1&cltype.id=" rootAble="true" />
			  </td>
				<td valign="middle" width="5px;" style="padding: 0px" >
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
										onclick="changeTreeDisplay(this)" id="showimg" />
			  </td>
				<td valign="top">
					<div style="text-align: left;" id="showtree">
								<a href="javascript:showtree(true);" class="textbg">显示类别</a>
							</div>
				<s:form action="shopping_partmentnewmyelclass_list" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="cltype.id"></s:hidden>
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
							<td> 
								状态:
								<s:select theme="simple"  headerValue="全部" headerKey="-1"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中',9:'已删除'}"
									name="elClass.status" value="elClass.status" />
							</td> 
							<td>
								<s:submit value="搜索"></s:submit>
							</td>
						</tr>  
			</table>  
			</s:form>  
			<s:if test="elclasses.size==0">没有符合条件的培训班</s:if>
			<s:else>
			<table width="100%" height="100%" align="center" cellpadding="1" cellspacing="1"
			>
			<tr>
				<th width="200" align="center" >
					培训班名称				</th>
				<th width="60" align="center" >类型</th>
				<th width="100" align="center" >
					类别				</th>
				<th width="100" align="center" >
					创建者				</th>
				<th width="120" align="center" >
					创建时间				</th>
				<th width="120" align="center" >
					开始时间				</th>
				<th width="120" align="center" >
					结束时间				</th>
				<th width="100" align="center" >
					审核状态				</th>
				<th width="120" align="center" >
					已报(计划)人数				</th>
				<th width="120" align="center" >&nbsp;</th> 
			</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
			<s:iterator value="elclasses">
			<tr>
				<td style="padding-left:8px;color:blue;" width="200" align="left"> 
					
				<s:property value="name"/>				</td>
				<td width="60" align="center" ><s:if test="isApplication == 1">
						<SPAN style="color:red">申请</SPAN>					</s:if><s:else>
						<SPAN style="color:gray">分配</SPAN>
				</s:else></td>
				<td width="100" align="center" >
					<s:property value="cltype.name"/>				</td>
				<td width="100" align="center" >
					<s:property value="creater.realname" />				</td>
				<td width="120" align="center" > 
					<s:date format="yyyy-MM-dd hh:mm:ss" name="createtime"/>				</td>
				<td width="120" align="center" >
					<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime"/> 
				<td width="120" align="center" >
					<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime"/>			    </td>
				<td width="100" align="center" style="color:green;">
				<s:property value="statusName"/>				</td>
				<td width="120" align="center" >
					<s:property value="classSize"/>	  
					<s:if test="isApplication == 1">
						<span style="color:red">(<s:property value="planNumber"/>)</span>					</s:if>				</td>
				<td width="200" align="left" >
				 <a href="elclass_check_students.action?sub_department=1&elclass.id=<s:property value="id"/>" class="textbg4">学员</a>
					<!-- <a href="elclass_view.action?elclass.id=<s:property value="id" />" class=textbg4>编辑</a>  -->
					<a href="shopping_classtodepartment_init.action?elclass.id=<s:property value="id" />" class="textbg4">分配</a>
					<s:if test="elclass.status == 0">
						<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 1);" class="textbg6">提交</a>
					</s:if>

					<a href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=-2&PageStatusint=-2" class="textbg4">详情</a>
				<!--<s:if test="status == 0 || status == 2"> 
					<a href="elclass_assign2userInit.action?sub_department=1&elclass.id=<s:property value="id" />" class="textbg">分配学员</a>				</s:if>		-->		</td>
			</tr>
			</s:iterator></tbody>
		</table> 
		</s:else>
		<wysLib:page></wysLib:page>
		
		</td>
	  </tr>
	</table> 
	
	</body>
</HTML>
