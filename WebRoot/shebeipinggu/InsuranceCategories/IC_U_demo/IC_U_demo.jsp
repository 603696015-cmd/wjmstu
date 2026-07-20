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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>设备详情</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="stylesheet" type="text/css" href="shebeipinggu/InsuranceCategories/IC_U_demo/${IC.tableName}.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>    
		<script type="text/javascript" src="editor/fckeditor.js"></script> 
		<SCRIPT type="text/javascript">
			
			
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
				myload2(); 
			} 
		function myload2(){ 
				if(!document.getElementById('content')){ 
				}else{
				var oFCKeditor = new FCKeditor('content') ; 
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea(); 
				setCurTime("releasetime");
				}
			}
			
		//求积求和
		function qiuji_keyup(){
			var baoxianProduct_huiyuanjia;
			var qiujiRelateColumns_string;
			var array = new Array();
			var tablename;
			
			baoxianProduct_huiyuanjia = "<s:property value='baoxianProduct.huiyuanjia'/>";
			
			qiujiRelateColumns_string = '${qiujiRelateColumns}';
			
			array = eval("("+qiujiRelateColumns_string+")") ;
			
			tablename = "<s:property value='IC.tableName'/>";
			
			
			
			var is_zuoweiji_value;//作为积
			var is_zuoweihe_value;//作为和
			var relateQiujiColumn="";//乘积字段  数量+单价
			var danjia;//单价字段
			var ji_column_name;//作为积的字段
			var he_column_name;//作为和的字段
			var result = "";
			var result_array = new Array();
			var result_qiuhe = new Array();
			var relateQiuheColumn;//求和字段
			var he_value = 0;//求和的值
			var ji_value = 0;//求积的值
			
			
			
			$.each(array,function(i,arr){
				if(array[i].from_entity == 1){
					$("#CSS_"+array[i].columnName).val(baoxianProduct_huiyuanjia);
					
				}
				is_zuoweihe_value = array[i].is_zuoweihe;
				is_zuoweiji_value = array[i].is_zuoweiji;
				if(is_zuoweiji_value == 1){
					ji_column_name = array[i].columnName;
					relateQiujiColumn = array[i].qiujiColumnName;
					
					if(relateQiujiColumn != ""){
						result_array = relateQiujiColumn.split(",");
					}
					
					//求积
					var ji = 0;
					for(var i=0;i<result_array.length;i++){
						if(i == 0){
							ji = 1;
						}
						ji = ji * $("#CSS_"+result_array[i]).val();
					}
					
					$("#CSS_"+ji_column_name).val(ji);
					
				}
				
				
				if(is_zuoweihe_value == 1){
					
					he_column_name = array[i].columnName;
					relateQiuheColumn = array[i].qiuheColumnName;
					if(relateQiuheColumn != ""){
						if(relateQiuheColumn.indexOf(',')>0){
							result_qiuhe = relateQiuheColumn.split(",");
						}else{
							result_qiuhe[0] = relateQiuheColumn;
						}
						
					}
					
					//求和
					$.each(result_qiuhe,function(i,arr){
						he_value = he_value + new Number($("#CSS_"+result_qiuhe[i]).val());
					});
					$("#CSS_"+he_column_name).val(he_value);
				}
			});
			
			
		}
		
		function getInformationByAuto(){
				if(IC_read_auto_biaodi != ""){//设备信息自动获取
					id_biaodi = search_user_information(document.getElementById("tablename").value,"biaodi").split(":")[0];
				}
				//获取数据来源表中某行数据的id
				var id_shebei = document.getElementById("shebei_id").value;
				var tablename = document.getElementById("tablename").value;
				var IC_read_auto_biaodi = document.getElementById("IC.read_auto_biaodi").value;
				var IC_read_auto_toubaoren = document.getElementById("IC.read_auto_toubaoren").value;
				var IC_read_auto_beibaoren = document.getElementById("IC.read_auto_beibaoren").value;
				
				var id_toubaoren;
				var id_beibaoren;
				var id_biaodi;
				
				if(IC_read_auto_toubaoren != ""){//投保人信息自动获取
					id_toubaoren = search_user_information(IC_read_auto_toubaoren,"user");
				}
				if(IC_read_auto_beibaoren != ""){//投保人信息自动获取
					id_beibaoren = search_user_information(IC_read_auto_beibaoren,"user");
				}
				
				
				//将获取的数据id和表绑定
				var id = ""+IC_read_auto_toubaoren+":"+id_toubaoren+";"+IC_read_auto_beibaoren+":"+id_beibaoren+";"+IC_read_auto_biaodi+":"+id_biaodi;
				
				
				
				$.ajax({
					 type: 'POST',
					 url: "PG_getInformationByAuto.action",
					 data: {id:id,tablename:tablename},
					 async:false,//同步
					 success: function(data){
						var result = eval("("+data+")").result;
						$.each(result,function(i,n){
							$("#CSS_"+result[i].columnName).val(result[i].relateColumnValue);
						});
					 }
				});
			}
			
			function search_user_information(tablename,type){
				if(type == "user"){
					width=800;
					height=600;
					var url = "PG_searchRelateId.action?x="+Math.random()+"&tablename="+tablename;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					return rv;
				}else if(type == "biaodi"){
					width=600;
					height=500;
					var url = "PG_searchShebei.action?x="+Math.random()+"&tablename="+tablename;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						var value = rv.split(":");
						document.getElementById("shebei_id").value = value[0];
						document.getElementById("IC.read_auto_biaodi").value = value[1];
						document.getElementById("IC.read_auto_toubaoren").value = value[2];
						document.getElementById("IC.read_auto_beibaoren").value = value[3];
						document.getElementById("tablename").value = tablename;
					}
					return rv;
				}
				
			}
		</SCRIPT>		 
