var s,sn=0,timer,slen,timer2;
function scrollInit(){
s=getid("s1");
s.scrollTop=0;
slen=s.innerHTML.split("|");
s.innerHTML="";
for(var i=0;i<slen.length;i++){s.innerHTML+=(slen[i]+"<br />");}
s.innerHTML+=slen[0];
timer2=setInterval(scrollstart,3000);
s.onmouseover=function(){clearInterval(timer2);clearInterval(timer);s.style.backgroundColor="#ccc";}
s.onmouseout=function(){timer2=setInterval(scrollstart,3000);s.style.backgroundColor="#fff";}
}
function scrollstart(){
if(s.scrollTop>=(slen.length*52)){s.scrollTop=0;}
timer=setInterval(scrollexec,3);
}
function scrollexec(){
if(sn<52){
sn++;
s.scrollTop++;
}else{
sn=0;
clearInterval(timer);
}
}
function getid(id){return document.getElementById(id);}
window.onload=scrollInit;

function scrollInit1(){
s=getid1("s2");
s.scrollTop=0;
slen=s.innerHTML.split("|");
s.innerHTML="";
for(var i=0;i<slen.length;i++){s.innerHTML+=(slen[i]+"<br />");}
s.innerHTML+=slen[0];
timer2=setInterval(scrollstart,3000);
}
function scrollstart(){
if(s.scrollTop>=(slen.length*52)){s.scrollTop=0;}
timer=setInterval(scrollexec,3);
}
function scrollexec(){
if(sn<52){
sn++;
s.scrollTop++;
}else{
sn=0;
clearInterval(timer);
}
}
function getid1(id){return document.getElementById(id);}
window.onload=scrollInit1;