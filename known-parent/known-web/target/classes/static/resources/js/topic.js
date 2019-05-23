$(document).ready(function() {
	$("#publicTopicBtn").click(function(event) {
		if(known.userId=="" || known.userId == 0){
    		goLogin();
    	    return;
    	}
		document.location.href = known.realpath + "/topic/prePublicTopic";
	});
});