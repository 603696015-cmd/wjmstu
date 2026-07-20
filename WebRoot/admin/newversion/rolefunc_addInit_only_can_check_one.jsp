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
		<link rel="stylesheet" type="text/css" href="css/system3.css" />
		<link rel="stylesheet" type="text/css" href="css/manage3.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css">
			table td{
				vertical-align:top;
			}
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/tree/dtreerole_newVersion.js"></script>
		<script type="text/javascript">
			function init(funcCount){
				for(var i=0;i<funcCount;i++){
					var nihao="d"+i;
					eval(nihao).closeAll();
				}
				//alert(funcCount);
			}
			function opTree(funcCount){
				var obj=jQuery("#treeOp");
				if(obj.html()=="全部展开"){
					for(var i=0;i<funcCount;i++){
						var nihao="d"+i;
						eval(nihao).openAll();
					}
					obj.html("全部收缩");
				}else{
					for(var i=0;i<funcCount;i++){
						var nihao="d"+i;
						eval(nihao).closeAll();
					}
					obj.html("全部展开");
				}
			}
			
			function doSubmit(){
				var radios = document.getElementsByName("role.funcs.id");
				if(radios.length>0){
					for (var i=0;i<radios.length;i++){
						if(radios[i].checked){
							window.returnValue = radios[i].value;
							window.close();
						}
					}
				}
			}
		</script>
	</HEAD>
	<body onLoad="init('<s:property value="funcTree.count"/>');">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">角色功能分配</span>

			</li>
		</ul>
		<form action="rolefunc_add.action" method="post">
		<div style="font-size: 13px; margin-top: 4px;">
			<a href="javascript:opTree('<s:property value="funcTree.count"/>');" id="treeOp" class="textbg6">全部展开</a>
			<table align="center" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<th align="center" width="50%">
						角色名
					</th>
					<th align="center" width="50%">
						描敘
					</th>
				</tr>
				<tr>
					<td  align="center" width="50%">
						<s:property value="role.name" />
					</td>
					<td  align="center" width="50%">
						<s:property value="role.description" />
					</td>
				</tr>
			</table>
			<table style="width:100%;border:0px solid #fff;" cellpadding="1" cellspacing="1">
			  <tr>
				<td>
					<div><wysLib:funcTree_newVersion did="0" nodeIndex="0" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="1" nodeIndex="1" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="2" nodeIndex="2" ></wysLib:funcTree_newVersion></div>
				</td>
			  </tr>
			  <tr>
				<td>
					<div><wysLib:funcTree_newVersion did="3" nodeIndex="3" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="4" nodeIndex="4" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="5" nodeIndex="5" ></wysLib:funcTree_newVersion></div>
				</td>
			  </tr>
			  <tr>
				<td>
					<div><wysLib:funcTree_newVersion did="6" nodeIndex="6" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="7" nodeIndex="7" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="8" nodeIndex="8" ></wysLib:funcTree_newVersion></div>
				</td>
			  </tr>
			  <s:if test="funcTree.count>9">
			  <tr>
				<td>
					<div><wysLib:funcTree_newVersion did="9" nodeIndex="9" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="10" nodeIndex="10" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="11" nodeIndex="11" ></wysLib:funcTree_newVersion></div>
				</td>
			  </tr>
			  </s:if>
			   <s:if test="funcTree.count>12">
			  <tr>
				<td>
					<div><wysLib:funcTree_newVersion did="12" nodeIndex="12" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="13" nodeIndex="13" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="14" nodeIndex="14" ></wysLib:funcTree_newVersion></div>
				</td>
			  </tr>
			  </s:if>
			   <s:if test="funcTree.count>15">
			  <tr>
				<td>
					<div><wysLib:funcTree_newVersion did="15" nodeIndex="15" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="16" nodeIndex="16" ></wysLib:funcTree_newVersion></div>
				</td>
				<td>
					<div><wysLib:funcTree_newVersion did="17" nodeIndex="17" ></wysLib:funcTree_newVersion></div>
				</td>
			  </tr>
			  </s:if>
			</table>
			<s:hidden name="role.id"></s:hidden>
		</div>
		<div style="margin-top:10px;text-align:center;">
			<input type="button" value="提交" class="textbg4" onclick="doSubmit();"/>
			<input type="button" onclick="window.close();" value="关闭" class="textbg4"/>
		</div>
		</form>
	</body>
</HTML>
