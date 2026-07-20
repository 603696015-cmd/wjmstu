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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<SCRIPT type="text/javascript">
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = "100%";
				oFCKeditor.ReplaceTextarea();
			}
			function searchKnowledgeTreeInit(){
				 width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("selectKnowledgeTreeInit.action?x="+Math.random(),null,sFeature);
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("-=wys=-");
					 document.getElementById("kledge.klTree.id").value=bh[2];
					 document.getElementById("kledge.klTree.name").value=bh[1];
				 }
			}
			
			function competence(type,competenceType){
				var userids_dom;
				var departments_dom;
				if(type == 1){
					userids_dom = document.getElementById("userids_view");
					departments_dom = document.getElementById("departments_view");
				}else if(type == 2){
					userids_dom = document.getElementById("userids_update");
					departments_dom = document.getElementById("departments_update");
				}else if(type == 3){
					userids_dom = document.getElementById("userids_delete");
					departments_dom = document.getElementById("departments_delete");
				}else if(type == 4){
					userids_dom = document.getElementById("userids_copy");
					departments_dom = document.getElementById("departments_copy");
				}else if(type == 5){
					userids_dom = document.getElementById("userids_download");
					departments_dom = document.getElementById("departments_download");
				}
				
				var user_ary ;
				var departments_ary;
				var user ;
				var department;
				var userids = "";
				var departmentids = "";
				var user_html = "" ;
				var department_html = "";
				var ccount = 5;//每行显示10个人
				if(competenceType == 1){//按人员授权
					width=screen.availWidth * 0.8;;
					height=screen.availHeight * 0.8;;
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				  	var rv =  window.showModalDialog("competenceByUserInit.action?x="+Math.random(),null,sFeature);
					if(rv!=undefined&&rv!=""){
						user_ary = rv.split(",");
						if(user_ary!=undefined && user_ary.length>0){
							user_html = "<table id=userids_table_"+type+" border='0'  cellspacing='1' cellpadding='1'><tr>";
							for(var i=0;i<user_ary.length;i++){
								user = user_ary[i].split("==");
								userids += user[0] + ",";
								user_html += "<td id=userids_td_"+type+"_"+i+">"+user[1] + "<a href='javascript:removeByUserid("+user[0]+","+type+","+i+");' ><span style='color:blue;corsor:hand'>X</span></a>" + "&nbsp;&nbsp;" +"</td>";
							}
							user_html += "</tr><table>";
						}
						if(user_html!=""){
							$("#users_div_1_"+type).html(user_html);
						}
						if(userids!="" && userids.charAt(userids.length-1)==","){
							userids = userids.substring(0,userids.lastIndexOf(","));
							userids_dom.value = userids;
						}
					}
				}else if(competenceType == 2){//按部门授权
					width=screen.availWidth * 0.8;;
					height=screen.availHeight * 0.8;;
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				  	var rv =  window.showModalDialog("competenceByDepartmentInit.action?fromAdd=1&competenceType="+type+"&x="+Math.random(),null,sFeature);
				  	if(rv!=undefined&&rv!=""){
				  		//departments.value = 
				  		departments_ary = rv.split(",");
				  		if(departments_ary!=undefined && departments_ary.length>0){
				  			department_html = "<table id=departmentids_table_"+type+" border='0'  cellspacing='1' cellpadding='1'><tr>";
							for(var i=0;i<departments_ary.length;i++){
								department = departments_ary[i].split("==");
								departmentids += department[0] + ",";
								department_html += "<td id=departmentids_td_"+type+"_"+i+">"+department[1] + "<a href='javascript:removeByDepartmentid("+department[0]+","+type+","+i+");' ><span style='color:blue;corsor:hand'>X</span></a>" + "&nbsp;&nbsp;"+"</td>";
							}
							department_html += "</tr><table>";
						}
						if(department_html!=""){
							$("#departments_div_1_"+type).html(department_html);
						}
						if(departmentids!="" && departmentids.charAt(departmentids.length-1)==","){
							departmentids = departmentids.substring(0,departmentids.lastIndexOf(","));
							departments_dom.value = departmentids;
						}
				  	}
				}
				
			}
			function getCompetenceType(type){
				var competenceType = "";
				switch(type)
				{
				case 1:
					competenceType = "view";
					break;
				case 2:
					competenceType = "update";
					break;
				case 3:
					competenceType = "delete";
					break;
				case 4:
					competenceType = "copy";
					break;
				case 5:
					competenceType = "download";
					break;
				}
				return competenceType;
			}
			function removeByUserid(userid,type,i){
				$("#userids_td_"+type+"_"+i).remove();
				var userids = document.getElementById("userids_"+getCompetenceType(type));
				var val = "";
				var arr;
				var val_ = "";
				if(userids!=undefined){
					val = userids.value;
					if(val!=""){
						arr = val.split(",");
						for(var i=0;i<arr.length;i++){
							if(parseInt(arr[i]) != parseInt(userid)){
								val_ += arr[i]+",";
							}
						}
					}
				}
				if(val_!="" && val_.charAt(val_.length-1)==","){
					val_ = val_.substring(0,val_.lastIndexOf(","));
				}
				userids.value = val_;
			}
			function removeByDepartmentid(departmentid,type,i){
				$("#departmentids_td_"+type+"_"+i).remove();
				var departmentids = document.getElementById("departments_"+getCompetenceType(type));
				var val = "";
				var arr;
				var val_ = "";
				if(departmentids!=undefined){
					val = departmentids.value;
					if(val!=""){
						arr = val.split(",");
						for(var i=0;i<arr.length;i++){
							if(parseInt(arr[i]) != parseInt(departmentid)){
								val_ += arr[i]+",";
							}
						}
					}
				}
				if(val_!="" && val_.charAt(val_.length-1)==","){
					val_ = val_.substring(0,val_.lastIndexOf(","));
				}
				departmentids.value = val_;
			}
			
			
			function doSubmit(){
				var titleObj=document.getElementById("kledge.name");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("知识名称不能为空!");
					titleObj.focus();
					return false;
				}
				var konwledgeTreeObj = document.getElementById("kledge.klTree.name");
				var knowledgeTree = konwledgeTreeObj.value.replace(/(\s*$)/g, "");
				if(knowledgeTree==""){
					alert("知识类别不能为空!");
					konwledgeTreeObj.focus();
					return false;
				}
				return true;
			}
			
			function preview() {
				var obj = document.getElementById("fujian").value;
				if(obj==""){
					alert("无附件,无法预览!");
					return;
				}
				width=900;
				height=600;
	  	 		var sFeature = "dialogHeight:"+height+"px;dialogWidth:"+width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
	  	 		//获取stuffid
	  	 		//elstuffs/1193/1204.doc
	  	 		var indexStart = obj.lastIndexOf("/");
	  	 		var indexEnd = obj.lastIndexOf(".");
	  	 		var qstuffid = obj.substring(indexStart+1,indexEnd);
				window.showModalDialog("question_stuffPreview.action?qstuff.id="+qstuffid+"&x="+Math.random(),null,sFeature);
			}
		</SCRIPT>
				<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	padding:5px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
