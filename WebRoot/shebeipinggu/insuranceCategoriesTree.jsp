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
		<TITLE>选择对应险种</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				name_post.submit();
			}
			function doSubmit(){
				//1.获取所有被选中的节点
				//var arrayBh=document.getElementsByName("depl");
				//alert(arrayBh.length);
				//var bh="";
				//for(var i=0; i<arrayBh.length;i++){  
				//	if(arrayBh[i].checked==true){
				//		//alert(arrayBh[i].alt);
				//		bh=arrayBh[i].alt;
				//		break;
				//	}
				//}
				//document.myForm.submit();
				var did =$(":radio:checked").val(); 
				var bh ="";
				if(did)
					$.ajax({	async:false,  //   
							type:"post",   
						    url:"PG_insuranceCategory_view.action",   
						    data:{"x":Math.random(),"IC.id":did,"optype":"ajax"},   
							success:function(data){
								jd = eval("("+data+")");
								bh=jd.bh+"-=wys=-"+jd.name+"-=wys=-"+jd.id;
						 }});
				window.returnValue = bh;
				window.close();
				//setTimeout(window.close(),2000);
			}
		</script>
	</HEAD>
	<body>
		<div id="div"></div>
		<form action="insuranceCategoriesTree.action" name="name_post" method="post">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
		</form>
		<div style="margin-left: 20px;">
				<table width="100%" align="center" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<tr>
						<th width="20"></th>
						<th width="100" height="30" align="center" >
							名称								 
						</th>
						<th width="90" height="30" align="center" >
							创建时间									
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="ICList">
						<tr>
							<td width="20" height="20" align="center">
								<input type="radio" value="<s:property value="id"/>"
									name="id">
							</td>
							<td height="30" align="center" >
									<s:property value="name" />
							</td>
							<td height="30" align="center" >
								<s:date name="createTime" format="yyyy-MM-dd hh:mm:ss"/>
							</td>
						</tr>
					</s:iterator>
					</tbody>
			  	</table>
			  	<center><wysLib:page></wysLib:page></center>
				<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit();" />
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	</body>
</HTML>
