package com.sopia.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.store.SimpleFSLockFactory;
import org.apache.lucene.util.Version;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

import com.sopia.lucene.Conext;
import com.sopia.lucene.reader.ExcelReader;
import com.sopia.lucene.reader.PDFReader;
import com.sopia.lucene.reader.PptReader;
import com.sopia.lucene.reader.WordReader;


public class IndexFactory {
//	public static Analyzer analyzer = new IKAnalyzer();
	public static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	public static Directory directory;
	private IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36, analyzer);
	private File indexFile = new File(Conext.INDEX_PATH);
	private IndexWriter indexWriter;

	public IndexFactory() throws IOException {
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);// 生成索引为覆盖操作
		indexWriterConfig.setMaxBufferedDocs(100);// 设置最大缓存文档数
		// 这种目录存在锁机制，在打开目录时，写的权利一次只分给一个用户；有效保证了索引文件不会因为多线程问题，同时写索引导致文件损坏。
		directory = new SimpleFSDirectory(indexFile, new SimpleFSLockFactory());
		indexWriter = new IndexWriter(directory, indexWriterConfig);
	}

	private Map<String, List<FileBean>> getDoces(String path) {
		Map<String, List<FileBean>> map = new HashMap<String, List<FileBean>>();
	//	for (String path : Conext.DOC_PATHS) {
			File file = new File(path);
			System.out.println("path==="+path);
			List<FileBean> list = new ArrayList<FileBean>();
			if (file.exists()) {
				for (File fs : file.listFiles()) {
					setFiles(list, fs);
				}
			}
			map.put(path, list);
	//	}
		return map;
	}

	private void setFiles(List<FileBean> list, File file) {
		if (!file.isDirectory()) {
			FileBean fileBean = new FileBean(file);
			list.add(fileBean);
			return;
		}
		for (File fs : file.listFiles()) {
			setFiles(list, fs);
		}
	}

	private void builderDoc(String path) throws IOException, XmlException, OpenXML4JException {
		// 很重要 如果指定的目录存在索引就不创建索引了
		Map<String, List<FileBean>> map = getDoces(path);
		if (indexFile.listFiles().length > 1) {
			System.out.println("检验到索引存在,跳过索引创建,将使用所指定的索引...");
			indexWriter.close();
			return;
		}
		System.out.println("索引不存在,创建索引...");
		Long start = System.currentTimeMillis();
		for (Entry<String, List<FileBean>> entry : map.entrySet()) {
			for (FileBean bean : entry.getValue()) {
				Document document = new Document();
				String contents = "";
				String type = bean.getType();
				File file = bean.getFile();

				// pdf
				if (type.equalsIgnoreCase("pdf")) {
					contents = PDFReader.getPDFtext(file);
				}
				// excel 2003
				else if (type.equalsIgnoreCase("xls")) {
					contents = ExcelReader.getExcelText2003(file);
				}
				// excel 2007
				else if (type.equalsIgnoreCase("xlsx")) {
					contents = ExcelReader.getExcelText2007(file);
				}
				// word 2003
				else if (type.equalsIgnoreCase("doc")) {
					contents = WordReader.getWordText2003(file);
				}
				// word 2007
				else if (type.equalsIgnoreCase("docx")) {
					contents = WordReader.getWordText2007(file);
				}

				// ppt 2003
				else if (type.equalsIgnoreCase("ppt")) {
					contents = PptReader.getPttText2003(file);
				}
				// ppt 2007
				else if (type.equalsIgnoreCase("pptx")) {
					contents = PptReader.getPttText2007(file);
				}

				// txt html xml ..
				else {
					contents = IOUtils.toString(FileUtils.openInputStream(file), "gbk").trim();
				}
				document.add(new Field("contents", contents, Store.YES, Index.ANALYZED));
				document.add(new Field("name", bean.getName(), Store.YES, Index.ANALYZED));
				document.add(new Field("type", bean.getType(), Store.YES, Index.ANALYZED));
				document.add(new Field("path", bean.getPath(), Store.YES, Index.NOT_ANALYZED));
				indexWriter.addDocument(document);
			}
		}
		indexWriter.forceMerge(1);
		indexWriter.close();
		System.out.println("索引创建完成,花费了" + ((System.currentTimeMillis() - start) / 1000) + " 秒");
	}

	public Directory getDir() {
		return this.directory;
	}

	public Analyzer getAnalyzer() {
		return this.analyzer;
	}

	public IndexWriter getIndexWriter() {
		return this.indexWriter;
	}

	public void createIndex(String path) throws IOException, XmlException, OpenXML4JException {
		builderDoc(path);
	}
}