</HEAD>
	<BODY onLoad="myload();">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="增加险种信息" /></div>
			</li> 
		</ul>
		<s:form action="PG_IC_U_addOrAlter" method="post" name="IC_U_addinfo" theme="simple" > 
		<s:hidden name="IC.id"></s:hidden>
		<s:hidden name="IC.tableName"></s:hidden> 
		<s:hidden name="policy.id"></s:hidden>
		<s:hidden name="actionName"></s:hidden>
		<s:hidden name="IC_U_ID"></s:hidden>
		<s:hidden name="baoxianProduct.id"></s:hidden> 
		<div align="center">  
		<!-- 
			说明:
				在   内容区域开始  下面插入table 在table内排版好表格
				然后使用标签  ${view['XXX']}  <wysLib:GET iname="XXX"></wysLib:GET>
				
				${view['XXX']}  
			 	此标签用于 获取在险种处填写的 列名称
				
				<wysLib:GET iname="XXX"></wysLib:GET> 
			 	此标签用于 获取在险种处填写的 如何显示

				例： 下面是险种 对应的数据

				列名称    列类型    页面显示名称  页面显示方式   
				KS_TBRMC  VARCHAR2  投保人名称    单行文本  

				下面是我们插入的table 
				<table>
				<tr>
					<td>${view['KS_TBRMC']}:</td>
					<td><wysLib:GET iname="KS_TBRMC"></wysLib:GET></td>
				</tr>
				<table>

				那么在页面上显示如下：

				投保人名称:    可输入文本框

				注意： 请不要在  内容区域开始  ~ 内容区域结束 之外做任何修改。
		--> 
		<!-- 内容区域开始-->
			
			
		<!-- 内容区域结束 -->
						<br/> 
						<s:if test="TypeView != 3 && TypeView != 4">
						<input class="textbg6" name="submit" type="submit" value="确认添加" /> 
 						</s:if>  
		</div>
		</s:form>
	</BODY>
</HTML>
