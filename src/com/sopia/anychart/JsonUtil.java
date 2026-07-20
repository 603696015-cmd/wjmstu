package com.sopia.anychart;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

/**
 * 拼凑anychart  JSON字符串
 * @author Taomingke
 *
 */
public class JsonUtil {
	/**
	 * 拼凑anychart  JSON字符串
	 * @param str
	 * @return
	 */
	public static String anychartJsonStr(String str){
		/**
		 * {
    "charts": {
        "chart": {
            "chart_settings": {
                "title": {
                    "text": "Simple Chart"
                }
            },
            "data": {
                "series": [
                    {
                        "point": [
                            {"name": "Jan", "y": 21},
                            {"name": "Feb", "y": 23},
                            {"name": "Mar", "y": 31},
                            {"name": "Apr", "y": 34},
                            {"name": "May", "y": 45}
                        ]
                    }
                ]
            },
            plot_type:"CategorizedHorizontal"
        }
    }
}
		 * 
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"charts\": {");
		sb.append(	"\"chart\": {");
		sb.append(		"\"chart_settings\": {");
		sb.append(			"\"title\": {");
		sb.append(				"\"text\": \"Simple Chart\"");
		sb.append(			"}");
		sb.append(		"},");
		sb.append(		"\"data\": {");
		sb.append(			"\"series\": [{");
		sb.append(				"\"point\": ");
		sb.append(					str);
		sb.append(			"}]");
		sb.append(		"}");
		sb.append(	"}");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	/** 
     * 将JAVA对象转换成JSON字符串 
     * @param obj 
     * @return 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
    @SuppressWarnings("rawtypes")
	public static String simpleObjectToJsonStr(Object obj,List<Class> claList) throws IllegalArgumentException, IllegalAccessException{  
        double points = 0.0;
    	if(obj==null){  
            return "null";  
        }  
        String jsonStr = "{";  
        Class<?> cla = obj.getClass();  
        Field fields[] = cla.getDeclaredFields();  
        for (Field field : fields) {  
            field.setAccessible(true);  
            if(field.getType() == long.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getLong(obj)+",";  
            }else if(field.getType() == double.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getDouble(obj)+",";  
            }else if(field.getType() == float.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getFloat(obj)+",";  
            }else if(field.getType() == int.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getInt(obj)+",";  
            }else if(field.getType() == boolean.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getBoolean(obj)+",";  
            }else if(field.getType() == Integer.class||field.getType() == Boolean.class  
                    ||field.getType() == Double.class||field.getType() == Float.class                     
                    ||field.getType() == Long.class){                 
                jsonStr += "\""+field.getName()+"\":"+field.get(obj)+",";  
            }else if(field.getType() == String.class){  
                jsonStr += "\""+field.getName()+"\":\""+field.get(obj)+"\",";  
            }else if(field.getType() == List.class){  
                String value = simpleListToJsonStr((List<?>)field.get(obj),claList);  
                jsonStr += "\""+field.getName()+"\":"+value+",";                  
            }else if(field.getType() instanceof Object){
            	if(field.getClass().getInterfaces() != null)
            	System.out.println("是对象"+field.getName());
            }else{        
                if(claList!=null&&claList.size()!=0&&claList.contains(field.getType())){  
                    String value = simpleObjectToJsonStr(field.get(obj),claList);  
                    jsonStr += "\""+field.getName()+"\":"+value+",";                      
                }else{  
                    jsonStr += "\""+field.getName()+"\":null,";  
                }  
            }  
        }  
        jsonStr = jsonStr.substring(0,jsonStr.length()-1); 
        jsonStr += "}";  
        
        
        
        return jsonStr;       
    }  
      
    /** 
     * 将JAVA的LIST转换成JSON字符串 
     * @param list 
     * @return 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
    @SuppressWarnings("rawtypes")  
    public static String simpleListToJsonStr(List<?> list,List<Class> claList) throws IllegalArgumentException, IllegalAccessException{  
        if(list==null||list.size()==0){  
            return "[]";  
        }  
        String jsonStr = "[";  
        for (Object object : list) {  
            jsonStr += simpleObjectToJsonStr(object,claList)+",";  
        }  
        jsonStr = jsonStr.substring(0,jsonStr.length()-1);  
        jsonStr += "]";       
//        return jsonStr;  
        return anychartJsonStr(jsonStr);
    }  
    
    public static void main(String[] args){
    	ELUser user = new ELUser(1,"taomingke");
    	Department dep = new Department();
    	dep.setId(1);
    	user.setDepartment(dep);
    	List<ELUser> users = new ArrayList<ELUser>();
    	users.add(user);
    	try {
			//String str = simpleListToJsonStr(users, null);
			String str1 = simpleObjectToJsonStr(user,null);
			System.out.println(str1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
