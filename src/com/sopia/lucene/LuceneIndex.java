package com.sopia.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndex {
	public static void main(String[] args){
		LuceneIndex index = new LuceneIndex();
		Date start = new Date();
		try {
			index.writeToIndex("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date end = new Date();
		System.out.println("建立索引用时"+(end.getTime()-start.getTime())+"毫秒");
		index.close();
	}

	//索引器
	private IndexWriter writer = null;
	public LuceneIndex(){
		//
		try{
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			String indexPath = "d:\\lucene\\index";  //索引文件存放位置
			Directory dir = FSDirectory.open(new File(indexPath));
			//建立索引器，指定索引存放目录,分析器--new StandardAnalyzer()
			writer = new IndexWriter(dir,iwc);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private Document getDocument(File f){
		//将要建立索引的文件构造成Document对象，并添加域content
		Document doc = new Document();
		BufferedReader bufReader = null;
		try{
			bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//添加内容
		doc.add(new Field("content",bufReader));
		doc.add(new Field("path",f.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		System.out.println("!!!!!!!"+doc.get("content"));
		return doc;
	}
	
	public void writeToIndex(String path)throws Exception {
		// 将目录d:\\lucene\\test下的文件，先通过getDocument(File)函数，
		//构造成Document，然后添加到索引器writer
		File folder = new File("d:\\lucene\\test");
	//	File folder = new File(path);
	//	System.out.println("路径="+path);
		if(folder.isDirectory()){
			File[] list = folder.listFiles();
			for(File f:list){
				Document doc = getDocument(f);
				try{
					System.out.println("建立索引:"+f);
					writer.addDocument(doc);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			writer.close();
		}
	}	
	
	//关闭索引
	private void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
