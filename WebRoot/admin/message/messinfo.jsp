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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system01.css" />
		<link rel="stylesheet" type="text/css" href="css/manage01.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
			function preview(obj) {
				width=900;
				height=600;
	  	 		var sFeature = "dialogHeight:"+height+"px;dialogWidth:"+width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
	  	 		//获取stuffid
	  	 		//elstuffs/1193/1204.doc
	  	 		var indexStart = obj.lastIndexOf("/");
	  	 		var indexEnd = obj.lastIndexOf(".");
	  	 		var qstuffid = obj.substring(indexStart+1,indexEnd);
				window.showModalDialog("question_stuffPreview.action?qstuff.id="+qstuffid+"&x="+Math.random(),null,sFeature);
			}
			
			function download(obj){
				if(obj==undefined||obj==""){
					alert("暂无添加附件，不能下载!");
					return ;
				}
				//elstuffs/1193/1204.doc
				var last = obj.lastIndexOf("/");
				var last_ = obj.indexOf("/");
				var folderid = obj.substring(last_+1,last);
				
				var indexStart = obj.lastIndexOf("/");
	  	 		var indexEnd = obj.lastIndexOf(".");
	  	 		var qstuffid = obj.substring(indexStart+1,indexEnd);
	  	 		
	  	 		document.getElementById("qsid").value=qstuffid;
				document.getElementById("qpsid").value=folderid;
				qstuff.action="question_stuffDownload.action";
				qstuff.submit();
			}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="消息内容页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">发件箱 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="mess_Rec.action?pN=0&pS=20">收件箱 </a>
			
			<li></li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		<form action="question_stuffDownload.action" method="post"
			name="qstuff">
			<s:hidden name="qstuff.id" id="qsid" />
			<s:hidden name="qpstuff.id" id="qpsid" />
		</form>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center; color: #039;">
			<table width="100%" align="center" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong>
					  发件人
					</strong></td>
					<td height="20" align="left" bgcolor="#F8FCFE">
						<s:property value="mess.mess_from.realname" />
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong>
					  收件人
					</strong></td>
					<td height="20" align="left" bgcolor="#F8FCFE">
						<s:property value="mess.mess_to.realname" />
					</td>
				</tr>
				<tr>
			  <td width="120" height="30" align="center" bgcolor="#F8FCFE"  ><strong>
					  标　题
					</strong></td>
					<td height="20" align="left" bgcolor="#F8FCFE">
						<s:property value="mess.mess_title" />
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong>
					  附　件
					</strong></td>
					<td height="20" align="left" bgcolor="#F8FCFE">
						<s:if test="mess.stuffs.size()!=0">
						<s:iterator value="mess.stuffs">
							<s:if test="stuff_path!=null&&stuff_path!=''">
								<a href="javascript:preview('<s:property value='stuff_path' />');" >预览</a>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:download('<s:property value='stuff_path' />');">下载</a>
								<br>	
							</s:if>
						</s:iterator>
						</s:if>
						<s:else>
							暂无附件
						</s:else>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong>
					  时　间
					</strong></td>
					<td height="20" align="left" bgcolor="#F8FCFE">
						<s:date name="mess.mess_time" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong>
					  内　容
					</strong></td>
					<td height="20" align="left" bgcolor="#F8FCFE">
						<%-- <s:property value="mess.mess_content" /> --%>
						${mess.mess_content }
						<s:if test="mess.auditType==3">
							<a href="forumView.action?forum.id=<s:property value="mess.forumid"/>" target="_blank">点击查看</a>
						</s:if>
					</td>
				</tr>
				<s:if test="deleteType==1">
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						</td>
						<td bgcolor="#F8FCFE">
							<a class="textbg4"
								href="mess_revertInit.action?mess.mess_id=<s:property value="mess.mess_id" />">回复</a>&nbsp;&nbsp;
					    <input type="button" style="width:100px;" value="返回收件箱" onClick="document.location='mess_Rec.action?pN=0&pS=10'" class="textbg4"/>
							<s:if test="mess.auditType==1">
								<span style="margin-left: 200px;"><a style="width: 80px"
									class="textbg4"
									href="javascript:displayList(1,'<s:property value="mess.auditName" />');">考场查看</a>
								</span>
							</s:if>
							<s:if test="mess.auditType==2">
								<span style="margin-left: 200px;"><a style="width: 100px"
									class="textbg4"
									href="javascript:displayList(2,'<s:property value="mess.auditName" />');">培训班查看</a>
								</span>
							</s:if>
						</td>
					</tr>
				</s:if>
			</table>
</div>
		<s:form action="examroom_alllist.action" method="post"
			name="toQueryPage_1">
	    <s:hidden name="examRoom.title" id="examTitle" />
			<s:hidden name="examRoom.classid" value="-2" />
			<s:hidden name="examRoom.valid" value="-1" />
			<s:hidden name="sublibs" value="1" />
		</s:form>
		<s:form action="elclass_alllist" method="post" name="toQueryPage_2">
			<s:hidden name="elClass.name" id="classTitle" />
			<s:hidden name="elClass.status" value="-1" />
			<s:hidden name="sublibs" value="1" />
		</s:form>
		<script type="text/javascript">
			function displayList(auditType,auditName){
				if(auditType==1){
					document.getElementById("examTitle").value=auditName;
					toQueryPage_1.action="examroom_alllist.action";
					toQueryPage_1.submit();
				}else if(auditType==2){
					//alert("等目标页面的组合查询做完在处理！");
					document.getElementById("classTitle").value=auditName;
					toQueryPage_2.action="elclass_alllist.action";
					toQueryPage_2.submit();
				}
			}
		</script>
		<!-- 内容 -->
	</BODY>