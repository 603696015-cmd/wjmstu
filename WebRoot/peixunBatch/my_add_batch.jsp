<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	<script type="text/javascript" src="js/cexampaper.js"></script>
	<script type="text/javascript">
	function sh3(id,status){   
		    document.getElementById("peixunBatch.id").value=id;
		    document.getElementById("status").value=status; 
		 	if(status==2 && window.confirm("确定返回？")){
		 		if(FillInNoteksInit(id)){
		 		document.forms.peixunBatch_sh.submit(); 
				}  
		 	}
		 	/*
		 	if(status==3 && window.confirm("确定提交申请？")){
		 		if(FillInNoteksInit(id)){
		 		document.forms.elclass_sh.submit(); 
				}  
		 	}
		 	*/
		 	if(status==3){
		 		if(FillInNoteksInit(id)){
		 			document.forms.elclass_sh.submit(); 
				}  
		 	}
		 	if(status==0 && window.confirm("确定让创建者修改吗？")){
		 		if(FillInNoteksInit(id)){
		 		document.forms.elclass_sh.submit(); 
				}  
		 	} 
	 	} 
	function FillInNoteksInit(id){
	 var rn=2;
	 if(rn==1 || rn==2){//点击了提交申请或者返回
	 	if(rn==2){
	 		document.getElementById("status").value=2;
	 	}
		 width=600;
		 height=500;
	  	 sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		 var rv =  window.showModalDialog("batch_addNotes.action?peixunBatch.id="+id+"&Return=batch_primash_list&x="+Math.random(),null,sFeature); 
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
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训批次列表页" /></div>
			</li>
			
		</ul> 
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="my_add_batch" name="myclist" theme="simple">
			<s:hidden name="pN" id = "pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			<br/>
			培训批次名称：
			<s:textfield name="peixunBatch.name"></s:textfield>
			<s:submit value="搜索"></s:submit>
			<!--	<a href="batch_edit.action" class="textbg">添加培训批次</a>   -->
		<s:hidden name="status" id="status"></s:hidden>
		<s:hidden name="id" id="id"></s:hidden>
		</s:form>
		<table width="100%">
			<tr><td>
			<s:if test="batchList.size==0">没有符合条件的培训批次</s:if>
			<s:else>
				<table width="100%" align="left" cellpadding="1" cellspacing="1" >
					<tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<th width="180" align="center" >
							批次名称						</th>
						<th width="180" align="center" >
							批次类别
						</th>
					  <th width="180" align="center" >培训班数量				  </th>
					  <th width="180" align="center" >创建时间			  </th> 
					   <th width="180" align="center" >创建者			  </th> 
					      <th width="180" align="center" >操作			  </th> 
					<s:iterator value="batchList">
						<tr>
							<td width="180" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
								<s:property value="name"/>
						  </td>
							<td align="center" >
								<s:property value="baseData.basevalue"/>
							</td>
							<td width="180" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
								<s:property value="ElclassCount"/>
						  </td>
						  <td width="180" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
								<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />			
						  </td>
						  <td width="180" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
								<s:property value="creater.realname"/>
						  </td>
							<td width="300" align="center" >
								<a href="batch_alter.action?peixunBatch.id=${id}&peixunBatch.elclassId=${elclassId }" class=textbg4>修改</a>&nbsp;
								<a href="batch_details.action?peixunBatch.id=${id}" class=textbg4>查看</a>	&nbsp;	
								<a href="delete_batch.action?peixunBatch.id=${id}" class=textbg4>删除</a>&nbsp;
								<a  href="#" onClick="sh3(${id}, 3);" class=textbg6>提交初审</a>
								<!--  <input type="button" value="提交初审" onclick="sh3(${id}, 3);" class=textbg6/>-->
							
									
													</td>
						</tr>
					</s:iterator></tbody>
			  </table>
			</s:else>   
			</td></tr>
	</table>
	<form action="batch_sh.action" name="peixunBatch_sh"
						method="post">
						<s:hidden name="peixunBatch.id" id="peixunBatch.id"></s:hidden>
						<s:hidden name="status" id="status"></s:hidden>
						<s:hidden name="sublibs" value="1"></s:hidden>
						<s:if test="elclass.status==0">
							<s:hidden name="Return" id="Return" value="elclass_alllist"></s:hidden>
						</s:if>
						<s:if test="elclass.status==1">
							<input type="hidden" name="Return" value="elclass_primash_list"
								id="Return" />
						</s:if>
						<s:if test="peixunBatch.status==3">
							<s:hidden name="Return" id="Return" value="peixunBatch_sh_list"></s:hidden>
						</s:if>
					</form>
		<script> 
			function page(i){ 
						document.location.href="my_add_batch.action?pS=<s:property value="pS"/>&pN="+i
								}  
							</script>
							<wysLib:page></wysLib:page>
	</body>
</HTML>