.STYLE1 {color: #3399FF}
                .STYLE2 {color: #FF0000}
                </style>
	</HEAD>
	<body  onLoad="myload();">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz" style="padding:0px;"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加知识" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz" style="padding:0px;">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		
		<s:form action="addKledge.action" method="post" name="kledge_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" name="kledge.klTree.id" id="kledge.klTree.id"/>
			<table width="100%" cellpadding="1" align="center" cellspacing="1"
				bgcolor="#ECEDEB">
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF">
						<span class="neededitem STYLE2">*</span><span class="STYLE1">知识名称：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="kledge.name" id="kledge.name" size="60" />
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF">
						<span class="neededitem STYLE2">*</span><span class="STYLE1">知识类别：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield theme="simple" name="kledge.klTree.name" 
								size="20" readonly="true" id="kledge.klTree.name"/>
			 				<a href="#" onClick="searchKnowledgeTreeInit();return false;" class="textbg5">点此进行选择</a>						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  有效期：					</span></td>
					<td align="left" bgcolor="#FFFFFF">
						开始时间：<input size="50" class="Wdate" name="kledge.begintime" readonly="readonly"
							type="text" onClick="setday(this)" id="kledge.begintime" />
						结束时间<input size="50" class="Wdate" name="kledge.endtime" readonly="readonly"
							type="text" onClick="setday(this)" id="kledge.endtime" />					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  制定部门：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="kledge.depname" id="kledge.depname" size="60" />
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  制作人：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="kledge.zhizuoren" id="kledge.zhizuoren" size="60" />
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  发布人：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<span><s:property value="kledge.fabuUser.realname" /></span>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<span><s:property value="kledge.fabuUser.department.name" /></span>
							<input type="hidden" name="kledge.fabuuserid" id="kledge.fabuuserid" value="<s:property value="kledge.fabuUser.id" />" />						</label>					</td>
				</tr>
				
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  查看权限：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<input type="hidden" name="userids_view"/>
							<input type="hidden" name="departments_view"/>
							<p><a href="javascript:competence(1,1);" class="textbg5" >按人员授权</a> </p>
							<span id="users_div_1_1"></span>
							<p><a href="javascript:competence(1,2);" class="textbg5" >按部门授权</a> </p>
							<span id="departments_div_1_1"></span>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  修改权限：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<input type="hidden" name="userids_update"/>
							<input type="hidden" name="departments_update"/>
							<p ><a href="javascript:competence(2,1);" class="textbg5" >按人员授权</a> </p>
							<div id="users_div_1_2"></div>
							<p ><a href="javascript:competence(2,2);" class="textbg5" >按部门授权</a> </p>
							<span id="departments_div_1_2"></span>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  删除权限：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<input type="hidden" name="userids_delete"/>
							<input type="hidden" name="departments_delete"/>
							<p ><a href="javascript:competence(3,1);" class="textbg5" >按人员授权</a> </p>
							<div id="users_div_1_3"></div>
							<p ><a href="javascript:competence(3,2);" class="textbg5" >按部门授权</a> </p>
							<span id="departments_div_1_3"></span>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  复制权限：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<input type="hidden" name="userids_copy"/>
							<input type="hidden" name="departments_copy"/>
							<p ><a href="javascript:competence(4,1);" class="textbg5" >按人员授权</a> </p>
							<div id="users_div_1_4"></div>
							<p ><a href="javascript:competence(4,2);" class="textbg5" >按部门授权</a> </p>
							<span id="departments_div_1_4"></span>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  下载权限：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<input type="hidden" name="userids_download"/>
							<input type="hidden" name="departments_download"/>
							<p ><a href="javascript:competence(5,1);" class="textbg5" >按人员授权</a> </p>
							<div id="users_div_1_5"></div>
							<p ><a href="javascript:competence(5,2);" class="textbg5" >按部门授权</a> </p>
							<span id="departments_div_1_5"></span>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  附件：					</span></td>
					<td bgcolor="#FFFFFF">
						<s:textfield name="kledge.fujian" id="fujian" size="60" />
						<a href="javascript:setUrl('fujian');" class="textbg5">浏览资源库</a>					
						<a href="javascript:preview();" class="textbg5">预览</a>		
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" bgcolor="#FFFFFF">
						知识简介
					</td>
				</tr>
		   </table>
			<div style="text-align: center; width: 100%">
				<s:textarea name="kledge.jianjie" id="content" cols="60" rows="7"
					cssStyle="width: 980px; height: 440px;; visibility: hidden;" />
			</div>
			<div style="text-align: center;">
				<input class="textbg5" type="submit" value="确认添加">
			</div>
		</s:form>
	
	</body>
</HTML>
