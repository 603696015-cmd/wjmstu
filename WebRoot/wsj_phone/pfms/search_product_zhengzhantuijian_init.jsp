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
		<TITLE>选择所属栏目编号</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/system.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/manage.css" />
		<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
		<script type="text/javascript">
			function Pro(){
				var index;//修改所在行
				var id ;//需要修改热度的商品id
				var zhengzhantuijian ;//修改的状态
			}
		</script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				document.forms[0].submit();
			}
			
			function doSubmit(){
				if(window.confirm("确定批量修改？")){
					//获取所有点击的数组
					var j=0;
					var productsArray = new Array();
					while(j<10){
						j++;
						var id_productName = "";
						var id_productNames;
						
						var names = document.getElementsByName("product_name_"+j);
						for(var i=0;i<names.length;i++){
							if(names[i].checked){
								id_productName = names[i].value;
							}
						}
						
						id_productNames = id_productName.split(",");
						
						var pro = new Pro();
						pro.id = id_productNames[0];
						pro.zhengzhantuijian = id_productNames[1];
						pro.index = id_productNames[2];
						if(j-1 >= 0){
							productsArray[j-1] = pro;
						}
					}
					var productListString = "";
					if(productsArray != null || productsArray != 'undefined'){
						for(var m=0;m<productsArray.length;m++){
							if(productsArray[m].id != ""){
								productListString = productListString + productsArray[m].id + "," + productsArray[m].zhengzhantuijian + "," + productsArray[m].index + ";";
							}
							
						}
						$.ajax({	
							async:false,  
							type:"post",   
						    url:"change_tuijian_product.action",   
						    data:{"x":Math.random(),productListString:productListString},   
							success:function(data){
								
								window.returnValue = productListString;
								
								window.close();
						 }});
					}
				}
			}
		</script>
	</HEAD>
	<body>
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
						<wysLib:productTypeTree href="search_zhengzhantuijian_init.action?sublibs=1&product.ptype.id="
							rootAble="true"></wysLib:productTypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table width="100%" align="center" cellpadding="2"
							cellspacing="2" bgcolor="#EBEBEB">
							<tr>
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
							</tr>
							<tbody  >
							<s:iterator value="productList" status="status">
								<tr>
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
								    	<s:date name='fabushijian' format="yyyy-MM-dd hh:mm:ss"/>
								    </td>
								    <td width="70" height="30" align="center" >
										<s:property value="shengchanshang" />
								    </td>
								    <td width="70" height="30" align="center" >
										<s:property value="shenhezhuangtai_entity.shenhezhuangtai" />
								    </td>
								    <td width="70" height="30" align="center">
										<s:property value="zhengzhantuijian" />
								    </td>
								</tr>
								<tr>
									<TD align="center">
								    	<label>
											普通
										</label>
										<input type="radio"  name="product_name_<s:property value='#status.index+1'/>" 
											value='<s:property value="id"/>,普通,<s:property value='#status.index+1'/>' />
										&nbsp;&nbsp;
										<label>
											推荐
										</label>
										<input type="radio"  name="product_name_<s:property value='#status.index+1'/>"
											value="<s:property value="id"/>,推荐,<s:property value='#status.index+1'/>"/>
										&nbsp;&nbsp;
										<label>
											重点
										</label>
										<input type="radio"  name="product_name_<s:property value='#status.index+1'/>"
											value="<s:property value="id"/>,重点,<s:property value='#status.index+1'/>"/>
										&nbsp;&nbsp;
										<label>
											热门
										</label>
										<input type="radio"  name="product_name_<s:property value='#status.index+1'/>"
											value="<s:property value="id"/>,热门,<s:property value='#status.index+1'/>"/>
										&nbsp;&nbsp;
										<label>
											幻灯
										</label>
										<input type="radio"  name="product_name_<s:property value='#status.index+1'/>"
											value="<s:property value="id"/>,幻灯,<s:property value='#status.index+1'/>"/>
										&nbsp;&nbsp;
								    </TD>
								</tr>
							</s:iterator>
							</tbody>
					  </table>
					</td>
				</tr>
			</table>
			<s:form action="search_zhengzhantuijian_init.action" method="post" name="assign">
				<s:hidden name="productids" id="productids" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</s:form>
			<wysLib:page></wysLib:page>
			<center>
				<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit();" class="textbg"/>
			</center>
		</div>
	
	</body>
</HTML>
