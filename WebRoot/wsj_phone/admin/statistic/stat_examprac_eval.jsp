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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/tree/dtreequizdep.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function openPrac(id){
			 if(confirm('确定开始练习？'))
			 	window.open("exampracinto.action?examprac.id="+id,"exampracpaper","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			}
			function setabled(idstr,id){
					document.getElementById(idstr+id).checked=true;
			}
			function toexcel(){     
				statEval.action = "stat_examprac_eval.action?exprot=true";
				statEval.submit();
			}
			function view(){     
				statEval.action = "stat_examprac_eval.action";
				statEval.submit();
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="部门比较" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">部门评比</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_examprac_gk.action?examprac.id=<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">练习概况</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_examprac_detail.action?examprac.id=<s:property value="examprac.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">练习详情</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top:0px;">
			<div>
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top">
							<form action="stat_examprac_eval.action" method="post" name="statEval">
								<input type="button" class="textbg4" value="查看" onClick="view()">	<input class="textbg4" type="button" value="导出" onClick="toexcel()">  
								<s:hidden name="examprac.id"></s:hidden>
								<wysLib:dep_list_aj itype="cb" rootAble="true" 
									iname="departments.id" ></wysLib:dep_list_aj>
								<script type="text/javascript">
									w0.setValues([<s:iterator value="departments" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments.size-1)!=#depst.index">,</s:if></s:iterator>]);
								</script>
							</form>
						</td>
						<td valign="top">
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" >
								<tr>
									<th align="center" >
										排行
									</th>
									<th align="center" >
										部门
									</th>
									<th align="center" >
										练习人数
									</th>
									<th align="center" >
										及格人数
									</th>
									<th align="center" >
										及格率
									</th>
									<th align="center" >
										平均分
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="departments" status="st">
									<tr>
										<td height="30" align="center" >
											<s:property value="#st.index+1" />									  </td>
										<td align="center" style="color: #CC0099;">
											<s:property value="name" />									  </td>
										<td align="center" >
											<s:property value="userCount" />
										</td>
										<td align="center" >
											<s:property value="userCredit" />
										</td>
										<td align="center" >
											<s:if test="userCount==0||userCredit==0">0%</s:if> 
											<s:else>
												<s:property value="ratiof_*100" />%
											</s:else>
										</td>
										<td align="center" >
											<s:property value="avg" />
										</td>
									</tr>
								</s:iterator> </tbody>
						  </table>
						</td>
					</tr>
			  </table>
			  <div style="text-align: center;">
				<a href="stat_examprac_gk.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >查看概况</a>
				<a href="stat_examprac_detail.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >查看详情</a>
				<a href="stat_examprac_eval.action?examprac.id=<s:property value="examprac.id"/>" class=textbg >部门比较</a>
				<a href="stat_examprac_list.action" class=textbg >返回练习列表</a>
			</div>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
