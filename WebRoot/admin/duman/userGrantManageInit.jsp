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
			//	alert(title);
				document.myForm.submit();
				window.returnValue = "nihao";
				window.close();
				//setTimeout(window.close(),2000);
			}
			function doSubmit3(treeType){
				document.myForm.submit();
				window.returnValue = "nihao";
				window.close();
			}
			function doSubmit2(treeType){
				document.myForm2.submit();
				window.returnValue = "nihao";
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
			<form action="userGrant.action" method="post" name="myForm">
				<s:hidden name="elUser.id"/>
				<s:hidden name="treeAllId"/>
				<s:hidden name="treeType"/>
			<!--
				用户名：<s:property value="elUser.username"/> 
				realname:<s:property value="elUser.realname"/> 
				 -->
				<!-- 判断树的类型 -->
				<!-- 题库树 -->
				<s:if test="treeType=='qlib'">
					<wysLib:qlibtree did="1" itype="cb_2" iname="question.qlib.id" treeType="qlib" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('qlib');" />
				</s:if>
				<!-- 课程树 -->
				<s:if test="treeType=='ctyp'">
					<wysLib:ctypeTree did="2" itype="cb_2" treeType="ctyp" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('ctyp');" />
				</s:if>
				<!-- 试卷树 -->
				<s:if test="treeType=='elib'">
					<wysLib:elibtree did="3" itype="cb_2" treeType="elib" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('elib');" />
				</s:if>
				<!-- 培训班树 -->
				<s:if test="treeType=='clty'">
					<wysLib:clTypeTree did="4" itype="cb_2" treeType="clty" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('clty');" />
				</s:if>
				<!-- 考场树 -->
				<s:if test="treeType=='eroo'">
					<wysLib:eroomLibTree did="5" itype="cb_2" treeType="eroo" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('eroo');" />
				</s:if>
				<!-- 部门树 -->
				<s:if test="treeType=='depl'">
					<wysLib:dep_list_aj rootAble="false" iname="departments.id" itype="cb"></wysLib:dep_list_aj>
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
					<script type="text/javascript">
					w0.setValues([<s:iterator value="departments" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments.size-1)!=#depst.index">,</s:if></s:iterator>]);
					</script>
				</s:if>
				<!-- 岗位树 -->
				<s:if test="treeType=='st'">
					<wysLib:st_list_aj rootAble="false" iname="stations.id" itype="cb"></wysLib:st_list_aj>
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('st');" />
					<script type="text/javascript">
					s0.setValues([<s:iterator value="stations" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(stations.size-1)!=#depst.index">,</s:if></s:iterator>]);
					</script>
				</s:if>
				<!-- 词汇树(节点权限选择方式与试卷库相同) -->
				<s:if test="treeType=='wd'">
					<wysLib:wordsTree did="6" itype="cb_2" treeType="wd" ></wysLib:wordsTree>
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('wd');" />
		<!--  	<script type="text/javascript">
					d0.setValues([<s:iterator value="words" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(words.size-1)!=#depst.index">,</s:if></s:iterator>]);
					</script> 	 -->	
				</s:if>
				<s:if test="treeType=='use'">
					<wysLib:dep_list_aj rootAble="false" iname="departments.id" itype="cb"></wysLib:dep_list_aj>
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
					<script type="text/javascript">
					w0.setValues([<s:iterator value="departments" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(departments.size-1)!=#depst.index">,</s:if></s:iterator>]);
					</script>
				</s:if>
				
				
				
				<!-- 素材树 -->
				<s:if test="treeType=='stuf'">
					<wysLib:stuffTree did="7" itype="cb_2" treeType="stuf" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('stuf');" />
				</s:if>
				<!-- 新闻树 -->
				<s:if test="treeType=='news'">
					<wysLib:newsTypeTree did="8" itype="cb_2" treeType="news" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('news');" />
				</s:if>
				<!-- 知识树 -->
				<s:if test="treeType=='klty'">
					<wysLib:kltype_list did="9" itype="cb_2" treeType="klty" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('klty');" />
				</s:if>
				<s:if test="treeType=='ptype'">
					<wysLib:productTypeTree did="10" itype="cb_2" treeType="ptype" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('ptype');" />
				</s:if>
				</form>
				<!-- 版面 -->
				<s:form action="userGrant.action" method="post" name="myForm2">
				<s:hidden name="elUser.id"/>
				<s:hidden name="treeType"/>
				<s:if test="treeType=='bmsq'">
					<s:if test="fbtypes.size==0">
				    	无版面权限
				    </s:if>
				    <s:else>
					    <s:iterator value="fbtypes">
			    		 	  	<!-- <DIV><s:property value="name"/></DIV> -->
					    	<s:if test="fblocks.size>0">
					    	<div style="margin-left:20px;line-height:16px;">
					    	     <s:property value="name"/>
					    		 <s:iterator value="fblocks">
					    		 	  	<div style="margin-left:20px;line-height:16px;">
					    		 	  		<input type="checkbox" name="chkNames" value="<s:property value="id"/>"
					    		 	  			<s:if test="isChecked==1"> checked="checked" </s:if>
					    		 	  		/>
					    		 	  		<s:property value="title"/>
					    		 	  	</div>
					    		 </s:iterator>
					    	</div>
					    	</s:if>
					    </s:iterator>
				    </s:else>
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit2('bmsq');" />
				</s:if>
				</s:form>
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	</body>
</HTML>
