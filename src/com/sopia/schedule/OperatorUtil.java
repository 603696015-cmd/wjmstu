package com.sopia.schedule;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作符Util
 * @author Administrator
 *
 */
public class OperatorUtil {
	public static final String COUNT_OPERATOR = "count";
	public static final String MIN_OPERATOR = "min";
	public static final String MAX_OPERATOR = "max";
	public static final String SUM_OPERATOR = "sum";
	public static final String AVG_OPERATOR = "avg";
	public static final String TOTAL_OPERATOR = "total";
	
	
	public static final String BIG = "big";
	public static final String SMALL = "small";
	
	public static final String[] OPREATORS = new String[]{"count","min","max","sum","avg","total"};
	public static final String[] OPREATORS_WITHOUT_TOTAL = new String[]{"count","min","max","sum","avg"};
	public static final String[] OPREATORS_BIG = new String[]{"COUNT","MIN","MAX","SUM","AVG","TOTAL"};
	
	//判断计算方式是否包含"count","min","max","sum","avg",total
	public static boolean isStringInOperator(String str,String big_or_small){
		boolean flag = false;
		String[] ops = OPREATORS;
		if(big_or_small.equals(BIG)){
			ops = OPREATORS_BIG;
		}
		for(int i=0;i<ops.length;i++){
			if(str.indexOf(ops[i])!=-1){
				flag = true;
			}
		}
		return flag;
	}
	//判断计算方式是否包含"count","min","max","sum","avg"
	public static boolean isStringInOperatorWithoutTotal(String str){
		boolean flag = false;
		for(int i=0;i<OPREATORS_WITHOUT_TOTAL.length;i++){
			if(str.indexOf(OPREATORS[i])!=-1){
				flag = true;
			}
		}
		return flag;
	}
	
	//map中key是否包含操作符
	
	//获取操作列操作符
	public static String[] isStringInOperatorReturnOperator(String str,String[] OPERATORS_){
		String string = "";
		for(int i=0;i<OPERATORS_.length;i++){
			if(str.indexOf(OPERATORS_[i])!=-1){
				string += OPERATORS_[i] + ",";
			}
		}
		if(string != null && !string.equals("")){
			string = string.substring(0,string.lastIndexOf(","));
		}
		return string.split(",");
	}
	
	//将sum(GZJH.GZJH_ZWPF)转换为SUMGZJHGZJH_ZWPF
	public static String[] getOperatorField(String str,int type){//sum(GZJH.GZJH_ZWPF)
		String[] ary = type == 2?isStringInOperatorReturnOperator(str,OPREATORS):isStringInOperatorReturnOperator(str,OPREATORS_WITHOUT_TOTAL);//{"sum"}
		String field = "";
		String temp_field = "";
		String[] returnArray = null;
		for(int i=0;i<ary.length;i++){
			int index = str.indexOf(ary[i]);
			if(index!=-1){
				int index_ = index + ary[i].length();
				temp_field = str.substring(index_+1,str.indexOf(")"));
				field += ary[i].toUpperCase()+temp_field.replace(".", "") + ",";//SUMGZJHGZJH_ZWPF
			}
		}
		if(field != null && !field.equals(""))
			returnArray = field.substring(0, field.lastIndexOf(",")).split(",");
		return returnArray;
	}
	
	//将sum(GZJH_ZWPF)/total(GZJH_ZWPF)转换为SUMGZJH.GZJH_ZWPF,TOTALGZJH.GZJH_ZWPF数组
	public static String[] getOperatorField_(String str,int type){//sum(GZJH_ZWPF)
		String[] ary = type == 2?isStringInOperatorReturnOperator(str,OPREATORS):isStringInOperatorReturnOperator(str,OPREATORS_WITHOUT_TOTAL);//{"sum"}
		String field = "";
		String[] returnArray = null;
		for(int i=0;i<ary.length;i++){
			//如有多个操作符，遍历sum(GZJH_ZWPF)+sum(GZJH_LDPF)
			if(ary[i] != null && !ary[i].equals("")){
				field = FabOperator(str,ary[i],field);
				//计算组中去除已经转换的字段
				if(field!=null&&!field.equals("")){
					String[] f = field.split(",");
					for(int j=0;j<f.length;j++){
						str = str.replace(getOperatorField_return(f[j]), "");
					}
				}
			}
		}
		if(field != null && !field.equals(""))
			returnArray = field.substring(0, field.lastIndexOf(",")).split(",");
		return returnArray;
	}
	
	//将sum(GZJH_ZWPF)/total(GZJH_ZWPF)转换为SUMGZJH.GZJH_ZWPF,TOTALGZJH.GZJH_ZWPF字符串
	public static String getOperatorFieldString_(String str,int type){//sum(GZJH_ZWPF)
		String[] ary = type == 2?isStringInOperatorReturnOperator(str,OPREATORS):isStringInOperatorReturnOperator(str,OPREATORS_WITHOUT_TOTAL);//{"sum"}
		String field = "";
		String[] returnArray = null;
		for(int i=0;i<ary.length;i++){
			//如有多个操作符，遍历sum(GZJH_ZWPF)+sum(GZJH_LDPF)
			if(ary[i] != null && !ary[i].equals(""))
				field = FabOperator(str,ary[i],field);
		}
		if(field != null && !field.equals(""))
			field.substring(0, field.lastIndexOf(",")).split(",");
		return field;
	}
	
	
	
