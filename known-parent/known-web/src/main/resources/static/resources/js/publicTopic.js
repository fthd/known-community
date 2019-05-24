var ue = UE.getEditor('editor');
known.topicType = {
    topicType0 : 0,// 普通话题
    topicType1 : 1
// 投票话题
}
/**
上传文件begin
 * */

var uploader = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto : true,

    // swf文件路径
    swf : known.realpath + '/resources/webuploader/Uploader.swf',

    // 文件接收服务端。
    server : known.realpath + '/fileUploader/fileUpload.action',

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick : {
	id : '#filePicker', multiple : false
    },
    // 只允许选择excel。
    duplicate : 1, // 不去重
    compress : false, // 压缩
    fileSingleSizeLimit : 10 * 1024 * 1024
});
uploader.on('fileQueued', function(file) {
	if(known.userId=="" || known.userId == 0){
		goLogin();
	    return;
	}
    var name = file.name;
    var type = name.substring(name.lastIndexOf(".") + 1);
    if (type != "rar" && type != "zip") {
	uploader.removeFile(file);
	layer.alert("文件只能是.rar或者.zip", {
		  icon: 5,
		  skin: 'layer-ext-moon' 
		});
	
	return;
    }
    $("#filePicker").hide();
    $("#fileuploadtip").hide();
    $("#loading-upload").show();
});

uploader.on('uploadSuccess', function(file, response) {
    if (response.code == "SUCCESS") {
	$("<div>" + file.name + "&nbsp;&nbsp;<a class='btn btn-info' href='javascript:deleteFile()'>删除</a></div>").appendTo($("#file-list"));
	$("#attached_file_name").val(file.name);
	$("#attached_file").val(response.savePath);
    }
    $("#loading-upload").hide();
});

uploader.on('error', function(handler) {
    if (handler == "F_EXCEED_SIZE") {
	layer.alert("文件不能超过2M", {
		  icon: 5,
		  skin: 'layer-ext-moon' 
		});
    }
});

deleteFile = function() {
	$.ajax({
		url: known.realpath + '/fileUploader/fileDelete.action',
		type: 'POST',
		dataType: 'json',
		data: { "fileName" : $("#attached_file").val()},
		success:function(res){
			if(res.code != "SUCCESS"){
				layer.alert(res.msg, {
				  icon: 5,
				  skin: 'layer-ext-moon' 
				})
			}
			else{
				$("#filePicker").show();
				$("#fileuploadtip").show();
				$("#loading-upload").hide();
				$("#attached_file_name").val("");
				$("#attached_file").val("");
				$("#file-list").empty();
			}
		}
	});
};


