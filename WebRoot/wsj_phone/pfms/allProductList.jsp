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
		<TITLE>产品管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/system.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/manage.css" />
		<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="<%=path %>/js/calendar.js"></script>
		<script type="text/javascript">
			/*
			function search_zhengzhantuijian_init(){
			     width=1000;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("search_zhengzhantuijian_init.action?x="+Math.random(),null,sFeature);
				 
				 if(rv!=undefined&&rv!=""){
				 
					var array = rv.split(";");
					var ids = new Array();
					var zhengzhantuijians = new Array();
					var indexs = new Array();
					
					
					for(var p=0;p<array.length;p++){
						ids[p] = array[p].split(",")[0];
						zhengzhantuijians[p] = array[p].split(",")[1];
						indexs[p] = array[p].split(",")[2];
					}
					
					for(var i=0;i<indexs.length;i++){
						$("#now_value_"+indexs[i]).html(zhengzhantuijians[i]);
					}
					
					
				 }
			}
			*/
		
			function delProduct(id){
				var assignProduct = document.getElementById("assignProduct");
				if(window.confirm("确定删除？")){
					assignProduct.action="deleteChanpin.action?id="+id+"&delete_inallList="+"all";
				    assignProduct.submit();
					//window.location.href="deleteChanpin.action?id="+id+"&pN="+pN;
				}
			}
		
			function page(i){
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
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
							billIDs += checkObj[i].value;
						}
					}
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				    var productids = document.getElementById("productids");
				    var value = document.getElementById("hot").value;
				    if(value == ""){
				    	alert("请选择热度!");
				    	return ;
				    }else{
				    	 document.getElementById("select_tuijian").value = value;
				    }
			        productids.value=billIDs;
			        assign.action="change_tuijian_product.action";
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
							billIDs += checkObj[i].value;
						}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				   var productids = document.getElementById("productids");
				   
				   var flag = checkShenhezhuangtai(billIDs);//flag为true时可以删除
				   if(flag ){
				   		return;
				   }
			       productids.value=billIDs;
			       assign.action="deleteChanpin.action?id="+id+"&delete_inallList="+"all";
				   assign.submit();
				}
			}
			
			//判断要删除的是否是通过审核的产品,是的话删除取消
			function checkShenhezhuangtai(productids){
				productids = productids.split(",");//string数组
				var checks = new Array();
				var flag = false;
				var message = "";
				for(var i=0;i<productids.length;i++){
					$.ajax({
					  type: 'POST',
					  url: "checkShztBeforeDelete_product.action",
					  data: {id:parseInt(productids[i])},
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
						<wysLib:productTypeTree href="getAllProductAction.action?sublibs=1&product.ptype.id="
							rootAble="true"></wysLib:productTypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
							<form action="getAllProductAction.action?product.ptype.id=<s:property value="product.ptype.id"/>" method="post" name="assignProduct" id="assignProduct">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<s:hidden name="product.zhengzhantuijian" id="zhengzhantuijian"></s:hidden>
								<div>
								<center>
									审核状态&nbsp;
      								<select name="product.shenhezhuangtai"  id="shenhezhuangtai" style="WIDTH: 110px" 
      									onchange="this.value=this.options[this.selectedIndex].value;">
      									<option value="0">
											==审核状态==
										</option>
										<s:iterator value="shenhezhuangtaiList">
										<option value="<s:property value="id"/>">
											<s:property value="shenhezhuangtai"/> 
										</option>
										</s:iterator>
									</select>
      								产品名称&nbsp;<input type="text" name="product.name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							生产商&nbsp;<input type="text" name="product.shengchanshang">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							<br>
	       							发布者用户名&nbsp;<input type="text" name="product.pfmsUser.user.username">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							发布者姓名&nbsp;<input type="text" name="product.pfmsUser.user.realname">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							<br>
	       							发布时间&nbsp;
									从<INPUT class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onClick="setday(this)">
	       						 	到<INPUT class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onClick="setday(this)">
      								<br>
	       							关键词(注:关键词之间以空格隔开!)&nbsp;<input type="text" name="product.key">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       							包含下属栏目：
									<label>
										<input type="checkbox" name="sublibs" 
											 <s:if test="sublibs==1">checked="checked"</s:if> 
											 id="sublibs" value="1">
									</label>&nbsp;&nbsp;
	       							<input type="submit"  value="搜索" />
								</center>
								</div> 
							</form>
						<s:if test="productList.size==0"><h3 align="center" style="margin-top:10px;">没有搜到发布的产品</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="20"></th>
									<th width="60" height="30" align="center" >
										产品名称									
									</th>
									<th width="60" height="30" align="center" >
										所属栏目								 
									</th>
									<th width="60" height="30" align="center" >
										市场价									
									</th>
									<th width="60" height="30" align="center" >
										会员价									
									</th>
									<th width="80" height="30" align="center" >
										产品发布者									
									</th>
									<!-- <th width="100" height="30" align="center">
										发布者所在单位								
									</th> -->
									<th width="80" height="30" align="center" >
										发布时间									
									</th>
									<th width="80" height="30" align="center" >
										生产商									
									</th>
									<th width="60" height="30" align="center" >
										审核状态									
									</th>
									<th width="60" height="30" align="center" >
										热度									
									</th>
									<th width="150" height="30" align="center"  colspan="5">
										操作									
									</th>
									
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="productList" status="status">
									<tr>
										<td width="20" height="20" align="center">
											<input id="change_id_<s:property value='#status.index+1'/>" type="checkbox" value="<s:property value="id"/>"
												name="id">
										</td>
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
									    <!-- <td width="90" height="30" align="center" >
											<s:property value="fabuzhesuozaidanwei" />
									    </td> -->
									    <td width="70" height="30" align="center" >
									    	<s:date name='fabushijian' format="yyyy-MM-dd hh:mm:ss"/>
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shengchanshang" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shenhezhuangtai_entity.shenhezhuangtai" />
									    </td>
									    <td width="70" height="30" align="center" onClick="changeZhengzhantuijian('<s:property value="roleId"/>',<s:property value='#status.index+1'/>)">
											<p id="now_value_<s:property value='#status.index+1'/>"><s:property value="zhengzhantuijian" /></p>
											<div id="hot_change_<s:property value='#status.index+1'/>" style="display:none;">
											<SELECT id="select_tuijian_<s:property value='#status.index+1'/>"  style="WIDTH: 60px;"  
									              onchange="this.value=this.options[this.selectedIndex].value;" >
										        <OPTION value=普通>普通</OPTION>
										        <OPTION value=推荐>推荐</OPTION>
										        <OPTION value=重点>重点</OPTION>
										        <OPTION value=热门>热门</OPTION>
										        <OPTION value=幻灯>幻灯</OPTION>
									      	</SELECT>
									      	<a href="javascript:change(<s:property value='#status.index+1'/>);" class="textbg4">修改</a>
									      	</div>
									    </td>
									    <td align="center" valign="middle"><p><a href="showChanpin.action?id=${id }" class="textbg4">查看</a></p></td>
									    <s:if test="is_product_fabu_can_alter==true">
									    	<td align="center" valign="middle"><p><a href="updateChanpinView.action?id=${id }&update_inallList=1" class="textbg4">修改</a></p></td>
									    </s:if>
									    <s:else>
									    	<s:if test="shenhezhuangtai != 2">
										    	<td align="center" valign="middle"><p><a href="updateChanpinView.action?id=${id }&update_inallList=1" class="textbg4">修改</a></p></td>
    								    	</s:if>
    								    	<s:else>
    								    		<td align="center" valign="middle"></td>
    								    	</s:else>
									    </s:else>
									    
   								    	<s:if test="is_product_sh==true">
   								    		<s:if test="shenhezhuangtai==1">
	   								    		<td align="center" valign="middle"><p><a href="shenheChanpinNotPass.action?id=${id }" class="textbg6">不通过</a></p></td>
	   								    		<td align="center" valign="middle"><p><a href="shenheChanpin.action?id=${id }" class="textbg4">通过</a></p></td>
	   								    	</s:if>
	   								    	
	   								    	<s:if test="shenhezhuangtai==2">
	   								    		<td align="center" valign="middle"><p><a href="shenheChanpinNotPass.action?id=${id }" class="textbg6">不通过</a></p></td>
	   								    		<td></td>
	   								    	</s:if>
	   								    	<s:if test="shenhezhuangtai==3">
	   								    		<td></td>
	   								    		<td align="center" valign="middle"><p><a href="shenheChanpin.action?id=${id }" class="textbg4">通过</a></p></td>
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
			热度属性:
			<SELECT id="hot" style="WIDTH: 80px" name="product.zhengzhantuijian" 
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
			<a href="javascript:del()" />删除</a>
			<s:form action="deleteChanpin.action" method="post" name="assign">
				<s:hidden name="productids" id="productids" />
				<s:hidden name="select_tuijian" id="select_tuijian" />
				<!--<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="delete_inallList" id="delete_inallList"></s:hidden>-->
			</s:form>
			<wysLib:page></wysLib:page>
		</div>
	
	</body>
</HTML>
										   








