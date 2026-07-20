<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base href="<%=basePath%>" target="_self">
		<TITLE>培训管理信息系统--管理端-学员管理</TITLE>
		<META http-equiv=Pragma content=no-cache>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" /> 
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script> 
		<script type="text/javascript">
			var arr = new Array();      
			function doSubmit(){    
				if(document.myForm.classids.value != '' && !document.myForm.classids.length){
				//在elclasses只有一个值的时候。document.myForm.classids是没有长度的， 只有value
					if(document.myForm.classids.checked){ 
						arr.push(document.myForm.classids.value);    
					} 
				}else{
					for(var i = 0 ; i < document.myForm.classids.length ; i++) 
					{ 
						if(document.myForm.classids[i].checked){ 
							arr.push(document.myForm.classids[i].value);    
						}
					}            
				}  
				window.returnValue = arr; 
				window.close(); 
				//setTimeout(window.close(),2000);
			}
		</script>
	</HEAD>
	<BODY style="height: 100%; width: 100%; margin: 0px">
		<table width="100%">
			<tr>
				<td width="200px;" valign="top" id="tree_list_td">
					<wysLib:clTypeTree
						href="mess_sendElclassUserList.action?cltype.id=" rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">
					<s:form action="mess_sendElclassUserList" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<div>
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1"> 
								<tr>
									<td>
										培训班名称:
										<input type="text" name="elClass.name"
											value="<s:property value="elClass.name"/>">
									</td>
									<td>
										<s:submit value="搜索"></s:submit>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										时间段范围&nbsp;
										<input type="text" onclick=setday(this)
											name="elClass.begintime"
											value="<s:date name="elClass.begintime" format="yyyy-MM-dd HH:mm"/>">
										&nbsp;到&nbsp;
										<input type="text" onclick=setday(this) name="elClass.endtime"
											value="<s:date name="elClass.endtime" format="yyyy-MM-dd HH:mm"/>">
									</td>
								</tr> 
							</table>
					</s:form>
				<s:form action="mess_sendElclassUserList.action" method="post" name="myForm" theme="simple" onsubmit="doSubmit();"> 
					<s:if test="elclasses.size==0">没有需要审核的培训班</s:if>
					<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1" bgcolor="#ECEDEB">
							<tr>
								<th align="center" bgcolor="#FFFFFF">
								</th>
								<th width="600" align="center" bgcolor="#FFFFFF">
									培训班名称
								</th>
								<th width="150" align="center" bgcolor="#FFFFFF">
									学员人数
								</th>
								<th width="200" align="center" bgcolor="#FFFFFF">
									&nbsp;
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="elclasses">
									<tr>
									 
										<td align="center" bgcolor="#FFFFFF" style="color: #CC0099;">
												<input type="checkbox" name="classids" value="<s:property value="id" />"">
										</td>
										<td width="600" align="center" bgcolor="#FFFFFF"
											style="color: #CC0099;">
											<s:property value="name" />
										</td>
										<td width="150" align="center" bgcolor="#FFFFFF">
											<s:property value="classSize" />
										</td>
										<td width="200" align="center" bgcolor="#FFFFFF">
											<a
												href="elclass_check_students.action?elclass.id=<s:property value="id"/>"
												target="_blank" class="textbg2">查看学员</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</s:else><input type="submit" style="margin-left:260px" value="确&nbsp;&nbsp;认"  />
					</s:form>
					<wysLib:page></wysLib:page> 
				</td>
			</tr>
		</table> 
		
		<script>    
			function page(i) {
				document.getElementById("pageNow").value=i;
				myclist.submit();
			}  
		</script> 
	
	</body>
</HTML>
