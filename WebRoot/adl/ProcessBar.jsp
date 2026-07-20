<%@ page language="java" import="java.util.*,org.adl.samplerte.util.*"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>进度条</title>
<style type="text/css">
<!--
.STYLE1 {
	color: #0099FF
}

.table1 {
	border: thin solid #999999;
}

tr {
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	height: 9px;
}

.td1 {
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	height: 9px;
}
-->
</style>
<script type="text/javascript" src="mtmcode.js">
	
</script>
</HEAD>

<body>
<%
	SCOHelper sh = new SCOHelper();
	String sco_user = String.valueOf(session.getAttribute("userId"));
	String sco_course = (String) session.getAttribute("COURSEID");
	String sco_id = (String) session.getAttribute("ACTIVITYID");
	System.out.println("[SCORM 1.2] USERID:"+ sco_user+ " COURSEID:"+sco_course +" ACTIVITYID:"+sco_id);
	int sum = sh.getItemCount(sco_course);
	int learned = sh.getLearnedCount(sco_user, sco_course);
	String lastid = sh.getLastItem(sco_course, sco_id);
	String nextid = sh.getNextItem(sco_course, sco_id);
%>
<table width="100%" height="10%" border="0" style="margin-top: -10">
	<tr>
		<td width="76%" height="19" align="left" valign="top">
		<table width="100%" height="20" border="0" cellpadding="0"
			cellspacing="0" bgcolor="#0099FF" class="table1">
			<tr>
				<%
					for (int i = 0; i < learned; i++) {
				%><td bgcolor="#40b4c0"></td>
				<%
					}
					for (int k = 0; k < sum - learned; k++) {
				%><td bgcolor="#ffffff"></td>
				<%
					}
				%>

			</tr>
		</table>
		</td>
		<td width="24%" align="center">
		<%
			if (!lastid.equals("")) {
		%><a href="javascript:launchItem('<%=lastid%>')" target="code"><<</a>
		<%
			}
		%> &nbsp;&nbsp;<%=learned%>/<%=sum%>&nbsp;&nbsp;&nbsp; <%
 	if (!nextid.equals("")) {
 %><a href="javascript:launchItem('<%=nextid%>')" target="code">>></a>
		<%
			}
		%>
		</td>
	</tr>
</table>

</body>
</html>
