known.backgroundName = known.background.substring(known.background.lastIndexOf("/")+1);
known.userUrl={
	saveBackground:known.realpath+"/admin/saveSysUserBg"
}
$(function(){
	initSystemBackground();
	$(document).on("click", ".system-icon-s", function() {
		 checkSystemIcon($(this));
	});
	
	$("#save-btn").click(function(){
		saveUserBackground($(this));
	});
	
	$("#saveUserPage").click(function(){
		saveUserPage();
	});
});
function initSystemBackground(){
	for(var i=1;i<=10;i++){
		var system_icon = $("<div class='system-icon-s' data='user_bg/bg"+i+".jpg'></div>").appendTo("#system-icon");
		$("<img src="+known.realpath+"/resources/images/user_bg/bg"+i+".jpg>").appendTo(system_icon);
		if(known.backgroundName==("bg"+i+".jpg")){
			$("<span class='icon-check'></span>").appendTo(system_icon);
			system_icon.css({"border":"2px solid #1094FA"});
		}
	}
	$('<div class="clear"></div>').appendTo("#system-icon");
};
function checkSystemIcon(curObj){
	$(".system-icon-s").css({"border":"2px solid #fff"});
	$("span.icon-check").remove();
	$("<span class='icon-check'></span>").appendTo(curObj);
	curObj.css({"border":"2px solid #1094FA"});
};

function saveUserBackground(curObj){
	if($(".system-icon-s span.icon-check").length==0){
		layer.msg("请选择背景图片", {icon: 5,time:1500});  
		return;
	}
	$.ajax({
		url : known.userUrl.saveBackground,
		type: 'POST',
		dataType: 'json',
		data : {
	    	background:$(".system-icon-s span.icon-check").parent().attr("data")
	    },
	    success:function(res){
	    	if(res.errorMsg != null){
					layer.msg(res.errorMsg, {icon: 5,time:1500});   
					return;
				}
			else{
					layer.msg('修改成功', {icon: 6,time:1500});
					location.reload();
			}
	    }
	});
};

