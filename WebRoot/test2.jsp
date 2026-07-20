<%@ page language="java" contentType="text/html; charset=GBK"%>  
<%@ page import="java.awt.*"%>
<%@ page import="com.javareport.beans.*"%>  
<%@ page extends="com.javareport.http.WebReportEngine"%>  
<%!  
    public Report createReport(HttpServletRequest request) throws Exception{  
	 	//实例化报表对象
	    Report report = new Report();
	    //在页眉中添加文本信息内容
	    report.addHeaderText("报表输出示例");
	    //在报表的页眉添加一条横直线
	    report.addHeaderSeparator(1);
	    //在页尾添加一条横直线
	    report.addFooterSeparator(1);
	    //在页尾添加文本信息内容
	    report.addFooterText("第{P}页， 共{N}页");
	    
	 	//在报表中添加文本信息内容
	    report.addText("销售情况一览表：");
	    //在报表中添加换行符号
	    report.addBreak();
	    //在报表中添加表格
	    report.addTable(getTableA(request));
	    //在报表中添加换行符号
	    report.addBreak();
	 
	  	//在报表中添加文本信息内容
	    report.addText("销售情况一览表(合并表格)：");
	    //在报表中添加换行符号
	    report.addBreak();
	    //在报表中添加表格
	    report.addTable(getTableB(request));
	    //在报表中添加换行符号
	    report.addBreak(); 
	
	    return report; 
    }  
  
	//------得到销售情况一览表对象------
	public Table getTableA(HttpServletRequest request){
		  String[][] data = getData(request);
		  Table table = new Table(data);
		  table.setColBorder(0);
		  table.setRowBorder(0);
		  table.setRowBorder(0,1);
		  table.setRowBackground(0,new Color(128,0,0));
		  table.setRowForeground(0,Color.white);
		  table.setRowBackground(1,new Color(255,255,128));
		  table.setRowForeground(1,Color.black);
		  table.setRowBackground(2,new Color(255,255,128));
		  table.setRowForeground(2,Color.black);
		  table.setRowBackground(3,new Color(255,255,128));
		  table.setRowForeground(3,Color.black);
		  table.setRowBackground(4,new Color(255,255,128));
		  table.setRowForeground(4,Color.black);
		  table.setRowBackground(5,new Color(255,255,128));
		  table.setRowForeground(5,Color.black);
		  return table;
	}
	
	public Table getTableB(HttpServletRequest request){
	    String[][] data = getTotalData(request);
	    Table table = new Table(data);
	    table.setAlignment(Table.H_CENTER + Table.V_CENTER);
	    table.setColAutoSize(true);
	    table.setRowBackground(0,Color.LIGHT_GRAY);
	    table.setRowBackground(1,Color.LIGHT_GRAY);
	    table.setColBackground(0,Color.LIGHT_GRAY);
	    table.setRowBackground(7,new Color(255,255,128));
	    table.setHeaderRowCount(2);
	    table.setHeaderColCount(1);
	    table.setRowBorder(table.LINE_THIN);
	    table.setColBorder(table.LINE_THIN);
	    table.setCellSpan(0,0,new Dimension(1,2));
	    table.setCellSpan(0,1,new Dimension(2,1));
	    table.setCellSpan(0,3,new Dimension(2,1));
	    table.setCellSpan(0,3,new Dimension(2,1));
	    return table;
	}
	
	
	public String[][] getData(HttpServletRequest request){  
		String[][] data = new String[6][4];
	    data[0][0] = "区域"; 
	    data[0][1] = "第一季度"; 
	    data[0][2] = "第二季度"; 
	    data[0][3] = "第三季度";
	    data[1][0] = "华南地区"; data[1][1] = "￥2,000,000";
	  	data[1][2] = "￥2,500,000"; data[1][3] = "￥2,200,000";
	    data[2][0] = "华东地区"; data[2][1] = "￥6,000,000";
	  	data[2][2] = "￥4,500,000"; data[2][3] = "￥4,800,000";
	    data[3][0] = "华中地区"; data[3][1] = "￥500,000";
	  	data[3][2] = "￥400,000"; data[3][3] = "￥700,000";
	    data[4][0] = "华北地区"; data[4][1] = "￥3,000,000";
	  	data[4][2] = "￥3,200,000"; data[4][3] = "￥2,500,000";
	    data[5][0] = "东北地区"; data[5][1] = "￥4,000,000";
	  	data[5][2] = "￥5,000,000"; data[5][3] = "￥4,400,000";
	    return data;  
	} 
	
	public String[][] getTotalData(HttpServletRequest request){  
	  String[][] data = new String[8][5];
	  data[0][0] = "区域"; data[0][1] = "上半年"; data[0][3] = "下半年";
	  data[1][1] = "第一季度"; data[1][2] = "第二季度"; data[1][3] = "第三季度";data[1][4] = "第四季度";
	  data[2][0] = "华南地区"; data[2][1] = "￥2,000,000"; data[2][2] = "￥2,500,000";
	  data[2][3] = "￥2,200,000";data[2][4] = "￥0";
	  data[3][0] = "华东地区"; data[3][1] = "￥6,000,000"; data[3][2] = "￥4,500,000";
	  data[3][3] = "￥4,800,000";data[3][4] = "￥0";
	  data[4][0] = "华中地区"; data[4][1] = "￥500,000"; data[4][2] = "￥400,000";
	  data[4][3] = "￥700,000";data[4][4] = "￥0";
	  data[5][0] = "华北地区"; data[5][1] = "￥3,000,000"; data[5][2] = "￥3,200,000";
	  data[5][3] = "￥2,500,000";data[5][4] = "￥0";
	  data[6][0] = "东北地区"; data[6][1] = "￥4,000,000"; data[6][2] = "￥5,000,000";
	  data[6][3] = "￥4,400,000";data[6][4] = "￥0";
	  data[7][0] = "总计"; data[7][1] = "￥15,500,000"; data[7][2] = "￥15,600,000";
	  data[7][3] = "￥14,600,000";data[7][4] = "￥0";
	  return data;  
	} 
	
	
  
    //定制Web报表在页面首部显示的工具栏为标准的样式，增加一个“返回”按钮，返回到首页  
    public String getToolbarScript(HttpServletRequest request){  
        return "<a href=\"test2.jsp\"><img src=\""+request.getRequestURI()+  
                "?op=Resource&name=/resource/back.gif\" border=\"0\" alt=\"返回\"></a>";  
    }  
%>  