<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<link rel="stylesheet" href="css/mode/zTreeStyle/zTreeStyle.css" type="text/css">
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
		<script type="text/javascript" src="js/mode/jquery.ztree.core-3.5.js"></script>
			
	</HEAD>
	<BODY>
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="模块列表" />
				</div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="modeList.size==0">没有记录</s:if>
			<s:else>
			<table align="center" cellpadding="2" cellspacing="2" width="100%"
				 bgcolor="#ECEDEB">
				<tr>
					<th>
						模块名称	
					</th>
					<th>
						模块信息	
					</th>
					<th>
						模块ID	
					</th>
					<th colspan="2">
						模块绑定信息	
					</th>
					<th colspan="2">
						内容页绑定信息	
					</th>
					<th colspan="2">
						类别绑定信息	
					</th>
					
				</tr>
				<s:iterator value="modeList">
					<tr>
						<td height="40" align="center">
							<s:property value="name" />
						</td>
						<td height="40" align="center">
							<s:property value="TypeName" />
						</td>
						<td height="40" align="center">
							<s:property value="id" />
						</td>
						<td height="40" align="center">
							<s:if test="modeJspName==null">
							未绑定
							</s:if>
							<s:else>
							<s:property value="modeJspName" />
							</s:else>
						</td>
						<td height="40" align="center">
							 <a  onclick="modeupd('<s:property value="id"/>','<s:property value="typeid"/>',1)" class=textbg4>设置</a>
							  <a  onclick="fuzhi('mode_action.action?mode.id=<s:property value='id' />&mode.bindtypeid=1&mode.typeid=<s:property value="typeid"/>')" class=textbg6>复制链接</a>
						</td>
						<td height="40" align="center">
							<s:if test="modeContentJspName==null">
								未绑定
							</s:if>
							<s:else>
								<s:property value="modeContentJspName" />
							</s:else>
						</td>
						<td height="40" align="center">
							 <a  onclick="modeupd('<s:property value="id"/>','<s:property value="typeid"/>',3)" class=textbg4>设置</a>
							  <a  onclick="fuzhi('mode_action.action?mode.id=<s:property value='id' />&mode.bindtypeid=3&mode.typeid=<s:property value="typeid"/>&geturl.contentid=')" class=textbg6>复制链接</a>
						</td>
						<s:if test="TypetableName==null">
						<td height="40" align="center" colspan="2">
							无类别
						</td>
						</s:if>
						<s:else>
							
							<td height="40" align="center">
								<s:property value="TypetableName" />
							</td>
							<td height="40" align="center">
								 <a onclick="modetypeupd('<s:property value="id"/>')" class=textbg4>设置</a>
								  <a  onclick="fuzhi('mode_action.action?mode.id=<s:property value='id' />&mode.bindtypeid=2&mode.typeid=<s:property value="typeid"/>&mode.typebindId=1')" class=textbg6>复制链接</a>
							</td>
						</s:else>
					</tr>
				</s:iterator>
			</table>
			</s:else>
 		<a href="mode_updateDemoInit.action"  class=textbg6>上传模板</a>&nbsp;&nbsp;&nbsp;<a href="mode_downloadDemoInit.action"  class=textbg6>下载模板</a>
		</div>
		<div >

</div>	
		<CENTER><wysLib:page></wysLib:page></CENTER>
		<form action="mode_allmodeList.action" method="post"
			name="acc_list">
			<s:hidden name="pN" id="pageNow"/>
			<s:hidden name="pS" />
			</form>
			
		<script type="text/javascript">
		function fuzhi(lablename){
		 if (window.clipboardData)
  		{
 
  // the IE-manier
  window.clipboardData.setData("Text", lablename);
   alert("连接已复制");
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
		function modetypeupd(mid){
		width=770;
				height=500;
				var courserstudyurl="mode_typemodeviwe.action?mode.id="+mid;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var sReturn =window.showModalDialog(courserstudyurl+"&x="+Math.random(),window,sFeature);
				  if (typeof(sReturn) != "undefined")
			       {
			       if (sReturn=="1")
			       {
			       
			        acc_list.submit();;
			      
			       }
			      }
		}
		function modeupd(mid,mtypeid,bindid){
				
				width=700;
				height=500;
				var courserstudyurl="mode_modeviwe.action?mode.id="+mid+"&mode.typeid="+mtypeid+"&mode.bindtypeid="+bindid;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var sReturn =window.showModalDialog(courserstudyurl+"&x="+Math.random(),window,sFeature);
				  if (typeof(sReturn) != "undefined")
			       {
			       if (sReturn=="1")
			       {
			       
			        acc_list.submit();;
			      
			       }
			      }


			
			}
			function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
			
		
	
					
					
		<!-- 内容 -->
	</BODY>
</HTML>