//上传文件end
$(function(){

	    $(".topicType").click(function() {
		topicTypeChoose($(this));
	    });
	    
	    $("#addVoteChoose").click(function(){
	    	var index = $("#voteChooseList").find("div.row").length;
	    	if (index >= 10) {
	    		layer.alert("最多只能有十个选项", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
	    	    return;
	    	}
	    	new voteChooseItem().appendTo($("#voteChooseList"));
	    });
	    
	    $(document).on("click", ".delete-vote", function() {
	    	$(this).parent().remove();
	        });
	    
	    $("#postExamBtn").click(function(){
	    	
	    	addTopic();
	    });
	    
	    initCategory();
	    
	    $(document).on("change", "#pCategoryId", function() {
	    	selectPCategory();
	        });
});

function topicTypeChoose(curObj) {
    var topicType = curObj.val();
    if (known.topicType.topicType0 == topicType) {
    	$("#votePanel").hide();
    } 
    else if (known.topicType.topicType1 == topicType) {
    	$("#votePanel").show();
    }
}

function voteChooseItem(){
	var item= $('<div class="row"><div class="col-xs-9"><input name="voteContent" type="text" class="form-control answer" placeholder="输入选项内容"></div><div class="col-xs-2 delete-vote"><a href="javascript:;"class="icon icon-del"></a></div></div>');
	return item;
}

/**
 * 添加话题
 */
function addTopic(){
	if(known.userId=="" || known.userId == 0){
		goLogin();
	    return;
	}
	var form = $("#postTopicForm");
	var title = form.find("input[name='title']").val();
	var categoryId = $("#categoryId").val();
	var topicType = $.trim(form.find("input[name='topicType']:checked").val());
	var endDateString = $.trim($("#endDateString").val());
	var mark = $("#mark").val();
	if(title == null || $.trim(title) == ''){
		$("#topicTitle").addClass("has-error");
		layer.alert("标题不能为空", {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
		return ;
	}
	$("#topicTitle").removeClass("has-error");
	if(categoryId == null || $.trim(categoryId) == ''){
		$("#category").addClass("has-error");
		layer.alert("请选择子版块", {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
		return ;
	}
	$("#category").removeClass("has-error");
	//如果是投票话题

	if(known.topicType.topicType1 == topicType){
		var voteChooses = $("input[name='voteContent']");
		for(var i = 0, _len = voteChooses.length; i < _len; i ++){
			$(voteChooses[i]).parent().parent().removeClass("has-error");
			if($.trim($(voteChooses[i]).val()) == ''){
				$(voteChooses[i]).parent().parent().addClass("has-error");
				layer.alert("选项"  + (i+1) + "内容不能为空", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				return ;
			}
		}
		
		if(endDateString == ''){
			$("#enddate").addClass("has-error");
			layer.alert("请选择投票截止日期", {
				  icon: 5,
				  skin: 'layer-ext-moon' 
				});
			return ;
		}
		$("#enddate").removeClass("has-error");
	}
	
	if (!ue.hasContents()) {
		$("#ueContent").addClass("has-error");
			layer.alert("内容不能为空", {
				  icon: 5,
				  skin: 'layer-ext-moon' 
				});
			return ;
	    }
	else{
		$("#richContent").val(ue.getContent());
		$("#ueContent").removeClass("has-error");
	}
	var numberReg = /^\d+$/;
	 if (!numberReg.test(mark)) {
		 $("#needMark").addClass("has-error");
			 layer.alert("积分只能是数字", {
				  icon: 5,
				  skin: 'layer-ext-moon' 
				});
			 return ;
		  }
	 $("#needMark").removeClass("has-error");
	 var d = dialog({
			content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;发布中...</div>",
		});
		d.showModal();
		setTimeout(function() {
			d.close().remove();
		}, 1000);
		$.ajax({
			url: known.realpath + '/topic/publicTopic',
			type: 'POST',
			dataType: 'json',
			data: $("#postTopicForm").serialize(),		
			success:function(res){
				if(res.code != "SUCCESS"){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					})
				}
				else{
					layer.msg('发布成功,跳转到详情页', {
						  icon: 1,
						  time: 1500 //2秒关闭（如果不配置，默认是3秒）
						}, function(){
							document.location.href = known.realpath + "/topic/" + res.data ;
						});
				}
			}
		});
}


/**
*加载分类
*/


function selectPCategory(subId){
	var pCategoryId = $("#pCategoryId").val();
var list = known.categories;
var children = [];
for (var i = 0, _len = list.length; i < _len; i++) {
    if (list[i].categoryId == pCategoryId) {
	children = list[i].children;
	break;
    }
}
$("#categoryId").empty();
$("<option value=''>请选择子板块</option>").appendTo($("#categoryId"));
for (var i = 0, _len = children.length; i < _len; i++) {
    var item = children[i];
    if(item.categoryId==subId){
	 $("<option selected='selected' value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#categoryId"));
    }else{
	 $("<option value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#categoryId"));
    }
   
}
}

function initCategory() {
$.ajax({
	url : known.realpath + "/topic/loadCategories" ,
	type:"POST",
	dataType:"json",
	success : function(res) {
    var list = res.data;
    known.categories = list;
    for (var i = 0, _len = list.length; i < _len; i++) {
	var item = list[i];
	if(item.categoryId==pCategoryId){
	    $("<option selected='selected' value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#pCategoryId")); 
	}else{
	    $("<option value=" + item.categoryId + ">" + item.name + "</option>").appendTo($("#pCategoryId")); 
	}
    }
    //让二级分类选中
    if($("#pCategoryId").val()!=''){
	selectPCategory(categoryId);
    }
}
});
}