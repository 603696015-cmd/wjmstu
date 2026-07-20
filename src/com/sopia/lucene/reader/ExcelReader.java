package com.sopia.lucene.reader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static String getExcelText2003(File file) throws IOException {
		return getExcelText(new HSSFWorkbook(FileUtils.openInputStream(file)));
	}

	public static String getExcelText2007(File file) throws IOException {
		return getExcelText(new XSSFWorkbook(FileUtils.openInputStream(file)));
	}

	private static String getExcelText(Workbook workbook) throws IOException {
		StringBuilder data = new StringBuilder(" ");
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						if (cell != null) {
							data.append(cell + "\n");
						}
					}
				}
			}
		}
		return data.toString().trim();
	}

}
