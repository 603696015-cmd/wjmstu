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
		<TITLE>练习分配管理</TITLE> 
		<base target="_self" href="<%=basePath%>">
		<script type="text/javascript">
			function doSubmit(treeType){
				//1.获取所有被选中的节点
				var allChk=document.getElementsByName("chkName");
				var title="";//存放了node.id和node.pid
				var oid_qid="";
				var oid_oid="";
				var bool=false;
				//var vv=0;
				for(var i=0;i<allChk.length;i++){
					if(allChk[i].checked==true){
						title=allChk[i].title;
						oid_qid=title.substr(title.lastIndexOf("_")+1,title.length);
						for(j=0;j<allChk.length;j++){
							//vv++;
							oid_oid=allChk[j].value;
							if(oid_oid&&document.getElementById(""+treeType+""+oid_qid)==null){
								//alert(allChk[i].value+"可以上传");
								allChk[i].name="chkNames";
								bool=true;
								break;
							}
							if(oid_qid==oid_oid&&document.getElementById(""+treeType+""+oid_qid).checked){//&&document.getElementById(oid_qid).checked==true
								bool=true;
							}
						}
						if(j==allChk.length&&bool==false){//没有子节点
							//alert(allChk[i].value+"可以上传");
							allChk[i].name="chkNames";
						}
						bool=false;
					}
				}
				//alert(vv);
				document.myForm.submit();
				window.returnValue = "nihao";
				window.close();
				//setTimeout(window.close(),2000);
			}
			function doSubmit2(treeType){
				document.myForm2.submit();
				window.returnValue = "nihao";
				window.close();
			}
		</script>
	</HEAD>
	<body>
		<div style="margin-left: 20px;">
			<s:form action="examprac_doDep" method="post" name="myForm">
				<s:hidden name="examprac.id" />
				<!-- 部门树 -->
				<wysLib:dep_list_f did="6" itype="cb_2" treeType="depl" />
				<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
			</s:form>
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	</body>
</HTML>
