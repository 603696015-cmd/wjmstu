<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>我要投保</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<LINK href="css/css.css" type=text/css rel=stylesheet>
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="js/common.js"></SCRIPT>

<SCRIPT language=javascript src="js/jquery.js"></SCRIPT>
<script type="text/javascript" src="js/fckeditor.js"></script>
		<script type="text/javascript" src="js/jquery2.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>

<META content="MSHTML 6.00.2900.6197" name=GENERATOR>
<style type="text/css">
<!--
.STYLE5 {	FONT-SIZE: 14px; FONT-WEIGHT: bold
}
.STYLE7 {	COLOR: #0000ff; FONT-SIZE: 14px; FONT-WEIGHT: bold
}
.STYLE8 {FONT-SIZE: 14px; color: #0000ff;}
-->
</style>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0>
<script type="text/javascript">
			
		  	 	var optCount=1;
				var nowCount=0;
				function addOption(){
					var _optiontype = "radio"
					var obj = document.createElement("div");
					obj.id="option_"+nowCount;
					if(nowCount==0){
					obj.innerHTML ="产品参数"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,"+nowCount+")' id='__option"+nowCount+"' name='question.options'></textarea>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"' frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"&nbsp;&nbsp;";
					}else{
					obj.innerHTML ="产品参数"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<textarea  style='border:1px solid buttonface;overflow:hidden; width:500px;height:120px' onfocus='createeditor(this,"+nowCount+")' id='__option"+nowCount+"' name='question.options'></textarea>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"'  frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"&nbsp;&nbsp;";
					}
					document.getElementById("option_area").appendChild(obj);
					++nowCount;
				}
				function createeditor(obj,id){
					//alert("dd"+id);
					//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
					//$("#opt_frame"+id).attr("width",500);
					//$("#opt_frame"+id).attr("height",120);
					var oFCKeditor = new FCKeditor(obj.id) ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 120;
					oFCKeditor.Width = 500;
					oFCKeditor.ToolbarSet = "qoption" ;
					oFCKeditor.ReplaceTextarea();
				}
				function removeOption(){
					if(nowCount<=1) {
						alert('相关产品参数请不要小于1个');
					}
					else{
					--nowCount;
					var obj = document.getElementById("option_"+nowCount);
					document.getElementById("option_area").removeChild(obj);
					}
				}
			
			
			
				function _onsubmit(){
					if(FCKeditorAPI.GetInstance("content").GetXHTML(true)==''){
						alert("题干不要为空");
						//document.getElementById("content").focus();
						return false;
					}
				
		  			var optionsanswers = document.getElementsByName("question.options");
					var _opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if(optionsanswers[_i].value=='')
							_opa++;
					}
					if(_opa!=0){
						alert("请填写完整产品参数");
						return false;
					
					}
					optionsanswers = document.getElementsByName("question.answers");
					_opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if(optionsanswers[_i].checked=='checked'||optionsanswers[_i].checked)
							_opa++;
					}
					if(_opa==0){
						alert("请选择选择答案");
						return false;
					
					}
				
				
		  		
		  		
		  		return true;
			}
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 200;
				oFCKeditor.Width = 700;
				oFCKeditor.ToolbarSet = "qcontent" ;
				oFCKeditor.ReplaceTextarea();
		  	 	
		  	 	
		  	 	addOption( );
				addOption( );
				addOption( );
				addOption( );
				
				
			}
		</script>
		 <script type="text/javascript">
		 	function addQuestion(tid){
		 		document.getElementById("qType").value=tid;
		 		form_question_create.action="question_addInit.action";
		 		//addQues.submit();
		 		form_question_create.submit();
		 	}
		 	
		 	function page(i){
				document.getElementById("pageNow").value=i;
				//document.forms[0].submit();
				assign.submit();
			}
		 </script>
		 <SCRIPT type=text/javascript>$('#locationid').html("修改基本信息");</SCRIPT>
    
    <SCRIPT>
	
       	 <!----检查用户名，电子邮箱结束-->
      function CheckForm() 
		{ 
			
			if (document.myform.RealName.value =="")
			{
			alert("请填写您的真实姓名！");
			document.myform.RealName.focus();
			return false;
			}
			if (document.myform.Sex.value =="")
			{
			alert("请选择您的性别！");
			document.myform.Sex.focus();
			return false;
			}
			if (document.myform.IDCard.value =="")
			{
			alert("请输入您的身份证号码！");
			document.myform.IDCard.focus();
			return false;
			}
			if (parseInt(document.myform.IDCard.value.length)!=15&&parseInt(document.myform.IDCard.value.length!=18))
			{
			alert("有效身份证号码必须是15位或18位！");
			document.myform.IDCard.focus();
			return false;
			}
		  return true;	
		}
    </SCRIPT>
    <form action="insure_online.action" method="post">
    	<center>
    		名称：<input type="text" name="baoxianProduct.name"/>&nbsp;&nbsp;
    		保险产品所属栏目:
    		<select name="baoxianProduct.ptype.id" id="parentid">
				<wysLib:productTypeSelect selectid="${baoxianProduct.ptype.id}"></wysLib:productTypeSelect>
			</select>
			<input type="submit" value="搜索"/>
    	</center>
    </form>
    <s:iterator value="baoxianProductList">
  <TABLE class=tdbg style="margin-top:15px;" border=0 cellSpacing=0 cellPadding=0 
            width="98%" align=center>
  <TBODY>
    <TR>
      <TD class=heicu14 height=35 vAlign=bottom>
      <TABLE width="100%" 
                  height=30 border=0 cellPadding=0 cellSpacing=0 bgcolor="#E1F4F3">
        <TBODY>
          <TR>
            <TD width="100" align="left" bgcolor="#E1F4F3" class=STYLE5>
            	<s:if test="logo != null">															
					<img src="<s:property value="logo_"/>" width="86" height="25" />
				</s:if>
				<s:else>
					<img src=""  width="86" height="25" /> 
				</s:else> 
            </TD>
            <TD align="left" bgcolor="#E1F4F3" class=STYLE5><span class="contenttitle"><A 
                        href="insure_baodan.action?id=${id }">
              <s:property value="name"/> </A></span></TD>
            <TD width="180" align=middle bgcolor="#E1F4F3" class="d_err">服务热线：
              <s:property value="fuwurexian"/></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD vAlign=bottom>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
          <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD width=130 height="70" align=center vAlign=middle><SCRIPT type=text/javascript>
																	obj = document.getElementById("cimg_0");
																	addImgs(obj);
																</SCRIPT>
                    <span class="STYLE8">产品特色</span></TD>
                <TD height=80 vAlign=middle style="padding:8px;">
                	<p>
                		<s:property value="jianjie"/>
                	</p>
                </TD>
              </TR>
            </TBODY>
          </TABLE>
          <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
                <TR>
                  <TD width=130 height="70" align=center vAlign=middle><SCRIPT type=text/javascript>
																	obj = document.getElementById("cimg_0");
																	addImgs(obj);
																</SCRIPT>
                      <span class="STYLE8">产品亮点</span></TD>
                  <TD height=80 vAlign=middle>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  	<wysLib:baoxianProduct_chanpinliangdian iname="${chanpinliangdian}"></wysLib:baoxianProduct_chanpinliangdian>
                  </table>
                  </TD>
                </TR>
              </TBODY>
            </TABLE></td>
          <td width="210"><table width="80" height="85" border="0" align="center">
  <tr>
    <td><DIV class=tbk>
            <DIV><IMG src="images/tbs.jpg"></DIV>
            <DIV class=tbk_md>
              <DIV class=tbk_md_q>
                <DIV class=" yj"><SPAN style="FONT-SIZE: 14px">￥</SPAN><s:property value="shichangjia"/>
                  <DIV class=yj_gw><IMG src="images/gwx.gif"></DIV>
                </DIV>
                ￥<s:property value="huiyuanjia"/> </DIV>
              <DIV><a href="insure_baodan.action?id=${id }"><IMG 
src="images/ckytb.jpg" border="0" 
style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"></a> </DIV>
            </DIV>
            <DIV><IMG src="images/tbx.jpg"></DIV>
          </DIV></td>
  </tr>
</table>
</td>
        </tr>
      </table>
      </TD>
    </TR>
  </TBODY>
</TABLE>
</s:iterator>
  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td align="center">
      	<s:form action="insure_online.action" method="post" name="assign">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
		</s:form>
      	<wysLib:page></wysLib:page>
      </td>
    </tr>
</table>
  <p>&nbsp;</p>
 

	</body></HTML>

