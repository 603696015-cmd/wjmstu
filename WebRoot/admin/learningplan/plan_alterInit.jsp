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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<SCRIPT type="text/javascript">
			
			
			function doSubmit(){
				var titleObj=document.getElementById("learnplan.name");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("计划名称不能为空!");
					titleObj.focus();
					return false;
				}
				return true;
			}
			
		</SCRIPT>
		<style type="text/css">
			td {
				font-size: 12px;
				color: #333333;
				line-height: 150%;
				padding:5px;
			}
			
			tr {
				background-color: expression(( this . sectionRowIndex % 2 == 0) ?
					"#ffffff" : "#f4f4f4" )
			}
			.STYLE1 {color: #3399FF}
			                .STYLE2 {color: #FF0000}
              </style>
	</HEAD>
<body >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz" style="padding:0px;"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加知识" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz" style="padding:0px;">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
</table>
		
		
	<s:form action="plan_alter.action" method="post" name="kledge_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" name="learnplan.id" value="${learnplan.id }">
		<table width="81%" cellpadding="1" align="center" cellspacing="1"
				bgcolor="#ECEDEB">
              <tr>
                <td width="113" height="30" align="right" bgcolor="#F0FAFF"><span class="neededitem STYLE2">*</span><span class="STYLE1">计划名称： </span></td>
                <td width="776" bgcolor="#FFFFFF"><label>
                  <s:textfield name="learnplan.name" id="learnplan.name" size="60" />
                  </label>
                </td>
              </tr>
              <tr>
                <td width="113" height="30" align="right" bgcolor="#F0FAFF"><span class="neededitem STYLE2">*</span><span class="STYLE1">计划周期： </span></td>
                <td bgcolor="#FFFFFF"><label>
                  <s:textfield theme="simple" name="learnplan.period" 
								size="20"  id="learnplan.period"/>
                  </label>
                </td>
              </tr>
              <tr>
                <td width="113" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1"> 计划开始日期： </span> </td>
                <td align="left" bgcolor="#FFFFFF"><input size="50" class="Wdate" name="learnplan.starttime" readonly="readonly"
							type="text" onClick="setday(this)" id="learnplan.starttime" value="${learnplan.starttime }"/>
                </td>
              </tr>
              <tr>
                <td width="113" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1"> 计划结束日期： </span> </td>
                <td bgcolor="#FFFFFF"><input size="50" class="Wdate" name="learnplan.endtime" readonly="readonly"
							type="text" onClick="setday(this)" id="learnplan.endtime" value="${learnplan.endtime}"/>
                </td>
              </tr>
              <tr>
                <td width="113" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1"> 计划完成学时： </span></td>
                <td bgcolor="#FFFFFF"><label>
                  <s:textfield name="learnplan.hours" id="learnplan.hours" size="60" />
                  </label>
                </td>
              </tr>
              <tr>
                <td width="113" height="30" align="right" bgcolor="#F0FAFF"><span class="STYLE1"> 学习内容计划： </span></td>
                <td bgcolor="#FFFFFF"><label>
                  <textarea name="learnplan.content" id="ep_description"cols="40" rows="4" >${learnplan.content}</textarea>
                  </label>
                </td>
              </tr>
            </table>
		<div style="text-align: center;">
			<input class="textbg5" type="submit" value="确认提交">
	    </div>
	</s:form>
</body>
</HTML>
