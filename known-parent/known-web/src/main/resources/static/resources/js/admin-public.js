$(document).ready(function() {
	$(document).on('click', '.mycollapse', function(event) {
		event.preventDefault();
		$(this).parent().find('.second-nav-level').collapse("toggle");
		var theSpan = $(this).find('.arrow');
		if(theSpan.text()=="-"){
			theSpan.text("+");
		}
		else if(theSpan.text()=="+"){
			theSpan.text("-");
		}
	});
});
$("#saveUserInfo").click(function(event) {
		if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
    	var d = dialog({
					content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;修改中...</div>",
				});
				d.showModal();
				setTimeout(function() {
					d.close().remove();
				}, 1000);	
	$.ajax({
		url: '/admin/updateUserInfo',
		type: 'POST',
		dataType: 'json',
		data: $("#updateUserForm").serialize(),

		success:function(res){
			if(res.msg != null){
					layer.msg(res.msg, {icon: 5,time:1500});
					return;
				}
			else{
				layer.msg('修改成功', {icon: 6,time:1000});

			}
		}
	});
	
});

	function checkForm(content, doc) {
		var d = dialog({
			content: content,
			align: 'right',
			quickClose: true // 点击空白处快速关闭
		});
		d.show(document.getElementById(doc));
	}

$("#updatePWD").click(function(event) {
	var passwordreg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
	var oldPWD = $("#oldPassword").val();
	var newPWD = $("#newPassword").val();
	var conNewPWD = $("#confirmNewPassword").val();

		if (oldPWD == null || $.trim(oldPWD) == '') {
			checkForm('旧密码不能是空串', 'oldPassword');
			$("#oldPassword").parent().parent().addClass('has-error');
			return;
		} 
		 else if (!passwordreg.test(oldPWD)) {
			checkForm('密码必须含有字母和数字且6到16个字符之间', 'oldPassword');
			$("#oldPassword").parent().parent().addClass('has-error');
			return;
		} 
		else if (newPWD == null || $.trim(newPWD) == '') {
			$("#oldPassword").parent().parent().removeClass('has-error');
			checkForm('新密码不能是空串', 'newPassword');
			$("#newPassword").parent().parent().addClass('has-error');
			return;
		} 
		 else if (!passwordreg.test(newPWD)) {
			checkForm('新密码必须含有字母和数字且6到16个字符之间', 'newPassword');
			$("#newPassword").parent().parent().addClass('has-error');
			return;
		}
		else if (newPWD != conNewPWD) {
			checkForm('两次输入密码必须一致', 'confirmNewPassword');
			$("#confirmNewPassword").parent().parent().addClass('has-error');
			return;
		}
		$("#oldPassword").parent().parent().removeClass('has-error');
		$("#newPassword").parent().parent().removeClass('has-error');
		$("#confirmNewPassword").parent().parent().removeClass('has-error');
		var d = dialog({
					content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;修改中...</div>",
				});
				d.showModal();
				setTimeout(function() {
					d.close().remove();
				}, 1000);	

		$.ajax({
			url: '/admin/modifyPassword',
			type: 'POST',
			dataType: 'json',
			data: {
				oldPassword: oldPWD,
				newPassword:newPWD
			},
			success:function(res){
				if(res.msg != null){
					layer.msg(res.msg, {icon: 5,time:1500});
					return;
				}
				else{
					layer.msg('修改成功', {icon: 6,time:1000});
				}
			}
		});
		

});