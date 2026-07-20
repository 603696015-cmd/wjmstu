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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />   
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function toEroom(id,mrrid){
				document.location.href="quizpaperinit.action?myroom.examroom.id="+id+"&myRoomRecord.id="+mrrid+"&iscommon=${myroom.examroom.iscommon}";
			}
			
			function refresh2(){
				document.location.href= document.location.href;
			}
			function addRecord(roomid){
				var recordSize=<s:property value="myroom.myRoomRecord.size" />;
				var examcount=<s:property value="myroom.examroom.examcount" />;
				if(recordSize>=examcount){
					alert("可考次数已满，不可继续添加！");
					return;
				}
				document.location.href="study_room_record_add.action?myroom.examroom.id="+roomid;
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
			<span style="font-weight: bold;">我的考场信息</span></li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">

			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					考场信息
				</caption>
				<tr>
					<th height="22" width="200" align="center" >
						考场标题
					</th>
					<td align="center" >
						<s:property value="myroom.examroom.title" />
					</td>
				</tr>
				<!--<tr>
					<th height="22" align="center" >
						我的状态
					</th>
					<td align="center" >
						<s:property value="myroom.statusName" /> 
					</td>
				</tr>-->
				<tr>
					<th height="22" align="center" >
						开始时间
					</th>
					<td align="center" >
						<s:date name="myroom.examroom.begintime"
							format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>

					<th height="22" align="center" >
						结束时间
					</th>
					<td align="center" >
						<s:date name="myroom.examroom.endtime"
							format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<th height="22" align="center" >
						可考次数
					</th>
					<td align="center" >
						<s:property value="myroom.examroom.examcount" />
					</td>
				</tr>
				<tr>
					<th height="22" align="center" >
						剩余次数
					</th>
					<td align="center" >
						<s:property value="myroom.examroom.examcount-myroom.myRoomRecord.size" />
					</td>
				</tr>
			</table>
			<div style="text-align:right;margin-right:20px;">
				<s:if test="myroom.examroom.examcount-myroom.myRoomRecord.size==0"><span style="color:red;">没有剩余次数</span></s:if>
				<s:else>
					<a href="javascript:addRecord('<s:property value="myroom.examroom.id" />');" class="textbg5">添加记录</a>
				</s:else>
			</div>
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					记录信息
				</caption>
				<tr>
					<th height="30" align="center" >序号</th>
					<th height="30" align="center" >考场标题</th>
					<th height="30" align="center" >
						成绩
					</th>
					<th height="30" align="center" >
						是否通过
					</th>
					<th height="30" align="center" >
						状态
					</th>
					<th width="200" height="30" align="center" ></th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<s:iterator value="myroom.myRoomRecord" status="statu">
					<tr>
						<td height="30" align="center" >
							<s:property value="#statu.count" />
						</td>
						<td height="30" align="center">
							<s:property value="myroom.examroom.title" />
						</td>
						<td height="30" align="center" >
							<s:property value="myScore" />
						</td>
						<td height="30" align="center" >
							<s:if test="ispassed==1">
								是
							</s:if>
							<s:else>否</s:else>
						</td>
						<td height="30" align="center" >
							<s:property value="status_" />
						</td>
						<td width="200" height="30" align="center">
							<a href="javascript:toEroom('<s:property value="examRoom.id"/>','<s:property value="id"/>');" class="textbg5">
								<s:if test="status==0">
									进入考试
								</s:if>
								<s:else>
									查看详情
								</s:else>
							</a>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
