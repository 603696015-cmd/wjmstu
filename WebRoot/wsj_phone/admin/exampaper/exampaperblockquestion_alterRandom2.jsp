<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<span style="color: #ff0000;">${elmessage} </span>
<script type="text/javascript">
<!--
function updateexampaperblockquestions_randomadd(epb_id){
	
	if(epb_id<=0){
		alert("大题修改添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	//请求前作表单验证
	var epQlevel1=<s:property value="epRandom1.qlevel1" />;
	var epQlevel2=<s:property value="epRandom1.qlevel2" />;
	var epQlevel3=<s:property value="epRandom1.qlevel3" />;
	var epQlevel4=<s:property value="epRandom1.qlevel4" />;
	var epQlevel5=<s:property value="epRandom1.qlevel5" />;
	var epQlevel=<s:property value="epRandom1.qlevel" />;
	var epQlevel1_1=<s:property value="epRandom.qlevel1" />;
	var epQlevel1_2=<s:property value="epRandom.qlevel2" />;
	var epQlevel1_3=<s:property value="epRandom.qlevel3" />;
	var epQlevel1_4=<s:property value="epRandom.qlevel4" />;
	var epQlevel1_5=<s:property value="epRandom.qlevel5" />;
	var epQlevel1_=<s:property value="epRandom.qlevel" />;
	var epr_qlevel1=$("#epr_qlevel1").val();
	var epr_qlevel2=$("#epr_qlevel2").val();
	var epr_qlevel3=$("#epr_qlevel3").val();
	var epr_qlevel4=$("#epr_qlevel4").val();
	var epr_qlevel5=$("#epr_qlevel5").val();
	var epr_qlevel=$("#epr_qlevel").val();
	var ts=/^[\d]{0,}$/;
	for(var i=1;i<=6;i++){  
		if(i!=6 && !ts.test($.trim(eval("$('#epr_qlevel"+i+"')\.val()")))){
			alert(i+"级试题中有非数字！");
			return false;
		}else{
			if(i==6 && !ts.test($.trim($('#epr_qlevel').val()))){
				alert("不限级试题中有非数字！");
				return false;
			}
		}
	}
	for(var i=1;i<=6;i++){
		if(i!=6 && eval("epr_qlevel"+i)>eval("epQlevel"+i)&&eval("epr_qlevel"+i)!=""){
			alert(i+"级试题超过试题总数！");
			return false;
		}else{
			if(i==6 && epr_qlevel>epQlevel&&epr_qlevel!=""){
				alert("不限级试题超过试题总数！");
				return false;
			}
		}
	}
	var questionCount=<s:property value="epBlock.questionamount" />;//题目的设置量
	var realqCount=<s:property value="epBlock.realqamount" />;//题目的实际数量
	//输入的值总和
	var questionSum=parseInt(epr_qlevel1)+parseInt(epr_qlevel2)+parseInt(epr_qlevel3)+parseInt(epr_qlevel4)+parseInt(epr_qlevel5)+parseInt(epr_qlevel);
	//原来的值总和
	var questionSum_=parseInt(epQlevel1_1)+parseInt(epQlevel1_2)+parseInt(epQlevel1_3)+parseInt(epQlevel1_4)+parseInt(epQlevel1_5)+parseInt(epQlevel1_);
	//alert(questionSum_);
	//alert(questionSum);
	//alert(realqCount);
	//alert(questionCount);
	if(questionSum=="" || questionSum==0){
		alert("输入的题量不能少于1，请正确输入！");
		return false;
	}
	realqCount=realqCount-questionSum_;
	if(parseInt(questionSum)+parseInt(realqCount)>parseInt(questionCount)){
		alert("实际题量大于设置的题量！");
		return false;
	}
	$.post("exampaperblockquestion_alterRandom.action", {
		"epBlock.id":epb_id,
		"epRandom.qlevel1":$("#epr_qlevel1").val(),
		"epRandom.qlevel2":$("#epr_qlevel2").val(),
		"epRandom.qlevel3":$("#epr_qlevel3").val(),
		"epRandom.qlevel4":$("#epr_qlevel4").val(),
		"epRandom.qlevel5":$("#epr_qlevel5").val(),
		"epRandom.qlevel":$("#epr_qlevel").val(),
		"epRandom.id":$("#epr_id").val(),
		"x":Math.random()
	}, 
	function (data) {
		//$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	listexampaperblockquestions(epb_id);
}
//-->
</script>
<table width="900" align="center" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB" style="margin: 0px">
	<tr>
		<td width="160" align="center" >
			试题库
		</td>
		<td >
			<label>
				<s:property value="question.qlib.name" />
				<s:if test="epRandom.suboperate==1">
					(包含下级题库)
				</s:if>
				<s:else>
					(不包含下级题库)
				</s:else>
				<input name="epRandom.id"
					value="<s:property value="epRandom.id"/>" id="epr_id" type="hidden" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			1级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel1" type="text" id="epr_qlevel1" size="10"
					value="<s:property value="epRandom.qlevel1"/>" />
				/ 总数
				<s:property value="epRandom1.qlevel1" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			2级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel2" type="text" id="epr_qlevel2" size="10"
					value="<s:property value="epRandom.qlevel2"/>" />
				/ 总数
				<s:property value="epRandom1.qlevel2" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			3级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel3" type="text" id="epr_qlevel3" size="10"
					value="<s:property value="epRandom.qlevel3"/>" />
				/ 总数
				<s:property value="epRandom1.qlevel3" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			4级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel4" type="text" id="epr_qlevel4" size="10"
					value="<s:property value="epRandom.qlevel4"/>" />
				/ 总数
				<s:property value="epRandom1.qlevel4" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			5级
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel5" type="text" id="epr_qlevel5" size="10"
					value="<s:property value="epRandom.qlevel5"/>" />
				/ 总数
				<s:property value="epRandom1.qlevel5" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			不限
		</td>
		<td >
			<label>
				<input name="epRandom.qlevel" type="text" id="epr_qlevel"
					value="<s:property value="epRandom.qlevel"/>" size="10" />
				/ 总数
				<s:property value="epRandom1.qlevel" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="160" align="center" >
			&nbsp;
		</td>
		<td >
			<input type="button" class="textbg6" name="button" onclick="updateexampaperblockquestions_randomadd(<s:property value="epBlock.id"/>)" value="确认修改" />
			<input type="button" class="textbg6" name="button" onclick="listexampaperblockquestions(<s:property value="epBlock.id"/>)" value="取消" />
		</td>
	</tr>
</table>