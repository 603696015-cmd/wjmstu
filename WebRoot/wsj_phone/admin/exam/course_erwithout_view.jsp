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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="详情查看" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场查看</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<span style="color: #ff0000;"></span>
			<table width="90%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td width="160" height="30" align="center" >
						考场标题
					</td>
					<td >
						<label>
							<s:property value="examRoom.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" align="center" >
						所属试卷库
					</td>
					<td bgcolor="#FFFFFF" colspan="3">
						<label>
							${examRoom.eroomLib.name}

						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						考场说明
					</td>
					<td >
						<label>
							<s:property value="examRoom.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						考试地点
					</td>
					<td >
						<label>
							<s:property value="examRoom.location" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						监考人员
					</td>
					<td >
						<div id="invigilators">
							<s:iterator value="examRoom.invigilators">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						阅卷人员
					</td>
					<td >
						<div id="appraises">
							<s:iterator value="examRoom.appraises">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						复核人员
					</td>
					<td >
						<div id="valids">
							<s:iterator value="examRoom.valids">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						考试时间
					</td>
					<td >
						<label>
							考场开始时间
							<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
						<br />
						<label>
							考场结束时间
							<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						考场类型
					</td>
					<td >
						<label> 
							<s:property value="examRoom.typeName" />
						</label> 
					<s:if test="examRoom.type == 1">
						<div id="invigilators">
							<s:iterator value="examRoom.invigilators">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div> 
					</s:if> 
					</td>
				</tr>
				
				<tr>
						<td width="160" height="30" align="center" >
							绑定mac地址：
						</td>
						<td >
							<s:if test="examRoom.isMacBand==1">
								是
							</s:if>
							<s:if test="examRoom.isMacBand==0">
								否
							</s:if>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							限&nbsp;定&nbsp;ip&nbsp;段：
						</td>
						<td >
							<s:if test="examRoom.isIpLimit==1">
								是
							</s:if>
							<s:if test="examRoom.isIpLimit==0">
								否
							</s:if>
						</td>
					</tr>
					<s:if test="examRoom.isIpLimit==1">
						<tr>
						  <td colspan="2">
						  	<div>
						  		<div style="float:left">
						  		  	 <s:iterator id="ipStrat" value="#request.ipStratList" status="statu">
						     		   <div>开&nbsp;始&nbsp;ip：<s:property value='ipStrat'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						  		 	 </s:iterator>  
						  		 </div>
						  		 <div>
						  		  	<s:iterator id="ipEnd" value="#request.ipEndList" status="statu">
						  			  <div>结&nbsp;束&nbsp;ip：<s:property value='ipEnd'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						  	        </s:iterator>
						  		 </div>
						  	</div>
						  </td>
						</tr>
					</s:if>
				<tr>
					<td width="160" height="30" align="center" >
						通过成绩：
					</td>
					<td >
						<label>
							<s:property value="examRoom.passgrade" />
							%
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						学分
					</td>
					<td >
						<label>
							<s:property value="examRoom.score" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						所用试卷
					</td>
					<td >
						<div id="eps_div">
							<s:iterator value="examRoom.exampapers" status="epsst">
								<div>
									<span style="width: 150"> <s:property value="title" /> 
									</span> 
									<span style="width: 60"> 达标线<s:property value="passgrade"/> </span>
									<lable name="prac">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a target="top" class="textbg4" href="exampaper_preview.action?examPaper.id=<s:property value='id' />">预览</a>
									<span style='color: red'>练习</span>：
									<span style="width: 150px;"><s:property
											value="prac.title" /> </span> 次数：
									<s:property value="practimes" />
									最低分
									<s:property value="pracscore" />
									</lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;
									<s:if test="prac.id!=0">
										<a target="top" class="textbg4" href="exampaper_preview.action?examPaper.id=<s:property value='prac.id' />">预览</a>
									</s:if>
								</div>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="50" align="center" >
						考生列表
					</td>
					<td >
						<s:if test="myrooms.size==0">
							<br>
						暂无考生
						</s:if>
						<s:else>
							<table width="96%" align="center" cellspacing="1" cellpadding="1">
								<caption>
									考生列表
								</caption>
								<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
										姓名/账号
									</td>
									<td height="30" align="center" >
										部门
									</td>
									<td height="30" align="center" >
										试卷数量
									</td>
								</tr>
								<s:iterator value="myrooms">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="tester.realname" />
											/
											<s:property value="tester.username" />
										</td>
										<td height="30" align="center" >
											<s:property value="tester.department.name" />
										</td>
										<td height="30" align="center" >
											<s:property value="epsize" />
										</td>
									</tr>
								</s:iterator> 
							</table>
							<script type="text/javascript">
								function page(i){
										document.location.href="course_erwithout_view.action?examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>&elclass.id=<s:property value="elclass.id"/>&Return=true&pS=<s:property value="pS"/>&pN="+i;
								}
							</script>
							<wysLib:page></wysLib:page>
						</s:else>
					</td>
				</tr> 
				<tr>
					<td>
						现 处于<span style="color:red"><s:property value="examRoom.validName"/></span> &nbsp;
												<span style="color:red"><s:property value="examRoom.uvalidName"/></span>状态，
					</td>
					<td> 
						<s:if test="examRoom.valid == 0 || examRoom.valid == 2 || examRoom.valid == 4">  
							<s:if test="examRoom.exampapers[0].title != null">  
								<s:if test="examRoom.type == 1 && examRoom.uvalid == 1">
									<span style="color:red">本考场为选拨类型，请到 结业考场选拨 进行人选筛选</span><br/>
								<a href="examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="elclass.id"/>" class="textbg">分配学员</a>
									<!-- <a href="examroom_selectingslist.action?examRoom.id=<s:property value="examRoom.id"/>&examPaper.id=<s:property value="id"/>" class=textbg>人员操作</a> -->
								</s:if><s:else>
									<s:if test="examRoom.uvalid != 1">
										<a href="examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="elclass.id"/>" class="textbg">人员修改</a>
								 		<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 1,'falsa','falsa','true','elclass_assignlist2');" class="textbg">复核并初审</a>  
										<s:if test="examRoom.valids.size ==0 "> <!-- 无复核人员 -->
											
										</s:if> 
									</s:if>								
								</s:else> 
							</s:if> 
						</s:if> 
							<s:if test="(examRoom.valid == 0 || examRoom.valid == 2)&& examRoom.type == 1">   
								<s:if  test="examRoom.type == 0 && examRoom.valids.size == 0"> 
									 <a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 1,'falsa','falsa','true','elclass_assignlist2');" class="textbg">复核并提交</a>  
									 <a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a>
								</s:if>
							</s:if> 
						<s:if test="examRoom.uvalid == 1">  
							<s:if test="examRoom.valid == 1 || examRoom.valid == 4">
								<s:if test="examRoom.valid == 4">
									<!-- <a href="examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id"/>&course.id=-1" class=textbg>人员编辑</a> -->
									<a href="erwithout_alterInit.action?examRoom.id=<s:property value="examRoom.id"/>" class=textbg>考场编辑</a>
									<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 3,'falsa','falsa','falsa','elclass_primash_list');" class="textbg">提交申请</a> 
									<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 2,'falsa','falsa','falsa','elclass_primash_list');" class="textbg">返回</a>  
									<a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a>
								</s:if><s:else>
									<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 3,'falsa','falsa','falsa','elclass_primash_list');" class="textbg">提交申请</a> 
									<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 2,'falsa','falsa','falsa','elclass_primash_list');" class="textbg">返回</a>  
								 	<a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a>
								</s:else>
							</s:if>
							<s:if test="examRoom.valid == 3">
								<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 5,'falsa','falsa','falsa','elclass_sh_list');" class="textbg">核准</a> 
								<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 4,'falsa','falsa','falsa','elclass_sh_list');" class="textbg">返回申请</a>  
								<a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a>
							</s:if> 	
						</s:if> 
						<s:if test="examRoom.valid == 5"> 
							<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>,6,'true','falsa','falsa','elclass_applyAlter_list');" class="textbg">申请修改</a> 	 
								 <a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a>
						</s:if>	
						<s:if test="examRoom.valid == 6">  
								<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 0,'falsa','falsa','falsa','elclass_alter_list');" class="textbg">允许修改</a> 
								<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 5,'falsa','falsa','falsa','elclass_alter_list');" class="textbg">不允许修改</a> 	
								<a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a> 
						</s:if>	
						<s:if test="examRoom.valid == 8">
								<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, 9,'falsa','falsa','falsa','elclass_delete_apply_list');" class="textbg">允许删除</a> 
								<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, <s:property value="examRoom.avalid"/>,'falsa','falsa','falsa','elclass_delete_apply_list');" class="textbg">不允许删除</a>
								<a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a> 	 
						</s:if>	
						<s:if test="examRoom.valid == 9"> 
								<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>, <s:property value="examRoom.avalid"/>,'falsa','falsa','falsa','combinationSearchclassInit');" class="textbg">还原状态</a> 	 
								<a href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="examRoom.id"/>&Return=close" target="_blank" class="textbg4">备 注</a>
						</s:if>	
						
						<!--<s:if test="examRoom.valid != 8 && examRoom.valid != 9">  
							<a style="cursor:pointer;"  onClick="sh(<s:property value="examRoom.id"/>,8,'falsa','true');" class="textbg">申请删除</a>  
						</s:if>-->   	
					</td>
				</tr> 
			</table> 
				<form action="examroom_sh_p.action" name="examroom_sh_p" method="post"> 
					<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
					<s:hidden name="examRoom.valid" id="examRoom.valid"></s:hidden>
					<s:hidden name="deleteValid" id="deleteValid"></s:hidden>
					<s:hidden name="alterValid" id="alterValid"></s:hidden>
					<s:hidden name="fushenValid" id="fushenValid"></s:hidden>
					<s:hidden name="Return" id="Return"></s:hidden><!-- value="course_return" -->
				</form>
				<script>  
					function sh(id,valid,alterValid,deleteValid,fushenValid,Return){
					    document.getElementById("examRoom.id").value=id;
					    document.getElementById("examRoom.valid").value=valid; 
					    document.getElementById("deleteValid").value=deleteValid; 
					    document.getElementById("alterValid").value=alterValid; 
					    document.getElementById("fushenValid").value=fushenValid; 
					    document.getElementById("Return").value=Return; 
					 	if(window.confirm("确定此操作？")){
							if(FillInNoteksInit(id)){ 
					 		document.forms.examroom_sh_p.submit();
					 		}
					 	} 
					}  
					function FillInNoteksInit(id){ 
					     width=600;
						 height=500;  
					  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
						 var rv =  window.showModalDialog("CRE_addNotes.action?examRoom.id="+id+"&Return=elclass_primash_list&x="+Math.random(),null,sFeature); 
						 if(rv == null){
							alert("未填写备注信息，您不能进行提交！");	 
							return false;				
						 }else{
							if(rv == true){  
								return true;
							}else if(rv == false){
								return false;
							}  
						 }
					 }  		
				</script>	 
		</div>
		<br/>
		<br/>
		<br/>
		<br/>
		<!-- 内容 -->
	
	</body>
</HTML>
