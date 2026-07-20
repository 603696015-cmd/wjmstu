<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/tz_images/css.css" type="text/css"  rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery-ui-1.9.2.js"></script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<title>拖拽题</title>
<style type="text/css">
img,input{margin:0;padding:0;border:0;}
input{background:none;}
.q_content{}
.q_content .cnt{height:30px;line-height: 40px;padding: 0px 10px 0px 10px;}
.q_content div{float:left}
.q_content .blnak{border-bottom: solid 1px;width:100px;height:30px;}
.bx_answeri{border:1px solid #0000FF;
 background:#C4E3FD; width:100px;
 height:30px;float: left;color:#000;
 text-align: center;line-height: 40px;
 margin:10px 0px 0px 10px;
 cursor: default;}
.bx_answeri_sp{width:20px;height:30px;float:left;}
.bx_answer{text-align:center; width:660px;height:150px;margin:0px auto 0px auto;}
.blnak-state-active{border: dotted 1px #ffffff;}
.clr{clear: both;}
</style>
<script type="text/javascript">
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
function sortQcont(){//题干排序
	var qcs = $("#q_content").children();
	
}
function sortAns(){//对答案排版
	var as = $("#bx_answer").children();
	var assize = as.length;
	if(assize>2&&assize<5){
		$("<div class='clr'></div><br/>").insertBefore(as[assize-2]);
	}
	if(assize>=5){
		$("<div class='clr'></div><br/>").insertBefore(as[assize-3]);
	}
}
$(function(){
		sortQcont();
		sortAns();
		$("#bx_answer .bx_answeri").draggable({
			revert: "invalid", 
			containment: $(this).parent(),
			helper: "clone",
			cursor: "move"
		});
		$("#q_content .blnak").droppable({//答案项
			accept: " .bx_answeri",
			activeClass: "blnak-state-active",
			drop: function( event, ui ) {
				var x = $(this).children();
					if(x.length==0){
						var aobj = ui.draggable;
						var a = $(aobj).text();
						aobj.append("<input name=\"question.stuAnswers\" type=\"hidden\" value=\""+a+"\" />")
						$(aobj).css("margin","0px");
						$(aobj).appendTo($(this));
					}
					else
						return ;
			}
		});
		$("#bx_answer").droppable({//答案项
			accept: " .bx_answeri",
			//activeClass: "custom-state-active",
			drop: function( event, ui ) {
				var aobj = ui.draggable;
				$(aobj).find("input").remove();
				var bx_ans=$("#bx_answer .bx_answeri");
				$(aobj).css("margin","10px 0px 0px 10px;");
				if(bx_ans.length>0)
				{
					var nowsortid = $(aobj).attr("sortid");
					var bf = null;
					for(var i=0;i<bx_ans.length;i++){
						var thesortid=$(bx_ans[i]).attr("sortid");
						if(nowsortid<thesortid){
							bf = bx_ans[i];		
							break;
						}
					}
					if(bf!=null)
						$(aobj).insertBefore(bf);
					else
						$(aobj).appendTo($(this));
				}else
					$(aobj).appendTo($(this));
			}
		});
});
function submittz(){
	if(window.confirm("请检查清楚！确定提交")){
	var xx = $('#theform').formSerialize();
	$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"quizquestion_save.action",data:xx,timeout:8000,cache:false,success:function (data) {
	   	if(data=='success'){
	   		alert(data);
		}else{
			var jdata=eval("("+data+")");
			if(jdata.atime==1)
				if(jdata.status==1){//1次回答正确
					alert("恭喜您回答正确！您"+jdata.atime+"您的得分："+jdata.myscore);
					window.parent.closeFrame();
					window.parent.showQN();
				}else{
					//继续作答
					alert("对不起，回答错误，请继续作答");
				}
			else if (jdata.atime==2){
				if(jdata.status==1){//1次回答正确
					alert("恭喜您回答正确！您"+jdata.atime+"次作答的得分："+jdata.myscore);
					window.parent.closeFrame();
					window.parent.showQN();
				}else{
					//跳到下一题
					window.parent.closeFrame();
					window.parent.showQN();
				}
			}else {
				//跳到下一题
				window.parent.closeFrame();
				window.parent.showQN();
			}
		}
	},error:function(msg){
		alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
	}});
	}	//window.parent.closeFrame();
}
</script>
<style type="text/css">
.daan {font-weight:bold;color:red; padding-left:20px;}
</style>
</head>

<body>
<div><img src="images/tz_images/header.jpg" width="1349" /></div>
<div><img src="images/tz_images/header_xia.jpg" width="1349" /></div>
<div id="main_l"><img src="images/tz_images/left.png" /></div>
<div id="content">
	<div>
    	<div id="content_l"><img src="images/tz_images/content_l.png" /></div>
        <!--题干和按钮部分开始-->
        
