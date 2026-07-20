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
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏考场树</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
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
			ToolsBarObj.ToolsBar_Add("toolbar_copy","复制","images/newversion/un_view.gif","copyDetail()");
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
			window.location.href = "erwithout_alterInit.action?examRoom.id="+value;
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
			window.location.href = "erwithout_view.action?examRoom.id="+value;
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
			window.location.href = "examroom_assignwcInit.action?examRoom.id="+value+"&course.id=-1";
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
				window.location.href = "examroom_valid.action?examRoom.id="+value+"&Return=examroom_alllist";
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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
				  "#ffffff" :     "#f4f4f4" )
}
.textbg4{margin-top:2px;}
.textbg6{margin-top:2px;}
</style>
		<script> 
			function page(i){
				document.getElementById("pageNow").value=i;
				erform.submit();
			}
			function initPN(){
				document.getElementById("pageNow").value=0;
				document.getElementById("sqlw").value=0;
				erform.submit();
			}
			function seachEroomInDel(){
				document.getElementById("pageNow").value=0;
				document.getElementById("sqlw").value=9;
				erform.submit();
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的考试考场 </span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px" valign="top" id="tree_list_td" style="display:none">
						<wysLib:eroomLibTree
							href="examroom_alllist.action?sublibs=1&examRoom.eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onClick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td>
						<form
							action="examroom_alllist.action?examRoom.eroomLib.id=<s:property value="examRoom.eroomLib.id"/>"
							method="post" name="erform">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="examRoom.sqlw" id="sqlw" />
                            <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
  <tr>
    <td width="100" rowspan="2" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
								<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>
							</div></td>
<td width="170" bgcolor="#F8FCFE"><div>
								包含下级节点:<input type="checkbox" name="sublibs" value="1"
									<s:if test="sublibs==1">checked="checked"</s:if> />
                                    </div></td>
    <td width="380" bgcolor="#F8FCFE">考场标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input size="16" type="text" name="examRoom.title"
									value="<s:property value="examroom.title"/>
      ">&nbsp; 状态：&nbsp;<s:select theme="simple" headerKey="-1" headerValue="全部"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="examRoom.valid" value="examRoom.valid" />
								</td>
    <td rowspan="2" bgcolor="#F8FCFE">&nbsp;<input onClick="initPN();" type="submit" class="textbg4"
									value="搜索" /></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">类型：<s:select theme="simple"
									list="#{-2:'全部',-1:'考核考场',0:'章节考场',1:'单纯课程考场',2:'培训班考场'}"
									name="examRoom.classid" value="examRoom.classid" />
								</td>
    <td bgcolor="#F8FCFE">开考时间：&nbsp;从
			  <input size="16" type="text" onclick=setday(this)
									name="examRoom.begintime"
									value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">
								&nbsp;到&nbsp;
								<input size="16" type="text" onclick=setday(this)
									name="examRoom.endtime"
									value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">
								</td>
  </tr>
                      </table>						
						</form>
<table width="100%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<td colspan=20><div id="Div_ToolsBar"></div></td>
							</tr>
							<tr>
								<th width="20" align="center">
							  </th>
								<!-- <th width="20"  align="center" >&nbsp;</th> -->
								<th width="300" align="center">
									考场信息
								</th>
								<th width="60" align="center">
									类型
								</th>
								<th width="90" align="center">
									开考时间
								</th>
								<th width="90" align="center">
									结束时间
								</th>
								<th width="70" align="center">
									状态
								</th>
								<th width="80" align="center">
									人数
								</th>
								<!-- 
								<th width="90" align="center">
									&nbsp;
								</th>
								 -->
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms" status="status">
									<tr>
										<td width="20" align="center">
													<input type="checkbox" value="<s:property value="id"/>"
														name="examid" onclick='clickcheckbox();'>
										<!-- 	<td width="20"  align="center" >
									<input type="checkbox" name="delete_item[]" value="5">
							  </td>
							   -->
										<td style="padding: 3px 0px 3px 2px;" valign="top"
											align="left">
											<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size:15px;color: blue;"><s:property
														value="title" /> </strong>
												<br />
												<strong>类别:</strong>
												<s:property value="eroomLib.name" />
												<br />
												<strong>组织单位:</strong>
												<s:property value="depName" />
												<br />
												<strong>组织工钟:</strong>
												<s:property value="jingzhong" />
												<br />
												<strong>创建者:</strong>
												<s:property value="creater.realname" /><br/>
												<s:if test="examRoom.classid!=-1">
													<strong> 所属课程: </strong><s:property value="course.name" />
												</s:if>
											</div>
										</td>
										<td align="center">
											<s:if test="isApplication == 1">
												<SPAN style="color: red">【申请】</SPAN>
											</s:if>
											<s:elseif test="isApplication == 2">
												<SPAN style="color: blue;">【全工】</SPAN>
											</s:elseif>
											<s:else>
												<SPAN style="color: gray">【分配】</SPAN>
											</s:else>
										</td>

										<td align="center">
											<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center">
											<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center">
											<s:property value="validName" />
										</td>
										<td align="center">
											参加：<s:property value="usersize" />
											<s:if test="isApplication == 1">
												<br />
												<span style="color: red">计划：<s:property
														value="planNumber" /> </span>
											</s:if>
										</td>
										<input type="hidden" id="valid_<s:property value='#status.index'/>" value="<s:property value='valid' />"/>
										<input type="hidden" id="uvalid_<s:property value='#status.index'/>" value="<s:property value='uvalid' />"/>
										
										<!-- 
										<td>
											<s:if test="valid == 0 || valid ==2">
												<s:if test="course.name!=null">
													<a
														href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>"
														class="textbg4">编 辑</a>
												</s:if>
												<s:else>
													<a
														href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>"
														class="textbg4">编 辑</a>
												</s:else>
											</s:if>
											<s:else>
												<a
													href="erwithout_view.action?examRoom.id=<s:property value="id"/>"
													class="textbg4">查 看</a>
											</s:else>
											<a
												href="copy_Eroom.action?examRoom.id=<s:property value="id"/>"
												class="textbg4">复制</a>
											<br />
											<s:if test="valid == 0 || valid == 2">
												<s:if test="uvalid != 1">
													<a
														href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1"
														class="textbg6">分配学员</a>
													<a
														href="examroom_valid.action?examRoom.id=<s:property value="id"/>&Return=examroom_alllist"
														onclick="return confirm('确定创建完成？')" class="textbg6">创建完成</a>
												</s:if>
											</s:if>
										</td>
										 -->
									</tr>
								</s:iterator>
							<tbody>
						</table>
						<wysLib:page></wysLib:page>
						<input class=textbg6 style="height: 35px;" type="button"
							value="创建考场"
							onClick="javascript:document.location.href='erwithout_addInit.action?course.id=-1';">
						<s:if test="examRoom.sqlw==9">
							<input class="textbg7" style="height: 35px;" type="button"
								value="返回考场列表" onClick="initPN();">
						</s:if><s:else>
						<input class=textbg7 style="height: 35px;" type="button"
							value="已删考场列表" onClick="seachEroomInDel();">
						</s:else>
						<!-- 加上考场类别 2012年12月21日 -->
							<input class=textbg7 style="height: 35px;" type="button"
							value="考场类别" onClick="document.location='eroomlib_list.action'">
							
							
					</td>
				</tr>
		  </table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
