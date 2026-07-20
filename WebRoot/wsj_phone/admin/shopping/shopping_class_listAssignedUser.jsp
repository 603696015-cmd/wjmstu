<%@ page language="java" pageEncoding="UTF-8"   %>
<%@page import="com.sopia.classman.entities.ElClass"%> 
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
		<TITLE>分配学员</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>		
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/pageutil.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
		function _onLoad(type,classid,eroomid){ 
				var toUserInfo = document.getElementById("toUserInfo");
				var toClassInfo = document.getElementById("toClassInfo");
				var toEroomInfo = document.getElementById("toEroomInfo"); 
				if(type == 0){ 
					toUserInfo.style.display="block";
					toClassInfo.style.display="none";
					toEroomInfo.style.display="none";
				}else if(type == 1){ 
					toUserInfo.style.display="none";
					toClassInfo.style.display="block";
					toEroomInfo.style.display="none"; 
				}else if(type == 2){ 
					toUserInfo.style.display="none";
					toClassInfo.style.display="none";
					toEroomInfo.style.display="block";
				}else{
					alert("未知类型！");
				}  
				if(classid != 0){
					document.getElementById("PXB").style.display="block";
				} 
				if(eroomid != 0){
					document.getElementById("KC").style.display="block";
					document.getElementById("KCSQ").style.display="block";  
					
				} 
		}
		function deleteClassInfo(obj,id,optype){
			if(window.confirm("确定删除？")){  
				obj.parentNode.parentNode.removeChild(obj.parentNode);
				document.getElementById("PXB").style.display="block";
			}
		}		
		
		function deleteEroomInfo(obj,id,optype){
			if(window.confirm("确定删除？")){ 
				obj.parentNode.parentNode.removeChild(obj.parentNode);
				document.getElementById("PXB").style.display="block";
			}
		}
		var toc=0;
		function DistributionMethods(type){
			var toUserInfo = document.getElementById("toUserInfo");
			var toClassInfo = document.getElementById("toClassInfo");
			var toEroomInfo = document.getElementById("toEroomInfo");
			if(type == 0){ 
				toUserInfo.style.display="block";
				toClassInfo.style.display="none";
				toEroomInfo.style.display="none";
				toc=0;
			}else if(type == 1){ 
				toUserInfo.style.display="none";
				toClassInfo.style.display="block";
				toEroomInfo.style.display="none"; 
				toc=0;
			}else if(type == 2){ 
				toUserInfo.style.display="none";
				toClassInfo.style.display="none";
				toEroomInfo.style.display="block";
				toc=1;
			}else{
				alert("未知类型！");
			} 
		}
		var index_=-1;
		function addExamRoomUserinfo(id){
			index_++;
			var _d = $("<div>");
			$(_d).attr("id","_kc_u"+id);
			$(_d).css("width","100%");
			$(_d).css("height","14px");
			$(_d).css("background","#edefff");
			$(_d).css("float","left");
			$(_d).css("border","solid #fff 1px");
			$.ajax(
			{	async:false,
				type:"post",   
			    url:"mess_getExamRoomUserInfo.action",   
			    data:{"examRoom.id":id,"input_name": "examRooms.id","x":Math.random()},   
				success:function(data){
						var jsondata = eval("("+data+")");
			$(_d).html(
				//'<input type="hidden" name="'+jsondata.input_name+'" value="'+jsondata.id+'">'+
				'<input type="hidden" name="erParas['+index_+'].examRoom.id" value="'+jsondata.id+'">'+
				'<label style="width:300px;float:left;text-align:left;">'+
				'名称：'+jsondata.title+'</label>'+
				'<label style="float:left;text-align:left;">'+
				'是否通过：<select name="erParas['+index_+'].isPassed">'+
				'<option value="-1">全部</option>'+
				'<option value="1">是</option>'+
				'<option value="2">否</option>'+
				'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
				'考场成绩：<select name="erParas['+index_+'].examScoreTerm">'+
				'<option value=">=">>=</option>'+
				'<option value=">">></option>'+
				'<option value="=">=</option>'+
				'<option value="<"><</option>'+
				'<option value="<="><=</option>'+
				'</select><input type="text" maxlength="4" style="width:30px;" name="erParas['+index_+'].examScore" value="0" />'+
				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
				'<select name="erParas['+index_+'].linkTerm">'+
				'<option value="or">or</option>'+
				'<option value="and">and</option>'+
				'</select>'+
				'</label>'+
				'<a style="cursor:hand;float:right;width:14px;height:14px;" href="" onclick="javascript:deleteExamRoomUserinfo(this,'+jsondata.id+');return false;">X</a>');
			 }});
			$("#KC").append(_d);
			if($("#KC").html()!=""){
				$("#KC").css("display","block");
			}
		}
		</script>
		<script type="text/javascript">
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
		function assign(){
			 if(window.confirm("确定分配？！")){
				var checkObj = document.getElementsByName("id");
			    var billIDs = "";
			    for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				 }
				if(billIDs==""){
				  alert("请选择要分配的记录！");
				  return ;
			    }
			    var userids = document.getElementById("userids");
			    userids.value=billIDs;
			    if(toc==0){
					course_assignment.action="shopping_elclass_newassign2user_add.action";
					course_assignment.submit();
				}else{
		 			//ajax
		 			var pa1 = $("#course_assignment").serialize();
		 			$.post("elclass_newassign2user_add.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
		 				seachOnEroomPage($("#pageNow2").val());
		 			});
		 		}
			 }
		}
		function unassign(){
		  if(window.confirm("确定取消分配？！")){
		     var checkObj = document.getElementsByName("id");
			    var billIDs = "";
			    for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				 }
				if(billIDs==""){
				  alert("请选择要取消分配的记录！");
				  return ;
			    }
			  var userids = document.getElementById("userids");
		      userids.value=billIDs;
		      if(toc==0){
			 	 course_assignment.action="elclass_newassign2user_delete.action";
			 	 course_assignment.submit();
			  }else{
	 			  //ajax
	 			  var pa1 = $("#course_assignment").serialize();
	 			  $.post("elclass_newassign2user_delete.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
	 			  	seachOnEroomPage($("#pageNow2").val());
	 			  });
	 		  }
			}
		}
	    function assignSearch(){
			//course_assignment.action="elclass_newassign2user_addAll.action?status=0";
			//course_assignment.submit();
			//alert(toc);
			if(toc==0){
				sousuo.action="elclass_newassign2user_addAll.action?status=0";
				sousuo.submit();
			}else{
 			    //ajax
 			    var pa1 = $("#sousuo").serialize();
 			    $.post("elclass_assignUserAll.action",pa1+"&ajax=1&x="+Math.random(), function (data) {
 			    	seachOnEroomPage($("#pageNow2").val());
 			    });
 		    }
	    }
	  	function page(i) { 
			document.getElementById("pageNow").value=i;
			acc_list.submit();
		}
		function doForm(){
			$("#pageNow2").val(0);
			sousuo.submit();
		}
		function seachOnEroom(queryManner){
			$("#queryManner").val(queryManner);
		 	seachOnEroomPage(0);
	 	}
	 	function seachOnEroomPage(pn){
	 	$("#pageNow2").attr("value",pn);
	 	var pa1 = $("#sousuo").serialize();
		 	//alert(pa1);
			$.post("examroom_seachUser_class.action", pa1, function (data) {
				if(data=='err1'){
					alert("没有选择考场");
				}else{
					var jsondata = eval("("+data+")");
					var cnt =jsondata.count;
					var ulist = jsondata.users;
					//alert(pn);
					$("#page_div").html(getPageDiv(cnt,pn,10))
					var dls = $("#data_list").find("tr");
					for(var i =0;i<dls.length;i++){
						$(dls[i]).remove();
					}
					for(var i = 0;i<ulist.length;i++){
						var tr = $("<tr>");
						var x = (ulist[i].joinwayInt==0||ulist[i].joinwayInt==2)?'<input type="checkbox" name="id" value="'+ulist[i].id+':'+ulist[i].assign+'" />':"";
						tr.append('<td width="20" height="20" align="center">'+x +
									//<s:if test="joinwayInt==0||joinwayInt==2">
									//	<input type="checkbox" name="canAssignUsers.id"
									//		value="<s:property value="id"/>" />
									//</s:if>
								'</td><td height="30" align="center">'+
									ulist[i].rolename+
								'</td><td height="20" align="center">'+
									ulist[i].sex +
								'</td> <td height="20" align="center">'+
									ulist[i].username+
								'</td> <td height="20" align="center">'+
									ulist[i].depname+
								'</td> <td height="20" align="center">'+
									ulist[i]. jz +
								'</td> <td height="20" align="center">'+
								 ulist[i].age +
								'</td> <td height="20" align="center">'+
									ulist[i].rolename  +
								'</td> <td height="20" align="center">'+
									ulist[i].assign  +
								'</td> <td height="20" align="center">'+
									ulist[i].joinway+
								'</td>' );
						$("#data_list").append(tr);
					}
				}
			});
			}
	</script>
	</HEAD>
	<BODY onLoad="_onLoad(<s:property value="DBMethods"/>,'<s:property value="elClass.id"/>','<s:property value="examRoom.id"/>')">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="分配学员" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<table width="100%">
			<tr>
				<td colspan="2">
					<s:form action="shopping_elclass_assign2userInit" method="post" theme="simple" name="sousuo" id="sousuo">  
					<s:hidden name="department.id" /> 
					<s:hidden name="examRoom.queryManner" id="queryManner" />
					<input type="radio" name="DBMethods" value="0" id = "DBM" onclick="DistributionMethods(0)" <s:if test="DBMethods == 0">checked="checked"</s:if>/> 按用户信息查询
					<input type="radio" name="DBMethods" value="1" id = "DBM" onclick="DistributionMethods(1)" <s:if test="DBMethods == 1">checked="checked"</s:if>/> 按培训班成绩查询
					<input type="radio" name="DBMethods" value="2" id = "DBM" onclick="DistributionMethods(2)" <s:if test="DBMethods == 2">checked="checked"</s:if>/> 按用考场成绩查询
					<div id="toUserInfo" style="display:block">
							<s:hidden name="elclass.id" />
							<s:hidden name="examRoom.id" />
							<s:hidden name="examPaper.id" />
							<s:hidden name="pN" id="pageNow2" />
							 <table align="center" cellpadding="1" cellspacing="1" width="100%"  
								>
								<tr>
									<td>
										<%-- 
										<wysLib:BasetName btid="4" />： 
												<s:select name="elUser.gangwei" cssClass="g-select"
														list="gangweis" listKey="id" key="2" listValue="basevalue"  headerValue="全部" headerKey="0" /> 
										 --%>
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								<tr>
									<td> 
										姓名：<input name="elUser.realname"
											value="<s:property value="elUser.realname"/>"
											id="elUser.realname">
									</td>
									<td>
										账号：
										<input name="elUser.username"
											value="<s:property value="elUser.username"/>"
											id="elUser.username">
									</td>
									<td>
										生日开始时间:
										<input type="text" size="16"
											value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
											name="elUser.shengri" onclick="setday(this)" readonly>
									</td>
									<td>
										生日结束时间:
										<input type="text" size="16"
											value="<s:date format="yyyy-MM-dd" name="elUser.shengri_end"/>"
											name="elUser.shengri_end" onclick="setday(this)" readonly>
									</td> 
									<td>
										性别： 
										<select name="elUser.sex">
											<option value="" selected="selected">
												全部
											</option>
											<option value="男"
												<s:if test="elUser.sex==\"男\"">selected='selected'</s:if>>
												男
											</option>
											<option value="女"
												<s:if test="elUser.sex==\"女\"">selected='selected'</s:if>>
												女
											</option>
										</select>
									</td>
								</tr> 
								<tr>
									<td>
										搜索包含下级部门：
										<input type="checkbox" name="sub_department"	<s:if test="sub_department==1">checked="checked"</s:if>	id="sub_department" value="1"/>
									</td>
									<td>
											是否已分配：
								           <select name="elUser.isAssign">
						               		   <option ></option>
								               <option value="0">是</option>
								               <option value="1">否</option>
								           </select>
									</td>
									<td>
									</td>
									<td>
									</td>
									<td> 
										<input id="find" name="find" type="button" onclick="doForm();" value="搜索"><%-- 点搜索后应该初始化分页 --%>
									</td>
								</tr>
							</table>
					</div>
					<div id="toClassInfo" style="display:none" >
						 <table align="center" cellpadding="1" cellspacing="1" width="100%">
							 <tr>
							 	<td colspan="3"> 
									<div id="PXB" style="display:none;width: 100%;">  
										<s:if test="elClass != null">
											<span
												style="width: 150px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="width: 130px; float: left;">
													<s:property value="elClass.name" />
													<s:hidden name="elClasss[0].id"></s:hidden>
												</label> <a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteClassInfo(this,<s:property value="elClass.id"/>,'PXB');return false;">X</a>
											</span> 
										</s:if>
									</div>
									<span class="txt-info"><a href="#"
										onClick="searchElclassUser();return false;">点此进行选择培训班</a> </span>  
								</td>
							 </tr> 
							 <tr>
							 	<td>
								 	总 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分：  
								 	<s:textfield name="elUser.btotalscore"	id="btotalscore" />~<s:textfield name="elUser.btotalscore_"	id="btotalscore_" />
								</td>
							 	<td>
							 		是 否 通 过&nbsp;：
						           <select name="elUser.isPass" >
						               <option value="全部" <s:if test="elUser.isPass == '全部'">checked="checked"</s:if> >全部</option>
						               <option value="0" <s:if test="elUser.isPass == '0'">checked="checked"</s:if>>通过</option>
						               <option value="1" <s:if test="elUser.isPass == '1'">checked="checked"</s:if>>不通过</option>
						           </select> 
							 	</td> 
							 </tr>
							 <tr>
							 	<td>
							 		必修课总学分：
								 	<s:textfield name="elUser.bxscore"	id="bxscore" />~<s:textfield name="elUser.bxscore_"	id="bxscore_" />
								 </td>
							 	<td>
							 		选修课总学分：
								 	<s:textfield name="elUser.xxscore"	id="xxscore" />~<s:textfield name="elUser.xxscore_"	id="xxscore_" />
								 </td>
							 	<td> 
										<input id="find" name="find" type="button" onclick="doForm();" value="搜索"><%-- 点搜索后应该初始化分页 --%>
							 	</td> 
							 </tr>
						 </table> 
					</div>
					<%-- 
					<div id="toEroomInfo_nouser" style="display:none"> 
						 <table align="center" cellpadding="1" cellspacing="1" width="100%"> 
							 <tr>
							 	<td colspan="3"> 
									<div id="KC" style="display:none;width: 100%;">  
										<s:if test="examRoom != null">
											<span
												style="width: 150px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="width: 130px; float: left;">
													<s:property value="examRoom.title" />
													<s:hidden name="examRooms[0].id"></s:hidden>
												</label> 
												<a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteEroomInfo(this,<s:property value="examRoom.id"/>,'PXB');return false;">X</a> 
											</span> 
										</s:if>
									</div>
									<span class="txt-info"><a href="#"
										onClick="searchExamRoomUser();return false;">点此进行选择考场</a> </span>  
								</td>
							 </tr>  
							 <tr>
							 	<td> 
							 		总分：
								 	<s:textfield name="elUser.KcBtotalscore" id="KcBtotalscore" value="0"></s:textfield>~
								 	<s:textfield name="elUser.KcBtotalscore_" id="KcBtotalscore_" value="0"></s:textfield>
								 </td>
							 	<td>
							 		是 否 通 过&nbsp;：
						           <select name="elUser.isKcPass" >
						               <option value="全部" <s:if test="elUser.isKcPass == '全部'">checked="checked"</s:if> >全部</option>
						               <option value="0" <s:if test="elUser.isKcPass == '0'">checked="checked"</s:if>>通过</option>
						               <option value="1" <s:if test="elUser.isKcPass == '1'">checked="checked"</s:if>>不通过</option>
						           </select> 
								 </td>
							 	<td> 
										<input id="find" name="find" type="submit" value="搜索">
							 	</td> 
							 </tr>
							 <tr>
							 	<td colspan="3">
								 	<div id="KCSQ" style="display:none;width: 100%;">  
										<input type="hidden" name="examRooms.id" value="<s:property value="examRoom.id"/>">   
										<s:iterator value="examPapers">  
												<input type="hidden" name="elUser.epids" value="<s:property value="id"/>">  
												试卷【<s:property value="title" /> 】得分：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<s:textfield name="elUser.Kcsq"	id="Kcsq" value="不限"/>~
												<s:textfield name="elUser.Kcsq_"	id="Kcsq_" value="不限"/> 
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												考试次数:<s:textfield name="elUser.Kclxcs" id="Kclxcs" value="不限"/>~
												<s:textfield name="elUser.Kclxcs_"	id="Kclxcs_" value="不限"/> <br/>
										</s:iterator>
								 	</div> 
							 	</td>
							 </tr>
							 </table>
					</div>
					 --%>
					 <div id="toEroomInfo" style="display: none; text-align: center;">
						<div
							style="border: 1px solid #D1E4F5; width: 100%; margin-top: 10px;">
							<div id="KC" style="width: 100%; display: none;"></div>
							<div style="text-align: right; float: left;">
								<a href="javascript:searchExamRoomUser();">点此进行选择考场</a>
								<input type="button" onclick="seachOnEroom('1');" value="搜索" />
							</div>
						</div>
					</div>
				</s:form>
				<s:if test="elclass.isApplication==1">
					<div style="color:red;text-align:center;">当前培训班是可申请培训班，结业考场由系统自动分配</div> 
				</s:if>
				</td> 
			</tr>
			<tr>
			<td width="150px;" valign="top" bgcolor="#FFFFFF">
			<%
			   ElClass elclass=(ElClass)request.getAttribute("elclass");   
				int classid = elclass.getId();
				String url = "elclass_assign2userInit.action?elclass.id="+classid+"&sub_department=1&department.id=";
			 %>
			<wysLib:dep_list_aj rootAble="true"	href="<%=url %>" iname="department.id"></wysLib:dep_list_aj>			</td>
			<td align="left" valign="top" bgcolor="#FFFFFF">
		<s:if test="elusers.size==0">当前还没有分配学员</s:if>
			<s:else>
				<table style="margin-top:0px;" width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" align="left" bgcolor="#66CCFF" style="padding-left:8px;color:blue;">						</td>
					    <td height="30" align="center" bgcolor="#66CCFF" >
						姓名						</td>
						 <td height="30" align="center" bgcolor="#66CCFF" >
						性别						</td>
					   <td height="30" align="center" bgcolor="#66CCFF" >
							账号					  </td>
					    <td height="30" align="center" bgcolor="#66CCFF" >
							部门						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							<wysLib:BasetName btid="1" />
					  </td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							年龄						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							角色						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							分配						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							参加方式						</td>
					</tr>
					<s:if test="elusers.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前还没有分配学员
							</TD>
						</TR>
					</s:if>
					<s:else>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()" id="data_list">
						<s:iterator value="elusers">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							     <input type="checkbox" value="<s:property value="id"/>:<s:property value="isAssign"/>" name="id"> 
					          </td>
							    <td height="30" align="center" >
							      <s:property value="realname" />
						       </td>
						         <td height="30" align="center" >
							      <s:property value="sex" />
						       </td>
                                <td height="30" align="center" >
									<s:property value="username" />
								</td>
                                <td height="30" align="center" >
									<s:property value="department.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="jingzhong_" />
								</td>
								<td height="30" align="center" >
									<s:property value="age" />
								</td>
								<td height="30" align="center" >
									<s:property value="role.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="isAssign" />
								</td>
								<td height="30" align="center" >
									<s:property value="joinway" />
								</td>
							</tr>
						</s:iterator>
						</tbody>
					</s:else>
			  </table> 
			</s:else></td></tr></table>
			<div id="page_div">
				<wysLib:page></wysLib:page>
			</div>
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<br>
			<input value="分配" type="button" onClick="assign()">
			<input value="取消分配" type="button" onClick="unassign()">
			<input value="分配给全部搜索结果" type="button" onClick="assignSearch()">
			<a href="shopping_elclass_check_students.action?sub_department=1&elclass.id=<s:property value="elclass.id"/>&elUser.isAssign=0" class="textbg6">确认提交</a>
		</div>  
			<form action="elclass_assign2userInit.action" method="post" name="course_assignment" id="course_assignment">
				<s:hidden name="deptid" />
				<s:hidden name="department.id" />
				<s:hidden name="elclass.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.jingzhong" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="elUser.isAssign" />
				<s:hidden name="userids" id="userids"></s:hidden>
				<s:hidden name="sub_department" id="sub_department"></s:hidden>
				<s:hidden name="elclass.isApplication" />
	     </form>
		<!-- 内容 -->
	
	</body>
		<form action="shopping_elclass_assign2userInit.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid"/>
				<s:hidden name="elclass.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
				<s:hidden name="cltype.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.jingzhong" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" id="sub_department"></s:hidden>
				<s:hidden name="elUser.isAssign" />
				<s:hidden name="elUser.btotalscore" />
				<s:hidden name="elUser.btotalscore_" />
				<s:hidden name="elUser.bxscore" />
				<s:hidden name="elUser.bxscore_" />
				<s:hidden name="elUser.xxscore" />
				<s:hidden name="elUser.xxscore_" />
				<s:hidden name="elUser.isPass" /> 
				<s:hidden name="DBMethods" /> 
				<s:hidden name="elClasss[0].id"/> 
				<!-- 按考场搜索 -->
				<input type="hidden" name="elUser.isKcPass">  
				<input type="hidden" name="examRooms[0].id" value="<s:property value="examRoom.id"/>"> 
				<input type="hidden" name="elUser.KcBtotalscore">  
				<input type="hidden" name="elUser.KcBtotalscore_">  
				<s:iterator value="examPapers">  
						<input type="hidden" name="elUser.epids" value="<s:property value="id"/>"> 
						<input type="hidden" name="elUser.Kcsq">      
						<input type="hidden" name="elUser.Kcsq_">     
						<input type="hidden" name="elUser.Kclxcs">    
						<input type="hidden" name="elUser.Kclxcs_">     
				</s:iterator> 
	  </form>
</HTML>
