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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function copy(id){
				if(window.confirm("您确定要复制这条数据吗?")){
					can_op.action = "copyKnowledgeManage.action";
					can_op.submit();
				}
			}
			
			function update(id){
				can_op.action = "updateKledgeInit.action";
				can_op.submit();
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
			
			function del(id){
				if(window.confirm("您确定要删除这条数据吗?")){
					can_op.action = "deleteKledge.action";
					can_op.submit();
				}
			}
			
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
		</script>
		
<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	padding:5px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
.STYLE1 {color: #3399FF}
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz" style="padding:0px;"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看知识" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz" style="padding:0px;">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
	</table>
			<form action="question_stuffDownload.action" method="post"
				name="qstuff">
				<s:hidden name="qstuff.id" id="qsid" />
				<s:hidden name="qpstuff.id" id="qpsid" />
			</form>
			<form action="deleteKledge.action" name="can_op" method="post">
				<input type="hidden" name="kledge.id" value="<s:property value='kledge.id' />" />
				<input type="hidden" name="fromView" value="1" /><!-- 来自查看页面的 -->
				<s:hidden name="listType"></s:hidden>
			</form>
		
			<table width="100%" cellpadding="1" align="center" cellspacing="1"
				bgcolor="#ECEDEB">
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  知识名称：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:property value="kledge.name" />
						</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<s:if test="kledge.ct_copy.can_op==true">
							<a href="javascript:copy(<s:property value='kledge.id' />);">复制</a>						
						</s:if>
						<s:if test="kledge.ct_update.can_op==true">
							<a href="javascript:update(<s:property value='kledge.id' />);">修改</a>						
						</s:if>
						<s:if test="kledge.ct_download.can_op==true">
							<a href="javascript:download('<s:property value='kledge.fujian' />');">下载</a>				
						</s:if>
						<s:if test="kledge.ct_delete.can_op==true">
							<a href="javascript:del(<s:property value='kledge.id' />);">删除</a>						
						</s:if>					
					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  知识类别：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:property value="kledge.klTree.name" />
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  有效期：					</span></td>
					<td align="left" bgcolor="#FFFFFF">
						开始时间：<s:date name="kledge.begintime" format="yyyy-MM-dd HH:mm"></s:date>
						&nbsp;&nbsp;&nbsp;&nbsp;
						结束时间：<s:date name="kledge.endtime" format="yyyy-MM-dd HH:mm"></s:date>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  制定部门：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:property value="kledge.depname"/>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  制作人：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:property value="kledge.zhizuoren"/>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  发布人：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:property value="kledge.fabuUser.realname"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<s:property value="kledge.fabuUser.department.name"/>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  发布时间：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:date name="kledge.fabutime" format="yyyy-MM-dd HH:mm"></s:date>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  修改人：					</span></td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:property value="kledge.xiugaiUser.realname"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<s:property value="kledge.xiugaiUser.department.name"/>
						</label>					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  附件：					</span></td>
					<td bgcolor="#FFFFFF">
						<s:if test="kledge.fujian!=null&&kledge.fujian!=''">
							<a href="javascript:preview('<s:property value='kledge.fujian' />');" >预览</a>		
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:if test="kledge.ct_download.can_op==true">
							<a href="javascript:download('<s:property value='kledge.fujian' />');">下载</a>				
							</s:if>				
						</s:if>	
					</td>
				</tr>
				<tr>
					<td width="90" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1">
					  知识简介：					</span></td>
					<td bgcolor="#FFFFFF" style="font-size:14px;line-height:25px;">
						<label>
							${kledge.jianjie}
						</label>					
					</td>
				</tr>
	</table>
	
	</body>
</HTML>
