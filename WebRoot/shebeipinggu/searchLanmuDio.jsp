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
		<script type="text/javascript">
			function doSubmit(treeType){
				var did =$("input[name='pro']:checked").val(); 
				var bh ="";
				if(did)
					$.ajax({	
							async:false,  
							type:"post",   
						    url:"addProductView.action",   
						    data:{"x":Math.random(),"ptype.id":did,"optype":"ajax"},   
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
		<div style="margin-left: 20px;">
			<form action="searchLanmuInit.action" method="post" name="myForm">
				<!-- 栏目树 -->
					<wysLib:productTypeTree did="0" iname="pro" itype="ra" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('pro');" />
			</form>
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	</body>
</HTML>
