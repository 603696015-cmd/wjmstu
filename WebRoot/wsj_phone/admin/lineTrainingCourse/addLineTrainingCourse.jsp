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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<style type="text/css">
		.error{color: red;}
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<SCRIPT type="text/javascript">
			function setUrl(obj) {
				width=800;
				height=400;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv = window.showModalDialog("question_stuffList.action?x="+Math.random(),
				 window,sFeature);
				
				 if(null==rv){
				 	return ;
				 }
				 document.getElementById(obj).value=rv;
				 
				 var stuff_id = 0;
				 stuff_id = rv.substring(rv.lastIndexOf('/')+1,rv.lastIndexOf('.'));
				 document.getElementById("lineTrainingCourse.stuff_id").value = stuff_id;
				 alert(stuff_id);
			}
			function myload(){
				var oFCKeditor = new FCKeditor("jianjie") ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = "100%";
				oFCKeditor.ReplaceTextarea();
			}
			
			function searchtypeInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("searchtypeInit.action?x="+Math.random(),null,sFeature);
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("-=wys=-");
					 document.getElementById("suoshulanmu_id").value=bh[2];
					 document.getElementById("suoshulanmu_name").value=bh[1];
				 }
			}
		</SCRIPT>

	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加线下培训" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<form action="addLineTrainingCourse.action" method="post" enctype="multipart/form-data">
			<s:hidden name="lineTrainingCourse.train_type_id" id="suoshulanmu_id" />
			<input type="hidden" name="lineTrainingCourse.stuff_id" id="lineTrainingCourse.stuff_id"/>
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
							 <s:textfield theme="simple" theme="simple" name="lineTrainingCourse.name"  id="name" />
						</td>
						<td  height="40" align="center" width="15%">
							<strong>计划招收人数</strong>
						</td>
						<td  height="40"  width="35%">
								<s:textfield theme="simple" name="lineTrainingCourse.person_number_plan" id="person_number_plan" />
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>发布时间</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.createtime" id="createtime" readonly="true" onclick="setday(this)"/>
								</label>
						</td>
						<td  height="40" align="center" >
							<strong>地点</strong>
						</td>
						<td  height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.place" id="place" />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>培训开始时间</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.train_begintime" id="train_begintime"  readonly="true" onclick="setday(this)"/>
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>培训结束时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
							
								<s:textfield theme="simple" name="lineTrainingCourse.train_endtime" id="train_endtime"  readonly="true"   onclick="setday(this)"/>
							</label>
							
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>报名开始时间</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.sign_begintime" id="sign_begintime"  readonly="true" onclick="setday(this)"/>
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>报名结束时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
							
								<s:textfield theme="simple" name="lineTrainingCourse.sign_endtime" id="sign_endtime"  readonly="true"   onclick="setday(this)"/>
							</label>
							
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>关键字</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.key" id="key" />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>报名表下载</strong>
						</td>
						<td height="40" align="left" >
							<s:textfield name="lineTrainingCourse.sign_table_name" id="sign_table_name" size="20" theme="simple" />
							<a style="color: black;font-weight: bolder;" href="javascript:setUrl('sign_table_name');" class="textbg">浏览我的资源</a>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>收费价格</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.fee_price" id="fee_price" />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>培训类别</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.ptype.name" size="20" id="suoshulanmu_name" readonly="true" />
				 				<a href="#" onClick="searchtypeInit();return false;">点此进行选择</a>
								<!-- <SELECT  style="WIDTH: 110px" name="lineTrainingCourse.train_type_id" 
							      onchange="this.value=this.options[this.selectedIndex].value;">
							        <OPTION value="" selected>选择培训类别</OPTION>
							        <s:iterator value="trainTypes">
							        	<option value="<s:property value="id"/>">
											<s:property value="name"/> 
										</option>
							        </s:iterator>
							    </SELECT> -->
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>联系方式</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.contact" id="contact" />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>联系人</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.contact_name" id="contact_name" />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>对应图片</strong>
						</td>
						<td height="40" align="left" >
								<s:textfield name="lineTrainingCourse.picture" id="picture" size="20" theme="simple" />
								<a style="color: black;font-weight: bolder;" href="javascript:setUrl('picture');" class="textbg">浏览我的资源</a>
						</td>
						<td  height="40" align="center" >
							<strong>学分</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="lineTrainingCourse.credit" id="credit" />
							</label>
						</td>
					</tr>	

				</table>
				
				简介
				<s:textarea name="lineTrainingCourse.jianjie" id="jianjie"
 					cssStyle="width:700px;height:150px;visibility:hidden;" />
				<s:submit value="确认添加" name="确认添加" theme="simple" />
			</div>

		</form>
		<!-- 内容 -->
	
	</body>
</HTML>
