<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="财贸">
<meta name="keywords" content="财贸">
<title>知识--<s:property value="knowledge.title"/></title>
<LINK href="elfrontimages/home2.css" rel=stylesheet type="text/css">
<script type="text/javascript" src="js/course.js"></script>
<style type="text/css"> 
<!--
.STYLE2 {color: #999999}
body {
	background-color: #ffffff;
}
.STYLE5 {color: #ed7b0f;font-weight:bold;}
.textbox {BORDER-RIGHT: #666666 1px solid; BORDER-TOP: #666666 1px solid; FONT-SIZE: 9pt; BORDER-LEFT: #666666 1px solid; COLOR: #666666; BORDER-BOTTOM: #666666 1px solid; FONT-FAMILY: verdana; HEIGHT: 18px; BACKGROUND-COLOR: #ffffff
}
.texwhite {color: white;}
.txtwhite {color: white;}
.lh22{ line-height:22px;}
img{ border:none;}
.STYLE8 {color: #CC0099}
-->
</style>
<SCRIPT type="text/javascript">
function changeTreeDisplay(obj1){
	var obj = document.getElementById("tree_list_td");
	if(obj.style.display==""||obj.style.display=="block"){
	 	obj.style.display="none";
	 	obj1.src="images/leftmenu/main_55_1.gif";
	}
	else{
		obj.style.display="block";
	 	obj1.src="images/leftmenu/main_55.gif";
	}
}
</SCRIPT>
</HEAD>
<body >
<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="150px" valign="top" id="tree_list_td">
					 <table style="margin-top: 5px;" width="150px" border="0"
						cellpadding="0" cellspacing="0" class="bkgreen">
						<tr>
							<td height="32" valign="middle"
								background="elfrontimages/b32.jpg">
								<table width="100%" height="32" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="110" align="center" valign="middle">
											<p>
												<a href="#" onclick="return false;" class="titgreen14">知识栏目导航</a>
											</p>
										</td>
										<td align="right" valign="middle">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="130" valign="top" style="padding: 10px;">
								<wysLib:kltype_center_list></wysLib:kltype_center_list>
							</td>
						</tr>
					</table>
				</td>
				<td valign="top" width="5px;" style="padding-top: 100px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top" align="left">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bkgreen">
                  <tr>
                    <td height="700px" align="center" valign="top" bgcolor="#FBFEF5" style="padding:10px;"><table width="662" height="40" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td height="42" align="center" class="blue14"><s:property value="knowledge.title" /></td>
                        </tr>
                        <tr>
                          <td height="42" align="center">创建者：<s:property value="knowledge.owner.realname"/> 创建时间：<s:date name="knowledge.createtime" format="yyyy年MM月dd日 HH:mm:ss"/> 浏览次数：<s:property value="knowledge.readtime"/> 次</td>
                        </tr>
                        
                      </table>
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center" valign="top" style="line-height:25px;font-size:12px;"><s:if test="knowledge.kltype.id==3"><script>window.onload=function(){
                            _cvideo = new CourseVideo(2,"<s:property value="knowledge.wendang"/>", 0);
							_cvideo.show("page_file" );
						
                            }</script><div id ="page_file" style="text-align:center; width:600px;height: 500px; "></div> </s:if>
                            <s:if test="knowledge.kltype.id==2">
                            <img src="${knowledge.mainimg}" align="center" width="400" height="300"/> 
							</s:if>
                            <div style="width:100% ;text-align: left;"><s:if test="knowledge.kltype.id==5">
                           <b> 相关附件:</b><s:if test="knowledge.stuffs.size==0">暂无</s:if><s:iterator value="knowledge.stuffs">
										<a href="download_kstuff.action?stuff.id=<s:property value="id"/>"><s:property value="title"/></a><br>
									</s:iterator>
							</s:if>${knowledge.content}</div></td>
                          </tr>
                        </table>
                      </td>
                  </tr>
              </table>
				</td>	  
			</tr>
		</table>
</body>
</HTML>                                                                                              

