package com.sopia.schedule.entities;

	/**
	 * 客户档案entity
	 * @author Administrator
	 *
	 */
	public class Kehu {
		private String KHDA_KHJD;//客户阶段
		private int number;//数量
		private double KHDA_YQJE;//预期金额
		private double bili1;//比例1
		private double KHDA_SJJE;//实际金额
		private double bili2;//比例2
		private double bili3;//比例3
		
		public Kehu(){}
		public Kehu(String KHDA_KHJD){
			this.KHDA_KHJD = KHDA_KHJD;
		}
		
		public double getBili3() {
			return bili3;
		}
		public void setBili3(double bili3) {
			this.bili3 = bili3;
		}
		public double getKHDA_SJJE() {
			return KHDA_SJJE;
		}
		public void setKHDA_SJJE(double khda_sjje) {
			KHDA_SJJE = khda_sjje;
		}
		public String getKHDA_KHJD() {
			return KHDA_KHJD;
		}
		public void setKHDA_KHJD(String khda_khjd) {
			KHDA_KHJD = khda_khjd;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public double getKHDA_YQJE() {
			return KHDA_YQJE;
		}
		public void setKHDA_YQJE(double khda_yqje) {
			KHDA_YQJE = khda_yqje;
		}
		public double getBili1() {
			return bili1;
		}
		public void setBili1(double bili1) {
			this.bili1 = bili1;
		}
		public double getBili2() {
			return bili2;
		}
		public void setBili2(double bili2) {
			this.bili2 = bili2;
		}

	}
