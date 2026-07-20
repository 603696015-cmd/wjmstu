package com.sopia;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.cache.CacheFactory;
import com.sopia.cache.IBaseCache;
import com.sopia.cache.impl.RedisCacheImpl;
import com.sopia.common.AuthorityNewVersionUtil;
import com.sopia.common.AuthorityUtil;
import com.sopia.common.DBConnection;
import com.sopia.common.ElQuerySql;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.IndexSystemConfigOp;
import com.sopia.common.IntelligentSystemConfOp;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.common.NavigationUtil;
import com.sopia.common.NewSystemConfOp;
import com.sopia.common.OfficeJpgUtil;
import com.sopia.common.RemindUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.ZdyStaticHtmlSystemConfOp;
import com.sopia.common.quiz.EpQStatus;
import com.sopia.common.register.Register;
import com.sopia.common.spring.SpringContextUtil;

public class InitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6047915061828398312L;
	private static final Log logger = LogFactory.getLog(InitServlet.class);

	public void destroy() {
		super.destroy();
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		super.service(req, res);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Register.init();
			String path = config.getServletContext().getRealPath(
					"/WEB-INF/config/");
			logger.info("配置文件路径123：" + path);
		
			SystemConfOp.setPath(path);
			SystemConfOp.load();
			IntelligentSystemConfOp.setPath(path);
			IntelligentSystemConfOp.load();
			IndexSystemConfigOp.setPath(path);
			IndexSystemConfigOp.load();
			JTMSystemConfOp.setPath(path);
			JTMSystemConfOp.load();
			ZdyStaticHtmlSystemConfOp.setPath(path);
			ZdyStaticHtmlSystemConfOp.load();
			NewSystemConfOp.setPath(path);
			NewSystemConfOp.load();
			DBConnection.startDatabase(path);
			
			// 初始化SCORM数据库连接
			DBConnection.getConnection().setAutoCommit(false);
			logger.info("dongkeshiwo1");
			//JdbcUtils.loadDB(path);
			ElQuerySql.init(path);
			logger.info("dongkeshiwo2");
			AuthorityUtil.load();
			logger.info("dongkeshiwo3");
			//新版个人中心的菜单
			AuthorityNewVersionUtil.load();
			logger.info("dongkeshiwo4");
			EpQStatus.init();
			logger.info("dongkeshiwo5");
//			EroomEpCache.init();
			NavigationUtil.load();
			logger.info("dongkeshiwo6");
			//获取所有提醒
			RemindUtil.load();
			logger.info("dongkeshiwo7");
			//获取所有office文档转换的缩略图集合
			OfficeJpgUtil.load();
			logger.info("789");
			//初始化缓存
			logger.info("开始设置缓存");
			IBaseCache cache = CacheFactory.getRedisCache();
			SystemConfOp.setBaseCache(cache);
			cache.put("xiaoming", 400);
			logger.info("配置缓存成功缓存值为:"+cache.get("xiaoming").toString());
			((IndexDataUtil) SpringContextUtil.getBean("indexDataUtil"))
					.loadIndexInfo(ElConstants.INDEX_MODEL_ALL);
			DBConnection.getConnection().commit();
			logger.info("all success");
		} catch (Exception e) {
			logger.error("数据库配置加载失败！", e);
			try {
				DBConnection.getConnection().rollback();
			} catch (Exception ee) {
				logger.error("filter-init-回滚数据库失败");
			}
		} finally {
			try {
				DBConnection.getConnection().close();
			} catch (Exception ee) {
				logger.error("filter-init-关闭数据库失败");
			}
			DBConnection.setNull();
		}
	}

	
}
