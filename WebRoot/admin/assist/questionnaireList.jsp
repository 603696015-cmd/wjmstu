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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
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
		</style>
		<script type="text/javascript"> 
				function page(i){
					document.getElementById("pageNow").value=i;
					examFh.submit();
					//document.location.href="examroomwithoutcourse_readlist.action?pS=<s:property value="pS"/>&pN="+i
				}
				function initPN(){
					document.getElementById("pageNow").value=0;
					examFh.submit();
				}
				function pollSh(id,status){
					if(window.confirm("确认提交？")){
						document.getElementById("pollId").value=id;
						document.getElementById("pollStatus").value=status;
						pollShForm.submit();
					}
				}
				
		</script>
		
		<script type="text/javascript">
			function Obj(pp_,valid_,uvalid_){ 
			this.pp=pp_; 
			this.valid=valid_;
			this.uvalid = uvalid_;
		} 
		//按钮
		var ToolsBarObj = null;
		var pp = [];
		var valid = [];
		var uvalid = [];
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_edit","编辑","images/newversion/un_view.gif","editDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_view","查看","images/newversion/un_view.gif","viewDetail()");
		//	ToolsBarObj.ToolsBar_Add("toolbar_copy","复制","images/newversion/un_view.gif","copyDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_fenpei","分配学员","images/newversion/un_view.gif","fenpeiDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_commit","创建完成","images/newversion/un_view.gif","commitDetail()");
		});
		
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			valid = obj.valid;
			uvalid = obj.uvalid;
			var value = 0;
			var va = 0;
			var uva = 0;
			if(pp.length>1){
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_copy");
				ToolsBarObj.ToolsBar_Disabled("toolbar_fenpei");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
			}else if(pp.length == 1){
				va= valid[0];
				uva = uvalid[0];
				if(va == 0 || va == 2){
					if(uva != 1){
						ToolsBarObj.ToolsBar_Enabled("toolbar_fenpei");
						ToolsBarObj.ToolsBar_Enabled("toolbar_commit");
					}else{
						ToolsBarObj.ToolsBar_Disabled("toolbar_fenpei");
						ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
					}
					ToolsBarObj.ToolsBar_Enabled("toolbar_edit");
					ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				}else{
					ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
					ToolsBarObj.ToolsBar_Enabled("toolbar_view");
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_copy");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_copy");
				ToolsBarObj.ToolsBar_Disabled("toolbar_fenpei");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
			}
		}
		
		function editDetail(){
			//erwithout_alterInit.action?examRoom.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "questionnaire_alterInit.action?examRoom.id="+value;
			/**
			var value = pp[0].toString();
			width=screen.availWidth * 0.8;
			height=screen.availHeight * 0.8;
			window.open ("course_preview.action?course.id="+value, '课程预览', 'height='+height+', width='+width+', toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no') ;
			*/
		}
		
		function viewDetail(){
			//erwithout_view.action?examRoom.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "questionnaire_view.action?examRoom.id="+value;
		}
		
		function copyDetail(){
			//copy_Eroom.action?examRoom.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "copy_Eroom.action?examRoom.id="+value;
		}
		
		function fenpeiDetail(){
			//examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "questionnaire_assignwcInit.action?examRoom.id="+value+"&course.id=-1";
		}
		
		function commitDetail(){
			//examroom_valid.action?examRoom.id=<s:property value="id"/>&Return=examroom_alllist    window.confirm()
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			if(window.confirm("确定创建完成？"))
				window.location.href = "examroom_valid.action?examRoom.id="+value+"&Return=questionnaireList";
		}
		
		
		//获取选中的checkbox
		function getCheckedCheckboxs(pp,valid,uvalid){
			var checkboxs = document.getElementsByName("examid");
			if(checkboxs.length>0){
				if(pp.length>0)  pp=[];
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked){
						pp.push(checkboxs[i].value);
						valid.push(document.getElementById("valid_"+i).value);
						uvalid.push(document.getElementById("uvalid_"+i).value);
					}
				}
			}
			var obj = new Obj(pp,valid,uvalid);
			return obj;
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
					<wysLib:Navigation ivalue="问卷列表" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<s:form action="pollSh" method="post" name="pollShForm">
			<s:hidden name="poll.id" id="pollId" />
			<s:hidden name="poll.status" id="pollStatus" />
		</s:form>
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;"></label>
			<form action="questionnaireList.action" method="post"
				name="examFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div>
					问卷名称：&nbsp;
					<input type="text" name="poll.title"
						value="<s:property value="poll.title"/>">
					创建时间段范围：&nbsp;从
					<input type="text" onclick=setday(this) name="poll.createtimeStart"
						value="<s:date name="poll.createtimeStart"/>">
					&nbsp;到&nbsp;
					<input type="text" onclick=setday(this) name="poll.createtimeEnd"
						value="<s:date name="poll.createtimeEnd"/>">
					<br />
					结束时间段范围：&nbsp;从
					<input type="text" onclick=setday(this) name="poll.endtimeStart"
						value="<s:date name="poll.endtimeStart" />">
					&nbsp;到&nbsp;
					<input type="text" onclick=setday(this) name="poll.endtimeEnd"
						value="<s:date name="poll.endtimeEnd" />">
					<input onClick="initPN();" type="button" value="搜索" class="textbg4" />
				</div>
			</form>
			<s:if test="polls.size==0">
			</s:if>
			<s:else>
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan=20><div id="Div_ToolsBar"></div></td>
					</tr>
					<tr>	
						<th width="20" align="center">
						</th>
						<th width="180" align="center">
							问卷名称
						</th>
						<th width="80" align="center">
							创建者
						</th>
						<th width="120" align="center">
							创建时间
						</th>
						<th width="160" align="center">
							创建者所属部门
						</th>
						<th width="120" align="center">
							开始时间
						</th>
						<th width="120" align="center">
							结束时间
						</th>
						<th width="70" align="center">
							人数
						</th>
						<th width="200" align="center">
							状态
						</th>
				 	<th width="80" align="center">
							问卷结果
						</th> 
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="examRooms" status="status">
							<tr>
								<td width="20" align="center">
													<input type="checkbox" value="<s:property value="id"/>"
														name="examid" onclick='clickcheckbox();'></td>
								<td align="center">
									<s:property value="title" />
								</td>
								<td align="center">
									<s:property value="creater.realname"/>
								</td>
								<td align="center">
									<s:date name="createtime"/>
								</td>
								<td align="center">
									<s:property value="creater.danwei"/>
								</td>
								<td align="center">
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									参加：<s:property value="usersize" />
								</td>
								<td align="center">
									<s:property value="validName" />
								</td>
							 <td align="center">
										<a href="questionnaireResult.action?examRoom.id=<s:property value="id"/>"
											class="textbg4">查看</a>
								</td>
								<input type="hidden" id="valid_<s:property value='#status.index'/>" value="<s:property value='valid' />"/>
								<input type="hidden" id="uvalid_<s:property value='#status.index'/>" value="<s:property value='uvalid' />"/>
									 		
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</s:else>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
