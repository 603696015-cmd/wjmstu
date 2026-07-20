package com.sopia.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;








import com.sopia.duman.entities.Department;

import sync.domin.JSONObj;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {

	private static final Log logger = LogFactory.getLog(HttpUtil.class);
	
	public static String json = "{'status':'1','info':'OK','infocode':'10000','count':'1','suggestion':{'keywords':[],'cities':[]},'districts':[{'citycode':'010','adcode':'110000','name':'北京市','center':'116.407394,39.904211','level':'province','districts':[{'citycode':'010','adcode':'110100','name':'北京城区','center':'116.407394,39.904211','level':'city','districts':[{'citycode':'010','adcode':'110101','name':'东城区','center':'116.41649,39.928341','level':'district','districts':[]},{'citycode':'010','adcode':'110102','name':'西城区','center':'116.365873,39.912235','level':'district','districts':[]},{'citycode':'010','adcode':'110105','name':'朝阳区','center':'116.443205,39.921506','level':'district','districts':[]},{'citycode':'010','adcode':'110106','name':'丰台区','center':'116.287039,39.858421','level':'district','districts':[]},{'citycode':'010','adcode':'110107','name':'石景山区','center':'116.222933,39.906611','level':'district','districts':[]},{'citycode':'010','adcode':'110108','name':'海淀区','center':'116.298262,39.95993','level':'district','districts':[]},{'citycode':'010','adcode':'110109','name':'门头沟区','center':'116.101719,39.940338','level':'district','districts':[]},{'citycode':'010','adcode':'110111','name':'房山区','center':'116.143486,39.748823','level':'district','districts':[]},{'citycode':'010','adcode':'110112','name':'通州区','center':'116.656434,39.909946','level':'district','districts':[]},{'citycode':'010','adcode':'110113','name':'顺义区','center':'116.654642,40.130211','level':'district','districts':[]},{'citycode':'010','adcode':'110114','name':'昌平区','center':'116.231254,40.220804','level':'district','districts':[]},{'citycode':'010','adcode':'110115','name':'大兴区','center':'116.341483,39.726917','level':'district','districts':[]},{'citycode':'010','adcode':'110116','name':'怀柔区','center':'116.631931,40.316053','level':'district','districts':[]},{'citycode':'010','adcode':'110117','name':'平谷区','center':'117.121351,40.140595','level':'district','districts':[]},{'citycode':'010','adcode':'110118','name':'密云区','center':'116.843047,40.376894','level':'district','districts':[]},{'citycode':'010','adcode':'110119','name':'延庆区','center':'115.974981,40.456591','level':'district','districts':[]}]}]}]}'";
	    /**
	     * httpPost
	     * @param url  路径
	     * @param jsonParam 参数
	     * @return
	     */
	    public static JSONObject httpPost(String url,JSONObject jsonParam){
	        return httpPost(url, jsonParam, false);
	    }
	 
	    /**
	     * post请求
	     * @param url         url地址
	     * @param jsonParam     参数
	     * @param noNeedResponse    不需要返回结果
	     * @return
	     */
	    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
	        //post请求返回结果
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        JSONObject jsonResult = null;
	        HttpPost method = new HttpPost(url);
	        try {
	            if (null != jsonParam) {
	                //解决中文乱码问题
	                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
	                entity.setContentEncoding("UTF-8");
	                entity.setContentType("application/json");
	                method.setEntity(entity);
	            }
	            HttpResponse result = httpClient.execute(method);
	            url = URLDecoder.decode(url, "UTF-8");
	            /**请求发送成功，并得到响应**/
	            if (result.getStatusLine().getStatusCode() == 200) {
	                String str = "";
	                try {
	                    /**读取服务器返回过来的json字符串数据**/
	                    str = EntityUtils.toString(result.getEntity());
	                    if (noNeedResponse) {
	                        return null;
	                    }
	                    /**把json字符串转换成json对象**/
	                    jsonResult = JSONObject.fromObject(str);
	                } catch (Exception e) {
	                    logger.error("post请求提交失败:" + url, e);
	                }
	            }
	        } catch (IOException e) {
	            logger.error("post请求提交失败:" + url, e);
	        }
	        return jsonResult;
	    }
	 
	 
	    /**
	     * 发送get请求
	     * @param url    路径
	     * @return
	     */
	    public static JSONObject httpGet(String url){
	        //get请求返回结果
	        JSONObject jsonResult = null;
	        try {
	            DefaultHttpClient client = new DefaultHttpClient();
	            //发送get请求
	            HttpGet request = new HttpGet(url);
	            HttpResponse response = client.execute(request);
	 
	            /**请求发送成功，并得到响应**/
	            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                /**读取服务器返回过来的json字符串数据**/
	                String strResult = EntityUtils.toString(response.getEntity());
	                /**把json字符串转换成json对象**/
	                jsonResult = JSONObject.fromObject(strResult);
	                url = URLDecoder.decode(url, "UTF-8");
	            } else {
	                logger.error("get请求提交失败:" + url);
	            }
	        } catch (IOException e) {
	            logger.error("get请求提交失败:" + url, e);
	        }
	        return jsonResult;
	    }
	
	public static void main(String[] args) {
		 JSONObject jsonObj = HttpUtil.httpGet("http://restapi.amap.com/v3/config/district?keywords=河北省&subdistrict=2&key=157bf68b8049e76e6809dba1a43dd972");
		 List<JSONObj> json1 = HttpUtil.fectData(jsonObj.getJSONArray("districts").getJSONObject(0));
		 for (int i = 0; i < json1.size(); i++) {
			System.out.println(json1.get(i).getName());
		}
		
	}
	
	public static List<JSONObj> fectData(JSONObject json){
		JSONArray jsonArr = json.getJSONArray("districts");
		
		List<JSONObj> resultSet = new ArrayList<JSONObj>();
		
		for (int i = 0; i < jsonArr.size(); i++) {
			if(jsonArr.getJSONObject(i).getString("level").equals("city")){
				
				JSONObject cityObj = jsonArr.getJSONObject(i);//市
				JSONObj result = new JSONObj();
				result.setName(cityObj.getString("name"));
				result.setCenter(cityObj.getString("center"));
				result.setAdcode(cityObj.getString("adcode"));
				result.setCitycode(cityObj.getString("citycode"));
				result.setLevel(cityObj.getString("level"));
				//获取市
				//result.setDistricts(Test.jsonToArrayList(jsonArr.getJSONObject(i).getJSONArray("districts").toString(), JSONObj.class));
				resultSet.add(result);
			}
			
		}
		
		
		return resultSet;
	}
}
