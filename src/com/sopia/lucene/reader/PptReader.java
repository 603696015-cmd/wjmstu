package com.sopia.lucene.reader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.xmlbeans.XmlException;

public class PptReader {
	public static String getPttText2003(File file) throws IOException {
		return new PowerPointExtractor(FileUtils.openInputStream(file.getAbsoluteFile())).getText().trim();

	}

	public static String getPttText2007(File file) throws IOException, XmlException, OpenXML4JException {
		return new XSLFPowerPointExtractor(POIXMLDocument.openPackage(file.getAbsolutePath())).getText().trim();
	}

}
