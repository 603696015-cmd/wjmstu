<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<style type="text/css">
		.error{color: red;}
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<SCRIPT type="text/javascript">
		var imgs = new Array();
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	} 
		
		
			$(function AddHeight()
		    {
			    document.getElementById("authorinfopart").style.display="none";
			    document.getElementById("bookinfopart").style.display="none";
			    document.getElementById("directoryinfopart").style.display="none";
		    	
		      var div = document.getElementById("authorinfo");
		      
		       var height=div.clientHeight ;
		      if(height<=300){
		      		div.style.height='';
				}else{
				
					div.style.height='300px';
				}
				
				var div2 = document.getElementById("bookinfo");
		      
		      var height2=div2.clientHeight ;
		      if(height2<=300){
		      		div2.style.height='';
				}else{
				
					div2.style.height='300px';
				}
				var div3 = document.getElementById("directoryinfo");
		      
		      var height3=div3.clientHeight ;
		      if(height3<=300){
		      		div3.style.height='';
				}else{
				
					div3.style.height='300px';
				}
		     
				}
				)
    	
      function all2(obj,obj2,obj3){
      var div = document.getElementById(obj2);
      
      div.style.height='';
      obj.style.display = "none";
      
      if(obj3==1){
      	document.getElementById("authorinfopart").style.display="";
      }if(obj3==2){
      	document.getElementById("bookinfopart").style.display="";
      
      }if(obj3==3){
      	document.getElementById("directoryinfopart").style.display="";
      }

    	}
    	function part(obj,obj2,obj3){
    		var div = document.getElementById(obj2);
    		var height=div.clientHeight ;
    		if(height>300){
    			div.style.height='300px';
    		}
    		obj.style.display="none";
    		
    if(obj3==1){
      	document.getElementById("authorinfoall").style.display="";
      }if(obj3==2){
      	document.getElementById("bookinfoall").style.display="";
      
      }if(obj3==3){
      	document.getElementById("directoryinfoall").style.display="";
      }
    	}
		</SCRIPT>

	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加图书" /></div>
			</li>
			<%-- 
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_searchInit.action">用户管理</a>

			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">添加用户</span>
			</li>
			 --%>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<s:form action="bookinfo_add" method="post" enctype="multipart/form-data">
			<s:hidden name="bookinfo.bookType.id" id="bookTypeid" />
			<div style="margin-top: 0px;">
				<table id="info12" width="90%" cellpadding="1" cellspacing="1">
					<caption>
						基本信息
					</caption>
					<tr>
						<td  height="40" align="center" width="15%">
							<strong>图书类别</strong>
						</td>
						<td height="40"  width="35%" align="left" >
							 <s:property   value="bookinfo.bookType.name"  />
						</td>
					<td  height="40" align="center" width="15%">
							<strong>作者</strong>
						</td>
						<td  height="40"  width="35%">
								 <s:property  value="bookinfo.author"  />
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>图书名称</strong>
						</td>
						<td height="40" align="left" >
							<label>
								 <s:property  value="bookinfo.name"  />
								</label>
						</td>
						<td  height="40" align="center" >
							<strong>印刷时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
								 <s:date name="bookinfo.printdate" format="yyyy-mm-dd" />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>出版社</strong>
						</td>
						<td height="40" align="left" >
							<label>
							<s:property  value="bookinfo.press"  />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>出版时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
								<s:date name="bookinfo.pressdate" format="yyyy-mm-dd" />
							</label>
							
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>市场价（元）</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.marketprice"  />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>会员价（元）</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.vipprice"  />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>版  次</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.version"  />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>开 本</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.format"  />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>页 数</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.page"  />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>字 数</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.words"  />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>纸 张</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.paper"  />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>包 装</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.spackage"  />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>阅读体验</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:property  value="bookinfo.readurl"  />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>发布时间</strong>
						</td>
						<td height="40" align="left" >
							<s:date name="bookinfo.release" format="yyyy-MM-dd HH:mm"/>
							
						</td>
					</tr>
					<tr>
						<td  height="40"  rowspan="5" align="center" >
							<strong>封 面</strong>
						</td>
						<td height="40" rowspan="5" align="left" >
						<s:if test="bookinfo.picture != null">
															<img src="<s:property value="bookinfo.mainimg_"/>" width="240" height="300"> 
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="bookinfo.mainimg_"/>"
															id="cimg_0" width="240" height="300" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else>			
						</td>
						<td  height="40" align="center" >
						<strong>是否推荐</strong>
						</td>
						<td height="40" align="left" >
							<s:property  value="bookinfo.Recommendname"  />
						</td>
						
					</tr>	
					<tr>
						<td  height="40"   align="center" >
							<strong>状 态</strong>
						</td>
						<td height="40" align="left" >
							<s:property  value="bookinfo.StatuseName"/>
						</td>
						
					</tr>
					<tr>
						<td  height="40"   align="center" >
							<strong>点击数</strong>
						</td>
						<td height="40" align="left" >
							<s:if  test="bookinfo.click ==null">0</s:if>
							<s:else><s:property  value="bookinfo.click"/></s:else>
							
						</td>
						
					</tr>
					<tr>
						<td  height="40"   align="center" >
							<strong>发布用户</strong>
						</td>
						<td height="40" align="left" >
							<s:property  value="bookinfo.user.realname"/>
						</td>
						
					</tr>
					<tr>
						<td  height="40"   align="center" >&nbsp;
							
						</td>
						<td height="40" align="left" >&nbsp;
							
						</td>
						
					</tr>
					<tr>
					<td  colspan="4"><div  id="authorinfo" style="display:block;width:100%;overflow:hidden;">
<table width="100%" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　作者简介 </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;">${bookinfo.authorinfo}</td>
  </tr>
</table>
</div>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
 <td height="15" align="right" valign="top"><img style="margin:10px;" src="images/shopping/more01.jpg" width="85" height="29" onClick="all2(this,'authorinfopart',1);" id="authorinfoall"><img style="margin:10px;" src="images/shopping/more02.jpg" width="85" height="29" id="authorinfopart" onClick="part(this,'authorinfo',1)"></td>
  </tr>
</table>
<div  id="bookinfo" style="display:block;width:100%;overflow:hidden;">
<table width="90%" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　内容简介 </td>
  </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;">　　${bookinfo.bookinfo} </td>
  </tr>
</table>
</div>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
 <td height="15" align="right" valign="top"><img style="margin:10px;" src="images/shopping/more01.jpg" width="85" height="29" onClick="all2(this,'bookinfo',2)" id="bookinfoall"><img style="margin:10px;" src="images/shopping/more02.jpg" width="85" height="29" onClick="part(this,'bookinfo',2)" id="bookinfopart"></td>
  </tr>
</table>
<div id="directoryinfo" style="display:block;width:100%;overflow:hidden;">
<table width="90%" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　图书目录 </td>
  </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;">　${bookinfo.directoryinfo} </td>
  </tr>

</table>
</div>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
 <td height="15" align="right" valign="top"><img style="margin:10px;" src="images/shopping/more01.jpg" width="85" height="29" onClick="all2(this,'directoryinfo',3)"  id="directoryinfoall"><img style="margin:10px;" src="images/shopping/more02.jpg" width="85" height="29" onClick="part(this,'directoryinfo',3)"  id="directoryinfopart"></td>
  </tr>
</table></td>
					</tr>
					<tr ><td  colspan="4"><a href="bookinfo_updinit.action?bookinfo.id=<s:property  value="bookinfo.id"  />&delestatus=<s:property  value="delestatus"/>" class=textbg>去修改</a> </td> </tr>
				</table>
			</div>

		</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>
