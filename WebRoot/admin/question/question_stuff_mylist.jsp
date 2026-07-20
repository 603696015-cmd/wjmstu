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
		<base target="_self">
		<script type="text/javascript">
			var path="";
			function closeWind(){
		      window.returnValue   = path;   
		      window.close();   
			}
			function setPath(id,ext){
				path = "<%=basePath%>elstuffs/"+id+"."+ext;
			}
			function fanhuiback(){
				ddd.action="question_stuff_mylist.action?pN=0&pS=10";
				ddd.submit();
			}
			function page(i){
				document.getElementById("pageNow").value=i;
				ddd.action="question_stuff_mylist.action?pN="+i+"&pS=10";
				ddd.submit();
			}
		</script>
	</HEAD>
	<BODY style="width: 500px; height: 400px;font: 12px;">
		<div style="height: 100%">
		<form action="question_stuff_mylist.action" name="ddd" method="post">
					<s:hidden name="pN" id="pageNow"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:textfield name="qstuff.title"></s:textfield>
					<select name="qstuff.type">
						<option value="0">
							全部
						</option>
						<option value="1">
							图片
						</option>
						<option value="2">
							音频
						</option>
						<option value="3">
							视频
						</option>
						<option value="4">
							文档
						</option>
						<option value="5">
							其他
						</option>
					</select>
					<input type="submit" value="查找">
				</form>
			<s:if test="qstuffs.size==0">您还没有资料<br>
				<input type="button" onclick="javascript:fanhuiback();" value="返回"/>
			</s:if>
			<s:else>
				<table width="90%" align="center" cellspacing="2" cellpadding="1">
					<tr>
						<td align="center" >
							&nbsp;
						</td>
						<td align="center" >
							名称
						</td>
						<td align="center" >
							类别
						</td>
						<td align="center" >
							大小
						</td>
						<td align="center" >
							创建时间
						</td>
					</tr>
					<s:iterator value="qstuffs">
						<tr>
							<td align="center" >
								<input type="radio" name="qstuffs.id"
									onclick="setPath(<s:property value="id"/>,'<s:property value="fileext" />')"
									value="<s:property value="id"/>">
							</td>
							<td align="center" >
								<s:property value="title" />
								.
								<s:property value="fileext" />
							</td>
							<td align="center" >
								<s:property value="typeName" />
							</td>
							<td align="center" >
								<s:property value="length" />
							</td>
							<td align="center" >
								<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
					</s:iterator>
				</table>
				<input type="button" value="确定" onclick="closeWind();">
				<wysLib:page></wysLib:page>
			</s:else>
		</div>
	</BODY>
</HTML>
