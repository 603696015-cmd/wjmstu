package com.sopia.lucene;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Conext {

	public static final String DOC_PATHS[];
	public static final String INDEX_PATH;

	/** 配置文件名称 */
	private static final String CNF = "doc.cfg.xml";

	static {
		Document document = null;
		try {
			document = new SAXReader().read(new File(Thread.currentThread().getContextClassLoader().getResource(CNF).toURI()));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		Element root = document.getRootElement();

		// 设置文档目录
		List<Node> nodes = root.selectNodes("//docPath");
		DOC_PATHS = new String[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			DOC_PATHS[i] = nodes.get(i).getText().trim();
		}

		// 设置索引目录
		INDEX_PATH = root.selectSingleNode("indexPath").getText().trim();
	}

}
