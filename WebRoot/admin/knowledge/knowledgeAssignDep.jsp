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
		<TITLE>练习管理</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
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
							//alert(allChk[i].value+"可以上传.");
							allChk[i].name="chkNames";
						}
						bool=false;
					}
				}
				//alert(vv);
				document.myForm.submit();
				//setTimeout(window.close(),2000);
			}
		</script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="资源目录分配给部门" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div align="center" style="background-color:#F8FCFE;">
			资源目录分配给部门
			<form action="knowledgeAssignDepDo.action" method="post" name="myForm">
			  <table width="800">
			    <tr>
				    <td style="vertical-align:top;width:400px;">
						<wysLib:kltype_list did="1" itype="cb_2" treeType="klty" />
			  	    </td>
				    <td style="vertical-align:top;width:400px;">
						<wysLib:dep_list_aj rootAble="false" iname="departments.id" itype="cb"></wysLib:dep_list_aj>
						<script type="text/javascript">
						w0.setValues([<s:iterator value="departments" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments.size-1)!=#depst.index">,</s:if></s:iterator>]);
						</script>
				    </td>
		  	    </tr>
			  </table>
			</form>
			<br />
			<a href="javascript:doSubmit('klty');" class="textbg6">确认提交</a>
			<font style="font-size:13px;width:150px;" color="#0033ff"><s:property value="#request.elmessage" /></font>
			<span style="font-size:13px;width:350px;color:#0033ff">注意：部门树选中上级节点后就不需要再选中下级节点</span>
		</div>
	</body>
</HTML>