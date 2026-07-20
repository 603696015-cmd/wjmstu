<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>培训班必修课程管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script> 
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		/*********************************** TableEdit.js  start *********************************/
			var mainTab = null;
			var currentRowIndex = null;
			var currentCell = null;
			var orgContent = "";
			//标记是否能编辑
			var editbleFlag = true;
			
			var showColume = false;
			
			//选中行默认颜色
			var currentBgc  = "cornflowerblue";
			//选中行字体颜色
			var currentFontColor = "black";
			
			//用来保存下拉菜单中的option项
			var optionText = "";
			//图片列号
			var imagePos = 0;
			//保存每一列的编辑类型
			var colStyle = new Array();
			//保存下拉框中的text和value
			var sText = new Array();
			var sValue = new Array();
			//增加是各个单元格的数据
			var cellData = new Array();
			/**
			 * 建立PowerTableEdit类，该类实现表格的自由编辑，删除，增加，上下移动 
			 * 其中自由编辑可以指定编辑方式及哪一列需要编辑
			 * 使用时，只需要利用表格的id创建PowerTableEdit对象
			 */
			function PowerTableEdit(tableId)
			{
			    //当前选中行
			    currentRowIndex = null;
			    //当前编辑cell
			    currentCell = null;
			
			    mainTab = document.all(tableId);
			    //获取已定义的颜色
			    readDef(mainTab);
			
			    mainTab.onclick = clickIt;
			    //mainTab.ondblclick    = dblclickIt;
			    
			    //设置select下拉框的数据
			    this.setCol = setColStyle;
			    this.setEditable = setEditable;
			    this.setCellData = setCellData;
			    this.getColData = getColData;
			    this.getRowData = getRowData;
			    //原始表格，可以支持reset
			    orgContent = mainTab.outerHTML;
			
			    //初始化增加表格的数据  
			    for(var i=0; i < mainTab.rows[0].cells.length; i++)
			        cellData[i] = "&nbsp;"; 
			    setEvenOddColor(mainTab);
			}
			
			//为select的onchange事件指定动作
			
			
			
			function selectChangeAction()
			{
			    var cellNum = currentCell.cellIndex;
			    var valueOfSel = document.all.powerTableSel.value;
			    //给单元格的data赋值
			    event.srcElement.parentNode.data = event.srcElement.value;
			    
			    //动作代理
			    selectProxy(cellNum,valueOfSel);
			}
			function selectProxy(cellNum, valueOfSel)
			{}
			
			//获取指定列的所有数据，以数组形式返回，如果是文本编辑方式，就返回文本内容，
			//如果是下拉框编辑方式，则返回其value值，即td中的data值
			//colIndex为列号，isImg为是否为图片
			function getColData(colIndex,isImg)
			{
			    if(colIndex == "")
			        return null;
			    var colNum = colIndex == null ? 0 : colIndex;
			    //如果为指定此参数，则默认为false，即不是图片格式数据
			    var isImgCol = isImg == null ? false : isImg;
			    var arrCelData = new Array();
			    if(/\D/g.test(colNum) 
			        || colNum >= mainTab.rows[0].cells.length
			         || colNum < 0)
			        return null;
			    if(isImgCol)
			    {
			        for(var i=1; i<mainTab.rows.length; i++)
			        {
			            if(mainTab.rows[i].cells[0].children[0]
			                && mainTab.rows[i].cells[0].children[0].tagName.toLowerCase() == "img")
			                arrCelData[i-1] = mainTab.rows[i].cells[0].data;
			            else
			                arrCelData[i-1] = null;
			        }
			    }   
			    else
			    { 
			        if(colStyle[parseInt(colNum)] == "txt")
			        {
			            for(var i=1; i<mainTab.rows.length; i++)
			            {
			                if(mainTab.rows[i].cells[colNum].children.length>0) 
			                    arrCelData[i-1] = mainTab.rows[i].cells[colNum].children[0].value;
			                else
			                    arrCelData[i-1] = mainTab.rows[i].cells[colNum].innerText;
			            }               
			        }
			        else if(colStyle[parseInt(colNum)] == "sel")
			        {
			            for(var i=1; i<mainTab.rows.length; i++)
			                arrCelData[i-1] = mainTab.rows[i].cells[colNum].data;
			        }
			        else
			        {
			                for(var i=1; i<mainTab.rows.length; i++)
			                arrCelData[i-1] = mainTab.rows[i].cells[colNum].innerText;              
			        }
			    }
			    return arrCelData;
			}
			
			//获取指定行的所有数据，以数组形式返回，如果是文本编辑方式，就返回文本内容，
			//如果是下拉框编辑方式，则返回其value值，即td中的data值
			//rowIndex为列号，isImg为是否为图片，如果是图片，则返回图片的id
			function getRowData(rowIndex)
			{
			    var arrRowData = new Array();
			    var rowNum = rowIndex == null ? 1 : rowIndex;
			    if(/\D/g.test(rowNum) 
			        || rowNum >= mainTab.rows.length
			         || rowNum <= 0)
			        return null;
			    for(var i=0; i<mainTab.rows[rowIndex].cells.length; i++)
			    {
			        with(mainTab.rows[rowIndex].cells[i])
			        {
			            if(children.length > 0)
			            {
			                if(children[0].tagName.toLowerCase() == "img")
			                    arrRowData[i] = data;
			                else if(children[0].tagName.toLowerCase() == "select")
			                    arrRowData[i] = data;
			                else if(children[0].tagName.toLowerCase() == "input")
			                    arrRowData[i] = children[0].value;
			                else
			                    arrRowData[i] = innerText;
			            }
			            else
			            {
			                if(colStyle[i] == "sel")
			                    arrRowData[i] = data;
			                else
			                    arrRowData[i] = innerText;
			            }
			        }
			    }
			    return arrRowData;
			}
			
			//如果是input或textarea,则允许选中里面的文字
			document.onselectstart = function()
			                         {
			                             var oObj = event.srcElement;
			                             if(oObj.tagName.toLowerCase() != "input" 
			                                && oObj.tagName.toLowerCase()!= "textarea")
			                                return false;
			                         }
			
			/**
			 *  设置编辑方式
			 *  colNum 为列编号
			 *  colSty为编辑类型，分为：txt－文本框编辑； sel－下拉框 编辑 
			 *  sDa 当编辑方式是下拉框方式时，传入下拉框中的数据数组
			 */
			function setColStyle(colNum,colSty,sTxt,sVal)
			{
			    colStyle[parseInt(colNum)] = colSty;
			    sText[parseInt(colNum)] = sTxt == null ? "" : sTxt;
			    sValue[parseInt(colNum)] = sVal == null ? "" : sVal;
			}
			
			//在表格的指定位置插入标记图标，其中，
			//oImg是用来插入的图标对象，
			//rIndex，是没个图标的id，如果一列中要采用不同的标志，该值不可相同
			//nCell,为设置的图标的列，默认为第一列
			function insertImg(oImg,rIndex,nCell)
			{
			    if(nCell == null)
			        nCell = 0;
			    else
			        imagePos = nCell;
			
			    var sHtml = "<img id=imgIndex_"+rIndex+" src='"+oImg.src+"' width=16 height=16/>";
			    
			    if(!currentRowIndex)
			    {
			        alert("请选中要设置图片的行！");
			        return;
			    }
			    
			    //检测所选行已经存在标志时的情况
			    if(mainTab.rows[currentRowIndex].cells[nCell].children[0])
			    {
			        if(mainTab.rows[currentRowIndex].cells[nCell].children[0].id != "imgIndex_"+rIndex)
			            alert("此位置已经存在其它的标志！");
			        else
			            return;
			    }       
			    else
			    {
			        //遍历整个表格，把原始标志还原
			        for(var i=0; i<mainTab.rows.length; i++)
			        {
			            with(mainTab.rows[i].cells[nCell])
			            {
			                if(children[0] && children[0].id == 'imgIndex_'+rIndex && i!= currentRowIndex)
			                {
			                    innerHTML = "&nbsp;";
			                    data = null;
			                }
			            }
			        }
			        //置新标志
			        mainTab.rows[currentRowIndex].cells[nCell].innerHTML = sHtml;
			        mainTab.rows[currentRowIndex].cells[nCell].data = rIndex;
			    }
			}
			
			//设置是否需要编辑的标记，如果设为true，则表格能编辑，反之不能，默认为可以编辑 
			function setEditable(editFlag)
			{
			    if(editFlag == null)
			        editbleFlag == true;
			    else
			        editbleFlag = editFlag;
			}
			
			//设置增加时各个单元格的数据
			function setCellData(arrData)
			{
			    //如果没有设置数据，则插入各个单元格所在的列数
			    if(arrData == null)
			    {
			        for(var i=0; i<mainTab.rows[0].cells.length; i++)
			            cellData[i] = i;
			    }
			    else
			    {
			        if(arrData.length >= arrData.length)
			        {
			            for(var i=0; i<mainTab.rows[0].cells.length; i++)
			                cellData[i] = arrData[i];
			        }
			        //数据不足，补以列号
			        if(arrData.length < mainTab.rows[0].cells.length)
			        {
			            for(var i=0; i<arrData.length; i++)
			                cellData[i] = arrData[i];
			            for(var i= arrData.length; i<mainTab.rows[0].cells.length; i++)
			                cellData[i] = i;
			        }
			    }
			}
			
			function clearColor()
			{
			    objTable=mainTab;
			    if (currentCell != null)
			        if (currentCell.children.length>0)
			    {
			        if(currentCell.children[0].tagName.toLowerCase() == "input")
			            currentCell.innerText=currentCell.children[0].value;
			        else if(currentCell.children[0].tagName.toLowerCase() == "select")
			            currentCell.innerHTML=currentCell.children[0].options[currentCell.children[0].selectedIndex].text;      
			    }
			    ClearColor(objTable,currentRowIndex,currentCell);
			}
			
			function document.onclick()
			{  
			    //alert(getRowData(currentRowIndex));
			    updateData(currentRowIndex);
			    clearColor();
			    currentRowIndex  = null;
			    currentCell = null;
			    
			}
			
			function readDef(objTable)
			{
			    ReadOrgColor(objTable);
			}
			
			function clickIt()
			{
			    event.cancelBubble=true;
			    var currentObject = event.srcElement;
			    var i = 0 ,j = 0;
			    //原编辑项变为表格  
			    if(currentCell!=null && currentRowIndex!=0
			        && currentObject.type!="select-one" 
			            && currentObject.type!="text")
			    if (currentCell.children.length>0 )
			    {
			        if(currentCell.children[0].tagName.toLowerCase() != "img"
			             && currentCell.children[0].tagName.toLowerCase() != "a")
			        {
			            if(currentCell.children[0].tagName.toLowerCase() == "input"){
			                currentCell.innerText=currentCell.children[0].value;
			            	//alert(currentCell.innerText);
			                //alert(getRowData(currentRowIndex));
			                updateData(currentRowIndex);
			            }
			            else if(currentCell.children[0].tagName.toLowerCase() == "select"){
			                currentCell.innerHTML=currentCell.children[0].options[currentCell.children[0].selectedIndex].text;
			                //alert(currentCell.children[0].options[currentCell.children[0].selectedIndex].text);
			                updateData(currentRowIndex);
			            }
			        }
			    }
			    if(currentObject.tagName.toLowerCase() != "table" 
			        && currentObject.tagName.toLowerCase() != "tbody" 
			           && currentObject.tagName.toLowerCase() != "tr")
			    {
			        var currentTd   = getElement(currentObject,"td");
			        if(currentTd==null) return;
			        
			        //更改点击图标，链接可以选择单行
			        if (currentTd.children.length<=0 
			            || currentTd.children[0].tagName.toLowerCase() == "a"
			             || currentTd.children[0].tagName.toLowerCase() == "img")
			        //更改点击图标，链接可以选择单行
			        {
			            var currentTr   = currentTd.parentElement;
			            var objTable = getElement(currentTd,"table");
			            var i = 0;
			            clearColor();
			            currentRowIndex = currentTr.rowIndex;
			            //设置选中的行
			            if(currentRowIndex!=0)
			            {
			                for(i=0;i<currentTr.cells.length;i++)
			                {
			                    with(currentTr.cells[i])
			                    {
			                        style.backgroundColor=currentBgc;
			                        style.color=currentFontColor;
			                    }
			                }
			            }
			        }
			
			        //根据标记设置是否可编辑
			        if(editbleFlag)
			        {
			            currentCell= currentTd;
			                
			            /*根据不同的设置进行编辑选择*/
			            if(currentCell.children.length!=1 && currentCell.parentNode.rowIndex != 0)  
			            {
			                var cellN = currentCell.cellIndex;
			                if(colStyle[parseInt(cellN)] == 'txt')
			                    editCell(mainTab,currentCell,'txt',true);   
			                else if(colStyle[parseInt(cellN)] == 'sel')
			                    editCell(mainTab,currentCell,'sel',true,sText[parseInt(cellN)],sValue[parseInt(cellN)]);
			                
			                if(currentCell.children.length > 0)
			                {
			                    if(currentCell.children[0].type == "select-one")
			                        currentCell.children[0].focus();
			                    else
			                        currentCell.children[0].select();
			                }
			            }
			        }
			    }
			
			    selectRowProxy(currentRowIndex);
			}
			
			//增加点击一行时的代理动作，参数是选中当前行号
			function selectRowProxy(currentRowIndex){}
			
			//表格指定位置变为可编辑
			//目前支持文本和下拉框
			function editCell(oTable,oCell,editType,bEditable,sText,sValue)
			{
			    if (bEditable)
			    {   
			        switch(editType)
			        {
			            case 'txt':
			                if(sText == null)
			                    sText = true;
			                oCell.innerHTML = "<input id=dyText type=text  size=16 onKeyDown = judgeKeyToDo() value=" 
			                                  // + oCell.innerText.replaceHTML() + ">";
			                	 				 + oCell.innerText + ">";
			                break;
			            case 'sel':         
			                var preValue = oCell.data;//获取当前表格的内容，在下拉框中选中
			                for(var i=0; i<sValue.length; i++)
			                {
			                    //如果是原有表格的内容，则默认选中
			                    if(sValue[i] == preValue)
			                        optionText += "<option value='"+sValue[i]+"' selected>"+sText[i];
			                    else                    
			                        optionText += "<option value='"+sValue[i]+"'>"+sText[i];
			                }
			                oCell.innerHTML="<select id=powerTableSel onKeyDown = judgeKeyToDo()>"+optionText+"</select>";
			                //为select的onchange指定事件
			                oCell.children[0].onchange = selectChangeAction;
			                //清空缓冲区
			                optionText = "";
			                break;
			        }
			    }
			}
			
			//向上移动指定表格的行
			function Move_up(objTable)
			{
			    event.cancelBubble=true;
			    if(currentRowIndex == null)
			        return;
			
			    if(currentRowIndex <= 1)
			        return;
			    else
			    {
			        MoveUp(objTable,currentRowIndex);
			        //当前选择也上移
			        --currentRowIndex;
			    }
			    setEvenOddColor(mainTab);
			}
			
			function Move_down(objTable)
			{
			    event.cancelBubble=true;
			    if(currentRowIndex == null)
			        return;
			        
			    if(currentRowIndex == mainTab.rows.length-1)
			        return;
			    else
			    {
			        MoveDown(objTable,currentRowIndex);
			        //当前选择也下移
			        ++currentRowIndex;
			    }
			    setEvenOddColor(mainTab);
			}
			
			function add_row(objTable) 
			{
			    event.cancelBubble=true;
			    clearColor();
			    var cellNow = cellData;
			    //如果没选中行，则在表格的最下方插入
			    var pos = currentRowIndex==null?objTable.rows.length:(currentRowIndex+1);
			    addRow(objTable,pos,cellNow);
			
			    currentCell=null;
			    readDef(objTable);
			    setEvenOddColor(mainTab);
			    //清除设置的数据
			    for(var i=0; i < mainTab.rows[0].cells.length; i++)
			        cellData[i] = "&nbsp;";
			}
			
			//删除行，并处理当前选择项为不选择
			function del_row(objTable) 
			{
			    if(currentRowIndex == null)
			        return;
			
			    if(confirm("确实要删除第"+currentRowIndex+"行吗?"))
			    {
			        delRow(objTable,currentRowIndex);
			        currentRowIndex=null;
			        currentCell=null;
			        clearColor();
			        setEvenOddColor(mainTab);
			    }
			}
			
			function res_tab(objTable)
			{
			    objTable.outerHTML=orgContent;
			    PowerTableEdit(objTable.id);
			}
			
			//在表格中指定位置,插入元素
			function addRow(oTable,rowIndex2Add,c)
			{
			    if (rowIndex2Add<0 || rowIndex2Add>oTable.rows.length)
			        return;
			    var currentCell;
			    var newRow=oTable.insertRow(rowIndex2Add);
			    for (var i=0;i<c.length;i++) 
			    {
			        //modified  2003.8.28
			        //增加一条数据时，如果时select形式的编辑方式，则搜索sValue和sText
			        //找到与输入值相符的value赋给td的data
			        if(colStyle[i] == "sel")
			        {
			            //如果没有设置数据而直接添加，会在编辑方式为
			            //select的td中添加选择框数据的第一项
			            if(c[i] == "&nbsp;" || c[i] == "")
			                c[i] = sText[i][0];
			            currentCell=newRow.insertCell(i);
			            currentCell.innerHTML= c[i];
			            for(var j=0; j<sText[i].length; j++)
			            {
			                if(c[i] == sText[i][j])
			                {
			                    currentCell.data= sValue[i][j];
			                }
			            }
			        }
			        else
			        {
			            currentCell=newRow.insertCell(i);
			            currentCell.innerHTML= c[i];
			        }
			        //modified 2004.8.28
			        //增加一条数据时，如果时select形式的编辑方式，则搜索sValue和sText
			        //找到与输入值相符的value赋给td的data
			    }
			}
			
			//删除指定行
			function delRow(oTable,nRowIndex2Del)
			{
			    //不删除标题行；指定行不在表格中也不执行删除；
			    if (oTable.rows.length==1
			          ||nRowIndex2Del==null
			            ||nRowIndex2Del==0
			              || nRowIndex2Del>=oTable.rows.length) 
			        return;
			    else
			        oTable.deleteRow(nRowIndex2Del);
			}
			
			function MoveUp(oTable,nRowIndex2Move)
			{
			    //判断移动的行是否合法
			    if(nRowIndex2Move==null 
			        || nRowIndex2Move<=1
			            || nRowIndex2Move>=oTable.rows.length)return;
			            
			    ChangeRow(oTable,nRowIndex2Move,--nRowIndex2Move);
			}
			
			//向下移动指定表格的行
			function MoveDown(oTable,nRowIndex2Move)
			{
			    //判断移动的行是否合法
			    if(nRowIndex2Move==null 
			        || currentRowIndex==oTable.rows.length 
			            || currentRowIndex==0)
			        return;
			            
			    ChangeRow(oTable,nRowIndex2Move,++nRowIndex2Move);
			}
			
			//指定表单的两行互换
			function ChangeRow(oTable,nRowIndex1,nRowIndex2)
			{
			    oTable.rows[nRowIndex1].swapNode(oTable.rows[nRowIndex2]);
			}
			
			//获取指定tag的元素 [逐级查找] 
			function getElement(oElement,sTag)
			{
			    sTag = sTag.toLowerCase();
			    if(oElement.tagName.toLowerCase()==sTag)
			        return oElement;
			    while(oElement=oElement.offsetParent)
			    {
			        if(oElement.tagName.toLowerCase()==sTag) 
			            return oElement;
			    }
			    return(null);
			}
			
			function ClearColor(oTable,nCurRowIndex,oCurCell)
			{
			    //清除选中行表现
			    if(nCurRowIndex!=null && nCurRowIndex != -1)
			    {
			        for(i=0;i<oTable.rows[nCurRowIndex].cells.length;i++)
			        {
			            with(oTable.rows[nCurRowIndex].cells[i])
			            {
			                style.backgroundColor=oBgc;
			                style.color=oFc;
			            }
			        }
			    }
			    //清除可编辑表格
			    if(oCurCell!=null)
			    {
			        if (oCurCell.children.length>0 )
			        {
			            if(oCurCell.children[0].tagName.toLowerCase() != "img"
			                &&oCurCell.children[0].tagName.toLowerCase() != "a")
			            {
			                if(oCurCell.children[0].tagName.toLowerCase() == "input")
			                    oCurCell.innerHTML=oCurCell.children[0].value.replaceHTML();
			                else if(oCurCell.children[0].tagName.toLowerCase() == "select")
			                    oCurCell.innerHTML=oCurCell.children[0].options[oCurCell.children[0].selectedIndex].text;
			            }
			        }
			    }
			}
			
			//读取表格现有颜色
			function ReadOrgColor(oTable)
			{
			    for(var i=0;i<oTable.rows.length;i++)
			    {
			        for(var j=0;j<oTable.rows[i].cells.length;j++)
			        {
			            with(oTable.rows[i])
			            {
			                cells[j].oBgc = "";
			                cells[j].oFc  = "";
			            }
			        }
			    }
			}
			
			function setEvenOddColor(mainTab)
			{
			    //增加奇偶行的css控制 奇行：tdOdd，偶行：tdEven
			    for(var i=1; i<mainTab.rows.length; i++)
			    {
			        if(i%2 == 0)
			            mainTab.rows[i].className = "TrEven";
			        else
			            mainTab.rows[i].className = "TrOdd";
			    }
			}
			
			//根据在编辑框按键的不同而采取不同的动作
			function judgeKeyToDo()
			{
			    //按键是tab
			    if(event.keyCode == 9)
			    {
			        var cellN;
			        if(currentCell.cellIndex == mainTab.rows[currentRowIndex].cells.length-1)
			            cellN = -1;
			        else
			            cellN = currentCell.cellIndex;
			        var nextCell = mainTab.rows[currentRowIndex].cells[cellN+1];        
			        //如果下一个表格未指定编辑方式，跳过
			        while(colStyle[parseInt(cellN+1)] == null)
			        {
			            cellN = cellN + 1; 
			            nextCell = mainTab.rows[currentRowIndex].cells[cellN+1];
			        }
			        //如果编辑方式为txt
			        if(colStyle[parseInt(cellN+1)] == 'txt')
			        {
			            if(currentCell.children.length>0)
			            {
			                if(currentCell.children[0].tagName.toLowerCase() == "input")
			                    currentCell.innerHTML=currentCell.children[0].value.replaceHTML();
			                else if(currentCell.children[0].tagName.toLowerCase() == "select")
			                    currentCell.innerHTML=currentCell.children[0].options[currentCell.children[0].selectedIndex].text;
			            }
			            editCell(mainTab,nextCell,'txt',true);
			        }
			        //如果编辑方式为select
			        else if(colStyle[parseInt(cellN+1)] == 'sel')
			        {
			            if(currentCell.children.length>0)
			            {
			                if(currentCell.children[0].tagName.toLowerCase() == "input")
			                    currentCell.innerHTML=currentCell.children[0].value.replaceHTML();
			                else if(currentCell.children[0].tagName.toLowerCase() == "select")
			                    currentCell.innerHTML=currentCell.children[0].options[currentCell.children[0].selectedIndex].text;
			            }
			            editCell(mainTab,nextCell,'sel',true,sText[parseInt(cellN+1)],sValue[parseInt(cellN+1)]);
			        }
			        //设置当前表格为下一个单元格
			        currentCell = nextCell;
			        
			        if(currentCell.children.length > 0)
			        {
			            if(currentCell.children[0].type == "select-one")
			                setTimeout('currentCell.children[0].focus()',10);
			            else
			                setTimeout('currentCell.children[0].select()',10);
			        }
			    }
			    //如果按键是enter
			    if(event.keyCode == 13)
			    {
			        if(currentCell.children[0].tagName.toLowerCase() == "input")
			            currentCell.innerHTML=currentCell.children[0].value.replaceHTML();
			        else if(currentCell.children[0].tagName.toLowerCase() == "select")
			            currentCell.innerHTML=currentCell.children[0].options[currentCell.children[0].selectedIndex].text;
			    }
			}
			
			String.prototype.replaceHTML = function()
			{
			    var result = this;
			    result = result.replace(/&/g,"&amp;");
			    result = result.replace(/</g,"&lt;");
			    result = result.replace(/>/g,"&gt;");
			    result = result.replace(/\s/g,"&nbsp;");
			    if(result == "")
			        result = "&nbsp;";
			    return result;
			}
			
		/*********************************** TableEdit.js  end *********************************/
		
			function addCourse(){
				window.open ('elclass_course_selectList.action?elclassId=${elclassId}&status=0','选择课程','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
				//window.open ('page.html','newwindow','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
			}

			//初始化可编辑表格
			function initTable()
			{
				//var classId = ${elclassId};
			    pt = new PowerTableEdit("table1");
			    var arrText = new Array();
			    arrText[0] = "学完";
			    arrText[1] = "考过";
			    arrText[2] = "学完且考过";
			    var arrValue = new Array();
			    arrValue[0] = "1";
			    arrValue[1] = "2";
			    arrValue[2] = "3";
			    
			    pt.setCol(6,'txt');
			    pt.setCol(7,'txt');
			    pt.setCol(8,'txt');
			    pt.setCol(9,'txt');
			    pt.setCol(10,'sel',arrText,arrValue);
			    
			}

			//更新数据
			function updateData(currentRowIndex){
				if(currentRowIndex != null){
					var rowdata = getRowData(currentRowIndex);
					//alert(rowdata);
					var courseId = rowdata[0];
					var startTime_2=rowdata[6];
					var endTime_2=rowdata[7];
					var suggestcredit = rowdata[8]; 
					var setcredit = rowdata[9];
					var getcredit = rowdata[10];
					//alert("classId:"+ classId + ",suggestcredit:" + suggestcredit +",setcredit:"+setcredit+",getcredit:"+getcredit);
					
					$.post("elclass_course_modify.action", {
						'courseId':courseId,
						'elclassId':'${elclassId}',
						'suggestcredit':suggestcredit,
						'setcredit':setcredit,
						'getcredit':getcredit,
						'startTime_2':startTime_2,
						'endTime_2':endTime_2
						}, 
						function (data) {
							//alert('更新成功');
						});
					}
			}
		</script>

	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<BODY onLoad="initTable();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻列表页" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">必修课管理</span>
			</li>
			<li class="sep">
			</li>
			<s:if test="elclassId != ''">
				<s:if test="elclass.status ==2 ">
					<li><span style="color:red">您正在审核该培训班，请不要修改培训班信息</span></li>
				</s:if><s:else>
					<li>
						<a style="cursor: hand"
							onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
							onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
							href="javascript:void(0)" onClick="addCourse()">添加课程</a>
					</li>
				</s:else>
			</s:if>
			<s:else>
				<li>保存培训班后可添加课程...</li>
			</s:else>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			
				<div class="divClass">
					<table width="100%" align="center" cellpadding="1" cellspacing="1" id="table1">
						<tr>
							<th width="110" height="30" align="center" >
								结业考场审核							</th>
							<th width="60">
								课程预览						</th>
							<th width="30" height="30" align="center" >
								ID							</th>
							<th width="100" height="30" align="center" >
								课程名称							</th>
							<!-- <th width="70" height="30" align="center" >
								创建者							</th>
							<th width="110" height="30" align="center" >
								创建时间							</th>
							<th width="100" height="30" align="center" >
								课程类别							</th>
							<th width="80" height="30" align="center" >
								课程时长							</th> -->
							<th width="100" height="30" align="center" >
								开始时间							</th>
							<th width="100" height="30" align="center" >
								结束时间							</th>
							<th width="60" height="30" align="center" >
								建议学分							</th>
							<th width="60" height="30" align="center" >
								设置学分							</th>
							<th width="60" height="30" align="center" >
								结业方式							</th>
						</tr>
			<s:if test="bxCourses.size==0">
				  </table>
				</div>
			</s:if>
			<s:else><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="bxCourses">
							<tr>
								<td align="center" > 
									<a href="examroom_class_shInit.action?elclassId=${elclassId}&courseId=${id}" target= "_blank " class=textbg6>考场审核</a>
								</td>
								<td align="center" >
									<!-- <a href="elclass_course_bx_del.action?elclassId=${elclassId}&courseId=${id}" class=textbg4>删除</a> -->
									<a href="course_preview.action?course.id=${id}" target= "_blank " class=textbg4>预览</a>
								</td>
								<td height="30" align="center" >
									<s:property value="id" />
								</td>
								<td height="30" align="center" >
									<s:property value="name" />
								</td>
								<!-- <td height="30" align="center" >
									<s:property value="creater.realname" />
								</td>
								<td height="30" align="center" >
									<s:date name="createtime" format="yyyy-MM-dd" />
								</td>
								<td height="30" align="center" >
									<s:property value="ctype.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="during" />分钟
								</td> -->
								<td id="start" height="30" align="center" >
									<s:date name="roomstart" format="yyyy-MM-dd~HH:mm:ss" />
								</td>
								<td id="end" height="30" align="center" >
									<s:date name="roomend" format="yyyy-MM-dd~HH:mm:ss" />
								</td>
								<td height="30" align="center" >
									<s:property value="suggestcredit" />
								</td>
								<td height="30" align="center" >
									<s:property value="setcredit" />
								</td>
								<td height="30" align="center" bgcolor="#ECEDEB" data="${getcredit==0?1:getcredit}">
									<s:if test="getcredit == 1">
											学完
									</s:if>
									<s:elseif test="getcredit == 2">
											考过
									</s:elseif>
									<s:elseif test="getcredit == 3">
											学完且考过
									</s:elseif>
									<s:else>
											学完
									</s:else>
								</td>
							</tr>
						</s:iterator></tbody>
					</table>
					</div>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
