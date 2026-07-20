//////////////////////////////   表单全键盘操作的控制函数  //////////////////////
//表单热键的控制

//保存
document.onkeyup = function(){ _bill_hotkey() };
	 function _bill_hotkey (){
		 if	(event.keyCode==83  && event.ctrlKey){  //ctl+s  保存
			if(document.forms.length>0)
				_click_button(form1);			
			return;
		}
		 if	(event.keyCode==45  && event.ctrlKey){  //ctl+insert  增行
                            if (document.getElementsByName('add' ).length>0) {
                                document.getElementsByName('add' )[0].click();
                            } else if  (document.getElementById('add' )!=null) {
						        document.getElementById('add' ).click();
                            }
			return;
		}
	 }



////////////  表单项目的焦点控制

function _bill_item_keypress(obj,type) {

	//处理录入框
	if (type=="020") {
		 if	(event.keyCode==40 || event.keyCode==13) {  //下箭头 或回车
			event.keyCode=9;
			return;
		}
		 if	(event.keyCode==38  ) {  //上箭头 或回车
		  _bill_item_up_focus(form1,obj);
			return;
		}
		return;
	}
	//处理单选下拉
	if (type=="030") {
		 if	(event.keyCode==39 || event.keyCode==13) {  // //右箭头 或回车
			event.keyCode=9;
			return;
		}
		 if	(event.keyCode==37  ) {  //左箭头 或回车
		  _bill_item_up_focus(form1,obj);
			return;
		}
		return;
	}
	//处理日期
	if (type=="130") {/*
		alert(event.keyCode);
		 if	(event.keyCode==40 || event.keyCode==13) {  //下箭头 或回车
			event.keyCode=9;
			return;
		}
		 if	(event.keyCode==38  ) {  //上箭头 或回车
		  _bill_item_up_focus(form1,obj);
			return;
		}
		if (event.keyCode==9)  return;   //按tab或shift tab的时候，不弹出窗口*/
		obj.fireEvent("onclick");
		//event.keyCode=39;
		return;
	}
	//弹出窗口
	if (type=="120") {
		//alert(event.keyCode);
		 if	(event.keyCode==40 || event.keyCode==13) {  //下箭头 或回车
			event.keyCode=9;
			return;
		}
		 if	(event.keyCode==38  ) {  //上箭头 或回车
		  _bill_item_up_focus(form1,obj);
			return;
		}
		if (event.keyCode==9)  return;   //按tab或shift tab的时候，不弹出窗口
		obj.fireEvent("onclick");
		//event.keyCode=39;
		return false;
	}
	//处理大文本
	if (type=="090") {
		 if	((event.keyCode==40 || event.keyCode==13)   && event.ctrlKey){  //下箭头 或回车
			_bill_item_down_focus(form1,obj);			
			return;
		}
		 if	(event.keyCode==38  && event.ctrlKey  ) {  //上箭头 或回车
		  _bill_item_up_focus(form1,obj);
			return;
		}
		return;
	}

}
//将光标移动到上一个焦点
function _bill_item_up_focus(form1,obj) {
				//首先找到当前对象
			var i=0;
			while (i<form1.elements.length) {
				if (form1.elements[i].name==obj.name) break;
				i++;
			}
			//如果找到了对象，则设置上一个对象，如果有错，则继续向上找 
			if (i<form1.elements.length) {
				i--;
				if (i<0)  i=form1.elements.length-1;
				moveOk=false;
				while (!moveOk) {
					try {
						form1.elements[i].focus();
						moveOk=true;
					}	catch (e) {	
						i--;
						if (i<0)  i=form1.elements.length-1;
					}
				}
			}
}
//将光标移动到下一个焦点
function _bill_item_down_focus(form1,obj) {
				//首先找到当前对象
			var i=0;
			while (i<form1.elements.length) {
				if (form1.elements[i].name==obj.name) break;
				i++;
			}
			//如果找到了对象，则设置上一个对象，如果有错，则继续向上找 
			if (i<form1.elements.length) {
				i++;
				if (i==form1.elements.length)  i=0;
				moveOk=false;
				while (!moveOk) {
					try {
						form1.elements[i].focus();
						moveOk=true;
					}	catch (e) {	
						i++;
						if (i==form1.elements.length)  i=0;
					}
				}
			}
}

//将对象设置为焦点状态
function _bill_item_focus_bg(obj) {
	if(!obj.readOnly){
		obj.style.backgroundColor="#dfe7f7";
		obj.style.color="blue";
	}
}
//将对象设置为非焦点状态
function _bill_item_nofocus_bg(obj) {
	if(!obj.readOnly){
		obj.style.backgroundColor="#f8fcff";
		obj.style.color="#000000";
	}
}

function _blur_check(obj){
	
}


