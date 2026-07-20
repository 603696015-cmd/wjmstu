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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		assignSearch_assignment.submit();
		 	}
		
			function do_submit(){
				assignSearch_assignment.submit();
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我的结果录入列表" /></div>
			</li>
		</ul>
		<!-- 内容 --> 
		<table width="100%">
				<tr> 
				<td valign="top" width="150px;">
					<wysLib:dep_list_aj rootAble="true"
					href="result_entry.action?sub_department=1&lineTrainingCourse.id=${lineTrainingCourse.id}&department.id=" iname="department.id" ></wysLib:dep_list_aj>	
				</td>
					<td valign="top"> 
			
			<s:form action="result_entry.action?sub_department=1" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<input type="hidden" name="lineTrainingCourse.id" value="<s:property value='lineTrainingCourse.id'/>"/>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<tr>
				
				   <td>姓名<input  name="assign.elUser.realname" /></td>
				   <td>
				   		<SELECT  style="WIDTH: 110px" name="assign.is_get_certificate" 
					      onchange="this.value=this.options[this.selectedIndex].value;">
					        <OPTION value=-1 selected>选择获证状态</OPTION>
				        	<option value=0>
								未获证 
							</option>
							<option value=1>
								已获证 
							</option>
					    </SELECT>
				   </td>
				   <td colspan="2">
		           	 	<input style="CURSOR: hand"  type="button" onclick="do_submit();" value="查询" >
		           </td>
				</tr>
			</table>
			</s:form>
		<table width="100%">
			<tr>
			
			
			<td valign="top" align="left"> 
			<s:if test="assignList.size==0"><table height="80" align="center" width="150"><tr align="center"><td align="center" > 没有待录入成绩学员</td></tr></table></s:if>
			<s:else>
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th></th>
					<th width="10%" height="30" align="center" >学员姓名</th>
					<th width="10%" align="center" >是否获证</th>
					<th width="10%" align="center" >成绩</th>
					<th width="10%" align="center" >学分数</th>
					<th width="10%" height="30" align="center" >获得学分</th>
					<th width="50%" height="30" align="center" >相关附件</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="assignList" status="status">
					<tr>
						<td width="20" height="20" align="center">
							<input type="hidden" value="<s:property value="id"/>"
								name="id">
						</td>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="elUser.realname" />
						</td>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;" onclick="changeIs_get_certificate(<s:property value='id'/>,<s:property value='line_training_course_id'/>,<s:property value='#status.index+1'/>);">
							<div id="now_value1_<s:property value='#status.index+1'/>">
								<s:if test="is_get_certificate == 0">
									<p id="value_no_<s:property value='#status.index+1'/>">否</p>
								</s:if>
								<s:else>
									<p id="value_yes_<s:property value='#status.index+1'/>">是</p>
								</s:else>
							</div>
						</td>
						
						<td align="center" onclick="show_div(<s:property value='#status.index+1'/>);">
							<p id="now_value_<s:property value='#status.index+1'/>"><s:property value="score" /></p>
							<div style="display:none;" id="changeScore_<s:property value='#status.index+1'/>">
								<input id="value_<s:property value='#status.index+1'/>"/>
								<br>
								<input type="button" class="textbg4" value="确认" onclick="changeScore(<s:property value='id'/>,<s:property value='#status.index+1'/>);"/>
							</div>
						</td>
						<td align="center" >
							<p id="credit_y_<s:property value='#status.index+1'/>"><s:property value="lineTrainingCourse.credit" /></p>
						</td>
						<td align="center" id="credit_<s:property value='#status.index+1'/>">
							<s:if test="is_get_certificate == 0">
								0.0
							</s:if>
							<s:else>
								<div ">
									<p ><s:property value="lineTrainingCourse.credit" /></p>
								</div>
							</s:else>
						</td>
						<td  height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
								<form action="accessory_update.action" enctype="multipart/form-data"	method="post"> 
									<input type="file" name="st" > 
									<input type="hidden" name="assign.id" value="<s:property value="id"/>"> 
									<input type="hidden" name="assign.line_training_course_id" value="<s:property value="line_training_course_id"/>"> 
									<input type="hidden" name="assign.userId" value="<s:property value="userId"/>"> 
									<s:if test="accessory.length() > 0">
										<input type="submit" value="替换">
							 			<a href="downloadInit.action?fileName=<s:property value="id"/>_accessory_<s:property value="line_training_course_id"/>_<s:property value="userId"/>.<s:property value="accessory"/>" target="_blank" style="color:red">下载</a> 
									</s:if>
									<s:else>
										<input type="submit" value="上传"> 
									</s:else>
								</form>
						</td>
					</tr>
				</s:iterator>
				
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						合计
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<s:i18n name="Format">
						   <s:text name="FormatNumeral" >
						       <s:param value="total_credit"/>
						    </s:text>
						</s:i18n>
						分
					</td>
					<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<p id="total_get_credit">
							<s:i18n name="Format">
							   <s:text name="FormatNumeral" >
							       <s:param value="total_get_credit"/>
							    </s:text>
							</s:i18n>
							分
						</p>
					</td>
					<td></td>
				</tr>
				</tbody> 
		  </table>
		  
		  <wysLib:page></wysLib:page>
		  <form action="option_in_result_entryPage.action" name="assign_option" method="post">
		  		<input type="hidden" name="ids" id="ids" />
		  </form>
		  <script type="text/javascript">
		  	function show_div(index){
		  		$("#changeScore_" + index).show();
		  	}
		  
		  	function changeScore(id,index){
		  		if(window.confirm("确认修改成绩？")){
		  			//$("#changeScore_" + index).hide();
		  			var assign_id = id;
			  		var assign_score = $("#value_"+index).val();
			  		if(assign_score>100){
			  			alert("分数不能超过100分,请重新填写");
			  			return ;
			  		}
			  		
			  		$.ajax({
					  type: 'POST',
					  url: "changeScore.action",
					  data: {assign_id:assign_id,assign_score:assign_score},
					  async:false,//同步
					  success: function(data){
			  			$("#now_value_"+index).html(assign_score);
			  			$("#changeScore_" + index).hide();
					  }
					});
		  		}
		  	}
		  	
		  	function changeIs_get_certificate(id,line_training_course_id,index){
		  		if(window.confirm("确认切换状态？")){
		  			var now_value=$("#now_value1_"+index).text();
		  			var change_is_get_certificate = 0;
		  			if(now_value == "是"){
		  				change_is_get_certificate = 0;
		  			}else{
		  				change_is_get_certificate = 1;
		  			}
		  			$.ajax({
					  type: 'POST',
					  url: "changeIs_get_certificate.action",
					  data: {change_is_get_certificate:change_is_get_certificate,assign_id:id},
					  async:false,//同步
					  success: function(data){
					  	if(now_value == "是"){
					  		$("#now_value1_"+index).text("否");
					  		
					  		//将获得学分至为0.0
					  		$("#credit_"+index).text("0.0");
					  		//更新数据库中学分,将合计学分重新计算
					  		$.ajax({
								  type: 'POST',
								  url: "updateCredit.action",
								  data: {assign_id:id,line_training_course_id:line_training_course_id,assign_credit:0},
								  async:false,//同步
								  success: function(data){
								  	//成功后修改总学分数
								  	 var total_get_credit = eval("("+data+")").total_get_credit;
								  	 $("#total_get_credit").html(""+total_get_credit+"分");
								  }
								});
					  	}else{
					  		$("#now_value1_"+index).text("是");
					  		//将获得学分至为学分数,合计获得学分做修改
					  		$("#credit_"+index).text($("#credit_y_"+index).text());
					  		$.ajax({
								  type: 'POST',
								  url: "updateCredit.action",
								  data: {assign_id:id,line_training_course_id:line_training_course_id,assign_credit:$("#credit_y_"+index).text()},
								  async:false,//同步
								  success: function(data){
								  	//成功后修改总学分数
								  	 var total_get_credit = eval("("+data+")").total_get_credit;
								  	 $("#total_get_credit").html(""+total_get_credit+"分");
								  }
								});
					  	}
					  }
					});
		  		}
		  	}
		  </script>
		  </s:else></td></tr></table> 
		<!-- 内容 -->
	</BODY>
</HTML>
				