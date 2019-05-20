$(function(){
	searchData(1);
});
known.countTotal = 0;
function searchData(curr){
	var articleType = $("#select").val();
	var keyword = $.trim($("#keyWord").val());
	if(articleType==""||keyword==""){
		return;
	}
	$(".seartch-tit").eq(0).text($("#select").find("option:selected").text());
	known.curr = curr;
    $.getJSON(known.realpath+"/searchArticle", {
        pageNum: curr || 1 ,//向服务端传的参数，此处只是演示
        articleType:articleType,
        keyword:keyword,
        countTotal:known.countTotal    	    
    }, function(response){
        //此处仅仅是为了演示变化的内容
    		var data = response.data;
    		var simplePage = data.page;
    		known.countTotal=simplePage.count;
    		var list = data.list;
    		$("#data-list .search-item").remove();
    		if (list.length > 0) {
    			 for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
    				  new SearchItem(d).appendTo($("#data-list"));
    			 }
    			 $('html,body').animate({
						scrollTop: 0
					},'fast');
    		} else {
    		    $('<div class="no-data search-item" >没有搜索到数据</div>').appendTo($("#data-list"));
    		}
        //显示分页
        laypage({
            cont: 'pager', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            skin: 'molv', //皮肤
            pages: response.data.page.pageTotal, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            jump: function(obj, first){ //触发分页后的回调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                    searchData(obj.curr);
                }
            }
        });
    });
};

function SearchItem(data){
	var url = "";
	var searchType = $("#select").val();
	var id = data.id;
	if(searchType=="T"){
		url = known.realpath+"/bbs/"+id;
	}else if(searchType=="K"){
		url = known.realpath+"/knowledge/"+id;
	}else if(searchType=="Z"){
		url = known.realpath+"/ask/"+id;
	}else if(searchType=="B"){
		url = known.realpath+"/user/"+data.userId+"/blog/"+id;
	}
	var item = $("<div class='search-item'></div>");
	$("<a href='"+url+"' target='_blank' class='search-item-title'>"+data.title+"</a><span class='time'>"+data.createTime+"</span><span class='search-by'>by</span><a class='search-user' target='_blank' href='"+known.realpath+"/user/"+data.userId+"'>"+data.userName+"</a>").appendTo(item);
	$("<div class='search-summary'>"+data.summary+"</div>").appendTo(item);
	return item;
}