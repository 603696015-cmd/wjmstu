<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>设备管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="<%=path %>/js/calendar.js"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
			}
			
			function delShebei(id){
				var assignProduct = document.getElementById("assignProduct");
				if(window.confirm("确定删除？")){
					assignProduct.action="deleteShebei.action?id="+id+"&delete_inallList="+"all";
				    assignProduct.submit();
				}
			}
		</script>
		
		<script type="text/javascript">
			function select_All(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			
			function select_Fan(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			
			function select_Bux(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			
			function del(){
			  if(window.confirm("确定删除？")){
			     var checkObj = document.getElementsByName("id");
				    var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				   var shebeiIds = document.getElementById("shebeiIds");
				   
				   var flag = checkShenhezhuangtai(billIDs);//flag为true时可以删除
				   if(flag ){
				   		return;
				   }
			       shebeiIds.value=billIDs;
			       assign.action="deleteShebei.action?id="+id+"&delete_inallList="+"all";
				   assign.submit();
				}
			}
			
			//判断要删除的是否是通过审核的产品,是的话删除取消
			function checkShenhezhuangtai(shebeiIds){
				shebeiIds = shebeiIds.split(",");//string数组
				var checks = new Array();
				var flag = false;
				for(var i=0;i<shebeiIds.length;i++){
					$.ajax({
					  type: 'POST',
					  url: "checkShztBeforeDelete.action",
					  data: {id:parseInt(shebeiIds[i])},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")");
				  		checks[i] = data.check_json_result;
				  		
				  		for(var j=0;j<checks.length;j++){
							if(checks[j] == true){
								alert("所选产品有已经发布的产品，不能删除!");
								flag = true;
							}else{
								continue;
							}
						}
					  }
					});
				}
				return flag;
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="全部设备列表页" /></div>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top">
							<form action="allShebeiList.action" method="post" name="assignProduct" id="assignProduct">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<div>
								<center>
									发布时间&nbsp;
									从<INPUT class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onClick="setday(this)">
	       						 	到<INPUT class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onClick="setday(this)">
      								<br>
									
      								投保状态
      								<select name="shebei.toubaozhuangtai"  id="toubaozhuangtai" style="WIDTH: 110px" 
      									onclick="shebei.toubaozhuangtai.value=this.options[this.selectedIndex].value">
      									<option value="">
											==投保状态== 
										</option>
										<s:iterator value="toubaozhuangtaiList">
										<option value="<s:property value="id"/>">
											<s:property value="toubaozhuangtai"/> 
										</option>
										</s:iterator>
									</select>
      								设备名称&nbsp;<input type="text" name="shebei.name" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							<br>
	       							
	       							审核状态&nbsp;
	       							<select name="shebei.shenhezhuangtai"   style="WIDTH: 110px" 
      									onclick="shebei.shenhezhuangtai.value=this.options[this.selectedIndex].value">
      									<option value="">
											==审核状态==
										</option>
										<s:iterator value="shenhezhuangtaiList">
										<option value="<s:property value="id"/>">
											<s:property value="shenhezhuangtai"/> 
										</option>
										</s:iterator>
									</select>
									设备类型&nbsp;
	       							<SELECT id="shebeileixing" style="WIDTH: 150px" name="shebei.shebeileixing" 
								      	onchange="shebei.shebeileixing.value=this.options[this.selectedIndex].value;">
								        <OPTION value="" selected>==请选择设备类型==</OPTION>
								        <OPTION value=移动类>移动类</OPTION>
								        <OPTION value=非移动类>非移动类</OPTION>
								        <OPTION value=其他>其他</OPTION>
								    </SELECT><br>
								    发布者&nbsp;<input type="text" name="shebei.fabuzhe" />&nbsp;&nbsp;&nbsp;
								    发布者所在单位&nbsp;<input type="text" name="shebei.fabuzhesuozaidanwei" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							<input type="submit"  value="搜索" />
								</center>
								</div> 
							</form>
						<s:if test="productList.size==0"><h3 align="center" style="margin-top:10px;">没有搜到设备</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="20"></th>
									<th height="30" align="center" >
										设备名称									
									</th>
									<th width="80" height="30" align="center" >
										登记编号								 
									</th>
									<th width="80" height="30" align="center" >
										发布者								 
									</th>
									<th width="100" height="30" align="center" >
										发布者单位									</th>
									<th width="80" height="30" align="center" >
										设备类型									
									</th>
									<th width="80" height="30" align="center">
										投保状态							
									</th>
									<th width="80" height="30" align="center">
										审核状态							
									</th>
									<th width="90" height="30" align="center" >
										发布时间									</th>
									<th width="100" height="30" align="center"  colspan="5">
										操作									
									</th>
									
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="shebeilist">
									<tr>
										<td width="20" height="20" align="center">
											<input type="checkbox" value="<s:property value="id"/>"
												name="id">
										</td>
										<td height="30" style="padding-left:8px;color:blue;" align="center">
											<s:property value="name" />
									    </td>
										<td height="30" align="center" >
												<s:property value="dengjibianhao" />
										</td>
										<td height="30" align="center" >
												<s:property value="fabuzhe" />
										</td>
										<td width="100" height="30" align="center" >
												<s:property value="fabuzhesuozaidanwei" />
									  </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shebeileixing" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="toubaozhuangtai_entity.toubaozhuangtai" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shenhezhuangtai_entity.shenhezhuangtai" />
									    </td>
									    <td width="90" height="30" align="center" >
									    	<s:date name="fabushijian" format="yyyy-MM-dd hh:mm:ss"/>
									  </td>
										
										<td align="center" valign="middle"><p><a href="showShebei.action?id=${id }" class="textbg4">查看</a></p></td>
										<s:if test="is_product_fabu_can_alter==true"><!-- 允许修改 -->
										    <td align="center" valign="middle"><p><a href="updateShebeiView.action?id=${id }&update_inallList=1" class="textbg4">修改</a></p></td>
	    								    <!-- <td align="center" valign="middle"><p><a href="javascript:delShebei( ${id })">删除</a></p></td> -->
										</s:if>
										<s:else><!-- 不允许修改 -->
											<s:if test="shenhezhuangtai != 2">
											    <td align="center" valign="middle"><p><a href="updateShebeiView.action?id=${id }&update_inallList=1" class="textbg4">修改</a></p></td>
											</s:if>
											<s:else>
												<td align="center" valign="middle"></td>
											</s:else>
										</s:else>
										
    								    <s:if test="is_shebei_sh==true">
    								    	<s:if test="shenhezhuangtai==1"><!-- 已创建 -->
	    								    	<td align="center" valign="middle"><p><a href="shenheShebei.action?id=${id }" class="textbg4">通过</a></p></td>
	    								    	<td align="center" valign="middle"><p><a href="shenheShebeiNotPass.action?id=${id }" class="textbg6">不通过</a></p></td>
	    								    </s:if>
	    								    <s:if test="shenhezhuangtai==2"><!-- 审核通过 -->
	    								    	<td></td>
	    								    	<td align="center" valign="middle"><p><a href="shenheShebeiNotPass.action?id=${id }" class="textbg6">不通过</a></p></td>
	    								    </s:if>
	    								    <s:if test="shenhezhuangtai==3"><!-- 审核不通过 -->
	    								    	<td align="center" valign="middle"><p><a href="shenheShebei.action?id=${id }" class="textbg4">通过</a></p></td>
	    								    </s:if>
    								    </s:if>
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<br>
			<a href="javascript:select_All();" />全选</a>
			<a href="javascript:select_Fan();" />反选</a>
			<a href="javascript:select_Bux();" />全不选</a>
			<a href="javascript:del();" />删除</a>
			<s:form action="deleteShebei.action" method="post" name="assign">
				<s:hidden name="shebeiIds" id="shebeiIds" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</s:form>
			
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
										   








