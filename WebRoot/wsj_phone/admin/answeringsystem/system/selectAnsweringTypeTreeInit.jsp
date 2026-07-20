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
		<TITLE>选择所属类别</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doSubmit(treeType){
				var did =$("input[name='ansty']:checked").val(); 
				var bh ="";
				if(did)
					$.ajax({	async:false,  //   
							type:"post",   
						    url:"viewAnsweringTypeTree.action",   
						    data:{"x":Math.random(),"answeringTypeTree.id":did,"optype":"ajax"},   
							success:function(data){
								jd = eval("("+data+")");
								bh=jd.bh+"-=wys=-"+jd.name+"-=wys=-"+jd.id;
						 }});
				window.returnValue = bh;
				window.close();
			}
		</script>
	</HEAD>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<form action="userRegister.action" method="post" name="myForm">
				<!-- 部门树 -->
					<wysLib:answeringTypeTree_list_aj did="0" iname="ansty" itype="ra" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('ansty');" />
			</form>
			
		</div>
	
	</body>
</HTML>
