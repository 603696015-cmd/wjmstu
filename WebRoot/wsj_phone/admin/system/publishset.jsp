<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文�?/title>

<script type="text/javascript">
	function NewPb(n){
		if(n==1){
			var s = document.getElementById("new").value;
			location = "pubNew.action?message=1&number="+s;
		}
		if(n==2){
			var start = document.getElementById("start").value;
			var end = document.getElementById("end").value;
			location = "pubNew.action?message=2&start="+start+"&end="+end;
		}
		if(n==3){
			var select = document.getElementById("select").value;
			location = "pubNew.action?message=3&ntype.id="+select;
		}
		if(n==4){
			var all = document.getElementsByName("all");
			for(var i=0;i<all.length;i++){
				var s = all.item(i).getAttribute("value");
				location="pubNew.action?message=4&all="+s;
			}
		}
		if(n==5){
			var list = document.getElementsByName("list");
			for(var i=0;i<list.length;i++){
				if(list.item(i).checked){
					var s = list.item(i).getAttribute("value");
					if(s==1){
						var list_1 = document.getElementById("list_1").value;
						location = "pubNew.action?message=5&number="+list_1;
					}
					if(s==0){
						location = "pubNew.action?message=5&number=3";
					}
				}
			}
		}
		if(n==6){
			var catalogue = document.getElementById("catalogue").value;
			var cata = document.getElementsByName("cata");
			var number = document.getElementById("number").value;
			for(var i=0;i<cata.length;i++){
				if(cata.item(i).checked){
					var s = cata.item(i).getAttribute("value");
					if(s==0){
						location = "pubNew.action?message=6&nid="+catalogue+"&number=3";
					}
					if(s==1){
						location = "pubNew.action?message=6&nid="+catalogue+"&number="+number;
					}
				}
			}
		}
	}
	
</script>

</head>

<body>
<table width="100%">
	<tbody>
		<tr>
			<td width="180" align="center" valign="top" style="border-bottom:solid; border-left:solid; border-right:solid; border-top:solid;">
				<div><strong>请选择要发布的模型</strong></div>
				<select style="width: 180px; height: 550px;" onchange="location.href=this.options[this.selectedIndex].value" size="2">
					<option value="channel.action?id=1" selected="selected">新闻系统</option>
				</select>
			</td>
			<td>
				<table>
					<tbody>
						<tr><td>发布内容页操�?/td></tr>
						<tr>
							<td height="35" align="center">发布最新添�?/td>
							<td width="78%" height="35">
								<input type="text"  name="pb" id="new" value="50" style="20%"/>篇文�?
								<input type="submit" onclick="NewPb(1)" border="0" value="发布>>"/>
							</td>
						</tr>
						<tr>
							<td height="35" align="center">按文章ID发布</td>
							<td height="35">
								�?input type="text" id="start"/>�?
								<input type="text" id="end"/>
								<input type="submit" onclick="NewPb(2)" border="0" value="发布>>"/>
							</td>
						</tr>
						<tr>
							<td height="50" align="center">按文章栏目发�?/td>
							<td height="50">
								<table width="100%" cellspacing="0" cellpadding="0" border="0">
									<tbody>
										<tr>
											<td width="39%">
												<select style="width:260" multiple="" size="10" id="select">
													<option  value="${ntypeTree.id }">${ntypeTree.name }</option>
													<s:iterator value="ntypeTree.child">
														<option value="${id }">--<s:property value="name"/></option>
													</s:iterator>
												</select>
											</td>
											<td width="61%">
												<input type="submit" border="0" onclick="NewPb(3)" value="发布选中栏目的文�?>"/>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
							<tr>
							<td height="30" align="center">发布所有文章页�?/td>
							<td height="30">
								<input type="radio" checked="checked" value="0" name="all" />仅发布未生成过Html的文�?
								<input type="radio" value="1" name="all" />发布所有页�?
								<input type="submit" onclick="NewPb(4)" border="0" value="发布>>"/>
							</td>
						</tr>
					</tbody>
				</table>
				<s:if test="publish_option==1||publish_option==2">
					<table width="100%" align="center" cellspacing="1" cellpadding="0" border="0" style="margin-top:2px">
					<tbody>
						<tr><td>发布文章栏目操作</td></tr>
						<tr>
							<td>发布全部栏目</td>
							<td>
								<table>
									<tbody>
										<tr>
											<td>
												<input type="radio" checked="checked" name="list" id="list_0" value="0"/>更新所有列表分�?br />
												<input type="radio"  value="1" name="list"/>仅发布每个列表页的前<input id="list_1" type="text"/>�?
											</td>
											<td><input type="submit" onclick="NewPb(5)" border="0" value="发布全部栏目>>"/></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">栏目发布</td>
							<td>
								<table>
									<tbody>
										<tr>
											<td><select id="catalogue">
												<option  value="${ntypeTree.id }">${ntypeTree.name }</option>
													<s:iterator value="ntypeTree.child">
														<option value="${id }">--<s:property value="name"/></option>
													</s:iterator>
											</select></td>
											<td>
												<input type="radio" checked="checked" name="cata" value="0"/>更新所有列表分�?br />
												
												<input type="radio" value="1" name="cata"/>仅发布每个列表页的前<input type="text" style="text-align:center" size="6" id="number"/>�?
												<input id="" type="submit" onclick="NewPb(6)" border="0" value="发布选中的栏�?>" name=""/>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
				</s:if>
				
			</td>
		</tr>
	</tbody>
</table>

	</body>

</html>