<div id="content_zhong">
          <div style="width:802px;margin-left:135px;margin-top:130px; float:left;">
            <!--主体上边背景-->
                <div id="m_t" style="width:800px">
                    <div style="float:left;"><img src="images/tz_images/main_lt.png" /></div>
                    <div id="main_t"></div>
                    <div><img src="images/tz_images/main_rt.png" /></div>
                </div>
                <!--主体中部-->
                <div id="m_z">
                    <div id="m_zl"></div>
                    <div id="m_zz">
                      <table width="100%" height="444" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td height="173" align="center"><table width="50%" border="0" cellspacing="5" cellpadding="0" style="margin-top:30px; font-size: 14px;">
                            <tr>
                              <th height="30"><table width="100%" border="0" cellspacing="5" cellpadding="0">
                                <tr>
                                  <td align="center"><img name="" src="images/tz_images/shij.jpg" width="249" height="200" alt="" /></td>
                                  <td align="center"><img name="" src="images/tz_images/shij.jpg" width="249" height="200" alt="" /></td>
                                </tr>
                              </table></th>
                            </tr>
                            <tr>
                              <td height="30" style=" color:#CCC; font-weight:bold;">
                              <!-- 题干 -->
                              <form action="#" id="theform" method="post">
                              <div class="q_content" id="q_content">
                              <s:if test="question.fwsize==1||question.fwsize==3"><div class="blnak"></div></s:if>
                              <s:set name="fencsize" value="question.fenContents.length"/>
                              <s:iterator value="question.fenContents" status="fenc">
                              	<div class="cnt"><s:property escape="false"/></div>
                              	<s:if test="(#fencsize-1)!=#fenc.index">
                              		<div class="blnak"></div>
                              	</s:if>
                              </s:iterator>
                              <s:if test="question.fwsize==2||question.fwsize==3"><div class="blnak"></div></s:if>
                              </div>
                              <s:hidden name="question.id" />
								<s:hidden name="question.qtype" />
								<s:hidden name="question.epblock.id" />
								<s:hidden name="myExamPaper.id" />
								
                              </form>
                              </td>
                              </tr>

                          </table></td>
                        </tr>
                        <tr>
                          <td><!-- 备选答案 -->
                         	   <div id="bx_answer" class="bx_answer">
                             	<s:iterator value="question.answers_" status="qast">
                              	<div class="bx_answeri" sortid="<s:property value="#qast.index"/>"><s:property escape="false"/></div>
                              </s:iterator>
                              </div>
                          </td>
                        </tr>
                        <tr>
                           <td height="30" align="right">考生答案：</td>
                           <td>XXXXX.MP3</td>
                           <td align="right">标准答案：</td>
                           <td>XXXXX.MP3</td>
                           <td align="right"><p>什么什么：</p></td>
                           <td>对（错） </td>
                        </tr>
                        <tr>
                           <td height="30" align="right">样音：</td>
                           <td>XXXXX.MP3</td>
                           <td align="right">考生录音：</td>
                           <td>XXXXX.MP3</td>
                           <td align="right"><p>系统评判：</p></td>
                           <td>对（错） </td>
                        </tr>
                        <tr>
	                        <td height="30" align="right">答案文本：</td>
	                        <td>XXXXX.MP3</td>
	                        <td align="right">考生文本：</td>
	                        <td>XXXXX.MP3</td>
	                        <td align="right"><p>考生得分： </p></td>
	                        <td>100分</td>
                       </tr>

                      </table>
                    </div><div id="m_zr"></div>
                </div>
                <!--主体下部-->
                <div id="m_b" style="width:800px;">
                    <div id="m_bl"><img src="images/tz_images/main_lb.png" /></div>
                    <div id="m_bz" ></div>
                    <div><img src="images/tz_images/main_rb.png" /></div>
                </div>
                  </div>
                  <div  id="fenshu" >100分</div>
                <!--主体播放条-->
<div id="bfq" style="margin-left:85px; width:911px;">
                    <div id="bfq_l"><img src="images/tz_images/bf_l.png" /></div>
                    <div id="bfq_z" >
                    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="63" align="center">
    	<table border="0" cellspacing="0" cellpadding="0">
      <tr>
       
        <td> <input type="button" onclick="submittz()" value="" style="background-image:url(images/tz_images/bf/tj.png); width:64px; height:50px;cursor: pointer;"/></td>
      </tr>
    </table>
    </td>
  </tr>
</table>

                    </div>
                    <div id="bfq_r"><img src="images/tz_images/bf_r.png" /></div>
                </div>
      </div>
          
 		
<!--题干和按钮部分到此结束-->
        <div id="content_r"><img src="images/tz_images/content_r.png" /></div>
    </div>
    <div id="bottom"></div>
</div> 
<div id="main_r"><img src="images/tz_images/right.png" /></div>

	</body>
</html>
