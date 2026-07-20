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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function do_submit(){
				assignSearch_assignment.submit();
			}
			
			function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		assignSearch_assignment.submit();
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
			
			
			function del(){
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
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				   var ids = document.getElementById("ids");
				   
				   var flag = check_is_open(billIDs);//flag为true时可以删除
				   if(flag ){
				   		return;
				   }
			       ids.value=billIDs;
				   assign.submit();
				}
			}
			
			//判断要删除的是否是开通的培训,是的话删除取消
			function check_is_open(ids){
				ids = ids.split(",");//string数组
				var checks = new Array();
				var flag = false;
				var message = "";
				for(var i=0;i<ids.length;i++){
					$.ajax({
					  type: 'POST',
					  url: "check_is_open.action",
					  data: {id:parseInt(ids[i])},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")");
				  		checks[i] = data.check_json_result;
				  		
				  		for(var j=0;j<checks.length;j++){
							if(checks[j] == true){
								message = "所选培训有已经开通的培训班，不能删除!";
								flag = true;
							}else{
								continue;
							}
						}
					  }
					});
				}
				if(message != ""){
					alert(message);
				}
				return flag;
			}
			
			function show_div(index){
		  		$("#changeCredit_" + index).show();
		  	}
			
			function changeCredit(id,index){
				if(window.confirm("确认修改学分？")){
		  			var assign_id = id;
			  		var assign_credit = $("#value_"+index).val();
			  		if(assign_credit>10){
			  			alert("学分不能超过10分,请重新填写");
			  			return ;
			  		}
			  		
			  		$.ajax({
					  type: 'POST',
					  url: "changeCredit.action",
					  data: {assign_id:assign_id,assign_credit:assign_credit},
					  async:false,//同步
					  success: function(data){
			  			$("#now_value_"+index).html(assign_credit);
			  			$("#changeCredit_" + index).hide();
					  }
					});
		  		}
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我的培训列表" /></div>
			</li>
		</ul>
		<!-- 内容 --> 
		<table width="100%">
			
			<s:form action="myLineTrainingCourse.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td>培训班名称<input  name="lineTrainingCourse.name" /></td>
				   <td>培训类别
						<SELECT  style="WIDTH: 110px" name="lineTrainingCourse.train_type_id" 
					      onchange="this.value=this.options[this.selectedIndex].value;">
					        <OPTION value="-1" selected>选择培训类别</OPTION>
					        <s:iterator value="trainTypes">
					        	<option value="<s:property value="id"/>">
									<s:property value="name"/> 
								</option>
					        </s:iterator>
					    </SELECT>			           
			       </td>
			       <td>
			       		发布时间
									从<INPUT class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onClick="setday(this)">
	       						 	到<INPUT class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onClick="setday(this)">
			       </td>
				   <td colspan="2">
		           	 	<input  type="button" onClick="do_submit();" value="搜索" >
		           </td>
				</tr>
			</table>
			</s:form>
		<table width="100%">
			<tr>
			
			
			<td valign="top" align="left"> 
			<s:if test="lineTrainingCourseList.size==0"><table height="80" align="center" width="100%">
			  <tr align="center"><td align="center" > 没有线下培训班</td></tr></table></s:if>
			<s:else>
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th width="30"></th>
					<th height="30" align="center" >培训名称</th>
					<th width="40" height="30" align="center" >学分</th>
					<th width="8%" align="center" >发布时间</th>
					<!--<th width="8%" align="center" >培训开始时间</th>
					<th width="8%" align="center" >培训结束时间</th>
					<th width="8%" height="30" align="center" >联系人</th>-->
					<th width="60" height="30" align="center" >价格</th>
					<th width="5%" height="30" align="center" >培训类别</th> 
					<th width="70" height="30" align="center" >分配人员</th> 
					<th width="70" height="30" align="center" >报名审核</th> 
					<th width="70" height="30" align="center" >结果录入</th> 	
					<th width="70" height="30" align="center" >开通状态</th> 
					<th width="8%" height="30" align="center" >报名/总人数</th>
					<th width="5%" height="30" align="center" >操作</th> 
					<!-- <th width="5%" height="30" align="center" >开通审核</th>	 -->																			
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="lineTrainingCourseList" status="status">
					<tr>
						<td width="30" height="20" align="center">
							<input type="checkbox" value="<s:property value="id"/>"
								name="id">
					  </td>
						<td height="30" align="left" bgcolor="#FFFFFF" style="color:#CC0099;padding-left:15px;">
							<s:property value="name" />					  </td>
						<td width="40" height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;" onClick="show_div(<s:property value='#status.index+1'/>);">
							<p id="now_value_<s:property value='#status.index+1'/>"><s:property value="credit" /></p>
							<div style="display:none;" id="changeCredit_<s:property value='#status.index+1'/>">
								<input id="value_<s:property value='#status.index+1'/>"/>
								<br>
								<input type="button" class="textbg4" value="确认" onClick="changeCredit(<s:property value='id'/>,<s:property value='#status.index+1'/>);"/>
							</div>
					  </td>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:date name="createtime" format="yyyy-MM-dd hh:mm:ss"/>
						</td>
						
						<!--<td align="center" ><s:date name="train_begintime" format="yyyy-MM-dd hh:mm:ss"/></td>
						<td align="center" ><s:date name="train_endtime" format="yyyy-MM-dd hh:mm:ss"/></td>
						<td align="center" ><s:property value="contact" /></td>-->
						<td width="60"  height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="fee_price" />		
					  </td>
						
						<td height="30" align="center" >
							<s:property value="trainType.name" />		
						</td>
						<td width="70"  height="30" align="center" >
							<s:if test="is_open == 1">
								<a href="allocation_person.action?lineTrainingCourse.id=<s:property value='id'/>&sub_department=<s:property value='sub_department'/>" class="textbg4">分配</a>
							</s:if>
					  </td> 
						<td width="70"  height="30" align="center" >
							<s:if test="is_open == 1">
								<a href="goto_shenhe.action?lineTrainingCourse.id=<s:property value='id'/>" class="textbg4">审核</a>
							</s:if>
					  </td> 
						<td width="70"  height="30" align="center" >
							<s:if test="is_open == 1">
								<a href="result_entry.action?lineTrainingCourse.id=<s:property value='id'/>" class="textbg4">开始</a>
							</s:if>
					  </td> 
						<td width="70"  height="30" align="center" >
							<s:property value="Is_open_chinese" />
					  </td> 
						<td  height="30" align="center" >
							<span style="color:red"><s:property value="has_signed_number" /></span>(<s:property value="person_number_plan" />)
						</td> 
						<td height="30" align="center" >
							<s:if test="is_open != 1">
								<a href="updateLineTrainingCourseInit.action?id=<s:property value='id'/>" class="textbg4">编辑</a>
							</s:if>
							<a href="showLineTrainingCourseView.action?id=<s:property value='id'/>" class="textbg4">查看</a>
						</td>
				</s:iterator></tbody> 
		  </table>
		  
		  <wysLib:page></wysLib:page>
		  <div>
			  <center>
				  	<a href="javascript:select_All()" />全选</a>
					<a href="javascript:select_Fan()" />反选</a>
					<a href="javascript:select_Bux()" />全不选</a>
					<a href="javascript:del()" />删除</a>
			  </center>
		  </div>
		  <form action="lineTrainingCourseByIds.action" name="assign" method="post">
		  		<input type="hidden" name="ids" id="ids" />
		  		<input type="hidden" name="type" value="del" />
		  </form>
		  </s:else></td></tr></table> 
		<!-- 内容 -->
		<a href="addLineTrainingCourseInit.action" class="textbg">添加培训班</a>
	
	</body>
</HTML>
				