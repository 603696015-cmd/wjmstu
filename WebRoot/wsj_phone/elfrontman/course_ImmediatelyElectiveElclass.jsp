<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>培训管理信息系统--管理端--</TITLE>
		<base target="_top" href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
			.tdStyle{
				color:red;
			}
		</style>
	</HEAD>
	<body> 
		<s:if test="classKs_pass.size != 0">
			该课程在
			<s:iterator value="classKs_pass">
				【<span style=" color:red"><s:property value="name"/></span> 培训班】
			</s:iterator>
			已拿过学分,不能再次学习该课程。
		</s:if> 
		<s:else>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程库课程加入我的培训班列表" /></div>
			</li> 
		</ul>
		<s:form action="course_ImmediatelyElectiveElclass.action" name="myclist" theme="simple">
			<s:hidden name="course.id" />
			<s:hidden name="PageStatus" />
			<s:hidden name="pN" id = "pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
		</s:form>
		<div style="text-align:center;margin-top:20px;">  
		<table width="100%" align="center" cellpadding="1" cellspacing="1" bgcolor="#ECEDEB">
			<tr>
				<th width="6" align="center" bgcolor="#FFFFFF">
					 				</th>
				<th width="200" align="center" bgcolor="#FFFFFF">
					培训班名称				</th>
				<th width="60" align="center" bgcolor="#FFFFFF">
					创建者				</th>
				<th align="center" bgcolor="#FFFFFF">
					所属部门				</th>
				<th width="80" align="center" bgcolor="#FFFFFF">
					学员人数				</th>
				<th width="120" align="center" bgcolor="#FFFFFF">
					开始时间				</th>
				<th width="120" align="center" bgcolor="#FFFFFF">
					结束时间				</th>
				<th width="70" align="center" bgcolor="#FFFFFF">
					状态				</th>
				<s:if test="PageStatus==1">
					<th width="60" align="center" bgcolor="#FFFFFF">详情</th>
				</s:if>
			</tr> 
			<s:if test="elclasses.size==0">您还没有 “自主培训班” 的这中可加入课程的班级类型。！</s:if>
			<s:else> 
			<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="elclasses">
				<tr>
					<td align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<s:if test="isExists == 1">
							<span style="color:red">已存在该课程</span>
						</s:if><s:else>
						 	<input type="radio" name="classid" value="<s:property value="id"/>">
						</s:else>
					</td>
					<td align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<s:property value="name"/>  
					</td>
					<td align="center" bgcolor="#FFFFFF">
						<s:property value="creater.realname" />
					</td>
					<td align="center" bgcolor="#FFFFFF"> 
						<s:property value="creater.department.name" />
					</td>
					<td align="center" bgcolor="#FFFFFF" >
						<s:property value="classSize"/>	  
					</td>
					<td align="center" bgcolor="#FFFFFF">
						<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime"/>
					</td>
					<td align="center" bgcolor="#FFFFFF">
						<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime"/>
				    </td>
					<td align="center" bgcolor="#FFFFFF" style="color:green;">
						<s:property value="statusName"/>	  
					</td>
					<s:if test="PageStatus==1">
					<td align="center" bgcolor="#FFFFFF"> 
						<a target="_blank" href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=-2&PageStatusint=-2" class=textbg4>查看</a>	
					</td>
					</s:if>
				</tr>
				</s:iterator>
			</tbody>
			</s:else> 
	</table> 
	</div>
			 
	<script>    
		function page(i) {
			document.getElementById("pageNow").value=i;
			myclist.submit();
		}
		function pageReturn(){ 
			var classids= document.getElementsByName("classid");  
			var classid;
			for(var i=0;i<classids.length;i++)
		    {
		    	if(classids.item(i).checked){
		        	classid=classids.item(i).getAttribute("value");  
			 	 	break;
			 	}else{
			 		continue;
			  	}
			} 
			if(classid != 0){
				window.returnValue = classid; 
				window.close(); 
			}else{ 
				alert("未选择培训班");
			}
		}
	</script>
			<div style="text-align:center;"><wysLib:page></wysLib:page></div>
			<div style="text-align:right;margin-right:50px;">
				<a style="cursor:pointer;" href="javascript:pageReturn();" class="textbg2">确认</a> 
			</div> 
		</s:else>
	
	</body>
</HTML>