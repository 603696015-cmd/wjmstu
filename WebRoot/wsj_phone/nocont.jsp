<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		<TITLE>课程学习提示</TITLE>
		<META name=description content="">
		<LINK rel=stylesheet type=text/css href="images/reg/style_110531.css">
		<LINK rel=stylesheet type=text/css href="images/reg/patch120202.css">
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<script type="text/javascript">
			function close(){
				top.opener =null;
				top.close();
			}
		</script>
		<STYLE type=text/css>
			.chose-list .recommend A {
				ZOOM: 1;
				COLOR: #000;
				TEXT-DECORATION: none
			}
			
			.chose-list .recommend A:hover {
				TEXT-DECORATION: underline
			}
			
			.chose-list .recommend LABEL {
				CURSOR: default
			}
			
			.error {
				color: red;
			}
		</STYLE>
		
		<META name=GENERATOR content="MSHTML 8.00.6001.19088">
	</HEAD>
	<BODY onLoad="init();">
		
			
			<DIV id=Rpage class=Rpage-main>
				<DIV id=Rbody>
					<DIV class=title>
						<B class=crl></B><B class=crr></B>
						<!-- <A class=ext href="study.action">返回学习中心»</A> -->
						<H1>课程提示						</H1>
				  </DIV>
					<DIV class=content>
					  <DIV class="g-collection collection-main">
					  
					  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100">&nbsp;</td>
    <td height="200" align="center" style="font-size:30px;color:red;">这门课程暂无学习内容<br>
      <br>
      请选择其他课程进行学习<br>
</td>
    <td width="100">&nbsp;</td>
  </tr>
  <tr>
    <td width="100">&nbsp;</td>
    <td height="200" align="center" valign="top"><a style="font-size:30px;color:blue;" href="javascript:close();">&lt;&lt;关 闭&gt;&gt;</a></td>
    <td width="100">&nbsp;</td>
  </tr>
</table>

					  
					  
					  </DIV>
					</DIV>
					<DIV class=bottom>
						<B class=crl></B><B class=crr></B>
					</DIV>
				</DIV>
			</DIV>
			
		
	
	</body>
</HTML>