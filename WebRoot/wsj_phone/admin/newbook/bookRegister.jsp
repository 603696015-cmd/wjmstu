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
		<TITLE>选择图书类型</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
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
				var did =$("input[name='btype.parent.id']:checked").val(); 
				var bh ="";
				var btname ="";
				if(did)
					$.ajax({	async:false,  //   
							type:"post",   
						    url:"booktype_ajaxview.action",   
						    data:{"x":Math.random(),"bookTypeTree.id":did},   
							success:function(data){
								jd = eval("("+data+")");
								bh=jd.bh+"-=wys=-"+jd.name+"-=wys=-"+jd.id;
								btname=jd.name;
								window.opener.document.getElementById('bookinfoname').value=btname ;
								window.opener.document.getElementById('bookTypeid').value=jd.id ;
								window.close();
						 }});
				
				//setTimeout(window.close(),2000);
			}
		</script>
	</HEAD>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<form action="userRegister.action" method="post" name="myForm">
				<!-- 图书类型树 -->
					<wysLib:testbooktypeTree did="0" iname="btype.parent.id" itype="ra_2no" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
			</form>
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	
	</body>
</HTML>
