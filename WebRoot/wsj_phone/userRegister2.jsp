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
		<TITLE>选择部门编号</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript">
			function doSubmit(treeType){
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
				var did =$("input[name='depl']:checked").val(); 
				var bh ="";
				if(did)
					$.ajax({	async:false,  //   
							type:"post",   
						    url:"sta_view.action",   
						    data:{"x":Math.random(),"station.id":did,"optype":"ajax"},   
							success:function(data){
								jd = eval("("+data+")");
								bh=jd.bh+"-=wys=-"+jd.name+"-=wys=-"+jd.id;
						 }});
						 alert(bh);
				window.returnValue = bh;
				window.close();
				//setTimeout(window.close(),2000);
			}
		</script>
	</HEAD>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<form action="userRegister2.action" method="post" name="myForm">
				<!-- 岗位树 -->
					<wysLib:st_list_aj did="0" iname="depl" itype="ra" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
			</form>
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	
	</body>
</HTML>
