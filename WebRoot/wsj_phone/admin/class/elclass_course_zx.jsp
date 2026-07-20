<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>培训班必修课程管理</TITLE>
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/editTable.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function addCourse(){
				window.open ('elclass_course_selectList.action?elclassId=${elclassId}&status=0','选择课程','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
				//window.open ('page.html','newwindow','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
			}

			//初始化可编辑表格
			function initTable()
			{
				//var classId = ${elclassId};
			    pt = new PowerTableEdit("table1");
			    var arrText = new Array();
			    arrText[0] = "学完";
			    arrText[1] = "考过";
			    arrText[2] = "学完且考过";
			    var arrValue = new Array();
			    arrValue[0] = "1";
			    arrValue[1] = "2";
			    arrValue[2] = "3";
			    
			    pt.setCol(6,'txt');
			    pt.setCol(7,'txt');
			    pt.setCol(8,'sel',arrText,arrValue);
			    
			}

			//更新数据
			function updateData(currentRowIndex){
				if(currentRowIndex != null){
					var rowdata = getRowData(currentRowIndex);
					//alert(rowdata);
					var courseId = rowdata[0];
					var suggestcredit = rowdata[6]; 
					var setcredit = rowdata[7];
					var getcredit = rowdata[8];
					//alert("classId:"+ classId + ",suggestcredit:" + suggestcredit +",setcredit:"+setcredit+",getcredit:"+getcredit);
					
					$.post("elclass_course_modify.action", {
						'courseId':courseId,
						'elclassId':'${elclassId}',
						'suggestcredit':suggestcredit,
						'setcredit':setcredit,
						'getcredit':getcredit
						}, 
						function (data) {
							//alert('更新成功');
						});
					}
			}
		</script>
	</HEAD>
	<BODY onLoad="initTable();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="必修课管理" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">必修课管理</span>
			</li>
			<li class="sep">
			</li>
			<s:if test="elclassId != ''">
				<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="javascript:void(0)" onClick="addCourse()">添加课程</a>
				</li>
			</s:if>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			
				<div class="divClass">
					<table id="table1" width="98%" align="center" cellspacing="1" cellpadding="1">
						<tr>
							<td>课程ID</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								课程名称
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								创建者
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								创建时间
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								课程类别
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								课程时长
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								建议学分
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								设置学分
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								学分获得方式
							</td>
						</tr>
			<s:if test="bxCourses.size==0">
					</table>
				</div>
			</s:if>
			<s:else>
						<s:iterator value="bxCourses">
							<tr>
								<td>
									<s:property value="id" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB">
									<s:property value="name" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB">
									<s:property value="creater.realname" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB">
									<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB">
									<s:property value="ctype.name" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB">
									<s:property value="during" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB">
									<s:property value="suggestcredit" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB">
									<s:property value="setcredit" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB" data="${getcredit==0?1:getcredit}">
									<s:if test="getcredit == 1">
											学完
									</s:if>
									<s:elseif test="getcredit == 2">
											考过
									</s:elseif>
									<s:elseif test="getcredit == 3">
											学完且考过
									</s:elseif>
									<s:else>
											学完
									</s:else>
								</td>
							</tr>
						</s:iterator>
					</table>
					</div>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
