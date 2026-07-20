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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/exampaperop.js"></script>
		<script type="text/javascript" src="js/jquery.bgiframe-2.1.2.js"></script>
		<link rel="stylesheet" type="text/css" href="js/tree/dtree.css" />
		<script type="text/javascript" src="js/tree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
		var dia_move = 0;var m_x =0;var m_y = 0;
		$(document).ready(function(){
			$("#dia_").bgiframe();
			$("#dia_head_").bind("mousedown",function(e){beginmove(this)});
		});
		function beginmove(obj){
			$(document).bind("mousedown",function(e){
				m_x = e.pageX - $("#dia_head_").offset().left;
			 	m_y = e.pageY - $("#dia_head_").offset().top;
			 	dia_move= true;
			 	e.cancelBubble = true;
			 	e.returnValue = false;
			}).bind("mouseup",function(e){
			 	dia_move = false;
			 	e.cancelBubble = true;
			 	$(this).unbind("mousemove");
			 	$(this).unbind("mouseup");
			}).bind("mousemove",function(e){
			    e.cancelBubble = true;
	 			if(dia_move){
	 				if((e.pageX-m_x)>=0
	 					&&(e.pageX-m_x+$("#dia_head_").width())<=$("body").width()){
			 			$("#dia_").css("left",e.pageX -m_x );
					}
					if((e.pageY-m_y)>=0){
						$("#dia_").css("top",e.pageY-m_y);
					}
				}else
					return false;
		    });
		}
		function checkExamPaper(){
			//ep_tscore总分
			//realqScoreSum实际分数
			//questionScoreSum设置的总分
			listepblocks(<s:property value="examPaper.id"/>);
			if(questionScoreSum == 0){
				alert("请添加大题");
			}else if(questionScoreSum != realqScoreSum){
				alert("您设置的总分与实际总分不一致，请添加实际题量");
			}else if(realqScoreSum != ep_tscore){
				alert('实际分数和总分分数不一样');
			}else{
				if(window.confirm("确定创建完成？确定试卷没有需要修改的地方了？"))
				document.location.href = "exampaper_update_status.action?examPaper.id=<s:property value="examPaper.id"/>";
			}
		}
		var ep_tscore=<s:property value="examPaper.ep_tscore" />;
	</script>
		<style type="text/css">
			td {
				font-size: 12px;
				color: #333333;
				line-height: 150%;
				padding:3px 0px 3px 0px;
			}
			tr {
				background-color: expression(( this . sectionRowIndex % 2 ==   0) ?
					"#ffffff" :   "#f4f4f4" )
			}
		</style>
	</HEAD>
	<body onLoad="listepblocks(<s:property value="examPaper.id"/>)">
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="试卷总览" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加试卷 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<span style="color: #ff0000; text-align: center"><s:property
					value="elmessage" /> </span>
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBda">
				<tr>
					<td align="left">
						<strong>试卷基本信息 </strong>
					</td>
					<td width="80px">
						
					</td>
					<td width="60px">
						<s:if test="examPaper.showmod==0">
							<a target="_blank"
								href="exampaper_preview.action?examPaper.id=<s:property value="examPaper.id"/>"
								class=textbg4>预 览</a>
						</s:if>
						<s:else>
							<a target="_blank"
								href="exampaper_preview_1b1.action?examPaper.id=<s:property value="examPaper.id"/>"
								class=textbg4>预 览</a>
						</s:else>
					</td>
					<td width="60px">
						<a href=""
							onclick="alterepbaseinfoinit('ep_baseinfo');return false;"
							class=textbg4>修 改</a>
					</td>
					<td width="60px">
						<a href="" onClick="showorhidden('ep_baseinfo');return false;"
							class=textbg4>收 起</a>
					</td>
				</tr>
			</table>
			<div id="ep_baseinfo">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" align="right" style="padding-right:10px;">
							试卷标题
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<strong> <s:property value="examPaper.title" /> </strong>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" style="padding-right:10px;">
							试卷呈现方式
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<strong> <s:property value="examPaper.showTypeName" /> </strong>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" style="padding-right:10px;">
							所属试卷库
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:property value="examPaper.epl.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" style="padding-right:10px;">
							查询题网站链接
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:iterator value="examPaper.queryurls">
									<s:property value="title" />:<s:property value="href" />
									<br />
								</s:iterator>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" style="padding-right:10px;">
							试卷说明
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:property value="examPaper.description" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" style="padding-right:10px;">
							试卷时长（分钟）
						</td>
						<td>
							<label>
								<s:property value="examPaper.during" />
							</label>
						</td>
						<td width="160" align="right" style="padding-right:10px;">
							试题总分
						</td>
						<td>
							<s:property value="examPaper.ep_tscore" />
						</td>
					</tr>
					<!--<tr>
						<td width="160" align="center">
							出题方式
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:if test="examPaper.showmod==0">
									普通显示
								</s:if>
								<s:else>
									逐题显示
								</s:else>
							</label>
						</td>
					</tr>
				--></table>
				<input id="ep_id" type="hidden"
					value="<s:property value="examPaper.id"/>" name="examPaper.id" />
			</div>
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td align="left">
						<strong>试卷组成信息</strong>
					</td>
					<td width="60">
						<a href="" onClick="addepblockinit();return false;" class=textbg>添加大题</a>
					</td>
					<td width="40">
						<a href="" onClick="showorhidden('ep_block_list');return false;"
							class=textbg>收 起</a>
					</td>
				</tr>
			</table>
			<div id="ep_block_list" style="text-align: center;">
			</div>
		</div>
		<div id="dia_"
			style="display: none; width: 560px;background: #ffffff; position: absolute; z-index: 9999; border: 1px solid black;">
			<div id="dia_head_"
				style="cursor:move; height:25px;width: 560px; border-bottom: solid 2px buttonface; background-image: url(images/textbg.gif); text-align: right;">
				<a style="width: 25px;color:fff;font-size:24px; text-decoration: none;" href=""
					onclick="dia_close();return false;">X</a>
			</div>
			<div id="dia_content"></div>
		</div>
		<br />
		<div style="text-align: center;">
		<input class=textbg style="border: none;" type="button"
							value="返回试卷列表" onClick="document.location='exampaper_list.action?sublibs=1'">
		<input class=textbg style="border: none;color:red;" type="button"
							value="创建完成" onClick="checkExamPaper();">
		<input class=textbg style="border: none;" type="button"
							value="刷新" onClick="document.location='exampaper_all_alterinit.action?examPaper.id=<s:property value="examPaper.id"/>';">
		<br/>
		提示：如数量与分值未变化请点击“刷新”按钮
		 </div>
		<br />
		<!-- 内容 -->
	
	</body>
</HTML>
