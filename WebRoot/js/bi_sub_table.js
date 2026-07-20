  var maxCount;
  var currentTable;
  var newRowHtml;
  var sumRowHtml;
  
  // 选择一行
  function _selectRow(row_index, currentTable)
  {
		for (i=1;i<currentTable.rows.length;i++) 
	    {
			if (row_index==i) 
				currentTable.rows.item(i).className="x_sub_table_row_selected";
			else 
				currentTable.rows.item(i).className="x_sub_table_row_unselected";
		}
  }
	//重新添加所有行的动态事件
	function _reAttachEvent(currentTable) {
			for (i=1;i<currentTable.rows.length;i++) 
			{
					currentTable.rows.item(i).onclick=   new Function("_selectRow(this.rowIndex,this. parentNode)");
					currentTable.rows.item(i).onchange = new Function("_recountSumRow()");
			}
		
	}

  // 插入一行
  function _addRow() 
  {
		var td; 
		var tr;
		var last_index;
		
		last_index = currentTable.rows.length;



		tr = currentTable.insertRow(last_index);
		tr.onclick=   new Function("_selectRow(this.rowIndex,this. parentNode)");
		tr.onchange = new Function("_recountSumRow()");
		tr.className="x_sub_table_row_unselected";

		//td=tr.insertCell(0);
		//td.noWrap=true;
		//td.align="center";
		//td.className="x_sub_table_row_td_hh";
		//td.innerHTML=tr.rowIndex ; 

		for (i=0;i<newRowHtml.length;i++) 
		{
			td=tr.insertCell(i);
			td.noWrap=true;
			if(i==newRowHtml.length-1)
				td.className="x_sub_table_row_td_right";
			else
				td.className="x_sub_table_row_td";
			td.innerHTML=newRowHtml[i];
			
		}
		maxCount.value = parseInt(maxCount.value) + 1;
  }
  // 删除一行
  function _deleteRow() 
  {
		var x=0;
		var last_index=currentTable.rows.length;
		for (i=1;i<last_index;i++) 
		{
			if (currentTable.rows.item(i).className=="x_sub_table_row_selected")
			{
				currentTable.deleteRow(i);
				last_index--;
				x=1;
			}
		}
		if (x==0) {alert("请用鼠标点击选择要删除的行！");return false;}
		
		return true;
  }

