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
		<style type="text/css">
td {
	font-size: 11px;
}
</style>
	</HEAD>
	<BODY style="overflow: scroll;">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">用户统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div  >
			<form action="stat_talent_searchlist.action" method="post"
				name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.zhichengleibie" />
				<s:hidden name="elUser.renyuanleibie" />
				<s:hidden name="elUser.suozaigangwei" />
				<s:hidden name="elUser.kuaijihao" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.peixunleibie" /> 
				<s:hidden name="elClasss[0].id" />
				<s:hidden name="elUser.major" /> 
				<s:hidden name="elUser.minzu" />
				<s:hidden name="elUser.role.id" />
				<s:hidden name="elUser.shifouzaizhis" />
				<s:hidden name="elUser.xueli" />
				<s:hidden name="elUser.xuewei" />
				<s:hidden name="elUser.age" />
				<s:hidden name="elUser.age_" />
			</form> 
			<script type="text/javascript">
													 	function page(i){
													 		document.getElementById("pageNow").value=i;
													 		acc_list.action="stat_talent_searchlist.action";
													 		acc_list.submit();
													 	}
													 	function toexcel(){
													 		acc_list.action="stat_talent_list.action";
													 		acc_list.submit();
													 	}
													</script>
			<table cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td   bgcolor="#FFFFFF">
						<strong>所属单位</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>所属部门</strong>
					</td>
					<!--<td   bgcolor="#FFFFFF">
						<strong>用户名</strong>
					</td>
					--><td   bgcolor="#FFFFFF">
						<strong>身份证号</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>会计证号</strong>
					</td>

					<td   bgcolor="#FFFFFF">
						<strong>姓 名</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>年 龄</strong>
					</td>

					<!--<td   bgcolor="#FFFFFF">
						<strong>学号</strong>
					</td>
					--><td   bgcolor="#FFFFFF">
						<strong>民族</strong>
					</td>
					<!--<td   bgcolor="#FFFFFF">
						<strong>角色</strong>
					</td>
					--><td   bgcolor="#FFFFFF">
						<strong>培训类别</strong>
					</td>
					<!--<td   bgcolor="#FFFFFF">
						<strong>备注</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>地 址</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>邮 编</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>电话</strong>
					</td>-->
					<td   bgcolor="#FFFFFF">
						<strong>手机</strong>
					</td>

					<!--<td   bgcolor="#FFFFFF">
						<strong>电子邮箱</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>QQ/MSN</strong>
					</td>
					--><td   bgcolor="#FFFFFF">
						<strong>毕业院校</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>毕业时间</strong>
					</td>

					<td   bgcolor="#FFFFFF">
						<strong>所学专业</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>学历</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>学位</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>是否在职</strong>
					</td>
					<!-- <td   bgcolor="#FFFFFF">
						<strong>职务</strong>
					</td> -->
					<td   bgcolor="#FFFFFF">
						<strong>岗位</strong>
					</td>
					<!-- <td   bgcolor="#FFFFFF">
						<strong>职称类别</strong>
					</td>
					<td   bgcolor="#FFFFFF">
						<strong>职称级别</strong>
					</td> -->
					<!--<td   bgcolor="#FFFFFF">
						<strong> 职称取得日期</strong>
					</td>

					<td   bgcolor="#FFFFFF">
						<strong>职务聘任日期</strong>
					</td>

					<td   bgcolor="#FFFFFF">
						<strong>职称证号</strong>
					</td>-->
					<td   bgcolor="#FFFFFF">
						<strong>是否通过</strong>
					</td>
				</tr>
				<s:iterator value="elUsers" status="st">
					<tr>
						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="company.name" />
							</label>
						</td>


						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="department.name" />
							</label>
						</td>


						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="shenfenzheng" /> 
							</label>
						</td>


						<!--<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="studentno" />
							</label>
						</td>


						--><td   bgcolor="#FFFFFF">
							<label>
								<s:property value="kuaijihao" />
							</label>
						</td>

						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="realname" />
							</label>
						</td>
						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="aGE" />
							</label>
						</td> 
						<!-- <td   bgcolor="#FFFFFF">
							<label>

								<s:property value="username" />
							</label>
						</td> -->

						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="minzu" />
							</label>
						</td>
						<!--<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="role.name" />
							</label>
						</td>



						--><td   bgcolor="#FFFFFF">
							<label>
								<s:property value="peixunleibie" />
							</label>
						</td>


						<!--<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="beizhu" />
							</label>
						</td>



						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="address" />
							</label>
						</td>


						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="major" />
							</label>
						</td>


						--><td   bgcolor="#FFFFFF">
							<label>
								<s:property value="lianxifangshi" />
							</label>
						</td>


						<!--<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="phone" />
							</label>
						</td>

						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="email" />
							</label>
						</td>


						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="studyDir" />
							</label>
						</td>



						--><td   bgcolor="#FFFFFF">
							<label>
								<s:property value="biyeyuanxiao" />
							</label>
						</td>

						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="biyeshijian" />
							</label>
						</td>


						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="suoxuezhuanye" />
							</label>
						</td>


						<td   bgcolor="#FFFFFF">
							<label>

								<s:property value="xueli" />
							</label>
						</td>


						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="xuewei" />
							</label>
						</td>



						<td  style="display: "  bgcolor="#FFFFFF">
							<label>
								<s:property value="shifouzaizhi" />
							</label>
						</td>

						<!-- <td   bgcolor="#FFFFFF">
							<label>
								<s:property value="renyuanleibie" />
							</label>
						</td> --> 
						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="suozaigangwei" />
							</label>
						</td> 
						<!-- <td   bgcolor="#FFFFFF">
							<label>

								<s:property value="zhichengleibie" />
							</label>
						</td> 
						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="zhichengjibie" />
							</label>
						</td>  
						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="zhichengquderiqi" />
							</label>
						</td> 
						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="zhiwupinrenriqi" />
							</label>
						</td> 
						<td   bgcolor="#FFFFFF">
							<label>
								<s:property value="zhichenghao" />
							</label>
						</td>-->
						<td   bgcolor="#FFFFFF"> 
							<s:if test="ispassed == 1">
								<span style="color:red">通过</span>
							</s:if><s:elseif test="ispassed == -1">
								<label>无班</label> 
							</s:elseif><s:else>
								<label>未通过</label> 
							</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
			<br>
			<wysLib:page></wysLib:page>
				<a target="" href="javascript:toexcel();">导出列表</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
