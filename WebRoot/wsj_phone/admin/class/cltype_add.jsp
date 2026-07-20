<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doSubmit(){
				var titleObj=document.getElementById("classTypeName");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("培训班类别名称不能为空!");
					titleObj.focus();
					return false;
				}
				var qlibId=$("input[name='cltype.parent.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择培训班类别！");
					return false;
				}
				return true;
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班类别添加" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		<div class="dh3">
			<!--<div class="newpos"></div>
			<div class="newpos2">
				<a href="cltype_list.action">培训班类别管理</a>
				<span style="font-weight: bold;">培训班类别添加</span>
			</div>-->
		</div>
		<s:form action="cltype_add" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" value="0" name="cltype.isshared" />
			<table style="margin-top:4px;" width="700" cellpadding="1" cellspacing="1">
				<tr>
					<td width="120" height="30" align="right" >
						<span class="neededitem">*</span>类别名称：
					</td>
					<td >
						<label>
							<s:textfield name="cltype.name" id="classTypeName" size="80" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
						类别介绍：
					</td>
					<td >
						<label>
							<s:textarea name="cltype.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>上级类别：
					</td>
					<td >
						<label>
							<wysLib:clTypeTree iname="cltype.parent.id" itype="ra_2no" href="" rootAble="false" />
						</label>
					</td>
				</tr>
				
				<%-- 
				<tr>
					<td width="120" height="30" align="center" >
						是否为共享节点
					</td>
					<td >
						<label>
							<s:select list="#{0:'不共享',1:'共享'}" name="cltype.isshared"></s:select>
						</label>
					</td>
				</tr>
				 --%>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						<input style="color: red;border: none;" class="textbg" type="submit" value="确认添加">
						<a href="cltype_list.action" class=textbg>取 消</a>
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	
	</body>
</HTML>
			