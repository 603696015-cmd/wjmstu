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
		<TITLE>保险产品管理</TITLE>
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
			
			function delBaoxianProduct(id){
				var assignProduct = document.getElementById("assignProduct");
				if(window.confirm("确定删除？")){
					assignProduct.action="PG_deleteBaoxianProduct.action?id="+id+"&delete_inallList="+"all";
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
			
			function sure_submit(){
				if(window.confirm("确定修改热度属性？")){
			     	var checkObj = document.getElementsByName("id");
				    var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value.split("_")[0];
						}
					}
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				    var baoxianProductids = document.getElementById("baoxianProductids");
				    var value = document.getElementById("hot").value;
				    if(value == ""){
				    	alert("请选择热度!");
				    	return ;
				    }else{
				    	 document.getElementById("select_tuijian").value = value;
				    }
			        baoxianProductids.value=billIDs;
			        assign.action="PG_change_tuijian.action";
				    assign.submit();
				}
			}
			
			function del(){
			  if(window.confirm("确定删除？")){
			     var checkObj = document.getElementsByName("id");
				    var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value.split("_")[0];
						}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				   var baoxianProductids = document.getElementById("baoxianProductids");
				   
				   var flag = checkShenhezhuangtai(billIDs);//flag为true时可以删除
				   
				   if(flag ){
				   		return;
				   }
			       baoxianProductids.value=billIDs;
			       assign.action="PG_deleteBaoxianProduct.action?id="+id+"&delete_inallList="+"all";
				   assign.submit();
				}
			}
			
			//填写评估报告
			function writePinggu(){
				var checkObj = document.getElementsByName("id");
			    var billIDs = "";
			    for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				 }
				if(billIDs=="" || billIDs.split(",").length>1){
				  alert("请选择一个设备！");
				  return ;
			    }
			   	var baoxianProductId = billIDs.split("_")[1];
			   	var returnValue = "";
			   	var array = new Array(4);
			   	$.ajax({
				  type: 'POST',
				  url: "PG_findICById.action",
				  data: {id:parseInt(baoxianProductId)},
				  async:false,//同步
				  success: function(data){
				  	returnValue = eval("("+data+")").check_json_result;
				  	array = returnValue.split("==");
				  }
				});
			    document.getElementById("baoxianProduct.id").value = billIDs.split("_")[0];
			    document.getElementById("IC.id").value = billIDs.split("_")[1];
			    document.getElementById("IC.tableName").value = array[0];
			    if(array[1] != "biaodi"){
			    	document.getElementById("IC.read_auto_biaodi").value = array[1];
			    }
			    if(array[2] != "toubaoren"){
			    	document.getElementById("IC.read_auto_toubaoren").array = array[2];
			    }
			    if(array[3] != "beibaoren"){
			    	document.getElementById("IC.read_auto_beibaoren").array = array[3];
			    }
			    document.getElementById("iwanttoubao").submit();
			}
			
			//判断要删除的是否是通过审核的产品,是的话删除取消
			function checkShenhezhuangtai(baoxianProductids){
				baoxianProductids = baoxianProductids.split(",");//string数组
				var checks = new Array();
				var flag = false;
				var message = "";
				for(var i=0;i<baoxianProductids.length;i++){
					$.ajax({
					  type: 'POST',
					  url: "PG_checkShztBeforeDelete_baoxianProduct.action",
					  data: {id:parseInt(baoxianProductids[i])},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")");
				  		
				  		checks[i] = data.check_json_result;
				  		for(var j=0;j<checks.length;j++){
							if(checks[j] == true){
								message = "所选产品有已经发布的产品，不能删除!";
								flag = true;
							}else{
								continue;
							}
						}
					  }
					});
				}
				if(message != ""){
					alert(message);
				}
				return flag;
			}
			
			function showDialogue(){
				 width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("PG_search_tuijian.action?x="+Math.random(),null,sFeature);
				 /*
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("suoshulanmu_name").value=bh[1];
					 document.getElementById("suoshulanmu_id").value=bh[2];
				 }
				 */
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="产品列表页" /></div>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
						<wysLib:productTypeTree href="PG_baoxianAllProductList.action?sublibs=1&ptype.id="
							rootAble="true"></wysLib:productTypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
							<form action="PG_baoxianAllProductList.action?ptype.id=<s:property value="ptype.id"/>" method="post" name="assignProduct" id="assignProduct">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<s:hidden name="baoxianProduct.zhengzhantuijian" id="zhengzhantuijian"></s:hidden>
								<div>
								<center>
									发布时间&nbsp;
									从<INPUT class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onClick="setday(this)">
	       						 	到<INPUT class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onClick="setday(this)">
      								<br>
      								设备名称&nbsp;<input type="text" name="baoxianProduct.name" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							发布者所在单位&nbsp;<input type="text" name="baoxianProduct.fabuzhesuozaidanwei" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							<br>
	       							发布者&nbsp;<input type="text" name="baoxianProduct.fabuzhe" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							审核状态&nbsp;<select name="baoxianProduct.shenhezhuangtai"  id="shenhezhuangtai" style="WIDTH: 110px" 
      									onclick="baoxianProduct.shenhezhuangtai.value=this.options[this.selectedIndex].value">
      									<option value="">
											==审核状态== 
										</option>
										<s:iterator value="shenhezhuangtaiList">
										<option value="<s:property value="id"/>">
											<s:property value="shenhezhuangtai"/> 
										</option>
										</s:iterator>
									</select>
	       							<input type="submit"  value="搜索" />
	       							
								</center>
								</div> 
							</form>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="20"></th>
									<!-- <th width="80" height="30" align="center" >
										保单ID									
									</th> -->
									<th width="80" height="30" align="center" >
										设备名称									
									</th>
									<th width="80" height="30" align="center" >
										所属栏目								 
									</th>
									<th width="60" height="30" align="center" >
										市场价									
									</th>
									<th width="60" height="30" align="center" >
										会员价									
									</th>
									<th width="80" height="30" align="center" >
										发布者									
									</th>
									<th width="80" height="30" align="center">
										发布者单位								
									</th>
									<th width="80" height="30" align="center" >
										发布时间									
									</th>
									<th width="80" height="30" align="center" >
										审核状态									
									</th>
									<th width="60" height="30" align="center" >
										热度									
									</th>
									<th width="100" height="30" align="center"  colspan="5">
										操作									
									</th>
									
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="baoxianProductList" status="status">
									<tr>
										<td width="20" height="20" align="center">
											<input id="change_id_<s:property value='#status.index+1'/>" type="checkbox" value="<s:property value="id"/>_<s:property value="insuranceCategoryId" />"
												name="id">
										</td>
										<!-- <td height="30" style="padding-left:8px;color:blue;" align="center">
											<span id="insuranceCategoryId_<s:property value='#status.index+1'/>"><s:property value="insuranceCategoryId" /></span>
									    </td> -->
										<td height="30" style="padding-left:8px;color:blue;" align="center">
											<s:property value="name" />
									    </td>
										<td height="30" align="center" >
												<s:property value="lanmu.lanmu" />
										</td>
										<td height="30" align="center" >
											<s:property value="shichangjia" />
										</td>
										<td height="30" align="center" >
											<s:property value="huiyuanjia" />
										</td>
										<td width="70" height="30" align="center" >
											<s:property value="fabuzhe" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="fabuzhesuozaidanwei" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:date name='fabushijian' format="yyyy-MM-dd hh:mm:ss"/>
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shenhezhuangtai_entity.shenhezhuangtai" />
									    </td>
									    <td width="70" height="30" align="center" >
											<p id="now_value_<s:property value='#status.index+1'/>"><s:property value="zhengzhantuijian" /></p>
									    </td>
									    <td align="center" valign="middle"><p><a href="PG_showBaoxianProduct.action?id=${id }" class="textbg4">查看</a></p></td>
									    <td align="center" valign="middle"><p><a href="PG_updateBaoxianProductView.action?id=${id }&update_inallList=1" class="textbg4">修改</a></p></td>
									    <s:if test="is_baoxian_product_sh==true">
	   								    	<s:if test="shenhezhuangtai==1">
	   								    		<td align="center" valign="middle"><p><a href="PG_shenheBaoxianProductNotPass.action?id=${id }" class="textbg6">不通过</a></p></td>
	   								    		<td align="center" valign="middle"><p><a href="PG_shenheBaoxianProduct.action?id=${id }" class="textbg4">通过</a></p></td>
	   								    	</s:if>
	   								    	
	   								    	<s:if test="shenhezhuangtai==2">
	   								    		<td align="center" valign="middle"><p><a href="PG_shenheBaoxianProductNotPass.action?id=${id }" class="textbg6">不通过</a></p></td>
	   								    		<td></td>
	   								    	</s:if>
	   								    	<s:if test="shenhezhuangtai==3">
	   								    		<td></td>
	   								    		<td align="center" valign="middle"><p><a href="PG_shenheBaoxianProduct.action?id=${id }" class="textbg4">通过</a></p></td>
	   								    	</s:if>
   								    	</s:if>
									</tr>
								</s:iterator></tbody>
						  </table>
					</td>
				</tr>
			</table>
			<br>
			热度属性:
			<SELECT id="hot" style="WIDTH: 80px" name="baoxianProduct.zhengzhantuijian" 
	              onchange="this.value=this.options[this.selectedIndex].value;">
            	<OPTION value="">==请选择==</OPTION>
		        <OPTION value=普通>普通</OPTION>
		        <OPTION value=推荐>推荐</OPTION>
		        <OPTION value=重点>重点</OPTION>
		        <OPTION value=热门>热门</OPTION>
		        <OPTION value=幻灯>幻灯</OPTION>
	      	</SELECT>
			
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<a href="javascript:sure_submit()" />确认提交</a>
			<a href="javascript:del()" />删除</a><br><br>
			<a href="javascript:writePinggu();" >填写评估报告保单</a>
			<s:form action="PG_deleteBaoxianProduct.action" method="post" name="assign">
				<s:hidden name="baoxianProductids" id="baoxianProductids" />
				<s:hidden name="select_tuijian" id="select_tuijian" />
				<!--<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />-->
			</s:form>
			
			<form action="PG_IC_U_InfoInit.action" method="post"  id="iwanttoubao">
	    		<!-- <input type="hidden" name="id" id="shebei_id"/> --><!-- 设备id -->
	    		<!-- <input type="hidden" name="tablename" id="tablename" value="<s:property value='tablename'/>"/> -->
	    		<input type="hidden" name="actionName" id="actionName" value="Policy_AuditListInit"/>
	    		<input type="hidden" name="baoxianProduct.id" id="baoxianProduct.id" />
	    		<input type="hidden" name="IC.id" id="IC.id" />
	    		<input type="hidden" name="IC.tableName" id="IC.tableName" />
		    	<input type="hidden" name="IC.read_auto_biaodi" id="IC.read_auto_biaodi"/>
		    	<input type="hidden" name="IC.read_auto_toubaoren" id="IC.read_auto_toubaoren"/>
		    	<input type="hidden" name="IC.read_auto_beibaoren" id="IC.read_auto_beibaoren"/>
	    	</form>
			<wysLib:page></wysLib:page>
		</div>
	
	</body>
</HTML>
										   








