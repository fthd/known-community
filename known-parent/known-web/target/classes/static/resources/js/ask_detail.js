$(document).ready(function() {
	$(document).on("mouseenter", ".comment-item", function() {
		$(this).find("a.accept-btn").show();
    });
   	 $(document).on("mouseleave", ".comment-item", function() {
		$(this).find("a.accept-btn").hide();
    });
    
    $(document).on("click", "a.accept-btn", function() {
		var commentid = $(this).attr("commentid");
		acceptAnswer(commentid);
    });
});

function acceptAnswer(commentid){
	$.ajax({
		url: known.realpath + '/ask/acceptAnswer',
		type: 'POST',
		dataType: 'json',
		data: {bestAnswerId : commentid,askId:known.topicId},
		success:function(res){
		if(res.msg != null){
			layer.alert(res.msg, {
			  icon: 5,
			  skin: 'layer-ext-moon' 
			});
			return;
			}
		else{
			layer.msg('采纳成功', {icon: 6,time:1000});
				location.reload(true);
			}
		}
	});
}