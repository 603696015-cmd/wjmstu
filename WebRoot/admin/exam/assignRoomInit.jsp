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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script>
			function sh(id,valid,huanyuanVlaid){
			    document.getElementById("examRoom.id").value=id;
			    document.getElementById("examRoom.valid").value=valid; 
			    document.getElementById("huanyuanVlaid").value=huanyuanVlaid; 
			 	if(window.confirm("确定该操作？")){
			 		document.forms.examroom_sh_p.submit();
			 	} 
			}  
			function page(i){
				document.getElementById("pageNow").value=i;
				erform.submit();
			}
			function showCre(roomid){
			  	width=750;
				height=500;  
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog("CRE_notelistInit.action?examRoom.id="+roomid+"&course.id=0&elclass.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="搜索结果页" />
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
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200" valign="top" id="tree_list_td">
						<wysLib:eroomLibTree
							href="combinationSearchExamroom.action?str=&examRoom.eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onClick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table width="100%" align="center" cellspacing="1">
							<tr>
								<th width="20" height="30" align="center" >&nbsp;</th>
								<th width="300" height="30" align="center">
									考场信息
								</th>
								<th width="50" height="30" align="center">
									类型
								</th>
								 
								<th width="120" height="30" align="center">
									开始时间
								</th>
								<th width="120" height="30" align="center">
									结束时间
								</th>
								<th width="80" height="30" align="center">
									状 态
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms">
									<tr>
										<td>
											<input type="radio" name="id" value="<s:property value='id' />"/>
										</td>
										<td style="padding: 3px 0px 3px 2px;" valign="top" align="left">
											<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size: 15px; color: blue;"><s:property
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
												<s:property value="creater.realname" />
												<br />
												<s:if test="examRoom.classid!=-1">
													<strong> 所属课程: </strong>
													<s:property value="course.name" />
												</s:if>
											</div>
										</td>
										<td align="left">
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
										<td align="left">
										</td>
									</tr>
								</s:iterator>
							<tbody>
						</table>
						<table width="100%" align="left" cellspacing="1">
							<tr>
								<td align=center>先学后考：</td>
								<td align=center>
									是：<input type="radio" name="ra" value=1 checked />
									否：<input type="radio" name="ra" value=0 />
								</td>
								<td align=center>智能辅导分达标线：</td>
								<td align=center><input type="text" name="sc" id="sc" /></td>
							</tr>
						</table >
						<wysLib:page></wysLib:page>
						<a href="javascript:assginRoom();" class="textbg4" style="width:80px">分配</a>
						<a href="elclass_assignRoom.action" class="textbg4" style="width:80px">返回</a>
					</td>
				</tr>
			</table>
		</div>
		<form action="assignRoom.action" name="assignRoom" method="post">
			<input type="hidden" name="roomid" id="roomid" />
			<s:hidden name="standardLine" id="standardLine"></s:hidden>
			<s:hidden name="firstLearnLaterExam" id="firstLearnLaterExam"></s:hidden>
			<s:hidden name="resultPage"></s:hidden>
			<s:hidden name="elClass.id" id="elClass.id"></s:hidden>
		</form>
		<!-- 内容 -->
		<script type="text/javascript">
		//培训班分配考场
			function assginRoom(){
				var radios = document.getElementsByName("id");
				var radio ;
				var roomid = "";
				if(radios!=undefined && radios.length>0){
					for(var i=0;i<radios.length;i++){
						radio = radios[i];
						if(radio.checked){
							roomid = radio.value ;
						}
					}
					/**
					if(roomids!=""&&roomids.charAt(roomids.length-1)==","){
						roomids = roomids.substring(0,roomids.length-1);
					}
					*/
				}
				if(roomid == ""){alert("请选择一个单选框");return;}
				var score = document.getElementById("sc");
				if(sc!=undefined){
					if(sc.value==""){
						alert("智能辅导分达标线不能为空");
						return ;
					}else{
						if(isNaN(sc.value)){
							alert("智能辅导分达标线必须为数字");
							return ;
						}else{
							if(parseInt(sc.value)<0){
								alert("智能辅导分达标线必须为大于0的数字");
								return ;
							}
						}
					}
				}
				document.getElementById("standardLine").value = sc.value;//智能辅导分达标线
				var ras = document.getElementsByName("ra");
				var ra_value = 0;
				if(ras!=undefined){
					for(var i=0;i<ras.length;i++){
						if(ras[i].checked){
							ra_value = ras[i].value;
							break;
						}
					}
				}
				document.getElementById("firstLearnLaterExam").value = ra_value;//先学后考设置
				if(window.confirm("确认分配?")){
					document.getElementById("roomid").value = roomid;
					assignRoom.submit();
				}
			}
		</script>
	</BODY>
</HTML>
