<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
    <%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		var authorinfocount=0;
		var bookinfocount=0;
		var bookinfodirectoryinfo=0;
		function addOption(obj){ 
					var type=obj;
					var obj = document.createElement("div");
					obj.id="option_"+type;
					if(type==1){
					obj.innerHTML ="单击此处作者简介进行编辑<br>"+"<textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,"+type+")' id='__option"+type+"' name='bookinfo.authorinfo' </textarea>"
					document.getElementById("option_area1").appendChild(obj);
					authorinfocount++;
				}
				if(type==3){
					
					obj.innerHTML ="单击此处目录简介进行编辑<br>"+"<textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,"+type+")' id='__option"+type+"' name='bookinfo.directoryinfo'></textarea>"
					document.getElementById("option_area3").appendChild(obj);
				}
				}
				function createeditor(obj,id){
					//alert("dd"+id);
					//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
					//$("#opt_frame"+id).attr("width",500);
					//$("#opt_frame"+id).attr("height",120);
					var oFCKeditor = new FCKeditor(obj.id) ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 200;
					oFCKeditor.Width = 700;
					oFCKeditor.ToolbarSet = "qoption" ;
					oFCKeditor.ReplaceTextarea();
					
				}
			
				
			
function sh(){
									if(window.confirm('确定提交？')){
									 	bookinfoupdform.submit();
								 	}
								} 
								
					$(		
					function test(){	
						var oFCKeditor = new FCKeditor('content') ;
						oFCKeditor.BasePath = "editor/" ;
						oFCKeditor.Height = 200;
						oFCKeditor.Width = 700;
						oFCKeditor.ToolbarSet = "qcontent" ;
						oFCKeditor.ReplaceTextarea();
						
						
					}
					)
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
		

		<s:form action="bookinfo_upd" method="post" enctype="multipart/form-data" name="bookinfoupdform">
			<s:hidden name="bookinfo.bookType.id" id="bookTypeid" />
			<s:hidden name="flagbookinfo.statuse"  />
			<s:hidden name="flagbookinfo.recommend"  />
			<s:hidden name="flagbookinfo.click"  />
			<s:hidden name="authorinfostatus"  />
			<s:hidden name="delestatus"  />
			<s:hidden name="bookinfostatus"  />
			<s:hidden name="directoryinfostatus"  />
			<s:hidden name="bookinfo.id"  />
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
							 <s:textfield theme="simple"  name="bookinfo.bookType.name"  id="bookinfoname" />
							 <SPAN onClick="window.open('bookinfo_typeaddinit.action','选择图书类型','width=400,height=200,toolbar=no')" >点此进行选择</SPAN>
						</td>
					<td  height="40" align="center" width="15%">
							<strong>作者</strong>
						</td>
						<td  height="40"  width="35%">
								<s:textfield theme="simple" name="bookinfo.author" id="bookinfoauthor" />
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>图书名称</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.name" id="bookinfoname" />
								</label>
						</td>
						<td  height="40" align="center" >
							<strong>印刷时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
							<input type="text" onClick="setday(this)"
									name="bookinfo.printdate"
									value="<s:date name="bookinfo.printdate" format="yyyy-MM-dd "/>" readonly="readonly" >
								
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>出版社</strong>
						</td>
						<td height="40" align="left" >
							<label>
							<s:textfield theme="simple" name="bookinfo.press" id="bookinfopress"  />
							
								
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>出版时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
								<input type="text" onClick="setday(this)"
									name="bookinfo.pressdate"
									value="<s:date name="bookinfo.pressdate" format="yyyy-MM-dd "/>" readonly="readonly" >
								
							</label>
							
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>市场价（元）</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.marketprice" id="bookinfomarketprice" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>会员价（元）</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.vipprice" id="bookinfovipprice" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>版  次</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.version" id="bookinfoversion" />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>开 本</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.format" id="bookinfoformat" />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>页 数</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.page" id="bookinfopage" />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>字 数(千字)</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.words" id="bookinfowords" />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>纸 张</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.paper" id="bookinfopaper" />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>包 装</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.spackage" id="bookinfospackage" />
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>阅读体验</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.readurl" id="bookinforeadurl" />
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>发布时间</strong>
						</td>
						<td height="40" align="left" >
						
							<input type="text" onClick="setday(this)"
									name="bookinfo.release"
									value="<s:date name="bookinfo.release" format="yyyy-MM-dd HH:mm"/>" readonly="readonly" >&nbsp;&nbsp;<span style="color:red;">*</span>
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
															id="cimg_0" width="450" height="300" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else>	
						</td>
						<td  height="40"   align="center" >
							<strong>修改封面(240*300)</strong>
						</td>
						<td height="40" align="left" >
							<s:textfield name="bookinfo.picture" id="mainimg" size="60" theme="simple" />
								<a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a>
						</td>
											
					</tr>
					<tr>
						
						<td  height="40" align="center" >
						<strong>是否推荐</strong>
						</td>
						<td height="40" align="left" >
						<s:if test="delestatus==1"><s:property  value="bookinfo.Recommendname"  /></s:if>
						<s:if test="delestatus==2"><s:select theme="simple"  
									list="#{1:'未推荐',2:'推荐'}"
									name="bookinfo.recommend" value="bookinfo.recommend" /></s:if>
							
						</td>	
					</tr>	
					<tr>
						<td  height="40"   align="center" >
							<strong>状 态</strong>
						</td>
						<td height="40" align="left" >
						<s:if test="delestatus==1"><s:property  value="bookinfo.StatuseName"  /></s:if>
							<s:if test="delestatus==2"><s:select theme="simple"  
									list="#{1:'未通过',2:'通过'}"
									name="bookinfo.statuse" value="bookinfo.statuse" /></s:if>
						</td>
						
					</tr>
					<tr>
						<td  height="40"   align="center" >
							<strong>点击数</strong>
						</td>
						<td height="40" align="left" >
						<s:if  test="delestatus==1"><s:if  test="bookinfo.click ==null">0</s:if><s:else><s:property  value="bookinfo.click"/></s:else></s:if>
								<s:if test="delestatus==2"><s:if  test="bookinfo.click ==null"><s:textfield theme="simple" name="bookinfo.click" id="bookinfoclick" value="0"/></s:if>
								<s:else><s:textfield theme="simple" name="bookinfo.click" id="bookinfoclick" /></s:else></s:if>						
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
<td  colspan="4">
<div  id="authorinfo" style="display:block;width:100%;overflow:hidden;"
>
<table width="90%" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　图书简介 </td>
  </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;"><s:textarea name="bookinfo.bookinfo" id="content"
cssStyle="width:700px;height:150px;visibility:hidden;" /></td>
  </tr>
</table>
</div>
<div  id="bookinfo" style="display:block;width:100%;overflow:hidden;">
<table width="90%" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　作者简介 </td>
  </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;"><s:textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,1)' id='__option1' name='bookinfo.authorinfo' ></s:textarea>
															
														</td>
  </tr>
</table>
</div>
<div id="directoryinfo" style="display:block;width:100%;overflow:hidden;">
<table width="90%" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　图书目录 </td>
  </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;"><s:textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,3)' id='__option3' name='bookinfo.directoryinfo' ></s:textarea> </td>
  </tr>

</table>
</div>
</td>
</tr>	

					<tr ><td  colspan="4"><a onClick="sh();" class=textbg>确认提交</a> </td> </tr>
				</table>
				
			</div>

		</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>
