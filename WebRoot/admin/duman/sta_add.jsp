<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>五矿发展员工职业发展系统--管理端--</title>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#stname").val()==''){
					alert("请填写岗位名称！");
					$("#stname").focus();
					return false;
				}
				var bh=document.getElementById("stBh");
				if(bh.value==""){
					alert("请填写岗位编号！");
					$("#stBh").focus();
					return false;
				}
				var ts=/^[\d]{1,}$/;
				if(!ts.test(bh.value)){
					alert("岗位编号只能是数字！");
					$("#stBh").focus();
					return false;
				}
				var stId=$("input[name='station.parent.id']:checked").val();
				if(stId==undefined){
					alert("请选择上级岗位！");
					return false;
				}
				return window.confirm("确定信息填写无误？");
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>

		<!-- 页面 -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
			
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="sta_add" method="post" theme="simple"
			name="station_info" id="station_info"
			onsubmit="return _onsubmit();">
			<table style="margin-top: 3px;" border="0" align="left"
				cellpadding="1" cellspacing="1" width="600px" bgcolor="#EBEBEB">
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>岗位：
					</td>
					<td>
						<label>
							<s:textfield id="stname" name="station.name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="80" align="right">
						详细说明：
					</td>
					<td>
						<label>
							<s:textarea name="station.description" cols="45" rows="5" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						<span class="neededitem">*</span>上级岗位：
					</td>
					<td>
						<label>
						<wysLib:st_list_aj rootAble="true" iname="station.parent.id" itype="ra"></wysLib:st_list_aj>
							<script type="text/javascript">
							w0.setValues([new ST(<s:property value="station.id"/>,<s:property value="station.lid"/>,<s:property value="station.rid"/>)]);
						</script></label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" style="display:none;" >
						联系电话：
					</td>
					<td style="display:none;" >
						<label>
							<s:textfield name="station.phone" size="60" />
						</label>
					</td>
				</tr>
				
				<tr>
					<td height="30" align="right" style="display:none;" >
						地 址：
					</td>
					<td style="display:none;" >
						<label>
							<s:textfield name="station.address" size="60" />
							<s:hidden name="station.manager.id" value="0" />

						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" style="display:none;" >
						邮政编码：
					</td>
					<td style="display:none;">
						<label>
							<s:textfield name="station.postalcode" size="6" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" style="display:none;" >
						传 真：
					</td>
					<td style="display:none;" >
						<label>
							<s:textfield name="station.fax" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" style="display:none;" >
						电子邮箱：
					</td>
					<td style="display:none;" >
						<label>
							<s:textfield name="station.email" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" >
						<span class="neededitem">*</span>编号：
					</td>
					<td>
						<label>
							<s:textfield name="station.bh" id="stBh" />
							<span style="color: red;"><s:property value="elmessage" />
							</span>
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" style="display:none;" >
						是否二级页面：
					</td>
					<td style="display:none;" >
						<label>
							<s:radio list="#{'0':'否','1':'是'}" name="station.issp" value="0"/>
							<span style="color: red;"><s:property value="elmessage" />
							</span>
						</label>
					</td>
				</tr>
				<tr>
					<td height="50" align="center">
					</td>
					<td>
						<input name="submit" type="submit" class="textbg4" value="添加" />
							<input type="button" class="textbg4" style="width:100px;" onclick="document.location='station_list.action'" value="返回列表" />
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>