$(function(){
    $("#downloadFile").click(function() {
	var topicid = $(this).attr("topicid");
	var attachmentid = $(this).attr("attachmentid");
	checkDownload(topicid, attachmentid);
    });
});
function checkDownload(topicid,attachmentid){
	var d = dialog({
		content: "<div><img src='" + known.realpath +"/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;加载中...</div>",
	});
	d.showModal();
	setTimeout(function() {
		d.close().remove();
	}, 1200);
	 	$.ajax({
			url : known.realpath+"/attachment/checkDownload",
			type: 'POST',
			dataType: 'json',
			data : {
			    attachmentId : attachmentid, topicId : topicid
			},
			success: function(res) {
				    if(res.data){
				    	document.location.href = known.realpath + "/attachment/downloadAction" + "?attachmentId=" + attachmentid;
					    var dcount = $("#dcount").text();
					    $("#dcount").text(parseInt(dcount) + 1);
				    }
				    else{
				    	layer.alert(res.msg, {
							  icon: 5,
							  skin: 'layer-ext-moon' 
							});
						return;
				    }
				}
		    });
}

