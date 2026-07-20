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

		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex %   2 == 0) ?   
		"#ffffff" :    "#f4f4f4" )
}

.textbg4 {
	margin-top: 2px;
}

.textbg6 {
	margin-top: 2px;
}
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
			
			var clid = 0 ; 
			var clname = '' ; 
			var pt="";
			var userid='';
			function alterFee(obj,type,examRoomid){ 
				pt=type;
				clid =  examRoomid ;
				clname =  obj.parentElement.children[0].innerHTML;
				document.getElementById("fee").style.display="block";
				var left = (obj.offsetLeft + obj.clientWidth);
				var top = (obj.offsetTop);
				while (obj = obj.offsetParent) {
					left += obj.offsetLeft;
					top += obj.offsetTop;
				}
				document.getElementById("fee").style.left =left-200;
				document.getElementById("fee").style.top =top;
			}
			function saveFee(){
				var fee1 = document.getElementById("clfee").value;
				if(window.confirm("确定为\""+clname+"\"设定价格为"+fee1+"？")){
					document.getElementById("e_id").value=clid;
					document.getElementById("e_type").value=pt; 
					document.getElementById("e_fee").value=fee1;
					erform.action="piece_applyfor_examroom.action";
					//alert(myclist.action);
					erform.submit();
					
				}
			
			}
				function sh(id,status){
								    document.getElementById("examRoom.id").value=id;
								    document.getElementById("status").value=status; 
									document.forms.erform.submit();
								 	
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
								<wysLib:Navigation ivalue="考场列表" />
							</div>
						</li>
						<!--<li>
				<span style="font-weight: bold;">我创建的考试考场 </span>
			</li>-->
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>

		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="100px" valign="top" id="tree_list_td"
						style="display: ''">
						<wysLib:eroomLibTree
							href="piece_applyfor_examroom.action?sublibs=1&examRoom.eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onClick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td>
						<s:form action="piece_applyfor_examroom" name="erform"
							theme="simple">
							<s:hidden name="biaoshi" id="status"></s:hidden>
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="examRoom.id" id="e_id"></s:hidden>
							<s:hidden name="examRoom.sqlw" id="sqlw" />
							<s:hidden name="wpeice" id="e_fee"></s:hidden>
							<s:hidden name="pt" id="e_type"></s:hidden>
							<input type="hidden" name="examRoom.valid" value="-1"/>
							<input type="hidden" name="examRoom.classid" value="-1"/>
							<s:select theme="simple" headerValue="全部" headerKey="-1"
								list="#{1:'未定价',4:'已定价',2:'审核中',3:'审核通过'}" name="status"
								value="status" />
						考场名称:<s:textfield name="examRoom.title" id="testname"></s:textfield>
							<s:submit value="搜索"></s:submit>

						</s:form>
						<form action="fee_coursecharge_nolist.action" name="myclistdel">
							<table width="100%" height="100%" align="center" cellpadding="1"
								cellspacing="1">
								<tr>
									<th align="center">
										考场名称
									</th>
									<th align="center">
										类别
									</th>
									<th align="center">
										创建者
									</th>
									<th align="center">
										创建时间
									</th>
									<th align="center">
										开始时间
									</th>
									<th align="center">
										结束时间
									</th>
									<th align="center" bgcolor="#FFFFFF">
										会员价格
									</th>
									<th align="center" bgcolor="#FFFFFF">
										市场价格
									</th>
									<th align="center" bgcolor="#FFFFFF">
										状态
									</th>
									<th align="center" bgcolor="#FFFFFF">
										操作
									</th>
								</tr>
								<s:iterator value="examRooms">
									<tr>
										<td align="center" bgcolor="#FFFFFF">
											<s:property value="examRoom.title" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:property value="examRoom.eroomLib.name" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:property value="examRoom.creater.realname" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="examRoom.pwdtime" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:date format="yyyy-MM-dd hh:mm:ss"
												name="examRoom.begintime" />
										</td>

										<td align="center" bgcolor="#FFFFFF">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="examRoom.endtime" />
										</td>
										<s:if test="status==0||status==4">
											<td align="center" bgcolor="#FFFFFF"
												onClick="alterFee(this,1,<s:property value="examRoom.id"/>)">
												<s:property value="examroomnowPrice" />
											</td>
										</s:if>
										<s:else>
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="examroomnowPrice" />
											</td>
										</s:else>
										<s:if test="status==0||status==4">
											<td align="center" bgcolor="#FFFFFF"
												onClick="alterFee(this,2,<s:property value="examRoom.id"/>)">
												<s:property value="examroomoldPrice" />
											</td>
										</s:if>
										<s:else>
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="examroomoldPrice" />
											</td>
										</s:else>

										<td align="center" bgcolor="#FFFFFF">
											<s:if test="status==0">
												未定价
												</s:if>
											<s:if test="status==1">
												审核通过
												</s:if>
											<s:if test="status==3">
												审核中
												</s:if>
											<s:if test="status==4">
												已定价
												</s:if>

										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:if test="status==0||status==4">

												<a style="cursor: pointer;"
													onClick="sh(<s:property value="examRoom.id"/>, 1);"
													class="textbg6">提交审核</a>
											</s:if>
										</td>
									</tr>
								</s:iterator>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div id="fee" style="background: #ddfdff;display:none; border: 1 solid buttonface;width: 160px;position: absolute;" >
		<input type="text" id="clfee" size="5"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onClick="saveFee()" value="设定" />
		<input type="button" onClick=" document.getElementById('fee').style.display='none'" value="关闭"/>
		</div>
		<!-- 内容 -->
		<wysLib:page></wysLib:page>
	</BODY>
</HTML>
