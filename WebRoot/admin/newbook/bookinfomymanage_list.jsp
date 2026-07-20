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
		<TITLE>我的图书管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<form action="newsManage_list.action" method="post" name="nmList">
			<s:hidden name="isOk" value="1"/>
			<s:hidden name="news.id" id="newsId" />
			<s:hidden name="newsOp" id="newsOp" />
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="图书列表" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
					<wysLib:testbooktypeTree rootAble="true" href="bookinfo_listview.action?btype.id=" ></wysLib:testbooktypeTree></td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
		<s:form action="bookinfo_listview.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="bookinfo.id" id="btype.id" ></s:hidden>
				<s:hidden name="delestatus" id="dstatus"></s:hidden>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td>  书	名：</td>
			      <td width="190"><s:textfield name="bookinfo.name" /></td>
				      <td>推荐属性 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->  
				           
	              </td>
				 
				    <td><s:select theme="simple"  headerValue="全部" headerKey=""
									list="#{1:'未推荐',2:'推荐'}"
									name="bookinfo.recommend" value="bookinfo.recommend" /></td>
				</tr>
				<tr>
				   <td>修改时间  开始时间:</td>
				     <td width="190"> 
					<input name='start'
						value="<s:date name="start" format="yyyy-MM-dd" />"
						onclick='setday(this)' readonly/>
				  </td>
				       <td>结束时间:</td>
				         <td>
				         	<input name='end'value="<s:date name="end" format="yyyy-MM-dd"/>" onclick='setday(this)' readonly/>
						</td>
				         <td colspan="2">
				           	 <input id="find" name="find" type="button" value="搜索" onClick="newsSubmit();" >
				         </td>
				</tr>
		  </table>
			</s:form>
						<s:if test="listb.size==0"><h3 align="center" style="margin-top:10px;">没有书籍信息</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="260" height="30" align="center" >
										书名									</th>
									<th width="100" height="30" align="center" >
										修改时间									</th>
									<th width="90" height="30" align="center" >
										发布者姓名									</th>
									<th width="100" height="30" align="center" >
										发布者部门								</th>
									<th width="70" height="30" align="center" >
										点击数									</th>
									<th width="70" height="30" align="center" >
										推荐属性								</th>
									
									<th width="140" align="center" >	操作								</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="listb">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="name" />
									  </td>
										<td height="30" align="center" >
											<s:date name="upddate" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="user.realname" />
										</td>
										<td width="100" height="30" align="center" >
											<s:property value="dename" />
									  </td>
										<td width="70" height="30" align="center" >
											<s:property value="click" />
									  </td>
										<td width="70" height="30" align="center" >
											<s:property value="Recommendname" />
									  </td>
										<td bgcolor="#FFFFFF" align="left" width="140" height="30">
											 <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>);"  class="textbg4">删除</a> 
											 <a style="cursor:pointer;"  href="bookinfo_view.action?bookinfo.id=<s:property value="id"/>&delestatus=1"  class="textbg4">浏览</a> 
											 <s:if test="statuse==1">
												 <a href="bookinfo_updinit.action?bookinfo.id=<s:property value="id"/>&delestatus=1"  class="textbg4">修 改</a>
											</s:if>
									  </td>
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<%-- 
			<form action="newsManage_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</form>
			 --%>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				assignSearch_assignment.submit();
			}
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				assignSearch_assignment.submit();
			}
			function sh(id){
									if(window.confirm('确定删除？')){
									    document.getElementById("btype.id").value=id;
									    document.getElementById("dstatus").value=1; 
									 	assignSearch_assignment.action="bookinfo_dele.action";
									 	assignSearch_assignment.submit();
								 	}
								} 
		</script>
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
										   