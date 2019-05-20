known.contentMaxLength = 500;
$(function(){
	
	
	known.emotion_data = ["[围观]", "[威武]", "[给力]", "[浮云]", "[奥特曼]", "[兔子]", "[熊猫]", "[飞机]", "[冰棍]", "[干杯]", "[给跪了]", "[囧]", "[风扇]", "[呵呵]", "[嘻嘻]", "[哈哈]", "[爱你]", "[晕]", "[泪]", "[馋嘴]", "[抓狂]", "[哼]", "[抱抱]", "[可爱]", "[怒]", "[汗]", "[困]", "[害羞]", "[睡觉]", "[钱]", "[偷笑]", "[酷]", "[衰]", "[吃惊]", "[闭嘴]", "[鄙视]", "[挖鼻屎]", "[花心]", "[鼓掌]", "[失望]", "[思考]", "[生病]", "[亲亲]", "[怒骂]", "[太开心]", "[懒得理你]", "[右哼哼]", "[左哼哼]", "[嘘]", "[委屈]", "[吐]", "[可怜]", "[打哈气]", "[黑线]", "[顶]", "[疑问]", "[握手]", "[耶]", "[好]", "[弱]", "[不要]", "[没问题]", "[赞]", "[来]", "[蛋糕]", "[心]", "[伤心]", "[钟]", "[猪头]", "[话筒]", "[月亮]", "[下雨]", "[太阳]", "[蜡烛]", "[礼花]", "[玫瑰]"];
	known.ueditor = UE.getEditor('comment-content', {
	    initialFrameWidth : 1055, initialFrameHeight :180
	});
	known.commentPanel = $("#comment-list");

	//查看消息屏幕滚动到指定位置
    var url = window.location.href;
    //获取url后面#的信息 1_120  1是页码  120是评论ID
    var comment4messageIndex = url.lastIndexOf("#");
    var comment4PageNo = 1;
    if (comment4messageIndex != -1) {
	var comment4message = url.substring(url.lastIndexOf("#") + 1);
	var messageInfo = comment4message.split("_");
	if(messageInfo.length>0){
	    comment4PageNo = messageInfo[0];
	    known.message4CommentId = messageInfo[1];
	}
    } 
    
    demo(comment4PageNo,known.message4CommentId);

    // 发表一级回复
    $("#post-p-comment-btn").click(function() {
    	if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
    	postPComment();
    });
    //发表二级回复
    $(document).on("click", ".reply", function() {
    	if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
    	postComment($(this));
        });
    //初始化评论数
    var comment_count = parseInt($("#comment-count").text());
    $("#comment-title-count").text(comment_count);
    
    initOperation();
});

//初始化操作框
function initOperation(){
	var rightNav = $('<div class="myRightNav"></div>').appendTo($(".main").eq(0));
	var operationDialog = $('<div class="navpanel" id="navpanel"><ul id="packUp"><li class="comment"><a>评论</a></li><li class="like"><a>喜欢</a></li><li class="collection"><a>收藏</a></li><li class="gotop"><a>顶部</a></li><li class="pack"><a>收起</a></li></ul><ul id="display" style="display: none;"><li class="display"><a>弹出</a></li></ul></div>').appendTo(rightNav);
}
//分页
function demo(curr,message4CommentId){
    $.getJSON(known.realpath + '/loadComment', {
        pageNum: curr || 1 ,//向服务端传的参数，此处只是演示
        articleId : known.topicId, 
	    articleType : known.articleType
    }, function(res){
        //此处仅仅是为了演示变化的内容
    	known.commentPageNum = res.data.page.pageNum
    	$("#comment-list").empty();
	    var list = res.data.list;
	    if (list.length == 0) {
		$("<div class='no-data'>没有评论，赶紧抢沙发</div>").appendTo($("#comment-list"));
	    }
	    var simplePage = res.data.page;
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			if(d.id==known.bestAnswerId){
			    continue;
			}
		new CommentItem(d).appendTo($("#comment-list"));
	    }
        //显示分页
        laypage({
            cont: 'page', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            skin: 'molv', //皮肤
            pages: res.data.page.pageTotal, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            jump: function(obj, first){ //触发分页后的回调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                    demo(obj.curr);
                }
            }
        });

        if(message4CommentId!=null&&message4CommentId>0){
			$('html,body').animate({scrollTop:$('#comment'+message4CommentId).offset().top}, 400);
	    }
    });
};

