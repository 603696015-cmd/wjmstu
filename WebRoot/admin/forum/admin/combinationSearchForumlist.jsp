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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="交流文章列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的帖子</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="combinationSearchforum.action" name="flbform" method="post">
		<div style="width: 100%; text-align: center; margin-top: 0px;">
			<s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			<s:hidden name="forum.begintime"/>
			<s:hidden name="forum.endtime"/>
			<s:hidden name="forum.title" />
			<input  type="hidden" value="${fbtid }" name="fbtid"/>
			<s:hidden name="forum.creater.realname"/>
			<s:hidden name="forum.creater.username" />
			<s:hidden name="optype" id="xx"/>
			<s:hidden name="forum.id" id="forum_id"></s:hidden>
			<!--<input type="text" name="forum.title" value="<s:property value="forum.title"/>">
			<input type="button" onclick="fsearch();" value="搜索">
		--></div>
		<s:if test="forums.size==0">
			<div style="width: 100%; text-align: center; margin-top: 4px;">
				您还没有发布交流文章
			</div>
		</s:if>
		<s:else>
			<table width="100%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<th>
						名称
					</th>
					<th>
						版块
					</th>
					<th>
						发布时间
					</th>
					<th>
						修改时间
					</th>
					<th>
						回帖数
					</th>
					<th>
						浏览数
					</th>
						<th>
					</th>
				</tr>
				<s:iterator value="forums">
					<tr>
						<td height="30" align="left" style="padding-left:8px;color:blue;">
							[
							  <s:property value="hotName"/>]<s:property value="title" />					  </td>
						<td align="center" >
							<s:property value="fblock.title" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<s:date name="modifytime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<s:property value="receipttime" />
						</td>
						<td align="center" >
							<s:property value="readtime" />
						</td>
						<td align="center" >
						<a href="forum_alterInit.action?forum.id=<s:property value="id"/>" class="textbg4">修改</a>
						<a class="textbg4" href="javascript:void(0);" onClick="delete__(<s:property value="id"/>);return false;">删除</a>
						</td>
					</tr>
				</s:iterator>
		  </table>
			<div style="width: 100%; text-align: center; margin-top: 10px;">
				<wysLib:page></wysLib:page>
				<a href="combinationSearchforumInit.action" class="textbg4" style="width:80px">返回</a>
			</div>
			
		</s:else>
		</form>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				flbform.action="combinationSearchforum.action?str=";
				document.getElementById("xx").value='';
				flbform.submit();
			}
			function fsearch( ){
				flbform.action="combinationSearchforum.action";
				document.getElementById("pageNow").value=0;
				document.getElementById("xx").value='';
				flbform.submit();
			}
			function delete__(forum_id){
				if(window.confirm('确定删除？')){//forum_deletebyuid.action?forum.id=<s:property value="id"/>
					flbform.action="combinationSearchforum.action";
					document.getElementById("pageNow").value=0;
					document.getElementById("xx").value='del';
					document.getElementById("forum_id").value=forum_id;
					flbform.submit();
				}
			}
		</script>
	</body>
</HTML>
