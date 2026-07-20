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
		<TITLE>课程价格设置</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter 
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />  
		<script type="text/javascript" src="js/menu.js"></script> 
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<span style="font-weight: bold;">课程定价</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 --> 
		<div style="margin-top: 0px; text-align: center;">
		<table width="100%" >
			<tr>
				<td width="200px" valign="top" id="tree_list_td"><wysLib:ctypeTree rootAble="true" href="peice_myallcourselist.action?ctype.id=" ></wysLib:ctypeTree></td>
				<td valign="middle" width="5px;" style="padding: 0px" >
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
										onclick="changeTreeDisplay(this)" />
					</td>
			<td valign="top">
			<s:form action="peice_myallcourselist" name="myclist" theme="simple">
				<s:hidden name="pN" id = "pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				<s:hidden name="biaoshi"  id="status"></s:hidden>
				<s:hidden name="ctype.id"></s:hidden>
				<s:hidden name="course.id" id="c_id"></s:hidden>
				<s:hidden name="wpeice" id="c_fee"></s:hidden>
				<s:hidden name="pt" id="c_type"></s:hidden> 
				<s:select theme="simple"  headerValue="全部" headerKey="0"
									list="#{1:'未定价',4:'已定价',2:'审核中',3:'审核通过'}"
									name="stype" value="stype" />
				课程名称：<s:textfield name="course.name" id="testname"></s:textfield> <s:submit value="搜索"></s:submit>
			
			</s:form>
							
			<s:if test="cp.size==0">没有找到符合条件的课程</s:if>
		<s:else> 
		<form action="fee_coursecharge_nolist.action" name="myclistdel">
			<table width="100%" height="100%" align="center" cellpadding="1"
				cellspacing="1" >
				<tr>
					<th align="center" bgcolor="#FFFFFF">
					</th>
					<th align="center" bgcolor="#FFFFFF">
						课程名称
					</th>
					<th align="center" bgcolor="#FFFFFF">
						创建者
					</th>
					<th align="center" bgcolor="#FFFFFF">
						课程类型	
					</th>
					<th align="center" bgcolor="#FFFFFF">
						创建时间
					</th>
					<th align="center" bgcolor="#FFFFFF">
						会员价格
					</th>
					<th align="center" bgcolor="#FFFFFF">
						市场价格
					</th>
					<th align="center" bgcolor="#FFFFFF">
						状态
					</th>
					<th align="center" bgcolor="#FFFFFF">
						操作
					</th>

					
				</tr>
				<s:iterator value="cp">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							
							 
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="course.name" />
						</td>
						
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="course.creater.realname" />
						</td>
						<td align="center" bgcolor="#FFFFFF">
							
							<s:property value="course.ctype.name" />
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<s:date name="course.createtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<s:if test="status==0||status==4">
						<td align="center" bgcolor="#FFFFFF" onClick="alterFee(this,1,<s:property value="course.id"/>)">
						<s:property value="coursenowPrice"/>
						</td>
						</s:if>
						<s:else><td align="center" bgcolor="#FFFFFF">
						<s:property value="coursenowPrice"/>
						</td>
						</s:else>
		
						<s:if test="status==0||status==4">
						<td align="center" bgcolor="#FFFFFF" onClick="alterFee(this,2,<s:property value="course.id"/>)">				
						<s:property value="courseoldPrice"/>
						</td>
						</s:if>
						<s:else>
						<td align="center" bgcolor="#FFFFFF">
						<s:property value="courseoldPrice"/>
						</td>
						</s:else>

						<td align="center" bgcolor="#FFFFFF">
							<s:if test="status==0">
							未定价
							</s:if>	
							<s:if test="status==1">
							审核通过
							</s:if>
							<s:if test="status==3">
							审核中
							</s:if>
							<s:if test="status==4">
							已定价
							</s:if>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<s:if test="status==0||status==4">
								
								<a style="cursor:pointer;"  onClick="sh(<s:property value="course.id"/>, 1);"  class="textbg6">提交审核</a>
							</s:if>
						</td>
						
					</tr>
				</s:iterator>
			</table>
			</form>
			</s:else></td>
			</tr>
		</table>
		<div id="fee" style="background: #ddfdff;display:none; border: 1 solid buttonface;width: 160px;position: absolute;" >
		<input type="text" id="cfee" size="5"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onClick="saveFee()" value="设定" />
		<input type="button" onClick=" document.getElementById('fee').style.display='none'" value="关闭"/>
		</div>
			
			<script>
			var cid = 0 ; 
			var cname = '' ; 
			var pt="";
			function alterFee(obj,type,courseid){ 
				pt=type;
				cid =  courseid ;
				cname =  obj.parentElement.children[1].innerHTML;
				document.getElementById("fee").style.display="block";
				var left = (obj.offsetLeft + obj.clientWidth);
				var top = (obj.offsetTop);
				while (obj = obj.offsetParent) {
					left += obj.offsetLeft;
					top += obj.offsetTop;
				}
				document.getElementById("fee").style.left =left-200;
				document.getElementById("fee").style.top =top;
			}
			function saveFee(){
				if(cid==0){
				alert("请选择课程");
				return;
				}
				
					var fee1 = document.getElementById("cfee").value;
				if(window.confirm("确定为\""+cname+"\"设定价格为"+fee1+"？")){
					document.getElementById("c_id").value=cid;
					document.getElementById("c_type").value=pt; 
					document.getElementById("c_fee").value=fee1;
					myclist.action="peice_myallcourselist.action";
					//alert(myclist.action);
					myclist.submit();
					
				}
			
			}
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
				function sh(id,status){
								    document.getElementById("course.id").value=id;
								    document.getElementById("status").value=status; 
									document.forms.myclist.submit();
								 	
								}  
			</script>
			<wysLib:page></wysLib:page> 
		</div>

		<!-- 内容 -->
	
	</body>
</HTML>
