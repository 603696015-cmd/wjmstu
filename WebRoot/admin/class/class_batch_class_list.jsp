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
		<TITLE>培训班列表</TITLE>
		<base target="_self" href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<SCRIPT type="text/javascript">
			var idandtitle = new Array();
			function queding(){
				var cks= document.getElementsByName("ids");
				var m =0;
				for(var i = 0 ; i < cks.length; i++){
					if(cks[i].checked){
						idandtitle[m]=cks[i].value;
						m++;
					}
				}
			
				window.returnValue = idandtitle;
				window.close();
			}
			function select_All(){
				var cks= document.getElementsByName("ids");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			function select_Fan(){
				var cks= document.getElementsByName("ids");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			function select_Bux(){
				var cks= document.getElementsByName("ids");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
		</SCRIPT>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="165" valign="top" id="tree_list_td">
					<wysLib:clTypeTree
						href="elclass_alllist.action?sublibs=1&cltype.id=" rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">
					<s:if test="elclasses.size==0">没有符合条件的培训班</s:if>
					<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1">
							<tr>
								<th></th>
								<th width="260" align="center">
									培训班信息
								</th>
								<th width="60" align="center">
									类型
								</th>
								<!-- <th width="90" align="center">
									创建时间
								</th>
								<th width="90" align="center">
									开始时间
								</th>
								<th width="90" align="center">
									结束时间
								</th>
								<th width="70" align="center">
									状态
								</th> -->
								<th width="70" align="center">
									人数
								</th>
							</tr>
							<tbody>
								<s:iterator value="elclasses">
									<tr>
										<td>
											<input type="checkbox" name="ids"
												value="<s:property value="id"/>" />
										</td>
										<td style="padding: 3px 0px 3px 2px;" valign="top"
											align="left">
											<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size: 15px; color: blue;"><s:property
														value="name" /> </strong>
												<!-- <br />
												<strong>类别:</strong>
												<s:property value="cltype.name" />
												<br />
												<strong>组织单位:</strong>
												<s:property value="depName" />
												<br />
												<strong>组织工钟:</strong>
												<s:property value="jingzhong" />
												<br />
												<strong>创建者:</strong>
												<s:property value="creater.realname" />
												<br /> -->
											</div>
										</td>
										<td align="center">
											<s:if test="isApplication == 1">
												<SPAN style="color: red">申请</SPAN>
											</s:if>
											<s:else>
												<SPAN style="color: gray">分配</SPAN>
											</s:else>
										</td>
										<!-- <td align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="createtime" />
										</td>
										<td align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime" />
										<td align="center">
											<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime" />
										</td>
										<td align="center" style="color: green;">
											<s:property value="statusName" />
										</td> -->
										<td align="center">
											参加:
											<s:property value="classSize" />
											<!--<s:if test="isApplication == 1">
												<br />
												<span style="color: red">计划:<s:property
														value="planNumber" /> </span>
											</s:if>-->
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</s:else>
					<wysLib:page></wysLib:page>
					<a href="javascript:select_All()" class="textbg4">全选</a>
					<a href="javascript:select_Fan()" class="textbg4">反选</a>
					<a href="javascript:select_Bux()" class="textbg4"
						style="width: 60px">全不选</a>
					<a href="javascript:queding()" class="textbg4">确定</a>
					<a href="javascript:window.close();" class="textbg4">关闭</a>
				</td>
			</tr>
		</table>
		<!-- 内容 -->
	</BODY>
</HTML>
