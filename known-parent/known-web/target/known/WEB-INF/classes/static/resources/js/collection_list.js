known.url = {
	loadCollection: known.realpath+"/userAdmin/load_collection.action",
	delCollection: known.realpath+"/userAdmin/del_collection.action"
}
$(function(){
    demo(1);
    $(document).on("click",".del",function(){
	var articleId = $(this).attr("articleId");
	var articleType = $(this).attr("articleType");

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
			url : known.url.delCollection,
			type: 'POST',
			dataType: 'json',
		 	data : {
				articleId:articleId,
				articleType:articleType
			    },
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
    });
})

function search(){
	known.articleType = $("#articleType").val();
   known.startDate = $("#startDate").val();
   known.endDate = $("#endDate").val();
   known.title = $("#title").val();
	demo(1);
}



    function demo(curr) {
	known.curr = curr;
	$.getJSON(known.url.loadCollection, {
		pageNum: curr || 1, //向服务端传的参数，此处只是演示
		startDate: known.startDate,
		endDate: known.endDate,
		articleType:$("#articleType").val(),
		title:known.title
	}, function(response) {
		//此处仅仅是为了演示变化的内容
		var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var edit = '<a href="javascript:;" title="删除" class="del" articleId="'+d.articleId+'" articleType="'+d.articleType.type+'"><i class="icon i-del"></i></a>';
			var url = "";
			if(d.articleType.type=="T"){
			    url = known.realpath+"/topic/"+d.articleId;
			}else if(d.articleType.type=="K"){
			    url = known.realpath+"/knowledge/"+d.articleId;
			}else if(d.articleType.type=="A"){
			    url = known.realpath+"/ask/"+d.articleId;
			}
			$("<tr><td valign='center'><a href='"+url+"' target='_blank'>"+d.title+"</a></td><td>"+d.createTimeString+"</td><td>" + edit + "</td></tr>").appendTo($("#data-list"));
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