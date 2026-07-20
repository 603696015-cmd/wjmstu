<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
	<script type="text/javascript">
			function full_screen(flag){
				if(flag){
					var header = document.getElementById("header");
					var img = document.getElementById("show_menu_window_img");
					header.style.display = "none";
					show_menu_window(img);
					document.frames("rightFrame").document.getElementById("quit").style.display = "block";
				}else{
					var header = document.getElementById("header");
					var img = document.getElementById("show_menu_window_img");
					header.style.display = "block";
					if(img.alt = "显示菜单")	
						show_menu_window(img);
					var frame = document.frames("rightFrame");
					document.frames("rightFrame").document.getElementById("quit").style.display = "none";
				}
			}
	</script>
	<table width="100%" height="58" style="margin: 0px;padding: 0px" cellpadding="0" cellspacing="0" background="images/bg_admin.jpg" border="0">
		<tr>
			<td style="padding: 0px"><img src="images/name2.jpg"/><td>
			<td><table width="100%" cellpadding="0" cellspacing="0">
				<tr><td align="right" style=" color:#fff; font-size:12px; ">
				姓名：
				<strong class="font_arial white">
					<s:property value="#session.realname" />
				</strong>，
				<!--身份证：
				<strong class="font_arial white">
					<s:property value="#session.shenfenzheng" />
				</strong>，-->
				部门：
				<strong class="font_arial white">
					<s:property value="#session.myDepName" />
				</strong>，
				用户名：
				<strong class="font_arial white"><s:property
						value="#session.username" /> </strong>，角色：
				<s:property value="#session.roleName" />
				|
				<a href="logout.action" class="white">退出登录</a> |
				<a href="index.action" class="white">网站首页</a> |
				<!--<a href="index2.action" class="white">hszx网站首页</a>-->
				</td>
				</tr> 
				
				<!-- 门户网站  网上课堂  竞赛中心  教务管理  资料管理  系统管理  个人中心  dk3-->				
				<tr>
				 <td style="padding: 0px;" align="right" valign="bottom"><ul id="menu" style="margin: 0px;">
					<s:iterator value="menus">
					<li>
						<a <s:if test="Return==funccode">class="on"</s:if> href="admin.action?module=<s:property value="funccode"/>"  title="<s:property value="name"/>"><span><s:property value="name"/></span>
						</a>
					</li>
					 </s:iterator>
					 <li>
					 	<a href="javascript:full_screen(true);"  ><img src="images/full-screen.png" border="0"  style="vertical-align:middle"></a>
					 </li>
				   </ul>
				 </td>
				</tr>
				
				
			</table></td>
		</tr>
		<tr>
		<td height="3px"></td>
		</tr>
	</table>
