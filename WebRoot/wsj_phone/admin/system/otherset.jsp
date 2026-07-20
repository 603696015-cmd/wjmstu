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
		<TITLE>系统设置</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript">
				function setParent(dd,id){
				document.getElementById("parentid"+dd).value=id;
			}
		</SCRIPT>
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			
			}
			function deleteElclassUserinfo(obj){
				if(window.confirm("确定删除？")){ 
					$.post("otherset_delete_index_class.action", { 
						"x":Math.random
						}, 
						function (data) {
							alert('删除成功');
						});
					obj.parentNode.parentNode.removeChild(obj.parentNode);
				}
			}
		
		</SCRIPT>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">系统设置</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="otherset" method="post" theme="simple" id="otherset">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<!--<tr>
						<td align="center" >
							注册否要审核
						</td>
						<td align="center" >
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.register_need_sh"></s:radio>
						</td>
					</tr>-->
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" align="right"> 
							论坛发帖是否要审核：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.forum_need_sh"></s:radio>
						</td>
					</tr>
					<!--<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="left"> 
							知识发布是否要审核
						</td>
						<td align="center" >
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.knowledge_need_sh"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="left"> 
							发证书是否需要审核
						</td>
						<td align="center" >
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.zhenshu_need_sh"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="left"> 
							课程制作是否需要审核
						</td>
						<td align="center" >
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.coursemake_need_sh"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="left"> 
							选班是否需要审核
						</td>
						<td align="center" >
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.study_class_need_sh"></s:radio>
						</td>
					</tr>-->
					<!--<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="left"> 
							选课是否需要审核
						</td>
						<td align="center" >
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.study_course_need_sh"></s:radio>
						</td>
					</tr>-->
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							同步课堂：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.openmeetings_url" size="50"/>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							同步课堂管理员账号：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.openmeetings_admin_user" />
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							同步课堂管理员密码：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.openmeetings_admin_pwd" />
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							资源服务器：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<input type="radio" checked="checked" name="sysconf.stuff_url_local" value="1"/>本机
							<input type="radio" <s:if test="sysconf.stuff_url_local==0">checked="checked" </s:if> name="sysconf.stuff_url_local" value="0"/>远程<br/>
							地址：<s:textfield name="sysconf.stuff_url" />（例如：http://www.baidu.com/）
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							帖子回复是否需要审核：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.stuff_isftopic"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="200" align="right"> 
							普通产品发布是否需要审核：

						
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.product_need_sh"></s:radio>
					</tr>
					<tr>

						<td height="30" style="padding-left:8px;color:blue;" width="200" align="right"> 
							保险产品发布是否需要审核：

						
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.baoxianProduct_need_sh"></s:radio>
						</td>
					</tr>
					<tr>

						<td height="30" style="padding-left:8px;color:blue;" width="200" align="right"> 
							设备发布是否需要审核：

						
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.shebei_need_sh"></s:radio>
						</td>
					</tr>
					<tr>

						<td height="30" style="padding-left:8px;color:blue;" width="200" align="right"> 
							商品审核后是否允许修改：

						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'允许','false':'不允许'}"
								name="sysconf.product_fabu_can_alter"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="200" align="right"> 
							添加线下培训是否需要审核：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.line_training_course_add_need_sh"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="200" align="right"> 
							是否可注册：

						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{1:'可以',0:'不可以'}" name="registerstatus"/>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							注册是否要审核：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.register_need_sh"></s:radio>
						</td>
					</tr>
					<!--<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							登录是否记录ip：
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							登录是否记录ip：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'1':'记录','0':'不记录'}"
								name="sysconf.login_addip"></s:radio>
						</td>
					</tr>-->
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							注册信息是否都要验证：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.register_isall"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							学员导入是否需要验证：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.userimp_ischeck"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							office题上传大小设置：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.officeSize" />
						</td>
					</tr>
					<tr style="display: none;">
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							office_home路径设置：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.office_home" />
						</td>
					</tr>
					<tr style="display: none;">
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							pdf2swf路径设置：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.pdf2swf_path" />
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							http所用端口：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.http_port" />
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							https所用端口：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.https_port" />
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							简易中国食品安全培训网：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.exam"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="200" align="right"> 
							登录是否需要验证验证码：

						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{1:'需要',0:'不需要'}" name="sysconf.yzcode_open"/>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							是否禁止多点登陆：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.allowMultipleSign"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							是否在全表内资料查询：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.is_enquiry_in_table"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							自定义模块静态页存放路径：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.zdy_html" />
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							收件人权限判断：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.is_receive_by_judge"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							发布问题是否需要审核：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.release_question_need_sh"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							回答问题是否要审核：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'是','false':'否'}"
								name="sysconf.answer_question_need_sh"></s:radio>
						</td>
					</tr>
					
					<tr>
						<td align="right" style="padding-left:8px;color:blue;">
						<span class="STYLE1">最大登陆数：</span></td>
						<td align="left" style="padding-left:8px;">
							<s:textfield name="sysconf.login_max" />
							<span style= "color:red" >( 0 表示不开启此功能)</span>
						</td>
					</tr>
					<tr>
						<td align="right" style="padding-left:8px;color:blue;">
						<span class="STYLE1">最大登陆失败次数：</span></td>
						<td align="left" style="padding-left:8px;">
							<s:textfield name="sysconf.login_failure_max" />
							<span style= "color:red" >( 0 表示不开启此功能)</span>
						</td>
					</tr>
					<tr>
						<td align="right" style="padding-left:8px;color:blue;">
						<span class="STYLE1">首页通过率设置：</span></td>
					  <td align="left" style="padding-left:8px;"> 
							<div id="PXB" > 
								<s:if test="sysconf.index_classid != 0">
										<span
											style="width: 250px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
											<label style="width: 200px; float: left;">
												<s:property value="sysconf.index_class.name" />
												<s:hidden name="elClasss[0].id"></s:hidden>
											</label> 
											<a style="cursor: hand; float: right; width: 28px; height: 28px;"
												href=""
												onclick="javascript:deleteElclassUserinfo(this,<s:property value="sysconf.index_class.id"/>);return false;">X</a>
										</span>
								</s:if> 
							</div> 
						<p>&nbsp;					    </p>
							<p>
							  <input type="button" onClick="searchElclassUser_RCCX()" value="添加"> 
					            </p></td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							公共文件开头：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.public_begin" />
							<a href="javascript:setUrl('otherset_sysconf_public_begin');" class="textbg4"
												style="width: 80px">浏览资源库</a>
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;"  align="right"> 
							公共文件结尾：
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							<s:textfield name="sysconf.public_end" />
							<a href="javascript:setUrl('otherset_sysconf_public_end');" class="textbg4"
												style="width: 80px">浏览资源库</a>
						</td>
					</tr>
					
					<!--
					<tr style="display: none;">
						<td align="center" >
							素材大小限制
						</td>
						<td align="center" >
							<s:textfield name="sysconf.stuff_size" />（M）
						</td>
					</tr>
					--><!--<tr>
						<td align="center" >
							首页广告图片
						</td>
						<td align="center" >
							<s:textfield name="sysconf.shouye_img" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							首页广告连接
						</td>
						<td align="center" >
							<s:textfield name="sysconf.shouye_url" />
						</td>
					</tr>
				--></table>
			  <br>
				<input type="submit" value="保存设置"  class="textbg6" >
			</s:form>
		</div>
		<script type="text/javascript">
				function checkFile(){
					if(document.getElementById("otherset_sysconf_public_begin").value==""||document.getElementById("otherset_sysconf_public_begin").value=="public_begin"){
						alert("请上传公共头文件！");
						return false;
					}
					if (document.getElementById("otherset_sysconf_public_begin").value.replace(/\s/g, "") != "") //这里输入框不为空
	            {
	                var FileType = "mp3,opus,ogg,wav,m4a,weba";    //这里是允许的后缀名，注意要小写
	                var FileName = document.getElementById("otherset_sysconf_public_begin").value;
	               // FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase(); //这里把后缀名转为小写了，不然一个后缀名会有很多种大小写组合，前面允许的文件后缀要写死人了。
	                    if(FileName==""){
	                    	alert("请检查文件格式是否正确!!");
	                    	return false;
	                    }else{
	                     FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase();
	                     if(!(FileName=="mp3"||FileName=="opus"||FileName=="ogg"||FileName=="wav"||FileName=="m4a"||FileName=="weba")){
	                  		 alert("请选择正确的音频格式！");
	                           return false;
	                  }
	            }
	            }
	            
	            if(document.getElementById("otherset_sysconf_public_end").value==""||document.getElementById("otherset_sysconf_public_end").value=="public_end"){
						alert("请上传公共尾文件！");
						return false;
					}
					
	            if (document.getElementById("otherset_sysconf_public_end").value.replace(/\s/g, "") != "") //这里输入框不为空
	            {
	                var FileType = "mp3,opus,ogg,wav,m4a,weba";    //这里是允许的后缀名，注意要小写
	                var FileName = document.getElementById("otherset_sysconf_public_end").value;
	               // FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase(); //这里把后缀名转为小写了，不然一个后缀名会有很多种大小写组合，前面允许的文件后缀要写死人了。
	                  if(FileName==""){
	                    	alert("请检查文件格式是否正确!!");
	                    	return false;
	                    }else{
	                     FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase();
	                     if(!(FileName=="mp3"||FileName=="opus"||FileName=="ogg"||FileName=="wav"||FileName=="m4a"||FileName=="weba")){
	                  		 alert("请选择正确的音频格式！");
	                           return false;
	                  }
	            }
	            }
	            document.otherset.action="otherset.action";
	            document.otherset.submit();
				}
		 </script>
		<!-- 内容 -->
	
	</body>
</HTML>
