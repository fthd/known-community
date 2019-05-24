var ue = UE.getEditor('editor');

$(document).ready(function() {
	$("#postExamBtn").click(function(event) {
		addAsk();
	});
});

function addAsk(){
	if(known.userId=="" || known.userId == 0){
		goLogin();
	    return;
	}
	var form = $("#postTopicForm");
	var title = form.find("input[name='title']").val();
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
	var myMark = parseInt($("#my-mark").text());
	if(myMark < mark){
		$("#needMark").addClass("has-error");
		layer.alert("您的积分只有：" + myMark, {
				  icon: 5,
				  skin: 'layer-ext-moon' 
				});
			 return ;
	}	  
	 $("#needMark").removeClass("has-error");
	 var d = dialog({
			content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;发表中...</div>",
		});
		d.showModal();
		setTimeout(function() {
			d.close().remove();
		}, 1000);
		$.ajax({
			url: known.realpath + '/ask/publicAsk',
			type: 'POST',
			dataType: 'json',
			data: $("#postTopicForm").serialize(),		
			success:function(res){
				if(res.code != "SUCCESS"){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				}
				else{
					layer.msg('发表成功', {
						  icon: 1,
						  time: 1500 //2秒关闭（如果不配置，默认是3秒）
						}, function(){
							document.location.href = known.realpath + "/ask/" + res.data ;
						});
				}
			}
		});
}