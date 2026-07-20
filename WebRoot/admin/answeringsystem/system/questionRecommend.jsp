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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="问题推荐" /></div>
			</li>
		</ul>
		<form action="questionRecommend.action" name="quesform" method="post">
		<div style="width: 100%; text-align: center; margin-top: 30px;">
			<s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			<input type="text" name="ques.name" value="<s:property value="ques.name"/>">
			<input type="button" onClick="fsearch();" value="搜索">
		</div>
		<s:if test="queses.size==0">
			<div style="width: 100%; text-align: center; margin-top: 30px;">
				暂无问题
			</div>
		</s:if>
		<s:else>
		
			<table width="86%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<th>
					</th>
					<th>
						标题
					</th>
					<th>
						类别
					</th>
					<th>
						发布时间
					</th>
					<th>
						有效期
					</th>
					<th>
						回复数
					</th>
					<th>
						浏览数
					</th>
					<th>
						状态
					</th>
				</tr>
				<s:iterator value="queses">
					<tr>
						<td>
							<input type="checkbox" name="id" value="<s:property value='id' />"/>
						</td>
						<td height="30" align="left" style="padding-left:8px;color:blue;">
							[<s:property value="hotName"/>]<s:property value="name" />
						</td>
						<td align="center" >
							<s:property value="answeringType.name" />
						</td>
						<td align="center" >
							<s:date name="fabuTime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<s:date name="validTime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<s:property value="answerCount" />
						</td>
						<td align="center" >
							<s:property value="viewCount" />
						</td>
						<td align="center" >
							<s:property value="status_" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<div style="width: 100%; text-align: center; margin-top: 10px;">
				<wysLib:page></wysLib:page>
			</div>
		</s:else>
		</form>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				quesform.action="myQues.action";
				quesform.submit();
			}
			function fsearch( ){
				quesform.action="questionRecommend.action";
				document.getElementById("pageNow").value=0;
				quesform.submit();
			}
			function setStatusTow(){
				var chs = document.getElementsByName("id");
				var ids = "";
				if(chs!=undefined && chs.length>0){
					for(var i=0;i<chs.length;i++){
						if(chs[i].checked){
							ids += chs[i].value + ",";
						}
					}
				}
				if(ids==""){
					alert("请至少选择一个!!!");
					return ;
				}
				if(ids!="" && ids.charAt(ids.length-1)==","){
					ids = ids.substring(0,ids.lastIndexOf(","));
					document.getElementById("ids").value = ids;
				}
				document.statusTow.action = "setStatusTow.action";
				if(window.confirm("确认设置?")){
					document.statusTow.submit();
				}
			}
		</script>
		<div style="width: 100%; text-align: center; margin-top: 10px;">
			<form action="setStatusTow.action" name="statusTow" method="post">
			<input type="hidden" name="ids" id="ids"/>
			<select name="ques.statusTow">
				<option value=0>头条</option>
				<option value=1>重点</option>
				<option value=2>热门</option>
				<option value=3>推荐</option>
				<option value=4>幻灯</option>
			</select>
			<input type="button" value="设置" onclick="setStatusTow();" />
			</form>
		</div>
	</body>
</HTML>
