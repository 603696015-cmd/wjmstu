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
		<TITLE>用户授权管理</TITLE> 
		<base target="_self" href="<%=basePath%>">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
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
				//alert(title);
				document.myForm.submit();
				window.returnValue = "nihao";
				window.close();
				//setTimeout(window.close(),2000);
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>
		<div style="margin-left: 20px;">
			<form action="depGrant.action" method="post" name="myForm">
				<s:hidden name="kledge.id"/>
				<s:hidden name="competenceType"/>
				<wysLib:dep_list_aj rootAble="false" iname="departments.id" itype="cb"></wysLib:dep_list_aj>
				<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
				<script type="text/javascript">
				w0.setValues([<s:iterator value="departments" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments.size-1)!=#depst.index">,</s:if></s:iterator>]);
				</script>
		</div>
	</body>
</HTML>
