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
		<base target="_self" href="<%=basePath%>" >
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style> 
		<script type="text/javascript">
			var arr = new Array();      
			function doSubmit(){    
				var x =0;
				var baseid = '';    
				if(document.myForm.basetj.value != '' && !document.myForm.basetj.length){
				//在baseDatatList只有一个值的时候。document.myForm.basetj是没有长度的， 只有value
					if(document.myForm.basetj.checked){
						arr.push(document.myForm.basetj.value);
						baseid = document.myForm.basetj.alt;
					} 
				}else{
					for(var i = 0 ; i < document.myForm.basetj.length ; i++) 
					{ 
						if(document.myForm.basetj[i].checked){ 
							arr.push(document.myForm.basetj[i].value);   
							if(x == 0){  
								baseid = document.myForm.basetj[i].alt;
							}else{ 
							  	baseid = baseid +"-"+ document.myForm.basetj[i].alt ;  	
							} 
						    x++; 
						}
					}      
				}
				var typeid = document.getElementById("typeid").value; 
				arr.push(baseid+"");
				arr.push(typeid);    
				window.returnValue = arr; 
				window.close(); 
				//setTimeout(window.close(),2000);
			}
			function doSubmit_(){
				var strIds="";
				var strNames="";
				var array=document.getElementsByName("basetj");
				for(var i=0;i<array.length;i++){
					//alert(array[i].value);//名称
					//alert(array[i].alt);//id
					if(i==0){
						strIds+=array[i].alt;
						strNames+=array[i].value;
					}else{
						strIds+=","+array[i].alt;
						strNames+=","+array[i].value;
					}
				}
				window.returnValue = strIds+"-=nihao=-"+strNames;
				window.close();
			}
		</script>
	</HEAD>
	<body>
		<div style="margin-left: 20px;">
			<s:form action="optionDep.action" method="post" name="myForm" theme="simple" onsubmit="doSubmit();"> 
					 <s:iterator value="baseDatatList">
					 	<input type="checkbox" name="basetj" value="<s:property value="basevalue"/>" <s:if test="selected == 1">checked="true" </s:if> alt="<s:property value="id"/>"><s:property value="basevalue"/></input>
					 </s:iterator> 
					 <s:hidden id="typeid" name="baseDatatList[0].typeid"></s:hidden>
					<input type="submit" style="margin-left:260px" value="确&nbsp;&nbsp;认"  />
			</s:form> 
		</div>
	</body>
</HTML>
