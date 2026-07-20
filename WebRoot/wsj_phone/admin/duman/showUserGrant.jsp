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
		<TITLE>用户授权管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		
		</script>
		<base href="<%=basePath%>">
		<style type="text/css">
#dv span {
	margin-left: 30px;
}
</style>
		<script type="text/javascript">
			function searchUserInit(_id,input_name,treeType){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userGrantManageInit.action?elUser.id="+input_name+"&treeType="+treeType+"&x="+Math.random(),null,sFeature);
				 if(rv=="nihao"){
				 	alert("授权成功！！！");
				 	location.href="showUserGrant.action?elUser.id="+<s:property value='elUser.id'/>+"&date="+new Date();
				 }
			}
			
			
			function userRightInit(userid){
			     width=1000;
				 height=800;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRole.action?elUser.id="+userid+"&date="+new Date(),null,sFeature);
				 if(rv=="nihao"){
				 	alert("授权成功！！！");
				 	location.href="showUserGrant.action?elUser.id="+<s:property value='elUser.id'/>+"&date="+new Date();
				 }
			}
			
			
			
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ? "#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<form action="userGrant.action" method="post" name="myForm">
			<s:hidden name="elUser.id" />
			<div id="dv">

				<table width="100%" border="0" align="center"
					style="margin-top: 40px;">
					<tr>
						<td width="25%" style="padding-left: 20px; color: red;">
							当前是给这个用户进行授权
						</td>
						<td align="left">
							用户名：
							<s:property value="elUser.username" />
							姓名：
							<s:property value="elUser.realname" />
							角色：
							<s:property value="elUser.role.name" />
							所属部门：
							<s:property value="elUser.department.name" />
						</td>
					</tr>
				</table>
			</div>

			<table width="100%" border="0" align="center">
				<tr>
					<td colspan="3" align="left" valign="top" style="padding: 0px;">
						<table width="100%" border="0"
							cellpadding="1" cellspacing="1" style="margin: 0px;">
							<tr>
								<td width="25%" align="left" valign="top" style="padding: 8px;">
									<div style="float: left; margin-left: 0px; width: 150px;">
										<wysLib:qlibtree did="1" itype="OP" rootAble="true" />
										<!-- 题库树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','qlib');return false;" />
									</div>
									&nbsp;
								</td>
								<td width="25%" align="left" valign="top" style="padding: 8px;">
									<div style="float: left; margin-left: 0px; width: 150px;">
										<wysLib:ctypeTree did="2" itype="OP" rootAble="true" />
										<!-- 课程树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','ctyp');return false;" />
									</div>
									&nbsp;
								</td>
								<td width="25%" align="left" valign="top" style="padding: 8px;">
									<div style="float: left; margin-left: 0px; width: 150px;">
										<wysLib:elibtree did="3" itype="OP" rootAble="true" />
										<!-- 试卷树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','elib');return false;" />
									</div>
									&nbsp;
								</td>
							</tr>
							<tr align="left" valign="top" style="padding: 8px;">
								<td style="padding: 8px;">
									<div style="float: left; margin-left: 0px;">
										<wysLib:clTypeTree did="4" itype="OP" rootAble="true" />
										<!-- 培训班树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','clty');return false;" />
									</div>
									&nbsp;
								</td>
								<td style="padding: 8px;">
									<div style="float: left; margin-left: 0px;">
										<wysLib:eroomLibTree did="5" itype="OP" rootAble="true" />
										<!-- 考场树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','eroo');return false;" />
									</div>
									&nbsp;
								</td>
								<td style="padding: 8px;">
									<div style="float: left; margin-left: 0px;">
										<wysLib:stuffTree did="8" rootAble="false" />
										<!-- 素材树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','stuf');return false;" />
									</div>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td style="padding: 8px;">
									<div style="float: left; margin-left: 0px;">
										<wysLib:newsTypeTree did="9" itype="001" rootAble="true" />
										<!-- 新闻树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','news');return false;" />
									</div>
									&nbsp;
								</td>
								<td style="padding: 8px;">
									<div style="float: left; margin-left: 0px;">
										<wysLib:kltype_list did="10" itype="001" rootAble="true" />
										<!-- 知识树 -->
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','klty');return false;" />
									</div>
									&nbsp;
								</td>
								<td style="padding: 8px;">
									<div style="float: left; margin-left: 0px;">
										<s:if test="fbtypes.size==0">
									    	无版面权限
									    </s:if>
										<s:else>
											<font color="#ccc">该用户的版面权限</font>
											<s:iterator value="fbtypes">
												<!-- <DIV><s:property value="name"/></DIV> -->
												<s:if test="fblocks.size>0">
													<div style="margin-left: 20px; line-height: 16px;">
														<s:property value="name" />
														<s:iterator value="fblocks">
															<div style="margin-left: 20px; line-height: 16px;">
																<s:property value="title" />
															</div>
														</s:iterator>
													</div>
												</s:if>
											</s:iterator>
										</s:else>
										<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
											onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','bmsq');return false;" />
								</td>
							</tr>
							
							
							<tr>
								<td colspan="3" style="white-space:nowrap;overflow:hidden">
								 
							 
											<div style="margin-left: 0px; width: 150px;">
												<wysLib:funcTree2 did="7"></wysLib:funcTree2>
											
											<div style="margin-left: 0px; width: 150px;">
												<wysLib:funcTreeUserLimit did="12"></wysLib:funcTreeUserLimit>
												
													
								 			 <input type="button" value="授&nbsp;&nbsp;&nbsp;权"
									 			onClick="userRightInit('<s:property value="elUser.id" />');return false;" />	
								 						
  
								
								
								</td>
							</tr>
						</table>
					</td>
					<td width="25%"   style="height:100%;vertical-align:top">
					
					
					
						<table cellspacing="0" style="width:100%; height:174px;"   >					
						<tr>
						<td style="vertical-align:top">
							<div style="margin-left: 0px; width: 150px;">
							<wysLib:dep_list_aj did="0" rootAble="true" />
							<!-- 部门树 -->
							<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
								onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','depl');return false;" />
						    </div>
						</td>
						</tr>
						<tr>
						<td style="vertical-align:top">
							<div style="margin-left: 0px; width: 150px;">
							<wysLib:st_list_aj did="20" rootAble="true" />
							<!-- 岗位树 -->
							<input type="button" value="授&nbsp;&nbsp;&nbsp;权"
								onClick="searchUserInit('can_op','<s:property value="elUser.id"/>','st');return false;" />
						    </div>
						</td>
						</tr>
						</table>
						 
					</td>
			
			</table>






		</form>
		<s:property value="strValue1.replace('s', 'A')" />
		
		
	
	</body>
</HTML>
