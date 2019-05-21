var ue = UE.getEditor('editor');
known.autoSveTime = 3*60*1000;
/**
上传文件begin
 * */

var uploader = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto : true,

    // swf文件路径
    swf : known.realpath + '/resources/webuploader/Uploader.swf',

    // 文件接收服务端。
    server : known.realpath + '/fileUpload.action',

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
    if (response.responseCode == "SUCCESS") {
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
		url: known.realpath + '/fileDelete.action',
		type: 'POST',
		dataType: 'json',
		data: { "fileName" : $("#attached_file").val()},
		success:function(res){
			if(res.responseCode != "SUCCESS"){
				layer.alert(res.msg, {
				  icon: 5,
				  skin: 'layer-ext-moon' 
				});
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
	$.ajax({
		url: known.realpath + "/admin/deleteBlogAttachment",
		type: 'POST',
		dataType: 'json',
		data: {attachmentId: $("#attachmentId").val()},
		success:function(res){
			if(res.msg != null){
				layer.msg(res.msg, {
				  icon: 5,
				   time: 1500 //2秒关闭（如果不配置，默认是3秒）
				});
			}
			else{
				location.reload();
			}
		}
	});
	
};


//上传文件end

$(document).ready(function() {
	$("#postExamBtn").click(function(event) {
		addBlog();
	});
	setInterval(function(){
	      	$("#richContent").val(ue.getContent());
	      	$.ajax({
	      		url: known.realpath + '/admin/addDraftBlog',
				type: 'POST',
				dataType: 'json',
				data: $("#postBbsForm").serialize(),		
				success:function(res){
					layer.msg('保存草稿成功', {
						  icon: 1,
						  time: 1500 //2秒关闭（如果不配置，默认是3秒）
						});		
				}
	      	});
	},known.autoSveTime);
});

function addBlog(){
	if(known.userId=="" || known.userId == 0){
		goLogin();
	    return;
	}
	var form = $("#postBbsForm");
	var title = form.find("input[name='title']").val();
	var categoryId = $("#categoryId").val();
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
	 if(categoryId=="0"){
	 	layer.confirm('是否不选择分类发表？', {
			btn: ['是','否'], //按钮
			shade: false //不显示遮罩
			}, function(){
				publicBlog();
			}, function(){

			});
	 }
	 else{
	 	publicBlog();
	 	}
	 
}

function publicBlog(){
		var d = dialog({
			content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;发布中...</div>",
					});
					d.showModal();
					setTimeout(function() {
						d.close().remove();
					}, 1000);
					$.ajax({
						url: known.realpath + '/admin/addBlog',
						type: 'POST',
						dataType: 'json',
						data: $("#postBbsForm").serialize(),		
						success:function(res){
							if(res.msg != null){
								layer.msg(res.msg, {
								  icon: 5,
								   time: 1500 //2秒关闭（如果不配置，默认是3秒）
								});
							}
							else{
								layer.msg('发布成功,跳转到详情页', {
									  icon: 1,
									  time: 1500 //2秒关闭（如果不配置，默认是3秒）
									}, function(){
										document.location.href = known.realpath + "/user/" + known.userId + "/blog/" + res.data ;
									});
							}
						}
					});		
}