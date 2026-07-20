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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function load(){
				if("${product_sh}" == "0"){
				}else{
					if("${product_sh}" == "1"){
						alert("产品已提交,请等待审核!");
					}else{
						alert("产品已发布!");
					}
				}
			}
			
		</script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
			}
			
			function delProduct(id){
				var assignProduct = document.getElementById("assignProduct");
				if(window.confirm("确定删除？")){
					assignProduct.action="deleteChanpin.action?id="+id;
				    assignProduct.submit();
					//window.location.href="deleteChanpin.action?id="+id+"&pN="+pN;
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
				var value = document.getElementById("hot").value;
				document.getElementById("zhengzhantuijian").value = value;
				assignProduct.submit();
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
	<body onLoad="load();">
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
						<wysLib:productTypeTree href="productList.action?sublibs=1&product.ptype.id="
							rootAble="true"></wysLib:productTypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
							<form action="productList.action?product.ptype.id=<s:property value="product.ptype.id"/>" method="post" name="assignProduct" id="assignProduct">
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
	       							发布时间&nbsp;
									从<INPUT class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onclick="setday(this)">
	       						 	到<INPUT class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onclick="setday(this)">
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
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="20"></th>
									<th width="80" height="30" align="center" >
										产品名称									
									</th>
									<th width="80" height="30" align="center" >
										所属栏目								 
									</th>
									<th width="80" height="30" align="center" >
										市场价									
									</th>
									<th width="80" height="30" align="center" >
										会员价									
									</th>
									<th width="80" height="30" align="center" >
										发布时间									
									</th>
									<th width="80" height="30" align="center" >
										生产商									
									</th>
									<th width="80" height="30" align="center" >
										审核状态									
									</th>
									<th width="100" height="30" align="center"  colspan="3">
										操作									
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="productList">
									<tr>
										<td width="20" height="20" align="center">
											<input type="checkbox" value="<s:property value="id"/>"
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
											<s:date name='fabushijian' format="yyyy-MM-dd hh:mm:ss"/>
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shengchanshang" />
									    </td>
									    <td width="70" height="30" align="center" >
											<s:property value="shenhezhuangtai_entity.shenhezhuangtai" />
									    </td>
									    <td align="center" valign="middle"  height="30"><p><a href="showChanpin.action?id=${id }" class="textbg4">查看</a></p></td>
									    <s:if test="is_product_fabu_can_alter==true">
									    	<td align="center" valign="middle"><p><a href="updateChanpinView.action?id=${id }" class="textbg4">修改</a></p></td>
									    </s:if>
									    <s:else>
									    	<s:if test="shenhezhuangtai != 2">
										    	<td align="center" valign="middle"><p><a href="updateChanpinView.action?id=${id }" class="textbg4">修改</a></p></td>
    								    	</s:if>
    								    	<s:else>
    								    		<td align="center" valign="middle"></td>
    								    	</s:else>
									    </s:else>
									</tr>
								</s:iterator></tbody>
						  </table>
					</td>
				</tr>
			</table>
			<br>
			热度属性:<SELECT id="hot" style="WIDTH: 80px" name="product.zhengzhantuijian" 
	              onchange="product.zhengzhantuijian.value=this.options[this.selectedIndex].value;">
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
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</s:form>
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
										   








