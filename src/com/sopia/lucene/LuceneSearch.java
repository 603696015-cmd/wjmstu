package com.sopia.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneSearch {
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static void main(String[] args){
		LuceneSearch search = new LuceneSearch();
		search.searchKeyword("李");
	}
	
	// 声明IndexSearcher对象
	private IndexSearcher searcher = null;
	// 声明Query对象
	private Query query = null;
	
	public String searchKeyword(String keyword){
		String indexPath = "d:\\lucene\\index";  //指明索引所在文件夹
		 try {
			searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(indexPath))));//创建索引器
			
			System.out.println("正在检索关键字：" + keyword);
			//将关键字包装为Query对象
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			String field = "content";
			QueryParser parser = new QueryParser(Version.LUCENE_36,field,analyzer);
			query = parser.parse(keyword);
			
			Date start = new Date();
			searcher.search(query,null,100);  //使用索引器检索
			Date end = new Date();
			System.out.println("检索完成，用时:"+(end.getTime()-start.getTime())+"毫秒");
			
			TopDocs results = searcher.search(query, 10);
			ScoreDoc[] hits = results.scoreDocs;
			Document doc = null;
			List<String> cons = new ArrayList<String>();
			for(ScoreDoc scoreDoc:hits){
				doc = searcher.doc(scoreDoc.doc);
			//	content = doc.get("content");
			//	System.out.println(content);
				String path = doc.get("path");
				cons.add(path);
			}
			searcher.close();
			for(String c:cons){
				System.out.println(c);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
}
