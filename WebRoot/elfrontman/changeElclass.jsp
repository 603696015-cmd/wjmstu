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
		<TITLE>选班页面</TITLE>
		<META name=description content="">
		<LINK rel=stylesheet type=text/css href="images/reg/style_110531.css">
		<LINK rel=stylesheet type=text/css href="images/reg/patch120202.css">
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
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
		.STYLE5 {	font-size: 14px;
	font-weight: bold;
}
.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.STYLE7 {	font-size: 12px
}
        </STYLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript"> 
			function changeNazheng(){
  		var classid = <s:property value="new_cla.elClass.id" />;
  		// this.location.href = "newclass_view2.action?elclass.id="+classid+"&ctype=2";
  		window.open("newclass_view2.action?elclass.id="+classid+"&ctype=2","change","fullscreen = yes , height=100, width=400, top=0, left=0,toolbar=yes, menubar=yes, scrollbars=yes,resizable=yes,location=yes, status=no");
  		//window.opener.location.href="newclass_view2.action?elclass.id="+classid+"&ctype=2";
  	//	window.close();
  		
  	}
  	function changeNianjian(){
  		var classid = <s:property value="nianjian_cla.elClass.id" />;
  		window.open("newclass_view2.action?elclass.id="+classid+"&ctype=2","change","fullscreen = yes , height=100, width=400, top=0, left=0,toolbar=yes, menubar=yes, scrollbars=yes,resizable=yes,location=yes, status=no");
  		//window.parent.location.href = "newclass_view2.action?elclass.id="+classid+"&ctype=2";
  		//window.opener.location.href="newclass_view2.action?elclass.id="+classid+"&ctype=2";
		 
  		//window.close();
	//	window.parent.close();
  	}
		</script>
		
		<META name=GENERATOR content="MSHTML 8.00.6001.19088">
	</HEAD>
	<BODY >
		<%@include file="/elfrontman/frontheader.jsp"%>
			
			<table width="980" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-top:8px;">
              <tr>
                <td valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                    <tbody>
                      <tr>
                        <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                        <td width="662" background="images/knowledge/zhao_22.gif"></td>
                        <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                      </tr>
                      <tr>
                        <td background="images/knowledge/zhao_24.gif"></td>
                        <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
                            <tr>
                             
                              <td>培训报名
                              </td>
                            </tr>
                        </table></td>
                        <td background="images/knowledge/zhao_25.gif"></td>
                      </tr>
                      <tr>
                        <td background="images/knowledge/zhao_24.gif"></td>
                        <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                        <td background="images/knowledge/zhao_25.gif"></td>
                      </tr>
                      <tr>
                        <td background="images/knowledge/zhao_24.gif"></td>
                        <td height="400" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
						
						<DIV id=Rpage class=Rpage-main>
				<DIV id=Rbody>
					<DIV class=title>
					
						<H1 align="center" style="color:red;font-size:20px;font-weight:bold;">
							已有证书，点击证书年检；尚无证书，点击培训报名！
						</H1>
					</DIV>
					
			</DIV>
    </DIV>
		   <table width="100%" height="300" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="50%"> <table width="245" border="0" align="center" cellpadding="0" cellspacing="0">
       <tr>
         <td height="75" align="center" valign="middle" background="images/sdimages/button.png"> <a href="newclass_view2.action?elclass.id=<s:property value='new_cla.elClass.id'/>" id="fanhui"><span style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">拿证报名</span></a>        </td>
       </tr>
     </table></td>
				<td width="50%"> <table width="245" border="0" align="center" cellpadding="0" cellspacing="0">
       <tr>
        
       
      
         <td height="75" align="center" valign="middle" background="images/sdimages/button.png"><a href="newclass_view2.action?elclass.id=<s:property value='nianjian_cla.elClass.id'/>" id="fanhui"><span style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">年检报名</span></a></td>
       </tr>
     </table></td>
			  </tr>
	</table>
						</td>
                        <td background="images/knowledge/zhao_25.gif"></td>
                      </tr>
                      <tr>
                        <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                        <td background="images/knowledge/zhao_27.gif"></td>
                        <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                      </tr>
                    </tbody>
                </table></td>
              </tr>
            </table>
			
	
		   
		<%@include file="/elfrontman/frontbottom.jsp"%>
	</BODY>
</HTML>