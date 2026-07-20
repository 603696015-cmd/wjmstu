package com.sopia.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

public class StringUtil {
	public static final Log logger=LogFactory.getLog(StringUtil.class);
	/**
	 * 转成双字节
	 * @param str
	 * @return
	 */
	public static String toSBC(String str) {
		String result_ = "";
		for (int i = 0; i < str.length(); i++) {
			int n = str.charAt(i);
			if (n == 32) {
				result_ += (char) (12288);
			} else if (n >= 33 && n <= 126) {
				result_ += (char) (n + 65248);
			} else {
				result_ += (char) (n);
			}
		}
		return result_;
	}
	public static String shortStr(String str,int length,String app){
		
		return str==null?"":(str.length()>length?(str.substring(0,length)+app):str);
	}
	public static String qshortTitle(String str,int length){
		return shortStr(str, length, "...");
	}
	/**
	 * sql语句模糊查询有用
	 * @param str
	 * @return
	 */
	public static String toLikeStr(String str){
		return str.replaceAll("%", "/%").replaceAll("_", "/_");
	}
	/**
	 * 过滤html的格式
	 * @param html
	 * @return
	 * @throws ParserException
	 */
	public static String htmlParse(String html) throws ParserException{
		TextExtractingVisitor v = new TextExtractingVisitor();
		Parser p = Parser.createParser(html, "UTF-8");//创建htmlparser对象
		p.visitAllNodesWith(v);//大概意思是填充到迭代器
		html= v.getExtractedText();//获取解析过滤后的文本
		return html;
	}
	/**
	 * 过滤html的格式
	 * @param html
	 * @return
	 * @throws ParserException
	 */
	public static String htmlParse_(String html){
		TextExtractingVisitor v = new TextExtractingVisitor();
		Parser p = Parser.createParser(html, "UTF-8");//创建htmlparser对象
		try {
			p.visitAllNodesWith(v);//大概意思是填充到迭代器
			html= v.getExtractedText();//获取解析过滤后的文本
		} catch (ParserException e) {
//			e.printStackTrace();
			logger.error("html解析报错",e);
		}
		return html;
	}
}
