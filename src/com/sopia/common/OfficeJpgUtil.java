package com.sopia.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.spring.SpringContextUtil;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.StuffOfficeJpg;
/**
 * 获取系统中生成的缩略图小图
 * @author taomingke
 *
 */
public class OfficeJpgUtil {
	
	private static List<Map<String, Object>> ids ;
	private static List<StuffOfficeJpg> stuffOfficeJpgs ;
	private static final Log logger = LogFactory.getLog(OfficeJpgUtil.class);
	
	public static void load() throws ElException{
		StuffDao sd = (StuffDao)SpringContextUtil.getBean("stuffDao");
		ids = sd.listJpgIds();
		int id = 0;
		String title = "";
		if(ids != null && ids.size()>0){
			stuffOfficeJpgs = new ArrayList<StuffOfficeJpg>();
			StuffOfficeJpg stuffOfficeJpg = null;
			for(int i=0;i<ids.size();i++){
				id = (Integer)ids.get(i).get("id");
				title = (String)ids.get(i).get("title");
				stuffOfficeJpg = new StuffOfficeJpg();
				stuffOfficeJpg.setId(id);
				stuffOfficeJpg.setNameBig(id+"-1.jpg");
				stuffOfficeJpg.setNameSmall(id+"-1-small.jpg");
				stuffOfficeJpg.setFolder(""+id);
				stuffOfficeJpg.setTitle(title);
				stuffOfficeJpgs.add(stuffOfficeJpg);
			}
		}
	}
	
	public static List<StuffOfficeJpg> getStuffOfficeJpgs() throws ElException{
		if(stuffOfficeJpgs == null){
			 load() ;
		}
		return stuffOfficeJpgs;
	}

}
