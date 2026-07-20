package com.sopia.common; 

public class getFloat {

    public static float GetFloat(float date)
    { 	 
//        float f = date;
//        f = Math.round(f*100)/100f;  
//        return f;
        return (float)(Math.round((date*1000)/10))/100;
    }
    
    public static float GetFloat(double date)
    { 	 
//    	double f = date;   
//        f = Math.round(f*100)/100f;
//        return new Float(f);
        return new Float(Math.round(date*100)/100f);
    }
    
    public static String GetFloat_(float date){
        return String.format("%.2f", date);
    }
    
    public static float GetFloatOne(float date)
    { 	 
//        float f = date;
//        f = Math.round(f*100)/100f;  
//        return f;
        return (float)(Math.round((date*1000)/100))/10;
    }
    
}
