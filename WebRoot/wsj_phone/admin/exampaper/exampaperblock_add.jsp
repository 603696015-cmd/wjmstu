<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
<!--
	function getQuestionInfo(){
		var qtype=$("#epbtype").val();
		var qtypeName=$("#epbtype").find("option:selected").text();
		if(window.confirm("确认查看"+qtypeName+"题量？  ")){
			width=400;
			height=450;
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			window.showModalDialog("questionCountInfo.action?sublibs=1&question.qtype="+qtype+"&x="+Math.random(),null,sFeature);
		}
	}
//-->
</script>
<table width="560" align="center" cellpadding="1" cellspacing="1"
	bgcolor="#EBEBEB">
	<tr>
		<td width="100" align="center" >
			<span class="neededitem">*</span>大题名称
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<input name="epBlock.title" type="text" id="epbtitle" size="60" />
			</label>
		</td>
	</tr>
	<tr>
		<td width="100" align="center" >
			大题说明
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<label>
				<textarea name="epBlock.description" id="epbdesc" cols="40" rows="4"></textarea>
			</label>
		</td>
	</tr>
	<tr>
		<td width="100" align="center" >
			<span class="neededitem">*</span>题型
		</td>
		<td >
			<select onchange="epblockshowrule()" name="epBlock.type" id="epbtype">
				<option value="1" id="type_1">
					判断题
				</option>
				<option value="2" id="type_2">
					单项选择题
				</option><!--
				<option value="3" id="type_3">
					不定项选择题
				</option>
				--><option value="4" id="type_4">
					多项选择题
				</option>
				<option value="5" id="type_5">
					填空题
				</option>
				<option value="6" id="type_6">
					问答题
				</option>
				<option value="7" id="type_7">
					材料题
				</option>
			    <option value="8" id="type_8">
					打字题
				</option>
				<option value="9" id="type_9">
					邮件题
				</option>
				<option value="10" id="type_10">
					搜索题
				</option>
				<option value="11" id="type_11">
					office题
				</option>
				<option value="12" id="type_12">
					选做题
				</option>
				<option value="15" id="type_15">
					看图选择
				</option>
				<option value="16" id="type_16">
					看动画选择
				</option>
				<option value="17" id="type_17">
					角色扮演
				</option>
				<option value="18" id="type_18">
					听音选图
				</option>
				<option value="19" id="type_19">
					拖拽题
				</option>
				<option value="20" id="type_20">
					排序题
				</option>
				
			</select>
		</td>
		<td width="100" align="center" >
			<span class="neededitem">*</span>出题方式
		</td>
		<td >
			<input type="radio" onclick="epblockshowrule()" name="epBlock.random" value="0" checked="checked" />
			<label>
				手工
			</label>
			<input type="radio" onclick="epblockshowrule()" name="epBlock.random" value="1"  />
			<label>
				随机
			</label>
		</td>
	</tr>
	<tr >
		<td width="100" align="center"  >
			<span style="font-weight:bolder;" id="bl_eachscorequestionamount"><span class="neededitem">*</span>试题总数</span>
		</td>
		<td >
			<label>
				<input type="text" name="epBlock.questionamount" id="questionamount"
					size="6" value="0" />
			</label>
			<span><a href="javascript:getQuestionInfo();"  class="textbg6">查看题量</a></span>
		</td>
		<td width="100" align="center" >
			<span style="font-weight:bolder;"><span class="neededitem">*</span>每题分数</span>
		</td>
		<td >
			<label>
				<input name="epBlock.eachscore" type="text" id="epbeachscore"
					value="0" size="6" />
			</label>
		</td>
		
	</tr>
	<tr >
		<td width="100" align="center"  >
			<span style="font-weight:bolder;" ><span class="neededitem">*</span>答题时长</span>
		</td>
		<td >
			<label>
				<input type="text" name="epBlock.answerTime" id="answerTimet"
					size="6" value="0" />
			</label>
		</td>
		<td width="100" align="center" >
			<span style="font-weight:bolder;"><span class="neededitem">*</span>第二次答对得分</span>
		</td>
		<td >
			<label>
				<input name="epBlock.secondScore" type="text" id="secondScore"
					value="0" size="6" />
			</label>
		</td>
		
	</tr>
	<tr id="rule_tr" style="display: none;">
		<td width="100" align="center" >
			<span class="neededitem">*</span>评分规则
		</td>
		<td colspan="3" id="rule_td">
		</td>		
	</tr>
	<tr style="display:none" id="rule_td_dz">
		<td width="100" align="center" >
			<span class="neededitem">*</span>范文最少字数
		</td>
		<td colspan="3">
					<input type="text" size="4" id="dazi_fwsize"
					value="<s:property value="epBlock.fwsize"/>" d-value="<s:property value="epBlock.fwsize"/>"/>
		</td>		
	</tr>
	<tr>
		<td width="100" align="center" >
		</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<input type="button" onclick="addepblock();" style="width:80px;" class="textbg4" name="button" id="button" value="确认添加" />
			<input type="button" onclick="dia_close();return false;" style="width:40px;" class="textbg4" name="button" id="button" value="取消" />
		</td>
	</tr>
