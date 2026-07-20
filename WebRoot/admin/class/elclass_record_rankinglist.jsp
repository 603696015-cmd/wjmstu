<%@ page language="java" pageEncoding="UTF-8"%> 
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>培训班积分排名统计表</TITLE>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>

		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="积分排行榜" /></div>
			</li> 
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 30px; text-align: center;">
			<s:form action="elclass_record_rankinglist.action" method="post" 	name="assignSearch_assignment" theme="simple" >
				<s:hidden name="elclass.id" /> 
				<table width="100%" align="center" cellpadding="2" cellspacing="1">
					<tr>
						<th width="120" align="center" >
							培训班名称
						</th>
						<td width="35%" align="center" >
							<s:property value="elClass.name" />
						</td>
						<th width="120" align="center" >
							证书名称
						</th>
						<td width="35%" align="center" >
							<s:property value="elClass.certificatename" />
						</td>
					</tr>
					<tr>
						<th width="120" align="center" >
							创建人
						</th>
						<td width="35%" align="center" >
							<s:property value="elClass.creater.realname" />
						</td>
						<th width="120" align="center" >
							所属类别
						</th>
						<td width="35%" align="center" >
							<s:property value="elClass.cltype.name" />
						</td>
					</tr>
					<tr>
						<th align="center" >
							结业条件
						</th>
						<td colspan="3" align="center" > 
					  		<s:if test="elClass.classtype==0">
						  		必修课全部通过，选修课最少获得　
								<span style="color:red;"><b>
								<s:property value="elClass.optionalcredit" /> 
								</b></span>　学分
							</s:if><s:elseif test="elClass.classtype==2"> 
						  		必修课最少获得:<span style="color:red"><s:property value="elClass.credit_bx" /></span><br/>
						  		选修课最少获得:<span style="color:red"><s:property value="elClass.credit_xx" /></span> 
							</s:elseif>
							<s:else>
								必修课全部通过
							</s:else>
						</td> 
					</tr>
					<tr>
						<th width="120" align="center" >
							简介
						</th>
						<td colspan="3" align="center" >
							<s:property value="elClass.description" />
						</td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td>
							部门:
						</td>
						<td>
							<select style="width: 100%" name="deptid" id="deptid">
								<wysLib:dep_select />
							</select>
						</td>

						<td>
							性别：
						</td>
						<td>
							<select name="elUser.sex">
								<option value="">
									全部
								</option>
								<option value="男"
									<s:if test="elUser.sex==\"男\"">selected='selected'</s:if>>
									男
								</option>
								<option value="女"
									<s:if test="elUser.sex==\"女\"">selected='selected'</s:if>>
									女
								</option>
							</select>
						</td>
						<td>
							是否已结业
						</td>
						<td>
							<select name="elUser.isAssign">
								<option value="">
									全部
								</option>
								<option value="1"
									<s:if test="elUser.isAssign==\"1\"">selected='selected'</s:if>>
									是
								</option>
								<option value="0"
									<s:if test="elUser.isAssign==\"0\"">selected='selected'</s:if>>
									否
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							姓名：
						</td>
						<td>
							<input name="elUser.realname" id="elUser.realname">
						</td>
						<td>
							账号：
						</td>
						<td>
							<input name="elUser.username" id="elUser.username">
						</td>
						<td>
							<wysLib:BasetName btid="1" />：
						</td>
						<td>
							<select name="elUser.jingzhong">
								<option value="" selected="selected">
									全部
								</option>
								<s:iterator value="elUser.jingzhongs" status="jzs">
									<option
										<s:if test="elUser.jingzhong==elUser.jingzhongs[#jzs.index]">selected = 'selected'</s:if>
										value="<s:property />">
										<s:property />
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<!--年龄段开始时间:-->
						</td>
						<td>
							<!-- <input type="text" size="16" name="starttime"
								onclick="setday(this)"> -->
						</td>
						<td>
							<!-- 年龄段结束时间: -->
						</td>
						<td>
							<!-- <input type="text" size="16" name="endtime"
								onclick="setday(this)">-->
						</td>
						<td >
							培训类别：
						</td>
						<td > 
							<select name="elUser.peixunleibie">
								<option value="" selected="selected">
									全部
								</option>
								<s:iterator value="elUser.peixunleibies" status="pxlb">
									<option
										<s:if test="elUser.peixunleibie==elUser.peixunleibies[#pxlb.index]">selected = 'selected'</s:if>
										value="<s:property />">
										<s:property />
									</option>
								</s:iterator> 
							</select>
							<input type="button" value="搜索" onClick="view()">&nbsp;&nbsp; <s:property value="deptid.id" />  
						</td>
					</tr>
				</table>
			</s:form>
			<table width="100%">
				<tr> 
					<td valign="top" align="left">
						<s:if test="elusers.size==0">尚无证书</s:if>
						<s:else>
							<table style="margin-top:0px;" width="100%" align="center" cellpadding="1" cellspacing="1"
								bgcolor="#EBEBEB">
								<tr> 
									<th height="30" style="padding-left:8px;color:blue;" align="left">
										姓名
								   </th>
									<th height="30" align="center" >
										性别
									</th>
									<th height="30" align="center" >
										账号
									</th>
									<th height="30" align="center" >
										单位
									</th>
									<th height="30" align="center" >
										部门
									</th>
									<th height="30" align="center" > 
										培训类别
									</th>
									<th height="30" align="center" >
										得分
									</th> 
									<th height="30" align="center" >
										加分
									</th>
									<th height="30" align="center" >
										总分
									</th> 
									<th height="30" align="center" > 
									</th> 
								</tr> 
									<s:iterator value="elusers" status="st">
										<tr>  
											<td height="30" style="padding-left:8px;color:blue;" align="left">
												<s:property value="realname" />
											</td>
											<td height="30" align="center" >
												<s:property value="sex" />
											</td>
											<td height="30" align="center" >
												<s:property value="username" />
											</td> 
											<td height="30" align="center" > 
												<s:property value="department.unit.unit.name" />
											</td>
											<td height="30" align="center" > 
												<s:property value="department.name" />
											</td>
											<td height="30" align="center" > 
												<s:property value="elclass.cltype.name" />
											</td> 
											<td height="30" align="center" > 
											<fmt:formatNumber value='${precord.cscore+precord.fscore}'   pattern="0.0" type="number"/> 
												
											</td>
											<td height="30" align="center" >     
											<input type="text" name="precord.addscore" id="addscore_<s:property value="#st.index+1" />" value="<s:property value="precord.addscore" />">
											<input type="button" value="确定" onClick="alterRatioPassing(<s:property value="id" />,<s:property value="#st.index+1" />)" />
											</td>
											<td height="30" align="center" > 
												<fmt:formatNumber value='${precord.totalscore}'   pattern="0.0" type="number"/>
											</td>
											<td height="30" align="center" >
												<a href="MyIntegraInit.action?elclass.id=<s:property value="elclass.id"/>&elUser.id=<s:property value="id"/>" class="textbg4">查 看</a>
											</td> 
										</tr>
									</s:iterator> 
						  </table>
						</s:else>
					</td>
				</tr>
			</table> 
		<script type="text/javascript">  
			function alterRatioPassing(userid,i){    
				var addscore = document.getElementById('addscore_'+i).value; 
				acc_list.action = "elclass_record_addscore.action?user.id="+userid+"&precord.addscore="+addscore;
				acc_list.submit();
				alert("修改成功!");
			} 		 
			function page(i) {
				document.getElementById("pageNow").value=i;
				acc_list.submit();
			} 
			function view(){      
				assignSearch_assignment.submit();
			}
			</script> 
			<wysLib:page></wysLib:page>
			<br>
		</div>
		<form action="elclass_record_rankinglist.action" method="post" name="acc_list">
			<s:hidden name="deptid" />
			<s:hidden name="elclass.id" />
			<s:hidden name="elUser.sex" />
			<s:hidden name="elUser.realname" />
			<s:hidden name="elUser.username" />
			<s:hidden name="elUser.id" />
			<s:hidden name="elUser.jingzhong" />
			<s:hidden name="elUser.certificateno" />
			<s:hidden name="starttime" />
			<s:hidden name="endtime" />
			<s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			<s:hidden name="elUser.isAssign" />
			<s:hidden name="userids" id="userids"></s:hidden>
		</form>
		<!-- 内容 -->
	</BODY> 
</HTML>
