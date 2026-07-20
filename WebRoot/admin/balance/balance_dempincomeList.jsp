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
		<form action="balance_dempincomeList.action" method="post"
			name="acc_list">

			<s:hidden name="deptid" />
			
		  <table width="100%" border="0" cellpadding="0" cellspacing="1">
		  <tr>
		     <td width="270" bgcolor="#F4F4F4">&nbsp;&nbsp;姓名：
			      <s:textfield name="elUser.realname" theme="simple"/></td>
				     <td width="270" bgcolor="#F4F4F4">&nbsp;&nbsp;账号：
		               <s:textfield name="elUser.username" theme="simple"/></td>
				      <td width="110" align="left" bgcolor="#F4F4F4">&nbsp;&nbsp;包含下属部门<input type="checkbox" name="sub_department"
							
				        
				        
				        
				        <s:if test="sub_department==1">checked="checked"</s:if>
		    id="sub_department" value="1"></td>
				       <td colspan="2" rowspan="2" bgcolor="#F4F4F4">&nbsp;&nbsp;<input id="find" name="find" type="submit" value="搜索" class="textbg6"></td>
	        </tr>
			<tr>
				<td bgcolor="#F4F4F4">&nbsp;&nbsp;年龄段开始时间：<input name='start'
						value="<s:date name="start" format="yyyy-MM-dd" />"
				  onclick='setday(this)' readonly/>				</td>
				<td bgcolor="#F4F4F4">&nbsp;&nbsp;年龄段结束时间：
				    <input name='end'
						value="<s:date name="end" format="yyyy-MM-dd" />"
		    onclick='setday(this)' readonly/>				</td>
				<td bgcolor="#F4F4F4">&nbsp;&nbsp;性别： 
				        <select name="elUser.sex">
					      <option value=""></option>
				       	  <option value="男">男</option>
				          <option value="女">女</option>
		    </select></td>
			</tr>
			<tr>
				   <td colspan="7" align="center" bgcolor="#F4F4F4"></td>
		    </tr>
		  </table>
		<table width="100%">
			<tr>
			<td valign="top" width="120">
				<wysLib:dep_list_aj rootAble="true" href="balance_dempincomeList.action?sub_department=1&deptid="></wysLib:dep_list_aj>
			<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
			</td>
			<td valign="top" align="left">
			<s:if test="shopping.count==0">当前部门没有人员</s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						 消费总况
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left" >
						总人数
						</td>
					   <td height="30" align="center" >
						总金额
					  </td>
					    <td height="30" align="center" >
						已消费金额
						</td>
					</tr>
				<tr>
				
				    <td height="30" style="padding-left:8px;color:blue;" align="left">
				      	<s:property value="shopping.count" />
			        </td>
                        <td height="30" align="center" >
						<s:property value="shopping.allprice" />
					</td>
                    <td height="30" align="center" >
						
						<s:property value="shopping.price" />
					</td>
				</tr>
			</table>
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						 消费明细
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left" onClick="alterFee(this,1)">
						姓名
						</td>
					   <td height="30" align="center" >
						账号
					   </td>
					   <td height="30" align="center" >
						部门
					   </td><td height="30" align="center" >
						总金额
					   </td><td height="30" align="center" >
						已消费
					   </td>
					   <td height="30" align="center" >
						明细
					   </td>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="ls">
							<tr>
							
							    <td height="30" style="padding-left:8px;color:blue;" align="left">
							      <s:property value="user.realname" />
						       </td>
                                <td height="30" align="center" >
									<s:property value="user.username" />
								</td>
                                <td height="30" align="center" >
									<s:property value="user.department.name" />
								</td>
								<td height="30" align="center">
									<s:property value="allprice" />
								</td>
								<td height="30" align="center">
									<s:property value="price" />
								</td>
								<td height="30" align="center" >
								   <a href="order_myorderlistinit.action?userid=<s:property value="user.id" />&orderstatus=1" class=textbg4>查 看</a>
								</td>
							</tr>
						</s:iterator></tbody>
			  </table>
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
				alert("请选择课程");
				return;
				}
				var fee1 = document.getElementById("cfee").value;
					
				if(window.confirm("确定为“"+uname+"”充值“"+fee1+"”元？")){
					document.getElementById("username").value=uid;
					document.getElementById("u_fee").value=fee1;
					acc_list.action="getMyAllBalance.action";
					//alert(acc_list.action);
					acc_list.submit();
					
				}
			
			}
			</script>
			</s:else></td></tr></table><wysLib:page></wysLib:page>
		  </form>
			<s:form action="balance_dempincomeList" method="post" name="assignUser">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="start" />
				<s:hidden name="end" />
			</s:form>
		</div>

		<!-- 内容 -->
	</BODY><script>
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				acc_list.submit();
			}
		
				function page(i) {
					document.getElementById("pageNow").value=i;
					assignUser.submit();
				}
			</script>
		
</HTML>
