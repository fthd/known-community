$(document).ready(function() {
				loadExamiers(1);
			});
			function loadExamiers(pageNum){
				known.pageNum = pageNum;
				$.ajax({
					url:'/exam/loadExamiers',
					type: 'POST',
					dataType: 'json',
					data: {"pageNum": pageNum},
					success:function(res){
						if(res.errorMsg != null){
							layer.alert(res.errorMsg, {
							  icon: 5,
							  skin: 'layer-ext-moon' 
							});
							return ;
						}
							var list = res.data.list;
							var simplePage = res.data.page;
							if(list.length==0){
								$('<div style="text-align:center;">还没有人出题，这么光荣的任务就你来吧！</div>').appendTo('#examiers-detail');
							}
							for (var i = 0, _len = list.length, data; i < _len, data = list[i]; i++) {
								new examier(data).appendTo($("#examiers-detail"));
							}
					}
				});				
			}
			function examier(data){
				var a = $('<a href="' +  known.realpath + '/user/' + data.userId + '"></a>');
				var img = $('<img src="' + data.userIcon + '" class="img-circle" style="display:inline;width:80px;height: 80px;">').appendTo(a);
				var userspan = $('<span>' + data.userName + '</span>').appendTo(a);
				var examisercount = $('<span class="examiers-count">出题：' + data.examCount + '</span>').appendTo(a);
				return a;
			}