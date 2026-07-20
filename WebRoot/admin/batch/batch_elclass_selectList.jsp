<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加培训班" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">可分配培训班</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%">
			<tr>
				<td width="200px;" valign="top" id="tree_list_td">
					<wysLib:clTypeTree href="batch_elclass_selectList.action?batchId=" rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand" onClick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">
					<s:form action="batch_elclass_selectList" name="myclist" theme="simple">
						<s:hidden name="batchId"></s:hidden>
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
					培训班名称：<s:textfield name="elClass.name"></s:textfield>
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
						<input type="submit" value="添加培训班"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="selectClass();">
					</s:else>
				</td>
			</tr>
		</table>
		<script>
			 	window.onunload = onunload_handler;   
			    function onunload_handler(){   
			        	//alert("刷新父窗口");
			        window.opener.location.reload(); //子窗口刷新父窗口   
			    }
		    
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
				      location = "batch_elclass_add.action?ids="+billIDs+"&batchId=${batchId}";
				   }
			    }

			   
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
		<wysLib:page></wysLib:page>
	</body>
</HTML>
