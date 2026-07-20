<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE></TITLE>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<style type="text/css">
		.error{color: red;}
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<SCRIPT type="text/javascript">
			function myload(){
				var oFCKeditor = new FCKeditor("jianjie") ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = "100%";
				oFCKeditor.ReplaceTextarea();
			}
			
			function fileDownload(id,qpid){
				//alert(id);
				document.getElementById("qsid").value=id;
				document.getElementById("qpsid").value=qpid;
				qstuff.submit();
			}
		</SCRIPT>

	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="显示线下培训" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

			<div style="margin-top: 0px;">
				<table id="info12" width="100%" cellpadding="1" cellspacing="1">
					<caption>
						基本信息
					</caption>
					<tr>
						<td  height="40" align="center" width="15%">
							<strong>培训名称</strong>
						</td>
						<td height="40"  width="35%" align="left" >
							 <s:property value="lineTrainingCourse.name"/>
						</td>
						<td  height="40" align="center" width="15%">
							<strong>计划招收人数</strong>
						</td>
						<td  height="40"  width="35%">
							<s:property value="lineTrainingCourse.person_number_plan"/>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>发布时间</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.createtime"/>
						</td>
						<td  height="40" align="center" >
							<strong>地点</strong>
						</td>
						<td  height="40" align="left" >
							<s:property value="lineTrainingCourse.place"/>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>培训开始时间</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.train_begintime"/>
						</td>
						<td  height="40" align="center" >
							<strong>培训结束时间</strong>
						</td>
						<td  height="40" align="left" >
							<s:property value="lineTrainingCourse.train_endtime"/>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>报名开始时间</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.sign_begintime"/>
						</td>
						<td  height="40" align="center" >
							<strong>报名结束时间</strong>
						</td>
						<td  height="40" align="left" >
							<s:property value="lineTrainingCourse.sign_endtime"/>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>关键字</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.key"/>
						</td>
						<td  height="40" align="center" >
							<strong>报名表下载</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.sign_table_name"/>
							<s:if test="lineTrainingCourse.sign_table_name.length() > 0">
					 			<strong><a style="color:red" href="javascript:fileDownload('<s:property value="lineTrainingCourse.stuff_id"/>','<s:property value="lineTrainingCourse.stuff.parent.id"/>');">下载</a></strong> 
							</s:if>
							
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>收费价格</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.fee_price"/>
						</td>
						<td  height="40" align="center" >
							<strong>培训类别</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.trainType.name"/>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>联系方式</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.contact"/>
						</td>
						<td  height="40" align="center" >
							<strong>联系人</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.contact_name"/>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>对应图片</strong>
						</td>
						<td height="40" align="left" >
							<s:if test="lineTrainingCourse.picture != null">															
								<img src="<s:property value="lineTrainingCourse.picture_"/>" width="100" height="80" />
							</s:if><s:else>
								<img src="" width="100" height="80" /> 
							</s:else> 
						</td>
						<td  height="40" align="center" >
							<strong>学分</strong>
						</td>
						<td height="40" align="left" >
							<s:property value="lineTrainingCourse.credit"/>
						</td>
					</tr>	
					
				</table>
				
				<table  width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td>简介</td>
						<td>${ lineTrainingCourse.jianjie_}</td>
					</tr>
				</table>
				
				<form action="question_stuffDownload.action" method="post"
					name="qstuff">
					<s:hidden name="qstuff.id" id="qsid" />
					<s:hidden name="qpstuff.id" id="qpsid" />
				</form>
			</div>

		<!-- 内容 -->
	
	</body>
</HTML>
