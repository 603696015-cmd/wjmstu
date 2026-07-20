<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>  
		<input type="hidden" name="<s:property value="input_name"/>" value="<s:property value="examRoom.id"/>">  
		<s:iterator value="examPapers">  
		<input type="hidden" name="elUser.epids" value="<s:property value="id"/>">  
				试卷【<s:property value="title" /> 】得分：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:textfield name="elUser.Kcsq"	id="Kcsq" value="不限"/>~<s:textfield name="elUser.Kcsq_"	id="Kcsq_" value="不限"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				考试次数:<s:textfield name="elUser.Kclxcs"	id="Kclxcs" value="不限"/>~<s:textfield name="elUser.Kclxcs_"	id="Kclxcs_" value="不限"/> <br/>
		</s:iterator>
