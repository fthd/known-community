known.userUrl={
	saveUserPage:known.realpath+"/admin/saveUserPage"
}
$(function(){
	$("#saveUserPage").click(function(){
		saveUserPage();
	});
});


function saveUserPage(){
	$.ajax({
		url : known.userUrl.saveUserPage,
		type: 'POST',
		dataType: 'json',
		data : {
	    	userPage:$(".not-input:checked").val()
	    },
	    success:function(res){
	    	if(res.errorMsg != null){
					layer.msg(res.errorMsg, {icon: 5,time:1500});   
					return;
				}
			else{
					layer.msg('修改成功', {icon: 6,time:1500});
					location.reload();
			}
	    }
	});
};