<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<HTML>
	<HEAD>
		<base target="_self"/>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>可选择的课程</TITLE>
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript">

		    window.onunload = onunload_handler;   
		    function onunload_handler(){   
		        	//alert("刷新父窗口");
		       // window.opener.location.reload(); //子窗口刷新父窗口   
		    }   		
		</script>
	</HEAD>
	<BODY style="height: 100%; width: 100%; text-align: center; overflow-x: scroll;">
		<ul class="nav" style="text-align: left;">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的课程</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div  style="text-align: center;width: 100%;overflow-x: scroll;"> 
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px" valign="top" id="tree_list_td">
							<wysLib:clTypeTree
						href="batch_elclass_List.action?sublibs=1&peixunBatch.id=${peixunBatch.id}&cltype.id=" rootAble="true" />
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="batch_elclass_List" name="myclist" theme="simple">
						<s:hidden name="peixunBatch.id"></s:hidden>
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="cltype.id"></s:hidden>
						<s:hidden name="elclass.sqlw" id="sqlw" />
					培训班名称：<s:textfield name="elclass.name"></s:textfield>
						<s:submit value="搜索"></s:submit>
					</s:form>

					<s:if test="elclasses.size==0">没有符合条件的培训班</s:if>
						<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1" >
							<tr>
								<th align="center" >
								</th>
								<th align="center" >
									培训班名称
								</th>
								<th align="center" >
									证书名称
								</th>
								<th align="center" >
									必修学分(课程数)
								</th>
								<th align="center" >
									选修学分(课程数)
								</th>
								<th align="center" >
									最少选修学分
								</th>
								<th align="center" >
									学员人数
								</th>
								<th align="center" >
									开放状态
								</th>
							</tr>
							<s:iterator value="elclasses">
								<tr>
									<td align="center" >
										<input type="checkbox" value="<s:property value="id"/>" name="elclass.id">
									</td>
									<td align="center" >
										<s:property value="name" />
									</td>
									<td align="center" >
										<s:property value="certificatename" />
									</td>
									<td align="center" >
										<s:property value="bxCredit" />(
										<s:property value="bxCount" />)
									</td>
									<td align="center" >
										<s:property value="xxCredit" />(
										<s:property value="xxCount" />)
									</td>
									<td align="center" >
										<s:property value="optionalcredit" />
									</td>
									<td align="center" >
										<s:property value="stuCount" />
									</td>
									<td align="center" >
										<s:property value="statusName" />
									</td>
								</tr>
							</s:iterator>
								</table>
							</form>
							<wysLib:page></wysLib:page>
							<input type="submit" value="选  班" class="textbg6"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="selectClass();">
							
						</s:else><input type="button" value="关  闭" class="textbg6"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="window.close();">
					</td>
				</tr>
			</table>
			<form action="batch_elclass_add.action" name="eca" method="post">
				<s:hidden name="ids" id="ids"></s:hidden>
				<s:hidden name="status" id="status"></s:hidden>
				<s:hidden name="peixunBatchId" id="peixunBatchId"></s:hidden>
				<s:hidden name="elclassId" id="elclassId"></s:hidden>
			</form>

			<script>
			    function selectClass(){
			       var checkObj = document.getElementsByName("elclass.id");
				   var billIDs = "";
				   for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					}
				   if(billIDs==""){
					  alert("请选择要添加的记录！");
					  return ;
				   }
				   if(confirm('确定选择？')){
				   		$("#ids").val(billIDs);
				   		//$("#status").val(${status});
				   		$("#peixunBatchId").val(${peixunBatch.id});
				   		//$("#elclassId").val(${peixunBatch.elclassId});
				   		
				   		eca.submit();
				   }
			    }
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
		</div>

		<!-- 内容 -->
	</BODY>
</HTML>
