
//菜单切换
function setFocus1(i)
{
 selectLayer1(i);
}
function selectLayer1(i)
{
 switch(i)
 {
 case 1:
 document.getElementById("foreshow1").style.display="block";
 document.getElementById("foreshow2").style.display="none"; 
 document.getElementById("foreshow3").style.display="none";
 document.getElementById("foreshow4").style.display="none";
 document.getElementById("foreshow5").style.display="none";
 document.getElementById("foreshow6").style.display="none";
 break;
 
 case 2:
 document.getElementById("foreshow1").style.display="none";
 document.getElementById("foreshow2").style.display="block";
 document.getElementById("foreshow3").style.display="none";
 document.getElementById("foreshow4").style.display="none";
 document.getElementById("foreshow5").style.display="none";
 document.getElementById("foreshow6").style.display="none";
 break;
 
 case 3:
 document.getElementById("foreshow1").style.display="none";
 document.getElementById("foreshow2").style.display="none";
 document.getElementById("foreshow3").style.display="block";
 document.getElementById("foreshow4").style.display="none";
 document.getElementById("foreshow5").style.display="none";
 document.getElementById("foreshow6").style.display="none";
 break;
 
 case 4:
 document.getElementById("foreshow1").style.display="none";
 document.getElementById("foreshow2").style.display="none";
 document.getElementById("foreshow3").style.display="none";
 document.getElementById("foreshow4").style.display="block";
 document.getElementById("foreshow5").style.display="none";
 document.getElementById("foreshow6").style.display="none";
 break;
 
 case 5:
 document.getElementById("foreshow1").style.display="none";
 document.getElementById("foreshow2").style.display="none";
 document.getElementById("foreshow3").style.display="none";
 document.getElementById("foreshow4").style.display="none";
 document.getElementById("foreshow5").style.display="block";
 document.getElementById("foreshow6").style.display="none";
 break;
 
 case 6:
 document.getElementById("foreshow1").style.display="none";
 document.getElementById("foreshow2").style.display="none";
 document.getElementById("foreshow3").style.display="none";
 document.getElementById("foreshow4").style.display="none";
 document.getElementById("foreshow5").style.display="none";
 document.getElementById("foreshow6").style.display="block";
 break;
 }
}



//首页查询服务js
function changediv(i)
{
selectLayer2(i);
}
function selectLayer2(i)
{
switch(i)
{
case 1:
document.getElementById("div1").style.display="block";
document.getElementById("div2").style.display="none"; 
document.getElementById("div3").style.display="none";
document.getElementById("div4").style.display="none";
document.getElementById("div5").style.display="none";
document.getElementById("div6").style.display="none";
break;

case 2:
document.getElementById("div1").style.display="none";
document.getElementById("div2").style.display="block";
document.getElementById("div3").style.display="none";
document.getElementById("div4").style.display="none";
document.getElementById("div5").style.display="none";
document.getElementById("div6").style.display="none";
break;

case 3:
document.getElementById("div1").style.display="none";
document.getElementById("div2").style.display="none";
document.getElementById("div3").style.display="block";
document.getElementById("div4").style.display="none";
document.getElementById("div5").style.display="none";
document.getElementById("div6").style.display="none";
break;

case 4:
document.getElementById("div1").style.display="none";
document.getElementById("div2").style.display="none";
document.getElementById("div3").style.display="none";
document.getElementById("div4").style.display="block";
document.getElementById("div5").style.display="none";
document.getElementById("div6").style.display="none";
break;

case 5:
document.getElementById("div1").style.display="none";
document.getElementById("div2").style.display="none";
document.getElementById("div3").style.display="none";
document.getElementById("div4").style.display="none";
document.getElementById("div5").style.display="block";
document.getElementById("div6").style.display="none";
break;

case 6:
document.getElementById("div1").style.display="none";
document.getElementById("div2").style.display="none";
document.getElementById("div3").style.display="none";
document.getElementById("div4").style.display="none";
document.getElementById("div5").style.display="none";
document.getElementById("div6").style.display="block";
break;
}
}

function j2gb(url)
{
   window.location.href="ht"+"tp://"+url;
   return;
}
function j2open(url)
{
   window.open("ht"+"tp://"+url);
   return;
}