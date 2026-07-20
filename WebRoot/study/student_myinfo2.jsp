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
		<TITLE>用户信息查看</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)> 
		<link rel="stylesheet" type="text/css" href="css/css2/style.css" />
		<link rel="stylesheet" type="text/css" href="css/css2/base.css" />
	</HEAD>
	<BODY>
		<div id="OutlineDiv">
			 <table id="OutlineTable" width="100%" height="100%" border="0"
					cellspacing="0" cellpadding="2">
					<tr>
						<td>
							<!------------基本信息----------------->
							<TABLE width="100%" border="0" align="center" cellPadding="0"
								cellSpacing="0" class="pg_add"><%-- 
								<TR>
									<TD colspan="7" align="right" class="pg_add_title"
										style="padding-right: 30px;">
										<a href="index.action">网站首页</a>
										<a href="studentman.action">个人中心首页</a>
										<a href="studentman.action">返回</a>
									</TD>
								</TR>--%>
								<TR>
									<TD class="pg_add_title" colspan="7">
										基本信息
									</TD>
								</TR>
								<tr>
									<td align="right" class="pg_add_head">
										姓名:
									</td>
									<td width="15%" class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;"> <s:property value="elUser.realname" /> 
									</td>
									<td width="15%" align="right" class="pg_add_head">
										有效身份证件类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhengjianleixing">  
												<option value="身份证">身份证</option> 
										</select>
									</td>
									<td width="19%" align="right" class="pg_add_head">
										有效身份证件号码:
									</td>
									<td width="19%" colspan="2" class="pg_add_content"> 
										 <s:property value="elUser.shenfenzheng"/> 
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										出生日期:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										 <s:date format="yyyy-MM-dd " name="elUser.shengri"/>
									</td>
									<td align="right" class="pg_add_head">
										会计证号:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										 <s:property value="elUser.kuaijihao"/> 
									</td>
									<td align="right" class="pg_add_head">
										会计证发证日期:
									</td>
									<td colspan="2" class="pg_add_content"> <s:property value="elUser.kuaijizhengfazhengriqi"/> 
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										会计证发证机关:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										 <s:property value="elUser.kuaijizhengfazhengjiguan"/> 
									</td>
									<td align="right" class="pg_add_head">
										有效期:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										 <s:property value="elUser.kuaijizhengyouxiaoqi"/> 
									</td>
									<td align="right" class="pg_add_head">
										性别:
									</td>
									<td colspan="2" class="pg_add_content">
										<s:property value ="elUser.sex" />
									</td>
								</tr>
								<!---------------辅助信息---------------------->
								<TR>
									<TD class="pg_add_title" colspan="7">
										辅助信息
									</TD>
								</TR>
								<TR>
									<TD class="pg_add_title" align="center">
										名称
									</TD>
									<TD class="pg_add_title" align="center">
										原信息
									</TD>
									<TD class="pg_add_title" align="center">
										新信息
									</TD>
									<TD class="pg_add_title" align="center">
										名称
									</TD>
									<TD class="pg_add_title" align="center">
										原信息
									</TD>
									<TD colspan="2" align="center" class="pg_add_title">
										新信息
									</TD>
								</TR>
								<tr>
									<td align="right" class="pg_add_head">
										培训类别:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.peixunleibie" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										民族:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.minzu" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										开始从事会计工作时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.kaishikuaijishijian" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										政治面貌:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhengzhi" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>

								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.xueli" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										会计专业技术职务:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.kuaijizhuanyejishuzhiwu" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学位:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.xuewei" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										会计专业技术职务聘任时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										&nbsp;
										<s:property value="elUser.kuaijizhuanyejishuzhiwuriqi"/>
									</td>
									<td colspan="2" class="pg_add_content">
									</td>

								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历毕业院校:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.biyeyuanxiao" />

									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										个人电话（手机）:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.phone" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历毕业时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										
										<s:date format="yyyy-MM-dd " name="elUser.biyeshijian"/>
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										电子邮箱:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.email" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历所学专业:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.suoxuezhuanye" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										人员状态:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.shifouzaizhi" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>

									<td align="right" class="pg_add_head">
										非全日制最高学历:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feixueli" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										工作单位全称:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.danwei" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制最高学位:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feixuewei" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										工作单位电话:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.lianxifangshi" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制学历（学位）取得时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feibiyeshijian" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										工作单位地址:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.danweiaddress" />

									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制学历（学位）授予院校:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feibiyeyuanxiao" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										工作单位经济类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.danweileixing" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制学历（学位）所学专业:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feisuoxuezhuanye" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										现从事会计工作岗位:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.suozaigangwei" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										会计行政职务:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.kuaijixingzhengzhiwu" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head">
										现所在地行政区划:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.suozaidixingzhengqu" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigeleixing" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td rowspan="9" align="right" class="pg_add_head">
										照片:
									</td>
									<td rowspan="9" class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;" align="center">
										<img id="head" width="120px" height="150px"
				src="elheaders/<s:property value="#session.userId"/>.<s:property value="elUser.headPhoto"/>"
				align="top"><br>
			<!--<a style="clear: both;" href="student_headerAlterInit.action">修改</a>-->
									</td>
									<td rowspan="8" class="pg_add_content">
										<div id="preview"
											style="filter: progid :                             DXImageTransform .                             Microsoft .                             AlphaImageLoader(sizingMethod =                             image); width: 114px; height: 156px;">
										</div>
									</td>
									<td rowspan="8" class="pg_add_content" width="90">
										<font style="font-size: 12px; color: #990000"> </font>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格级别:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigejibie" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格取得方式:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigehuoqufangshi" />

									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格取得时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigehuoquriqi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格证书号或批文号:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigezhengshu" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否注册会计师:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhucekuaijishi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否注册评估师:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhucepinggushi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否注册税务师:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuceshuiwushi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否高端人才:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.gaoduanrencai" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>

									<td align="right" class="pg_add_head">
										高端人才类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.gaoduanrencaileixing" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>


									<td align="right" class="pg_add_head" id="zctexttd">
										角色权限:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.role.name" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>

								</tr>

								<tr>

									<td align="right" class="pg_add_head">
										高端人才资格取得时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.gaoduanrencairiqi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">


									</td>

									<td align="right" class="pg_add_head" id="zctexttd">
										开通状态:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.validName" />
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										备注:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.beizhu" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td align="right" class="pg_add_head" id="zctexttd">
										所属部门:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.company.name"/>/<s:property value="elUser.department.name"/>
									</td>
									<td colspan="2" class="pg_add_content">
									</td>

								</tr>
								<tr>

									<td align="right" class="pg_add_head">
										用户名:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.username" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.username" />
									</td>


									<td align="right" class="pg_add_head" id="zctexttd">
										密码:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<!--<s:property value="elUser.password" />-->
									</td>
									<td colspan="2" class="pg_add_content">
									</td>
								</tr>
								<TR class="pg_add_bottom">
									<TD colspan="7">
										<DIV align="center">
											<!--<a href="student_myalterInit.action">修改</a>-->
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
		</div>
	</BODY>
</HTML>
