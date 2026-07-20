package com.sopia.schedule.entities;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class MapToJson {
	
	public static Object getJsObject(Object obj) {
	    if (obj instanceof Map) {
	        return getJsMap((Map) obj);
	    } else if (obj instanceof Collection) {
	        return getJsCollection((Collection) obj);
	    } else {
	        return getJsValue(obj);
	    }
	}
	
	public static Object getJsMap(Map map) {
	    StringBuffer buf = new StringBuffer();
	    buf.append("{");
	    Iterator iter = map.entrySet().iterator();
	    if (iter.hasNext()) {
	        Map.Entry ety = (Map.Entry) iter.next();
	        buf.append(getJsValue(ety.getKey()));
	        buf.append(":");
	        buf.append(getJsObject(ety.getValue()));
	    }
	    while (iter.hasNext()) {
	        Map.Entry ety = (Map.Entry) iter.next();
	        buf.append(",");
	        buf.append(getJsValue(ety.getKey()));
	        buf.append(":");
	        buf.append(getJsObject(ety.getValue()));
	    }
	    buf.append("}");
	    return buf;
	}
	 
	public static Object getJsCollection(Collection list) {
	    StringBuffer buf = new StringBuffer();
	    buf.append("[");
	    Iterator iter = list.iterator();
	    if (iter.hasNext()) {
	        buf.append(getJsObject(iter.next()));
	    }
	    while (iter.hasNext()) {
	        buf.append(",");
	        buf.append(getJsObject(iter.next()));
	    }
	    buf.append("]");
	    return buf;
	}
	 
	public static String getJsString(Object obj) {
	    if (obj == null) {
	        obj = new String("");
	    }
	    return obj.toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\\'");
	}

	public static Object getJsValue(Object objValue) {
	    StringBuffer buf = new StringBuffer();
	    buf.append("'");
	    buf.append(getJsString(objValue));
	    buf.append("'");
	    return buf;
	}

	    
	    
	    public static void valueToString(StringBuffer buf, Object objValue) {
	        if (objValue instanceof Map) {
	            buf.append(getJsMap((Map) objValue));
	        } else if (objValue instanceof Collection) {
	            buf.append(getJsCollection((Collection) objValue));
	        } else {
	            buf.append("'");
	            buf.append(getJsString(objValue));
	            buf.append("'");
	        }
	    }





	
	

}
