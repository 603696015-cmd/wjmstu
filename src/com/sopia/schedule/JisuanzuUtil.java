package com.sopia.schedule;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sopia.common.J2EEFileUtil;
import com.sopia.common.MD5;

  
/** 
 * 报表计算组工具类 
 * @author TMK 2013-02-18
 */  
public final class JisuanzuUtil {  
	public static final String  DEFAULTFARMAT = "0.0";//默认保留两位小数
	
    /** 
     * 格式化日期 
     * @param obj 日期对象 
     * @param format 格式化字符串 
     * @return 
     * @author ZYWANG 2009-8-26 
     */  
    public static String formatDate(Object obj, String format) {  
        if (obj == null)  
            return "";  
  
        String s = String.valueOf(obj);  
        if (format == null || "".equals(format.trim())) {  
            format = "yyyy-MM-dd";  
        }  
        try {  
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);  
            s = dateFormat.format(obj);  
        } catch (Exception e) {  
        }  
        return s;  
    }  
  
    /** 
     * 格式化数字 
     * @param obj 数字对象 
     * @param format 格式化字符串 
     * @return 
     * @author ZYWANG 2009-8-26 
     */  
    public static String formatNumber(Object obj, String format) {  
        if (obj == null)  
            return "";  
  
        String s = String.valueOf(obj);  
        if (format == null || "".equals(format.trim())) {  
            format = "0.0";  
        }  
        try {  
            if (obj instanceof Double || obj instanceof Float) {  
                if (format.contains("%")) {  
                    NumberFormat numberFormat = NumberFormat.getPercentInstance();  
                    s = numberFormat.format(obj);  
                } else {  
                    DecimalFormat decimalFormat = new DecimalFormat(format);  
                    s = decimalFormat.format(obj);  
                }  
            } else {  
                NumberFormat numberFormat = NumberFormat.getInstance();  
                s = numberFormat.format(obj);  
            }  
        } catch (Exception e) {  
        }  
        return s;  
    }  
    
    /**
     * 返回保留几位小数的格式
     * @param number
     * @return
     */
    public static String getFormatNumber(int number){
    	String temp = "";
    	if(number >2)
	    	for(int i=0;i<number-1;i++){
	    		temp += "0";
	    	}
    	return DEFAULTFARMAT + temp ;
    }
  
    /** 
     * 计算字符串四则运算表达式 
     * @param string 
     * @return 
     * @author ZYWANG 2009-8-31 
     */  
    public static String computeString(String string) {  
        String regexCheck = "[\\(\\)\\d\\+\\-\\*/\\.]*";// 是否是合法的表达式  
  
        if (!Pattern.matches(regexCheck, string))  
            return string;  
  
        Matcher matcher = null;  
        String temp = "";  
        int index = -1;  
        String regex = "\\([\\d\\.\\+\\-\\*/]+\\)";// 提取括号表达式  
        string = string.replaceAll("\\s", "");// 去除空格  
        try {  
            Pattern pattern = Pattern.compile(regex);  
            // 循环计算所有括号里的表达式  
            while (pattern.matcher(string).find()) {  
                matcher = pattern.matcher(string);  
                while (matcher.find()) {  
                    temp = matcher.group();  
                    index = string.indexOf(temp);  
                    string = string.substring(0, index)  
                            + computeStirngNoBracket(temp)  
                            + string.substring(index + temp.length());  
                }  
            }  
            // 最后计算总的表达式结果  
            string = computeStirngNoBracket(string);  
        } catch (NumberFormatException e) {  
            return e.getMessage();  
        }  
        return string;  
    }  
  
    /** 
     * 计算不包含括号的表达式 
     * @param string 
     * @return 
     * @author ZYWANG 2009-8-31 
     */  
    private static String computeStirngNoBracket(String string) {  
        string = string.replaceAll("(^\\()|(\\)$)", "");  
        String regexMultiAndDivision = "[\\d\\.]+(\\*|\\/)[\\d\\.]+";  
        String regexAdditionAndSubtraction = "(^\\-)?[\\d\\.]+(\\+|\\-)[\\d\\.]+";  
  
        String temp = "";  
        int index = -1;  
  
        // 解析乘除法  
        Pattern pattern = Pattern.compile(regexMultiAndDivision);  
        Matcher matcher = null;  
        while (pattern.matcher(string).find()) {  
            matcher = pattern.matcher(string);  
            if (matcher.find()) {  
                temp = matcher.group();  
                index = string.indexOf(temp);  
                string = string.substring(0, index) + doMultiAndDivision(temp)  
                        + string.substring(index + temp.length());  
            }  
        }  
  
        // 解析加减法  
        pattern = Pattern.compile(regexAdditionAndSubtraction);  
        while (pattern.matcher(string).find()) {  
            matcher = pattern.matcher(string);  
            if (matcher.find()) {  
                temp = matcher.group();  
                index = string.indexOf(temp);  
                if (temp.startsWith("-")) {  
                    string = string.substring(0, index)  
                            + doNegativeOperation(temp)  
                            + string.substring(index + temp.length());  
                } else {  
                    string = string.substring(0, index)  
                            + doAdditionAndSubtraction(temp)  
                            + string.substring(index + temp.length());  
                }  
            }  
        }  
  
        return string;  
    }  
  
    /** 
     * 执行乘除法 
     * @param string 
     * @return 
     * @author ZYWANG 2009-8-31 
     */  
    private static String doMultiAndDivision(String string) {  
        String value = "";  
        double d1 = 0;  
        double d2 = 0;  
        String[] temp = null;  
        if (string.contains("*")) {  
            temp = string.split("\\*");  
        } else {  
            temp = string.split("/");  
        }  
  
        if (temp.length < 2)  
            return string;  
  
        d1 = Double.valueOf(temp[0]);  
        d2 = Double.valueOf(temp[1]);  
        if (string.contains("*")) {  
            value = String.valueOf(d1 * d2);  
        } else {  
            value = String.valueOf(d1 / d2);  
        }  
  
        return value;  
    }  
  
    /** 
     * 执行加减法 
     * @param string 
     * @return 
     * @author ZYWANG 2009-8-31 
     */  
    private static String doAdditionAndSubtraction(String string) {  
        double d1 = 0;  
        double d2 = 0;  
        String[] temp = null;  
        String value = "";  
        if (string.contains("+")) {  
            temp = string.split("\\+");  
        } else {  
            temp = string.split("\\-");  
        }  
  
        if (temp.length < 2)  
            return string;  
  
        d1 = Double.valueOf(temp[0]);  
        d2 = Double.valueOf(temp[1]);  
        if (string.contains("+")) {  
            value = String.valueOf(d1 + d2);  
        } else {  
            value = String.valueOf(d1 - d2);  
        }  
  
        return value;  
    }  
  
    /** 
     * 执行负数运算 
     * @param string 
     * @return 
     * @author ZYWANG 2010-11-8 
     */  
    private static String doNegativeOperation(String string) {  
        String temp = string.substring(1);  
        if (temp.contains("+")) {  
            temp = temp.replace("+", "-");  
        } else {  
            temp = temp.replace("-", "+");  
        }  
        temp = doAdditionAndSubtraction(temp);  
        if (temp.startsWith("-")) {  
            temp = temp.substring(1);  
        } else {  
            temp = "-" + temp;  
        }  
        return temp;  
    }  
    
    //右移两位//0.2012=>20.12
    public static String stringPointMoveRight(String str,int number){
    	NumberFormat format=NumberFormat.getNumberInstance() ;
    	format.setMaximumFractionDigits(number);
    	String s= format.format(Double.parseDouble(str)*100) ;
    	return s;
    }
    
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
//		System.out.println(field);
		return field;
	}
    
    //相关字段再次统计的时候计算结果
    public static double jisuan(String formula,Map<String,Double> m){
    	double value = 0;
    	
    	for(String key:m.keySet()){
    		if(formula.indexOf(key)!=-1){
    			formula = formula.replace(key, String.valueOf(m.get(key))) ;
    		}
    	}
    	
    	if(formula!=null&&!formula.equals("")){
    		value = Double.parseDouble(computeString(formula));
    	}
    	
    	return value;
    }
    
  
    public static void main(String[] args) {  
    	
    }  
}  