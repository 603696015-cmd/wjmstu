function showsubmenu(sid)
{
whichEl = eval("submenu" + sid);
imgmenu = eval("imgmenu" + sid);
if (whichEl.style.display == "none")
{
eval("submenu" + sid + ".style.display=\"\";");
imgmenu.background="images/leftmenu/main_47.gif";
}
else
{
eval("submenu" + sid + ".style.display=\"none\";");
imgmenu.background="images/leftmenu/main_48.gif";
}
}
function lmenu(id,name,hrefStr,level,target){
		this.id = id;
		this.name = name;
		this.hrefStr =hrefStr ; 
		this.target = target;
		this.level =level
		this.child=[];
}
lmenu.prototype.addChild = function (obj){
	this.child[this.child.length] =obj;
}
lmenu.prototype.hidden = function (){
	document.getElementById(this.id).style.display="none";
}
lmenu.prototype.show = function (){
	document.getElementById(this.id).style.display="block";
}
lmenu.prototype.writeItems = function (){
	var sid = "";
	if(this.level==0){
		document.write("<table id='"+this.id+"' width=\"165\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
	 "<tr>"+
		 "<td height=\"28\" background=\"images/leftmenu/main_40.gif\">"+
			 "<table width=\"100%\" border=\"0\" cellspacing=\"0\""+
				 "cellpadding=\"0\">"+
				 "<tr>"+
					 "<td width=\"19%\">"+
					 "	&nbsp;"+
					 "</td>"+
				 "	<td width=\"81%\" height=\"20\">"+
					 "	<span class=\"STYLE1\">"+this.name+"</span>"+
					 "</td>"+
			 "	</tr>"+
			 "</table>"+
		 "</td>"+
	 "</tr><tr><td>");
	}
	if(this.level==1){
		sid = this.id;//.substring(this.id.length-1,this.id.length) ;
		document.write(
          "<table width=\"151\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\">"+
          "<tr>"+
          "	<td height=\"23\" background=\""+basePath+"images/leftmenu/main_47.gif\" id=\"imgmenu"+sid+"\" class=\"menu_title\" onMouseOver=\"this.className='menu_title2';\" onClick=\"showsubmenu('"+sid+"')\" onMouseOut=\"this.className='menu_title';\" style=\"cursor:hand\">"+
          "		<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
          "    		<tr>"+
          "     	 	<td width=\"18%\">&nbsp;</td>"+
          "      		<td width=\"82%\" class=\"STYLE1\">" +this.name +" </td>"+
          "   		</tr>"+
          "		</table>"+
          "	</td>"+
          "</tr>"+
          "<tr>"+
          "<td background=\""+basePath+"images/leftmenu/main_51.gif\" style='display:none;' id=\"submenu"+sid+"\"> "+
          "<div class=\"sec_menu\" >"+
     	  "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" + 
     	  "<tr>"+
          "<td>" );
	}
	if(this.level==2){
	document.writeln(
                  "<table width=\"90%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">"+
                  "	<tr>"+
                  " 	<td width=\"16%\" height=\"25\">"+
                  "  	  <div><img src=\"images/leftmenu/left.gif\" width=\"10\" height=\"10\" /></div>"+
                  "		</td>"+
                  "  	<td width=\"84%\" height=\"23\">"+
                  "			<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
                  "      	<tr>"+
                  "     	   <td height=\"20\" style=\"cursor:hand\" onmouseover=\"this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; \"onmouseout=\"this.style.borderStyle='none'\">"+
                  " 			<a class=\"STYLE3\" href='"+this.hrefStr+"' target='"+this.target+"'>"+this.name+"</a>"+
                  "			   </td>"+
                  "     	 </tr>"+
                  " 		</table>"+
                  "		</td>"+
                  "	</tr>"+
                  "</table>");
	}
	for(var i = 0 ;i<this.child.length;++i){
		this.child[i].writeItems();
	} 
	if(this.level==1){
		document.write(
		"</td>"+
		"</tr>"+
       	"<tr>"+
        "<td height=\"5\"><img src=\"images/leftmenu/main_52.gif\" width=\"151\" height=\"5\" />"+
        "</td>"+
        "</tr>"+
        "</table>"+
        "</div>"+
        "</td>"+
        "</tr>"+
        "</table> ");
	}
	if(this.level==0){
		document.write("</td></tr></table>");
	}
	
} 
function showMenu(model){
		l1.hidden();
		l2.hidden();
		l3.hidden();
		l4.hidden();
		l5.hidden();
		l6.hidden();
		l7.hidden();
		l8.hidden();
		l9.hidden();
	if(model=='common'){
		l1.show();
	}
	if(model=='course')
	{
		l2.show();
	}
	if(model=='question'){
		l3.show();
	}
	if(model=='student'){
		l4.show();
	}
	if(model=='talent'){
		l5.show();
	}
	if(model=='knowledge'){
		l6.show();
	}
	if(model=='assist'){
		l7.show();
	}
	if(model=='coursemakeman'){
		l8.show();
	}
	if(model=='forumman'){
		l9.show();
	}
	//alert(document.getElementById("menu_"+model).className)
}
function showTopMenu(model){

}
function initMenu(basepath,cid){
	basePath = basepath;
	l101 = new lmenu("l101","用户管理","",1);
	l101.addChild(new lmenu("l10101","部门管理","dep_list.action",2,"rightFrame"));			
	l101.addChild(new lmenu("l10102","学员管理","account_searchInit.action",2,"rightFrame"));			
	l101.addChild(new lmenu("l10103","角色管理","role_list.action",2,"rightFrame"));			
	l101.addChild(new lmenu("l10104","功能列表","func_list.action",2,"rightFrame"));			
	//l101.addChild(new lmenu("l10103","工作组管理",".action",2,"rightFrame"));	
	//l101.addChild(new lmenu("l10104","消息群发","mess_send2groupInit.action",2,"rightFrame"));	
	//l101.addChild(new lmenu("l10105","注册设置","registersetInit.action",2,"rightFrame"));	
	//l101.addChild(new lmenu("l10105","系统设置","otherset.action",2,"rightFrame"));	
	l102 = new lmenu("l102","课程管理","",1);
	l102.addChild(new lmenu("l10201","课程类别管理","coursetype_list.action",2,"rightFrame"));			
	//l102.addChild(new lmenu("l10202","创建课程","course_addInit.action",2,"rightFrame"));			
	//l102.addChild(new lmenu("l10203","我创建的课程","myCourse_list.action?pN=0&pS=10",2,"rightFrame"));			
	l102.addChild(new lmenu("l10204","可分配的课程","course_assignInit.action",2,"rightFrame"));			
	//l102.addChild(new lmenu("l10205","我监考的课程","myExamroom_list.action",2,"rightFrame"));			
	l102.addChild(new lmenu("l10206","处理课程删除申请","course_delete_list.action?pN=0&pS=10",2,"rightFrame"));			
	//l102.addChild(new lmenu("l10207","考试场次管理","examroom_listInit.action",2,"rightFrame"));
	//l102.addChild(new lmenu("l10208","选课审核","course_selectlist.action?pN=0&pS=10&status=3",2,"rightFrame"));
	l102.addChild(new lmenu("l10209","查看课程小结","course_notequeryInit.action",2,"rightFrame"));
	//l102.addChild(new lmenu("l10209","课程推荐设置","course_hotsetInit.action",2,"rightFrame"));
	l103 = new lmenu("l103","培训班管理","",1);
	l103.addChild(new lmenu("l10301","培训班类别管理","cltype_list.action",2,"rightFrame") );
	l103.addChild(new lmenu("l10302","创建培训班","elclass_addInit.action",2,"rightFrame") );
	l103.addChild(new lmenu("l10303","我创建的培训班","elclass_list.action?pN=0&pS=10",2,"rightFrame") );
	l103.addChild(new lmenu("l10304","可分配培训班","elclass_assignlistInit.action",2,"rightFrame") );
	l103.addChild(new lmenu("l10305","选班审核","elclass_selectInit.action?pN=0&pS=10",2,"rightFrame") );
	l103.addChild(new lmenu("l10306","结业审核","elclass_graduate_apply_list.action?pN=0&pS=10",2,"rightFrame") );
	l103.addChild(new lmenu("l10307","处理培训班删除申请","elclass_delete_apply_list.action?pN=0&pS=10",2,"rightFrame") );
	//l104 = new lmenu("l104","新闻公告","",1);
	//l104.addChild(new lmenu("l10401","栏目类别管理","newstype_list.action",2,"rightFrame") );
	//l104.addChild(new lmenu("l10402","新闻公告管理","news_list.action?pN=0&pS=10",2,"rightFrame") );
	l104 = new lmenu("l104","考试管理","",1);
	l104.addChild(new lmenu("l10401","我监考的课程","myExamroom_list.action",2,"rightFrame"));			
	l104.addChild(new lmenu("l10402","考试场次管理","examroom_listInit.action",2,"rightFrame"));
	l104.addChild(new lmenu("l10403","我创建的考试场次","examroom_mylist.action",2,"rightFrame"));
	l104.addChild(new lmenu("l10404","全部考试场次","examroom_myalllist.action",2,"rightFrame"));
	
	l105 = new lmenu("l105","统计分析","",1);
	l105.addChild(new lmenu("l10501","用户统计","user_searchInit.action",2,"rightFrame") );
	l105.addChild(new lmenu("l10502","部门统计","dep_searchInit.action",2,"rightFrame") );
	l105.addChild(new lmenu("l10503","课程统计","course_searchInit.action",2,"rightFrame") );
	l105.addChild(new lmenu("l10504","考试统计","quiz_searchInit.action",2,"rightFrame") );
	l105.addChild(new lmenu("l10505","模拟考试统计","sim_searchInit.action",2,"rightFrame") );
	
	l107=  new lmenu("l107","系统管理","",1);
	l107.addChild(new lmenu("l10701","首页课程类别","coursetype_setInit.action",2,"rightFrame"));
	//l107.addChild(new lmenu("l10702","新闻公告栏目","newstype_list.action",2,"rightFrame") );
	//l107.addChild(new lmenu("l10703","新闻公告管理","news_list.action?pN=0&pS=10",2,"rightFrame") );
	l107.addChild(new lmenu("l10704","课程推荐设置","course_hotsetInit.action",2,"rightFrame"));
	l107.addChild(new lmenu("l10705","注册设置","registersetInit.action",2,"rightFrame"));
	l107.addChild(new lmenu("l10706","安全设置","student_mypwdalterInit.action",2,"rightFrame"));			
	l1=new lmenu("l1","培训管理","",0);
	l1.addChild(l101);
	l1.addChild(l102);
	l1.addChild(l103);
	l1.addChild(l104);
	l1.addChild(l105);
	//l1.addChild(l106);
	l1.addChild(l107);
	
	l201 = new lmenu("l201","课程信息","",1);
	l201.addChild(new lmenu("l20101","课程简介","exammanage.action?course.id="+cid,2,"rightFrame"));			
	l201.addChild(new lmenu("l20102","修改课程信息","course_alterInit.action?course.id="+cid,2,"rightFrame"));			
	l201.addChild(new lmenu("l20103","课程预览","coursepage_view.action?coursePage.id=-1&course.id="+cid,2,"_blank"));			
	l202 = new lmenu("l202","内容管理","",1);
	l202.addChild(new lmenu("l20201","练习管理","practicepaper_list.action?course.id="+cid+"&pracPaper.course.id="+cid+"&pracPaper.cpage.id=0",2,"rightFrame"));			
	l202.addChild(new lmenu("l20202","模拟考试","simexampaper_list.action?course.id="+cid,2,"rightFrame"));			
	l202.addChild(new lmenu("l20203","考试管理","quizpaper_list.action?course.id="+cid,2,"rightFrame"));			
	l202.addChild(new lmenu("l20204","网页管理","coursepage_list.action?course.id="+cid,2,"rightFrame"));			
	l203 = new lmenu("l203","批卷阅卷","",1);
	l203.addChild(new lmenu("l20301","试卷评阅","exampaperreadInit.action?course.id="+cid,2,"rightFrame"));			
	l203.addChild(new lmenu("l20302","模考试卷评阅","simpaperreadlist.action?course.id="+cid,2,"rightFrame"));			
	//l204 = new lmenu("l204","考试管理","",1);
	//l204.addChild(new lmenu("l20401","我监考的课程","myExamroom_list.action",2,"rightFrame"));			
	//l204.addChild(new lmenu("l20402","考试场次管理","examroom_listInit.action",2,"rightFrame"));
	l2 = new lmenu("l2","课程管理","",0);
	l2.addChild(l201);
	l2.addChild(l202);
	l2.addChild(l203);
	//l2.addChild(l204);
	l301 = new lmenu("l301","试题库管理","",1);
	l301.addChild(new lmenu("l30101","添加试题库","question_lib_addInit.action",2,"rightFrame"));			
	l301.addChild(new lmenu("l30102","管理试题库","question_lib_list.action",2,"rightFrame"));			
	l302 = new lmenu("l302","试题管理","",1);
	l302.addChild(new lmenu("l30201","添加试题","question_add_type.action",2,"rightFrame"));			
	l302.addChild(new lmenu("l30202","管理试题","question_listInit.action",2,"rightFrame"));			
	l303 = new lmenu("l303","试题导入","",1);
	l303.addChild(new lmenu("l30301","导入知识目录","questionlib_importInit.action",2,"rightFrame"));			
	l303.addChild(new lmenu("l30302","导入试题","question_importInit.action",2,"rightFrame"));			
	l304 = new lmenu("l304","试卷库管理","",1);
	l304.addChild(new lmenu("l30401","添加试卷库","exampaperLib_addInit.action",2,"rightFrame"));			
	l304.addChild(new lmenu("l30402","管理试卷库","exampaperLib_list.action",2,"rightFrame"));			
	l305 = new lmenu("l305","试卷管理","",1);
	l305.addChild(new lmenu("l30501","添加试卷","exampaper_addInit.action",2,"rightFrame"));			
	l305.addChild(new lmenu("l30501","试卷管理","exampaper_listInit.action",2,"rightFrame"));
	l3 = new lmenu("l3","题库管理","",0);
	l3.addChild(l301);
	l3.addChild(l302);
	l3.addChild(l303);		
	l3.addChild(l304);		
	l3.addChild(l305);
	l401 = new lmenu("l401","教学超市","",1);
	l401.addChild(new lmenu("l40101","浏览课程","courseIndex.action?pN=0&pS=10&course.ctype.id=2&containsub=1",2,"_blank"));
	l401.addChild(new lmenu("l40102","浏览培训班","class_listbytitle.action?pN=0&pS=10&elclass.name=",2,"_blank"));
	l401.addChild(new lmenu("l40104","我要选课","listCanAppalyCourseInit.action",2,"rightFrame"));
	l401.addChild(new lmenu("l40105","我要选班","listCanApplyClass.action?elclass.name=&pN=0&pS=10",2,"rightFrame"));
	l401.addChild(new lmenu("l40106","搜索课程","elstudentman/course_search.jsp",2,"rightFrame"));
	l401.addChild(new lmenu("l40106","选课排行","course_phlist.action?pN=0&pS=10",2,"rightFrame"));
	
	//l401.addChild(new lmenu("l40101","必修课","mybxcourse_list.action",2,"rightFrame"));			
	//l401.addChild(new lmenu("l40102","选修课","myxxcourse_list.action",2,"rightFrame"));			
	//l401.addChild(new lmenu("l40103","可申请课程","listCanAppalyCourseInit.action",2,"rightFrame"));			
	l402 = new lmenu("l402","学习银行","",1);
	l402.addChild(new lmenu("l40201","快捷学习","mycourselist.action",2,"rightFrame"));
	l402.addChild(new lmenu("l40202","我的课程","mybxcourse_list.action",2,"rightFrame"));
	l402.addChild(new lmenu("l40203","我的消息","mess_SendBox.action?pN=0&pS=10",2,"rightFrame"));
	l402.addChild(new lmenu("l40204","我的成绩","myquiz_result.action",2,"rightFrame"));
	
	l403 = new lmenu("1403","在线考场","",1);
	l403.addChild(new lmenu("l40301","在线自测","mybxcquiz_list.action",2,"rightFrame"));
	l403.addChild(new lmenu("l40302","提交小结","mybxcourse_list.action?elclass.name=&pN=0&pS=10",2,"rightFrame"));
	l403.addChild(new lmenu("l40303","查询结果","myquiz_result.action?elclass.name=&pN=0&pS=10",2,"rightFrame"));
	
	l404 = new lmenu("l404","学员社区","",1);
	l404.addChild(new lmenu("l40401","社区公告","forumListByBlockid.action?fblock.id=1",2,"_blank"));			
	l404.addChild(new lmenu("l40402","问卷调查","student_survey_list.action?pN=0&pS=10",2,"rightFrame"));			
	l404.addChild(new lmenu("l40403","在线投票","student_poll_list.action?pN=0&pS=10",2,"rightFrame"));			
	l404.addChild(new lmenu("l40404","学习论坛","forumIndex.action",2,"_blank"));			
	l404.addChild(new lmenu("l40405","我要发帖","forumAddInit.action",2,"_blank"));			
	l404.addChild(new lmenu("l40406","我的帖子","forum_list_byuid.action?pN=0&pS=10",2,"rightFrame"));			
	l405 = new lmenu("l405","我的知识","",1);
	l405.addChild(new lmenu("l40501","我的知识","myknowledge_list.action?pN=0&pS=10",2,"rightFrame"));			
	l405.addChild(new lmenu("l40502","发布知识","knowledge_addInit.action?pN=0&pS=10",2,"rightFrame"));			
	l405.addChild(new lmenu("l40503","知识中心","knowledge_center.action",2,"_blank"));			
	l405.addChild(new lmenu("l40504","资源管理","question_stuffList.action?pN=0&pS=10",2,"rightFrame"));			
	l405.addChild(new lmenu("l40505","添加新资源","question_stuffaddInit.action",2,"rightFrame"));			
	
	l406 = new lmenu("l406","辅助工具","",1);
	//l406.addChild(new lmenu("l40601","下载列表","stuff_listbyTitle.action?qstuff.title=&pN=0&pS=10",2,"_blank"));			
	l406.addChild(new lmenu("l40601","下载列表","elhelp/ansist_tools.jsp",2,"rightFrame"));
	 l407 = new lmenu("l407","个人设置","",1);
	 l407.addChild(new lmenu("l40701","基本信息","student_myinfo.action",2,"rightFrame"));			
	 l407.addChild(new lmenu("l40702","修改设置","student_myalterInit.action",2,"rightFrame"));			
	 l407.addChild(new lmenu("l40703","安全设置","student_mypwdalterInit.action",2,"rightFrame"));			
	
	//l402.addChild(new lmenu("l40201","考试成绩","myquiz_result.action",2,"rightFrame"));			
	//l402.addChild(new lmenu("l40202","模考成绩","mysimexam_result.action",2,"rightFrame"));			
	//l402.addChild(new lmenu("l40203","学分查询","myCredit_result.action",2,"rightFrame"));			
	//l406 = new lmenu("l406","我的考试","",1);
	//l406.addChild(new lmenu("l40601","必修课考试","mybxcquiz_list.action",2,"rightFrame"));			
	//l406.addChild(new lmenu("l40602","选修课考试","myxxcquiz_list.action",2,"rightFrame"));			
	l408 = new lmenu("1408","我的培训班","",1);
	l408.addChild(new lmenu("l40801","可申请培训班","listCanApplyClass.action?elclass.name=&pN=0&pS=10",2,"rightFrame"));
	l408.addChild(new lmenu("l40802","在学培训班","myelclass_list.action?pN=0&pS=10",2,"rightFrame"));
	l408.addChild(new lmenu("l40804","申请结业","graduate_applyInit.action",2,"rightFrame"));
	l408.addChild(new lmenu("l40805","证书查询","mydiploma_result_p.action",2,"rightFrame"));
	l409 = new lmenu("1409","测评考试","",1);
	l409.addChild(new lmenu("l40901","我要测评","student_talent_troom_list.action",2,"rightFrame"));
	//l409.addChild(new lmenu("l40902","我的测评","student_talent_mytroom_list.action",2,"rightFrame"));
	l409.addChild(new lmenu("l40903","我的得分","student_talent_mytroom_result.action",2,"rightFrame"));
	l4011 = new lmenu("14011","民主测评","",1);
	l4011.addChild(new lmenu("l401101","我要测评","student_talent_ztroom_list.action",2,"rightFrame"));
	l4011.addChild(new lmenu("l401102","我的测评","student_talent_myztroom_list.action",2,"rightFrame"));
	l4011.addChild(new lmenu("l401103","我的得分","student_talent_myztroom_result.action",2,"rightFrame"));
	l4012 = new lmenu("14012","专家地图","",1);
	l4012.addChild(new lmenu("l401201","地图概貌","student_talent_troom_list.action",2,"rightFrame"));
	l4012.addChild(new lmenu("l401202","专家搜索","student_talent_mytroom_list.action",2,"rightFrame"));
	l4012.addChild(new lmenu("l401203","专家论坛","student_talent_mytroom_result.action",2,"rightFrame"));
	
	//l404 = new lmenu("l404","我的消息","",1);
	//l404.addChild(new lmenu("l40401","发送消息","messsend2groupInit.action",2,"rightFrame"));			
	//l404.addChild(new lmenu("l40402","收件箱","mess_Rec.action?pN=0&pS=10",2,"rightFrame"));			
	//l404.addChild(new lmenu("l40403","发件箱","mess_SendBox.action?pN=0&pS=10",2,"rightFrame"));			
	//l405 = new lmenu("l405","个人设置","",1);
	//l405.addChild(new lmenu("l40501","基本信息","student_myinfo.action",2,"rightFrame"));			
	//l405.addChild(new lmenu("l40502","修改设置","student_myalterInit.action",2,"rightFrame"));			
	//l405.addChild(new lmenu("l40503","安全设置","student_mypwdalterInit.action",2,"rightFrame"));			
	//l407 = new lmenu("l407","投票调查","",1);
	//l407.addChild(new lmenu("l40701","问卷调查","student_survey_list.action?pN=0&pS=10",2,"rightFrame"));			
	//l407.addChild(new lmenu("l40702","投票","student_poll_list.action?pN=0&pS=10",2,"rightFrame"));			
	//l408 = new lmenu("1408","学习社区","",1);
	l4 = new lmenu("l4","学习中心","",0);
	l4.addChild(l401);
	l4.addChild(l402);
	l4.addChild(l403);
	l4.addChild(l404);
	l4.addChild(l405);
	l4.addChild(l406);
	l4.addChild(l407);
	l4.addChild(l408);
	l4.addChild(l409);
	l4.addChild(l4011);
	l4.addChild(l4012);

	l501 = new lmenu("l501","人才库","",1);
	l501.addChild(new lmenu("l50101","人才管理","account_searchInit.action?pN=0&pS=10",2,"rightFrame"));			
	//l501.addChild(new lmenu("l50102","专家管理","talent_expert_list.action",2,"rightFrame"));
	l502 = new lmenu("1502","客观评价","",1);
	l502.addChild(new lmenu("l50201","创建评价场次","talent_roomcollect_addInit.action?pN=0&pS=10",2,"rightFrame"));			
	l502.addChild(new lmenu("l50202","我创建的场次","talent_roomcollect_list.action?pN=0&pS=10",2,"rightFrame"));			
	l502.addChild(new lmenu("l50203","统计分析","talent_room_statInit.action?pN=0&pS=10",2,"rightFrame"));			
	l503 = new lmenu("1503","主观评价","",1);
	l503.addChild(new lmenu("l50301","创建评价场次","talent_ztroom_addInit.action",2,"rightFrame"));			
	l503.addChild(new lmenu("l50302","我创建的场次","talent_ztroom_list.action?pN=0&pS=10",2,"rightFrame"));			
	l503.addChild(new lmenu("l50303","统计分析","talent_ztroom_statInit.action",2,"rightFrame"));			
 	l504 = new lmenu("1504","专家库","",1);
    //l504.addChild(new lmenu("l50401","类别管理","talent_roomcollect_addInit.action?pN=0&pS=10",2,"rightFrame"));			
	l504.addChild(new lmenu("l50402","帐号管理","account_searchInit.action?pN=0&pS=10",2,"rightFrame"));			
	
      
  
	l5 = new lmenu("l5","人才管理","",0);
	l5.addChild(l504);
	l5.addChild(l501);
	l5.addChild(l502);
	l5.addChild(l503);
	 
	//l601 = new lmenu("l601","我的知识库","",1);
	//l601.addChild(new lmenu("l60101","我的知识","myknowledge_list.action?pN=0&pS=10",2,"rightFrame"));			
	l602 = new lmenu("l602","知识库管理","",1);
	l602.addChild(new lmenu("l60201","知识类别","knowledgetype_list.action",2,"rightFrame") );
	l602.addChild(new lmenu("l60202","知识管理","knowledge_listInit.action",2,"rightFrame") );
	l603 = new lmenu("l603","知识中心","",1);
	l603.addChild(new lmenu("l60301","知识中心","knowledge_center.action",2,"_blank") );
	l604 = new lmenu("l604","素材管理","",1);
	l604.addChild(new lmenu("l60401","资源管理","question_stuffList.action?pN=0&pS=10",2,"rightFrame"));			
	l604.addChild(new lmenu("l60402","添加新资源","question_stuffaddInit.action",2,"rightFrame"));			
	l6 = new lmenu("l6","知识管理","",0);
	//l6.addChild(l601);
	l6.addChild(l602);
	l6.addChild(l603);
	l6.addChild(l604);
	//l701 = new lmenu("l701","培训计划","", 1);
	//l701.addChild(new lmenu("l70101","制定培训计划","assist_plan_addInit.action",2,"rightFrame"));
	//l701.addChild(new lmenu("l70102","我发布的计划列表","assist_plan_list.action?pN=0&pS=10",2,"rightFrame"));
	//l701.addChild(new lmenu("l70103","待审核的计划列表","assist_plan_verifylist.action?pN=0&pS=10",2,"rightFrame"));
	//l701.addChild(new lmenu("l70104","查看计划实施详情","assist_plan_viewlist.action?pN=0&pS=10",2,"rightFrame"));
	
	l702 = new lmenu("l702","问卷调查","", 1);
	l702.addChild(new lmenu("l70201","制作问卷调查","assist_survey_addInit.action",2,"rightFrame"));
	l702.addChild(new lmenu("l70202","我发布的问卷调查","assist_survey_list.action?pN=0&pS=10",2,"rightFrame"));
	l703 = new lmenu("l703","投票管理","", 1);
	l703.addChild(new lmenu("l70301","制作投票","assist_poll_addInit.action",2,"rightFrame"));
	l703.addChild(new lmenu("l70302","我发布的投票","assist_poll_list.action?pN=0&pS=10",2,"rightFrame"));
	l701 = new lmenu("l701","新闻公告","", 1);
	l701.addChild(new lmenu("l70101","新闻公告栏目","newstype_list.action",2,"rightFrame") );
	l701.addChild(new lmenu("l70102","新闻公告管理","news_list.action?pN=0&pS=10",2,"rightFrame") );
	l704 = new lmenu("l704","消息群发","", 1);
	l704.addChild(new lmenu("l70401","消息群发","mess_send2groupInit.action",2,"rightFrame"));	
	l7=  new lmenu("l7","教学辅助","",0);
	l7.addChild(l701);
	l7.addChild(l702);
	l7.addChild(l703);
	l7.addChild(l704);
	
	l801 = new lmenu("l801","我创建的课程","", 1);
	l801.addChild(new lmenu("l80101","创建新课程","course_addInit.action",2,"rightFrame"));			
	l801.addChild(new lmenu("l80102","我创建的课程","myCourse_list.action?pN=0&pS=10",2,"rightFrame"));			
	l802 = new lmenu("l802","课件制作","", 1);
	l802.addChild(new lmenu("l80201","课件制作","myCoursemake_list.action?pN=0&pS=10",2,"rightFrame"));			
	
	l8=  new lmenu("l8","课件制作","",0);
	l8.addChild(l801);
	l8.addChild(l802);
	
	l901 = new lmenu("l901","板块设置","", 1);
	l901.addChild(new lmenu("l90101","版面分类","forum_blocktype_list.action",2,"rightFrame"));			
	l901.addChild(new lmenu("l90102","版面管理","forum_block_list.action",2,"rightFrame"));
				
	l902 = new lmenu("l902","文章管理","", 1);
	l902.addChild(new lmenu("l90201","文章管理","forum_list_byblockid.action?forum.title=&pN=0&pS=10",2,"rightFrame"));			
	l9=  new lmenu("l9","论坛管理","",0);
	l9.addChild(l901);
	l9.addChild(l902);
	
	l1.writeItems();
	l2.writeItems();
	l3.writeItems();
	l4.writeItems();
	l5.writeItems();
	l6.writeItems();
	l7.writeItems();
	l8.writeItems();
	l9.writeItems();
}
function changeTreeDisplay(obj1){
	var obj = document.getElementById("tree_list_td");
	if(obj.style.display==""||obj.style.display=="block"){
	 	obj.style.display="none";
	 	obj1.src="images/leftmenu/main_55_1.gif";
	}
	else{
		obj.style.display="block";
	 	obj1.src="images/leftmenu/main_55.gif";
	}
}