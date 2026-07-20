package com.sopia.common;
import java.util.Date; 

public class DateUtility {
	/**
	 * 根据生日获取年龄
	 * date 生日日期
	 * Hwc
	 */ 

     public static int GetAge(Date date)
     {
         int age = 0;
         if (date != null)
         {
        	 Date nowDate = new Date();
             int full = -1;
             if (nowDate.getMonth() > date.getMonth())
                 full = 0;
             if (nowDate.getMonth() == date.getMonth() && nowDate.getDate() > date.getDate())
                 full = 0;
             age = (nowDate.getYear()- date.getYear()) + full;
         }
         return age;
     }  

}