	//Feb获取所有需要操作的字段
	//sum(GZJH_ZWPF)/total(GZJH_ZWPF)
	public static String FabOperator(String str,String operator,String field){//sum(GZJH_ZWPF)+sum(GZJH_LDPF)
		String temp_field = "";
		String table_field = "";
		int index = str.indexOf(operator);
		if(index!=-1){
			int index_ = index + operator.length();
			int index_end = str.indexOf(")");
			temp_field = str.substring(index_+1,index_end);
			if(temp_field != null && !temp_field.equals(""))
				table_field = temp_field.split("_")[0] + "." + temp_field;
			field += operator.toUpperCase()+table_field + ",";//SUMGZJH.GZJH_ZWPF,
			str = str.replace(str.substring(index, index_)+"("+temp_field+")","");//+sum(GZJH_LDPF)
			field = FabOperator(str,operator,field);
		}
		return field;
	}
	
	//将sum(GZJH_ZWPF)转换为SUMGZJHGZJH_ZWPF
	public static String[] getOperatorField_without_point(String str,int type){//sum(GZJH_ZWPF)
		String[] ary = type == 2?isStringInOperatorReturnOperator(str,OPREATORS):isStringInOperatorReturnOperator(str,OPREATORS_WITHOUT_TOTAL);//{"sum"}
		String field = "";
		String temp_field = "";
		String table_field = "";
		String[] returnArray = null;
		for(int i=0;i<ary.length;i++){
			int index = str.indexOf(ary[i]);
			if(index!=-1){
				int index_ = index + ary[i].length();
				temp_field = str.substring(index_+1,str.indexOf(")"));
				if(temp_field != null && !temp_field.equals(""))
					table_field = temp_field.split("_")[0]  + temp_field;
				field += ary[i].toUpperCase()+table_field + ",";//SUMGZJH.GZJH_ZWPF
			}
		}
		if(field != null && !field.equals(""))
			returnArray = field.substring(0, field.lastIndexOf(",")).split(",");
		return returnArray;
	}
	
	
	//SUMGZJH.GZJH_ZWPF=>sum(GZJH_ZWPF)
	public static String getOperatorField_return(String str){
		String returnValue = "";
		String operatorTablename = str.substring(0,str.indexOf("."));//SUMGZJH
		String columnname = str.substring(str.indexOf(".")+1,str.length());//GZJH_ZWPF
		String[] columnArr = columnname.split("_");
		if(operatorTablename.indexOf(columnArr[0])!=-1){
			operatorTablename = operatorTablename.replace(columnArr[0], "").toLowerCase();
			returnValue = operatorTablename + "(" + columnname + ")";
		}
		
		return returnValue;
	}
	
	
	
	//根据计算方式获取表名
	public static String getTablenameByFormula(String str,int type){
		String[] ary = type == 2?isStringInOperatorReturnOperator(str,OPREATORS):isStringInOperatorReturnOperator(str,OPREATORS_WITHOUT_TOTAL);//{"sum"}
		String field = "";
		String table_field = "";
		for(int i=0;i<ary.length;i++){
			int index = str.indexOf(ary[i]);
			if(index!=-1){
				int index_ = index + ary[i].length();
				field = str.substring(index_+1,str.indexOf(")"));
				if(field != null && !field.equals(""))
					table_field = field.split("_")[0] ;
			}
		}
		return table_field;
	}
	
	
	public static String getFormatFormula(String formula,String total){//sum(GZJH_ZWPF)/total(GZJH_ZWPF)=>total(GZJH_ZWPF)
		String[] ary = isStringInOperatorReturnOperator(formula,OPREATORS);//{"sum"}
		for(int i=0;i<ary.length;i++){
			if(ary[i].equals(total) ){
				int index = formula.indexOf(total);
				formula = formula.substring(index,formula.length());
				formula = formula.replace(total, "sum");
			}
		}
		return formula;
	}	
	
	//将sum替换为TOTAL		sum(GZJH_ZWPF)=>TOTALGZJH.GZJH_ZWPF
	public static String replaceOperatorByTotal(String formula,String total,int type){
		String[] ary = type == 2?isStringInOperatorReturnOperator(formula,OPREATORS):isStringInOperatorReturnOperator(formula,OPREATORS_WITHOUT_TOTAL);
		for(int i=0;i<ary.length;i++){
			int index = formula.indexOf(ary[i]);
			if(index!=-1){
				formula = formula.replace(ary[i], total);//total(GZJH_ZWPF)
			}
		}
		return getOperatorField_(formula,2)[0];
	}
}
