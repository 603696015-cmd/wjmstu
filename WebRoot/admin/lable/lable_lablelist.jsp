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
		<TITLE>标签列表</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示类别</a>';
					}
				}
				
				
				function load(){
					if("${elmessage}"!=""){
						alert( "${elmessage}!");
					}
				}
				
		</script>
	</HEAD>
	<body onload="load();">
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?   
		   "#ffffff" :       "#f4f4f4" )
}
</style>
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="标签列表" />
				</div>
			</li>
		</ul>
		<div style="margin-top: 40px; text-align: center;">

			<table align="center" cellpadding="2" cellspacing="2" width="60%"
				bgcolor="#ECEDEB">
				<tr>
					<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td"
						style="padding: 8px; display: none;">
						<wysLib:lableTree_list_aj iname="" itype="" ivalue=""
							href="lable_getalllable.action?klTree.id=" rootAble="true"></wysLib:lableTree_list_aj>
					</td>
					<td width="5px;" valign="middle" bgcolor="#FAFCFC"
						style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td valign="top">
						<s:form action="lable_getalllable.action" method="post"
							name="acc_list" theme="simple">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="circulationListLable.name" id="lname" />
							<s:hidden name="searchLable.name" id="sname" />
							<s:hidden name="lable.name" id="lable.name" />
							<s:hidden name="lable.type" id="lable.type" />
							<div style="height: 40px; line-height: 40px; padding-left: 15px; margin-top: 10px;">
								标签名：&nbsp;
								<input type="text" name="searchL.name"
									value="<s:property value="searchL.name"/>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								关键词：&nbsp;
								<input type="text" name="searchL.keyword"
									value="<s:property value="searchL.keyword"/>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="submit" value="搜索"  />
							</div>
						</s:form>
						<div style="text-align: left;" id="showtree">
							<a href="javascript:showtree(true);" class="textbg">显示类别</a>
						</div>
						<div id="searchdiv" style="display: block; margin-top: -5px;">
							<table width="100%" border="0" cellspacing="1" cellpadding="5">
								<tr>
									<th>
										标签名称
									</th>
									<th>
										创建时间
									</th>
									<th>
										所属类别
									</th>
									<th>
										操作
									</th>
								</tr>
								<s:iterator value="listLable">
									<tr>
										<td height="20" align="center">
											<s:property value="name" />
										</td>
										<td height="20" align="center">
											<s:date name="createtime" format="yyyy/MM/dd HH:mm:ss"/>
										</td>
										<td height="20" align="center">
											<s:property value="lableTree.name" />
										</td>

										<td height="20" align="center">
											<a
												onclick="updlable('<s:property value="name" />','<s:property value="type"/>')"
												class=textbg4>修 改</a>
											<a
												onclick="copylable('<s:property value="name" />','<s:property value="type"/>')"
												class=textbg4>复 制</a>
											<a 
												onclick="copylableToDb('<s:property value="name" />','<s:property value="type"/>')"
												class=textbg4>标签复制</a>
										</td>
									</tr>
								</s:iterator>
							</table>
						</div>

					</td>
				</tr>

			</table>
			<wysLib:page></wysLib:page>
			<!-- 
			<form action="lable_getalllable.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="circulationListLable.name" id="lname" />
				<s:hidden name="searchLable.name" id="sname" />
				<s:hidden name="lable.name" id="lable.name" />
				<s:hidden name="lable.type" id="lable.type" />
				标签名：<s:textfield name="searchL.name"></s:textfield>
				关键词：<s:textfield name="searchL.keyword"></s:textfield>
				<input type="submit" value="搜索"  />
			</form>
			 -->
		</div>
		<script>
		function copylableToDb(name,type){
			acc_list.action = "copylableToDb.action";
			document.getElementById("lable.name").value = name;
			document.getElementById("lable.type").value = type;
			if(window.confirm("确定复制?")){
				acc_list.submit();
			}
		}
		
		 function copylable(name,type){
		 var lablename="";
		 if(type==1){
		 	lablename="<zdyLib:zdyloop lablename='"+name+"'  xunhuan='' setnull='暂无数据' switches='' include=''  constraint=''    ></zdyLib:zdyloop>";
		 }
		else if(type==2){
			lablename="<zdyLib:zdypage lablename='"+name+"'   setnull='暂无数据'  switches='' include=''  constraint=''></zdyLib:zdypage>";
			
		}else if(type==3){
			lablename="<zdyLib:zidingyisearch lablename='"+name+"' setnull='暂无数据' ></zdyLib:zidingyisearch>";
			  
		}
			
		 if (window.clipboardData)
  		{
 
  // the IE-manier
  window.clipboardData.setData("Text", lablename);
   alert("标签已复制");
  // waarschijnlijk niet de beste manier om Moz/NS te detecteren;
  // het is mij echter onbekend vanaf welke versie dit precies werkt:
  }
  else if (window.netscape)
  {
 
  // dit is belangrijk maar staat nergens duidelijk vermeld:
  // you have to sign the code to enable this, or see notes below
  netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
 
  // maak een interface naar het clipboard
  var clip = Components.classes['@mozilla.org/widget/clipboard;1']
         .createInstance(Components.interfaces.nsIClipboard);
  if (!clip) return;
 
  // maak een transferable
  var trans = Components.classes['@mozilla.org/widget/transferable;1']
          .createInstance(Components.interfaces.nsITransferable);
  if (!trans) return;
 
  // specificeer wat voor soort data we op willen halen; text in dit geval
  trans.addDataFlavor('text/unicode');
 
  // om de data uit de transferable te halen hebben we 2 nieuwe objecten
  // nodig om het in op te slaan
  var str = new Object();
  var len = new Object();
 
  var str = Components.classes["@mozilla.org/supports-string;1"]
         .createInstance(Components.interfaces.nsISupportsString);
 
  var copytext=lablename;

  str.data=copytext;
 
  trans.setTransferData("text/unicode",str,copytext.length*2);
 
  var clipid=Components.interfaces.nsIClipboard;
 
  if (!clip) return false;
 
  clip.setData(trans,null,clipid.kGlobalClipboard);
 
 
  }
 
  return false;

		
		
		}
			function updlable(name,type){
			if(type==1||type==2){
				acc_list.action="lable_cirulationListLableinfoAddInit.action";
				document.getElementById("lname").value=name;
			}else{
				acc_list.action="lable_searchLableinfoAddInit.action";
				document.getElementById("sname").value=name;
			}
			
			
			
			acc_list.submit();
			
			}
		
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
	</body>
</HTML>
