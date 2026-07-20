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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<span style="font-weight: bold;">学员统计</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 40px; margin-left: 40px;">
			<s:form action="stat_talent_searchlist" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" align="left" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF">
							<strong>单位名称：</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<s:property value="company.name" />
							<s:hidden name="company.id" />
							<s:hidden name="pN" value="0" />
							<s:hidden name="pS" value="10" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							所属部门：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<select style="width: 300px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong>包含下属部门</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong>用户名</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong>姓    名</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<!-- <tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong>职称 </strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>	<SELECT name="elUser.zhichengleibie" >
									<option value="">全部</option>
								<option value="会计师">会计师</option> 
								<option value="审计师">审计师</option>
								<option value="经济师">经济师</option>
							</SELECT> 
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong> 职务 </strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
							<SELECT name="elUser.renyuanleibie" >
								<option value="">全部</option>
								<option value="会计机构负责人">会计机构负责人</option>
								<option value="会计主管人员">会计主管人员</option>
								<option value="总会计师">总会计师</option>
								<option value="会计">会计</option>
								<option value="出纳">出纳</option>
								<option value="其他">其他</option>
								<option value="无">无</option>
						</SELECT>
							</label>
						</td>
					</tr> -->
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong> 岗位 </strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<SELECT name="elUser.gangwei" >
								<option value="">全部</option>
											<option
												<s:if test="elUser.kuaijixingzhengzhiwu=='会计机构负责人(会计主管)'">selected='selected'</s:if>
												value="会计机构负责人(会计主管)">
												会计机构负责人(会计主管)
											</option>
											<option
												<s:if test="elUser.gangwei=='出纳'">selected='selected'</s:if>
												value="出纳">
												出纳
											</option>
											<option
												<s:if test="elUser.gangwei=='稽核'">selected='selected'</s:if>
												value="稽核">
												稽核
											</option>
											<option
												<s:if test="elUser.gangwei=='资本、基金核算'">selected='selected'</s:if>
												value="资本、基金核算">
												资本、基金核算
											</option>
											<option
												<s:if test="elUser.gangwei=='收入、支出、债权、债务核算'">selected='selected'</s:if>
												value="收入、支出、债权、债务核算">
												收入、支出、债权、债务核算
											</option>
											<option
												<s:if test="elUser.gangwei=='工资核算、成本费用核算、财务成果核算'">selected='selected'</s:if>
												value="工资核算、成本费用核算、财务成果核算">
												工资核算、成本费用核算、财务成果核算
											</option>
											<option
												<s:if test="elUser.gangwei=='财产物资的收发、增减核算'">selected='selected'</s:if>
												value="财产物资的收发、增减核算">
												财产物资的收发、增减核算
											</option>
											<option
												<s:if test="elUser.gangwei=='总账'">selected='selected'</s:if>
												value="总账">
												总账
											</option>
											<option
												<s:if test="elUser.gangwei=='财务会计报告编制'">selected='selected'</s:if>
												value="财务会计报告编制">
												财务会计报告编制
											</option>
											<option
												<s:if test="elUser.gangwei=='会计机构内档案管理'">selected='selected'</s:if>
												value="会计机构内档案管理">
												会计机构内档案管理
											</option>
											<option
												<s:if test="elUser.gangwei=='其他'">selected='selected'</s:if>
												value="其他">
												其他
											</option>
											<option
												<s:if test="elUser.gangwei=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
						</SELECT>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong> 会计证号</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="text" name="elUser.kuaijihao" id="email" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong> 性别 </strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
									<SELECT name="elUser.sex">
							<option value="">全部</option>
								<option value="男">男</option>
								<option value="女">女</option>
							</SELECT>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong> 培训类别</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label> 
							<select name="elUser.peixunleibie"> 
								<s:iterator value="elUser.peixunleibies" status="pxlb">
								<option value="">全部</option>
									<option
										<s:if test="elUser.peixunleibie==elUser.peixunleibies[#pxlb.index]">selected = 'selected'</s:if>
										value="<s:property />">
										<s:property />
									</option>
								</s:iterator>
							</select>
							</label>
						</td> 
					</tr>
					<tr>
						<td>选择培训班</td>
						<td>
							<div id="PXB" style="display: none;width: 340px;"></div>
							<input type="button" onClick="searchElclassUser_RCCX()" value="添加">
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#FFFFFF">
							<strong>是否通过培训班</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
							<SELECT name="elUser.major">
							<option value="">全部</option>
							<option value="1">是</option>
							<option value="0">否</option></SELECT>
							</label>
						</td>
					</tr>
					<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						<strong>民族</strong>
					</td>
					<td height="30" align="left" bgcolor="#FFFFFF">
						<label>
										<select name="elUser.minzu">
											<option value="">全部</option>
											<option
												<s:if test="elUser.minzu=='汉族'">selected='selected'</s:if>
												value="汉族">
												汉族
											</option>
											<option
												<s:if test="elUser.minzu=='蒙古族'">selected='selected'</s:if>
												value="蒙古族">
												蒙古族
											</option>
											<option
												<s:if test="elUser.minzu=='回族'">selected='selected'</s:if>
												value="回族">
												回族
											</option>
											<option
												<s:if test="elUser.minzu=='藏族'">selected='selected'</s:if>
												value="藏族">
												藏族
											</option>
											<option
												<s:if test="elUser.minzu=='维吾尔族'">selected='selected'</s:if>
												value="维吾尔族">
												维吾尔族
											</option>
											<option
												<s:if test="elUser.minzu=='苗族'">selected='selected'</s:if>
												value="苗族">
												苗族
											</option>
											<option
												<s:if test="elUser.minzu=='彝族'">selected='selected'</s:if>
												value="彝族">
												彝族
											</option>
											<option
												<s:if test="elUser.minzu=='壮族'">selected='selected'</s:if>
												value="壮族">
												壮族
											</option>
											<option
												<s:if test="elUser.minzu=='布依族'">selected='selected'</s:if>
												value="布依族">
												布依族
											</option>
											<option
												<s:if test="elUser.minzu=='朝鲜族'">selected='selected'</s:if>
												value="朝鲜族">
												朝鲜族
											</option>
											<option
												<s:if test="elUser.minzu=='满族'">selected='selected'</s:if>
												value="满族">
												满族
											</option>
											<option
												<s:if test="elUser.minzu=='侗族'">selected='selected'</s:if>
												value="侗族">
												侗族
											</option>
											<option
												<s:if test="elUser.minzu=='瑶族'">selected='selected'</s:if>
												value="瑶族">
												瑶族
											</option>
											<option
												<s:if test="elUser.minzu=='白族'">selected='selected'</s:if>
												value="白族">
												白族
											</option>
											<option
												<s:if test="elUser.minzu=='土家族'">selected='selected'</s:if>
												value="土家族">
												土家族
											</option>
											<option
												<s:if test="elUser.minzu=='哈尼族'">selected='selected'</s:if>
												value="哈尼族">
												哈尼族
											</option>
											<option
												<s:if test="elUser.minzu=='哈萨克族'">selected='selected'</s:if>
												value="哈萨克族">
												哈萨克族
											</option>
											<option
												<s:if test="elUser.minzu=='傣族'">selected='selected'</s:if>
												value="傣族">
												傣族
											</option>
											<option
												<s:if test="elUser.minzu=='黎族'">selected='selected'</s:if>
												value="黎族">
												黎族
											</option>
											<option
												<s:if test="elUser.minzu=='傈僳族'">selected='selected'</s:if>
												value="傈僳族">
												傈僳族
											</option>
											<option
												<s:if test="elUser.minzu=='佤族'">selected='selected'</s:if>
												value="佤族">
												佤族
											</option>
											<option
												<s:if test="elUser.minzu=='畲族'">selected='selected'</s:if>
												value="畲族">
												畲族
											</option>
											<option
												<s:if test="elUser.minzu=='高山族'">selected='selected'</s:if>
												value="高山族">
												高山族
											</option>
											<option
												<s:if test="elUser.minzu=='拉祜族'">selected='selected'</s:if>
												value="拉祜族">
												拉祜族
											</option>
											<option
												<s:if test="elUser.minzu=='水族'">selected='selected'</s:if>
												value="水族">
												水族
											</option>
											<option
												<s:if test="elUser.minzu=='东乡族'">selected='selected'</s:if>
												value="东乡族">
												东乡族
											</option>
											<option
												<s:if test="elUser.minzu=='纳西族'">selected='selected'</s:if>
												value="纳西族">
												纳西族
											</option>
											<option
												<s:if test="elUser.minzu=='景颇族'">selected='selected'</s:if>
												value="景颇族">
												景颇族
											</option>
											<option
												<s:if test="elUser.minzu=='柯尔克孜族'">selected='selected'</s:if>
												value="柯尔克孜族">
												柯尔克孜族
											</option>
											<option
												<s:if test="elUser.minzu=='土族'">selected='selected'</s:if>
												value="土族">
												土族
											</option>
											<option
												<s:if test="elUser.minzu=='达斡尔族'">selected='selected'</s:if>
												value="达斡尔族">
												达斡尔族
											</option>
											<option
												<s:if test="elUser.minzu=='仫佬族'">selected='selected'</s:if>
												value="仫佬族">
												仫佬族
											</option>
											<option
												<s:if test="elUser.minzu=='羌族'">selected='selected'</s:if>
												value="羌族">
												羌族
											</option>
											<option
												<s:if test="elUser.minzu=='布朗族'">selected='selected'</s:if>
												value="布朗族">
												布朗族
											</option>
											<option
												<s:if test="elUser.minzu=='撒拉族'">selected='selected'</s:if>
												value="撒拉族">
												撒拉族
											</option>
											<option
												<s:if test="elUser.minzu=='毛难族'">selected='selected'</s:if>
												value="毛难族">
												毛难族
											</option>
											<option
												<s:if test="elUser.minzu=='仡佬族'">selected='selected'</s:if>
												value="仡佬族">
												仡佬族
											</option>
											<option
												<s:if test="elUser.minzu=='锡伯族'">selected='selected'</s:if>
												value="锡伯族">
												锡伯族
											</option>
											<option
												<s:if test="elUser.minzu=='阿昌族'">selected='selected'</s:if>
												value="阿昌族">
												阿昌族
											</option>
											<option
												<s:if test="elUser.minzu=='普米族'">selected='selected'</s:if>
												value="普米族">
												普米族
											</option>
											<option
												<s:if test="elUser.minzu=='塔吉克族'">selected='selected'</s:if>
												value="塔吉克族">
												塔吉克族
											</option>
											<option
												<s:if test="elUser.minzu=='怒族'">selected='selected'</s:if>
												value="怒族">
												怒族
											</option>
											<option
												<s:if test="elUser.minzu=='乌孜别克族'">selected='selected'</s:if>
												value="乌孜别克族">
												乌孜别克族
											</option>
											<option
												<s:if test="elUser.minzu=='俄罗斯族'">selected='selected'</s:if>
												value="俄罗斯族">
												俄罗斯族
											</option>
											<option
												<s:if test="elUser.minzu=='鄂温克族'">selected='selected'</s:if>
												value="鄂温克族">
												鄂温克族
											</option>
											<option
												<s:if test="elUser.minzu=='崩龙族'">selected='selected'</s:if>
												value="崩龙族">
												崩龙族
											</option>
											<option
												<s:if test="elUser.minzu=='保安族'">selected='selected'</s:if>
												value="保安族">
												保安族
											</option>
											<option
												<s:if test="elUser.minzu=='裕固族'">selected='selected'</s:if>
												value="裕固族">
												裕固族
											</option>
											<option
												<s:if test="elUser.minzu=='京族'">selected='selected'</s:if>
												value="京族">
												京族
											</option>
											<option
												<s:if test="elUser.minzu=='塔塔尔族'">selected='selected'</s:if>
												value="塔塔尔族">
												塔塔尔族
											</option>
											<option
												<s:if test="elUser.minzu=='独龙族'">selected='selected'</s:if>
												value="独龙族">
												独龙族
											</option>
											<option
												<s:if test="elUser.minzu=='鄂伦春族'">selected='selected'</s:if>
												value="鄂伦春族">
												鄂伦春族
											</option>
											<option
												<s:if test="elUser.minzu=='赫哲族'">selected='selected'</s:if>
												value="赫哲族">
												赫哲族
											</option>
											<option
												<s:if test="elUser.minzu=='门巴族'">selected='selected'</s:if>
												value="门巴族">
												门巴族
											</option>
											<option
												<s:if test="elUser.minzu=='珞巴族'">selected='selected'</s:if>
												value="珞巴族">
												珞巴族
											</option>
											<option
												<s:if test="elUser.minzu=='基诺族'">selected='selected'</s:if>
												value="基诺族">
												基诺族
											</option>
											<option
												<s:if test="elUser.minzu=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
										</select>
						</label>
					</td>
				</tr>
				<tr>
							<td>
								<strong> 权限</strong>
							</td>
							<td height="30" align="left" bgcolor="#FFFFFF">
								<!--<label>
									<input type="checkbox"
										<s:if test="elUser.role.id==3">checked='checked'</s:if>
										name="is_teacher" value="1" />
								</label>
							-->
							<select name="elUser.role.id">
								<option value="">全部</option>
								<s:iterator value="roles">
									<option <s:if test="id==elUser.role.id">selected='selected'</s:if> value="<s:property value="id"/>"><s:property value="name"/></option>
								</s:iterator>
							</select>
							</td>
						</tr>
						<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						<strong>人员状态:</strong>
					</td>
					<td height="30" style="display: " align="left" bgcolor="#FFFFFF">
						<label> 
						<select name="elUser.shifouzaizhi"> 
							<s:iterator value="elUser.shifouzaizhis" status="sfzz">
								<option value="">全部</option>
								<option
									<s:if test="elUser.shifouzaizhi==elUser.shifouzaizhis[#sfzz.index]">selected = 'selected'</s:if>
									value="<s:property />">
									<s:property />
								</option>
							</s:iterator>
						</select> 
						</label>
					</td>
				</tr>
				<!-- <tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						<strong>职称级别</strong>
					</td>
					<td height="30" align="left" bgcolor="#FFFFFF">
						<label>
							<SELECT name="elUser.zhichengjibie" >
								<option value="">全部</option>
								<option <s:if test="elUser.zhichengjibie=='初级'">selected='selected'</s:if> value="初级">初级</option>
								<option <s:if test="elUser.zhichengjibie=='中级'">selected='selected'</s:if> value="中级">中级</option>
								<option <s:if test="elUser.zhichengjibie=='高级'">selected='selected'</s:if> value="高级">高级</option>
								<option <s:if test="elUser.zhichengjibie=='其他'">selected='selected'</s:if> value="其他">其他</option>
							</SELECT> 
						</label>
					</td>
				</tr> -->
				<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						<strong>学历</strong>
					</td>
					<td height="30" align="left" bgcolor="#FFFFFF">
						<label>
							<select name="elUser.xueli">
								<option value="">全部</option>
											<option
												<s:if test="elUser.xueli=='初中及以下'">selected='selected'</s:if>
												value="初中及以下">
												初中及以下
											</option>
											<option
												<s:if test="elUser.xueli=='高中'">selected='selected'</s:if>
												value="高中">
												高中
											</option>
											<option
												<s:if test="elUser.xueli=='中专'">selected='selected'</s:if>
												value="中专">
												中专
											</option>
											<option
												<s:if test="elUser.xueli=='大专'">selected='selected'</s:if>
												value="大专">
												大专
											</option>
											<option
												<s:if test="elUser.xueli=='本科'">selected='selected'</s:if>
												value="本科">
												本科
											</option>
											<option
												<s:if test="elUser.xueli=='硕士研究生'">selected='selected'</s:if>
												value="硕士研究生">
												硕士研究生
											</option>
											<option
												<s:if test="elUser.xueli=='博士研究生'">selected='selected'</s:if>
												value="博士研究生">
												博士研究生
											</option>
											<option
												<s:if test="elUser.xueli=='无学历'">selected='selected'</s:if>
												value="无学历">
												无学历
											</option>
						</select> 
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						<strong>学位</strong>
					</td>
					<td height="30" align="left" bgcolor="#FFFFFF">
						<label>
							
							<SELECT name="elUser.xuewei">
								<option value="">全部</option>
											<option
												<s:if test="elUser.xuewei=='学士'">selected='selected'</s:if>
												value="学士">
												学士
											</option>
											<option
												<s:if test="elUser.xuewei=='双学士'">selected='selected'</s:if>
												value="双学士">
												双学士
											</option>
											<option
												<s:if test="elUser.xuewei=='硕士'">selected='selected'</s:if>
												value="硕士">
												硕士
											</option>
											<option
												<s:if test="elUser.xuewei=='博士'">selected='selected'</s:if>
												value="博士">
												博士
											</option>
											<option
												<s:if test="elUser.xuewei=='无学位'">selected='selected'</s:if>
												value="无学位">
												无学位
											</option>
						</SELECT>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						<strong>年龄段</strong>
					</td>
					<td height="30" align="left" bgcolor="#FFFFFF"> 
						<input type="text" name="elUser.age" id="age" value="">~
						<input type="text" name="elUser.age_" id="age_" value="">
					</td>
				</tr>
					<tr>
						<td width="120" height="50" align="center" bgcolor="#FFFFFF">
							&nbsp;
						</td>
						<td bgcolor="#FFFFFF">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50">
										<input type="submit" value="搜索">
									</td>
									<td width="20">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