$(document).on("click", ".op-emotion", function() {
	showEmotion($(this)[0], $(this).parent().parent().find("textarea"));
	});

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
$(document).on("click", ".op-at", function() {
	showAtUser($(this)[0], $(this).parent().parent().find("textarea"));
});
function showAtUser(targetObj, textarea){
	var d = dialog({
		width:360,
	    align: 'bottom left',
	    quickClose: true// 点击空白处快速关闭
	});
	var at_panel = $("<div></div>");
	$.ajax({
		url: known.realpath+ '/loadUserFriend',
		type: 'POST',
		dataType: 'json',
		data: {"pageNum": 1},
		success:function(res){
			if(res.errorMsg != null){
				layer.alert(res.errorMsg, {
				  icon: 5,
				  skin: 'layer-ext-moon' 
				});
				return;
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
					$("<a href='javascript:;' class='at_user'><img src='" +  item.friendUserIcon + "' class='img-thumbnail' style='width:40px;height:40px;'><span>" + item.friendUserName + "</span></a>").appendTo(content).click(function() {
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

$(document).on("click", ".comment-btn", function() {
	$(".comment-form").hide();
	var pid = $(this).attr("pid");
	var comment_id = $(this).attr("comment-id")
	var user_name = $("#user-name-" + comment_id).text();
	var comment_form = $("#comment-form-" + pid).show();
	var textarea = comment_form.find("textarea");
	if (pid != comment_id) {
	    textarea.val(textarea.val()).focus().val(textarea.val() + "@" + user_name + " ");
	} else {
	    textarea.val("").focus();
	}
    });




function CommentItem(data) {
    var item = $("<div class='comment-item'></div>").appendTo(known.commentPanel);
    new CommentCon(data, data.id,true).appendTo(item);
    var children = data.children;
    // 二级回复
    var item_child_panel = $("<div class='child-panel'></div>").appendTo(item);
    for (var i = 0, _len = children.length; i < _len; i++) {
	new CommentCon(children[i], data.id,false).appendTo(item_child_panel);
    }
    // 回复表单
    var comment_form = $("<div class='comment-form' id='comment-form-" + data.id + "'></div>").appendTo(item);
    $("<div class='subcomment-form-textarea'><textarea></textarea></div>").appendTo(comment_form);
    var op = $("<div class='comment-form-op'></div>").appendTo(comment_form);
    $("<a href='javascript:;' title='@好友' class='op-at'><i class='icon icon-op-at'></i></a>").appendTo(op);
    $("<a href='javascript:;' title='插入表情' class='op-emotion'><i class='icon icon-op-emotion'></i></a>").appendTo(op);
    $("<a href='javascript:;' class='btn btn-info reply' pid='" + data.id + "'>回复</a>").appendTo(op);
    return item;
}

function CommentCon(data, pid,pComment) {
    var comment_panel = $("<div class='comment-panel' id='comment"+data.id+"'></div>");
    // 头像
    $("<div class='user-icon'><a href='" + known.realpath + "/user/" + data.userId + "'><img   src='"  + data.userIcon + "'></a></div>").appendTo(comment_panel);
    var comment_con = $("<div class='comment-con'></div>").appendTo(comment_panel);
    $("<div class='clear'></div>").appendTo(comment_panel);
    // 内容
    $("<div class='content_detail'><a id='user-name-" + data.id + "' href='" + known.realpath + "/user/" + data.userId + "'>" + data.userName + "</a>：<span>" + data.showContent + "</span></div>")
	    .appendTo(comment_con);
    // 时间，回复
   var op = $("<div class='time-op'><span class='time'>" + data.createTime + "</span><a href='javascript:;'  pid='" + pid + "' comment-id = '" + data.id + "' class='comment-btn'><i class='icon icon-re'></i>回复</a></div>").appendTo(
	    comment_con);
   if(pComment&&known.bestAnswerId==""&&known.userId==known.articleUserId){
       $("<a href='javascript:;' class='btn btn-info accept-btn' commentid="+pid+">采纳答案</a>").appendTo(op);
   }
    return comment_panel;
}
//发表一级回复
function postPComment() {

    if (known.ueditor == null && $.trim($("#comment-content").val()) == "" || known.ueditor != null && !known.ueditor.hasContents()) {
	layer.alert("回复内容不能为空", {
		  icon: 5,
		  skin: 'layer-ext-moon' 
		});
		return;
    }
    var content = known.ueditor == null ? $.trim($("#comment-content").val()) : known.ueditor.getContent();
    console.log(content);
    $.ajax({
	url : known.realpath + "/addComment",
	type: 'POST',
	dataType: 'json',
	data : {
	    content : content,
	    articleId : known.topicId, 
	    articleType : known.articleType, 
	    pid : 0,
	    pageNum:known.commentPageNum
	},
	success : function(res) {
		if(res.errorMsg != null){
			layer.alert(res.errorMsg, {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
			return;
		}
	    var data = res.data;
	    known.ueditor == null ? $("#comment-content").val("") : known.ueditor.setContent("");
	    known.commentPanel.find(".no-data").remove();
	    var comment_items = known.commentPanel.find(".comment-item");
	    var commentCon  = new CommentItem(data);
	    if (comment_items.length == 0) {
		commentCon.appendTo(known.commentPanel);
	    } else {
		comment_items.eq(0).before(commentCon);
	    }
	    
	    var comment_count = parseInt($("#comment-count").text());
	    $("#comment-title-count").text(comment_count+1);
	    $("#comment-count").text(comment_count+1);
	   
	}
    });
}
//发表二级回复
function postComment(curObj) {
    var pid = curObj.attr("pid");
    var comment_form = $("#comment-form-" + pid);
    var textarea = comment_form.find("textarea");
    var content = $.trim(textarea.val());
    if (content == "" || content.length > known.contentMaxLength) {
    	layer.alert("回复内容必须大于1个字小于1000字", {
  		  icon: 5,
  		  skin: 'layer-ext-moon' 
  		});
  		return;
    }

   $.ajax({
	url : known.realpath + "/addComment2",
	type: 'POST',
	dataType: 'json',
	data : {
		content : content,
	    articleId : known.topicId, 
	    articleType : known.articleType, 
	    pid : pid,
	    pageNum:known.commentPageNum
	}, 
	success : function(res) {
		if(res.errorMsg != null){
			layer.alert(res.errorMsg, {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
			return;
		}
	    textarea.val("");
	    var data = res.data;
	    var child_panel = comment_form.parent().find(".child-panel").eq(0);
	    var commentCon = new CommentCon(data, pid,false).appendTo(child_panel);	    
	    var comment_count = parseInt($("#comment-count").text());
	    $("#comment-title-count").text(comment_count+1);
	    $("#comment-count").text(comment_count+1);	    
	}
    });
}