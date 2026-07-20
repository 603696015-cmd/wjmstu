<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.EroomBatch"%>
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
		<TITLE>考场批次管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function searchRoomInit(){
			     width=820;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("eroom_batch_room_list.action?m="+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择考场！');
				 }else{
				 	if(rv[0]<=0)
						alert('您没有选择考场！');
				 	seterinfo(rv[0])
				 }
			}
			function seterinfo(id){
				$.ajax({url:"eroom_block_erview.action",async:false,type:"post",
					data: {
					"examRoom.id":id,
					"x":Math.random
					}, 
					success:function (data) {
						json = eval("("+data+")");
						$("#erid").attr("value",json.id);
						$("#ername").html(json.title);
					}}); 
			}
			function searchRoomepInit(){
			     width=520;
				 height=400;
				 var erid =$("#erid").val();
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv = window.showModalDialog("eroom_block_eplist.action?examRoom.id="+erid+"&m="+Math.random(),null,sFeature);
				 if(null==rv||undefined==rv){
				 	alert('您没有选择大题！');
				 }else{
				 	if(rv.length<=0)
						alert('您没有选择大题！');
					else
						addblocks(rv);
				 }
			}
			var blocksize=0;
			function addblocks(xx){
				var ids = "";
				var titles="";
				for(var i = 0 ;i<xx.length;i++){
					ids += xx[i].split("=-=")[0]+",";
					titles+= xx[i].split("=-=")[1]+",";
				}
				if(xx.length>0)
				{	
					ids = ids.substring(0,ids.length-1);
					titles = titles.substring(0,titles.length-1);
				}
				var bls=$("<div>");
				bls.id="blocks__"+blocksize;
				bls.html("模块名称： <input type='text' epbeptitle=1 name='erepblocks["+blocksize+"].title' value=''/>，"+
				"\n包含的大题：<input type='hidden' name='erepblocks["+blocksize+"].blockids' value='"+ids+"'/>"+
				"\n<input type='text' style='width:300px;border:none;background:#f4f4f4' readonly='readonly' name='erepblocks["+blocksize+"].blocktitles' value='"+titles+"'/>"+
				"\n<a href=\"javascript:void(0)\" title=\"删除\" onclick=\"deleteerbep(this,0);\" class=\"textbg4\" style=\"width:20px\">X</a>");
				$("#blocks").append(bls);
				blocksize++;
			}
			function deleteerbep(obj,id){
				if(window.confirm("确定删除？")){
					if(id<=0){
						$(obj).parent().remove();
						alert("模块已删除！");
						return;
					}
					$.ajax({url:"eroom_block_erblock_delete.action",async:false,type:"post",
						data: {
						"erepblock.id":id,
						"x":Math.random
						}, 
						success:function (data) {
							$(obj).parent().remove();
							alert("模块已删除！");
						}});
				} 
			}
			function onsubmit_(){
				if($("#erbname").val()==''){
					alert("请填写模块标题！");
					return false;
				}
				var inputs = $("input");
				if(inputs.length==0){
					alert("请设置模块！");
					return false;
				}
				for(var i = 0; i<inputs.length;i++){
					if($(inputs[i]).attr("epbeptitle")==1){
						if($(inputs[i]).val()=='')
						{
							alert("模块名称不要为空");
							$(inputs[i]).focus();
							return false;
						}
					}
				}
			}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加考场批次" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加考场批次新</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroom_batch_list.action">考场批次管理</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="eroom_block_add" method="post" name="catalog_info"
				theme="simple" onsubmit="return onsubmit_();">
				<table width="800px" align="left" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" height="30" align="center">
							名称
						</td>
						<td>
							<label>
								<s:textfield name="erblock.name" id="erbname" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							介绍
						</td>
						<td>
							<label>
								<s:textarea name="erblock.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center">
							选择考场：
						</td>
						<td>
							<span id="ername"><s:property value="erblock.eroom.title"/> </span><input id="erid" type="hidden" value="<s:property value="erblock.eroom.id"/>" name="erblock.eroom.id"/>
							<a class="textbg4" href="javascript:void(0)"
								onclick="searchRoomInit(); return false;">选择</a>
						</td>
					</tr>
					<tr>
						<td width="120" align="center">
							设置模块：
						</td>
						<td>
							<div id="blocks">
								<s:iterator value="erblock.erepblocks" status="xx">
									<div>
									模块名称：<input type='hidden' name='erepblocks[<s:property value="#xx.index"/>].id' value='<s:property value="id"/>'/>
									<input epbeptitle=1 name='erepblocks[<s:property value="#xx.index"/>].title' value='<s:property value="title"/>'/>，
									包含的大题：<input type='hidden' name='erepblocks[<s:property value="#xx.index"/>].blockids' value='<s:property value="blockids"/>'/>
									<input style="width:300px;border:none;background:#f4f4f4" readonly="readonly" name='erepblocks[<s:property value="#xx.index"/>].blocktitles' value='<s:property value="blocktitles"/>'/>
									<a href="javascript:void(0)" title="删除" onClick="deleteerbep(this,<s:property value="id"/>);" class="textbg4" style="width:20px">X</a></div>
									<script type="text/javascript">blocksize++ </script>
								</s:iterator>
							</div>
							<a class="textbg4" href="javascript:void(0)"
								onclick="searchRoomepInit(); return false;">设置</a>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center">&nbsp;
							

						</td>
						<td>
							<input type="submit" style="width: 90px" class="textbg4"
								value="确认添加" />
								
							<a href="stat_eroom_block_list.action?examRoom.id=<s:property value="examRoom.id"/>" class="textbg4" style="width:90px">返回</a>
						
						</td>
					</tr>
				</table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
