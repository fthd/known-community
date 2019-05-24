$(document).ready(function() {
	var url = known.curUrl;
    if (url.indexOf("redirect") != -1) {
	known.redirect = url.substring(url.indexOf("redirect") + 9, url.length);
    }
	$("ul.tag li").click(function(event) {
		$('ul.tag li').removeClass('active');
		$(this).addClass('active');

	});

	$("#dropdown").click(function(event) {
		if(known.userId!=""){
        	 showMessageList();
         }
	});
	$(document).click(function(event) {
		$("#dropdownmenu").slideUp("slow");
	});

	$('#reset').click(function(event) {
		$('#username').val('');
		$('#email').val('');
		$('#password').val('');
		$('#confirmPassword').val('');
	});

	function checkForm(content, doc) {
		var d = dialog({
			content: content,
			align: 'right',
			quickClose: true // 点击空白处快速关闭
		});
		d.show(document.getElementById(doc));
	}


// 对找回密码的邮箱进行验证
	$('#findpassword').click(function(event) {
		var email = $('#email').val();
		var emailreg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		if (email == null || $.trim(email) == '') {
			checkForm('邮箱不能为空', 'email');
			$("#email").parent().addClass('has-error');
		} else if (!emailreg.test(email)) {
			checkForm('请输入正确的邮箱', "email");
			$("#email").parent().addClass('has-error');
		} else {
			$("#email").parent().removeClass('has-error');
			var loadingindex = layer.load(0, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			$.ajax({
				url: known.realpath + '/user/sendCheckCode',
				type: 'POST',
				dataType: 'json',
				data: $('#findPassword').serialize(),
				success: function(data) {
					layer.close(loadingindex);
					if (data.msg == null) {
						var content = '<form id="modifyPassword"><div class="form-group"><input class="form-control" type="password" placeholder="请输入新密码" name="password" id="password"></input></div><div class="form-group"><input class="form-control" type="password" placeholder="请确认新密码" name="confirmPassword" id="confirmPassword"></input></div><div class="form-group"><input class="form-control" placeholder="验证码" name="checkcode" id="checkcode"></input></div></form>';
						var d = dialog({
							title: '修改密码',
							width: 300,
							content: content,
							okValue: '确定',
							ok: function() {
								var passwordreg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
								var password = $('#password').val();
								var confirmPassword = $('#confirmPassword').val();
								var checkcode = $("#checkcode").val();
								if (password == null || $.trim(password) == '') {
									checkForm('密码不能是空串', 'password');
									$("#password").parent().addClass('has-error');
									return false;
								} else if (password.indexOf(" ") > -1) {
									checkForm('密码不能含有空格', 'password');
									$("#password").parent().addClass('has-error');
									return false;
								} else if (password.length < 6) {
									checkForm('密码长度不能小于6个字符', 'password');
									$("#password").parent().addClass('has-error');
									return false;
								} else if (password.length > 16) {
									checkForm('密码长度不能大于16个字符', 'password');
									$("#password").parent().addClass('has-error');
									return false;
								} else if (!passwordreg.test(password)) {
									checkForm('密码必须含有字母和数字', 'password');
									$("#password").parent().addClass('has-error');
									return false;
								} else if (confirmPassword != password) {
									$("#password").parent().removeClass('has-error');
									checkForm('两次输入密码必须一致', 'confirmPassword');
									$("#confirmPassword").parent().addClass('has-error');
									return false;
								} else if (checkcode == null || $.trim(checkcode) == '') {
									$("#confirmPassword").parent().removeClass('has-error');
									checkForm("验证码不能为空串", "checkcode");
									$("#checkcode").parent().addClass('has-error');
									return false;
								} else {
									$.ajax({
										url: known.realpath+ '/user/findPassword.do',
										type: 'POST',
										dataType: 'json',
										data: {
											"email": email,
											"password": password,
											"checkcode": checkcode
										},
										success: function(data) {
											if (data.msg == null) {
												var d = dialog({
													content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;修改成功,跳转中...</div>",
												});
												d.showModal();
												setTimeout(function() {
													d.close().remove();
													document.location.href = known.realpath + "/user/login";
												}, 1000);
											} else {
												checkForm(data.msg, "checkcode");
											}
										}
									});
									return false;

								}
							},
							cancelValue: '取消',
							cancel: function() {}
						});
						d.show();

					} else {
						checkForm(data.msg, "email");
					}
				}
			});


		}
	});

//控制用户信息显示  加载用户签到信息
	$("#userheadicon").mouseenter(function(){
		$.ajax({
	 		url:  known.realpath + '/signIn/loadSignInfo',
	 		type: 'POST',
	 		dataType: 'json',
	 		success:function(result){
	 			$(".usermark").text(result.data.mark);
	 			$(".usertotalsignin em").text(result.data.userSignInCount);
	 			$(".signindate").html(result.data.curYear+"&nbsp;年&nbsp;" + result.data.curMonth + "月<em>" + result.data.curDay + "</em>日");
	 			if(result.data.haveSignInToday){	 					
	 					$("#signinimage").removeClass("unsigninimage");
	 					$("#signinimage").addClass("signinimage");
	 			} else{
 					$("#signinimage").removeClass("signinimage");
 					$("#signinimage").addClass("unsigninimage");
 			}
	 		}
	 	});
	  });
	$("#userheadicon").mouseover(function(){
	    $("#mypanel").show();   
	  });
	 $("#userheadicon").mouseleave(function(event) {
	 	 known.showdialog_timeout = setTimeout(function(){
			$("#mypanel").hide();
	    },100);	 	
	 });
	 $("#mypanel").mouseenter(function(){
	 	clearTimeout(known.showdialog_timeout);
	    $("#mypanel").show();   
	  });
	  $("#mypanel").mouseleave(function(){
	    $("#mypanel").hide();   
	  });
	 
	//进行签到
	 $(".unsigninimage").click(function(event) {
			$.ajax({
				url: known.realpath + '/signIn/signIn',
                type: 'POST',
                dataType: 'json',
				success:function(result){
					if(result.code == "SUCCESS"){
                        var d = dialog({
						width: $(window).width(),
						height: $(window).height(),
						content: "<div><img src='" + known.realpath +"/resources/images/signin.gif' /></div>"
						});
						d.showModal();
						setTimeout(function() {
							d.close().remove();
						}, 1200);
					} else{
						var d = dialog({
								width: 200,
								content: result.msg,
								quickClose: true // 点击空白处快速关闭
						});
						d.show();
					}
				}
			});
		});
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
		var item = $("<div data=" + emotions[i] + " class='emotion' title=" + emotions[i] + "><img src='${realpath}/resources/images/emotions/" + i + ".gif'></div>").appendTo(emotion_panel).bind("click", function() {
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

//用户没有登录，都需要跳转到这里登录
function goLogin() {
	var url = known.curUrl;
	url = encodeURI(url);
	document.location.href = known.realpath + "/user/login?redirect=" + url;
}