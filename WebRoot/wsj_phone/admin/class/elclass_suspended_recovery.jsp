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
		<TITLE>培训班审核</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" /> 
		<script type="text/javascript" src="js/menu.js"></script> 
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训班审核</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%">
			<tr>
				<td width="200px;" valign="top" id="tree_list_td">
					<wysLib:clTypeTree  href="elclass_suspended_recovery.action?cltype.id="
						rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">
					
					<s:form action="elclass_suspended_recovery" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden><div>
					<table width="100%" align="center" cellspacing="1" cellpadding="1">
						<tr>
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
					<s:if test="elclasses.size==0">没有需要审核的培训班</s:if>
					<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1" >
							<tr>
								<th width="200" align="center" >
									培训班名称				</th>
								<th width="100" align="center" >
									类别				</th>
								<th width="60" align="center" >
									创建者				</th>
								<th width="120" align="center" >
									创建时间				</th>
								<th width="120" align="center" >
									开始时间				</th>
								<th width="120" align="center" >
									结束时间				</th>
								<th width="100" align="center" >
									审核状态				</th>
								<th width="80" align="center" >
									学员人数				</th>
								<th width="80" align="center" >
									考场待操作								</th>
								<th width="200" align="center" >&nbsp;</th> 
							</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
							<s:iterator value="elclasses">
								<tr>
									<td width="200" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
										<s:property value="name"/>  
								  </td>
									<td width="100" align="center" >
										<s:property value="cltype.name"/>	 
								  </td>
									<td width="60" align="center" >
										<s:property value="creater.realname" /> 
								  </td>
									<td width="120" align="center" > 
										<s:date format="yyyy-MM-dd hh:mm:ss" name="createtime"/> 
								  </td>
									<td width="120" align="center" >
										<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime"/> 
								  <td width="120" align="center" >
										<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime"/>
								  </td>
									<td width="100" align="center" >
									<s:if test="status == 1 || status == 4">
										<span style="color:red"><s:property value="statusName" /></span>
									</s:if><s:else>
										<s:if test="status == 5"> 
											<span style="color:green"><s:property value="statusName" /></span> 
										</s:if><s:else>
											 <s:property value="statusName" /> 
										</s:else>
									</s:else>
								  </td>
									<td width="80" align="center" >
										<s:property value="classSize"/>	  
								  </td>
									<td width="80" align="center" > 
										<s:if test="operation == 1">
											<span style="color:green">全开通</span>
										</s:if>
										<s:if test="operation == 2">
											<span style="color:red">有待审</span> 
										</s:if>
										<s:if test="operation == 3">
											无待审 
										</s:if> 
								  </td> 
									<td width="200" align="center" > 
										<s:if test="status == 11">  
											<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 5);"  class="textbg4">恢复</a> 
										</s:if><s:else>    
											<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 11);"  class="textbg4">暂停</a>  
										</s:else>
										    <a href="CRE_notelistInit.action?elclass.id=<s:property value="id"/>&Return=elclass_primash_list" target="_blank" class="textbg4">备 注</a>  
											<a href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=1&PageStatusint=4" class="textbg6">查看详情</a>   
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
								<s:hidden name="Return" id="Return" value="elclass_suspended_recovery"></s:hidden>
							</form>  
		<wysLib:page></wysLib:page>  
					<script>    
						function sh(id,status){    
							    document.getElementById("elclass.id").value=id;
							    document.getElementById("status").value=status; 
							 	if(status == 11 && window.confirm("确定暂停？")){
							 		if(FillInNoteksInit(id)){
							 		document.forms.elclass_sh.submit(); 
									}  
							 	} 
							 	if(status == 5 && window.confirm("确定恢复？")){
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
			</script> 
	
	</body>
</HTML>
