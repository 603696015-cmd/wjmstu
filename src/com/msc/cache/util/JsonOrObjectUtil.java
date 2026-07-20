package com.msc.cache.util;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.msc.cache.IBaseCache;
import com.msc.cache.impl.MapCachedBaseCache;
import com.msc.cache.impl.RedisCacheImpl;



public class JsonOrObjectUtil {
	/**
	 * json数组标识
	 */
	public static final String ARRAY_START = "[";

	public static final String OBJECT_START = "{";

	@SuppressWarnings("unchecked")
	public static Object jsonToListBean(Object jsonBean, Class<?> clazz,
			IBaseCache ib) {
		if (ib == null) {
			return null;
		}

		JSONArray jsonArr = null;
		JSONObject json = null;

		if (jsonBean.toString().startsWith(OBJECT_START)) {// obj对象
			json = JSONObject.fromObject(jsonBean);
		} else if (jsonBean.toString().startsWith(ARRAY_START)) {// 数组对象
			jsonArr = JSONArray.fromObject(jsonBean);
			System.out.println(jsonArr.toString());
		} else {// 其他情况

		}

		if (ib instanceof RedisCacheImpl) {// redisCache
			if (jsonArr != null && !jsonArr.isEmpty()) {
				List<Object> list = new ArrayList<Object>();
				if (jsonBean.getClass().getSimpleName().equals("HashMap")
						|| jsonBean.getClass().getSimpleName().equals("String")) {
					JSONArray jarr1 = JSONArray.fromObject(jsonBean);
					Map<String, List<Object>> temp = new HashMap<String, List<Object>>();
					for (int i = 0; i < jarr1.size(); i++) {
						JSONObject json1 = jarr1.getJSONObject(i);
						Iterator it = json1.keys();

						while (it.hasNext()) {
							String key = it.next().toString();
							temp.put(key, (List<Object>) json1.get(key));
						}

					}
					return temp;
				} else {

					JSONArray jarr = JSONArray.fromObject(jsonBean);
					list = (List<Object>) JSONArray.toCollection(jarr, clazz);
				}

				return list;
			} else {
				return JSONObject.toBean(json, clazz);
			}
		} else if (ib instanceof MapCachedBaseCache) {// map缓存

		} else {// 其他
			return null;
		}
		return null;
	}

	public static String beanToJson(Object obj, IBaseCache ib) {
		if (ib == null) {
			return null;
		}
		JSONArray jsonArr = null;
		JSONObject json = null;
		Object o = null;

		if (obj instanceof Collection || obj.getClass().isArray()
				|| obj.getClass().getSimpleName().equals("HashMap")) {
			jsonArr = JSONArray.fromObject(obj);
		} else {

			if (obj.getClass().getSimpleName().equals("Object")|| (obj.getClass().toString().contains("class") && obj.getClass().toString().contains("com")) ) {
				json = JSONObject.fromObject(obj);
			} else {
				o = obj;
			}

		}

		if (ib instanceof RedisCacheImpl) {// redisCache
			if (jsonArr != null && jsonArr.size() > 0) {
				JSONArray jarr = JSONArray.fromObject(obj);
				return jarr.toString();
			} else {
				if (json != null) {
					return json.toString();
				} else {
					return String.valueOf(o);
				}

			}
		} else if (ib instanceof MapCachedBaseCache) {// map缓存

		} else {// 其他
			return null;
		}
		return null;
	}

	public static void main(String[] args) {
		/*
		 * IBaseCache baseCache = RedisCacheBaseCache.getInstance(); Student
		 * stu3= (Student) jsonToListBean(baseCache.get("key"),Student.class,
		 * baseCache); System.out.println(stu3.getAge());
		 */
		/*Student stu = new Student();
		stu.setName("董克");
		stu.setAge("18");
		stu.setSex("男");
		stu.setAddress("北京大兴");

		// baseCache.put("key", stu);

		Student stu1 = new Student();
		stu1.setName("董薇");
		stu1.setAge("14");
		stu1.setSex("女");
		stu1.setAddress("北京大兴");

		List<Student> stuList = new ArrayList<Student>();
		stuList.add(stu);
		stuList.add(stu1);

		Map<String, List<Student>> map = new HashMap<String, List<Student>>();
		map.put("aa", stuList);
		JSONArray jj = JSONArray.fromObject(map);
		System.out.println(jj.toString());
		for (int i = 0; i < jj.size(); i++) {
			JSONObject json1 = jj.getJSONObject(i);
			Iterator it = json1.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				System.out.println(key + "#########" + json1.get(key));
			}

		}*/

	}
}
