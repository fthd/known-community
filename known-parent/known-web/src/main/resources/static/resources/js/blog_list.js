known.searchParam = "";
known.url = {
	loadBlog : known.realpath + "/admin/loadBlog",
	delBlog: known.realpath + "/admin/del_blog.action"
}
$(function(){
    demo(1);
    $(document).on("click",".del-btn",function(){
    	var blogId = $(this).attr("blogId");
    	layer.confirm('确定删除？', {
			btn: ['是','否'], //按钮
			shade: false //不显示遮罩
			}, function(){
				deleteBlog(blogId);
			}, function(){

			});
    });
})

function deleteBlog(blogId){
	var d = dialog({
		content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;删除中...</div>",
				});
		d.showModal();
		setTimeout(function() {
		d.close().remove();
	}, 1000);
	$.ajax({
		url:known.url.delBlog,
		type: 'POST',
		dataType: 'json',
		data: {blogId: blogId},
		success:function(res){
			if(res.msg != null){
				layer.msg(res.msg, {
				  icon: 5,
				   time: 1500 //2秒关闭（如果不配置，默认是3秒）
				});
			}
			else{
				layer.msg('删除成功', {
					  icon: 1,
					  time: 1500 //2秒关闭（如果不配置，默认是3秒）
					}, function(){
						demo(known.curr);
					});
			}
		}
	});
	
}

function search(){
    known.title = $("#title").val();
    known.startDate = $("#startDate").val();
    known.endDate = $("#endDate").val();
    demo(1);
}


function demo(curr){
	known.curr = curr;
    $.getJSON(known.url.loadBlog, {
        pageNum: curr || 1 ,//向服务端传的参数，此处只是演示
        title : known.title, 
        startDate : known.startDate, 
        endDate : known.endDate    	    
    }, function(response){
        //此处仅仅是为了演示变化的内容
    	var data = response.data;
		var simplePage = data.page;
		var list = data.list;
		$("#data-list tr").remove();
		if (list.length > 0) {
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			var edit = '<a href="'+known.realpath+'/admin/edit_blog.action?blogId='+d.blogId+'" title="修改" class="edit-btn" blodId="'+d.blogId+'"><i class="icon i-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" title="删除" class="del-btn"  blogId="'+d.blogId+'"><i class="icon i-del"></i></a>';
			$("<tr><td valign='center'><a href='"+known.realpath+"/user/"+known.userId+"/blog/"+d.blogId+"' target='_blank'>"+d.title+"</a></td><td>"+d.commentCount+"</td><td>"+d.readCount+"</td><td>"+d.createTimeString+"</td><td>" + edit + "</td></tr>").appendTo($("#data-list"));
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
            jump: function(obj, first){ //触发分页后的回调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                    demo(obj.curr);
                }
            }
        });
    });
};