</table>
<script type="text/javascript">

	function addepblock(){
	var ep_id = $("#ep_id").val();
	var epbtitle = $("#epbtitle").val();
	var epbeachscore = $("#epbeachscore").val()
	var questionamount = $("#questionamount").val()
	var epbdesc = $("#epbdesc").val()
	var epbtype = $("#epbtype").val()
	var eprandom =$(":radio[name='epBlock.random'][checked]").val();
	var answerTimet = $("#answerTimet").val();
	var secondScore = $("#secondScore").val();
	if(ep_id<=0){
		alert("添加大题发生错误！请确定试卷是否存在！");
		return false;
	}
	if(epbtitle==''){
		alert("请填写大题名称 ");
		$("#epbtitle").focus();
		return false;
	}
	
	if(epbeachscore==''){
		alert("请填写每题分数 ");
		$("#epbeachscore").focus();
		return false;
	}
	
	
	if(parseFloat(epbeachscore)==0){
		alert("每题分数必须大于0");
		$("#epbeachscore").focus();
		return false;
	}
	if(questionamount==''){
		alert("请填写	试题总数 ");
		$("#questionamount").focus();
		return false;
	}
	if(parseInt(questionamount)==0){
		alert("试题总数必须大于0");
		$("#questionamount").focus();
		return false;
	}
	var ts=/^\d+$|^\d+\.?\d+$/;
	if(!ts.test($.trim(epbeachscore))){
		alert("每题分数不能为非数字型 ");
		$("#epbeachscore").focus();
		return false;
	}
	var checkepbeachscore=/^\d+(\.\d{1})?(\.\d{2})?(\.\d{3})?$/;
	if(!checkepbeachscore.test(epbeachscore)){
		alert("每题分数不能超过三位小数！");
		$("#epbeachscore").focus();
		return false;
	}
	if(!ts.test($.trim(questionamount))){
		alert("试题总数不能为非数字型 ");
		$("#questionamount").focus();
		return false;
	}
	var checkquestionamount =/^\d+$/ ;
	if(!checkquestionamount.test(questionamount)){
		alert("试题总数必须为整数，且必须大于0");
		$("#questionamount").focus();
		return false;
	}
	
	if(parseInt(answerTimet)==0){
		alert("答题时长大于0 ");
		$("#answerTimet").focus();
		return false;
	}
	
	if(parseFloat(secondScore)==0){
		alert("答对得分大于0 ");
		$("#secondScore").focus();
		return false;
	}
	
	if((epbtype==12)&&eprandom==1){
		alert("大题题型是选做题不能使用随机组卷！");
		return false;
	}
	var rulestring= "";
	if( epbtype==12){
		//rulestring = $("#rules1").val()+"-=SpRule-"+$("#rules2").val()+
		//	"-=SpRule-"+$("#rules3").val();
		rulestring = $("#rules1").val()+"-=SpRule-"+questionamount+
			"-=SpRule-"+epbeachscore;
	}
	if( epbtype==9&&eprandom==1 ){
			rulestring = $("#rules1").val()+"-=SpRule-"+$("#rules2").val()+
			"-=SpRule-"+$("#rules3").val()+"-=SpRule-"+$("#rules4").val()+"-=SpRule-"+$("#rules5").val()+"-=SpRule-"+$("#rules6").val() ;
	}
	var fwsize = 0;
	if(epbtype==8&&eprandom==1){
		rulestring = $("#rules1").val()+"-=SpRule-"+$("#rules2").val() +"-=SpRule-"+$("#rules3").val()+"-=SpRule-" ;
		if(dazi<=1)
		{
			alert("该打字题未设置年龄段速度，请设置！");
			return false;
		}
		if(!checkdazirule()){
			return false;
		}
		for(var jj=1;jj<dazi;jj++){
			rulestring+=$("#b_dazirules"+jj).val ()+":"+$("#e_dazirules"+jj).val ()+
			":"+$("#jg_dazirules"+jj).val ()+":"+$("#yx_dazirules"+jj).val ()+":"+$("#mf_dazirules"+jj).val ()+":"
		}
		if(parseInt($("#rules1").val())<parseFloat($("#rules1").attr("d-value"))){
			alert("范文最少字数不可少于最大满分速度*规定时间，请重新设定！");
			return false;
		}
		if(!window.confirm("确定不需要修改范文最少字数？")){
			return false;
		}
		fwsize = $("#rule1").val();
	}
	//if(epbtype==10&&eprandom==1){
	//	rulestring = $("#rules0").val()+"-=SpRule-";
	//}
	if(!window.confirm("确定提交？")) return false;
	$.post("exampaperblock_add.action", {
		"examPaper.id": ep_id,
		"epBlock.title": epbtitle,
		"epBlock.eachscore": epbeachscore,
		"epBlock.questionamount": questionamount,
		"epBlock.description": epbdesc,
		"epBlock.type": epbtype,
		"epBlock.random": eprandom,
		"epBlock.rulestring": rulestring,
		"epBlock.fwsize":$("#dazi_fwsize").val(),
		"epBlock.answerTime":answerTimet,
		"epBlock.secondScore":secondScore,
		"x":Math.random()
	}, 
	function (data) {
		dia_close();
		listepblocks(ep_id);
	});
}

</script>
