<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训批次统计列表" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%" align="center" cellpadding="1" cellspacing="1">
			<tr>
				<td style="padding: 0px">
					<s:if test="batchList.size==0">没有符合条件的培训批次</s:if>
					<s:else>
						<table width="100%" style="margin: 0px" align="left"
							cellpadding="0" cellspacing="1">
							<tr>
								<th align="center" bgcolor="#FFFFFF" width="30%">
									批次名称
								</th>
								<th align="center">
									学员人数
								</th>
								<th align="center">
									通过人数
								</th>
								<th align="center">
									通过率
								</th>
								<th align="center">
									查看详情
								</th>
								<th align="center" bgcolor="#FFFFFF" width="20%">&nbsp;
									

								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="batchs">
									<tr>
										<td height="30px" align="center">
											<s:property value="name" />
										</td>
										<td align="center">
											<s:property value="userCount" />
										</td>
										<td align="center">
											<s:property value="userPassedCount" />
										</td>
										<td align="center">
											<s:property value="passper" />
										</td>
										<td align="center">
											<a href="stat_class_batch_view.action?batch.id=${id}"
												class=textbg4>查看</a>
										</td>
										<td align="center">
											<a href="class_batch_alterinit.action?batch.id=${id}"
												class=textbg4>修改</a>
											<a href="class_batch_delete.action?batch.id=${id}"
												onclick="return window.confirm('确定删除此批次？')" class=textbg4>删除</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</s:else>
				</td>
			</tr>
		</table>
		<div style="width: 100%; text-align: center;">
      <wysLib:page></wysLib:page>
			<br />
			<a href="class_batch_addinit.action" class="textbg4"
				style="width: 120px">添加培训批次</a>

			<a class="textbg4" href="dep_class_view.action">返回</a>
		</div>
	</body>
</HTML>
