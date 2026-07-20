<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel" %> 
<% 
    //就是靠这一行，让前端浏览器以为接收到一个excel档   
     response.setHeader("Content-disposition","attachment; filename=xxx.xls"); 
%> 

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
			<table cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td   >
						<strong>所属单位</strong>
					</td>
					<td   >
						<strong>所属部门</strong>
					</td>
					<!--<td   >
						<strong>用户名</strong>
					</td>
					--><td   >
						<strong>身份证号</strong>
					</td>
					<td   >
						<strong>会计证号</strong>
					</td>

					<td   >
						<strong>姓 名</strong>
					</td>

					<!--<td   >
						<strong>学号</strong>
					</td>
					--><td   >
						<strong>民族</strong>
					</td>
					<!--<td   >
						<strong>角色</strong>
					</td>
					--><td   >
						<strong>培训类别</strong>
					</td>
					<!--<td   >
						<strong>备注</strong>
					</td>
					<td   >
						<strong>地 址</strong>
					</td>
					<td   >
						<strong>邮 编</strong>
					</td>-->
					<td   >
						<strong>电话</strong>
					</td>
					<td   >
						<strong>手机</strong>
					</td>

					<!--<td   >
						<strong>电子邮箱</strong>
					</td>
					<td   >
						<strong>QQ/MSN</strong>
					</td>
					--><td   >
						<strong>毕业院校</strong>
					</td>
					<td   >
						<strong>毕业时间</strong>
					</td>

					<td   >
						<strong>所学专业</strong>
					</td>
					<td   >
						<strong>学历</strong>
					</td>
					<td   >
						<strong>学位</strong>
					</td>
					<td   >
						<strong>是否在职</strong>
					</td>
					<td   >
						<strong>职务</strong>
					</td>
					<td   >
						<strong>岗位</strong>
					</td>
					<td   >
						<strong>职称类别</strong>
					</td>
					<td   >
						<strong>职称级别</strong>
					</td>
					<!--<td   >
						<strong> 职称取得日期</strong>
					</td>

					<td   >
						<strong>职务聘任日期</strong>
					</td>

					--><td   >
						<strong>职称证号</strong>
					</td>
				</tr>
				<s:iterator value="elUsers" status="st">
					<tr>
						<td   >
							<label>
								<s:property value="company.name" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="department.name" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="username" />
							</label>
						</td>


						<!--<td   >
							<label>
								<s:property value="studentno" />
							</label>
						</td>


						--><td   >
							<label>
								<s:property value="kuaijihao" />
							</label>
						</td>

						<td   >
							<label>
								<s:property value="realname" />
							</label>
						</td>


						<td   >
							<label>

								<s:property value="username" />
							</label>
						</td>

						<td   >
							<label>
								<s:property value="minzu" />
							</label>
						</td>
						<!--<td   >
							<label>
								<s:property value="role.name" />
							</label>
						</td>



						--><td   >
							<label>
								<s:property value="peixunleibie" />
							</label>
						</td>


						<!--<td   >
							<label>
								<s:property value="beizhu" />
							</label>
						</td>



						<td   >
							<label>
								<s:property value="address" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="major" />
							</label>
						</td>


						--><td   >
							<label>
								<s:property value="lianxifangshi" />
							</label>
						</td>


						<!--<td   >
							<label>
								<s:property value="phone" />
							</label>
						</td>

						<td   >
							<label>
								<s:property value="email" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="studyDir" />
							</label>
						</td>



						--><td   >
							<label>
								<s:property value="biyeyuanxiao" />
							</label>
						</td>

						<td   >
							<label>
								<s:property value="biyeshijian" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="suoxuezhuanye" />
							</label>
						</td>


						<td   >
							<label>

								<s:property value="xueli" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="xuewei" />
							</label>
						</td>



						<td  style="display: "  >
							<label>
								<s:property value="shifouzaizhi" />
							</label>
						</td>

						<td   >
							<label>
								<s:property value="renyuanleibie" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="suozaigangwei" />
							</label>
						</td>



						<td   >
							<label>

								<s:property value="zhichengleibie" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="zhichengjibie" />
							</label>
						</td><!--


						<td   >
							<label>
								<s:property value="zhichengquderiqi" />
							</label>
						</td>


						<td   >
							<label>
								<s:property value="zhiwupinrenriqi" />
							</label>
						</td>


						--><td   >
							<label>
								<s:property value="zhichenghao" />
							</label>
						</td>
					</tr>
				</s:iterator>
			</table>
			<br>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
