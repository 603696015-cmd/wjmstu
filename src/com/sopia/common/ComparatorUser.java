package com.sopia.common;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.sopia.duman.entities.UnitRanking;

public class ComparatorUser implements Comparator{

 public int compare(Object arg0, Object arg1) {
	 UnitRanking user0=(UnitRanking)arg0;
	 UnitRanking user1=(UnitRanking)arg1;
	 if(user1.getFinalScore() > user0.getFinalScore()){//第一个比第二个大，返回-1  
		 return 1; 
		 }else if(user0.getFinalScore() == user1.getFinalScore()){//第一个和第二个相等，返回0
			 return 0;  }else{//第一个比第二个小，返回1   
				 return -1;  }
	 }

 }





