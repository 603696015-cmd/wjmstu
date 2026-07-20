/*** 判断是否为“YYYYMM”式的时期 ***/
function isDate6(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    if (year < 1700 || year > 2500) return false;
    if (month < 1 || month > 12) return false;
    return true;
}

function isDate6_2(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = "19"+sDate.substring(0, 2);
    month = sDate.substring(2, 4);
    day = sDate.substring(4,6);
    if (year < 1700 || year > 2500) return false;
    if (month < 1 || month > 12) return false;
    if (day < 1 || day > 31) return false;
    return true;
}

/*** 判断是否为“YYYYMMDD”式的时期 ***/
function isDate8(sDate) {
    if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);

    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year < 1700 || year > 2500) return false;
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false;
    if (day < 1 || day > iaMonthDays[month - 1]) return false;
    return true;
}  
/*** 身份证号码验证 ***/
function isIdCardNo(num) {
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var parityBit = new Array("1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2");
    var parityBit2 = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
    var varArray = new Array();
    var intVal;
    var lngProd = 0;
    var intCheckDigit;
    var intCheckDigit2;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }
    // check and set val
    for (i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < "0" || varArray[i] > "9") && (i != 17)) {
            return false;
        }
        else if (i < 17) {
            varArray[i] = varArray[i] * factorArr[i];
        }
    }

    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6, 14);
        if (isDate8(date8) == false) {
            return false;
        }
        // calculate the sum of the prod ts
        for (i = 0; i < 17; i++) {
            lngProd = lngProd + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProd % 11];
        intCheckDigit2 = parityBit2[lngProd % 11];
        // check last digit
        if ((varArray[17] != intCheckDigit)&&(varArray[17] != intCheckDigit2)) {
            return false;
        }
    }
    else {        //length is 15
        //check date
        var date6 = idNumber.substring(6, 12);
        if (isDate6_2(date6) == false) {
            return false;
        }
    }
    return true;
}
function searchUserInit(){
     width=600;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("userRegister.action?x="+Math.random(),null,sFeature);
	 //alert(rv);
	 if(rv!=undefined&&rv!=""){
		 //var bh=rv.split("_");
		 var bh=rv.split("-=wys=-");
		 document.getElementById("danwei").value=bh[2];
		 document.getElementById("danweiName").value=bh[1];
	 }
}

function searchUserInit2(){
     width=600;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("userRegister2.action?x="+Math.random(),null,sFeature);
	 if(rv!=undefined&&rv!=""){
		 //var bh=rv.split("_");
		 var bh=rv.split("-=wys=-");
		 document.getElementById("staid").value=bh[2];
		 document.getElementById("gangweiName").value=bh[1];
	 }
}
