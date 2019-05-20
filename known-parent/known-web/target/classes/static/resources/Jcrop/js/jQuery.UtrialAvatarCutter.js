jQuery.UtrialAvatarCutter = function(config){
	var h,w,x,y;
	this.os;
	var oh,ow;

	var api = null;

	var sel = this;

	var img_content_id = config.content;

	var img_id = "img_"+(Math.random()+"").substr(3,8);
	var purviews = new Array();

	var select_width = null;
	var select_height = null;

	if(config.purviews){
		for(i=0,c=config.purviews.length;i<c;++i){
			purviews[purviews.length] = config.purviews[i];
		}
	}
	
	check_thums_img = function(){
		if(config.purviews){
			for(i=0,c=config.purviews.length;i<c;++i){
				if($('#'+config.purviews[i].id+" img").length==0){
					$('#'+config.purviews[i].id).html("<img src='"+cutter.os+"'/>");
				}else{
					$('#'+config.purviews[i].id+" img").attr("src",cutter.os);
				}
			}
		}
	}

	/*
	 *	重新加载图片
	 */
	this.reload = function(img_url){
		if(img_url!=null && img_url != ""){
		//	this.os = img_url+"?"+Math.random();
            this.os = img_url;
			$("#"+img_content_id).html("<img id='"+img_id+"' src='"+this.os+"'/>");
			$("#"+img_id).bind("load",
				function(){
					check_thums_img();
					sel.init();
				}
			);
		}
	}
	$("#"+img_content_id+" img").attr("id",img_id);

	var preview = function(c) {
		if ( c.w == 0 || c.h == 0 ) {
			api.setSelect([ x, y, x+w, y+h ]);
			api.animateTo([ x, y, x+w, y+h ]);
			return;
		}
		x = c.x;
		y = c.y;
		w = c.w;
		h = c.h;
		for(i=0,c=purviews.length;i<c;++i){
			var purview = purviews[i];
			var rx = purview.width / w;
			var ry = purview.height / h;
			$('#'+purview.id+" img").css({
				width: Math.round(rx * ow) + 'px',
				height: Math.round(ry * oh) + 'px',
				marginLeft: '-' + Math.round(rx * x) + 'px',
				marginTop: '-' + Math.round(ry * y) + 'px'
			});
		}
	}

	this.init = function(){
		if(api!=null){
			api.destroy();
		}
		this.os = $("#"+img_content_id+" img").attr("src");
		if(this.os=="")
			return;
		check_thums_img();
		for(i=0,c=purviews.length;i<c;++i){
			var purview = purviews[i];
			var purview_content = $("#"+purview.id);
			purview_content.css({position: "relative", overflow:"hidden", height:purview.height+"px", width:purview.width+"px"});
		}

		oh = $('#'+img_id).height();
		ow = $('#'+img_id).width();
		
		select_width = config.selector.width;
		select_height = config.selector.height;	

		select_width = Math.min(ow,select_width);
		select_height = Math.min(oh,select_height);
		
		x = ((ow - select_width) / 2);
		y = ((oh - select_height) / 2);
		//这是原Jcrop配置,修改此处可修改Jcrop的其它各种功能
		api = $.Jcrop('#'+img_id,{ 
			aspectRatio: 1,
			onChange: preview,
			onSelect: preview
		});
		//设置选择框默认位置
		api.animateTo([ x, y, x+select_width, y+select_height ]);
		
	}

	this.submit = function(){
		return {w:w,h:h,x:x,y:y,s:this.os};
	}
}