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
		<TITLE>学籍查询管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
						<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
			
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<form action="getMyAllBalance.action" method="post"
			name="acc_list">
			<s:hidden name="pN" id="pageNow"/>
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
				<s:hidden name="username" id="username"/>
				<s:hidden name="balanceValue" id="u_fee"></s:hidden>
				<s:hidden name="userids" id="userids"/>
				
			<table align="center" cellpadding="1" cellspacing="1" width="100%"
				>
				<tr align="right"><td width="120" height="30" align="left" bgcolor="#F4F4F4"><a  href="geteluserzengzi_recharge_info1.action?type=3" class="textbg5">手工增资记录</a></td>
				<td width="120" align="left" bgcolor="#F4F4F4">包含下属部门					
					
						<input type="checkbox" name="sub_department"
							
						
						
						
						<s:if test="sub_department==1">checked="checked"</s:if>
				  id="sub_department" value="1"></td>
				<td width="160" align="left" bgcolor="#F4F4F4">角色：					
					
					<select name="elUser.role.id" style="margin-left:5px;">
									<option value="0">
										请选择
									</option>
									<s:iterator value="roles">
										<option <s:if test="role.id==id">selected='selected'</s:if>
											value="<s:property value="id"/>">
											<s:property value="name" />
										</option>
									</s:iterator>
				  </select></td>
				<td width="220" align="left" bgcolor="#F4F4F4">姓名：					
					
						&nbsp;
						<input name="elUser.realname"
							value="<s:property value="elUser.realname"/>"
				  id="elUser.realname"></td>
				<td width="220" align="left" bgcolor="#F4F4F4">
						账号：					
					
						&nbsp;&nbsp;
						<input name="elUser.username"
							value="<s:property value="elUser.username"/>"
				  id="elUser.username"></td>
							<td align="left" bgcolor="#F4F4F4">
						      <input id="find" name="find" type="button" onClick="newsSubmit()" value="搜索" style="5px;" class="textbg6">
				  </td>
			  </tr>
				<tr>
					<%-- 
					<td>
						部门:
					</td>
					<td>
						<select name="elUser.department.id" id="deptid">
							<wysLib:dep_select selectid="<%=1%>" />
						</select>
					</td>
					 --%>

				</tr>			
				</tr>
		  </table>
		<table width="100%">
			<tr>
			<td valign="top" width="120">
				<wysLib:dep_list_aj rootAble="true" href="getMyAllBalance.action?deptid="></wysLib:dep_list_aj>
			
				<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
			</td>
			<td valign="top" align="left">
			<s:if test="listSchoolrolls.size==0">当前还没有记录</s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						 手动增资
					</caption>
					  
					<tr>
					<td height="30" align="center" >
							选择
					  </td>
						<td height="30" style="padding-left:8px;color:blue;" align="left" onClick="alterFee(this,1)">
						姓名
						</td>
					   <td height="30" align="center" >
							账号
					  </td>
					    <td height="30" align="center" >
							部门
						</td>
						<td height="30" align="center" >
							角色
						</td>
						<td height="30" align="center" >
							状态
						</td>
						<td height="30" align="center" >
							余额
						</td>
						<td height="30" align="center" >
							增值记录
						</td>
						
					</tr>
					<s:if test="elUsers.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前没有记录
							</TD>
						</TR>
					</s:if>
					<s:else><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="elUsers">
							<tr>
								<td height="30" style="padding-left:8px;color:blue;" align="left">
							     <input type="checkbox" value="<s:property value="user.id"/>" name="id"> 
						       </td>
							    <td height="30" style="padding-left:8px;color:blue;" align="left">
							      <s:property value="user.realname" />
						       </td>
                                <td height="30" align="center" >
									<s:property value="user.username" />
								</td>
                                <td height="30" align="center" >
									<s:property value="user.department.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="user.role.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="user.validName" />
								</td>
								<td height="30" align="center" onClick="alterFee(this,<s:property value="user.id" />)">
									<s:property value="balance" />
					  </td>
								
								<td height="30" align="center" >
								   <a href="geteluser_recharge_info.action?username= <s:property value="user.id" />" class=textbg4>查 看</a>
								</td>
							</tr>
						</s:iterator></tbody>
					</s:else>
			  </table>
			  <div id="fee" style="background: #ddfdff;display:none; border: 1 solid buttonface;width: 160px;position: absolute;" >
			  
		<input type="text" id="cfee" size="5"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onClick="saveFee()" value="充值" />
		<input type="button" onClick=" document.getElementById('fee').style.display='none'" value="关闭"/>
		</div>
		<div id="fee2" style="background: #ddfdff;display:none; border: 1 solid buttonface;width: 160px;position: absolute;" >
		<input type="text" id="cfee2" size="5"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onClick="allsaveFee()" value="充值" />
		<input type="button" onClick=" document.getElementById('fee2').style.display='none'" value="关闭"/>
		</div>
		
		<script>
			var uid = 0 ; 
			var uname = '' ; 
			function alterFee(obj,userid){
				uid =   userid;
				uname =  obj.parentElement.children[0].innerHTML;
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
				if(uid==0){
				alert("请选择用户");
				return;
				}
				var fee1 = document.getElementById("cfee").value;
				if(isNaN(fee1)){
					   alert("请输入正确的金额！");
					   return;
					}	
				if(window.confirm("确定为“"+uname+"”充值“"+fee1+"”元？")){
					document.getElementById("username").value=uid;
					document.getElementById("u_fee").value=fee1;
					acc_list.action="getMyAllBalance.action";
					//alert(acc_list.action);
					acc_list.submit();
					
				}
			
			}
			function  allalter(obj){
			document.getElementById("fee2").style.display="block";
				var left = (obj.offsetLeft + obj.clientWidth);
				var top = (obj.offsetTop);
				while (obj = obj.offsetParent) {
					left += obj.offsetLeft;
					top += obj.offsetTop;
				}
				document.getElementById("fee2").style.left =left;
				document.getElementById("fee2").style.top =top-50;
			uname='所选用户';
			
			}
			function allsaveFee(){
			if(uid==0){
				alert("请选择用户");
				return;
				}
				var fee1 = document.getElementById("cfee2").value;
				if(isNaN(fee1)){
					   alert("请输入正确的金额！");
					   return;
					}	
				if(window.confirm("确定为所选用户每人充值“"+fee1+"”元？")){
					document.getElementById("userids").value=uid;
					document.getElementById("u_fee").value=fee1;
					acc_list.action="allgetMyAllBalance.action";
					//alert(acc_list.action);
					acc_list.submit();
					
				}
			}
			function unassign(obj){
						  if(window.confirm("确定给所选用户增资？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要增资的用户！");
								  return ;
							    }
							    uid=billIDs;
								 allalter(obj);
						      
							}
						}	
			</script>
			</s:else></td></tr></table><wysLib:page></wysLib:page>
			<a onClick="unassign(this)" class="textbg6" >批量增资</a>
		  </form>
		</div>

		<!-- 内容 -->
	</BODY><script>
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				acc_list.submit();
			}
		
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<!--<form action="schoolrolls.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
			</form>
-->
</HTML>
