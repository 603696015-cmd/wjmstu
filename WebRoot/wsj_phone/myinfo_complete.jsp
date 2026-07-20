
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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>基本信息填写</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<style type="text/css">
			caption {
				font: bolder 14px red; text-align: left;
			}
		</style>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#studentno").val()==''){
					alert("请输入身份证号");
					$("#studentno" ).focus();
					return false;
				}
				if($("#minzu").val()==''){
					alert("请输入民族");
					$("#minzu" ).focus();
					return false;
				}
				if($("#beizhu").val()==''){
					alert("请输入备注");
					$("#beizhu" ).focus();
					return false;
				}
				 if($("#address").val()==''){
					alert("请输入地址");
					$("#address" ).focus();
					return false;
				} 
 				if($("#major" ).val()==''){
					alert("请输入邮编");
					$("#major" ).focus();
					return false;
				}
 				if($("#lianxifangshi" ).val()==''){
					alert("请输入电话");
					$("#lianxifangshi" ).focus();
					return false;
				}  
 				if($("#phone"  ).val()==''){
					alert("请输入电话（手机）");
					$("#phone" ).focus();
					return false;
				}
 				if($("#email" ).val()==''){
					alert("请输入e_mail");
					$("#email" ).focus();
					return false;
				}
				if($("#studydir" ).val()==''){
				
					alert("请输入Msn/Qq");
					$("#studydir" ).focus();
					return false;
				}
				if($("#biyeyuanxiao"  ).val()==''){
				
					alert("毕业院校");
					$("#biyeyuanxiao" ).focus();
					return false;
				}
  				if($("#biyeshijian").val()==''){
				
					alert("请输入毕业时间");
					$("#biyeshijian" ).focus();
					return false;
				}
  				if($("#suoxuezhuanye"  ).val()==''){
				
					alert("请输入所学专业");
					$("#suoxuezhuanye" ).focus();
					return false;
				}
				return true;
			}
		</script>
	</HEAD>
	<BODY onLoad="">
		<ul class="nav">
			<li>
				<span style="font-weight: bold;">您的账号是 准开放状态，如需开通请填写完整如下信息</span>
			</li>
		</ul>
		<!-- 内容 -->
		<table width="970" align="center" height="450px" cellpadding="1" cellspacing="1">
			<tr>
				<td valign="top">
					<s:form onsubmit="return _onsubmit()" action="myinfo_complete" method="post" theme="simple">
						<div>

							<a href="javascript:showInfo(1)" style="color: blue;font-size: 14;font-weight:bolder;">基本信息</a>
							<a href="javascript:showInfo(4)" style="color: blue;font-size: 14;font-weight:bolder;">联系方式</a>
							<!--
							<a href="javascript:showInfo(3)" style="color: blue;font-size: 14;font-weight:bolder;">工作经历</a>
							-->
							<a href="javascript:showInfo(2)" style="color: blue;font-size: 14;font-weight:bolder;">学习经历</a>
							<table id="info1" width="100%" cellpadding="1" cellspacing="1"
								bgcolor="#ECEDEB">
								<caption>
									基本信息
								</caption>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>所属单位/部门</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:property value="elUser.department.name" />
											<s:hidden name="elUser.department.id"></s:hidden>
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>用户名</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:property value="elUser.username" />
										</label>
									</td>
								</tr>
								<!--<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>学号</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:property value="elUser.xuehao" />
										</label>
									</td>
								</tr>
								--><tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>姓 名</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield name="elUser.realname" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>性别</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:radio name="elUser.sex" value="m"
												list="#{'m':'男','f':'女'}" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>身份证</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<input name="elUser.studentno" id="studentno"
												value="<s:property value="elUser.username"/>" />
										</label>
									</td>
								</tr>
								<!--<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>会计证号</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield name="elUser.kuaijihao" />
										</label>
									</td>
								</tr>
								--><tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>民族</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<SELECT id="minzu" name="elUser.minzu">
												<option value="汉族">
													汉族
												</option>
												<option value="满族">
													满族
												</option>
												<option value="回族">
													回族
												</option>
												<option value="蒙族">
													蒙族
												</option>
												<option value="藏族">
													藏族
												</option>
												<option value="其他">
													其他
												</option>
											</SELECT>
										</label>
									</td>
								</tr>
								<!--<tr>
					<td width="120" height="30" align="center" bgcolor="#FFFFFF">
						<strong>单位编号</strong>
					</td>
					<td height="30" align="left" bgcolor="#FFFFFF">
						<label>
							<s:textfield name="elUser.danweihao" />
						</label>
					</td>
				</tr>
				-->
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>角色</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:property value="elUser.role.name" />
										</label>
									</td>
								</tr>
								<!--<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>人员类别</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<SELECT name="elUser.peixunleibie">
												<option value="财务处科长">
													财务处科长
												</option>
												<option value="特色班培训">
													特色班培训
												</option>
												<option value="高级会计师">
													高级会计师
												</option>
												<option value="在线学习人员">
													在线学习人员
												</option>
												<option value="其他人员">
													其他人员
												</option>
											</SELECT>
											<s:textfield name="elUser.peixunleibie" />
						
										</label>
									</td>
								</tr>-->
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>备注</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="beizhu" name="elUser.beizhu" />
										</label>
									</td>
								</tr>
							</table>
							<table id="info4" width="100%" cellpadding="1" cellspacing="1"
								bgcolor="#ECEDEB">
							<caption>
									联系方式
								</caption>

								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>地 址</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="address" name="elUser.address" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>邮 编</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="major" name="elUser.major" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>电话</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="lianxifangshi"  name="elUser.lianxifangshi" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>手机</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="phone" name="elUser.phone" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>电子邮箱</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="email" name="elUser.email" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>QQ/MSN</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="studydir" name="elUser.studyDir" />
										</label>
									</td>
								</tr>
							</table>
							<table id="info2" width="100%" cellpadding="1" cellspacing="1"
								bgcolor="#ECEDEB">
							<caption>
									学习经历
								</caption>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>毕业院校</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="biyeyuanxiao" name="elUser.biyeyuanxiao" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>毕业时间</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<input type="text" id="biyeshijian"
												value="<s:date format="yyyy-MM-dd" name="elUser.biyeshijian"/>"
												onclick="setday(this)" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>所学专业</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield id="suoxuezhuanye" name="elUser.suoxuezhuanye" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>学历</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<select name="elUser.xueli">
												<option value="">
													高中
												</option>
												<option value="">
													大专
												</option>
												<option value="本科">
													本科
												</option>
												<option value="研究生">
													研究生
												</option>
												<option value="其他">
													其他
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
												<option value="学士">
													学士
												</option>
												<option value="硕士">
													硕士
												</option>
												<option value="博士">
													博士
												</option>
												<option value="其他">
													其他
												</option>
											</SELECT>
										</label>
									</td>
								</tr>
							</table>
							<!--<table id="info3" width="60%" cellpadding="1" cellspacing="1"
								bgcolor="#ECEDEB">
								<caption>
									工作经历
								</caption>

								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>是否在职</strong>
									</td>
									<td height="30" style="display: " align="left"
										bgcolor="#FFFFFF">
										<label>
											<s:radio name="elUser.shifouzaizhi" list="#{'是':'是','否':'否'}" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>岗位</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<SELECT name="elUser.suozaigangwei">
												<option value="财务处长">
													财务处长
												</option>
												<option value="财务科长">
													财务科长
												</option>
												<option value="主管会计">
													主管会计
												</option>
												<option value="会计室报表">
													会计室报表
												</option>
												<option value="会计室出纳">
													会计室出纳
												</option>
												<option value="会计室记账">
													会计室记账
												</option>
												<option value="会计室审核">
													会计室审核
												</option>
												<option value="会计室制单">
													会计室制单
												</option>
												<option value="收费处收费员">
													收费处收费员
												</option>
												<option value="收费处组长">
													收费处组长
												</option>
												<option value="住院处会计">
													住院处会计
												</option>
												<option value="住院处收费员">
													住院处收费员
												</option>
												<option value="住院处组长">
													住院处组长
												</option>
												<option value="基建会计">
													基建会计
												</option>
												<option value="固定资产会计">
													固定资产会计
												</option>
												<option value="食堂会计">
													食堂会计
												</option>
												<option value="药品会计">
													药品会计
												</option>
												<option value="其他">
													其他
												</option>
											</SELECT>
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>职务</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<SELECT name="elUser.renyuanleibie">
												<option value="会计机构负责人">
													会计机构负责人
												</option>
												<option value="会计主管人员">
													会计主管人员
												</option>
												<option value="总会计师">
													总会计师
												</option>
												<option value="会计">
													会计
												</option>
												<option value="出纳">
													出纳
												</option>
												<option value="其他">
													其他
												</option>
												<option value="无">
													无
												</option>
											</SELECT>
										</label>
									</td>
								</tr>

								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>职称类别</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<SELECT name="elUser.zhichengleibie">
												<option value="会计师">
													会计师
												</option>
												<option value="审计师">
													审计师
												</option>
												<option value="经济师">
													经济师
												</option>
											</SELECT>
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>职称级别</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<SELECT name="elUser.zhichengjibie">
												<option value="初级">
													初级
												</option>
												<option value="中级">
													中级
												</option>
												<option value="高级">
													高级
												</option>
											</SELECT>
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong> 职称取得日期</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<input type="text" name="elUser.zhichengquderiqi"
												value="<s:date format="yyyy-MM-dd" name="elUser.zhichengquderiqi"/>"
												onclick="setday(this)" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong>职务聘任日期</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<input type="text" name="elUser.zhiwupinrenriqi"
												value="<s:date format="yyyy-MM-dd" name="elUser.zhiwupinrenriqi"/>"
												onclick="setday(this)" />
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" bgcolor="#FFFFFF">
										<strong> 职称证号</strong>
									</td>
									<td height="30" align="left" bgcolor="#FFFFFF">
										<label>
											<s:textfield name="elUser.zhichenghao" />
										</label>
									</td>
								</tr>
							</table>
						--></div>

						<SCRIPT type="text/javascript">
		
			function showInfo(i){	
				document.getElementById("info1").style.display="none";
				document.getElementById("info2").style.display="none";
				//document.getElementById("info3").style.display="none";
				document.getElementById("info4").style.display="none";
				document.getElementById("info"+i).style.display="block";
			}
			//if(head.fileSize<=0){
			 //head.src="elfrontimages/coursedimg.jpg";
			//}
			showInfo(1)
		</SCRIPT>
		<s:hidden name="elUser.id"></s:hidden>
					 	<input type="submit" value="提交修改"/>
					</s:form>
				</td>
			</tr>
		</table>
		<!-- 内容 -->
	
	</body>
</HTML>
