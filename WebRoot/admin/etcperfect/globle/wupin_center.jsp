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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
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
			function page(i){
				document.getElementById("pageNow").value=i;
				wupin.submit();
			}
			
			function merger(){
				document.getElementById("pageNow").value=0;
				document.getElementById("merger").value=1;
				wupin.submit();
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
					<wysLib:Navigation ivalue="物品过程中心" />
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
			<form action="wupin_center.action" name="wupin" method="post">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" /> 
			<s:hidden name="tablename" /> 
			<input type="hidden" name="merger" value="<s:property value='merger'/>"/>
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<tr>
					<td>关联模块</td>
					<td>
						<!--<s:select  name="moduleName" onchange="this.value=this.options[this.selectedIndex].value;"  id="moduleName"  
								list="moduleList" 
								theme="simple" headerKey="请选择模块" headerValue="请选择模块"  />-->
						<select name="moduleName" id="moduleName">
							<option value="请选择模块">
									请选择模块
							</option>
							<s:iterator value="moduleList" id="map">
								<s:iterator value="map" id="mapElement">
									<option value="<s:property value="key"/>">
											<s:property value="value" />
									</option>
								</s:iterator>
							</s:iterator>
						</select>
					</td>
					<td>状态下拉</td>
					<td>
						<s:select  name="status" onchange="this.value=this.options[this.selectedIndex].value;"  id="status"  
								list="{'已创建','通过'}" 
								theme="simple" headerKey="请选择状态" headerValue="请选择状态"  />
					</td>
					<td>名称仓库</td>
					<td>
						<s:select  name="cangkuname" onchange="this.value=this.options[this.selectedIndex].value;"  id="cangkuname"  
								list="cangkuList" 
								theme="simple" headerKey="请选择仓库" headerValue="请选择仓库"  />
					</td>
				</tr>
				<tr>
					<td>物品名称</td>
					<td><input type="text" name="wupinname"/></td>
					<td align="center" >
						时间段范围
					</td>
					<td >
						<label>
							开始时间<input type="text" name="starttime" 
								onclick="setday(this);">
						</label>
					</td>
					<td >
						<label>
							结束时间<input type="text" name="endtime"  
								onclick="setday(this);">
						</label>
					</td>
					<td align="center" ><input type="submit" value="搜索"  class="textbg4"/></td>
				</tr>
				</tbody>
			</table>
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					物品中心
				</caption>
				<tr>
					<th height="30" align="center">
						物品名称
					</th>
					<s:if test="merger != 1">
						<th height="30" align="center">
						仓库
						</th>
					</s:if>
					<!-- <th height="30" align="center">
						时间
					</th> -->
					<th height="30" align="center">
						销售数量
					</th>
					<th height="30" align="center">
						销售总价
					</th>
					<th height="30" align="center">
						销售退货数量
					</th>
					<th height="30" align="center">
						销售退货总价
					</th>
					<th height="30" align="center">
						采购数量
					</th>
					<th height="30" align="center">
						采购总价
					</th>
					<th height="30" align="center">
						采购退货数量
					</th>
					<th height="30" align="center">
						采购退货总价
					</th>
					<th height="30" align="center">
						出库数量
					</th>
					<th height="30" align="center">
						入库数量
					</th>
					<th height="30" align="center">
						存量
					</th>
					<th height="30" align="center">
						市场价
					</th>
					<th height="30" align="center">
						总价
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="wupinList" status="status">
						<tr>
						<td align='center'><s:property value="wupinname"/></td>
						<s:if test="merger != 1">
							<td align='center'><s:property value="cangku"/></td>
						</s:if>
						<!-- <td align='center'><s:property value="time"/></td> -->
						<td align='center'><s:property value="xiaoshoushuliang"/></td>
						<td align='center'><s:property value="xiaoshouzongjia"/></td>
						<td align='center'><s:property value="xiaoshoutuihuoshuliang"/></td>
						<td align='center'><s:property value="xiaoshoutuihuozongjia"/></td>
						<td align='center'><s:property value="caigoushuliang"/></td>
						<td align='center'><s:property value="caigouzongjia"/></td>
						<td align='center'><s:property value="caigoutuihuoshuliang"/></td>
						<td align='center'><s:property value="caigoutuihuozongjia"/></td>
						<td align='center'><s:property value="chukushuliang"/></td>
						<td align='center'><s:property value="rukushuliang"/></td>
						<td align='center'><s:property value="cunliang"/></td>
						<td align='center'>
							<s:if test="!#status.last">
								<s:i18n name="Format">
								   <s:text name="FormatNumeral" >
								       <s:param value="shichangjia"/>
								    </s:text>
								</s:i18n>
							</s:if>
						</td>
						<td align='center'><s:property value="zongjia"/></td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			</form>
			<br>
			<input type="button" value="合并同一物品" onclick="merger();"/>
			<center><wysLib:page></wysLib:page></center>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
