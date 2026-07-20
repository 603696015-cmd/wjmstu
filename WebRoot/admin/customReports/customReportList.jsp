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
						<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="标签列表" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>

<div style="margin-top: 40px; text-align: center;">

			<table align="center" cellpadding="2" cellspacing="2" width="60%"
				 bgcolor="#ECEDEB">
				<tr>
					<th>
						标签名称	
					</th>
					<!-- <th>
						JSP名称
					</th> -->
					<th>
						操作	
					</th>
					
					
				</tr>
			<s:iterator value="customReports">
					<tr>
						<td height="20" align="center">
							<s:property value="name" />
						</td>
						<!-- <td height="20" align="center">
							<s:property value="resultPage" />
						</td> -->
						
						<td height="20" align="center">
							<a onclick="updlable('<s:property value="id" />')" class=textbg4>修  改</a>
							<a onclick="copylable('<s:property value="id" />')" class=textbg4>复  制</a>
							<!-- <a onclick="updload('<s:property value="id" />')" class=textbg4>上  传</a> -->
							<a onclick="dellable('<s:property value="id" />')" class=textbg4>删  除</a>
						</td>
					</tr>
				</s:iterator>	
			</table>
			<center>
			
			</center>
			<wysLib:page></wysLib:page>
			<form action="queryCustomReportByName.action" method="post"
				name="acc_list">
				<input type="hidden" name="customReport.id" id="customReport.id"/>
			</form>
		</div>
<script>
	if('${elmessage}' != ""){
		alert('${elmessage}');
	}

		 function copylable(id){
		 var lablename="";
		 lablename="customReport.id="+id;
		 
		 
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
			function updlable(id){
				acc_list.action="queryCustomReportById.action";
				document.getElementById("customReport.id").value=id;
			
				acc_list.submit();
			
			}
		
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
				
				
				function updload(id){
					acc_list.action="uploadJspInit.action";
					document.getElementById("customReport.id").value=id;
			
					acc_list.submit();
				}
				
				
				function dellable(id){
					acc_list.action="deleteLableById.action";
					document.getElementById("customReport.id").value=id;
					if(window.confirm("删除操作后标签、该标签的计算组都会被删除，确认删除?")){
						acc_list.submit();
					}
				}
			</script>
	</body>
</HTML>
