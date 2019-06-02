var ue = UE.getEditor('editor');
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
    fileSingleSizeLimit : 2 * 1024 * 1024
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
	url : known.realpath + "/knowledge/loadCategories" ,
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

$(document).ready(function() {
	initCategory();

	$(document).on("change", "#pCategoryId", function() {
		selectPCategory();
	    });
	$("#postExamBtn").click(function(event) {
		addKnowledge();
	});
});

function addKnowledge(){
	if(known.userId=="" || known.userId == 0){
		goLogin();
	    return;
	}
	var form = $("#postTopicForm");
	var title = form.find("input[name='title']").val();
	var categoryId = $("#categoryId").val();
	var categoryName = $('#categoryId option:selected').text();
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
		layer.alert("请选择二级分类", {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
		return ;
	}
	$("#category").removeClass("has-error");
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
			url: known.realpath + '/knowledge/publicKnowledge',
			type: 'POST',
			dataType: 'json',
			data: $("#postTopicForm").serialize(),		
			success:function(res){
				if(res.code != "SUCCESS"){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					})
				} else {
					layer.msg('投稿成功,等待审核', {
						  icon: 1,
						  time: 1500 //2秒关闭（如果不配置，默认是3秒）
						}, function(){
							document.location.href = known.realpath + "/knowledge/knowledge";
						});
				}
			}
		});
}