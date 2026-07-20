package com.sopia.lucene.main;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

import com.sopia.lucene.file.FileSeach;


public class Main {
	public static void main(String[] args) throws IOException, ParseException, XmlException, OpenXML4JException {
		FileSeach.init("D:/Tomcat6.0/webapps/beijing/elstuffs/1193");
//		System.out.println(FileSeach.seachFile("全文检索", 0, 1).getBeans());
	}
}
