<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" target="_self" />
		<title>资源库视频播放</title>
		
		<link href="css/study_csp.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/cpstudy.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
	    <style type="text/css">
<!--
.STYLE7 {
	color: #333333;
	font-size: 14px;
	font-weight: bold;
}
-->
        </style>
        <script type="text/javascript">
			var _cvideo;
			function myload(){
				_cvideo = new CourseVideo(1,"<s:property value="qstuff.stuff_path"/>", 1);
				_cvideo.show("page_file");
			}
		</script>
</HEAD>
	<body onload="myload();">
		<div id="message" style="display: none;"></div>
		<div
			style="position: absolute; border: solid 1px buttonface; width: 400; height: 300px; background: white; z-index: 300; display: none;"
			id="noteadd">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 380">做笔记</span><span
					style="cursor: hand; width: 15px;" onClick="closediv('noteadd')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="noteaddcontent"></div>
		</div>
		<div
			style="position: absolute; border: solid 1px buttonface; width: 600; height: 400px; background: white; z-index: 301; display: none;"
			id="notelist">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 580">查看笔记</span><span
					style="cursor: hand; width: 15px;" onClick="closediv('notelist')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="notelistcontent"></div>
		</div>
		<table width="960" height="35" border="0" align="center"
			cellpadding="0" cellspacing="0" class="tabb2">
			<tr>
				<td background="images/bg1.gif" class="STYLE6" style="padding-left:20px;">
					当前位置：网络学院 >> 资源库 >> 资源标题
			  </td>
			</tr>
		</table>
		<table width="960" height="25" border="0" align="center"
			cellpadding="0" cellspacing="0" class="tabb2">
          <tr>
            <td height="30" style="font-size: 12px;padding-left:20px;"><span class="STYLE7"><s:property value="qstuff.title"/></span>
            </td>
            <td width="300" align="center" style="font-size: 12px;">
            	<s:if test="previousid==0"><a>&lt;&lt;&lt;上一个资源</a></s:if>
            	<s:else><a href="question_stuffPreview_index.action?qstuff.id=<s:property value="previousid"/>">&lt;&lt;&lt;上一个资源 </a></s:else>
            	<strong>| </strong>
            	<s:if test="nextid==0"><a>下一个资源 &gt;&gt;&gt;</a></s:if>
            	<s:else><a href="question_stuffPreview_index.action?qstuff.id=<s:property value="nextid"/>">下一个资源 &gt;&gt;&gt;</a></s:else>
            </td>
          </tr>
        </table>
		<table style="margin-top: 8px;" width="960px" height="500px" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="620px" valign="middle" align="center" height="500px">
				<div style="height:490px;width: 610px;" id="page_file"></div>
				</td>
				<td valign="top">
					<table width="100%" height="30" border="0" cellpadding="0"
						cellspacing="0" background="images/bg1.gif" class="tab42">
						<tr>
							<td style="padding-left:15px;">
								推荐资源
							</td>
						</tr>
					</table>
					<table width="100%" height="300px;" border="0" cellpadding="0"
						cellspacing="0" bgcolor="f3fcff" class="tabrlb">
						<tr>
						  <td valign="top" height="465" class=tdpad><table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <s:iterator value="qstuffs">
                            <tr>
                              <td width="30" height="35" align="center"><img src="images/shopping/pic_02.gif" width="5" height="9"></td>
                              <td><s:property value="title"/></td>
                            </tr>
                            </s:iterator>
                          </table></td>
						</tr>
					</table>
					
			  </td>
			</tr>
		</table>
		<table width="960" height="30" border="0" align="center" cellpadding="0"
						cellspacing="0" background="images/bg1.gif" class="tab42" style="margin-top:8px;">
          <tr>
            <td style="padding-left:15px;"> 资源简介 </td>
          </tr>
        </table>
		<table width="960" height="200" border="0" align="center" cellpadding="0"
						cellspacing="0" bgcolor="f3fcff" class="tabrlb">
          <tr>
            <td valign="top" class=tdpad><s:property value="qstuff.fileinfo"/></td>
          </tr>
        </table>
		<s:include value="frontbottom.jsp" />
	</body>
</html>
