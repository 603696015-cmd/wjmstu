package com.sopia.record.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 相似度 计算工具类
 * 
 * @author jiahaijiang
 */
public class SimilarDegreeUtil {

	public static int getSimilarDegree(String str1, String str2){ 
       //创建向量空间模型，使用map实现，主键为词项，值为长度为2的数组，存放着对应词项在字符串中的出现次数 
        Map<String, int[]> vectorSpace = new HashMap<String, int[]>(); 
        int[] itemCountArray = null;//为了避免频繁产生局部变量，所以将itemCountArray声明在此 
         
        char strArray[] = str1.toCharArray();//str1.trim().replaceAll("\\s+", " ").split(" "); 
        for(int i=0; i<strArray.length; ++i) 
        {   
            if(vectorSpace.containsKey(String.valueOf(strArray[i]))) 
                ++(vectorSpace.get(String.valueOf(strArray[i]))[0]); 
            else 
            { 
                itemCountArray = new int[2]; 
                itemCountArray[0] = 1; 
                itemCountArray[1] = 0; 
                vectorSpace.put(String.valueOf(strArray[i]), itemCountArray); 
            } 
        } 
         
        strArray = str2.toCharArray();//str2.trim().replaceAll("\\s+", " ").split(" "); 
        for(int i=0; i<strArray.length; ++i) 
        { 
            if(vectorSpace.containsKey(String.valueOf(strArray[i]))) 
            	++(vectorSpace.get(String.valueOf(strArray[i]))[1]); 
            else 
            { 
                itemCountArray = new int[2]; 
                itemCountArray[0] = 0; 
                itemCountArray[1] = 1; 
                vectorSpace.put(String.valueOf(strArray[i]), itemCountArray); 
            } 
        } 
         
        //计算相似度 
        double vector1Modulo = 0.00;//向量1的模 
        double vector2Modulo = 0.00;//向量2的模 
        double vectorProduct = 0.00; //向量积 
        Iterator iter = vectorSpace.entrySet().iterator(); 
        while(iter.hasNext()) 
        { 
            Map.Entry entry = (Map.Entry)iter.next(); 
            itemCountArray = (int[])entry.getValue(); 
             
            vector1Modulo += itemCountArray[0]*itemCountArray[0]; 
            vector2Modulo += itemCountArray[1]*itemCountArray[1]; 
             
            vectorProduct += itemCountArray[0]*itemCountArray[1]; 
        } 
         
        vector1Modulo = Math.sqrt(vector1Modulo); 
        vector2Modulo = Math.sqrt(vector2Modulo); 
         
        //返回相似度  www.2cto.com
       return Double.valueOf((vectorProduct/(vector1Modulo*vector2Modulo))*100).intValue(); 
    } 
	
	public static void main(String[] args) {
		String str1 = "中华人民共和国成立于1949年秋天";
		String str2 = "中华人民共和国成立于1949年秋";
		System.out.println(getSimilarDegree(str1, str2));
	}

}
