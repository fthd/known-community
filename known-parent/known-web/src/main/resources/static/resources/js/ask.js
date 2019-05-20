$(document).ready(function() {
	laypage({
	    cont: 'page',
	    pages: known.pageTotal, //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
	    skin: 'molv', //皮肤
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/pageNum=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	        	var s = location.search.match(/solveType=(\d+)/);
	        	s = s ?  "solveType="+s[1]+"&" : "";
	            location.href = '?'+ s + "pageNum=" + e.curr;
	        }
	    }
	});
	$("#toAsk").click(function(event) {
	if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
	document.location.href = known.realpath + "/ask/prePublicAsk";
	});
});
