known.searchParam = "";
known.url = {
	loadMessage: known.realpath + "/userAdmin/load_user_message_list.action",
	delMessage: known.realpath + "/userAdmin/del_message.action",
	markMessage: known.realpath + "/userAdmin/mark_message_read.action"
}
$(function() {
	demo(1);
	$(document).on("click", ".del-btn", function() {
		del("ids=" + $(this).attr("messageId"));
	});
	$(document).on("click", ".mark-btn", function() {
		mark("ids=" + $(this).attr("messageId"));
	});

	$("#check-all").click(function() {
		if ($(this).is(':checked')) {
			$("#data-list input[name='message']").prop("checked", true);
		} else {
			$("#data-list input[name='message']").prop("checked", false);
		}
	});

	$("#mark-batch-btn").click(function() {
		var checkedMessages = $("#data-list input[name='message']:checked");
		if (checkedMessages.length == 0) {
			layer.msg("请选择要标记的消息", {
						icon: 5,
						time: 1500 //2秒关闭（如果不配置，默认是3秒）
					});
			return;
		}
		var messsageIds = "";
		for (var i = 0, _len = checkedMessages.length; i < _len; i++) {
			var d = d = checkedMessages.eq(i);
			if (i == _len - 1) {
				messsageIds = messsageIds + "ids=" + d.val()
			} else {
				messsageIds = messsageIds + "ids=" + d.val() + "&";
			}
		}
		mark(messsageIds);
	});
	$("#del-batch-btn").click(function() {
		var checkedMessages = $("#data-list input[name='message']:checked");
		if (checkedMessages.length == 0) {
			layer.msg("请选择要标记的消息", {
						icon: 5,
						time: 1500 //2秒关闭（如果不配置，默认是3秒）
					});
			return;
		}
		var messsageIds = "";
		for (var i = 0, _len = checkedMessages.length; i < _len; i++) {
			var d = d = checkedMessages.eq(i);
			if (i == _len - 1) {
				messsageIds = messsageIds + "ids=" + d.val()
			} else {
				messsageIds = messsageIds + "ids=" + d.val() + "&";
			}
		}
		del(messsageIds);
	});
})

function del(messageId) {

	layer.confirm('确认要删除吗？', {
		btn: ['是', '否'], //按钮
		shade: false //不显示遮罩
	}, function() {
	var d = dialog({
		content: "<div><img src='" + known.realpath + "/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;删除中...</div>",
	});
	d.showModal();
	setTimeout(function() {
		d.close().remove();
	}, 1000);
		$.ajax({
			url: known.url.delMessage,
			type: 'POST',
			dataType: 'json',
			data: messageId,
			success: function(res) {
				if (res.msg != null) {
					layer.msg(res.msg, {
						icon: 5,
						time: 1500 //2秒关闭（如果不配置，默认是3秒）
					});
				} else {
					layer.msg('删除成功', {
						icon: 1,
						time: 1000 //2秒关闭（如果不配置，默认是3秒）
					}, function() {
						demo(known.curr);
					});
				}
			}
		});


	}, function() {

	});

}


function mark(messageId) {
	layer.confirm('确认要标记为已读吗？', {
		btn: ['是', '否'], //按钮
		shade: false //不显示遮罩
	}, function() {
	var d = dialog({
		content: "<div><img src='" + known.realpath + "/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;标记中...</div>",
	});
	d.showModal();
	setTimeout(function() {
		d.close().remove();
	}, 1000);
		$.ajax({
			url: known.url.markMessage,
			type: 'POST',
			dataType: 'json',
			data: messageId,
			success: function(res) {
				if (res.msg != null) {
					layer.msg(res.msg, {
						icon: 5,
						time: 1500 //2秒关闭（如果不配置，默认是3秒）
					});
				} else {
					layer.msg('标记成功', {
						icon: 1,
						time: 1000 //2秒关闭（如果不配置，默认是3秒）
					}, function() {
						demo(known.curr);
					});
				}
			}
		});

	}, function() {

	});
}

function search() {
	known.searchParam = $("#searchform").serialize();
	known.startDate = $("#startDate").val();
	known.endDate = $("#endDate").val();
	demo(1);
}


function demo(curr) {
	known.curr = curr;
	$.getJSON(known.url.loadMessage, {
		pageNum: curr || 1, //向服务端传的参数，此处只是演示
		startDate: known.startDate,
		endDate: known.endDate
	}, function(response) {
		//此处仅仅是为了演示变化的内容
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
			for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
				var edit = "";
				if (d.url != "") {
					edit = edit + '<a href="' + known.realpath + '/userAdmin/readMessage.action?id=' + d.id + '" title="查看" target="_blank" class="read-btn" url="' +known.realpath+ d.url + '"><i class="icon i-preview"></i>&nbsp;&nbsp;&nbsp;&nbsp;'
				}
				edit = edit + '<a href="javascript:;" title="标记为已读" class="mark-btn" messageId="' + d.id + '"><i class="icon i-start"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
				edit = edit + '<a href="javascript:;" title="删除" class="del-btn"  messageId="' + d.id + '"><i class="icon i-del"></i></a>';
				$("<tr><td><input type='checkbox' class='not-input' name='message' value='" + d.id + "'></td><td valign='center'>" + (d.status.type == "0" ? "未读" : "已读") + "</td><td valign='center'>" + d.description + "</td><td>" + d.createTimeString + "</td><td>" + edit + "</td></tr>").appendTo($("#data-list"));
			}
		} else {
			$('<tr><td colspan="100"><div class="no-data" >没有数据</div></td></tr>').appendTo($("#data-list"));
		}
		//显示分页
		laypage({
			cont: 'pager', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
			skin: 'molv', //皮肤
			pages: response.data.page.pageTotal, //通过后台拿到的总页数
			curr: curr || 1, //当前页
			jump: function(obj, first) { //触发分页后的回调
				if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
					demo(obj.curr);
				}
			}
		});
	});
};