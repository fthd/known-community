$(document).on("click","#image-con span a",function(){$(this).parent().remove();});
var loadingindex ={};
var uploader = WebUploader.create({
	auto: true,
	swf: known.realpath+ '/resources/webuploader/Uploader.swf',
	server: '/imageUploader/imageUpload.action',
	pick: {
		id: '#upload-image',
		multiple: false
	},
	accept: {
		title: 'Images',
		extensions: 'gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG',
		mimeTypes: 'image/*'
	},
	duplicate: 1,
	compress: false,
	fileSingleSizeLimit: 3 * 1024 * 1024
});
uploader.on('fileQueued', function(file) {
	if(known.userId=="" || known.userId == 0){
		goLogin();
	    return;
	}
	if ($("#image-con .imageItem").length > 2) {
		uploader.removeFile(file);
		layer.alert("图片不能超过3张", {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
		return;
	}
	$("#image-con").show();
		loadingindex = layer.load(0, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
});
uploader.on('uploadSuccess', function(file, response) {
	if (response.responseCode == "SUCCESS") {
		new ImageItem(response.savePath).appendTo($("#image-con"));
	}
	$("#image-con").show();
	layer.close(loadingindex); 

});
uploader.on('error', function(handler) {
	if (handler == "F_EXCEED_SIZE") {
		layer.alert("图片不能超过3M", {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
	}
});
$(document).on("click","#image-con span a",function(){$(this).parent().remove();});

function ImageItem(img) {
	var span = $('<span class="imageItem" data =' + img + ' ></span>');
	$('<img src="'+ known.realpath  + '/upload/' + img + '" class="showimg">').appendTo(span);
	$('<a href="javascript:void(0)"><img src="' + known.realpath + '/resources/images/icon/icon-del.png"></a>').appendTo(span);
	return span;
}

known.emotion_data = ["[围观]", "[威武]", "[给力]", "[浮云]", "[奥特曼]", "[兔子]", "[熊猫]", "[飞机]", "[冰棍]", "[干杯]", "[给跪了]", "[囧]", "[风扇]", "[呵呵]", "[嘻嘻]", "[哈哈]", "[爱你]", "[晕]", "[泪]", "[馋嘴]", "[抓狂]", "[哼]", "[抱抱]", "[可爱]", "[怒]", "[汗]", "[困]", "[害羞]", "[睡觉]", "[钱]", "[偷笑]", "[酷]", "[衰]", "[吃惊]", "[闭嘴]", "[鄙视]", "[挖鼻屎]", "[花心]", "[鼓掌]", "[失望]", "[思考]", "[生病]", "[亲亲]", "[怒骂]", "[太开心]", "[懒得理你]", "[右哼哼]", "[左哼哼]", "[嘘]", "[委屈]", "[吐]", "[可怜]", "[打哈气]", "[黑线]", "[顶]", "[疑问]", "[握手]", "[耶]", "[好]", "[弱]", "[不要]", "[没问题]", "[赞]", "[来]", "[蛋糕]", "[心]", "[伤心]", "[钟]", "[猪头]", "[话筒]", "[月亮]", "[下雨]", "[太阳]", "[蜡烛]", "[礼花]", "[玫瑰]"];
$(document).on("click", ".user-thumbnail img", function() {
	var bimg = $(this).attr("src").split("_s")[0];
		// var d = dialog({
		//     title: '图片查看',
		//     content: '<div style="text-align:center"><img src="' + bimg + '"style="max-width:1300px;"></div>'
		// });-
		// d.showModal();
		layer.open({
		maxWidth:1300,
        content: '<div style="text-align:center;"><a href="' + bimg + '" target="_blank"><img src="' + bimg + '"style="max-width:1300px;"></a></div>'
    });

});
$(document).on("click", "#load-more", function() {
	$(this).remove();
	var index = layer.load(0,{time: 800});
	loadShuoShuos(known.pageNum + 1);

});
$(document).on("click", "#op-at", function() {
	showAtUser($(this)[0], $("#shuoShuoArea"));
});
$(document).on("click", ".op-at", function() {
	showAtUser($(this)[0], $(this).parent().parent().find("textarea"));
});



	

	$(document).on("click", "#op-emotion", function() {
		showEmotion($(this)[0], $("#shuoShuoArea"));
	});
	$(document).on("click", ".comment-info", function() {
	showComment($(this));
	});
	$(document).on("click", ".op-emotion", function() {
	showEmotion($(this)[0], $(this).parent().parent().find("textarea"));
	});
		$(document).on("click", ".comment-at", function() {
		showCommentPost4At($(this));
	});
	$(document).on("click", ".reply", function() {
		if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
		publicShuoShuoComment($(this));
	});
	$(document).on("click", ".shuoshuo-item-info .like-info", function() {
		if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
		doShuoShuoLike($(this));
	});
	$("#publicShuoShuo").click(function(event) {
		if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
		publicShuoShuo();
	});
	function doShuoShuoLike(curObj){
		var likeCount = curObj.find("span");
		likeCountSpan = likeCount.text();
		var userlikepanel = curObj.parent().parent().parent().find('.user-like-panel').find('.like-info').eq(0);
		var shuoShuoId = curObj.attr('shuoshuoid');
		$.ajax({
			url: known.realpath + '/shuoShuo/doShuoShuoLike',
			type: 'POST',
			dataType: 'json',
			data: {"shuoshuoId": shuoShuoId},
			success:function(res){
				if(res.msg != null){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					})
				}
				else{
					likeCount.text(parseInt(likeCountSpan) + 1);
					var item = new LikeItem(res.data);
					userlikepanel.after(item);
					var index = layer.load(0,{time: 1000});
					var d = dialog({
					    content: "点赞成功",
					    quickClose: true// 点击空白处快速关闭
					});
					d.show();
					setTimeout(function() {
						d.close().remove();
					}, 1500);

				}
			}
		});
		
	}
	function publicShuoShuoComment(curObj){
		var commentform = curObj.parent().parent();
		var textarea = commentform.find('textarea');
		var cddate = commentform.parent().find('.cd-date');
		var content = $.trim(textarea.val());
		var shuoshuoId = curObj.attr('shuoshuoid');
		var commentcount = commentform.parent().find('.user-op-panel').children().find('.comment-info').find('span'); 
		var comentCountSpan = commentcount.text();
		if (content == "") {
			var d = dialog({
			    content: '评论内容不能为空',
			    quickClose: true// 点击空白处快速关闭
			});
			d.show();
			return;
		}
		if (content.length > 1000) {
			var d = dialog({
			    content: '评论内容不能超过1000字',
			    quickClose: true// 点击空白处快速关闭
			});
			d.show();
			return;
		}
		$.ajax({
			url: known.realpath + '/shuoShuo/publicShuoShuoComment',
			type: 'POST',
			dataType: 'json',
			data: {"shuoshuoId": shuoshuoId,
					"content" : content
				},
			success:function(res){
				if(res.msg != null){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					})
				}
				else{
					textarea.val("");
					commentform.hide();
					commentcount.text(parseInt(comentCountSpan) + 1);
					var item = new CommentItem(res.data);
					cddate.before(item);
					var index = layer.load(0,{time: 1000});
					var d = dialog({
					    content: "评论成功，+1积分",
					    quickClose: true// 点击空白处快速关闭
					});
					d.show();
					setTimeout(function() {
						d.close().remove();
					}, 1500);

				}
			}
		});	

	}
	function showCommentPost4At(curObj){
		var commentform = curObj.parent().parent().parent().parent().parent().find('.comment-form');
		commentform.show();
		var textarea = commentform.find('textarea');
		textarea.val(textarea.val()).focus().val(textarea.val() + "@" + curObj.attr("username") + "  ");
	}
	function showComment(curObj){
		var commentform = curObj.parent().parent().parent().find('.comment-form');
		$(".comment-form").hide();
		commentform.show();
		commentform.find('textarea').focus();
	}
	function showEmotion(targetObj, textarea){
		var d = dialog({
			width:300,
		    align: 'bottom left',
		    quickClose: true// 点击空白处快速关闭
		});
		var emotion_panel = $("<div></div>")
		var emotions = known.emotion_data;
		for (var i = 0, _len = emotions.length; i < _len; i++) {
			var item = $("<div data=" + emotions[i] + " class='emotion' title=" + emotions[i] + "><img src='" + known.realpath +"/resources/images/emotions/" + i + ".gif'></div>").appendTo(emotion_panel).bind("click", function() {
				d.close();
				textarea.val(textarea.val() + $(this).attr("data")).focus();
			});
		}
		d.content(emotion_panel);
		d.show(targetObj);
	}
	function showAtUser(targetObj, textarea){
		var d = dialog({
			width:380,
		    align: 'bottom left',
		    quickClose: true// 点击空白处快速关闭
		});
		var at_panel = $("<div></div>");
		$.ajax({
			url: known.realpath + '/userFriend/loadUserFriend',
			type: 'POST',
			dataType: 'json',
			data: {"pageNum": 1},
			success:function(res){
				if(res.msg != null){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				}
				else{
				var content = "";
				var list = res.data.list;
				var _len = list.length;
				if (_len == 0) {
					content = "没有关注的用户";
				} else {
					content = at_panel;
					for (var i = 0, item; i < _len, item = list[i]; i++) {
						$("<a href='javascript:;' class='at_user'><img src='" + item.friendUserIcon + "' class='img-thumbnail' style='width:40px;height:40px;'><span>" + item.friendUserName + "</span></a>").appendTo(content).click(function() {
							d.close();
							textarea.val(textarea.val()).focus().val(textarea.val() + "@" + $(this).text() + " ");
						});
					}
				}
				d.content(content);
				d.show(targetObj);
			}
			}
		});
		
	}
	function publicShuoShuo(){
		var content = $.trim($("#shuoShuoArea").val());
		var imageArry = [];
		var imageItems = $("#image-con .imageItem");
		for (var i = 0, _len = imageItems.length; i < _len; i++) {
			imageArry.push(imageItems.eq(i).attr("data"));
		}
		if (content == "") {
			var d = dialog({
			    content: '说说内容不能为空',
			    quickClose: true// 点击空白处快速关闭
			});
			d.show();
			return;
		}
		if (content.length > 1000) {
			var d = dialog({
			    content: '说说内容不能超过1000字',
			    quickClose: true// 点击空白处快速关闭
			});
			d.show();
			return;
		}
		$.ajax({
			url: known.realpath + '/shuoShuo/publicShuoShuo',
			type: 'POST',
			dataType: 'json',
			data: {
				content: content,
				imageUrl: imageArry.join("|")
			},
			success:function(res){
				if(res.msg != null){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					})
				}
				else{
					var item = new ShuoShuoItem(res.data);
					$(".cd-timeline-block").eq(0).before(item);
					var index = layer.load(0,{time: 1200});
					var d = dialog({
					    content: "说说发表成功，+2积分",
					    quickClose: true// 点击空白处快速关闭
					});
					d.show();
					setTimeout(function() {
						d.close().remove();
					}, 1500);
					clearForm();
				}
			}
		});
	}
	function clearForm() {
		$("#shuoShuoArea").val("");
		$("#image-con").empty();
	}
	function loadShuoShuos(pageNum){
		known.pageNum = pageNum;
		$.ajax({
			url: '/shuoShuo/loadShuoShuos',
			type: 'POST',
			dataType: 'json',
			data: {"pageNum": pageNum},
			success:function(res){
				var list = res.data.list;
				var simplePage = res.data.page;
				for (var i = 0, _len = list.length, data; i < _len, data = list[i]; i++) {
				new ShuoShuoItem(data).appendTo($("#cd-timeline"));
				}
				if (simplePage.pageTotal > simplePage.pageNum) {
				$('<div id="load-more" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo($("#cd-timeline"));
				}
			}
		});
	}
	function ShuoShuoItem(data){
		var item = $('<div class="cd-timeline-block"></div>');
		$('<a href="../user/' + data.userId+ '"><img src="' + data.userIcon + '" class="cd-timeline-img img-circle"></a>').appendTo(item);
		var shuoshuocontent = $('<div class="cd-timeline-content"></div>').appendTo(item);
		$('<div class="main-content">' + data.showContent + '</div>').appendTo(shuoshuocontent);
		if (data.imageUrlSmall != null && data.imageUrlSmall != ""){
			var userthumbnail = $('<div class="user-thumbnail"></div>').appendTo(shuoshuocontent);
			var imageSmallArray = data.imageUrlSmall.split("|");
			for (var i = 0, _len = imageSmallArray.length, img; i < _len, img = imageSmallArray[i]; i++){
				if (img !== ""){
					// $('<img  layer-src="../upload/' + img.split("_s")[0] +'" src="../upload/' + img + '" alt="图片名">').appendTo(userthumbnail)
					$('<img src="'  + img + '" class="img-thumbnail" style="width: 150px;height: 100px;">').appendTo(userthumbnail);
				}
			}
		}
		var useroppanel = $('<div class="user-op-panel"></div>').appendTo(shuoshuocontent);
		var shuoshuoiteminfo = $('<p class="shuoshuo-item-info"></p>').appendTo(useroppanel);
		var userinfo = $('<a href="' + known.realpath + '/user/' + data.userId + '" class="user-info"><i class="glyphicon glyphicon-user"></i><span>' + data.userName + '</span></a>').appendTo(shuoshuoiteminfo);
		var likeinfo = $('<a shuoshuoid="' + data.id + '" href="javascript:;" class="like-info"><i class="glyphicon glyphicon-thumbs-up"></i>赞(<span>'+ data.likeCount +'</span>)</a>').appendTo(shuoshuoiteminfo);
		var commentinfo = $('<a href="javascript:;" class="comment-info"><i class="glyphicon glyphicon-pencil"></i>评论(<span>'+ data.commentCount +'</span>)</a>').appendTo(shuoshuoiteminfo);

		var userlikepanel = $('<div class="user-like-panel"></div>').appendTo(shuoshuocontent);
		var likinginfo = $('<a href="javascript:;" class="like-info"><i class="user-like glyphicon glyphicon-thumbs-up"></i></a>').appendTo(userlikepanel);

		if(data.likeCount > 0){
			var likes = data.shuoShuoLikeList;
			for (var i = 0, _len = likes.length, like; i < _len, like = likes[i]; i++) {
				new LikeItem(like).appendTo(userlikepanel);
			}
		}
		$('<span class="cd-date">' + data.createTime + '</span>').appendTo(shuoshuocontent);

		//此处需要加入commentItem到shuoshuocontent
		if(data.commentCount > 0){
			var comments = data.commentList;
			for (var i = 0, _len = comments.length, comment; i < _len, comment = comments[i]; i++) {
				new CommentItem(comment).appendTo(shuoshuocontent);
			}
		}		
		new CommentForm(data).appendTo(shuoshuocontent);
		return item;
	}
	function LikeItem(like){
		var likeItem = $('<a href="' + known.realpath + '/user/' + like.userId + '" class="like-info"><img class="user-like-info img-responsive" src="' +  like.userIcon + '"></a>');
		return likeItem;
	}
	function CommentItem(comment){
		var usercommentpanel = $('<div class="user-comment-panel"></div>');
		var commentitem = $('<div class="comment-item"></div>').appendTo(usercommentpanel);
		var commentusericon = $('<div class="comment-user-icon"></div>').appendTo(commentitem);
		$('<a href="' + known.realpath + '/user/' + comment.userId + '"><img src="' + comment.userIcon + '" class="user-icon-image img-circle"></a>').appendTo(commentusericon);
		var commentmain = $('<div class="comment-main"></a>').appendTo(commentitem);
		var commentcontent = $('<div class="comment-content"><a href="' + known.realpath + '/user/' + comment.userId + '">' + comment.userName + '</a>：' + comment.showContent +'</div>').appendTo(commentmain);
		var commentop = $('<div class="comment-op"><span>' + comment.createTime + '</span><a class="comment-at" username="' + comment.userName + '" href="javascript:;">回复</a></div>').appendTo(commentmain);
		return usercommentpanel;
	}
	function CommentForm(data){
		var commentform = $('<div class="comment-form"></div>');
		var commenttextarea = $('<textarea></textarea>').appendTo(commentform);
		var commentformop = $('<div class="comment-form-op"></div>').appendTo(commentform);
		var opat = $('<a href="javascript:;" title="@好友" class="op-at"><i class="icon icon-op-at"></i></a>').appendTo(commentformop);
		var opemotion = $('<a href="javascript:;" title="插入表情" class="op-emotion"><i class="icon icon-op-emotion"></i></a>').appendTo(commentformop);

		var opreply = $('<button shuoshuoid="' + data.id + '" type="button" class="btn btn-success reply">回复</button>').appendTo(commentformop);
		return commentform;
	}