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
		<TITLE>部门授权</TITLE> 
		<base target="_self" href="<%=basePath%>">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function DEP(id,lid,rid){
				this.id = id;
				this.lid = lid;
				this.rid = rid;
			}
			function doSubmit(treeType){
				//1.获取所有被选中的节点
				var allChk = $("input[type=checkbox]");
				var chk ;
				var title="";//存放了node.id和node.pid
				var name = "";
				var id = "";
				var oid_qid="";
				var oid_oid="";
				var bool=false;
				var html = "";
				for(var i=0;i<allChk.length;i++){
					if(allChk[i].checked==true){
						title=allChk[i].title;
						id = allChk[i].id;
						name = document.getElementById(id+"_a").innerHTML;//部门名称
						oid_qid=title.substr(title.lastIndexOf("_")+1,title.length);//部门父id
						oid_oid = title.substring(4,title.lastIndexOf("_"));//部门id
						html += oid_oid + "==" + name + ",";
					}
				}
				if(html!=""&&html.charAt(html.length-1)==","){
					html = html.substring(0,html.lastIndexOf(","));
				}
				window.returnValue = html;
				window.close();
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>
		<div style="margin-left: 20px;">
		<wysLib:dep_list_aj rootAble="false" iname="departments.id" itype="cb" did="0"></wysLib:dep_list_aj>
		<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
		<script type="text/javascript">
					w0.setValues([<s:iterator value="departments" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments.size-1)!=#depst.index">,</s:if></s:iterator>]);
					</script>
		</div>
	</body>
</HTML>
