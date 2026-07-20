<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>查找试卷</TITLE>
		<base target="_self">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
		<script type="text/javascript">
					function page(i){
						document.getElementById("pageNow").value=i;
						//epsform.action = "exampaper_list.action"; 
						epsform.action = "assist_survey_epsearchlist.action";
						epsform.submit();
						
					}
					function backtosearch(){
							epsform.action = "assist_survey_epsearchInit.action";
							epsform.submit();
					}
					var idandtitle = new Array();
					function queding(){
						if(idandtitle!=undefined&&idandtitle.length>0)
						window.returnValue = idandtitle;
						window.close();
					}
					function selectTheEP(id,ept,tscore){
						idandtitle[0]= id;
						idandtitle[1]= ept;
						idandtitle[2]= tscore; 
					}
				</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查找试卷" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form method="post" name="epsform" theme="simple"
				action="exampaper_delete">
				<s:if test="examPapers.size==0">没有符合条件的试卷<Br />
					<input class="textbg4" style="width: 100px;" type="button"
						value="重新搜索试卷" onclick='backtosearch()' />
				</s:if>
				<s:else>

					<s:hidden name="pN" id="pageNow"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:hidden name="examPaper.title"></s:hidden>
					<s:hidden name="examPaper.epl.id"></s:hidden>
					<s:hidden name="sublibs"></s:hidden>
					<table width="96%" align="center" cellspacing="2">
						<tr>
							<th align="center">&nbsp;
								
							</th>
							<th align="center">
								试卷标题
							</th>
							<th align="center">
								所属试卷库
							</th>
							<th align="center">
								分值
							</th>
							<th align="center">
								创建时间
							</th>
							<th align="center">
							</th>
						</tr>
						<s:iterator value="examPapers">
							<tr>
								<td align="center">
									<input type="radio"
										onclick="selectTheEP(<s:property value="id"/>,'<s:property value="title" />','<s:property value="ep_tscore" />')"
										name="examPapers1.id" value="<s:property value="id"/>">
								</td>
								<td align="center">
									<s:property value="title" />
								</td>
								<td align="center">
									<s:property value="epl.name" />
								</td>
								<td align="center">
									<s:property value="ep_tscore" />
								</td>
								<td align="center">
									<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
								</td>
								<td align="center">
									<a target="_blank"
										href="exampaper_preview.action?examPaper.id=<s:property value="id" />"
										class=textbg4>预 览</a>
								</td>
							</tr>
						</s:iterator>
					</table>
					<wysLib:page></wysLib:page>
					<input type="button" value="确定" class="textbg6"
						onclick='queding();' />&nbsp;&nbsp;<input class="textbg4"
						style="width: 100px;" type="button" value="重新搜索试卷"
						onclick='backtosearch()' />
					<br />
				</s:else>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
