known.userIconUrl={
	saveSysUserIcon:known.realpath+"/userAdmin/saveSysUserIcon"
	
}
function tag(config) {
		var id = config.id;
		var contentClass = config.contentClass;
		var event = config.event || "click";
		var fun = config.fun || function() {};
		var tag = $("#" + id);
		var lis = tag.find("li");
		$("." + contentClass).css({
			"marginTop": "10px"
		});
		$("." + contentClass).hide();
		$("." + contentClass).eq(0).show();
		lis.each(function(i, v) {
			$(this).attr("index", i);
		});
		lis.bind(event, function() {
			lis.removeClass("active");
			$(this).addClass("active");
			if($(this).attr('index')==0){
				$("body").css('background-color', '#e9f0f5');
				$(this).css('border-bottom', '1px solid #E9F0F5');
			}
			else{
				$("body").css('background-color', '#fff');
				$(this).css('border-bottom', '1px solid #fff');
			}
			$("." + config.contentClass).hide();
			$("." + config.contentClass).eq($(this).attr("index")).show();
			fun($(this).attr("index"));
		});
	}

known.iconType = 0;
var uploader = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto : true,

    // swf文件路径
    swf : known.realpath + '/resources/webuploader/Uploader.swf',

    // 文件接收服务端。
    server : known.realpath + '/imageUploader/imageUpload.action',

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick : {
	id : '#filePicker', multiple : false
    },
    accept : {
	title : 'Images', extensions : 'gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG', mimeTypes : 'image/*'
    },
    duplicate : 1, // 不去重
    compress : false, // 压缩
    fileSingleSizeLimit : 2 * 1024 * 1024
});
uploader.on('fileQueued', function(file) {
    $("#filePicker").hide();
    $("#loading-upload").show();
});

uploader.on('uploadSuccess', function(file, response) {
    if (response.code == "SUCCESS") {
	$("#imgarea").show();

	cutter.reload(known.realpath+"/"+response.savePath);
    }
    $("#file_upload").hide();
    $("#uploading").hide();
});

uploader.on('error', function(handler) {
    if (handler == "F_EXCEED_SIZE") {
	alert("文件不能超过2M");
    }
});

var cutter = new jQuery.UtrialAvatarCutter({
		//主图片所在容器ID
		content : "picture_original",
		//缩略图配置,ID:所在容器ID;width,height:缩略图大小
		purviews : [{id:"picture_200",width:180,height:180}],
		//选择器默认大小
		selector : {width:180,height:180}
	}
);

$(function() {
	tag({
		id : "tag", contentClass : "tag-content", fun : function(index) {
			known.iconType = index;
		}
    });
    cutter.init();
    //初始化系统头像
    initSysUserIcon();
    $("#save-btn").click(function(){
	saveUserIcon();
    });
    
    $(document).on("click",".system-user-icon-s",function(){
	checkSysUserIcon($(this));
    })
});
//初始化系统头像
function initSysUserIcon(){
	for(var i=1;i<=10;i++){
		var system_icon = $("<div class='system-user-icon-s' data='default_usericon/"+i+".jpg'></div>").appendTo("#sys-user-icon");
		$("<img src="+known.realpath+"/resources/images/default_usericon/"+i+".jpg>").appendTo(system_icon);
	}
	$('<div class="clear"></div>').appendTo("#sys-user-icon");
}
//点击系统头像
function checkSysUserIcon(curObj){
	$(".system-user-icon-s").css({"border":"2px solid #fff"});
	$("span.icon-check").remove();
	$("<span class='icon-check'></span>").appendTo(curObj);
	curObj.css({"border":"2px solid #1094FA"});
}


//保存头像
function saveUserIcon(){
    
    //上传头像
    if(known.iconType==0){
	var data = cutter.submit();
	var imgPath = data.s;
	if(imgPath==""){
		layer.msg("请先上传头像", {icon: 5,time:1500});  
	    return;
	}
	var start = imgPath.lastIndexOf("upload")+7;
	var imgSavePath = imgPath.substring(start,data.s.size);
		$.ajax({
		url : known.userIconUrl.saveCustomerUserIcon,
		type: 'POST',
		dataType: 'json',
		data : {
		"img" : imgSavePath,
		"x1" : data.x,
		"y1" : data.y,
		"width" : data.w,
		"height" : data.h,
		"date" : new Date()
	    },
		success:function(res){
			if(res.msg != null){
					layer.msg(res.msg, {icon: 5,time:1500});
					return;
				}
			else{
					layer.msg('修改成功', {icon: 6,time:2000});
					location.reload();
			}
		}
	});
    } else if(known.iconType==1){//系统头像
	if($(".system-user-icon-s span.icon-check").length==0){
	    layer.msg("请选择头像", {icon: 5,time:1500});   
	    return;
	}
	var userIcon = $(".system-user-icon-s span.icon-check").parent().attr("data");
	$.ajax({
		url: known.userIconUrl.saveSysUserIcon,
		type: 'POST',
		dataType: 'json',
		data : {userIcon:userIcon},
		success:function(res){
			if(res.msg != null){
					layer.msg(res.msg, {icon: 5,time:1500});
					return;
				}
			else{
					layer.msg('修改成功', {icon: 6,time:2000});
					location.reload();
			}
		}
	});

    }
}