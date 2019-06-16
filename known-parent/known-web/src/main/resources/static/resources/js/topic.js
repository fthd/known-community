$(document).ready(function() {
	$("#publicTopicBtn").click(function(event) {
		if(known.userId==""){
    		goLogin();
    	    return;
    	}
		document.location.href = known.realpath + "/topic/prePublicTopic";
	});
});