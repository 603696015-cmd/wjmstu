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
		<TITLE>选择数据表</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				searchShebei.submit();
			}
			
			function doSubmit(){
				var index = $("input[type='radio']:checked").attr("id");
				value = $("#id_"+index).html();
				if(value == null){
					alert("请选择一条记录!!!");
					return ;
				}
				window.returnValue = value+":<s:property value="IC.read_auto_biaodi"/>:<s:property value="IC.read_auto_toubaoren"/>:<s:property value="IC.read_auto_beibaoren"/>";
				window.close();
			}
		</script>
	</HEAD>
	<body>
		<div>
				<form action="searchShebei.action" method="post" name="searchShebei">
					<s:hidden name="pN" id="pageNow" />
					<s:hidden name="pS" /> 
				</form>
				<center style="color:red">
					选择<s:property value='tablename'/>表具体信息
				</center>
				<table width="100%" align="center" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<s:iterator value="list_designe"  id="map" status="status"> 
						<tr>
							  <s:if test="#status.index==0">
							  		<th></th>
							  	  <s:iterator value="map" id="column" >    
								   		<th><s:property value="key"/></th>
								  </s:iterator> 
							  </s:if>
						</tr>
					</s:iterator>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="list_designe" status="status" id="map"> 
							<tr>
								  <td><input type="radio" name="radio" id="<s:property value='#status.index+1'/>"/></td>
							      <s:iterator value="map" id="column">  
								   		<td>
								   			<center>
								   				<s:if test="key == 'id'">
								   					<p style="color:red;" id="id_<s:property value='#status.index+1'/>"><s:property value="value"/></p>
								   				</s:if>
								   				<s:else>
								   					<s:property value="value"/>
								   				</s:else>
								   			</center>
								   		</td>
								  </s:iterator> 
							</tr>
						</s:iterator> 
					</tbody>
			  	</table>
				<center>
					<s:if test="count != 0">
						<wysLib:page></wysLib:page>
					</s:if>
				</center>
				<center>
					<input type="button"  value="确&nbsp;认" onclick="doSubmit();" class="textbg4"/>
				</center>
		</div>
		
	
	</body>
</HTML>
