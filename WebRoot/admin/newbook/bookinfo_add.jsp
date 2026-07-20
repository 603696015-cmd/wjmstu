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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
		<style type="text/css">
		.error{color: red;}
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<SCRIPT type="text/javascript">
		var authorinfocount=0;
		var bookinfocount=0;
		var bookinfodirectoryinfo=0;
		function addOption(obj){ 
					var type=obj;
					var obj = document.createElement("div");
					obj.id="option_"+type;
					if(type==1&&authorinfocount==0){
					
					obj.innerHTML ="<table align='center'><tr><td>单击此处作者简介进行编辑</td></tr></table>"+"<textarea style='border:1px solid buttonface;overflow:hidden;width:100%px;height:120px' onfocus='createeditor(this,"+type+")' id='__option"+type+"' name='bookinfo.authorinfo'></textarea>"
					document.getElementById("option_area").appendChild(obj);
					authorinfocount++;
				}
				if(type==2&&bookinfocount==0){
					
					obj.innerHTML ="<table align='center'><tr><td>单击此处图书进行编辑</td></tr></table>"+"<textarea style='border:1px solid buttonface;overflow:hidden;width:100%px;height:120px' onfocus='createeditor(this,"+type+")' id='__option"+type+"' name='bookinfo.bookinfo'></textarea>"
					document.getElementById("option_area").appendChild(obj);
					bookinfocount++;
				}
				if(type==3&&bookinfodirectoryinfo==0){
					
					obj.innerHTML ="<table align='center'><tr><td>单击此处目录简介进行编辑</td></tr></table>"+"<textarea style='border:1px solid buttonface;overflow:hidden;width:100%px;height:120px' onfocus='createeditor(this,"+type+")' id='__option"+type+"' name='bookinfo.directoryinfo'></textarea>"
					document.getElementById("option_area").appendChild(obj);
					bookinfodirectoryinfo++;
				}
				}
				function createeditor(obj,id){
					//alert("dd"+id);
					//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
					//$("#opt_frame"+id).attr("width",500);
					//$("#opt_frame"+id).attr("height",120);
					var oFCKeditor = new FCKeditor(obj.id) ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height =200;
					oFCKeditor.Width = 700;
					oFCKeditor.ToolbarSet = "qoption" ;
					oFCKeditor.ReplaceTextarea();
				}
	$(
		function test(){
			var oFCKeditor = new FCKeditor('content') ;
			oFCKeditor.BasePath = "editor/" ;
			oFCKeditor.Height = 200;
			oFCKeditor.Width = 700;
			oFCKeditor.ToolbarSet = "qcontent" ;
			oFCKeditor.ReplaceTextarea();
			addOption(1);
			addOption(3);
		}
	
	)
	
	
	function  check(){

			if($("#bookinfoname").val()==""){
				alert("请选择图书类别");
				return false;
			}
			if($("#bookinfoauthor").val()==""){
				alert("请填写作者信息");
				return false;
			}
			if($("#bookinfoname2").val()==""){
				alert("请填写图书名称信息");
				return false;
			}
			if($("#bookinfomarketprice").val()==""){
				alert("请填写市场价格");
				return false;
			}
			if($("#bookinfovipprice").val()==""){
				alert("请填写会员价格");
				return false;
			}
			if(isNaN($("#bookinfomarketprice").val())){

					   alert("请输入正确的市场价格！");
					   return false;
					}
			if(isNaN($("#bookinfovipprice").val())){
					   alert("请输入正确的会员价格！");
					   return false;
					}	
			if($("#bookinfomarketprice").val()<0){
	
					   alert("请输入正确的市场价格！");
					   return false;
					}		
			if($("#bookinfovipprice").val()<0){
					   alert("请输入正确的会员价格！");
					   return false;
					}
			if($("#bookinfoformat").val()==""){
				alert("请输入正确的开本数！");
				return false;
			}	
			if(isNaN($("#bookinfoformat").val())){
					   alert("请输入正确的开本数！");
					   return false;
					}		
		if($("#bookinfoformat").val()<0){
				alert("请输入正确的开本数！");
				return false;
			}
			if($("#bookinfopage").val()==""){
				alert("请输入正确的页数！");
				return false;
			}	
			if(isNaN($("#bookinfopage").val())){
					   alert("请输入正确的页数！");
					   return false;
					}								
			
			if($("#bookinfopage").val()<0){
				alert("请输入正确的页数！");
				return false;
			}
			if($("#bookinforelease").val()==""){
				alert("请输入发布时间！");
				return false;
			}	
			
			
			
			return  true;
	
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
		

		<s:form action="bookinfo_add" method="post" enctype="multipart/form-data" onsubmit="return check();">
			<s:hidden name="bookinfo.bookType.id" id="bookTypeid" />
			<div style="margin-top: 0px;">
				<table id="info12" width="100%" cellpadding="1" cellspacing="1">
					<caption>
						基本信息
					</caption>
					<tr>
						<td  height="40" align="center" width="15%">
							<strong>图书类别</strong>
						</td>
						<td height="40"  width="35%" align="left" >
							 <s:textfield theme="simple" theme="simple" name="booktypename"  id="bookinfoname" readonly="true"/>
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
								<s:textfield theme="simple" name="bookinfo.name" id="bookinfoname2" />
						  </label>
						</td>
						<td  height="40" align="center" >
							<strong>印刷时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.printdate" id="bookinfoprintdate" readonly="true"  onclick="setday(this)"/>&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>出版社</strong>
						</td>
						<td height="40" align="left" >
							<label>
								<s:textfield theme="simple" name="bookinfo.press" id="bookinfopress" />&nbsp;&nbsp;<span style="color:red;">*</span>
							</label>
						</td>
						<td  height="40" align="center" >
							<strong>出版时间</strong>
						</td>
						<td  height="40" align="left" >
							<label>
							
								<s:textfield theme="simple" name="bookinfo.pressdate" id="bookinfopressdate"  readonly="true"   onclick="setday(this)"/>&nbsp;&nbsp;<span style="color:red;">*</span>
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
									name="bookinfo.release" id="bookinforelease"
									value="<s:date name="bookinfo.release" format="yyyy-MM-dd HH:mm"/>" readonly="readonly" >&nbsp;&nbsp;<span style="color:red;">*</span>
						</td>
					</tr>
					<tr>
						<td  height="40" align="center" >
							<strong>封 面(240*300)</strong>
						</td>
						<td height="40" align="left" >
<s:textfield name="bookinfo.picture" id="mainimg" size="60" theme="simple" />
								<a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a>
						</td>
						<td  height="40" align="center" >&nbsp;
						
						</td>
						<td height="40" align="left" >
							
						</td>
					</tr>	

			  </table>
				
				<table width="100%" align="center">
				<tr>
				  <td height="25">　图书信息编辑框
				</td>
				</tr>
			  </table>
				<s:textarea name="bookinfo.bookinfo" id="content" cssStyle="width:100%px;height:150px;visibility:hidden;" theme="simple" />
				
				<div id="option_area"></div>
				
					<s:submit value="确认添加" name="确认添加" theme="simple" />
					
		  </div>
				
				
		</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>
