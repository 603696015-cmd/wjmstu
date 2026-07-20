package com.sopia.lucene.reader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

public class WordReader {
	public static String getWordText2007(File file) throws IOException, XmlException, OpenXML4JException {
		XWPFWordExtractor extractor = new XWPFWordExtractor(POIXMLDocument.openPackage(file.getAbsolutePath()));
		return extractor.getText().trim();
	}

	public static String getWordText2003(File file) throws IOException, XmlException, OpenXML4JException {
		WordExtractor extractor = new WordExtractor(FileUtils.openInputStream(file));
		return extractor.getText().trim();
	}

}
