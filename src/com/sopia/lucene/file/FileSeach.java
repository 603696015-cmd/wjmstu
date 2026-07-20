package com.sopia.lucene.file;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

import com.sopia.lucene.index.FileBean;
import com.sopia.lucene.index.IndexFactory;


public class FileSeach {

	public static IndexSearcher indexSearcher;
	private static IndexFactory indexFactory;
	private static Analyzer analyzer;

	public static void init(String path) throws IOException, XmlException, OpenXML4JException {
		indexFactory = new IndexFactory();
		indexFactory.createIndex(path);
		indexSearcher = new IndexSearcher(IndexReader.open(indexFactory.getDir()));
		analyzer = indexFactory.getAnalyzer();

	}

	public static FileResult seachFile(String query, int start, int limit,Sort sort) throws IOException, ParseException {
		String[] fields = { "contents", "name", "type" };
		QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_36, fields, analyzer);
		System.out.println("queryParser="+queryParser);
		Query q = queryParser.parse(query);
		System.out.println("query="+query);
		TopDocs topDocs = indexSearcher.search(q, limit + start,sort.RELEVANCE);
		if (topDocs.totalHits < limit) {
			limit = topDocs.totalHits;
		}
		// 上一页的最后一个document索引
		int index = start + limit;
		ScoreDoc scoreDoc = null;
		List<FileBean> data = new ArrayList<FileBean>();
		if (index > 0 && start > 0) {
			scoreDoc = topDocs.scoreDocs[index - 3];
		}

		if (topDocs.totalHits == 0) {
			return new FileResult(data, 0);
		}
		TopDocs hits = indexSearcher.searchAfter(scoreDoc, q, limit);

		for (ScoreDoc s : hits.scoreDocs) {
			FileBean bean = new FileBean();
			Document document = indexSearcher.doc(s.doc);
			bean.setName(document.get("name"));
			bean.setType(document.get("type"));
			bean.setPath(document.get("path"));
			data.add(bean);
		}
		for(FileBean file : data){
			System.out.println("以下是检索结果："+file.getName()+"-----"+file.getPath());
		}
		indexSearcher.close();
		return new FileResult(data, hits.totalHits);
	}
	
	private String getHighLight(Document doc, Analyzer analyzer, Query query,
			String field) throws Exception{
		
		//设置高亮显示格式
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'><strong>", "</strong></font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter,new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(100));
		//取field字段的值，准备进行高亮
		String fieldValue = doc.get(field);
		TokenStream tokenStream = analyzer.tokenStream(field, new StringReader(fieldValue));
		//转成高亮的值
		String highLightFieldValue = highlighter.getBestFragment(tokenStream, fieldValue);
		
		if(highLightFieldValue==null)
			highLightFieldValue = fieldValue;
			
		return highLightFieldValue;
		
	}

}
