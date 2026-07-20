package com.sopia.common.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class ChartUtil {
	private static final Log logger = LogFactory.getLog(ChartUtil.class);

	/**
	 * Description: 
	* @Version1.0 2012-6-28 上午11:03:49 by 闻益舜（wenyishun110@163.com）创建
	 * @param title
	 * @param x
	 * @param y
	 * @param categoryDataset
	 * @return
	 */
	public static JFreeChart createChart(String title,String x,String y,CategoryDataset categoryDataset) {
		// 创建JFreeChart对象：ChartFactory.createLineChart
		JFreeChart jfreechart = ChartFactory.createLineChart(title, // 标题
				x, // categoryAxisLabel （category轴，横轴，X轴标签）
				y, // valueAxisLabel（value轴，纵轴，Y轴的标签）
				categoryDataset, // dataset
				PlotOrientation.VERTICAL, true, // legend
				false, // tooltips
				false); // URLs
		jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD,16));
		// 使用CategoryPlot设置各种参数。以下设置可以省略。
		CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
		// 背景色 透明度
		plot.setBackgroundAlpha(0.5f);
		// 前景色 透明度
		plot.setForegroundAlpha(0.5f);
		// 其他设置 参考 CategoryPlot类
		CategoryAxis domainAxis = plot.getDomainAxis();
		/*------设置X轴坐标上的文字-----------*/
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot   
		  .getRenderer();
//		renderer.setSeriesStroke(0, new BasicStroke(2.0F));
		renderer.setSeriesPaint(0, Color.black);
		/*------设置X轴的标题文字------------*/
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		/*------设置Y轴坐标上的文字-----------*/
		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		/*------设置Y轴的标题文字------------*/
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
		/*------这句代码解决了底部汉字乱码的问题-----------*/
		jfreechart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		return jfreechart;
	}

	/**
	 * 创建CategoryDataset对象
	 * 
	 */
//	public static CategoryDataset createDataset(String[] rowKeys,String[] colKeys,double[][] data) {
//		String[] rowKeys = { "打撒", "额外清热", "程序" };
//		String[] colKeys = { "1987", "1997", "2007" };
//		double[][] data = { { 50, 20, 30 }, { 20, 10D, 40D },
//				{ 40, 30.0008D, 38.24D }, };
		// 或者使用类似以下代码
		// DefaultCategoryDataset categoryDataset = new
		// DefaultCategoryDataset();
		// categoryDataset.addValue(10, "rowKey", "colKey");
//		return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
//	}

	public static void output(OutputStream os, JFreeChart freeChart, int width,
			int height) {
		try {
			ChartUtilities.writeChartAsPNG(os, freeChart, width, height);
			os.flush();
			os.close();
		} catch (Exception e) {
			logger.error("生成图表失败",e);
		}
	}
	public static void dazioutput(String title,OutputStream os,String x ,String[] vs,String[] ss,double myss[][]){
		JFreeChart freeChart = createChart(title,x,"得分", DatasetUtilities.createCategoryDataset(vs, ss, myss));
		output(os, freeChart, 1210, 650);
	}
}
