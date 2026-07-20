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
		<TITLE></TITLE>
		<base href="<%=basePath%>" target="_self">
		<link rel="stylesheet" type="text/css" href="css/system3.css" />
		<link rel="stylesheet" type="text/css" href="css/manage3.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css">
			table td{
				vertical-align:top;
			}
		</style>
		<script type="text/javascript" src="js/tree/dtreerole.js"></script>
		<script type="text/javascript">
			function init(funcCount){
				for(var i=0;i<funcCount;i++){
					var nihao="d"+i;
					eval(nihao).openAll();
				}
				//alert(funcCount);
			}
			
			
			function doSubmit2(){				
				document.myForm2.submit();
				window.returnValue = "nihao";
				window.close();
			}  
			
			
		</script>
	</HEAD>
	<body onLoad="init('<s:property value="funcTree.count"/>');">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">角色功能分配</span>

			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="font-size: 13px; margin-top: 4px;">
			<form id="myForm2" name="myForm2" action="userRole_Select.action" method="post">
				<!--<input type="submit" value="提交" class="textbg4"/>-->
				<input type="button" style="margin-left: 260px" value="提交" onclick="doSubmit2();" class="textbg4"/>
				<s:hidden name="elUser.id" />
				<input type="button" onclick="window.close();" value="关闭" class="textbg4" />
				<table align="center" cellpadding="1" cellspacing="1" width="100%">
					<tr>
						<th>
							角色名
						</th>
						<th>
							描敘
						</th>
					</tr>
					<tr>
						<td height="20" align="center">
							<s:property value="role.name" />
						</td>
						<td height="20" align="center">
							<s:property value="role.description" />
						</td>
					</tr>
				</table>
				<table style="width: 100%; border: 0px solid #fff;" cellpadding="1"
					cellspacing="1">
					<tr>
						<td>
							<div>
								<wysLib:userFuncTree did="0" nodeIndex="0"></wysLib:userFuncTree>
							</div>
						</td>
						<td>
							<div>
								<wysLib:userFuncTree did="1" nodeIndex="1"></wysLib:userFuncTree>
							</div>
						</td>
						<td>
							<div>
								<wysLib:userFuncTree did="2" nodeIndex="2"></wysLib:userFuncTree>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div>
								<wysLib:userFuncTree did="3" nodeIndex="3"></wysLib:userFuncTree>
							</div>
						</td>
						<td>
							<div>
								<wysLib:userFuncTree did="4" nodeIndex="4"></wysLib:userFuncTree>
							</div>
						</td>
						<td>
							<div>
								<wysLib:userFuncTree did="5" nodeIndex="5"></wysLib:userFuncTree>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div>
								<wysLib:userFuncTree did="6" nodeIndex="6"></wysLib:userFuncTree>
							</div>
						</td>
						<td>
							<div>
								<wysLib:userFuncTree did="7" nodeIndex="7"></wysLib:userFuncTree>
							</div>
						</td>
						<td>
							<div>
								<wysLib:userFuncTree did="8" nodeIndex="8"></wysLib:userFuncTree>
							</div>
						</td>
					</tr>
					<s:if test="funcTree.count>9">
						<tr>
							<td>
								<div>
									<wysLib:userFuncTree did="9" nodeIndex="9"></wysLib:userFuncTree>
								</div>
							</td>
							<td>
								<div>
									<wysLib:userFuncTree did="10" nodeIndex="10"></wysLib:userFuncTree>
								</div>
							</td>
							<td>
								<div>
									<wysLib:userFuncTree did="11" nodeIndex="11"></wysLib:userFuncTree>
								</div>
							</td>
						</tr>
					</s:if>
					<s:if test="funcTree.count>12">
						<tr>
							<td>
								<div>
									<wysLib:userFuncTree did="12" nodeIndex="12"></wysLib:userFuncTree>
								</div>
							</td>
							<td>
								<div>
									<wysLib:userFuncTree did="13" nodeIndex="13"></wysLib:userFuncTree>
								</div>
							</td>
							<td>
								<div>
									<wysLib:userFuncTree did="14" nodeIndex="14"></wysLib:userFuncTree>
								</div>
							</td>
						</tr>
					</s:if>
					<s:if test="funcTree.count>15">
						<tr>
							<td>
								<div>
									<wysLib:userFuncTree did="15" nodeIndex="15"></wysLib:userFuncTree>
								</div>
							</td>
							<td>
								<div>
									<wysLib:userFuncTree did="16" nodeIndex="16"></wysLib:userFuncTree>
								</div>
							</td>
							<td>
								<div>
									<wysLib:userFuncTree did="17" nodeIndex="17"></wysLib:userFuncTree>
								</div>
							</td>
						</tr>
					</s:if>
				</table>
				<s:hidden name="role.id"></s:hidden>
			</form>
		</div>
	
	</body>
</HTML>

