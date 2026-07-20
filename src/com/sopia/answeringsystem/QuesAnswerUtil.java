package com.sopia.answeringsystem;

public class QuesAnswerUtil {
	public static boolean contains(String[] numbers,int number){
		boolean flag = false;
		for(int i=0;i<numbers.length;i++){
			if(Integer.parseInt(numbers[i]) == number){
				flag = true;
				break;
			}
		}
		return flag;
	}

}
