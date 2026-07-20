<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function columnsearch(orderBy){
				/**
				document.getElementById("orderBy").value = orderBy;
				var sc=document.getElementById("ordersc").value;
				if(sc == ""){
					document.getElementById("ordersc").value = "asc";
				}else if(sc == "asc"){
					document.getElementById("ordersc").value = "desc";
				}
				log.submit();
				*/
			}
			
			function page(i){
				document.getElementById("pageNow").value=i;
				ana.submit();
			}
			
			function do_search(){
				document.getElementById("pageNow").value=0;
				ana.submit();
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
					<wysLib:Navigation ivalue="客户分析一览" />
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
		<div style="margin-top: 0px; text-align: center;">
			<form action="kehu_analysis.action" name="ana" method="post">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" /> 
			<s:hidden name="tablename" /> 
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<%
							Department dep = (Department) request
										.getAttribute("department");
								String depid = dep.getId() + "";
						%>
						<wysLib:dep_list_aj rootAble="true"
							href="kehu_analysis.action?tablename=KHDA&sub_department=1&department.id="
							iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table align="center" cellpadding="1" cellspacing="1"
							width="100%" height="100%">
							<tr>
								<td align="center" >
									客户名称
								</td>
								<td >
									<label>
										<input type="text" name="kehuname" 
											value="">
									</label>
								</td>
								<td align="center" >
									阶段
								</td>
								<td >
									<label>
										<s:select  name="jieduan" onchange="this.value=this.options[this.selectedIndex].value;"  id="jieduan"  
								list="{'新建客户','初步联系','联系中客户','意向客户','正式客户','老客户','VIP客户'}" 
								theme="simple" headerKey="请选择客户阶段" headerValue="请选择客户阶段"  />
									</label>
								</td>
								<td align="center" >
									地区
								</td>
								<td >
									<label>
										<input type="text" name="diqu" 
											value="">
									</label>
								</td>
								<td align="center" >
									性质
								</td>
								<td >
									<label>
										<s:select  name="xingzhi" onchange="this.value=this.options[this.selectedIndex].value;"  id="xingzhi"  
								list="{'民营企业','国有企业','政府机构','事业单位','外商独资','合资合作','私营企业','其他'}" 
								theme="simple" headerKey="请选择客户性质" headerValue="请选择客户性质"  />
									</label>
								</td>
							</tr>
							<tr>
								<td align="center" >
									利润
								</td>
								<td >
									<label>
										从<input type="text" name="lirun_begin" >
									</label>
								</td>
								<td >
									<label>
										到<input type="text" name="lirun_end" >
									</label>
								</td>
								<td align="center" >
									收款
								</td>
								<td >
									<label>
										从<input type="text" name="shoukuan_begin" >
									</label>
								</td>
								<td >
									<label>
										到<input type="text" name="shoukuan_end" >
									</label>
								</td>
								<td></td>
								<td align="center" ><input type="button" value="搜索" onclick="do_search();" class="textbg4"/></td>
							</tr>
						</table>
						<table width="98%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">客户名称</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">地区</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">阶段</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">性质</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">相关行为</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">相关日志</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">相关项目</a>
								</th>
								<th height="30" align="center" colspan="6">
									<a href="javascript:columnsearch('');">相关单据</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">分摊成本</a>
								</th>
								<th height="30" align="center" rowspan="2">
									<a href="javascript:columnsearch('');">利润</a>
								</th>
							</tr>
							<tr>
								<th align="center">应收</th>
								<th align="center">应付</th>
								<th align="center">收款</th>
								<th align="center">付款</th>
								<th align="center">其他应收</th>
								<th align="center">其他应付</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="kehu_ayalysis">
									<tr>
										<td align="center"><s:property value="KHDA_GSMC"/></td>
										<td align="center">
											<s:if test="KHDA_CS != null">
												<s:property value="KHDA_CS"/>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="KHDA_KHJD != null">
												<s:property value="KHDA_KHJD"/>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="KHDA_GSXZ != null">
												<s:property value="KHDA_GSXZ"/>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="xiangguanxingwei != 0">
												<span style="color:red"><s:property value="xiangguanxingwei"/></span>个<br/>
												<a href="myContactTags_.action?tablename=LXXW&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="xiangguanrizhi != 0">
												<span style="color:red"><s:property value="xiangguanrizhi"/></span>个<br/>
												<a href="myContactTags_.action?tablename=GRRZ&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="xiangguanxiangmu != 0">
												<span style="color:red"><s:property value="xiangguanxiangmu"/></span>个<br/>
												<a href="myContactTags_.action?tablename=XMDA&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="SK != 0">
												<span style="color:red"><s:property value="SK"/></span>元<br/>
												<a href="myContactTags_.action?tablename=SK&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="FK != 0">
												<span style="color:red"><s:property value="FK"/></span>元<br/>
												<a href="myContactTags_.action?tablename=FK&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="YS != 0">
												<span style="color:red"><s:property value="YS"/></span>元<br/>
												<a href="myContactTags_.action?tablename=YS&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="YF != 0">
												<span style="color:red"><s:property value="YF"/></span>元<br/>
												<a href="myContactTags_.action?tablename=YF&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="QTSR != 0">
												<span style="color:red"><s:property value="QTSR"/></span>元<br/>
												<a href="myContactTags_.action?tablename=QTSR&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<s:if test="FYZC != 0">
												<span style="color:red"><s:property value="FYZC"/></span>元<br/>
												<a href="myContactTags_.action?tablename=FYZC&id=<s:property value="id"/>" >查看</a>
											</s:if>
											<s:else>-</s:else>
										</td>
										<td align="center">
											<span style="color:red"><s:property value="KHDA_FTCB"/></span>元<br/>
										</td>
										<td align="center">
											<span style="color:red">
											<s:property value="SK+YS+QTSR-FK-YF-FYZC"/>
											</span>元<br/>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			</form>
			<center><wysLib:page></wysLib:page></center>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
