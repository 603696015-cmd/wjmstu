package com.sopia.lucene.reader;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFReader {
	private static PDFTextStripper pdfStripper;
	static {
		try {
			pdfStripper = new PDFTextStripper();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPDFtext(File file) throws IOException {
		PDDocument doc = PDDocument.load(file);
		String text = pdfStripper.getText(doc);// 获取文本
		doc.close();
		return text.trim();
	}

}
