var pfmsBaseInfo;
var relevantCopies;
var passwordSetting;
var memberProfile;

function init(){
	pfmsBaseInfo = document.getElementById("pfmsBaseInfo");
	relevantCopies = document.getElementById('relevantCopies');
	passwordSetting = document.getElementById('passwordSetting');
	memberProfile = document.getElementById('memberProfile');
}

function showPfmsBaseInfo(){
	init();
	pfmsBaseInfo.style.display = "block";
	relevantCopies.style.display = "none";
	passwordSetting.style.display = "none";
	memberProfile.style.display = "none";
}

function showRelevantCopies(){
	init(); 
	pfmsBaseInfo.style.display = "none";
	relevantCopies.style.display = "block";
	passwordSetting.style.display = "none";
	memberProfile.style.display = "none";
}

function showPasswordSetting(){
	init();
	pfmsBaseInfo.style.display = "none";
	relevantCopies.style.display = "none";
	passwordSetting.style.display = "block";
	memberProfile.style.display = "none";
}

function showMemberProfile(){
	init();
	pfmsBaseInfo.style.display = "none";
	relevantCopies.style.display = "none";
	passwordSetting.style.display = "none";
	memberProfile.style.display = "block";
}